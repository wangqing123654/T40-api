package com.dongyang.db;

import java.sql.Connection;
import com.dongyang.util.Log;
import java.sql.DriverManager;
import java.util.Vector;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.StringTool;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.File;
//import com.dongyang.module.base.TModulePool;
import java.util.Map;
import com.dongyang.data.TParm;

public class TConnectionPool {
    private String tag;
    private String type;
    private String address;
    private String dbName;
    private String userName;
    private String password;
    private String url;
    private int connectCount = 0;
    private int maxConnectCount = 0;
    private int minConnectCount = 0;
    private int defaultConnectCount = 0;
    private Vector idlessePool = new Vector();
    private Vector userPool = new Vector();
    private int closeTime;
    private boolean isResumeConnectCount = true;
    private boolean isClose;
    private int sqltime;
    private String sqllog;
    private boolean checkOFF;
    private int checkObjCount;
    private int checkRowCount;
    private int checkSheepTime;
    private int checkTime;
    //private TModulePool mPool;
    /**
     * ����ʱ���ʽ
     */
    public static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss.SS");
    /**
     * ��־ϵͳ
     */
    Log log;
    public TConnectionPool()
    {
        log = new Log();
    }
    /**
     * ������ʱ�رյ�ʱ�� 0ȡ����ʱ�ر�
     * @param closeTime int
     */
    public void setCloseTime(int closeTime)
    {
        this.closeTime = closeTime;
    }
    /**
     * �õ���ʱ�رյ�ʱ��
     * @return int
     */
    public int getCloseTime()
    {
        return closeTime;
    }
    /**
     * ����Ĭ��������
     * @param count int
     */
    public void setDefaultConnectCount(int count)
    {
        defaultConnectCount = count;
    }
    /**
     * �õ�Ĭ��������
     * @return int
     */
    public int getDefaultConnectCount()
    {
        return defaultConnectCount;
    }
    /**
     * ������������Ĭ��������ʱ,�黹���ӵ�ʱ���Ƿ���С��ԭ�������Ӵ�С
     * @param flg boolean
     */
    public void setResumeConnectCount(boolean flg)
    {
        isResumeConnectCount = flg;
    }
    /**
     * ������������Ĭ��������ʱ,�黹���ӵ�ʱ���Ƿ���С��ԭ�������Ӵ�С
     * @return boolean
     */
    public boolean isResumeConnectCount()
    {
        return isResumeConnectCount;
    }
    public void setSQLTime(int sqltime)
    {
        this.sqltime = sqltime;
    }
    public int getSQLTime()
    {
        return sqltime;
    }
    public void setSQLLog(String sqllog)
    {
        this.sqllog = sqllog;
    }
    public String getSQLLog()
    {
        return sqllog;
    }
    public void setCheckOFF(boolean checkOFF)
    {
        this.checkOFF = checkOFF;
    }
    public boolean getCheckOFF()
    {
        return checkOFF;
    }
    public void setCheckObjCount(int checkObjCount)
    {
        this.checkObjCount = checkObjCount;
    }
    public int getCheckObjCount()
    {
        return checkObjCount;
    }
    public void setCheckRowCount(int checkRowCount)
    {
        this.checkRowCount = checkRowCount;
    }
    public int getCheckRowCount()
    {
        return checkRowCount;
    }
    public void setCheckSheepTime(int checkSheepTime)
    {
        this.checkSheepTime = checkSheepTime;
    }
    public int getCheckSheepTime()
    {
        return checkSheepTime;
    }
    public void setCheckTime(int checkTime)
    {
        this.checkTime = checkTime;
    }
    public int getCheckTime()
    {
        return checkTime;
    }
    /*public TModulePool getMPool()
    {
        if(mPool == null)
           mPool = new TModulePool(this);
       return mPool;

    }*/
    /**
     * ��ʼ��
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm)
    {
        setType(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".Type"));
        setAddress(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".Address"));
        setURL(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".URL"));
        setDBName(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".DBName"));
        setUserName(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".UserName"));
        setPassword(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".Password"));
        setCloseTime(parm.getConfig().getInt(parm.getSystemGroup(),getTag() + ".CloseTime",0));
        setDefaultConnectCount(parm.getConfig().getInt(parm.getSystemGroup(),getTag() + ".DefaultConnectCount",0));
        setSQLTime(parm.getConfig().getInt(parm.getSystemGroup(),getTag() + ".sqltime",1));
        setSQLLog(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".sqllog"));

        setCheckOFF(StringTool.getBoolean(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".checkOFF","N")));
        setCheckObjCount(parm.getConfig().getInt(parm.getSystemGroup(),getTag() + ".checkObjCount",100));
        setCheckRowCount(parm.getConfig().getInt(parm.getSystemGroup(),getTag() + ".checkRowCount",1000));
        setCheckSheepTime(parm.getConfig().getInt(parm.getSystemGroup(),getTag() + ".checkSheepTime",100));
        setCheckTime(parm.getConfig().getInt(parm.getSystemGroup(),getTag() + ".checkTime",300));
        minConnectCount = getDefaultConnectCount();
        maxConnectCount = getDefaultConnectCount();
        setResumeConnectCount(StringTool.getBoolean(parm.getConfig().getString(parm.getSystemGroup(),getTag() + ".isResumeConnectCount","N")));
        if(getDefaultConnectCount() > 0)
            for(int i = 0;i < getDefaultConnectCount();i++)
                idlessePool.add(createConnection());
    }
    /**
     * �õ���ǰ��ʱ��
     * @return String
     */
    public static String getTime() {
        return dateFormat.format(new Date());
    }
    public void log(int time,String sql)
    {
        if(time < this.getSQLTime())
            return;
        if(getSQLLog() == null || getSQLLog().length() == 0)
            return;
        int i = getSQLLog().lastIndexOf("\\");
        String dir = getSQLLog().substring(0,i);
        File f = new File(dir);
        if(!f.exists())
            f.mkdirs();
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(getSQLLog(),true));
            String s = getTime() + "\t" + time + "\t" + sql + "\r";
            bw.write(s);
            bw.close();
        }catch(Exception e)
        {

        }
    }
    /**
     * ���ñ�ǩ
     * @param tag String
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    /**
     * �õ���ǩ
     * @return String
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * �������ݿ�����
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �õ����ݿ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * �������ݿ����IP��ַ
     * @param address String
     */
    public void setAddress(String address)
    {
        this.address = address;
    }
    /**
     * �õ����ݿ����IP��ַ
     * @return String
     */
    public String getAddress()
    {
        return address;
    }
    /**
     * �������ӵ�ַ
     * @param url String
     */
    public void setURL(String url)
    {
        this.url = url;
    }
    /**
     * �õ����ӵ�ַ
     * @return String
     */
    public String getURL()
    {
        return url;
    }
    /**
     * �������ݿ�����
     * @param dbName String
     */
    public void setDBName(String dbName)
    {
        this.dbName = dbName;
    }
    /**
     * �õ����ݿ�����
     * @return String
     */
    public String getDBName()
    {
        return dbName;
    }
    /**
     * �������ݿ��û�����
     * @param userName String
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    /**
     * �õ����ݿ��û�����
     * @return String
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * �������ݿ��¼����
     * @param password String
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    /**
     * �õ����ݿ��¼����
     * @return String
     */
    public String getPassword()
    {
        return password;
    }
    /**
     * ����������
     */
    private void addConnectCount()
    {
        connectCount ++;
        //System.out.println("-----addConnectCount ��ǰ������----"+connectCount);
        //System.out.println("-----addConnectCount ���������----"+maxConnectCount);
        //
        if(connectCount > maxConnectCount)
            maxConnectCount = connectCount;
    }
    /**
     * ��С������
     */
    private void removeConnectCount()
    {
        connectCount --;
        //System.out.println("-----removeConnectCount ��ǰ������----"+connectCount);
        //System.out.println("-----removeConnectCount ��С������----"+minConnectCount);
        if(connectCount < minConnectCount)
            minConnectCount = connectCount;
    }
    /**
     * �õ���ǰ���ݿ�������
     * @return int
     */
    public int getConnectCount()
    {
        return connectCount;
    }
    /**
     * �õ���ʷ���������
     * @return int
     */
    public int getMaxConnectCount()
    {
        return maxConnectCount;
    }
    /**
     * �õ���ʷ��С������
     * @return int
     */
    public int getMinConnectCount()
    {
        return minConnectCount;
    }
    /**
     * ʹ�����Ӹ���
     * @return int
     */
    public int getUserCount()
    {
        return userPool.size();
    }
    /**
     * �������Ӹ���
     * @return int
     */
    public int getIdlesseCount()
    {
        return idlessePool.size();
    }
    /**
     * ��������
     * @return Connection
     */
    private Connection createConnection()
    {
        //System.out.println("-----------createConnection()");
        Connection conn = null;
        try{
            if ("oracle".equalsIgnoreCase(getType())) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String sql = getURL();
                if(sql == null || sql.trim().length() == 0)
                    sql = "jdbc:oracle:thin:@" + getAddress() + ":1521:" +
                          getDBName();
                //System.out.println("-----------createConnection() start");
                conn = DriverManager.getConnection(sql, getUserName(),
                        getPassword());
                //System.out.println("-----------createConnection() conn=" + conn);
            }
            else if("sql".equalsIgnoreCase(getType()))
            {
                /*Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                String sql = "jdbc:microsoft:sqlserver://" + getAddress() + ":1433;SelectMethod=cursor";*/
            	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String sql = "jdbc:sqlserver://" + getAddress() + ":1433;SelectMethod=cursor";
                conn = DriverManager.getConnection(sql, getUserName(), getPassword());
                conn.setCatalog(getDBName());
            }
            else if("db2".equalsIgnoreCase(getType()))
            {
                Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
                String sql = "jdbc:db2:" + getDBName();
                //System.out.println("sql======="+sql);
                //System.out.println("username======="+getUserName());
                //System.out.println("Password======="+getPassword());
                conn = DriverManager.getConnection(sql, getUserName(), getPassword());
                conn.setTransactionIsolation(1);
            }
            else if("catch".equalsIgnoreCase(getType()))
            {
                Class.forName("com.intersys.jdbc.CacheDriver");
                String sql = "jdbc:Cache://" + getAddress() + ":1972/" + getDBName();
                conn = DriverManager.getConnection(sql, getUserName(), getPassword());
            }
            else if("postgre".equalsIgnoreCase(getType()))
            {
                Class.forName("org.postgresql.Driver").newInstance();
                String sql = "jdbc:postgresql://" + getAddress() + "/" + getDBName();
                conn = DriverManager.getConnection(sql, getUserName(), getPassword());
            }
            conn.setAutoCommit(false);
            addConnectCount();
        }catch(Exception e)
        {
            e.printStackTrace();
            err(e.getMessage());
            return null;
        }
        return conn;
    }
    /**
     * �õ�����
     * @return Connection
     */
    public synchronized TConnection getConnection()
    {
        Connection conn = null;
        if(isClose)
            return null;
        while(idlessePool.size() > 0)
        {
            conn = (Connection)idlessePool.remove(0);
            if(conn == null)
            {
                removeConnectCount();
                continue;
            }
            try{
                if (conn.isClosed()) {
                    removeConnectCount();
                    conn = null;
                    continue;
                }
            }catch(Exception e)
            {
                removeConnectCount();
                conn = null;
                continue;
            }
            break;
        }
        if(conn == null)
        {
            conn = createConnection();
            if(conn == null)
                return null;
        }
        TConnection tconnection = new TConnection(conn,this);
        tconnection.setCloseTime(getCloseTime());
        tconnection.setTimeOut(new Date().getTime());
        userPool.add(tconnection);
        if(getCloseTime() > 0)
            tconnection.start();
        return tconnection;
    }
    /**
     * �黹����
     * @param conn TConnection
     */
    public synchronized void setConnection(TConnection conn)
    {
        if(idlessePool.indexOf(conn.getConn()) != -1)
            return;
        if(isClose)
            return;
        if(conn == null)
            return;
        userPool.remove(conn);
        try{
            if (conn.getConn().isClosed())
            {
                removeConnectCount();
                return;
            }
            conn.getConn().rollback();
            conn.getConn().setAutoCommit(false);
            if(isResumeConnectCount() && idlessePool.size() > 0 && getConnectCount() > getDefaultConnectCount())
            {
                conn.getConn().close();
                removeConnectCount();
                return;
            }
            //test lx
            //System.out.println("------idlessePool ago-------"+idlessePool.size());

            idlessePool.add(conn.getConn());
            
            //System.out.println("------idlessePool late-------"+idlessePool.size());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * �ر����ӳ�
     */
    public synchronized void close()
    {
        //isClose = true;
        //�ͷſ��г�
        for(int i = idlessePool.size() - 1;i >= 0;i--)
        {
            Connection c = (Connection)idlessePool.remove(i);
            removeConnectCount();
            try{
                c.close();
            }catch(Exception e)
            {
            }
        }
        //�ͷ�ʹ�ó�
        for(int i = userPool.size() - 1;i >= 0;i--)
        {
            TConnection c = (TConnection)userPool.remove(i);
            removeConnectCount();
            try{
                c.close();
                c.getConn().close();
            }catch(Exception e)
            {
            }
        }
        connectCount = 0;
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
        parm.setData("Tag",getTag());
        parm.setData("MaxConnectCount",getMaxConnectCount());
        parm.setData("DefaultConnectCount",getDefaultConnectCount());
        parm.setData("Type",getType());
        parm.setData("Address",getAddress());
        parm.setData("DBName",getDBName());
        parm.setData("UserName",getUserName());
        parm.setData("ConnectCount",getConnectCount());
        parm.setData("IdlesseCount",getIdlesseCount());
        parm.setData("UserCount",getUserCount());
        parm.setData("Time",new Date());
        return parm.getData();
    }
    public synchronized Map getUserTime()
    {
        TParm parm = new TParm();
        for(int i = 0;i < userPool.size();i++)
        {
            TConnection con = (TConnection)userPool.get(i);
            parm.addData("TConnection",con.getTimeOut() + " " + con.getSQL());
        }
        return parm.getData();
    }
}
