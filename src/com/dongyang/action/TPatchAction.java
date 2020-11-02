package com.dongyang.action;

import com.dongyang.data.TParm;
import com.dongyang.patch.PatchManager;

public class TPatchAction
{
    /**
     * 启动
     * @param parm TParm "boolean START"
     * @return TParm
     */
    public TParm start(TParm parm)
    {
        boolean state = parm.getBoolean("START");
        PatchManager.getManager().setStart(state);
        return new TParm();
    }
    /**
     * 服务器是否启动
     * @param parm TParm
     * @return TParm "boolean IS_START"
     */
    public TParm isStart(TParm parm)
    {
        TParm result = new TParm();
        result.setData("IS_START",PatchManager.getManager().isStart());
        return result;
    }
    /**
     * 设置休眠时间
     * @param parm TParm "int SLEEP_TIME"
     * @return TParm
     */
    public TParm setSleeptime(TParm parm)
    {
        PatchManager.getManager().setSleeptime(parm.getInt("SLEEP_TIME"));
        return new TParm();
    }
    /**
     * 得到休眠时间
     * @param parm TParm
     * @return TParm "int SLEEP_TIME"
     */
    public TParm getSleeptime(TParm parm)
    {
        TParm result = new TParm();
        int time = PatchManager.getManager().getSleeptime();
        result.setData("SLEEP_TIME",time);
        return result;
    }
    /**
     * 设置过期扫描时间
     * @param parm TParm "int EXPIRED_TIME"
     * @return TParm
     */
    public TParm setExpiredtime(TParm parm)
    {
        PatchManager.getManager().setExpiredtime(parm.getInt("EXPIRED_TIME"));
        return new TParm();
    }
    /**
     * 得到过期扫描时间
     * @param parm TParm
     * @return TParm "int EXPIRED_TIME"
     */
    public TParm getExpiredtime(TParm parm)
    {
        TParm result = new TParm();
        int time = PatchManager.getManager().getExpiredtime();
        result.setData("EXPIRED_TIME",time);
        return result;
    }
    /**
     * 删除作业
     * @param parm TParm "String POP_JOB"
     * @return TParm
     */
    public TParm popJob(TParm parm)
    {
        PatchManager.getManager().popJob(parm.getValue("POP_JOB"));
        return new TParm();
    }
    /**
     * 加载作业
     * @param parm TParm "String PUT_JOB"
     * @return TParm
     */
    public TParm putJob(TParm parm)
    {
        PatchManager.getManager().putJob(parm.getValue("PUT_JOB"));
        return new TParm();
    }
    /**
     * 得到加载数据
     * @param parm TParm
     * @return TParm "String[] DATA"
     */
    public TParm getList(TParm parm)
    {
        TParm result = new TParm();
        String data[] = PatchManager.getManager().getList();
        result.setData("DATA",data);
        return result;
    }
}
