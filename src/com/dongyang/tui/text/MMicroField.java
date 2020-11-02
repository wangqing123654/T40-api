package com.dongyang.tui.text;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
/**
 *
 * <p>Title: 带入宏管理器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.9.1
 * @version 1.0
 */
public class MMicroField
{
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 数据
     */
    Map map = new HashMap();
    /**
     * 数据Code
     */
    Map mapCode = new HashMap();
    /**
     * 刷新开关
     */
    boolean isReset = true;
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 设置宏
     * @param name String
     * @param value String
     * @param code String
     */
    public void setMicroField(String name,String value,String code)
    {
        if(name == null || name.length() == 0)
            return;
        boolean isChangeV = setMicroFieldValue(name, value);
        boolean isChangeC = setMicroFieldCode(name, code);
        if(isReset && (isChangeV || isChangeC))
        {
            getPM().getPageManager().resetData(new EAction(EAction.
                    SET_MICRO_FIELD));
            getPM().getPageManager().update();
        }
    }
    /**
     * 设置宏
     * @param name String 名称
     * @param value String 显示值
     */
    public void setMicroField(String name,String value)
    {
        setMicroField(name,value,"");
    }
    /**
     * 设置宏值
     * @param name String
     * @param value String
     * @return boolean
     */
    public boolean setMicroFieldValue(String name,String value){
        if(value == null)
        {
            map.remove(name);
            return false;
        }
        if(getMicroField(name).equals(value))
            return false;
        map.put(name,value);
        return true;
    }
    /**
     * 设置宏编码
     * @param name String
     * @param code String
     * @return boolean
     */
    public boolean setMicroFieldCode(String name,String code){
       if(code == null)
       {
           mapCode.remove(name);
           return false;
       }
       if(getMicroFieldCode(name).equals(code))
           return false;
       mapCode.put(name,code);
       return true;
   }
   /**
    * 得到宏Code
    * @param name String 名称
    * @return String
    */
   public String getMicroFieldCode(String name)
   {
       String value = (String)mapCode.get(name);
       if(value == null)
           value = "";
       return value;
    }
    /**
     * 得到宏
     * @param name String 名称
     * @return String
     */
    public String getMicroField(String name)
    {
        String value = (String)map.get(name);
        if(value == null)
            value = "";
        return value;
    }
    /**
     * 得到Vector
     * @return Vector
     */
    public Vector getVector()
    {
        Vector data = new Vector();
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext())
        {
            Vector v = new Vector();
            String name = (String)iterator.next();
            v.add(name);
            v.add(getMicroField(name));
            data.add(v);
        }
        return data;
    }
    /**
     * 设置Vector
     * @param v Vector
     */
    public void setVector(Vector v)
    {
        map = new HashMap();
        if(v == null)
            return;
        isReset = false;
        for(int i = 0;i < v.size();i++)
        {
            Vector data = (Vector)v.get(i);
            if(data.size() != 2)
                continue;
            String name = (String)data.get(0);
            String value = (String)data.get(1);
            if(value == null)
                value = "";
            setMicroField(name,value);
        }
        isReset = true;
        getPM().getPageManager().resetData(new EAction(EAction.SET_MICRO_FIELD));
        getPM().getPageManager().update();
    }
    /**
     * 属性对话框
     */
    public void openPropertyDialog()
    {
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\MicroFieldDataEdit.x",this);
        if(value == null || !"OK".equals(value))
            return;
    }
}
