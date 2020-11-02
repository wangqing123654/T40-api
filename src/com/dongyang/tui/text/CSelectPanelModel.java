package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: ѡ�����</p>
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
    public CSelectPanelModel(EPanel panel)
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
    /**
     * ɾ���Լ�
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
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        getPanel().exeAction(action);
    }
}
