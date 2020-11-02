package com.dongyang.tui;

/**
 *
 * <p>Title: 尺寸对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.25
 * @version 1.0
 */
public class DSize
{
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
    public DSize()
    {

    }
    /**
     * 构造器
     * @param width int
     * @param height int
     */
    public DSize(int width,int height)
    {
        //设置宽度
        setWidth(width);
        //设置高度
        setHeight(height);
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
}
