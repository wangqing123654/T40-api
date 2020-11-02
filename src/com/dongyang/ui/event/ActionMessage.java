package com.dongyang.ui.event;

import java.util.HashMap;

/**
 *
 * <p>Title: 动作消息</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.10
 * @version 1.0
 */
public class ActionMessage extends HashMap
{
    /**
     * 得到动作
     * @param name String
     * @return String
     */
    public String getAction(String name)
    {
        return (String)get(name);
    }
    /**
     * 设置动作
     * @param name String
     * @param action String
     */
    public void setAction(String name,String action)
    {
        put(name,action);
    }
    /**
     * 删除动作
     * @param name String
     */
    public void removeAction(String name)
    {
        remove(name);
    }
    /**
     * 动作是否存在
     * @param name String
     * @return boolean
     */
    public boolean exists(String name)
    {
        String s = getAction(name);
        return s != null || s.length() > 0;
    }
}
