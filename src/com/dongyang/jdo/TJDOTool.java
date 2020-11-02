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
     * ����ģ������
     */
    private String moduleName;
    /**
     * ����ģ�Ͷ���
     */
    private TModule moduleObject;
    /**
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
    public TJDOTool()
    {
        if(TIOM_AppServer.SOCKET != null)
            setClientlink(true);
    }
    /**
     * ��������ģ������
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * �õ�����ģ������
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
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
     * ��������ģ�Ͷ���
     * @param moduleObject TModule
     */
    public void setModuleObject(TModule moduleObject)
    {
        this.moduleObject = moduleObject;
    }
    /**
     * �õ�����ģ�Ͷ���
     * @return TModule
     */
    public TModule getModuleObject()
    {
        return moduleObject;
    }
    /**
     * ��ʼ��
     * @return boolean true �ɹ� false ʧ��
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
     * ��װһ���������
     * @param errCode int ������
     * @param errText String ������Ϣ
     * @return TParm
     */
    public TParm err(int errCode,String errText)
    {
        return err(errCode,errText,"");
    }
    /**
     * ��װһ���������
     * @param errCode int ������
     * @param errText String ������Ϣ
     * @param errName String ��������
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
     * �洢�������
     * @param methodName String ��������
     * @param parm TParm ����
     * @return TParm �ز�
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
     * �洢�������
     * @param methodName String ��������
     * @return TParm �ز�
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
     * ��ѯ���
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
            return err(-1,"��ֹ��ǰ�˵������񷽷�!");
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
     * ��ѯ���
     * @param methodName String ��������
     * @param parm TParm ����
     * @return TParm �ز�
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
     * ��ѯ���
     * @param methodName String ��������
     * @return TParm �ز�
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
     * ����
     * @param methodName String ��������
     * @param parm TParm ����
     * @return TParm �ز�
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
     * ����
     * @param methodName String ��������
     * @return TParm �ز�
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
     * ����
     * @param methodName String ��������
     * @param parm TParm
     * @param connection TConnection
     * @return TParm
     */
    public TParm update(String methodName,TParm parm,TConnection connection)
    {
        if(isClientlink())
            return err(-1,"��ֹ��ǰ�˵������񷽷�!");
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.update(methodName,parm,connection);
    }
    /**
     * ����
     * @param methodName String ��������
     * @param connection TConnection
     * @return TParm �ز�
     */
    public TParm update(String methodName,TConnection connection)
    {
        if(isClientlink())
            return err(-1,"��ֹ��ǰ�˵������񷽷�!");
        TModule module = getModuleObject();
        if(module == null)
            return err(-1,"module object is not init!");
        return module.update(methodName,connection);
    }
    /**
     * �õ�����
     * @param databaseName String ���ݳ���
     * @return TConnection
     */
    public TConnection getConnection(String databaseName)
    {
        if(isClientlink())
        {
            err("-1  ��ֹ��ǰ�˵������񷽷�!");
            return null;
        }
        return getModuleObject().getConnection(databaseName);
    }
    /**
     * �õ�������
     * @return TConnection
     */
    public TConnection getConnection()
    {
        if(isClientlink())
        {
            err("-1  ��ֹ��ǰ�˵������񷽷�!");
            return null;
        }
        return getModuleObject().getConnection();
    }
    /**
     * �õ������ַ�����ֵ
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
     * �õ������ַ�����ֵ(Timestamp)
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
     * �õ������ַ�����ֵ(int)
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
     * �õ������ַ�����ֵ(double)
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
     * �õ������ַ�����ֵ(boolean)
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
     * �����ִ�
     * @param text String Դ�ִ�
     * @return String ���ܺ��ִ�
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
     * �����ִ�
     * @param text String Դ�ִ�
     * @return String ���ܺ��ִ�
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
     * 
     * @param srgs
     */
    public static void main(String srgs[]){
    	TJDOTool  t=new TJDOTool();
    	System.out.println(t.decrypt("ffceffcdffceffcb"));
    }
}
