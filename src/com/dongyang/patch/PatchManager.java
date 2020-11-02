package com.dongyang.patch;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.util.StringTool;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import com.dongyang.jdo.TJDODBTool;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 批次管理器</p>
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
public class PatchManager
{
    /**
     * 实例
     */
    private static PatchManager instance;
    /**
     * 时钟
     */
    private PatchTimer timer;
    /**
     * 是否启动
     */
    private boolean isStart;
    /**
     * 休眠时间
     */
    private int sleeptime = 1000;
    /**
     * 过期扫描时间
     */
    private int expiredtime = 10;
    /**
     * 日期数据列表
     */
    private Map data;
    /**
     * 作业
     */
    private Map jobMap;
    /**
     * 加载日期
     */
    private Date loadDate;
    
    private static final java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy/MM/dd");
    
    /**
     * 得到管理器
     * @return PatchManager
     */
    public static PatchManager getManager()
    {
        if(instance == null)
            instance = new PatchManager();
        return instance;
    }
    /**
     * 构造器
     */
    public PatchManager()
    {
        data = new HashMap();
        jobMap = new HashMap();
    }
    /**
     * 创建时钟
     */
    public void createTimer()
    {
        setTimer(new PatchTimer(this));
    }
    /**
     * 设置时钟
     * @param timer PatchTimer
     */
    public void setTimer(PatchTimer timer)
    {
        this.timer = timer;
    }
    /**
     * 得到时钟
     * @return PatchTimer
     */
    public PatchTimer getTimer()
    {
        return timer;
    }
    /**
     * 是否启动
     * @return boolean
     */
    public boolean isStart()
    {
        return isStart;
    }
    /**
     * 设置启动
     * @param isStart boolean
     */
    public void setStart(boolean isStart)
    {
        if(this.isStart == isStart)
            return;
        this.isStart = isStart;
        Date d = new Date();
        if(isStart)
        {
            data = new HashMap();
            jobMap = new HashMap();
            System.out.println("=====================================");
            System.out.println(" Batch Server V1.0");
            System.out.println("  start " + StringTool.getString(d,"yyyy/MM/dd HH:mm:ss"));
            System.out.println("=====================================");
            load(d);
            createTimer();
            getTimer().setLoadDate(d);
            getTimer().start();
            return;
        }
        System.out.println("=====================================");
        System.out.println(" Batch Server V1.0");
        System.out.println(" stop" + StringTool.getString(d,"yyyy/MM/dd HH:mm:ss"));
        System.out.println("=====================================");
        setTimer(null);
    }
    /**
     * 设置休眠时间
     * @param sleeptime int
     */
    public void setSleeptime(int sleeptime)
    {
        this.sleeptime = sleeptime;
    }
    /**
     * 得到休眠时间
     * @return int
     */
    public int getSleeptime()
    {
        return sleeptime;
    }
    /**
     * 设置过期扫描时间
     * @param expiredtime int
     */
    public void setExpiredtime(int expiredtime)
    {
        this.expiredtime = expiredtime;
    }
    /**
     * 得到过期扫描时间
     * @return int
     */
    public int getExpiredtime()
    {
        return expiredtime;
    }
    /**
     * 工作
     * @param date Date
     */
    public synchronized void work(Date date)
    {
        PatchDate patchDate = getPatchDate(date);
        if(patchDate == null)
            return;
        patchDate.work(date);
        if(patchDate.size() == 0)
            remove(date);
    }
    /**
     * 过期扫描
     * @param date Date
     */
    public synchronized void expired(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        String s = year + "/" + month + "/" + day;
        
        Date currentDate=null;
		try {
			currentDate=df.parse(s);
			//System.out.println("--currentDate--"+currentDate);
		} catch (ParseException e) {
			System.out.println(s+"---expired方法 日期格式转化错误---");
			e.printStackTrace();
		}
		
        Iterator iterator = data.keySet().iterator();
        List list = new ArrayList();
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            Date taskDate=null;
            try {
    			taskDate=df.parse(name);
    			//System.out.println("--taskDate--"+taskDate);
    		} catch (ParseException e) {
    			System.out.println(name+"---expired方法 日期格式转化错误---");
    			e.printStackTrace();
    		}
    		//原来过期检查有错误
    		int i = taskDate.compareTo(currentDate);
            if(i < 0)
                list.add(name);
        }
        for(int i = 0;i < list.size();i++)
        {
            PatchDate patchData = getPatchDate((String)list.get(i));
            patchData.expired();
            data.remove(list.get(i));
        }
        PatchDate patchData = getPatchDate(s);
        if(patchData == null)
            return;
        patchData.expired(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
        if(patchData.size() == 0)
            data.remove(s);
    }
    /**
     * 得到批次日期对象
     * @param date String
     * @return PatchDate
     */
    public PatchDate getPatchDate(String date)
    {
        return (PatchDate)data.get(date);
    }
    /**
     * 得到日期参数
     * @param date Date
     * @return String
     */
    private String getDateString(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }
    /**
     * 得到批次日期对象
     * @param date Date
     * @return PatchDate
     */
    public PatchDate getPatchDate(Date date)
    {
        return getPatchDate(getDateString(date));
    }
    /**
     * 增加日期对象
     * @param date PatchDate
     */
    public void add(PatchDate date)
    {
        if(date == null)
            return;
        data.put(date.getYear() + "/" + date.getMonth() + "/" + date.getDay(),date);
    }
    /**
     * 增加日期对象
     * @param job PatchJob
     */
    public void add(PatchJob job)
    {
        if(job == null)
            return;
        PatchDate patchDate = getPatchDate(job.getDate());
        if(patchDate == null)
        {
            patchDate = new PatchDate(job.getDate());
            add(patchDate);
        }
        patchDate.add(job.getDate(),job);
    }
    /**
     * 删除日期对象
     * @param date Date
     */
    public synchronized void remove(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        data.remove(year + "/" + month + "/" + day);
    }
    /**
     * 加入作业
     * @param job PatchJob
     * @return boolean
     */
    public boolean putJob(PatchJob job)
    {
        synchronized(jobMap)
        {
            List list = (List) jobMap.get(job.getID());
            if (list == null)
            {
                list = new ArrayList();
                jobMap.put(job.getID(), list);
            }
            for(int i = 0;i < list.size();i++)
            {
                PatchJob patchJob = (PatchJob)list.get(i);
                if(patchJob.getDate().equals(job.getDate()))
                    return false;
            }
            list.add(job);
            return true;
        }
    }
    /**
     * 删除作业
     * @param id String
     */
    public synchronized void popJob(String id)
    {
        if(!isStart())
            return;
        if(id == null || id.length() == 0)
            return;
        List list = (List) jobMap.get(id);
        if(list == null)
            return;
        List removeList = new ArrayList();
        for(int i = 0;i < list.size();i++)
        {
            PatchJob job = (PatchJob)list.get(i);
            String name = getDateString(job.getDate());
            PatchDate date = getPatchDate(name);
            if(date == null)
                continue;
            date.removeJob(job);
            if(date.size() == 0)
                removeList.add(name);
        }
        for(int i = 0;i < list.size();i++){
            //System.out.println("======data========"+data);
            //System.out.println("=======removeList==========["+i+"]"+removeList.get(i));
            data.remove(removeList.get(i));
        }

        jobMap.remove(id);
    }
    /**
     * 加载数据
     * @param d Date
     */
    public synchronized void load(Date d)
    {
        TParm parm = new TParm(TJDODBTool.getInstance().select("SELECT * FROM SYS_PATCH WHERE STATUS='Y'"));
        int count = parm.getCount();
        for(int i = 0;i < count;i++)
        {
            PatchJob job = new PatchJob(this);
            job.setID(parm.getValue("PATCH_CODE",i));
            job.setName(parm.getValue("PATCH_DESC",i));
            job.setSrc(parm.getValue("PATCH_SRC",i));
            job.setType(parm.getInt("PATCH_TYPE",i));
            job.setPatchDate(parm.getValue("PATCH_DATE",i));
            job.setOthertimeCount(parm.getInt("PATCH_REOMIT_COUNT",i));
            job.setOthertimeString(parm.getValue("PATCH_REOMIT_INTERVAL",i));
            job.setNowOtherTime(parm.getBoolean("PATCH_REOMIT_POINT",i));
            job.setEndDate(parm.getTimestamp("END_DATE",i));
            if(!job.checkDate(d))
                continue;
            if(!putJob(job))
                continue;
            add(job);
        }
        /**
         * 保存加载日期
         */
        loadDate = d;
    }
    /**
     * 加载作业
     * @param id String
     * @return boolean
     */
    public synchronized boolean putJob(String id)
    {
        if(!isStart())
            return false;
        TParm parm = new TParm(TJDODBTool.getInstance().select("SELECT * FROM SYS_PATCH WHERE STATUS='Y' AND PATCH_CODE='" + id + "'"));
        if(parm.getErrCode() < 0 || parm.getCount() == 0)
            return false;
        for(Date d = getcDate();cDate(d,loadDate);d = addDate(d))
        {
            PatchJob job = new PatchJob(this);
            job.setID(parm.getValue("PATCH_CODE", 0));
            job.setName(parm.getValue("PATCH_DESC", 0));
            job.setSrc(parm.getValue("PATCH_SRC", 0));
            job.setType(parm.getInt("PATCH_TYPE", 0));
            job.setPatchDate(parm.getValue("PATCH_DATE", 0));
            job.setOthertimeCount(parm.getInt("PATCH_REOMIT_COUNT", 0));
            job.setOthertimeString(parm.getValue("PATCH_REOMIT_INTERVAL", 0));
            job.setNowOtherTime(parm.getBoolean("PATCH_REOMIT_POINT", 0));
            job.setEndDate(parm.getTimestamp("END_DATE", 0));
            if (!job.checkDate(d))
                continue;
            if(!putJob(job))
                continue;
            add(job);
        }
        return true;
    }
    private Date getcDate()
    {
        String s = StringTool.getString(new Date(),"yyyyMMdd") +
                   StringTool.getString(loadDate,"HHmmss");
        return StringTool.getDate(s,"yyyyMMddHHmmss");

    }
    private boolean cDate(Date d1,Date d2)
    {
        int x = (int) ((d2.getTime() - d1.getTime()) / 1000.0 / 60.0 / 60.0 /
                      24.0);
        return x >= 0;
    }
    private Date addDate(Date d)
    {
        return new Date(d.getTime() + 1 * 24 * 60 * 60 * 1000);
    }
    /**
     * 得到数据列表
     * @return String[]
     */
    public synchronized String[] getList()
    {
        List list = new ArrayList();
        Iterator iterator = data.keySet().iterator();
        while(iterator.hasNext())
        {
            String date = (String)iterator.next();
            PatchDate patchData = getPatchDate(date);
            patchData.getList(list);
        }
        String data[] = (String[])list.toArray(new String[]{});
        StringTool.orderStringArray(data);
        return data;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = data.keySet().iterator();
        while(iterator.hasNext())
        {
            String hour = (String)iterator.next();
            sb.append("date=" + hour + "\n");
            sb.append(getPatchDate(hour));
        }
        return sb.toString();
    }
    public static void main(String args[])
    {
        com.dongyang.util.TDebug.initServer();
        PatchManager m = new PatchManager();
        m.setStart(true);
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        /*System.out.println(m);
        m.popJob("1");
        System.out.println("-----------------");
        System.out.println(m);
        m.putJob("1");
        System.out.println("=================");
        System.out.println(m);*/
    }
}
