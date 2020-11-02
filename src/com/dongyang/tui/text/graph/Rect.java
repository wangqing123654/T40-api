package com.dongyang.tui.text.graph;

import com.dongyang.tui.text.EPicData;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Graphics2D;

/**
 *
 * <p>Title: ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.31
 * @version 1.0
 */
public class Rect
{
    /**
     * ����
     */
    private EPicData parent;
    /**
     * ��ɫ
     */
    private Color color;
    /**
     * �ߴ�
     */
    private int size = 15;
    /**
     * ������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int lineType = 0;
    /**
     * �߿�
     */
    private float lineWidth = 0.5f;
    /**
     * �����߼�
     */
    private float lineWidth1 = 5.0f;
    /**
     * ����ɫ
     */
    private Color lineColor = new Color(0,0,0);
    /**
     * ��Ӱ
     */
    private boolean shadow = true;
    /**
     * ��Ӱ��ɫ
     */
    private Color shadowColor = new Color(0,0,0);
    /**
     * ��Ӱƫ��
     */
    private int shadowOffset = 3;
    /**
     * �к�
     */
    private int row;

    /**
     * ������
     */
    public Rect()
    {

    }
    /**
     * ������
     * @param parent EPicData
     */
    public Rect(EPicData parent)
    {
        setParent(parent);
    }
    /**
     * ���ø���
     * @param parent EPicData
     */
    public void setParent(EPicData parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return EPicData
     */
    public EPicData getParent()
    {
        return parent;
    }
    /**
     * �õ�X����
     * @return int
     */
    public int getX()
    {
        return getParent().getX();
    }
    /**
     * �õ�Y����
     * @return int
     */
    public int getY()
    {
        return getParent().getY();
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return getParent().getWidth();
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return getParent().getHeight();
    }
    /**
     * ������ɫ
     * @param color Color
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    /**
     * �õ���ɫ
     * @return Color
     */
    public Color getColor()
    {
        return color;
    }
    /**
     * ���óߴ�
     * @param size int
     */
    public void setSize(int size)
    {
        this.size = size;
    }
    /**
     * �õ��ߴ�
     * @return int
     */
    public int getSize()
    {
        return size;
    }
    /**
     * ����������
     * @param lineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setLineType(int lineType)
    {
        this.lineType = lineType;
    }
    /**
     * �õ�������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getLineType()
    {
        return lineType;
    }
    /**
     * �����߿�
     * @param lineWidth float
     */
    public void setLineWidth(float lineWidth)
    {
        this.lineWidth = lineWidth;
    }
    /**
     * �õ��߿�
     * @return float
     */
    public float getLineWidth()
    {
        return lineWidth;
    }
    /**
     * ���������߼�
     * @param lineWidth1 float
     */
    public void setLineWidth1(float lineWidth1)
    {
        this.lineWidth1 = lineWidth1;
    }
    /**
     * �õ������߼�
     * @return float
     */
    public float getLineWidth1()
    {
        return lineWidth1;
    }
    /**
     * ��������ɫ
     * @param lineColor Color
     */
    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
    }
    /**
     * �õ�����ɫ
     * @return Color
     */
    public Color getLineColor()
    {
        return lineColor;
    }
    /**
     * ������Ӱ
     * @param shadow boolean
     */
    public void setShadow(boolean shadow)
    {
        this.shadow = shadow;
    }
    /**
     * �õ���Ӱ
     * @return boolean
     */
    public boolean isShadow()
    {
        return shadow;
    }
    /**
     * ������Ӱ��ɫ
     * @param shadowColor Color
     */
    public void setShadowColor(Color shadowColor)
    {
        this.shadowColor = shadowColor;
    }
    /**
     * �õ���Ӱ��ɫ
     * @return Color
     */
    public Color getShadowColor()
    {
        return shadowColor;
    }
    /**
     * ������Ӱƫ��
     * @param shadowOffset int
     */
    public void setShadowOffset(int shadowOffset)
    {
        this.shadowOffset = shadowOffset;
    }
    /**
     * �õ���Ӱƫ��
     * @return int
     */
    public int getShadowOffset()
    {
        return shadowOffset;
    }
    /**
     * �����к�
     * @param row int
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    /**
     * �õ��к�
     * @return int
     */
    public int getRow()
    {
        return row;
    }
    /**
     * ����Ĭ����ɫ
     */
    public void initColor()
    {
        switch(getRow())
        {
        case 0:
            setColor(new Color(153,153,255));
            break;
        case 1:
            setColor(new Color(153,51,102));
            break;
        case 2:
            setColor(new Color(255,255,204));
            break;
        case 3:
            setColor(new Color(204,255,255));
            break;
        case 4:
            setColor(new Color(102,0,102));
            break;
        case 5:
            setColor(new Color(255,128,128));
            break;
        case 6:
            setColor(new Color(204,204,255));
            break;
        default:
            setColor(new Color(204,204,255));
            break;
        }
        setLineColor(new Color(0,0,0));
    }
    /**
     * �õ�����
     * @return int
     */
    public int getColumnCount()
    {
        return getParent().getColumnCount();
    }
    /**
     * �õ�������ƫ����(�м�) X
     * @param index int
     * @return int
     */
    public int getTypeAxisCenterX(int index)
    {
        return getParent().getTypeAxisCenterX(index);
    }
    /**
     * ������ֵ�߶�
     * @param value double
     * @return int
     */
    public int getValueY(double value)
    {
        return getParent().getValueY(value);
    }
    /**
     * �õ��ܳߴ�
     * @return int
     */
    public int getRectSize()
    {
        return getParent().getRectSize();
    }
    /**
     * �õ��ϼƳߴ�
     * @return int
     */
    public int getRectSizeSum()
    {
        return getParent().getRectSizeSum(getRow());
    }
    /**
     * �õ�����
     * @param row int
     * @param column int
     * @return double
     */
    public double getData(int row,int column)
    {
        return getParent().getData(row,column);
    }
    /**
     * ������Ӱ
     * @param g Graphics
     */
    public void paintShadow(Graphics g)
    {
        if(!isShadow())
            return;
        int count = getColumnCount();
        int offset = getRectSizeSum() - getRectSize() / 2;
        g.setColor(getShadowColor());
        for(int i = 0;i < count;i++)
        {
            double value = getData(getRow(), i);
            int x = getTypeAxisCenterX(i) + offset + getShadowOffset();
            int y = getValueY(value) + getShadowOffset();
            int h = getHeight() +  getY() - y;
            g.fillRect(x,y,getSize(),h);
        }
    }
    /**
     * �õ������Ͷ���
     * @return Stroke
     */
    private Stroke getLineStroke()
    {
        switch(getLineType())
        {
        case 1:
            return new BasicStroke(getLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getLineWidth1()}, 0.0f);
        case 2:
            return new BasicStroke(getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getLineWidth1(),2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getLineWidth());
    }
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        int count = getColumnCount();
        int offset = getRectSizeSum() - getRectSize() / 2;
        for(int i = 0;i < count;i++)
        {
            double value = getData(getRow(), i);
            int x = getTypeAxisCenterX(i) + offset;
            int y = getValueY(value);
            int h = getHeight() +  getY() - y;
            //���Ƶ�ɫ
            g.setColor(getColor());
            g.fillRect(x,y,getSize(),h);
            //���Ʊ߿�
            ((Graphics2D)g).setStroke(getLineStroke());
            g.setColor(getLineColor());
            g.drawRect(x,y,getSize(),h);
        }
    }
}
