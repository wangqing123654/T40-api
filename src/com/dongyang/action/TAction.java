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
     * �������
     */
    private TParm inParm;
    /**
     * ������Ϣ
     */
    private TParm errParm;
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * ���ö���
     */
    private TConfigParm configParm = new TConfigParm();
    /**
     * ������
     */
    public TAction()
    {
        log = new Log();
    }
    /**
     * ���ô������
     * @param inParm TParm
     */
    public void setInParm(TParm inParm)
    {
        this.inParm = inParm;
    }
    /**
     * �õ��������
     * @return TParm
     */
    public TParm getInParm()
    {
        return inParm;
    }
    /**
     * �����û�IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        log.setIP(IP);
    }
    /**
     * �õ��û�IP
     * @return String
     */
    public String getIP()
    {
        return log.getIP();
    }
    /**
     * ���ô�����Ϣ
     * @param parm TParm
     */
    public void setErrParm(TParm parm)
    {
        this.errParm = parm;
    }
    /**
     * �õ�������Ϣ
     * @return TParm
     */
    public TParm getErrParm()
    {
        return errParm;
    }
    /**
     * �õ������
     * @return Map
     */
    public Map getResultPools()
    {
        return resultPools;
    }
    /**
     * �õ�����
     * @param databaseName String ���ݳ���
     * @return TConnection
     */
    public TConnection getConnection(String databaseName)
    {
        TConnection connection = TDBPoolManager.getInstance().getConnection(databaseName);
        if(connection == null)
        {
            setErrParm(err( -1, "�޷�ȡ�����ݿ�����", databaseName));
            return null;
        }
        return connection;
    }
    /**
     * �õ�������
     * @return TConnection
     */
    public TConnection getConnection()
    {
        TConnection connection = TDBPoolManager.getInstance().getConnection();
        if(connection == null)
        {
            setErrParm(err( -1, "�޷�ȡ�����ݿ�������"));
            return null;
        }
        return connection;
    }
    /**
     * �õ����ݿ�����
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
                setErrParm(err(-1, "���ӳ�ʱ " + connectionCode,""));
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
        //err(errCode + " " + errText + " EN:" + errName);
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
     * �õ��������
     * @return DataService
     */
    public DataService getService()
    {
        return DataService.getInstance();
    }
    /**
     * ִ��һ������Action
     * @param parm TParm ����
     * @param className String ����
     * @param methodName String ������
     * @return TParm
     */
    public TParm executeAction(TParm parm,String className,String methodName)
    {
        if(parm == null)
            return null;
        return executeAction(parm.getData(),className,methodName);
    }
    /**
     * ִ��һ������Action
     * @param inObject Map ����
     * @param className String ����
     * @param methodName String ������
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
     * �������ö���
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * �õ����ö���
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return configParm;
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
}
