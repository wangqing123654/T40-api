package com.dongyang.module.base;

import com.dongyang.db.TDBPoolManager;
import com.dongyang.db.TConnection;
import com.dongyang.util.Log;
import com.dongyang.data.TParm;
import java.util.Date;
import com.dongyang.db.TConnectionPool;

public class TModuleBase {
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * �û�IP
     */
    private String IP;
    /**
     * ������
     */
    public TModuleBase()
    {
        setLog(new Log());
    }
    /**
     * �����û�IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        this.IP = IP;
        log.setIP(IP);
    }
    /**
     * �õ��û�IP
     * @return String
     */
    public String getIP()
    {
        return IP;
    }
    /**
     * ����Log����
     * @param log Log
     */
    public void setLog(Log log)
    {
        this.log = log;
    }
    /**
     * �õ�Log����
     * @return Log
     */
    public Log getLog()
    {
        return log;
    }
    /**
     * �õ�����
     * @param databaseName String ���ݳ���
     * @return TConnection
     */
    public TConnection getConnection(String databaseName)
    {
        return TDBPoolManager.getInstance().getConnection(databaseName);
    }
    /**
     * �õ�������
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return TDBPoolManager.getInstance().getConnection();
    }

    /**
     * �õ�����
     * @param parm TParm ACTION:DATABASE_NAME ���ݳ���
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
     * ִ��select���
     * @param dbname String ���ݿ���
     * @param sql String sql���
     * @return TParm
     */
    public TParm executeQuery(String dbname,String sql)
    {
        return executeQuery(dbname,sql,true,true,false,true);
    }
    /**
     * ִ��select���
     * @param sql String sql���
     * @return TParm
     */
    public TParm executeQuery(String sql)
    {
        return executeQuery("",sql);
    }
    /**
     * ִ��select���
     * @param dbname String ���ݿ���
     * @param sql String sql���
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
     * ִ��select���
     * @param sql String sql���
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
     * ִ��select���
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
     * ִ��select���
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
     * ִ��select���
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
     * ִ��select���
     * @param sql String sql���
     * @param parm TParm ����
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
     * ִ��select���
     * @param dbname String ���ݿ���
     * @param sql String sql���
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
     * ִ��select���
     * @param sql String sql���
     * @param outType String ���� "MR_NO:VARCHAR"
     * @return TParm
     */
    public TParm executeCall(String sql,String outType)
    {
        return executeCall(sql,outType,true,true);
    }
    /**
     * ִ��select���
     * @param sql String sql���
     * @param dnull boolean ȥ��
     * @param trim boolean ȥ�ո�
     * @param outType String ���� "MR_NO:VARCHAR"
     * @return TParm
     */
    public TParm executeCall(String sql,String outType,boolean dnull,boolean trim)
    {
        return executeCall("",sql,outType,dnull,trim);
    }
    /**
     * ִ�д洢�������
     * @param dbname String ���ݿ���
     * @param sql String sql���
     * @param outType String ���� "MR_NO:VARCHAR"
     * @return TParm
     */
    public TParm executeCall(String dbname,String sql,String outType)
    {
        return executeCall(dbname,sql,outType,true,true);
    }

    /**
     * ִ�д洢�������
     * @param dbname String ���ݿ���
     * @param sql String sql���
     * @param outType String ���� "MR_NO:VARCHAR"
     * @param dnull boolean ȥ��
     * @param trim boolean ȥ�ո�
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
     * ִ�д洢�������
     * @param sql String sql���
     * @param outType String ���� "MR_NO:VARCHAR"
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
     * ִ�д洢�������
     * @param dbname String ���ݿ���
     * @param sql String sql���
     * @param outType String ���� "MR_NO:VARCHAR"
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
     * ִ�д洢�������
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
     * ִ�и������
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
     * ִ�и������
     * @param sql String sql���
     * @param connection TConnection
     * @return TParm
     */
    public TParm executeUpdate(String sql,TConnection connection)
    {
        return executeUpdate(sql,true,true,false,true,connection);
    }
    /**
     * ִ�и������
     * @param sql String sql���
     * @param parm TParm ����
     * @param connection TConnection ����
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
     * ִ�и������
     * @param sql String sql���
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
	 * ִ�и������
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
     * ִ�и������
     * @param dbname String ���ݿ���
     * @param sql String sql���
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
     * ִ�и������
     * @param sql String sql���
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
     * ִ�и������
     * @param dbname String ���ݿ���
     * @param sql String �������
     * @return TParm
     */
    public TParm executeUpdate(String dbname,String sql)
    {
        return executeUpdate(dbname,sql,true,true,false,true);
    }
    /**
     * ִ�и������
     * @param sql String SQL���
     * @return TParm
     */
    public TParm executeUpdate(String sql)
    {
        return executeUpdate("",sql);
    }
    /**
     * ִ�и������
     * @param dbname String ���ݿ���
     * @param sql String SQL���
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
     * ִ�и������
     * @param sql String SQL���
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
     * ��װһ���������
     * @param errCode int ������
     * @param errText String ������Ϣ
     * @return TParm
     */
    public TParm err(int errCode,String errText)
    {
        return err(errCode,errText,"");
    }
    /**
     * ��װһ���������
     * @param errCode int ������
     * @param errText String ������Ϣ
     * @param errName String ��������
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
     * ����һ��������Ϣ
     * @param parm TParm ����
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
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        getLog().out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text,boolean debug)
    {
        getLog().out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        getLog().err(text);
    }
}
