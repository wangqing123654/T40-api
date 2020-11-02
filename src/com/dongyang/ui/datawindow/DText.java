package com.dongyang.ui.datawindow;

import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;
import java.util.StringTokenizer;
import com.dongyang.config.INode;

/**
 *
 * <p>Title: 文本标签</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 东佑项目部</p>
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
  //标签
  private String tag;
  //是否可以动态改变尺寸
  private boolean resizable;
  //动态改变位置的设备
  private boolean moveable;
  private Moveable moveableObject = new Moveable(this);
  /**
   * 构造器
   */
  public DText()
  {
    id = getSID();
    onInit();
  }

  /**
   * 构造器
   */
  public DText(String text)
  {
    this();
    setText(text);
  }

  /**
   * 得到系统ID
   * @return
   */
  public int getID()
  {
    return id;
  }

  /**
   * 设置系统ID
   * @param id
   */
  public void setID(int id)
  {
    this.id = id;
  }

  /**
   * 得到标签
   * @return
   */
  public String getTag()
  {
    return tag;
  }

  /**
   * 设置标签
   * @param tag
   */
  public void setTag(String tag)
  {
    this.tag = tag;
  }

  /**
   * 是否可以动态改变尺寸
   * @return true 可以改变 false不可以改变
   */
  public boolean isResizable()
  {
    return resizable;
  }

  /**
   * 设置是否可以动态改变尺寸
   * @param resizable true 可以改变 false不可以改变
   */
  public void setResizable(boolean resizable)
  {
    this.resizable = resizable;
  }

  /**
   * 是否可以动态改变位置
   * @return true 可以改变 false不可以改变
   */
  public boolean isMoveable()
  {
    return moveable;
  }

  /**
   * 设置是否可以动态改变位置
   * @param moveable true 可以改变 false不可以改变
   */
  public void setMoveable(boolean moveable)
  {
    this.moveable = moveable;
  }

  /**
   * 初始化
   */
  public void onInit()
  {
    setFont(new java.awt.Font("宋体", 0, 12));
    setTransparence(true);
    this.setBorderTag(IBorderStyle.BORDER_NO);
    this.addMouseListener(moveableObject);
    this.addMouseMotionListener(moveableObject);
  }

  /**
   * 得到X位置
   * @return X位置
   */
  public int getX()
  {
    return this.getLocation().x;
  }

  /**
   * 得到Y位置
   * @return Y位置
   */
  public int getY()
  {
    return this.getLocation().y;
  }

  /**
   * 设置X位置
   * @param x 位置
   */
  public void setX(int x)
  {
    setLocation(x, getY());
  }

  /**
   * 设置Y位置
   * @param y 位置
   */
  public void setY(int y)
  {
    setLocation(getX(), y);
  }

  /**
   * 设置宽度
   * @param width 宽度
   */
  public void setWidth(int width)
  {
    this.setSize(width, getHeight());
  }

  /**
   * 设置高度
   * @param height 高度
   */
  public void setHeight(int height)
  {
    this.setSize(getWidth(), height);
  }

  /**
   * 得到移动控制对象
   * @return
   */
  public Moveable getMoveable()
  {
    return moveableObject;
  }

  /**
   * 检测扩选
   * @param x1 左上 x
   * @param y1 左上 y
   * @param x2 右下 x
   * @param y2 右下 y
   * @param type 类型 1接触选 2包含选
   * @return true 在范围内 false 不再
   */
  public boolean checkInclude(int x1, int y1, int x2, int y2, int type)
  {
    int x = getX();
    int y = getY();
    int width = getWidth();
    int height = getHeight();
    switch (type)
    {
      case 1: //接触选
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
      case 2: //包含选
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2
            && width <= x2 - x1 - x + x1
            && width <= y2 - y1 - y + y1)
          return true;
    }
    return false;
  }

  /**
   * clone 复制一个对象
   * @return 自身对象景象
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
   * 转换为文本信息
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
   * 加载文本信息
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
   * 得到对象类型
   * @return
   */
  public String getType()
  {
    return "text";
  }

  /**
   * 绘制组件
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
   * 保存动作日志
   * @param action 动作名称
   * @param parent 父类XML对象
   * @return 日志信息
   */
  public String saveWorkLog(String action, INode parent)
  {
    //新建对象
    if (action.equals("Create"))
    {
      parent.addChildElement(createXML());
      return getType() + ":" + getID() + "," + action + "," + this;
    }
    //删除对象
    if (action.equals("Delete"))
    {
      parent.removeChildElement(getXML());
      return getType() + ":" + getID() + "," + action + "," + this;
    }
    //修改名称
    if (action.equals("Edit Name"))
    {
      String name = readXMLName();
      if (name.equals(getName()))
        return "";
      saveXMLName();
      return getType() + ":" + getID() + "," + action + "," + name +
          "," + getName();
    }
    //修改标签
    if (action.equals("Edit Tag"))
    {
      String tag = readXMLTag();
      if (tag.equals(getTag()))
        return "";
      saveXMLTag();
      return getType() + ":" + getID() + "," + action + "," + tag +
          "," + getTag();
    }
    //修改文字
    if (action.equals("Edit Text"))
    {
      String text = readXMLText();
      if (text.equals(getText()))
        return "";
      saveXMLText();
      return getType() + ":" + getID() + "," + action + "," + text +
          "," + getText();
    }
    //修改字体
    if (action.equals("Edit Font"))
    {
      String font = readXMLFont();
      if (font.equals(getFont().getFontName()))
        return "";
      saveXMLFont();
      return getType() + ":" + getID() + "," + action + "," + font + "," +
          getFont().getFontName();
    }
    //修改字体尺寸
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
    //修改边框
    if (action.equals("Edit Border"))
    {
      String border = readXMLBorder();
      if (border.equals(getBorderTag()))
        return "";
      saveXMLBorder();
      return getType() + ":" + getID() + "," + action + "," + border + "," +
          getBorderTag();
    }
    //修改对齐方式
    if (action.equals("Edit Alignment"))
    {
      int alignment = readXMLAlignment();
      if (getHorizontalAlignment() == alignment)
        return "";
      saveXMLAlignment();
      return getType() + ":" + getID() + "," + action + "," + alignment + "," +
          getHorizontalAlignment();
    }
    //修改字体颜色
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
    //修改背景颜色
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
   * 回放动作日志
   * @param action 动作
   * @param data 数据
   */
  public boolean loadWorkLog(String action, String data)
  {
    //名称改变回放
    if (action.equals("Edit Name"))
    {
      setName(data);
      saveXMLName();
      return true;
    }
    //标签改变回放
    if (action.equals("Edit Tag"))
    {
      setTag(data);
      saveXMLTag();
      return true;
    }
    //文本改变回放
    if (action.equals("Edit Text"))
    {
      setText(data);
      saveXMLText();
      return true;
    }
    //字体改变回放
    if (action.equals("Edit Font"))
    {
      setFont(new Font(data, getFont().getStyle(), getFont().getSize()));
      saveXMLFont();
      return true;
    }
    //字体尺寸改变回放
    if (action.equals("Edit Font Size"))
    {
      setFont(new Font(getFont().getFontName(), getFont().getStyle(),
                       Integer.parseInt(data)));
      saveXMLFontSize();
      return true;
    }
    //字体类型改变回放
    if (action.equals("Edit Font Style"))
    {
      setFont(new Font(getFont().getFontName(), Integer.parseInt(data),
                       getFont().getSize()));
      saveXMLFontStyle();
      return true;
    }
    //边框改变回放
    if (action.equals("Edit Border"))
    {
      setBorderTag(data);
      saveXMLBorder();
      return true;
    }
    //对齐方式改变回放
    if (action.equals("Edit Alignment"))
    {
      setHorizontalAlignment(Integer.parseInt(data));
      saveXMLAlignment();
      return true;
    }
    //文字颜色改变回放
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
    //背景颜色改变回放
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
   * 事件接口,处理子控件上传消息
   * @param tag 产生事件的组件名称
   * @param eventName 事件类型
   * @param eventObj 事件对象
   */
  public void eventListener(String tag, String eventName, Object eventObj)
  {

  }

  /**
   * 处理外部功能调用请求
   * @param action 功能名称
   * @param parm 参数
   */
  public void callEventListener(String action, Object parm)
  {
    if (action.equals(DoubleClicked))
      onDoubleClicked();
  }

  /**
   * 双击事件
   */
  public void onDoubleClicked()
  {
    //TextDialog dialog = new TextDialog(this);
    //dialog.setVisible(true);
  }
}
