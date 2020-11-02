package com.dongyang.tui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.HashMap;
import java.awt.Rectangle;
import com.dongyang.ui.base.TCBase;
import java.awt.Insets;
import com.dongyang.util.TypeTool;
import com.dongyang.util.TList;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.io.DSerializable;
import java.awt.Cursor;
import com.dongyang.util.StringTool;
import com.dongyang.ui.util.TDrawTool;
import java.awt.Font;
import javax.swing.UIManager;
import com.dongyang.tui.control.DComponentControl;
import com.dongyang.util.RunClass;
import java.awt.event.KeyEvent;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.TFrame;
import com.dongyang.ui.TDialog;
import javax.swing.JOptionPane;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TWindow;

/**
 *
 * <p>Title: 控件基础类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.12
 * @version 1.0
 */
public class DComponent implements DSerializable
{
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         DEFAULT_OPTION = -1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_OPTION = 0;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_CANCEL_OPTION = 1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         OK_CANCEL_OPTION = 2;

    //
    // Return values.
    //
    /** Return value from class method if YES is chosen. */
    public static final int         YES_OPTION = 0;
    /** Return value from class method if NO is chosen. */
    public static final int         NO_OPTION = 1;
    /** Return value from class method if CANCEL is chosen. */
    public static final int         CANCEL_OPTION = 2;
    /** Return value form class method if OK is chosen. */
    public static final int         OK_OPTION = 0;
    /**
     * 鼠标控制边框的尺寸
     */
    static final int MOUSE_BORDER_SIZE = 3;
    /**
     * 鼠标控制边框顶角扩展的尺寸
     */
    static final int MOUSE_BORDER_LENGTH = 10;
    /**
     * 连接线起始点尺寸
     */
    static final int LINK_LINE_SIZE = 5;
    /**
     * 属性
     */
    Map attribute = new HashMap();
    /**
     * 构造器
     */
    public DComponent()
    {
        setVisible(true);
        setEnabled(true);
        //初始化尺寸
        setBounds(new DRectangle());
        //初始化内部尺寸
        setInsets(new DInsets());
        setColor("黑");
        //设置默认边框尺寸
        setDefaultInsets();
    }
    /**
     * 设置默认边框尺寸
     */
    public void setDefaultInsets()
    {
    }

    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        if(name == null || name.length() == 0)
        {
            attribute.remove("D_name");
            return;
        }
        attribute.put("D_name",name);
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return (String)attribute.get("D_name");
    }
    /**
     * 设置标签
     * @param tag String
     */
    public void setTag(String tag)
    {
        if(tag == null || tag.length() == 0)
        {
            attribute.remove("D_tag");
            return;
        }
        attribute.put("D_tag",tag);
    }
    /**
     * 得到标签
     * @return String
     */
    public String getTag()
    {
        return (String)attribute.get("D_tag");
    }
    /**
     * 设置尺寸
     * @param rectangle DRectangle
     */
    public void setBounds(DRectangle rectangle)
    {
        attribute.put("D_bound",rectangle);
    }
    /**
     * 设置尺寸
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setBounds(int x,int y,int width,int height)
    {
        DRectangle rectangle = getBounds();
        boolean local = rectangle.getX() == x &&
                        rectangle.getY() == y;
        boolean size =  rectangle.getWidth() == width &&
                        rectangle.getHeight() == height;
        if(local && size)
            return;
        repaintBlank(getComponentBounds());
        rectangle.setBounds(x, y, width, height);
        repaintBlank(getComponentBounds());
        if(!local)
            onComponentMoved();
        if(!size)
            onComponentResized();
    }
    /**
     * 得到尺寸
     * @return DRectangle
     */
    public DRectangle getBounds()
    {
        return (DRectangle)attribute.get("D_bound");
    }
    /**
     * 设置是否同步TCBase尺寸
     * @param autoBaseSize boolean
     */
    public void setAutoBaseSize(boolean autoBaseSize)
    {
        if(!autoBaseSize)
        {
            attribute.remove("D_autoBaseSize");
            return;
        }
        attribute.put("D_autoBaseSize",autoBaseSize);
    }
    /**
     * 是否同步TCBase尺寸
     * @return boolean
     */
    public boolean isAutoBaseSize()
    {
        return TypeTool.getBoolean(attribute.get("D_autoBaseSize"));
    }
    /**
     * 得到组件尺寸
     * @return DRectangle
     */
    public DRectangle getComponentBounds()
    {
        if(isUIEditStatus() && !isNoUIEdit())
            return getBounds();
        //同步TCBase尺寸
        if(isAutoBaseSize())
        {
            TCBase tcBase = getTCBase();
            if(tcBase == null)
                return getBounds();
            Rectangle r = tcBase.getBounds();
            Insets insetsBase = tcBase.getInsets();
            return new DRectangle(0,0,r.width - insetsBase.left - insetsBase.right,
                                  r.height - insetsBase.top - insetsBase.bottom);
        }
        DRectangle r = getBounds().cloneThis();

        DLinkLine line = getLinkComponent(1);
        if(line != null && line.toComponent != null)
        {
            r.setY(line.toComponent.getLinkPoint(line.toType,line.size));
            if(line.toType == 6)
                r.setHeight(line.size);
        }
        line = getLinkComponent(2);
        if(line != null && line.toComponent != null)
        {
            if(line.toType == 5)
                r.setY(0);
            r.setHeight(line.toComponent.getLinkPoint(line.toType, line.size) - r.getY());
        }
        line = getLinkComponent(3);
        if(line != null && line.toComponent != null)
        {
            r.setX(line.toComponent.getLinkPoint(line.toType,line.size));
            if(line.toType == 8)
                r.setWidth(line.size);
        }
        line = getLinkComponent(4);
        if(line != null && line.toComponent != null)
        {
            if(line.toType == 7)
                r.setX(0);
            r.setWidth(line.toComponent.getLinkPoint(line.toType, line.size) - r.getX());
        }
        return r;
    }
    /**
     * 得到连接点坐标
     * @param type int
     * @param size int
     * @return int
     */
    public int getLinkPoint(int type,int size)
    {
        switch(type)
        {
        case 5://top
            return size;
        case 6://bottom
            return getInsetHeight() - size;
        case 7://left
            return size;
        case 8://right
            return getInsetWidth() - size;
        }
        return 0;
    }
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        DRectangle rectangle = getBounds();
        x = checkXRang(x);
        y = checkYRang(y);
        if(rectangle.getX() == x && rectangle.getY() == y)
            return;
        repaintBlank(getComponentBounds());
        rectangle.setLocation(x,y);
        repaintBlank(getComponentBounds());
        //位置变化
        onComponentMoved();
    }
    /**
     * 设置横坐标
     * @param x int
     */
    public void setX(int x)
    {
        DRectangle r = getBounds();
        x = checkXRang(x);
        if(r.getX() == x)
            return;
        repaintBlank(getComponentBounds());
        r.setX(x);
        repaintBlank(getComponentBounds());
        //位置变化
        onComponentMoved();
    }
    /**
     * 设置横坐标
     * @return int
     */
    public int getX()
    {
        return getBounds().getX();
    }
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
        DRectangle r = getBounds();
        y = checkYRang(y);
        if(r.getY() == y)
            return;
        repaintBlank(getComponentBounds());
        r.setY(y);
        repaintBlank(getComponentBounds());
        //位置变化
        onComponentMoved();
    }
    /**
     * 得到纵坐标
     * @return int
     */
    public int getY()
    {
        return getBounds().getY();
    }
    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        DRectangle r = getBounds();
        repaintBlank(getComponentBounds());
        r.setWidth(width);
        repaintBlank(getComponentBounds());
        //尺寸变化
        onComponentResized();
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return getBounds().getWidth();
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        DRectangle r = getBounds();
        repaintBlank(getComponentBounds());
        r.setHeight(height);
        repaintBlank(getComponentBounds());
        //尺寸变化
        onComponentResized();
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getHeight()
    {
        return getBounds().getHeight();
    }
    /**
     * 得到控件显示宽度
     * @return int
     */
    public int getWidthV()
    {
        return getComponentBounds().getWidth();
    }
    /**
     * 得到控件显示高度
     * @return int
     */
    public int getHeightV()
    {
        return getComponentBounds().getHeight();
    }
    /**
     * 设置尺寸
     * @param size DSize
     */
    public void setSize(DSize size)
    {
        if(size == null)
            return;
        setSize(size.getWidth(),size.getHeight());
    }
    /**
     * 设置尺寸
     * @param width int
     * @param height int
     */
    public void setSize(int width,int height)
    {
        DRectangle rectangle = getBounds();
        if(rectangle.getWidth() == width && rectangle.getHeight() == height)
            return;
        repaintBlank(getComponentBounds());
        rectangle.setSize(width,height);
        repaintBlank(getComponentBounds());
        //尺寸变化
        onComponentResized();
    }
    /**
     * 设置能否调整尺寸
     * @param b boolean
     */
    public void setCanResetSize(boolean b)
    {
        if(b == canResetSize())
            return;
        if(b)
        {
            attribute.remove("D_canResetSize");
            return;
        }
        attribute.put("D_canResetSize",!b);
    }
    /**
     * 能否调整尺寸
     * @return boolean
     */
    public boolean canResetSize()
    {
        return !TypeTool.getBoolean(attribute.get("D_canResetSize"));
    }
    /**
     * 调整尺寸
     */
    public void resetSize()
    {

    }
    /**
     * 得到内部宽度
     * @return int
     */
    public int getInsetWidth()
    {
        DRectangle r = getComponentBounds();
        DInsets i = getInsets();
        return r.getWidth() - i.left - i.right;
    }
    /**
     * 得到内部高度
     * @return int
     */
    public int getInsetHeight()
    {
        DRectangle r = getComponentBounds();
        DInsets i = getInsets();
        return r.getHeight() - i.top - i.bottom;
    }
    /**
     * 得到内部尺寸
     * @return DSize
     */
    public DSize getInsetSize()
    {
        DRectangle r = getComponentBounds();
        DInsets i = getInsets();
        return new DSize(r.getWidth() - i.left - i.right,r.getHeight() - i.top - i.bottom);
    }
    /**
     * 得到父类内部宽度
     * @return int
     */
    public int getParentInsetWidth()
    {
        DComponent com = getParent();
        if(com != null)
            return com.getInsetWidth();
        TCBase base = getParentTCBase();
        if(base != null)
            return base.getInsetWidth();
        return 0;
    }
    /**
     * 得到父类内部高度
     * @return int
     */
    public int getParentInsetHeight()
    {
        DComponent com = getParent();
        if(com != null)
            return com.getInsetHeight();
        TCBase base = getParentTCBase();
        if(base != null)
            return base.getInsetHeight();
        return 0;
    }
    /**
     * 得到内部X
     * @return int
     */
    public int getInsetX()
    {
        return getInsets().left;
    }
    /**
     * 得到内部Y
     * @return int
     */
    public int getInsetY()
    {
        return getInsets().top;
    }

    /**
     * 设置背景颜色
     * @param color String
     */
    public void setBKColor(String color)
    {
        attribute.put("D_bkColor",color);
    }
    /**
     * 设置背景颜色
     * @param r int
     * @param g int
     * @param b int
     */
    public void setBKColor(int r,int g,int b)
    {
        setBKColor(r + "," + g + "," + b);
    }
    /**
     * 设置背景颜色
     * @param color Color
     */
    public void setBKColor(Color color)
    {
        setBKColor(color.getRed(),color.getGreen(),color.getBlue());
    }
    /**
     * 得到背景颜色
     * @return String
     */
    public String getBKColor()
    {
        return (String)attribute.get("D_bkColor");
    }
    /**
     * 设置前景颜色
     * @param color String
     */
    public void setColor(String color)
    {
        attribute.put("D_color",color);
    }
    /**
     * 设置前景颜色
     * @param r int
     * @param g int
     * @param b int
     */
    public void setColor(int r,int g,int b)
    {
        setColor(r + "," + g + "," + b);
    }
    /**
     * 设置前景颜色
     * @param color Color
     */
    public void setColor(Color color)
    {
        setColor(color.getRed(),color.getGreen(),color.getBlue());
    }
    /**
     * 得到前景颜色
     * @return String
     */
    public String getColor()
    {
        return (String)attribute.get("D_color");
    }
    /**
     * 设置边框
     * @param border String
     */
    public void setBorder(String border)
    {
        attribute.put("D_border",border);
        //设置内部尺寸
        DBorder.setInsets(this,border);
    }
    /**
     * 得到边框
     * @return String
     */
    public String getBorder()
    {
        return (String)attribute.get("D_border");
    }
    /**
     * 设置内部尺寸
     * @param insets DInsets
     */
    public void setInsets(DInsets insets)
    {
        attribute.put("D_insets",insets);
    }
    /**
     * 设置内部尺寸
     * @param top int
     * @param left int
     * @param bottom int
     * @param right int
     */
    public void setInsets(int top,int left,int bottom,int right)
    {
        getInsets().setInsets(top,left,bottom,right);
    }
    /**
     * 得到内部尺寸
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return (DInsets)attribute.get("D_insets");
    }
    /**
     * 设置字体
     * @param font String
     */
    public void setFont(String font)
    {
        attribute.put("D_font",font);
    }
    /**
     * 得到字体
     * @return String
     */
    public String getFont()
    {
        return (String)attribute.get("D_font");
    }
    /**
     * 设置是否显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        if(!visible)
        {
            attribute.remove("D_visible");
            return;
        }
        attribute.put("D_visible",visible);
    }
    /**
     * 是否现实
     * @return boolean
     */
    public boolean isVisible()
    {
        return TypeTool.getBoolean(attribute.get("D_visible"));
    }
    /**
     * 设置有效
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled)
    {
        if(!enabled)
        {
            attribute.remove("D_enabled");
            return;
        }
        attribute.put("D_enabled",enabled);
    }
    /**
     * 是否有效
     * @return boolean
     */
    public boolean isEnabled()
    {
        return TypeTool.getBoolean(attribute.get("D_enabled"));
    }
    /**
     * 设置父类
     * @param com DComponent
     */
    public void setParent(DComponent com)
    {
        if(com == null)
        {
            attribute.remove("D_parent");
            return;
        }
        attribute.put("D_parent",com);
    }
    /**
     * 得到父类
     * @return DComponent
     */
    public DComponent getParent()
    {
        return (DComponent)attribute.get("D_parent");
    }
    /**
     * 设置父类Java组件
     * @param com TCBase
     */
    public void setParentTCBase(TCBase com)
    {
        if(com == null)
        {
            attribute.remove("D_parentTCBase");
            return;
        }
        attribute.put("D_parentTCBase",com);
    }
    /**
     * 得到父类Java组件
     * @return TCBase
     */
    public TCBase getParentTCBase()
    {
        return (TCBase)attribute.get("D_parentTCBase");
    }
    /**
     * 得到TCBase
     * @return TCBase
     */
    public TCBase getTCBase()
    {
        DComponent parent = getParent();
        if(parent != null)
            return parent.getTCBase();
        return getParentTCBase();
    }
    /**
     * 设置鼠标X位置
     * @param x int
     */
    public void setMouseX(int x)
    {
        attribute.put("D_mouseX",x);
    }
    /**
     * 得到鼠标X位置
     * @return int
     */
    public int getMouseX()
    {
        return TypeTool.getInt(attribute.get("D_mouseX"));
    }
    /**
     * 设置鼠标Y位置
     * @param y int
     */
    public void setMouseY(int y)
    {
        attribute.put("D_mouseY",y);
    }
    /**
     * 得到鼠标Y位置
     * @return int
     */
    public int getMouseY()
    {
        return TypeTool.getInt(attribute.get("D_mouseY"));
    }
    /**
     * 设置鼠标位置
     * @param x int
     * @param y int
     */
    public void setMousePoint(int x,int y)
    {
        setMouseX(x);
        setMouseY(y);
    }
    /**
     * 设置坐标点
     * @param point DPoint
     */
    public void setMousePoint(DPoint point)
    {
        if(point == null)
        {
            setMouseX(-1);
            setMouseY(-1);
            return;
        }
        setMouseX(point.getX());
        setMouseY(point.getY());
    }
    /**
     * 得到坐标点
     * @return DPoint
     */
    public DPoint getMousePoint()
    {
        return new DPoint(getMouseX(),getMouseY());
    }
    /**
     * 设置鼠标按下X位置
     * @param x int
     */
    public void setMouseDownX(int x)
    {
        attribute.put("D_mouseDownX",x);
    }
    /**
     * 得到鼠标按下X位置
     * @return int
     */
    public int getMouseDownX()
    {
        return TypeTool.getInt(attribute.get("D_mouseDownX"));
    }
    /**
     * 设置鼠标按下Y位置
     * @param y int
     */
    public void setMouseDownY(int y)
    {
        attribute.put("D_mouseDownY",y);
    }
    /**
     * 得到鼠标按下Y位置
     * @return int
     */
    public int getMouseDownY()
    {
        return TypeTool.getInt(attribute.get("D_mouseDownY"));
    }
    /**
     * 设置鼠标按下位置
     * @param x int
     * @param y int
     */
    public void setMouseDownPoint(int x,int y)
    {
        setMouseDownX(x);
        setMouseDownY(y);
    }
    /**
     * 设置按下坐标点
     * @param point DPoint
     */
    public void setMouseDownPoint(DPoint point)
    {
        if(point == null)
        {
            setMouseDownX(-1);
            setMouseDownY(-1);
            return;
        }
        setMouseDownX(point.getX());
        setMouseDownY(point.getY());
    }
    /**
     * 得到坐标点
     * @return DPoint
     */
    public DPoint getMouseDownPoint()
    {
        return new DPoint(getMouseDownX(),getMouseDownY());
    }
    /**
     * 设置鼠标滑轮移动尺寸
     * @param i int
     */
    public void setMouseWheelMoved(int i)
    {
        attribute.put("D_mouseWheelMoved",i);
    }
    /**
     * 得到鼠标滑轮移动尺寸
     * @return int
     */
    public int getMouseWheelMoved()
    {
        return TypeTool.getInt(attribute.get("D_mouseWheelMoved"));
    }
    /**
     * 设置鼠标拖入控件
     * @param com DComponent
     */
    public void setMouseDraggedInComponent(DComponent com)
    {
        if(com == null)
        {
            attribute.remove("D_mouseDraggedInComponent");
            return;
        }
        attribute.put("D_mouseDraggedInComponent",com);
    }
    /**
     * 得到鼠标拖入控件
     * @return DComponent
     */
    public DComponent getMouseDraggedInComponent()
    {
        return (DComponent)attribute.get("D_mouseDraggedInComponent");
    }
    /**
     * 设置鼠标拖入到控件
     * @param com DComponent
     */
    public void setMouseDraggedGotoComponent(DComponent com)
    {
        if(com == null)
        {
            attribute.remove("D_mouseDraggedGotoComponent");
            return;
        }
        attribute.put("D_mouseDraggedGotoComponent",com);
    }
    /**
     * 得到鼠标拖入到控件
     * @return DComponent
     */
    public DComponent getMouseDraggedGotoComponent()
    {
        return (DComponent)attribute.get("D_mouseDraggedGotoComponent");
    }
    /**
     * 设置移动类型
     * @param type int
     */
    public void setMouseMoveType(int type)
    {
        if(type == 0)
        {
            attribute.remove("D_mouseMoveType");
            return;
        }
        attribute.put("D_mouseMoveType",type);
    }
    /**
     * 得到移动类型
     * @return int
     */
    public int getMouseMoveType()
    {
        return TypeTool.getInt(attribute.get("D_mouseMoveType"));
    }
    /**
     * 设置键盘事件
     * @param e KeyEvent
     */
    public void setKeyEvent(KeyEvent e)
    {
        if(e == null)
        {
            attribute.remove("D_keyEvent");
            return;
        }
        attribute.put("D_keyEvent",e);
    }
    /**
     * 得到键盘事件
     * @return KeyEvent
     */
    public KeyEvent getKeyEvent()
    {
        return (KeyEvent)attribute.get("D_keyEvent");
    }
    /**
     * 得到组件列表
     * @return TList
     */
    public TList getDComponentList()
    {
        return (TList)attribute.get("D_components");
    }
    /**
     * 设置组件列表
     * @param list TList
     */
    public void setDComponentList(TList list)
    {
        if(list == null)
        {
            attribute.remove("D_components");
            return;
        }
        attribute.put("D_components",list);
    }
    /**
     * 得到组件的位置
     * @param com DComponent
     * @return int
     */
    public int getDComponentIndex(DComponent com)
    {
        TList list = getDComponentList();
        if(list == null)
            return -1;
        return list.indexOf(com);
    }
    /**
     * 增加组件
     * @param component DComponent
     */
    public void addDComponent(DComponent component)
    {
        if(component == null)
            return;
        TList components = getDComponentList();
        if(components == null)
        {
            components = new TList();
            attribute.put("D_components",components);
        }
        components.add(component);
        component.setParent(this);
    }
    /**
     * 删除组件
     * @param component DComponent
     */
    public void removeDComponent(DComponent component)
    {
        if(component == null)
            return;
        TList components = (TList)attribute.get("D_components");
        if(components == null)
            return;
        components.remove(component);
        if(components.size() == 0)
            attribute.remove("D_components");
        //删除鼠标所在组件
        if(getMouseInComponent() == component)
            setMouseInComponent(null);
    }
    /**
     * 删除组件
     * @param tag String
     */
    public void removeDComponent(String tag)
    {
        if(tag == null || tag.length() == 0)
            return;
        DComponent component = getDComponent(tag);
        if(component == null)
            return;
        removeDComponent(component);
    }
    /**
     * 得到组件
     * @param index int
     * @return DComponent
     */
    public DComponent getDComponent(int index)
    {
        TList components = (TList)attribute.get("D_components");
        if(components == null)
            return null;
        return (DComponent)components.get(index);
    }
    /**
     * 查找子对象
     * @param tag String
     * @return DComponent
     */
    public DComponent getDComponent(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        TList list = getDComponentList();
        if(list == null)
            return null;
        for(int i = 0;i < list.size();i++)
        {
            DComponent component = (DComponent)list.get(i);
            if(tag.equalsIgnoreCase(component.getTag()))
                return component;
        }
        return null;
    }
    /**
     * 得到组件个数
     * @return int
     */
    public int getDComponentCount()
    {
        TList components = (TList)attribute.get("D_components");
        if(components == null)
            return 0;
        return components.size();
    }
    /**
     * 设置鼠标所在控件
     * @param com DComponent
     */
    public void setMouseInComponent(DComponent com)
    {
        if(com == null)
        {
            attribute.remove("D_mouseInComponent");
            return;
        }
        attribute.put("D_mouseInComponent",com);
    }
    /**
     * 得到鼠标所在控件
     * @return DComponent
     */
    public DComponent getMouseInComponent()
    {
        return (DComponent)attribute.get("D_mouseInComponent");
    }
    /**
     * 设置鼠标按下控件
     * @param com DComponent
     */
    public void setMousePressedComponent(DComponent com)
    {
        if(com == null)
        {
            attribute.remove("D_mousePressedComponent");
            return;
        }
        attribute.put("D_mousePressedComponent",com);
    }
    /**
     * 得到鼠标按下控件
     * @return DComponent
     */
    public DComponent getMousePressedComponent()
    {
        return (DComponent)attribute.get("D_mousePressedComponent");
    }
    /**
     * 设置左键按下组件标记
     * @param b boolean
     */
    public void setMouseLeftKeyDownComponent(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_mouseLeftKeyDownComponent");
            return;
        }
        attribute.put("D_mouseLeftKeyDownComponent",b);
    }
    /**
     * 得到左键按下组件标记
     * @return boolean
     */
    public boolean isMouseLeftKeyDownComponent()
    {
        return TypeTool.getBoolean(attribute.get("D_mouseLeftKeyDownComponent"));
    }
    /**
     * 设置左键按下标记
     * @param b boolean
     */
    public void setMouseLeftKeyDown(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_mouseLeftKeyDown");
            return;
        }
        attribute.put("D_mouseLeftKeyDown",b);
    }
    /**
     * 得到左键按下标记
     * @return boolean
     */
    public boolean isMouseLeftKeyDown()
    {
        return TypeTool.getBoolean(attribute.get("D_mouseLeftKeyDown"));
    }
    /**
     * 设置鼠标边框拖动类型
     * @param type int
     *  11111111(二进制)
     *  上,下,左,右,左上,右上,右下,左下
     *  使用为运算标记255表示全部开启
     */
    public void setMouseBorderDraggedType(int type)
    {
        if(type == 0)
        {
            attribute.remove("D_mouseBorderDraggedType");
            return;
        }
        attribute.put("D_mouseBorderDraggedType",type);
    }
    /**
     * 得到鼠标边框拖动类型
     * @return int
     *  11111111(二进制)
     *  上,下,左,右,左上,右上,右下,左下
     *  使用为运算标记255表示全部开启
     */
    public int getMouseBorderDraggedType()
    {
        return TypeTool.getInt(attribute.get("D_mouseBorderDraggedType"));
    }
    /**
     * 设置鼠标边框拖动状态
     * @param type int
     * 0 停止
     * 1 上
     * 2 下
     * 3 左
     * 4 右
     * 5 左上
     * 6 右上
     * 7 右下
     * 8 左下
     */
    public void setMouseBorderDraggedState(int type)
    {
        if(type == 0)
        {
            attribute.remove("D_mouseBorderDraggedState");
            return;
        }
        attribute.put("D_mouseBorderDraggedState",type);
    }
    /**
     * 得到鼠标边框拖动状态
     * @return int
     * 0 停止
     * 1 上
     * 2 下
     * 3 左
     * 4 右
     * 5 左上
     * 6 右上
     * 7 右下
     * 8 左下
     */
    public int getMouseBorderDraggedState()
    {
        return TypeTool.getInt(attribute.get("D_mouseBorderDraggedState"));
    }
    /**
     * 设置光标
     * @param cursor int
     */
    public void setCursor(int cursor)
    {
        if(StringTool.equals(getCursor(),cursor))
            return;
        if(cursor == 0)
        {
            attribute.remove("D_cursor");
            updateCursor(cursor);
            return;
        }
        attribute.put("D_cursor",cursor);
        updateCursor(cursor);
    }
    /**
     * 得到光标
     * @return int
     */
    public int getCursor()
    {
        return TypeTool.getInt(attribute.get("D_cursor"));
    }
    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text)
    {
        attribute.put("D_text",text);
    }
    /**
     * 得到文本
     * @return String
     */
    public String getText()
    {
        return (String)attribute.get("D_text");
    }
    /**
     * 设置字符横向位置
     * @param horizontalAlignment String
     */
    public void setHorizontalAlignment(String horizontalAlignment)
    {
        if(horizontalAlignment == null)
        {
            attribute.remove("D_horizontalAlignment");
            return;
        }
        attribute.put("D_horizontalAlignment",horizontalAlignment);
    }
    /**
     * 得到字符横向位置
     * @return String
     */
    public String getHorizontalAlignment()
    {
        return (String)attribute.get("D_horizontalAlignment");
    }
    /**
     * 设置字符纵向位置
     * @param verticalAlignment String
     */
    public void setVerticalAlignment(String verticalAlignment)
    {
        if(verticalAlignment == null)
        {
            attribute.remove("D_verticalAlignment");
            return;
        }
        attribute.put("D_verticalAlignment",verticalAlignment);
    }
    /**
     * 得到字符的纵向位置
     * @return String
     */
    public String getVerticalAlignment()
    {
        return (String)attribute.get("D_verticalAlignment");
    }
    /**
     * 设置鼠标是否进入
     * @param b boolean
     */
    public void setMouseEntered(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_mouseEntered");
            return;
        }
        attribute.put("D_mouseEntered",b);
    }
    /**
     * 得到鼠标是否进入
     * @return boolean
     */
    public boolean isMouseEntered()
    {
        return TypeTool.getBoolean(attribute.get("D_mouseEntered"));
    }
    /**
     * 设置鼠标拖入
     * @param type int
     * 1 上
     * 2 下
     * 3 左
     * 4 右
     */
    public void setMouseDraggedIn(int type)
    {
        if(type == 0)
        {
            attribute.remove("D_mouseDraggedIn");
            return;
        }
        attribute.put("D_mouseDraggedIn",type);
    }
    /**
     * 得到鼠标拖入
     * @return int
     */
    public int getMouseDraggedIn()
    {
        return TypeTool.getInt(attribute.get("D_mouseDraggedIn"));
    }
    /**
     * 设置UI编辑状态
     * @param b boolean
     */
    public void setUIEditStatus(boolean b)
    {
        if(isUIEditStatus() == b)
            return;
        if(!b)
        {
            attribute.remove("D_UIEditStatus");
            setMouseMoveType(0);
            setMouseBorderDraggedType(0);
        }else
        {
            attribute.put("D_UIEditStatus", b);
            setMouseMoveType(1);
            setMouseBorderDraggedType(255);
        }
        repaint();
        TList list = getDComponentList();
        if(list == null)
            return;
        for(int i = 0;i < list.size();i++)
        {
            DComponent component = (DComponent)list.get(i);
            if(component == null)
                continue;
            component.setUIEditStatus(b);
        }
    }
    /**
     * 得到UI编辑状态
     * @return boolean
     */
    public boolean isUIEditStatus()
    {
        return TypeTool.getBoolean(attribute.get("D_UIEditStatus"));
    }
    /**
     * 设置禁止编辑
     * @param b boolean
     */
    public void setNoUIEdit(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_noUIEdit");
            return;
        }
        attribute.put("D_noUIEdit",b);
    }
    /**
     * 是否禁止编辑
     * @return boolean
     */
    public boolean isNoUIEdit()
    {
        return TypeTool.getBoolean(attribute.get("D_noUIEdit"));
    }
    /**
     * 设置UI编辑选中
     * @param b boolean
     */
    public void setUIEditSelecgted(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_UIEditSelecgted");
            return;
        }
        attribute.put("D_UIEditSelecgted",b);
    }
    /**
     * 得到UI编辑选中
     * @return boolean
     */
    public boolean isUIEditSelected()
    {
        return TypeTool.getBoolean(attribute.get("D_UIEditSelecgted"));
    }
    //====================================================//
    // 事件区域
    //====================================================//
    /**
     * 得到ctrl建是否按下
     * @return boolean
     */
    public boolean isControlDown()
    {
        DComponent comParent = getParent();
        if(comParent != null)
            return comParent.isControlDown();
        TCBase tcb = getParentTCBase();
        if(tcb != null)
            return tcb.isControlDown();
        return false;
    }
    /**
     * 得到shift建是否按下
     * @return boolean
     */
    public boolean isShiftDown()
    {
        DComponent comParent = getParent();
        if(comParent != null)
            return comParent.isShiftDown();
        TCBase tcb = getParentTCBase();
        if(tcb != null)
            return tcb.isShiftDown();
        return false;
    }
    /**
     * 重新设置鼠标位置
     * @param com DComponent
     */
    public void resetMousePoint(DComponent com)
    {
        if(com == null)
            return;
        DRectangle r = com.getComponentBounds();
        //保存坐标
        DInsets insets = getInsets();
        int x = getMouseX() - insets.left;
        int y = getMouseY() - insets.right;
        com.setMousePoint(x - r.getX(), y - r.getY());
    }
    /**
     * 查找鼠标位置在哪个控件
     * @return DComponent
     */
    private DComponent findMousePointInComponent()
    {
        DInsets insets = getInsets();
        int x = getMouseX() - insets.left;
        int y = getMouseY() - insets.right;
        if(x < 0 || y < 0)
            return null;
        return findMousePointInComponent(x,y);
    }
    /**
     * 查找鼠标位置在那个控件
     * @param x int
     * @param y int
     * @return DComponent
     */
    private DComponent findMousePointInComponent(int x,int y)
    {
        return findMousePointInComponent(x,y,false);
    }
    /**
     * 查找鼠标位置在那个控件
     * @param x int
     * @param y int
     * @param b boolean true 不包括自己　false 包括自己
     * @return DComponent
     */
    private DComponent findMousePointInComponent(int x,int y,boolean b)
    {
        TList components = getDComponentList();
        if(components == null)
            return null;
        for(int i = components.size() - 1;i >= 0;i--)
        {
            DComponent com =  (DComponent)components.get(i);
            if(com == null)
                continue;
            if(b && com == this)
                continue;
            //组件是隐含的
            if(!com.isVisible())
                continue;
            if(!com.isEnabled())
                continue;
            //坐标在组件范围内
            if(com.inMouse(x,y))
                return com;
        }
        return null;
    }
    /**
     * 查找鼠标位置在那个控件(包括自组件)
     * @param x int
     * @param y int
     * @param comSrc DComponent 排出的组件
     * @return DComponent
     */
    public DComponent findMousePointInComponentIncludeChild(int x,int y,DComponent comSrc)
    {
        TList components = getDComponentList();
        if(components == null)
            return null;
        for(int i = components.size() - 1;i >= 0;i--)
        {
            DComponent com =  (DComponent)components.get(i);
            if(com == null)
                continue;
            if(com == comSrc)
                continue;
            //组件是隐含的
            if(!com.isVisible())
                continue;
            //坐标在组件范围内
            if(com.inMouse(x,y))
            {
                DInsets insets = com.getInsets();
                DComponent childCom = com.findMousePointInComponentIncludeChild(x - com.getX() - insets.left,y - com.getY() - insets.top,comSrc);
                if(childCom != null)
                    return childCom;
                return com;
            }
        }
        return null;
    }
    /**
     * 查找鼠标位置在哪个控件
     * @param comSrc DComponent 排出的组件
     * @return DComponent
     */
    public DComponent findParentMouseInComponent(DComponent comSrc)
    {
        DComponent com = null;
        DComponent parent = getParent();
        if(parent != null)
        {
            com = parent.findParentMouseInComponent(comSrc);
            if(com != null)
                return com;
            return null;
        }
        DInsets insets = getInsets();
        com = findMousePointInComponentIncludeChild(getMouseX() - insets.left,getMouseY() - insets.top,comSrc);
        if(com != null)
            return com;
        if(comSrc != this && inMouse())
            return this;
        return null;
    }
    /**
     * 鼠标坐标在本控件区间
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean inMouse(int x,int y)
    {
        DRectangle rectangle = getComponentBounds();
        if(x >= rectangle.getX() && x <= rectangle.getX() + rectangle.getWidth() &&
           y >= rectangle.getY() && y <= rectangle.getY() + rectangle.getHeight())
            return true;
        return false;
    }
    /**
     * 鼠标坐标在本控件区间
     * @return boolean
     */
    public boolean inMouse()
    {
        DRectangle r = getComponentBounds();
        return inMouse(getMouseX() + r.getX(),getMouseY() + r.getY());
    }
    /**
     * 鼠标进入
     * @return boolean
     */
    public boolean onMouseEntered()
    {
        return false;
    }
    /**
     * 鼠标离开
     * @return boolean
     */
    public boolean onMouseExited()
    {
        mouseMouseExitedListener();
        setMouseEntered(false);
        return false;
    }
    /**
     * 设置默认光标
     */
    public void setDefaultCursor()
    {
        updateCursor(getCursor());
    }
    /**
     * 鼠标移动
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        //边框检测
        if(checkMouseBorderDragged())
        {
            setMouseEntered(false);
            return true;
        }
        //内部组件检测
        if(mouseMovedListener())
        {
            setMouseEntered(false);
            return true;
        }
        //连接线起点检测
        if(mouseMovedLinkLine())
        {
            setMouseEntered(false);
            return true;
        }
        setMouseEntered(true);
        return false;
    }
    /**
     * 测试鼠标在边框上的图标
     * @return boolean true 存在 false 不存在
     */
    public boolean checkMouseBorderDragged()
    {
        int type = getMouseBorderDraggedType();
        if(type == 0)
        {
            setDefaultCursor();
            return false;
        }
        DRectangle r = getComponentBounds();
        int wlength = MOUSE_BORDER_LENGTH > r.getWidth() / 3?r.getWidth() / 3:MOUSE_BORDER_LENGTH;
        int hlength = MOUSE_BORDER_LENGTH > r.getHeight() / 3?r.getHeight() / 3:MOUSE_BORDER_LENGTH;

        if((type & 1) == 1 && getMouseY() < MOUSE_BORDER_SIZE &&
           getMouseX() > wlength &&
           getMouseX() < r.getWidth() - wlength)
        {
            updateCursor(DCursor.N_RESIZE_CURSOR);
            setMouseBorderDraggedState(1);
            return true;
        }
        if(((type >> 1) & 1) == 1 && getMouseY() > r.getHeight() - MOUSE_BORDER_SIZE &&
           getMouseX() > wlength &&
           getMouseX() < r.getWidth() - wlength)
        {
            updateCursor(DCursor.N_RESIZE_CURSOR);
            setMouseBorderDraggedState(2);
            return true;
        }
        if(((type >> 2) & 1) == 1 && getMouseX() < MOUSE_BORDER_SIZE &&
           getMouseY() > hlength &&
           getMouseY() < r.getHeight() - hlength)
        {
            updateCursor(DCursor.E_RESIZE_CURSOR);
            setMouseBorderDraggedState(3);
            return true;
        }
        if(((type >> 3) & 1) == 1 && getMouseX() > r.getWidth() - MOUSE_BORDER_SIZE &&
           getMouseY() > hlength &&
           getMouseY() < r.getHeight() - hlength)
        {
            updateCursor(DCursor.E_RESIZE_CURSOR);
            setMouseBorderDraggedState(4);
            return true;
        }
        if(((type >> 4) & 1) == 1 &&
           ((getMouseY() < MOUSE_BORDER_SIZE && getMouseX() <= wlength)||
            getMouseX() < MOUSE_BORDER_SIZE && getMouseY() <= hlength))
        {
            updateCursor(DCursor.NW_RESIZE_CURSOR);
            setMouseBorderDraggedState(5);
            return true;
        }
        if(((type >> 5) & 1) == 1 &&
           ((getMouseY() < MOUSE_BORDER_SIZE && getMouseX() >= r.getWidth() - wlength)||
            getMouseX() > r.getWidth() - MOUSE_BORDER_SIZE && getMouseY() <= hlength))
        {
            updateCursor(DCursor.NE_RESIZE_CURSOR);
            setMouseBorderDraggedState(6);
            return true;
        }
        if(((type >> 6) & 1) == 1 &&
           ((getMouseY() > r.getHeight() -  MOUSE_BORDER_SIZE && getMouseX() >= r.getWidth() - wlength)||
            getMouseX() > r.getWidth() -  MOUSE_BORDER_SIZE && getMouseY() >= r.getHeight() - hlength))
        {
            updateCursor(DCursor.NW_RESIZE_CURSOR);
            setMouseBorderDraggedState(7);
            return true;
        }
        if(((type >> 7) & 1) == 1 &&
           ((getMouseY() > r.getHeight() -  MOUSE_BORDER_SIZE && getMouseX() <= wlength)||
            getMouseX() < MOUSE_BORDER_SIZE && getMouseY() >= r.getHeight() - hlength))
        {
            updateCursor(DCursor.NE_RESIZE_CURSOR);
            setMouseBorderDraggedState(8);
            return true;
        }
        setDefaultCursor();
        setMouseBorderDraggedState(0);
        return false;
    }
    /**
     * 设置光标
     * @param cursor int
     */
    void updateCursor(int cursor)
    {
        updateCursor(DCursor.getCursor(cursor));
    }
    /**
     * 设置光标
     * @param cursor Cursor
     */
    public void updateCursor(Cursor cursor) {
        DComponent com = getParent();
        if(com != null)
        {
            com.updateCursor(cursor);
            return;
        }
        TCBase comBase = getParentTCBase();
        if(comBase != null)
            comBase.setCursor(cursor);
    }
    /**
     * 左键按下
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        //边框检测
        if(checkMouseBorderDragged())
        {
            //记录鼠标按下点
            setMouseDownPoint(getMousePoint());
            setMouseLeftKeyDown(true);
            return true;
        }
        //自组件传递
        if(isEnabled() && mouseLeftPressedListener())
            return true;
        //连接线起点检测
        if(mouseLeftPressedLinkLine())
            return true;
        if(!isEnabled())
            return false;
        //记录鼠标按下点
        setMouseDownPoint(getMousePoint());
        setMouseLeftKeyDown(true);
        return false;
    }
    /**
     * 中键按下
     * @return boolean
     */
    public boolean onMouseCenterPressed()
    {
        if(mouseCenterPressedListener())
            return true;
        return false;
    }
    /**
     * 右键按下
     * @return boolean
     */
    public boolean onMouseRightPressed()
    {
        if(mouseRightPressedListener())
            return true;
        return false;
    }
    /**
     * 左键抬起
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        setMouseLeftKeyDown(false);
        if(mouseLeftReleasedListener())
            return true;
        //连接线起点检测
        if(mouseLeftReleasedLinkLine())
            return true;
        if(!inMouse())
            setMouseEntered(false);
        return false;
    }
    /**
     * 中键抬起
     * @return boolean
     */
    public boolean onMouseCenterReleased()
    {
        if(mouseCenterReleasedListener())
            return true;
        return false;
    }
    /**
     * 右键抬起
     * @return boolean
     */
    public boolean onMouseRightReleased()
    {
        if(mouseRightReleasedListener())
            return true;
        return false;
    }
    /**
     * 点击
     * @return boolean
     */
    public boolean onClickedS()
    {
        if(mouseClickedListener())
            return true;
        return false;
    }
    /**
     * 双击
     * @return boolean
     */
    public boolean onDoubleClickedS()
    {
        if(mouseDoubleClickedListener())
            return true;
       return false;
    }
    /**
     * 鼠标滑轮
     * @return boolean
     */
    public boolean onMouseWheelMoved()
    {
        if(mouseWheelMovedListener())
            return true;
        return false;
    }
    /**
     * 鼠标拖动
     * @return boolean
     */
    public boolean onMouseDragged()
    {
        //内部组件拖动监听
        if(mouseDraggedListener())
            return true;
        //鼠标边框拖动监听
        if(mouseBorderDraggedListener())
            return true;
        //连接线起点检测
        if(mouseDraggedLinkLine())
            return true;
        //鼠标拖动组件监听
        if(mouseDraggedMoveListener())
            return true;
        setMouseEntered(inMouse());
        return false;
    }
    /**
     * 是否监听父类尺寸
     * @return boolean true 在监听 false 不再监听
     */
    public boolean isListenerParentResized()
    {
        if(isAutoBaseSize())
            return true;
        DLinkLine line = getLinkComponent(2);
        if(line != null && line.toComponent != null && line.toComponent == getParent() && line.toType == 6)
            return true;
        line = getLinkComponent(4);
        if(line != null && line.toComponent != null && line.toComponent == getParent() && line.toType == 8)
            return true;
        return false;
    }
    /**
     * 父类尺寸改变
     * @return boolean
     */
    public boolean onParentResized()
    {
        if(isListenerParentResized())
        {
            onComponentResized();
            TList list = getDComponentList();
            if (list == null)
                return true;
            for (int i = 0; i < list.size(); i++)
            {
                DComponent com = (DComponent) list.get(i);
                if (com == null)
                    continue;
                com.onParentResized();
            }
        }
        return true;
    }
    /**
     * 尺寸改变
     * @return boolean
     */
    public boolean onComponentResized()
    {
        if(isUIEditStatus() && getParent() != null)
            getParent().repaint();
        return true;
    }
    /**
     * 位置改变
     * @return boolean
     */
    public boolean onComponentMoved()
    {
        if(isUIEditStatus() && getParent() != null)
            getParent().repaint();
        return true;
    }
    /**
     * 窗口鼠标按键
     */
    public void onWindowMousePressed()
    {

    }
    /**
     * 设置当前是否是焦点
     * @param b boolean
     */
    public void setIsFocus(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_isFocus");
            return;
        }
        attribute.put("D_isFocus",b);
    }
    /**
     * 当前是否是焦点
     * @return boolean
     */
    public boolean isFocus()
    {
        return TypeTool.getBoolean(attribute.get("D_isFocus"));
    }
    /**
     * 得到焦点事件
     * @return boolean
     */
    public boolean onFocusGained()
    {
        setIsFocus(true);
        return false;
    }
    /**
     * 失去焦点事件
     * @return boolean
     */
    public boolean onFocusLost()
    {
        setIsFocus(false);
        return false;
    }
    /**
     * 键盘按下
     * @return boolean
     */
    public boolean onKeyPressed()
    {
        return false;
    }
    /**
     * 键盘抬起
     * @return boolean
     */
    public boolean onKeyReleased()
    {
        return false;
    }
    /**
     * 击打
     * @return boolean
     */
    public boolean onKeyTyped()
    {
        return false;
    }
    /**
     * 测试x位置
     * @return int[]
     */
    public int[] checkXRange()
    {
        //return new int[]{0,getParentInsetWidth() - getWidth()};
        return null;
    }
    public int checkXRang(int x)
    {
        int[] c = checkXRange();
        if(c == null || c.length == 0)
            return x;
        if(x < c[0])
            return c[0];
        if(c.length > 1 && x > c[1])
            return c[1];
        return x;
    }
    public int[] checkYRange()
    {
        //return new int[]{0,getParentInsetHeight() - getHeight()};
        return null;
    }
    public int checkYRang(int y)
    {
        int[] c = checkYRange();
        if(c == null || c.length == 0)
            return y;
        if(y < c[0])
            return c[0];
        if(c.length > 1 && y > c[1])
            return c[1];
        return y;
    }
    /**
     * 测试尺寸大小
     * @param width int
     * @param height int
     * @return boolean
     */
    public boolean checkSize(int width,int height)
    {
        return true;
    }
    /**
     * 鼠标拖动组件监听
     * @return boolean
     */
    private boolean mouseDraggedMoveListener()
    {
        switch(getMouseMoveType())
        {
        case 0:
            return false;
        case 1:
            int x = getMouseX() - getMouseDownX() + getX();
            int y = getMouseY() - getMouseDownY() + getY();
            setLocation(x,y);
            //测试拖动嵌入组件
            checkDraggedComponent();
            return true;
        case 2:
            setX(getMouseX() - getMouseDownX() + getX());
            return true;
        case 3:
            setY(getMouseY() - getMouseDownY() + getY());
            return true;
        }
        return false;
    }
    /**
     * 测试拖动组件
     */
    private void checkDraggedComponent()
    {
        //按下Ctrl键启动功能
        if(!isControlDown())
            return;
        DComponent com = findParentMouseInComponent(this);
        if(com == null)
            return;
        if(com == getParent())
            return;
        DComponent comParent = getParent();
        if(comParent != null)
        {
            comParent.cancelMouseFollowing();
            comParent.setMousePressedComponent(null);
            comParent.removeDComponent(this);
        }
        com.addDComponent(this);
        resetMouseFollowing();
        DInsets insets = com.getInsets();
        setLocation(com.getMouseX() - getMouseDownX() - insets.left,
                com.getMouseY() - getMouseDownY() - insets.top);
        //设置连接线转移
        for(int i = 1;i <= 4;i++)
        {
            DLinkLine line = getLinkComponent(i);
            if(line == null)
                continue;
            removeLinkComponent(i);
            if(line.toComponent == comParent)
            {
                line.toComponent = getParent();
                setLinkComponent(i,line.toComponent,line.toType,line.size);
            }
        }
    }
    /**
     * 鼠标拖动上边框
     */
    private void mouseBorderDragged1()
    {
        int y = getMouseY() - getMouseDownY();
        int h = getHeight() - y;
        if(h < 0)
        {
            y += h;
            h = 0;
        }
        y += getY();
        int y0 = checkYRang(y);
        h -= y0 - y;
        setY(y0);
        setHeight(h);
    }
    /**
     * 鼠标拖动下边框
     */
    private void mouseBorderDragged2()
    {
        if(getHeight() == 0 && getMouseY() < getMouseDownY())
            return;
        int y = getMouseY() - getMouseDownY();
        int h = getHeight() + y;
        if(h < 0)
        {
            setMouseDownY(h - getMouseY());
            h = 0;
        }
        else
            setMouseDownY(getMouseY());
        setHeight(h);
    }
    /**
     * 鼠标拖动左边框
     */
    private void mouseBorderDragged3()
    {
        int x = getMouseX() - getMouseDownX();
        int w = getWidth() - x;
        if(w < 0)
        {
            x += w;
            w = 0;
        }
        x += getX();
        int x0 = checkXRang(x);
        w -= x0 - x;
        setX(x0);
        setWidth(w);
    }
    /**
     * 鼠标拖动右边框
     */
    private void mouseBorderDragged4()
    {
        if(getWidth() == 0 && getMouseX() < getMouseDownX())
            return;
        int x = getMouseX() - getMouseDownX();
        int w = getWidth() + x;
        if(w < 0)
        {
            setMouseDownX(w - getMouseX());
            w = 0;
        }
        else
            setMouseDownX(getMouseX());
        setWidth(w);
    }
    /**
     * 鼠标边框拖动监听
     * @return boolean
     */
    private boolean mouseBorderDraggedListener()
    {
        switch(getMouseBorderDraggedState())
        {
        case 0:
            return false;
        case 1://上
            mouseBorderDragged1();
            return true;
        case 2://下
            mouseBorderDragged2();
            return true;
        case 3://左
            mouseBorderDragged3();
            return true;
        case 4://右
            mouseBorderDragged4();
            return true;
        case 5://左上
            mouseBorderDragged3();
            mouseBorderDragged1();
            return true;
        case 6://右上
            mouseBorderDragged4();
            mouseBorderDragged1();
            return true;
        case 7://右下
            mouseBorderDragged4();
            mouseBorderDragged2();
            return true;
        case 8://左下
            mouseBorderDragged3();
            mouseBorderDragged2();
            return true;
        }
        return false;
    }
    /**
     * 鼠标拖入
     */
    public void onMouseDraggedIn()
    {
        setMouseDraggedIn(5);
        repaint();
        if(!isUIEditStatus())
            return;
        System.out.println(getTag() + " onMouseDraggedIn" + getMouseDraggedInComponent());
    }
    /**
     * 鼠标拖离
     */
    public void onMouseDraggedOut()
    {
        setMouseDraggedIn(0);
        repaint();
        if(!isUIEditStatus())
            return;
        System.out.println(getTag() + " onMouseDraggedOut" + getMouseDraggedInComponent());
    }
    /**
     * 鼠标拖停
     */
    public void onMouseDraggedStop()
    {
        if(!isUIEditStatus())
            return;
        System.out.println(getTag() + " onMouseDraggedStop" + getMouseDraggedInComponent());
    }
    /**
     * 鼠标移动组件传递
     * @return boolean
     */
    private boolean mouseMovedListener()
    {
        DComponent com = findMousePointInComponent();
        DComponent oldCom = getMouseInComponent();
        if (oldCom != null && oldCom != com)
        {
            oldCom.onMouseExited();
            setMouseInComponent(null);
        }
        if (com == null)
            return false;
        if(isUIEditStatus() && com.isNoUIEdit())
            return false;
        resetMousePoint(com);
        if (oldCom != com)
        {
            setMouseInComponent(com);
            com.onMouseEntered();
        }
        com.onMouseMoved();
        return true;
    }
    /**
     * 鼠标移出组件传递
     * @return boolean
     */
    private boolean mouseMouseExitedListener()
    {
        DComponent oldCom = getMouseInComponent();
        if (oldCom == null)
            return false;
        oldCom.onMouseExited();
        setMouseInComponent(null);
        return true;
    }
    /**
     * 移动时连接线起点检测
     * @return boolean
     */
    private boolean mouseMovedLinkLine()
    {
        if(!isUIEditStatus())
            return false;
        DLinkLine line = inMouseLinkLineStartPoint();
        if(line == null)
        {
            setDefaultCursor();
            return false;
        }
        updateCursor(DCursor.CROSS);
        return true;
    }
    /**
     * 按下左键连接线起点检测
     * @return boolean
     */
    private boolean mouseLeftPressedLinkLine()
    {
        if(!isUIEditStatus())
            return false;
        if(isNoUIEdit())
            return false;
        DLinkLine line = inMouseLinkLineStartPoint();
        if(line == null)
        {
            setDefaultCursor();
            return false;
        }
        setDefaultCursor();
        setMoveLinkLine(line);
        return true;
    }
    /**
     * 抬起左键连接线起点检测
     * @return boolean
     */
    private boolean mouseLeftReleasedLinkLine()
    {
        if(!isUIEditStatus())
            return false;
        if(isNoUIEdit())
            return false;
        DLinkLine line = getMoveLinkLine();
        if(line == null)
            return false;
        if(line.toComponent != null)
            line.fromComponent.removeLinkComponent(line.fromType);
        int index = getMouseDraggedIn();
        if(index > 0)
        {
            //if(line.toComponent != null)
            //    line.fromComponent.removeLinkComponent(line.fromType);
            line.fromComponent.setLinkComponent(line.fromType,this,index + 4,0);
        }
        setMoveLinkLine(null);
        setDefaultCursor();
        setMouseDraggedIn(0);
        repaint();
        return true;
    }
    /**
     * 拖动连接线起点检测
     * @return boolean
     */
    private boolean mouseDraggedLinkLine()
    {
        if(!isUIEditStatus())
            return false;
        if(isNoUIEdit())
            return false;
        DLinkLine line = getMoveLinkLine();
        if(line == null)
            return false;
        //测试连接线拖动位置
        checkDraggedLinkLine(line.fromType);
        repaint();
        return true;
    }
    /**
     * 测试连接线拖动位置
     * @param type int
     */
    private void checkDraggedLinkLine(int type)
    {
        DInsets insets = getInsets();
        int x = getMouseX() - insets.left;
        int y = getMouseY() - insets.top;
        if(y < 5 && (type == 1 || type == 2))
        {
            setMouseDraggedIn(1);
            return;
        }
        if(y > this.getInsetHeight() - 5 && (type == 1 || type == 2))
        {
            setMouseDraggedIn(2);
            return;
        }
        if(x < 5 && (type == 3 || type == 4))
        {
            setMouseDraggedIn(3);
            return;
        }
        if(x > this.getInsetWidth() - 5 && (type == 3 || type == 4))
        {
            setMouseDraggedIn(4);
            return;
        }
        setMouseDraggedIn(0);
    }
    /**
     * 测试鼠标在外四点区域内
     * @return DLinkLine
     */
    private DLinkLine inMouseLinkLineStartPoint()
    {
        TList list = getDComponentList();
        if(list == null)
            return null;
        DInsets insets = getInsets();
        int x = getMouseX() - insets.left;
        int y = getMouseY() - insets.right;
        for(int i = 0;i < list.size();i++)
        {
            DComponent com = (DComponent)list.get(i);
            if(com == null)
                continue;
            if(com.isNoUIEdit())
                return null;
            //测试鼠标在外四点区域内
            int index = inMouseLinkLineStartPoint(x,y,com.getComponentBounds());
            if(index > 0)
            {
                DLinkLine line = com.getLinkComponent(index);
                if(line != null)
                    return line;
                line = new DLinkLine();
                line.fromComponent = com;
                line.fromType = index;
                return line;
            }
        }
        return null;
    }
    /**
     * 左键按下组件传递
     * @return boolean
     */
    private boolean mouseLeftPressedListener()
    {
        DComponent com = findMousePointInComponent();
        if (com == null)
            return false;
        if(isUIEditStatus() && com.isNoUIEdit())
            return false;
        resetMousePoint(com);
        //记录鼠标按下组件
        setMousePressedComponent(com);
        setMouseLeftKeyDownComponent(true);
        com.onMouseLeftPressed();
        return true;
    }
    /**
     * 撤销鼠标组件跟踪
     */
    public void cancelMouseFollowing()
    {
        setMousePressedComponent(null);
        setMouseLeftKeyDownComponent(false);
        //撤销父类
        DComponent comParent = getParent();
        if(comParent != null)
        {
            comParent.cancelMouseFollowing();
            return;
        }
        //撤销基类
        TCBase base = getParentTCBase();
        if(base != null)
            base.setMousePressedComponent(null);
    }
    public void resetMouseFollowing()
    {
        DComponent comParent = getParent();
        if(comParent != null)
        {
            comParent.resetMouseFollowing(this);
            return;
        }
        //设置基类
        TCBase base = getParentTCBase();
        if(base != null)
        {
            base.setMousePressedComponent(this);
            base.resetMousePoint(this);
        }
    }
    /**
     * 重新调整鼠标组件跟踪
     * @param com DComponent
     */
    public void resetMouseFollowing(DComponent com)
    {
        if(com == null)
            return;
        setMousePressedComponent(com);
        resetMousePoint(com);
        setMouseLeftKeyDownComponent(true);
        //设置基类
        DComponent comParent = getParent();
        if(comParent != null)
        {
            comParent.resetMouseFollowing(this);
            return;
        }
        //设置基类
        TCBase base = getParentTCBase();
        if(base != null)
        {
            base.setMousePressedComponent(this);
            base.resetMousePoint(this);
        }
    }
    /**
     * 中键按下组件传递
     * @return boolean
     */
    private boolean mouseCenterPressedListener()
    {
        DComponent com = findMousePointInComponent();
        if (com == null)
            return false;
        resetMousePoint(com);
        setMousePressedComponent(com);
        com.onMouseCenterPressed();
        return true;
    }
    /**
     * 右键按下组件传递
     * @return boolean
     */
    private boolean mouseRightPressedListener()
    {
        DComponent com = findMousePointInComponent();
        if (com == null)
            return false;
        resetMousePoint(com);
        setMousePressedComponent(com);
        com.onMouseRightPressed();
        return true;
    }
    /**
     * 左键抬起组件传递
     * @return boolean
     */
    private boolean mouseLeftReleasedListener()
    {
        DComponent com = getMousePressedComponent();
        if(com == null)
            return false;
        resetMousePoint(com);
        setMouseLeftKeyDownComponent(false);
        com.onMouseLeftReleased();
        setMousePressedComponent(null);
        setMouseLeftKeyDownComponent(false);
        return true;
    }
    /**
     * 中键抬起组件传递
     * @return boolean
     */
    private boolean mouseCenterReleasedListener()
    {
        DComponent com = getMousePressedComponent();
        if(com == null)
            return false;
        resetMousePoint(com);
        com.onMouseCenterReleased();
        setMousePressedComponent(null);
        return true;
    }
    /**
     * 右键抬起组件传递
     * @return boolean
     */
    private boolean mouseRightReleasedListener()
    {
        DComponent com = getMousePressedComponent();
        if(com == null)
            return false;
        resetMousePoint(com);
        com.onMouseCenterReleased();
        setMousePressedComponent(null);
        return true;
    }
    /**
     * 点击组件传递
     * @return boolean
     */
    private boolean mouseClickedListener()
    {
        DComponent com = findMousePointInComponent();
        if(com == null)
            return false;
        resetMousePoint(com);
        com.onClickedS();
        return true;
    }
    /**
     * 双击组件传递
     * @return boolean
     */
    private boolean mouseDoubleClickedListener()
    {
        DComponent com = findMousePointInComponent();
        if(com == null)
            return false;
        resetMousePoint(com);
        com.onDoubleClickedS();
        return true;
    }
    /**
     * 滑轮滚动组件传递
     * @return boolean
     */
    private boolean mouseWheelMovedListener()
    {
        DComponent com = findMousePointInComponent();
        if(com == null)
            return false;
        resetMousePoint(com);
        com.setMouseWheelMoved(getMouseWheelMoved());
        com.onMouseWheelMoved();
        return true;
    }
    /**
     * 拖动组件传递
     * @return boolean
     */
    private boolean mouseDraggedListener()
    {
        if(!isMouseLeftKeyDownComponent())
            return false;
        DComponent com = getMousePressedComponent();
        if(com == null)
            return false;
        resetMousePoint(com);
        if(com.onMouseDragged())
            return true;
        return true;
    }
    /**
     * 组件是否被覆盖
     * @param component DComponent
     * @param index int 显示层
     * @return boolean true 覆盖 false 没有被覆盖
     */
    public boolean isBestrowCom(DComponent component,int index)
    {
        if(component == null)
            return true;
        TList components = getDComponentList();
        //组件在最顶部
        if(index == components.size() - 1)
            return false;
        DRectangle tc = component.getComponentBounds();
        //组件尺寸<0
        if(tc.getWidth() <= 0 || tc.getHeight() <= 0)
            return true;
        TList list = new TList();
        list.add(tc);
        //处理遮盖
        for(int i = index + 1;i < components.size();i++)
        {
            DComponent com = (DComponent)components.get(i);
            if(com == null)
                continue;
            tc.getNoBestrolList(list,com.getComponentBounds());
            if(list == null || list.size() == 0)
                return true;
        }
        return false;
    }
    /**
     * 得到当前的方法名
     * @param index int 位置
     * @return String 方法名
     */
    public String getThisMethodName(int index)
    {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        for(int i = 0;i < stack.length;i++)
            if("getThisMethodName".equals(stack[i].getMethodName()))
                return stack[i + index + 1].getMethodName();
        return "";
    }
    /**
     * 要求重画
     */
    public void repaint() {
        DRectangle r = getComponentBounds();
        repaint(0, 0, 0, r.getWidth(), r.getHeight());
    }
    /**
     * 填充父类空白
     * @param rectangle DRectangle
     */
    public void repaintBlank(DRectangle rectangle)
    {
        if(rectangle == null)
            return;
        DComponent parent = getParent();
        if (parent != null)
        {
            DInsets insets = parent.getInsets();
            parent.repaint(0, rectangle.getX() + insets.left,
                           rectangle.getY() + insets.top,
                           rectangle.getWidth(),
                           rectangle.getHeight());
            return;
        }
        TCBase cParent = getParentTCBase();
        if(cParent == null)
            return;
        Insets insets = cParent.getInsets();
        cParent.repaint(0, rectangle.getX() + insets.left,
                   rectangle.getY() + insets.top,
                   rectangle.getWidth(),
                   rectangle.getHeight());
    }
    /**
     * 要求重画
     * @param tm long 最小时间
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void repaint(long tm, int x, int y, int width, int height)
    {
        DComponent parent = getParent();
        TCBase cParent = getParentTCBase();
        if(cParent == null && parent == null)
            return;
        DRectangle r = getComponentBounds();
        int px = r.getX() + ((x < 0) ? 0 : x);
        int py = r.getY() + ((y < 0) ? 0 : y);
        int w = r.getWidth();
        int h = r.getHeight();
        int pwidth = (width > w) ? w : width;
        int pheight = (height > h) ? h : height;

        if (parent != null) {
            DInsets insets = parent.getInsets();
            parent.repaint(tm, px + insets.left, py + insets.top, pwidth, pheight);
            return;
        }
        Insets insets = cParent.getInsets();
        cParent.repaint(tm, px + insets.left, py + insets.top, pwidth, pheight);
    }
    /**
     * 测试绘制区域
     * @param g Graphics
     */
    public void checkPaintClipBounds(Graphics g)
    {
        DRectangle r = getComponentBounds();
        Rectangle rg = g.getClipBounds();
        if(rg.getX() > r.getX() + r.getWidth() ||
           rg.getY() > r.getY() + r.getHeight() ||
           rg.getX() + rg.getWidth() < r.getX() ||
           rg.getY() + rg.getHeight() < r.getY())
            return;
        Graphics g1 = g.create(r.getX(),r.getY(),r.getWidth(),r.getHeight());
        paint(g1);
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        //绘制边框
        paintBorder(g);
        Rectangle r = g.getClipBounds();
        if(r.getWidth() <= 0|| r.getHeight() <= 0)
            return;
        //得到内部图形设备
        Graphics insetsG;
        DInsets insets = this.getInsets();
        DRectangle d = getComponentBounds();
        int widht = d.getWidth();
        int height = d.getHeight();
        if(insets.isEmpty())
            insetsG = g;
        else
        {
            widht -= insets.left + insets.right;
            height -= insets.top + insets.bottom;
            insetsG = g.create(insets.left, insets.top, widht, height);
        }
        //绘制背景
        paintBackground(insetsG,widht,height);
        //绘制编辑背景
        paintUIEditBackground(insetsG,widht,height);
        //绘制组件
        paintComponents(insetsG,widht,height);
        //绘制前景
        paintForeground(insetsG,widht,height);
        //绘制拖入焦点
        paintMouseDraggedIn(insetsG,widht,height);
        //绘制编辑
        if(isUIEditStatus() && !isNoUIEdit())
        {
            //绘制编辑状态
            paintUIEditStatus(insetsG, d.getWidth(), d.getHeight());
            //绘制移动中的连接线
            paintUIEditMoveLinkLine(insetsG,widht,height);
        }
    }
    /**
     * 绘制拖入焦点
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintMouseDraggedIn(Graphics g,int width,int height)
    {
        int t = 5;
        int jg = 1;
        float f = 0.5f;
        Color c = new Color(0,0,255);
        switch(getMouseDraggedIn())
        {
        case 0:
            return;
        case 1://上
            TDrawTool.fillAlpha(0,0,width,t,c,jg,f,g);
            break;
        case 2://下
            TDrawTool.fillAlpha(0,height - t,width,t,c,jg,f,g);
            break;
        case 3://左
            TDrawTool.fillAlpha(0,0,t,height,c,jg,f,g);
            break;
        case 4://右
            TDrawTool.fillAlpha(width - t,0,t,height,c,jg,f,g);
            break;
        case 5:
            TDrawTool.fillAlpha(0,0,width,height,c,jg,f,g);
            break;
        }
    }
    /**
     * 绘制编辑状态
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintUIEditStatus(Graphics g,int width,int height)
    {
        paintUIEditText(g,width,height);
        //绘制编辑选中
        if(isUIEditSelected())
            paintUIEditSelecgted(g,width,height);
    }
    /**
     * 绘制编辑选中
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintUIEditSelecgted(Graphics g,int width,int height)
    {
        TDrawTool.fillAlpha(0,0,4,4,new Color(0,0,255),0,0.5f,g);
        TDrawTool.fillAlpha(width - 4,0,4,4,new Color(0,0,255),0,0.5f,g);
        TDrawTool.fillAlpha(0,height - 4,4,4,new Color(0,0,255),0,0.5f,g);
        TDrawTool.fillAlpha(width - 4,height - 4,4,4,new Color(0,0,255),0,0.5f,g);
    }
    /**
     * 绘制编辑标签
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintUIEditText(Graphics g,int width,int height)
    {
        String s = getTag();
        if(s == null)
            s = "";
        if(s.length() == 0)
            s = "(none)";
        g.setColor(new Color(255,0,0));
        g.setFont(new Font("宋体",0,11));
        g.drawString(s,5,10);
    }
    /**
     * 绘制组件
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintComponents(Graphics g,int width,int height)
    {
        TList components = getDComponentList();
        if(components == null)
            return;
        for(int i = 0;i < components.size();i ++)
        {
            DComponent com = (DComponent)components.get(i);
            if(com == null)
                continue;
            if(!com.isVisible())
                continue;
            if(isBestrowCom(com,i))
                continue;
            com.checkPaintClipBounds(g);
        }
    }
    /**
     * 绘制前景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {
        paintText(g,width,height);
    }
    /**
     * 绘制移动中的连接线
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintUIEditMoveLinkLine(Graphics g,int width,int height)
    {
        DLinkLine line = getMoveLinkLine();
        if(line == null)
            return;
        if(line.toComponent != null)
            paintUIEditLinkLine(g,width / 2,height / 2,width,height,line,new Color(155,0,0));
        //得到连接线的起始点
        DPoint pointStart = getLinkLineStartPoint(line);
        DPoint pointEnd = getLinkLineEndPoint();
        g.setColor(new Color(255,0,0));
        g.drawLine(pointStart.x,pointStart.y,pointEnd.x,pointEnd.y);
    }
    /**
     * 得到连接线的结束点
     * @return DPoint
     */
    public DPoint getLinkLineEndPoint()
    {
        switch(getMouseDraggedIn())
        {
        case 1://上
            return new DPoint(getInsetWidth() / 2,LINK_LINE_SIZE / 2);
        case 2://下
            return new DPoint(getInsetWidth() / 2,getInsetHeight() - LINK_LINE_SIZE / 2);
        case 3://左
            return new DPoint(LINK_LINE_SIZE / 2,getInsetHeight() / 2);
        case 4://右
            return new DPoint(getInsetWidth() - LINK_LINE_SIZE / 2,getInsetHeight() / 2);
        }
        return new DPoint(getMouseX(),getMouseY());
    }
    /**
     * 得到连接线的起始点
     * @param line DLinkLine
     * @return DPoint
     */
    public DPoint getLinkLineStartPoint(DLinkLine line)
    {
        if(line.fromComponent == null)
            return null;
        DRectangle r = line.fromComponent.getComponentBounds();
        int t = LINK_LINE_SIZE;
        switch(line.fromType)
        {
        case 1:
            return new DPoint(r.getX() + r.getWidth() / 2,r.getY() - t / 2);
        case 2:
            return new DPoint(r.getX() + r.getWidth() / 2,r.getY() + r.getHeight() + t / 2);
        case 3:
            return new DPoint(r.getX() - t / 2,r.getY() + r.getHeight() / 2);
        case 4:
            return new DPoint(r.getX() + r.getWidth() + t / 2,r.getY() + r.getHeight() / 2);
        }
        return null;
    }
    /**
     * 绘制背景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int width,int height)
    {
        String bkColor = getBKColor();
        if(bkColor != null && bkColor.length() > 0)
            TDrawTool.fillRect(0,0,width,height,DColor.getColor(bkColor),g);
    }
    /**
     * 绘制编辑背景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintUIEditBackground(Graphics g,int width,int height)
    {
        if(isUIEditStatus() && !isNoUIEdit())
            paintUIEditLinkLine(g,width,height);
    }
    /**
     * 绘制连接线
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintUIEditLinkLine(Graphics g,int width,int height)
    {
        g.setColor(new Color(0, 0, 255));
        int x0 = width / 2;
        int y0 = height / 2;
        g.fillRect(0, y0 - 2, 5, 5);
        g.fillRect(width - 5, y0 - 2, 5, 5);
        g.fillRect(x0 - 2, 0, 5, 5);
        g.fillRect(x0 - 2, height - 5, 5, 5);
        //绘制全部组件的起始点
        pointUIEditLinkLineStartPointAllChildComponent(g,new Color(0,128,0));
        //绘制连接线
        for (int i = 1; i <= 8; i++)
        {
            TList list = getLinkLine(i);
            if (list == null)
                continue;
            for (int j = 0; j < list.size(); j++)
            {
                DLinkLine line = (DLinkLine) list.get(j);
                if (line == null)
                    continue;
                if (line.toComponent != this)
                    continue;
                if (line.fromComponent.getParent() != this)
                    continue;
                paintUIEditLinkLine(g,x0,y0,width,height,line,new Color(0,0,255));
            }
        }
    }
    /**
     * 绘制全部组件的起始点
     * @param g Graphics
     * @param color Color
     */
    public void pointUIEditLinkLineStartPointAllChildComponent(Graphics g,Color color)
    {
        TList list = getDComponentList();
        if(list == null)
            return;
        for(int i = 0;i < list.size();i++)
        {
            DComponent com = (DComponent)list.get(i);
            if(com == null)
                continue;
            if(com.isNoUIEdit())
                continue;
            com.pointUIEditLinkLineStartPoint(g, color);
        }
    }
    /**
     * 绘制连接线的起始点
     * @param g Graphics
     * @param color Color
     */
    public void pointUIEditLinkLineStartPoint(Graphics g,Color color)
    {
        DRectangle r = getComponentBounds();
        for(int i = 1;i <= 4;i++)
            pointUIEditLinkLineStartPoint(g,color,r,i,0);
    }
    /**
     * 测试鼠标在外四点区域内
     * @param x int
     * @param y int
     * @param r DRectangle
     * @return int
     */
    public int inMouseLinkLineStartPoint(int x,int y,DRectangle r)
    {
        int t = LINK_LINE_SIZE;
        int x0 = r.getX() + r.getWidth() / 2 - 2;
        int y0 = r.getY() - t;
        if(x >= x0 && x <= x0 + t && y >= y0 && y <= y0 + t)
            return 1;
        y0 = r.getY() + r.getHeight();
        if(x >= x0 && x <= x0 + t && y >= y0 && y <= y0 + t)
            return 2;
        x0 = r.getX() - t;
        y0 = r.getY() + r.getHeight() / 2 - 2;
        if(x >= x0 && x <= x0 + t && y >= y0 && y <= y0 + t)
            return 3;
        x0 = r.getX() + r.getWidth();
        if(x >= x0 && x <= x0 + t && y >= y0 && y <= y0 + t)
            return 4;
        return 0;
    }
    /**
     * 绘制连接线的起始点
     * @param g Graphics
     * @param color Color
     * @param r DRectangle
     * @param fromType int
     * @param type int
     */
    public void pointUIEditLinkLineStartPoint(Graphics g,Color color,DRectangle r,int fromType,int type)
    {
        int t = LINK_LINE_SIZE;
        int x = 0;
        int y = 0;
        switch(fromType)
        {
        case 1://y1
            x = r.getX() + r.getWidth() / 2 - 2;
            y = r.getY() - t;
            break;
        case 2://y2
            x = r.getX() + r.getWidth() / 2 - 2;
            y = r.getY() + r.getHeight();
            break;
        case 3://x1
            x = r.getX() - t;
            y = r.getY() + r.getHeight() / 2 - 2;
            break;
        case 4://x2
            x = r.getX() + r.getWidth();
            y = r.getY() + r.getHeight() / 2 - 2;
            break;
        }
        g.setColor(color);
        switch(type)
        {
        case 0:
            g.drawRect(x,y,t - 1,t - 1);
            break;
        case 1:
            g.fillRect(x,y,t,t);
            break;
        case 2:
            g.drawOval(x,y,t - 1,t - 1);
            break;
        case 3:
            g.fillOval(x,y,t,t);
            break;
        }
    }
    /**
     * 绘制连接线
     * @param g Graphics
     * @param x0 int
     * @param y0 int
     * @param width int
     * @param height int
     * @param line DLinkLine
     * @param color Color
     */
    public void paintUIEditLinkLine(Graphics g,int x0,int y0,int width,int height,DLinkLine line,Color color)
    {
        if(line.toComponent != this)
            return;
        //绘制内部四点连线
        if (line.fromComponent == null)
            return;
        DRectangle r = line.fromComponent.getComponentBounds();
        //绘制连接线的起始点
        pointUIEditLinkLineStartPoint(g,new Color(255,0,0),r,line.fromType,1);
        String s = line.toComponent.getTag() + ":" + line.size;
        g.setFont(new Font("宋体",0,11));
        int sw = g.getFontMetrics().stringWidth(s);
        int sh = g.getFontMetrics().getHeight();
        switch(line.toType)
        {
        case 5://top
            if(line.fromType == 1)
            {
                TDrawTool.drawLine(x0, 4, r.getX() + r.getWidth() / 2, r.getY(),4,color, g);
                g.drawString(line.toComponent.getTag() + ":" + line.size,r.getX() + r.getWidth() / 2, r.getY() - sh);
            }
            if(line.fromType == 2)
            {
                TDrawTool.drawLine(x0, 4, r.getX() + r.getWidth() / 2, r.getY() + r.getHeight(),6,color, g);
                g.drawString(s,r.getX() + r.getWidth() / 2 - sw / 2, r.getY() + r.getHeight() + sh + 8);
            }
            TDrawTool.drawJ(x0, 5,2,g);
            break;
        case 6://bottom
            if(line.fromType == 2)
            {
                TDrawTool.drawLine(x0, height - 4, r.getX() + r.getWidth() / 2,r.getY() + r.getHeight(), 4, color, g);
                g.drawString(s,r.getX() + r.getWidth() / 2 - sw / 2, r.getY() + r.getHeight() + sh + 8);
            }
            if(line.fromType == 1)
            {
                TDrawTool.drawLine(x0, height - 4, r.getX() + r.getWidth() / 2,r.getY(), 5,color, g);
                g.drawString(s,r.getX() + r.getWidth() / 2 - sw / 2, r.getY() - sh);
            }
            TDrawTool.drawJ(x0, height - 6,1,g);
            break;
        case 7://left
            if(line.fromType == 3)
            {
                TDrawTool.drawLine(4, y0, r.getX(),r.getY() + r.getHeight() / 2, 3, color, g);
                g.drawString(s,r.getX() - sw - 2, r.getY() + r.getHeight() / 2 + sh);
            }
            if(line.fromType == 4)
            {
                TDrawTool.drawLine(4, y0, r.getX() + r.getWidth(),r.getY() + r.getHeight() / 2, 8, color, g);
                g.drawString(s,r.getX() + r.getWidth() + 2, r.getY() + r.getHeight() / 2 + sh);
            }
            TDrawTool.drawJ(5, y0,4,g);
            break;
        case 8://right
            if(line.fromType == 4)
            {
                TDrawTool.drawLine(width - 4, y0, r.getX() + r.getWidth(), r.getY() + r.getHeight() / 2, 3,color, g);
                g.drawString(s,r.getX() + r.getWidth() + 2, r.getY() + r.getHeight() / 2 + sh);
            }
            if(line.fromType == 3)
            {
                TDrawTool.drawLine(width - 4, y0, r.getX(), r.getY() + r.getHeight() / 2, 7,color, g);
                g.drawString(s,r.getX() - sw - 2, r.getY() + r.getHeight() / 2 + sh);
            }
            TDrawTool.drawJ(width - 6,y0,3,g);
            break;
        }
    }

    /**
     * 绘制边框
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        if(getBorder() == null || getBorder().length() == 0)
            return;
        DBorder.paintBorder(this,getBorder(),g);
    }
    /**
     * 绘制字体
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintText(Graphics g,int width,int height)
    {
        String color = getColor();
        String text = getText();
        String font = getFont();
        if(color == null || color.length() == 0 || text == null || text.length() == 0)
            return;
        TDrawTool.paintText(text,DFont.getFont(font),
                            DColor.getColor(color),
                            width,height,
                            DAlignment.getHorizontalAlignment(getHorizontalAlignment()),
                            DAlignment.getVerticalAlignment(getVerticalAlignment()),g);
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeBoolean(1,isVisible(),true);
        s.writeBoolean(2,isEnabled(),true);
        s.writeString(3,getName(),"");
        s.writeString(4,getTag(),"");
        s.writeShort(5);
        getBounds().writeObject(s);
        s.writeShort(6);
        getInsets().writeObject(s);
        s.writeString(7,getBKColor(),null);
        s.writeString(8,getColor(),"黑");
        s.writeString(9,getBorder(),null);
        s.writeString(10,getFont(),null);
        s.writeInt(11,getCursor(),0);
        s.writeString(12,getText(),null);
        s.writeString(13,getHorizontalAlignment(),null);
        s.writeString(14,getVerticalAlignment(),null);
        //保存连接线
        writeObjectAttributeLinkLine(15,s);
    }
    /**
     * 保存连接线
     * @param id int
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttributeLinkLine(int id,DataOutputStream s)
            throws IOException
    {
        s.writeShort(id);
        for(int i = 1;i <= 4;i++)
        {
            DLinkLine line = getLinkComponent(i);
            if(line == null)
            {
                s.writeBoolean(false);
                continue;
            }
            s.writeBoolean(true);
            s.writeInt(line.toComponent == getParent()?-100:getDComponentIndex(line.toComponent));
            s.writeByte(line.toType);
            s.writeInt(line.size);
        }
    }
    /**
     * 读取连接线
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObjectAttributeLinkLine(DataInputStream s)
            throws IOException
    {
        for(int i = 1;i <= 4;i++)
        {
            if(!s.readBoolean())
                continue;
            DLinkLine line = new DLinkLine();
            line.fromComponent = this;
            line.fromType = i;
            line.toComponentIndex = s.readInt();
            line.toType = s.readByte();
            line.size = s.readInt();
            initLinkComponent(i,line);
        }
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        writeObjectAttribute(s);
        s.writeShort(0);
        TList list = getDComponentList();
        if(list == null)
        {
            s.writeInt(0);
            return;
        }
        s.writeInt(list.size());
        for(int i = 0;i < list.size();i++)
        {
            DComponent c = (DComponent) list.get(i);
            if(c.isNoUIEdit())
            {
                s.writeObject(null);
                continue;
            }
            s.writeObject(c);
        }
    }
    /**
     * 读对象属性
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        switch(id)
        {
        case 1:
            setVisible(s.readBoolean());
            return true;
        case 2:
            setEnabled(s.readBoolean());
            return true;
        case 3:
            setName(s.readString());
            return true;
        case 4:
            setTag(s.readString());
            return true;
        case 5:
            setBounds(new DRectangle(s));
            return true;
        case 6:
            setInsets(new DInsets(s));
            return true;
        case 7:
            setBKColor(s.readString());
            return true;
        case 8:
            setColor(s.readString());
            return true;
        case 9:
            setBorder(s.readString());
            return true;
        case 10:
            setFont(s.readString());
            return true;
        case 11:
            setCursor(s.readInt());
            return true;
        case 12:
            setText(s.readString());
            return true;
        case 13:
            setHorizontalAlignment(s.readString());
            return true;
        case 14:
            setVerticalAlignment(s.readString());
            return true;
        case 15:
            readObjectAttributeLinkLine(s);
            return true;
        }
        return false;
    }
    /**
     * 得到属性编码最大值
     * @return int
     */
    public int getAttributeIDMax()
    {
        return 100;
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int index = 0;
        while((index = s.readShort()) > 0)
            readObjectAttribute(index,s);

        int size = s.readInt();
        if(size == -1)
            return;
        for(int i = 0;i < size;i++)
            addDComponent((DComponent)s.readObject());
        //初始化连接
        TList list = getDComponentList();
        if(list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                DComponent com = (DComponent) list.get(i);
                if (com == null)
                    continue;
                com.initLinkComponent();
            }
        }
    }
    /**
     * 初始化系统UI控制器
     */
    public void initSystemUIManager()
    {
        try {
            if(!"Windows".equals(UIManager.getLookAndFeel().getName()))
                UIManager.setLookAndFeel(
                        "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e) {
        }
    }
    /**
     * 加载对象
     * @param className String
     * @return Object
     */
    public Object loadClass(String className)
    {
        try{
            Class c = getClass().getClassLoader().loadClass(className);
            return c.newInstance();
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 设置接口控制类
     * @param IOControl String
     */
    public void setIOControl(String IOControl)
    {
        if(IOControl == null || IOControl.length() == 0)
        {
            attribute.remove("D_IOControl");
            return;
        }
        attribute.put("D_IOControl",IOControl);
    }
    /**
     * 得到接口控制类
     * @return String
     */
    public String getIOControl()
    {
        return (String)attribute.get("D_IOControl");
    }
    /**
     * 设置接口控制类对象
     * @param componentIOControl DComponentControl
     */
    public void setIOControlObject(DComponentControl componentIOControl)
    {
        if(componentIOControl == null)
        {
            attribute.remove("D_IOControleObject");
            return;
        }
        attribute.put("D_IOControleObject",componentIOControl);
    }
    /**
     * 得到接口控制类对象
     * @return DComponentControl
     */
    public DComponentControl getIOControlObject()
    {
        return (DComponentControl)attribute.get("D_IOControleObject");
    }
    /**
     * 设置顶级对象
     * @param topComponent boolean
     */
    public void setTopComponent(boolean topComponent)
    {
        if(!topComponent)
        {
            attribute.remove("D_topComponent");
            return;
        }
        attribute.put("D_topComponent",topComponent);
    }
    /**
     * 是否是顶级对象
     * @return boolean
     */
    public boolean isTopComponent()
    {
        return TypeTool.getBoolean(attribute.get("D_topComponent"));
    }
    /**
     * 查找对象
     * @param tag String
     * @return DComponent
     */
    public DComponent findComponent(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        if(tag.equalsIgnoreCase(getTag()))
            return this;
        //是顶级对象开始向下查找
        if(isTopComponent())
            return findChildComponent(tag);
        DComponent comParent = getParent();
        if(comParent != null)
            return comParent.findComponent(tag);
        return findChildComponent(tag);
    }
    /**
     * 查找子对象
     * @param tag String
     * @return DComponent
     */
    public DComponent findChildComponent(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        TList list = getDComponentList();
        if(list == null)
            return null;
        for(int i = 0;i < list.size();i++)
        {
            DComponent component = (DComponent)list.get(i);
            if(tag.equalsIgnoreCase(component.getTag()))
                return component;
            component = component.findChildComponent(tag);
            if(component != null)
                return component;
        }
        return null;
    }
    /**
     * 初始化接口控制类
     */
    public void initIOControl()
    {
        String controlName = getIOControl();
        if(controlName == null || controlName.length() == 0)
            return;
        Object object = loadClass(controlName);
        if(object == null || !(object instanceof DComponentControl))
            return;
        DComponentControl cControl = (DComponentControl)object;
        setIOControlObject(cControl);
        cControl.setComponent(this);
    }
    /**
     * 增加共享方法
     * @param name String
     * @param object Object
     */
    public void addPublicMethod(String name,Object object)
    {
        if(name == null|| name.length() == 0)
            return;
        if(object == null)
        {
            removePublicMethod(name);
            return;
        }
        Map map = (Map)attribute.get("D_publicMethod");
        if(map == null)
        {
            map = new HashMap();
            attribute.put("D_publicMethod",map);
        }
        map.put(name,object);
    }
    /**
     * 删除共享方法
     * @param name String
     */
    public void removePublicMethod(String name)
    {
        Map map = (Map)attribute.get("D_publicMethod");
        if(map == null)
            return;
        map.remove(name);
        if(map.size() == 0)
            attribute.remove("D_publicMethod");
    }
    /**
     * 得到共享方法的对象
     * @param name String
     * @return Object
     */
    public Object getPublicMethod(String name)
    {
        Map map = (Map)attribute.get("D_publicMethod");
        if(map == null)
            return null;
        return map.get(name);
    }
    /**
     * 查找共享方法
     * @param name String
     * @return Object
     */
    public Object findPublicMethod(String name)
    {
        Object obj = getPublicMethod(name);
        if(obj != null)
            return obj;
        //是顶级对象开始向下查找
        if(isTopComponent())
            return findChildPublicMethod(name);
        DComponent comParent = getParent();
        if(comParent != null)
            return comParent.findPublicMethod(name);
        return findChildPublicMethod(name);
    }
    /**
     * 查找自组件的共享方法
     * @param name String
     * @return Object
     */
    public Object findChildPublicMethod(String name)
    {
        if(name == null || name.length() == 0)
            return null;
        TList list = getDComponentList();
        if(list == null)
            return null;
        for(int i = 0;i < list.size();i++)
        {
            DComponent component = (DComponent)list.get(i);
            Object obj = component.getPublicMethod(name);
            if(obj != null)
                return obj;
            obj = component.findChildPublicMethod(name);
            if(obj != null)
                return component;
        }
        return null;
    }
    /**
     * 呼叫方法
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters)
    {
        return invokePublicMethod(message,parameters);
    }
    /**
     * 执行共享方法
     * @param name String
     * @param args Object
     * @return Object
     */
    public Object invokePublicMethod(String name, Object args)
    {
        //处理方法
        String value[] = StringTool.getHead(name, "|");

        Object obj = findPublicMethod(value[0]);
        if(obj == null)
            return null;
        return RunClass.invokeMethodT(obj, value, args);
    }
    /**
     * 设置动作地图
     * @param name String
     * @param value String
     */
    public void setActionMap(String name,String value)
    {
        if(name == null || name.length() == 0)
            return;
        if(value == null || value.length() == 0)
        {
            removeActionMap(name);
            return;
        }
        Map map = (Map)attribute.get("D_actionMap");
        if(map == null)
        {
            map = new HashMap();
            attribute.put("D_actionMap",map);
        }
        map.put(name,value);
    }
    /**
     * 删除动作地图
     * @param name String
     */
    public void removeActionMap(String name)
    {
        if(name == null || name.length() == 0)
            return;
        Map map = (Map)attribute.get("D_actionMap");
        if(map == null)
            return;
        map.remove(name);
        if(map.size() == 0)
            attribute.remove("D_actionMap");
    }
    /**
     * 得到动作地图
     * @param name String
     * @return String
     */
    public String getActionMap(String name)
    {
        if(name == null || name.length() == 0)
            return null;
        Map map = (Map)attribute.get("D_actionMap");
        if(map == null)
            return null;
        return (String)map.get(name);
    }
    /**
     * 执行动作地图
     * @param name String
     */
    public void runActionMap(String name)
    {
        if(name == null || name.length() == 0)
            return;
        String message = getActionMap(name);
        if(message == null || message.length() == 0)
            return;
        String action[] = StringTool.parseLine(message,";");
        for(int i = 0;i < action.length;i++)
            callFunction(action[i]);
    }
    /**
     * 设置连接线
     * @param name int
     * @param line DLinkLine
     */
    public void addLinkLine(int name,DLinkLine line)
    {
        if(line == null)
            return;
        Map map = (Map)attribute.get("D_linkLine");
        if(map == null)
        {
            map = new HashMap();
            attribute.put("D_linkLine",map);
        }
        TList list = (TList)map.get(name);
        if(list == null)
        {
            list = new TList();
            map.put(name,list);
        }
        list.put(line);
    }
    /**
     * 得到连接线集合
     * @param name int
     * @return TList
     */
    public TList getLinkLine(int name)
    {
        Map map = (Map)attribute.get("D_linkLine");
        if(map == null)
            return null;
        return (TList)map.get(name);
    }
    /**
     * 删除连接线
     * @param name int
     * @param line DLinkLine
     */
    public void removeLinkLine(int name,DLinkLine line)
    {
        Map map = (Map)attribute.get("D_linkLine");
        if(map == null)
            return;
        TList list = (TList)map.get(name);
        if(list == null)
            return;
        list.remove(line);
        if(list.size() == 0)
            map.remove(name);
        if(map.size() == 0)
            attribute.remove("D_linkLine");
    }
    /**
     * 设置连接对象
     * @param name int
     * @param com DComponent
     * @param type int
     */
    public void setLinkComponent(int name,DComponent com,int type)
    {
        setLinkComponent(name,com,type,0);
    }
    /**
     * 设置连接对象
     * @param name String
     * @param com DComponent
     * @param type String
     * @param size int
     */
    public void setLinkComponent(int name,DComponent com,int type,int size)
    {
        if(getParent() == null)
            return;
        if(com == null)
            return;
        Map map = (Map)attribute.get("D_linkComponent");
        if(map == null)
        {
            map = new HashMap();
            attribute.put("D_linkComponent",map);
        }
        DLinkLine link = (DLinkLine)map.get(name);
        if(link == null)
        {
            link = new DLinkLine();
            map.put(name,link);
        }
        link.fromComponent = this;
        link.toComponent = com;
        link.fromType = name;
        link.toType = type;
        link.size = size;
        //增加连接
        if(getParent() != null)
            getParent().addLinkLine(type,link);
    }
    /**
     * 初始化连接对象
     * @param name int
     * @param line DLinkLine
     */
    public void initLinkComponent(int name,DLinkLine line)
    {
        if(line == null)
            return;
        Map map = (Map)attribute.get("D_linkComponent");
        if(map == null)
        {
            map = new HashMap();
            attribute.put("D_linkComponent",map);
        }
        map.put(name,line);
    }
    /**
     * 初始化连接对象
     */
    public void initLinkComponent()
    {
        if(getParent() == null)
            return;
        Map map = (Map)attribute.get("D_linkComponent");
        if(map == null)
        {
            map = new HashMap();
            attribute.put("D_linkComponent",map);
        }
        for(int i = 1;i <= 4;i++)
        {
            DLinkLine link = (DLinkLine)map.get(i);
            if(link == null)
                continue;
            if(link.toComponent != null)
                continue;
            link.toComponent = link.toComponentIndex == -100?getParent():getParent().getDComponent(link.toComponentIndex);
            getParent().addLinkLine(link.toType,link);
        }
    }
    /**
     * 删除连接对象
     */
    public void removeLinkComponent()
    {
        for(int i = 1;i <= 4;i++)
            removeLinkComponent(i);
    }
    /**
     * 删除连接对象
     * @param name int
     */
    public void removeLinkComponent(int name)
    {
        Map map = (Map)attribute.get("D_linkComponent");
        if(map == null)
            return;
        DLinkLine link = (DLinkLine)map.remove(name);
        if(map.size() == 0)
            attribute.remove("D_linkComponent");
        //删除连接
        if(link != null)
            link.toComponent.removeLinkLine(link.toType,link);
    }
    /**
     * 得到连接对象
     * @param name int
     * @return TList
     */
    public DLinkLine getLinkComponent(int name)
    {
        Map map = (Map)attribute.get("D_linkComponent");
        if(map == null)
            return null;
        return (DLinkLine)map.get(name);
    }
    /**
     * 设置移动的连接线
     * @param line DLinkLine
     */
    public void setMoveLinkLine(DLinkLine line)
    {
        if(line == null)
        {
            attribute.remove("D_moveLinkLine");
            return;
        }
        attribute.put("D_moveLinkLine",line);
    }
    /**
     * 得到移动的连接线
     * @return DLinkLine
     */
    public DLinkLine getMoveLinkLine()
    {
        return (DLinkLine)attribute.get("D_moveLinkLine");
    }
    /**
     * 能够得到焦点
     * @return boolean
     */
    public boolean canFocus()
    {
        return false;
    }
    /**
     * 得到默认焦点
     * @return DComponent
     */
    public DComponent getDefaultFocus()
    {
        if(!isVisible() || !isEnabled() || !canFocus())
            return null;
        TList components = getDComponentList();
        if(components == null)
            return this;
        for(int i = 0;i < components.size();i++)
        {
            DComponent com = (DComponent)components.get(i);
            if(com == null)
                continue;
            if(!com.isVisible() || !com.isEnabled() || !com.canFocus())
                continue;
            DComponent focusCom = com.getDefaultFocus();
            if(focusCom != null)
                return focusCom;
        }
        return this;
    }
    /**
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DComponent parent = getParent();
        if(parent != null)
        {
            DPoint point = parent.getScreenPoint();
            DInsets insets = parent.getInsets();
            DRectangle rectangle = getComponentBounds();
            point.x += insets.left + rectangle.getX();
            point.y += insets.top + rectangle.getY();
            return point;
        }
        TCBase base = getParentTCBase();
        if(base != null)
        {
            DPoint point = base.getScreenPoint();
            Insets insets = base.getInsets();
            DRectangle rectangle = getComponentBounds();
            point.x += insets.left + rectangle.getX();
            point.y += insets.top + rectangle.getY();
            return point;
        }
        return new DPoint(0,0);
    }
    /**
     * 窗口移动事件
     */
    public void onWindowMoved()
    {
        TList list = getDComponentList();
        if(list == null)
            return;
        for(int i = 0;i < list.size();i++)
        {
            DComponent com = (DComponent)list.get(0);
            if(com == null)
                continue;
            com.onWindowMoved();
        }
    }
    /**
     * 让当前组件得到焦点
     */
    public void grabFocus()
    {
        if(!isVisible() || !isEnabled() || !canFocus())
            return;
        setFocus(this);
    }
    /**
     * 设置焦点
     * @param component DComponent
     */
    public void setFocus(DComponent component)
    {
        TCBase base = getTCBase();
        if(base != null)
        {
            base.setFocus(component);
            return;
        }
    }
    /**
     * 得到焦点组件
     * @return DComponent
     */
    public DComponent getFocus()
    {
        TCBase base = getTCBase();
        if(base != null)
            return base.getFocus();
        return null;
    }
    /**
     * 得到最大显示宽度
     * @return int
     */
    public int getMaxViewWidth()
    {
        return getComponentBounds().getWidth();
    }
    /**
     * 得到最大显示高度
     * @return int
     */
    public int getMaxViewHeight()
    {
        return getComponentBounds().getHeight();
    }
    /**
     * 得到配置参数对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        TCBase base = getTCBase();
        if(base == null)
            return null;
        return base.getConfigParm();
    }
    /**
     * 打开窗口
     * @param config String 配置文件名
     * @return TComponent
     */
    public TComponent openWindow(String config)
    {
        return openWindow(config,null);
    }
    /**
     * 打开窗口
     * @param config String
     * @param parameter Object
     * @param isTop boolean true 浮动窗口 false 普通窗口
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter,boolean isTop)
    {
        if(isTop)
            return TWindow.openWindow(getConfigParm().newConfig(config),getTCBase(),parameter);
        return TFrame.openWindow(getConfigParm().newConfig(config),parameter);
    }
    /**
     * 打开窗口
     * @param config String 配置文件名
     * @param parameter Object 参数
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter)
    {
        return openWindow(config,parameter,false);
    }
    /**
     * 打开消息窗口
     * @param config String 配置文件名
     * @return Object 返回值
     */
    public Object openDialog(String config)
    {
        return openDialog(config,null);
    }
    /**
     * 打开消息窗口
     * @param config String 配置文件名
     * @param parameter Object 参数
     * @return Object 返回值
     */
    public Object openDialog(String config, Object parameter)
    {
        return openDialog(config,parameter,true);
    }
    /**
     * 打开消息窗口
     * @param config String 配置文件名
     * @param parameter Object 参数
     * @param flg boolean true 动态加载 false 静态加载
     * @return Object
     */
    public Object openDialog(String config, Object parameter,boolean flg)
    {
        return TDialog.openWindow(getConfigParm().newConfig(config,flg),parameter);
    }
    /**
     * 弹出对话框提示消息
     * @param message Object
     */
    public void messageBox(Object message){
        JOptionPane.showMessageDialog(getTCBase(),TypeTool.getString(message));
    }
    /**
     * 提示消息窗口
     * @param title String 标题
     * @param message Object 信息
     * @param optionType int 按钮类型
     * @return int
     */
    public int messageBox(String title,Object message,int optionType)
    {
        return JOptionPane.showConfirmDialog(getTCBase(),TypeTool.getString(message),title,optionType);
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DComponent>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
    }
}
