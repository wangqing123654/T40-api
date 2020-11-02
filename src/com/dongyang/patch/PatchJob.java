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
 * <p>Title: ������</p>
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
     * ������
     */
    private PatchManager manager;
    /**
     * ���
     */
    private String id;
    /**
     * ����
     */
    private String name;
    /**
     * ִ��ʱ��
     */
    private Date date;
    /**
     * ����
     */
    private boolean isStart;
    /**
     * ����ʱ����
     */
    private long othertime = 4000;
    /**
     * ����ʱ�����ִ�
     */
    private String othertimeString = "";
    /**
     * �Ƿ��ص�ǰʱ����ʱ����
     */
    private boolean nowOtherTime = true;
    /**
     * �����ʹ���
     */
    private int othertimeCount = 2;
    /**
     * ���ʹ���
     */
    private int othertimeIndex;
    /**
     * ִ�г���
     */
    private String src;
    /**
     * ����
     */
    private int type;
    /**
     * ����
     */
    private TParm parm;
    /**
     * �ϴν���ʱ��
     */
    private Date endDate;
    /**
     * ��ʼʱ��
     */
    private String patchDate;
    /**
     * ������
     * @param manager PatchManager
     */
    public PatchJob(PatchManager manager)
    {
        setManager(manager);
    }
    /**
     * ������
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
     * ���ñ��
     * @param id String
     */
    public void setID(String id)
    {
        this.id = id;
    }
    /**
     * �õ����
     * @return String
     */
    public String getID()
    {
        return id;
    }
    /**
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * ����ִ��ʱ��
     * @param date Date
     */
    public void setDate(Date date)
    {
        this.date = date;
    }
    /**
     * �õ�ִ��ʱ��
     * @return Date
     */
    public Date getDate()
    {
        return date;
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
     * ��������ʱ����
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
     * �õ�����ʱ����
     * @return String
     */
    public String getOthertimeString()
    {
        return othertimeString;
    }
    /**
     * ��������ʱ����
     * @param othertime long
     */
    public void setOthertime(long othertime)
    {
        this.othertime = othertime;
    }
    /**
     * �õ�����ʱ����
     * @return long
     */
    public long getOthertime()
    {
        return othertime;
    }
    /**
     * ���ÿ����ʹ���
     * @param othertimeCount int
     */
    public void setOthertimeCount(int othertimeCount)
    {
        this.othertimeCount = othertimeCount;
    }
    /**
     * �õ������ʹ���
     * @return int
     */
    public int getOthertimeCount()
    {
        return othertimeCount;
    }
    /**
     * �������ʹ���
     * @param othertimeIndex int
     */
    public void setOthertimeIndex(int othertimeIndex)
    {
        this.othertimeIndex = othertimeIndex;
    }
    /**
     * �õ����ʹ���
     * @return int
     */
    public int getOthertimeIndex()
    {
        return othertimeIndex;
    }
    /**
     * �����Ƿ��ص�ǰʱ����ʱ����
     * @param nowOtherTime boolean
     */
    public void setNowOtherTime(boolean nowOtherTime)
    {
        this.nowOtherTime = nowOtherTime;
    }
    /**
     * �Ƿ��Ƿ��ص�ǰʱ����ʱ����
     * @return boolean
     */
    public boolean isNowOtherTime()
    {
        return nowOtherTime;
    }
    /**
     * ����ִ�г���
     * @param src String
     */
    public void setSrc(String src)
    {
        this.src = src;
    }
    /**
     * �õ�ִ�г���
     * @return String
     */
    public String getSrc()
    {
        return src;
    }
    /**
     * ��������
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * ���ò���
     * @param parm TParm
     */
    public void setParm(TParm parm)
    {
        this.parm = parm;
    }
    /**
     * �õ�����
     * @return TParm
     */
    public TParm getParm()
    {
        return parm;
    }
    /**
     * �����ϴν���ʱ��
     * @param date Date
     */
    public void setEndDate(Date date)
    {
        this.endDate = date;
    }
    /**
     * �õ��ϴν���ʱ��
     * @return Date
     */
    public Date getEndDate()
    {
        return endDate;
    }
    /**
     * ���ÿ�ʼʱ��
     * @param patchDate String
     */
    public void setPatchDate(String patchDate)
    {
        this.patchDate = patchDate;
    }
    /**
     * �õ���ʼʱ��
     * @return String
     */
    public String getPatchDate()
    {
        return patchDate;
    }
    /**
     * ��������
     * @param d Date
     * @return boolean
     */
    public boolean checkDate(Date d)
    {
        switch(getType())
        {
        case 1://һ����
            setDate(StringTool.getDate(getPatchDate(),"yyyyMMddHHmmss"));
            break;
        case 2://ÿ��ִ��
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
        case 3://ÿ��ִ��
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
        case 4://ÿ��ִ��
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
        case 5://ÿ��ִ��
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
     * ����ʱ�����
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
            //�Ѿ�ִ�й���
            if(x <= 0)
                return false;
        }
        return true;
    }
    /**
     * ����
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
     * �Ƿ�����
     * @return boolean
     */
    public synchronized boolean isStart()
    {
        return isStart;
    }
    /**
     * ����
     */
    public void work()
    {
        if(!start())
            return;
        Date start = new Date();
        //������¼
        startData(start);
        Date end = new Date();
        boolean status = true;
        String message = "";
        Object actionObject = Server.getConfigParm().loadObject(getSrc());
        //System.out.println("==========actionObject==========="+actionObject);
        if(actionObject == null)
        {
            status = false;
            message = getSrc() + "������";
            endData(start,end,status,message);
            return;
        }
        if(!(actionObject instanceof Patch))
        {
            status = false;
            message = getSrc() + "��Ҫ�̳� com.dongyang.patch.Patch";
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
        //������¼
        endData(start,end,status,message);
        if(!status)
            expired();
    }
    /**
     * ��ʼ������
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
     * ������¼
     * @param start Date
     * @param end Date
     * @param status boolean
     * @param message String
     */
    public void endData(Date start,Date end,boolean status,String message)
    {
        String sql = "UPDATE SYS_PATCH_LOG SET " +
                     "PATCH_STATUS='" + (status?"�ɹ�":"����") + "'," +
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
     * ������¼
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
                     "'����',"+
                     "''," +
                     "'',"+
                     "'Server',"+
                     "SYSDATE,"+
                     "''"+
                     ")";
        TParm parm = new TParm(TJDODBTool.getInstance().update(sql));
    }
    /**
     * ����
     */
    public synchronized void expired()
    {
        //���ʹ�����
        if(getOthertimeIndex() >= getOthertimeCount())
            return;
        setOthertimeIndex(getOthertimeIndex() + 1);
        //��������ʱ��
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
