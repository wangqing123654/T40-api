package com.dongyang.tui;

import com.dongyang.tui.base.DWindowBase;
import com.dongyang.tui.control.DWindowControl;
import com.dongyang.tui.control.DComponentControl;
import com.dongyang.ui.base.TCBase;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.JFrame;

/**
 *
 * <p>Title: ����</p>
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
public class DWindow extends DComponent
{
    /**
     * ��������
     */
    private DWindowBase windowBase;
    /**
     * �ذ�����
     */
    private TCBase tcBase;
    /**
     * ������
     */
    public DWindow()
    {
        setBounds(10, 10, 500, 500);
        setBKColor(1,255,255);
        //setBorder("���");
        setTag("DWindow");
        //setText("aaaaa");
        setHorizontalAlignment("��");
        setVerticalAlignment("��");
        //����Ϊ��������
        setTopComponent(true);
        //������
        addPublicMethod("close",this);
    }
    /**
     * �õ�����ӿ�
     * @return DWindowControl
     */
    public DWindowControl getIOWindow()
    {
        DComponentControl control = getIOControlObject();
        if(control == null || !(control instanceof DWindowControl))
            return null;
        return (DWindowControl)control;
    }
    /**
     * �Ƿ��������ߴ�
     * @return boolean true �ڼ��� false ���ټ���
     */
    public boolean isListenerParentResized()
    {
        return true;
    }
    /**
     * �õ�����ߴ�
     * @return DRectangle
     */
    public DRectangle getComponentBounds()
    {
        if(windowBase != null)
        {
            Rectangle r = tcBase.getBounds();
            Insets insetsBase = tcBase.getInsets();
            return new DRectangle(0,0,r.width - insetsBase.left - insetsBase.right,
                                  r.height - insetsBase.top - insetsBase.bottom);
        }
        return getBounds();
    }
    /**
     * �õ����ڳߴ�
     */
    public void setWindowSize()
    {
        if(windowBase == null)
            return;
        getBounds().setBounds(windowBase.getBounds());
    }
    /**
     * ��ʾ
     */
    public void open(Frame frame)
    {
        initSystemUIManager();
        if(tcBase == null)
        {
            tcBase = new TCBase();
            tcBase.onInit();
            tcBase.addDComponent(this);
        }
        if(windowBase == null)
        {
            windowBase = new DWindowBase(frame,this);
            windowBase.getContentPane().add(tcBase);
        }else
        {
            //���ô���λ��
            windowBase.setLocation(getX(),getY());
            //���ô��ڳߴ�
            windowBase.setSize(getWidth(),getHeight());
        }
        String bkColor = getBKColor();
        if(bkColor != null && bkColor.length() > 0)
        {
            Color color = DColor.getColor(bkColor);
            tcBase.setBackground(color);
            windowBase.setBackground(color);
        }
        //���¼�
        if(!onOpen())
            return;
        windowBase.setVisible(isVisible());
        tcBase.grabFocus();
    }
    /**
     * ���¼�
     * @return boolean
     */
    public boolean onOpen()
    {
        DWindowControl control = getIOWindow();
        if(control != null)
            return control.onOpen();
        return true;
    }
    /**
     * �رն���
     */
    public void close()
    {
        if(!onClose())
            return;
        windowBase.dispose();
        //�ر�֮���¼�
        onClosed();
    }
    /**
     * �ر��¼�
     * @return boolean
     */
    public boolean onClose()
    {
        DWindowControl control = getIOWindow();
        if(control != null)
            return control.onClose();
        return true;
    }
    /**
     * �ر�֮���¼�
     */
    public void onClosed()
    {
        DWindowControl control = getIOWindow();
        if(control != null)
            control.onClosed();
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DWindow>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
    }
    /**
     * �ܹ��õ�����
     * @return boolean
     */
    public boolean canFocus()
    {
        return isEnabled();
    }
    /**
     * �õ���Ļ����
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        if(windowBase == null)
            return super.getScreenPoint();
        return new DPoint(getX(),getY());
    }
    /**
     * �������
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        return false;
    }
    public static void main(String args[])
    {
        com.dongyang.util.TDebug.initClient();
        JFrame frame = new JFrame();
        DWindow f = new DWindow();
        //f.setUIEditStatus(true);
        f.open(null);
    }
}
