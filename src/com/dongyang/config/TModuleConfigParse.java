package com.dongyang.config;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Vector;
import com.dongyang.util.StringTool;

public class TModuleConfigParse
{
    /**
     * ȫ��������
     */
    private Vector lines;
    /**
     * ������
     * @param data String
     */
    public TModuleConfigParse(String data)
    {
        //��������
        load(new BufferedReader(new StringReader(data)));
        //����
        parse();
        //��������;
        /*setRoot(parseObject("UI"));
        //��ʼ����ͼ
        setView(new TUIEditView());*/
    }
    /**
     * ����
     */
    public void parse()
    {
        //��������
        parseBase();
        //��������
        parseData();
    }
    /**
     * ��������
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
                row.setType("����");
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
                row.setType("����");
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
     * ��������
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
                row.setType("��");
                row.setName(head[0].trim());
                row.setValue(head[1].trim());
                continue;
            }
            row.setType("����");
            String head[] = StringTool.getHead(data,"=");
            String lastHead[] = StringTool.getLastHead(head[0],'.');
            row.setTag(lastHead[0]);
            row.setName(lastHead[1]);
            row.setValue(head[1].trim());
        }
    }
    /**
     * ��������
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
     * �õ���һ����Ч������
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
     * �õ�������
     * @return int
     */
    public int getRowCount()
    {
        return lines.size();
    }
    /**
     * �õ��ж���
     * @param index int
     * @return Row
     */
    public Row getRow(int index)
    {
        return (Row)lines.get(index);
    }
    /**
     * ������
     * @param index int λ��
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
     * ������
     * @return Row
     */
    public Row addRow()
    {
        Row row = new Row();
        lines.add(row);
        return row;
    }
    /**
     * ɾ����
     * @param row Row
     */
    public void removeRow(Row row)
    {
        lines.remove(row);
    }
    /**
     * �õ���λ��
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
         * ����
         */
        private String name;
        /**
         * ����
         */
        private String value;
        /**
         * ����
         */
        private String data;
        /**
         * �ı�
         */
        private String text;
        /**
         * ����
         */
        private String type;
        /**
         * ��ǩ
         */
        private String tag;
        /**
         * ������
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
         * ������
         * @param text String
         */
        public Row(String text)
        {
            this();
            setText(text);
        }
        /**
         * �����ı�
         * @param text String
         */
        public void setText(String text)
        {
            this.text = text;
        }
        /**
         * �õ��ı�
         * @return String
         */
        public String getText()
        {
            return text;
        }
        /**
         * ��������
         * @param type String
         */
        public void setType(String type)
        {
            this.type = type;
        }
        /**
         * �õ�����
         * @return String
         */
        public String getType()
        {
            return type;
        }
        /**
         * ��������
         * @param data String
         */
        public void setData(String data)
        {
            this.data = data;
        }
        /**
         * �õ�����
         * @return String
         */
        public String getData()
        {
            return data;
        }
        /**
         * ��������
         * @param name String
         */
        public void setName(String name)
        {
            this.name = name;
        }
        /**
         * �õ�����
         * @return String
         */
        public String getName()
        {
            return name;
        }
        /**
         * ��������
         * @param value String
         */
        public void setValue(String value)
        {
            this.value = value;
        }
        /**
         * �õ�����
         * @return String
         */
        public String getValue()
        {
            return value;
        }
        /**
         * ���ñ�ǩ
         * @param tag String
         */
        public void setTag(String tag)
        {
            this.tag = tag;
        }
        /**
         * �õ���ǩ
         * @return String
         */
        public String getTag()
        {
            return tag;
        }
        /**
         * �޸�ֵ
         * @param value String
         */
        public void modifiedValue(String value)
        {
            setValue(value);
            if("��".equals(getType()))
            {
                setData(getName() + "=" + value);
                setText("<" + getData() + ">");
                return;
            }
            setData(getTag() + "." + getName() + "=" + value);
            setText(getData());
        }
        /**
         * �޸ı�ǩ
         * @param tag String
         */
        public void modifiedTag(String tag)
        {
            setTag(tag);
            if("��".equals(getType()))
            {
                setData(getName() + "=" + getValue());
                setText("<" + getData() + ">");
                return;
            }
            setData(getTag() + "." + getName() + "=" + getValue());
            setText(getData());
        }
        /**
         * �Ƿ���������
         * @return boolean
         */
        public boolean isDataRow()
        {
            if("����".equals(getType()))
                return false;
            if("#".equals(getType()))
                return false;
            if("//".equals(getType()))
                return false;
            if("����".equals(getType()))
                return false;
            if("��".equals(getType()))
                return false;
            return true;
        }
        /**
         * �Ƿ��Ǻ�
         * @return boolean
         */
        public boolean isGrand()
        {
            return "��".equals(getType());
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
