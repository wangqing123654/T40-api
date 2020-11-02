package com.dongyang.bpel;

import com.dongyang.data.TTimer;
import com.dongyang.config.TConfigParm;
import com.dongyang.data.TSocket;
import com.dongyang.util.StringTool;
import com.dongyang.manager.TIOM_FileServer;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

public class BPELPope extends TTimer{
    public static DateFormat dateFormat = new SimpleDateFormat(
            "yyyyMMddhhmmssSSS");
    private TConfigParm parm = new TConfigParm();
    TSocket socket = new TSocket("localhost",TSocket.BPEL_SERVER_PORT);
    public boolean init()
    {
        parm.setSystemGroup("");
        parm.setConfig("BPELPope.ini");
        parm.setConfigClass("BPELClass.ini");
        return true;
    }
    public String getNow()
    {
        return dateFormat.format(new Date());
    }
    public void work()
    {
        //设置延时时间
        timerCount = parm.getConfig().getInt(parm.getSystemGroup(),"TimerCount",100);
        //是否读取本地文件
        boolean isLocalhost = StringTool.getBoolean(parm.getConfig().getString(parm.getSystemGroup(),"isLocalhost","Y"));
        if(!isLocalhost)
        {
            socket.setIP(parm.getConfig().getString(parm.getSystemGroup(),"FileServerIP","localhost"));
            socket.setPort(parm.getConfig().getInt(parm.getSystemGroup(),"FileServerPort",TSocket.BPEL_SERVER_PORT));
        }
        String rootDir = parm.getConfig().getString(parm.getSystemGroup(),"RootDir","c:/");
        rootDir.replace('\\','/');
        if(!rootDir.endsWith("/"))
            rootDir += "/";
        int maxFileCount = parm.getConfig().getInt(parm.getSystemGroup(),"MaxFileCount",20);
        //监听厂商OUT目录的设置
        String[] listenterArray = parm.getConfig().getStringList(parm.getSystemGroup(),"ListenerAllOutPath");
        //要解析文件的行位置(MSH|&&|HIS||ECG|此值为4表示要第4为的
        int fileIndex = parm.getConfig().getInt(parm.getSystemGroup(),"TargetArrayIndex");
        for(int index = 0; index < listenterArray.length; index++)
        {
            String targetPath = rootDir + listenterArray[index] + "OUT";
            String fileNames[] = isLocalhost?TIOM_FileServer.listFile(targetPath):
                                 TIOM_FileServer.listFile(socket,targetPath);
            if(fileNames == null)
                continue;
            int fileCount = fileNames.length;
            if(fileCount > maxFileCount)
                fileCount = maxFileCount;
            for(int i = 0;i < fileCount;i++)
            {
                String fileName = targetPath+"/"+fileNames[i];
                String data = isLocalhost?TIOM_FileServer.readFileHead(fileName):
                              TIOM_FileServer.readFileHead(socket,fileName);
                if(data==null||data.length()==0)
                    continue;
                String target = rootDir + StringTool.parseLine(data,"|")[fileIndex]+"IN";
                byte[] fileByte = isLocalhost?TIOM_FileServer.readFile(fileName):
                                  TIOM_FileServer.readFile(socket,fileName);
                if(fileByte==null||fileByte.length==0)
                    continue;
                String targetFileName = target+"/"+listenterArray[index]+getNow()+".HL7";
                boolean writeFlg = isLocalhost?TIOM_FileServer.writeFile(targetFileName,fileByte):
                                   TIOM_FileServer.writeFile(socket,targetFileName,fileByte);
                if(!writeFlg)
                    continue;
                if(isLocalhost)
                    TIOM_FileServer.deleteFile(fileName);
                else
                    TIOM_FileServer.deleteFile(socket,fileName);
                System.out.println("movefile:"+fileName+"-->newFile:"+targetFileName);
            }
        }
    }
}
