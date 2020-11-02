package test.c;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.Type;

public class Testc extends A
{
    public void onOpen()
    {
        System.out.println("run open");
    }
    public void onOpen(int x)
    {
        System.out.println("run open int " + x);
    }
    public void onOpen(String x)
    {
        System.out.println("run open String " + x);
    }
    public void onClose()
    {
        System.out.println("close");
    }
    public static void main(String args[])
    {
        int y = 100;
        Object x = y;
        try{
            Class c = new Testc().getClass().getClassLoader().loadClass("test.c.Testc");
            Object obj = c.newInstance();
            Method m = c.getMethod("aaa",int.class);
            System.out.println(m);
            m.invoke(obj,100);
            /*
            Method m[] = c.getMethods();
            for(int i = 0;i < m.length;i++)
            {
                System.out.println(m[i].getName());
                if(m[i].getName().equals("onOpen"))
                {
                    Class [] cs = m[i].getParameterTypes();
                    for(int j = 0;j < cs.length;j++)
                    {

                        System.out.println("    " + cs[j] + " " + cs[j].isInstance(x));
                    }
                    //m[i].invoke(obj);
                }
            }*/
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
