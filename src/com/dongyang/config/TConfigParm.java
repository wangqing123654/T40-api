package com.dongyang.config;

import com.dongyang.util.DynamicClassLoader;
import com.dongyang.util.Parameter;
import com.dongyang.util.StringTool;
import java.lang.reflect.Constructor;
import com.dongyang.util.Log;
import com.dongyang.data.TSocket;

public class TConfigParm {
    /**
     * ϵͳ���
     */
    private String systemGroup;
    /**
     * ���ö���
     */
    private TConfig config;
    /**
     * �����ö���
     */
    private TConfig configClass;
    /**
     * ��ɫ���ö���
     */
    private TConfig configColor;
    /**
     * �������
     */
    private DynamicClassLoader classLoader;
    /**
     * ���Ӷ���
     */
    TSocket socket;
    /**
     * ��ʼ��
     */
    public TConfigParm()
    {
        setSystemGroup("");
    }
    /**
     * �������Ӷ���
     * @param socket TSocket
     */
    public void setSocket(TSocket socket) {
        this.socket = socket;
    }

    /**
     * �õ����Ӷ���
     * @return TSocket
     */
    public TSocket getSocket() {
        return socket;
    }
    /**
     * ����ϵͳ���
     * @param systemGroup String
     */
    public void setSystemGroup(String systemGroup)
    {
        this.systemGroup = systemGroup;
    }
    /**
     * �õ�ϵͳ���
     * @return String
     */
    public String getSystemGroup()
    {
        return systemGroup;
    }
    /**
     * �������ö���
     * @param config TConfig
     */
    public void setConfig(TConfig config)
    {
        this.config = config;
    }
    /**
     * �������ö���
     * @param fileName String �ļ���
     */
    public void setConfig(String fileName)
    {
        this.config = new TConfig(fileName,getSocket());
    }
    /**
     * �õ����ö���
     * @return TConfig
     */
    public TConfig getConfig()
    {
        return config;
    }
    /**
     * ����Class���ö���
     * @param configClass TConfig
     */
    public void setConfigClass(TConfig configClass)
    {
        setConfigClass(configClass,"");
    }
    /**
     * ����Class���ö���
     * @param configClass TConfig
     * @param realPath String ��Ŀ¼
     */
    public void setConfigClass(TConfig configClass,String realPath)
    {
        this.configClass = configClass;
        Log.onInit(this);
        classLoader = new DynamicClassLoader();
        classLoader.setSocket(getSocket());
        classLoader.setRealPath(realPath);
        classLoader.setConfigParm(this);
    }
    /**
     * ����Class���ö���
     * @param fileName String �ļ���
     */
    public void setConfigClass(String fileName)
    {
        setConfigClass(fileName,"");
    }
    /**
     * ����Class���ö���
     * @param fileName String �ļ���
     * @param realPath String ��Ŀ¼
     */
    public void setConfigClass(String fileName,String realPath)
    {
        setConfigClass(new TConfig(fileName,getSocket()),realPath);

    }
    /**
     * �õ�Class���ö���
     * @return TConfig
     */
    public TConfig getConfigClass()
    {
        return configClass;
    }
    /**
     * ������ɫ���ö���
     * @param configColor TConfig
     */
    public void setConfigColor(TConfig configColor)
    {
        this.configColor = configColor;
    }
    /**
     * ������ɫ���ö���
     * @param fileName String
     */
    public void setConfigColor(String fileName)
    {
        this.configColor = new TConfig(fileName,getSocket());
    }
    /**
     * �õ���ɫ���ö���
     * @return TConfig
     */
    public TConfig getConfigColor()
    {
        return configColor;
    }
    /**
     * �����������
     * @param classLoader DynamicClassLoader
     */
    public void setClassLoader(DynamicClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }
    /**
     * �õ��������
     * @return DynamicClassLoader
     */
    public DynamicClassLoader getClassLoader()
    {
        return classLoader;
    }
    /**
     * ���¼���
     */
    public void reset()
    {
        if(getConfigColor() != null)
            getConfigColor().reset();
        if(getConfigClass() != null)
            getConfigClass().reset();
        if(getConfig() != null)
            getConfig().reset();
        Log.onInit(this);
        String path = classLoader.getRealPath();
        classLoader = new DynamicClassLoader();
        classLoader.setRealPath(path);
        classLoader.setConfigParm(this);
        classLoader.setSocket(getSocket());
    }
    /**
     * ��̬���ض���
     * @param type String
     * @return Object
     */
    public Object loadObject(String type)
    {
        Parameter parameter = StringTool.getParameter(type);
        //�õ�class��������
        String className = getConfigClass().getString(getSystemGroup(),
                parameter.name + ".Name", parameter.name);

        //System.out.println("===className==="+className);
        try
        {
            Class classObj = getClassLoader().loadClass(className, false);
            //�չ�����
            if (parameter.values.length == 0)
                return classObj.newInstance();
            Constructor constructor[] = classObj.getConstructors();
            for (int i = 0; i < constructor.length; i++)
            {
                Class parameterTypes[] = constructor[i].getParameterTypes();
                if (parameterTypes.length != parameter.values.length)
                    continue;
                boolean isequals = true;
                for (int j = 0; j < parameterTypes.length; j++)
                    if (!StringTool.equalsObjectType(parameterTypes[j].getName(),
                            parameter.values[j].getClass().getName()))
                    {
                        isequals = false;
                        break;
                    }
                if (isequals)
                    return constructor[i].newInstance(parameter.values);
            }
        } catch (Exception e)
        {
            //System.out.println("TConfigParm loadObject ERR:" + e.getMessage());
            return null;
        }
        return null;
    }
    /**
     * �õ��µ����ö���
     * @param filename String �����ļ���
     * @return TConfigParm
     */
    public TConfigParm newConfig(String filename)
    {
        return newConfig(filename,true);
    }
    /**
     * �õ��µ����ö���
     * @param filename String �����ļ���
     * @param flg boolean true ��̬���� false ��̬����
     * @return TConfigParm
     */
    public TConfigParm newConfig(String filename,boolean flg)
    {
        TConfigParm configParm = new TConfigParm();
        configParm.setSocket(getSocket());
        //��ʼ��ϵͳ���
        configParm.setSystemGroup(getSystemGroup());
        //װ���������ļ�
        configParm.setConfig(filename);
        //װ����ɫ�����ļ�
        configParm.setConfigColor(getConfigColor());
        if(flg)
            //װ��Class�����ļ�
            configParm.setConfigClass(getConfigClass());
        else
        {
            configParm.configClass = configClass;
            configParm.classLoader = classLoader;
        }
        return configParm;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TConfigParm[systemGroup=");
        sb.append(systemGroup);
        sb.append("]");
        return sb.toString();
    }
}
