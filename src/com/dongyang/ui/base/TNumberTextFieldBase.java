package com.dongyang.ui.base;

import javax.swing.JFormattedTextField;
import com.dongyang.ui.TComponent;
import com.dongyang.util.RunClass;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.StringTool;
import java.awt.Container;
import java.text.DecimalFormat;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.InternationalFormatter;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import com.dongyang.ui.event.TKeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import com.dongyang.ui.event.TNumberTextFieldEvent;
import java.text.ParseException;
import javax.swing.SwingUtilities;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.ui.TUIStyle;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import com.dongyang.ui.event.TFocusListener;
import java.awt.event.FocusEvent;
import com.dongyang.util.TypeTool;
import com.dongyang.util.TSystem;

public class TNumberTextFieldBase extends JFormattedTextField implements TComponent
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
     * 失去焦点动作
     */
    private String focusLostAction;
    /**
     * 鼠标事件监听对象
     */
    private TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    private TMouseMotionListener mouseMotionListenerObject;
    /**
     * 动作监听对象
     */
    private TActionListener actionListenerObject;
    /**
     * 键盘监听对象
     */
    private TKeyListener keyListenerObject;
    /**
     * 焦点事件监听对象
     */
    private TFocusListener focusListenerObject;
    /**
     * 是否初始化事件监听
     */
    private boolean isInitListener;
    /**
     * 格式
     */
    private String format;
    /**
     * 小数点左边个数
     */
    private int leftCount = 0;
    /**
     * 小数点左边个数
     */
    private int rightCount = 0;
    /**
     * 格式对象
     */
    private DecimalFormat formatObject;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
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
     * 构造器
     */
    public TNumberTextFieldBase() {
        uiInit();
    }
    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        setFormat("#########0.00");
        setHorizontalAlignment(RIGHT);
        setText("0");
        setTFont(TUIStyle.getNumberTextFieldDefaultFont());
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
        //监听鼠标移动事件
        if(mouseMotionListenerObject == null)
        {
            mouseMotionListenerObject = new TMouseMotionListener(this);
            addMouseMotionListener(mouseMotionListenerObject);
        }
        //监听键盘事件
        if(keyListenerObject == null)
        {
            keyListenerObject = new TKeyListener(this);
            addKeyListener(keyListenerObject);
        }
        //监听焦点事件
        if(focusListenerObject == null)
        {
            focusListenerObject = new TFocusListener(this);
            addFocusListener(focusListenerObject);
        }
        //监听动作事件
        if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }
        if(!isInitListener)
        {
            //监听Mouse事件
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,"onMouseReleased");
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,"onMouseEntered");
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,"onMouseExited");
            addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
            addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
            addEventListener(getTag() + "->" + TKeyListener.KEY_PRESSED,"onKeyPressed");
            addEventListener(getTag() + "->" + TKeyListener.KEY_RELEASED,"onKeyReleased");
            addEventListener(getTag() + "->" + TKeyListener.KEY_TYPE,"onKeyType");
            addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onActionPerformed");
            addEventListener(getTag() + "->" + TFocusListener.FOCUS_LOST,"onFocusLostAction");
            isInitListener = true;
        }
    }
    /**
     * 失去焦点事件
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        format();
        exeAction(getFocusLostAction());
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
    public void onActionPerformed(ActionEvent e)
    {
    }
    /**
     * 键盘按键事件
     * @param e KeyEvent
     */
    public void onKeyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            onKeyEnter(e);
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            onKeyESC(e);
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_TAB)
        {
            onKeyTab(e);
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
            format();
    }

    public String formatText()
    {
        if(formatObject == null)
            return getText();
        return formatObject.format(TypeTool.getDouble(getText()));
    }
    public void format() {
        int fb = getFocusLostBehavior();
        if (fb == JFormattedTextField.COMMIT ||
            fb == JFormattedTextField.COMMIT_OR_REVERT) {
            try {
                commitEdit();
                // Give it a chance to reformat.
                setValue(getValue());
            } catch (ParseException pe) {
                if (fb == COMMIT_OR_REVERT) {
                    setValue(getValue());
                }
            }
        }
        else if (fb == JFormattedTextField.REVERT) {
            setValue(getValue());
        }
    }
    /**
     * 键盘回车键按下
     * @param e KeyEvent
     */
    public void onKeyEnter(KeyEvent e)
    {
        format();
        callEvent(getTag() + "->" + TNumberTextFieldEvent.EDIT_ENTER,
                  new Object[]{},new String[]{""});
        callMessage(getTag() + "->" + TActionListener.ACTION_PERFORMED,null);
    }
    /**
     * 键盘ESC按下
     * @param e KeyEvent
     */
    public void onKeyESC(KeyEvent e)
    {
        callEvent(getTag() + "->" + TNumberTextFieldEvent.EDIT_ESC,
                  new Object[]{},new String[]{});
    }
    /**
     * 键盘TAB按下
     * @param e KeyEvent
     */
    public void onKeyTab(KeyEvent e)
    {
        format();
        if(e.isShiftDown())
            callEvent(getTag() + "->" + TNumberTextFieldEvent.EDIT_SHIFT_TAB,
                      new Object[]{},new String[]{});
        else
            callEvent(getTag() + "->" + TNumberTextFieldEvent.EDIT_TAB,
                      new Object[]{},new String[]{});
    }
    /**
     * 键盘抬键事件
     * @param e KeyEvent
     */
    public void onKeyReleased(KeyEvent e)
    {
    }
    /**
     * 键盘录入事件
     * @param e KeyEvent
     */
    public void onKeyType(KeyEvent e)
    {

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
            String message = value[i];
            if ("call".equalsIgnoreCase(message))
                message = getText();
            callMessage(message);
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
     * 设置格式
     * @param format String
     */
    public void setFormat(String format)
    {
        if(format == null|| format.length() == 0)
            return;
        if(this.format == format)
            return;
        formatObject = new DecimalFormat(format);
        setFormatterFactory(new DefaultFormatterFactory(new InternationalFormatter(formatObject)));
        this.format = format;
        leftCount = format.indexOf(".");
        if(leftCount == -1)
        {
            rightCount = 0;
            leftCount = format.length();
            return;
        }
        rightCount = format.length() - leftCount - 1;
    }
    /**
     * 得到格式
     * @return String
     */
    public String getFormat()
    {
        return format;
    }
    protected boolean processKeyBinding(KeyStroke ks, KeyEvent e,
                                        int condition, boolean pressed) {
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
        if(!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' ||
             e.getKeyChar() == '.' || e.getKeyChar() == '-'))
            return true;
        if(getSelectionStart() > 0 && e.getKeyChar() == '-')
            return true;
        if(rightCount == 0 && e.getKeyChar() == '.')
            return true;
        int left = getText().indexOf(".");
        if(left != -1 && e.getKeyChar() == '.')
            return true;
        if(left == -1)
            left = getText().length();
        int selectCount = getSelectionEnd() - getSelectionStart();
        int fh = 0;
        if(getText().startsWith("-"))
            fh = 1;
        if(getSelectionStart() <= left)
            if(left + 1 > leftCount + selectCount + fh && e.getKeyChar() != '.')
                return true;
        if(getSelectionStart() > left)
            if(getText().length() - left > rightCount + selectCount)
                return true;

        return super.processKeyBinding(ks, e, condition, pressed);
    }
    /**
     * 得到Table组件显示文本
     * @param value String
     * @return String
     */
    public String getTableShowValue(String value)
    {
        setText(value);
        format();
        return getText();
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
        return "TNumberTextFieldBase";
    }
    /**
     * 设置值
     * @param value Object
     */
    public void setValue(Object value)
    {
        if(value != null && value instanceof String)
        {
            if (((String) value).length() == 0)
                setValue(0);
            setText((String)value);
            format();
            return;
        }
        super.setValue(value);
    }
    /**
     * 得到焦点
     */
    public void grabFocus() {
        requestFocus();
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                selectAll();
            }
        });
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
     * 设置失去焦点动作
     * @param focusLostAction String
     */
    public void setFocusLostAction(String focusLostAction)
    {
        this.focusLostAction = focusLostAction;
    }
    /**
     * 得到失去焦点动作
     * @return String
     */
    public String getFocusLostAction()
    {
        return focusLostAction;
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
        if(language == null)
            return;

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
}
