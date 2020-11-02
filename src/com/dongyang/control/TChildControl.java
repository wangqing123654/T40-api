package com.dongyang.control;

import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.TComponent;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.Log;
import com.dongyang.util.StringTool;
import com.dongyang.util.RunClass;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.data.TParm;
import javax.swing.JOptionPane;
import java.awt.Component;
import com.dongyang.ui.TFrame;
import com.dongyang.ui.TDialog;
import java.util.Vector;
import com.dongyang.config.TConfig;

public class TChildControl implements TComponent {
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         DEFAULT_OPTION = -1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_OPTION = 0;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_CANCEL_OPTION = 1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         OK_CANCEL_OPTION = 2;

    //
    // Return values.
    //
    /** Return value from class method if YES is chosen. */
    public static final int         YES_OPTION = 0;
    /** Return value from class method if NO is chosen. */
    public static final int         NO_OPTION = 1;
    /** Return value from class method if CANCEL is chosen. */
    public static final int         CANCEL_OPTION = 2;
    /** Return value form class method if OK is chosen. */
    public static final int         OK_OPTION = 0;
    /**
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 配置对象
     */
    private TConfigParm configParm;
    /**
     * 标签
     */
    private String tag;
    /**
     * 日志系统
     */
    private Log log;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 构造器
     */
    public TChildControl()
    {
        log = new Log();
    }
    /**
     * 构造器
     */
    public void onInit() {
    }
    /**
     * 初始化
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm) {
        out("begin");
        if (parm == null)
            return;
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),
                getTag());
        for (int index = 0; index < value.length; index++)
            callMessage(value[index], parm);
        out("end");
    }

    /**
     * Returns the button's tag.
     * @return the buttons tag
     * @see #setTag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the button's tag.
     * @param tag the string used to set the tag
     * @see #getTag
     *  description: The button's tag.
     */
    public void setTag(String tag) {
        this.tag = tag;
        log.setUserInf("[" + tag + "]");
    }
    /**
     * 设置主Control
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
    }
    /**
     * 得到主Control
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * 得到UI组件
     * @return TComponent
     */
    public TComponent getComponent() {
        return getControl().getComponent();
    }

