package com.dongyang.tui.io;

import com.dongyang.jdo.TStrike;
import com.dongyang.config.TConfig;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Map;
import com.dongyang.data.TParm;
import com.dongyang.util.StringTool;
import com.dongyang.util.FileTool;
import java.io.File;
import java.util.Date;

/**
 *
 * <p>Title: Java���빤��</p>
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
public class JavaCTool extends TStrike
{
    private TConfig config;
    /**
     * ������
     */
    public JavaCTool()
    {
        if(!isClientlink())
            setConfig(TConfig.getConfig("WEB-INF\\config\\system\\TConfig.x"));
    }
    /**
     * ���������ļ�
     * @param config TConfig
     */
    public void setConfig(TConfig config)
    {
        this.config = config;
    }
    /**
     * �õ������ļ�
     * @return TConfig
     */
    public TConfig getConfig()
    {
        return config;
    }
    /**
     * �õ�������Ϣ
     * @param name String
     * @return String
     */
    public String getConfigString(String name)
    {
        if(isClientlink())
            return (String)callServerMethod(name);
        return getConfig().getString("","JavaCTool." + name);
    }
    /**
     * �õ�JDK��װĿ¼
     * @return String
     */
    public String getJavaHome()
    {
        return getConfigString("JavaHome");
    }
    /**
     * �õ�Դ����Ŀ¼
     * @return String
     */
    public String getSourcePath()
    {
        return getConfigString("SourcePath");
    }
    /**
     * �õ����Ŀ¼
     * @return String
     */
    public String getOutPath()
    {
        return getConfigString("OutPath");
    }
    /**
     * �õ�ClassĿ¼
     * @return String
     */
    public String getClassPath()
    {
        return getConfigString("ClassPath");
    }
    /**
     * �õ���������
     * @param className String
     * @return String
     */
    public String javacCmdString(String className)
    {
        if(isClientlink())
            return (String)callServerMethod(className);
        if(className == null || className.length() == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        //javaHomeĿ¼
        String javaHome = getJavaHome();
        if(javaHome.length() > 0)
        {
            sb.append(javaHome);
            if(!javaHome.endsWith("\\"))
                sb.append("\\");
            sb.append("bin\\javac");
        }
        //���Ŀ¼
        String out = getOutPath();
        if(out.length() > 0)
        {
            sb.append(" -d \"");
            sb.append(out);
            sb.append("\"");
        }
        //Դ����Ŀ¼
        String sourcepath = getSourcePath();
        if(sourcepath.length() > 0)
        {
            sb.append(" -sourcepath \"");
            sb.append(sourcepath);
            sb.append("\"");
        }
        //�õ�ClassĿ¼
        String classPath = getClassPath();
        if(classPath.length() > 0)
        {
            sb.append(" -classpath \"");
            sb.append(classPath);
            sb.append("\"");
        }
        sb.append(" \"");
        sb.append(getSrcFileName(className));
        sb.append("\"");
        return sb.toString();
    }
    /**
     * �õ���������
     * @param className String
     * @return String
     */
    public String javaCmdString(String className)
    {
        if(className == null || className.length() == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        //javaHomeĿ¼
        String javaHome = getJavaHome();
        if(javaHome.length() > 0)
        {
            sb.append(javaHome);
            if(!javaHome.endsWith("\\"))
                sb.append("\\");
            sb.append("bin\\java");
        }
        //�õ�ClassĿ¼
        String classPath = getClassPath();
        if(classPath.length() > 0)
        {
            sb.append(" -classpath \"");
            sb.append(classPath);
            sb.append("\"");
        }
        sb.append(" ");
        sb.append(className);
        return sb.toString();
    }
    /**
     * �����
     * @param input InputStream
     * @return String
     */
    public String out(InputStream input)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            //byte data[] = FileTool.getByte(input);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String s = br.readLine();
            while (s != null)
            {
                if(sb.length() > 0)
                    sb.append("\n");
                sb.append(s);
                s = br.readLine();
            }
            br.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * ����
     * @param className String
     * @return Map
     */
    public Map javac(String className)
    {
        if(isClientlink())
            return (Map)callServerMethod(className);
        TParm reset = new TParm();
        String com = javacCmdString(className);
        //System.out.println("com=" + com);
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(com);
            String err = out(p.getErrorStream());
            if(p.exitValue() == 1)
                reset.setErr(-1,err);
            if(err.length() > 0)
                reset.setData("err",err);
            else
            {
                String out = out(p.getInputStream());
                if (out.length() > 0)
                    reset.setData("out", out);
            }
        } catch (Exception e)
        {
            reset.setErr(-1,e.getMessage());
        }
        return reset.getData();
    }
    /**
     * ����
     * @param className String
     * @return String
     */
    public Map java(String className)
    {
        if(isClientlink())
            return (Map)callServerMethod(className);
        TParm reset = new TParm();
        String com = javaCmdString(className);
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(com);
            String out = out(p.getInputStream());
            String err = out(p.getErrorStream());
            if(out.length() > 0)
                reset.setData("out",out);
            if(err.length() > 0)
                reset.setData("err",err);
            if(p.exitValue() == 1)
                reset.setErr(-1,err);
        } catch (Exception e)
        {
            reset.setErr(-1,e.getMessage());
        }
        return reset.getData();
    }
    /**
     * �õ�������ļ���
     * @param className String
     * @return String
     */
    public String getSrcFileName(String className)
    {
        StringBuffer sb = new StringBuffer();
        //Դ����Ŀ¼
        String sourcepath = getSourcePath();
        if(sourcepath.length() > 0)
        {
            sb.append(sourcepath);
            if(!sourcepath.endsWith("\\"))
                sb.append("\\");
        }
        sb.append(StringTool.replaceAll(className,".","\\"));
        sb.append(".java");
        return sb.toString();
    }
    /**
     * �õ�class�ļ���
     * @param className String
     * @return String
     */
    public String getClassFileName(String className)
    {
        StringBuffer sb = new StringBuffer();
        //���Ŀ¼
        String outpath = getOutPath();
        if(outpath.length() > 0)
        {
            sb.append(outpath);
            if(!outpath.endsWith("\\"))
                sb.append("\\");
        }
        sb.append(StringTool.replaceAll(className,".","\\"));
        sb.append(".class");
        return sb.toString();
    }
    /**
     * ����Դ����
     * @param className String
     * @param src String
     * @return boolean
     */
    public boolean saveSrc(String className,String src)
    {
        if(isClientlink())
            return (Boolean)callServerMethod(className,src);
        String fileName = getSrcFileName(className);
        try{
            FileTool.setString(fileName, src, false);
        }catch(Exception e)
        {
            return false;
        }
        return true;
    }
    /**
     * �õ����Class
     * @param className String
     * @return byte[]
     */
    public byte[] loadClass(String className)
    {
        if(isClientlink())
            return (byte[])callServerMethod(className);
        String fileName = getClassFileName(className);
        try{
            return FileTool.getByte(fileName);
        }catch(Exception e)
        {
            return null;
        }
    }
    /**
     * �õ������Ŀ¼
     * @param packageName String
     * @return String
     */
    public String getPackageFileName(String packageName)
    {
        StringBuffer sb = new StringBuffer();
        //Դ����Ŀ¼
        String sourcepath = getSourcePath();
        if(sourcepath.length() > 0)
        {
            sb.append(sourcepath);
            if(!sourcepath.endsWith("\\"))
                sb.append("\\");
        }
        sb.append(StringTool.replaceAll(packageName,".","\\"));
        return sb.toString();
    }
    /**
     * ����Class����
     * @param packageName String
     * @return String
     */
    public String createClassName(String packageName)
    {
        if(isClientlink())
            return (String)callServerMethod(packageName);
        String pathFileName = getPackageFileName(packageName);
        File f = new File(pathFileName);
        if(!f.exists())
            f.mkdirs();

        String fn = "C" + StringTool.getString(new Date(),"yyyyMMdd");
        int x = 0;
        String fileName = pathFileName + "\\" + fn + x + ".java";
        f = new File(fileName);
        while(f.exists())
        {
            x++;
            fileName = pathFileName + "\\" + fn + x + ".java";
            f = new File(fileName);
        }
        try{
            FileTool.setString(fileName,"");
        }catch(Exception e)
        {
            return "";
        }
        return fn + x;
    }
    public static void main(String args[])
    {
        //com.dongyang.util.TDebug.initClient();
        com.dongyang.util.TDebug.initServer();
        JavaCTool tool = new JavaCTool();
        System.out.println(tool.createClassName("a1.a2"));
        /*String s = "package test;\n" +
                   "import com.dongyang.data.TParm;\n" +
                   "import javax.swing.*;\n" +
                   "public class TestJavaC{\n" +
                   "public static void main(String args[]){\n" +
                   "TParm p = new TParm();\n" +
                   "p.setData(\"ID\",150);\n" +
                   "p.setData(\"Name\",\"name\");\n" +
                   "System.out.println(\"aaaParm=\" + p);\n" +
                   "}\n" +
                   "}";

        System.out.println(tool.saveSrc("test.TestJavaC",s));
        System.out.println(tool.javac("test.TestJavaC"));
        System.out.println(tool.java("test.TestJavaC"));*/
    }
}
