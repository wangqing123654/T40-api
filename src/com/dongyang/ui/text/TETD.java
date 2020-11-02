package com.dongyang.ui.text;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public class TETD extends TEText{
    public TETD()
    {
        setType("TD");
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

        g.setColor(new Color(255,0,0));
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        if(paintElement(g,pageIndex))
            return;
    }
    /**
     * 调整最佳尺寸
     */
    public void fine()
    {
        fineInit();
        if(fineElement())
        {
            TETD nextTD = getNextTD();
            if(nextTD != null)
                nextTD.fine();
            return;
        }
        fineText();
    }
    /**
     * 调整最佳尺寸初始化
     */
    public void fineInit()
    {
        TETR tr = getTR();
        TETable table = getTable();
        if (table == null)
            return;
        //如果是第一个TR
        if (tr.getTD(0) == this) {
            setStartPage(table.getStartPage());
            setRowIndex(0);
            setX(0);
            setY(0);
            if(getWidth() == 0)
                setWidth(tr.getInsideWidth());
            return;
        }
        //不是第一个TR
        TETD previousTD = getPreviousTD();
        setX(previousTD.getX() + previousTD.getWidth());
        setY(previousTD.getY());
    }
    /**
     * 得到父TR对象
     * @return TETR
     */
    public TETR getTR()
    {
        if (getParentElement() == null)
            return null;
        if(!(getParentElement() instanceof TETR))
            return null;
        return(TETR)getParentElement();
    }
    /**
     * 得到父Table对象
     * @return TETable
     */
    public TETable getTable()
    {
        TETR tr = getTR();
        if (tr == null)
            return null;
        if(!(tr.getParentElement() instanceof TETable))
            return null;
        return(TETable)tr.getParentElement();
    }
    /**
     * 得到前一个TD
     * @return TETD
     */
    public TETD getPreviousTD()
    {
        IElement element = getPreviousElement();
        while(element != null)
        {
            if(element instanceof TETD)
                return (TETD)element;
            element = element.getPreviousElement();
        }
        return null;
    }
    /**
     * 得到下一个TD
     * @return TETD
     */
    public TETD getNextTD()
    {
        IElement element = getNextElement();
        while(element != null)
        {
            if(element instanceof TETD)
                return (TETD)element;
            element = element.getNextElement();
        }
        return null;
    }
}
