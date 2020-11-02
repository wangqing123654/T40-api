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
 * <p>Title: 批次日期对象</p>
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
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 日
     */
    private int day;
    /**
     * 小时
     */
    private Map hourMap;
    /**
     * 构造器
     */
    public PatchDate()
    {
        hourMap = new HashMap();
    }
    /**
     * 构造器
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
     * 设置年
     * @param year int
     */
    public void setYear(int year)
    {
        this.year = year;
    }
    /**
     * 得到年
     * @return int
     */
    public int getYear()
    {
        return year;
    }
    /**
     * 设置月
     * @param month int
     */
    public void setMonth(int month)
    {
        this.month = month;
    }
    /**
     * 得到月
     * @return int
     */
    public int getMonth()
    {
        return month;
    }
    /**
     * 得到日
     * @param day int
     */
    public void setDay(int day)
    {
        this.day = day;
    }
    /**
     * 设置日
     * @return int
     */
    public int getDay()
    {
        return day;
    }
    /**
     * 增加小时对象
     * @param hour PatchHour
     */
    public void add(PatchHour hour)
    {
        if(hour == null)
            return;
        hourMap.put(hour.getHour(),hour);
    }
    /**
     * 得到小时对象
     * @param hour int
     * @return PatchHour
     */
    public PatchHour get(int hour)
    {
        return (PatchHour)hourMap.get(hour);
    }
    /**
     * 删除小时对象
     * @param hour int
     */
    public synchronized void remove(int hour)
    {
        hourMap.remove(hour);
    }
    /**
     * 尺寸
     * @return int
     */
    public int size()
    {
        return hourMap.size();
    }
    /**
     * 增加
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
     * 删除作业
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
     * 工作
     * @param date Date
     */
    public void work(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        work(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
    }
    /**
     * 工作
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
     * 过期
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
     * 过期检查
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
     * 得到数据列表
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
