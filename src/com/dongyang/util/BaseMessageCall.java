package com.dongyang.util;

public interface BaseMessageCall {
	public Object callMessage(String message);
	public Object callMessage(String message,Object parm);
	//public Object onBaseMessage(String message, Object parm);
	public void baseFieldNameChange(String value[]);
}
