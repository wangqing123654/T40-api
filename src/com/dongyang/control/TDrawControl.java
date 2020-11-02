package com.dongyang.control;

import com.dongyang.ui.TComponent;
import java.awt.Graphics;

/**
 *
 * <p>Title: ��ͼ������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.7.2
 * @version 1.0
 */
public class TDrawControl
{
    /**
     * �����
     */
    public TComponent parent;
    /**
     * ��ʼ��
     */
    public TDrawControl()
    {

    }
    /**
     * ���ø����
     * @param parent TComponent
     */
    public void setParent(TComponent parent)
    {
        this.parent = parent;
    }
    /**
     * �õ������
     * @return TComponent
     */
    public TComponent getParent()
    {
        return parent;
    }
    /**
     * �õ����������
     * @return TControl
     */
    public TControl getControl()
    {
        if(getParent() == null)
            return null;
        return (TControl)getParent().callFunction("getControl");
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        if(getParent() == null)
            return 0;
        return (Integer)getParent().callFunction("getWidth");
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        if(getParent() == null)
            return 0;
        return (Integer)getParent().callFunction("getHeight");
    }
    /**
     * ���Ʊ���
     * @param g Graphics
     */
    public void paintBackground(Graphics g)
    {

    }
    /**
     * ����ǰ��
     * @param g Graphics
     */
    public void paintForeground(Graphics g)
    {

    }
}