    /**
     * 设置控制对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }
    /**
     * 得到控制对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm() {
        return configParm;
    }
    /**
     * 得到配置文件对象
     * @return TConfig
     */
    public TConfig getConfig()
    {
        if(getConfigParm() == null)
            return null;
        return getConfigParm().getConfig();
    }
    /**
     * 得到系统组别
     * @return String
     */
    public String getSystemGroup()
    {
        if(getConfigParm() == null)
            return null;
        return getConfigParm().getSystemGroup();
    }
    /**
     * 得到字符型属性配置
     * @param name String 名称
     * @return String 返回值
     */
    public String getConfigString(String name)
    {
        return getConfigString(name,"");
    }
    /**
     * 得到字符型属性配置
     * @param name String 名称
     * @param defaultValue String 默认值
     * @return String 返回值
     */
    public String getConfigString(String name,String defaultValue)
    {
        if(getConfig() == null)
            return "";
        return getConfig().getString(getSystemGroup(),name,defaultValue);
    }
    /**
     * 得到整形型属性配置
     * @param name String 名称
     * @return int 返回值
     */
    public int getConfigInt(String name)
    {
        return getConfigInt(name,0);
    }
    /**
     * 得到整形型属性配置
     * @param name String 名称
     * @param defaultValue int 默认值
     * @return int 返回值
     */
    public int getConfigInt(String name, int defaultValue)
    {
        if(getConfig() == null)
            return 0;
        return getConfig().getInt(getSystemGroup(),name,defaultValue);
    }
    /**
     * 得到长整形型属性配置
     * @param name String 名称
     * @return long 返回值
     */
    public long getConfigLong(String name)
    {
        return getConfigLong(name,0);
    }
    /**
     * 得到长整形型属性配置
     * @param name String 名称
     * @param defaultValue int 默认值
     * @return long 返回值
     */
    public long getConfigLong(String name,int defaultValue)
    {
        return TCM_Transform.getLong(getConfigString(name,"" + defaultValue));
    }
    /**
     * 得到双精度型属性配置
     * @param name String 名称
     * @return double 返回值
     */
    public double getConfigDouble(String name)
    {
        return getConfigDouble(name,0);
    }
    /**
     * 得到双精度型属性配置
     * @param name String 名称
     * @param defaultValue double 默认值
     * @return double 返回值
     */
    public double getConfigDouble(String name,double defaultValue)
    {
        return TCM_Transform.getDouble(getConfigString(name,"" + defaultValue));
    }
    /**
     * 得到字符型属性配置
     * @param name String 名称
     * @return char 返回值
     */
    public char getConfigChar(String name)
    {
        return getConfigChar(name,' ');
    }
    /**
     * 得到字符型属性配置
     * @param name String 名称
     * @param defaultValue char 默认值
     * @return char 返回值
     */
    public char getConfigChar(String name,char defaultValue)
    {
        return TCM_Transform.getChar(getConfigString(name,"" + defaultValue));
    }
    /**
     * 得到布尔型属性配置
     * @param name String 名称
     * @return boolean 返回值
     */
    public boolean getConfigBoolean(String name)
    {
        return getConfigBoolean(name,false);
    }
    /**
     * 得到布尔型属性配置
     * @param name String 名称
     * @param defaultValue boolean 默认值
     * @return boolean 返回值
     */
    public boolean getConfigBoolean(String name,boolean defaultValue)
    {
        return TCM_Transform.getBoolean(getConfigString(name,"" + defaultValue));
    }
    /**
     * 得到Vector型属性配置
     * @param name String 名称
     * @return Vector 返回值
     */
    public Vector getConfigVector(String name)
    {
        return getConfigVector(name,new Vector());
    }
    /**
     * 得到Vector型属性配置
     * @param name String 名称
     * @param defaultValue Vector 默认值
     * @return Vector 返回值
     */
    public Vector getConfigVector(String name,Vector defaultValue)
    {
        return TCM_Transform.getVector(getConfigString(name,"" + defaultValue));
    }
    /**
     * 得到多值属性配置
     * @param name String 名称
     * @return String[] 返回值
     */
    public String[] getConfigStringList(String name)
    {
        return getConfigStringList(name,"");
    }
    /**
     * 得到多值属性配置
     * @param name String 名称
     * @param defaultValue String 默认值
     * @return String[] 返回值
     */
    public String[] getConfigStringList(String name,String defaultValue)
    {
        if(getConfig() == null)
            return new String[]{};
        return getConfig().getStringList(getSystemGroup(),name,defaultValue);
    }

    /**
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }

    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }
    /**
     * 增加监听方法(传给自己类)
     * @param eventName String 事件名称
     * @param methodName String 处理对象
     */
    public void addEventListener(String eventName, String methodName) {
        addEventListener(eventName, this, methodName);
    }

