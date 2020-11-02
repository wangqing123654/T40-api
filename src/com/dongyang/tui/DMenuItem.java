package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import java.awt.FontMetrics;
/**
 *
 * <p>Title: 菜单项目</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.25
 * @version 1.0
 */
public class DMenuItem extends DComponent
{
    /**
     * 构造器
     */
    public DMenuItem()
    {
    }
    /**
     * 设置默认边框尺寸
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(1,8,2,8);
    }
    /**
     * 设置按钮边框绘制样式
     * @param state int
     */
    public void setButtonBorderDrawState(int state)
    {
        if(state == 0)
        {
            attribute.remove("D_buttonBorderDrawState");
            return;
        }
        attribute.put("D_buttonBorderDrawState",state);
    }
    /**
     * 得到按钮边框绘制样式
     * @return int
     */
    public int getButtonBorderDrawState()
    {
        return TypeTool.getInt(attribute.get("D_buttonBorderDrawState"));
    }
    /**
     * 设置快捷键
     * @param c char
     */
    public void setMnemonic(char c)
    {
        if(c == 0)
        {
            attribute.remove("D_mnemonic");
            return;
        }
        attribute.put("D_mnemonic",c);
    }
    /**
     * 得到快捷键
     * @return char
     */
    public char getMnemonic()
    {
        return TypeTool.getChar(attribute.get("D_mnemonic"));
    }
    /**
     * 设置快捷键
     * @param key String
     */
    public void setKey(String key)
    {
        if(key == null || key.length() == 0)
        {
            attribute.remove("D_key");
            return;
        }
        attribute.put("D_key",key);
    }
    /**
     * 得到快捷键
     * @return String
     */
    public String getKey()
    {
        return (String)attribute.get("D_key");
    }
    /**
     * 鼠标离开
     * @return boolean
     */
    public boolean onMouseExited()
    {
        super.onMouseExited();
        setButtonBorderDrawState(0);
        repaint();
        return false;
    }
    /**
     * 左键按下
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        if(!isEnabled())
            return false;
        //点击菜单
        clickedMenuItem();
        setButtonBorderDrawState(0);
        return false;
    }
    /**
     * 鼠标移动
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        super.onMouseMoved();
        if(!isEnabled())
            return false;
        DMenu menu = getCurrentMenu();
        if(menu != null && parentIsPopupMenu())
        {
            menu.setButtonBorderDrawState(0);
            menu.setChecked(false);
            menu.repaint();
            setCurrentMenu(null);
        }
        setButtonBorderDrawState(2);

        repaint();
        return false;
    }
    /**
     * 点击菜单
     */
    public void clickedMenuItem()
    {
        DMenuBar menuBar = getTopMenuBar();
        if(menuBar != null)
            menuBar.hidePopupMenu();
        else{
            DPopupMenu popupMenu = getTopPopupMenu();
            if (popupMenu != null)
                popupMenu.hide();
        }
        onClicked();
    }
    /**
     * 点击事件
     */
    public void onClicked()
    {
        runActionMap("onClicked");
    }
    /**
     * 得到顶部的菜点条
     * @return DMenuBar
     */
    public DMenuBar getTopMenuBar()
    {
        DMenuBar menubar = getParentMenuBar();
        if(menubar != null)
            return menubar;
        DPopupMenu popupMenu = getParentPopupMenu();
        if(popupMenu == null)
            return null;
        DMenu menu = popupMenu.getTopMenu();
        if(menu == null)
            return null;
        return menu.getParentMenuBar();
    }
    /**
     * 得到顶部的弹出菜单
     * @return DPopupMenu
     */
    public DPopupMenu getTopPopupMenu()
    {
        DPopupMenu popupMenu = getParentPopupMenu();
        if(popupMenu == null)
            return null;
        DMenu menu = popupMenu.getTopMenu();
        if(menu == null)
            return popupMenu;
        return menu.getParentPopupMenu();
    }
    /**
     * 绘制边框
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        if(getBorder() != null && getBorder().length() >= 0)
        {
            super.paintBorder(g);
            return;
        }
        //绘制菜单边框
        paintMenuBorder(g);
    }
    /**
     * 绘制菜单边框
     * @param g Graphics
     */
    public void paintMenuBorder(Graphics g)
    {
        DBorder.paintMenuItem(this,g,getButtonBorderDrawState());
    }
    /**
     * 绘制字体
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintText(Graphics g,int width,int height)
    {
        if(!parentIsPopupMenu())
        {
            super.paintText(g, width, height);
            return;
        }

        String color = getColor();
        String text = getText();
        String font = getFont();
        char c = getMnemonic();
        if(color == null || color.length() == 0 || text == null || text.length() == 0)
            return;
        if(!isEnabled())
            color = "141,141,141";
        int picWidth = 22;
        Graphics g1 = g.create(picWidth,0,width - picWidth,height);
        TDrawTool.paintText(text,DFont.getFont(font),
                            DColor.getColor(color),
                            width - picWidth,height,
                            DAlignment.getHorizontalAlignment("left"),
                            DAlignment.getVerticalAlignment(getVerticalAlignment()),c,g1);
        String key = getKey();
        if(key != null && key.length() > 0)
        {
            TDrawTool.paintText(key,DFont.getFont(font),
                                DColor.getColor(color),
                                width - picWidth,height,
                                DAlignment.getHorizontalAlignment("right"),
                                DAlignment.getVerticalAlignment(getVerticalAlignment()),g1);
        }
    }
    /**
     * 得到最大显示宽度
     * @return int
     */
    public int getMaxViewWidth()
    {
        String text = getText();
        if(text == null)
            text = " ";
        char c = getMnemonic();
        if (c != 0)
        {
            String m = ("" + c).toUpperCase();
            if (text.indexOf(m) == -1)
                text += "(" + m + ")";
        }
        String key = getKey();
        if(key != null && key.length() > 0)
        {
            text += " " + key;
        }
        FontMetrics fontMetric = DFont.getFontMetrics(DFont.getFont(getFont()));
        DInsets insets = getInsets();
        return fontMetric.stringWidth(text) + insets.left + insets.right + (parentIsPopupMenu()?22:0);
    }
    /**
     * 得到最大显示高度
     * @return int
     */
    public int getMaxViewHeight()
    {
        FontMetrics fontMetric = DFont.getFontMetrics(DFont.getFont(getFont()));
        DInsets insets = getInsets();
        return fontMetric.getHeight() + insets.top + insets.bottom;
    }
    /**
     * 父类是菜单条
     * @return boolean
     */
    public boolean parentIsMenuBar()
    {
        return getParentMenuBar() != null;
    }
    /**
     * 父类是弹出菜单
     * @return boolean
     */
    public boolean parentIsPopupMenu()
    {
        return getParentPopupMenu() != null;
    }
    /**
     * 得到父工具条
     * @return DMenuBar
     */
    public DMenuBar getParentMenuBar()
    {
        DComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof DMenuBar)
            return (DMenuBar)component;
        return null;
    }
    /**
     * 得到父弹出菜单
     * @return DPopupMenu
     */
    public DPopupMenu getParentPopupMenu()
    {
        DComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof DPopupMenu)
            return (DPopupMenu)component;
        return null;
    }
    /**
     * 设置当前打开菜单
     * @param menu DMenu
     */
    public void setCurrentMenu(DMenu menu)
    {
        DComponent parent = getParent();
        if(parent == null)
            return;
        //菜单条
        if(parent instanceof DMenuBar)
        {
            ((DMenuBar)parent).setCurrentMenu(menu);
            return;
        }
        //弹出菜单
        if(parent instanceof DPopupMenu)
        {
            ((DPopupMenu)parent).setCurrentMenu(menu);
            return;
        }
    }
    /**
     * 得到当前打开菜单
     * @return DMenu
     */
    public DMenu getCurrentMenu()
    {
        DComponent parent = getParent();
        if(parent == null)
            return null;
        //菜单条
        if(parent instanceof DMenuBar)
            return ((DMenuBar)parent).getCurrentMenu();
        //弹出菜单
        if(parent instanceof DPopupMenu)
            return ((DPopupMenu)parent).getCurrentMenu();
        return null;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DMenuItem>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
    }
}
