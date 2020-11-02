package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.util.TypeTool;
import com.dongyang.tui.DInsets;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: Table ģ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.10
 * @version 1.0
 */
public class ETableModel
{
    /**
     * �п��
     */
    private TList columnWidths;
    /**
     * Table
     */
    private ETable table;
    /**
     * ������
     */
    public ETableModel()
    {
        columnWidths = new TList();
    }
    /**
     * ������
     * @param width int
     */
    public void add(int width)
    {
        columnWidths.add(width);
    }
    /**
     * ������
     * @param index int
     * @param width int
     */
    public void add(int index,int width)
    {
        columnWidths.add(index,width);
    }
    /**
     * ɾ����
     * @param index int
     */
    public void remove(int index)
    {
        columnWidths.remove(index);
    }
    /**
     * �õ��п��
     * @param index int
     * @return int
     */
    public int get(int index)
    {
        return TypeTool.getInt(columnWidths.get(index));
    }
    /**
     * ���ÿ��
     * @param index int
     * @param x int
     */
    public void set(int index,int x)
    {
        columnWidths.set(index,x);
    }
    /**
     * �õ�����
     * @return int
     */
    public int size()
    {
        return columnWidths.size();
    }
    /**
     * ����Table
     * @param table ETable
     */
    public void setTable(ETable table)
    {
        this.table = table;
    }
    /**
     * �õ�Table
     * @return ETable
     */
    public ETable getTable()
    {
        return table;
    }
    /**
     * ��ʼ������
     * @param count int
     */
    public void init(int count)
    {
        columnWidths.removeAll();
        if(getTable() == null)
            return;
        DInsets insets = getTable().getTRInsets();
        int width = getTable().getInsetsWidth() - insets.left - insets.right;
        int w = getTable().getWSpace();
        int tdW = (int)(((double)width - (double)w * (double)(count - 1)) / (double)count + 0.5);
        int endW = width - (tdW + w) * (count - 1);
        for(int i = 0;i < count - 1;i++)
            add(tdW);
        add(endW);
    }
    /**
     * ɾ��ȫ��������
     */
    public void removeDataRow()
    {
        removeDataRow(ETRModel.COLUMN_EDIT);
        removeDataRow(ETRModel.COLUMN_TYPE);
        removeDataRow(ETRModel.GROUP_HEAD_EDIT);
        removeDataRow(ETRModel.GROUP_SUM_EDIT);
        removeDataRow(ETRModel.GROUP_HEAD);
        removeDataRow(ETRModel.GROUP_SUM);
    }
    /**
     * ɾ��������
     * @param type int ����
     */
    public void removeDataRow(int type)
    {
        ETable table = getTable().getHeadTable();
        while(table != null)
        {
            for(int i = table.size() - 1;i >= 0;i--)
            {
                ETR tr = table.get(i);
                if (tr != null && tr.getModel() != null &&
                    tr.getModel().getType() == type)
                    table.remove(tr);
            }
            ETable nextTable = table.getNextTable();
            if(table.size() == 0)
                table.removeThis();
            table = nextTable;
        }
    }
    public String toString()
    {
        return "<ETableModel size=" + size() +
                ",columnWidths=" + columnWidths + ">";
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
        s.WO("<ETableModel>",c);
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i ++)
        {
            if(sb.length() > 0)
                sb.append(";");
            sb.append(get(i));
        }
        if(sb.length() > 0)
            s.WO(sb.toString(),c + 1);
        s.WO("</ETableModel>",c);
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
        //����ҳ
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            s.writeInt(get(i));
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
        //��ȡ��
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            add(s.readInt());
        }
    }
    public ETableModel clone(ETable table)
    {
        ETableModel model = new ETableModel();
        model.setTable(table);
        for(int i = 0;i < size();i++)
        {
            int x = get(i);
            model.add(x);
        }
        return model;
    }
}
