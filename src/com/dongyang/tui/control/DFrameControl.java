package com.dongyang.tui.control;

/**
 *
 * <p>Title: ���������</p>
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
     * ���¼�
     * @return boolean
     */
    public boolean onOpen()
    {
        getComponent("button1").setText("EDIT");
        System.out.println("onOpen");
        return true;
    }
    /**
     * �ر��¼�
     * @return boolean true �رմ��� false ���رմ���
     */
    public boolean onClose()
    {
        System.out.println("onClose");
        return true;
    }
    /**
     * �ر�֮���¼�
     */
    public void onClosed()
    {

    }
}
