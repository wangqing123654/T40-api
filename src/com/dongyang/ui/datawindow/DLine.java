package com.dongyang.ui.datawindow;

import java.awt.*;
import javax.swing.JLabel;
import java.util.StringTokenizer;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;

/**
 *
 * <p>Title: �߶���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ŀ��</p>
 * @author lzk 2006.07.05
 * @version 1.0
 */
public class DLine
    extends JLabel
    implements IMoveable,DComponent
{
  //������
  public static int SID;
  /**
   * �õ����
   * @return
   */
  static synchronized int getSID()
  {
    SID++;
    return SID;
  }

  //���
  private int id;
  //�Ƿ���Զ�̬�ı�ߴ�
  private boolean resizable;
  //��̬�ı�λ�õ��豸
  private boolean moveable;
  //����
  private boolean reversal;
  //��һ�����λ��
  private int pOne;
  //�ƶ��豸
  LineMoveable moveableObject = new LineMoveable(this);
  //����
  private String area;
  //XMLԪ��
  INode xml;
  /**
   * ������
   */
  public DLine()
  {
    setID(getSID());
    onInit();
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
   * �Ƿ�����
   * @return true ���� false ����
   */
  public boolean isReversal()
  {
    return reversal;
  }

  /**
   * ���÷�����
   * @param reversal true ���� false ����
   */
  public void setReversal(boolean reversal)
  {
    this.reversal = reversal;
  }

  /**
   * ���ö���
   * @param pOne 1 ���� 2 ���� 3 ���� 4 ����
   */
  public void setPointOne(int pOne)
  {
    this.pOne = pOne;
  }

  /**
   * �õ�����
   * @return 1 ���� 2 ���� 3 ���� 4 ����
   */
  public int getPointOne()
  {
    return pOne;
  }

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
   * ��ʼ��
   */
  public void onInit()
  {
    setPointOne(1);
    this.addMouseListener(moveableObject);
    this.addMouseMotionListener(moveableObject);
  }

  /**
   * ����
   * @param g ͼ�ζ���
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
   * ����Ƿ����Զ���Χ
   * @param x
   * @param y
   * @return true ���� false ������
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
      case 2: //����ѡ
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2
            && width <= x2 - x1 - x + x1
            && width <= y2 - y1 - y + y1)
          return true;
    }
    return false;
  }

  /**
   * ����߶���ƽ���ཻ
   * @param x01 �߶�
   * @param y01 �߶�
   * @param x02 �߶�
   * @param y02 �߶�
   * @param x1 ƽ��
   * @param y1 ƽ��
   * @param x2 ƽ��
   * @param y2 ƽ��
   * @return true �ཻ false ���ཻ
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
   * ������ƽ���ཻ
   * @param x ��
   * @param y ��
   * @param x1 ƽ��
   * @param y1 ƽ��
   * @param x2 ƽ��
   * @param y2 ƽ��
   * @return true �ཻ false ���ཻ
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
   * clone ����һ������
   * @return ���������
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
   * �����������XMLԪ��
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
   * ͬ��XML��Ϣ
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
   * <p>Title: XML��������</p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2006</p>
   * <p>Company: ������Ŀ��</p>
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
     * ������
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
     * ���
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
     * ����
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
   * ת�����ı���Ϣ
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
   * �õ���������
   * @return
   */
  public String getType()
  {
    return "line";
  }

  /**
   * �����ƶ���Ϣ
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
   * �����ƶ���Ϣ
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
  /**
   * ���涯����־
   * @param action ��������
   * @param parent ����XML����
   * @return ��־��Ϣ
   */
  public String saveWorkLog(String action, INode parent)
  {
    //�½� Line ���
    if (action.equals("Create"))
    {
      parent.addChildElement(createXML());
      return getType() + ":" + getID() + "," + action + "," + this;
    }
    //ɾ�� Delete ���
    if (action.equals("Delete"))
    {
      parent.removeChildElement(getXML());
      return getType() + ":" + getID() + "," + action + "," + this;
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
    return false;
  }
}
