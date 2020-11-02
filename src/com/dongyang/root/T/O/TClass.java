package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.dongyang.util.FileTool;

/**
 *
 * <p>Title: TClass</p>
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
public class TClass
        implements java.io.Serializable
{
    /**
     * ����
     */
    private String name;
    /**
     * �����б�
     */
    private List s;
    /**
     * �����б�
     */
    private List m;
    /**
     * ������
     */
    public TClass()
    {
        s = new ArrayList();
        m = new ArrayList();
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
     * �õ�����
     * @param index int
     * @return TS
     */
    public TS getS(int index)
    {
        return (TS)s.get(index);
    }
    /**
     * ��������
     * @param index int
     * @param ts TS
     */
    public void setS(int index,TS ts)
    {
        s.set(index,ts);
    }
    /**
     * ��������
     * @param ts TS
     */
    public void addS(TS ts)
    {
        s.add(ts);
    }
    /**
     * ��������
     * @param name String
     * @param type String
     */
    public void addS(String name,String type)
    {
        TS ts = new TS();
        ts.setName(name);
        ts.setType(type);
        addS(ts);
    }
    /**
     * ɾ������
     * @param index int
     */
    public void removeS(int index)
    {
        s.remove(index);
    }
    /**
     * ���Ը���
     * @return int
     */
    public int sizeS()
    {
        return s.size();
    }
    /**
     * ��������
     * @return int
     */
    public int sizeM()
    {
        return m.size();
    }
    /**
     * �õ�����
     * @param index int
     * @return TM
     */
    public TM getM(int index)
    {
        return (TM)m.get(index);
    }
    public TM getM(String name,Object[] parm)
    {
        int count = sizeM();
        for(int i = 0;i < count;i++)
        {
            TM tm = getM(i);
            if(tm.getName().equals(name) && tm.getParmSize() == parm.length)
            {
                int c = tm.getParmSize();
                boolean b = true;
                for(int j = 0;j < c;j++)
                {
                    if(!tm.getParm(j).equalsType(parm[j]))
                        b = false;;
                }
                if(b)
                    return tm;
            }
        }
        return null;
    }
    /**
     * �õ�����
     * @param name String
     * @return TM[]
     */
    public TM[] getM(String name)
    {
        ArrayList list = new ArrayList();
        int count = sizeM();
        for(int i = 0;i < count;i++)
        {
            TM tm = getM(i);
            if(tm.getName().equals(name))
                list.add(tm);
        }
        return (TM[])list.toArray(new TM[]{});
    }
    /**
     * ����
     * @param tm TM
     */
    public void addM(TM tm)
    {
        m.add(tm);
    }
    /**
     * ���ҷ���
     * @param name String
     * @param parm String[]
     * @return TM
     */
    public TM getM(String name,String parm[])
    {
        int count = sizeM();
        for(int i = 0;i < count;i++)
        {
            TM tm = getM(i);
            if(tm.getName().equals(name) && tm.getParmSize() == parm.length)
            {
                boolean b = true;
                for (int j = 0; j < parm.length; j++)
                    if (!parm[j].equals(tm.getParm(j).getType()))
                    {
                        b = false;
                        break;
                    }
                if(b)
                    return tm;
            }
        }
        return null;
    }
    /**
     * ����
     * @param fileName String �ļ���
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean save(String fileName)
    {
        try
        {
            FileTool.setObject(fileName, this);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * ��ȡ
     * @param fileName String �ļ���
     * @return TClass
     */
    public static TClass load(String fileName)
    {
        try
        {
            return (TClass)FileTool.getObject(fileName);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
