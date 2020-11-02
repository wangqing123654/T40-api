package com.dongyang.ui.event;

import java.awt.event.FocusListener;
import com.dongyang.ui.TComponent;
import java.awt.event.FocusEvent;

/**
 *
 * <p>Title: �����¼�������</p>
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
     * ʧȥ�����¼�
     */
    public static final String FOCUS_GAINED = "focusGained";
    /**
     * ʧȥ�����¼�
     */
    public static final String FOCUS_LOST = "focusLost";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TFocusListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * �õ������¼�
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
     * ʧȥ�����¼�
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
