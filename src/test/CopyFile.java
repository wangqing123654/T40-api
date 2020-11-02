package test;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 *
 * <p>Title: 文件拷贝</p>
 *
 * <p>Description: 文件拷贝</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author zhangjuan 2008.9.3
 * @version 1.0
 */
public class CopyFile
{
    /**
     * 写入文件
     * @param file File 文件对象
     * @param data byte[] 文件数据包
     * @throws IOException
     */
    public static void setByte(File file, byte[] data) throws IOException
    {
      FileOutputStream output = new FileOutputStream(file);
      output.write(data);
      output.close();
    }
    /**
     * 读取文件
     * @param file File 文件对象
     * @return byte[] 文件数据包
     * @throws IOException 抛出异常
     */
    public static byte[] getByte(File file) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte b[] = new byte[1024];
        int n;
        FileInputStream input = new FileInputStream(file);
        while ((n = input.read(b)) != -1)
            out.write(b, 0, n);
        out.close();
        return out.toByteArray();
    }
    /**
     * 拷贝文件方法
     * @param name1 String 源文件名
     * @param name2 String 目标文件名
     * @throws IOException 抛出异常
     */
    public static void copy(String name1,String name2)throws IOException
    {
        setByte(new File(name2),getByte(new File(name1)));
    }
    /**
     * 主程
     * @param args String[]
     */
    public static void main(String args[])
    {
        try{
            //拷贝范例
            copy("c:\\aaa.txt", "c:\\bbb.txt");
            //命令行参数拷贝范例 cmd cos下运行命令:java test.CopyFile "c:\aaa.txt" "c:\bbb.txt"
            //copy(args[0],args[1]);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
