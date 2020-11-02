package com.dongyang.ui.datawindow;

import java.awt.*;
import com.dongyang.ui.event.TDataWindowEvent;
/**
 *
 * <p>Title: ����ƶ����ſ����豸�ӿ�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.27
 * @version 1.0
 */
public interface IMoveable extends TDataWindowEvent
{
  /**
   * �Ƿ��������
   * @return true ���� false ������
   */
  public boolean isResizable();
  /**
   * �Ƿ�����ƶ�
   * @return  true ���� false ������
   */
  public boolean isMoveable();
  /**
   * �õ��齨λ��
   * @return Point�����
   */
  public Point getLocation();
  /**
   * ����λ��
   * @param x ������
   * @param y ������
   */
  public void setLocation(int x,int y);
  /**
   * ���ù����ʽ
   * @param cursor ������
   */
  public void setCursor(Cursor cursor);
  /**
   * �õ��߿�ߴ�
   * @return Rectangle
   */
  public Rectangle getBounds();
  /**
   * ���ñ߿�ߴ�
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  public void setBounds(int x,int y,int width,int height);
  /**
   * ���ô�С�ߴ�
   * @param width ���
   * @param height �߶�
   */
  public void setSize(int width,int height);
  /**
   * �õ��ߴ�
   * @return Dimension
   */
  public Dimension getSize();
  /**
   * �õ���������
   * @return ����
   */
  public Container getParent();
  /**
   * �õ�Xλ��
   * @return Xλ��
   */
  public int getX();
  /**
   * �õ�Yλ��
   * @return Yλ��
   */
  public int getY();
  /**
   * ����Xλ��
   * @param x λ��
   */
  public void setX(int x);
  /**
   * ����Yλ��
   * @param y λ��
   */
  public void setY(int y);
  /**
   * �õ����
   * @return ���
   */
  public int getWidth();
  /**
   * �õ��߶�
   * @return �߶�
   */
  public int getHeight();
  /**
   * ���ÿ��
   * @param width ���
   */
  public void setWidth(int width);
  /**
   * ���ø߶�
   * @param height �߶�
   */
  public void setHeight(int height);
  /**
   * �����ѡ
   * @param x1 ���� x
   * @param y1 ���� y
   * @param x2 ���� x
   * @param y2 ���� y
   * @param type ���� 1�Ӵ�ѡ 2����ѡ
   * @return true �ڷ�Χ�� false ����
   */
  public boolean checkInclude(int x1,int y1,int x2,int y2,int type);
  /**
   * �õ�ϵͳID
   * @return ϵͳID
   */
  public int getID();
  /**
   * �õ���������
   * @return ��������
   */
  public String getType();
  /**
   * �����ⲿ���ܵ�������
   * @param action ��������
   * @param parm ����
   */
  public void callEventListener(String action,Object parm);

}
