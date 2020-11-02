package com.dongyang.tui;

import java.awt.Component;
import java.awt.Graphics;
import com.dongyang.util.StringTool;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import com.dongyang.ui.util.TDrawTool;
import com.dongyang.util.TypeTool;

public class DBorder
{
    public static boolean paintBorder(DComponent com,String type, Graphics g)
    {
        if(type == null || type.length() == 0)
            return false;
        String c[] = StringTool.parseLine(type, "|");
        switch(getIndex(c[0]))
        {
        case 1://雕刻
            return paintEtched(com,g,c);
        case 2://线框
            return paintLine(com,g,c);
        case 3://凹陷
            return paintLowered(com,g,c);
        case 4://凸起
            return paintRaised(com,g,c);
        case 5://组框
            return paintTitled(com,g,c);
        case 6://按钮
            return paintButton(com,g,c);
        }
        return false;
    }
    /**
     * 分析类型
     * @param type String
     * @return int
     */
    private static int getIndex(String type)
    {
        if ("Etched".equalsIgnoreCase(type) || "雕刻".equals(type) ||
            "刻".equals(type))
            return 1;
        if ("Line".equalsIgnoreCase(type) || "线框".equals(type) ||
            "线".equals(type))
            return 2;
        if ("LoweredBevel".equalsIgnoreCase(type) || "凹陷".equals(type) ||
            "凹".equals(type))
            return 3;
        if ("RaisedBevel".equalsIgnoreCase(type) || "凸起".equals(type) ||
            "凸".equals(type))
            return 4;
        if ("Titled".equalsIgnoreCase(type) || "组框".equals(type) ||
            "组".equals(type))
            return 5;
        if ("button".equalsIgnoreCase(type)||"按钮".equals(type))
            return 6;
        return 0;
    }
    /**
     * 设置内部尺寸
     * @param com DComponent
     * @param type String
     */
    public static void setInsets(DComponent com,String type)
    {
        DInsets insets = com.getInsets();
        if(type == null || type.length() == 0)
        {
            insets.setInsets(0,0,0,0);
            return;
        }
        String c[] = StringTool.parseLine(type, "|");
        switch(getIndex(c[0]))
        {
        case 1://雕刻
            insets.setInsets(2,2,2,2);
            return;
        case 2://线框
            insets.setInsets(1,1,1,1);
            return;
        case 3://凹陷
            insets.setInsets(2,2,2,2);
            return;
        case 4://凸起
            insets.setInsets(2,2,2,2);
            return;
        case 5://组框
            Font font = c.length > 2?DFont.getFont(c[2]):new Font("宋体",0,12);
            int height = DFont.getFontMetrics(font).getHeight();
            insets.setInsets(height,2,2,2);
            return;
        case 6://按钮
            insets.setInsets(4,4,4,4);
            return;
        }
    }
    /**
     * 蚀刻
     * @param com DComponent
     * @param g Graphics
     * @param s String[]
     * @return boolean
     */
    private static boolean paintEtched(DComponent com,Graphics g,String s[])
    {
        DRectangle r = com.getComponentBounds();
        Color c1 = s.length > 1?DColor.getColor(s[1]):new Color(165,163,151);
        Color c2 = s.length > 2?DColor.getColor(s[2]):new Color(255,255,255);
        g.setColor(c1);
        g.drawRect(0,0,r.getWidth() - 2,r.getHeight() - 2);
        g.setColor(c2);
        g.drawRect(1,1,r.getWidth() - 2,r.getHeight() - 2);
        return true;
    }
    /**
     * 线
     * @param com DComponent
     * @param g Graphics
     * @param s String[]
     * @return boolean
     */
    private static boolean paintLine(DComponent com,Graphics g,String s[])
    {
        DRectangle r = com.getComponentBounds();
        Color c1 = s.length > 1?DColor.getColor(s[1]):new Color(0,0,0);
        g.setColor(c1);
        g.drawRect(0,0,r.getWidth() - 1,r.getHeight() - 1);
        return true;
    }
    /**
     * 凹陷
     * @param com DComponent
     * @param g Graphics
     * @param s String[]
     * @return boolean
     */
    private static boolean paintLowered(DComponent com,Graphics g,String s[])
    {
        DRectangle r = com.getComponentBounds();
        Color c1 = s.length > 1?DColor.getColor(s[1]):new Color(165,163,151);
        Color c2 = s.length > 1?DColor.getColor(s[1]):new Color(255,255,255);
        Color c3 = s.length > 1?DColor.getColor(s[1]):new Color(115,114,105);
        Color c4 = s.length > 1?DColor.getColor(s[1]):new Color(255,255,255);
        g.setColor(c1);
        g.drawLine(0,0,0,r.getHeight() - 1);
        g.drawLine(0,0,r.getWidth() - 1,0);
        g.setColor(c2);
        g.drawLine(1,r.getHeight() - 1,r.getWidth() - 1,r.getHeight() - 1);
        g.drawLine(r.getWidth() - 1,1,r.getWidth() - 1,r.getHeight() - 1);
        g.setColor(c3);
        g.drawLine(1,1,1,r.getHeight() - 2);
        g.drawLine(1,1,r.getWidth() - 2,1);
        g.setColor(c4);
        g.drawLine(2,r.getHeight() - 2,r.getWidth() - 2,r.getHeight() - 2);
        g.drawLine(r.getWidth() - 2,2,r.getWidth() - 2,r.getHeight() - 2);
        return true;
    }
    /**
     * 凸起
     * @param com DComponent
     * @param g Graphics
     * @param s String[]
     * @return boolean
     */
    private static boolean paintRaised(DComponent com,Graphics g,String s[])
    {
        DRectangle r = com.getComponentBounds();
        Color c1 = s.length > 1?DColor.getColor(s[1]):new Color(255,255,255);
        Color c2 = s.length > 1?DColor.getColor(s[1]):new Color(115,114,105);
        Color c3 = s.length > 1?DColor.getColor(s[1]):new Color(255,255,255);
        Color c4 = s.length > 1?DColor.getColor(s[1]):new Color(165,163,151);
        g.setColor(c1);
        g.drawLine(0,0,0,r.getHeight() - 1);
        g.drawLine(0,0,r.getWidth() - 1,0);
        g.setColor(c2);
        g.drawLine(1,r.getHeight() - 1,r.getWidth() - 1,r.getHeight() - 1);
        g.drawLine(r.getWidth() - 1,1,r.getWidth() - 1,r.getHeight() - 1);
        g.setColor(c3);
        g.drawLine(1,1,1,r.getHeight() - 2);
        g.drawLine(1,1,r.getWidth() - 2,1);
        g.setColor(c4);
        g.drawLine(2,r.getHeight() - 2,r.getWidth() - 2,r.getHeight() - 2);
        g.drawLine(r.getWidth() - 2,2,r.getWidth() - 2,r.getHeight() - 2);
        return true;
    }
    /**
     *
     * @param com DComponent
     * @param g Graphics
     * @param s String[]
     * @return boolean
     */
    private static boolean paintTitled(DComponent com,Graphics g,String s[])
    {
        DRectangle r = com.getComponentBounds();
        String title = s.length > 1?s[1]:"";
        Font font = s.length > 2?DFont.getFont(s[2]):new Font("宋体",0,12);
        Color c1 = s.length > 3?DColor.getColor(s[3]):new Color(0,0,0);
        Color c2 = s.length > 4?DColor.getColor(s[4]):new Color(165,163,151);
        Color c3 = s.length > 5?DColor.getColor(s[5]):new Color(255,255,255);

        g.setFont(font);
        FontMetrics fontMetric = g.getFontMetrics(font);
        int width = fontMetric.stringWidth(title);
        int x = 10;
        int fontAscent = fontMetric.getAscent();
        int y = fontAscent;
        g.setColor(c1);
        g.drawString(title,x,y);
        y = fontAscent / 2;

        g.setColor(c2);
        g.drawLine(0,y,x - 2,y);
        if(x + width < r.getWidth() - 2)
        {
            g.drawLine(x + width, y, r.getWidth() - 2, y);
            g.drawLine(r.getWidth() - 2, y, r.getWidth() - 2,
                       r.getHeight() - 2);
        }else
            g.drawLine(r.getWidth() - 2, fontAscent + 2, r.getWidth() - 2,
                       r.getHeight() - 2);
        g.drawLine(0,y,0,r.getHeight() - 2);
        g.drawLine(0,r.getHeight() - 2,r.getWidth() - 2,r.getHeight() - 2);

        g.setColor(c3);
        g.drawLine(1,y + 1,x - 2,y + 1);
        if(x + width < r.getWidth() - 2)
        {
            g.drawLine(x + width, y + 1, r.getWidth() - 1, y + 1);
            g.drawLine(r.getWidth() - 1, y + 1, r.getWidth() - 1,
                       r.getHeight() - 1);
        }else
            g.drawLine(r.getWidth() - 1, fontAscent + 2, r.getWidth() - 1,
                       r.getHeight() - 1);

        g.drawLine(1,y + 1,1,r.getHeight() - 1);

        g.drawLine(1,r.getHeight() - 1,r.getWidth() - 1,r.getHeight() - 1);
        return true;
    }
    private static boolean paintButton(DComponent com,Graphics g,String s[])
    {
        int type = 0;
        if(s.length > 0)
            type = TypeTool.getInt(s[1]);
        return paintButton(com,g,type);
    }
    /**
     * 绘制滚动条按钮样式
     * @param com DComponent
     * @param g Graphics
     * @param type int
     * @return boolean
     */
    public static boolean paintScrollBarButton(DComponent com,Graphics g,int type)
    {
        DRectangle r = com.getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        //底色
        TDrawTool.fillRect(0,0,width - 1,height - 1,new Color(255,255,255),g);
        TDrawTool.fillTransitionH(width - 1,0,1,height - 2,new Color(238,237,229),new Color(160,181,211),g);

        paintScrollBarButtonFocus(com,g,type);
        return true;
    }
    /**
     * 绘制滚动条按钮焦点
     * @param com DComponent
     * @param g Graphics
     * @param type int
     */
    public static void paintScrollBarButtonFocus(DComponent com,Graphics g,int type)
    {
        DRectangle r = com.getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        switch (type)
        {
        case 0://基础
            TDrawTool.fillTransition(2,2,width - 5,height - 5,new Color(225,234,254),
                                     new Color(183,203,249),new Color(188,206,250),
                                     new Color(174,200,247),g);
            TDrawTool.fillTransitionW(2,1,width - 5,1,new Color(208,223,252),new Color(175,197,244),g);
            TDrawTool.fillTransitionW(2,height - 3,width - 5,1,new Color(183,198,241),new Color(176,196,242),g);
            TDrawTool.fillTransitionH(1,2,1,height - 5,new Color(208,223,252),new Color(170,192,243),g);
            TDrawTool.fillTransitionH(width - 3,2,1,height - 5,new Color(185,201,243),new Color(176,197,242),g);

            TDrawTool.fillPoint(1,1,new Color(230,238,252),g);
            TDrawTool.fillPoint(width - 3,1,new Color(220,230,249),g);
            TDrawTool.fillPoint(1,height - 3,new Color(219,227,248),g);
            TDrawTool.fillPoint(width - 3,height - 3,new Color(217,227,246),g);

            TDrawTool.fillPoint(0,height - 2,new Color(195,212,231),g);
            TDrawTool.fillPoint(width - 2,height - 2,new Color(174,196,229),g);
            TDrawTool.fillTransitionW(1,height - 1,width - 4,1,new Color(170,192,225),new Color(130,164,214),g);
            TDrawTool.fillPoint(width - 3,height - 1,new Color(148,176,221),g);
            break;
            case 1:
            case 2://焦点蓝
                TDrawTool.fillTransition(2,2,width - 5,height - 5,new Color(253,255,255),
                                         new Color(197,221,252),new Color(205,226,252),
                                         new Color(185,218,251),g);
                TDrawTool.fillTransitionW(2,1,width - 5,1,new Color(180,199,235),new Color(138,166,225),g);
                TDrawTool.fillTransitionW(2,height - 3,width - 5,1,new Color(147,167,223),new Color(139,166,223),g);
                TDrawTool.fillTransitionH(1,2,1,height - 5,new Color(180,199,235),new Color(135,160,222),g);
                TDrawTool.fillTransitionH(width - 3,2,1,height - 5,new Color(151,171,224),new Color(141,167,221),g);

                TDrawTool.fillPoint(1,1,new Color(207,219,236),g);
                TDrawTool.fillPoint(width - 3,1,new Color(194,207,234),g);
                TDrawTool.fillPoint(1,height - 3,new Color(195,205,231),g);
                TDrawTool.fillPoint(width - 3,height - 3,new Color(190,203,230),g);

                TDrawTool.fillPoint(0,height - 2,new Color(188,211,235),g);
                TDrawTool.fillPoint(width - 2,height - 2,new Color(164,191,230),g);
                TDrawTool.fillTransitionW(1,height - 1,width - 4,1,new Color(170,192,225),new Color(130,164,214),g);
                TDrawTool.fillPoint(width - 3,height - 1,new Color(148,176,221),g);
                break;
            case 3://凹
                TDrawTool.fillTransition(2,2,width - 5,height - 5,new Color(110,142,241),
                                         new Color(141,158,239),new Color(128,150,241),
                                         new Color(210,222,235),g);
                TDrawTool.fillTransitionW(2,1,width - 5,1,new Color(162,172,220),new Color(119,134,217),g);
                TDrawTool.fillTransitionW(2,height - 3,width - 5,1,new Color(183,198,241),new Color(176,196,242),g);
                TDrawTool.fillTransitionH(1,2,1,height - 5,new Color(162,172,220),new Color(170,192,243),g);
                TDrawTool.fillTransitionH(width - 3,2,1,height - 5,new Color(185,201,243),new Color(176,197,242),g);

                TDrawTool.fillPoint(1,1,new Color(187,194,220),g);
                TDrawTool.fillPoint(width - 3,1,new Color(175,182,219),g);
                TDrawTool.fillPoint(1,height - 3,new Color(219,227,248),g);
                TDrawTool.fillPoint(width - 3,height - 3,new Color(217,227,246),g);

                TDrawTool.fillPoint(0,height - 2,new Color(195,212,231),g);
                TDrawTool.fillPoint(width - 2,height - 2,new Color(174,196,229),g);
                TDrawTool.fillTransitionW(1,height - 1,width - 4,1,new Color(170,192,225),new Color(130,164,214),g);
                TDrawTool.fillPoint(width - 3,height - 1,new Color(148,176,221),g);
                break;
        }
    }
    /**
     * 绘制滚动条按钮样式(滚动块)
     * @param com DComponent
     * @param g Graphics
     * @param type int
     * @return boolean
     */
    public static boolean paintScrollBarButtonB(DComponent com,Graphics g,int type)
    {
        DRectangle r = com.getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        //底色
        TDrawTool.fillRect(0,0,width - 1,height - 1,new Color(255,255,255),g);
        TDrawTool.fillTransitionH(width - 1,3,1,height - 6,new Color(159,181,210),new Color(159,181,210),g);
        TDrawTool.fillPoint(width - 1,1,new Color(231,231,227),g);
        TDrawTool.fillPoint(width - 1,2,new Color(198,209,220),g);
        TDrawTool.fillPoint(width - 1,height - 3,new Color(182,196,215),g);
        paintScrollBarButtonFocusB(com,g,type);
        return true;
    }
    /**
     * 绘制滚动条按钮焦点(滚动块)
     * @param com DComponent
     * @param g Graphics
     * @param type int
     */
    public static void paintScrollBarButtonFocusB(DComponent com,Graphics g,int type)
    {
        DRectangle r = com.getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        switch (type)
        {
        case 0://基础
            TDrawTool.fillTransitionW(2,2,width - 4,1,new Color(225,234,254),new Color(185,201,243),g);
            TDrawTool.fillTransitionH(2,3,width - 7,height - 6,new Color(200,214,251),new Color(193,211,251),g);
            TDrawTool.fillTransitionH(width - 5,3,3,height - 6,new Color(193,211,251),new Color(185,203,243),g);
            TDrawTool.fillPoint(1,2,new Color(208,223,252),g);
            TDrawTool.fillRect(1,3,1,height - 6,new Color(183,202,245),g);
            TDrawTool.fillTransitionW(2,1,width - 5,1,new Color(208,223,252),new Color(175,197,244),g);
            TDrawTool.fillTransitionW(2,height - 3,width - 5,1,new Color(183,198,241),new Color(176,196,242),g);

            TDrawTool.fillPoint(1,1,new Color(230,238,252),g);
            TDrawTool.fillPoint(width - 3,1,new Color(220,230,249),g);
            TDrawTool.fillPoint(1,height - 3,new Color(219,227,248),g);
            TDrawTool.fillPoint(width - 3,height - 3,new Color(217,227,246),g);

            TDrawTool.fillPoint(0,height - 2,new Color(195,212,231),g);
            TDrawTool.fillPoint(width - 2,height - 2,new Color(174,196,229),g);
            TDrawTool.fillTransitionW(1,height - 1,width - 4,1,new Color(170,192,225),new Color(130,164,214),g);
            TDrawTool.fillPoint(width - 3,height - 1,new Color(148,176,221),g);
            break;
            case 1:
            case 2://焦点蓝
                TDrawTool.fillTransition(2,2,width - 5,height - 5,new Color(253,255,255),
                                         new Color(197,221,252),new Color(205,226,252),
                                         new Color(185,218,251),g);
                TDrawTool.fillTransitionW(2,1,width - 5,1,new Color(180,199,235),new Color(138,166,225),g);
                TDrawTool.fillTransitionW(2,height - 3,width - 5,1,new Color(147,167,223),new Color(139,166,223),g);
                TDrawTool.fillTransitionH(1,2,1,height - 5,new Color(180,199,235),new Color(135,160,222),g);
                TDrawTool.fillTransitionH(width - 3,2,1,height - 5,new Color(151,171,224),new Color(141,167,221),g);

                TDrawTool.fillPoint(1,1,new Color(207,219,236),g);
                TDrawTool.fillPoint(width - 3,1,new Color(194,207,234),g);
                TDrawTool.fillPoint(1,height - 3,new Color(195,205,231),g);
                TDrawTool.fillPoint(width - 3,height - 3,new Color(190,203,230),g);

                TDrawTool.fillPoint(0,height - 2,new Color(188,211,235),g);
                TDrawTool.fillPoint(width - 2,height - 2,new Color(164,191,230),g);
                TDrawTool.fillTransitionW(1,height - 1,width - 4,1,new Color(170,192,225),new Color(130,164,214),g);
                TDrawTool.fillPoint(width - 3,height - 1,new Color(148,176,221),g);
                break;
            case 3://凹
                TDrawTool.fillTransition(2,2,width - 5,height - 5,new Color(110,142,241),
                                         new Color(141,158,239),new Color(128,150,241),
                                         new Color(210,222,235),g);
                TDrawTool.fillTransitionW(2,1,width - 5,1,new Color(162,172,220),new Color(119,134,217),g);
                TDrawTool.fillTransitionW(2,height - 3,width - 5,1,new Color(183,198,241),new Color(176,196,242),g);
                TDrawTool.fillTransitionH(1,2,1,height - 5,new Color(162,172,220),new Color(170,192,243),g);
                TDrawTool.fillTransitionH(width - 3,2,1,height - 5,new Color(185,201,243),new Color(176,197,242),g);

                TDrawTool.fillPoint(1,1,new Color(187,194,220),g);
                TDrawTool.fillPoint(width - 3,1,new Color(175,182,219),g);
                TDrawTool.fillPoint(1,height - 3,new Color(219,227,248),g);
                TDrawTool.fillPoint(width - 3,height - 3,new Color(217,227,246),g);

                TDrawTool.fillPoint(0,height - 2,new Color(195,212,231),g);
                TDrawTool.fillPoint(width - 2,height - 2,new Color(174,196,229),g);
                TDrawTool.fillTransitionW(1,height - 1,width - 4,1,new Color(170,192,225),new Color(130,164,214),g);
                TDrawTool.fillPoint(width - 3,height - 1,new Color(148,176,221),g);
                break;
        }
    }
    /**
     * 绘制按钮样式
     * @param com DComponent
     * @param g Graphics
     * @param type int
     * @return boolean
     */
    public static boolean paintButton(DComponent com,Graphics g,int type)
    {
        DRectangle r = com.getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        //底色
        TDrawTool.fillRect(0,0,width,height,new Color(229, 224, 207),g);
        //外圈上
        TDrawTool.fillRect(4,1,width - 8,1,new Color(0,60,116),null,null,
                           new Color[]{new Color(37,88,132),new Color(123,149,168)},
                new Color[]{new Color(23,77,126),new Color(103,136,160)},g);
        //外圈下
        TDrawTool.fillRect(4,height - 2,width - 8,1,new Color(0,60,116),null,null,
                           new Color[]{new Color(37,88,132),new Color(125,153,171)},
                new Color[]{new Color(23,77,126),new Color(108,140,165)},g);
        //外圈左
        TDrawTool.fillRect(1,4,1,height - 8,new Color(0,60,116),
                           new Color[]{new Color(37,88,132),new Color(123,149,168)},
                new Color[]{new Color(23,77,126),new Color(103,135,160)},null,null,g);
        //外圈右
        TDrawTool.fillRect(width - 2,4,1,height - 8,new Color(0,60,116),
                           new Color[]{new Color(37,88,132),new Color(123,150,170)},
            new Color[]{new Color(23,77,126),new Color(107,139,163)},null,null,g);
        if(width > 4 && height > 4)
            paintButtonFocus(com,g,type);
        return true;
    }
    /**
     * 绘制按钮焦点
     * @param com DComponent
     * @param g Graphics
     * @param type int
     */
    public static void paintButtonFocus(DComponent com,Graphics g,int type)
    {
        DRectangle r = com.getComponentBounds();
        int width = r.getWidth();
        int height = r.getHeight();
        switch(type)
        {
        case 0://基础
            //内充
            TDrawTool.fillRect(2,6,width - 6,height - 11,new Color(246, 246, 242),g);
            //上1
            TDrawTool.fillRect(4,2,width - 8,1,new Color(255,255,255),null,null,
                               new Color[]{new Color(191,206,220),new Color(85,125,162)},
                    new Color[]{new Color(213,223,232),new Color(85,125,162)},g);
            if(height > 5)
                //上2
                TDrawTool.fillRect(3,3,width - 6,1,new Color(254,254,254),null,null,
                                   new Color[]{new Color(192,207,221)},
                        new Color[]{new Color(191,206,219)},g);

            if(height > 6)
                //上3
                TDrawTool.fillRect(2,4,width - 6,1,new Color(252,252,251),null,null,null,
                                   new Color[]{new Color(251,251,250),new Color(246,244,242)},g);
            if(height > 7)
                //上4
                TDrawTool.fillRect(2,5,width - 6,1,new Color(250,250,249),null,null,null,
                                   new Color[]{new Color(245,244,242),new Color(240,238,234)},g);
            if(height > 8)
                //上5
                TDrawTool.fillRect(2,6,width - 6,1,new Color(248,248,246),null,null,null,
                                   new Color[]{new Color(243,241,238),new Color(237,234,230)},g);
            //下1
            TDrawTool.fillRect(4,height - 3,width - 8,1,new Color(214,208,197),null,null,
                               new Color[]{new Color(163,174,180),new Color(74,113,147)},
                    new Color[]{new Color(177,183,182),new Color(72,110,144)},g);
            if(height > 5)
                //下2
                TDrawTool.fillRect(4,height - 4,width - 8,1,new Color(225,222,213),null,null,
                                   new Color[]{new Color(226,223,214),new Color(191,199,202)},
                        new Color[]{new Color(219,215,205),new Color(182,189,189)},g);
            if(height > 6)
                //下3
                TDrawTool.fillRect(4,height - 5,width - 8,1,new Color(236,235,230),null,null,
                                   new Color[]{new Color(235,235,229),new Color(236,235,230)},
                        new Color[]{new Color(228,226,218),new Color(224,221,212)},g);
            //右1
            TDrawTool.fillRect(width - 3,7,1,height - 12,new Color(233,230,224),g);
            if(width > 5)
                //右2
                TDrawTool.fillRect(width - 4,7,1,height - 12,new Color(238,237,231),g);
            break;
        case 1://焦点黄
            //内充
            TDrawTool.fillRect(4,6,width - 8,height - 11,new Color(246, 246, 242),g);
            //上1
            TDrawTool.fillRect(4,2,width - 8,1,new Color(255,240,207),null,null,
                               new Color[]{new Color(239,232,210),new Color(141,158,161)},
                    new Color[]{new Color(248,237,211),new Color(141,158,161)},g);
            if(height > 5)
                //上2
                TDrawTool.fillRect(4,3,width - 8,1,new Color(253,216,137),null,null,
                                   new Color[]{new Color(252,210,121),new Color(238,220,173)},
                        new Color[]{new Color(252,210,121),new Color(238,220,173)},g);
            if(height > 6)
                //上3
                TDrawTool.fillRect(4,4,width - 8,1,new Color(252,252,251),g);
            if(height > 7)
                //上4
                TDrawTool.fillRect(4,5,width - 8,1,new Color(250,250,249),g);
            if(height > 8)
                //上5
                TDrawTool.fillRect(4,6,width - 8,1,new Color(248,248,246),g);

            //下1
            TDrawTool.fillRect(4,height - 3,width - 8,1,new Color(229,151,0),null,null,
                               new Color[]{new Color(229,151,0),new Color(132,134,113)},
                    new Color[]{new Color(229,151,0),new Color(131,132,111)},g);
            if(height > 5)
                //下2
                TDrawTool.fillRect(4,height - 4,width - 8,1,new Color(248,179,48),g);
            if(height > 6)
                //下3
                TDrawTool.fillRect(4,height - 5,width - 8,1,new Color(236,235,230),g);
            //左1
            TDrawTool.fillTransitionH(2,4,1,height - 7,new Color(254,223,154),new Color(239,181,73),g);
            if(width > 5)
                //左2
                TDrawTool.fillTransitionH(3,4,1,height - 7,new Color(252,210,121),new Color(248,178,48),g);
            //右1
            TDrawTool.fillTransitionH(width - 3,4,1,height - 7,new Color(253,224,155),new Color(237,181,71),g);
            if(width > 5)
                //右2
                TDrawTool.fillTransitionH(width - 4,4,1,height - 7,new Color(252,210,121),new Color(248,179,48),g);
            break;
        case 2://焦点蓝
            //内充
            TDrawTool.fillRect(4,6,width - 8,height - 11,new Color(246, 246, 242),g);
            //上1
            TDrawTool.fillRect(3,2,width - 6,1,new Color(206,231,255),null,null,
                               new Color[]{new Color(39,77,133)},
                    new Color[]{new Color(39,77,133)},g);
            if(height > 5)
                //上2
                TDrawTool.fillRect(2,3,width - 4,1,new Color(188,212,246),g);
            if(height > 6)
                //上3
                TDrawTool.fillRect(4,4,width - 8,1,new Color(252,252,251),g);
            if(height > 7)
                //上4
                TDrawTool.fillRect(4,5,width - 8,1,new Color(250,250,249),g);
            if(height > 8)
                //上5
                TDrawTool.fillRect(4,6,width - 8,1,new Color(248,248,246),g);
            //下1
            TDrawTool.fillRect(4,height - 3,width - 8,1,new Color(105,130,238),null,null,
                               new Color[]{new Color(77,125,193),new Color(39,77,133)},
                    new Color[]{new Color(105,130,238),new Color(39,77,133)},g);
            if(height > 5)
                //下2
                TDrawTool.fillRect(4,height - 4,width - 8,1,new Color(137,173,228),g);
            if(height > 6)
                //下3
                TDrawTool.fillRect(4,height - 5,width - 8,1,new Color(236,235,230),g);
            int size = width > 5?2:1;
            //左1
            TDrawTool.fillTransitionH(2,3,size,height - 6,new Color(188,212,246),new Color(137,173,228),g);
            //右1
            TDrawTool.fillTransitionH(width - 2 - size,3,size,height - 6,new Color(188,212,246),new Color(137,173,228),g);
            break;
        case 3://凹
            //内充
            TDrawTool.fillRect(4,5,width - 8,height - 10,new Color(228, 227, 220),g);
            //上1
            TDrawTool.fillRect(4,2,width - 8,1,new Color(209,204,193),null,null,
                               new Color[]{new Color(157,168,172),new Color(70,109,142)},
                    new Color[]{new Color(177,183,183),new Color(73,112,146)},g);
            if(height > 5)
                //上2
                TDrawTool.fillRect(4,3,width - 8,1,new Color(220,216,207),null,null,
                                   new Color[]{new Color(214,208,198),new Color(160,171,177)},
                        new Color[]{new Color(220,217,208),new Color(169,181,189)},g);
            if(height > 6)
                //上3
                TDrawTool.fillRect(4,4,width - 8,1,new Color(229,228,221),g);
            if(height > 7)
                //上4
                TDrawTool.fillRect(4,5,width - 8,1,new Color(230,230,224),g);

            //下1
            TDrawTool.fillRect(4,height - 3,width - 8,1,new Color(242,241,238),null,null,
                               new Color[]{new Color(163,174,180),new Color(74,113,147)},
                    new Color[]{new Color(202,211,218),new Color(79,119,155)},g);
            if(height > 5)
                //下2
                TDrawTool.fillRect(4,height - 4,width - 8,1,new Color(234,233,227),null,null,
                                   new Color[]{new Color(230,230,222),new Color(188,197,199)},
                        new Color[]{new Color(237,236,231),new Color(196,206,210)},g);
            if(height > 6)
                //下3
                TDrawTool.fillRect(4,height - 5,width - 8,1,new Color(226,226,218),g);
            //左1
            TDrawTool.fillTransitionH(2,4,1,height - 8,new Color(216,212,203),new Color(218,216,207),g);
            if(width > 5)
                //左2
                TDrawTool.fillTransitionH(3,4,1,height - 8,new Color(221,218,209),new Color(223,222,214),g);
            //右1
            TDrawTool.fillTransitionH(width - 3,4,1,height - 8,new Color(229,228,221),new Color(230,229,223),g);
            if(width > 5)
                //右2
                TDrawTool.fillTransitionH(width - 4,4,1,height - 8,new Color(228,227,220),new Color(230,229,223),g);
        }
    }
    /**
     * 绘制滚动条
     * @param com DScrollBar
     * @param g Graphics
     * @param state int
     */
    public static void paintScrollBar(DScrollBar com,Graphics g,int state)
    {
        if(com == null)
            return;
        DRectangle r = com.getComponentBounds();
        int location = com.getBlockLocation();
        int size = com.getBlockSize();

        switch(com.getOrientation())
        {
        case DScrollBar.HORIZONTAL://横向
            TDrawTool.fillRect(0,r.getHeight() - 1,r.getWidth(),1,new Color(238,237,229),g);
            TDrawTool.fillRect(0,0,r.getWidth(),1,new Color(238,237,229),g);
            TDrawTool.fillTransitionH(0,1,r.getWidth(),r.getHeight() - 2,new Color(243,241,236),new Color(254,254,251),g);
            switch(state)
            {
            case 1://焦点蓝区域1
                TDrawTool.fillRect(0,r.getHeight() - 1,location,1,new Color(238,237,229),g);
                TDrawTool.fillRect(0,0,location,1,new Color(238,237,229),g);
                TDrawTool.fillTransitionH(0,1,location,r.getHeight() - 2,new Color(190,208,252),new Color(254,254,251),g);
                break;
            case 2://焦点蓝区域2
                TDrawTool.fillRect(location + size,r.getHeight() - 1,r.getWidth() - location - size,1,new Color(238,237,229),g);
                TDrawTool.fillRect(location + size,0,r.getWidth() - location - size,1,new Color(238,237,229),g);
                TDrawTool.fillTransitionH(location + size,1,r.getWidth() - location - size,r.getHeight() - 2,new Color(190,208,252),new Color(254,254,251),g);
                break;
            case 11://凹区域1
                TDrawTool.fillRect(0,r.getHeight() - 1,location,1,new Color(238,237,229),g);
                TDrawTool.fillRect(0,0,location,1,new Color(238,237,229),g);
                TDrawTool.fillTransitionH(0,1,location,r.getHeight() - 2,new Color(128,164,249),new Color(253,253,246),g);
                break;
            case 12://凹区域2
                TDrawTool.fillRect(location + size,r.getHeight() - 1,r.getWidth() - location - size,1,new Color(238,237,229),g);
                TDrawTool.fillRect(location + size,0,r.getWidth() - location - size,1,new Color(238,237,229),g);
                TDrawTool.fillTransitionH(location + size,1,r.getWidth() - location - size,r.getHeight() - 2,new Color(128,164,249),new Color(253,253,246),g);
                break;
            }
            break;
        case DScrollBar.VERTICAL://纵向
            TDrawTool.fillRect(0,0,1,r.getHeight(),new Color(238,237,229),g);
            TDrawTool.fillRect(r.getWidth() - 1,0,1,r.getHeight(),new Color(238,237,229),g);
            TDrawTool.fillTransitionW(1,0,r.getWidth() - 2,r.getHeight(),new Color(243,241,236),new Color(254,254,251),g);
            switch(state)
            {
            case 1://焦点蓝区域1
                TDrawTool.fillRect(0,0,1,location,new Color(238,237,229),g);
                TDrawTool.fillRect(r.getWidth() - 1,0,1,location,new Color(238,237,229),g);
                TDrawTool.fillTransitionW(1,0,r.getWidth() - 2,location,new Color(190,208,252),new Color(254,254,251),g);
                break;
            case 2://焦点蓝区域2
                TDrawTool.fillRect(0,location + size,1,r.getHeight() - location - size,new Color(238,237,229),g);
                TDrawTool.fillRect(r.getWidth() - 1,location + size,1,r.getHeight() - location - size,new Color(238,237,229),g);
                TDrawTool.fillTransitionW(1,location + size,r.getWidth() - 2,r.getHeight() - location - size,new Color(190,208,252),new Color(254,254,251),g);
                break;
            case 11:////凹区域1
                TDrawTool.fillRect(0,0,1,location,new Color(238,237,229),g);
                TDrawTool.fillRect(r.getWidth() - 1,0,1,location,new Color(238,237,229),g);
                TDrawTool.fillTransitionW(1,0,r.getWidth() - 2,location,new Color(128,164,249),new Color(253,253,246),g);
                break;
            case 12:////凹区域2
                TDrawTool.fillRect(0,location + size,1,r.getHeight() - location - size,new Color(238,237,229),g);
                TDrawTool.fillRect(r.getWidth() - 1,location + size,1,r.getHeight() - location - size,new Color(238,237,229),g);
                TDrawTool.fillTransitionW(1,location + size,r.getWidth() - 2,r.getHeight() - location - size,new Color(128,164,249),new Color(253,253,246),g);
                break;
            }
        }
    }
    /**
     * 绘制菜单样式
     * @param com DMenu
     * @param g Graphics
     * @param type int
     * @return boolean
     */
    public static boolean paintMenu(DMenu com,Graphics g,int type)
    {
        DRectangle r = com.getComponentBounds();
        switch(type)
        {
            case 0://基础
                if(com.parentIsPopupMenu())
                {
                    TDrawTool.fillTransitionW(0,0,22,r.getHeight(),new Color(227,239,255),
                                              new Color(136,174,228),g);
                }
                break;
            case 1:
                break;
            case 3:
                if(com.parentIsMenuBar())
                {
                    TDrawTool.drawRect(0,0,r.getWidth(),r.getHeight(),new Color(0,45,150),g);
                    TDrawTool.fillTransitionH(1,1,r.getWidth() - 2,r.getHeight() - 2,new Color(227,239,255),
                                             new Color(152,185,232),g);
                    break;
                }
            case 2://焦点蓝
                TDrawTool.drawRect(0,0,r.getWidth(),r.getHeight(),new Color(0,0,128),g);
                TDrawTool.fillTransitionH(1,1,r.getWidth() - 2,r.getHeight() - 2,new Color(255,244,204),
                                         new Color(255,215,157),g);
                break;

        }
        return true;
    }
    /**
     * 绘制菜单样式
     * @param com DMenuItem
     * @param g Graphics
     * @param type int
     * @return boolean
     */
    public static boolean paintMenuItem(DMenuItem com,Graphics g,int type)
    {
        if(com == null)
            return false;
        DRectangle r = com.getComponentBounds();
        switch(type)
        {
            case 0://基础
                if(com.parentIsPopupMenu())
                {
                    TDrawTool.fillTransitionW(0,0,22,r.getHeight(),new Color(227,239,255),
                                              new Color(136,174,228),g);
                }
                break;
            case 1:
                break;
            case 3:
                //if(com.parentIsMenuBar())
                {
                    TDrawTool.drawRect(0,0,r.getWidth(),r.getHeight(),new Color(0,45,150),g);
                    TDrawTool.fillTransitionH(1,1,r.getWidth() - 2,r.getHeight() - 2,new Color(227,239,255),
                                             new Color(152,185,232),g);
                    break;
                }
            case 2://焦点蓝
                TDrawTool.drawRect(0,0,r.getWidth(),r.getHeight(),new Color(0,0,128),g);
                TDrawTool.fillTransitionH(1,1,r.getWidth() - 2,r.getHeight() - 2,new Color(255,244,204),
                                         new Color(255,215,157),g);
                break;

        }
        return true;
    }
}
