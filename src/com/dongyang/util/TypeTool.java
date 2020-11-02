package com.dongyang.util;

import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * <p>Title: ����ת������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.21
 * @version 1.0
 */
public class TypeTool
{
    /**
     * �õ� boolean ����
     * @param obj Object
     * @return boolean
     */
    public static boolean getBoolean(Object obj)
    {
        if(obj == null)
            return false;
        if(obj instanceof Boolean)
            return (Boolean)obj;
        if(obj instanceof String)
            return StringTool.getBoolean((String) obj);
        if(obj instanceof Integer)
            return (Integer)obj != 0;
        if(obj instanceof Long)
            return (Long)obj != 0;
        if(obj instanceof Double)
            return (Double)obj != 0;
        return false;
    }
    /**
     * �õ� char ����
     * @param obj Object
     * @return char
     */
    public static char getChar(Object obj)
    {
        if(obj == null)
            return 0;
        if(obj instanceof Character)
            return (Character)obj;
        if(obj instanceof Integer)
            return (char)(int)(Integer)obj;
        if(obj instanceof Long)
            return (char)(int)((Long)obj).intValue();
        if(obj instanceof Double)
            return (char)(int)((Double)obj).intValue();
        if(obj instanceof String)
            return (char)(int)StringTool.getInt((String) obj);
        return 0;
    }
    /**
     * �õ� int ����
     * @param obj Object
     * @return int
     */
    public static int getInt(Object obj)
    {
        if(obj == null)
            return 0;
        if(obj instanceof Integer)
            return (Integer)obj;
        if(obj instanceof Long)
            return ((Long)obj).intValue();
        if(obj instanceof Double)
            return ((Double)obj).intValue();
        if(obj instanceof String)
            return StringTool.getInt((String) obj);
        return 0;
    }
    /**
     * �õ� long ����
     * @param obj Object
     * @return long
     */
    public static long getLong(Object obj)
    {
        if(obj == null)
            return 0;
        if(obj instanceof Integer)
            return (Integer)obj;
        if(obj instanceof Long)
            return ((Long)obj).longValue();
        if(obj instanceof Double)
            return ((Double)obj).longValue();
        if(obj instanceof String)
            return StringTool.getLong((String) obj);
        return 0;
    }
    /**
     * �õ� double ����
     * @param obj Object
     * @return double
     */
    public static double getDouble(Object obj)
    {
        if(obj == null)
            return 0;
        if(obj instanceof Integer)
            return (Integer)obj;
        if(obj instanceof Long)
            return ((Long)obj).doubleValue();
        if(obj instanceof Double)
            return ((Double)obj).doubleValue();
        if(obj instanceof String)
            return StringTool.getDouble((String) obj);
        return 0;
    }
    /**
     * �õ� float ����
     * @param obj Object
     * @return float
     */
    public static float getFloat(Object obj)
    {
        if(obj == null)
            return 0;
        if(obj instanceof Integer)
            return (Integer)obj;
        if(obj instanceof Long)
            return ((Long)obj).floatValue();
        if(obj instanceof Double)
            return ((Double)obj).floatValue();
        if(obj instanceof String)
            return StringTool.getFloat((String) obj);
        return 0;
    }
    /**
     * �õ� String ����
     * @param obj Object
     * @return String
     */
    public static String getString(Object obj)
    {
        if(obj == null)
            return "";
        if(obj instanceof String)
            return (String)obj;
        return "" + obj;
    }
    /**
     * �õ� Timestamp����
     * @param obj Object
     * @return Timestamp
     */
    public static Timestamp getTimestamp(Object obj)
    {
        if(obj == null || !(obj instanceof Timestamp))
            return null;
        return (Timestamp)obj;
    }
}
