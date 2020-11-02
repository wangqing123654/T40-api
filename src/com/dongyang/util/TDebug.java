package com.dongyang.util;

import com.dongyang.config.TConfigParm;
import com.dongyang.config.TConfig;
import com.dongyang.Service.DataService;
import com.dongyang.module.TModule;
import com.dongyang.db.TDBPoolManager;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.Service.Server;

public class TDebug {
    /**
     * 客户端测试初始化(测试程序专用)
     */
    public static void initClient()
    {
        System.out.println("----------------------------------------------");
        System.out.println("   Client测试模式  Javahis V1.0");
        System.out.println("----------------------------------------------");
        TConfig config = new TConfig("Debug.ini",null);
        String ip = config.getString("","IP");
        int port = config.getInt("","Port");
        String web = config.getString("","Web");
        TSocket socket = new TSocket(ip,port,web);
        TIOM_AppServer.SOCKET = socket;
    }
    /**
     * 服务器端测试初始化(测试程序专用)
     */
    public static void initServer()
    {
        System.out.println("----------------------------------------------");
        System.out.println("   Server测试模式  Javahis V1.0");
        System.out.println("----------------------------------------------");
        initServer("Debug.ini");
    }
    public static void initServer(String ini)
    {
        TConfig config = new TConfig(ini,null);
        String webServerpath = config.getString("","WebServerPath");
        //
        System.out.println("-----webServerpath---"+webServerpath);
        //
        String systemGroup = config.getString("","SystemGroup");
        TModule a;
        if(!Server.isInit())
        {
            Server.setServerPath(webServerpath);
            Server.onInit();
        }

        //startDB(webServerpath);

        DataService.setConfigDir(webServerpath + "\\WEB-INF\\config\\");
        String systemDir = DataService.getConfigDir() + "system\\";
        TConfigParm configParm = new TConfigParm();
        configParm.setSystemGroup(systemGroup);
        configParm.setConfig(systemDir + "Service.x");
        configParm.setConfigClass(systemDir + "TClass.x",webServerpath);
        DataService.setConfigParm(configParm);
    }
    /**
     * 启动数据库(测试程序专用)
     * @param path String 网站根目录目录
     */
    public static void startDB(String path)
    {
        if(path == null)
            return;
        if(!path.endsWith("\\"))
            path += "\\";
        path += "WEB-INF\\config\\system\\";
        TDBPoolManager dbManager = TDBPoolManager.getInstance();
        dbManager.init(path);
    }
}
