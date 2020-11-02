package com.dongyang.data.base;

import java.util.Map;
import com.dongyang.data.exception.TException;
import java.util.HashMap;
import com.dongyang.util.StringTool;
import java.util.Vector;
import com.dongyang.data.TParm;
import com.dongyang.ui.event.BaseEvent;
/**
 * 基础数据类型
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author lzk 2008.06.26 0:02
 * @version JavaHis 1.0
 */
public class TBaseData {
    /**
     * 数据包
     */
    public static final String DATA = "Data";
    /**
     * 属性包
     */
    public static final String ATTRIBUTE = "Attribute";
    /**
     * 属性类型
     */
    public static final String ATTRIBUTE_TYPE = "Type";
    /**
     * 属性尺寸
     */
    public static final String ATTRIBUTE_SIZE = "Size";
    /**
     * 属性是否初始化
     */
    public static final String ATTRIBUTE_IS_INIT = "isInit";
    /**
     * 属性原始数据
     */
    public static final String ATTRIBUTE_OLD_DATA = "oldData";
    /**
     * 属性值是否修改
     */
    public static final String ATTRIBUTE_MODIFIED = "modified";
    /**
     * 属性值是否是多值
     */
    public static final String ATTRIBUTE_MULTI = "multi";

    /**
     * 数据包
     */
    private TParm data;
    /**
     * 双缓冲
     */
    private boolean doubleBuffered;
    /**
     * 构造器
     * @throws TException
     */
    public TBaseData() throws TException
    {
        this(new TParm());
    }
    /**
     * 构造器
     * @param parm TParm
     * @throws TException
     */
    public TBaseData(TParm parm) throws TException
    {
        setData(parm);
    }
    /**
     * 构造器
     * @param map Map
     * @throws TException
     */
    public TBaseData(Map map) throws TException
    {
        setDataMap(map);
    }
    /**
     * 设置是否是双缓冲
     * @param doubleBuffered boolean true是双缓冲 false 不是双缓冲
     */
    public void setDoubleBuffered(boolean doubleBuffered)
    {
        this.doubleBuffered = doubleBuffered;
    }
    /**
     * 是否是双缓冲
     * @return boolean true是双缓冲 false 不是双缓冲
     */
    public boolean isDoubleBuffered()
    {
        return doubleBuffered;
    }
    /**
     * 设置数据包(TParm)
     * @param data TParm
     * @throws TException
     */
    public void setData(TParm data) throws TException
    {
        if(data == null)
            throw new TException("数据包为空");
        this.data = data;
    }
    /**
     * 得到数据包(TParm)
     * @return TParm
     */
    public TParm getData()
    {
        return data;
    }
    /**
     * 设置数据包(MAP)
     * @param map Map
     * @throws TException
     */
    public void setDataMap(Map map) throws TException
    {
        data = new TParm();
        if(map == null)
            throw new TException("数据包为空");
        data.setData(map);
    }
    /**
     * 得到数据包(MAP)
     * @return Map
     * @throws TException
     */
    public Map getDataMap() throws TException
    {
        if(getData() == null)
            throw new TException("数据包没有初始化");
        return data.getData();
    }
    /**
     * 得到属性包
     * @param name String 属性名
     * @return Object 属性包
     * @throws TException
     */
    public Map getAttributePackage(String name) throws TException
    {
        if(getData() == null)
            throw new TException("数据包没有初始化");
        if(name == null || name.trim().length() == 0)
            throw new TException("<" + name + ">属性名为空");
        Object value = getData().getData(ATTRIBUTE,name);
        if(value == null)
            return null;
        if(!(value instanceof Map))
            throw new TException("属性包数据类型错误");
        return (Map)value;
    }
    /**
     * 属性是否存在
     * @param name String 属性名
     * @return boolean true 存在 false 不存在
     * @throws TException
     */
    public boolean existAttribute(String name) throws TException
    {
        return getAttributePackage(name) != null;
    }
    /**
     * 设置属性类型
     * @param name String 属性名
     * @param type String 属性类型
     * @throws TException
     */
    public void setAttributeType(String name,String type) throws TException
    {
        setAttributeType(getAttributePackage(name),name,type);
    }
    /**
     * 设置属性类型
     * @param map Map 数据包
     * @param name String 属性名
     * @param type String 属性类型
     * @throws TException
     */
    public void setAttributeType(Map map,String name,String type) throws TException
    {
        if(type == null || type.trim().length() == 0)
            throw new TException("<" + name + ">属性类型为空");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        map.put(ATTRIBUTE_TYPE,type);
    }
    /**
     * 得到属性类型
     * @param name String 属性名
     * @return String 属性类型
     * @throws TException
     */
    public String getAttributeType(String name) throws TException
    {
        return getAttributeType(getAttributePackage(name),name);
    }
    /**
     * 得到属性类型
     * @param map Map 数据包
     * @param name String 属性名
     * @return String 属性类型
     * @throws TException
     */
    public String getAttributeType(Map map,String name) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Object value = map.get(ATTRIBUTE_TYPE);
        if(value == null)
            throw new TException("<" + name + ">属性类型为空");
        if(!(value instanceof String))
            throw new TException("<" + name + ">属性类型格式错误");
        return (String)value;
    }
    /**
     * 设置属性尺寸
     * @param name String 属性名
     * @param size int 属性尺寸
     * @throws TException
     */
    public void setAttributeSize(String name,int size) throws TException
    {
        setAttributeSize(getAttributePackage(name),name,size);
    }
    /**
     * 设置属性尺寸
     * @param map Map 数据包
     * @param name String 属性名
     * @param size int 属性尺寸
     * @throws TException
     */
    public void setAttributeSize(Map map,String name,int size) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        map.put(ATTRIBUTE_SIZE,size);
    }
    /**
     * 得到属性尺寸
     * @param name String 属性名
     * @return int 属性尺寸
     * @throws TException
     */
    public int getAttributeSize(String name) throws TException
    {
        return getAttributeSize(getAttributePackage(name),name);
    }
    /**
     * 得到属性尺寸
     * @param map Map 数据包
     * @param name String 属性名
     * @return int 属性尺寸
     * @throws TException
     */
    public int getAttributeSize(Map map,String name) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Object value = map.get(ATTRIBUTE_SIZE);
        if(value == null)
            return 0;
        if(!(value instanceof Integer))
            throw new TException("<" + name + ">属性尺寸格式错误");
        return (int)(Integer)value;
    }
    /**
     * 设置属性初始化
     * @param name String 属性名
     * @param isInit boolean 属性初始化
     * @throws TException
     */
    public void setAttributeInit(String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        setAttributeInit(getAttributePackage(name),name,isInit);
    }
    /**
     * 设置属性初始化
     * @param map Map 数据包
     * @param name String 属性名
     * @param isInit boolean 属性初始化
     * @throws TException
     */
    public void setAttributeInit(Map map,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        map.put(ATTRIBUTE_IS_INIT,isInit);
    }
    /**
     * 得到属性是否初始化
     * @param name String 属性名
     * @return boolean 否初始化
     * @throws TException
     */
    public boolean getAttributeInit(String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        return getAttributeInit(getAttributePackage(name),name);
    }
    /**
     * 得到属性是否初始化
     * @param map Map 数据包
     * @param name String 属性名
     * @return boolean 是否初始化
     * @throws TException
     */
    public boolean getAttributeInit(Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Object value = map.get(ATTRIBUTE_IS_INIT);
        if(value == null)
            return false;
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">属性初始化格式错误");
        return (boolean)(Boolean)value;
    }
    /**
     * 设置属性初始化
     * @param row int 行号
     * @param name String 属性名
     * @param isInit boolean 属性初始化
     * @throws TException
     */
    public void setAttributeInit(int row,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        setAttributeInit(row,getAttributePackage(name),name,isInit);
    }
    /**
     * 设置属性初始化
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @param isInit boolean 属性初始化
     * @throws TException
     */
    public void setAttributeInit(int row,Map map,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包的多值包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_IS_INIT);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_IS_INIT,data);
        }
        while(data.size() < row + 1)
            data.add(false);
        data.set(row,isInit);
    }
    /**
     * 插入属性初始化
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @param isInit boolean 属性初始化
     * @throws TException
     */
    public void insertAttributeInit(int row,Map map,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包的多值包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_IS_INIT);
        data.add(row,isInit);
    }
    /**
     * 得到属性是否初始化
     * @param row int 行号
     * @param name String 属性名
     * @return boolean 是否初始化
     * @throws TException
     */
    public boolean getAttributeInit(int row,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        return getAttributeInit(row,getAttributePackage(name),name);
    }
    /**
     * 得到属性是否初始化
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @return boolean 是否初始化
     * @throws TException
     */
    public boolean getAttributeInit(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_IS_INIT);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_IS_INIT,data);
        }
        while(data.size() < row + 1)
            data.add(false);
        Object value = data.get(row);
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">属性初始化格式错误");
        return (boolean)(Boolean)value;
    }
    /**
     * 删除属性是否初始化
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @throws TException
     */
    public void removeAttribteInit(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_IS_INIT);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_IS_INIT,data);
        }
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">属性包属性行越界 " + row);
        data.remove(row);
    }
    /**
     * 设置属性原始值
     * @param name String 属性名
     * @param data Object 原始值
     * @throws TException
     */
    public void setAttributeOldData(String name,Object data) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        setAttributeOldData(getAttributePackage(name),name,data);
    }
    /**
     * 设置属性原始值
     * @param map Map 数据包
     * @param name String 属性名
     * @param data Object 原始值
     * @throws TException
     */
    public void setAttributeOldData(Map map,String name,Object data) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        map.put(ATTRIBUTE_OLD_DATA,data);
    }
    /**
     * 得到属性原始值
     * @param name String 属性名
     * @return Object 原始值
     * @throws TException
     */
    public Object getAttributeOldData(String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        return getAttributeOldData(getAttributePackage(name),name);
    }
    /**
     * 得到属性原始值
     * @param map Map 数据包
     * @param name String 属性名
     * @return Object 原始值
     * @throws TException
     */
    public Object getAttributeOldData(Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            return null;
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        return map.get(ATTRIBUTE_OLD_DATA);
    }
    /**
     * 设置属性原始值
     * @param row int 行号
     * @param name String 属性名
     * @param data Object 原始值
     * @throws TException
     */
    public void setAttributeOldData(int row,String name,Object data) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        setAttributeOldData(row,getAttributePackage(name),name,data);
    }
    /**
     * 设置属性原始值
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @param value Object 原始值
     * @throws TException
     */
    public void setAttributeOldData(int row,Map map,String name,Object value) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_OLD_DATA);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_OLD_DATA,data);
        }
        while(data.size() < row + 1)
            data.add(null);
        data.set(row,value);
    }
    /**
     * 插入属性原始值
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @param value Object 原始值
     * @throws TException
     */
    public void insertAttributeOldData(int row,Map map,String name,Object value) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_OLD_DATA);
        data.add(row,value);
    }
    /**
     * 得到属性原始值
     * @param row int 行号
     * @param name String 属性名
     * @return Object 原始值
     * @throws TException
     */
    public Object getAttributeOldData(int row,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        return getAttributeOldData(row,getAttributePackage(name),name);
    }
    /**
     * 得到属性原始值
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @return Object 原始值
     * @throws TException
     */
    public Object getAttributeOldData(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_OLD_DATA);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_OLD_DATA,data);
        }
        while(data.size() < row + 1)
            data.add(false);
        return data.get(row);
    }
    /**
     * 删除属性原始值
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @throws TException
     */
    public void removeAttributeOldData(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_OLD_DATA);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_OLD_DATA,data);
        }
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">属性包属性行越界 " + row);
        data.remove(row);
    }
    /**
     * 设置属性值是否修改
     * @param name String 属性名
     * @param isModified boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public void setAttributeModified(String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        setAttributeModified(getAttributePackage(name),name,isModified);
    }
    /**
     * 设置属性值是否修改
     * @param map Map 数据包
     * @param name String 属性名
     * @param isModified boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public void setAttributeModified(Map map,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        map.put(ATTRIBUTE_MODIFIED,isModified);
    }
    /**
     * 得到属性值是否修改
     * @param name String 属性名
     * @return boolean boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public boolean getAttributeModified(String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        return getAttributeModified(getAttributePackage(name),name);
    }
    /**
     * 得到属性值是否修改
     * @param map Map 数据包
     * @param name String 属性名
     * @return boolean boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public boolean getAttributeModified(Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Object value = map.get(ATTRIBUTE_MODIFIED);
        if(value == null)
            return false;
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">属性修改格式错误");
        return (boolean)(Boolean)value;
    }
    /**
     * 设置属性值是否修改
     * @param row int 行号
     * @param name String 属性名
     * @param isModified boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public void setAttributeModified(int row,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        setAttributeModified(row,getAttributePackage(name),name,isModified);
    }
    /**
     * 设置属性值是否修改
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @param isModified boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public void setAttributeModified(int row,Map map,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_MODIFIED);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_MODIFIED,data);
        }
        while(data.size() < row + 1)
            data.add(false);
        data.set(row,isModified);
    }
    /**
     * 插入属性值是否修改
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @param isModified boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public void insertAttributeModified(int row,Map map,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_MODIFIED);
        data.add(row,isModified);
    }
    /**
     * 得到属性值是否修改
     * @param row int 行号
     * @param name String 属性名
     * @return boolean boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public boolean getAttributeModified(int row,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        return getAttributeModified(row,getAttributePackage(name),name);
    }
    /**
     * 得到属性值是否修改
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @return boolean boolean 属性值是否修改 true 修改过 false 没有修改过
     * @throws TException
     */
    public boolean getAttributeModified(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_MODIFIED);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_MODIFIED,data);
        }
        while(data.size() < row + 1)
            data.add(false);
        Object value = data.get(row);
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">属性修改格式错误");
        return (boolean)(Boolean)value;
    }
    /**
     * 删除属性值是否修改
     * @param row int 行号
     * @param map Map 数据包
     * @param name String 属性名
     * @throws TException
     */
    public void removeAttributeModified(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("没有启动双缓冲");
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Vector data = (Vector)map.get(ATTRIBUTE_MODIFIED);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_MODIFIED,data);
        }
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">属性包属性行越界 " + row);
        data.remove(row);
    }
    /**
     * 设置属性值是否是多值
     * @param name String 属性名
     * @param multi boolean 属性值是否是多值 true 多值 false 单值
     * @throws TException
     */
    public void setAttributeMulti(String name,boolean multi) throws TException
    {
        setAttributeMulti(getAttributePackage(name),name,multi);
    }
    /**
     * 设置属性值是否是多值
     * @param map Map 数据包
     * @param name String 属性名
     * @param multi boolean 属性值是否是多值 true 多值 false 单值
     * @throws TException
     */
    public void setAttributeMulti(Map map,String name,boolean multi) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        map.put(ATTRIBUTE_MULTI,multi);
    }
    /**
     * 得到属性值是否是多值
     * @param name String 属性名
     * @return boolean boolean 属性值是否是多值 true 多值 false 单值
     * @throws TException
     */
    public boolean getAttributeMulti(String name) throws TException
    {
        return getAttributeModified(getAttributePackage(name),name);
    }
    /**
     * 得到属性值是否是多值
     * @param map Map 数据包
     * @param name String 属性名
     * @return boolean boolean 属性值是否是多值 true 多值 false 单值
     * @throws TException
     */
    public boolean getAttributeMulti(Map map,String name) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        Object value = map.get(ATTRIBUTE_MULTI);
        if(value == null)
            return false;
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">属性多值格式错误");
        return (boolean)(Boolean)value;
    }
    /**
     * 增加属性
     * @param name String 属性名
     * @param type String 属性类型
     * @throws TException
     */
    public void addAttribute(String name,String type) throws TException
    {
        if(existAttribute(name))
            throw new TException("<" + name + ">属性已经存在");
        Map map = new HashMap();
        addAttribute(map,name,type);
        getData().setData(ATTRIBUTE,name,map);
    }
    /**
     * 删除属性
     * @param name String
     * @throws TException
     */
    public void removeAttribute(String name)throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        getData().removeData(ATTRIBUTE,name);
        getData().removeData(name);
    }
    /**
     * 得到属性个数
     * @return int 属性个数
     * @throws TException
     */
    public int attributeCount() throws TException
    {
        if(getData() == null)
            throw new TException("数据包没有初始化");
        Map map = getData().getGroupData(ATTRIBUTE);
        if(map == null)
            return 0;
        return map.size();
    }
    /**
     * 增加属性
     * @param map Map 属性包
     * @param name String 属性名
     * @param type String 属性类型
     * @throws TException
     */
    public void addAttribute(Map map,String name,String type) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">属性包数据为空");
        if(name == null || name.trim().length() == 0)
            throw new TException("<" + name + ">属性名为空");
        if(type == null || type.trim().length() == 0)
            throw new TException("<" + name + ">属性类型为空");
        map.put(ATTRIBUTE_TYPE,type);
    }
    /**
     * 比较两个对象是否相同
     * @param obj1 Object
     * @param obj2 Object
     * @return boolean
     */
    public boolean equalsValue(Object obj1,Object obj2)
    {
        if(obj1 == null && obj2 == null)
            return true;
        if(obj1 != null)
            return obj1.equals(obj2);
        else
            return obj2.equals(obj1);
    }
    /**
     * 检测String值得长度是否合法
     * @param type String 类型
     * @param attributeMap Map 属性数据
     * @param name String 名称
     * @param value String 值
     * @throws TException
     */
    private void checkAttributeValueLength(String type,Map attributeMap,String name,
                                   Object value) throws TException
    {
        if(!"String".equals(type))
            return;
        //得到属性尺寸
        int size = getAttributeSize(attributeMap,name);
        if(size > 0 && value != null && ((String)value).length() > size)
            throw new TException("<" + name + ">属性值'" + value + "'超出长度" + size);
    }
    /**
     * 检测值类型是否合法
     * @param type String
     * @param name String
     * @param value Object
     * @throws TException
     */
    private void checkAttributeValueType(String type,String name,Object value)
            throws TException
    {
        if(!StringTool.equalsObjectType(type,value))
            throw new TException("<" + name + ">属性附值类型错误");
    }
    /**
     * 属性双缓冲处理单值
     * @param attributeMap Map 属性包
     * @param name String 名称
     * @param value Object 值
     * @throws TException
     */
    public void doubleBufferedValue(Map attributeMap,String name,Object value)
            throws TException
    {
        if(!isDoubleBuffered())
            return;
        //是否初始化属性
        if(!getAttributeInit(attributeMap,name))
        {
            setAttributeInit(attributeMap, name, true);
            //设置属性原始值
            setAttributeOldData(attributeMap,name,value);
        }else
        {
            Object oldValue = getAttributeOldData(attributeMap,name);
            //设置修改状态
            setAttributeModified(attributeMap,name,!equalsValue(oldValue,value));
        }
    }
    /**
     * 属性双缓冲处理多值
     * @param row int 行号
     * @param attributeMap Map 属性包
     * @param name String 名称
     * @param value Object 值
     * @throws TException
     */
    public void setDoubleBufferedValue(int row,Map attributeMap,String name,Object value)
            throws TException
    {
        if(!isDoubleBuffered())
            return;
        //是否初始化属性
        if(!getAttributeInit(row,attributeMap,name))
        {
            setAttributeInit(row,attributeMap, name, true);
            //设置属性原始值
            setAttributeOldData(row,attributeMap,name,value);
        }else
        {
            Object oldValue = getAttributeOldData(row,attributeMap,name);
            //设置修改状态
            setAttributeModified(row,attributeMap,name,!equalsValue(oldValue,value));
        }
    }
    /**
     * 属性双缓冲处理多值插入
     * @param row int 行号
     * @param attributeMap Map 属性包
     * @param name String 名称
     * @param value Object 值
     * @throws TException
     */
    public void insertDoubleBufferedValue(int row,Map attributeMap,String name,Object value)
            throws TException
    {
        if(!isDoubleBuffered())
            return;
        //插入初始化状态
        insertAttributeInit(row,attributeMap, name, true);
        //插入属性原始值
        insertAttributeOldData(row,attributeMap,name,value);
        //插入属性修改状态
        insertAttributeModified(row,attributeMap,name,false);
    }
    /**
     * 属性双缓冲处理多值为空
     * @param row int
     * @param attributeMap Map
     * @param name String
     * @throws TException
     */
    public void setDoubleBufferedValue(int row,Map attributeMap,String name)
            throws TException
    {
        if(!isDoubleBuffered())
            return;
        //插入初始化状态
        insertAttributeInit(row,attributeMap, name, false);
        //插入属性原始值
        insertAttributeOldData(row,attributeMap,name,null);
        //插入属性修改状态
        insertAttributeModified(row,attributeMap,name,false);
    }
    /**
     * 设置属性值
     * @param name String 名称
     * @param value Object 值
     * @throws TException
     */
    public void setAttributeValue(String name,Object value) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        //得到属性包
        Map attributeMap = this.getAttributePackage(name);
        if(getAttributeMulti(attributeMap,name))
            throw new TException("<" + name + ">属性是多值属性");
        //得到属性类型
        String type = getAttributeType(attributeMap,name);
        //检测值类型是否合法
        checkAttributeValueType(type,name,value);
        //检测值得长度是否合法
        checkAttributeValueLength(type,attributeMap,name,value);
        //出发事件
        eventAttributeListener("SET",0,name,value,getAttributeOldData(attributeMap,name));
        //属性双缓冲处理单值
        doubleBufferedValue(attributeMap,name,value);
        //保存值
        getData().setData(DATA,name,value);
    }
    /**
     * 得到属性值
     * @param name String 属性名
     * @return Object 属性值
     * @throws TException
     */
    public Object getAttributeValue(String name) throws TException
    {
        if(getData() == null)
            throw new TException("数据包没有初始化");
        if(name == null || name.trim().length() == 0)
            throw new TException(name + "属性名为空");
        if(!existAttribute(name))
            throw new TException(name + "属性名不存在");
        return getData().getData(DATA,name);
    }
    /**
     * 得到多值属性的数据包
     * @param name String 名称
     * @return Vector 数据包
     * @throws TException
     */
    public Vector getAttributeMultiVector(String name)throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        return getAttributeMultiVector(attributeMap,name);
    }
    /**
     * 得到多值属性的数据包
     * @param attributeMap Map 属性包
     * @param name String 名称
     * @return Vector 数据包
     * @throws TException
     */
    public Vector getAttributeMultiVector(Map attributeMap,String name)throws TException
    {
        if(!getAttributeMulti(attributeMap,name))
            throw new TException("<" + name + ">属性不是多值属性");
        Object value = getData().getData(DATA,name);
        if(value == null)
        {
            value = new Vector();
            getData().setData(DATA,name,value);
        }
        if(!(value instanceof Vector))
            throw new TException("<" + name + ">属性值类型错误");
        return (Vector)value;
    }
    /**
     * 增加值值
     * @param name String 名称
     * @param value Object 值
     * @throws TException
     */
    public void addAttributeValue(String name,Object value) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        //得到多值属性的数据包
        Vector data = getAttributeMultiVector(attributeMap,name);
        //得到属性类型
        String type = getAttributeType(attributeMap,name);
        //检测值类型是否合法
        checkAttributeValueType(type,name,value);
        //检测值得长度是否合法
        checkAttributeValueLength(type,attributeMap,name,value);
        //属性双缓冲处理多值
        setDoubleBufferedValue(data.size(),attributeMap,name,value);
        //增加值
        data.add(value);
        //出发事件
        eventAttributeListener("SET",data.size() - 1,name,value,getAttributeOldData(attributeMap,name));
    }
    /**
     * 设置属性值
     * @param row int 行号
     * @param name String 名称
     * @param value Object 值
     * @throws TException
     */
    public void setAttributeValue(int row,String name,Object value) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        //得到多值属性的数据包
        Vector data = getAttributeMultiVector(attributeMap,name);
        //得到属性类型
        String type = getAttributeType(attributeMap,name);
        //检测值类型是否合法
        checkAttributeValueType(type,name,value);
        //检测值得长度是否合法
        checkAttributeValueLength(type,attributeMap,name,value);
        //属性双缓冲处理多值
        setDoubleBufferedValue(row,attributeMap,name,value);
        while(data.size() < row + 1)
            data.add(null);
        //增加值
        data.set(row,value);
        //出发事件
        eventAttributeListener("SET",row,name,value,getAttributeOldData(attributeMap,name));
    }
    /**
     * 得到属性值
     * @param row int 行号
     * @param name String 名称
     * @return Object 值
     * @throws TException
     */
    public Object getAttributeValue(int row,String name) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        //得到多值属性的数据包
        Vector data = getAttributeMultiVector(attributeMap,name);
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">属性行越界 " + row);
        return data.get(row);
    }
    /**
     * 插入属性值
     * @param row int 行号
     * @param name String 名称
     * @param value Object 值
     * @throws TException
     */
    public void addAttributeValue(int row,String name,Object value) throws TException
    {
        if(getAttributeCount(name) <= row)
        {
            addAttributeValue(name,value);
            return;
        }
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        //得到多值属性的数据包
        Vector data = getAttributeMultiVector(attributeMap,name);
        //得到属性类型
        String type = getAttributeType(attributeMap,name);
        //检测值类型是否合法
        checkAttributeValueType(type,name,value);
        //检测值得长度是否合法
        checkAttributeValueLength(type,attributeMap,name,value);
        //属性双缓冲处理多值插入
        insertDoubleBufferedValue(row,attributeMap,name,value);
        data.add(row,value);
        //出发事件
        eventAttributeListener("SET",row,name,value,getAttributeOldData(attributeMap,name));
    }
    /**
     * 插入属性值(空值)
     * @param row int 行号
     * @param name String 名称
     * @throws TException
     */
    public void addAttributeValue(int row,String name) throws TException
    {
        if(getAttributeCount(name) <= row)
        {
            addAttributeValue(name);
            return;
        }
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        //得到多值属性的数据包
        Vector data = getAttributeMultiVector(attributeMap,name);
        //属性双缓冲处理多值插入
        setDoubleBufferedValue(row,attributeMap,name);
        data.add(row,null);
    }
    /**
     * 增加值值(空值)
     * @param name String 名称
     * @throws TException
     */
    public void addAttributeValue(String name) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        //得到多值属性的数据包
        Vector data = getAttributeMultiVector(attributeMap,name);
        //属性双缓冲处理多值
        setDoubleBufferedValue(data.size(),attributeMap,name);
        data.add(null);
    }
    /**
     * 删除属性值
     * @param row int 行号
     * @param name String 名称
     * @throws TException
     */
    public void removeAttributeValue(int row,String name) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">属性不存在");
        //得到属性包
        Map attributeMap = getAttributePackage(name);
        //得到多值属性的数据包
        Vector data = getAttributeMultiVector(attributeMap,name);
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">属性行越界 " + row);
        removeAttribteInit(row,attributeMap,name);
        removeAttributeOldData(row,attributeMap,name);
        removeAttributeModified(row,attributeMap,name);
        data.remove(row);
    }
    /**
     * 得到多值属性值得个数
     * @param name String 名称
     * @return int 得个数
     * @throws TException
     */
    public int getAttributeCount(String name) throws TException
    {
        return getAttributeMultiVector(name).size();
    }
    /**
     * 属性事件
     * @param event String 时间名称
     * @param row int 行号
     * @param name String 名称
     * @param value Object 值
     * @param oldValue Object 旧值
     */
    public void eventAttributeListener(String event,int row,String name,Object value,Object oldValue)
    {

    }
}
