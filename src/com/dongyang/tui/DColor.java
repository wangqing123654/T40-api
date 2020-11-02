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
 * ��ɫ��
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
     * ������ɫ�б�
     */
    private static Map mapColor;
    /**
     * �õ���ɫ
     * @param color String
     * @return Color
     */
    public static Color getColor(String color)
    {
        if(color == null || color.length() == 0 || "null".equalsIgnoreCase(color) || "͸��".equalsIgnoreCase(color))
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
     * �õ���ԭɫ
     * @param color String
     * @return int[]
     */
    public static int[] getRGBColor(String color)
    {
        if(color == null || color.length() == 0 || "null".equalsIgnoreCase(color) || "͸��".equalsIgnoreCase(color))
            return null;
        String c[] = StringTool.parseLine(color, ",");
        if (c.length < 3)
            return null;
        return new int[]{StringTool.getInt(c[0]), StringTool.getInt(c[1]), StringTool.getInt(c[2])};
    }
    /**
     * �õ�������ɫ
     * @param s String
     * @return int[]
     */
    public static int[] getIntColor(String s)
    {
        return (int[])mapColor.get(s);
    }
    /**
     * ��ɫ�Ի���
     * @param com TComponent
     * @param color Color
     * @return Color
     */
    public static Color colorDialog(TComponent com,Color color)
    {
        final JColorChooser c = new JColorChooser(color);
        JColorChooser.createDialog((Component)com,"ѡ����ɫ",true,c,
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
        mapColor.put("͸��",null);

        mapColor.put("��",new int[]{0,0,0});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("BLACK",mapColor.get("��"));

        mapColor.put("��",new int[]{153,51,0});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("BROWN",mapColor.get("��"));

        mapColor.put("���",new int[]{51,51,0});
        mapColor.put("���ɫ",mapColor.get("���"));
        mapColor.put("OLIVE",mapColor.get("���"));

        mapColor.put("����",new int[]{0,51,0});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("BOTTLE_GREEN",mapColor.get("����"));

        mapColor.put("����",new int[]{0,51,102});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("BOTTLE_CYAN",mapColor.get("����"));

        mapColor.put("����",new int[]{0,0,128});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("BOTTLE_BLUE",mapColor.get("����"));

        mapColor.put("����",new int[]{51,51,153});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("INDIGO_BLUE",mapColor.get("����"));

        mapColor.put("��80",new int[]{51,51,51});
        mapColor.put("��ɫ80",mapColor.get("��80"));
        mapColor.put("GRAY80",mapColor.get("��80"));

        mapColor.put("���",new int[]{128,0,0});
        mapColor.put("���ɫ",mapColor.get("���"));
        mapColor.put("CARMINE",mapColor.get("���"));

        mapColor.put("��",new int[]{255,102,0});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("SALMON_PINK",mapColor.get("��"));

        mapColor.put("���",new int[]{128,128,0});
        mapColor.put("���ɫ",mapColor.get("���"));
        mapColor.put("BOTTLE_YELLOW",mapColor.get("���"));

        mapColor.put("��",new int[]{0,128,0});
        mapColor.put("��ɫ",mapColor.get("��ɫ"));
        mapColor.put("BREEN",mapColor.get("��ɫ"));

        mapColor.put("��",new int[]{0,128,128});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("CYAN",mapColor.get("��"));

        mapColor.put("��",new int[]{0,0,255});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("BLUE",mapColor.get("��"));

        mapColor.put("����",new int[]{102,102,153});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("BLUE_GRAY",mapColor.get("����"));

        mapColor.put("��50",new int[]{128,128,128});
        mapColor.put("��ɫ50",mapColor.get("��50"));
        mapColor.put("GRAY50",mapColor.get("��50"));

        mapColor.put("��",new int[]{255,0,0});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("RED",mapColor.get("��"));

        mapColor.put("���Ⱥ�",new int[]{255,153,0});
        mapColor.put("���Ⱥ�ɫ",mapColor.get("���Ⱥ�"));
        mapColor.put("FICELL",mapColor.get("���Ⱥ�"));

        mapColor.put("��Ⱥ�",new int[]{153,204,0});
        mapColor.put("��Ⱥ�ɫ",mapColor.get("��Ⱥ�"));
        mapColor.put("LIME_RED",mapColor.get("��Ⱥ�"));

        mapColor.put("����",new int[]{51,153,102});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("SEA_GREEN",mapColor.get("����"));

        mapColor.put("ˮ��",new int[]{51,204,204});
        mapColor.put("ˮ��ɫ",mapColor.get("ˮ��"));
        mapColor.put("WATER_GREEN",mapColor.get("ˮ��"));

        mapColor.put("ǳ��",new int[]{51,102,255});
        mapColor.put("ǳ��ɫ",mapColor.get("ǳ��"));
        mapColor.put("CAMBRIDGE_BLUE",mapColor.get("ǳ��"));

        mapColor.put("������",new int[]{128,0,128});
        mapColor.put("������ɫ",mapColor.get("������"));
        mapColor.put("VIOLET",mapColor.get("������"));

        mapColor.put("��40",new int[]{153,153,153});
        mapColor.put("��ɫ40",mapColor.get("��40"));
        mapColor.put("VIOLET",mapColor.get("��40"));

        mapColor.put("�ۺ�",new int[]{255,0,255});
        mapColor.put("�ۺ�ɫ",mapColor.get("�ۺ�"));
        mapColor.put("CARNATION",mapColor.get("�ۺ�"));

        mapColor.put("��",new int[]{255,204,0});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("GOLD",mapColor.get("��"));

        mapColor.put("��",new int[]{255,255,0});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("YELLOW",mapColor.get("��"));

        mapColor.put("����",new int[]{0,255,0});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("EMERALD_GREEN",mapColor.get("����"));

        mapColor.put("����",new int[]{0,255,255});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("TURQUOISE",mapColor.get("����"));

        mapColor.put("����",new int[]{0,204,255});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("SKY_BLUE",mapColor.get("����"));

        mapColor.put("÷��",new int[]{153,51,102});
        mapColor.put("÷��ɫ",mapColor.get("÷��"));
        mapColor.put("CLUB",mapColor.get("÷��"));

        mapColor.put("��25",new int[]{192,192,192});
        mapColor.put("��ɫ25",mapColor.get("��25"));
        mapColor.put("GRAY25",mapColor.get("��25"));

        mapColor.put("õ���",new int[]{255,153,204});
        mapColor.put("õ���ɫ",mapColor.get("õ���"));
        mapColor.put("ROSE",mapColor.get("õ���"));

        mapColor.put("��",new int[]{255,204,153});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("TAWNY",mapColor.get("��"));

        mapColor.put("����",new int[]{255,255,153});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("PRIMROSE_YELLOW",mapColor.get("����"));

        mapColor.put("����",new int[]{204,255,204});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("PEA_GREEN",mapColor.get("����"));

        mapColor.put("������",new int[]{204,255,255});
        mapColor.put("������ɫ",mapColor.get("������"));
        mapColor.put("BABY_TURQUOISE",mapColor.get("������"));

        mapColor.put("����",new int[]{153,204,255});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("BABY_BLUE",mapColor.get("����"));

        mapColor.put("����",new int[]{204,153,255});
        mapColor.put("����ɫ",mapColor.get("����"));
        mapColor.put("HELIOTROPE",mapColor.get("����"));

        mapColor.put("��",new int[]{255,255,255});
        mapColor.put("��ɫ",mapColor.get("��"));
        mapColor.put("WHITE",mapColor.get("��"));
    }
}
