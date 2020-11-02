package com.dongyang.tui.control;

/**
 *
 * <p>Title: 窗体控制类</p>
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
public class DFrameControl extends DComponentControl
{
    /**
     * 打开事件
     * @return boolean
     */
    public boolean onOpen()
    {
        getComponent("button1").setText("EDIT");
        System.out.println("onOpen");
        return true;
    }
    /**
     * 关闭事件
     * @return boolean true 关闭窗口 false 不关闭窗口
     */
    public boolean onClose()
    {
        System.out.println("onClose");
        return true;
    }
    /**
     * 关闭之后事件
     */
    public void onClosed()
    {

    }
}
