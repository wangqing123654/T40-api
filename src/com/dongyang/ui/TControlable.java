package com.dongyang.ui;

import com.dongyang.control.TControl;
import com.dongyang.util.BaseMessageCall;

public interface TControlable extends BaseMessageCall{
    /**
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control);
    
    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl();
    
    /**
     * 设置组件类名
     * @param value String
     */
    public void setControlClassName(String value);
    
    /**
     * 设置组件配置
     * @param value String
     */
    public void setControlConfig(String value);
}
