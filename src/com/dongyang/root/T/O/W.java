package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 1009.1.6
 * @version 1.0
 */
public class W
        implements java.io.Serializable
{
    /**
     * ���б�
     */
    private List cs;
    /**
     * ������
     */
    public W()
    {
        cs = new ArrayList();
    }
    /**
     * �ߴ�
     * @return int
     */
    public int size()
    {
        return cs.size();
    }
    /**
     * �õ���
     * @param index int
     * @return C
     */
    public C get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (C)cs.get(index);
    }
    /**
     * ������
     * @param index int
     * @param c C
     */
    public void set(int index,C c)
    {
        if(index < 0 || index >= size())
            return;
        cs.set(index,c);
    }
    /**
     * ɾ����
     * @param index int
     */
    public void remove(int index)
    {
        cs.remove(index);
    }
    /**
     * ������
     * @param c C
     */
    public void add(C c)
    {
        cs.add(c);
    }
    /**
     * ������
     * @param index int λ��
     * @param c C
     */
    public void add(int index,C c)
    {
        if(index < 0 || index >= size())
        {
            cs.add(c);
            return;
        }
        cs.add(index,c);
    }
    /**
     * �õ��ִ�
     * @return String
     */
    public String getW()
    {
        StringBuffer sb = new StringBuffer();
        int count = size();
        for(int i = 0;i < count;i++)
            sb.append(get(i).getC());
        return sb.toString();
    }
    /**
     * �����ִ�
     * @param s String
     * @param cl CL
     */
    public void setW(String s,CL cl)
    {
        if(cl == null)
            return;
        for(int i = 0;i < s.length();i++)
            add(cl.find("" + s.charAt(i)));
    }
    public String toString()
    {
        return getW();
    }
}
