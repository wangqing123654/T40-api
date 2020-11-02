package com.dongyang.ui.datawindow;

import com.dongyang.config.INode;


public interface DComponent
{

  public String saveWorkLog(String action, INode parent);

  /**
   * 得到对象类型
   * @return
   */
  public String getType();

  /**
   * 得到系统ID
   * @return
   */
  public int getID();

  /**
   * 设置系统ID
   * @param id
   */
  public void setID(int id);

  /**
   * 加载文本信息
   * @param s
   */
  public void loadString(String s);

  /**
   * 得到区域
   * @return
   */
  public String getArea();

  /**
   * 创建本组件的XML元素
   * @return
   */
  public INode createXML();

  /**
   * 得到XML元素
   * @return
   */
  public INode getXML();
  /**
   * 回放动作日志
   * @param action 动作
   * @param data 数据
   */
  public boolean loadWorkLog(String action, String data);
  /**
   * clone 复制一个对象
   * @return 自身对象景象
   */
  public Object clone();

}
