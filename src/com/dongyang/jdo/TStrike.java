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
 * <p>Title: JDO穿透对象</p>
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
     * 是否是客户端连接
     */
    private boolean clientlink;
    /**
     * 日志系统
     */
    private Log log;
    /**
     * 构造器
     */
    public TStrike()
    {
        log = new Log();
        if(TIOM_AppServer.SOCKET != null)
            setClientlink(true);
    }
    /**
     * 设置是否是客户端连接
     * @param clientlink boolean
     */
    public void setClientlink(boolean clientlink)
    {
        this.clientlink = clientlink;
    }
    /**
     * 是否是客户端连接
     * @return boolean
     */
    public boolean isClientlink()
    {
        return clientlink;
    }
    /**
     * 调用服务器的当前方法
     * @param parameters Object[]
     * @return Object
     */
    public Object callServerMethod(Object ... parameters)
    {
        String methodName = getThisMethodName(1);
        return TIOM_AppServer.executeClass(getClass().getName(),methodName,parameters);
    }
    /**
     * 得到当前的方法名
     * @param index int 位置
     * @return String 方法名
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
     * 得到连接
     * @return TConnection
     */
    public TConnection getConnection()
    {
        return getConnection("");
    }
    /**
     * 得到连接
     * @param databaseName String 数据库名
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
     * 得到数据库连接池名称
     * @return String
     */
    public String getMainPoolName()
    {
        if(isClientlink())
            return (String)callServerMethod();
        return TDBPoolManager.getInstance().getMainPoolName();
    }
    /**
     * 得到所有者
     * @return String
     */
    public String getOwner()
    {
        if(isClientlink())
            return (String)callServerMethod();
        return getOwner(getMainPoolName());
    }
    /**
     * 得到所有者
     * @param databaseName String 数据库连接池名称
     * @return String
     */
    public String getOwner(String databaseName)
    {
        if(isClientlink())
            return (String)callServerMethod(databaseName);
        return TDBPoolManager.getInstance().getPool(databaseName).getUserName();
    }
    /**
     * 查询
     * @param sql String
     * @return Map
     */
    public Map select(String sql)
    {
        return select("",sql);
    }
    /**
     * 得到结果集
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
     * 得到连接类型
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
     * 查询
     * @param databaseName String 数据库连接池名称
     * @param sql String SQL语句
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
     * 查询
     * @param databaseName String 数据库连接池名称
     * @param sql String SQL语句
     * @param snippet boolean true 存在分页
     * @param startRow int 开始行号
     * @param endRow int 结束行号
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
            result.setErr(-1,"没有得到数据库连接");
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
        	//System.out.println("---执行释放连接资源---");
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
     * 更新语句
     * @param sql String SQL语句
     * @return Map
     */
    public Map update(String sql)
    {
        return update("",sql);
    }
    /**
     * 更新语句
     * @param databaseName String 数据库连接池名称
     * @param sql String SQL语句
     * @return Map
     */
    public Map update(String databaseName,String sql)
    {
        if(isClientlink())
            return (Map)callServerMethod(databaseName,sql);
        return update(databaseName,new String[]{sql});
    }
    /**
     * 更新语句
     * @param sql String[] SQL语句
     * @return Map
     */
    public Map update(String sql[])
    {
        return update("",sql);
    }
    /**
     * 更新语句
     * @param databaseName String 数据库连接池名称
     * @param sql String[] SQL语句
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
            result.setErr(-1,"没有得到数据库连接");
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
     * 更新语句
     * @param sql String[]
     * @param connection TConnection
     * @return Map
     */
    public Map update(String sql[],TConnection connection)
    {
        TParm result = new TParm();
        if(notClient())
        {
            result.setErr(-1,"update不能本地调用");
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
     * 更新语句
     * @param sql String
     * @param connection TConnection
     * @return Map
     */
    public Map update(String sql,TConnection connection)
    {
        TParm result = new TParm();
        if(notClient())
        {
            result.setErr(-1,"update不能本地调用");
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
     * 更新
     * @param sql String SQL语句
     * @param statement Statement
     * @return Map
     */
    public Map update(String sql,Statement statement)
    {
        TParm result = new TParm();
        if(notClient())
        {
            result.setErr(-1,"update不能本地调用");
            return result.getData();
        }
        if(statement == null)
        {
            result.setErr(-1,"连接失败!");
            return result.getData();
        }
        try{
        	//test
        	//System.out.println("=======TStrike类 updat(sql statement)========"+sql);
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
     * 不是客户端
     * @return boolean true 不是客户端 false 是客户端
     */
    public boolean notClient()
    {
        if(isClientlink())
        {
            err("不能本地使用!");
            return true;
        }
        return false;
    }
    /**
     * 得到系统时间
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
     * 得到系统时间(精确到毫秒3位)
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
     * 执行外部类
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
     * 执行接口动作
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
     * 执行接口动作
     * @param className String
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm exeIOAction(String className,TParm parm,TConnection connection)
    {
        if(isClientlink())
        {
            System.out.println("前台程序禁止调用带连接的 exeIOAction");
            return null;
        }
        if(parm == null)
            parm = new TParm();
        return new TParm((Map)exeServerClass(className,"initIO",new Object[]{connection,parm.getData()}));
    }
}
