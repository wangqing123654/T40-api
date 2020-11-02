package com.dongyang.data;

import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.util.StringTool;

public class TStore
{
    public static String PRIMARY = "Primary";
    /**
     * 缓冲区
     */
    private Map bufferList = new HashMap();
    /**
     * 索引行号
     */
    private Vector indexID = new Vector();
    /**
     * 最大索引号
     */
    private int indexMax = -1;
    /**
     * 列列表
     */
    private ColumnList columnList;
    /**
     * 构造器
     */
    public TStore()
    {
        setData(new TParm());
        onInit();
    }
    /**
     * 构造器
     * @param data TParm
     */
    public TStore(TParm data)
    {
        setData(data);
        onInit();
    }
    /**
     * 设置数据包
     * @param data TParm
     */
    public void setData(TParm data)
    {
        setData(PRIMARY,data);
    }
    /**
     * 设置数据包
     * @param name String 缓冲区名
     * @param data TParm
     */
    public void setData(String name,TParm data)
    {
        bufferList.put(name,data);
    }
    /**
     * 得到数据包
     * @param name String 缓冲区名
     * @return TParm
     */
    public TParm getData(String name)
    {
        return (TParm)bufferList.get(name);
    }
    /**
     * 得到珠缓冲区数据包
     * @return TParm
     */
    public TParm getData()
    {
        return getData(PRIMARY);
    }
    /**
     * 得到列个数
     * @return int
     */
    public int getColumnCount()
    {
        return columnList.size();
    }
    /**
     * 得到行数
     * @return int
     */
    public int getRowCount()
    {
        return getRowCount(PRIMARY);
    }
    /**
     * 得到行数
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
     * 初始化
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
     * 增加列
     * @param name String 列名
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
     * 增加列
     * @param name String 列名
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
     * 删除列
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
     * 删除列
     * @param name String 列名
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
     * 插入行
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
     * 新增行
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
     * <p>Title: 列列表</p>
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
         * 构造器
         */
        public ColumnList()
        {
            list = new Vector();
        }
        /**
         * 增加列
         * @param column Column
         */
        public void add(Column column)
        {
            list.add(column);
        }
        /**
         * 得到列
         * @param index int
         * @return Column
         */
        public Column get(int index)
        {
            return (Column)list.get(index);
        }
        /**
         * 得到列
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
         * 得到列名
         * @param index int
         * @return String
         */
        public String getName(int index)
        {
            return get(index).getName();
        }
        /**
         * 删除列
         * @param index int
         */
        public void remove(int index)
        {
            list.remove(index);
        }
        /**
         * 删除列
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
         * 个数
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
     * <p>Title: 列对象</p>
     *
     * <p>Description: 列对象</p>
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
         * 列名
         */
        private String name;
        /**
         * 得到数据
         */
        private Vector data;
        /**
         * 设置列名
         * @param name String
         */
        public void setName(String name)
        {
            this.name = name;
        }
        /**
         * 得到列名
         * @return String
         */
        public String getName()
        {
            return name;
        }
        /**
         * 设置列数据
         * @param data Vector
         */
        public void setData(Vector data)
        {
            this.data = data;
        }
        /**
         * 得到列数据
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
