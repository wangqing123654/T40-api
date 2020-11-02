package test;

import com.dongyang.control.TChildControl;

public class TestChildControl extends TChildControl{
    public Object callMessage(String message,Object parm)
    {
        Object value = super.callMessage(message,parm);
        //System.out.println("TestChildControl message=" + message);
        return value;
    }

}
