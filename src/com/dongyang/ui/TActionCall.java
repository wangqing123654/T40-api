package com.dongyang.ui;

public interface TActionCall {
	/**
     * 执行默认动作
     * @param message String 格式 : TAG->ACTION
     * @param parm Object 
     * @return Object
     */
	public Object onDefaultActionMessage(String message, Object parm);
}
