package com.dongyang.root.util;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.sql.Timestamp;
import com.dongyang.ui.event.BaseEvent;
import java.io.ByteArrayOutputStream;

/**
 *
 * <p>Title: Socket工具类</p>
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
public class SocketTool
{
    /**
     * 设置用户名
     */
    public final static int SET_USER_NAME = 0x01;
    /**
     * 设置用户密码
     */
    public final static int SET_USER_PASSWORD = 0x02;
    /**
     * 发送消息
     */
    public final static int SEND_MESSAGE = 0x03;
    /**
     * 广播
     */
    public final static int SEND_BROADCAST = 0x04;
    /**
     * 在线
     */
    public final static int ON_LINE = 0x05;
    /**
     * 断线
     */
    public final static int OFF_LINE = 0x06;
    /**
     * 共享桌面开始
     */
    public final static int START_SCREEN = 0x07;
    /**
     * 共享桌面停止
     */
    public final static int STOP_SCREEN = 0x08;
    /**
     * 共享桌面
     */
    public final static int SHARE_SCREEN = 0x09;
    /**
     * 共享桌面(VIDEO)
     */
    public final static int SHARE_SCREEN_VIDEO = 0x0A;
    /**
     * 同意共享桌面开始
     */
    public final static int PASS_START_SCREEN = 0x0B;
    /**
     * 命令
     */
    public final static int COMMAND = 0x0C;
    /**
     * 输入流
     */
    private ObjectInputStream in;
    /**
     * 输出流
     */
    private ObjectOutputStream out;

    /**
     * Socket
     */
    private Socket socket;
    /**
     * 是否关闭
     */
    private boolean isClose;
    /**
     * 基础事件
     */
    private BaseEvent baseEvent;
    /**
     * 构造器
     * @param socket Socket
     */
    public SocketTool(Socket socket)
    {
        setSocket(socket);
    }
    /**
     * 设置Socket
     * @param socket Socket
     */
    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }
    /**
     * 得到Socket
     * @return Socket
     */
    public Socket getSocket()
    {
        return socket;
    }
    /**
     * 设置基础事件对象
     * @param baseEvent BaseEvent
     */
    public void setBaseEvent(BaseEvent baseEvent)
    {
        this.baseEvent = baseEvent;
    }
    /**
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEvent()
    {
        return baseEvent;
    }
    /**
     * 初始化流(服务器端)
     * @return boolean
     */
    public boolean initStreamServer()
    {
        if(isClose())
            return false;
        try{
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch(Exception e)
        {
        	//e.printStackTrace();
            close();
            return false;
        }
        return true;
    }
    /**
     * 初始化流(客户端)
     * @return boolean
     */
    public boolean initStreamClient()
    {
        if(isClose())
            return false;
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }catch(Exception e)
        {
        	//e.printStackTrace();
            close();
            return false;
        }
        return true;
    }
    /**
     * 得到输入流
     * @return ObjectInputStream
     */
    public ObjectInputStream getIn()
    {
        return in;
    }
    /**
     * 得到输出流
     * @return ObjectOutputStream
     */
    public ObjectOutputStream getOut()
    {
        return out;
    }
    /**
     * 关闭
     */
    public  void close()
    {
        if(isClose())
            return;
        try{
    		in.close();
    		out.close();
    		getSocket().close();
        }catch(Exception e)
        {
        	//e.printStackTrace();
        }finally{
	        in = null;
	        out = null;
	        socket = null;
	        isClose = true;
	        if(getBaseEvent() != null)
	            getBaseEvent().callEvent("close");
        }
    }
    /**
     * 是否已经关闭
     * @return boolean true 关闭 false 有效
     */
    public boolean isClose()
    {
        return isClose;
    }
    /**
     * 读取字符串
     * @return String
     */
    public String getString()
    {
        if(isClose())
            return null;
        try{
            return in.readUTF();
        }catch(Exception e)
        {
            close();
            return null;
        }
    }
    /**
     * 写入字符串
     * @param s String
     * @return boolean true 成功 false 失败
     */
    public boolean setString(String s)
    {
        if(isClose())
            return false;
        try{
            out.writeUTF(s);
            out.flush();
            return true;
        }catch(Exception e)
        {
            close();
            return false;
        }
    }
    /**
     * 读取int
     * @return int
     */
    public int getInt()
    {
        if(isClose())
            return -1;
        try{
            return in.readInt();
        }catch(Exception e)
        {
            close();
            return -1;
        }
    }
    /**
     * 写入int
     * @param x int
     * @return boolean true 成功 false 失败
     */
    public boolean setInt(int x)
    {
        if(isClose())
            return false;
        try{
            out.writeInt(x);
            out.flush();
            return true;
        }catch(Exception e)
        {
            close();
            return false;
        }
    }
    /**
     * 读取int
     * @return int
     */
    public byte[] getByte()
    {
        if(isClose())
            return null;
        try{
            int length = in.readInt();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte b[] = new byte[1024];
            while (length > 0)
            {
                int n = 1024;
                if(length > 1024)
                    length -= 1024;
                else
                {
                    n = length;
                    length = 0;
                }
                if(in.read(b,0,n) == -1)
                    break;
                out.write(b, 0, n);
            }
            out.close();
            return out.toByteArray();
        }catch(Exception e)
        {
            close();
            return null;
        }
    }
    /**
     * 写入Byte
     * @param data byte[]
     * @return boolean true 成功 false 失败
     */
    public boolean setByte(byte[] data)
    {
        if(isClose())
            return false;
        try{
            out.writeInt(data.length);
            out.flush();
            out.write(data);
            out.flush();
            return true;
        }catch(Exception e)
        {
            close();
            return false;
        }
    }
    /**
     * 读取Object
     * @return Object
     */
    public Object getObject()
    {
        if(isClose())
            return null;
        try{
            return in.readObject();
        }catch(Exception e)
        {
            close();
            return null;
        }
    }
    /**
     * 写入Object
     * @param obj Object
     * @return boolean true 成功 false 失败
     */
    public boolean setObject(Object obj)
    {
        if(isClose())
            return false;
        try{
            out.writeObject(obj);
            out.flush();
            return true;
        }catch(Exception e)
        {
            close();
            return false;
        }
    }
    /**
     * 读取时间
     * @return Timestamp
     */
    public Timestamp getTimestamp()
    {
        return (Timestamp)getObject();
    }
    /**
     * 写入时间
     * @param time Timestamp
     * @return boolean
     */
    public boolean setTimestamp(Timestamp time)
    {
        return setObject(time);
    }
    /**
     * 读取boolean
     * @return boolean
     */
    public boolean getBoolean()
    {
        return getInt() == 1;
    }
    /**
     * 写入boolean
     * @param flg boolean
     * @return boolean
     */
    public boolean setBoolean(boolean flg)
    {
        return setInt(flg?1:0);
    }
}
