package com.dongyang.root.T.O;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * <p>Title: 加载器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.6
 * @version 1.0
 */
public class TClassLoad
        implements java.io.Serializable
{

    /**
     * 加载列表
     */
    private List data;
    /**
     * 路径
     */
    private String path;
    /**
     * 加载脚本
     */
    private boolean isLoadSrc;
    /**
     * 构造器
     */
    public TClassLoad()
    {
        data = new ArrayList();
    }
    /**
     * 设置路径
     * @param path String
     */
    public void setPath(String path)
    {
        this.path = path;
    }
    /**
     * 得到路径
     * @return String
     */
    public String getPath()
    {
        return path;
    }
    /**
     * 设置加载脚本
     * @param isLoadSrc boolean
     */
    public void setLoadSrc(boolean isLoadSrc)
    {
        this.isLoadSrc = isLoadSrc;
    }
    /**
     * 是否加载脚本
     * @return boolean
     */
    public boolean isLoadSrc()
    {
        return isLoadSrc;
    }
    /**
     * 尺寸
     * @return int
     */
    public int size()
    {
        return data.size();
    }
    /**
     * 增加Class
     * @param tClass TClass
     */
    public void add(TClass tClass)
    {
        data.add(tClass);
    }
    /**
     * 得到Class
     * @param name String
     * @return TClass
     */
    public TClass get(String name)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            TClass tClass = get(i);
            if(tClass.getName().equals(name))
                return tClass;
        }
        return null;
    }
    /**
     * 得到类位置
     * @param name String
     * @return int
     */
    public int getIndex(String name)
    {
        int count = size();
        for(int i = 0;i < count;i++)
        {
            TClass tClass = get(i);
            if(tClass.getName().equals(name))
                return i;
        }
        return -1;
    }
    /**
     * 得到Class
     * @param index int
     * @return TClass
     */
    public TClass get(int index)
    {
        return (TClass)data.get(index);
    }
    /**
     * 设置
     * @param index int
     * @param tClass TClass
     */
    public void set(int index,TClass tClass)
    {
        data.set(index,tClass);
    }
    /**
     * 新对象
     * @param name String
     * @return TObject
     */
    public TObject newObject(String name)
    {
        TClass tClass = get(name);
        if(tClass == null)
            return null;
        TObject object = new TObject();
        object.setTClass(tClass);
        object.init();
        return object;
    }
    /**
     * 加载
     * @param className String
     * @return boolean
     */
    public boolean load(String className)
    {
        return load(className,false);
    }
    /**
     * 加载
     * @param className String
     * @param b boolean
     * @return boolean
     */
    public boolean load(String className,boolean b)
    {
        int index = getIndex(className);
        TClass tClass = null;
        if(b || index == -1)
        {
            tClass = TClass.load(getPath() + "\\" + className + ".x");
            if (tClass == null)
                return false;
            if(index != -1)
                set(index,tClass);
            else
                add(tClass);
        }
        return true;
    }
    public static void main(String args[])
    {
        TClassLoad load = new TClassLoad();
        TClass c = new TClass();
        c.setName("A");
        c.addS("x","int");
        TM tm = new TM();
        tm.setName("f");
        tm.addParm("s","int");
        tm.add("x=s");
        c.addM(tm);
        tm = new TM();
        tm.setName("main");
        tm.addParm("args","String[]");
        tm.setStatic(true);

        //tm.add("int x = (10 + 10)");
        //tm.add("out(x)");
        tm.add("A a = new A()");
        tm.add("a.f(100)");
        tm.add("out (a.x)");

        /*tm.add("int i");
        tm.add("do");
        tm.add("{");6
        tm.add("i++");
        tm.add("out(\"--------i=\" + i)");
        tm.add("}");
        tm.add("while(i < 3)");
        */
        /*tm.add("int i");
        tm.add("for(;i < 10;i++)");
        tm.add("{");
        tm.add("if(i==2)");
        tm.add("continue");
        tm.add("out(\"--------i=\" + i)");
        tm.add("}");
        tm.add("out(\"-----end---\" + i)");
        */

        //tm.add("int x=10");
        //tm.add("out(\"i=\" + i)");
        //tm.add("}");

        /*tm.add("int x=12");
        tm.add("if(x > 10)");
        tm.add("return 100");
        tm.add("out(\"12345\")");*/



        c.addM(tm);
        load.add(c);
        TObject obj = load.newObject("A");
        c.save("c:\\A.x");

    }
}
