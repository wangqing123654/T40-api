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
     * ��ʼ��Socket
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
        System.out.println("��������:" + host + ":" + port + "/" + path);
        TIOM_AppServer.SOCKET = new TSocket(host,port,path);
    }
    /**
     * ��ʼ�����ö���
     * @return TConfigParm
     */
    public TConfigParm initConfigParm()
    {
        //���ñ�ǩ
        setTag(getParameter("tag"));
        //�����������ò�������
        TConfigParm configParm = new TConfigParm();
        //װ��Socket
        configParm.setSocket(TIOM_AppServer.SOCKET);
        //��ʼ��ϵͳ���
        configParm.setSystemGroup("");
        //װ���������ļ�
        configParm.setConfig(getParameter("config"));
        //װ����ɫ�����ļ�
        configParm.setConfigColor(getParameter("configColor"));
        //װ��Class�����ļ�
        configParm.setConfigClass(getParameter("configClass"));
        //����ϵͳ���
        configParm.setSystemGroup(configParm.getConfig().getString("",getTag() + ".SystemGroup"));
        //��־׷�ӱ��
        boolean appendLog = StringTool.getBoolean(configParm.getConfig().getString("",getTag() + ".AppendLog"));
        //�����־Ŀ¼
        String outLogPath = configParm.getConfig().getString("",getTag() + ".OutLogPath");
        //������־��־Ŀ¼
        String errLogPath = configParm.getConfig().getString("",getTag() + ".ErrLogPath");
        //��ʼ����־���Ŀ¼
        Log.initLogPath(outLogPath,errLogPath,appendLog);
        //��ʼ��ϵͳ������
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
     * Applet������ʼ��
     */
    public void init()
    {
        //��ʼ��Socket
        initSocket();
        //���ؽ���
        init(initConfigParm());

        //System.out.println("ID=" + TIOM_AppServer.getSessionAttribute("ID"));
        //System.out.println(StringTool.getString(TIOM_AppServer.getSessionAttributeNames()));

        //����ϵͳ��ʼ��
    	String ldapuser = getParameter("LDAPUSER");
    	String ldappw = getParameter("LDAPPW");
    	if( null!=ldapuser && !"".equals(ldapuser) && null!=ldappw && !"".equals(ldappw) ){
    		onInit(ldapuser,ldappw);
    	}else{
    		onInit();
    	}

    }
    /**
     * Applet����
     */
    public void destroy() {
        super.destroy();
        Log.closeLogFile();
    }
}
