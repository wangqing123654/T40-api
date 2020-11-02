package com.dongyang.config;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Vector;
import com.dongyang.util.StringTool;

public class TModuleConfigParse
{
    /**
     * 全部数据行
     */
    private Vector lines;
    /**
     * 构造器
     * @param data String
     */
    public TModuleConfigParse(String data)
    {
        //加载数据
        load(new BufferedReader(new StringReader(data)));
        //解析
        parse();
        //解析对象;
        /*setRoot(parseObject("UI"));
        //初始化试图
        setView(new TUIEditView());*/
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
}
