package com.dongyang.jdo;

import java.util.List;
import java.util.ArrayList;
import com.dongyang.util.StringTool;
import com.dongyang.data.TParm;
import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.manager.TCM_Transform;
import java.util.Iterator;
import com.dongyang.ui.TTable;
import com.dongyang.ui.TTableSort;
import com.dongyang.ui.base.TTableBase;
import com.dongyang.ui.base.TTableSortBase;

/**
 *
 * <p>Title: 数据对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.19
 * @version 1.0
 */
public class TDS extends TDataStore
{
    /**
     * 观察者列表
     */
    private List observerList;
    /**
     * 属性
     */
    private Map attribute;
    /**
     * 增加观察者
     * @param observer TObserver
     */
    public void addObserver(TObserver observer)
    {
        if(observerList == null)
        {
            observerList = new ArrayList();
        }
        if(existObserver(observer))
            return;
        observerList.add(observer);
    }
    /**
     * 得到观察者对象
     * @param index int
     * @return TObserver
     */
    public TObserver getObserver(int index)
    {
        if(index < 0 || index >= observerList.size())
            return null;
        return (TObserver)observerList.get(index);
    }
    /**
     * 得到观察者对象
     * @param name String
     * @return TObserver
     */
    public TObserver getObserver(String name)
    {
        return getObserver(findObserverIndex(name));
    }
    /**
     * 设置观察者对象
     * @param index int
     * @param observer TObserver
     */
    public void setObserver(int index,TObserver observer)
    {
        if(index < 0 || index >= observerList.size())
            return;
        observerList.set(index,observer);
    }
    /**
     * 设置观察者对象
     * @param name String
     * @param observer TObserver
     */
    public void setObserver(String name,TObserver observer)
    {
        int index = findObserverIndex(name);
        if(index == -1)
            return;
        observerList.set(index,observer);
    }
    /**
     * 删除观察者对象
     * @param index int
     */
    public void removeObserver(int index)
    {
        if(index < 0 || index >= observerList.size())
            return;
        observerList.remove(index);
        if(observerList.size() == 0)
            observerList = null;
    }
    /**
     * 删除观察者
     * @param observer TObserver
     */
    public void removeObserver(TObserver observer)
    {
        int index = findObserverIndex(observer);
        if(index == -1)
            return;
        removeObserver(index);
    }
    /**
     * 删除观察者
     * @param name String
     */
    public void removeObserver(String name)
    {
        if(name == null || name.length() == 0)
            return;
        int index = findObserverIndex(name);
        if(index == -1)
            return;
        removeObserver(index);
    }
    /**
     * 得到观察者个数
     * @return int
     */
    public int getObserverCount()
    {
        if(observerList == null)
            return 0;
        return observerList.size();
    }
    /**
     * 存在观察者
     * @param observer TObserver
     * @return boolean
     */
    public boolean existObserver(TObserver observer)
    {
        return findObserverIndex(observer) != -1;
    }
    /**
     * 存在观察者
     * @param name String
     * @return boolean
     */
    public boolean existObserver(String name){
        return findObserverIndex(name) != -1;
    }
    /**
     * 查询观察者位置
     * @param observer TObserver
     * @return int
     */
    private int findObserverIndex(TObserver observer)
    {
        if(observerList == null)
            return -1;
        for(int i = 0;i < observerList.size();i++)
            if(observer == observerList.get(i))
                return i;
        return -1;
    }
    /**
     * 查询观察者位置
     * @param name String
     * @return int
     */
    private int findObserverIndex(String name)
    {
        if(observerList == null)
            return -1;
        if(name == null||name.length() == 0)
            return -1;
        for(int i = 0;i < observerList.size();i++)
        {
            TObserver s = (TObserver)observerList.get(i);
            if (s == null)
                continue;
            if(name.equals(s.getName()))
                return i;
        }
        return -1;
    }

