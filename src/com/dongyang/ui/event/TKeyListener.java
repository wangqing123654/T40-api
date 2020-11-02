package com.dongyang.ui.event;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import com.dongyang.ui.TComponent;

public class TKeyListener implements KeyListener,TEvent
{
    /**
     * ���̰����¼�
     */
    public static final String KEY_PRESSED = "keyPressed";
    /**
     * ����̧���¼�
     */
    public static final String KEY_RELEASED = "keyReleased";
    /**
     * ����¼���¼�
     */
    public static final String KEY_TYPE = "keyTyped";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TKeyListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * ���̰����¼�
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + KEY_PRESSED,new Object[]{e},new String[]{"java.awt.event.KeyEvent"});
    }
    /**
     * ����̧���¼�
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + KEY_RELEASED,new Object[]{e},new String[]{"java.awt.event.KeyEvent"});
    }
    /**
     * ����¼���¼�
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + KEY_TYPE,new Object[]{e},new String[]{"java.awt.event.KeyEvent"});
    }
}
