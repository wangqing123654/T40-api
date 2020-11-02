package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: Text控制器保存扩展</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.16
 * @version 1.0
 */
public class MTextSave
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 构造器
     */
    public MTextSave()
    {
        components = new TList();
    }
    /**
     * 增加成员
     * @param text EText
     * @return int
     */
    public int add(EText text)
    {
        if (text == null)
            return -1;
        components.add(text);
        return components.size() - 1;
    }
    /**
     * 删除成员
     * @param text EText
     */
    public void remove(EText text)
    {
        components.remove(text);
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
     * @return EText
     */
    public EText get(int index)
    {
        return (EText) components.get(index);
    }
    /**
     * 查找位置
     * @param text EText
     * @return int
     */
    public int indexOf(EText text)
    {
        if(text == null)
            return -1;
        return components.indexOf(text);
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
