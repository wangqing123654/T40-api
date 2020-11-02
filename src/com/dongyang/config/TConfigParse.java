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
     * ȫ��������
     */
    private Vector lines;
    /**
     * ������
     */
    private TObject objectRoot;
    /**
     * �༭��ͼ
     */
    private TUIEditView view;
    public class TObject
    {
        /**
         * ����
         */
        private String type;
        /**
         * ��ǩ
         */
        private String tag;
        /**
         * �ж���
         */
        private Row rows[];
        /**
         * �����Ա
         */
        private Vector items;
        /**
         * λ��
         */
        private String position;
        /**
         * ���ӵ����
         */
        private TComponent component;
        /**
         * ���ݶ���
         */
        private Object data;
        /**
         * ����
         */
        private TObject parent;
        /**
         * ������
         */
        public TObject()
        {
            items = new Vector();
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
         * ���ñ�ǩ
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
         * �õ���ǩ
         * @return String
         */
        public String getTag()
        {
            return tag;
        }
        /**
         * �������ݶ���
         * @param data Object
         */
        public void setData(Object data)
        {
            this.data = data;
        }
        /**
         * �õ����ݶ���
         * @return Object
         */
        public Object getData()
        {
            return data;
        }
        /**
         * ���ø���
         * @param parent TObject
         */
        public void setParent(TObject parent)
        {
            this.parent = parent;
        }
        /**
         * �õ�����
         * @return TObject
         */
        public TObject getParent()
        {
            return parent;
        }
        /**
         * �����ж���
         * @param rows Row[]
         */
        public void setRows(Row rows[])
        {
            this.rows = rows;
        }
        /**
         * �õ��ж���
         * @return Row[]
         */
        public Row[] getRows()
        {
            return rows;
        }
        /**
         * �õ�����
         * @return int
         */
        public int getRowCount()
        {
            return getRows().length;
        }
        /**
         * �õ���
         * @param name String ����
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
         * �½���
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
            row.setType("����");
            row.modifiedValue("");
            return row;
        }
        /**
         * ����λ��
         * @param position String
         */
        public void setPosition(String position)
        {
            this.position = position;
        }
        /**
         * �õ�λ��
         * @return String
         */
        public String getPosition()
        {
            return position;
        }
        /**
         * �����������
         * @param component TComponent
         */
        public void setComponent(TComponent component)
        {
            this.component = component;
        }
        /**
         * �õ��������
         * @return TComponent
         */
        public TComponent getComponent()
        {
            return component;
        }
        /**
         * �õ��������
         * @return int
         */
        public int getItemCount()
        {
            return items.size();
        }
        /**
         * �õ��������
         * @param index int λ��
         * @return TObject
         */
        public TObject getItem(int index)
        {
            return (TObject)items.get(index);
        }
        /**
         * �������
         * @param obj TObject
         */
        public void addItem(TObject obj)
        {
            items.add(obj);
        }
        /**
         * �½�����
         * @param tag String ��ǩ
         * @param type String ����
         * @return TObject
         */
        public TObject newItem(String tag,String type)
        {
             TObject obj = new TObject();
             Row row = addRow(getRowIndex(rows[rows.length - 1]) + 1);
             row.setTag(tag);
             row.setName("Type");
             row.setType("����");
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
         * ɾ������
         * @param obj TObject
         */
        public void deleteItem(TObject obj)
        {
            if(obj == null)
                return;
            //ɾ���ö����ȫ���Ӷ���
            obj.deleteItemAll();
            //ɾ�������е�ȫ��������
            for(int i = 0;i < obj.getRowCount();i++)
                removeRow(obj.getRows()[i]);
            //�޸�����
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
            //�Ƴ�����
            items.remove(obj);
        }
        /**
         * ��Ŀ����tag����
         * @param oldTag String
         * @param newTag String
         */
        public void removeItemTag(String oldTag,String newTag)
        {
            //�޸�����
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
         * ɾ��ȫ������
         */
        public void deleteItemAll()
        {
            for(int i = getItemCount() - 1;i >= 0;i--)
                deleteItem(getItem(i));
        }
        /**
         * �õ�������(String)
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
         * �õ�����ֵ
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
         * �õ���ͼ
         * @return TUIEditView
         */
        public TUIEditView getView()
        {
            return TConfigParse.this.getView();
        }
        /**
         * �޸�����
         * @param name String ����
         * @param value String ֵ
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
         * �õ�����List
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
    /**
     * ������
     * @param data String
     */
    public TConfigParse(String data)
    {
        this(data,"UI");
        //��ʼ����ͼ
        setView(new TUIEditView());
    }
    public TConfigParse(String data,String type)
    {
        //��������
        load(new BufferedReader(new StringReader(data)));
        //����
        parse();
        //��������;
        setRoot(parseObject(type));
    }
    /**
     * ������ͼ
     * @param view TUIEditView
     */
    public void setView(TUIEditView view)
    {
        this.view = view;
    }
    /**
     * �õ���ͼ
     * @return TUIEditView
     */
    public TUIEditView getView()
    {
        return view;
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
     * ��������
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
     * @param tag String ��ǩ
     * @return TObject ����
     */
    public TObject parseObject(String tag)
    {
        Row rows[] = getTagRow(tag);
        if(rows.length == 0)
            return null;
        TObject obj = new TObject();
        obj.setRows(rows);
        obj.setTag(tag);
        //�õ���������
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
     * ������Ƹñ�ǩ��ȫ���ж���
     * @param tag String ��ǩ
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
     * �õ�������
     * @param name String ����
     * @return String ��ֵ
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
     * �õ����б�
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
     * ���������ж���
     * @param rows Row[] ���б�
     * @param name String ����
     * @return Row[] �ж���
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
    /**
     * �õ�������
     * @return TObject
     */
    public TObject getRoot()
    {
        return objectRoot;
    }
    /**
     * ���ø�����
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
