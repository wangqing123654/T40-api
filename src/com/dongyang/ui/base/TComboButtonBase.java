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
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.dongyang.ui.TUIStyle;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.ui.TToolButton;
import javax.swing.JPopupMenu;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Cursor;
import com.dongyang.ui.event.TButtonEvent;

public class TComboButtonBase extends JComponent implements TComponent {
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
     * 固定宽度
     */
    public static int WIDTH = 42;
    /**
     * 固定高度
     */
    public static int HEIGHT = 32;
    /**
     * 选中按钮
     */
    private TToolButton button;
    /**
     * 鼠标进入状态
     */
    private boolean bEntered;
    /**
     * 鼠标点下状态
     */
    private boolean bPressed;
    /**
     * 弹出面板
     */
    private JPopupMenu pop = new PopupPanel();
    GridLayout gridLayout = new GridLayout();
    /**
     * 构造器
     */
    public TComboButtonBase() {
        uiInit();
    }

    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        setFont(TUIStyle.getButtonDefaultFont());
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(new Dimension(WIDTH, HEIGHT));
        validate();
        pop.setLayout(gridLayout);
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
    /*public void setGlobalKey(int keyValue, int controlKey) {
        registerKeyboardAction(actionListener,
                               KeyStroke.getKeyStroke(keyValue, controlKey, false),
                               2);
        //this.setAccelerator(KeyStroke.getKeyStroke(keyValue, controlKey, false));
    }*/

