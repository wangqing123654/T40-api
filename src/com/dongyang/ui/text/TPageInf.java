package com.dongyang.ui.text;

public class TPageInf {
    /**
     * ҳ���
     */
    private int pageIndex;
    /**
     * x����
     */
    private int x;
    /**
     * y����
     */
    private int y;
    /**
     * ��ʾ���
     */
    private int showWidth;
    /**
     * ��ʾ�߶�
     */
    private int showHeight;
    /**
     * ��������X����
     */
    private int workX;
    /**
     * ��������Y����
     */
    private int workY;
    /**
     * �������Ŀ������
     */
    private int workWidth;
    /**
     * �������ĸ߶�����
     */
    private int workHeight;
    /**
     * ����ҳ���
     * @param pageIndex int
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
    /**
     * �õ�ҳ���
     * @return int
     */
    public int getPageIndex()
    {
        return pageIndex;
    }
    /**
     * ����x����
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * �õ�x����
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * ����y����
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * �õ�y����
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * ����ҳ�����ʾ���
     * @param showWidth int
     */
    public void setShowWidth(int showWidth)
    {
        this.showWidth = showWidth;
    }
    /**
     * �õ�ҳ�����ʾ���
     * @return int
     */
    public int getShowWidth()
    {
        return showWidth;
    }
    /**
     * ����ҳ�����ʾ����
     * @param showHeight int
     */
    public void setShowHeight(int showHeight)
    {
        this.showHeight = showHeight;
    }
    /**
     * �õ�ҳ�����ʾ����
     * @return int
     */
    public int getShowHeight()
    {
        return showHeight;
    }
    /**
     * ���ù�������X����
     * @param workX int
     */
    public void setWorkX(int workX)
    {
        this.workX = workX;
    }
    /**
     * �õ���������X����
     */
    public int getWorkX()
    {
        return workX;
    }
    /**
     * ���ù�������Y����
     * @param workY int
     */
    public void setWorkY(int workY)
    {
        this.workY = workY;
    }
    /**
     * �õ���������Y����
     * @return int
     */
    public int getWorkY()
    {
        return workY;
    }
    /**
     * ���ù������Ŀ��
     * @param workWidth int
     */
    public void setWorkWidth(int workWidth)
    {
        this.workWidth = workWidth;
    }
    /**
     * �õ��������Ŀ��
     * @return int
     */
    public int getWorkWidth()
    {
        return workWidth;
    }
    /**
     * ���ù������ĸ߶�
     * @param workHeight int
     */
    public void setWorkHeight(int workHeight)
    {
        this.workHeight = workHeight;
    }
    /**
     * �õ��������ĸ߶�
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
