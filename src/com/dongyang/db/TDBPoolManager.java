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
     * ʵ��
     */
    private static TDBPoolManager instanceObject;
    /**
     * ���ӳ�
     */
    private Map pools = new HashMap();
    /**
     * ���ö���
     */
    private TConfigParm configParm;
    /**
     * �����ӳ�����
     */
    private String mainPoolName;
    /**
     * �Ƿ��ʼ��
     */
    private boolean isInit;
    /**
     * ��־ϵͳ
     */
    Log log;
    /**
     * ���Ψһʵ��
     * @return TDBPoolManager
     */
    public static TDBPoolManager getInstance()
    {
        if (instanceObject == null)
          instanceObject = new TDBPoolManager();
        return instanceObject;
    }
    /**
     * ������
     */
    public TDBPoolManager()
    {
        log = new Log();
    }
    /**
     * �������Ӷ���
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * �õ����Ӷ���
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return configParm;
    }
    /**
     * ���������ӳ�����
     * @param mainPoolName String
     */
    public void setMainPoolName(String mainPoolName)
    {
        this.mainPoolName = mainPoolName;
    }
    /**
     * �õ������ӳ�����
     * @return String
     */
    public String getMainPoolName()
    {
        return mainPoolName;
    }
    /**
     * ��ʼ��
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
            //System.out.println("===�������ӳ���==="+poolName[i]);
            loadPool(poolName[i], false);
        }

        //���������ӳ�����
        setMainPoolName(configParm.getConfig().getString(configParm.getSystemGroup(),"Pools.MainPool","MAIN"));
    }
    /**
     * ��ʼ������
     * @param dir String
     */
    public void init(String dir)
    {
        init(dir,"");
    }
    /**
     * ��ʼ��
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
     * װ�����ӳ�
     * @param name String ����
     * @param resetConfig boolean true ˢ�����ö���
     */
    public void loadPool(String name,boolean resetConfig)
    {
        //System.out.println("==================���ӳ���==================:"+name);
        if(resetConfig)
            getConfigParm().reset();
        TConnectionPool pool = new TConnectionPool();
        pool.setTag(name);
        pool.init(getConfigParm());
        pools.put(name,pool);
    }
    /**
     * �õ����ӳ�
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
     * �õ������ӳ�
     * @return TConnectionPool
     */
    public TConnectionPool getPool()
    {
        return getPool(getMainPoolName());
    }
    /**
     * �õ�������������
     * @return String
     */
    public String getPoolType()
    {
        return getPoolType("");
    }
    /**
     * �õ�������������
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
            err("û�����ݿ�� " + name);
            return null;
        }
        return pool.getType().toUpperCase();
    }
    /**
     * �õ�����
     * @param name String
     * @return Connection
     */
    public TConnection getConnection(String name)
    {
        //System.out.println("-----------getConnection(" + name + ")");
        TConnectionPool pool = getPool(name);
        if(pool == null)
        {
            err("û�����ݿ�� " + name);
            return null;
        }
        return pool.getConnection();
    }
    /**
     * �õ�������
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return getConnection(getMainPoolName());
    }
    /**
     * �ر����ӳ�
     * @param name String ����
     */
    public void closePool(String name)
    {
        TConnectionPool pool = (TConnectionPool)pools.remove(name);
        if(pool == null)
        {
            err("û�����ݿ�� " + name);
            return;
        }
        pool.close();
    }
    /**
     * �Ƿ��ʼ��
     * @return boolean
     */
    public boolean isInit()
    {
        return isInit;
    }
    /**
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        log.err(text);
    }
    /**
     * �õ�����״̬
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
     * �õ����ӳ������б�
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
