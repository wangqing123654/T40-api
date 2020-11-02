package com.dongyang.ui.text;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TText;
import com.dongyang.ui.TComponent;

public class TTextUI extends JPanel implements ComponentListener{
    /**
     * tText����
     */
    private TComponent tText;
    /**
     * ҳ����
     */
    private TPage page;
    /**
     * ������
     */
    public TTextUI()
    {
        init();
    }
    /**
     * ������
     * @param tText TComponent
     */
    public TTextUI(TComponent tText)
    {
        this.tText = tText;
        init();
    }
    /**
     * ��ʼ��
     */
    public void init()
    {
        enableEvents(60L);
        page = new TPage();
        page.setUI(this);
        addComponentListener(this);
    }
    /**
     * �õ�ҳ�����
     * @return TPage
     */
    public TPage getPage()
    {
        return page;
    }
    /**
     * UI�����¼�
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e)
    {

    }
    /**
     * UI�ƶ��¼�
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e)
    {
    }
    /**
     * UI�ߴ�ı��¼�
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        //ͬ�������������ߴ�
        getPage().synchronizationScrollBarW();
        getPage().synchronizationScrollBarH();
    }
    /**
     * UI��ʾ�¼�
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
    }
    /**
     * �����¼�����
     * @param keyevent KeyEvent
     */
    protected void processKeyEvent(KeyEvent keyevent)
    {
        int keyCode = keyevent.getKeyCode();
        System.out.println(keyCode);
    }
    /**
     * AWT�¼�����
     * @param awtevent AWTEvent
     */
    protected void processEvent(AWTEvent awtevent)
    {
         super.processEvent(awtevent);
         int id = awtevent.getID();
         //�����
         if(id == 501)
             requestFocus();
         if(awtevent instanceof MouseEvent)
            mouseEvent((MouseEvent)awtevent);
    }
    /**
     * �����������¼�
     * @param e MouseEvent
     */
    public void mouseLeftKeyDown(MouseEvent e)
    {
        System.out.println(e.getX() + " " + e.getY());
    }
    /**
     * ����¼�
     * @param e MouseEvent
     */
    public void mouseEvent(MouseEvent e)
    {
        int eventID = e.getID();
        switch(eventID)
        {
        case 501://����
            //���������������¼�
            if(e.getButton() == 1)
                mouseLeftKeyDown(e);
            return;
        case 502://̧��
            if(e.getButton() == 1)
            {
            }
            return;
        case 503://�ƶ�
            mouseMove(e);
            return;
        case 506://�϶�
            return;
        }
    }
    /**
     * ����ƶ�
     * @param e MouseEvent
     */
    public void mouseMove(MouseEvent e)
    {

    }
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        page.paint(g);
    }
}
