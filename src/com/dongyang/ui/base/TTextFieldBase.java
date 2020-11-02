package com.dongyang.ui.base;

import javax.swing.JTextField;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.control.TControl;
import java.awt.Container;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TTableSort;
import com.dongyang.ui.TUIStyle;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import com.dongyang.util.TMessage;
import com.dongyang.ui.event.TFocusListener;
import java.awt.event.FocusEvent;
import java.sql.Timestamp;
import java.awt.Dimension;
import com.dongyang.ui.event.TKeyListener;
import com.dongyang.ui.event.TTextFieldEvent;
import javax.swing.UIManager;
import com.dongyang.ui.TPopupMenu;
import com.dongyang.data.TParm;
import com.dongyang.ui.event.TPopupMenuEvent;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.ui.event.TMouseListener;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import com.dongyang.ui.TTable;
import java.awt.Font;
import com.dongyang.util.TSystem;

public class TTextFieldBase extends JTextField implements TComponent {
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
     * 输入字符的长度
     */
    private int inputLength;
    /**
     * 动作事件监听对象
     */
    private TActionListener actionListenerObject;
    /**
     * 焦点事件监听对象
     */
    private TFocusListener focusListenerObject;
    /**
     * 键盘事件监听对象
     */
    private TKeyListener keyListenerObject;
    /**
     * 拼音带入的标签
     */
    private String pyTag;
    /**
     * 省带入的标签
     */
    private String stateTag;
    /**
     * 城市带入的标签
     */
    private String cityTag;
    /**
     * 执行身份证解析
     */
    private boolean exePID;
    /**
     * 外国人带入的标签
     */
    private String foreignerTag;
    /**
     * 性别带入的标签
     */
    private String sexTag;
    /**
     * 生日带入的标签
     */
    private String birdayTag;
    /**
     * 弹出菜单
     */
    private TPopupMenu popupMenu;

    /**
     * 弹出菜单参数类
     */
    private PopupMenuParameter popupMenuParameter;
    /**
     * 动作列表
     */
    private ActionMessage actionList;
    /**
     * 鼠标事件监听对象
     */
    private TMouseListener mouseListenerObject;
    private TTable table;

    private TTableSort tableSort;
    /**
     * 中文Tip
     */
    private String zhTip;
    /**
     * 英文Tip
     */
    private String enTip;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 语种
     */
    private String language;
    /**
     * 字体
     */
    private Font tfont;

    /**
     * 必填项
     */
    private boolean required;

    /**
     * 构造器
     */
    public TTextFieldBase() {
        uiInit();
    }
    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        //初始化动作列表
        actionList = new ActionMessage();
        setTFont(TUIStyle.getTextFieldDefaultFont());
        UIManager.put("TextField.inactiveForeground", TUIStyle.getInactiveForeground());
        updateUI();
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
     * @param action String
     */
    public void setActionMessage(String action) {
        actionList.setAction("actionAction",action);
    }

