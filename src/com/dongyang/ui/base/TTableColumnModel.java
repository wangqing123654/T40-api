package com.dongyang.ui.base;

import javax.swing.table.DefaultTableColumnModel;

public class TTableColumnModel extends DefaultTableColumnModel{
    /**
     * Table����
     */
    private TTableBase table;
    /**
     * ������
     */
    private int[] columnIndexs;
    /**
     * ������
     * @param table TTableBase
     */
    public TTableColumnModel(TTableBase table)
    {
        setTable(table);
        setSelectionModel(new TTableSelectionModel(table,1));
    }
    /**
     * ����Table
     * @param table TTableBase
     */
    public void setTable(TTableBase table)
    {
        this.table = table;
    }
    /**
     * �õ�Table
     * @return TTable
     */
    public TTableBase table()
    {
        return table;
    }
    /**
     * ��ʼ��������
     * @param columnCount int
     */
    public void initColumnIndex(int columnCount)
    {
        columnIndexs = new int[columnCount];
        for(int i = 0; i < columnCount;i++)
            columnIndexs[i] = i;
    }
    /**
     * �õ����б��
     * @param columnIndex int
     * @return int
     */
    public int getColumnIndex(int columnIndex)
    {
        return columnIndexs[columnIndex];
    }
    /**
     * �õ����б��
     * @param columnIndex int
     * @return int
     */
    public int getColumnIndexNew(int columnIndex)
    {
        for(int i = 0;i < columnIndexs.length;i++)
            if(columnIndexs[i] == columnIndex)
                return i;
        return -1;
    }
    /**
     * ���ƶ�
     * @param i int
     * @param j int
     */
    public void moveColumn(int i, int j)
    {
        super.moveColumn(i, j);
        if(columnIndexs == null)
            return;
        int x = columnIndexs[i];
        columnIndexs[i] = columnIndexs[j];
        columnIndexs[j] = x;
    }
}
