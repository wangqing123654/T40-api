package com.dongyang.data;
import com.dongyang.manager.TCM_Transform;
/**
 *
 * <p>Title: ������������</p>
 *
 * <p>Description: �������������仯�����</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.17
 * @version 1.0
 */
public class IntValue implements TValue
{
    /**
     * ��������
     */
    private ModifiedStatus modifiedStatus;
    /**
     * ֵ
     */
    private int value;
    /**
     * ��ֵ
     */
    private int oldValue;
    /**
     * �Ƿ��޸�
     */
    private boolean isModify;
    /**
     * ������
     */
    public IntValue()
    {

    }
    /**
     * ������
     * @param modifiedStatus ModifiedStatus
     */
    public IntValue(ModifiedStatus modifiedStatus)
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
     * @param value int
     */
    public void setValue(int value)
    {
        this.value = value;
    }
    /**
     * �õ�ֵ
     * @return int
     */
    public int getValue()
    {
        return value;
    }
    /**
     * ���þ�ֵ
     * @param oldValue int
     */
    public void setOldValue(int oldValue)
    {
        this.oldValue = oldValue;
    }
    /**
     * �õ���ֵ
     * @return int
     */
    public int getOldValue()
    {
        return oldValue;
    }
    /**
     * ����ֵ(���ʹ��)
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        setValue(TCM_Transform.getInt(obj));
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
        modifyValue(TCM_Transform.getInt(obj));
    }
    /**
     * �޸�ֵ
     * @param value int
     */
    public void modifyValue(int value)
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
            setOldValue(0);
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
        setOldValue(0);
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
