package com.dongyang.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.DatabaseMetaData;
import java.util.Map;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLWarning;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;

import com.dongyang.util.Log;
import com.dongyang.data.TParm;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.dongyang.util.StringTool;
import java.sql.Timestamp;
import java.sql.Types;
import com.dongyang.data.TNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TConnection implements Connection, Runnable {
	private int closeTime;
	private boolean closed;
	private Date accessed;
	private Connection conn;
	private TConnectionPool pool;
	private Map parentMap;
	private long timeOut;
	private List sqlList = new ArrayList();
	/**
	 * 日志系统
	 */
	Log log;

	public TConnection(Connection conn, TConnectionPool pool) {
		this.conn = conn;
		this.pool = pool;
		log = new Log();
	}

	public String getSQL() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sqlList.size(); i++)
			sb.append("[" + i + ":" + sqlList.get(i) + "]");
		return sb.toString();
	}

	public void addSQL(String sql) {
		sqlList.add(sql);
	}

	/**
	 * 得到连接池
	 *
	 * @return TConnectionPool
	 */
	public TConnectionPool getPool() {
		return pool;
	}

	public void start() {
		accessed();
		new Thread(this).start();
	}

	public void accessed() {
		accessed = new Date();
	}

	public void setParentMap(Map map) {
		this.parentMap = map;
	}

	public Map getParentMap() {
		return parentMap;
	}

	public void setCloseTime(int closeTime) {
		this.closeTime = closeTime;
	}

	public int getCloseTime() {
		return closeTime;
	}

	public void checkTimeout() throws Exception {
		Date minAgo = new Date(System.currentTimeMillis()
				- (long) (getCloseTime() * 1000));
		if (accessed.before(minAgo) && !closed) {
			if (conn != null) {
				rollback();
				close();
			} else
				closed = true;
		}
	}

	public void clearWarnings() throws SQLException {
		accessed();
        conn.clearWarnings();

	}

	public String getIndexCode()
    {
        return "CONN-" + hashCode();
    }

	public void close() {
		try{
            if (pool != null) {
                pool.setConnection(this);
                closed = true;
                return;
            }
            conn.close();
            closed = true;
        }catch(Exception e)
        {
        }
        if(getParentMap() != null)
            getParentMap().remove(getIndexCode());

	}

	public Connection getConn()
    {
        return conn;
    }

	public void commit(){
		accessed();
        try{
            conn.commit();
        }catch(Exception e)
        {

        }

	}

	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Statement createStatement() throws SQLException {
		 accessed();
	        return conn.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		accessed();
        return conn.createStatement(resultSetType, resultSetConcurrency);
	}

	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		accessed();
        return conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getAutoCommit() throws SQLException {
		 accessed();
	     return conn.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		accessed();
        return conn.getCatalog();
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHoldability() throws SQLException {
		 accessed();
	     return conn.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		accessed();
        return conn.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		 accessed();
	     return conn.getTransactionIsolation();
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		 accessed();
	     return conn.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		accessed();
        return conn.getWarnings();
	}

	public boolean isClosed() {
		 accessed();
	     return closed;
	}

	public boolean isReadOnly() throws SQLException {
		accessed();
        return conn.isReadOnly();
	}

	public boolean isValid(int timeout) throws SQLException {

		return false;
	}

	public String nativeSQL(String sql) throws SQLException {
		accessed();
        return conn.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		 accessed();
	     return conn.prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		accessed();
        return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		accessed();
        return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		accessed();
        return conn.prepareStatement(sql, columnNames);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		accessed();
        return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		accessed();
        return conn.prepareStatement(sql, resultSetType, resultSetHoldability);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		accessed();
        conn.releaseSavepoint(savepoint);

	}

	public void rollback() {
		try{
            if (closed || conn.isClosed()) {
                return;
            } else {
                accessed();
                conn.rollback();
                return;
            }
        }catch(Exception e)
        {
        }

	}

	public void rollback(Savepoint savepoint) throws SQLException {
		accessed();
        conn.rollback(savepoint);

	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		accessed();
        conn.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException {
		 accessed();
	     conn.setCatalog(catalog);

	}

	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	public void setHoldability(int holdability) throws SQLException {
		accessed();
        conn.setHoldability(holdability);

	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		 accessed();
	        conn.setReadOnly(readOnly);

	}

	public Savepoint setSavepoint() throws SQLException {
		accessed();
        return conn.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		accessed();
        return conn.setSavepoint(name);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		 accessed();
	     conn.setTransactionIsolation(level);

	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		accessed();
        conn.setTypeMap(map);

	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void run() {
		while(!closed)
        {
            synchronized(this)
            {
                try
                {
                    wait(1000);
                    if(!closed)
                        checkTimeout();
                }
                catch(Exception ex)
                {
                    err(ex.getMessage());
                    closed = true;
                }
            }
        }

	}

	public TParm executeCall(TParm parm)
    {
        accessed();
        //处理带入字段
        if(!getSQLParm(parm))
        {
            TParm result = new TParm();
            result.setErr(parm);
            return result;
        }
        String sql = parm.getValue("SQL");
        TParm result = new TParm();
        try{
        	//test
        	//System.out.println("=======TConnection类    executeCall(parm)=========="+sql);
        	//
            CallableStatement cstmt = prepareCall(sql);
            callableStatementGetParm(cstmt,parm);
            cstmt.execute();
            String outType = parm.getValue("ACTION","OUT_TYPE");
            int parmCount = parm.getCount("VALUES");
            if(parmCount < 0)
                parmCount = 0;
            if(outType.length() > 0)
            {
                String s[] = StringTool.parseLine(outType,";");
                for(int i = 0;i < s.length;i++)
                {
                    String type = "VARCHAR";
                    String s1[] = StringTool.parseLine(s[i], ":");
                    String name = s1[0];
                    if(s1.length == 2)
                        type = s1[1];
                    if("VARCHAR".equalsIgnoreCase(type))
                        result.addData(name, cstmt.getString(i + 1 + parmCount));
                    else if("CHAR".equalsIgnoreCase(type))
                        result.addData(name,cstmt.getString(i + 1 + parmCount));
                    else if("INT".equalsIgnoreCase(type))
                        result.addData(name,cstmt.getInt(i + 1 + parmCount));
                    else if("DOUBLE".equalsIgnoreCase(type))
                        result.addData(name,cstmt.getDouble(i + 1 + parmCount));
                }
            }
            cstmt.close();
            commit();
        }catch(Exception e)
        {
            err(e.getMessage());
            result.setErr(-1,e.getMessage());
            return result;
        }
        return result;
    }


	public boolean executeUpdate(TParm parm)
    {
        accessed();
        //处理带入字段
        if(!getSQLParm(parm))
            return false;
        String sql = parm.getValue("SQL");
        addSQL(sql);
       
        if(parm.getBoolean("ACTION","PREPARE"))
        {
        	 PreparedStatement preparedStatement=null;
            try {
                 preparedStatement = prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                if(!preparedStatementGetParm(preparedStatement,parm))
                    return false;
                preparedStatement.executeUpdate();
                //preparedStatement.close();
                return true;
            }catch(Exception e)
            {
                err(e.getMessage());
                err("\n ##[BlueCore]err detail:  " + e.toString());
                err("\n ##[BlueCore]err sql:  " + sql);
                err("\n ##[BlueCore]err sql -parm :  " + parm.getData());
                parm.setErr(-1,e.getMessage());
                return false;
            }finally{
            	if(preparedStatement!=null){
            		try {
						preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            	}
            }
        }
        //
        Statement statement =null;
        try{
            statement = createStatement();
            statement.executeUpdate(sql);
            statement.close();
            parm.removeData("VALUES");

        }catch(Exception e)
        {
            err(e.getMessage());
            err("\n ##[Bluecore]err detail:  " + e.toString());
            err("\n ##[Bluecore]err sql:  " + sql);
            err("\n ##[Bluecore]err sql -parm :  " + parm.getData());
            parm.setErr(-1,e.getMessage());
            return false;
        }finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        //
        return true;
    }

	/**
     * 解析带入字段
     * @param parm TParm
     * @return boolean
     */
    public boolean getSQLParm(TParm parm)
    {
        String sql = parm.getValue("SQL");
        if(sql == null||sql.length() == 0)
        {
            err("SQL is null");
            parm.setErr(-1,"SQL is null");
            return false;
        }
        StringBuffer sb = new StringBuffer();
        boolean b = false;
        StringBuffer nv = new StringBuffer();
        parm.removeData("VALUES");
        for(int i = 0;i < sql.length();i++)
        {
            char c = sql.charAt(i);
            if(!b&&c == '<')
            {
                b = true;
                continue;
            }
            if(!b)
            {
                sb.append(c);
                continue;
            }
            if(c == '<')
            {
                sb.append("<");
                sb.append(nv.toString());
                nv.setLength(0);
                continue;
            }
            if(c == '>')
            {
                String name = nv.toString().trim();
                if(name.length() == 0)
                {
                    sb.append("<>");
                    nv.setLength(0);
                    b = false;
                    continue;
                }
                //if(parm.checkEmpty(name,parm))
                //    return false;
                parm.addData("VALUES",parm.getData(name));
                nv.setLength(0);
                b = false;
                sb.append("?");
                continue;
            }
            nv.append(c);
        }
        if(b)
            sb.append("<" + nv);
        parm.setData("SQL_BAK",parm.getValue("SQL"));
        parm.setData("SQL",sb.toString());
        return true;
    }
    private boolean preparedStatementGetParm(PreparedStatement preparedStatement,TParm parm)
    {
        int valueCount = parm.getCount("VALUES");
        try{
            for (int i = 0; i < valueCount; i++) {
                Object value = parm.getData("VALUES", i);

                if(value instanceof TNull)
                    preparedStatement.setNull(i + 1,((TNull)value).getType());
                else if (value instanceof Boolean)
                    preparedStatement.setString(i + 1,((Boolean) value)?"Y":"N");
                else if (value instanceof String)
                    preparedStatement.setString(i + 1,(String) value);
                else if (value instanceof Long)
                    preparedStatement.setLong(i + 1, (Long) value);
                else if (value instanceof Integer)
                    preparedStatement.setInt(i + 1, (Integer) value);
                else if (value instanceof Double)
                    preparedStatement.setDouble(i + 1, (Double) value);
                else if (value instanceof Timestamp)
                    preparedStatement.setTimestamp(i + 1, (Timestamp) value);
                else if (value instanceof java.sql.Date)
                    preparedStatement.setDate(i + 1, (java.sql.Date) value);
                else if (value instanceof Date)
                    preparedStatement.setDate(i + 1,StringTool.getSQLDate((Date)
                            value));
            }
        }catch(Exception e)
        {
            err(e.getMessage());
            parm.setErr(-1,e.getMessage());
            return false;
        }
        return true;
    }


    private boolean callableStatementGetParm(CallableStatement callableStatement,TParm parm)
    {
        int valueCount = parm.getCount("VALUES");
        try{
            for (int i = 0; i < valueCount; i++) {
                Object value = parm.getData("VALUES", i);
                if (value instanceof String)
                {
                    callableStatement.registerOutParameter(i + 1,java.sql.Types.VARCHAR);
                    callableStatement.setString(i + 1, (String) value);
                }
                else if (value instanceof Integer)
                {
                    callableStatement.registerOutParameter(i + 1,java.sql.Types.INTEGER);
                    callableStatement.setInt(i + 1, (Integer) value);
                }
                else if (value instanceof Double)
                {
                    callableStatement.registerOutParameter(i + 1,java.sql.Types.DOUBLE);
                    callableStatement.setDouble(i + 1, (Double) value);
                }
                else if (value instanceof Timestamp)
                {
                    callableStatement.registerOutParameter(i + 1,java.sql.Types.TIMESTAMP);
                    callableStatement.setTimestamp(i + 1, (Timestamp) value);
                }
                else if (value instanceof java.sql.Date)
                {
                    callableStatement.registerOutParameter(i + 1,java.sql.Types.DATE);
                    callableStatement.setDate(i + 1, (java.sql.Date) value);
                }
                else if (value instanceof Date)
                {
                    callableStatement.registerOutParameter(i + 1,java.sql.Types.DATE);
                    callableStatement.setDate(i + 1,StringTool.getSQLDate((Date)
                            value));
                }
            }
            String outType = parm.getValue("ACTION","OUT_TYPE");
            if(outType.length() == 0)
                return true;
            String s[] = StringTool.parseLine(outType,";");
            if(valueCount == -1)
                valueCount = 0;
            for(int i = 0;i < s.length;i++)
            {
                String type = "VARCHAR";
                String s1[] = StringTool.parseLine(s[i],":");
                if(s1.length == 2)
                    type = s1[1];
                if("VARCHAR".equalsIgnoreCase(type))
                {
                    callableStatement.registerOutParameter(valueCount + i + 1,java.sql.Types.VARCHAR);
                }
                else if("CHAR".equalsIgnoreCase(type))
                {
                    callableStatement.registerOutParameter(valueCount + i + 1,java.sql.Types.CHAR);
                }
                else if("INT".equalsIgnoreCase(type))
                {
                    callableStatement.registerOutParameter(valueCount + i + 1,java.sql.Types.INTEGER);
                }
                else if("DOUBLE".equalsIgnoreCase(type))
                {
                    callableStatement.registerOutParameter(valueCount + i + 1,java.sql.Types.DOUBLE);
                }
            }
        }catch(Exception e)
        {
            err(e.getMessage());
            parm.setErr(-1,e.getMessage());
            return false;
        }
        return true;
    }

    public boolean executeParmQuery(TParm parm)
    {
        parm.setData("SYSTEM","EXE_STATE","TConnection.executeParmQuery");
        accessed();
        //处理带入字段
        if(!getSQLParm(parm))
            return false;
        if(parm.getBoolean("ACTION","PREPARE"))
        {
            String sql = parm.getValue("SQL");
            addSQL(sql);
            try{
                PreparedStatement preparedStatement = prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                if(!preparedStatementGetParm(preparedStatement,parm))
                    return false;
                ResultSet resultSet = preparedStatement.executeQuery();
                parm.setData("ACTION","PREPARED_STATEMENT",preparedStatement);
                parm.setData("ACTION","RESULTSET",resultSet);
                return true;
            }catch(Exception e)
            {
                err(e.getMessage() + " sql:" + sql);
                parm.setErr(-1,e.getMessage());
                return false;
            }
        }
        try{
            addSQL(parm.getValue("SQL"));
            Statement statement = createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(parm.getValue("SQL"));
            parm.setData("ACTION","STATEMENT",statement);
            parm.setData("ACTION","RESULTSET",resultSet);
        }catch(Exception e)
        {
            err(e.getMessage());
            parm.setErr(-1,e.getMessage());
            return false;
        }
        parm.setData("SYSTEM","EXE_STATE","TConnection.executeParmQuery end");
        return true;
    }

    public TParm closeParmData(TParm parm)
    {
    	//
    	//System.out.println("TConnection closeParmData PreparedStatement closed.");
    	//
        parm.setData("SYSTEM","EXE_STATE","TConnection.closeParmData");
        accessed();
        TParm result = new TParm();
        try{
            ResultSet resultSet = (ResultSet)parm.getData("ACTION","RESULTSET");
            if(resultSet != null)
                resultSet.close();
            Statement statement = (Statement)parm.getData("ACTION","STATEMENT");
            if(statement != null)
                statement.close();
            PreparedStatement preparedStatement = (PreparedStatement)parm.getData("ACTION","PREPARED_STATEMENT");
            if(preparedStatement != null)
                preparedStatement.close();
        }catch(Exception e)
        {
            err(e.getMessage());
            result.setErr(-1,e.getMessage());
        }
        parm.setData("SYSTEM","EXE_STATE","TConnection.closeParmData end");
        return result;
    }
    public TParm parseParmData(TParm parm)
    {
        parm.setData("SYSTEM","EXE_STATE","TConnection.parseParmData");
        TParm result = new TParm();
        ResultSet resultSet = (ResultSet)parm.getData("ACTION","RESULTSET");
        if(resultSet == null)
        {
            err("ACTION.RESULTSET is null");
            result.setErr(-1,"ACTION.RESULTSET is null");
        }
        try{
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount =  resultSetMetaData.getColumnCount();
            //是否去除null
            boolean dnull = parm.getBoolean("ACTION","DNULL");
            //是否去除空格
            boolean trim = parm.getBoolean("ACTION","TRIM");
            //是否抓取片断
            boolean snippet = parm.getBoolean("ACTION","SNIPPET");
            int startRow = 0;
            int endRow = 0;
            int row = -1;
            if(snippet)
            {
                startRow = parm.getInt("ACTION","START_ROW");
                endRow = parm.getInt("ACTION","END_ROW");
            }
            resultSet.beforeFirst();
            while(resultSet.next())
            {
                row++;
                if(snippet)
                {
                    if(row < startRow)
                        continue;
                    if(row > endRow)
                        continue;
                }
                for (int i = 0; i < columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnLabel(i + 1).toUpperCase();
                    switch(resultSetMetaData.getColumnType(i + 1))
                    {
                    case 2://NUMBER
                        if(resultSetMetaData.getScale(i + 1) > 0)
                            result.addData(columnName,resultSet.getDouble(i + 1));
                        else
                        {
                            String nData = resultSet.getString(i + 1);
                            if(nData == null)
                            {
                                result.addData(columnName, 0);
                                continue;
                            }
                            if(nData.indexOf(".") >= 0)
                                result.addData(columnName,resultSet.getDouble(i + 1));
                            else
                                result.addData(columnName, resultSet.getLong(i + 1));
                        }
                        break;
                    case 91://DATE
                        Timestamp timestamp= resultSet.getTimestamp(i + 1);
                        result.addData(columnName,timestamp);
                        break;
                    case 1://CHAR
                    case 12://VARCHAR2
                    default:
                        String s = resultSet.getString(i + 1);
                        if(dnull && s == null)
                            s = "";
                        if(trim && s != null)
                            s = s.trim();
                        result.addData(columnName,s);
                    }
                }
            }
            result.setData("ACTION","COUNT",row + 1);
        }catch(Exception e)
        {
            err(e.getMessage());
            result.setErr(-1,e.getMessage());
        }
        parm.setData("SYSTEM","EXE_STATE","TConnection.parseParmData end");
        return result;
    }
    public TParm executeQuery(TParm parm)
    {
        parm.setData("SYSTEM","EXE_STATE","TConnection.executeQuery");
        TParm result = null;
        if(!executeParmQuery(parm))
        {
            result = new TParm();
            //如果有错误，一定要关掉statement
            closeParmData(parm);
            //
            result.setErr(parm);
            return result;
        }
        result = parseParmData(parm);
        closeParmData(parm);
        if(parm.getErrCode() < 0)
        {
            result = new TParm();
            result.setErr(parm);
            return result;
        }
        return result;
    }

    public void setTimeOut(long timeOut)
    {
        this.timeOut = timeOut;
    }
    public long getTimeOut()
    {
        return timeOut;
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

    public static void main(String args[])
    {
        TConnection c = new TConnection(null,null);
        TParm p = new TParm();
        p.setData("SQL","select aaa,bbb from a1 < 100");
        c.getSQLParm(p);
        System.out.println(p);
    }


}
