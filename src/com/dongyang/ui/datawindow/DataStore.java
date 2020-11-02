package com.dongyang.ui.datawindow;


import java.util.*;
import com.dongyang.ui.base.TDataWindowBase;
import com.dongyang.data.TParm;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.config.INode;

/**
 *
 * <p>Title: ���ݴ洢�豸,֧��DataWindow�ĺ�̨���ݹ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.08.8
 * @version 1.0
 */
public class DataStore
{
  public boolean isTestData = false;
  //XML����
  private INode xmlElement;

  //DataWindow�豸
  private TDataWindowBase datawindow;

  //�����б�
  private List columns;

  //����
  private TParm data;

  /**
   * ������
   */
  public DataStore()
  {

  }

  /**
   * ������
   * @param datawindow ���DataWindow����
   */
  public DataStore(TDataWindowBase datawindow)
  {
    this.datawindow = datawindow;
  }

  /**
   * �õ�ȫ������
   * @return TParm
   */
  public TParm getData()
  {
    return data;
  }

  /**
   * ����ȫ������
   * @param data TParm
   */
  public void setData(TParm data)
  {
    this.data = data;
  }

  /**
   * ��ȡxml��Ϣ
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
    //�����������
    initTestData();
    return true;
  }

  /**
   * ���<δ���>
   */
  public void reset()
  {

  }

  /**
   * ��Ӧ�÷�������ȡȫ������
   * @return ��������
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
   * �õ�������
   * @return
   */
  public int getErrCode()
  {
    return data.getErrCode();
  }

  /**
   * �õ������ı���Ϣ
   * @return
   */
  public String getErrText()
  {
    return data.getErrText();
  }

  /**
   * �õ�XML����
   * @return
   */
  public INode getXML()
  {
    return xmlElement;
  }

  /**
   * �õ�SQL���
   * @return
   */
  public String getSQL()
  {
    return getXML().getChildElement("tables").getChildElement("sql").getValue();
  }

  /**
   * �õ�ȫ�������ö��ż��
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
   * �õ�����
   * @param i �к�
   * @return ����
   */
  public String getColumn(int i)
  {
    if (i >= columns.size())
      return "";
    return (String) columns.get(i);
  }

  /**
   * �õ�������
   * @return ������,���󷵻�-1
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
   * �õ���������
   * @param row �к�
   * @param column �к�
   * @return String ����
   */
  public String getItemString(int row, int column)
  {
    return getItemString(row, getColumn(column));
  }

  /**
   * �õ���������
   * @param row �к�
   * @param column ����
   * @return String ����
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
   * �õ���������
   * @param column int �к�
   * @return String ����
   */
  public String getItemString(int column)
  {
    return getItemString(getColumn(column));
  }

  /**
   * �õ���������
   * @param column String ����
   * @return String ����
   */
  public String getItemString(String column)
  {
    if (data == null)
      return "";
      return data.getValue(column);
  }

  /**
   * ��ʼ��ȫ������
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
   * �õ������е� Vector
   * @param column String ����
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
   * �õ������е� Vector
   * @param column int �к�
   * @return Vector
   */
  public Vector getItemData(int column)
  {
    return getItemData(getColumn(column));
  }

  /**
   * ��������
   * @param row int �к�
   * @param column String ����
   * @param object Object ����
   * @return boolean true �ɹ� false ʧ��
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
   * ��������
   * @param row int �к�
   * @param column int �к�
   * @param object Object ����
   * @return boolean true �ɹ� false ʧ��
   */
  public boolean setItem(int row,int column,Object object)
  {
    return setItem(row,getColumn(column),object);
  }
  /**
   * ����һ������(0 ׷��)
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
