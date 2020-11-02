package com.dongyang.jdo;

import com.dongyang.util.TDebug;
import com.dongyang.data.TParm;
import java.util.Map;

/**
 *
 * <p>Title: 数据库工具(Oracle)</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.16
 * @version 1.0
 */
public class TJDODBTool extends TStrike
{
    /**
     * 唯一实例
     */
    private static TJDODBTool instance;
    /**
     * 得到实例
     * @return TJDODBTool
     */
    public static TJDODBTool getInstance()
    {
        if(instance == null)
            instance = new TJDODBTool();
        return instance;
    }
    /**
     * 得到全部表名
     * @return String[]
     */
    public String[] getTables()
    {
        if(isClientlink())
            return (String[])callServerMethod();
        return getTables("");
    }
    /**
     * 得到全部表名
     * @param owner String 所有者
     * @return String[]
     */
    public String[] getTables(String owner)
    {
        if(isClientlink())
            return (String[])callServerMethod(owner);
        return getTables("",owner);
    }
    /**
     * 得到全部表名
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @return String[]
     */
    public String[] getTables(String databaseName,String owner)
    {
        if(isClientlink())
            return (String[])callServerMethod(databaseName,owner);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        String type = getPoolType(databaseName);
        String sql = "";
        if("ORACLE".equals(type))
            sql = "SELECT TABLE_NAME"+
                  " FROM DBA_TABLES"+
                  " WHERE OWNER='" + owner.toUpperCase() + "'"+
                  " ORDER BY TABLE_NAME";
        else if("DB2".equals(type))
            sql = "SELECT TABNAME AS TABLE_NAME" +
                  " FROM SYSCAT.TABLES"+
                  " WHERE TABSCHEMA='" + owner.toUpperCase() + "'"+
                  " ORDER BY TABNAME";

        TParm parm = new TParm(select(databaseName,sql));
        return parm.getStringArray("TABLE_NAME");
    }
    /**
     * 得到全部表名和注释
     * @return TParm
     */
    public Map getTablesAndComments()
    {
        if(isClientlink())
            return (Map)callServerMethod();
        return getTablesAndComments("");
    }
    /**
     * 得到全部表名和注释
     * @param owner String 所有者
     * @return TParm
     */
    public Map getTablesAndComments(String owner)
    {
        if(isClientlink())
            return (Map)callServerMethod(owner);
        return getTablesAndComments("",owner);
    }
    /**
     * 得到全部表名和注释
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @return Map
     */
    public Map getTablesAndComments(String databaseName,String owner)
    {
        if(isClientlink())
            return (Map)callServerMethod(databaseName,owner);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        String sql = "";
        String type = getPoolType(databaseName);
        if("ORACLE".equals(type))
            sql = "SELECT A.TABLE_NAME,B.COMMENTS" +
                  " FROM DBA_TABLES A,ALL_TAB_COMMENTS B" +
                  " WHERE A.OWNER='" + owner.toUpperCase() + "'"+
                  " AND A.TABLE_NAME = B.TABLE_NAME"+
                  " AND A.OWNER = B.OWNER"+
                  " ORDER BY A.TABLE_NAME";
        else if("DB2".equals(type))
            sql = "SELECT TABNAME AS TABLE_NAME,REMARKS AS COMMENTS" +
                  " FROM SYSCAT.TABLES"+
                  " WHERE TABSCHEMA='" + owner.toUpperCase() + "'"+
                  " ORDER BY TABNAME";
        return select(databaseName,sql);
    }
    /**
     * 得到表的注释
     * @param tableName String 表名
     * @return String 注释
     */
    public String getTableComment(String tableName)
    {
        if(isClientlink())
            return (String)callServerMethod(tableName);
        return getTableComment("",tableName);
    }
    /**
     * 得到表的注释
     * @param owner String 所有者
     * @param tableName String 表名
     * @return String 注释
     */
    public String getTableComment(String owner,String tableName)
    {
        if(isClientlink())
            return (String)callServerMethod(owner,tableName);
        return getTableComment("",owner,tableName);
    }
    /**
     * 得到表的注释
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @return String 注释
     */
    public String getTableComment(String databaseName,String owner,String tableName)
    {
        if(isClientlink())
            return (String)callServerMethod(databaseName,owner,tableName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        String sql = "";
        String type = getPoolType(databaseName);
        if("ORACLE".equals(type))
            sql = "SELECT COMMENTS" +
                  " FROM ALL_TAB_COMMENTS" +
                  " WHERE OWNER='" + owner.toUpperCase() + "'"+
                  " AND TABLE_NAME='" + tableName.toUpperCase() + "'";
        else if("DB2".equals(type))
            sql = "SELECT REMARKS AS COMMENTS" +
                  " FROM SYSCAT.TABLES"+
                  " WHERE TABSCHEMA='" + owner.toUpperCase() + "'"+
                  " AND TABNAME='" + tableName.toUpperCase() + "'";

        TParm parm = new TParm(select(databaseName,sql));
        if(parm.getErrCode() < 0)
            return "";
        return parm.getValue("COMMENTS",0);
    }
    /**
     * 得到表的全部列
     * @param tableName String 表名
     * @return String[]
     */
    public String[] getColumns(String tableName)
    {
        if(isClientlink())
            return (String[])callServerMethod(tableName);
        return getColumns("",tableName);
    }
    /**
     * 得到表的全部列
     * @param owner String 所有者
     * @param tableName String 表名
     * @return String[]
     */
    public String[] getColumns(String owner,String tableName)
    {
        if(isClientlink())
            return (String[])callServerMethod(owner,tableName);
        return getColumns("",owner,tableName);
    }
    /**
     * 得到表的全部列
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @return String[]
     */
    public String[] getColumns(String databaseName,String owner,String tableName)
    {
        if(isClientlink())
            return (String[])callServerMethod(databaseName,owner,tableName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        String sql = "";
        String type = getPoolType(databaseName);
        if("ORACLE".equals(type))
            sql = "SELECT COLUMN_NAME"+
                  " FROM ALL_TAB_COLS"+
                  " WHERE OWNER='" + owner.toUpperCase() + "'"+
                  " AND TABLE_NAME='" + tableName.toUpperCase() + "'"+
                  " ORDER BY COLUMN_ID";
        else if("DB2".equals(type))
            sql = "SELECT COLNAME AS COLUMN_NAME"+
                  " FROM SYSCAT.COLUMNS"+
                  " WHERE TABSCHEMA='" + owner.toUpperCase() + "'"+
                  " AND TABNAME='" + tableName.toUpperCase() + "'"+
                  " ORDER BY COLNO";
        TParm parm = new TParm(select(databaseName,sql));
        return parm.getStringArray("COLUMN_NAME");
    }
    /**
     * 得到全部列名和注释
     * @param tableName String 表名
     * @return Map
     */
    public Map getColumnsAndComments(String tableName)
    {
        if(isClientlink())
            return (Map)callServerMethod(tableName);
        return getColumnsAndComments("",tableName);
    }
    /**
     * 得到全部列名和注释
     * @param owner String 所有者
     * @param tableName String 表名
     * @return Map
     */
    public Map getColumnsAndComments(String owner,String tableName)
    {
        if(isClientlink())
            return (Map)callServerMethod(owner,tableName);
        return getColumnsAndComments("",owner,tableName);
    }
    /**
     * 得到全部列名和注释
     * @param databaseName String 表名
     * @param owner String 所有者
     * @param tableName String 表名
     * @return Map
     */
    public Map getColumnsAndComments(String databaseName,String owner,String tableName)
    {
        if(isClientlink())
            return (Map)callServerMethod(databaseName,owner,tableName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        String sql = "";
        String type = getPoolType(databaseName);
        if("ORACLE".equals(type))
            sql = "SELECT A.COLUMN_NAME,B.COMMENTS"+
                  " FROM ALL_TAB_COLS A,ALL_COL_COMMENTS B"+
                  "  WHERE A.OWNER='" + owner.toUpperCase() + "'"+
                  "   AND A.TABLE_NAME='" + tableName.toUpperCase() + "'"+
                  "   AND A.OWNER=B.OWNER"+
                  "   AND A.TABLE_NAME=B.TABLE_NAME"+
                  "   AND A.COLUMN_NAME=B.COLUMN_NAME"+
                  "    ORDER BY COLUMN_ID";
        else if("DB2".equals(type))
            sql = "SELECT COLNAME AS COLUMN_NAME,REMARKS AS COMMENTS"+
                  " FROM SYSCAT.COLUMNS"+
                  " WHERE TABSCHEMA='" + owner.toUpperCase() + "'"+
                  " AND TABNAME='" + tableName.toUpperCase() + "'"+
                  " ORDER BY COLNO";

        return select(databaseName,sql);
    }
    /**
     * 得到全部列信息
     * @param tableName String 表名
     * @return Map
     */
    public Map getColumnsInf(String tableName)
    {
        if(isClientlink())
            return (Map)callServerMethod(tableName);
        return getColumnsInf("",tableName);
    }
    /**
     * 得到全部列信息
     * @param owner String 所有者
     * @param tableName String 表名
     * @return Map
     */
    public Map getColumnsInf(String owner,String tableName)
    {
        if(isClientlink())
            return (Map)callServerMethod(owner,tableName);
        return getColumnsInf("",owner,tableName);
    }
    /**
     * 得到全部列信息
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @return Map
     */
    public Map getColumnsInf(String databaseName,String owner,String tableName)
    {
        if(isClientlink())
            return (Map)callServerMethod(databaseName,owner,tableName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        String sql = "";
        String type = getPoolType(databaseName);
        if("ORACLE".equals(type))
        {
            TParm parm = new TParm(
                    select(databaseName,"SELECT A.COLUMN_NAME,B.COMMENTS,A.DATA_TYPE,A.DATA_LENGTH,A.DATA_PRECISION,"+
                           "A.DATA_SCALE,A.NULLABLE," +
                           " '' as PK," +
                           " A.DATA_DEFAULT" +
                           " FROM ALL_TAB_COLS A,ALL_COL_COMMENTS B" +
                           "  WHERE A.OWNER='" + owner.toUpperCase() + "'" +
                           "   AND A.TABLE_NAME='" + tableName.toUpperCase() +
                           "'" +
                           "   AND A.OWNER=B.OWNER" +
                           "   AND A.TABLE_NAME=B.TABLE_NAME" +
                           "   AND A.COLUMN_NAME=B.COLUMN_NAME" +
                           "    ORDER BY COLUMN_ID"));
            if (parm.getErrCode() < 0)
                return parm.getData();
            String[] pk = getPkColumns(databaseName, owner, tableName);
            if (pk == null || pk.length == 0)
                return parm.getData();
            int count = parm.getCount();
            for (int i = 0; i < pk.length; i++)
            {
                for (int j = 0; j < count; j++)
                    if (pk[i].equalsIgnoreCase(parm.getValue("COLUMN_NAME", j)))
                        parm.setData("PK", j, i + 1);
            }
            return parm.getData();
        }
        else if("DB2".equals(type))
            sql = "SELECT COLNAME AS COLUMN_NAME,REMARKS AS COMMENTS,TYPENAME AS DATA_TYPE,LENGTH AS DATA_LENGTH,LENGTH - SCALE AS DATA_PRECISION,"+
                  "SCALE AS DATA_SCALE,NULLS AS NULLABLE,KEYSEQ as PK,DEFAULT AS DATA_DEFAULT"+
                  " FROM SYSCAT.COLUMNS"+
                  " WHERE TABSCHEMA='" + owner.toUpperCase() + "'"+
                  " AND TABNAME='" + tableName.toUpperCase() + "'"+
                  " ORDER BY COLNO";

        return select(databaseName,sql);
    }
    /**
     * 得到表的主键列表
     * @param tableName String 表名
     * @return String[]
     */
    public String[] getPkColumns(String tableName)
    {
        if(isClientlink())
            return (String[])callServerMethod(tableName);
        return getPkColumns("",tableName);
    }
    /**
     * 得到表的主键列表
     * @param owner String 所有者
     * @param tableName String 表名
     * @return String[]
     */
    public String[] getPkColumns(String owner,String tableName)
    {
        if(isClientlink())
            return (String[])callServerMethod(owner,tableName);
        return getPkColumns("",owner,tableName);
    }
    /**
     * 得到表的主键列表
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @return String[]
     */
    public String[] getPkColumns(String databaseName,String owner,String tableName)
    {
        if(isClientlink())
            return (String[])callServerMethod(databaseName,owner,tableName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();

        TParm parm = new TParm(select(databaseName,"SELECT COLUMN_NAME FROM ALL_CONS_COLUMNS A,ALL_CONSTRAINTS B"+
                                      " WHERE A.CONSTRAINT_NAME=B.CONSTRAINT_NAME"+
                                      " AND A.OWNER='" + owner.toUpperCase() + "'"+
                                      " AND A.TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      " AND B.CONSTRAINT_TYPE='P'"+
                                      " ORDER BY POSITION"));
        return parm.getStringArray("COLUMN_NAME");
    }
    /**
     * 得到列类型
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 类型
     */
    public String getColumnType(String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(tableName,columnName);
        return getColumnType("",tableName,columnName);
    }
    /**
     * 得到列类型
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 类型
     */
    public String getColumnType(String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(owner,tableName,columnName);
        return getColumnType("",owner,tableName,columnName);
    }
    /**
     * 得到列类型
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 类型
     */
    public String getColumnType(String databaseName,String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(databaseName,owner,tableName,columnName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        TParm parm = new TParm(select("SELECT DATA_TYPE"+
                                      " FROM ALL_TAB_COLS"+
                                      " WHERE OWNER='" + owner.toUpperCase() + "'"+
                                      " AND TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      " AND COLUMN_NAME='" + columnName.toUpperCase() + "'"));
        if(parm.getErrCode() < 0)
            return "";
        return parm.getValue("DATA_TYPE",0);
    }
    /**
     * 得到类型
     * @param tableName String 表名
     * @param columnName String[] 列列表
     * @return String[]
     */
    public String[] getColumnType(String tableName,String columnName[])
    {
        if(isClientlink())
            return (String[])callServerMethod(tableName,columnName);
        return getColumnType("",tableName,columnName);
    }
    /**
     * 得到类型
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String[] 列列表
     * @return String[] 类型
     */
    public String[] getColumnType(String owner,String tableName,String columnName[])
    {
        if(isClientlink())
            return (String[])callServerMethod(owner,tableName,columnName);
        return getColumnType("",owner,tableName,columnName);
    }
    /**
     * 得到类型
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String[] 列列表
     * @return String[] 类型
     */
    public String[] getColumnType(String databaseName,String owner,String tableName,String columnName[])
    {
        if(isClientlink())
            return (String[])callServerMethod(databaseName,owner,tableName,columnName,columnName);
        String types[] = new String[columnName.length];
        for(int i = 0;i < columnName.length;i++)
            types[i] = getColumnType(databaseName,owner,tableName,columnName[i]);
        return types;
    }
    /**
     * 得到列长度
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnLength(String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(tableName,columnName);
        return getColumnLength("",tableName,columnName);
    }
    /**
     * 得到列长度
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnLength(String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(owner,tableName,columnName);
        return getColumnLength("",owner,tableName,columnName);
    }
    /**
     * 得到列长度
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnLength(String databaseName,String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(databaseName,owner,tableName,columnName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        TParm parm = new TParm(select("SELECT DATA_LENGTH"+
                                      " FROM ALL_TAB_COLS"+
                                      " WHERE OWNER='" + owner.toUpperCase() + "'"+
                                      " AND TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      " AND COLUMN_NAME='" + columnName.toUpperCase() + "'"));
        if(parm.getErrCode() < 0)
            return 0;
        return parm.getInt("DATA_LENGTH",0);
    }
    /**
     * 得到列数字长度
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnPrecision(String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(tableName,columnName);
        return getColumnPrecision("",tableName,columnName);
    }
    /**
     * 得到列数字长度
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnPrecision(String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(owner,tableName,columnName);
        return getColumnPrecision("",owner,tableName,columnName);
    }
    /**
     * 得到列数字长度
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnPrecision(String databaseName,String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(databaseName,owner,tableName,columnName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        TParm parm = new TParm(select("SELECT DATA_PRECISION"+
                                      " FROM ALL_TAB_COLS"+
                                      " WHERE OWNER='" + owner.toUpperCase() + "'"+
                                      " AND TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      " AND COLUMN_NAME='" + columnName.toUpperCase() + "'"));
        if(parm.getErrCode() < 0)
            return 0;
        return parm.getInt("DATA_PRECISION",0);
    }
    /**
     * 得到列小数长度
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnScale(String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(tableName,columnName);
        return getColumnScale("",tableName,columnName);
    }
    /**
     * 得到列小数长度
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnScale(String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(owner,tableName,columnName);
        return getColumnScale("",owner,tableName,columnName);
    }
    /**
     * 得到列小数长度
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return int 长度
     */
    public int getColumnScale(String databaseName,String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Integer)callServerMethod(databaseName,owner,tableName,columnName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        TParm parm = new TParm(select("SELECT DATA_SCALE"+
                                      " FROM ALL_TAB_COLS"+
                                      " WHERE OWNER='" + owner.toUpperCase() + "'"+
                                      " AND TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      " AND COLUMN_NAME='" + columnName.toUpperCase() + "'"));
        if(parm.getErrCode() < 0)
            return 0;
        return parm.getInt("DATA_SCALE",0);
    }
    /**
     * 得到列是非允许为空
     * @param tableName String 表名
     * @param columnName String 列名
     * @return boolean true 允许为空 false 不允许为空
     */
    public boolean getColumnNullAble(String tableName,String columnName)
    {
        if(isClientlink())
            return (Boolean)callServerMethod(tableName,columnName);
        return getColumnNullAble("",tableName,columnName);
    }
    /**
     * 得到列是非允许为空
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return boolean true 允许为空 false 不允许为空
     */
    public boolean getColumnNullAble(String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Boolean)callServerMethod(owner,tableName,columnName);
        return getColumnNullAble("",owner,tableName,columnName);
    }
    /**
     * 得到列是非允许为空
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return boolean true 允许为空 false 不允许为空
     */
    public boolean getColumnNullAble(String databaseName,String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (Boolean)callServerMethod(databaseName,owner,tableName,columnName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        TParm parm = new TParm(select("SELECT NULLABLE"+
                                      " FROM ALL_TAB_COLS"+
                                      " WHERE OWNER='" + owner.toUpperCase() + "'"+
                                      " AND TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      " AND COLUMN_NAME='" + columnName.toUpperCase() + "'"));
        if(parm.getErrCode() < 0)
            return false;
        return parm.getBoolean("NULLABLE",0);
    }
    /**
     * 得到列的默认值
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 默认值
     */
    public String getColumnDefault(String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(tableName,columnName);
        return getColumnDefault("",tableName,columnName);
    }
    /**
     * 得到列的默认值
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 默认值
     */
    public String getColumnDefault(String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(owner,tableName,columnName);
        return getColumnDefault("",owner,tableName,columnName);
    }
    /**
     * 得到列的默认值
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 默认值
     */
    public String getColumnDefault(String databaseName,String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(databaseName,owner,tableName,columnName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        TParm parm = new TParm(select("SELECT DATA_DEFAULT"+
                                      " FROM ALL_TAB_COLS"+
                                      " WHERE OWNER='" + owner.toUpperCase() + "'"+
                                      " AND TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      " AND COLUMN_NAME='" + columnName.toUpperCase() + "'"));
        if(parm.getErrCode() < 0)
            return "";
        return parm.getValue("DATA_DEFAULT",0);
    }
    /**
     * 得到列的注释
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 注释
     */
    public String getColumnComment(String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(tableName,columnName);
        return getColumnComment("",tableName,columnName);
    }
    /**
     * 得到列的注释
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 注释
     */
    public String getColumnComment(String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(owner,tableName,columnName);
        return getColumnComment("",owner,tableName,columnName);
    }
    /**
     * 得到列的注释
     * @param databaseName String 数据库名
     * @param owner String 所有者
     * @param tableName String 表名
     * @param columnName String 列名
     * @return String 注释
     */
    public String getColumnComment(String databaseName,String owner,String tableName,String columnName)
    {
        if(isClientlink())
            return (String)callServerMethod(databaseName,owner,tableName,columnName);
        if(owner == null || owner.length() == 0)
            owner = getOwner();
        TParm parm = new TParm(select("SELECT B.COMMENTS"+
                                      " FROM ALL_TAB_COLS A,ALL_COL_COMMENTS B"+
                                      "  WHERE A.OWNER='" + owner.toUpperCase() + "'"+
                                      "   AND A.TABLE_NAME='" + tableName.toUpperCase() + "'"+
                                      "   AND A.COLUMN_NAME='" + columnName.toUpperCase() + "'"+
                                      "   AND A.OWNER=B.OWNER"+
                                      "   AND A.TABLE_NAME=B.TABLE_NAME"+
                                      "   AND A.COLUMN_NAME=B.COLUMN_NAME"+
                                      "    ORDER BY COLUMN_ID"));
        if(parm.getErrCode() < 0)
            return "";
        return parm.getValue("COMMENTS",0);
    }
    public static void main(String args[])
    {
        //TDebug.initServer();
        TDebug.initClient();
        TJDODBTool tool = new TJDODBTool();
        System.out.println(tool.getOwner());
        System.out.println(tool.getTableComment("SYS_FEE"));
        //String s[] = tool.getPkColumns("SYS_FEE");
        //System.out.println(StringTool.getString(s));
    }
}
