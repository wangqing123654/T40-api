package com.dongyang.tui.control;

import com.dongyang.tui.DComponent;

/**
 *
 * <p>Title: 组件控制类</p>
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
     * 控件对象
     */
    DComponent component;
    /**
     * 设置控件
     * @param component DComponent
     */
    public void setComponent(DComponent component)
    {
        this.component = component;
    }
    /**
     * 得到控件对象
     * @return DComponent
     */
    public DComponent getComponent()
    {
        return component;
    }
    /**
     * 得到控件对象
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
     * 呼叫方法
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
