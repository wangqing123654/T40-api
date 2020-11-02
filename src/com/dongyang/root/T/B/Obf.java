package com.dongyang.root.T.B;

import com.dongyang.root.T.Ob;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: Ob附着器</p>
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
     * 受控对象
     */
    Object object;
    /**
     * 设置受控对象
     * @param object Object
     */
    public void setObject(Object object)
    {
        this.object = object;
    }
    /**
     * 得到受控对象
     * @return Object
     */
    public Object getObject()
    {
        return object;
    }
    /**
     * 控制通道
     * @param io int 通道
     * @param m String 信息
     * @param parm Object 参数
     * @return Object 结果
     */
    public Object inM(int io,String m,Object parm)
    {
        if(io == 1)
        {
            //处理方法
            String value[] = StringTool.getHead(m, "|");
            if(object == null)
                return null;
            return RunClass.invokeMethodT(object, value, parm);
        }
        return super.inM(io,m,parm);
    }
    /**
     * 出发
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
