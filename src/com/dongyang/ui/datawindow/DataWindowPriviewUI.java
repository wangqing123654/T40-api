package com.dongyang.ui.datawindow;

import com.dongyang.ui.base.TDataWindowBase;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.print.Printable;
import java.awt.Graphics2D;
import com.dongyang.config.TNode;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.Iterator;
import java.awt.Font;
import java.awt.BasicStroke;
import com.dongyang.config.INode;

public class DataWindowPriviewUI implements Printable
{
    //受控DataWindow对象
    private TDataWindowBase datawindow;

    //绘制文本组件的设备
    private TextLabel textLabel = new TextLabel();

    //打印页码范围列表
    private List printPageIDList;

    private int pageCount;
    /**
     * 构造器
     * @param datawindow 受控对象
     */
    public DataWindowPriviewUI(TDataWindowBase datawindow)
    {
      this.datawindow = datawindow;
    }
    public void paintTest(Graphics2D g)
    {
      //Graphics2D g2 = (Graphics2D) g.create();
/*      if(datawindow.getDataStore().getData() != null)
      {
        String isTest = datawindow.getDataStore().getData().getCommitValue("IS_TEST_V");
        if (isTest.equals("N"))
          return;
        if (isTest.length() == 0)
          if (!system.HISMain.getMain().vTest)
            return;
      }
      String s = "V3.0 测试版";
      Font f = new Font("宋体",Font.PLAIN,12);
      g.setFont(f);
      g.drawString(s,20,10);
*/      //Font f = new Font("宋体",Font.PLAIN,100);
      //AffineTransform at = new AffineTransform();
      //at.setToRotation(Math.PI/5);
      //g2.transform(at);
      //TextLayout tl = new TextLayout(s,f,g2.getFontRenderContext());
      //g2.setColor(new Color(220,220,220));
      //tl.draw(g2, 100.0f, 100);
      //tl.draw(g2, 260.0f, 370);
    }

    /**
     * 打印绘制入口
     * @param graphics 图形设备
     * @param pageFormat 页面格式
     * @param pageIndex 打印页号
     * @return
     */
    public synchronized int print(Graphics graphics, PageFormat pageFormat,
                                  int pageIndex)
    {
      if (printPageIDList != null)
      {
        if (pageIndex >= printPageIDList.size())
        {
          printPageIDList = null;
          return Printable.NO_SUCH_PAGE;
        }
        pageIndex = ( (Integer) printPageIDList.get(pageIndex)).intValue() - 1;
      }
      pageCount = datawindow.getPageCount();
      if (pageIndex >= pageCount)
        return Printable.NO_SUCH_PAGE;

      int x = (int) pageFormat.getImageableX();
      int y = (int) pageFormat.getImageableY();
      int w = (int) pageFormat.getImageableWidth();
      int h = (int) pageFormat.getImageableHeight();
      paintPage( (Graphics2D) graphics.create(x, y, w, h), w, h, pageIndex,
                getPageData(), 1.0);
      return Printable.PAGE_EXISTS;
    }

    /*  g2d.setStroke(new BasicStroke(1, 1, 1, 10F, new float[]
                                    {5F, 4F}
                                    , 0.0F));
     */
    /**
     * 内部显示绘制入口
     * @param g 图形设备
     * @param pageFormat 打印格式
     * @param width 宽度
     * @param height 高度
     */
    public void paint(Graphics g, PageFormat pageFormat, int width, int height)
    {
      checkScrollBar(pageFormat, width, height);
      int w = (int) ( (double) pageFormat.getWidth() * getPriviewZoom());
      int h = (int) ( (double) pageFormat.getHeight() * getPriviewZoom());
      datawindow.setHeightBlockIncrement(h + 10);
      pageCount = datawindow.getPageCount();
      for (int i = 0; i < pageCount; i++)
      {
        int y = i * (h + 10) - datawindow.getScrollHeightValue();
        if (y < height && y + h > 0)
          paintPriview(g.create(datawindow.getScrollWidthValue() * -1 + 5, y + 5,
                                w, h), pageFormat, w, h, i);
      }
    }

