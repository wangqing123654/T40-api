package com.dongyang.server;

import java.net.Socket;
import java.net.ServerSocket;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.root.server.SocketListener;
import com.dongyang.util.TDebug;

/**
 *
 * <p>Title: Root服务器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.23
 * @version 1.0
 */
public class RootServer
{
    /**
     * 端口
     */
    private int port = TSocket.ROOT_SERVER_PORT;
    /**
     * 停止开关
     */
    private  boolean stop;
    
    //private static final int THREADPOOLSIZE = 10;
    /**
     * 设置端口
     * @param port int
     */
    public void setPort(int port)
    {
        this.port = port;
    }
    /**
     * 得到端口
     * @return int
     */
    public int getPort()
    {
        return port;
    }
    /**
     * 停止程序
     */
    public  void stop()
    {
        stop = true;
    }
    /**
     * 是否停止程序
     * @return boolean
     */
    public  boolean isStop()
    {
        return stop;
    }
    /**
     * 启动
     */
    public void start()
    {
        try {
           final ServerSocket server = new ServerSocket(getPort());
            out("Root Server 4.0 Start " + getPort());
            
            //test
          /*  for(int i=0;i<THREADPOOLSIZE;i++){
            	 Thread thread = new Thread(){
            		 public void run(){
            			  while(true){
                              try {
                                  //等待客户端的连接
                                  Socket socket = server.accept();
                                  System.out.println("与客户端连接成功！");
                                  //一旦连接成功，则在该线程中与客户端通信
                                  new SocketListener(socket);
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }          			 
            		 }
            		 }
            	 };
            	 thread.start();
            }*/
            //
            while (!isStop())
            {
                try {
                    Socket socket = server.accept();
                    new Thread(new SocketListener(socket)).start();
                    //
                    //起动监测程序
                    //new Thread(new ProbeSListenerThread()).start();
                    
                } catch (Exception ep) {
                    ep.printStackTrace();
                }
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 入口主程
     * @param args String[]
     */
    public static void main(String args[]) {
        TDebug.initServer("Root.ini");
        RootServer root = new RootServer();
        if (args.length == 1)
            root.setPort(TCM_Transform.getInt(args[0]));
        root.start();
    }
    /**
     * 输出日至
     * @param function String
     * @param text String
     */
    public void out(String function, String text) {
        System.out.println(function + "->" + text);
    }
    /**
     * 输出日至
     * @param text String
     */
    public static void out(String text) {
        System.out.println(text);
    }
}
