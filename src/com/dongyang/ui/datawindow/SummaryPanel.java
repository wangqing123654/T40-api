package com.dongyang.ui.datawindow;

import java.awt.*;

/**
 *
 * <p>Title: �ϼ��������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.08
 * @version 1.0
 */
public class SummaryPanel
    extends AreaPanel
{
  /**
   * ������
   */
  public SummaryPanel()
  {
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * ��ʼ��
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception
  {
    this.setBackground(Color.white);
    this.setLayout(null);
  }
}