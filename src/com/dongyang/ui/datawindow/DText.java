package com.dongyang.ui.datawindow;

import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;
import java.util.StringTokenizer;
import com.dongyang.config.INode;

/**
 *
 * <p>Title: �ı���ǩ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ŀ��</p>
 * @author lzk 2006.07.05
 * @version 1.0
 */
public class DText
    extends DTextXML
    implements IMoveable,DComponent
{
  public static int SID;
  static synchronized int getSID()
  {
    SID++;
    return SID;
  }

  private int id;
  //��ǩ
  private String tag;
  //�Ƿ���Զ�̬�ı�ߴ�
  private boolean resizable;
  //��̬�ı�λ�õ��豸
  private boolean moveable;
  private Moveable moveableObject = new Moveable(this);
  /**
   * ������
   */
  public DText()
  {
    id = getSID();
    onInit();
  }

  /**
   * ������
   */
  public DText(String text)
  {
    this();
    setText(text);
  }

  /**
   * �õ�ϵͳID
   * @return
   */
  public int getID()
  {
    return id;
  }

  /**
   * ����ϵͳID
   * @param id
   */
  public void setID(int id)
  {
    this.id = id;
  }

  /**
   * �õ���ǩ
   * @return
   */
  public String getTag()
  {
    return tag;
  }

  /**
   * ���ñ�ǩ
   * @param tag
   */
  public void setTag(String tag)
  {
    this.tag = tag;
  }

  /**
   * �Ƿ���Զ�̬�ı�ߴ�
   * @return true ���Ըı� false�����Ըı�
   */
  public boolean isResizable()
  {
    return resizable;
  }

  /**
   * �����Ƿ���Զ�̬�ı�ߴ�
   * @param resizable true ���Ըı� false�����Ըı�
   */
  public void setResizable(boolean resizable)
  {
    this.resizable = resizable;
  }

  /**
   * �Ƿ���Զ�̬�ı�λ��
   * @return true ���Ըı� false�����Ըı�
   */
  public boolean isMoveable()
  {
    return moveable;
  }

  /**
   * �����Ƿ���Զ�̬�ı�λ��
   * @param moveable true ���Ըı� false�����Ըı�
   */
  public void setMoveable(boolean moveable)
  {
    this.moveable = moveable;
  }

  /**
   * ��ʼ��
   */
  public void onInit()
  {
    setFont(new java.awt.Font("����", 0, 12));
    setTransparence(true);
    this.setBorderTag(IBorderStyle.BORDER_NO);
    this.addMouseListener(moveableObject);
    this.addMouseMotionListener(moveableObject);
  }

  /**
   * �õ�Xλ��
   * @return Xλ��
   */
  public int getX()
  {
    return this.getLocation().x;
  }

  /**
   * �õ�Yλ��
   * @return Yλ��
   */
  public int getY()
  {
    return this.getLocation().y;
  }

  /**
   * ����Xλ��
   * @param x λ��
   */
  public void setX(int x)
  {
    setLocation(x, getY());
  }

  /**
   * ����Yλ��
   * @param y λ��
   */
  public void setY(int y)
  {
    setLocation(getX(), y);
  }

  /**
   * ���ÿ��
   * @param width ���
   */
  public void setWidth(int width)
  {
    this.setSize(width, getHeight());
  }

  /**
   * ���ø߶�
   * @param height �߶�
   */
  public void setHeight(int height)
  {
    this.setSize(getWidth(), height);
  }

  /**
   * �õ��ƶ����ƶ���
   * @return
   */
  public Moveable getMoveable()
  {
    return moveableObject;
  }

  /**
   * �����ѡ
   * @param x1 ���� x
   * @param y1 ���� y
   * @param x2 ���� x
   * @param y2 ���� y
   * @param type ���� 1�Ӵ�ѡ 2����ѡ
   * @return true �ڷ�Χ�� false ����
   */
  public boolean checkInclude(int x1, int y1, int x2, int y2, int type)
  {
    int x = getX();
    int y = getY();
    int width = getWidth();
    int height = getHeight();
    switch (type)
    {
      case 1: //�Ӵ�ѡ
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2)
          return true;
        if (x + width >= x1 && x + width <= x2 && y >= y1 && y <= y2)
          return true;
        if (x + width >= x1 && x + width <= x2 && y + height >= y1 &&
            y + height <= y2)
          return true;
        if (x >= x1 && x <= x2 && y + height >= y1 && y + height <= y2)
          return true;
        if (x1 >= x && x2 <= x + width)
          if (y1 <= y && y2 >= y)
            return true;
          else if (y2 >= y + height && y1 <= y + height)
            return true;
        if (y1 >= y && y2 <= y + height)
          if (x1 <= x && x2 >= x)
            return true;
          else if (x2 >= x + width && x1 <= x + width)
            return true;
      case 2: //����ѡ
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2
            && width <= x2 - x1 - x + x1
            && width <= y2 - y1 - y + y1)
          return true;
    }
    return false;
  }

  /**
   * clone ����һ������
   * @return ���������
   */
  public Object clone()
  {
    DText text = new DText();
    clone(text);
    return text;
  }

  public void clone(DText text)
  {
    text.setName(getName());
    text.setText(getText());
    text.setArea(getArea());
    text.setLocation(getLocation());
    text.setSize(getSize());
    text.setFont(getFont());
    text.setBorderTag(getBorderTag());
    text.setHorizontalAlignment(getHorizontalAlignment());
    text.setTransparence(isTransparence());
    text.setForeground(getForeground());
    text.setBackground(getBackground());
  }

  /**
   * ת��Ϊ�ı���Ϣ
   * @return
   */
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("band=" + getArea() + ";");
    sb.append("text=" + getText() + ";");
    sb.append("name=" + getName() + ";");
    sb.append("font=" + getFont().getFontName() + ";");
    sb.append("fontsize=" + getFont().getSize() + ";");
    sb.append("border=" + getBorderTag() + ";");
    sb.append("alignment=" + getHorizontalAlignment() + ";");
    sb.append("x=" + getX() + ";");
    sb.append("y=" + getY() + ";");
    sb.append("width=" + getWidth() + ";");
    sb.append("height=" + getHeight() + ";");
    sb.append("color=" + getForeground().getRed() + "-" +
              getForeground().getGreen() + "-" + getForeground().getBlue() +
              ";");
    String backcolor = "N";
    if (!isTransparence())
      backcolor = getBackground().getRed() + "-" + getBackground().getGreen() +
          "-" + getBackground().getBlue();
    sb.append("backcolor=" + backcolor);
    return sb.toString();
  }

  /**
   * �����ı���Ϣ
   * @param s
   */
  public void loadString(String s)
  {
    StringTokenizer stk = new StringTokenizer(s, ";");
    while (stk.hasMoreTokens())
    {
      StringTokenizer data = new StringTokenizer(stk.nextToken(), "=");
      String name = data.nextToken();
      String value = data.nextToken();
      if (name.equals("band"))
        setArea(value);
      else if (name.equals("text"))
        setText(value);
      else if (name.equals("name"))
        setName(value);
      else if (name.equals("font"))
        setFont(new Font(value, 0, getFont().getSize()));
      else if (name.equals("fontsize"))
        setFont(new Font(getFont().getFontName(), 0, Integer.parseInt(value)));
      else if (name.equals("alignment"))
        setHorizontalAlignment(Integer.parseInt(value));
      else if (name.equals("border"))
        setBorderTag(value);
      else if (name.equals("x"))
        setX(Integer.parseInt(value));
      else if (name.equals("y"))
        setY(Integer.parseInt(value));
      else if (name.equals("width"))
        setWidth(Integer.parseInt(value));
      else if (name.equals("height"))
        setHeight(Integer.parseInt(value));
      else if (name.equals("color"))
      {
        StringTokenizer c = new StringTokenizer(value, "-");
        setForeground(new Color(Integer.parseInt(c.nextToken()),
                                Integer.parseInt(c.nextToken()),
                                Integer.parseInt(c.nextToken())));
      }
      else if (name.equals("backcolor"))
      {
        if (value.equals("N"))
          setTransparence(true);
        else
        {
          setTransparence(false);
          StringTokenizer c = new StringTokenizer(value, "-");
          setBackground(new Color(Integer.parseInt(c.nextToken()),
                                  Integer.parseInt(c.nextToken()),
                                  Integer.parseInt(c.nextToken())));
        }

      }
    }
  }

  /**
   * �õ���������
   * @return
   */
  public String getType()
  {
    return "text";
  }

  /**
   * �������
   * @param g
   */
  public void paint(Graphics g)
  {
    if (!isTransparence())
    {
      Insets insets = getBorder().getBorderInsets(this);
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth() - insets.right, getHeight() - insets.bottom);
    }
    super.paint(g);
  }

  /**
   * ���涯����־
   * @param action ��������
   * @param parent ����XML����
   * @return ��־��Ϣ
   */
  public String saveWorkLog(String action, INode parent)
  {
    //�½�����
    if (action.equals("Create"))
    {
      parent.addChildElement(createXML());
      return getType() + ":" + getID() + "," + action + "," + this;
    }
    //ɾ������
    if (action.equals("Delete"))
    {
      parent.removeChildElement(getXML());
      return getType() + ":" + getID() + "," + action + "," + this;
    }
    //�޸�����
    if (action.equals("Edit Name"))
    {
      String name = readXMLName();
      if (name.equals(getName()))
        return "";
      saveXMLName();
      return getType() + ":" + getID() + "," + action + "," + name +
          "," + getName();
    }
    //�޸ı�ǩ
    if (action.equals("Edit Tag"))
    {
      String tag = readXMLTag();
      if (tag.equals(getTag()))
        return "";
      saveXMLTag();
      return getType() + ":" + getID() + "," + action + "," + tag +
          "," + getTag();
    }
    //�޸�����
    if (action.equals("Edit Text"))
    {
      String text = readXMLText();
      if (text.equals(getText()))
        return "";
      saveXMLText();
      return getType() + ":" + getID() + "," + action + "," + text +
          "," + getText();
    }
    //�޸�����
    if (action.equals("Edit Font"))
    {
      String font = readXMLFont();
      if (font.equals(getFont().getFontName()))
        return "";
      saveXMLFont();
      return getType() + ":" + getID() + "," + action + "," + font + "," +
          getFont().getFontName();
    }
    //�޸�����ߴ�
    if (action.equals("Edit Font Size"))
    {
      int fontsize = readXMLFontSize();
      if (fontsize == getFont().getSize())
        return "";
      saveXMLFontSize();
      return getType() + ":" + getID() + "," + action + "," + fontsize + "," +
          getFont().getSize();
    }
    if (action.equals("Edit Font Style"))
    {
      int fontStyle = readXMLFontStyle();
      if (fontStyle == getFont().getStyle())
        return "";
      saveXMLFontStyle();
      return getType() + ":" + getID() + "," + action + "," + fontStyle + "," +
          getFont().getStyle();
    }
    //�޸ı߿�
    if (action.equals("Edit Border"))
    {
      String border = readXMLBorder();
      if (border.equals(getBorderTag()))
        return "";
      saveXMLBorder();
      return getType() + ":" + getID() + "," + action + "," + border + "," +
          getBorderTag();
    }
    //�޸Ķ��뷽ʽ
    if (action.equals("Edit Alignment"))
    {
      int alignment = readXMLAlignment();
      if (getHorizontalAlignment() == alignment)
        return "";
      saveXMLAlignment();
      return getType() + ":" + getID() + "," + action + "," + alignment + "," +
          getHorizontalAlignment();
    }
    //�޸�������ɫ
    if (action.equals("Edit Text Color"))
    {
      Color color = readXMLColor();
      if (color.equals(getForeground()))
        return "";
      saveXMLColor();
      return getType() + ":" + getID() + "," + action + "," +
          color.getRed() + ";" +
          color.getGreen() + ";" +
          color.getBlue() + "," +
          getForeground().getRed() + ";" +
          getForeground().getGreen() + ";" +
          getForeground().getBlue();
    }
    //�޸ı�����ɫ
    if (action.equals("Edit Back Color"))
    {
      Color color = readXMLBackColor();
      if (color == null && isTransparence())
        return "";
      if (getBackground().equals(color))
        return "";
      saveXMLBackColor();
      String color1 = color == null ? "N" : color.getRed() + ";" +
          color.getGreen() + ";" + color.getBlue();
      String color2 = isTransparence() ? "N" :
          getBackground().getRed() + ";" +
          getBackground().getGreen() + ";" +
          getBackground().getBlue();
      return getType() + ":" + getID() + "," + action + "," + color1 + "," +
          color2;
    }
    return "";
  }

  /**
   * �طŶ�����־
   * @param action ����
   * @param data ����
   */
  public boolean loadWorkLog(String action, String data)
  {
    //���Ƹı�ط�
    if (action.equals("Edit Name"))
    {
      setName(data);
      saveXMLName();
      return true;
    }
    //��ǩ�ı�ط�
    if (action.equals("Edit Tag"))
    {
      setTag(data);
      saveXMLTag();
      return true;
    }
    //�ı��ı�ط�
    if (action.equals("Edit Text"))
    {
      setText(data);
      saveXMLText();
      return true;
    }
    //����ı�ط�
    if (action.equals("Edit Font"))
    {
      setFont(new Font(data, getFont().getStyle(), getFont().getSize()));
      saveXMLFont();
      return true;
    }
    //����ߴ�ı�ط�
    if (action.equals("Edit Font Size"))
    {
      setFont(new Font(getFont().getFontName(), getFont().getStyle(),
                       Integer.parseInt(data)));
      saveXMLFontSize();
      return true;
    }
    //�������͸ı�ط�
    if (action.equals("Edit Font Style"))
    {
      setFont(new Font(getFont().getFontName(), Integer.parseInt(data),
                       getFont().getSize()));
      saveXMLFontStyle();
      return true;
    }
    //�߿�ı�ط�
    if (action.equals("Edit Border"))
    {
      setBorderTag(data);
      saveXMLBorder();
      return true;
    }
    //���뷽ʽ�ı�ط�
    if (action.equals("Edit Alignment"))
    {
      setHorizontalAlignment(Integer.parseInt(data));
      saveXMLAlignment();
      return true;
    }
    //������ɫ�ı�ط�
    if (action.equals("Edit Text Color"))
    {
      StringTokenizer c = new StringTokenizer(data, ";");
      Color color = new Color(Integer.parseInt(c.nextToken()),
                              Integer.parseInt(c.nextToken()),
                              Integer.parseInt(c.nextToken()));
      setForeground(color);
      saveXMLColor();
      return true;
    }
    //������ɫ�ı�ط�
    if (action.equals("Edit Back Color"))
    {
      if (data.equals("N"))
      {
        setTransparence(true);
      }
      else
      {
        setTransparence(false);
        StringTokenizer c = new StringTokenizer(data, ";");
        Color color = new Color(Integer.parseInt(c.nextToken()),
                                Integer.parseInt(c.nextToken()),
                                Integer.parseInt(c.nextToken()));
        setBackground(color);
      }
      saveXMLBackColor();
      return true;
    }
    return false;
  }

  /**
   * �¼��ӿ�,�����ӿؼ��ϴ���Ϣ
   * @param tag �����¼����������
   * @param eventName �¼�����
   * @param eventObj �¼�����
   */
  public void eventListener(String tag, String eventName, Object eventObj)
  {

  }

  /**
   * �����ⲿ���ܵ�������
   * @param action ��������
   * @param parm ����
   */
  public void callEventListener(String action, Object parm)
  {
    if (action.equals(DoubleClicked))
      onDoubleClicked();
  }

  /**
   * ˫���¼�
   */
  public void onDoubleClicked()
  {
    //TextDialog dialog = new TextDialog(this);
    //dialog.setVisible(true);
  }
}
