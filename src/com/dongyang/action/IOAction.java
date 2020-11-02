package com.dongyang.action;

import com.dongyang.db.TConnection;
import com.dongyang.data.TParm;
import java.util.Map;
import com.dongyang.util.LogFile;
import com.dongyang.config.TConfig;

abstract public class IOAction
{
    /**
     * 数据库连接
     */
    private TConnection connection;
    /**
     * 参数
     */
    private TParm parm;
    /**
     * 结果
     */
    private TParm result = new TParm();
    /**
     * 日至系统
     */
    private LogFile logFile = new LogFile();
    /**
     * 设置日至路径Key
     * @param s String
     */
    public void setLogPathKey(String s)
    {
        setLogPath(TConfig.getSystemValue(s));
    }
    /**
     * 设置日至路径
     * @param s String
     */
    public void setLogPath(String s)
    {
        logFile.setPath(s);
    }
    /**
     * 得到日至路径
     * @return String
     */
    public String getLogPath()
    {
        return logFile.getPath();
    }
    /**
     * 设置日至文件名
     * @param fileName String
     */
    public void setLogFileName(String fileName)
    {
        logFile.setFileName(fileName);
    }
    /**
     * 得到日至文件名
     * @return String
     */
    public String getLogFileName()
    {
        return logFile.getFileName();
    }
    /**
     * 设置日至用户信息
     * @param userInf String
     */
    public void setLogUserInf(String userInf)
    {
        logFile.setUserInf(userInf);
    }
    /**
     * 得到日至用户信息
     * @return String
     */
    public String getLogUserInf()
    {
        return logFile.getUserInf();
    }
    /**
     * 设置日至用户IP
     * @param userIP String
     */
    public void setLogUserIP(String userIP)
    {
        logFile.setIP(userIP);
    }
    /**
     * 得到用户日至IP
     * @return String
     */
    public String getLogUserIP()
    {
        return logFile.getIP();
    }
    /**
     * 输出日至
     * @param s String
     */
    public void out(String s)
    {
        logFile.out(s);
    }
    /**
     * 接口方法
     * @param connection TConnection
     * @param map Map
     * @return Map
     */
    public Map initIO(TConnection connection,Map map)
    {
        setConnection(connection);
        return initIO(map);
    }
    /**
     * 接口方法
     * @param map Map
     * @return Map
     */
    public Map initIO(Map map)
    {
        setParm(new TParm(map));
        //初始化
        onInit();
        //运行
        onRun();
        if(getResult() == null)
            setResult(new TParm());
        return getResult().getData();
    }
    /**
     * 初始化
     */
    abstract public void onInit();
    /**
     * 运行
     */
    abstract public void onRun();
    /**
     * 设置参数
     * @param parm TParm
     */
    public void setParm(TParm parm)
    {
        this.parm = parm;
    }
    /**
     * 得到参数
     * @return TParm
     */
    public TParm getParm()
    {
        return parm;
    }
    /**
     * 设置结果
     * @param result TParm
     */
    public void setResult(TParm result)
    {
        this.result = result;
    }
    /**
     * 得到结果
     * @return TParm
     */
    public TParm getResult()
    {
        return result;
    }
    /**
     * 设置连接
     * @param connection TConnection
     */
    public void setConnection(TConnection connection)
    {
        this.connection = connection;
    }
    /**
     * 得到连接
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return connection;
    }
}
