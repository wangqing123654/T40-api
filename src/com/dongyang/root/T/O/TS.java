package com.dongyang.root.T.O;

import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: 属性</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.6
 * @version 1.0
 */
public class TS
        implements java.io.Serializable
{
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 设置类型
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * 类型相同
     * @param obj Object
     * @return boolean
     */
    public boolean equalsType(Object obj)
    {
        if(obj == null)
            return true;
        return StringTool.equalsObjectType(getType(),obj.getClass().getName());
    }
    /**
     * 得到类型
     * @return Object
     */
    public Object getDefaultValue()
    {
        if(getType() == null)
            return null;
        if("int".equals(getType()))
            return (int)0;
        if("long".equals(getType()))
            return (long)0;
        if("char".equals(getType()))
            return (char)0;
        if("float".equals(getType()))
            return (float)0;
        if("double".equals(getType()))
            return (double)0;
        if("byte".equals(getType()))
            return (byte)0;
        if("short".equals(getType()))
            return (short)0;
        if("boolean".equals(getType()))
            return false;
        return null;
    }
}
