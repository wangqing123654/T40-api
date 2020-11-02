package com.dongyang.patch;

import java.util.Date;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 批次父类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.7.16
 * @version 1.0
 */
public class Patch
{
    /**
     * 编号
     */
    private String ID;
    /**
     * 消息
     */
    private String message = "";
    /**
     * 启动时间
     */
    private Date startDate;
    /**
     * 作业
     */
    private PatchJob job;
    /**
     * 参数
     */
    private TParm parm;
    /**
     * 设置编号
     * @param ID String
     */
    public void setID(String ID)
    {
        this.ID = ID;
    }
    /**
     * 得到编号
     * @return String
     */
    public String getID()
    {
        return ID;
    }
    /**
     * 设置启动时间
     * @param startDate Date
     */
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    /**
     * 得到启动时间
     * @return Date
     */
    public Date getStartDate()
    {
        return startDate;
    }
    /**
     * 设置消息
     * @param message String
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
    /**
     * 得到消息
     * @return String
     */
    public String getMessage()
    {
        return message;
    }
    /**
     * 设置作业
     * @param job PatchJob
     */
    public void setJob(PatchJob job)
    {
        this.job = job;
    }
    /**
     * 得到作业
     * @return PatchJob
     */
    public PatchJob getJob()
    {
        return job;
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
     * 运行
     * @return boolean
     */
    public boolean run()
    {
        return true;
    }
    public String toString()
    {
        return "Patch:ID=" + getID();
    }
}
