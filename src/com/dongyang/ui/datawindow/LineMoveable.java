package com.dongyang.ui.datawindow;

import java.awt.event.*;
import java.awt.*;

/**
 *
 * <p>Title: 线段移动缩放控制设备</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 东佑项目部</p>
 * @author lzk 2006.07.20
 * @version 1.0
 */
public class LineMoveable
    implements MouseListener, MouseMotionListener
{
  //被控线段对象
  private DLine object;
  //鼠标点下距离边框位置
  private int oldX; //距离左边框位置
  private int oldY; //距离上边框位置
  private int oldX1; //距离右边框位置
  private int oldY1; //距离下边框位置
  //鼠标当前状态
  private int state;
  //两顶点指示标
  private DPoint p1, p2;
  //选中控制对象
  private SelectControl selectControl;
  /**
   * 构造器
   * @param object
   */
  public LineMoveable(DLine object)
  {
    this.object = object;
  }

  /**
   * 鼠标点击
   * @param e
   */
  public void mouseClicked(MouseEvent e)
  {
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
    //检测选中
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
    //保存点下位置
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

        //带动全部选中的对象一起移动
        getSelectControl().mouseDragged(mX - oldX, mY - oldY);
      else
      {
        //自身移动
        object.setLocation(x, y);
        setPointBounds(x, y, width, height);
      }
      return;
    }
    //顶点移动
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
   * 交换鼠标参照点
   * @param i 1 左右交换 2 上下交换
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
   * 设置状态,光标
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
   * 边框尺寸改变
   * @param x 横坐标
   * @param y 纵坐标
   * @param width 宽度
   * @param height 高度
   */
  public void setBounds(int x, int y, int width, int height)
  {
    Rectangle bound = object.getBounds();
    if (height < 1) //上下交换
    {
      if (state == 7) //右下 -> 右上
      {
        if (object.getPointOne() == 1)
          object.setPointOne(4);
        else if (object.getPointOne() == 3)
          object.setPointOne(2);
        setState(6);
      }
      else if (state == 6) //右上 -> 右下
      {
        if (object.getPointOne() == 4)
          object.setPointOne(1);
        else if (object.getPointOne() == 2)
          object.setPointOne(3);
        setState(7);
        y = bound.y + bound.height - 1;
      }
      else if (state == 8) //左下 -> 左上
      {
        if (object.getPointOne() == 4)
          object.setPointOne(1);
        else if (object.getPointOne() == 2)
          object.setPointOne(3);
        setState(5);
      }
      else if (state == 5) //左上 -> 左下
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
    else if (width < 1) //左右交换
    {
      if (state == 7) //右下 -> 左下
      {
        if (object.getPointOne() == 1)
          object.setPointOne(2);
        else if (object.getPointOne() == 3)
          object.setPointOne(4);
        setState(8);
      }
      else if (state == 8) //左下 -> 右下
      {
        if (object.getPointOne() == 2)
          object.setPointOne(1);
        else if (object.getPointOne() == 4)
          object.setPointOne(3);
        setState(7);
        x = bound.x + bound.width - 1;
      }
      else if (state == 5) // 左上 -> 右上
      {
        if (object.getPointOne() == 1)
          object.setPointOne(2);
        else if (object.getPointOne() == 3)
          object.setPointOne(4);
        setState(6);
        x = bound.x + bound.width - 1;
      }
      else if (state == 6) //右上 -> 左下
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
    //改变两顶点指示标
    setPointBounds(x, y, width, height);
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
   * 设置两顶点指示标
   * @param p1 左上
   * @param p2 右上
   * @param p3 右下
   * @param p4 左下
   */
  public void setPoint(DPoint p1, DPoint p2)
  {
    this.p1 = p1;
    this.p2 = p2;
  }

  /**
   * 移除两顶点指示标
   */
  public void removePoint()
  {
    this.p1 = null;
    this.p2 = null;
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
    }
    return null;
  }

  /**
   * 改变两顶点指示标位置
   * @param x 横坐标
   * @param y 纵坐标
   * @param width 宽度
   * @param height 高度
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
   * 移动顶点指示标坐标
   * @param x 横坐标
   * @param y 纵坐标
   * @param pos 指示标类型 1 左上 2 右下
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
   * 移动
   * @param x 新位置
   * @param y 新位置
   */
  public void move(int x, int y)
  {
    object.setLocation(x, y);
    //四顶点跟随
    setPointBounds(x, y, object.getWidth(), object.getHeight());
  }

  /**
   * 改变尺寸
   * @param x 偏移量
   * @param y 偏移量
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
