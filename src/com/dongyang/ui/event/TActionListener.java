package com.dongyang.ui.event;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.dongyang.ui.TComponent;

public class TActionListener
        implements ActionListener,TEvent{
    /**
     * 动作事件
     */
    public static final String ACTION_PERFORMED = "actionPerformed";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TActionListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 动作事件
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + ACTION_PERFORMED,new Object[]{e},new String[]{"java.awt.event.ActionEvent"});
        component.callMessage(component.getTag() + "->" + ACTION_PERFORMED,e);
    }
}
