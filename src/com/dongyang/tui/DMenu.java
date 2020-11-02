package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import java.awt.Color;
import java.awt.FontMetrics;

/**
 *
 * <p>Title: 菜单</p>
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
public class DMenu extends DComponent
{
    /**
     * 构造器
     */
    public DMenu()
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
     * 设置弹出菜单
     * @param popupMenu DPopupMenu
     */
    public void setPopupMenu(DPopupMenu popupMenu)
    {
        if(popupMenu == null)
        {
            attribute.remove("D_popupMenu");
            return;
        }
        //设置父菜单
        popupMenu.setParentMenu(this);
        attribute.put("D_popupMenu",popupMenu);
    }
    /**
     * 得到弹出菜单
     * @return DPopupMenu
     */
    public DPopupMenu getPopupMenu()
    {
        return (DPopupMenu)attribute.get("D_popupMenu");
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
     * 设置选中
     * @param checked boolean
     */
    public void setChecked(boolean checked)
    {
        if(checked == isChecked())
            return;
        setCheckedP(checked);
        if(checked)
            showPopupMenu();
        else
            hidePopupMenu();
    }
    /**
     * 设置选中
     * @param checked boolean
     */
    public void setCheckedP(boolean checked)
    {
        if(!checked)
        {
            attribute.remove("D_checked");
            return;
        }
        attribute.put("D_checked",checked);
    }
    /**
     * 是否选中
     * @return boolean
     */
    public boolean isChecked()
    {
        return TypeTool.getBoolean(attribute.get("D_checked"));
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
     * 鼠标离开
     * @return boolean
     */
    public boolean onMouseExited()
    {
        super.onMouseExited();
        if(getCurrentMenu() == null)
        {
            setButtonBorderDrawState(0);
            repaint();
            return false;
        }
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
        clickedMenu();
        return false;
    }
    /**
     * 点击菜单
     */
    public void clickedMenu()
    {
        if(!parentIsMenuBar())
            return;
        if(!isChecked())
        {
            setButtonBorderDrawState(3);
            setCurrentMenu(this);
            setChecked(true);
            repaint();
            return;
        }
        setButtonBorderDrawState(2);
        setChecked(false);
        setCurrentMenu(null);
        repaint();
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
        //鼠标移动到菜单
        if(parentIsMenuBar())
            moveMenuForMenuBar();
        else if(this.parentIsPopupMenu())
            moveMenuForPopupMenu();
        return false;
    }
    /**
     * 鼠标移动到菜单父类是弹出菜单
     */
    public void moveMenuForPopupMenu()
    {
        if (getButtonBorderDrawState() == 2)
            return;
        setButtonBorderDrawState(2);
        repaint();
        setChecked(true);
        setCurrentMenu(this);
    }
    /**
     * 鼠标移动到菜单父类是菜单条
     */
    public void moveMenuForMenuBar()
    {
        DMenu menu = getCurrentMenu();
        if(menu == null)
        {
            if (getButtonBorderDrawState() != 2)
            {
                setButtonBorderDrawState(2);
                repaint();
            }
            return;
        }
        if(menu == this)
            return;
        menu.setButtonBorderDrawState(0);
        menu.setChecked(false);
        menu.repaint();
        setButtonBorderDrawState(3);
        setChecked(true);
        setCurrentMenu(this);

        repaint();
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
     * 绘制菜单边框
     * @param g Graphics
     */
    public void paintMenuBorder(Graphics g)
    {
        DBorder.paintMenu(this,g,getButtonBorderDrawState());
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
            String color = getColor();
            String text = getText();
            char c = getMnemonic();
            String font = getFont();
            if(color == null || color.length() == 0 || text == null || text.length() == 0)
                return;
            if(!isEnabled())
                color = "141,141,141";
            TDrawTool.paintText(text,DFont.getFont(font),
                                DColor.getColor(color),
                                width,height,
                                DAlignment.getHorizontalAlignment(getHorizontalAlignment()),
                                DAlignment.getVerticalAlignment(getVerticalAlignment()),c,g);
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
        Color color1 = DColor.getColor(color);
        int picWidth = 22;
        Graphics g1 = g.create(picWidth,0,width - picWidth,height);
        TDrawTool.paintText(text,DFont.getFont(font),
                            color1,
                            width - picWidth,height,
                            DAlignment.getHorizontalAlignment("left"),
                            DAlignment.getVerticalAlignment(getVerticalAlignment()),c,g1);
        //绘制箭头
        g.setColor(color1);
        TDrawTool.drawJ(width - 1,height / 2 + 1,8,g);
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
    /**
     * 得到父类最大宽度
     * @return int
     */
    public int getParentMaxWidth()
    {
        DMenuBar menuBar = getParentMenuBar();
        if(menuBar != null)
            return menuBar.getInsetWidth();
        return 0;
    }
    /**
     * 调整尺寸
     */
    public void resetSize()
    {
        //不能调整尺寸
        if(!canResetSize())
            return;
        String text = getText();
        if(text == null)
            text = "";
        int width = getParentMaxWidth();
        DInsets insets = getInsets();
        width -= insets.left + insets.right;
        DSize size = TDrawTool.getTextSize(text,DFont.getFont(getFont()),getParentMaxWidth(),getMnemonic());
        setSize(size.getWidth() + insets.left + insets.right,size.getHeight() + insets.top + insets.bottom);
    }
    /**
     * 显示弹出菜单
     */
    public void showPopupMenu()
    {
        DPopupMenu menu = getPopupMenu();
        if(menu == null)
            return;
        DPoint point = getScreenPoint();
        if(parentIsMenuBar())
            menu.show(point.getX(),point.getY() + getComponentBounds().getHeight() - 1);
        else if(parentIsPopupMenu())
        {
            menu.show(point.getX() + getComponentBounds().getWidth(),point.getY());
        }
    }
    /**
     * 隐藏弹出菜单
     */
    public void hidePopupMenu()
    {
        DPopupMenu menu = getPopupMenu();
        if(menu == null)
            return;
        menu.hide();
    }
    /**
     * 得到最大显示宽度
     * @return int
     */
    public int getMaxViewWidth()
    {
        String text = getText();
        if(text == null)
            text = "";
        char c = getMnemonic();
        if (c != 0)
        {
            String m = ("" + c).toUpperCase();
            if (text.indexOf(m) == -1)
                text += "(" + m + ")";
        }
        FontMetrics fontMetric = DFont.getFontMetrics(DFont.getFont(getFont()));
        DInsets insets = getInsets();
        return fontMetric.stringWidth(text) + insets.left + insets.right + (parentIsPopupMenu()?30:0);
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
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DMenu>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
    }
}