    /**
     * 打印阅览绘制主程,同时支持显示和打印
     * @param g 图形设备
     * @param pageFormat 打印格式
     * @param width 宽度
     * @param height 高度
     * @param page 页号
     */
    public void paintPriview(Graphics g, PageFormat pageFormat, int width,
                             int height, int page)
    {
      g.setColor(new Color(255, 255, 255));
      g.fillRect(1, 1, width - 1, height - 1);
      g.setColor(new Color(0, 0, 0));
      g.drawRect(0, 0, width - 1, height - 1);
      g.setColor(new Color(0, 0, 255));
      int x1 = (int) ( (double) pageFormat.getImageableX() * getPriviewZoom());
      int y1 = (int) ( (double) pageFormat.getImageableY() * getPriviewZoom());
      int w1 = (int) ( (double) pageFormat.getImageableWidth() * getPriviewZoom());
      int h1 = (int) ( (double) pageFormat.getImageableHeight() * getPriviewZoom());
      g.drawRect(x1 - 1, y1 - 1, w1 + 1, h1 + 1);
      PageData pageData = getPageData();
      paintPage( (Graphics2D) g.create(x1, y1, w1, h1), w1, h1, page, pageData,
                getPriviewZoom());
    }

    /**
     * 检测滚动条大小
     * @param pageFormat 打印格式
     * @param width 宽度
     * @param height 高度
     */
    private void checkScrollBar(PageFormat pageFormat, int width, int height)
    {
      int w = (int) ( (double) pageFormat.getWidth() * getPriviewZoom() + 10);
      int h = (int) ( (double) pageFormat.getHeight() * getPriviewZoom()) + 10;
      datawindow.setScrollBarMax(w - width,
                                 h * datawindow.getPageCount() - height);
    }

    /**
     * 绘制一个子页
     * @param g 图形设备
     * @param width 宽度
     * @param height 高度
     * @param page 页号
     * @param zoom 缩放比例
     */
    private void paintPage(Graphics2D g, int width, int height, int page,
                           PageData pageData, double zoom)
    {
      paintTest(g);
      pageData.setData(page);
      for (int index = 0; index < pageData.getDrawerSize(); index++)
      {
        TDataWindowBase dataWindowObject = pageData.getDrawerDataWindow(index);
        int row = pageData.getDrawerRow(index);
        String type = pageData.getDrawerType(index);
        int x = (int) ( (double) pageData.getDrawerX(index) * zoom);
        int y = (int) ( (double) pageData.getDrawerY(index) * zoom);
        int w = (int) ( (double) pageData.getDrawerWidth(index) * zoom);
        int h = (int) ( (double) pageData.getDrawerHeight(index) * zoom);
        if (w + x > width)
          w = width - x;
        if (h + y > height)
          h = height - y;
        if (dataWindowObject != datawindow)
        {
          dataWindowObject.getPriviewUI().paintDraw( (Graphics2D) g.create(x, y,
              w, h), w, h, row, type, page, pageData.getZoom() * zoom);
          continue;
        }
        paintDraw( (Graphics2D) g.create(x, y, w, h), w, h, row, type, page,
                  pageData.getZoom() * zoom);
      }
    }

    public void paintDraw(Graphics2D g, int width, int height, int row,
                          String type, int page, double zoom)
    {
      if (type.equals("Header"))
        paintHeader(g, width, height, page, zoom);
      if (type.equals("Footer"))
        paintFooter(g, width, height, page, zoom);
      if (type.equals("Detail"))
        paintRow(g, width, height, page, row, zoom);
      //g.setColor(new Color(255, 0, 0));
      //g.drawRect(0, 0, width - 1, height - 1);
    }

