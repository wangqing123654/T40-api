package com.dongyang.tui;

import java.awt.Rectangle;
import com.dongyang.util.TList;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 尺寸</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.12
 * @version 1.0
 */
public class DRectangle implements Cloneable
{
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 构造器
     */
    public DRectangle()
    {

    }
    /**
     * 构造器
     * @param s DataInputStream
     * @throws IOException
     */
    public DRectangle(DataInputStream s)
            throws IOException
    {
        readObject(s);
    }
    /**
     * 构造器
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public DRectangle(int x,int y,int width,int height)
    {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * 构造器
     * @param rectangle Rectangle
     */
    public DRectangle(Rectangle rectangle)
    {
        setBounds(rectangle);
    }
    /**
     * 构造器
     * @param rectangle DRectangle
     */
    public DRectangle(DRectangle rectangle)
    {
        setX(rectangle.x);
        setY(rectangle.y);
        setWidth(rectangle.width);
        setHeight(rectangle.height);
    }
    /**
     * 设置全部尺寸
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setBounds(int x,int y,int width,int height)
    {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * 设置全部尺寸
     * @param rectangle Rectangle
     */
    public void setBounds(Rectangle rectangle)
    {
        setX(rectangle.x);
        setY(rectangle.y);
        setWidth(rectangle.width);
        setHeight(rectangle.height);
    }
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        setX(x);
        setY(y);
    }
    /**
     * 设置横坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 设置横坐标
     * @return int
     */
    public int getX()
    {
        return this.x;
    }
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到纵坐标
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
     * 得到宽度
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * 设置尺寸
     * @param size DSize
     */
    public void setSize(DSize size)
    {
        if(size == null)
            return;
        setWidth(size.getWidth());
        setHeight(size.getHeight());
    }
    /**
     * 设置尺寸
     * @param width int
     * @param height int
     */
    public void setSize(int width,int height)
    {
        setWidth(width);
        setHeight(height);
    }
    /**
     * 在区间内
     * @param r Rectangle
     * @return boolean
     */
    public boolean inBounds(Rectangle r)
    {
        if(x > r.getX() + r.getWidth() ||
           y > r.getY() + r.getHeight() ||
           x + width < r.getX() ||
           y + height < r.getY())
            return false;
        return true;
    }
    /**
     * 在区间内
     * @param r DRectangle
     * @return boolean true 交叉 false 不交叉
     */
    public boolean inBounds(DRectangle r)
    {
        if(x > r.getX() + r.getWidth() ||
           y > r.getY() + r.getHeight() ||
           x + width < r.getX() ||
           y + height < r.getY())
            return false;
        return true;
    }
    /**
     * 是否覆盖
     * @param r DRectangle
     * @return boolean true 覆盖 false 没覆盖
     */
    public boolean inBestrol(DRectangle r)
    {
        if(x >= r.getX() && y >= r.getY() &&
           width <= r.getWidth() - x + r.getX() &&
           height <= r.getHeight() - y + r.getY())
            return true;
        return false;
    }
    /**
     * 得到没有覆盖的区间
     * @param list List
     * @param r DRectangle
     * @return boolean true 覆盖 false 没有覆盖
     */
    public boolean getNoBestrolList(TList list,DRectangle r)
    {
        if(list == null)
            return false;
        TList data = list.removeAll();
        boolean b = false;
        for(int i = 0;i < data.size();i++)
        {
            DRectangle rt = (DRectangle)data.get(i);
            if(rt.isBestrolList(list,r))
                b = true;
        }
        return b;
    }
    /**
     * 得到覆盖的区间
     * @param list TList
     * @param r DRectangle
     * @return boolean true 覆盖 false 没有覆盖
     */
    public boolean isBestrolList(TList list,DRectangle r)
    {
        if(width <= 0 || height <= 0)
            return false;
        //覆盖
        if(inBestrol(r))
            return true;
        //没有交集
        if(!inBounds(r))
        {
            list.add(this);
            return false;
        }
        int size = list.size();
        int h1 = 0;
        int h2 = 0;
        int y2 = y + height;
        int w1 = 0;
        int x2 = x + width;
        //上边
        if(y < r.getY())
        {
            h1 = r.getY() - y;
            if(h1 > 0)
                list.add(new DRectangle(x,y,width,h1));
        }
        //下边
        if(y + height > r.y + r.height)
        {
            y2 = r.y + r.height;
            h2 = height - y2 + y;
            if(h2 > 0)
                list.add(new DRectangle(x,y2,width,h2));
        }
        if(x < r.getX())
        {
            w1 = r.getX() - x;
            h2 = y2 - y - h1;
            if(w1 > 0 && h2 > 0)
                list.add(new DRectangle(x,y + h1,w1,h2));
        }
        if(x + width > r.x + r.width)
        {
            x2 = r.x + r.width;
            w1 = width - x2 + x;
            h2 = y2 - y - h1;
            if(w1 > 0 && h2 > 0)
                list.add(new DRectangle(x2,y + h1,w1,h2));
        }
        return list.size() > size;
    }
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(x);
        s.writeInt(y);
        s.writeInt(width);
        s.writeInt(height);
    }
    public void readObject(DataInputStream s)
      throws IOException
    {
        x = s.readInt();
        y = s.readInt();
        width = s.readInt();
        height = s.readInt();
    }
    /**
     * 克隆
     * @return Object
     */
    public Object clone()
    {
        try{
            return super.clone();
        }catch(Exception e)
        {
            return null;
        }
    }
    /**
     * 克隆自己
     * @return DRectangle
     */
    public DRectangle cloneThis()
    {
        return(DRectangle)clone();
    }
    public String toString()
    {
        return "x=" + x + ";y=" + y + ";width=" + width + ";height=" + height;
    }
}
