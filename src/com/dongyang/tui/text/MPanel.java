package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 面板控制类(保存扩充)</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.23
 * @version 1.0
 */
public class MPanel
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 构造器
     */
    public MPanel()
    {
        components = new TList();
    }
    /**
     * 增加成员
     * @param panel EPanel
     * @return int
     */
    public int add(EPanel panel)
    {
        if (panel == null)
            return -1;
        components.add(panel);
        return components.size() - 1;
    }
    /**
     * 删除成员
     * @param panel EPanel
     */
    public void remove(EPanel panel)
    {
        components.remove(panel);
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
     * @return EPanel
     */
    public EPanel get(int index)
    {
        return (EPanel) components.get(index);
    }
    /**
     * 查找位置
     * @param panel EPanel
     * @return int
     */
    public int indexOf(EPanel panel)
    {
        if(panel == null)
            return -1;
        return components.indexOf(panel);
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
