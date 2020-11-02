package test;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 *
 * <p>Title: �ļ�����</p>
 *
 * <p>Description: �ļ�����</p>
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
     * д���ļ�
     * @param file File �ļ�����
     * @param data byte[] �ļ����ݰ�
     * @throws IOException
     */
    public static void setByte(File file, byte[] data) throws IOException
    {
      FileOutputStream output = new FileOutputStream(file);
      output.write(data);
      output.close();
    }
    /**
     * ��ȡ�ļ�
     * @param file File �ļ�����
     * @return byte[] �ļ����ݰ�
     * @throws IOException �׳��쳣
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
     * �����ļ�����
     * @param name1 String Դ�ļ���
     * @param name2 String Ŀ���ļ���
     * @throws IOException �׳��쳣
     */
    public static void copy(String name1,String name2)throws IOException
    {
        setByte(new File(name2),getByte(new File(name1)));
    }
    /**
     * ����
     * @param args String[]
     */
    public static void main(String args[])
    {
        try{
            //��������
            copy("c:\\aaa.txt", "c:\\bbb.txt");
            //�����в����������� cmd cos����������:java test.CopyFile "c:\aaa.txt" "c:\bbb.txt"
            //copy(args[0],args[1]);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
