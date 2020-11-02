package com.dongyang.tui;

import javax.swing.SwingConstants;

/**
 *
 * <p>Title: λ�ô������</p>
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
     * ��������λ��
     * @param horizontalAlignment String
     * @return int
     */
    public static int getHorizontalAlignment(String horizontalAlignment)
    {
        if(horizontalAlignment == null || horizontalAlignment.length() == 0)
            return SwingConstants.CENTER;
        if("0".equals(horizontalAlignment) ||
           "center".equalsIgnoreCase(horizontalAlignment) ||
           "����".equals(horizontalAlignment) ||
           "��".equals(horizontalAlignment)||
           "c".equalsIgnoreCase(horizontalAlignment))
            return SwingConstants.CENTER;
        if("2".equals(horizontalAlignment)||
            "left".equalsIgnoreCase(horizontalAlignment) ||
            "����".equals(horizontalAlignment) ||
            "��".equals(horizontalAlignment)||
            "l".equalsIgnoreCase(horizontalAlignment))
            return SwingConstants.LEFT;
        if("4".equals(horizontalAlignment)||
            "right".equalsIgnoreCase(horizontalAlignment) ||
            "����".equals(horizontalAlignment) ||
            "��".equals(horizontalAlignment)||
            "r".equalsIgnoreCase(horizontalAlignment))
            return SwingConstants.RIGHT;
        return SwingConstants.CENTER;
    }
    /**
     * ��������λ��
     * @param verticalAlignment String
     * @return int
     */
    public static int getVerticalAlignment(String verticalAlignment)
    {
        if(verticalAlignment == null || verticalAlignment.length() == 0)
            return SwingConstants.CENTER;
        if("0".equals(verticalAlignment) ||
           "center".equalsIgnoreCase(verticalAlignment) ||
           "����".equals(verticalAlignment) ||
           "��".equals(verticalAlignment)||
           "c".equalsIgnoreCase(verticalAlignment))
            return SwingConstants.CENTER;
        if("1".equals(verticalAlignment)||
            "top".equalsIgnoreCase(verticalAlignment) ||
            "����".equals(verticalAlignment) ||
            "��".equals(verticalAlignment)||
            "t".equalsIgnoreCase(verticalAlignment))
            return SwingConstants.TOP;
        if("3".equals(verticalAlignment)||
            "bottom".equalsIgnoreCase(verticalAlignment) ||
            "����".equals(verticalAlignment) ||
            "��".equals(verticalAlignment)||
            "b".equalsIgnoreCase(verticalAlignment))
            return SwingConstants.BOTTOM;
        return SwingConstants.CENTER;
    }
    /**
     * �����ַ����λ��(����)
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
     * �����ַ����λ��(����)
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
