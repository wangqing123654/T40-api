package com.dongyang.tui;

import java.awt.Font;
import com.dongyang.util.StringTool;
import java.awt.FontMetrics;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * <p>Title: 字体</p>
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
     * 得到字体
     * @param font String
     * @return Font
     */
    public static Font getFont(String font)
    {
        if(font == null || font.length() == 0)
            return new Font("宋体",0,12);
        String c[] = StringTool.parseLine(font, ",");
        String name = c.length > 0?c[0]:"宋体";
        int style = c.length > 1?getStyle(c[1]):0;
        int size = c.length > 2?StringTool.getInt(c[2]):14;
        return new Font(name,style,size);
    }
    /**
     * 得到字体处理对象
     * @param font Font
     * @return FontMetrics
     */
    public static FontMetrics getFontMetrics(Font font)
    {
        return label.getFontMetrics(font);
    }
    /**
     * 得到类型
     * @param s String
     * @return int
     */
    public static int getStyle(String s)
    {
        if(s.length() == 0)
            return 0;
        if("1".equals(s)||"B".equalsIgnoreCase(s)||"BOLD".equalsIgnoreCase(s)||"粗体".equals(s)||"粗".equals(s))
            return Font.BOLD;
        if("2".equals(s)||"I".equalsIgnoreCase(s)||"ITALIC".equalsIgnoreCase(s)||"斜体".equals(s)||"斜".equals(s))
            return Font.ITALIC;
        if("3".equals(s)||"BI".equalsIgnoreCase(s)||"IB".equalsIgnoreCase(s)||"粗斜".equals(s)||"斜粗".equals(s))
            return Font.BOLD | Font.ITALIC;
        return 0;
    }
}
