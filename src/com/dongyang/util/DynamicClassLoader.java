package com.dongyang.util;

import java.io.File;
import java.io.IOException;
import com.dongyang.config.TConfigParm;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TIOM_AppServer;

public class DynamicClassLoader extends ClassLoader {
    public static boolean DYNAMIC = false;
    public static final String INI_CLASS_EXCLUDED = "excluded";
    public static final String INI_CLASS_STATIC_EXCLUDED = "staticexcluded";
    public static final String INI_CLASS_CLASS_PATH = "classPath";
    public static final String INI_CLASS_PATH = ".Path";
    //路径
    private String path;
    private String realPath;
    private TConfigParm configParm;
    /**
     * 系统类
     */
    private String excluded[];
    /**
     * 静态加载类
     */
    private String staticExcluded[];
    private String classPath[];
    /**
     * 日志系统
     */
    Log log;
    /**
     * 连接对象
     */
    TSocket socket;
    /**
     * 构造器
     */
    public DynamicClassLoader() {
        log = new Log();
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
     * 设置INI对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
        //系统类
        excluded = configParm.getConfigClass().getStringList(configParm.getSystemGroup(),
                INI_CLASS_EXCLUDED);
        for (int i = 0; i < excluded.length; i++)
            if (excluded[i].endsWith("*"))
                excluded[i] = excluded[i].substring(0, excluded[i].length() - 1);
        //静态加载类
        staticExcluded = configParm.getConfigClass().getStringList(configParm.getSystemGroup(),
                INI_CLASS_STATIC_EXCLUDED);
        for (int i = 0; i < staticExcluded.length; i++)
            if (staticExcluded[i].endsWith("*"))
                staticExcluded[i] = staticExcluded[i].substring(0, staticExcluded[i].length() - 1);

        classPath = configParm.getConfigClass().getStringList(configParm.getSystemGroup(),
                INI_CLASS_CLASS_PATH);
        if(getRealPath() != null && getRealPath().length() > 0)
            for (int i = 0; i < classPath.length; i++)
                if (classPath[i].toUpperCase().startsWith("%ROOT%"))
                    classPath[i] = getRealPath() +
                                   classPath[i].substring(6, classPath[i].length());
    }

    /**
     * 得到加载路径
     * @return String
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置加载路径
     * @param path String
     */
    public void setPath(String path) {
        this.path = path;
    }

    public void setRealPath(String realPath) {
        if (realPath != null && realPath.endsWith("/"))
            realPath = realPath.substring(0, realPath.length() - 1);
        this.realPath = realPath;
    }

    public String getRealPath() {
        return realPath;
    }

    /**
     * 判断不是动态加载的类
     * @param name String
     * @return boolean
     */
    public boolean isExcluded(String name) {
        for (int i = 0; i < excluded.length; i++)
            if (name.startsWith(excluded[i]))
                return true;

        return false;
    }
    /**
     * 判断不是静态加载的类
     * @param name String
     * @return boolean
     */
    public boolean isEtaticExcluded(String name)
    {
        for (int i = 0; i < staticExcluded.length; i++)
            if (name.startsWith(staticExcluded[i]))
                return true;

        return false;
    }
    public synchronized Class loadClass(String className, boolean resolve) throws
            ClassNotFoundException {
        //System.out.println("======className========="+className);
        //if(DYNAMIC)
        //    return getClass().getClassLoader().loadClass(className);
        if (isExcluded(className)) {
            return getClass().getClassLoader().loadClass(className);
        }
        if (isEtaticExcluded(className))
        {
            ClassLoader classLoader = TMessage.getClassLoader();
            if(classLoader != null && classLoader != this)
                return classLoader.loadClass(className);
        }
        Class c = findLoadedClass(className);
        if (c != null) {
            return c;
        }
        c = findClass(className);
        return c;
    }
    /**
     * 动态加载类
     * @param className String
     * @param data byte[]
     * @return Class
     * @throws ClassNotFoundException
     */
    public synchronized Class loadClassDynamic(String className,byte data[]) throws
            ClassNotFoundException {
        return defineClass(className, data, 0, data.length);
    }

    /**
     * 查找Class文件
     * @param name String 名称
     * @return Class
     * @throws ClassNotFoundException
     */
    public Class findClass(String name) throws ClassNotFoundException {
        //得到class的路径
        String classPath = configParm.getConfigClass().getString(configParm.getSystemGroup(),
                name + INI_CLASS_PATH);
        byte[] data = null;
        if (classPath.length() > 0) {
            if(getSocket() != null)
            {
                data = TIOM_AppServer.readFile(getSocket(), classPath);
            }
            else
            {
                String fileName = getPath() + classPath;
                try {
                    data = FileTool.getByte(fileName);
                } catch (IOException e) {
                    err("IOException:" + e.getMessage());
                }
            }
        } else {
            data = loadClassData(name);
        }
        if (data == null || data.length == 0)
            return super.findClass(name);
        if(data[0] != -54)
        {
            for(int i = 0;i < 100;i++)
                data[i] = (byte)(255 - data[i]);
        }
        return defineClass(name, data, 0, data.length);
    }

    /**
     * 读取文件数据
     * @param className String 文件名
     * @return byte[] 数据数组
     * @throws ClassNotFoundException
     */
    private byte[] loadClassData(String className) throws
            ClassNotFoundException {
        byte data[] = null;
        for (int i = 0; i < classPath.length; i++) {
            String path = classPath[i];
            String fileName = className.replace('.', '/') + ".class";
            if (isJar(path))
            {
                if(getSocket() != null)
                    data = TIOM_AppServer.readJarFile(getSocket(),path, fileName);
                else
                    data = FileTool.getJarByte(path, fileName);
            }
            else
                data = loadFileData(path, fileName);
            if (data != null)
                return data;
        }

        throw new ClassNotFoundException(className);
    }

    boolean isJar(String pathEntry) {
        return pathEntry.endsWith(".jar") || pathEntry.endsWith(".zip");
    }

    /**
     * 读取文件数据
     * @param path String 路径
     * @param fileName String 文件名
     * @return byte[]
     */
    private byte[] loadFileData(String path, String fileName) {
        if(getSocket() != null)
            return TIOM_AppServer.readFile(getSocket(), path, fileName);
        File file = new File(path, fileName);
        if (file.exists())
            try{
                return FileTool.getByte(file);
            }catch(Exception e)
            {
                err(e.getMessage());
            }
        return null;
    }

    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        log.err(text);
    }
}
