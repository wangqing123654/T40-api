package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: TD控制器保存扩展</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.7
 * @version 1.0
 */
public class MTDSave
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 构造器
     */
    public MTDSave()
    {
        components = new TList();
    }
    /**
     * 增加成员
     * @param td ETD
     * @return int
     */
    public int add(ETD td)
    {
        if (td == null)
            return -1;
        components.add(td);
        return components.size() - 1;
    }
    /**
     * 删除成员
     * @param td ETD
     */
    public void remove(ETD td)
    {
        components.remove(td);
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
     * @return ETD
     */
    public ETD get(int index)
    {
        return (ETD) components.get(index);
    }
    /**
     * 查找位置
     * @param td ETD
     * @return int
     */
    public int indexOf(ETD td)
    {
        if(td == null)
            return -1;
        return components.indexOf(td);
    }
    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
}
