package com.dongyang.data.exception;

public class TException extends Exception{
    /**
     * �쳣��Ϣ
     */
    private String message;
    /**
     * ������
     * @param message String
     */
    public TException(String message)
    {
        super(message);
        this.message = message;
    }
}
