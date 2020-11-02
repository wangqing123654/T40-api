package com.dongyang.tui.text;

/**
 *
 * <p>Title: TD模型</p>
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
     * 回复开关
     */
    //private boolean writeBackSwitch;
    /**
     * 构造器
     * @param td ETD
     */
    public ETDModel(ETD td)
    {
        setTD(td);
    }
    /**
     * 设置TD
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }
    /**
     * 得到TD
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
    }
    /**
     * 设置回复开关
     * @param writeBackSwitch boolean
     */
    /*public void setWriteBackSwitch(boolean writeBackSwitch)
    {
        this.writeBackSwitch = writeBackSwitch;
    }*/
    /**
     * 是否开启回复开关
     * @return boolean
     */
    /*public boolean isWriteBackSwitch()
    {
        return writeBackSwitch;
    }*/
    /**
     * 数据同步
     * @param b boolean true 传送TD false 回收TD
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
        //是否开启回复开关
        if(!isWriteBackSwitch())
            return;
        getSyntax().setSyntax(getTD().getString());
    }*/
}
