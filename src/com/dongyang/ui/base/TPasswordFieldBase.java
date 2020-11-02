package com.dongyang.ui.base;

import javax.swing.JPasswordField;
import javax.swing.text.Document;
import com.dongyang.control.TControl;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.config.TConfigParm;
import java.awt.Container;
import com.dongyang.util.StringTool;
import java.awt.event.ActionListener;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TUIStyle;

public class TPasswordFieldBase extends JPasswordField implements TComponent {
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
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * ������Ϣ
     */
    private String actionMessage;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����Tip
     */
    private String zhTip;
    /**
     * Ӣ��Tip
     */
    private String enTip;
    /**
     * Constructs a new <code>TPasswordField</code>,
     * with a default document, <code>null</code> starting
     * text string, and 0 column width.
     */
    public TPasswordFieldBase() {
        this(null, null, 0);
    }

    /**
     * Constructs a new <code>TPasswordField</code> initialized
     * with the specified text.  The document model is set to the
     * default, and the number of columns to 0.
     *
     * @param text the text to be displayed, <code>null</code> if none
     */
    public TPasswordFieldBase(String text) {
        this(null, text, 0);
    }

    /**
     * Constructs a new empty <code>TPasswordField</code> with the specified
     * number of columns.  A default model is created, and the initial string
     * is set to <code>null</code>.
     *
     * @param columns the number of columns >= 0
     */
    public TPasswordFieldBase(int columns) {
        this(null, null, columns);
    }

    /**
     * Constructs a new <code>JPasswordField</code> initialized with
     * the specified text and columns.  The document model is set to
     * the default.
     *
     * @param text the text to be displayed, <code>null</code> if none
     * @param columns the number of columns >= 0
     */
    public TPasswordFieldBase(String text, int columns) {
        this(null, text, columns);
    }

    /**
     * Constructs a new <code>TPasswordField</code> that uses the
     * given text storage model and the given number of columns.
     * This is the constructor through which the other constructors feed.
     * The echo character is set to '*'.  If the document model is
     * <code>null</code>, a default one will be created.
     *
     * @param doc  the text storage to use
     * @param txt the text to be displayed, <code>null</code> if none
     * @param columns  the number of columns to use to calculate
     *   the preferred width >= 0; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
    public TPasswordFieldBase(Document doc, String txt, int columns) {
        super(doc, txt, columns);
        setFont(TUIStyle.getPasswordFieldDefaultFont());
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
        ActionListener actionListeners[] = getActionListeners();
        for (int i = 0; i < actionListeners.length; i++)
            removeActionListener(actionListeners[i]);
        addActionListener(new TActionListener(this));
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
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
        TComponent parentTComponent = getParentTComponent();
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
            String message = value[i];
            if ("call".equalsIgnoreCase(message))
                message = getText();
            callMessage(message);
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
        else if ("action".equalsIgnoreCase(value[0]))
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
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TPasswordFieldBase";
    }
    /**
     * ����ֵ
     * @param text String
     */
    public void setValue(String text)
    {
        setText(text);
    }
    /**
     * �õ�ֵ
     * @return String
     */
    public String getValue()
    {
        return getText();
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
        if(language == null)
            return;

        if("en".equals(language) && getEnTip() != null && getEnTip().length() > 0)
            setToolTipText(getEnTip());
        else if(getZhTip() != null && getZhTip().length() > 0)
            setToolTipText(getZhTip());
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
