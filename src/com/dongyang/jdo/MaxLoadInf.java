package com.dongyang.jdo;

import com.dongyang.ui.TComponent;
import com.dongyang.data.TParm;
import com.dongyang.ui.TComboBox;

public class MaxLoadInf
{
    public static final int COMBO = 1;
    /**
     * 类型
     */
    private int type;
    /**
     * 控制对象
     */
    private TComponent component;
    /**
     * module名称
     */
    private String moduleName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 设置参数
     */
    private TParm inParm;
    /**
     * 设置类型
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * 设置控制对象
     * @param component TComponent
     */
    public void setComponent(TComponent component)
    {
        this.component = component;
    }
    /**
     * 得到控制对象
     * @return TComponent
     */
    public TComponent getComponent()
    {
        return component;
    }
    /**
     * 得到下拉列表
     * @return TComboBox
     */
    public TComboBox getCombo()
    {
        return (TComboBox)getComponent();
    }
    /**
     * 设置Module名称
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * 得到Module名称
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
    }
    /**
     * 设置方法名称
     * @param methodName String
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    /**
     * 得到方法名称
     * @return String
     */
    public String getMethodName()
    {
        return methodName;
    }
    /**
     * 设置进参
     * @param parm TParm
     */
    public void setInParm(TParm parm)
    {
        this.inParm = parm;
    }
    /**
     * 得到进参
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
