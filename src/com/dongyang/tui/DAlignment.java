package com.dongyang.tui;

import javax.swing.SwingConstants;

/**
 *
 * <p>Title: 位置处理对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.12
 * @version 1.0
 */
public class DAlignment
{
    /**
     * 解析横向位置
     * @param horizontalAlignment String
     * @return int
     */
    public static int getHorizontalAlignment(String horizontalAlignment)
    {
        if(horizontalAlignment == null || horizontalAlignment.length() == 0)
            return SwingConstants.CENTER;
        if("0".equals(horizontalAlignment) ||
           "center".equalsIgnoreCase(horizontalAlignment) ||
           "居中".equals(horizontalAlignment) ||
           "中".equals(horizontalAlignment)||
           "c".equalsIgnoreCase(horizontalAlignment))
            return SwingConstants.CENTER;
        if("2".equals(horizontalAlignment)||
            "left".equalsIgnoreCase(horizontalAlignment) ||
            "居左".equals(horizontalAlignment) ||
            "左".equals(horizontalAlignment)||
            "l".equalsIgnoreCase(horizontalAlignment))
            return SwingConstants.LEFT;
        if("4".equals(horizontalAlignment)||
            "right".equalsIgnoreCase(horizontalAlignment) ||
            "居右".equals(horizontalAlignment) ||
            "右".equals(horizontalAlignment)||
            "r".equalsIgnoreCase(horizontalAlignment))
            return SwingConstants.RIGHT;
        return SwingConstants.CENTER;
    }
    /**
     * 解析纵向位置
     * @param verticalAlignment String
     * @return int
     */
    public static int getVerticalAlignment(String verticalAlignment)
    {
        if(verticalAlignment == null || verticalAlignment.length() == 0)
            return SwingConstants.CENTER;
        if("0".equals(verticalAlignment) ||
           "center".equalsIgnoreCase(verticalAlignment) ||
           "居中".equals(verticalAlignment) ||
           "中".equals(verticalAlignment)||
           "c".equalsIgnoreCase(verticalAlignment))
            return SwingConstants.CENTER;
        if("1".equals(verticalAlignment)||
            "top".equalsIgnoreCase(verticalAlignment) ||
            "居上".equals(verticalAlignment) ||
            "上".equals(verticalAlignment)||
            "t".equalsIgnoreCase(verticalAlignment))
            return SwingConstants.TOP;
        if("3".equals(verticalAlignment)||
            "bottom".equalsIgnoreCase(verticalAlignment) ||
            "居下".equals(verticalAlignment) ||
            "下".equals(verticalAlignment)||
            "b".equalsIgnoreCase(verticalAlignment))
            return SwingConstants.BOTTOM;
        return SwingConstants.CENTER;
    }
    /**
     * 计算字符输出位置(横向)
     * @param width int
     * @param sWidth int
     * @param h int
     * @return int
     */
    public static int getHPoint(int width,int sWidth,int h)
    {
        switch(h)
        {
        case SwingConstants.CENTER:
            return (width - sWidth) / 2;
        case SwingConstants.LEFT:
            return 0;
        case SwingConstants.RIGHT:
            return width - sWidth;
        }
        return (width - sWidth) / 2;
    }
    /**
     * 计算字符输出位置(纵向)
     * @param height int
     * @param sHeight int
     * @param fontAscent int
     * @param v int
     * @return int
     */
    public static int getVPoint(int height,int sHeight,int fontAscent,int v)
    {
        switch(v)
        {
        case SwingConstants.CENTER:
            return (height - sHeight) / 2 + fontAscent;
        case SwingConstants.TOP:
            return fontAscent;
        case SwingConstants.BOTTOM:
            return height - sHeight + fontAscent;
        }
        return height / 2 - sHeight / 2 + fontAscent;
    }
    public static int getVPoint(int count,int row,int k,int height,int sHeight,int fontAscent,int v)
    {
        switch(v)
        {
        case SwingConstants.CENTER:
            return (height - count * sHeight - (count - 1) * k) / 2 + row * (sHeight + k) + fontAscent;
        case SwingConstants.TOP:
            return row * (sHeight + k) + fontAscent;
        case SwingConstants.BOTTOM:
            return height - (count - row - 1) * (sHeight + k) - sHeight + fontAscent;
        }
        return height / 2 - sHeight / 2 + fontAscent;
    }
}
