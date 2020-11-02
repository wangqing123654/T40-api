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
     * �õ�����
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
     * ��¼(�õ�������Ϣ)
     * @param userID String �û����
     * @return Map
     */
    /*public Map login(String userID)
    {
        return select("SELECT ID,NAME,PASSWORD,ONLY_ONE"+
                      " FROM SKT_USER"+
                      " WHERE ID='" + userID + "'");
    }*/
    /**
     * ��¼(�õ�������Ϣ)
     * @param userID String
     * @return TParm
     */
    public synchronized TParm login(String userID)
    {
        TParm parm = userInf.getBuffer(userInf.PRIMARY);
        if(parm == null || parm.getCount() == 0)
            return TParm.newErrParm(-1,"SKT_USER ��������Ч!");
        int index = getInfRow(userID);
        if(index == -1)
            return TParm.newErrParm(-1,"İ���û�" + userID + "!");
        return parm.getRow(index);
    }
    /**
     * �õ�ID������
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
     * �޸��û���
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
     * �޸��û�����
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
     * �������Ա�б�
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
     * ��ѯ���漰��Ա�б�
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
