package com.dongyang.ui.datawindow;

import java.awt.Point;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * <p>Title: ѡ�п���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class SelectControl
    implements KeyListener
{
  //��������
  private static List copyList = new ArrayList();
  //���ߵװ�
  private BackPanel parent;
  //ѡ���б�
  private List selectList = new ArrayList();
  //ѡ��
  private DText selectBox = new DText();
  //�Ƿ�ʼ��ѡ
  private boolean selectBoxStart;
  //��ѡ�Ķ���
  private int selectX0, selectY0;
  //��ѡ���� 1�Ӵ�ѡ 2����ѡ
  private int selectType = 1;
  /**
   * ������
   * @param parent ���ߵװ�
   */
  public SelectControl(BackPanel parent)
  {
    this.parent = parent;
  }

  /**
   * ����ѡ�����
   * @param obj ѡ�����
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
   * ȡ��ȫ��ѡ��
   */
  public void removeSelects()
  {
    for (int index = selectList.size() - 1; index >= 0; index--)
      removeSelect( (IMoveable) selectList.get(index));
    parent.callEventListener("SELECT_OBJECT", null);
    parent.repaint();
  }

  /**
   * �õ�ȫ��ѡ�ж����б�
   * @return �÷ֺż���Ķ��������б�����������"����:���"���
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
   * ����ѡ�ж���
   * @param s �÷ֺż���Ķ��������б�����������"����:���"���
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
   * ȡ��ѡ��
   * @param obj ѡ�����
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
   * �Ƿ�ѡ��
   * @param obj ���
   * @return true ѡ�� false δѡ��
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
   * �Ƿ���ƶ�,���ͬʱѡ��һ�����ϵ����,ֻ�ܸı�λ��,�������ųߴ�
   * @return true ���ƶ� false ��������
   */
  public boolean onlyMove()
  {
    return selectList.size() > 1;
  }

  /**
   * �����ѡ
   * @param obj �������
   * @param isControlDown �Ƿ���Ctrl��
   * @return true ���Լ����ƶ�,false ����
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
      //����ڶ���ȡ��ѡ��
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
   * ����϶����,�����ǰ�Ƕ�ѡ�����ȫ��ѡ�����һ���ƶ�
   * @param x ������
   * @param y ������
   */
  public void mouseDragged(int x, int y)
  {
    mouseDragged(x, y, false);
  }

  /**
   * ����϶�������ȫ�����һ���ƶ�
   * @param x ��λ��xʸ��
   * @param y ��λ��yʸ��
   * @param b true ������־ false ��������־
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
   * �����������,�����ǰ�Ƕ�ѡ�����ȫ��ѡ�����һ������
   * @param x ������
   * @param y ������
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
   * ��ȫ������ƶ���ϱ����ƶ��ۼ�,�޸�xml
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
   * �����ƶ�Text���
   * @param text �������
   * @param x ������
   * @param y ������
   */
  public void moveText(DText text, int x, int y)
  {
    int moveX = text.getX() + x;
    int moveY = text.getY() + y;
    //����Ƿ����Խ��
    if (x > 0)
      parent.checkPreferredWidth(moveX + text.getWidth());
      //����Ƿ��Խ����
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
   * �����ƶ�Line���
   * @param line �������
   * @param x ������
   * @param y ������
   */
  public void moveLine(DLine line, int x, int y)
  {
    int moveX = line.getX() + x;
    int moveY = line.getY() + y;
    //����Ƿ����Խ��
    if (x > 0)
      parent.checkPreferredWidth(moveX + line.getWidth());
      //����Ƿ��Խ����
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
   * ��������Text���
   * @param text �������
   * @param x ������
   * @param y ������
   */
  public void sizeText(DText text, int x, int y)
  {
    if (x > 0)
      parent.checkPreferredWidth(text.getX() + text.getWidth() + x);
    text.getMoveable().resize(x, y);
  }

  /**
   * ��������Line���
   * @param line �������
   * @param x ������
   * @param y ������
   */
  public void sizeLine(DLine line, int x, int y)
  {
    if (x > 0)
      parent.checkPreferredWidth(line.getX() + line.getWidth() + x);
    line.getMoveable().resize(x, y);
  }

  /**
   * ȡ��Text���ѡ��
   * @param text �������
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
   * ȡ��Line���ѡ��
   * @param line �������
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
   * ѡ��Text���
   * @param text �������
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
   * ѡ��Line���
   * @param line �������
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
   * ɾ���Ķ����ǵ�����һ��
   * @param point
   */
  private void deletePont(DPoint point)
  {
    if (point == null)
      return;
    parent.remove(point);
  }

  /**
   * �����Ķ����ǵ�����һ��
   * @param x ������
   * @param y ������
   * @param pos λ�� 1���� 2���� 3���� 4����
   * @return �����
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
  //   ��ѡ�����
  //---------------------------------------------------------------------//
  /**
   * ��ʼ��ѡ
   * @param x ������
   * @param y ������
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
   * ȡ����ѡ
   */
  public void removeSelectBox()
  {
    if (!selectBoxStart)
      return;
    parent.remove(selectBox);
    parent.repaint();
    selectBoxStart = false;
    //�����ѡ
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
   * ��ѡ�ƶ��ڶ�����
   * @param x ������
   * @param y ������
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
   * ������ѡ����
   * @param type int 1�Ӵ�ѡ 2����ѡ
   */
  public void setSelectType(int type)
  {
    this.selectType = type;
  }

  /**
   * �õ���ѡ����
   * @return int 1�Ӵ�ѡ 2����ѡ
   */
  public int getSelectType()
  {
    return selectType;
  }

  /**
   * ���̴���
   * @param e
   */
  public void keyPressed(KeyEvent e)
  {
    //����Shift
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
    //����Control
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
    //�ƶ�
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
   * ȫ��ѡ��
   */
  public void selectAll()
  {
    removeSelects();
    parent.selectAll();
  }

  /**
   * ɾ��ѡ�ж���
   */
  public void delete()
  {
    delete(false);
  }

  /**
   * ɾ��ѡ�ж���
   * @param b true ������� fase ���������
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
   * ����
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
   * ����
   */
  public void cut()
  {
    copy();
    delete(true);
  }

  /**
   * ճ��
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
   * �ع�
   */
  public void undo()
  {
    parent.getWorkLog().undo();
  }

  /**
   * ǰ��
   */
  public void redo()
  {
    parent.getWorkLog().redo();
  }

  /**
   * �ܷ񿽱�
   * @return true �� false ��
   */
  public boolean canCopy()
  {
    return copyList.size() > 0;
  }

  /**
   * �õ��������
   * @return
   */
  public BackPanel getParentObject()
  {
    return parent;
  }
}
