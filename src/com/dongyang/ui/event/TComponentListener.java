package com.dongyang.ui.event;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import com.dongyang.ui.TComponent;

public class TComponentListener
        implements ComponentListener,TEvent
{
    /**
     * 尺寸改变事件
     */
    public static final String RESIZED = "resized";
    /**
     * 组件隐藏事件
     */
    public static final String COMPONENT_HIDDEN = "componentHidden";
    /**
     * 组件移动事件
     */
    public static final String COMPONENT_MOVED = "componentMoved";
    /**
     * 组件尺寸改变事件
     */
    public static final String COMPONENT_RESIZED = "componentResized";
    /**
     * 组件显示事件
     */
    public static final String COMPONENT_SHOWN = "componentShown";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TComponentListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 组件隐藏事件
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_HIDDEN,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
    /**
     * 组件移动事件
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_MOVED,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
    /**
     * 组件尺寸改变事件
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_RESIZED,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
    /**
     * 组件显示事件
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_SHOWN,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
}
