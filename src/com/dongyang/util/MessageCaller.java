package com.dongyang.util;

import java.awt.Component;

import com.dongyang.ui.TActionCall;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TControlable;

public class MessageCaller {

	/**
	 * ��Ϣ����
	 * 
	 * @param message
	 *            String ��Ϣ����
	 * @param parm
	 *            Object ����
	 * @return Object
	 */
	public static Object callMessage(Object obj, String message, Object parm) {
		if (message == null || message.length() == 0)
			return null;
		String name = obj.getClass().getName();
		String pname = parm.getClass().getName();
		// ���������Ϣ
		Object value = onBaseMessage(obj, message, parm);
		if (value != null){
			return value;
		}
		
		//objΪTControlable,������������Ϣ
		if (obj instanceof TControlable) {
			TControlable component = (TControlable)obj;
			// ������������Ϣ
			if (component.getControl() != null) {
				value = component.getControl().callMessage(message, parm);
				if (value != null){
					System.out.println("Control.callMessage:"+name+":"+message+"["+pname+"]");
					return value;
				}
			}
		}
		
		//objΪTComponent,������Ϣ���´�
		if (obj instanceof TComponent) {
			TComponent component = (TComponent)obj;
			
			//objΪTContainer,����Ϣ�´�
			if(obj instanceof TContainer){
				value = onTagBaseMessage((TContainer)obj, message, parm);
				if (value != null){
					return value;
				}
			}
			
			// ����Ϣ�ϴ����ϲ�TComponent
			TComponent parentTComponent = component.getParentComponent();
			if (parentTComponent != null) {
				value = parentTComponent.callMessage(message, parm);
				if (value != null){
					System.out.println("parent.callMessage:"+name+":"+message);
					return value;
				}
			}
			
			// ��ΪTActionCall,����Ĭ�϶���
			if (component instanceof TActionCall) {
				value = ((TActionCall) component).onDefaultActionMessage(
						message, parm);
				System.out.println("DefaultActionMessage:"+name+":"+message);
			}
		}
		if (value != null)
			return value;
		
		System.out.println("NULL:"+name+":"+message);
		return null;
	}

	/**
	 * ��������Ϣ
	 * 1.ִ�з��� : ��'|'�ָ�,��һ���ִ�Ϊ����,value[1]:����ֵ,Object[]����ֵ
     * 2.�趨���� : ��'='�ָ�,��һ���ִ�Ϊ������,�ڶ����ִ�Ϊ����ֵ,Object[0]Ϊ����ֵ
	 * @param obj
	 * @param message
	 * @param parm
	 * @return
	 */
	public static Object onBaseMessage(Object obj, String message, Object parm) {
		//String name = obj.getClass().getName();
		//System.out.println("onBaseMessage:"+name+":"+message);
		if (message == null)
			return null;
		// ������
		String value[] = StringTool.getHead(message, "|");
		Object result = null;
		if ((result = RunClass.invokeMethodT(obj, value, parm)) != null)
			return result;
		// ��������
		value = StringTool.getHead(message, "=");

		// ����������������
		if (obj instanceof BaseMessageCall)
			((BaseMessageCall) obj).baseFieldNameChange(value);

		if ((result = RunClass.invokeFieldT(obj, value, parm)) != null)
			return result;
		return null;
	}
	
	/**
     * �����Ӽ�����Ϣ (��TAG�������,ִ���������callMessage)
     * @param message String '|'�ָ�,��һ���ִ�ΪTAG,value[1]:����ֵ
     * @param parm Object Object[]����ֵ
     * @return Object
     */
    protected static Object onTagBaseMessage(TContainer cmp, String message, Object parm) {
    	String name = cmp.getClass().getName();
		System.out.println("onTagBaseMessage:"+name+":"+message);
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        TComponent component = cmp.findObject(value[0]);
        if (component == null)
            return null;
        return component.callMessage(value[1], parm);
    }
    
 
}
