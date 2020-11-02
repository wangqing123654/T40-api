package com.dongyang.config;

import com.dongyang.util.DynamicClassLoader;
import com.dongyang.util.Parameter;
import com.dongyang.util.StringTool;
import java.lang.reflect.Constructor;
import com.dongyang.util.Log;
import com.dongyang.data.TSocket;

public class TConfigParm {
    /**
     * 系统组别
     */
    private String systemGroup;
    /**
     * 配置对象
     */
    private TConfig config;
    /**
     * 类配置对象
     */
    private TConfig configClass;
    /**
     * 颜色配置对象
     */
    private TConfig configColor;
    /**
     * 类加载器
     */
    private DynamicClassLoader classLoader;
    /**
     * 连接对象
     */
    TSocket socket;
    /**
     * 初始化
     */
    public TConfigParm()
    {
        setSystemGroup("");
    }
    /**
     * 设置连接对象
     * @param socket TSocket
     */
    public void setSocket(TSocket socket) {
        this.socket = socket;
    }

    /**
     * 得到连接对象
     * @return TSocket
     */
    public TSocket getSocket() {
        return socket;
    }
    /**
     * 设置系统组别
     * @param systemGroup String
     */
    public void setSystemGroup(String systemGroup)
    {
        this.systemGroup = systemGroup;
    }
    /**
     * 得到系统组别
     * @return String
     */
    public String getSystemGroup()
    {
        return systemGroup;
    }
    /**
     * 设置配置对象
     * @param config TConfig
     */
    public void setConfig(TConfig config)
    {
        this.config = config;
    }
    /**
     * 设置配置对象
     * @param fileName String 文件名
     */
    public void setConfig(String fileName)
    {
        this.config = new TConfig(fileName,getSocket());
    }
    /**
     * 得到配置对象
     * @return TConfig
     */
    public TConfig getConfig()
    {
        return config;
    }
    /**
     * 设置Class配置对象
     * @param configClass TConfig
     */
    public void setConfigClass(TConfig configClass)
    {
        setConfigClass(configClass,"");
    }
    /**
     * 设置Class配置对象
     * @param configClass TConfig
     * @param realPath String 根目录
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
     * 设置Class配置对象
     * @param fileName String 文件名
     */
    public void setConfigClass(String fileName)
    {
        setConfigClass(fileName,"");
    }
    /**
     * 设置Class配置对象
     * @param fileName String 文件名
     * @param realPath String 根目录
     */
    public void setConfigClass(String fileName,String realPath)
    {
        setConfigClass(new TConfig(fileName,getSocket()),realPath);

    }
    /**
     * 得到Class配置对象
     * @return TConfig
     */
    public TConfig getConfigClass()
    {
        return configClass;
    }
    /**
     * 设置颜色配置对象
     * @param configColor TConfig
     */
    public void setConfigColor(TConfig configColor)
    {
        this.configColor = configColor;
    }
    /**
     * 设置颜色配置对象
     * @param fileName String
     */
    public void setConfigColor(String fileName)
    {
        this.configColor = new TConfig(fileName,getSocket());
    }
    /**
     * 得到颜色配置对象
     * @return TConfig
     */
    public TConfig getConfigColor()
    {
        return configColor;
    }
    /**
     * 设置类加载器
     * @param classLoader DynamicClassLoader
     */
    public void setClassLoader(DynamicClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }
    /**
     * 得到类加载器
     * @return DynamicClassLoader
     */
    public DynamicClassLoader getClassLoader()
    {
        return classLoader;
    }
    /**
     * 重新加载
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
     * 动态加载对象
     * @param type String
     * @return Object
     */
    public Object loadObject(String type)
    {
        Parameter parameter = StringTool.getParameter(type);
        //得到class的物理名
        String className = getConfigClass().getString(getSystemGroup(),
                parameter.name + ".Name", parameter.name);

        //System.out.println("===className==="+className);
        try
        {
            Class classObj = getClassLoader().loadClass(className, false);
            //空构造器
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
     * 得到新的配置对象
     * @param filename String 配置文件名
     * @return TConfigParm
     */
    public TConfigParm newConfig(String filename)
    {
        return newConfig(filename,true);
    }
    /**
     * 得到新的配置对象
     * @param filename String 配置文件名
     * @param flg boolean true 动态加载 false 静态加载
     * @return TConfigParm
     */
    public TConfigParm newConfig(String filename,boolean flg)
    {
        TConfigParm configParm = new TConfigParm();
        configParm.setSocket(getSocket());
        //初始化系统组别
        configParm.setSystemGroup(getSystemGroup());
        //装载主配置文件
        configParm.setConfig(filename);
        //装载颜色配置文件
        configParm.setConfigColor(getConfigColor());
        if(flg)
            //装载Class配置文件
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
