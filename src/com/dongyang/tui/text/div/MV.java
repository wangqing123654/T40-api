package com.dongyang.tui.text.div;

import com.dongyang.util.TList;
import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.text.PM;
import com.dongyang.data.TParm;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.MView;

/**
 *
 * <p>Title: DIV管理类</p>
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
public class MV
{
    /**
     * 父类
     */
    private Object parent;
    /**
     * 成员
     */
    private TList list;
    /**
     * 是否是前景图层
     */
    private boolean isForeground;
    /**
     * 显示
     */
    private boolean visible = true;
    /**
     * 名称
     */
    private String name = "<none>";
    /**
     * 页码
     */
    private int pageIndex;
    /**
     * 构造器
     */
    public MV()
    {
        list = new TList();
    }
    /**
     * 设置父类
     * @param parent Object
     */
    public void setParent(Object parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return Object
     */
    public Object getParent()
    {
        return parent;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        if(getParent() == null)
            return null;
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getPM();
        if(getParent() instanceof DIV)
            return ((DIV)getParent()).getPM();
        return null;
    }
    /**
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }
    /**
     * 增加
     * @param div DIV
     */
    public void add(DIV div)
    {
        list.add(div);
    }
    /**
     * 得到
     * @param index int
     * @return DIV
     */
    public DIV get(int index)
    {
        return (DIV)list.get(index);
    }
    /**
     * 得到
     * @param name String
     * @return DIV
     */
    public DIV get(String name)
    {
        if(name == null || name.length() == 0)
            return null;
        for(int i = 0;i < size();i++)
        {
            DIV div = get(i);
            if(name.equals(div.getName()))
                return div;
        }
        return null;
    }
    /**
     * 查找位置
     * @param div DIV
     * @return int
     */
    public int indexOf(DIV div)
    {
        return list.indexOf(div);
    }
    /**
     * 删除
     * @param div DIV
     */
    public void remove(DIV div)
    {
        list.remove(div);
    }
    /**
     * 清除全部
     */
    public void removeAll()
    {
        list.removeAll();
    }
    /**
     * 删除
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    /**
     * 总数
     * @return int
     */
    public int size()
    {
        return list.size();
    }
    /**
     * 设置是否是前景图层
     * @param isForeground boolean
     */
    public void setIsForeground(boolean isForeground)
    {
        this.isForeground = isForeground;
    }
    /**
     * 是否是前景图层
     * @return boolean
     */
    public boolean isForeground()
    {
        return isForeground;
    }
    /**
     * 设置是否显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    public int getStartXP()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getStartX();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getStartXP();
        return 0;
    }
    public int getStartYP()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getStartY();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getStartYP();
        return 0;
    }
    /**
     * 得到开始原点X
     * @return int
     */
    public int getStartX()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getStartX();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getStartX();
        return 0;
    }
    /**
     * 得到开始原点Y
     * @return int
     */
    public int getStartY()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getStartY();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getStartY();
        return 0;
    }
    public int getEndXP()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getEndXP();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getEndXP();
        return 0;
    }
    public int getEndYP()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getEndYP();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getEndYP();
        return 0;
    }
    /**
     * 得到结束原点X
     * @return int
     */
    public int getEndX()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getEndX();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getEndX();
        return 0;
    }
    /**
     * 得到结束原点Y
     * @return int
     */
    public int getEndY()
    {
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getEndY();
        if(getParent() instanceof VTable)
            return ((VTable)getParent()).getEndY();
        return 0;
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        for(int i = 0;i < size();i++)
        {
            DIV div = get(i);
            if(!div.isVisible())
                continue;
            if(div instanceof VTable)
                ((VTable)div).setPageIndex(getPageIndex());
            div.paint(g);
        }
    }
    /**
     * 绘制(打印)
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        for(int i = 0;i < size();i++)
        {
            DIV div = get(i);
            if(!div.isVisible())
                continue;
            if(div instanceof VTable)
                ((VTable)div).setPageIndex(getPageIndex());
            div.print(g);
        }
    }
    /**
     * 增加线
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param color Color
     * @return VLine
     */
    public VLine addLine(int x1,int y1,int x2,int y2,Color color)
    {
        VLine line = new VLine(x1,y1,x2,y2,color);
        line.setParent(this);
        add(line);
        return line;
    }
    /**
     * 增加文字
     * @param x int
     * @param y int
     * @param color Color
     * @return VText
     */
    public VText addText(int x,int y,Color color)
    {
        VText text = new VText(x,y,color);
        text.setParent(this);
        add(text);
        return text;
    }
    /**
     * 增加表格
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @return VTable
     */
    public VTable addTable(int x,int y,int width,int height)
    {
        VTable table = new VTable(x,y,width,height);
        table.setParent(this);
        add(table);
        table.initNew();
        return table;
    }
    /**
     * 增加表格
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @return VTable
     */
    public VPic addPic(int x,int y,int width,int height)
    {
        VPic pic = new VPic(x,y,width,height);
        pic.setParent(this);
        add(pic);
        //条码图片，去掉边框
        //pic.initNew();
        return pic;
    }
    /**
     * 新增条码
     * @param x int
     * @param y int
     * @return VBarCode
     */
    public VBarCode addBarCode(int x,int y)
    {
        VBarCode barCode = new VBarCode(x,y);
        barCode.setParent(this);
        add(barCode);
        return barCode;
    }
    /**
     * 修改参数
     * @param div DIV
     * @param name String
     */
    public void modify(DIV div,String name)
    {
        if(getParent() instanceof MVList)
        {
            ((MVList) getParent()).modify(this, div, name);
            return;
        }
        if(getParent() instanceof VTable)
        {
            ((VTable) getParent()).modify(this, div, name);
            return;
        }
    }
    /**
     * 释放
     */
    public void DC()
    {
        for(int i = 0;i < size();i++)
            get(i).DC();
    }
    /**
     * 得到创建内容
     * @return TParm
     */
    public TParm getCreateTParm()
    {
        TParm parm = new TParm();
        parm.addData("ID",1);
        parm.addData("NAME","直线");
        parm.addData("TYPE","VLine");

        parm.addData("ID",2);
        parm.addData("NAME","表格");
        parm.addData("TYPE","VTable");

        parm.addData("ID",3);
        parm.addData("NAME","文字");
        parm.addData("TYPE","VText");

        parm.addData("ID",4);
        parm.addData("NAME","图片");
        parm.addData("TYPE","VPic");

        parm.addData("ID",5);
        parm.addData("NAME","条码");
        parm.addData("TYPE","VBarCode");
        return parm;
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
        s.writeBoolean(3,isForeground(),false);
        s.writeShort(-1);
        //保存项目
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            DIV div = get(i);
            if(div == null)
                continue;
            s.writeInt(div.getTypeID());
            div.writeObject(s);
        }
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
                setIsForeground(s.readBoolean());
                break;
            }
            id = s.readShort();
        }
        //读取项目
        int count = s.readInt();
        for (int i = 0; i < count; i++)
        {
            DIV div = createDiv(s.readInt());
            div.readObject(s);
        }
    }
    /**
     * 创建DIV
     * @param type int
     * @return DIV
     */
    public DIV createDiv(int type)
    {
        DIV div = null;
        switch(type)
        {
        case 1:
            div = new VLine();
            break;
        case 2:
            div = new VTable();
            break;
        case 3:
            div = new VText();
            break;
        case 4:
            div = new VPic();
            break;
        case 5:
            div = new VBarCode();
            break;
        default:
            return null;
        }
        div.setParent(this);
        add(div);
        return div;
    }
    /**
     * 父类是否是宏模型
     * @return boolean
     */
    public boolean isMacroroutineModel()
    {
        if(getParent() == null)
            return false;
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).isMacroroutineModel();
        if(getParent() instanceof DIV)
            return ((DIV)getParent()).isMacroroutineModel();
        return false;
    }
    /**
     * 得到数据
     * @param column String
     * @return Object
     */
    public Object getMacroroutineData(String column)
    {
        if(!isMacroroutineModel())
            return null;
        if(getParent() instanceof MVList)
            return ((MVList)getParent()).getMacroroutineData(column);
        if(getParent() instanceof DIV)
            return ((DIV)getParent()).getMacroroutineData(column);
        return null;
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
}
