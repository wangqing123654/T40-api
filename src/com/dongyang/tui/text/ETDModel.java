package com.dongyang.tui.text;

/**
 *
 * <p>Title: TDģ��</p>
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
public class ETDModel
{
    /**
     * TD
     */
    private ETD td;
    /**
     * �ظ�����
     */
    //private boolean writeBackSwitch;
    /**
     * ������
     * @param td ETD
     */
    public ETDModel(ETD td)
    {
        setTD(td);
    }
    /**
     * ����TD
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }
    /**
     * �õ�TD
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
    }
    /**
     * ���ûظ�����
     * @param writeBackSwitch boolean
     */
    /*public void setWriteBackSwitch(boolean writeBackSwitch)
    {
        this.writeBackSwitch = writeBackSwitch;
    }*/
    /**
     * �Ƿ����ظ�����
     * @return boolean
     */
    /*public boolean isWriteBackSwitch()
    {
        return writeBackSwitch;
    }*/
    /**
     * ����ͬ��
     * @param b boolean true ����TD false ����TD
     */
    /*public void update(boolean b)
    {
        if(getSyntax() == null)
            return;
        if(b)
        {
            getTD().setString(getSyntax().getSyntax());
            return;
        }
        //�Ƿ����ظ�����
        if(!isWriteBackSwitch())
            return;
        getSyntax().setSyntax(getTD().getString());
    }*/
}
