package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: �洢</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.6
 * @version 1.0
 */
public class S
        implements java.io.Serializable
{
    private List data;
    /**
     * ������
     */
    public S()
    {
        data = new ArrayList();
    }
    /**
     * �ߴ�
     * @return int
     */
    public int size()
    {
        return data.size();
    }
    /**
     * ���ӱ���
     * @param name String
     * @param obj Object
     */
    public void add(String name,Object obj)
    {
        if(exist(name))
            return;
        Data d = new Data();
        d.name = name;
        d.data = obj;
        data.add(d);
    }
    /**
     * ɾ��
     * @param name String
     */
    public void remove(String name)
    {
        int count = size();
        for(int i = 0;i < count;i ++)
        {
            if(getData(i).name.equals(name))
            {
                data.remove(i);
                return;
            }
        }
    }
    /**
     * �õ�
     * @param name String
     * @return Object
     */
    public Object get(String name)
    {
        return getData(name).data;
    }
    /**
     * �õ�ֵ
     * @param index int
     * @return Object
     */
    public Object get(int index)
    {
        Data d = getData(index);
        if(d == null)
            return null;
        return d.data;
    }
    /**
     * �õ�����
     * @param index int
     * @return String
     */
    public String getName(int index)
    {
        Data d = getData(index);
        if(d == null)
            return null;
        return d.name;
    }
    /**
     * ����
     * @param name String
     * @param obj Object
     */
    public void set(String name,Object obj)
    {
        Data d = getData(name);
        if(d == null)
            return;
        d.data = obj;
    }
    /**
     * �Ƿ����
     * @param name String
     * @return boolean
     */
    public boolean exist(String name)
    {
        return getData(name) != null;
    }
    /**
     * �õ�Data
     * @param index int
     * @return Data
     */
    private Data getData(int index)
    {
        return (Data)data.get(index);
    }
    /**
     * �õ�����
     * @param name String
     * @return Data
     */
    private Data getData(String name)
    {
        int count = size();
        for(int i = 0;i < count;i ++)
        {
            Data data = getData(i);
            if(data.name.equals(name))
                return data;
        }
        return null;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        int count = size();
        sb.append("S>>");
        sb.append(count);
        sb.append("[");
        for(int i = 0;i < count;i ++)
        {
            Data d = getData(i);
            sb.append(d.name);
            sb.append("=");
            sb.append(d.data);
            if(i < count - 1)
                sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
    class Data
            implements java.io.Serializable
    {
        public String name;
        public Object data;
    }
}
