package com.dongyang.root.T.O;

import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: ����</p>
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
     * ����
     */
    private String name;
    /**
     * ����
     */
    private String type;
    /**
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * ��������
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * ������ͬ
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
     * �õ�����
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
