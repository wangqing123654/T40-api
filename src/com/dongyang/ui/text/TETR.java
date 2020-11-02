package com.dongyang.ui.text;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.Vector;

public class TETR extends TElement{
    /**
     * 构造器
     */
    public TETR()
    {
        setType("TR");
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
        //paintBorder(g,pageIndex);

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
        //绘制TD
        paintTD(g,pageIndex);
        return true;
    }
    /**
     * 绘制TD
     * @param g Graphics
     * @param pageIndex int
     */
    public void paintTD(Graphics g, int pageIndex)
    {
        int count = elementSize();
        if(count == 0)
            return;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            if(!(element instanceof TETD))
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
     * 得到绘图设备
     * @param element IElement
     * @param g Graphics
     * @return Graphics
     */
    public Graphics getElementGraphics(IElement element,Graphics g)
    {
        Rectangle r = g.getClipBounds();
        if(element.getX() + getBorderLeft() > r.getX() + r.getWidth()||
           element.getY() + getBorderUp() > r.getY() + r.getHeight()||
           element.getX() + getBorderLeft() + element.getWidth() < r.getX()||
           element.getY() + getBorderUp() + element.getHeight() < r.getY())
            return null;
        Graphics g1 = g.create(element.getX() + getBorderLeft(),
                               element.getY() + getBorderUp(),
                               element.getWidth(),
                               element.getHeight());
        return g1;
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
     * 调整最佳尺寸初始化
     */
    private void fineInit()
    {
        TETable table = getTable();
        if (table == null)
            return;
        setX(0);
        setWidth(table.getInsideWidth());
        //如果是第一个TR
        if (table.getTR(0) == this) {
            setStartPage(table.getStartPage());
            setRowIndex(0);
            setY(0);
            return;
        }
    }
    /**
     * 调整最佳尺寸
     * @return boolean
     */
    public boolean fineElement()
    {
        //调整TD尺寸
        findTD();
        /*int count = elementSize();
        if(count == 0)
            return false;
        getElement(0).fine();*/
        return true;
    }
    /**
     * 调整TR最佳尺寸
     */
    public void findTD()
    {
        TETD tdElement = getTD(0);
        if(tdElement == null)
            return;
        tdElement.fine();
        //将TD调整相同的高度
        findTDHeight();
    }
    /**
     * 将TD调整相同的高度
     */
    private void findTDHeight()
    {
        Vector tds = getTDVector();
        if(tds.size() == 0)
            return;
        int h = 0;
        for(int i = 0; i < tds.size();i++)
        {
            TETD td = (TETD)tds.get(i);
            if(td.getHeight() > h)
                h = td.getHeight();
        }
        for(int i = 0; i < tds.size();i++)
            ((TETD)tds.get(i)).setHeight(h);
    }
    /**
     * 得到TD的Vector
     * @return Vector
     */
    public Vector getTDVector()
    {
        Vector data = new Vector();
        int count = elementSize();
        if(count == 0)
            return data;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            if(element instanceof TETD)
                data.add(element);
        }
        return data;
    }
    /**
     * 得到TD对象
     * @param index int
     * @return IElement
     */
    public TETD getTD(int index)
    {
        int count = elementSize();
        if(count == 0)
            return null;
        int trIndex = -1;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            if(element instanceof TETD)
            {
                trIndex ++;
                if(index == trIndex)
                    return (TETD)element;
            }
        }
        return null;
    }
    /**
     * 得到父Table对象
     * @return TETable
     */
    public TETable getTable()
    {
        if (getParentElement() == null)
            return null;
        if(!(getParentElement() instanceof TETable))
            return null;
        return(TETable)getParentElement();
    }
}
