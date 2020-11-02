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
public class MTRSave
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 构造器
     */
    public MTRSave()
    {
        components = new TList();
    }
    /**
     * 增加成员
     * @param tr ETR
     * @return int
     */
    public int add(ETR tr)
    {
        if (tr == null)
            return -1;
        components.add(tr);
        return components.size() - 1;
    }
    /**
     * 删除成员
     * @param tr ETR
     */
    public void remove(ETR tr)
    {
        components.remove(tr);
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
     * @return ETR
     */
    public ETR get(int index)
    {
        return (ETR) components.get(index);
    }
    /**
     * 查找位置
     * @param tr ETR
     * @return int
     */
    public int indexOf(ETR tr)
    {
        if(tr == null)
            return -1;
        return components.indexOf(tr);
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
