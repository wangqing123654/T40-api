package com.dongyang.server;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import com.dongyang.data.TParm;
import java.util.Map;
import java.net.ServerSocket;
import com.dongyang.data.TSocket;
import java.io.File;
import com.dongyang.config.TConfigParm;
import com.dongyang.data.TTimer;
import java.util.HashMap;
import com.dongyang.Service.Server;

public class BpelServer implements Runnable {
    private static TConfigParm parm = new TConfigParm();
    private static Map pools = new HashMap();
    /**
     * socket ref
     */
    private Socket socket;
    public BpelServer(Socket socket) {
        this.socket = socket;
    }

    /**
     * 处理数据流
     */
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.
                    getOutputStream());
            Map data = (Map) in.readObject();
            TParm parm = new TParm();
            parm.setData(data);
            TParm result = work(parm);
            out.writeObject(result.getData());
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分析客户端请求
     * @param parm ActionParm
     * @return ActionParm
     */
    public TParm work(TParm parm) {
        int action = parm.getInt("ACTION");
        switch (action) {
        case 0x1: //启动服务器
            return startServer(parm);
        case 0x2: //停止服务器
            return stopServer(parm);
        }
        TParm result = new TParm();
        result.setErr( -1, "陌生的动作类型 ACTION:" + action);
        return result;
    }
    public TParm startServer(TParm parm)
    {
        String function = "startServer";
        String name = parm.getValue("NAME");
        out(function, "NAME=" + name);
        TParm result = new TParm();
        if (name == null || name.trim().length() == 0) {
            result.setErr( -1, "Err:BpelServer." + function + "()->NAME 参数为空!");
            out(function, "NAME 参数为空!");
            return result;
        }
        if(!loadTimer(name))
        {
            result.setErr( -1, "Err:BpelServer." + function + "()->流程" + name + "加载失败" );
            out(function, "没有找到流程" + name);
            return result;
        }
        return result;
    }
    public TParm stopServer(TParm parm)
    {
        String function = "stopServer";
        String name = parm.getValue("NAME");
        out(function, "NAME=" + name);
        TParm result = new TParm();
        if (name == null || name.trim().length() == 0) {
            result.setErr( -1, "Err:BpelServer." + function + "()->NAME 参数为空!");
            out(function, "NAME 参数为空!");
            return result;
        }
        TTimer timer = (TTimer)pools.get(name);
        if(timer == null){
            result.setErr( -1, "Err:BpelServer." + function + "()->没有找到流程" + name);
            out(function, "没有找到流程" + name);
            return result;
        }
        timer.isStart = false;
        return result;
    }
    public static void init()
    {

        parm.setSystemGroup("");
        parm.setConfig("BPEL.ini");
        parm.setConfigClass("BPELClass.ini");
        String webServerpath = parm.getConfig().getString("","WebServerPath");
        if(!Server.isInit())
        {
            Server.setServerPath(webServerpath);
            Server.onInit();
        }
        String item[] = parm.getConfig().getStringList(parm.getSystemGroup(),"item");
        for(int i = 0;i < item.length;i++)
        {
            loadTimer(item[i],false);
        }

    }
    public static boolean loadTimer(String name)
    {
        return loadTimer(name,true);
    }
    public static boolean loadTimer(String name,boolean resetConfig)
    {
        if(resetConfig)
            parm.reset();
        //组件类型
        String type = parm.getConfig().getString(parm.getSystemGroup(),name + ".type");
        if(type.length() == 0)
            return false;
        Object obj = parm.loadObject(type);
        if(obj == null || !(obj instanceof TTimer) )
            return false;
        TTimer timer = (TTimer)obj;
        pools.put(name,timer);
        timer.start();
        return true;
    }
    public static void main(String args[]) {
        init();
        int port = TSocket.BPEL_SERVER_PORT;
        if (args.length == 1)
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            ServerSocket server = new ServerSocket(port);
            out("BPEL Server 4.0 Start");
            while (true) {
                try {
                    Socket socket = server.accept();
                    new Thread(new BpelServer(socket)).start();
                } catch (Exception ep) {
                    ep.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void out(String function, String text) {
        System.out.println(function + "->" + text);
    }

    public static void out(String text) {
        System.out.println(text);
    }
}
