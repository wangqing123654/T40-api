package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.text.ui.CTable;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: ��ģ���б�</p>
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
     * Table������
     */
    private CTable cTable;
    /**
     * �����
     */
    private TList components;
    /**
     * ������
     */
    public EGroupModelList()
    {
        components = new TList();
    }
    /**
     * ������
     * @param cTable CTable
     */
    public EGroupModelList(CTable cTable)
    {
        this();
        setCTable(cTable);
    }
    /**
     * ����Table������
     * @param cTable CTable
     */
    public void setCTable(CTable cTable)
    {
        this.cTable = cTable;
    }
    /**
     * �õ�Table������
     * @return CTable
     */
    public CTable getCTable()
    {
        return cTable;
    }
    /**
     * ������Ŀ
     * @param item EGroupModel
     */
    public void add(EGroupModel item)
    {
        components.add(item);
    }
    /**
     * ������Ŀ
     * @param index int
     * @param item EGroupModel
     */
    public void add(int index,EGroupModel item)
    {
        components.add(index,item);
    }
    /**
     * ɾ����Ŀ
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
    }
    /**
     * ɾ����Ŀ
     * @param item EGroupModel
     */
    public void remove(EGroupModel item)
    {
        components.remove(item);
    }
    /**
     * �õ���Ŀ����
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * �õ���Ŀ
     * @param index int
     * @return EGroupModel
     */
    public EGroupModel get(int index)
    {
        return (EGroupModel)components.get(index);
    }
    /**
     * ������Ϲ�����
     */
    public void clearColumnMacroroutine()
    {
        for(int i = 0;i < size();i++)
            get(i).clearColumnMacroroutine();
    }
    /**
     * �õ���У��ֵ�б�
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
     * �½���ģ��
     * @return EGroupModel
     */
    public EGroupModel newGroupModel()
    {
        EGroupModel model = new EGroupModel(this);
        add(model);
        return model;
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
            EGroupModel model = get(i);
            if(model == null)
                continue;
            model.writeObject(s);
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
        //��ȡ��ģ��
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EGroupModel model = newGroupModel();
            model.readObject(s);
        }
    }
    /**
     * ɾ���Լ�
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
