package com.dongyang.tui;

public interface DMessageIO
{
    /**
     * 处理消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm);
}
