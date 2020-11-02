package com.dongyang.server;

import java.io.ObjectOutputStream;
import com.dongyang.data.TParm;
import java.io.ObjectInputStream;
import java.util.Map;
import java.net.Socket;
import java.net.ServerSocket;
import com.dongyang.data.TSocket;
import java.util.HashMap;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.dongyang.util.FileTool;

public class ListenerServer implements Runnable
{
    private Socket socket;
    public ListenerServer(Socket socket) {
        this.socket = socket;
    }
    /**
     * 处理数据流
     */
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();//10.72.24.22
            String s = getHead(in);
            int x = 641;
            while(s != null && s.length() > 0)
            {
                //System.out.println(s);
                /*if(s.startsWith("Content-Length: "))
                {
                    x = Integer.parseInt(s.substring("Content-Length: ".length()));
                }*/
                s = getHead(in);
            }
            /*StringBuffer s1 = new StringBuffer(100);
            for(int i = 0;i < x;i++)
            {
                int t = in.read();
                 s1.append( (char) t);
            }
            System.out.println(s1);*/
            byte b[] = FileTool.getByte("D:\\lzk\\Project\\client\\x11.txt");
            //byte b[] = getData();
            //byte b[] = "APP Server buinding...".getBytes();
            out.write(b,0,b.length);
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getHead(InputStream input)
    {
      StringBuffer s = new StringBuffer(100);
      try
      {
        int t = input.read();
        while (t != 10) {
          if (t != 13)
            s.append( (char) t);
          t = input.read();
        }
      }catch(IOException e)
      {
          return null;
      }
      return s.toString();
    }
    public static void main(String args[]) {
        int port = 8011;
        if (args.length == 1)
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                try {
                    Socket socket = server.accept();
                    new Thread(new ListenerServer(socket)).start();
                } catch (Exception ep) {
                    ep.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] getData()
    {
        byte data[] = null;
        try{
            Socket socket = new Socket("10.72.24.16", 57772);
            OutputStream out = socket.getOutputStream();
            byte b[] = FileTool.getByte("D:\\lzk\\Project\\client\\in.txt");
            out.write(b, 0, b.length);
            InputStream in = socket.getInputStream();
            /*int x = 1000;
                     StringBuffer s1 = new StringBuffer();
                     for(int i = 0;i < x;i++)
                     {
                         int t = in.read();
             s1.append( (char) t);
                     }
                     System.out.println(s1);*/
            data = FileTool.getByte(in);
            System.out.println(new String(data));
            socket.close();

        }catch(Exception e)
        {

        }
        return data;
    }
}