    /**
     * 绘制表头
     * @param g 图形设备
     * @param width 宽度
     * @param height 高度
     * @param page 页号
     * @param zoom 缩放比例
     */
    public void paintHeader(Graphics2D g, int width, int height, int page,
                            double zoom)
    {
      /*List list = datawindow.getChildDataWindowList("Header");
      if(list != null)
      for(int i = 0;i < list.size();i++)
      {
        TDataWindowBase childDataWindow = datawindow.getChildDataWindow((String) list.get(i));
        if(childDataWindow == null)
          continue;
        childDataWindow.setDataStore(datawindow.getDataStore());
        childDataWindow.getPriviewUI().paintHeader(g,width,height,page,zoom);
      }*/

      Iterator iterator = getXML("texts").getChildElements();
      while (iterator.hasNext())
        drawText(g, (TNode) iterator.next(), "Header", page, zoom);
      iterator = getXML("lines").getChildElements();
      while (iterator.hasNext())
        drawLine(g, (TNode) iterator.next(), "Header", -1, page, zoom);
    }

    /**
     * 绘制页脚
     * @param g 图形设备
     * @param width 宽度
     * @param height 高度
     * @param page 页号
     * @param zoom 缩放比例
     */
    public void paintFooter(Graphics2D g, int width, int height, int page,
                            double zoom)
    {
      Iterator iterator = getXML("texts").getChildElements();
      while (iterator.hasNext())
        drawText(g, (TNode) iterator.next(), "Footer", page, zoom);
      iterator = getXML("lines").getChildElements();
      while (iterator.hasNext())
        drawLine(g, (TNode) iterator.next(), "Footer",-1, page, zoom);
    }

    /**
     * 绘制数据区
     * @param g 图形设备
     * @param width 宽度
     * @param height 高度
     * @param detailHeight 行高度
     * @param page 页号
     * @param zoom 缩放比例
     */
    /*public void paintRow(Graphics2D g, int width, int height,
                            int page, int row, double zoom)
    {
      //g.setColor(new Color(255, 255, 0));
      //g.fillRect(0,0,width,height);
      int start = page * datawindow.getPageRowCount();
      int stop = start + datawindow.getPageRowCount();
      if (stop > datawindow.rowCount())
        stop = datawindow.rowCount();
      for (int row = start, y = 0; row < stop; row++, y += detailHeight)
      {
        paintRow( (Graphics2D) g.create(0, y, width, detailHeight), width,
                 detailHeight, row, page, zoom);
      }
    }*/

    /**
     * 绘制一行数据
     * @param g 图形设备
     * @param width 宽度
     * @param height 高度
     * @param page 页号
     * @param row 行号
     * @param zoom 缩放比例
     */
    public void paintRow(Graphics2D g, int width, int height,
                            int page, int row, double zoom)
    {
      //g.setColor(new Color(255, 0, 0));
      //g.fillRect(0,0,width,height);
      Iterator iterator = getXML("texts").getChildElements();
      while (iterator.hasNext())
        drawText(g, (TNode) iterator.next(), "Detail", page, zoom);
      iterator = getXML("columns").getChildElements();
      while (iterator.hasNext())
        drawColumn(g, (TNode) iterator.next(), "Detail", row, page, zoom);
      iterator = getXML("lines").getChildElements();
      while (iterator.hasNext())
        drawLine(g, (TNode) iterator.next(), "Detail", row,page, zoom);
    }

