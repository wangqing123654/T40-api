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
 * <p>Title: ���ݶ���</p>
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
     * �۲����б�
     */
    private List observerList;
    /**
     * ����
     */
    private Map attribute;
    /**
     * ���ӹ۲���
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
     * �õ��۲��߶���
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
     * �õ��۲��߶���
     * @param name String
     * @return TObserver
     */
    public TObserver getObserver(String name)
    {
        return getObserver(findObserverIndex(name));
    }
    /**
     * ���ù۲��߶���
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
     * ���ù۲��߶���
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
     * ɾ���۲��߶���
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
     * ɾ���۲���
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
     * ɾ���۲���
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
     * �õ��۲��߸���
     * @return int
     */
    public int getObserverCount()
    {
        if(observerList == null)
            return 0;
        return observerList.size();
    }
    /**
     * ���ڹ۲���
     * @param observer TObserver
     * @return boolean
     */
    public boolean existObserver(TObserver observer)
    {
        return findObserverIndex(observer) != -1;
    }
    /**
     * ���ڹ۲���
     * @param name String
     * @return boolean
     */
    public boolean existObserver(String name){
        return findObserverIndex(name) != -1;
    }
    /**
     * ��ѯ�۲���λ��
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
     * ��ѯ�۲���λ��
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
     * ������
     * @param row int
     * @return int
     */
    public int insertRow(int row)
    {
        int newRow = super.insertRow(row);
        //������Ĭ��ֵ
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
        //���ݹ۲���
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).insertRow(this,newRow);
        return newRow;
    }
    /**
     * ������Ŀ
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
     * �õ���Ŀ
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
     * ɾ����
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
     * �õ�����SQL
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
     * ����޸ĺۼ�
     */
    public void resetModify()
    {
        super.resetModify();
        for(int i = 0;i < getObserverCount();i++)
            getObserver(i).resetModify(this);
    }
    /**
     * �õ�������Ŀ
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
     * ���Ա�������
     * @return boolean
     */
    public boolean checkSave()
    {
        //����Ϊ����������Ĭ��ֵ
        onSaveNewRowDefaultColumn();
        //����Ϊ�޸�������Ĭ��ֵ
        onSaveModifiedRowDefaultColumn();
        for(int i = 0;i < getObserverCount();i++)
            if(!getObserver(i).checkSave(this))
                return false;
        return true;
    }
    /**
     * ����Ϊ����������Ĭ��ֵ
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
     * ����Ϊ�޸�������Ĭ��ֵ
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
     * ����������Ŀ
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
     * ��������
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
     * �õ�����
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
     * �õ�int����
     * @param name String
     * @return int
     */
    public int getAttributeInt(String name)
    {
        return TCM_Transform.getInt(getAttribute(name));
    }
    /**
     * �õ�String����
     * @param name String
     * @return String
     */
    public String getAttributeString(String name)
    {
        return TCM_Transform.getString(getAttribute(name));
    }
    /**
     * �õ�double����
     * @param name String
     * @return double
     */
    public double getAttributeDouble(String name)
    {
        return TCM_Transform.getDouble(getAttribute(name));
    }
    /**
     * �õ�boolean����
     * @param name String
     * @return boolean
     */
    public boolean getAttributeBoolean(String name)
    {
        return TCM_Transform.getBoolean(getAttribute(name));
    }
    /**
     * �õ�Timestamp����
     * @param name String
     * @return Timestamp
     */
    public Timestamp getAttributeTimestamp(String name)
    {
        return TCM_Transform.getTimestamp(getAttribute(name));
    }
    /**
     * �õ�TParm����
     * @param name String
     * @return TParm
     */
    public TParm getAttributeTParm(String name)
    {
        return (TParm)getAttribute(name);
    }
    /**
     * �õ����Ե�����
     * @return Iterator
     */
    public Iterator getAttributeIterator()
    {
        if(attribute == null)
            return null;
        return attribute.keySet().iterator();
    }
    /**
     * �õ�ȫ��������
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
     * ��������
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
     * �õ���������
     * @return Map
     */
    public Map getAttributeData()
    {
        return attribute;
    }
    /**
     * ������������
     * @param map Map
     */
    public void setAttributeData(Map map)
    {
        this.attribute = map;
    }
    /**
     * �õ����Ը���
     * @return int
     */
    public int getAttributeCount()
    {
        if(attribute == null)
            return 0;
        return attribute.size();
    }
    /**
     * ɾ������
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
     * ��ʾ����(����)
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
     * ���ø������
     * @return Object
     */
    public Object getParent()
    {
        return getAttribute("parentObject");
    }
    /**
     * �õ��������
     * @param parent Object
     */
    public void setParent(Object parent)
    {
        setAttribute("parentObject",parent);
    }
    /**
     * �õ�Table
     * @return TTable
     */
    public TTable getTable()
    {
        return (TTable)getAttribute("TTableObject");
    }
    /**
     * ����Table
     * @param table TTable
     */
    public void setTable(TTableBase table)
    {
        setAttribute("TTableObject",table);
    }
    /**
     * ���ӱ���ʱ�����е�Ĭ��������
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
     * �õ�����ʱ�����е�Ĭ��������
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
     * �õ�����ʱ�����е�Ĭ�������ݵ�����
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
     * �õ�����ʱ�����е�Ĭ������������
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
     * ɾ������ʱ�����е�Ĭ��������
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
     * ���ñ���ʱ�����е�Ĭ��������
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
     * ���ӱ���ʱ�޸��е�Ĭ��������
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
     * �õ�����ʱ�޸��е�Ĭ��������
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
     * �õ�����ʱ�޸��е�Ĭ�������ݵ�����
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
     * �õ�����ʱ�޸��е�Ĭ������������
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
     * ɾ������ʱ�޸��е�Ĭ��������
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
     * ���ñ���ʱ�޸��е�Ĭ��������
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
     * ���������е�Ĭ��������
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
     * �õ������е�Ĭ��������
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
     * �õ������е�Ĭ�������ݵ�����
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
     * �õ������е�Ĭ������������
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
     * ɾ�������е�Ĭ��������
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
     * ���������е�Ĭ��������
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
     * �õ�Table
     * @return TTable
     */
    public TTableSort getTableSort()
    {
        return (TTableSort)getAttribute("TTableObject");
    }
    /**
     * ����Table
     * @param table TTable
     */
    public void setTableSort(TTableSortBase table)
    {
        setAttribute("TTableObject",table);
    }
 }
