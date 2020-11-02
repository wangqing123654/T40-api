package com.dongyang.ui.base;

import com.dongyang.control.TControl;
import com.dongyang.config.TConfig;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import com.dongyang.ui.TComponent;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.Action;
import javax.swing.DefaultButtonModel;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.event.TButtonEvent;
import com.dongyang.ui.TToolButton;
import java.awt.Font;
import com.dongyang.util.TSystem;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.awt.Cursor;

/**
 *
 * <p>Title: TButton基础类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.15
 * @version 1.0
 */
public class TButtonBase extends JButton implements TComponent {
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
     * 控制类名
     */
    private String controlClassName;
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 动作消息
     */
    private String actionMessage;
    /**
     * 快捷键
     */
    private String key;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 鼠标事件监听对象
     */
    TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    TMouseMotionListener mouseMotionListenerObject;
    /**
     * 动作监听对象
     */
    TActionListener actionListenerObject;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 选中方式
     */
    private boolean selectedMode;
    /**
     * 中文显示
     */
    private String zhText;
    /**
     * 英文显示
     */
    private String enText;
    /**
     * 中文Tip
     */
    private String zhTip;
    /**
     * 英文Tip
     */
    private String enTip;
    /**
     * 字体
     */
    private Font tfont;
    /**
     * 自动图标尺寸
     */
    private boolean isAutoIconSize;
    /**
     * 图片名称
     */
    private String pictureName;
    /**
     * 光标
     */
    private int cursorType;

