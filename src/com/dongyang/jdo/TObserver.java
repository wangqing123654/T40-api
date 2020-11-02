package com.dongyang.jdo;

import com.dongyang.data.TParm;

/**
 *
 * <p>Title: �۲��߽ӿ�</p>
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
     * ��������
     * @param name String
     */
    public void setName(String name);
    /**
     * �õ�����
     * @return String
     */
    public String getName();
    /**
     * ������
     * @param ds TDS
     * @param row int
     * @return int
     */
    public int insertRow(TDS ds,int row);
    /**
     * �޸���Ŀ
     * @param ds TDS
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setItem(TDS ds,int row,String column,Object value);
    /**
     * �õ���Ŀ
     * @param ds TDS
     * @param row int
     * @param column String
     * @param dwbuffer String
     * @param originalvalue boolean
     * @return Object
     */
    public Object getItemData(TDS ds,int row,String column,String dwbuffer,boolean originalvalue);
    /**
     * ɾ����
     * @param ds TDS
     * @param row int
     * @return boolean
     */
    public boolean deleteRow(TDS ds,int row);
    /**
     * �õ��������
     * @param ds TDS
     * @return String[]
     */
    public String[] getUpdateSQL(TDS ds);
    /**
     * ��ձ���״̬
     * @param ds TDS
     */
    public void resetModify(TDS ds);
    /**
     * �õ�������Ŀ
     * @param ds TDS
     * @param parm TParm
     * @param row int
     * @param column String
     * @return Object
     */
    public Object getOtherColumnValue(TDS ds,TParm parm,int row,String column);
    /**
     * ����������Ŀ
     * @param ds TDS
     * @param parm TParm
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setOtherColumnValue(TDS ds,TParm parm,int row,String column,Object value);
    /**
     * ���Ա�������
     * @param ds TDS
     * @return boolean
     */
    public boolean checkSave(TDS ds);
}
