package com.dongyang.patch;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: ����Сʱ����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: Javahis</p>
 *
 * @author lzk 2009.7.14
 * @version 1.0
 */
public class PatchHour
{
    /**
     * Сʱ
     */
    private int hour;
    /**
     * ����
     */
    private Map minuteMap;
    /**
     * ������
     */
    public PatchHour()
    {
        minuteMap = new HashMap();
    }

    /**
     * ������
     * @param hour int
     */
    public PatchHour(int hour)
    {
        this();
        setHour(hour);
    }
    /**
     * ����Сʱ
     * @param hour int
     */
    public void setHour(int hour)
    {
        this.hour = hour;
    }
    /**
     * �õ�Сʱ
     * @return int
     */
    public int getHour()
    {
        return hour;
    }
    /**
     * ���ӷ��Ӷ���
     * @param minute PatchMinute
     */
    public synchronized void add(PatchMinute minute)
    {
        if(minute == null)
            return;
        minuteMap.put(minute.getMinute(),minute);
    }
    /**
     * ����
     * @param minute int
     * @param second int
     * @param job PatchJob
     */
    public void add(int minute,int second,PatchJob job)
    {
        PatchMinute patchMinute = get(minute);
        if(patchMinute == null)
        {
            patchMinute = new PatchMinute(minute);
            add(patchMinute);
        }
        patchMinute.add(second,job);
    }
    /**
     * �õ����Ӷ���
     * @param minute int
     * @return PatchMinute
     */
    public synchronized PatchMinute get(int minute)
    {
        return (PatchMinute)minuteMap.get(minute);
    }
    /**
     * ɾ�����Ӷ���
     * @param minute int
     */
    public synchronized void remove(int minute)
    {
        minuteMap.remove(minute);
    }
    /**
     * ɾ����ҵ
     * @param job PatchJob
     */
    public void removeJob(PatchJob job)
    {
        Iterator iterator = minuteMap.keySet().iterator();
        List list = new ArrayList();
        while(iterator.hasNext())
        {
            int name = (Integer) iterator.next();
            PatchMinute patchMinute = (PatchMinute)minuteMap.get(name);
            patchMinute.removeJob(job);
            if(patchMinute.size() == 0)
                list.add(name);
        }
        for(int i = 0;i < list.size();i++)
            minuteMap.remove(list.get(i));
    }
    /**
     * ����
     * @param minute int
     * @param secound int
     */
    public synchronized void work(int minute,int secound)
    {
        PatchMinute patchMinute = get(minute);
        if(patchMinute == null)
            return;
        patchMinute.work(secound);
        if(patchMinute.size() == 0)
            remove(minute);
    }
    /**
     * ����
     */
    public synchronized void expired()
    {
        Iterator iterator = minuteMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int name = (Integer) iterator.next();
            PatchMinute patchMinute = (PatchMinute)minuteMap.get(name);
            patchMinute.expired();
        }
        minuteMap.clear();
    }
    /**
     * ���ڼ��
     * @param minute int
     * @param secound int
     */
    public synchronized void expired(int minute,int secound)
    {
        Iterator iterator = minuteMap.keySet().iterator();
        List list = new ArrayList();
        while(iterator.hasNext())
        {
            int name = (Integer)iterator.next();
            if(name < minute)
                list.add(name);
        }
        for(int i = 0;i < list.size();i++)
        {
            PatchMinute patchMinute = get((Integer)list.get(i));
            patchMinute.expired();
            minuteMap.remove(list.get(i));
        }
        PatchMinute patchMinute = get(minute);
        if(patchMinute == null)
            return;
        patchMinute.expired(secound);
        if(patchMinute.size() == 0)
            remove(minute);
    }
    /**
     * �ߴ�
     * @return int
     */
    public int size()
    {
        return minuteMap.size();
    }
    /**
     * �õ������б�
     * @param list List
     */
    public synchronized void getList(List list)
    {
        Iterator iterator = minuteMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int minute = (Integer)iterator.next();
            PatchMinute patchMinute = get(minute);
            patchMinute.getList(list);
        }
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = minuteMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int minute = (Integer)iterator.next();
            sb.append("monute=" + minute + "\n");
            sb.append(get(minute));
        }
        return sb.toString();
    }
}
