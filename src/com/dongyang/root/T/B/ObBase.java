package com.dongyang.root.T.B;

import com.dongyang.root.T.Ob;

public class ObBase extends Ob
{
    /**
     * ͨ��
     * @param io int ͨ��
     * @param m String ��Ϣ
     * @param parm Object ����
     * @return Object ���
     */
    public Object inM(int io,String m,Object parm)
    {
        return super.inM(io,m,parm);
    }
    /**
     * ���
     * @param s String
     * @return Object
     */
    public Object out(String s)
    {
        System.out.println(s);
        return "OK";
    }
}
