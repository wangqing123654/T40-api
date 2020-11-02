package com.dongyang.db;

public class TWhere
{
    /**
     * 标签
     */
    private String tag;
    /**
     * where 语句
     */
    private String where;
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
     * 设置Where
     * @param where String
     */
    public void setWhere(String where)
    {
        this.where = where;
    }
    /**
     * 得到where
     * @return String
     */
    public String getWhere()
    {
        return where;
    }
}
