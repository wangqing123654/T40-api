package com.dongyang.system;

import com.dongyang.ui.TApplet;
import com.dongyang.ui.TButton;
import javax.swing.UIManager;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.StringTool;
import com.dongyang.util.Log;
import com.dongyang.data.TParm;
import com.dongyang.config.TRegistry;

public class TMain extends TApplet{
	//static { System.setSecurityManager(null); }
    public TMain()
    {
        //System.out.println("aaa " + TRegistry.get("HKEY_LOCAL_MACHINE\\Software\\JavaSoft\\Java Plug-in"));
        //String path = TRegistry.get("HKEY_LOCAL_MACHINE\\Software\\JavaSoft\\Java Plug-in\\1.5.0_06\\JavaHome");
        //System.out.println("path=" + path);
    }
    /**
     * 初始化Socket
     */
    public void initSocket()
    {
        String host = getCodeBase().getHost();
        int port = getCodeBase().getPort();
        String path = getCodeBase().getPath();
        if(path.toUpperCase().endsWith("/COMMON/LIB/"))
            path = path.substring(0,path.length() - 12);
        if(path.startsWith("/"))
            path = path.substring(1,path.length());
        System.out.println("连接主机:" + host + ":" + port + "/" + path);
        TIOM_AppServer.SOCKET = new TSocket(host,port,path);
    }
    /**
     * 初始化配置对象
     * @return TConfigParm
     */
    public TConfigParm initConfigParm()
    {
        //设置标签
        setTag(getParameter("tag"));
        //建立基本配置参数对象
        TConfigParm configParm = new TConfigParm();
        //装载Socket
        configParm.setSocket(TIOM_AppServer.SOCKET);
        //初始化系统组别
        configParm.setSystemGroup("");
        //装载主配置文件
        configParm.setConfig(getParameter("config"));
        //装载颜色配置文件
        configParm.setConfigColor(getParameter("configColor"));
        //装载Class配置文件
        configParm.setConfigClass(getParameter("configClass"));
        //设置系统组别
        configParm.setSystemGroup(configParm.getConfig().getString("",getTag() + ".SystemGroup"));
        //日志追加标记
        boolean appendLog = StringTool.getBoolean(configParm.getConfig().getString("",getTag() + ".AppendLog"));
        //输出日志目录
        String outLogPath = configParm.getConfig().getString("",getTag() + ".OutLogPath");
        //错误日志日志目录
        String errLogPath = configParm.getConfig().getString("",getTag() + ".ErrLogPath");
        //初始化日志输出目录
        Log.initLogPath(outLogPath,errLogPath,appendLog);
        //初始化系统界面风格
        try{
            UIManager.setLookAndFeel(configParm.getConfig().getString(configParm.getSystemGroup(),getTag() + ".LookAndFeel",
                                     UIManager.getSystemLookAndFeelClassName()));
        }catch(Exception e)
        {
            err(e.getMessage());
        }
        return configParm;
    }
    /**
     * Applet启动初始化
     */
    public void init()
    {
        //初始化Socket
        initSocket();
        //加载界面
        init(initConfigParm());

        //System.out.println("ID=" + TIOM_AppServer.getSessionAttribute("ID"));
        //System.out.println(StringTool.getString(TIOM_AppServer.getSessionAttributeNames()));

        //启动系统初始化
    	String ldapuser = getParameter("LDAPUSER");
    	String ldappw = getParameter("LDAPPW");
    	if( null!=ldapuser && !"".equals(ldapuser) && null!=ldappw && !"".equals(ldappw) ){
    		onInit(ldapuser,ldappw);
    	}else{
    		onInit();
    	}

    }
    /**
     * Applet结束
     */
    public void destroy() {
        super.destroy();
        Log.closeLogFile();
    }
}
