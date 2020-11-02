package com.dongyang.root.T.D;

import java.util.List;
import java.util.ArrayList;

public class Dl
{
    /**
     * 数据列表
     */
    private List list = new ArrayList();
    /**
     * 增加点
     * @param d D2
     */
    public void add(D2 d)
    {
        list.add(d);
    }
    /**
     * 得到点
     * @param index int
     * @return D2
     */
    public D2 get(int index)
    {
        return (D2)list.get(index);
    }
    /**
     * 得到尺寸
     * @return int
     */
    public int size()
    {
        return list.size();
    }
    /**
     * 得到点
     * @param type String
     * @return D2
     */
    public D2 get(String type)
    {
        if(type == null)
            return null;
        for(int i = 0;i < size();i++)
        {
            D2 d = get(i);
            if(type.equalsIgnoreCase(d.getType()))
                return d;
        }
        return null;
    }
    /**
     * 得到遗传单链
     * @return List
     */
    public List getR()
    {
        List l = new ArrayList();
        for(int i = 0;i < size();i++)
            l.add(get(i).getR());
        return l;
    }
    /**
     * 删除点
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
}
