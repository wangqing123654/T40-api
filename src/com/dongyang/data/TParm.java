package com.dongyang.data;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import com.dongyang.manager.TCM_Transform;
import java.util.StringTokenizer;
import com.dongyang.util.StringTool;
import java.sql.Timestamp;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.io.DSerializable;
import com.dongyang.util.FileTool;
import com.dongyang.util.TypeTool;
import com.dongyang.util.RunClass;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class TParm implements DSerializable
{
    private Map data;
    public static final String DEFAULT_GROUP = "Data";
    public static final String ERR_GROUP = "Err";
    public static final String LISTENER_GROUP = "Listener";
    /**
     * ������
     */
    public TParm()
    {
        setData(new HashMap());
    }

    /**
     * ������
     * @param data Map
     */
    public TParm(Map data)
    {
        setData(data);
    }

    /**
     * �õ�ȫ������
     * @return Map
     */
    public Map getData()
    {
        return data;
    }

    /**
     * ����ȫ������
     * @param data Map
     */
    public void setData(Map data)
    {
        this.data = data;
    }

    /**
     * �õ�������
     * @param name String ����
     * @return Map
     */
    public Map getGroupData(String name)
    {
        if(data == null)
            return null;
        return (Map) data.get(name);
    }

    /**
     * ɾ��������
     * @param name String
     * @return Map
     */
    public Map removeGroupData(String name)
    {
        return (Map) data.remove(name);
    }

    /**
     * ����������
     * @param name String ����
     * @param value Map
     */
    public void setGroupData(String name, Map value)
    {
        data.put(name, value);
    }

    /**
     * �õ�Ĭ��������
     * @param name String ����
     * @return Object
     */
    public Object getData(String name)
    {
        return getData(DEFAULT_GROUP, name);
    }
    /**
     * �õ�����
     * @param column int
     * @return Object
     */
    public Object getData(int column)
    {
        String columnName = getValue("SYSTEM","COLUMNS",column);
        if(columnName == null || columnName.length() == 0)
            return null;
        return getData(columnName);
    }
    /**
     * �õ�ָ��������
     * @param group String ����
     * @param name String ����
     * @return Object
     */
    public Object getData(String group, String name)
    {
        Map map = getGroupData(group);
        if (map == null)
        {
            return null;
        }
        return map.get(name);
    }

    /**
     * ����Ĭ��������
     * @param name String ����
     * @param data Object
     */
    public void setData(String name, Object data)
    {
        setData(DEFAULT_GROUP, name, data);
    }

    /**
     * ����ָ��������
     * @param group String ����
     * @param name String ����
     * @param data Object
     */
    public void setData(String group, String name, Object data)
    {
        Map map = getGroupData(group);
        if (map == null)
        {
            map = new HashMap();
            setGroupData(group, map);
        }
        map.put(name, data);
    }

    /**
     * ����Ĭ��������(�������Ϊ�ղ�����)
     * @param name String ����
     * @param data Object
     */
    public void setDataN(String name, Object data)
    {
        setDataN(DEFAULT_GROUP, name, data);
    }

    /**
     * ����ָ��������(�������Ϊ�ղ�����)
     * @param group String ����
     * @param name String ����
     * @param data Object
     */
    public void setDataN(String group, String name, Object data)
    {
        if (TCM_Transform.isNull(data))
        {
            return;
        }
        setData(group, name, data);
    }

    /**
     * ��������(�������Ϊ�ղ�����)
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @param data Object
     */
    public void setDataN(String group, String name, int row, Object data)
    {
        if (TCM_Transform.isNull(data))
        {
            return;
        }
        setData(group, name, row, data);
    }

    /**
     * ��������(�������Ϊ�ղ�����)
     * @param name String ����
     * @param row int �к�
     * @param data Object
     */
    public void setDataN(String name, int row, Object data)
    {
        setDataN(DEFAULT_GROUP, name, row, data);
    }

    /**
     * �õ����Ա�б�
     * @return String[]
     */
    public String[] getGroupNames()
    {
        return (String[]) data.keySet().toArray(new String[]
                                                {});
    }

    /**
     * �õ�Ĭ�����ڳ�Ա�б�
     * @return String[]
     */
    public String[] getNames()
    {
        return getNames(DEFAULT_GROUP);
    }

    /**
     * �õ�ָ�����Ա�б�
     * @param group String ����
     * @return String[]
     */
    public String[] getNames(String group)
    {
        Map map = getGroupData(group);
        if (map == null)
        {
            return new String[]
                    {};
        }
        return (String[]) map.keySet().toArray(new String[]
                                               {});
    }

    /**
     * ��������
     * @param name String ����
     * @param row int �к�
     * @param data Object
     */
    public void setData(String name, int row, Object data)
    {
        setData(DEFAULT_GROUP, name, row, data);
    }

    /**
     * ���Ӷ�������
     * @param name String ����
     * @param data Object
     */
    public void addData(String name, Object data)
    {
        addData(DEFAULT_GROUP, name, data);
    }

    /**
     * ���Ӷ�������
     * @param group String ����
     * @param name String ����
     * @param data Object
     */
    public void addData(String group, String name, Object data)
    {
        Object obj = getData(group, name);
        if (obj == null)
        {
            obj = new Vector();
            setData(group, name, obj);
        }
        if (obj instanceof Vector)
        {
            Vector v = (Vector) obj;
            v.add(data);
            return;
        }
        Vector v = new Vector();
        v.add(obj);
        v.add(data);
        setData(group, name, v);
    }

    /**
     * ��������
     * @param name String ����
     * @param row int �к�
     * @param data Object
     * @return int
     */
    public int insertData(String name, int row, Object data)
    {
        return insertData(DEFAULT_GROUP, name, row, data);
    }

    /**
     * ��������
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @param data Object
     * @return int
     */
    public int insertData(String group, String name, int row, Object data)
    {
        Object obj = getData(group, name);
        if (obj == null)
        {
            obj = new Vector();
            setData(group, name, obj);
        }
        if (obj instanceof Vector)
        {
            Vector v = (Vector) obj;
            if (row < 0 || row >= v.size())
            {
                v.add(data);
                return v.size() - 1;
            } else
            {
                v.add(row, data);
                return row;
            }
        }
        int r = row;
        Vector v = new Vector();
        v.add(obj);
        if (row < 0 || row >= v.size())
        {
            v.add(data);
            r = v.size() - 1;
        } else
        {
            v.add(row, data);
        }
        setData(group, name, v);
        return r;
    }

    /**
     * ��������
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @param data Object
     */
    public void setData(String group, String name, int row, Object data)
    {
        Object obj = getData(group, name);
        if (obj == null)
        {
            obj = new Vector();
            setData(group, name, obj);
        }
        if (obj instanceof Vector)
        {
            Vector v = (Vector) obj;
            if (row >= v.size())
            {
                v.add(data);
            } else
            {
                v.set(row, data);
            }
            return;
        }
        Vector v = new Vector();
        v.add(obj);
        v.add(data);
        setData(group, name, v);
    }

    /**
     * �õ�����
     * @param name String ����
     * @param row int �к�
     * @return Object
     */
    public Object getData(String name, int row)
    {
        return getData(DEFAULT_GROUP, name, row);
    }
    /**
     * �õ�����
     * @param row int
     * @param column int
     * @return Object
     */
    public Object getData(int row,int column)
    {
        String columnName = getValue("SYSTEM","COLUMNS",column);
        if(columnName == null || columnName.length() == 0)
            return null;
        return getData(columnName,row);
    }
    /**
     * �õ�����
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return Object
     */
    public Object getData(String group, String name, int row)
    {
        Object obj = getData(group, name);
        if (obj == null)
        {
            return null;
        }
        if (!(obj instanceof Vector))
        {
            return null;
        }
        Vector value = (Vector) obj;
        if (row >= value.size())
        {
            return null;
        }
        try{
            return value.get(row);
        }catch(Exception e)
        {
            System.out.println("group=" + group + " name=" + name + " row=" + row);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ɾ������
     * @param name String ����
     */
    public void removeData(String name)
    {
        removeData(DEFAULT_GROUP, name);
    }

    /**
     * ɾ������
     * @param group String ����
     * @param name String ����
     */
    public void removeData(String group, String name)
    {
        Map map = getGroupData(group);
        if (map == null)
        {
            return;
        }
        map.remove(name);
    }

    /**
     * �õ����ؽ��������
     * @return int
     */
    public int getCount()
    {
        if (!existGroup("ACTION"))
        {
            return -1;
        }
        return getInt("ACTION", "COUNT");
    }
    /**
     * ��������
     * @param count int
     */
    public void setCount(int count)
    {
        setData("ACTION","COUNT",count);
    }
    /**
     * �õ����ݸ���
     * @param name String ����
     * @return int
     */
    public int getCount(String name)
    {
        return getCount(DEFAULT_GROUP, name);
    }

    /**
     * �õ����ݸ���
     * @param group String ����
     * @param name String ����
     * @return int
     */
    public int getCount(String group, String name)
    {
        Object obj = getData(group, name);
        if (obj == null)
        {
            return -1;
        }
        if (!(obj instanceof Vector))
        {
            return -1;
        }
        Vector value = (Vector) obj;
        return value.size();
    }

    /**
     * �õ�����ֵ
     * @param name String ����
     * @return int ֵ
     */
    public int getInt(String name)
    {
        return getInt(DEFAULT_GROUP, name);
    }

    /**
     * �õ�����ֵ
     * @param group String ����
     * @param name String ����
     * @return int ֵ
     */
    public int getInt(String group, String name)
    {
        return TCM_Transform.getInt(getData(group, name));
    }

    /**
     * �õ�����ֵ
     * @param name String ����
     * @param row int �к�
     * @return int ֵ
     */
    public int getInt(String name, int row)
    {
        return getInt(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�����ֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return int ֵ
     */
    public int getInt(String group, String name, int row)
    {
        return TCM_Transform.getInt(getData(group, name, row));
    }
    /**
     * �õ�intֵ
     * @param row int
     * @param column int
     * @return int
     */
    public int getInt(int row,int column)
    {
        return TypeTool.getInt(getData(row,column));
    }

    /**
     * �õ�������ֵ
     * @param name String ����
     * @return long ֵ
     */
    public long getLong(String name)
    {
        return getLong(DEFAULT_GROUP, name);
    }

    /**
     * �õ�������ֵ
     * @param group String ����
     * @param name String ����
     * @return long ֵ
     */
    public long getLong(String group, String name)
    {
        return TCM_Transform.getLong(getData(group, name));
    }

    /**
     * �õ�������ֵ
     * @param name String ����
     * @param row int �к�
     * @return long ֵ
     */
    public long getLong(String name, int row)
    {
        return getLong(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�������ֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return long ֵ
     */
    public long getLong(String group, String name, int row)
    {
        return TCM_Transform.getLong(getData(group, name, row));
    }
    /**
     * �õ�longֵ
     * @param row int
     * @param column int
     * @return long
     */
    public long getLong(int row,int column)
    {
        return TypeTool.getLong(getData(row,column));
    }

    /**
     * �õ�doubleֵ
     * @param name String ����
     * @return double ֵ
     */
    public double getDouble(String name)
    {
        return getDouble(DEFAULT_GROUP, name);
    }

    /**
     * �õ�doubleֵ
     * @param group String ����
     * @param name String ����
     * @return double ֵ
     */
    public double getDouble(String group, String name)
    {
        return TCM_Transform.getDouble(getData(group, name));
    }

    /**
     * �õ�doubleֵ
     * @param name String ����
     * @param row int �к�
     * @return double ֵ
     */
    public double getDouble(String name, int row)
    {
        return getDouble(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�doubleֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return double ֵ
     */
    public double getDouble(String group, String name, int row)
    {
        return TCM_Transform.getDouble(getData(group, name, row));
    }
    /**
     * �õ�doubleֵ
     * @param row int
     * @param column int
     * @return double
     */
    public double getDouble(int row,int column)
    {
        return TypeTool.getDouble(getData(row,column));
    }

    /**
     * �õ�shortֵ
     * @param name String ����
     * @return short ֵ
     */
    public short getShort(String name)
    {
        return getShort(DEFAULT_GROUP, name);
    }

    /**
     * �õ�shortֵ
     * @param group String ����
     * @param name String ����
     * @return short ֵ
     */
    public short getShort(String group, String name)
    {
        return TCM_Transform.getShort(getData(group, name));
    }

    /**
     * �õ�shortֵ
     * @param name String ����
     * @param row int �к�
     * @return short ֵ
     */
    public short getShort(String name, int row)
    {
        return getShort(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�shortֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return short ֵ
     */
    public short getShort(String group, String name, int row)
    {
        return TCM_Transform.getShort(getData(group, name, row));
    }

    /**
     * �õ�byteֵ
     * @param name String ����
     * @return byte ֵ
     */
    public byte getByte(String name)
    {
        return getByte(DEFAULT_GROUP, name);
    }

    /**
     * �õ�byteֵ
     * @param group String ����
     * @param name String ����
     * @return byte ֵ
     */
    public byte getByte(String group, String name)
    {
        return TCM_Transform.getByte(getData(group, name));
    }

    /**
     * �õ�byteֵ
     * @param name String ����
     * @param row int �к�
     * @return byte ֵ
     */
    public byte getByte(String name, int row)
    {
        return getByte(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�byteֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return byte ֵ
     */
    public byte getByte(String group, String name, int row)
    {
        return TCM_Transform.getByte(getData(group, name, row));
    }

    /**
     * �õ�floatֵ
     * @param name String ����
     * @return float ֵ
     */
    public float getFloat(String name)
    {
        return getFloat(DEFAULT_GROUP, name);
    }

    /**
     * �õ�floatֵ
     * @param group String ����
     * @param name String ����
     * @return float ֵ
     */
    public float getFloat(String group, String name)
    {
        return TCM_Transform.getFloat(getData(group, name));
    }

    /**
     * �õ�floatֵ
     * @param name String ����
     * @param row int �к�
     * @return float ֵ
     */
    public float getFloat(String name, int row)
    {
        return getFloat(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�floatֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return float ֵ
     */
    public float getFloat(String group, String name, int row)
    {
        return TCM_Transform.getFloat(getData(group, name, row));
    }

    /**
     * �õ�charֵ
     * @param name String ����
     * @return char ֵ
     */
    public char getChar(String name)
    {
        return getChar(DEFAULT_GROUP, name);
    }

    /**
     * �õ�charֵ
     * @param group String ����
     * @param name String ����
     * @return char ֵ
     */
    public char getChar(String group, String name)
    {
        return TCM_Transform.getChar(getData(group, name));
    }

    /**
     * �õ�charֵ
     * @param name String ����
     * @param row int �к�
     * @return char ֵ
     */
    public char getChar(String name, int row)
    {
        return getChar(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�charֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return char ֵ
     */
    public char getChar(String group, String name, int row)
    {
        return TCM_Transform.getChar(getData(group, name, row));
    }

    /**
     * �õ�booleanֵ
     * @param name String ����
     * @return boolean ֵ
     */
    public boolean getBoolean(String name)
    {
        return getBoolean(DEFAULT_GROUP, name);
    }

    /**
     * �õ�booleanֵ
     * @param group String ����
     * @param name String ����
     * @return boolean ֵ
     */
    public boolean getBoolean(String group, String name)
    {
        return TCM_Transform.getBoolean(getData(group, name));
    }

    /**
     * �õ�booleanֵ
     * @param name String ����
     * @param row int �к�
     * @return boolean ֵ
     */
    public boolean getBoolean(String name, int row)
    {
        return getBoolean(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�booleanֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return boolean ֵ
     */
    public boolean getBoolean(String group, String name, int row)
    {
        return TCM_Transform.getBoolean(getData(group, name, row));
    }
    /**
     * �õ�booleanֵ
     * @param row int
     * @param column int
     * @return boolean
     */
    public boolean getBoolean(int row,int column)
    {
        return TypeTool.getBoolean(getData(row,column));
    }
    /**
     * �õ�Stringֵ
     * @param name String ����
     * @return String ֵ
     */
    public String getValue(String name)
    {
        return getValue(DEFAULT_GROUP, name);
    }

    /**
     * �õ�Stringֵ
     * @param group String ����
     * @param name String ����
     * @return String ֵ
     */
    public String getValue(String group, String name)
    {
        return TCM_Transform.getString(getData(group, name));
    }

    /**
     * �õ�Stringֵ
     * @param name String ����
     * @param row int �к�
     * @return String ֵ
     */
    public String getValue(String name, int row)
    {
        return getValue(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�Stringֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return String ֵ
     */
    public String getValue(String group, String name, int row)
    {
        return TCM_Transform.getString(getData(group, name, row));
    }

    /**
     * �õ�����ֵ
     * @param name String
     * @return Timestamp ����
     */
    public Timestamp getTimestamp(String name)
    {
        return getTimestamp(DEFAULT_GROUP, name);
    }

    /**
     * �õ�����ֵ
     * @param group String ����
     * @param name String ����
     * @return Timestamp
     */
    public Timestamp getTimestamp(String group, String name)
    {
        return (Timestamp) getData(group, name);
    }

    /**
     * �õ�����ֵ
     * @param name String ����
     * @param row int �к�
     * @return Timestamp ֵ
     */
    public Timestamp getTimestamp(String name, int row)
    {
        return getTimestamp(DEFAULT_GROUP, name, row);
    }

    /**
     * �õ�����ֵ
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return Timestamp ֵ
     */
    public Timestamp getTimestamp(String group, String name, int row)
    {
        return (Timestamp) getData(group, name, row);
    }

    /**
     * �õ� Vector ֵ
     * @param names String �����б� "ID;NAME"
     * @return Vector
     */
    public Vector getVector(String names)
    {
        return getVector(names,0);
    }
    /**
     * �õ� Vector ֵ
     * @param names String �����б� "ID;NAME"
     * @param size int �������
     * @return Vector
     */
    public Vector getVector(String names,int size)
    {
        return getVector(DEFAULT_GROUP, names,size);
    }

    /**
     * �õ� Vector ֵ
     * @param group String ����
     * @param names String �����б� "ID;NAME"
     * @return Vector
     */
    public Vector getVector(String group, String names)
    {
        return getVector(group,names,0);
    }
    /**
     * �õ� Vector ֵ
     * @param group String ����
     * @param names String "ID;NAME"
     * @param size int �������
     * @return Vector
     */
    public Vector getVector(String group, String names,int size)
    {
        Vector data = new Vector();
        String nameArray[] = StringTool.parseLine(names, ";");
        if (nameArray.length == 0)
        {
            return data;
        }
        int count = getCount(group, nameArray[0]);
        if(size > 0 && count > size)
            count = size;
        for (int i = 0; i < count; i++)
        {
            Vector row = new Vector();
            for (int j = 0; j < nameArray.length; j++)
            {
                row.add(getData(group, nameArray[j], i));
            }
            data.add(row);
        }
        return data;
    }

    /**
     * �õ�һ�е�Vector
     * @param row int �к�
     * @param names String �б�
     * @return Vector
     */
    public Vector getRowVector(int row, String names)
    {
        return getRowVector(DEFAULT_GROUP, row, names);
    }

    /**
     * �õ�һ�е�Vector
     * @param group String ���
     * @param row int �к�
     * @param names String �б�
     * @return Vector
     */
    public Vector getRowVector(String group, int row, String names)
    {
        Vector data = new Vector();
        String nameArray[] = StringTool.parseLine(names, ";");
        if (nameArray.length == 0)
        {
            return data;
        }
        int count = getCount(group, nameArray[0]);
        if (row < 0 || row >= count)
        {
            for (int j = 0; j < nameArray.length; j++)
            {
                data.add(getData(group, nameArray[j]));
            }
            return data;
        }
        for (int j = 0; j < nameArray.length; j++)
        {
            data.add(getData(group, nameArray[j], row));
        }
        return data;
    }

    /**
     * ����������
     * @param inParm TParm ����Դ
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setRowData(TParm inParm)
    {
        return setRowData( -1, inParm);
    }

    /**
     * ����������
     * @param row int �к�
     * @param inParm TParm ����Դ
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setRowData(int row, TParm inParm)
    {
        return setRowData(row, inParm, -1);
    }

    /**
     * ����������
     * @param row int �к�
     * @param inParm TParm ����Դ
     * @param inParmRow int ����Դ�к�
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setRowData(int row, TParm inParm, int inParmRow)
    {
        return setRowData(row, inParm, inParmRow, "");
    }

    /**
     * ����������
     * @param row int �к�
     * @param inParm TParm ����Դ
     * @param inParmRow int ����Դ�к�
     * @param names String �� "ID;NAME"
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setRowData(int row, TParm inParm, int inParmRow,
                              String names)
    {
        return setRowData(DEFAULT_GROUP, row, inParm, DEFAULT_GROUP, inParmRow,
                          names);
    }

    /**
     * ����������
     * @param group String ���
     * @param row int �к�
     * @param inParm TParm ����Դ
     * @param inParmGroup String ����Դ���
     * @param inParmRow int ����Դ�к�
     * @param names String "ID;NAME"
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setRowData(String group, int row, TParm inParm,
                              String inParmGroup, int inParmRow, String names)
    {
        if (inParm == null)
        {
            return false;
        }
        if (!inParm.existGroup(inParmGroup))
        {
            return false;
        }
        String nameArray[];
        if (names != null && names.length() > 0)
        {
            nameArray = StringTool.parseLine(names, ";");
        } else
        {
            nameArray = inParm.getNames(inParmGroup);
        }
        int count = nameArray.length;
        for (int i = 0; i < count; i++)
        {
            Object value;
            if (inParmRow < 0)
            {
                value = inParm.getData(inParmGroup, nameArray[i]);
            } else
            {
                value = inParm.getData(inParmGroup, nameArray[i], inParmRow);
            }
            if (row < 0)
            {
                setData(group, nameArray[i], value);
            } else
            {
                setData(group, nameArray[i], row, value);
            }
        }
        return true;
    }
    /**
     * ���������
     * @param inParm TParm ����Դ
     * @param inParmRow int ����Դ�к�
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean addRowData(TParm inParm, int inParmRow)
    {
        return addRowData(inParm, inParmRow, "");
    }
    /**
     * ����������
     * @param inParm TParm ����Դ
     * @param inParmRow int ����Դ�к�
     * @param names String �� "ID;NAME"
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean addRowData(TParm inParm, int inParmRow,
                              String names)
    {
        return addRowData(DEFAULT_GROUP, inParm, DEFAULT_GROUP, inParmRow,
                          names);
    }
    /**
     * ���������
     * @param group String ���
     * @param inParm TParm ����Դ
     * @param inParmGroup String ����Դ���
     * @param inParmRow int ����Դ�к�
     * @param names String "ID;NAME"
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean addRowData(String group, TParm inParm,
                              String inParmGroup, int inParmRow, String names)
    {
        if (inParm == null)
        {
            return false;
        }
        if (!inParm.existGroup(inParmGroup))
        {
            return false;
        }
        String nameArray[];
        if (names != null && names.length() > 0)
        {
            nameArray = StringTool.parseLine(names, ";");
        } else
        {
            nameArray = inParm.getNames(inParmGroup);
        }
        int count = nameArray.length;
        for (int i = 0; i < count; i++)
        {
            Object value;
            if (inParmRow < 0)
            {
                value = inParm.getData(inParmGroup, nameArray[i]);
            } else
            {
                value = inParm.getData(inParmGroup, nameArray[i], inParmRow);
            }
            addData(group, nameArray[i], value);
        }
        if(nameArray.length > 0)
            setCount(getCount(nameArray[0]));
        return true;
    }

    /**
     * ɾ����
     * @param row int �к�
     */
    public void removeRow(int row)
    {
        removeRow(DEFAULT_GROUP, row);
    }

    /**
     * ɾ����
     * @param row int �к�
     * @param names String �� "ID;NAME"
     */
    public void removeRow(int row, String names)
    {
        removeRow(DEFAULT_GROUP, row, names);
    }

    /**
     * ɾ����
     * @param group String ���
     * @param row int �к�
     */
    public void removeRow(String group, int row)
    {
        removeRow(group, row, "");
    }

    /**
     * ɾ����
     * @param group String ���
     * @param row int �к�
     * @param names String �� "ID;NAME"
     */
    public void removeRow(String group, int row, String names)
    {
        if (!existGroup(group))
        {
            return;
        }
        String nameArray[];
        if (names != null && names.length() > 0)
        {
            nameArray = StringTool.parseLine(names, ";");
        } else
        {
            nameArray = getNames(group);
        }
        int count = nameArray.length;
        for (int i = 0; i < count; i++)
        {
            Object obj = getData(group, nameArray[i]);
            if (obj instanceof Vector)
            {
                ((Vector) obj).remove(row);
            }
        }
        if(count > 0)
            setCount(getCount(nameArray[0]));
    }

    /**
     * ����һ�п���
     * @return int ���к�
     */
    public int insertRow()
    {
        return insertRow( -1);
    }

    /**
     * ����һ�п���
     * @param row int -1 ����
     * @return int ���к�
     */
    public int insertRow(int row)
    {
        return insertRow(row, "");
    }

    /**
     * ����һ�п���
     * @param row int -1 ����
     * @param names String ��
     * @return int ���к�
     */
    public int insertRow(int row, String names)
    {
        return insertRow(DEFAULT_GROUP, row, names);
    }

    /**
     * ����һ�п���
     * @param group String ���
     * @return int ���к�
     */
    public int insertRow(String group)
    {
        return insertRow(group, -1);
    }

    /**
     * ����һ�п���
     * @param group String ���
     * @param row int -1 ����
     * @return int ���к�
     */
    public int insertRow(String group, int row)
    {
        return insertRow(group, row, "");
    }

    /**
     * ����һ�п���
     * @param group String ���
     * @param names String
     * @return int
     */
    public int insertRow(String group, String names)
    {
        return insertRow(group, -1, names);
    }

    /**
     * ����һ�п���
     * @param group String ���
     * @param row int �к� -1 ����
     * @param names String ��
     * @return int ���к�
     */
    public int insertRow(String group, int row, String names)
    {
        String nameArray[];
        if (!existGroup(group))
        {
            if (names == null || names.trim().length() == 0)
            {
                return 0;
            }
            nameArray = StringTool.parseLine(names, ";");
            for (int i = 0; i < nameArray.length; i++)
            {
                addData(group, nameArray[i], "");
            }
            return 0;
        }
        if (names != null && names.length() > 0)
        {
            nameArray = StringTool.parseLine(names, ";");
        } else
        {
            nameArray = getNames(group);
        }
        int count = nameArray.length;
        int newRow = row;
        for (int i = 0; i < count; i++)
        {
            Object obj = getData(group, nameArray[i]);
            if (obj != null && obj instanceof Vector)
            {
                Vector d = (Vector) obj;
                if (row < 0)
                {
                    d.add("");
                    newRow = d.size() - 1;
                } else
                {
                    d.add(row, "");
                }
            }
        }
        return newRow;
    }

    /**
     * �õ�������
     * @return int
     */
    public int getErrCode()
    {
        return getInt(ERR_GROUP, "Code");
    }

    /**
     * ���ô�����
     * @param code int
     */
    public void setErrCode(int code)
    {
        setData(ERR_GROUP, "Code", code);
    }

    /**
     * �õ�������Ϣ
     * @return String ������Ϣ
     */
    public String getErrText()
    {
        return getValue(ERR_GROUP, "Text");
    }

    /**
     * ���ô�����Ϣ
     * @param text String
     */
    public void setErrText(String text)
    {
        setData(ERR_GROUP, "Text", text);
    }

    /**
     * �õ�����ֵ����
     * @return String
     */
    public String getErrName()
    {
        return getValue(ERR_GROUP, "Name");
    }

    /**
     * ���ô���ֵ����
     * @param name String
     */
    public void setErrName(String name)
    {
        setData(ERR_GROUP, "Name", name);
    }

    /**
     * ���ô�����Ϣ
     * @param code int ����ֵ
     * @param text String ������Ϣ
     */
    public void setErr(int code, String text)
    {
        setErrCode(code);
        setErrText(text);
    }

    /**
     * ���ô�����Ϣ
     * @param code int ����ֵ
     * @param text String ������Ϣ
     * @param name String ��������
     */
    public void setErr(int code, String text, String name)
    {
        setErrCode(code);
        setErrText(text);
        setErrName(name);
    }

    /**
     * ���ô�����Ϣ
     * @param parm TParm
     */
    public void setErr(TParm parm)
    {
        setErrCode(parm.getErrCode());
        setErrText(parm.getErrText());
        setErrName(parm.getErrName());
    }

    /**
     * �õ��µĴ�����Ϣ����
     * @return TParm
     */
    public TParm getErrParm()
    {
        TParm result = new TParm();
        result.setErr(getErrCode(), getErrText(), getErrName());
        return result;
    }

    /**
     * ������
     * @param group String ����
     * @return boolean true ����� false �鲻����
     */
    public boolean existGroup(String group)
    {
        return getGroupData(group) != null;
    }

    /**
     * ��������
     * @param name String ����
     * @return boolean true�������� false ����������
     */
    public boolean existData(String name)
    {
        return existData(DEFAULT_GROUP, name);
    }

    /**
     * ��������
     * @param group String ����
     * @param name String ����
     * @return boolean true�������� false ����������
     */
    public boolean existData(String group, String name)
    {
        return getData(group, name) != null;
    }

    /**
     * ��������
     * @param name String ����
     * @param row int �к�
     * @return boolean true�������� false ����������
     */
    public boolean existData(String name, int row)
    {
        return existData(DEFAULT_GROUP, name, row);
    }

    /**
     * ��������
     * @param group String ����
     * @param name String ����
     * @param row int �к�
     * @return boolean true�������� false ����������
     */
    public boolean existData(String group, String name, int row)
    {
        return getData(group, name, row) != null;
    }

    /**
     * ��⴫������Ƿ�Ϊ��
     * @param names String ����
     * @param returnParm TParm ����ֵ
     * @return boolean true ����:��Ϊ���� false ��ȷ
     */
    public boolean checkEmpty(String names, TParm returnParm)
    {
        return checkEmpty(DEFAULT_GROUP, names, returnParm);
    }

    /**
     * ��⴫������Ƿ�Ϊ��
     * @param group String ����
     * @param names String �����б�
     * @param returnParm TParm ����ֵ
     * @return boolean true ����:��Ϊ���� false ��ȷ
     */
    public boolean checkEmpty(String group, String names, TParm returnParm)
    {
        StringTokenizer stk = new StringTokenizer(names, ",");
        while (stk.hasMoreTokens())
        {
            String name = stk.nextToken().trim();
            Object value = null;
            int index = name.indexOf(":");
            if (index > 0)
            {
                String g = name.substring(0, index).trim();
                String n = name.substring(index + 1, name.length()).trim();
                name = g + ":" + n;
                value = getData(g, n);
            } else
            {
                value = getData(group, name);
            }
            if (value == null || value.toString().trim().length() == 0)
            {
                returnParm.setErr( -1, name + "����Ϊ��", name);
                return true;
            }
        }
        return false;
    }

    public TParm getParm(String name)
    {
        return getParm(DEFAULT_GROUP, name);
    }

    public TParm getParm(String group, String name)
    {
        TParm parm = new TParm();
        parm.setData((Map) getData(group, name));
        return parm;
    }

    public TParm getParm(String name, int index)
    {
        return getParm(DEFAULT_GROUP, name, index);
    }

    public TParm getParm(String group, String name, int index)
    {
        TParm parm = new TParm();
        parm.setData((Map) getData(group, name, index));
        return parm;
    }

    public TParm getRow()
    {
        TParm parm = new TParm();
        parm.setRowData( -1, this, -1);
        return parm;
    }

    public TParm getRow(int row)
    {
        TParm parm = new TParm();
        parm.setRowData( -1, this, row);
        return parm;
    }

    /**
     * �õ�����
     * @param name String
     * @return String[]
     */
    public String[] getStringArray(String name)
    {
        if (getErrCode() < 0)
        {
            return null;
        }
        if (!existData(name))
        {
            return null;
        }
        return (String[]) ((Vector) getData(name)).toArray(new String[]
                {});
    }
    /**
     * ������ݰ�
     * @return byte[]
     */
    public byte[] outData()
    {
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream dataStream = new ObjectOutputStream(out);
            dataStream.writeObject(getData());
            out.close();
            return out.toByteArray();
        }catch(Exception e)
        {
            return null;
        }
    }
    /**
     * �������ݰ�
     * @param data byte[]
     * @return boolean
     */
    public boolean inData(byte[] data)
    {
        try{
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream dataStream = new ObjectInputStream(in);
            setData((Map)dataStream.readObject());
            in.close();
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(data);
        return sb.toString();
    }
    /**
     * ��������
     * @param fileName String
     * @return boolean
     */
    public boolean save(String fileName)
    {
        DataOutputStream out = openOutStream(fileName);
        if(out == null)
            return false;
        try{
            writeObject(out);
        }catch(Exception e)
        {
            e.printStackTrace();
            out.close();
            return false;
        }
        out.close();
        return true;
    }
    /**
     * ��ȡ����
     * @param fileName String
     * @return boolean
     */
    public boolean read(String fileName)
    {
        DataInputStream in = openInStream(fileName);
        if(in == null)
            return false;
        try{
            readObject(in);
        }catch(Exception e)
        {
            e.printStackTrace();
            in.close();
            return false;
        }
        in.close();
        return true;
    }
    /**
     * ��������
     * @param fileName String
     * @return DataInputStream
     */
    private DataInputStream openInStream(String fileName)
    {
        try{
            InputStream inputStream = new FileInputStream(fileName);
            return new DataInputStream(inputStream);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * �������
     * @param fileName String
     * @return DataOutputStream
     */
    private DataOutputStream openOutStream(String fileName)
    {
        try{
            OutputStream outputStream = new FileOutputStream(fileName);
            return new DataOutputStream(outputStream);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeMap(getData());
    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        setData(s.readMap());
    }
    /**
     * �õ�Vector����
     * @param group String
     * @param name String
     * @return Vector
     */
    public Vector getVectorValue(String group, String name)
    {
        Object obj = getData(group, name);
        if(obj == null || !(obj instanceof Vector))
            return null;
        return (Vector)obj;
    }
    /**
     * �õ�Vector����
     * @param name String
     * @return Vector
     */
    public Vector getVectorValue(String name)
    {
        return getVectorValue(DEFAULT_GROUP,name);
    }
    /**
     * ׷������
     * @param parm TParm
     */
    public void addParm(TParm parm)
    {
        if(parm == null)
            return;
        String s[] = parm.getNames();
        for(int i = 0;i < s.length;i++)
        {
            Vector data = getVectorValue(s[i]);
            if(data == null)
                continue;
            Vector a = parm.getVectorValue(s[i]);
            data.addAll(a);
        }
        setCount(getCount() + parm.getCount());
    }
    /**
     * �õ�����Parm
     * @param code int
     * @param text String
     * @return TParm
     */
    public static TParm newErrParm(int code,String text)
    {
        TParm parm = new TParm();
        parm.setErr(code,text);
        return parm;
    }
    /**
     * ���Ӽ���
     * @param action String ������
     * @param object Object ��Ӧ����
     * @param methodName String ������
     */
    public void addListener(String action,Object object,String methodName)
    {
        if(action == null || action.length() == 0 || object == null ||
           methodName == null || methodName.length() == 0)
            return;
        Map map = getListenerData();
        if(map == null)
        {
            map = new HashMap();
            setGroupData(LISTENER_GROUP,map);
        }
        ArrayList list = (ArrayList)map.get(action);
        if(list == null)
        {
            list = new ArrayList();
            map.put(action,list);
        }
        Vector vector = new Vector();
        vector.add(object);
        vector.add(methodName);
        list.add(vector);
    }
    /**
     * ɾ������
     * @param action String
     * @param object Object
     * @param methodName String
     */
    public void removeListener(String action,Object object,String methodName)
    {
        if(action == null || action.length() == 0 || object == null ||
           methodName == null || methodName.length() == 0)
            return;
        Map map = getListenerData();
        if(map == null)
            return;
        ArrayList list = (ArrayList)map.get(action);
        if(list == null)
            return;
        for(int i = 0;i < list.size();i++)
        {
            Vector vector = (Vector)list.get(i);
            if(vector.get(0) == object &&
               vector.get(1).equals(methodName))
            {
                list.remove(i);
                break;
            }
        }
        if(list.size() == 0)
            map.remove(action);
        if(map.size() == 0)
            removeGroupData(LISTENER_GROUP);
    }
    /**
     * ִ�м���
     * @param action String
     * @param parameters Object[]
     * @return Object
     */
    public Object runListener(String action,Object ... parameters)
    {
        return runListenerArray(action,parameters);
    }
    /**
     * ִ�м���
     * @param action String
     * @param parameters Object[]
     * @return Object
     */
    public Object runListenerArray(String action,Object [] parameters)
    {
        if(action == null || action.length() == 0)
            return null;
        Map map = getListenerData();
        if(map == null)
            return null;
        ArrayList list = (ArrayList)map.get(action);
        if(list == null)
            return null;
        Object[] result = new Object[list.size()];
        for(int i = 0;i < list.size();i++)
        {
            Vector vector = (Vector)list.get(i);
            Object object = vector.get(0);
            String methodName = (String)vector.get(1);
            result[i] = RunClass.invokeMethod(object,methodName, parameters);
        }
        return result;
    }
    /**
     * ���ü������ݰ�
     * @param map Map
     */
    public void setListenerData(Map map)
    {
        setGroupData(LISTENER_GROUP,map);
    }
    /**
     * �õ��������ݰ�
     * @return Map
     */
    public Map getListenerData()
    {
        return getGroupData(LISTENER_GROUP);
    }

    public static void main(String args[])
    {
        TParm data = new TParm();
        data.setData("ORDER_CODE", "1AAAC0001");
        System.out.println("------"+data);
        /*TParm parm = new TParm();
        class A
        {
            public void aaa(int x)
            {
                System.out.println("OK " + x);
            }
        }
        A a = new A();
        parm.addListener("test",a,"aaa");
        System.out.println(parm);
        parm.runListener("test",100);*/
    }
}
