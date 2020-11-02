package com.dongyang.ui.event;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TComponent;

public class TMouseMotionListener implements MouseMotionListener,TEvent{
    /**
     * ����϶��¼�
     */
    public static final String MOUSE_DRAGGED = "mouseDragged";
    /**
     * ����ƶ��¼�
     */
    public static final String MOUSE_MOVED = "mouseMoved";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TMouseMotionListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * ����϶��¼�
     * @param e MouseEvent
     */
    public void mouseDragged(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_DRAGGED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
    }
    /**
     * ����ƶ��¼�
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_MOVED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
    }
}
