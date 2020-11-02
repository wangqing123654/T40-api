package com.dongyang.ui.datawindow;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;

/**
 *
 * <p>Title: ������־�����豸</p>
 * <p>Description: ��Ҫ��¼�û�������ÿһ��ϸ��,֧�ֻع���ǰ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.27
 * @version 1.0
 */
public class WorkLog
{
  //���ض���,���ߵװ�
  BackPanel backPanel;
  //��־���ݴ洢�б����
  private List loglist = new ArrayList();

  //��ǰ�ع�λ��
  private int logPoint = 0;

  /**
   * ������
   * @param backPanel
   */
  public WorkLog(BackPanel backPanel)
  {
    this.backPanel = backPanel;
  }

  /**
   * ��־���ݱ�����ڷ���,��Ҫ�������־ȫ���ύ��������ͳһ����
   * @param name ��������
   * @param type ���ͼ�����
   */
  public void saveLog(String name, String type)
  {
    //��������ߴ�ı��¼
    if (name.equals("Header") || name.equals("Detail") || name.equals("Summary") ||
        name.equals("Footer"))
    {
      if (type.equals("height"))
      {
        add(name + "," + type + "," +
            getConfigElement(name).getAttributeValue(type) + "," +
            backPanel.getArea(name).getHeight());
        getConfigElement(name).setAttributeValue(type,
                                                 backPanel.getArea(name).
                                                 getHeight());
        return;
      }
      return;
    }
    //ѡ�����
    else if (name.equals("selected"))
    {
      add(name + "," + type);
    }
    //�ƶ����
    else if (name.equals("move"))
    {
      add(name + "," + type);
    }
    else
    {
      DComponent component = backPanel.getObjectForID(name);
      if (component == null)
        return;
      INode componentXML = backPanel.getConfigElement(component.getType() + "s");
      if (componentXML == null)
      {
        componentXML = new TNode(component.getType() + "s");
        backPanel.getConfig().addChildElement(componentXML);
      }
      String log = component.saveWorkLog(type, componentXML);
      if (log.length() > 0)
        add(log);

    }
    backPanel.callEventListener("UNDO_STATE", new Boolean(true));
    backPanel.callEventListener("REDO_STATE", new Boolean(false));
    //------������ʱ����-----//
    /*System.out.println("-----------------------");
         for(int i = 0;i < loglist.size();i++)
      System.out.println(loglist.get(i));*/
  }

  /**
   * ��ռ�¼
   */
  public void reset()
  {
    loglist = new ArrayList();
    logPoint = 0;
  }

  /**
   * ����Ϣд����־
   * @param text
   */
  private void add(String text)
  {
    settle();
    loglist.add(text);
    logPoint = loglist.size();
  }

  /**
   * ������־��¼,��д���¼�¼ʱɾ��ȫ�����ڼ�¼
   */
  public void settle()
  {
    if (loglist.size() == 0)
      return;
    for (int i = loglist.size() - 1; i >= logPoint; i--)
      loglist.remove(i);
  }

  /**
   * �ع�����
   */
  public void undo()
  {
    if (logPoint < 1)
      return;
    logPoint--;
    work(false);
    backPanel.callEventListener("REDO_STATE", new Boolean(true));
    if (logPoint < 1)
      backPanel.callEventListener("UNDO_STATE", new Boolean(false));
  }

  /**
   * ǰ������
   */
  public void redo()
  {
    if (loglist.size() < 0 || logPoint >= loglist.size())
      return;
    work(true);
    logPoint++;
    backPanel.callEventListener("UNDO_STATE", new Boolean(true));
    if (logPoint == loglist.size())
      backPanel.callEventListener("REDO_STATE", new Boolean(false));
  }

