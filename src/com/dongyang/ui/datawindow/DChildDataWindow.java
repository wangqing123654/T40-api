package com.dongyang.ui.datawindow;

import com.dongyang.config.TNode;
import com.dongyang.config.INode;


/**
 *
 * <p>Title: �����ݴ��ڶ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ŀ��</p>
 * @author lzk 2007.01.16
 * @version 1.0
 */
public class DChildDataWindow
    extends DText
{
  /**
   * ������
   */
  public DChildDataWindow()
  {
  }
  /**
   * ������
   * @param text
   */
  public DChildDataWindow(String text)
  {
    super(text);
  }
  /**
   * clone ����һ������
   * @return ���������
   */
  public Object clone()
  {
    DChildDataWindow datawindow = new DChildDataWindow();
    clone(datawindow);
    return datawindow;
  }
  /**
   * �����������XMLԪ��
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
   * ͬ��XML��Ϣ
   */
  public void loadXML()
  {
    super.loadXML();
    //setText(getName());
  }
  /**
   * �õ���������
   * @return
   */
  public String getType()
  {
    return "childdatawindow";
  }

}
