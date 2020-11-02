package com.dongyang.root.server;

import com.dongyang.jdo.TStrike;
import com.dongyang.data.TParm;
import java.util.Map;
import com.dongyang.jdo.TDS;
import java.util.Vector;
import java.util.ArrayList;

public class ModuleTool extends TStrike
{
    private TDS userInf;
    private TDS userGroup;
    private static ModuleTool instance;
    /**
     * 得到工具
     * @return ModuleTool
     */
    public synchronized static ModuleTool getTool()
    {
        if(instance == null)
            instance = new ModuleTool();
        return instance;
    }
    public ModuleTool()
    {
        userInf = new TDS();
        userInf.setSQL("SELECT ID,NAME,PASSWORD,ONLY_ONE FROM SKT_USER");
        userInf.retrieve();
        userGroup = new TDS();
        userGroup.setSQL("SELECT ID,LINK_ID,NAME,GROUP_ID FROM SKT_GROUP");
    }
    /**
     * 登录(得到基本信息)
     * @param userID String 用户编号
     * @return Map
     */
    /*public Map login(String userID)
    {
        return select("SELECT ID,NAME,PASSWORD,ONLY_ONE"+
                      " FROM SKT_USER"+
                      " WHERE ID='" + userID + "'");
    }*/
    /**
     * 登录(得到基本信息)
     * @param userID String
     * @return TParm
     */
    public synchronized TParm login(String userID)
    {
        TParm parm = userInf.getBuffer(userInf.PRIMARY);
        if(parm == null || parm.getCount() == 0)
            return TParm.newErrParm(-1,"SKT_USER 表数据无效!");
        int index = getInfRow(userID);
        if(index == -1)
            return TParm.newErrParm(-1,"陌生用户" + userID + "!");
        return parm.getRow(index);
    }
    /**
     * 得到ID所在行
     * @param id String
     * @return int
     */
    public int getInfRow(String id)
    {
        if(id.equals("#System.Reset#"))
        {
            userInf.retrieve();
            userGroup.retrieve();
            return -1;
        }
        TParm parm = userInf.getBuffer(userInf.PRIMARY);
        if(parm == null || parm.getCount() == 0)
            return -1;
        Vector ids = (Vector)parm.getData("ID");
        if(ids == null)
            return -1;
        return ids.indexOf(id);
    }
    /**
     * 修改用户名
     * @param id String
     * @param name String
     * @return boolean
     */
    public synchronized boolean setUserName(String id,String name)
    {
        int index = getInfRow(id);
        if(index == -1)
            return false;
        userInf.setItem(index,"NAME",name);
        return userInf.update();
    }
    /**
     * 修改用户密码
     * @param id String
     * @param password String
     * @return boolean
     */
    public synchronized boolean setUserPassword(String id,String password)
    {
        int index = getInfRow(id);
        if(index == -1)
            return false;
        userInf.setItem(index,"PASSWORD",password);
        return userInf.update();
    }
    /**
     * 查找组成员列表
     * @param id String
     * @return String[]
     */
    public synchronized String[] getGroupList(String id)
    {
        TParm parm = userGroup.getBuffer(userInf.PRIMARY);
        if(parm == null || parm.getCount() == 0)
            return new String[]{};
        Vector ids = (Vector)parm.getData("ID");
        if(ids == null)
            return new String[]{};
        ArrayList list = new ArrayList();
        int index;
        while((index = ids.indexOf(id)) != -1)
            list.add(userGroup.getItemData(index,"LINK_ID"));
        return (String[])list.toArray(new String[]{});
    }
    /**
     * 查询组涉及人员列表
     * @param id String
     * @return String[]
     */
    public synchronized String[] getLinkList(String id)
    {
        TParm parm = userGroup.getBuffer(userInf.PRIMARY);
        if(parm == null || parm.getCount() == 0)
            return new String[]{};
        Vector ids = (Vector)parm.getData("LINK_ID");
        if(ids == null)
            return new String[]{};
        ArrayList list = new ArrayList();
        int index;
        while((index = ids.indexOf(id)) != -1)
            list.add(userGroup.getItemData(index,"ID"));
        return (String[])list.toArray(new String[]{});
    }
}
