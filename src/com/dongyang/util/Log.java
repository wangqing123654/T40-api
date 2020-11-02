package com.dongyang.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.dongyang.config.TConfigParm;
import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintStream;
/**
 * ��־ϵͳ
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
     * ȫ��debug״̬
     */
    public static boolean DEBUG = false;
    /**
     * ����debug״̬
     */
    public static boolean debug = false;
    /**
     * ��־�ؼ���
     */
    public static final String INI_CLASS_LOG = "log";
    /**
     * ����ʱ���ʽ
     */
    public static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss.SS");
    /**
     * ���ļ��б�
     */
    public static String debugList[];
    /**
     * �ļ������
     */
    private static FileOutputStream outFileStream;
    /**
     * �����ļ������
     */
    private static FileOutputStream errFileStream;
    /**
     * �û���Ϣ
     */
    private String userInf;
    /**
     * �û�IP
     */
    private String IP;
    /**
     * ����
     */
    private static Log instance;
    /**
     * �õ�ʵ��
     * @return Log
     */
    public static Log getInstance()
    {
        if(instance == null)
            instance = new Log();
        return instance;
    }
    /**
     * ������
     */
    public Log()
    {
        reset();
    }
    /**
     * ������
     * @param userInf String
     */
    public Log(String userInf)
    {
        setUserInf(userInf);
    }
    /**
     * ����
     */
    public void reset()
    {
        StackTraceElement stack = Thread.currentThread().getStackTrace()[3];
        debug = isDebug(stack.getClassName());
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
        return userInf == null?"":userInf;
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
        return IP == null?"":IP;
    }
    /**
     * ��ʼ��
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
     * �жϴ����Ƿ������־
     * @param className String ����
     * @return boolean true �����־ false �������־
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
     * �����־
     * @param text String
     */
    public void out(String text)
    {
        out(text,false);
    }
    /**
     * �����־
     * @param text String ��־��Ϣ
     * @param flg boolean true ǿ�������־ false ��ȡ�����
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
     * ��λ��
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
     * ���������Ϣ
     * @param text String ������Ϣ
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
     * �õ���ǰ��ʱ��
     * @return String
     */
    public static String getTime() {
        return dateFormat.format(new Date());
    }
    /**
     * ��ʼ����־Ŀ¼
     * @param outLogPath String ���Ŀ¼
     * @param errLogPath String ����Ŀ¼
     * @param appendLog boolean �Ƿ񸲸Ǵ�ǰ����־ true׷�� false����
     */
    public static void initLogPath(String outLogPath,String errLogPath,boolean appendLog)
    {
        //�������Ŀ¼
        if(outLogPath.length() > 0)
        {
            String dir = FileTool.getDir(outLogPath);
            File f = new File(dir);
            if(!f.exists())
                if(!f.mkdirs())
                    System.out.println("�޷���������Ŀ¼" + dir);

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
        //���ô���Ŀ¼
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
     * �ر���־�򿪵��ļ�
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
