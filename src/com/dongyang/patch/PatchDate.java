package com.dongyang.patch;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: �������ڶ���</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.7.14
 * @version 1.0
 */
public class PatchDate
{
    /**
     * ��
     */
    private int year;
    /**
     * ��
     */
    private int month;
    /**
     * ��
     */
    private int day;
    /**
     * Сʱ
     */
    private Map hourMap;
    /**
     * ������
     */
    public PatchDate()
    {
        hourMap = new HashMap();
    }
    /**
     * ������
     * @param date Date
     */
    public PatchDate(Date date)
    {
        this();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setYear(c.get(Calendar.YEAR));
        setMonth(c.get(Calendar.MONTH) + 1);
        setDay(c.get(Calendar.DATE));
    }
    /**
     * ������
     * @param year int
     */
    public void setYear(int year)
    {
        this.year = year;
    }
    /**
     * �õ���
     * @return int
     */
    public int getYear()
    {
        return year;
    }
    /**
     * ������
     * @param month int
     */
    public void setMonth(int month)
    {
        this.month = month;
    }
    /**
     * �õ���
     * @return int
     */
    public int getMonth()
    {
        return month;
    }
    /**
     * �õ���
     * @param day int
     */
    public void setDay(int day)
    {
        this.day = day;
    }
    /**
     * ������
     * @return int
     */
    public int getDay()
    {
        return day;
    }
    /**
     * ����Сʱ����
     * @param hour PatchHour
     */
    public void add(PatchHour hour)
    {
        if(hour == null)
            return;
        hourMap.put(hour.getHour(),hour);
    }
    /**
     * �õ�Сʱ����
     * @param hour int
     * @return PatchHour
     */
    public PatchHour get(int hour)
    {
        return (PatchHour)hourMap.get(hour);
    }
    /**
     * ɾ��Сʱ����
     * @param hour int
     */
    public synchronized void remove(int hour)
    {
        hourMap.remove(hour);
    }
    /**
     * �ߴ�
     * @return int
     */
    public int size()
    {
        return hourMap.size();
    }
    /**
     * ����
     * @param date Date
     * @param job PatchJob
     */
    public synchronized void add(Date date,PatchJob job)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        PatchHour patchHour = get(hour);
        if(patchHour == null)
        {
            patchHour = new PatchHour(hour);
            add(patchHour);
        }
        patchHour.add(minute,second,job);
    }
    /**
     * ɾ����ҵ
     * @param job PatchJob
     */
    public void removeJob(PatchJob job)
    {
        Iterator iterator = hourMap.keySet().iterator();
        List list = new ArrayList();
        while(iterator.hasNext())
        {
            int name = (Integer) iterator.next();
            PatchHour patchHour = (PatchHour)hourMap.get(name);
            patchHour.removeJob(job);
            if(patchHour.size() == 0)
                list.add(name);
        }
        for(int i = 0;i < list.size();i++)
            hourMap.remove(list.get(i));
    }
    /**
     * ����
     * @param date Date
     */
    public void work(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        work(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
    }
    /**
     * ����
     * @param hour int
     * @param minute int
     * @param second int
     */
    public synchronized void work(int hour,int minute,int second)
    {
        PatchHour patchHour = get(hour);
        if(patchHour == null)
            return;
        patchHour.work(minute,second);
        if(patchHour.size() == 0)
            remove(hour);
    }
    /**
     * ����
     */
    public synchronized void expired()
    {
        Iterator iterator = hourMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int name = (Integer) iterator.next();
            PatchHour patchHour = (PatchHour)hourMap.get(name);
            patchHour.expired();
        }
        hourMap.clear();
    }
    /**
     * ���ڼ��
     * @param hour int
     * @param minute int
     * @param secound int
     */
    public synchronized void expired(int hour,int minute,int secound)
    {
        Iterator iterator = hourMap.keySet().iterator();
        List list = new ArrayList();
        while(iterator.hasNext())
        {
            int name = (Integer)iterator.next();
            if(name < hour)
                list.add(name);
        }
        for(int i = 0;i < list.size();i++)
        {
            PatchHour patchHour = get((Integer)list.get(i));
            patchHour.expired();
            hourMap.remove(list.get(i));
        }
        PatchHour patchHour = get(hour);
        if(patchHour == null)
            return;
        patchHour.expired(minute,secound);
        if(patchHour.size() == 0)
            remove(hour);
    }
    /**
     * �õ������б�
     * @param list List
     */
    public synchronized void getList(List list)
    {
        Iterator iterator = hourMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int hour = (Integer)iterator.next();
            PatchHour patchHour = get(hour);
            patchHour.getList(list);
        }
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = hourMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int hour = (Integer)iterator.next();
            sb.append("hour=" + hour + "\n");
            sb.append(get(hour));
        }
        return sb.toString();
    }
}
