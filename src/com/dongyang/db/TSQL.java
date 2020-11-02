package com.dongyang.db;

import com.dongyang.config.TConfigParm;
import com.dongyang.util.Log;
import com.dongyang.control.TControl;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;
import com.dongyang.data.TParm;

public class TSQL {
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * ��ǩ
     */
    private String tag;
    /**
     * ���ر�ǩ
     */
    private String loadtag;
    /**
     * ������
     */
    private TControl control;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * SQL���
     */
    private String SQL;
    /**
     * ����
     */
    private String outType;
    /**
     * ����
     */
    private boolean debug;
    /**
     * ��̬where�б�
     */
    private TWhereList whereList;
    /**
     * ��չ��̬where�б�
     */
    private TEWhereList eWhereList;
    /**
     * ������
     */
    public TSQL()
    {
        setLog(new Log());
        whereList = new TWhereList();
        eWhereList = new TEWhereList();
    }
    /**
     * �õ���ǩ
     * @return String
     */
    public String getTag() {
        return tag;
    }

    /**
     * ���ñ�ǩ
     * @param tag String
     */
    public void setTag(String tag) {
        this.tag = tag;
        String text = "";
        if(tag != null && tag.length() > 0)
            text += tag;
        if(text.length() > 0)
            text = " [" + text + "]";
        getLog().setUserInf(text);
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
     * ���ü��ر�ǩ
     * @param loadtag String
     */
    public void setLoadTag(String loadtag) {
        this.loadtag = loadtag;
    }

    /**
     * �õ����ر�ǩ
     * @return String
     */
    public String getLoadTag() {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }
    /**
     * ����SQL���
     * @param SQL String
     */
    public void setSQL(String SQL)
    {
        this.SQL = SQL;
    }
    /**
     * �õ�SQL���
     * @return String
     */
    public String getSQL()
    {
        return SQL;
    }
    /**
     * ���ó���
     * @param outType String
     */
    public void setOutType(String outType)
    {
        this.outType = outType;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getOutType()
    {
        return outType;
    }
    /**
     * ���õ��Կ���
     * @param debug boolean
     */
    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }
    /**
     * �Ƿ����
     * @return boolean
     */
    public boolean isDebug()
    {
        return debug;
    }
    /**
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null)
            control.callMessage("setComponent", this);
    }
    /**
     * �õ����������
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }
    /**
     * �������ò�������
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }

    /**
     * �õ����ò�������
     * @return TConfigParm
     */
    public TConfigParm getConfigParm() {
        return configParm;
    }
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm) {
        if (configParm == null)
            return;
        //�������ö���
        setConfigParm(configParm);
        //����ȫ������
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
        //���ؿ�����
        if (getControl() != null)
            getControl().init();
    }
    /**
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    protected boolean filterInit(String message) {
        return true;
    }
    /**
     * ����˳��
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig";
    }
    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }
    /**
     * ������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //���������Ϣ
        Object value = null;
        if ((value = onBaseMessage(message, parm)) != null)
            return value;
        //�����Ӽ�����Ϣ
        //if ((value = onTagBaseMessage(message, parm)) != null)
        //    return value;
        //������������Ϣ
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //������ֹ��Ϣ
        //if ((value = onStopMessage(message, parm)) != null)
        //    return value;
        //��Ϣ�ϴ�
        return null;
    }
    /**
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message, "=");
        //����������������
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * ����������������
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
    }
    /**
     * ת��ΪString
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TSQL{ SQL=");
        sb.append(getSQL());
        sb.append("}");
        return sb.toString();
    }
    /**
     * ������Ŀ
     * @param message String
     * @return Object
     */
    public Object setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
        return "OK";
    }
    /**
     * ���س�Ա���
     * @param value String
     */
    protected void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //���ID
        String cid = values[0];
        if(cid.startsWith("#"))
        {
            //�б�
            String item = getConfigParm().getConfig().getString(getConfigParm().
                    getSystemGroup(), getTag() + "." + cid + ".item");
            String items[] = StringTool.parseLine(item, ";");
            TWhereList list = new TWhereList();
            String sql = getConfigParm().getConfig().getString(getConfigParm().
                        getSystemGroup(), getTag() + "." + cid);
            list.setSql(sql);
            for(int i = 0;i < items.length;i ++)
            {
                //Where�������
                String where = getConfigParm().getConfig().getString(getConfigParm().
                        getSystemGroup(), getTag() + "." + cid + "." + items[i]);
                list.addWhere(items[i],where);
            }
            eWhereList.addWhereList(cid, list);
        }
        //Where�������
        String where = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), getTag() + "." + cid);
        whereList.addWhere(cid,where);
    }
    /**
     * �õ�SQL���
     * @param parm TParm
     * @return String
     */
    public String getSQLParm(TParm parm)
    {
        String sql = getSQL();
        if(parm == null)
            return sql;
        int count = whereList.getSize();
        if(count == 0)
            return sql;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < count;i ++)
        {
            String tag = whereList.getTag(i);
            if(tag == null||tag.length() == 0)
                continue;
            if(parm.existData(tag))
            {
                if(sb.length() > 0)
                    sb.append(" AND ");
                sb.append(whereList.getWhere(i));
            }
        }
        count = eWhereList.getSize();
        if(count > 0)
        {
            for(int i = 0;i < count;i ++)
            {
                TWhereList list = eWhereList.getTWhereList(i);
                if(list == null)
                    continue;
                if(list.existTag(parm))
                {
                    String esql = list.getSql();
                    int ecount = list.getSize();
                    for(int j = 0;j < ecount;j ++)
                    {
                        String tag = list.getTag(j);
                        if(tag == null||tag.length() == 0)
                            continue;
                        if(parm.existData(tag))
                        {
                            int index = esql.indexOf("<#" + tag + ">");
                            if(index < 0)
                                continue;
                            String s1 = esql.substring(0,index).trim();
                            String s2 = esql.substring(index + tag.length() + 3,esql.length());
                            if(s1.toUpperCase().endsWith("WHERE"))
                                esql = s1 + " " + list.getWhere(j) + " " + s2;
                            else
                                esql = s1 + " AND " + list.getWhere(j) + " " + s2;
                        }
                    }
                    if(sb.length() > 0)
                        sb.append(" AND ");
                    sb.append(deleteESQL(esql));
                }
            }
        }
        if(sb.length() == 0)
            return sql;
        int index = sql.toUpperCase().indexOf(" ORDER BY ");
        String orderBy = "";
        if(index > 0)
        {
            orderBy = sql.substring(index,sql.length());
            sql = sql.substring(0,index);
        }
        index = sql.toUpperCase().indexOf(" GROUP BY ");
        String groupBy = "";
        if(index > 0)
        {
            groupBy = sql.substring(index,sql.length());
            sql = sql.substring(0,index);
        }
        if(existSQL(sql,"WHERE"))
            sql += " AND " + sb.toString();
        else
            sql += " WHERE " + sb.toString();
        if(groupBy.length() > 0)
            sql += " " +  groupBy;
        if(orderBy.length() > 0)
            sql += " " +  orderBy;
        return sql;
    }
    public boolean existSQL(String sql,String parm)
    {
        String s[] = StringTool.parseLine(sql, ' ', "[]{}()");
        for(int i = 0;i < s.length;i++)
            if(s[i].trim().equalsIgnoreCase(parm.trim()))
                return true;
        return false;
    }
    /**
     * ɾ��<#ID>
     * @param sql String
     * @return String
     */
    private String deleteESQL(String sql)
    {
        if(sql == null)
            return sql;
        int index = sql.indexOf("<#");
        while(index > 0)
        {
            String s1 = sql.substring(0,index);
            String s2 = sql.substring(index + 2,sql.length());
            index = s2.indexOf(">");
            s2 = s2.substring(index + 1,s2.length());
            if(s1.trim().toUpperCase().endsWith("WHERE") && s2.trim().toUpperCase().startsWith("AND"))
            {
                index = s2.toUpperCase().indexOf("AND");
                s2 = s2.substring(index + 3,s2.length());
            }
            sql = s1 + s2;
            index = sql.indexOf("<#");
        }
        return sql;
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
