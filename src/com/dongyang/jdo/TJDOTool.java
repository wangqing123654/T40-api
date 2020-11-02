package com.dongyang.jdo;

import com.dongyang.data.TParm;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.module.TModule;
import com.dongyang.util.Log;
import com.dongyang.manager.TCM_Transform;
import java.sql.Timestamp;
import com.dongyang.db.TConnection;

public class TJDOTool {
    /**
     * 数据模型名称
     */
    private String moduleName;
    /**
     * 数据模型对象
     */
    private TModule moduleObject;
    /**
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
    public TJDOTool()
    {
        if(TIOM_AppServer.SOCKET != null)
            setClientlink(true);
    }
    /**
     * 设置数据模型名称
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * 得到数据模型名称
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
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
     * 设置数据模型对象
     * @param moduleObject TModule
     */
    public void setModuleObject(TModule moduleObject)
    {
        this.moduleObject = moduleObject;
    }
    /**
     * 得到数据模型对象
     * @return TModule
     */
    public TModule getModuleObject()
    {
        return moduleObject;
    }
    /**
     * 初始化
     * @return boolean true 成功 false 失败
     */
    public boolean onInit()
    {
        log = new Log();
        if(getModuleName() == null||getModuleName().length() == 0)
            return false;
        if(!isClientlink())
        {
            TModule moduel = TModule.getModule(getModuleName());
            if(moduel == null)
                return false;
            setModuleObject(moduel);
        }else
        {
            if(TIOM_AppServer.SOCKET == null)
                return false;
        }
        return true;
    }
    /**
     * 组装一个错误对象
     * @param errCode int 错误编号
     * @param errText String 错误信息
     * @return TParm
     */
    public TParm err(int errCode,String errText)
    {
        return err(errCode,errText,"");
    }
    /**
     * 组装一个错误对象
     * @param errCode int 错误编号
     * @param errText String 错误信息
     * @param errName String 错误名称
     * @return TParm
     */
    public TParm err(int errCode,String errText,String errName)
    {
        TParm result = new TParm();
        result.setErr(errCode,errText,errName);
        err(errCode + " " + errText + " EN:" + errName);
        return result;
    }
    /**
     * 存储过程语句
     * @param methodName String 方法名称
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public TParm call(String methodName,TParm parm)
    {
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return TIOM_AppServer.call(getModuleName(), methodName, parm);
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.call(methodName,parm);
    }
    /**
     * 存储过程语句
     * @param methodName String 方法名称
     * @return TParm 回参
     */
    public TParm call(String methodName)
    {
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return TIOM_AppServer.call(getModuleName(), methodName);
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.call(methodName);
    }
    /**
     * 查询语句
     * @param methodName String
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm query(String methodName,TParm parm,TConnection connection)
    {
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return err(-1,"禁止在前端调用事务方法!");
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.query(methodName,parm,connection);
    }

    public TParm query(String methodName,TParm parm,String dbPoolName){
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return TIOM_AppServer.query(dbPoolName,getModuleName(), methodName, parm);
        TModule module = getModuleObject();
       if(module == null)
           return err(-1,"module object is not init!");
       return module.query(methodName,parm,dbPoolName);

    }
    /**
     * 查询语句
     * @param methodName String 方法名称
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public TParm query(String methodName,TParm parm)
    {
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return TIOM_AppServer.query(getModuleName(), methodName, parm);
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.query(methodName,parm);
    }
    /**
     * 查询语句
     * @param methodName String 方法名称
     * @return TParm 回参
     */
    public TParm query(String methodName)
    {
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return TIOM_AppServer.query(getModuleName(), methodName);
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.query(methodName);
    }
    /**
     * 更新
     * @param methodName String 方法名称
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public TParm update(String methodName,TParm parm)
    {
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return TIOM_AppServer.update(getModuleName(), methodName, parm);
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.update(methodName,parm);
    }
    /**
     * 更新
     * @param methodName String 方法名称
     * @return TParm 回参
     */
    public TParm update(String methodName)
    {
        if(getModuleName() == null|| getModuleName().length() == 0)
            return err(-1,"getModuleName is not init!");
        if(isClientlink())
            return TIOM_AppServer.update(getModuleName(), methodName);
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.update(methodName);
    }
    /**
     * 更新
     * @param methodName String 方法名称
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm update(String methodName,TParm parm,TConnection connection)
    {
        if(isClientlink())
            return err(-1,"禁止在前端调用事务方法!");
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.update(methodName,parm,connection);
    }
    /**
     * 更新
     * @param methodName String 方法名称
     * @param connection TConnection
     * @return TParm 回参
     */
    public TParm update(String methodName,TConnection connection)
    {
        if(isClientlink())
            return err(-1,"禁止在前端调用事务方法!");
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.update(methodName,connection);
    }
    /**
     * 得到连接
     * @param databaseName String 数据池名
     * @return TConnection
     */
    public TConnection getConnection(String databaseName)
    {
        if(isClientlink())
        {
            err("-1  禁止在前端调用事务方法!");
            return null;
        }
        return getModuleObject().getConnection(databaseName);
    }
    /**
     * 得到主连接
     * @return TConnection
     */
    public TConnection getConnection()
    {
        if(isClientlink())
        {
            err("-1  禁止在前端调用事务方法!");
            return null;
        }
        return getModuleObject().getConnection();
    }
    /**
     * 得到返回字符串单值
     * @param result TParm
     * @param name String
     * @return String
     */
    public String getResultString(TParm result,String name)
    {
        if(result.getErrCode() != 0)
        {
            err(result.getErrCode() + " " + result.getErrText() + " in " + getModuleName());
            return "";
        }
        if(result.getCount(name) == 0)
            return "";
        return result.getValue(name,0);
    }
    /**
     * 得到返回字符串单值(Timestamp)
     * @param result TParm
     * @param name String
     * @return Timestamp
     */
    public Timestamp getResultTimestamp(TParm result,String name)
    {
        if(result.getErrCode() != 0)
        {
            err(result.getErrCode() + " " + result.getErrText() + " in " + getModuleName());
            return null;
        }
        if(result.getCount(name) == 0)
            return null;
        return (Timestamp)result.getData(name,0);
    }
    /**
     * 得到返回字符串单值(int)
     * @param result TParm
     * @param name String
     * @return int
     */
    public int getResultInt(TParm result,String name)
    {
        if(result.getErrCode() != 0)
        {
            err(result.getErrCode() + " " + result.getErrText() + " in " + getModuleName());
            return -1;
        }
        if(result.getCount(name) == 0)
            return -1;
        return TCM_Transform.getInt(result.getData(name,0));
    }
    /**
     * 得到返回字符串单值(double)
     * @param result TParm
     * @param name String
     * @return double
     */
    public double getResultDouble(TParm result,String name)
    {
        if(result.getErrCode() != 0)
        {
            err(result.getErrCode() + " " + result.getErrText() + " in " + getModuleName());
            return -1;
        }
        if(result.getCount(name) == 0)
            return -1;
        return TCM_Transform.getDouble(result.getData(name,0));
    }
    /**
     * 得到返回字符串单值(boolean)
     * @param result TParm
     * @param name String
     * @return boolean
     */
    public boolean getResultBoolean(TParm result,String name)
    {
        if(result.getErrCode() != 0)
        {
            err(result.getErrCode() + " " + result.getErrText() + " in " + getModuleName());
            return false;
        }
        if(result.getCount(name) == 0)
            return false;
        return TCM_Transform.getBoolean(result.getData(name,0));
    }
    /**
     * 加密字串
     * @param text String 源字串
     * @return String 加密后字串
     */
    public String encrypt(String text)
    {
        String av_str = "";
        try
        {
            byte aa[] = text.getBytes("UTF-16BE");
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < aa.length; i++)
            {
                aa[i] = (byte)(~aa[i]);
                sb.append(Integer.toHexString(aa[i]).substring(6));
            }

            av_str = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return av_str;
    }
    /**
     * 解密字串
     * @param text String 源字串
     * @return String 解密后字串
     */
    public String decrypt(String text)
    {
        String vb_str = "";
        try
        {
            byte bb[] = new byte[text.length() / 2];
            for(int i = 0; i < text.length(); i += 2)
            {
                bb[i / 2] = (byte)(Integer.parseInt(text.substring(i, i + 2), 16) + -256);
                bb[i / 2] = (byte)(~bb[i / 2]);
            }

            vb_str = new String(bb, "UTF-16BE");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return vb_str;
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
     * 
     * @param srgs
     */
    public static void main(String srgs[]){
    	TJDOTool  t=new TJDOTool();
    	System.out.println(t.decrypt("ffceffcdffceffcb"));
    }
}
