package com.dongyang.root.server;

import java.net.Socket;
import com.dongyang.root.util.SocketTool;
import com.dongyang.data.TParm;

import java.util.Map;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.Iterator;

/**
 *
 * <p>Title: Socket监听器</p>
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
public class SocketListener implements Runnable
{
    /**
     * 用户列表
     */
    //public static Map userList = new HashMap();
	//并发同步检测的
	public static Map userList =new HashMap();
    /**
     * 总登录个数
     */
    public static int loginCount;
    /**
     * Socket 工具
     */
    private SocketTool io;
    /**
     * 基本信息
     */
    private TParm info;
    /**
     * 构造器
     * @param socket Socket
     */
    public SocketListener(Socket socket)
    {
        io = new SocketTool(socket);
        io.initStreamServer();
    }
    /**
     * 得到用户编号
     * @return String
     */
    public String getID()
    {
        return info.getValue("ID");
    }
    /**
     * 得到用户名称
     * @return String
     */
    public String getUserName()
    {
        return info.getValue("NAME");
    }
    /**
     * 得到内部序号
     * @return int
     */
    public int getSID()
    {
        return info.getInt("SID");
    }
    /**
     * 得到Module
     * @return ModuleTool
     */
    public ModuleTool getModule()
    {
        return ModuleTool.getTool();
    }
    /**
     * 登录
     * @return boolean true 成功 false 失败
     */
    public boolean login()
    {
        String ID = io.getString();
        String password = io.getString();
        if (ID == null || password == null)
            return false;
        info = getModule().login(ID);
        if (info.getErrCode() < 0)
        {
            io.setObject(info.getData());
            io.close();
            return false;
        }
        if(!password.equals(info.getValue("PASSWORD")))
        {
            io.setObject(TParm.newErrParm(-1,"密码错误!").getData());
            io.close();
            return false;
        }
        if (getUserListener(ID) != null)
        {
            if(info.getBoolean("ONLY_ONE"))
            {
                info.setErr( -1, "用户已经登录");
                io.setObject(info.getData());
                io.close();
                return false;
            }
        }
        int sid = addUserListener(ID, this);
        info.setData("SID",sid);
        io.setObject(info.getData());
        showOnLine();
        sendOnLineMessage();
        //System.out.println(ID + ":" +sid + " login");
        return true;
    }
    /**
     * 连接
     */
    public void listener()
    {
        String id = io.getString();
        //System.out.println(id);
    }
    /**
     * 处理数据流
     */
    public void run() {
        if (!login())
            return;
        try{
	        while (!io.isClose()){
	            work();
	        
	        }
        }catch(Exception e){
        	//
        	e.printStackTrace();
        }finally{
        	removeUserListener(getID(),getSID());
            sendOffLineMessage();      	 
        	 io.close();
        	 System.out.println(getID() + ":" + getSID() + " closed");
        	 //
        	 //Thread.currentThread().interrupt();
        	 //System.out.println(Thread.currentThread().getName()+ " : " + Thread.currentThread().isInterrupted());
        }       

    }
    /**
     * 处理工作
     */
    public void work()
    {
        int action = io.getInt();
        switch(action)
        {
        case SocketTool.SET_USER_NAME:
            setUserName(io.getString());
            break;
        case SocketTool.SET_USER_PASSWORD:
            setUserPassword(io.getString());
            break;
        case SocketTool.SEND_MESSAGE:
            sendMessage(io.getTimestamp(),io.getString(),io.getString());
            break;
        case SocketTool.SEND_BROADCAST:
            sendBroadcast(io.getTimestamp(),io.getString());
            break;
        case SocketTool.COMMAND:
            sendCommand(io.getString(),io.getInt(),io.getInt(),io.getObject());
            break;
        }
    }
    /**
     * 得到用户对象
     * @param id String
     * @return Map
     */
    public static Map getUserListener(String id)
    {
        return (Map)userList.get(id);
    }
    /**
     * 得到用户对象
     * @param id String
     * @param sid int
     * @return SocketListener
     */
    public static SocketListener getUserListener(String id,int sid)
    {
        Map map = getUserListener(id);
        if(map == null)
            return null;
        return (SocketListener)map.get(sid);
    }
    /**
     * 登录用户
     * @param id String 编号
     * @param obj SocketListener
     * @return int
     */
    public synchronized static int addUserListener(String id,SocketListener obj)
    {
        Map map = (Map)userList.get(id);
        if(map == null)
        {
            map = new HashMap();
            userList.put(id,map);
        }
        int i = 0;
        SocketListener s = (SocketListener)map.get(i);
        while(s != null)
        {
            i++;
            s = (SocketListener)map.get(i);
        }
        map.put(i,obj);
        loginCount ++;
        //System.out.println("loginCount=" + loginCount);
        return i;
    }
    /**
     * 断开用户
     * @param id String
     * @param sid int
     */
    public synchronized static void removeUserListener(String id,int sid)
    {
    	//用户临听消息
        Map map = (Map)userList.get(id);
        if(map == null)
            return;
        map.remove(sid);
        //没有任何监听消息了，清除在线用户
        if(map.size() == 0)
            userList.remove(id);
        loginCount --;
    }
    /**
     * 修改用户名
     * @param name String 新用户名
     */
    public void setUserName(String name)
    {
        if(name == null || name.length() == 0)
            return;
        info.setData("NAME",0,name);
        getModule().setUserName(getID(),name);
    }
    /**
     * 修改用密码
     * @param password String 新用密码
     */
    public void setUserPassword(String password)
    {
        if(password == null || password.length() == 0)
            return;
        info.setData("PASSWORD",0,password);
        getModule().setUserPassword(getID(),password);
    }
    /**
     * 发送消息
     * @param time Timestamp 时间
     * @param id String 发送ID
     * @param message String 消息
     */
    public  void sendMessage(Timestamp time,String id,String message)
    { 
    	//System.out.println("--id is--"+id);
        Map map = getUserListener(id);
        if(map == null)
            return;
        //System.out.println("map size is---"+map.size());
        Object[] iterator = map.keySet().toArray();
        SocketListener listener=null;
        for(int i=0;i<iterator.length;i++){
        	if(map.get(iterator[i])!=null){
        		//System.out.println("key is iterator["+i+"]="+iterator[i]);
        		listener = (SocketListener)map.get(iterator[i]);  
        		if(listener!=null){
        			//if(!listener.io.isClose()){
	        			System.out.println("reciver---"+id);
	        			System.out.println("message---"+message);
	        			//
	        			listener.sendMessageT(time,getID(),message);
        			//}
        		}
        	}
        }
        //
//        if(map!=null){
//	        System.out.println("size==================="+map.size());
//	        System.out.println("iterator==================="+iterator);
//        }
//        .
//        //
//        while(iterator.hasNext())
//        {       	
//        	SocketListener listener=null;
//        	//try{
//        		listener = (SocketListener)map.get(iterator.next());      
///*        	}catch(Exception e){
//        		e.printStackTrace();    
//        		System.out.println("继续执行发送消息...");
//        		 continue;
//        	}*/
//            //
//            if(listener == null){            	
//                continue;
//            }
//            //
//            listener.sendMessageT(time,getID(),message);
 //       }
    }
    /**
     * 发送消息(本地)
     * @param time Timestamp 时间
     * @param id String 发送ID
     * @param message String 消息
     */
    public synchronized void sendMessageT(Timestamp time,String id,String message)
    {
	        io.setInt(io.SEND_MESSAGE);
	        io.setTimestamp(time);
	        io.setString(id);
	        io.setString(message);
    }
    /**
     * 广播
     * @param time Timestamp 时间
     * @param message String 消息
     */
    public void sendBroadcast(Timestamp time,String message)
    {
        Iterator iterator = userList.keySet().iterator();
        while(iterator.hasNext())
        {
            String id = (String)iterator.next();
            Map map = getUserListener(id);
            if(map == null)
                continue;
            Iterator iterator1 = map.keySet().iterator();
            while(iterator1.hasNext())
            {
                SocketListener listener = (SocketListener) map.get(iterator1.
                        next());
                if (listener != null)
                    listener.sendBroadcast(time, getID(), message);
            }
        }
    }
    /**
     * 广播消息(本地)
     * @param time Timestamp 时间
     * @param id String 发送ID
     * @param message String 消息
     */
    public synchronized void sendBroadcast(Timestamp time,String id,String message)
    {
        io.setInt(io.SEND_BROADCAST);
        io.setTimestamp(time);
        io.setString(id);
        io.setString(message);
    }
    /**
     * 察看在线
     */
    public synchronized void showOnLine()
    {
        String s[] = getModule().getGroupList(getID());
        if(s == null)
            return;
        for(int i = 0;i < s.length;i++)
        {
            Map map = getUserListener(s[i]);
            if(map != null)
            {
                io.setInt(io.ON_LINE);
                io.setString(s[i]);
            }
        }
    }
    /**
     * 在线通知
     */
    public void sendOnLineMessage()
    {
        String s[] = getModule().getLinkList(getID());
        if(s == null)
            return;
        for(int i = 0;i < s.length;i++)
        {
            Map map = getUserListener(s[i]);
            if(map == null)
                return;
            Iterator iterator = map.keySet().iterator();
            while(iterator.hasNext())
            {
                SocketListener listener = (SocketListener) map.get(iterator.
                        next());
                if (listener == null)
                    continue;
                listener.onLine(getID());
            }
        }
    }
    /**
     * 断线通知
     */
    public void sendOffLineMessage()
    {
        String s[] = getModule().getLinkList(getID());
        if(s == null)
            return;
        for(int i = 0;i < s.length;i++)
        {
            Map map = getUserListener(s[i]);
            if(map == null)
                return;
            Iterator iterator = map.keySet().iterator();
            while(iterator.hasNext())
            {
                SocketListener listener = (SocketListener) map.get(iterator.
                        next());
                if (listener == null)
                    continue;
                listener.offLine(getID());
            }
        }
    }
    /**
     * 在线通知
     * @param id String
     */
    public synchronized void onLine(String id)
    {
        io.setInt(io.ON_LINE);
        io.setString(id);
    }
    /**
     * 断线通知
     * @param id String
     */
    public synchronized void offLine(String id)
    {
        io.setInt(io.OFF_LINE);
        io.setString(id);
    }
    /**
     * 同意开始共享屏幕(恢复)
     * @param id String
     * @param flg boolean
     */
    public synchronized void passStartScreenT(String id,boolean flg)
    {
        io.setInt(io.PASS_START_SCREEN);
        io.setString(id);
        io.setBoolean(flg);
    }
    /**
     * 通知开始共享桌面
     * @param id String
     */
    public synchronized void startScreenT(String id)
    {
        io.setInt(io.START_SCREEN);
        io.setString(id);
    }
    /**
     * 发送命令
     * @param id String
     * @param sid String
     * @param action int
     * @param data Object
     */
    public void sendCommand(String id,int sid,int action,Object data)
    {
        if(sid >= 0)
        {
            SocketListener listener = getUserListener(id,sid);
            if (listener != null)
                listener.sendCommandT(getID(), action, data);
        }
        Map map = getUserListener(id);
        if(map == null)
            return;
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext())
        {
            SocketListener listener = (SocketListener)map.get(iterator.next());
            if(listener == null)
                continue;
            listener.sendCommandT(getID(), action, data);
        }
    }
    /**
     * 发送命令(传送)
     * @param id String
     * @param action int
     * @param data Object
     */
    public synchronized void sendCommandT(String id,int action,Object data)
    {
        io.setInt(io.COMMAND);
        io.setString(id);
        io.setInt(action);
        io.setObject(data);
    }
}
