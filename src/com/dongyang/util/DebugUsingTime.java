package com.dongyang.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * <p>Title: ����ʹ��ʱ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DebugUsingTime
{
    /**
     * �ϵ��б�
     */
    private static List list = new ArrayList();
    /**
     * ����������
     */
    public static void start()
    {
        list = new ArrayList();
        list.add(new Data("start"));
    }
    /**
     * ���Ӷϵ�
     * @param s String
     */
    public static void add(String s)
    {
        add(s,true);
    }
    public static void add(String s,boolean b)
    {
        list.add(new Data(s));
        if(b && list.size() > 1)
        {
            Data d1 = (Data)list.get(list.size() - 2);
            Data d2 = (Data)list.get(list.size() - 1);
            System.out.println("DebugUsingTime->" + d2.name + ":" + (d2.date.getTime() - d1.date.getTime()));
        }
    }
    /**
     * ��ӡ
     */
    public static void print()
    {
        if(list.size() == 0)
        {
            System.out.println("DebugUsingTime û�м�¼!");
            return;
        }
        Date d = ((Data)list.get(0)).date;
        for(int i = 1;i < list.size();i++)
        {
            Data data = (Data)list.get(i);
            System.out.println("DebugUsingTime->" + data.name + ":" + (data.date.getTime() - d.getTime()));
            d = data.date;
        }
    }
    static class Data
    {
        String name;
        Date date = new Date();
        public Data(String name)
        {
            this.name = name;
        }
    }
}
