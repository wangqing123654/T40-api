package com.dongyang.util;

import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.dongyang.tui.text.CSelectTextBlock;
import com.dongyang.tui.text.EText;

/**
 *
 * <p>Title: List 对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company:JavaHis</p>
 *
 * @author lzk 2009.1.20
 * @version 1.0
 */
public class TList implements java.io.Serializable
{
    /**
     * 数据
     */
    Object data[];
    /**
     * 尺寸
     */
    transient int size;
    /**
     * 构造器
     */
    public TList()
    {
        data = new Object[10];
    }
    public void setData(TList list)
    {
        data = list.data;
    }
    /**
     * 删除全部
     * @return TList
     */
    public TList removeAll()
    {
        TList l = new TList();
        l.data = data;
        l.size = size;
        data = new Object[10];
        size = 0;
        return l;
    }
    /**
     * 增加成员
     * @param obj Object
     */
    public void add(Object obj)
    {
        ensureCapacity(size + 1);
        data[size++] = obj;
    }
    /**
     * 增加成员(正序)
     * @param s Object
     */
    public void addSeqString(Object s)
    {
        int index = getInsertSeqTextIndex(s.toString());
        if(index < 0)
            add(s);
        else
            add(index,s);

    }
    /**
     * 得到插入的最佳位置
     * @param text String
     * @return int
     */
    private int getInsertSeqTextIndex(String text)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            String s1 = (String)get(i);
            if(s1 == null)
                continue;
            if(s1.compareTo(text) > 0)
                return i;
        }
        return -1;
    }
    /**
     * 增加成员(不可重复)
     * @param obj Object
     */
    public void put(Object obj)
    {
        int index = indexOf(obj);
        if(index != -1)
            return;
        add(obj);
    }
    /**
     * 插入成员
     * @param index int
     * @param obj Object
     */
    public void add(int index, Object obj) {
        if (index >= size || index < 0)
        {
            add(obj);
            return;
        }
        ensureCapacity(size+1);
        System.arraycopy(data, index, data, index + 1,
                         size - index);
        data[index] = obj;
        size++;
    }
    /**
     * 查找对象
     * @param elem Object
     * @return int
     */
    public int indexOf(Object elem) {
        if (elem == null) {
            for (int i = 0; i < size; i++)
                if (data[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (elem.equals(data[i]))
                    return i;
        }
        return -1;
    }
    /**
     * 计算内存增量
     * @param length int
     */
    private void ensureCapacity(int length) {
        int old = data.length;
        if (length > old) {
            Object oldData[] = data;
            int n = (old * 3)/2 + 1;
            if (n < length)
                n = length;
            data = new Object[n];
            System.arraycopy(oldData, 0, data, 0, size);
        }
    }
    /**
     * 得到元素
     * @param index int
     * @return Object
     */
    public Object get(int index)
    {
        return data[index];
    }
    /**
     * 设置元素
     * @param index int
     * @param value Object
     */
    public void set(int index,Object value)
    {
        if (index >= size || index < 0)
        {
            add(value);
            return;
        }
        data[index] = value;
    }
    /**
     * 删除元素
     * @param index int
     */
    public void remove(int index)
    {
        if(index == size - 1)
        {
            size --;
            return;
        }
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(data, index+1, data, index,
                             numMoved);
        size --;
    }
    /**
     * 删除对象
     * @param o Object
     * @return boolean
     */
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (data[index] == null) {              
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(data[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(data, index + 1, data, index,
                             numMoved);


        data[--size] = null;
    }
    /**
     * 得到尺寸
     * @return int
     */
    public int size()
    {
        return size;
    }
    /**
     * 追加List
     * @param list TList
     */
    public void addList(TList list)
    {
        if(list == null || list.size() == 0)
            return;
        for(int i = 0;i < list.size();i++)
            add(list.get(i));
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<TList>size=");
        sb.append(size);
        sb.append(" {");
        for(int i = 0;i < size;i++)
        {
            if(i > 0)
                sb.append(", ");
            sb.append(get(i));
        }
        sb.append("}");
        return sb.toString();
    }
    public void gc()
    {
        Object d[] = new Object[size];
        System.arraycopy(d, 0, data, 0, size);
        data = d;
    }
    private void writeObject(ObjectOutputStream s)
      throws IOException
    {
        gc();
        s.writeObject(data);
    }
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException
    {
        data = (Object[])s.readObject();
        size = data.length;
    }
    public static void main(String args[])
    {
        DebugUsingTime.start();
        ArrayList list1 = new ArrayList();
        for(int i = 0;i < 100000;i++)
            list1.add(i);
        DebugUsingTime.add("L create");

        DebugUsingTime.start();
        TList list = new TList();
        for(int i = 0;i < 100000;i++)
            list.add(i);
        DebugUsingTime.add("T create");

        System.out.println(list.size);

        try{
            com.dongyang.util.FileTool.setObject("c:\\aaa\\test.w", list);
            TList listw = (TList)com.dongyang.util.FileTool.getObject("c:\\aaa\\test.w");
            System.out.println(listw.size);
        }catch(Exception e)
        {

        }


        /*System.out.println(list.get(1025));
        System.out.println(list1.get(1025));

        DebugUsingTime.start();
        for(int j = 0;j < 10000;j++)
            for(int i = j;i >= 0;i--)
                list1.get(i);
        DebugUsingTime.add("L get");

        DebugUsingTime.start();
        for(int j = 0;j < 10000;j++)
            for(int i = j;i >= 0;i--)
                list.get(i);
        DebugUsingTime.add("T get");

        DebugUsingTime.start();
        for(int j = 0;j < 10000;j++)
            for(int i = j;i >= 0;i--)
                list1.set(i,i);
        DebugUsingTime.add("L set");

        DebugUsingTime.start();
        for(int j = 0;j < 10000;j++)
            for(int i = j;i >= 0;i--)
                list.set(i,i);
        DebugUsingTime.add("T set");

        DebugUsingTime.start();
        for(int i = 10000 - 1;i >= 0;i--)
            list1.remove(i);
        DebugUsingTime.add("L remove");

        DebugUsingTime.start();
        for(int i = 10000 - 1;i >= 0;i--)
            list.remove(i);
        DebugUsingTime.add("T remove");*/

    }
}
