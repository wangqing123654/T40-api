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
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.dongyang.ui.TUIStyle;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.ui.TToolButton;
import javax.swing.JPopupMenu;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Cursor;
import com.dongyang.ui.event.TButtonEvent;

public class TComboButtonBase extends JComponent implements TComponent {
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
     * �̶����
     */
    public static int WIDTH = 42;
    /**
     * �̶��߶�
     */
    public static int HEIGHT = 32;
    /**
     * ѡ�а�ť
     */
    private TToolButton button;
    /**
     * ������״̬
     */
    private boolean bEntered;
    /**
     * ������״̬
     */
    private boolean bPressed;
    /**
     * �������
     */
    private JPopupMenu pop = new PopupPanel();
    GridLayout gridLayout = new GridLayout();
    /**
     * ������
     */
    public TComboButtonBase() {
        uiInit();
    }

    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        setFont(TUIStyle.getButtonDefaultFont());
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(new Dimension(WIDTH, HEIGHT));
        validate();
        pop.setLayout(gridLayout);
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
    /*public void setGlobalKey(int keyValue, int controlKey) {
        registerKeyboardAction(actionListener,
                               KeyStroke.getKeyStroke(keyValue, controlKey, false),
                               2);
        //this.setAccelerator(KeyStroke.getKeyStroke(keyValue, controlKey, false));
    }*/

