package com.dongyang.tui.text;

public class RowID
{
    int index = 0;
    /**
     * �õ����
     * @return int
     */
    public int getIndex()
    {
        index ++;
        return index;
    }
    /**
     * �õ���ʼ���
     * @return int
     */
    public int getStart()
    {
        return index + 1;
    }
    /**
     * �õ��������
     * @return int
     */
    public int getEnd()
    {
        return index;
    }
}
