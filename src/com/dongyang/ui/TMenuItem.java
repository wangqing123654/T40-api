package com.dongyang.ui;

import com.dongyang.ui.base.TMenuItemBase;
import com.dongyang.util.StringTool;

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
 * @author lzk 2009.3.29
 * @version 1.0
 */
public class TMenuItem extends TMenuItemBase{
    /**
     * 创建菜单项目
     * @param message String
     * @return TMenuItem
     */
    public static TMenuItem createMenuItem(String message)
    {
        String s[] = StringTool.parseLine(message,",");
        if(s.length < 2)
        {
            System.out.println("ERR createMenuItem message " + message);
            return null;
        }
        switch(s.length)
        {
        case 2:
            return createMenuItem(s[0], s[1]);
        case 3:
            return createMenuItem(s[0], s[1].charAt(0), s[2]);
        case 4:
            return createMenuItem(s[0], s[1], s[2].charAt(0), s[3]);
        case 5:
            return createMenuItem(s[0], s[1], s[2].charAt(0), s[3], s[4]);
        }
        return null;
    }
    /**
     * 创建菜单项目
     * @param text String
     * @param action String
     * @return TMenuItem
     */
    public static TMenuItem createMenuItem(String text,String action)
    {
        String tag[] = StringTool.parseLine(text,"|");
        return createMenuItem(tag[0],text,(char)0,action);
    }
    /**
     * 创建菜单项目
     * @param text String
     * @param m char
     * @param action String
     * @return TMenuItem
     */
    public static TMenuItem createMenuItem(String text,char m,String action)
    {
        return createMenuItem(text,text,m,"",action);
    }
    /**
     * 创建菜单项目
     * @param tag String
     * @param text String
     * @param m char
     * @param action String
     * @return TMenuItem
     */
    public static TMenuItem createMenuItem(String tag,String text,char m,String action)
    {
        return createMenuItem(tag,text,m,"",action);
    }
    /**
     * 创建菜单项目
     * @param tag String
     * @param text String
     * @param m char
     * @param pic String
     * @param action String
     * @return TMenuItem
     */
    public static TMenuItem createMenuItem(String tag,String text,char m,String pic,String action)
    {
        TMenuItem menu = new TMenuItem();
        menu.setTag(tag);
        String texts[] = StringTool.parseLine(text,"|");
        if(texts.length == 1)
            menu.setText(text);
        else if(texts.length == 2)
        {
            menu.setZhText(texts[0]);
            menu.setEnText(texts[1]);
        }
        if(m != 0)
            menu.setM(m);
        menu.setActionMessage(action);
        if(pic != null && pic.length() > 0)
            menu.setPictureName(pic);
        return menu;
    }

}
