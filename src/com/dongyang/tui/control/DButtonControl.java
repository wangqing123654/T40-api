package com.dongyang.tui.control;

/**
 *
 * <p>Title: ��ť������</p>
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
     * ����¼�
     */
    public void onClicked()
    {
        callFunction("close");
        System.out.println(getComponent().getTag() + " onClicked()");
    }
}
