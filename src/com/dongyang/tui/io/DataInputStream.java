package com.dongyang.tui.io;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import com.dongyang.util.FileTool;
import java.sql.Timestamp;
import com.dongyang.util.StringTool;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;

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
public class DataInputStream
{
    /**
     * ������
     */
    private InputStream stream;
    /**
     * ������
     * @param stream InputStream
     */
    public DataInputStream(InputStream stream)
    {
        this.stream = stream;
    }
    /**
     * ������
     * @param fileName String
     */
    public DataInputStream(String fileName)
    {
        try
        {
            stream = new FileInputStream(fileName);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * �õ���
     * @return InputStream
     */
    public InputStream getStream()
    {
        return stream;
    }
    /**
     * ��ȡbooleanֵ
     * @return boolean
     * @throws IOException
     */
    public boolean readBoolean()
            throws IOException
    {
        return stream.read() == 1;
    }
    /**
     * ��ȡshortֵ
     * @return short
     * @throws IOException
     */
    public short readShort() throws IOException {
        return (short)((stream.read() << 8) |
                       (stream.read() << 0));
    }
    /**
     * ��ȡintֵ
     * @return int
     * @throws IOException
     */
    public int readInt()
            throws IOException
    {
        return (stream.read() << 24) |
                (stream.read() << 16) |
                (stream.read() << 8) |
                stream.read();
    }
    /**
     * ��ȡlongֵ
     * @return long
     * @throws IOException
     */
    public final long readLong() throws IOException {
        return (((long)stream.read() << 56) |
                ((long)stream.read() << 48) |
                ((long)stream.read() << 40) |
                ((long)stream.read() << 32) |
                ((long)stream.read() << 24) |
                (stream.read() << 16) |
                (stream.read() <<  8) |
                stream.read());
    }
    /**
     * ��ȡfloatֵ
     * @return float
     * @throws IOException
     */
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }
    /**
     * ��ȡdoubleֵ
     * @return double
     * @throws IOException
     */
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }
    /**
     * ��ȡTimestampֵ
     * @return Timestamp
     * @throws IOException
     */
    public Timestamp readTimestamp() throws IOException {
        return new Timestamp(readLong());
    }
    /**
     * ��ȡbyteֵ
     * @return byte
     * @throws IOException
     */
    public byte readByte() throws IOException {
        return (byte)stream.read();
    }
    /**
     * ��ȡbyte����ֵ
     * @return byte[]
     * @throws IOException
     */
    public byte[] readBytes() throws IOException {
        int size = readInt();
        if(size == -1)
            return null;
        return FileTool.getByte(stream,size);
    }
    /**
     * ��ȡcharֵ
     * @return char
     * @throws IOException
     */
    public char readChar() throws IOException {
        return (char)readShort();
    }
    /**
     * ��һ���ֽ�
     * @return int
     * @throws IOException
     */
    public int read()throws IOException
    {
        return stream.read();
    }
    /**
     * ��ȡStringֵ
     * @return String
     * @throws IOException
     */
    public String readString()throws IOException
    {
        int size = readByte();
        if(size == -128)
            return null;
        if(size == 0)
            return "";
        if(size < 0)
            size = -size;
        else if(size == 127)
        {
            size = readByte();
            if(size < 0)
                size = size << 24 >>> 24;
        }
        else if(size == 126)
        {
            size = readShort();
            if(size < 0)
                size = size << 16 >> 16;
        }
        else
            size = readInt();

        byte[] data = FileTool.getByte(stream,size);
        return new String(data);
    }
    /**
     * ��ȡ����
     * @return DSerializable
     * @throws IOException
     */
    public DSerializable readObject()throws IOException
    {
        String name = readString();
        if(name == null)
            return null;
        try{
            DSerializable object = (DSerializable) getClass().getClassLoader().
                                   loadClass(name).newInstance();
            object.readObject(this);
            return object;
        }catch(Exception e)
        {
            System.out.println("ERR û���ҵ�����" + name);
            e.printStackTrace();
            return null;
        }
    }
    /**
     * ��ȡMap
     * @return Map
     * @throws IOException
     */
    public Map readMap()throws IOException
    {
        int size = readInt();
        if(size == -1)
            return null;
        Map map = new HashMap();
        for(int i = 0;i < size;i++)
            map.put(readBaseObject(), readBaseObject());
        return map;
    }
    /**
     * ��ȡVector
     * @return Vector
     * @throws IOException
     */
    public Vector readVector()throws IOException
    {
        int size = readInt();
        if(size == -1)
            return null;
        Vector v = new Vector();
        for(int i = 0;i < size;i++)
            v.add(readBaseObject());
        return v;
    }
    /**
     * ��ȡArrayList
     * @return ArrayList
     * @throws IOException
     */
    public ArrayList readArrayList()throws IOException
    {
        int size = readInt();
        if(size == -1)
            return null;
        ArrayList v = new ArrayList();
        for(int i = 0;i < size;i++)
            v.add(readBaseObject());
        return v;
    }
    /**
     * ��ȡ�ַ�������
     * @return String[]
     * @throws IOException
     */
    public String[] readStrings()throws IOException
    {
        int size = readInt();
        if(size == -1)
            return null;
        String[] s = new String[size];
        for(int i = 0;i < size;i++)
            s[i] = readString();
        return s;
    }
    /**
     * ��ȡ��������
     * @return Object
     * @throws IOException
     */
    public Object readBaseObject()throws IOException
    {
        int type = readChar();
        switch(type)
        {
        case 0:
            return null;
        case 1:
            return readInt();
        case 2:
            return readLong();
        case 3:
            return readDouble();
        case 4:
            return readFloat();
        case 5:
            return readBoolean();
        case 6:
            return readShort();
        case 7:
            return readShort();
        case 8:
            return readChar();
        case 9:
            return readString();
        case 10:
            return readTimestamp();
        case 11:
            return readMap();
        case 12:
            return readVector();
        case 13:
            return readArrayList();
        case 14:
            return readStrings();
        }
        throw new IOException("�޷�ʶ������" + type);
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
     * @return int[][]
     * @throws IOException
     */
    public int[][] readInts()throws IOException
    {
        int row = readInt();
        if(row == -1)
            return null;
        int[][] s = new int[row][];
        for(int i = 0;i < row;i++){
            int column = readInt();
            int[] a = new int[column];
            for (int j = 0; j < column; j++)
                a[j] = readInt();
            s[i] = a;
        }
        return s;
    }

    public static void main(String args[])
    {

        byte b = -82;
        int x = b << 24 >>> 24;


        System.out.println("b=" + b + " " + x);
        /*try{
            DataOutputStream s = new DataOutputStream(new FileOutputStream("c:/aaa/a.x"));
            String t = null;//StringTool.fill0("ABC",127);
            s.writeInt(Integer.MAX_VALUE);
            s.writeString(t);
            s.writeLong(Long.MAX_VALUE);
            DataInputStream s1 = new DataInputStream(new FileInputStream("c:/aaa/a.x"));
            System.out.println(Integer.MAX_VALUE);
            System.out.println("OK " + s1.readInt());
            System.out.println(s1.readString());
            System.out.println(Long.MAX_VALUE);
            System.out.println(s1.readLong());
        }catch(Exception e)
        {

        }*/
    }
}
