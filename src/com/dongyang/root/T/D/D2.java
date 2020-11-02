package com.dongyang.root.T.D;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: 因子对</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.29
 * @version 1.0
 */
public class D2
        implements java.io.Serializable
{
    /**
     * 数据列表
     */
    private List list = new ArrayList();
    /**
     * 增加点
     * @param d DBase
     */
    public void add(DBase d)
    {
        list.add(d);
    }
    /**
     * 得到点
     * @param index int
     * @return DBase
     */
    public DBase get(int index)
    {
        return (DBase)list.get(index);
    }
    /**
     * 得到点
     * @return DBase
     */
    public DBase getM()
    {
        return get(0);
    }
    /**
     * 得到遗传点
     * @return DBase
     */
    public DBase getR()
    {
        return get((int)(Math.random() * list.size()));
    }
    /**
     * 删除点
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return get(0).getType();
    }
}
