package com.dongyang.jdo;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.dongyang.data.TParm;
import com.dongyang.manager.TCM_Transform;
import java.util.ArrayList;
import java.sql.Timestamp;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: DataStore���϶���</p>
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
public class TDSObject
{
    /**
     * DS Map
     */
    private Map dsMap;
    /**
     * ����
     */
    private Map attribute;
    /**
     * ������Ϣ
     */
    private String err;
    /**
     * ���ݿ⹤��
     */
    private TJDODBTool dbTool = new TJDODBTool();
    /**
     * ������
     */
    public TDSObject()
    {
        dsMap = new HashMap();
    }
    /**
     * ����DS
     * @param name String
     * @return TDS
     */
    public TDS addDS(String name)
    {
        return addDS(name,"");
    }
    /**
     * ����DS
     * @param name String
     * @param sql String
     * @return TDS
     */
    public TDS addDS(String name,String sql)
    {
        TDS ds = new TDS();
        if(sql != null && sql.length() > 0)
            ds.setSQL(sql);
        addDS(name,ds);
        return ds;
    }
    /**
     * ����DS
     * @param name String
     * @param ds TDS
     */
    public void addDS(String name,TDS ds)
    {
        if(ds == null)
            return;
        ds.setParent(this);
        dsMap.put(name,ds);
        Iterator iterator = getAttributeIterator();
        if(iterator == null)
            return;
        while(iterator.hasNext())
        {
            String attributeName = (String)iterator.next();
            ds.setAttribute(attributeName,getAttribute(attributeName));
        }
    }
    /**
     * �õ�DS
     * @param name String
     * @return TDS
     */
    public TDS getDS(String name)
    {
        return(TDS)dsMap.get(name);
    }
    /**
     * �õ�DS�ĵ�����
     * @return Iterator
     */
    public Iterator getDSIterator()
    {
        return dsMap.keySet().iterator();
    }
    /**
     * �õ�DS����
     * @return int
     */
    public int getDSCount()
    {
        return dsMap.size();
    }
    /**
     * ɾ��DS
     * @param name String
     */
    public void removeDS(String name)
    {
        dsMap.remove(name);
    }
    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getErr()
    {
        return err;
    }
    /**
     * ����DS��SQL���
     * @param name String
     * @param s String
     * @return boolean
     */
    public boolean setDSSql(String name,String s)
    {
        err = "";
        if(s == null || s.length() == 0)
        {
            err = name + " ������Ч��SQL���";
            return false;
        }
        TDS ds = getDS(name);
        if(ds == null)
        {
            err = "û���ҵ� " + name + " DS";
            return false;
        }
        if(!ds.setSQL(s))
        {
            err = name + " setSQL err " + ds.getErrText();
            return false;
        }
        return true;
    }
    /**
     * ��ѯ
     * @return boolean
     */
    public boolean retrieve()
    {
        err = "";
        Iterator iterator = getDSIterator();
        if(iterator == null)
            return false;
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            TDS ds = this.getDS(name);
            if(ds == null)
                continue;
            if(ds.retrieve() < 0)
            {
                err = name + " retrieve err " + ds.getErrText();
                return false;
            }
        }
        return true;
    }
    /**
     * �õ����ݿ⹤��
     * @return TJDODBTool
     */
    public TJDODBTool getDBTool()
    {
        return dbTool;
    }
    /**
     * ���Ա�������
     * @return boolean
     */
    public boolean checkSave()
    {
        err = "";
        setAttribute("SAVE_TIME",getDBTool().getDBTime());
        setAttribute("SAVE_TIME_STRING",StringTool.getString((Timestamp)getAttribute("SAVE_TIME"),"yyyyMMddHHmmss"));
        Iterator iterator = getDSIterator();
        if(iterator == null)
            return false;
        while(iterator.hasNext())
        {
            String name = (String) iterator.next();
            TDS ds = this.getDS(name);
            if (ds == null)
                continue;
            if(!ds.checkSave())
            {
                err = "���Ա���" + name + "���� " + ds.getErrText();
                return false;
            }
        }
        removeAttribute("SAVE_TIME");
        removeAttribute("SAVE_TIME_STRING");
        return true;
    }
    /**
     * ����
     * @return boolean
     */
    public boolean update()
    {
        if(!checkSave())
            return false;
        err = "";
        String sql[] = new String[]{};
        Iterator iterator = getDSIterator();
        if(iterator == null)
            return false;
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            TDS ds = this.getDS(name);
            if(ds == null)
                continue;
            String[] tempSql = ds.getUpdateSQL();
            if(tempSql == null || tempSql.length == 0)
                continue;
            sql = StringTool.copyArray(sql,tempSql);
        }
        if(sql.length < 0)
            return true;
        //lx test
/*        for(int i=0;i<sql.length;i++){
        	System.out.println("-----SQL------"+sql[i]);
        }*/
        //lx 
        TParm result = new TParm(getDBTool().update(sql));
        if(result.getErrCode() < 0)
        {
            err = "update err " + result.getErrText();
            System.out.println(result.getErrText());
            System.out.println(StringTool.getString(sql));
            return false;
        }
        //����޸ļ�¼
        resetModify();
        return true;
    }
    /**
     * ����޸ļ�¼
     */
    public void resetModify()
    {
        Iterator iterator = getDSIterator();
        if(iterator == null)
            return;
        while(iterator.hasNext())
        {
            String name = (String) iterator.next();
            TDS ds = this.getDS(name);
            if (ds == null)
                continue;
            ds.resetModify();
        }
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
        Iterator iterator = getDSIterator();
        if(iterator == null)
            return;
        while(iterator.hasNext())
        {
            TDS ds = getDS((String)iterator.next());
            if(ds != null)
                ds.setAttribute(name,value);
        }
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
        Iterator iterator = getDSIterator();
        if(iterator == null)
            return;
        while(iterator.hasNext())
        {
            TDS ds = getDS((String)iterator.next());
            if(ds != null)
                ds.removeAttribute(name);
        }
    }
    /**
     * ��ʾ����(����)
     */
    public void showDebugAttribute()
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = getAttributeIterator();
        if(iterator == null)
            return;
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
     * ���ӹ۲���
     * @param name String
     * @param observer TObserver
     */
    public void addObserver(String name,TObserver observer)
    {
        TDS ds = getDS(name);
        if(ds != null)
            ds.addObserver(observer);
    }
    /**
     * ɾ���۲���
     * @param name String
     * @param observerName String
     */
    public void removeObserver(String name,String observerName)
    {
        TDS ds = getDS(name);
        if(ds != null)
            ds.removeObserver(observerName);
    }
}
