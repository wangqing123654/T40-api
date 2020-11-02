package com.dongyang.ui;

import com.dongyang.config.TConfigParm;

public interface TComponent {
    /**
     * ����Tag
     * @param tag String
     */
    public void setTag(String tag);
    /**
     * �õ�Tag
     * @return String
     */
    public String getTag();
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm);
    /**
     * ���з���
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters);
    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @return Object
     */
    public Object callMessage(String message);
    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @param parm Object ����
     * @return Object
     */
    public Object callMessage(String message,Object parm);
    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName,Object[] parms,String[] parmType);
    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName,Object parm);
    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName);
    /**
     * �ͷ�
     */
    public void release();
    /**
     * ���ø������
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent);

    /**
     * �õ��������
     * @return TComponent
     */
    public TComponent getParentComponent();
}
