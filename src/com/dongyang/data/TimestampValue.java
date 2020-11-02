package com.dongyang.data;
import com.dongyang.manager.TCM_Transform;
import java.sql.Timestamp;

/**
 *
 * <p>Title: ���ڱ�������</p>
 *
 * <p>Description: �������ڱ����仯�����</p>
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
     * ��������
     */
    private ModifiedStatus modifiedStatus;
    /**
     * ֵ
     */
    private Timestamp value;
    /**
     * ��ֵ
     */
    private Timestamp oldValue;
    /**
     * �Ƿ��޸�
     */
    private boolean isModify;
    /**
     * ������
     */
    public TimestampValue()
    {

    }
    /**
     * ������
     * @param modifiedStatus ModifiedStatus
     */
    public TimestampValue(ModifiedStatus modifiedStatus)
    {
        setModifiedParent(modifiedStatus);
    }
    /**
     * ���ü�����
     * @param modifiedStatus ModifiedStatus
     */
    public void setModifiedParent(ModifiedStatus modifiedStatus)
    {
        this.modifiedStatus = modifiedStatus;
    }
    /**
     * �õ�������
     * @return ModifiedStatus
     */
    public ModifiedStatus getModifiedParent()
    {
        return modifiedStatus;
    }
    /**
     * ����ֵ
     * @param value Timestamp
     */
    public void setValue(Timestamp value)
    {
        this.value = value;
    }
    /**
     * �õ�ֵ
     * @return Timestamp
     */
    public Timestamp getValue()
    {
        return value;
    }
    /**
     * ���þ�ֵ
     * @param oldValue Timestamp
     */
    public void setOldValue(Timestamp oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * �õ���ֵ
     * @return Timestamp
     */
    public Timestamp getOldValue()
    {
        return oldValue;
    }
    /**
     * ����ֵ(���ʹ��)
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        setValue(TCM_Transform.getTimestamp(obj));
    }
    /**
     * �õ�ֵ(���ʹ��)
     * @return Object
     */
    public Object getObject()
    {
        return value;
    }
    /**
     * �õ���ֵ(���ʹ��)
     * @return Object
     */
    public Object getOldObject()
    {
        if(isModified())
            return getOldValue();
        return value;
    }
    /**
     * �޸�ֵ
     * @param obj Object
     */
    public void modifyObject(Object obj)
    {
        modifyValue(TCM_Transform.getTimestamp(obj));
    }
    /**
     * �޸�ֵ
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
     * �ж������Ƿ���ͬ
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
     * �Ƿ��޸�
     * @return boolean
     */
    public boolean isModified()
    {
        return isModify;
    }
    /**
     * ����
     */
    public void reset()
    {
        if(!isModified())
            return;
        setOldValue(null);
        isModify = false;
    }
    /**
     * �ַ���ʾת������
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
