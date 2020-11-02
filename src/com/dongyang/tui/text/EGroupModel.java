package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: ��ģ��</p>
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
     * ����
     */
    EGroupModelList parent;
    /**
     * ��ID
     */
    private int id;
    /**
     * ������
     */
    private String name;
    /**
     * �����
     */
    private EColumnModel head;
    /**
     * ��ϼ�
     */
    private EColumnModel sum;
    /**
     * ��ʾData
     */
    private boolean isShowData;
    /**
     * ������
     * @param parent EGroupModelList
     */
    public EGroupModel(EGroupModelList parent)
    {
        setParent(parent);
    }
    /**
     * ���ø���
     * @param parent EGroupModelList
     */
    public void setParent(EGroupModelList parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return EGroupModelList
     */
    public EGroupModelList getParent()
    {
        return parent;
    }
    /**
     * ������ID
     * @param id int
     */
    public void setID(int id)
    {
        this.id = id;
    }
    /**
     * �õ���ID
     * @return int
     */
    public int getID()
    {
        return id;
    }
    /**
     * ����������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�������
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * ���ñ���ģ��
     * @param head EColumnModel
     */
    public void setHead(EColumnModel head)
    {
        this.head = head;
    }
    /**
     * �õ�����ģ��
     * @return EColumnModel
     */
    public EColumnModel getHead()
    {
        return head;
    }
    /**
     * �Ƿ���ڱ���
     * @return boolean
     */
    public boolean hasHead()
    {
        return getHead() != null;
    }
    /**
     * ���ú�ģ��
     * @param sum EColumnModel
     */
    public void setSum(EColumnModel sum)
    {
        this.sum = sum;
    }
    /**
     * �õ��ϼ�ģ��
     * @return EColumnModel
     */
    public EColumnModel getSum()
    {
        return sum;
    }
    /**
     * �Ƿ���ںϼ�
     * @return boolean
     */
    public boolean hasSum()
    {
        return getSum() != null;
    }
    /**
     * �����Ƿ���ʾ����
     * @param isShowData boolean
     */
    public void setShowData(boolean isShowData)
    {
        this.isShowData = isShowData;
    }
    /**
     * �Ƿ���ʾ����
     * @return boolean
     */
    public boolean isShowData()
    {
        return isShowData;
    }
    /**
     * ������Ϲ�����
     */
    public void clearColumnMacroroutine()
    {
        if(hasHead())
            getHead().clearColumnMacroroutine();
        if(hasSum())
            getSum().clearColumnMacroroutine();
    }
    /**
     * д����
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
     * д����
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
     * ɾ���Լ�
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
