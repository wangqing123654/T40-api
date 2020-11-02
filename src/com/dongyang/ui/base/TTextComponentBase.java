package com.dongyang.ui.base;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import com.dongyang.ui.event.TFocusListener;
import java.awt.event.FocusEvent;
/**
 *
 * <p>Title: 文本编辑组件基类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.6
 * @version 1.0
 */
public class TTextComponentBase extends TComponentBase
{
    /**
     * 焦点事件监听对象
     */
    private TFocusListener focusListenerObject;
    /**
     * 构造器
     */
    public TTextComponentBase()
    {
        enableEvents(60L);
    }
    public void initListeners()
    {
        super.initListeners();
        //监听焦点事件
        if(focusListenerObject == null)
        {
            focusListenerObject = new TFocusListener(this);
            addFocusListener(focusListenerObject);
        }
        addEventListener(getTag() + "->" + TFocusListener.FOCUS_LOST,"onFocusLostAction");
        addEventListener(getTag() + "->" + TFocusListener.FOCUS_GAINED,"onFocusGainedAction");
    }
    /**
     * 得到焦点事件
     * @param e FocusEvent
     */
    public void onFocusGainedAction(FocusEvent e)
    {

    }
    /**
     * 失去焦点事件
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {

    }

    /**
     * 键盘事件监听
     * @param keyevent KeyEvent
     */
    protected void processKeyEvent(KeyEvent keyevent)
    {
        int keyID = keyevent.getID();
        switch(keyID)
        {
        case KeyEvent.KEY_PRESSED:
            keyPressed(keyevent);
            break;
        case KeyEvent.KEY_RELEASED:
            keyReleased(keyevent);
            break;
        case KeyEvent.KEY_TYPED:
            keyTyped(keyevent);
            break;
        }
    }
    /**
     * 击打
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {

    }
    /**
     * 键盘按下
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
    }
    /**
     * 键盘抬起
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e)
    {

    }
    /**
     * AWT事件监听
     * @param awtevent AWTEvent
     */
    protected void processEvent(AWTEvent awtevent)
    {
        if(awtevent.getID() == 504)
            return;
         super.processEvent(awtevent);
         int id = awtevent.getID();
         //鼠标点击
         if(id == 501)
             requestFocus();
    }
    public void requestFocus()
    {
        if(!isEnabled())
            return;
        super.requestFocus();
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "TTextComponentBase";
    }
}
