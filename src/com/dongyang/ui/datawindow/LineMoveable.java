package com.dongyang.ui.datawindow;

import java.awt.event.*;
import java.awt.*;

/**
 *
 * <p>Title: �߶��ƶ����ſ����豸</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ŀ��</p>
 * @author lzk 2006.07.20
 * @version 1.0
 */
public class LineMoveable
    implements MouseListener, MouseMotionListener
{
  //�����߶ζ���
  private DLine object;
  //�����¾���߿�λ��
  private int oldX; //������߿�λ��
  private int oldY; //�����ϱ߿�λ��
  private int oldX1; //�����ұ߿�λ��
  private int oldY1; //�����±߿�λ��
  //��굱ǰ״̬
  private int state;
  //������ָʾ��
  private DPoint p1, p2;
  //ѡ�п��ƶ���
  private SelectControl selectControl;
  /**
   * ������
   * @param object
   */
  public LineMoveable(DLine object)
  {
    this.object = object;
  }

  /**
   * �����
   * @param e
   */
  public void mouseClicked(MouseEvent e)
  {
  }

  /**
   * ������
   * @param e
   */
  public void mouseEntered(MouseEvent e)
  {
  }

  /**
   * ����뿪
   * @param e
   */
  public void mouseExited(MouseEvent e)
  {
  }

  /**
   * ������
   * @param e
   */
  public void mousePressed(MouseEvent e)
  {
    if (e.getButton() != 1)
      return;
    //���ѡ��
    if (!object.isMoveable() && !object.isResizable())
    {
      if (getSelectControl() == null)
        return;
      if (!getSelectControl().mousePressed(object, e.isControlDown()))
        return;
    }
    else if (e.isControlDown())
    {
      if (getSelectControl() != null)
        if (!getSelectControl().mousePressed(object, e.isControlDown()))
          return;
    }
    //�������λ��
    Dimension size = object.getSize();
    oldX = e.getX();
    oldY = e.getY();
    oldX1 = (int) size.getWidth() - e.getX();
    oldY1 = (int) size.getHeight() - e.getY();
  }

  /**
   * �����ͷ�
   * @param e
   */
  public void mouseReleased(MouseEvent e)
  {
    if (object.isMoveable() && state == 0)
    {
      if (getSelectControl() != null)
        getSelectControl().saveMove();
    }
    else if (object.isResizable() && state > 0)
    {
      getSelectControl().saveMove();
    }
  }

  /**
   * ����϶�
   * @param e
   */
  public void mouseDragged(MouseEvent e)
  {
    Rectangle bound = object.getBounds();
    int x = (int) bound.getX();
    int y = (int) bound.getY();
    int width = (int) bound.getWidth();
    int height = (int) bound.getHeight();
    int mX = e.getX();
    int mY = e.getY();

    if (object.isMoveable() && state == 0)
    {
      x = x + mX - oldX;
      y = y + mY - oldY;
      if (getSelectControl() != null)

        //����ȫ��ѡ�еĶ���һ���ƶ�
        getSelectControl().mouseDragged(mX - oldX, mY - oldY);
      else
      {
        //�����ƶ�
        object.setLocation(x, y);
        setPointBounds(x, y, width, height);
      }
      return;
    }
    //�����ƶ�
    if (object.isResizable())
      switch (state)
      {
        case 1:
          setBounds(x, y + mY - oldY, width, height - mY + oldY);
          break;
        case 2:
          setBounds(x, y, width, mY + oldY1);
          break;
        case 3:
          setBounds(x + mX - oldX, y, width - mX + oldX, height);
          break;
        case 4:
          setBounds(x, y, mX + oldX1, height);
          break;
        case 5:
          setBounds(x + mX - oldX, y + mY - oldY, width - mX + oldX,
                    height - mY + oldY);
          break;
        case 6:
          setBounds(x, y + mY - oldY, mX + oldX1, height - mY + oldY);
          break;
        case 7:
          setBounds(x, y, mX + oldX1, mY + oldY1);
          break;
        case 8:
          setBounds(x + mX - oldX, y, width - mX + oldX, mY + oldY1);
          break;
      }
  }

  /**
   * ���������յ�
   * @param i 1 ���ҽ��� 2 ���½���
   */
  public void change(int i)
  {
    if (i == 1)
    {
      int temp = oldX;
      oldX = oldX1;
      oldX1 = temp;
    }
    else if (i == 2)
    {
      int temp = oldY;
      oldY = oldY1;
      oldY1 = temp;
    }
    object.setReversal(!object.isReversal());
  }

  /**
   * ����ƶ�
   * @param e
   */
  public void mouseMoved(MouseEvent e)
  {
    if (!object.isMoveable() && !object.isResizable())
    {
      setState(0);
      return;
    }
    if (getSelectControl() != null && getSelectControl().onlyMove())
    {
      setState(0);
      return;
    }
    Rectangle bound = object.getBounds();
    int length = 8;
    int x = (int) bound.getX();
    int y = (int) bound.getY();
    int width = (int) bound.getWidth();
    int height = (int) bound.getHeight();
    int mX = e.getX();
    int mY = e.getY();

    if (!object.isReversal() && mX < length && mY < length)
      setState(5);
    else if (object.isReversal() && mX < length && mY > height - length)
      setState(8);
    else if (object.isReversal() && mX > width - length && mY < length)
      setState(6);
    else if (!object.isReversal() && mX > width - length &&
             mY > height - length)
      setState(7);
    else
      setState(0);
  }

  /**
   * ����״̬,���
   * @param state
   */
  public void setState(int state)
  {
    int cursor = 0;
    this.state = state;
    switch (state)
    {
      case 0:
        break;
      case 5:
      case 7:
        cursor = Cursor.NW_RESIZE_CURSOR;
        break;
      case 6:
      case 8:
        cursor = Cursor.NE_RESIZE_CURSOR;
        break;
    }
    object.setCursor(new Cursor(cursor));
  }

  /**
   * �߿�ߴ�ı�
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  public void setBounds(int x, int y, int width, int height)
  {
    Rectangle bound = object.getBounds();
    if (height < 1) //���½���
    {
      if (state == 7) //���� -> ����
      {
        if (object.getPointOne() == 1)
          object.setPointOne(4);
        else if (object.getPointOne() == 3)
          object.setPointOne(2);
        setState(6);
      }
      else if (state == 6) //���� -> ����
      {
        if (object.getPointOne() == 4)
          object.setPointOne(1);
        else if (object.getPointOne() == 2)
          object.setPointOne(3);
        setState(7);
        y = bound.y + bound.height - 1;
      }
      else if (state == 8) //���� -> ����
      {
        if (object.getPointOne() == 4)
          object.setPointOne(1);
        else if (object.getPointOne() == 2)
          object.setPointOne(3);
        setState(5);
      }
      else if (state == 5) //���� -> ����
      {
        if (object.getPointOne() == 1)
          object.setPointOne(4);
        else if (object.getPointOne() == 3)
          object.setPointOne(2);
        setState(8);
        y = bound.y + bound.height - 1;
      }
      change(2);
      height = 1;
    }
    else if (width < 1) //���ҽ���
    {
      if (state == 7) //���� -> ����
      {
        if (object.getPointOne() == 1)
          object.setPointOne(2);
        else if (object.getPointOne() == 3)
          object.setPointOne(4);
        setState(8);
      }
      else if (state == 8) //���� -> ����
      {
        if (object.getPointOne() == 2)
          object.setPointOne(1);
        else if (object.getPointOne() == 4)
          object.setPointOne(3);
        setState(7);
        x = bound.x + bound.width - 1;
      }
      else if (state == 5) // ���� -> ����
      {
        if (object.getPointOne() == 1)
          object.setPointOne(2);
        else if (object.getPointOne() == 3)
          object.setPointOne(4);
        setState(6);
        x = bound.x + bound.width - 1;
      }
      else if (state == 6) //���� -> ����
      {
        if (object.getPointOne() == 2)
          object.setPointOne(1);
        else if (object.getPointOne() == 4)
          object.setPointOne(3);
        setState(5);
        width = x - bound.x;
      }
      change(1);
      width = 1;
    }
    object.setBounds(x, y, width, height);
    //�ı�������ָʾ��
    setPointBounds(x, y, width, height);
  }

  /**
   * ����ѡ�п��ƶ���
   * @param selectControl ѡ�п��ƶ���
   */
  public void setSelectControl(SelectControl selectControl)
  {
    this.selectControl = selectControl;
  }

  /**
   * �õ�ѡ�п��ƶ���
   * @return ѡ�п��ƶ���
   */
  public SelectControl getSelectControl()
  {
    return selectControl;
  }

  /**
   * ����������ָʾ��
   * @param p1 ����
   * @param p2 ����
   * @param p3 ����
   * @param p4 ����
   */
  public void setPoint(DPoint p1, DPoint p2)
  {
    this.p1 = p1;
    this.p2 = p2;
  }

  /**
   * �Ƴ�������ָʾ��
   */
  public void removePoint()
  {
    this.p1 = null;
    this.p2 = null;
  }

  /**
   * �õ��Ķ���ָʾ�����
   * @param index λ�� 1 ���� 2 ���� 3 ���� 4 ����
   * @return ����ָʾ�����
   */
  public DPoint getPoint(int index)
  {
    switch (index)
    {
      case 1:
        return p1;
      case 2:
        return p2;
    }
    return null;
  }

  /**
   * �ı�������ָʾ��λ��
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void setPointBounds(int x, int y, int width, int height)
  {
    Point p = object.getParent().getLocation();
    if (object.isReversal())
    {
      movePoint(x + p.x + width, y + p.y, 1);
      movePoint(x + p.x, y + p.y + height, 2);
    }
    else
    {
      movePoint(x + p.x, y + p.y, 1);
      movePoint(x + p.x + width, y + p.y + height, 2);
    }
  }

  /**
   * �ƶ�����ָʾ������
   * @param x ������
   * @param y ������
   * @param pos ָʾ������ 1 ���� 2 ����
   */
  private void movePoint(int x, int y, int pos)
  {
    DPoint point = null;
    switch (pos)
    {
      case 1:
        x -= 3;
        y -= 3;
        point = p1;
        break;
      case 2:
        x -= 1;
        y -= 1;
        point = p2;
        break;
    }
    if (point == null)
      return;
    point.setLocation(x, y);
  }

  /**
   * �ƶ�
   * @param x ��λ��
   * @param y ��λ��
   */
  public void move(int x, int y)
  {
    object.setLocation(x, y);
    //�Ķ������
    setPointBounds(x, y, object.getWidth(), object.getHeight());
  }

  /**
   * �ı�ߴ�
   * @param x ƫ����
   * @param y ƫ����
   */
  public void resize(int x, int y)
  {
    switch (object.getPointOne())
    {
      case 1:
        setBounds(object.getX(), object.getY(), object.getWidth() + x,
                  object.getHeight() + y);
        break;
      case 2:
        setBounds(object.getX() + x, object.getY(), object.getWidth() - x,
                  object.getHeight() + y);
        break;
      case 3:
        setBounds(object.getX() + x, object.getY() + y, object.getWidth() - x,
                  object.getHeight() - y);
        break;
      case 4:
        setBounds(object.getX(), object.getY() + y, object.getWidth() + x,
                  object.getHeight() - y);
        break;
    }
  }

  /**
   * �����³ߴ�
   * @param x
   * @param y
   * @param width
   * @param height
   */
  public void setSize(int x, int y, int width, int height)
  {
    object.setLocation(x, y);
    object.setSize(width, height);
    setPointBounds(x, y, width, height);
  }
}
