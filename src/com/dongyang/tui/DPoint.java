package com.dongyang.tui;

import java.awt.Point;

/**
 *
 * <p>Title: ����</p>
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
     *  ������
     */
    int x;
    /**
     * ������
     */
    int y;
    /**
     * ������
     * @param point Point
     */
    public DPoint(Point point)
    {
        setX(point.x);
        setY(point.y);
    }
    /**
     * ������
     * @param x int
     * @param y int
     */
    public DPoint(int x,int y)
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
     * �õ�������
     * @return int
     */
    public int getX()
    {
        return x;
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
    public String toString()
    {
        return x + "," + y;
    }
}
