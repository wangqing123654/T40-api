package com.dongyang.manager;

import com.dongyang.data.TParm;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: 批次服务器控制类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.7.20
 * @version 1.0
 */
public class TIOM_PatchServer
{
    /**
     * 服务器启动
     */
    public static void start()
    {
        TParm parm = new TParm();
        parm.setData("START",true);
        TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","start",parm);
    }
    /**
     * 服务器停止
     */
    public static void stop()
    {
        TParm parm = new TParm();
        parm.setData("START",false);
        TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","start",parm);
    }
    /**
     * 服务器是否启动
     * @return boolean
     */
    public static boolean isStart()
    {
        TParm parm = new TParm();
        TParm result = TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","isStart",parm);
        return result.getBoolean("IS_START");
    }
    /**
     * 设置休眠时间
     * @param sleeptime int
     */
    public static void setSleeptime(int sleeptime)
    {
        TParm parm = new TParm();
        parm.setData("SLEEP_TIME",sleeptime);
        TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","setSleeptime",parm);
    }
    /**
     * 得到休眠时间
     * @return int
     */
    public static int getSleeptime()
    {
        TParm parm = new TParm();
        TParm result = TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","getSleeptime",parm);
        return result.getInt("SLEEP_TIME");
    }
    /**
     * 设置过期扫描时间
     * @param expiredtime int
     */
    public static void setExpiredtime(int expiredtime)
    {
        TParm parm = new TParm();
        parm.setData("EXPIRED_TIME",expiredtime);
        TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","setExpiredtime",parm);
    }
    /**
     * 得到过期扫描时间
     * @return int
     */
    public static int getExpiredtime()
    {
        TParm parm = new TParm();
        TParm result = TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","getExpiredtime",parm);
        return result.getInt("EXPIRED_TIME");
    }
    /**
     * 删除作业
     * @param id String
     */
    public static void popJob(String id)
    {
        TParm parm = new TParm();
        parm.setData("POP_JOB",id);
        TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","popJob",parm);
    }
    /**
     * 加载作业
     * @param id String
     */
    public static void putJob(String id)
    {
        TParm parm = new TParm();
        parm.setData("PUT_JOB",id);
        TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","putJob",parm);
    }
    /**
     * 得到加载数据
     * @return String[]
     */
    public static String[] getList()
    {
        TParm parm = new TParm();
        TParm result = TIOM_AppServer.executeAction("com.dongyang.action.TPatchAction","getList",parm);
        return (String[])result.getData("DATA");
    }
    public static void main(String args[])
    {
        com.dongyang.util.TDebug.initClient();
        //TIOM_PatchServer.stop();
        //TIOM_PatchServer.start();
        //popJob("1");
        putJob("1");
        String s[] = getList();
        System.out.println(StringTool.getString(s));

    }
}
