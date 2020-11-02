package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: �ʿ�</p>
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
public class WL
        implements java.io.Serializable
{
    /**
     * �ֿ�
     */
    private CL cl;
    /**
     * ����
     */
    private List data;
    /**
     * ������
     */
    public WL()
    {
        data = new ArrayList();
    }
    /**
     * �����ֿ�
     * @param cl CL
     */
    public void setCL(CL cl)
    {
        this.cl = cl;
    }
    /**
     * �õ��ֿ�
     * @return CL
     */
    public CL getCL()
    {
        return cl;
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
     * @param w W
     */
    private void add(W w)
    {
        data.add(w);
    }
    /**
     * ɾ��W
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
     * @param w W
     * @return int
     */
    private int findC(W w)
    {
        int count = size();
        for(int i = 0;i < count;i++)
            if(get(i) == w)
                return i;
        return -1;
    }
    /**
     * ����C
     * @param s String
     * @return W
     */
    private W find(String s)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            W w = get(i);
            if(w.getW().equals(s))
            {
                up(i);
                return w;
            }
        }
        return null;
    }
    private void up(int index)
    {
        if(index <= 0)
            return;
        W w = get(index - 1);
        set(index - 1,get(index));
        set(index,w);
    }
    /**
     * �õ�W
     * @param index int
     * @return W
     */
    private W get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (W)data.get(index);
    }
    /**
     * ����W
     * @param index int
     * @param w W
     */
    private void set(int index, W w)
    {
        if(index < 0 || index >= size())
            return;
        data.set(index,w);
    }
    /**
     * ����C
     * @param s String
     * @return W
     */
    public W add(String s)
    {
        W w = new W();
        w.setW(s,getCL());
        add(w);
        return w;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("WL>>Size=");
        int count = size();
        sb.append(count);
        sb.append("\n");
        for(int i = 0;i < count;i++)
        {
            sb.append(get(i).getW());
            sb.append(" ");
        }
        return sb.toString();
    }
    public static void main(String args[])
    {
        CL cl = new CL();
        WL wl = new WL();
        wl.setCL(cl);
        wl.add("��");
        wl.add("����");
        System.out.println(wl);
        System.out.println(cl);
    }
}
