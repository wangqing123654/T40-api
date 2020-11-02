package com.dongyang.tui;

import com.dongyang.util.StringTool;
import java.util.Map;
import java.util.HashMap;
import java.awt.Color;
import javax.swing.JColorChooser;
import com.dongyang.ui.TComponent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;

/**
 * ÑÕÉ«Àà
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.11
 * @version 1.0
 */
public class DColor
{
    /**
     * ¹ÌÓÐÑÕÉ«ÁÐ±í
     */
    private static Map mapColor;
    /**
     * µÃµ½ÑÕÉ«
     * @param color String
     * @return Color
     */
    public static Color getColor(String color)
    {
        if(color == null || color.length() == 0 || "null".equalsIgnoreCase(color) || "Í¸Ã÷".equalsIgnoreCase(color))
            return null;
        int index = color.indexOf(",");
        if(index > 0)
            return StringTool.getColor(color);
        int c[] = getIntColor(color);
        if(c == null)
            return null;
        return new Color(c[0],c[1],c[2]);
    }

    /**
     * µÃµ½ÈýÔ­É«
     * @param color String
     * @return int[]
     */
    public static int[] getRGBColor(String color)
    {
        if(color == null || color.length() == 0 || "null".equalsIgnoreCase(color) || "Í¸Ã÷".equalsIgnoreCase(color))
            return null;
        String c[] = StringTool.parseLine(color, ",");
        if (c.length < 3)
            return null;
        return new int[]{StringTool.getInt(c[0]), StringTool.getInt(c[1]), StringTool.getInt(c[2])};
    }
    /**
     * µÃµ½¹ÌÓÐÑÕÉ«
     * @param s String
     * @return int[]
     */
    public static int[] getIntColor(String s)
    {
        return (int[])mapColor.get(s);
    }
    /**
     * ÑÕÉ«¶Ô»°¿ò
     * @param com TComponent
     * @param color Color
     * @return Color
     */
    public static Color colorDialog(TComponent com,Color color)
    {
        final JColorChooser c = new JColorChooser(color);
        JColorChooser.createDialog((Component)com,"Ñ¡ÔñÑÕÉ«",true,c,
                                   new ActionListener()
        {
          public void actionPerformed(ActionEvent e1)
          {
          }
        },new ActionListener()
        {
          public void actionPerformed(ActionEvent e1)
          {
          }
        }
        ).setVisible(true);
        return c.getColor();
    }
    static{
        mapColor = new HashMap();

        mapColor.put("null",null);
        mapColor.put("Í¸Ã÷",null);

        mapColor.put("ºÚ",new int[]{0,0,0});
        mapColor.put("ºÚÉ«",mapColor.get("ºÚ"));
        mapColor.put("BLACK",mapColor.get("ºÚ"));

        mapColor.put("ºÖ",new int[]{153,51,0});
        mapColor.put("ºÖÉ«",mapColor.get("ºÖ"));
        mapColor.put("BROWN",mapColor.get("ºÖ"));

        mapColor.put("éÏé­",new int[]{51,51,0});
        mapColor.put("éÏé­É«",mapColor.get("éÏé­"));
        mapColor.put("OLIVE",mapColor.get("éÏé­"));

        mapColor.put("ÉîÂÌ",new int[]{0,51,0});
        mapColor.put("ÉîÂÌÉ«",mapColor.get("ÉîÂÌ"));
        mapColor.put("BOTTLE_GREEN",mapColor.get("ÉîÂÌ"));

        mapColor.put("ÉîÇà",new int[]{0,51,102});
        mapColor.put("ÉîÇàÉ«",mapColor.get("ÉîÇà"));
        mapColor.put("BOTTLE_CYAN",mapColor.get("ÉîÇà"));

        mapColor.put("ÉîÀ¶",new int[]{0,0,128});
        mapColor.put("ÉîÀ¶É«",mapColor.get("ÉîÀ¶"));
        mapColor.put("BOTTLE_BLUE",mapColor.get("ÉîÀ¶"));

        mapColor.put("µåÀ¶",new int[]{51,51,153});
        mapColor.put("µåÀ¶É«",mapColor.get("µåÀ¶"));
        mapColor.put("INDIGO_BLUE",mapColor.get("µåÀ¶"));

        mapColor.put("»Ò80",new int[]{51,51,51});
        mapColor.put("»ÒÉ«80",mapColor.get("»Ò80"));
        mapColor.put("GRAY80",mapColor.get("»Ò80"));

        mapColor.put("Éîºì",new int[]{128,0,0});
        mapColor.put("ÉîºìÉ«",mapColor.get("Éîºì"));
        mapColor.put("CARMINE",mapColor.get("Éîºì"));

        mapColor.put("³È",new int[]{255,102,0});
        mapColor.put("³ÈÉ«",mapColor.get("³È"));
        mapColor.put("SALMON_PINK",mapColor.get("³È"));

        mapColor.put("Éî»Æ",new int[]{128,128,0});
        mapColor.put("Éî»ÆÉ«",mapColor.get("Éî»Æ"));
        mapColor.put("BOTTLE_YELLOW",mapColor.get("Éî»Æ"));

        mapColor.put("ÂÌ",new int[]{0,128,0});
        mapColor.put("ÂÌÉ«",mapColor.get("ÂÌÉ«"));
        mapColor.put("BREEN",mapColor.get("ÂÌÉ«"));

        mapColor.put("Çà",new int[]{0,128,128});
        mapColor.put("ÇàÉ«",mapColor.get("Çà"));
        mapColor.put("CYAN",mapColor.get("Çà"));

        mapColor.put("À¶",new int[]{0,0,255});
        mapColor.put("À¶É«",mapColor.get("À¶"));
        mapColor.put("BLUE",mapColor.get("À¶"));

        mapColor.put("À¶»Ò",new int[]{102,102,153});
        mapColor.put("À¶»ÒÉ«",mapColor.get("À¶»Ò"));
        mapColor.put("BLUE_GRAY",mapColor.get("À¶»Ò"));

        mapColor.put("»Ò50",new int[]{128,128,128});
        mapColor.put("»ÒÉ«50",mapColor.get("»Ò50"));
        mapColor.put("GRAY50",mapColor.get("»Ò50"));

        mapColor.put("ºì",new int[]{255,0,0});
        mapColor.put("ºìÉ«",mapColor.get("ºì"));
        mapColor.put("RED",mapColor.get("ºì"));

        mapColor.put("µ­³Èºì",new int[]{255,153,0});
        mapColor.put("µ­³ÈºìÉ«",mapColor.get("µ­³Èºì"));
        mapColor.put("FICELL",mapColor.get("µ­³Èºì"));

        mapColor.put("Ëá³Èºì",new int[]{153,204,0});
        mapColor.put("Ëá³ÈºìÉ«",mapColor.get("Ëá³Èºì"));
        mapColor.put("LIME_RED",mapColor.get("Ëá³Èºì"));

        mapColor.put("º£ÂÌ",new int[]{51,153,102});
        mapColor.put("º£ÂÌÉ«",mapColor.get("º£ÂÌ"));
        mapColor.put("SEA_GREEN",mapColor.get("º£ÂÌ"));

        mapColor.put("Ë®ÂÌ",new int[]{51,204,204});
        mapColor.put("Ë®ÂÌÉ«",mapColor.get("Ë®ÂÌ"));
        mapColor.put("WATER_GREEN",mapColor.get("Ë®ÂÌ"));

        mapColor.put("Ç³À¶",new int[]{51,102,255});
        mapColor.put("Ç³À¶É«",mapColor.get("Ç³À¶"));
        mapColor.put("CAMBRIDGE_BLUE",mapColor.get("Ç³À¶"));

        mapColor.put("×ÏÂÞÀ¼",new int[]{128,0,128});
        mapColor.put("×ÏÂÞÀ¼É«",mapColor.get("×ÏÂÞÀ¼"));
        mapColor.put("VIOLET",mapColor.get("×ÏÂÞÀ¼"));

        mapColor.put("»Ò40",new int[]{153,153,153});
        mapColor.put("»ÒÉ«40",mapColor.get("»Ò40"));
        mapColor.put("VIOLET",mapColor.get("»Ò40"));

        mapColor.put("·Ûºì",new int[]{255,0,255});
        mapColor.put("·ÛºìÉ«",mapColor.get("·Ûºì"));
        mapColor.put("CARNATION",mapColor.get("·Ûºì"));

        mapColor.put("½ð",new int[]{255,204,0});
        mapColor.put("½ðÉ«",mapColor.get("½ð"));
        mapColor.put("GOLD",mapColor.get("½ð"));

        mapColor.put("»Æ",new int[]{255,255,0});
        mapColor.put("»ÆÉ«",mapColor.get("»Æ"));
        mapColor.put("YELLOW",mapColor.get("»Æ"));

        mapColor.put("ÏÊÂÌ",new int[]{0,255,0});
        mapColor.put("ÏÊÂÌÉ«",mapColor.get("ÏÊÂÌ"));
        mapColor.put("EMERALD_GREEN",mapColor.get("ÏÊÂÌ"));

        mapColor.put("ÇàÂÌ",new int[]{0,255,255});
        mapColor.put("ÇàÂÌÉ«",mapColor.get("ÇàÂÌ"));
        mapColor.put("TURQUOISE",mapColor.get("ÇàÂÌ"));

        mapColor.put("ÌìÀ¶",new int[]{0,204,255});
        mapColor.put("ÌìÀ¶É«",mapColor.get("ÌìÀ¶"));
        mapColor.put("SKY_BLUE",mapColor.get("ÌìÀ¶"));

        mapColor.put("Ã·ºì",new int[]{153,51,102});
        mapColor.put("Ã·ºìÉ«",mapColor.get("Ã·ºì"));
        mapColor.put("CLUB",mapColor.get("Ã·ºì"));

        mapColor.put("»Ò25",new int[]{192,192,192});
        mapColor.put("»ÒÉ«25",mapColor.get("»Ò25"));
        mapColor.put("GRAY25",mapColor.get("»Ò25"));

        mapColor.put("Ãµ¹åºì",new int[]{255,153,204});
        mapColor.put("Ãµ¹åºìÉ«",mapColor.get("Ãµ¹åºì"));
        mapColor.put("ROSE",mapColor.get("Ãµ¹åºì"));

        mapColor.put("²è",new int[]{255,204,153});
        mapColor.put("²èÉ«",mapColor.get("²è"));
        mapColor.put("TAWNY",mapColor.get("²è"));

        mapColor.put("µ­»Æ",new int[]{255,255,153});
        mapColor.put("µ­»ÆÉ«",mapColor.get("µ­»Æ"));
        mapColor.put("PRIMROSE_YELLOW",mapColor.get("µ­»Æ"));

        mapColor.put("µ­ÂÌ",new int[]{204,255,204});
        mapColor.put("µ­ÂÌÉ«",mapColor.get("µ­ÂÌ"));
        mapColor.put("PEA_GREEN",mapColor.get("µ­ÂÌ"));

        mapColor.put("µ­ÇàÂÌ",new int[]{204,255,255});
        mapColor.put("µ­ÇàÂÌÉ«",mapColor.get("µ­ÇàÂÌ"));
        mapColor.put("BABY_TURQUOISE",mapColor.get("µ­ÇàÂÌ"));

        mapColor.put("µ­À¶",new int[]{153,204,255});
        mapColor.put("µ­À¶É«",mapColor.get("µ­À¶"));
        mapColor.put("BABY_BLUE",mapColor.get("µ­À¶"));

        mapColor.put("µ­×Ï",new int[]{204,153,255});
        mapColor.put("µ­×ÏÉ«",mapColor.get("µ­×Ï"));
        mapColor.put("HELIOTROPE",mapColor.get("µ­×Ï"));

        mapColor.put("°×",new int[]{255,255,255});
        mapColor.put("°×É«",mapColor.get("°×"));
        mapColor.put("WHITE",mapColor.get("°×"));
    }
}
