package com.dongyang.root.T.D;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: ���Ӷ�</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.29
 * @version 1.0
 */
public class D2
        implements java.io.Serializable
{
    /**
     * �����б�
     */
    private List list = new ArrayList();
    /**
     * ���ӵ�
     * @param d DBase
     */
    public void add(DBase d)
    {
        list.add(d);
    }
    /**
     * �õ���
     * @param index int
     * @return DBase
     */
    public DBase get(int index)
    {
        return (DBase)list.get(index);
    }
    /**
     * �õ���
     * @return DBase
     */
    public DBase getM()
    {
        return get(0);
    }
    /**
     * �õ��Ŵ���
     * @return DBase
     */
    public DBase getR()
    {
        return get((int)(Math.random() * list.size()));
    }
    /**
     * ɾ����
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return get(0).getType();
    }
}
