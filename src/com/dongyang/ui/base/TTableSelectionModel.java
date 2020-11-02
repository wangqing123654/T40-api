package com.dongyang.ui.base;

import javax.swing.DefaultListSelectionModel;

/**
 *
 * <p>Title: Table 选中模型</p>
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
     * Table对象
     */
    private TTableBase table;
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
     * @param table TTableBase
     */
    public TTableSelectionModel(TTableBase table)
    {
        this(table,0);
    }
    /**
     * 构造器
     * @param table TTableBase
     * @param type int
     */
    public TTableSelectionModel(TTableBase table,int type)
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
     * @param table TTableBase
     */
    public void setTable(TTableBase table)
    {
        this.table = table;
    }
    /**
     * 得到Table
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
