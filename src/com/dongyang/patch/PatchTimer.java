package com.dongyang.patch;

import java.util.Date;
import com.dongyang.util.StringTool;
import java.sql.Timestamp;

/**
 *
 * <p>Title: ����ʱ��</p>
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
     * ������
     */
    private PatchManager manager;
    /**
     * �ϴ�ʱ��
     */
    private Date olddate;
    /**
     * ����ʱ��
     */
    private Date expiredDate;
    /**
     * ����ʱ��
     */
    private Date loadDate;
    /**
     * ������
     * @param manager PatchManager
     */
    public PatchTimer(PatchManager manager)
    {
        setManager(manager);
    }
    /**
     * ���ù�����
     * @param manager PatchManager
     */
    public void setManager(PatchManager manager)
    {
        this.manager = manager;
    }
    /**
     * �õ�������
     * @return PatchManager
     */
    public PatchManager getManager()
    {
        return manager;
    }
    /**
     * ���ü���ʱ��
     * @param loadDate Date
     */
    public void setLoadDate(Date loadDate)
    {
        this.loadDate = loadDate;
    }
    /**
     * �õ�����ʱ��
     * @return Date
     */
    public Date getLoadDate()
    {
        return loadDate;
    }
    /**
     * ����
     */
    public void run()
    {
        //����ɨ��
        olddate = new Date();
        expired(olddate);
        //ʱ������
        while(getManager().isStart() && getManager().getTimer() == this)
        {
            //����
            patchSleep();
            Date d = new Date();
            //����ɨ��
            working(d);
            //����ɨ��
            expireding(d);
            //��������
            loadData(d);
        }
    }
    /**
     * ����ɨ��
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
     * ��������
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
     * ����ɨ��
     * @param d Date
     */
    public void working(Date d)
    {
        long x = olddate.getTime();
        int count = (int) (d.getTime() / 1000) - (int) (olddate.getTime() / 1000);
        for(int i = 1;i < count;i++)
            work(new Date(x + i * 1000L));
        //����
        work(d);
        olddate = d;
    }
    /**
     * ����
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
     * ����
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
