package test;

import com.dongyang.control.TControl;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.ui.event.TMouseListener;

public class TestTreeControl extends TControl{
    /**
     * ³õÊ¼»¯
     */
    public void onInit() {
        addEventListener(getUITag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED, this,"onClick");
        //addEventListener("Close->" + TActionListener.ACTION_PERFORMED, this,"onClose");
    }
    /*public Object callMessage(String message,Object parm)
    {
        Object value = super.callMessage(message,parm);
        System.out.println("Tree Control message=" + message);
        return value;
    }*/
    public void onClick()
    {
        System.out.println("onClick");
        callMessage("UI|AAA|setEnabled|N");
    }
}
