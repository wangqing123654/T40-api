package com.dongyang.ui.datawindow;

import java.awt.event.*;
import java.awt.*;
import com.dongyang.ui.event.TDataWindowEvent;

/**
 *
 * <p>Title: �ı�����ƶ����ſ����豸</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ŀ��</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class Moveable
    implements MouseListener, MouseMotionListener
{
  //���ƶ���
  private IMoveable object;
  //�����¾���������
  private int oldX;
  //�����¾����ϲ����
  private int oldY;
  //�����¾����²����
  private int oldX1;
  //�����¾����Ҳ����
  private int oldY1;
  //��ǰ�϶�״̬
  private int state;
  //ѡ�п��ƶ���
  private SelectControl selectControl;
  //�Ķ���ָʾ��
  private DPoint p1, p2, p3, p4;
  /**
   * ������
   * @param object ���ض���
   */
  public Moveable(IMoveable object)
  {
    this.object = object;
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
   * �����Ķ���ָʾ��
   * @param p1 ����
   * @param p2 ����
   * @param p3 ����
   * @param p4 ����
   */
  public void setPoint(DPoint p1, DPoint p2, DPoint p3, DPoint p4)
  {
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
    this.p4 = p4;
  }

  /**
   * �Ƴ��Ķ���ָʾ��
   */
  public void removePoint()
  {
    this.p1 = null;
    this.p2 = null;
    this.p3 = null;
    this.p4 = null;
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
      case 3:
        return p3;
      case 4:
        return p4;
    }
    return null;
  }

  /**
   * �����
   * @param e
   */
  public void mouseClicked(MouseEvent e)
  {
    if(e.getButton() == 1)
        if(e.getClickCount() == 2)
          object.callEventListener(TDataWindowEvent.DoubleClicked,e);
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
    if (!object.isMoveable() && !object.isResizable())
      return;
    int x = object.getX();
    int y = object.getY();
    int width = object.getWidth();
    int height = object.getHeight();
    int mX = e.getX();
    int mY = e.getY();

    if (object.isMoveable() && state == 0)
    {
      x = x + mX - oldX;
      y = y + mY - oldY;
      if (getSelectControl() != null)
        getSelectControl().mouseDragged(mX - oldX, mY - oldY);
      else
      {
        object.setLocation(x, y);
        setPointBounds(x, y, width, height);
      }
      return;
    }
    if (object.isResizable())
    {
      setCursor();
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
  }

  /**
   * �ı�λ�úͳߴ�
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void setBounds(int x, int y, int width, int height)
  {
    //���÷�ת
    Rectangle bound = object.getBounds();
    if (height < 1)
    {
      if (state == 7)
        setState(6);
      else if (state == 6)
      {
        setState(7);
        y = bound.y + bound.height - 1;
      }
      else if (state == 8)
        setState(5);
      else if (state == 5)
      {
        setState(8);
        y = bound.y + bound.height - 1;
      }
      change(2);
      height = 1;
    }
    else if (width < 1)
    {
      if (state == 7)
        setState(8);
      else if (state == 8)
      {
        setState(7);
        x = bound.x + bound.width - 1;
      }
      else if (state == 5)
      {
        setState(6);
        x = bound.x + bound.width - 1;
      }
      else if (state == 6)
      {
        setState(5);
        width = x - bound.x;
      }
      change(1);
      width = 1;
    }
    //�ı�λ�óߴ�
    object.setBounds(x, y, width, height);
    //�ı��Ķ���ָʾ��
    setPointBounds(x, y, width, height);
  }

  /**
   * �ı��Ķ���ָʾ��λ��
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void setPointBounds(int x, int y, int width, int height)
  {
    Point p = object.getParent().getLocation();
    movePoint(x + p.x, y + p.y, 1);
    movePoint(x + p.x + width, y + p.y, 2);
    movePoint(x + p.x + width, y + p.y + height, 3);
    movePoint(x + p.x, y + p.y + height, 4);
  }

  /**
   * �ƶ�����ָʾ������
   * @param x ������
   * @param y ������
   * @param pos ָʾ������ 1 ���� 2 ���� 3 ���� 4 ����
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
        y -= 3;
        point = p2;
        break;
      case 3:
        x -= 1;
        y -= 1;
        point = p3;
        break;
      case 4:
        x -= 3;
        y -= 1;
        point = p4;
    }
    if (point == null)
      return;
    point.setLocation(x, y);
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
    int length = 4;
    int x = (int) bound.getX();
    int y = (int) bound.getY();
    int width = (int) bound.getWidth();
    int height = (int) bound.getHeight();
    int mX = e.getX();
    int mY = e.getY();
    if (mX < length && mY < length)
      setState(5);
    else if (mX < length && mY > height - length)
      setState(8);
    else if (mX < length)
      setState(3);
    else if (mX > width - length && mY < length)
      setState(6);
    else if (mX > width - length && mY > height - length)
      setState(7);
    else if (mX > width - length)
      setState(4);
    else if (mY < length)
      setState(1);
    else if (mY > height - length)
      setState(2);
    else
      setState(0);
  }

  /**
   * �ı���
   */
  public void setCursor()
  {
    int cursor = 0;
    switch (state)
    {
      case 1:
      case 2:
        cursor = Cursor.N_RESIZE_CURSOR;
        break;
      case 3:
      case 4:
        cursor = Cursor.E_RESIZE_CURSOR;
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
   * ��ָ�ƶ�״̬
   * @param state
   */
  public void setState(int state)
  {
    this.state = state;
    setCursor();
  }

  /**
   * ��ת����
   * @param type 1 ���ҽ��� 2 ���½���
   */
  public void change(int type)
  {
    if (type == 1)
    {
      int temp = oldX;
      oldX = oldX1;
      oldX1 = temp;
    }
    else if (type == 2)
    {
      int temp = oldY;
      oldY = oldY1;
      oldY1 = temp;
    }
  }

  /**
   * �ƶ�
   * @param x ����
   * @param y ����
   */
  public void move(int x, int y)
  {
    object.setLocation(x, y);
    setPointBounds(x, y, object.getWidth(), object.getHeight());
  }

  /**
   * �ߴ�ı�
   * @param x ƫ����
   * @param y ƫ����
   */
  public void resize(int x, int y)
  {
    object.setSize(object.getWidth() + x, object.getHeight() + y);
    setPointBounds(object.getX(), object.getY(), object.getWidth(),
                   object.getHeight());
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
