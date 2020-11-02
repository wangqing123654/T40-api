package com.dongyang.tui.text.graph;

import java.awt.Color;
import com.dongyang.tui.text.EPicData;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;

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
public class Oval
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
     * �к�
     */
    private int column;
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
     * �и�ߴ�
     */
    private int cutSize = 10;
    /**
     * ������
     */
    public Oval()
    {

    }
    /**
     * ������
     * @param parent EPicData
     */
    public Oval(EPicData parent)
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
     * �����к�
     * @param column int
     */
    public void setColumn(int column)
    {
        this.column = column;
    }
    /**
     * �õ��к�
     * @return int
     */
    public int getColumn()
    {
        return column;
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
     * �����и�ߴ�
     * @param cutSize int
     */
    public void setCutSize(int cutSize)
    {
        this.cutSize = cutSize;
    }
    /**
     * �õ��и�ߴ�
     * @return int
     */
    public int getCutSize()
    {
        return cutSize;
    }
    /**
     * ����Ĭ����ɫ
     */
    public void initColor()
    {
        switch(getColumn())
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
     * �õ���ֵ
     * @param row int
     * @return double
     */
    public double getDataSum(int row)
    {
        return getParent().getDataSum(row);
    }
    /**
     * �õ���ֵ
     * @param row int
     * @param column int
     * @return double
     */
    public double getDataSum(int row,int column)
    {
        return getParent().getDataSum(row,column);
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
     * ������Ӱ
     * @param g Graphics
     */
    public void paintShadow(Graphics g)
    {
        if (!isShadow())
            return;
        int ss = getShadowOffset();
        int w = getWidth() - ss;
        int h = getHeight() - ss;
        int x0 = w / 2 + getX() + ss;
        int y0 = h / 2 + getY() + ss;
        int l = Math.min(w,h);
        int r = l / 2;
        g.setColor(getShadowColor());
        double sum = getDataSum(0);
        int r1 = (int)(getDataSum(0,getColumn()) / sum * 360.0 + 0.99);
        int r2 = (int)(getData(0,getColumn()) / sum * 360.0 + 0.99);
        if(getCutSize() > 0)
        {
            int r3 = r1 + r2 / 2;
            int x1 = (int)(Math.cos(Math.toRadians(r3)) * (double)getCutSize() + 0.5);
            int y1 = (int)(Math.sin(Math.toRadians(r3)) * (double)getCutSize() + 0.5);
            x0 += x1;
            y0 -= y1;
        }
        g.fillArc(x0 - r,y0 - r,l,l,r1,r2);
    }
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        int ss = 0;
        if (isShadow())
            ss = getShadowOffset();
        int w = getWidth() - ss;
        int h = getHeight() - ss;
        int x0 = w / 2 + getX();
        int y0 = h / 2 + getY();
        int l = Math.min(w,h);
        int r = l / 2;
        g.setColor(getColor());
        double sum = getDataSum(0);
        int r1 = (int)(getDataSum(0,getColumn()) / sum * 360.0 + 0.99);
        int r2 = (int)(getData(0,getColumn()) / sum * 360.0 + 0.99);
        int xp = 0;
        int yp = 0;
        if(getCutSize() > 0)
        {
            int r3 = r1 + r2 / 2;
            xp = (int)(Math.cos(Math.toRadians(r3)) * (double)getCutSize() + 0.5);
            yp = (int)(Math.sin(Math.toRadians(r3)) * (double)getCutSize() + 0.5);
        }
        x0 += xp;
        y0 -= yp;
        g.fillArc(x0 - r,y0 - r,l,l,r1,r2);

        //���Ʊ߿�
        ((Graphics2D)g).setStroke(getLineStroke());
        g.setColor(getLineColor());
        g.drawArc(x0 - r,y0 - r,l,l,r1,r2);

        int y1 = (int)(Math.sin(Math.toRadians(r1)) * (double)r + 0.5);
        int x1 = (int)(Math.cos(Math.toRadians(r1)) * (double)r + 0.5);
        g.drawLine(x0 + x1,y0 - y1,x0,y0);

        y1 = (int)(Math.sin(Math.toRadians(r1 + r2)) * (double)r + 0.5);
        x1 = (int)(Math.cos(Math.toRadians(r1 + r2)) * (double)r + 0.5);
        g.drawLine(x0 + x1,y0 - y1,x0,y0);

    }
}
