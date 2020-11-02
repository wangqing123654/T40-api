package com.dongyang.ui.datawindow;

import java.awt.event.*;
import java.awt.*;
import com.dongyang.ui.event.TDataWindowEvent;

/**
 *
 * <p>Title: 文本组件移动缩放控制设备</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 东佑项目部</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class Moveable
    implements MouseListener, MouseMotionListener
{
  //控制对象
  private IMoveable object;
  //鼠标点下距离左侧距离
  private int oldX;
  //鼠标点下距离上侧距离
  private int oldY;
  //鼠标点下距离下侧距离
  private int oldX1;
  //鼠标点下距离右侧距离
  private int oldY1;
  //当前拖动状态
  private int state;
  //选中控制对象
  private SelectControl selectControl;
  //四顶点指示标
  private DPoint p1, p2, p3, p4;
  /**
   * 构造器
   * @param object 被控对象
   */
  public Moveable(IMoveable object)
  {
    this.object = object;
  }

  /**
   * 设置选中控制对象
   * @param selectControl 选中控制对象
   */
  public void setSelectControl(SelectControl selectControl)
  {
    this.selectControl = selectControl;
  }

  /**
   * 得到选中控制对象
   * @return 选中控制对象
   */
  public SelectControl getSelectControl()
  {
    return selectControl;
  }

  /**
   * 设置四顶点指示标
   * @param p1 左上
   * @param p2 右上
   * @param p3 右下
   * @param p4 左下
   */
  public void setPoint(DPoint p1, DPoint p2, DPoint p3, DPoint p4)
  {
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
    this.p4 = p4;
  }

  /**
   * 移除四顶点指示标
   */
  public void removePoint()
  {
    this.p1 = null;
    this.p2 = null;
    this.p3 = null;
    this.p4 = null;
  }

  /**
   * 得到四顶点指示标对象
   * @param index 位置 1 左上 2 右上 3 右下 4 左下
   * @return 顶点指示标对象
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
   * 鼠标点击
   * @param e
   */
  public void mouseClicked(MouseEvent e)
  {
    if(e.getButton() == 1)
        if(e.getClickCount() == 2)
          object.callEventListener(TDataWindowEvent.DoubleClicked,e);
  }

  /**
   * 鼠标进入
   * @param e
   */
  public void mouseEntered(MouseEvent e)
  {

  }

  /**
   * 鼠标离开
   * @param e
   */
  public void mouseExited(MouseEvent e)
  {

  }

  /**
   * 鼠标点下
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
   * 鼠标键释放
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
   * 鼠标拖动
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
   * 改变位置和尺寸
   * @param x 横坐标
   * @param y 纵坐标
   * @param width 宽度
   * @param height 高度
   */
  private void setBounds(int x, int y, int width, int height)
  {
    //设置反转
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
    //改变位置尺寸
    object.setBounds(x, y, width, height);
    //改变四顶点指示标
    setPointBounds(x, y, width, height);
  }

  /**
   * 改变四顶点指示标位置
   * @param x 横坐标
   * @param y 纵坐标
   * @param width 宽度
   * @param height 高度
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
   * 移动顶点指示标坐标
   * @param x 横坐标
   * @param y 纵坐标
   * @param pos 指示标类型 1 左上 2 右上 3 右下 4 左下
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
   * 鼠标移动
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
   * 改变光标
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
   * 是指移动状态
   * @param state
   */
  public void setState(int state)
  {
    this.state = state;
    setCursor();
  }

  /**
   * 反转交换
   * @param type 1 左右交换 2 上下交换
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
   * 移动
   * @param x 向量
   * @param y 向量
   */
  public void move(int x, int y)
  {
    object.setLocation(x, y);
    setPointBounds(x, y, object.getWidth(), object.getHeight());
  }

  /**
   * 尺寸改变
   * @param x 偏移量
   * @param y 偏移量
   */
  public void resize(int x, int y)
  {
    object.setSize(object.getWidth() + x, object.getHeight() + y);
    setPointBounds(object.getX(), object.getY(), object.getWidth(),
                   object.getHeight());
  }

  /**
   * 设置新尺寸
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
