package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import java.awt.FontMetrics;
/**
 *
 * <p>Title: �˵���Ŀ</p>
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
     * ������
     */
    public DMenuItem()
    {
    }
    /**
     * ����Ĭ�ϱ߿�ߴ�
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(1,8,2,8);
    }
    /**
     * ���ð�ť�߿������ʽ
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
     * �õ���ť�߿������ʽ
     * @return int
     */
    public int getButtonBorderDrawState()
    {
        return TypeTool.getInt(attribute.get("D_buttonBorderDrawState"));
    }
    /**
     * ���ÿ�ݼ�
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
     * �õ���ݼ�
     * @return char
     */
    public char getMnemonic()
    {
        return TypeTool.getChar(attribute.get("D_mnemonic"));
    }
    /**
     * ���ÿ�ݼ�
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
     * �õ���ݼ�
     * @return String
     */
    public String getKey()
    {
        return (String)attribute.get("D_key");
    }
    /**
     * ����뿪
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
     * �������
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        if(!isEnabled())
            return false;
        //����˵�
        clickedMenuItem();
        setButtonBorderDrawState(0);
        return false;
    }
    /**
     * ����ƶ�
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
     * ����˵�
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
     * ����¼�
     */
    public void onClicked()
    {
        runActionMap("onClicked");
    }
    /**
     * �õ������Ĳ˵���
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
     * �õ������ĵ����˵�
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
     * ���Ʊ߿�
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        if(getBorder() != null && getBorder().length() >= 0)
        {
            super.paintBorder(g);
            return;
        }
        //���Ʋ˵��߿�
        paintMenuBorder(g);
    }
    /**
     * ���Ʋ˵��߿�
     * @param g Graphics
     */
    public void paintMenuBorder(Graphics g)
    {
        DBorder.paintMenuItem(this,g,getButtonBorderDrawState());
    }
    /**
     * ��������
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
     * �õ������ʾ���
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
     * �õ������ʾ�߶�
     * @return int
     */
    public int getMaxViewHeight()
    {
        FontMetrics fontMetric = DFont.getFontMetrics(DFont.getFont(getFont()));
        DInsets insets = getInsets();
        return fontMetric.getHeight() + insets.top + insets.bottom;
    }
    /**
     * �����ǲ˵���
     * @return boolean
     */
    public boolean parentIsMenuBar()
    {
        return getParentMenuBar() != null;
    }
    /**
     * �����ǵ����˵�
     * @return boolean
     */
    public boolean parentIsPopupMenu()
    {
        return getParentPopupMenu() != null;
    }
    /**
     * �õ���������
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
     * �õ��������˵�
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
     * ���õ�ǰ�򿪲˵�
     * @param menu DMenu
     */
    public void setCurrentMenu(DMenu menu)
    {
        DComponent parent = getParent();
        if(parent == null)
            return;
        //�˵���
        if(parent instanceof DMenuBar)
        {
            ((DMenuBar)parent).setCurrentMenu(menu);
            return;
        }
        //�����˵�
        if(parent instanceof DPopupMenu)
        {
            ((DPopupMenu)parent).setCurrentMenu(menu);
            return;
        }
    }
    /**
     * �õ���ǰ�򿪲˵�
     * @return DMenu
     */
    public DMenu getCurrentMenu()
    {
        DComponent parent = getParent();
        if(parent == null)
            return null;
        //�˵���
        if(parent instanceof DMenuBar)
            return ((DMenuBar)parent).getCurrentMenu();
        //�����˵�
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
