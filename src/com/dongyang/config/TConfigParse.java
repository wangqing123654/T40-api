package com.dongyang.config;

import java.io.StringReader;
import java.io.BufferedReader;
import java.util.Vector;
import com.dongyang.util.StringTool;
import java.util.ArrayList;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.edit.TUIEditView;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.ui.edit.TAttributeList.TAttribute;

public class TConfigParse {
    /**
     * 全部数据行
     */
    private Vector lines;
    /**
     * 根对象
     */
    private TObject objectRoot;
    /**
     * 编辑视图
     */
    private TUIEditView view;
    public class TObject
    {
        /**
         * 类型
         */
        private String type;
        /**
         * 标签
         */
        private String tag;
        /**
         * 行对象
         */
        private Row rows[];
        /**
         * 组件成员
         */
        private Vector items;
        /**
         * 位置
         */
        private String position;
        /**
         * 连接的组件
         */
        private TComponent component;
        /**
         * 数据对象
         */
        private Object data;
        /**
         * 父类
         */
        private TObject parent;
        /**
         * 构造器
         */
        public TObject()
        {
            items = new Vector();
        }
        /**
         * 设置类型
         * @param type String
         */
        public void setType(String type)
        {
            this.type = type;
        }
        /**
         * 得到类型
         * @return String
         */
        public String getType()
        {
            return type;
        }
        /**
         * 设置标签
         * @param tag String
         */
        public void setTag(String tag)
        {
            if(this.tag == null)
            {
                this.tag = tag;
                return;
            }
            if("UI".equalsIgnoreCase(this.tag))
                return;
            if(getParent() == null)
                return;
            for(int i = 0;i < getRowCount();i++)
                getRows()[i].modifiedTag(tag);
            getParent().removeItemTag(this.tag,tag);
            this.tag = tag;
        }
        /**
         * 得到标签
         * @return String
         */
        public String getTag()
        {
            return tag;
        }
        /**
         * 设置数据对象
         * @param data Object
         */
        public void setData(Object data)
        {
            this.data = data;
        }
        /**
         * 得到数据对象
         * @return Object
         */
        public Object getData()
        {
            return data;
        }
        /**
         * 设置父类
         * @param parent TObject
         */
        public void setParent(TObject parent)
        {
            this.parent = parent;
        }
        /**
         * 得到父类
         * @return TObject
         */
        public TObject getParent()
        {
            return parent;
        }
        /**
         * 设置行对象
         * @param rows Row[]
         */
        public void setRows(Row rows[])
        {
            this.rows = rows;
        }
        /**
         * 得到行对象
         * @return Row[]
         */
        public Row[] getRows()
        {
            return rows;
        }
        /**
         * 得到行数
         * @return int
         */
        public int getRowCount()
        {
            return getRows().length;
        }
        /**
         * 得到行
         * @param name String 名称
         * @return Row
         */
        public Row getRow(String name)
        {
            int count = getRowCount();
            for(int i = 0;i < count;i++)
            {
                Row row = getRows()[i];
                if(name.equalsIgnoreCase(row.getName()))
                    return row;
            }
            return null;
        }
        /**
         * 新建行
         * @param name String
         * @return Row
         */
        public Row newRow(String name)
        {
            Row row = addRow(getRowIndex(rows[rows.length - 1]) + 1);
            Row rowsNew[] = new Row[rows.length + 1];
            System.arraycopy(rows, 0, rowsNew, 0, rows.length);
            rowsNew[rows.length] = row;
            rows = rowsNew;
            row.setName(name);
            row.setTag(getTag());
            row.setType("程序");
            row.modifiedValue("");
            return row;
        }
        /**
         * 设置位置
         * @param position String
         */
        public void setPosition(String position)
        {
            this.position = position;
        }
        /**
         * 得到位置
         * @return String
         */
        public String getPosition()
        {
            return position;
        }
        /**
         * 设置连接组件
         * @param component TComponent
         */
        public void setComponent(TComponent component)
        {
            this.component = component;
        }
        /**
         * 得到连接组件
         * @return TComponent
         */
        public TComponent getComponent()
        {
            return component;
        }
        /**
         * 得到组件个数
         * @return int
         */
        public int getItemCount()
        {
            return items.size();
        }
        /**
         * 得到组件对象
         * @param index int 位置
         * @return TObject
         */
        public TObject getItem(int index)
        {
            return (TObject)items.get(index);
        }
        /**
         * 增加组件
         * @param obj TObject
         */
        public void addItem(TObject obj)
        {
            items.add(obj);
        }
        /**
         * 新建对象
         * @param tag String 标签
         * @param type String 类型
         * @return TObject
         */
        public TObject newItem(String tag,String type)
        {
             TObject obj = new TObject();
             Row row = addRow(getRowIndex(rows[rows.length - 1]) + 1);
             row.setTag(tag);
             row.setName("Type");
             row.setType("程序");
             row.modifiedValue(type);
             obj.setRows(new Row[]{row});
             obj.setTag(tag);
             obj.setType(type);
             addItem(obj);
             String itemValue = getValue("Item");
             if(itemValue == null)
                 itemValue = "";
             if(itemValue.length() > 0)
                 itemValue += ";";
             itemValue += tag;
             setValue("Item",itemValue);
             obj.setParent(this);
             return obj;
        }
        /**
         * 删除对象
         * @param obj TObject
         */
        public void deleteItem(TObject obj)
        {
            if(obj == null)
                return;
            //删除该对象的全部子对象
            obj.deleteItemAll();
            //删除对象中的全部数据行
            for(int i = 0;i < obj.getRowCount();i++)
                removeRow(obj.getRows()[i]);
            //修改索引
            String itemValues[] = StringTool.parseLine(getValue("Item"), ';', "[]{}()");
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i < itemValues.length;i++)
            {
                if(itemValues[i].trim().length() == 0)
                    continue;
                if(itemValues[i].equalsIgnoreCase(obj.getTag()))
                    continue;
                if(sb.length() > 0)
                    sb.append(";");
                sb.append(itemValues[i]);
            }
            setValue("Item",sb.toString());
            //移除对象
            items.remove(obj);
        }
        /**
         * 项目索引tag改名
         * @param oldTag String
         * @param newTag String
         */
        public void removeItemTag(String oldTag,String newTag)
        {
            //修改索引
            String itemValues[] = StringTool.parseLine(getValue("Item"), ';', "[]{}()");
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i < itemValues.length;i++)
            {
                if(itemValues[i].trim().length() == 0)
                    continue;
                if(sb.length() > 0)
                    sb.append(";");
                if(itemValues[i].equalsIgnoreCase(oldTag))
                    sb.append(newTag);
                else
                    sb.append(itemValues[i]);
            }
            setValue("Item",sb.toString());
        }
        /**
         * 删除全部对象
         */
        public void deleteItemAll()
        {
            for(int i = getItemCount() - 1;i >= 0;i--)
                deleteItem(getItem(i));
        }
        /**
         * 得到行索引(String)
         * @return String
         */
        public String getRowsString()
        {
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i < rows.length;i++)
            {
                if (sb.length() > 0)
                    sb.append(",");
                sb.append(getRowIndex(rows[i]));
            }
            return "[" + sb.toString() + "]";
        }
        /**
         * 得到属性值
         * @param name String
         * @return String
         */
        public String getValue(String name)
        {
            Row[] rows = getNameRow(getRows(),name);
            if(rows.length == 0)
                return null;
            return rows[rows.length - 1].getValue();
        }
        /**
         * 得到试图
         * @return TUIEditView
         */
        public TUIEditView getView()
        {
            return TConfigParse.this.getView();
        }
        /**
         * 修改属性
         * @param name String 名称
         * @param value String 值
         */
        public void setValue(String name,String value)
        {
            if(name == null || name.length() == 0)
                return;
            Row row = getRow(name);
            if(row == null)
                row = newRow(name);
            if(row == null)
                return;
            row.modifiedValue(value);
        }
        /**
         * 得到属性List
         * @return TAttributeList
         */
        public TAttributeList getAttributeList()
        {
            if(getComponent() == null)
                return null;
            TAttributeList attributeList = (TAttributeList)getComponent().callFunction("getAttributes");
            if(attributeList == null)
                return null;
            for(int i = 0;i < attributeList.size();i++)
            {
                TAttribute attribute = attributeList.get(i);
                if("Tag".equalsIgnoreCase(attribute.getName()))
                {
                    attribute.setValue(getTag());
                    continue;
                }
                String value = getValue(attribute.getName());
                if(value != null)
                    attribute.setValue(value);
            }
            return attributeList;
        }
        public String toString()
        {
            return toString(0);
        }
        public String toString(int p)
        {
            StringBuffer sb = new StringBuffer();
            if(p > 0)
                sb.append(StringTool.fill(" ",p));
            sb.append("{type=");
            sb.append(getType());
            sb.append(" rows=");
            sb.append(getRowsString());
            sb.append(" position=");
            sb.append(getPosition());
            int count = getItemCount();
            if(count > 0)
            {
                for(int i = 0;i < count;i++)
                {
                    sb.append("\n");
                    sb.append(getItem(i).toString(p + 2));
                }
                sb.append("\n");
                if(p > 0)
                    sb.append(StringTool.fill(" ",p));
            }
            sb.append("}");
            return sb.toString();
        }
    }
    public class Row
    {
        /**
         * 名称
         */
        private String name;
        /**
         * 数据
         */
        private String value;
        /**
         * 数据
         */
        private String data;
        /**
         * 文本
         */
        private String text;
        /**
         * 类型
         */
        private String type;
        /**
         * 标签
         */
        private String tag;
        /**
         * 构造器
         */
        public Row()
        {
            setName("");
            setValue("");
            setText("");
            setTag("");
            setType("");
            setData("");
        }
        /**
         * 构造器
         * @param text String
         */
        public Row(String text)
        {
            this();
            setText(text);
        }
        /**
         * 设置文本
         * @param text String
         */
        public void setText(String text)
        {
            this.text = text;
        }
        /**
         * 得到文本
         * @return String
         */
        public String getText()
        {
            return text;
        }
        /**
         * 设置类型
         * @param type String
         */
        public void setType(String type)
        {
            this.type = type;
        }
        /**
         * 得到类型
         * @return String
         */
        public String getType()
        {
            return type;
        }
        /**
         * 设置数据
         * @param data String
         */
        public void setData(String data)
        {
            this.data = data;
        }
        /**
         * 得到数据
         * @return String
         */
        public String getData()
        {
            return data;
        }
        /**
         * 设置名称
         * @param name String
         */
        public void setName(String name)
        {
            this.name = name;
        }
        /**
         * 得到名称
         * @return String
         */
        public String getName()
        {
            return name;
        }
        /**
         * 设置数据
         * @param value String
         */
        public void setValue(String value)
        {
            this.value = value;
        }
        /**
         * 得到数据
         * @return String
         */
        public String getValue()
        {
            return value;
        }
        /**
         * 设置标签
         * @param tag String
         */
        public void setTag(String tag)
        {
            this.tag = tag;
        }
        /**
         * 得到标签
         * @return String
         */
        public String getTag()
        {
            return tag;
        }
        /**
         * 修改值
         * @param value String
         */
        public void modifiedValue(String value)
        {
            setValue(value);
            if("宏".equals(getType()))
            {
                setData(getName() + "=" + value);
                setText("<" + getData() + ">");
                return;
            }
            setData(getTag() + "." + getName() + "=" + value);
            setText(getData());
        }
        /**
         * 修改标签
         * @param tag String
         */
        public void modifiedTag(String tag)
        {
            setTag(tag);
            if("宏".equals(getType()))
            {
                setData(getName() + "=" + getValue());
                setText("<" + getData() + ">");
                return;
            }
            setData(getTag() + "." + getName() + "=" + getValue());
            setText(getData());
        }
        /**
         * 是否是数据行
         * @return boolean
         */
        public boolean isDataRow()
        {
            if("空行".equals(getType()))
                return false;
            if("#".equals(getType()))
                return false;
            if("//".equals(getType()))
                return false;
            if("连行".equals(getType()))
                return false;
            if("宏".equals(getType()))
                return false;
            return true;
        }
        /**
         * 是否是宏
         * @return boolean
         */
        public boolean isGrand()
        {
            return "宏".equals(getType());
        }
        public String toString()
        {
            StringBuffer sb = new StringBuffer();
            sb.append("{type=\"");
            sb.append(getType());
            sb.append("\" tag=\"");
            sb.append(getTag());
            sb.append("\" name=\"");
            sb.append(getName());
            sb.append("\" value=\"");
            sb.append(getValue());
            sb.append("\" data=\"");
            sb.append(getData());
            sb.append("\" text=\"");
            sb.append(getText());
            sb.append("\"}");
            return sb.toString();
        }
    }
    /**
     * 构造器
     * @param data String
     */
    public TConfigParse(String data)
    {
        this(data,"UI");
        //初始化试图
        setView(new TUIEditView());
    }
    public TConfigParse(String data,String type)
    {
        //加载数据
        load(new BufferedReader(new StringReader(data)));
        //解析
        parse();
        //解析对象;
        setRoot(parseObject(type));
    }
    /**
     * 设置试图
     * @param view TUIEditView
     */
    public void setView(TUIEditView view)
    {
        this.view = view;
    }
    /**
     * 得到试图
     * @return TUIEditView
     */
    public TUIEditView getView()
    {
        return view;
    }
    /**
     * 加载数据
     * @param br BufferedReader
     */
    public void load(BufferedReader br)
    {
        lines = new Vector();
        try {
            String line = "";
            while ((line = br.readLine()) != null) {
                Row row = new Row(line);
                lines.add(row);
            }
        }catch(Exception e)
        {
        }
    }
    /**
     * 生成数据
     * @return String
     */
    public String save()
    {
        StringBuffer sb = new StringBuffer();
        int count = getRowCount();
        for(int i = 0;i < count;i++)
        {
            Row row = getRow(i);
            sb.append(row.getText());
            if(i < count - 1)
                sb.append('\r');
        }
        return sb.toString();
    }
    /**
     * 解析
     */
    public void parse()
    {
        //基本解析
        parseBase();
        //解析数据
        parseData();
    }
    /**
     * 基本解析
     */
    public void parseBase()
    {
        int count = getRowCount();
        for(int i = 0;i < count;i++)
        {
            Row row = getRow(i);
            String text = row.getText();
            text = text.trim();
            if (text.length() == 0)
            {
                row.setType("空行");
                row.setData("");
                continue;
            }
            if (text.startsWith("#"))
            {
                row.setType("#");
                row.setData(text.substring(1,text.length()));
                continue;
            }
            if (text.startsWith("//"))
            {
                row.setType("//");
                row.setData(text.substring(2,text.length()));
                continue;
            }
            if (text.endsWith("&")) {
                text = text.substring(0, text.length() - 1).trim();
                row.setType("连行");
                row.setData(text);
                Row r = getRowForData(i);
                if(r != null)
                    r.setData(r.getData() + text);
                continue;
            }
            row.setData(text.trim());
        }
    }
    /**
     * 解析数据
     */
    public void parseData()
    {
        int count = getRowCount();
        for(int i = 0;i < count;i++)
        {
            Row row = getRow(i);
            if(!row.isDataRow())
                continue;
            String data = row.getData();
            if (data.startsWith("<") && data.endsWith(">")) {
                data = data.substring(1, data.length() - 1).trim();
                String head[] = StringTool.getHead(data,"=");
                row.setType("宏");
                row.setName(head[0].trim());
                row.setValue(head[1].trim());
                continue;
            }
            row.setType("程序");
            String head[] = StringTool.getHead(data,"=");
            String lastHead[] = StringTool.getLastHead(head[0],'.');
            row.setTag(lastHead[0]);
            row.setName(lastHead[1]);
            row.setValue(head[1].trim());
        }
    }
    /**
     * 解析对象
     * @param tag String 标签
     * @return TObject 对象
     */
    public TObject parseObject(String tag)
    {
        Row rows[] = getTagRow(tag);
        if(rows.length == 0)
            return null;
        TObject obj = new TObject();
        obj.setRows(rows);
        obj.setTag(tag);
        //得到对象类型
        String type = obj.getValue("Type");
        if(type == null && "UI".equalsIgnoreCase(tag))
            type = getGrandValue("Type");
        obj.setType(type);
        String item = obj.getValue("Item");
        String items[] = StringTool.parseLine(item, ';', "[]{}()");
        for (int i = 0; i < items.length; i++)
        {
            if(items[i].length() == 0)
                continue;
            String values[] = StringTool.parseLine(items[i], "|");
            String cid = values[0];
            TObject com = parseObject(cid);
            com.setParent(obj);
            if(values.length == 2)
                com.setPosition(values[1]);
            if(com == null)
                continue;
            obj.addItem(com);
        }
        return obj;
    }
    /**
     * 查找设计该标签的全部行对象
     * @param tag String 标签
     * @return Row[]
     */
    public Row[] getTagRow(String tag)
    {
        int count = getRowCount();
        ArrayList list = new ArrayList();
        for(int i = 0;i < count;i++)
        {
            Row row = getRow(i);
            if(!row.isDataRow())
                continue;
            if(!tag.equalsIgnoreCase(row.getTag()))
                continue;
            list.add(row);
        }
        return (Row[])list.toArray(new Row[]{});
    }
    /**
     * 得到宏属性
     * @param name String 名称
     * @return String 数值
     */
    public String getGrandValue(String name)
    {
        Row[] rows = getGrandRow();
        int count = rows.length;
        for(int i = 0; i < count;i++)
            if(name.equalsIgnoreCase(rows[i].getName()))
                return rows[i].getValue();
        return null;
    }
    /**
     * 得到宏列表
     * @return Row[]
     */
    public Row[] getGrandRow()
    {
        int count = getRowCount();
        ArrayList list = new ArrayList();
        for(int i = 0;i < count;i++)
        {
            Row row = getRow(i);
            if (row.isGrand())
                list.add(row);
        }
        return (Row[])list.toArray(new Row[]{});
    }
    /**
     * 查找属性行对象
     * @param rows Row[] 行列表
     * @param name String 名称
     * @return Row[] 行对象
     */
    public Row[] getNameRow(Row[] rows,String name)
    {
        int count = rows.length;
        ArrayList list = new ArrayList();
        for(int i = 0; i < count;i++)
        {
            Row row = rows[i];
            if(name.equalsIgnoreCase(row.getName()))
                list.add(row);
        }
        return (Row[])list.toArray(new Row[]{});
    }
    /**
     * 得到上一个有效数据行
     * @param index int
     * @return Row
     */
    public Row getRowForData(int index)
    {
        for(int i = index - 1;i >= 0;i--)
        {
            Row row = getRow(i);
            if(row.isDataRow())
                return row;
        }
        return null;
    }
    /**
     * 得到总行数
     * @return int
     */
    public int getRowCount()
    {
        return lines.size();
    }
    /**
     * 得到行对象
     * @param index int
     * @return Row
     */
    public Row getRow(int index)
    {
        return (Row)lines.get(index);
    }
    /**
     * 插入行
     * @param index int 位置
     * @return Row
     */
    public Row addRow(int index)
    {
        if(index < 0 || index >= getRowCount())
            return addRow();
        Row row = new Row();
        lines.add(index,row);
        return row;
    }
    /**
     * 新增行
     * @return Row
     */
    public Row addRow()
    {
        Row row = new Row();
        lines.add(row);
        return row;
    }
    /**
     * 删除行
     * @param row Row
     */
    public void removeRow(Row row)
    {
        lines.remove(row);
    }
    /**
     * 得到行位置
     * @param row Row
     * @return int
     */
    public int getRowIndex(Row row)
    {
        int count = getRowCount();
        for(int i = 0;i < count;i++)
            if(row == getRow(i))
                return i;
        return -1;
    }
    /**
     * 得到根对象
     * @return TObject
     */
    public TObject getRoot()
    {
        return objectRoot;
    }
    /**
     * 设置根对象
     * @param root TObject
     */
    public void setRoot(TObject root)
    {
        objectRoot = root;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        int count = getRowCount();
        for(int i = 0;i < count;i++)
        {
            sb.append(i);
            sb.append(getRow(i));
            sb.append("\n");
        }
        sb.append(getRoot());
        return sb.toString();
    }
}
