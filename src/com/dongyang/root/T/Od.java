package com.dongyang.root.T;

import java.util.Map;
import java.util.HashMap;
import com.dongyang.root.T.D.Dl;
import java.util.Iterator;

/**
 *
 * <p>Title: Od�ڿ�</p>
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
     * ���ӵ�ͼ
     */
    private Map map = new HashMap();
    /**
     * �õ����ӵ�ͼ
     * @param s String
     * @return Dl
     */
    public Dl getMap(String s)
    {
        return (Dl)map.get(s);
    }
    /**
     * �������ӵ�ͼ
     * @param s String
     * @param d Dl
     */
    public void setMap(String s,Dl d)
    {
        map.put(s,d);
    }
    /**
     * �õ��Ŵ����ӵ�ͼ
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
