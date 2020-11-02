package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 选中面板</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: Javahis</p>
 *
 * @author lzk 2009.11.18
 * @version 1.0
 */
public class CSelectPanelModel implements IExeAction
{
    /**
     * 设置面板
     */
    private EPanel panel;
    /**
     * 选择块列表
     */
    private TList selectList;
    /**
     * 初始化
     * @param panel EPanel
     */
    public CSelectPanelModel(EPanel panel)
    {
        setPanel(panel);
    }
    /**
     * 设置面板
     * @param panel EPanel
     */
    public void setPanel(EPanel panel)
    {
        this.panel = panel;
    }
    /**
     * 得到面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return panel;
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
        getPanel().setSelectedAll(false);
        getPanel().setSelectedModel(null);
        setSelectList(null);
    }
    public String toString()
    {
        return "CSelectPanelModel " + getPanel().getIndexString();
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        getPanel().exeAction(action);
    }
}
