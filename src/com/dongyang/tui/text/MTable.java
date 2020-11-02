package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: Table管理器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.24
 * @version 1.0
 */
public class MTable
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 构造器
     */
    public MTable()
    {
        components = new TList();
    }
    /**
     * 增加成员
     * @param table ETable
     * @return int
     */
    public int add(ETable table)
    {
        if (table == null)
            return -1;
        components.add(table);
        return components.size() - 1;
    }
    /**
     * 删除成员
     * @param table ETable
     */
    public void remove(ETable table)
    {
        components.remove(table);
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * 得到成员
     * @param index int
     * @return ETable
     */
    public ETable get(int index)
    {
        return (ETable) components.get(index);
    }
    /**
     * 查找位置
     * @param table ETable
     * @return int
     */
    public int indexOf(ETable table)
    {
        if(table == null)
            return -1;
        return components.indexOf(table);
    }
    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
}
