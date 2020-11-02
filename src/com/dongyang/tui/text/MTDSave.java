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
public class MTDSave
{
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    public MTDSave()
    {
        components = new TList();
    }
    /**
     * ���ӳ�Ա
     * @param td ETD
     * @return int
     */
    public int add(ETD td)
    {
        if (td == null)
            return -1;
        components.add(td);
        return components.size() - 1;
    }
    /**
     * ɾ����Ա
     * @param td ETD
     */
    public void remove(ETD td)
    {
        components.remove(td);
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
     * @return ETD
     */
    public ETD get(int index)
    {
        return (ETD) components.get(index);
    }
    /**
     * ����λ��
     * @param td ETD
     * @return int
     */
    public int indexOf(ETD td)
    {
        if(td == null)
            return -1;
        return components.indexOf(td);
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
