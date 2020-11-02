package com.dongyang.tui.text;

import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: �޸ļ�¼��Ϣ</p>
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
     * ����
     */
    private MModifyNode parent;
    /**
     * ���
     */
    private int ID;
    /**
     * �޸ļ���
     */
    private int level;
    /**
     * �û�ID
     */
    private String userID;
    /**
     * �޸���
     */
    private String userName;
    /**
     * �޸�����
     */
    private String modifyDate;
    /**
     * ���ñ��
     * @param ID int
     */
    public void setID(int ID)
    {
        this.ID = ID;
    }
    /**
     * �õ����
     * @return int
     */
    public int getID()
    {
        return ID;
    }
    /**
     * �����޸ļ���
     * @param level int
     */
    public void setLevel(int level)
    {
        this.level = level;
    }
    /**
     * �õ��޸ļ���
     * @return int
     */
    public int getLevel()
    {
        return level;
    }
    /**
     * �����û�ID
     * @param userID String
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    /**
     * �õ��û�ID
     * @return String
     */
    public String getUserID()
    {
        return userID;
    }
    /**
     * �����û���
     * @param userName String
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    /**
     * �õ��û���
     * @return String
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * �����޸�����
     * @param modifyDate String
     */
    public void setModifyDate(String modifyDate)
    {
        this.modifyDate = modifyDate;
    }
    /**
     * �õ��޸�����
     * @return String
     */
    public String getModifyDate()
    {
        return modifyDate;
    }
    /**
     * ���ø���
     * @param parent MModifyNode
     */
    public void setParent(MModifyNode parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return MModifyNode
     */
    public MModifyNode getParent()
    {
        return parent;
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //д��������
        writeObjectAttribute(s);
        s.writeShort( -1);
        //�����ڲ�����
        s.writeInt(0);
    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //����������
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //��ȡ��
        int count = s.readInt();
    }
    /**
     * д��������
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
     * ����������
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
