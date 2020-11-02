package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;

public class TM
        implements java.io.Serializable
{
    /**
     * 名称
     */
    private String name;
    /**
     * 程序列表
     */
    private List list;
    /**
     * 参数
     */
    private List s;
    /**
     * 是否是静态方法
     */
    private boolean isStatic;
    /**
     * 构造器
     */
    public TM()
    {
        list = new ArrayList();
        s = new ArrayList();
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
     * 设置是否是静态方法
     * @param isStatic boolean
     */
    public void setStatic(boolean isStatic)
    {
        this.isStatic = isStatic;
    }
    /**
     * 是否是静态方法
     * @return boolean
     */
    public boolean isStatic()
    {
        return isStatic;
    }
    /**
     * 尺寸
     * @return int
     */
    public int size()
    {
        return list.size();
    }
    /**
     * 得到
     * @param index int
     * @return String
     */
    public String get(int index)
    {
        return (String)list.get(index);
    }
    /**
     * 增加
     * @param s String
     */
    public void add(String s)
    {
        list.add(s);
    }
    /**
     * 删除
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    /**
     * 参数个数
     * @return int
     */
    public int getParmSize()
    {
        return s.size();
    }
    /**
     * 增加参数
     * @param parm TS
     */
    public void addParm(TS parm)
    {
        s.add(parm);
    }
    /**
     * 增加参数
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
     * 得到参数
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
