package com.dongyang.tui.text.div;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.control.TControl;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 虚拟表格</p>
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
public class VTable extends DIVBase
{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean x1B;
    private boolean y1B;
    private boolean x2B;
    private boolean y2B;
    /**
     * 显示范围
     * 0 所有页
     * 1 首页
     * 2 尾页
     */
    private int showPage = 0;
    /**
     * 页码
     */
    private int pageIndex;
    /**
     * 图层
     */
    private MV mv;
    /**
     * 构造器
     */
    public VTable()
    {
        setMV(new MV());
    }
    /**
     * 构造器
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public VTable(int x,int y,int width,int height)
    {
        this();
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * 设置图层
     * @param mv MV
     */
    public void setMV(MV mv)
    {
        mv.setParent(this);
        this.mv = mv;
    }
    /**
     * 得到图层
     * @return MV
     */
    public MV getMV()
    {
        return mv;
    }
    /**
     * 设置X
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 得到X
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * 设置Y
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到Y1
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return height;
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
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "表格";
    }
    /**
     * 得到类型
     * @return int
     */
    public int getTypeID()
    {
        return 2;
    }
    /**
     * 得到打印X
     * @return int
     */
    public int getStartXP()
    {
        if(isX1B())
            return getParent().getStartXP() + getParent().getEndXP() - getX();
        return getParent().getStartXP() + getX();
    }
    /**
     * 得到打印Y
     * @return int
     */
    public int getStartYP()
    {
        if(isY1B())
            return getParent().getStartYP() + getParent().getEndYP() - getY();
        return getParent().getStartYP() + getY();
    }
    /**
     * 起始点X
     * @return int
     */
    public int getStartX()
    {
        if(isX1B())
            return getDrawStartX() + getParent().getEndX() - (int)(getX() * getZoom() / 75.0 + 0.5);
        return getDrawStartX() + (int)(getX() * getZoom() / 75.0 + 0.5);
    }
    /**
     * 起始点Y
     * @return int
     */
    public int getStartY()
    {
        if(isY1B())
            return getDrawStartY() + getParent().getEndY() - (int)(getY() * getZoom() / 75.0 + 0.5);
        return getDrawStartY() + (int)(getY() * getZoom() / 75.0 + 0.5);
    }
    /**
     * 得到结束点
     * @return int
     */
    public int getEndXP()
    {
        if(isX2B())
            return getParent().getEndXP() - getWidth() - getX();
        return getWidth();
    }
    /**
     * 得到结束点
     * @return int
     */
    public int getEndYP()
    {
        if(isY2B())
            return getParent().getEndYP() - getHeight() - getY();
        return getHeight();
    }
    /**
     * 得到结束点
     * @return int
     */
    public int getEndX()
    {
        return (int)(getEndXP() * getZoom() / 75.0 + 0.5);
    }
    /**
     * 得到结束点
     * @return int
     */
    public int getEndY()
    {
        return (int)(getEndYP() * getZoom() / 75.0 + 0.5);
    }
    /**
     * 得到原点X
     * @return int
     */
    public int getDrawStartX()
    {
        return getParent().getStartX();
    }
    /**
     * 得到原点Y
     * @return int
     */
    public int getDrawStartY()
    {
        return getParent().getStartY();
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        MV mv = this.getMV();
        if(!mv.isVisible())
            return;
        if(getShowPage() == 1 && getPageIndex() > 0)
            return;
        if(getShowPage() == 2 && getPageIndex() != getPM().getPageManager().size() - 1)
            return;
        mv.setPageIndex(pageIndex);
        mv.paint(g);
    }
    /**
     * 打印
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        MV mv = this.getMV();
        if(!mv.isVisible())
            return;
        if(getShowPage() == 1 && getPageIndex() > 0)
            return;
        if(getShowPage() == 2 && getPageIndex() != getPM().getPageManager().size() - 1)
            return;
        mv.setPageIndex(pageIndex);
        mv.print(g);
    }
    /**
     * 得到属性对话框名称
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VTableDialog.x";
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeString(1,getName(),"");
        s.writeBoolean(2,isVisible(),true);
        s.writeInt(3,getX(),0);
        s.writeInt(4,getY(),0);
        s.writeInt(5,getWidth(),0);
        s.writeInt(6,getHeight(),0);
        s.writeShort(7);
        getMV().writeObject(s);
        s.writeInt(8,getShowPage(),0);
        s.writeShort(9);
        s.writeBoolean(isX1B());
        s.writeBoolean(isY1B());
        s.writeBoolean(isX2B());
        s.writeBoolean(isY2B());
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
            setName(s.readString());
            return true;
        case 2:
            setVisible(s.readBoolean());
            return true;
        case 3:
            setX(s.readInt());
            return true;
        case 4:
            setY(s.readInt());
            return true;
        case 5:
            setWidth(s.readInt());
            return true;
        case 6:
            setHeight(s.readInt());
            return true;
        case 7:
            getMV().readObject(s);
            return true;
        case 8:
            setShowPage(s.readInt());
            return true;
        case 9:
            setX1B(s.readBoolean());
            setY1B(s.readBoolean());
            setX2B(s.readBoolean());
            setY2B(s.readBoolean());
            return true;
        }
        return false;
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        writeObjectAttribute(s);
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
            //读对象属性
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //读取项目
        int count = s.readInt();
    }
    /**
     * 初始化新Table
     */
    public void initNew()
    {
        VLine line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("上边");
        line.setXYB(false,false,true,false);

        line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("下边");
        line.setXYB(false,true,true,true);

        line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("左边");
        line.setXYB(false,false,false,true);

        line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("右边");
        line.setXYB(true,false,true,true);

    }
    /**
     * 修改参数
     * @param mv MV
     * @param div DIV
     * @param name String
     */
    public void modify(MV mv,DIV div,String name)
    {
        propertyModify(mv,div,name);
    }
    /**
     * 属性对话框修改参数
     * @param mv MV
     * @param div DIV
     * @param name String
     */
    public void propertyModify(MV mv,DIV div,String name)
    {
        if(getPropertyWindow() == null || getPropertyWindow().isClose())
            return;
        TControl control = getPropertyWindow().getControl();
        if(control == null)
            return;
        control.callFunction("modify",mv,div,name);
    }
    /**
     * 设置页码
     * @param pageIndex int
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
    /**
     * 得到页码
     * @return int
     */
    public int getPageIndex()
    {
        return pageIndex;
    }
    /**
     * 设置显示范围
     * 0 所有页
     * 1 首页
     * 2 尾页
     * @param showPage int
     */
    public void setShowPage(int showPage)
    {
        this.showPage = showPage;
    }
    /**
     * 得到显示范围
     * 0 所有页
     * 1 首页
     * 2 尾页
     * @return int
     */
    public int getShowPage()
    {
        return showPage;
    }
    /**
     * 释放
     */
    public void DC()
    {
        super.DC();
        if(getMV() != null)
            getMV().DC();
    }
}
