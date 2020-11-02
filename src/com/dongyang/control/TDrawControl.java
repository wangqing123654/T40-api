package com.dongyang.control;

import com.dongyang.ui.TComponent;
import java.awt.Graphics;

/**
 *
 * <p>Title: 绘图控制类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.7.2
 * @version 1.0
 */
public class TDrawControl
{
    /**
     * 父组件
     */
    public TComponent parent;
    /**
     * 初始化
     */
    public TDrawControl()
    {

    }
    /**
     * 设置父组件
     * @param parent TComponent
     */
    public void setParent(TComponent parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父组件
     * @return TComponent
     */
    public TComponent getParent()
    {
        return parent;
    }
    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl()
    {
        if(getParent() == null)
            return null;
        return (TControl)getParent().callFunction("getControl");
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        if(getParent() == null)
            return 0;
        return (Integer)getParent().callFunction("getWidth");
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        if(getParent() == null)
            return 0;
        return (Integer)getParent().callFunction("getHeight");
    }
    /**
     * 绘制背景
     * @param g Graphics
     */
    public void paintBackground(Graphics g)
    {

    }
    /**
     * 绘制前景
     * @param g Graphics
     */
    public void paintForeground(Graphics g)
    {

    }
}
