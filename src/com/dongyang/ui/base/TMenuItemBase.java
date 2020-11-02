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
     * �����ı�
     */
    private String text;
    /**
     * ��ݼ�
     */
    private String key;
    /**
     * ������Ϣ
     */
    private String actionMessage;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ������ͦ����
     */
    private TActionListener actionListener = new TActionListener(this);
    /**
     * ͼƬ����
     */
    private String pictureName;
    /**
     * ͼƬ����
     */
    private ImageIcon pic;
    private static Icon defaultIcon;
    private static JLabel label;
    /**
     * �����ı�
     */
    private String zhText;
    /**
     * Ӣ���ı�
     */
    private String enText;
    /**
     * ����
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
     * UI��ʼ��
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
     * ���ð�ťͼƬ
     * @param name ͼƬ����
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
     * �õ���ťͼƬ
     * @return ͼƬ����
     */
    public String getPictureName() {
        return pictureName;
    }

    /**
     * ���ö�����Ϣ
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
     * �õ�������Ϣ
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }

    /**
     * ������Ч (����ͬ������������)
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
     * ������ʾ (����ͬ������������)
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
     * �õ�������
     * @return TToolBar
     */
    public TToolBar getToolBar() {
        TMenuBar menubar = getMenuBar();
        if (menubar == null)
            return null;
        return menubar.getToolBar();
    }

    /**
     * �õ��������ڲ����
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
     * �õ��˵���
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
     * ����ͼƬ
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
            err("û���ҵ�ͼ��" + path);
        }
        return icon;
    }

    /**
     * ��ȫ�ֿ�ݼ�
     * @param keyValue int
     * @param controlKey int 1 shift 2 ctrl 3 shift+ctrl
     */
    public void setGlobalKey(int keyValue, int controlKey) {
        //registerKeyboardAction(actionListener, KeyStroke.getKeyStroke(keyValue, controlKey, false), 2);
        this.setAccelerator(KeyStroke.getKeyStroke(keyValue, controlKey, false));
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
     * ��ʼ��
     */
    public void onInit() {
        ActionListener actionListeners[] = getActionListeners();
        for (int i = 0; i < actionListeners.length; i++)
            removeActionListener(actionListeners[i]);
        addActionListener(actionListener);
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
    public boolean filterInit(String message) {
        String value[] = StringTool.getHead(message, "=");
        if ("tip".equalsIgnoreCase(value[0]))
            return false;
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
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
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
        //����Ĭ�϶���
        value = onDefaultActionMessage(message, parm);
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
            return sendActionMessage();
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
     * ���ÿ�ݼ�
     * @param c char
     */
    public void setM(char c) {
        setMnemonic(c);
    }

    /**
     * �õ���ݼ�
     * @return char
     */
    public char getM() {
        return (char) getMnemonic();
    }

    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
        if (this.getMnemonic() > 0)
            text += "(" + (char) getMnemonic() + ")";
        super.setText(text);
    }
    /**
     * ���������ı�
     * @param zhText String
     */
    public void setZhText(String zhText)
    {
        this.zhText = zhText;
    }
    /**
     * �õ������ı�
     * @return String
     */
    public String getZhText()
    {
        return zhText;
    }
    /**
     * ����Ӣ���ı�
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * �õ�Ӣ�����ı�
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }

    /**
     * ���ÿ�ݼ�
     * @param mnemonic char
     */
    public void setMnemonic(char mnemonic) {
        super.setMnemonic(mnemonic);
        if (mnemonic != 0)
            setText(getTextString());
    }

    /**
     * ���ػ����ı���Ϣ
     * @return String
     */
    public String getTextString() {
        return text;
    }

    /**
     * ��ͼ
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
     * ��������
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
     * ����ͼƬ
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
     * ���ƽ���
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
