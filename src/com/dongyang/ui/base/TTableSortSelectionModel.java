package com.dongyang.ui.base;

import javax.swing.DefaultListSelectionModel;

public class TTableSortSelectionModel extends DefaultListSelectionModel{

	  /**
     * Table����
     */
    private TTableSortBase table;
    /**
     * ѡ��λ��
     */
    private int selectIndex = -1;
    /**
     * ģʽ 0 �� 1 ��
     */
    private int type;
    /**
     * ������
     * @param table TTableSortBase
     */
    public TTableSortSelectionModel(TTableSortBase table)
    {
        this(table,0);
    }
    /**
     * ������
     * @param table TTableSortBase
     * @param type int
     */
    public TTableSortSelectionModel(TTableSortBase table,int type)
    {
        setTable(table);
        setType(type);
    }
    /**
     * ��������
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * ����Table
     * @param table TTableSortBase
     */
    public void setTable(TTableSortBase table)
    {
        this.table = table;
    }
    /**
     * �õ�Table
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
