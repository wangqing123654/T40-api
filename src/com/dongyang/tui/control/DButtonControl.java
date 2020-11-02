package com.dongyang.tui.control;

/**
 *
 * <p>Title: 按钮控制类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.7
 * @version 1.0
 */
public class DButtonControl extends DComponentControl
{
    /**
     * 点击事件
     */
    public void onClicked()
    {
        callFunction("close");
        System.out.println(getComponent().getTag() + " onClicked()");
    }
}
