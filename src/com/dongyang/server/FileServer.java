package com.dongyang.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import com.dongyang.data.TParm;
import java.io.File;
import java.io.FileInputStream;
import com.dongyang.util.FileTool;
import java.io.FileOutputStream;
import java.util.ArrayList;
import com.dongyang.data.TSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.dongyang.util.DynamicClassLoader;
import com.dongyang.util.RunClass;
import java.util.HashMap;

public class FileServer implements Runnable {
    /**
     * socket ref
     */
    private Socket socket;
    private Map ioObject;
    public FileServer(Socket socket) {
        ioObject = new HashMap();
        this.socket = socket;
    }

    /**
     * 处理数据流
     */
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            Map data = (Map) in.readObject();
            TParm parm = new TParm();
            parm.setData(data);
            TParm result = work(parm);
            out.writeObject(result.getData());
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分析客户端请求
     * @param parm ActionParm
     * @return ActionParm
     */
    public TParm work(TParm parm) {
        int action = parm.getInt("ACTION");
        switch (action) {
        case 0x1: //读文件
            return readFile(parm);
        case 0x2: //写入文件
            return writeFile(parm);
        case 0x3: //文件列表
            return listFile(parm);
        case 0x4: //读文件头
            return readFileHead(parm);
        case 0x5: //删除文件
            return deleteFile(parm);
        case 0x6: //建目录
            return mkdir(parm);
        case 0xFF: //服务器是否启动
            return appIsRun(parm);
        case 0x7://外设接口调用
            return runIO(parm);
        case 0x8://目录列表
            return listDir(parm);
        case 0x9://查找目录
            return findDir(parm);
        }
        TParm result = new TParm();
        result.setErr( -1, "陌生的动作类型 ACTION:" + action);
        return result;
    }
    /**
     * 服务器是否启动
     * @param parm TParm
     * @return TParm
     */
    public TParm appIsRun(TParm parm)
    {
        return new TParm();
    }
    /**
     * 读取文件
     * @param parm TParm FILE_NAME,DEL_FLG
     * @return TParm BYTES
     */
    public TParm readFile(TParm parm) {
        String function = "readFile";
        String fileName = parm.getValue("FILE_NAME");
        boolean deleteFlg = parm.getBoolean("DEL_FLG");
        out(function, "FILE_NAME=" + fileName + " DEL_FLG=" + deleteFlg);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->FILE_NAME 参数为空!");
            out(function, "FILE_NAME 参数为空!");
            return result;
        }
        File f = new File(fileName);
        if (!f.exists()) {
            result.setErr( -1,
                          "Err:FileServer." + function + "()->" + fileName + " 文件不存在!");
            out(function, fileName + " 文件不存在!");
            return result;
        }
        try {
            //读文件内容
            FileInputStream stream = new FileInputStream(f);
            byte data[] = FileTool.getByte(stream);
            stream.close();
            result.setData("BYTES", data);
            if (deleteFlg)
                f.delete();
        } catch (Exception e) {
            result.setErr( -1, e.getMessage());
            out(function, fileName + " " + e.getMessage());
            return result;
        }
        return result;
    }
    /**
     * 读文件头
     * @param parm TParm FILE_NAME
     * @return TParm Data
     */
    public TParm readFileHead(TParm parm)
    {
        String function = "readFileHead";
        String fileName = parm.getValue("FILE_NAME");
        out(function, "FILE_NAME=" + fileName);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->FILE_NAME 参数为空!");
            out(function, "FILE_NAME 参数为空!");
            return result;
        }
        File f = new File(fileName);
        if (!f.exists()) {
            result.setErr( -1,
                          "Err:FileServer." + function + "()->" + fileName + " 文件不存在!");
            out(function, fileName + " 文件不存在!");
            return result;
        }
        try {
            BufferedReader dr = new BufferedReader(new InputStreamReader(new
                    FileInputStream(f)));
            String data = dr.readLine();
            dr.close();
            result.setData("DATA", data);
        }
        catch (Exception e) {
            result.setErr( -1, e.getMessage());
            out(function,e.getMessage());
            return result;
        }
        return result;
    }
    /**
     * 删除文件
     * @param parm TParm FILE_NAME
     * @return TParm
     */
    public TParm deleteFile(TParm parm)
    {
        String function = "deleteFile";
        String fileName = parm.getValue("FILE_NAME");
        out(function, "FILE_NAME=" + fileName);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->FILE_NAME 参数为空!");
            out(function, "FILE_NAME 参数为空!");
            return result;
        }
        File f = new File(fileName);
        if (!f.exists()) {
            result.setErr( -1,
                          "Err:FileServer." + function + "()->" + fileName + " 文件不存在!");
            out(function, fileName + " 文件不存在!");
            return result;
        }
        if(!f.delete())
        {
            result.setErr( -1,
                           "Err:FileServer." + function + "()->" + fileName + " 删除文件失败!");
            out(function, fileName + " 删除文件失败!");
            return result;
        }
        return result;
    }
    /**
     * 查找目录
     * @param parm TParm
     * @return TParm
     */
    public TParm findDir(TParm parm)
    {
        String function = "findDir";
        String dirName = parm.getValue("DIR_NAME");
        String fileName = parm.getValue("FILE_NAME");
        out(function, "在" + dirName + "中查找" + fileName);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0 ||
            dirName == null || dirName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->参数为空!");
            out(function, "参数为空!");
            return result;
        }
        File f = new File(dirName);
        if (!f.exists()) {
            result.setErr( -1,
                          "Err:FileServer." + function + "()->" + dirName + " 目录不存在!");
            out(function, dirName + " 目录不存在!");
            return result;
        }
        String s = findDir(dirName,fileName);
        System.out.println(function + "找到" + s);
        result.setData("DATA", s);
        return result;
    }
    /**
     * 查找目录
     * @param dir String
     * @param name String
     * @return String
     */
    private String findDir(String dir,String name)
    {
        File[] f = new File(dir).listFiles();
        if(f == null)
            return "";
        for(int i = 0;i < f.length;i++)
        {
            System.out.println(name + " " + f[i].getName() + " " + name.equalsIgnoreCase(f[i].getName()));
            if(name.equalsIgnoreCase(f[i].getName()))
                return dir;
            String s = findDir(dir + "\\" + f[i].getName(),name);
            if(s != null && s.length() > 0)
                return s;
        }
        return "";
    }
    /**
     * 写入文件
     * @param parm TParm FILE_NAME,BYTES
     * @return TParm
     */
    public TParm writeFile(TParm parm) {
        String function = "writeFile";
        String fileName = parm.getValue("FILE_NAME");
        out(function, "FILE_NAME=" + fileName);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->FILE_NAME 参数为空!");
            out(function, "FILE_NAME 参数为空!");
            return result;
        }
        if (parm.getData("BYTES") == null || !(parm.getData("BYTES") instanceof byte[])) {
            result.setErr( -1, "Err:FileServer." + function + "()->BYTES 参数错误!");
            out(function, "BYTES 参数为空!");
            return result;
        }
        byte data[] = (byte[]) parm.getData("BYTES");
        String dir = FileTool.getDir(fileName);
        if (dir.length() == 0) {
            result.setErr( -1,
                          "Err:FileServer." + function + "()->FILE_NAME " + fileName +
                          "目录错误!");
            out(function, fileName + " 文件不存在!");
            return result;
        }
        //目录检测
        File f = new File(dir);
        if (!f.exists())
            f.mkdirs();
        try {
            //读文件内容
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(data);
            stream.close();
        } catch (Exception e) {
            result.setErr( -1, e.getMessage());
            out(function,e.getMessage());
            return result;
        }
        return result;
    }
    /**
     * 创建目录
     * @param parm TParm
     * @return TParm
     */
    public TParm mkdir(TParm parm)
    {
        String function = "mkdir";
        String fileName = parm.getValue("FILE_NAME");
        out(function, "FILE_NAME=" + fileName);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->FILE_NAME 参数为空!");
            out(function, "FILE_NAME 参数为空!");
            return result;
        }
        //目录检测
        File f = new File(fileName);
        if (!f.exists())
            f.mkdirs();
        return result;
    }
    /**
     * 文件列表
     * @param parm TParm FILE_NAME
     * @return TParm FILE_LIST
     */
    public TParm listFile(TParm parm)
    {
        String function = "listFile";
        String fileName = parm.getValue("FILE_NAME");
        out(function, "FILE_NAME=" + fileName);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->FILE_NAME 参数为空!");
            out(function, "FILE_NAME 参数为空!");
            return result;
        }
        File f = new File(fileName);
        if (!f.exists()) {
          result.setErr(-1,"Err:FileServer." + function + "()->" + fileName + " 文件不存在!");
          out(function, fileName + " 文件不存在!");
          return result;
        }
        File files[] = f.listFiles();
        ArrayList list = new ArrayList();
        if(files != null)
            for(int i = 0;i < files.length;i++)
                if(files[i].isFile())
                    list.add(files[i].getName());
        result.setData("FILE_LIST",(String[])list.toArray(new String[]{}));
        return result;
    }
    /**
     * 目录列表
     * @param parm TParm
     * @return TParm
     */
    public TParm listDir(TParm parm)
    {
        String function = "listDir";
        String fileName = parm.getValue("FILE_NAME");
        out(function, "FILE_NAME=" + fileName);
        TParm result = new TParm();
        if (fileName == null || fileName.trim().length() == 0) {
            result.setErr( -1, "Err:FileServer." + function + "()->FILE_NAME 参数为空!");
            out(function, "FILE_NAME 参数为空!");
            return result;
        }
        File f = new File(fileName);
        if (!f.exists()) {
          result.setErr(-1,"Err:FileServer." + function + "()->" + fileName + " 文件不存在!");
          out(function, fileName + " 文件不存在!");
          return result;
        }
        File files[] = f.listFiles();
        ArrayList list = new ArrayList();
        if(files != null)
            for(int i = 0;i < files.length;i++)
                if(files[i].isDirectory())
                    list.add(files[i].getName());
        result.setData("FILE_LIST",(String[])list.toArray(new String[]{}));
        return result;
    }

    public static void main(String args[]) {
        int port = TSocket.FILE_SERVER_PORT;
        if (args.length == 1)
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            ServerSocket server = new ServerSocket(port);
            out("File Server 4.0 Start io " + port);
            while (true) {
                try {
                    Socket socket = server.accept();
                    new Thread(new FileServer(socket)).start();
                } catch (Exception ep) {
                    ep.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 外设接口调用
     * @param parm TParm
     * @return TParm
     */
    public TParm runIO(TParm parm)
    {
        TParm result = new TParm();
        String name = parm.getValue("CLASS_NAME");
        Object object = ioObject.get(name);
        if(object == null && name != null && name.length() > 0)
        {
            try{
                Class s = Class.forName(name);
                object = s.newInstance();
            }catch(Exception e)
            {

               result.setErr(-1,e.getMessage());
               return result;
            }
        }
        if(object == null)
        {
            result.setErr(-1,"IOObject 空对象");
            return result;
        }
        ioObject.put(name,object);
        Object[] inData = (Object[])parm.getData("INDATA");
        String functionName = parm.getValue("FUNCTION_NAME");
        if(inData == null)
        {
            result.setErr(-1,"参数错误");
            return result;
        }
        Object obj = RunClass.runMethod(object,functionName,inData);
        result.setData("RETURN",obj);
        return result;
    }

    public void out(String function, String text) {
        System.out.println(function + "->" + text);
    }

    public static void out(String text) {
        System.out.println(text);
    }
}
