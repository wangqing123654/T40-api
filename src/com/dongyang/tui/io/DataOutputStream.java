package com.dongyang.tui.io;

import java.io.OutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import com.dongyang.util.StringTool;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.io.FileOutputStream;

/**
 *
 * <p>Title: ����������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.25
 * @version 1.0
 */
public class DataOutputStream
{
    /**
     * ������
     */
    private OutputStream stream;
    /**
     * ������
     * @param stream OutputStream
     */
    public DataOutputStream(OutputStream stream)
    {
        this.stream = stream;
    }
    /**
     * ������
     * @param fileName String
     */
    public DataOutputStream(String fileName)
    {
        try{
            stream = new FileOutputStream(fileName);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * д��booleanֵ
     * @param b boolean
     * @throws IOException
     */
    public void writeBoolean(boolean b)
            throws IOException
    {
        stream.write(b?1:0);
    }
    /**
     * д��booleanֵ
     * @param id int
     * @param b boolean
     * @param d boolean
     * @throws IOException
     */
    public void writeBoolean(int id,boolean b,boolean d)
            throws IOException
    {
        if(b == d)
            return;
        writeShort(id);
        writeBoolean(b);
    }
    /**
     * д��Shortֵ
     * @param v int
     * @throws IOException
     */
    public void writeShort(int v) throws IOException {
        stream.write((v >>> 8) & 0xFF);
        stream.write(v & 0xFF);
    }
    /**
     * д��intֵ
     * @param x int
     * @throws IOException
     */
    public void writeInt(int x)
            throws IOException
    {
        stream.write((x >>> 24) & 0xFF);
        stream.write((x >>> 16) & 0xFF);
        stream.write((x >>>  8) & 0xFF);
        stream.write(x & 0xFF);
    }
    /**
     * д��intֵ
     * @param id int
     * @param x int
     * @param d int
     * @throws IOException
     */
    public void writeInt(int id,int x,int d)throws IOException
    {
        if(x == d)
            return;
        writeShort(id);
        writeInt(x);
    }
    /**
     * д��longֵ
     * @param v long
     * @throws IOException
     */
    public void writeLong(long v)
            throws IOException {
        stream.write((byte) (v >>> 56));
        stream.write((byte) (v >>> 48));
        stream.write((byte) (v >>> 40));
        stream.write((byte) (v >>> 32));
        stream.write((byte) (v >>> 24));
        stream.write((byte) (v >>> 16));
        stream.write((byte) (v >>> 8));
        stream.write((byte) v);
    }
    /**
     * д��float ֵ
     * @param v float
     * @throws IOException
     */
    public void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }
    /**
     * д��float ֵ
     * @param id int
     * @param x float
     * @param d float
     * @throws IOException
     */
    public void writeFloat(int id,float x,float d)throws IOException
    {
        if(x == d)
            return;
        writeShort(id);
        writeFloat(x);
    }
    /**
     * д��double ֵ
     * @param v double
     * @throws IOException
     */
    public void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }
    /**
     * д��double ֵ
     * @param id int
     * @param x double
     * @param d double
     * @throws IOException
     */
    public void writeDouble(int id,double x,double d)throws IOException
    {
        if(x == d)
            return;
        writeShort(id);
        writeDouble(x);
    }
    /**
     * д��Timestampֵ
     * @param t Timestamp
     * @throws IOException
     */
    public void writeTimestamp(Timestamp t) throws IOException {
        writeLong(t.getTime());
    }
    /**
     * д��byteֵ
     * @param v int
     * @throws IOException
     */
    public void writeByte(int v) throws IOException {
        stream.write(v);
    }
    /**
     * д��byte����ֵ
     * @param id int
     * @param x int
     * @param d int
     * @throws IOException
     */
    public void writeByte(int id,int x,int d)throws IOException
    {
        if(x == d)
            return;
        writeShort(id);
        writeByte(x);
    }
    /**
     * д��byte����ֵ
     * @param data byte[]
     * @throws IOException
     */
    public void writeBytes(byte[] data) throws IOException {
        if(data == null)
        {
            writeInt(-1);
            return;
        }
        writeInt(data.length);
        stream.write(data);
    }
    /**
     * д��charֵ
     * @param v int
     * @throws IOException
     */
    public void writeChar(int v) throws IOException {
        writeShort(v);
    }
    /**
     * ���ո�
     * @param c int
     * @throws IOException
     */
    public void WOC(int c)throws IOException
    {
        WO(StringTool.fill(" ",c * 2));
    }
    /**
     * д���ַ���
     * @param obj Object
     * @throws IOException
     */
    public void WO(Object obj)throws IOException
    {
        String s = "" + obj;
        if(s.length() == 0)
            return;
        byte bytes[] = s.getBytes();
        stream.write(bytes);
    }
    /**
     * д���ִ�
     * @param obj Object
     * @param c int
     * @throws IOException
     */
    public void WO(Object obj,int c)throws IOException
    {
        WOC(c);
        WO(obj);
        WO("\n");
    }
    /**
     * д���ַ���
     * @param s String
     * @throws IOException
     */
    public void writeString(String s)throws IOException
    {
        if(s == null)
        {
            writeByte(-128);
            return;
        }
        byte bytes[] = s.getBytes();
        if(bytes.length == 0)
        {
            writeByte(0);
            return;
        }
        if(bytes.length < 127)
            writeByte(-bytes.length);
        else if(bytes.length <= 0xFF)
        {
            writeByte(127);
            writeByte(bytes.length);
        }
        else if(bytes.length <= 0xFFFF)
        {
            writeByte(126);
            writeShort(bytes.length);
        }
        else
        {
            writeByte(125);
            writeInt(bytes.length);
        }
        stream.write(bytes);
    }
    /**
     * �����������
     * @param id int
     * @param name String
     * @param s Object
     * @param d Object
     * @param c int
     * @throws IOException
     */
    public void WO(int id,String name,Object s,Object d,int c)throws IOException
    {
        if(StringTool.equals(s,d))
            return;
        WOC(c);
        String sid = "";
        if(id > 0)
            sid = " ID=" + id + " ";
        WO("<" + name + sid + ">");
        WO(s);
        WO("</" + name + ">\n");
    }
    /**
     * д���ַ���
     * @param id int
     * @param s String
     * @param d String
     * @throws IOException
     */
    public void writeString(int id,String s,String d)throws IOException
    {
        if(StringTool.equals(s,d))
            return;
        writeShort(id);
        writeString(s);
    }
    /**
     * д����
     * @param object DSerializable
     * @throws IOException
     */
    public void writeObject(DSerializable object)throws IOException
    {
        if(object == null)
        {
            writeString(null);
            return;
        }
        writeString(object.getClass().getName());
        object.writeObject(this);
    }
    /**
     * дMap
     * @param map Map
     * @throws IOException
     */
    public void writeMap(Map map)throws IOException
    {
        if(map == null)
        {
            writeInt(-1);
            return;
        }
        int count = map.size();
        writeInt(count);
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext())
        {
            Object key = (String)iterator.next();
            Object obj = map.get(key);
            writeBaseObject(key);
            writeBaseObject(obj);
        }
    }
    /**
     * дVector
     * @param data Vector
     * @throws IOException
     */
    public void writeVector(Vector data)throws IOException
    {
        if(data == null)
        {
            writeInt(-1);
            return;
        }
        Object obj[] = data.toArray();
        writeInt(obj.length);
        for(int i = 0;i < obj.length;i++)
            writeBaseObject(obj[i]);
    }
    /**
     * дArrayList
     * @param data ArrayList
     * @throws IOException
     */
    public void writeArrayList(ArrayList data)throws IOException
    {
        if(data == null)
        {
            writeInt(-1);
            return;
        }
        int count = data.size();
        writeInt(count);
        for(int i = 0;i < count;i++)
            writeBaseObject(data.get(i));
    }
    /**
     * дString����
     * @param s String[]
     * @throws IOException
     */
    public void writeStrings(String s[])throws IOException
    {
        if(s == null)
        {
            writeInt(-1);
            return;
        }
        int count = s.length;
        writeInt(count);
        for(int i = 0;i < count;i++)
            writeString(s[i]);
    }
    /**
     * �õ����ݵĶ�������
     * @param obj Object
     * @throws IOException
     */
    public void writeBaseObject(Object obj)throws IOException
    {
        if(obj == null)
        {
            writeChar(0);
            return;
        }
        if(obj instanceof Integer)
        {
            writeChar(1);
            writeInt((Integer)obj);
            return;
        }
        if(obj instanceof Long)
        {
            writeChar(2);
            writeLong((Long)obj);
            return;
        }
        if(obj instanceof Double)
        {
            writeChar(3);
            writeDouble((Double)obj);
            return;
        }
        if(obj instanceof Float)
        {
            writeChar(4);
            writeFloat((Float)obj);
            return;
        }
        if(obj instanceof Boolean)
        {
            writeChar(5);
            writeBoolean((Boolean)obj);
            return;
        }
        if(obj instanceof Byte)
        {
            writeChar(6);
            writeByte((Byte)obj);
            return;
        }
        if(obj instanceof Short)
        {
            writeChar(7);
            writeShort((Short)obj);
            return;
        }
        if(obj instanceof Character)
        {
            writeChar(8);
            writeChar((Character)obj);
            return;
        }
        if(obj instanceof String)
        {
            writeChar(9);
            writeString((String)obj);
            return;
        }
        if(obj instanceof Timestamp)
        {
            writeChar(10);
            writeTimestamp((Timestamp)obj);
            return;
        }
        if(obj instanceof Map)
        {
            writeChar(11);
            writeMap((Map)obj);
            return;
        }
        if(obj instanceof Vector)
        {
            writeChar(12);
            writeVector((Vector)obj);
            return;
        }
        if(obj instanceof ArrayList)
        {
            writeChar(13);
            writeArrayList((ArrayList)obj);
            return;
        }
        if(obj instanceof String[])
        {
            writeChar(14);
            writeStrings((String[])obj);
        }
        throw new IOException(obj.getClass().getName() + "���ܴ��л�");
    }
    /**
     * �ر�
     */
    public void close()
    {
        try{
            stream.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ������
     * @param s int[][]
     * @throws IOException
     */
    public void writeInts(int s[][])throws IOException
    {
        if(s == null || s.length == 0 || s[0].length == 0)
        {
            writeInt(-1);
            return;
        }
        writeInt(s.length);
        for(int i = 0;i < s.length;i++){
            writeInt(s[i].length);
            for (int j = 0; j < s[i].length;j++)
                writeInt(s[i][j]);
        }
    }

    public static void main(String args[])
    {
        try{
            DataOutputStream d = new DataOutputStream(
                    "C:/JavaHis/JavaHisWord/a.wav");
            d.writeString(null);
            d.writeString(StringTool.fill("a",900));
            d.close();
            DataInputStream i = new DataInputStream("C:/JavaHis/JavaHisWord/a.wav");
            String s = i.readString();
            System.out.println(s);
            System.out.println(s.length());

            s = i.readString();
            System.out.println(s);
            System.out.println(s.length());

            i.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
