package com.dongyang.db;

import java.util.Map;
import java.util.HashMap;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.Log;
import com.dongyang.data.TParm;
import com.dongyang.util.StringTool;
import java.util.Iterator;

public class TDBPoolManager {
    /**
     * 实例
     */
    private static TDBPoolManager instanceObject;
    /**
     * 连接池
     */
    private Map pools = new HashMap();
    /**
     * 配置对象
     */
    private TConfigParm configParm;
    /**
     * 主连接池名称
     */
    private String mainPoolName;
    /**
     * 是否初始化
     */
    private boolean isInit;
    /**
     * 日志系统
     */
    Log log;
    /**
     * 获得唯一实例
     * @return TDBPoolManager
     */
    public static TDBPoolManager getInstance()
    {
        if (instanceObject == null)
          instanceObject = new TDBPoolManager();
        return instanceObject;
    }
    /**
     * 构造器
     */
    public TDBPoolManager()
    {
        log = new Log();
    }
    /**
     * 设置连接对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * 得到连接对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return configParm;
    }
    /**
     * 设置主连接池名称
     * @param mainPoolName String
     */
    public void setMainPoolName(String mainPoolName)
    {
        this.mainPoolName = mainPoolName;
    }
    /**
     * 得到主连接池名称
     * @return String
     */
    public String getMainPoolName()
    {
        return mainPoolName;
    }
    /**
     * 初始化
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm)
    {
        if(configParm == null)
            return;
        setConfigParm(configParm);
        pools = new HashMap();
        String poolName[] = configParm.getConfig().getStringList(configParm.getSystemGroup(),"Pools.item");
        for(int i = 0;i < poolName.length;i++){
            //System.out.println("===加载连接池名==="+poolName[i]);
            loadPool(poolName[i], false);
        }

        //设置主连接池名称
        setMainPoolName(configParm.getConfig().getString(configParm.getSystemGroup(),"Pools.MainPool","MAIN"));
    }
    /**
     * 初始化方法
     * @param dir String
     */
    public void init(String dir)
    {
        init(dir,"");
    }
    /**
     * 初始化
     * @param dir String
     * @param systemGroup String
     */
    public void init(String dir,String systemGroup)
    {
        if(isInit())
            return;
        System.out.println("=====================================");
        System.out.println("  JavaHis App Server");
        System.out.println("  V1.0");
        System.out.println("=====================================");
        System.out.println("start database pool...");
        //
        TConfigParm parm = new TConfigParm();
        parm.setSystemGroup(systemGroup);
        parm.setConfig(dir + "TDBInfo.x");
        parm.setConfigClass(dir + "TClass.x");
        init(parm);
        isInit = true;
    }
    /**
     * 装载连接池
     * @param name String 名称
     * @param resetConfig boolean true 刷新配置对象
     */
    public void loadPool(String name,boolean resetConfig)
    {
        //System.out.println("==================连接池名==================:"+name);
        if(resetConfig)
            getConfigParm().reset();
        TConnectionPool pool = new TConnectionPool();
        pool.setTag(name);
        pool.init(getConfigParm());
        pools.put(name,pool);
    }
    /**
     * 得到连接池
     * @param name String
     * @return TConnectionPool
     */
    public TConnectionPool getPool(String name)
    {
        if(name == null || name.length() == 0)
            name = getMainPoolName();
        return (TConnectionPool)pools.get(name);
    }
    /**
     * 得到主连接池
     * @return TConnectionPool
     */
    public TConnectionPool getPool()
    {
        return getPool(getMainPoolName());
    }
    /**
     * 得到连接数据类型
     * @return String
     */
    public String getPoolType()
    {
        return getPoolType("");
    }
    /**
     * 得到连接数据类型
     * @param name String
     * @return String
     */
    public String getPoolType(String name)
    {
        if(name == null || name.length() == 0)
            name = getMainPoolName();
        TConnectionPool pool = getPool(name);
        if(pool == null)
        {
            err("没有数据库池 " + name);
            return null;
        }
        return pool.getType().toUpperCase();
    }
    /**
     * 得到连接
     * @param name String
     * @return Connection
     */
    public TConnection getConnection(String name)
    {
        //System.out.println("-----------getConnection(" + name + ")");
        TConnectionPool pool = getPool(name);
        if(pool == null)
        {
            err("没有数据库池 " + name);
            return null;
        }
        return pool.getConnection();
    }
    /**
     * 得到主连接
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return getConnection(getMainPoolName());
    }
    /**
     * 关闭连接池
     * @param name String 名称
     */
    public void closePool(String name)
    {
        TConnectionPool pool = (TConnectionPool)pools.remove(name);
        if(pool == null)
        {
            err("没有数据库池 " + name);
            return;
        }
        pool.close();
    }
    /**
     * 是否初始化
     * @return boolean
     */
    public boolean isInit()
    {
        return isInit;
    }
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        log.err(text);
    }
    /**
     * 得到连接状态
     * @return Map
     */
    public Map getInf()
    {
        TParm parm = new TParm();
        Iterator iterator = pools.keySet().iterator();
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            parm.addData("DataBasePool",getPool(name).getInf());
        }
        return parm.getData();
    }
    /**
     * 得到连接池名称列表
     * @return String[]
     */
    public String[] getPoolNames()
    {
        String s[] = new String[pools.size()];
        int index = 0;
        Iterator iterator = pools.keySet().iterator();
        while(iterator.hasNext())
        {
            String name = (String) iterator.next();
            s[index] = name;
            index ++;
        }
        return s;
    }
    public static void main(String args[])
    {
        TConfigParm parm = new TConfigParm();
        parm.setSystemGroup("");
        parm.setConfig("DBInfo.ini");
        parm.setConfigClass("class.ini");
        TDBPoolManager m = new TDBPoolManager();
        m.init(parm);
        TConnection c = m.getConnection("lzk");
        try{

            TParm p = new TParm();
            //p.setData("SQL","update ADM_INP set OPT_DATE=? where HOSP_AREA=? and CASE_NO=?");
            //p.setData("SQL","select CASE_NO,OPT_DATE from ADM_INP where HOSP_AREA=? and CASE_NO=?");
            p.setData("SQL","select CASE_NO,OPT_DATE from ADM_INP where OPT_DATE>=<OPT_DATE1> and OPT_DATE<=<OPT_DATE2>");
            p.setData("ACTION","DNULL",true);
            p.setData("ACTION","TRIM",true);
            p.setData("ACTION","SNIPPET",false);
            p.setData("ACTION","START_ROW",0);
            p.setData("ACTION","END_ROW",0);
            p.setData("ACTION","PREPARE",true);
            //p.addData("VALUES",StringTool.getTimestamp("2007-10-01 00:00:00","yyyy-MM-dd hh:mm:ss"));
            //p.addData("VALUES",StringTool.getTimestamp("2007-10-01 23:59:59","yyyy-MM-dd hh:mm:ss"));
            p.setData("OPT_DATE1",StringTool.getTimestamp("2007-10-01 00:00:00","yyyy-MM-dd hh:mm:ss"));
            p.setData("OPT_DATE2",StringTool.getTimestamp("2007-10-01 23:59:59","yyyy-MM-dd hh:mm:ss"));
            //p.addData("VALUES","HIS");
            //p.addData("VALUES","0710230000391");
            //p.addData("VALUES",StringTool.getDate("2007-10-23 10:13:08","yyyy-MM-dd hh:mm:ss"));
            /*c.executeParmQuery(p);
            System.out.println(c.parseParmData(p));
            p.setData("ACTION","START_ROW",1);
            p.setData("ACTION","END_ROW",1);
            System.out.println(c.parseParmData(p));
            System.out.println(c.closeParmData(p));
            */
           //System.out.println(c.executeParmUpdate(p));

           //c.commit();
            System.out.println(c.executeQuery(p));
            /*int count = 1000;
            ResultSet set[] = new ResultSet[count];
            Statement statement[] = new Statement[count];
            for(int i = 0;i < count;i++)
            {
                System.out.println("i=" + i);
                statement[i] = c.createStatement();
                set[i] = c.executeQuery(statement[i],p);
                c.executeQuery1(set[i],p);
            }
            for(int i = 0;i < count;i++)
            {
                set[i].close();
                statement[i].close();
            }
*/
            c.close();
        }catch(Exception e)
        {

        }
        /*TParm p = new TParm();
        p.setData("SQL","select * from ADM_INP");
        p.setData("ACTION","DNULL",true);
        p.setData("ACTION","TRIM",true);
        c.executeQuery(p);*/
        /*Connection c1 = m.getConnection("lzk");
        Connection c2 = m.getConnection("lzk");
        Connection c3 = m.getConnection("lzk");
        Connection c4 = m.getConnection("lzk");
        System.out.println("getConnectCount=" + m.getPool("lzk").getConnectCount());
        try{
            c.close();
            System.out.println("getConnectCount=" + m.getPool("lzk").getConnectCount());
            System.out.println("getUserCount=" + m.getPool("lzk").getUserCount());
            System.out.println("getIdlesseCount=" + m.getPool("lzk").getIdlesseCount());
            c1.close();
            System.out.println("getConnectCount=" + m.getPool("lzk").getConnectCount());
            System.out.println("getUserCount=" + m.getPool("lzk").getUserCount());
            System.out.println("getIdlesseCount=" + m.getPool("lzk").getIdlesseCount());
            c2.close();
            System.out.println("getConnectCount=" + m.getPool("lzk").getConnectCount());
            System.out.println("getUserCount=" + m.getPool("lzk").getUserCount());
            System.out.println("getIdlesseCount=" + m.getPool("lzk").getIdlesseCount());
            c3.close();
            System.out.println("getConnectCount=" + m.getPool("lzk").getConnectCount());
            System.out.println("getUserCount=" + m.getPool("lzk").getUserCount());
            System.out.println("getIdlesseCount=" + m.getPool("lzk").getIdlesseCount());
            //c4.close();
            m.getPool("lzk").close();
            System.out.println("  getConnectCount=" + m.getPool("lzk").getConnectCount());
            System.out.println("  getUserCount=" + m.getPool("lzk").getUserCount());
            System.out.println("  getIdlesseCount=" + m.getPool("lzk").getIdlesseCount());
            //m.closePool("lzk");


            System.out.println("test.getConnectCount=" + m.getPool("test").getConnectCount());
        }catch(Exception e){
            e.printStackTrace();
        }        */

    }
}
