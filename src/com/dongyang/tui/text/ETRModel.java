package com.dongyang.tui.text;

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
 * @author lzk 2009.4.11
 * @version 1.0
 */
public class ETRModel
{
    /**
     * Ĭ������
     */
    public final static int DEFAULT_TYPE = 0;
    /**
     * ������
     */
    public final static int COLUMN_TYPE = 1;
    /**
     * �б༭
     */
    public final static int COLUMN_EDIT = 2;
    /**
     *  �ܼ���
     */
    public final static int COLUMN_MAX = 3;
    /**
     * �����
     */
    public final static int GROUP_HEAD = 4;
    /**
     * ��ϼ�
     */
    public final static int GROUP_SUM = 5;
    /**
     * �����༭
     */
    public final static int GROUP_HEAD_EDIT = 6;
    /**
     * ��ϼƱ༭
     */
    public final static int GROUP_SUM_EDIT = 7;
    /**
     * TR
     */
    private ETR tr;
    /**
     * ����
     */
    private int type;
    /**
     * �к�
     */
    private int row = -1;
    /**
     * �����к�
     */
    private int endRow = -1;
    /**
     * ����
     */
    private int groupID = -1;
    /**
     * ����
     */
    private String groupName;
    /**
     * ������
     * @param tr ETR
     */
    public ETRModel(ETR tr)
    {
        setTR(tr);
    }
    /**
     * ����TR
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * �õ�TR
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
    }
    /**
     * ��������
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * ������
     * @param row int
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    /**
     * �õ���
     * @return int
     */
    public int getRow()
    {
        return row;
    }
    /**
     * ���ý�����
     * @param endRow int
     */
    public void setEndRow(int endRow)
    {
        this.endRow = endRow;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getEndRow()
    {
        return endRow;
    }
    /**
     * ��������
     * @param groupID int
     */
    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getGroupID()
    {
        return groupID;
    }
    /**
     * ��������
     * @param groupName String
     */
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getGroupName()
    {
        return groupName;
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
        s.WO("<ETRModel Type=" + getType() + " Row=" + getRow() +
             " EndRow=" + getEndRow() + " GroupID=" + getGroupID() + " GroupName=" + getGroupName() + " >",c);
        s.WO("</ETRModel>",c);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(1,getType(),0);
        s.writeInt(2,getRow(),0);
        s.writeInt(3,getEndRow(),0);
        s.writeInt(4,getGroupID(),0);
        s.writeString(5,getGroupName(),"");
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
                setType(s.readInt());
                break;
            case 2:
                setRow(s.readInt());
                break;
            case 3:
                setEndRow(s.readInt());
                break;
            case 4:
                setGroupID(s.readInt());
                break;
            case 5:
                setGroupName(s.readString());
                break;
            }
            id = s.readShort();
        }
    }
}
