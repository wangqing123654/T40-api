package com.dongyang.ui.base;

import javax.swing.table.DefaultTableColumnModel;

public class TTableSortColumnModel extends DefaultTableColumnModel{
	
	
	/**
     * Table对象
     */
    private TTableSortBase table;
    /**
     * 列索引
     */
    private int[] columnIndexs;
    /**
     * 构造器
     * @param table TTableSortBase
     */
    public TTableSortColumnModel(TTableSortBase table)
    {
        setTable(table);
        setSelectionModel(new TTableSortSelectionModel(table,1));
    }
    /**
     * 设置Table
     * @param table TTableSortBase
     */
    public void setTable(TTableSortBase table)
    {
        this.table = table;
    }
    /**
     * 得到Table
     * @return TTable
     */
    public TTableSortBase table()
    {
        return table;
    }
    /**
     * 初始化列索引
     * @param columnCount int
     */
    public void initColumnIndex(int columnCount)
    {
        columnIndexs = new int[columnCount];
        for(int i = 0; i < columnCount;i++)
            columnIndexs[i] = i;
    }
    /**
     * 得到旧列编号
     * @param columnIndex int
     * @return int
     */
    public int getColumnIndex(int columnIndex)
    {
        return columnIndexs[columnIndex];
    }
    /**
     * 得到新列编号
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
     * 列移动
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
