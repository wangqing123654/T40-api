package com.dongyang.db;

public class TWhere
{
    /**
     * ��ǩ
     */
    private String tag;
    /**
     * where ���
     */
    private String where;
    /**
     * ���ñ�ǩ
     * @param tag String
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    /**
     * �õ���ǩ
     * @return String
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * ����Where
     * @param where String
     */
    public void setWhere(String where)
    {
        this.where = where;
    }
    /**
     * �õ�where
     * @return String
     */
    public String getWhere()
    {
        return where;
    }
}
