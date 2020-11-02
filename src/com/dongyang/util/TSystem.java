package com.dongyang.util;

import java.util.Map;
import java.util.HashMap;

/**
 * 系统对象
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
     * 参数地图
     */
    private static Map map = new HashMap();
    /**
     * 得到系统对象
     * @param name String 名称
     * @return Object 对象
     */
    public static Object getObject(String name)
    {
        return map.get(name);
    }
    /**
     * 设置系统对象
     * @param name String 名称
     * @param obj Object 对象
     */
    public static void setObject(String name,Object obj)
    {
        map.put(name,obj);
    }
}
