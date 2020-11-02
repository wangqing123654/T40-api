package com.dongyang.tui.base;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import com.dongyang.tui.DFrame;

/**
 * <p>Title: 窗口基础类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.4
 * @version 1.0
 */
public class DFrameBase extends JFrame implements WindowListener,ComponentListener
{
    /**
     * 设置窗体类
     */
    DFrame frame;
    /**
     * 构造器
     */
    public DFrameBase(DFrame frame)
    {
        if(frame == null)
            return;
        //设置窗口位置
        setLocation(frame.getX(),frame.getY());
        //设置窗口尺寸
        setSize(frame.getWidth(),frame.getHeight());
        //设置窗体类
        this.frame = frame;
        //设置点击叉关闭窗口父类什么也不做
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //增加窗口监听
        addWindowListener(this);
        //增加组件监听
        addComponentListener(this);
        //去掉布局管理器
        //getContentPane().setLayout(null);
        setTitle(frame.getTitle());
    }
    /**
     * 窗口激活事件
     * @param e WindowEvent
     */
    public void windowActivated(WindowEvent e)
    {
    }
    /**
     * 窗口关闭事件
     * @param e WindowEvent
     */
    public void windowClosed(WindowEvent e)
    {
    }
    /**
     * 窗口将要关闭事件
     * @param e WindowEvent
     */
    public void windowClosing(WindowEvent e)
    {
        frame.close();
    }
    /**
     * 窗口失去交点事件
     * @param e WindowEvent
     */
    public void windowDeactivated(WindowEvent e)
    {
    }
    /**
     * 窗口隐含事件
     * @param e WindowEvent
     */
    public void windowDeiconified(WindowEvent e)
    {
    }
    /**
     * 窗口恢复事件
     * @param e WindowEvent
     */
    public void windowIconified(WindowEvent e)
    {
    }
    /**
     * 窗口打开事件
     * @param e WindowEvent
     */
    public void windowOpened(WindowEvent e)
    {
    }
    /**
     * 组件隐藏事件
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e)
    {
    }
    /**
     * 组件移动事件
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e)
    {
        //事件传递
        frame.onWindowMoved();
    }
    /**
     * 组件尺寸改变事件
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        //事件传递
        frame.onWindowResized();
    }
    /**
     * 组件显示事件
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
    }

}
