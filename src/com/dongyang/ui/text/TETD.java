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
     * ����ͼ��
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
     * ������ѳߴ�
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
     * ������ѳߴ��ʼ��
     */
    public void fineInit()
    {
        TETR tr = getTR();
        TETable table = getTable();
        if (table == null)
            return;
        //����ǵ�һ��TR
        if (tr.getTD(0) == this) {
            setStartPage(table.getStartPage());
            setRowIndex(0);
            setX(0);
            setY(0);
            if(getWidth() == 0)
                setWidth(tr.getInsideWidth());
            return;
        }
        //���ǵ�һ��TR
        TETD previousTD = getPreviousTD();
        setX(previousTD.getX() + previousTD.getWidth());
        setY(previousTD.getY());
    }
    /**
     * �õ���TR����
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
     * �õ���Table����
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
     * �õ�ǰһ��TD
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
     * �õ���һ��TD
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
