package com.dongyang.ui;

import com.dongyang.ui.base.TPopupMenuBase;
import java.awt.Component;
import com.dongyang.config.TConfigParm;
import com.dongyang.data.TParm;
import com.dongyang.util.StringTool;
import java.awt.Point;
import com.dongyang.util.ImageTool;
import java.awt.Dimension;

public class TPopupMenu extends TPopupMenuBase{
    /**
     * 得到弹出菜单
     * @param tag String
     * @param component Component
     * @param configParm TConfigParm
     * @return TPopupMenu
     */
    public static TPopupMenu getPopupMenu(String tag,Component component,TConfigParm configParm)
    {
        return getPopupMenu(tag,component,configParm,new TParm());
    }
    /**
     * 得到弹出菜单
     * @param tag String
     * @param component Component
     * @param configParm TConfigParm
     * @param parameter TParm
     * @return TPopupMenu
     */
    public static TPopupMenu getPopupMenu(String tag,Component component,TConfigParm configParm,TParm parameter)
    {
        TPopupMenu popupMenu = new TPopupMenu();
        popupMenu.setParameter(parameter);
        popupMenu.setTag(tag);
        popupMenu.setLoadTag("UI");
        popupMenu.init(configParm);
        popupMenu.onInit();
        popupMenu.setPopupSize(popupMenu.getWidth(),popupMenu.getHeight());
        popupMenu.setInvokerComponent(component);
        return popupMenu;
    }
    /**
     * 显示
     */
    public void showPopupMenu()
    {
        if(getInvokerComponent() instanceof TTextField)
        {
            TTable table = ((TTextField)getInvokerComponent()).getTable();
            if (table != null)
            {

                Point p1 = getInvokerComponent().getLocationOnScreen();
                Point p2 = table.getLocationOnScreen();
                int x = p1.x - p2.x;
                int y = p1.y - p2.y;
                Dimension d = ImageTool.getScreenSize();
                if((int)d.getWidth() - p1.x - 10 < getWidth())
                    x = (int)d.getWidth() - getWidth() - p2.x - 10;
                if((int)d.getHeight() - p1.y - 50 < getHeight())
                    y = (int)d.getHeight() - getHeight() - p2.y - 50;
                show(table, x, y);
                return;
            }
        }
        show(getInvokerComponent(),0,0);
    }
    /**
     * 隐藏
     */
    public void hidePopupMenu()
    {
        setVisible(false);
    }
    /**
     * 创建弹出菜单
     * @param message String
     * @return TPopupMenu
     */
    public static TPopupMenu createPopupMenu(String message)
    {
        TPopupMenu popup = new TPopupMenu();
        popup.setTag("POPUP_MENU");
        String s[] = StringTool.parseLine(message,";");
        for(int i = 0;i < s.length;i++)
        {
            if(s[i].equals("|"))
            {
                popup.addSeparator();
                continue;
            }
            TMenuItem item = TMenuItem.createMenuItem(s[i]);
            popup.add(item);
            item.setParentComponent(popup);
            item.onInit();
        }
        popup.onInit();
        return popup;
    }
}
