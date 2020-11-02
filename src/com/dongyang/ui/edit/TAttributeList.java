package com.dongyang.ui.edit;

import java.util.Vector;
import com.dongyang.util.StringTool;

public class TAttributeList
{
    /**
     * 属性列表
     */
    private Vector items;

    public static class TAttribute {
        /**
         * 属性名称
         */
        private String name;
        /**
         * 属性类型
         */
        private String type;
        /**
         * 值
         */
        private String value;
        /**
         * 显示位置
         */
        private String alignment;
        /**
         * 编辑对话框
         */
        private String editDialog;
        /**
         * 构造器
         * @param name String 属性名
         * @param type String　类型
         * @param value String　值
         */
        public TAttribute(String name, String type,String value)
        {
            this(name,type,value,"Left");
        }
        /**
         * 构造器
         * @param name String　属性名
         * @param type String　类型
         * @param value String　值
         * @param alignment String　位置(默认Left)
         */
        public TAttribute(String name, String type,String value,String alignment)
        {
            this(name,type,value,alignment,"");
        }
        /**
         * 构造器
         * @param name String 属性名
         * @param type String 类型
         * @param value String 值
         * @param alignment String 位置(默认Left)
         * @param editDialog String 编辑对话框
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
         * 设置属性名称
         * @param name String
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * 得到属性名称
         * @return String
         */
        public String getName() {
            return name;
        }

        /**
         * 设置属性类型
         * @param type String
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * 得到属性类型
         * @return String
         */
        public String getType() {
            return type;
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
         * @return Object
         */
        public Object getValue()
        {
            return value;
        }
        /**
         * 设置显示位置
         * @param alignment String
         */
        public void setAlignment(String alignment)
        {
            this.alignment = alignment;
        }
        /**
         * 得到显示位置
         * @return String
         */
        public String getAlignment()
        {
            return alignment;
        }
        /**
         * 设置编辑对话框
         * @param editDialog String
         */
        public void setEditDialog(String editDialog)
        {
            this.editDialog = editDialog;
        }
        /**
         * 得到编辑对话框
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
     * 构造器
     */
    public TAttributeList()
    {
        items = new Vector();
    }
    /**
     * 属性个数
     * @return int
     */
    public int size()
    {
        return items.size();
    }
    /**
     * 得到属性
     * @param index int
     * @return TAttribute
     */
    public TAttribute get(int index)
    {
        return (TAttribute)items.get(index);
    }
    /**
     * 得到属性
     * @param name String 属性名
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
     * 新增属性
     * @param attribute TAttribute
     */
    public void add(TAttribute attribute)
    {
        items.add(attribute);
    }
    /**
     * 新增属性
     * @param index int 位置
     * @param attribute TAttribute
     */
    public void add(int index,TAttribute attribute)
    {
        items.add(index,attribute);
    }
    /**
     * 删除属性
     * @param index int 位置
     */
    public void remove(int index)
    {
        items.remove(index);
    }
    /**
     * 删除属性
     * @param attribute TAttribute
     */
    public void remove(TAttribute attribute)
    {
        items.remove(attribute);
    }
    /**
     * 得到值
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
     * 得到编辑类型数据
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
     * 得到编辑类型位置数据
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
