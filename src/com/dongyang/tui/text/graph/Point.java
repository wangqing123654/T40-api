package com.dongyang.tui.text.graph;

import com.dongyang.tui.text.EPicData;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Graphics2D;

/**
 *
 * <p>Title: 点</p>
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
     * 父类
     */
    private EPicData parent;
    /**
     * 显示
     */
    private boolean lineVisible = true;
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
    private float lineWidth1 = 0.5f;
    /**
     * 线颜色
     */
    private Color lineColor = new Color(0,0,0);
    /**
     * 显示刻度
     */
    private boolean pointVisible = true;
    /**
     * 样式
     */
    private int pointType = 1;
    /**
     * 前景颜色
     */
    private Color foreground = new Color(255,0,0);
    /**
     * 背景颜色
     */
    private Color background;
    /**
     * 尺寸
     */
    private int pointSize = 5;
    /**
     * 边框线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int pointLineType = 0;
    /**
     * 边框线宽
     */
    private float pointLineWidth = 2.5f;
    /**
     * 边框虚线线间
     */
    private float pointLineWidth1 = 5.0f;
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
    public Point()
    {

    }
    /**
     * 构造器
     * @param parent EPicData
     */
    public Point(EPicData parent)
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
     * 设置显示线
     * @param lineVisible boolean
     */
    public void setLineVisible(boolean lineVisible)
    {
        this.lineVisible = lineVisible;
    }
    /**
     * 是否显示线
     * @return boolean
     */
    public boolean isLineVisible()
    {
        return lineVisible;
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
     * 设置显示刻度
     * @param pointVisible boolean
     */
    public void setPointVisible(boolean pointVisible)
    {
        this.pointVisible = pointVisible;
    }
    /**
     * 得到显示刻度
     * @return boolean
     */
    public boolean isPointVisible()
    {
        return pointVisible;
    }
    /**
     * 设置样式
     * @param pointType int
     */
    public void setPointType(int pointType)
    {
        this.pointType = pointType;
    }
    /**
     * 得到样式
     * @return int
     */
    public int getPointType()
    {
        return pointType;
    }
    /**
     * 设置前景颜色
     * @param foreground Color
     */
    public void setForeground(Color foreground)
    {
        this.foreground = foreground;
    }
    /**
     * 得到前景颜色
     * @return Color
     */
    public Color getForeground()
    {
        return foreground;
    }
    /**
     * 设置背景颜色
     * @param background Color
     */
    public void setBackground(Color background)
    {
        this.background = background;
    }
    /**
     * 得到背景颜色
     * @return Color
     */
    public Color getBackground()
    {
        return background;
    }
    /**
     * 默认颜色
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
     * 设置尺寸
     * @param pointSize int
     */
    public void setPointSize(int pointSize)
    {
        this.pointSize = pointSize;
    }
    /**
     * 得到尺寸
     * @return int
     */
    public int getPointSize()
    {
        return pointSize;
    }
    /**
     * 设置边框线类型
     * @param pointLineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setPointLineType(int pointLineType)
    {
        this.pointLineType = pointLineType;
    }
    /**
     * 得到边框线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getPointLineType()
    {
        return pointLineType;
    }
    /**
     * 设置边框线宽
     * @param pointLineWidth float
     */
    public void setPointLineWidth(float pointLineWidth)
    {
        this.pointLineWidth = pointLineWidth;
    }
    /**
     * 得到边框线宽
     * @return float
     */
    public float getPointLineWidth()
    {
        return pointLineWidth;
    }
    /**
     * 设置边框虚线线间
     * @param pointLineWidth1 float
     */
    public void setPointLineWidth1(float pointLineWidth1)
    {
        this.pointLineWidth1 = pointLineWidth1;
    }
    /**
     * 得到边框虚线线间
     * @return float
     */
    public float getPointLineWidth1()
    {
        return pointLineWidth1;
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
     * 得到列数
     * @return int
     */
    public int getColumnCount()
    {
        return getParent().getColumnCount();
    }
    /**
     * 得到分类轴偏移量 X
     * @param index int
     * @return int
     */
    public int getTypeAxisX(int index)
    {
        return getParent().getTypeAxisX(index);
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
     * 设置阴影
     * @param shadow boolean
     */
    public void setShadow(boolean shadow)
    {
        this.shadow = shadow;
    }
    /**
     * 是否阴影
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
     * 绘制
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
            //绘制线
            if(isLineVisible() && i > 0)
                paintLine(g,x0,y0,x,y);
            //绘制点
            if(isPointVisible())
                paintPoint(g,x,y);
            x0 = x;
            y0 = y;
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
     * 绘制线
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
     * 得到点边线类型对象
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
     * 绘制点
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
