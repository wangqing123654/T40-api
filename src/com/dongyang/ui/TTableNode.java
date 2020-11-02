package com.dongyang.ui;

public class TTableNode
{
    /**
     * 行号
     */
    private int row;
    /**
     * 列号
     */
    private int column;
    /**
     * 旧值
     */
    private Object oldValue;
    /**
     * 值
     */
    private Object value;
    /**
     * Ttable对象
     */
    private TTable table;
    /**
     * 设置行号
     * @param row int
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    /**
     * 得到行号
     * @return int
     */
    public int getRow()
    {
        return row;
    }
    /**
     * 设置列号
     * @param column int
     */
    public void setColumn(int column)
    {
        this.column = column;
    }
    /**
     * 得到列号
     * @return int
     */
    public int getColumn()
    {
        return column;
    }
    /**
     * 设置旧值
     * @param oldValue Object
     */
    public void setOldValue(Object oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * 得到旧值
     * @return Object
     */
    public Object getOldValue()
    {
        return oldValue;
    }
    /**
     * 设置值
     * @param value Object
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
    /**
     * 得到值
     * @return Object
     */
    public Object getValue()
    {
        return value;
    }
    /**
     * 设置Table对象
     * @param table TTable
     */
    public void setTable(TTable table)
    {
        this.table = table;
    }
    /**
     * 得到Table对象
     * @return TTable
     */
    public TTable getTable()
    {
        return table;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TTableNode{row=");
        sb.append(getRow());
        sb.append(" column=");
        sb.append(getColumn());
        sb.append(" value=");
        sb.append(getValue());
        sb.append(" oldValue=");
        sb.append(getOldValue());
        sb.append("}");
        return sb.toString();
    }
}
