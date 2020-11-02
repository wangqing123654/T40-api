package com.dongyang.ui.datawindow;

import java.awt.*;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;

/**
 *
 * <p>Title: ͼƬ����</p>
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
   * ������
   */
  public DPicture()
  {
  }
  /**
   * ������
   * @param text
   */
  public DPicture(String text)
  {
    super(text);
  }
  /**
   * clone ����һ������
   * @return ���������
   */
  public Object clone()
  {
    DPicture picture = new DPicture();
    clone(picture);
    return picture;
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
    setText(getName());
  }
  /**
   * �õ���������
   * @return
   */
  public String getType()
  {
    return "picture";
  }

}
