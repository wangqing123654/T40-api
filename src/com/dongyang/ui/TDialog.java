package com.dongyang.ui;

import com.dongyang.config.TConfigParm;
import com.dongyang.ui.base.TDialogBase;
import javax.swing.UIManager;

public class TDialog extends TDialogBase{

    public static void main(String args[])
    {
        try{
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception e)
        {
        }
        TConfigParm parm = new TConfigParm();
        parm.setSystemGroup("TEDA");
        //parm.setConfig("D:\\Project\\client\\aaa.ini");
        parm.setConfig("datawindow.x");
        parm.setConfigColor("D:\\Project\\client\\Color.ini");
        parm.setConfigClass("D:\\Project\\client\\class.ini");

        TDialog frame = new TDialog();
        frame.init(parm);
        frame.open();
    }
}
