package com.dongyang.ui.base;

import javax.swing.DefaultListSelectionModel;

public class TTableSortSelectionModel extends DefaultListSelectionModel{

	  /**
     * Table对象
     */
    private TTableSortBase table;
    /**
     * 选中位置
     */
    private int selectIndex = -1;
    /**
     * 模式 0 行 1 列
     */
    private int type;
    /**
     * 构造器
     * @param table TTableSortBase
     */
    public TTableSortSelectionModel(TTableSortBase table)
    {
        this(table,0);
    }
    /**
     * 构造器
     * @param table TTableSortBase
     * @param type int
     */
    public TTableSortSelectionModel(TTableSortBase table,int type)
    {
        setTable(table);
        setType(type);
    }
    /**
     * 设置类型
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return int
     */
    public int getType()
    {
        return type;
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
    public TTableSortBase getTable()
    {
        return table;
    }
    public void setSelectionInterval(int index0, int index1) {
        super.setSelectionInterval(index0,index1);
        if(index0 == selectIndex)
            return;
        if(getTable() != null)
            if(getType() == 0)
                getTable().onRowChange();
            else
                getTable().onColumnChange();
        selectIndex = index0;
    }
}
