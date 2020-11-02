package com.dongyang.manager;

import java.util.Map;
import com.dongyang.data.TSocket;
import com.dongyang.data.TParm;
import com.dongyang.util.Log;

public class TIOM_BpelServer extends TIOM_Expend{
    public static boolean startServer(TSocket socket,String name)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x1);
        parm.setData("NAME",name);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
        {
            out("err:return null");
            return false;
        }
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
        {
            out("err:" + returnParm.getErrCode() + " " + returnParm.getErrText());
            return false;
        }
        return true;
    }
    public static boolean stopServer(TSocket socket,String name)
    {
        out("begin name=" + name);
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x2);
        parm.setData("NAME",name);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
        {
            out("err:return null");
            return false;
        }
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
        {
            out("err:" + returnParm.getErrCode() + " " + returnParm.getErrText());
            return false;
        }
        return true;
    }
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public static void out(String text) {
        Log.getInstance().out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public static void out(String text,boolean debug)
    {
        Log.getInstance().out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public static void err(String text) {
        Log.getInstance().err(text);
    }
    public static void main(String args[])
    {
        System.out.println(TIOM_BpelServer.stopServer(new TSocket("localhost",TSocket.BPEL_SERVER_PORT),"Pope"));
        System.out.println(TIOM_BpelServer.startServer(new TSocket("localhost",TSocket.BPEL_SERVER_PORT),"Pope"));
    }
}
