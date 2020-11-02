package com.dongyang.ui.base;

import com.dongyang.ui.TComponent;
import javax.swing.JComponent;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import com.dongyang.util.RunClass;
import java.awt.Container;
import com.dongyang.util.StringTool;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Graphics;
import com.dongyang.ui.event.ActionMessage;
import java.awt.Font;
import com.dongyang.ui.TPanel;
import com.dongyang.ui.event.TComponentListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ComponentEvent;
import java.awt.Insets;

/**
 *
 * <p>Title: �����ؼ�</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.4
 * @version 1.0
 */
public class TComponentBase extends JComponent
        implements TComponent,MouseWheelListener
{
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
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����¼���������
     */
    private TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    private TMouseMotionListener mouseMotionListenerObject;
    /**
     * �����������
     */
    private TComponentListener componentListenerObject;
    /**
     * ���
     */
    private int cursorType;
    /**
     * ��Ϣ��ͼ
     */
    protected ActionMessage actionList;
    /**
     * �Զ��ߴ�
     */
    private int autoSize = 5;
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
     * ������
     */
    public TComponentBase()
    {
        actionList = new ActionMessage();
        this.addMouseWheelListener(this);
    }
    /**
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject()
    {
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
    public void addEventListener(String eventName, Object object, String methodName)
    {
        getBaseEventObject().add(eventName,object,methodName);
    }
    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object, String methodName)
    {
        getBaseEventObject().remove(eventName,object,methodName);
    }
    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName,Object[] parms,String[] parmType)
    {
        return getBaseEventObject().callEvent(eventName,parms,parmType);
    }
    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName)
    {
        return callEvent(eventName,new Object[]{},new String[]{});
    }
    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName,Object parm)
    {
        return callEvent(eventName,new Object[]{parm},new String[]{"java.lang.Object"});
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
    public Object callMessage(String message)
    {
        return callMessage(message,null);
    }
    /**
     * ������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message,Object parm)
    {
        if(message == null ||message.length() == 0)
            return null;
        Object value = onBaseMessage(message,parm);
        if(value != null)
            return value;
        if(getControl() != null)
        {
            value = getControl().callMessage(message,parm);
            if(value != null)
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
     * �õ�����
     * @return TComponent
     */
    public TComponent getParentTComponent()
    {
        return getParentTComponent(getParent());
    }
    /**
     * �õ�����(�ݹ���)
     * @param container Container
     * @return TComponent
     */
    public TComponent getParentTComponent(Container container)
    {
        if(container == null)
            return null;
        if(container instanceof TComponent)
            return(TComponent)container;
        return getParentTComponent(container.getParent());
    }
    /**
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message,Object parm)
    {
        if(message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message,"|");
        Object result = null;
        if((result = RunClass.invokeMethodT(this,value,parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message,"=");
        //����������������
        baseFieldNameChange(value);
        if((result = RunClass.invokeFieldT(this,value,parm)) != null)
            return result;
        return null;
    }
    /**
     * ����������������
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[])
    {
        if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
    }
    /**
     * �����������
     * @param value String
     */
    public void setControlConfig(String value)
    {
        if(getControl() == null)
        {
            err("control is null");
            return;
        }
        getControl().setConfigParm(getConfigParm().newConfig(value));
    }
    /**
     * ���������������
     * @param value String
     */
    public void setControlClassName(String value)
    {
        this.controlClassName = controlClassName;
        if(value == null || value.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(value);
        if(obj == null)
        {
            err("Class loadObject err className=" + value);
            return;
        }
        if(!(obj instanceof TControl))
        {
            err("Class loadObject type err className=" + value + " is not TControl");
            return;
        }
        TControl control = (TControl)obj;
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
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control)
    {
        this.control = control;
        if(control != null)
            control.setComponent(this);
    }
    /**
     * �õ����������
     * @return TControl
     */
    public TControl getControl()
    {
        return control;
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
     * �������ò�������
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * �õ����ò�������
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return configParm;
    }
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm)
    {
        if(configParm == null)
            return;
        //�������ö���
        setConfigParm(configParm);
        //����ȫ������
        String value[] = configParm.getConfig().getTagList(configParm.getSystemGroup(),getLoadTag(),getDownLoadIndex());
        for(int index = 0;index < value.length;index++)
            if(filterInit(value[index]))
                callMessage(value[index], configParm);
        //���ؿ�����
       if (getControl() != null)
           getControl().init();
    }
    /**
     * �õ���ǩ
     * @return String
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * ���ñ�ǩ
     * @param tag String
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    /**
     * ���ü��ر�ǩ
     * @param loadtag String
     */
    public void setLoadTag(String loadtag)
    {
        this.loadtag = loadtag;
    }
    /**
     * �õ����ر�ǩ
     * @return String
     */
    public String getLoadTag()
    {
        if(loadtag != null)
            return loadtag;
        return getTag();
    }
    /**
     * ����X����
     * @param x int
     */
    public void setX(int x)
    {
        this.setLocation(x,getLocation().y);
    }
    /**
     * ����Y����
     * @param y int
     */
    public void setY(int y)
    {
        this.setLocation(getLocation().x,y);
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        this.setSize(width,getSize().height);
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.setSize(getSize().width,height);
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
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    protected boolean filterInit(String message)
    {
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
     * ��ʼ��
     */
    public void onInit()
    {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        if(getControl() != null)
            getControl().onInit();
    }
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        //��������¼�
        if (mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //��������ƶ��¼�
        if (mouseMotionListenerObject == null)
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
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,"onRightClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
        TComponent component = getParentTComponent();
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
    }
    /**
     * �����ʾ�¼�
     * @param e ComponentEvent
     */
    public void onComponentShown(ComponentEvent e)
    {
    }
    /**
     * �һ�
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
    }
    /**
     * ���
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
    }
    /**
     * ˫��
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {

    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        sendActionMessage();
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
    }
    /**
     * ��껬��
     * @param e MouseWheelEvent
     */
    public void onMouseWheelMoved(MouseWheelEvent e)
    {

    }
    /**
     * ��껬��
     * @param e MouseWheelEvent
     */
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        onMouseWheelMoved(e);
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
     * ������Ϣ��ͼ
     * @param name String
     * @param action String
     */
    public void setActionMessageMap(String name,String action)
    {
        actionList.setAction(name,action);
    }
    /**
     * �õ���Ϣ��ͼ
     * @param name String
     * @return String
     */
    public String getActionMessageMap(String name)
    {
        return actionList.getAction(name);
    }
    /**
     * ���ö�����Ϣ
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        setActionMessageMap("ActionMessage",actionMessage);
    }

    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getActionMessage() {
        return getActionMessageMap("ActionMessage");
    }
    /**
     * ����ѡ������Ϣ
     * @param actionMessage String
     */
    public void setChangedAction(String actionMessage) {
        setActionMessageMap("ChangedAction",actionMessage);
    }

    /**
     * �õ�ѡ������Ϣ
     * @return String
     */
    public String getChangedAction() {
        return getActionMessageMap("ChangedAction");
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
     * �ػ�
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
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
     * �����Զ��ߴ�
     * @param autoSize int
     */
    public void setAutoSize(int autoSize)
    {
        this.autoSize = autoSize;
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
     * ��������
     * @param name String
     */
    public void setFontName(String name)
    {
        Font f = getFont();
        setFont(new Font(name,f.getStyle(),f.getSize()));
    }
    /**
     * �õ�����
     * @return String
     */
    public String getFontName()
    {
        return getFont().getFontName();
    }
    /**
     * ��������ߴ�
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getFont();
        setFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * �õ�����ߴ�
     * @return int
     */
    public int getFontSize()
    {
        return getFont().getSize();
    }
    /**
     * ������������
     * @param style int
     */
    public void setFontStyle(int style)
    {
        Font f = getFont();
        setFont(new Font(f.getFontName(),style,f.getSize()));
    }
    /**
     * �õ���������
     * @return int
     */
    public int getFontStyle()
    {
        return getFont().getStyle();
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
        if(isAutoX())
            setX(insets.left + getAutoSize());
        if(isAutoY())
            setY(insets.top + getAutoSize());
        if(isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize());
        }
        if(isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize());
        }
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TComponentBase";
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
}
