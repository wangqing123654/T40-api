package com.dongyang.tui.text;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
/**
 *
 * <p>Title: ����������</p>
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
     * ������
     */
    private PM pm;
    /**
     * ����
     */
    Map map = new HashMap();
    /**
     * ����Code
     */
    Map mapCode = new HashMap();
    /**
     * ˢ�¿���
     */
    boolean isReset = true;
    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * ���ú�
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
     * ���ú�
     * @param name String ����
     * @param value String ��ʾֵ
     */
    public void setMicroField(String name,String value)
    {
        setMicroField(name,value,"");
    }
    /**
     * ���ú�ֵ
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
     * ���ú����
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
    * �õ���Code
    * @param name String ����
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
     * �õ���
     * @param name String ����
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
     * �õ�Vector
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
     * ����Vector
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
     * ���ԶԻ���
     */
    public void openPropertyDialog()
    {
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\MicroFieldDataEdit.x",this);
        if(value == null || !"OK".equals(value))
            return;
    }
}
