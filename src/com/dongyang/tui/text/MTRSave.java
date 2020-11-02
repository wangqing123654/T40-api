package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: TD������������չ</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.7
 * @version 1.0
 */
public class MTRSave
{
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    public MTRSave()
    {
        components = new TList();
    }
    /**
     * ���ӳ�Ա
     * @param tr ETR
     * @return int
     */
    public int add(ETR tr)
    {
        if (tr == null)
            return -1;
        components.add(tr);
        return components.size() - 1;
    }
    /**
     * ɾ����Ա
     * @param tr ETR
     */
    public void remove(ETR tr)
    {
        components.remove(tr);
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
     * @return ETR
     */
    public ETR get(int index)
    {
        return (ETR) components.get(index);
    }
    /**
     * ����λ��
     * @param tr ETR
     * @return int
     */
    public int indexOf(ETR tr)
    {
        if(tr == null)
            return -1;
        return components.indexOf(tr);
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
