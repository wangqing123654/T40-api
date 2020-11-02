package com.dongyang.db;

import com.dongyang.config.TConfigParm;
import com.dongyang.util.Log;
import com.dongyang.control.TControl;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;
import com.dongyang.data.TParm;

public class TSQL {
    /**
     * 日志系统
     */
    private Log log;
    /**
     * 标签
     */
    private String tag;
    /**
     * 加载标签
     */
    private String loadtag;
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * SQL语句
     */
    private String SQL;
    /**
     * 出参
     */
    private String outType;
    /**
     * 调试
     */
    private boolean debug;
    /**
     * 动态where列表
     */
    private TWhereList whereList;
    /**
     * 扩展动态where列表
     */
    private TEWhereList eWhereList;
    /**
     * 构造器
     */
    public TSQL()
    {
        setLog(new Log());
        whereList = new TWhereList();
        eWhereList = new TEWhereList();
    }
    /**
     * 得到标签
     * @return String
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
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
     * 设置加载标签
     * @param loadtag String
     */
    public void setLoadTag(String loadtag) {
        this.loadtag = loadtag;
    }

    /**
     * 得到加载标签
     * @return String
     */
    public String getLoadTag() {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }
    /**
     * 设置SQL语句
     * @param SQL String
     */
    public void setSQL(String SQL)
    {
        this.SQL = SQL;
    }
    /**
     * 得到SQL语句
     * @return String
     */
    public String getSQL()
    {
        return SQL;
    }
    /**
     * 设置出参
     * @param outType String
     */
    public void setOutType(String outType)
    {
        this.outType = outType;
    }
    /**
     * 得到出参
     * @return String
     */
    public String getOutType()
    {
        return outType;
    }
    /**
     * 设置调试开关
     * @param debug boolean
     */
    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }
    /**
     * 是否调试
     * @return boolean
     */
    public boolean isDebug()
    {
        return debug;
    }
    /**
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null)
            control.callMessage("setComponent", this);
    }
    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }
    /**
     * 设置配置参数对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }

    /**
     * 得到配置参数对象
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
        //保存配置对象
        setConfigParm(configParm);
        //加载全部属性
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
        //加载控制类
        if (getControl() != null)
            getControl().init();
    }
    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    protected boolean filterInit(String message) {
        return true;
    }
    /**
     * 加载顺序
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig";
    }
    /**
     * 消息处理
     * @param message String 消息处理
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }
    /**
     * 处理消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //处理基本消息
        Object value = null;
        if ((value = onBaseMessage(message, parm)) != null)
            return value;
        //处理子集的消息
        //if ((value = onTagBaseMessage(message, parm)) != null)
        //    return value;
        //处理控制类的消息
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //处理终止消息
        //if ((value = onStopMessage(message, parm)) != null)
        //    return value;
        //消息上传
        return null;
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message, "=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
    }
    /**
     * 转换为String
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
     * 设置项目
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
     * 加载成员组件
     * @param value String
     */
    protected void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //组件ID
        String cid = values[0];
        if(cid.startsWith("#"))
        {
            //列表
            String item = getConfigParm().getConfig().getString(getConfigParm().
                    getSystemGroup(), getTag() + "." + cid + ".item");
            String items[] = StringTool.parseLine(item, ";");
            TWhereList list = new TWhereList();
            String sql = getConfigParm().getConfig().getString(getConfigParm().
                        getSystemGroup(), getTag() + "." + cid);
            list.setSql(sql);
            for(int i = 0;i < items.length;i ++)
            {
                //Where组件类型
                String where = getConfigParm().getConfig().getString(getConfigParm().
                        getSystemGroup(), getTag() + "." + cid + "." + items[i]);
                list.addWhere(items[i],where);
            }
            eWhereList.addWhereList(cid, list);
        }
        //Where组件类型
        String where = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), getTag() + "." + cid);
        whereList.addWhere(cid,where);
    }
    /**
     * 得到SQL语句
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
     * 删除<#ID>
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
