package com.dongyang.Service;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IOService extends HttpServlet
{
    public IOService()
    {
    }
    /**
     * Get方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //System.out.println("doGet start");
        try {
            //建立Input
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(request.getInputStream()));
            ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(response.getOutputStream()));
            System.out.println(inputStream.readObject());
            System.out.println(inputStream.readObject());
            //建立Output
            outputStream.writeObject("OK");
            outputStream.flush();
            System.out.println(inputStream.readObject());
            outputStream.writeObject("end");
            outputStream.flush();
            /*while("end".equals(inObject))
            {
                System.out.println(inObject);
                outputStream.writeObject(inObject + " OK");
                inObject = inputStream.readObject();
            }*/
            inputStream.close();
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("doGet end");
    }
    /**
     * Post方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}
