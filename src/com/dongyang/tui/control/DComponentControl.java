package com.dongyang.tui.control;

import com.dongyang.tui.DComponent;

/**
 *
 * <p>Title: ���������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.6
 * @version 1.0
 */
public class DComponentControl
{
    /**
     * �ؼ�����
     */
    DComponent component;
    /**
     * ���ÿؼ�
     * @param component DComponent
     */
    public void setComponent(DComponent component)
    {
        this.component = component;
    }
    /**
     * �õ��ؼ�����
     * @return DComponent
     */
    public DComponent getComponent()
    {
        return component;
    }
    /**
     * �õ��ؼ�����
     * @param tag String
     * @return DComponent
     */
    public DComponent getComponent(String tag)
    {
        if(component == null)
            return null;
        return component.findComponent(tag);
    }
    /**
     * ���з���
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters)
    {
        DComponent component = getComponent();
        if(component == null)
            return null;
        return component.invokePublicMethod(message,parameters);
    }
}
