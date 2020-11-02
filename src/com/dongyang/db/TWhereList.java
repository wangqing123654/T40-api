package com.dongyang.db;

import java.util.Vector;
import java.util.ArrayList;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: ��̬Where�б�</p>
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
     * ���ݰ�
     */
    private Vector data;
    /**
     * ��ǩ
     */
    private String tag;
    /**
     * ���
     */
    private String sql;
    /**
     * ������
     */
    public TWhereList()
    {
        data = new Vector();
    }
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
     * ����SQL���
     * @param sql String
     */
    public void setSql(String sql)
    {
        this.sql = sql;
    }
    /**
     * �õ�SQL���
     * @return String
     */
    public String getSql()
    {
        return sql;
    }
    /**
     * ����Where����
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
     * ɾ��TWhere����
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
     * ɾ��ȫ����TWhere����
     */
    public void removeAllWhere()
    {
        data = new Vector();
    }
    /**
     * ����Where���
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
     * �õ�Where���
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
     * �õ�Where���
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
     * �õ�Tag
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
     * �õ�ȫ��Tag����
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
     * �õ��ߴ�
     * @return int
     */
    public int getSize()
    {
        return data.size();
    }
    /**
     * �õ�TWhere����
     * @param index int λ��
     * @return TWhere
     */
    public TWhere getTWhere(int index)
    {
        if(index < 0 || index >= getSize())
            return null;
        return (TWhere)data.get(index);
    }
    /**
     * �õ�Tag
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
     * �õ�TWhere�����λ��
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
     * �õ�TWhere����
     * @param tag String ��ǩ
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
     * ����Tag
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
