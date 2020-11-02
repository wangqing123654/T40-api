package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: ѡ�б����</p>
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
     * ������
     */
    private ETR tr;
    /**
     * ѡ����б�
     */
    private TList selectList;
    /**
     * ��ʼ��
     * @param tr ETR
     */
    public CSelectTRModel(ETR tr)
    {
        setTR(tr);
    }
    /**
     * ������
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * �õ���
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
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
        getTR().setSelectedAll(false);
        getTR().setSelectedModel(null);
        setSelectList(null);
    }
    public String toString()
    {
        return "CSelectTRModel " + getTR().getIndexString();
    }
    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        getTR().exeAction(action);
    }
}