    /**
     * 得到动作消息
     * @return String
     */
    public String getActionMessage() {
        return actionList.getAction("actionAction");
    }
    /**
     * 设置失去焦点动作
     * @param action String
     */
    public void setFocusLostAction(String action)
    {
        actionList.setAction("focusLostAction",action);
    }
    /**
     * 得到失去焦点动作
     * @return String
     */
    public String getFocusLostAction()
    {
        return actionList.getAction("focusLostAction");
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
        if(actionListenerObject == null)
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
        if(keyListenerObject == null)
        {
            keyListenerObject = new TKeyListener(this);
            addKeyListener(keyListenerObject);
        }
        addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
        addEventListener(getTag() + "->" + TFocusListener.FOCUS_LOST,"onFocusLostAction");
        addEventListener(getTag() + "->" + TKeyListener.KEY_PRESSED,"onKeyPressed");
        addEventListener(getTag() + "->" + TKeyListener.KEY_RELEASED,"onKeyReleased");
        addEventListener(TTextFieldEvent.KEY_RELEASED,"onTextKeyReleased");
        //监听Mouse事件
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,"onRightClicked");
    }
    /**
     * 点击
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
        exeAction(getClickedAction());
    }
    /**
     * 右击
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
        exeAction(getRightClickedAction());
    }
    /**
     * 双击
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {
        exeAction(getDoubleClickedAction());
    }
    /**
     * 动作事件
     * @param e ActionEvent
     */
    public void onAction(ActionEvent e)
    {
        //执行拼音带入动作
        onPyTag();
        //执行省带入动作
        onStateTag();
        //执行城市带入动作
        onCityTag();
        //执行带入性别
        onSexTag();
        //执行带入生日
        onBirdayTag();
    }
    /**
     * 失去焦点事件
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        exeAction(getFocusLostAction());
    }
    /**
     * 按键事件
     * @param e KeyEvent
     */
    public void onKeyPressed(KeyEvent e)
    {

    }
    /**
     * 抬键事件
     * @param e KeyEvent
     */
    public void onKeyReleased(KeyEvent e)
    {
        callEvent(TTextFieldEvent.KEY_RELEASED,new Object[]{getText()},new String[]{"java.lang.String"});
    }
    /**
     * 内部抬键事件
     * @param s String
     */
    public void onTextKeyReleased(String s)
    {
        if(getPopupMenuParameter() == null)
            return;
        if(getPopupMenu() == null)
            return;
        getPopupMenuParameter().parm.setData("TEXT",s);
        getPopupMenu().setParameter(getPopupMenuParameter().parm);
        getPopupMenu().showPopupMenu();
        getPopupMenu().changeLanguage(language);
        getPopupMenu().callFunction("onInitReset");
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
        //调整语种
        changeLanguage((String)TSystem.getObject("Language"));
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
            if ("call".equalsIgnoreCase(message))
                message = getText();
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
            if ("call".equalsIgnoreCase(actionMessage))
                actionMessage = getText();
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
     * 设置输入字符的长度
     * @param inputLength int 0 不限制
     */
    public void setInputLength(int inputLength)
    {
        this.inputLength = inputLength;
    }
    /**
     * 得到输入字符的长度
     * @return int 0 不限制
     */
    public int getInputLength()
    {
        return inputLength;
    }
    protected boolean processKeyBinding(KeyStroke ks, KeyEvent e,
                                        int condition, boolean pressed) {
        if(getInputLength() <= 0)
            return super.processKeyBinding(ks, e, condition, pressed);
        if(e.isActionKey() || e.isControlDown() || e.isAltDown())
            return super.processKeyBinding(ks, e, condition, pressed);
        if(e.isShiftDown() && (e.getKeyCode() == KeyEvent.VK_LEFT||
                               e.getKeyCode() == KeyEvent.VK_RIGHT))
            return super.processKeyBinding(ks, e, condition, pressed);
        if(e.getKeyCode() == KeyEvent.VK_DELETE ||
           e.getKeyCode() == KeyEvent.VK_BACK_SPACE||
           e.getKeyCode() == KeyEvent.VK_ENTER||
           e.getKeyCode() == KeyEvent.VK_TAB||
           e.getKeyCode() == KeyEvent.VK_ESCAPE)
            return super.processKeyBinding(ks, e, condition, pressed);
        int selectCount = getSelectionEnd() - getSelectionStart();
        if(getText().length() >= getInputLength() + selectCount)
            return false;
        return super.processKeyBinding(ks, e, condition, pressed);
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
        return getText();
    }
    /**
     * 得到焦点
     */
    public void grabFocus() {
        requestFocus();
        selectAll();
    }
    /**
     * 设置文本
     * @param t String
     */
    public void setText(String t)
    {
        super.setText(t);
        select(0,0);
        validate();
    }
    /**
     * 设置拼音带入的Tag
     * @param pyTag String
     */
    public void setPyTag(String pyTag)
    {
        this.pyTag = pyTag;
    }
    /**
     * 得到拼音带入的Tag
     * @return String
     */
    public String getPyTag()
    {
        return pyTag;
    }
    /**
     * 设置省带入的Tag
     * @param stateTag String
     */
    public void setStateTag(String stateTag)
    {
        this.stateTag = stateTag;
    }
    /**
     * 得到省带入的Tag
     * @return String
     */
    public String getStateTag()
    {
        return stateTag;
    }
    /**
     * 设置城市带入的Tag
     * @param cityTag String
     */
    public void setCityTag(String cityTag)
    {
        this.cityTag = cityTag;
    }
    /**
     * 得到城市带入的Tag
     * @return String
     */
    public String getCityTag()
    {
        return cityTag;
    }
    /**
     * 设置解析身份证
     * @param exePID boolean
     */
    public void setExePID(boolean exePID)
    {
        this.exePID = exePID;
    }
    /**
     * 是否解析身份证
     * @return boolean
     */
    public boolean isExePID()
    {
        return exePID;
    }
    /**
     * 设置外国人带入的标签
     * @param foreignerTag String
     */
    public void setForeignerTag(String foreignerTag)
    {
        this.foreignerTag = foreignerTag;
    }
    /**
     * 得到外国人带入的标签
     * @return String
     */
    public String getForeignerTag()
    {
        return foreignerTag;
    }
    /**
     * 设置性别带入标签
     * @param sexTag String
     */
    public void setSexTag(String sexTag)
    {
        this.sexTag = sexTag;
    }
    /**
     * 得到性别带入的标签
     * @return String
     */
    public String getSexTag()
    {
        return sexTag;
    }
    /**
     * 设置生日带入的标签
     * @param birdayTag String
     */
    public void setBirdayTag(String birdayTag)
    {
        this.birdayTag = birdayTag;
    }
    /**
     * 得到生日带入的标签
     * @return String
     */
    public String getBirdayTag()
    {
        return birdayTag;
    }
    /**
     * 拼音带入动作
     */
    public void onPyTag()
    {
        if(getPyTag() == null || getPyTag().length() == 0)
            return;
        String py = TMessage.getPy(getText());
        callFunction(getPyTag() + "|setText",py);
    }
    /**
     * 省带入动作
     */
    public void onStateTag()
    {
        if(getStateTag() == null || getStateTag().length() == 0)
            return;
        String state = TMessage.getState(getText());
        callFunction(getStateTag() + "|setText",state);
    }
    /**
     * 城市带入动作
     */
    public void onCityTag()
    {
        if(getCityTag() == null || getCityTag().length() == 0)
            return;
        String city = TMessage.getCity(getText());
        callFunction(getCityTag() + "|setText",city);
    }
    /**
     * 性别带入动作
     */
    public void onSexTag()
    {
        if(!isExePID() || getSexTag() == null || getSexTag().length() == 0)
            return;
        if(!getForeignerFlg())
            return;
        String sex = StringTool.isMaleFromID(getText());
        callFunction(getSexTag() + "|setText",sex);
    }
    /**
     * 生日带入动作
     */
    public void onBirdayTag()
    {
        if(!isExePID() || getBirdayTag() == null || getBirdayTag().length() == 0)
            return;
        if(!getForeignerFlg())
            return;
        Timestamp birday = StringTool.getBirdayFromID(getText());
        callFunction(getBirdayTag() + "|setValue",birday);
    }

    /**
     * 读取外国人主机标记
     * @return boolean
     */
    public boolean getForeignerFlg()
    {
        if(getForeignerTag() == null || getForeignerTag().length() == 0)
            return true;
        if("Y".equals(callFunction(getForeignerTag() + "|getValue")))
            return false;
        return true;
    }
    /**
     * 设置最佳宽度
     * @param width int
     */
    public void setPreferredWidth(int width)
    {
        Dimension d = getPreferredSize();
        d.setSize(width,d.getHeight());
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }
    /**
     * 设置最佳高度
     * @param height int
     */
    public void setPreferredHeight(int height)
    {
        Dimension d = getPreferredSize();
        d.setSize(d.getWidth(),height);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }
    /**
     * 设置弹出菜单参数类
     * @param tag String 弹出菜单Tag
     * @param filename String 弹出菜单文件名
     */
    public void setPopupMenuParameter(String tag,String filename)
    {
        setPopupMenuParameter(tag,filename,new TParm());
    }
    /**
     * 设置弹出菜单参数类
     * @param tag String 弹出菜单Tag
     * @param filename String 弹出菜单文件名
     * @param parm TParm 弹出菜单传入参数
     */
    public void setPopupMenuParameter(String tag,String filename,TParm parm)
    {
        setPopupMenuParameter(tag,getConfigParm().newConfig(filename),parm);
    }
    /**
     * 设置弹出菜单参数类
     * @param tag String 弹出菜单Tag
     * @param configParm TConfigParm 弹出菜单配置文件
     */
    public void setPopupMenuParameter(String tag,TConfigParm configParm)
    {
        setPopupMenuParameter(tag,configParm,new TParm());
    }
    /**
     * 设置弹出菜单参数类
     * @param tag String 弹出菜单Tag
     * @param configParm TConfigParm 弹出菜单配置文件
     * @param parm TParm 弹出菜单传入参数
     */
    public void setPopupMenuParameter(String tag,TConfigParm configParm,TParm parm)
    {
        PopupMenuParameter popupMenuParameter = new PopupMenuParameter();
        popupMenuParameter.tag = tag;
        popupMenuParameter.configParm = configParm;
        popupMenuParameter.parm = parm;
        if(getPopupMenuParameter() != null && getPopupMenuParameter().equals(popupMenuParameter))
            return;
        setPopupMenuParameter(popupMenuParameter);
        TPopupMenu popup = TPopupMenu.getPopupMenu(tag,this,configParm,parm);
        popup.addEventListener(TPopupMenuEvent.RETURN_VALUE,this,"popupMenuEvent");
        setPopupMenu(popup);
    }
    /**
     * 接收弹出菜单的返回值
     * @param tag String
     * @param returnValue Object
     */
    public void popupMenuEvent(String tag,Object returnValue)
    {
        this.callEvent(TPopupMenuEvent.RETURN_VALUE,
                       new Object[] {tag,returnValue},
                       new String[] {"java.lang.String","java.lang.Object"});
    }
    /**
     * 设置弹出菜单
     * @param popupMenu TPopupMenu
     */
    public void setPopupMenu(TPopupMenu popupMenu)
    {
        this.popupMenu = popupMenu;
    }
    /**
     * 得到弹出菜单
     * @return TPopupMenu
     */
    public TPopupMenu getPopupMenu()
    {
        return popupMenu;
    }
    /**
     * 设置弹出菜单参数类
     * @param popupMenuParameter PopupMenuParameter
     */
    public void setPopupMenuParameter(PopupMenuParameter popupMenuParameter)
    {
        this.popupMenuParameter = popupMenuParameter;
    }
    /**
     * 得到弹出菜单参数类
     * @return PopupMenuParameter
     */
    public PopupMenuParameter getPopupMenuParameter()
    {
        return popupMenuParameter;
    }
    /**
     *
     * <p>Title: 弹出菜单参数类</p>
     *
     * <p>Description: </p>
     *
     * <p>Copyright: Copyright (c) 2008</p>
     *
     * <p>Company: JavaHis</p>
     *
     * @author lzk 2008.12.17
     * @version 1.0
     */
    public class PopupMenuParameter
    {
        public String tag;
        public TConfigParm configParm;
        public TParm parm;
        public boolean equals(Object obj)
        {
            if(obj == null)
                return false;
            if(!(obj instanceof PopupMenuParameter))
                return false;
            PopupMenuParameter p = (PopupMenuParameter)obj;
            if(tag != null && p.tag == null)
                return false;
            if(!tag.equals(p.tag))
                return false;
            if(!configParm.getConfig().getFileName().equals(p.configParm.getConfig().getFileName()))
                return false;
            return true;
        }
    }
    /**
     * 设置单击动作
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * 得到单击动作
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * 设置双击动作
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * 得到双击动作
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * 设置右击动作
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * 得到右击动作
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * 设置Table
     * @param table TTable
     */
    public void setTable(TTable table)
    {
        this.table = table;
    }
    /**
     * 得到Table
     * @return TTable
     */
    public TTable getTable()
    {
        return table;
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

    public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
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
        if("en".equals(language) && getEnTip() != null && getEnTip().length() > 0)
            setToolTipText(getEnTip());
        else if(getZhTip() != null && getZhTip().length() > 0)
            setToolTipText(getZhTip());
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

	public TTableSort getTableSort() {
		return tableSort;
	}
	public void setTableSort(TTableSort tableSort) {
		this.tableSort = tableSort;
	}


}
