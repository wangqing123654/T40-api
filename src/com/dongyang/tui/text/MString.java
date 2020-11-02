package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 字段管理类</p>
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
     * 成员列表
     */
    private TList components;

   /**
    * 管理器
    */
   private PM pm;

    /**
     * 构造器
     */
    public MString()
    {
        components = new TList();
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }

    /**
     * 增加成员
     * @param s DString
     */
    public void add(DString s)
    {
        if (s == null)
            return;
        components.add(s);
    }

    /**
     * 删除成员
     * @param s DString
     */
    public void remove(DString s)
    {
        components.remove(s);
    }
    /**
     * 全部清除
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * 得到成员
     * @param index int
     * @return DString
     */
    public DString get(int index)
    {
        return (DString) components.get(index);
    }

    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * 设置父类
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
    }
    /**
     * 得到父类
     * @return EComponent
     */
    public EComponent getParent()
    {
        return null;
    }
}
