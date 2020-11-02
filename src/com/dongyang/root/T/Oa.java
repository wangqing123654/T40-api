package com.dongyang.root.T;

import java.util.Map;
import java.util.HashMap;
import com.dongyang.util.FileTool;

/**
 *
 * <p>Title: Oa���ݷ�����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.29
 * @version 1.0
 */
public class Oa
        implements java.io.Serializable
{
    /**
     * ���
     */
    private Ot ot;
    /**
     * �߼�����
     */
    private Map map = new HashMap();
    /**
     * �������
     * @param ot Ot
     */
    public void setOt(Ot ot)
    {
        this.ot = ot;
    }
    /**
     * �õ����
     * @return Ot
     */
    public Ot getOt()
    {
        return ot;
    }
    /**
     * ���
     * @param io int ͨ��
     * @param m Object ��Ϣ
     * @return Object
     */
    public Object inM(int io,Object m)
    {
        System.out.println(getOt().getID() + ":" +io + " " + m);
        return "OK";
    }
    /**
     * ���
     * @param io int ͨ��
     * @param m String ��Ϣ
     * @param parm Object ����
     * @return Object
     */
    public Object inM(int io,String m,Object parm)
    {
        Object value = getMap(m);
        if(value != null)
            return value;
        return null;
    }
    /**
     * �õ�����
     * @param name String
     * @return Object
     */
    public Object getMap(String name)
    {
        return map.get(name);
    }
    /**
     * ���ô���
     * @param name String
     * @param object Object
     */
    public void setMap(String name,Object object)
    {
        map.put(name,object);
    }
    /**
     * ����
     * @param fileName String �ļ���
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean save(String fileName)
    {
        try
        {
            FileTool.setObject(fileName, map);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * ��ȡ
     * @param fileName String �ļ���
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean load(String fileName)
    {
        try
        {
            Map data = (Map)FileTool.getObject(fileName);
            if(data == null)
                return false;
            map = data;
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String args[])
    {
        Oa oa = new Oa();
    }
}