    /**
     * ��ȫ�ֿ�ݼ�
     * @param key String "CTRL+F1"
     */
    /*public void setGlobalKey(String key) {
        this.key = key;
        setGlobalKey(StringTool.getKey(key), StringTool.getControlKey(key));
    }*/

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
        /*if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }*/
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
        bPressed = true;
        if (!pop.isShowing())
          pop.show(this, 0, HEIGHT);
        repaint();
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        bPressed = false;
        repaint();
    }
    /**
     * ������
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        bEntered = true;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        repaint();
    }
    /**
     * ����뿪
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        bEntered = false;
        repaint();
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

    }
    /**
     * ��ʼ��
     */
    public void onInit() {
        //��ʼ�������¼�
        initListeners();
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
     *
     * <p>Title: �������</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * <p>Company: JavaHis</p>
     * @author lzk 2006.08.03
     * @version 1.0
     */
    public class PopupPanel
        extends JPopupMenu
    {
      /**
       * ������
       */
      public PopupPanel()
      {
        setBackground(new Color(214, 221, 255));
        setBorderPainted(false);
      }

      public TComboButtonBase getCombo()
      {
          return TComboButtonBase.this;
      }

      /**
       * ��ͼ
       * @param g Graphics
       */
      public void paint(Graphics g)
      {
        super.paint(g);
        drawBorder(g, 0, 0, getWidth(), getHeight(), 1);
      }
    }
    /**
     * ���߿�
     * @param g Graphics ͼ���豸
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param style int ���� 0 �� 1 ͹ 2 ��
     */
    public void drawBorder(Graphics g, int x, int y, int width, int height,
                           int style)
    {
      if (style == 0 || style > 2)
        return;
      Color c1 = new Color(255, 255, 255);
      Color c2 = new Color(165, 163, 151);
      if (style == 2)
      {
        c1 = new Color(165, 163, 151);
        c2 = new Color(255, 255, 255);
      }
      g.setColor(c1);
      g.drawLine(x, y, width - 1, y);
      g.drawLine(x, y, x, height - 1);
      g.setColor(c2);
      g.drawLine(width, y, width, height);
      g.drawLine(x, height, width, height);
    }
    /**
     * ��������
     * @param column int
     */
    public void setColumnCount(int column)
    {
        gridLayout.setColumns(column);
    }
    /**
     * �õ�����
     * @return int
     */
    public int getColumnCount()
    {
        return gridLayout.getColumns();
    }
    /**
     * ��������
     * @param row int
     */
    public void setRowCount(int row)
    {
        gridLayout.setRows(row);
    }
    /**
     * �õ�����
     * @return int
     */
    public int getRowCount()
    {
        return gridLayout.getRows();
    }
    /**
     * ����Ŀ
     * @param c Component
     */
    public void addItem(Component c)
    {
        pop.add(c);
    }
    /**
     * �õ���Ŀ
     * @param index int
     * @return Component
     */
    public Component getItem(int index)
    {
        return pop.getComponent(index);
    }
    /**
     * �õ���Ŀ����
     * @return int
     */
    public int getItemCount()
    {
        return pop.getComponentCount();
    }
    /**
     * �õ���Ŀ
     * @param tag String
     * @return TComponent
     */
    public TComponent getItem(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        int count = getItemCount();
        for(int i = 0;i < count;i++)
        {
            Component com = getItem(i);
            if(!(com instanceof TComponent))
                continue;
            TComponent tcom = (TComponent)com;
            if(tcom.getTag().equals(tag))
                return tcom;
        }
        return null;
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        int style = 0;
        int x = getWidth() - 10;
        int y = 1;
        if (bEntered)
        {
          style = 1;
          if (bPressed)
            style = 2;
          drawBorder(g, x, y, getWidth() - 1, getHeight() - 2, style);
        }
        if (bEntered && bPressed)
          drawPoit(g, x + 6, y + 11);
        else
          drawPoit(g, x + 5, y + 10);
    }
    /**
     * ���м������
     * @param g Graphics ͼ���豸
     * @param x int
     * @param y int
     */
    public void drawPoit(Graphics g, int x, int y)
    {
        g.setColor(new Color(0, 0, 0));
        g.fillPolygon(new int[]
                      {x, x - 3, x + 3}
                      , new int[]
                      {y + 3, y, y}
                      , 3);
    }
    /**
     * ���õ�ǰ��ť
     * @param button TToolButton
     */
    public void setButton(TToolButton button)
    {
        if(button == null)
            return;
        TToolButton b = new TToolButton();
        b.setPictureName(button.getPictureName());
        b.setTag(button.getTag());
        b.setToolTipText(button.getToolTipText());
        b.setParentComponent(this);
        b.setMaximumSize(new Dimension(32,32));
        b.setMinimumSize(new Dimension(32,32));
        b.setPreferredSize(new Dimension(32,32));
        b.setSize(32,32);
        b.setActionMessage(button.getActionMessage());
        b.onInit();
        this.button = button;
        removeAll();
        add(b);
        validate();
        repaint();
        hidePop();
    }
    /**
     * ���ð�ť
     * @param tag String ��ǩ
     */
    public void setButton(String tag)
    {
        TComponent com = getItem(tag);
        if(com == null)
            return;
        if(!(com instanceof TToolButton))
            return;
        TToolButton button = (TToolButton)com;
        setButton(button);
    }
    /**
     * ���ص������
     */
    public void hidePop()
    {
        if (pop.isShowing())
            pop.setVisible(false);
        this.grabFocus();
    }
    /**
     * ������Ŀ
     * @param tag String ��ǩ
     * @param tip String ע��
     * @param pic String ͼƬ
     * @param action String ����
     * @return TToolButton
     */
    public TToolButton addItem(String tag,String tip,String pic,String action)
    {
        TToolButton b = new TToolButton();
        b.setTag(tag);
        b.setToolTipText(tip);
        b.setPictureName(pic);
        b.setParentComponent(this);
        b.setMaximumSize(new Dimension(32,32));
        b.setMinimumSize(new Dimension(32,32));
        b.setPreferredSize(new Dimension(32,32));
        b.setSize(32,32);
        b.setActionMessage(action);
        b.onInit();
        b.addEventListener(TButtonEvent.ACTION,this,"onToolbuttonAction");
        addItem(b);
        return b;
    }
    /**
     * ������Ŀ
     * @param items String
     */
    public void setItems(String items)
    {
        String list[] = StringTool.parseLine(items,";");
        for(int i = 0;i < list.length;i++)
        {
            String message[] = StringTool.parseLine(list[i],",");
            if(message.length < 4)
            {
                err("items ������������!" + list[i] + " ��ʽΪ: TAG,tip,pic,action");
                return;
            }
            addItem(message[0],message[1],message[2],message[3]);
        }
    }
    /**
     * ��ť����
     * @param tag String
     * @param com TComponent
     */
    public void onToolbuttonAction(String tag,TComponent com)
    {
        TToolButton button = (TToolButton)com;
        setButton(button);
        button.getModel().setRollover(false);
        button.getModel().setPressed(false);
        callEvent(TButtonEvent.ACTION,new Object[]{tag,com},new String[]{"java.lang.String","com.dongyang.ui.TComponent"});
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
