package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: �ֶι�����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @version 1.0
 */
public class MString
{
    /**
     * ��Ա�б�
     */
    private TList components;

   /**
    * ������
    */
   private PM pm;

    /**
     * ������
     */
    public MString()
    {
        components = new TList();
    }
    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }

    /**
     * ���ӳ�Ա
     * @param s DString
     */
    public void add(DString s)
    {
        if (s == null)
            return;
        components.add(s);
    }

    /**
     * ɾ����Ա
     * @param s DString
     */
    public void remove(DString s)
    {
        components.remove(s);
    }
    /**
     * ȫ�����
     */
    public void removeAll()
    {
        components.removeAll();
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
     * @return DString
     */
    public DString get(int index)
    {
        return (DString) components.get(index);
    }

    /**
     * �õ���Ա�б�
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * ���ø���
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getParent()
    {
        return null;
    }
}
