package com.dongyang.data;
import com.dongyang.manager.TCM_Transform;
import java.sql.Timestamp;

/**
 *
 * <p>Title: 日期变量类型</p>
 *
 * <p>Description: 处理日期变量变化的情况</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.17
 * @version 1.0
 */
public class TimestampValue implements TValue
{
    /**
     * 监听对象
     */
    private ModifiedStatus modifiedStatus;
    /**
     * 值
     */
    private Timestamp value;
    /**
     * 旧值
     */
    private Timestamp oldValue;
    /**
     * 是否修改
     */
    private boolean isModify;
    /**
     * 构造器
     */
    public TimestampValue()
    {

    }
    /**
     * 构造器
     * @param modifiedStatus ModifiedStatus
     */
    public TimestampValue(ModifiedStatus modifiedStatus)
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
     * @param value Timestamp
     */
    public void setValue(Timestamp value)
    {
        this.value = value;
    }
    /**
     * 得到值
     * @return Timestamp
     */
    public Timestamp getValue()
    {
        return value;
    }
    /**
     * 设置旧值
     * @param oldValue Timestamp
     */
    public void setOldValue(Timestamp oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * 得到旧值
     * @return Timestamp
     */
    public Timestamp getOldValue()
    {
        return oldValue;
    }
    /**
     * 设置值(借口使用)
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        setValue(TCM_Transform.getTimestamp(obj));
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
        modifyValue(TCM_Transform.getTimestamp(obj));
    }
    /**
     * 修改值
     * @param value Timestamp
     */
    public void modifyValue(Timestamp value)
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
     * 判断日期是否相同
     * @param value1 Timestamp
     * @param value2 Timestamp
     * @return boolean
     */
    boolean isquals(Timestamp value1,Timestamp value2)
    {
        if(value1 == null && value2 == null)
            return true;
        if(value1 != null && value2 != null && value1.getTime() == value2.getTime())
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
