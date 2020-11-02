package com.dongyang.module.base;

import com.dongyang.db.TDBPoolManager;
import com.dongyang.db.TConnection;
import com.dongyang.util.Log;
import com.dongyang.data.TParm;
import java.util.Date;
import com.dongyang.db.TConnectionPool;

public class TModuleBase {
    /**
     * 日志系统
     */
    private Log log;
    /**
     * 用户IP
     */
    private String IP;
    /**
     * 构造器
     */
    public TModuleBase()
    {
        setLog(new Log());
    }
    /**
     * 设置用户IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        this.IP = IP;
        log.setIP(IP);
    }
    /**
     * 得到用户IP
     * @return String
     */
    public String getIP()
    {
        return IP;
    }
    /**
     * 设置Log对象
     * @param log Log
     */
    public void setLog(Log log)
    {
        this.log = log;
    }
    /**
     * 得到Log对象
     * @return Log
     */
    public Log getLog()
    {
        return log;
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
        String databaseName = parm.getValue("ACTION","DATABASE_NAME");
        parm.setData("SYSTEM","EXE_STATE","getConnection " + databaseName);
        if(databaseName == null || databaseName.trim().length() == 0)
            return getConnection();
        return getConnection(databaseName);
    }
    /**
     * 执行select语句
     * @param dbname String 数据库名
     * @param sql String sql语句
     * @return TParm
     */
    public TParm executeQuery(String dbname,String sql)
    {
        return executeQuery(dbname,sql,true,true,false,true);
    }
    /**
     * 执行select语句
     * @param sql String sql语句
     * @return TParm
     */
    public TParm executeQuery(String sql)
    {
        return executeQuery("",sql);
    }
    /**
     * 执行select语句
     * @param dbname String 数据库名
     * @param sql String sql语句
     * @param dnull boolean
     * @param trim boolean
     * @param snippet boolean
     * @param prepart boolean
     * @return TParm
     */
    public TParm executeQuery(String dbname,String sql,boolean dnull,boolean trim,
                              boolean snippet,boolean prepart)
    {
        TParm parm = new TParm();
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","DNULL",dnull);
        parm.setData("ACTION","TRIM",trim);
        parm.setData("ACTION","SNIPPET",snippet);
        parm.setData("ACTION","PREPARE",prepart);

        TParm result = executeQuery(parm);

        return result;
    }
    /**
     * 执行select语句
     * @param sql String sql语句
     * @param dnull boolean
     * @param trim boolean
     * @param snippet boolean
     * @param prepart boolean
     * @return TParm
     */
    public TParm executeQuery(String sql,boolean dnull,boolean trim,
                              boolean snippet,boolean prepart)
    {
        return executeQuery("",sql,dnull,trim,snippet,prepart);
    }
    /**
     * 执行select语句
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm executeQuery(TParm parm,TConnection connection)
    {
        if(connection == null)
        {
            //parm.setData("SYSTEM","EXE_STATE","TModuleBase.executeQuery connection == null ");
            return err( -1, "connection is null");
        }
        //parm.setData("SYSTEM","EXE_STATE","TModuleBase.executeQuery begin ");
        TParm result = connection.executeQuery(parm);
        //parm.setData("SYSTEM","EXE_STATE","TModuleBase.executeQuery end result=" + result);
        return result;
    }
    /**
     * 执行select语句
     * @param parm TParm
     * @return TParm
     */
    public TParm executeQuery(TParm parm)
    {
        parm.setData("SYSTEM","EXE_STATE","TModuleBase.executeQuery 1 ");
        //String databaseName = parm.getValue("ACTION","DATABASE_NAME").trim();
        //if(databaseName.length() == 0)
        //    databaseName = TDBPoolManager.getInstance().getMainPoolName();
        //TConnectionPool pool = TDBPoolManager.getInstance().getPool(databaseName);

        //if(pool.getCheckOFF())
        //    return pool.getMPool().executeQuery(parm);

        //Date dStart = new Date();

        TConnection connection = getConnection(parm);
        TParm result=null;
        try{
         result = executeQuery(parm,connection);
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	//System.out.println("TModuleBase executeQuery  connection closed.");
        	connection.close();
        }
        //Date dEnd = new Date();
        //int t = (int)(dEnd.getTime() - dStart.getTime());
        //connection.getPool().log(t,parm.toString());

        return result;
    }
    /**
     * 执行select语句
     * @param sql String
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm executeQuery(String sql,TParm parm,TConnection connection)
    {
        if(parm == null)
            return err(-1,"parm is null");
        parm.setData("SQL",sql);
        parm.setData("ACTION","PREPARE",true);
        return executeQuery(parm,connection);
    }
    /**
     * 执行select语句
     * @param sql String sql语句
     * @param parm TParm 参数
     * @return TParm
     */
    public TParm executeQuery(String sql,TParm parm)
    {
        //parm.setData("SYSTEM","EXE_STATE","TModuleBase.executeQuery 2 ");
        if(parm == null)
            return err(-1,"parm is null");
        parm.setData("SQL",sql);
        parm.setData("ACTION","PREPARE",true);
        return executeQuery(parm);
    }
    /**
     * 执行select语句
     * @param dbname String 数据库名
     * @param sql String sql语句
     * @param parm TParm
     * @return TParm
     */
    public TParm executeQuery(String dbname,String sql,TParm parm)
    {
        if(parm == null)
            return err(-1,"parm is null");
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","PREPARE",true);
        return executeQuery(parm);
    }
    /**
     * 执行select语句
     * @param sql String sql语句
     * @param outType String 出参 "MR_NO:VARCHAR"
     * @return TParm
     */
    public TParm executeCall(String sql,String outType)
    {
        return executeCall(sql,outType,true,true);
    }
    /**
     * 执行select语句
     * @param sql String sql语句
     * @param dnull boolean 去空
     * @param trim boolean 去空格
     * @param outType String 出参 "MR_NO:VARCHAR"
     * @return TParm
     */
    public TParm executeCall(String sql,String outType,boolean dnull,boolean trim)
    {
        return executeCall("",sql,outType,dnull,trim);
    }
    /**
     * 执行存储过程语句
     * @param dbname String 数据库名
     * @param sql String sql语句
     * @param outType String 出参 "MR_NO:VARCHAR"
     * @return TParm
     */
    public TParm executeCall(String dbname,String sql,String outType)
    {
        return executeCall(dbname,sql,outType,true,true);
    }

