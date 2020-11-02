package com.dongyang.module.base;

import com.dongyang.data.TParm;
import java.util.Date;

public class TModuleR
{
    private TParm result;
    private long time;
    public TModuleR()
    {
        setTime(new Date().getTime());
    }
    public void setResult(TParm result)
    {
        this.result = result;
    }
    public TParm getResult()
    {
        return result;
    }
    public void setTime(long time)
    {
        this.time = time;
    }
    public long getTime()
    {
        return time;
    }
}
