package com.dongyang.ui.datawindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * <p>Title: 区域范围显示条</p>
 * <p>Description: 区域范围显示条</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class BoundPanel extends WorkPanel
{
  //显示文字
  JLabel jLabel = new JLabel();
  //布局管理器
  BorderLayout borderLayout1 = new BorderLayout();
  //鼠标点下Y位置
  private int oldY;
  //标记
  private String tag;
  /**
   * 构造器
   */
  public BoundPanel()
  {
    try{
      jbInit();
    }catch(Exception e)
    {
    }
  }
  /**
   * 构造器
   * @param text 区域条文字
   */
  public BoundPanel(String text)
  {
    this();
    setText(text);
  }
  /**
   * 设置区域条文字
   * @param text 区域条文字
   */
  public void setText(String text)
  {
    jLabel.setText(text);
  }
  /**
   * 得到区域条文字
   * @return 区域条文字
   */
  public String getText()
  {
    return jLabel.getText();
  }
  /**
   * 得到标记名
   * @return
   */
  public String getTag()
  {
    return tag;
  }
  /**
   * 设置标记名
   * @param tag 标记名
   */
  public void setTag(String tag)
  {
    this.tag = tag;
  }
  /**
   * 初始化
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {

    this.setLayout(borderLayout1);
    this.setBorder(BorderFactory.createLineBorder(Color.black));
    jLabel.setFont(new java.awt.Font("宋体", 0, 12));
    this.add(jLabel,  BorderLayout.CENTER);
  }
  /**
   * 鼠标点下,记录点下y位置
   * @param e 鼠标事件参数
   */
  public void mousePressed(MouseEvent e)
  {
    oldY = e.getY();
    if(getParent() instanceof WorkPanel)
    ((WorkPanel)getParent()).grabFocus();
  }
  /**
   * 鼠标键释放
   * @param e
   */
  public void mouseReleased(MouseEvent e)
  {
    ((BackPanel)getParent()).saveLog(tag,"height");
  }
  /**
   * 鼠标移动
   * @param e 鼠标事件参数
   */
  public void mouseMoved(MouseEvent e)
  {
    setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
  }
  /**
   * 鼠标拖动,改变纵坐标位置
   * @param e 鼠标事件参数
   */
  public void mouseDragged(MouseEvent e)
  {
    int y = getY() + e.getY() - oldY;

    //改变信息传递给上级面板
    if(getParent() instanceof WorkPanel)
      ((WorkPanel)getParent()).resizeEvent(getX(),y,this,tag);
  }
}
