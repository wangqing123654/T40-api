package com.dongyang.util;

import java.awt.Component;

import com.dongyang.ui.TActionCall;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TControlable;

public class MessageCaller {

	/**
	 * 消息处理
	 * 
	 * @param message
	 *            String 消息处理
	 * @param parm
	 *            Object 参数
	 * @return Object
	 */
	public static Object callMessage(Object obj, String message, Object parm) {
		if (message == null || message.length() == 0)
			return null;
		String name = obj.getClass().getName();
		String pname = parm.getClass().getName();
		// 处理基本消息
		Object value = onBaseMessage(obj, message, parm);
		if (value != null){
			return value;
		}
		
		//obj为TControlable,则处理控制类的消息
		if (obj instanceof TControlable) {
			TControlable component = (TControlable)obj;
			// 处理控制类的消息
			if (component.getControl() != null) {
				value = component.getControl().callMessage(message, parm);
				if (value != null){
					System.out.println("Control.callMessage:"+name+":"+message+"["+pname+"]");
					return value;
				}
			}
		}
		
		//obj为TComponent,则处理消息上下传
		if (obj instanceof TComponent) {
			TComponent component = (TComponent)obj;
			
			//obj为TContainer,将消息下传
			if(obj instanceof TContainer){
				value = onTagBaseMessage((TContainer)obj, message, parm);
				if (value != null){
					return value;
				}
			}
			
			// 将消息上传至上层TComponent
			TComponent parentTComponent = component.getParentComponent();
			if (parentTComponent != null) {
				value = parentTComponent.callMessage(message, parm);
				if (value != null){
					System.out.println("parent.callMessage:"+name+":"+message);
					return value;
				}
			}
			
			// 若为TActionCall,启动默认动作
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
	 * 基础类消息
	 * 1.执行方法 : 以'|'分隔,第一個字串为方法,value[1]:参数值,Object[]参数值
     * 2.设定属性 : 以'='分隔,第一個字串为属性名,第二個字串为属性值,Object[0]为属性值
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
		// 处理方法
		String value[] = StringTool.getHead(message, "|");
		Object result = null;
		if ((result = RunClass.invokeMethodT(obj, value, parm)) != null)
			return result;
		// 处理属性
		value = StringTool.getHead(message, "=");

		// 重新命名属性名称
		if (obj instanceof BaseMessageCall)
			((BaseMessageCall) obj).baseFieldNameChange(value);

		if ((result = RunClass.invokeFieldT(obj, value, parm)) != null)
			return result;
		return null;
	}
	
	/**
     * 处理子集的消息 (以TAG找子组件,执行子组件的callMessage)
     * @param message String '|'分隔,第一個字串为TAG,value[1]:参数值
     * @param parm Object Object[]参数值
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
