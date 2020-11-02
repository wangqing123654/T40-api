package com.dongyang.ui.text;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;


public class TPage extends TPageBase{
    /**
     * ҳ��ĵذ���ɫ
     */
    public static Color PAGE_BACK_COLOR = new Color(144,153,174);
    /**
     * ��ͨ�ĵذ���ɫ
     */
    public static Color BACK_BACK_COLOR = new Color(255,255,255);
    /**
     * ҳ��߿���ɫ
     */
    public static Color PAGE_BORDER_COLOR = new Color(0,0,0);
    /**
     * ҳ�汳����ɫ
     */
    public static Color PAGE_BK_COLOR = new Color(255,255,255);
    /**
     * ��λ����ɫ
     */
    public static Color PAGE_ANCHOR_LINE_COLOR = new Color(170,170,170);
    /**
     * ��λ�߳���
     */
    public static int PAGE_ANCHOR_LINE_SIZE = 24;
    /**
     * ҳ�����ߴ�
     */
    public static int PAGE_BACK_BORDER = 20;
    /**
     * ҳ�����ߴ����Ӻ�
     */
    public static int PAGE_BACK_LINK_BORDER = 2;
    /**
     * ҳ��߿�߶ȳߴ����Ӻ�
     */
    public static int PAGE_LINK_BORDER = 3;
    /**
     * ��ߵı�����ɫ
     */
    public static Color SIZE_TOOLBAR_BACK_COLOR = new Color(216,231,252);
    /**
     * ��߿��
     */
    public static int SIZE_TOOLBAR_SIZE = 23;
    /**
     * ��ҳ��
     */
    public int pageSize;
    /**
     * ��ǰ��ʾҳ��������
     */
    public TPageInf[] currentShowPageIndexs;
    /**
     * ������
     */
    public TPage()
    {
        setPageSize(3);
    }
    /**
     * ������ҳ��
     * @param pageSize int
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    /**
     * �õ���ҳ��
     * @return int
     */
    public int getPageSize()
    {
        return pageSize;
    }
    /**
     * �õ�ҳ��X����
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
     * �õ�ҳ��Y����
     * @param pageIndex int ҳ��
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
     * �õ�ҳ����ʾ���
     * @return int
     */
    public int getPageShowWidth()
    {
        return getPageWidth();
    }
    /**
     * �õ�ҳ����ʾ�߶�
     * @return int
     */
    public int getPageShowHeight()
    {
        if(!isLinkPage())
            return getPageHeight();
        return getPageHeight() - getPageUp() - getPageDown() + PAGE_LINK_BORDER * 2;
    }
    /**
     * �õ�ȫ��ҳ��ĺϼƸ߶�
     * @return int
     */
    public int getAllPageHeight()
    {
        if(!isLinkPage())
            return getPageSize() * (PAGE_BACK_BORDER + getPageHeight()) + PAGE_BACK_BORDER;
        return getPageSize() * (getPageWorkHeight() + PAGE_LINK_BORDER * 2 + PAGE_BACK_LINK_BORDER) + PAGE_BACK_LINK_BORDER;
    }
    /**
     * �õ�ȫ��ҳ��ĺϼƿ��
     * @return int
     */
    public int getAllPageWidth()
    {
        return getPageWidth() + PAGE_BACK_BORDER * 2;
    }
    /**
     * ͬ�������������λ��H ҳ����
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
     * ͬ�������������λ��W ҳ����
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
     * �����Ƿ���ʾ�ߴ繤����
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
        //UI��û�г�ʼ��
        if(getUI() == null)
            return;
        //ͬ�������������ߴ�
        synchronizationScrollBarW();
        synchronizationScrollBarH();
        getUI().repaint();
    }
    /**
     * ҳ����ʾ
     * @param g Graphics
     */
    public void paintPageView(Graphics g)
    {
        //���Ƴߴ繤����
        drawSizeToolbar(g);
        //�õ��������Ļ�ͼ�豸
        Graphics workGraphics = getWorkGraphics(g);
        if(workGraphics == null)
            return;
        paintPageBack(workGraphics);
        drawAllPage(workGraphics);
    }
    /**
     * ��ҳ��ʾ
     * @param g Graphics
     */
    public void paintHtmlView(Graphics g)
    {
        //���Ƴߴ繤����
        drawSizeToolbar(g);
        //�õ��������Ļ�ͼ�豸
        Graphics workGraphics = getWorkGraphics(g);
        if(workGraphics == null)
            return;
        paintHtmlBack(workGraphics);
        getElementRoot().paint(workGraphics,0);
    }
    /**
     * ��ͨ
     * @param g Graphics
     */
    public void paintBaseView(Graphics g)
    {
        //���Ƴߴ繤����
        drawSizeToolbar(g);
        //�õ��������Ļ�ͼ�豸
        Graphics workGraphics = getWorkGraphics(g);
        if(workGraphics == null)
            return;
        paintBaseBack(workGraphics);
        getElementRoot().paint(workGraphics,0);
    }
    /**
     * �õ��������Ļ�ͼ�豸
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
     * ����ȫ��ҳ
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
     * �õ�ҳ��Ļ�����Ϣ
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
     * ����ҳ
     * @param g Graphics
     * @param inf TPageInf
     */
    public void drawPage(Graphics g,TPageInf inf)
    {
        //����ҳ�汳��
        drawPageBack(g);
        //����ҳ�涨λ��
        drawPageAnchorLine(g);
        //�õ�ҳ�湤������ͼ���豸
        Graphics workGraphics = getPageWrokGraphics(g,inf);
        if(workGraphics == null)
            return;
        //����ҳ�湤����
        drawPageWork(workGraphics,inf);
    }
    /**
     * ����ҳ�湤����
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
     * ���Ƴߴ繤����
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
     * ����ҳ�汳��
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
     * ����ҳ�涨λ��
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
        //����
        g.drawLine(x, y, x > PAGE_ANCHOR_LINE_SIZE ? x - PAGE_ANCHOR_LINE_SIZE : 1, y);
        g.drawLine(x, y, x, y > PAGE_ANCHOR_LINE_SIZE ? y - PAGE_ANCHOR_LINE_SIZE : 1);
        //����
        g.drawLine(x + w - 1, y, x1 > PAGE_ANCHOR_LINE_SIZE ? x + w + PAGE_ANCHOR_LINE_SIZE - 1 : x + w + x1 - 1, y);
        g.drawLine(x + w - 1, y, x + w - 1, y > PAGE_ANCHOR_LINE_SIZE ? y - PAGE_ANCHOR_LINE_SIZE : 1);
        //����
        g.drawLine(x, y + h - 1, x > PAGE_ANCHOR_LINE_SIZE ? x - PAGE_ANCHOR_LINE_SIZE : 1, y + h - 1);
        g.drawLine(x, y + h - 1, x, y1 > PAGE_ANCHOR_LINE_SIZE ? y + h + PAGE_ANCHOR_LINE_SIZE - 1: y + h + y1 - 1);
        //����
        g.drawLine(x + w - 1, y + h - 1, x1 > PAGE_ANCHOR_LINE_SIZE ? x + w + PAGE_ANCHOR_LINE_SIZE - 1 : x + w + x1 - 1, y + h - 1);
        g.drawLine(x + w - 1, y + h, x + w - 1, y1 > PAGE_ANCHOR_LINE_SIZE ? y + h + PAGE_ANCHOR_LINE_SIZE : y + h + y1 - 1);
    }
    /**
     * �õ�ҳ���ͼ���豸
     * @param g Graphics ͼ���豸
     * @param pageInf TPageInf ҳ����Ϣ
     * @return Graphics ��ͼ���豸
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
     * �õ�ҳ�湤������ͼ���豸
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
     * ����ҳ��ذ���ɫ
     * @param g Graphics
     */
    public void paintPageBack(Graphics g)
    {
        g.setColor(PAGE_BACK_COLOR);
        g.fillRect(0,0,getWorkWidth(),getWorkHeight());
    }
    /**
     * ������ҳ��ʾ�ĵذ���ɫ
     * @param g Graphics
     */
    public void paintHtmlBack(Graphics g)
    {
        //����ͨ��ͬ
        paintBaseBack(g);
    }
    /**
     * ������ͨ��ʾ�ĵذ���ɫ
     * @param g Graphics
     */
    public void paintBaseBack(Graphics g)
    {
        g.setColor(BACK_BACK_COLOR);
        g.fillRect(0,0,getWorkWidth(),getWorkHeight());
    }
}
