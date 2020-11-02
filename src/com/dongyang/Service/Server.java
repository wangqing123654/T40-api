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
 * <p>Title: �������ܿ�</p>
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
     * �������Ƿ��ʼ��
     */
    private static boolean isInit;
    /**
     * ������·��
     */
    private static String serverPath;
    /**
     * ���ö���
     */
    private static TConfigParm configParm = new TConfigParm();
    /**
     * ���÷�����·��
     * @param serverPath String
     */
    public static void setServerPath(String serverPath)
    {
        Server.serverPath = serverPath;
    }
    /**
     * ���õ�ǰĿ¼
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
     * �Զ���ʼ��
     * @param obj Object
     */
    public static void autoInit(Object obj)
    {
        if(obj == null)
            return;
        autoInit(obj.getClass().getResource("/"));
    }
    /**
     * �Զ���ʼ��
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
     * �õ�������·��
     * @return String
     */
    public static String getServerPath()
    {
        return serverPath;
    }
    /**
     * �õ�����Ŀ¼
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
     * �õ����ö���
     * @return TConfigParm
     */
    public static TConfigParm getConfigParm()
    {
        return configParm;
    }
    /**
     * �������Ƿ��ʼ��
     * @return boolean
     */
    public static boolean isInit()
    {
        return isInit;
    }
    /**
     * ��ʼ��������
     */
    public static void onInit()
    {
        if(isInit())
            return;
        String dir = getConfigDir() + "system/";
        configParm.setSystemGroup("");
        configParm.setConfig(dir + "Service.x");
        configParm.setConfigClass(dir + "TClass.x",getServerPath());
        //��ʼ�����ݿ����ӳ�
        initDB();
        isInit = true;
        //��ʼ������
        initPatch();
    }
    /**
     * ��ʼ�����ݿ����ӳ�
     */
    public static void initDB()
    {
        String path = getServerPath();
        if(!path.endsWith("\\"))
            path += "\\";
        TDBPoolManager.getInstance().init(path + "WEB-INF\\config\\system\\");
    }
    /**
     * ��ʼ������
     */
    public static void initPatch()
    {
        PatchManager.getManager();
        //$$========add by lx 2012/06/26 �������ο���start==========$$//
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
     * ִ��Action
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
            result.setErr(-1,"û���ҵ�������" + className);
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
            result.setErr(-1,"û���ҵ������෽��" + className + "." + methodName);
            return result;
        }
        try{
            Object obj = method.invoke(actionObject, new Object[] {parm});
            if(obj == null||!(obj instanceof TParm))
            {
                TParm result = new TParm();
                result.setErr(-1,"�ز�Ϊ��");
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
     * �õ�����
     * @param methodName String
     * @param actionObject Object
     * @return Method
     */
    public static Method getMethods(String methodName, Object actionObject)
    {
      //�������
      if (methodName == null)
        return null;

      //ѭ��������
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
     * ִ���ⲿ��
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
            System.out.println("û���ҵ���" + className);
            return null;
        }
        Object valueObj = RunClass.runMethod(object, methodName, parameters);
        return valueObj;
    }
}
