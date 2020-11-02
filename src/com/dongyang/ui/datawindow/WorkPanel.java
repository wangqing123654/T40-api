package com.dongyang.ui.datawindow;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 *
 * <p>Title: DataWindow工具面板</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class WorkPanel extends JPanel
    implements MouseListener, MouseMotionListener
{
  /**
   * 构造器
   */
  public WorkPanel()
  {
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
  }
  public void mouseClicked(MouseEvent e)
  {

  }

  public void mouseEntered(MouseEvent e)
  {

  }

  public void mouseExited(MouseEvent e)
  {

  }

  public void mousePressed(MouseEvent e)
  {
  }

  public void mouseReleased(MouseEvent e)
  {

  }

  public void mouseDragged(MouseEvent e)
  {
  }

  public void mouseMoved(MouseEvent e)
  {
  }
  /**
   * 调整尺寸事件
   * @param x
   * @param y
   * @param obj 改变的对象
   * @param tag 对象标记
   */
  public void resizeEvent(int x,int y,Object obj,String tag)
  {
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
    setLocation(x,getY());
  }
  /**
   * 设置Y位置
   * @param y 位置
   */
  public void setY(int y)
  {
    setLocation(getX(),y);
  }
  /**
   * 设置宽度
   * @param width 宽度
   */
  public void setWidth(int width)
  {
    this.setSize(width,getHeight());
  }
  /**
   * 设置高度
   * @param height 高度
   */
  public void setHeight(int height)
  {
    this.setSize(getWidth(),height);
  }
  /**
   * 得到首选宽度
   * @return 宽度
   */
  public int getPreferredWidth()
  {
    return (int)this.getPreferredSize().getWidth();
  }
  /**
   * 得到首选高度
   * @return 高度
   */
  public int getPreferredHeight()
  {
    return (int)this.getPreferredSize().getHeight();
  }
  /**
   * 设置首选宽度
   * @param width 宽度
   */
  public void setPreferredWidth(int width)
  {
    if(width <= getPreferredWidth())
      return;
    setPreferredSize(new Dimension(width,getPreferredHeight()));
    getParent().validate();
  }
  /**
   * 设置首选高度
   * @param height 高度
   */
  public void setPreferredHeight(int height)
  {
    if(height <= getPreferredHeight())
      return;
    setPreferredSize(new Dimension(getPreferredWidth(),height));
    getParent().validate();
  }
}
