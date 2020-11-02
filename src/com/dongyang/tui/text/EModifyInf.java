package com.dongyang.tui.text;

import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 修改记录信息</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: javaHis</p>
 *
 * @author lzk 2010.2.1
 * @version 1.0
 */
public class EModifyInf
{
    /**
     * 父类
     */
    private MModifyNode parent;
    /**
     * 编号
     */
    private int ID;
    /**
     * 修改级别
     */
    private int level;
    /**
     * 用户ID
     */
    private String userID;
    /**
     * 修改人
     */
    private String userName;
    /**
     * 修改日期
     */
    private String modifyDate;
    /**
     * 设置编号
     * @param ID int
     */
    public void setID(int ID)
    {
        this.ID = ID;
    }
    /**
     * 得到编号
     * @return int
     */
    public int getID()
    {
        return ID;
    }
    /**
     * 设置修改级别
     * @param level int
     */
    public void setLevel(int level)
    {
        this.level = level;
    }
    /**
     * 得到修改级别
     * @return int
     */
    public int getLevel()
    {
        return level;
    }
    /**
     * 设置用户ID
     * @param userID String
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    /**
     * 得到用户ID
     * @return String
     */
    public String getUserID()
    {
        return userID;
    }
    /**
     * 设置用户名
     * @param userName String
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    /**
     * 得到用户名
     * @return String
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * 设置修改日期
     * @param modifyDate String
     */
    public void setModifyDate(String modifyDate)
    {
        this.modifyDate = modifyDate;
    }
    /**
     * 得到修改日期
     * @return String
     */
    public String getModifyDate()
    {
        return modifyDate;
    }
    /**
     * 设置父类
     * @param parent MModifyNode
     */
    public void setParent(MModifyNode parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return MModifyNode
     */
    public MModifyNode getParent()
    {
        return parent;
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
        s.writeInt(0);
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
        //读取行
        int count = s.readInt();
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeInt(1,getID(),0);
        s.writeInt(2,getLevel(),0);
        s.writeString(3,getUserID(),null);
        s.writeString(4,getUserName(),null);
        s.writeString(5,getModifyDate(),null);
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
        switch (id)
        {
        case 1:
            setID(s.readInt());
            return true;
        case 2:
            setLevel(s.readInt());
            return true;
        case 3:
            setUserID(s.readString());
            return true;
        case 4:
            setUserName(s.readString());
            return true;
        case 5:
            setModifyDate(s.readString());
            return true;
        }
        return false;
    }
}
