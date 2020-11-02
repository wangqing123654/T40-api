package com.dongyang.root.T;

import com.dongyang.util.FileTool;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.root.T.B.ObBase;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: Ot���</p>
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
public class Ot
        implements java.io.Serializable
{
    /**
     * ���
     */
    private int id;
    /**
     * ����
     */
    private Oi oi;
    /**
     * ������
     */
    private Oa oa;
    /**
     * ����ִ����
     */
    private Ob ob;
    /**
     * �ڿ�
     */
    private Od od;
    /**
     * ״̬
     */
    private int state;
    /**
     * ����
     */
    private int type;
    /**
     * ������
     */
    public Ot()
    {
        setOi(new Oi());
    }
    /**
     * ���ñ��
     * @param id int
     */
    public void setID(int id)
    {
        this.id = id;
    }
    /**
     * �õ����
     * @return int
     */
    public int getID()
    {
        return id;
    }
    /**
     * ����״̬
     * @param state int
     */
    public void setState(int state)
    {
        this.state = state;
    }
    /**
     * �õ�״̬
     * @return int
     */
    public int getState()
    {
        return state;
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
     * ���ô���
     * @param oi Oi
     */
    public void setOi(Oi oi)
    {
        this.oi = oi;
        if(oi != null)
            oi.setOt(this);
    }
    /**
     * �õ�����
     * @return Oi
     */
    public Oi getOi()
    {
        return oi;
    }
    /**
     * ���ô�����
     * @param oa Oa
     */
    public void setOa(Oa oa)
    {
        this.oa = oa;
        if(oa != null)
            oa.setOt(this);
    }
    /**
     * �õ�������
     * @return Oa
     */
    public Oa getOa()
    {
        return oa;
    }
    /**
     * ����ִ����
     * @param ob Ob
     */
    public void setOb(Ob ob)
    {
        this.ob = ob;
        if(ob != null)
            ob.setOt(this);
    }
    /**
     * �õ�ִ����
     * @return Ob
     */
    public Ob getOb()
    {
        return ob;
    }
    /**
     * �����ڿ�
     * @param od Od
     */
    public void setOd(Od od)
    {
        this.od = od;
    }
    /**
     * �õ��ڿ�
     * @return Od
     */
    public Od getOd()
    {
        return od;
    }
    /**
     * ���͹ܵ�
     * @param parm Object
     * @return Object
     */
    public Object outPipeLine(Object parm)
    {
        if(getOi() == null)
            return null;
        int id = getOi().chooseIo();
        return outPipeLine(id,parm);
    }
    /**
     * ���͹ܵ�
     * @param id int
     * @param parm Object
     * @return Object
     */
    public Object outPipeLine(int id,Object parm)
    {
        if(getOi() == null)
            return null;
        return outPipeLine(getOi().getSOt(id),parm);
    }
    /**
     * ���͹ܵ�
     * @param ot Ot
     * @param parm Object
     * @return Object
     */
    public Object outPipeLine(Ot ot,Object parm)
    {
        if(ot == null)
            return null;
        return ot.inPipeline(this,parm);
    }
    /**
     * ���չܵ�
     * @param ot Ot
     * @param parm Object
     * @return Object
     */
    public Object inPipeline(Ot ot,Object parm)
    {
        if(ot == null || getOi() == null)
            return null;
        int id = getOi().getSOt(ot);
        if(id == -1)
            return null;
        return inM(id,parm);
    }
    /**
     * ���
     * @param io int ͨ��
     * @param m Object ��Ϣ
     * @return Object
     */
    public Object inM(int io,Object m)
    {
        if(oa == null)
            return null;
        return oa.inM(io,m);
    }
    /**
     * ����
     */
    public void trigger()
    {
        if(ob != null)
            ob.trigger();
    }
    /**
     * ����
     * @param ot Ot
     * @return boolean
     */
    public boolean link(Ot ot)
    {
        if(oi == null||ot == null || ot.getOi() == null)
            return false;
        oi.link(ot,1);
        ot.getOi().link(this,-1);
        return true;
    }
    public static void main(String args[])
    {
        boolean b = true;
        if(b)
        {
            Od od = new Od();
            Oa oa = new Oa();
            Ot ot = new Ot();
            ot.setID(0);
            ot.setOd(od);
            ot.setOa(oa);
            ot.setOb(new ObBase());
            oa.setMap("1",new Object[]{"����",1,"����"});
            oa.setMap("2",new Object[]{"����",2,"����"});
            oa.setMap("+",new Object[]{"����","�ӷ�","����"});
            oa.setMap("=",new Object[]{"����","���","����"});

            Ot ot1 = new Ot();
            ot1.setID(1);
            ot1.setOa(oa);
            ot.link(ot1);

            Ot ot2 = new Ot();
            ot2.setID(2);
            oa = new Oa();
            ot2.setOa(oa);
            ot.link(ot2);

            Ot ot4 = new Ot();
            ot4.setID(4);
            oa = new Oa();
            ot4.setOa(oa);
            ot4.link(ot);

            Ot ot3 = new Ot();
            ot3.setID(3);
            oa = new Oa();
            ot3.setOa(oa);
            ot.link(ot3);

            ot.getOi().g(4);
            ot.getOi().g(2);
            ot.getOi().g(1);
            ot.getOi().g(1);

            System.out.println(ot.getOi());
            System.out.println(ot1.getOi());
            System.out.println(ot2.getOi());
            System.out.println(ot3.getOi());
            System.out.println(ot4.getOi());
            System.out.println(ot.outPipeLine("XXX"));
            //System.out.println(StringTool.getString((Object[])ot.inM(0,"1")));
            try
            {
                FileTool.setObject("ot.t", ot);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            //ot.getOb().startLife();
            try
            {
                Ot ot = (Ot) FileTool.getObject("ot.t");
                System.out.println(ot.getOi());
                System.out.println(StringTool.getString((Object[])ot.inM(0,"1")));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
