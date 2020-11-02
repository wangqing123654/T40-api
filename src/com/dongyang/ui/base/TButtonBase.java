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
 * <p>Title: TButton������</p>
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
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * ��ǩ
     */
    private String tag = "";
    /**
     * ���ر�ǩ
     */
    private String loadtag;
    /**
     * ��������
     */
    private String controlClassName;
    /**
     * ������
     */
    private TControl control;
    /**
     * ������Ϣ
     */
    private String actionMessage;
    /**
     * ��ݼ�
     */
    private String key;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * ����¼���������
     */
    TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    TMouseMotionListener mouseMotionListenerObject;
    /**
     * ������������
     */
    TActionListener actionListenerObject;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ѡ�з�ʽ
     */
    private boolean selectedMode;
    /**
     * ������ʾ
     */
    private String zhText;
    /**
     * Ӣ����ʾ
     */
    private String enText;
    /**
     * ����Tip
     */
    private String zhTip;
    /**
     * Ӣ��Tip
     */
    private String enTip;
    /**
     * ����
     */
    private Font tfont;
    /**
     * �Զ�ͼ��ߴ�
     */
    private boolean isAutoIconSize;
    /**
     * ͼƬ����
     */
    private String pictureName;
    /**
     * ���
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
     * �ڲ���ʼ��UI
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
     * ���ü��ر�ǩ
     * @param loadtag String
     */
    public void setLoadTag(String loadtag) {
        this.loadtag = loadtag;
    }

    /**
     * �õ����ر�ǩ
     * @return String
     */
    public String getLoadTag() {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }

    /**
     * ����X����
     * @param x int
     */
    public void setX(int x) {
        this.setLocation(x, getLocation().y);
    }

    /**
     * ����Y����
     * @param y int
     */
    public void setY(int y) {
        this.setLocation(getLocation().x, y);
    }

    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width) {
        this.setSize(width, getSize().height);
    }

    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height) {
        this.setSize(getSize().width, height);
    }
    /**
     * X����ƫ��
     * @param d int
     */
    public void setX$(int d)
    {
        if(d == 0)
            return;
        setX(getX() + d);
    }
    /**
     * Y����ƫ��
     * @param d int
     */
    public void setY$(int d)
    {
        if(d == 0)
            return;
        setY(getY() + d);
    }
    /**
     * ��������
     * @param d int
     */
    public void setX_$(int d)
    {
        setX$(d);
        setWidth$(-d);
    }
    /**
     * ��������
     * @param d int
     */
    public void setY_$(int d)
    {
        setY$(d);
        setHeight$(-d);
    }
    /**
     * �������ƫ��
     * @param d int
     */
    public void setWidth$(int d)
    {
        if(d == 0)
            return;
        setWidth(getWidth() + d);
    }
    /**
     * �߶�����ƫ��
     * @param d int
     */
    public void setHeight$(int d)
    {
        if(d == 0)
            return;
        setHeight(getHeight() + d);
    }

    /**
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null) {
            control.setComponent(this);
        }
    }

    /**
     * �õ����������
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * ���ö�����Ϣ
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }

    /**
     * �������ò�������
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }

    /**
     * �õ����ò�������
     * @return TConfigParm
     */
    public TConfigParm getConfigParm() {
        return configParm;
    }

    /**
     * ��ȫ�ֿ�ݼ�
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
     * ��ȫ�ֿ�ݼ�
     * @param key String "CTRL+F1"
     */
    public void setGlobalKey(String key) {
        this.key = key;
        setGlobalKey(StringTool.getKey(key), StringTool.getControlKey(key));
    }

    /**
     * �õ�ȫ�ֿ�ݼ�
     * @return String
     */
    public String getGlobalKey() {
        return key;
    }

    /**
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param methodName String ������
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},new String[] {"java.lang.Object"});
    }
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        //��������¼�
        if(mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //��������ƶ��¼�
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
        //����Mouse�¼�
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,"onMouseReleased");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,"onMouseEntered");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,"onMouseExited");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
        addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
    }
    /**
     * ������
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        if(getCursorType() != 0)
            setCursor(new Cursor(getCursorType()));
    }
    /**
     * ����뿪
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        if(getCursorType() != 0)
            setCursor(new Cursor(0));
    }
    /**
     * ����϶�
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
    }
    /**
     * ����ƶ�
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }
    /**
     * �����¼�
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
     * ��ʼ��
     */
    public void onInit() {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        //��ʼ��������
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
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message) {
        return true;
    }

    /**
     * ����˳��
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig";
    }
    /**
     * ���з���
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters)
    {
        return callMessage(message,parameters);
    }

    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * ������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //���������Ϣ
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //������������Ϣ
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //��Ϣ�ϴ�
        TComponent parentTComponent = getParentComponent();
        if(parentTComponent == null)
            parentTComponent = getParentTComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        //����Ĭ�϶���
        value = onDefaultActionMessage(message, parm);
        if (value != null)
            return value;
        return null;
    }

    /**
     * ִ��Ĭ�϶���
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
     * ���Ͷ�����Ϣ
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
     * �õ�����
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * �õ�����(�ݹ���)
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
     * �õ�ͬ���ķ���
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
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message, "=");
        //����������������
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }

    /**
     * ����������������
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
     * �����������
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
     * ���������������
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
     * �õ������������
     * @return String
     */
    public String getControlClassName()
    {
        return controlClassName;
    }
    /**
     * �õ��Լ�����
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * �������
     * @param tag String ��ǩ
     * @return TComponent
     */
    public TComponent findTComponent(String tag)
    {
        return (TComponent)callMessage(tag + "|getThis");
    }
    /**
     * ���ø������
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent)
    {
        this.parentComponent = parentComponent;
    }
    /**
     * �õ��������
     * @return TComponent
     */
    public TComponent getParentComponent()
    {
        return parentComponent;
    }
    /**
     * ����ѡ��ģʽ
     * @param b boolean
     */
    public void setSelectedMode(boolean b)
    {
        selectedMode = b;
    }
    /**
     * �õ�ѡ��ģʽ
     * @return boolean
     */
    public boolean isSelectedMode()
    {
        return selectedMode;
    }
    /**
     * �ͷ�
     */
    public void release()
    {

    }
    /**
     * ����������ʾ
     * @param zhText String
     */
    public void setZhText(String zhText)
    {
        this.zhText = zhText;
    }
    /**
     * �õ�������ʾ
     * @return String
     */
    public String getZhText()
    {
        return zhText;
    }
    /**
     * ����Ӣ����ʾ
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * �õ�Ӣ����ʾ
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }
    /**
     * ��������Tip
     * @param zhTip String
     */
    public void setZhTip(String zhTip)
    {
        this.zhTip = zhTip;
    }
    /**
     * �õ�����Tip
     * @return String
     */
    public String getZhTip()
    {
        return zhTip;
    }
    /**
     * ����Ӣ��Tip
     * @param enTip String
     */
    public void setEnTip(String enTip)
    {
        this.enTip = enTip;
    }
    /**
     * �õ�Ӣ��Tip
     * @return String
     */
    public String getEnTip()
    {
        return enTip;
    }
    /**
     * ��������
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
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * ����ͼ������
     * @param name String ͼ������
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
     * �����Զ�ͼ��ߴ�
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
     * �Ƿ��Զ�ͼ��ߴ�
     * @return boolean
     */
    public boolean isAutoIconSize()
    {
        return isAutoIconSize;
    }
    /**
     * ��������
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * �õ�����
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * �õ�����
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
     * ��������
     * @param name String
     */
    public void setFontName(String name)
    {
        Font f = getTFont();
        setTFont(new Font(name,f.getStyle(),f.getSize()));
    }
    /**
     * �õ�����
     * @return String
     */
    public String getFontName()
    {
        return getTFont().getFontName();
    }
    /**
     * ��������ߴ�
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * �õ�����ߴ�
     * @return int
     */
    public int getFontSize()
    {
        return getTFont().getSize();
    }
    /**
     * ������������
     * @param style int
     */
    public void setFontStyle(int style)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),style,f.getSize()));
    }
    /**
     * �õ���������
     * @return int
     */
    public int getFontStyle()
    {
        return getTFont().getStyle();
    }
    /**
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBKColor(String color) {
        if(color == null || color.length() == 0)
            return;
        setBackground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * ����������ɫ
     * @param color String
     */
    public void setColor(String color)
    {
        if(color == null || color.length() == 0)
            return;
        this.setForeground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * ���ñ߿�
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }
    /**
     * ���ð�ťͼƬ
     * @param name ͼƬ����
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
     * �õ���ťͼƬ
     * @return ͼƬ����
     */
    public String getPictureName()
    {
        return pictureName;
    }
    /**
     * ����ͼƬ
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
            err("û���ҵ�ͼ��" + path);
        }
        return icon;
    }
    /**
     * ���ù��
     * @param cursorType int
     */
    public void setCursorType(int cursorType)
    {
        this.cursorType = cursorType;
    }
    /**
     * �õ����
     * @return int
     */
    public int getCursorType()
    {
        return cursorType;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TButtonBase";
    }
}
