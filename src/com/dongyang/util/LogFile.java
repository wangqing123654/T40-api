package com.dongyang.util;

import java.io.FileOutputStream;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 *
 * <p>Title: 日至系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LogFile
{
    /**
     * 日期时间格式
     */
    public static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss.SS");
    /**
     * 路径
     */
    private String path;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 用户信息
     */
    private String userInf;
    /**
     * 用户IP
     */
    private String IP;
    /**
     * 设置路径
     * @param path String
     */
    public void setPath(String path)
    {
        this.path = path;
    }
    /**
     * 得到路径
     * @return String
     */
    public String getPath()
    {
        return path;
    }
    /**
     * 设置文件名
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * 得到文件名
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * 设置用户信息
     * @param userInf String
     */
    public void setUserInf(String userInf)
    {
        this.userInf = userInf;
    }
    /**
     * 得到用户信息
     * @return String
     */
    public String getUserInf()
    {
        return userInf;
    }
    /**
     * 设置IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        this.IP = IP;
    }
    /**
     * 得到IP
     * @return String
     */
    public String getIP()
    {
        return IP;
    }
    /**
     * 得到文件
     * @return File
     */
    public File getFile()
    {
        String s = getPath();
        if(s == null || s.length() == 0)
            return null;
        if(!s.endsWith("\\"))
            s += "\\";
        String f = getFileName();
        if(f == null || f.length() == 0)
            return null;
        if(!f.toLowerCase().endsWith(".log"))
            f += ".log";
        File file = new File(s);
        if(!file.exists())
            file.mkdirs();
        return new File(s + f);
    }
    /**
     * 输出
     * @param s String
     */
    public void out(String s)
    {
        if(s == null || s.length() == 0)
            return;
        write(getInf() + s);
    }
    /**
     * 写文件
     * @param s String
     */
    public void write(String s)
    {
        File f = getFile();
        if(f == null)
            return;
        write(f,s + "\r");
    }
    /**
     * 写文件
     * @param f File
     * @param s String
     */
    public void write(File f,String s)
    {
        try{
            FileOutputStream outFileStream = new FileOutputStream(f, true);
            outFileStream.write(s.getBytes());
            outFileStream.close();
        }catch(Exception e)
        {
        }
    }
    /**
     * 得到当前的时间
     * @return String
     */
    public static String getTime() {
        return dateFormat.format(new Date());
    }
    /**
     * 得到信息
     * @return String
     */
    public String getInf()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(getTime());
        if(getIP() != null && getIP().length() > 0)
        {
            sb.append(" ");
            sb.append(getIP());
        }
        if(getUserInf() != null && getUserInf().length() > 0)
        {
            sb.append(" ");
            sb.append(getUserInf());
        }
        sb.append(" ");
        return sb.toString();
    }
}
