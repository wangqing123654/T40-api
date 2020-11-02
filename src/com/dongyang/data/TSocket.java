package com.dongyang.data;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedInputStream;
import java.net.ConnectException;
import java.util.Map;
import com.dongyang.util.TypeTool;

/**
 *
 * <p>Title: Socket</p>
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
public class TSocket {
    /**
     * 文件服务器端口   爱育华 8106;  
     * 文件服务器端口   泰     心8103
     */
    public static final int FILE_SERVER_PORT = 8106;
    /**
     * BPEL服务器端口
     */
    public static final int BPEL_SERVER_PORT = 8104;
    /**
     * ROOT服务器端口
     */
    public static final int ROOT_SERVER_PORT = 8105;
    public static final String DATASERVICE = "servlet/DataService";
    public static final String IOSERVICE = "servlet/IOService";
    private String IP;
    private int port;
    private String root;
    private URL url;
    public TSocket(String URL)
    {
        if(URL == null)
            return;
        if(URL.length() == 0)
            return;
        if(URL.length() > 7)
            if(URL.substring(0,7).toLowerCase().equals("http://"))
                URL = URL.substring(7);
        int index = URL.indexOf(":");
        if(index != -1)
        {
            setIP(URL.substring(0,index));
            URL = URL.substring(index + 1);
            int i = URL.indexOf("/");
            if(i == -1)
            {
                setPort(TypeTool.getInt(URL));
                return;
            }
            String root = URL.substring(i + 1);
            if(root.endsWith("/"))
                root = root.substring(0,root.length() - 1);
            setRoot(root);
            setPort(TypeTool.getInt(URL.substring(0,i)));
            return;
        }
        setPort(80);
        int i = URL.indexOf("/");
        if(i == -1)
        {
            setIP(URL);
            return;
        }
        setIP(URL.substring(0,i));
        String root = URL.substring(i + 1);
        if(root.endsWith("/"))
            root = root.substring(0,root.length() - 1);
        setRoot(root);
    }
    public TSocket(String IP,int port)
    {
        setIP(IP);
        setPort(port);
    }
    public TSocket(String IP,int port,String root)
    {
        setIP(IP);
        setPort(port);
        setRoot(root);
    }
    public void setIP(String IP)
    {
        this.IP = IP;
    }
    public String getIP()
    {
        return IP;
    }
    public void setPort(int port)
    {
        this.port = port;
    }
    public int getPort()
    {
        return port;
    }
    public void setRoot(String root)
    {
        this.root = root;
    }
    public String getRoot()
    {
        return root;
    }
    public Object doSocket(Object inObject)
    {
        //System.out.println("==========================TSocket doSocket start===============================");
        Object outObject = null;
        try{
          Socket socket = new Socket(getIP(), getPort());
         // socket.setSoTimeout(timeout)
          ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
          out.writeObject(inObject);
          out.flush();
          ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
          outObject = in.readObject();
          socket.close();
        }catch(Exception e)
        {
          System.out.println(getIP() + ":" + getPort() + " 没有启动 " + e.getMessage());
        }
        //System.out.println("==========================TSocket doSocket end===============================");
        return outObject;
    }
    public String getServletPath(String service)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("http://");
        sb.append(getIP());
        if(getPort() != 80)
        {
            sb.append(":");
            sb.append(getPort());
        }
        sb.append("/");
        if(getRoot() != null && getRoot().length() > 0)
        {
            sb.append(getRoot());
            sb.append("/");
        }
        sb.append(service);
        return sb.toString();
    }
    public IOLink getLink()
    {
        IOLink link = new IOLink();
        if(!link.link(getServletPath(IOSERVICE)))
            return null;
        return link;
    }
    public URL getURL()
    {
        if(url != null)
            return url;
        String servletPath = getServletPath(DATASERVICE);
        //System.out.println("=====servletPath======"+servletPath);
        try{
            url = new URL(servletPath);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return url;
    }
    public static int count;
    //synchronized
    public TParm doAction(TParm parm)
    {
        //System.out.println(parm);
        if(parm == null)
            return null;
        //count++;
        //System.out.println("count=" + count + " " + parm);
        //com.dongyang.util.DebugUsingTime.add("start");
        TParm result = new TParm();
        ObjectOutputStream out =null;
        ObjectInputStream in =null;
        try {
            URLConnection con = getURL().openConnection();
            //System.out.println("=========con========="+con.getURL().toString());
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            //输出对象
            out = new ObjectOutputStream(con.getOutputStream());
            out.writeObject(parm.getData());
            out.flush();
            //接收对象
            in = new ObjectInputStream(new
                    BufferedInputStream(con.getInputStream()));
            result.setData((Map)in.readObject());
            //关闭连接
            //in.close();
            //out.close();
        } catch (ConnectException e) {
            e.printStackTrace();
            result.setErr(-1,"没有找到网站" + getServletPath(DATASERVICE));
        } catch (Exception e) {
            e.printStackTrace();
            result.setErr(-1,"没有找到网站 " + getServletPath(DATASERVICE) + " " + e.getMessage());
        }finally{
        	//System.out.println("=======关闭IOStream资源========");
        	try {
        		if(in!=null){
        			in.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
            try {
            	if(out!=null){
            		out.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        //System.out.println("result=" + result);
        //com.dongyang.util.DebugUsingTime.add("end");
        return result;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("IP=");
        sb.append(getIP());
        sb.append(" Port=");
        sb.append(getPort());
        sb.append(" Root=");
        sb.append(getRoot());
        return sb.toString();
    }
    public static void main(String args[])
    {
        TSocket s = new TSocket("http://192.168.1.3:8090/web/");
        System.out.println(s);
    }
}
