package com.dongyang.tui;

import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import java.awt.Color;
import java.awt.Frame;
import com.dongyang.ui.base.TCBase;
import com.dongyang.util.TList;

/**
 *
 * <p>Title: �����˵�</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.3.20
 * @version 1.0
 */
public class DPopupMenu extends DWindow
{
    /**
     * ������
     */
    public DPopupMenu()
    {
        setBKColor(246,246,246);
    }
    /**
     * ����Ĭ�ϱ߿�ߴ�
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(2,2,2,2);
    }
    /**
     * ���ø��˵�
     * @param menu DMenu
     */
    public void setParentMenu(DMenu menu)
    {
        if(menu == null)
        {
            attribute.remove("D_parentMenu");
            return;
        }
        attribute.put("D_parentMenu",menu);
    }
    /**
     * �õ����˵�
     * @return DMenu
     */
    public DMenu getParentMenu()
    {
        return (DMenu)attribute.get("D_parentMenu");
    }
    /**
     * �õ������˵�
     * @return DMenu
     */
    public DMenu getTopMenu()
    {
        DMenu menu = getParentMenu();
        if(menu == null)
            return null;
        DPopupMenu popupMenu = menu.getParentPopupMenu();
        if(popupMenu != null)
            return popupMenu.getTopMenu();
        return menu;
    }
    /**
     * ��ʾ
     * @param x int
     * @param y int
     */
    public void show(int x,int y)
    {
        //����λ��
        setLocation(x,y);
        //�õ����ര��
        Frame frame = getParentFrame();
        if(frame == null)
            return;
        //�����ߴ�
        resetSize();
        //��ʾ
        open(frame);
    }
    /**
     * �õ����ര��
     * @return Frame
     */
    public Frame getParentFrame()
    {
        DMenu menu = getTopMenu();
        if(menu != null)
        {
            TCBase base = menu.getTCBase();
            if(base == null)
                return null;
            return base.getFrame();
        }
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
     * ����
     */
    public void hide()
    {
        //���ص����˵�
        hidePopupMenu();
        close();
    }
    /**
     * ���Ƶ����˵��߿�
     * @param g Graphics
     */
    public void paintPopupMenuBorder(Graphics g)
    {
        DRectangle r = getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        TDrawTool.drawRect(0,0,width,height,new Color(0,45,150),g);
        //���Ƶ����˵�Ч��
        paintTopMenu(g,width,height);
    }
    /**
     * ���Ƶ����˵�Ч��
     * @param g Graphics
     * @param width int
     * @param height int
     */
    private void paintTopMenu(Graphics g,int width,int height)
    {
        DMenu menu = getParentMenu();
        if(menu == null)
            return;
        if(menu.parentIsMenuBar())
        {
            String bkColor = getBKColor();
            if(bkColor == null || bkColor.length() == 0)
                return;
            DRectangle r = menu.getComponentBounds();
            int w = r.getWidth();
            if(w > width)
                w = width;
            g.setColor(DColor.getColor(bkColor));
            g.drawLine(1,0,w - 2,0);
            return;
        }
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
        if(getBorder() != null && getBorder().length() >= 0)
        {
            super.paintBorder(g);
            return;
        }
        //���ư�ť�߿�
        paintPopupMenuBorder(g);
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
        int maxViewWidth = getMaxViewWidth();
        int x = 0;
        int y = 0;
        for(int i = 0;i < list.size();i++)
        {
            DComponent com = (DComponent) list.get(i);
            if (com == null)
                continue;
            com.setLocation(x,y);
            int maxViewHeight = com.getMaxViewHeight();
            com.setSize(maxViewWidth,maxViewHeight);
            y += maxViewHeight;
        }
        DInsets insets = getInsets();
        setSize(maxViewWidth + insets.left + insets.right,y + insets.top + insets.bottom);
    }
    /**
     * �õ���ʾ���
     * @return int
     */
    public int getMaxViewWidth()
    {
        TList list = getDComponentList();
        if(list == null)
            return 0;
        int width = 0;
        for(int i = 0;i < list.size();i++)
        {
            DComponent com = (DComponent)list.get(i);
            if(com == null)
                continue;
            width = Math.max(width,com.getMaxViewWidth());
        }
        return width;
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
            /*DComponent oldFocus = getOldFocus();
            if(oldFocus != null)
            {
                setOldFocus(null);
                if (getFocus() == this)
                    setFocus(oldFocus);
            }*/
            return;
        }
        /*if(!isFocus())
        {
            //��¼�ɽ���
            setOldFocus(getFocus());
            grabFocus();
        }*/
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
     * ���ҹ�����
     * @param name String
     * @return Object
     */
    public Object findPublicMethod(String name)
    {
        DMenu menu = getParentMenu();
        System.out.println(menu);
        if(menu != null)
            return menu.findPublicMethod(name);
        return null;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DPopupMenu>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
    }

}
