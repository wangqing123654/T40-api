package com.dongyang.ui.datawindow;


import java.util.*;
import com.dongyang.ui.base.TDataWindowBase;
import com.dongyang.data.TParm;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.config.INode;

/**
 *
 * <p>Title: 数据存储设备,支持DataWindow的后台数据管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.8
 * @version 1.0
 */
public class DataStore
{
  public boolean isTestData = false;
  //XML数据
  private INode xmlElement;

  //DataWindow设备
  private TDataWindowBase datawindow;

  //列名列表
  private List columns;

  //数据
  private TParm data;

  /**
   * 构造器
   */
  public DataStore()
  {

  }

  /**
   * 构造器
   * @param datawindow 邦定的DataWindow对象
   */
  public DataStore(TDataWindowBase datawindow)
  {
    this.datawindow = datawindow;
  }

  /**
   * 得到全部数据
   * @return TParm
   */
  public TParm getData()
  {
    return data;
  }

  /**
   * 设置全部数据
   * @param data TParm
   */
  public void setData(TParm data)
  {
    this.data = data;
  }

  /**
   * 读取xml信息
   * @param element
   * @return
   */
  public boolean load(INode element)
  {
    reset();
    if(element == null)
        return true;
    xmlElement = element;
    initColumns();
    //插入测试数据
    initTestData();
    return true;
  }

  /**
   * 清空<未完成>
   */
  public void reset()
  {

  }

  /**
   * 从应用服务器读取全部数据
   * @return 数据行数
   */
  public int retrieve()
  {
      System.out.println("sql=" + getSQL());
      data = TIOM_AppServer.executeQuery(getSQL());
      System.out.println(data);
      if(getErrCode() < 0)
          return -1;
      return rowCount();
  }

  /**
   * 得到错误编号
   * @return
   */
  public int getErrCode()
  {
    return data.getErrCode();
  }

  /**
   * 得到错误文本信息
   * @return
   */
  public String getErrText()
  {
    return data.getErrText();
  }

  /**
   * 得到XML对象
   * @return
   */
  public INode getXML()
  {
    return xmlElement;
  }

  /**
   * 得到SQL语句
   * @return
   */
  public String getSQL()
  {
    return getXML().getChildElement("tables").getChildElement("sql").getValue();
  }

  /**
   * 得到全部列名用逗号间隔
   * @return
   */
  public String getColumnsString()
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < columns.size(); i++)
    {
      if (sb.length() > 0)
        sb.append(",");
      sb.append(columns.get(i));
    }
    return sb.toString();
  }

  /**
   * 得到列名
   * @param i 列号
   * @return 列名
   */
  public String getColumn(int i)
  {
    if (i >= columns.size())
      return "";
    return (String) columns.get(i);
  }

  /**
   * 得到总行数
   * @return 总行数,错误返回-1
   */
  public int rowCount()
  {
    if (data == null)
      return -1;
    return data.getCount();
  }

  public int columnCount()
  {
    return columns.size();
  }
  /**
   * 得到具体数据
   * @param row 行号
   * @param column 列号
   * @return String 数据
   */
  public String getItemString(int row, int column)
  {
    return getItemString(row, getColumn(column));
  }

  /**
   * 得到具体数据
   * @param row 行号
   * @param column 列名
   * @return String 数据
   */
  public String getItemString(int row, String column)
  {
    if (data == null)
      return "";
      return data.getValue(column,row);
  }
  public Object getItemData(int row,String column)
  {
    if (data == null)
      return null;
    Vector cData = getItemData(column);
    if(cData == null)
      return null;
    if(row >= cData.size())
      return null;
    return cData.get(row);
  }
  public Object getItemData(int row,int column)
  {
    return getItemData(row,getColumn(column));
  }
  /**
   * 得到具体数据
   * @param column int 列号
   * @return String 数据
   */
  public String getItemString(int column)
  {
    return getItemString(getColumn(column));
  }

  /**
   * 得到具体数据
   * @param column String 列名
   * @return String 数据
   */
  public String getItemString(String column)
  {
    if (data == null)
      return "";
      return data.getValue(column);
  }

  /**
   * 初始化全部类名
   */
  private void initColumns()
  {
    columns = new ArrayList();
    StringBuffer sb = new StringBuffer();
    Iterator iterator = getXML().getChildElement("tables").getChildElements();
    while (iterator.hasNext())
    {
      INode element = (INode) iterator.next();
      if (element.getName().equals("column"))
      {
        columns.add(element.getAttributeValue("name"));
      }
    }
  }
  /**
   * 得到数据列的 Vector
   * @param column String 列名
   * @return Vector
   */
  public Vector getItemData(String column)
  {
    if(getData() == null)
      setData(new TParm());
    Vector cData = (Vector)data.getData(column);
    if (cData == null)
    {
      cData = new Vector();
      getData().setData(column,cData);
    }
    return cData;
  }
  /**
   * 得到数据列的 Vector
   * @param column int 列号
   * @return Vector
   */
  public Vector getItemData(int column)
  {
    return getItemData(getColumn(column));
  }

  /**
   * 设置数据
   * @param row int 行号
   * @param column String 列名
   * @param object Object 数据
   * @return boolean true 成功 false 失败
   */
  public boolean setItem(int row,String column,Object object)
  {
    if (data == null)
      return false;
    if(row >= rowCount())
      return false;
    Vector cData = getItemData(column);
    if(cData == null)
      return false;
    cData.set(row,object);
    return true;
  }

  /**
   * 设置数据
   * @param row int 行号
   * @param column int 列号
   * @param object Object 数据
   * @return boolean true 成功 false 失败
   */
  public boolean setItem(int row,int column,Object object)
  {
    return setItem(row,getColumn(column),object);
  }
  /**
   * 插入一行数据(0 追加)
   * @param row int
   */
  public int insertRow(int row)
  {
    if(columnCount() == 0)
      return -1;
    for(int i = 0;i < columnCount();i++)
    {
      Vector cData = getItemData(i);
      if(row == 0)
        cData.add("");
      else
        cData.insertElementAt("",i);
    }
    if(row == 0)
      return getItemData(0).size() - 1;
    return row;
  }
  public void initTestData()
  {
    if(!isTestData)
      return;
    int count = (int)(Math.random() * 10.0);
    for(int i = 0;i < 10;i++)
    {
      int row = insertRow(0);
      for(int j = 0;j < columnCount();j++)
        setItem(row,j,row + ":" + getColumn(j));
    }
  }
}
