package com.dongyang.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.dongyang.config.TConfigParm;
import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintStream;
/**
 * 日志系统
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author lzk
 * @version Javahis 1.0
 */
public class Log {
    /**
     * 全局debug状态
     */
    public static boolean DEBUG = false;
    /**
     * 对象debug状态
     */
    public static boolean debug = false;
    /**
     * 日志关键字
     */
    public static final String INI_CLASS_LOG = "log";
    /**
     * 日期时间格式
     */
    public static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss.SS");
    /**
     * 类文件列表
     */
    public static String debugList[];
    /**
     * 文件输出流
     */
    private static FileOutputStream outFileStream;
    /**
     * 错误文件输出流
     */
    private static FileOutputStream errFileStream;
    /**
     * 用户信息
     */
    private String userInf;
    /**
     * 用户IP
     */
    private String IP;
    /**
     * 事例
     */
    private static Log instance;
    /**
     * 得到实例
     * @return Log
     */
    public static Log getInstance()
    {
        if(instance == null)
            instance = new Log();
        return instance;
    }
    /**
     * 构造器
     */
    public Log()
    {
        reset();
    }
    /**
     * 构造器
     * @param userInf String
     */
    public Log(String userInf)
    {
        setUserInf(userInf);
    }
    /**
     * 重置
     */
    public void reset()
    {
        StackTraceElement stack = Thread.currentThread().getStackTrace()[3];
        debug = isDebug(stack.getClassName());
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
        return userInf == null?"":userInf;
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
        return IP == null?"":IP;
    }
    /**
     * 初始化
     * @param configParm TConfigParm
     */
    public static void onInit(TConfigParm configParm) {
        if (configParm == null)
            return;
        debugList = configParm.getConfigClass().getStringList(configParm.
                getSystemGroup(), INI_CLASS_LOG);
        for (int i = 0; i < debugList.length; i++) {
            if (debugList[i].endsWith("*"))
                debugList[i] = debugList[i].substring(0, debugList[i].length() - 1);
        }
    }
    /**
     * 判断此类是否输出日志
     * @param className String 类名
     * @return boolean true 输出日志 false 不输出日志
     */
    public static boolean isDebug(String className) {
        if (DEBUG)
            return true;
        if (debugList == null)
            return false;
        for (int i = 0; i < debugList.length; i++)
            if (className.startsWith(debugList[i]))
                return true;
        return false;
    }
    /**
     * 输出日志
     * @param text String
     */
    public void out(String text)
    {
        out(text,false);
    }
    /**
     * 输出日志
     * @param text String 日志信息
     * @param flg boolean true 强行输出日志 false 读取类加载
     */
    public void out(String text,boolean flg)
    {
        StackTraceElement stack = getStack();
        if(debug || flg || isDebug(stack.getClassName()))
        {
            if(text.endsWith("\n"))
                text = text.substring(0,text.length() - 1);
            System.out.println(getTime() + getIP() + getUserInf() + " " +
                               stack.getClassName() + "." + stack.getMethodName() +
                               "(" + stack.getFileName() + ":" +
                               stack.getLineNumber() + ")->" + text);

        }
    }
    /**
     * 定位堆
     * @return StackTraceElement
     */
    public StackTraceElement getStack()
    {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        for(int i = 0;i < stack.length;i++)
        {
            if ("com.dongyang.util.Log".equals(stack[i].getClassName()))
                continue;
            if ( "java.lang.Thread".equals(stack[i].getClassName()))
                continue;
            if ("out".equals(stack[i].getMethodName()))
                continue;
            if ("err".equals(stack[i].getMethodName()))
                continue;
            return stack[i];
        }
        return null;
    }
    /**
     * 输出错误信息
     * @param text String 错误信息
     */
    public void err(String text)
    {
        if(text != null && text.endsWith("\n"))
            text = text.substring(0,text.length() - 1);
        StackTraceElement stack = getStack();
        String s = getTime() + getIP() + getUserInf() + " " + stack.getClassName() + "." + stack.getMethodName() + "(" + stack.getFileName() + ":" + stack.getLineNumber() + ")->" + text;
        System.out.println(s);
        /*StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
        for(int i = stacks.length - 1;i > 5;i --)
        {
            System.err.println(getTime() + getIP() + getUserInf() + " " +
                               stacks[i].getClassName() + "." +
                               stacks[i].getMethodName() + "(" +
                               stacks[i].getFileName() + ":" +
                               stacks[i].getLineNumber() + ")");
        }*/
        System.err.println(s);
    }
    /**
     * 得到当前的时间
     * @return String
     */
    public static String getTime() {
        return dateFormat.format(new Date());
    }
    /**
     * 初始化日志目录
     * @param outLogPath String 输出目录
     * @param errLogPath String 错误目录
     * @param appendLog boolean 是否覆盖从前的日志 true追加 false覆盖
     */
    public static void initLogPath(String outLogPath,String errLogPath,boolean appendLog)
    {
        //设置输出目录
        if(outLogPath.length() > 0)
        {
            String dir = FileTool.getDir(outLogPath);
            File f = new File(dir);
            if(!f.exists())
                if(!f.mkdirs())
                    System.out.println("无法创建日至目录" + dir);

            f = new File(outLogPath);
            if(!appendLog && f.exists())
                f.delete();
            try{
                outFileStream = new FileOutputStream(f, true);
                System.setOut(new PrintStream(outFileStream, true));
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        //设置错误目录
        if(errLogPath.length() > 0)
        {
            File f = new File(FileTool.getDir(errLogPath));
            if(!f.exists())
                f.mkdirs();
            f = new File(errLogPath);
            if(appendLog && f.exists())
                f.delete();
            try{
                errFileStream = new FileOutputStream(f, true);
                System.setErr(new PrintStream(errFileStream, true));
            }catch(Exception e)
            {
            }
        }
    }
    /**
     * 关闭日志打开的文件
     */
    public static void closeLogFile()
    {
        if(outFileStream != null)
            try{
                outFileStream.close();
            }catch(Exception e)
            {
            }
        if(errFileStream != null)
            try{
                errFileStream.close();
            }catch(Exception e)
            {
            }
    }
}
