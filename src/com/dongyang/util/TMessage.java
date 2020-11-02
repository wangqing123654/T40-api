package com.dongyang.util;

public class TMessage
{
    /**
     * �õ���Ϣ
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
     * �õ�ƴ��
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
     * �õ�ʡ
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
     * �õ�����
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
     * ���з���
     * @param name String
     * @return Object
     */
    public static Object runFunction(String name)
    {
        return runFunction(name,new Object[]{});
    }
    /**
     * ���з���
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
     * �õ��������
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
