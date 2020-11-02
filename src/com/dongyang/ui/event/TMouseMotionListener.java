package com.dongyang.ui.event;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TComponent;

public class TMouseMotionListener implements MouseMotionListener,TEvent{
    /**
     * 鼠标拖动事件
     */
    public static final String MOUSE_DRAGGED = "mouseDragged";
    /**
     * 鼠标移动事件
     */
    public static final String MOUSE_MOVED = "mouseMoved";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TMouseMotionListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 鼠标拖动事件
     * @param e MouseEvent
     */
    public void mouseDragged(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_DRAGGED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
    }
    /**
     * 鼠标移动事件
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + MOUSE_MOVED,new Object[]{e},new String[]{"java.awt.event.MouseEvent"});
    }
}
