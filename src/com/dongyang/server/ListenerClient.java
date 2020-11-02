package com.dongyang.server;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import com.dongyang.util.FileTool;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class ListenerClient
{
    public static void main(String args[])
    {
        try{
            //FileOutputStream f1 = new FileOutputStream("D:\\lzk\\Project\\client\\x12.txt");

            //Socket socket = new Socket("10.72.24.16", 57772);
            Socket socket = new Socket("10.72.24.160", 7800);
            OutputStream out = socket.getOutputStream();
            byte b[] = FileTool.getByte("D:\\lzk\\Project\\client\\in.txt");
            out.write(b, 0, b.length);
            InputStream in = socket.getInputStream();
            byte data[] = getH(in);
            String s1 = new String(data);
            int x = 0;
            while(!"0\r\n".equals(s1))
            {
                //f1.write(data);
                x += data.length;
                System.out.println(s1);
                data = getH(in);
                s1 = new String(data);
            }
            getH(in);
            //f1.write(data);
            //f1.close();
            socket.close();
            System.out.println("x=" + x);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static String getHead(InputStream input)
    {
        StringBuffer s = new StringBuffer(100);
        try
        {
            int t = input.read();
            while (t != 10)
            {
                if (t != 13 && t != 10)
                    s.append((char) t);
                t = input.read();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return s.toString();
    }
    public static byte[] getH(InputStream input)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
        try{
            int t = input.read();
            while (t != 10)
            {
                out.write(t);
                t = input.read();
            }
            out.write(10);
            out.close();
        }catch(Exception e)
        {
        }
        return out.toByteArray();
    }
}
