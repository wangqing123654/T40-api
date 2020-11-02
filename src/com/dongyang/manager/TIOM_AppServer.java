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
     * 执行Class
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
     * 执行Class
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
     * 调用Action
     * @param className String 类名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
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
     * 调用Action
     * @param socket TSocket
     * @param className String 类名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
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
     * 重新加载Action
     * @return boolean true 成功 false 失败
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
     * 重新加载Action
     * @param socket TSocket
     * @return boolean true 成功 false 失败
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
     * 服务器是否启动
     * @param socket TSocket
     * @return boolean true 启动 false 没有启动
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
     * 执行SQL 查询语句
     * @param parm TParm 参数
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
     * 执行SQL 查询语句
     * @param socket TSocket
     * @param parm TParm 参数
     * @return TParm
     */
    public static TParm executeQuery(TSocket socket,TParm parm)
    {
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeQuery",parm);
    }
    /**
     * 执行SQL 查询语句
     * @param sql String SQL语句
     * @return TParm 结果
     */
    public static TParm executeQuery(String sql)
    {
        return executeQuery("",sql);
    }
    /**
     * 执行SQL 查询语句
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
     * @return TParm 结果
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
     * 执行SQL 查询语句
     * @param socket TSocket
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
     * @return TParm 结果
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
     * 执行SQL 查询语句(保留结果集)
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
     * 执行SQL 查询语句(保留结果集)
     * @param socket TSocket
     * @param parm TParm
     * @return TParm
     */
    public static TParm executeParmQuery(TSocket socket,TParm parm)
    {
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeParmQuery",parm);
    }
    /**
     * 执行SQL 查询语句(保留结果集)
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
     * @param startRow int 起始行号
     * @param endRow int 结束行号
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
     * 执行SQL 查询语句(保留结果集)
     * @param socket TSocket
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
     * @param startRow int 起始行号
     * @param endRow int 结束行号
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
     * 翻页查询语句(保留结果集)
     * @param dbname String 数据库连接名
     * @param startRow int 起始行号
     * @param endRow int 结束行号
     * @param parmCode String 结果集编号
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
     * 翻页查询语句(保留结果集)
     * @param socket TSocket
     * @param dbname String 数据库连接名
     * @param startRow int 起始行号
     * @param endRow int 结束行号
     * @param parmCode String 结果集编号
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
     * 关闭结果集
     * @param dbname String 数据库连接池名
     * @param parmCode String 结果集编号
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
     * 关闭结果集
     * @param socket TSocket
     * @param dbname String 数据库连接池名
     * @param parmCode String 结果集编号
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
     * 执行修改的SQL语句
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
     * 执行修改的SQL语句
     * @param socket TSocket
     * @param parm TParm
     * @return TParm
     */
    public static TParm executeUpdate(TSocket socket,TParm parm)
    {
        return executeAction(socket,"com.dongyang.action.TSQLAction","executeUpdate",parm);
    }
    /**
     * 执行修改的SQL语句
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
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
     * 执行修改的SQL语句
     * @param socket TSocket
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
     * @return TParm
     */
    public static TParm executeUpdate(TSocket socket,String dbname,String sql)
    {
        return executeUpdate(socket,dbname,sql,false,"");
    }
    /**
     * 执行修改的SQL语句(保留连接)
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
     * @param connectionContinue boolean 启用保留连接
     * @param indexCode String 连接编号
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
     * 执行修改的SQL语句(保留连接)
     * @param socket TSocket
     * @param dbname String 数据库连接池名
     * @param sql String SQL语句
     * @param connectionContinue boolean 启用保留连接
     * @param indexCode String 连接编号
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
     * @param indexCode String 连接编号
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
     * @param indexCode String 连接编号
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
     * @param indexCode String 连接编号
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
     * @param indexCode String 连接编号
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
     * 创建数据库连接
     * @param dbname String 数据库连接池名
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
     * 创建数据库连接
     * @param socket TSocket
     * @param dbname String 数据库连接池名
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
     * 关闭连接
     * @param indexCode String 数据库连接池名
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
     * 关闭连接
     * @param socket TSocket
     * @param indexCode String 数据库连接池名
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
     * 读文件
     * @param dir String 目录
     * @param fileName String 文件名
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
     * 读文件
     * @param socket TSocket
     * @param dir String 目录
     * @param fileName String 文件名
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
     * 读取文件保存到本地
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
     * 读文件
     * @param fileName String 文件名
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
     * 读文件
     * @param socket TSocket
     * @param fileName String 文件名
     * @return byte[]
     */
    public static byte[] readFile(TSocket socket,String fileName)
    {
        TParm parm = new TParm();
        fileName = StringTool.replaceAll(fileName,"\\","/");
        //
        //System.out.println("====fileName====="+fileName);
        //本地存在
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
     * 从服务器加载图标
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
     * 从服务器加载图标
     * @param fileName String
     * @return ImageIcon
     */
    public static ImageIcon getImage(String fileName)
    {
        ImageIcon icon = null;
        byte[] data = readFile(fileName);
        if(data == null)
        {
            err("ERR:没有找到图片" + fileName);
            return icon;
        }
        icon = new ImageIcon(data);
        return icon;
    }
    /**
     * 读xml文件
     * @param socket TSocket
     * @param fileName String 文件名
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
     * 读xml文件
     * @param fileName String 文件名
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
     * 读JAR中的文件
     * @param dir String 路径
     * @param fileName String 文件名
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
     * 读JAR中的文件
     * @param socket TSocket
     * @param dir String 路径
     * @param fileName String 文件名
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
     * 读文件头
     * @param fileName String 文件名
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
     * 读文件头
     * @param socket TSocket
     * @param fileName String 文件名
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
     * 写文件
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
     * 写文件
     * @param fileName String 文件名
     * @param data byte[] 数据
     * @return boolean true 成功 false 失败
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
     * 写文件
     * @param socket TSocket
     * @param fileName String 文件名
     * @param data byte[] 数据
     * @return boolean true 成功 false 失败
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
     * 列表文件
     * @param fileName String 目录名
     * @param listType String 分类:D列表目录 F列表文件
     * @param fileFilter String 文件后缀 ".TXT"
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
     * 列表文件
     * @param socket TSocket
     * @param fileName String 文件名
     * @param listType String 分类:D列表目录 F列表文件
     * @param fileFilter String 文件后缀 ".TXT"
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
     * 文件列表
     * @param socket TSocket
     * @param fileName String 文件名
     * @return String[]
     */
    public static String[] listFile(TSocket socket,String fileName)
    {
        return listFile(socket,fileName);
    }
    /**
     * 文件列表
     * @param fileName String 文件名
     * @return String[]
     */
    public static String[] listFile(String fileName)
    {
        return listFile(fileName,"");
    }
    /**
     * 文件列表
     * @param fileName String 文件名
     * @param fileFilter String 文件后缀 ".TXT"
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
     * 文件列表
     * @param socket TSocket
     * @param fileName String 文件名
     * @param fileFilter String 文件后缀 ".TXT"
     * @return String[]
     */
    public static String[] listFile(TSocket socket,String fileName,String fileFilter)
    {
        return listFiles(socket,fileName,"F",fileFilter);
    }
    /**
     * 列表目录
     * @param fileName String 文件
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
     * 列表子目录
     * @param socket TSocket
     * @param fileName String 目录名
     * @return String[]
     */
    public static String[] listDir(TSocket socket,String fileName)
    {
        return listFiles(socket,fileName,"D","");
    }
    /**
     * 删除文件
     * @param fileName String 文件名
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
     * 删除文件
     * @param socket TSocket
     * @param fileName String 文件名
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
     * 建目录
     * @param fileName String 目录名
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
     * 建目录
     * @param socket TSocket
     * @param fileName String 目录名
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
     * 读取文件信息
     * @param fileName String 文件名
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
     * 读取文件信息
     * @param socket TSocket
     * @param fileName String 文件名
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
     * 得到Session的属性
     * @param name String 名称
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
     * 得到Session的属性
     * @param socket TSocket
     * @param name String 名称
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
     * 设置Session的属性
     * @param name String 名称
     * @param obj Object 值
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
     * 设置Session的属性
     * @param socket TSocket
     * @param name String 名称
     * @param obj Object 值
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
     * 得到Session属性列表
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
     * 得到Session属性列表
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
     * 调用Module
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param methodType String 类型update/query
     * @param parm TParm 参数
     * @return TParm 回参
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
     * 调用Module
     * @param socket TSocket
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param methodType String 类型update/query
     * @param parm TParm 参数
     * @return TParm 回参
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
     * 查询select
     * @param socket TSocket
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public static TParm query(TSocket socket,String moduleID,String methodName,TParm parm)
    {
        return executeModule(socket,moduleID,methodName,"query",parm);
    }
    /**
     * 查询select
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public static TParm query(String moduleID,String methodName,TParm parm)
    {
        return executeModule(moduleID,methodName,"query",parm);
    }
    /**
     * 查询select
     * @param socket TSocket
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @return TParm 回参
     */
    public static TParm query(TSocket socket,String moduleID,String methodName)
    {
        return executeModule(socket,moduleID,methodName,"query",null);
    }
    /**
     * 查询select
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @return TParm 回参
     */
    public static TParm query(String moduleID,String methodName)
    {
        return executeModule(moduleID,methodName,"query",null);
    }
    /**
     * 更新update
     * @param socket TSocket
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public static TParm update(TSocket socket,String moduleID,String methodName,TParm parm)
    {
        return executeModule(socket,moduleID,methodName,"update",parm);
    }
    public static TParm query(String dbName,String moduleID,String methodName,TParm parm){
        return executeModule(dbName,moduleID,methodName,"query",parm);
    }


    /**
     * 更新update
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public static TParm update(String moduleID,String methodName,TParm parm)
    {
        return executeModule(moduleID,methodName,"update",parm);
    }
    /**
     * 更新update
     * @param socket TSocket
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @return TParm 回参
     */
    public static TParm update(TSocket socket,String moduleID,String methodName)
    {
        return executeModule(socket,moduleID,methodName,"update",null);
    }
    /**
     * 更新update
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @return TParm 回参
     */
    public static TParm update(String moduleID,String methodName)
    {
        return executeModule(moduleID,methodName,"update",null);
    }
    /**
     * 查询select
     * @param socket TSocket
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public static TParm call(TSocket socket,String moduleID,String methodName,TParm parm)
    {
        return executeModule(socket,moduleID,methodName,"call",parm);
    }
    /**
     * 查询call
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @param parm TParm 参数
     * @return TParm 回参
     */
    public static TParm call(String moduleID,String methodName,TParm parm)
    {
        return executeModule(moduleID,methodName,"call",parm);
    }
    /**
     * 查询call
     * @param socket TSocket
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @return TParm 回参
     */
    public static TParm call(TSocket socket,String moduleID,String methodName)
    {
        return executeModule(socket,moduleID,methodName,"call",null);
    }
    /**
     * 查询call
     * @param moduleID String Module标签或文件名
     * @param methodName String 方法名
     * @return TParm 回参
     */
    public static TParm call(String moduleID,String methodName)
    {
        return executeModule(moduleID,methodName,"call",null);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public static void out(String text) {
        Log.getInstance().out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public static void out(String text,boolean debug)
    {
        Log.getInstance().out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public static void err(String text) {
        Log.getInstance().err(text);
    }
    /**
     * 测试初始化(测试程序专用)
     */
    public static void testInit()
    {

    }
    
    /**
     * 读本地文件
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
        	result.setErr(-1,fileName + " 文件不存在!");
            return result;
        }
        //
        //TParm result1 = new TParm();
        try {
            //读文件内容
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
