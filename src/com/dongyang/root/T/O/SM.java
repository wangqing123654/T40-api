package com.dongyang.root.T.O;

import java.util.List;
import java.util.ArrayList;
import com.dongyang.root.T.Ot;

/**
 *
 * <p>Title: ��Ϣ��</p>
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
     * ����
     */
    private String type;
    /**
     * ;��
     */
    private List ois = new ArrayList();
    /**
     * �õ�·������
     * @return int
     */
    public int getOISSize()
    {
        return ois.size();
    }
    /**
     * ����·��
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
