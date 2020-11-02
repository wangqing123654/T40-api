package com.dongyang.ui.datawindow;

import javax.swing.border.*;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * <p>Title: �߿�����ƶ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.08
 * @version 1.0
 */
public class BaseBorder
    extends AbstractBorder
{
  //�������,��������ο�IBorderStyle�߿�����
  String style;
  //��Ӱ��С
  int shadowLength = 3;
  /**
   * ������
   * @param style ���
   */
  public BaseBorder(String style)
  {
    this.style = style;
  }

  /**
   * ���Ʊ߿�
   * @param c �ؼ�
   * @param g ͼ���豸
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  public void paintBorder(Component c, Graphics g, int x, int y, int width,
                          int height)
  {
    if (IBorderStyle.BORDER_BOX.equals(style))
      paintBox(c, g, x, y, width, height);
    else if (IBorderStyle.BORDER_UNDERLINE.equals(style))
      paintUnderline(c, g, x, y, width, height);
    else if (IBorderStyle.BORDER_SHADOWBOX.equals(style))
      paintShadowBox(c, g, x, y, width, height);
    else if (IBorderStyle.BORDER_RAISED_BEVEL.equals(style))
      paintRaised(c, g, x, y, width, height);
    else if (IBorderStyle.BORDER_LOWERED_BEVEL.equals(style))
      paintLowered(c, g, x, y, width, height);
    else if (IBorderStyle.BORDER_ETCHED.equals(style))
      paintEtched(c, g, x, y, width, height);

  }

  /**
   * ����Box���
   * @param c �ؼ�
   * @param g ͼ���豸
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void paintBox(Component c, Graphics g, int x, int y,
                        int width, int height)
  {
    g.setColor(new Color(0, 0, 0));
    g.drawRect(x, y, width - 1, height - 1);
  }

  /**
   * �����»��߷��
   * @param c �ؼ�
   * @param g ͼ���豸
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void paintUnderline(Component c, Graphics g, int x, int y,
                              int width, int height)
  {
    g.setColor(new Color(0, 0, 0));
    g.drawLine(x, height - 1, width - 1, height - 1);
  }

  /**
   * ������Ӱ���
   * @param c �ؼ�
   * @param g ͼ���豸
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void paintShadowBox(Component c, Graphics g, int x, int y,
                              int width, int height)
  {
    g.setColor(new Color(0, 0, 0));
    g.drawRect(x, y, width - shadowLength - 1, height - shadowLength - 1);
    g.setColor(new Color(172, 168, 153));
    g.fillRect(x + shadowLength, height - shadowLength, width - shadowLength,
               shadowLength);
    g.fillRect(width - shadowLength, shadowLength, shadowLength,
               height - shadowLength);
  }

  /**
   * ����͹�����
   * @param c �ؼ�
   * @param g ͼ���豸
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void paintRaised(Component c, Graphics g, int x, int y,
                           int width, int height)
  {
    g.setColor(new Color(241, 239, 226));
    g.drawLine(x, y, x, height - 1);
    g.drawLine(x, y, width - 1, y);
    g.setColor(new Color(255, 255, 255));
    g.drawLine(x + 1, y + 1, x + 1, height - 2);
    g.drawLine(x + 1, y + 1, width - 2, y + 1);
    g.setColor(new Color(113, 111, 100));
    g.drawLine(width - 1, y, width - 1, height - 1);
    g.drawLine(x, height - 1, width - 1, height - 1);
    g.setColor(new Color(172, 168, 153));
    g.drawLine(width - 2, y - 1, width - 2, height - 2);
    g.drawLine(x + 1, height - 2, width - 2, height - 2);
  }

  /**
   * ���ư������
   * @param c �ؼ�
   * @param g ͼ���豸
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void paintLowered(Component c, Graphics g, int x, int y,
                            int width, int height)
  {
    g.setColor(new Color(172, 168, 153));
    g.drawLine(x, y, x, height - 1);
    g.drawLine(x, y, width - 1, y);
    g.setColor(new Color(113, 111, 100));
    g.drawLine(x + 1, y + 1, x + 1, height - 2);
    g.drawLine(x + 1, y + 1, width - 2, y + 1);
    g.setColor(new Color(255, 255, 255));
    g.drawLine(width - 1, y, width - 1, height - 1);
    g.drawLine(x, height - 1, width - 1, height - 1);
    g.setColor(new Color(241, 239, 226));
    g.drawLine(width - 2, y - 1, width - 2, height - 2);
    g.drawLine(x + 1, height - 2, width - 2, height - 2);
  }

  /**
   * ����ʴ�̷��
   * @param c �ؼ�
   * @param g ͼ���豸
   * @param x ������
   * @param y ������
   * @param width ���
   * @param height �߶�
   */
  private void paintEtched(Component c, Graphics g, int x, int y,
                           int width, int height)
  {
    g.setColor(new Color(159, 161, 174));
    g.drawRect(x, y, width - 2, height - 2);
    g.setColor(new Color(255, 255, 255));
    g.drawLine(x + 1, y + 1, x + 1, height - 3);
    g.drawLine(x + 1, y + 1, width - 3, y + 1);
    g.drawLine(width - 1, y, width - 1, height - 1);
    g.drawLine(x, height - 1, width - 1, height - 1);
  }

  /**
   * �õ�����ڲ��ߴ�
   * @param c ���
   * @return Insets�ڲ��ߴ�
   */
  public Insets getBorderInsets(Component c)
  {
    if (IBorderStyle.BORDER_UNDERLINE.equals(style))
      return new Insets(0, 0, 0, 1);
    else if (IBorderStyle.BORDER_BOX.equals(style))
      return new Insets(1, 1, 1, 1);
    else if (IBorderStyle.BORDER_SHADOWBOX.equals(style))
      return new Insets(1, 1, shadowLength, shadowLength);
    else if (IBorderStyle.BORDER_RAISED_BEVEL.equals(style) ||
             IBorderStyle.BORDER_LOWERED_BEVEL.equals(style) ||
             IBorderStyle.BORDER_ETCHED.equals(style))
      return new Insets(2, 2, 2, 2);
    return new Insets(0, 0, 0, 0);
  }

  /**
   * �õ�����ڲ��ߴ�
   * @param c ���
   * @param insets�ߴ�
   * @return Insets�ڲ��ߴ�
   */
  public Insets getBorderInsets(Component c, Insets insets)
  {
    if (IBorderStyle.BORDER_UNDERLINE.equals(style))
    {
      insets.left = 0;
      insets.top = 0;
      insets.right = 0;
      insets.bottom = 1;
    }
    else if (IBorderStyle.BORDER_BOX.equals(style))
    {
      insets.left = 1;
      insets.top = 1;
      insets.right = 1;
      insets.bottom = 1;
    }
    else if (IBorderStyle.BORDER_SHADOWBOX.equals(style))
    {
      insets.left = 1;
      insets.top = 1;
      insets.right = shadowLength;
      insets.bottom = shadowLength;
    }
    else if (IBorderStyle.BORDER_RAISED_BEVEL.equals(style) ||
             IBorderStyle.BORDER_LOWERED_BEVEL.equals(style) ||
             IBorderStyle.BORDER_ETCHED.equals(style))
    {
      insets.left = 2;
      insets.top = 2;
      insets.right = 2;
      insets.bottom = 2;
    }
    else
    {
      insets.left = 0;
      insets.top = 0;
      insets.right = 0;
      insets.bottom = 0;
    }
    return insets;
  }
}
