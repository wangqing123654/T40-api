package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 选中表格行</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.18
 * @version 1.0
 */
public class CSelectTRModel implements IExeAction
{
    /**
     * 设置行
     */
    private ETR tr;
    /**
     * 选择块列表
     */
    private TList selectList;
    /**
     * 初始化
     * @param tr ETR
     */
    public CSelectTRModel(ETR tr)
    {
        setTR(tr);
    }
    /**
     * 设置行
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * 得到行
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
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
        getTR().setSelectedAll(false);
        getTR().setSelectedModel(null);
        setSelectList(null);
    }
    public String toString()
    {
        return "CSelectTRModel " + getTR().getIndexString();
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        getTR().exeAction(action);
    }
}