    /**
     * 插入行
     * @param row int
     * @return int
     */
    public int insertRow(int row)
    {
        int newRow = super.insertRow(row);
        //新增行默认值
        String names[] = getNewRowDefaultColumnNames();
        if(names.length > 0)
        {
            for(int j = 0;j < names.length;j++)
            {
                String aName = getNewRowDefaultColumn(names[j]);
                if(aName == null || aName.length() == 0)
                    continue;
                setItem(newRow,names[j], getAttribute(aName));
            }
        }
        //传递观察者
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).insertRow(this,newRow);
        return newRow;
    }
    /**
     * 设置项目
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setItem(int row,String column,Object value)
    {
        boolean b = super.setItem(row,column,value);
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).setItem(this,row,column,value);
        return b;
    }
    /**
     * 得到项目
     * @param row int
     * @param column String
     * @param dwbuffer String
     * @param originalvalue boolean
     * @return Object
     */
    public Object getItemData(int row,String column,String dwbuffer,boolean originalvalue)
    {
        Object result = super.getItemData(row,column,dwbuffer,originalvalue);
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).getItemData(this,row,column,dwbuffer,originalvalue);
        return result;
    }
    /**
     * 删除行
     * @param row int
     * @return boolean
     */
    public boolean deleteRow(int row)
    {
        boolean b = super.deleteRow(row);
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).deleteRow(this,row);
        return b;
    }
    /**
     * 得到更新SQL
     * @return String[]
     */
    public String[] getUpdateSQL()
    {
        String result[] = super.getUpdateSQL();
        for(int i = 0;i < getObserverCount();i++)
        {
            String s[] = getObserver(i).getUpdateSQL(this);
            if(s == null || s.length == 0)
                continue;
            result = StringTool.copyArray(result,s);
        }
        return result;
    }
    /**
     * 清空修改痕迹
     */
    public void resetModify()
    {
        super.resetModify();
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).resetModify(this);
    }
    /**
     * 得到其他项目
     * @param parm TParm
     * @param row int
     * @param column String
     * @return Object
     */
    public Object getOtherColumnValue(TParm parm,int row,String column)
    {
        for(int i = 0;i < getObserverCount();i++)
        {
            Object result = getObserver(i).getOtherColumnValue(this,parm,row,column);
            if(result != null)
                return result;
        }
        return "";
    }
    /**
     * 测试保存数据
     * @return boolean
     */
    public boolean checkSave()
    {
        //保存为新增行设置默认值
        onSaveNewRowDefaultColumn();
        //保存为修改行设置默认值
        onSaveModifiedRowDefaultColumn();
        for(int i = 0;i < getObserverCount();i++)
            if(!getObserver(i).checkSave(this))
                return false;
        return true;
    }
    /**
     * 保存为新增行设置默认值
     */
    public void onSaveNewRowDefaultColumn()
    {
        String names[] = getSaveNewRowDefaultColumnNames();
        if(names.length == 0)
            return;
        String storeName = isFilter()?FILTER:PRIMARY;
        int rows[] = getNewRows(storeName);
        if(rows.length == 0)
            return;
        for(int i = 0;i < rows.length;i++)
        {
            for(int j = 0;j < names.length;j++)
            {
                String aName = getSaveNewRowDefaultColumn(names[j]);
                if(aName == null || aName.length() == 0)
                    continue;
                setItem(rows[i],names[j], getAttribute(aName),storeName);
            }
        }
    }
    /**
     * 保存为修改行设置默认值
     */
    public void onSaveModifiedRowDefaultColumn()
    {
        String names[] = getSaveModifiedRowDefaultColumnNames();
        if(names.length == 0)
            return;
        String storeName = isFilter()?FILTER:PRIMARY;
        int rows[] = getModifiedRows(storeName);
        if(rows.length == 0)
            return;
        for(int i = 0;i < rows.length;i++)
        {
            for(int j = 0;j < names.length;j++)
            {
                String aName = getSaveModifiedRowDefaultColumn(names[j]);
                if(aName == null || aName.length() == 0)
                    continue;
                setItem(rows[i],names[j], getAttribute(aName),storeName);
            }
        }
    }
    /**
     * 设置其他项目
     * @param parm TParm
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setOtherColumnValue(TParm parm,int row,String column,Object value)
    {
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).setOtherColumnValue(this,parm,row,column,value);
        return true;
    }
    /**
     * 设置属性
     * @param name String
     * @param value Object
     */
    public void setAttribute(String name,Object value)
    {
        if(attribute == null)
            attribute = new HashMap();
        attribute.put(name,value);
    }
    /**
     * 得到属性
     * @param name String
     * @return Object
     */
    public Object getAttribute(String name)
    {
        if(attribute == null)
            return null;
        return attribute.get(name);
    }
    /**
     * 得到int属性
     * @param name String
     * @return int
     */
    public int getAttributeInt(String name)
    {
        return TCM_Transform.getInt(getAttribute(name));
    }
    /**
     * 得到String属性
     * @param name String
     * @return String
     */
    public String getAttributeString(String name)
    {
        return TCM_Transform.getString(getAttribute(name));
    }
    /**
     * 得到double属性
     * @param name String
     * @return double
     */
    public double getAttributeDouble(String name)
    {
        return TCM_Transform.getDouble(getAttribute(name));
    }
    /**
     * 得到boolean属性
     * @param name String
     * @return boolean
     */
    public boolean getAttributeBoolean(String name)
    {
        return TCM_Transform.getBoolean(getAttribute(name));
    }
    /**
     * 得到Timestamp属性
     * @param name String
     * @return Timestamp
     */
    public Timestamp getAttributeTimestamp(String name)
    {
        return TCM_Transform.getTimestamp(getAttribute(name));
    }
    /**
     * 得到TParm属性
     * @param name String
     * @return TParm
     */
    public TParm getAttributeTParm(String name)
    {
        return (TParm)getAttribute(name);
    }
    /**
     * 得到属性迭代器
     * @return Iterator
     */
    public Iterator getAttributeIterator()
    {
        if(attribute == null)
            return null;
        return attribute.keySet().iterator();
    }
    /**
     * 得到全部属性名
     * @return String[]
     */
    public String[] getAttributeNames()
    {
        Iterator iterator = getAttributeIterator();
        if(iterator == null)
            return new String[]{};
        List list = new ArrayList();
        while(iterator.hasNext())
            list.add(iterator.next());
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 存在属性
     * @param name String
     * @return boolean
     */
    public boolean existAttribute(String name)
    {
        if(name == null || name.length() == 0 || attribute == null)
            return false;
        String s[] = getAttributeNames();
        for(int i = 0;i < s.length;i++)
            if(name.equals(s[i]))
                return true;
        return false;
    }
    /**
     * 得到属性数据
     * @return Map
     */
    public Map getAttributeData()
    {
        return attribute;
    }
    /**
     * 设置属性数据
     * @param map Map
     */
    public void setAttributeData(Map map)
    {
        this.attribute = map;
    }
    /**
     * 得到属性个数
     * @return int
     */
    public int getAttributeCount()
    {
        if(attribute == null)
            return 0;
        return attribute.size();
    }
    /**
     * 删除属性
     * @param name String
     */
    public void removeAttribute(String name)
    {
        if(attribute == null)
            return;
        attribute.remove(name);
        if(attribute.size() == 0)
            attribute = null;
    }
    /**
     * 显示属性(调试)
     */
    public void showDebugAttribute()
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = getAttributeIterator();
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            sb.append(name + "=" + this.getAttribute(name) + ";");
        }
        System.out.println(sb.toString());
    }
    /**
     * 设置父类对象
     * @return Object
     */
    public Object getParent()
    {
        return getAttribute("parentObject");
    }
    /**
     * 得到父类对象
     * @param parent Object
     */
    public void setParent(Object parent)
    {
        setAttribute("parentObject",parent);
    }
    /**
     * 得到Table
     * @return TTable
     */
    public TTable getTable()
    {
        return (TTable)getAttribute("TTableObject");
    }
    /**
     * 设置Table
     * @param table TTable
     */
    public void setTable(TTableBase table)
    {
        setAttribute("TTableObject",table);
    }
    /**
     * 增加保存时新增行的默认列数据
     * @param columnName String
     * @param attributeName String
     */
    public void addSaveNewRowDefaultColumn(String columnName,String attributeName)
    {
        Map map = (Map)getAttribute("SaveNewRowDefaultColumn");
        if(map == null)
        {
            map = new HashMap();
            setAttribute("SaveNewRowDefaultColumn",map);
        }
        map.put(columnName,attributeName);
    }
    /**
     * 得到保存时新增行的默认列数据
     * @param columnName String
     * @return String
     */
    public String getSaveNewRowDefaultColumn(String columnName)
    {
        Map map = (Map)getAttribute("SaveNewRowDefaultColumn");
        if(map == null)
            return null;
        return (String)map.get(columnName);
    }
    /**
     * 得到保存时新增行的默认列数据迭代器
     * @return Iterator
     */
    public Iterator getSaveNewRowDefaultColumnIterator()
    {
        Map map = (Map)getAttribute("SaveNewRowDefaultColumn");
        if(map == null)
            return null;
        return map.keySet().iterator();
    }
    /**
     * 得到保存时新增行的默认列数据列名
     * @return String[]
     */
    public String[] getSaveNewRowDefaultColumnNames()
    {
        Iterator iterator = getSaveNewRowDefaultColumnIterator();
        if(iterator == null)
            return new String[]{};
        List list = new ArrayList();
        while(iterator.hasNext())
            list.add(iterator.next());
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 删除保存时新增行的默认列数据
     * @param columnName String
     */
    public void removeSaveNewRowDefaultColumn(String columnName)
    {
        Map map = (Map)getAttribute("SaveNewRowDefaultColumn");
        if(map == null)
            return;
        map.remove(columnName);
        if(map.size() == 0)
            removeAttribute("SaveNewRowDefaultColumn");
    }
    /**
     * 设置保存时新增行的默认列数据
     * @param s String
     */
    public void setSaveNewRowDefaultColumn(String s)
    {
        String data[] = StringTool.parseLine(s,";");
        for(int i = 0;i < data.length;i++)
        {
            String d[] = StringTool.parseLine(data[i],":");
            if(d.length == 0)
                continue;
            String columnName = d[0];
            String value = d[0];
            if(d.length == 2)
                value = d[1];
            addSaveNewRowDefaultColumn(columnName,value);
        }
    }
    /**
     * 增加保存时修改行的默认列数据
     * @param columnName String
     * @param attributeName String
     */
    public void addSaveModifiedRowDefaultColumn(String columnName,String attributeName)
    {
        Map map = (Map)getAttribute("SaveModifiedRowDefaultColumn");
        if(map == null)
        {
            map = new HashMap();
            setAttribute("SaveModifiedRowDefaultColumn",map);
        }
        map.put(columnName,attributeName);
    }
    /**
     * 得到保存时修改行的默认列数据
     * @param columnName String
     * @return String
     */
    public String getSaveModifiedRowDefaultColumn(String columnName)
    {
        Map map = (Map)getAttribute("SaveModifiedRowDefaultColumn");
        if(map == null)
            return null;
        return (String)map.get(columnName);
    }
    /**
     * 得到保存时修改行的默认列数据迭代器
     * @return Iterator
     */
    public Iterator getSaveModifiedRowDefaultColumnIterator()
    {
        Map map = (Map)getAttribute("SaveModifiedRowDefaultColumn");
        if(map == null)
            return null;
        return map.keySet().iterator();
    }
    /**
     * 得到保存时修改行的默认列数据列名
     * @return String[]
     */
    public String[] getSaveModifiedRowDefaultColumnNames()
    {
        Iterator iterator = getSaveModifiedRowDefaultColumnIterator();
        if(iterator == null)
            return new String[]{};
        List list = new ArrayList();
        while(iterator.hasNext())
            list.add(iterator.next());
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 删除保存时修改行的默认列数据
     * @param columnName String
     */
    public void removeSaveModifiedRowDefaultColumn(String columnName)
    {
        Map map = (Map)getAttribute("SaveModifiedRowDefaultColumn");
        if(map == null)
            return;
        map.remove(columnName);
        if(map.size() == 0)
            removeAttribute("SaveModifiedwRowDefaultColumn");
    }
    /**
     * 设置保存时修改行的默认列数据
     * @param s String
     */
    public void setSaveModifiedRowDefaultColumn(String s)
    {
        String data[] = StringTool.parseLine(s,";");
        for(int i = 0;i < data.length;i++)
        {
            String d[] = StringTool.parseLine(data[i],":");
            if(d.length == 0)
                continue;
            String columnName = d[0];
            String value = d[0];
            if(d.length == 2)
                value = d[1];
            addSaveModifiedRowDefaultColumn(columnName,value);
        }
    }
    /**
     * 增加新增行的默认列数据
     * @param columnName String
     * @param attributeName String
     */
    public void addNewRowDefaultColumn(String columnName,String attributeName)
    {
        Map map = (Map)getAttribute("NewRowDefaultColumn");
        if(map == null)
        {
            map = new HashMap();
            setAttribute("NewRowDefaultColumn",map);
        }
        map.put(columnName,attributeName);
    }
    /**
     * 得到新增行的默认列数据
     * @param columnName String
     * @return String
     */
    public String getNewRowDefaultColumn(String columnName)
    {
        Map map = (Map)getAttribute("NewRowDefaultColumn");
        if(map == null)
            return null;
        return (String)map.get(columnName);
    }
    /**
     * 得到新增行的默认列数据迭代器
     * @return Iterator
     */
    public Iterator getNewRowDefaultColumnIterator()
    {
        Map map = (Map)getAttribute("NewRowDefaultColumn");
        if(map == null)
            return null;
        return map.keySet().iterator();
    }
    /**
     * 得到新增行的默认列数据列名
     * @return String[]
     */
    public String[] getNewRowDefaultColumnNames()
    {
        Iterator iterator = getNewRowDefaultColumnIterator();
        if(iterator == null)
            return new String[]{};
        List list = new ArrayList();
        while(iterator.hasNext())
            list.add(iterator.next());
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 删除新增行的默认列数据
     * @param columnName String
     */
    public void removeNewRowDefaultColumn(String columnName)
    {
        Map map = (Map)getAttribute("NewRowDefaultColumn");
        if(map == null)
            return;
        map.remove(columnName);
        if(map.size() == 0)
            removeAttribute("NewRowDefaultColumn");
    }
    /**
     * 设置新增行的默认列数据
     * @param s String
     */
    public void setNewRowDefaultColumn(String s)
    {
        String data[] = StringTool.parseLine(s,";");
        for(int i = 0;i < data.length;i++)
        {
            String d[] = StringTool.parseLine(data[i],":");
            if(d.length == 0)
                continue;
            String columnName = d[0];
            String value = d[0];
            if(d.length == 2)
                value = d[1];
            addNewRowDefaultColumn(columnName,value);
        }
    }
    
    /**
     * 得到Table
     * @return TTable
     */
    public TTableSort getTableSort()
    {
        return (TTableSort)getAttribute("TTableObject");
    }
    /**
     * 设置Table
     * @param table TTable
     */
    public void setTableSort(TTableSortBase table)
    {
        setAttribute("TTableObject",table);
    }
 }
