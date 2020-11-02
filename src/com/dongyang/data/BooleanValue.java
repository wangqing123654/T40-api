package com.dongyang.data;

import com.dongyang.manager.TCM_Transform;
/**
 *
 * <p>Title: ������������</p>
 *
 * <p>Description: �����������仯�����</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.17
 * @version 1.0
 */
public class BooleanValue implements TValue
{
    /**
     * ��������
     */
    private ModifiedStatus modifiedStatus;
    /**
     * ֵ
     */
    private boolean value;
    /**
     * ��ֵ
     */
    private boolean oldValue;
    /**
     * �Ƿ��޸�
     */
    private boolean isModify;
    /**
     * ������
     */
    public BooleanValue()
    {

    }
    /**
     * ������
     * @param modifiedStatus ModifiedStatus
     */
    public BooleanValue(ModifiedStatus modifiedStatus)
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
     * @param value boolean
     */
    public void setValue(boolean value)
    {
        this.value = value;
    }
    /**
     * �õ�ֵ
     * @return boolean
     */
    public boolean getValue()
    {
        return value;
    }
    /**
     * ���þ�ֵ
     * @param oldValue boolean
     */
    public void setOldValue(boolean oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * �õ���ֵ
     * @return boolean
     */
    public boolean getOldValue()
    {
        return oldValue;
    }
    /**
     * ����ֵ(���ʹ��)
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        setValue(TCM_Transform.getBoolean(obj));
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
        modifyValue(TCM_Transform.getBoolean(obj));
    }
    /**
     * �޸�ֵ
     * @param value boolean
     */
    public void modifyValue(boolean value)
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
            setOldValue(false);
            isModify = false;
        }
        setValue(value);
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
        setOldValue(false);
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
