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
 * <p>Title: DataStore集合对象</p>
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
     * 属性
     */
    private Map attribute;
    /**
     * 错误信息
     */
    private String err;
    /**
     * 数据库工具
     */
    private TJDODBTool dbTool = new TJDODBTool();
    /**
     * 构造器
     */
    public TDSObject()
    {
        dsMap = new HashMap();
    }
    /**
     * 增加DS
     * @param name String
     * @return TDS
     */
    public TDS addDS(String name)
    {
        return addDS(name,"");
    }
    /**
     * 增加DS
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
     * 增加DS
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
     * 得到DS
     * @param name String
     * @return TDS
     */
    public TDS getDS(String name)
    {
        return(TDS)dsMap.get(name);
    }
    /**
     * 得到DS的迭代器
     * @return Iterator
     */
    public Iterator getDSIterator()
    {
        return dsMap.keySet().iterator();
    }
    /**
     * 得到DS个数
     * @return int
     */
    public int getDSCount()
    {
        return dsMap.size();
    }
    /**
     * 删除DS
     * @param name String
     */
    public void removeDS(String name)
    {
        dsMap.remove(name);
    }
    /**
     * 得到错误信息
     * @return String
     */
    public String getErr()
    {
        return err;
    }
    /**
     * 设置DS的SQL语句
     * @param name String
     * @param s String
     * @return boolean
     */
    public boolean setDSSql(String name,String s)
    {
        err = "";
        if(s == null || s.length() == 0)
        {
            err = name + " 设置无效的SQL语句";
            return false;
        }
        TDS ds = getDS(name);
        if(ds == null)
        {
            err = "没有找到 " + name + " DS";
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
     * 查询
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
     * 得到数据库工具
     * @return TJDODBTool
     */
    public TJDODBTool getDBTool()
    {
        return dbTool;
    }
    /**
     * 测试保存数据
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
                err = "测试保存" + name + "错误 " + ds.getErrText();
                return false;
            }
        }
        removeAttribute("SAVE_TIME");
        removeAttribute("SAVE_TIME_STRING");
        return true;
    }
    /**
     * 保存
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
        //清除修改记录
        resetModify();
        return true;
    }
    /**
     * 清除修改记录
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
     * 设置属性
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
     * 显示属性(调试)
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
     * 增加观察者
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
     * 删除观察者
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
