package com.dongyang.root.T.O;

import java.util.Date;

public class VM
{
    private TClassLoad load;
    /**
     * 构造器
     */
    public VM()
    {
        load = new TClassLoad();
    }
    /**
     * 得到加载器
     * @return TClassLoad
     */
    public TClassLoad getClassLoad()
    {
        return load;
    }
    /**
     * 运行
     * @param className String
     * @param args String[]
     * @return Object
     */
    public Object run(String className,String args[])
    {
        if(!getClassLoad().load(className))
            return "加载" + className + "失败";
        TClass tClass = getClassLoad().get(className);
        if(tClass == null)
            return "加载器错误";
        TM tm = tClass.getM("main",new String[]{"String[]"});
        if(tm == null)
            return "没有找到 main(String[] args) 方法";
        if(!tm.isStatic())
            return "main不是静态方法";
        StackTrace st = new StackTrace();
        st.setVM(this);
        S s = new S();
        s.add(tm.getParm(0).getName(),args);
        st.setTM(tm);
        st.setS(s);
        return st.run();
    }
    public static void main(String args[])
    {

        VM vm = new VM();
        vm.getClassLoad().setPath("c:");

        //vm.getClassLoad().setLoadSrc(true);
        Date start = new Date();
        System.out.println("-END-" + vm.run("A",new String[]{}));
        Date end = new Date();
        long x2 = end.getTime() - start.getTime();
        System.out.println("x2=" + x2);
    }
}
