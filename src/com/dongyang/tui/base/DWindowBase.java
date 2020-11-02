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
 * <p>Title: ���ڻ�����</p>
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
     * ���ô�����
     */
    DWindow window;
    /**
     * ������
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
        //���ô���λ��
        setLocation(window.getX(),window.getY());
        //���ô��ڳߴ�
        setSize(window.getWidth(),window.getHeight());
        //���ô�����
        setWindow(window);
        //���Ӵ��ڼ���
        addWindowListener(this);
        //�����������
        addComponentListener(this);
        //ȥ�����ֹ�����
        //getContentPane().setLayout(null);
    }
    /**
     * ���ô���
     * @param window DWindow
     */
    public void setWindow(DWindow window)
    {
        this.window = window;
    }
    /**
     * �õ�����
     * @return DWindow
     */
    public DWindow getWindow()
    {
        return window;
    }
    /**
     * ���ڼ����¼�
     * @param e WindowEvent
     */
    public void windowActivated(WindowEvent e)
    {
    }
    /**
     * ���ڹر��¼�
     * @param e WindowEvent
     */
    public void windowClosed(WindowEvent e)
    {
    }
    /**
     * ���ڽ�Ҫ�ر��¼�
     * @param e WindowEvent
     */
    public void windowClosing(WindowEvent e)
    {
        window.close();
    }
    /**
     * ����ʧȥ�����¼�
     * @param e WindowEvent
     */
    public void windowDeactivated(WindowEvent e)
    {
    }
    /**
     * ���������¼�
     * @param e WindowEvent
     */
    public void windowDeiconified(WindowEvent e)
    {
    }
    /**
     * ���ڻָ��¼�
     * @param e WindowEvent
     */
    public void windowIconified(WindowEvent e)
    {
    }
    /**
     * ���ڴ��¼�
     * @param e WindowEvent
     */
    public void windowOpened(WindowEvent e)
    {
    }
    /**
     * ��������¼�
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e)
    {
    }
    /**
     * ����ƶ��¼�
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e)
    {
        window.setWindowSize();
    }
    /**
     * ����ߴ�ı��¼�
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        window.setWindowSize();
    }
    /**
     * �����ʾ�¼�
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
    }
}
