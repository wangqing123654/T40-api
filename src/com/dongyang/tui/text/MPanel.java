package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: ��������(��������)</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.23
 * @version 1.0
 */
public class MPanel
{
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    public MPanel()
    {
        components = new TList();
    }
    /**
     * ���ӳ�Ա
     * @param panel EPanel
     * @return int
     */
    public int add(EPanel panel)
    {
        if (panel == null)
            return -1;
        components.add(panel);
        return components.size() - 1;
    }
    /**
     * ɾ����Ա
     * @param panel EPanel
     */
    public void remove(EPanel panel)
    {
        components.remove(panel);
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
     * @return EPanel
     */
    public EPanel get(int index)
    {
        return (EPanel) components.get(index);
    }
    /**
     * ����λ��
     * @param panel EPanel
     * @return int
     */
    public int indexOf(EPanel panel)
    {
        if(panel == null)
            return -1;
        return components.indexOf(panel);
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
