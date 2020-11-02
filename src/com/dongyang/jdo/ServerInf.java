package com.dongyang.jdo;

import java.util.Map;
import com.dongyang.db.TDBPoolManager;

public class ServerInf extends TStrike
{
    /**
     * 唯一实例
     */
    private static ServerInf instance;
    /**
     * 得到实例
     * @return ServerInf
     */
    public static ServerInf getInstance()
    {
        if(instance == null)
            instance = new ServerInf();
        return instance;
    }
    /**
     * 得到数据库连接池状态
     * @return Map
     */
    public Map getDataBasePoolInf()
    {
        if(isClientlink())
            return (Map)callServerMethod();
        return TDBPoolManager.getInstance().getInf();
    }
    /**
     * 得到数据库连接池状态
     * @param name String
     * @return Map
     */
    public Map getDataBasePoolInf(String name)
    {
        if(isClientlink())
            return (Map)callServerMethod(name);
        return TDBPoolManager.getInstance().getPool(name).getInf();
    }
    /**
     * 得到连接池名称列表
     * @return String[]
     */
    public String[] getPoolNames()
    {
        if(isClientlink())
            return (String[])callServerMethod();
        return TDBPoolManager.getInstance().getPoolNames();
    }
    /**
     * 关闭连接池
     * @param name String
     * @return boolean
     */
    public boolean closeDB(String name)
    {
        if(isClientlink())
            return (Boolean)callServerMethod(name);
        TDBPoolManager.getInstance().getPool(name).close();
        return true;
    }
    public Map getUserTime(String name)
    {
        if(isClientlink())
            return (Map)callServerMethod(name);
        return TDBPoolManager.getInstance().getPool(name).getUserTime();
    }
}
