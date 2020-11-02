package com.dongyang.system;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.dongyang.config.TConfigParm;
import com.dongyang.control.TControl;
import com.dongyang.data.TSocket;
import com.dongyang.jdo.MaxLoad;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.BaseMessageCall;
import com.dongyang.util.Log;
import com.dongyang.util.MessageCaller;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;
import com.dongyang.util.TSystem;

public class AppMain extends JFrame implements TComponent, TContainer{
	
//    protected static final String host = "127.0.0.1";
//    protected static final int port = 8080;
	protected static final String host = "127.0.0.1";
    protected static final int port = 8080;
    protected static String path = "web";
	
	/**
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 标签
     */
    private String tag = "";
    /**
     * 加载标签
     */
    private String loadtag;
    /**
     * 文本
     */
    private String text = "";
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 构造器
     */
    public AppMain() {    	
    	init();
    	this.setSize(1020, 768);
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
    }

    /**
     * 设置加载标签
     * @param loadtag String
     */
    public void setLoadTag(String loadtag) {
        this.loadtag = loadtag;
    }

    /**
     * 得到加载标签
     * @return String
     */
    public String getLoadTag() {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }

    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 得到文本
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * 设置X坐标
     * @param x int
     */
    public void setX(int x) {
        this.setLocation(x, getLocation().y);
    }

