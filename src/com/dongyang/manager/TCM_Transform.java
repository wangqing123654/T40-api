package com.dongyang.manager;

import com.dongyang.util.StringTool;
import java.util.Vector;
import java.sql.Timestamp;
/**
 *
 * <p>Title: 数据转换管理器</p>
 *
 * <p>Description: 负责数据之间转化问题，例如XML、HL7等数据交换问题</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 4.0
 */
public class TCM_Transform {
    /**
     * 转换为int
     * @param value Object
     * @return int
     */
    public static int getInt(Object value)
    {
        return getInt(value,null);
    }
    /**
     * 转换为int
     * @param value Object
     * @param type String 指定类型
     * @return int
     */
    public static int getInt(Object value,String type)
    {
        if(value == null)
            return 0;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (int)(Integer)value;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return ((Long)value).intValue();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return ((Double)value).intValue();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return ((Float)value).intValue();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getInt((String)value);
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return (int)(Character)value;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (int)(Short)value;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (int)(Byte)value;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (int)((Boolean)value?1:0);
        return 0;
    }
    /**
     * 转换为long
     * @param value Object
     * @return long
     */
    public static long getLong(Object value)
    {
        return getLong(value,null);
    }
    /**
     * 转换为long
     * @param value Object
     * @param type String 指定类型
     * @return long
     */
    public static long getLong(Object value,String type)
    {
        if(value == null)
            return 0;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (long)(Integer)value;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return ((Long)value).longValue();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return ((Double)value).longValue();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return ((Float)value).longValue();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getLong((String)value);
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return (long)(Character)value;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (long)(Short)value;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (long)(Byte)value;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (long)((Boolean)value?1:0);
        return 0;
    }
    /**
     * 转换为double
     * @param value Object
     * @return double
     */
    public static double getDouble(Object value)
    {
        return getDouble(value,null);
    }
    /**
     * 转换为double
     * @param value Object
     * @param type String 指定类型
     * @return double
     */
    public static double getDouble(Object value,String type)
    {
        if(value == null)
            return 0;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (double)(Integer)value;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return ((Long)value).doubleValue();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return ((Double)value).doubleValue();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return ((Float)value).doubleValue();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getDouble((String)value);
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return (double)(Character)value;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (double)(Short)value;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (double)(Byte)value;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (double)((Boolean)value?1:0);
        return 0;
    }
    /**
     * 转换为short
     * @param value Object
     * @return short
     */
    public static short getShort(Object value)
    {
        return getShort(value,null);
    }
    /**
     * 转换为short
     * @param value Object
     * @param type String 指定类型
     * @return short
     */
    public static short getShort(Object value,String type)
    {
        if(value == null)
            return 0;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (short)(int)(Integer)value;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return ((Long)value).shortValue();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return ((Double)value).shortValue();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return ((Float)value).shortValue();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getShort((String)value);
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return (short)(int)(Character)value;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (short)(Short)value;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (short)(Byte)value;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (short)((Boolean)value?1:0);
        return 0;
    }
    /**
     * 转换为byte
     * @param value Object
     * @return byte
     */
    public static byte getByte(Object value)
    {
        return getByte(value,null);
    }
    /**
     * 转换为byte
     * @param value Object
     * @param type String
     * @return byte
     */
    public static byte getByte(Object value,String type)
    {
        if(value == null)
            return 0;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (byte)(int)(Integer)value;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return ((Long)value).byteValue();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return ((Double)value).byteValue();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return ((Float)value).byteValue();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
        {
            try{
                return (byte)Integer.parseInt((String)value);
            }catch(Exception e)
            {
                e.printStackTrace();
                return 0;
            }
        }
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return (byte)(int)(Character)value;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (byte)(short)(Short)value;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (Byte)value;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (byte)((Boolean)value?1:0);
        return 0;
    }
    public static float getFloat(Object value)
    {
        return getFloat(value,null);
    }
    public static float getFloat(Object value,String type)
    {
        if(value == null)
            return 0;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (float)(int)(Integer)value;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return ((Long)value).floatValue();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return ((Double)value).floatValue();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return ((Float)value).floatValue();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
        {
            try{
                return (float)Double.parseDouble((String)value);
            }catch(Exception e)
            {
                e.printStackTrace();
                return 0;
            }
        }
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return (float)(int)(Character)value;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (float)(Short)value;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (float)(Byte)value;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (float)((Boolean)value?1:0);
        return 0;
    }
    public static char getChar(Object value)
    {
        return getChar(value,null);
    }
    public static char getChar(Object value,String type)
    {
        if(value == null)
            return 0;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (char)(int)(Integer)value;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return (char)((Long)value).byteValue();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return (char)((Double)value).byteValue();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return (char)((Float)value).byteValue();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getChar((String)value);
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return (Character)value;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (char)(short)(Short)value;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (char)(byte)(Byte)value;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (char)((Boolean)value?1:0);
        return 0;
    }
    public static boolean getBoolean(Object value)
    {
        return getBoolean(value,null);
    }
    public static boolean getBoolean(Object value,String type)
    {
        if(value == null)
            return false;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return (Integer)value == 1;
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return (Long)value == 1;
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return (Double)value == 1;
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return (Float)value == 1;
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getBoolean((String)value);
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return StringTool.getBoolean("" + value);;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return (Short)value == 1;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return (Byte)value == 1;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (Boolean)value;
        return false;
    }
    public static String getString(Object value)
    {
        return getString(value,null);
    }
    public static String getString(Object value,String type)
    {
        if(value == null)
            return "";
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return value.toString();
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return value.toString();
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return value.toString();
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return value.toString();
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return (String)value;
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return value.toString();
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return value.toString();
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return value.toString();
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return (boolean)(Boolean)value?"Y":"N";
        return value.toString();
    }
    public static Timestamp getTimestamp(Object value)
    {
        return getTimestamp(value,null);
    }
    public static Timestamp getTimestamp(Object value,String type)
    {
        if(value == null)
            return null;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.lang.Integer".equalsIgnoreCase(type)||
           "Integer".equalsIgnoreCase(type)||
           "int".equalsIgnoreCase(type))
            return new Timestamp((Integer)value);
        if("java.lang.Long".equalsIgnoreCase(type)||
           "Long".equalsIgnoreCase(type))
            return new Timestamp((Integer)value);
        if("java.lang.Double".equalsIgnoreCase(type)||
           "Double".equalsIgnoreCase(type))
            return new Timestamp((Integer)value);
        if("java.lang.Float".equalsIgnoreCase(type)||
           "Float".equalsIgnoreCase(type))
            return new Timestamp((Integer)value);
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getTimestamp((String)value,"yyyy/MM/dd");
        if("java.lang.Character".equalsIgnoreCase(type)||
           "Character".equalsIgnoreCase(type)||
           "char".equalsIgnoreCase(type))
            return null;
        if("java.lang.Short".equalsIgnoreCase(type)||
           "Short".equalsIgnoreCase(type))
            return null;
        if("java.lang.Byte".equalsIgnoreCase(type)||
           "Byte".equalsIgnoreCase(type))
            return null;
        if("java.lang.Boolean".equalsIgnoreCase(type)||
           "Boolean".equalsIgnoreCase(type))
            return null;
        if("java.sql.Timestamp".equalsIgnoreCase(type)||
           "Timestamp".equalsIgnoreCase(type))
            return (Timestamp)value;
        return null;
    }
    public static Vector getVector(Object value)
    {
        return getVector(value,null);
    }
    public static Vector getVector(Object value,String type)
    {
        if(value == null)
            return null;
        if(type == null || type.length() == 0)
            type = value.getClass().getName();
        if("java.util.Vector".equalsIgnoreCase(type)||
           "Vector".equalsIgnoreCase(type))
            return (Vector)value;
        if("java.lang.String".equalsIgnoreCase(type)||
           "String".equalsIgnoreCase(type))
            return StringTool.getVector((String)value);
        return null;
    }

    /**
     * 得到指定类型
     * @param type String 指定类型
     * @param value Object 传入参数
     * @return Object 返回参数
     */
    public static Object getObject(String type,Object value)
    {
        if("boolean".equals(type)||"java.lang.Boolean".equals(type))
            return getBoolean(value);
        if("byte".equals(type)||"java.lang.Byte".equals(type))
            return getByte(value);
        if("char".equals(type)||"java.lang.Character".equals(type))
            return getChar(value);
        if("double".equals(type)||"java.lang.Double".equals(type))
            return getDouble(value);
        if("float".equals(type)||"java.lang.Float".equals(type))
            return getFloat(value);
        if("int".equals(type)||"java.lang.Integer".equals(type))
            return getInt(value);
        if("long".equals(type)||"java.lang.Long".equals(type))
            return getLong(value);
        if("short".equals(type)||"java.lang.Short".equals(type))
            return getShort(value);
        if("java.lang.String".equals(type))
            return getString(value);
        if("java.util.Vector".equals(type))
            return getVector(value);
        return value;
    }
    /**
     * 是否是空
     * @param value Object
     * @return boolean
     */
    public static boolean isNull(Object value)
    {
        if(value == null)
            return true;
        String type = value.getClass().getName();
        if("boolean".equals(type)||"java.lang.Boolean".equals(type))
            return !(Boolean)value;
        if("double".equals(type)||"java.lang.Double".equals(type))
            return (Double)value == 0.0;
        if("float".equals(type)||"java.lang.Float".equals(type))
            return (Float)value == 0.0;
        if("int".equals(type)||"java.lang.Integer".equals(type))
            return (Integer)value == 0;
        if("long".equals(type)||"java.lang.Long".equals(type))
            return (Long)value == 0;
        if("short".equals(type)||"java.lang.Short".equals(type))
            return (Short)value == 0;
        if("java.lang.String".equals(type))
            return ((String)value).trim().length() == 0;
        return false;
    }
}
