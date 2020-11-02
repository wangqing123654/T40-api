package com.dongyang.jdo;

import java.util.Map;
import com.dongyang.db.TDBPoolManager;

public class ServerInf extends TStrike
{
    /**
     * Ψһʵ��
     */
    private static ServerInf instance;
    /**
     * �õ�ʵ��
     * @return ServerInf
     */
    public static ServerInf getInstance()
    {
        if(instance == null)
            instance = new ServerInf();
        return instance;
    }
    /**
     * �õ����ݿ����ӳ�״̬
     * @return Map
     */
    public Map getDataBasePoolInf()
    {
        if(isClientlink())
            return (Map)callServerMethod();
        return TDBPoolManager.getInstance().getInf();
    }
    /**
     * �õ����ݿ����ӳ�״̬
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
     * �õ����ӳ������б�
     * @return String[]
     */
    public String[] getPoolNames()
    {
        if(isClientlink())
            return (String[])callServerMethod();
        return TDBPoolManager.getInstance().getPoolNames();
    }
    /**
     * �ر����ӳ�
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
