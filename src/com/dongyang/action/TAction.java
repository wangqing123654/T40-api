package com.dongyang.action;

import com.dongyang.data.TParm;
import com.dongyang.util.Log;
import com.dongyang.db.TDBPoolManager;
import com.dongyang.db.TConnection;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.Service.DataService;
import com.dongyang.config.TConfigParm;

public class TAction {
    private static Map connectPools = new HashMap();
    private static Map resultPools = new HashMap();
    /**
     * 传入参数
     */
    private TParm inParm;
    /**
     * 错误信息
     */
    private TParm errParm;
    /**
     * 日志系统
     */
    private Log log;
    /**
     * 配置对象
     */
    private TConfigParm configParm = new TConfigParm();
    /**
     * 构造器
     */
    public TAction()
    {
        log = new Log();
    }
    /**
     * 设置传入参数
     * @param inParm TParm
     */
    public void setInParm(TParm inParm)
    {
        this.inParm = inParm;
    }
    /**
     * 得到传入参数
     * @return TParm
     */
    public TParm getInParm()
    {
        return inParm;
    }
    /**
     * 设置用户IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        log.setIP(IP);
    }
    /**
     * 得到用户IP
     * @return String
     */
    public String getIP()
    {
        return log.getIP();
    }
    /**
     * 设置错误信息
     * @param parm TParm
     */
    public void setErrParm(TParm parm)
    {
        this.errParm = parm;
    }
    /**
     * 得到错误信息
     * @return TParm
     */
    public TParm getErrParm()
    {
        return errParm;
    }
    /**
     * 得到结果池
     * @return Map
     */
    public Map getResultPools()
    {
        return resultPools;
    }
    /**
     * 得到连接
     * @param databaseName String 数据池名
     * @return TConnection
     */
    public TConnection getConnection(String databaseName)
    {
        TConnection connection = TDBPoolManager.getInstance().getConnection(databaseName);
        if(connection == null)
        {
            setErrParm(err( -1, "无法取得数据库连接", databaseName));
            return null;
        }
        return connection;
    }
    /**
     * 得到主连接
     * @return TConnection
     */
    public TConnection getConnection()
    {
        TConnection connection = TDBPoolManager.getInstance().getConnection();
        if(connection == null)
        {
            setErrParm(err( -1, "无法取得数据库主连接"));
            return null;
        }
        return connection;
    }
    /**
     * 得到数据库连接
     * @param parm TParm
     * @return TConnection
     */
    public TConnection getConnection(TParm parm)
    {
        boolean connectionContinue = parm.getBoolean("ACTION","CONNECTION_CONTINUE");
        String connectionCode = parm.getValue("ACTION","CONNECTION_CODE");
        if(connectionContinue && connectionCode.length() > 0)
        {
            TConnection connection = (TConnection)connectPools.get(connectionCode);
            if(connection == null || connection.isClosed())
            {
                connectPools.remove(connectionCode);
                setErrParm(err(-1, "连接超时 " + connectionCode,""));
                return null;
            }
            return connection;
        }

        String databaseName = parm.getValue("ACTION","DATABASE_NAME").trim();
        TConnection connection;
        if(databaseName.length() == 0)
            connection = getConnection();
        else
            connection = getConnection(databaseName);
        if(connection == null)
            return null;
        if(connectionContinue)
        {
            connectPools.put(connection.getIndexCode(), connection);
            connection.setParentMap(connectPools);
        }
        return connection;
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
        //err(errCode + " " + errText + " EN:" + errName);
        return result;
    }
    /**
     * 创建一个错误信息
     * @param parm TParm 参数
     * @return TParm
     */
    public TParm err(TParm parm)
    {
        TParm result = new TParm();
        result.setErr(parm);
        err(parm.getErrCode() + " " + parm.getErrText() + " EN:" + parm.getErrName());
        return result;
    }
    /**
     * 得到服务对象
     * @return DataService
     */
    public DataService getService()
    {
        return DataService.getInstance();
    }
    /**
     * 执行一个其他Action
     * @param parm TParm 数据
     * @param className String 类名
     * @param methodName String 方法名
     * @return TParm
     */
    public TParm executeAction(TParm parm,String className,String methodName)
    {
        if(parm == null)
            return null;
        return executeAction(parm.getData(),className,methodName);
    }
    /**
     * 执行一个其他Action
     * @param inObject Map 数据
     * @param className String 类名
     * @param methodName String 方法名
     * @return TParm
     */
    public TParm executeAction(Map inObject,String className,String methodName)
    {
        if(inObject == null)
            return null;
        TParm parm = new TParm();
        parm.setData(inObject);
        parm.setData("SYSTEM","IP",getInParm().getData("SYSTEM","IP"));
        parm.setData("SYSTEM","INPUT_STREAM",getInParm().getData("SYSTEM","INPUT_STREAM"));
        parm.setData("SYSTEM","OUTPUT_STREAM",getInParm().getData("SYSTEM","OUTPUT_STREAM"));
        parm.setData("SYSTEM","REQUEST",getInParm().getData("SYSTEM","REQUEST"));
        parm.setData("SYSTEM","RESPONSE",getInParm().getData("SYSTEM","RESPONSE"));
        parm.setData("SYSTEM","REALPATH",getInParm().getData("SYSTEM","REALPATH"));
        parm.setData("SYSTEM","ACTION_CLASS_NAME",className);
        parm.setData("SYSTEM","ACTION_METHOD_NAME",methodName);
        return getService().executeAction(parm);
    }
    /**
     * 设置配置对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * 得到配置对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return configParm;
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
}
