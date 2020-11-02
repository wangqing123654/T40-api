package com.dongyang.ui.datawindow;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.*;
import java.io.*;
import javax.xml.parsers.*;
import java.util.*;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;
import com.dongyang.data.TParm;
import com.dongyang.manager.TIOM_AppServer;

/**
 *
 * <p>Title: DataWindow xml�������ݿ�����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ŀ��</p>
 * @author lzk 2006.07.27
 * @version 1.0
 */
public class ConfigDataWindowFile
{
  /**
   * ��ȡ XML
   * @param fileName �ļ�����
   * @return
   */
  public INode read(String fileName)
  {
      byte[] data = TIOM_AppServer.readFile(fileName);
      if (data == null)
          return null;
      INode element = TNode.loadXML(data);
      return element;
  }
  public INode readData(String data)
  {
    InputSource inputsource = new InputSource(new StringReader(data));
    INode element = new TNode(loadXML(inputsource), null);
    return element;
  }
  /**
   * �� xml ת��Ϊ String ����
   * @param element
   * @return
   */
  public String getXMLData(INode element)
  {
    StringBuffer data = new StringBuffer();
    data.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
    data.append("<!-- \n");
    data.append("  TDataWindow\n");
    data.append("    JavaHis\n");
    data.append("  modify " + new Date() + "\n");
    data.append("-->\n");
    data.append(getXMLData(element, 0));
    return data.toString();
  }

  /**
   * �� xml ת��Ϊ String ����(�ڲ�ʹ��)
   * @param element
   * @param i �ݽ�����
   * @return
   */
  private String getXMLData(INode element, int i)
  {
    String blank = getBlank(i);
    StringBuffer data = new StringBuffer();
    data.append(blank + "<" + element.getName());
    Iterator iterator = element.getAttributeNames();
    while (iterator.hasNext())
    {
      String name = (String) iterator.next();
      data.append(" " + name + "=\"" + element.getAttributeValue(name) + "\"");
    }
    StringBuffer childData = new StringBuffer();
    iterator = element.getChildElements();
    while (iterator.hasNext())
      childData.append(getXMLData( (INode) iterator.next(), i + 1));
    if (childData.length() == 0)
    {
      if (element.getValue().length() > 0)
        data.append(">" + element.getValue() + "</" + element.getName());
      else
        data.append("/");
    }
    else
      data.append(">\n" + childData + blank + "</" + element.getName());
    data.append(">\n");
    return data.toString();
  }

  /**
   * ���ո�
   * @param count ����
   * @return String
   */
  private String getBlank(int count)
  {
    StringBuffer sb = new StringBuffer(count);
    for (int i = 0; i < count; i++)
      sb.append(" ");
    return sb.toString();
  }

  /**
   * �õ��������洢xml�ļ��б�
   * @param type �ļ���׺
   * @return ActionParm ��������
   */
  public TParm openFileList(String type)
  {
    return openFileList(type, "");
  }

  /**
   * �õ��������洢xml�ļ��б�
   * @param type �ļ���׺
   * @param dir ·��
   * @return ActionParm ��������
   */
  public TParm openFileList(String type, String dir)
  {
    /*TParm inParm = new TParm();
    inParm.setData("FILE_TYPE", type);
    inParm.setData("FILE_DIR", dir);
    DyAction actionObj = new DyAction("action.dwt.DataWindowAction");
    inParm = (TParm) actionObj.callMethod("openFileList", inParm);
    return inParm;
    */
   return null;
  }

  /**
   * ��ȡXML�ļ�
   * @param inputsource ������
   * @return Element
   */
  private Element loadXML(InputSource inputsource)
  {
    Element element = null;
    try
    {
      DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.
          newInstance();
      documentbuilderfactory.setNamespaceAware(true);
      DocumentBuilder documentbuilder = documentbuilderfactory.
          newDocumentBuilder();
      Document document = documentbuilder.parse(inputsource);
      element = document.getDocumentElement();
    }
    catch (Exception e)
    {
    }
    return element;
  }

