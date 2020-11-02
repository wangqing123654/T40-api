package com.dongyang.ui.event;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TComponent;

public class TMouseListener
        implements MouseListener,TEvent{
    /**
     * 鼠标点击事件
     */
    public static final String MOUSE_CLICKED = "mouseClicked";
    /**
     * 鼠标双击事件
     */
    public static final String MOUSE_DOUBLE_CLICKED = "mousedoubleClicked";
    /**
     * 鼠标左键点击事件
     */
    public static final String MOUSE_LEFT_CLICKED = "mouseLeftClicked";
    /**
     * 鼠标右键点击事件
     */
    public static final String MOUSE_RIGHT_CLICKED = "mouseRightClicked";
    /**
     * 鼠标进入事件
     */
    public static final String MOUSE_ENTERED = "mouseEntered";
    /**
     * 鼠标离开事件
     */
    public static final String MOUSE_EXITED = "mouseExited";
    /**
     * 鼠标按键事件
     */
    public static final String MOUSE_PRESSED = "mousePressed";
    /**
     * 鼠标抬键事件
     */
    public static final String MOUSE_RELEASED = "mouseReleased";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TMouseListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 鼠标点击事件
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_CLICKED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
        component.callMessage(component.getTag() + "->" + MOUSE_CLICKED,e);

        if(e.getButton() == e.BUTTON1)
            if(e.getClickCount() == 2)
            {
                component.callEvent(component.getTag() + "->" + MOUSE_DOUBLE_CLICKED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
                component.callMessage(component.getTag() + "->" + MOUSE_DOUBLE_CLICKED,e);
            }
            else
            {
                component.callEvent(component.getTag() + "->" + MOUSE_LEFT_CLICKED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
                component.callMessage(component.getTag() + "->" + MOUSE_LEFT_CLICKED,e);
            }
        if(e.getButton() == e.BUTTON2)
        {
            //mouseCenterPressed(e);
        }
        if(e.getButton() == e.BUTTON3)
        {
            component.callEvent(component.getTag() + "->" + MOUSE_RIGHT_CLICKED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
            component.callMessage(component.getTag() + "->" + MOUSE_RIGHT_CLICKED,e);
        }
    }
    /**
     * 鼠标进入事件
     * @param e MouseEvent
     */
    public void mouseEntered(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_ENTERED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
        //component.callMessage(component.getTag() + "->" + MOUSE_ENTERED,e);
    }
    /**
     * 鼠标离开事件
     * @param e MouseEvent
     */
    public void mouseExited(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_EXITED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
        //component.callMessage(component.getTag() + "->" + MOUSE_EXITED,e);
    }
    /**
     * 鼠标按键事件
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_PRESSED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
        component.callMessage(component.getTag() + "->" + MOUSE_PRESSED,e);
    }
    /**
     * 鼠标抬键事件
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_RELEASED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
        component.callMessage(component.getTag() + "->" + MOUSE_RELEASED,e);
    }
}
