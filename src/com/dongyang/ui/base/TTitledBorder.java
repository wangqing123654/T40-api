package com.dongyang.ui.base;

import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import com.dongyang.util.StringTool;

public class TTitledBorder extends TitledBorder
{
    private String language;
    public TTitledBorder(Border border, String title) {
        super(border,title);
    }
    public String getTitle()
    {
        if(title == null || title.length() == 0)
            return "";
        String c[] = StringTool.parseLine(title, "|");
        if(c.length == 1)
            return title;
        if("en".equals(getLanguage()) && c.length == 2)
            return c[1];
        return c[0];
    }
    public void setLanguage(String language)
    {
        this.language = language;
    }
    public String getLanguage()
    {
        return language;
    }
}
