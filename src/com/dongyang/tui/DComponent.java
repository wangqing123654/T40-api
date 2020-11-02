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
 * <p>Title: �ؼ�������</p>
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
     * �����Ʊ߿�ĳߴ�
     */
    static final int MOUSE_BORDER_SIZE = 3;
    /**
     * �����Ʊ߿򶥽���չ�ĳߴ�
     */
    static final int MOUSE_BORDER_LENGTH = 10;
    /**
     * ��������ʼ��ߴ�
     */
    static final int LINK_LINE_SIZE = 5;
    /**
     * ����
     */
    Map attribute = new HashMap();
    /**
     * ������
     */
    public DComponent()
    {
        setVisible(true);
        setEnabled(true);
        //��ʼ���ߴ�
        setBounds(new DRectangle());
        //��ʼ���ڲ��ߴ�
        setInsets(new DInsets());
        setColor("��");
        //����Ĭ�ϱ߿�ߴ�
        setDefaultInsets();
    }
    /**
     * ����Ĭ�ϱ߿�ߴ�
     */
    public void setDefaultInsets()
    {
    }

    /**
     * ��������
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
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return (String)attribute.get("D_name");
    }
    /**
     * ���ñ�ǩ
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
     * �õ���ǩ
     * @return String
     */
    public String getTag()
    {
        return (String)attribute.get("D_tag");
    }
    /**
     * ���óߴ�
     * @param rectangle DRectangle
     */
    public void setBounds(DRectangle rectangle)
    {
        attribute.put("D_bound",rectangle);
    }
    /**
     * ���óߴ�
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
     * �õ��ߴ�
     * @return DRectangle
     */
    public DRectangle getBounds()
    {
        return (DRectangle)attribute.get("D_bound");
    }
    /**
     * �����Ƿ�ͬ��TCBase�ߴ�
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
     * �Ƿ�ͬ��TCBase�ߴ�
     * @return boolean
     */
    public boolean isAutoBaseSize()
    {
        return TypeTool.getBoolean(attribute.get("D_autoBaseSize"));
    }
    /**
     * �õ�����ߴ�
     * @return DRectangle
     */
    public DRectangle getComponentBounds()
    {
        if(isUIEditStatus() && !isNoUIEdit())
            return getBounds();
        //ͬ��TCBase�ߴ�
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
     * �õ����ӵ�����
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
     * ����λ��
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
        //λ�ñ仯
        onComponentMoved();
    }
    /**
     * ���ú�����
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
        //λ�ñ仯
        onComponentMoved();
    }
    /**
     * ���ú�����
     * @return int
     */
    public int getX()
    {
        return getBounds().getX();
    }
    /**
     * ����������
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
        //λ�ñ仯
        onComponentMoved();
    }
    /**
     * �õ�������
     * @return int
     */
    public int getY()
    {
        return getBounds().getY();
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        DRectangle r = getBounds();
        repaintBlank(getComponentBounds());
        r.setWidth(width);
        repaintBlank(getComponentBounds());
        //�ߴ�仯
        onComponentResized();
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return getBounds().getWidth();
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        DRectangle r = getBounds();
        repaintBlank(getComponentBounds());
        r.setHeight(height);
        repaintBlank(getComponentBounds());
        //�ߴ�仯
        onComponentResized();
    }
    /**
     * �õ����
     * @return int
     */
    public int getHeight()
    {
        return getBounds().getHeight();
    }
    /**
     * �õ��ؼ���ʾ���
     * @return int
     */
    public int getWidthV()
    {
        return getComponentBounds().getWidth();
    }
    /**
     * �õ��ؼ���ʾ�߶�
     * @return int
     */
    public int getHeightV()
    {
        return getComponentBounds().getHeight();
    }
    /**
     * ���óߴ�
     * @param size DSize
     */
    public void setSize(DSize size)
    {
        if(size == null)
            return;
        setSize(size.getWidth(),size.getHeight());
    }
    /**
     * ���óߴ�
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
        //�ߴ�仯
        onComponentResized();
    }
    /**
     * �����ܷ�����ߴ�
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
     * �ܷ�����ߴ�
     * @return boolean
     */
    public boolean canResetSize()
    {
        return !TypeTool.getBoolean(attribute.get("D_canResetSize"));
    }
    /**
     * �����ߴ�
     */
    public void resetSize()
    {

    }
    /**
     * �õ��ڲ����
     * @return int
     */
    public int getInsetWidth()
    {
        DRectangle r = getComponentBounds();
        DInsets i = getInsets();
        return r.getWidth() - i.left - i.right;
    }
    /**
     * �õ��ڲ��߶�
     * @return int
     */
    public int getInsetHeight()
    {
        DRectangle r = getComponentBounds();
        DInsets i = getInsets();
        return r.getHeight() - i.top - i.bottom;
    }
    /**
     * �õ��ڲ��ߴ�
     * @return DSize
     */
    public DSize getInsetSize()
    {
        DRectangle r = getComponentBounds();
        DInsets i = getInsets();
        return new DSize(r.getWidth() - i.left - i.right,r.getHeight() - i.top - i.bottom);
    }
    /**
     * �õ������ڲ����
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
     * �õ������ڲ��߶�
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
     * �õ��ڲ�X
     * @return int
     */
    public int getInsetX()
    {
        return getInsets().left;
    }
    /**
     * �õ��ڲ�Y
     * @return int
     */
    public int getInsetY()
    {
        return getInsets().top;
    }

    /**
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBKColor(String color)
    {
        attribute.put("D_bkColor",color);
    }
    /**
     * ���ñ�����ɫ
     * @param r int
     * @param g int
     * @param b int
     */
    public void setBKColor(int r,int g,int b)
    {
        setBKColor(r + "," + g + "," + b);
    }
    /**
     * ���ñ�����ɫ
     * @param color Color
     */
    public void setBKColor(Color color)
    {
        setBKColor(color.getRed(),color.getGreen(),color.getBlue());
    }
    /**
     * �õ�������ɫ
     * @return String
     */
    public String getBKColor()
    {
        return (String)attribute.get("D_bkColor");
    }
    /**
     * ����ǰ����ɫ
     * @param color String
     */
    public void setColor(String color)
    {
        attribute.put("D_color",color);
    }
    /**
     * ����ǰ����ɫ
     * @param r int
     * @param g int
     * @param b int
     */
    public void setColor(int r,int g,int b)
    {
        setColor(r + "," + g + "," + b);
    }
    /**
     * ����ǰ����ɫ
     * @param color Color
     */
    public void setColor(Color color)
    {
        setColor(color.getRed(),color.getGreen(),color.getBlue());
    }
    /**
     * �õ�ǰ����ɫ
     * @return String
     */
    public String getColor()
    {
        return (String)attribute.get("D_color");
    }
    /**
     * ���ñ߿�
     * @param border String
     */
    public void setBorder(String border)
    {
        attribute.put("D_border",border);
        //�����ڲ��ߴ�
        DBorder.setInsets(this,border);
    }
    /**
     * �õ��߿�
     * @return String
     */
    public String getBorder()
    {
        return (String)attribute.get("D_border");
    }
    /**
     * �����ڲ��ߴ�
     * @param insets DInsets
     */
    public void setInsets(DInsets insets)
    {
        attribute.put("D_insets",insets);
    }
    /**
     * �����ڲ��ߴ�
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
     * �õ��ڲ��ߴ�
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return (DInsets)attribute.get("D_insets");
    }
    /**
     * ��������
     * @param font String
     */
    public void setFont(String font)
    {
        attribute.put("D_font",font);
    }
    /**
     * �õ�����
     * @return String
     */
    public String getFont()
    {
        return (String)attribute.get("D_font");
    }
    /**
     * �����Ƿ���ʾ
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
     * �Ƿ���ʵ
     * @return boolean
     */
    public boolean isVisible()
    {
        return TypeTool.getBoolean(attribute.get("D_visible"));
    }
    /**
     * ������Ч
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
     * �Ƿ���Ч
     * @return boolean
     */
    public boolean isEnabled()
    {
        return TypeTool.getBoolean(attribute.get("D_enabled"));
    }
    /**
     * ���ø���
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
     * �õ�����
     * @return DComponent
     */
    public DComponent getParent()
    {
        return (DComponent)attribute.get("D_parent");
    }
    /**
     * ���ø���Java���
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
     * �õ�����Java���
     * @return TCBase
     */
    public TCBase getParentTCBase()
    {
        return (TCBase)attribute.get("D_parentTCBase");
    }
    /**
     * �õ�TCBase
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
     * �������Xλ��
     * @param x int
     */
    public void setMouseX(int x)
    {
        attribute.put("D_mouseX",x);
    }
    /**
     * �õ����Xλ��
     * @return int
     */
    public int getMouseX()
    {
        return TypeTool.getInt(attribute.get("D_mouseX"));
    }
    /**
     * �������Yλ��
     * @param y int
     */
    public void setMouseY(int y)
    {
        attribute.put("D_mouseY",y);
    }
    /**
     * �õ����Yλ��
     * @return int
     */
    public int getMouseY()
    {
        return TypeTool.getInt(attribute.get("D_mouseY"));
    }
    /**
     * �������λ��
     * @param x int
     * @param y int
     */
    public void setMousePoint(int x,int y)
    {
        setMouseX(x);
        setMouseY(y);
    }
    /**
     * ���������
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
     * �õ������
     * @return DPoint
     */
    public DPoint getMousePoint()
    {
        return new DPoint(getMouseX(),getMouseY());
    }
    /**
     * ������갴��Xλ��
     * @param x int
     */
    public void setMouseDownX(int x)
    {
        attribute.put("D_mouseDownX",x);
    }
    /**
     * �õ���갴��Xλ��
     * @return int
     */
    public int getMouseDownX()
    {
        return TypeTool.getInt(attribute.get("D_mouseDownX"));
    }
    /**
     * ������갴��Yλ��
     * @param y int
     */
    public void setMouseDownY(int y)
    {
        attribute.put("D_mouseDownY",y);
    }
    /**
     * �õ���갴��Yλ��
     * @return int
     */
    public int getMouseDownY()
    {
        return TypeTool.getInt(attribute.get("D_mouseDownY"));
    }
    /**
     * ������갴��λ��
     * @param x int
     * @param y int
     */
    public void setMouseDownPoint(int x,int y)
    {
        setMouseDownX(x);
        setMouseDownY(y);
    }
    /**
     * ���ð��������
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
     * �õ������
     * @return DPoint
     */
    public DPoint getMouseDownPoint()
    {
        return new DPoint(getMouseDownX(),getMouseDownY());
    }
    /**
     * ������껬���ƶ��ߴ�
     * @param i int
     */
    public void setMouseWheelMoved(int i)
    {
        attribute.put("D_mouseWheelMoved",i);
    }
    /**
     * �õ���껬���ƶ��ߴ�
     * @return int
     */
    public int getMouseWheelMoved()
    {
        return TypeTool.getInt(attribute.get("D_mouseWheelMoved"));
    }
    /**
     * �����������ؼ�
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
     * �õ��������ؼ�
     * @return DComponent
     */
    public DComponent getMouseDraggedInComponent()
    {
        return (DComponent)attribute.get("D_mouseDraggedInComponent");
    }
    /**
     * ����������뵽�ؼ�
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
     * �õ�������뵽�ؼ�
     * @return DComponent
     */
    public DComponent getMouseDraggedGotoComponent()
    {
        return (DComponent)attribute.get("D_mouseDraggedGotoComponent");
    }
    /**
     * �����ƶ�����
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
     * �õ��ƶ�����
     * @return int
     */
    public int getMouseMoveType()
    {
        return TypeTool.getInt(attribute.get("D_mouseMoveType"));
    }
    /**
     * ���ü����¼�
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
     * �õ������¼�
     * @return KeyEvent
     */
    public KeyEvent getKeyEvent()
    {
        return (KeyEvent)attribute.get("D_keyEvent");
    }
    /**
     * �õ�����б�
     * @return TList
     */
    public TList getDComponentList()
    {
        return (TList)attribute.get("D_components");
    }
    /**
     * ��������б�
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
     * �õ������λ��
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
     * �������
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
     * ɾ�����
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
        //ɾ������������
        if(getMouseInComponent() == component)
            setMouseInComponent(null);
    }
    /**
     * ɾ�����
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
     * �õ����
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
     * �����Ӷ���
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
     * �õ��������
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
     * ����������ڿؼ�
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
     * �õ�������ڿؼ�
     * @return DComponent
     */
    public DComponent getMouseInComponent()
    {
        return (DComponent)attribute.get("D_mouseInComponent");
    }
    /**
     * ������갴�¿ؼ�
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
     * �õ���갴�¿ؼ�
     * @return DComponent
     */
    public DComponent getMousePressedComponent()
    {
        return (DComponent)attribute.get("D_mousePressedComponent");
    }
    /**
     * �����������������
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
     * �õ��������������
     * @return boolean
     */
    public boolean isMouseLeftKeyDownComponent()
    {
        return TypeTool.getBoolean(attribute.get("D_mouseLeftKeyDownComponent"));
    }
    /**
     * ����������±��
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
     * �õ�������±��
     * @return boolean
     */
    public boolean isMouseLeftKeyDown()
    {
        return TypeTool.getBoolean(attribute.get("D_mouseLeftKeyDown"));
    }
    /**
     * �������߿��϶�����
     * @param type int
     *  11111111(������)
     *  ��,��,��,��,����,����,����,����
     *  ʹ��Ϊ������255��ʾȫ������
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
     * �õ����߿��϶�����
     * @return int
     *  11111111(������)
     *  ��,��,��,��,����,����,����,����
     *  ʹ��Ϊ������255��ʾȫ������
     */
    public int getMouseBorderDraggedType()
    {
        return TypeTool.getInt(attribute.get("D_mouseBorderDraggedType"));
    }
    /**
     * �������߿��϶�״̬
     * @param type int
     * 0 ֹͣ
     * 1 ��
     * 2 ��
     * 3 ��
     * 4 ��
     * 5 ����
     * 6 ����
     * 7 ����
     * 8 ����
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
     * �õ����߿��϶�״̬
     * @return int
     * 0 ֹͣ
     * 1 ��
     * 2 ��
     * 3 ��
     * 4 ��
     * 5 ����
     * 6 ����
     * 7 ����
     * 8 ����
     */
    public int getMouseBorderDraggedState()
    {
        return TypeTool.getInt(attribute.get("D_mouseBorderDraggedState"));
    }
    /**
     * ���ù��
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
     * �õ����
     * @return int
     */
    public int getCursor()
    {
        return TypeTool.getInt(attribute.get("D_cursor"));
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        attribute.put("D_text",text);
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        return (String)attribute.get("D_text");
    }
    /**
     * �����ַ�����λ��
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
     * �õ��ַ�����λ��
     * @return String
     */
    public String getHorizontalAlignment()
    {
        return (String)attribute.get("D_horizontalAlignment");
    }
    /**
     * �����ַ�����λ��
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
     * �õ��ַ�������λ��
     * @return String
     */
    public String getVerticalAlignment()
    {
        return (String)attribute.get("D_verticalAlignment");
    }
    /**
     * ��������Ƿ����
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
     * �õ�����Ƿ����
     * @return boolean
     */
    public boolean isMouseEntered()
    {
        return TypeTool.getBoolean(attribute.get("D_mouseEntered"));
    }
    /**
     * �����������
     * @param type int
     * 1 ��
     * 2 ��
     * 3 ��
     * 4 ��
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
     * �õ��������
     * @return int
     */
    public int getMouseDraggedIn()
    {
        return TypeTool.getInt(attribute.get("D_mouseDraggedIn"));
    }
    /**
     * ����UI�༭״̬
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
     * �õ�UI�༭״̬
     * @return boolean
     */
    public boolean isUIEditStatus()
    {
        return TypeTool.getBoolean(attribute.get("D_UIEditStatus"));
    }
    /**
     * ���ý�ֹ�༭
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
     * �Ƿ��ֹ�༭
     * @return boolean
     */
    public boolean isNoUIEdit()
    {
        return TypeTool.getBoolean(attribute.get("D_noUIEdit"));
    }
    /**
     * ����UI�༭ѡ��
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
     * �õ�UI�༭ѡ��
     * @return boolean
     */
    public boolean isUIEditSelected()
    {
        return TypeTool.getBoolean(attribute.get("D_UIEditSelecgted"));
    }
    //====================================================//
    // �¼�����
    //====================================================//
    /**
     * �õ�ctrl���Ƿ���
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
     * �õ�shift���Ƿ���
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
     * �����������λ��
     * @param com DComponent
     */
    public void resetMousePoint(DComponent com)
    {
        if(com == null)
            return;
        DRectangle r = com.getComponentBounds();
        //��������
        DInsets insets = getInsets();
        int x = getMouseX() - insets.left;
        int y = getMouseY() - insets.right;
        com.setMousePoint(x - r.getX(), y - r.getY());
    }
    /**
     * �������λ�����ĸ��ؼ�
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
     * �������λ�����Ǹ��ؼ�
     * @param x int
     * @param y int
     * @return DComponent
     */
    private DComponent findMousePointInComponent(int x,int y)
    {
        return findMousePointInComponent(x,y,false);
    }
    /**
     * �������λ�����Ǹ��ؼ�
     * @param x int
     * @param y int
     * @param b boolean true �������Լ���false �����Լ�
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
            //�����������
            if(!com.isVisible())
                continue;
            if(!com.isEnabled())
                continue;
            //�����������Χ��
            if(com.inMouse(x,y))
                return com;
        }
        return null;
    }
    /**
     * �������λ�����Ǹ��ؼ�(���������)
     * @param x int
     * @param y int
     * @param comSrc DComponent �ų������
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
            //�����������
            if(!com.isVisible())
                continue;
            //�����������Χ��
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
     * �������λ�����ĸ��ؼ�
     * @param comSrc DComponent �ų������
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
     * ��������ڱ��ؼ�����
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
     * ��������ڱ��ؼ�����
     * @return boolean
     */
    public boolean inMouse()
    {
        DRectangle r = getComponentBounds();
        return inMouse(getMouseX() + r.getX(),getMouseY() + r.getY());
    }
    /**
     * ������
     * @return boolean
     */
    public boolean onMouseEntered()
    {
        return false;
    }
    /**
     * ����뿪
     * @return boolean
     */
    public boolean onMouseExited()
    {
        mouseMouseExitedListener();
        setMouseEntered(false);
        return false;
    }
    /**
     * ����Ĭ�Ϲ��
     */
    public void setDefaultCursor()
    {
        updateCursor(getCursor());
    }
    /**
     * ����ƶ�
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        //�߿���
        if(checkMouseBorderDragged())
        {
            setMouseEntered(false);
            return true;
        }
        //�ڲ�������
        if(mouseMovedListener())
        {
            setMouseEntered(false);
            return true;
        }
        //�����������
        if(mouseMovedLinkLine())
        {
            setMouseEntered(false);
            return true;
        }
        setMouseEntered(true);
        return false;
    }
    /**
     * ��������ڱ߿��ϵ�ͼ��
     * @return boolean true ���� false ������
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
     * ���ù��
     * @param cursor int
     */
    void updateCursor(int cursor)
    {
        updateCursor(DCursor.getCursor(cursor));
    }
    /**
     * ���ù��
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
     * �������
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        //�߿���
        if(checkMouseBorderDragged())
        {
            //��¼��갴�µ�
            setMouseDownPoint(getMousePoint());
            setMouseLeftKeyDown(true);
            return true;
        }
        //���������
        if(isEnabled() && mouseLeftPressedListener())
            return true;
        //�����������
        if(mouseLeftPressedLinkLine())
            return true;
        if(!isEnabled())
            return false;
        //��¼��갴�µ�
        setMouseDownPoint(getMousePoint());
        setMouseLeftKeyDown(true);
        return false;
    }
    /**
     * �м�����
     * @return boolean
     */
    public boolean onMouseCenterPressed()
    {
        if(mouseCenterPressedListener())
            return true;
        return false;
    }
    /**
     * �Ҽ�����
     * @return boolean
     */
    public boolean onMouseRightPressed()
    {
        if(mouseRightPressedListener())
            return true;
        return false;
    }
    /**
     * ���̧��
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        setMouseLeftKeyDown(false);
        if(mouseLeftReleasedListener())
            return true;
        //�����������
        if(mouseLeftReleasedLinkLine())
            return true;
        if(!inMouse())
            setMouseEntered(false);
        return false;
    }
    /**
     * �м�̧��
     * @return boolean
     */
    public boolean onMouseCenterReleased()
    {
        if(mouseCenterReleasedListener())
            return true;
        return false;
    }
    /**
     * �Ҽ�̧��
     * @return boolean
     */
    public boolean onMouseRightReleased()
    {
        if(mouseRightReleasedListener())
            return true;
        return false;
    }
    /**
     * ���
     * @return boolean
     */
    public boolean onClickedS()
    {
        if(mouseClickedListener())
            return true;
        return false;
    }
    /**
     * ˫��
     * @return boolean
     */
    public boolean onDoubleClickedS()
    {
        if(mouseDoubleClickedListener())
            return true;
       return false;
    }
    /**
     * ��껬��
     * @return boolean
     */
    public boolean onMouseWheelMoved()
    {
        if(mouseWheelMovedListener())
            return true;
        return false;
    }
    /**
     * ����϶�
     * @return boolean
     */
    public boolean onMouseDragged()
    {
        //�ڲ�����϶�����
        if(mouseDraggedListener())
            return true;
        //���߿��϶�����
        if(mouseBorderDraggedListener())
            return true;
        //�����������
        if(mouseDraggedLinkLine())
            return true;
        //����϶��������
        if(mouseDraggedMoveListener())
            return true;
        setMouseEntered(inMouse());
        return false;
    }
    /**
     * �Ƿ��������ߴ�
     * @return boolean true �ڼ��� false ���ټ���
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
     * ����ߴ�ı�
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
     * �ߴ�ı�
     * @return boolean
     */
    public boolean onComponentResized()
    {
        if(isUIEditStatus() && getParent() != null)
            getParent().repaint();
        return true;
    }
    /**
     * λ�øı�
     * @return boolean
     */
    public boolean onComponentMoved()
    {
        if(isUIEditStatus() && getParent() != null)
            getParent().repaint();
        return true;
    }
    /**
     * ������갴��
     */
    public void onWindowMousePressed()
    {

    }
    /**
     * ���õ�ǰ�Ƿ��ǽ���
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
     * ��ǰ�Ƿ��ǽ���
     * @return boolean
     */
    public boolean isFocus()
    {
        return TypeTool.getBoolean(attribute.get("D_isFocus"));
    }
    /**
     * �õ������¼�
     * @return boolean
     */
    public boolean onFocusGained()
    {
        setIsFocus(true);
        return false;
    }
    /**
     * ʧȥ�����¼�
     * @return boolean
     */
    public boolean onFocusLost()
    {
        setIsFocus(false);
        return false;
    }
    /**
     * ���̰���
     * @return boolean
     */
    public boolean onKeyPressed()
    {
        return false;
    }
    /**
     * ����̧��
     * @return boolean
     */
    public boolean onKeyReleased()
    {
        return false;
    }
    /**
     * ����
     * @return boolean
     */
    public boolean onKeyTyped()
    {
        return false;
    }
    /**
     * ����xλ��
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
     * ���Գߴ��С
     * @param width int
     * @param height int
     * @return boolean
     */
    public boolean checkSize(int width,int height)
    {
        return true;
    }
    /**
     * ����϶��������
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
            //�����϶�Ƕ�����
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
     * �����϶����
     */
    private void checkDraggedComponent()
    {
        //����Ctrl����������
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
        //����������ת��
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
     * ����϶��ϱ߿�
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
     * ����϶��±߿�
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
     * ����϶���߿�
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
     * ����϶��ұ߿�
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
     * ���߿��϶�����
     * @return boolean
     */
    private boolean mouseBorderDraggedListener()
    {
        switch(getMouseBorderDraggedState())
        {
        case 0:
            return false;
        case 1://��
            mouseBorderDragged1();
            return true;
        case 2://��
            mouseBorderDragged2();
            return true;
        case 3://��
            mouseBorderDragged3();
            return true;
        case 4://��
            mouseBorderDragged4();
            return true;
        case 5://����
            mouseBorderDragged3();
            mouseBorderDragged1();
            return true;
        case 6://����
            mouseBorderDragged4();
            mouseBorderDragged1();
            return true;
        case 7://����
            mouseBorderDragged4();
            mouseBorderDragged2();
            return true;
        case 8://����
            mouseBorderDragged3();
            mouseBorderDragged2();
            return true;
        }
        return false;
    }
    /**
     * �������
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
     * �������
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
     * �����ͣ
     */
    public void onMouseDraggedStop()
    {
        if(!isUIEditStatus())
            return;
        System.out.println(getTag() + " onMouseDraggedStop" + getMouseDraggedInComponent());
    }
    /**
     * ����ƶ��������
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
     * ����Ƴ��������
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
     * �ƶ�ʱ�����������
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
     * ������������������
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
     * ̧����������������
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
     * �϶������������
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
        //�����������϶�λ��
        checkDraggedLinkLine(line.fromType);
        repaint();
        return true;
    }
    /**
     * �����������϶�λ��
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
     * ������������ĵ�������
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
            //������������ĵ�������
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
     * ��������������
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
        //��¼��갴�����
        setMousePressedComponent(com);
        setMouseLeftKeyDownComponent(true);
        com.onMouseLeftPressed();
        return true;
    }
    /**
     * ��������������
     */
    public void cancelMouseFollowing()
    {
        setMousePressedComponent(null);
        setMouseLeftKeyDownComponent(false);
        //��������
        DComponent comParent = getParent();
        if(comParent != null)
        {
            comParent.cancelMouseFollowing();
            return;
        }
        //��������
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
        //���û���
        TCBase base = getParentTCBase();
        if(base != null)
        {
            base.setMousePressedComponent(this);
            base.resetMousePoint(this);
        }
    }
    /**
     * ���µ�������������
     * @param com DComponent
     */
    public void resetMouseFollowing(DComponent com)
    {
        if(com == null)
            return;
        setMousePressedComponent(com);
        resetMousePoint(com);
        setMouseLeftKeyDownComponent(true);
        //���û���
        DComponent comParent = getParent();
        if(comParent != null)
        {
            comParent.resetMouseFollowing(this);
            return;
        }
        //���û���
        TCBase base = getParentTCBase();
        if(base != null)
        {
            base.setMousePressedComponent(this);
            base.resetMousePoint(this);
        }
    }
    /**
     * �м������������
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
     * �Ҽ������������
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
     * ���̧���������
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
     * �м�̧���������
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
     * �Ҽ�̧���������
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
     * ����������
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
     * ˫���������
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
     * ���ֹ����������
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
     * �϶��������
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
     * ����Ƿ񱻸���
     * @param component DComponent
     * @param index int ��ʾ��
     * @return boolean true ���� false û�б�����
     */
    public boolean isBestrowCom(DComponent component,int index)
    {
        if(component == null)
            return true;
        TList components = getDComponentList();
        //��������
        if(index == components.size() - 1)
            return false;
        DRectangle tc = component.getComponentBounds();
        //����ߴ�<0
        if(tc.getWidth() <= 0 || tc.getHeight() <= 0)
            return true;
        TList list = new TList();
        list.add(tc);
        //�����ڸ�
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
     * �õ���ǰ�ķ�����
     * @param index int λ��
     * @return String ������
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
     * Ҫ���ػ�
     */
    public void repaint() {
        DRectangle r = getComponentBounds();
        repaint(0, 0, 0, r.getWidth(), r.getHeight());
    }
    /**
     * ��丸��հ�
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
     * Ҫ���ػ�
     * @param tm long ��Сʱ��
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
     * ���Ի�������
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
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        //���Ʊ߿�
        paintBorder(g);
        Rectangle r = g.getClipBounds();
        if(r.getWidth() <= 0|| r.getHeight() <= 0)
            return;
        //�õ��ڲ�ͼ���豸
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
        //���Ʊ���
        paintBackground(insetsG,widht,height);
        //���Ʊ༭����
        paintUIEditBackground(insetsG,widht,height);
        //�������
        paintComponents(insetsG,widht,height);
        //����ǰ��
        paintForeground(insetsG,widht,height);
        //�������뽹��
        paintMouseDraggedIn(insetsG,widht,height);
        //���Ʊ༭
        if(isUIEditStatus() && !isNoUIEdit())
        {
            //���Ʊ༭״̬
            paintUIEditStatus(insetsG, d.getWidth(), d.getHeight());
            //�����ƶ��е�������
            paintUIEditMoveLinkLine(insetsG,widht,height);
        }
    }
    /**
     * �������뽹��
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
        case 1://��
            TDrawTool.fillAlpha(0,0,width,t,c,jg,f,g);
            break;
        case 2://��
            TDrawTool.fillAlpha(0,height - t,width,t,c,jg,f,g);
            break;
        case 3://��
            TDrawTool.fillAlpha(0,0,t,height,c,jg,f,g);
            break;
        case 4://��
            TDrawTool.fillAlpha(width - t,0,t,height,c,jg,f,g);
            break;
        case 5:
            TDrawTool.fillAlpha(0,0,width,height,c,jg,f,g);
            break;
        }
    }
    /**
     * ���Ʊ༭״̬
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintUIEditStatus(Graphics g,int width,int height)
    {
        paintUIEditText(g,width,height);
        //���Ʊ༭ѡ��
        if(isUIEditSelected())
            paintUIEditSelecgted(g,width,height);
    }
    /**
     * ���Ʊ༭ѡ��
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
     * ���Ʊ༭��ǩ
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
        g.setFont(new Font("����",0,11));
        g.drawString(s,5,10);
    }
    /**
     * �������
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
     * ����ǰ��
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {
        paintText(g,width,height);
    }
    /**
     * �����ƶ��е�������
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
        //�õ������ߵ���ʼ��
        DPoint pointStart = getLinkLineStartPoint(line);
        DPoint pointEnd = getLinkLineEndPoint();
        g.setColor(new Color(255,0,0));
        g.drawLine(pointStart.x,pointStart.y,pointEnd.x,pointEnd.y);
    }
    /**
     * �õ������ߵĽ�����
     * @return DPoint
     */
    public DPoint getLinkLineEndPoint()
    {
        switch(getMouseDraggedIn())
        {
        case 1://��
            return new DPoint(getInsetWidth() / 2,LINK_LINE_SIZE / 2);
        case 2://��
            return new DPoint(getInsetWidth() / 2,getInsetHeight() - LINK_LINE_SIZE / 2);
        case 3://��
            return new DPoint(LINK_LINE_SIZE / 2,getInsetHeight() / 2);
        case 4://��
            return new DPoint(getInsetWidth() - LINK_LINE_SIZE / 2,getInsetHeight() / 2);
        }
        return new DPoint(getMouseX(),getMouseY());
    }
    /**
     * �õ������ߵ���ʼ��
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
     * ���Ʊ���
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
     * ���Ʊ༭����
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
     * ����������
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
        //����ȫ���������ʼ��
        pointUIEditLinkLineStartPointAllChildComponent(g,new Color(0,128,0));
        //����������
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
     * ����ȫ���������ʼ��
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
     * ���������ߵ���ʼ��
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
     * ������������ĵ�������
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
     * ���������ߵ���ʼ��
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
     * ����������
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
        //�����ڲ��ĵ�����
        if (line.fromComponent == null)
            return;
        DRectangle r = line.fromComponent.getComponentBounds();
        //���������ߵ���ʼ��
        pointUIEditLinkLineStartPoint(g,new Color(255,0,0),r,line.fromType,1);
        String s = line.toComponent.getTag() + ":" + line.size;
        g.setFont(new Font("����",0,11));
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
     * ���Ʊ߿�
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        if(getBorder() == null || getBorder().length() == 0)
            return;
        DBorder.paintBorder(this,getBorder(),g);
    }
    /**
     * ��������
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
     * д��������
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
        s.writeString(8,getColor(),"��");
        s.writeString(9,getBorder(),null);
        s.writeString(10,getFont(),null);
        s.writeInt(11,getCursor(),0);
        s.writeString(12,getText(),null);
        s.writeString(13,getHorizontalAlignment(),null);
        s.writeString(14,getVerticalAlignment(),null);
        //����������
        writeObjectAttributeLinkLine(15,s);
    }
    /**
     * ����������
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
     * ��ȡ������
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
     * д����
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
     * ����������
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
     * �õ����Ա������ֵ
     * @return int
     */
    public int getAttributeIDMax()
    {
        return 100;
    }
    /**
     * ������
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
        //��ʼ������
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
     * ��ʼ��ϵͳUI������
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
     * ���ض���
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
     * ���ýӿڿ�����
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
     * �õ��ӿڿ�����
     * @return String
     */
    public String getIOControl()
    {
        return (String)attribute.get("D_IOControl");
    }
    /**
     * ���ýӿڿ��������
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
     * �õ��ӿڿ��������
     * @return DComponentControl
     */
    public DComponentControl getIOControlObject()
    {
        return (DComponentControl)attribute.get("D_IOControleObject");
    }
    /**
     * ���ö�������
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
     * �Ƿ��Ƕ�������
     * @return boolean
     */
    public boolean isTopComponent()
    {
        return TypeTool.getBoolean(attribute.get("D_topComponent"));
    }
    /**
     * ���Ҷ���
     * @param tag String
     * @return DComponent
     */
    public DComponent findComponent(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        if(tag.equalsIgnoreCase(getTag()))
            return this;
        //�Ƕ�������ʼ���²���
        if(isTopComponent())
            return findChildComponent(tag);
        DComponent comParent = getParent();
        if(comParent != null)
            return comParent.findComponent(tag);
        return findChildComponent(tag);
    }
    /**
     * �����Ӷ���
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
     * ��ʼ���ӿڿ�����
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
     * ���ӹ�����
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
     * ɾ��������
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
     * �õ��������Ķ���
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
     * ���ҹ�����
     * @param name String
     * @return Object
     */
    public Object findPublicMethod(String name)
    {
        Object obj = getPublicMethod(name);
        if(obj != null)
            return obj;
        //�Ƕ�������ʼ���²���
        if(isTopComponent())
            return findChildPublicMethod(name);
        DComponent comParent = getParent();
        if(comParent != null)
            return comParent.findPublicMethod(name);
        return findChildPublicMethod(name);
    }
    /**
     * ����������Ĺ�����
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
     * ���з���
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters)
    {
        return invokePublicMethod(message,parameters);
    }
    /**
     * ִ�й�����
     * @param name String
     * @param args Object
     * @return Object
     */
    public Object invokePublicMethod(String name, Object args)
    {
        //������
        String value[] = StringTool.getHead(name, "|");

        Object obj = findPublicMethod(value[0]);
        if(obj == null)
            return null;
        return RunClass.invokeMethodT(obj, value, args);
    }
    /**
     * ���ö�����ͼ
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
     * ɾ��������ͼ
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
     * �õ�������ͼ
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
     * ִ�ж�����ͼ
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
     * ����������
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
     * �õ������߼���
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
     * ɾ��������
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
     * �������Ӷ���
     * @param name int
     * @param com DComponent
     * @param type int
     */
    public void setLinkComponent(int name,DComponent com,int type)
    {
        setLinkComponent(name,com,type,0);
    }
    /**
     * �������Ӷ���
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
        //��������
        if(getParent() != null)
            getParent().addLinkLine(type,link);
    }
    /**
     * ��ʼ�����Ӷ���
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
     * ��ʼ�����Ӷ���
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
     * ɾ�����Ӷ���
     */
    public void removeLinkComponent()
    {
        for(int i = 1;i <= 4;i++)
            removeLinkComponent(i);
    }
    /**
     * ɾ�����Ӷ���
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
        //ɾ������
        if(link != null)
            link.toComponent.removeLinkLine(link.toType,link);
    }
    /**
     * �õ����Ӷ���
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
     * �����ƶ���������
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
     * �õ��ƶ���������
     * @return DLinkLine
     */
    public DLinkLine getMoveLinkLine()
    {
        return (DLinkLine)attribute.get("D_moveLinkLine");
    }
    /**
     * �ܹ��õ�����
     * @return boolean
     */
    public boolean canFocus()
    {
        return false;
    }
    /**
     * �õ�Ĭ�Ͻ���
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
     * �õ���Ļ����
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
     * �����ƶ��¼�
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
     * �õ�ǰ����õ�����
     */
    public void grabFocus()
    {
        if(!isVisible() || !isEnabled() || !canFocus())
            return;
        setFocus(this);
    }
    /**
     * ���ý���
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
     * �õ��������
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
     * �õ������ʾ���
     * @return int
     */
    public int getMaxViewWidth()
    {
        return getComponentBounds().getWidth();
    }
    /**
     * �õ������ʾ�߶�
     * @return int
     */
    public int getMaxViewHeight()
    {
        return getComponentBounds().getHeight();
    }
    /**
     * �õ����ò�������
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
     * �򿪴���
     * @param config String �����ļ���
     * @return TComponent
     */
    public TComponent openWindow(String config)
    {
        return openWindow(config,null);
    }
    /**
     * �򿪴���
     * @param config String
     * @param parameter Object
     * @param isTop boolean true �������� false ��ͨ����
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter,boolean isTop)
    {
        if(isTop)
            return TWindow.openWindow(getConfigParm().newConfig(config),getTCBase(),parameter);
        return TFrame.openWindow(getConfigParm().newConfig(config),parameter);
    }
    /**
     * �򿪴���
     * @param config String �����ļ���
     * @param parameter Object ����
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter)
    {
        return openWindow(config,parameter,false);
    }
    /**
     * ����Ϣ����
     * @param config String �����ļ���
     * @return Object ����ֵ
     */
    public Object openDialog(String config)
    {
        return openDialog(config,null);
    }
    /**
     * ����Ϣ����
     * @param config String �����ļ���
     * @param parameter Object ����
     * @return Object ����ֵ
     */
    public Object openDialog(String config, Object parameter)
    {
        return openDialog(config,parameter,true);
    }
    /**
     * ����Ϣ����
     * @param config String �����ļ���
     * @param parameter Object ����
     * @param flg boolean true ��̬���� false ��̬����
     * @return Object
     */
    public Object openDialog(String config, Object parameter,boolean flg)
    {
        return TDialog.openWindow(getConfigParm().newConfig(config,flg),parameter);
    }
    /**
     * �����Ի�����ʾ��Ϣ
     * @param message Object
     */
    public void messageBox(Object message){
        JOptionPane.showMessageDialog(getTCBase(),TypeTool.getString(message));
    }
    /**
     * ��ʾ��Ϣ����
     * @param title String ����
     * @param message Object ��Ϣ
     * @param optionType int ��ť����
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
