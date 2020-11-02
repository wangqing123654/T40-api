package com.dongyang.ui.base;

import javax.swing.JFrame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import java.awt.Component;
import java.awt.LayoutManager;
import com.dongyang.control.TControl;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import com.dongyang.ui.event.TWindowListener;
import com.dongyang.ui.base.TFocusTraversalPolicy;
import java.awt.Dimension;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TToolBar;
import com.dongyang.ui.TMenuBar;
import com.dongyang.ui.TSelectBlock;
import com.dongyang.ui.TFrame;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.event.TComponentListener;
import java.awt.event.ComponentEvent;
import java.util.Map;
import javax.swing.ButtonGroup;
import java.util.HashMap;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.util.TSystem;
import com.dongyang.jdo.MaxLoad;
import java.util.Date;

public class TFrameBase extends JFrame implements TComponent, TContainer {
    /**
     * �����б�
     */
    private ActionMessage actionList;
    /**
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * ��ǩ
     */
    private String tag;
    /**
     * ���ر�ǩ
     */
    private String loadtag;
    /**
     * ������
     */
    private TControl control;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * ���������
     */
    private JPanel workPanel;
    /**
     * ������
     */
    private TToolBar toolbar;
    /**
     * �Ƿ���д���
     */
    private boolean centerWindow = true;
    /**
     * ���㴦�����
     */
    private TFocusTraversalPolicy tFocus;
    /**
     * �������
     */
    private Object parameter;
    /**
     * Ȩ��
     */
    private Object popedem;
    /**
     * ����ֵ
     */
    private Object returnValue;
    /**
     * �����¼���������
     */
    private TWindowListener windowListenerObject;
    /**
     * �����������
     */
    private TComponentListener componentListenerObject;
    /**
     * ��ť������
     */
    private Map buttonGroupMap;
    /**
     * �˵���
     */
    private TMenuBar menuBar;
    /**
     * ������
     */
    private TToolBar toolBar;
    /**
     * �˵��б�
     */
    private String menuMap;
    /**
     * �˵��б�(Map)
     */
    private Map menuMapData = new HashMap();
    /**
     * �˵����
     */
    private String menuID;
    /**
     * ����ʾ
     */
    private boolean isOpenShow = true;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����
     */
    private String language;
    /**
     * ���ı���
     */
    private String zhTitle;
    /**
     * Ӣ�ı���
     */
    private String enTitle;
    /**
     * ���ɼ�����
     */
    private MaxLoad maxLoad;
    /**
     * ���ؿ���
     */
    private boolean loadFlg;

    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     */
    public TFrameBase() throws HeadlessException {
        super();
        uiInit();
    }

    /**
     * Creates a <code>Frame</code> in the specified
     * <code>GraphicsConfiguration</code> of
     * a screen device and a blank title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param gc the <code>GraphicsConfiguration</code> that is used
     * 		to construct the new <code>Frame</code>;
     * 		if <code>gc</code> is <code>null</code>, the system
     * 		default <code>GraphicsConfiguration</code> is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from
     * 		a screen device.  This exception is always thrown when
     *      GraphicsEnvironment.isHeadless() returns true.
     */
    public TFrameBase(GraphicsConfiguration gc) {
        super(gc);
        uiInit();
    }

    /**
     * Creates a new, initially invisible <code>Frame</code> with the
     * specified title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title for the frame
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     */
    public TFrameBase(String title) throws HeadlessException {
        super(title);
        uiInit();
    }

