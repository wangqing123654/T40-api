package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: ѡ�б��Ԫ</p>
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
     * ���õ�Ԫ
     */
    private ETD td;
    /**
     * ѡ����б�
     */
    private TList selectList;
    /**
     * ��ʼ��
     * @param td ETD
     */
    public CSelectTDModel(ETD td)
    {
        setTD(td);
    }
    /**
     * ���õ�Ԫ
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }
    /**
     * �õ���Ԫ
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
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
        getTD().setSelectedAll(false);
        getTD().setSelectedModel(null);
        setSelectList(null);
    }
    public String toString()
    {
        return "CSelectTDModel " + getTD().getIndexString();
    }
    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        getTD().exeAction(action);
    }
}
