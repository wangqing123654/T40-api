package com.dongyang.server;

import java.net.Socket;
import java.net.ServerSocket;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.root.server.SocketListener;
import com.dongyang.util.TDebug;

/**
 *
 * <p>Title: Root������</p>
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
     * �˿�
     */
    private int port = TSocket.ROOT_SERVER_PORT;
    /**
     * ֹͣ����
     */
    private  boolean stop;
    
    //private static final int THREADPOOLSIZE = 10;
    /**
     * ���ö˿�
     * @param port int
     */
    public void setPort(int port)
    {
        this.port = port;
    }
    /**
     * �õ��˿�
     * @return int
     */
    public int getPort()
    {
        return port;
    }
    /**
     * ֹͣ����
     */
    public  void stop()
    {
        stop = true;
    }
    /**
     * �Ƿ�ֹͣ����
     * @return boolean
     */
    public  boolean isStop()
    {
        return stop;
    }
    /**
     * ����
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
                                  //�ȴ��ͻ��˵�����
                                  Socket socket = server.accept();
                                  System.out.println("��ͻ������ӳɹ���");
                                  //һ�����ӳɹ������ڸ��߳�����ͻ���ͨ��
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
                    //�𶯼�����
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
     * �������
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
     * �������
     * @param function String
     * @param text String
     */
    public void out(String function, String text) {
        System.out.println(function + "->" + text);
    }
    /**
     * �������
     * @param text String
     */
    public static void out(String text) {
        System.out.println(text);
    }
}
