package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: �ֶ�</p>
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
     * ����
     */
    private char[] data;
    /**
     * �ߴ�
     */
    private transient int size;
    /**
     * ������
     */
    public DString()
    {
        data = new char[10];
    }
    /**
     * ������
     * @param s String
     */
    public DString(String s)
    {
        this();
        add(s);
    }
    /**
     * �����ַ�
     * @param c char
     */
    public void add(char c)
    {
        ensureCapacityData(size + 1);
        data[size++] = c;
    }
    /**
     * �����ַ���
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
     * �����ַ�
     * @param index int λ��
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
     * �����ַ���
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
     * ɾ��λ��
     * @param index int
     */
    public void remove(int index)
    {
        remove(index,1);
    }
    /**
     * ɾ��λ��
     * @param index int λ��
     * @param length int ����
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
     * ɾ��ȫ��
     */
    public void removeAll()
    {
        data = new char[10];
        size = 0;
    }
    /**
     * �����ڴ�����
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
     * �õ��ַ���
     * @return String
     */
    public String getString()
    {
        return new String(data,0,size);
    }
    /**
     * �õ��Դ�
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
     * �õ�����
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
        DString w = new DString("123��ò��ԣ�");
        w.add(1,"ABC");
        System.out.println(w.getString());
        w.remove(5,2);
        System.out.println(w.getString());
        System.out.println(w.size());
    }
}
