package com.dongyang.root.T.O;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;
import com.dongyang.util.StringTool;

public class ClassSrc
{
    /**
     * TClass对象
     */
    private TClass tClass;
    /**
     * 构造器
     */
    public ClassSrc()
    {
    }
    /**
     * 设置类
     * @param tClass TClass
     */
    public void setTClass(TClass tClass)
    {
        this.tClass = tClass;
    }
    /**
     * 得到类
     * @return TClass
     */
    public TClass getTClass()
    {
        return tClass;
    }
    /**
     * 增加方法
     * @param data String
     * @return boolean
     */
    public boolean readTM(TM tm,String data)
    {
        if(tm == null)
            return false;
        SRC src = new SRC(data);
        while(!src.isEnd())
            System.out.println(src.getLine());


        /*    index = s1.indexOf(";");
            System.out.println("s1---" + s1);
            System.out.println("index=" + index);
            while(index > 0 && !isString(s1.substring(0,index)))
            {
                String s2 = s1.substring(0,index);
                System.out.println("---->" + s2);
                s1 = s1.substring(index + 1);
                index = s1.indexOf(";");
            }
            System.out.println(s1);
        }*/
        return true;
    }
    public boolean isString(String s)
    {
        boolean is = false;
        for(int i = 0;i < s.length();i++)
        {
            if(s.charAt(i) == '"' && (!is || s.charAt(i - 1) != '\\'))
                is = !is;
        }
        return is;
    }
    /**
     * 增加方法
     * @param br BufferedReader
     * @return TM
     */
    public TM readTM(BufferedReader br)
    {
        TM tm = new TM();
        tm.setName("main");
        tm.addParm("args","String[]");
        tm.setStatic(true);

        //tm.add("int x = (10 + 10)");
        //tm.add("out(x)");
        tm.add("A a = new A()");
        tm.add("a.f(100)");
        tm.add("out (a.x)");
        return tm;
    }
    /**
     * 读取文件
     * @param fileName String
     * @return TClass
     */
    public TClass getClassSrcFile(String fileName)
    {
        File f = new File(fileName);
        if (!f.exists())
            return null;
        try {
            return getClassSrc(new BufferedReader(new FileReader(f)));
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 读取文件
     * @param br BufferedReader
     * @return TClass
     */
    public TClass getClassSrc(BufferedReader br)
    {

        String s = "";
        try {
            while ((s = br.readLine()) != null)
            {
                s = s.trim();
                if (s.length() == 0)
                    continue;
                if (s.startsWith("//"))
                    continue;
                if(s.startsWith("class "))
                    if(!srcClass(tClass,s))
                    {
                        br.close();
                        return null;
                    }
            }
            br.close();
        }catch(Exception e)
        {
            return null;
        }
        return tClass;
    }
    private boolean srcClass(TClass tClass,String s)
    {
        if(tClass.getName() != null)
            return false;
        tClass.setName(s.substring(6));
        return false;
    }
    public static void main(String args[])
    {
        ClassSrc cs = new ClassSrc();
        TM tm = new TM();
        cs.readTM(tm,"if(a)out(12);for(int i = 0;i < 10;i ++){x=10;y=20}");
        cs.setTClass(new TClass());
        System.out.println(cs.getTClass());
    }
}
