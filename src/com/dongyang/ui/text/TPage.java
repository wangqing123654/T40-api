package com.dongyang.ui.text;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;


public class TPage extends TPageBase{
    /**
     * 页面的地板颜色
     */
    public static Color PAGE_BACK_COLOR = new Color(144,153,174);
    /**
     * 普通的地板颜色
     */
    public static Color BACK_BACK_COLOR = new Color(255,255,255);
    /**
     * 页面边框颜色
     */
    public static Color PAGE_BORDER_COLOR = new Color(0,0,0);
    /**
     * 页面背景颜色
     */
    public static Color PAGE_BK_COLOR = new Color(255,255,255);
    /**
     * 定位线颜色
     */
    public static Color PAGE_ANCHOR_LINE_COLOR = new Color(170,170,170);
    /**
     * 定位线长度
     */
    public static int PAGE_ANCHOR_LINE_SIZE = 24;
    /**
     * 页面间隔尺寸
     */
    public static int PAGE_BACK_BORDER = 20;
    /**
     * 页面间隔尺寸连接后
     */
    public static int PAGE_BACK_LINK_BORDER = 2;
    /**
     * 页面边框高度尺寸连接后
     */
    public static int PAGE_LINK_BORDER = 3;
    /**
     * 标尺的背景颜色
     */
    public static Color SIZE_TOOLBAR_BACK_COLOR = new Color(216,231,252);
    /**
     * 标尺宽度
     */
    public static int SIZE_TOOLBAR_SIZE = 23;
    /**
     * 总页数
     */
    public int pageSize;
    /**
     * 当前显示页索引数组
     */
    public TPageInf[] currentShowPageIndexs;
    /**
     * 构造器
     */
    public TPage()
    {
        setPageSize(3);
    }
    /**
     * 设置总页数
     * @param pageSize int
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    /**
     * 得到中页数
     * @return int
     */
    public int getPageSize()
    {
        return pageSize;
    }
    /**
     * 得到页的X坐标
     * @param pageIndex int
     * @return int
     */
    public int getPageX(int pageIndex)
    {
        int w = getAllPageWidth();
        if(w < getWorkWidth())
            return (int)((double)(getWorkWidth() - w) / 2.0) + PAGE_BACK_BORDER;
        return PAGE_BACK_BORDER - getW();
    }
    /**
     * 得到页的Y坐标
     * @param pageIndex int 页号
     * @return int
     */
    public int getPageY(int pageIndex)
    {
        int y = pageIndex * (getPageWorkHeight() + PAGE_LINK_BORDER * 2 + PAGE_BACK_LINK_BORDER) + PAGE_BACK_LINK_BORDER;
        if(!isLinkPage())
            y = pageIndex * (PAGE_BACK_BORDER + getPageHeight()) + PAGE_BACK_BORDER;
        return y - getH();
    }
    /**
     * 得到页的显示宽度
     * @return int
     */
    public int getPageShowWidth()
    {
        return getPageWidth();
    }
    /**
     * 得到页的显示高度
     * @return int
     */
    public int getPageShowHeight()
    {
        if(!isLinkPage())
            return getPageHeight();
        return getPageHeight() - getPageUp() - getPageDown() + PAGE_LINK_BORDER * 2;
    }
    /**
     * 得到全部页面的合计高度
     * @return int
     */
    public int getAllPageHeight()
    {
        if(!isLinkPage())
            return getPageSize() * (PAGE_BACK_BORDER + getPageHeight()) + PAGE_BACK_BORDER;
        return getPageSize() * (getPageWorkHeight() + PAGE_LINK_BORDER * 2 + PAGE_BACK_LINK_BORDER) + PAGE_BACK_LINK_BORDER;
    }
    /**
     * 得到全部页面的合计宽度
     * @return int
     */
    public int getAllPageWidth()
    {
        return getPageWidth() + PAGE_BACK_BORDER * 2;
    }
    /**
     * 同步滚动条的最大位置H 页面风格
     */
    public void synchronizationScrollBarHPage()
    {
        int allPageHeight = getAllPageHeight();
        if(getWorkHeight() > allPageHeight)
        {
            setScrollBarHMax(0);
            return;
        }
        setScrollBarHMax(allPageHeight - getWorkHeight());
    }
    /**
     * 同步滚动条的最大位置W 页面风格
     */
    public void synchronizationScrollBarWPage()
    {
        int allPageWidth = getAllPageWidth();
        if(getWorkWidth() > allPageWidth)
        {
            setScrollBarWMax(0);
            return;
        }
        setScrollBarWMax(allPageWidth - getWorkWidth());
    }
    /**
     * 设置是否显示尺寸工具条
     * @param visibleSizeToolbar boolean
     */
    public void setVisibleSizeToolbar(boolean visibleSizeToolbar)
    {
        super.setVisibleSizeToolbar(visibleSizeToolbar);
        if(visibleSizeToolbar)
        {
            if("PageView".equals(getViewType()))
            {
                setUILeft(SIZE_TOOLBAR_SIZE);
                setUIUp(SIZE_TOOLBAR_SIZE);
            }
            else if("HtmlView".equals(getViewType()) ||
                    "BaseView".equals(getViewType()))
            {
                setUILeft(0);
                setUIUp(SIZE_TOOLBAR_SIZE);
            }
        }else
        {
            setUILeft(0);
            setUIUp(0);
        }
        //UI还没有初始化
        if(getUI() == null)
            return;
        //同步滚动条的最大尺寸
        synchronizationScrollBarW();
        synchronizationScrollBarH();
        getUI().repaint();
    }
    /**
     * 页面显示
     * @param g Graphics
     */
    public void paintPageView(Graphics g)
    {
        //绘制尺寸工具条
        drawSizeToolbar(g);
        //得到工作区的绘图设备
        Graphics workGraphics = getWorkGraphics(g);
        if(workGraphics == null)
            return;
        paintPageBack(workGraphics);
        drawAllPage(workGraphics);
    }
    /**
     * 网页显示
     * @param g Graphics
     */
    public void paintHtmlView(Graphics g)
    {
        //绘制尺寸工具条
        drawSizeToolbar(g);
        //得到工作区的绘图设备
        Graphics workGraphics = getWorkGraphics(g);
        if(workGraphics == null)
            return;
        paintHtmlBack(workGraphics);
        getElementRoot().paint(workGraphics,0);
    }
    /**
     * 普通
     * @param g Graphics
     */
    public void paintBaseView(Graphics g)
    {
        //绘制尺寸工具条
        drawSizeToolbar(g);
        //得到工作区的绘图设备
        Graphics workGraphics = getWorkGraphics(g);
        if(workGraphics == null)
            return;
        paintBaseBack(workGraphics);
        getElementRoot().paint(workGraphics,0);
    }
    /**
     * 得到工作区的绘图设备
     * @param g Graphics
     * @return Graphics
     */
    public Graphics getWorkGraphics(Graphics g)
    {
        Rectangle r = g.getClipBounds();
        if(getWorkX() > r.getX() + r.getWidth()||
           getWorkY() > r.getY() + r.getHeight()||
           getWorkY() + getWorkWidth() < r.getX()||
           getWorkY() + getWorkHeight() < r.getY())
            return null;
        return g.create(getWorkX(),getWorkY(),getWorkWidth(),getWorkHeight());
    }
    /**
     * 绘制全部页
     * @param g Graphics
     */
    public void drawAllPage(Graphics g)
    {
        ArrayList list = new ArrayList();
        for(int pageIndex = 0;pageIndex < getPageSize();pageIndex++)
        {
            TPageInf inf = getPageInf(pageIndex);
            Graphics gPage = getPageGraphics(g,inf);
            if(gPage == null)
                continue;
            drawPage(gPage, inf);
            list.add(inf);
        }
        currentShowPageIndexs = (TPageInf[])list.toArray(new TPageInf[]{});
    }
    /**
     * 得到页面的基本信息
     * @param pageIndex int
     * @return TPageInf
     */
    public TPageInf getPageInf(int pageIndex)
    {
        TPageInf inf = new TPageInf();
        inf.setPageIndex(pageIndex);
        inf.setX(getPageX(pageIndex));
        inf.setY(getPageY(pageIndex));
        inf.setShowWidth(getPageShowWidth());
        inf.setShowHeight(getPageShowHeight());
        inf.setWorkX(getPageLeft());
        inf.setWorkY(isLinkPage()?PAGE_LINK_BORDER:getPageUp());
        inf.setWorkWidth(getPageWorkWidth());
        inf.setWorkHeight(getPageWorkHeight());
        return inf;
    }
    /**
     * 绘制页
     * @param g Graphics
     * @param inf TPageInf
     */
    public void drawPage(Graphics g,TPageInf inf)
    {
        //绘制页面背景
        drawPageBack(g);
        //绘制页面定位线
        drawPageAnchorLine(g);
        //得到页面工作区的图形设备
        Graphics workGraphics = getPageWrokGraphics(g,inf);
        if(workGraphics == null)
            return;
        //绘制页面工作区
        drawPageWork(workGraphics,inf);
    }
    /**
     * 绘制页面工作区
     * @param g Graphics
     * @param inf TPageInf
     */
    public void drawPageWork(Graphics g,TPageInf inf)
    {
        //g.setColor(new Color(255,0,0));
        //g.fillRect(0,0,inf.getWorkWidth(),inf.getWorkHeight());
        getElementRoot().paint(g,inf.getPageIndex());
    }
    /**
     * 绘制尺寸工具条
     * @param g Graphics
     */
    public void drawSizeToolbar(Graphics g)
    {
        if(!isVisibleSizeToolbar())
            return;
        g.setColor(SIZE_TOOLBAR_BACK_COLOR);
        if("PageView".equals(getViewType()))
        {
            g.fillRect(0, 0, getUI().getWidth(), SIZE_TOOLBAR_SIZE);
            g.fillRect(0, 0, SIZE_TOOLBAR_SIZE, getUI().getHeight());
        }
        else if("BaseView".equals(getViewType())||
                "HtmlView".equals(getViewType()))
        {
            g.fillRect(0, 0, getUI().getWidth(), SIZE_TOOLBAR_SIZE);
        }
    }
    /**
     * 绘制页面背景
     * @param g Graphics
     */
    public void drawPageBack(Graphics g)
    {
        int w = getPageShowWidth();
        int h = getPageShowHeight();
        g.setColor(PAGE_BK_COLOR);
        g.fillRect(0,0,w,h);
        g.setColor(PAGE_BORDER_COLOR);
        g.drawRect(0,0,w,h);
        g.fillRect(w + 1,2,2,h);
        g.fillRect(2,h + 1,w,2);
    }
    /**
     * 绘制页面定位线
     * @param g Graphics
     */
    public void drawPageAnchorLine(Graphics g) {
        int x = getPageLeft();
        int y = getPageUp();
        int x1 = getPageRight();
        int y1 = getPageDown();
        int w = getPageWorkWidth();
        int h = getPageWorkHeight();
        if (isLinkPage())
        {
            y = PAGE_LINK_BORDER;
            y1 = PAGE_LINK_BORDER;
        }
        g.setColor(PAGE_ANCHOR_LINE_COLOR);
        //左上
        g.drawLine(x, y, x > PAGE_ANCHOR_LINE_SIZE ? x - PAGE_ANCHOR_LINE_SIZE : 1, y);
        g.drawLine(x, y, x, y > PAGE_ANCHOR_LINE_SIZE ? y - PAGE_ANCHOR_LINE_SIZE : 1);
        //右上
        g.drawLine(x + w - 1, y, x1 > PAGE_ANCHOR_LINE_SIZE ? x + w + PAGE_ANCHOR_LINE_SIZE - 1 : x + w + x1 - 1, y);
        g.drawLine(x + w - 1, y, x + w - 1, y > PAGE_ANCHOR_LINE_SIZE ? y - PAGE_ANCHOR_LINE_SIZE : 1);
        //左下
        g.drawLine(x, y + h - 1, x > PAGE_ANCHOR_LINE_SIZE ? x - PAGE_ANCHOR_LINE_SIZE : 1, y + h - 1);
        g.drawLine(x, y + h - 1, x, y1 > PAGE_ANCHOR_LINE_SIZE ? y + h + PAGE_ANCHOR_LINE_SIZE - 1: y + h + y1 - 1);
        //右下
        g.drawLine(x + w - 1, y + h - 1, x1 > PAGE_ANCHOR_LINE_SIZE ? x + w + PAGE_ANCHOR_LINE_SIZE - 1 : x + w + x1 - 1, y + h - 1);
        g.drawLine(x + w - 1, y + h, x + w - 1, y1 > PAGE_ANCHOR_LINE_SIZE ? y + h + PAGE_ANCHOR_LINE_SIZE : y + h + y1 - 1);
    }
    /**
     * 得到页面的图形设备
     * @param g Graphics 图形设备
     * @param pageInf TPageInf 页面信息
     * @return Graphics 新图形设备
     */
    public Graphics getPageGraphics(Graphics g,TPageInf pageInf)
    {
        Rectangle r = g.getClipBounds();
        if(pageInf.getX() > r.getX() + r.getWidth()||
           pageInf.getY() > r.getY() + r.getHeight()||
           pageInf.getX() + pageInf.getShowWidth() < r.getX()||
           pageInf.getY() + pageInf.getShowHeight() < r.getY())
            return null;
        return g.create(pageInf.getX(),
                        pageInf.getY(),
                        pageInf.getShowWidth() + 3,
                        pageInf.getShowHeight() + 3);
    }
    /**
     * 得到页面工作区的图形设备
     * @param g Graphics
     * @param pageInf TPageInf
     * @return Graphics
     */
    public Graphics getPageWrokGraphics(Graphics g,TPageInf pageInf)
    {
        Rectangle r = g.getClipBounds();
        if(pageInf.getWorkX() > r.getX() + r.getWidth()||
           pageInf.getWorkY() > r.getY() + r.getHeight()||
           pageInf.getWorkX() + pageInf.getWorkWidth() < r.getX()||
           pageInf.getWorkY() + pageInf.getWorkHeight() < r.getY())
            return null;
        return g.create(pageInf.getWorkX(),
                        pageInf.getWorkY(),
                        pageInf.getWorkWidth() + 1,
                        pageInf.getWorkHeight() + 1);
    }
    /**
     * 绘制页面地板颜色
     * @param g Graphics
     */
    public void paintPageBack(Graphics g)
    {
        g.setColor(PAGE_BACK_COLOR);
        g.fillRect(0,0,getWorkWidth(),getWorkHeight());
    }
    /**
     * 绘制网页显示的地板颜色
     * @param g Graphics
     */
    public void paintHtmlBack(Graphics g)
    {
        //与普通相同
        paintBaseBack(g);
    }
    /**
     * 绘制普通显示的地板颜色
     * @param g Graphics
     */
    public void paintBaseBack(Graphics g)
    {
        g.setColor(BACK_BACK_COLOR);
        g.fillRect(0,0,getWorkWidth(),getWorkHeight());
    }
}
