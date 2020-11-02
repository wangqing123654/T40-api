package com.dongyang.ui.event;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TComponent;

public class TMouseListener
        implements MouseListener,TEvent{
    /**
     * ������¼�
     */
    public static final String MOUSE_CLICKED = "mouseClicked";
    /**
     * ���˫���¼�
     */
    public static final String MOUSE_DOUBLE_CLICKED = "mousedoubleClicked";
    /**
     * ����������¼�
     */
    public static final String MOUSE_LEFT_CLICKED = "mouseLeftClicked";
    /**
     * ����Ҽ�����¼�
     */
    public static final String MOUSE_RIGHT_CLICKED = "mouseRightClicked";
    /**
     * �������¼�
     */
    public static final String MOUSE_ENTERED = "mouseEntered";
    /**
     * ����뿪�¼�
     */
    public static final String MOUSE_EXITED = "mouseExited";
    /**
     * ��갴���¼�
     */
    public static final String MOUSE_PRESSED = "mousePressed";
    /**
     * ���̧���¼�
     */
    public static final String MOUSE_RELEASED = "mouseReleased";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TMouseListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * ������¼�
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
     * �������¼�
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
     * ����뿪�¼�
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
     * ��갴���¼�
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
     * ���̧���¼�
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
