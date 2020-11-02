package com.dongyang.patch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import com.dongyang.data.TParm;
import com.dongyang.util.TypeTool;
import com.dongyang.util.StringTool;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.dongyang.jdo.TJDODBTool;
import com.dongyang.Service.Server;

/**
 *
 * <p>Title: 工作类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.7.15
 * @version 1.0
 */
public class PatchJob
{
    /**
     * 管理者
     */
    private PatchManager manager;
    /**
     * 编号
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 执行时间
     */
    private Date date;
    /**
     * 启动
     */
    private boolean isStart;
    /**
     * 重送时间间隔
     */
    private long othertime = 4000;
    /**
     * 重送时间间隔字串
     */
    private String othertimeString = "";
    /**
     * 是否重当前时间延时发送
     */
    private boolean nowOtherTime = true;
    /**
     * 可重送次数
     */
    private int othertimeCount = 2;
    /**
     * 重送次数
     */
    private int othertimeIndex;
    /**
     * 执行程序
     */
    private String src;
    /**
     * 类型
     */
    private int type;
    /**
     * 参数
     */
    private TParm parm;
    /**
     * 上次结束时间
     */
    private Date endDate;
    /**
     * 开始时间
     */
    private String patchDate;
    /**
     * 构造器
     * @param manager PatchManager
     */
    public PatchJob(PatchManager manager)
    {
        setManager(manager);
    }
    /**
     * 构造器
     * @param manager PatchManager
     * @param name String
     * @param date Date
     */
    public PatchJob(PatchManager manager,String name,Date date)
    {
        setManager(manager);
        setName(name);
        setDate(date);
    }
    /**
     * 设置编号
     * @param id String
     */
    public void setID(String id)
    {
        this.id = id;
    }
    /**
     * 得到编号
     * @return String
     */
    public String getID()
    {
        return id;
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 设置执行时间
     * @param date Date
     */
    public void setDate(Date date)
    {
        this.date = date;
    }
    /**
     * 得到执行时间
     * @return Date
     */
    public Date getDate()
    {
        return date;
    }
    /**
     * 设置管理类
     * @param manager PatchManager
     */
    public void setManager(PatchManager manager)
    {
        this.manager = manager;
    }
    /**
     * 得到管理类
     * @return PatchManager
     */
    public PatchManager getManager()
    {
        return manager;
    }
    /**
     * 设置重送时间间隔
     * @param s String
     */
    public void setOthertimeString(String s)
    {
        if(s == null || s.length() == 0)
            return;
        othertimeString = s;
        long othertime = 0;
        switch(s.length())
        {
        case 2:
            othertime = TypeTool.getInt(s) * 1000;
            break;
        case 5:
            othertime = TypeTool.getInt(s.substring(0,2)) * 1000 * 60 +
                        TypeTool.getInt(s.substring(3)) * 1000;
            break;
        case 8:
            othertime = TypeTool.getInt(s.substring(0,2)) * 1000 * 60 * 60 +
                        TypeTool.getInt(s.substring(3,5)) * 1000 * 60 +
                        TypeTool.getInt(s.substring(6)) * 1000;
            break;
        }
        setOthertime(othertime);
    }
    /**
     * 得到重送时间间隔
     * @return String
     */
    public String getOthertimeString()
    {
        return othertimeString;
    }
    /**
     * 设置重送时间间隔
     * @param othertime long
     */
    public void setOthertime(long othertime)
    {
        this.othertime = othertime;
    }
    /**
     * 得到重送时间间隔
     * @return long
     */
    public long getOthertime()
    {
        return othertime;
    }
    /**
     * 设置可重送次数
     * @param othertimeCount int
     */
    public void setOthertimeCount(int othertimeCount)
    {
        this.othertimeCount = othertimeCount;
    }
    /**
     * 得到可重送次数
     * @return int
     */
    public int getOthertimeCount()
    {
        return othertimeCount;
    }
    /**
     * 设置重送次数
     * @param othertimeIndex int
     */
    public void setOthertimeIndex(int othertimeIndex)
    {
        this.othertimeIndex = othertimeIndex;
    }
    /**
     * 得到重送次数
     * @return int
     */
    public int getOthertimeIndex()
    {
        return othertimeIndex;
    }
    /**
     * 设置是否重当前时间延时发送
     * @param nowOtherTime boolean
     */
    public void setNowOtherTime(boolean nowOtherTime)
    {
        this.nowOtherTime = nowOtherTime;
    }
    /**
     * 是否是否重当前时间延时发送
     * @return boolean
     */
    public boolean isNowOtherTime()
    {
        return nowOtherTime;
    }
    /**
     * 设置执行程序
     * @param src String
     */
    public void setSrc(String src)
    {
        this.src = src;
    }
    /**
     * 得到执行程序
     * @return String
     */
    public String getSrc()
    {
        return src;
    }
    /**
     * 设置类型
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * 设置参数
     * @param parm TParm
     */
    public void setParm(TParm parm)
    {
        this.parm = parm;
    }
    /**
     * 得到参数
     * @return TParm
     */
    public TParm getParm()
    {
        return parm;
    }
    /**
     * 设置上次结束时间
     * @param date Date
     */
    public void setEndDate(Date date)
    {
        this.endDate = date;
    }
    /**
     * 得到上次结束时间
     * @return Date
     */
    public Date getEndDate()
    {
        return endDate;
    }
    /**
     * 设置开始时间
     * @param patchDate String
     */
    public void setPatchDate(String patchDate)
    {
        this.patchDate = patchDate;
    }
    /**
     * 得到开始时间
     * @return String
     */
    public String getPatchDate()
    {
        return patchDate;
    }
    /**
     * 测试日期
     * @param d Date
     * @return boolean
     */
    public boolean checkDate(Date d)
    {
        switch(getType())
        {
        case 1://一次性
            setDate(StringTool.getDate(getPatchDate(),"yyyyMMddHHmmss"));
            break;
        case 2://每天执行
            if(getPatchDate() == null || getPatchDate().length() != 6)
                return false;
            if(!checkuser(d))
                return false;
            long x = ((long)(d.getTime()/ 1000/60/60/24)) * 1000*60*60*24;
            x += (TypeTool.getInt(getPatchDate().substring(0,2)) - 8) * 60 * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(2,4)) * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(4,6)) * 1000;
            setDate(new Date(x));
            break;
        case 3://每周执行
            if(getPatchDate() == null || getPatchDate().length() != 7)
                return false;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            int nowweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int week = TypeTool.getInt(getPatchDate().substring(0,1));
            if(week != nowweek)
                return false;
            if(!checkuser(d))
                return false;
            x = ((long)(d.getTime()/ 1000/60/60/24)) * 1000*60*60*24;
            x += (TypeTool.getInt(getPatchDate().substring(1,3)) - 8) * 60 * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(3,5)) * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(5,7)) * 1000;
            setDate(new Date(x));
            break;
        case 4://每月执行
            if(getPatchDate() == null || getPatchDate().length() != 8)
                return false;
            calendar = Calendar.getInstance();
            calendar.setTime(d);
            int nowMonth = calendar.get(Calendar.MONTH) + 1;
            int nowyear = calendar.get(Calendar.YEAR);
            int nowday = calendar.get(Calendar.DATE);
            int day = TypeTool.getInt(getPatchDate().substring(0,2));
            if(day == 99)
            {
                if(nowMonth == 2)
                    if(new GregorianCalendar().isLeapYear(nowyear))
                        day = 29;
                    else
                        day = 28;
                else
                {
                    int m[] = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                    day = m[nowMonth];
                }
            }
            if(day != nowday)
                return false;
            if(!checkuser(d))
                return false;
            x = ((long)(d.getTime()/ 1000/60/60/24)) * 1000*60*60*24;
            x += (TypeTool.getInt(getPatchDate().substring(2,4)) - 8) * 60 * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(4,6)) * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(6,8)) * 1000;
            setDate(new Date(x));
            break;
        case 5://每年执行
            if(getPatchDate() == null || getPatchDate().length() != 10)
                return false;
            calendar = Calendar.getInstance();
            calendar.setTime(d);
            nowMonth = calendar.get(Calendar.MONTH) + 1;
            nowyear = calendar.get(Calendar.YEAR);
            nowday = calendar.get(Calendar.DATE);
            int month = TypeTool.getInt(getPatchDate().substring(0,2));
            day = TypeTool.getInt(getPatchDate().substring(2,4));
            if(month != nowMonth)
                return false;
            if(day == 99)
            {
                if(nowMonth == 2)
                    if(new GregorianCalendar().isLeapYear(nowyear))
                        day = 29;
                    else
                        day = 28;
                else
                {
                    int m[] = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                    day = m[nowMonth];
                }
            }
            if(day != nowday)
                return false;
            if(!checkuser(d))
                return false;
            x = ((long)(d.getTime()/ 1000/60/60/24)) * 1000*60*60*24;
            x += (TypeTool.getInt(getPatchDate().substring(4,6)) - 8) * 60 * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(6,8)) * 60 * 1000;
            x += TypeTool.getInt(getPatchDate().substring(8,10)) * 1000;
            setDate(new Date(x));
            break;
        }
        return true;
    }
    /**
     * 测试时间过期
     * @param d Date
     * @return boolean
     */
    private boolean checkuser(Date d)
    {
        if(getEndDate() != null)
        {
            int x = (int) ((d.getTime() - getEndDate().getTime()) / 1000.0 /
                   60.0 / 60.0 /
                   24.0);
            //已经执行过了
            if(x <= 0)
                return false;
        }
        return true;
    }
    /**
     * 启动
     * @return boolean
     */
    public synchronized boolean start()
    {
        if(isStart)
            return false;
        isStart = true;
        return true;
    }
    /**
     * 是否启动
     * @return boolean
     */
    public synchronized boolean isStart()
    {
        return isStart;
    }
    /**
     * 工作
     */
    public void work()
    {
        if(!start())
            return;
        Date start = new Date();
        //启动记录
        startData(start);
        Date end = new Date();
        boolean status = true;
        String message = "";
        Object actionObject = Server.getConfigParm().loadObject(getSrc());
        //System.out.println("==========actionObject==========="+actionObject);
        if(actionObject == null)
        {
            status = false;
            message = getSrc() + "不存在";
            endData(start,end,status,message);
            return;
        }
        if(!(actionObject instanceof Patch))
        {
            status = false;
            message = getSrc() + "需要继承 com.dongyang.patch.Patch";
            endData(start,end,status,message);
        }
        Patch patch = (Patch)actionObject;
        patch.setID(getID());
        patch.setJob(this);
        initParm();
        patch.setParm(getParm());
        status = patch.run();
        message = patch.getMessage();
        
        //System.out.println("======message======="+message);
        //结束记录
        endData(start,end,status,message);
        if(!status)
            expired();
    }
    /**
     * 初始化参数
     */
    public void initParm()
    {
        TParm parm = new TParm(TJDODBTool.getInstance().select("SELECT * FROM SYS_PATCH_PARM WHERE PATCH_CODE='" + getID() + "'"));
        if(parm.getErrCode() != 0)
            return;
        int count = parm.getCount();
        TParm v = new TParm();
        for(int i = 0;i < count;i++)
        {
            String name = parm.getValue("PATCH_PARM_NAME",i);
            String value = parm.getValue("PATCH_PARM_VALUE",i);
            v.setData(name,value);
        }
        setParm(v);
    }
    /**
     * 结束记录
     * @param start Date
     * @param end Date
     * @param status boolean
     * @param message String
     */
    public void endData(Date start,Date end,boolean status,String message)
    {
        String sql = "UPDATE SYS_PATCH_LOG SET " +
                     "PATCH_STATUS='" + (status?"成功":"错误") + "'," +
                     "PATCH_MESSAGE='" + message + "' " +
                     "WHERE PATCH_CODE='" + getID() + "' AND " +
                     "PATCH_START_DATE='" + StringTool.getString(start,"yyyyMMddHHmmss") + "'";
        TParm parm = new TParm(TJDODBTool.getInstance().update(sql));
        String s1 = "";
        if(getType() == 1)
            s1 = ",STATUS='N'";
        sql = "UPDATE SYS_PATCH SET END_DATE=to_Date('" + StringTool.getString(start,"yyyyMMddHHmmss") + "','yyyymmddhh24miss')"+
              s1 +
              " WHERE PATCH_CODE='" + getID() + "'";
        parm = new TParm(TJDODBTool.getInstance().update(sql));
    }
    /**
     * 启动记录
     * @param d Date
     */
    public void startData(Date d)
    {
        String sql = "INSERT INTO SYS_PATCH_LOG VALUES("+
                     "'" + getID() + "'," +
                     "'" + StringTool.getString(d,"yyyyMMddHHmmss") + "'," +
                     "'" + getName() + "'," +
                     "'" + getSrc() + "'," +
                     getType() + ","+
                     "'" + getPatchDate() + "'," +
                     getOthertimeCount() + ","+
                     "'" + getOthertimeString() + "'," +
                     (isNowOtherTime()?"'Y'":"'N'")+","+
                     getOthertimeIndex() + ","+
                     "'',"+
                     "'运行',"+
                     "''," +
                     "'',"+
                     "'Server',"+
                     "SYSDATE,"+
                     "''"+
                     ")";
        TParm parm = new TParm(TJDODBTool.getInstance().update(sql));
    }
    /**
     * 过期
     */
    public synchronized void expired()
    {
        //重送次数满
        if(getOthertimeIndex() >= getOthertimeCount())
            return;
        setOthertimeIndex(getOthertimeIndex() + 1);
        //重新设置时间
        if(isNowOtherTime())
            setDate(new Date(new Date().getTime() + getOthertime()));
        else
            setDate(new Date(getDate().getTime() + getOthertime()));
        getManager().add(this);
    }
    public String toString()
    {
        return name;
    }
}
