package com.dongyang.ui.base;

import com.dongyang.ui.TComponent;
import javax.swing.JComponent;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import com.dongyang.util.RunClass;
import java.awt.Container;
import com.dongyang.util.StringTool;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Graphics;
import com.dongyang.ui.event.ActionMessage;
import java.awt.Font;
import com.dongyang.ui.TPanel;
import com.dongyang.ui.event.TComponentListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ComponentEvent;
import java.awt.Insets;

/**
 *
 * <p>Title: 基础控件</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.4
 * @version 1.0
 */
public class TComponentBase extends JComponent
        implements TComponent,MouseWheelListener
{
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
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 鼠标事件监听对象
     */
    private TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    private TMouseMotionListener mouseMotionListenerObject;
    /**
     * 组件监听对象
     */
    private TComponentListener componentListenerObject;
    /**
     * 光标
     */
    private int cursorType;
    /**
     * 消息地图
     */
    protected ActionMessage actionList;
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
     * 构造器
     */
    public TComponentBase()
    {
        actionList = new ActionMessage();
        this.addMouseWheelListener(this);
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
        if(message == null ||message.length() == 0)
            return null;
        Object value = onBaseMessage(message,parm);
        if(value != null)
            return value;
        if(getControl() != null)
        {
            value = getControl().callMessage(message,parm);
            if(value != null)
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
     * 得到父类
     * @return TComponent
     */
    public TComponent getParentTComponent()
    {
        return getParentTComponent(getParent());
    }
    /**
     * 得到父类(递归用)
     * @param container Container
     * @return TComponent
     */
    public TComponent getParentTComponent(Container container)
    {
        if(container == null)
            return null;
        if(container instanceof TComponent)
            return(TComponent)container;
        return getParentTComponent(container.getParent());
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message,Object parm)
    {
        if(message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message,"|");
        Object result = null;
        if((result = RunClass.invokeMethodT(this,value,parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message,"=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if((result = RunClass.invokeFieldT(this,value,parm)) != null)
            return result;
        return null;
    }
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[])
    {
        if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
    }
    /**
     * 设置组件配置
     * @param value String
     */
    public void setControlConfig(String value)
    {
        if(getControl() == null)
        {
            err("control is null");
            return;
        }
        getControl().setConfigParm(getConfigParm().newConfig(value));
    }
    /**
     * 设置组件控制类名
     * @param value String
     */
    public void setControlClassName(String value)
    {
        this.controlClassName = controlClassName;
        if(value == null || value.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(value);
        if(obj == null)
        {
            err("Class loadObject err className=" + value);
            return;
        }
        if(!(obj instanceof TControl))
        {
            err("Class loadObject type err className=" + value + " is not TControl");
            return;
        }
        TControl control = (TControl)obj;
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
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control)
    {
        this.control = control;
        if(control != null)
            control.setComponent(this);
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
     * 设置配置参数对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * 得到配置参数对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return configParm;
    }
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm)
    {
        if(configParm == null)
            return;
        //保存配置对象
        setConfigParm(configParm);
        //加载全部属性
        String value[] = configParm.getConfig().getTagList(configParm.getSystemGroup(),getLoadTag(),getDownLoadIndex());
        for(int index = 0;index < value.length;index++)
            if(filterInit(value[index]))
                callMessage(value[index], configParm);
        //加载控制类
       if (getControl() != null)
           getControl().init();
    }
    /**
     * 得到标签
     * @return String
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * 设置标签
     * @param tag String
     */
    public void setTag(String tag)
    {
        this.tag = tag;
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
     * 设置X坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.setLocation(x,getLocation().y);
    }
    /**
     * 设置Y坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.setLocation(getLocation().x,y);
    }
    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.setSize(width,getSize().height);
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.setSize(getSize().width,height);
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
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    protected boolean filterInit(String message)
    {
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
     * 初始化
     */
    public void onInit()
    {
        //初始化监听事件
        initListeners();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        if(getControl() != null)
            getControl().onInit();
    }
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        //监听鼠标事件
        if (mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //监听鼠标移动事件
        if (mouseMotionListenerObject == null)
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
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,"onRightClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
        TComponent component = getParentTComponent();
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
    }
    /**
     * 组件显示事件
     * @param e ComponentEvent
     */
    public void onComponentShown(ComponentEvent e)
    {
    }
    /**
     * 右击
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
    }
    /**
     * 点击
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
    }
    /**
     * 双击
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {

    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        sendActionMessage();
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
    }
    /**
     * 鼠标滑轮
     * @param e MouseWheelEvent
     */
    public void onMouseWheelMoved(MouseWheelEvent e)
    {

    }
    /**
     * 鼠标滑轮
     * @param e MouseWheelEvent
     */
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        onMouseWheelMoved(e);
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
     * 设置消息地图
     * @param name String
     * @param action String
     */
    public void setActionMessageMap(String name,String action)
    {
        actionList.setAction(name,action);
    }
    /**
     * 得到消息地图
     * @param name String
     * @return String
     */
    public String getActionMessageMap(String name)
    {
        return actionList.getAction(name);
    }
    /**
     * 设置动作消息
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        setActionMessageMap("ActionMessage",actionMessage);
    }

    /**
     * 得到动作消息
     * @return String
     */
    public String getActionMessage() {
        return getActionMessageMap("ActionMessage");
    }
    /**
     * 设置选择动作消息
     * @param actionMessage String
     */
    public void setChangedAction(String actionMessage) {
        setActionMessageMap("ChangedAction",actionMessage);
    }

    /**
     * 得到选择动作消息
     * @return String
     */
    public String getChangedAction() {
        return getActionMessageMap("ChangedAction");
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
     * 重画
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
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
     * 设置字体
     * @param name String
     */
    public void setFontName(String name)
    {
        Font f = getFont();
        setFont(new Font(name,f.getStyle(),f.getSize()));
    }
    /**
     * 得到字体
     * @return String
     */
    public String getFontName()
    {
        return getFont().getFontName();
    }
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getFont();
        setFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * 得到字体尺寸
     * @return int
     */
    public int getFontSize()
    {
        return getFont().getSize();
    }
    /**
     * 设置字体类型
     * @param style int
     */
    public void setFontStyle(int style)
    {
        Font f = getFont();
        setFont(new Font(f.getFontName(),style,f.getSize()));
    }
    /**
     * 得到字体类型
     * @return int
     */
    public int getFontStyle()
    {
        return getFont().getStyle();
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
        if(isAutoX())
            setX(insets.left + getAutoSize());
        if(isAutoY())
            setY(insets.top + getAutoSize());
        if(isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize());
        }
        if(isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize());
        }
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "TComponentBase";
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
}
