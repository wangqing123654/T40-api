package com.dongyang.tui;

import java.awt.Cursor;
import java.awt.Graphics2D;
import test.AButton;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Graphics;

/**
 *
 * <p>Title: 光标</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.9
 * @version 1.0
 */
public class DCursor
{
    /**
     * The default cursor type (gets set if no cursor is defined).
     */
    public static final int	DEFAULT_CURSOR   		= 0;

    /**
     * The crosshair cursor type.
     */
    public static final int	CROSSHAIR_CURSOR 		= 1;

    /**
     * The text cursor type.
     */
    public static final int	TEXT_CURSOR 	 		= 2;

    /**
     * The wait cursor type.
     */
    public static final int	WAIT_CURSOR	 		= 3;

    /**
     * The south-west-resize cursor type.
     */
    public static final int	SW_RESIZE_CURSOR	 	= 4;

    /**
     * The south-east-resize cursor type.
     */
    public static final int	SE_RESIZE_CURSOR	 	= 5;

    /**
     * The north-west-resize cursor type.
     */
    public static final int	NW_RESIZE_CURSOR		= 6;

    /**
     * The north-east-resize cursor type.
     */
    public static final int	NE_RESIZE_CURSOR	 	= 7;

    /**
     * The north-resize cursor type.
     */
    public static final int	N_RESIZE_CURSOR 		= 8;

    /**
     * The south-resize cursor type.
     */
    public static final int	S_RESIZE_CURSOR 		= 9;

    /**
     * The west-resize cursor type.
     */
    public static final int	W_RESIZE_CURSOR	 		= 10;

    /**
     * The east-resize cursor type.
     */
    public static final int	E_RESIZE_CURSOR			= 11;

    /**
     * The hand cursor type.
     */
    public static final int	HAND_CURSOR			= 12;

    /**
     * The move cursor type.
     */
    public static final int	MOVE_CURSOR			= 13;
    /**
     * 十字
     */
    public static final int     CROSS                           = 14;
    /**
     * 红十字
     */
    public static final int     CROSS_RED                       = 15;
    /**
     * 得到光标
     * @param cursor int
     * @return Cursor
     */
    public static Cursor getCursor(int cursor)
    {
        if(cursor < 14)
            return new Cursor(cursor);
        CursorImage image = new CursorImage(cursor);
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        return toolkit.createCustomCursor(image,image.getPoint(),"");
    }
    /**
     *
     * <p>Title: 光标图案</p>
     *
     * <p>Description: </p>
     *
     * <p>Copyright: Copyright (c) 2008</p>
     *
     * <p>Company: JavaHis</p>
     *
     * @author lzk 2009.3.9
     * @version 1.0
     */
    public static class CursorImage extends BufferedImage
    {
        int cursor;
        /**
         * 构造器
         * @param cursor int
         */
        public CursorImage(int cursor)
        {
            super(32,32,TYPE_INT_ARGB);
            this.cursor = cursor;
            paint(createGraphics());
        }
        /**
         * 绘图
         * @param g Graphics
         */
        public void paint(Graphics g)
        {
            switch(cursor)
            {
            case CROSS:
                g.setColor(new Color(0,0,0));
                g.drawLine(0,10,20,10);
                g.drawLine(10,0,10,20);
                break;
            case CROSS_RED:
                g.setColor(new Color(255,0,0));
                g.drawLine(0,10,20,10);
                g.drawLine(10,0,10,20);
                break;
            }
        }
        /**
         * 得到起始点
         * @return Point
         */
        public Point getPoint()
        {
            switch(cursor)
            {
            case CROSS:
            case CROSS_RED:
                return new Point(10, 10);
            }
            return new Point(0, 0);
        }
    }
}
