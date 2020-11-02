package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: ѡ�����س���</p>
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
    //���б�ʶ
    public static final String NEW_LINE="newline";
    /**
     * �������
     */
    private EPanel panel;
    /**
     * ѡ����б�
     */
    private TList selectList;
    /**
     * ��ʼ��
     * @param panel EPanel
     */
    public CSelectPanelEnterModel(EPanel panel)
    {
        setPanel(panel);
    }
    /**
     * �������
     * @param panel EPanel
     */
    public void setPanel(EPanel panel)
    {
        this.panel = panel;
    }
    /**
     * �õ����
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return panel;
    }
    /**
     * ����ѡ����б�
     * @param list TList
     */
    public void setSelectList(TList list)
    {
        this.selectList = list;
    }
    /**
     * �õ�ѡ����б�
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
     * ɾ���Լ�
     */
    public void removeThis()
    {
        getPanel().setSelectedEnterModel(null);
        setSelectList(null);
    }
    /**
     * ִ�ж���
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
            //$$============Added by lx 2011-09-07 ���ƿؼ����ܼ���س�START===================$$//
            CopyOperator.addComList(NEW_LINE);
            //$$============Added by lx 2011-09-07 ���ƿؼ����ܼ���س�END===================$$//
            CopyOperator copyOperator = action.getCopyOperator(0);
            copyOperator.addRow();
            return;
        }
    }
}
