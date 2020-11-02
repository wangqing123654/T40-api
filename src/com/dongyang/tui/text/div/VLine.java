package com.dongyang.tui.text.div;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.util.TypeTool;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 线</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.21
 * @version 1.0
 */
public class VLine extends DIVBase
{
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private boolean x1B;
    private boolean y1B;
    private boolean x2B;
    private boolean y2B;
    /**
     * 偏移X
     */
    private int x0;
    /**
     * 偏移Y
     */
    private int y0;
    /**
     * 线个数
     */
    private int count;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 线高
     */
    private float lineWidth = 1.0f;
    /**
     * 线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int lineType = 0;
    /**
     * 虚线间距
     */
    private float lineWidth1 = 5.0f;
    /**
     * TParm 代入线段
     */
    private boolean isLineT;

    /**
     * 保存坐标
     */
    private int pointS[][];

    /**
     * 构造器
     */
    public VLine()
    {

    }
    /**
     * 构造器
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param color Color
     */
    public VLine(int x1,int y1,int x2,int y2,Color color)
    {
        setX1(x1);
        setY1(y1);
        setX2(x2);
        setY2(y2);
        setColor(color);
    }
    /**
     * 设置X1
     * @param x1 int
     */
    public void setX1(int x1)
    {
        this.x1 = x1;
    }
    /**
     * 得到X1
     * @return int
     */
    public int getX1()
    {
        return x1;
    }
    /**
     * 设置Y1
     * @param y1 int
     */
    public void setY1(int y1)
    {
        this.y1 = y1;
    }
    /**
     * 得到Y1
     * @return int
     */
    public int getY1()
    {
        return y1;
    }
    /**
     * 设置X2
     * @param x2 int
     */
    public void setX2(int x2)
    {
        this.x2 = x2;
    }
    /**
     * 得到X2
     * @return int
     */
    public int getX2()
    {
        return x2;
    }
    /**
     * 设置Y2
     * @param y2 int
     */
    public void setY2(int y2)
    {
        this.y2 = y2;
    }
    /**
     * 得到Y2
     * @return int
     */
    public int getY2()
    {
        return y2;
    }
    /**
     * 设置偏移X
     * @param x0 int
     */
    public void setX0(int x0)
    {
        this.x0 = x0;
    }
    /**
     * 得到偏移X
     * @return int
     */
    public int getX0()
    {
        return x0;
    }
    /**
     * 设置偏移Y
     * @param y0 int
     */
    public void setY0(int y0)
    {
        this.y0 = y0;
    }
    /**
     * 得到偏移Y
     * @return int
     */
    public int getY0()
    {
        return y0;
    }
    public void setX1B(boolean x1B)
    {
        this.x1B = x1B;
    }
    public boolean isX1B()
    {
        return x1B;
    }
    public void setY1B(boolean y1B)
    {
        this.y1B = y1B;
    }
    public boolean isY1B()
    {
        return y1B;
    }
    public void setX2B(boolean x2B)
    {
        this.x2B = x2B;
    }
    public boolean isX2B()
    {
        return x2B;
    }
    public void setY2B(boolean y2B)
    {
        this.y2B = y2B;
    }
    public boolean isY2B()
    {
        return y2B;
    }
    public void setXYB(boolean x1B,boolean y1B,boolean x2B,boolean y2B)
    {
        setX1B(x1B);
        setY1B(y1B);
        setX2B(x2B);
        setY2B(y2B);
    }
    /**
     * 设置线个数
     * @param count int
     */
    public void setCount(int count)
    {
        this.count = count;
    }
    /**
     * 得到线个数
     * @return int
     */
    public int getCount()
    {
        return count;
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
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "直线";
    }
    /**
     * 得到类型
     * @return int
     */
    public int getTypeID()
    {
        return 1;
    }
    /**
     * 设置线高
     * @param lineWidth float
     */
    public void setLineWidth(float lineWidth)
    {
        this.lineWidth = lineWidth;
    }
    /**
     * 得到线高
     * @return float
     */
    public float getLineWidth()
    {
        return lineWidth;
    }
    /**
     * 设置线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     * @param lineType int
     */
    public void setLineType(int lineType)
    {
        this.lineType = lineType;
    }
    /**
     * 得到线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     * @return int
     */
    public int getLineType()
    {
        return lineType;
    }
    /**
     * 设置虚线间距
     * @param lineWidth1 float
     */
    public void setLineWidth1(float lineWidth1)
    {
        this.lineWidth1 = lineWidth1;
    }
    /**
     * 得到虚线间距
     * @return float
     */
    public float getLineWidth1()
    {
        return lineWidth1;
    }
    /**
     * 设置TParm代入线段
     * @param isLineT boolean
     */
    public void setLineT(boolean isLineT)
    {
        this.isLineT = isLineT;
    }
    /**
     * 是否TParm代入线段
     * @return boolean
     */
    public boolean isLineT()
    {
        return isLineT;
    }
    /**
     * 得到线类型对象
     * @return Stroke
     */
    private Stroke getStroke()
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
    public int getDrawStartXP()
    {
        return getParent().getStartXP();
    }
    public int getDrawStartYP()
    {
        return getParent().getStartYP();
    }
    /**
     * 得到开始原点X
     * @return int
     */
    public int getDrawStartX()
    {
        return getParent().getStartX();
    }
    /**
     * 得到开始原点Y
     * @return int
     */
    public int getDrawStartY()
    {
        return getParent().getStartY();
    }
    public int getDrawX1P()
    {
        if(isX1B())
            return getParent().getEndXP() - getX1() + getDrawStartXP();
        return getX1() + getDrawStartXP();
    }
    public int getDrawY1P()
    {
        if(isY1B())
            return getParent().getEndYP() - getY1() + getDrawStartYP();
        return getY1() + getDrawStartYP();
    }
    public int getDrawX2P()
    {
        if(isX2B())
            return getParent().getEndXP() - getX2() + getDrawStartXP();
        return getX2() + getDrawStartXP();
    }
    public int getDrawY2P()
    {
        if(isY2B())
            return getParent().getEndYP() - getY2() + getDrawStartYP();
        return getY2() + getDrawStartYP();
    }
    public int getDrawX1()
    {
        int x = (int)(getX1() * getZoom() / 75.0 + 0.5);
        if(isX1B())
            return getParent().getEndX() - x + getDrawStartX();
        return x + getDrawStartX();
    }
    public int getDrawY1()
    {
        int y = (int)(getY1() * getZoom() / 75.0 + 0.5);
        if(isY1B())
            return getParent().getEndY() - y + getDrawStartY();
        return y + getDrawStartY();
    }
    public int getDrawX2()
    {
        int x = (int)(getX2() * getZoom() / 75.0 + 0.5);
        if(isX2B())
            return getParent().getEndX() - x + getDrawStartX();
        return x + getDrawStartX();
    }
    public int getDrawY2()
    {
        int y = (int)(getY2() * getZoom() / 75.0 + 0.5);
        if(isY2B())
            return getParent().getEndY() - y + getDrawStartY();
        return y + getDrawStartY();
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        Stroke oldStroke = ((Graphics2D)g).getStroke();
        ((Graphics2D)g).setStroke(getStroke());
        g.setColor(getColor());
        if(isLineT())
        {
            int point[][] = null;
            if(isMacroroutineModel())
            {
                Object obj = getMacroroutineData(getName());
                if(!(obj instanceof int[][]))
                    point = pointS;
                else{
                    point = (int[][]) obj;
                    pointS = point;
                }
            }
            else
            {
                Object obj = getPM().getFileManager().getParameter();
                if (obj == null || !(obj instanceof TParm))
                    return;
                point = (int[][]) ((TParm) obj).getData(getName(), "LINE");
            }
            if(point == null)
                return;
            for(int i = 0;i < point.length;i++)
            {
                int x1 = (int)(point[i][0] * getZoom() / 75.0 + 0.5) + getDrawStartX();
                int y1 = (int)(point[i][1] * getZoom() / 75.0 + 0.5) + getDrawStartY();
                int w = (int)(point[i][2] * getZoom() / 75.0 + 0.5);
                int h = (int)(point[i][3] * getZoom() / 75.0 + 0.5);
                int x2 = w + getDrawStartX();
                int y2 = h + getDrawStartY();
                int type = 1;
                if(point[i].length > 4)
                    type = point[i][4];
                int type1 = 0;
                if(point[i].length > 5)
                    type1 = point[i][5];
                switch(type)
                {
                case 1:
                    g.drawLine(x1,y1,x2,y2);
                    break;
                case 2:
                    g.drawOval(x1,y1,w,h);
                    break;
                case 3:
                    g.drawRect(x1,y1,w,h);
                    break;
                case 4:
                    g.fillOval(x1,y1,w,h);
                    break;
                case 5:
                    g.fillRect(x1,y1,w,h);
                    break;
                case 6:
                    g.drawOval(x1,y1,w,h);
                    g.fillOval(x1 + w / 3,y1 + h / 3,w / 3,h / 3);
                    break;
                case 7:
                    g.drawLine(x1,y1,x2,y2);
                    g.drawLine(x2,y1,x1,y2);
                    break;
                }
                switch(type1)
                {
                case 1:
                    int x11 = x1 + w / 2;
                    int y11 = y1 + h + 2;
                    g.drawLine(x11,y11,x11,y11 + 10);
                    g.drawLine(x11,y11 + 10,x11 - 2,y11 + 6);
                    g.drawLine(x11,y11 + 10,x11 + 2,y11 + 6);
                    break;
                }
            }
            ((Graphics2D)g).setStroke(oldStroke);
            return;
        }
        int x1 = getDrawX1();
        int y1 = getDrawY1();
        int x2 = getDrawX2();
        int y2 = getDrawY2();
        g.drawLine(x1,y1,x2,y2);
        for(int i = 1;i <= count;i++)
        {
            int x0 = (int)(getX0() * i * getZoom() / 75.0 + 0.5);
            int y0 = (int)(getY0() * i * getZoom() / 75.0 + 0.5);
            g.drawLine(x1 + x0,y1 + y0,x2 + x0,y2 + y0);
        }
        ((Graphics2D)g).setStroke(oldStroke);
    }
    /**
     * 打印
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        ((Graphics2D)g).setStroke(getStroke());
        g.setColor(getColor());
        if(isLineT())
        {
            int point[][] = null;
            if(isMacroroutineModel())
            {
                Object obj = getMacroroutineData(getName());
                if(!(obj instanceof int[][]))
                    point = pointS;
                else{
                    point = (int[][]) obj;
                    pointS = point;
                }
            }
            else
            {
                Object obj = getPM().getFileManager().getParameter();
                if (obj == null || !(obj instanceof TParm))
                    return;
                point = (int[][]) ((TParm) obj).getData(getName(), "LINE");
            }
            if(point == null)
                return;
            for(int i = 0;i < point.length;i++)
            {
                int x1 = point[i][0] + getDrawStartXP();
                int y1 = point[i][1] + getDrawStartYP();
                int w = point[i][2];
                int h = point[i][3];
                int x2 = w + getDrawStartXP();
                int y2 = h + getDrawStartYP();
                int type = 1;
                if(point[i].length > 4)
                    type = point[i][4];
                int type1 = 0;
                if(point[i].length > 5)
                    type1 = point[i][5];
                switch(type)
                {
                case 1:
                    g.drawLine(x1,y1,x2,y2);
                    break;
                case 2:
                    g.drawOval(x1,y1,w,h);
                    break;
                case 3:
                    g.drawRect(x1,y1,w,h);
                    break;
                case 4:
                    g.fillOval(x1,y1,w,h);
                    break;
                case 5:
                    g.fillRect(x1,y1,w,h);
                    break;
                case 6:
                    g.drawOval(x1,y1,w,h);
                    g.fillOval(x1 + w / 3,y1 + h / 3,w / 3,h / 3);
                    break;
                case 7:
                    g.drawLine(x1,y1,x2,y2);
                    g.drawLine(x2,y1,x1,y2);
                    break;
                }
                switch(type1)
                {
                case 1:
                    int x11 = x1 + w / 2;
                    int y11 = y1 + h + 2;
                    g.drawLine(x11,y11,x11,y11 + 10);
                    g.drawLine(x11,y11 + 10,x11 - 2,y11 + 6);
                    g.drawLine(x11,y11 + 10,x11 + 2,y11 + 6);
                    break;
                }
            }
            return;
        }
        int x1 = getDrawX1P();
        int y1 = getDrawY1P();
        int x2 = getDrawX2P();
        int y2 = getDrawY2P();
        g.drawLine(x1,y1,x2,y2);
        for(int i = 0;i < count;i++)
        {
            x1 += getX0();
            x2 += getX0();
            y1 += getY0();
            y2 += getY0();
            g.drawLine(x1,y1,x2,y2);
        }
    }
    /**
     * 得到属性对话框名称
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\LineDialog.x";
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeString(1,getName(),"");
        s.writeBoolean(2,isVisible(),true);
        s.writeInt(3,getLineType(),0);
        s.writeFloat(4,getLineWidth(),0);
        s.writeFloat(5,getLineWidth1(),0);
        s.writeShort(6);
        s.writeInt(getColor().getRGB());
        s.writeInt(7,getX1(),0);
        s.writeInt(8,getY1(),0);
        s.writeInt(9,getX2(),0);
        s.writeInt(10,getY2(),0);
        s.writeInt(11,getX0(),0);
        s.writeInt(12,getY0(),0);
        s.writeInt(13,getCount(),0);
        s.writeShort(14);
        s.writeBoolean(isX1B());
        s.writeBoolean(isY1B());
        s.writeBoolean(isX2B());
        s.writeBoolean(isY2B());
        s.writeBoolean(15,isLineT(),false);
        s.writeShort(16);
        s.writeInts(getPointS());
        s.writeShort(-1);
        //保存项目
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
            switch (id)
            {
            case 1:
                setName(s.readString());
                break;
            case 2:
                setVisible(s.readBoolean());
                break;
            case 3:
                setLineType(s.readInt());
                break;
            case 4:
                setLineWidth(s.readFloat());
                break;
            case 5:
                setLineWidth1(s.readFloat());
                break;
            case 6:
                setColor(new Color(s.readInt()));
                break;
            case 7:
                setX1(s.readInt());
                break;
            case 8:
                setY1(s.readInt());
                break;
            case 9:
                setX2(s.readInt());
                break;
            case 10:
                setY2(s.readInt());
                break;
            case 11:
                setX0(s.readInt());
                break;
            case 12:
                setY0(s.readInt());
                break;
            case 13:
                setCount(s.readInt());
                break;
            case 14:
                setX1B(s.readBoolean());
                setY1B(s.readBoolean());
                setX2B(s.readBoolean());
                setY2B(s.readBoolean());
                break;
            case 15:
                setLineT(s.readBoolean());
                break;
            case 16:
                setPointS(s.readInts());
                break;
            }
            id = s.readShort();
        }
        //读取项目
        int count = s.readInt();
    }

    /**
     * 设置坐标
     * @param pointS int[][]
     */
    public void setPointS(int[][] pointS){
        this.pointS = pointS;
    }

    /**
     * 取得坐标
     * @return int[][]
     */
    public int[][] getPointS(){
        return this.pointS;
    }
}
