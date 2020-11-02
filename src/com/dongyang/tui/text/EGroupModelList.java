package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.text.ui.CTable;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 组模型列表</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.14
 * @version 1.0
 */
public class EGroupModelList
{
    /**
     * Table控制类
     */
    private CTable cTable;
    /**
     * 组组件
     */
    private TList components;
    /**
     * 构造器
     */
    public EGroupModelList()
    {
        components = new TList();
    }
    /**
     * 构造器
     * @param cTable CTable
     */
    public EGroupModelList(CTable cTable)
    {
        this();
        setCTable(cTable);
    }
    /**
     * 设置Table控制类
     * @param cTable CTable
     */
    public void setCTable(CTable cTable)
    {
        this.cTable = cTable;
    }
    /**
     * 得到Table控制类
     * @return CTable
     */
    public CTable getCTable()
    {
        return cTable;
    }
    /**
     * 增加项目
     * @param item EGroupModel
     */
    public void add(EGroupModel item)
    {
        components.add(item);
    }
    /**
     * 增加项目
     * @param index int
     * @param item EGroupModel
     */
    public void add(int index,EGroupModel item)
    {
        components.add(index,item);
    }
    /**
     * 删除项目
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
    }
    /**
     * 删除项目
     * @param item EGroupModel
     */
    public void remove(EGroupModel item)
    {
        components.remove(item);
    }
    /**
     * 得到项目个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * 得到项目
     * @param index int
     * @return EGroupModel
     */
    public EGroupModel get(int index)
    {
        return (EGroupModel)components.get(index);
    }
    /**
     * 清除列上关联宏
     */
    public void clearColumnMacroroutine()
    {
        for(int i = 0;i < size();i++)
            get(i).clearColumnMacroroutine();
    }
    /**
     * 得到组校验值列表
     * @return TList
     */
    public TList createValueList()
    {
        TList list = new TList();
        for(int i = 0;i < size();i++)
            list.add(new Object());
        return list;
    }
    /**
     * 新建组模型
     * @return EGroupModel
     */
    public EGroupModel newGroupModel()
    {
        EGroupModel model = new EGroupModel(this);
        add(model);
        return model;
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
        s.WO("<EGroupModelList>", c);
        for(int i = 0;i < size();i ++)
        {
            EGroupModel model = get(i);
            if(model == null)
                continue;
            model.writeObjectDebug(s,c + 1);
        }
        s.WO("</EGroupModelList>", c);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeShort( -1);
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            EGroupModel model = get(i);
            if(model == null)
                continue;
            model.writeObject(s);
        }
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
            id = s.readShort();
        }
        //读取组模型
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EGroupModel model = newGroupModel();
            model.readObject(s);
        }
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EGroupModel model = get(i);
            if(model == null)
                continue;
            model.removeThis();
            remove(model);
        }
    }
}
