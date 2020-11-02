package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: �޸ļ�¼������</p>
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
     * ������
     */
    private PM pm;
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ��ǰ����
     */
    private int index = -1;
    /**
     * ������
     */
    public MModifyNode()
    {
        components = new TList();
    }
    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * ���ӳ�Ա
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
     * ɾ����Ա
     * @param inf EModifyInf
     */
    public void remove(EModifyInf inf)
    {
        components.remove(inf);
    }
    /**
     * ��Ա����
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * �õ���Ա
     * @param index int
     * @return EModifyInf
     */
    public EModifyInf get(int index)
    {
        return (EModifyInf) components.get(index);
    }
    /**
     * ����λ��
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
     * �õ���Ա�б�
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * ���õ�ǰ����
     * @param index int
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    /**
     * �õ���ǰ����
     * @return int
     */
    public int getIndex()
    {
        return index;
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
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
            get(i).writeObject(s);
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
        components = new TList();
        //��ȡ��
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EModifyInf inf = new EModifyInf();
            inf.readObject(s);
            add(inf);
        }
    }
    /**
     * д��������
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
     * ����������
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
