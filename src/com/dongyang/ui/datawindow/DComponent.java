package com.dongyang.ui.datawindow;

import com.dongyang.config.INode;


public interface DComponent
{

  public String saveWorkLog(String action, INode parent);

  /**
   * �õ���������
   * @return
   */
  public String getType();

  /**
   * �õ�ϵͳID
   * @return
   */
  public int getID();

  /**
   * ����ϵͳID
   * @param id
   */
  public void setID(int id);

  /**
   * �����ı���Ϣ
   * @param s
   */
  public void loadString(String s);

  /**
   * �õ�����
   * @return
   */
  public String getArea();

  /**
   * �����������XMLԪ��
   * @return
   */
  public INode createXML();

  /**
   * �õ�XMLԪ��
   * @return
   */
  public INode getXML();
  /**
   * �طŶ�����־
   * @param action ����
   * @param data ����
   */
  public boolean loadWorkLog(String action, String data);
  /**
   * clone ����һ������
   * @return ���������
   */
  public Object clone();

}
