package com.dongyang.ui.base;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfig;
import com.dongyang.config.TConfigParm;
import java.awt.Component;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.control.TControl;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.event.TComponentListener;
import java.awt.event.ComponentEvent;
import javax.swing.ButtonGroup;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import com.dongyang.ui.TPanel;
import com.dongyang.ui.TMenuBar;
import com.dongyang.ui.TToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import com.dongyang.ui.TTabbedPane;
import java.awt.Insets;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.control.TDrawControl;
import java.awt.Cursor;
import java.awt.Point;
import com.dongyang.util.TSystem;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import com.dongyang.jdo.MaxLoad;
import java.util.Date;
import com.dongyang.ui.TRootPanel;

public class TPanelBase extends JPanel implements TComponent, TContainer {
    /**
     * �����ƶ�
     */
    public static final int NO_MOVE = 0;
    /**
     * ���Һ����ƶ�
     */
    public static final int WIDTH_MOVE = 1;
    /**
     * ���������ƶ�
     */
    public static final int HEIGHT_MOVE = 2;
    /**
     * �����ƶ�
     */
    public static final int MOVE = 3;
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
    private String loadtag ;
    /**
     * �ı�
     */
    private String text = "";
    /**
     * ������
     */
    private TControl control;
    /**
     * ��ͼ������
     */
    private TDrawControl drawControl;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * ͸����
     */
    private float transparence = 1.0f;
    /**
     * �������
     */
    private Object parameter;
    /**
     * Ȩ��
     */
    private Object popedem;
    /**
     * ��������
     */
    private String title;
    /**
     * �˵�λ���ļ���
     */
    private String menuConfig;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����¼���������
     */
    TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    TMouseMotionListener mouseMotionListenerObject;
    /**
     * �Զ��ߴ�
     */
    private int autoSize = 5;
    /**
     * �Զ��ߴ������߾���
     */
    private int autoHSize = 0;
    /**
     * �Զ�X
     */
    private boolean autoX;
    /**
     * �Զ�Y
     */
    private boolean autoY;
    /**
     * �Զ����
     */
    private boolean autoWidth;
    /**
     * �Զ��߶�
     */
    private boolean autoHeight;
    /**
     * �Զ�H
     */
    private boolean autoH;
    /**
     * �Զ�W
     */
    private boolean autoW;
    /**
     * ���㴦�����
     */
    private TFocusTraversalPolicy tFocus;
    /**
     * �����������
     */
    private TComponentListener componentListenerObject;
    /**
     * ��ť������
     */
    private Map buttonGroupMap;
    /**
     * ��ť��������Ч true ��Ч false ��Ч
     */
    private boolean buttonGroupFlg = true;
    /**
     * ���������
     */
    private JPanel workPanel;
    /**
     * �������
     */
    private JPanel titlePanel;
    /**
     * ��ʾ����
     */
    private boolean showTitle;
    /**
     * ��ʾ�˵�
     */
    private boolean showMenu;
    /**
     * �˵�
     */
    private TMenuBar menuBar;
    /**
     * ������
     */
    private TToolBar toolBar;
    /**
     * ��ʾ������
     */
    private boolean showToolBar;
    /**
     * �Ƿ��ڴ��ڸ��ǲ˵�
     */
    private boolean topMenu;
    /**
     * �Ƿ��ڴ��ڸ��ǹ�����
     */
    private boolean topToolBar;
    /**
     * ���ز˵��ϴ�
     */
    private boolean refetchTopMenu;
    /**
     * ���ع������ϴ�
     */
    private boolean refetchToolBar;
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
     * ���ڼ�������
     */
    private boolean initNow;
    /**
     * �����б�
     */
    public ActionMessage actionList;
    /**
     * �Ƿ��϶���
     */
    private boolean isMove;
    /**
     * �ƶ�����
     */
    private int moveType = NO_MOVE;
    /**
     * ����Xλ��
     */
    private int oldX;
    /**
     * ����Yλ��
     */
    private int oldY;
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
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     *
     * @param layout  the LayoutManager to use
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public TPanelBase(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        uiInit();
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout  the LayoutManager to use
     */
    public TPanelBase(LayoutManager layout) {
        this(layout, true);
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
     * and the specified buffering strategy.
     * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
     * will use a double buffer.
     *
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public TPanelBase(boolean isDoubleBuffered) {
        this(new FlowLayout(), isDoubleBuffered);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public TPanelBase() {
        this(false);
        //System.out.println(this.getUI().getClass().getName());
        //javax.swing.plaf.basic.BasicPanelUI a;
        //System.out.println(this.isOpaque());
        //setOpaque(false);
    }
    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        setLayout("null");
        setBackground(TUIStyle.getPanelBackColor());
        tFocus = new TFocusTraversalPolicy();
        setFocusable(false);
        setFocusTraversalPolicy(tFocus);
        actionList = new ActionMessage();
        //this.setMaximumSize(new Dimension(1024,748));
    }
    public void setOpaque(boolean isOpaque)
    {
        super.setOpaque(isOpaque);
        repaint();
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
     * Returns the button's text.
     * @return the buttons text
     * @see #setText
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the button's text.
     * @param text the string used to set the text
     * @see #getText
     *  description: The button's text.
     */
    public void setText(String text) {
        this.text = text;
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
     * ����͸����
     * @param transparence float
     */
    public void setTransparence(float transparence) {
        this.transparence = transparence;
    }

    /**
     * �õ�͸����
     * @return float
     */
    public float getTransparence() {
        return transparence;
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
        return popedem;
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
     * ���ñ߿�
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
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
     * ���û�ͼ������
     * @param drawControl TDrawControl
     */
    public void setDrawControl(TDrawControl drawControl)
    {
        this.drawControl = drawControl;
        if(drawControl != null)
            drawControl.setParent(this);
    }
    /**
     * �õ���ͼ������
     * @return TDrawControl
     */
    public TDrawControl getDrawControl()
    {
        return drawControl;
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
        if(getBaseEventObject() == null)
            return;
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
        dynamicInit();
    }
    /**
     * ��̬����
     */
    private void dynamicInit()
    {
        initNow = true;
        //����ȫ������
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
        {
            //    if (filterInit(value[index]))
            callMessage(value[index], configParm);
        }
        //���ؿ�����
        if (getControl() != null)
            getControl().init();
        initNow = false;
    }
    /**
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message) {
        //String value[] = StringTool.getHead(message, "=");
        return true;
    }

    /**
     * ����˳��
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,DrawControlClassName,ControlConfig,Layout,toolBar,MenuConfig,MenuMap,ShowMenu,Border";
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
        //�����Ӽ�����Ϣ
        value = onTagBaseMessage(message, parm);
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
        return null;
    }

    /**
     * �õ��������
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * �õ��������<BR>
     * (���ڵݹ���ò��Ҹ������.ע:�ڲ�ʹ��)
     * @param container Container ��������
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
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
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
        //��ʼ���˵���
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
     * �õ��˵���
     * @return TMenuBar
     */
    public TMenuBar getMenuBar()
    {
        return menuBar;
    }
    /**
     * �õ�������
     * @return TToolBar
     */
    public TToolBar getToolBar()
    {
        return toolBar;
    }
    public void paint(Graphics g) {
        if(getDrawControl() != null)
            getDrawControl().paintBackground(g);
        if(getTransparence() != 1.0f)
        {
            BufferedImage bufimg = new BufferedImage(
                    getWidth(),
                    getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufimg.createGraphics();
            g2.setColor(g.getColor());
            super.paint(g2);
            Graphics2D gx = (Graphics2D) g;
            gx.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, getTransparence()));
            gx.drawImage(bufimg, 0, 0, null);
        }else
            super.paint(g);
        if(getDrawControl() != null)
            getDrawControl().paintForeground(g);
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
        if ("ToolBar".equalsIgnoreCase(value[0]))
            value[0] = "ShowToolBar";
    }

    /**
     * ������Ŀ
     * @param message String
     * @return Object
     */
    public Object setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
        return "OK";
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
     * ������ʾ�������
     * @param value String
     */
    public void setDrawControlClassName(String value)
    {
        if(value == null || value.length() == 0)
        {
            setDrawControl(null);
            return;
        }
        Object obj = getConfigParm().loadObject(value);
        if(obj == null)
        {
            setDrawControl(null);
            err("Class loadObject err className=" + value);
            return;
        }
        if(!(obj instanceof TDrawControl)){
            setDrawControl(null);
            err("class loadObject type err className=" + value + "is not TDrawControl");
            return;
        }
        TDrawControl control = (TDrawControl)obj;
        setDrawControl(control);
    }
    /**
     * ���ز��ֹ�����
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("��")) {
            if(getWorkPanel() != null)
                getWorkPanel().setLayout((LayoutManager)null);
            else
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
        if(getWorkPanel() != null)
            getWorkPanel().setLayout(layoutManager);
        else
            setLayout(layoutManager);
    }
    /**
     * ���س�Ա���
     * @param value String
     */
    protected void createItem(final String value)
    {
        //new Thread(){
        //    public void run()
        //    {
                dynamicCreateItem(value);
        //    }
        //}.start();
    }
    /**
     * ���س�Ա���
     * @param value String
     */
    protected synchronized void dynamicCreateItem(String value) {
        if(value == null || value.length() == 0)
            return;
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
        }
        if (values.length == 1)
        {
            if (getWorkPanel() != null)
                getWorkPanel().add(component);
            else
                add(component);
        }
        else if (values.length == 2)
        {
            if(getWorkPanel() != null)
                getWorkPanel().add(component, StringTool.layoutConstraint(values[1]));
            else
                add(component, StringTool.layoutConstraint(values[1]));
        }
        //���ӳߴ�ͬ������
        if (component instanceof TComponent)
        {
            ((TComponent)component).callFunction("onParentResize",getWidth(),getHeight() - getWorkPanelY());
            callFunction("addEventListener",
                         getTag() + "->" + TComponentListener.RESIZED,
                         component, "onParentResize");
        }
    }
    /**
     * ������Ŀ
     * @param com Object
     */
    public void addItem(Object com)
    {
        if(com == null)
            return;
        if(!(com instanceof Component))
            return;
        if(getWorkPanel() != null)
            getWorkPanel().add((Component)com);
        else
            add((Component)com);
    }
    /**
     * ������Ŀ
     * @param com Object
     * @param index int
     */
    public void addItem(Object com,int index)
    {
        if(com == null)
            return;
        if(!(com instanceof TComponent))
            return;
        if(getWorkPanel() != null)
            getWorkPanel().add((Component)com,index);
        else
            add((Component)com,index);
    }
    /**
     * �ͷż����¼�
     */
    public void releaseListeners()
    {
        //�ͷ�������
        removeMouseListener(mouseListenerObject);
        //�ͷ�����ƶ�����
        removeMouseMotionListener(mouseMotionListenerObject);
        //�ͷ�����¼�
        removeComponentListener(componentListenerObject);

        getBaseEventObject().release();

        TComponent component = getParentComponent();
        if(component != null)
            component.callFunction("removeEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
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
        if(componentListenerObject == null)
        {
            //��������¼�
            componentListenerObject = new TComponentListener(this);
            addComponentListener(componentListenerObject);
        }
        //��������¼�
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_HIDDEN,"onComponentHidden");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_MOVED,"onComponentMoved");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_RESIZED,"onComponentResized");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_SHOWN,"onComponentShown");
        //����Mouse�¼�
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,"onMouseReleased");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,"onMouseEntered");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,"onMouseExited");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
        TComponent component = getParentComponent();
        if(component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
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
        int width = getWidth();
        int height = getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }
    /**
     * �����ʾ�¼�
     * @param e ComponentEvent
     */
    public void onComponentShown(ComponentEvent e)
    {
        int width = getWidth();
        int height = getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
    }
    /**
     * �������ߴ�ͬ��
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
     * �������ߴ�ı�
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
            setX((w == 0?height:w) - insets.right - getAutoSize() - getWidth());
        }
        if(isAutoH())
        {
            int h = c.getHeight();
            setY((h == 0?height:h) - insets.bottom - getAutoSize() - getHeight());
        }
        if(!isAutoW() && isAutoX())
            setX(insets.left + getAutoSize());
        if(!isAutoH() && isAutoY())
            setY(insets.top + getAutoSize());
        if(!isAutoW() && isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize());
        }
        if(!isAutoH() && isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize() - getAutoHSize());
        }
    }
    /**
     * �����ƶ�����
     * @param moveType int
     */
    public void setMoveType(int moveType)
    {
        this.moveType = moveType;
    }
    /**
     * �õ��ƶ�����
     * @return int
     */
    public int getMoveType()
    {
        return moveType;
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        if(e.getButton() != 1)
            return;
        if(e.getClickCount() == 2)
            return;
        if(getMoveType() == NO_MOVE)
            return;
        oldX = e.getX();
        oldY = e.getY();
        isMove = true;
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        isMove = false;
    }
    /**
     * ������
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
    }
    /**
     * ����뿪
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
    }
    /**
     * ����϶�
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        if(!isMove)
            return;
        int x = 0;
        int y = 0;
        switch(getMoveType())
        {
        case WIDTH_MOVE:
            x += e.getX() - oldX;
            break;
        case HEIGHT_MOVE:
            y += e.getY() - oldY;
            break;
        case MOVE:
            x += e.getX() - oldX;
            y += e.getY() - oldY;
            break;
        }
        Point point = new Point(x,y);
        setLocation(point.x + getX(),point.y + getY());
    }
    /**
     * ����ƶ�
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }

    /**
     * ��ʼ��
     */
    public void onInit() {
        //��ʼ�������¼�
        initListeners();
        //����������
        if(isLoadFlg())
            startMaxLoad();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
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
        if (getControl() != null)
            getControl().onInit();
        showTopMenu();
    }
    /**
     * ��ʼ���˵�
     */
    public void onInitMenu()
    {
        //��ʼ���˵���
        if (menuBar != null)
            menuBar.onInit();
        //��ʼ��������
        if (toolBar != null)
            toolBar.onInit();
    }
    /**
     * ��ʾ�����˵�����
     */
    public void showTopMenu()
    {
        if(isTopMenu())
            setTopMenu(true);
        if(isTopToolBar())
            setTopToolBar(true);
    }
    /**
     * ���ñ�������
     * @param title String
     */
    public void setTitle(String title)
    {
        this.title = title;
        if(title == null || title.length() == 0)
            return;
        TTabbedPane tabbed = null;
        if(getParentComponent() == null)
        {
            TComponent com = getParentTComponent();
            if(com == null || !(com instanceof TTabbedPane))
                return;
            tabbed = (TTabbedPane)com;
        }
        else if(getParentComponent() instanceof TTabbedPane)
            tabbed = (TTabbedPane)getParentComponent();
        else
            return;
        if(tabbed != null)
            tabbed.setTitleAt(getTag(),title);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getTitle()
    {
        return title;
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
     * ���ò˵������ļ���
     * @param menuConfig String
     */
    public void setMenuConfig(String menuConfig)
    {
        this.menuConfig = menuConfig;
        if (menuConfig == null && menuConfig.length() == 0)
        {
            menuBar = null;
            toolBar = null;
            return;
        }
        menuBar = new TMenuBar();
        menuBar.setTag("MENI");
        menuBar.setParentComponent(this);
        menuBar.callMessage("setLoadTag|UI");
        toolBar = new TToolBar();
        toolBar.setParentComponent(this);
        menuBar.setToolBar(toolBar);
        //���ز˵������ļ�
        menuBar.init(getConfigParm().newConfig(menuConfig));
        if (getTitlePanel() != null)
        {
            if (menuBar != null && isShowMenu())
                getTitlePanel().add(menuBar, BorderLayout.CENTER);
            if (toolBar != null && isShowToolBar())
                getTitlePanel().add(toolBar, BorderLayout.SOUTH);
            updateUI();
            repaint();
        }
    }
    /**
     * �õ��˵������ļ���
     * @return String
     */
    public String getMenuConfig()
    {
        return menuConfig;
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
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBKColor(String color) {
        if(color == null || color.length() == 0)
            return;
        setBackground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * �����Զ�X
     * @param autoX boolean
     */
    public void setAutoX(boolean autoX)
    {
        this.autoX = autoX;
        onParentResize();
    }
    /**
     * �Ƿ����Զ�X
     * @return boolean
     */
    public boolean isAutoX()
    {
        return autoX;
    }
    /**
     * �����Զ�Y
     * @param autoY boolean
     */
    public void setAutoY(boolean autoY)
    {
        this.autoY = autoY;
        onParentResize();
    }
    /**
     * �Ƿ����Զ�Y
     * @return boolean
     */
    public boolean isAutoY()
    {
        return autoY;
    }
    /**
     * �����Զ����
     * @param autoWidth boolean
     */
    public void setAutoWidth(boolean autoWidth)
    {
        this.autoWidth = autoWidth;
        onParentResize();
    }
    /**
     * �Ƿ����Զ����
     * @return boolean
     */
    public boolean isAutoWidth()
    {
        return autoWidth;
    }
    /**
     * �����Զ��߶�
     * @param autoHeight boolean
     */
    public void setAutoHeight(boolean autoHeight)
    {
        this.autoHeight = autoHeight;
        onParentResize();
    }
    /**
     * �Ƿ����Զ��߶�
     * @return boolean
     */
    public boolean isAutoHeight()
    {
        return autoHeight;
    }
    /**
     * �����Զ�H
     * @param autoH boolean
     */
    public void setAutoH(boolean autoH)
    {
        this.autoH = autoH;
    }
    /**
     * �Ƿ����Զ�H
     * @return boolean
     */
    public boolean isAutoH()
    {
        return autoH;
    }
    /**
     * �����Զ�H
     * @param autoW boolean
     */
    public void setAutoW(boolean autoW)
    {
        this.autoW = autoW;
    }
    /**
     * �Ƿ����Զ�W
     * @return boolean
     */
    public boolean isAutoW()
    {
        return autoW;
    }
    /**
     * �����Զ��ߴ�
     * @param autoSize int
     */
    public void setAutoSize(int autoSize)
    {
        this.autoSize = autoSize;
        onParentResize();
    }
    /**
     * �õ��Զ��ߴ�
     * @return int
     */
    public int getAutoSize()
    {
        return autoSize;
    }
    /**
     * �����Զ��ߴ������߳ߴ�
     * @param autoHSize int
     */
    public void setAutoHSize(int autoHSize)
    {
        this.autoHSize = autoHSize;
        onParentResize();
    }
    /**
     * �õ��Զ��ߴ������߳ߴ�
     * @return int
     */
    public int getAutoHSize()
    {
        return autoHSize;
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
     * @return Object
     */
    public Object afterFocus(String tag) {
        String afterTag = tFocus.getTagAfter(this, tag);
        if (afterTag == null)
            return null;
        callMessage(afterTag + "|grabFocus");
        return "OK";
    }
    /**
     * ǰһ������õ�����
     * @param tag String
     * @return Object
     */
    public Object beforeFocus(String tag) {
        String beforeTag = tFocus.getTagBefore(this, tag);
        if (beforeTag == null)
            return null;
        callMessage(beforeTag + "|grabFocus");
        return "OK";
    }
    /**
     * �õ���ť��
     * @param name String ����
     * @return ButtonGroup
     */
    public ButtonGroup getButtonGroup(String name)
    {
        if(!isButtonGroupFlg())
            return null;
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
     * ���ð�ť�������Ƿ���Ч
     * @param buttonGroupFlg boolean true ��Ч false ��Ч
     */
    public void setButtonGroupFlg(boolean buttonGroupFlg)
    {
        this.buttonGroupFlg = buttonGroupFlg;
    }
    /**
     * ��ť�������Ƿ���Ч
     * @return boolean true ��Ч false ��Ч
     */
    public boolean isButtonGroupFlg()
    {
        return buttonGroupFlg;
    }
    /**
     * ���س�Ա���
     * @param tag String ���ӳ�Ա�ı�ǩ
     * @param config String ���ӳ�Ա�������ļ�
     */
    public void addItem(String tag,String config)
    {
        addItem(tag,config,null);
    }
    /**
     * ��ʾ��Ա
     * @param tag String
     * @return boolean
     */
    public boolean showItem(String tag)
    {
        return showItem(tag,null);
    }
    /**
     * ��ʾ��Ա
     * @param tag String
     * @param parm Object
     * @return boolean
     */
    public boolean showItem(String tag,Object parm)
    {
        TComponent com = findItem(tag);
        if(com == null)
            return false;
        if(parm != null && com instanceof TPanel)
        {
            TPanel panel =(TPanel)com;
            panel.setParameter(parm);
            if(panel.getControl()!= null)
                panel.getControl().onInitReset();
        }
        showItem(com);
        return true;
    }
    /**
     * ���س�Ա���
     * @param tag String ���ӳ�Ա�ı�ǩ
     * @param config String ���ӳ�Ա�������ļ�
     * @param parm Object ����
     */
    public void addItem(String tag,String config,Object parm)
    {
        addItem(tag,config,parm,true);
    }
    /**
     * ���س�Ա���
     * @param tag String
     * @param config String
     * @param parm Object
     * @param isDynamic boolean
     */
    public void addItem(final String tag,final String config,final Object parm,boolean isDynamic)
    {
        if(isDynamic)
            new Thread(){
                public void run()
                {
                    dynamicAddItem(tag,config,parm);
                }
            }.start();
        else
            dynamicAddItem(tag,config,parm);
    }
    /**
     * ��̬��Ա�������
     * @param tag String
     * @param config String
     * @param parm Object
     */
    private synchronized void dynamicAddItem(String tag,String config,Object parm) {
        if(tag == null || tag.length() == 0)
            return;
        if(config == null || config.length() == 0)
            return;
        if(showItem(tag,parm))
            return;

        TConfigParm configParm = getConfigParm().newConfig(config,false);

        if(!configParm.getConfig().isInit())
        {
            err("��ʼ��" + config + "ʧ��!");
            return;
        }
        //�������
        String type = configParm.getConfig().getType();
        if(type.equalsIgnoreCase("TFrame"))
            type = "TPanel";
        if(type == null || type.length() == 0)
        {
            err("��ʼ��" + config + "����[" + type + "]ʧ��!");
            return;
        }
        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof Component))
            return;
        Component component = (Component) obj;
        if (component instanceof TComponent) {
            TComponent tComponent = (TComponent) component;
            tComponent.setTag(tag);
            tComponent.callMessage("setLoadTag|UI");
            //����
            if(parm != null && tComponent instanceof TPanelBase)
                ((TPanelBase)tComponent).setParameter(parm);
            tComponent.init(configParm);
        }
        if(getWorkPanel() != null)
            getWorkPanel().add(component,0);
        else
            add(component,0);
        if (component instanceof TComponent)
        {
            TComponent tComponent = (TComponent) component;
            tComponent.setParentComponent(this);
            tComponent.callFunction("onInit");
            tComponent.callFunction("onParentResize",getWidth(),getHeight() - getWorkPanelY());
            tComponent.callFunction("changeLanguage",(String)TSystem.getObject("Language"));
        }
        if(component instanceof TPanel)
            ((TPanel)component).showTopMenu();
        updateUI();
        component.setVisible(true);
    }
    /**
     * ɾ����Ŀ
     * @param tag String
     */
    public void removeItem(String tag)
    {
        if(tag == null || tag.length() == 0)
            return;
        TComponent component = findItem(tag);
        if(component == null)
            return;
        Component com = (Component)component;
        if(com.getParent() != this)
            return;
        if(getWorkPanel() != null)
            getWorkPanel().remove(com);
        else
            remove(com);
        updateUI();
    }
    /**
     * ��ʾ��Ա
     * @param component TComponent
     */
    public void showItem(TComponent component)
    {
        if(component == null)
            return;
        Component com = (Component)component;
        if(com.getParent() != this)
            return;
        if(getWorkPanel() != null)
            getWorkPanel().remove(com);
        else
            remove(com);
        if(getWorkPanel() != null)
            getWorkPanel().add(com,0);
        else
            add(com,0);
        if(component instanceof TPanel)
            ((TPanel)component).showTopMenu();
        updateUI();
    }
    /**
     * ���ҳ�Ա
     * @param tag String
     * @return TComponent
     */
    public TComponent findItem(String tag) {
        if (tag == null || tag.length() == 0)
            return null;
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                if (tag.equalsIgnoreCase(tComponent.getTag()))
                    return tComponent;
            }
        }
        return null;
    }
    /**
     * ���ù�����
     * @param workPanel JPanel
     */
    public void setWorkPanel(JPanel workPanel)
    {
        this.workPanel = workPanel;
    }
    /**
     * �õ�������
     * @return JPanel
     */
    public JPanel getWorkPanel()
    {
        return workPanel;
    }
    /**
     * ���ñ�����
     * @param titlePanel JPanel
     */
    public void setTitlePanel(JPanel titlePanel)
    {
        this.titlePanel = titlePanel;
    }
    /**
     * �õ�������
     * @return JPanel
     */
    public JPanel getTitlePanel()
    {
        return titlePanel;
    }
    /**
     * �����Ƿ���ʾ������
     * @param showToolBar boolean
     */
    public void setShowToolBar(boolean showToolBar)
    {
        if(this.showToolBar == showToolBar)
            return;
        this.showToolBar = showToolBar;
        if(getTitlePanel() == null || toolBar == null)
            return;
        if(showToolBar)
            getTitlePanel().add(toolBar,BorderLayout.SOUTH);
        else
            getTitlePanel().remove(toolBar);
        getTitlePanel().updateUI();
    }
    /**
     * �õ��Ƿ���ʾ������
     * @return boolean
     */
    public boolean isShowToolBar()
    {
        return showToolBar;
    }
    /**
     * �Ƿ���ʾ�˵�
     * @return boolean
     */
    public boolean isShowMenu()
    {
        return showMenu;
    }
    /**
     * ������ʾ�˵�
     * @param showMenu boolean
     */
    public void setShowMenu(boolean showMenu)
    {
        this.showMenu = showMenu;
    }
    /**
     * ������ʾ����
     * @param showTitle boolean
     */
    public void setShowTitle(boolean showTitle)
    {
        if (this.showTitle == showTitle)
            return;
        this.showTitle = showTitle;
        if(!showTitle)
        {
            if(getWorkPanel() == null)
                return;
            setLayout((LayoutManager)null);
            removeAll();
            int count = getWorkPanel().getComponentCount();
            for(int i = count - 1;i >= 0 ;i --)
                add(getWorkPanel().getComponent(i),0);
            getWorkPanel().removeAll();
            setWorkPanel(null);
            setTitlePanel(null);
            callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{
                      getWidth(),getHeight()},new String[]{"int","int"});
            updateUI();
            repaint();
            return;
        }
        JPanel panel = new JPanel(){
            public void setBounds(int x, int y, int width, int height)
                {
                    super.setBounds(x,y,width,height);
                    callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height},new String[]{"int","int"});
                }
            };

        setWorkPanel(panel);
        getWorkPanel().setLayout(null);
        getWorkPanel().setBackground(getBackground());
        int count = getComponentCount();
        for(int i = count - 1;i >= 0 ;i --)
            getWorkPanel().add(getComponent(i),0);
        removeAll();
        setLayout(new BorderLayout());
        setTitlePanel(new JPanel());
        getTitlePanel().setLayout(new BorderLayout());
        if(menuBar != null && isShowMenu())
            getTitlePanel().add(menuBar, BorderLayout.CENTER);
        if(toolBar != null && isShowToolBar())
            getTitlePanel().add(toolBar,BorderLayout.SOUTH);
        JLabel label = new JLabel(getTitle());
        getTitlePanel().add(label,BorderLayout.NORTH);
        add(getTitlePanel(),BorderLayout.NORTH);
        add(getWorkPanel(),BorderLayout.CENTER);
        updateUI();
        repaint();
    }
    /**
     * �����Ƿ��ڴ��ڸ��ǲ˵�
     * @param topMenu boolean
     */
    public void setTopMenu(boolean topMenu)
    {
        this.topMenu = topMenu;
        if(topMenu)
        {
            callFunction("removeChildMenuBar");
            if (menuBar != null)
                callFunction("setChildMenuBar", getTag(), menuBar);
        }else
        {
            if (menuBar != null && getTitlePanel() != null && isShowMenu())
            {
                callFunction("removeChildMenuBar");
                getTitlePanel().add(menuBar, BorderLayout.CENTER);
            }

        }
    }
    /**
     * �Ƿ��ڴ��ڸ��ǲ˵�
     * @return boolean
     */
    public boolean isTopMenu()
    {
        return topMenu;
    }
    /**
     * �����Ƿ��ڴ��ڸ��ǹ�����
     * @param topToolBar boolean
     */
    public void setTopToolBar(boolean topToolBar)
    {
        this.topToolBar = topToolBar;
        if(topToolBar)
        {
            callFunction("removeChildToolBar");
            if (toolBar != null)
                callFunction("setChildToolBar", getTag(), toolBar);
        }else
        {
            if (toolBar != null && getTitlePanel() != null)
            {
                callFunction("removeChildToolBar");
                getTitlePanel().add(toolBar, BorderLayout.SOUTH);
            }
        }
    }
    /**
     * �Ƿ��ڴ��ڸ��ǹ�����
     * @return boolean
     */
    public boolean isTopToolBar()
    {
        return topToolBar;
    }
    /**
     * �Ƿ���ʾ����
     * @return boolean
     */
    public boolean isShowTitle()
    {
        return showTitle;
    }
    /**
     * �õ���������Yλ��
     * @return int
     */
    public int getWorkPanelY()
    {
        if(getWorkPanel() == null)
            return 0;
        return (int)getWorkPanel().getBounds().getY();
    }
    public void resize(int width, int height) {
        callEvent(getTag() + "->" + TComponentListener.RESIZED,new Object[]{width,height - getWorkPanelY()},new String[]{"int","int"});
        super.resize(width,height);
        validate();
    }
    /**
     * �������ز˵��ϴ�
     * @param refetchTopMenu boolean
     */
    public void setRefetchTopMenu(boolean refetchTopMenu)
    {
        this.refetchTopMenu = refetchTopMenu;
    }
    /**
     * �Ƿ����ز˵��ϴ�
     * @return boolean
     */
    public boolean isRefetchTopMenu()
    {
        return refetchTopMenu;
    }
    /**
     * �������ع������ϴ�
     * @param refetchToolBar boolean
     */
    public void setRefetchToolBar(boolean refetchToolBar)
    {
        this.refetchToolBar = refetchToolBar;
    }
    /**
     * �Ƿ����ع������ϴ�
     * @return boolean
     */
    public boolean isRefetchToolBar()
    {
        return refetchToolBar;
    }
    /**
     * �����Ӳ˵�(������)
     * @param tag String
     * @param menubar TMenuBar
     * @return Object
     */
    public Object setChildMenuBar(String tag,TMenuBar menubar)
    {
        if(isRefetchTopMenu())
            return "Refetch";
        return null;
    }
    /**
     * ɾ���Ӳ˵�(������)
     * @return Object
     */
    public Object removeChildMenuBar()
    {
        if(isRefetchTopMenu())
            return "Refetch";
        return null;
    }
    /**
     * �����ӹ�����
     * @param tag String
     * @param toolbar TToolBar
     * @return Object
     */
    public Object setChildToolBar(String tag,TToolBar toolbar)
    {
        if(isRefetchToolBar())
            return "Refetch";
        return null;
    }
    /**
     * ɾ���ӹ�����
     * @return Object
     */
    public Object removeChildToolBar()
    {
        if(isRefetchToolBar())
            return "Refetch";
        return null;
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
        showTopMenu();
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
     * �Ƿ����ڼ���
     * @return boolean
     */
    public boolean isInitNow()
    {
        return initNow;
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
            if ("call".equalsIgnoreCase(actionMessage))
                actionMessage = getText();
            callMessage(actionMessage);
        }
        return "OK";
    }

    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * ��ʾ����
     */
    public void onShowWindow()
    {
        showTopMenu();
        exeAction(getShowWindowAction());
    }
    /**
     * ������ʾ������Ϣ
     * @param action String
     */
    public void setShowWindowAction(String action) {
        actionList.setAction("showWindowAction",action);
    }

    /**
     * �õ���ʾ������Ϣ
     * @return String
     */
    public String getShowWindowAction() {
        return actionList.getAction("showWindowAction");
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
     * /�ͷ�Ԫ��
     */
    public void releaseItem()
    {
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (!(component instanceof TComponent))
                continue;
            TComponent tComponent = (TComponent) component;
            tComponent.release();
        }
    }
    /**
     * �ͷ�
     */
    public void release()
    {
        //�ͷż���
        releaseListeners();
        //�ͷſ�����
        if(control != null)
        {
            control.release();
            control = null;
        }
        //�ͷ�Ԫ��
        releaseItem();
        //�ͷ�����
        RunClass.release(this);
    }
    /**
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        super.setName(name);
        if(name == null || name.length() == 0)
            return;
        TTabbedPane tabbed = null;
        if(getParentComponent() == null)
        {
            TComponent com = getParentTComponent();
            if(com == null || !(com instanceof TTabbedPane))
                return;
            tabbed = (TTabbedPane)com;
        }
        else if(getParentComponent() instanceof TTabbedPane)
            tabbed = (TTabbedPane)getParentComponent();
        else
            return;
        if(tabbed != null)
            tabbed.setTitleAt(getTag(),name);
    }
    protected void finalize() throws Throwable {
        super.finalize();
        //System.out.println("finalize TPanelBase");
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
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if(language == null)
            return;
        if(language.equals(this.language))
            return;
        this.language = language;
        int count = getWorkPanel() != null?getWorkPanel().getComponentCount():getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getWorkPanel() != null?getWorkPanel().getComponent(i):getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                //System.out.println("=======language======="+language);
                tComponent.callFunction("changeLanguage",language);
            }
        }
        //��ʼ���˵���
        if (menuBar != null && menuBar instanceof TContainer)
            menuBar.changeLanguage(language);
        //��ѯ������
        TToolBar toolbar = getToolBar();
        if(toolbar != null)
            toolbar.changeLanguage(language);
        Border border = getBorder();
        if(border instanceof TTitledBorder)
        {
            TTitledBorder titleBorder = (TTitledBorder)border;
            titleBorder.setLanguage(language);
            repaint();
        }
        if(getControl()!= null)
            getControl().onChangeLanguage(language);
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
        if(maxLoad != null)
            return maxLoad;
        if(getParentComponent() instanceof TContainer)
            return ((TContainer)getParentComponent()).getMaxLoad();
        return null;
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
     * ���Ӵ���
     * @param tag String
     * @param config String
     * @param parm Object
     * @return TRootPanel
     */
    public TRootPanel openSheet(String tag,String config,Object parm)
    {
        TRootPanel p = new TRootPanel();
        p.setTag(tag);
        p.setIsMDISheet(true);
        p.setConfigParm(getConfigParm());
        p.addItem(tag + "_T",config,parm,false);
        TPanel p1 = (TPanel)p.getComponent(0);
        p.setX(p1.getX());
        p.setY(p1.getY());
        p.setWidth(p1.getWidth() + 6);
        p.setHeight(p1.getHeight() + 24);
        p.setTitle(p1.getTitle());
        p1.setAutoX(true);
        p1.setAutoY(true);
        p1.setAutoWidth(true);
        p1.setAutoHeight(true);
        p1.setAutoSize(0);
        p.setIsWindow(false);
        p.setParentComponent(this);
        add(p,0);
        p.onInit();
        updateUI();
        p.setVisible(true);
        return p;
    }
}
