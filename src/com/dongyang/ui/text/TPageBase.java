package com.dongyang.ui.text;

import java.awt.Graphics;
import javax.swing.JScrollBar;
import com.dongyang.util.StringTool;
import java.util.Map;
import com.dongyang.manager.TCM_Transform;
import java.util.HashMap;
import com.dongyang.config.INode;
import java.util.Iterator;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import com.dongyang.util.Log;

public abstract class TPageBase {
    /**
     * 纵向滚动条
     */
    private JScrollBar scrollBarH;
    /**
     * 横向滚动条
     */
    private JScrollBar scrollBarW;
    /**
     * 浏览类型
     */
    private String viewType;
    /**
     * 纸张宽度
     */
    private int pageWidth;
    /**
     * 纸张高度
     */
    private int pageHeight;
    /**
     * 纸张左边距
     */
    private int pageLeft;
    /**
     * 纸张右边距
     */
    private int pageRight;
    /**
     * 纸张上边距
     */
    private int pageUp;
    /**
     * 纸张下边距
     */
    private int pageDown;
    /**
     * 连接显示页面
     */
    private boolean isLinkPage;
    /**
     * UI对象
     */
    private TTextUI UI;
    /**
     * UI左边距
     */
    private int uiLeft;
    /**
     * UI右边距
     */
    private int uiRight;
    /**
     * UI上边距
     */
    private int uiUp;
    /**
     * UI下边距
     */
    private int uiDown;
    /**
     * 是否显示尺寸工具条
     */
    private boolean visibleSizeToolbar;
    /**
     * 组件元素
     */
    private IElement elementRoot;
    /**
     * 日志系统
     */
    Log log;
    /**
     * 构造器
     */
    public TPageBase()
    {
        log = new Log();
        initScroll();
        //默认页面尺寸
        defaultPage();
        setElementRoot(new TEData());
        //初始化组件根数据
        fineElementRoot();
    }
    /**
     * 默认页面尺寸
     */
    public void defaultPage()
    {
        setViewType("PageView");
        setPageWidth(300);
        setPageHeight(300);
        setPageLeft(100);
        setPageRight(100);
        setPageUp(100);
        setPageDown(100);
    }
    /**
     * 得到纵向滚动条
     * @return JScrollBar
     */
    public JScrollBar getScrollBarH()
    {
        return scrollBarH;
    }
    /**
     * 得到横向滚动条
     * @return JScrollBar
     */
    public JScrollBar getScrollBarW()
    {
        return scrollBarW;
    }
    /**
     * 得到纵向滚动条最大尺寸
     * @param h int
     */
    public void setScrollBarHMax(int h)
    {
        if(getScrollBarH().getMaximum() == h)
            return;
        getScrollBarH().setMaximum(0);
        getScrollBarH().setMaximum(h);
    }
    /**
     * 得到横向滚动条最大尺寸
     * @param w int
     */
    public void setScrollBarWMax(int w)
    {
        if(getScrollBarW().getMaximum() == w)
            return;
        getScrollBarW().setMaximum(0);
        getScrollBarW().setMaximum(w);
    }
    /**
     * 得到纵向滚动条位置
     * @return int
     */
    public int getH()
    {
        return getScrollBarH().getValue();
    }
    /**
     * 得到横向滚动条位置
     * @return int
     */
    public int getW()
    {
        return getScrollBarW().getValue();
    }
    /**
     * 设置浏览类型
     * @param viewType String
     */
    public void setViewType(String viewType)
    {
        this.viewType = viewType;
        //同步尺寸工具条的显示，因为不同的浏览类型尺寸工具条的样式不同
        //setVisibleSizeToolbar在TPage中有重载
        setVisibleSizeToolbar(isVisibleSizeToolbar());
    }
    /**
     * 得到浏览模式
     * @return String
     */
    public String getViewType()
    {
        return viewType;
    }
    /**
     * 设置纸张宽度
     * @param pageWidth int
     */
    public void setPageWidth(int pageWidth)
    {
        this.pageWidth = pageWidth;
    }
    /**
     * 得到纸张宽度
     * @return int
     */
    public int getPageWidth()
    {
        return pageWidth;
    }
    /**
     * 设置纸张高度
     * @param pageHeight int
     */
    public void setPageHeight(int pageHeight)
    {
        this.pageHeight = pageHeight;
    }
    /**
     * 得到纸张高度
     * @return int
     */
    public int getPageHeight()
    {
        return pageHeight;
    }
    /**
     * 设置纸张左边距
     * @param pageLeft int
     */
    public void setPageLeft(int pageLeft)
    {
        this.pageLeft = pageLeft;
    }
    /**
     * 得到纸张左边距
     * @return int
     */
    public int getPageLeft()
    {
        return pageLeft;
    }
    /**
     * 设置纸张右边距
     * @param pageRight int
     */
    public void setPageRight(int pageRight)
    {
        this.pageRight = pageRight;
    }
    /**
     * 得到纸张右边距
     * @return int
     */
    public int getPageRight()
    {
        return pageRight;
    }
    /**
     * 设置纸张上边距
     * @param pageUp int
     */
    public void setPageUp(int pageUp)
    {
        this.pageUp = pageUp;
    }
    /**
     * 得到纸张上边距
     * @return int
     */
    public int getPageUp()
    {
        return pageUp;
    }
    /**
     * 设置纸张下边距
     * @param pageDown int
     */
    public void setPageDown(int pageDown)
    {
        this.pageDown = pageDown;
    }
    /**
     * 得到纸张下边距
     * @return int
     */
    public int getPageDown()
    {
        return pageDown;
    }
    /**
     * 设置是否连接显示页面
     * @param isLinkPage boolean
     */
    public void setLinkPage(boolean isLinkPage)
    {
        this.isLinkPage = isLinkPage;
    }
    /**
     * 是否连接显示页面
     * @return boolean
     */
    public boolean isLinkPage()
    {
        return isLinkPage;
    }
    /**
     * 设置UI对象
     * @param UI TTextUI
     */
    public void setUI(TTextUI UI)
    {
        this.UI = UI;
    }
    /**
     * 得到UI对象
     * @return TTextUI
     */
    public TTextUI getUI()
    {
        return UI;
    }
    /**
     * 设置UI左边距
     * @param uiLeft int
     */
    public void setUILeft(int uiLeft)
    {
        this.uiLeft = uiLeft;
    }
    /**
     * 得到UI左边距
     * @return int
     */
    public int getUILeft()
    {
        return uiLeft;
    }
    /**
     * 设置UI右边距
     * @param uiRight int
     */
    public void setUIRight(int uiRight)
    {
        this.uiRight = uiRight;
    }
    /**
     * 得到UI右边距
     * @return int
     */
    public int getUIRight()
    {
        return uiRight;
    }
    /**
     * 设置UI上边距
     * @param uiUp int
     */
    public void setUIUp(int uiUp)
    {
        this.uiUp = uiUp;
    }
    /**
     * 得到UI上边距
     * @return int
     */
    public int getUIUp()
    {
        return uiUp;
    }
    /**
     * 设置UI下边距
     * @param uiDown int
     */
    public void setUIDown(int uiDown)
    {
        this.uiDown = uiDown;
    }
    /**
     * 得到UI下边距
     * @return int
     */
    public int getUIDown()
    {
        return uiDown;
    }
    /**
     * 得到工作区的X坐标
     * @return int
     */
    public int getWorkX()
    {
        return getUILeft();
    }
    /**
     * 得到工作区的Y坐标
     * @return int
     */
    public int getWorkY()
    {
        return getUIUp();
    }
    /**
     * 得到工作区的宽度坐标
     * @return int
     */
    public int getWorkWidth()
    {
        return getUI().getWidth() - getUILeft() - getUIRight();
    }
    /**
     * 得到工作区的高度坐标
     * @return int
     */
    public int getWorkHeight()
    {
        return getUI().getHeight() - getUIUp() - getUIDown();
    }
    /**
     * 设置是否显示尺寸工具条
     * @param visibleSizeToolbar boolean
     */
    public void setVisibleSizeToolbar(boolean visibleSizeToolbar)
    {
        this.visibleSizeToolbar = visibleSizeToolbar;
    }
    /**
     * 是否显示尺寸工具条
     * @return boolean
     */
    public boolean isVisibleSizeToolbar()
    {
        return visibleSizeToolbar;
    }
    /**
     * 设置组件根元素
     * @param elementRoot IElement
     */
    public void setElementRoot(IElement elementRoot)
    {
        this.elementRoot = elementRoot;
    }
    /**
     * 得到组件根元素
     * @return IElement
     */
    public IElement getElementRoot()
    {
        return elementRoot;
    }
    /**
     * 得到页面工作区宽度
     * @return int
     */
    public int getPageWorkWidth()
    {
        return getPageWidth() - getPageLeft() - getPageRight();
    }
    /**
     * 得到页面工作区高度
     * @return int
     */
    public int getPageWorkHeight()
    {
        return getPageHeight() - getPageUp() - getPageDown();
    }
    /**
     * 初始化滚动条
     */
    public void initScroll()
    {
        scrollBarH = new JScrollBar();
        scrollBarW = new JScrollBar();
        getScrollBarW().setOrientation(JScrollBar.HORIZONTAL);
        getScrollBarW().addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                UI.repaint();
            }
        });
        getScrollBarH().addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                UI.repaint();
            }
        });
    }
    /**
     * 写xml数据
     * @return String
     */
    public String write()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(writeXMLHead());
        sb.append("<DataWindow>\n");
        sb.append(writePage());
        sb.append(writeData());
        sb.append("</DataWindow>\n");
        return sb.toString();
    }
    /**
     * 写XML头数据
     * @return String
     */
    public String writeXMLHead()
    {
        return "<?xml version='1.0' encoding='GBK'?>\n";
    }
    /**
     * 写数据
     * @return String
     */
    public String writeData()
    {
        return getElementRoot().write(1);
    }
    /**
     * 写XML页面数据
     * @return String
     */
    public String writePage()
    {
        return getPageXML("viewType,isLinkPage,pageWidth,pageHeight,pageLeft,"+
                          "pageRight,pageUp,pageDown,visibleSizeToolbar");
    }
    public String getPageXML(String nameList)
    {
        Map map = new HashMap();
        getMapValue(map);
        String names[] = StringTool.parseLine(nameList,",");
        StringBuffer sb = new StringBuffer();
        sb.append("\t<Page ");
        for(int i = 0; i < names.length;i++)
        {
            sb.append(names[i]);
            sb.append("='");
            sb.append(map.get(names[i]));
            sb.append("' ");
        }
        sb.append("/>\n");
        return sb.toString();
    }
    /**
    * 加载xml
    * @param node INode
    */
   public void read(INode node)
   {
       out("begin");
       if(!"dataWindow".equalsIgnoreCase(node.getName()))
       {
           err("not datawindow xml data!");
           return;
       }
       //加载页面
       readPage(node.getChildElement("Page"));
       //加载数据
       readData(node.getChildElement("Data"));
       //同步滚动条的最大尺寸
       synchronizationScrollBarW();
       synchronizationScrollBarH();
       getUI().repaint();
       out("end");
   }
   /**
    * 加载数据
    * @param node INode
    */
   public void readData(INode node)
   {
       out("begin");
       getElementRoot().read(node);
       //初始化组件根数据
       fineElementRoot();
       out("end");
   }
   /**
    * 初始化组件根数据
    */
   public void fineElementRoot()
   {
       getElementRoot().setX(0);
       getElementRoot().setY(0);
       getElementRoot().setWidth(getPageWorkWidth());
       getElementRoot().setHeight(getPageWorkHeight());
       getElementRoot().fine();
   }
   /**
    * 加载页面
    * @param node INode
    */
   public void readPage(INode node)
   {
       out("begin");
       //加载页面属性
       readAttribute(node);
       out(this.toString());
       out("end");
   }
   /**
   * 加载属性
   * @param node INode
   */
  public void readAttribute(INode node)
  {
      Iterator iterator = node.getAttributeNames();
      while(iterator.hasNext())
      {
          String name = (String)iterator.next();
          String value = (String)node.getAttributeValue(name);
          callMessage(name + "=" + value);
      }
  }
  /**
     * 导出Map
     * @param map Map
     */
    public void getMapValue(Map map) {
        if (map == null)
            return;
        map.put("viewType", getViewType());
        map.put("isLinkPage",isLinkPage());
        map.put("pageWidth", getPageWidth());
        map.put("pageHeight", getPageHeight());
        map.put("pageLeft", getPageLeft());
        map.put("pageRight", getPageRight());
        map.put("pageUp", getPageUp());
        map.put("pageDown", getPageDown());
        map.put("visibleSizeToolbar", isVisibleSizeToolbar());
    }
    /**
     * 装载Map
     * @param map Map
     */
    public void setMapValue(Map map) {
        if (map == null)
            return;
        setViewType(TCM_Transform.getString(map.get("viewType")));
        setLinkPage(TCM_Transform.getBoolean(map.get("isLinkPage")));
        setPageWidth(TCM_Transform.getInt(map.get("pageWidth")));
        setPageHeight(TCM_Transform.getInt(map.get("pageHeight")));
        setPageLeft(TCM_Transform.getInt(map.get("pageLeft")));
        setPageRight(TCM_Transform.getInt(map.get("pageRight")));
        setPageUp(TCM_Transform.getInt(map.get("pageUp")));
        setPageDown(TCM_Transform.getInt(map.get("pageDown")));
        setVisibleSizeToolbar(TCM_Transform.getBoolean(map.get("visibleSizeToolbar")));
    }
    /**
     * 转换成字符串
     * @return String
     */
    public String toString() {
        Map map = new HashMap();
        getMapValue(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName() + ":" +
                  Integer.toHexString(hashCode()).toUpperCase() + map.toString());
        return sb.toString();
    }
    /**
     * 消息处理
     * @param message String
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * 消息处理
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        if ("setViewType".equalsIgnoreCase(value[0])) {
            setViewType(value[1]);
            return "OK";
        }
        if ("getViewType".equalsIgnoreCase(value[0])) {
            return getViewType();
        }
        if ("setPageWidth".equalsIgnoreCase(value[0])) {
            setPageWidth(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getPageWidth".equalsIgnoreCase(value[0])) {
            return getPageWidth();
        }
        if ("setPageHeight".equalsIgnoreCase(value[0])) {
            setPageHeight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getPageHeight".equalsIgnoreCase(value[0])) {
            return getPageHeight();
        }
        if ("setPageLeft".equalsIgnoreCase(value[0])) {
            setPageLeft(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getPageLeft".equalsIgnoreCase(value[0])) {
            return getPageLeft();
        }
        if ("setPageRight".equalsIgnoreCase(value[0])) {
            setPageRight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getPageRight".equalsIgnoreCase(value[0])) {
            return getPageRight();
        }
        if ("setPageUp".equalsIgnoreCase(value[0])) {
            setPageUp(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getPageUp".equalsIgnoreCase(value[0])) {
            return getPageUp();
        }
        if ("setPageDown".equalsIgnoreCase(value[0])) {
            setPageDown(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getPageDown".equalsIgnoreCase(value[0])) {
            return getPageDown();
        }
        if("setLinkPage".equalsIgnoreCase(value[0]))
        {
            setLinkPage(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if("isLinkPage".equalsIgnoreCase(value[0]))
        {
            return isLinkPage();
        }
        if("setVisibleSizeToolbar".equalsIgnoreCase(value[0]))
        {
            setVisibleSizeToolbar(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if("isVisibleSizeToolbar".equalsIgnoreCase(value[0]))
        {
            return isVisibleSizeToolbar();
        }
        value = StringTool.getHead(message, "=");
        if ("viewType".equalsIgnoreCase(value[0])) {
            setViewType(value[1]);
            return "OK";
        }
        if ("pageWidth".equalsIgnoreCase(value[0])) {
            setPageWidth(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("pageHeight".equalsIgnoreCase(value[0])) {
            setPageHeight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("pageLeft".equalsIgnoreCase(value[0])) {
            setPageLeft(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("pageRight".equalsIgnoreCase(value[0])) {
            setPageRight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("pageUp".equalsIgnoreCase(value[0])) {
            setPageUp(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("pageDown".equalsIgnoreCase(value[0])) {
            setPageDown(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("isLinkPage".equalsIgnoreCase(value[0]))
        {
            setLinkPage(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if("visibleSizeToolbar".equalsIgnoreCase(value[0]))
        {
            setVisibleSizeToolbar(StringTool.getBoolean(value[1]));
            return "OK";
        }
        return null;
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        if("PageView".equalsIgnoreCase(getViewType()))
            paintPageView(g);
        else if("HtmlView".equalsIgnoreCase(getViewType()))
            paintHtmlView(g);
        else if("BaseView".equalsIgnoreCase(getViewType()))
            paintBaseView(g);
    }
    /**
     * 同步滚动条的最大位置H
     */
    public void synchronizationScrollBarH()
    {
        if("PageView".equalsIgnoreCase(getViewType()))
            synchronizationScrollBarHPage();
        else if("HtmlView".equalsIgnoreCase(getViewType()))
            synchronizationScrollBarHHtml();
        else if("BaseView".equalsIgnoreCase(getViewType()))
            synchronizationScrollBarHBase();
    }
    /**
     * 同步滚动条的最大位置H 页面风格
     */
    public abstract void synchronizationScrollBarHPage();
    /**
     * 同步滚动条的最大位置H 网页风格
     */
    public void synchronizationScrollBarHHtml()
    {

    }
    /**
     * 同步滚动条的最大位置H 普通风格
     */
    public void synchronizationScrollBarHBase()
    {

    }
    /**
     * 同步滚动条的最大位置W
     */
    public void synchronizationScrollBarW()
    {
        if("PageView".equalsIgnoreCase(getViewType()))
            synchronizationScrollBarWPage();
        else if("HtmlView".equalsIgnoreCase(getViewType()))
            synchronizationScrollBarWHtml();
        else if("BaseView".equalsIgnoreCase(getViewType()))
            synchronizationScrollBarWBase();
    }
    /**
     * 同步滚动条的最大位置W 页面风格
     */
    public abstract void synchronizationScrollBarWPage();
    /**
     * 同步滚动条的最大位置W 网页风格
     */
    public void synchronizationScrollBarWHtml()
    {

    }
    /**
     * 同步滚动条的最大位置W 普通风格
     */
    public void synchronizationScrollBarWBase()
    {

    }
    /**
     * 页面显示
     * @param g Graphics
     */
    public abstract void paintPageView(Graphics g);
    /**
     * 网页显示
     * @param g Graphics
     */
    public abstract void paintHtmlView(Graphics g);
    /**
     * 普通
     * @param g Graphics
     */
    public abstract void paintBaseView(Graphics g);
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        log.err(text);
    }
}
