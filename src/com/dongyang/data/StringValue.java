package com.dongyang.data;
import com.dongyang.manager.TCM_Transform;
/**
 *
 * <p>Title: 字符变量类型</p>
 *
 * <p>Description: 处理字符变量变化的情况</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.17
 * @version 1.0
 */
public class StringValue implements TValue
{
    /**
     * 监听对象
     */
    private ModifiedStatus modifiedStatus;
    /**
     * 值
     */
    private String value;
    /**
     * 旧值
     */
    private String oldValue;
    /**
     * 是否修改
     */
    private boolean isModify;
    /**
     * 构造器
     */
    public StringValue()
    {

    }
    /**
     * 构造器
     * @param modifiedStatus ModifiedStatus
     */
    public StringValue(ModifiedStatus modifiedStatus)
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
     * @param value String
     */
    public void setValue(String value)
    {
        this.value = value;
    }
    /**
     * 得到值
     * @return String
     */
    public String getValue()
    {
        return value;
    }
    /**
     * 设置旧值
     * @param oldValue String
     */
    public void setOldValue(String oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * 得到旧值
     * @return String
     */
    public String getOldValue()
    {
        return oldValue;
    }
    /**
     * 设置值(借口使用)
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        setValue(TCM_Transform.getString(obj));
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
        modifyValue(TCM_Transform.getString(obj));
    }
    /**
     * 修改值
     * @param value String
     */
    public void modifyValue(String value)
    {
        if(isquals(value,getValue()))
            return;
        if(!isModify)
        {
            setOldValue(getValue());
            if(getModifiedParent() != null)
                getModifiedParent().addModify(this);
            isModify = true;
        }else if(isquals(value,getOldValue()))
        {
            if(getModifiedParent() != null)
                getModifiedParent().removeModify(this);
            setOldValue(null);
            isModify = false;
        }
        setValue(value);
    }
    /**
     * 判断字符是否相同
     * @param value1 String
     * @param value2 String
     * @return boolean
     */
    boolean isquals(String value1,String value2)
    {
        if(value1 == null && value2 == null)
            return true;
        if(value1 != null && value2 != null && value1.equals(value2))
            return true;
        return false;
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
        setOldValue(null);
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