    /**
     * 设置Y坐标
     * @param y int
     */
    public void setY(int y) {
        this.setLocation(getLocation().x, y);
    }

    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width) {
        this.setSize(width, getSize().height);
    }

    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height) {
        this.setSize(getSize().width, height);
    }
    /**
     * X坐标偏移
     * @param d int
     */
    public void setX$(int d)
    {
        if(d == 0)
            return;
        setX(getX() + d);
    }
    /**
     * Y坐标偏移
     * @param d int
     */
    public void setY$(int d)
    {
        if(d == 0)
            return;
        setY(getY() + d);
    }
    /**
     * 向左生长
     * @param d int
     */
    public void setX_$(int d)
    {
        setX$(d);
        setWidth$(-d);
    }
    /**
     * 向上升上
     * @param d int
     */
    public void setY_$(int d)
    {
        setY$(d);
        setHeight$(-d);
    }
    /**
     * 宽度坐标偏移
     * @param d int
     */
    public void setWidth$(int d)
    {
        if(d == 0)
            return;
        setWidth(getWidth() + d);
    }
    /**
     * 高度坐标偏移
     * @param d int
     */
    public void setHeight$(int d)
    {
        if(d == 0)
            return;
        setHeight(getHeight() + d);
    }
    /**
     * 设置背景颜色
     * @param color String
     */
    public void setBackground(String color) {
        setBackground(StringTool.getColor(color, getConfigParm()));
    }

    /**
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null) {
            control.setComponent(this);
        }
    }

    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * 设置配置参数对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }

    /**
     * 得到配置参数对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm() {
        return configParm;
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
     * 初始化
     */
    public void onInit() {
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        for (int i = 0; i < getContentPane().getComponentCount(); i++) {
            Component component = getContentPane().getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        if (getControl() != null)
            getControl().onInit();
    }

    /**
     *
     * @param ldapuser
     */
    public void onInit(String ldapuser,String ldappw) {

    	TSystem.setObject("LDAPUSER", ldapuser);
    	TSystem.setObject("LDAPPW", ldappw);

    	onInit();
    }


    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm) {
        if (configParm == null)
            return;
        //保存配置对象
        setConfigParm(configParm);
        //清除全部组件
        getContentPane().removeAll();
        //加载全部属性
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
        //加载控制类
        if (getControl() != null)
            getControl().init();
    }

    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    protected boolean filterInit(String message) {
        String value[] = StringTool.getHead(message, "=");
        if ("toolBar".equalsIgnoreCase(value[0]))
            return false;
        return true;
    }

    /**
     * 加载顺序
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig,Layout";
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
        return MessageCaller.callMessage(this, message, parm);
    }

    /**
     * 得到父组件
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * 得到父组件
     * @param container Container
     * @return TComponent
     */
    public TComponent getParentTComponent(Container container) {
        if (container == null)
            return null;
        if (container instanceof TComponent)
            return (TComponent) container;
        return getParentTComponent(container.getParent());
    }

    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object onBaseMessage(String message, Object parm) {
    	return MessageCaller.onBaseMessage(this, message, parm);
    }

    /**
     * 重新命名属性名称
     * @param value String[]
     */
    public void baseFieldNameChange(String value[]) {
        if ("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
    }

    /**
     * 设置组件类名
     * @param value String
     */
    public void setControlClassName(String value) {
        if(value == null || value.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(value);
        if (obj == null) {
            err("Class loadObject err className=" + value);
            return;
        }
        if (!(obj instanceof TControl)) {
            err("Class loadObject type err className=" + value +
                " is not TControl");
            return;
        }
        TControl control = (TControl) obj;
        control.setConfigParm(getConfigParm());
        setControl(control);
    }

    /**
     * 设置组件配置
     * @param value String
     */
    public void setControlConfig(String value) {
        if (getControl() == null) {
            err("control is null");
            return;
        }
        getControl().setConfigParm(getConfigParm().newConfig(value));
    }

    /**
     * 设置项目
     * @param message String
     * @return Object
     */
    public Object setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
        return "OK";
    }

    /**
     * 加载布局管理器
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("空")) {
            getContentPane().setLayout(null);
            return;
        }
        String type = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), value + ".type");
        if (type.length() == 0)
            type = value;
        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof LayoutManager))
            return;
        LayoutManager layoutManager = (LayoutManager) obj;
        getContentPane().setLayout(layoutManager);

    }

    /**
     * 加载成员组件
     * @param value String
     */
    protected void createItem(String value) {
        //System.out.println("=========TAppletBase value==============="+value);
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //组件ID
        String cid = values[0];
        //组件类型
        String type = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), cid + ".type");
        if (type.length() == 0)
            return;

        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof Component)) {
            err("loadObject " + type + " 失败 obj=" + obj);
            return;
        }

        Component component = (Component) obj;
        if (component instanceof TComponent) {
            TComponent tComponent = (TComponent) component;
            tComponent.setTag(cid);
            String configValue = getConfigParm().getConfig().getString(
                    getConfigParm().getSystemGroup(), cid + ".Config");
            if (configValue.length() == 0)
                tComponent.init(getConfigParm());
            else {
                tComponent.callMessage("setLoadTag|UI");
                tComponent.init(getConfigParm().newConfig(configValue));
            }
            tComponent.callFunction("setParentComponent",this);
        }
        if (values.length == 1)
            getContentPane().add(component);
        else if (values.length == 2)
            getContentPane().add(component,
                                 StringTool.layoutConstraint(values[1]));
    }

    /**
     * 查找Tag对象
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag) {
        if (tag == null || tag.length() == 0)
            return null;
        for (int i = 0; i < getContentPane().getComponentCount(); i++) {
            Component component = getContentPane().getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                if (tag.equalsIgnoreCase(tComponent.getTag()))
                    return tComponent;
                if (tComponent instanceof TContainer) {
                    TContainer container = (TContainer) tComponent;
                    TComponent value = container.findObject(tag);
                    if (value != null)
                        return value;
                }
            }
        }
        return null;
    }

    /**
     * 刷新
     * @param g Graphics 图形设备
     */
    public void paint(Graphics g) {
        super.paint(g);
        //callEvent("paint",new Object[]{g},new String[]{"java.awt.Graphics"});
    }

    /**
     * Applet开始
     */
    public void start() {
        callEvent("start");
    }

    /**
     * Applet停止
     */
    public void stop() {
        callEvent("stop");
    }

    /**
     * Applet结束
     */
    public void destroy1() {
        this.callEvent("destroy");
    }
    /**
     * 得到自己对象
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * 查找组件
     * @param tag String 标签
     * @return TComponent
     */
    public TComponent findTComponent(String tag)
    {
        return (TComponent)callMessage(tag + "|getThis");
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
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * 得到继承加载器
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        return null;
    }

    //=================================================================================
    
    
    /**
     * 初始化Socket
     */
    public void initSocket()
    {
        if(path.toUpperCase().endsWith("/COMMON/LIB/"))
            path = path.substring(0,path.length() - 12);
        if(path.startsWith("/"))
            path = path.substring(1,path.length());
        System.out.println("连接主机:" + host + ":" + port + "/" + path);
        TIOM_AppServer.SOCKET = new TSocket(host,port,path);
    }
    /**
     * 初始化配置对象
     * @return TConfigParm
     */
    public TConfigParm initConfigParm()
    {
        //设置标签
        setTag("UI");
        //建立基本配置参数对象
        TConfigParm configParm = new TConfigParm();
        //装载Socket
        configParm.setSocket(TIOM_AppServer.SOCKET);
        //初始化系统组别
        configParm.setSystemGroup("");
        //装载主配置文件
        configParm.setConfig("%ROOT%\\config\\system\\TMain.x");
        //装载颜色配置文件
        configParm.setConfigColor("%ROOT%\\config\\system\\TColor.x");
        //装载Class配置文件
        configParm.setConfigClass("%ROOT%\\config\\system\\TClass.x");
        //设置系统组别
        configParm.setSystemGroup(configParm.getConfig().getString("",getTag() + ".SystemGroup"));
        //日志追加标记
        boolean appendLog = StringTool.getBoolean(configParm.getConfig().getString("",getTag() + ".AppendLog"));
        //输出日志目录
        String outLogPath = configParm.getConfig().getString("",getTag() + ".OutLogPath");
        //错误日志日志目录
        String errLogPath = configParm.getConfig().getString("",getTag() + ".ErrLogPath");
        //初始化日志输出目录
        //Log.initLogPath(outLogPath,errLogPath,appendLog);
        //初始化系统界面风格
        try{
            UIManager.setLookAndFeel(configParm.getConfig().getString(configParm.getSystemGroup(),getTag() + ".LookAndFeel",
                                     UIManager.getSystemLookAndFeelClassName()));
        }catch(Exception e)
        {
            err(e.getMessage());
        }
        return configParm;
    }
    /**
     * Applet启动初始化
     */
    public void init()
    {
    	System.out.println("Start...................");
        //初始化Socket
        initSocket();
        
        System.out.println("initSocket OK...................");
        
        //加载界面
        init(initConfigParm());
        
        System.out.println("initConfigParm OK...................");

        //System.out.println("ID=" + TIOM_AppServer.getSessionAttribute("ID"));
        //System.out.println(StringTool.getString(TIOM_AppServer.getSessionAttributeNames()));

        //启动系统初始化
    	String ldapuser = "";
    	String ldappw = "";
    	if( null!=ldapuser && !"".equals(ldapuser) && null!=ldappw && !"".equals(ldappw) ){
    		onInit(ldapuser,ldappw);
    	}else{
    		onInit();
    	}

    }
    /**
     * Applet结束
     */
    public void destroy() {
    	this.callEvent("destroy");
        Log.closeLogFile();
    }
    
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}
    
    public static void main(String[] avgs){
    	AppMain main = new AppMain();    	
    	main.setVisible(true);
    }
    
}
