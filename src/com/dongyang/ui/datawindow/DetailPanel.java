package com.dongyang.ui.datawindow;

import java.awt.*;

/**
 *
 * <p>Title: 区域对象</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.08
 * @version 1.0
 */
public class DetailPanel
    extends AreaPanel
{
  /**
   * 构造器
   */
  public DetailPanel()
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
   * 初始化
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception
  {
    this.setBackground(Color.white);
    this.setLayout(null);
  }
}
