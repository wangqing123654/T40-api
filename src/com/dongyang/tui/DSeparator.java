package com.dongyang.tui;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.ui.util.TDrawTool;

/**
 *
 * <p>Title: 分割器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.26
 * @version 1.0
 */
public class DSeparator extends DComponent
{
    /**
     * 构造器
     */
    public DSeparator()
    {

    }
    /**
     * 得到最大显示高度
     * @return int
     */
    public int getMaxViewHeight()
    {
        return 3;
    }
    /**
     * 绘制边框
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        if(getBorder() != null && getBorder().length() >= 0)
        {
            super.paintBorder(g);
            return;
        }
        //绘制菜单边框
        paintMenuBorder(g);
    }
    /**
     * 绘制菜单边框
     * @param g Graphics
     */
    public void paintMenuBorder(Graphics g)
    {
        DRectangle r = getComponentBounds();
        TDrawTool.fillTransitionW(0,0,22,r.getHeight(),new Color(227,239,255),
                                  new Color(136,174,228),g);
        g.setColor(new Color(106,140,203));
        int y = r.getHeight() / 2;
        g.drawLine(30,y,r.getWidth(),y);
    }

}
