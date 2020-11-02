package com.dongyang.db;

import java.util.Vector;
/**
 *
 * <p>Title: 扩展WhereList</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.8
 * @version 1.0
 */
public class TEWhereList
{
    /**
     * 数据包
     */
    private Vector data;
    /**
     * 构造器
     */
    public TEWhereList()
    {
        data = new Vector();
    }
    /**
     * 增加WhereList对象
     * @param tag String
     * @param WhereList TWhereList
     */
    public void addWhereList(String tag,TWhereList WhereList)
    {
        if(getTWhereList(tag) != null)
            return;
        WhereList.setTag(tag);
        data.add(WhereList);
    }
    /**
     * 删除TWhereList对象
     * @param tag String
     */
    public void removeWhereList(String tag)
    {
        int index = getTWhereIndex(tag);
        if(index < 0 || index >= getSize())
            return;
        data.remove(index);
    }
    /**
     * 删除全部的TWhereList对象
     */
    public void removeAllWhereList()
    {
        data = new Vector();
    }
    /**
     * 得到WhereList
     * @param index int
     * @return TWhereList
     */
    public TWhereList getTWhereList(int index)
    {
        if(index < 0 || index >= getSize())
            return null;
        return (TWhereList)data.get(index);
    }
    /**
     * 得到TWhereList对象的位置
     * @param tag String
     * @return int
     */
    public int getTWhereIndex(String tag)
    {
        int count = getSize();
        for(int i = 0;i < count;i ++)
        {
            TWhereList whereList = (TWhereList)data.get(i);
            if(whereList == null)
                continue;
            if(whereList.getTag().equalsIgnoreCase(tag))
                return i;
        }
        return -1;
    }
    /**
     * 得到WhereList
     * @param tag String
     * @return TWhereList
     */
    public TWhereList getTWhereList(String tag)
    {
        return getTWhereList(getTWhereIndex(tag));
    }
    /**
     * 得到尺寸
     * @return int
     */
    public int getSize()
    {
        return data.size();
    }
}
