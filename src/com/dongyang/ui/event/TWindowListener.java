package com.dongyang.ui.event;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import com.dongyang.ui.TComponent;

public class TWindowListener
        implements WindowListener,TEvent{
    /**
     * ���ڼ����¼�
     */
    public static final String WINDOW_ACTIVATED = "windowActivated";
    /**
     * ���ڹر��¼�
     */
    public static final String WINDOW_CLOSED = "windowClosed";
    /**
     * ���ڽ�Ҫ�ر��¼�
     */
    public static final String WINDOW_CLOSING = "windowClosing";
    /**
     * ����ʧȥ�����¼�
     */
    public static final String WINDOW_DEACTIVATED = "windowDeactivated";
    /**
     * ���������¼�
     */
    public static final String WINDOW_DEICONIFIED = "windowDeiconified";
    /**
     * ���ڻָ��¼�
     */
    public static final String WINDOW_ICONIFIED = "windowIconified";
    /**
     * ���ڴ��¼�
     */
    public static final String WINDOW_OPENED = "windowOpened";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TWindowListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * ���ڼ����¼�
     * @param e WindowEvent
     */
    public void windowActivated(WindowEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + WINDOW_ACTIVATED,
                            new Object[]{e},new String[]{"java.awt.event.WindowEvent"});
        component.callMessage(component.getTag() + "->" + WINDOW_ACTIVATED,e);
    }
    /**
     * ���ڹر��¼�
     * @param e WindowEvent
     */
    public void windowClosed(WindowEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + WINDOW_CLOSED,
                            new Object[]{e},new String[]{"java.awt.event.WindowEvent"});
        component.callMessage(component.getTag() + "->" + WINDOW_CLOSED,e);
    }
    /**
     * ���ڽ�Ҫ�ر��¼�
     * @param e WindowEvent
     */
    public void windowClosing(WindowEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + WINDOW_CLOSING,
                            new Object[]{e},new String[]{"java.awt.event.WindowEvent"});
        component.callMessage(component.getTag() + "->" + WINDOW_CLOSING,e);
    }
    /**
     * ����ʧȥ�����¼�
     * @param e WindowEvent
     */
    public void windowDeactivated(WindowEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + WINDOW_DEACTIVATED,
                            new Object[]{e},new String[]{"java.awt.event.WindowEvent"});
        component.callMessage(component.getTag() + "->" + WINDOW_DEACTIVATED,e);
    }
    /**
     * ���������¼�
     * @param e WindowEvent
     */
    public void windowDeiconified(WindowEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + WINDOW_DEICONIFIED,
                            new Object[]{e},new String[]{"java.awt.event.WindowEvent"});
        component.callMessage(component.getTag() + "->" + WINDOW_DEICONIFIED,e);
    }
    /**
     * ���ڻָ��¼�
     * @param e WindowEvent
     */
    public void windowIconified(WindowEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + WINDOW_ICONIFIED,
                            new Object[]{e},new String[]{"java.awt.event.WindowEvent"});
        component.callMessage(component.getTag() + "->" + WINDOW_ICONIFIED,e);
    }
    /**
     * ���ڴ��¼�
     * @param e WindowEvent
     */
    public void windowOpened(WindowEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + WINDOW_OPENED,
                            new Object[]{e},new String[]{"java.awt.event.WindowEvent"});
        component.callMessage(component.getTag() + "->" + WINDOW_OPENED,e);
    }
}