  /**
   * �����ط�ִ��
   * @param state
   */
  private void work(boolean state)
  {
    String s = getLog();
    if (s.length() == 0)
      return;
    StringTokenizer stk = new StringTokenizer(s, ",");
    String name = stk.nextToken();
    String type = stk.nextToken();
    //���������¼ִ��
    if (name.equals("Header") || name.equals("Detail") || name.equals("Summary") ||
        name.equals("Footer"))
    {
      if (type.equals("height"))
      {
        if (state)
          stk.nextToken();
        backPanel.resizeArea(Integer.parseInt(stk.nextToken()), name);
        getConfigElement(name).setAttributeValue(type,
                                                 backPanel.getArea(name).
                                                 getHeight());
      }
    }
    //ѡ�м�¼ִ��
    else if (name.equals("selected"))
    {
      if (state)
        type = stk.nextToken();
      getSelectControl().setSelects(type);
    }
    //�ƶ���¼ִ��
    else if (name.equals("move"))
    {
      StringTokenizer list = new StringTokenizer(type, " ");
      while (list.hasMoreTokens())
      {
        String value = list.nextToken();
        StringTokenizer names = new StringTokenizer(value, ";");
        Object obj = backPanel.getObjectForID(names.nextToken());
        if (obj instanceof DText)
          ( (DText) obj).loadMove(value, state);
        if (obj instanceof DLine)
          ( (DLine) obj).loadMove(value, state);
      }
    }
    else
    {
      int id = 0;
      String classType = "";
      int index = name.indexOf(":");
      if (index > 0)
      {
        id = Integer.parseInt(name.substring(index + 1, name.length()));
        classType = name.substring(0, index);
      }
      if (type.equals("Create") && state ||
          type.equals("Delete") && !state)
      {
        getSelectControl().removeSelects();
        DComponent component = newComponent(classType);
        component.setID(id);
        component.loadString(stk.nextToken());
        backPanel.getArea(component.getArea()).add((JComponent)component, 0);
        getSelectControl().addSelect((IMoveable)component);
        backPanel.getConfigElement(component.getType() + "s").addChildElement(component.createXML());
      }
      else if (type.equals("Create") && !state ||
               type.equals("Delete") && state)
      {
        DComponent component = backPanel.getObjectForID(name);
        if (component == null)
          return;
        getSelectControl().removeSelects();
        getSelectControl().addSelect((IMoveable)component);
        getSelectControl().delete();
        backPanel.getConfigElement(component.getType() + "s").removeChildElement(component.getXML());
      }else
      {
        DComponent component = backPanel.getObjectForID(name);
        if (state)
          stk.nextToken();
        component.loadWorkLog(type, stk.nextToken());
      }
    }
  }
  public DComponent newComponent(String type)
  {
    if(type.equals("text"))
      return new DText();
    if(type.equals("column"))
      return new DColumn();
    if(type.equals("picture"))
      return new DPicture();
    //if(type.equals("childdatawindow"))
    //  return new DChildDataWindow();
    if(type.equals("line"))
      return new DLine();
    return null;
  }
  /**
   * �õ���־��¼
   * @param name ��������
   * @param index ����λ��
   * @return ����
   */
  public String getLog(String name, int index)
  {
    for (int i = logPoint; i >= 0; i--)
    {
      if (i < loglist.size())
      {
        StringTokenizer stk = new StringTokenizer( (String) loglist.get(i), ",");
        if (name.equals(stk.nextToken()))
          for (int j = 1; j < index && stk.hasMoreTokens(); j++)
            stk.nextToken();
        return stk.nextToken();
      }
    }
    return "";
  }

  /**
   * �õ���ǰλ�õļ�¼����
   * @return ��������ȫ������
   */
  private String getLog()
  {
    if (logPoint < 0 || logPoint >= loglist.size())
      return "";
    return (String) loglist.get(logPoint);
  }

  /**
   * �õ�xml���ݶ���
   * @return xml���ݶ���
   */
  public INode getConfig()
  {
    return backPanel.getConfig();
  }

  /**
   * �õ�xml�����ݶ���
   * @param name ����
   * @return xml���ݶ���
   */
  public INode getConfigElement(String name)
  {
    return backPanel.getConfigElement(name);
  }

  /**
   * �õ�xml����������
   * @return xml����������
   */
  public ConfigDataWindowFile getConfigManager()
  {
    return backPanel.getConfigManager();
  }

  /**
   * �õ�ѡ�п���������
   * @return ѡ�п���������
   */
  public SelectControl getSelectControl()
  {
    return backPanel.getSelectControl();
  }

}
