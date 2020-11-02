package com.dongyang.ui.datawindow;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import com.dongyang.config.INode;

/**
 *
 * <p>Title: 区域面板</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 东佑项目部</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class AreaPanel
    extends WorkPanel
{
  //区域
  private String area;
  /**
   * 鼠标键点下
   * @param e 鼠标事件
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
        //开始扩选
        getSelectControl().createSelectBox(e.getX() + getX(), e.getY() + getY());
      }
    }
  }

  /**
   * 鼠标键释放
   * @param e
   */
  public void mouseReleased(MouseEvent e)
  {
    if (e.getButton() == 1)
    {
      //取消扩选
      getSelectControl().removeSelectBox();
    }
  }

  /**
   * 鼠标拖动
   * @param e
   */
  public void mouseDragged(MouseEvent e)
  {
    //移动扩选
    getSelectControl().moveSelectBox(e.getX() + getX(), e.getY() + getY());
  }

  /**
   * 创建文本
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
   * 创建图片
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
   * 创建列
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
   * 创建子数据窗口
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
   * 创建线
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
   * 得到选中控制
   * @return 控制对象
   */
  public SelectControl getSelectControl()
  {
    return ( (BackPanel) getParent()).getSelectControl();
  }

  /**
   * 得到当前状态
   * @return String 状态
   */
  public String getState()
  {
    return ( (BackPanel) getParent()).getState();
  }

  /**
   * 设置当前状态
   * @param state 状态
   */
  public void setState(String state)
  {
    ( (BackPanel) getParent()).setState(state);
  }

  /**
   * 检测范围
   * @param y 纵坐标
   * @param height 高度
   * @return true 在范围内 false 不再范围内
   */
  public boolean checkPanel(int y, int height)
  {
    if (y >= getY())
      return true;
    return false;
  }

  /**
   * 检测扩选
   * @param x1 左上 x
   * @param y1 左上 y
   * @param x2 右下 x
   * @param y2 右下 y
   */
  public void checkInclude(int x1, int y1, int x2, int y2)
  {
    Component c[] = getComponents();
    for (int i = 0; i < c.length; i++)
    {
      //检测成员
      if (c[i] instanceof IMoveable)
      {
        IMoveable obj = (IMoveable) c[i];
        if (obj.checkInclude(x1, y1, x2, y2, getSelectControl().getSelectType()))
          getSelectControl().addSelect(obj);
      }
    }
  }

  /**
   * 设置区域
   * @param area
   */
  public void setArea(String area)
  {
    this.area = area;
  }

  /**
   * 得到区域
   * @return
   */
  public String getArea()
  {
    return area;
  }

  /**
   * 全部选中
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
   * 加载 DataWindow XML文件
   * @param element
   * @return
   */
  public boolean load()
  {
    INode xmlElement = ( (BackPanel) getParent()).getConfigElement(
        getArea());
    //调整区域高度
    ( (BackPanel) getParent()).resizeArea(xmlElement.getAttributeValueAsInteger(
        "height").intValue(), getArea());

    //加载Text
    loadText( ( (BackPanel) getParent()).getConfigElement("texts"));
    //加载Picture
    loadPicture( ( (BackPanel) getParent()).getConfigElement("pictures"));
    //加载Picture
    loadChildDataWindow( ( (BackPanel) getParent()).getConfigElement("childdatawindows"));
    //加载Column
    loadColumn( ( (BackPanel) getParent()).getConfigElement("columns"));
    //加载Line
    loadLine( ( (BackPanel) getParent()).getConfigElement("lines"));
    return true;
  }

  /**
   * 加载Text
   * @param xmlElement XML元素
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
   * 加载Picture
   * @param xmlElement XML元素
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
   * 加载ChildDataWindow
   * @param xmlElement XML元素
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
   * 加载Column
   * @param xmlElement XML元素
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
   * 加载Line
   * @param xmlElement XML元素
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
   * 得到组件对象
   * @param id 组件ID
   * @param type 组件类型
   * @return 组件对象
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
