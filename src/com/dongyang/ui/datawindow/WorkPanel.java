package com.dongyang.ui.datawindow;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 *
 * <p>Title: DataWindow�������</p>
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
   * ������
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
   * �����ߴ��¼�
   * @param x
   * @param y
   * @param obj �ı�Ķ���
   * @param tag ������
   */
  public void resizeEvent(int x,int y,Object obj,String tag)
  {
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
    setLocation(x,getY());
  }
  /**
   * ����Yλ��
   * @param y λ��
   */
  public void setY(int y)
  {
    setLocation(getX(),y);
  }
  /**
   * ���ÿ��
   * @param width ���
   */
  public void setWidth(int width)
  {
    this.setSize(width,getHeight());
  }
  /**
   * ���ø߶�
   * @param height �߶�
   */
  public void setHeight(int height)
  {
    this.setSize(getWidth(),height);
  }
  /**
   * �õ���ѡ���
   * @return ���
   */
  public int getPreferredWidth()
  {
    return (int)this.getPreferredSize().getWidth();
  }
  /**
   * �õ���ѡ�߶�
   * @return �߶�
   */
  public int getPreferredHeight()
  {
    return (int)this.getPreferredSize().getHeight();
  }
  /**
   * ������ѡ���
   * @param width ���
   */
  public void setPreferredWidth(int width)
  {
    if(width <= getPreferredWidth())
      return;
    setPreferredSize(new Dimension(width,getPreferredHeight()));
    getParent().validate();
  }
  /**
   * ������ѡ�߶�
   * @param height �߶�
   */
  public void setPreferredHeight(int height)
  {
    if(height <= getPreferredHeight())
      return;
    setPreferredSize(new Dimension(getPreferredWidth(),height));
    getParent().validate();
  }
}