    /**
     * 绘制文本组件
     * @param g 图形设备
     * @param text 文本组件的XML
     * @param area 区域
     * @param page 页号
     * @param zoom 缩放比例
     */
    public void drawText(Graphics2D g, TNode text, String area, int page,
                         double zoom)
    {
      if (!text.getAttributeValue("band").equals(area))
        return;
      String value = text.getAttributeValue("text");
      if (value.startsWith("t_"))
      {
        value = datawindow.getItemString(value.substring(2, value.length()));
        if (value.length() == 0)
          value = "";
      }
      value = replace(value,"$page()",page + 1);
      value = replace(value,"$pageCount()",pageCount);

      textLabel.setText(value);
      ImageIcon icon = null;
      if(value.startsWith("p_"))
      {
        textLabel.setText("");
        icon = createImageIcon(value.substring(2,value.length()));
        //textLabel.setIcon(createImageIcon(value.substring(2,value.length())));
      }//else
        //textLabel.setIcon(null);
      int x = (int) (text.getAttributeValueAsDouble("x").doubleValue() * zoom);
      int y = (int) (text.getAttributeValueAsDouble("y").doubleValue() * zoom);
      int width = (int) (text.getAttributeValueAsDouble("width").doubleValue() *
                         zoom);
      int height = (int) (text.getAttributeValueAsDouble("height").doubleValue() *
                          zoom);
      String font = text.getAttributeValue("font");
      String border = text.getAttributeValue("border");
      int alignment = text.getAttributeValueAsInteger("alignment").intValue();
      int fontsize = 12;
      if (text.getAttributeValueAsInteger("fontsize") != null)
        fontsize = text.getAttributeValueAsInteger("fontsize").intValue();
      int fontstyle = 0;
      if (text.getAttributeValueAsInteger("fontstyle") != null)
        fontstyle = text.getAttributeValueAsInteger("fontstyle").intValue();
      if (font != null && font.length() == 0)
        font = "宋体";
        //g.setColor(new Color(255,0,0));
        //g.fillRect(x,y,width,height);
      Font f = new Font(font, fontstyle,
                        10).deriveFont( (float) ( (double) fontsize * zoom));
      textLabel.setFont(f);
      textLabel.setSize(width, height);
      textLabel.setBorder(new BaseBorder(border));
      textLabel.setHorizontalAlignment(alignment);
      Color color = new Color(0, 0, 0);
      if (text.getAttributeValue("color") != null)
      {
        StringTokenizer stk = new StringTokenizer(text.getAttributeValue("color"),
                                                  ",");
        color = new Color(Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()));
      }
      textLabel.setForeground(color);
      color = null;
      if (text.getAttributeValue("backcolor") != null &&
          !text.getAttributeValue("backcolor").equals("N"))
      {
        StringTokenizer stk = new StringTokenizer(text.getAttributeValue(
            "backcolor"), ",");
        color = new Color(Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()));
      }
      textLabel.setTransparence(color == null);
      textLabel.setBackground(color);
      if(icon == null)
        textLabel.paint(g.create(x, y, width, height));
      else
        g.drawImage(icon.getImage(),x,y,width,height,null);
    }
    private String replace(String s,String name,int value)
    {
      return replace(s,name,"" + value);
    }
    private String replace(String s,String name,String value)
    {
      int index = s.indexOf(name);
      if(index == -1)
        return s;
      return s.substring(0,index) + value + s.substring(index + name.length(),s.length());
    }
    /**
     * 绘制列组件
     * @param g 图形设备
     * @param column 列组件xml
     * @param area 区域
     * @param row 行号
     * @param page 页号
     * @param zoom 缩放比例
     */
    public void drawColumn(Graphics g, TNode column, String area,
                           int row, int page,
                           double zoom)
    {
      if (!column.getAttributeValue("band").equals(area))
        return;
      String value = datawindow.getItemString(row,
                                              column.getAttributeValue("name"));
      textLabel.setText(value);
      int x = (int) (column.getAttributeValueAsDouble("x").doubleValue() * zoom);
      int y = (int) (column.getAttributeValueAsDouble("y").doubleValue() * zoom);
      int width = (int) (column.getAttributeValueAsDouble("width").doubleValue() *
                         zoom);
      int height = (int) (column.getAttributeValueAsDouble("height").doubleValue() *
                          zoom);
      String font = column.getAttributeValue("font");
      String border = column.getAttributeValue("border");
      int alignment = column.getAttributeValueAsInteger("alignment").intValue();
      int fontsize = 12;
      if (column.getAttributeValueAsInteger("fontsize") != null)
        fontsize = column.getAttributeValueAsInteger("fontsize").intValue();
      int fontstyle = 0;
      if (column.getAttributeValueAsInteger("fontstyle") != null)
        fontstyle = column.getAttributeValueAsInteger("fontstyle").intValue();
      if (font != null && font.length() == 0)
        font = "宋体";
        //g.setColor(new Color(255,255,255));
        //g.fillRect(x - datawindow.getScrollWidthValue(),y,width,height);
      textLabel.setSize(width, height);
      Font f = new Font(font, fontstyle,
                        10).deriveFont( (float) ( (double) fontsize * zoom));
      textLabel.setFont(f);
      textLabel.setBorder(new BaseBorder(border));
      textLabel.setHorizontalAlignment(alignment);
      Color color = new Color(0, 0, 0);
      if (column.getAttributeValue("color") != null)
      {
        StringTokenizer stk = new StringTokenizer(column.getAttributeValue(
            "color"), ",");
        color = new Color(Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()));
      }
      textLabel.setForeground(color);
      color = null;
      if (column.getAttributeValue("backcolor") != null &&
          !column.getAttributeValue("backcolor").equals("N"))
      {
        StringTokenizer stk = new StringTokenizer(column.getAttributeValue(
            "backcolor"), ",");
        color = new Color(Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()),
                          Integer.parseInt(stk.nextToken()));
      }
      textLabel.setTransparence(color == null);
      textLabel.setBackground(color);
      textLabel.paint(g.create(x, y, width, height));
    }

    /**
     * 绘制线组件
     * @param g 图形设备
     * @param line 线组件XML
     * @param area 区域
     * @param page 页号
     * @param zoom 缩放比例
     */
    public void drawLine(Graphics2D g, TNode line, String area, int row,int page,
                         double zoom)
    {
      if (!line.getAttributeValue("band").equals(area))
        return;
      String visible = line.getAttributeValue("visible");
      if(visible != null)
      {
        if("0".equals(visible))
          return;
        if (visible.startsWith("t_"))
        {
          visible = visible.substring(2, visible.length());
          String name = visible;
          String value = "0";
          int index = visible.indexOf("==");
          if (index > 0)
          {
            name = visible.substring(0, index);
            value = visible.substring(index + 2, visible.length());
            visible = datawindow.getItemString(row, name);
            if (!value.equals(visible))
              return;
          }
          index = visible.indexOf("!=");
          if (index > 0)
          {
            name = visible.substring(0, index);
            value = visible.substring(index + 2, visible.length());
            visible = datawindow.getItemString(row, name);
            if (value.equals(visible))
              return;
          }
        }
      }
      //System.out.println("name=" + name);
      //String value = datawindow.getItemString(row,);
      int x1 = (int) (line.getAttributeValueAsDouble("x1").doubleValue() * zoom);
      int y1 = (int) (line.getAttributeValueAsDouble("y1").doubleValue() * zoom);
      int x2 = (int) (line.getAttributeValueAsDouble("x2").doubleValue() * zoom);
      int y2 = (int) (line.getAttributeValueAsDouble("y2").doubleValue() * zoom);
      g.setStroke(new BasicStroke( (float) (0.5 * zoom)));
      g.setColor(new Color(0, 0, 0));
      g.drawLine(x1, y1, x2, y2);
    }

    /**
     * 得到XML对象
     * @param name
     * @return
     */
    public INode getXML(String name)
    {
      return datawindow.getXML().getChildElement(name);
    }

    /**
     * 得到打印XML对象
     * @return
     */
    public INode getPrintXML()
    {
      return getXML("Print");
    }

    /**
     * 得到打印阅览缩放比例
     * @return
     */
    public double getPriviewZoom()
    {
      return getPrintXML().getAttributeValueAsDouble("PriviewZoom").doubleValue() /
          100;
    }

    /**
     * 初始化打印页码范围
     * @param s
     * @return
     */
    public boolean initPrintPageList(String s)
    {
      if (s == null)
      {
        printPageIDList = null;
        return false;
      }
      printPageIDList = new ArrayList();
      try
      {
        StringTokenizer sk = new StringTokenizer(s, ",");
        while (sk.hasMoreTokens())
        {
          String value = sk.nextToken().toUpperCase().trim();
          int index = value.indexOf("TO");
          if (index > 0)
          {
            int start = Integer.parseInt(value.substring(0, index).trim());
            int end = Integer.parseInt(value.substring(index + 2, value.length()).
                                       trim());
            for (int i = start; i <= end; i++)
              printPageIDList.add(new Integer(i));
          }
          else
            printPageIDList.add(new Integer(value));
        }
      }
      catch (Exception e)
      {
        return false;
      }
      return true;
    }

    public PageData getPageData()
    {
      return datawindow.getPageData();
    }
    private ImageIcon createImageIcon(String filename)
    {
        return datawindow.createImageIcon(filename);
    }
}
