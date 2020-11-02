package com.dongyang.data;

public interface TValue
{
    public void setObject(Object obj);
    public Object getObject();
    public void modifyObject(Object obj);
    public Object getOldObject();
    public void reset();
}