  /**
   * ����XML�ļ�
   * @param fileName �ļ���
   * @param element xml ����
   * @return 0 �ɹ�
   */
  public int saveXML(String fileName, INode element)
  {
    return saveXML(fileName, getXMLData(element));
  }

  /**
   * ����XML�ļ�
   * @param fileName �ļ���
   * @param data xml �ַ�������
   * @return 0 �ɹ�
   */
  public int saveXML(String fileName, String data)
  {
      if(!TIOM_AppServer.writeFile(fileName,data.getBytes()))
          return -1;
      return 0;
  }

  /**
   * ����SQL������� DataWindow xml �﷨
   * @param sql
   * @return
   */
  public INode create(String sql)
  {
    INode dataWindowElement = new TNode("datawindow");
    //�Ӵ�ӡ����
    INode print = new TNode("Print");
    print.setAttributeValue("Width", 595.275590551181);
    print.setAttributeValue("Height", 841.8897637795276);
    print.setAttributeValue("ImageableX", 72.0);
    print.setAttributeValue("ImageableY", 72.0);
    print.setAttributeValue("ImageableWidth", 451.2755905511811);
    print.setAttributeValue("ImageableHeight", 697.8897637795276);
    print.setAttributeValue("Orientation", 1);
    print.setAttributeValue("PrintZoom", 100.0);
    print.setAttributeValue("PriviewZoom", 100.0);
    dataWindowElement.addChildElement(print);
    //����ͷ��
    INode header = new TNode("Header");
    header.setAttributeValue("height", 40);
    header.setAttributeValue("color", 536870912);
    dataWindowElement.addChildElement(header);
    //��������
    INode detail = new TNode("Detail");
    detail.setAttributeValue("height", 40);
    detail.setAttributeValue("color", 536870912);
    dataWindowElement.addChildElement(detail);
    //�Ӽ�����
    INode summary = new TNode("Summary");
    summary.setAttributeValue("height", 0);
    summary.setAttributeValue("color", 536870912);
    dataWindowElement.addChildElement(summary);
    //�ӱ����
    INode footer = new TNode("Footer");
    footer.setAttributeValue("height", 0);
    footer.setAttributeValue("color", 536870912);
    dataWindowElement.addChildElement(footer);
    //��Table
    INode table = new TNode("tables");
    if (sql != null && sql.length() > 0)
    {
      //�� Table �� Column
      addTableColumn(sql, table);
      //�� Table ����
      addTableName(sql, table);
    }
    //��SQL
    INode sqlElement = new TNode("sql");
    sqlElement.setValue(sql);
    table.addChildElement(sqlElement);
    dataWindowElement.addChildElement(table);
    //�� Text
    INode text = new TNode("texts");
    addText(table, text);
    dataWindowElement.addChildElement(text);
    //�� Column
    INode column = new TNode("columns");
    addColumn(table, column);
    dataWindowElement.addChildElement(column);
    //�� Line
    INode line = new TNode("lines");
    dataWindowElement.addChildElement(line);
    return dataWindowElement;
  }
  /**
   * ���� Table Column �﷨
   * @param sql
   * @param parent
   */
  private void addColumn(String sql,INode parent)
  {
    StringTokenizer stk = new StringTokenizer(sql, ",");
    while (stk.hasMoreTokens())
    {
      String column = stk.nextToken().trim();
      INode columnElement = new TNode("column");
      columnElement.setAttributeValue("dbname", column);
      int index = column.indexOf("AS");
      if (index > 0)
        column = column.substring(index + 2, column.length()).trim();
      index = column.indexOf(".");
      if (index > 0)
        column = column.substring(index + 1, column.length()).trim();
      columnElement.setAttributeValue("name", column);
      parent.addChildElement(columnElement);
    }
  }
  /**
   * ���� Table Column �﷨
   * @param sql
   * @param parent
   */
  private void addTableColumn(String sql, INode parent)
  {
    sql = sql.toUpperCase();
    sql = sql.replace('\n', ' ');
    sql = sql.replace('\r', ' ').trim();
    if(sql.startsWith("COLUMN:"))
    {
      sql = sql.substring(7, sql.length()).trim();
      addColumn(sql,parent);
      return;
    }
    if (sql.startsWith("SELECT"))
      sql = sql.substring(6, sql.length()).trim();
    if (sql.startsWith("DISTINCT"))
      sql = sql.substring(8, sql.length()).trim();
    int index = sql.indexOf("FROM");
    if (index <= 0)
      return;
    sql = sql.substring(0, index).trim();
    StringTokenizer stk = new StringTokenizer(sql, ",");
    while (stk.hasMoreTokens())
    {
      String column = stk.nextToken().trim();
      INode columnElement = new TNode("column");
      columnElement.setAttributeValue("dbname", column);
      index = column.indexOf("AS");
      if (index > 0)
        column = column.substring(index + 2, column.length()).trim();
      index = column.indexOf(".");
      if (index > 0)
        column = column.substring(index + 1, column.length()).trim();
      columnElement.setAttributeValue("name", column);
      parent.addChildElement(columnElement);
    }
  }

