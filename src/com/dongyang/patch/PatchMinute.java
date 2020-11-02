package com.dongyang.patch;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: 批次分钟对象</p>
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
public class PatchMinute
{
    /**
     * 分钟
     */
    private int minute;
    /**
     * 秒
     */
    private Map secoundMap;
    /**
     * 构造器
     */
    public PatchMinute()
    {
        secoundMap = new HashMap();
    }
    /**
     * 构造器
     * @param minute int
     */
    public PatchMinute(int minute)
    {
        this();
        setMinute(minute);
    }
    /**
     * 设置分钟
     * @param minute int
     */
    public void setMinute(int minute)
    {
        this.minute = minute;
    }
    /**
     * 得到分钟
     * @return int
     */
    public int getMinute()
    {
        return minute;
    }
    /**
     * 增加秒对象
     * @param second PatchSecond
     */
    public synchronized void add(PatchSecond second)
    {
        if(second == null)
            return;
        secoundMap.put(second.getSecond(),second);
    }
    /**
     * 增加
     * @param second int
     * @param job PatchJob
     */
    public void add(int second,PatchJob job)
    {
        PatchSecond patchSecond = get(second);
        if(patchSecond == null)
        {
            patchSecond = new PatchSecond(second, job);
            add(patchSecond);
            return;
        }
        patchSecond.addJob(job);
    }
    /**
     * 得到秒对象
     * @param second int
     * @return PatchSecond
     */
    public synchronized PatchSecond get(int second)
    {
        return (PatchSecond)secoundMap.get(second);
    }
    /**
     * 删除
     * @param secound int
     */
    public synchronized void remove(int secound)
    {
        secoundMap.remove(secound);
    }
    /**
     * 尺寸
     * @return int
     */
    public int size()
    {
        return secoundMap.size();
    }
    /**
     * 删除作业
     * @param job PatchJob
     */
    public synchronized void removeJob(PatchJob job)
    {
        Iterator iterator = secoundMap.keySet().iterator();
        List list = new ArrayList();
        while(iterator.hasNext())
        {
            int name = (Integer) iterator.next();
            PatchSecond patchSecond = (PatchSecond)secoundMap.get(name);
            patchSecond.removeJob(job);
            if(patchSecond.jobSize() == 0)
                list.add(name);
        }
        for(int i = 0;i < list.size();i++)
            secoundMap.remove(list.get(i));
    }
    /**
     * 工作
     * @param secound int
     */
    public synchronized void work(int secound)
    {
        PatchSecond patchSecond = get(secound);
        if(patchSecond == null)
            return;
        patchSecond.work();
        remove(secound);
    }
    /**
     * 过期
     */
    public synchronized void expired()
    {
        Iterator iterator = secoundMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int name = (Integer) iterator.next();
            PatchSecond patchSecond = (PatchSecond)secoundMap.get(name);
            patchSecond.expired();
        }
        secoundMap.clear();
    }
    /**
     * 过期检查
     * @param secound int
     */
    public synchronized void expired(int secound)
    {
        Iterator iterator = secoundMap.keySet().iterator();
        List list = new ArrayList();
        while(iterator.hasNext())
        {
            int name = (Integer)iterator.next();
            if(name < secound)
                list.add(name);
        }
        for(int i = 0;i < list.size();i++)
        {
            PatchSecond patchSecond = get((Integer)list.get(i));
            patchSecond.expired();
            secoundMap.remove(list.get(i));
        }
        PatchSecond patchSecond = get(secound);
        if(patchSecond == null)
            return;
        patchSecond.expired();
        remove(secound);
    }
    /**
     * 得到数据列表
     * @param list List
     */
    public synchronized void getList(List list)
    {
        Iterator iterator = secoundMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int second = (Integer)iterator.next();
            PatchSecond patchSecond = get(second);
            patchSecond.getList(list);
        }
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = secoundMap.keySet().iterator();
        while(iterator.hasNext())
        {
            int second = (Integer)iterator.next();
            sb.append("second=" + second + "\n");
            sb.append(get(second));
        }
        return sb.toString();
    }
}
