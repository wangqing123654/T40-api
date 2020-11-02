package com.dongyang.ui.datawindow;

import java.awt.Point;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * <p>Title: 选中控制</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class SelectControl
    implements KeyListener
{
  //拷贝对象
  private static List copyList = new ArrayList();
  //工具底板
  private BackPanel parent;
  //选中列表
  private List selectList = new ArrayList();
  //选中
  private DText selectBox = new DText();
  //是否开始扩选
  private boolean selectBoxStart;
  //扩选的顶点
  private int selectX0, selectY0;
  //扩选类型 1接触选 2包含选
  private int selectType = 1;
  /**
   * 构造器
   * @param parent 工具底板
   */
  public SelectControl(BackPanel parent)
  {
    this.parent = parent;
  }

  /**
   * 增加选中组件
   * @param obj 选中组件
   */
  public void addSelect(IMoveable obj)
  {
    if (obj instanceof DText)
      selectText( (DText) obj);
    else if (obj instanceof DLine)
      selectLine( (DLine) obj);
    selectList.add(obj);
    parent.grabFocus();
    parent.callEventListener("SELECT_OBJECT", obj);
  }

  public List getSelectObjects()
  {
    return selectList;
  }

  public int getSelectCount()
  {
    return selectList.size();
  }

  /**
   * 取消全部选中
   */
  public void removeSelects()
  {
    for (int index = selectList.size() - 1; index >= 0; index--)
      removeSelect( (IMoveable) selectList.get(index));
    parent.callEventListener("SELECT_OBJECT", null);
    parent.repaint();
  }

  /**
   * 得到全部选中对象列表
   * @return 用分号间隔的对象名称列表，对象名称由"类型:编号"组成
   */
  public String getSelects()
  {
    StringBuffer sb = new StringBuffer();
    for (int index = 0; index < selectList.size(); index++)
    {
      if (sb.length() > 0)
        sb.append(";");
      sb.append( ( (IMoveable) selectList.get(index)).getType() + ":" +
                ( (IMoveable) selectList.get(index)).getID());
    }
    if (sb.length() == 0)
      return "null";
    return sb.toString();
  }

  /**
   * 设置选中对象
   * @param s 用分号间隔的对象名称列表，对象名称由"类型:编号"组成
   */
  public void setSelects(String s)
  {
    removeSelects();
    if (s.equals("null"))
      return;
    StringTokenizer stk = new StringTokenizer(s, ";");
    while (stk.hasMoreTokens())
    {
      String value = stk.nextToken();
      IMoveable object = (IMoveable) parent.getObjectForID(value);
      addSelect(object);
    }
  }

  /**
   * 取消选中
   * @param obj 选中组件
   */
  public void removeSelect(IMoveable obj)
  {
    if (obj instanceof DText)
      removeSelectText( (DText) obj);
    else if (obj instanceof DLine)
      removeSelectLine( (DLine) obj);
    selectList.remove(obj);

  }

  /**
   * 是否被选中
   * @param obj 组件
   * @return true 选中 false 未选中
   */
  public boolean existSelect(IMoveable obj)
  {
    for (int index = 0; index < selectList.size(); index++)
    {
      if (obj == selectList.get(index))
        return true;
    }
    return false;
  }

  /**
   * 是否仅移动,如果同时选中一个以上的组件,只能改变位置,不能缩放尺寸
   * @return true 仅移动 false 可以缩放
   */
  public boolean onlyMove()
  {
    return selectList.size() > 1;
  }

  /**
   * 组件点选
   * @param obj 被点组件
   * @param isControlDown 是否按下Ctrl键
   * @return true 可以继续移动,false 结束
   */
  public boolean mousePressed(IMoveable obj, boolean isControlDown)
  {
    parent.grabFocus();
    String oldSelect = getSelects();
    if (!isControlDown)
    {
      removeSelects();
      addSelect(obj);
    }
    else
    {
      //点击第二下取消选中
      if (existSelect(obj))
      {
        removeSelect(obj);
        parent.repaint();
        parent.getWorkLog().saveLog("selected", oldSelect + "," + getSelects());
        return false;
      }
      addSelect(obj);
    }
    parent.getWorkLog().saveLog("selected", oldSelect + "," + getSelects());
    return true;
  }

  /**
   * 鼠标拖动组件,如果当前是多选则带动全部选中组件一起移动
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void mouseDragged(int x, int y)
  {
    mouseDragged(x, y, false);
  }

  /**
   * 鼠标拖动，带动全部组件一起移动
   * @param x 新位置x矢量
   * @param y 新位置y矢量
   * @param b true 保存日志 false 不保存日志
   */
  public void mouseDragged(int x, int y, boolean b)
  {
    for (int index = 0; index < selectList.size(); index++)
      if (selectList.get(index)instanceof DText)
        moveText( (DText) selectList.get(index), x, y);
      else if (selectList.get(index)instanceof DLine)
        moveLine( (DLine) selectList.get(index), x, y);
    if (b && selectList.size() > 0)
      saveMove();
  }

  /**
   * 键盘缩放组件,如果当前是多选则带动全部选中组件一起缩放
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void sizeDragged(int x, int y)
  {
    for (int index = 0; index < selectList.size(); index++)
      if (selectList.get(index)instanceof DText)
        sizeText( (DText) selectList.get(index), x, y);
      else if (selectList.get(index)instanceof DLine)
        sizeLine( (DLine) selectList.get(index), x, y);
    if (selectList.size() > 0)
      saveMove();
  }

  /**
   * 当全部组件移动完毕保存移动痕迹,修改xml
   */
  public void saveMove()
  {
    if(selectList.size() == 0)
      return;
    StringBuffer sb = new StringBuffer();
    for (int index = 0; index < selectList.size(); index++)
    {
      if (selectList.get(index)instanceof DText)
      {
        DText text = (DText) selectList.get(index);
        if (sb.length() > 0)
          sb.append(" ");
        sb.append(text.saveMove());
      }
      else if (selectList.get(index)instanceof DLine)
      {
        DLine line = (DLine) selectList.get(index);
        if (sb.length() > 0)
          sb.append(" ");
        sb.append(line.saveMove());
      }
    }
    if(sb.length() > 0)
      parent.getWorkLog().saveLog("move", sb.toString());
  }

  /**
   * 单独移动Text组件
   * @param text 组件对象
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void moveText(DText text, int x, int y)
  {
    int moveX = text.getX() + x;
    int moveY = text.getY() + y;
    //检测是否横向越界
    if (x > 0)
      parent.checkPreferredWidth(moveX + text.getWidth());
      //检测是否跨越区域
    if (y != 0)
    {
      int y0 = text.getParent().getY() + text.getY();
      AreaPanel panel = parent.checkPanel(y0 + y, text.getHeight());
      if (panel != null && panel != text.getParent())
      {
        moveY = y0 + y - panel.getY();
        text.getParent().remove(text);
        text.setArea(panel.getArea());
        panel.add(text, 0);
      }
    }
    text.getMoveable().move(moveX, moveY);
  }

  /**
   * 单独移动Line组件
   * @param line 组件对象
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void moveLine(DLine line, int x, int y)
  {
    int moveX = line.getX() + x;
    int moveY = line.getY() + y;
    //检测是否横向越界
    if (x > 0)
      parent.checkPreferredWidth(moveX + line.getWidth());
      //检测是否跨越区域
    if (y != 0)
    {
      int y0 = line.getParent().getY() + line.getY();
      AreaPanel panel = parent.checkPanel(y0 + y, line.getHeight());
      if (panel != null && panel != line.getParent())
      {
        moveY = y0 + y - panel.getY();
        line.getParent().remove(line);
        line.setArea(panel.getArea());
        panel.add(line, 0);
      }
    }
    line.getMoveable().move(moveX, moveY);
  }

  /**
   * 单独缩放Text组件
   * @param text 组件对象
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void sizeText(DText text, int x, int y)
  {
    if (x > 0)
      parent.checkPreferredWidth(text.getX() + text.getWidth() + x);
    text.getMoveable().resize(x, y);
  }

  /**
   * 单独缩放Line组件
   * @param line 组件对象
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void sizeLine(DLine line, int x, int y)
  {
    if (x > 0)
      parent.checkPreferredWidth(line.getX() + line.getWidth() + x);
    line.getMoveable().resize(x, y);
  }

  /**
   * 取消Text组件选中
   * @param text 组件对象
   */
  public void removeSelectText(DText text)
  {
    text.setMoveable(false);
    text.setResizable(false);
    deletePont(text.getMoveable().getPoint(1));
    deletePont(text.getMoveable().getPoint(2));
    deletePont(text.getMoveable().getPoint(3));
    deletePont(text.getMoveable().getPoint(4));
    text.getMoveable().removePoint();
  }

  /**
   * 取消Line组件选中
   * @param line 组件对象
   */
  public void removeSelectLine(DLine line)
  {
    line.setMoveable(false);
    line.setResizable(false);
    deletePont(line.getMoveable().getPoint(1));
    deletePont(line.getMoveable().getPoint(2));
    line.getMoveable().removePoint();
  }

  /**
   * 选中Text组件
   * @param text 组件对象
   */
  public void selectText(DText text)
  {
    text.setMoveable(true);
    text.setResizable(true);
    text.getMoveable().setSelectControl(this);
    Point p = text.getParent().getLocation();
    DPoint p1 = createPoint(text.getX() + p.x, text.getY() + p.y, 1);
    DPoint p2 = createPoint(text.getX() + p.x + text.getWidth(),
                            text.getY() + p.y, 2);
    DPoint p3 = createPoint(text.getX() + p.x + text.getWidth(),
                            text.getY() + p.y + text.getHeight(), 3);
    DPoint p4 = createPoint(text.getX() + p.x,
                            text.getY() + p.y + text.getHeight(), 4);
    text.getMoveable().setPoint(p1, p2, p3, p4);
    parent.repaint();
  }

  /**
   * 选中Line组件
   * @param line 组件对象
   */
  public void selectLine(DLine line)
  {
    line.setMoveable(true);
    line.setResizable(true);
    line.getMoveable().setSelectControl(this);
    Point p = line.getParent().getLocation();
    DPoint p1 = null;
    DPoint p2 = null;
    if (line.isReversal())
    {
      p1 = createPoint(line.getX() + p.x + line.getWidth(),
                       line.getY() + p.y, 1);
      p2 = createPoint(line.getX() + p.x,
                       line.getY() + p.y + line.getHeight(), 2);
    }
    else
    {
      p1 = createPoint(line.getX() + p.x, line.getY() + p.y, 1);
      p2 = createPoint(line.getX() + p.x + line.getWidth(),
                       line.getY() + p.y + line.getHeight(), 2);
    }

    line.getMoveable().setPoint(p1, p2);
    parent.repaint();
  }

  /**
   * 删除四顶点标记的其中一点
   * @param point
   */
  private void deletePont(DPoint point)
  {
    if (point == null)
      return;
    parent.remove(point);
  }

  /**
   * 创建四顶点标记的其中一点
   * @param x 横坐标
   * @param y 纵坐标
   * @param pos 位置 1上左 2上右 3下右 4下左
   * @return 点对象
   */
  private DPoint createPoint(int x, int y, int pos)
  {
    switch (pos)
    {
      case 1:
        x -= 3;
        y -= 3;
        break;
      case 2:
        x -= 1;
        y -= 3;
        break;
      case 3:
        x -= 1;
        y -= 1;
        break;
      case 4:
        x -= 3;
        y -= 1;
    }
    DPoint point = new DPoint();
    point.setLocation(x, y);
    parent.add(point, 0);
    return point;
  }

  //---------------------------------------------------------------------//
  //   扩选程序段
  //---------------------------------------------------------------------//
  /**
   * 开始扩选
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void createSelectBox(int x, int y)
  {
    selectX0 = x;
    selectY0 = y;
    selectBoxStart = true;
    selectBox.setBounds(x, y, 1, 1);
    selectBox.setBorder(BorderFactory.createEtchedBorder());
    parent.add(selectBox, 0);
    parent.repaint();
    parent.grabFocus();
  }

  /**
   * 取消扩选
   */
  public void removeSelectBox()
  {
    if (!selectBoxStart)
      return;
    parent.remove(selectBox);
    parent.repaint();
    selectBoxStart = false;
    //检测扩选
    String oldSelect = parent.getWorkLog().getLog("selected", 2);
    parent.checkInclude(selectBox.getX(), selectBox.getY(),
                        selectBox.getX() + selectBox.getWidth(),
                        selectBox.getY() + selectBox.getHeight());
    String newSelect = getSelects();
    if (oldSelect.length() == 0)
    {
      oldSelect = "null";
      if (newSelect.equals("null"))
        return;
    }
    if(newSelect.equals(oldSelect))
      return;
    parent.getWorkLog().saveLog("selected", oldSelect + "," + newSelect);
  }

  /**
   * 扩选移动第二个点
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void moveSelectBox(int x, int y)
  {
    if (!selectBoxStart)
      return;
    int x1 = selectX0;
    int y1 = selectY0;
    if (x1 > x)
    {
      int temp = x1;
      x1 = x;
      x = temp;
    }
    if (y1 > y)
    {
      int temp = y1;
      y1 = y;
      y = temp;
    }
    selectBox.setBounds(x1, y1, x - x1, y - y1);
  }

  /**
   * 设置扩选类型
   * @param type int 1接触选 2包含选
   */
  public void setSelectType(int type)
  {
    this.selectType = type;
  }

  /**
   * 得到扩选类型
   * @return int 1接触选 2包含选
   */
  public int getSelectType()
  {
    return selectType;
  }

  /**
   * 键盘处理
   * @param e
   */
  public void keyPressed(KeyEvent e)
  {
    //点下Shift
    if (e.isShiftDown())
    {
      switch (e.getKeyCode())
      {
        case KeyEvent.VK_UP:
          sizeDragged(0, -1);
          return;
        case KeyEvent.VK_DOWN:
          sizeDragged(0, 1);
          return;
        case KeyEvent.VK_LEFT:
          sizeDragged( -1, 0);
          return;
        case KeyEvent.VK_RIGHT:
          sizeDragged(1, 0);
          return;
      }
    }
    //点下Control
    if (e.isControlDown())
    {
      switch (e.getKeyCode())
      {
        case KeyEvent.VK_C:
          copy();
          return;
        case KeyEvent.VK_V:
          paste();
          return;
        case KeyEvent.VK_X:
          cut();
          return;
        case KeyEvent.VK_A:
          String oldSelect = getSelects();
          selectAll();
          parent.getWorkLog().saveLog("selected", oldSelect + "," + getSelects());
          return;
        case KeyEvent.VK_Z:
          if (e.isShiftDown())
            redo();
          else
            undo();
          return;
      }
    }
    //移动
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_UP:
        mouseDragged(0, -1, true);
        return;
      case KeyEvent.VK_DOWN:
        mouseDragged(0, 1, true);
        return;
      case KeyEvent.VK_LEFT:
        mouseDragged( -1, 0, true);
        return;
      case KeyEvent.VK_RIGHT:
        mouseDragged(1, 0, true);
        return;
      case KeyEvent.VK_DELETE:
        delete(true);
        return;
    }
  }

  public void keyReleased(KeyEvent e)
  {
  }

  public void keyTyped(KeyEvent e)
  {
  }

  /**
   * 全部选中
   */
  public void selectAll()
  {
    removeSelects();
    parent.selectAll();
  }

  /**
   * 删除选中对象
   */
  public void delete()
  {
    delete(false);
  }

  /**
   * 删除选中对象
   * @param b true 保存回退 fase 不保存回退
   */
  public void delete(boolean b)
  {
    for (int index = selectList.size() - 1; index >= 0; index--)
    {
      DComponent component = (DComponent)selectList.get(index);
      String area = component.getArea();
      if (b)
        parent.getWorkLog().saveLog(component.getType() + ":" + component.getID(), "Delete");

      AreaPanel work = parent.getArea(area);
      if (work == null)
        continue;
      removeSelect( (IMoveable) component);
      work.remove((JComponent)component);
      work.validate();
      work.repaint();
    }
  }

  /**
   * 拷贝
   */
  public void copy()
  {
    copyList.clear();
    for (int index = 0; index < selectList.size(); index++)
      copyList.add(((DComponent)selectList.get(index)).clone());
    if (copyList.size() > 0)
      parent.callEventListener("COPY_OBJECT", null);
  }

  /**
   * 剪切
   */
  public void cut()
  {
    copy();
    delete(true);
  }

  /**
   * 粘贴
   */
  public void paste()
  {
    removeSelects();
    for (int index = 0; index < copyList.size(); index++)
    {
      DComponent component = (DComponent)copyList.get(index);
      component = (DComponent)component.clone();
      AreaPanel work = parent.getArea(component.getArea());
      if (work == null)
        continue;
      work.add((JComponent)component, 0);
      addSelect((IMoveable)component);
      parent.getWorkLog().saveLog(component.getType() + ":" + component.getID(), "Create");
    }
  }

  /**
   * 回滚
   */
  public void undo()
  {
    parent.getWorkLog().undo();
  }

  /**
   * 前滚
   */
  public void redo()
  {
    parent.getWorkLog().redo();
  }

  /**
   * 能否拷贝
   * @return true 能 false 否
   */
  public boolean canCopy()
  {
    return copyList.size() > 0;
  }

  /**
   * 得到父类对象
   * @return
   */
  public BackPanel getParentObject()
  {
    return parent;
  }
}
