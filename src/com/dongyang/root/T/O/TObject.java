package com.dongyang.root.T.O;

/**
 *
 * <p>Title: ����</p>
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
public class TObject
        implements java.io.Serializable
{
    /**
     * ����
     */
    private S s;
    /**
     * ����
     */
    private TClass tClass;
    /**
     * ������
     */
    public TObject()
    {
        s = new S();
    }
    /**
     * ��ʼ��
     */
    public void init()
    {
        initS();
    }
    /**
     * ��ʼ������
     */
    public void initS()
    {
        int count = tClass.sizeS();
        for(int i = 0;i < count;i++)
        {
            TS ts = tClass.getS(i);
            s.add(ts.getName(),ts.getDefaultValue());
        }
    }
    /**
     * �õ�����
     * @return S
     */
    public S getS()
    {
        return s;
    }
    /**
     * �õ�����ֵ
     * @param name String
     * @return Object
     */
    public Object getSValue(String name)
    {
        int index = name.indexOf(".");
        if(index > 0)
        {
            String sName = name.substring(0,index);
            String cName = name.substring(index + 1);
            Object obj = getSValue(sName);
            if(obj == null)
                //�쳣
                return null;
            if(obj instanceof TObject)
            {
                TObject tobject = (TObject)obj;
                return tobject.getSValue(cName);
            }
            //�쳣
            return null;
        }
        if(s.exist(name))
            return s.get(name);
        //�쳣
        return null;
    }
    /**
     * ��������
     * @param name String
     * @param value Object
     * @return boolean
     */
    public boolean setSValue(String name,Object value)
    {
        int index = name.indexOf(".");
        if(index > 0)
        {
            String sName = name.substring(0,index);
            String cName = name.substring(index + 1);
            Object obj = getSValue(sName);
            if(obj == null)
                //�쳣
                return false;
            if(obj instanceof TObject)
            {
                TObject tobject = (TObject)obj;
                return tobject.setSValue(cName,value);
            }
            //�쳣
            return false;
        }
        if(s.exist(name))
        {
            s.set(name, value);
            return true;
        }
        //�쳣
        return false;
    }
    /**
     * ��������
     * @param tClass TClass
     */
    public void setTClass(TClass tClass)
    {
        this.tClass = tClass;
    }
    /**
     * �õ�����
     * @return TClass
     */
    public TClass getTClass()
    {
        return tClass;
    }
    public Object runM(VM vm,String name,Object[] parm)
    {
        TM tm = getTClass().getM(name,parm);
        if(tm == null)
            //�쳣
            return null;
        return runM(vm,tm,parm);
    }
    /**
     * ���з���
     * @param vm VM
     * @param tm TM
     * @param parm Object[]
     * @return Object
     */
    public Object runM(VM vm,TM tm,Object[] parm)
    {
        if(tm == null || parm == null)
            return null;
        if(tm.getParmSize() != parm.length)
            return null;
        S ls = new S();
        for(int i = 0;i < tm.getParmSize();i++)
        {
            TS ts = tm.getParm(i);
            ls.add(ts.getName(),parm[i]);
        }
        StackTrace st = new StackTrace();
        st.setVM(vm);
        st.setTM(tm);
        st.setS(ls);
        st.setTObject(this);
        return st.run();
    }
}