    /**
     * Creates a button with no set text or icon.
     */
    public TButtonBase() {
        this(null, null);
        this.setDoubleBuffered(false);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon  the Icon image to display on the button
     */
    public TButtonBase(Icon icon) {
        this(null, icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public TButtonBase(String text) {
        this(text, null);
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public TButtonBase(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public TButtonBase(String text, Icon icon) {
        // Create the model
        setModel(new DefaultButtonModel());

        // initialize
        init(text, icon);
        uiInit();
    }

    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        setTFont(TUIStyle.getButtonDefaultFont());
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
     * 设置动作消息
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    /**
     * 得到动作消息
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
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
     * 加全局快捷键
     * @param keyValue int
     * @param controlKey int 1 shift 2 ctrl 3 shift+ctrl
     */
    public void setGlobalKey(int keyValue, int controlKey) {
        registerKeyboardAction(actionListener,
                               KeyStroke.getKeyStroke(keyValue, controlKey, false),
                               2);
        //this.setAccelerator(KeyStroke.getKeyStroke(keyValue, controlKey, false));
    }

    /**
     * 加全局快捷键
     * @param key String "CTRL+F1"
     */
    public void setGlobalKey(String key) {
        this.key = key;
        setGlobalKey(StringTool.getKey(key), StringTool.getControlKey(key));
    }

    /**
     * 得到全局快捷键
     * @return String
     */
    public String getGlobalKey() {
        return key;
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
        return callEvent(eventName, new Object[] {parm},new String[] {"java.lang.Object"});
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
        if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }
        //监听Mouse事件
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,"onMouseReleased");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,"onMouseEntered");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,"onMouseExited");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
        addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
    }
    /**
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        if(getCursorType() != 0)
            setCursor(new Cursor(getCursorType()));
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        if(getCursorType() != 0)
            setCursor(new Cursor(0));
    }
    /**
     * 鼠标拖动
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
    }
    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }
    /**
     * 动作事件
     * @param e ActionEvent
     */
    public void onAction(ActionEvent e)
    {
        if(isSelectedMode())
            setSelected(!isSelected());
        //System.out.println("===getTag()==="+getTag());
        callEvent(TButtonEvent.ACTION,new Object[]{getTag(),this},new String[]{"java.lang.String","com.dongyang.ui.TComponent"});
    }
    /**
     * 初始化
     */
    public void onInit() {
        //初始化监听事件
        initListeners();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        //初始化控制类
        if (getControl() != null)
            getControl().onInit();
    }
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm) {
        if (configParm == null)
            return;
        setConfigParm(configParm);
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
    }
    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message) {
        return true;
    }

    /**
     * 加载顺序
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig";
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
        //启动默认动作
        value = onDefaultActionMessage(message, parm);
        if (value != null)
            return value;
        return null;
    }

    /**
     * 执行默认动作
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object onDefaultActionMessage(String message, Object parm) {
        String value[] = StringTool.getHead(message, "->");
        if (value[0].equalsIgnoreCase(getTag()) &&
            value[1].equalsIgnoreCase(TActionListener.ACTION_PERFORMED)) {
            Object result = sendActionMessage();
            if (result != null)
                return result;
            callMessage("afterFocus|" + getTag());
        }
        return null;
    }

    /**
     * 发送动作消息
     * @return Object
     */
    public Object sendActionMessage() {
        if (getActionMessage() == null || getActionMessage().length() == 0)
            return null;
        String value[] = StringTool.parseLine(getActionMessage(), ';',
                                              "[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            callMessage(value[i]);
        }
        return "OK";
    }

    /**
     * 得到父类
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * 得到父类(递归用)
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
     * 得到同名的方法
     * @param name String
     * @return Method[]
     */
    public Method[] searchMethods(String name) {
        Method[] methods = getClass().getMethods();
        ArrayList list = new ArrayList();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            if (m.getName().equalsIgnoreCase(name))
                list.add(m);
        }
        return (Method[]) list.toArray(new Method[] {});
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
        if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
        else if ("Key".equalsIgnoreCase(value[0]))
            value[0] = "globalkey";
        else if ("Tip".equalsIgnoreCase(value[0]))
            value[0] = "toolTipText";
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
     * 设置组件控制类名
     * @param controlClassName String
     */
    public void setControlClassName(String controlClassName) {
        this.controlClassName = controlClassName;
        if(controlClassName == null || controlClassName.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(controlClassName);
        if (obj == null) {
            err("Class loadObject err className=" + controlClassName);
            return;
        }
        if (!(obj instanceof TControl)) {
            err("Class loadObject type err className=" + controlClassName +
                " is not TControl");
            return;
        }
        TControl control = (TControl) obj;
        control.setConfigParm(getConfigParm());
        setControl(control);
    }
    /**
     * 得到组件控制类名
     * @return String
     */
    public String getControlClassName()
    {
        return controlClassName;
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
     * 设置选中模式
     * @param b boolean
     */
    public void setSelectedMode(boolean b)
    {
        selectedMode = b;
    }
    /**
     * 得到选中模式
     * @return boolean
     */
    public boolean isSelectedMode()
    {
        return selectedMode;
    }
    /**
     * 释放
     */
    public void release()
    {

    }
    /**
     * 设置中文显示
     * @param zhText String
     */
    public void setZhText(String zhText)
    {
        this.zhText = zhText;
    }
    /**
     * 得到中文显示
     * @return String
     */
    public String getZhText()
    {
        return zhText;
    }
    /**
     * 设置英文显示
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * 得到英文显示
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }
    /**
     * 设置中文Tip
     * @param zhTip String
     */
    public void setZhTip(String zhTip)
    {
        this.zhTip = zhTip;
    }
    /**
     * 得到中文Tip
     * @return String
     */
    public String getZhTip()
    {
        return zhTip;
    }
    /**
     * 设置英文Tip
     * @param enTip String
     */
    public void setEnTip(String enTip)
    {
        this.enTip = enTip;
    }
    /**
     * 得到英文Tip
     * @return String
     */
    public String getEnTip()
    {
        return enTip;
    }
    /**
     * 设置语种
     * @param language String
     */
    public void changeLanguage(String language)
    {
        //System.out.println("=========== tbutton Base ========="+language);
        if(language == null)
            return;
        if ("en".equals(language) && getEnText() != null &&
            getEnText().length() > 0)
            setText(getEnText());
        else if (getZhText() != null && getZhText().length() > 0)
            setText(getZhText());

        if("en".equals(language) && getEnTip() != null && getEnTip().length() > 0)
            setToolTipText(getEnTip());
        else if(getZhTip() != null && getZhTip().length() > 0)
            setToolTipText(getZhTip());
    }

    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * 设置图标名称
     * @param name String 图标名称
     */
    public void setIconName(String name) {
        byte[] data = TIOM_AppServer.readFile(name);
        if (data == null)
            return;
        ImageIcon icon = new ImageIcon(data);
        if(isAutoIconSize())
        {
            setWidth(icon.getIconWidth());
            setHeight(icon.getIconHeight());
        }
        setIcon(icon);
    }
    /**
     * 设置自动图标尺寸
     * @param isAutoIconSize boolean
     */
    public void setAutoIconSize(boolean isAutoIconSize)
    {
        this.isAutoIconSize = isAutoIconSize;
        if(!isAutoIconSize)
            return;
        if(getIcon()!= null)
            setSize(getIcon().getIconWidth(),getIcon().getIconHeight());
    }
    /**
     * 是否自动图标尺寸
     * @return boolean
     */
    public boolean isAutoIconSize()
    {
        return isAutoIconSize;
    }
    /**
     * 设置字体
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getFont() {
        Font f = getTFont();
        if(f == null)
            return super.getFont();
        String language = (String)TSystem.getObject("Language");
        if(language == null)
            return f;
        if("zh".equals(language))
        {
            Object obj = TSystem.getObject("ZhFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        if("en".equals(language))
        {
            Object obj = TSystem.getObject("EnFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        return f;
    }
    /**
     * 设置字体
     * @param name String
     */
    public void setFontName(String name)
    {
        Font f = getTFont();
        setTFont(new Font(name,f.getStyle(),f.getSize()));
    }
    /**
     * 得到字体
     * @return String
     */
    public String getFontName()
    {
        return getTFont().getFontName();
    }
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * 得到字体尺寸
     * @return int
     */
    public int getFontSize()
    {
        return getTFont().getSize();
    }
    /**
     * 设置字体类型
     * @param style int
     */
    public void setFontStyle(int style)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),style,f.getSize()));
    }
    /**
     * 得到字体类型
     * @return int
     */
    public int getFontStyle()
    {
        return getTFont().getStyle();
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
     * 设置字体颜色
     * @param color String
     */
    public void setColor(String color)
    {
        if(color == null || color.length() == 0)
            return;
        this.setForeground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * 设置边框
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }
    /**
     * 设置按钮图片
     * @param name 图片名称
     */
    public void setPictureName(String name)
    {
        pictureName = name;
        Icon pic = null;
        if (name != null)
            pic = createImageIcon(name);
        setIcon(pic);
    }
    /**
     * 得到按钮图片
     * @return 图片名称
     */
    public String getPictureName()
    {
        return pictureName;
    }
    /**
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
        if(TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" + filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if(icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try{
            icon = new ImageIcon(getClass().getResource(path));
        }catch(NullPointerException e)
        {
            err("没有找到图标" + path);
        }
        return icon;
    }
    /**
     * 设置光标
     * @param cursorType int
     */
    public void setCursorType(int cursorType)
    {
        this.cursorType = cursorType;
    }
    /**
     * 得到光标
     * @return int
     */
    public int getCursorType()
    {
        return cursorType;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "TButtonBase";
    }
}
