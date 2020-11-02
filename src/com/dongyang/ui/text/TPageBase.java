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
     * ���������
     */
    private JScrollBar scrollBarH;
    /**
     * ���������
     */
    private JScrollBar scrollBarW;
    /**
     * �������
     */
    private String viewType;
    /**
     * ֽ�ſ��
     */
    private int pageWidth;
    /**
     * ֽ�Ÿ߶�
     */
    private int pageHeight;
    /**
     * ֽ����߾�
     */
    private int pageLeft;
    /**
     * ֽ���ұ߾�
     */
    private int pageRight;
    /**
     * ֽ���ϱ߾�
     */
    private int pageUp;
    /**
     * ֽ���±߾�
     */
    private int pageDown;
    /**
     * ������ʾҳ��
     */
    private boolean isLinkPage;
    /**
     * UI����
     */
    private TTextUI UI;
    /**
     * UI��߾�
     */
    private int uiLeft;
    /**
     * UI�ұ߾�
     */
    private int uiRight;
    /**
     * UI�ϱ߾�
     */
    private int uiUp;
    /**
     * UI�±߾�
     */
    private int uiDown;
    /**
     * �Ƿ���ʾ�ߴ繤����
     */
    private boolean visibleSizeToolbar;
    /**
     * ���Ԫ��
     */
    private IElement elementRoot;
    /**
     * ��־ϵͳ
     */
    Log log;
    /**
     * ������
     */
    public TPageBase()
    {
        log = new Log();
        initScroll();
        //Ĭ��ҳ��ߴ�
        defaultPage();
        setElementRoot(new TEData());
        //��ʼ�����������
        fineElementRoot();
    }
    /**
     * Ĭ��ҳ��ߴ�
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
     * �õ����������
     * @return JScrollBar
     */
    public JScrollBar getScrollBarH()
    {
        return scrollBarH;
    }
    /**
     * �õ����������
     * @return JScrollBar
     */
    public JScrollBar getScrollBarW()
    {
        return scrollBarW;
    }
    /**
     * �õ�������������ߴ�
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
     * �õ�������������ߴ�
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
     * �õ����������λ��
     * @return int
     */
    public int getH()
    {
        return getScrollBarH().getValue();
    }
    /**
     * �õ����������λ��
     * @return int
     */
    public int getW()
    {
        return getScrollBarW().getValue();
    }
    /**
     * �����������
     * @param viewType String
     */
    public void setViewType(String viewType)
    {
        this.viewType = viewType;
        //ͬ���ߴ繤��������ʾ����Ϊ��ͬ��������ͳߴ繤��������ʽ��ͬ
        //setVisibleSizeToolbar��TPage��������
        setVisibleSizeToolbar(isVisibleSizeToolbar());
    }
    /**
     * �õ����ģʽ
     * @return String
     */
    public String getViewType()
    {
        return viewType;
    }
    /**
     * ����ֽ�ſ��
     * @param pageWidth int
     */
    public void setPageWidth(int pageWidth)
    {
        this.pageWidth = pageWidth;
    }
    /**
     * �õ�ֽ�ſ��
     * @return int
     */
    public int getPageWidth()
    {
        return pageWidth;
    }
    /**
     * ����ֽ�Ÿ߶�
     * @param pageHeight int
     */
    public void setPageHeight(int pageHeight)
    {
        this.pageHeight = pageHeight;
    }
    /**
     * �õ�ֽ�Ÿ߶�
     * @return int
     */
    public int getPageHeight()
    {
        return pageHeight;
    }
    /**
     * ����ֽ����߾�
     * @param pageLeft int
     */
    public void setPageLeft(int pageLeft)
    {
        this.pageLeft = pageLeft;
    }
    /**
     * �õ�ֽ����߾�
     * @return int
     */
    public int getPageLeft()
    {
        return pageLeft;
    }
    /**
     * ����ֽ���ұ߾�
     * @param pageRight int
     */
    public void setPageRight(int pageRight)
    {
        this.pageRight = pageRight;
    }
    /**
     * �õ�ֽ���ұ߾�
     * @return int
     */
    public int getPageRight()
    {
        return pageRight;
    }
    /**
     * ����ֽ���ϱ߾�
     * @param pageUp int
     */
    public void setPageUp(int pageUp)
    {
        this.pageUp = pageUp;
    }
    /**
     * �õ�ֽ���ϱ߾�
     * @return int
     */
    public int getPageUp()
    {
        return pageUp;
    }
    /**
     * ����ֽ���±߾�
     * @param pageDown int
     */
    public void setPageDown(int pageDown)
    {
        this.pageDown = pageDown;
    }
    /**
     * �õ�ֽ���±߾�
     * @return int
     */
    public int getPageDown()
    {
        return pageDown;
    }
    /**
     * �����Ƿ�������ʾҳ��
     * @param isLinkPage boolean
     */
    public void setLinkPage(boolean isLinkPage)
    {
        this.isLinkPage = isLinkPage;
    }
    /**
     * �Ƿ�������ʾҳ��
     * @return boolean
     */
    public boolean isLinkPage()
    {
        return isLinkPage;
    }
    /**
     * ����UI����
     * @param UI TTextUI
     */
    public void setUI(TTextUI UI)
    {
        this.UI = UI;
    }
    /**
     * �õ�UI����
     * @return TTextUI
     */
    public TTextUI getUI()
    {
        return UI;
    }
    /**
     * ����UI��߾�
     * @param uiLeft int
     */
    public void setUILeft(int uiLeft)
    {
        this.uiLeft = uiLeft;
    }
    /**
     * �õ�UI��߾�
     * @return int
     */
    public int getUILeft()
    {
        return uiLeft;
    }
    /**
     * ����UI�ұ߾�
     * @param uiRight int
     */
    public void setUIRight(int uiRight)
    {
        this.uiRight = uiRight;
    }
    /**
     * �õ�UI�ұ߾�
     * @return int
     */
    public int getUIRight()
    {
        return uiRight;
    }
    /**
     * ����UI�ϱ߾�
     * @param uiUp int
     */
    public void setUIUp(int uiUp)
    {
        this.uiUp = uiUp;
    }
    /**
     * �õ�UI�ϱ߾�
     * @return int
     */
    public int getUIUp()
    {
        return uiUp;
    }
    /**
     * ����UI�±߾�
     * @param uiDown int
     */
    public void setUIDown(int uiDown)
    {
        this.uiDown = uiDown;
    }
    /**
     * �õ�UI�±߾�
     * @return int
     */
    public int getUIDown()
    {
        return uiDown;
    }
    /**
     * �õ���������X����
     * @return int
     */
    public int getWorkX()
    {
        return getUILeft();
    }
    /**
     * �õ���������Y����
     * @return int
     */
    public int getWorkY()
    {
        return getUIUp();
    }
    /**
     * �õ��������Ŀ������
     * @return int
     */
    public int getWorkWidth()
    {
        return getUI().getWidth() - getUILeft() - getUIRight();
    }
    /**
     * �õ��������ĸ߶�����
     * @return int
     */
    public int getWorkHeight()
    {
        return getUI().getHeight() - getUIUp() - getUIDown();
    }
    /**
     * �����Ƿ���ʾ�ߴ繤����
     * @param visibleSizeToolbar boolean
     */
    public void setVisibleSizeToolbar(boolean visibleSizeToolbar)
    {
        this.visibleSizeToolbar = visibleSizeToolbar;
    }
    /**
     * �Ƿ���ʾ�ߴ繤����
     * @return boolean
     */
    public boolean isVisibleSizeToolbar()
    {
        return visibleSizeToolbar;
    }
    /**
     * ���������Ԫ��
     * @param elementRoot IElement
     */
    public void setElementRoot(IElement elementRoot)
    {
        this.elementRoot = elementRoot;
    }
    /**
     * �õ������Ԫ��
     * @return IElement
     */
    public IElement getElementRoot()
    {
        return elementRoot;
    }
    /**
     * �õ�ҳ�湤�������
     * @return int
     */
    public int getPageWorkWidth()
    {
        return getPageWidth() - getPageLeft() - getPageRight();
    }
    /**
     * �õ�ҳ�湤�����߶�
     * @return int
     */
    public int getPageWorkHeight()
    {
        return getPageHeight() - getPageUp() - getPageDown();
    }
    /**
     * ��ʼ��������
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
     * дxml����
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
     * дXMLͷ����
     * @return String
     */
    public String writeXMLHead()
    {
        return "<?xml version='1.0' encoding='GBK'?>\n";
    }
    /**
     * д����
     * @return String
     */
    public String writeData()
    {
        return getElementRoot().write(1);
    }
    /**
     * дXMLҳ������
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
    * ����xml
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
       //����ҳ��
       readPage(node.getChildElement("Page"));
       //��������
       readData(node.getChildElement("Data"));
       //ͬ�������������ߴ�
       synchronizationScrollBarW();
       synchronizationScrollBarH();
       getUI().repaint();
       out("end");
   }
   /**
    * ��������
    * @param node INode
    */
   public void readData(INode node)
   {
       out("begin");
       getElementRoot().read(node);
       //��ʼ�����������
       fineElementRoot();
       out("end");
   }
   /**
    * ��ʼ�����������
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
    * ����ҳ��
    * @param node INode
    */
   public void readPage(INode node)
   {
       out("begin");
       //����ҳ������
       readAttribute(node);
       out(this.toString());
       out("end");
   }
   /**
   * ��������
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
     * ����Map
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
     * װ��Map
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
     * ת�����ַ���
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
     * ��Ϣ����
     * @param message String
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * ��Ϣ����
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
     * ����
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
     * ͬ�������������λ��H
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
     * ͬ�������������λ��H ҳ����
     */
    public abstract void synchronizationScrollBarHPage();
    /**
     * ͬ�������������λ��H ��ҳ���
     */
    public void synchronizationScrollBarHHtml()
    {

    }
    /**
     * ͬ�������������λ��H ��ͨ���
     */
    public void synchronizationScrollBarHBase()
    {

    }
    /**
     * ͬ�������������λ��W
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
     * ͬ�������������λ��W ҳ����
     */
    public abstract void synchronizationScrollBarWPage();
    /**
     * ͬ�������������λ��W ��ҳ���
     */
    public void synchronizationScrollBarWHtml()
    {

    }
    /**
     * ͬ�������������λ��W ��ͨ���
     */
    public void synchronizationScrollBarWBase()
    {

    }
    /**
     * ҳ����ʾ
     * @param g Graphics
     */
    public abstract void paintPageView(Graphics g);
    /**
     * ��ҳ��ʾ
     * @param g Graphics
     */
    public abstract void paintHtmlView(Graphics g);
    /**
     * ��ͨ
     * @param g Graphics
     */
    public abstract void paintBaseView(Graphics g);
    /**
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        log.err(text);
    }
}
