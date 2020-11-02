package test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;
import com.dongyang.jdo.TDataStore;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TIOM_AppServer;

public class A1
{
    public static void main(String args[])
    {
        TSocket socket = new TSocket("192.168.1.105",8080,"web");
        TIOM_AppServer.SOCKET = socket;
        com.dongyang.util.TDebug.initClient();
        /*TDataStore ds = new TDataStore();
        ds.setSQL("select * from sys_dept");
        ds.retrieve();
        ds.showDebug();
        /*ds.setItem(0,"OPT_TERM","aaa");
        ds.showDebug();
        ds.update();
        ds.showDebug();

       ds.setFilter("OPT_TERM='aaa'");
       ds.filter();
       ds.showDebug();*/
    }
}
