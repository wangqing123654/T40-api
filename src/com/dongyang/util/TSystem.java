package com.dongyang.util;

import java.util.Map;
import java.util.HashMap;

/**
 * ϵͳ����
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.08.21
 * @version 1.0
 */
public class TSystem
{
    /**
     * ������ͼ
     */
    private static Map map = new HashMap();
    /**
     * �õ�ϵͳ����
     * @param name String ����
     * @return Object ����
     */
    public static Object getObject(String name)
    {
        return map.get(name);
    }
    /**
     * ����ϵͳ����
     * @param name String ����
     * @param obj Object ����
     */
    public static void setObject(String name,Object obj)
    {
        map.put(name,obj);
    }
}
