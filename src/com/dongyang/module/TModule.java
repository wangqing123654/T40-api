package com.dongyang.module;

import com.dongyang.module.base.TModuleBase;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.StringTool;
import com.dongyang.control.TControl;
import com.dongyang.util.RunClass;
import com.dongyang.db.TSQL;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.util.Log;
import com.dongyang.data.TParm;
import com.dongyang.db.TConnection;
import com.dongyang.Service.Server;

public class TModule extends TModuleBase{
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
     * SQL集合
     */
    private Map SQLMap;
    /**
     * 构造器
     */
    public TModule()
    {
        SQLMap = new HashMap();
        setTag("Module");
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
        if(tag != null && tag.length() > 0)
            getLog().setUserInf(" [" + tag + "]");
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
     * 设置项目
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
    }
    /**
     * 设置组件配置
     * @param value String
     */
    public void setControlConfig(String value) {
        if (getControl() == null) {
            err("control is null");
            return;
        }
        getControl().setConfigParm(getConfigParm().newConfig(value));
    }

    /**
     * 设置组件类名
     * @param value String
     */
    public void setControlClassName(String value) {
        Object obj = getConfigParm().loadObject(value);
        if (obj == null) {
            err("Class loadObject err className=" + value);
            return;
        }
        if (!(obj instanceof TControl)) {
            err("Class loadObject type err className=" + value +
                " is not TControl");
            return;
        }
        TControl control = (TControl) obj;
        setControl(control);
    }
    /**
     * 创建SQL语句
     * @param value String
     */
    protected void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //组件ID
        String cid = values[0];
        //组件类型
        String type = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), cid + ".type");
        if (type.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof TSQL))
            return;
        TSQL sql = (TSQL) obj;
        sql.setTag(cid);
        sql.init(getConfigParm());
        addSQL(cid,sql);
    }
    /**
     * 增加SQL对象
     * @param name String 名称
     * @param sql TSQL SQL对象
     */
    public void addSQL(String name,TSQL sql)
    {
        SQLMap.put(name,sql);
    }
    /**
     * 得到SQL对象
     * @param name String 名称
     * @return TSQL
     */
    public TSQL getSQL(String name)
    {
        return (TSQL)SQLMap.get(name);
    }
    /**
     * 删除SQL对象
     * @param name String
     * @return TSQL
     */
    public TSQL removeSQL(String name)
    {
        return (TSQL)SQLMap.remove(name);
    }
    /**
     * 得到SQL对象个数
     * @return int
     */
    public int getSQLCount()
    {
        return SQLMap.size();
    }
    /**
     * 删除全部的SQL对象
     */
    public void removeSQLAll()
    {
        SQLMap = new HashMap();
    }
    /**
     * 执行存储过程语句
     * @param tag String sql标签
     * @return TParm
     */
    public TParm call(String tag)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQL();
        String outType = tSQL.getOutType();
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        return executeCall(sql,outType);
    }
    /**
     * 执行存储过程语句
     * @param tag String sql标签
     * @param parm TParm 参数
     * @return TParm
     */
    public TParm call(String tag,TParm parm)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQL();
        String outType = tSQL.getOutType();
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("    " + parm);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        return executeCall(sql,outType,parm);
    }
    /**
     * 执行查询语句
     * @param tag String sql标签
     * @return TParm
     */
    public TParm query(String tag)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQL();
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        return executeQuery(sql);
    }
    /**
     * 执行查询语句
     * @param tag String
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm query(String tag,TParm parm,TConnection connection)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQLParm(parm);
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("    " + parm);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        TParm p = executeQuery(sql,parm,connection);
        return p;
    }

  /**
     * 执行查询语句
     * @param tag String sql标签
     * @param parm TParm 参数
     * @return TParm
     */
    public TParm query(String tag,TParm parm)
    {
        //parm.setData("SYSTEM","EXE_STATE","query 2");
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQLParm(parm);
        //parm.setData("SYSTEM","EXE_STATE","query 2 sql "+sql);
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("    " + parm);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        TParm p = executeQuery(sql,parm);
        //parm.setData("SYSTEM","EXE_STATE","query 2 "+p.toString());
        return p;
    }


       public TParm query(String tag,TParm parm,String dbPoolName){
           //parm.setData("SYSTEM","EXE_STATE","query 2");
           TSQL tSQL = getSQL(tag);
           if (tSQL == null)
               return err( -1, "no find TSQL Object from tag " + tag);
           String sql = tSQL.getSQLParm(parm);
           //System.out.println("==========SQL query============"+sql);
           //parm.setData("SYSTEM","EXE_STATE","query 2 sql "+sql);
           if (tSQL.isDebug()) {
               System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
               System.out.println("    " + sql);
               System.out.println("    " + parm);
               System.out.println("TSQL:" + tSQL.getTag() + "------------END");
           }
           TParm p = executeQuery(dbPoolName, sql, parm);
           //parm.setData("SYSTEM","EXE_STATE","query 2 "+p.toString());
           return p;
      }


    /**
     * 执行更新语句
     * @param tag String sql标签
     * @return TParm
     */
    public TParm update(String tag)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQL();
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        return executeUpdate(sql);
    }
    /**
     * 执行更新语句
     * @param tag String sql标签
     * @param parm TParm 参数
     * @return TParm
     */
    public TParm update(String tag,TParm parm)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQLParm(parm);
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("    " + parm);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        return executeUpdate(sql,parm);
    }
    /**
     * 执行更新语句
     * @param tag String sql标签
     * @param connection TConnection 连接
     * @return TParm
     */
    public TParm update(String tag,TConnection connection)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQL();
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        return executeUpdate(sql,connection);
    }
    /**
     * 执行更新语句
     * @param tag String sql标签
     * @param parm TParm 参数
     * @param connection TConnection 连接
     * @return TParm
     */
    public TParm update(String tag,TParm parm,TConnection connection)
    {
        TSQL tSQL = getSQL(tag);
        if(tSQL == null)
            return err(-1,"no find TSQL Object from tag " + tag);
        String sql = tSQL.getSQLParm(parm);
        if(tSQL.isDebug())
        {
            System.out.println("TSQL:" + tSQL.getTag() + "------------Begin");
            System.out.println("    " + sql);
            System.out.println("    " + parm);
            System.out.println("TSQL:" + tSQL.getTag() + "------------END");
        }
        return executeUpdate(sql,parm,connection);
    }
    public static void reset()
    {
        moduleMap = new HashMap();
    }
    static Map moduleMap = new HashMap();
    /**
     * 得到Module
     * @param configName String 配置文件名
     * @return TModule
     */
    public static TModule getModule(String configName)
    {
        if(configName == null||configName.length() == 0)
            return null;
        TModule module = (TModule)moduleMap.get(configName);
        if(module == null)
        {
            module = new TModule();
            module.init(Server.getConfigParm().newConfig(Server.getConfigDir() +
                    "module\\" + configName));
            moduleMap.put(configName,module);
        }
        return module;
    }
    public static void main(String args[])
    {
        //Log.DEBUG = true;
        TModule module = TModule.getModule("SYSDictionaryModule.x");

        //module.init(parm);
        TParm data = new TParm();
        data.setData("GROUP_ID","ROOT");
        data.setData("ID","GROUP");
        System.out.println(module.query("getName",data));
        //System.out.println(module.query("getGroupList"));
        //System.out.println(module.query("SQL1"));
        //System.out.println(module.update("SQL2"));
        //TParm data = new TParm();
        //data.setData("CLIENT_IP","172.20.154.13");
        //System.out.println(module.query("SQL3",data));
        //data.setData("CLIENT_IP","101");
        //System.out.println(module.update("SQL4",data));

    }
}
