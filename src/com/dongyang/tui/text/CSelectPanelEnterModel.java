package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 选中面板回车符</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.9
 * @version 1.0
 */
public class CSelectPanelEnterModel implements IExeAction
{
    //换行标识
    public static final String NEW_LINE="newline";
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
    public CSelectPanelEnterModel(EPanel panel)
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
    public String toString()
    {
        return "CSelectPanelEnterModel " + getPanel().getIndexString();
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        getPanel().setSelectedEnterModel(null);
        setSelectList(null);
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        switch(action.getType())
        {
        case EAction.DELETE:
            getPanel().onDeleteEnter();
            return;
        case EAction.COPY:
            //$$============Added by lx 2011-09-07 复制控件功能加入回车START===================$$//
            CopyOperator.addComList(NEW_LINE);
            //$$============Added by lx 2011-09-07 复制控件功能加入回车END===================$$//
            CopyOperator copyOperator = action.getCopyOperator(0);
            copyOperator.addRow();
            return;
        }
    }
}
