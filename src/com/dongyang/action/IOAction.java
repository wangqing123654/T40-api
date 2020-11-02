package com.dongyang.action;

import com.dongyang.db.TConnection;
import com.dongyang.data.TParm;
import java.util.Map;
import com.dongyang.util.LogFile;
import com.dongyang.config.TConfig;

abstract public class IOAction
{
    /**
     * ���ݿ�����
     */
    private TConnection connection;
    /**
     * ����
     */
    private TParm parm;
    /**
     * ���
     */
    private TParm result = new TParm();
    /**
     * ����ϵͳ
     */
    private LogFile logFile = new LogFile();
    /**
     * ��������·��Key
     * @param s String
     */
    public void setLogPathKey(String s)
    {
        setLogPath(TConfig.getSystemValue(s));
    }
    /**
     * ��������·��
     * @param s String
     */
    public void setLogPath(String s)
    {
        logFile.setPath(s);
    }
    /**
     * �õ�����·��
     * @return String
     */
    public String getLogPath()
    {
        return logFile.getPath();
    }
    /**
     * ���������ļ���
     * @param fileName String
     */
    public void setLogFileName(String fileName)
    {
        logFile.setFileName(fileName);
    }
    /**
     * �õ������ļ���
     * @return String
     */
    public String getLogFileName()
    {
        return logFile.getFileName();
    }
    /**
     * ���������û���Ϣ
     * @param userInf String
     */
    public void setLogUserInf(String userInf)
    {
        logFile.setUserInf(userInf);
    }
    /**
     * �õ������û���Ϣ
     * @return String
     */
    public String getLogUserInf()
    {
        return logFile.getUserInf();
    }
    /**
     * ���������û�IP
     * @param userIP String
     */
    public void setLogUserIP(String userIP)
    {
        logFile.setIP(userIP);
    }
    /**
     * �õ��û�����IP
     * @return String
     */
    public String getLogUserIP()
    {
        return logFile.getIP();
    }
    /**
     * �������
     * @param s String
     */
    public void out(String s)
    {
        logFile.out(s);
    }
    /**
     * �ӿڷ���
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
     * �ӿڷ���
     * @param map Map
     * @return Map
     */
    public Map initIO(Map map)
    {
        setParm(new TParm(map));
        //��ʼ��
        onInit();
        //����
        onRun();
        if(getResult() == null)
            setResult(new TParm());
        return getResult().getData();
    }
    /**
     * ��ʼ��
     */
    abstract public void onInit();
    /**
     * ����
     */
    abstract public void onRun();
    /**
     * ���ò���
     * @param parm TParm
     */
    public void setParm(TParm parm)
    {
        this.parm = parm;
    }
    /**
     * �õ�����
     * @return TParm
     */
    public TParm getParm()
    {
        return parm;
    }
    /**
     * ���ý��
     * @param result TParm
     */
    public void setResult(TParm result)
    {
        this.result = result;
    }
    /**
     * �õ����
     * @return TParm
     */
    public TParm getResult()
    {
        return result;
    }
    /**
     * ��������
     * @param connection TConnection
     */
    public void setConnection(TConnection connection)
    {
        this.connection = connection;
    }
    /**
     * �õ�����
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return connection;
    }
}
