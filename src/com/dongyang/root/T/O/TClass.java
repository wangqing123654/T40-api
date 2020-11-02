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
     * 名称
     */
    private String name;
    /**
     * 属性列表
     */
    private List s;
    /**
     * 方法列表
     */
    private List m;
    /**
     * 构造器
     */
    public TClass()
    {
        s = new ArrayList();
        m = new ArrayList();
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 得到属性
     * @param index int
     * @return TS
     */
    public TS getS(int index)
    {
        return (TS)s.get(index);
    }
    /**
     * 设置属性
     * @param index int
     * @param ts TS
     */
    public void setS(int index,TS ts)
    {
        s.set(index,ts);
    }
    /**
     * 增加属性
     * @param ts TS
     */
    public void addS(TS ts)
    {
        s.add(ts);
    }
    /**
     * 增加属性
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
     * 删除属性
     * @param index int
     */
    public void removeS(int index)
    {
        s.remove(index);
    }
    /**
     * 属性个数
     * @return int
     */
    public int sizeS()
    {
        return s.size();
    }
    /**
     * 方法个数
     * @return int
     */
    public int sizeM()
    {
        return m.size();
    }
    /**
     * 得到方法
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
     * 得到方法
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
     * 增加
     * @param tm TM
     */
    public void addM(TM tm)
    {
        m.add(tm);
    }
    /**
     * 查找方法
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
     * 保存
     * @param fileName String 文件名
     * @return boolean true 成功 false 失败
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
     * 读取
     * @param fileName String 文件名
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
