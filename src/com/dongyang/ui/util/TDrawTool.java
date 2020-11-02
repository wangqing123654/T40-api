package com.dongyang.ui.util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.FontMetrics;
import com.dongyang.tui.DAlignment;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Composite;
import com.dongyang.tui.DRectangle;
import com.dongyang.tui.DScrollBar;
import com.dongyang.tui.DInsets;
import com.dongyang.tui.DSize;
import com.dongyang.tui.DFont;
import java.awt.BasicStroke;

/**
 *
 * <p>Title: 绘图工具类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.16
 * @version 1.0
 */
public class TDrawTool
{
    /**
     * 画下拉按钮
     * @param g Graphics
     * @param x int
     * @param y int
     * @param w int
     * @param h int
     * @param type int
     */
    public static void paintDownButton(Graphics g,int x,int y,int w,int h,int type)
    {
        g.setColor(new Color(0,0,0));
        switch(type)
        {
        case 0:
            fillTransition(x + 1, y + 1, w - 2, h - 2,
                           new Color(225, 234, 254),
                           new Color(188, 206, 250),
                           new Color(183, 203, 249),
                           new Color(174, 200, 247), g);
            fillTransitionW(x + 1, y, w - 2, 1,
                            new Color(209, 224, 253),
                            new Color(175, 197, 244), g);
            fillTransitionW(x + 1, y + h - 1, w - 2, 1,
                            new Color(183, 198, 241),
                            new Color(176, 197, 242), g);
            fillTransitionH(x, y + 1, 1, h - 2,
                            new Color(209, 224, 253),
                            new Color(171, 193, 244), g);
            fillTransitionH(x + w - 1, y + 1, 1, h - 2,
                            new Color(185, 201, 243),
                            new Color(176, 197, 242), g);
            fillPoint(x, y, new Color(230, 238, 252), g);
            fillPoint(x + w - 1, y, new Color(220, 230, 249), g);
            fillPoint(x, y + h - 1, new Color(219, 227, 248), g);
            fillPoint(x + w - 1, y + h - 1, new Color(217, 227, 246), g);
            break;
        case 1:
            fillTransition(x + 1, y + 1, w - 2, h - 2,
                           new Color(253, 255, 255),
                           new Color(205, 226, 252),
                           new Color(197, 221, 252),
                           new Color(185, 218, 251), g);
            fillTransitionW(x + 1, y, w - 2, 1,
                            new Color(180, 199, 235),
                            new Color(138, 166, 227), g);
            fillTransitionW(x + 1, y + h - 1, w - 2, 1,
                            new Color(147, 167, 223),
                            new Color(140, 167, 222), g);
            fillTransitionH(x, y + 1, 1, h - 2,
                            new Color(180, 199, 235),
                            new Color(135, 160, 222), g);
            fillTransitionH(x + w - 1, y + 1, 1, h - 2,
                            new Color(151, 172, 224),
                            new Color(140, 167, 222), g);
            fillPoint(x, y, new Color(207, 219, 236), g);
            fillPoint(x + w - 1, y, new Color(194, 207, 234), g);
            fillPoint(x, y + h - 1, new Color(195, 205, 231), g);
            fillPoint(x + w - 1, y + h - 1, new Color(190, 203, 230), g);
            break;
        case 2:
            fillTransition(x + 1, y + 1, w - 2, h - 2,
                           new Color(110, 142, 241),
                           new Color(128, 150, 241),
                           new Color(141, 158, 239),
                           new Color(210, 222, 235), g);
            fillTransitionW(x + 1, y, w - 2, 1,
                            new Color(162, 172, 220),
                            new Color(119, 134, 217), g);
            fillTransitionW(x + 1, y + h - 1, w - 2, 1,
                            new Color(183, 198, 241),
                            new Color(176, 197, 242), g);
            fillTransitionH(x, y + 1, 1, h - 2,
                            new Color(162, 172, 220),
                            new Color(115, 129, 217), g);
            fillTransitionH(x + w - 1, y + 1, 1, h - 2,
                            new Color(185, 201, 243),
                            new Color(176, 197, 242), g);
            fillPoint(x, y, new Color(187, 194, 220), g);
            fillPoint(x + w - 1, y, new Color(175, 182, 219), g);
            fillPoint(x, y + h - 1, new Color(219, 227, 248), g);
            fillPoint(x + w - 1, y + h - 1, new Color(217, 227, 246), g);
            break;
        }
        drawV(x + w / 2,y + h / 2,g);
    }
    /**
     * 画下箭头
     * @param x int
     * @param y int
     * @param g Graphics
     */
    public static void drawV(int x,int y,Graphics g)
    {
        g.setColor(new Color(77,97,133));
        g.drawLine(x - 3,y - 2, x, y + 1);
        g.drawLine(x + 3,y - 2, x, y + 1);
        g.drawLine(x - 3,y - 1, x, y + 2);
        g.drawLine(x + 3,y - 1, x, y + 2);
        g.drawLine(x - 4,y - 1, x, y + 3);
        g.drawLine(x + 4,y - 1, x, y + 3);
    }
    public static void drawV1(DRectangle r,int type,int state,Graphics g)
    {
        switch(type)
        {
        case DScrollBar.VERTICAL:
            if(r.getHeight() < 17)
                return;
            int x = r.getWidth() / 2;
            int y = r.getHeight() / 2;
            if(state == 0)
                g.setColor(new Color(238,244,254));
            else
            {
                g.setColor(new Color(207, 221, 253));
                x ++;
                y ++;
            }
            g.drawLine(x - 4,y - 4, x + 1, y - 4);
            g.drawLine(x - 4,y - 2, x + 1, y - 2);
            g.drawLine(x - 4,y, x + 1, y);
            g.drawLine(x - 4,y + 2, x + 1, y + 2);
            if(state == 0)
                g.setColor(new Color(140,176,248));
            else
                g.setColor(new Color(131,158,216));
            g.drawLine(x - 3,y - 3, x + 2, y - 3);
            g.drawLine(x - 3,y - 1, x + 2, y - 1);
            g.drawLine(x - 3,y + 1, x + 2, y + 1);
            g.drawLine(x - 3,y + 3, x + 2, y + 3);
            break;
        case DScrollBar.HORIZONTAL:
            if(r.getWidth() < 17)
                return;
            x = r.getWidth() / 2;
            y = r.getHeight() / 2;
            if(state == 0)
                g.setColor(new Color(238,244,254));
            else
            {
                g.setColor(new Color(207, 221, 253));
                x ++;
                y ++;
            }
            g.drawLine(x - 4,y - 4, x - 4, y + 1);
            g.drawLine(x - 2,y - 4, x - 2, y + 1);
            g.drawLine(x,y - 4, x, y + 1);
            g.drawLine(x + 2,y - 4, x + 2, y + 1);
            if(state == 0)
                g.setColor(new Color(140,176,248));
            else
                g.setColor(new Color(131,158,216));
            g.drawLine(x - 3,y - 3, x - 3, y + 2);
            g.drawLine(x - 1,y - 3, x - 1, y + 2);
            g.drawLine(x + 1,y - 3, x + 1, y + 2);
            g.drawLine(x + 3,y - 3, x + 3, y + 2);
            break;
        }
    }
    /**
     * 绘制箭头
     * @param x int
     * @param y int
     * @param type int
     * @param g Graphics
     */
    public static void drawV(int x,int y,int type,Graphics g)
    {
        g.setColor(new Color(77,97,133));
        switch(type)
        {
        case 1://上
            g.drawLine(x - 4,y + 1, x, y - 3);
            g.drawLine(x + 4,y + 1, x, y - 3);
            g.drawLine(x - 3,y + 1, x, y - 2);
            g.drawLine(x + 3,y + 1, x, y - 2);
            g.drawLine(x - 3,y + 2, x, y - 1);
            g.drawLine(x + 3,y + 2, x, y - 1);
            break;
        case 2://下
            g.drawLine(x - 3,y - 2, x, y + 1);
            g.drawLine(x + 3,y - 2, x, y + 1);
            g.drawLine(x - 3,y - 1, x, y + 2);
            g.drawLine(x + 3,y - 1, x, y + 2);
            g.drawLine(x - 4,y - 1, x, y + 3);
            g.drawLine(x + 4,y - 1, x, y + 3);
            break;
        case 3://左
            y --;
            g.drawLine(x + 1,y - 4, x - 3, y);
            g.drawLine(x + 1,y + 4, x - 3, y);
            g.drawLine(x + 1,y - 3, x - 2, y);
            g.drawLine(x + 1,y + 3, x - 2, y);
            g.drawLine(x + 2,y - 3, x - 1, y);
            g.drawLine(x + 2,y + 3, x - 1, y);
            break;
        case 4://右
            y --;
            g.drawLine(x,y - 4, x + 4, y);
            g.drawLine(x,y + 4, x + 4, y);
            g.drawLine(x,y - 3, x + 3, y);
            g.drawLine(x,y + 3, x + 3, y);
            g.drawLine(x - 1,y - 3, x + 2, y);
            g.drawLine(x - 1,y + 3, x + 2, y);
            break;
        }
    }
    /**
     * 画点
     * @param x int
     * @param y int
     * @param c Color
     * @param g Graphics
     */
    public static void fillPoint(int x, int y,Color c,Graphics g)
    {
        fillPoint(x,y,1,c,g);
    }
    /**
     * 画方框
     * @param x int
     * @param y int
     * @param size int
     * @param c Color
     * @param g Graphics
     */
    public static void fillPoint(int x, int y, int size,Color c,Graphics g)
    {
        g.setColor(c);
        g.fillRect(x,y,size,size);
    }
    /**
     * 立体彩条
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color 左上
     * @param c2 Color 左下
     * @param c3 Color 右上
     * @param c4 Color 右下
     * @param g Graphics
     */
    public static void fillTransition(int x, int y, int width, int height, Color c1,
                               Color c2 ,Color c3,Color c4, Graphics g)
    {
      for (int i = 0; i < width; i++)
      {
          double d = (double) i / (double) width;
          Color iC1 = getColorDJ(c1,c2,d);
          Color iC2 = getColorDJ(c3,c4,d);
          for(int j = 0;j < height;j++)
          {
              double dj = (double) j / (double) height;
              g.setColor(getColorDJ(iC1,iC2,dj));
              g.fillRect(x + i, y + j, 1, 1);
          }
      }
    }
    /**
     * 纵向彩条
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color
     * @param c2 Color
     * @param g Graphics
     */
    public static void fillTransitionH(int x, int y, int width, int height, Color c1,
                               Color c2, Graphics g)
    {
      for (int i = 0; i < height; i++)
      {
        double d = (double) i / (double) height;
        g.setColor(getColorDJ(c1,c2,d));
        g.fillRect(x, i + y, width, 1);
      }
    }
    /**
     * 横向彩条
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color
     * @param c2 Color
     * @param g Graphics
     */
    public static void fillTransitionW(int x, int y, int width, int height, Color c1,
                               Color c2, Graphics g)
    {
      for (int i = 0; i < width; i++)
      {
        double d = (double) i / (double) width;
        g.setColor(getColorDJ(c1,c2,d));
        g.fillRect(i + x, y, 1, height);
      }
    }
    /**
     * 计算颜色递减
     * @param c1 Color
     * @param c2 Color
     * @param d double
     * @return Color
     */
    public static Color getColorDJ(Color c1,Color c2,double d)
    {
        int R = (int) (c1.getRed() - (c1.getRed() - c2.getRed()) * d);
        int G = (int) (c1.getGreen() - (c1.getGreen() - c2.getGreen()) * d);
        int B = (int) (c1.getBlue() - (c1.getBlue() - c2.getBlue()) * d);
        return new Color(R, G, B);
    }
    /**
     * 矩形
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c Color
     * @param g Graphics
     */
    public static void drawRect(int x,int y,int width,int height,Color c,Graphics g)
    {
        g.setColor(c);
        g.drawRect(x,y,width - 1,height - 1);
    }

