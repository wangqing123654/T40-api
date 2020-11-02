package com.dongyang.tui.text.graph;

import java.awt.Color;
import com.dongyang.tui.text.EPicData;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 *
 * <p>Title: 饼</p>
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
     * 父类
     */
    private EPicData parent;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 列号
     */
    private int column;
    /**
     * 线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int lineType = 0;
    /**
     * 线宽
     */
    private float lineWidth = 0.5f;
    /**
     * 虚线线间
     */
    private float lineWidth1 = 5.0f;
    /**
     * 线颜色
     */
    private Color lineColor = new Color(0,0,0);
    /**
     * 阴影
     */
    private boolean shadow = true;
    /**
     * 阴影颜色
     */
    private Color shadowColor = new Color(0,0,0);
    /**
     * 阴影偏移
     */
    private int shadowOffset = 3;
    /**
     * 切割尺寸
     */
    private int cutSize = 10;
    /**
     * 构造器
     */
    public Oval()
    {

    }
    /**
     * 构造器
     * @param parent EPicData
     */
    public Oval(EPicData parent)
    {
        setParent(parent);
    }
    /**
     * 设置父类
     * @param parent EPicData
     */
    public void setParent(EPicData parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return EPicData
     */
    public EPicData getParent()
    {
        return parent;
    }
    /**
     * 得到X坐标
     * @return int
     */
    public int getX()
    {
        return getParent().getX();
    }
    /**
     * 得到Y坐标
     * @return int
     */
    public int getY()
    {
        return getParent().getY();
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return getParent().getWidth();
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return getParent().getHeight();
    }
    /**
     * 设置颜色
     * @param color Color
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    /**
     * 得到颜色
     * @return Color
     */
    public Color getColor()
    {
        return color;
    }
    /**
     * 设置列号
     * @param column int
     */
    public void setColumn(int column)
    {
        this.column = column;
    }
    /**
     * 得到列号
     * @return int
     */
    public int getColumn()
    {
        return column;
    }
    /**
     * 设置线类型
     * @param lineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setLineType(int lineType)
    {
        this.lineType = lineType;
    }
    /**
     * 得到线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getLineType()
    {
        return lineType;
    }
    /**
     * 设置线宽
     * @param lineWidth float
     */
    public void setLineWidth(float lineWidth)
    {
        this.lineWidth = lineWidth;
    }
    /**
     * 得到线宽
     * @return float
     */
    public float getLineWidth()
    {
        return lineWidth;
    }
    /**
     * 设置虚线线间
     * @param lineWidth1 float
     */
    public void setLineWidth1(float lineWidth1)
    {
        this.lineWidth1 = lineWidth1;
    }
    /**
     * 得到虚线线间
     * @return float
     */
    public float getLineWidth1()
    {
        return lineWidth1;
    }
    /**
     * 设置线颜色
     * @param lineColor Color
     */
    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
    }
    /**
     * 得到线颜色
     * @return Color
     */
    public Color getLineColor()
    {
        return lineColor;
    }
    /**
     * 设置阴影
     * @param shadow boolean
     */
    public void setShadow(boolean shadow)
    {
        this.shadow = shadow;
    }
    /**
     * 得到阴影
     * @return boolean
     */
    public boolean isShadow()
    {
        return shadow;
    }
    /**
     * 设置阴影颜色
     * @param shadowColor Color
     */
    public void setShadowColor(Color shadowColor)
    {
        this.shadowColor = shadowColor;
    }
    /**
     * 得到阴影颜色
     * @return Color
     */
    public Color getShadowColor()
    {
        return shadowColor;
    }
    /**
     * 设置阴影偏移
     * @param shadowOffset int
     */
    public void setShadowOffset(int shadowOffset)
    {
        this.shadowOffset = shadowOffset;
    }
    /**
     * 得到阴影偏移
     * @return int
     */
    public int getShadowOffset()
    {
        return shadowOffset;
    }
    /**
     * 设置切割尺寸
     * @param cutSize int
     */
    public void setCutSize(int cutSize)
    {
        this.cutSize = cutSize;
    }
    /**
     * 得到切割尺寸
     * @return int
     */
    public int getCutSize()
    {
        return cutSize;
    }
    /**
     * 设置默认颜色
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
     * 得到总值
     * @param row int
     * @return double
     */
    public double getDataSum(int row)
    {
        return getParent().getDataSum(row);
    }
    /**
     * 得到总值
     * @param row int
     * @param column int
     * @return double
     */
    public double getDataSum(int row,int column)
    {
        return getParent().getDataSum(row,column);
    }
    /**
     * 得到数据
     * @param row int
     * @param column int
     * @return double
     */
    public double getData(int row,int column)
    {
        return getParent().getData(row,column);
    }
    /**
     * 得到线类型对象
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
     * 绘制阴影
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
     * 绘制
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

        //绘制边框
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
