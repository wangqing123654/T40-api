package com.dongyang.root.T;

import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: Ob����ִ����</p>
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
     * ���
     */
    private Ot ot;
    /**
     * ��ϢԴ
     */
    private life life;
    /**
     * �������
     * @param ot Ot
     */
    public void setOt(Ot ot)
    {
        this.ot = ot;
    }

    /**
     * �õ����
     * @return Ot
     */
    public Ot getOt()
    {
        return ot;
    }

    /**
     * ���
     * @param io int ͨ��
     * @param m String ��Ϣ
     * @return Object ���
     */
    public Object inM(int io, String m)
    {
        return inM(io, m, null);
    }

    /**
     * ͨ��
     * @param io int ͨ��
     * @param m String ��Ϣ
     * @param parm Object ����
     * @return Object ���
     */
    public Object inM(int io, String m, Object parm)
    {
        if (io == 0)
            return baseAction(m, parm);
        return null;
    }

    /**
     * ��������
     * @param m String
     * @param parm Object
     * @return Object
     */
    public Object baseAction(String m, Object parm)
    {
        //������
        String value[] = StringTool.getHead(m, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(m, "=");
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * ������ϢԴ
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
     * ֹͣ��ϢԴ
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
     * ����
     */
    public void trigger()
    {
    }
}
