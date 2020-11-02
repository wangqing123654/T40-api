package com.dongyang.util;

import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import com.dongyang.manager.TCM_Transform;

public class RunClass {
    public static Class intClass;
    /**
     * ִ�з���
     * @param object Object ����
     * @param methodName String ����
     * @param parameters Object[] ����
     * @return Object
     */
    public static Object runMethod(Object object,String methodName,Object parameters[])
    {
        if(object == null)
            return null;
        Method accessMethod = findMethod(object,methodName,parameters);
        if(accessMethod == null)
            return null;
        return runMethod(object,accessMethod,parameters);
    }
    /**
     * ִ�з���
     * @param object Object ����
     * @param methodName String ����
     * @param parameters Object[] ����
     * @return Object
     */
    public static Object runDeclaredMethod(Object object,String methodName,Object parameters[])
    {
        if(object == null)
            return null;
        Method accessMethod = findDeclaredMethod(object,methodName,parameters);
        if(accessMethod == null)
            return null;
        return runMethod(object,accessMethod,parameters);
    }
    /**
     * ִ�з���(String������)
     * @param object Object ����
     * @param methodName String ����
     * @param parameters String[] ����
     * @return Object
     */
    public static Object runMethodS(Object object,String methodName,String[] parameters)
    {
        if(object == null)
            return null;
        if(methodName == null)
            return null;
        Method[] ms = findMethod(object,methodName,parameters.length);
        //û���ҵ�����
        if(ms.length == 0)
            return null;
        //�ҵ�һ������,�˶Բ�������
        if(ms.length == 1)
        {
            Object parm[] = new Object[parameters.length];
            Class<?>[] types = ms[0].getParameterTypes();
            for(int i = 0;i < parm.length;i++)
            {
                parm[i] = TCM_Transform.getObject(types[i].getName(),
                                                  parameters[i]);
            }
            return runMethod(object,ms[0],parm);
        }
        Object parm[] = new Object[parameters.length];
        for(int i = 0;i < parm.length;i++)
            parm[i] = StringTool.getObject(parameters[i]);
        return runMethod(object,methodName,parm);
    }
    /**
     * ִ�з���(String������ ר�÷���)
     * @param object Object ����
     * @param methodName String ����
     * @param parameters String[] ����
     * @return Object
     */
    public static Object runDeclaredMethodS(Object object,String methodName,String ... parameters)
    {
        if(object == null)
            return null;
        if(methodName == null)
            return null;
        Method[] ms = findDeclaredMethod(object,methodName,parameters.length);
        //û���ҵ�����
        if(ms.length == 0)
            return null;
        //�ҵ�һ������,�˶Բ�������
        if(ms.length == 1)
        {
            Object parm[] = new Object[parameters.length];
            Class<?>[] types = ms[0].getParameterTypes();
            for(int i = 0;i < parm.length;i++)
                parm[i] = TCM_Transform.getObject(types[i].getName(),parameters[i]);
            return runMethod(object,ms[0],parm);
        }
        Object parm[] = new Object[parameters.length];
        for(int i = 0;i < parm.length;i++)
            parm[i] = StringTool.getObject(parameters[i]);
        return runMethod(object,methodName,parm);
    }
    /**
     * ִ�з���(String������ ���߼��)
     * @param object Object ����
     * @param methodName String ����
     * @param parametersString String ����
     * @return Object
     */
    public static Object runMethodString(Object object,String methodName,String parametersString)
    {
        if(parametersString == null)
            parametersString = "";
        if(parametersString.length() == 0)
        {
            Object result = runMethodS(object, methodName,new String[]{});
            if (result != null)
                return result;
            return runMethodS(object, methodName,new String[]{""});
        }
        String parm[] = StringTool.parseLine(parametersString,"|");
        return runMethodS(object, methodName,parm);
    }
    /**
     * ִ�з���(String������ ���߼�� ר�÷���)
     * @param object Object ����
     * @param methodName String ����
     * @param parametersString String ����
     * @return Object
     */
    public static Object runDeclaredMethodString(Object object,String methodName,String parametersString)
    {
        if(parametersString == null)
            parametersString = "";
        if(parametersString.length() == 0)
        {
            Object result = runDeclaredMethodS(object, methodName);
            if (result != null)
                return result;
            return runMethodS(object, methodName,new String[]{""});
        }
        String parm[] = StringTool.parseLine(parametersString,"|");
        return runMethodS(object, methodName,parm);
    }
    /**
     * ִ�з���
     * @param object Object ����
     * @param accessMethod Method ����
     * @param parameters Object[] ����
     * @return Object
     */
    public static Object runMethod(Object object,Method accessMethod,Object ... parameters)
    {
        if(accessMethod == null)
            return null;
        try{
            Object value = accessMethod.invoke(object,parameters);
            if("void".equals(accessMethod.getReturnType().getName()))
                return "void";
            return value;
        }catch(Exception e)
        {
            String err = "err: " + object.getClass().getName() + "." + accessMethod.getName() + "(" + parameters.length + ")" + e.getMessage();
            err(err);
            e.printStackTrace();
            return err;
        }
    }
    /**
     * ���ҷ���
     * @param object Object ����
     * @param methodName String ����
     * @param parameters Object[] ����
     * @return Method
     */
    public static Method findMethod(Object object,String methodName,Object parameters[])
    {
        if(object == null)
            return null;
        if(methodName == null)
            return null;
        Method[] ms = object.getClass().getMethods();
        GOTO1:
        for(int i = 0;i < ms.length;i++)
        {
            if(!ms[i].getName().equalsIgnoreCase(methodName))
                continue;
            Class<?>[] types = ms[i].getParameterTypes();
            if(parameters.length != types.length)
                continue;
            for(int j = 0;j < types.length;j++)
                if (!StringTool.equalsType(types[j],parameters[j]))
                    continue GOTO1;
            return ms[i];
        }
        return null;
    }
    /**
     * ����ͬ��ͬ�θ����ķ���
     * @param object Object ����
     * @param methodName String ������
     * @param parameterLength int ��������
     * @return Method[]
     */
    public static Method[] findMethod(Object object,String methodName,int parameterLength)
    {
        if(object == null)
            return null;
        if(methodName == null)
            return null;
        Method[] ms = object.getClass().getMethods();
        ArrayList list = new ArrayList();
        for(int i = 0;i < ms.length;i++)
        {
            if(!ms[i].getName().equalsIgnoreCase(methodName))
                continue;
            Class<?>[] types = ms[i].getParameterTypes();
            if(parameterLength != types.length)
                continue;
            list.add(ms[i]);
        }
        return (Method[])list.toArray(new Method[]{});
    }
    /**
     * ����ͬ��ͬ�ķ���
     * @param object Object ����
     * @param methodName String ������
     * @return Method[]
     */
    public static Method[] findMethods(Object object,String methodName)
    {
        if(object == null)
            return null;
        if(methodName == null)
            return null;
        Method[] ms = object.getClass().getMethods();
        ArrayList list = new ArrayList();
        for(int i = 0;i < ms.length;i++)
        {
            if(!ms[i].getName().equalsIgnoreCase(methodName))
                continue;
            Class<?>[] types = ms[i].getParameterTypes();
            list.add(ms[i]);
        }
        return (Method[])list.toArray(new Method[]{});
    }
    /**
     * ���ҷ���(ר�÷���)
     * @param object Object ����
     * @param methodName String ������
     * @param parameters Object[] ����
     * @return Method
     */
    public static Method findDeclaredMethod(Object object,String methodName,Object parameters[])
    {
        if(object == null)
            return null;
        if(methodName == null)
            return null;
        Class classObject = object.getClass();
        while(classObject != null)
        {
            Method[] ms = classObject.getDeclaredMethods();
            GOTO1:
                    for (int i = 0; i < ms.length; i++)
            {
                if (!ms[i].getName().equalsIgnoreCase(methodName))
                    continue;
                Class<?> [] types = ms[i].getParameterTypes();
                if (parameters.length != types.length)
                    continue;
                for (int j = 0; j < types.length; j++)
                    if (!StringTool.equalsType(types[j],
                            parameters[j]))
                        continue GOTO1;
                ms[i].setAccessible(true);
                return ms[i];
            }
            classObject = classObject.getSuperclass();
        }
        return null;
    }
    /**
     * ����ͬ��ͬ�θ����ķ���(ר�÷���)
     * @param object Object ����
     * @param methodName String ������
     * @param parameterLength int ��������
     * @return Method[]
     */
    public static Method[] findDeclaredMethod(Object object,String methodName,int parameterLength)
    {
        if(object == null)
            return null;
        if(methodName == null)
            return null;
        Method[] ms = object.getClass().getDeclaredMethods();
        ArrayList list = new ArrayList();
        for(int i = 0;i < ms.length;i++)
        {
            if(!ms[i].getName().equalsIgnoreCase(methodName))
                continue;
            Class<?>[] types = ms[i].getParameterTypes();
            if(parameterLength != types.length)
                continue;
            ms[i].setAccessible(true);
            list.add(ms[i]);
        }
        return (Method[])list.toArray(new Method[]{});
    }
    /**
     * ���Ҷ�������
     * @param object Object
     * @param fieldName String
     * @return Field
     */
    public static Field findField(Object object,String fieldName)
    {
        if(object == null)
            return null;
        if(fieldName == null)
            return null;
        Field field = null;
        try{
            field = object.getClass().getField(fieldName);
        }catch(Exception e)
        {
        }
        try{
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        }catch(Exception e)
        {
        }
        return field;
    }
    /**
     * �ͷ�����
     * @param object Object
     */
    public static void release(Object object)
    {
        if(object == null)
            return;
        Field names[] = object.getClass().getFields();
        for(int i = 0;i < names.length;i++)
        {
            try{
                names[i].set(object, null);
            }catch(Exception e)
            {
            }
        }
        names = object.getClass().getDeclaredFields();
        for(int i = 0;i < names.length;i++)
        {
            try{
                names[i].setAccessible(true);
                names[i].set(object, null);
            }catch(Exception e)
            {
            }
        }
    }
    /**
     * ���ö�������
     * @param object Object
     * @param fieldName String
     * @param value Object
     * @return Object
     */
    public static Object setField(Object object,String fieldName,Object value)
    {
        Field field = findField(object,fieldName);
        if(field == null)
            return null;
        try{
            field.set(object, value);
            return "void";
        }catch(Exception e)
        {
            err("err: " + object.getClass().getName() + "." + fieldName + " " + e.getMessage());
            return null;
        }
    }
    /**
     * �õ���������
     * @param object Object
     * @param fieldName String
     * @return Object
     */
    public static Object getField(Object object,String fieldName)
    {
        Field field = findField(object,fieldName);
        if(field == null)
            return null;
        try{
            return field.get(object);
        }catch(Exception e)
        {
            err("err: " + object.getClass().getName() + "." + fieldName + " " + e.getMessage());
            return null;
        }
    }
    /**
     * ���ö�������(����set����)
     * @param object Object
     * @param fieldName String
     * @param value Object
     * @return Object
     */
    public static Object setFieldM(Object object,String fieldName,Object value)
    {
        Field field = findField(object,fieldName);
        if(findMethods(object,"set" + fieldName).length > 0)
            return runMethod(object,"set" + fieldName,new Object[]{value});
        if(field == null)
            return null;
        try{
            field.set(object,value);
            return "void";
        }catch(Exception e)
        {
            err("err: " + object.getClass().getName() + "." + fieldName + " " + e.getMessage());
            return null;
        }
    }
    /**
     * �õ���������(����is ��get ����)
     * @param object Object
     * @param fieldName String
     * @return Object
     */
    public static Object getFieldM(Object object,String fieldName)
    {
        Field field = findField(object,fieldName);
        if(field != null && "boolean".equals(field.getType().getName())&&
           findMethods(object,"is" + fieldName).length > 0)
            return runMethod(object,"is" + fieldName,new Object[]{});
        if(findMethods(object,"get" + fieldName).length > 0)
            return runMethod(object,"get" + fieldName,new Object[]{});
        if(field == null)
            return null;
        try{
            return field.get(object);
        }catch(Exception e)
        {
            err("err: " + object.getClass().getName() + "." + fieldName + " " + e.getMessage());
            return null;
        }
    }
    /**
     * ���ö�������(����get ���� ����String ��)
     * @param object Object
     * @param fieldName String
     * @param value String
     * @return Object
     */
    public static Object setFieldMString(Object object,String fieldName,String value)
    {
        if(findMethods(object,"set" + fieldName).length > 0)
            return runMethodS(object,"set" + fieldName,new String[]{value});
        Field field = findField(object,fieldName);
        if(field == null)
            return null;
        Object parm = TCM_Transform.getObject(field.getType().getName(),value);
        try{
            field.set(object,parm);
            return "void";
        }catch(Exception e)
        {
            err("err: " + object.getClass().getName() + "." + fieldName + " " + e.getMessage());
            return null;
        }
    }
    public static Object invokeMethod(Object object,String methodName,Object[] parameters)
    {
        if(object == null)
            return null;

        Method accessMethod = findMethod(object,methodName,parameters);
        if(accessMethod != null)
        {
            if(!accessMethod.isAccessible())
                accessMethod.setAccessible(true);
            return runMethod(object, accessMethod, parameters);
        }
        accessMethod = findDeclaredMethod(object,methodName,parameters);
        if(accessMethod != null)
            return runMethod(object,accessMethod,parameters);

        return null;
    }
    /**
     * ִ�з���(T���ʹ��)
     * @param object Object
     * @param value String[]
     * @param valueObject Object
     * @return Object
     */
    public static Object invokeMethodT(Object object,String value[],Object valueObject)
    {
        if(valueObject instanceof Object[])
            if(value[0].startsWith("~"))
                return runDeclaredMethod(object,value[0].substring(1,value[0].length()),(Object[])valueObject);
            else
                return runMethod(object,value[0],(Object[])valueObject);
        if(value[0].startsWith("~"))
            return runDeclaredMethodString(object,value[0].substring(1,value[0].length()),value[1]);
        else
            return runMethodString(object,value[0],value[1]);
    }
    /**
     * ִ������(T���ʹ��)
     * @param object Object
     * @param value String[]
     * @param valueObject Object
     * @return Object
     */
    public static Object invokeFieldT(Object object,String value[],Object valueObject)
    {
        if(valueObject != null && valueObject instanceof Object[] &&
                ((Object[])valueObject).length == 1){
            return RunClass.setFieldM(object, value[0], ((Object[]) valueObject)[0]);
        }
        return RunClass.setFieldMString(object,value[0],value[1]);
    }
    /**
     * ��־���
     * @param text String ��־����
     */
    public static void out(String text) {
        Log.getInstance().out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public static void out(String text,boolean debug)
    {
        Log.getInstance().out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public static void err(String text) {
        Log.getInstance().err(text);
    }
    public static void main(String args[])
    {
        RunClass runClass = new RunClass();
        /*int x = 12;
        try{
            Field field = runClass.getClass().getDeclaredField("x");
            System.out.println("field=" + field);
            RunClass.intClass = field.getType();

        }catch(Exception e)
        {

        }*/
        //System.out.println(RunClass.runMethod(new a(),"a",12,13));
        //System.out.println(RunClass.runDeclaredMethod(new a(),"AAA",12));
        //System.out.println(RunClass.runMethodString(new a(),"a","''"));
        a a = new a();
        RunClass.setFieldMString(a,"x","T");
        System.out.println(RunClass.getFieldM(a,"x"));
        System.out.println(a);
        /*a a = new a();
        Method[] m= a.getClass().getMethods();
        for(int i = 0;i < m.length;i++)
            System.out.println("m[" +  i + "]" + m[i]);
        Method[] m2= a.getClass().getDeclaredMethods();
        for(int i = 0;i < m2.length;i++)
            System.out.println("m[" +  i + "]" + m2[i]);
        Method m1= a.getClass().getEnclosingMethod();
        System.out.println("m1=" + m1);*/

    }
}
class a
{
    private boolean x;
    public void setX(boolean x)
    {
        System.out.println("setX=" + x);
        this.x = x;
    }
    public boolean isX()
    {
        System.out.println("isX=" + x);
        return x;
    }
    public String toString()
    {
        return "x=" + x;
    }
    public void a(int x,int y)
    {
        System.out.println("aaaaaaaaaaaa " + x + " " + y);
    }
}
