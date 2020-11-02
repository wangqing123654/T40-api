package com.dongyang.module.base;

import java.util.Map;
import java.util.HashMap;
import com.dongyang.data.TParm;
import com.dongyang.db.TConnection;
import com.dongyang.db.TDBPoolManager;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import com.dongyang.db.TConnectionPool;

public class TModulePool
{
    private TConnectionPool pool;
    private Map map = new HashMap();
    private ThreadTime time;

    public class ThreadTime extends Thread
    {
        public boolean isStart = true;
        public void run()
        {
            while(isStart)
            {
                try{
                    this.sleep(pool.getCheckSheepTime());
                }catch(Exception e)
                {
                }
                List list = new ArrayList();
                long t = new Date().getTime();
                Iterator iterator = map.keySet().iterator();
                while(iterator.hasNext())
                {
                    String name = (String)iterator.next();
                    TModuleR r = (TModuleR)map.get(name);
                    if(t - r.getTime() > pool.getCheckTime())
                        list.add(name);
                }
                for(int i = 0;i < list.size();i ++)
                    map.remove(list.get(i));
            }
        }
    }
    public TModulePool(TConnectionPool pool)
    {
        this.pool = pool;
        start();
    }
    public void finalize()
    {
        stop();
    }
    public void start()
    {
        time = new ThreadTime();
        time.start();
    }
    public void stop()
    {
        time.isStart = false;
    }
    public TParm executeQuery(TParm parm)
    {
        String name = parm.toString();
        TModuleR r = (TModuleR)map.get(name);
        if(r != null)
        {
            return r.getResult();
        }
        Date dStart = new Date();
        TConnection connection = getConnection(parm);
        if(connection == null)
            return err(-1,"connection is null");
        TParm result = connection.executeQuery(parm);
        connection.close();
        Date dEnd = new Date();
        int t = (int)(dEnd.getTime() - dStart.getTime());
        connection.getPool().log(t,parm.toString());

        parm.removeData("VALUES");
        parm.setData("SQL",parm.getData("SQL_BAK"));
        parm.removeData("SQL_BAK");
        parm.removeData("ACTION","RESULTSET");
        parm.removeData("ACTION","PREPARED_STATEMENT");
        r = new TModuleR();
        r.setResult(result);
        if(map.size() < pool.getCheckObjCount() && result.getCount() < pool.getCheckRowCount())
            map.put(parm.toString(),r);
        return result;
    }
    /**
     * 得到连接
     * @param databaseName String 数据池名
     * @return TConnection
     */
    public TConnection getConnection(String databaseName)
    {
        return TDBPoolManager.getInstance().getConnection(databaseName);
    }
    /**
     * 得到主连接
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return TDBPoolManager.getInstance().getConnection();
    }
    /**
     * 得到连接
     * @param parm TParm ACTION:DATABASE_NAME 数据池名
     * @return TConnection
     */
    public TConnection getConnection(TParm parm)
    {
        String databaseName = parm.getValue("ACTION","DATABASE_NAME").trim();
        if(databaseName.length() == 0)
            return getConnection();
        return getConnection(databaseName);
    }
    /**
     * 组装一个错误对象
     * @param errCode int 错误编号
     * @param errText String 错误信息
     * @return TParm
     */
    public TParm err(int errCode,String errText)
    {
        return err(errCode,errText,"");
    }
    /**
     * 组装一个错误对象
     * @param errCode int 错误编号
     * @param errText String 错误信息
     * @param errName String 错误名称
     * @return TParm
     */
    public TParm err(int errCode,String errText,String errName)
    {
        TParm result = new TParm();
        result.setErr(errCode,errText,errName);
        return result;
    }
}
