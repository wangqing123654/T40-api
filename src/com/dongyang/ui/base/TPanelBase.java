package com.dongyang.ui.base;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfig;
import com.dongyang.config.TConfigParm;
import java.awt.Component;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.control.TControl;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.event.TComponentListener;
import java.awt.event.ComponentEvent;
import javax.swing.ButtonGroup;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import com.dongyang.ui.TPanel;
import com.dongyang.ui.TMenuBar;
import com.dongyang.ui.TToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import com.dongyang.ui.TTabbedPane;
import java.awt.Insets;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.control.TDrawControl;
import java.awt.Cursor;
import java.awt.Point;
import com.dongyang.util.TSystem;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import com.dongyang.jdo.MaxLoad;
import java.util.Date;
import com.dongyang.ui.TRootPanel;

public class TPanelBase extends JPanel implements TComponent, TContainer {
    /**
     * 不能移动
     */
    public static final int NO_MOVE = 0;
    /**
     * 左右横向移动
     */
    public static final int WIDTH_MOVE = 1;
    /**
     * 上下纵向移动
     */
    public static final int HEIGHT_MOVE = 2;
    /**
     * 任意移动
     */
    public static final int MOVE = 3;
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
    private String loadtag ;
    /**
     * 文本
     */
    private String text = "";
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 绘图控制类
     */
    private TDrawControl drawControl;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 透明度
     */
    private float transparence = 1.0f;
    /**
     * 传入参数
     */
    private Object parameter;
    /**
     * 权限
     */
    private Object popedem;
    /**
     * 标题文字
     */
    private String title;
    /**
     * 菜单位置文件名
     */
    private String menuConfig;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 鼠标事件监听对象
     */
    TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    TMouseMotionListener mouseMotionListenerObject;
    /**
     * 自动尺寸
     */
    private int autoSize = 5;
    /**
     * 自动尺寸下留边举例
     */
    private int autoHSize = 0;
    /**
     * 自动X
     */
    private boolean autoX;
    /**
     * 自动Y
     */
    private boolean autoY;
    /**
     * 自动宽度
     */
    private boolean autoWidth;
    /**
     * 自动高度
     */
    private boolean autoHeight;
    /**
     * 自动H
     */
    private boolean autoH;
    /**
     * 自动W
     */
    private boolean autoW;
    /**
     * 焦点处理对象
     */
    private TFocusTraversalPolicy tFocus;
    /**
     * 组件监听对象
     */
    private TComponentListener componentListenerObject;
    /**
     * 按钮组容器
     */
    private Map buttonGroupMap;
    /**
     * 按钮组容器有效 true 有效 false 无效
     */
    private boolean buttonGroupFlg = true;
    /**
     * 工作区面板
     */
    private JPanel workPanel;
    /**
     * 标题面板
     */
    private JPanel titlePanel;
    /**
     * 显示标题
     */
    private boolean showTitle;
    /**
     * 显示菜单
     */
    private boolean showMenu;
    /**
     * 菜单
     */
    private TMenuBar menuBar;
    /**
     * 工具条
     */
    private TToolBar toolBar;
    /**
     * 显示工具条
     */
    private boolean showToolBar;
    /**
     * 是否在窗口覆盖菜单
     */
    private boolean topMenu;
    /**
     * 是否在窗口覆盖工具条
     */
    private boolean topToolBar;
    /**
     * 拦截菜单上传
     */
    private boolean refetchTopMenu;
    /**
     * 拦截工具条上传
     */
    private boolean refetchToolBar;
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
     * 正在加载数据
     */
    private boolean initNow;
    /**
     * 动作列表
     */
    public ActionMessage actionList;
    /**
     * 是否拖动中
     */
    private boolean isMove;
    /**
     * 移动类型
     */
    private int moveType = NO_MOVE;
    /**
     * 鼠标旧X位置
     */
    private int oldX;
    /**
     * 鼠标旧Y位置
     */
    private int oldY;
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
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     *
     * @param layout  the LayoutManager to use
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public TPanelBase(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        uiInit();
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout  the LayoutManager to use
     */
    public TPanelBase(LayoutManager layout) {
        this(layout, true);
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
     * and the specified buffering strategy.
     * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
     * will use a double buffer.
     *
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public TPanelBase(boolean isDoubleBuffered) {
        this(new FlowLayout(), isDoubleBuffered);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public TPanelBase() {
        this(false);
        //System.out.println(this.getUI().getClass().getName());
        //javax.swing.plaf.basic.BasicPanelUI a;
        //System.out.println(this.isOpaque());
        //setOpaque(false);
    }
    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        setLayout("null");
        setBackground(TUIStyle.getPanelBackColor());
        tFocus = new TFocusTraversalPolicy();
        setFocusable(false);
        setFocusTraversalPolicy(tFocus);
        actionList = new ActionMessage();
        //this.setMaximumSize(new Dimension(1024,748));
    }
    public void setOpaque(boolean isOpaque)
    {
        super.setOpaque(isOpaque);
        repaint();
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
     * Returns the button's text.
     * @return the buttons text
     * @see #setText
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the button's text.
     * @param text the string used to set the text
     * @see #getText
     *  description: The button's text.
     */
    public void setText(String text) {
        this.text = text;
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
     * 设置透明度
     * @param transparence float
     */
    public void setTransparence(float transparence) {
        this.transparence = transparence;
    }

    /**
     * 得到透明度
     * @return float
     */
    public float getTransparence() {
        return transparence;
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
        return popedem;
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
     * 设置边框
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
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
     * 设置绘图控制类
     * @param drawControl TDrawControl
     */
    public void setDrawControl(TDrawControl drawControl)
    {
        this.drawControl = drawControl;
        if(drawControl != null)
            drawControl.setParent(this);
    }
    /**
     * 得到绘图控制类
     * @return TDrawControl
     */
    public TDrawControl getDrawControl()
    {
        return drawControl;
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
        if(getBaseEventObject() == null)
            return;
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
        dynamicInit();
    }
    /**
     * 动态加载
     */
    private void dynamicInit()
    {
        initNow = true;
        //加载全部属性
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
        {
            //    if (filterInit(value[index]))
            callMessage(value[index], configParm);
        }
        //加载控制类
        if (getControl() != null)
            getControl().init();
        initNow = false;
    }
    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message) {
        //String value[] = StringTool.getHead(message, "=");
        return true;
    }

    /**
     * 加载顺序
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,DrawControlClassName,ControlConfig,Layout,toolBar,MenuConfig,MenuMap,ShowMenu,Border";
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
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //处理子集的消息
        value = onTagBaseMessage(message, parm);
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
        if(parentTComponent == null)
            parentTComponent = getParentTComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        return null;
    }

    /**
     * 得到父类组件
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * 得到父类组件<BR>
     * (用于递归调用查找父类对象.注:内部使用)
     * @param container Container 父类容器
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
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
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
        //初始化菜单条
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
     * 得到菜单条
     * @return TMenuBar
     */
    public TMenuBar getMenuBar()
    {
        return menuBar;
    }
    /**
     * 得到工具条
     * @return TToolBar
     */
    public TToolBar getToolBar()
    {
        return toolBar;
    }
    public void paint(Graphics g) {
        if(getDrawControl() != null)
            getDrawControl().paintBackground(g);
        if(getTransparence() != 1.0f)
        {
            BufferedImage bufimg = new BufferedImage(
                    getWidth(),
                    getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufimg.createGraphics();
            g2.setColor(g.getColor());
            super.paint(g2);
            Graphics2D gx = (Graphics2D) g;
            gx.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, getTransparence()));
            gx.drawImage(bufimg, 0, 0, null);
        }else
            super.paint(g);
        if(getDrawControl() != null)
            getDrawControl().paintForeground(g);
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
        if ("ToolBar".equalsIgnoreCase(value[0]))
            value[0] = "ShowToolBar";
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
     * 设置显示组件类名
     * @param value String
     */
    public void setDrawControlClassName(String value)
    {
        if(value == null || value.length() == 0)
        {
            setDrawControl(null);
            return;
        }
        Object obj = getConfigParm().loadObject(value);
        if(obj == null)
        {
            setDrawControl(null);
            err("Class loadObject err className=" + value);
            return;
        }
        if(!(obj instanceof TDrawControl)){
            setDrawControl(null);
            err("class loadObject type err className=" + value + "is not TDrawControl");
            return;
        }
        TDrawControl control = (TDrawControl)obj;
        setDrawControl(control);
    }
    /**
     * 加载布局管理器
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("空")) {
            if(getWorkPanel() != null)
                getWorkPanel().setLayout((LayoutManager)null);
            else
                setLayout((LayoutManager)null);
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
        if(getWorkPanel() != null)
            getWorkPanel().setLayout(layoutManager);
        else
            setLayout(layoutManager);
    }
    /**
     * 加载成员组件
     * @param value String
     */
    protected void createItem(final String value)
    {
        //new Thread(){
        //    public void run()
        //    {
                dynamicCreateItem(value);
        //    }
        //}.start();
    }
    /**
     * 加载成员组件
     * @param value String
     */
    protected synchronized void dynamicCreateItem(String value) {
        if(value == null || value.length() == 0)
            return;
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
        }
        if (values.length == 1)
        {
            if (getWorkPanel() != null)
                getWorkPanel().add(component);
            else
                add(component);
        }
        else if (values.length == 2)
        {
            if(getWorkPanel() != null)
                getWorkPanel().add(component, StringTool.layoutConstraint(values[1]));
            else
                add(component, StringTool.layoutConstraint(values[1]));
        }
        //增加尺寸同步监听
        if (component instanceof TComponent)
        {
            ((TComponent)component).callFunction("onParentResize",getWidth(),getHeight() - getWorkPanelY());
            callFunction("addEventListener",
                         getTag() + "->" + TComponentListener.RESIZED,
                         component, "onParentResize");
        }
    }
    /**
     * 增加项目
     * @param com Object
     */
    public void addItem(Object com)
    {
        if(com == null)
            return;
        if(!(com instanceof Component))
            return;
        if(getWorkPanel() != null)
            getWorkPanel().add((Component)com);
        else
            add((Component)com);
    }
    /**
     * 增加项目
     * @param com Object
     * @param index int
     */
    public void addItem(Object com,int index)
    {
        if(com == null)
            return;
        if(!(com instanceof TComponent))
            return;
        if(getWorkPanel() != null)
            getWorkPanel().add((Component)com,index);
        else
            add((Component)com,index);
    }
    /**
     * 释放监听事件
     */
    public void releaseListeners()
    {
        //释放鼠标监听
        removeMouseListener(mouseListenerObject);
        //释放鼠标移动监听
        removeMouseMotionListener(mouseMotionListenerObject);
        //释放组件事件
        removeComponentListener(componentListenerObject);

        getBaseEventObject().release();

        TComponent component = getParentComponent();
        if(component != null)
            component.callFunction("removeEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
    }
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        //监听鼠标事件
        if(mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //监听鼠标移动事件
        if(mouseMotionListenerObject == null)
        {
            mouseMotionListenerObject = new TMouseMotionListener(this);
            addMouseMotionListener(mouseMotionListenerObject);
        }
        if(componentListenerObject == null)
        {
            //监听组件事件
            componentListenerObject = new TComponentListener(this);
            addComponentListener(componentListenerObject);
        }
        //监听组件事件
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_HIDDEN,"onComponentHidden");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_MOVED,"onComponentMoved");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_RESIZED,"onComponentResized");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_SHOWN,"onComponentShown");
        //监听Mouse事件
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,"onMouseReleased");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,"onMouseEntered");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,"onMouseExited");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
        TComponent component = getParentComponent();
        if(component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
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
        int width = getWidth();
        int height = getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }
    /**
     * 组件显示事件
     * @param e ComponentEvent
     */
    public void onComponentShown(ComponentEvent e)
    {
        int width = getWidth();
        int height = getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }
    /**
     * 父容器尺寸同步
     */
    public void onParentResize()
    {
        if(isAutoX() || isAutoY() || isAutoWidth() || isAutoHeight())
        {
            TComponent parentTComponent = getParentTComponent();
            if (parentTComponent == null)
                return;
            int width = 0;
            int height = 0;
            if(parentTComponent instanceof TPanel)
            {
                TPanel panel = (TPanel)parentTComponent;
                width = panel.getWidth();
                height = panel.getHeight();
                if(panel.getWorkPanel() != null)
                {
                    width = (int) panel.getWorkPanel().getBounds().getWidth();
                    height = (int) panel.getWorkPanel().getBounds().getHeight();
                }
            }else
            {
                width = (Integer) parentTComponent.callFunction("getWidth");
                height = (Integer) parentTComponent.callFunction("getHeight");
            }
            onParentResize(width, height);
        }
    }
    /**
     * 父容器尺寸改变
     * @param width int
     * @param height int
     */
    public void onParentResize(int width,int height)
    {
        Container c = getParent();
        if(c == null)
            return;
        Insets insets = c.getInsets();
        if(isAutoW())
        {
            int w = c.getWidth();
            setX((w == 0?height:w) - insets.right - getAutoSize() - getWidth());
        }
        if(isAutoH())
        {
            int h = c.getHeight();
            setY((h == 0?height:h) - insets.bottom - getAutoSize() - getHeight());
        }
        if(!isAutoW() && isAutoX())
            setX(insets.left + getAutoSize());
        if(!isAutoH() && isAutoY())
            setY(insets.top + getAutoSize());
        if(!isAutoW() && isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize());
        }
        if(!isAutoH() && isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize() - getAutoHSize());
        }
    }
    /**
     * 设置移动类型
     * @param moveType int
     */
    public void setMoveType(int moveType)
    {
        this.moveType = moveType;
    }
    /**
     * 得到移动类型
     * @return int
     */
    public int getMoveType()
    {
        return moveType;
    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        if(e.getButton() != 1)
            return;
        if(e.getClickCount() == 2)
            return;
        if(getMoveType() == NO_MOVE)
            return;
        oldX = e.getX();
        oldY = e.getY();
        isMove = true;
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        isMove = false;
    }
    /**
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
    }
    /**
     * 鼠标拖动
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        if(!isMove)
            return;
        int x = 0;
        int y = 0;
        switch(getMoveType())
        {
        case WIDTH_MOVE:
            x += e.getX() - oldX;
            break;
        case HEIGHT_MOVE:
            y += e.getY() - oldY;
            break;
        case MOVE:
            x += e.getX() - oldX;
            y += e.getY() - oldY;
            break;
        }
        Point point = new Point(x,y);
        setLocation(point.x + getX(),point.y + getY());
    }
    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }

    /**
     * 初始化
     */
    public void onInit() {
        //初始化监听事件
        initListeners();
        //启动加载器
        if(isLoadFlg())
            startMaxLoad();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
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
        if (getControl() != null)
            getControl().onInit();
        showTopMenu();
    }
    /**
     * 初始化菜单
     */
    public void onInitMenu()
    {
        //初始化菜单条
        if (menuBar != null)
            menuBar.onInit();
        //初始化工具条
        if (toolBar != null)
            toolBar.onInit();
    }
    /**
     * 显示顶部菜单加载
     */
    public void showTopMenu()
    {
        if(isTopMenu())
            setTopMenu(true);
        if(isTopToolBar())
            setTopToolBar(true);
    }
    /**
     * 设置标题文字
     * @param title String
     */
    public void setTitle(String title)
    {
        this.title = title;
        if(title == null || title.length() == 0)
            return;
        TTabbedPane tabbed = null;
        if(getParentComponent() == null)
        {
            TComponent com = getParentTComponent();
            if(com == null || !(com instanceof TTabbedPane))
                return;
            tabbed = (TTabbedPane)com;
        }
        else if(getParentComponent() instanceof TTabbedPane)
            tabbed = (TTabbedPane)getParentComponent();
        else
            return;
        if(tabbed != null)
            tabbed.setTitleAt(getTag(),title);
    }
    /**
     * 得到标题文字
     * @return String
     */
    public String getTitle()
    {
        return title;
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
     * 设置菜单配置文件名
     * @param menuConfig String
     */
    public void setMenuConfig(String menuConfig)
    {
        this.menuConfig = menuConfig;
        if (menuConfig == null && menuConfig.length() == 0)
        {
            menuBar = null;
            toolBar = null;
            return;
        }
        menuBar = new TMenuBar();
        menuBar.setTag("MENI");
        menuBar.setParentComponent(this);
        menuBar.callMessage("setLoadTag|UI");
        toolBar = new TToolBar();
        toolBar.setParentComponent(this);
        menuBar.setToolBar(toolBar);
        //加载菜单配置文件
        menuBar.init(getConfigParm().newConfig(menuConfig));
        if (getTitlePanel() != null)
        {
            if (menuBar != null && isShowMenu())
                getTitlePanel().add(menuBar, BorderLayout.CENTER);
            if (toolBar != null && isShowToolBar())
                getTitlePanel().add(toolBar, BorderLayout.SOUTH);
            updateUI();
            repaint();
        }
    }
    /**
     * 得到菜单配置文件名
     * @return String
     */
    public String getMenuConfig()
    {
        return menuConfig;
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
     * 设置背景颜色
     * @param color String
     */
    public void setBKColor(String color) {
        if(color == null || color.length() == 0)
            return;
        setBackground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * 设置自动X
     * @param autoX boolean
     */
    public void setAutoX(boolean autoX)
    {
        this.autoX = autoX;
        onParentResize();
    }
    /**
     * 是否是自动X
     * @return boolean
     */
    public boolean isAutoX()
    {
        return autoX;
    }
    /**
     * 设置自动Y
     * @param autoY boolean
     */
    public void setAutoY(boolean autoY)
    {
        this.autoY = autoY;
        onParentResize();
    }
    /**
     * 是否是自动Y
     * @return boolean
     */
    public boolean isAutoY()
    {
        return autoY;
    }
    /**
     * 设置自动宽度
     * @param autoWidth boolean
     */
    public void setAutoWidth(boolean autoWidth)
    {
        this.autoWidth = autoWidth;
        onParentResize();
    }
    /**
     * 是否是自动宽度
     * @return boolean
     */
    public boolean isAutoWidth()
    {
        return autoWidth;
    }
    /**
     * 设置自动高度
     * @param autoHeight boolean
     */
    public void setAutoHeight(boolean autoHeight)
    {
        this.autoHeight = autoHeight;
        onParentResize();
    }
    /**
     * 是否是自动高度
     * @return boolean
     */
    public boolean isAutoHeight()
    {
        return autoHeight;
    }
    /**
     * 设置自动H
     * @param autoH boolean
     */
    public void setAutoH(boolean autoH)
    {
        this.autoH = autoH;
    }
    /**
     * 是否是自动H
     * @return boolean
     */
    public boolean isAutoH()
    {
        return autoH;
    }
    /**
     * 设置自动H
     * @param autoW boolean
     */
    public void setAutoW(boolean autoW)
    {
        this.autoW = autoW;
    }
    /**
     * 是否是自动W
     * @return boolean
     */
    public boolean isAutoW()
    {
        return autoW;
    }
    /**
     * 设置自动尺寸
     * @param autoSize int
     */
    public void setAutoSize(int autoSize)
    {
        this.autoSize = autoSize;
        onParentResize();
    }
    /**
     * 得到自动尺寸
     * @return int
     */
    public int getAutoSize()
    {
        return autoSize;
    }
    /**
     * 设置自动尺寸下留边尺寸
     * @param autoHSize int
     */
    public void setAutoHSize(int autoHSize)
    {
        this.autoHSize = autoHSize;
        onParentResize();
    }
    /**
     * 得到自动尺寸下留边尺寸
     * @return int
     */
    public int getAutoHSize()
    {
        return autoHSize;
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
     * @return Object
     */
    public Object afterFocus(String tag) {
        String afterTag = tFocus.getTagAfter(this, tag);
        if (afterTag == null)
            return null;
        callMessage(afterTag + "|grabFocus");
        return "OK";
    }
    /**
     * 前一个组件得到焦点
     * @param tag String
     * @return Object
     */
    public Object beforeFocus(String tag) {
        String beforeTag = tFocus.getTagBefore(this, tag);
        if (beforeTag == null)
            return null;
        callMessage(beforeTag + "|grabFocus");
        return "OK";
    }
    /**
     * 得到按钮组
     * @param name String 组名
     * @return ButtonGroup
     */
    public ButtonGroup getButtonGroup(String name)
    {
        if(!isButtonGroupFlg())
            return null;
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
     * 设置按钮组容器是否有效
     * @param buttonGroupFlg boolean true 有效 false 无效
     */
    public void setButtonGroupFlg(boolean buttonGroupFlg)
    {
        this.buttonGroupFlg = buttonGroupFlg;
    }
    /**
     * 按钮组容器是否有效
     * @return boolean true 有效 false 无效
     */
    public boolean isButtonGroupFlg()
    {
        return buttonGroupFlg;
    }
    /**
     * 加载成员组件
     * @param tag String 增加成员的标签
     * @param config String 增加成员的配置文件
     */
    public void addItem(String tag,String config)
    {
        addItem(tag,config,null);
    }
    /**
     * 显示成员
     * @param tag String
     * @return boolean
     */
    public boolean showItem(String tag)
    {
        return showItem(tag,null);
    }
    /**
     * 显示成员
     * @param tag String
     * @param parm Object
     * @return boolean
     */
    public boolean showItem(String tag,Object parm)
    {
        TComponent com = findItem(tag);
        if(com == null)
            return false;
        if(parm != null && com instanceof TPanel)
        {
            TPanel panel =(TPanel)com;
            panel.setParameter(parm);
            if(panel.getControl()!= null)
                panel.getControl().onInitReset();
        }
        showItem(com);
        return true;
    }
    /**
     * 加载成员组件
     * @param tag String 增加成员的标签
     * @param config String 增加成员的配置文件
     * @param parm Object 参数
     */
    public void addItem(String tag,String config,Object parm)
    {
        addItem(tag,config,parm,true);
    }
    /**
     * 加载成员组件
     * @param tag String
     * @param config String
     * @param parm Object
     * @param isDynamic boolean
     */
    public void addItem(final String tag,final String config,final Object parm,boolean isDynamic)
    {
        if(isDynamic)
            new Thread(){
                public void run()
                {
                    dynamicAddItem(tag,config,parm);
                }
            }.start();
        else
            dynamicAddItem(tag,config,parm);
    }
    /**
     * 动态成员加载组件
     * @param tag String
     * @param config String
     * @param parm Object
     */
    private synchronized void dynamicAddItem(String tag,String config,Object parm) {
        if(tag == null || tag.length() == 0)
            return;
        if(config == null || config.length() == 0)
            return;
        if(showItem(tag,parm))
            return;

        TConfigParm configParm = getConfigParm().newConfig(config,false);

        if(!configParm.getConfig().isInit())
        {
            err("初始化" + config + "失败!");
            return;
        }
        //组件类型
        String type = configParm.getConfig().getType();
        if(type.equalsIgnoreCase("TFrame"))
            type = "TPanel";
        if(type == null || type.length() == 0)
        {
            err("初始化" + config + "类型[" + type + "]失败!");
            return;
        }
        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof Component))
            return;
        Component component = (Component) obj;
        if (component instanceof TComponent) {
            TComponent tComponent = (TComponent) component;
            tComponent.setTag(tag);
            tComponent.callMessage("setLoadTag|UI");
            //传参
            if(parm != null && tComponent instanceof TPanelBase)
                ((TPanelBase)tComponent).setParameter(parm);
            tComponent.init(configParm);
        }
        if(getWorkPanel() != null)
            getWorkPanel().add(component,0);
        else
            add(component,0);
        if (component instanceof TComponent)
        {
            TComponent tComponent = (TComponent) component;
            tComponent.setParentComponent(this);
            tComponent.callFunction("onInit");
            tComponent.callFunction("onParentResize",getWidth(),getHeight() - getWorkPanelY());
            tComponent.callFunction("changeLanguage",(String)TSystem.getObject("Language"));
        }
        if(component instanceof TPanel)
            ((TPanel)component).showTopMenu();
        updateUI();
        component.setVisible(true);
    }
    /**
     * 删除项目
     * @param tag String
     */
    public void removeItem(String tag)
    {
        if(tag == null || tag.length() == 0)
            return;
        TComponent component = findItem(tag);
        if(component == null)
            return;
        Component com = (Component)component;
        if(com.getParent() != this)
            return;
        if(getWorkPanel() != null)
            getWorkPanel().remove(com);
        else
            remove(com);
        updateUI();
    }
    /**
     * 显示成员
     * @param component TComponent
     */
    public void showItem(TComponent component)
    {
        if(component == null)
            return;
        Component com = (Component)component;
        if(com.getParent() != this)
            return;
        if(getWorkPanel() != null)
            getWorkPanel().remove(com);
        else
            remove(com);
        if(getWorkPanel() != null)
            getWorkPanel().add(com,0);
        else
            add(com,0);
        if(component instanceof TPanel)
            ((TPanel)component).showTopMenu();
        updateUI();
    }
    /**
     * 查找成员
     * @param tag String
     * @return TComponent
     */
    public TComponent findItem(String tag) {
        if (tag == null || tag.length() == 0)
            return null;
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                if (tag.equalsIgnoreCase(tComponent.getTag()))
                    return tComponent;
            }
        }
        return null;
    }
    /**
     * 设置工作区
     * @param workPanel JPanel
     */
    public void setWorkPanel(JPanel workPanel)
    {
        this.workPanel = workPanel;
    }
    /**
     * 得到工作区
     * @return JPanel
     */
    public JPanel getWorkPanel()
    {
        return workPanel;
    }
    /**
     * 设置标题区
     * @param titlePanel JPanel
     */
    public void setTitlePanel(JPanel titlePanel)
    {
        this.titlePanel = titlePanel;
    }
    /**
     * 得到标题区
     * @return JPanel
     */
    public JPanel getTitlePanel()
    {
        return titlePanel;
    }
    /**
     * 设置是否显示工具条
     * @param showToolBar boolean
     */
    public void setShowToolBar(boolean showToolBar)
    {
        if(this.showToolBar == showToolBar)
            return;
        this.showToolBar = showToolBar;
        if(getTitlePanel() == null || toolBar == null)
            return;
        if(showToolBar)
            getTitlePanel().add(toolBar,BorderLayout.SOUTH);
        else
            getTitlePanel().remove(toolBar);
        getTitlePanel().updateUI();
    }
    /**
     * 得到是否显示工具条
     * @return boolean
     */
    public boolean isShowToolBar()
    {
        return showToolBar;
    }
    /**
     * 是否显示菜单
     * @return boolean
     */
    public boolean isShowMenu()
    {
        return showMenu;
    }
    /**
     * 设置显示菜单
     * @param showMenu boolean
     */
    public void setShowMenu(boolean showMenu)
    {
        this.showMenu = showMenu;
    }
    /**
     * 设置显示标题
     * @param showTitle boolean
     */
    public void setShowTitle(boolean showTitle)
    {
        if (this.showTitle == showTitle)
            return;
        this.showTitle = showTitle;
        if(!showTitle)
        {
            if(getWorkPanel() == null)
                return;
            setLayout((LayoutManager)null);
            removeAll();
            int count = getWorkPanel().getComponentCount();
            for(int i = count - 1;i >= 0 ;i --)
                add(getWorkPanel().getComponent(i),0);
            getWorkPanel().removeAll();
            setWorkPanel(null);
            setTitlePanel(null);
            callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{
                      getWidth(),getHeight()},new String[]{"int","int"});
            updateUI();
            repaint();
            return;
        }
        JPanel panel = new JPanel(){
            public void setBounds(int x, int y, int width, int height)
                {
                    super.setBounds(x,y,width,height);
                    callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
                }
            };

        setWorkPanel(panel);
        getWorkPanel().setLayout(null);
        getWorkPanel().setBackground(getBackground());
        int count = getComponentCount();
        for(int i = count - 1;i >= 0 ;i --)
            getWorkPanel().add(getComponent(i),0);
        removeAll();
        setLayout(new BorderLayout());
        setTitlePanel(new JPanel());
        getTitlePanel().setLayout(new BorderLayout());
        if(menuBar != null && isShowMenu())
            getTitlePanel().add(menuBar, BorderLayout.CENTER);
        if(toolBar != null && isShowToolBar())
            getTitlePanel().add(toolBar,BorderLayout.SOUTH);
        JLabel label = new JLabel(getTitle());
        getTitlePanel().add(label,BorderLayout.NORTH);
        add(getTitlePanel(),BorderLayout.NORTH);
        add(getWorkPanel(),BorderLayout.CENTER);
        updateUI();
        repaint();
    }
    /**
     * 设置是否在窗口覆盖菜单
     * @param topMenu boolean
     */
    public void setTopMenu(boolean topMenu)
    {
        this.topMenu = topMenu;
        if(topMenu)
        {
            callFunction("removeChildMenuBar");
            if (menuBar != null)
                callFunction("setChildMenuBar", getTag(), menuBar);
        }else
        {
            if (menuBar != null && getTitlePanel() != null && isShowMenu())
            {
                callFunction("removeChildMenuBar");
                getTitlePanel().add(menuBar, BorderLayout.CENTER);
            }

        }
    }
    /**
     * 是否在窗口覆盖菜单
     * @return boolean
     */
    public boolean isTopMenu()
    {
        return topMenu;
    }
    /**
     * 设置是否在窗口覆盖工具条
     * @param topToolBar boolean
     */
    public void setTopToolBar(boolean topToolBar)
    {
        this.topToolBar = topToolBar;
        if(topToolBar)
        {
            callFunction("removeChildToolBar");
            if (toolBar != null)
                callFunction("setChildToolBar", getTag(), toolBar);
        }else
        {
            if (toolBar != null && getTitlePanel() != null)
            {
                callFunction("removeChildToolBar");
                getTitlePanel().add(toolBar, BorderLayout.SOUTH);
            }
        }
    }
    /**
     * 是否在窗口覆盖工具条
     * @return boolean
     */
    public boolean isTopToolBar()
    {
        return topToolBar;
    }
    /**
     * 是否显示标题
     * @return boolean
     */
    public boolean isShowTitle()
    {
        return showTitle;
    }
    /**
     * 得到工作区的Y位置
     * @return int
     */
    public int getWorkPanelY()
    {
        if(getWorkPanel() == null)
            return 0;
        return (int)getWorkPanel().getBounds().getY();
    }
    public void resize(int width, int height) {
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height - getWorkPanelY()},new String[]{"int","int"});
        super.resize(width,height);
        validate();
    }
    /**
     * 设置拦截菜单上传
     * @param refetchTopMenu boolean
     */
    public void setRefetchTopMenu(boolean refetchTopMenu)
    {
        this.refetchTopMenu = refetchTopMenu;
    }
    /**
     * 是否拦截菜单上传
     * @return boolean
     */
    public boolean isRefetchTopMenu()
    {
        return refetchTopMenu;
    }
    /**
     * 设置拦截工具条上传
     * @param refetchToolBar boolean
     */
    public void setRefetchToolBar(boolean refetchToolBar)
    {
        this.refetchToolBar = refetchToolBar;
    }
    /**
     * 是否拦截工具条上传
     * @return boolean
     */
    public boolean isRefetchToolBar()
    {
        return refetchToolBar;
    }
    /**
     * 设置子菜单(拦截用)
     * @param tag String
     * @param menubar TMenuBar
     * @return Object
     */
    public Object setChildMenuBar(String tag,TMenuBar menubar)
    {
        if(isRefetchTopMenu())
            return "Refetch";
        return null;
    }
    /**
     * 删除子菜单(拦截用)
     * @return Object
     */
    public Object removeChildMenuBar()
    {
        if(isRefetchTopMenu())
            return "Refetch";
        return null;
    }
    /**
     * 设置子工具条
     * @param tag String
     * @param toolbar TToolBar
     * @return Object
     */
    public Object setChildToolBar(String tag,TToolBar toolbar)
    {
        if(isRefetchToolBar())
            return "Refetch";
        return null;
    }
    /**
     * 删除子工具条
     * @return Object
     */
    public Object removeChildToolBar()
    {
        if(isRefetchToolBar())
            return "Refetch";
        return null;
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
        showTopMenu();
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
     * 是否正在加载
     * @return boolean
     */
    public boolean isInitNow()
    {
        return initNow;
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
            if ("call".equalsIgnoreCase(actionMessage))
                actionMessage = getText();
            callMessage(actionMessage);
        }
        return "OK";
    }

    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * 显示窗体
     */
    public void onShowWindow()
    {
        showTopMenu();
        exeAction(getShowWindowAction());
    }
    /**
     * 设置显示窗体消息
     * @param action String
     */
    public void setShowWindowAction(String action) {
        actionList.setAction("showWindowAction",action);
    }

    /**
     * 得到显示窗体消息
     * @return String
     */
    public String getShowWindowAction() {
        return actionList.getAction("showWindowAction");
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
     * /释放元素
     */
    public void releaseItem()
    {
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (!(component instanceof TComponent))
                continue;
            TComponent tComponent = (TComponent) component;
            tComponent.release();
        }
    }
    /**
     * 释放
     */
    public void release()
    {
        //释放监听
        releaseListeners();
        //释放控制类
        if(control != null)
        {
            control.release();
            control = null;
        }
        //释放元素
        releaseItem();
        //释放属性
        RunClass.release(this);
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        super.setName(name);
        if(name == null || name.length() == 0)
            return;
        TTabbedPane tabbed = null;
        if(getParentComponent() == null)
        {
            TComponent com = getParentTComponent();
            if(com == null || !(com instanceof TTabbedPane))
                return;
            tabbed = (TTabbedPane)com;
        }
        else if(getParentComponent() instanceof TTabbedPane)
            tabbed = (TTabbedPane)getParentComponent();
        else
            return;
        if(tabbed != null)
            tabbed.setTitleAt(getTag(),name);
    }
    protected void finalize() throws Throwable {
        super.finalize();
        //System.out.println("finalize TPanelBase");
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
     * 设置语种
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if(language == null)
            return;
        if(language.equals(this.language))
            return;
        this.language = language;
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                //System.out.println("=======language======="+language);
                tComponent.callFunction("changeLanguage",language);
            }
        }
        //初始化菜单条
        if (menuBar != null && menuBar instanceof TContainer)
            menuBar.changeLanguage(language);
        //查询工具条
        TToolBar toolbar = getToolBar();
        if(toolbar != null)
            toolbar.changeLanguage(language);
        Border border = getBorder();
        if(border instanceof TTitledBorder)
        {
            TTitledBorder titleBorder = (TTitledBorder)border;
            titleBorder.setLanguage(language);
            repaint();
        }
        if(getControl()!= null)
            getControl().onChangeLanguage(language);
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
        if(maxLoad != null)
            return maxLoad;
        if(getParentComponent() instanceof TContainer)
            return ((TContainer)getParentComponent()).getMaxLoad();
        return null;
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
     * 打开子窗口
     * @param tag String
     * @param config String
     * @param parm Object
     * @return TRootPanel
     */
    public TRootPanel openSheet(String tag,String config,Object parm)
    {
        TRootPanel p = new TRootPanel();
        p.setTag(tag);
        p.setIsMDISheet(true);
        p.setConfigParm(getConfigParm());
        p.addItem(tag + "_T",config,parm,false);
        TPanel p1 = (TPanel)p.getComponent(0);
        p.setX(p1.getX());
        p.setY(p1.getY());
        p.setWidth(p1.getWidth() + 6);
        p.setHeight(p1.getHeight() + 24);
        p.setTitle(p1.getTitle());
        p1.setAutoX(true);
        p1.setAutoY(true);
        p1.setAutoWidth(true);
        p1.setAutoHeight(true);
        p1.setAutoSize(0);
        p.setIsWindow(false);
        p.setParentComponent(this);
        add(p,0);
        p.onInit();
        updateUI();
        p.setVisible(true);
        return p;
    }
}
