package com.dongyang.manager;

import java.io.File;
import java.io.FileInputStream;

import com.dongyang.util.Log;
import com.dongyang.data.TSocket;
import com.dongyang.data.TParm;
import com.dongyang.util.StringTool;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;
import javax.swing.ImageIcon;
import com.dongyang.util.FileTool;

public class TIOM_AppServer extends TIOM_Expend{
    public static TSocket SOCKET;
    /**
     * ִ��Class
     * @param className String
     * @param methodName String
     * @param parameters Object[]
     * @return Object
     */
    public static Object executeClass(String className,String methodName,Object parameters[])
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeClass(SOCKET,className,methodName,parameters);
    }
    /**
     * ִ��Class
     * @param socket TSocket
     * @param className String
     * @param methodName String
     * @param parameters Object[]
     * @return Object
     */
    public static Object executeClass(TSocket socket,String className,String methodName,Object parameters[])
    {
        TParm parm = new TParm();
        parm.setData("SYSTEM","ACTION",0x5);
        parm.setData("SYSTEM","CLASS_NAME",className);
        parm.setData("SYSTEM","METHOD_NAME",methodName);
        parm.setData("SYSTEM","PARAMETERS",parameters);
        //
        //System.out.println("=======CLASS_NAME========="+className);
        //System.out.println("=======METHOD_NAME========="+methodName);
        //System.out.println("======tiom appserver parm======="+parm);

        TParm result = socket.doAction(parm);
        if(result.getErrCode() < 0)
        {
            err(result.getErrCode() + " " + result.getErrText());
            return null;
        }
        return result.getData("VALUE");
    }
    /**
     * ����Action
     * @param className String ����
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm executeAction(String className,String methodName,TParm parm)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeAction(SOCKET,className,methodName,parm);
    }
    /**
     * ����Action
     * @param socket TSocket
     * @param className String ����
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm executeAction(TSocket socket,String className,String methodName,TParm parm)
    {
        parm.setData("SYSTEM","ACTION",0x1);
        parm.setData("SYSTEM","ACTION_CLASS_NAME",className);
        parm.setData("SYSTEM","ACTION_METHOD_NAME",methodName);
        TParm result = socket.doAction(parm);
        return result;
    }
    /**
     * ���¼���Action
     * @return boolean true �ɹ� false ʧ��
     */
    public static boolean resetAction()
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return false;
        }
        return resetAction(SOCKET);
    }
    /**
     * ���¼���Action
     * @param socket TSocket
     * @return boolean true �ɹ� false ʧ��
     */
    public static boolean resetAction(TSocket socket)
    {
        TParm parm = new TParm();
        parm.setData("SYSTEM","ACTION",0x2);
        parm = socket.doAction(parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return false;
        }
        return true;
    }
    /**
     * �������Ƿ�����
     * @param socket TSocket
     * @return boolean true ���� false û������
     */
    public static boolean appIsRun(TSocket socket)
    {
        TParm parm = new TParm();
        parm.setData("SYSTEM","ACTION",0x3);
        parm = socket.doAction(parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return false;
        }
        return true;
    }
    /**
     * ִ��SQL ��ѯ���
     * @param parm TParm ����
     * @return TParm
     */
    public static TParm executeQuery(TParm parm)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeQuery(SOCKET,parm);
    }
    /**
     * ִ��SQL ��ѯ���
     * @param socket TSocket
     * @param parm TParm ����
     * @return TParm
     */
    public static TParm executeQuery(TSocket socket,TParm parm)
    {
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeQuery",parm);
    }
    /**
     * ִ��SQL ��ѯ���
     * @param sql String SQL���
     * @return TParm ���
     */
    public static TParm executeQuery(String sql)
    {
        return executeQuery("",sql);
    }
    /**
     * ִ��SQL ��ѯ���
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @return TParm ���
     */
    public static TParm executeQuery(String dbname,String sql)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeQuery(SOCKET,dbname,sql);
    }
    /**
     * ִ��SQL ��ѯ���
     * @param socket TSocket
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @return TParm ���
     */
    public static TParm executeQuery(TSocket socket,String dbname,String sql)
    {
        TParm parm = new TParm();
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","DNULL",true);
        parm.setData("ACTION","TRIM",true);
        parm.setData("ACTION","SNIPPET",false);
        parm.setData("ACTION","PREPARE",false);
        return executeQuery(socket,parm);
    }
    /**
     * ִ��SQL ��ѯ���(���������)
     * @param parm TParm
     * @return TParm
     */
    public static TParm executeParmQuery(TParm parm)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeParmQuery(SOCKET,parm);
    }
    /**
     * ִ��SQL ��ѯ���(���������)
     * @param socket TSocket
     * @param parm TParm
     * @return TParm
     */
    public static TParm executeParmQuery(TSocket socket,TParm parm)
    {
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeParmQuery",parm);
    }
    /**
     * ִ��SQL ��ѯ���(���������)
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @param startRow int ��ʼ�к�
     * @param endRow int �����к�
     * @return TParm
     */
    public static TParm executeParmQuery(String dbname,String sql,int startRow,int endRow)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeParmQuery(SOCKET,dbname,sql,startRow,endRow);
    }
    /**
     * ִ��SQL ��ѯ���(���������)
     * @param socket TSocket
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @param startRow int ��ʼ�к�
     * @param endRow int �����к�
     * @return TParm
     */
    public static TParm executeParmQuery(TSocket socket,String dbname,String sql,int startRow,int endRow)
    {
        TParm parm = new TParm();
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","DNULL",true);
        parm.setData("ACTION","TRIM",true);
        parm.setData("ACTION","SNIPPET",true);
        parm.setData("ACTION","PREPARE",false);
        parm.setData("ACTION","START_ROW",startRow);
        parm.setData("ACTION","END_ROW",endRow);
        return executeParmQuery(socket,parm);
    }
    /**
     * ��ҳ��ѯ���(���������)
     * @param dbname String ���ݿ�������
     * @param startRow int ��ʼ�к�
     * @param endRow int �����к�
     * @param parmCode String ��������
     * @return TParm
     */
    public static TParm executeParmQuery(String dbname,int startRow,int endRow,String parmCode)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeParmQuery(SOCKET,dbname,startRow,endRow,parmCode);
    }
    /**
     * ��ҳ��ѯ���(���������)
     * @param socket TSocket
     * @param dbname String ���ݿ�������
     * @param startRow int ��ʼ�к�
     * @param endRow int �����к�
     * @param parmCode String ��������
     * @return TParm
     */
    public static TParm executeParmQuery(TSocket socket,String dbname,int startRow,int endRow,String parmCode)
    {
        TParm parm = new TParm();
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","DNULL",true);
        parm.setData("ACTION","TRIM",true);
        parm.setData("ACTION","SNIPPET",true);
        parm.setData("ACTION","PREPARE",false);
        parm.setData("ACTION","START_ROW",startRow);
        parm.setData("ACTION","END_ROW",endRow);
        parm.setData("ACTION","PARM_CODE",parmCode);
        return executeParmQuery(socket,parm);
    }
    /**
     * �رս����
     * @param dbname String ���ݿ����ӳ���
     * @param parmCode String ��������
     * @return TParm
     */
    public static TParm executeResultClose(String dbname,String parmCode)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeResultClose(SOCKET,dbname,parmCode);
    }
    /**
     * �رս����
     * @param socket TSocket
     * @param dbname String ���ݿ����ӳ���
     * @param parmCode String ��������
     * @return TParm
     */
    public static TParm executeResultClose(TSocket socket,String dbname,String parmCode)
    {
        TParm parm = new TParm();
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","PARM_CODE",parmCode);
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeResultClose",parm);
    }
    /**
     * ִ���޸ĵ�SQL���
     * @param parm TParm
     * @return TParm
     */
    public static TParm executeUpdate(TParm parm)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeUpdate(SOCKET,parm);
    }
    /**
     * ִ���޸ĵ�SQL���
     * @param socket TSocket
     * @param parm TParm
     * @return TParm
     */
    public static TParm executeUpdate(TSocket socket,TParm parm)
    {
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeUpdate",parm);
    }
    /**
     * ִ���޸ĵ�SQL���
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @return TParm
     */
    public static TParm executeUpdate(String dbname,String sql)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeUpdate(SOCKET,dbname,sql);
    }
    /**
     * ִ���޸ĵ�SQL���
     * @param socket TSocket
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @return TParm
     */
    public static TParm executeUpdate(TSocket socket,String dbname,String sql)
    {
        return executeUpdate(socket,dbname,sql,false,"");
    }
    /**
     * ִ���޸ĵ�SQL���(��������)
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @param connectionContinue boolean ���ñ�������
     * @param indexCode String ���ӱ��
     * @return TParm
     */
    public static TParm executeUpdate(String dbname,String sql,boolean connectionContinue,String indexCode)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeUpdate(SOCKET,dbname,sql,connectionContinue,indexCode);
    }
    /**
     * ִ���޸ĵ�SQL���(��������)
     * @param socket TSocket
     * @param dbname String ���ݿ����ӳ���
     * @param sql String SQL���
     * @param connectionContinue boolean ���ñ�������
     * @param indexCode String ���ӱ��
     * @return TParm
     */
    public static TParm executeUpdate(TSocket socket,String dbname,String sql,boolean connectionContinue,String indexCode)
    {
        TParm parm = new TParm();
        parm.setData("SQL",sql);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        parm.setData("ACTION","CONNECTION_CONTINUE",connectionContinue);
        parm.setData("ACTION","CONNECTION_CODE",indexCode);
        parm.setData("ACTION","PREPARE",false);
        return executeUpdate(socket,parm);
    }
    /**
     * Commit
     * @param indexCode String ���ӱ��
     * @return TParm
     */
    public static TParm executeCommit(String indexCode)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeCommit(SOCKET,indexCode);
    }
    /**
     * Commit
     * @param socket TSocket
     * @param indexCode String ���ӱ��
     * @return TParm
     */
    public static TParm executeCommit(TSocket socket,String indexCode)
    {
        TParm parm = new TParm();
        parm.setData("ACTION","CONNECTION_CONTINUE",true);
        parm.setData("ACTION","CONNECTION_CODE",indexCode);
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeCommit",parm);
    }
    /**
     * Rollback
     * @param indexCode String ���ӱ��
     * @return TParm
     */
    public static TParm executeRollback(String indexCode)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeRollback(SOCKET,indexCode);
    }
    /**
     * Rollback
     * @param socket TSocket
     * @param indexCode String ���ӱ��
     * @return TParm
     */
    public static TParm executeRollback(TSocket socket,String indexCode)
    {
        TParm parm = new TParm();
        parm.setData("ACTION","CONNECTION_CONTINUE",true);
        parm.setData("ACTION","CONNECTION_CODE",indexCode);
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeRollback",parm);
    }
    /**
     * �������ݿ�����
     * @param dbname String ���ݿ����ӳ���
     * @return TParm
     */
    public static TParm executeOpenConnection(String dbname)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeOpenConnection(SOCKET,dbname);
    }
    /**
     * �������ݿ�����
     * @param socket TSocket
     * @param dbname String ���ݿ����ӳ���
     * @return TParm
     */
    public static TParm executeOpenConnection(TSocket socket,String dbname)
    {
        TParm parm = new TParm();
        parm.setData("ACTION","CONNECTION_CONTINUE",true);
        parm.setData("ACTION","DATABASE_NAME",dbname);
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeOpenConnection",parm);
    }
    /**
     * �ر�����
     * @param indexCode String ���ݿ����ӳ���
     * @return TParm
     */
    public static TParm executeCloseConnection(String indexCode)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeCloseConnection(SOCKET,indexCode);
    }
    /**
     * �ر�����
     * @param socket TSocket
     * @param indexCode String ���ݿ����ӳ���
     * @return TParm
     */
    public static TParm executeCloseConnection(TSocket socket,String indexCode)
    {
        TParm parm = new TParm();
        parm.setData("ACTION","CONNECTION_CONTINUE",true);
        parm.setData("ACTION","CONNECTION_CODE",indexCode);
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeCloseConnection",parm);
    }
    /**
     * ���ļ�
     * @param dir String Ŀ¼
     * @param fileName String �ļ���
     * @return byte[]
     */
    public static byte[] readFile(String dir,String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return readFile(SOCKET,dir,fileName);
    }
    /**
     * ���ļ�
     * @param socket TSocket
     * @param dir String Ŀ¼
     * @param fileName String �ļ���
     * @return byte[]
     */
    public static byte[] readFile(TSocket socket,String dir,String fileName)
    {
        if(dir == null)
            return null;
        if(!dir.endsWith("/"))
            dir += "/";
        return readFile(socket,dir + fileName);
    }
    /**
     * ��ȡ�ļ����浽����
     * @param fileName String
     * @param localFileName String
     * @return boolean
     */
    public static boolean readFileToLocal(String fileName,String localFileName)
    {
        byte data[] = readFile(fileName);
        if(data == null)
            return false;
        try{
            FileTool.setByte(localFileName,data);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * ���ļ�
     * @param fileName String �ļ���
     * @return byte[]
     */
    public static byte[] readFile(String fileName)
    {
        if(SOCKET == null)
        {
            err("1111SOCKET is null");
            //$$ test lx
            return null;
           // TSocket socket = new TSocket("127.0.0.1",8080,"web");
           // TIOM_AppServer.SOCKET = socket;
        }
        //
        return readFile(SOCKET,fileName);
    }
    /**
     * ���ļ�
     * @param socket TSocket
     * @param fileName String �ļ���
     * @return byte[]
     */
    public static byte[] readFile(TSocket socket,String fileName)
    {
        TParm parm = new TParm();
        fileName = StringTool.replaceAll(fileName,"\\","/");
        //
        //System.out.println("====fileName====="+fileName);
        //���ش���
        if(fileName.indexOf(":")!=-1){
        	 parm.setData("FILE_NAME",fileName.substring(fileName.indexOf(":")-1, fileName.length()));
        	 //System.out.println("----fileName1111---"+fileName.substring(fileName.indexOf(":")-1, fileName.length()));
        	 parm=readFile(parm);
        	 
        	 return (byte[])parm.getData("BYTES");
        }
        //
        parm.setData("FILE_NAME",fileName);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","readFile",parm);
        if(parm.getErrCode() < 0)
            return null;
        return (byte[])parm.getData("BYTES");
    }
    /**
     * �ӷ���������ͼ��
     * @param socket TSocket
     * @param fileName String
     * @return ImageIcon
     */
    public static ImageIcon getImage(TSocket socket,String fileName)
    {
        ImageIcon icon = null;
        try{
            icon = new ImageIcon(readFile(socket,fileName));
        }catch(NullPointerException e)
        {
            err(e.getMessage());
        }
        return icon;
    }
    /**
     * �ӷ���������ͼ��
     * @param fileName String
     * @return ImageIcon
     */
    public static ImageIcon getImage(String fileName)
    {
        ImageIcon icon = null;
        byte[] data = readFile(fileName);
        if(data == null)
        {
            err("ERR:û���ҵ�ͼƬ" + fileName);
            return icon;
        }
        icon = new ImageIcon(data);
        return icon;
    }
    /**
     * ��xml�ļ�
     * @param socket TSocket
     * @param fileName String �ļ���
     * @return INode
     */
    public INode getXMLFile(TSocket socket,String fileName)
    {
        byte data[] = readFile(socket,fileName);
        if(data == null)
            return null;
        return TNode.loadXML(data);
    }
    /**
     * ��xml�ļ�
     * @param fileName String �ļ���
     * @return INode
     */
    public INode getXMLFile(String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return getXMLFile(SOCKET,fileName);
    }
    /**
     * ��JAR�е��ļ�
     * @param dir String ·��
     * @param fileName String �ļ���
     * @return byte[]
     */
    public static byte[] readJarFile(String dir,String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return readJarFile(SOCKET,dir,fileName);
    }
    /**
     * ��JAR�е��ļ�
     * @param socket TSocket
     * @param dir String ·��
     * @param fileName String �ļ���
     * @return byte[]
     */
    public static byte[] readJarFile(TSocket socket,String dir,String fileName)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        TParm parm = new TParm();
        parm.setData("DIR",dir);
        parm.setData("FILE_NAME",fileName);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","readJarFile",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return null;
        }
        return (byte[])parm.getData("BYTES");
    }
    /**
     * ���ļ�ͷ
     * @param fileName String �ļ���
     * @return String
     */
    public static String readFileHead(String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return readFileHead(SOCKET,fileName);
    }
    /**
     * ���ļ�ͷ
     * @param socket TSocket
     * @param fileName String �ļ���
     * @return String
     */
    public static String readFileHead(TSocket socket,String fileName)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        TParm parm = new TParm();
        parm.setData("FILE_NAME",fileName);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","readFileHead",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return null;
        }
        return parm.getValue("DATA");
    }
    /**
     * д�ļ�
     * @param fileName String
     * @param localFileName String
     * @return boolean
     */
    public static boolean writeFile(String fileName,String localFileName)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        try{
            byte data[] = FileTool.getByte(localFileName);
            if (data == null)
                return false;
            return writeFile(fileName, data);
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * д�ļ�
     * @param fileName String �ļ���
     * @param data byte[] ����
     * @return boolean true �ɹ� false ʧ��
     */
    public static boolean writeFile(String fileName,byte[] data)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return false;
        }
        return writeFile(SOCKET,fileName,data);
    }
    /**
     * д�ļ�
     * @param socket TSocket
     * @param fileName String �ļ���
     * @param data byte[] ����
     * @return boolean true �ɹ� false ʧ��
     */
    public static boolean writeFile(TSocket socket,String fileName,byte[] data)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        TParm parm = new TParm();
        parm.setData("FILE_NAME",fileName);
        parm.setData("BYTES",data);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","writeFile",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return false;
        }
        return true;
    }
    /**
     * �б��ļ�
     * @param fileName String Ŀ¼��
     * @param listType String ����:D�б�Ŀ¼ F�б��ļ�
     * @param fileFilter String �ļ���׺ ".TXT"
     * @return String[]
     */
    public static String[] listFiles(String fileName,String listType,String fileFilter)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return listFiles(SOCKET,fileName,listType,fileFilter);
    }
    /**
     * �б��ļ�
     * @param socket TSocket
     * @param fileName String �ļ���
     * @param listType String ����:D�б�Ŀ¼ F�б��ļ�
     * @param fileFilter String �ļ���׺ ".TXT"
     * @return String[]
     */
    public static String[] listFiles(TSocket socket,String fileName,String listType,String fileFilter)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        TParm parm = new TParm();
        parm.setData("FILE_NAME",fileName);
        parm.setData("LIST_TYPE",listType);
        parm.setData("FILE_FILTER",fileFilter);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","listFile",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return null;
        }
        return (String[])parm.getData("FILE_LIST");
    }
    /**
     * �ļ��б�
     * @param socket TSocket
     * @param fileName String �ļ���
     * @return String[]
     */
    public static String[] listFile(TSocket socket,String fileName)
    {
        return listFile(socket,fileName);
    }
    /**
     * �ļ��б�
     * @param fileName String �ļ���
     * @return String[]
     */
    public static String[] listFile(String fileName)
    {
        return listFile(fileName,"");
    }
    /**
     * �ļ��б�
     * @param fileName String �ļ���
     * @param fileFilter String �ļ���׺ ".TXT"
     * @return String[]
     */
    public static String[] listFile(String fileName,String fileFilter)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return listFile(SOCKET,fileName,fileFilter);
    }
    /**
     * �ļ��б�
     * @param socket TSocket
     * @param fileName String �ļ���
     * @param fileFilter String �ļ���׺ ".TXT"
     * @return String[]
     */
    public static String[] listFile(TSocket socket,String fileName,String fileFilter)
    {
        return listFiles(socket,fileName,"F",fileFilter);
    }
    /**
     * �б�Ŀ¼
     * @param fileName String �ļ�
     * @return String[]
     */
    public static String[] listDir(String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return listDir(SOCKET,fileName);
    }
    /**
     * �б���Ŀ¼
     * @param socket TSocket
     * @param fileName String Ŀ¼��
     * @return String[]
     */
    public static String[] listDir(TSocket socket,String fileName)
    {
        return listFiles(socket,fileName,"D","");
    }
    /**
     * ɾ���ļ�
     * @param fileName String �ļ���
     * @return boolean
     */
    public static boolean deleteFile(String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return false;
        }
        return deleteFile(SOCKET,fileName);
    }
    /**
     * ɾ���ļ�
     * @param socket TSocket
     * @param fileName String �ļ���
     * @return boolean
     */
    public static boolean deleteFile(TSocket socket,String fileName)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        TParm parm = new TParm();
        parm.setData("FILE_NAME",fileName);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","deleteFile",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return false;
        }
        return true;
    }
    /**
     * ��Ŀ¼
     * @param fileName String Ŀ¼��
     * @return boolean
     */
    public static boolean mkdir(String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return false;
        }
        return mkdir(SOCKET,fileName);
    }
    /**
     * ��Ŀ¼
     * @param socket TSocket
     * @param fileName String Ŀ¼��
     * @return boolean
     */
    public static boolean mkdir(TSocket socket,String fileName)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        TParm parm = new TParm();
        parm.setData("FILE_NAME",fileName);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","mkdir",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return false;
        }
        return true;
    }
    /**
     * ��ȡ�ļ���Ϣ
     * @param fileName String �ļ���
     * @return TParm
     */
    public static TParm fileInf(String fileName)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return fileInf(SOCKET,fileName);
    }
    /**
     * ��ȡ�ļ���Ϣ
     * @param socket TSocket
     * @param fileName String �ļ���
     * @return TParm
     */
    public static TParm fileInf(TSocket socket,String fileName)
    {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        TParm parm = new TParm();
        parm.setData("FILE_NAME",fileName);
        parm = executeAction(socket,"com.dongyang.action.TFileAction","fileInf",parm);
        if(parm.getErrCode() < 0)
            err(parm.getErrText());
        return parm;
    }
    /**
     * �õ�Session������
     * @param name String ����
     * @return Object
     */
    public static Object getSessionAttribute(String name)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return getSessionAttribute(SOCKET,name);
    }
    /**
     * �õ�Session������
     * @param socket TSocket
     * @param name String ����
     * @return Object
     */
    public static Object getSessionAttribute(TSocket socket,String name)
    {
        TParm parm = new TParm();
        parm.setData("NAME",name);
        parm = executeAction(socket,"com.dongyang.action.TSessionAction","getSessionAttribute",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return null;
        }
        return parm.getData("VALUE");
    }
    /**
     * ����Session������
     * @param name String ����
     * @param obj Object ֵ
     * @return boolean
     */
    public static boolean setSessionAttribute(String name,Object obj)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return false;
        }
        return setSessionAttribute(SOCKET,name,obj);
    }
    /**
     * ����Session������
     * @param socket TSocket
     * @param name String ����
     * @param obj Object ֵ
     * @return boolean
     */
    public static boolean setSessionAttribute(TSocket socket,String name,Object obj)
    {
        TParm parm = new TParm();
        parm.setData("NAME",name);
        parm.setData("VAKUE",obj);
        parm = executeAction(socket,"com.dongyang.action.TSessionAction","setSessionAttribute",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return false;
        }
        return true;
    }
    /**
     * �õ�Session�����б�
     * @return String[]
     */
    public static String[] getSessionAttributeNames()
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return getSessionAttributeNames(SOCKET);
    }
    /**
     * �õ�Session�����б�
     * @param socket TSocket
     * @return String[]
     */
    public static String[] getSessionAttributeNames(TSocket socket)
    {
        TParm parm = new TParm();
        parm = executeAction(socket,"com.dongyang.action.TSessionAction","getSessionAttributeNames",parm);
        if(parm.getErrCode() < 0)
        {
            err(parm.getErrText());
            return null;
        }
        return (String[])parm.getData("LIST");
    }
    /**
     * ����Module
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param methodType String ����update/query
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm executeModule(String moduleID,String methodName,String methodType,TParm parm)
    {
        if(SOCKET == null)
        {
            err("SOCKET is null");
            return null;
        }
        return executeModule(SOCKET,moduleID,methodName,methodType,parm);
    }

    public static TParm executeModule(String dbName,String moduleID,String methodName,String methodType,TParm parm)
    {
        if (SOCKET == null) {
            err("SOCKET is null");
            return null;
        }
        return executeModule(SOCKET, dbName, moduleID, methodName, methodType,
                             parm);
    }


    /**
     * ����Module
     * @param socket TSocket
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param methodType String ����update/query
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm executeModule(TSocket socket,String moduleID,String methodName,String methodType,TParm parm)
    {
        TParm inParm = new TParm();
        inParm.setData("SYSTEM","ACTION",0x4);
        inParm.setData("SYSTEM","MODULE_ID",moduleID);
        inParm.setData("SYSTEM","MOULE_METHOD_NAME",methodName);
        inParm.setData("SYSTEM","METHOD_TYPE",methodType);
        inParm.setData("SYSTEM","MODULE_PARM",parm == null?null:parm.getData());
        TParm result = socket.doAction(inParm);
        return result;
    }

    public static TParm executeModule(TSocket socket,String dbName,String moduleID,String methodName,String methodType,TParm parm){
       TParm inParm = new TParm();
       inParm.setData("ACTION","DATABASE_NAME",dbName);
       inParm.setData("SYSTEM","ACTION",0x4);
       inParm.setData("SYSTEM","MODULE_ID",moduleID);
       inParm.setData("SYSTEM","MOULE_METHOD_NAME",methodName);
       inParm.setData("SYSTEM","METHOD_TYPE",methodType);
       inParm.setData("SYSTEM","MODULE_PARM",parm == null?null:parm.getData());
       TParm result = socket.doAction(inParm);
       return result;

     }


    /**
     * ��ѯselect
     * @param socket TSocket
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm query(TSocket socket,String moduleID,String methodName,TParm parm)
    {
        return executeModule(socket,moduleID,methodName,"query",parm);
    }
    /**
     * ��ѯselect
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm query(String moduleID,String methodName,TParm parm)
    {
        return executeModule(moduleID,methodName,"query",parm);
    }
    /**
     * ��ѯselect
     * @param socket TSocket
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @return TParm �ز�
     */
    public static TParm query(TSocket socket,String moduleID,String methodName)
    {
        return executeModule(socket,moduleID,methodName,"query",null);
    }
    /**
     * ��ѯselect
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @return TParm �ز�
     */
    public static TParm query(String moduleID,String methodName)
    {
        return executeModule(moduleID,methodName,"query",null);
    }
    /**
     * ����update
     * @param socket TSocket
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm update(TSocket socket,String moduleID,String methodName,TParm parm)
    {
        return executeModule(socket,moduleID,methodName,"update",parm);
    }
    public static TParm query(String dbName,String moduleID,String methodName,TParm parm){
        return executeModule(dbName,moduleID,methodName,"query",parm);
    }


    /**
     * ����update
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm update(String moduleID,String methodName,TParm parm)
    {
        return executeModule(moduleID,methodName,"update",parm);
    }
    /**
     * ����update
     * @param socket TSocket
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @return TParm �ز�
     */
    public static TParm update(TSocket socket,String moduleID,String methodName)
    {
        return executeModule(socket,moduleID,methodName,"update",null);
    }
    /**
     * ����update
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @return TParm �ز�
     */
    public static TParm update(String moduleID,String methodName)
    {
        return executeModule(moduleID,methodName,"update",null);
    }
    /**
     * ��ѯselect
     * @param socket TSocket
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm call(TSocket socket,String moduleID,String methodName,TParm parm)
    {
        return executeModule(socket,moduleID,methodName,"call",parm);
    }
    /**
     * ��ѯcall
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @param parm TParm ����
     * @return TParm �ز�
     */
    public static TParm call(String moduleID,String methodName,TParm parm)
    {
        return executeModule(moduleID,methodName,"call",parm);
    }
    /**
     * ��ѯcall
     * @param socket TSocket
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @return TParm �ز�
     */
    public static TParm call(TSocket socket,String moduleID,String methodName)
    {
        return executeModule(socket,moduleID,methodName,"call",null);
    }
    /**
     * ��ѯcall
     * @param moduleID String Module��ǩ���ļ���
     * @param methodName String ������
     * @return TParm �ز�
     */
    public static TParm call(String moduleID,String methodName)
    {
        return executeModule(moduleID,methodName,"call",null);
    }
    /**
     * ��־���
     * @param text String ��־����
     */
    public static void out(String text) {
        Log.getInstance().out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public static void out(String text,boolean debug)
    {
        Log.getInstance().out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public static void err(String text) {
        Log.getInstance().err(text);
    }
    /**
     * ���Գ�ʼ��(���Գ���ר��)
     */
    public static void testInit()
    {

    }
    
    /**
     * �������ļ�
     * @param parm TParm FILE_NAME
     * @return TParm BYTES
     */
    public static TParm readFile(TParm parm) {
    	TParm result = new TParm();
        String fileName = parm.getValue("FILE_NAME");
        if (fileName.trim().length() == 0){
        	result.setErr(-1,"FILE_NAME is null","FILE_NAME");
            return result;
        }
        //
        File f = new File(fileName);
        if (!f.exists()){
        	result.setErr(-1,fileName + " �ļ�������!");
            return result;
        }
        //
        //TParm result1 = new TParm();
        try {
            //���ļ�����
            FileInputStream stream = new FileInputStream(f);
            byte data[] = FileTool.getByte(stream);
            stream.close();
            result.setData("BYTES", data);
        } catch (Exception e) {
        	result.setErr(-1,e.getMessage());
            return result;
        }
        return result;
    }
    public static void main(String args[])
    {
        TSocket socket = new TSocket("lzk",8090,"web");
        TIOM_AppServer.SOCKET = socket;
        TParm parm = new TParm();
        //System.out.println(TIOM_AppServer.query("SYSDictionaryModule.x","getGroupList"));
        parm.setData("GROUP_ID","ROOT");
        parm.setData("ID","GROUP");
        System.out.println(TIOM_AppServer.query("SYSDictionaryModule.x","getName",parm));

        //parm.setData("TEST","OK");
        //System.out.println(new String(TIOM_AppServer.readFile(socket,"%ROOT%\\WEB-INF\\config\\system\\class.ini")));
        //System.out.println(TIOM_AppServer.fileInf(socket,"%ROOT%\\WEB-INF\\config\\system\\class.ini"));
        //byte b[] = TIOM_AppServer.readJarFile("%ROOT%\\common\\lib\\a.jar","com\\javahis\\system\\TMainControl.class");
        //byte b[] = com.dongyang.util.FileTool.getJarByte("D:\\Project\\webserver\\common\\lib\\a.jar","com\\javahis\\system\\TMainControl.class");
        //System.out.println(b);
        //System.out.println(StringTool.getString(TIOM_AppServer.listFiles("c:","","")));
        //System.out.println(StringTool.getString(TIOM_AppServer.listFile("c:",".ini")));
        //System.out.println(StringTool.getString(TIOM_AppServer.listDir("c:")));
        //System.out.println(StringTool.getString(TIOM_AppServer.listDir("%ROOT%")));
        //System.out.println(TIOM_TiisServer.executeAction(socket,"action.test.TestAction","test",parm));
        //System.out.println(TIOM_TiisServer.resetAction(socket));
        //System.out.println(TIOM_AppServer.executeQuery(socket,"lzk","select * from ACI_RESULT"));

        //parm = TIOM_TiisServer.executeOpenConnection(socket,"lzk");
        //System.out.println(parm);
        /*String indexCode = parm.getValue("ACTION","CONNECTION_CODE");
        //System.out.println(TIOM_TiisServer.executeUpdate(socket,"lzk","create table aaa(id VARCHAR2(20),name varchar2(20))",true,indexCode));
        //System.out.println(TIOM_TiisServer.executeUpdate(socket,"lzk","ALTER TABLE aaa ADD (  CONSTRAINT aaa PRIMARY KEY (id))",true,indexCode));
        System.out.println(TIOM_TiisServer.executeUpdate(socket,"lzk","insert into aaa values('2','b')",true,indexCode));
        System.out.println(TIOM_TiisServer.executeRollback(socket,indexCode));
        System.out.println(TIOM_TiisServer.executeUpdate(socket,"lzk","insert into aaa values('3','c')",true,indexCode));

        System.out.println(TIOM_TiisServer.executeCommit(socket,indexCode));
        System.out.println(TIOM_TiisServer.executeCloseConnection(socket,indexCode));
*/

        //System.out.println(TIOM_TiisServer.executeUpdate(socket,"lzk","insert into aaa values('2','b')"));

        //System.out.println(TIOM_TiisServer.executeParmQuery(socket,"lzk","select * from aaa",0,0));
        //System.out.println(TIOM_TiisServer.executeParmQuery(socket,"lzk",0,0,"PARM23383006"));
        //System.out.println(TIOM_TiisServer.executeResultClose(socket,"lzk","PARM16200795"));
    }
}
