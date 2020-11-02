package com.dongyang.tui.text;

/**
 *
 * <p>Title: ������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.12
 * @version 1.0
 */
public class EAction
{
    public static final int FONT_NAME = 0x01;
    public static final int FONT_SIZE = 0x02;
    public static final int FONT_BOLD = 0x03;
    public static final int FONT_ITALIC = 0x04;
    public static final int DELETE = 0x05;
    public static final int COPY = 0x06;
    public static final int PASTE = 0x07;
    public static final int CUT = 0x08;


    /**
     * ����״̬����
     */
    public static final int PREVIEW_STATE = 0x100;
    /**
     * �༭״̬����
     */
    public static final int EDIT_STATE = 0x101;
    /**
     * ���ú�ֵ
     */
    public static final int SET_MICRO_FIELD = 0x102;
    /**
     * �����Ա�����
     */
    public static final int SET_SEX_CONTROL = 0x103;
    /**
     * �̶��ı�����ץȡ����
     */
    public static final int FIXED_TRY_RESET = 0x104;

    /**
     * ��������
     */
    private int type;
    /**
     * ����
     */
    private Object[] value;
    /**
     * ������
     * @param type int
     * @param value Object[]
     */
    public EAction(int type,Object ...value)
    {
        setType(type);
        setValue(value);
    }
    /**
     * ���ö�������
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * �õ���������
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * ���ò���
     * @param value Object[]
     */
    public void setValue(Object[] value)
    {
        this.value = value;
    }
    /**
     * �õ�����
     * @return Object[]
     */
    public Object[] getValue()
    {
        return value;
    }
    /**
     * �õ�����
     * @param i int
     * @return int
     */
    public int getInt(int i)
    {
        return (Integer)value[i];
    }
    /**
     * �õ��ַ���
     * @param i int
     * @return String
     */
    public String getString(int i)
    {
        return (String)value[i];
    }
    /**
     * �õ�����
     * @param i int
     * @return boolean
     */
    public boolean getBoolean(int i)
    {
        return (Boolean)value[i];
    }
    /**
     * �õ���������
     * @param i int
     * @return CopyOperator
     */
    public CopyOperator getCopyOperator(int i)
    {
        if(value[i] == null || !(value[i] instanceof CopyOperator))
            return null;

        return (CopyOperator)value[i];
    }
    /**
     * �����к�
     */
    public void addRow()
    {
        CopyOperator copyOperator = getCopyOperator(0);
        if(copyOperator == null)
            return;
        copyOperator.addRow();
    }
    public void insertBlock()
    {
        CopyOperator copyOperator = getCopyOperator(0);
        if(copyOperator == null)
            return;
        copyOperator.insertBlock();
    }
    public void addBlock()
    {
        CopyOperator copyOperator = getCopyOperator(0);
        if(copyOperator == null)
            return;
        copyOperator.addBlock();
    }
}
