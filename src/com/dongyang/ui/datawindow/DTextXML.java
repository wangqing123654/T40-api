package com.dongyang.ui.datawindow;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import java.util.StringTokenizer;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;

/**
 *
 * <p>Title: 文本标签父类处理XML解析</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.09
 * @version 1.0
 */
public abstract class DTextXML
    extends JLabel
{
  //XML元素
  private INode xml;
  //区域
  private String area;
  //边框
  private String border;
  //透明
  private boolean transparence;

  /**
   * 得到对象类型
   * @return
   */
  public abstract String getType();

  /**
   * 得到系统ID
   * @return
   */
  public abstract int getID();

  /**
   * 设置标签
   * @param tag
   */
  public abstract void setTag(String tag);

  /**
   * 得到移动控制对象
   * @return
   */
  public abstract Moveable getMoveable();

  /**
   * 设置XML元素
   * @param xml
   */
  public void setXML(INode xml)
  {
    this.xml = xml;
  }

  /**
   * 得到XML元素
   * @return
   */
  public INode getXML()
  {
    return xml;
  }

  /**
   * 创建本组件的XML元素
   * @return
   */
  public INode createXML()
  {
    if (getXML() == null)
      setXML(new TNode(getType()));
    saveXMLName();
    saveXMLTag();
    saveXMLText();
    saveXMLAlignment();
    saveXMLSize();
    saveXMLColor();
    saveXMLBackColor();
    saveXMLFont();
    saveXMLFontSize();
    saveXMLFontStyle();
    saveXMLBorder();
    return getXML();
  }

  /**
   * 同步XML信息
   */
  public void loadXML()
  {
    loadXMLName();
    loadXMLTag();
    loadXMLText();
    loadXMLAlignment();
    loadXMLSize();
    loadXMLColor();
    loadXMLBackColor();
    loadXMLFont();
    loadXMLFontSize();
    loadXMLFontStyle();
    loadXMLBorder();
  }

  //-----------------------------XML名称-----------------------------//
  /**
   * 保存XML名称
   */
  public void saveXMLName()
  {
    getXML().setAttributeValue("name", getName());
  }

  /**
   * 读取XML名称
   */
  public String readXMLName()
  {
    String name = getXML().getAttributeValue("name");
    if(name == null)
      return "";
    return name;
  }

  /**
   * 加载XML名称
   */
  public void loadXMLName()
  {
    setName(readXMLName());
  }

  //-----------------------------XML标签-----------------------------//
  /**
   * 保存XML标签
   */
  public void saveXMLTag()
  {
    getXML().setAttributeValue("tag", getName());
  }

  /**
   * 读取XML标签
   */
  public String readXMLTag()
  {
    String tag = getXML().getAttributeValue("tag");
    if(tag == null)
      return "";
    return tag;
  }

  /**
   * 加载XML标签
   */
  public void loadXMLTag()
  {
    setTag(readXMLTag());
  }

  //-----------------------------XML尺寸-----------------------------//
  /**
   * 读取XML横坐标
   * @return
   */
  public int readXMLX()
  {
    return getXML().getAttributeValueAsInteger("x").intValue();
  }

  /**
   * 保存XML横坐标
   */
  public void saveXMLX()
  {
    getXML().setAttributeValue("x", getX());
  }

  /**
   * 读取XML纵坐标
   * @return
   */
  public int readXMLY()
  {
    return getXML().getAttributeValueAsInteger("y").intValue();
  }

  /**
   * 保存XML纵坐标
   */
  public void saveXMLY()
  {
    getXML().setAttributeValue("y", getY());
  }

  /**
   * 读取XML宽度
   * @return
   */
  public int readXMLWidth()
  {
    return getXML().getAttributeValueAsInteger("width").intValue();
  }

  /**
   * 保存XML宽度
   */
  public void saveXMLWidth()
  {
    getXML().setAttributeValue("width", getWidth());
  }

  /**
   * 读取XML高度
   * @return
   */
  public int readXMLHeight()
  {
    return getXML().getAttributeValueAsInteger("height").intValue();
  }

  /**
   * 保存XML高度
   */
  public void saveXMLHeight()
  {
    getXML().setAttributeValue("height", getHeight());
  }

  /**
   * 读取XML区域
   * @return
   */
  public String readXMLArea()
  {
    return getXML().getAttributeValue("band");
  }

  /**
   * 保存XML区域
   */
  public void saveXMLArea()
  {
    getXML().setAttributeValue("band", getArea());
  }

  /**
   * 保存XML尺寸
   * @param element
   */
  public void saveXMLSize()
  {
    saveXMLX();
    saveXMLY();
    saveXMLWidth();
    saveXMLHeight();
    saveXMLArea();
  }

  /**
   * 读取XML尺寸
   */
  public void loadXMLSize()
  {
    setBounds(new Rectangle(readXMLX(), readXMLY(), readXMLWidth(),
                            readXMLHeight()));
    setArea(getXML().getAttributeValue("band"));
  }

  //-----------------------------XML文本-----------------------------//
  /**
   * 保存XML文本
   */
  public void saveXMLText()
  {
    getXML().setAttributeValue("text", getText());
  }

  /**
   * 读取XML文本
   * @return
   */
  public String readXMLText()
  {
    String text = getXML().getAttributeValue("text");
    if(text == null)
      return "";
    return text;
  }

  /**
   * 加载XML文本
   */
  public void loadXMLText()
  {
    setText(readXMLText());
  }

  //-----------------------------XML对齐方式---------------------------//
  /**
   * 保存XML对齐方式
   */
  public void saveXMLAlignment()
  {
    getXML().setAttributeValue("alignment", getHorizontalAlignment());
  }

  /**
   * 读取XML对齐方式
   * @return
   */
  public int readXMLAlignment()
  {
    if (getXML().getAttributeValue("alignment") != null)
      return getXML().getAttributeValueAsInteger("alignment").intValue();
    return SwingConstants.LEFT;
  }

  /**
   * 加载XML对齐方式
   */
  public void loadXMLAlignment()
  {
    setHorizontalAlignment(readXMLAlignment());
  }

  //-----------------------------XML文本颜色---------------------------//
  /**
   * 保存XML文本颜色
   * @param element
   */
  public void saveXMLColor()
  {
    getXML().setAttributeValue("color",
                               getForeground().getRed() + "," +
                               getForeground().getGreen() + "," +
                               getForeground().getBlue());
  }

  /**
   * 读取XML文本颜色
   */
  public Color readXMLColor()
  {
    Color color = new Color(0, 0, 0);
    if (getXML().getAttributeValue("color") != null)
    {
      StringTokenizer stk = new StringTokenizer(getXML().getAttributeValue(
          "color"), ",");
      color = new Color(Integer.parseInt(stk.nextToken()),
                        Integer.parseInt(stk.nextToken()),
                        Integer.parseInt(stk.nextToken()));
    }
    return color;
  }

  /**
   * 加载XML文本颜色
   */
  public void loadXMLColor()
  {
    setForeground(readXMLColor());
  }

  //-----------------------------XML背景颜色---------------------------//
  /**
   * 保存XML背景颜色
   * @param element
   */
  public void saveXMLBackColor()
  {
    String backcolor = "N";
    if (!isTransparence())
      backcolor = getBackground().getRed() + "," + getBackground().getGreen() +
          "," + getBackground().getBlue();
    getXML().setAttributeValue("backcolor", backcolor);
  }

  /**
   * 读取XML背景颜色
   */
  public Color readXMLBackColor()
  {
    Color color = null;
    if (getXML().getAttributeValue("backcolor") != null &&
        !getXML().getAttributeValue("backcolor").equals("N"))
    {
      StringTokenizer stk = new StringTokenizer(getXML().getAttributeValue(
          "backcolor"), ",");
      color = new Color(Integer.parseInt(stk.nextToken()),
                        Integer.parseInt(stk.nextToken()),
                        Integer.parseInt(stk.nextToken()));
    }
    return color;
  }

  /**
   * 加载XML背景颜色
   */
  public void loadXMLBackColor()
  {
    Color color = readXMLBackColor();
    setTransparence(color == null);
    setBackground(color);
  }

  //-----------------------------XML边框-----------------------------//
  /**
   * 保存XML边框
   * @param element
   */
  public void saveXMLBorder()
  {
    getXML().setAttributeValue("border", getBorderTag());
  }

  /**
   * 读取XML边框
   * @return
   */
  public String readXMLBorder()
  {
    String border = getXML().getAttributeValue("border");
    if (border != null)
      return border;
    return IBorderStyle.BORDER_NO;
  }

  /**
   * 加载XML边框
   */
  public void loadXMLBorder()
  {
    setBorderTag(readXMLBorder());
  }

  //-----------------------------XML字体-----------------------------//
  /**
   * 保存XML字体
   * @param element
   */
  public void saveXMLFont()
  {
    getXML().setAttributeValue("font", getFont().getFontName());
  }

  /**
   * 读取XML字体
   * @return
   */
  public String readXMLFont()
  {
    if (getXML().getAttributeValue("font") != null)
      return getXML().getAttributeValue("font");
    return "宋体";
  }

  /**
   * 加载XML字体
   */
  public void loadXMLFont()
  {
    setFont(new Font(readXMLFont(), getFont().getStyle(), getFont().getSize()));
  }

  //-----------------------------XML字体尺寸---------------------------//
  /**
   * 保存XML字体尺寸
   */
  public void saveXMLFontSize()
  {
    getXML().setAttributeValue("fontsize", getFont().getSize());
  }

  /**
   * 读取XML字体尺寸
   * @return
   */
  public int readXMLFontSize()
  {
    int fontsize = 12;
    if (getXML().getAttributeValue("fontsize") != null)
      fontsize = getXML().getAttributeValueAsInteger("fontsize").intValue();
    return fontsize;
  }

  /**
   * 加载XML字体尺寸
   */
  public void loadXMLFontSize()
  {
    setFont(new Font(getFont().getFontName(), getFont().getStyle(),
                     readXMLFontSize()));
  }

  //-----------------------------XML字体样式---------------------------//
  /**
   * 保存XML字体样式
   */
  public void saveXMLFontStyle()
  {
    getXML().setAttributeValue("fontstyle", getFont().getStyle());
  }

  /**
   * 读取XML字体样式
   * @return
   */
  public int readXMLFontStyle()
  {
    int fontstyle = 0;
    if (getXML().getAttributeValue("fontstyle") != null)
      fontstyle = getXML().getAttributeValueAsInteger("fontstyle").intValue();
    return fontstyle;
  }

  /**
   * 加载XML字体样式
   */
  public void loadXMLFontStyle()
  {
    setFont(new Font(getFont().getFontName(), readXMLFontStyle(),
                     getFont().getSize()));
  }

  /**
   * 设置区域
   * @param area
   */
  public void setArea(String area)
  {
    this.area = area;
  }

  /**
   * 得到区域
   * @return
   */
  public String getArea()
  {
    return area;
  }

  /**
   * 设置边框
   * @param border
   */
  public void setBorderTag(String border)
  {
    this.border = border;
    setBorder(new BaseBorder(IBorderStyle.BORDER_NO.equals(border) ?
                             IBorderStyle.BORDER_ETCHED : border));
  }

  /**
   * 读取边框
   * @return
   */
  public String getBorderTag()
  {
    return border;
  }

  /**
   * 设置是否透明
   * @param transparence true 透明 fase 不透明
   */
  public void setTransparence(boolean transparence)
  {
    this.transparence = transparence;
    repaint();
  }

  /**
   * 是否透明
   * @return true 透明 fase 不透明
   */
  public boolean isTransparence()
  {
    return transparence;
  }

  /**
   * 保存移动信息
   * @return
   */
  public String saveMove()
  {
    StringBuffer sb = new StringBuffer();
    if (readXMLX() != getX())
    {
      sb.append("oldx=" + readXMLX() + ";");
      sb.append("x=" + getX());
      saveXMLX();
    }
    if (readXMLY() != getY())
    {
      if (sb.length() > 0)
        sb.append(";");
      sb.append("oldy=" + readXMLY() + ";");
      sb.append("y=" + getY());
      saveXMLY();
    }
    if (readXMLWidth() != getWidth())
    {
      if (sb.length() > 0)
        sb.append(";");
      sb.append("oldwidth=" + readXMLWidth() + ";");
      sb.append("width=" + getWidth());
      saveXMLWidth();
    }
    if (readXMLHeight() != getHeight())
    {
      if (sb.length() > 0)
        sb.append(";");
      sb.append("oldheight=" + readXMLHeight() + ";");
      sb.append("height=" + getHeight());
      saveXMLHeight();
    }
    if (!readXMLArea().equals(getArea()))
    {
      if (sb.length() > 0)
        sb.append(";");
      sb.append("oldband=" + readXMLArea() + ";");
      sb.append("band=" + getArea());
      saveXMLArea();
    }
    if (sb.length() == 0)
      return "";
    return getType() + ":" + getID() + ";" + sb;
  }

  /**
   * 加载移动信息
   * @param s
   * @param b
   */
  public void loadMove(String s, boolean b)
  {
    StringTokenizer stk = new StringTokenizer(s, ";");
    int x = getX();
    int y = getY();
    int width = getWidth();
    int height = getHeight();
    String band = "";
    while (stk.hasMoreTokens())
    {
      StringTokenizer data = new StringTokenizer(stk.nextToken(), "=");
      String name = data.nextToken();
      if (name.startsWith(getType() + ":"))
        continue;
      String value = data.nextToken();
      if (b)
      {
        if (name.equals("x"))
          x = Integer.parseInt(value);
        else if (name.equals("y"))
          y = Integer.parseInt(value);
        else if (name.equals("width"))
          width = Integer.parseInt(value);
        else if (name.equals("height"))
          height = Integer.parseInt(value);
        else if (name.equals("band"))
          band = value;
      }
      else
      {
        if (name.equals("oldx"))
          x = Integer.parseInt(value);
        else if (name.equals("oldy"))
          y = Integer.parseInt(value);
        else if (name.equals("oldwidth"))
          width = Integer.parseInt(value);
        else if (name.equals("oldheight"))
          height = Integer.parseInt(value);
        else if (name.equals("oldband"))
          band = value;
      }
    }
    if (band.length() > 0)
      if (getParent().getParent()instanceof BackPanel)
      {
        AreaPanel panel = ( (BackPanel) getParent().getParent()).getArea(band);
        getParent().remove(this);
        panel.add(this, 0);
        setArea(band);
      }
    getMoveable().setSize(x, y, width, height);
    saveXMLSize();
  }
}
