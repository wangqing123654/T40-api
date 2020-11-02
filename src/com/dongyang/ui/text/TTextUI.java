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
     * tText对象
     */
    private TComponent tText;
    /**
     * 页对象
     */
    private TPage page;
    /**
     * 构造器
     */
    public TTextUI()
    {
        init();
    }
    /**
     * 构造器
     * @param tText TComponent
     */
    public TTextUI(TComponent tText)
    {
        this.tText = tText;
        init();
    }
    /**
     * 初始化
     */
    public void init()
    {
        enableEvents(60L);
        page = new TPage();
        page.setUI(this);
        addComponentListener(this);
    }
    /**
     * 得到页面对象
     * @return TPage
     */
    public TPage getPage()
    {
        return page;
    }
    /**
     * UI隐藏事件
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e)
    {

    }
    /**
     * UI移动事件
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e)
    {
    }
    /**
     * UI尺寸改变事件
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        //同步滚动条的最大尺寸
        getPage().synchronizationScrollBarW();
        getPage().synchronizationScrollBarH();
    }
    /**
     * UI显示事件
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
    }
    /**
     * 键盘事件监听
     * @param keyevent KeyEvent
     */
    protected void processKeyEvent(KeyEvent keyevent)
    {
        int keyCode = keyevent.getKeyCode();
        System.out.println(keyCode);
    }
    /**
     * AWT事件监听
     * @param awtevent AWTEvent
     */
    protected void processEvent(AWTEvent awtevent)
    {
         super.processEvent(awtevent);
         int id = awtevent.getID();
         //鼠标点击
         if(id == 501)
             requestFocus();
         if(awtevent instanceof MouseEvent)
            mouseEvent((MouseEvent)awtevent);
    }
    /**
     * 鼠标左键按下事件
     * @param e MouseEvent
     */
    public void mouseLeftKeyDown(MouseEvent e)
    {
        System.out.println(e.getX() + " " + e.getY());
    }
    /**
     * 鼠标事件
     * @param e MouseEvent
     */
    public void mouseEvent(MouseEvent e)
    {
        int eventID = e.getID();
        switch(eventID)
        {
        case 501://按键
            //触发鼠标左键按下事件
            if(e.getButton() == 1)
                mouseLeftKeyDown(e);
            return;
        case 502://抬键
            if(e.getButton() == 1)
            {
            }
            return;
        case 503://移动
            mouseMove(e);
            return;
        case 506://拖动
            return;
        }
    }
    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void mouseMove(MouseEvent e)
    {

    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        page.paint(g);
    }
}
