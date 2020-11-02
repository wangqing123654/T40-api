package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;

public class TM
        implements java.io.Serializable
{
    /**
     * ����
     */
    private String name;
    /**
     * �����б�
     */
    private List list;
    /**
     * ����
     */
    private List s;
    /**
     * �Ƿ��Ǿ�̬����
     */
    private boolean isStatic;
    /**
     * ������
     */
    public TM()
    {
        list = new ArrayList();
        s = new ArrayList();
    }
    /**
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * �����Ƿ��Ǿ�̬����
     * @param isStatic boolean
     */
    public void setStatic(boolean isStatic)
    {
        this.isStatic = isStatic;
    }
    /**
     * �Ƿ��Ǿ�̬����
     * @return boolean
     */
    public boolean isStatic()
    {
        return isStatic;
    }
    /**
     * �ߴ�
     * @return int
     */
    public int size()
    {
        return list.size();
    }
    /**
     * �õ�
     * @param index int
     * @return String
     */
    public String get(int index)
    {
        return (String)list.get(index);
    }
    /**
     * ����
     * @param s String
     */
    public void add(String s)
    {
        list.add(s);
    }
    /**
     * ɾ��
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    /**
     * ��������
     * @return int
     */
    public int getParmSize()
    {
        return s.size();
    }
    /**
     * ���Ӳ���
     * @param parm TS
     */
    public void addParm(TS parm)
    {
        s.add(parm);
    }
    /**
     * ���Ӳ���
     * @param name String
     * @param type String
     */
    public void addParm(String name,String type)
    {
        TS ts = new TS();
        ts.setName(name);
        ts.setType(type);
        addParm(ts);
    }
    /**
     * �õ�����
     * @param index int
     * @return TS
     */
    public TS getParm(int index)
    {
        return (TS)s.get(index);
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TM>>");
        sb.append(getName());
        sb.append("(");
        for(int i = 0;i < getParmSize();i++)
        {
            TS ts = getParm(i);
            sb.append(ts.getType());
            sb.append(" ");
            sb.append(ts.getName());
            if(i < getParmSize() - 1)
                sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }
}
