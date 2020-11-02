package com.dongyang.data;

import java.sql.Types;
import java.sql.Timestamp;
import java.io.Serializable;
public class TNull implements Serializable
{
    int type;
    public TNull(int type)
    {
        this.type = type;
    }
    public TNull(Class<?> classType)
    {
        if(classType == String.class)
            type = Types.VARCHAR;
        if(classType == Integer.class)
            type = Types.INTEGER;
        if(classType == Double.class)
            type = Types.DOUBLE;
        if(classType == Timestamp.class)
            type = Types.TIMESTAMP;
    }
    public int getType()
    {
        return type;
    }
    public String toString()
    {
        return "<TNULL>";
    }
}
