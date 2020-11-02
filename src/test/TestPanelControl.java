package test;

import com.dongyang.control.TControl;

public class TestPanelControl extends TControl{
    public Object callMessage(String message,Object parm)
    {
        Object value = super.callMessage(message,parm);
        System.out.println("TestPanelControl message=" + message);
        return value;
    }
}
