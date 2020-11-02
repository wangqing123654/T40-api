package com.dongyang.tui.text.graph;

import com.dongyang.tui.text.EPicData;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Graphics2D;

/**
 *
 * <p>Title: 柱</p>
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
     * 父类
     */
    private EPicData parent;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 尺寸
     */
    private int size = 15;
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
     * 行号
     */
    private int row;

    /**
     * 构造器
     */
    public Rect()
    {

    }
    /**
     * 构造器
     * @param parent EPicData
     */
    public Rect(EPicData parent)
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
     * 设置尺寸
     * @param size int
     */
    public void setSize(int size)
    {
        this.size = size;
    }
    /**
     * 得到尺寸
     * @return int
     */
    public int getSize()
    {
        return size;
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
     * 设置行号
     * @param row int
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    /**
     * 得到行号
     * @return int
     */
    public int getRow()
    {
        return row;
    }
    /**
     * 设置默认颜色
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
     * 得到列数
     * @return int
     */
    public int getColumnCount()
    {
        return getParent().getColumnCount();
    }
    /**
     * 得到分类轴偏移量(中间) X
     * @param index int
     * @return int
     */
    public int getTypeAxisCenterX(int index)
    {
        return getParent().getTypeAxisCenterX(index);
    }
    /**
     * 计算数值高度
     * @param value double
     * @return int
     */
    public int getValueY(double value)
    {
        return getParent().getValueY(value);
    }
    /**
     * 得到总尺寸
     * @return int
     */
    public int getRectSize()
    {
        return getParent().getRectSize();
    }
    /**
     * 得到合计尺寸
     * @return int
     */
    public int getRectSizeSum()
    {
        return getParent().getRectSizeSum(getRow());
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
     * 绘制阴影
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
     * 绘制
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
            //绘制底色
            g.setColor(getColor());
            g.fillRect(x,y,getSize(),h);
            //绘制边框
            ((Graphics2D)g).setStroke(getLineStroke());
            g.setColor(getLineColor());
            g.drawRect(x,y,getSize(),h);
        }
    }
}
