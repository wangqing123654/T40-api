package com.dongyang.ui.datawindow;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import com.dongyang.config.INode;

/**
 *
 * <p>Title: �������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ŀ��</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class AreaPanel
    extends WorkPanel
{
  //����
  private String area;
  /**
   * ��������
   * @param e ����¼�
   */
  public void mousePressed(MouseEvent e)
  {
    if (e.getButton() == 1)
    {
      if (getState().equals("Create Text"))
        createText(e.getX(), e.getY());
      else if (getState().equals("Create Picture"))
        createPicture(e.getX(), e.getY());
      else if (getState().equals("Create Line"))
        createLine(e.getX(), e.getY());
      else if (getState().equals("Create Column"))
        createColumn(e.getX(), e.getY());
      else if(getState().equals("Create DataWindow"))
        createChildDataWindow(e.getX(), e.getY());
      else
      {
        getSelectControl().removeSelects();
        //��ʼ��ѡ
        getSelectControl().createSelectBox(e.getX() + getX(), e.getY() + getY());
      }
    }
  }

  /**
   * �����ͷ�
   * @param e
   */
  public void mouseReleased(MouseEvent e)
  {
    if (e.getButton() == 1)
    {
      //ȡ����ѡ
      getSelectControl().removeSelectBox();
    }
  }

  /**
   * ����϶�
   * @param e
   */
  public void mouseDragged(MouseEvent e)
  {
    //�ƶ���ѡ
    getSelectControl().moveSelectBox(e.getX() + getX(), e.getY() + getY());
  }

  /**
   * �����ı�
   * @param x
   * @param y
   */
  private void createText(int x, int y)
  {
    getSelectControl().removeSelects();
    DText text = new DText("text");
    text.setBounds(new Rectangle(x, y, 30, 18));
    text.setArea(getArea());
    add(text, 0);
    getSelectControl().addSelect(text);
    setState("");
    ( (BackPanel) getParent()).saveLog("text:" + text.getID(), "Create");
  }

  /**
   * ����ͼƬ
   * @param x
   * @param y
   */
  public void createPicture(int x, int y)
  {
    getSelectControl().removeSelects();
    DPicture picture = new DPicture("picture");
    picture.setBounds(new Rectangle(x, y, 30, 30));
    picture.setArea(getArea());
    add(picture, 0);
    getSelectControl().addSelect(picture);
    setState("");
    ( (BackPanel) getParent()).saveLog("picture:" + picture.getID(), "Create");
  }

  /**
   * ������
   * @param x
   * @param y
   */
  private void createColumn(int x, int y)
  {
    getSelectControl().removeSelects();
    DColumn column = new DColumn("column");
    column.setBounds(new Rectangle(x, y, 40, 18));
    column.setBorder(BorderFactory.createEtchedBorder());
    column.setArea(getArea());
    add(column, 0);
    getSelectControl().addSelect(column);
    setState("");
    ( (BackPanel) getParent()).saveLog("column:" + column.getID(), "Create");
  }
  /**
   * ���������ݴ���
   * @param x int
   * @param y int
   */
  private void createChildDataWindow(int x,int y)
  {
    getSelectControl().removeSelects();
    DChildDataWindow childDataWindow = new DChildDataWindow("childdatawindow");
    childDataWindow.setBounds(new Rectangle(x, y, 40, 18));
    childDataWindow.setBorder(BorderFactory.createEtchedBorder());
    childDataWindow.setArea(getArea());
    add(childDataWindow, 0);
    getSelectControl().addSelect(childDataWindow);
    setState("");
    ( (BackPanel) getParent()).saveLog("childdatawindow:" + childDataWindow.getID(), "Create");

  }
  /**
   * ������
   * @param x
   * @param y
   */
  private void createLine(int x, int y)
  {
    getSelectControl().removeSelects();
    DLine line = new DLine();
    line.setBounds(new Rectangle(x, y, 30, 1));
    line.setArea(getArea());
    add(line, 0);
    getSelectControl().addSelect(line);
    setState("");
    ( (BackPanel) getParent()).saveLog("line:" + line.getID(), "Create");
  }

  /**
   * �õ�ѡ�п���
   * @return ���ƶ���
   */
  public SelectControl getSelectControl()
  {
    return ( (BackPanel) getParent()).getSelectControl();
  }

  /**
   * �õ���ǰ״̬
   * @return String ״̬
   */
  public String getState()
  {
    return ( (BackPanel) getParent()).getState();
  }

  /**
   * ���õ�ǰ״̬
   * @param state ״̬
   */
  public void setState(String state)
  {
    ( (BackPanel) getParent()).setState(state);
  }

  /**
   * ��ⷶΧ
   * @param y ������
   * @param height �߶�
   * @return true �ڷ�Χ�� false ���ٷ�Χ��
   */
  public boolean checkPanel(int y, int height)
  {
    if (y >= getY())
      return true;
    return false;
  }

  /**
   * �����ѡ
   * @param x1 ���� x
   * @param y1 ���� y
   * @param x2 ���� x
   * @param y2 ���� y
   */
  public void checkInclude(int x1, int y1, int x2, int y2)
  {
    Component c[] = getComponents();
    for (int i = 0; i < c.length; i++)
    {
      //����Ա
      if (c[i] instanceof IMoveable)
      {
        IMoveable obj = (IMoveable) c[i];
        if (obj.checkInclude(x1, y1, x2, y2, getSelectControl().getSelectType()))
          getSelectControl().addSelect(obj);
      }
    }
  }

  /**
   * ��������
   * @param area
   */
  public void setArea(String area)
  {
    this.area = area;
  }

  /**
   * �õ�����
   * @return
   */
  public String getArea()
  {
    return area;
  }

  /**
   * ȫ��ѡ��
   */
  public void selectAll()
  {
    for (int index = 0; index < this.getComponentCount(); index++)
    {
      Component c = this.getComponent(index);
      if (c instanceof DText || c instanceof DLine)
        getSelectControl().addSelect( (IMoveable) c);
    }
  }

  /**
   * ���� DataWindow XML�ļ�
   * @param element
   * @return
   */
  public boolean load()
  {
    INode xmlElement = ( (BackPanel) getParent()).getConfigElement(
        getArea());
    //��������߶�
    ( (BackPanel) getParent()).resizeArea(xmlElement.getAttributeValueAsInteger(
        "height").intValue(), getArea());

    //����Text
    loadText( ( (BackPanel) getParent()).getConfigElement("texts"));
    //����Picture
    loadPicture( ( (BackPanel) getParent()).getConfigElement("pictures"));
    //����Picture
    loadChildDataWindow( ( (BackPanel) getParent()).getConfigElement("childdatawindows"));
    //����Column
    loadColumn( ( (BackPanel) getParent()).getConfigElement("columns"));
    //����Line
    loadLine( ( (BackPanel) getParent()).getConfigElement("lines"));
    return true;
  }

  /**
   * ����Text
   * @param xmlElement XMLԪ��
   */
  private void loadText(INode xmlElement)
  {
    if(xmlElement == null)
      return;
    Iterator iterator = xmlElement.getChildElements();
    while (iterator.hasNext())
    {
      INode textxml = (INode) iterator.next();
      if (!textxml.getAttributeValue("band").equals(getArea()))
        continue;
      DText text = new DText();
      text.setXML(textxml);
      text.loadXML();
      text.getMoveable().setSelectControl(getSelectControl());
      add(text, 0);
      ( (BackPanel) getParent()).checkPreferredWidth(text.getX() +
          text.getHeight());
    }
  }

  /**
   * ����Picture
   * @param xmlElement XMLԪ��
   */
  private void loadPicture(INode xmlElement)
  {
    if(xmlElement == null)
      return;
    Iterator iterator = xmlElement.getChildElements();
    while (iterator.hasNext())
    {
      INode textxml = (INode) iterator.next();
      if (!textxml.getAttributeValue("band").equals(getArea()))
        continue;
      DPicture picture = new DPicture();
      picture.setXML(textxml);
      picture.loadXML();
      picture.getMoveable().setSelectControl(getSelectControl());
      add(picture, 0);
      ( (BackPanel) getParent()).checkPreferredWidth(picture.getX() +
          picture.getHeight());
    }
  }
  /**
   * ����ChildDataWindow
   * @param xmlElement XMLԪ��
   */
  private void loadChildDataWindow(INode xmlElement)
  {
    if(xmlElement == null)
      return;
    Iterator iterator = xmlElement.getChildElements();
    while (iterator.hasNext())
    {
      INode textxml = (INode) iterator.next();
      if (!textxml.getAttributeValue("band").equals(getArea()))
        continue;
      DChildDataWindow childDataWindow = new DChildDataWindow();
      childDataWindow.setXML(textxml);
      childDataWindow.loadXML();
      childDataWindow.getMoveable().setSelectControl(getSelectControl());
      add(childDataWindow, 0);
      ( (BackPanel) getParent()).checkPreferredWidth(childDataWindow.getX() +
          childDataWindow.getHeight());
    }
  }
  /**
   * ����Column
   * @param xmlElement XMLԪ��
   */
  private void loadColumn(INode xmlElement)
  {
    if(xmlElement == null)
      return;
    Iterator iterator = xmlElement.getChildElements();
    while (iterator.hasNext())
    {
      INode columnxml = (INode) iterator.next();
      if (!columnxml.getAttributeValue("band").equals(getArea()))
        continue;
      DColumn column = new DColumn();
      column.setXML(columnxml);
      column.loadXML();
      column.getMoveable().setSelectControl(getSelectControl());
      add(column, 0);
      ( (BackPanel) getParent()).checkPreferredWidth(column.getX() +
          column.getHeight());
    }
  }

  /**
   * ����Line
   * @param xmlElement XMLԪ��
   */
  private void loadLine(INode xmlElement)
  {
    Iterator iterator = xmlElement.getChildElements();
    while (iterator.hasNext())
    {
      INode linexml = (INode) iterator.next();
      if (!linexml.getAttributeValue("band").equals(getArea()))
        continue;
      DLine line = new DLine();
      line.setXML(linexml);
      line.loadXML();
      line.getMoveable().setSelectControl(getSelectControl());
      add(line, 0);
      ( (BackPanel) getParent()).checkPreferredWidth(line.getX() +
          line.getHeight());
    }
  }

  /**
   * �õ��������
   * @param id ���ID
   * @param type �������
   * @return �������
   */
  public DComponent getObjectForID(int id, String type)
  {
    for (int index = 0; index < this.getComponentCount(); index++)
    {
      Component c = getComponent(index);
      if(c instanceof DComponent && ((DComponent)c).getType().equals(type)&&
         ((DComponent)c).getID() == id)
        return (DComponent)c;
    }
    return null;
  }
}
