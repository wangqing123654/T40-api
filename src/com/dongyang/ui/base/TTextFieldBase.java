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
     * �����ַ��ĳ���
     */
    private int inputLength;
    /**
     * �����¼���������
     */
    private TActionListener actionListenerObject;
    /**
     * �����¼���������
     */
    private TFocusListener focusListenerObject;
    /**
     * �����¼���������
     */
    private TKeyListener keyListenerObject;
    /**
     * ƴ������ı�ǩ
     */
    private String pyTag;
    /**
     * ʡ����ı�ǩ
     */
    private String stateTag;
    /**
     * ���д���ı�ǩ
     */
    private String cityTag;
    /**
     * ִ�����֤����
     */
    private boolean exePID;
    /**
     * ����˴���ı�ǩ
     */
    private String foreignerTag;
    /**
     * �Ա����ı�ǩ
     */
    private String sexTag;
    /**
     * ���մ���ı�ǩ
     */
    private String birdayTag;
    /**
     * �����˵�
     */
    private TPopupMenu popupMenu;

    /**
     * �����˵�������
     */
    private PopupMenuParameter popupMenuParameter;
    /**
     * �����б�
     */
    private ActionMessage actionList;
    /**
     * ����¼���������
     */
    private TMouseListener mouseListenerObject;
    private TTable table;

    private TTableSort tableSort;
    /**
     * ����Tip
     */
    private String zhTip;
    /**
     * Ӣ��Tip
     */
    private String enTip;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����
     */
    private String language;
    /**
     * ����
     */
    private Font tfont;

    /**
     * ������
     */
    private boolean required;

    /**
     * ������
     */
    public TTextFieldBase() {
        uiInit();
    }
    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        //��ʼ�������б�
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
     * @param action String
     */
    public void setActionMessage(String action) {
        actionList.setAction("actionAction",action);
    }

    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getActionMessage() {
        return actionList.getAction("actionAction");
    }
    /**
     * ����ʧȥ���㶯��
     * @param action String
     */
    public void setFocusLostAction(String action)
    {
        actionList.setAction("focusLostAction",action);
    }
    /**
     * �õ�ʧȥ���㶯��
     * @return String
     */
    public String getFocusLostAction()
    {
        return actionList.getAction("focusLostAction");
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
        //���������¼�
        if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }
        //���������¼�
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
        //����Mouse�¼�
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,"onRightClicked");
    }
    /**
     * ���
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
        exeAction(getClickedAction());
    }
    /**
     * �һ�
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
        exeAction(getRightClickedAction());
    }
    /**
     * ˫��
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {
        exeAction(getDoubleClickedAction());
    }
    /**
     * �����¼�
     * @param e ActionEvent
     */
    public void onAction(ActionEvent e)
    {
        //ִ��ƴ�����붯��
        onPyTag();
        //ִ��ʡ���붯��
        onStateTag();
        //ִ�г��д��붯��
        onCityTag();
        //ִ�д����Ա�
        onSexTag();
        //ִ�д�������
        onBirdayTag();
    }
    /**
     * ʧȥ�����¼�
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        exeAction(getFocusLostAction());
    }
    /**
     * �����¼�
     * @param e KeyEvent
     */
    public void onKeyPressed(KeyEvent e)
    {

    }
    /**
     * ̧���¼�
     * @param e KeyEvent
     */
    public void onKeyReleased(KeyEvent e)
    {
        callEvent(TTextFieldEvent.KEY_RELEASED,new Object[]{getText()},new String[]{"java.lang.String"});
    }
    /**
     * �ڲ�̧���¼�
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
     * ��ʼ��
     */
    public void onInit() {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        if (getControl() != null)
            getControl().onInit();
        //��������
        changeLanguage((String)TSystem.getObject("Language"));
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
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TTextFieldBase";
    }
    /**
     * ���������ַ��ĳ���
     * @param inputLength int 0 ������
     */
    public void setInputLength(int inputLength)
    {
        this.inputLength = inputLength;
    }
    /**
     * �õ������ַ��ĳ���
     * @return int 0 ������
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
     * ����ֵ
     * @param value String
     */
    public void setValue(String value)
    {
        setText(value);
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
     * �õ�����
     */
    public void grabFocus() {
        requestFocus();
        selectAll();
    }
    /**
     * �����ı�
     * @param t String
     */
    public void setText(String t)
    {
        super.setText(t);
        select(0,0);
        validate();
    }
    /**
     * ����ƴ�������Tag
     * @param pyTag String
     */
    public void setPyTag(String pyTag)
    {
        this.pyTag = pyTag;
    }
    /**
     * �õ�ƴ�������Tag
     * @return String
     */
    public String getPyTag()
    {
        return pyTag;
    }
    /**
     * ����ʡ�����Tag
     * @param stateTag String
     */
    public void setStateTag(String stateTag)
    {
        this.stateTag = stateTag;
    }
    /**
     * �õ�ʡ�����Tag
     * @return String
     */
    public String getStateTag()
    {
        return stateTag;
    }
    /**
     * ���ó��д����Tag
     * @param cityTag String
     */
    public void setCityTag(String cityTag)
    {
        this.cityTag = cityTag;
    }
    /**
     * �õ����д����Tag
     * @return String
     */
    public String getCityTag()
    {
        return cityTag;
    }
    /**
     * ���ý������֤
     * @param exePID boolean
     */
    public void setExePID(boolean exePID)
    {
        this.exePID = exePID;
    }
    /**
     * �Ƿ�������֤
     * @return boolean
     */
    public boolean isExePID()
    {
        return exePID;
    }
    /**
     * ��������˴���ı�ǩ
     * @param foreignerTag String
     */
    public void setForeignerTag(String foreignerTag)
    {
        this.foreignerTag = foreignerTag;
    }
    /**
     * �õ�����˴���ı�ǩ
     * @return String
     */
    public String getForeignerTag()
    {
        return foreignerTag;
    }
    /**
     * �����Ա�����ǩ
     * @param sexTag String
     */
    public void setSexTag(String sexTag)
    {
        this.sexTag = sexTag;
    }
    /**
     * �õ��Ա����ı�ǩ
     * @return String
     */
    public String getSexTag()
    {
        return sexTag;
    }
    /**
     * �������մ���ı�ǩ
     * @param birdayTag String
     */
    public void setBirdayTag(String birdayTag)
    {
        this.birdayTag = birdayTag;
    }
    /**
     * �õ����մ���ı�ǩ
     * @return String
     */
    public String getBirdayTag()
    {
        return birdayTag;
    }
    /**
     * ƴ�����붯��
     */
    public void onPyTag()
    {
        if(getPyTag() == null || getPyTag().length() == 0)
            return;
        String py = TMessage.getPy(getText());
        callFunction(getPyTag() + "|setText",py);
    }
    /**
     * ʡ���붯��
     */
    public void onStateTag()
    {
        if(getStateTag() == null || getStateTag().length() == 0)
            return;
        String state = TMessage.getState(getText());
        callFunction(getStateTag() + "|setText",state);
    }
    /**
     * ���д��붯��
     */
    public void onCityTag()
    {
        if(getCityTag() == null || getCityTag().length() == 0)
            return;
        String city = TMessage.getCity(getText());
        callFunction(getCityTag() + "|setText",city);
    }
    /**
     * �Ա���붯��
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
     * ���մ��붯��
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
     * ��ȡ������������
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
     * ������ѿ��
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
     * ������Ѹ߶�
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
     * ���õ����˵�������
     * @param tag String �����˵�Tag
     * @param filename String �����˵��ļ���
     */
    public void setPopupMenuParameter(String tag,String filename)
    {
        setPopupMenuParameter(tag,filename,new TParm());
    }
    /**
     * ���õ����˵�������
     * @param tag String �����˵�Tag
     * @param filename String �����˵��ļ���
     * @param parm TParm �����˵��������
     */
    public void setPopupMenuParameter(String tag,String filename,TParm parm)
    {
        setPopupMenuParameter(tag,getConfigParm().newConfig(filename),parm);
    }
    /**
     * ���õ����˵�������
     * @param tag String �����˵�Tag
     * @param configParm TConfigParm �����˵������ļ�
     */
    public void setPopupMenuParameter(String tag,TConfigParm configParm)
    {
        setPopupMenuParameter(tag,configParm,new TParm());
    }
    /**
     * ���õ����˵�������
     * @param tag String �����˵�Tag
     * @param configParm TConfigParm �����˵������ļ�
     * @param parm TParm �����˵��������
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
     * ���յ����˵��ķ���ֵ
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
     * ���õ����˵�
     * @param popupMenu TPopupMenu
     */
    public void setPopupMenu(TPopupMenu popupMenu)
    {
        this.popupMenu = popupMenu;
    }
    /**
     * �õ������˵�
     * @return TPopupMenu
     */
    public TPopupMenu getPopupMenu()
    {
        return popupMenu;
    }
    /**
     * ���õ����˵�������
     * @param popupMenuParameter PopupMenuParameter
     */
    public void setPopupMenuParameter(PopupMenuParameter popupMenuParameter)
    {
        this.popupMenuParameter = popupMenuParameter;
    }
    /**
     * �õ������˵�������
     * @return PopupMenuParameter
     */
    public PopupMenuParameter getPopupMenuParameter()
    {
        return popupMenuParameter;
    }
    /**
     *
     * <p>Title: �����˵�������</p>
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
     * ���õ�������
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * ����˫������
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * �õ�˫������
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * �����һ�����
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * �õ��һ�����
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * ����Table
     * @param table TTable
     */
    public void setTable(TTable table)
    {
        this.table = table;
    }
    /**
     * �õ�Table
     * @return TTable
     */
    public TTable getTable()
    {
        return table;
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

    public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
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

	public TTableSort getTableSort() {
		return tableSort;
	}
	public void setTableSort(TTableSort tableSort) {
		this.tableSort = tableSort;
	}


}
