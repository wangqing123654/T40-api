package com.dongyang.jdo;

import com.dongyang.util.Log;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.db.TDBPoolManager;
import com.dongyang.db.TConnection;
import com.dongyang.data.TParm;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import com.dongyang.Service.Server;
import java.util.ArrayList;

/**
 *
 * <p>Title: JDO��͸����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TStrike
{
    /**f
     * �Ƿ��ǿͻ�������
     */
    private boolean clientlink;
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * ������
     */
    public TStrike()
    {
        log = new Log();
        if(TIOM_AppServer.SOCKET != null)
            setClientlink(true);
    }
    /**
     * �����Ƿ��ǿͻ�������
     * @param clientlink boolean
     */
    public void setClientlink(boolean clientlink)
    {
        this.clientlink = clientlink;
    }
    /**
     * �Ƿ��ǿͻ�������
     * @return boolean
     */
    public boolean isClientlink()
    {
        return clientlink;
    }
    /**
     * ���÷������ĵ�ǰ����
     * @param parameters Object[]
     * @return Object
     */
    public Object callServerMethod(Object ... parameters)
    {
        String methodName = getThisMethodName(1);
        return TIOM_AppServer.executeClass(getClass().getName(),methodName,parameters);
    }
    /**
     * �õ���ǰ�ķ�����
     * @param index int λ��
     * @return String ������
     */
    public String getThisMethodName(int index)
    {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        for(int i = 0;i < stack.length;i++)
            if("getThisMethodName".equals(stack[i].getMethodName()))
                return stack[i + index + 1].getMethodName();
        return "";
    }
    /**
     * �õ�����
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return getConnection("");
    }
    /**
     * �õ�����
     * @param databaseName String ���ݿ���
     * @return TConnection
     */
    public TConnection getConnection(String databaseName)
    {
        if(notClient())
            return null;
        if(databaseName == null || databaseName.length() == 0)
            return TDBPoolManager.getInstance().getConnection();
        return TDBPoolManager.getInstance().getConnection(databaseName);
    }
    /**
     * �õ����ݿ����ӳ�����
     * @return String
     */
    public String getMainPoolName()
    {
        if(isClientlink())
            return (String)callServerMethod();
        return TDBPoolManager.getInstance().getMainPoolName();
    }
    /**
     * �õ�������
     * @return String
     */
    public String getOwner()
    {
        if(isClientlink())
            return (String)callServerMethod();
        return getOwner(getMainPoolName());
    }
    /**
     * �õ�������
     * @param databaseName String ���ݿ����ӳ�����
     * @return String
     */
    public String getOwner(String databaseName)
    {
        if(isClientlink())
            return (String)callServerMethod(databaseName);
        return TDBPoolManager.getInstance().getPool(databaseName).getUserName();
    }
    /**
     * ��ѯ
     * @param sql String
     * @return Map
     */
    public Map select(String sql)
    {
        return select("",sql);
    }
    /**
     * �õ������
     * @param sql String
     * @return String[]
     */
    public String[] selectList(String sql)
    {
        TParm parm = new TParm(select(sql));
        if(parm.getErrCode() < 0)
            return new String[]{"ERR:" + parm.getErrCode() + " " + parm.getErrText()};
        ArrayList list = new ArrayList();
        int count = parm.getCount();
        Vector columns = (Vector)parm.getData("SYSTEM","COLUMNS");
        for(int i = 0;i < count;i++)
        {
            StringBuffer s = new StringBuffer();
            for(int j = 0;j < columns.size();j++)
            {
                String name = (String)columns.get(j);
                if(j > 0)
                    s.append(";");
                s.append("" + parm.getData(name,i));
            }
            list.add(s.toString());
        }
        return (String[])list.toArray(new String[]{});
    }
    /**
     * �õ���������
     * @param databaseName String
     * @return String
     */
    public String getPoolType(String databaseName)
    {
        if(isClientlink())
            return (String)callServerMethod(databaseName);
        return TDBPoolManager.getInstance().getPoolType(databaseName);
    }
    public static final int BYTE_COUNT = 5000;
    /**
     * ��ѯ
     * @param databaseName String ���ݿ����ӳ�����
     * @param sql String SQL���
     * @return Map
     */
    public Map select(String databaseName,String sql)
    {
        return select(databaseName,sql,-1);
    }
    public Map select(String databaseName,String sql,int maxCount)
    {
        TParm parm = new TParm(select(databaseName,sql,true,0,maxCount == -1?BYTE_COUNT:maxCount));
        if(maxCount != -1)
            return parm.getData();
        int count = parm.getInt("SYSTEM","COUNT");
        while(parm.getCount() > 0 && parm.getCount() < count)
        {
            TParm parm1 = new TParm(select(databaseName,sql,true,parm.getCount(),parm.getCount() + BYTE_COUNT));
            parm.addParm(parm1);
            parm.setCount(parm1.getCount());
        }
        return parm.getData();
    }
    /**
     * ��ѯ
     * @param databaseName String ���ݿ����ӳ�����
     * @param sql String SQL���
     * @param snippet boolean true ���ڷ�ҳ
     * @param startRow int ��ʼ�к�
     * @param endRow int �����к�
     * @return Map
     */
    public Map select(String databaseName,String sql,boolean snippet,int startRow,int endRow)
    {
        if(isClientlink())
            return (Map)callServerMethod(databaseName,sql,snippet,startRow,endRow);
        TParm result = new TParm();
        TConnection connection = getConnection(databaseName);
        if(connection == null)
        {
            result.setErr(-1,"û�еõ����ݿ�����");
            return result.getData();
        }
        //test 
        //System.out.println("=======TStrike(databaseName,sql,snippet,startRow,endRow)=========="+sql);
        //test end
        connection.addSQL(sql);
        Statement statement=null;
        ResultSet resultSet=null;
        try{
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount =  resultSetMetaData.getColumnCount();
            Vector columns = new Vector();
            for (int i = 0; i < columnCount; i++)
            {
                String columnName = resultSetMetaData.getColumnLabel(i + 1).
                                    toUpperCase();
                columns.add(columnName);
            }
            int row = -1;
            while(resultSet.next())
            {
                row++;
                if (snippet)
                {
                    if (row < startRow)
                        continue;
                    if (row > endRow)
                        continue;
                }
                for (int i = 0; i < columnCount; i++)
                {
                    String columnName = (String)columns.get(i);
                    switch (resultSetMetaData.getColumnType(i + 1))
                    {
                    case 2: //NUMBER
                        if (resultSetMetaData.getScale(i + 1) > 0)
                            result.addData(columnName,
                                           resultSet.getDouble(i + 1));
                        else
                        {
                            String nData = resultSet.getString(i + 1);
                            if(nData == null)
                            {
                                result.addData(columnName, 0);
                                continue;
                            }
                            if (nData.indexOf(".") >= 0)
                                result.addData(columnName,
                                               resultSet.getDouble(i + 1));
                            else
                                result.addData(columnName,
                                               resultSet.getLong(i + 1));
                        }
                        break;
                    case 91: //DATE
                        Timestamp timestamp = resultSet.getTimestamp(i + 1);
                        result.addData(columnName, timestamp);
                        break;
                    case 1: //CHAR
                    case 12: //VARCHAR2
                    default:
                        String s = resultSet.getString(i + 1);
                        if (s == null)
                            s = "";
                        s = s.trim();
                        result.addData(columnName, s);
                    }
                }
                result.setData("ACTION","COUNT",row + 1);
            }
            result.setData("SYSTEM","COUNT",row + 1);
            result.setData("SYSTEM","COLUMNS",columns);
           /* resultSet.close();
            statement.close();
            connection.close();*/
        }catch(Exception e)
        {
            //connection.close();
            e.printStackTrace();
            result.setErr(-1,e.getMessage());
            err("BlueCore log:"+sql+e.getMessage());
        }finally{
        	//
        	//System.out.println("---ִ���ͷ�������Դ---");
        	//
        	try {
        		if(resultSet!=null){
        			resultSet.close();
        		}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            try {
            	if(statement!=null){
            		statement.close();
            	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(connection!=null){
				connection.close();
			}
        }
        return result.getData();
    }
    /**
     * �������
     * @param sql String SQL���
     * @return Map
     */
    public Map update(String sql)
    {
        return update("",sql);
    }
    /**
     * �������
     * @param databaseName String ���ݿ����ӳ�����
     * @param sql String SQL���
     * @return Map
     */
    public Map update(String databaseName,String sql)
    {
        if(isClientlink())
            return (Map)callServerMethod(databaseName,sql);
        return update(databaseName,new String[]{sql});
    }
    /**
     * �������
     * @param sql String[] SQL���
     * @return Map
     */
    public Map update(String sql[])
    {
        return update("",sql);
    }
    /**
     * �������
     * @param databaseName String ���ݿ����ӳ�����
     * @param sql String[] SQL���
     * @return Map
     */
    public Map update(String databaseName,String sql[])
    {
        if(isClientlink())
            return (Map)callServerMethod(databaseName,sql);
        TParm result = new TParm();
        TConnection connection = getConnection(databaseName);
        if(connection == null)
        {
            result.setErr(-1,"û�еõ����ݿ�����");
            return result.getData();
        }
        Statement statement=null;
        try{
            statement = connection.createStatement();
            //
            for(int i = 0;i < sql.length;i ++)
            {
                connection.addSQL(sql[i]);
                result = new TParm(update(sql[i],statement));
                if(result.getErrCode() < 0)
                {
                    connection.rollback();
                    statement.close();
                    connection.close();
                    return result.getData();
                }
            }
            connection.commit();
            //statement.close();
            //connection.close();
        }catch(Exception e)
        {
        	try {
				statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            connection.rollback();
            e.printStackTrace();
            result.setErr(-1,e.getMessage());
            err(e.getMessage());
        }finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(connection!=null){
        		connection.close();
        	}
        }
        return result.getData();
    }
    /**
     * �������
     * @param sql String[]
     * @param connection TConnection
     * @return Map
     */
    public Map update(String sql[],TConnection connection)
    {
        TParm result = new TParm();
        if(notClient())
        {
            result.setErr(-1,"update���ܱ��ص���");
            return result.getData();
        }
        for(int i = 0;i < sql.length;i ++)
        {
            result = new TParm(update(sql[i],connection));
            if(result.getErrCode() < 0)
                return result.getData();
        }
        return result.getData();
    }
    /**
     * �������
     * @param sql String
     * @param connection TConnection
     * @return Map
     */
    public Map update(String sql,TConnection connection)
    {
        TParm result = new TParm();
        if(notClient())
        {
            result.setErr(-1,"update���ܱ��ص���");
            return result.getData();
        }
        Statement statement=null;
        try{
            statement = connection.createStatement();
            connection.addSQL(sql);
            result.setData(update(sql,statement));
            if(result.getErrCode() < 0)
            {
                connection.rollback();
                statement.close();
                connection.close();
                return result.getData();
            }
            statement.close();
        }catch(Exception e)
        {
            connection.rollback();
            //test
            try {
				statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            connection.close();
            e.printStackTrace();
            result.setErr(-1,e.getMessage());
            err(e.getMessage());
        }
        return result.getData();
    }
    /**
     * ����
     * @param sql String SQL���
     * @param statement Statement
     * @return Map
     */
    public Map update(String sql,Statement statement)
    {
        TParm result = new TParm();
        if(notClient())
        {
            result.setErr(-1,"update���ܱ��ص���");
            return result.getData();
        }
        if(statement == null)
        {
            result.setErr(-1,"����ʧ��!");
            return result.getData();
        }
        try{
        	//test
        	//System.out.println("=======TStrike�� updat(sql statement)========"+sql);
        	//test
            result.setData("RETURN",statement.executeUpdate(sql));
        }catch(Exception e)
        {
            e.printStackTrace();
            result.setErr(-1,e.getMessage());
            err("BlueCore log:"+sql+e.getMessage());
        }
        return result.getData();
    }
    /**
     * ���ǿͻ���
     * @return boolean true ���ǿͻ��� false �ǿͻ���
     */
    public boolean notClient()
    {
        if(isClientlink())
        {
            err("���ܱ���ʹ��!");
            return true;
        }
        return false;
    }
    /**
     * �õ�ϵͳʱ��
     * @return Timestamp
     */
    public Timestamp getDBTime()
    {
        if(isClientlink())
            return (Timestamp)callServerMethod();
        TParm parm = new TParm(select("SELECT SYSDATE FROM DUAL"));
        return parm.getTimestamp("SYSDATE",0);
    }
    
    /**
     * �õ�ϵͳʱ��(��ȷ������3λ)
     * @author zhangp
     * @return Timestamp
     */
    public Timestamp getDBExactTime()
    {
    	if(isClientlink())
    		return (Timestamp)callServerMethod();
    	TParm parm = new TParm(select("SELECT TO_CHAR (SYSTIMESTAMP (3), 'YYYY-MM-DD HH24:MI:SSXFF') SYSTIMESTAMP FROM DUAL"));
    	Timestamp ts = null;
    	try {
	        Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        Date d = (Date) f.parseObject(parm.getValue("SYSTIMESTAMP",0));
	        ts = new Timestamp(d.getTime());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ts;
		}
    	return ts;
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
     * ִ���ⲿ��
     * @param className String
     * @param methodName String
     * @param parameters Object[]
     * @return Object
     */
    public Object exeServerClass(String className,String methodName,Object[] parameters)
    {
        if(isClientlink())
            return TIOM_AppServer.executeClass(className,methodName,parameters);
        return Server.exeServerClass(className,methodName,parameters);
    }
    /**
     * ִ�нӿڶ���
     * @param className String
     * @param parm TParm
     * @return TParm
     */
    public TParm exeIOAction(String className,TParm parm)
    {
        if(parm == null)
            parm = new TParm();
        return new TParm((Map)exeServerClass(className,"initIO",new Object[]{parm.getData()}));
    }
    /**
     * ִ�нӿڶ���
     * @param className String
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm exeIOAction(String className,TParm parm,TConnection connection)
    {
        if(isClientlink())
        {
            System.out.println("ǰ̨�����ֹ���ô����ӵ� exeIOAction");
            return null;
        }
        if(parm == null)
            parm = new TParm();
        return new TParm((Map)exeServerClass(className,"initIO",new Object[]{connection,parm.getData()}));
    }
}
