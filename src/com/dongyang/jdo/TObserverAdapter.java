package com.dongyang.jdo;

import com.dongyang.data.TParm;

public class TObserverAdapter implements TObserver
{
    /**
     * ����
     */
    private String name;
    /**
     * �ܿ�DS
     */
    private TDS ds;
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
     * �����ܿ�DS
     * @param ds TDS
     */
    public void setDS(TDS ds)
    {
        this.ds = ds;
    }
    /**
     * �õ��ܿ�DS
     * @return TDS
     */
    public TDS getDS()
    {
        return ds;
    }
    /**
     * ������
     * @param ds TDS
     * @param row int
     * @return int
     */
    public int insertRow(TDS ds,int row)
    {
        return 0;
    }
    /**
     * �޸���Ŀ
     * @param ds TDS
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setItem(TDS ds,int row,String column,Object value)
    {
        return false;
    }
    /**
     * �õ���Ŀ
     * @param ds TDS
     * @param row int
     * @param column String
     * @param dwbuffer String
     * @param originalvalue boolean
     * @return Object
     */
    public Object getItemData(TDS ds,int row,String column,String dwbuffer,boolean originalvalue)
    {
        return null;
    }
    /**
     * ɾ����
     * @param ds TDS
     * @param row int
     * @return boolean
     */
    public boolean deleteRow(TDS ds,int row)
    {
        return false;
    }
    /**
     * �õ��������
     * @param ds TDS
     * @return String[]
     */
    public String[] getUpdateSQL(TDS ds)
    {
        return null;
    }
    /**
     * ��ձ���״̬
     * @param ds TDS
     */
    public void resetModify(TDS ds)
    {
    }
    /**
     * �õ�������Ŀ
     * @param ds TDS
     * @param parm TParm
     * @param row int
     * @param column String
     * @return Object
     */
    public Object getOtherColumnValue(TDS ds,TParm parm,int row,String column)
    {
        return null;
    }
    /**
     * ����������Ŀ
     * @param ds TDS
     * @param parm TParm
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setOtherColumnValue(TDS ds,TParm parm,int row,String column,Object value)
    {
        return false;
    }
    /**
     * ���Ա�������
     * @param ds TDS
     * @return boolean
     */
    public boolean checkSave(TDS ds)
    {
        return true;
    }
}
