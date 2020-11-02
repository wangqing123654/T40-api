package test;

import com.dongyang.control.TControl;
import com.dongyang.ui.event.TActionListener;

public class TestFrameControl extends TControl{
    /**
     * ³õÊ¼»¯
     */
    public void onInit() {
        addEventListener("Open->" + TActionListener.ACTION_PERFORMED, this,"onOpen");
        addEventListener("Close->" + TActionListener.ACTION_PERFORMED, this,"onClose");
    }
    public Object callMessage(String message,Object parm)
    {
        Object value = super.callMessage(message,parm);
        System.out.println("TestFrameControl message=" + message);
        return value;
    }
    public void onOpen()
    {
        //callMessage("UI|Close|setEnabled|false");
        callMessage("UI|onReset");
    }
    public void onClose()
    {
        //callMessage("UI|Close|setVisible|false");
    }
}
