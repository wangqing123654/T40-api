package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Graphics2D;
import com.dongyang.tui.text.graph.Axis;
import com.dongyang.tui.text.graph.Point;
import com.dongyang.tui.text.graph.Rect;
import com.dongyang.tui.text.graph.Oval;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 数据对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.26
 * @version 1.0
 */
public class EPicData
{
    /**
     * 父类
     */
    private EPic parent;
    private String column[];
    private String row[];
    private double data[][];
    private int x = 50;
    private int y = 50;
    private int width = 300;
    private int height = 200;
    /**
     * 点
     */
    private Point points[];
    /**
     * 柱
     */
    private Rect rects[];
    /**
     * 饼
     */
    private Oval ovals[];
    /**
     * 类型
     */
    private int type = 1;
    /**
     * 数值轴
     */
    private Axis numberAxis;
    /**
     * 分类轴
     */
    private Axis typeAxis;
    /**
     * 背景颜色
     */
    private Color background = new Color(168,168,168);
    /**
     * 构造器
     */
    public EPicData()
    {
        testData();
        //初始化
        init();
    }
    /**
     * 初始化
     */
    public void init()
    {
        setNumberAxis(new Axis(this));
        getNumberAxis().setWay(true);
        getNumberAxis().setNumberAxis(true);
        setTypeAxis(new Axis(this));
        getTypeAxis().setWay(false);
        getTypeAxis().setTextAutoEnter(false);
        getTypeAxis().setMaxValue(40);
        getTypeAxis().setTextCenter(true);
    }
    /**
     * 设置父类
     * @param parent EPic
     */
    public void setParent(EPic parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return EPic
     */
    public EPic getParent()
    {
        return parent;
    }
    /**
     * 设置列
     * @param column String[]
     */
    public void setColumns(String[] column)
    {
        this.column = column;
        //初始化饼
        initOvals();
    }
    /**
     * 得到列
     * @return String[]
     */
    public String[] getColumns()
    {
        return column;
    }
    /**
     * 设置行
     * @param row String[]
     */
    public void setRows(String[] row)
    {
        this.row = row;
        //初始化点
        initPoints();
        //初始化柱
        initRects();
    }
    /**
     * 得到行
     * @return String[]
     */
    public String[] getRows()
    {
        return row;
    }
    /**
     * 设置数据
     * @param data double[][]
     */
    public void setData(double[][] data)
    {
        this.data = data;
    }
    /**
     * 得到数据
     * @return double[][]
     */
    public double[][] getData()
    {
        if(getParent() == null)
            return data;
        Object obj = getParent().getPM().getFileManager().getParameter();
        if(obj == null || !(obj instanceof TParm))
            return data;
        TParm parm = (TParm)obj;
        if(!parm.existData(getParent().getName(),"DATA"))
            return data;
        return (double[][])parm.getData(getParent().getName(),"DATA");
    }
    /**
     * 得到行数
     * @return int
     */
    public int getRowCount()
    {
        if(row == null)
            return 0;
        return row.length;
    }
    /**
     * 得到列数
     * @return int
     */
    public int getColumnCount()
    {
        if(column == null)
            return 0;
        return column.length;
    }
    /**
     * 得到总值
     * @param row int
     * @return double
     */
    public double getDataSum(int row)
    {
        if(row < 0 || row >= getRowCount())
            return 0.0;
        double value = 0.0;
        for(int i = 0;i < getColumnCount();i++)
            value += getData(row,i);
        return value;
    }
    /**
     * 得到总值
     * @param row int
     * @param column int
     * @return double
     */
    public double getDataSum(int row,int column)
    {
        if(row < 0 || row >= getRowCount())
            return 0.0;
        double value = 0.0;
        for(int i = 0;i < column;i++)
            value += getData(row,i);
        return value;
    }
    /**
     * 分配数据区
     */
    public void newData()
    {
        if(getRowCount() <= 0 || getColumnCount() <= 0)
            return;
        data = new double[getRowCount()][getColumnCount()];
    }
    /**
     * 得到数据
     * @param row int
     * @param column int
     * @return double
     */
    public double getData(int row,int column)
    {
        if(getData().length <= row)
            return 0.0;
        if(getData()[row].length <= column)
            return 0.0;
        return getData()[row][column];
    }
    /**
     * 设置数据
     * @param row int
     * @param column int
     * @param d double
     */
    public void setData(int row,int column,double d)
    {
        data[row][column] = d;
    }
    /**
     * 设置类型
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
        //初始化线
        initPoints();
        //初始化柱
        initRects();
        //初始化饼
        initOvals();
    }
    /**
     * 得到类型
     * @return int
     */
    public int getType()
    {
        return type;
    }

    /**
     * 设置测试数据
     */
    public void testData()
    {
        setColumns(new String[]{"第一季度","第二季度","第三季度","第四季度"});
        setRows(new String[]{"东部","西部","北部"});
        setData(new double[][]{
                {20.4,27.4,90,20.4},
                {30.6,38.6,34.6,31.6},
                {45.9,46.9,45,43.9}});
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getX()
    {
        return x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getY()
    {
        return y;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public int getWidth()
    {
        return width;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }
    public int getHeight()
    {
        return height;
    }
    /**
     * 设置数值轴
     * @param numberAxis Axis
     */
    public void setNumberAxis(Axis numberAxis)
    {
        this.numberAxis = numberAxis;
    }
    /**
     * 得到数值轴
     * @return Axis
     */
    public Axis getNumberAxis()
    {
        return numberAxis;
    }
    /**
     * 设置分类轴
     * @param typeAxis Axis
     */
    public void setTypeAxis(Axis typeAxis)
    {
        this.typeAxis = typeAxis;
    }
    /**
     * 得到分类轴
     * @return Axis
     */
    public Axis getTypeAxis()
    {
        return typeAxis;
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
     * 得到背景颜色(String)
     * @return String
     */
    public String getBackgroundString()
    {
        if(background == null)
            return "";
        return background.getRed() + "," + background.getGreen() + "," + background.getBlue();
    }
    /**
     * 设置点
     * @param points Point[]
     */
    public void setPoints(Point[] points)
    {
        this.points = points;
    }
    /**
     * 初始化点
     */
    public void initPoints()
    {
        if(getType() != 1)
        {
            setPoints(null);
            return;
        }
        setPoints(new Point[getRowCount()]);
        for(int i = 0;i < getRowCount();i++)
        {
            Point point = new Point(this);
            //设置行号
            point.setRow(i);
            //设置默认颜色
            point.initBackground();
            getPoints()[i] = point;

        }
    }
    /**
     * 得到点
     * @return Point[]
     */
    public Point[] getPoints()
    {
        return points;
    }
    /**
     * 设置柱
     * @param rects Rect[]
     */
    public void setRects(Rect rects[])
    {
        this.rects = rects;
    }
    /**
     * 初始化点
     */
    public void initRects()
    {
        if(getType() != 2)
        {
            setRects(null);
            return;
        }
        setRects(new Rect[getRowCount()]);
        for(int i = 0;i < getRowCount();i++)
        {
            Rect rect = new Rect(this);
            //设置行号
            rect.setRow(i);
            //设置默认颜色
            rect.initColor();
            getRects()[i] = rect;
        }
    }
    /**
     * 得到柱
     * @return Rect[]
     */
    public Rect[] getRects()
    {
        return rects;
    }
    /**
     * 得到总尺寸
     * @return int
     */
    public int getRectSize()
    {
        if(getRects() == null)
            return 0;
        int size = 0;
        for(int i = 0;i < getRects().length;i++)
            size += getRects()[i].getSize();
        return size;
    }
    /**
     * 得到合计尺寸
     * @param index int
     * @return int
     */
    public int getRectSizeSum(int index)
    {
        if(getRects() == null || index < 0 || index >= getRects().length)
            return 0;
        int size = 0;
        for(int i = 0;i < index;i++)
            size += getRects()[i].getSize();
        return size;
    }
    /**
     * 设置饼
     * @param ovals Oval[]
     */
    public void setOvals(Oval ovals[])
    {
        this.ovals = ovals;
    }
    /**
     * 初始化点
     */
    public void initOvals()
    {
        if(getType() != 3)
        {
            setOvals(null);
            return;
        }
        setOvals(new Oval[getColumnCount()]);
        for(int i = 0;i < getColumnCount();i++)
        {
            Oval oval = new Oval(this);
            //设置列号
            oval.setColumn(i);
            //设置默认颜色
            oval.initColor();
            getOvals()[i] = oval;
        }
    }
    /**
     * 得到饼
     * @return Oval[]
     */
    public Oval[] getOvals()
    {
        return ovals;
    }
    /**
     * 得到分类轴偏移量 X
     * @param index int
     * @return int
     */
    public int getTypeAxisX(int index)
    {
        return getTypeAxis().getAxisX(index);
    }
    /**
     * 得到分类轴偏移量 Y
     * @param index int
     * @return int
     */
    public int getTypeAxisY(int index)
    {
        return getTypeAxis().getAxisY(index);
    }
    /**
     * 得到分类轴偏移量(中间) X
     * @param index int
     * @return int
     */
    public int getTypeAxisCenterX(int index)
    {
        return getTypeAxis().getAxisCenterX(index);
    }
    /**
     * 得到分类轴偏移量(中间) Y
     * @param index int
     * @return int
     */
    public int getTypeAxisCenterY(int index)
    {
        return getTypeAxis().getAxisCenterY(index);
    }
    /**
     * 计算数值宽度
     * @param value double
     * @return int
     */
    public int getValueX(double value)
    {
        return getNumberAxis().getValueX(value);
    }
    /**
     * 计算数值高度
     * @param value double
     * @return int
     */
    public int getValueY(double value)
    {
        return getNumberAxis().getValueY(value);
    }
    /**
     * 绘制
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int width,int height)
    {
        switch(getType())
        {
        case 0:
            return;
        case 1://线图
            paintPoint(g,width,height);
            return;
        case 2://柱图
            paintRect(g,width,height);
            return;
        case 3://饼图
            paintOval(g,width,height);
            return;
        }
    }
    /**
     * 线图
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintPoint(Graphics g,int width,int height)
    {
        //背景颜色
        if(getBackground() != null)
        {
            g.setColor(getBackground());
            g.fillRect(getX(),getY(),getWidth(),getHeight());
        }
        //绘制背景数值轴
        getNumberAxis().paintBackground(g);
        //绘制背景分类轴
        getTypeAxis().paintBackground(g);
        //绘制数值轴
        getNumberAxis().paint(g);
        //绘制分类轴
        getTypeAxis().paint(g);
        //绘制点
        for(int i = 0;i < getRowCount();i++)
            getPoints()[i].paint(g,width,height);
    }
    /**
     * 柱图
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintRect(Graphics g,int width,int height)
    {
        //背景颜色
        if(getBackground() != null)
        {
            g.setColor(getBackground());
            g.fillRect(getX(),getY(),getWidth(),getHeight());
        }
        //绘制背景数值轴
        getNumberAxis().paintBackground(g);
        //绘制背景分类轴
        getTypeAxis().paintBackground(g);
        //绘制数值轴
        getNumberAxis().paint(g);
        //绘制分类轴
        getTypeAxis().paint(g);

        //绘制阴影
        for(int i = 0;i < getRowCount();i++)
            getRects()[i].paintShadow(g);

        for(int i = 0;i < getRowCount();i++)
            getRects()[i].paint(g);
    }
    /**
     * 柱饼
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintOval(Graphics g,int width,int height)
    {
        //背景颜色
        if(getBackground() != null)
        {
            g.setColor(getBackground());
            g.fillRect(getX(),getY(),getWidth(),getHeight());
        }
        //绘制阴影
        for(int i = 0;i < getColumnCount();i++)
            getOvals()[i].paintShadow(g);

        for(int i = 0;i < getColumnCount();i++)
            getOvals()[i].paint(g);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
        //保存页
        s.writeInt(0);
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //读对象属性
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //读取行
        int count = s.readInt();
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeInt(1,getX(),0);
        s.writeInt(2,getY(),0);
        s.writeInt(3,getWidth(),0);
        s.writeInt(4,getHeight(),0);
        s.writeInt(5,getType(),0);
        if(getBackground() != null)
        {
            s.writeShort(6);
            s.writeInt(getBackground().getRGB());
        }
        s.writeShort(7);
        getNumberAxis().writeObject(s);
        s.writeShort(8);
        getTypeAxis().writeObject(s);
        s.writeShort(9);
        s.writeInt(getColumns().length);
        for(int i = 0;i < getColumns().length;i++)
            s.writeString(getColumns()[i]);
        s.writeShort(10);
        s.writeInt(getRows().length);
        for(int i = 0;i < getRows().length;i++)
            s.writeString(getRows()[i]);
    }
    /**
     * 读对象属性
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        switch (id)
        {
        case 1:
            setX(s.readInt());
            return true;
        case 2:
            setY(s.readInt());
            return true;
        case 3:
            setWidth(s.readInt());
            return true;
        case 4:
            setHeight(s.readInt());
            return true;
        case 5:
            setType(s.readInt());
            return true;
        case 6:
            setBackground(new Color(s.readInt()));
            return true;
        case 7:
            getNumberAxis().readObject(s);
            return true;
        case 8:
            getTypeAxis().readObject(s);
            return true;
        case 9:
            int count = s.readInt();
            String c[] = new String[count];
            for(int i = 0;i < count;i++)
                c[i] = s.readString();
            setColumns(c);
            return true;
        case 10:
            count = s.readInt();
            c = new String[count];
            for(int i = 0;i < count;i++)
                c[i] = s.readString();
            setRows(c);
            return true;
        }
        return false;
    }
}
