package com.dongyang.tui.text;

/**
 *
 * <p>Title: �ı�������ýӿ���չ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.21
 * @version 1.0
 */
public abstract class ETextSpecialIO extends ETextBase
{
    /**
     * ɾ��ָ������������չ�ӿ�
     * @param start int ��ʼλ��
     * @param end int ����λ��
     * @return boolean true ��ֹ�������� false �����׼����
     */
    public boolean onDeleteActionIO(int start,int end)
    {
        return false;
    }
    /**
     * �õ���ʾ�ִ�
     * @return String
     */
    public String getShowString()
    {
        return getString();
    }
    /**
     * �õ���ʾ�ַ��ĳ���
     * @return int
     */
    public int getShowStringWidth()
    {
        return getStyle().stringWidth(getShowString());
    }
    /**
     * �ܷ���
     * @return boolean
     */
    public boolean canSeparate()
    {
        return true;
    }
    /**
     * �õ���ʾ�����Ѩ���
     * @return int
     */
    public int getShowLeftW()
    {
        return 0;
    }
    /**
     * �õ���ʾ�����Ѩ���
     * @return int
     */
    public int getShowRightW()
    {
        return 0;
    }
}
