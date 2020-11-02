package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.tui.DText;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: Table控制器管理类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MCTable
{
    /**
     * 成员列表
     */
    private TList components;

    /**
     * 管理器
     */
    private PM pm;

    /**
     * 构造器
     */
    public MCTable()
    {
        components = new TList();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
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
     * @param cTable CTable
     */
    public void add(CTable cTable)
    {
        if (cTable == null)
            return;
        components.add(cTable);
        cTable.setCTableManager(this);
    }
    /**
     * 查找位置
     * @param item CTable
     * @return int
     */
    public int indexOf(CTable item)
    {
        if (item == null)
            return -1;
        return components.indexOf(item);
    }

    /**
     * 删除成员
     * @param cTable CTable
     */
    public void remove(CTable cTable)
    {
        components.remove(cTable);
        cTable.removeThis();
    }
    /**
     * 全部清除
     */
    public void removeAll()
    {
        for(int i = size() - 1;i >= 0;i--)
            remove(get(i));
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
     * @return CTable
     */
    public CTable get(int index)
    {
        return (CTable) components.get(index);
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
     * 设置父类
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
    }
    /**
     * 得到父类
     * @return EComponent
     */
    public EComponent getParent()
    {
        return null;
    }
    /**
     * 新建Table控制器
     * @param etable ETable
     * @return CTable
     */
    public CTable newCTable(ETable etable)
    {
        CTable ctable = new CTable(etable);
        add(ctable);
        //getUI().addDComponent(ctable);
        return ctable;
    }
    /**
     * 新建Table控制器
     * @return CTable
     */
    public CTable newCTable()
    {
        CTable ctable = new CTable();
        add(ctable);
        //getUI().addDComponent(ctable);
        return ctable;
    }
    /**
     * 纵向滚动条值改变
     * @param value int
     */
    public void onVScrollBarChangeValue(int value)
    {
        for(int i = 0;i < size();i++)
        {
            CTable table = get(i);
            if(table == null)
                continue;
            table.onVScrollBarChangeValue(value);
        }
    }
    /**
     * 清除全部显示数据
     */
    public void clearShowData()
    {
        for(int i = 0;i < size();i++)
            get(i).clearShowData();
    }
    /**
     * 显示全部数据
     */
    public void showData()
    {
    	//System.out.println("size============"+size());
        for(int i = 0;i < size();i++)
            get(i).showData();
    }
    /**
     * 查询数据
     */
    public void retrieve()
    {
        for(int i = 0;i < size();i++)
            get(i).retrieve();
    }
    /**
     * 显示编辑
     */
    public void showEdit()
    {
        for(int i = 0;i < size();i++)
            get(i).showEdit();
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
        s.WO("<MCTable>",c);
        for(int i = 0;i < size();i ++)
        {
            CTable item = get(i);
            if(item == null)
                continue;
            item.writeObjectDebug(s,c + 1);
        }
        s.WO("</MCTable>",c);
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
            CTable item = get(i);
            if(item == null)
                continue;
            item.writeObject(s);
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
        //读取Table控制器
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            CTable item = newCTable();
            item.readObject(s);
        }
    }
}