    /**
     * Creates a <code>JFrame</code> with the specified title and the
     * specified <code>GraphicsConfiguration</code> of a screen device.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title to be displayed in the
     * 		frame's border. A <code>null</code> value is treated as
     * 		an empty string, "".
     * @param gc the <code>GraphicsConfiguration</code> that is used
     * 		to construct the new <code>JFrame</code> with;
     *		if <code>gc</code> is <code>null</code>, the system
     * 		default <code>GraphicsConfiguration</code> is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from
     * 		a screen device.  This exception is always thrown when
     *      GraphicsEnvironment.isHeadless() returns true.
     */
    public TFrameBase(String title, GraphicsConfiguration gc) {
        super(title, gc);
        uiInit();
    }

    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        //��ʼ�������б�
        actionList = new ActionMessage();
        //����Tag
        setTag("UI");
        //�������������
        tFocus = new TFocusTraversalPolicy();
        //ȡ������
        getContentPane().setFocusable(false);
        //���ý��������
        setFocusTraversalPolicy(tFocus);
        //���ù������
        setWorkPanel(new JPanel());
        //���ò��ֹ�����
        getWorkPanel().setLayout(new BorderLayout());
        //���ع������
        getContentPane().add(getWorkPanel(), BorderLayout.CENTER);
        //���õ����رմ��ڸ���ʲôҲ����
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout("null");
        getWorkPanel().setBackground(TUIStyle.getFrameBackColor());
    }

    /**
     *
     * @return Container
     */
    @Deprecated
    public Container getContentPane() {
        return super.getContentPane();
    }

    /**
     * Returns the button's tag.
     * @return the buttons tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the button's tag.
     * @param tag the string used to set the tag
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
        if(width > 1024)
            width = 1024;
        this.setSize(width, getSize().height);
    }

    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height) {
        if(height > 748)
            height = 748;
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
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBackground(String color) {
        getWorkPanel().setBackground(StringTool.getColor(color, getConfigParm()));
    }

    /**
     * ���ý����б�
     * @param tags String
     */
    public void setFocusList(String tags) {
        tFocus.setFocusList(tags);
    }

    /**
     * �õ������б�
     * @return String
     */
    public String getFocusList() {
        return tFocus.getFocusList();
    }

    /**
     * ��һ������õ�����
     * @param tag String
     */
    public void afterFocus(String tag) {
        String afterTag = tFocus.getTagAfter(this, tag);
        if (afterTag == null)
            return;
        callMessage(afterTag + "|grabFocus");
    }

    /**
     * ǰһ������õ�����
     * @param tag String
     */
    public void beforeFocus(String tag) {
        String beforeTag = tFocus.getTagBefore(this, tag);
        if (beforeTag == null)
            return;
        callMessage(beforeTag + "|grabFocus");
    }

    /**
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null)
            control.setComponent(this);
    }

    /**
     * �õ����������
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * ���ù������
     * @param workPanel JPanel
     */
    protected void setWorkPanel(JPanel workPanel) {
        this.workPanel = workPanel;
    }

    /**
     * �õ��������
     * @return JPanel
     */
    protected JPanel getWorkPanel() {
        return workPanel;
    }

    /**
     * ���ù�����
     * @param toolbar TToolBar
     */
    public void setToolBar(TToolBar toolbar) {
        if (this.toolbar == toolbar)
            return;
        if (this.toolbar != null)
            getContentPane().remove(this.toolbar);
        this.toolbar = toolbar;
        if (toolbar != null)
            getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    /**
     * �õ�������
     * @return TToolBar
     */
    public TToolBar getToolBar() {
        return toolbar;
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
        return callEvent(eventName, new Object[] {parm},
                         new String[] {"java.lang.Object"});
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
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm) {
        if (configParm == null)
            return;
        //�������ö���
        setConfigParm(configParm);
        //����ȫ������
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
        //���ؿ�����
        if (getControl() != null)
            getControl().init();
    }
    /**
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    protected boolean filterInit(String message) {
        String value[] = StringTool.getHead(message, "=");
        if ("toolBar".equalsIgnoreCase(value[0]))
            return false;
        return true;
    }

    /**
     * ����˳��
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig,Layout,MenuConfig,MenuMap";
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
        Object value = null;
        if ((value = onBaseMessage(message, parm)) != null)
            return value;
        //�����Ӽ�����Ϣ
        if ((value = onTagBaseMessage(message, parm)) != null)
            return value;
        //������������Ϣ
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //������ֹ��Ϣ
        if ((value = onStopMessage(message, parm)) != null)
            return value;
        //��Ϣ�ϴ�
        return null;
    }

    /**
     * ������Ϣ<BR>
     * TWindowListener.WINDOW_CLOSING -> onClose -> dispose
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onStopMessage(String message, Object parm) {
        String value[] = StringTool.getHead(message, "->");
        if (value[0].equalsIgnoreCase(getTag()) &&
            value[1].equalsIgnoreCase(TWindowListener.WINDOW_CLOSING)) {
            callMessage("onExit");
            return "OK";
        }
        if ("onExit".equalsIgnoreCase(value[0])||"onClose".equalsIgnoreCase(value[0])) {
            onClosed();
            return "OK";
        }
        return null;
    }

    /**
     * �����Ӽ�����Ϣ
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
     * ����Tag����
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag) {
        if (tag == null || tag.length() == 0)
            return null;
        for (int i = 0; i < getWorkPanel().getComponentCount(); i++) {
            Component component = getWorkPanel().getComponent(i);
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
        //��ѯ�˵���
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null && menuBar instanceof TContainer) {
            TContainer container = (TContainer) menuBar;
            TComponent value = container.findObject(tag);
            if (value != null)
                return value;
        }
        //��ѯ������
        TToolBar toolbar = getToolBar();
        if(toolbar != null)
        {
            TContainer container = (TContainer) toolbar;
            TComponent value = container.findObject(tag);
            if (value != null)
                return value;
        }
        return null;
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
        if ("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
    }

    /**
     * ������Ŀ
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
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
     * �����������
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
     * ���ز˵�
     * @param value String
     */
    public void setMenuConfig(String value)
    {
        setMenuConfig(value,null);
    }
    /**
     * ���ز˵�
     * @param value String
     * @param parentComponent TComponent �������
     */
    public void setMenuConfig(String value,TComponent parentComponent) {
        if(value == null || value.length() == 0)
            return;
        if(parentComponent == null)
            parentComponent = this;
        menuBar = new TMenuBar();
        menuBar.setTag("MENI");
        menuBar.setParentComponent(parentComponent);
        menuBar.callMessage("setLoadTag|UI");
        //���ò˵���
        setJMenuBar(menuBar);
        //�Ƿ���ع�����
        if (StringTool.getBoolean(getConfigParm().getConfig().getString(
                getConfigParm().getSystemGroup(), getTag() + ".ToolBar"))) {
            toolBar = new TToolBar();
            toolBar.setParentComponent(this);
            menuBar.setToolBar(toolBar);
            setToolBar(toolBar);
        }
        //���ز˵������ļ�
        menuBar.init(getConfigParm().newConfig(value));
    }

    /**
     * ���ز��ֹ�����
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("��")) {
            getWorkPanel().setLayout(null);
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
        getWorkPanel().setLayout(layoutManager);
    }

    /**
     * ���������
     * @param value String
     */
    protected void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //���ID
        String cid = values[0];
        //�������
        String type = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), cid + ".type");
        if (type.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof Component))
            return;
        Component component = (Component) obj;
        if (component instanceof TComponent) {
            TComponent tComponent = (TComponent) component;
            tComponent.setTag(cid);
            tComponent.init(getConfigParm());
            tComponent.setParentComponent(this);
            if (StringTool.getBoolean(getConfigParm().getConfig().getString(
                    getConfigParm().getSystemGroup(), getTag() + ".editUI"))) {
                TSelectBlock tSelectBlock = new TSelectBlock();
                getWorkPanel().add(tSelectBlock);
                tSelectBlock.setTComponent(tComponent);
                tSelectBlock.update(false);
            }
        }

        if (values.length == 1)
            getWorkPanel().add(component);
        else if (values.length == 2)
            getWorkPanel().add(component, StringTool.layoutConstraint(values[1]));
    }
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        if(windowListenerObject == null)
        {
            //����Window�¼�
            windowListenerObject = new TWindowListener(this);
            addWindowListener(windowListenerObject);
        }
        if(componentListenerObject == null)
        {
            //��������¼�
            componentListenerObject = new TComponentListener(this);
            addComponentListener(componentListenerObject);
        }
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_HIDDEN,"onComponentHidden");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_MOVED,"onComponentMoved");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_RESIZED,"onComponentResized");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_SHOWN,"onComponentShown");
    }
    /**
     * ��������¼�
     * @param e ComponentEvent
     */
    public void onComponentHidden(ComponentEvent e)
    {

    }
    /**
     * ����ƶ��¼�
     * @param e ComponentEvent
     */
    public void onComponentMoved(ComponentEvent e)
    {

    }
    /**
     * ����ߴ�ı��¼�
     * @param e ComponentEvent
     */
    public void onComponentResized(ComponentEvent e)
    {
        int width = getWorkPanel().getWidth();
        int height = getWorkPanel().getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }
    /**
     * �����ʾ�¼�
     * @param e ComponentEvent
     */
    public void onComponentShown(ComponentEvent e)
    {
        int width = getWorkPanel().getWidth();
        int height = getWorkPanel().getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }

    /**
     * ��ʼ��
     */
    public void onInit() {
        //��ʼ�������¼�
        initListeners();
        //Date d1 = new Date();
        //����������
        if(isLoadFlg())
            startMaxLoad();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        //�����ڲ������ʼ��
        for (int i = 0; i < getWorkPanel().getComponentCount(); i++) {
            Component component = getWorkPanel().getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        //��ʼ���˵�
        onInitMenu();
        //ֹͣ������
        if(isLoadFlg())
            stopMaxLoad();
        //��ʼ��������
        if (getControl() != null)
            getControl().onInit();
        //Date d2 = new Date();
        //System.out.println("TFrameBase.onInit.time->" + (d2.getTime() - d1.getTime()) + " " + isLoadFlg());
        //��������
        changeLanguage((String)TSystem.getObject("Language"));
    }
    /**
     * ��ʼ���˵�
     */
    public void onInitMenu()
    {
        //��ʼ���˵���
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null && menuBar instanceof TComponent)
            ((TComponent) menuBar).callMessage("onInit");
        //��ʼ��������
        TToolBar toolBar = getToolBar();
        if (toolBar != null)
            toolBar.callMessage("onInit");
    }
    /**
     * �ر��¼�
     */
    public void onClosed() {
        Object obj = callFunction("onClosing");
        if(obj instanceof Boolean && !((Boolean)obj))
            return;
        dispose();
    }

    /**
     * ���¼����¼�
     */
    public void onReset() {
        setToolBar(null);
        setJMenuBar(null);
        //���ȫ�����
        getWorkPanel().removeAll();
        this.language = "";
        //���¼���
        getConfigParm().reset();
        init(getConfigParm());
        onInit();
        int width = getWorkPanel().getWidth();
        int height = getWorkPanel().getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
        validate();
        repaint();
    }

    /**
     * ����ʾ
     */
    public void open() {
        onInit();
        if(isOpenShow())
        {
            if (isCenterWindow())
                centerWindow();
            setVisible(true);
        }
    }

    /**
     * ���þ��д���
     * @param centerWindow boolean
     */
    public void setCenterWindow(boolean centerWindow) {
        this.centerWindow = centerWindow;
    }

    /**
     * �����Ƿ��Զ�����
     * @return boolean
     */
    public boolean isCenterWindow() {
        return centerWindow;
    }

    /**
     * ���д���
     */
    public void centerWindow() {
        //�õ���Ļ��ȣ��߶�
        Dimension dimension = getToolkit().getScreenSize();
        int screenWidth = dimension.width;
        int screenHeight = dimension.height;
        //�ô��ڿ�ȣ��߶�
        int thisWidth = getWidth();
        int thisHeight = getHeight();
        //������Ļ����
        int y = (screenHeight - thisHeight) / 2 - 20;
        if(y < 0)
            y = 0;
        setLocation((screenWidth - thisWidth) / 2,y);
    }
    /**
     * �����ʾ
     */
    public void showMaxWindow()
    {
        //�õ���Ļ��ȣ��߶�
        Dimension dimension = getToolkit().getScreenSize();
        int screenWidth = dimension.width;
        int screenHeight = dimension.height;
        setLocation(0,0);
        setSize(screenWidth,screenHeight - 30);
    }

    /**
     * ���ô������
     * @param parameter Object
     */
    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    /**
     * �õ��������
     * @return Object
     */
    public Object getParameter() {
        if(parameter == null)
            return "void";
        return parameter;
    }
    /**
     * ����Ȩ��
     * @param popedem Object
     */
    public void setPopedem(Object popedem)
    {
        this.popedem = popedem;
    }
    /**
     * �õ�Ȩ��
     * @return Object
     */
    public Object getPopedem()
    {
        if(popedem == null)
            return "void";
        return popedem;
    }

    /**
     * ���÷���ֵ
     * @param returnValue String
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * �õ�����ֵ
     * @return Object
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * �򿪴���
     * @param parm TConfigParm
     * @return TFrame
     */
    public static TFrame openWindow(TConfigParm parm) {
        return openWindow(parm, null);
    }

    /**
     * �򿪴���
     * @param parm TConfigParm
     * @param parameter Object �������
     * @return TFrame
     */
    public static TFrame openWindow(TConfigParm parm, Object parameter) {
        TFrame frame = new TFrame();
        frame.init(parm);
        if (parameter != null)
            frame.setParameter(parameter);
        frame.open();
        return frame;
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
     * �õ���ť��
     * @param name String ����
     * @return ButtonGroup
     */
    public ButtonGroup getButtonGroup(String name)
    {
        if(buttonGroupMap == null)
            buttonGroupMap = new HashMap();
        ButtonGroup buttonGroup = (ButtonGroup)buttonGroupMap.get(name);
        if(buttonGroup == null)
        {
            buttonGroup = new ButtonGroup();
            buttonGroupMap.put(name,buttonGroup);
        }
        return buttonGroup;
    }
    /**
     * ������ʾ
     * @param visible boolean true ��ʾ false ����ʾ
     */
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        if(visible)
        {
            Component com = tFocus.getDefaultComponent(this);
            if(com != null && com instanceof TComponent)
                ((TComponent)com).callFunction("grabFocus");
        }
    }
    /**
     * �����Ӳ˵�
     * @param tag String
     * @param menubar TMenuBar
     */
    public void setChildMenuBar(String tag,TMenuBar menubar)
    {
        if(menubar == null || menubar.getComponentCount() == 0)
        {
            setJMenuBar(this.menuBar);
            validate();
            this.menuBar.repaint();
        }
        else
        {
            setJMenuBar((JMenuBar) menubar);
            validate();
            menubar.repaint();
        }
    }
    /**
     * ɾ���Ӳ˵�
     */
    public void removeChildMenuBar()
    {
        setJMenuBar(menuBar);
        validate();
        menuBar.repaint();
    }
    /**
     * �����ӹ�����
     * @param tag String
     * @param toolbar TToolBar
     */
    public void setChildToolBar(String tag,TToolBar toolbar)
    {
        if(toolbar == null || toolbar.getComponentCount() == 0)
        {
            setToolBar(this.toolBar);
            validate();
            this.toolBar.repaint();
        }
        else
        {
            setToolBar(toolbar);
            //�¼�����
            exeAction(getChildToolBarAction());
            validate();
            toolbar.repaint();
        }
    }
    /**
     * ɾ���ӹ�����
     */
    public void removeChildToolBar()
    {
        setToolBar(toolBar);
        validate();
        toolbar.repaint();
    }
    /**
     * ���ò˵��б�
     * @param menuMap String
     */
    public void setMenuMap(String menuMap)
    {
        if(this.menuMap == menuMap)
            return;
        this.menuMap = menuMap;
        menuMapData = new HashMap();
        if(menuMap == null)
            return;
        String s[] = StringTool.parseLine(menuMap,";");
        for(int i = 0;i < s.length;i++)
        {
            String s1[] = StringTool.parseLine(s[i],":");
            if(s1.length < 2)
            {
                err("MenuMap �������� TAG:Filename;TAG:Filename");
                return;
            }
            String tag = s1[0];
            String fileName = s1[1];
            TMenuBar menuBar = loadMenu(fileName);
            menuMapData.put(tag,menuBar);
        }
    }
    /**
     * ���ز˵�
     * @param fileName String �ļ���
     * @return TMenuBar
     */
    public TMenuBar loadMenu(String fileName)
    {
        if(fileName == null)
            return null;
        TMenuBar menuBar = new TMenuBar();
        menuBar.setTag("MENI");
        menuBar.setParentComponent(this);
        menuBar.callMessage("setLoadTag|UI");
        TToolBar toolBar = new TToolBar();
        toolBar.setParentComponent(this);
        menuBar.setToolBar(toolBar);
        //���ز˵������ļ�
        menuBar.init(getConfigParm().newConfig(fileName));
        menuBar.onInit();
        toolBar.onInit();
        return menuBar;
    }
    /**
     * �õ��˵��б�
     * @return String
     */
    public String getMenuMap()
    {
        return menuMap;
    }
    /**
     * �õ��˵�����tag
     * @param tag String
     * @return TMenuBar
     */
    public TMenuBar getMenuForTag(String tag)
    {
        return (TMenuBar)menuMapData.get(tag);
    }
    /**
     * ���ò˵����
     * @param menuID String
     */
    public void setMenuID(String menuID)
    {
        if(menuID == null || menuID.length() == 0)
            return;
        if(this.menuID == menuID)
            return;
        TMenuBar newMenuBar = getMenuForTag(menuID);
        if(newMenuBar == null)
            return;
        this.menuID = menuID;
        menuBar = newMenuBar;
        toolBar = newMenuBar.getToolBar();
        //���ò˵���
        setJMenuBar(menuBar);
        //���ù�����
        setToolBar(toolBar);
    }
    /**
     * �õ��˵����
     * @return String
     */
    public String getMenuID()
    {
        return menuID;
    }
    /**
     * ���ô���ʾ
     * @param isOpenShow boolean
     */
    public void setOpenShow(boolean isOpenShow)
    {
        this.isOpenShow = isOpenShow;
    }
    /**
     * �Ƿ����ʾ
     * @return boolean
     */
    public boolean isOpenShow()
    {
        return isOpenShow;
    }
    /**
     * ִ�ж���
     * @param message String ��Ϣ
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
     * ���õ�������
     * @param action String
     */
    public void setChildToolBarAction(String action)
    {
        actionList.setAction("childToolBarAction",action);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getChildToolBarAction()
    {
        return actionList.getAction("childToolBarAction");
    }
    /**
     * ���ø������
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent) {
        this.parentComponent = parentComponent;
    }

    /**
     * �õ��������
     * @return TComponent
     */
    public TComponent getParentComponent() {
        return parentComponent;
    }
    /**
     * �ͷ�
     */
    public void release()
    {

    }
    /**
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        if(language == null || language.length() == 0)
        {
            String l = (String) TSystem.getObject("Language");
            if(l == null || l.length() == 0)
                return "zh";
            return l;
        }
        return language;
    }
    /**
     * �������ı���
     * @param zhTitle String
     */
    public void setZhTitle(String zhTitle)
    {
        this.zhTitle = zhTitle;
    }
    /**
     * �õ����ı���
     * @return String
     */
    public String getZhTitle()
    {
        return zhTitle;
    }
    /**
     * ����Ӣ�ı���
     * @param enTitle String
     */
    public void setEnTitle(String enTitle)
    {
        this.enTitle = enTitle;
    }
    /**
     * �õ�Ӣ�ı���
     * @return String
     */
    public String getEnTitle()
    {
        return enTitle;
    }
    /**
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if (language == null)
            return;
        if (language.equals(this.language))
            return;
        this.language = language;

        if("en".equals(language) && getEnTitle() != null && getEnTitle().length() > 0)
            setTitle(getEnTitle());
        else if(getZhTitle() != null && getZhTitle().length() > 0)
            setTitle(getZhTitle());

        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callFunction("changeLanguage",language);
            }
        }
        //��ʼ���˵���
        if (menuBar != null)
            menuBar.changeLanguage(language);
        //��ѯ������
        TToolBar toolbar = getToolBar();
        if(toolbar != null)
            toolbar.changeLanguage(language);
        if(getControl()!= null)
            getControl().onChangeLanguage(language);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * �������ɼ�����
     */
    public void startMaxLoad()
    {
        setMaxLoad(new MaxLoad());
    }
    /**
     * ֹͣ���ɼ�����
     */
    public void stopMaxLoad()
    {
        getMaxLoad().run();
        setMaxLoad(null);
    }
    /**
     * ���ü��ɼ�����
     * @param maxLoad MaxLoad
     */
    public void setMaxLoad(MaxLoad maxLoad)
    {
        this.maxLoad = maxLoad;
    }
    /**
     * �õ��̳м�����
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        return maxLoad;
    }
    /**
     * ���ü��ؿ���
     * @param loadFlg boolean
     */
    public void setLoadFlg(boolean loadFlg)
    {
        this.loadFlg = loadFlg;
    }
    /**
     * ���ؿ����Ƿ�����
     * @return boolean
     */
    public boolean isLoadFlg()
    {
        return loadFlg;
    }
    
    /**
     * ����ֵ
     * @param parm
     * @param parameter
     * @param flg   true
     * @return
     */
    public static Object openWindow(TConfigParm parm, Object parameter,boolean flg) {
        TFrame frame = new TFrame();
        frame.init(parm);
        if (parameter != null)
            frame.setParameter(parameter);
        frame.open();
        return frame.getReturnValue();
    }
}

