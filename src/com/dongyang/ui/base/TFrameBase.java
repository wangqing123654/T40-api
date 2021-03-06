package com.dongyang.ui.base;

import javax.swing.JFrame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import java.awt.Component;
import java.awt.LayoutManager;
import com.dongyang.control.TControl;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import com.dongyang.ui.event.TWindowListener;
import com.dongyang.ui.base.TFocusTraversalPolicy;
import java.awt.Dimension;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TToolBar;
import com.dongyang.ui.TMenuBar;
import com.dongyang.ui.TSelectBlock;
import com.dongyang.ui.TFrame;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.event.TComponentListener;
import java.awt.event.ComponentEvent;
import java.util.Map;
import javax.swing.ButtonGroup;
import java.util.HashMap;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.util.TSystem;
import com.dongyang.jdo.MaxLoad;
import java.util.Date;

public class TFrameBase extends JFrame implements TComponent, TContainer {
    /**
     * 动作列表
     */
    private ActionMessage actionList;
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
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 工作区面板
     */
    private JPanel workPanel;
    /**
     * 工具条
     */
    private TToolBar toolbar;
    /**
     * 是否居中窗口
     */
    private boolean centerWindow = true;
    /**
     * 焦点处理对象
     */
    private TFocusTraversalPolicy tFocus;
    /**
     * 传入参数
     */
    private Object parameter;
    /**
     * 权限
     */
    private Object popedem;
    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 窗口事件监听对象
     */
    private TWindowListener windowListenerObject;
    /**
     * 组件监听对象
     */
    private TComponentListener componentListenerObject;
    /**
     * 按钮组容器
     */
    private Map buttonGroupMap;
    /**
     * 菜单条
     */
    private TMenuBar menuBar;
    /**
     * 工具条
     */
    private TToolBar toolBar;
    /**
     * 菜单列表
     */
    private String menuMap;
    /**
     * 菜单列表(Map)
     */
    private Map menuMapData = new HashMap();
    /**
     * 菜单编号
     */
    private String menuID;
    /**
     * 打开显示
     */
    private boolean isOpenShow = true;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 语种
     */
    private String language;
    /**
     * 中文标题
     */
    private String zhTitle;
    /**
     * 英文标题
     */
    private String enTitle;
    /**
     * 集成加载器
     */
    private MaxLoad maxLoad;
    /**
     * 加载开关
     */
    private boolean loadFlg;

    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     */
    public TFrameBase() throws HeadlessException {
        super();
        uiInit();
    }

    /**
     * Creates a <code>Frame</code> in the specified
     * <code>GraphicsConfiguration</code> of
     * a screen device and a blank title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param gc the <code>GraphicsConfiguration</code> that is used
     * 		to construct the new <code>Frame</code>;
     * 		if <code>gc</code> is <code>null</code>, the system
     * 		default <code>GraphicsConfiguration</code> is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from
     * 		a screen device.  This exception is always thrown when
     *      GraphicsEnvironment.isHeadless() returns true.
     */
    public TFrameBase(GraphicsConfiguration gc) {
        super(gc);
        uiInit();
    }

    /**
     * Creates a new, initially invisible <code>Frame</code> with the
     * specified title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title for the frame
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     */
    public TFrameBase(String title) throws HeadlessException {
        super(title);
        uiInit();
    }

