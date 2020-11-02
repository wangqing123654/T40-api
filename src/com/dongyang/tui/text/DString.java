package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 字段</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @version 1.0
 */
public class DString
{
    /**
     * 数据
     */
    private char[] data;
    /**
     * 尺寸
     */
    private transient int size;
    /**
     * 构造器
     */
    public DString()
    {
        data = new char[10];
    }
    /**
     * 构造器
     * @param s String
     */
    public DString(String s)
    {
        this();
        add(s);
    }
    /**
     * 增加字符
     * @param c char
     */
    public void add(char c)
    {
        ensureCapacityData(size + 1);
        data[size++] = c;
    }
    /**
     * 增加字符串
     * @param s String
     */
    public void add(String s)
    {
        if(s == null || s.length() == 0)
            return;
        ensureCapacityData(size + s.length());
        System.arraycopy(s.toCharArray(), 0, data, size, s.length());
        size += s.length();
    }
    /**
     * 插入字符
     * @param index int 位置
     * @param c char
     */
    public void add(int index,char c)
    {
        if (index >= size || index < 0)
        {
            add(c);
            return;
        }
        ensureCapacityData(size+1);
        System.arraycopy(data, index, data, index + 1,
                         size - index);
        data[index] = c;
        size++;
    }
    /**
     * 插入字符串
     * @param index int
     * @param s String
     */
    public void add(int index,String s)
    {
        if (index >= size || index < 0)
        {
            add(s);
            return;
        }
        ensureCapacityData(size + s.length());
        System.arraycopy(data, index, data, index + s.length(),
                         size - index);
        System.arraycopy(s.toCharArray(), 0, data, index,
                         s.length());
        size += s.length();
    }
    /**
     * 删除位置
     * @param index int
     */
    public void remove(int index)
    {
        remove(index,1);
    }
    /**
     * 删除位置
     * @param index int 位置
     * @param length int 个数
     */
    public void remove(int index,int length)
    {
        if(index < 0 || index >= size || length <= 0)
            return;
        if(index + length > size)
        {
            size = index;
            return;
        }
        int numMoved = size - index - length;
        if (numMoved > 0)
            System.arraycopy(data, index + length, data, index,numMoved);
        size -= length;
    }
    /**
     * 删除全部
     */
    public void removeAll()
    {
        data = new char[10];
        size = 0;
    }
    /**
     * 计算内存增量
     * @param length int
     */
    private void ensureCapacityData(int length) {
        int old = data.length;
        if (length > old) {
            char oldData[] = data;
            int n = (old * 3)/2 + 1;
            if (n < length)
                n = length;
            data = new char[n];
            System.arraycopy(oldData, 0, data, 0, size);
        }
    }
    /**
     * 得到字符串
     * @return String
     */
    public String getString()
    {
        return new String(data,0,size);
    }
    /**
     * 得到自串
     * @param start int
     * @param end int
     * @return String
     */
    public String getString(int start,int end)
    {
        if(start < 0 || end > size)
            return "";
        if(end < start)
        {
            System.out.println("err:DString getString(" + start + "," + end + ")");
            return "";
        }
        return new String(data,start,end - start);
    }
    /**
     * 得到长度
     * @return int
     */
    public int size()
    {
        return size;
    }
    public String toString()
    {
        return getString();
    }
    public static void main(String args[])
    {
        DString w = new DString("123你好测试，");
        w.add(1,"ABC");
        System.out.println(w.getString());
        w.remove(5,2);
        System.out.println(w.getString());
        System.out.println(w.size());
    }
}
