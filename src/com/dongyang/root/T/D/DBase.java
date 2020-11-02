package com.dongyang.root.T.D;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * <p>Title: ���ӵ�Դ</p>
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
public class DBase
        implements java.io.Serializable
{
    /**
     * ����
     */
    private String type;
    /**
     * ״̬��ͼ
     */
    private Map map = new HashMap();
    /**
     * ��������
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * �õ�״̬
     * @param state int
     * @return Map
     */
    public Map getMap(int state)
    {
        return (Map)map.get(state);
    }
    /**
     * ����״̬
     * @param state int
     * @param obj Map
     */
    public void setMap(int state,Map obj)
    {
        map.put(state,obj);
    }
    /**
     * ���ö���
     * @param state int
     * @param m String
     * @param parm Object[]
     */
    public void setAction(int state,String m,Object parm[])
    {
        Map map = getMap(state);
        if(map == null)
        {
            map = new HashMap();
            setMap(state,map);
        }
        map.put(m,parm);
    }
    /**
     * �õ�����
     * @param state int
     * @param m String
     * @return Object[]
     */
    public Object[] getAction(int state,String m)
    {
        Map map = getMap(state);
        if(map == null)
            return null;
        return (Object[])map.get(m);
    }
}
