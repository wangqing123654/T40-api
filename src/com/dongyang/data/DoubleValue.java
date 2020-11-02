package com.dongyang.data;
import com.dongyang.manager.TCM_Transform;
/**
 *
 * <p>Title: 小数变量类型</p>
 *
 * <p>Description: 处理小数变量变化的情况</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.17
 * @version 1.0
 */
public class DoubleValue implements TValue
{
    /**
     * 监听对象
     */
    private ModifiedStatus modifiedStatus;
    /**
     * 值
     */
    private double value;
    /**
     * 旧值
     */
    private double oldValue;
    /**
     * 是否修改
     */
    private boolean isModify;
    /**
     * 构造器
     */
    public DoubleValue()
    {

    }
    /**
     * 构造器
     * @param modifiedStatus ModifiedStatus
     */
    public DoubleValue(ModifiedStatus modifiedStatus)
    {
        setModifiedParent(modifiedStatus);
    }
    /**
     * 设置监听类
     * @param modifiedStatus ModifiedStatus
     */
    public void setModifiedParent(ModifiedStatus modifiedStatus)
    {
        this.modifiedStatus = modifiedStatus;
    }
    /**
     * 得到监听类
     * @return ModifiedStatus
     */
    public ModifiedStatus getModifiedParent()
    {
        return modifiedStatus;
    }
    /**
     * 设置值
     * @param value double
     */
    public void setValue(double value)
    {
        this.value = value;
    }
    /**
     * 得到值
     * @return double
     */
    public double getValue()
    {
        return value;
    }
    /**
     * 设置旧值
     * @param oldValue double
     */
    public void setOldValue(double oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * 得到旧值
     * @return double
     */
    public double getOldValue()
    {
        return oldValue;
    }
    /**
     * 设置值(借口使用)
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        setValue(TCM_Transform.getDouble(obj));
    }
    /**
     * 得到值(借口使用)
     * @return Object
     */
    public Object getObject()
    {
        return value;
    }
    /**
     * 得到旧值(借口使用)
     * @return Object
     */
    public Object getOldObject()
    {
        if(isModified())
            return getOldValue();
        return value;
    }
    /**
     * 修改值
     * @param obj Object
     */
    public void modifyObject(Object obj)
    {
        modifyValue(TCM_Transform.getDouble(obj));
    }
    /**
     * 修改值
     * @param value double
     */
    public void modifyValue(double value)
    {
        if(value == getValue())
            return;
        if(!isModify)
        {
            setOldValue(getValue());
            if(getModifiedParent() != null)
                getModifiedParent().addModify(this);
            isModify = true;
        }else if(value == getOldValue())
        {
            if(getModifiedParent() != null)
                getModifiedParent().removeModify(this);
            setOldValue(0.0);
            isModify = false;
        }
        setValue(value);
    }
    /**
     * 是否修改
     * @return boolean
     */
    public boolean isModified()
    {
        return isModify;
    }
    /**
     * 清零
     */
    public void reset()
    {
        if(!isModified())
            return;
        setOldValue(0.0);
        isModify = false;
    }
    /**
     * 字符显示转化函数
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[value=");
        sb.append(getValue());
        sb.append(",old=");
        sb.append(getOldValue());
        sb.append(",isModify=");
        sb.append(isModified());
        sb.append("]");
        return sb.toString();
    }
}
