package com.dongyang.ui.base;

import java.awt.Graphics;
import java.util.List;
import com.dongyang.tui.DComponent;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.MouseWheelEvent;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.tui.DRectangle;
import com.dongyang.util.TList;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import com.dongyang.tui.DPoint;
import java.awt.Frame;
import java.awt.Container;
import java.awt.Component;
import com.dongyang.tui.base.DWindowBase;
import com.dongyang.tui.DWindow;
import com.dongyang.tui.DPopupMenu;
import com.dongyang.tui.DMenu;

/**
 *
 * <p>Title: D组件基础类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.30
 * @version 1.0
 */
public class TCBase extends TTextComponentBase
{
    /**
     * 属性
     */
    Map attribute = new HashMap();
    /**
     * 构造器
     */
    public TCBase()
    {
    }
    /**
     * 设置焦点
     * @param component DComponent
     */
    public void setFocus(DComponent component)
    {
        if(getFocus() == component)
            return;
        if(getFocus() != null)
            getFocus().onFocusLost();
        setFocusP(component);
        if(component != null)
            component.onFocusGained();
        grabFocus();
    }
    /**
     * 设置焦点(内部使用)
     * @param component DComponent
     */
    private void setFocusP(DComponent component)
    {
        if(component == null)
        {
            attribute.remove("D_focus");
            return;
        }
        attribute.put("D_focus",component);
    }
    /**
     * 设置焦点
     * @param tag String
     */
    public void setFocus(String tag)
    {
        DComponent com = findComponent(tag);
        if(com == null)
            return;
        setFocus(com);
    }
    /**
     * 查找组件
     * @param tag String
     * @return DComponent
     */
    public DComponent findComponent(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        TList list = getDComponentList();
        if(list == null)
            return null;
        for(int i = 0;i < list.size();i++)
        {
            DComponent component = (DComponent)list.get(i);
            if(component == null)
                continue;
            DComponent findCom = component.findComponent(tag);
            if(findCom != null)
                return findCom;
        }
        return null;
    }
    /**
     * 得到焦点组件
     * @return DComponent
     */
    public DComponent getFocus()
    {
        return (DComponent)attribute.get("D_focus");
    }
    /**
     * 得到默认焦点
     * @return DComponent
     */
    public DComponent getDefaultFocus()
    {
        TList components = getDComponentList();
        if(components == null)
            return null;
        for(int i = 0;i < components.size();i++)
        {
            DComponent com = (DComponent)components.get(i);
            if(com == null)
                continue;
            DComponent focusCom = com.getDefaultFocus();
            if(focusCom != null)
                return focusCom;
        }
        return null;
    }
    /**
     * 设置光标
     * @param cursor Cursor
     */
    public void setCursor(Cursor cursor)
    {
        if(getParent() != null)
        getParent().setCursor(cursor);
    }
    /**
     * 增加组件
     * @param component DComponent
     */
    public void addDComponent(DComponent component)
    {
        TList components = getDComponentList();
        if(components == null)
        {
            components = new TList();
            attribute.put("D_components",components);
        }
        components.add(component);
        component.setParentTCBase(this);
    }
    /**
     * 得到组件位置
     * @param component DComponent
     * @return int
     */
    public int getDComponentIndex(DComponent component)
    {
        if(component == null)
            return -1;
        List components = (List)attribute.get("D_components");
        if(components == null)
            return -1;
        for(int i = 0;i < components.size();i++)
            if(component == components.get(i))
                return i;
        return -1;
    }
    /**
     * 删除组件
     * @param component DComponent
     */
    public void removeDComponent(DComponent component)
    {
        List components = (List)attribute.get("D_components");
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
    public void setDCcomponentList(TList list)
    {
        if(list == null)
        {
            attribute.remove("D_components");
            return;
        }
        attribute.put("D_components",list);
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
        resetMousePoint(com);
    }
    /**
     * 重新设置鼠标位置
     * @param com DComponent
     */
    public void resetMousePoint(DComponent com)
    {
        if(com == null)
            return;
        //保存坐标
        MouseEvent e = getMouseEvent();
        if(e == null)
            return;
        Insets insets = getInsets();
        DRectangle r = com.getComponentBounds();
        int x = e.getX() - insets.left;
        int y = e.getY() - insets.right;
        com.setMousePoint(x - r.getX(), y - r.getY());
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
     * 设置鼠标事件参数
     * @param e MouseEvent
     */
    public void setMouseEvent(MouseEvent e)
    {
        if(e == null)
        {
            attribute.remove("D_mouseEvent");
            return;
        }
        attribute.put("D_mouseEvent",e);
    }
    /**
     * 得到鼠标事件参数
     * @return MouseEvent
     */
    public MouseEvent getMouseEvent()
    {
        return (MouseEvent)attribute.get("D_mouseEvent");
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
        return TCM_Transform.getBoolean(attribute.get("D_mouseLeftKeyDown"));
    }
    /**
     * 设置ctrl建是否按下
     * @param b boolean
     */
    public void setControlDown(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_controlDown");
            return;
        }
        attribute.put("D_controlDown",b);
    }
    /**
     * 得到ctrl建是否按下
     * @return boolean
     */
    public boolean isControlDown()
    {
        return TCM_Transform.getBoolean(attribute.get("D_controlDown"));
    }
    /**
     * 设置shift建是否按下
     * @param b boolean
     */
    public void setShiftDown(boolean b)
    {
        if(!b)
        {
            attribute.remove("D_shiftDown");
            return;
        }
        attribute.put("D_shiftDown",b);
    }
    /**
     * 得到shift建是否按下
     * @return boolean
     */
    public boolean isShiftDown()
    {
        return TCM_Transform.getBoolean(attribute.get("D_shiftDown"));
    }
    /**
     * 得到内部宽度
     * @return int
     */
    public int getInsetWidth()
    {
        Insets i = getInsets();
        return getWidth() - i.left - i.right;
    }
    /**
     * 得到内部高度
     * @return int
     */
    public int getInsetHeight()
    {
        Insets i = getInsets();
        return getHeight() - i.top - i.bottom;
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
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        setMouseEvent(e);
        onMouseEntered();
        DComponent com = findMousePointInComponent();
        DComponent oldCom = getMouseInComponent();
        if(oldCom != null && oldCom != com)
        {
            com.onMouseEntered();
            setMouseInComponent(null);
        }
        if(com != null)
        {
            setMouseInComponent(com);
            resetMousePoint(com);
            com.onMouseEntered();
        }
        setMouseEvent(null);
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        setMouseEvent(e);
        DComponent oldCom = getMouseInComponent();
        if(oldCom != null)
        {
            oldCom.onMouseExited();
            setMouseInComponent(null);
        }
        onMouseExited();
        setMouseEvent(null);
    }
    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
        setMouseEvent(e);
        DComponent com = findMousePointInComponent();
        DComponent oldCom = getMouseInComponent();
        if(oldCom != null && oldCom != com)
        {
            oldCom.onMouseExited();
            setMouseInComponent(null);
        }
        if(com != null)
        {
            resetMousePoint(com);
            if(oldCom != com)
            {
                setMouseInComponent(com);
                com.onMouseEntered();
            }
            com.onMouseMoved();
            setMouseEvent(null);
            return;
        }
        onMouseMoved();
        setMouseEvent(null);
    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        setMouseEvent(e);
        DComponent focusCom = getFocus();
        if(focusCom != null)
            focusCom.onWindowMousePressed();
        DComponent com = findMousePointInComponent();
        if (com != null)
        {
            //记录鼠标按下组建
            setMousePressedComponent(com);
            switch (e.getButton())
            {
            case MouseEvent.BUTTON1:
                setMouseLeftKeyDown(true);
                com.onMouseLeftPressed();
                break;
            case MouseEvent.BUTTON2:
                com.onMouseCenterPressed();
                break;
            case MouseEvent.BUTTON3:
                com.onMouseRightPressed();
                break;
            }
            setMouseEvent(null);
            return;
        }
        switch(e.getButton())
        {
        case MouseEvent.BUTTON1:
            setMouseLeftKeyDown(true);
            onMouseLeftPressed();
            break;
        case MouseEvent.BUTTON2:
            onMouseCenterPressed();
            break;
        case MouseEvent.BUTTON3:
            onMouseRightPressed();
            break;
        }
        setMouseEvent(null);
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        setMouseEvent(e);
        DComponent com = getMousePressedComponent();
        if(com != null)
        {
            resetMousePoint(com);
            switch (e.getButton())
            {
            case MouseEvent.BUTTON1:
                setMouseLeftKeyDown(false);
                com.onMouseLeftReleased();
                break;
            case MouseEvent.BUTTON2:
                com.onMouseCenterReleased();
                break;
            case MouseEvent.BUTTON3:
                com.onMouseRightReleased();
                break;
            }
            setMousePressedComponent(null);
            setMouseEvent(null);
            return;
        }
        switch(e.getButton())
        {
        case MouseEvent.BUTTON1:
            setMouseLeftKeyDown(false);
            onMouseLeftReleased();
            setMouseDraggedInComponent(null);
            break;
        case MouseEvent.BUTTON2:
            onMouseCenterReleased();
            break;
        case MouseEvent.BUTTON3:
            onMouseRightReleased();
            break;
        }
        setMouseEvent(null);
    }
    /**
     * 点击
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
        setMouseEvent(e);
        DComponent com = findMousePointInComponent();
        if(com != null)
        {
            resetMousePoint(com);
            com.onClickedS();
            setMouseEvent(null);
            return;
        }
        onClicked();
        setMouseEvent(null);
    }
    /**
     * 双击
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {
        setMouseEvent(e);
        DComponent com = findMousePointInComponent();
        if(com != null)
        {
            resetMousePoint(com);
            com.onDoubleClickedS();
            setMouseEvent(null);
            return;
        }
        onDoubleClicked();
        setMouseEvent(null);
    }
    /**
     * 鼠标滑轮
     * @param e MouseWheelEvent
     */
    public void onMouseWheelMoved(MouseWheelEvent e)
    {
        setMouseEvent(e);
        DComponent com = findMousePointInComponent();
        if(com != null)
        {
            resetMousePoint(com);
            com.setMouseWheelMoved(e.getUnitsToScroll());
            com.onMouseWheelMoved();
            setMouseEvent(null);
            return;
        }
        onMouseWheelMoved();
        setMouseEvent(null);
    }
    /**
     * 鼠标拖动
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        if(!isMouseLeftKeyDown())
            return;
        setMouseEvent(e);
        DComponent com = getMousePressedComponent();
        if(com != null)
        {
            resetMousePoint(com);
            if(com.onMouseDragged())
                return;
            setMouseEvent(null);
            return;
        }
        onMouseDragged();
        setMouseEvent(null);
    }
    public void onInit()
    {
        super.onInit();
        onComponentResized(null);
    }
    /**
     * 组件尺寸改变事件
     * @param e ComponentEvent
     */
    public void onComponentResized(ComponentEvent e)
    {
        TList list = getDComponentList();
        if(list == null)
            return;
        for(int i = 0;i < list.size();i++)
        {
            DComponent com = (DComponent)list.get(i);
            if(com == null)
                continue;
            com.onParentResized();
        }
    }
    /**
     * 得到焦点事件
     * @param e FocusEvent
     */
    public void onFocusGainedAction(FocusEvent e)
    {
        //System.out.println("onFocusGainedAction " + e);
        if (!isEnabled())
            return;
        DComponent com = getFocus();
        if(com != null)
        {
            com.onFocusGained();
            return;
        }
        com = getDefaultFocus();
        if(com != null)
        {
            //设置焦点
            setFocusP(com);
            com.onFocusGained();
            return;
        }
    }
    /**
     * 失去焦点事件
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        if (!isEnabled())
            return;
        DComponent com = getFocus();
        if(com == null)
            return;
        Component component = e.getOppositeComponent();
        if(component == null || !(component instanceof DWindowBase))
        {
            com.onFocusLost();
            return;
        }
        DWindow window = ((DWindowBase)component).getWindow();
        if(window == null || !(window instanceof DPopupMenu))
        {
            com.onFocusLost();
            return;
        }
        DMenu menu = ((DPopupMenu)window).getTopMenu();
        if(menu != null && menu.getTCBase() == this)
            return;
        com.onFocusLost();
    }
    /**
     * 鼠标进入
     */
    public void onMouseEntered()
    {
        DComponent com = getFocus();
        if(com != null)
        {
            com.onFocusGained();
            return;
        }
    }
    /**
     * 鼠标离开
     */
    public void onMouseExited()
    {
    }
    /**
     * 鼠标移动
     */
    public void onMouseMoved()
    {
        setCursor(new Cursor(0));
    }
    /**
     * 左键按下
     */
    public void onMouseLeftPressed()
    {
    }
    /**
     * 中键按下
     */
    public void onMouseCenterPressed()
    {
    }
    /**
     * 右键按下
     */
    public void onMouseRightPressed()
    {
    }
    /**
     * 左键抬起
     */
    public void onMouseLeftReleased()
    {
    }
    /**
     * 中键抬起
     */
    public void onMouseCenterReleased()
    {
    }
    /**
     * 右键抬起
     */
    public void onMouseRightReleased()
    {
    }
    /**
     * 点击
     */
    public void onClicked()
    {
    }
    /**
     * 双击
     */
    public void onDoubleClicked()
    {
    }
    /**
     * 鼠标滑轮
     */
    public void onMouseWheelMoved()
    {
    }
    /**
     * 鼠标拖动
     */
    public void onMouseDragged()
    {
    }
    /**
     * 击打
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {
        DComponent com = getFocus();
        if(com != null)
        {
            com.setKeyEvent(e);
            com.onKeyTyped();
            com.setKeyEvent(null);
            return;
        }
    }
    /**
     * 键盘按下
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
        setControlDown(e.isControlDown());
        setShiftDown(e.isShiftDown());
        DComponent com = getFocus();
        if(com != null)
        {
            com.setKeyEvent(e);
            com.onKeyPressed();
            com.setKeyEvent(null);
            return;
        }
    }
    /**
     * 键盘抬起
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e)
    {
        setControlDown(e.isControlDown());
        setShiftDown(e.isShiftDown());
        DComponent com = getFocus();
        if(com != null)
        {
            com.setKeyEvent(e);
            com.onKeyReleased();
            com.setKeyEvent(null);
            return;
        }
    }
    /**
     * 查找鼠标位置在哪个控件
     * @return DComponent
     */
    private DComponent findMousePointInComponent()
    {
        MouseEvent e = getMouseEvent();
        Insets insets = getInsets();
        int x = e.getX() - insets.left;
        int y = e.getY() - insets.right;
        if(x < 0 || y < 0)
            return null;
        return findMousePointInComponent(x,y);
    }
    /**
     * 查找鼠标位置在哪个控件
     * @param x int
     * @param y int
     * @return DComponent
     */
    private DComponent findMousePointInComponent(int x,int y)
    {
        TList components = getDComponentList();
        if(components == null)
            return null;
        for(int i = components.size() - 1;i >= 0;i--)
        {
            DComponent com =  (DComponent)components.get(i);
            if(com == null)
                continue;
            //组件是隐含的
            if(!com.isVisible())
                continue;
            //坐标不在组件范围内
            if(com.inMouse(x,y))
                return com;
        }
        return null;
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
     * 重画
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        //DebugUsingTime.add("g start",false);
        super.paint(g);
        //绘制前景
        Insets insets = getInsets();
        Graphics g1 = g.create(insets.left,insets.top,
                               getWidth() - insets.left - insets.right,
                               getHeight() - insets.top - insets.bottom);
        //绘制背景
        paintBack(g1);
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
            com.checkPaintClipBounds(g1);
        }
        //DebugUsingTime.add("g end");
    }
    /**
     * 绘制背景
     * @param g Graphics
     */
    public void paintBack(Graphics g)
    {
        if(getBackground() == null)
            return;
        //g.setColor(new Color(255,0,0));
        //g.fillRect(0,0,getWidth(),getHeight());
    }
    /**
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        return new DPoint(getLocationOnScreen());
    }
    /**
     * 得到窗口
     * @return Frame
     */
    public Frame getFrame()
    {
        Container container = getParent();
        while(container != null && !(container instanceof Frame))
            container = container.getParent();
        return (Frame)container;
    }
}
