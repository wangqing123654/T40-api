package com.dongyang.ui.text;

import java.util.Vector;

public class TRow {
    /**
     * ҳ��
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
     * ���
     */
    private int width;
    /**
     * �߶�
     */
    private int height;
    /**
     * Ԫ��
     */
    private Vector elements;
    /**
     * �п�ʼԪ��λ��
     */
    private int startIndex;
    /**
     * �н���Ԫ��λ��
     */
    private int endIndex;
    /**
     * �Ƿ�����ʼ��
     */
    private boolean isStart;
    /**
     * �Ƿ��ǽ�����
     */
    private boolean isEnd;
    /**
     * ������
     */
    public TRow()
    {
        elements = new Vector();
    }
    /**
     * ����ҳ��
     * @param pageIndex int
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
    /**
     * �õ�ҳ��
     * @return int
     */
    public int getPageIndex()
    {
        return pageIndex;
    }
    /**
     * ����X����
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
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * ����Ԫ��
     * @param element IElement
     */
    public void addElement(IElement element)
    {
        elements.add(element);
    }
    /**
     * ����Ԫ��
     * @param index int λ��
     * @param element IElement
     */
    public void addElement(int index,IElement element)
    {
        elements.add(index,element);
    }
    /**
     * ɾ��Ԫ��
     * @param element IElement
     */
    public void removeElement(IElement element)
    {
        elements.remove(element);
    }
    /**
     * ɾ��Ԫ��
     * @param index int λ��
     */
    public void removeElement(int index)
    {
        elements.remove(index);
    }
    /**
     * ����Ԫ��
     * @param element IElement
     * @return int λ��
     */
    public int indexOfElement(IElement element)
    {
        return elements.indexOf(element);
    }
    /**
     * ����Ԫ��
     * @param element IElement
     * @param index int ��ʼ���ҵ�λ��
     * @return int
     */
    public int indexOfElement(IElement element,int index)
    {
        return elements.lastIndexOf(element,index);
    }
    /**
     * �õ�Ԫ��
     * @param index int λ��
     * @return IElement
     */
    public IElement getElement(int index)
    {
        return (IElement)elements.get(index);
    }
    /**
     * �ÿ�Ԫ��
     */
    public void resetElement()
    {
        elements = new Vector();
    }
    /**
     * �õ�Ԫ�ظ���
     * @return int ����
     */
    public int sizeElement()
    {
        return elements.size();
    }
    /**
     * ���ÿ�ʼλ��
     * @param startIndex int
     */
    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }
    /**
     * �õ���ʼλ��
     * @return int
     */
    public int getStartIndex()
    {
        return startIndex;
    }
    /**
     * ���ý���λ��
     * @param endIndex int
     */
    public void setEndIndex(int endIndex)
    {
        this.endIndex = endIndex;
    }
    /**
     * �õ�����λ��
     * @return int
     */
    public int getEndIndex()
    {
        return endIndex;
    }
    /**
     * �����Ƿ�����ʼ��
     * @param isStart boolean
     */
    public void setIsStart(boolean isStart)
    {
        this.isStart = isStart;
    }
    /**
     * �Ƿ�����ʼ��
     * @return boolean
     */
    public boolean isStart()
    {
        return isStart;
    }
    /**
     * �����Ƿ��ǽ�����
     * @param isEnd boolean
     */
    public void setIsEnd(boolean isEnd)
    {
        this.isEnd = isEnd;
    }
    /**
     * �Ƿ��ǽ�����
     * @return boolean
     */
    public boolean isEnd()
    {
        return isEnd;
    }
}