    /**
     * 删除监听方法
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }
    /**
     * 删除监听方法(传给自己类)
     * @param eventName String 事件名称
     * @param methodName String 处理对象
     */
    public void removeEventListener(String eventName, String methodName) {
        removeEventListener(eventName, this, methodName);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},
                         new String[] {"java.lang.Object"});
    }
    /**
    * 呼叫方法
    * @param message String
    * @param parameters Object[]
    * @return Object
    */
   public Object callFunction(String message,Object ... parameters)
   {
       return callMessage(message,parameters);
   }
   /**
     * 消息处理
     * @param message String 消息处理
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * 处理消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        callEvent(message);
        callEvent(message, parm);
        return null;
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        out("begin message=\"" + message + "\"");
        if (message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if("UI".equalsIgnoreCase(value[0]))
            return getComponent().callMessage(value[1], parm);
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message, "=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
    }
    /**
     * 得到组件的文本
     * @param tag String 组件标签
     * @return String
     */
    public String getText(String tag)
    {
        return TCM_Transform.getString(callMessage("UI|" + tag + "|getText"));
    }
    /**
     * 得到组件的注释信息
     * @param tag String
     * @return String
     */
    public String getTip(String tag)
    {
        String tip = TCM_Transform.getString(callMessage("UI|" + tag + "|getToolTipText"));
        if(tip == null && tip.trim().length() == 0)
            tip = "[" + tag + "]";
        return tip;
    }
    /**
     * 设置焦点
     * @param tag String
     */
    public void grabFocus(String tag)
    {
        callMessage("UI|" + tag + "|grabFocus");
    }
    /**
     * 检测录入组件是否全部录入齐全
     * @param s String 组件tag列表用分号间隔 例如 "ID,Name"
     * @return boolean true 全部有效 false 有组件Text为空
     */
    public boolean emptyTextCheck(String s)
    {
        String tags[] = StringTool.parseLine(s,",");
        for(int i = 0;i < tags.length;i++)
        {
            String value = getText(tags[i]);
            if(value == null||value.trim().length() == 0)
            {
                messageBox("请录入" + getTip(tags[i]) + "!");
                grabFocus(tags[i]);
                return false;
            }
        }
        return true;
    }
    /**
     * 得到文本的参数
     * @param s String 组件tag列表用分号间隔 例如 "ID,Name"
     * @return TParm (默认"RETURN"组)
     */
    public TParm getTextParm(String s)
    {
        return getTextParm(s,"RETURN");
    }
    /**
     * 得到文本的参数
     * @param s String 组件tag列表用分号间隔 例如 "ID,Name"
     * @param groupName String 组别
     * @return TParm
     */
    public TParm getTextParm(String s,String groupName)
    {
        TParm parm = new TParm();
        String tags[] = StringTool.parseLine(s,",");
        for(int i = 0;i < tags.length;i++)
        {
            String value = getText(tags[i]);
            parm.setData(groupName,tags[i],value);
        }
        return parm;
    }
    /**
     * 设置传入参数
     * @param value Object
     */
    public void setParameter(Object value)
    {
        if(value instanceof String)
            callMessage("UI|setParameter|" + value);
        else
            callMessage("UI|setParameter",value);
    }
    /**
     * 得到传入参数
     * @return Object
     */
    public Object getParameter()
    {
        return callMessage("UI|getParameter");
    }
    /**
     * 设置返回参数
     * @param value Object
     */
    public void setReturnValue(Object value)
    {
        if(value instanceof String)
            callMessage("UI|setReturnValue|" + value);
        else
            callMessage("UI|setReturnValue", value);
    }
    /**
     * 得到返回参数
     * @return Object
     */
    public Object getReturnValue()
    {
        return callMessage("UI|getReturnValue");
    }
    /**
     * 打开窗口
     * @param config String 配置文件名
     */
    public void openWindow(String config)
    {
        openWindow(config,null);
    }
    /**
     * 打开窗口
     * @param config String 配置文件名
     * @param parameter Object 参数
     */
    public void openWindow(String config, Object parameter)
    {
        TFrame.openWindow(getConfigParm().newConfig(config),parameter);
    }
    /**
     * 打开消息窗口
     * @param config String 配置文件名
     * @return Object 返回值
     */
    public Object openDialog(String config)
    {
        return openDialog(config,null);
    }
    /**
     * 打开消息窗口
     * @param config String 配置文件名
     * @param parameter Object 参数
     * @return Object 返回值
     */
    public Object openDialog(String config, Object parameter)
    {
        return TDialog.openWindow(getConfigParm().newConfig(config),parameter);
    }
    /**
     * 弹出对话框提示消息
     * @param message String
     */
    public void messageBox(String message){
        JOptionPane.showMessageDialog((Component)getComponent(),message);
    }
    /**
     * 提示消息窗口
     * @param title 标题
     * @param message 信息
     * @param optionType 按钮类型
     * @return int
     */
    public int messageBox(String title,String message,int optionType)
    {
        return JOptionPane.showConfirmDialog((Component)getComponent(),title,message,optionType);
    }
    /**
     * 关闭窗口
     */
    public void closeWindow()
    {
        callMessage("UI|onClose");
    }
    /**
     * 设置父类组件
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent) {
        this.parentComponent = parentComponent;
    }

    /**
     * 得到父类组件
     * @return TComponent
     */
    public TComponent getParentComponent() {
        return parentComponent;
    }
    /**
     * 释放
     */
    public void release()
    {

    }
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        log.err(text);
    }
}
