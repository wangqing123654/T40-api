package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.awt.Graphics;
import com.dongyang.tui.DText;
import java.awt.Color;
import com.dongyang.tui.DScrollBar;
import com.dongyang.tui.DInsets;
import com.dongyang.ui.util.TDrawTool;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import com.dongyang.util.TypeTool;

/**
 *
 * <p>Title: ��ʾ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @version 1.0
 */
public class MView
{
    /**
     * ҳ����߿�ߴ�
     */
    private int pageBorderSize = 16;
    /**
     * ҳ��߿�
     */
    public boolean PAGE_RECT = true;
    /**
     * ������ɫ
     */
    public Color backColor = new Color(144,153,174);
    /**
     * ������
     */
    private PM pm;
    /**
     * ҳ����
     * 0 ��ͨ
     * 1 ҳ��
     */
    private int viewStyle = 1;
    /**
     * ���ű���
     */
    private double zoom = 100.0;
    /**
     * ���� EPage
     */
    private boolean pageDebug = false;
    /**
     * ���� EPanel
     */
    private boolean panelDebug = false;
    /**
     * ���� EText
     */
    private boolean textDebug = false;
    /**
     * ���� ETR
     */
    private boolean trDebug = false;
    /**
     * ���� ETD
     */
    private boolean tdDebug = false;
    /**
     * ���� EFixed
     */
    private boolean fixedDebug = false;
    /**
     * ���� ��
     */
    private boolean macroroutineDebug = false;
    /**
     * �Ƿ�������״̬
     */
    private boolean isPreview;
    /**
     * �Ƿ���ʾ�к�
     */
    private boolean isShowRowID = false;
    /**
     * ������
     */
    public MView()
    {
    }
    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * ����ҳ����
     * @param viewStyle int
     * 0 ��ͨ
     * 1 ҳ��
     */
    public void setViewStyle(int viewStyle)
    {
        this.viewStyle = viewStyle;
        if(viewStyle == 0)
            initBaseView();
    }
    /**
     * ��ʼ��������ʾ
     */
    public void initBaseView()
    {
        DInsets insets = getPageManager().getInsets();
        insets.left = 30;
        insets.right = 0;
        insets.top = 0;
        insets.bottom = 0;
        getPageManager().setWidth(100);
        getPageManager().setHeight(100);
        EPage page = getPageManager().get(0);
        page.get(0).get(0).setModify(true);
        setPageBorderSize(0);
    }
    /**
     * �õ�ҳ����
     * @return int
     * 0 ��ͨ
     * 1 ҳ��
     */
    public int getViewStyle()
    {
        return viewStyle;
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }
    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPM().getPageManager();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * �������ű���
     * @param zoom double
     */
    public void setZoom(double zoom)
    {
        this.zoom = zoom;
    }
    /**
     * �õ����ű���
     * @return double
     */
    public double getZoom()
    {
        return zoom;
    }
    /**
     * ��������״̬
     * @param isPreview boolean
     */
    public void setPreview(boolean isPreview)
    {
        this.isPreview = isPreview;
    }
    /**
     * �Ƿ�������״̬
     * @return boolean
     */
    public boolean isPreview()
    {
        return isPreview;
    }
    /**
     * Ҫ���ػ�
     */
    public void repaint()
    {
        getUI().repaint();
    }
    /**
     * �����ߴ�
     */
    public void resetSize()
    {
        switch(getViewStyle())
        {
        case 0:
            resetBaseStyleSize();
            break;
        case 1:
            resetPageStyleSize();
            break;
        }
    }
    /**
     * �����������ߴ�
     */
    public void resetBaseStyleSize()
    {
        if(getUI() == null)
            return;
        //�õ�UI�ڲ���ʾ�����
        int width = getUI().getInsetWidth();
        //�õ�UI�ڲ���ʾ���߶�
        int height = getUI().getInsetHeight();
        //�õ������ʾ���
        int maxWidth = getPageShowWidth() + 100;
        //�õ������ʾ�߶�
        int maxHeight = getPageShowHeight() + 2;
        //������ݹ�������ʾ���
        boolean showH = maxWidth > width;
        boolean showV = maxHeight > height - (showH?17:0);
        if(showV)
            showH = maxWidth > width - 17;
        if(showH)
            showV = maxHeight > height - 17;
        //���ú��������
        if(showH)
        {
            getUI().setShowHScrollBar(true);
            DScrollBar hScrollBar = getUI().getHScrollBar();
            hScrollBar.setMaximum(maxWidth - width + (showV?17:0));
            hScrollBar.setVisibleAmount(width);
        }else
            getUI().setShowHScrollBar(false);
        //�������������
        if(showV)
        {
            getUI().setShowVScrollBar(true);
            DScrollBar vScrollBar = getUI().getVScrollBar();
            vScrollBar.setMaximum(maxHeight - height + (showH?17:0));
            vScrollBar.setVisibleAmount(height);
        }else
            getUI().setShowVScrollBar(false);
    }
    /**
     * ����ҳ����ߴ�
     */
    public void resetPageStyleSize()
    {
        if(getUI() == null)
            return;
        //�õ�UI�ڲ���ʾ�����
        int width = getUI().getInsetWidth();
        //�õ�UI�ڲ���ʾ���߶�
        int height = getUI().getInsetHeight();
        //�õ�ҳ���������ʾ���
        int maxWidth = getPageSytleMaxWidth();
        //�õ�ҳ���������ʾ�߶�
        int maxHeight = getPageSytleMaxHeight();
        //������ݹ�������ʾ���
        boolean showH = maxWidth > width;
        boolean showV = maxHeight > height - (showH?17:0);
        if(showV)
            showH = maxWidth > width - 17;
        if(showH)
            showV = maxHeight > height - 17;
        //���ú��������
        if(showH)
        {
            getUI().setShowHScrollBar(true);
            DScrollBar hScrollBar = getUI().getHScrollBar();
            hScrollBar.setMaximum(maxWidth - width + (showV?17:0));
            hScrollBar.setVisibleAmount(width);
        }else
            getUI().setShowHScrollBar(false);
        //�������������
        if(showV)
        {
            getUI().setShowVScrollBar(true);
            DScrollBar vScrollBar = getUI().getVScrollBar();
            vScrollBar.setMaximum(maxHeight - height + (showH?17:0));
            vScrollBar.setVisibleAmount(height);
        }else
            getUI().setShowVScrollBar(false);
    }
    /**
     * �Ƿ���ʾ���������
     * @return boolean
     */
    public boolean isShowHScrollBar()
    {
        return getUI().isShowHScrollBar();
    }
    /**
     * �Ƿ���ʾ���������
     * @return boolean
     */
    public boolean isShowVScrollBar()
    {
        return getUI().isShowVScrollBar();
    }
    /**
     * �õ����������λ��
     * @return int
     */
    public int getHScrollBarValue()
    {
        if(!isShowHScrollBar())
            return 0;
        return getUI().getHScrollBar().getValue();
    }
    /**
     * �õ����������λ��
     * @return int
     */
    public int getVScrollBarValue()
    {
        if(!isShowVScrollBar())
            return 0;
        return getUI().getVScrollBar().getValue();
    }
    /**
     * �õ�ҳ���������ʾ���
     * @return int
     */
    public int getPageSytleMaxWidth()
    {
        return getPageBorderSize() * 2 + getPageShowWidth();
    }
    /**
     * ҳ����ʾ���
     * @return int
     */
    public int getPageShowWidth()
    {
        return (int)(getPageWidth() * getZoom() / 75.0);
    }
    /**
     * ҳ����ʾ�߶�
     * @return int
     */
    public int getPageShowHeight()
    {
        return (int)(getPageHeight() * getZoom() / 75.0);
    }
    /**
     * �õ�ҳ����
     * @return int
     */
    public int getPageWidth()
    {
        return getPageManager().getWidth();
    }
    /**
     * �õ�ҳ��߶�
     * @return int
     */
    public int getPageHeight()
    {
        return getPageManager().getHeight();
    }
    /**
     * �õ�ҳ���������ʾ�߶�
     * @return int
     */
    public int getPageSytleMaxHeight()
    {
        //ҳ��ߴ�
        int size = getPageManager().size();
        //ҳ��߶�
        int height = getPageShowHeight();
        return size * (height + getPageBorderSize()) + getPageBorderSize();
    }
    /**
     * ���Ʊ���
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int width,int height)
    {
        switch(getViewStyle())
        {
        case 0://�������
            paintBaseView(g,width,height);
            break;
        case 1://ҳ����
            paintPageView(g,width,height);
            break;
        }
    }
    /**
     * �õ���ʾ�����
     * @return int
     */
    public int getViewWidth()
    {
        return getUI().getInsetWidth() - (isShowVScrollBar()?17:0);
    }
    /**
     * �õ���ʾ���ĸ߶�
     * @return int
     */
    public int getViewHeight()
    {
        return getUI().getInsetHeight() - (isShowHScrollBar()?17:0);
    }
    /**
     * �õ���ʾ��������
     * @return int
     */
    public int getViewX()
    {
        //��ʾ�����
        int width = getViewWidth();
        //ҳ����
        int pageWidth = getPageShowWidth();
        int x = getPageBorderSize();
        if(getViewStyle() != 0 && pageWidth + getPageBorderSize() * 2 < width)
            x = (width - pageWidth) / 2;
        else
            x -= getHScrollBarValue();
        return x;
    }
    /**
     * �õ���ʾ��ҳ��������
     * @param pageIndex int ҳ��
     * @return int
     */
    public int getViewY(int pageIndex)
    {
        //������λ��
        int y = getVScrollBarValue();
        //ҳ��߶�
        int pageHeight = getPageShowHeight();
        return pageIndex * (pageHeight + getPageBorderSize()) + getPageBorderSize() - y;
    }
    /**
     * �õ���ʾ������ʾҳ��
     * @return int
     */
    public int getStartPageIndex()
    {
        //������λ��
        int y = getVScrollBarValue();
        //ҳ��߶�
        int pageHeight = getPageShowHeight();
        return (int)((double)y / (double)(pageHeight + getPageBorderSize()));
    }
    /**
     * �õ���ʾ���Ľ���ҳ��
     * @return int
     */
    public int getEndPageIndex()
    {
        int y = getVScrollBarValue();
        //��ʾ���ĸ߶�
        int height = getViewHeight();
        //ҳ��߶�
        int pageHeight = getPageShowHeight();
        //ҳ�����
        int pageSize = getPageManager().size();
        int endPage = (int)((double)(y + height) / (double)(pageHeight + getPageBorderSize()) + 0.9999);
        if(endPage > pageSize)
            endPage = pageSize;
        return endPage;
    }
    /**
     * ����ǰ��
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {

    }
    /**
     * ���ƻ������
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBaseView(Graphics g,int width,int height)
    {
        int size = getPageManager().size();
        int y = getVScrollBarValue();
        int x = getHScrollBarValue();
        for(int i = 0;i < size;i++)
        {
            EPage page = getPageManager().get(i);
            if(page == null)
                continue;
            //����ҳ��
            paintBasePage(g,-x,-y,i);
        }
    }
    /**
     * ����ҳ����
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintPageView(Graphics g,int width,int height)
    {
        //���Ƶ�ɫ
        g.setColor(backColor);
        g.fillRect(0,0,width,height);

        //ҳ����
        int pageWidth = getPageShowWidth();
        //ҳ��߶�
        int pageHeight = getPageShowHeight();
        //��ʾ������ʾҳ��
        int startPage = getStartPageIndex();
        //��ʾ���Ľ���ҳ��
        int endPage = getEndPageIndex();
        //����X������
        int pageX = getViewX();
        //������ʾ��ҳ��
        for(int i = startPage;i < endPage;i++)
        {
            //�õ���ʾ��ҳ��������
            int pageY = getViewY(i);
            //����ҳ��
            paintPage(g,pageX,pageY,pageWidth,pageHeight,i);
        }
    }
    /**
     * ���ƻ���ҳ��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */

