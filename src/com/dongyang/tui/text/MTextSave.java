package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: Text������������չ</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.16
 * @version 1.0
 */
public class MTextSave
{
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    public MTextSave()
    {
        components = new TList();
    }
    /**
     * ���ӳ�Ա
     * @param text EText
     * @return int
     */
    public int add(EText text)
    {
        if (text == null)
            return -1;
        components.add(text);
        return components.size() - 1;
    }
    /**
     * ɾ����Ա
     * @param text EText
     */
    public void remove(EText text)
    {
        components.remove(text);
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
     * @return EText
     */
    public EText get(int index)
    {
        return (EText) components.get(index);
    }
    /**
     * ����λ��
     * @param text EText
     * @return int
     */
    public int indexOf(EText text)
    {
        if(text == null)
            return -1;
        return components.indexOf(text);
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
