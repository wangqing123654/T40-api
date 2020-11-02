package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.ui.CTable;

/**
 *
 * <p>Title: 列模型</p>
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
public class EColumnModel
{
    /**
     * Table控制类
     */
    private CTable cTable;
    /**
     * 列组件
     */
    private TList components;
    /**
     * 构造器
     * @param cTable CTable
     */
    public EColumnModel(CTable cTable)
    {
        components = new TList();
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
     * @param index int
     */
    public void add(int index)
    {
        EMacroroutineModel mode = getMacroroutineManager().get(index);
        if(mode == null)
            return;
        add(mode);
    }
    /**
     * 增加项目
     * @param item EMacroroutineModel
     */
    public void add(EMacroroutineModel item)
    {
        components.add(item);
    }
    /**
     * 增加项目
     * @param index int
     * @param item EMacroroutineModel
     */
    public void add(int index,EMacroroutineModel item)
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
     * @param item EMacroroutineModel
     */
    public void remove(EMacroroutineModel item)
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
     * @return EMacroroutineModel
     */
    public EMacroroutineModel get(int index)
    {
        return (EMacroroutineModel)components.get(index);
    }
    /**
     * 清除列上关联宏
     */
    public void clearColumnMacroroutine()
    {
        for(int i = 0;i < size();i++)
            get(i).setMacroroutine(null);
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getCTable().getPM();
    }
    /**
     * 得到宏控制器
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager()
    {
        return getPM().getMacroroutineManager();
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
        s.WO("<EColumnModel>", c);
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i ++)
        {
            EMacroroutineModel item = get(i);
            if(sb.length() > 0)
                sb.append(",");
            sb.append(item.findIndex());
        }
        s.WO(sb.toString(), c + 1);
        s.WO("</EColumnModel>", c);
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
            EMacroroutineModel item = get(i);
            if(item == null)
                continue;
            s.writeInt(item.findIndex());
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
        //读取宏模型
        int count = s.readInt();
        for(int i = 0;i < count;i++)
            add(s.readInt());
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EMacroroutineModel item = get(i);
            if(item == null)
                continue;
            item.removeThis();
            remove(item);
        }
    }
}
