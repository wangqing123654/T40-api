package test;

import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.data.TSocket;

public class Reset
{
    public static void main(String args[])
    {
        if(args.length < 3)
        {
            System.out.println("Reset IP port root");
            return;
        }
        String IP = args[0];
        int port = Integer.parseInt(args[1]);
        String root = args[2];

        TSocket socket = new TSocket(IP,port,root);
        if(TIOM_AppServer.resetAction(socket))
            System.out.println("OK");
    }
}
