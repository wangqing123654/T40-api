package com.dongyang.control;

import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.TComponent;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import java.awt.Component;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.data.TParm;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TDialog;
import com.dongyang.ui.TFrame;
import com.dongyang.config.TConfig;
import java.util.Vector;
import com.dongyang.util.TMessage;
import java.sql.Timestamp;
import com.dongyang.data.TNull;
import com.dongyang.ui.TWindow;
import com.dongyang.ui.TPanel;
import com.dongyang.util.TSystem;
import com.dongyang.ui.TRootPanel;

/**
 * @author whao 2013~
 * @version 1.0
 */
public class TControl implements TComponent{

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
     * 控制UI
     */
    private TComponent component;
    /**
     * 配置对象
     */
    private TConfigParm configParm;
    /**
     * 标签
     */
    private String tag;
    /**
     * 子控制类容器
     */
    private Map childControls = new HashMap();
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 构造器
     */
    public TControl()
    {
        setTag("Control");
    }
    /**
     * 初始化参数
     */
    public void onInitParameter()
    {
    }
    /**
     * 初始化
     */
    public void onInit()
    {
        Iterator iterator = childControls.keySet().iterator();
        while(iterator.hasNext())
            ((TChildControl)childControls.get(iterator.next())).onInit();
    }
    /**
     * 重新初始化
     */
    public void onInitReset()
    {

    }
    /**
     *  初始化
     */
    public void init()
    {
        init(getConfigParm());
    }
    /**
     * 初始化
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm)
    {
        if(parm == null)
            return;
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),getTag());

        for(int index = 0;index < value.length;index++)
            callMessage(value[index], parm);
    }
    /**
     * Returns the button's tag.
     * @return the buttons tag
     * @see #setTag
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * Sets the button's tag.
     * @param tag the string used to set the tag
     * @see #getTag
     *  description: The button's tag.
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    /**
     * 得到UI标签
     * @return String
     */
    public String getUITag()
    {
        return getComponent().getTag();
    }
    /**
     * 设置UI组件
     * @param component TComponent
     */
    public void setComponent(TComponent component)
    {
        this.component = component;
    }
    /**
     * 得到UI组件
     * @return TComponent
     */
    public TComponent getComponent()
    {
        return component;
    }
    /**
     * 得到UI组件
     * @param tag String
     * @return TComponent
     */
    public TComponent getComponent(String tag)
    {
        return (TComponent)getComponent().callFunction(tag + "|getThis");
    }
    /**
     * 设置控制对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * 得到控制对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
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
    public BaseEvent getBaseEventObject()
    {
      return baseEvent;
    }
    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, Object object, String methodName)
    {
        getBaseEventObject().add(eventName,object,methodName);
    }
    /**
     * 增加监听方法(传给自己类)
     * @param eventName String 事件名称
     * @param methodName String 处理对象
     */
    public void addEventListener(String eventName, String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * 删除监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理对象
     */
    public void removeEventListener(String eventName, Object object, String methodName)
    {
      getBaseEventObject().remove(eventName,object,methodName);
    }
    /**
     * 删除监听方法(传给自己类)
     * @param eventName String 事件名称
     * @param methodName String 处理对象
     */
    public void removeEventListener(String eventName, String methodName)
    {
        removeEventListener(eventName,this,methodName);
    }
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName,Object[] parms,String[] parmType)
    {
        return getBaseEventObject().callEvent(eventName,parms,parmType);
    }
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName)
    {
      return callEvent(eventName,new Object[]{},new String[]{});
    }
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName,Object parm)
    {
      return callEvent(eventName,new Object[]{parm},new String[]{"java.lang.Object"});
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
    public Object callMessage(String message)
    {
        return callMessage(message,null);
    }
    /**
     * 处理消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message,Object parm)
    {
        //System.out.println("=====Tcontrol callMessage(message,parm)======="+message);
        if(message == null ||message.length() == 0)
            return null;
        Object value = null;
        if((value = onBaseMessage(message,parm)) != null){
            //System.out.println("=====onBaseMessage(message,parm)=======");
            return value;
        }
        if((value = callEvent(message)) != null){
            //System.out.println("=====callEvent(message)=======");
            return value;
        }
        if((value = callEvent(message,parm)) != null){
            //System.out.println("=====callEvent(message,parm)=======");
            return value;
        }
        if((value = callChildControlsMessage(message,parm)) != null){
             //System.out.println("=====callChildControlsMessage(message,parm)=======");
            return value;
        }
        return null;
    }
    /**
     * 子Conrol处理
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callChildControlsMessage(String message,Object parm)
    {
        Iterator iterator = childControls.keySet().iterator();
        Object value = null;
        while(iterator.hasNext())
            if((value = ((TChildControl) childControls.get(iterator.next())).callMessage(
                    message, parm)) != null)
                return value;
        return null;
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        if(message.startsWith("UI|"))
        {
            String s = message.substring(3);
            return getComponent().callMessage(s, parm);
        }
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message, "=");
        //System.out.println("TControl================="+value);

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
     * 设置项目
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
    }
    /**
     * 创建子Control
     * @param value String
     */
    private void createItem(String value)
    {
        String values[] = StringTool.parseLine(value,"|");
        if(values.length == 0)
            return;
        //组件ID
        String cid = values[0];
        //组件类型
        String type = getConfigParm().getConfig().getString(getConfigParm().getSystemGroup(),cid + ".type");
        if(type.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(type);
        if(obj == null || !(obj instanceof TChildControl) )
          return;
        TChildControl childControl = (TChildControl)obj;
        childControl.setTag(cid);
        childControl.setControl(this);
        String configValue = getConfigParm().getConfig().getString(getConfigParm().getSystemGroup(),cid + ".ControlConfig");
        if(configValue.length() == 0)
            childControl.setConfigParm(getConfigParm());
        else
            childControl.setConfigParm(getConfigParm().newConfig(configValue));
        childControl.init(childControl.getConfigParm());
        childControls.put(cid,childControl);
    }
    /**
     * 得到组件的文本
     * @param tag String 组件标签
     * @return String 文本
     */
    public String getText(String tag)
    {
        return TCM_Transform.getString(callMessage("UI|" + tag + "|getText"));
    }
    /**
     * 设置组件的文本
     * @param tag String 组件标签
     * @param text String 文本
     */
    public void setText(String tag,String text)
    {
        callFunction("UI|" + tag + "|setText",text);
    }
    /**
     * 设置组件的值
     * @param tag String 组件标签
     * @param value Object 值
     */
    public void setValue(String tag,Object value)
    {
        callFunction("UI|" + tag + "|setValue",value);
    }
    /**
     * 得到组件的值
     * @param tag String
     * @return Object
     */
    public Object getValue(String tag)
    {
        return callFunction("UI|" + tag + "|getValue");
    }
    /**
     * 得到组件的值
     * @param tag String
     * @return String
     */
    public String getValueString(String tag)
    {
        return TCM_Transform.getString(getValue(tag));
    }
    /**
     * 得到组件的值
     * @param tag String
     * @return int
     */
    public int getValueInt(String tag)
    {
        return TCM_Transform.getInt(getValue(tag));
    }
    /**
     * 得到组件的值
     * @param tag String
     * @return double
     */
    public double getValueDouble(String tag)
    {
        return TCM_Transform.getDouble(getValue(tag));
    }
    /**
     * 得到组件的值
     * @param tag String
     * @return boolean
     */
    public boolean getValueBoolean(String tag)
    {
        return TCM_Transform.getBoolean(getValue(tag));
    }
    /**
     * 设置文本读取Parm
     * @param linkNames String "T_ID:ID;T_NAME:NAME"
     * @param parm TParm
     * @param row int
     */
    public void setTextForParm(String linkNames,TParm parm,int row)
    {
        if(parm == null)
            return;
        String names[] = StringTool.parseLine(linkNames,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            if(v.length > 1)
                name = v[1];
            setText(v[0],parm.getValue(name,row));
        }
    }
    /**
     * 设置值读取Parm
     * @param linkNames String "T_ID:ID;T_NAME:NAME"
     * @param parm TParm
     */
    public void setValueForParm(String linkNames,TParm parm)
    {
        setValueForParm(linkNames,parm,-1);
    }
    /**
     * 设置值读取Parm
     * @param linkNames String "T_ID:ID;T_NAME:NAME"
     * @param parm TParm
     * @param row int
     */
    public void setValueForParm(String linkNames,TParm parm,int row)
    {
        if(parm == null)
            return;
        String names[] = StringTool.parseLine(linkNames,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            if(v.length > 1)
                name = v[1];
            if(row < 0)
                setValue(v[0],parm.getData(name));
            else
                setValue(v[0],parm.getData(name,row));
        }
    }
    /**
     * 从页面抓取数据
     * @param tags String "ID:string:T_ID;NAME;SEQ:int"
     * @return TParm
     */
    public TParm getParmForTag(String tags)
    {
        return getParmForTag(tags,false);
    }
    /**
     * 从页面抓取数据
     * @param tags String
     * @param delNull boolean true 参数为空时不加入Tparm false 全部加入Tparm
     * @return TParm
     */
    public TParm getParmForTag(String tags,boolean delNull)
    {
        TParm parm = new TParm();
        String names[] = StringTool.parseLine(tags,";");
        if(names.length == 0)
            return parm;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            String type = "String";
            String tag = name;
            if(v.length > 1)
                type = v[1];
            if(v.length > 2)
                tag = v[2];

            if("string".equalsIgnoreCase(type))
            {
                String value = TCM_Transform.getString(getValue(tag));
                if(value.trim().length() > 0 || !delNull)
                    parm.setData(name, value);
            }
            else if("int".equalsIgnoreCase(type))
            {
                int value = TCM_Transform.getInt(getValue(tag));
                if(value != 0 || !delNull)
                    parm.setData(name, value);
            }
            else if("double".equalsIgnoreCase(type))
            {
                double value = TCM_Transform.getDouble(getValue(tag));
                if(value != 0.0 || !delNull)
                    parm.setData(name, value);
            }
            else if("Timestamp".equalsIgnoreCase(type))
            {
                Timestamp value = (Timestamp)getValue(tag);
                if(value != null)
                    parm.setData(name, value);
                else if(!delNull)
                    parm.setData(name,new TNull(Timestamp.class));
            }
        }
        return parm;
    }
    /**
     * 清空文本
     * @param tags String "ID;NAME"
     */
    public void clearText(String tags)
    {
        String names[] = StringTool.parseLine(tags,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
            setText(names[i],"");
    }
    /**
     * 清空值
     * @param tags String
     */
    public void clearValue(String tags)
    {
        String names[] = StringTool.parseLine(tags,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
            setValue(names[i],"");
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
            String value = "" + getValue(tags[i]);
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
        Object parm = callMessage("UI|getParameter");
        if("void".equals(parm))
            return null;
        return parm;
    }
    /**
     * 得到进参
     * @return TParm
     */
    public TParm getInputParm()
    {
        Object obj = getParameter();
        if(obj == null)
            return null;
        if(!(obj instanceof TParm))
            return null;
        return (TParm)obj;
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
            callMessage("UI|setReturnValue", new Object[]{(Object)value});
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
     * @return TComponent
     */
    public TComponent openWindow(String config)
    {
        return openWindow(config,null);
    }
    /**
     * 打开窗口
     * @param config String
     * @param isTop boolean true 浮动窗口 false 普通窗口
     * @return TComponent
     */
    public TComponent openWindow(String config, boolean isTop)
    {
        return openWindow(config,null,isTop);
    }
    /**
     * 打开窗口
     * @param config String 配置文件名
     * @param parameter Object 参数
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter)
    {
        return openWindow(config,parameter,false);
    }
    /**
     * 打开窗口
     * @param config String
     * @param parameter Object
     * @param isTop boolean true 浮动窗口 false 普通窗口
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter,boolean isTop)
    {
        if(isTop)
        {
            return TWindow.openWindow(getConfigParm().newConfig(config),getComponent(),parameter);
        }
        return TFrame.openWindow(getConfigParm().newConfig(config),parameter);
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
        return openDialog(config,parameter,true);
    }
    /**
     * 打开消息窗口
     * @param config String 配置文件名
     * @param parameter Object 参数
     * @param flg boolean true 动态加载 false 静态加载
     * @return Object
     */
    public Object openDialog(String config, Object parameter,boolean flg)
    {
        return TDialog.openWindow(getConfigParm().newConfig(config,flg),parameter);
    }
    /**
     * 打开打印阅览窗口
     * @param fileName String 加载文件名
     * @param parameter Object 参数
     * @return Object
     */
    public Object openPrintWindow(String fileName,Object parameter)
    {
        return openPrintWindow(fileName,parameter,false);
    }
    /**
     * 打开打印阅览窗口
     * @param fileName String 加载文件名
     * @param parameter Object 参数
     * @param isPrint boolean true 打印不显示 false 阅览
     * @return Object
     */
    public Object openPrintWindow(String fileName,Object parameter,boolean isPrint)
    {
        return openWindow("%ROOT%\\config\\database\\PreviewWord.x",new Object[]{fileName,parameter,isPrint});
    }
    /**
     * 打开打印阅览窗口
     * @param fileName String 加载文件名
     * @param parameter Object 参数
     * @return Object
     */
    public Object openPrintDialog(String fileName,Object parameter)
    {
        return openPrintDialog(fileName,parameter,false);
    }
    /**
     * 打开打印阅览消息窗口
     * @param fileName String 加载文件名
     * @param parameter Object 参数
     * @param isPrint boolean true 打印不显示 false 阅览
     * @return Object
     */
    public Object openPrintDialog(String fileName,Object parameter,boolean isPrint)
    {
        return openDialog("%ROOT%\\config\\database\\PreviewWord.x",new Object[]{fileName,parameter,isPrint});
    }

    /**
     * 弹出对话框提示消息
     * @param message Object
     */
    public void messageBox_(Object message){
        JOptionPane.showMessageDialog((Component)getComponent(),TCM_Transform.getString(message));
    }

    /**
     * 弹出对话框提示消息
     * @param message String
     */
    public void messageBox(String message){
        message = TMessage.get(message);
        String title = "消息";
        if("en".equals(getLanguage()))
            title = "Message";
        JOptionPane.showMessageDialog((Component)getComponent(),message,title,JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * 弹出对话框提示消息
     * @param title String
     * @param message String
     */
    public void messageBox(String title,String message){

        message = TMessage.get(message);
        JOptionPane.showMessageDialog((Component)getComponent(),message,title,JOptionPane.INFORMATION_MESSAGE);
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
        message = TMessage.get(message);
        return JOptionPane.showConfirmDialog((Component)getComponent(),message,title,optionType);
    }

    /**
     * 关闭窗口
     */
    public void closeWindow()
    {
        callMessage("UI|onClose");
    }
    /**
     * 是否关闭窗口
     * @return boolean true 关闭 false 不关闭
     */
    public boolean onClosing()
    {
        return true;
    }
    /**
     * 得到权限
     * @return Object
     */
    public Object getPopedem()
    {
        Object parm = callMessage("UI|getPopedem");
        if("void".equals(parm))
            return null;
        return parm;
    }
    /**
     * 得到权限的Parm
     * @return TParm
     */
    public TParm getPopedemParm()
    {
        Object obj = getPopedem();
        if(obj == null)
            return null;
        if(!(obj instanceof TParm))
            return null;
        return (TParm)obj;
    }
    /**
     * 得到权限状态
     * @param id String 编号
     * @return boolean true 有权限 false 权限
     */
    public boolean getPopedem(String id)
    {
        if(id == null || id.length() == 0)
            return false;
        TParm parm = getPopedemParm();
        if(parm == null)
            return false;
        if(parm.getErrCode() < 0)
            return false;
        int count = parm.getCount();
        for(int i = 0;i < count;i ++)
        {
            String s = parm.getValue("ID",i);
            if(id.equals(s))
                return true;
        }
        return false;
    }
    /**
     * 设置权限
     * @param id String
     * @param popedem boolean
     */
    public void setPopedem(String id,boolean popedem)
    {
        if(id == null || id.length() == 0)
            return;
        TParm parm = getPopedemParm();
        if(parm == null)
        {
            parm = new TParm();
            callFunction("UI|setPopedem",parm);
        }
        int count = parm.getCount();
        for(int i = 0;i < count;i ++)
        {
            String s = parm.getValue("ID",i);
            if(id.equals(s))
            {
                if(popedem)
                    parm.setData("ID",i,id);
                else
                    parm.removeRow(i);
                parm.setCount(parm.getCount("ID"));
                return;
            }
        }
        if(popedem)
        {
            parm.addData("ID", id);
            parm.setCount(parm.getCount("ID"));
        }
    }
    /**
     * 设置Title
     * @param title String
     */
    public void setTitle(String title)
    {
        TComponent com = getComponent();
        if(com == null)
            return;
        if(com instanceof TFrame)
            ((TFrame)com).setTitle(title);
        if(com instanceof TDialog)
            ((TDialog)com).setTitle(title);
        if(com instanceof TPanel)
            ((TPanel)com).setTitle(title);
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
        //释放监听
        baseEvent.release();
        //释放所以属性
        RunClass.release(this);
    }
    /**
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        //System.out.println("==TControl Language=="+(String)TSystem.getObject("Language"));

        String language = (String)callFunction("UI|getLanguage");
        if(language == null || language.length() == 0)
            language = (String)TSystem.getObject("Language");
        return language;
    }
    /**
     * 设置语种
     * @param language String
     */
    public void onChangeLanguage(String language)
    {

    }
    /**
     * 打开Sheet窗口
     * @param panelTag String
     * @param tag String
     * @param config String
     * @return TRootPanel
     */
    public TRootPanel openSheet(String panelTag,String tag,String config)
    {
        return openSheet(panelTag,tag,config,null);
    }
    /**
     * 打开Sheet窗口
     * @param panelTag String
     * @param tag String
     * @param config String
     * @param parm Object
     * @return TRootPanel
     */
    public TRootPanel openSheet(String panelTag,String tag,String config,Object parm)
    {
        TComponent com = getComponent(panelTag);
        if(!(com instanceof TPanel))
            return null;
        TPanel panel = (TPanel)com;
        if(panel == null)
            return null;
        return panel.openSheet(tag,config,parm);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        System.out.println(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        System.out.println(text);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }

    /**
     * 必填项共用检查方法
     * @return
     */
    public boolean chkEmpty(){
    	//System.out.println("-----come in chkEmpty1111---------");
    	Map m=getConfig().getMap();
    	String strRequireds="";
    	//构造必须项的值;
    	for (Object key:m.keySet()) {
    		   //System.out.println("key= "+ key + " and value= " + m.get(key));
    		//必须项
    		if(((String)key).indexOf("REQUIRED")!=-1){
    			if(((String)m.get(key)).equals("Y")){
    				strRequireds+=((String)key).split("\\.")[0]+",";
    			}
    		}

    	}
    	if(strRequireds!=null&&strRequireds.length()>0){
    		strRequireds.substring(0, strRequireds.length()-1);
    	}
    	//System.out.println("------------"+strRequireds);

    	return this.emptyTextCheck(strRequireds);

    	//return false;
    }


}
