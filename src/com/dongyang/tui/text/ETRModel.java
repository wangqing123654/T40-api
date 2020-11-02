package com.dongyang.tui.text;

import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 行模型</p>
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
     * 默认类型
     */
    public final static int DEFAULT_TYPE = 0;
    /**
     * 列类型
     */
    public final static int COLUMN_TYPE = 1;
    /**
     * 列编辑
     */
    public final static int COLUMN_EDIT = 2;
    /**
     *  总计列
     */
    public final static int COLUMN_MAX = 3;
    /**
     * 组标题
     */
    public final static int GROUP_HEAD = 4;
    /**
     * 组合计
     */
    public final static int GROUP_SUM = 5;
    /**
     * 组标题编辑
     */
    public final static int GROUP_HEAD_EDIT = 6;
    /**
     * 组合计编辑
     */
    public final static int GROUP_SUM_EDIT = 7;
    /**
     * TR
     */
    private ETR tr;
    /**
     * 类型
     */
    private int type;
    /**
     * 行号
     */
    private int row = -1;
    /**
     * 结束行号
     */
    private int endRow = -1;
    /**
     * 组编号
     */
    private int groupID = -1;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 构造器
     * @param tr ETR
     */
    public ETRModel(ETR tr)
    {
        setTR(tr);
    }
    /**
     * 设置TR
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * 得到TR
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
    }
    /**
     * 设置类型
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * 设置行
     * @param row int
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    /**
     * 得到行
     * @return int
     */
    public int getRow()
    {
        return row;
    }
    /**
     * 设置结束行
     * @param endRow int
     */
    public void setEndRow(int endRow)
    {
        this.endRow = endRow;
    }
    /**
     * 得到结束行
     * @return int
     */
    public int getEndRow()
    {
        return endRow;
    }
    /**
     * 设置组编号
     * @param groupID int
     */
    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }
    /**
     * 得到组编号
     * @return int
     */
    public int getGroupID()
    {
        return groupID;
    }
    /**
     * 设置组名
     * @param groupName String
     */
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    /**
     * 得到组名
     * @return String
     */
    public String getGroupName()
    {
        return groupName;
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
        s.WO("<ETRModel Type=" + getType() + " Row=" + getRow() +
             " EndRow=" + getEndRow() + " GroupID=" + getGroupID() + " GroupName=" + getGroupName() + " >",c);
        s.WO("</ETRModel>",c);
    }
    /**
     * 写对象
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
