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
    //·��
    private String path;
    private String realPath;
    private TConfigParm configParm;
    /**
     * ϵͳ��
     */
    private String excluded[];
    /**
     * ��̬������
     */
    private String staticExcluded[];
    private String classPath[];
    /**
     * ��־ϵͳ
     */
    Log log;
    /**
     * ���Ӷ���
     */
    TSocket socket;
    /**
     * ������
     */
    public DynamicClassLoader() {
        log = new Log();
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
     * ����INI����
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
        //ϵͳ��
        excluded = configParm.getConfigClass().getStringList(configParm.getSystemGroup(),
                INI_CLASS_EXCLUDED);
        for (int i = 0; i < excluded.length; i++)
            if (excluded[i].endsWith("*"))
                excluded[i] = excluded[i].substring(0, excluded[i].length() - 1);
        //��̬������
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
     * �õ�����·��
     * @return String
     */
    public String getPath() {
        return path;
    }

    /**
     * ���ü���·��
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
     * �жϲ��Ƕ�̬���ص���
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
     * �жϲ��Ǿ�̬���ص���
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
     * ��̬������
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
     * ����Class�ļ�
     * @param name String ����
     * @return Class
     * @throws ClassNotFoundException
     */
    public Class findClass(String name) throws ClassNotFoundException {
        //�õ�class��·��
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
     * ��ȡ�ļ�����
     * @param className String �ļ���
     * @return byte[] ��������
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
     * ��ȡ�ļ�����
     * @param path String ·��
     * @param fileName String �ļ���
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
