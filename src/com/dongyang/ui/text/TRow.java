package com.dongyang.ui.text;

import java.util.Vector;

public class TRow {
    /**
     * 页号
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
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 元素
     */
    private Vector elements;
    /**
     * 行开始元素位置
     */
    private int startIndex;
    /**
     * 行结束元素位置
     */
    private int endIndex;
    /**
     * 是否是起始行
     */
    private boolean isStart;
    /**
     * 是否是结束行
     */
    private boolean isEnd;
    /**
     * 构造器
     */
    public TRow()
    {
        elements = new Vector();
    }
    /**
     * 设置页号
     * @param pageIndex int
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
    /**
     * 得到页号
     * @return int
     */
    public int getPageIndex()
    {
        return pageIndex;
    }
    /**
     * 设置X坐标
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
    /**
     * 增加元素
     * @param element IElement
     */
    public void addElement(IElement element)
    {
        elements.add(element);
    }
    /**
     * 增加元素
     * @param index int 位置
     * @param element IElement
     */
    public void addElement(int index,IElement element)
    {
        elements.add(index,element);
    }
    /**
     * 删除元素
     * @param element IElement
     */
    public void removeElement(IElement element)
    {
        elements.remove(element);
    }
    /**
     * 删除元素
     * @param index int 位置
     */
    public void removeElement(int index)
    {
        elements.remove(index);
    }
    /**
     * 查找元素
     * @param element IElement
     * @return int 位置
     */
    public int indexOfElement(IElement element)
    {
        return elements.indexOf(element);
    }
    /**
     * 查找元素
     * @param element IElement
     * @param index int 开始查找的位置
     * @return int
     */
    public int indexOfElement(IElement element,int index)
    {
        return elements.lastIndexOf(element,index);
    }
    /**
     * 得到元素
     * @param index int 位置
     * @return IElement
     */
    public IElement getElement(int index)
    {
        return (IElement)elements.get(index);
    }
    /**
     * 置空元素
     */
    public void resetElement()
    {
        elements = new Vector();
    }
    /**
     * 得到元素个数
     * @return int 个数
     */
    public int sizeElement()
    {
        return elements.size();
    }
    /**
     * 设置开始位置
     * @param startIndex int
     */
    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }
    /**
     * 得到开始位置
     * @return int
     */
    public int getStartIndex()
    {
        return startIndex;
    }
    /**
     * 设置结束位置
     * @param endIndex int
     */
    public void setEndIndex(int endIndex)
    {
        this.endIndex = endIndex;
    }
    /**
     * 得到结束位置
     * @return int
     */
    public int getEndIndex()
    {
        return endIndex;
    }
    /**
     * 设置是否是起始行
     * @param isStart boolean
     */
    public void setIsStart(boolean isStart)
    {
        this.isStart = isStart;
    }
    /**
     * 是否是起始行
     * @return boolean
     */
    public boolean isStart()
    {
        return isStart;
    }
    /**
     * 设置是否是结束行
     * @param isEnd boolean
     */
    public void setIsEnd(boolean isEnd)
    {
        this.isEnd = isEnd;
    }
    /**
     * 是否是结束行
     * @return boolean
     */
    public boolean isEnd()
    {
        return isEnd;
    }
}
