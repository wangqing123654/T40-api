package com.dongyang.jdo;

import com.dongyang.util.StringTool;
import com.dongyang.data.TParm;

import java.util.Vector;
import com.dongyang.manager.TCM_Transform;
import java.sql.Timestamp;
import java.util.Map;
import com.dongyang.util.FileTool;
import java.util.ArrayList;
import com.dongyang.util.Script;
import com.dongyang.util.RunClass;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DSerializable;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.ui.event.BaseEvent;

/**
 *
 * <p>Title: ���ݴ洢</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.11.14
 * @version 1.0
 */
public class TDataStore implements DSerializable
{
    public static final String PRIMARY = "Primary!";
    public static final String DELETE = "Delete!";
    public static final String MODIFY = "Modify!";
    public static final String FILTER = "Filter!";
    public static final String TABLE = "Table!";
    public static final String DELETE_INSERT_MODE = "DeleteInsertMode!";
    public static final String UPDATE_MODE = "UpdateMode!";
    public static final String ACTION_SET_ITEM = "SET_ITEM";
    public static final String ACTION_SET_OTHER_ITEM = "SET_OTHER_ITEM";
    
    public static final String KEY_WORD_ONE="FLG";
    private int IDMax;
    /**
     * ��������
     */
    private TParm primaryParm = new TParm();
    /**
     * ɾ��������
     */
    private TParm modifiedParm = new TParm();
    /**
     * ɾ��������
     */
    private TParm deletedParm = new TParm();
    /**
     * ���˻�����
     */
    private TParm filterParm = new TParm();
    /**
     * ����Ϣ
     */
    private TParm tableInfParm = new TParm();
    /**
     * ������Ϣ
     */
    private String errText = "";
    /**
     * ���ݿ���
     */
    private String databaseName;
    /**
     * ������
     */
    private String owner;
    /**
     * ����
     */
    private String tableName;
    /**
     * where����
     */
    private String where;
    /**
     * ��������
     */
    private String orderBy;
    /**
     * ������
     */
    private String[] columns;
    /**
     * ������
     */
    private String[] columnType;
    /**
     * �Ƿ����ù���
     */
    private boolean isFilter;
    /**
     * ��������
     */
    private String updateMode = UPDATE_MODE;
    /**
     * ���ݿ⹤��
     */
    private TJDODBTool dbTool = new TJDODBTool();
    /**
     * ����ʱ��
     */
    private String retrieveTime = "";
    /**
     * ��������
     */
    private String filterString = "";
    /**
     * ����
     */
    private String sort;
    /**
     * ��������
     */
    private Map otherData;
    /**
     * �ܷ񱣴�
     */
    private boolean canUpdate = true;
    /**
     * SQL���
     */
    private String sql;
    /**
     * �Ƿ�Distinct
     */
    private boolean isDistinct;
    /**
     * ��ת��SQL���
     */
    private Map columnSql = new HashMap();
    /**
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * ���ֶ���
     */
    private String languageMap;
    /**
     * ����
     */
    private String language;
    /**
     * ��������
     */
    private Map changeValueMap;
    /**
     * SQL���ǰ׺
     */
    private String sqlHead = "";
    /**
     * �������ݿ���
     * @param databaseName String
     */
    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }
    /**
     * �õ����ݿ���
     * @return String
     */
    public String getDatabaseName()
    {
        return databaseName;
    }
    /**
     * ����������
     * @param owner String
     */
    public void setOwner(String owner)
    {
        this.owner = owner;
    }
    /**
     * �õ�������
     * @return String
     */
    public String getOwner()
    {
        return owner;
    }
    /**
     * ���ñ���
     * @param tableName String
     */
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getTableName()
    {
        return tableName;
    }
    /**
     * ����Where����
     * @param where String
     */
    public void setWhere(String where)
    {
        this.where = where;
    }
    /**
     * �õ�Where����
     * @return String
     */
    public String getWhere()
    {
        return where;
    }
    /**
     * ��������
     * @param orderBy String
     */
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getOrderBy()
    {
        return orderBy;
    }
    /**
     * ����������
     * @param columns String[]
     */
    public void setColumns(String columns[])
    {
        this.columns = columns;
    }
    /**
     * �õ�������
     * @return String[]
     */
    public String[] getColumns()
    {
        return columns;
    }
    /**
     * �õ���������λ��
     * @param name String
     * @return int
     */
    public int getColumnIndex(String name)
    {
        for(int i = 0;i < columns.length;i++)
            if(name.equals(columns[i]))
                return i;
        return -1;
    }
    /**
     * �õ����ִ�
     * @return String
     */
    private String getColumnString()
    {
        return getColumnString(",");
    }
    /**
     * �õ����ִ�
     * @param fg String �ָ��
     * @return String
     */
    private String getColumnString(String fg)
    {
        return StringTool.getString(getColumns(),fg);
    }
    private String getColumnStringSelect()
    {
        if(getColumns() == null)
            return "";
        StringBuffer sb = new StringBuffer();
        String strColumn="";
        for (int i = 0; i < getColumns().length; i++)
        {
            if (sb.length() > 0)
                sb.append(",");
            //System.out.println("------test getColumns()["+i+"]-------"+getColumns()[i]);
            //add by lx 2013/03/29 ����  'Y' FLG���
            strColumn=getColumns()[i];
            if(strColumn.equals(KEY_WORD_ONE)){
            	strColumn="'N' "+KEY_WORD_ONE;
            }
            sb.append(getColumnSql(strColumn));
        }
        return sb.toString();
    }
    /**
     * ����������
     * @param columnType String[]
     */
    public void setColumnType(String[] columnType)
    {
        this.columnType = columnType;
    }
    /**
     * �õ�������
     * @return String[]
     */
    public String[] getColumnType()
    {
        return columnType;
    }
    /**
     * ����SQL���
     * @param SQL String
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setSQL(String SQL)
    {
        if(!canUpdate)
        {
            this.sql = SQL;
            return true;
        }
        if(!parseSQL(SQL))
            return false;
        return true;
    }
    /**
     * �õ�SQL���
     * @return String
     */
    public String getSQL()
    {
        if(!canUpdate)
        {
            return sql;
        }
        String columnString = getColumnStringSelect();
        if(columnString.length() == 0)
            return "";
        if(getTableName() == null || getTableName().length() == 0)
            return "";
        String where = getWhere();
        if(where != null && where.length() > 0)
            where = " WHERE " + where;
        String orderBy = getOrderBy();
        if(orderBy != null && orderBy.length() > 0)
            orderBy = " ORDER BY " + orderBy;
        if(isDistinct)
            columnString = "DISTINCT " + columnString;
        return "SELECT " + columnString + " FROM " + getTableName() + where + orderBy;
    }
    /**
     * ���ô�����Ϣ
     * @param errText String
     */
    public void setErrText(String errText)
    {
        this.errText = errText;
    }
    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getErrText()
    {
        return errText;
    }
    /**
     * ���ø���ģʽ
     * DELETE_INSERT_MODE ɾ���ڲ���
     * UPDATE_MODE ����
     * @param updateMode String
     */
    public void setUpdateMode(String updateMode)
    {
        this.updateMode = updateMode;
    }
    /**
     * �õ�����ģʽ
     * @return String
     */
    public String getUpdateMode()
    {
        return updateMode;
    }
    /**
     * �õ����ݿ⹤��
     * @return TJDODBTool
     */
    private TJDODBTool getDBTool()
    {
        return dbTool;
    }
    /**
     * ����SQL
     * @param SQL String
     * @return boolean
     */
    private boolean parseSQL(String SQL)
    {
        setErrText("");
        if(SQL == null || SQL.trim().length() == 0)
        {
            setErrText("SQL���Ϊ��");
            return false;
        }
        SQL = SQL.trim();
        String upperSQL = SQL.toUpperCase();
        if(!upperSQL.startsWith("SELECT"))
        {
            setErrText("SQL��䲻��SELECT���");
            return false;
        }
        int fromIndex = upperSQL.indexOf("FROM");
        if(fromIndex == -1)
        {
            setErrText("SQL���û���ҵ�FROM");
            return false;
        }
        int whereIndex = upperSQL.indexOf("WHERE");
        int orderByIndex = upperSQL.indexOf("ORDER BY");
        String column = SQL.substring(6,fromIndex).trim();
        int index = whereIndex;
        if(index == -1)
            index = orderByIndex;
        if(index == -1)
            index = SQL.length();
        String from = SQL.substring(fromIndex + 4,index).trim();
        String where = "";
        if(whereIndex > 0)
        {
            index = orderByIndex;
            if(index == -1)
                index = SQL.length();
            where = SQL.substring(whereIndex + 5,index).trim();
        }
        String orderBy = "";
        if(orderByIndex > 0)
            orderBy = SQL.substring(orderByIndex + 8,SQL.length()).trim();
        if(from.length() == 0)
        {
            setErrText("SQL���û���ҵ�����");
            return false;
        }
        String tables[] = StringTool.parseLine(from,",");
        if(tables.length > 1)
        {
            setErrText("ֻ�ܴ��������");
            return false;
        }
        setTableName(tables[0].toUpperCase());
        String columns[] = null;
        //System.out.println("----------str column----------"+column);
        if("*".equals(column) && getTableName() != null && getTableName().length() > 0){
        	//System.out.println("----------��*----------");
            columns = getDBTool().getColumns(getDatabaseName(),getOwner(),getTableName());
                        
        //.*�������   ������������кϲ�  Modified by lx 2013/03/25
        }else if(column.indexOf(" "+KEY_WORD_ONE)!=-1){
        	//System.out.println("------�������.*��-------");
        	String columns1[] = StringTool.parseLine(column, ',', "()");
        	String tableName1=null;
        	String columns2[]=null;
        	StringBuffer bf=new StringBuffer();
            for(int i = 0;i < columns1.length;i++){
            	//System.out.println("-----columns1["+i+"]-------"+columns1[i]);
            	//�������������ڿո����  'Y' FLG
            	if(columns1[i].toUpperCase().trim().indexOf(" ")!=-1){
            		bf.append(columns1[i].toUpperCase().trim().split(" ")[1]);
            		//System.out.println("-----Y FLG columns1["+i+"]--------"+columns1[i].toUpperCase().trim().split(" ")[1]);                   
            		bf.append(",");
                //OPD_ORDER.*������	
            	}if(columns1[i].toUpperCase().trim().indexOf(".*")!=-1){
            		tableName1=columns1[i].toUpperCase().trim().split("\\.")[0];
            		columns2 = getDBTool().getColumns(getDatabaseName(),getOwner(),tableName1);
            		for(String str:columns2){
            			//System.out.println("-----str-----"+str);
            			bf.append(str+",");
            		}           		           		
                }

            }
            String strbf=bf.toString();           
            column=strbf.substring(0, strbf.length() - 1);
            //System.out.println("=====strbf======"+strbf);          
            columns = StringTool.parseLine(column, ',', "()");
        
        //�������
        }else
        {
        	//System.out.println("----------not *----------");
            columns = StringTool.parseLine(column, ',', "()");
            for(int i = 0;i < columns.length;i++){
               columns[i] = columns[i].toUpperCase().trim();
            }
            if(columns[0].endsWith("DISTINCT"))
            {
                columns[0] = columns[0].substring(0,columns[0].length() - 8).trim();
                isDistinct = true;
            }
        }
        tableInfParm = new TParm(getDBTool().getColumnsInf(getDatabaseName(),getOwner(),getTableName()));
        //System.out.println("---------tableInfParm111111----------------"+tableInfParm);
        
        if(tableInfParm.getErrCode() < 0)
        {
            setErrText(tableInfParm.getErrText());
            return false;
        }
        setWhere(where);
        setOrderBy(orderBy);
        //System.out.println("-------columns1111111111111111---------"+columns);
        setColumns(columns);
        setColumnType(columnType);
        return true;
    }
    /**
     * ��ѯ����
     * @return int
     */
    public int retrieve()
    {
        return retrieve(-1);
    }
    public int retrieve(int maxCount)
    {
        isFilter = false;
        primaryParm = new TParm();
        filterParm = new TParm();
        modifiedParm = new TParm();
        deletedParm = new TParm();
        String sql = getSQL();
        //System.out.println("========retrieve sql=========="+sql);
        if(sql.length() == 0)
        {
            setErrText("SQL������");
            return -1;
        }
        //System.out.println("====retrieve SQL===="+getSQL());
        primaryParm = new TParm(getDBTool().select(getDatabaseName(),getSQL(),maxCount));
        if(primaryParm.getErrCode() < 0)
        {
            setErrText(primaryParm.getErrText());
            return -1;
        }
        int count = primaryParm.getCount();
        if(count < 0)
            count = 0;
        for(int i = 0;i < count;i++)
        {
            primaryParm.addData("#ID#", i);
            primaryParm.addData("#MODIFY#",false);
            primaryParm.addData("#NEW#",false);
            primaryParm.addData("#ACTIVE#",true);
        }
        IDMax = count - 1;
        return count;
    }
    /**
     * �õ���������
     * @return int
     */
    public int rowCount()
    {
        int count = primaryParm.getCount();
        if(count < 0)
            count = 0;
        return count;
    }
    /**
     * �õ�����ȥ������
     * @return int
     */
    public int rowCountFilter()
    {
        int count = filterParm.getCount();
        if(count < 0)
            count = 0;
        return count;
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return Object
     */
    public Object getItemData(int row,int column)
    {
        return getItemData(row,column,PRIMARY);
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return Object
     */
    public Object getItemData(int row,String column)
    {
        return getItemData(row,column,PRIMARY);
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int ����
     * @return String
     */
    public String getItemString(int row,int column)
    {
        return TCM_Transform.getString(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return String
     */
    public String getItemString(int row,String column)
    {
        return TCM_Transform.getString(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return int
     */
    public int getItemInt(int row,int column)
    {
        return TCM_Transform.getInt(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return int
     */
    public int getItemInt(int row,String column)
    {
        return TCM_Transform.getInt(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return double
     */
    public double getItemDouble(int row,int column)
    {
        return TCM_Transform.getDouble(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return double
     */
    public double getItemDouble(int row,String column)
    {
        return TCM_Transform.getDouble(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return Timestamp
     */
    public Timestamp getItemTimestamp(int row,int column)
    {
        return TCM_Transform.getTimestamp(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String �к�
     * @return Timestamp
     */
    public Timestamp getItemTimestamp(int row,String column)
    {
        return TCM_Transform.getTimestamp(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @param dwbuffer String ������
     * @return Object
     */
    public Object getItemData(int row,int column,String dwbuffer)
    {
        return getItemData(row,column,dwbuffer,false);
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String �к�
     * @param dwbuffer String ������
     * @return Object
     */
    public Object getItemData(int row,String column,String dwbuffer)
    {
        return getItemData(row,column,dwbuffer,false);
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @param dwbuffer String ������
     * @param originalvalue boolean boolean true ȡ��ֵ false ȡ��ֵ
     * @return Object
     */
    public Object getItemData(int row,int column,String dwbuffer,boolean originalvalue)
    {
        return getItemData(row,getColumns()[column],dwbuffer,originalvalue);
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @param dwbuffer String ������
     * @param originalvalue boolean true ȡ��ֵ false ȡ��ֵ
     * @return Object
     */
    public Object getItemData(int row,String column,String dwbuffer,boolean originalvalue)
    {
        if(row < 0)
            return null;
        if(PRIMARY.equals(dwbuffer))
        {
            if(row >= primaryParm.getCount())
                return null;
            if(!originalvalue)
                return primaryParm.getData(column,row);
            if(!primaryParm.getBoolean("#MODIFY#",row))
                return primaryParm.getData(column,row);
            int oldRow = findModifiedRow(primaryParm.getInt("#ID#", row));
            return modifiedParm.getData(column,oldRow);
        }
        if(DELETE.equals(dwbuffer))
        {
            if(row >= deletedParm.getCount())
                return null;
            return deletedParm.getData(column,row);
        }
        if(FILTER.equals(dwbuffer))
        {
            if(row >= filterParm.getCount())
                return null;
            if(!originalvalue)
                return filterParm.getData(column,row);
            if(!filterParm.getBoolean("#MODIFY#",row))
                return filterParm.getData(column,row);
            int oldRow = findModifiedRow(filterParm.getInt("#ID#", row));
            return modifiedParm.getData(column,oldRow);
        }
        return null;
    }
    /**
     * ��λ�޸��к�
     * @param ID int
     * @return int
     */
    private int findModifiedRow(int ID)
    {
        if(modifiedParm == null)
            return -1;
        int count = modifiedParm.getCount();
        if(count <= 0)
            return -1;
        for(int i = 0;i < count;i++)
            if (modifiedParm.getInt("#ID#",i) == ID)
                return i;
        return -1;
    }
    /**
     * ��λ�����к�
     * @param ID int
     * @return int
     */
    private int findFilterRow(int ID)
    {
        if(filterParm == null)
            return -1;
        int count = filterParm.getCount();
        if(count <= 0)
            return -1;
        for(int i = 0;i < count;i++)
            if(filterParm.getInt("#ID#",i) == ID)
                return i;
        return -1;
    }
    /**
     * ��λ���������к�
     * @param ID int
     * @return int
     */
    private int findPrimaryRow(int ID)
    {
        if(primaryParm == null)
            return -1;
        int count = primaryParm.getCount();
        if(count <= 0)
            return -1;
        for(int i = 0;i < count;i++)
            if(primaryParm.getInt("#ID#",i) == ID)
                return i;
        return -1;
    }
    /**
     * ��������
     * @return int
     */
    public int insertRow()
    {
        return insertRow(-1);
    }
    /**
     * ��������
     * @param row int �к�
     * @return int
     */
    public int insertRow(int row)
    {
        String[] columns = getColumns();
        int newRow = -1;
        for(int i = 0;i < columns.length;i++){
        	//System.out.println("======columns["+i+"]======"+columns[i]);
        	//System.out.println("======getTableColumnIndex["+i+"]======"+getTableColumnIndex(columns[i]));
        	//add by lx 2013/04/15
        	if(columns[i].equals(KEY_WORD_ONE)){
        		newRow = primaryParm.insertData(columns[i], row,'N');
        	}else{
	        	//
	            newRow = primaryParm.insertData(columns[i], row,
	                                            getDefaultValue(getTableColumnIndex(columns[i])));
        	}
        
    }
        IDMax ++;
        primaryParm.insertData("#ID#",row,IDMax);
        primaryParm.insertData("#MODIFY#",row,false);
        primaryParm.insertData("#NEW#",row,true);
        primaryParm.insertData("#ACTIVE#",row,true);

        primaryParm.setData("ACTION","COUNT",primaryParm.getInt("ACTION","COUNT") + 1);
        
        //System.out.println("--------insertRow primaryParm------"+primaryParm);

        if(isFilter)
        {
            TParm parm = primaryParm.getRow(newRow);
            int c = filterParm.getCount();
            if(c < 0)
                c = 0;
            filterParm.setRowData(c,parm,-1);
            filterParm.setData("ACTION","COUNT",filterParm.getInt("ACTION","COUNT") + 1);
            
            //System.out.println("--------insertRow filterParm------"+filterParm);
        }
        return newRow;
    }
    /**
     * �õ��е�Ĭ��ֵ
     * @param column int �к�
     * @return Object
     */
    public Object getDefaultValue(int column)
    {
    	if(!canUpdate)
            return null;
        String type = tableInfParm.getValue("DATA_TYPE",column);    
        //System.out.println("=======type======="+type);
        String defaultValue = tableInfParm.getValue("DATA_DEFAULT",column).trim();
        //System.out.println("=======defaultValue======="+defaultValue);
        //System.out.println("=====columns["+column+"]======="+columns[column]);

        //
        int scale = tableInfParm.getInt("DATA_SCALE",column);
        if("VARCHAR2".equals(type) || "CHAR".equals(type))
        {
            if(defaultValue.length() > 0)
                if(defaultValue.startsWith("'"))
                    return defaultValue.substring(1,defaultValue.length() - 1);
            return "";
        }
        if("NUMBER".equals(type))
        {
            if(scale > 0)
            {
                if (defaultValue.length() > 0)
                    return Double.parseDouble(defaultValue);
                return 0.0;
            }
            if (defaultValue.length() > 0)
                return Long.parseLong(defaultValue);
            return 0;
        }
        if("DATE".equals(type))
        {
            return null;
        }
        return null;
    }
    /**
     * �õ���������
     * @param column String
     * @param obj Object
     * @return String
     */
    private String getUpdateValue(int column,Object obj)
    {
        if(obj == null)
            return "null";
        String type = tableInfParm.getValue("DATA_TYPE",column);
        if("VARCHAR2".equals(type))
            return "'" + obj + "'";
        if("CHAR".equals(type))
            return "'" + obj + "'";
        if("NUMBER".equals(type))
            return obj.toString();
        if("DATE".equals(type))
        {
            if(obj instanceof Timestamp)
                return "to_Date('" + StringTool.getString((Timestamp)obj,"yyyy/MM/dd HH:mm:ss") + "','YYYY/MM/DD HH24:MI:SS')";
            if(obj instanceof String)
            {
                String data = (String)obj;
                if(data.length() == 8)
                    return "to_Date('" + data + "','YYYYMMDD')";
                if(data.length() == 10)
                    return "to_Date('" + data.substring(0,4) +
                           data.substring(5,7) +
                           data.subSequence(8,10) + "','YYYYMMDD')";
               if(data.length() == 14)
                   return "to_Date('" + obj + "','YYYYMMDDHH24MISS')";
               if(data.length() == 19)
                   return "to_Date('" + data.substring(0,4) +
                          data.substring(5,7) +
                          data.subSequence(8,10) +
                          data.subSequence(11,13) +
                          data.subSequence(14,16) +
                          data.subSequence(17,19) + "','YYYYMMDDHH24MISS')";
            }
            return "to_Date('" + obj + "','YYYY/MM/DD HH24:MI:SS')";
        }
        return null;
    }
    /**
     * �õ�������
     * @param buffer String
     * @return TParm
     */
    public TParm getBuffer(String buffer)
    {
        if(PRIMARY.equals(buffer))
            return primaryParm;
        if(MODIFY.equals(buffer))
            return modifiedParm;
        if(DELETE.equals(buffer))
            return deletedParm;
        if(FILTER.equals(buffer))
            return filterParm;
        if(TABLE.equals(buffer))
            return tableInfParm;
        return null;
    }
    /**
     * ����ֵ
     * @param row int �к�
     * @param column int �к�
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setItem(int row,int column,Object value)
    {
    	//System.out.println("----row-----"+row);
    	//System.out.println("----column-----"+column);
    	//System.out.println("----value-----"+value);
        return setItem(row,getColumns()[column],value);
    }
    /**
     * ����ֵ
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setItem(int row,String column,Object value)
    {
    	//System.out.println("----row-----"+row);
    	//System.out.println("----column-----"+column);
    	//System.out.println("----value-----"+value);
        return setItem(row,column,value,PRIMARY) >= 0;
    }
    /**
     * ��Ŀ�ı䶯��
     * @param column String
     * @param value Object
     */
    public void actionSetItem(String column,Object value)
    {
        callEvent(ACTION_SET_ITEM,new Object[]{column,value},new String[]{"java.lang.String","java.lang.Object"});
    }
    /**
     * ������Ŀ�ı䶯��
     * @param column String
     * @param value Object
     */
    public void actionSetOtherItem(String column,Object value)
    {
        callEvent(ACTION_SET_OTHER_ITEM,new Object[]{column,value},new String[]{"java.lang.String","java.lang.Object"});
    }

    /**
     * �õ��¼�����
     * @return String
     */
    public static String helpEvent()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("//==================================//\n");
        sb.append("//    TDataStore �¼�����\n");
        sb.append("//==================================//\n");
        sb.append("������ ACTION_SET_ITEM\n");
        sb.append("    �� DataStore �� setItem ����������ʱ�����¼���\n");
        sb.append("������ ACTION_SET_OTHER_ITEM\n");
        sb.append("    �� DataStore �� setOtherColumnValue ����������ʱ�����¼���\n");
        sb.append("��������:\n");
        sb.append("    tds.addEventListener(tds.ACTION_SET_ITEM,this,\"onSetItemEvent\");\n");
        sb.append("����ʵ�ַ���:\n");
        sb.append("    public void onSetItemEvent(String columnName,Object value){\n");
        sb.append("    }\n");
        return sb.toString();
    }
    /**
     * ����ֵ
     * @param row int �к�
     * @param column String ����
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��
     */
    private boolean setItemPrimary(int row,String column,Object value)
    {
        if(row >= primaryParm.getCount())
            return false;
        if(!existsColumn(column))
            return setOtherColumnValue(primaryParm,row,column,value);
        value = checkData(column,value);
        if(equalsValue(primaryParm.getData(column,row),value))
            return true;
        //������
        if(primaryParm.getBoolean("#NEW#",row))
        {
            primaryParm.setData(column,row,value);
            if(isFilter)
            {
                int idRow = findFilterRow(primaryParm.getInt("#ID#",row));
                filterParm.setData(column, idRow, value);
                //add by lx ��������״̬
                filterParm.setData("#NEW#",idRow,true);
            }
            //lx test
            //System.out.println("--------NEW is true-------"+row);
            //add by lx ��������״̬
            primaryParm.setData("#NEW#",row,true);
            //��Ŀ�ı䶯��
            actionSetItem(column,value);
            return true;
        }
        //û���޸Ĺ�
        if(!primaryParm.getBoolean("#MODIFY#",row))
        {
            TParm parm = primaryParm.getRow(row);
            parm.removeData("#MODIFY#");
            parm.removeData("#NEW#");
            int modifyCount = modifiedParm.getCount();
            if(modifyCount < 0)
                modifyCount = 0;
            modifiedParm.setRowData(modifyCount,parm,-1);
            modifiedParm.setData("ACTION","COUNT",modifiedParm.getInt("ACTION","COUNT") + 1);
            //lx test
            //System.out.println("--------MODIFY is true-------"+row);
            primaryParm.setData("#MODIFY#",row,true);
            primaryParm.setData(column,row,value);
            if(isFilter)
            {
                int idRow = findFilterRow(primaryParm.getInt("#ID#",row));
                filterParm.setData(column,idRow,value);
                filterParm.setData("#MODIFY#",idRow,true);
            }
            //��Ŀ�ı䶯��
            actionSetItem(column,value);
            return true;
        }
        //�޸�
        primaryParm.setData(column,row,value);
        if(isFilter)
        {
            int idRow = findFilterRow(primaryParm.getInt("#ID#",row));
            filterParm.setData(column,idRow,value);
        }
        //ȡ���޸�״̬
        if(!checkModifyRow(PRIMARY,row))
        {
            int idRow = findModifiedRow(primaryParm.getInt("#ID#", row));
            modifiedRemoveRow(idRow);
            primaryParm.setData("#MODIFY#",row,false);
            if(isFilter)
            {
                int fidRow = findFilterRow(primaryParm.getInt("#ID#",row));
                filterParm.setData("#MODIFY#",fidRow,false);
            }
        }
        //��Ŀ�ı䶯��
        actionSetItem(column,value);
        return true;
    }
    /**
     * ����У���Ƿ��޸�
     */
    public void checkModify()
    {
        if(isFilter)
        {
            int count = filterParm.getCount();
            for(int row = 0;row < count;row++)
            {
                if(!filterParm.getBoolean("#MODIFY#",row))
                    continue;
                if(!checkModifyRow(FILTER,row))
                {
                    int idRow = findModifiedRow(filterParm.getInt("#ID#", row));
                    modifiedRemoveRow(idRow);
                    idRow = findPrimaryRow(filterParm.getInt("#ID#", row));
                    primaryParm.setData("#MODIFY#",idRow,false);
                    filterParm.setData("#MODIFY#",row,false);
                }
            }
            return;
        }
        int count = primaryParm.getCount();
        for(int row = 0;row < count;row++)
        {
            if(!primaryParm.getBoolean("#MODIFY#",row))
                continue;
            if(!checkModifyRow(PRIMARY,row))
            {
                int idRow = findModifiedRow(primaryParm.getInt("#ID#", row));
                modifiedRemoveRow(idRow);
                primaryParm.setData("#MODIFY#",row,false);
            }
        }
        return;

    }
    /**
     * �������Ƿ��޸�
     * @param buffer String
     * @param row int
     * @return boolean true �޸� false û���޸�
     */
    private boolean checkModifyRow(String buffer,int row)
    {
        int idRow = 0;
        TParm p = null;
        if(PRIMARY.equals(buffer))
        {
            idRow = findModifiedRow(primaryParm.getInt("#ID#", row));
            p = primaryParm;
        }
        else if(FILTER.equals(buffer))
        {
            idRow = findModifiedRow(filterParm.getInt("#ID#", row));
            p = filterParm;
        }else
            return false;
        int count = getColumns().length;
        boolean b = false;
        for(int i = 0;i < count;i++)
        {
            b = b || !equalsValue(p.getData(getColumns()[i], row),
                                  modifiedParm.getData(getColumns()[i], idRow));
        }
        return b;
    }
    /**
     * ����ֵ
     * @param row int
     * @param column int
     * @param value Object
     * @param buffer String PRIMARY or FILTER
     * @return int ���������������к�
     */
    public int setItem(int row,int column,Object value,String buffer)
    {
        return setItem(row,getColumns()[column],value,buffer);
    }
    /**
     * ����ֵ
     * @param row int
     * @param column String
     * @param value Object
     * @param buffer String PRIMARY or FILTER
     * @return int ���������������к�
     */
    public int setItem(int row,String column,Object value,String buffer)
    {
    	//System.out.println("------row---------"+row);
    	//System.out.println("------column---------"+column);
    	//System.out.println("------value---------"+value);
    	//System.out.println("------buffer---------"+buffer);    	
    	
        if(PRIMARY.equals(buffer))
        {
            setItemPrimary(row, column, value);
            return row;
        }
        if(!FILTER.equals(buffer))
            return -2;
        if(row >= filterParm.getCount())
            return -2;
        if(!existsColumn(column))
            return setOtherColumnValue(filterParm,row,column,value)?-1:-3;
        value = checkData(column,value);
        if(equalsValue(filterParm.getData(column,row),value))
            return -2;
        //������
        if(filterParm.getBoolean("#NEW#",row))
        {
            filterParm.setData(column,row,value);
            int idRow = findPrimaryRow(filterParm.getInt("#ID#",row));
            if(idRow == -1)
                return -1;
            primaryParm.setData(column,idRow,value);
            return idRow;
        }
        //û���޸���
        if(!filterParm.getBoolean("#MODIFY#",row))
        {
            TParm parm = filterParm.getRow(row);
            parm.removeData("#MODIFY#");
            parm.removeData("#NEW#");
            int modifyCount = modifiedParm.getCount();
            if(modifyCount < 0)
                modifyCount = 0;
            modifiedParm.setRowData(modifyCount,parm,-1);
            modifiedParm.setData("ACTION","COUNT",modifiedParm.getInt("ACTION","COUNT") + 1);
            filterParm.setData("#MODIFY#",row,true);
            filterParm.setData(column,row,value);
            int idRow = findPrimaryRow(filterParm.getInt("#ID#",row));
            if(idRow == -1)
                return -1;
            primaryParm.setData(column,idRow,value);
            primaryParm.setData("#MODIFY#",idRow,true);
            return idRow;
        }
        //�޸�
        filterParm.setData(column,row,value);
        int idRow = findPrimaryRow(filterParm.getInt("#ID#",row));
        if(idRow != -1)
            primaryParm.setData(column,idRow,value);

        //ȡ���޸�״̬
        if(!checkModifyRow(FILTER,row))
        {
            int idRow1 = findModifiedRow(filterParm.getInt("#ID#", row));
            modifiedRemoveRow(idRow1);
            filterParm.setData("#MODIFY#",row,false);
            if(idRow != -1)
                primaryParm.setData("#MODIFY#",idRow,false);
        }
        return idRow;

    }
    /**
     * �ж���ͬ
     * @param o1 Object
     * @param o2 Object
     * @return boolean
     */
    private boolean equalsValue(Object o1,Object o2)
    {
        if(o1 == null && o2 == null)
            return true;
        if(o1 != null && o2 != null)
            return o1.equals(o2);
        return false;
    }
    /**
     * ɾ��ȫ����
     * @return boolean
     */
    public boolean deleteRowAll()
    {
        for(int i = primaryParm.getCount() - 1;i >= 0;i --)
            if(!deleteRow(i))
                return false;
        return true;
    }
    /**
     * ɾ��һ�й��˻�����
     * @param row int
     */
    private void filterRemoveRow(int row)
    {
        filterParm.removeRow(row);
    }
    /**
     * ɾ��һ����������
     * @param row int
     */
    private void primaryRemoveRow(int row)
    {
        primaryParm.removeRow(row);
    }
    /**
     * ɾ��һ���޸Ļ�����
     * @param row int
     */
    private void modifiedRemoveRow(int row)
    {
        modifiedParm.removeRow(row);
    }
    /**
     * ����ɾ������
     * @param parm TParm
     */
    private void addDeleteRow(TParm parm)
    {
    	//System.out.println("=====del parm======"+parm);
        parm.removeData("#MODIFY#");
        int deleteCount = deletedParm.getCount();
        if(deleteCount < 0)
            deleteCount = 0;
        deletedParm.setRowData(deleteCount,parm,-1);
        deletedParm.setData("ACTION","COUNT",deletedParm.getInt("ACTION","COUNT") + 1);
    }
    /**
     * �����ڲ�IDɾ����������
     * @param id int
     */
    private void primaryRemoveID(int id)
    {
        int row = findPrimaryRow(id);
        if(row != -1)
            primaryRemoveRow(row);
    }
    /**
     * �����ڲ�IDɾ�����˻�����
     * @param id int
     */
    private void filterRemoveID(int id)
    {
        int row = findFilterRow(id);
        if(row != -1)
            filterRemoveRow(row);
    }
    /**
     * ɾ����������
     * @param row int �к�
     * @param buffer String PRIMARY or FILTER
     * @return boolean
     */
    public boolean deleteRow(int row,String buffer)
    {
    	//System.out.println("=======deleteRow0===========");
        if(row < 0)
            return false;
        if(PRIMARY.equals(buffer))
            return deleteRow(row);
        if(!FILTER.equals(buffer))
            return false;
        if(row < 0 || row >= filterParm.getCount())
            return false;
        int id = filterParm.getInt("#ID#",row);
        if(filterParm.getBoolean("#NEW#"))
        {
            filterRemoveRow(row);
            primaryRemoveID(id);
            return true;
        }
        if(filterParm.getBoolean("#MODIFY#",row))
        {
            int modifiedRow = findModifiedRow(id);
            addDeleteRow(modifiedParm.getRow(modifiedRow));
            modifiedRemoveRow(modifiedRow);
            filterRemoveRow(row);
            primaryRemoveID(id);
            return true;
        }
        addDeleteRow(filterParm.getRow(row));
        filterRemoveRow(row);
        primaryRemoveID(id);
        return true;
    }
    /**
     * ɾ����
     * @param row int �к�
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean deleteRow(int row)
    {
    	//System.out.println("=======deleteRow1===========");
        if(row < 0 || row >= primaryParm.getCount())
            return false;
        int id = primaryParm.getInt("#ID#",row);
        if(primaryParm.getBoolean("#NEW#",row))
        {
            primaryRemoveRow(row);
            filterRemoveID(id);
            return true;
        }
        if(primaryParm.getBoolean("#MODIFY#",row))
        {
            int modifiedRow = findModifiedRow(id);
            TParm parm = modifiedParm.getRow(modifiedRow);
            parm.setData("#NEW#",false);
            addDeleteRow(parm);
            modifiedRemoveRow(modifiedRow);
            primaryRemoveRow(row);
            filterRemoveID(id);
            return true;
        }
        addDeleteRow(primaryParm.getRow(row));
        primaryRemoveRow(row);
        filterRemoveID(id);
        return true;
    }
    /**
     * �õ�SQL���
     * @return String[]
     */
    public String[] getUpdateSQL()
    {
        Vector newData = new Vector();
        //ɾ��
        int count = deletedParm.getCount();
        for(int i = 0;i < count;i++)
        {
            if(!deletedParm.getBoolean("#ACTIVE#",i))
                continue;
            String sql = getDeleteSQL(i);
            if(sql.length() == 0)
                return null;
            newData.add(sql);
        }
        //�޸�
        if(isFilter)
            count = filterParm.getCount();
        else
            count = primaryParm.getCount();
        for(int i = 0;i < count;i ++)
        {
            if(isFilter)
            {
                if(!filterParm.getBoolean("#ACTIVE#",i))
                    continue;
                if (!filterParm.getBoolean("#MODIFY#", i))
                    continue;
            }
            else
            {
                if(!primaryParm.getBoolean("#ACTIVE#",i))
                    continue;
                if (!primaryParm.getBoolean("#MODIFY#", i))
                    continue;
            }
            String sql[] = getUpdateSQL(i);
            if(sql == null)
                return null;
            for(int j = 0;j < sql.length;j++)
                newData.add(sql[j]);
        }
        //����
        if(isFilter)
            count = filterParm.getCount();
        else
            count = primaryParm.getCount();
        for(int i = 0;i < count;i ++)
        {
            if(isFilter)
            {
                if(!filterParm.getBoolean("#ACTIVE#",i))
                    continue;
                if(!filterParm.getBoolean("#NEW#",i))
                    continue;
            }else
            {
                if(!primaryParm.getBoolean("#ACTIVE#",i))
                    continue;
                if (!primaryParm.getBoolean("#NEW#", i))
                    continue;
            }
            String sql = getInsertSQL(i);
            if(sql.length() == 0)
                return null;
            newData.add(sql);
        }
        return (String[])newData.toArray(new String[]{});
    }
    /**
     * ����
     * @return boolean
     */
    public boolean update()
    {
        //�õ�SQL���
        String data[] = getUpdateSQL();
        if(data == null)
            return false;
        TParm result = new TParm(getDBTool().update(getDatabaseName(),data));
        if(result.getErrCode() < 0)
        {
            setErrText(result.getErrText());
            //System.out.println(result.getErrText());
            //System.out.println(StringTool.getString(data));
            return false;
        }
        //����޸ļ�¼
        resetModify();
        return true;
    }
    /**
     * ����޸ļ�¼
     */
    public void resetModify()
    {
        int count = primaryParm.getCount();
        for(int i = 0;i < count;i++)
        {
            if(!primaryParm.getBoolean("#ACTIVE#",i))
                continue;
            primaryParm.setData("#MODIFY#",i,false);
            primaryParm.setData("#NEW#",i,false);
        }
        count = filterParm.getCount();
        for(int i = 0;i < count;i++)
        {
            if(!filterParm.getBoolean("#ACTIVE#",i))
                continue;
            filterParm.setData("#MODIFY#",i,false);
            filterParm.setData("#NEW#",i,false);
        }
        count = deletedParm.getCount();
        for(int i = count - 1;i >= 0;i--)
        {
            if (!deletedParm.getBoolean("#ACTIVE#", i))
                continue;
            deletedParm.removeRow(i);
        }
        count = modifiedParm.getCount();
        for(int i = count - 1;i >= 0;i--)
        {
            if (!modifiedParm.getBoolean("#ACTIVE#", i))
                continue;
            modifiedParm.removeRow(i);
        }
    }
    /**
     * �õ������SQL���
     * @param row int
     * @return String
     */
    private String getInsertSQL(int row)
    {
        if(getTableName() == null || getTableName().length() == 0)
        {
            setErrText("����Ϊ��");
            return "";
        }
        String columnString = getColumnString();
        //System.out.println("---columnString---"+columnString);
        if(columnString.length() == 0)
        {
            setErrText("����������");
            return "";
        }
        //add by lx 2013/04/07
        columnString=this.procColumnString(columnString);
        //
        StringBuffer value = new StringBuffer();
        for(int i = 0;i < getColumns().length;i++)
        {        	
        	
        	//add by lx 2013/04/07 ��һ��FLG���˵�
        	if(getColumns()[i].equals(KEY_WORD_ONE)){
        		//System.out.println("----���� FLG------"+getColumns()[i]);
        		continue;
        	}
        	//
            if(value.length() > 0)
                value.append(",");
            String v = null;
            if(isChangeValue())
                v = getChangeValue(getColumns()[i]);
            if(v == null)
            {
                String buffer = isFilter ? FILTER : PRIMARY;
                v = getUpdateValue(getTableColumnIndex(getColumns()[i]),getItemData(row,i,buffer));
            }
            
            value.append(v);
        }
        //System.out.println("-----value11111------"+value);
        return getSQLHead() + "INSERT INTO " + getTableName() +
                "(" + columnString + ")" +
                "VALUES(" + value + ")";
    }
    /**
     * �õ�ɾ����SQL���
     * @param row int
     * @return String
     */
    private String getDeleteSQL(int row)
    {
        if(getTableName() == null || getTableName().length() == 0)
        {
            setErrText("����Ϊ��");
            return "";
        }
        String columnString = getColumnString();
        if(columnString.length() == 0)
        {
            setErrText("����������");
            return "";
        }
        String value = getUpdateWhereValue(row,DELETE);
        if(value.length() == 0)
            return "";
        return getSQLHead() + "DELETE FROM " + getTableName() +
                " WHERE " + value;
    }
    private String getUpdateWhereValue(int row,String mode)
    {
        boolean b = false;
        StringBuffer value = new StringBuffer();
        int count = tableInfParm.getCount();
        for(int i = 0;i < count;i++)
        {
            String pk = tableInfParm.getValue("PK",i);
            if(pk == null||pk.length() == 0)
               continue;
            String columnName = tableInfParm.getValue("COLUMN_NAME",i);
            if(value.length() > 0)
                value.append(" AND ");
            value.append(columnName);
            value.append("=");
            if(findColumn(columnName) < 0)
            {
                setErrText("û�в�ѯ������" + columnName);
                return "";
            }
            String v = null;
            if(isChangeValue())
                v = getChangeValue(columnName);
            if(v == null)
            {
                Object v1 = getItemData(row, columnName, mode, true);
                if (v1 == null)
                {
                    setErrText("������������ֵ" + columnName);
                    return "";
                }
                v = getUpdateValue(i,v1);
            }
            value.append(v);
            b = true;
        }
        if(!b)
        {
            setErrText("û���ҵ�������!");
        }
        return value.toString();
    }
    private String[] getUpdateSQL(int row)
    {
        if(getTableName() == null || getTableName().length() == 0)
        {
            setErrText("����Ϊ��");
            return null;
        }
        String columnString = getColumnString();
        if(columnString.length() == 0)
        {
            setErrText("����������");
            return null;
        }
        //add by lx 2013/04/07
        columnString=this.procColumnString(columnString);
        //
        
        String buffer = isFilter?FILTER:PRIMARY;
        String whereValue = getUpdateWhereValue(row,buffer);
        if(whereValue.length() == 0)
            return null;
        if(getUpdateMode().equals(UPDATE_MODE))
        {
            StringBuffer value = new StringBuffer();
            for(int i = 0;i < getColumns().length;i++)
            {
            	//add by lx 2013/04/07 ��һ��FLG���˵�
            	if(getColumns()[i].equals(KEY_WORD_ONE)){
            		//System.out.println("----���� FLG------"+getColumns()[i]);
            		continue;
            	}
            	//
            	
                Object newV = getItemData(row,i,buffer);
                Object oldV = getItemData(row,i,buffer,true);
                if(equalsValue(newV,oldV))
                    continue;
                if(value.length() > 0)
                    value.append(",");
                value.append(getColumns()[i]);
                value.append("=");
                String v = null;
                if(isChangeValue())
                    v = getChangeValue(getColumns()[i]);
                if(v == null)
                    v = getUpdateValue(getTableColumnIndex(getColumns()[i]),newV);
                value.append(v);
            }
            if(value.length() == 0)
                return new String[]{};
            return new String[]{getSQLHead() + "UPDATE " + getTableName() +
                    " SET " + value +
                    " WHERE " + whereValue};
        }
        if(getUpdateMode().equals(DELETE_INSERT_MODE))
        {
            return new String[]{getSQLHead() +"DELETE FROM " + getTableName() +
                " WHERE " + whereValue,
                getInsertSQL(row)};
        }
        return null;
    }
    public Object checkData(String column,Object obj)
    {
    	//add by lx 2013/12/checkData13
    	 if(column.equals(KEY_WORD_ONE)){
    		 //System.out.println("---checkData----"+obj);
    		 return obj;
    	 }
    	//
        Vector v = (Vector)tableInfParm.getData("COLUMN_NAME");
        int index = v.indexOf(column);
        return checkData(index,obj);
    }
    public Object checkData(int column,Object obj)
    {
        if(obj == null)
            return null;
        String type = tableInfParm.getValue("DATA_TYPE",column);
        int scale = tableInfParm.getInt("DATA_SCALE",column);
        if("VARCHAR2".equals(type) || "CHAR".equals(type))
        {
            if(obj == null || obj instanceof String)
                return obj;
            return "" + obj;
        }
        if("NUMBER".equals(type))
        {
            if(scale > 0)
            {
                if(obj instanceof Double)
                    return obj;
                if(obj instanceof Integer)
                    return (double)(Integer)obj;
                if(obj instanceof String)
                    try{
                        return Double.parseDouble((String)obj);
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                        return 0;
                    }
            return 0.0;
            }
            if(obj instanceof Double)
                return obj;
            if(obj instanceof Integer)
                return obj;
            if(obj instanceof Long)
                return obj;
            if(obj instanceof String)
            {
                String s = (String) obj;
                if (s.indexOf(".") > 0)
                    s = s.substring(0,s.indexOf("."));
                    try
                    {
                        return Long.parseLong(s);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        return 0;
                    }
            }
            return 0;
        }
        if("DATE".equals(type))
        {
            if(obj instanceof Timestamp)
                return obj;
            if(obj instanceof String)
            {
                String date = (String)obj;
                switch(date.length())
                {
                case 10:
                    return StringTool.getTimestamp((String) obj, "yyyy/MM/dd");
                case 19:
                    return StringTool.getTimestamp((String) obj, "yyyy/MM/dd HH:mm:ss");
                case 14:
                    return StringTool.getTimestamp((String) obj, "yyyyMMddHHmmss");
                case 8:
                    return StringTool.getTimestamp((String) obj, "yyyyMMdd");
                }
            }
        }
        return obj;
    }
    /**
     * ������λ��
     * @param name String
     * @return int
     */
    public int findColumn(String name)
    {
        if(getColumns() == null)
            return -1;
        int count = getColumns().length;
        for(int i = 0;i < count;i++)
            if(getColumns()[i].equals(name))
                return i;
        return -1;
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeObject(primaryParm);
        s.writeObject(modifiedParm);
        s.writeObject(deletedParm);
        s.writeObject(filterParm);
        s.writeObject(tableInfParm);
        s.writeString(errText);
        s.writeString(databaseName);
        s.writeString(owner);
        s.writeString(tableName);
        s.writeString(where);
        s.writeString(orderBy);
        s.writeStrings(columns);
        s.writeStrings(columnType);
        s.writeBoolean(isFilter);
        s.writeString(updateMode);
        s.writeString(retrieveTime);
        s.writeString(filterString);
    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        primaryParm = (TParm)s.readObject();
        modifiedParm = (TParm)s.readObject();
        deletedParm = (TParm)s.readObject();
        filterParm = (TParm)s.readObject();
        tableInfParm = (TParm)s.readObject();
        errText = s.readString();
        databaseName = s.readString();
        owner = s.readString();
        tableName = s.readString();
        where = s.readString();
        orderBy = s.readString();
        columns = s.readStrings();
        columnType = s.readStrings();
        isFilter = s.readBoolean();
        updateMode = s.readString();
        retrieveTime = s.readString();
        filterString = s.readString();
    }
    /**
     * ������ݰ�
     * @return Map
     */
    public Map outData()
    {
        TParm parm = new TParm();
        parm.setData(PRIMARY,primaryParm.getData());
        parm.setData(MODIFY,modifiedParm.getData());
        parm.setData(DELETE,deletedParm.getData());
        parm.setData(FILTER,filterParm.getData());
        parm.setData(TABLE,tableInfParm.getData());
        parm.setData("errText",errText);
        parm.setData("databaseName",databaseName);
        parm.setData("owner",owner);
        parm.setData("tableName",tableName);
        parm.setData("where",where);
        parm.setData("orderBy",orderBy);
        parm.setData("columns",columns);
        parm.setData("columnType",columnType);
        parm.setData("isFilter",isFilter);
        parm.setData("updateMode",updateMode);
        parm.setData("retrieveTime",retrieveTime);
        parm.setData("filterString",filterString);
        return parm.getData();
    }
    /**
     * ������ݰ�
     * @param dwbuffer String ����������
     * @return byte[]
     */
    public byte[] outData(String dwbuffer)
    {
        return getBuffer(dwbuffer).outData();
    }
    /**
     * �������ݰ�
     * @param map Map
     * @return boolean
     */
    public boolean inData(Map map)
    {
        TParm parm = new TParm(map);
        primaryParm.setData((Map)parm.getData(PRIMARY));
        modifiedParm.setData((Map)parm.getData(MODIFY));
        deletedParm.setData((Map)parm.getData(DELETE));
        filterParm.setData((Map)parm.getData(FILTER));
        tableInfParm.setData((Map)parm.getData(TABLE));
        errText = (String)parm.getData("errText");
        databaseName = (String)parm.getData("databaseName");
        owner = (String)parm.getData("owner");
        tableName = (String)parm.getData("tableName");
        where = (String)parm.getData("where");
        orderBy = (String)parm.getData("orderBy");
        columns = (String[])parm.getData("columns");
        columnType = (String[])parm.getData("columnType");
        isFilter = parm.getBoolean("isFilter");
        updateMode = (String)parm.getData("updateMode");
        retrieveTime = (String)parm.getData("retrieveTime");
        filterString = (String)parm.getData("filterString");
        return true;
    }
    /**
     * �������ݰ�
     * @param data byte[]
     * @param dwbuffer String ����������
     * @return boolean
     */
    public boolean inData(byte[] data,String dwbuffer)
    {
        return getBuffer(dwbuffer).inData(data);
    }
    public static final boolean SAVE_FORMAT = false;
    /**
     * �����ļ�
     * @param filename String �ļ���
     * @return boolean
     */
    public boolean saveFile(String filename)
    {
        try
        {
            if(SAVE_FORMAT)
                FileTool.setDObject(filename,this);
            else
                FileTool.setObject(filename,outData());
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }


        return true;
    }
    /**
     * �����ļ�
     * @param filename String �ļ���
     * @param dwbuffer String ����������
     * @return boolean
     */
    public boolean saveFile(String filename,String dwbuffer)
    {
        try{
            FileTool.setByte(filename, outData(dwbuffer));
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
    /**
     * ��ȡ�ļ�
     * @param filename String �ļ���
     * @return boolean
     */
    public boolean loadFile(String filename)
    {
        try{
            if(SAVE_FORMAT)
                FileTool.getDObject(filename,this);
            else
                inData((Map)FileTool.getObject(filename));
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
    /**
     * ��ȡ�仯�ļ�
     * @param filename String
     * @return boolean
     */
    public boolean loadFileChoose(String filename)
    {
        try{
            byte data[] = FileTool.getByte(filename);
            if(data == null || data.length == 0)
                return false;
            return inDataChoose(data);
        }catch(Exception e)
        {
            return false;
        }
    }
    /**
     * ���ر仯����
     * @param data byte[]
     * @return boolean
     */
    public boolean inDataChoose(byte[] data)
    {
        TParm parm = new TParm();
        if(!parm.inData(data))
            return false;
        int count = parm.getCount();
        for(int i = 0;i < count;i++)
        {
            String action = parm.getValue("ACTION",i);
            int row = getChooseDataPXRow(parm,i);
            if("INSERT".equals(action))
            {
                if(row != -1)
                    continue;
                row = insertRow();
                for (int j = 0; j < getColumns().length; j++)
                    setItem(row, j, parm.getData(getColumns()[j], i));
                continue;
            }
            if("UPDATE".equals(action))
            {
                if(row == -1)
                    continue;
                for (int j = 0; j < getColumns().length; j++)
                    setItem(row, j, parm.getData(getColumns()[j], i));
                continue;
            }
            if("DELETE".equals(action))
            {
                if(row == -1)
                    continue;
                deleteRow(row);
                continue;
            }
        }
        return true;
    }
    /**
     * ���Ҹı����ݵ�λ��
     * @param parm TParm
     * @param row int
     * @return int
     */
    private int getChooseDataPXRow(TParm parm,int row)
    {
        String pkColumnName[] = getPKColumns();
        if(pkColumnName.length == 0)
            return -1;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < pkColumnName.length;i++)
        {
            if(sb.length() > 0)
                sb.append("|");
            String s = parm.getValue(pkColumnName[i] + "_OLD",row);
            if(s.length() == 0)
                s = parm.getValue(pkColumnName[i],row);
            sb.append(s);
        }
        return getPKRow(sb.toString());
    }
    /**
     * ��������ֵλ��
     * @param value String
     * @return int
     */
    public int getPKRow(String value)
    {
        if(value == null||value.length() == 0)
            return -1;
        int count = rowCount();
        for(int i = 0;i < count;i ++)
            if(value.equals(getPKValue(i)))
                return i;
        return -1;
    }
    /**
     * �õ�����ֵ
     * @param row int �к�
     * @return String
     */
    public String getPKValue(int row)
    {
        String pkColumnName[] = getPKColumns();
        if(pkColumnName.length == 0)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < pkColumnName.length;i++)
        {
            if(sb.length() > 0)
                sb.append("|");
            sb.append(primaryParm.getValue(pkColumnName[i],row));
        }
        return sb.toString();
    }
    /**
     * �õ�������
     * @return String[]
     */
    public String[] getPKColumns()
    {
        int count = tableInfParm.getCount();
        ArrayList list = new ArrayList();
        for(int i = 0;i < count;i++)
        {
            String pk = tableInfParm.getValue("PK",i);
            if(pk == null||pk.length() == 0)
               continue;
           list.add(tableInfParm.getValue("COLUMN_NAME",i));
        }
        return (String[])list.toArray(new String[]{});
    }
    /**
     * �õ�������
     * @param column int
     * @return String
     */
    public String getColumnComment(int column)
    {
        return getColumnComment(getColumns()[column]);
    }
    /**
     * �õ�������
     * @param column String
     * @return String
     */
    public String getColumnComment(String column)
    {
        int index = getTableColumnIndex(column);
        if(index == -1)
            return column;
        String comment = tableInfParm.getValue("COMMENTS", index);
        if(comment == null||comment.length() == 0)
            return column;
        return comment;
    }
    /**
     * �õ�����λ��
     * @param column String
     * @return int
     */
    public int getTableColumnIndex(String column)
    {
    	//System.out.println("----column-----"+column);
        if(column == null || column.length() == 0)
            return -1;
        
        int count = tableInfParm.getCount();
        for(int i = 0;i < count;i++)
            if(column.equals(tableInfParm.getValue("COLUMN_NAME",i)))
                return i;
        return -1;
    }
    /**
     * �õ�Vector����
     * @return Vector
     */
    public Vector getVector()
    {
        return getVector("");
    }
    /**
     * �õ�Vector����
     * @param names String
     * @return Vector
     */
    public Vector getVector(String names)
    {
        return getVector(names,0);
    }
    /**
     * �õ���Vector����
     * @param row int
     * @return Vector
     */
    public Vector getRowVector(int row)
    {
        return getRowVector(row,"");
    }
    /**
     * �õ���Vector����
     * @param row int
     * @param names String
     * @return Vector
     */
    public Vector getRowVector(int row, String names)
    {
        return getRowVector(row,names,PRIMARY);
    }
    /**
     * �õ���Vector����
     * @param row int
     * @param names String
     * @param dbbuffer String
     * @return Vector
     */
    public Vector getRowVector(int row, String names,String dbbuffer)
    {
        if(names == null|| names.length() == 0)
            names = getColumnString(";");
        TParm parm = getBuffer(dbbuffer);
        String nameArray[] = StringTool.parseLine(names, ";");
        if (nameArray.length == 0)
            return new Vector();
        int otherID[] = getOtherColumn(nameArray);
        return getRowVector(row,nameArray,otherID,parm);
    }
    /**
     * �õ���Vector����
     * @param row int
     * @param names String[]
     * @param otherID int[]
     * @param parm TParm
     * @return Vector
     */
    public Vector getRowVector(int row,String names[],int otherID[],TParm parm)
    {
        Vector data = new Vector();
        if (names.length == 0)
            return data;
        for (int i = 0; i < names.length; i++)
        {
            if(inIntArray(otherID,i))
            {
                data.add(getOtherColumnValue(parm,row,names[i]));
                continue;
            }
            data.add(parm.getData(names[i], row));
        }
        return data;
    }
    /**
     * �õ�Vector����
     * @param names String
     * @param size int �������
     * @return Vector
     */
    public Vector getVector(String names,int size)
    {
        return getVector(names,size,PRIMARY);
    }
    /**
     * �õ�Vector����
     * @param names String
     * @param size int
     * @param dbbuffer String
     * @return Vector
     */
    public Vector getVector(String names,int size,String dbbuffer)
    {
        if(names == null|| names.length() == 0)
            names = getColumnString(";");
        names = checkLanguage(names);
        TParm parm = getBuffer(dbbuffer);
        Vector data = new Vector();
        String nameArray[] = StringTool.parseLine(names, ";");
        if (nameArray.length == 0)
            return data;
        int count = parm.getCount();
        if(size > 0 && count > size)
            count = size;
        int otherID[] = getOtherColumn(nameArray);
        for (int row = 0; row < count; row++)
            data.add(getRowVector(row,nameArray,otherID,parm));
        return data;
    }
    /**
     * �õ���������
     * @param row int
     * @param names String
     * @return Vector
     */
    public Vector getVectorRow(int row,String names)
    {
        return getVectorRow(row,names,PRIMARY);
    }
    /**
     * ���ֶ��ո���
     * @param names String
     * @return String
     */
    private String checkLanguage(String names)
    {
        if(!"en".equals(getLanguage()))
           return names;
        if(getLanguageMap() == null || getLanguageMap().length() == 0)
            return names;
        String list[] = StringTool.parseLine(getLanguageMap(),";");
        for(int i = 0;i < list.length;i++)
        {
            String s[] = StringTool.parseLine(list[i], "|");
            if (s.length == 2)
                names = StringTool.replaceAll(names, s[0], s[1]);
        }
        return names;
    }
    /**
     * �õ���������
     * @param row int
     * @param names String
     * @param dbbuffer String
     * @return Vector
     */
    public Vector getVectorRow(int row,String names,String dbbuffer)
    {
        if(names == null|| names.length() == 0)
            names = getColumnString(";");
        names = checkLanguage(names);
        TParm parm = getBuffer(dbbuffer);
        Vector data = new Vector();
        String nameArray[] = StringTool.parseLine(names, ";");
        if (nameArray.length == 0)
            return data;
        int otherID[] = getOtherColumn(nameArray);
        return getRowVector(row,nameArray,otherID,parm);
    }
    /**
     * ������������
     * @param data int[]
     * @param x int
     * @return boolean
     */
    public boolean inIntArray(int data[],int x)
    {
        for(int i = 0;i < data.length;i++)
            if(data[i] == x)
                return true;
        return false;
    }
    /**
     * �õ���������λ��
     * @param names String[]
     * @return int[]
     */
    public int[] getOtherColumn(String names[])
    {
        ArrayList list = new ArrayList();
        int count = names.length;
        for(int i = 0;i < count;i++)
        {
            if(!existsColumn(names[i]))
                list.add(i);
        }
        return getIntArray(list);
    }
    /**
     * ���Ƿ����
     * @param column String
     * @return boolean
     */
    public boolean existsColumn(String column)
    {
        if(column == null || column.length() == 0 || getColumns() == null)
            return false;
        int count = getColumns().length;
        for(int i = 0;i < count;i++)
        {
            if(column.equals(getColumns()[i]))
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
        return getDBTool().getDBTime();
    }
    /**
     * ���ø���ʱ��
     * @param retrieveTime String
     */
    public void setRetrieveTime(String retrieveTime)
    {
        this.retrieveTime = retrieveTime;
    }
    /**
     * �õ�����ʱ��
     * @return String
     */
    public String getRetrieveTime()
    {
        return retrieveTime;
    }
    /**
     * ��ʾ�ڴ�(������)
     */
    public void showDebug()
    {
        System.out.println("PRIMARY " + getBuffer(PRIMARY));
        System.out.println("MODIFY  " + getBuffer(MODIFY));
        System.out.println("DELETE  " + getBuffer(DELETE));
        System.out.println("FILTER   " + getBuffer(FILTER));
        System.out.println("TABLE   " + getBuffer(TABLE));
        System.out.println("RetrieveTime" + getRetrieveTime());
        System.out.println("FILTER  " + getFilter());
    }
    /**
     * ���ù�������
     * @param filterString String
     */
    public void setFilter(String filterString)
    {
        this.filterString = filterString;
    }
    /**
     * �õ���������
     * @return String
     */
    public String getFilter()
    {
        return filterString;
    }
    /**
     * ���й���
     * @return boolean
     */
    public boolean filter()
    {
        return filter(0);
    }
    /**
     * ���й���
     * @param size int ���ߴ�
     * @return boolean
     */
    public boolean filter(int size)
    {
        if(isFilter && (filterString == null || filterString.length() == 0))
        {
            primaryParm = filterParm;
            filterParm = new TParm();
            isFilter = false;
            return true;
        }
        if(!isFilter)
        {
            if (filterString == null || filterString.length() == 0)
                return true;
            filterParm = primaryParm;
        }
        primaryParm = new TParm();
        int count = filterParm.getCount();
        for(int i = 0;i < count;i ++)
        {
            TParm parm = filterParm.getRow(i);
            if(!filterCheck(getFilter(),i,parm))
                continue;
            int c = primaryParm.getCount();
            if(c < 0)
                c = 0;
            primaryParm.setRowData(c,parm,-1);
            int cc = primaryParm.getInt("ACTION","COUNT") + 1;
            if(size > 0 && cc == size)
                break;
            primaryParm.setData("ACTION","COUNT",cc);
        }
        isFilter = true;
        return true;
    }
    /**
     * ����
     * @param filterObject Object
     * @param methodName String
     * @return boolean
     */
    public boolean filterObject(Object filterObject,String methodName)
    {
        return filterObject(filterObject,methodName,0);
    }
    /**
     * ����
     * @param filterObject Object
     * @param methodName String
     * @param size int
     * @return boolean
     */
    public boolean filterObject(Object filterObject,String methodName,int size)
    {
        if(filterObject == null)
            return false;
        Method method = RunClass.findMethod(filterObject,methodName,new Object[]{new TParm(),0});
        if(method == null)
        {
            System.out.println("û���ҵ�����" + methodName);
            return false;
        }
        if(!isFilter)
            filterParm = primaryParm;
        primaryParm = new TParm();
        int count = filterParm.getCount();
        for(int i = 0;i < count;i ++)
        {
            if(!(Boolean)RunClass.runMethod(filterObject,method,filterParm,i))
                continue;
            int c = primaryParm.getCount();
            if(c < 0)
                c = 0;
            primaryParm.setRowData(c,filterParm,i);
            c++;
            if(size > 0 && c == size)
                break;
            primaryParm.setData("ACTION","COUNT",c);
        }
        isFilter = true;
        return true;
    }
    /**
     * ���˲�ѯ
     * @param src String
     * @param row int
     * @param parm TParm
     * @return boolean
     */
    public boolean filterCheck(String src,int row,TParm parm)
    {
        Script script = new Script();
        script.setParm(parm);
        script.setData(src);
        return script.exec();
    }
    /**
     * �õ�һ�е�TParm����
     * @param row int �к�
     * @return TParm
     */
    public TParm getRowParm(int row)
    {
        return getRowParm(row,PRIMARY);
    }
    /**
     * �õ�һ�е�TParm����
     * @param row int �к�
     * @param buffer String ��������
     * @return TParm
     */
    public TParm getRowParm(int row,String buffer)
    {
        TParm parm = getBuffer(buffer).getRow(row);
        parm.removeData("#ID#");
        parm.removeData("#MODIFY#");
        parm.removeData("#NEW#");
        parm.removeData("#ACTIVE#");

        return parm;
    }
    /**
     * �õ��޸ĵ��к�
     * @return int[]
     */
    public int[] getModifiedRows()
    {
        return getModifiedRows(PRIMARY);
    }
    /**
     * �õ��޸ĵ��к�
     * @param buffer String ������ PRIMARY OR FILTER
     * @return int[]
     */
    public int[] getModifiedRows(String buffer)
    {
        TParm parm = this.getBuffer(buffer);
        int count = parm.getCount();
        ArrayList list = new ArrayList();
        for(int i = 0;i < count;i++)
        {
            if(!parm.getBoolean("#ACTIVE#",i))
                continue;
            if(parm.getBoolean("#MODIFY#",i) ||
               parm.getBoolean("#NEW#",i))
                list.add(new Integer(i));
        }
        return getIntArray(list);
    }
    /**
     * �õ��޸ĵ��к�
     * @param buffer String
     * @return int[]
     */
    public int[] getOnlyModifiedRows(String buffer)
    {
        TParm parm = this.getBuffer(buffer);
        int count = parm.getCount();
        ArrayList list = new ArrayList();
        for(int i = 0;i < count;i++)
        {
            if(!parm.getBoolean("#ACTIVE#",i))
                continue;
            if(parm.getBoolean("#MODIFY#",i))
                list.add(new Integer(i));
        }
        return getIntArray(list);
    }
    /**
     * �õ���������
     * @param list ArrayList
     * @return int[]
     */
    public int[] getIntArray(ArrayList list)
    {
        int[] value = new int[list.size()];
        for(int i = 0;i < value.length;i ++)
            value[i] = (Integer)list.get(i);
        return value;
    }
    /**
     * �õ��������к�
     * @return int[]
     */
    public int[] getNewRows()
    {
        return getModifiedRows(PRIMARY);
    }
    /**
     * �õ��������к�
     * @param buffer String ������ PRIMARY OR FILTER
     * @return int[]
     */
    public int[] getNewRows(String buffer)
    {
        TParm parm = this.getBuffer(buffer);
        int count = parm.getCount();
        ArrayList list = new ArrayList();
        for(int i = 0;i < count;i++)
        {
            if(!parm.getBoolean("#ACTIVE#",i))
                continue;
            if(parm.getBoolean("#NEW#",i))
                list.add(new Integer(i));
        }
        return getIntArray(list);
    }
    /**
     * �õ�ɾ��������
     * @return int
     */
    public int getDeleteCount()
    {
        return deletedParm.getCount();
    }
    /**
     * �õ���������
     * @return int
     */
    public int getLogDateCount()
    {
        Timestamp now = getDBTime();
        Timestamp load = StringTool.getTimestamp(getRetrieveTime(),"yyyyMMddHHmmss");
        return StringTool.getDateDiffer(now,load);
    }
    /**
     * ������������
     * @param sort String "AAA ASC,BBB DESC"
     */
    public void setSort(String sort)
    {
        this.sort = sort;
    }
    /**
     * �õ���������
     * @return String
     */
    public String getSort()
    {
        return sort;
    }
    /**
     * ����
     * @return boolean
     */
    public boolean sort()
    {
        if(getSort() == null || getSort().length() == 0)
            return false;
        String sorts[] = StringTool.parseLine(getSort(),",");
        String oldName = "";
        for(int i = 0;i < sorts.length;i ++)
        {
            String columnName = sorts[i].trim().toUpperCase();
            boolean f = true;
            if(columnName.endsWith(" DESC"))
            {
                f = false;
                columnName = columnName.substring(0,columnName.length() - 5).trim();
            }else if(columnName.endsWith(" ASC"))
                columnName = columnName.substring(0,columnName.length() - 4).trim();
            sort(oldName,columnName,f);
            oldName = columnName;
        }
        return true;
    }
    /**
     * ������
     * @param oldName String
     * @param columnName String
     * @param f boolean
     */
    private void sort(String oldName,String columnName,boolean f)
    {
        if(oldName == null || oldName.length() == 0)
        {
            quickSort(0, primaryParm.getCount() - 1, columnName, f);
            return;
        }
        String os = "";
        int one = 0;
        for(int i = 0;i < primaryParm.getCount();i ++)
        {
            String s = primaryParm.getValue(oldName,i);
            if(StringTool.compareTo(os,s) == 0)
                continue;
            if(i - one > 1)
                quickSort(one, i - 1, columnName, f);
            one = i;
            os = s;
        }
        int i = primaryParm.getCount() - 1;
        if(one < i)
            quickSort(one, i, columnName, f);
    }
    /**
     * �Ƚ�
     * @param s1 String
     * @param s2 String
     * @param f boolean
     * @return boolean
     */
    boolean compareTo(String s1,String s2,boolean f)
    {
        if(f)
            return StringTool.compareTo(s1,s2) < 0;
        return StringTool.compareTo(s1,s2) > 0;
    }
    /**
     * �����㷨
     * @param lo0 int
     * @param hi0 int
     * @param columnName String
     * @param f boolean
     */
    private void quickSort(int lo0, int hi0, String columnName,boolean f)
    {
        int lo = lo0;
        int hi = hi0;
        String mid;

        if (hi0 > lo0)
        {
            mid = primaryParm.getValue(columnName, (lo0 + hi0) / 2);
            while (lo <= hi)
            {
                while ((lo < hi0) && compareTo(primaryParm.getValue(columnName, lo),mid,f))
                    ++lo;

                while ((hi > lo0) && compareTo(primaryParm.getValue(columnName,hi),mid,!f))
                    --hi;

                if (lo <= hi)
                {
                    swap(lo, hi);
                    ++lo;
                    --hi;
                }
            }
            if (lo0 < hi)
                quickSort(lo0, hi,columnName,f);
            if (lo < hi0)
                quickSort(lo, hi0,columnName,f);
        }
    }
    /**
     * ����
     * @param i1 int
     * @param i2 int
     */
    private void swap(int i1,int i2)
    {
        String s[] = getColumns();
        for(int i = 0;i < s.length;i ++)
            swap(s[i],i1,i2);
        swap("#ID#",i1,i2);
        swap("#NEW#",i1,i2);
        swap("#MODIFY#",i1,i2);
        swap("#ACTIVE#",i1,i2);
    }
    /**
     * ����
     * @param columnName String
     * @param i1 int
     * @param i2 int
     */
    private void swap(String columnName,int i1,int i2)
    {
        Object obj = primaryParm.getData(columnName,i1);
        primaryParm.setData(columnName,i1,primaryParm.getData(columnName,i2));
        primaryParm.setData(columnName,i2,obj);
    }
    /**
     * ��ѯ
     * @param s String �﷨
     * @return int
     */
    public int find(String s)
    {
        return find(s,PRIMARY);
    }
    /**
     * ��ѯ
     * @param s String �﷨
     * @param buffer String ������
     * @return int
     */
    public int find(String s,String buffer)
    {
        TParm parm = getBuffer(buffer);
        int count = parm.getCount();
        for(int i = 0;i < count;i ++)
        {
            TParm row = parm.getRow(i);
            if(!filterCheck(s,i,row))
                continue;
            return i;
        }
        return -1;
    }
    /**
     * �Ƿ����
     * @param s String �﷨
     * @param buffer String ������
     * @return boolean true ���� false ������
     */
    public boolean exist(String s,String buffer)
    {
        return find(s,buffer) != -1;
    }
    /**
     * �Ƿ����
     * @param s String �﷨
     * @return boolean true ���� false ������
     */
    public boolean exist(String s)
    {
        if(isFilter)
            return exist(s,FILTER);
        return exist(s,PRIMARY);
    }
    /**
     * ��ǰ�Ƿ��ǹ���״̬
     * @return boolean true �� false ����
     */
    public boolean isFilter()
    {
        return isFilter;
    }
    /**
     * �Ƿ��޸�
     * @return boolean
     */
    public boolean isModified()
    {
        TParm parm = primaryParm;
        int count = parm.getCount();
        for(int i = 0;i < count;i++)
        {
            if(!parm.getBoolean("#ACTIVE#",i))
                continue;
            if(parm.getBoolean("#MODIFY#",i))
                return true;
            if(parm.getBoolean("#NEW#",i))
                return true;
        }
        parm = filterParm;
        count = parm.getCount();
        for(int i = 0;i < count;i++)
        {
            if(!parm.getBoolean("#ACTIVE#",i))
                continue;
            if(parm.getBoolean("#MODIFY#",i))
                return true;
            if(parm.getBoolean("#NEW#",i))
                return true;
        }
        parm = deletedParm;
        count = parm.getCount();
        for(int i = 0;i < count;i++)
        {
            if(!parm.getBoolean("#ACTIVE#",i))
                continue;
            return true;
        }
        return false;
    }
    /**
     * �������ñ��
     * @param row int
     * @param dwbuffer String
     * @return boolean
     */
    public boolean setActive(int row,String dwbuffer)
    {
        return setActive(row,true,dwbuffer);
    }
    /**
     * �������ñ��
     * @param row int
     * @return boolean
     */
    public boolean setActive(int row)
    {
        return setActive(row,true);
    }
    /**
     * �������ñ��
     * @param row int
     * @param active boolean
     * @return boolean
     */
    public boolean setActive(int row,boolean active)
    {
        return setActive(row,active,PRIMARY);
    }
    /**
     * �������ñ��
     * @param row int
     * @param active boolean
     * @param dwbuffer String
     * @return boolean
     */
    public boolean setActive(int row,boolean active,String dwbuffer)
    {
        if(row < 0)
            return false;
        if(PRIMARY.equals(dwbuffer))
        {
            if(primaryParm.getBoolean("#ACTIVE#",row) == active)
                return true;
            primaryParm.setData("#ACTIVE#",row,active);
            int id = primaryParm.getInt("#ID#",row);
            if(primaryParm.getBoolean("#MODIFY#",row))
                modifiedParm.setData("#ACTIVE#",findModifiedRow(id),active);
            if(isFilter())
                filterParm.setData("#ACTIVE#",findFilterRow(id),active);
            return true;
        }
        if(MODIFY.equals(dwbuffer))
        {
            if(modifiedParm.getBoolean("#ACTIVE#",row) == active)
                return true;
            modifiedParm.setData("#ACTIVE#",row,active);
            int id = modifiedParm.getInt("#ID#",row);
            int primaryRow = findPrimaryRow(id);
            if(primaryRow != -1)
                primaryParm.setData("#ACTIVE#",primaryRow,active);
            if(isFilter())
                filterParm.setData("#ACTIVE#",findFilterRow(id),active);
            return true;
        }
        if(FILTER.equals(dwbuffer))
        {
            if(!isFilter())
                return false;
            if(filterParm.getBoolean("#ACTIVE#",row) == active)
                return true;
            filterParm.setData("#ACTIVE#",row,active);
            int id = filterParm.getInt("#ID#",row);
            if(filterParm.getBoolean("#MODIFY#",row))
                modifiedParm.setData("#ACTIVE#",findModifiedRow(id),active);
            int primaryRow = findPrimaryRow(id);
            if(primaryRow != -1)
                primaryParm.setData("#ACTIVE#",primaryRow,active);
        }
        if(DELETE.equals(dwbuffer))
        {
            if(deletedParm.getBoolean("#ACTIVE#",row) == active)
                return true;
            deletedParm.setData("#ACTIVE#",row,active);
        }
        return false;
    }
    /**
     * �Ƿ�����
     * @param row int
     * @return boolean
     */
    public boolean isActive(int row)
    {
        return isActive(row,PRIMARY);
    }
    /**
     * �Ƿ�����
     * @param row int
     * @param dwbuffer String
     * @return boolean
     */
    public boolean isActive(int row,String dwbuffer)
    {
        if(row < 0)
            return false;
        if(PRIMARY.equals(dwbuffer))
            return primaryParm.getBoolean("#ACTIVE#",row);
        if(MODIFY.equals(dwbuffer))
            return modifiedParm.getBoolean("#ACTIVE#",row);
        if(FILTER.equals(dwbuffer))
            return filterParm.getBoolean("#ACTIVE#",row);
        if(FILTER.equals(dwbuffer))
            return filterParm.getBoolean("#ACTIVE#",row);
        return false;
    }
    /**
     * �õ�����������
     * @param parm TParm
     * @param row int
     * @param column String
     * @return Object
     */
    public Object getOtherColumnValue(TParm parm,int row,String column)
    {
        return "";
    }
    /**
     * ��������������
     * @param parm TParm
     * @param row int
     * @param column String
     * @param value Object
     * @return boolean
     */
    public boolean setOtherColumnValue(TParm parm,int row,String column,Object value)
    {
        actionSetOtherItem(column,value);
        return true;
    }
    /**
     * ����������ʾ��
     * @param row int
     */
    public void addOtherShowRowList(int row)
    {
        if(otherData == null)
            otherData = new HashMap();
        ArrayList list = (ArrayList)otherData.get("OtherShowRowList");
        if(list == null)
        {
            list = new ArrayList();
            otherData.put("OtherShowRowList",list);
        }
        list.add(row);
    }
    /**
     * ɾ��������ʾ��
     * @param row int
     */
    public void removeOtherShowRowList(int row)
    {
        if(otherData == null)
            return;
        ArrayList list = (ArrayList)otherData.get("OtherShowRowList");
        if(list == null)
            return;
        list.remove(row);
        if(list.size() == 0)
            otherData.remove("OtherShowRowList");
    }
    /**
     * ɾ��ȫ��������ʾ��
     */
    public void removeOtherShowRowList()
    {
        if(otherData == null)
            return;
        ArrayList list = (ArrayList)otherData.get("OtherShowRowList");
        if(list != null)
            otherData.remove("OtherShowRowList");
    }
    /**
     * �õ�������ʾ��
     * @return int[]
     */
    public int[] getOtherShowRowList()
    {
        if(otherData == null)
            return null;
        ArrayList list = (ArrayList)otherData.get("OtherShowRowList");
        if(list == null)
            return null;
        return getIntArray(list);
    }
    /**
     * ��������ʾSQL
     * @param name String
     * @param sql String
     */
    public void addColumnSql(String name,String sql)
    {
        if(name == null)
            return;
        columnSql.put(name.toUpperCase(),sql);
    }
    /**
     * �õ�����ʾSQL
     * @param name String
     * @return String
     */
    public String getColumnSql(String name)
    {
        String sql = (String)columnSql.get(name);
        if(sql == null || sql.length() == 0)
            return name;
        return sql;
    }
    /**
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject()
    {
      return baseEvent;
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param methodName String ������
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object, String methodName)
    {
        getBaseEventObject().add(eventName,object,methodName);
    }
    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object, String methodName)
    {
      getBaseEventObject().remove(eventName,object,methodName);
    }
    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName,Object[] parms,String[] parmType)
    {
        return getBaseEventObject().callEvent(eventName,parms,parmType);
    }
    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName)
    {
      return callEvent(eventName,new Object[]{},new String[]{});
    }
    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName,Object parm)
    {
      return callEvent(eventName,new Object[]{parm},new String[]{"java.lang.Object"});
    }
    /**
     * ��������
     * @param language String
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * �������ֶ���
     * @param languageMap String zh|en
     */
    public void setLanguageMap(String languageMap)
    {
        this.languageMap = languageMap;
    }
    /**
     * �õ����ֶ���
     * @return String zh|en
     */
    public String getLanguageMap()
    {
        return languageMap;
    }
    /**
     * �Ƿ���ڲ�������
     * @return boolean
     */
    public boolean isChangeValue()
    {
        if(changeValueMap == null)
            return false;
        if(changeValueMap.size() == 0)
            return false;
        return true;
    }
    /**
     * ��Ӳ�������
     * @param columnName String ����
     * @param value String ֵ
     */
    public void addChangeValue(String columnName,String value)
    {
        if(changeValueMap == null)
            changeValueMap = new HashMap();
        changeValueMap.put(columnName,value);
    }
    /**
     * �õ�����������ֵ
     * @param columnName String
     * @return String
     */
    public String getChangeValue(String columnName)
    {
        if(changeValueMap == null)
            return null;
        return (String)changeValueMap.get(columnName);
    }
    /**
     * ɾ��������������
     * @param columnName String
     */
    public void removeChangeValue(String columnName)
    {
        if(changeValueMap == null)
            return;
        changeValueMap.remove(columnName);
    }
    /**
     * ��ղ�������
     */
    public void clearChangeValue()
    {
        changeValueMap = null;
    }
    /**
     * ����SQL���ǰ׺
     * @param sqlHead String
     */
    public void setSQLHead(String sqlHead)
    {
        this.sqlHead = sqlHead;
    }
    /**
     * �õ�SQL���ǰ׺
     * @return String
     */
    public String getSQLHead()
    {
        if(sqlHead == null || sqlHead.length() == 0)
            return "";
        return sqlHead + ":";
    }
    public static void main(String args[])
    {
        System.out.println(TDataStore.helpEvent());
        /*
        TDebug.initClient();
        TDataStore dataStore = new TDataStore();
        dataStore.setSQL("select ID,NAME from ADM_AUTOBILL");
        dataStore.addColumnSql("NAME","(select NAME from ABC WHERE ADM_AUTOBILL.ID=ABC.ID) AS NAME");
        System.out.println(dataStore.retrieve());
        dataStore.deleteRowAll();

        System.out.println(StringTool.getString(dataStore.getUpdateSQL()));

        dataStore.update();
        System.out.println(dataStore.getErrText());
*/
    }
    
    public void initPrimaryParm(){
    	 int count = filterParm.getCount();
         if(count < 0)
             count = 0;
         for(int i = 0;i < count;i++)
         {
        	//System.out.println("--------filterParm ago----------"+primaryParm);
        	 filterParm.setData("#MODIFY#", i, false);
        	 //filterParm.setData("#NEW#", i, false);
             //System.out.println("--------filterParm late----------"+primaryParm);
         }
    }
    //$$ add by lx 2013/04/07  
    /**
     * �Թؼ��ֽ��д���    
     * @param columnString
     * @return
     */
    private String procColumnString(String columnString){   
    	if (isExistKeyword(columnString)) {
			columnString = columnString.substring((KEY_WORD_ONE + ",").length(),
					columnString.length());
			//System.out.println("-----columnString11111-----" + columnString);
		}
    	return columnString;
    }
    
    /**
     * �Ƿ���ڹؽ��� FLG
     * @param columnString
     * @return
     */
    private boolean isExistKeyword(String columnString){
    	if (columnString.startsWith(KEY_WORD_ONE + ",")) {
    		return true;
    	}
    	return false;
    }
    
    
}
