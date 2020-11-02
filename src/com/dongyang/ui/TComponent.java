package com.dongyang.ui;

import com.dongyang.config.TConfigParm;

public interface TComponent {
    /**
     * 设置Tag
     * @param tag String
     */
    public void setTag(String tag);
    /**
     * 得到Tag
     * @return String
     */
    public String getTag();
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm);
    /**
     * 呼叫方法
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters);
    /**
     * 消息处理
     * @param message String 消息处理
     * @return Object
     */
    public Object callMessage(String message);
    /**
     * 消息处理
     * @param message String 消息处理
     * @param parm Object 参数
     * @return Object
     */
    public Object callMessage(String message,Object parm);
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName,Object[] parms,String[] parmType);
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName,Object parm);
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName);
    /**
     * 释放
     */
    public void release();
    /**
     * 设置父类组件
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent);

    /**
     * 得到父类组件
     * @return TComponent
     */
    public TComponent getParentComponent();
}
