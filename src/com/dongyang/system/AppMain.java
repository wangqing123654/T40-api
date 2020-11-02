package com.dongyang.system;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.dongyang.config.TConfigParm;
import com.dongyang.control.TControl;
import com.dongyang.data.TSocket;
import com.dongyang.jdo.MaxLoad;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.BaseMessageCall;
import com.dongyang.util.Log;
import com.dongyang.util.MessageCaller;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;
import com.dongyang.util.TSystem;

public class AppMain extends JFrame implements TComponent, TContainer{
	
//    protected static final String host = "127.0.0.1";
//    protected static final int port = 8080;
	protected static final String host = "127.0.0.1";
    protected static final int port = 8080;
    protected static String path = "web";
	
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
     * �ı�
     */
    private String text = "";
    /**
     * ������
     */
    private TControl control;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ������
     */
    public AppMain() {    	
    	init();
    	this.setSize(1020, 768);
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
     * �����ı�
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * �õ��ı�
     * @return String
     */
    public String getText() {
        return text;
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
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBackground(String color) {
        setBackground(StringTool.getColor(color, getConfigParm()));
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
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
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
     * ��ʼ��
     */
    public void onInit() {
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        for (int i = 0; i < getContentPane().getComponentCount(); i++) {
            Component component = getContentPane().getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        if (getControl() != null)
            getControl().onInit();
    }

    /**
     *
     * @param ldapuser
     */
    public void onInit(String ldapuser,String ldappw) {

    	TSystem.setObject("LDAPUSER", ldapuser);
    	TSystem.setObject("LDAPPW", ldappw);

    	onInit();
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
        //���ȫ�����
        getContentPane().removeAll();
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
        return "ControlClassName,ControlConfig,Layout";
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
        return MessageCaller.callMessage(this, message, parm);
    }

    /**
     * �õ������
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * �õ������
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
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object onBaseMessage(String message, Object parm) {
    	return MessageCaller.onBaseMessage(this, message, parm);
    }

    /**
     * ����������������
     * @param value String[]
     */
    public void baseFieldNameChange(String value[]) {
        if ("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
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
     * ���ز��ֹ�����
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("��")) {
            getContentPane().setLayout(null);
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
        getContentPane().setLayout(layoutManager);

    }

    /**
     * ���س�Ա���
     * @param value String
     */
    protected void createItem(String value) {
        //System.out.println("=========TAppletBase value==============="+value);
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
        if (obj == null || !(obj instanceof Component)) {
            err("loadObject " + type + " ʧ�� obj=" + obj);
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
            tComponent.callFunction("setParentComponent",this);
        }
        if (values.length == 1)
            getContentPane().add(component);
        else if (values.length == 2)
            getContentPane().add(component,
                                 StringTool.layoutConstraint(values[1]));
    }

    /**
     * ����Tag����
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag) {
        if (tag == null || tag.length() == 0)
            return null;
        for (int i = 0; i < getContentPane().getComponentCount(); i++) {
            Component component = getContentPane().getComponent(i);
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
     * ˢ��
     * @param g Graphics ͼ���豸
     */
    public void paint(Graphics g) {
        super.paint(g);
        //callEvent("paint",new Object[]{g},new String[]{"java.awt.Graphics"});
    }

    /**
     * Applet��ʼ
     */
    public void start() {
        callEvent("start");
    }

    /**
     * Appletֹͣ
     */
    public void stop() {
        callEvent("stop");
    }

    /**
     * Applet����
     */
    public void destroy1() {
        this.callEvent("destroy");
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
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * �õ��̳м�����
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        return null;
    }

    //=================================================================================
    
    
    /**
     * ��ʼ��Socket
     */
    public void initSocket()
    {
        if(path.toUpperCase().endsWith("/COMMON/LIB/"))
            path = path.substring(0,path.length() - 12);
        if(path.startsWith("/"))
            path = path.substring(1,path.length());
        System.out.println("��������:" + host + ":" + port + "/" + path);
        TIOM_AppServer.SOCKET = new TSocket(host,port,path);
    }
    /**
     * ��ʼ�����ö���
     * @return TConfigParm
     */
    public TConfigParm initConfigParm()
    {
        //���ñ�ǩ
        setTag("UI");
        //�����������ò�������
        TConfigParm configParm = new TConfigParm();
        //װ��Socket
        configParm.setSocket(TIOM_AppServer.SOCKET);
        //��ʼ��ϵͳ���
        configParm.setSystemGroup("");
        //װ���������ļ�
        configParm.setConfig("%ROOT%\\config\\system\\TMain.x");
        //װ����ɫ�����ļ�
        configParm.setConfigColor("%ROOT%\\config\\system\\TColor.x");
        //װ��Class�����ļ�
        configParm.setConfigClass("%ROOT%\\config\\system\\TClass.x");
        //����ϵͳ���
        configParm.setSystemGroup(configParm.getConfig().getString("",getTag() + ".SystemGroup"));
        //��־׷�ӱ��
        boolean appendLog = StringTool.getBoolean(configParm.getConfig().getString("",getTag() + ".AppendLog"));
        //�����־Ŀ¼
        String outLogPath = configParm.getConfig().getString("",getTag() + ".OutLogPath");
        //������־��־Ŀ¼
        String errLogPath = configParm.getConfig().getString("",getTag() + ".ErrLogPath");
        //��ʼ����־���Ŀ¼
        //Log.initLogPath(outLogPath,errLogPath,appendLog);
        //��ʼ��ϵͳ������
        try{
            UIManager.setLookAndFeel(configParm.getConfig().getString(configParm.getSystemGroup(),getTag() + ".LookAndFeel",
                                     UIManager.getSystemLookAndFeelClassName()));
        }catch(Exception e)
        {
            err(e.getMessage());
        }
        return configParm;
    }
    /**
     * Applet������ʼ��
     */
    public void init()
    {
    	System.out.println("Start...................");
        //��ʼ��Socket
        initSocket();
        
        System.out.println("initSocket OK...................");
        
        //���ؽ���
        init(initConfigParm());
        
        System.out.println("initConfigParm OK...................");

        //System.out.println("ID=" + TIOM_AppServer.getSessionAttribute("ID"));
        //System.out.println(StringTool.getString(TIOM_AppServer.getSessionAttributeNames()));

        //����ϵͳ��ʼ��
    	String ldapuser = "";
    	String ldappw = "";
    	if( null!=ldapuser && !"".equals(ldapuser) && null!=ldappw && !"".equals(ldappw) ){
    		onInit(ldapuser,ldappw);
    	}else{
    		onInit();
    	}

    }
    /**
     * Applet����
     */
    public void destroy() {
    	this.callEvent("destroy");
        Log.closeLogFile();
    }
    
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}
    
    public static void main(String[] avgs){
    	AppMain main = new AppMain();    	
    	main.setVisible(true);
    }
    
}
