package com.dongyang.tui.base;

import javax.swing.JWindow;
import com.dongyang.tui.DWindow;
import java.awt.event.WindowListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.Frame;
/**
 *
 * <p>Title: 窗口基础类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.24
 * @version 1.0
 */
public class DWindowBase extends JWindow implements WindowListener,ComponentListener
{
    /**
     * 设置窗体类
     */
    DWindow window;
    /**
     * 构造器
     * @param window DWindow
     */
    public DWindowBase(DWindow window)
    {
        this(getFrame(),window);
    }
    public static Frame getFrame()
    {
        Frame frame = new Frame();
        frame.setVisible(true);
        return frame;
    }
    public DWindowBase(Frame owner,DWindow window)
    {
        super(owner);
        if(window == null)
            return;
        //设置窗口位置
        setLocation(window.getX(),window.getY());
        //设置窗口尺寸
        setSize(window.getWidth(),window.getHeight());
        //设置窗体类
        setWindow(window);
        //增加窗口监听
        addWindowListener(this);
        //增加组件监听
        addComponentListener(this);
        //去掉布局管理器
        //getContentPane().setLayout(null);
    }
    /**
     * 设置窗口
     * @param window DWindow
     */
    public void setWindow(DWindow window)
    {
        this.window = window;
    }
    /**
     * 得到窗口
     * @return DWindow
     */
    public DWindow getWindow()
    {
        return window;
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
        window.close();
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
        window.setWindowSize();
    }
    /**
     * 组件尺寸改变事件
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        window.setWindowSize();
    }
    /**
     * 组件显示事件
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
    }
}
