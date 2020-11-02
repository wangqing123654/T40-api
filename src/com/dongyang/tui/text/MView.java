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
 * <p>Title: 显示层</p>
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
     * 页面风格边框尺寸
     */
    private int pageBorderSize = 16;
    /**
     * 页面边框
     */
    public boolean PAGE_RECT = true;
    /**
     * 背景颜色
     */
    public Color backColor = new Color(144,153,174);
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 页面风格
     * 0 普通
     * 1 页面
     */
    private int viewStyle = 1;
    /**
     * 缩放比例
     */
    private double zoom = 100.0;
    /**
     * 调试 EPage
     */
    private boolean pageDebug = false;
    /**
     * 调试 EPanel
     */
    private boolean panelDebug = false;
    /**
     * 调试 EText
     */
    private boolean textDebug = false;
    /**
     * 调试 ETR
     */
    private boolean trDebug = false;
    /**
     * 调试 ETD
     */
    private boolean tdDebug = false;
    /**
     * 调试 EFixed
     */
    private boolean fixedDebug = false;
    /**
     * 调试 宏
     */
    private boolean macroroutineDebug = false;
    /**
     * 是否是阅览状态
     */
    private boolean isPreview;
    /**
     * 是否显示行号
     */
    private boolean isShowRowID = false;
    /**
     * 构造器
     */
    public MView()
    {
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 设置页面风格
     * @param viewStyle int
     * 0 普通
     * 1 页面
     */
    public void setViewStyle(int viewStyle)
    {
        this.viewStyle = viewStyle;
        if(viewStyle == 0)
            initBaseView();
    }
    /**
     * 初始化基础显示
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
     * 得到页面风格
     * @return int
     * 0 普通
     * 1 页面
     */
    public int getViewStyle()
    {
        return viewStyle;
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }
    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPM().getPageManager();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 设置缩放比例
     * @param zoom double
     */
    public void setZoom(double zoom)
    {
        this.zoom = zoom;
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom()
    {
        return zoom;
    }
    /**
     * 设置阅览状态
     * @param isPreview boolean
     */
    public void setPreview(boolean isPreview)
    {
        this.isPreview = isPreview;
    }
    /**
     * 是否是阅览状态
     * @return boolean
     */
    public boolean isPreview()
    {
        return isPreview;
    }
    /**
     * 要求重画
     */
    public void repaint()
    {
        getUI().repaint();
    }
    /**
     * 调整尺寸
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
     * 调整基础风格尺寸
     */
    public void resetBaseStyleSize()
    {
        if(getUI() == null)
            return;
        //得到UI内部显示区宽度
        int width = getUI().getInsetWidth();
        //得到UI内部显示区高度
        int height = getUI().getInsetHeight();
        //得到最大显示宽度
        int maxWidth = getPageShowWidth() + 100;
        //得到最大显示高度
        int maxHeight = getPageShowHeight() + 2;
        //计算横纵滚动条显示情况
        boolean showH = maxWidth > width;
        boolean showV = maxHeight > height - (showH?17:0);
        if(showV)
            showH = maxWidth > width - 17;
        if(showH)
            showV = maxHeight > height - 17;
        //设置横向滚动条
        if(showH)
        {
            getUI().setShowHScrollBar(true);
            DScrollBar hScrollBar = getUI().getHScrollBar();
            hScrollBar.setMaximum(maxWidth - width + (showV?17:0));
            hScrollBar.setVisibleAmount(width);
        }else
            getUI().setShowHScrollBar(false);
        //设置纵向滚动条
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
     * 调整页面风格尺寸
     */
    public void resetPageStyleSize()
    {
        if(getUI() == null)
            return;
        //得到UI内部显示区宽度
        int width = getUI().getInsetWidth();
        //得到UI内部显示区高度
        int height = getUI().getInsetHeight();
        //得到页面风格最大显示宽度
        int maxWidth = getPageSytleMaxWidth();
        //得到页面风格最大显示高度
        int maxHeight = getPageSytleMaxHeight();
        //计算横纵滚动条显示情况
        boolean showH = maxWidth > width;
        boolean showV = maxHeight > height - (showH?17:0);
        if(showV)
            showH = maxWidth > width - 17;
        if(showH)
            showV = maxHeight > height - 17;
        //设置横向滚动条
        if(showH)
        {
            getUI().setShowHScrollBar(true);
            DScrollBar hScrollBar = getUI().getHScrollBar();
            hScrollBar.setMaximum(maxWidth - width + (showV?17:0));
            hScrollBar.setVisibleAmount(width);
        }else
            getUI().setShowHScrollBar(false);
        //设置纵向滚动条
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
     * 是否显示横向滚动条
     * @return boolean
     */
    public boolean isShowHScrollBar()
    {
        return getUI().isShowHScrollBar();
    }
    /**
     * 是否显示纵向滚动条
     * @return boolean
     */
    public boolean isShowVScrollBar()
    {
        return getUI().isShowVScrollBar();
    }
    /**
     * 得到横向滚动条位置
     * @return int
     */
    public int getHScrollBarValue()
    {
        if(!isShowHScrollBar())
            return 0;
        return getUI().getHScrollBar().getValue();
    }
    /**
     * 得到纵向滚动条位置
     * @return int
     */
    public int getVScrollBarValue()
    {
        if(!isShowVScrollBar())
            return 0;
        return getUI().getVScrollBar().getValue();
    }
    /**
     * 得到页面风格最大显示宽度
     * @return int
     */
    public int getPageSytleMaxWidth()
    {
        return getPageBorderSize() * 2 + getPageShowWidth();
    }
    /**
     * 页面显示宽度
     * @return int
     */
    public int getPageShowWidth()
    {
        return (int)(getPageWidth() * getZoom() / 75.0);
    }
    /**
     * 页面显示高度
     * @return int
     */
    public int getPageShowHeight()
    {
        return (int)(getPageHeight() * getZoom() / 75.0);
    }
    /**
     * 得到页面宽度
     * @return int
     */
    public int getPageWidth()
    {
        return getPageManager().getWidth();
    }
    /**
     * 得到页面高度
     * @return int
     */
    public int getPageHeight()
    {
        return getPageManager().getHeight();
    }
    /**
     * 得到页面风格最大显示高度
     * @return int
     */
    public int getPageSytleMaxHeight()
    {
        //页面尺寸
        int size = getPageManager().size();
        //页面高度
        int height = getPageShowHeight();
        return size * (height + getPageBorderSize()) + getPageBorderSize();
    }
    /**
     * 绘制背景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int width,int height)
    {
        switch(getViewStyle())
        {
        case 0://基本风格
            paintBaseView(g,width,height);
            break;
        case 1://页面风格
            paintPageView(g,width,height);
            break;
        }
    }
    /**
     * 得到显示区宽度
     * @return int
     */
    public int getViewWidth()
    {
        return getUI().getInsetWidth() - (isShowVScrollBar()?17:0);
    }
    /**
     * 得到显示区的高度
     * @return int
     */
    public int getViewHeight()
    {
        return getUI().getInsetHeight() - (isShowHScrollBar()?17:0);
    }
    /**
     * 得到显示区横坐标
     * @return int
     */
    public int getViewX()
    {
        //显示区宽度
        int width = getViewWidth();
        //页面宽度
        int pageWidth = getPageShowWidth();
        int x = getPageBorderSize();
        if(getViewStyle() != 0 && pageWidth + getPageBorderSize() * 2 < width)
            x = (width - pageWidth) / 2;
        else
            x -= getHScrollBarValue();
        return x;
    }
    /**
     * 得到显示区页面纵坐标
     * @param pageIndex int 页码
     * @return int
     */
    public int getViewY(int pageIndex)
    {
        //滚动条位置
        int y = getVScrollBarValue();
        //页面高度
        int pageHeight = getPageShowHeight();
        return pageIndex * (pageHeight + getPageBorderSize()) + getPageBorderSize() - y;
    }
    /**
     * 得到显示区的启示页号
     * @return int
     */
    public int getStartPageIndex()
    {
        //滚动条位置
        int y = getVScrollBarValue();
        //页面高度
        int pageHeight = getPageShowHeight();
        return (int)((double)y / (double)(pageHeight + getPageBorderSize()));
    }
    /**
     * 得到显示区的结束页号
     * @return int
     */
    public int getEndPageIndex()
    {
        int y = getVScrollBarValue();
        //显示区的高度
        int height = getViewHeight();
        //页面高度
        int pageHeight = getPageShowHeight();
        //页面个数
        int pageSize = getPageManager().size();
        int endPage = (int)((double)(y + height) / (double)(pageHeight + getPageBorderSize()) + 0.9999);
        if(endPage > pageSize)
            endPage = pageSize;
        return endPage;
    }
    /**
     * 绘制前景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {

    }
    /**
     * 绘制基础风格
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
            //绘制页面
            paintBasePage(g,-x,-y,i);
        }
    }
    /**
     * 绘制页面风格
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintPageView(Graphics g,int width,int height)
    {
        //绘制底色
        g.setColor(backColor);
        g.fillRect(0,0,width,height);

        //页面宽度
        int pageWidth = getPageShowWidth();
        //页面高度
        int pageHeight = getPageShowHeight();
        //显示区的启示页号
        int startPage = getStartPageIndex();
        //显示区的结束页号
        int endPage = getEndPageIndex();
        //计算X启画点
        int pageX = getViewX();
        //绘制显示区页面
        for(int i = startPage;i < endPage;i++)
        {
            //得到显示区页面纵坐标
            int pageY = getViewY(i);
            //绘制页面
            paintPage(g,pageX,pageY,pageWidth,pageHeight,i);
        }
    }
    /**
     * 绘制基础页面
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */

    public void paintBasePage(Graphics g,int x,int y,int pageIndex)
    {
        //绘制页面
        EPage page = getPageManager().get(pageIndex);
        if(page == null)
            return;
        page.paint(g,x,y,page.getWidth(),page.getHeight());
    }
    /**
     * 绘制页面
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
            //边框
            g.setColor(new Color(0,0,0));
            g.drawRect(x-1,y-1,width + 2,height + 2);
            g.fillRect(x + 1,y + height + 2,width + 3,2);
            g.fillRect(x + width + 2,y + 1,2,height + 2);
            //底色
            g.setColor(new Color(255,255,255));
            g.fillRect(x,y,width + 1,height + 1);
        }
        //绘制括号线
        TDrawTool.drawRectInsets(getPageManager().getInsets(),x,y,width,height,new Color(170,170,170),24,getZoom(),g);

        /*double zoom = getZoom() / 75.0;
        AffineTransform at = new AffineTransform();
        System.out.println(zoom);
        at.scale(zoom,zoom);
        Graphics2D g2 = (Graphics2D)g;
        g2.setTransform(at);*/

        //绘制背景
        getPageManager().paintBackground(g,x,y,pageIndex);


        //绘制页面
        EPage page = getPageManager().get(pageIndex);
        if(page == null)
            return;
        page.paint(g,x,y,width,height);
        //绘制焦点
        getFocusManager().paintPage(g,x,y,width,height,pageIndex);

        //绘制前景
        getPageManager().paintForeground(g,x,y,pageIndex);

        //调试
        //g.setFont(new Font("宋体",0,12));
        //g.setColor(new Color(255,0,0));
        //g.drawString("page " + pageIndex,30,30);
    }
    /**
     * 是否调试 EPage
     * @return boolean
     */
    public boolean isPageDebug()
    {
        return pageDebug;
    }
    /**
     * 是否调试 EPanel
     * @return boolean
     */
    public boolean isPanelDebug()
    {
        return panelDebug;
    }
    /**
     * 是否调试 EText
     * @return boolean
     */
    public boolean isTextDebug()
    {
        return textDebug;
    }
    /**
     * 是否调试 ETR
     * @return boolean
     */
    public boolean isTRDebug()
    {
        return trDebug;
    }
    /**
     * 是否调试 ETD
     * @return boolean
     */
    public boolean isTDDebug()
    {
        return tdDebug;
    }
    /**
     * 是否调试 EFixed
     * @return boolean
     */
    public boolean isFixedDebug()
    {
        return fixedDebug;
    }
    /**
     * 是否调试 宏
     * @return boolean
     */
    public boolean isMacroroutineDebug()
    {
        return macroroutineDebug;
    }
    /**
     * 设置缩放尺寸(字符参数)
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
            System.out.println("MView.setZoomString(" + zoom + ") :参数非法");
        }
    }
    /**
     * 得到缩放尺寸(字符参数)
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
     * 设置页面风格边框尺寸
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
     * 得到页面风格边框尺寸
     * @return int
     */
    public int getPageBorderSize()
    {
        return pageBorderSize;
    }
    /**
     * 设置显示行号
     * @param isShowRowID boolean
     */
    public void setShowRowID(boolean isShowRowID)
    {
        this.isShowRowID = isShowRowID;
    }
    /**
     * 是否显示行号
     * @return boolean
     */
    public boolean isShowRowID()
    {
        return isShowRowID;
    }
}
