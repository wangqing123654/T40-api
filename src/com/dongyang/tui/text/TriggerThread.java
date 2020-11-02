package com.dongyang.tui.text;

import com.dongyang.util.RunClass;

/**
 *
 * <p>Title: �����߳�</p>
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
     * ��������
     */
    private Object obj;
    /**
     * ��������
     */
    private String methodName;
    /**
     * ������
     * @param obj Object
     * @param methodName String
     */
    public TriggerThread(Object obj,String methodName)
    {
        this.obj = obj;
        this.methodName = methodName;
    }
    /**
     * ��������
     */
    public void runFunction()
    {
        RunClass.runMethod(obj,methodName,new Object[]{});
    }
    /**
     * �����߳�
     */
    public void start()
    {
        if(thread != null)
            return;
        thread = new Thread(this);
        thread.start();
    }
    /**
     * ֹͣ�߳�
     */
    public void stop()
    {
        if(thread == null)
            return;
        thread = null;
    }
    /**
     * ����
     */
    public void run()
    {
        //��������
        runFunction();
        try{
            thread.sleep(500);
        }catch(Exception e)
        {
        }
        while(thread != null)
        {
            //��������
            runFunction();
            try{
                thread.sleep(10);
            }catch(Exception e)
            {
            }
        }
    }
}
