package com.dongyang.tui.text.image;

import com.dongyang.tui.text.PM;

public interface BlockPI
{
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom();
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM();
    /**
     * 随动比例
     * @param index int
     * @param x double
     * @param y double
     * @param block GBlock
     */
    public void onMouseDraggedOther(int index,double x,double y,GBlock block);
}
