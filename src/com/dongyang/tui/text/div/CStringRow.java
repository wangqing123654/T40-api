package com.dongyang.tui.text.div;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 单行字对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.20
 * @version 1.0
 */
public class CStringRow
{
    private int x;
    private int y;
    private int width;
    private String text;
    private int x0[];
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
    public void setText(String text)
    {
        this.text = text;
    }
    public String getText()
    {
        return text;
    }
    public void setX0(TList list)
    {
        int x[] = new int[list.size()];
        for(int i = 0;i < list.size();i++)
            x[i] = (Integer)list.get(i);
        setX0(x);
    }
    public void setX0(int x0[])
    {
        this.x0 = x0;
    }
    public int[] getX0()
    {
        return x0;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("x=" + getX());
        sb.append(" y=" + getY());
        sb.append(" width=" + getWidth());
        sb.append(" text=" + getText());
        sb.append(" text.length=" + getText().length());
        sb.append(" x0 size=" + getX0().length + " [");
        for(int i = 0;i < getX0().length;i++)
        {
            if(i > 0)
                sb.append(",");
            sb.append(getX0()[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
