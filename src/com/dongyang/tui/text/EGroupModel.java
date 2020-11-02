package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 组模型</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.4.14
 * @version 1.0
 */
public class EGroupModel
{
    /**
     * 父类
     */
    EGroupModelList parent;
    /**
     * 组ID
     */
    private int id;
    /**
     * 组名称
     */
    private String name;
    /**
     * 组标题
     */
    private EColumnModel head;
    /**
     * 组合计
     */
    private EColumnModel sum;
    /**
     * 显示Data
     */
    private boolean isShowData;
    /**
     * 构造器
     * @param parent EGroupModelList
     */
    public EGroupModel(EGroupModelList parent)
    {
        setParent(parent);
    }
    /**
     * 设置父类
     * @param parent EGroupModelList
     */
    public void setParent(EGroupModelList parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return EGroupModelList
     */
    public EGroupModelList getParent()
    {
        return parent;
    }
    /**
     * 设置组ID
     * @param id int
     */
    public void setID(int id)
    {
        this.id = id;
    }
    /**
     * 得到组ID
     * @return int
     */
    public int getID()
    {
        return id;
    }
    /**
     * 设置组名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到组名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 设置标题模型
     * @param head EColumnModel
     */
    public void setHead(EColumnModel head)
    {
        this.head = head;
    }
    /**
     * 得到标题模型
     * @return EColumnModel
     */
    public EColumnModel getHead()
    {
        return head;
    }
    /**
     * 是否存在标题
     * @return boolean
     */
    public boolean hasHead()
    {
        return getHead() != null;
    }
    /**
     * 设置合模型
     * @param sum EColumnModel
     */
    public void setSum(EColumnModel sum)
    {
        this.sum = sum;
    }
    /**
     * 得到合计模型
     * @return EColumnModel
     */
    public EColumnModel getSum()
    {
        return sum;
    }
    /**
     * 是否存在合计
     * @return boolean
     */
    public boolean hasSum()
    {
        return getSum() != null;
    }
    /**
     * 设置是否显示数据
     * @param isShowData boolean
     */
    public void setShowData(boolean isShowData)
    {
        this.isShowData = isShowData;
    }
    /**
     * 是否显示数据
     * @return boolean
     */
    public boolean isShowData()
    {
        return isShowData;
    }
    /**
     * 清除列上关联宏
     */
    public void clearColumnMacroroutine()
    {
        if(hasHead())
            getHead().clearColumnMacroroutine();
        if(hasSum())
            getSum().clearColumnMacroroutine();
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s,int c)
      throws IOException
    {
        s.WO("<EGroupModel ID=" + getID() + " Name=" + getName() + " ShowData=" + isShowData() + " >", c);
        if(hasHead())
            getHead().writeObjectDebug(s,c+1);
        if(hasSum())
            getSum().writeObjectDebug(s,c+1);
        s.WO("</EGroupModel>", c);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(1,getID(),0);
        s.writeString(2,getName(),"");
        s.writeBoolean(3,isShowData(),false);
        if(hasHead())
        {
            s.writeShort(4);
            getHead().writeObject(s);
        }
        if(hasSum())
        {
            s.writeShort(5);
            getSum().writeObject(s);
        }
        s.writeShort( -1);
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
            switch(id)
            {
            case 1:
                setID(s.readInt());
                break;
            case 2:
                setName(s.readString());
                break;
            case 3:
                setShowData(s.readBoolean());
                break;
            case 4:
                setHead(new EColumnModel(getParent().getCTable()));
                getHead().readObject(s);
                break;
            case 5:
                setSum(new EColumnModel(getParent().getCTable()));
                getSum().readObject(s);
                break;
            }
            id = s.readShort();
        }
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        if(getHead() != null)
        {
            getHead().removeThis();
            setHead(null);
        }
        if(getSum() != null)
        {
            getSum().removeThis();
            setSum(null);
        }
    }
}
