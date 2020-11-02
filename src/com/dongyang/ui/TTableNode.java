package com.dongyang.ui;

public class TTableNode
{
    /**
     * �к�
     */
    private int row;
    /**
     * �к�
     */
    private int column;
    /**
     * ��ֵ
     */
    private Object oldValue;
    /**
     * ֵ
     */
    private Object value;
    /**
     * Ttable����
     */
    private TTable table;
    /**
     * �����к�
     * @param row int
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    /**
     * �õ��к�
     * @return int
     */
    public int getRow()
    {
        return row;
    }
    /**
     * �����к�
     * @param column int
     */
    public void setColumn(int column)
    {
        this.column = column;
    }
    /**
     * �õ��к�
     * @return int
     */
    public int getColumn()
    {
        return column;
    }
    /**
     * ���þ�ֵ
     * @param oldValue Object
     */
    public void setOldValue(Object oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * �õ���ֵ
     * @return Object
     */
    public Object getOldValue()
    {
        return oldValue;
    }
    /**
     * ����ֵ
     * @param value Object
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
    /**
     * �õ�ֵ
     * @return Object
     */
    public Object getValue()
    {
        return value;
    }
    /**
     * ����Table����
     * @param table TTable
     */
    public void setTable(TTable table)
    {
        this.table = table;
    }
    /**
     * �õ�Table����
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