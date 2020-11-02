package com.dongyang.ui.base;

import javax.swing.JPopupMenu;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.StringTool;
import java.awt.Container;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Graphics;
import javax.swing.border.Border;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.TMenu;
import com.dongyang.ui.TMenuItem;
import java.awt.LayoutManager;
import com.dongyang.ui.event.TPopupMenuEvent;
import java.awt.Point;
import javax.swing.MenuSelectionManager;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import com.dongyang.ui.TTextField;
import com.dongyang.tui.DMessageIO;
import com.dongyang.jdo.MaxLoad;

public class TPopupMenuBase extends JPopupMenu implements TComponent,
        TContainer {
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
     * 控制类
     */
    private TControl control;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 消息IO;
     */
    private DMessageIO messageIO;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 焦点处理对象
     */
    private TFocusTraversalPolicy tFocus;
    /**
     * 传入参数
     */
    private Object parameter;
    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 不隐含
     */
    private boolean notHide;
    /**
     * 内部不隐含
     */
    private boolean insetNotHide;
    /**
     * 类型
     */
    private String type;
    /**
     * 目标容器
     */
    private Component invokerComponent;
    /**
     * 语种
     */
    private String language;
    /**
     * Creates a TPopupMenu.
     */
    public TPopupMenuBase() {
        setBackground(TUIStyle.getMenuBackColor());
        setBorder(new Border() {
            public void paintBorder(Component c, Graphics g, int x, int y,
                                    int width, int height) {
                g.setColor(TUIStyle.getMenuBorderColor());
                g.drawLine(0, 0, 0, height - 1);
                g.drawLine(0, 0, width - 1, 0);
                g.drawLine(width - 1, 0, width - 1, height - 1);
                g.drawLine(0, height - 1, width - 1, height - 1);
            }

            public Insets getBorderInsets(Component c) {
                return new Insets(1, 1, 1, 1);
            }

            public boolean isBorderOpaque() {
                return true;
            }
        });
        //创建焦点控制类
        tFocus = new TFocusTraversalPolicy();
        //取出焦点
        setFocusable(false);
        //设置焦点控制类
        setFocusTraversalPolicy(tFocus);
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
     * 设置消息IO
     * @param messageIO DMessageIO
     */
    public void setMessageIO(DMessageIO messageIO)
    {
        this.messageIO = messageIO;
    }
    /**
     * 得到消息IO
     * @return DMessageIO
     */
    public DMessageIO getMessageIO()
    {
        return messageIO;
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
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        if (getControl() != null)
            getControl().onInit();
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
        System.out.println("value[] " + getLoadTag() + " " + StringTool.getString(value));
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
    public boolean filterInit(String message) {
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
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        if(getMessageIO() != null)
            getMessageIO().callMessage(message,parm);
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
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
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
        if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
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
     * 设置项目
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
    }

    /**
     * 创建子组件
     * @param value String
     */
    private void createItem(String value) {
        //竖线代表分割线
        if (value.equals("|")) {
            addSeparator();
            return;
        }
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
        }
        //设置父类组件
        if (component instanceof TMenu)
            ((TMenu) component).setParentComponent(this);
        if (component instanceof TMenuItem)
            ((TMenuItem) component).setParentComponent(this);
        if (values.length == 1)
            add(component);
        else if (values.length == 2)
            add(component, StringTool.layoutConstraint(values[1]));
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
        setLayout(layoutManager);
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
     * 显示
     * @param invoker Component 父类组建
     * @param x int
     * @param y int
     */
    public void show(Component invoker, int x, int y) {
        super.show(invoker,x,y);
        if(tFocus.getCompFocus().length > 0)
            callMessage(tFocus.getCompFocus()[0] + "|grabFocus");
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
     * 设置返回值
     * @param returnValue String
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
        this.callEvent(TPopupMenuEvent.RETURN_VALUE,
                       new Object[] {tag,returnValue},
                       new String[] {"java.lang.String","java.lang.Object"});
    }

    /**
     * 得到返回值
     * @return Object
     */
    public Object getReturnValue() {
        return returnValue;
    }
    /**
     * 设置不隐含
     * @param notHide boolean
     */
    public void setNotHide(boolean notHide)
    {
        this.notHide = notHide;
    }
    /**
     * 是否不隐含
     * @return boolean
     */
    public boolean isNotHide()
    {
        return notHide;
    }
    /**
     * 设置内部不隐含
     * @param insetNotHide boolean
     */
    public void setInsetNotHide(boolean insetNotHide)
    {
        this.insetNotHide = insetNotHide;
    }
    /**
     * 是否内部不隐含
     * @return boolean
     */
    public boolean isInsetNotHide()
    {
        return insetNotHide;
    }
    /**
     * 隐含
     */
    public void hidePopup()
    {
        boolean b = notHide;
        boolean b1 = insetNotHide;
        notHide = false;
        insetNotHide = false;
        setVisible(false);
        notHide = b;
        insetNotHide = b1;
        if(getInvokerComponent() != null && getInvokerComponent() instanceof TTextField)
            ((TTextField)getInvokerComponent()).grabFocus();
    }
    /**
     * 设置类型
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return type;
    }
    public void setVisible(boolean visible)
    {
        if(!visible && notHide)
            return;
        if(!visible && insetNotHide && getMousePosition() != null)
        {
            /*SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MenuElement[] oldme = MenuSelectionManager.defaultManager().getSelectedPath();
                    MenuElement[] me = new MenuElement[oldme.length + 1];
                    System.arraycopy(oldme,0,me,0,oldme.length);
                    me[oldme.length] = (MenuElement) TPopupMenuBase.this;
                    MenuSelectionManager.defaultManager().
                            setSelectedPath(me);
                }
            });*/
            MenuElement[] me = new MenuElement[1];

            me[0] = (MenuElement) TPopupMenuBase.this;
            MenuSelectionManager.defaultManager().
                    setSelectedPath(me);
            return;
        }
        super.setVisible(visible);
    }
    /**
     * 设置目标容器
     * @param invokerComponent Component
     */
    public void setInvokerComponent(Component invokerComponent)
    {
        this.invokerComponent = invokerComponent;
    }
    /**
     * 得到目标容器
     * @return Component
     */
    public Component getInvokerComponent()
    {
        return invokerComponent;
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
        int count = getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callFunction("changeLanguage",language);
            }
        }

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
}
