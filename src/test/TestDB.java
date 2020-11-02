package test;

import com.dongyang.util.TDebug;
import com.dongyang.jdo.ServerInf;
import com.dongyang.jdo.TJDODBTool;
import java.util.Date;
import com.dongyang.data.TParm;

public class TestDB
{
    public static void main(String args[])
    {
        TDebug.initClient();
        System.out.println(ServerInf.getInstance().getDataBasePoolInf());
        TParm parm = new TParm(ServerInf.getInstance().getUserTime("javahis"));
        int count = parm.getCount("TConnection");
        for(int i = 0;i < count;i++)
            System.out.println(parm.getValue("TConnection",i));
        //System.out.println(new Date().getTime());
    }
}
