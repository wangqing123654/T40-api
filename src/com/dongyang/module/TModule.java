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
     * SQL����
     */
    private Map SQLMap;
    /**
     * ������
     */
    public TModule()
    {
        SQLMap = new HashMap();
        setTag("Module");
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
        if(tag != null && tag.length() > 0)
            getLog().setUserInf(" [" + tag + "]");
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
     * ������Ŀ
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
    }
    /**
     * �����������
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
     * �����������
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
     * ����SQL���
     * @param value String
     */
    protected void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //���ID
        String cid = values[0];
        //�������
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
     * ����SQL����
     * @param name String ����
     * @param sql TSQL SQL����
     */
    public void addSQL(String name,TSQL sql)
    {
        SQLMap.put(name,sql);
    }
    /**
     * �õ�SQL����
     * @param name String ����
     * @return TSQL
     */
    public TSQL getSQL(String name)
    {
        return (TSQL)SQLMap.get(name);
    }
    /**
     * ɾ��SQL����
     * @param name String
     * @return TSQL
     */
    public TSQL removeSQL(String name)
    {
        return (TSQL)SQLMap.remove(name);
    }
    /**
     * �õ�SQL�������
     * @return int
     */
    public int getSQLCount()
    {
        return SQLMap.size();
    }
    /**
     * ɾ��ȫ����SQL����
     */
    public void removeSQLAll()
    {
        SQLMap = new HashMap();
    }
    /**
     * ִ�д洢�������
     * @param tag String sql��ǩ
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
     * ִ�д洢�������
     * @param tag String sql��ǩ
     * @param parm TParm ����
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
     * ִ�в�ѯ���
     * @param tag String sql��ǩ
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
     * ִ�в�ѯ���
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
     * ִ�в�ѯ���
     * @param tag String sql��ǩ
     * @param parm TParm ����
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
     * ִ�и������
     * @param tag String sql��ǩ
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
     * ִ�и������
     * @param tag String sql��ǩ
     * @param parm TParm ����
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
     * ִ�и������
     * @param tag String sql��ǩ
     * @param connection TConnection ����
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
     * ִ�и������
     * @param tag String sql��ǩ
     * @param parm TParm ����
     * @param connection TConnection ����
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
     * �õ�Module
     * @param configName String �����ļ���
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
