package test;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class TestL
{
    public static void main(String args[])
    {
        try{
            String str = "\u00E7";
            System.out.println((int)str.charAt(0));
            System.out.println(new String(str.getBytes("UTF-8"),"UTF-8"));
            /*File f = new File("D:\\lzk\\Project\\webserver\\config\\system\\Language1.xml");
            FileOutputStream fos = new FileOutputStream(f);
            Writer out = new OutputStreamWriter(fos, "utf-8");
            out.write(str);
            out.flush();
            out.close();
            fos.close(); */

            /*String s1 = new String("Fran\u00E7ais".getBytes("UTF-8"),"UTF-8");
            System.out.println(s1);
            FileInputStream f = new FileInputStream("D:\\lzk\\Project\\webserver\\config\\system\\Language1.xml");
            byte b[] = new byte[100];
            f.read(b);
            f.close();
            String s = new String(b,"GBK");
            System.out.println(s);*/
        }catch(Exception e)
        {
        }
    }
}