    /**
     * 执行存储过程语句
     * @param dbname String 数据库名
     * @param sql String sql语句
     * @param outType String 出参 "MR_NO:VARCHAR"
     * @param dnull boolean 去空
     * @param trim boolean 去空格
     * @return TParm
     */
    public TParm executeCall(String dbname,String sql,String outType,boolean dnull,boolean trim)
    {
        TParm parm = new TParm();
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","DNULL",dnull);
        parm.setData("ACTION","TRIM",trim);
        parm.setData("ACTION","OUT_TYPE",outType);
        return executeCall(parm);
    }
    /**
     * 执行存储过程语句
     * @param sql String sql语句
     * @param outType String 出参 "MR_NO:VARCHAR"
     * @param parm TParm
     * @return TParm
     */
    public TParm executeCall(String sql,String outType,TParm parm)
    {
        if(parm == null)
            return err(-1,"parm is null");
        parm.setData("SQL",sql);
        parm.setData("ACTION","OUT_TYPE",outType);
        return executeCall(parm);
    }
    /**
     * 执行存储过程语句
     * @param dbname String 数据库名
     * @param sql String sql语句
     * @param outType String 出参 "MR_NO:VARCHAR"
     * @param parm TParm
     * @return TParm
     */
    public TParm executeCall(String dbname,String sql,String outType,TParm parm)
    {
        if(parm == null)
            return err(-1,"parm is null");
        parm.setData("SQL",sql);
        parm.setData("ACTION","OUT_TYPE",outType);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        return executeCall(parm);
    }
    /**
     * 执行存储过程语句
     * @param parm TParm
     * @return TParm
     */
    public TParm executeCall(TParm parm)
    {
        TConnection connection = getConnection(parm);
        if(connection == null)
            return err(-1,"connection is null");
        TParm result = connection.executeCall(parm);
        connection.close();
        return result;
    }
    /**
     * 执行更新语句
     * @param connection TConnection
     * @param parm TParm
     * @return TParm
     */
    public TParm executeUpdate(TParm parm,TConnection connection)
    {
        if(!connection.executeUpdate(parm))
        {
            connection.rollback();
            return parm.getErrParm();
        }
        return new TParm();
    }
    /**
     * 执行更新语句
     * @param sql String sql语句
     * @param connection TConnection
     * @return TParm
     */
    public TParm executeUpdate(String sql,TConnection connection)
    {
        return executeUpdate(sql,true,true,false,true,connection);
    }
    /**
     * 执行更新语句
     * @param sql String sql语句
     * @param parm TParm 参数
     * @param connection TConnection 连接
     * @return TParm
     */
    public TParm executeUpdate(String sql,TParm parm,TConnection connection)
    {
        parm.setData("SQL",sql);
        parm.setData("ACTION","DNULL",true);
        parm.setData("ACTION","TRIM",true);
        parm.setData("ACTION","SNIPPET",false);
        parm.setData("ACTION","PREPARE",true);
        return executeUpdate(parm,connection);
    }
    /**
     * 执行更新语句
     * @param sql String sql语句
     * @param dnull boolean
     * @param trim boolean
     * @param snippet boolean
     * @param prepart boolean
     * @param connection TConnection
     * @return TParm
     */
    public TParm executeUpdate(String sql,boolean dnull,boolean trim,
                              boolean snippet,boolean prepart,TConnection connection)
    {
        TParm parm = new TParm();
        parm.setData("SQL",sql);
        parm.setData("ACTION","DNULL",dnull);
        parm.setData("ACTION","TRIM",trim);
        parm.setData("ACTION","SNIPPET",snippet);
        parm.setData("ACTION","PREPARE",prepart);
        return executeUpdate(parm,connection);
    }

