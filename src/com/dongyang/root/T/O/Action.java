package com.dongyang.root.T.O;

import com.dongyang.manager.TCM_Transform;
import com.dongyang.util.RunClass;

public class Action
{
    /**
     * 得到对象类型
     * @param object Object
     * @return int
     */
    public static int getNumberType(Object object)
    {
        String type = object.getClass().getName();
        if("java.lang.Byte".equals(type) || "byte".equals(type))
            return 1;
        if ("java.lang.Character".equals(type) || "char".equals(type))
            return 1;
        if ("java.lang.Short".equals(type) || "short".equals(type))
            return 2;
        if ("java.lang.Integer".equals(type) || "int".equals(type))
            return 3;
        if ("java.lang.Long".equals(type) || "long".equals(type))
            return 4;
        if ("java.lang.Float".equals(type) || "float".equals(type))
            return 5;
        if ("java.lang.Double".equals(type) || "double".equals(type))
            return 6;
        return -1;
    }
    /**
     * 得到最大的数
     * @param x1 int
     * @param x2 int
     * @return int
     */
    public static int getMax(int x1,int x2)
    {
        if(x1 > x2)
            return x1;
        return x2;
    }
    /**
     * 转换类型
     * @param type int
     * @param value Object
     * @return object
     */
    public static Object getTypeValue(int type,Object value)
    {
        if(type < 3)
            type = 3;
        switch(type)
        {
        case 3:
            return TCM_Transform.getInt(value);
        case 4:
            return TCM_Transform.getLong(value);
        case 5:
            return TCM_Transform.getFloat(value);
        case 6:
            return TCM_Transform.getDouble(value);
        }
        return value;
    }
    /**
     * 加法
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object addition(Object o1,Object o2)
    {
        if(o1 != null && o1 instanceof String)
            return (String)o1 + o2;
        if(o2 != null && o2 instanceof String)
            return o1 + (String)o2;
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return getTypeValue(getMax(t1,t2),TCM_Transform.getDouble(o1) + TCM_Transform.getDouble(o2));
        return RunClass.runMethod(o1,"addition",new Object[]{o2});
    }
    /**
     * 减法
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object subtration(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return getTypeValue(getMax(t1,t2),TCM_Transform.getDouble(o1) - TCM_Transform.getDouble(o2));
        return RunClass.runMethod(o1,"subtration",new Object[]{o2});
    }
    /**
     * 减法
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object multiplication(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return getTypeValue(getMax(t1,t2),TCM_Transform.getDouble(o1) * TCM_Transform.getDouble(o2));
        return RunClass.runMethod(o1,"multiplication",new Object[]{o2});
    }
    /**
     * 除法
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object division(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return getTypeValue(getMax(t1,t2),TCM_Transform.getDouble(o1) / TCM_Transform.getDouble(o2));
        return RunClass.runMethod(o1,"division",new Object[]{o2});
    }
    /**
     * >
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object largenOperation(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return TCM_Transform.getDouble(o1) > TCM_Transform.getDouble(o2);
        return RunClass.runMethod(o1,"largenOperation",new Object[]{o2});
    }
    /**
     * <
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object lessOperation(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return TCM_Transform.getDouble(o1) < TCM_Transform.getDouble(o2);
        return RunClass.runMethod(o1,"lessOperation",new Object[]{o2});
    }
    /**
     * >=
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object largenEqualOperation(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return TCM_Transform.getDouble(o1) >= TCM_Transform.getDouble(o2);
        return RunClass.runMethod(o1,"largenEqualOperation",new Object[]{o2});
    }
    /**
     * <=
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object lessEqualOperation(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return TCM_Transform.getDouble(o1) <= TCM_Transform.getDouble(o2);
        return RunClass.runMethod(o1,"lessEqualOperation",new Object[]{o2});
    }
    /**
     * ==
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object equalOperation(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return TCM_Transform.getDouble(o1) == TCM_Transform.getDouble(o2);
        return o1 == o2;
    }
    /**
     * !=
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object notEqualOperation(Object o1,Object o2)
    {
        if(o1 == null || o2 == null)
            return null;
        int t1 = getNumberType(o1);
        int t2 = getNumberType(o2);
        if(t1 > 0 && t2 > 0)
            return TCM_Transform.getDouble(o1) != TCM_Transform.getDouble(o2);
        return o1 != o2;
    }
    /**
     * 运算
     * @param action String
     * @param o1 Object
     * @param o2 Object
     * @return Object
     */
    public static Object operation(String action,Object o1,Object o2)
    {
        if(action.equals("+"))
            return addition(o1,o2);
        if(action.equals("-"))
            return subtration(o1,o2);
        if(action.equals("*"))
            return multiplication(o1,o2);
        if(action.equals("/"))
            return division(o1,o2);
        if(action.equals(">"))
            return largenOperation(o1,o2);
        if(action.equals("<"))
            return lessOperation(o1,o2);
        if(action.equals(">="))
            return largenEqualOperation(o1,o2);
        if(action.equals("<="))
            return lessEqualOperation(o1,o2);
        if(action.equals("=="))
            return equalOperation(o1,o2);
        if(action.equals("!="))
            return notEqualOperation(o1,o2);
        return null;
    }
}
