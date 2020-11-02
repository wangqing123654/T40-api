package com.dongyang.ui.base;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import com.dongyang.ui.event.TFocusListener;
import java.awt.event.FocusEvent;
/**
 *
 * <p>Title: �ı��༭�������</p>
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
     * �����¼���������
     */
    private TFocusListener focusListenerObject;
    /**
     * ������
     */
    public TTextComponentBase()
    {
        enableEvents(60L);
    }
    public void initListeners()
    {
        super.initListeners();
        //���������¼�
        if(focusListenerObject == null)
        {
            focusListenerObject = new TFocusListener(this);
            addFocusListener(focusListenerObject);
        }
        addEventListener(getTag() + "->" + TFocusListener.FOCUS_LOST,"onFocusLostAction");
        addEventListener(getTag() + "->" + TFocusListener.FOCUS_GAINED,"onFocusGainedAction");
    }
    /**
     * �õ������¼�
     * @param e FocusEvent
     */
    public void onFocusGainedAction(FocusEvent e)
    {

    }
    /**
     * ʧȥ�����¼�
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {

    }

    /**
     * �����¼�����
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
     * ����
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {

    }
    /**
     * ���̰���
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
    }
    /**
     * ����̧��
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e)
    {

    }
    /**
     * AWT�¼�����
     * @param awtevent AWTEvent
     */
    protected void processEvent(AWTEvent awtevent)
    {
        if(awtevent.getID() == 504)
            return;
         super.processEvent(awtevent);
         int id = awtevent.getID();
         //�����
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
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TTextComponentBase";
    }
}
