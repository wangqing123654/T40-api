package com.dongyang.patch;

import java.util.List;
import java.util.ArrayList;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: ���������</p>
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
public class PatchSecond
{
    /**
     * ��
     */
    private int second;
    /**
     * ����
     */
    private List jobs;
    /**
     * ������
     */
    public PatchSecond()
    {
        jobs = new ArrayList();
    }
    /**
     * ������
     * @param second int
     * @param job PatchJob
     */
    public PatchSecond(int second,PatchJob job)
    {
        this();
        setSecond(second);
        addJob(job);
    }
    /**
     * ������
     * @param second int
     */
    public void setSecond(int second)
    {
        this.second = second;
    }
    /**
     * �õ���
     * @return int
     */
    public int getSecond()
    {
        return second;
    }

    /**
     * ���ù���
     * @param job PatchJob
     */
    public synchronized void addJob(PatchJob job)
    {
        jobs.add(job);
    }

    /**
     * �õ�����
     * @param i int
     * @return PatchJob
     */
    public synchronized PatchJob getJob(int i)
    {
        if(i < 0 || i >= jobSize())
            return null;
        return (PatchJob)jobs.get(i);
    }
    /**
     * ɾ������
     * @param job PatchJob
     */
    public synchronized void removeJob(PatchJob job)
    {
        jobs.remove(job);
    }
    /**
     * ��������
     * @return int
     */
    public int jobSize()
    {
        return jobs.size();
    }
    /**
     * ����
     */
    public synchronized void work()
    {
        if (jobSize() == 0)
            return;
        for(int i = 0;i < jobSize();i++)
        {
            final PatchJob job = getJob(i);
            if(job == null || job.isStart())
                continue;
            new Thread(){
                public void run()
                {
                    job.work();
                }
            }.start();
        }
    }
    /**
     * ����
     */
    public synchronized void expired()
    {
        for(int i = 0;i < jobSize();i++)
        {
            final PatchJob job = getJob(i);
            if(job == null || job.isStart())
                continue;
            new Thread(){
                public void run()
                {
                    job.expired();
                }
            }.start();
        }
    }
    /**
     * �õ������б�
     * @param list List
     */
    public synchronized void getList(List list)
    {
        for(int i = 0;i < jobSize();i++)
        {
            PatchJob patchJob = getJob(i);
            String s = StringTool.getString(patchJob.getDate(),"yyyy/MM/dd HH:mm:ss");
            s += "|" + patchJob.getID();
            s += "|" + patchJob.getName();
            list.add(s);
        }
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < jobSize();i++)
            sb.append(i + ":" + getJob(i) + "\n");
        return sb.toString();
    }
}
