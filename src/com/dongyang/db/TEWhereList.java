package com.dongyang.db;

import java.util.Vector;
/**
 *
 * <p>Title: ��չWhereList</p>
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
     * ���ݰ�
     */
    private Vector data;
    /**
     * ������
     */
    public TEWhereList()
    {
        data = new Vector();
    }
    /**
     * ����WhereList����
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
     * ɾ��TWhereList����
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
     * ɾ��ȫ����TWhereList����
     */
    public void removeAllWhereList()
    {
        data = new Vector();
    }
    /**
     * �õ�WhereList
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
     * �õ�TWhereList�����λ��
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
     * �õ�WhereList
     * @param tag String
     * @return TWhereList
     */
    public TWhereList getTWhereList(String tag)
    {
        return getTWhereList(getTWhereIndex(tag));
    }
    /**
     * �õ��ߴ�
     * @return int
     */
    public int getSize()
    {
        return data.size();
    }
}
