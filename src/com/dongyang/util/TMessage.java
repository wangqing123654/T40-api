package com.dongyang.util;

public class TMessage
{
    /**
     * 得到消息
     * @param name String
     * @return String
     */
    public static String get(String name)
    {
        Object obj = TSystem.getObject("MessageObject");
        if(obj == null)
            return name;
        Object result = RunClass.runMethod(obj,"getMessage",new Object[]{name});
        if(obj == null || result.toString().length() == 0)
            return name;
        return result.toString();
    }
    /**
     * 得到拼音
     * @param text String
     * @return String
     */
    public static String getPy(String text)
    {
        Object obj = TSystem.getObject("MessageObject");
        if(obj == null)
            return "";
        Object result = RunClass.runMethod(obj,"getPy",new Object[]{text});
        if(obj == null || result.toString().length() == 0)
            return "";
        return result.toString();
    }
    /**
     * 得到省
     * @param postNo3 String
     * @return String
     */
    public static String getState(String postNo3)
    {
        Object obj = TSystem.getObject("MessageObject");
        if(obj == null)
            return "";
        Object result = RunClass.runMethod(obj,"getState",new Object[]{postNo3});
        if(obj == null || result.toString().length() == 0)
            return "";
        return result.toString();
    }
    /**
     * 得到城市
     * @param postNo3 String
     * @return String
     */
    public static String getCity(String postNo3)
    {
        Object obj = TSystem.getObject("MessageObject");
        if(obj == null)
            return "";
        Object result = RunClass.runMethod(obj,"getCity",new Object[]{postNo3});
        if(obj == null || result.toString().length() == 0)
            return "";
        return result.toString();
    }
    /**
     * 运行方法
     * @param name String
     * @return Object
     */
    public static Object runFunction(String name)
    {
        return runFunction(name,new Object[]{});
    }
    /**
     * 运行方法
     * @param name String
     * @param parm Object[]
     * @return Object
     */
    public static Object runFunction(String name,Object[] parm)
    {
        Object obj = TSystem.getObject("MessageObject");
        if(obj == null)
            return null;
        return RunClass.runMethod(obj,name,parm);
    }
    /**
     * 得到类加载器
     * @return ClassLoader
     */
    public static ClassLoader getClassLoader()
    {
        Object obj = TSystem.getObject("MessageObject");
        if(obj == null)
            return null;
        return obj.getClass().getClassLoader();
    }
}
