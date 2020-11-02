package com.dongyang.ui.event;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import com.dongyang.ui.TComponent;

public class TWindowListener
        implements WindowListener,TEvent{
    /**
     * 窗口激活事件
     */
    public static final String WINDOW_ACTIVATED = "windowActivated";
    /**
     * 窗口关闭事件
     */
    public static final String WINDOW_CLOSED = "windowClosed";
    /**
     * 窗口将要关闭事件
     */
    public static final String WINDOW_CLOSING = "windowClosing";
    /**
     * 窗口失去交点事件
     */
    public static final String WINDOW_DEACTIVATED = "windowDeactivated";
    /**
     * 窗口隐含事件
     */
    public static final String WINDOW_DEICONIFIED = "windowDeiconified";
    /**
     * 窗口恢复事件
     */
    public static final String WINDOW_ICONIFIED = "windowIconified";
    /**
     * 窗口打开事件
     */
    public static final String WINDOW_OPENED = "windowOpened";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TWindowListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 窗口激活事件
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
     * 窗口关闭事件
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
     * 窗口将要关闭事件
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
     * 窗口失去交点事件
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
     * 窗口隐含事件
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
     * 窗口恢复事件
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
     * 窗口打开事件
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
