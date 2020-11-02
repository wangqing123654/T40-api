package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.ui.CTable;

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
public class EColumnModel
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
     * @param cTable CTable
     */
    public EColumnModel(CTable cTable)
    {
        components = new TList();
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
     * ������Ŀ
     * @param item EMacroroutineModel
     */
    public void add(EMacroroutineModel item)
    {
        components.add(item);
    }
    /**
     * ������Ŀ
     * @param index int
     * @param item EMacroroutineModel
     */
    public void add(int index,EMacroroutineModel item)
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
     * @param item EMacroroutineModel
     */
    public void remove(EMacroroutineModel item)
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
     * @return EMacroroutineModel
     */
    public EMacroroutineModel get(int index)
    {
        return (EMacroroutineModel)components.get(index);
    }
    /**
     * ������Ϲ�����
     */
    public void clearColumnMacroroutine()
    {
        for(int i = 0;i < size();i++)
            get(i).setMacroroutine(null);
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getCTable().getPM();
    }
    /**
     * �õ��������
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager()
    {
        return getPM().getMacroroutineManager();
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
            EMacroroutineModel item = get(i);
            if(item == null)
                continue;
            s.writeInt(item.findIndex());
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
            add(s.readInt());
    }
    /**
     * ɾ���Լ�
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
