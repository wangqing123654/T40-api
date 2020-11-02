package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Javac
{
    public Javac()
    {
    }

    public static void main(String args[])
    {
        run();
    }

    public static void run()
    {
        try
        {
            Runtime rt = Runtime.getRuntime();
            System.out.println("make");
            Process p = rt.exec("javac -d \"D:\\lzk\\Project\\client\\classes\" -sourcepath \"D:\\lzk\\Project\\client\\src\" \"D:\\lzk\\Project\\client\\src\\test\\AAA.java\"");
            System.out.println(p);
            outq(p.getInputStream());
            outq(p.getErrorStream());
            System.out.println("run");
            p = rt.exec("java -cp \"D:\\lzk\\Project\\client\\classes\" test.AAA");
            outq(p.getInputStream());
            outq(p.getErrorStream());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void outq(InputStream input)
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String s = br.readLine();
            while (s != null)
            {
                System.out.println(s);
                s = br.readLine();
            }
            br.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