	/**
	 * 执行更新语句
	 * 
	 * @param parm
	 *            TParm
	 * @return TParm
	 */
	public TParm executeUpdate(TParm parm) {
		TConnection connection = getConnection(parm);
		if (connection == null)
			return err(-1, "connection is null");
		TParm result = executeUpdate(parm, connection);
		if (result.getErrCode() < 0) {
			connection.rollback();
		} else {
			connection.commit();
		}
		connection.close();
		return result;
	}
    /**
     * 执行更新语句
     * @param dbname String 数据库名
     * @param sql String sql语句
     * @param dnull boolean
     * @param trim boolean
     * @param snippet boolean
     * @param prepart boolean
     * @return TParm
     */
    public TParm executeUpdate(String dbname,String sql,boolean dnull,boolean trim,
                              boolean snippet,boolean prepart)
    {
        TParm parm = new TParm();
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","DNULL",dnull);
        parm.setData("ACTION","TRIM",trim);
        parm.setData("ACTION","SNIPPET",snippet);
        parm.setData("ACTION","PREPARE",prepart);
        return executeUpdate(parm);
    }
    /**
     * 执行更新语句
     * @param sql String sql语句
     * @param dnull boolean
     * @param trim boolean
     * @param snippet boolean
     * @param prepart boolean
     * @return TParm
     */
    public TParm executeUpdate(String sql,boolean dnull,boolean trim,
                              boolean snippet,boolean prepart)
    {
        return executeUpdate("",sql,dnull,trim,snippet,prepart);
    }
    /**
     * 执行更新语句
     * @param dbname String 数据库名
     * @param sql String 更新语句
     * @return TParm
     */
    public TParm executeUpdate(String dbname,String sql)
    {
        return executeUpdate(dbname,sql,true,true,false,true);
    }
    /**
     * 执行更新语句
     * @param sql String SQL语句
     * @return TParm
     */
    public TParm executeUpdate(String sql)
    {
        return executeUpdate("",sql);
    }
    /**
     * 执行更新语句
     * @param dbname String 数据库名
     * @param sql String SQL语句
     * @param parm TParm
     * @return TParm
     */
    public TParm executeUpdate(String dbname,String sql,TParm parm)
    {
        if(parm == null)
            return err(-1,"parm is null");
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","PREPARE",true);
        return executeUpdate(parm);
    }
    /**
     * 执行更新语句
     * @param sql String SQL语句
     * @param parm TParm
     * @return TParm
     */
    public TParm executeUpdate(String sql,TParm parm)
    {
        if(parm == null)
            return err(-1,"parm is null");
        parm.setData("SQL",sql);
        parm.setData("ACTION","PREPARE",true);
        return executeUpdate(parm);
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
        err(errCode + " " + errText + " EN:" + errName);
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
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        getLog().out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        getLog().out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        getLog().err(text);
    }
}
