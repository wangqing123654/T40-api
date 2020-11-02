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
 * <p>Title: �ı���ǩ���ദ��XML����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.09
 * @version 1.0
 */
public abstract class DTextXML
    extends JLabel
{
  //XMLԪ��
  private INode xml;
  //����
  private String area;
  //�߿�
  private String border;
  //͸��
  private boolean transparence;

  /**
   * �õ���������
   * @return
   */
  public abstract String getType();

  /**
   * �õ�ϵͳID
   * @return
   */
  public abstract int getID();

  /**
   * ���ñ�ǩ
   * @param tag
   */
  public abstract void setTag(String tag);

  /**
   * �õ��ƶ����ƶ���
   * @return
   */
  public abstract Moveable getMoveable();

  /**
   * ����XMLԪ��
   * @param xml
   */
  public void setXML(INode xml)
  {
    this.xml = xml;
  }

  /**
   * �õ�XMLԪ��
   * @return
   */
  public INode getXML()
  {
    return xml;
  }

  /**
   * �����������XMLԪ��
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
   * ͬ��XML��Ϣ
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

  //-----------------------------XML����-----------------------------//
  /**
   * ����XML����
   */
  public void saveXMLName()
  {
    getXML().setAttributeValue("name", getName());
  }

  /**
   * ��ȡXML����
   */
  public String readXMLName()
  {
    String name = getXML().getAttributeValue("name");
    if(name == null)
      return "";
    return name;
  }

  /**
   * ����XML����
   */
  public void loadXMLName()
  {
    setName(readXMLName());
  }

  //-----------------------------XML��ǩ-----------------------------//
  /**
   * ����XML��ǩ
   */
  public void saveXMLTag()
  {
    getXML().setAttributeValue("tag", getName());
  }

  /**
   * ��ȡXML��ǩ
   */
  public String readXMLTag()
  {
    String tag = getXML().getAttributeValue("tag");
    if(tag == null)
      return "";
    return tag;
  }

  /**
   * ����XML��ǩ
   */
  public void loadXMLTag()
  {
    setTag(readXMLTag());
  }

  //-----------------------------XML�ߴ�-----------------------------//
  /**
   * ��ȡXML������
   * @return
   */
  public int readXMLX()
  {
    return getXML().getAttributeValueAsInteger("x").intValue();
  }

  /**
   * ����XML������
   */
  public void saveXMLX()
  {
    getXML().setAttributeValue("x", getX());
  }

  /**
   * ��ȡXML������
   * @return
   */
  public int readXMLY()
  {
    return getXML().getAttributeValueAsInteger("y").intValue();
  }

  /**
   * ����XML������
   */
  public void saveXMLY()
  {
    getXML().setAttributeValue("y", getY());
  }

  /**
   * ��ȡXML���
   * @return
   */
  public int readXMLWidth()
  {
    return getXML().getAttributeValueAsInteger("width").intValue();
  }

  /**
   * ����XML���
   */
  public void saveXMLWidth()
  {
    getXML().setAttributeValue("width", getWidth());
  }

  /**
   * ��ȡXML�߶�
   * @return
   */
  public int readXMLHeight()
  {
    return getXML().getAttributeValueAsInteger("height").intValue();
  }

  /**
   * ����XML�߶�
   */
  public void saveXMLHeight()
  {
    getXML().setAttributeValue("height", getHeight());
  }

  /**
   * ��ȡXML����
   * @return
   */
  public String readXMLArea()
  {
    return getXML().getAttributeValue("band");
  }

  /**
   * ����XML����
   */
  public void saveXMLArea()
  {
    getXML().setAttributeValue("band", getArea());
  }

  /**
   * ����XML�ߴ�
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
   * ��ȡXML�ߴ�
   */
  public void loadXMLSize()
  {
    setBounds(new Rectangle(readXMLX(), readXMLY(), readXMLWidth(),
                            readXMLHeight()));
    setArea(getXML().getAttributeValue("band"));
  }

  //-----------------------------XML�ı�-----------------------------//
  /**
   * ����XML�ı�
   */
  public void saveXMLText()
  {
    getXML().setAttributeValue("text", getText());
  }

  /**
   * ��ȡXML�ı�
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
   * ����XML�ı�
   */
  public void loadXMLText()
  {
    setText(readXMLText());
  }

  //-----------------------------XML���뷽ʽ---------------------------//
  /**
   * ����XML���뷽ʽ
   */
  public void saveXMLAlignment()
  {
    getXML().setAttributeValue("alignment", getHorizontalAlignment());
  }

  /**
   * ��ȡXML���뷽ʽ
   * @return
   */
  public int readXMLAlignment()
  {
    if (getXML().getAttributeValue("alignment") != null)
      return getXML().getAttributeValueAsInteger("alignment").intValue();
    return SwingConstants.LEFT;
  }

  /**
   * ����XML���뷽ʽ
   */
  public void loadXMLAlignment()
  {
    setHorizontalAlignment(readXMLAlignment());
  }

  //-----------------------------XML�ı���ɫ---------------------------//
  /**
   * ����XML�ı���ɫ
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
   * ��ȡXML�ı���ɫ
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
   * ����XML�ı���ɫ
   */
  public void loadXMLColor()
  {
    setForeground(readXMLColor());
  }

  //-----------------------------XML������ɫ---------------------------//
  /**
   * ����XML������ɫ
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
   * ��ȡXML������ɫ
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
   * ����XML������ɫ
   */
  public void loadXMLBackColor()
  {
    Color color = readXMLBackColor();
    setTransparence(color == null);
    setBackground(color);
  }

  //-----------------------------XML�߿�-----------------------------//
  /**
   * ����XML�߿�
   * @param element
   */
  public void saveXMLBorder()
  {
    getXML().setAttributeValue("border", getBorderTag());
  }

  /**
   * ��ȡXML�߿�
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
   * ����XML�߿�
   */
  public void loadXMLBorder()
  {
    setBorderTag(readXMLBorder());
  }

  //-----------------------------XML����-----------------------------//
  /**
   * ����XML����
   * @param element
   */
  public void saveXMLFont()
  {
    getXML().setAttributeValue("font", getFont().getFontName());
  }

  /**
   * ��ȡXML����
   * @return
   */
  public String readXMLFont()
  {
    if (getXML().getAttributeValue("font") != null)
      return getXML().getAttributeValue("font");
    return "����";
  }

  /**
   * ����XML����
   */
  public void loadXMLFont()
  {
    setFont(new Font(readXMLFont(), getFont().getStyle(), getFont().getSize()));
  }

  //-----------------------------XML����ߴ�---------------------------//
  /**
   * ����XML����ߴ�
   */
  public void saveXMLFontSize()
  {
    getXML().setAttributeValue("fontsize", getFont().getSize());
  }

  /**
   * ��ȡXML����ߴ�
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
   * ����XML����ߴ�
   */
  public void loadXMLFontSize()
  {
    setFont(new Font(getFont().getFontName(), getFont().getStyle(),
                     readXMLFontSize()));
  }

  //-----------------------------XML������ʽ---------------------------//
  /**
   * ����XML������ʽ
   */
  public void saveXMLFontStyle()
  {
    getXML().setAttributeValue("fontstyle", getFont().getStyle());
  }

  /**
   * ��ȡXML������ʽ
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
   * ����XML������ʽ
   */
  public void loadXMLFontStyle()
  {
    setFont(new Font(getFont().getFontName(), readXMLFontStyle(),
                     getFont().getSize()));
  }

  /**
   * ��������
   * @param area
   */
  public void setArea(String area)
  {
    this.area = area;
  }

  /**
   * �õ�����
   * @return
   */
  public String getArea()
  {
    return area;
  }

  /**
   * ���ñ߿�
   * @param border
   */
  public void setBorderTag(String border)
  {
    this.border = border;
    setBorder(new BaseBorder(IBorderStyle.BORDER_NO.equals(border) ?
                             IBorderStyle.BORDER_ETCHED : border));
  }

  /**
   * ��ȡ�߿�
   * @return
   */
  public String getBorderTag()
  {
    return border;
  }

  /**
   * �����Ƿ�͸��
   * @param transparence true ͸�� fase ��͸��
   */
  public void setTransparence(boolean transparence)
  {
    this.transparence = transparence;
    repaint();
  }

  /**
   * �Ƿ�͸��
   * @return true ͸�� fase ��͸��
   */
  public boolean isTransparence()
  {
    return transparence;
  }

  /**
   * �����ƶ���Ϣ
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
   * �����ƶ���Ϣ
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
