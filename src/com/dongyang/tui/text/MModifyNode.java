package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 修改记录管理器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2010.2.1
 * @author whao 2013~
 * @version 1.0
 */
public class MModifyNode
{
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 当前级别
     */
    private int index = -1;
    /**
     * 构造器
     */
    public MModifyNode()
    {
        components = new TList();
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 增加成员
     * @param inf EModifyInf
     */
    public void add(EModifyInf inf)
    {
        if (inf == null)
            return;
        components.add(inf);
        inf.setParent(this);
    }
    /**
     * 删除成员
     * @param inf EModifyInf
     */
    public void remove(EModifyInf inf)
    {
        components.remove(inf);
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * 得到成员
     * @param index int
     * @return EModifyInf
     */
    public EModifyInf get(int index)
    {
        return (EModifyInf) components.get(index);
    }
    /**
     * 查找位置
     * @param inf EModifyInf
     * @return int
     */
    public int indexOf(EModifyInf inf)
    {
        if(inf == null)
            return -1;
        return components.indexOf(inf);
    }
    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * 设置当前级别
     * @param index int
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    /**
     * 得到当前级别
     * @return int
     */
    public int getIndex()
    {
        return index;
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
        //保存内部对象
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
            get(i).writeObject(s);
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //读对象属性
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        components = new TList();
        //读取行
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EModifyInf inf = new EModifyInf();
            inf.readObject(s);
            add(inf);
        }
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        /*s.writeBoolean(1,isVisible(),true);
        s.writeString(2,getName(),"");
        s.writeInt(3,getWidth(),0);
        s.writeInt(4,getHeight(),0);
        s.writeBoolean(5,isLockEdit(),false);*/
    }
    /**
     * 读对象属性
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        /*switch (id)
        {
        case 1:
            setVisible(s.readBoolean());
            return true;
        case 2:
            setName(s.readString());
            return true;
        case 3:
            setWidth(s.readInt());
            return true;
        case 4:
            setHeight(s.readInt());
            return true;
        case 5:
            setLockEdit(s.readBoolean());
            return true;
        }*/
        return false;
    }
}
