package com.dongyang.tui;

import com.dongyang.util.TList;
import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import com.dongyang.ui.base.TCBase;
import java.awt.event.MouseEvent;

/**
 *
 * <p>Title: �˵���</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.20
 * @version 1.0
 */
public class DMenuBar extends DComponent
{
    /**
     * ������
     */
    public DMenuBar()
    {
        setBKColor(164,195,245);
    }
    /**
     * ����Ĭ�ϱ߿�ߴ�
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(2,2,2,2);
    }
    /**
     * �õ�Ĭ�Ͻ���
     * @return DComponent
     */
    public DComponent getDefaultFocus()
    {
        return null;
    }
    /**
     * ���ص����˵�
     */
    public void hidePopupMenu()
    {
        DMenu menu = getCurrentMenu();
        if(menu == null)
            return;
        setCurrentMenu(null);
        menu.setButtonBorderDrawState(0);
        menu.setChecked(false);
        menu.repaint();
    }
    /**
     * ���þɽ���
     * @param component DComponent
     */
    public void setOldFocus(DComponent component)
    {
        if(component == getOldFocus())
            return;
        if(component == null)
        {
            attribute.remove("D_oldFocus");
            return;
        }
        attribute.put("D_oldFocus",component);
    }
    /**
     * �õ��ɽ���
     * @return DComponent
     */
    public DComponent getOldFocus()
    {
        return (DComponent)attribute.get("D_oldFocus");
    }
    /**
     * ���õ�ǰ�򿪲˵�
     * @param menu DMenu
     */
    public void setCurrentMenu(DMenu menu)
    {
        if(getCurrentMenu() == menu)
            return;
        if(menu == null)
        {
            attribute.remove("D_currentMenu");
            DComponent oldFocus = getOldFocus();
            if(oldFocus != null)
            {
                setOldFocus(null);
                if (getFocus() == this)
                    setFocus(oldFocus);
            }
            return;
        }
        if(!isFocus())
        {
            //��¼�ɽ���
            setOldFocus(getFocus());
            grabFocus();
        }
        attribute.put("D_currentMenu",menu);
    }
    /**
     * �õ���ǰ�򿪲˵�
     * @return DMenu
     */
    public DMenu getCurrentMenu()
    {
        return (DMenu)attribute.get("D_currentMenu");
    }
    /**
     * �õ������¼�
     * @return boolean
     */
    public boolean onFocusGained()
    {
        super.onFocusGained();

        return false;
    }
    /**
     * ʧȥ�����¼�
     * @return boolean
     */
    public boolean onFocusLost()
    {
        super.onFocusLost();
        hidePopupMenu();
        return false;
    }
    /**
     * ������갴��
     */
    public void onWindowMousePressed()
    {
        DMenu menu = getCurrentMenu();
        if(menu == null)
            return;
        TCBase base = getTCBase();
        if(base == null)
            return;
        MouseEvent e = base.getMouseEvent();
        if(e == null)
            return;
        DPoint point = getScreenPoint();
        DPoint pointBase = base.getScreenPoint();
        int ex = e.getX() + pointBase.getX();
        int ey = e.getY() + pointBase.getY();
        if(ex < point.getX() || ex > point.getX() + getWidthV() ||
           ey < point.getY() || ey > point.getY() + getHeightV())
        {
            hidePopupMenu();
            return;
        }
    }
    /**
     * �������
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        hidePopupMenu();
        return false;
    }
    /**
     * �ߴ�ı�
     * @return boolean
     */
    public boolean onComponentResized()
    {
        super.onComponentResized();
        hidePopupMenu();
        resetSize();
        return true;
    }
    /**
     * �����ƶ��¼�
     */
    public void onWindowMoved()
    {
        hidePopupMenu();
        super.onWindowMoved();
    }
    /**
     * �����ߴ�
     */
    public void resetSize()
    {
        //���ܵ����ߴ�
        if (!canResetSize())
            return;
        TList list = getDComponentList();
        if(list == null)
            return;
        DSize size = getInsetSize();
        int x = 0;
        int y = 0;
        int h = 0;
        for(int i = 0;i < list.size();i++)
        {
            DComponent component = (DComponent)list.get(i);
            if(component == null)
                continue;
            component.resetSize();
            if(x > 0 && x + component.getWidthV() > size.getWidth())
            {
                x = 0;
                y += h;
                h = 0;
            }
            component.setLocation(x,y);
            h = Math.max(h,component.getHeightV());
            x += component.getWidthV();
        }
        DInsets i = getInsets();
        h = y + h + i.top + i.bottom;
        if(h != getHeight())
            setHeight(h);

    }
    /**
     * ���Ʊ���
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int width,int height)
    {
    }
    /**
     * ���Ʊ߿�
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        String bkColor = getBKColor();
        if(bkColor != null && bkColor.length() > 0)
        {
            DRectangle rectangle = getComponentBounds();
            TDrawTool.fillRect(0, 0, rectangle.getWidth(), rectangle.getHeight(), DColor.getColor(bkColor),g);
        }
        super.paintBorder(g);
    }
    /**
     * �ܹ��õ�����
     * @return boolean
     */
    public boolean canFocus()
    {
        return true;
    }
}
