package com.dongyang.root.T.O;

import java.util.Date;

public class VM
{
    private TClassLoad load;
    /**
     * ������
     */
    public VM()
    {
        load = new TClassLoad();
    }
    /**
     * �õ�������
     * @return TClassLoad
     */
    public TClassLoad getClassLoad()
    {
        return load;
    }
    /**
     * ����
     * @param className String
     * @param args String[]
     * @return Object
     */
    public Object run(String className,String args[])
    {
        if(!getClassLoad().load(className))
            return "����" + className + "ʧ��";
        TClass tClass = getClassLoad().get(className);
        if(tClass == null)
            return "����������";
        TM tm = tClass.getM("main",new String[]{"String[]"});
        if(tm == null)
            return "û���ҵ� main(String[] args) ����";
        if(!tm.isStatic())
            return "main���Ǿ�̬����";
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
