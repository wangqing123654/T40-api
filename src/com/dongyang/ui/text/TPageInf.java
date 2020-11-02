package com.dongyang.ui.text;

public class TPageInf {
    /**
     * 页序号
     */
    private int pageIndex;
    /**
     * x坐标
     */
    private int x;
    /**
     * y坐标
     */
    private int y;
    /**
     * 显示宽度
     */
    private int showWidth;
    /**
     * 显示高度
     */
    private int showHeight;
    /**
     * 工作区的X坐标
     */
    private int workX;
    /**
     * 工作区的Y坐标
     */
    private int workY;
    /**
     * 工作区的宽度坐标
     */
    private int workWidth;
    /**
     * 工作区的高度坐标
     */
    private int workHeight;
    /**
     * 设置页序号
     * @param pageIndex int
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
    /**
     * 得到页序号
     * @return int
     */
    public int getPageIndex()
    {
        return pageIndex;
    }
    /**
     * 设置x坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 得到x坐标
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * 设置y坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到y坐标
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置页面的显示宽度
     * @param showWidth int
     */
    public void setShowWidth(int showWidth)
    {
        this.showWidth = showWidth;
    }
    /**
     * 得到页面的显示宽度
     * @return int
     */
    public int getShowWidth()
    {
        return showWidth;
    }
    /**
     * 设置页面的显示豪赌
     * @param showHeight int
     */
    public void setShowHeight(int showHeight)
    {
        this.showHeight = showHeight;
    }
    /**
     * 得到页面的显示豪赌
     * @return int
     */
    public int getShowHeight()
    {
        return showHeight;
    }
    /**
     * 设置工作区的X坐标
     * @param workX int
     */
    public void setWorkX(int workX)
    {
        this.workX = workX;
    }
    /**
     * 得到工作区的X坐标
     */
    public int getWorkX()
    {
        return workX;
    }
    /**
     * 设置工作区的Y坐标
     * @param workY int
     */
    public void setWorkY(int workY)
    {
        this.workY = workY;
    }
    /**
     * 得到工作区的Y坐标
     * @return int
     */
    public int getWorkY()
    {
        return workY;
    }
    /**
     * 设置工作区的宽度
     * @param workWidth int
     */
    public void setWorkWidth(int workWidth)
    {
        this.workWidth = workWidth;
    }
    /**
     * 得到工作区的宽度
     * @return int
     */
    public int getWorkWidth()
    {
        return workWidth;
    }
    /**
     * 设置工作区的高度
     * @param workHeight int
     */
    public void setWorkHeight(int workHeight)
    {
        this.workHeight = workHeight;
    }
    /**
     * 得到工作区的高度
     * @return int
     */
    public int getWorkHeight()
    {
        return workHeight;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TPageInf{pageIndex=");
        sb.append(getPageIndex());
        sb.append(",x=");
        sb.append(getX());
        sb.append(",y=");
        sb.append(getY());
        sb.append("}");
        return sb.toString();
    }
}
