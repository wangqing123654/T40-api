package com.dongyang.util;

import java.io.FileOutputStream;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 *
 * <p>Title: ����ϵͳ</p>
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
     * ����ʱ���ʽ
     */
    public static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss.SS");
    /**
     * ·��
     */
    private String path;
    /**
     * �ļ���
     */
    private String fileName;
    /**
     * �û���Ϣ
     */
    private String userInf;
    /**
     * �û�IP
     */
    private String IP;
    /**
     * ����·��
     * @param path String
     */
    public void setPath(String path)
    {
        this.path = path;
    }
    /**
     * �õ�·��
     * @return String
     */
    public String getPath()
    {
        return path;
    }
    /**
     * �����ļ���
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * �õ��ļ���
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * �����û���Ϣ
     * @param userInf String
     */
    public void setUserInf(String userInf)
    {
        this.userInf = userInf;
    }
    /**
     * �õ��û���Ϣ
     * @return String
     */
    public String getUserInf()
    {
        return userInf;
    }
    /**
     * ����IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        this.IP = IP;
    }
    /**
     * �õ�IP
     * @return String
     */
    public String getIP()
    {
        return IP;
    }
    /**
     * �õ��ļ�
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
     * ���
     * @param s String
     */
    public void out(String s)
    {
        if(s == null || s.length() == 0)
            return;
        write(getInf() + s);
    }
    /**
     * д�ļ�
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
     * д�ļ�
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
     * �õ���ǰ��ʱ��
     * @return String
     */
    public static String getTime() {
        return dateFormat.format(new Date());
    }
    /**
     * �õ���Ϣ
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
