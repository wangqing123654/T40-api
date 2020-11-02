package com.dongyang.patch;

import java.util.Date;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: ���θ���</p>
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
     * ���
     */
    private String ID;
    /**
     * ��Ϣ
     */
    private String message = "";
    /**
     * ����ʱ��
     */
    private Date startDate;
    /**
     * ��ҵ
     */
    private PatchJob job;
    /**
     * ����
     */
    private TParm parm;
    /**
     * ���ñ��
     * @param ID String
     */
    public void setID(String ID)
    {
        this.ID = ID;
    }
    /**
     * �õ����
     * @return String
     */
    public String getID()
    {
        return ID;
    }
    /**
     * ��������ʱ��
     * @param startDate Date
     */
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    /**
     * �õ�����ʱ��
     * @return Date
     */
    public Date getStartDate()
    {
        return startDate;
    }
    /**
     * ������Ϣ
     * @param message String
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
    /**
     * �õ���Ϣ
     * @return String
     */
    public String getMessage()
    {
        return message;
    }
    /**
     * ������ҵ
     * @param job PatchJob
     */
    public void setJob(PatchJob job)
    {
        this.job = job;
    }
    /**
     * �õ���ҵ
     * @return PatchJob
     */
    public PatchJob getJob()
    {
        return job;
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
     * ����
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
