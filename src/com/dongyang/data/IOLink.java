package com.dongyang.data;

import java.net.URL;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.URLConnection;
import java.io.BufferedInputStream;
import java.net.ConnectException;
import java.io.BufferedOutputStream;
import java.util.Map;

public class IOLink
{
    private String servletPath;
    private URL url;
    private URLConnection con;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public String getServletPath()
    {
        return servletPath;
    }

    public URL getUrl()
    {
        return url;
    }

    public URLConnection getURLConnection()
    {
        return con;
    }

    public ObjectOutputStream getOut()
    {
        return out;
    }

    public ObjectInputStream getIn()
    {
        return in;
    }
    public boolean writeObject(Object object)
    {
        try{
            out.writeObject(object);
            out.flush();
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Object readObject()
    {
        try{
            return in.readObject();
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public boolean link(String servletPath)
    {
        this.servletPath = servletPath;
        try
        {
            //建立连接
            url = new URL(servletPath);
            con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            //输出对象
            out = new ObjectOutputStream(con.getOutputStream());
            writeObject("text");
            writeObject("123");
            //接收对象
            in = new ObjectInputStream(new
                    BufferedInputStream(con.getInputStream()));
            System.out.println(in.readObject());
            writeObject("text11111");
            System.out.println(in.readObject());
        } catch (ConnectException e)
        {
            e.printStackTrace();
            return false;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean close()
    {
        try
        {
            //关闭连接
            in.close();
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
