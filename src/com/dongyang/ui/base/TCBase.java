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
 * <p>Title: D���������</p>
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
     * ����
     */
    Map attribute = new HashMap();
    /**
     * ������
     */
    public TCBase()
    {
    }
    /**
     * ���ý���
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
     * ���ý���(�ڲ�ʹ��)
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
     * ���ý���
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
     * �������
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
     * �õ��������
     * @return DComponent
     */
    public DComponent getFocus()
    {
        return (DComponent)attribute.get("D_focus");
    }
    /**
     * �õ�Ĭ�Ͻ���
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
     * ���ù��
     * @param cursor Cursor
     */
    public void setCursor(Cursor cursor)
    {
        if(getParent() != null)
        getParent().setCursor(cursor);
    }
    /**
     * �������
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
     * �õ����λ��
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
     * ɾ�����
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
        //ɾ������������
        if(getMouseInComponent() == component)
            setMouseInComponent(null);
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
        resetMousePoint(com);
    }
    /**
     * �����������λ��
     * @param com DComponent
     */
    public void resetMousePoint(DComponent com)
    {
        if(com == null)
            return;
        //��������
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
     * �õ���갴�¿ؼ�
     * @return DComponent
     */
    public DComponent getMousePressedComponent()
    {
        return (DComponent)attribute.get("D_mousePressedComponent");
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
     * ��������¼�����
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
     * �õ�����¼�����
     * @return MouseEvent
     */
    public MouseEvent getMouseEvent()
    {
        return (MouseEvent)attribute.get("D_mouseEvent");
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
        return TCM_Transform.getBoolean(attribute.get("D_mouseLeftKeyDown"));
    }
    /**
     * ����ctrl���Ƿ���
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
     * �õ�ctrl���Ƿ���
     * @return boolean
     */
    public boolean isControlDown()
    {
        return TCM_Transform.getBoolean(attribute.get("D_controlDown"));
    }
    /**
     * ����shift���Ƿ���
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
     * �õ�shift���Ƿ���
     * @return boolean
     */
    public boolean isShiftDown()
    {
        return TCM_Transform.getBoolean(attribute.get("D_shiftDown"));
    }
    /**
     * �õ��ڲ����
     * @return int
     */
    public int getInsetWidth()
    {
        Insets i = getInsets();
        return getWidth() - i.left - i.right;
    }
    /**
     * �õ��ڲ��߶�
     * @return int
     */
    public int getInsetHeight()
    {
        Insets i = getInsets();
        return getHeight() - i.top - i.bottom;
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
     * ������
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
     * ����뿪
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
     * ����ƶ�
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
     * ��������
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
            //��¼��갴���齨
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
     * ����̧��
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
     * ���
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
     * ˫��
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
     * ��껬��
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
     * ����϶�
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
     * ����ߴ�ı��¼�
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
     * �õ������¼�
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
            //���ý���
            setFocusP(com);
            com.onFocusGained();
            return;
        }
    }
    /**
     * ʧȥ�����¼�
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
     * ������
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
     * ����뿪
     */
    public void onMouseExited()
    {
    }
    /**
     * ����ƶ�
     */
    public void onMouseMoved()
    {
        setCursor(new Cursor(0));
    }
    /**
     * �������
     */
    public void onMouseLeftPressed()
    {
    }
    /**
     * �м�����
     */
    public void onMouseCenterPressed()
    {
    }
    /**
     * �Ҽ�����
     */
    public void onMouseRightPressed()
    {
    }
    /**
     * ���̧��
     */
    public void onMouseLeftReleased()
    {
    }
    /**
     * �м�̧��
     */
    public void onMouseCenterReleased()
    {
    }
    /**
     * �Ҽ�̧��
     */
    public void onMouseRightReleased()
    {
    }
    /**
     * ���
     */
    public void onClicked()
    {
    }
    /**
     * ˫��
     */
    public void onDoubleClicked()
    {
    }
    /**
     * ��껬��
     */
    public void onMouseWheelMoved()
    {
    }
    /**
     * ����϶�
     */
    public void onMouseDragged()
    {
    }
    /**
     * ����
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
     * ���̰���
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
     * ����̧��
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
     * �������λ�����ĸ��ؼ�
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
     * �������λ�����ĸ��ؼ�
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
            //�����������
            if(!com.isVisible())
                continue;
            //���겻�������Χ��
            if(com.inMouse(x,y))
                return com;
        }
        return null;
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
     * �ػ�
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        //DebugUsingTime.add("g start",false);
        super.paint(g);
        //����ǰ��
        Insets insets = getInsets();
        Graphics g1 = g.create(insets.left,insets.top,
                               getWidth() - insets.left - insets.right,
                               getHeight() - insets.top - insets.bottom);
        //���Ʊ���
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
     * ���Ʊ���
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
     * �õ���Ļ����
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        return new DPoint(getLocationOnScreen());
    }
    /**
     * �õ�����
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
