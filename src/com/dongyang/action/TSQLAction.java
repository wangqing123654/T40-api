package com.dongyang.action;

import com.dongyang.data.TParm;
import com.dongyang.db.TConnection;

public class TSQLAction extends TAction{
    /**
     * sql查询
     * @param parm TParm
     * @return TParm
     */
    public TParm executeQuery(TParm parm)
    {
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        TParm result = connection.executeQuery(parm);
        connection.close();
        return result;
    }
    public TParm executeParmQuery(TParm parm)
    {
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        String parmCode = parm.getValue("ACTION","PARM_CODE");
        if(parmCode.length() > 0)
        {
            Object[] vParm = (Object[])getResultPools().get(parmCode);
            if(vParm == null)
                return err(-1,"超时请重新查询");
            parm.setData("ACTION","RESULTSET",vParm[0]);
            parm.setData("ACTION","STATEMENT",vParm[1]);
            TParm result = connection.parseParmData(parm);
            connection.close();
            return result;
        }
        if(!connection.executeParmQuery(parm))
            return err(parm);
        TParm result = connection.parseParmData(parm);
        parmCode = "PARM" + parm.hashCode();
        getResultPools().put(parmCode,new Object[]{parm.getData("ACTION","RESULTSET"),
                             parm.getData("ACTION","STATEMENT")});
        result.setData("ACTIPN","PARM_CODE",parmCode);
        connection.close();
        return result;
    }
    public TParm executeResultClose(TParm parm)
    {
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        String parmCode = parm.getValue("ACTION","PARM_CODE");
        if(parmCode.length() == 0)
            return err(-1,"PARM_CODE is null","PARM_CODE");
        Object[] vParm = (Object[])getResultPools().get(parmCode);
        if(vParm == null)
            return err(-1,"超时请重新查询");
        parm.setData("ACTION","RESULTSET",vParm[0]);
        parm.setData("ACTION","STATEMENT",vParm[1]);
        connection.closeParmData(parm);
        connection.close();
        TParm result = new TParm();
        result.setData("RETURN","close result ok");
        return result;
    }
    /**
     * sql修改
     * @param parm TParm
     * @return TParm
     */
    public TParm executeUpdate(TParm parm)
    {
        boolean ConnectionContinue = parm.getBoolean("ACTION","CONNECTION_CONTINUE");
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        TParm result = new TParm();
        boolean state = connection.executeUpdate(parm);
        if(!state)
            result.setErr(parm);
        if(!ConnectionContinue)
        {
            if (!state)
                connection.rollback();
            else
                connection.commit();
            connection.close();
        }
        else if(result.getValue("ACTION","CONNECTION_CODE").length() == 0)
            result.setData("ACTION","CONNECTION_CODE",connection.getIndexCode());
        result.setData("RETURN","modify ok");
        return result;
    }
    /**
     * 提交
     * @param parm TParm
     * @return TParm
     */
    public TParm executeCommit(TParm parm)
    {
        boolean ConnectionContinue = parm.getBoolean("ACTION","CONNECTION_CONTINUE");
        if(!ConnectionContinue)
            return err(-1,"CONNECTION_CONTINUE is false");
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        connection.commit();
        TParm result = new TParm();
        result.setData("RETURN","commit ok");
        return result;
    }
    /**
     * 撤消
     * @param parm TParm
     * @return TParm
     */
    public TParm executeRollback(TParm parm)
    {
        boolean ConnectionContinue = parm.getBoolean("ACTION","CONNECTION_CONTINUE");
        if(!ConnectionContinue)
            return err(-1,"CONNECTION_CONTINUE is false");
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        connection.rollback();
        TParm result = new TParm();
        result.setData("RETURN","rollback ok");
        return result;
    }
    /**
     * 建立连接
     * @param parm TParm
     * @return TParm
     */
    public TParm executeOpenConnection(TParm parm)
    {
        boolean ConnectionContinue = parm.getBoolean("ACTION","CONNECTION_CONTINUE");
        if(!ConnectionContinue)
            return err(-1,"CONNECTION_CONTINUE is false");
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        TParm result = new TParm();
        result.setData("ACTION","CONNECTION_CODE",connection.getIndexCode());
        result.setData("RETURN","open ok");
        return result;
    }
    /**
     * 关闭连接
     * @param parm TParm
     * @return TParm
     */
    public TParm executeCloseConnection(TParm parm)
    {
        boolean ConnectionContinue = parm.getBoolean("ACTION","CONNECTION_CONTINUE");
        if(!ConnectionContinue)
            return err(-1,"CONNECTION_CONTINUE is false");
        TConnection connection = getConnection(parm);
        if(connection == null)
            return getErrParm();
        TParm result = new TParm();
        connection.close();
        result.setData("RETURN","close ok");
        return result;
    }
}
