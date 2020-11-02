package com.dongyang.ui.event;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.dongyang.ui.TComponent;

public class TActionListener
        implements ActionListener,TEvent{
    /**
     * �����¼�
     */
    public static final String ACTION_PERFORMED = "actionPerformed";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TActionListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * �����¼�
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
