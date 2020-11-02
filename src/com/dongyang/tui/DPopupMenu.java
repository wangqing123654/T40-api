package com.dongyang.tui;

import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import java.awt.Color;
import java.awt.Frame;
import com.dongyang.ui.base.TCBase;
import com.dongyang.util.TList;

/**
 *
 * <p>Title: 弹出菜单</p>
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
     * 构造器
     */
    public DPopupMenu()
    {
        setBKColor(246,246,246);
    }
    /**
     * 设置默认边框尺寸
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(2,2,2,2);
    }
    /**
     * 设置父菜单
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
     * 得到父菜单
     * @return DMenu
     */
    public DMenu getParentMenu()
    {
        return (DMenu)attribute.get("D_parentMenu");
    }
    /**
     * 得到顶级菜单
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
     * 显示
     * @param x int
     * @param y int
     */
    public void show(int x,int y)
    {
        //调整位置
        setLocation(x,y);
        //得到父类窗口
        Frame frame = getParentFrame();
        if(frame == null)
            return;
        //调整尺寸
        resetSize();
        //显示
        open(frame);
    }
    /**
     * 得到父类窗口
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
     * 隐藏弹出菜单
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
     * 隐藏
     */
    public void hide()
    {
        //隐藏弹出菜单
        hidePopupMenu();
        close();
    }
    /**
     * 绘制弹出菜单边框
     * @param g Graphics
     */
    public void paintPopupMenuBorder(Graphics g)
    {
        DRectangle r = getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        TDrawTool.drawRect(0,0,width,height,new Color(0,45,150),g);
        //绘制弹出菜单效果
        paintTopMenu(g,width,height);
    }
    /**
     * 绘制弹出菜单效果
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
     * 绘制边框
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
        //绘制按钮边框
        paintPopupMenuBorder(g);
    }
    /**
     * 绘制背景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int width,int height)
    {
    }
    /**
     * 调整尺寸
     */
    public void resetSize()
    {
        //不能调整尺寸
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
     * 得到显示宽度
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
     * 设置当前打开菜单
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
            //记录旧焦点
            setOldFocus(getFocus());
            grabFocus();
        }*/
        attribute.put("D_currentMenu",menu);
    }
    /**
     * 得到当前打开菜单
     * @return DMenu
     */
    public DMenu getCurrentMenu()
    {
        return (DMenu)attribute.get("D_currentMenu");
    }
    /**
     * 查找共享方法
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
