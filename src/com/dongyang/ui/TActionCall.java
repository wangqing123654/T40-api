package com.dongyang.ui;

public interface TActionCall {
	/**
     * ִ��Ĭ�϶���
     * @param message String ��ʽ : TAG->ACTION
     * @param parm Object 
     * @return Object
     */
	public Object onDefaultActionMessage(String message, Object parm);
}
