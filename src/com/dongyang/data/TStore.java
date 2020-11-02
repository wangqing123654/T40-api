package com.dongyang.data;

import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.util.StringTool;

public class TStore
{
    public static String PRIMARY = "Primary";
    /**
     * ������
     */
    private Map bufferList = new HashMap();
    /**
     * �����к�
     */
    private Vector indexID = new Vector();
    /**
     * ���������
     */
    private int indexMax = -1;
    /**
     * ���б�
     */
    private ColumnList columnList;
    /**
     * ������
     */
    public TStore()
    {
        setData(new TParm());
        onInit();
    }
    /**
     * ������
     * @param data TParm
     */
    public TStore(TParm data)
    {
        setData(data);
        onInit();
    }
    /**
     * �������ݰ�
     * @param data TParm
     */
    public void setData(TParm data)
    {
        setData(PRIMARY,data);
    }
    /**
     * �������ݰ�
     * @param name String ��������
     * @param data TParm
     */
    public void setData(String name,TParm data)
    {
        bufferList.put(name,data);
    }
    /**
     * �õ����ݰ�
     * @param name String ��������
     * @return TParm
     */
    public TParm getData(String name)
    {
        return (TParm)bufferList.get(name);
    }
    /**
     * �õ��黺�������ݰ�
     * @return TParm
     */
    public TParm getData()
    {
        return getData(PRIMARY);
    }
    /**
     * �õ��и���
     * @return int
     */
    public int getColumnCount()
    {
        return columnList.size();
    }
    /**
     * �õ�����
     * @return int
     */
    public int getRowCount()
    {
        return getRowCount(PRIMARY);
    }
    /**
     * �õ�����
     * @param name String
     * @return int
     */
    public int getRowCount(String name)
    {
        if(getColumnCount() == 0)
            return 0;
        TParm parm = getData(name);
        if(parm == null)
            return 0;
        return parm.getCount(columnList.getName(0));
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {
        columnList = new ColumnList();
        TParm primary = getData();
        if(primary == null)
            return;
        String s[] = primary.getNames();
        for(int i = 0;i < s.length;i++)
        {
            Object data = primary.getData(s[i]);
            if(data == null || !(data instanceof Vector))
                continue;
            Column column = new Column();
            column.setName(s[i]);
            column.setData((Vector)data);
            columnList.add(column);
        }
        int count = getRowCount();
        for(int i = 0; i < count;i++)
            indexID.add(getIndexMax());
    }
    public int getIndexMax()
    {
        indexMax ++;
        return indexMax;
    }
    /**
     * ������
     * @param name String ����
     */
    public void addColumn(String name)
    {
        if(name == null || name.length() == 0)
            return;
        int count = getRowCount();
        Vector data = new Vector();
        for(int i = 0;i < count;i ++)
            data.add("");
        addColumn(name,data);
    }
    /**
     * ������
     * @param name String ����
     * @param data Vector
     */
    public void addColumn(String name,Vector data)
    {
        if(name == null || name.length() == 0 || data == null)
            return;
        if(getRowCount() != data.size())
            return;
        Column column = new Column();
        column.setName(name);
        column.setData(data);
        columnList.add(column);
        TParm parm = getData();
        if(parm != null)
            parm.setData(name,data);
    }
    /**
     * ɾ����
     * @param index int
     */
    public void removeColumn(int index)
    {
        if(index < 0 || index >= getColumnCount())
            return;
        String name = columnList.getName(index);
        columnList.remove(index);
        TParm parm = getData();
        if(parm != null)
            parm.removeData(name);
    }
    /**
     * ɾ����
     * @param name String ����
     */
    public void removeColumn(String name)
    {
        if(name == null || name.length() == 0)
            return;
        TParm parm = getData();
        if(parm == null)
            return;
        Column column = columnList.get(name);
        if(column == null)
            return;
        columnList.remove(column.getName());
        parm.removeData(column.getName());
    }
    /**
     * ������
     * @param row int
     * @return int
     */
    public int addRow(int row)
    {
        if(row < 0 || row >= getRowCount())
            return addRow();
        TParm parm = getData();
        if(parm == null)
            return -1;
        int count = columnList.size();
        for(int i = 0;i < count;i++)
        {
            Vector data = (Vector)parm.getData(columnList.getName(i));
            data.add(row,"");
        }
        indexID.add(row,getIndexMax());
        return row;
    }
    /**
     * ������
     * @return int
     */
    public int addRow()
    {
        TParm parm = getData();
        if(parm == null)
            return -1;
        int count = columnList.size();
        for(int i = 0;i < count;i++)
        {
            Vector data = (Vector)parm.getData(columnList.getName(i));
            data.add("");
        }
        indexID.add(getIndexMax());
        return getRowCount();
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TStore{\n");
        sb.append("  Columns [");
        sb.append(columnList);
        sb.append("]\n  PrimaryBuffer ");
        sb.append(getData());
        sb.append("]\n  indexID ");
        sb.append(indexID);
        sb.append("\n}");
        return sb.toString();
    }

    /*public int getColumnCount()
    {
        return data.get
    }*/
    /**
     *
     * <p>Title: ���б�</p>
     *
     * <p>Description: </p>
     *
     * <p>Copyright: Copyright (c) 2008</p>
     *
     * <p>Company: JavaHis</p>
     *
     * @author lzk 2008.8.27
     * @version 1.0
     */
    public class ColumnList
    {
        private Vector list;
        /**
         * ������
         */
        public ColumnList()
        {
            list = new Vector();
        }
        /**
         * ������
         * @param column Column
         */
        public void add(Column column)
        {
            list.add(column);
        }
        /**
         * �õ���
         * @param index int
         * @return Column
         */
        public Column get(int index)
        {
            return (Column)list.get(index);
        }
        /**
         * �õ���
         * @param name String
         * @return Column
         */
        public Column get(String name)
        {
            if(name == null || name.length() == 0)
                return null;
            int count = size();
            for(int i = 0;i < count;i ++)
            {
                Column column = get(i);
                if (name.equalsIgnoreCase(column.getName()))
                    return column;
            }
            return null;
        }
        /**
         * �õ�����
         * @param index int
         * @return String
         */
        public String getName(int index)
        {
            return get(index).getName();
        }
        /**
         * ɾ����
         * @param index int
         */
        public void remove(int index)
        {
            list.remove(index);
        }
        /**
         * ɾ����
         * @param name String
         */
        public void remove(String name)
        {
            if(name == null || name.length() == 0)
                return;
            int count = size();
            for(int i = 0;i < count;i ++)
                if(name.equalsIgnoreCase(get(i).getName()))
                {
                    remove(i);
                    return;
                }
        }
        /**
         * ����
         * @return int
         */
        public int size()
        {
            return list.size();
        }
        public String toString()
        {
            StringBuffer sb = new StringBuffer();
            int count = size();
            for(int i = 0;i < count;i++)
            {
                if(sb.length() > 0)
                    sb.append(",");
                sb.append(get(i).getName());
            }
            return sb.toString();
        }
    }
    /**
     *
     * <p>Title: �ж���</p>
     *
     * <p>Description: �ж���</p>
     *
     * <p>Copyright: Copyright (c) 2008</p>
     *
     * <p>Company: JavaHis</p>
     *
     * @author lzk 2008.8.27
     * @version 1.0
     */
    public class Column
    {
        /**
         * ����
         */
        private String name;
        /**
         * �õ�����
         */
        private Vector data;
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
         * ����������
         * @param data Vector
         */
        public void setData(Vector data)
        {
            this.data = data;
        }
        /**
         * �õ�������
         * @return Vector
         */
        public Vector getData()
        {
            return data;
        }
    }
    public static void main(String args[])
    {
        TParm parm = new TParm();
        parm.addData("ID",01);
        parm.addData("NAME","aaa");
        parm.addData("ID",02);
        parm.addData("NAME","bbb");
        //System.out.println("parm=" + parm);
        TStore store = new TStore(parm);
        store.addColumn("test");
        //store.removeColumn("id");
        store.addRow(0);
        store.addRow();
        System.out.println("store=" + store);
    }
}
