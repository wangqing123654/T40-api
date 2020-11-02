package com.dongyang.ui.event;

import com.dongyang.ui.TComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TChangeListener
        implements ChangeListener,TEvent{
    /**
     * �����¼�
     */
    public static final String STATE_CHANGED = "stateChanged";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TChangeListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * ��ǩ�ı�
     * @param e ChangeEvent
     */
    public void stateChanged(ChangeEvent e) {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + STATE_CHANGED,new Object[]{e},new String[]{"javax.swing.event.ChangeEvent"});
        component.callMessage(component.getTag() + "->" + STATE_CHANGED,e);
    }
}
