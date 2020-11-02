package com.dongyang.ui.base;

import com.dongyang.ui.TComponent;
import javax.swing.JComponent;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import com.dongyang.ui.event.BaseEvent;
import java.awt.Container;
import com.dongyang.ui.TUIStyle;
import com.dongyang.util.RunClass;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.util.StringTool;
import java.util.Vector;
import com.dongyang.ui.TListNode;
import javax.swing.Icon;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.Insets;
import com.dongyang.ui.event.TMouseListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.event.TListViewEvent;

public class TListViewBase extends JPanel implements TComponent,AdjustmentListener
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
     * ������Ϣ
     */
    private String actionMessage;
    /**
     * ���ݰ�
     */
    private Vector nodes;
    /**
     * ͼ���б�
     */
    private String pics;
    /**
     * ͼ���б�
     */
    private Map icons;
    /**
     * Ĭ��ͼ��
     */
    private Icon defaultIcon;
    /**
     * ���������
     */
    private JScrollBar scrollBarHeight = new JScrollBar();
    /**
     * �������ߴ�
     */
    private int scrollBarValue;
    /**
     * ������
     */
    public static int ITEM_WIDTH = 80;
    /**
     * ����߶�
     */
    public static int ITEM_HEIGHT = 80;
    /**
     * ��ͼ���
     */
    private static _Label label = new _Label();
    /**
     * ����¼���������
     */
    private TMouseListener mouseListenerObject;
    /**
     * ѡ�е���Ŀ
     */
    private TListNode selectedItem;
    /**
     * �������
     */
    private String clickedAction;
    /**
     * ˫������
     */
    private String doubleClickedAction;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ������
     */
    public TListViewBase() {
        uiInit();
    }
    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        nodes = new Vector();
        icons = new HashMap();
        label.setFont(TUIStyle.getListViewDefaultFont());
        setBackground(TUIStyle.getListViewDefaultColor());
        defaultIcon = createImageIcon("Clear.gif");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(scrollBarHeight);
        scrollBarHeight.addAdjustmentListener(this);
        /*TListNode node = new TListNode();
        node.setType("T1");
        node.setName("�Һ�ϵͳ");
        addItem(node);
        node = new TListNode();
        //node.setType("T1");
        node.setName("����ҽ��վ");
        addItem(node);
        node = new TListNode();
        node.setName("סԺϵͳ");
        addItem(node);
        node = new TListNode();
        node.setName("ϵͳ����");
        addItem(node);*/
    }

    /**
     * ���ñ�ǩ
     * @param tag String
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * �õ���ǩ
     * @return String
     */
    public String getTag() {
        return tag;
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
        /*if(actionListenerObject == null)
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
        addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
        addEventListener(getTag() + "->" + TFocusListener.FOCUS_LOST,"onFocusLostAction");*/
        //����Mouse�¼�
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onMouseDoublePressed");
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
            //if ("call".equalsIgnoreCase(message))
            //    message = getText();
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
            //if ("call".equalsIgnoreCase(actionMessage))
            //    actionMessage = getText();
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
        return null;//getText();
    }
    /**
     * �����ı�
     * @param t String
     */
    public void setText(String t)
    {
        //super.setText(t);
        //select(0,0);
        //validate();
    }
    /**
     * �õ���Ŀ����
     * @return int
     */
    public int getItemCount()
    {
        return nodes.size();
    }
    /**
     * ������Ŀ
     * @param node TListNode
     */
    public void addItem(TListNode node)
    {
        nodes.add(node);
    }
    /**
     * �õ���Ŀ
     * @param index int
     * @return TListNode
     */
    public TListNode getItem(int index)
    {
        return (TListNode)nodes.get(index);
    }
    /**
     * ɾ����Ŀ
     * @param index int
     */
    public void removeItem(int index)
    {
        if(selectedItem != null && selectedItem == getItem(index))
            selectedItem = null;
        nodes.remove(index);
    }
    /**
     * ����ͼ���б�
     * @param pics String
     */
    public void setPics(String pics)
    {
        this.pics = pics;
        icons = new HashMap();
        String s[] = StringTool.parseLine(pics,';',"[]{}()");
        for(int i = 0;i < s.length;i++)
        {
            String parm[] = StringTool.parseLine(s[i],':',"[]{}()");
            if(parm.length < 2)
                continue;
            String type = parm[0];
            String iconName = parm[1];
            if(iconName.trim().length() == 0)
                continue;
            Icon icon1 = createImageIcon(iconName);
            Icon icon2 = icon1;
            if(icon1 == null)
                continue;
            if(parm.length >= 3 && parm[2] != null && parm[2].trim().length() > 0)
            {
                icon2 = createImageIcon(parm[2]);
                if(icon2 == null)
                    icon2 = icon1;
            }
            icons.put(type,icon1);
            icons.put(type + "_E",icon2);
        }
    }
    /**
     * �õ�ͼ���б�
     * @return String
     */
    public String getPics()
    {
        return pics;
    }
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        //���Ʊ���
        paintBack(g);
        super.paint(g);
        //�������
        paintItems(g);
    }
    /**
     * ���Ʊ���
     * @param g Graphics
     */
    public void paintBack(Graphics g)
    {
        Rectangle rectangel = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(rectangel.x,rectangel.y,rectangel.width,rectangel.height);
    }
    /**
     * �������
     * @param g Graphics
     */
    public void paintItems(Graphics g)
    {
        gotoItems();
        int count = getItemCount();
        Insets insets = getInsets();
        g = g.create(insets.left,insets.top,getWidth() - insets.right,getHeight() - insets.bottom);
        Rectangle rectangel = g.getClipBounds();
        for(int i = 0;i < count;i++)
        {
            TListNode node = getItem(i);
            if(node.getX() + ITEM_WIDTH < rectangel.getX() ||
               node.getX() > rectangel.getX() + rectangel.getWidth() ||
               node.getY() + ITEM_HEIGHT < rectangel.getY() + scrollBarValue||
               node.getY() > rectangel.getY() + scrollBarValue + rectangel.getHeight())
                continue;
            //�������
            int x = node.getX();
            int y = node.getY() - scrollBarValue;
            int width = ITEM_WIDTH;
            int height = ITEM_HEIGHT;
            paintItem(g.create(x,y,width,height),node);

        }
    }
    /**
     * �������
     * @param g Graphics
     * @param node TListNode
     */
    public void paintItem(Graphics g,TListNode node)
    {
        String type = node.getType();
        Icon icon = (Icon)icons.get(type);
        if(icon == null)
            icon = defaultIcon;
        label.setSelected(false);
        label.setIcon(icon);
        label.setText(null);
        label.setSize(ITEM_WIDTH,ITEM_HEIGHT);
        label.paint(g);
        Graphics g1 = g.create(0,ITEM_HEIGHT - 14,ITEM_WIDTH,14);
        label.setIcon(null);
        label.setSize(ITEM_WIDTH,14);
        label.setText(node.getName());
        if(selectedItem == node)
            label.setSelected(selectedItem == node);
        label.paint(g1);
        //g.setColor(new Color(255,0,0));
        //g.drawRect(0,0,ITEM_WIDTH - 1,ITEM_HEIGHT -1);
    }
    /**
     * ����ͼƬ
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
        if(TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" + filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if(icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try{
            icon = new ImageIcon(getClass().getResource(path));
        }catch(NullPointerException e)
        {
            err("û���ҵ�ͼ��" + path);
        }
        return icon;
    }
    /**
     * �ڷ����
     */
    public void gotoItems()
    {
        int count = getItemCount();
        Insets insets = getInsets();
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        int x = 0;
        int y = 0;
        for(int i = 0;i < count; i++)
        {
            TListNode node = getItem(i);
            node.setX(x);
            node.setY(y);
            x += ITEM_WIDTH;
            if(x + ITEM_WIDTH > width - 18)
            {
                x = 0;
                y += ITEM_HEIGHT;
            }
        }
        if(y < height)
        {
            scrollBarHeight.setVisible(false);
            scrollBarValue = 0;
        }
        else
        {
            scrollBarHeight.setLocation(getWidth() - 18 - insets.right,insets.top);
            scrollBarHeight.setSize(18,getHeight() - insets.top - insets.bottom);
            scrollBarHeight.setMaximum(y - getHeight() + insets.top + insets.bottom + 18);
            scrollBarHeight.setVisible(true);
            scrollBarHeight.validate();
        }
    }
    /**
     * ��������ֵ�ı�
     * @param e AdjustmentEvent
     */
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        if (e.getSource() == scrollBarHeight)
            scrollBarValue = e.getValue();
        repaint();
    }
    /**
     * ���ñ߿�
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        TListNode node = getNodeForLocation(e.getX(),e.getY());
        if(node == null)
            return;
        selectedItem = node;
        repaint();
        callEvent(getTag() + "->" + TListViewEvent.CLICKED,new Object[]{node},new String[]{"com.dongyang.ui.TListNode"});
        //callMessage(getTag() + "->" + TListViewEvent.CLICKED,node);
        exeAction(getClickedAction());
    }
    /**
     * ���˫��
     * @param e MouseEvent
     */
    public void onMouseDoublePressed(MouseEvent e)
    {
        TListNode node = getNodeForLocation(e.getX(),e.getY());
        if(node == null)
            return;
        selectedItem = node;
        repaint();
        callEvent(getTag() + "->" + TListViewEvent.DOUBLE_CLICKED,new Object[]{node},new String[]{"com.dongyang.ui.TListNode"});
        //callMessage(getTag() + "->" + TListViewEvent.DOUBLE_CLICKED,node);
        exeAction(getDoubleClickedAction());
    }
    /**
     * ����ѡ����Ŀ
     * @param selectedItem TListNode
     */
    public void setSelectedItem(TListNode selectedItem)
    {
        this.selectedItem = selectedItem;
    }
    /**
     * �õ�ѡ����Ŀ
     * @return TListNode
     */
    public TListNode getSelectedItem()
    {
        return selectedItem;
    }
    /**
     * ���������ѯ��Ŀ
     * @param x int
     * @param y int
     * @return TListNode
     */
    public TListNode getNodeForLocation(int x,int y)
    {
        Insets insets = getInsets();
        x += insets.left;
        y += scrollBarValue + insets.top;
        int count = getItemCount();
        for(int i = 0;i < count;i++)
        {
            TListNode node = getItem(i);
            if(x > node.getX() && x < node.getX() + ITEM_WIDTH &&
               y > node.getY() && y < node.getY() + ITEM_HEIGHT)
                return node;
        }
        return null;
    }
    private static class _Label extends JLabel
    {
        private boolean selected;
        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
        public boolean isSelected()
        {
            return selected;
        }
        /**
         * ����
         * @param g Graphics
         */
        public void paint(Graphics g)
        {
            if(!isSelected())
            {
                super.paint(g);
                return;
            }
            g.setColor(new Color(49,106,197));
            g.fillRect(0,0,getWidth(),getHeight());
            Color c = getForeground();
            setForeground(new Color(255,255,255));
            super.paint(g);
            setForeground(c);
        }
    }
    /**
     * ���õ������
     * @param clickedAction String
     */
    public void setClickedAction(String clickedAction)
    {
        this.clickedAction = clickedAction;
    }
    /**
     * �õ��������
     * @return String
     */
    public String getClickedAction()
    {
        return clickedAction;
    }
    /**
     * ����˫������
     * @param doubleClickedAction String
     */
    public void setDoubleClickedAction(String doubleClickedAction)
    {
        this.doubleClickedAction = doubleClickedAction;
    }
    /**
     * �õ�˫������
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return doubleClickedAction;
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
}
