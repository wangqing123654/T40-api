package com.dongyang.tui.text.image;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Stroke;

/**
 *
 * <p>Title: 线段</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2010.4.7 lzk
 * @version 1.0
 */
public class GLine extends GBlock
{
    /**
     * 构造器
     */
    public GLine()
    {
        setMoveF(592);
        setTextEnterMode(2);
    }
    /**
     * 绘制边框
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void printBorder(Graphics g,int x,int y,int width,int height)
    {
        pBorder(g,x,y,width,height,true);
    }
    /**
     * 绘制边框
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintBorder(Graphics g,int x,int y,int width,int height)
    {
        pBorder(g,x,y,width,height,false);
    }
    public void pBorder(Graphics g,int x,int y,int width,int height,boolean isPrint)
    {
        if(isBorderVisible() && getBorderColor() != null)
        {
            g.setColor(getBorderColor());
            Stroke oldStroke = ((Graphics2D)g).getStroke();
            ((Graphics2D)g).setStroke(getStroke());
            if(!isLineFX() && !isLineFY() || isLineFX() && isLineFY())
            {
                g.drawLine(x,y,x + width,y + height);
                setMoveF(592);
            }else
            {
                g.drawLine(x, y + height, x + width, y);
                setMoveF(672);
            }
            if(!isLineFX() && !isLineFY())
            {
                paintArrow(g,x,y,width,height,1,5,isPrint);
                paintArrow(g,x,y,width,height,2,7,isPrint);
            }else if(isLineFX() && !isLineFY())
            {
                paintArrow(g,x,y,width,height,1,6,isPrint);
                paintArrow(g,x,y,width,height,2,8,isPrint);
            }else if(!isLineFX() && isLineFY())
            {
                paintArrow(g,x,y,width,height,1,8,isPrint);
                paintArrow(g,x,y,width,height,2,6,isPrint);
            }else
            {
                paintArrow(g,x,y,width,height,1,7,isPrint);
                paintArrow(g,x,y,width,height,2,5,isPrint);
            }
            ((Graphics2D)g).setStroke(oldStroke);
        }
    }
    /**
     * 绘制箭头
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param f int
     * @param index int
     * @param isPrint boolean
     */
    public void paintArrow(Graphics g,int x,int y,int width,int height,int f,int index,boolean isPrint)
    {
        if(!isArrowDraw(f))
            return;
        int x1,y1,x2,y2;
        double d = Math.atan((double)width/(double)height);
        double d0 = Math.PI/180.0 * getArrowDegree(f);
        double zoom = isPrint?1:getZoom() / 75.0;

        int l = (int) (getArrowLength(f) * zoom);
        double k = l / Math.cos(d0);
        y1 = (int)(Math.cos(d - d0) * k);
        x1 = (int)(Math.sin(d - d0) * k);
        y2 = (int)(Math.sin(Math.PI / 2 - d - d0) * k);
        x2 = (int)(Math.cos(Math.PI / 2 - d - d0) * k);
        switch(index)
        {
        case 5:
            paintDrawArrow(g,x,y,x1 + x,y1 + y,x2 + x,y2 + y,f,l);
            break;
        case 6:
            paintDrawArrow(g,x + width,y,x + width - x1,y1 + y,x + width - x2,y2 + y,f,l);
            break;
        case 7:
            paintDrawArrow(g,x + width,y + height,x + width - x1,y + height - y1,x + width - x2,y + height - y2,f,l);
            break;
        case 8:
            paintDrawArrow(g,x,y + height,x1 + x,y + height - y1,x2 + x,y + height - y2,f,l);
            break;
        }
    }
    public void paintDrawArrow(Graphics g,int x,int y,int x1,int y1,int x2,int y2,int f,int l)
    {
        switch(getArrowDraw(f))
        {
        case 1:
            g.drawLine(x,y,x1,y1);
            g.drawLine(x,y,x2,y2);
            break;
        case 2:
            g.fillPolygon(new int[]{x,x1,x2},new int[]{y,y1,y2},3);
            break;
        case 3:
            g.drawOval((int)(x - (double)l / 2.0),(int)(y - (double)l / 2.0),l,l);
            break;
        case 4:
            g.fillOval((int)(x - (double)l / 2.0),(int)(y - (double)l / 2.0),l,l);
            break;
        }
    }
    /**
     * 测试坐标在组件内部
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean isSelectCheck(int x,int y)
    {
        return isSelectLineCheck(x - getX(),y - getY());
    }
    /**
     * 得到类型编号
     * @return int
     */
    public int getTypeID()
    {
        return 1;
    }
    /**
     * 复制对象
     * @return GBlock
     */
    public GBlock copyObject()
    {
        GBlock block = new GLine();
        copyObject(block);
        return block;
    }
}
