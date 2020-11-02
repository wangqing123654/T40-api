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
 * <p>Title: �ļ�������������</p>
 *
 * <p>Description: ������ļ�������ͨѶ</p>
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
     * �õ��ļ�����������
     * @return TSocket
     */
    public static TSocket getSocket()
    {
        return getSocket("Main");
    }
    /**
     * �õ��ļ�����������
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
     * �õ��ļ��������ĸ�Ŀ¼
     * @return String
     */
    public static String getRoot()
    {
        return getRoot("Main");
    }
    /**
     * �õ��ļ��������ĸ�Ŀ¼
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
     * �õ�Ŀ¼
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
     * ��ȡ�ļ�
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @return byte[] �ļ�����
     */
    public static byte[] readFile(TSocket socket,String fileName)
    {
        if(socket == null)
            return null;
        return readFile(socket,fileName,false);
    }
    /**
     * ��ȡ�ļ�
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @param deleteFlg boolean true ��ȡɾ���ļ�
     * @return byte[] �ļ�����
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
     * ��ȡ�ļ�
     * @param fileName String �ļ���
     * @return byte[] �ļ�����
     */
    public static byte[] readFile(String fileName)
    {
        return readFile(fileName,false);
    }
    /**
     * ��ȡ�ļ�
     * @param fileName String �ļ���
     * @param deleteFlg boolean true ��ȡɾ���ļ�
     * @return byte[] �ļ�����
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
            //���ļ�����
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
     * �ӷ���������ͼ��
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
            err("ERR:û���ҵ�ͼƬ" + fileName);
            return icon;
        }
        icon = new ImageIcon(data);
        return icon;
    }
    /**
     * д�ļ�
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @param data byte[] �ļ�����
     * @return boolean true �ɹ� false ʧ��
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
     * д�ļ�
     * @param fileName String �ļ���
     * @param data byte[] �ļ�����
     * @return boolean true �ɹ� false ʧ��
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
        //Ŀ¼���
        File f = new File(dir);
        if (!f.exists())
            f.mkdirs();
        f = new File(fileName);
        try {
            //���ļ�����
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(data);
            stream.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * д�ļ�
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
     * �õ��ļ�Ŀ¼
     * @param s String �ļ���
     * @return String Ŀ¼
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
     * �б��ļ�
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @return String[] �ļ��б�
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
     * �б�Ŀ¼
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @return String[] Ŀ¼�б�
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
     * �б��ļ�
     * @param fileName String �ļ���
     * @return String[] �ļ��б�
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
     * ����Ŀ¼
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
     * ���ļ�ͷ
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @return String �ļ�����������
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
     * ���ļ�ͷ
     * @param fileName String �ļ���
     * @return String �ļ�����������
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
     * ɾ���ļ�
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @return boolean true �ɹ� false ʧ��
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
     * ɾ���ļ�
     * @param fileName String �ļ���
     * @return boolean true �ɹ� false ʧ��
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
     * ����Ŀ¼
     * @param socket TSocket ���Ӷ���
     * @param fileName String �ļ���
     * @return boolean true �ɹ� false ʧ��
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
     * ����Ŀ¼
     * @param fileName String �ļ���
     * @return boolean true �ɹ� false ʧ��
     */
    public static boolean mkdir(String fileName)
    {
        if (fileName == null || fileName.trim().length() == 0)
            return false;
        //Ŀ¼���
        File f = new File(fileName);
        if (!f.exists())
            f.mkdirs();
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
     * ����ӿڵ���
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
     * ��ȡ�ļ����浽����
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
        //TIOM_FileServer.deleteFile(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/����/�½��ļ���/aaa.txt");
        //TIOM_FileServer.mkdir(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/����/�½��ļ���/aaa");
        //TIOM_FileServer.deleteFile(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/����/�½��ļ���/aaa");
        /*System.out.println(TIOM_FileServer.readFileHead(new TSocket("localhost",TSocket.FILE_SERVER_PORT),"C:/Documents and Settings/lizk/����/�½��ļ���/HIS32104.HL7"));
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
        //System.out.println(TIOM_FileServer.readFileHead("C:/Documents and Settings/lizk/����/�½��ļ���/HIS32104.HL7"));
        //TIOM_FileServer.deleteFile("C:/Documents and Settings/lizk/����/�½��ļ���/aaa");
        //TIOM_FileServer.mkdir("C:/Documents and Settings/lizk/����/�½��ļ���/aaa");


    }
}
