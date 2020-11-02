package com.dongyang.ui.base;

import javax.swing.JPopupMenu;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.StringTool;
import java.awt.Container;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Graphics;
import javax.swing.border.Border;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.TMenu;
import com.dongyang.ui.TMenuItem;
import java.awt.LayoutManager;
import com.dongyang.ui.event.TPopupMenuEvent;
import java.awt.Point;
import javax.swing.MenuSelectionManager;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import com.dongyang.ui.TTextField;
import com.dongyang.tui.DMessageIO;
import com.dongyang.jdo.MaxLoad;

public class TPopupMenuBase extends JPopupMenu implements TComponent,
        TContainer {
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
     * ������
     */
    private TControl control;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ��ϢIO;
     */
    private DMessageIO messageIO;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * ���㴦�����
     */
    private TFocusTraversalPolicy tFocus;
    /**
     * �������
     */
    private Object parameter;
    /**
     * ����ֵ
     */
    private Object returnValue;
    /**
     * ������
     */
    private boolean notHide;
    /**
     * �ڲ�������
     */
    private boolean insetNotHide;
    /**
     * ����
     */
    private String type;
    /**
     * Ŀ������
     */
    private Component invokerComponent;
    /**
     * ����
     */
    private String language;
    /**
     * Creates a TPopupMenu.
     */
    public TPopupMenuBase() {
        setBackground(TUIStyle.getMenuBackColor());
        setBorder(new Border() {
            public void paintBorder(Component c, Graphics g, int x, int y,
                                    int width, int height) {
                g.setColor(TUIStyle.getMenuBorderColor());
                g.drawLine(0, 0, 0, height - 1);
                g.drawLine(0, 0, width - 1, 0);
                g.drawLine(width - 1, 0, width - 1, height - 1);
                g.drawLine(0, height - 1, width - 1, height - 1);
            }

            public Insets getBorderInsets(Component c) {
                return new Insets(1, 1, 1, 1);
            }

            public boolean isBorderOpaque() {
                return true;
            }
        });
        //�������������
        tFocus = new TFocusTraversalPolicy();
        //ȡ������
        setFocusable(false);
        //���ý��������
        setFocusTraversalPolicy(tFocus);
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
     * ������ϢIO
     * @param messageIO DMessageIO
     */
    public void setMessageIO(DMessageIO messageIO)
    {
        this.messageIO = messageIO;
    }
    /**
     * �õ���ϢIO
     * @return DMessageIO
     */
    public DMessageIO getMessageIO()
    {
        return messageIO;
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
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        if (getControl() != null)
            getControl().onInit();
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
        System.out.println("value[] " + getLoadTag() + " " + StringTool.getString(value));
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
    public boolean filterInit(String message) {
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
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        if(getMessageIO() != null)
            getMessageIO().callMessage(message,parm);
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
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
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
     * ������Ŀ
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
    }

    /**
     * ���������
     * @param value String
     */
    private void createItem(String value) {
        //���ߴ���ָ���
        if (value.equals("|")) {
            addSeparator();
            return;
        }
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
        }
        //���ø������
        if (component instanceof TMenu)
            ((TMenu) component).setParentComponent(this);
        if (component instanceof TMenuItem)
            ((TMenuItem) component).setParentComponent(this);
        if (values.length == 1)
            add(component);
        else if (values.length == 2)
            add(component, StringTool.layoutConstraint(values[1]));
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
     * ���ز��ֹ�����
     * @param value String
     */
    public void setLayout(String value) {
        if (value.length() == 0)
            return;
        if (value.equalsIgnoreCase("null") || value.equals("��")) {
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
        setLayout(layoutManager);
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
     * ��ʾ
     * @param invoker Component �����齨
     * @param x int
     * @param y int
     */
    public void show(Component invoker, int x, int y) {
        super.show(invoker,x,y);
        if(tFocus.getCompFocus().length > 0)
            callMessage(tFocus.getCompFocus()[0] + "|grabFocus");
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
     * ���÷���ֵ
     * @param returnValue String
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
        this.callEvent(TPopupMenuEvent.RETURN_VALUE,
                       new Object[] {tag,returnValue},
                       new String[] {"java.lang.String","java.lang.Object"});
    }

    /**
     * �õ�����ֵ
     * @return Object
     */
    public Object getReturnValue() {
        return returnValue;
    }
    /**
     * ���ò�����
     * @param notHide boolean
     */
    public void setNotHide(boolean notHide)
    {
        this.notHide = notHide;
    }
    /**
     * �Ƿ�����
     * @return boolean
     */
    public boolean isNotHide()
    {
        return notHide;
    }
    /**
     * �����ڲ�������
     * @param insetNotHide boolean
     */
    public void setInsetNotHide(boolean insetNotHide)
    {
        this.insetNotHide = insetNotHide;
    }
    /**
     * �Ƿ��ڲ�������
     * @return boolean
     */
    public boolean isInsetNotHide()
    {
        return insetNotHide;
    }
    /**
     * ����
     */
    public void hidePopup()
    {
        boolean b = notHide;
        boolean b1 = insetNotHide;
        notHide = false;
        insetNotHide = false;
        setVisible(false);
        notHide = b;
        insetNotHide = b1;
        if(getInvokerComponent() != null && getInvokerComponent() instanceof TTextField)
            ((TTextField)getInvokerComponent()).grabFocus();
    }
    /**
     * ��������
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    public void setVisible(boolean visible)
    {
        if(!visible && notHide)
            return;
        if(!visible && insetNotHide && getMousePosition() != null)
        {
            /*SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MenuElement[] oldme = MenuSelectionManager.defaultManager().getSelectedPath();
                    MenuElement[] me = new MenuElement[oldme.length + 1];
                    System.arraycopy(oldme,0,me,0,oldme.length);
                    me[oldme.length] = (MenuElement) TPopupMenuBase.this;
                    MenuSelectionManager.defaultManager().
                            setSelectedPath(me);
                }
            });*/
            MenuElement[] me = new MenuElement[1];

            me[0] = (MenuElement) TPopupMenuBase.this;
            MenuSelectionManager.defaultManager().
                    setSelectedPath(me);
            return;
        }
        super.setVisible(visible);
    }
    /**
     * ����Ŀ������
     * @param invokerComponent Component
     */
    public void setInvokerComponent(Component invokerComponent)
    {
        this.invokerComponent = invokerComponent;
    }
    /**
     * �õ�Ŀ������
     * @return Component
     */
    public Component getInvokerComponent()
    {
        return invokerComponent;
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
        int count = getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callFunction("changeLanguage",language);
            }
        }

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
}
