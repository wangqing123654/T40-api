package com.dongyang.data;
import com.dongyang.manager.TCM_Transform;
/**
 *
 * <p>Title: �ַ���������</p>
 *
 * <p>Description: �����ַ������仯�����</p>
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
     * ��������
     */
    private ModifiedStatus modifiedStatus;
    /**
     * ֵ
     */
    private String value;
    /**
     * ��ֵ
     */
    private String oldValue;
    /**
     * �Ƿ��޸�
     */
    private boolean isModify;
    /**
     * ������
     */
    public StringValue()
    {

    }
    /**
     * ������
     * @param modifiedStatus ModifiedStatus
     */
    public StringValue(ModifiedStatus modifiedStatus)
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
     * @param value String
     */
    public void setValue(String value)
    {
        this.value = value;
    }
    /**
     * �õ�ֵ
     * @return String
     */
    public String getValue()
    {
        return value;
    }
    /**
     * ���þ�ֵ
     * @param oldValue String
     */
    public void setOldValue(String oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * �õ���ֵ
     * @return String
     */
    public String getOldValue()
    {
        return oldValue;
    }
    /**
     * ����ֵ(���ʹ��)
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        setValue(TCM_Transform.getString(obj));
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
        modifyValue(TCM_Transform.getString(obj));
    }
    /**
     * �޸�ֵ
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
     * �ж��ַ��Ƿ���ͬ
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
