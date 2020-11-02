package com.dongyang.root.util;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.sql.Timestamp;
import com.dongyang.ui.event.BaseEvent;
import java.io.ByteArrayOutputStream;

/**
 *
 * <p>Title: Socket������</p>
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
     * �����û���
     */
    public final static int SET_USER_NAME = 0x01;
    /**
     * �����û�����
     */
    public final static int SET_USER_PASSWORD = 0x02;
    /**
     * ������Ϣ
     */
    public final static int SEND_MESSAGE = 0x03;
    /**
     * �㲥
     */
    public final static int SEND_BROADCAST = 0x04;
    /**
     * ����
     */
    public final static int ON_LINE = 0x05;
    /**
     * ����
     */
    public final static int OFF_LINE = 0x06;
    /**
     * �������濪ʼ
     */
    public final static int START_SCREEN = 0x07;
    /**
     * ��������ֹͣ
     */
    public final static int STOP_SCREEN = 0x08;
    /**
     * ��������
     */
    public final static int SHARE_SCREEN = 0x09;
    /**
     * ��������(VIDEO)
     */
    public final static int SHARE_SCREEN_VIDEO = 0x0A;
    /**
     * ͬ�⹲�����濪ʼ
     */
    public final static int PASS_START_SCREEN = 0x0B;
    /**
     * ����
     */
    public final static int COMMAND = 0x0C;
    /**
     * ������
     */
    private ObjectInputStream in;
    /**
     * �����
     */
    private ObjectOutputStream out;

    /**
     * Socket
     */
    private Socket socket;
    /**
     * �Ƿ�ر�
     */
    private boolean isClose;
    /**
     * �����¼�
     */
    private BaseEvent baseEvent;
    /**
     * ������
     * @param socket Socket
     */
    public SocketTool(Socket socket)
    {
        setSocket(socket);
    }
    /**
     * ����Socket
     * @param socket Socket
     */
    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }
    /**
     * �õ�Socket
     * @return Socket
     */
    public Socket getSocket()
    {
        return socket;
    }
    /**
     * ���û����¼�����
     * @param baseEvent BaseEvent
     */
    public void setBaseEvent(BaseEvent baseEvent)
    {
        this.baseEvent = baseEvent;
    }
    /**
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEvent()
    {
        return baseEvent;
    }
    /**
     * ��ʼ����(��������)
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
     * ��ʼ����(�ͻ���)
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
     * �õ�������
     * @return ObjectInputStream
     */
    public ObjectInputStream getIn()
    {
        return in;
    }
    /**
     * �õ������
     * @return ObjectOutputStream
     */
    public ObjectOutputStream getOut()
    {
        return out;
    }
    /**
     * �ر�
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
     * �Ƿ��Ѿ��ر�
     * @return boolean true �ر� false ��Ч
     */
    public boolean isClose()
    {
        return isClose;
    }
    /**
     * ��ȡ�ַ���
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
     * д���ַ���
     * @param s String
     * @return boolean true �ɹ� false ʧ��
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
     * ��ȡint
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
     * д��int
     * @param x int
     * @return boolean true �ɹ� false ʧ��
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
     * ��ȡint
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
     * д��Byte
     * @param data byte[]
     * @return boolean true �ɹ� false ʧ��
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
     * ��ȡObject
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
     * д��Object
     * @param obj Object
     * @return boolean true �ɹ� false ʧ��
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
     * ��ȡʱ��
     * @return Timestamp
     */
    public Timestamp getTimestamp()
    {
        return (Timestamp)getObject();
    }
    /**
     * д��ʱ��
     * @param time Timestamp
     * @return boolean
     */
    public boolean setTimestamp(Timestamp time)
    {
        return setObject(time);
    }
    /**
     * ��ȡboolean
     * @return boolean
     */
    public boolean getBoolean()
    {
        return getInt() == 1;
    }
    /**
     * д��boolean
     * @param flg boolean
     * @return boolean
     */
    public boolean setBoolean(boolean flg)
    {
        return setInt(flg?1:0);
    }
}
