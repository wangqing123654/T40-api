package com.dongyang.ui.text;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.Vector;

public class TETR extends TElement{
    /**
     * ������
     */
    public TETR()
    {
        setType("TR");
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

        g.setColor(new Color(0,0,0));
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        if(paintElement(g,pageIndex))
            return;
    }
    /**
     * �������
     * @param g Graphics
     * @param pageIndex int
     * @return boolean
     */
    public boolean paintElement(Graphics g, int pageIndex)
    {
        //����TD
        paintTD(g,pageIndex);
        return true;
    }
    /**
     * ����TD
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
            //���������
            if(!element.isVisible())
                continue;
            //�������ҳ��
            if(pageIndex < element.getStartPage() || pageIndex > element.getEndPage())
                continue;
            Graphics g1 = getElementGraphics(element,g);
            if(g1 == null)
                continue;
            element.paint(g1,pageIndex);
        }
    }
    /**
     * �õ���ͼ�豸
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
     * ������ѳߴ�
     */
    public void fine()
    {
        fineInit();
        if(fineElement())
            return;
        //fineText();
    }
    /**
     * ������ѳߴ��ʼ��
     */
    private void fineInit()
    {
        TETable table = getTable();
        if (table == null)
            return;
        setX(0);
        setWidth(table.getInsideWidth());
        //����ǵ�һ��TR
        if (table.getTR(0) == this) {
            setStartPage(table.getStartPage());
            setRowIndex(0);
            setY(0);
            return;
        }
    }
    /**
     * ������ѳߴ�
     * @return boolean
     */
    public boolean fineElement()
    {
        //����TD�ߴ�
        findTD();
        /*int count = elementSize();
        if(count == 0)
            return false;
        getElement(0).fine();*/
        return true;
    }
    /**
     * ����TR��ѳߴ�
     */
    public void findTD()
    {
        TETD tdElement = getTD(0);
        if(tdElement == null)
            return;
        tdElement.fine();
        //��TD������ͬ�ĸ߶�
        findTDHeight();
    }
    /**
     * ��TD������ͬ�ĸ߶�
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
     * �õ�TD��Vector
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
     * �õ�TD����
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
     * �õ���Table����
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
