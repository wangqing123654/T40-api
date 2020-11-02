package com.dongyang.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.util.Map;
import com.dongyang.data.TParm;
import java.util.HashMap;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.Log;
import java.lang.reflect.Method;
import com.dongyang.action.TAction;
import com.dongyang.module.TModule;
import com.dongyang.util.RunClass;
import com.dongyang.patch.PatchManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class DataService extends HttpServlet implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7736577934772576519L;
	/**
     * ȫ��ʵ��
     */
    public static DataService instanceObject;
    /**
     * ���ö���
     */
    private static TConfigParm configParm = new TConfigParm();
    /**
     * �����ļ�Ŀ¼
     */
    private static String configDir;
    /**
     * ��־ϵͳ
     */
    Log log = new Log();
    /**
     * �õ�����
     * @return DataService
     */
    public static DataService getInstance()
    {
        return instanceObject;
    }
    /**
     * �������ö���
     * @param parm TConfigParm
     */
    public static void setConfigParm(TConfigParm parm)
    {
        configParm = parm;
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
     * ���������ļ�Ŀ¼
     * @param dir String
     */
    public static void setConfigDir(String dir)
    {
        configDir = dir;
    }
    /**
     * �õ������ļ�Ŀ¼
     * @return String
     */
    public static String getConfigDir()
    {
        return configDir;
    }
    /**
     * ��ʼ��
     */
    public void init() {
        instanceObject = this;
        configDir = getServletContext().getRealPath("/");
        if(!configDir.endsWith("/"))
            configDir += "/";
        configDir += "WEB-INF/config/";
        String dir = configDir + "system/";
        String systemGroup = "";
        configParm.setSystemGroup(systemGroup);
        configParm.setConfig(dir + "Service.x");
        configParm.setConfigClass(dir + "TClass.x",getServletContext().getRealPath("/"));
        log.reset();
        if(!Server.isInit())
        {
            Server.setServerPath(getServletContext().getRealPath("/"));
            Server.onInit();
        }
        Thread thread = new Thread(this);
        thread.start();
        System.out.println("Service��ʱ�������");
    }
    public void destroy()
    {
        PatchManager.getManager().setStart(false);
    }
    private static List list = new ArrayList();
    /**
     * ����ʱ���ʽ
     */
    public static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss.SS");
    public void run()
    {
        while(true)
        {
            try{
                Thread.sleep(1000 * 60);
            }catch(Exception e)
            {
            }
            look();
        }
    }
    private static synchronized void add(TParm parm)
    {
        list.add(parm);
    }
    private static synchronized void remove(TParm parm)
    {
        list.remove(parm);
    }
    private static synchronized void look()
    {
        Date d = new Date();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < list.size();i++)
        {
            TParm parm = (TParm)list.get(i);
            Date date = (Date)parm.getData("SYSTEM","LINK_DATE");
            if(d.getTime() - date.getTime() > 30000)
                sb.append((d.getTime() - date.getTime()) + "->" + parm + "\n\r");
        }
        if(sb.length() > 0)
        {
            System.out.println("=====================��ʱͳ��==Begin");
            //test
            System.out.println(dateFormat.format(d)+"->"+sb.toString());
            //
            System.out.println("=====================��ʱͳ��==End");
        }
    }
    /**
     * Get����
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        TParm parm = new TParm();
        ObjectInputStream inputStream =null;
        ObjectOutputStream outputStream=null;
        try {
            //����Input
            inputStream = new ObjectInputStream(new BufferedInputStream(request.getInputStream()));
            outputStream = new ObjectOutputStream(new BufferedOutputStream(response.getOutputStream()));
            //
            Object inObject = inputStream.readObject();
            Object outObject = null;
            if(inObject instanceof Map)
            {
                parm.setData((Map)inObject);
                //parm.setData("SYSTEM","IP",request.getRemoteAddr());
                parm.setData("SYSTEM","INPUT_STREAM",inputStream);
                parm.setData("SYSTEM","OUTPUT_STREAM",outputStream);
                parm.setData("SYSTEM","REQUEST",request);
                parm.setData("SYSTEM","RESPONSE",response);
                parm.setData("SYSTEM","REALPATH",getServletContext().getRealPath("/"));
                parm.setData("SYSTEM","LINK_DATE",new Date());
                add(parm);
                TParm result = work(parm);
                if(result != null)
                    outObject = result.getData();
                remove(parm);
            }
            if(outObject == null)
                outObject = new HashMap();
            //����Output
            outputStream.writeObject(outObject);
           
        }
        catch (Exception e) {
            remove(parm);
            e.printStackTrace();
        }finally{      	
        	if(inputStream!=null){
        	  inputStream.close();
        	}
        	if(outputStream!=null){
        		outputStream.close();	
        	}
             
        }
    }

    /**
     * Post����
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
    /**
     * ����������
     * @param parm TParm
     * @return TParm
     */
    public TParm work(TParm parm)
    {
        parm.setData("SYSTEM","EXE_STATE","work");
        int action = parm.getInt("SYSTEM","ACTION");
        switch (action) {
        case 0x1: //ִ��Action
            return executeAction(parm);
        case 0x2: //���¼���Action
            return resetAction(parm);
        case 0x3: //�������Ƿ�����
            return appIsRun(parm);
        case 0x4: //ִ��Module
            return executeModule(parm);
        case 0x5://ִ��Class
            return executeClass(parm);
        }
        TParm result = new TParm();
        result.setData("VALUE","OK");
        return result;
    }
    /**
     * ִ��Class
     * @param parm TParm
     * @return TParm
     */
    public TParm executeClass(TParm parm)
    {
        //Date start = new Date();
        String className = parm.getValue("SYSTEM","CLASS_NAME");
        String methodName = parm.getValue("SYSTEM","METHOD_NAME");
        Object parameters[] = (Object[])parm.getData("SYSTEM","PARAMETERS");
        //out(ip + " " + className + "." + methodName + "()");
        Object object = configParm.loadObject(className);
        if(object == null)
        {
            TParm result = new TParm();
            result.setErr(-1,"û���ҵ���" + className);
            err(" û���ҵ���" + className);
            return result;
        }
        Object valueObj = RunClass.runMethod(object, methodName, parameters);
        //Date end = new Date();
        //out(ip + " end " + className + "." + methodName + "() time=" + (end.getTime() - start.getTime()));
        TParm result = new TParm();
        result.setData("VALUE",valueObj);
        return result;
    }
    /**
     * ִ��Module
     * @param parm TParm
     * @return TParm
     */
    public TParm executeModule(TParm parm)
    {
        parm.setData("SYSTEM","EXE_STATE","executeModule");
        String ip = parm.getValue("SYSTEM","IP");
        String moduleID = parm.getValue("SYSTEM","MODULE_ID");

        TParm SQLParm = null;
        Map map = (Map)parm.getData("SYSTEM","MODULE_PARM");
        if(map != null)
        {
            SQLParm = new TParm();
            SQLParm.setData(map);
        }
        if(moduleID.length() == 0)
        {
            TParm result = new TParm();
            result.setErr(-1,"û���ҵ�����ģ����" + moduleID);
            err(ip +" û���ҵ�����ģ����" + moduleID);
            return result;
        }
        TModule module = TModule.getModule(moduleID);
        String methodName = parm.getValue("SYSTEM","MOULE_METHOD_NAME");
        String methodType = parm.getValue("SYSTEM","METHOD_TYPE");
        String dbName = parm.getValue("ACTION","DATABASE_NAME");

        if(SQLParm == null)
        {
            if (methodType == null || "update".equalsIgnoreCase(methodType))
                return module.update(methodName);
            else if("call".equalsIgnoreCase(methodType))
                return module.call(methodName);
            else
            {
                parm.setData("SYSTEM","EXE_STATE","query 1");
                TParm r = module.query(methodName);
                parm.setData("SYSTEM","EXE_STATE","query 1 " + r.toString());
                return r;
            }
        }
        else
        {
            if (methodType == null || "update".equalsIgnoreCase(methodType))
                return module.update(methodName, SQLParm);
            else if("call".equalsIgnoreCase(methodType))
                return module.call(methodName,SQLParm);
            else
            if(dbName!=null&&dbName.length()>0){
                 return module.query(methodName, SQLParm,dbName);
             }else{
                 return module.query(methodName, SQLParm);
             }

        }
    }
    /**
     * ִ��Action
     * @param parm TParm
     * @return TParm
     */
    public TParm executeAction(TParm parm)
    {
        //Date start = new Date();
        String ip = parm.getValue("SYSTEM","IP");
        String actionid = parm.getValue("SYSTEM","ACTION_ID");
        String className = parm.getValue("SYSTEM","ACTION_CLASS_NAME");
        String methodName = parm.getValue("SYSTEM","ACTION_METHOD_NAME");
        if(actionid.length() > 0)
        {
            className = configParm.getConfig().getString(configParm.getSystemGroup(),actionid + ".ClassName");
            String actionMethodName = configParm.getConfig().getString(configParm.getSystemGroup(),actionid + ".MethodName");
            if(actionMethodName.length() > 0)
                methodName = actionMethodName;
        }
        //out(ip + " " + actionid + " " + className + "." + methodName + "()");
        Object actionObject = configParm.loadObject(className);
        if(actionObject == null)
        {
            TParm result = new TParm();
            result.setErr(-1,"û���ҵ�������" + className);
            err(ip +" û���ҵ�������" + className);
            return result;
        }
        if(actionObject instanceof TAction)
        {
            TAction action = (TAction)actionObject;
            action.setIP(parm.getValue("SYSTEM","IP"));
            action.setInParm(parm);
            String configName = "";
            if(actionid.length() > 0)
                configName = configParm.getConfig().getString(configParm.getSystemGroup(),actionid + ".Config");
            if(configName.length() > 0)
                action.setConfigParm(configParm.newConfig(getConfigDir() + configName));
            else
                action.setConfigParm(configParm);
        }
        Method method = getMethods(methodName, actionObject);
        if(method == null)
        {
            TParm result = new TParm();
            result.setErr(-1,"û���ҵ������෽��" + className + "." + methodName);
            err(ip +" û���ҵ������෽��" + className + "." + methodName);
            return result;
        }
        try{
            Object obj = method.invoke(actionObject, new Object[] {parm});
            if(obj == null||!(obj instanceof TParm))
            {
                TParm result = new TParm();
                result.setErr(-1,"�ز�Ϊ��");
                err(ip + " " + className + "." + methodName + " �ز�Ϊ��");
                return result;
            }
            //Date end = new Date();
            //out(ip + " end " + className + "." + methodName + "() time=" + (end.getTime() - start.getTime()));
            return (TParm)obj;
        }catch(Exception e)
        {
            TParm result = new TParm();
            result.setErr(-1,e.getMessage());
            err(ip + " " + e.getMessage() + " " + className + "." + methodName);
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
    public Method getMethods(String methodName, Object actionObject)
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
    public TParm resetAction(TParm parm)
    {
        TParm result = new TParm();
        TModule.reset();
        configParm.reset();
        Server.getConfigParm().reset();
        result.setData("RETURN","DataService reset Action OK.");
        return result;
    }
    public TParm appIsRun(TParm parm)
    {
        return new TParm();
    }
    /**
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        log.err(text);
    }
}
