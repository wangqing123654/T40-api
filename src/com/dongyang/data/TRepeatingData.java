package com.dongyang.data;

import java.util.Vector;

public class TRepeatingData {
    private String name;
    private String type;
    private Vector value = new Vector();
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getType()
    {
        return type;
    }
    public void setValue(Vector value)
    {
        this.value = value;
    }
    public Vector getValue()
    {
        return value;
    }
    public int getCount()
    {
        return value.size();
    }
    public void addValue(Object value)
    {
        getValue().add(value);
    }
    public void insertValue(Object value,int index)
    {
        getValue().insertElementAt(value,index);
    }
    public void removeValue(int index)
    {
        getValue().removeElementAt(index);
    }
    public Object get(int index)
    {
        return getValue().get(index);
    }
    public void set(int index,Object value)
    {
        getValue().set(index,value);
    }
    public int index(Object value)
    {
        int index = getValue().indexOf(value);
        if(index < 0)
            return index;
        return index;
    }
    public void clear()
    {
        getValue().clear();
    }
}
