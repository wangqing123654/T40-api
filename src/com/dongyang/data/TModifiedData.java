package com.dongyang.data;

import com.dongyang.util.StringTool;
import com.dongyang.util.RunClass;
import java.lang.reflect.Field;
import java.sql.Types;
import com.dongyang.util.Log;

public class TModifiedData implements ModifiedStatus
{
    private int modifiedCount;
    private TModifiedList listIO;
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
    public TModifiedData()
    {
        log = new Log();
    }
    public void setParentList(TModifiedList listIO)
    {
        this.listIO = listIO;
    }
    public TModifiedList getParentList()
    {
        return listIO;
    }
    public void addModify(TValue value)
    {
        modifiedCount ++;
        if(getParentList() != null)
            getParentList().modifyData(this);
    }
    public void removeModify(TValue value)
    {
        modifiedCount --;
        if(getParentList() != null)
            getParentList().modifyData(this);
    }
    public boolean isModified()
    {
        return modifiedCount > 0;
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
    /**
     * �õ����յ�������
     * @param name String ��������
     * @return String
     */
    public String getMapStringName(String name)
    {
        String mapString = getMapString();
        if(mapString == null || mapString.length() == 0)
        {
            if(getParentList() == null)
                return "";
            mapString = getParentList().getMapString();
            if(mapString == null || mapString.length() == 0)
                return "";
        }
        String names[] = StringTool.parseLine(mapString,";");
        int count = names.length;
        for(int i = 0;i < count;i ++)
        {
            String ns[] = StringTool.parseLine(names[i],":");
            if(ns.length != 2)
                continue;
            if(name.equalsIgnoreCase(ns[1]))
                return ns[0];
        }
        return "";
    }
    /**
     * �޸�����
     * @param name String ����
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean modify(String name,Object value)
    {
        //���ݿ���ת��Ϊ������
        name = getMapStringName(name);
        Field field = RunClass.findField(this,name);
        if(field == null)
        {
            err("İ������" + name);
            return false;
        }
        try{
            Object obj = field.get(this);
            if (!(obj instanceof TValue))
            {
                err(name + " is not TValue");
                return false;
            }
            ((TValue)obj).modifyObject(value);
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean initParm(TParm parm,int row)
    {
        String mapString = getMapString();
        if(mapString == null || mapString.length() == 0)
        {
            if(getParentList() == null)
            {
                err("getParentList()=null");
                return false;
            }
            mapString = getParentList().getMapString();
            if(mapString == null || mapString.length() == 0)
            {
                err("getMapString()=null");
                return false;
            }
        }
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
            if(field == null)
            {
                err("İ������" + tag);
                return false;
            }
            try{
                Object obj = field.get(this);
                if(obj instanceof TValue)
                {
                    ((TValue) obj).setObject(value);
                    continue;
                }
                if(value == null)
                {
                    if(field.getType() == Integer.class)
                        value = new Integer(0);
                    if(field.getType() == Double.class)
                        value = new Double(0.0);
                }
                if(value != null)
                {
                    if("java.lang.Long".equalsIgnoreCase(value.getClass().getName())
                       && "int".equals(field.getType().getName()))
                    {
                        long l = (Long)value;
                        value = (int)l;
                    }else if("java.lang.String".equalsIgnoreCase(value.getClass().getName())
                             && "boolean".equals(field.getType().getName()))
                        value = StringTool.getBoolean((String)value);
                }
                field.set(this, value);
            }catch(Exception e)
            {

                err("field:" + tag + ":" + name + " " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    /**
     * �޸Ĳ���
     * @param parm TParm
     * @return boolean
     */
    public boolean modifyParm(TParm parm)
    {
        return modifyParm(parm,-1);
    }
    /**
     * �޸Ĳ���
     * @param parm TParm
     * @param row int
     * @return boolean
     */
    public boolean modifyParm(TParm parm,int row)
    {
        String mapString = getMapString();
        if(mapString == null || mapString.length() == 0)
        {
            if(getParentList() == null)
                return false;
            mapString = getParentList().getMapString();
            if(mapString == null || mapString.length() == 0)
                return false;
        }
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
            if(field == null)
            {
                err("İ������" + tag);
                return false;
            }
            try{
                Object obj = field.get(this);
                if(!(obj instanceof TValue))
                    continue;
                ((TValue) obj).modifyObject(value);
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
        //System.out.println("=====mapString====="+mapString);
        
        if(mapString == null || mapString.length() == 0)
        {
            if(getParentList() == null)
                return result;
            mapString = getParentList().getMapString();
            if(mapString == null || mapString.length() == 0)
                return result;
        }
        String names[] = StringTool.parseLine(mapString,";");
        int count = names.length;
        for(int i = 0;i < count;i ++)
        {
            String ns[] = StringTool.parseLine(names[i],":");
            if(ns.length < 2)
                continue;
            String tag = ns[0];
            //System.out.println("=====tag====="+tag);
            String name = ns[1];
            //System.out.println("=====name====="+name);
            boolean getold = false;
            if(ns.length == 3)
                getold = "OLD".equalsIgnoreCase(ns[2]);
            Field field = RunClass.findField(this,tag);
            Object value = "";
            try{
                Object obj = field.get(this);
                if(obj instanceof TValue)
                {
                    if (getold)
                        value = ((TValue) obj).getOldObject();
                    else
                        value = ((TValue) obj).getObject();
                    if(value == null)
                    {
                        if(obj instanceof StringValue)
                            value = new TNull(Types.VARCHAR);
                        else if(obj instanceof TimestampValue)
                            value = new TNull(Types.TIMESTAMP);
                    }
                }
                else
                {
                    value = obj;
                    if(obj == null)
                        value = new TNull(field.getType());
                }
                result.setData(name,value);
            }catch(Exception e)
            {
                e.printStackTrace();
                return result;
            }
        }
        return result;
    }
    public void reset()
    {
        Field fields[] = getClass().getDeclaredFields();
        for(int i = 0;i < fields.length;i++)
        {
            fields[i].setAccessible(true);
            try{
                Object obj = fields[i].get(this);
                if (obj instanceof TValue)
                    ((TValue) obj).reset();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        modifiedCount = 0;
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
