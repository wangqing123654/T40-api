package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: 词</p>
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
     * 字列表
     */
    private List cs;
    /**
     * 构造器
     */
    public W()
    {
        cs = new ArrayList();
    }
    /**
     * 尺寸
     * @return int
     */
    public int size()
    {
        return cs.size();
    }
    /**
     * 得到字
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
     * 设置字
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
     * 删除字
     * @param index int
     */
    public void remove(int index)
    {
        cs.remove(index);
    }
    /**
     * 增加字
     * @param c C
     */
    public void add(C c)
    {
        cs.add(c);
    }
    /**
     * 增加字
     * @param index int 位置
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
     * 得到字串
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
     * 设置字串
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
