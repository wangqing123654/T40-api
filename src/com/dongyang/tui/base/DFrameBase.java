package com.dongyang.tui.base;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import com.dongyang.tui.DFrame;

/**
 * <p>Title: ���ڻ�����</p>
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
     * ���ô�����
     */
    DFrame frame;
    /**
     * ������
     */
    public DFrameBase(DFrame frame)
    {
        if(frame == null)
            return;
        //���ô���λ��
        setLocation(frame.getX(),frame.getY());
        //���ô��ڳߴ�
        setSize(frame.getWidth(),frame.getHeight());
        //���ô�����
        this.frame = frame;
        //���õ����رմ��ڸ���ʲôҲ����
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //���Ӵ��ڼ���
        addWindowListener(this);
        //�����������
        addComponentListener(this);
        //ȥ�����ֹ�����
        //getContentPane().setLayout(null);
        setTitle(frame.getTitle());
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
        frame.close();
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
        //�¼�����
        frame.onWindowMoved();
    }
    /**
     * ����ߴ�ı��¼�
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e)
    {
        //�¼�����
        frame.onWindowResized();
    }
    /**
     * �����ʾ�¼�
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e)
    {
    }

}
