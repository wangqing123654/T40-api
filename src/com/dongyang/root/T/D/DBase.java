package com.dongyang.root.T.D;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * <p>Title: 因子点源</p>
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
     * 类型
     */
    private String type;
    /**
     * 状态地图
     */
    private Map map = new HashMap();
    /**
     * 设置类型
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * 得到状态
     * @param state int
     * @return Map
     */
    public Map getMap(int state)
    {
        return (Map)map.get(state);
    }
    /**
     * 设置状态
     * @param state int
     * @param obj Map
     */
    public void setMap(int state,Map obj)
    {
        map.put(state,obj);
    }
    /**
     * 设置动作
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
     * 得到动作
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
