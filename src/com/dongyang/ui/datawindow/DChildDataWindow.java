package com.dongyang.ui.datawindow;

import com.dongyang.config.TNode;
import com.dongyang.config.INode;


/**
 *
 * <p>Title: 子数据窗口对象</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 东佑项目部</p>
 * @author lzk 2007.01.16
 * @version 1.0
 */
public class DChildDataWindow
    extends DText
{
  /**
   * 构造器
   */
  public DChildDataWindow()
  {
  }
  /**
   * 构造器
   * @param text
   */
  public DChildDataWindow(String text)
  {
    super(text);
  }
  /**
   * clone 复制一个对象
   * @return 自身对象景象
   */
  public Object clone()
  {
    DChildDataWindow datawindow = new DChildDataWindow();
    clone(datawindow);
    return datawindow;
  }
  /**
   * 创建本组件的XML元素
   * @return
   */
  public INode createXML()
  {
    if(getXML() == null)
      setXML(new TNode(getType()));
    saveXMLName();
    saveXMLTag();
    //saveXMLText();
    saveXMLAlignment();
    saveXMLSize();
    saveXMLColor();
    saveXMLBackColor();
    saveXMLFont();
    saveXMLFontSize();
    saveXMLFontStyle();
    saveXMLBorder();
    return getXML();
  }
  /**
   * 同步XML信息
   */
  public void loadXML()
  {
    super.loadXML();
    //setText(getName());
  }
  /**
   * 得到对象类型
   * @return
   */
  public String getType()
  {
    return "childdatawindow";
  }

}
