package com.dongyang.ui.base;

import com.dongyang.ui.TComponent;
import javax.swing.JComponent;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.event.BaseEvent;
import java.awt.Container;
import com.dongyang.ui.TUIStyle;
import com.dongyang.util.RunClass;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.util.StringTool;
import java.util.Vector;
import com.dongyang.ui.TListNode;
import javax.swing.Icon;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.Insets;
import com.dongyang.ui.event.TMouseListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.event.TListViewEvent;

public class TListViewBase extends JPanel implements TComponent,AdjustmentListener
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
     * 动作消息
     */
    private String actionMessage;
    /**
     * 数据包
     */
    private Vector nodes;
    /**
     * 图标列表
     */
    private String pics;
    /**
     * 图标列表
     */
    private Map icons;
    /**
     * 默认图标
     */
    private Icon defaultIcon;
    /**
     * 纵向滚动条
     */
    private JScrollBar scrollBarHeight = new JScrollBar();
    /**
     * 滚动条尺寸
     */
    private int scrollBarValue;
    /**
     * 组件宽度
     */
    public static int ITEM_WIDTH = 80;
    /**
     * 组件高度
     */
    public static int ITEM_HEIGHT = 80;
    /**
     * 绘图组件
     */
    private static _Label label = new _Label();
    /**
     * 鼠标事件监听对象
     */
    private TMouseListener mouseListenerObject;
    /**
     * 选中的项目
     */
    private TListNode selectedItem;
    /**
     * 点击动作
     */
    private String clickedAction;
    /**
     * 双击动作
     */
    private String doubleClickedAction;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 构造器
     */
    public TListViewBase() {
        uiInit();
    }
    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        nodes = new Vector();
        icons = new HashMap();
        label.setFont(TUIStyle.getListViewDefaultFont());
        setBackground(TUIStyle.getListViewDefaultColor());
        defaultIcon = createImageIcon("Clear.gif");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(scrollBarHeight);
        scrollBarHeight.addAdjustmentListener(this);
        /*TListNode node = new TListNode();
        node.setType("T1");
        node.setName("挂号系统");
        addItem(node);
        node = new TListNode();
        //node.setType("T1");
        node.setName("门诊医生站");
        addItem(node);
        node = new TListNode();
        node.setName("住院系统");
        addItem(node);
        node = new TListNode();
        node.setName("系统管理");
        addItem(node);*/
    }

    /**
     * 设置标签
     * @param tag String
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 得到标签
     * @return String
     */
    public String getTag() {
        return tag;
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
        //监听动作事件
        /*if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }
        //监听焦点事件
        if(focusListenerObject == null)
        {
            focusListenerObject = new TFocusListener(this);
            addFocusListener(focusListenerObject);
        }
        addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
        addEventListener(getTag() + "->" + TFocusListener.FOCUS_LOST,"onFocusLostAction");*/
        //监听Mouse事件
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onMouseDoublePressed");
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
            String message = value[i];
            //if ("call".equalsIgnoreCase(message))
            //    message = getText();
            callMessage(message);
        }
        return "OK";
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
            //if ("call".equalsIgnoreCase(actionMessage))
            //    actionMessage = getText();
            callMessage(actionMessage);
        }
        return "OK";
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
        else if ("action".equalsIgnoreCase(value[0]))
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
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "TTextFieldBase";
    }
    /**
     * 设置值
     * @param value String
     */
    public void setValue(String value)
    {
        setText(value);
    }
    /**
     * 得到值
     * @return String
     */
    public String getValue()
    {
        return null;//getText();
    }
    /**
     * 设置文本
     * @param t String
     */
    public void setText(String t)
    {
        //super.setText(t);
        //select(0,0);
        //validate();
    }
    /**
     * 得到项目个数
     * @return int
     */
    public int getItemCount()
    {
        return nodes.size();
    }
    /**
     * 增加项目
     * @param node TListNode
     */
    public void addItem(TListNode node)
    {
        nodes.add(node);
    }
    /**
     * 得到项目
     * @param index int
     * @return TListNode
     */
    public TListNode getItem(int index)
    {
        return (TListNode)nodes.get(index);
    }
    /**
     * 删除项目
     * @param index int
     */
    public void removeItem(int index)
    {
        if(selectedItem != null && selectedItem == getItem(index))
            selectedItem = null;
        nodes.remove(index);
    }
    /**
     * 设置图标列表
     * @param pics String
     */
    public void setPics(String pics)
    {
        this.pics = pics;
        icons = new HashMap();
        String s[] = StringTool.parseLine(pics,';',"[]{}()");
        for(int i = 0;i < s.length;i++)
        {
            String parm[] = StringTool.parseLine(s[i],':',"[]{}()");
            if(parm.length < 2)
                continue;
            String type = parm[0];
            String iconName = parm[1];
            if(iconName.trim().length() == 0)
                continue;
            Icon icon1 = createImageIcon(iconName);
            Icon icon2 = icon1;
            if(icon1 == null)
                continue;
            if(parm.length >= 3 && parm[2] != null && parm[2].trim().length() > 0)
            {
                icon2 = createImageIcon(parm[2]);
                if(icon2 == null)
                    icon2 = icon1;
            }
            icons.put(type,icon1);
            icons.put(type + "_E",icon2);
        }
    }
    /**
     * 得到图标列表
     * @return String
     */
    public String getPics()
    {
        return pics;
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        //绘制背景
        paintBack(g);
        super.paint(g);
        //绘制组件
        paintItems(g);
    }
    /**
     * 绘制背景
     * @param g Graphics
     */
    public void paintBack(Graphics g)
    {
        Rectangle rectangel = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(rectangel.x,rectangel.y,rectangel.width,rectangel.height);
    }
    /**
     * 绘制组件
     * @param g Graphics
     */
    public void paintItems(Graphics g)
    {
        gotoItems();
        int count = getItemCount();
        Insets insets = getInsets();
        g = g.create(insets.left,insets.top,getWidth() - insets.right,getHeight() - insets.bottom);
        Rectangle rectangel = g.getClipBounds();
        for(int i = 0;i < count;i++)
        {
            TListNode node = getItem(i);
            if(node.getX() + ITEM_WIDTH < rectangel.getX() ||
               node.getX() > rectangel.getX() + rectangel.getWidth() ||
               node.getY() + ITEM_HEIGHT < rectangel.getY() + scrollBarValue||
               node.getY() > rectangel.getY() + scrollBarValue + rectangel.getHeight())
                continue;
            //绘制组件
            int x = node.getX();
            int y = node.getY() - scrollBarValue;
            int width = ITEM_WIDTH;
            int height = ITEM_HEIGHT;
            paintItem(g.create(x,y,width,height),node);

        }
    }
    /**
     * 绘制组件
     * @param g Graphics
     * @param node TListNode
     */
    public void paintItem(Graphics g,TListNode node)
    {
        String type = node.getType();
        Icon icon = (Icon)icons.get(type);
        if(icon == null)
            icon = defaultIcon;
        label.setSelected(false);
        label.setIcon(icon);
        label.setText(null);
        label.setSize(ITEM_WIDTH,ITEM_HEIGHT);
        label.paint(g);
        Graphics g1 = g.create(0,ITEM_HEIGHT - 14,ITEM_WIDTH,14);
        label.setIcon(null);
        label.setSize(ITEM_WIDTH,14);
        label.setText(node.getName());
        if(selectedItem == node)
            label.setSelected(selectedItem == node);
        label.paint(g1);
        //g.setColor(new Color(255,0,0));
        //g.drawRect(0,0,ITEM_WIDTH - 1,ITEM_HEIGHT -1);
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
     * 摆放组件
     */
    public void gotoItems()
    {
        int count = getItemCount();
        Insets insets = getInsets();
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        int x = 0;
        int y = 0;
        for(int i = 0;i < count; i++)
        {
            TListNode node = getItem(i);
            node.setX(x);
            node.setY(y);
            x += ITEM_WIDTH;
            if(x + ITEM_WIDTH > width - 18)
            {
                x = 0;
                y += ITEM_HEIGHT;
            }
        }
        if(y < height)
        {
            scrollBarHeight.setVisible(false);
            scrollBarValue = 0;
        }
        else
        {
            scrollBarHeight.setLocation(getWidth() - 18 - insets.right,insets.top);
            scrollBarHeight.setSize(18,getHeight() - insets.top - insets.bottom);
            scrollBarHeight.setMaximum(y - getHeight() + insets.top + insets.bottom + 18);
            scrollBarHeight.setVisible(true);
            scrollBarHeight.validate();
        }
    }
    /**
     * 滚动条的值改变
     * @param e AdjustmentEvent
     */
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        if (e.getSource() == scrollBarHeight)
            scrollBarValue = e.getValue();
        repaint();
    }
    /**
     * 设置边框
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        TListNode node = getNodeForLocation(e.getX(),e.getY());
        if(node == null)
            return;
        selectedItem = node;
        repaint();
        callEvent(getTag() + "->" + TListViewEvent.CLICKED,new Object[]{node},new String[]{"com.dongyang.ui.TListNode"});
        //callMessage(getTag() + "->" + TListViewEvent.CLICKED,node);
        exeAction(getClickedAction());
    }
    /**
     * 鼠标双击
     * @param e MouseEvent
     */
    public void onMouseDoublePressed(MouseEvent e)
    {
        TListNode node = getNodeForLocation(e.getX(),e.getY());
        if(node == null)
            return;
        selectedItem = node;
        repaint();
        callEvent(getTag() + "->" + TListViewEvent.DOUBLE_CLICKED,new Object[]{node},new String[]{"com.dongyang.ui.TListNode"});
        //callMessage(getTag() + "->" + TListViewEvent.DOUBLE_CLICKED,node);
        exeAction(getDoubleClickedAction());
    }
    /**
     * 设置选中项目
     * @param selectedItem TListNode
     */
    public void setSelectedItem(TListNode selectedItem)
    {
        this.selectedItem = selectedItem;
    }
    /**
     * 得到选中项目
     * @return TListNode
     */
    public TListNode getSelectedItem()
    {
        return selectedItem;
    }
    /**
     * 根据坐标查询项目
     * @param x int
     * @param y int
     * @return TListNode
     */
    public TListNode getNodeForLocation(int x,int y)
    {
        Insets insets = getInsets();
        x += insets.left;
        y += scrollBarValue + insets.top;
        int count = getItemCount();
        for(int i = 0;i < count;i++)
        {
            TListNode node = getItem(i);
            if(x > node.getX() && x < node.getX() + ITEM_WIDTH &&
               y > node.getY() && y < node.getY() + ITEM_HEIGHT)
                return node;
        }
        return null;
    }
    private static class _Label extends JLabel
    {
        private boolean selected;
        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
        public boolean isSelected()
        {
            return selected;
        }
        /**
         * 绘制
         * @param g Graphics
         */
        public void paint(Graphics g)
        {
            if(!isSelected())
            {
                super.paint(g);
                return;
            }
            g.setColor(new Color(49,106,197));
            g.fillRect(0,0,getWidth(),getHeight());
            Color c = getForeground();
            setForeground(new Color(255,255,255));
            super.paint(g);
            setForeground(c);
        }
    }
    /**
     * 设置点击动作
     * @param clickedAction String
     */
    public void setClickedAction(String clickedAction)
    {
        this.clickedAction = clickedAction;
    }
    /**
     * 得到点击动作
     * @return String
     */
    public String getClickedAction()
    {
        return clickedAction;
    }
    /**
     * 设置双击动作
     * @param doubleClickedAction String
     */
    public void setDoubleClickedAction(String doubleClickedAction)
    {
        this.doubleClickedAction = doubleClickedAction;
    }
    /**
     * 得到双击动作
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return doubleClickedAction;
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
}
