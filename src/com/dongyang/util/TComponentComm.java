package com.dongyang.util;

import com.dongyang.ui.TActionCall;
import com.dongyang.ui.TComponent;

public class TComponentComm implements BaseMessageCall{
	
	TComponent component = null;
	
	public TComponentComm(TComponent component){
		this.component = component;		
	}
	
	/**
     * 呼叫方法
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters){
    	return callMessage(message,parameters);
    }
    
    /**
     * 消息处理
     * @param message String 消息处理
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }
    
    /**
     * 消息处理
     * @param message String 消息处理
     * @param parm Object 参数
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //处理基本消息
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //处理控制类的消息
        if (component.getControl() != null) {
            value = component.getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //消息上传
        TComponent parentTComponent = component.getParentComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        //启动默认动作
        if(component instanceof TActionCall){
        	value = ((TActionCall)component).onDefaultActionMessage(message, parm);
        }
        if (value != null)
            return value;
        return null;
    }    
    
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object onBaseMessage(String message,Object parm)
    {
        if(message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message,"|");
        Object result = null;
        if((result = RunClass.invokeMethodT(this,value,parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message,"=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if((result = RunClass.invokeFieldT(this,value,parm)) != null)
            return result;
        return null;
    }
    
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    public void baseFieldNameChange(String value[])
    {
        if("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
    }
}
