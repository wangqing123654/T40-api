package com.dongyang.root.T.B;

import com.dongyang.root.T.Ob;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: Ob������</p>
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
public class Obf extends Ob
{
    /**
     * �ܿض���
     */
    Object object;
    /**
     * �����ܿض���
     * @param object Object
     */
    public void setObject(Object object)
    {
        this.object = object;
    }
    /**
     * �õ��ܿض���
     * @return Object
     */
    public Object getObject()
    {
        return object;
    }
    /**
     * ����ͨ��
     * @param io int ͨ��
     * @param m String ��Ϣ
     * @param parm Object ����
     * @return Object ���
     */
    public Object inM(int io,String m,Object parm)
    {
        if(io == 1)
        {
            //������
            String value[] = StringTool.getHead(m, "|");
            if(object == null)
                return null;
            return RunClass.invokeMethodT(object, value, parm);
        }
        return super.inM(io,m,parm);
    }
    /**
     * ����
     */
    public void trigger()
    {
        int x = (Integer)inM(1,"getX");
        int y = (Integer)inM(1,"getY");
        int i = (int)(Math.random() * 4);
        switch((int)(Math.random() * 4))
        {
        case 0:
            inM(1, "setLocation", new Object[]{x + i, y});
            break;
        case 1:
            inM(1, "setLocation", new Object[]{x - i, y});
            break;
        case 2:
            inM(1, "setLocation", new Object[]{x, y + i});
            break;
        case 3:
            inM(1, "setLocation", new Object[]{x, y - i});
            break;
        }
    }
}
