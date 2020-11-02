package com.dongyang.util;
import java.io.*;
import java.util.Vector;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import javax.swing.ImageIcon;
import com.dongyang.tui.io.DSerializable;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.util.ArrayList;
/**
 *
 * <p>Title: 文件处理类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.3
 * @version 1.0
 */
public class FileTool
{
  /**
   * 读取byte[]数据
   * @param name String 文件名
   * @throws IOException
   * @return byte[]
   */
  public static byte[] getByte(String name) throws IOException
  {
    File file = new File(name);
    return getByte(file);
  }

  /**
   * 写入byte[]数据
   * @param name String 文件名
   * @param data byte[]
   * @throws IOException
   */
  public static void setByte(String name, byte[] data) throws IOException
  {
    File file = new File(name);
    setByte(file, data);
  }

  /**
   * 读取byte[]数据
   * @param file File 文件对象
   * @throws IOException
   * @return byte[]
   */
  public static byte[] getByte(File file) throws IOException
  {
    FileInputStream input = new FileInputStream(file);
    byte[] data = getByte(input);
    input.close();
    return data;
  }
  /**
   * 从Jar中读取byte[]数据
   * @param path String 路径名
   * @param fileName String 文件名
   * @return byte[]
   */
  public static byte[] getJarByte(String path, String fileName)
  {
      ZipFile zipFile = null;
      InputStream stream = null;
      File archive = new File(path);
      if (!archive.exists())
          return null;
      try {
          zipFile = new ZipFile(archive);
      } catch (IOException io) {
          return null;
      }
      fileName = fileName.replace('\\','/');
      ZipEntry entry = zipFile.getEntry(fileName);
      if (entry == null)
          return null;
      int size = (int) entry.getSize();
      try {
          stream = zipFile.getInputStream(entry);
          byte data[] = new byte[size];
          int n;
          for (int pos = 0; pos < size; pos += n)
              n = stream.read(data, pos, data.length - pos);

          zipFile.close();
          byte abyte0[] = data;
          return abyte0;
      } catch (IOException ioexception) {} finally {
          try {
              if (stream != null)
                  stream.close();
          } catch (IOException e) {}
      }
      return null;
  }

  /**
   * 写入byte[]数据
   * @param file File 文件对象
   * @param data byte[]
   * @throws IOException
   */
  public static void setByte(File file, byte[] data) throws IOException
  {
    FileOutputStream output = new FileOutputStream(file);
    setByte(output, data);
    output.close();
  }

  /**
   * 读取byte[]数据
   * @param input InputStream 输入流
   * @throws IOException
   * @return byte[]
   */
  public static byte[] getByte(InputStream input) throws IOException
  {
      ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
      byte b[] = new byte[1000];
      int n;
      while ((n = input.read(b)) != -1)
          out.write(b, 0, n);
      out.close();
      return out.toByteArray();

      //旧写法
    /*int count = 0;
    byte data[] = new byte[0];
    byte b[] = new byte[1024];
    int l = input.read(b, 0, b.length);
    while (l > 0)
    {
      byte b1[] = data;
      data = new byte[count + l];
      if (count > 0)
        System.arraycopy(b1, 0, data, 0, count);
      System.arraycopy(b, 0, data, count, l);
      count += l;
      l = input.read(b, 0, b.length);
    }
    return data;*/
  }

  /**
   * 读取byte[]数据
   * @param input InputStream 输入流
   * @param length int 读取长度
   * @throws IOException
   * @return byte[]
   */
  public static byte[] getByte(InputStream input, int length) throws
      IOException
  {
    int count = 0;
    byte data[] = new byte[0];
    byte b[] = new byte[1024];
    int readLength = 1024;
    if (readLength > length)
      readLength = length;
    int l = input.read(b, 0, readLength);
    while (l > 0)
    {
      byte b1[] = data;
      data = new byte[count + l];
      if (count > 0)
        System.arraycopy(b1, 0, data, 0, count);
      System.arraycopy(b, 0, data, count, l);
      count += l;
      length -= l;
      if (length == 0)
        break;
      if (readLength > length)
        readLength = length;
      l = input.read(b, 0, readLength);
    }
    return data;
  }

  /**
   * 写入byte[]数据
   * @param output OutputStream 输出流
   * @param data byte[]
   * @throws IOException
   */
  public static void setByte(OutputStream output, byte[] data) throws
      IOException
  {
    output.write(data);
  }

