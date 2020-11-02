package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: Table������</p>
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
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    public MTable()
    {
        components = new TList();
    }
    /**
     * ���ӳ�Ա
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
     * ɾ����Ա
     * @param table ETable
     */
    public void remove(ETable table)
    {
        components.remove(table);
    }
    /**
     * ��Ա����
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * �õ���Ա
     * @param index int
     * @return ETable
     */
    public ETable get(int index)
    {
        return (ETable) components.get(index);
    }
    /**
     * ����λ��
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
     * �õ���Ա�б�
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
}
