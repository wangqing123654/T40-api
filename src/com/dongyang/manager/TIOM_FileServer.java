package com.dongyang.manager;

import com.dongyang.data.TSocket;
import com.dongyang.data.TParm;
import java.util.Map;
import com.dongyang.util.Log;
import java.io.File;
import java.io.FileInputStream;
import com.dongyang.util.FileTool;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.dongyang.config.TConfig;
import com.dongyang.util.TDebug;
import javax.swing.ImageIcon;

/**
 *
 * <p>Title: 文件服务器管理器</p>
 *
 * <p>Description: 负责和文件服务器通讯</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 4.0
 */
public class TIOM_FileServer extends TIOM_Expend{
    /**
     * 得到文件服务器连接
     * @return TSocket
     */
    public static TSocket getSocket()
    {
        return getSocket("Main");
    }
    /**
     * 得到文件服务器连接
     * @param name String
     * @return TSocket
     */
    public static TSocket getSocket(String name)
    {
        TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
        String ip = config.getString("","FileServer." + name + ".IP");      
        TSocket socket = new TSocket(ip,TSocket.FILE_SERVER_PORT);
        if(!appIsRun(socket))
            return null;
        return socket;
    }
    /**
     * 得到文件服务器的根目录
     * @return String
     */
    public static String getRoot()
    {
        return getRoot("Main");
    }
    /**
     * 得到文件服务器的根目录
     * @param name String
     * @return String
     */
    public static String getRoot(String name)
    {
        TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
        String dir = config.getString("","FileServer." + name + ".Root");
        if(!dir.endsWith("/"))
            dir += "/";
        return dir;
    }
    /**
     * 得到目录
     * @param name String
     * @return String
     */
    public static String getPath(String name)
    {
        TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
        String dir = config.getString("",name);
        if(!dir.endsWith("/"))
            dir += "/";
        return dir;
    }
    /**
     * 读取文件
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @return byte[] 文件数据
     */
    public static byte[] readFile(TSocket socket,String fileName)
    {
        if(socket == null)
            return null;
        return readFile(socket,fileName,false);
    }
    /**
     * 读取文件
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @param deleteFlg boolean true 读取删除文件
     * @return byte[] 文件数据
     */
    public static byte[] readFile(TSocket socket,String fileName,boolean deleteFlg)
    {
        if(socket == null)
            return null;
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x1);
        parm.setData("FILE_NAME",fileName);
        parm.setData("DEL_FLG",deleteFlg);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return null;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return null;
        return (byte[])returnParm.getData("BYTES");
    }
    /**
     * 读取文件
     * @param fileName String 文件名
     * @return byte[] 文件数据
     */
    public static byte[] readFile(String fileName)
    {
        return readFile(fileName,false);
    }
    /**
     * 读取文件
     * @param fileName String 文件名
     * @param deleteFlg boolean true 读取删除文件
     * @return byte[] 文件数据
     */
    public static byte[] readFile(String fileName,boolean deleteFlg)
    {
        if (fileName == null || fileName.trim().length() == 0)
            return null;
        File f = new File(fileName);
        if (!f.exists())
            return null;
        byte data[] = null;
        try {
            //读文件内容
            FileInputStream stream = new FileInputStream(f);
            data = FileTool.getByte(stream);
            stream.close();
            if (deleteFlg)
                f.delete();
        } catch (Exception e) {
            return null;
        }
        return data;
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
        byte[] data = readFile(socket,fileName);
        if(data == null)
        {
            err("ERR:没有找到图片" + fileName);
            return icon;
        }
        icon = new ImageIcon(data);
        return icon;
    }
    /**
     * 写文件
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @param data byte[] 文件数据
     * @return boolean true 成功 false 失败
     */
    public static boolean writeFile(TSocket socket,String fileName,byte[] data)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x2);
        parm.setData("FILE_NAME",fileName);
        parm.setData("BYTES",data);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return false;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return false;
        return true;
    }
    /**
     * 写文件
     * @param fileName String 文件名
     * @param data byte[] 文件数据
     * @return boolean true 成功 false 失败
     */
    public static boolean writeFile(String fileName,byte[] data)
    {
        if (fileName == null || fileName.trim().length() == 0)
            return false;
        if (data == null)
            return false;
        String dir = getDir(fileName);
        if (dir.length() == 0)
            return false;
        //目录检测
        File f = new File(dir);
        if (!f.exists())
            f.mkdirs();
        f = new File(fileName);
        try {
            //读文件内容
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(data);
            stream.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * 写文件
     * @param socket TSocket
     * @param fileName String
     * @param localFileName String
     * @return boolean
     */
    public static boolean writeFile(TSocket socket,String fileName,String localFileName)
    {
        try{
            byte data[] = FileTool.getByte(localFileName);
            if (data == null)
                return false;
            return writeFile(socket,fileName, data);
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 得到文件目录
     * @param s String 文件名
     * @return String 目录
     */
    public static String getDir(String s) {
        int i;
        for (i = s.length() - 1; i >= 0; i--)
            if (s.charAt(i) == '/')
                break;
        if (i < 0)
            return "";
        return s.substring(0, i);
    }
    /**
     * 列表文件
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @return String[] 文件列表
     */
    public static String[] listFile(TSocket socket,String fileName)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x3);
        parm.setData("FILE_NAME",fileName);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return null;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return null;
        return (String[])returnParm.getData("FILE_LIST");
    }
    /**
     * 列表目录
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @return String[] 目录列表
     */
    public static String[] listDir(TSocket socket,String fileName)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x8);
        parm.setData("FILE_NAME",fileName);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return null;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return null;
        return (String[])returnParm.getData("FILE_LIST");
    }
    /**
     * 列表文件
     * @param fileName String 文件名
     * @return String[] 文件列表
     */
    public static String[] listFile(String fileName)
    {
        if (fileName == null || fileName.trim().length() == 0)
            return new String[]{};
        File f = new File(fileName);
        if (!f.exists())
          return new String[]{};
        File files[] = f.listFiles();
        ArrayList list = new ArrayList();
        for(int i = 0;i < files.length;i++)
          if(files[i].isFile())
            list.add(files[i].getName());
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 查找目录
     * @param socket TSocket
     * @param dir String
     * @param name String
     * @return String
     */
    public static String findDir(TSocket socket,String dir,String name)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x9);
        parm.setData("DIR_NAME",dir);
        parm.setData("FILE_NAME",name);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return null;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return null;
        return (String)returnParm.getData("DATA");
    }
    /**
     * 读文件头
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @return String 文件的首行数据
     */
    public static String readFileHead(TSocket socket,String fileName)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x4);
        parm.setData("FILE_NAME",fileName);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return null;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return null;
        return returnParm.getValue("DATA");
    }
    /**
     * 读文件头
     * @param fileName String 文件名
     * @return String 文件的首行数据
     */
    public static String readFileHead(String fileName)
    {
        if (fileName == null || fileName.trim().length() == 0)
            return null;
        File f = new File(fileName);
        if (!f.exists())
            return null;
        try {
            BufferedReader dr = new BufferedReader(new InputStreamReader(new
                    FileInputStream(f)));
            String data = dr.readLine();
            dr.close();
            return data;
        }
        catch (Exception e) {
            return null;
        }
    }
    /**
     * 删除文件
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @return boolean true 成功 false 失败
     */
    public static boolean deleteFile(TSocket socket,String fileName)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x5);
        parm.setData("FILE_NAME",fileName);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return false;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return false;
        return true;
    }
    /**
     * 删除文件
     * @param fileName String 文件名
     * @return boolean true 成功 false 失败
     */
    public static boolean deleteFile(String fileName)
    {
        if (fileName == null || fileName.trim().length() == 0)
            return false;
        File f = new File(fileName);
        if (!f.exists())
            return false;
        if(!f.delete())
            return false;
        return true;
    }
    /**
     * 创建目录
     * @param socket TSocket 连接对象
     * @param fileName String 文件名
     * @return boolean true 成功 false 失败
     */
    public static boolean mkdir(TSocket socket,String fileName)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x6);
        parm.setData("FILE_NAME",fileName);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return false;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
            return false;
        return true;
    }
    /**
     * 创建目录
     * @param fileName String 文件名
     * @return boolean true 成功 false 失败
     */
    public static boolean mkdir(String fileName)
    {
        if (fileName == null || fileName.trim().length() == 0)
            return false;
        //目录检测
        File f = new File(fileName);
        if (!f.exists())
            f.mkdirs();
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
        parm.setData("ACTION",0xFF);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return false;
        parm = new TParm((Map)obj);
        if(parm.getErrCode() < 0)
            return false;
        return true;
    }
    /**
     * 外设接口调用
     * @param socket TSocket
     * @param className String
     * @param functionName String
     * @param inData Object[]
     * @return Object
     */
    public static Object runIO(TSocket socket,String className,String functionName,Object[] inData)
    {
        TParm parm = new TParm();
        TParm returnParm = new TParm();
        parm.setData("ACTION",0x7);
        parm.setData("CLASS_NAME",className);
        parm.setData("FUNCTION_NAME",functionName);
        parm.setData("INDATA",inData);
        Object obj = socket.doSocket(parm.getData());
        if(obj == null)
            return null;
        returnParm.setData((Map)obj);
        if(returnParm.getErrCode() != 0)
        {
            System.out.println(returnParm.getErrText());
            return null;
        }
        return returnParm.getData("RETURN");
    }
    /**
     * 读取文件保存到本地
     * @param socket TSocket
     * @param fileName String
     * @param localFileName String
     * @return boolean
     */
    public static boolean readFileToLocal(TSocket socket,String fileName,String localFileName)
    {
        if(socket == null)
            return false;
        byte data[] = readFile(socket,fileName);
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
    public static void main(String args[])
    {
        TDebug.initClient();
        //TDebug.initServer();
        TIOM_FileServer.getSocket();
        System.out.println(TIOM_FileServer.appIsRun(TIOM_FileServer.getSocket()));

        Object obj = TIOM_FileServer.runIO(TIOM_FileServer.getSocket(),"test.TestFileIO","aaa",new Object[]{"in123"});
        System.out.println(obj);

        //String s = TIOM_FileServer.getRoot();
        //System.out.println(StringTool.getString(TIOM_FileServer.listFile(getSocket(),s)));
        //TIOM_FileServer.deleteFile(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/桌面/新建文件夹/aaa.txt");
        //TIOM_FileServer.mkdir(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/桌面/新建文件夹/aaa");
        //TIOM_FileServer.deleteFile(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/桌面/新建文件夹/aaa");
        /*System.out.println(TIOM_FileServer.readFileHead(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/桌面/新建文件夹/HIS32104.HL7"));
        String[] s = TIOM_FileServer.listFile(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"c:");
        System.out.println(StringTool.ArrayToString(s));

        TIOM_FileServer.writeFile(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"c:/bbb.txt","abc".getBytes());
*/
        //byte[] data = TIOM_FileServer.readFile(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"c:/bbb.txt",false);
        //TIOM_FileServer.writeFile("c:/bbb.txt","123".getBytes());
        //byte[] data = TIOM_FileServer.readFile("c:/bbb.txt",false);
        //System.out.println(new String(data));
        //String[] s = TIOM_FileServer.listFile("c:");
        //System.out.println(StringTool.ArrayToString(s));
        //System.out.println(TIOM_FileServer.readFileHead("C:/Documents and Settings/lizk/桌面/新建文件夹/HIS32104.HL7"));
        //TIOM_FileServer.deleteFile("C:/Documents and Settings/lizk/桌面/新建文件夹/aaa");
        //TIOM_FileServer.mkdir("C:/Documents and Settings/lizk/桌面/新建文件夹/aaa");


    }
}