    /**
     * Creates a <code>JFrame</code> with the specified title and the
     * specified <code>GraphicsConfiguration</code> of a screen device.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title to be displayed in the
     * 		frame's border. A <code>null</code> value is treated as
     * 		an empty string, "".
     * @param gc the <code>GraphicsConfiguration</code> that is used
     * 		to construct the new <code>JFrame</code> with;
     *		if <code>gc</code> is <code>null</code>, the system
     * 		default <code>GraphicsConfiguration</code> is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from
     * 		a screen device.  This exception is always thrown when
     *      GraphicsEnvironment.isHeadless() returns true.
     */
    public TFrameBase(String title, GraphicsConfiguration gc) {
        super(title, gc);
        uiInit();
    }

    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        //初始化动作列表
        actionList = new ActionMessage();
        //设置Tag
        setTag("UI");
        //创建焦点控制类
        tFocus = new TFocusTraversalPolicy();
        //取出焦点
        getContentPane().setFocusable(false);
        //设置焦点控制类
        setFocusTraversalPolicy(tFocus);
        //设置工作面板
        setWorkPanel(new JPanel());
        //设置布局管理器
        getWorkPanel().setLayout(new BorderLayout());
        //加载工作面板
        getContentPane().add(getWorkPanel(), BorderLayout.CENTER);
        //设置点击叉关闭窗口父类什么也不做
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout("null");
        getWorkPanel().setBackground(TUIStyle.getFrameBackColor());
    }

    /**
     *
     * @return Container
     */
    @Deprecated
    public Container getContentPane() {
        return super.getContentPane();
    }

    /**
     * Returns the button's tag.
     * @return the buttons tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the button's tag.
     * @param tag the string used to set the tag
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
        if(width > 1024)
            width = 1024;
        this.setSize(width, getSize().height);
    }

    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height) {
        if(height > 748)
            height = 748;
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
        getWorkPanel().setBackground(StringTool.getColor(color, getConfigParm()));
    }

    /**
     * 设置焦点列表
     * @param tags String
     */
    public void setFocusList(String tags) {
        tFocus.setFocusList(tags);
    }

    /**
     * 得到焦点列表
     * @return String
     */
    public String getFocusList() {
        return tFocus.getFocusList();
    }

    /**
     * 下一个组件得到焦点
     * @param tag String
     */
    public void afterFocus(String tag) {
        String afterTag = tFocus.getTagAfter(this, tag);
        if (afterTag == null)
            return;
        callMessage(afterTag + "|grabFocus");
    }

    /**
     * 前一个组件得到焦点
     * @param tag String
     */
    public void beforeFocus(String tag) {
        String beforeTag = tFocus.getTagBefore(this, tag);
        if (beforeTag == null)
            return;
        callMessage(beforeTag + "|grabFocus");
    }

    /**
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null)
            control.setComponent(this);
    }

    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * 设置工作面板
     * @param workPanel JPanel
     */
    protected void setWorkPanel(JPanel workPanel) {
        this.workPanel = workPanel;
    }

    /**
     * 得到工作面板
     * @return JPanel
     */
    protected JPanel getWorkPanel() {
        return workPanel;
    }

    /**
     * 设置工具条
     * @param toolbar TToolBar
     */
    public void setToolBar(TToolBar toolbar) {
        if (this.toolbar == toolbar)
            return;
        if (this.toolbar != null)
            getContentPane().remove(this.toolbar);
        this.toolbar = toolbar;
        if (toolbar != null)
            getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    /**
     * 得到工具条
     * @return TToolBar
     */
    public TToolBar getToolBar() {
        return toolbar;
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
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
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
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm) {
        if (configParm == null)
            return;
        //保存配置对象
        setConfigParm(configParm);
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
        return "ControlClassName,ControlConfig,Layout,MenuConfig,MenuMap";
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
        //处理基本消息
        Object value = null;
        if ((value = onBaseMessage(message, parm)) != null)
            return value;
        //处理子集的消息
        if ((value = onTagBaseMessage(message, parm)) != null)
            return value;
        //处理控制类的消息
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //处理终止消息
        if ((value = onStopMessage(message, parm)) != null)
            return value;
        //消息上传
        return null;
    }

    /**
     * 结束消息<BR>
     * TWindowListener.WINDOW_CLOSING -> onClose -> dispose
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onStopMessage(String message, Object parm) {
        String value[] = StringTool.getHead(message, "->");
        if (value[0].equalsIgnoreCase(getTag()) &&
            value[1].equalsIgnoreCase(TWindowListener.WINDOW_CLOSING)) {
            callMessage("onExit");
            return "OK";
        }
        if ("onExit".equalsIgnoreCase(value[0])||"onClose".equalsIgnoreCase(value[0])) {
            onClosed();
            return "OK";
        }
        return null;
    }

    /**
     * 处理子集的消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onTagBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        TComponent component = findObject(value[0]);
        if (component == null)
            return null;
        return component.callMessage(value[1], parm);
    }

    /**
     * 查找Tag对象
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag) {
        if (tag == null || tag.length() == 0)
            return null;
        for (int i = 0; i < getWorkPanel().getComponentCount(); i++) {
            Component component = getWorkPanel().getComponent(i);
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
        //查询菜单条
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null && menuBar instanceof TContainer) {
            TContainer container = (TContainer) menuBar;
            TComponent value = container.findObject(tag);
            if (value != null)
                return value;
        }
        //查询工具条
        TToolBar toolbar = getToolBar();
        if(toolbar != null)
        {
            TContainer container = (TContainer) toolbar;
            TComponent value = container.findObject(tag);
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
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
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
        if ("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
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
     * 加载菜单
     * @param value String
     */
    public void setMenuConfig(String value)
    {
        setMenuConfig(value,null);
    }
    /**
     * 加载菜单
     * @param value String
     * @param parentComponent TComponent 父类组件
     */
    public void setMenuConfig(String value,TComponent parentComponent) {
        if(value == null || value.length() == 0)
            return;
        if(parentComponent == null)
            parentComponent = this;
        menuBar = new TMenuBar();
        menuBar.setTag("MENI");
        menuBar.setParentComponent(parentComponent);
        menuBar.callMessage("setLoadTag|UI");
        //设置菜单条
        setJMenuBar(menuBar);
        //是否加载工具条
        if (StringTool.getBoolean(getConfigParm().getConfig().getString(
                getConfigParm().getSystemGroup(), getTag() + ".ToolBar"))) {
            toolBar = new TToolBar();
            toolBar.setParentComponent(this);
            menuBar.setToolBar(toolBar);
            setToolBar(toolBar);
        }
        //加载菜单配置文件
        menuBar.init(getConfigParm().newConfig(value));
    }

    /**
     * 加载布局管理器
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("空")) {
            getWorkPanel().setLayout(null);
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
        getWorkPanel().setLayout(layoutManager);
    }

    /**
     * 创建子组件
     * @param value String
     */
    protected void createItem(String value) {
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
        if (obj == null || !(obj instanceof Component))
            return;
        Component component = (Component) obj;
        if (component instanceof TComponent) {
            TComponent tComponent = (TComponent) component;
            tComponent.setTag(cid);
            tComponent.init(getConfigParm());
            tComponent.setParentComponent(this);
            if (StringTool.getBoolean(getConfigParm().getConfig().getString(
                    getConfigParm().getSystemGroup(), getTag() + ".editUI"))) {
                TSelectBlock tSelectBlock = new TSelectBlock();
                getWorkPanel().add(tSelectBlock);
                tSelectBlock.setTComponent(tComponent);
                tSelectBlock.update(false);
            }
        }

        if (values.length == 1)
            getWorkPanel().add(component);
        else if (values.length == 2)
            getWorkPanel().add(component, StringTool.layoutConstraint(values[1]));
    }
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        if(windowListenerObject == null)
        {
            //监听Window事件
            windowListenerObject = new TWindowListener(this);
            addWindowListener(windowListenerObject);
        }
        if(componentListenerObject == null)
        {
            //监听组件事件
            componentListenerObject = new TComponentListener(this);
            addComponentListener(componentListenerObject);
        }
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_HIDDEN,"onComponentHidden");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_MOVED,"onComponentMoved");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_RESIZED,"onComponentResized");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_SHOWN,"onComponentShown");
    }
    /**
     * 组件隐藏事件
     * @param e ComponentEvent
     */
    public void onComponentHidden(ComponentEvent e)
    {

    }
    /**
     * 组件移动事件
     * @param e ComponentEvent
     */
    public void onComponentMoved(ComponentEvent e)
    {

    }
    /**
     * 组件尺寸改变事件
     * @param e ComponentEvent
     */
    public void onComponentResized(ComponentEvent e)
    {
        int width = getWorkPanel().getWidth();
        int height = getWorkPanel().getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }
    /**
     * 组件显示事件
     * @param e ComponentEvent
     */
    public void onComponentShown(ComponentEvent e)
    {
        int width = getWorkPanel().getWidth();
        int height = getWorkPanel().getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }

    /**
     * 初始化
     */
    public void onInit() {
        //初始化监听事件
        initListeners();
        //Date d1 = new Date();
        //启动加载器
        if(isLoadFlg())
            startMaxLoad();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        //触发内部组件初始化
        for (int i = 0; i < getWorkPanel().getComponentCount(); i++) {
            Component component = getWorkPanel().getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        //初始化菜单
        onInitMenu();
        //停止加载器
        if(isLoadFlg())
            stopMaxLoad();
        //初始化控制类
        if (getControl() != null)
            getControl().onInit();
        //Date d2 = new Date();
        //System.out.println("TFrameBase.onInit.time->" + (d2.getTime() - d1.getTime()) + " " + isLoadFlg());
        //调整语种
        changeLanguage((String)TSystem.getObject("Language"));
    }
    /**
     * 初始化菜单
     */
    public void onInitMenu()
    {
        //初始化菜单条
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null && menuBar instanceof TComponent)
            ((TComponent) menuBar).callMessage("onInit");
        //初始化工具条
        TToolBar toolBar = getToolBar();
        if (toolBar != null)
            toolBar.callMessage("onInit");
    }
    /**
     * 关闭事件
     */
    public void onClosed() {
        Object obj = callFunction("onClosing");
        if(obj instanceof Boolean && !((Boolean)obj))
            return;
        dispose();
    }

    /**
     * 重新加载事件
     */
    public void onReset() {
        setToolBar(null);
        setJMenuBar(null);
        //清除全部组件
        getWorkPanel().removeAll();
        this.language = "";
        //重新加载
        getConfigParm().reset();
        init(getConfigParm());
        onInit();
        int width = getWorkPanel().getWidth();
        int height = getWorkPanel().getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
        validate();
        repaint();
    }

    /**
     * 打开显示
     */
    public void open() {
        onInit();
        if(isOpenShow())
        {
            if (isCenterWindow())
                centerWindow();
            setVisible(true);
        }
    }

    /**
     * 设置居中窗口
     * @param centerWindow boolean
     */
    public void setCenterWindow(boolean centerWindow) {
        this.centerWindow = centerWindow;
    }

    /**
     * 窗口是否自动居中
     * @return boolean
     */
    public boolean isCenterWindow() {
        return centerWindow;
    }

    /**
     * 居中窗口
     */
    public void centerWindow() {
        //得到屏幕宽度，高度
        Dimension dimension = getToolkit().getScreenSize();
        int screenWidth = dimension.width;
        int screenHeight = dimension.height;
        //得窗口宽度，高度
        int thisWidth = getWidth();
        int thisHeight = getHeight();
        //设置屏幕居中
        int y = (screenHeight - thisHeight) / 2 - 20;
        if(y < 0)
            y = 0;
        setLocation((screenWidth - thisWidth) / 2,y);
    }
    /**
     * 最大化显示
     */
    public void showMaxWindow()
    {
        //得到屏幕宽度，高度
        Dimension dimension = getToolkit().getScreenSize();
        int screenWidth = dimension.width;
        int screenHeight = dimension.height;
        setLocation(0,0);
        setSize(screenWidth,screenHeight - 30);
    }

    /**
     * 设置传入参数
     * @param parameter Object
     */
    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    /**
     * 得到传入参数
     * @return Object
     */
    public Object getParameter() {
        if(parameter == null)
            return "void";
        return parameter;
    }
    /**
     * 设置权限
     * @param popedem Object
     */
    public void setPopedem(Object popedem)
    {
        this.popedem = popedem;
    }
    /**
     * 得到权限
     * @return Object
     */
    public Object getPopedem()
    {
        if(popedem == null)
            return "void";
        return popedem;
    }

    /**
     * 设置返回值
     * @param returnValue String
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * 得到返回值
     * @return Object
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * 打开窗口
     * @param parm TConfigParm
     * @return TFrame
     */
    public static TFrame openWindow(TConfigParm parm) {
        return openWindow(parm, null);
    }

    /**
     * 打开窗口
     * @param parm TConfigParm
     * @param parameter Object 传入参数
     * @return TFrame
     */
    public static TFrame openWindow(TConfigParm parm, Object parameter) {
        TFrame frame = new TFrame();
        frame.init(parm);
        if (parameter != null)
            frame.setParameter(parameter);
        frame.open();
        return frame;
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
     * 得到按钮组
     * @param name String 组名
     * @return ButtonGroup
     */
    public ButtonGroup getButtonGroup(String name)
    {
        if(buttonGroupMap == null)
            buttonGroupMap = new HashMap();
        ButtonGroup buttonGroup = (ButtonGroup)buttonGroupMap.get(name);
        if(buttonGroup == null)
        {
            buttonGroup = new ButtonGroup();
            buttonGroupMap.put(name,buttonGroup);
        }
        return buttonGroup;
    }
    /**
     * 设置显示
     * @param visible boolean true 显示 false 不显示
     */
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        if(visible)
        {
            Component com = tFocus.getDefaultComponent(this);
            if(com != null && com instanceof TComponent)
                ((TComponent)com).callFunction("grabFocus");
        }
    }
    /**
     * 设置子菜单
     * @param tag String
     * @param menubar TMenuBar
     */
    public void setChildMenuBar(String tag,TMenuBar menubar)
    {
        if(menubar == null || menubar.getComponentCount() == 0)
        {
            setJMenuBar(this.menuBar);
            validate();
            this.menuBar.repaint();
        }
        else
        {
            setJMenuBar((JMenuBar) menubar);
            validate();
            menubar.repaint();
        }
    }
    /**
     * 删除子菜单
     */
    public void removeChildMenuBar()
    {
        setJMenuBar(menuBar);
        validate();
        menuBar.repaint();
    }
    /**
     * 设置子工具条
     * @param tag String
     * @param toolbar TToolBar
     */
    public void setChildToolBar(String tag,TToolBar toolbar)
    {
        if(toolbar == null || toolbar.getComponentCount() == 0)
        {
            setToolBar(this.toolBar);
            validate();
            this.toolBar.repaint();
        }
        else
        {
            setToolBar(toolbar);
            //事件出发
            exeAction(getChildToolBarAction());
            validate();
            toolbar.repaint();
        }
    }
    /**
     * 删除子工具条
     */
    public void removeChildToolBar()
    {
        setToolBar(toolBar);
        validate();
        toolbar.repaint();
    }
    /**
     * 设置菜单列表
     * @param menuMap String
     */
    public void setMenuMap(String menuMap)
    {
        if(this.menuMap == menuMap)
            return;
        this.menuMap = menuMap;
        menuMapData = new HashMap();
        if(menuMap == null)
            return;
        String s[] = StringTool.parseLine(menuMap,";");
        for(int i = 0;i < s.length;i++)
        {
            String s1[] = StringTool.parseLine(s[i],":");
            if(s1.length < 2)
            {
                err("MenuMap 参数错误 TAG:Filename;TAG:Filename");
                return;
            }
            String tag = s1[0];
            String fileName = s1[1];
            TMenuBar menuBar = loadMenu(fileName);
            menuMapData.put(tag,menuBar);
        }
    }
    /**
     * 加载菜单
     * @param fileName String 文件名
     * @return TMenuBar
     */
    public TMenuBar loadMenu(String fileName)
    {
        if(fileName == null)
            return null;
        TMenuBar menuBar = new TMenuBar();
        menuBar.setTag("MENI");
        menuBar.setParentComponent(this);
        menuBar.callMessage("setLoadTag|UI");
        TToolBar toolBar = new TToolBar();
        toolBar.setParentComponent(this);
        menuBar.setToolBar(toolBar);
        //加载菜单配置文件
        menuBar.init(getConfigParm().newConfig(fileName));
        menuBar.onInit();
        toolBar.onInit();
        return menuBar;
    }
    /**
     * 得到菜单列表
     * @return String
     */
    public String getMenuMap()
    {
        return menuMap;
    }
    /**
     * 得到菜单根据tag
     * @param tag String
     * @return TMenuBar
     */
    public TMenuBar getMenuForTag(String tag)
    {
        return (TMenuBar)menuMapData.get(tag);
    }
    /**
     * 设置菜单编号
     * @param menuID String
     */
    public void setMenuID(String menuID)
    {
        if(menuID == null || menuID.length() == 0)
            return;
        if(this.menuID == menuID)
            return;
        TMenuBar newMenuBar = getMenuForTag(menuID);
        if(newMenuBar == null)
            return;
        this.menuID = menuID;
        menuBar = newMenuBar;
        toolBar = newMenuBar.getToolBar();
        //设置菜单条
        setJMenuBar(menuBar);
        //设置工具条
        setToolBar(toolBar);
    }
    /**
     * 得到菜单编号
     * @return String
     */
    public String getMenuID()
    {
        return menuID;
    }
    /**
     * 设置打开显示
     * @param isOpenShow boolean
     */
    public void setOpenShow(boolean isOpenShow)
    {
        this.isOpenShow = isOpenShow;
    }
    /**
     * 是否打开显示
     * @return boolean
     */
    public boolean isOpenShow()
    {
        return isOpenShow;
    }
    /**
     * 执行动作
     * @param message String 消息
     * @return Object
     */
    public Object exeAction(String message)
    {
        if (message == null || message.length() == 0)
            return null;
        String value[] = StringTool.parseLine(message, ';',"[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            String actionMessage = value[i];
            callMessage(actionMessage);
        }
        return "OK";
    }
    /**
     * 设置单击动作
     * @param action String
     */
    public void setChildToolBarAction(String action)
    {
        actionList.setAction("childToolBarAction",action);
    }
    /**
     * 得到单击动作
     * @return String
     */
    public String getChildToolBarAction()
    {
        return actionList.getAction("childToolBarAction");
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
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        if(language == null || language.length() == 0)
        {
            String l = (String) TSystem.getObject("Language");
            if(l == null || l.length() == 0)
                return "zh";
            return l;
        }
        return language;
    }
    /**
     * 设置中文标题
     * @param zhTitle String
     */
    public void setZhTitle(String zhTitle)
    {
        this.zhTitle = zhTitle;
    }
    /**
     * 得到中文标题
     * @return String
     */
    public String getZhTitle()
    {
        return zhTitle;
    }
    /**
     * 设置英文标题
     * @param enTitle String
     */
    public void setEnTitle(String enTitle)
    {
        this.enTitle = enTitle;
    }
    /**
     * 得到英文标题
     * @return String
     */
    public String getEnTitle()
    {
        return enTitle;
    }
    /**
     * 设置语种
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if (language == null)
            return;
        if (language.equals(this.language))
            return;
        this.language = language;

        if("en".equals(language) && getEnTitle() != null && getEnTitle().length() > 0)
            setTitle(getEnTitle());
        else if(getZhTitle() != null && getZhTitle().length() > 0)
            setTitle(getZhTitle());

        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callFunction("changeLanguage",language);
            }
        }
        //初始化菜单条
        if (menuBar != null)
            menuBar.changeLanguage(language);
        //查询工具条
        TToolBar toolbar = getToolBar();
        if(toolbar != null)
            toolbar.changeLanguage(language);
        if(getControl()!= null)
            getControl().onChangeLanguage(language);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * 启动集成加载器
     */
    public void startMaxLoad()
    {
        setMaxLoad(new MaxLoad());
    }
    /**
     * 停止集成加载器
     */
    public void stopMaxLoad()
    {
        getMaxLoad().run();
        setMaxLoad(null);
    }
    /**
     * 设置集成加载器
     * @param maxLoad MaxLoad
     */
    public void setMaxLoad(MaxLoad maxLoad)
    {
        this.maxLoad = maxLoad;
    }
    /**
     * 得到继承加载器
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        return maxLoad;
    }
    /**
     * 设置加载开关
     * @param loadFlg boolean
     */
    public void setLoadFlg(boolean loadFlg)
    {
        this.loadFlg = loadFlg;
    }
    /**
     * 加载开关是否启动
     * @return boolean
     */
    public boolean isLoadFlg()
    {
        return loadFlg;
    }
    
    /**
     * 返回值
     * @param parm
     * @param parameter
     * @param flg   true
     * @return
     */
    public static Object openWindow(TConfigParm parm, Object parameter,boolean flg) {
        TFrame frame = new TFrame();
        frame.init(parm);
        if (parameter != null)
            frame.setParameter(parameter);
        frame.open();
        return frame.getReturnValue();
    }
}

