package com.dongyang.ui.edit;

import java.util.Vector;
import com.dongyang.util.StringTool;

public class TAttributeList
{
    /**
     * �����б�
     */
    private Vector items;

    public static class TAttribute {
        /**
         * ��������
         */
        private String name;
        /**
         * ��������
         */
        private String type;
        /**
         * ֵ
         */
        private String value;
        /**
         * ��ʾλ��
         */
        private String alignment;
        /**
         * �༭�Ի���
         */
        private String editDialog;
        /**
         * ������
         * @param name String ������
         * @param type String������
         * @param value String��ֵ
         */
        public TAttribute(String name, String type,String value)
        {
            this(name,type,value,"Left");
        }
        /**
         * ������
         * @param name String��������
         * @param type String������
         * @param value String��ֵ
         * @param alignment String��λ��(Ĭ��Left)
         */
        public TAttribute(String name, String type,String value,String alignment)
        {
            this(name,type,value,alignment,"");
        }
        /**
         * ������
         * @param name String ������
         * @param type String ����
         * @param value String ֵ
         * @param alignment String λ��(Ĭ��Left)
         * @param editDialog String �༭�Ի���
         */
        public TAttribute(String name, String type,String value,String alignment,String editDialog)
        {
            setName(name);
            setType(type);
            setValue(value);
            setAlignment(alignment);
            setEditDialog(editDialog);
        }

        /**
         * ������������
         * @param name String
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * �õ���������
         * @return String
         */
        public String getName() {
            return name;
        }

        /**
         * ������������
         * @param type String
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * �õ���������
         * @return String
         */
        public String getType() {
            return type;
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
         * @return Object
         */
        public Object getValue()
        {
            return value;
        }
        /**
         * ������ʾλ��
         * @param alignment String
         */
        public void setAlignment(String alignment)
        {
            this.alignment = alignment;
        }
        /**
         * �õ���ʾλ��
         * @return String
         */
        public String getAlignment()
        {
            return alignment;
        }
        /**
         * ���ñ༭�Ի���
         * @param editDialog String
         */
        public void setEditDialog(String editDialog)
        {
            this.editDialog = editDialog;
        }
        /**
         * �õ��༭�Ի���
         * @return String
         */
        public String getEditDialog()
        {
            return editDialog;
        }
        public String toString()
        {
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            sb.append(getName());
            sb.append(",");
            sb.append(getType());
            sb.append(",");
            sb.append(getValue());
            sb.append(",");
            sb.append(getAlignment());
            sb.append(",");
            sb.append(getEditDialog());
            sb.append("]");
            return sb.toString();
        }
    }
    /**
     * ������
     */
    public TAttributeList()
    {
        items = new Vector();
    }
    /**
     * ���Ը���
     * @return int
     */
    public int size()
    {
        return items.size();
    }
    /**
     * �õ�����
     * @param index int
     * @return TAttribute
     */
    public TAttribute get(int index)
    {
        return (TAttribute)items.get(index);
    }
    /**
     * �õ�����
     * @param name String ������
     * @return TAttribute
     */
    public TAttribute get(String name)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            TAttribute attribute = get(i);
            if(attribute.getName().equalsIgnoreCase(name))
                return attribute;
        }
        return null;
    }
    /**
     * ��������
     * @param attribute TAttribute
     */
    public void add(TAttribute attribute)
    {
        items.add(attribute);
    }
    /**
     * ��������
     * @param index int λ��
     * @param attribute TAttribute
     */
    public void add(int index,TAttribute attribute)
    {
        items.add(index,attribute);
    }
    /**
     * ɾ������
     * @param index int λ��
     */
    public void remove(int index)
    {
        items.remove(index);
    }
    /**
     * ɾ������
     * @param attribute TAttribute
     */
    public void remove(TAttribute attribute)
    {
        items.remove(attribute);
    }
    /**
     * �õ�ֵ
     * @return String
     */
    public String getValue()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i++)
        {
            TAttribute attribute = get(i);
            if(sb.length() > 0)
                sb.append(",");
            sb.append("[");
            sb.append(attribute.getName());
            sb.append(",\"");
            sb.append(attribute.getValue());
            sb.append("\"]");
        }
        String data = "[" + sb.toString() + "]";
        data = StringTool.replaceAll(data,"\\","\\\\\\\\");
        return data;
    }
    /**
     * �õ��༭��������
     * @return String
     */
    public String getEditType()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i++)
        {
            TAttribute attribute = get(i);
            if(sb.length() > 0)
                sb.append(";");
            sb.append(i);
            sb.append(",");
            sb.append(1);
            sb.append(",");
            sb.append(attribute.getType());
        }
        return sb.toString();
    }
    /**
     * �õ��༭����λ������
     * @return String
     */
    public String getEditAlignment()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i++)
        {
            TAttribute attribute = get(i);
            if (sb.length() > 0)
                sb.append(";");
            sb.append(i);
            sb.append(",");
            sb.append(1);
            sb.append(",");
            sb.append(attribute.getAlignment());
        }
        return sb.toString();
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for(int i = 0;i < size();i++)
            sb.append(get(i));
        sb.append("}");
        return sb.toString();
    }
}
