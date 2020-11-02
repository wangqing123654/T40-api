package com.dongyang.jdo;

import com.dongyang.util.Log;
import java.lang.reflect.Field;
import com.dongyang.util.RunClass;
import com.dongyang.data.TValue;
import com.dongyang.data.TParm;
import com.dongyang.util.StringTool;

public class TJDOObject
{
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * �õ����ձ�
     * "������:�ֶ���:OLD(��ѡ);������:�ֶ���"
     */
    private String mapString;
    /**
     * ������
     */
    public TJDOObject()
    {
        log = new Log();
    }
    /**
     * ���ö��ձ�
     * @param mapString String "������:�ֶ���:OLD(��ѡ);������:�ֶ���"
     */
    public void setMapString(String mapString)
    {
        this.mapString = mapString;
    }
    /**
     * �õ����ձ�
     * @return String "������:�ֶ���:OLD(��ѡ);������:�ֶ���"
     */
    public String getMapString()
    {
        return mapString;
    }
    public boolean initParm(TParm parm)
    {
        return initParm(parm,-1);
    }
    public boolean initParm(TParm parm,int row)
    {
        String mapString = getMapString();
        if(mapString == null || mapString.length() == 0)
            return false;
        String names[] = StringTool.parseLine(mapString,";");
        int count = names.length;
        for(int i = 0;i < count;i ++)
        {
            String ns[] = StringTool.parseLine(names[i],":");
            if(ns.length != 2)
                continue;
            String tag = ns[0];
            String name = ns[1];
            Object value = null;
            if(row == -1)
                value = parm.getData(name);
            else
                value = parm.getData(name,row);
            Field field = RunClass.findField(this,tag);
            try{
                Object obj = field.get(this);
                if(obj instanceof TValue)
                {
                    ((TValue) obj).setObject(value);
                    continue;
                }
                field.set(this, value);
            }catch(Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    public TParm getParm()
    {
        TParm result = new TParm();
        String mapString = getMapString();
        if(mapString == null || mapString.length() == 0)
            return result;
        String names[] = StringTool.parseLine(mapString,";");
        int count = names.length;
        for(int i = 0;i < count;i ++)
        {
            String ns[] = StringTool.parseLine(names[i],":");
            if(ns.length < 2)
                continue;
            String tag = ns[0];
            String name = ns[1];
            boolean getold = false;
            if(ns.length == 3)
                getold = "OLD".equalsIgnoreCase(ns[2]);
            Field field = RunClass.findField(this,tag);
            Object value = "";
            try{
                Object obj = field.get(this);
                if(obj instanceof TValue)
                    if(getold)
                        value = ((TValue) obj).getOldObject();
                    else
                        value = ((TValue) obj).getObject();
                else
                    value = obj;
                result.setData(name,value);
            }catch(Exception e)
            {
                e.printStackTrace();
                return result;
            }
        }
        return result;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName());
        sb.append("{");
        Field[] fields = getClass().getDeclaredFields();
        for(int i = 0;i < fields.length;i++)
        {
            fields[i].setAccessible(true);
            sb.append(fields[i].getName());
            sb.append("=");
            try{
                sb.append(fields[i].get(this));
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }
    /**
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        log.err(text);
    }
}
