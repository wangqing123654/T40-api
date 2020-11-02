package com.dongyang.root.T;

import java.util.Map;
import java.util.HashMap;
import com.dongyang.util.FileTool;

/**
 *
 * <p>Title: Oa数据分析器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.29
 * @version 1.0
 */
public class Oa
        implements java.io.Serializable
{
    /**
     * 外壳
     */
    private Ot ot;
    /**
     * 逻辑处理
     */
    private Map map = new HashMap();
    /**
     * 设置外壳
     * @param ot Ot
     */
    public void setOt(Ot ot)
    {
        this.ot = ot;
    }
    /**
     * 得到外壳
     * @return Ot
     */
    public Ot getOt()
    {
        return ot;
    }
    /**
     * 入口
     * @param io int 通道
     * @param m Object 信息
     * @return Object
     */
    public Object inM(int io,Object m)
    {
        System.out.println(getOt().getID() + ":" +io + " " + m);
        return "OK";
    }
    /**
     * 入口
     * @param io int 通道
     * @param m String 信息
     * @param parm Object 参数
     * @return Object
     */
    public Object inM(int io,String m,Object parm)
    {
        Object value = getMap(m);
        if(value != null)
            return value;
        return null;
    }
    /**
     * 得到处理
     * @param name String
     * @return Object
     */
    public Object getMap(String name)
    {
        return map.get(name);
    }
    /**
     * 设置处理
     * @param name String
     * @param object Object
     */
    public void setMap(String name,Object object)
    {
        map.put(name,object);
    }
    /**
     * 保存
     * @param fileName String 文件名
     * @return boolean true 成功 false 失败
     */
    public boolean save(String fileName)
    {
        try
        {
            FileTool.setObject(fileName, map);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 读取
     * @param fileName String 文件名
     * @return boolean true 成功 false 失败
     */
    public boolean load(String fileName)
    {
        try
        {
            Map data = (Map)FileTool.getObject(fileName);
            if(data == null)
                return false;
            map = data;
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String args[])
    {
        Oa oa = new Oa();
    }
}
