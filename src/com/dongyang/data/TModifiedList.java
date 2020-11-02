package com.dongyang.data;

import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public class TModifiedList
{
    public static String PRIMARY = "Primary";
    public static String NEW = "New";
    public static String MODIFIED = "Modified";
    public static String DELETED = "Deleted";
    public static String ACTION = "Action";
    /**
     * 得到对照表
     * "属性名:字段名:OLD(可选);属性名:字段名"
     */
    private String mapString;
    private Map data = new HashMap();

    public TModifiedList()
    {
        addStore(PRIMARY);
        addStore(NEW);
        addStore(MODIFIED);
        addStore(DELETED);
        addStore(ACTION);
    }
    /**
     * 增加缓冲区
     * @param name String
     */
    public void addStore(String name)
    {
        data.put(name,new Vector());
    }
    /**
     * 得到缓冲区
     * @param name String
     * @return Vector
     */
    public Vector getStore(String name)
    {
        return (Vector)data.get(name);
    }
    /**
     * 删除缓冲区
     * @param name String
     */
    public void removeStore(String name)
    {
        data.remove(name);
    }
    /**
     * 新建一行数据(追加)
     * @param row TModifiedData
     */
    public void newData(TModifiedData row)
    {
        add(row);
        add(NEW,row);
    }
    /**
     * 新建一行数据(插入)
     * @param index int
     * @param row TModifiedData
     */
    public void newData(int index,TModifiedData row)
    {
        add(index,row);
        add(NEW,row);
    }
    /**
     * 删除一行
     * @param row TModifiedData
     */
    public void removeData(TModifiedData row)
    {
        int index = indexOf(PRIMARY,row);
        if(index == -1)
            return;
        remove(row);
        index = indexOf(NEW,row);
        if(index >= 0)
        {
            remove(NEW,row);
            return;
        }
        index = indexOf(MODIFIED,row);
        if(index >= 0)
            remove(MODIFIED,row);
        add(DELETED,row);
    }
    /**
     * 删除一行
     * @param index int
     */
    public void removeData(int index)
    {
        if(index < 0 || index >= size())
            return;
        TModifiedData row = get(index);
        remove(row);
        index = indexOf(NEW,row);
        if(index >= 0)
        {
            remove(NEW,row);
            return;
        }
        index = indexOf(MODIFIED,row);
        if(index >= 0)
            remove(MODIFIED,row);
        add(DELETED,row);
    }
    /**
     * 删除全部
     */
    public void removeDataAll()
    {
        for(int i = size() -1;i >= 0;i--)
            removeData(i);
    }
    /**
     * 修改一行数据
     * @param row TModifiedData
     */
    public void modifyData(TModifiedData row)
    {
        if(row.isModified())
        {
            if (indexOf(NEW, row) == -1 && indexOf(MODIFIED, row) == -1)
                add(MODIFIED, row);
            return;
        }
        if (indexOf(MODIFIED, row) >= 0)
            remove(MODIFIED, row);
    }
    public void add(int index,TModifiedData row)
    {
        add(PRIMARY,index,row);
    }
    public void add(String storeName,int index,TModifiedData row)
    {
        Vector store = getStore(storeName);
        if(store == null)
            return;
        store.add(index,row);
    }
    public void add(TModifiedData row)
    {
        row.setParentList(this);
        add(PRIMARY,row);
    }
    public void add(String storeName,TModifiedData row)
    {
        Vector store = getStore(storeName);
        if(store == null)
            return;
        store.add(row);
    }
    public void remove(TModifiedData row)
    {
        remove(PRIMARY,row);
    }
    public void remove(String storeName,TModifiedData row)
    {
        Vector store = getStore(storeName);
        if(store == null)
            return;
        store.remove(row);
    }
    public TModifiedData get(int index)
    {
        return get(PRIMARY,index);
    }
    public TModifiedData get(String storeName,int index)
    {
        Vector store = getStore(storeName);
        if(store == null)
            return null;
        return (TModifiedData)store.get(index);
    }
    /**
     * 查找对象位置
     * @param storeName String
     * @param row TModifiedData
     * @return int
     */
    public int indexOf(String storeName,TModifiedData row)
    {
        Vector store = getStore(storeName);
        if(store == null)
            return -1;
        int count = store.size();
        for(int i = 0;i < count;i ++)
        {
            if(store.get(i) == row)
                return i;
        }
        return -1;
    }
    public int size()
    {
        return size(PRIMARY);
    }
    public int size(String storeName)
    {
        Vector store = getStore(storeName);
        if(store == null)
            return 0;
        return store.size();
    }
    /**
     * 设置对照表
     * @param mapString String "属性名:字段名:OLD(可选);属性名:字段名"
     */
    public void setMapString(String mapString)
    {
    	//System.out.println("=======setMapString======"+mapString);
        this.mapString = mapString;
    }
    /**
     * 得到对照表
     * @return String "属性名:字段名:OLD(可选);属性名:字段名"
     */
    public String getMapString()
    {
    	//System.out.println("=======getMapString======"+mapString);
        return mapString;
    }
    public TParm getParm(String storeName)
    {
        return getParm(storeName,false);
    }
    public TParm getParm(String storeName,boolean hasObject)
    {
        TParm result = new TParm();
        return getParm(storeName,result,hasObject);
    }
    public TParm getParm(String storeName,TParm result)
    {
        return getParm(storeName,result,false);
    }
    public TParm getParm(String storeName,TParm result,boolean hasObject)
    {
    	//System.out.println("======getParm come in========");
        Vector store = getStore(storeName);
        if(store == null)
            return result;
        int count = store.size();
        //System.out.println("======count========"+count);
        
        for(int i = 0;i < count;i++)
        {
            TModifiedData modifiedData = (TModifiedData)store.get(i);
            if(modifiedData == null)
            {
                //System.out.println("TModifiedData is null index " + i);
                return result;
            }
            TParm p = modifiedData.getParm();
            //System.out.println("=====modifiedData getParm==========="+p);
            if(hasObject)
                p.setData("OBJECT",modifiedData);
            int c = result.getCount();
            if(c == -1)
                c = 0;
            result.setRowData(c,p);
            result.setData("ACTION","COUNT",c + 1);
        }
        return result;
    }
    public TParm getParm()
    {
        TParm result = new TParm();
        result.setData(NEW,getParm(NEW).getData());
        result.setData(MODIFIED,getParm(MODIFIED).getData());
        result.setData(DELETED,getParm(DELETED).getData());
        return result;
    }
    /**
     * Table
     * @return TParm
     */
    public TParm getTableParm()
    {
        return getParm(PRIMARY,true);
    }
    /**
     * 重置
     */
    public void reset()
    {
        addStore(NEW);
        addStore(MODIFIED);
        addStore(DELETED);
        addStore(ACTION);
        Vector store = getStore(PRIMARY);
        if(store == null)
            return;
        int count = store.size();
        for(int i = 0;i < count;i++)
            ((TModifiedData)store.get(i)).reset();
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TModifiedList{ ");
        sb.append(PRIMARY);
        sb.append(".count=");
        sb.append(size(PRIMARY));
        sb.append(" ");
        sb.append(NEW);
        sb.append(".count=");
        sb.append(size(NEW));
        sb.append(" ");
        sb.append(MODIFIED);
        sb.append(".count=");
        sb.append(size(MODIFIED));
        sb.append(" ");
        sb.append(DELETED);
        sb.append(".count=");
        sb.append(size(DELETED));
        sb.append("}");
        return sb.toString();
    }
}
