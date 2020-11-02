package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import java.awt.Color;
import java.awt.FontMetrics;

/**
 *
 * <p>Title: �˵�</p>
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
     * ������
     */
    public DMenu()
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
     * ���õ����˵�
     * @param popupMenu DPopupMenu
     */
    public void setPopupMenu(DPopupMenu popupMenu)
    {
        if(popupMenu == null)
        {
            attribute.remove("D_popupMenu");
            return;
        }
        //���ø��˵�
        popupMenu.setParentMenu(this);
        attribute.put("D_popupMenu",popupMenu);
    }
    /**
     * �õ������˵�
     * @return DPopupMenu
     */
    public DPopupMenu getPopupMenu()
    {
        return (DPopupMenu)attribute.get("D_popupMenu");
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
     * ����ѡ��
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
     * ����ѡ��
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
     * �Ƿ�ѡ��
     * @return boolean
     */
    public boolean isChecked()
    {
        return TypeTool.getBoolean(attribute.get("D_checked"));
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
     * ����뿪
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
        clickedMenu();
        return false;
    }
    /**
     * ����˵�
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
     * ����ƶ�
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        super.onMouseMoved();
        if(!isEnabled())
            return false;
        //����ƶ����˵�
        if(parentIsMenuBar())
            moveMenuForMenuBar();
        else if(this.parentIsPopupMenu())
            moveMenuForPopupMenu();
        return false;
    }
    /**
     * ����ƶ����˵������ǵ����˵�
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
     * ����ƶ����˵������ǲ˵���
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
     * ���Ʋ˵��߿�
     * @param g Graphics
     */
    public void paintMenuBorder(Graphics g)
    {
        DBorder.paintMenu(this,g,getButtonBorderDrawState());
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
        //���Ƽ�ͷ
        g.setColor(color1);
        TDrawTool.drawJ(width - 1,height / 2 + 1,8,g);
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
    /**
     * �õ����������
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
     * �����ߴ�
     */
    public void resetSize()
    {
        //���ܵ����ߴ�
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
     * ��ʾ�����˵�
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
     * ���ص����˵�
     */
    public void hidePopupMenu()
    {
        DPopupMenu menu = getPopupMenu();
        if(menu == null)
            return;
        menu.hide();
    }
    /**
     * �õ������ʾ���
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
     * �õ������ʾ�߶�
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
