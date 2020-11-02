package com.dongyang.ui.datawindow;
import javax.swing.*;
import java.awt.*;
/**
 *
 * <p>Title: 顶点显示黑方框</p>
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
   * 构造器
   */
  public DPoint()
  {
    setSize(4, 4);
  }

  /**
   * 绘制方框
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
