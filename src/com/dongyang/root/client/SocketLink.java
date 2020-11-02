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
     *  �û����
     */
    private String userID;
    /**
     * �û�����
     */
    private String password;
    /**
     * Socket ����
     */
    private SocketTool io;
    /**
     * ������Ϣ
     */
    private TParm info;
    /**
     * ������Ϣ
     */
    private String errText;
    /**
     * �����¼�
     */
    private BaseEvent baseEvent;
    /**
     * ������
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
     * ����
     * @return boolean true �ɹ� false ʧ��
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
     * �õ�Socket
     * @return Socket
     */
    public Socket getSocket()
    {
        try{
            return new Socket(getIP(),TSocket.ROOT_SERVER_PORT);
        }catch(Exception e)
        {
            errText = "û���ҵ�ͨѶ������";
            return null;
        }
    }
    /**
     * ����IP
     * @param IP String
     */
    public void setIP(String IP)
    {
        this.IP = IP;
    }
    /**
     * �õ�IP
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
     * �õ�Ĭ��IP
     * @return String
     */
    public String getDefaultIP()
    {
        TConfig config = TConfig.getConfig("WEB-INF\\config\\system\\TConfig.x");
        return config.getString("","RootServer.IP");
    }
    /**
     * �����û����
     * @param userID String
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    /**
     * �õ��û����
     * @return String
     */
    public String getUserID()
    {
        return userID;
    }
    /**
     * �����û�����
     * @param password String
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    /**
     * �õ��û�����
     * @return String
     */
    public String getPassword()
    {
        return password;
    }
    /**
     * ��¼
     * @return boolean true �ɹ� false ʧ��
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
        //���û����¼�
        io.setBaseEvent(getBaseEventObject());
        return true;
    }
    /**
     * ����������
     */
    public void run() {
        while (!isClose())
            work();
        close();
    }
    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getErrText()
    {
        return errText;
    }
    /**
     * �ر�
     */
    public void close()
    {
        if(io == null)
            return;
        io.close();
    }
    /**
     * �Ƿ�ر�
     * @return boolean
     */
    public boolean isClose()
    {
        if(io == null)
            return true;
        return io.isClose();
    }
    /**
     * �õ�������Ϣ
     * @return TParm
     */
    public TParm getInfo()
    {
        return info;
    }
    /**
     * �õ��û����
     * @return String
     */
    public String getID()
    {
        return info.getValue("ID",0);
    }
    /**
     * �õ��ڲ����
     * @return int
     */
    public int getSID()
    {
        return info.getInt("SID");
    }
    /**
     * �õ��û�����
     * @return String
     */
    public String getUserName()
    {
        return info.getValue("NAME",0);
    }
    /**
     * �����û�����
     * @param name String ������
     * @return boolean true �ɹ� false ʧ��
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
     * �����û�����
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
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }

    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param methodName String ������
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},
                         new String[] {"java.lang.Object"});
    }
    /**
     * �õ�ʱ��
     * @return Timestamp
     */
    public Timestamp getTime()
    {
        return TJDODBTool.getInstance().getDBTime();
    }
    /**
     * ������
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
     * ������Ϣ
     * @param id String �û����
     * @param s String ��Ϣ����
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean sendMessage(String id,String s)
    {
        return sendMessage(getTime(),id,s);
    }
    /**
     * ������Ϣ
     * @param time Timestamp ʱ��
     * @param id String �û����
     * @param s String ��Ϣ����
     * @return boolean true �ɹ� false ʧ��
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
     * �յ���Ϣ
     * @param time Timestamp ʱ��
     * @param id String �����߱��
     * @param s String ��Ϣ
     */
    public void sendMessageT(Timestamp time,String id,String s)
    {
        callEvent("Message",new Object[]{time,id,s},new String[]{"java.sql.Timestamp","java.lang.String","java.lang.String"});
    }
    /**
     * ���͹㲥
     * @param s String ��Ϣ����
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean sendBroadcast(String s)
    {
        return sendBroadcast(getTime(),s);
    }
    /**
     * ���͹㲥
     * @param time Timestamp ʱ��
     * @param s String ��Ϣ����
     * @return boolean true �ɹ� false ʧ��
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
     * �㲥��Ϣ
     * @param time Timestamp ʱ��
     * @param id String �����߱��
     * @param s String ��Ϣ
     */
    public void sendBroadcastT(Timestamp time,String id,String s)
    {
        callEvent("Broadcast",new Object[]{time,id,s},new String[]{"java.sql.Timestamp","java.lang.String","java.lang.String"});
    }
    /**
     * ����
     * @param userID String
     * @param password String
     * @return SocketLink
     */
    public static SocketLink running(String userID,String password)
    {
        return running("",userID,password);
    }
    /**
     * ����
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
     * ����
     */
    public void listener()
    {
        new Thread(this).start();
    }
    /**
     * ����
     * @param id String
     */
    public void onLineT(String id)
    {
        callEvent("onLine",new Object[]{id},new String[]{"java.lang.String"});
    }
    /**
     * ����
     * @param id String
     */
    public void offLineT(String id)
    {
        callEvent("offLine",new Object[]{id},new String[]{"java.lang.String"});
    }
    /**
     * ���뿪ʼ��Ļ����
     * @param id String �Է�ID
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
     * ͬ�⿪ʼ������Ļ
     * @param id String �Է����
     * @param pass boolean true ͬ�� false �ܾ�
     * @return boolean true �ɹ� false ʧ��
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
     * ִ������
     * @param id String ���Ʊ��
     * @param action int ��������
     * @param obj Object ���Ʋ���
     * @return boolean true �ɹ� false ʧ��
     */
    public synchronized boolean sendCommand(String id,int action,Object obj)
    {
        return sendCommand(id,-1,action,obj);
    }
    /**
     * ִ������
     * @param id String ���Ʊ��
     * @param sid int �ڲ����
     * @param action int ��������
     * @param obj Object ���Ʋ���
     * @return boolean true �ɹ� false ʧ��
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
     * ִ������
     * @param id String ���Ʊ��
     * @param action int ��������
     * @param obj Object ���Ʋ���
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
