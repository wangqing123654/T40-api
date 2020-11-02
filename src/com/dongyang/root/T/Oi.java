package com.dongyang.root.T;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: 触角</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.30
 * @version 1.0
 */
public class Oi
        implements java.io.Serializable
{
    /**
     * 链路列表
     */
    private List list = new ArrayList();
    /**
     * 外壳
     */
    private Ot ot;
    /**
     * 最大编号
     */
    private int max;
    /**
     * 设置外壳
     * @param ot Ot
     */
    public void setOt(Ot ot)
    {
        this.ot = ot;
    }
    /**
     * 得到外壳
     * @return Ot
     */
    public Ot getOt()
    {
        return ot;
    }
    class S
            implements java.io.Serializable
    {
        public Ot in;
        public int id;
        public int count;
        public int f;
        public String toString()
        {
            StringBuffer sb = new StringBuffer();
            sb.append("<S id=");
            sb.append(id);
            sb.append(",in=");
            sb.append(in.getID());
            sb.append(",f=");
            sb.append(f);
            sb.append(",count=");
            sb.append(count);
            sb.append(" /S>");
            return sb.toString();
        }
    }
    /**
     * 连接
     * @param ot Ot
     * @param f int 方向 1 出 -1 入
     * @return boolean
     */
    public synchronized boolean link(Ot ot,int f)
    {
        if(getS(ot) != null)
            return false;
        max ++;
        S s = new S();
        s.in = ot;
        s.count = 1;
        s.id = max;
        s.f = f;
        add(s);
        return true;
    }
    /**
     * 增加
     * @param s S
     */
    private void add(S s)
    {
        if(s.f < 0)
        {
            list.add(s);
            return;
        }
        int index = getFIndex();
        if(index < 0)
            list.add(s);
        else
            list.add(index,s);
    }
    /**
     * 得到负通道首地址
     * @return int
     */
    private int getFIndex()
    {
        int count = size();
        for(int i = 0; i < count;i++)
        {
            if(get(i).f < 0)
                return i;
        }
        return -1;
    }
    /**
     * 得到尺寸
     * @return int
     */
    public synchronized int size()
    {
        return list.size();
    }
    /**
     * 得到S
     * @param i int 位置
     * @return S
     */
    private S get(int i)
    {
        if(i < 0 || i >= size())
            return null;
        return (S)list.get(i);
    }
    /**
     * 得到索引
     * @param id int
     * @return int
     */
    private int getI(int id)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            S s = get(i);
            if(s.id == id)
                return i;
        }
        return -1;
    }
    /**
     * 得到索引
     * @param ot Ot
     * @return int
     */
    private int getI(Ot ot)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            S s = get(i);
            if(s.in == ot)
                return i;
        }
        return -1;
    }
    /**
     * 得到S
     * @param id int
     * @return S
     */
    private S getS(int id)
    {
        int index = getI(id);
        if(index < 0)
            return null;
        return get(index);
    }
    /**
     * 得到S
     * @param ot Ot
     * @return S
     */
    private S getS(Ot ot)
    {
        int index = getI(ot);
        if(index < 0)
            return null;
        return get(index);
    }
    /**
     * 得到连接位置
     * @param ot Ot
     * @return int
     */
    public synchronized int getSOt(Ot ot)
    {
        S s = getS(ot);
        if(s == null)
            return -1;
        return s.id;
    }
    /**
     * 得到Ot
     * @param id int ID
     * @return Ot
     */
    public synchronized Ot getSOt(int id)
    {
        S s = getS(id);
        if(s == null)
            return null;
        return s.in;
    }
    /**
     * 删除
     * @param ot Ot
     */
    public synchronized void remove(Ot ot)
    {
        removeT(getI(ot));
    }
    /**
     * 删除
     * @param id int
     */
    public synchronized void remove(int id)
    {
        removeT(getI(id));
    }
    /**
     * 删除(内部使用)
     * @param index int
     */
    private void removeT(int index)
    {
        if(index < 0)
            return;
        S s = get(index);
        list.remove(index);
        if(s.f < 0)
            return;
        if(s.in.getOi() == null)
            return;
        s.in.getOi().remove(getOt());
    }
    /**
     * 得到变数
     * @return int
     */
    private int getMaxChange()
    {
        int count = size();
        int n = 0;
        for(int i = 0;i < count;i++)
        {
            S s = get(i);
            if(s.f < 0)
                return n;
            n += s.count;
        }
        return n;
    }
    /**
     * 增强通道
     * @param id int
     * @return boolean
     */
    public synchronized boolean g(int id)
    {
        return gI(getI(id));
    }
    /**
     * 增强通道
     * @param ot Ot
     * @return boolean
     */
    public synchronized boolean g(Ot ot)
    {
        return gI(getI(ot));
    }
    /**
     * 增强通道
     * @param index int
     * @return boolean
     */
    private boolean gI(int index)
    {
        S s = get(index);
        if (s == null || s.in == null || s.in.getOi() == null)
            return false;
        s.count++;
        if (s.f < 0)
            return true;
        s.in.getOi().g(getOt());
        gT(index);
        return true;
    }
    /**
     * 提升通道
     * @param index int
     */
    private void gT(int index)
    {
        if(index <= 0)
            return;
        S s = get(index);
        int x = -1;
        for(int i = index - 1;i >= 0;i--)
        {
            S s1 = get(i);
            if(s.count > s1.count)
                x = i;
            if(s.count < s1.count)
                break;
        }
        if(x < 0)
            return;
        list.remove(index);
        list.add(x,s);
    }
    /**
     * 选择端口
     * @return int
     */
    public synchronized int chooseIo()
    {
        int count = size();
        if(count <= 0)
            return -1;
        int n = (int)(getMaxChange() * Math.random());
        for(int i = 0; i < count;i ++)
        {
            S s = get(i);
            if(s.f < 0)
                continue;
            if(n < s.count)
                return s.id;
            n -= s.count;
        }
        return -1;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<Oi ot=");
        sb.append(ot == null?"null":ot.getID());
        sb.append(",max=");
        sb.append(max);
        sb.append(",list=");
        int count = size();
        sb.append(count);
        if(count > 0)
        {
            sb.append(",");
            for (int i = 0; i < count; i++)
                sb.append(get(i));
        }
        sb.append(" /Oi>");
        return sb.toString();
    }
    public static void main(String args[])
    {
        for(int i = 0;i < 10;i++)
            System.out.println(3 * Math.random());
    }
}
