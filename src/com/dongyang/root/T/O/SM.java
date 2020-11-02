package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;
import com.dongyang.root.T.Ot;

/**
 *
 * <p>Title: 消息包</p>
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
public class SM
        implements java.io.Serializable
{
    /**
     * 类型
     */
    private String type;
    /**
     * 途径
     */
    private List ois = new ArrayList();
    /**
     * 得到路径个数
     * @return int
     */
    public int getOISSize()
    {
        return ois.size();
    }
    /**
     * 增加路径
     * @param ot Ot
     * @param io int
     */
    public void add(Ot ot,int io)
    {
        S s = new S();
        s.in = ot;
        s.io = io;
        ois.add(s);
    }
    public S get(int index)
    {
        return (S)ois.get(index);
    }
    public Ot getOt(int index)
    {
        return get(index).in;
    }
    class S
    {
        public Ot in;
        public int io;
    }
}
