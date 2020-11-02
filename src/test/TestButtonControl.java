package test;

import com.dongyang.control.TControl;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.util.StringTool;

public class TestButtonControl extends TControl{
    public void onInit()
    {
        addEventListener(getComponent().getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
    }
    public Object callMessage(String message,Object parm)
    {
        Object value = super.callMessage(message,parm);
        return value;
    }
    public void onAction()
    {
        System.out.println("TestButtonControl=" + getComponent().callMessage("getINIString|getText"));
        //getComponent().callMessage("getINIString|setEnabled|" +
        //(!StringTool.getBoolean(""+getComponent().callMessage("getINIString|isEnabled"))));
        //getComponent().callMessage("onClose");
        getComponent().callMessage("onReset");
        System.out.println("cccccc");
    }
}
