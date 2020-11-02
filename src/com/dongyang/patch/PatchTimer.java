package com.dongyang.patch;

import java.util.Date;
import com.dongyang.util.StringTool;
import java.sql.Timestamp;

/**
 *
 * <p>Title: 批次时钟</p>
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
public class PatchTimer extends Thread
{
    /**
     * 管理器
     */
    private PatchManager manager;
    /**
     * 上次时间
     */
    private Date olddate;
    /**
     * 过期时间
     */
    private Date expiredDate;
    /**
     * 加载时间
     */
    private Date loadDate;
    /**
     * 构造器
     * @param manager PatchManager
     */
    public PatchTimer(PatchManager manager)
    {
        setManager(manager);
    }
    /**
     * 设置管理器
     * @param manager PatchManager
     */
    public void setManager(PatchManager manager)
    {
        this.manager = manager;
    }
    /**
     * 得到管理器
     * @return PatchManager
     */
    public PatchManager getManager()
    {
        return manager;
    }
    /**
     * 设置加载时间
     * @param loadDate Date
     */
    public void setLoadDate(Date loadDate)
    {
        this.loadDate = loadDate;
    }
    /**
     * 得到加载时间
     * @return Date
     */
    public Date getLoadDate()
    {
        return loadDate;
    }
    /**
     * 运行
     */
    public void run()
    {
        //过期扫描
        olddate = new Date();
        expired(olddate);
        //时钟主体
        while(getManager().isStart() && getManager().getTimer() == this)
        {
            //休眠
            patchSleep();
            Date d = new Date();
            //工作扫描
            working(d);
            //过期扫描
            expireding(d);
            //加载数据
            loadData(d);
        }
    }
    /**
     * 过期扫描
     * @param d Date
     */
    public void expireding(Date d)
    {
        if(getManager().getExpiredtime() == 0)
            return;
        if(expiredDate == null)
        {
            expiredDate = d;
            return;
        }
        long x = (d.getTime() - expiredDate.getTime()) / 1000;
        if(x >= getManager().getExpiredtime())
        {
            expired(d);
            expiredDate = d;
        }
    }
    /**
     * 加载数据
     * @param d Date
     */
    public void loadData(Date d)
    {

        int x = (int) ((getLoadDate().getTime() - d.getTime()) / 1000.0 / 60.0 / 60.0 /
                      24.0);
        if(x == 0)
        {
            Date d1 = new Date(getLoadDate().getTime() + 1 * 24 * 60 * 60 * 1000);
            getManager().load(d1);
            setLoadDate(d1);
        }
    }
    public void expired(final Date date)
    {
        new Thread(){
            public void run()
            {
                getManager().expired(date);
            }
        }.start();
    }
    /**
     * 工作扫描
     * @param d Date
     */
    public void working(Date d)
    {
        long x = olddate.getTime();
        int count = (int) (d.getTime() / 1000) - (int) (olddate.getTime() / 1000);
        for(int i = 1;i < count;i++)
            work(new Date(x + i * 1000L));
        //工作
        work(d);
        olddate = d;
    }
    /**
     * 工作
     * @param date Date
     */
    public void work(final Date date)
    {
        new Thread(){
            public void run()
            {
                getManager().work(date);
            }
        }.start();
    }
    /**
     * 休眠
     */
    public void patchSleep()
    {
        try{
            sleep(getManager().getSleeptime());
        }catch(Exception e)
        {
        }
    }
}
