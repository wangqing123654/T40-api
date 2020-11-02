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
 * @author lzk 2009.5.27
 * @version 1.0
 */
public class Point
{
    /**
     * ����
     */
    private EPicData parent;
    /**
     * ��ʾ
     */
    private boolean lineVisible = true;
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
    private float lineWidth1 = 0.5f;
    /**
     * ����ɫ
     */
    private Color lineColor = new Color(0,0,0);
    /**
     * ��ʾ�̶�
     */
    private boolean pointVisible = true;
    /**
     * ��ʽ
     */
    private int pointType = 1;
    /**
     * ǰ����ɫ
     */
    private Color foreground = new Color(255,0,0);
    /**
     * ������ɫ
     */
    private Color background;
    /**
     * �ߴ�
     */
    private int pointSize = 5;
    /**
     * �߿�������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int pointLineType = 0;
    /**
     * �߿��߿�
     */
    private float pointLineWidth = 2.5f;
    /**
     * �߿������߼�
     */
    private float pointLineWidth1 = 5.0f;
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
    public Point()
    {

    }
    /**
     * ������
     * @param parent EPicData
     */
    public Point(EPicData parent)
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
     * ������ʾ��
     * @param lineVisible boolean
     */
    public void setLineVisible(boolean lineVisible)
    {
        this.lineVisible = lineVisible;
    }
    /**
     * �Ƿ���ʾ��
     * @return boolean
     */
    public boolean isLineVisible()
    {
        return lineVisible;
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
     * ������ʾ�̶�
     * @param pointVisible boolean
     */
    public void setPointVisible(boolean pointVisible)
    {
        this.pointVisible = pointVisible;
    }
    /**
     * �õ���ʾ�̶�
     * @return boolean
     */
    public boolean isPointVisible()
    {
        return pointVisible;
    }
    /**
     * ������ʽ
     * @param pointType int
     */
    public void setPointType(int pointType)
    {
        this.pointType = pointType;
    }
    /**
     * �õ���ʽ
     * @return int
     */
    public int getPointType()
    {
        return pointType;
    }
    /**
     * ����ǰ����ɫ
     * @param foreground Color
     */
    public void setForeground(Color foreground)
    {
        this.foreground = foreground;
    }
    /**
     * �õ�ǰ����ɫ
     * @return Color
     */
    public Color getForeground()
    {
        return foreground;
    }
    /**
     * ���ñ�����ɫ
     * @param background Color
     */
    public void setBackground(Color background)
    {
        this.background = background;
    }
    /**
     * �õ�������ɫ
     * @return Color
     */
    public Color getBackground()
    {
        return background;
    }
    /**
     * Ĭ����ɫ
     */
    public void initBackground()
    {
        switch(getRow())
        {
        case 0:
            setBackground(new Color(0,0,128));
            setLineColor(new Color(0,0,128));
            break;
        case 1:
            setBackground(new Color(255,0,255));
            setLineColor(new Color(255,0,255));
            break;
        case 2:
            setBackground(new Color(255,255,0));
            setLineColor(new Color(255,255,0));
            break;
        case 3:
            setBackground(new Color(0,255,255));
            setLineColor(new Color(0,255,255));
            break;
        case 4:
            setBackground(new Color(128,0,128));
            setLineColor(new Color(128,0,128));
            break;
        case 5:
            setBackground(new Color(128,0,0));
            setLineColor(new Color(128,0,0));
            break;
        case 6:
            setBackground(new Color(0,128,128));
            setLineColor(new Color(0,128,128));
            break;
        default:
            setBackground(new Color(0,0,255));
            setLineColor(new Color(0,0,255));
            break;
        }
        setPointType(getRow() % 5 + 1);
    }
    /**
     * ���óߴ�
     * @param pointSize int
     */
    public void setPointSize(int pointSize)
    {
        this.pointSize = pointSize;
    }
    /**
     * �õ��ߴ�
     * @return int
     */
    public int getPointSize()
    {
        return pointSize;
    }
    /**
     * ���ñ߿�������
     * @param pointLineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setPointLineType(int pointLineType)
    {
        this.pointLineType = pointLineType;
    }
    /**
     * �õ��߿�������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getPointLineType()
    {
        return pointLineType;
    }
    /**
     * ���ñ߿��߿�
     * @param pointLineWidth float
     */
    public void setPointLineWidth(float pointLineWidth)
    {
        this.pointLineWidth = pointLineWidth;
    }
    /**
     * �õ��߿��߿�
     * @return float
     */
    public float getPointLineWidth()
    {
        return pointLineWidth;
    }
    /**
     * ���ñ߿������߼�
     * @param pointLineWidth1 float
     */
    public void setPointLineWidth1(float pointLineWidth1)
    {
        this.pointLineWidth1 = pointLineWidth1;
    }
    /**
     * �õ��߿������߼�
     * @return float
     */
    public float getPointLineWidth1()
    {
        return pointLineWidth1;
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
     * �õ�����
     * @return int
     */
    public int getColumnCount()
    {
        return getParent().getColumnCount();
    }
    /**
     * �õ�������ƫ���� X
     * @param index int
     * @return int
     */
    public int getTypeAxisX(int index)
    {
        return getParent().getTypeAxisX(index);
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
     * @param shadow boolean
     */
    public void setShadow(boolean shadow)
    {
        this.shadow = shadow;
    }
    /**
     * �Ƿ���Ӱ
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
     * ����
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int width,int height)
    {
        int count = getColumnCount();
        int x0 = 0;
        int y0 = 0;
        for(int i = 0;i < count;i++)
        {
            double value = getData(getRow(),i);
            int x = getTypeAxisX(i);
            int y = getValueY(value);
            //������
            if(isLineVisible() && i > 0)
                paintLine(g,x0,y0,x,y);
            //���Ƶ�
            if(isPointVisible())
                paintPoint(g,x,y);
            x0 = x;
            y0 = y;
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
     * ������
     * @param g Graphics
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     */
    public void paintLine(Graphics g,int x1,int y1,int x2,int y2)
    {
        if(getLineColor() == null)
            return;
        g.setColor(getLineColor());
        ((Graphics2D)g).setStroke(getLineStroke());
        g.drawLine(x1,y1,x2,y2);
    }
    /**
     * �õ���������Ͷ���
     * @return Stroke
     */
    private Stroke getPointLineStroke()
    {
        switch(this.getPointLineType())
        {
        case 1:
            return new BasicStroke(getPointLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getPointLineWidth1()}, 0.0f);
        case 2:
            return new BasicStroke(getPointLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getPointLineWidth1(),2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getPointLineWidth());
    }
    /**
     * ���Ƶ�
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void paintPoint(Graphics g,int x,int y)
    {
        if(isShadow() && getShadowColor() != null)
        {
            g.setColor(getShadowColor());
            int size = getPointSize();
            int ss = getShadowOffset();
            switch (getPointType())
            {
            case 1:
                g.fillRect(x - size + ss, y - size + ss, size * 2, size * 2);
                break;
            case 2:
                g.fillPolygon(new int[]{x - size + ss,x + ss,x + size + ss,x + ss},
                              new int[]{y + ss,y - size + ss,y + ss,y + size + ss},4);
                break;
            case 3:
                g.fillPolygon(new int[]{x + ss,x + size + ss,x - size + ss},
                              new int[]{y - size + ss,y + size + ss,y + size + ss},3);
                break;
            case 4:
                g.fillOval(x - size + ss, y - size + ss, size * 2, size * 2);
                break;
            case 5:
                g.drawLine(x - size + ss,y + ss,x + size + ss,y + ss);
                break;
            }
        }
        if(getBackground() != null)
        {
            g.setColor(getBackground());
            int size = getPointSize();
            switch (getPointType())
            {
            case 1:
                g.fillRect(x - size, y - size, size * 2, size * 2);
                break;
            case 2:
                g.fillPolygon(new int[]{x - size,x,x + size,x},
                              new int[]{y,y - size,y,y + size},4);
                break;
            case 3:
                g.fillPolygon(new int[]{x,x + size,x - size},
                              new int[]{y - size,y + size,y + size},3);
                break;
            case 4:
                g.fillOval(x - size, y - size, size * 2, size * 2);
                break;
            case 5:
                g.drawLine(x - size,y,x + size,y);
                break;
            }
        }
        if(getForeground() != null)
        {
            ((Graphics2D)g).setStroke(getPointLineStroke());
            g.setColor(getForeground());
            int size = getPointSize();
            switch (getPointType())
            {
            case 1:
                g.drawRect(x - size, y - size, size * 2 - 1, size * 2 - 1);
                break;
            case 2:
                g.drawPolygon(new int[]{x - size,x,x + size,x},
                              new int[]{y,y - size,y,y + size},4);
                break;
            case 3:
                g.drawPolygon(new int[]{x,x + size,x - size},
                              new int[]{y - size,y + size,y + size},3);
                break;
            case 4:
                g.drawOval(x - size, y - size, size * 2, size * 2);
                break;
            case 5:
                g.drawLine(x - size,y - 1,x + size,y - 1);
                break;
            }
        }
    }
}
