package com.dongyang.root.T;

import java.util.Map;
import java.util.HashMap;
import com.dongyang.root.T.D.Dl;
import java.util.Iterator;

/**
 *
 * <p>Title: Od内壳</p>
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
public class Od
        implements java.io.Serializable
{
    /**
     * 因子地图
     */
    private Map map = new HashMap();
    /**
     * 得到因子地图
     * @param s String
     * @return Dl
     */
    public Dl getMap(String s)
    {
        return (Dl)map.get(s);
    }
    /**
     * 设置因子地图
     * @param s String
     * @param d Dl
     */
    public void setMap(String s,Dl d)
    {
        map.put(s,d);
    }
    /**
     * 得到遗传因子地图
     * @return Map
     */
    public Map getR()
    {
        Map m = new HashMap();
        Iterator iterator = map.keySet().iterator();
        if(iterator.hasNext())
        {
            String name = (String)iterator.next();
            m.put(name,getMap(name).getR());
        }
        return m;
    }
}
