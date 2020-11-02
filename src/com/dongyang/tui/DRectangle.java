package com.dongyang.tui;

import java.awt.Rectangle;
import com.dongyang.util.TList;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: �ߴ�</p>
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
     * ������
     */
    private int x;
    /**
     * ������
     */
    private int y;
    /**
     * ���
     */
    private int width;
    /**
     * �߶�
     */
    private int height;
    /**
     * ������
     */
    public DRectangle()
    {

    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public DRectangle(DataInputStream s)
            throws IOException
    {
        readObject(s);
    }
    /**
     * ������
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
     * ������
     * @param rectangle Rectangle
     */
    public DRectangle(Rectangle rectangle)
    {
        setBounds(rectangle);
    }
    /**
     * ������
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
     * ����ȫ���ߴ�
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
     * ����ȫ���ߴ�
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
     * ����λ��
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        setX(x);
        setY(y);
    }
    /**
     * ���ú�����
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * ���ú�����
     * @return int
     */
    public int getX()
    {
        return this.x;
    }
    /**
     * ����������
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * �õ����
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * ���óߴ�
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
     * ���óߴ�
     * @param width int
     * @param height int
     */
    public void setSize(int width,int height)
    {
        setWidth(width);
        setHeight(height);
    }
    /**
     * ��������
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
     * ��������
     * @param r DRectangle
     * @return boolean true ���� false ������
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
     * �Ƿ񸲸�
     * @param r DRectangle
     * @return boolean true ���� false û����
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
     * �õ�û�и��ǵ�����
     * @param list List
     * @param r DRectangle
     * @return boolean true ���� false û�и���
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
     * �õ����ǵ�����
     * @param list TList
     * @param r DRectangle
     * @return boolean true ���� false û�и���
     */
    public boolean isBestrolList(TList list,DRectangle r)
    {
        if(width <= 0 || height <= 0)
            return false;
        //����
        if(inBestrol(r))
            return true;
        //û�н���
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
        //�ϱ�
        if(y < r.getY())
        {
            h1 = r.getY() - y;
            if(h1 > 0)
                list.add(new DRectangle(x,y,width,h1));
        }
        //�±�
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
     * ��¡
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
     * ��¡�Լ�
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
