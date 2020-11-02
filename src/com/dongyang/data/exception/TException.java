package com.dongyang.data.exception;

public class TException extends Exception{
    /**
     * 异常信息
     */
    private String message;
    /**
     * 构造器
     * @param message String
     */
    public TException(String message)
    {
        super(message);
        this.message = message;
    }
}
