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
 * <p>Title: ���ݶ���</p>
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
     * ����
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
     * ��
     */
    private Point points[];
    /**
     * ��
     */
    private Rect rects[];
    /**
     * ��
     */
    private Oval ovals[];
    /**
     * ����
     */
    private int type = 1;
    /**
     * ��ֵ��
     */
    private Axis numberAxis;
    /**
     * ������
     */
    private Axis typeAxis;
    /**
     * ������ɫ
     */
    private Color background = new Color(168,168,168);
    /**
     * ������
     */
    public EPicData()
    {
        testData();
        //��ʼ��
        init();
    }
    /**
     * ��ʼ��
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
     * ���ø���
     * @param parent EPic
     */
    public void setParent(EPic parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return EPic
     */
    public EPic getParent()
    {
        return parent;
    }
    /**
     * ������
     * @param column String[]
     */
    public void setColumns(String[] column)
    {
        this.column = column;
        //��ʼ����
        initOvals();
    }
    /**
     * �õ���
     * @return String[]
     */
    public String[] getColumns()
    {
        return column;
    }
    /**
     * ������
     * @param row String[]
     */
    public void setRows(String[] row)
    {
        this.row = row;
        //��ʼ����
        initPoints();
        //��ʼ����
        initRects();
    }
    /**
     * �õ���
     * @return String[]
     */
    public String[] getRows()
    {
        return row;
    }
    /**
     * ��������
     * @param data double[][]
     */
    public void setData(double[][] data)
    {
        this.data = data;
    }
    /**
     * �õ�����
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
     * �õ�����
     * @return int
     */
    public int getRowCount()
    {
        if(row == null)
            return 0;
        return row.length;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getColumnCount()
    {
        if(column == null)
            return 0;
        return column.length;
    }
    /**
     * �õ���ֵ
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
     * �õ���ֵ
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
     * ����������
     */
    public void newData()
    {
        if(getRowCount() <= 0 || getColumnCount() <= 0)
            return;
        data = new double[getRowCount()][getColumnCount()];
    }
    /**
     * �õ�����
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
     * ��������
     * @param row int
     * @param column int
     * @param d double
     */
    public void setData(int row,int column,double d)
    {
        data[row][column] = d;
    }
    /**
     * ��������
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
        //��ʼ����
        initPoints();
        //��ʼ����
        initRects();
        //��ʼ����
        initOvals();
    }
    /**
     * �õ�����
     * @return int
     */
    public int getType()
    {
        return type;
    }

    /**
     * ���ò�������
     */
    public void testData()
    {
        setColumns(new String[]{"��һ����","�ڶ�����","��������","���ļ���"});
        setRows(new String[]{"����","����","����"});
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
     * ������ֵ��
     * @param numberAxis Axis
     */
    public void setNumberAxis(Axis numberAxis)
    {
        this.numberAxis = numberAxis;
    }
    /**
     * �õ���ֵ��
     * @return Axis
     */
    public Axis getNumberAxis()
    {
        return numberAxis;
    }
    /**
     * ���÷�����
     * @param typeAxis Axis
     */
    public void setTypeAxis(Axis typeAxis)
    {
        this.typeAxis = typeAxis;
    }
    /**
     * �õ�������
     * @return Axis
     */
    public Axis getTypeAxis()
    {
        return typeAxis;
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
     * �õ�������ɫ(String)
     * @return String
     */
    public String getBackgroundString()
    {
        if(background == null)
            return "";
        return background.getRed() + "," + background.getGreen() + "," + background.getBlue();
    }
    /**
     * ���õ�
     * @param points Point[]
     */
    public void setPoints(Point[] points)
    {
        this.points = points;
    }
    /**
     * ��ʼ����
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
            //�����к�
            point.setRow(i);
            //����Ĭ����ɫ
            point.initBackground();
            getPoints()[i] = point;

        }
    }
    /**
     * �õ���
     * @return Point[]
     */
    public Point[] getPoints()
    {
        return points;
    }
    /**
     * ������
     * @param rects Rect[]
     */
    public void setRects(Rect rects[])
    {
        this.rects = rects;
    }
    /**
     * ��ʼ����
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
            //�����к�
            rect.setRow(i);
            //����Ĭ����ɫ
            rect.initColor();
            getRects()[i] = rect;
        }
    }
    /**
     * �õ���
     * @return Rect[]
     */
    public Rect[] getRects()
    {
        return rects;
    }
    /**
     * �õ��ܳߴ�
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
     * �õ��ϼƳߴ�
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
     * ���ñ�
     * @param ovals Oval[]
     */
    public void setOvals(Oval ovals[])
    {
        this.ovals = ovals;
    }
    /**
     * ��ʼ����
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
            //�����к�
            oval.setColumn(i);
            //����Ĭ����ɫ
            oval.initColor();
            getOvals()[i] = oval;
        }
    }
    /**
     * �õ���
     * @return Oval[]
     */
    public Oval[] getOvals()
    {
        return ovals;
    }
    /**
     * �õ�������ƫ���� X
     * @param index int
     * @return int
     */
    public int getTypeAxisX(int index)
    {
        return getTypeAxis().getAxisX(index);
    }
    /**
     * �õ�������ƫ���� Y
     * @param index int
     * @return int
     */
    public int getTypeAxisY(int index)
    {
        return getTypeAxis().getAxisY(index);
    }
    /**
     * �õ�������ƫ����(�м�) X
     * @param index int
     * @return int
     */
    public int getTypeAxisCenterX(int index)
    {
        return getTypeAxis().getAxisCenterX(index);
    }
    /**
     * �õ�������ƫ����(�м�) Y
     * @param index int
     * @return int
     */
    public int getTypeAxisCenterY(int index)
    {
        return getTypeAxis().getAxisCenterY(index);
    }
    /**
     * ������ֵ���
     * @param value double
     * @return int
     */
    public int getValueX(double value)
    {
        return getNumberAxis().getValueX(value);
    }
    /**
     * ������ֵ�߶�
     * @param value double
     * @return int
     */
    public int getValueY(double value)
    {
        return getNumberAxis().getValueY(value);
    }
    /**
     * ����
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
        case 1://��ͼ
            paintPoint(g,width,height);
            return;
        case 2://��ͼ
            paintRect(g,width,height);
            return;
        case 3://��ͼ
            paintOval(g,width,height);
            return;
        }
    }
    /**
     * ��ͼ
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintPoint(Graphics g,int width,int height)
    {
        //������ɫ
        if(getBackground() != null)
        {
            g.setColor(getBackground());
            g.fillRect(getX(),getY(),getWidth(),getHeight());
        }
        //���Ʊ�����ֵ��
        getNumberAxis().paintBackground(g);
        //���Ʊ���������
        getTypeAxis().paintBackground(g);
        //������ֵ��
        getNumberAxis().paint(g);
        //���Ʒ�����
        getTypeAxis().paint(g);
        //���Ƶ�
        for(int i = 0;i < getRowCount();i++)
            getPoints()[i].paint(g,width,height);
    }
    /**
     * ��ͼ
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintRect(Graphics g,int width,int height)
    {
        //������ɫ
        if(getBackground() != null)
        {
            g.setColor(getBackground());
            g.fillRect(getX(),getY(),getWidth(),getHeight());
        }
        //���Ʊ�����ֵ��
        getNumberAxis().paintBackground(g);
        //���Ʊ���������
        getTypeAxis().paintBackground(g);
        //������ֵ��
        getNumberAxis().paint(g);
        //���Ʒ�����
        getTypeAxis().paint(g);

        //������Ӱ
        for(int i = 0;i < getRowCount();i++)
            getRects()[i].paintShadow(g);

        for(int i = 0;i < getRowCount();i++)
            getRects()[i].paint(g);
    }
    /**
     * ����
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintOval(Graphics g,int width,int height)
    {
        //������ɫ
        if(getBackground() != null)
        {
            g.setColor(getBackground());
            g.fillRect(getX(),getY(),getWidth(),getHeight());
        }
        //������Ӱ
        for(int i = 0;i < getColumnCount();i++)
            getOvals()[i].paintShadow(g);

        for(int i = 0;i < getColumnCount();i++)
            getOvals()[i].paint(g);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //д��������
        writeObjectAttribute(s);
        s.writeShort( -1);
        //����ҳ
        s.writeInt(0);
    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //����������
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //��ȡ��
        int count = s.readInt();
    }
    /**
     * д��������
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
     * ����������
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
