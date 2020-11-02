package com.dongyang.root.T.B;

import com.dongyang.root.T.Ob;

public class ObBase extends Ob
{
    /**
     * 通道
     * @param io int 通道
     * @param m String 信息
     * @param parm Object 参数
     * @return Object 结果
     */
    public Object inM(int io,String m,Object parm)
    {
        return super.inM(io,m,parm);
    }
    /**
     * 输出
     * @param s String
     * @return Object
     */
    public Object out(String s)
    {
        System.out.println(s);
        return "OK";
    }
}
