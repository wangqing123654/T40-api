package com.dongyang.root.T.O;

/**
 * <p>Title: ��ĸ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.6
 * @version 1.0
 */
public class C
        implements java.io.Serializable
{
    /**
     * ����
     */
    public final static int CHINESE = 1;
    /**
     * ��ĸ
     */
    public final static int CHAR = 2;
    /**
     * ����
     */
    public final static int NUMBER = 3;
    /**
     * ����
     */
    public final static int OPERATOR = 4;
    /**
     * ��
     */
    public final static int WORD = 5;
    /**
     * ��
     */
    public final static int NULL = -1;
    /**
     * �޷�ȷ��
     */
    public final static int NO = 0;
    /**
     * �ַ�
     */
    private String c;
    /**
     * ����
     */
    private int type = NULL;
    /**
     * ������
     */
    public C()
    {

    }
    /**
     * ������
     * @param c String
     */
    public C(String c)
    {
        setC(c);
    }
    /**
     * �����ַ�
     * @param c String
     */
    public void setC(String c)
    {
        this.c = c;
        setType(LX(c));
    }
    /**
     * �õ��ַ�
     * @return String
     */
    public String getC()
    {
        return c;
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
     * �õ�����
     * @return String
     */
    public String getTypeString()
    {
        return getTypeString(type);
    }
    /**
     * �õ���������
     * @param type int
     * @return String
     */
    public static String getTypeString(int type)
    {
        switch(type)
        {
        case CHINESE:
            return "����";
        case CHAR:
            return "��ĸ";
        case NUMBER:
            return "����";
        case OPERATOR:
            return "����";
        case WORD:
            return "��";
        }
        return "�޷�ȷ��";
    }
    /**
     * �ж��ַ�����
     * @param s String
     * @return int
     */
    public static int LX(String s)
    {
        if(s == null || s.length() == 0)
            return NULL;
        if(s.length() > 1)
            return WORD;
        char c = s.charAt(0);
        if(c >= '0' && c <= '9')
            return NUMBER;
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            return CHAR;
        if(c > 255)
            return CHINESE;
        return NO;
    }
    public String toString()
    {
        return getC() + ">>" + getTypeString();
    }
}
