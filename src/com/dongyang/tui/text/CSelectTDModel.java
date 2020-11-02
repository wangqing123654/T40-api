package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 选中表格单元</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: Javahis</p>
 *
 * @author lzk 2009.11.19
 * @version 1.0
 */
public class CSelectTDModel implements IExeAction
{
    /**
     * 设置单元
     */
    private ETD td;
    /**
     * 选择块列表
     */
    private TList selectList;
    /**
     * 初始化
     * @param td ETD
     */
    public CSelectTDModel(ETD td)
    {
        setTD(td);
    }
    /**
     * 设置单元
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }
    /**
     * 得到单元
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
    }
    /**
     * 设置选择块列表
     * @param list TList
     */
    public void setSelectList(TList list)
    {
        this.selectList = list;
    }
    /**
     * 得到选择块列表
     * @return TList
     */
    public TList getSelectList()
    {
        return selectList;
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        getTD().setSelectedAll(false);
        getTD().setSelectedModel(null);
        setSelectList(null);
    }
    public String toString()
    {
        return "CSelectTDModel " + getTD().getIndexString();
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        getTD().exeAction(action);
    }
}
