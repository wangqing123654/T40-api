package com.dongyang.ui;

import com.dongyang.control.TControl;
import com.dongyang.util.BaseMessageCall;

public interface TControlable extends BaseMessageCall{
    /**
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control);
    
    /**
     * �õ����������
     * @return TControl
     */
    public TControl getControl();
    
    /**
     * �����������
     * @param value String
     */
    public void setControlClassName(String value);
    
    /**
     * �����������
     * @param value String
     */
    public void setControlConfig(String value);
}
