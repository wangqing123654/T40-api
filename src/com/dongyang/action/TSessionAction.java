package com.dongyang.action;

import java.io.File;
import com.dongyang.data.TParm;
import com.dongyang.util.FileTool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.ArrayList;


public class TSessionAction  extends TAction{
    /**
     * 得到属性
     * @param parm TParm NAME
     * @return TParm VALUE
     */
    public TParm getSessionAttribute(TParm parm) {
        String name = parm.getValue("NAME");
        if (name.trim().length() == 0)
            return err(-1,"NAME is null","NAME");
        HttpServletRequest request = (HttpServletRequest)parm.getData("SYSTEM","REQUEST");
        if(request == null)
            return err(-1,"SYSTEM.REQUEST is null");
        HttpSession session = request.getSession();
        TParm result = new TParm();
        result.setData("VALUE", session.getAttribute(name));
        return result;
    }
    /**
     * 设置属性
     * @param parm TParm NAME,VALUE
     * @return TParm
     */
    public TParm setSessionAttribute(TParm parm)
    {
        String name = parm.getValue("NAME");
        if (name.trim().length() == 0)
            return err(-1,"NAME is null","NAME");
        String value = parm.getValue("VALUE");
        HttpServletRequest request = (HttpServletRequest)parm.getData("SYSTEM","REQUEST");
        if(request == null)
            return err(-1,"SYSTEM.REQUEST is null");
        HttpSession session = request.getSession();
        session.setAttribute(name,value);
        return new TParm();
    }
    /**
     * 得到属性列表
     * @param parm TParm
     * @return TParm LIST
     */
    public TParm getSessionAttributeNames(TParm parm)
    {
        HttpServletRequest request = (HttpServletRequest)parm.getData("SYSTEM","REQUEST");
        if(request == null)
            return err(-1,"SYSTEM.REQUEST is null");
        HttpSession session = request.getSession();
        Enumeration enumeration = session.getAttributeNames();
        ArrayList list = new ArrayList();
        while(enumeration.hasMoreElements())
            list.add(enumeration.nextElement());
        TParm result = new TParm();
        result.setData("LIST",(String[])list.toArray(new String[]{}));
        return result;
    }
}
