package com.dongyang.ui.event;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import com.dongyang.ui.TComponent;

public class TKeyListener implements KeyListener,TEvent
{
    /**
     * 键盘按键事件
     */
    public static final String KEY_PRESSED = "keyPressed";
    /**
     * 键盘抬键事件
     */
    public static final String KEY_RELEASED = "keyReleased";
    /**
     * 键盘录入事件
     */
    public static final String KEY_TYPE = "keyTyped";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TKeyListener(TComponent component)
    {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 键盘按键事件
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + KEY_PRESSED,new Object[]{e},new String[]{"java.awt.event.KeyEvent"});
    }
    /**
     * 键盘抬键事件
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + KEY_RELEASED,new Object[]{e},new String[]{"java.awt.event.KeyEvent"});
    }
    /**
     * 键盘录入事件
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {
        if(component == null)
            return;
        component.callEvent(component.getTag() + "->" + KEY_TYPE,new Object[]{e},new String[]{"java.awt.event.KeyEvent"});
    }
}
