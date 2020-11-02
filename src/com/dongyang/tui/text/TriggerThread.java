package com.dongyang.tui.text;

import com.dongyang.util.RunClass;

/**
 *
 * <p>Title: 触发线称</p>
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
public class TriggerThread implements Runnable
{
    Thread thread;
    /**
     * 出发对象
     */
    private Object obj;
    /**
     * 出发方法
     */
    private String methodName;
    /**
     * 构造器
     * @param obj Object
     * @param methodName String
     */
    public TriggerThread(Object obj,String methodName)
    {
        this.obj = obj;
        this.methodName = methodName;
    }
    /**
     * 触发方法
     */
    public void runFunction()
    {
        RunClass.runMethod(obj,methodName,new Object[]{});
    }
    /**
     * 启动线程
     */
    public void start()
    {
        if(thread != null)
            return;
        thread = new Thread(this);
        thread.start();
    }
    /**
     * 停止线程
     */
    public void stop()
    {
        if(thread == null)
            return;
        thread = null;
    }
    /**
     * 运行
     */
    public void run()
    {
        //触发方法
        runFunction();
        try{
            thread.sleep(500);
        }catch(Exception e)
        {
        }
        while(thread != null)
        {
            //触发方法
            runFunction();
            try{
                thread.sleep(10);
            }catch(Exception e)
            {
            }
        }
    }
}