    public void paintBasePage(Graphics g,int x,int y,int pageIndex)
    {
        //����ҳ��
        EPage page = getPageManager().get(pageIndex);
        if(page == null)
            return;
        page.paint(g,x,y,page.getWidth(),page.getHeight());
    }
    /**
     * ����ҳ��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param pageIndex int
     */
    public void paintPage(Graphics g,int x,int y,int width,int height,int pageIndex)
    {
        if(PAGE_RECT)
        {
            //�߿�
            g.setColor(new Color(0,0,0));
            g.drawRect(x-1,y-1,width + 2,height + 2);
            g.fillRect(x + 1,y + height + 2,width + 3,2);
            g.fillRect(x + width + 2,y + 1,2,height + 2);
            //��ɫ
            g.setColor(new Color(255,255,255));
            g.fillRect(x,y,width + 1,height + 1);
        }
        //����������
        TDrawTool.drawRectInsets(getPageManager().getInsets(),x,y,width,height,new Color(170,170,170),24,getZoom(),g);

        /*double zoom = getZoom() / 75.0;
        AffineTransform at = new AffineTransform();
        System.out.println(zoom);
        at.scale(zoom,zoom);
        Graphics2D g2 = (Graphics2D)g;
        g2.setTransform(at);*/

        //���Ʊ���
        getPageManager().paintBackground(g,x,y,pageIndex);


        //����ҳ��
        EPage page = getPageManager().get(pageIndex);
        if(page == null)
            return;
        page.paint(g,x,y,width,height);
        //���ƽ���
        getFocusManager().paintPage(g,x,y,width,height,pageIndex);

        //����ǰ��
        getPageManager().paintForeground(g,x,y,pageIndex);

        //����
        //g.setFont(new Font("����",0,12));
        //g.setColor(new Color(255,0,0));
        //g.drawString("page " + pageIndex,30,30);
    }
    /**
     * �Ƿ���� EPage
     * @return boolean
     */
    public boolean isPageDebug()
    {
        return pageDebug;
    }
    /**
     * �Ƿ���� EPanel
     * @return boolean
     */
    public boolean isPanelDebug()
    {
        return panelDebug;
    }
    /**
     * �Ƿ���� EText
     * @return boolean
     */
    public boolean isTextDebug()
    {
        return textDebug;
    }
    /**
     * �Ƿ���� ETR
     * @return boolean
     */
    public boolean isTRDebug()
    {
        return trDebug;
    }
    /**
     * �Ƿ���� ETD
     * @return boolean
     */
    public boolean isTDDebug()
    {
        return tdDebug;
    }
    /**
     * �Ƿ���� EFixed
     * @return boolean
     */
    public boolean isFixedDebug()
    {
        return fixedDebug;
    }
    /**
     * �Ƿ���� ��
     * @return boolean
     */
    public boolean isMacroroutineDebug()
    {
        return macroroutineDebug;
    }
    /**
     * �������ųߴ�(�ַ�����)
     * @param zoom String
     */
    public void setZoomString(String zoom)
    {
        String s = TypeTool.getString(zoom);
        if(s == null || s.length() == 0)
            return;
        if(s.endsWith("%"))
            s = s.substring(0,s.length() - 1);
        try{
            double d = Double.parseDouble(s);
            if(d == getZoom())
                return;
            setZoom(d);
            getFocusManager().update();
            resetSize();
        }catch(Exception e)
        {
            System.out.println("MView.setZoomString(" + zoom + ") :�����Ƿ�");
        }
    }
    /**
     * �õ����ųߴ�(�ַ�����)
     * @return String
     */
    public String getZoomString()
    {
        double zoom = getZoom();
        if((int)zoom == zoom)
            return (int)zoom + "%";
        return zoom + "%";
    }
    /**
     * ����ҳ����߿�ߴ�
     * @param pageBorderSize int
     */
    public void setPageBorderSize(int pageBorderSize)
    {
        if(this.pageBorderSize == pageBorderSize)
            return;
        this.pageBorderSize = pageBorderSize;
        this.getFocusManager().update();
    }
    /**
     * �õ�ҳ����߿�ߴ�
     * @return int
     */
    public int getPageBorderSize()
    {
        return pageBorderSize;
    }
    /**
     * ������ʾ�к�
     * @param isShowRowID boolean
     */
    public void setShowRowID(boolean isShowRowID)
    {
        this.isShowRowID = isShowRowID;
    }
    /**
     * �Ƿ���ʾ�к�
     * @return boolean
     */
    public boolean isShowRowID()
    {
        return isShowRowID;
    }
}
