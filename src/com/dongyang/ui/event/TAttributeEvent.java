package com.dongyang.ui.event;

public class TAttributeEvent{
    public int row;
    public String name;
    public Object value;
    public Object oldValue;
    public TAttributeEvent(int row,String name,Object value,Object oldValue)
    {
        this.row = row;
        this.name = name;
        this.value = value;
        this.oldValue = oldValue;
    }
}
