package com.dongyang.ui.event;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import com.dongyang.ui.TComponent;

public class TComponentListener
        implements ComponentListener,TEvent
{
    /**
     * �ߴ�ı��¼�
     */
    public static final String RESIZED = "resized";
    /**
     * ��������¼�
     */
    public static final String COMPONENT_HIDDEN = "componentHidden";
    /**
     * ����ƶ��¼�
     */
    public static final String COMPONENT_MOVED = "componentMoved";
    /**
     * ����ߴ�ı��¼�
     */
    public static final String COMPONENT_RESIZED = "componentResized";
    /**
     * �����ʾ�¼�
     */
    public static final String COMPONENT_SHOWN = "componentShown";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TComponentListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * ��������¼�
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_HIDDEN,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
    /**
     * ����ƶ��¼�
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_MOVED,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
    /**
     * ����ߴ�ı��¼�
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_RESIZED,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
    /**
     * �����ʾ�¼�
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + COMPONENT_SHOWN,new Object[]{e},new String[]{"java.awt.event.ComponentEvent"});
    }
}
