package com.dongyang.ui.datawindow;
import javax.swing.*;
import java.awt.*;
/**
 *
 * <p>Title: ������ʾ�ڷ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.08
 * @version 1.0
 */
public class DPoint
    extends JLabel
{
  /**
   * ������
   */
  public DPoint()
  {
    setSize(4, 4);
  }

  /**
   * ���Ʒ���
   * @param g
   */
  public void paint(Graphics g)
  {
    super.paint(g);
    Dimension d = this.getSize();
    int width = (int) d.getWidth();
    int height = (int) d.getHeight();
    g.fillRect(0, 0, width - 1, d.height - 1);
  }
}
