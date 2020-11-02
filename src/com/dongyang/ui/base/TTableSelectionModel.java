package com.dongyang.ui.base;

import javax.swing.DefaultListSelectionModel;

/**
 *
 * <p>Title: Table ѡ��ģ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.3.27
 * @version 1.0
 */
public class TTableSelectionModel extends DefaultListSelectionModel
{
    /**
     * Table����
     */
    private TTableBase table;
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
     * @param table TTableBase
     */
    public TTableSelectionModel(TTableBase table)
    {
        this(table,0);
    }
    /**
     * ������
     * @param table TTableBase
     * @param type int
     */
    public TTableSelectionModel(TTableBase table,int type)
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
    public TTableBase getTable()
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
