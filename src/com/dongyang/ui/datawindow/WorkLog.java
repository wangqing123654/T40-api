package com.dongyang.ui.datawindow;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;

/**
 *
 * <p>Title: 操作日志控制设备</p>
 * <p>Description: 主要记录用户操作的每一步细节,支持回滚和前滚</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.27
 * @version 1.0
 */
public class WorkLog
{
  //被控对象,工具底板
  BackPanel backPanel;
  //日志数据存储列表对象
  private List loglist = new ArrayList();

  //当前回滚位置
  private int logPoint = 0;

  /**
   * 构造器
   * @param backPanel
   */
  public WorkLog(BackPanel backPanel)
  {
    this.backPanel = backPanel;
  }

  /**
   * 日志数据保存入口方法,需要保存的日志全部提交到本方法统一处理
   * @param name 动作名称
   * @param type 类型及参数
   */
  public void saveLog(String name, String type)
  {
    //操作区域尺寸改变记录
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
    //选中组件
    else if (name.equals("selected"))
    {
      add(name + "," + type);
    }
    //移动组件
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
    //------测试临时程序-----//
    /*System.out.println("-----------------------");
         for(int i = 0;i < loglist.size();i++)
      System.out.println(loglist.get(i));*/
  }

  /**
   * 清空记录
   */
  public void reset()
  {
    loglist = new ArrayList();
    logPoint = 0;
  }

  /**
   * 将信息写入日志
   * @param text
   */
  private void add(String text)
  {
    settle();
    loglist.add(text);
    logPoint = loglist.size();
  }

  /**
   * 整理日志记录,当写入新记录时删除全部过期记录
   */
  public void settle()
  {
    if (loglist.size() == 0)
      return;
    for (int i = loglist.size() - 1; i >= logPoint; i--)
      loglist.remove(i);
  }

  /**
   * 回滚动作
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
   * 前滚动作
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
   * 动作回放执行
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
    //操作区域记录执行
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
    //选中记录执行
    else if (name.equals("selected"))
    {
      if (state)
        type = stk.nextToken();
      getSelectControl().setSelects(type);
    }
    //移动记录执行
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
   * 得到日志记录
   * @param name 动作名称
   * @param index 数据位置
   * @return 数据
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
   * 得到当前位置的记录数据
   * @return 本操作的全部数据
   */
  private String getLog()
  {
    if (logPoint < 0 || logPoint >= loglist.size())
      return "";
    return (String) loglist.get(logPoint);
  }

  /**
   * 得到xml数据对象
   * @return xml数据对象
   */
  public INode getConfig()
  {
    return backPanel.getConfig();
  }

  /**
   * 得到xml子数据对象
   * @param name 名称
   * @return xml数据对象
   */
  public INode getConfigElement(String name)
  {
    return backPanel.getConfigElement(name);
  }

  /**
   * 得到xml控制器对象
   * @return xml控制器对象
   */
  public ConfigDataWindowFile getConfigManager()
  {
    return backPanel.getConfigManager();
  }

  /**
   * 得到选中控制器对象
   * @return 选中控制器对象
   */
  public SelectControl getSelectControl()
  {
    return backPanel.getSelectControl();
  }

}
