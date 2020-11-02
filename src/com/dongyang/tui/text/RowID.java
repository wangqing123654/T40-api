package com.dongyang.tui.text;

public class RowID
{
    int index = 0;
    /**
     * 得到序号
     * @return int
     */
    public int getIndex()
    {
        index ++;
        return index;
    }
    /**
     * 得到开始序号
     * @return int
     */
    public int getStart()
    {
        return index + 1;
    }
    /**
     * 得到结束序号
     * @return int
     */
    public int getEnd()
    {
        return index;
    }
}
