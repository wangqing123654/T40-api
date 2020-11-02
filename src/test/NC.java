package test;

import java.util.Vector;
import com.dongyang.util.DynamicClassLoader;

public class NC
{
    public static void main(String args[])
    {
        p();
        aaa();
        p();
        Runtime.getRuntime().gc();
        p();
        System.out.println("----------------");
        aaa();
        p();
        Runtime.getRuntime().gc();
        p();
    }
    public static void aaa()
    {
        AAA aaa = new AAA();
        p();
    }
    public static void p()
    {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long userMemory = totalMemory - freeMemory;
        //System.out.println("availableProcessors=" + Runtime.getRuntime().availableProcessors());
        //System.out.println("totalMemory=" + totalMemory);
        //System.out.println("maxMemory=" + Runtime.getRuntime().maxMemory());
        //System.out.println("freeMemory=" + freeMemory);
        System.out.println("userMemory=" + userMemory);
    }
    static class AAA
    {
        Vector v = new Vector();
        public AAA()
        {
            for(int i = 0;i < 1000;i++)
                v.add(new DynamicClassLoader());
        }
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("finalize TPanelBase");
        }
    }
}
