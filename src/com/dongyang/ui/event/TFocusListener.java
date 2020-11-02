package com.dongyang.ui.event;

import java.awt.event.FocusListener;
import com.dongyang.ui.TComponent;
import java.awt.event.FocusEvent;

/**
 *
 * <p>Title: 焦点事件适配器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.26
 * @version 1.0
 */
public class TFocusListener
        implements FocusListener,TEvent{
    /**
     * 失去焦点事件
     */
    public static final String FOCUS_GAINED = "focusGained";
    /**
     * 失去焦点事件
     */
    public static final String FOCUS_LOST = "focusLost";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TFocusListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 得到焦点事件
     * @param e FocusEvent
     */
    public void focusGained(FocusEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + FOCUS_GAINED,new Object[]{e},new String[]{"java.awt.event.FocusEvent"});
        //component.callMessage(component.getTag() + "->" + FOCUS_GAINED,e);
    }
    /**
     * 失去焦点事件
     * @param e FocusEvent
     */
    public void focusLost(FocusEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + FOCUS_LOST,new Object[]{e},new String[]{"java.awt.event.FocusEvent"});
        //component.callMessage(component.getTag() + "->" + FOCUS_LOST,e);
    }
}
