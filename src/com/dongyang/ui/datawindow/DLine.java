package com.dongyang.ui.datawindow;

import java.awt.*;
import javax.swing.JLabel;
import java.util.StringTokenizer;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;

/**
 *
 * <p>Title: 线对象</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 东佑项目部</p>
 * @author lzk 2006.07.05
 * @version 1.0
 */
public class DLine
    extends JLabel
    implements IMoveable,DComponent
{
  //最大序号
  public static int SID;
  /**
   * 得到序号
   * @return
   */
  static synchronized int getSID()
  {
    SID++;
    return SID;
  }

  //序号
  private int id;
  //是否可以动态改变尺寸
  private boolean resizable;
  //动态改变位置的设备
  private boolean moveable;
  //反向
  private boolean reversal;
  //第一个点的位置
  private int pOne;
  //移动设备
  LineMoveable moveableObject = new LineMoveable(this);
  //区域
  private String area;
  //XML元素
  INode xml;
  /**
   * 构造器
   */
  public DLine()
  {
    setID(getSID());
    onInit();
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
   * 是否反向画线
   * @return true 反向 false 正向
   */
  public boolean isReversal()
  {
    return reversal;
  }

  /**
   * 设置反向画线
   * @param reversal true 反向 false 正向
   */
  public void setReversal(boolean reversal)
  {
    this.reversal = reversal;
  }

  /**
   * 设置顶点
   * @param pOne 1 左上 2 右上 3 右下 4 左下
   */
  public void setPointOne(int pOne)
  {
    this.pOne = pOne;
  }

  /**
   * 得到顶点
   * @return 1 左上 2 右上 3 右下 4 左下
   */
  public int getPointOne()
  {
    return pOne;
  }

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
   * 初始化
   */
  public void onInit()
  {
    setPointOne(1);
    this.addMouseListener(moveableObject);
    this.addMouseMotionListener(moveableObject);
  }

  /**
   * 画线
   * @param g 图形对象
   */
  public void paint(Graphics g)
  {
    super.paint(g);
    if (isReversal())
      g.drawLine(0, getHeight() - 1, getWidth() - 1, 0);
    else
      g.drawLine(0, 0, getWidth() - 1, getHeight() - 1);
  }

  /**
   * 检测是否在自定范围
   * @param x
   * @param y
   * @return true 包含 false 不包含
   */
  public boolean contains(int x, int y)
  {
    Dimension size = getSize();
    if (x < 0 || x > size.getWidth() || y < 0 || y > size.getHeight())
      return false;
    int l;
    if (isReversal())
      l = (int) Math.abs( (size.getWidth() - x) * size.getHeight() -
                         y * size.getWidth());
    else
      l = (int) Math.abs(x * size.getHeight() - y * size.getWidth());
    return l < 500;
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
        if (isReversal())
        {
          if (checkLineInclude(x, height + y, width + x, y, x1, y1, x2, y2))
            return true;
        }
        else
        {
          if (checkLineInclude(x, y, width + x, height + y, x1, y1, x2, y2))
            return true;
        }
      case 2: //包含选
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2
            && width <= x2 - x1 - x + x1
            && width <= y2 - y1 - y + y1)
          return true;
    }
    return false;
  }

  /**
   * 检测线段与平面相交
   * @param x01 线段
   * @param y01 线段
   * @param x02 线段
   * @param y02 线段
   * @param x1 平面
   * @param y1 平面
   * @param x2 平面
   * @param y2 平面
   * @return true 相交 false 不相交
   */
  public boolean checkLineInclude(int x01, int y01, int x02, int y02, int x1,
                                  int y1, int x2, int y2)
  {
    int x = Math.abs(x02 - x01);
    int y = Math.abs(y02 - y01);
    int r = (int) Math.sqrt(x * x + y * y);

    for (int r0 = 0; r0 <= r; r0++)
    {
      x = (int) ( (double) r0 / (double) r * (x02 - x01) + x01);
      y = (int) ( (double) r0 / (double) r * (y02 - y01) + y01);
      this.repaint();
      if (checkPoint(x, y, x1, y1, x2, y2))
        return true;
    }
    return false;
  }

  /**
   * 检测点与平面相交
   * @param x 点
   * @param y 点
   * @param x1 平面
   * @param y1 平面
   * @param x2 平面
   * @param y2 平面
   * @return true 相交 false 不相交
   */
  public boolean checkPoint(int x, int y, int x1, int y1, int x2, int y2)
  {
    return x >= x1 && x <= x2 && y >= y1 && y <= y2;
  }

  public LineMoveable getMoveable()
  {
    return moveableObject;
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
   * clone 复制一个对象
   * @return 自身对象景象
   */
  public Object clone()
  {
    DLine line = new DLine();
    line.setText(getText());
    line.setArea(getArea());
    line.setLocation(getLocation());
    line.setSize(getSize());
    line.setFont(getFont());
    line.setPointOne(getPointOne());
    line.setReversal(isReversal());
    return line;
  }

  /**
   * 创建本组件的XML元素
   * @return
   */
  public INode createXML()
  {
    INode element = new TNode("line");
    element.setAttributeValue("band", getArea());
    element.setAttributeValue("border", 0);
    element.setAttributeValue("x1", getX());
    element.setAttributeValue("y1", getY());
    element.setAttributeValue("x2", getWidth() + getX() - 1);
    element.setAttributeValue("y2", getHeight() + getY() - 1);
    setXML(element);
    return element;
  }

  /**
   * 同步XML信息
   */
  public void loadXML()
  {
    setArea(getXML().getAttributeValue("band"));
    lXML xml1 = new lXML();
    setPointOne(xml1.pointOne);
    setReversal(xml1.reversal);
    setBounds(new Rectangle(xml1.x, xml1.y, xml1.width, xml1.height));
  }

  /**
   *
   * <p>Title: XML加载子类</p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2006</p>
   * <p>Company: 东佑项目部</p>
   * @author lzk 2006.08.08
   * @version 1.0
   */
  public class lXML
  {
    public int pointOne;
    public boolean reversal;
    public int x;
    public int y;
    public int width;
    public int height;
    /**
     * 构造器
     */
    public lXML()
    {
      int x1 = getXML().getAttributeValueAsInteger("x1").intValue();
      int y1 = getXML().getAttributeValueAsInteger("y1").intValue();
      int x2 = getXML().getAttributeValueAsInteger("x2").intValue() + 1;
      int y2 = getXML().getAttributeValueAsInteger("y2").intValue() + 1;
      boolean bx = false;
      boolean by = false;
      if (x1 > x2)
      {
        int temp = x1;
        x1 = x2;
        x2 = temp;
        bx = true;
      }
      if (y1 > y2)
      {
        int temp = y1;
        y1 = y2;
        y2 = temp;
        by = true;
      }
      if (bx)
      {
        if (by)
          pointOne = 3;
        else
        {
          pointOne = 2;
          reversal = true;
        }
      }
      else if (by)
      {
        pointOne = 4;
        reversal = true;
      }
      else
        pointOne = 1;
      x = x1;
      y = y1;
      width = x2 - x1;
      height = y2 - y1;
    }

    /**
     * 清空
     */
    public void reset()
    {
      x = getX();
      y = getY();
      width = getWidth();
      height = getHeight();
      pointOne = getPointOne();
      reversal = isReversal();
    }

    /**
     * 保存
     */
    public void save()
    {
      switch (pointOne)
      {
        case 1:
          getXML().setAttributeValue("x1", x);
          getXML().setAttributeValue("y1", y);
          getXML().setAttributeValue("x2", width + x - 1);
          getXML().setAttributeValue("y2", height + y - 1);
          break;
        case 2:
          getXML().setAttributeValue("x1", width + x - 1);
          getXML().setAttributeValue("y1", y);
          getXML().setAttributeValue("x2", x);
          getXML().setAttributeValue("y2", height + y - 1);
          break;
        case 3:
          getXML().setAttributeValue("x1", width + x - 1);
          getXML().setAttributeValue("y1", height + y - 1);
          getXML().setAttributeValue("x2", x);
          getXML().setAttributeValue("y2", y);
          break;
        case 4:
          getXML().setAttributeValue("x1", x);
          getXML().setAttributeValue("y1", height + y - 1);
          getXML().setAttributeValue("x2", width + x - 1);
          getXML().setAttributeValue("y2", y);
          break;
      }
    }
  }

  /**
   * 转换成文本信息
   * @return
   */
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("band=" + area + ";");
    sb.append("name=" + getName() + ";");
    sb.append("x=" + getX() + ";");
    sb.append("y=" + getY() + ";");
    sb.append("width=" + getWidth() + ";");
    sb.append("height=" + getHeight() + ";");
    sb.append("reversal=" + isReversal() + ";");
    sb.append("pOne=" + getPointOne());
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
      else if (name.equals("name"))
        setName(value);
      else if (name.equals("x"))
        setX(Integer.parseInt(value));
      else if (name.equals("y"))
        setY(Integer.parseInt(value));
      else if (name.equals("width"))
        setWidth(Integer.parseInt(value));
      else if (name.equals("height"))
        setHeight(Integer.parseInt(value));
      else if (name.equals("reversal"))
        setReversal(value.toLowerCase().equals("true"));
      else if (name.equals("pOne"))
        setPointOne(Integer.parseInt(value));
    }
  }

  /**
   * 得到对象类型
   * @return
   */
  public String getType()
  {
    return "line";
  }

  /**
   * 保存移动信息
   * @return
   */
  public String saveMove()
  {
    StringBuffer sb = new StringBuffer();
    lXML xml1 = new lXML();
    sb.append(getType() + ":" + getID() + ";");
    sb.append("oldx=" + xml1.x + ";");
    sb.append("oldy=" + xml1.y + ";");
    sb.append("oldwidth=" + xml1.width + ";");
    sb.append("oldheight=" + xml1.height + ";");
    sb.append("oldband=" + getXML().getAttributeValue("band") + ";");
    sb.append("oldpointOne=" + xml1.pointOne + ";");
    sb.append("oldreversal=" + xml1.reversal + ";");
    sb.append("x=" + getX() + ";");
    sb.append("y=" + getY() + ";");
    sb.append("width=" + getWidth() + ";");
    sb.append("height=" + getHeight() + ";");
    sb.append("band=" + getArea() + ";");
    sb.append("pointOne=" + getPointOne() + ";");
    sb.append("reversal=" + isReversal() + ";");
    xml1.reset();
    xml1.save();
    getXML().setAttributeValue("band", getArea());
    return sb.toString();
  }

  /**
   * 加载移动信息
   * @param s
   * @param b
   */
  public void loadMove(String s, boolean b)
  {
    StringTokenizer stk = new StringTokenizer(s, ";");
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
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
        else if (name.equals("band"))
          band = value;
        else if (name.equals("width"))
          width = Integer.parseInt(value);
        else if (name.equals("height"))
          height = Integer.parseInt(value);
        else if (name.equals("pointOne"))
          setPointOne(Integer.parseInt(value));
        else if (name.equals("reversal"))
          setReversal(value.toLowerCase().equals("true"));
      }
      else
      {
        if (name.equals("oldx"))
          x = Integer.parseInt(value);
        else if (name.equals("oldy"))
          y = Integer.parseInt(value);
        else if (name.equals("oldband"))
          band = value;
        else if (name.equals("oldwidth"))
          width = Integer.parseInt(value);
        else if (name.equals("oldheight"))
          height = Integer.parseInt(value);
        else if (name.equals("oldpointOne"))
          setPointOne(Integer.parseInt(value));
        else if (name.equals("oldreversal"))
          setReversal(value.toLowerCase().equals("true"));
      }
    }
    if (!band.equals(getArea()))
    {
      if (getParent().getParent()instanceof BackPanel)
      {
        AreaPanel panel = ( (BackPanel) getParent().getParent()).getArea(band);
        getParent().remove(this);
        panel.add(this, 0);
        setArea(band);
      }
    }

    getMoveable().setSize(x, y, width, height);
    lXML xml1 = new lXML();
    xml1.reset();
    xml1.save();
    getXML().setAttributeValue("band", getArea());
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
  /**
   * 保存动作日志
   * @param action 动作名称
   * @param parent 父类XML对象
   * @return 日志信息
   */
  public String saveWorkLog(String action, INode parent)
  {
    //新建 Line 组件
    if (action.equals("Create"))
    {
      parent.addChildElement(createXML());
      return getType() + ":" + getID() + "," + action + "," + this;
    }
    //删除 Delete 组件
    if (action.equals("Delete"))
    {
      parent.removeChildElement(getXML());
      return getType() + ":" + getID() + "," + action + "," + this;
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
    return false;
  }
}
