package action.test;

import com.dongyang.data.TParm;

public class TestAction {
    public String toString()
    {
        return "TestAction";
    }
    public TParm test(TParm parm)
    {
        TParm result = new TParm();
        System.out.println("TestAction:" + parm);
        //result.setData("NEW","OK" + A1.VALUE);
        return result;
    }
}