  /**
   * ���� Table �﷨
   * @param sql
   * @param parent
   */
  private void addTableName(String sql, INode parent)
  {
    sql = sql.toUpperCase();
    sql = sql.replace('\n', ' ');
    sql = sql.replace('\r', ' ').trim();
    int index = sql.indexOf("FROM");
    if (index <= 0)
      return;
    sql = sql.substring(index + 4, sql.length()).trim();
    index = sql.indexOf("WHERE");
    if (index > 0)
      sql = sql.substring(0, index).trim();
    StringTokenizer stk = new StringTokenizer(sql, ",");
    while (stk.hasMoreTokens())
    {
      String table = stk.nextToken().trim();
      INode columnElement = new TNode("table");
      columnElement.setAttributeValue("name", table);
      parent.addChildElement(columnElement);
    }
  }

  /**
   * ���� Text �﷨
   * @param table
   * @param parent
   */
  private void addText(INode table, INode parent)
  {
    int width = 100;
    int index = 0;
    Iterator iterator = table.getChildElements();
    while (iterator.hasNext())
    {
      INode element = (INode) iterator.next();
      if (!element.getName().equals("column"))
        continue;
      INode textElement = new TNode("text");
      textElement.setAttributeValue("name",
                                    element.getAttributeValue("name") + "_t");
      textElement.setAttributeValue("band", "Header");
      textElement.setAttributeValue("text", element.getAttributeValue("name"));
      textElement.setAttributeValue("alignment", 2);
      textElement.setAttributeValue("border", IBorderStyle.BORDER_NO);
      textElement.setAttributeValue("x", index * width);
      textElement.setAttributeValue("y", 8);
      textElement.setAttributeValue("width", width);
      textElement.setAttributeValue("height", 30);
      parent.addChildElement(textElement);
      index++;
    }
  }

  /**
   * ���� Column �﷨
   * @param table
   * @param parent
   */
  private void addColumn(INode table, INode parent)
  {
    int width = 100;
    int index = 0;
    Iterator iterator = table.getChildElements();
    while (iterator.hasNext())
    {
      INode element = (INode) iterator.next();
      if (!element.getName().equals("column"))
        continue;
      INode columnElement = new TNode("column");
      columnElement.setAttributeValue("id", index + 1);
      columnElement.setAttributeValue("name", element.getAttributeValue("name"));
      columnElement.setAttributeValue("band", "Detail");
      columnElement.setAttributeValue("alignment", 2);
      columnElement.setAttributeValue("border", IBorderStyle.BORDER_NO);
      columnElement.setAttributeValue("x", index * width);
      columnElement.setAttributeValue("y", 8);
      columnElement.setAttributeValue("width", width);
      columnElement.setAttributeValue("height", 30);
      parent.addChildElement(columnElement);
      index++;
    }
  }
}
