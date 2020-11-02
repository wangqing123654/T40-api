package com.dongyang.jdo;

import com.dongyang.data.TParm;

public class TObserverAdapter implements TObserver
{
    /**
     * 名称
     */
    private String name;
    /**
     * 受控DS
     */
    private TDS ds;
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
     * 设置受控DS
     * @param ds TDS
     */
    public void setDS(TDS ds)
    {
        this.ds = ds;
    }
    /**
     * 得到受控DS
     * @return TDS
     */
    public TDS getDS()
    {
        return ds;
    }
    /**
     * 插入行
     * @param ds TDS
     * @param row int
     * @return int
     */
    public int insertRow(TDS ds,int row)
    {
        return 0;
    }
    /**
     * 修改项目
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
     * 得到项目
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
     * 删除行
     * @param ds TDS
     * @param row int
     * @return boolean
     */
    public boolean deleteRow(TDS ds,int row)
    {
        return false;
    }
    /**
     * 得到更新语句
     * @param ds TDS
     * @return String[]
     */
    public String[] getUpdateSQL(TDS ds)
    {
        return null;
    }
    /**
     * 清空保存状态
     * @param ds TDS
     */
    public void resetModify(TDS ds)
    {
    }
    /**
     * 得到其他项目
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
     * 设置其他项目
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
     * 测试保存数据
     * @param ds TDS
     * @return boolean
     */
    public boolean checkSave(TDS ds)
    {
        return true;
    }
}
