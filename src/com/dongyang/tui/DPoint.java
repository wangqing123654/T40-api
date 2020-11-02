package com.dongyang.tui;

import java.awt.Point;

/**
 *
 * <p>Title: 坐标</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.2.18
 * @version 1.0
 */
public class DPoint
{
    /**
     *  横坐标
     */
    int x;
    /**
     * 纵坐标
     */
    int y;
    /**
     * 构造器
     * @param point Point
     */
    public DPoint(Point point)
    {
        setX(point.x);
        setY(point.y);
    }
    /**
     * 构造器
     * @param x int
     * @param y int
     */
    public DPoint(int x,int y)
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
     * 得到横坐标
     * @return int
     */
    public int getX()
    {
        return x;
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
    public String toString()
    {
        return x + "," + y;
    }
}
