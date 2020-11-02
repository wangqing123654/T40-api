package com.dongyang.tui;

import java.awt.Font;
import com.dongyang.util.StringTool;
import java.awt.FontMetrics;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * <p>Title: ����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.12
 * @version 1.0
 */
public class DFont
{
    private static JLabel label = new JLabel();
    /**
     * �õ�����
     * @param font String
     * @return Font
     */
    public static Font getFont(String font)
    {
        if(font == null || font.length() == 0)
            return new Font("����",0,12);
        String c[] = StringTool.parseLine(font, ",");
        String name = c.length > 0?c[0]:"����";
        int style = c.length > 1?getStyle(c[1]):0;
        int size = c.length > 2?StringTool.getInt(c[2]):14;
        return new Font(name,style,size);
    }
    /**
     * �õ����崦�����
     * @param font Font
     * @return FontMetrics
     */
    public static FontMetrics getFontMetrics(Font font)
    {
        return label.getFontMetrics(font);
    }
    /**
     * �õ�����
     * @param s String
     * @return int
     */
    public static int getStyle(String s)
    {
        if(s.length() == 0)
            return 0;
        if("1".equals(s)||"B".equalsIgnoreCase(s)||"BOLD".equalsIgnoreCase(s)||"����".equals(s)||"��".equals(s))
            return Font.BOLD;
        if("2".equals(s)||"I".equalsIgnoreCase(s)||"ITALIC".equalsIgnoreCase(s)||"б��".equals(s)||"б".equals(s))
            return Font.ITALIC;
        if("3".equals(s)||"BI".equalsIgnoreCase(s)||"IB".equalsIgnoreCase(s)||"��б".equals(s)||"б��".equals(s))
            return Font.BOLD | Font.ITALIC;
        return 0;
    }
}
