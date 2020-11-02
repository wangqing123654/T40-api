package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.tui.DText;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: Table������������</p>
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
     * ��Ա�б�
     */
    private TList components;

    /**
     * ������
     */
    private PM pm;

    /**
     * ������
     */
    public MCTable()
    {
        components = new TList();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
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
     * ����λ��
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
     * ɾ����Ա
     * @param cTable CTable
     */
    public void remove(CTable cTable)
    {
        components.remove(cTable);
        cTable.removeThis();
    }
    /**
     * ȫ�����
     */
    public void removeAll()
    {
        for(int i = size() - 1;i >= 0;i--)
            remove(get(i));
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
     * @return CTable
     */
    public CTable get(int index)
    {
        return (CTable) components.get(index);
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
     * ���ø���
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getParent()
    {
        return null;
    }
    /**
     * �½�Table������
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
     * �½�Table������
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
     * ���������ֵ�ı�
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
     * ���ȫ����ʾ����
     */
    public void clearShowData()
    {
        for(int i = 0;i < size();i++)
            get(i).clearShowData();
    }
    /**
     * ��ʾȫ������
     */
    public void showData()
    {
    	//System.out.println("size============"+size());
        for(int i = 0;i < size();i++)
            get(i).showData();
    }
    /**
     * ��ѯ����
     */
    public void retrieve()
    {
        for(int i = 0;i < size();i++)
            get(i).retrieve();
    }
    /**
     * ��ʾ�༭
     */
    public void showEdit()
    {
        for(int i = 0;i < size();i++)
            get(i).showEdit();
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
     * д����
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
            id = s.readShort();
        }
        //��ȡTable������
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            CTable item = newCTable();
            item.readObject(s);
        }
    }
}
