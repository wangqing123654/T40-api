package com.dongyang.util;

import java.util.HashMap;

public class TMap
{
    private TList data;
    public TMap()
    {
        data = new TList();
    }
    public void put(Object key, Object value)
    {
        if(key == null)
            return;
        /*for(int i = 0;i < data.size;i++)
        {
            /*Object[] d = (Object[])data.get(i);
            if(d[0].equals(key))
            {
                d[1] = value;
                return;
            }*/
        //}

        data.add(new Object[]{key,value});
    }
    public int size()
    {
        return data.size;
    }
    public static void main(String args[])
    {
        DebugUsingTime.start();
        HashMap map1 = new HashMap();
        for(int i = 0;i < 10000;i++)
        {
            map1.put(i,i);
        }
        DebugUsingTime.add("L create");
        System.out.println("L size=" + map1.size());

        DebugUsingTime.start();
        for(int i = 0;i < 10000;i++)
        {
            map1.get(i);
        }
        DebugUsingTime.add("end");

        /*DebugUsingTime.start();
        TMap map = new TMap();
        for(int i = 0;i < 10000;i++)
        {
            map.put(i,i);
        }
        DebugUsingTime.add("T create");
        System.out.println("T size=" + map.size());*/
    }
}