  /**
   * 读取Object数据
   * @param name String 文件名
   * @throws IOException
   * @return Object
   */
  public static Object getObject(String name) throws IOException
  {
    return getObject(new File(name));
  }

  /**
   * 写入Object数据
   * @param name String 文件名
   * @param object Object
   * @throws IOException
   */
  public static void setObject(String name, Object object) throws IOException
  {
      setObject(new File(name), object);
  }
  /**
   * 读取Object数据
   * @param file File 文件对象
   * @throws IOException
   * @return Object
   */
  public static Object getObject(File file) throws IOException
  {
    FileInputStream input = new FileInputStream(file);
    Object object = getObject(input);
    input.close();
    return object;
  }

  /**
   * 写入Object数据
   * @param file File 文件对象
   * @param object Object
   * @throws IOException
   */
  public static void setObject(File file, Object object) throws IOException
  {
    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(
        file));
    output.writeObject(object);
    output.close();
  }


  /**
   * 读取Object数据
   * @param input InputStream 输入流
   * @throws IOException
   * @return Object
   */
  public static Object getObject(InputStream input) throws IOException
  {
    ObjectInputStream objectInput = new ObjectInputStream(input);
    return getObject(objectInput);
  }

  /**
   * 写入Object数据
   * @param output OutputStream 输出流
   * @param object Object
   * @throws IOException
   */
  public static void setObject(OutputStream output, Object object) throws
      IOException
  {
    setObject(new ObjectOutputStream(output), object);
  }

  /**
   * 读取Object数据
   * @param input ObjectInputStream Object输入流
   * @throws IOException
   * @return Object
   */
  public static Object getObject(ObjectInputStream input) throws IOException
  {
    try
    {
      return input.readObject();
    }
    catch (ClassNotFoundException e)
    {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * 写入Object数据
   * @param output ObjectOutputStream Object输出流
   * @param object Object
   * @throws IOException
   */
  public static void setObject(ObjectOutputStream output, Object object) throws
      IOException
  {
    output.writeObject(object);
  }

  /**
   * 写入Object数据(D版)
   * @param name String 文件名
   * @param object DSerializable
   * @throws IOException
   */
  public static void setDObject(String name,DSerializable object) throws IOException
  {
      setDObject(new File(name), object);
  }
  /**
   * 写入Object数据(D版)
   * @param file File
   * @param object DSerializable
   * @throws IOException
   */
  public static void setDObject(File file, DSerializable object) throws IOException
  {
      OutputStream outputStream = new FileOutputStream(file);
      setDObject(outputStream,object);
      outputStream.close();
  }
  /**
   * 写入Object数据(D版)
   * @param outputStream OutputStream
   * @param object DSerializable
   * @throws IOException
   */
  public static void setDObject(OutputStream outputStream,DSerializable object) throws IOException
  {
      DataOutputStream dataObjectStream = new DataOutputStream(outputStream);
      dataObjectStream.writeObject(object);
  }
  /**
   * 写入Object数据(D版)
   * @param dataOutputStream DataOutputStream
   * @param object DSerializable
   * @throws IOException
   */
  public static void setDObject(DataOutputStream dataOutputStream,DSerializable object)throws IOException
  {
      object.writeObject(dataOutputStream);
  }
  /**
   * 读取Object数据(D版)
   * @param name String
   * @return DSerializable
   * @throws IOException
   */
  public static DSerializable getDObject(String name) throws IOException
  {
      return getDObject(new File(name));
  }

  /**
   * 读取Object数据(D版)
   * @param file File
   * @return DSerializable
   * @throws IOException
   */
  public static DSerializable getDObject(File file) throws IOException
  {
      InputStream inputStream = new FileInputStream(file);
      DSerializable obj = getDObject(inputStream);
      inputStream.close();
      return obj;
  }
  /**
   * 读取Object数据(D版)
   * @param input InputStream
   * @return DSerializable
   * @throws IOException
   */
  public static DSerializable getDObject(InputStream input)
          throws IOException
  {
      DataInputStream objectInput = new DataInputStream(input);
      return objectInput.readObject();
  }
  /**
   * 读取Object数据(D版)
   * @param name String
   * @param object DSerializable
   * @throws IOException
   */
  public static void getDObject(String name,DSerializable object) throws IOException
  {
      getDObject(new File(name),object);
  }
  /**
   * 读取Object数据(D版)
   * @param file File
   * @param object DSerializable
   * @throws IOException
   */
  public static void getDObject(File file,DSerializable object) throws IOException
  {
      InputStream inputStream = new FileInputStream(file);
      getDObject(inputStream,object);
      inputStream.close();
  }
  /**
   * 读取Object数据(D版)
   * @param input InputStream
   * @param object DSerializable
   * @throws IOException
   */
  public static void getDObject(InputStream input,DSerializable object)
          throws IOException
  {
      if(object == null)
          throw new IOException("对象为空");
      DataInputStream objectInput = new DataInputStream(input);
      String name = objectInput.readString();
      if(!object.getClass().getName().equals(name))
          throw new IOException("类型不匹配" + object.getClass().getName() + " " + name);
      object.readObject(objectInput);
  }
  /**
   * 读取Object数据(D版)
   * @param input DataInputStream
   * @param object DSerializable
   * @throws IOException
   */
  public static void getDObject(DataInputStream input,DSerializable object)
          throws IOException
  {

      object.readObject(input);
  }
  /**
   * 读取String数据
   * @param name String 文件名
   * @throws IOException
   * @return Vector
   */
  public static Vector getString(String name) throws IOException
  {
    File file = new File(name);
    return getString(file);
  }

  /**
   * 写入String数据(追加方式写入)
   * @param name String 文件名
   * @param s String 数据
   * @throws IOException
   */
  public static void setString(String name, String s) throws IOException
  {
      setString(name,s,true);
  }
  /**
   * 写入String数据
   * @param name String 文件名
   * @param s String 数据
   * @param flg boolean true 追加 false 覆盖
   * @throws IOException
   */
  public static void setString(String name, String s,boolean flg) throws IOException
  {
    File file = new File(name);
    setString(file, s,flg);
  }

  /**
   * 读取String数据
   * @param file File 文件对象
   * @throws IOException
   * @return Vector
   */
  public static Vector getString(File file) throws IOException
  {
    FileReader input = new FileReader(file);
    Vector data = getString(new BufferedReader(input));
    input.close();
    return data;
  }

  /**
   * 写入String数据(追加方式写入)
   * @param file File 文件对象
   * @param s String
   * @throws IOException
   */
  public static void setString(File file, String s) throws IOException
  {
      setString(file,s,true);
  }
  /**
   * 写入String数据
   * @param file File
   * @param s String
   * @param flg boolean true 追加 false 覆盖
   * @throws IOException
   */
  public static void setString(File file, String s,boolean flg) throws IOException
  {
    BufferedWriter output = new BufferedWriter(new FileWriter(file, flg));
    setString(output, s);
    output.close();
  }

  /**
   * 读取String数据
   * @param input BufferedReader 读入流
   * @throws IOException
   * @return Vector
   */
  public static Vector getString(BufferedReader input) throws IOException
  {
    Vector data = new Vector();
    String s = input.readLine();
    while (s != null)
    {
      data.add(s);
      s = input.readLine();
    }
    return data;
  }
  /**
   * 得到文件目录名
   * @param filename String 文件名
   * @return String 目录名
   */
  public static String getDir(String filename) {
      int i;
      for (i = filename.length() - 1; i >= 0; i--)
          if (filename.charAt(i) == '\\')
              break;
      if (i < 0)
          return "";
      return filename.substring(0, i);
  }

  /**
   * 写入String数据
   * @param output BufferedWriter 写入流
   * @param s String 写入字串
   * @throws IOException
   */
  public static void setString(BufferedWriter output, String s) throws
      IOException
  {
    output.write(s);
    output.newLine();
  }
  /**
   * 从服务器加载图标
   * @param fileName String
   * @return ImageIcon
   */
  public static ImageIcon getImage(String fileName)
  {
      ImageIcon icon = null;
      try{
          icon = new ImageIcon(getByte(fileName));
      }catch(IOException e)
      {
      }
      return icon;
  }
  /**
   * 列表目录
   * @param name String
   * @return String[]
   */
  public static String[] getDirs(String name)
  {
      ArrayList list = new ArrayList();
      File dirFile = new File(name);
      File docFiles[] = dirFile.listFiles();
      for(int i = 0;i < docFiles.length;i++)
          if(docFiles[i].isDirectory())
              list.add(docFiles[i].getName());
      String s[] = (String[])list.toArray(new String[]{});
      StringTool.orderStringArray(s);
      return s;
  }
}
