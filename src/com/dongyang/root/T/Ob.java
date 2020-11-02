package com.dongyang.root.T;

import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: Ob动作执行器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.29
 * @version 1.0
 */
public class Ob implements java.io.Serializable
{
    /**
     * 外壳
     */
    private Ot ot;
    /**
     * 消息源
     */
    private life life;
    /**
     * 设置外壳
     * @param ot Ot
     */
    public void setOt(Ot ot)
    {
        this.ot = ot;
    }

    /**
     * 得到外壳
     * @return Ot
     */
    public Ot getOt()
    {
        return ot;
    }

    /**
     * 入口
     * @param io int 通道
     * @param m String 信息
     * @return Object 结果
     */
    public Object inM(int io, String m)
    {
        return inM(io, m, null);
    }

    /**
     * 通道
     * @param io int 通道
     * @param m String 信息
     * @param parm Object 参数
     * @return Object 结果
     */
    public Object inM(int io, String m, Object parm)
    {
        if (io == 0)
            return baseAction(m, parm);
        return null;
    }

    /**
     * 基础动作
     * @param m String
     * @param parm Object
     * @return Object
     */
    public Object baseAction(String m, Object parm)
    {
        //处理方法
        String value[] = StringTool.getHead(m, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(m, "=");
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * 启动消息源
     * @return boolean
     */
    public boolean startLife()
    {
        if(life == null)
            life = new life();
        life.start();
        return true;
    }
    /**
     * 停止消息源
     * @return boolean
     */
    public boolean stopLife()
    {
        if(life == null)
            return false;
        life.stop();
        life = null;
        return true;
    }
    class life implements java.io.Serializable, Runnable
    {
        private boolean life;
        private int sleep = 100;
        public void start()
        {
            if(life)
                return;
            life = true;
            new Thread(this).start();
        }
        public void stop()
        {
            life = false;
        }
        public void run()
        {
            while (life)
            {
                if(ot != null)
                    ot.trigger();
                try
                {
                    Thread.sleep(sleep);
                } catch (Exception e)
                {
                }
            }
        }
    }
    /**
     * 出发
     */
    public void trigger()
    {
    }
}
