package com.dongyang.Service;

import com.dongyang.db.TDBPoolManager;
import java.util.Date;
import com.dongyang.data.TParm;
import com.dongyang.action.TAction;
import java.lang.reflect.Method;
import com.dongyang.config.TConfigParm;
import com.dongyang.patch.PatchManager;
import com.dongyang.util.RunClass;
import java.net.URL;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: 服务器管控</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.6.4
 * @version 1.0
 */
public class Server
{
    /**
     * 服务器是否初始化
     */
    private static boolean isInit;
    /**
     * 服务器路径
     */
    private static String serverPath;
    /**
     * 配置对象
     */
    private static TConfigParm configParm = new TConfigParm();
    /**
     * 设置服务器路径
     * @param serverPath String
     */
    public static void setServerPath(String serverPath)
    {
        Server.serverPath = serverPath;
    }
    /**
     * 设置当前目录
     * @param url URL
     */
    public static void setServerPath(URL url)
    {
        String dir = url.getPath();
        dir = StringTool.replaceAll(dir,"%20"," ");
        if(dir.endsWith("/WEB-INF/classes/"))
            dir = dir.substring(0,dir.length() - 17);
        setServerPath(dir);
    }
    /**
     * 自动初始化
     * @param obj Object
     */
    public static void autoInit(Object obj)
    {
        if(obj == null)
            return;
        autoInit(obj.getClass().getResource("/"));
    }
    /**
     * 自动初始化
     * @param url URL
     */
    public static void autoInit(URL url)
    {
        if(isInit())
            return;
        setServerPath(url);
        Server.onInit();
    }
    /**
     * 得到服务器路径
     * @return String
     */
    public static String getServerPath()
    {
        return serverPath;
    }
    /**
     * 得到配置目录
     * @return String
     */
    public static String getConfigDir()
    {
        String path = getServerPath();
        if(!path.endsWith("/"))
            path += "/";
        return path + "WEB-INF/config/";
    }
    /**
     * 得到配置对象
     * @return TConfigParm
     */
    public static TConfigParm getConfigParm()
    {
        return configParm;
    }
    /**
     * 服务器是否初始化
     * @return boolean
     */
    public static boolean isInit()
    {
        return isInit;
    }
    /**
     * 初始化服务器
     */
    public static void onInit()
    {
        if(isInit())
            return;
        String dir = getConfigDir() + "system/";
        configParm.setSystemGroup("");
        configParm.setConfig(dir + "Service.x");
        configParm.setConfigClass(dir + "TClass.x",getServerPath());
        //初始化数据库连接池
        initDB();
        isInit = true;
        //初始化批次
        initPatch();
    }
    /**
     * 初始化数据库连接池
     */
    public static void initDB()
    {
        String path = getServerPath();
        if(!path.endsWith("\\"))
            path += "\\";
        TDBPoolManager.getInstance().init(path + "WEB-INF\\config\\system\\");
    }
    /**
     * 初始化批次
     */
    public static void initPatch()
    {
        PatchManager.getManager();
        //$$========add by lx 2012/06/26 加入批次开关start==========$$//
        String dir = getConfigDir() + "system/";
        configParm.setSystemGroup("");
        configParm.setConfig(dir + "TConfig.x");
        String batchServer=configParm.getConfig().getString("", "BatchServer", "N");
        //System.out.println("=========rxx=========="+batchServer);
        boolean flg=false;
        if(batchServer.equals("Y")){
        	flg=true;
        }
        //$$========add by lx 2012/06/26 end==========$$//
        PatchManager.getManager().setStart(flg);
    }
    /**
     * 执行Action
     * @param className String
     * @param methodName String
     * @param parm TParm
     * @return TParm
     */
    public static TParm executeAction(String className,String methodName,TParm parm)
    {
        Object actionObject = configParm.loadObject(className);
        if(actionObject == null)
        {
            TParm result = new TParm();
            result.setErr(-1,"没有找到动作类" + className);
            return result;
        }
        if(actionObject instanceof TAction)
        {
            TAction action = (TAction)actionObject;
            action.setIP(parm.getValue("SYSTEM","IP"));
            action.setInParm(parm);
            String configName = "";
            if(configName.length() > 0)
                action.setConfigParm(configParm.newConfig(getServerPath() + configName));
            else
                action.setConfigParm(configParm);
        }
        Method method = getMethods(methodName, actionObject);
        if(method == null)
        {
            TParm result = new TParm();
            result.setErr(-1,"没有找到动作类方法" + className + "." + methodName);
            return result;
        }
        try{
            Object obj = method.invoke(actionObject, new Object[] {parm});
            if(obj == null||!(obj instanceof TParm))
            {
                TParm result = new TParm();
                result.setErr(-1,"回参为空");
                return result;
            }
            return (TParm)obj;
        }catch(Exception e)
        {
            TParm result = new TParm();
            result.setErr(-1,e.getMessage());
            e.printStackTrace();
            return result;
        }
    }
    /**
     * 得到方法
     * @param methodName String
     * @param actionObject Object
     * @return Method
     */
    public static Method getMethods(String methodName, Object actionObject)
    {
      //参数检测
      if (methodName == null)
        return null;

      //循环检测参数
      Method[] methods = actionObject.getClass().getMethods();
      for (int i = 0; i < methods.length; i++)
      {
        if (methods[i].getName().equals(methodName) &&
            methods[i].getParameterTypes().length == 1 &&
            methods[i].getParameterTypes()[0].getName().equals("com.dongyang.data.TParm"))
          return methods[i];
      }
      return null;
    }
    /**
     * 执行外部类
     * @param className String
     * @param methodName String
     * @param parameters Object[]
     * @return Object
     */
    public static Object exeServerClass(String className,String methodName,Object[] parameters)
    {
        Object object = getConfigParm().loadObject(className);
        if(object == null)
        {
            System.out.println("没有找到类" + className);
            return null;
        }
        Object valueObj = RunClass.runMethod(object, methodName, parameters);
        return valueObj;
    }
}
