package com.dongyang.root.client;

import java.net.Socket;
import com.dongyang.data.TSocket;
import com.dongyang.root.util.SocketTool;
import com.dongyang.data.TParm;
import java.util.Map;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.jdo.TJDODBTool;
import java.sql.Timestamp;
import java.awt.Robot;
import java.awt.event.InputEvent;
import com.dongyang.config.TConfig;

public class SocketLink implements Runnable
{
    public final static int MOUSE_MODE = 0x01;
    public final static int MOUSE_DRAGGED = 0x02;
    public final static int MOUSE_PRESSED = 0x03;
    public final static int MOUSE_RELEASED = 0x04;
    public final static int KEY_PRESSED = 0x05;
    public final static int KEY_RELEASED = 0x06;
    public final static int ADD_FRIEND = 0x07;
    public final static int ADD_FRIEND_PASS = 0x08;
    public final static int ADD_FRIEND_DENY = 0x09;
    private Robot robot;
    //private Socket socket=null;
    /**
     * IP
     */
    private String IP;
    /**
     *  用户编号
     */
    private String userID;
    /**
     * 用户密码
     */
    private String password;
    /**
     * Socket 工具
     */
    private SocketTool io;
    /**
     * 基本信息
     */
    private TParm info;
    /**
     * 错误信息
     */
    private String errText;
    /**
     * 基础事件
     */
    private BaseEvent baseEvent;
    /**
     * 构造器
     */
    public SocketLink()
    {
        baseEvent = new BaseEvent();
        try{
            robot = new Robot();
        }catch(Exception e)
        {
            //e.printStackTrace();
        }
    }
    /**
     * 连接
     * @return boolean true 成功 false 失败
     */
    public boolean link()
    {
        io = null;
        Socket socket = getSocket();
        if(socket == null)
            return false;
        io = new SocketTool(socket);
        return io.initStreamClient();
/*        io = null;
        if(socket==null||socket.isClosed()){
           socket = getSocket();
           //
	        if(socket == null){
	            return false;
	        }
        }
        //
        io = new SocketTool(socket);
        return io.initStreamClient();*/
    }
    /**
     * 得到Socket
     * @return Socket
     */
    public Socket getSocket()
    {
        try{
            return new Socket(getIP(),TSocket.ROOT_SERVER_PORT);
        }catch(Exception e)
        {
            errText = "没有找到通讯服务器";
            return null;
        }
    }
    /**
     * 设置IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        this.IP = IP;
    }
    /**
     * 得到IP
     * @return String
     */
    public String getIP()
    {
        if(IP != null && IP.length() > 0)
            return IP;
        IP = getDefaultIP();
        return IP;
    }
    /**
     * 得到默认IP
     * @return String
     */
    public String getDefaultIP()
    {
        TConfig config = TConfig.getConfig("WEB-INF\\config\\system\\TConfig.x");
        return config.getString("","RootServer.IP");
    }
    /**
     * 设置用户编号
     * @param userID String
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    /**
     * 得到用户编号
     * @return String
     */
    public String getUserID()
    {
        return userID;
    }
    /**
     * 设置用户密码
     * @param password String
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    /**
     * 得到用户密码
     * @return String
     */
    public String getPassword()
    {
        return password;
    }
    /**
     * 登录
     * @return boolean true 成功 false 失败
     */
    public boolean login()
    {
        io.setString(getUserID());
        io.setString(getPassword());
        info = new TParm((Map)io.getObject());
        if(info.getErrCode() != 0)
        {
            io.close();
            errText = info.getErrText();
            return false;
        }
        if(info.getErrCode() < 0)
        {
            io.close();
            errText = info.getErrText();
            return false;
        }
        //设置基础事件
        io.setBaseEvent(getBaseEventObject());
        return true;
    }
    /**
     * 处理数据流
     */
    public void run() {
        while (!isClose())
            work();
        close();
    }
    /**
     * 得到错误信息
     * @return String
     */
    public String getErrText()
    {
        return errText;
    }
    /**
     * 关闭
     */
    public void close()
    {
        if(io == null)
            return;
        io.close();
    }
    /**
     * 是否关闭
     * @return boolean
     */
    public boolean isClose()
    {
        if(io == null)
            return true;
        return io.isClose();
    }
    /**
     * 得到基本信息
     * @return TParm
     */
    public TParm getInfo()
    {
        return info;
    }
    /**
     * 得到用户编号
     * @return String
     */
    public String getID()
    {
        return info.getValue("ID",0);
    }
    /**
     * 得到内部编号
     * @return int
     */
    public int getSID()
    {
        return info.getInt("SID");
    }
    /**
     * 得到用户名称
     * @return String
     */
    public String getUserName()
    {
        return info.getValue("NAME",0);
    }
    /**
     * 设置用户名称
     * @param name String 新名称
     * @return boolean true 成功 false 失败
     */
    public synchronized boolean setUserName(String name)
    {
        if(name == null || name.length() == 0)
            return false;
        info.setData("NAME",0,name);
        io.setInt(io.SET_USER_NAME);
        return io.setString(name);
    }
    /**
     * 设置用户密码
     * @param password String
     * @return boolean
     */
    public synchronized boolean setUserPassword(String password)
    {
        if(password == null || password.length() == 0)
            return false;
        info.setData("PASSWORD",0,password);
        io.setInt(io.SET_USER_PASSWORD);
        return io.setString(password);
    }
    /**
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }

    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * 删除监听方法
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},
                         new String[] {"java.lang.Object"});
    }
    /**
     * 得到时间
     * @return Timestamp
     */
    public Timestamp getTime()
    {
        return TJDODBTool.getInstance().getDBTime();
    }
    /**
     * 处理工作
     */
    public void work()
    {
        int action = io.getInt();
        switch(action)
        {
        case SocketTool.SEND_MESSAGE:
            sendMessageT(io.getTimestamp(),io.getString(),io.getString());
            break;
        case SocketTool.SEND_BROADCAST:
            sendBroadcastT(io.getTimestamp(),io.getString(),io.getString());
            break;
        case SocketTool.ON_LINE:
            onLineT(io.getString());
            break;
        case SocketTool.OFF_LINE:
            offLineT(io.getString());
            break;
        case SocketTool.COMMAND:
            sendCommandT(io.getString(),io.getInt(),io.getObject());
            break;
        }
    }
    /**
     * 发送消息
     * @param id String 用户编号
     * @param s String 消息内容
     * @return boolean true 成功 false 失败
     */
    public boolean sendMessage(String id,String s)
    {
        return sendMessage(getTime(),id,s);
    }
    /**
     * 发送消息
     * @param time Timestamp 时间
     * @param id String 用户编号
     * @param s String 消息内容
     * @return boolean true 成功 false 失败
     */
    public synchronized boolean sendMessage(Timestamp time,String id,String s)
    {
        if(id == null || id.length() == 0)
            return false;
        if(s == null || s.length() == 0)
            return false;
        io.setInt(io.SEND_MESSAGE);
        io.setTimestamp(time);
        io.setString(id);
        return io.setString(s);
    }
    /**
     * 收到消息
     * @param time Timestamp 时间
     * @param id String 发送者编号
     * @param s String 消息
     */
    public void sendMessageT(Timestamp time,String id,String s)
    {
        callEvent("Message",new Object[]{time,id,s},new String[]{"java.sql.Timestamp","java.lang.String","java.lang.String"});
    }
    /**
     * 发送广播
     * @param s String 消息内容
     * @return boolean true 成功 false 失败
     */
    public boolean sendBroadcast(String s)
    {
        return sendBroadcast(getTime(),s);
    }
    /**
     * 发送广播
     * @param time Timestamp 时间
     * @param s String 消息内容
     * @return boolean true 成功 false 失败
     */
    public synchronized boolean sendBroadcast(Timestamp time,String s)
    {
        if(s == null || s.length() == 0)
            return false;
        io.setInt(io.SEND_BROADCAST);
        io.setTimestamp(time);
        return io.setString(s);
    }
    /**
     * 广播消息
     * @param time Timestamp 时间
     * @param id String 发送者编号
     * @param s String 消息
     */
    public void sendBroadcastT(Timestamp time,String id,String s)
    {
        callEvent("Broadcast",new Object[]{time,id,s},new String[]{"java.sql.Timestamp","java.lang.String","java.lang.String"});
    }
    /**
     * 运行
     * @param userID String
     * @param password String
     * @return SocketLink
     */
    public static SocketLink running(String userID,String password)
    {
        return running("",userID,password);
    }
    /**
     * 运行
     * @param IP String
     * @param userID String
     * @param password String
     * @return SocketLink
     */
    public static SocketLink running(String IP,String userID,String password)
    {
        SocketLink client = new SocketLink();
        client.setIP(IP);
        client.setUserID(userID);
        client.setPassword(password);
        if(!client.link())
            return client;
        if(!client.login())
            return client;
        return client;
    }
    /**
     * 监听
     */
    public void listener()
    {
        new Thread(this).start();
    }
    /**
     * 在线
     * @param id String
     */
    public void onLineT(String id)
    {
        callEvent("onLine",new Object[]{id},new String[]{"java.lang.String"});
    }
    /**
     * 断线
     * @param id String
     */
    public void offLineT(String id)
    {
        callEvent("offLine",new Object[]{id},new String[]{"java.lang.String"});
    }
    /**
     * 申请开始屏幕共享
     * @param id String 对方ID
     * @return boolean
     */
    public synchronized boolean sendStartScreen(String id)
    {
        if(id == null || id.length() == 0)
            return false;
        io.setInt(io.START_SCREEN);
        return io.setString(id);
    }
    /**
     * 同意开始共享屏幕
     * @param id String 对方编号
     * @param pass boolean true 同意 false 拒绝
     * @return boolean true 成功 false 失败
     */
    public synchronized boolean passStartScreen(String id,boolean pass)
    {
        if(id == null || id.length() == 0)
            return false;
        io.setInt(io.PASS_START_SCREEN);
        io.setString(id);
        return io.setBoolean(pass);
    }
    /**
     * 执行命令
     * @param id String 控制编号
     * @param action int 控制类型
     * @param obj Object 控制参数
     * @return boolean true 成功 false 失败
     */
    public synchronized boolean sendCommand(String id,int action,Object obj)
    {
        return sendCommand(id,-1,action,obj);
    }
    /**
     * 执行命令
     * @param id String 控制编号
     * @param sid int 内部序号
     * @param action int 控制类型
     * @param obj Object 控制参数
     * @return boolean true 成功 false 失败
     */
    public synchronized boolean sendCommand(String id,int sid,int action,Object obj)
    {
        io.setInt(io.COMMAND);
        io.setString(id);
        io.setInt(sid);
        io.setInt(action);
        return io.setObject(obj);
    }
    /**
     * 执行命令
     * @param id String 控制编号
     * @param action int 控制类型
     * @param obj Object 控制参数
     */
    public void sendCommandT(final String id,final int action,final Object obj)
    {
        switch(action)
        {
        case MOUSE_MODE:
        case MOUSE_DRAGGED:
            int[] p = (int[])obj;
            robot.mouseMove(p[0],p[1]);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            return;
        case MOUSE_PRESSED:
            switch((Integer)obj)
            {
              case 1:
                robot.mousePress(InputEvent.BUTTON1_MASK);
                return;
              case 2:
                robot.mousePress(InputEvent.BUTTON2_MASK);
                return;
              case 3:
                robot.mousePress(InputEvent.BUTTON3_MASK);
                return;
            }
            return;
        case MOUSE_RELEASED:
            switch((Integer)obj)
            {
              case 1:
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                return;
              case 2:
                robot.mouseRelease(InputEvent.BUTTON2_MASK);
                return;
              case 3:
                robot.mouseRelease(InputEvent.BUTTON3_MASK);
                return;
            }
            return;
        case KEY_PRESSED:
            robot.keyPress((Integer)obj);
            return;
        case KEY_RELEASED:
            robot.keyRelease((Integer)obj);
            return;
        }
        new Thread(){
            public void run()
            {
                callEvent("Command",new Object[]{id,action,obj},new String[]{"java.lang.String","int","java.lang.Object"});
            }
        }.start();
    }
    public static void main(String args[])
    {
        byte[] data = new byte[10];
        System.out.println(data.getClass().getName());
        /*try{
            SocketLink link = new SocketLink();
            link.setIP("192.168.1.101");
            link.link();
            link.setUserID("0001");
            link.setPassword("test");
            if(!link.login())
            {
                System.out.println(link.getErrText());
                return;
            }

            System.out.println(link.getInfo());
            link.listener();
            //link.sendMessage("0001","hello");
            //link.setUserName("admin");
            //link.setUserPassword("admin");
            link.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }*/
    }
}
