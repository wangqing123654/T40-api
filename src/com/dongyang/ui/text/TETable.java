package com.dongyang.ui.text;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public class TETable extends TElement{
    /**
     * 构造器
     */
    public TETable()
    {
        setType("Table");
    }
    /**
     * 绘制图像
     * @param g Graphics
     * @param pageIndex int
     */
    public void paint(Graphics g, int pageIndex)
    {
        Rectangle r = g.getClipBounds();
        if(r.getWidth() < 0 || r.getHeight() < 0)
            return;
        paintBorder(g,pageIndex);

        g.setColor(new Color(0,0,0));
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        if(paintElement(g,pageIndex))
            return;
    }
    /**
     * 绘制组件
     * @param g Graphics
     * @param pageIndex int
     * @return boolean
     */
    public boolean paintElement(Graphics g, int pageIndex)
    {
        //绘制标题
        paintCaption(g,pageIndex);
        //绘制TR
        paintTR(g,pageIndex);
        return true;
    }
    /**
     * 绘制TR
     * @param g Graphics
     * @param pageIndex int
     */
    public void paintTR(Graphics g, int pageIndex)
    {
        int count = elementSize();
        if(count == 0)
            return;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            if(!(element instanceof TETR))
                continue;
            //组件不可视
            if(!element.isVisible())
                continue;
            //组件不在页内
            if(pageIndex < element.getStartPage() || pageIndex > element.getEndPage())
                continue;
            Graphics g1 = getElementGraphics(element,g);
            if(g1 == null)
                continue;
            element.paint(g1,pageIndex);
        }
    }
    /**
     * 绘制标题
     * @param g Graphics
     * @param pageIndex int
     */
    public void paintCaption(Graphics g, int pageIndex)
    {
        IElement element = getCaption();
        if(element == null)
            return;
        Graphics g1 = g.create(0,0,element.getWidth(),element.getHeight());
        element.paint(g1,pageIndex);
    }
    /**
     * 得到标题的高度
     * @return int
     */
    public int getCaptionHeight()
    {
        IElement element = getCaption();
        if(element == null)
            return 0;
        return element.getHeight();
    }
    /**
     * 得到绘图设备
     * @param element IElement
     * @param g Graphics
     * @return Graphics
     */
    public Graphics getElementGraphics(IElement element,Graphics g)
    {
        Rectangle r = g.getClipBounds();
        int captionHeight = getCaptionHeight();
        if(element.getX() + getBorderLeft() > r.getX() + r.getWidth()||
           element.getY() + getBorderUp() + captionHeight > r.getY() + r.getHeight()||
           element.getX() + getBorderLeft() + element.getWidth() < r.getX()||
           element.getY() + getBorderUp() + captionHeight + element.getHeight() < r.getY())
            return null;
        Graphics g1 = g.create(element.getX() + getBorderLeft(),
                               element.getY() + getBorderUp() + captionHeight,
                               element.getWidth(),
                               element.getHeight());
        return g1;
    }
    /**
     * 得到标题对象
     * @return IElement
     */
    public IElement getCaption()
    {
        if(elementSize() == 0)
            return null;
        IElement element = getElement(0);
        if(element instanceof TECaption)
            return element;
        return null;
    }
    /**
     * 得到TR对象
     * @param index int
     * @return IElement
     */
    public TETR getTR(int index)
    {
        int count = elementSize();
        if(count == 0)
            return null;
        int trIndex = -1;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            if(element instanceof TETR)
            {
                trIndex ++;
                if(index == trIndex)
                    return (TETR)element;
            }
        }
        return null;
    }
    /**
     * 绘制边框
     * @param g Graphics
     * @param pageIndex int
     */
    public void paintBorder(Graphics g, int pageIndex)
    {
        Color color1 = new Color(192,192,192);
        Color color2 = new Color(128,128,128);
        int captionHeight = getCaptionHeight();
        g.setColor(color1);
        for(int i = 0;i < getBorderUp();i++)
            g.drawLine(0,i + captionHeight,getWidth(),i + captionHeight);
        for(int i = 0;i < getBorderLeft();i++)
            g.drawLine(i,captionHeight,i,getHeight());
        g.setColor(color2);
        for(int i = 0;i < getBorderDown();i++)
        {
            int t = (int)((double)getBorderLeft() / (double)getBorderDown() * (double)i + 0.5);
            g.drawLine(t, getHeight() - i, getWidth(), getHeight() - i);
        }
        for(int i = 0;i < getBorderRight();i++)
        {
            int t = (int)((double)getBorderUp() / (double)getBorderRight() * (double)i + 0.5) + captionHeight;
            g.drawLine(getWidth() - i, t, getWidth() - i, getHeight());
        }
    }
    /**
     * 调整最佳尺寸
     */
    public void fine()
    {
        fineInit();
        if(fineElement())
            return;
        //fineText();
    }
    /**
     * 调整最佳尺寸
     * @return boolean
     */
    public boolean fineElement()
    {
        //调整标题尺寸
        fineCaption();
        findTR();
        /*int count = elementSize();
        if(count == 0)
            return false;
        getElement(0).fine();*/
        return true;
    }
    /**
     * 调整TR最佳尺寸
     */
    public void findTR()
    {
        TETR trElement = getTR(0);
        if(trElement == null)
            return;
        trElement.fine();
    }
    /**
     * 调整标题尺寸
     */
    public void fineCaption()
    {
        IElement element = getCaption();
        if(element == null)
            return;
        element.setWidth(getWidth());
        element.fine();
    }
    /**
     * 调整最佳尺寸初始化
     */
    private void fineInit()
    {
        if(getParentElement() == null)
            return;
        //如果是第一个元素
        if(getParentElement().elementIndexOf(this) == 0)
        {
            setStartPage(getParentElement().getStartPage());
            setRowIndex(0);
            setX(0);
            setY(0);
            return;
        }
        //不是第一个元素
        IElement element = getPreviousElement();
        if(element.getText().length() > 0 &&
           element.getText().charAt(element.getText().length() - 1) == '\n')
        {
            setRowIndex(element.getRowIndex() + 1);
            setX(0);
            setY(getParentElement().getRowMaxHeight(element.getRowIndex()) + element.getY());
            return;
        }
        setRowIndex(element.getRowIndex());
        setX(element.getX() + element.getWidth());
        setY(element.getY());
    }
}