    /**
     * 填充矩形
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c Color
     * @param g Graphics
     */
    public static void fillRect(int x,int y,int width,int height,Color c,Graphics g)
    {
        g.setColor(c);
        g.fillRect(x,y,width,height);
    }
    public static void fillRect(int x,int y,int width,int height,int l,Color c,Graphics g)
    {
        g.setColor(c);
        if(l == 0)
        {
            g.fillRect(x,y,width,height);
            return;
        }
        for(int i = 0;i < height;i += l)
            g.fillRect(x,i * l + y,width,l);
    }
    public static void fillAlpha(int x,int y,int width,int height,Color c,float alpha,Graphics g)
    {
        fillAlpha(x,y,width,height,c,0,alpha,g);
    }
    public static void fillAlpha(int x,int y,int width,int height,Color c,int jg,float alpha,Graphics g)
    {
        BufferedImage bufimg = new BufferedImage(width,height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufimg.createGraphics();
        TDrawTool.fillRect(0,0,width,height,jg,c,g2);
        Graphics2D gx = (Graphics2D) g;
        Composite composite = gx.getComposite();
        gx.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha));
        gx.drawImage(bufimg, x, y, null);
        gx.setComposite(composite);
    }
    /**
     * 填充矩形
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c Color
     * @param u Color[] 上延长填充
     * @param d Color[] 下延长填充
     * @param l Color[] 左延长填充
     * @param r Color[] 右延长填充
     * @param g Graphics
     */
    public static void fillRect(int x,int y,int width,int height,Color c,Color[] u,Color[] d,Color[] l,Color[] r,Graphics g)
    {
        fillRect(x,y,width,height,c,g);
        if(u != null)
        {
            for(int i = 0;i < u.length;i++)
                fillRect(x,y - i - 1,width,1,u[i],g);
        }
        if(d != null)
        {
            for(int i = 0;i < d.length;i++)
                fillRect(x,y + height + i,width,1,d[i],g);
        }
        if(l != null)
        {
            for(int i = 0;i < l.length;i++)
                fillRect(x - i - 1,y,1,height,l[i],g);
        }
        if(r != null)
        {
            for(int i = 0;i < r.length;i++)
                fillRect(x + width + i,y,1,height,r[i],g);
        }
    }
    /**
     * 制定区域绘制文字
     * @param text String
     * @param font Font
     * @param color Color
     * @param width int
     * @param height int
     * @param hA int
     * @param vA int
     * @param g Graphics
     */
    public static void paintText(String text,Font font,Color color,int width,int height,int hA,int vA,Graphics g)
    {
        paintText(text,font,color,width,height,hA,vA,(char)0,g);
    }
    /**
     * 制定区域绘制文字
     * @param text String
     * @param font Font
     * @param color Color
     * @param width int
     * @param height int
     * @param hA int
     * @param vA int
     * @param c char
     * @param g Graphics
     */
    public static void paintText(String text,Font font,Color color,int width,int height,int hA,int vA,char c,Graphics g)
    {
        g.setFont(font);
        g.setColor(color);

        String m = "";
        int mIndex = 0;
        if(c != 0)
        {
            m = ("" + c).toUpperCase();
            mIndex = text.indexOf(m);
            if(mIndex == -1)
            {
                text += "(" + m + ")";
                mIndex = text.length() - 2;
            }
        }
        FontMetrics fontMetric = g.getFontMetrics();
        int textWidth = fontMetric.stringWidth(text);
        int textHeight = fontMetric.getHeight();
        int fontAscent = fontMetric.getAscent();
        if(textWidth < width)
        {
            int x = DAlignment.getHPoint(width,textWidth,hA);
            int y = DAlignment.getVPoint(height,textHeight,fontAscent,vA);
            g.drawString(text,x,y);
            if(c != 0)
            {
                int lx = fontMetric.stringWidth(text.substring(0,mIndex));
                int lw = fontMetric.stringWidth(m);
                g.fillRect(x + lx, y - fontAscent + textHeight - 2, lw, 1);
            }
            return;
        }
        int k = 0;
        String s[] = getStringAlignmentArray(text,fontMetric,width);
        for(int i = 0;i < s.length;i++)
        {
            textWidth = fontMetric.stringWidth(s[i]);
            int x = DAlignment.getHPoint(width,textWidth,hA);
            int y = DAlignment.getVPoint(s.length,i,k,height,textHeight,fontAscent,vA);
            g.drawString(s[i],x,y);
            if(c != 0)
            {
                if(mIndex < s[i].length())
                {
                    int lx = fontMetric.stringWidth(s[i].substring(0,mIndex));
                    int lw = fontMetric.stringWidth(m);
                    g.fillRect(x + lx, y - fontAscent + textHeight - 2, lw, 1);
                }
                else
                    mIndex -= s[i].length();
            }
        }
    }
    /**
     * 计算最佳尺寸
     * @param text String
     * @param font Font
     * @param width int
     * @return DSize
     */
    public static DSize getTextSize(String text,Font font,int width)
    {
        return getTextSize(text,font,width,(char)0);
    }
    /**
     * 计算最佳尺寸
     * @param text String
     * @param font Font
     * @param width int
     * @param c char
     * @return DSize
     */
    public static DSize getTextSize(String text,Font font,int width,char c)
    {
        if(c != 0)
        {
            String m = ("" + c).toUpperCase();
            if(text.indexOf(m) == -1)
                text += "(" + m + ")";
        }

        FontMetrics fontMetric = DFont.getFontMetrics(font);
        int textWidth = fontMetric.stringWidth(text);
        int textHeight = fontMetric.getHeight();
        if(width < 0 || textWidth < width)
            return new DSize(textWidth,textHeight);
        String s[] = getStringAlignmentArray(text,fontMetric,width);
        width = 0;
        int height = 0;
        for(int i = 0;i < s.length;i++)
        {
            textWidth = fontMetric.stringWidth(s[i]);
            width = Math.max(width,textWidth);
            height += textHeight;
        }
        return new DSize(width,height);
    }
    /**
     * 处理字体分行
     * @param text String
     * @param fontMetric FontMetrics
     * @param width int
     * @return String[]
     */
    public static String[] getStringAlignmentArray(String text,FontMetrics fontMetric,int width)
    {
        ArrayList list = new ArrayList();
        int count = text.length();
        int w = 0;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < count;i++)
        {
            char c = text.charAt(i);
            int cw = fontMetric.charWidth(c);
            if(cw + w > width)
            {
                w = cw;
                list.add(sb.toString());
                sb = new StringBuffer();
                sb.append(c);
                continue;
            }
            w += cw;
            sb.append(c);
        }
        if(sb.length() > 0)
            list.add(sb.toString());
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 绘制箭头
     * @param x int
     * @param y int
     * @param type int 1 向下 2 向上 3向右 4 向左 8 向右黑头
     * @param g Graphics
     */
    public static void drawJ(int x,int y,int type,Graphics g)
    {
        switch(type)
        {
        case 1:
            g.drawLine(x - 1,y - 1,x + 1,y - 1);
            g.drawLine(x - 2,y - 2,x + 2,y - 2);
            g.drawLine(x - 3,y - 3,x + 3,y - 3);
            break;
        case 2:
            g.drawLine(x - 1,y + 1,x + 1,y + 1);
            g.drawLine(x - 2,y + 2,x + 2,y + 2);
            g.drawLine(x - 3,y + 3,x + 3,y + 3);
            break;
        case 3:
            g.drawLine(x - 1,y - 1,x - 1,y + 1);
            g.drawLine(x - 2,y - 2,x - 2,y + 2);
            g.drawLine(x - 3,y - 3,x - 3,y + 3);
            break;
        case 4:
            g.drawLine(x + 1,y - 1, x + 1,y + 1);
            g.drawLine(x + 2,y - 2,x + 2,y + 2);
            g.drawLine(x + 3,y - 3,x + 3,y + 3);
            break;
        case 8:
            g.drawLine(x - 4,y - 4, x - 4,y + 4);
            g.drawLine(x - 3,y - 3, x - 3,y + 3);
            g.drawLine(x - 2,y - 2, x - 2,y + 2);
            g.drawLine(x - 1,y - 1, x - 1,y + 1);
            g.drawLine(x,y, x,y);
        }
    }
    /**
     * 画线段
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param type int
     * @param color Color
     * @param g Graphics
     */
    public static void drawLine(int x1,int y1,int x2,int y2,int type,Color color,Graphics g)
    {
        int t = 3;
        int ht = 10;
        g.setColor(color);
        switch(type)
        {
        case 0://斜线
            g.drawLine(x1,y1,x2,y2);
            break;
        case 1://横折直角线
            int w = x1 + (x2 - x1) / 2;
            g.drawLine(x1,y1,w,y1);
            g.drawLine(w,y2,x2,y2);
            g.drawLine(w,y1,w,y2);
            break;
        case 2://纵折直角线
            int h = y1 + (y2 - y1) / 2;
            g.drawLine(x1,y1,x1,h);
            g.drawLine(x2,h,x2,y2);
            g.drawLine(x1,h,x2,h);
            break;
        case 3://横折圆角角线
            if(t > Math.abs(y1 - y2))
                t = Math.abs(y1 - y2);
            w = x1 + (x2 - x1) / 2;
            boolean xp = x1 < x2;
            boolean yp = y1 < y2;
            g.drawLine(x1,y1,xp?w - t:w + t,y1);
            g.drawLine(xp?w + t:w - t,y2,x2,y2);
            g.drawLine(w,yp?y1 + t:y1 - t,w,yp?y2 - t:y2 + t);

            g.drawLine(xp?w - t:w + t,y1,w,yp?y1 + t:y1 - t);
            g.drawLine(xp?w + t:w - t,y2,w,yp?y2 - t:y2 + t);
            break;
        case 4://纵折圆角线
            if(t > Math.abs(x1 - x2))
                t = Math.abs(x1 - x2);
            h = y1 + (y2 - y1) / 2;
            xp = x1 < x2;
            yp = y1 < y2;
            g.drawLine(x1,y1,x1,yp?h - t:h + t);
            g.drawLine(x2,yp?h + t:h - t,x2,y2);
            g.drawLine(xp?x1 + t:x1 - t,h,xp?x2 - t:x2 + t,h);

            g.drawLine(x1,yp?h - t:h + t,xp?x1 + t:x1 - t,h);
            g.drawLine(x2,yp?h + t:h - t,xp?x2 - t:x2 + t,h);
            break;
        case 5://n线
            int y = Math.min(y1,y2) - ht;
            xp = x1 < x2;
            g.drawLine(x1,y,x1,y1);
            g.drawLine(x2,y,x2,y2);
            g.drawLine(xp?x1 + t:x1 - t,y - t,xp?x2 - t:x2 + t,y - t);
            g.drawLine(x1,y,xp?x1 + t:x1 - t,y - t);
            g.drawLine(x2,y,xp?x2 - t:x2 + t,y - t);
            break;
        case 6://u线
            y = Math.max(y1,y2) + ht;
            xp = x1 < x2;
            g.drawLine(x1,y,x1,y1);
            g.drawLine(x2,y,x2,y2);
            g.drawLine(xp?x1 + t:x1 - t,y + t,xp?x2 - t:x2 + t,y + t);
            g.drawLine(x1,y,xp?x1 + t:x1 - t,y + t);
            g.drawLine(x2,y,xp?x2 - t:x2 + t,y + t);
            break;
        case 7://c线
            int x = Math.min(x1,x2) - ht;
            yp = y1 < y2;
            g.drawLine(x,y1,x1,y1);
            g.drawLine(x,y2,x2,y2);
            g.drawLine(x - t,yp?y1 + t:y1 - t,x - t,yp?y2 - t:y2 + t);
            g.drawLine(x,y1,x - t,yp?y1 + t:y1 - t);
            g.drawLine(x,y2,x - t,yp?y2 - t:y2 + t);
            break;
        case 8://>线
            x = Math.max(x1,x2) + ht;
            yp = y1 < y2;
            g.drawLine(x,y1,x1,y1);
            g.drawLine(x,y2,x2,y2);
            g.drawLine(x + t,yp?y1 + t:y1 - t,x + t,yp?y2 - t:y2 + t);
            g.drawLine(x,y1,x + t,yp?y1 + t:y1 - t);
            g.drawLine(x,y2,x + t,yp?y2 - t:y2 + t);
            break;
        }
    }
    /**
     * 绘制括号线
     * @param insets DInsets
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param color Color
     * @param length int
     * @param zoom double
     * @param g Graphics
     */
    public static void drawRectInsets(DInsets insets,int x,int y,int width,int height,Color color,int length,double zoom,Graphics g)
    {
        ((Graphics2D)g).setStroke(new BasicStroke(1.0f));
        g.setColor(color);
        int top = (int)(insets.top * zoom / 75.0);
        int bottom = (int)(insets.bottom * zoom / 75.0);
        int left = (int)(insets.left * zoom / 75.0);
        int right = (int)(insets.right * zoom / 75.0);
        length = (int)(length * zoom / 100.0);
        //左上
        int x0 = left;
        if(x0 > width / 2)
            x0 = width / 2;
        int y0 = top;
        if(y0 > height / 2)
            y0 = height / 2;
        int x1 = (x0 - length < 1)?1:(x0 - length);
        if(x1 > width / 2)
            x1 = width / 2;
        int y1 = (y0 - length < 1)?1:(y0 - length);
        if(y1 > height / 2)
            y1 = height / 2;
        g.drawLine(x1 + x,y0 + y,x0 + x,y0 + y);
        g.drawLine(x0 + x,y1 + y,x0 + x,y0 + y);
        //右上
        x0 = width - right;
        if(x0 < width / 2)
            x0 = width / 2;
        y0 = top;
        if(y0 > height / 2)
            y0 = height / 2;
        x1 = (x0 + length > width)?(width - 1):(x0 + length);
        if(x1 < width / 2)
            x1 = width / 2;
        y1 = (y0 - length < 1)?1:(y0 - length);
        if(y1 > height / 2)
            y1 = height / 2;
        g.drawLine(x1 + x,y0 + y,x0 + x,y0 + y);
        g.drawLine(x0 + x,y1 + y,x0 + x,y0 + y);
        //左下
        x0 = left;
        if(x0 > width / 2)
            x0 = width / 2;
        y0 = height - bottom;
        if(y0 < height / 2)
            y0 = height / 2;
        x1 = (x0 - length < 1)?1:(x0 - length);
        if(x1 > width / 2)
            x1 = width / 2;
        y1 = (y0 + length > height)?(height - 1):(y0 + length);
        if(y1 < height / 2)
            y1 = height / 2;
        g.drawLine(x1 + x,y0 + y,x0 + x,y0 + y);
        g.drawLine(x0 + x,y1 + y,x0 + x,y0 + y);
        //右下
        x0 = width - right;
        if(x0 < width / 2)
            x0 = width / 2;
        y0 = height - bottom;
        if(y0 < height / 2)
            y0 = height / 2;
        x1 = (x0 + length > width)?(width - 1):(x0 + length);
        if(x1 < width / 2)
            x1 = width / 2;
        y1 = (y0 + length > height)?(height - 1):(y0 + length);
        if(y1 < height / 2)
            y1 = height / 2;
        g.drawLine(x1 + x,y0 + y,x0 + x,y0 + y);
        g.drawLine(x0 + x,y1 + y,x0 + x,y0 + y);
    }
    /**
     * 绘制回车符
     * @param x int
     * @param y int
     * @param g Graphics
     */
    public static void drawEnter(int x,int y,Graphics g)
    {
        y-= 6;
        g.setColor(new Color(128,128,128));
        g.drawLine(x,y,x + 5,y);
        g.drawLine(x + 1,y - 1,x + 1,y + 1);
        g.drawLine(x + 2,y - 2,x + 2,y + 2);
        g.drawLine(x + 6,y - 4,x + 6,y - 1);
        g.drawLine(x + 6,y - 4,x + 4,y - 4);
    }
}
