package com.dongyang.ui.base;

import javax.swing.JScrollPane;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.control.TControl;
import java.awt.Component;
import com.dongyang.config.TConfigParm;
import java.awt.Container;
import com.dongyang.util.StringTool;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import java.awt.LayoutManager;
import com.dongyang.ui.event.TComponentListener;
import java.awt.Insets;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.jdo.MaxLoad;

public class TScrollPaneBase extends JScrollPane implements TComponent,
        TContainer {
    /**
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 文本
     */
    private String text = "";
    /**
     * 标签
     */
    private String tag = "";
    /**
     * 加载标签
     */
    private String loadtag;
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 配置对象
     */
    private TConfigParm configParm;
    /**
     * 自动尺寸
     */
    private int autoSize = 5;
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
     * 自动W尺寸
     */
    private int autoWidthSize;
    /**
     * 自动H尺寸
     */
    private int autoHeightSize;
    /**
     * 动作列表
     */
    public ActionMessage actionList;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * Creates a <code>TScrollPane</code> that displays the view
     * component in a viewport
     * whose view position can be controlled with a pair of scrollbars.
     * The scrollbar policies specify when the scrollbars are displayed,
     * For example, if <code>vsbPolicy</code> is
     * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code>
     * then the vertical scrollbar only appears if the view doesn't fit
     * vertically. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * @see #setViewportView
     *
     * @param view the component to display in the scrollpanes viewport
     * @param vsbPolicy an integer that specifies the vertical
     *		scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal
     *		scrollbar policy
     */
    public TScrollPaneBase(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        uiInit();
    }


    /**
     * Creates a <code>TScrollPane</code> that displays the
     * contents of the specified
     * component, where both horizontal and vertical scrollbars appear
     * whenever the component's contents are larger than the view.
     *
     * @see #setViewportView
     * @param view the component to display in the scrollpane's viewport
     */
    public TScrollPaneBase(Component view) {
        super(view);
        uiInit();
    }


    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * with specified
     * scrollbar policies. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * @see #setViewportView
     *
     * @param vsbPolicy an integer that specifies the vertical
     *		scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal
     *		scrollbar policy
     */
    public TScrollPaneBase(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        uiInit();
    }


    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * where both horizontal and vertical scrollbars appear when needed.
     */
    public TScrollPaneBase() {
        super();
        uiInit();
    }

    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        //初始化动作列表
        actionList = new ActionMessage();
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
     * 释放监听事件
     */
    public void releaseListeners()
    {
        getBaseEventObject().release();

        TComponent component = getParentTComponent();
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
        TComponent component = getParentTComponent();
        if(component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
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
            setX((w == 0?height:w) - insets.right - getAutoSize() - getWidth() - getAutoWidthSize());
        }
        if(isAutoH())
        {
            int h = c.getHeight();
            setY((h == 0?height:h) - insets.bottom - getAutoSize() - getHeight() - getAutoHeightSize());
        }
        if(!isAutoW() && isAutoX())
            setX(insets.left + getAutoSize());
        if(!isAutoH() && isAutoY())
            setY(insets.top + getAutoSize());
        if(!isAutoW() && isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize() - getAutoWidthSize());
        }
        if(!isAutoH() && isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize() - getAutoHeightSize());
        }
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
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm) {
        if (parm == null)
            return;
        //保存配置对象
        setConfigParm(parm);
        //加载全部属性
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),
                getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], parm);
        //加载控制类
        if (getControl() != null)
            getControl().init();
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
    public String getDownLoadIndex() {
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
        TComponent parentTComponent = getParentTComponent();
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
        return null;
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
        for (int i = 0; i < getViewport().getComponentCount(); i++) {
            Component component = getViewport().getComponent(i);
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
     * 增加项目
     * @param com Object
     */
    public void addItem(Object com)
    {
        if(com == null)
            return;
        if(!(com instanceof Component))
            return;
        getViewport().add((Component)com);
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
        getViewport().add((Component)com,index);
    }
    /**
     * 调整UI
     */
    public void resetUI()
    {
        getViewport().updateUI();
    }
    /**
     * 加载布局管理器
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("空")) {
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
        getViewport().setLayout(layoutManager);
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
     * 设置自动X
     * @param autoX boolean
     */
    public void setAutoX(boolean autoX)
    {
        this.autoX = autoX;
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
     * 设置自动宽度尺寸
     * @param autoWidthSize int
     */
    public void setAutoWidthSize(int autoWidthSize)
    {
        this.autoWidthSize = autoWidthSize;
    }
    /**
     * 得到自动宽度尺寸
     * @return int
     */
    public int getAutoWidthSize()
    {
        return autoWidthSize;
    }
    /**
     * 设置自定高度尺寸
     * @param autoHeightSize int
     */
    public void setAutoHeightSize(int autoHeightSize)
    {
        this.autoHeightSize = autoHeightSize;
    }
    /**
     * 得到自动高度尺寸
     * @return int
     */
    public int getAutoHeightSize()
    {
        return autoHeightSize;
    }

    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
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
        releaseListeners();
        //释放控制类
        if (control != null)
        {
            control.release();
            control = null;
        }
        //释放属性
        RunClass.release(this);
    }
    /**
     * 得到继承加载器
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        if(getParentComponent() instanceof TContainer)
            return ((TContainer)getParentComponent()).getMaxLoad();
        return null;
    }
}
