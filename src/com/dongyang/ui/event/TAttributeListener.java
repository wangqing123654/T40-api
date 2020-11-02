package com.dongyang.ui.event;

import com.dongyang.ui.TComponent;

public class TAttributeListener implements TEvent{
    /**
     * �����¼�
     */
    public static final String ATTRIBUTE_SET = "SET";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TAttributeListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * ��������
     * @param e TAttributeEvent
     */
    public void attributeSet(TAttributeEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + e.name + ":" + ATTRIBUTE_SET,new Object[]{e},new String[]{"com.dongyang.ui.event.TAttributeEvent"});
        component.callMessage(component.getTag() + "->" + e.name + ":" + ATTRIBUTE_SET,e);
    }
}
