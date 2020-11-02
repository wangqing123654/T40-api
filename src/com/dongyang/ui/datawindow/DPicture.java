package com.dongyang.ui.datawindow;

import java.awt.*;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;

/**
 *
 * <p>Title: 图片对象</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.08
 * @version 1.0
 */
public class DPicture
    extends DText
{
  /**
   * 构造器
   */
  public DPicture()
  {
  }
  /**
   * 构造器
   * @param text
   */
  public DPicture(String text)
  {
    super(text);
  }
  /**
   * clone 复制一个对象
   * @return 自身对象景象
   */
  public Object clone()
  {
    DPicture picture = new DPicture();
    clone(picture);
    return picture;
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
    setText(getName());
  }
  /**
   * 得到对象类型
   * @return
   */
  public String getType()
  {
    return "picture";
  }

}
