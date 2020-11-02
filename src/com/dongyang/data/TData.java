package com.dongyang.data;

import com.dongyang.config.TConfigParm;
import com.dongyang.data.base.TBaseData;
import com.dongyang.data.exception.TException;
import com.dongyang.ui.TComponent;
import com.dongyang.util.Log;
import com.dongyang.control.TControl;
import com.dongyang.util.StringTool;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.event.TAttributeListener;
import com.dongyang.ui.event.TAttributeEvent;
/**
 * 基础数据类型
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author lzk 2008.06.26 0:02
 * @version JavaHis 1.0
 */
public class TData extends TBaseData
        implements TComponent{
    /**
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 标签
     */
    private String tag;
    /**
     * 加载标签
     */
    private String loadtag;
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 属性监听
     */
    private TAttributeListener attributeListener;
    /**
     * 日志系统
     */
    Log log;
    /**
     * 构造器
     * @throws TException
     */
    public TData() throws TException
    {
        log = new Log();
        setTag("Data");
    }
    /**
     * 得到标签
     * @param tag String
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    /**
     * 设置标签
     * @return String
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * 设置加载标签
     * @param loadtag String
     */
    public void setLoadTag(String loadtag)
    {
        this.loadtag = loadtag;
    }
    /**
     * 得到加载标签
     * @return String
     */
    public String getLoadTag()
    {
        if(loadtag != null)
            return loadtag;
        return getTag();
    }
    /**
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control)
    {
        this.control = control;
        if(control != null)
        {
            control.callMessage("setComponent",this);
        }
    }
    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl()
    {
        return control;
    }
    /**
     * 设置父类组件
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent)
    {
        this.parentComponent = parentComponent;
    }
    /**
     * 得到父类组件
     * @return TComponent
     */
    public TComponent getParentComponent()
    {
        return parentComponent;
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
     * 删除监听方法
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object, String methodName)
    {
      getBaseEventObject().remove(eventName,object,methodName);
    }
    public void addAttributeListener(TAttributeListener attributeListener)
    {
        this.attributeListener = attributeListener;
    }
    /**
     * 属性事件
     * @param event String 时间名称
     * @param row int 行号
     * @param name String 名称
     * @param value Object 值
     * @param oldValue Object 旧值
     */
    public void eventAttributeListener(String event,int row,String name,Object value,Object oldValue)
    {
        if(attributeListener == null)
            return;
        if("SET".equalsIgnoreCase(event))
            attributeListener.attributeSet(new TAttributeEvent(row,name,value,oldValue));
    }
    /**
     * 初始化
     */
    public void onInit()
    {
        out("begin");
        //监听Action事件
        addAttributeListener(new TAttributeListener(this));
        //初始化控制类
        if(getControl() != null)
            getControl().onInit();
        out("end");
    }
    /**
     * initialize
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm)
    {
        if(parm == null)
            return;
        if(parm.getSystemGroup() == null)
            parm.setSystemGroup("");
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),getTag());
        for(int index = 0;index < value.length;index++)
            if(filterInit(value[index]))
                callMessage(value[index], parm);
    }
    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message)
    {
        return true;
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
        out("begin message=\"" + message + "\"");
        if (message == null || message.length() == 0)
            return null;
        //处理基本消息
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //处理控制类的消息
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //消息上传
        TComponent parentTComponent = getParentComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        return null;
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message,Object parm)
    {
        out( "begin message=\"" + message + "\"");
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        if("setTag".equalsIgnoreCase(value[0]))
        {
            setTag(value[1]);
            return "OK";
        }
        if("getTag".equalsIgnoreCase(value[0]))
            return getTag();
        if("setDoubleBuffered".equalsIgnoreCase(value[0]))
        {
            setDoubleBuffered(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if("isDoubleBuffered".equalsIgnoreCase(value[0]))
            return isDoubleBuffered();
        if("setControl".equalsIgnoreCase(value[1]))
        {
            if(parm instanceof TControl)
                setControl((TControl)parm);
            return "OK";
        }
        if("getControl".equalsIgnoreCase(value[1]))
            return getControl();
        if("addEventListener".equalsIgnoreCase(value[0]))
        {
            String value1[] = StringTool.parseLine(value[1],"|");
            if(parm == null || value1.length < 2)
                return "addEventListener 参数错误";
            addEventListener(value1[0],parm,value1[1]);
            return "OK";
        }
        if("onInit".equalsIgnoreCase(value[0]))
        {
            onInit();
            return "OK";
        }
        value = StringTool.getHead(message,"=");
        if("Control".equalsIgnoreCase(value[0]))
        {
            if(parm instanceof TControl)
                setControl((TControl) parm);
            return "OK";
        }
        if("ControlClassName".equalsIgnoreCase(value[0]))
        {
            if(parm instanceof TConfigParm)
            {
                TConfigParm uiParm = (TConfigParm) parm;
                Object obj = ((TConfigParm)parm).loadObject(value[1]);
                if(obj == null)
                {
                    err("Class loadObject err className=" + value[1]);
                    return "ERR";
                }
                if(obj instanceof TControl)
                {
                    TControl control = (TControl)obj;
                    setControl(control);
                    String configValue = uiParm.getConfig().getString(uiParm.getSystemGroup(),getLoadTag() + ".ControlConfig");
                    if(configValue.length() == 0)
                        control.setConfigParm(uiParm);
                    else
                        callMessage("ControlConfig=" + configValue,parm);
                    control.init(control.getConfigParm());
                    return "OK";
                }
            }
        }
        if("ControlConfig".equalsIgnoreCase(value[0]))
        {
            if(getControl() != null && parm instanceof TConfigParm)
            {
                TConfigParm uiParm = (TConfigParm) parm;
                getControl().setConfigParm(uiParm.newConfig(value[1]));
            }
        }
        if("doubleBuffered".equalsIgnoreCase(value[0]))
        {
            setDoubleBuffered(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if("item".equalsIgnoreCase(value[0]))
        {
            if(parm instanceof TConfigParm)
            {
                String s[] = StringTool.parseLine(value[1],';',"[]{}()");
                for(int i = 0;i < s.length;i++)
                    createItem(s[i],(TConfigParm)parm);
                return "OK";
            }
        }
        return null;
    }
    /**
     * 创建子组件
     * @param value String
     * @param parm TConfigParm
     */
    private void createItem(String value,TConfigParm parm)
    {
        out("begin value=\"" + value + "\"");
        String values[] = StringTool.parseLine(value,",");
        if(values.length < 4)
            return;
        //属性名
        String name = values[0];
        if(name.length() == 0)
            return;
        //类型
        String type = values[1];
        if(type.length() == 0)
            return;
        //是否是多值
        boolean multi = StringTool.getBoolean(values[2]);
        //尺寸
        int size = StringTool.getInt(values[3]);
        try{
            addAttribute(name, type);
            setAttributeSize(name, size);
            setAttributeMulti(name, multi);
        }catch(TException e)
        {
            err(e.getMessage());
        }
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

    class Data extends TData
    {
        public Data()throws TException
        {
            TConfigParm parm = new TConfigParm();
            parm.setSystemGroup("TEDA");
            parm.setConfig("Data.x");
            parm.setConfigColor("D:\\Project\\client\\Color.ini");
            parm.setConfigClass("D:\\Project\\client\\class.ini");
            init(parm);
            onInit();
        }
        public void setAAA(String s)throws TException
        {
            setAttributeValue("AAA",s);
        }
        public String getAAA()throws TException
        {
            return (String)getAttributeValue("AAA");
        }
    }
    /**
     * 释放
     */
    public void release()
    {

    }
    public static void main(String args[])
    {
        TConfigParm parm = new TConfigParm();
        parm.setSystemGroup("TEDA");
        parm.setConfig("Data.x");
        parm.setConfigColor("D:\\Project\\client\\Color.ini");
        parm.setConfigClass("D:\\Project\\client\\class.ini");
        try{
            TData data = new TData();
            data.init(parm);
            data.onInit();
            data.setAttributeValue("AAA","12");
            data.setAttributeValue("AAA","20");
            data.setAttributeValue("AAA","12");
            System.out.println(data.getAttributeValue("AAA"));
            System.out.println(data.getAttributeModified("AAA"));

            data.addAttributeValue("BBB",1);
            data.addAttributeValue("BBB",200);
            System.out.println(data.getAttributeCount("BBB"));
            System.out.println(data.getAttributeValue(1,"BBB"));

            System.out.println("map=" + data.getDataMap());
            /*data.setAttributeValue("AAA","teterter");
            data.addAttributeValue("BBB",1);
            data.addAttributeValue("BBB",2);
            data.setAttributeValue(4,"BBB",4);
            data.setAttributeValue(4,"BBB",5);
            data.setAttributeValue(4,"BBB",4);
            data.setAttributeValue(1,"BBB",200);
            System.out.println("map=" + data.getDataMap());
            data.addAttributeValue(100,"BBB");
            System.out.println("map=" + data.getDataMap());*/

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
