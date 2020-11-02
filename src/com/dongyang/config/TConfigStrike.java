package com.dongyang.config;

import com.dongyang.jdo.TStrike;
import com.dongyang.Service.Server;
import java.io.File;
import java.util.Date;
import com.dongyang.util.StringTool;

public class TConfigStrike extends TStrike
{
    /**
     * 唯一实例
     */
    private static TConfigStrike instance;
    /**
     * 得到实例
     * @return TConfigStrike
     */
    public static TConfigStrike getInstance()
    {
        if(instance == null)
            instance = new TConfigStrike();
        return instance;
    }
    public long getFileLastModified(String fileName)
    {
        if(isClientlink())
            return (Long)callServerMethod(fileName);
        if(fileName.startsWith("%ROOT%"))
        {
            String s = getServerPath();
            if(!s.endsWith("/"))
                s += "/";
            fileName = s + fileName.substring(7, fileName.length());
            fileName = StringTool.replaceAll(fileName,"\\","/");
        }
        File f = new File(fileName);
        return f.lastModified();
    }
    public String getServerPath()
    {
        return Server.getServerPath();
    }
}
