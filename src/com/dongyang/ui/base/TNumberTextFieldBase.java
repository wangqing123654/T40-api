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
     * ʧȥ���㶯��
     */
    private String focusLostAction;
    /**
     * ����¼���������
     */
    private TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    private TMouseMotionListener mouseMotionListenerObject;
    /**
     * ������������
     */
    private TActionListener actionListenerObject;
    /**
     * ���̼�������
     */
    private TKeyListener keyListenerObject;
    /**
     * �����¼���������
     */
    private TFocusListener focusListenerObject;
    /**
     * �Ƿ��ʼ���¼�����
     */
    private boolean isInitListener;
    /**
     * ��ʽ
     */
    private String format;
    /**
     * С������߸���
     */
    private int leftCount = 0;
    /**
     * С������߸���
     */
    private int rightCount = 0;
    /**
     * ��ʽ����
     */
    private DecimalFormat formatObject;
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
     * ����
     */
    private Font tfont;
    /**
     * ������
     */
    public TNumberTextFieldBase() {
        uiInit();
    }
    /**
     * �ڲ���ʼ��UI
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
     * ���ÿ���
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
     * ��������ƫ��
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
     * @param methodName String ��������
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }

    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object ��������
     * @param methodName String ��������
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
        //��������ƶ��¼�
        if(mouseMotionListenerObject == null)
        {
            mouseMotionListenerObject = new TMouseMotionListener(this);
            addMouseMotionListener(mouseMotionListenerObject);
        }
        //���������¼�
        if(keyListenerObject == null)
        {
            keyListenerObject = new TKeyListener(this);
            addKeyListener(keyListenerObject);
        }
        //���������¼�
        if(focusListenerObject == null)
        {
            focusListenerObject = new TFocusListener(this);
            addFocusListener(focusListenerObject);
        }
        //���������¼�
        if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }
        if(!isInitListener)
        {
            //����Mouse�¼�
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
     * ʧȥ�����¼�
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        format();
        exeAction(getFocusLostAction());
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
    public void onActionPerformed(ActionEvent e)
    {
    }
    /**
     * ���̰����¼�
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
     * ���̻س�������
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
     * ����ESC����
     * @param e KeyEvent
     */
    public void onKeyESC(KeyEvent e)
    {
        callEvent(getTag() + "->" + TNumberTextFieldEvent.EDIT_ESC,
                  new Object[]{},new String[]{});
    }
    /**
     * ����TAB����
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
     * ����̧���¼�
     * @param e KeyEvent
     */
    public void onKeyReleased(KeyEvent e)
    {
    }
    /**
     * ����¼���¼�
     * @param e KeyEvent
     */
    public void onKeyType(KeyEvent e)
    {

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
        //����������Ϣ
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //�������������Ϣ
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
            String message = value[i];
            if ("call".equalsIgnoreCase(message))
                message = getText();
            callMessage(message);
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
        //��������
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
     * ���ø�ʽ
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
     * �õ���ʽ
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
     * �õ�Table�����ʾ�ı�
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
        return "TNumberTextFieldBase";
    }
    /**
     * ����ֵ
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
     * �õ�����
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
     * ����ʧȥ���㶯��
     * @param focusLostAction String
     */
    public void setFocusLostAction(String focusLostAction)
    {
        this.focusLostAction = focusLostAction;
    }
    /**
     * �õ�ʧȥ���㶯��
     * @return String
     */
    public String getFocusLostAction()
    {
        return focusLostAction;
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