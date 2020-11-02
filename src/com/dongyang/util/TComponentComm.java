package com.dongyang.util;

import com.dongyang.ui.TActionCall;
import com.dongyang.ui.TComponent;

public class TComponentComm implements BaseMessageCall{
	
	TComponent component = null;
	
	public TComponentComm(TComponent component){
		this.component = component;		
	}
	
	/**
     * ���з���
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters){
    	return callMessage(message,parameters);
    }
    
    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }
    
    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @param parm Object ����
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //���������Ϣ
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //������������Ϣ
        if (component.getControl() != null) {
            value = component.getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //��Ϣ�ϴ�
        TComponent parentTComponent = component.getParentComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        //����Ĭ�϶���
        if(component instanceof TActionCall){
        	value = ((TActionCall)component).onDefaultActionMessage(message, parm);
        }
        if (value != null)
            return value;
        return null;
    }    
    
    /**
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object onBaseMessage(String message,Object parm)
    {
        if(message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message,"|");
        Object result = null;
        if((result = RunClass.invokeMethodT(this,value,parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message,"=");
        //����������������
        baseFieldNameChange(value);
        if((result = RunClass.invokeFieldT(this,value,parm)) != null)
            return result;
        return null;
    }
    
    /**
     * ����������������
     * @param value String[]
     */
    public void baseFieldNameChange(String value[])
    {
        if("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
    }
}
