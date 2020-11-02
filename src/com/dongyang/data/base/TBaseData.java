package com.dongyang.data.base;

import java.util.Map;
import com.dongyang.data.exception.TException;
import java.util.HashMap;
import com.dongyang.util.StringTool;
import java.util.Vector;
import com.dongyang.data.TParm;
import com.dongyang.ui.event.BaseEvent;
/**
 * ������������
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
     * ���ݰ�
     */
    public static final String DATA = "Data";
    /**
     * ���԰�
     */
    public static final String ATTRIBUTE = "Attribute";
    /**
     * ��������
     */
    public static final String ATTRIBUTE_TYPE = "Type";
    /**
     * ���Գߴ�
     */
    public static final String ATTRIBUTE_SIZE = "Size";
    /**
     * �����Ƿ��ʼ��
     */
    public static final String ATTRIBUTE_IS_INIT = "isInit";
    /**
     * ����ԭʼ����
     */
    public static final String ATTRIBUTE_OLD_DATA = "oldData";
    /**
     * ����ֵ�Ƿ��޸�
     */
    public static final String ATTRIBUTE_MODIFIED = "modified";
    /**
     * ����ֵ�Ƿ��Ƕ�ֵ
     */
    public static final String ATTRIBUTE_MULTI = "multi";

    /**
     * ���ݰ�
     */
    private TParm data;
    /**
     * ˫����
     */
    private boolean doubleBuffered;
    /**
     * ������
     * @throws TException
     */
    public TBaseData() throws TException
    {
        this(new TParm());
    }
    /**
     * ������
     * @param parm TParm
     * @throws TException
     */
    public TBaseData(TParm parm) throws TException
    {
        setData(parm);
    }
    /**
     * ������
     * @param map Map
     * @throws TException
     */
    public TBaseData(Map map) throws TException
    {
        setDataMap(map);
    }
    /**
     * �����Ƿ���˫����
     * @param doubleBuffered boolean true��˫���� false ����˫����
     */
    public void setDoubleBuffered(boolean doubleBuffered)
    {
        this.doubleBuffered = doubleBuffered;
    }
    /**
     * �Ƿ���˫����
     * @return boolean true��˫���� false ����˫����
     */
    public boolean isDoubleBuffered()
    {
        return doubleBuffered;
    }
    /**
     * �������ݰ�(TParm)
     * @param data TParm
     * @throws TException
     */
    public void setData(TParm data) throws TException
    {
        if(data == null)
            throw new TException("���ݰ�Ϊ��");
        this.data = data;
    }
    /**
     * �õ����ݰ�(TParm)
     * @return TParm
     */
    public TParm getData()
    {
        return data;
    }
    /**
     * �������ݰ�(MAP)
     * @param map Map
     * @throws TException
     */
    public void setDataMap(Map map) throws TException
    {
        data = new TParm();
        if(map == null)
            throw new TException("���ݰ�Ϊ��");
        data.setData(map);
    }
    /**
     * �õ����ݰ�(MAP)
     * @return Map
     * @throws TException
     */
    public Map getDataMap() throws TException
    {
        if(getData() == null)
            throw new TException("���ݰ�û�г�ʼ��");
        return data.getData();
    }
    /**
     * �õ����԰�
     * @param name String ������
     * @return Object ���԰�
     * @throws TException
     */
    public Map getAttributePackage(String name) throws TException
    {
        if(getData() == null)
            throw new TException("���ݰ�û�г�ʼ��");
        if(name == null || name.trim().length() == 0)
            throw new TException("<" + name + ">������Ϊ��");
        Object value = getData().getData(ATTRIBUTE,name);
        if(value == null)
            return null;
        if(!(value instanceof Map))
            throw new TException("���԰��������ʹ���");
        return (Map)value;
    }
    /**
     * �����Ƿ����
     * @param name String ������
     * @return boolean true ���� false ������
     * @throws TException
     */
    public boolean existAttribute(String name) throws TException
    {
        return getAttributePackage(name) != null;
    }
    /**
     * ������������
     * @param name String ������
     * @param type String ��������
     * @throws TException
     */
    public void setAttributeType(String name,String type) throws TException
    {
        setAttributeType(getAttributePackage(name),name,type);
    }
    /**
     * ������������
     * @param map Map ���ݰ�
     * @param name String ������
     * @param type String ��������
     * @throws TException
     */
    public void setAttributeType(Map map,String name,String type) throws TException
    {
        if(type == null || type.trim().length() == 0)
            throw new TException("<" + name + ">��������Ϊ��");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        map.put(ATTRIBUTE_TYPE,type);
    }
    /**
     * �õ���������
     * @param name String ������
     * @return String ��������
     * @throws TException
     */
    public String getAttributeType(String name) throws TException
    {
        return getAttributeType(getAttributePackage(name),name);
    }
    /**
     * �õ���������
     * @param map Map ���ݰ�
     * @param name String ������
     * @return String ��������
     * @throws TException
     */
    public String getAttributeType(Map map,String name) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Object value = map.get(ATTRIBUTE_TYPE);
        if(value == null)
            throw new TException("<" + name + ">��������Ϊ��");
        if(!(value instanceof String))
            throw new TException("<" + name + ">�������͸�ʽ����");
        return (String)value;
    }
    /**
     * �������Գߴ�
     * @param name String ������
     * @param size int ���Գߴ�
     * @throws TException
     */
    public void setAttributeSize(String name,int size) throws TException
    {
        setAttributeSize(getAttributePackage(name),name,size);
    }
    /**
     * �������Գߴ�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param size int ���Գߴ�
     * @throws TException
     */
    public void setAttributeSize(Map map,String name,int size) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        map.put(ATTRIBUTE_SIZE,size);
    }
    /**
     * �õ����Գߴ�
     * @param name String ������
     * @return int ���Գߴ�
     * @throws TException
     */
    public int getAttributeSize(String name) throws TException
    {
        return getAttributeSize(getAttributePackage(name),name);
    }
    /**
     * �õ����Գߴ�
     * @param map Map ���ݰ�
     * @param name String ������
     * @return int ���Գߴ�
     * @throws TException
     */
    public int getAttributeSize(Map map,String name) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Object value = map.get(ATTRIBUTE_SIZE);
        if(value == null)
            return 0;
        if(!(value instanceof Integer))
            throw new TException("<" + name + ">���Գߴ��ʽ����");
        return (int)(Integer)value;
    }
    /**
     * �������Գ�ʼ��
     * @param name String ������
     * @param isInit boolean ���Գ�ʼ��
     * @throws TException
     */
    public void setAttributeInit(String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        setAttributeInit(getAttributePackage(name),name,isInit);
    }
    /**
     * �������Գ�ʼ��
     * @param map Map ���ݰ�
     * @param name String ������
     * @param isInit boolean ���Գ�ʼ��
     * @throws TException
     */
    public void setAttributeInit(Map map,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        map.put(ATTRIBUTE_IS_INIT,isInit);
    }
    /**
     * �õ������Ƿ��ʼ��
     * @param name String ������
     * @return boolean ���ʼ��
     * @throws TException
     */
    public boolean getAttributeInit(String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        return getAttributeInit(getAttributePackage(name),name);
    }
    /**
     * �õ������Ƿ��ʼ��
     * @param map Map ���ݰ�
     * @param name String ������
     * @return boolean �Ƿ��ʼ��
     * @throws TException
     */
    public boolean getAttributeInit(Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Object value = map.get(ATTRIBUTE_IS_INIT);
        if(value == null)
            return false;
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">���Գ�ʼ����ʽ����");
        return (boolean)(Boolean)value;
    }
    /**
     * �������Գ�ʼ��
     * @param row int �к�
     * @param name String ������
     * @param isInit boolean ���Գ�ʼ��
     * @throws TException
     */
    public void setAttributeInit(int row,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        setAttributeInit(row,getAttributePackage(name),name,isInit);
    }
    /**
     * �������Գ�ʼ��
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param isInit boolean ���Գ�ʼ��
     * @throws TException
     */
    public void setAttributeInit(int row,Map map,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰��Ķ�ֵ������Ϊ��");
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
     * �������Գ�ʼ��
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param isInit boolean ���Գ�ʼ��
     * @throws TException
     */
    public void insertAttributeInit(int row,Map map,String name,boolean isInit) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰��Ķ�ֵ������Ϊ��");
        Vector data = (Vector)map.get(ATTRIBUTE_IS_INIT);
        data.add(row,isInit);
    }
    /**
     * �õ������Ƿ��ʼ��
     * @param row int �к�
     * @param name String ������
     * @return boolean �Ƿ��ʼ��
     * @throws TException
     */
    public boolean getAttributeInit(int row,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        return getAttributeInit(row,getAttributePackage(name),name);
    }
    /**
     * �õ������Ƿ��ʼ��
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @return boolean �Ƿ��ʼ��
     * @throws TException
     */
    public boolean getAttributeInit(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
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
            throw new TException("<" + name + ">���Գ�ʼ����ʽ����");
        return (boolean)(Boolean)value;
    }
    /**
     * ɾ�������Ƿ��ʼ��
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @throws TException
     */
    public void removeAttribteInit(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Vector data = (Vector)map.get(ATTRIBUTE_IS_INIT);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_IS_INIT,data);
        }
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">���԰�������Խ�� " + row);
        data.remove(row);
    }
    /**
     * ��������ԭʼֵ
     * @param name String ������
     * @param data Object ԭʼֵ
     * @throws TException
     */
    public void setAttributeOldData(String name,Object data) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        setAttributeOldData(getAttributePackage(name),name,data);
    }
    /**
     * ��������ԭʼֵ
     * @param map Map ���ݰ�
     * @param name String ������
     * @param data Object ԭʼֵ
     * @throws TException
     */
    public void setAttributeOldData(Map map,String name,Object data) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        map.put(ATTRIBUTE_OLD_DATA,data);
    }
    /**
     * �õ�����ԭʼֵ
     * @param name String ������
     * @return Object ԭʼֵ
     * @throws TException
     */
    public Object getAttributeOldData(String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        return getAttributeOldData(getAttributePackage(name),name);
    }
    /**
     * �õ�����ԭʼֵ
     * @param map Map ���ݰ�
     * @param name String ������
     * @return Object ԭʼֵ
     * @throws TException
     */
    public Object getAttributeOldData(Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            return null;
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        return map.get(ATTRIBUTE_OLD_DATA);
    }
    /**
     * ��������ԭʼֵ
     * @param row int �к�
     * @param name String ������
     * @param data Object ԭʼֵ
     * @throws TException
     */
    public void setAttributeOldData(int row,String name,Object data) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        setAttributeOldData(row,getAttributePackage(name),name,data);
    }
    /**
     * ��������ԭʼֵ
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param value Object ԭʼֵ
     * @throws TException
     */
    public void setAttributeOldData(int row,Map map,String name,Object value) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
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
     * ��������ԭʼֵ
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param value Object ԭʼֵ
     * @throws TException
     */
    public void insertAttributeOldData(int row,Map map,String name,Object value) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Vector data = (Vector)map.get(ATTRIBUTE_OLD_DATA);
        data.add(row,value);
    }
    /**
     * �õ�����ԭʼֵ
     * @param row int �к�
     * @param name String ������
     * @return Object ԭʼֵ
     * @throws TException
     */
    public Object getAttributeOldData(int row,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        return getAttributeOldData(row,getAttributePackage(name),name);
    }
    /**
     * �õ�����ԭʼֵ
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @return Object ԭʼֵ
     * @throws TException
     */
    public Object getAttributeOldData(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
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
     * ɾ������ԭʼֵ
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @throws TException
     */
    public void removeAttributeOldData(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Vector data = (Vector)map.get(ATTRIBUTE_OLD_DATA);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_OLD_DATA,data);
        }
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">���԰�������Խ�� " + row);
        data.remove(row);
    }
    /**
     * ��������ֵ�Ƿ��޸�
     * @param name String ������
     * @param isModified boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public void setAttributeModified(String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        setAttributeModified(getAttributePackage(name),name,isModified);
    }
    /**
     * ��������ֵ�Ƿ��޸�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param isModified boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public void setAttributeModified(Map map,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        map.put(ATTRIBUTE_MODIFIED,isModified);
    }
    /**
     * �õ�����ֵ�Ƿ��޸�
     * @param name String ������
     * @return boolean boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public boolean getAttributeModified(String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        return getAttributeModified(getAttributePackage(name),name);
    }
    /**
     * �õ�����ֵ�Ƿ��޸�
     * @param map Map ���ݰ�
     * @param name String ������
     * @return boolean boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public boolean getAttributeModified(Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Object value = map.get(ATTRIBUTE_MODIFIED);
        if(value == null)
            return false;
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">�����޸ĸ�ʽ����");
        return (boolean)(Boolean)value;
    }
    /**
     * ��������ֵ�Ƿ��޸�
     * @param row int �к�
     * @param name String ������
     * @param isModified boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public void setAttributeModified(int row,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        setAttributeModified(row,getAttributePackage(name),name,isModified);
    }
    /**
     * ��������ֵ�Ƿ��޸�
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param isModified boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public void setAttributeModified(int row,Map map,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
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
     * ��������ֵ�Ƿ��޸�
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @param isModified boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public void insertAttributeModified(int row,Map map,String name,boolean isModified) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Vector data = (Vector)map.get(ATTRIBUTE_MODIFIED);
        data.add(row,isModified);
    }
    /**
     * �õ�����ֵ�Ƿ��޸�
     * @param row int �к�
     * @param name String ������
     * @return boolean boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public boolean getAttributeModified(int row,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        return getAttributeModified(row,getAttributePackage(name),name);
    }
    /**
     * �õ�����ֵ�Ƿ��޸�
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @return boolean boolean ����ֵ�Ƿ��޸� true �޸Ĺ� false û���޸Ĺ�
     * @throws TException
     */
    public boolean getAttributeModified(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
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
            throw new TException("<" + name + ">�����޸ĸ�ʽ����");
        return (boolean)(Boolean)value;
    }
    /**
     * ɾ������ֵ�Ƿ��޸�
     * @param row int �к�
     * @param map Map ���ݰ�
     * @param name String ������
     * @throws TException
     */
    public void removeAttributeModified(int row,Map map,String name) throws TException
    {
        if(!isDoubleBuffered())
            throw new TException("û������˫����");
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Vector data = (Vector)map.get(ATTRIBUTE_MODIFIED);
        if(data == null)
        {
            data = new Vector();
            map.put(ATTRIBUTE_MODIFIED,data);
        }
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">���԰�������Խ�� " + row);
        data.remove(row);
    }
    /**
     * ��������ֵ�Ƿ��Ƕ�ֵ
     * @param name String ������
     * @param multi boolean ����ֵ�Ƿ��Ƕ�ֵ true ��ֵ false ��ֵ
     * @throws TException
     */
    public void setAttributeMulti(String name,boolean multi) throws TException
    {
        setAttributeMulti(getAttributePackage(name),name,multi);
    }
    /**
     * ��������ֵ�Ƿ��Ƕ�ֵ
     * @param map Map ���ݰ�
     * @param name String ������
     * @param multi boolean ����ֵ�Ƿ��Ƕ�ֵ true ��ֵ false ��ֵ
     * @throws TException
     */
    public void setAttributeMulti(Map map,String name,boolean multi) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        map.put(ATTRIBUTE_MULTI,multi);
    }
    /**
     * �õ�����ֵ�Ƿ��Ƕ�ֵ
     * @param name String ������
     * @return boolean boolean ����ֵ�Ƿ��Ƕ�ֵ true ��ֵ false ��ֵ
     * @throws TException
     */
    public boolean getAttributeMulti(String name) throws TException
    {
        return getAttributeModified(getAttributePackage(name),name);
    }
    /**
     * �õ�����ֵ�Ƿ��Ƕ�ֵ
     * @param map Map ���ݰ�
     * @param name String ������
     * @return boolean boolean ����ֵ�Ƿ��Ƕ�ֵ true ��ֵ false ��ֵ
     * @throws TException
     */
    public boolean getAttributeMulti(Map map,String name) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        Object value = map.get(ATTRIBUTE_MULTI);
        if(value == null)
            return false;
        if(!(value instanceof Boolean))
            throw new TException("<" + name + ">���Զ�ֵ��ʽ����");
        return (boolean)(Boolean)value;
    }
    /**
     * ��������
     * @param name String ������
     * @param type String ��������
     * @throws TException
     */
    public void addAttribute(String name,String type) throws TException
    {
        if(existAttribute(name))
            throw new TException("<" + name + ">�����Ѿ�����");
        Map map = new HashMap();
        addAttribute(map,name,type);
        getData().setData(ATTRIBUTE,name,map);
    }
    /**
     * ɾ������
     * @param name String
     * @throws TException
     */
    public void removeAttribute(String name)throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        getData().removeData(ATTRIBUTE,name);
        getData().removeData(name);
    }
    /**
     * �õ����Ը���
     * @return int ���Ը���
     * @throws TException
     */
    public int attributeCount() throws TException
    {
        if(getData() == null)
            throw new TException("���ݰ�û�г�ʼ��");
        Map map = getData().getGroupData(ATTRIBUTE);
        if(map == null)
            return 0;
        return map.size();
    }
    /**
     * ��������
     * @param map Map ���԰�
     * @param name String ������
     * @param type String ��������
     * @throws TException
     */
    public void addAttribute(Map map,String name,String type) throws TException
    {
        if(map == null)
            throw new TException("<" + name + ">���԰�����Ϊ��");
        if(name == null || name.trim().length() == 0)
            throw new TException("<" + name + ">������Ϊ��");
        if(type == null || type.trim().length() == 0)
            throw new TException("<" + name + ">��������Ϊ��");
        map.put(ATTRIBUTE_TYPE,type);
    }
    /**
     * �Ƚ����������Ƿ���ͬ
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
     * ���Stringֵ�ó����Ƿ�Ϸ�
     * @param type String ����
     * @param attributeMap Map ��������
     * @param name String ����
     * @param value String ֵ
     * @throws TException
     */
    private void checkAttributeValueLength(String type,Map attributeMap,String name,
                                   Object value) throws TException
    {
        if(!"String".equals(type))
            return;
        //�õ����Գߴ�
        int size = getAttributeSize(attributeMap,name);
        if(size > 0 && value != null && ((String)value).length() > size)
            throw new TException("<" + name + ">����ֵ'" + value + "'��������" + size);
    }
    /**
     * ���ֵ�����Ƿ�Ϸ�
     * @param type String
     * @param name String
     * @param value Object
     * @throws TException
     */
    private void checkAttributeValueType(String type,String name,Object value)
            throws TException
    {
        if(!StringTool.equalsObjectType(type,value))
            throw new TException("<" + name + ">���Ը�ֵ���ʹ���");
    }
    /**
     * ����˫���崦��ֵ
     * @param attributeMap Map ���԰�
     * @param name String ����
     * @param value Object ֵ
     * @throws TException
     */
    public void doubleBufferedValue(Map attributeMap,String name,Object value)
            throws TException
    {
        if(!isDoubleBuffered())
            return;
        //�Ƿ��ʼ������
        if(!getAttributeInit(attributeMap,name))
        {
            setAttributeInit(attributeMap, name, true);
            //��������ԭʼֵ
            setAttributeOldData(attributeMap,name,value);
        }else
        {
            Object oldValue = getAttributeOldData(attributeMap,name);
            //�����޸�״̬
            setAttributeModified(attributeMap,name,!equalsValue(oldValue,value));
        }
    }
    /**
     * ����˫���崦���ֵ
     * @param row int �к�
     * @param attributeMap Map ���԰�
     * @param name String ����
     * @param value Object ֵ
     * @throws TException
     */
    public void setDoubleBufferedValue(int row,Map attributeMap,String name,Object value)
            throws TException
    {
        if(!isDoubleBuffered())
            return;
        //�Ƿ��ʼ������
        if(!getAttributeInit(row,attributeMap,name))
        {
            setAttributeInit(row,attributeMap, name, true);
            //��������ԭʼֵ
            setAttributeOldData(row,attributeMap,name,value);
        }else
        {
            Object oldValue = getAttributeOldData(row,attributeMap,name);
            //�����޸�״̬
            setAttributeModified(row,attributeMap,name,!equalsValue(oldValue,value));
        }
    }
    /**
     * ����˫���崦���ֵ����
     * @param row int �к�
     * @param attributeMap Map ���԰�
     * @param name String ����
     * @param value Object ֵ
     * @throws TException
     */
    public void insertDoubleBufferedValue(int row,Map attributeMap,String name,Object value)
            throws TException
    {
        if(!isDoubleBuffered())
            return;
        //�����ʼ��״̬
        insertAttributeInit(row,attributeMap, name, true);
        //��������ԭʼֵ
        insertAttributeOldData(row,attributeMap,name,value);
        //���������޸�״̬
        insertAttributeModified(row,attributeMap,name,false);
    }
    /**
     * ����˫���崦���ֵΪ��
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
        //�����ʼ��״̬
        insertAttributeInit(row,attributeMap, name, false);
        //��������ԭʼֵ
        insertAttributeOldData(row,attributeMap,name,null);
        //���������޸�״̬
        insertAttributeModified(row,attributeMap,name,false);
    }
    /**
     * ��������ֵ
     * @param name String ����
     * @param value Object ֵ
     * @throws TException
     */
    public void setAttributeValue(String name,Object value) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        //�õ����԰�
        Map attributeMap = this.getAttributePackage(name);
        if(getAttributeMulti(attributeMap,name))
            throw new TException("<" + name + ">�����Ƕ�ֵ����");
        //�õ���������
        String type = getAttributeType(attributeMap,name);
        //���ֵ�����Ƿ�Ϸ�
        checkAttributeValueType(type,name,value);
        //���ֵ�ó����Ƿ�Ϸ�
        checkAttributeValueLength(type,attributeMap,name,value);
        //�����¼�
        eventAttributeListener("SET",0,name,value,getAttributeOldData(attributeMap,name));
        //����˫���崦��ֵ
        doubleBufferedValue(attributeMap,name,value);
        //����ֵ
        getData().setData(DATA,name,value);
    }
    /**
     * �õ�����ֵ
     * @param name String ������
     * @return Object ����ֵ
     * @throws TException
     */
    public Object getAttributeValue(String name) throws TException
    {
        if(getData() == null)
            throw new TException("���ݰ�û�г�ʼ��");
        if(name == null || name.trim().length() == 0)
            throw new TException(name + "������Ϊ��");
        if(!existAttribute(name))
            throw new TException(name + "������������");
        return getData().getData(DATA,name);
    }
    /**
     * �õ���ֵ���Ե����ݰ�
     * @param name String ����
     * @return Vector ���ݰ�
     * @throws TException
     */
    public Vector getAttributeMultiVector(String name)throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        return getAttributeMultiVector(attributeMap,name);
    }
    /**
     * �õ���ֵ���Ե����ݰ�
     * @param attributeMap Map ���԰�
     * @param name String ����
     * @return Vector ���ݰ�
     * @throws TException
     */
    public Vector getAttributeMultiVector(Map attributeMap,String name)throws TException
    {
        if(!getAttributeMulti(attributeMap,name))
            throw new TException("<" + name + ">���Բ��Ƕ�ֵ����");
        Object value = getData().getData(DATA,name);
        if(value == null)
        {
            value = new Vector();
            getData().setData(DATA,name,value);
        }
        if(!(value instanceof Vector))
            throw new TException("<" + name + ">����ֵ���ʹ���");
        return (Vector)value;
    }
    /**
     * ����ֵֵ
     * @param name String ����
     * @param value Object ֵ
     * @throws TException
     */
    public void addAttributeValue(String name,Object value) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        //�õ���ֵ���Ե����ݰ�
        Vector data = getAttributeMultiVector(attributeMap,name);
        //�õ���������
        String type = getAttributeType(attributeMap,name);
        //���ֵ�����Ƿ�Ϸ�
        checkAttributeValueType(type,name,value);
        //���ֵ�ó����Ƿ�Ϸ�
        checkAttributeValueLength(type,attributeMap,name,value);
        //����˫���崦���ֵ
        setDoubleBufferedValue(data.size(),attributeMap,name,value);
        //����ֵ
        data.add(value);
        //�����¼�
        eventAttributeListener("SET",data.size() - 1,name,value,getAttributeOldData(attributeMap,name));
    }
    /**
     * ��������ֵ
     * @param row int �к�
     * @param name String ����
     * @param value Object ֵ
     * @throws TException
     */
    public void setAttributeValue(int row,String name,Object value) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        //�õ���ֵ���Ե����ݰ�
        Vector data = getAttributeMultiVector(attributeMap,name);
        //�õ���������
        String type = getAttributeType(attributeMap,name);
        //���ֵ�����Ƿ�Ϸ�
        checkAttributeValueType(type,name,value);
        //���ֵ�ó����Ƿ�Ϸ�
        checkAttributeValueLength(type,attributeMap,name,value);
        //����˫���崦���ֵ
        setDoubleBufferedValue(row,attributeMap,name,value);
        while(data.size() < row + 1)
            data.add(null);
        //����ֵ
        data.set(row,value);
        //�����¼�
        eventAttributeListener("SET",row,name,value,getAttributeOldData(attributeMap,name));
    }
    /**
     * �õ�����ֵ
     * @param row int �к�
     * @param name String ����
     * @return Object ֵ
     * @throws TException
     */
    public Object getAttributeValue(int row,String name) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        //�õ���ֵ���Ե����ݰ�
        Vector data = getAttributeMultiVector(attributeMap,name);
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">������Խ�� " + row);
        return data.get(row);
    }
    /**
     * ��������ֵ
     * @param row int �к�
     * @param name String ����
     * @param value Object ֵ
     * @throws TException
     */
    public void addAttributeValue(int row,String name,Object value) throws TException
    {
        if(getAttributeCount(name) <= row)
        {
            addAttributeValue(name,value);
            return;
        }
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        //�õ���ֵ���Ե����ݰ�
        Vector data = getAttributeMultiVector(attributeMap,name);
        //�õ���������
        String type = getAttributeType(attributeMap,name);
        //���ֵ�����Ƿ�Ϸ�
        checkAttributeValueType(type,name,value);
        //���ֵ�ó����Ƿ�Ϸ�
        checkAttributeValueLength(type,attributeMap,name,value);
        //����˫���崦���ֵ����
        insertDoubleBufferedValue(row,attributeMap,name,value);
        data.add(row,value);
        //�����¼�
        eventAttributeListener("SET",row,name,value,getAttributeOldData(attributeMap,name));
    }
    /**
     * ��������ֵ(��ֵ)
     * @param row int �к�
     * @param name String ����
     * @throws TException
     */
    public void addAttributeValue(int row,String name) throws TException
    {
        if(getAttributeCount(name) <= row)
        {
            addAttributeValue(name);
            return;
        }
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        //�õ���ֵ���Ե����ݰ�
        Vector data = getAttributeMultiVector(attributeMap,name);
        //����˫���崦���ֵ����
        setDoubleBufferedValue(row,attributeMap,name);
        data.add(row,null);
    }
    /**
     * ����ֵֵ(��ֵ)
     * @param name String ����
     * @throws TException
     */
    public void addAttributeValue(String name) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        //�õ���ֵ���Ե����ݰ�
        Vector data = getAttributeMultiVector(attributeMap,name);
        //����˫���崦���ֵ
        setDoubleBufferedValue(data.size(),attributeMap,name);
        data.add(null);
    }
    /**
     * ɾ������ֵ
     * @param row int �к�
     * @param name String ����
     * @throws TException
     */
    public void removeAttributeValue(int row,String name) throws TException
    {
        if(!existAttribute(name))
            throw new TException("<" + name + ">���Բ�����");
        //�õ����԰�
        Map attributeMap = getAttributePackage(name);
        //�õ���ֵ���Ե����ݰ�
        Vector data = getAttributeMultiVector(attributeMap,name);
        if(row < 0 || row >= data.size())
            throw new TException("<" + name + ">������Խ�� " + row);
        removeAttribteInit(row,attributeMap,name);
        removeAttributeOldData(row,attributeMap,name);
        removeAttributeModified(row,attributeMap,name);
        data.remove(row);
    }
    /**
     * �õ���ֵ����ֵ�ø���
     * @param name String ����
     * @return int �ø���
     * @throws TException
     */
    public int getAttributeCount(String name) throws TException
    {
        return getAttributeMultiVector(name).size();
    }
    /**
     * �����¼�
     * @param event String ʱ������
     * @param row int �к�
     * @param name String ����
     * @param value Object ֵ
     * @param oldValue Object ��ֵ
     */
    public void eventAttributeListener(String event,int row,String name,Object value,Object oldValue)
    {

    }
}
