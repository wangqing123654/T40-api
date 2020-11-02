package com.dongyang.ui.base;

import javax.swing.JMenuItem;
import javax.swing.Icon;
import javax.swing.Action;
import com.dongyang.config.TConfig;
import com.dongyang.util.StringTool;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import com.dongyang.control.TControl;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.config.TConfigParm;
import java.awt.event.ActionListener;
import com.dongyang.ui.event.TActionListener;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.FontMetrics;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TMenuBar;
import com.dongyang.ui.TToolBar;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.TMenu;

public class TMenuItemBase extends JMenuItem implements TComponent {
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
     * 设置文本
     */
    private String text;
    /**
     * 快捷键
     */
    private String key;
    /**
     * 动作消息
     */
    private String actionMessage;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 动作坚挺对象
     */
    private TActionListener actionListener = new TActionListener(this);
    /**
     * 图片名称
     */
    private String pictureName;
    /**
     * 图片对象
     */
    private ImageIcon pic;
    private static Icon defaultIcon;
    private static JLabel label;
    /**
     * 中文文本
     */
    private String zhText;
    /**
     * 英文文本
     */
    private String enText;
    /**
     * 语种
     */
    private String language;
    /**
     * Creates a <code>JMenuItem</code> with no set text or icon.
     */
    public TMenuItemBase() {
        this(null, (Icon)null);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified icon.
     *
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public TMenuItemBase(Icon icon) {
        this(null, icon);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text.
     *
     * @param text the text of the <code>JMenuItem</code>
     */
    public TMenuItemBase(String text) {
        this(text, (Icon)null);
    }

    /**
     * Creates a menu item whose properties are taken from the
     * specified <code>Action</code>.
     *
     * @param a the action of the <code>JMenuItem</code>
     * @since 1.3
     */
    public TMenuItemBase(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and icon.
     *
     * @param text the text of the <code>JMenuItem</code>
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public TMenuItemBase(String text, Icon icon) {
        super(text, icon);
        uiInit();
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and
     * keyboard mnemonic.
     *
     * @param text the text of the <code>JMenuItem</code>
     * @param mnemonic the keyboard mnemonic for the <code>JMenuItem</code>
     */
    public TMenuItemBase(String text, int mnemonic) {
        super(text, mnemonic);
        uiInit();
    }

    /**
     * UI初始化
     */
    public void uiInit() {
        setBackground(TUIStyle.getMenuItemBackColor());
        setFont(TUIStyle.getMenuFont());
        if (defaultIcon == null)
            defaultIcon = createImageIcon("close.gif");
        setIcon(defaultIcon);
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
     * 设置按钮图片
     * @param name 图片名称
     */
    public void setPictureName(String name) {
        pictureName = name;
        if (name != null)
            downPic();
        //this.setIcon(pic);
    }
    public void downPic()
    {
        new Thread(){
            public void run()
            {
                pic = createImageIcon(pictureName);
            }
        }.start();
    }

    /**
     * 得到按钮图片
     * @return 图片名称
     */
    public String getPictureName() {
        return pictureName;
    }

    /**
     * 设置动作消息
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
        TComponent component = getToolButton(getTag());
        if (component == null)
            return;
        component.callFunction("setActionMessage",actionMessage);
    }

    /**
     * 得到动作消息
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }

    /**
     * 设置有效 (增加同步工具条方法)
     * @param b boolean
     */
    public void setEnabled(boolean b) {
        if (isEnabled() == b)
            return;
        super.setEnabled(b);
        TComponent component = getToolButton(getTag());
        if (component == null)
            return;
        component.callMessage("setEnabled|" + b);
    }

    /**
     * 设置显示 (增加同步工具条方法)
     * @param b boolean
     */
    public void setVisible(boolean b) {
        if (isVisible() == b)
            return;
        super.setVisible(b);
        TComponent component = getToolButton(getTag());
        if (component == null)
            return;
        component.callMessage("setVisible|" + b);
    }

    /**
     * 得到工具条
     * @return TToolBar
     */
    public TToolBar getToolBar() {
        TMenuBar menubar = getMenuBar();
        if (menubar == null)
            return null;
        return menubar.getToolBar();
    }

    /**
     * 得到工具条内部组件
     * @param tag String
     * @return TComponent
     */
    public TComponent getToolButton(String tag) {
        TToolBar toolbar = getToolBar();
        if (toolbar == null)
            return null;
        TComponent component = toolbar.findObject(tag);
        if (component == null)
            return null;
        return component;
    }

    /**
     * 得到菜单条
     * @return TMenuBar
     */
    public TMenuBar getMenuBar() {
        TComponent component = getParentComponent();
        while (component != null) {
            if (component instanceof TMenuBar)
                return (TMenuBar) component;
            if (component instanceof TMenu)
                component = ((TMenu) component).getParentComponent();
            else
                break;
        }
        return null;
    }

    /**
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename) {
        if (TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" +
                                           filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if (icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try {
            icon = new ImageIcon(getClass().getResource(path));
        } catch (NullPointerException e) {
            err("没有找到图标" + path);
        }
        return icon;
    }

    /**
     * 加全局快捷键
     * @param keyValue int
     * @param controlKey int 1 shift 2 ctrl 3 shift+ctrl
     */
    public void setGlobalKey(int keyValue, int controlKey) {
        //registerKeyboardAction(actionListener, KeyStroke.getKeyStroke(keyValue, controlKey, false), 2);
        this.setAccelerator(KeyStroke.getKeyStroke(keyValue, controlKey, false));
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
     * 初始化
     */
    public void onInit() {
        ActionListener actionListeners[] = getActionListeners();
        for (int i = 0; i < actionListeners.length; i++)
            removeActionListener(actionListeners[i]);
        addActionListener(actionListener);
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
    public boolean filterInit(String message) {
        String value[] = StringTool.getHead(message, "=");
        if ("tip".equalsIgnoreCase(value[0]))
            return false;
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
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
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
        //启动默认动作
        value = onDefaultActionMessage(message, parm);
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
            return sendActionMessage();
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
        else if ("key".equalsIgnoreCase(value[0]))
            value[0] = "globalkey";
        else if ("pic".equalsIgnoreCase(value[0]))
            value[0] = "pictureName";
        else if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
        //else if("Tip".equalsIgnoreCase(value[0]))
        //    value[0] = "toolTipText";
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
     * 设置快捷键
     * @param c char
     */
    public void setM(char c) {
        setMnemonic(c);
    }

    /**
     * 得到快捷键
     * @return char
     */
    public char getM() {
        return (char) getMnemonic();
    }

    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
        if (this.getMnemonic() > 0)
            text += "(" + (char) getMnemonic() + ")";
        super.setText(text);
    }
    /**
     * 设置中文文本
     * @param zhText String
     */
    public void setZhText(String zhText)
    {
        this.zhText = zhText;
    }
    /**
     * 得到中文文本
     * @return String
     */
    public String getZhText()
    {
        return zhText;
    }
    /**
     * 设置英文文本
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * 得到英文文文本
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }

    /**
     * 设置快捷键
     * @param mnemonic char
     */
    public void setMnemonic(char mnemonic) {
        super.setMnemonic(mnemonic);
        if (mnemonic != 0)
            setText(getTextString());
    }

    /**
     * 返回基本文本信息
     * @return String
     */
    public String getTextString() {
        return text;
    }

    /**
     * 绘图
     * @param g Graphics
     */
    public void paint(Graphics g) {
        g.setColor(TUIStyle.getMenuItemBackColor());
        g.fillRect(0, 0, getWidth(), getHeight());

        int picSize = TUIStyle.getMenuItemIconSize();
        fillTransition(0, 0, picSize + 6, getHeight(), TUIStyle.getMenuItemBackColor1(),
                       TUIStyle.getMenuItemBackColor2(), g);

        if (getModel().isArmed()) {
            g.setColor(TUIStyle.getMenuBorderColor());
            g.drawRect(2, 1, getWidth() - 4, getHeight() - 3);
            fillTransition(3, 2, getWidth() - 5, getHeight() - 4,
                           new Color(255, 242, 200), new Color(255, 215, 194),
                           g);
        }
        drawPic(g);
        int w = drawText(g);
        drawKey(g, w);
    }

    public void drawKey(Graphics g, int w) {
        if (getGlobalKey() == null)
            return;
        if (isEnabled())
            g.setColor(new Color(0, 0, 0));
        else
            g.setColor(new Color(141, 141, 141));
        g.setFont(TUIStyle.getMenuKeyFont());
        FontMetrics metrics = getFontMetrics(TUIStyle.getMenuKeyFont());
        int h = metrics.getHeight();
        int y = metrics.getAscent() + 2 + (getHeight() - h) / 2;
        int x = getWidth() - 68;
        if (x < w)
            x = w + 1;
        g.drawString(getGlobalKey(), x, y);
    }

    /**
     * 绘制文字
     * @param g Graphics
     * @return int
     */
    public int drawText(Graphics g) {
        int x = TUIStyle.getMenuItemIconSize() + 14;
        if (isEnabled())
            g.setColor(new Color(0, 0, 0));
        else
            g.setColor(new Color(141, 141, 141));
        FontMetrics metrics = getFontMetrics(getFont());
        int h = metrics.getHeight();
        int y = metrics.getAscent() + 2 + (getHeight() - h) / 2;
        int w = metrics.stringWidth(getText());
        g.setFont(getFont());
        g.drawString(getText(), x, y);
        if (getMnemonic() == 0)
            return x + w;
        String s = "" + (char) getMnemonic();
        int index = getText().indexOf(s);
        if (index == -1)
            return x + w;
        int x1 = x + metrics.stringWidth(getText().substring(0, index));
        int w1 = metrics.stringWidth(s);
        y += 1;
        g.drawLine(x1, y, x1 + w1 - 1, y);
        return x + w;
    }

    /**
     * 绘制图片
     * @param g Graphics
     */
    private void drawPic(Graphics g) {
        int picSize = TUIStyle.getMenuItemIconSize();
        if (pic == null)
            return;
        if (label == null)
            label = new JLabel();
        int y = (int) (((double) getHeight() - (double) picSize) / 2.0);
        label.setIcon(pic);
        label.setSize(picSize, picSize);
        label.setEnabled(isEnabled());
        label.paint(g.create(3, y, picSize, picSize));
    }

    /**
     * 绘制渐进
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color
     * @param c2 Color
     * @param g Graphics
     */
    public void fillTransition(int x, int y, int width, int height, Color c1,
                               Color c2, Graphics g) {
        for (int i = 0; i < width; i++) {
            double d = (double) i / (double) width;
            int R = (int) (c1.getRed() - (c1.getRed() - c2.getRed()) * d);
            int G = (int) (c1.getGreen() - (c1.getGreen() - c2.getGreen()) * d);
            int B = (int) (c1.getBlue() - (c1.getBlue() - c2.getBlue()) * d);
            g.setColor(new Color(R, G, B));
            g.fillRect(i + x, y, 1, height);
        }
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
        if("en".equals(language) && getEnText() != null && getEnText().length() > 0)
        {
            if(getMnemonic() > 0 && getEnText().toUpperCase().indexOf("" + (char)getMnemonic()) == -1)
                super.setText(getEnText() + "(" + (char)getMnemonic() + ")  ");
            else
                super.setText(getEnText() + "  ");
        }
        else if(getZhText() != null && getZhText().length() > 0)
            setText(getZhText());
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
