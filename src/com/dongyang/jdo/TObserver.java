package com.dongyang.jdo;

import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 观察者接口</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.19
 * @version 1.0
 */
public interface TObserver
{
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name);
    /**
     * 得到名称
     * @return String
     */
    public String getName();
    /**
     * 插入行
     * @param ds TDS
     * @param row int
     * @return int
     */
    public int insertRow(TDS ds,int row);
    /**
     * 修改项目
     * @param ds TDS
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setItem(TDS ds,int row,String column,Object value);
    /**
     * 得到项目
     * @param ds TDS
     * @param row int
     * @param column String
     * @param dwbuffer String
     * @param originalvalue boolean
     * @return Object
     */
    public Object getItemData(TDS ds,int row,String column,String dwbuffer,boolean originalvalue);
    /**
     * 删除行
     * @param ds TDS
     * @param row int
     * @return boolean
     */
    public boolean deleteRow(TDS ds,int row);
    /**
     * 得到更新语句
     * @param ds TDS
     * @return String[]
     */
    public String[] getUpdateSQL(TDS ds);
    /**
     * 清空保存状态
     * @param ds TDS
     */
    public void resetModify(TDS ds);
    /**
     * 得到其他项目
     * @param ds TDS
     * @param parm TParm
     * @param row int
     * @param column String
     * @return Object
     */
    public Object getOtherColumnValue(TDS ds,TParm parm,int row,String column);
    /**
     * 设置其他项目
     * @param ds TDS
     * @param parm TParm
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setOtherColumnValue(TDS ds,TParm parm,int row,String column,Object value);
    /**
     * 测试保存数据
     * @param ds TDS
     * @return boolean
     */
    public boolean checkSave(TDS ds);
}
