package com.dongyang.tui.text.image;

import com.dongyang.tui.text.PM;

public interface BlockPI
{
    /**
     * �õ����ű���
     * @return double
     */
    public double getZoom();
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM();
    /**
     * �涯����
     * @param index int
     * @param x double
     * @param y double
     * @param block GBlock
     */
    public void onMouseDraggedOther(int index,double x,double y,GBlock block);
}
