package com.dongyang.db;

import java.util.Vector;
import java.util.ArrayList;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 动态Where列表</p>
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
public class TWhereList
{
    /**
     * 数据包
     */
    private Vector data;
    /**
     * 标签
     */
    private String tag;
    /**
     * 语句
     */
    private String sql;
    /**
     * 构造器
     */
    public TWhereList()
    {
        data = new Vector();
    }
    /**
     * 设置标签
     * @param tag String
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    /**
     * 得到标签
     * @return String
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * 设置SQL语句
     * @param sql String
     */
    public void setSql(String sql)
    {
        this.sql = sql;
    }
    /**
     * 得到SQL语句
     * @return String
     */
    public String getSql()
    {
        return sql;
    }
    /**
     * 增加Where对象
     * @param tag String
     * @param whereSQL String
     */
    public void addWhere(String tag,String whereSQL)
    {
        if(getTWhere(tag) != null)
            return;
        TWhere where = new TWhere();
        where.setTag(tag);
        where.setWhere(whereSQL);
        data.add(where);
    }
    /**
     * 删除TWhere对象
     * @param tag String
     */
    public void removeWhere(String tag)
    {
        int index = getTWhereIndex(tag);
        if(index < 0 || index >= getSize())
            return;
        data.remove(index);
    }
    /**
     * 删除全部的TWhere对象
     */
    public void removeAllWhere()
    {
        data = new Vector();
    }
    /**
     * 设置Where语句
     * @param tag String
     * @param whereSQL String
     */
    public void setWhere(String tag,String whereSQL)
    {
        TWhere where = getTWhere(tag);
        if(where == null)
            return;
        where.setWhere(whereSQL);
    }
    /**
     * 得到Where语句
     * @param tag String
     * @return String
     */
    public String getWhere(String tag)
    {
        TWhere where = getTWhere(tag);
        if(where == null)
            return "";
        return where.getWhere();
    }
    /**
     * 得到Where语句
     * @param index int
     * @return String
     */
    public String getWhere(int index)
    {
        TWhere where = getTWhere(index);
        if(where == null)
            return "";
        return where.getWhere();
    }
    /**
     * 得到Tag
     * @param index int
     * @return String
     */
    public String getTag(int index)
    {
        TWhere where = getTWhere(index);
        if(where == null)
            return null;
        return where.getTag();
    }
    /**
     * 得到全部Tag数组
     * @return String[]
     */
    public String[] getTags()
    {
        ArrayList list = new ArrayList();
        int count = getSize();
        for(int i = 0;i < count;i ++)
        {
            TWhere where = getTWhere(i);
            if(where == null)
                continue;
            list.add(where.getTag());
        }
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 得到尺寸
     * @return int
     */
    public int getSize()
    {
        return data.size();
    }
    /**
     * 得到TWhere对象
     * @param index int 位置
     * @return TWhere
     */
    public TWhere getTWhere(int index)
    {
        if(index < 0 || index >= getSize())
            return null;
        return (TWhere)data.get(index);
    }
    /**
     * 得到Tag
     * @param index int
     * @return String
     */
    public String getTWhereTag(int index)
    {
        TWhere twhere = getTWhere(index);
        if(twhere == null)
            return "";
        return twhere.getTag();
    }
    /**
     * 得到TWhere对象的位置
     * @param tag String
     * @return int
     */
    public int getTWhereIndex(String tag)
    {
        int count = getSize();
        for(int i = 0;i < count;i ++)
        {
            TWhere where = getTWhere(tag);
            if(where == null)
                continue;
            if(where.getTag().equalsIgnoreCase(tag))
                return i;
        }
        return -1;
    }
    /**
     * 得到TWhere对象
     * @param tag String 标签
     * @return TWhere
     */
    public TWhere getTWhere(String tag)
    {
        int count = getSize();
        for(int i = 0;i < count;i ++)
        {
            TWhere where = getTWhere(i);
            if(where == null)
                continue;
            if(where.getTag().equalsIgnoreCase(tag))
                return where;
        }
        return null;
    }
    /**
     * 存在Tag
     * @param parm TParm
     * @return boolean
     */
    public boolean existTag(TParm parm)
    {
        int count = getSize();
        for(int i = 0;i < count;i++)
        {
            String tag = getTWhereTag(i);
            if(parm.existData(tag))
                return true;
        }
        return false;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TWhereList{\n");
        int count = getSize();
        for(int i = 0;i < count;i++)
        {
            TWhere where = getTWhere(i);
            sb.append("  ");
            sb.append(where.getTag());
            sb.append("  ->");
            sb.append(where.getWhere());
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