    /**
     * 加全局快捷键
     * @param key String "CTRL+F1"
     */
    /*public void setGlobalKey(String key) {
        this.key = key;
        setGlobalKey(StringTool.getKey(key), StringTool.getControlKey(key));
    }*/

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
        /*if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }*/
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
        bPressed = true;
        if (!pop.isShowing())
          pop.show(this, 0, HEIGHT);
        repaint();
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        bPressed = false;
        repaint();
    }
    /**
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        bEntered = true;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        repaint();
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        bEntered = false;
        repaint();
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

    }
    /**
     * 初始化
     */
    public void onInit() {
        //初始化监听事件
        initListeners();
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
     *
     * <p>Title: 弹出面板</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * <p>Company: JavaHis</p>
     * @author lzk 2006.08.03
     * @version 1.0
     */
    public class PopupPanel
        extends JPopupMenu
    {
      /**
       * 构造器
       */
      public PopupPanel()
      {
        setBackground(new Color(214, 221, 255));
        setBorderPainted(false);
      }

      public TComboButtonBase getCombo()
      {
          return TComboButtonBase.this;
      }

      /**
       * 画图
       * @param g Graphics
       */
      public void paint(Graphics g)
      {
        super.paint(g);
        drawBorder(g, 0, 0, getWidth(), getHeight(), 1);
      }
    }
    /**
     * 画边框
     * @param g Graphics 图形设备
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param style int 类型 0 无 1 凸 2 凹
     */
    public void drawBorder(Graphics g, int x, int y, int width, int height,
                           int style)
    {
      if (style == 0 || style > 2)
        return;
      Color c1 = new Color(255, 255, 255);
      Color c2 = new Color(165, 163, 151);
      if (style == 2)
      {
        c1 = new Color(165, 163, 151);
        c2 = new Color(255, 255, 255);
      }
      g.setColor(c1);
      g.drawLine(x, y, width - 1, y);
      g.drawLine(x, y, x, height - 1);
      g.setColor(c2);
      g.drawLine(width, y, width, height);
      g.drawLine(x, height, width, height);
    }
    /**
     * 设置列数
     * @param column int
     */
    public void setColumnCount(int column)
    {
        gridLayout.setColumns(column);
    }
    /**
     * 得到列数
     * @return int
     */
    public int getColumnCount()
    {
        return gridLayout.getColumns();
    }
    /**
     * 设置行数
     * @param row int
     */
    public void setRowCount(int row)
    {
        gridLayout.setRows(row);
    }
    /**
     * 得到行数
     * @return int
     */
    public int getRowCount()
    {
        return gridLayout.getRows();
    }
    /**
     * 加项目
     * @param c Component
     */
    public void addItem(Component c)
    {
        pop.add(c);
    }
    /**
     * 得到项目
     * @param index int
     * @return Component
     */
    public Component getItem(int index)
    {
        return pop.getComponent(index);
    }
    /**
     * 得到项目个数
     * @return int
     */
    public int getItemCount()
    {
        return pop.getComponentCount();
    }
    /**
     * 得到项目
     * @param tag String
     * @return TComponent
     */
    public TComponent getItem(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        int count = getItemCount();
        for(int i = 0;i < count;i++)
        {
            Component com = getItem(i);
            if(!(com instanceof TComponent))
                continue;
            TComponent tcom = (TComponent)com;
            if(tcom.getTag().equals(tag))
                return tcom;
        }
        return null;
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        int style = 0;
        int x = getWidth() - 10;
        int y = 1;
        if (bEntered)
        {
          style = 1;
          if (bPressed)
            style = 2;
          drawBorder(g, x, y, getWidth() - 1, getHeight() - 2, style);
        }
        if (bEntered && bPressed)
          drawPoit(g, x + 6, y + 11);
        else
          drawPoit(g, x + 5, y + 10);
    }
    /**
     * 画中间的三角
     * @param g Graphics 图形设备
     * @param x int
     * @param y int
     */
    public void drawPoit(Graphics g, int x, int y)
    {
        g.setColor(new Color(0, 0, 0));
        g.fillPolygon(new int[]
                      {x, x - 3, x + 3}
                      , new int[]
                      {y + 3, y, y}
                      , 3);
    }
    /**
     * 设置当前按钮
     * @param button TToolButton
     */
    public void setButton(TToolButton button)
    {
        if(button == null)
            return;
        TToolButton b = new TToolButton();
        b.setPictureName(button.getPictureName());
        b.setTag(button.getTag());
        b.setToolTipText(button.getToolTipText());
        b.setParentComponent(this);
        b.setMaximumSize(new Dimension(32,32));
        b.setMinimumSize(new Dimension(32,32));
        b.setPreferredSize(new Dimension(32,32));
        b.setSize(32,32);
        b.setActionMessage(button.getActionMessage());
        b.onInit();
        this.button = button;
        removeAll();
        add(b);
        validate();
        repaint();
        hidePop();
    }
    /**
     * 设置按钮
     * @param tag String 标签
     */
    public void setButton(String tag)
    {
        TComponent com = getItem(tag);
        if(com == null)
            return;
        if(!(com instanceof TToolButton))
            return;
        TToolButton button = (TToolButton)com;
        setButton(button);
    }
    /**
     * 隐藏弹出面板
     */
    public void hidePop()
    {
        if (pop.isShowing())
            pop.setVisible(false);
        this.grabFocus();
    }
    /**
     * 增加项目
     * @param tag String 标签
     * @param tip String 注释
     * @param pic String 图片
     * @param action String 动作
     * @return TToolButton
     */
    public TToolButton addItem(String tag,String tip,String pic,String action)
    {
        TToolButton b = new TToolButton();
        b.setTag(tag);
        b.setToolTipText(tip);
        b.setPictureName(pic);
        b.setParentComponent(this);
        b.setMaximumSize(new Dimension(32,32));
        b.setMinimumSize(new Dimension(32,32));
        b.setPreferredSize(new Dimension(32,32));
        b.setSize(32,32);
        b.setActionMessage(action);
        b.onInit();
        b.addEventListener(TButtonEvent.ACTION,this,"onToolbuttonAction");
        addItem(b);
        return b;
    }
    /**
     * 设置项目
     * @param items String
     */
    public void setItems(String items)
    {
        String list[] = StringTool.parseLine(items,";");
        for(int i = 0;i < list.length;i++)
        {
            String message[] = StringTool.parseLine(list[i],",");
            if(message.length < 4)
            {
                err("items 参数个数错误!" + list[i] + " 格式为: TAG,tip,pic,action");
                return;
            }
            addItem(message[0],message[1],message[2],message[3]);
        }
    }
    /**
     * 按钮动作
     * @param tag String
     * @param com TComponent
     */
    public void onToolbuttonAction(String tag,TComponent com)
    {
        TToolButton button = (TToolButton)com;
        setButton(button);
        button.getModel().setRollover(false);
        button.getModel().setPressed(false);
        callEvent(TButtonEvent.ACTION,new Object[]{tag,com},new String[]{"java.lang.String","com.dongyang.ui.TComponent"});
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
