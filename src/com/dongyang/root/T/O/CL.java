package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: �ֿ�</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.6
 * @version 1.0
 */
public class CL
        implements java.io.Serializable
{
    /**
     * ����
     */
    private List data;
    /**
     * ������
     */
    public CL()
    {
        data = new ArrayList();
    }
    /**
     * �ߴ�
     * @return int
     */
    public int size()
    {
        return data.size();
    }
    /**
     * ����
     * @param c C
     */
    private void add(C c)
    {
        data.add(c);
    }
    /**
     * ɾ��C
     * @param index int
     */
    private void remove(int index)
    {
        if(index < 0 || index >= size())
            return;
        data.remove(index);
    }
    /**
     * ����C��λ��
     * @param c C
     * @return int
     */
    private int findC(C c)
    {
        int count = size();
        for(int i = 0;i < count;i++)
            if(get(i) == c)
                return i;
        return -1;
    }
    /**
     * ����C
     * @param s String
     * @return C
     */
    private C findC(String s)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            C c = get(i);
            if(c.getC().equals(s))
            {
                up(i);
                return c;
            }
        }
        return null;
    }
    private void up(int index)
    {
        if(index <= 0)
            return;
        C c = get(index - 1);
        set(index - 1,get(index));
        set(index,c);
    }
    /**
     * �õ�C
     * @param index int
     * @return C
     */
    private C get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (C)data.get(index);
    }
    /**
     * ����C
     * @param index int
     * @param c C
     */
    private void set(int index, C c)
    {
        if(index < 0 || index >= size())
            return;
        data.set(index,c);
    }
    /**
     * ����C
     * @param s String
     * @return C
     */
    public C find(String s)
    {
        C c = findC(s);
        if(c != null)
        {
            return c;
        }
        c = new C(s);
        add(c);
        return c;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("CL>>Size=");
        int count = size();
        sb.append(count);
        sb.append("\n");
        for(int i = 0;i < count;i++)
        {
            sb.append(get(i).getC());
            sb.append(" ");
        }
        return sb.toString();
    }
}
