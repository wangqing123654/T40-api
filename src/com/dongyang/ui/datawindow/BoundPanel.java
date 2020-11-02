package com.dongyang.ui.datawindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * <p>Title: ����Χ��ʾ��</p>
 * <p>Description: ����Χ��ʾ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class BoundPanel extends WorkPanel
{
  //��ʾ����
  JLabel jLabel = new JLabel();
  //���ֹ�����
  BorderLayout borderLayout1 = new BorderLayout();
  //������Yλ��
  private int oldY;
  //���
  private String tag;
  /**
   * ������
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
   * ������
   * @param text ����������
   */
  public BoundPanel(String text)
  {
    this();
    setText(text);
  }
  /**
   * ��������������
   * @param text ����������
   */
  public void setText(String text)
  {
    jLabel.setText(text);
  }
  /**
   * �õ�����������
   * @return ����������
   */
  public String getText()
  {
    return jLabel.getText();
  }
  /**
   * �õ������
   * @return
   */
  public String getTag()
  {
    return tag;
  }
  /**
   * ���ñ����
   * @param tag �����
   */
  public void setTag(String tag)
  {
    this.tag = tag;
  }
  /**
   * ��ʼ��
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {

    this.setLayout(borderLayout1);
    this.setBorder(BorderFactory.createLineBorder(Color.black));
    jLabel.setFont(new java.awt.Font("����", 0, 12));
    this.add(jLabel,  BorderLayout.CENTER);
  }
  /**
   * ������,��¼����yλ��
   * @param e ����¼�����
   */
  public void mousePressed(MouseEvent e)
  {
    oldY = e.getY();
    if(getParent() instanceof WorkPanel)
    ((WorkPanel)getParent()).grabFocus();
  }
  /**
   * �����ͷ�
   * @param e
   */
  public void mouseReleased(MouseEvent e)
  {
    ((BackPanel)getParent()).saveLog(tag,"height");
  }
  /**
   * ����ƶ�
   * @param e ����¼�����
   */
  public void mouseMoved(MouseEvent e)
  {
    setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
  }
  /**
   * ����϶�,�ı�������λ��
   * @param e ����¼�����
   */
  public void mouseDragged(MouseEvent e)
  {
    int y = getY() + e.getY() - oldY;

    //�ı���Ϣ���ݸ��ϼ����
    if(getParent() instanceof WorkPanel)
      ((WorkPanel)getParent()).resizeEvent(getX(),y,this,tag);
  }
}
