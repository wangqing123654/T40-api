package com.dongyang.jdo;

import com.dongyang.ui.TComponent;
import com.dongyang.data.TParm;
import com.dongyang.ui.TComboBox;

public class MaxLoadInf
{
    public static final int COMBO = 1;
    /**
     * ����
     */
    private int type;
    /**
     * ���ƶ���
     */
    private TComponent component;
    /**
     * module����
     */
    private String moduleName;
    /**
     * ��������
     */
    private String methodName;
    /**
     * ���ò���
     */
    private TParm inParm;
    /**
     * ��������
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * ���ÿ��ƶ���
     * @param component TComponent
     */
    public void setComponent(TComponent component)
    {
        this.component = component;
    }
    /**
     * �õ����ƶ���
     * @return TComponent
     */
    public TComponent getComponent()
    {
        return component;
    }
    /**
     * �õ������б�
     * @return TComboBox
     */
    public TComboBox getCombo()
    {
        return (TComboBox)getComponent();
    }
    /**
     * ����Module����
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * �õ�Module����
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
    }
    /**
     * ���÷�������
     * @param methodName String
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    /**
     * �õ���������
     * @return String
     */
    public String getMethodName()
    {
        return methodName;
    }
    /**
     * ���ý���
     * @param parm TParm
     */
    public void setInParm(TParm parm)
    {
        this.inParm = parm;
    }
    /**
     * �õ�����
     * @return TParm
     */
    public TParm getInParm()
    {
        return inParm;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("type=");
        sb.append(getType());
        sb.append(" moduleName=");
        sb.append(getModuleName());
        sb.append(" methodName=");
        sb.append(getMethodName());
        sb.append(" inParm=");
        sb.append(getInParm());
        return sb.toString();
    }
}
