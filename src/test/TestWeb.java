package test;

import java.net.URL;
import java.net.URLConnection;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.net.ConnectException;
import com.dongyang.data.TParm;

public class TestWeb {
    public static void main(String args[]) {
        String servletPath = "http://lzk:8090/web/servlet/DataService";
        try {
            //建立连接
            URL url = new URL(servletPath);
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            //输出对象
            ObjectOutputStream out = new ObjectOutputStream(con.getOutputStream());
            TParm parm = new TParm();
            parm.setData("SYSTEM","ACTION",0x2);
            parm.setData("SYSTEM","ACTION_CLASS_NAME","action.test.TestAction");
            parm.setData("SYSTEM","ACTION_METHOD_NAME","test");
            out.writeObject(parm.getData());
            out.flush();
            //接收对象
            ObjectInputStream in = new ObjectInputStream(new
                    BufferedInputStream(con.getInputStream()));
            Object outObject = in.readObject();
            System.out.println(outObject.getClass().getName());
            System.out.println(outObject);
            //关闭连接
            in.close();
            out.close();
        } catch (ConnectException e) {
            System.out.println("ERR:没有找到网站" + servletPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
