package com.dongyang.ui.base;

import com.dongyang.ui.TComponent;
import javax.swing.JComponent;
import com.dongyang.ui.event.TMouseListener;
import javax.swing.ImageIcon;
import com.dongyang.util.FileTool;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import java.awt.Container;
import com.dongyang.ui.event.BaseEvent;
import java.awt.event.MouseEvent;
import com.dongyang.util.RunClass;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.ui.event.TMouseMotionListener;
import com.dongyang.ui.event.TComponentListener;
import com.dongyang.util.StringTool;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.Font;
import com.dongyang.ui.datawindow.TextLabel;
import com.dongyang.ui.datawindow.DataWindowUI;
import com.dongyang.ui.datawindow.DataWindowPriviewUI;
import com.dongyang.config.TNode;
import com.dongyang.config.INode;
import java.awt.print.Paper;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import com.dongyang.data.TParm;
import com.dongyang.ui.datawindow.PageData;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import com.dongyang.ui.datawindow.DataStore;
import com.dongyang.ui.datawindow.PageDialog;
import com.dongyang.ui.datawindow.ConfigDataWindowFile;

public class TDataWindowBase extends JComponent implements TComponent,
        AdjustmentListener
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
     * �ı�
     */
    private String text;
    /**
     * ����¼���������
     */
    TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    TMouseMotionListener mouseMotionListenerObject;
    /**
     * �����������
     */
    private TComponentListener componentListenerObject;
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
     * ���������
     */
    JScrollBar scrollBarWidth = new JScrollBar();
    /**
     * ���������
     */
    JScrollBar scrollBarHeight = new JScrollBar();
    /**
     * �������λ��
     */
    private int scrollWidthValue;
    /**
     * �������λ��
     */
    private int scrollHeightValue;
    /**
     * ���ں��������
     */
    private boolean hasScrollBarWidth = true;
    /**
     * �������������
     */
    private boolean hasScrollBarHeight = true;
    /**
     * �������ߴ�
     */
    private int scrollBarLength = 17;
    /**
     * ����
     */
    private String title = "���ݴ���";
    /**
     * ����������ɫ
     */
    private Color titleTextColor = new Color(255, 255, 255);
    /**
     * ���ⱳ����ɫ
     */
    private Color titleBkColor = new Color(0, 0, 255);
    /**
     * ��������
     */
    private Font titleFont = new Font("����", 0, 14);
    /**
     * ����߶�
     */
    private int titleHeight = 20;
    /**
     * �Ƿ��б���
     */
    private boolean hasTitle = true;
    /**
     * UI
     */
    private DataWindowUI datawindowui = new DataWindowUI(this);
    /**
     * ��ӡ UI
     */
    private DataWindowPriviewUI datawindowpriviewui = new DataWindowPriviewUI(this);
    /**
     * ��ǰ�Ƿ�������״̬
     */
    private boolean preview = true;
    /**
     * �ļ���
     */
    private String fileName;
    /**
     * XML����
     */
    private INode nodeData;
    /**
     * ��ӡ������
     */
    private int printType;
    /**
     * ��ǰҳ��
     */
    private int page;
    /**
     * ��ǰ��ӡ��ҳ������,�� "1,3,4,5,10 to 20"
     */
    private String printPageID;
    /**
     * �����
     */
    private TParm parmValue;
    /**
     * ��ʱ��ҳ�����ݰ�
     */
    private PageData tempPageData;
    /**
     * �����ݴ���
     */
    private Map childDataWindows = new HashMap();
    private DataStore datastore = new DataStore(this);
    /**
     * xml�����豸
     */
    private ConfigDataWindowFile configXML = new ConfigDataWindowFile();
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     *  ������
     */
    public TDataWindowBase()
    {
        uiInit();
    }

    /**
     * ��ӡ��ǰҳ
     */
    public final static int PRINT_CURRENT_PAGE = 1;
    /**
     * ��ӡ�Զ���ҳ
     */
    public final static int PRINT_CUSTOM_PAGE = 2;

    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit()
    {
        scrollBarWidth.setOrientation(JScrollBar.HORIZONTAL);
        add(scrollBarWidth);
        add(scrollBarHeight);
        scrollBarWidth.addAdjustmentListener(this);
        scrollBarHeight.addAdjustmentListener(this);
    }

    /**
     * Returns the button's tag.
     * @return the buttons tag
     * @see #setTag
     */
    public String getTag()
    {
        return tag;
    }

    /**
     * Sets the button's tag.
     * @param tag the string used to set the tag
     * @see #getTag
     *  description: The button's tag.
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
        if (loadtag != null)
            return loadtag;
        return getTag();
    }

    /**
     * ����X����
     * @param x int
     */
    public void setX(int x)
    {
        this.setLocation(x, getLocation().y);
    }

    /**
     * ����Y����
     * @param y int
     */
    public void setY(int y)
    {
        this.setLocation(getLocation().x, y);
    }

    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        this.setSize(width, getSize().height);
    }

    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.setSize(getSize().width, height);
    }

    /**
     * X����ƫ��
     * @param d int
     */
    public void setX$(int d)
    {
        if (d == 0)
            return;
        setX(getX() + d);
    }

    /**
     * Y����ƫ��
     * @param d int
     */
    public void setY$(int d)
    {
        if (d == 0)
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
        setWidth$( -d);
    }

    /**
     * ��������
     * @param d int
     */
    public void setY_$(int d)
    {
        setY$(d);
        setHeight$( -d);
    }

    /**
     * �������ƫ��
     * @param d int
     */
    public void setWidth$(int d)
    {
        if (d == 0)
            return;
        setWidth(getWidth() + d);
    }

    /**
     * �߶�����ƫ��
     * @param d int
     */
    public void setHeight$(int d)
    {
        if (d == 0)
            return;
        setHeight(getHeight() + d);
    }

    /**
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control)
    {
        this.control = control;
        if (control != null)
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
     * ����ͼƬ
     * @param filename String
     * @return ImageIcon
     */
    public ImageIcon createImageIcon(String filename)
    {
        if (TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" +
                                           filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if (icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try
        {
            icon = new ImageIcon(getClass().getResource(path));
        } catch (NullPointerException e)
        {
            err("û���ҵ�ͼ��" + path);
        }
        return icon;
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
    public void addEventListener(String eventName, String methodName)
    {
        addEventListener(eventName, this, methodName);
    }

    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName)
    {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName)
    {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType)
    {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName)
    {
        return callEvent(eventName, new Object[]
                         {}, new String[]
                         {});
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName, Object parm)
    {
        return callEvent(eventName, new Object[]
                         {parm}, new String[]
                         {"java.lang.Object"});
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
        if (componentListenerObject == null)
        {
            //��������¼�
            componentListenerObject = new TComponentListener(this);
            addComponentListener(componentListenerObject);
        }
        //��������¼�
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_HIDDEN,
                         "onComponentHidden");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_MOVED,
                         "onComponentMoved");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_RESIZED,
                         "onComponentResized");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_SHOWN,
                         "onComponentShown");
        //����Mouse�¼�
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,
                         "onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,
                         "onMouseReleased");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,
                         "onMouseEntered");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,
                         "onMouseExited");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,
                         "onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,
                         "onMouseMoved");
        TComponent component = getParentTComponent();
        if (component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" +
                                   TComponentListener.RESIZED,
                                   this, "onParentResize");
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
        resizeScrollBar();
        int width = getWidth();
        int height = getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED, new Object[]
                  {width, height}, new String[]
                  {"int", "int"});
    }

    /**
     * �����ʾ�¼�
     * @param e ComponentEvent
     */
    public void onComponentShown(ComponentEvent e)
    {
        resizeScrollBar();
        int width = getWidth();
        int height = getHeight();
        callEvent(getTag() + "->" + TComponentListener.RESIZED, new Object[]
                  {width, height}, new String[]
                  {"int", "int"});
    }

    /**
     * �������ߴ�ı�
     * @param width int
     * @param height int
     */
    public void onParentResize(int width, int height)
    {
        if (isAutoX())
            setX(getAutoSize());
        if (isAutoY())
            setY(getAutoSize());
        if (isAutoWidth())
            setWidth(width - getX() - getAutoSize());
        if (isAutoHeight())
            setHeight(height - getY() - getAutoSize());
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
     * ��ʼ��
     */
    public void onInit()
    {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        if (getControl() != null)
            getControl().onInit();
        resizeScrollBar();
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
    protected boolean filterInit(String message)
    {
        return true;
    }

    /**
     * ����˳��
     * @return String
     */
    protected String getDownLoadIndex()
    {
        return "ControlClassName,ControlConfig";
    }

    /**
     * ���з���
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message, Object ...parameters)
    {
        return callMessage(message, parameters);
    }

    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @return Object
     */
    public Object callMessage(String message)
    {
        return callMessage(message, null);
    }

    /**
     * ������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm)
    {
        if (message == null || message.length() == 0)
            return null;
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        if (getControl() != null)
        {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        TComponent parentTComponent = getParentTComponent();
        if (parentTComponent != null)
        {
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
    protected Object onBaseMessage(String message, Object parm)
    {
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
    protected void baseFieldNameChange(String value[])
    {
        if ("pic".equalsIgnoreCase(value[0]))
            value[0] = "pictureName";
    }

    /**
     * �����������
     * @param value String
     */
    public void setControlConfig(String value)
    {
        if (getControl() == null)
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
        if(controlClassName == null || controlClassName.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(value);
        if (obj == null)
        {
            err("Class loadObject err className=" + value);
            return;
        }
        if (!(obj instanceof TControl))
        {
            err("Class loadObject type err className=" + value +
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
        return (TComponent) callMessage(tag + "|getThis");
    }

    /**
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBKColor(String color)
    {
        if (color == null || color.length() == 0)
            setBackground(null);
        else
            setBackground(StringTool.getColor(color, getConfigParm()));
        repaint();
    }

    /**
     * ����������ɫ
     * @param color String
     */
    public void setColor(String color)
    {
        if (color == null || color.length() == 0)
            return;
        this.setForeground(StringTool.getColor(color, getConfigParm()));
    }

    /**
     * ���ñ߿�
     * @param border String
     */
    public void setBorder(String border)
    {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }

    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TLabelBase";
    }

    /**
     * �����Զ�X
     * @param autoX boolean
     */
    public void setAutoX(boolean autoX)
    {
        this.autoX = autoX;
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
     * ����ֵ
     * @param value String
     */
    public void setValue(String value)
    {
        //setText(value);
    }

    /**
     * �õ�ֵ
     * @return String
     */
    public String getValue()
    {
        return ""; //getText();
    }

    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        return text;
    }

    /**
     * �������ͼ�ν���
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        int titleHeight = isHasTitle() ? getTitleHeight() : 0;
        int x = getBorderLength();
        int y = getBorderLength() + titleHeight;
        int width = (hasScrollBarHeight ? getWidth() - scrollBarLength :
                     getWidth()) -
                    getBorderLength() * 2;
        int height = (hasScrollBarWidth ? getHeight() - scrollBarLength :
                      getHeight()) -
                     getBorderLength() * 2 - titleHeight;

        if (isInit())
        {
            if (isPreview())
            {
                datawindowpriviewui.paint(g.create(x, y, width, height),
                                          getPageFormat(),
                                          width, height);
            } else
            {
                if (getBackground() != null)
                {
                    g.setColor(getBackground());
                    g.fillRect(x, y, getWidth(), getHeight());
                }
                datawindowui.paint(g.create(x, y, width, height), width, height);
            }
        } else
        {
            if (getBackground() != null)
            {
                g.setColor(getBackground());
                g.fillRect(x, y, getWidth(), getHeight());
            }
        }
        if (isHasTitle())
            paintTitle(g.create(getBorderLength(), getBorderLength(),
                                getWidth() - getBorderLength() * 2, titleHeight),
                       getWidth() - getBorderLength() * 2, titleHeight);
        super.paint(g);
    }

    /**
     * ���Ʊ���ͼ��ҳ��
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintTitle(Graphics g, int width, int height)
    {
        if (titleBkColor != null)
        {
            g.setColor(titleBkColor);
            g.fillRect(0, 0, width, height);
        }
        TextLabel label = datawindowui.getLabel();
        label.setText(getTitle());
        label.setFont(getTitleFont());
        label.setForeground(getTitleTextColor());
        label.setSize(width, height);
        label.setTransparence(true);
        label.paint(g.create(5, 0, width - 5, height));
    }

    /**
     * �Ƿ��Ѿ���ʼ��
     * @return boolean true ��ʼ�� false û�г�ʼ��
     */
    public boolean isInit()
    {
        return getNodeData() != null;
    }

    /**
     * ���õ�ǰ�Ƿ�������״̬
     * @param preview boolean true ������״̬ false ��������״̬
     */
    public void setPreview(boolean preview)
    {
        this.preview = preview;
        repaint();
    }

    /**
     * �õ���ǰ�Ƿ��Ǵ�ӡ����״̬
     * @return boolean true ������״̬ false ��������״̬
     */
    public boolean isPreview()
    {
        return preview;
    }

    /**
     * ��������ֵ�ı�
     * @param e AdjustmentEvent
     */
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        if (e.getSource() == scrollBarWidth)
            scrollWidthValue = e.getValue();
        else if (e.getSource() == scrollBarHeight)
            scrollHeightValue = e.getValue();
        repaint();
    }

    /**
     * �õ������������λ��
     * @return int
     */
    public int getScrollWidthValue()
    {
        return scrollWidthValue;
    }

    /**
     * �õ������������λ��
     * @return int
     */
    public int getScrollHeightValue()
    {
        return scrollHeightValue;
    }

    /**
     * ���ڹ������ڴ����е�λ��
     */
    private void resizeScrollBar()
    {
        if (hasScrollBarWidth)
        {
            scrollBarWidth.setBounds(new Rectangle(getBorderLength(),
                    getHeight() - scrollBarLength -
                    getBorderLength(),
                    (hasScrollBarHeight ?
                     getWidth() - scrollBarLength :
                     getWidth()) -
                    getBorderLength() * 2,
                    scrollBarLength));
            if (!scrollBarWidth.isVisible())
                scrollBarWidth.setVisible(true);
            scrollBarWidth.validate();
            scrollBarWidth.repaint();
        } else if (scrollBarWidth.isVisible())
        {
            scrollBarWidth.setVisible(false);
            scrollBarWidth.validate();
            scrollBarWidth.repaint();
        }
        if (hasScrollBarHeight)
        {
            int titleHeight = isHasTitle() ? getTitleHeight() : 0;
            scrollBarHeight.setBounds(new Rectangle(getWidth() -
                    scrollBarLength -
                    getBorderLength(),
                    getBorderLength() + titleHeight,
                    scrollBarLength,
                    (hasScrollBarWidth ?
                     getHeight() - scrollBarLength :
                     getHeight()) -
                    getBorderLength() * 2 -
                    titleHeight));
            if (!scrollBarHeight.isVisible())
                scrollBarHeight.setVisible(true);
            scrollBarHeight.validate();
            scrollBarHeight.repaint();
        } else if (scrollBarHeight.isVisible())
        {
            scrollBarHeight.setVisible(false);
            scrollBarHeight.validate();
            scrollBarHeight.repaint();
        }

        if (hasScrollBarWidth || hasScrollBarHeight)
            this.updateUI();
    }

    /**
     * �õ��߿�ߴ�
     * @return int
     */
    private int getBorderLength()
    {
        if (getBorder() == null)
            return 0;
        return 2;
    }

    /**
     * �Ƿ��б���
     * @return boolean
     */
    public boolean isHasTitle()
    {
        return hasTitle;
    }

    /**
     * �����Ƿ���ʾ����
     * @param fag true ��ʾ false ����ʾ
     */
    public void setHasTitle(boolean fag)
    {
        hasTitle = fag;
        resizeScrollBar();
        repaint();
    }

    /**
     * �õ�����߶�
     * @return int
     */
    public int getTitleHeight()
    {
        return titleHeight;
    }

    /**
     * ���ñ���߶�
     * @param height �߶�
     */
    public void setTitleHeight(int height)
    {
        titleHeight = height;
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
     * ���ñ�������
     * @param title String
     */
    public void setTitle(String title)
    {
        this.title = title;
        repaint();
    }

    /**
     * �õ�����������ɫ
     * @return Color
     */
    public Color getTitleTextColor()
    {
        return titleTextColor;
    }

    /**
     * ���ñ���������ɫ
     * @param c Color
     */
    public void setTitleTextColor(Color c)
    {
        titleTextColor = c;
    }

    /**
     * �õ����ⱳ����ɫ
     * @return Color
     */
    public Color getTitleBkColor()
    {
        return titleBkColor;
    }

    /**
     * ���ñ��ⱳ����ɫ
     * @param color String
     */
    public void setTitleBKColor(String color)
    {
        if (color == null || color.length() == 0)
            setTitleBkColor(null);
        else
            setTitleBkColor(StringTool.getColor(color, getConfigParm()));
        repaint();
    }

    /**
     * ���ñ��ⱳ����ɫ
     * @param c Color
     */
    public void setTitleBkColor(Color c)
    {
        titleBkColor = c;
    }

    /**
     * �õ�������������
     * @return Font
     */
    public Font getTitleFont()
    {
        return titleFont;
    }

    /**
     * ���ñ�����������
     * @param f Font
     */
    public void setTitleFont(Font f)
    {
        titleFont = f;
    }

    /**
     * �����ļ���
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        if (fileName == null || fileName.equalsIgnoreCase(this.fileName))
            return;
        this.fileName = fileName;
        loadFile();
    }

    /**
     * �õ��ļ���
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * �����ļ�
     */
    public void loadFile()
    {
        setNodeData(null);
        byte[] data = TIOM_AppServer.readFile(getFileName());
        if (data == null)
            return;
        setNodeData(TNode.loadXML(data));
    }
    /**
     * ��������
     * @param data String
     */
    public void loadString(String data)
    {
        setNodeData(TNode.loadXML(data));
    }
    /**
     * ����XML����
     * @param nodeData INode
     */
    public void setNodeData(INode nodeData)
    {
        this.nodeData = nodeData;
        datastore.load(nodeData);
    }

    /**
     * �õ�XML����
     * @return INode
     */
    public INode getNodeData()
    {
        return nodeData;
    }

    /**
     * �õ���ӡ��XML����
     * @return INode
     */
    public INode getPrintNode()
    {
        if (getNodeData() == null)
            return null;
        return getNodeData().getChildElement("Print");
    }
    public INode getXML(String name)
    {
        if (getNodeData() == null)
            return null;
        return getNodeData().getChildElement(name);
    }

    /**
     * �õ���ӡҳ��ߴ�
     * @return Paper
     */
    public Paper getPaper()
    {
        INode print = getPrintNode();
        if (print == null)
            return null;
        Paper paper = new Paper();
        double x = print.getAttributeValueAsDouble("ImageableX");
        double y = print.getAttributeValueAsDouble("ImageableY");
        double w = print.getAttributeValueAsDouble("ImageableWidth");
        double h = print.getAttributeValueAsDouble("ImageableHeight");
        double width = print.getAttributeValueAsDouble("Width");
        double height = print.getAttributeValueAsDouble("Height");
        switch (print.getAttributeValueAsInteger("Orientation"))
        {
        case PageFormat.PORTRAIT: //����
            paper.setImageableArea(x, y, w, h);
            paper.setSize(width, height);
            break;
        case PageFormat.REVERSE_LANDSCAPE: //����
            paper.setImageableArea(y, x, h, w);
            paper.setSize(height, width);
            break;
        }
        return paper;
    }

    /**
     * �õ���ӡ��ʽ����
     * @return PageFormat
     */
    private PageFormat getPageFormat()
    {
        INode print = getPrintNode();
        if (print == null)
            return null;
        Paper paper = getPaper();
        if (paper == null)
            return null;
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(print.getAttributeValueAsInteger(
                "Orientation"));
        return pageFormat;
    }

    /**
     * ��ӡ
     * @return boolean
     */
    public boolean print()
    {
        return print(PrinterJob.getPrinterJob());
    }

    /**
     * ��ӡ
     * @param printService PrintService
     * @return boolean
     */
    public boolean print(PrintService printService)
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        try
        {
            printJob.setPrintService(printService);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return print(printJob);
    }

    /**
     * ���õ�ǰ��ӡ������
     * @param type int
     */
    public void setPrintType(int type)
    {
        printType = type;
    }

    /**
     * �õ���ǰ��ӡ������
     * @return int
     */
    public int getPrintType()
    {
        return printType;
    }

    /**
     * ���õ�ǰҳ��
     * @param page int
     */
    public void setPage(int page)
    {
        this.page = page;
    }

    /**
     * �õ���ǰҳ��
     * @return int
     */
    public int getPage()
    {
        return page;
    }

    /**
     * ���õ�ǰ��ӡ��ҳ������
     * @param id int
     */
    public void setPrintPageID(String id)
    {
        printPageID = id;
    }

    /**
     * �õ���ǰ��ӡ��ҳ������,�� "1,3,4,5,10 to 20"
     * @return String
     */
    public String getPrintPageID()
    {
        return printPageID;
    }

    /**
     * ��ӡ
     * @param printJob PrinterJob
     * @return boolean
     */
    public boolean print(PrinterJob printJob)
    {
        switch (getPrintType())
        {
        case PRINT_CURRENT_PAGE:
            if (!datawindowpriviewui.initPrintPageList("" + getPage()))
                return false;
        case PRINT_CUSTOM_PAGE:
            if (getPrintPageID() == null || getPrintPageID().length() == 0)
                return false;
            if (!datawindowpriviewui.initPrintPageList(getPrintPageID()))
                return false;
        }
        PageFormat pageFormat = getPageFormat();
        printJob.setJobName("DataWindow Print");
        printJob.setPrintable(datawindowpriviewui, pageFormat);
        printJob.setPageable(new TPageable(pageFormat, datawindowpriviewui));
        try
        {
            printJob.print();
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * �õ������
     * @return int
     */
    public int getGroupCount()
    {
        if (getNodeData() == null)
            return 0;
        int count = getNodeData().size();
        int n = 0;
        for(int i = 0;i < count;i ++)
        {
            INode node = getNodeData().getChildElement(i);
            if(!"Group".equalsIgnoreCase(node.getName()))
                continue;
            n ++;
        }
        return n;
    }
    /**
     * �õ���XML����
     * @param index int ��λ��
     * @return INode
     */
    public INode getGroupNode(int index)
    {
        if (getNodeData() == null)
            return null;
        int count = getNodeData().size();
        int n = 0;
        for(int i = 0;i < count;i ++)
        {
            INode node = getNodeData().getChildElement(i);
            if(!"Group".equalsIgnoreCase(node.getName()))
                continue;
            if(index == n)
                return node;
            n ++;
        }
        return null;
    }
    /**
     * �õ���XML����
     * @param name String ����
     * @return INode
     */
    public INode getGroupNode(String name)
    {
        if (getNodeData() == null || name == null)
            return null;
        int count = getNodeData().size();
        for(int i = 0;i < count;i ++)
        {
            INode node = getNodeData().getChildElement(i);
            if(!"Group".equalsIgnoreCase(node.getName()))
                continue;
            if(name.equalsIgnoreCase(node.getAttributeValue("name")))
                return node;
        }
        return null;
    }
    /**
     * ����Parm����
     * @param parmValue TParm
     */
    public void setParmValue(TParm parmValue)
    {
        this.parmValue = parmValue;
    }
    /**
     * �õ�Parm����
     * @return TParm
     */
    public TParm getParmValue()
    {
        return parmValue;
    }
    /**
     *
     * <p>Title: ��ӡҳ���Զ���</p>
     *
     * <p>Description: </p>
     *
     * <p>Copyright: Copyright (c) 2008</p>
     *
     * <p>Company: JavaHis</p>
     *
     * @author lzk 2008.08.30
     * @version 1.0
     */
    class TPageable implements Pageable
    {
        private PageFormat mFormat;
        private Printable mPainter;

        TPageable(PageFormat pageformat, Printable printable)
        {
            mFormat = pageformat;
            mPainter = printable;
        }

        public int getNumberOfPages()
        {
            return -1;
        }

        public PageFormat getPageFormat(int i)
        {
            return mFormat;
        }

        public Printable getPrintable(int i) throws IndexOutOfBoundsException
        {
            return mPainter;
        }

    }

    /**
     * �õ���ҳ��
     * @return int
     */
    public int getPageCount()
    {
      tempPageData = new PageData();
      tempPageData.setZoom(getPrintZoom() / 100.0);
      tempPageData.setHeight((int)getImageableHeight());
      tempPageData.setWidth((int)getImageableWidth());
      tempPageData.newPage();
      int count = getPageCount(tempPageData);
      tempPageData.closePage();
      return count;
    }
    public int getPageCount(PageData pageData)
    {
      pageData.addHeader(this,getAreaHeight("Header",pageData.getZoom()));
      pageData.addFooter(this,getAreaHeight("Footer",pageData.getZoom()));
      for(int row = 0;row < getDataStore().rowCount();row ++)
      {
        int height = getAreaHeight("Detail",row,pageData.getZoom());
        int detailHeight = (int)(getXML("Detail").getAttributeValueAsDouble("height").
                                 doubleValue() * pageData.getZoom());
        List list = getChildDataWindowList("Detail");
        if(!pageData.userHeight(this,"Detail",row,detailHeight,false))
        {
          pageData.newPage();
          if (!pageData.userHeight(this, "Detail", row, detailHeight,false))
            return pageData.getPageCount();
        }
        if(list == null)
        {
          if(pageData.userHeight(this,"Detail",row,height))
            continue;
          pageData.newPage();
          if(!pageData.userHeight(this,"Detail",row,height))
            return pageData.getPageCount();
          continue;
        }
        int oldy = 0;
        for(int i = 0;i < list.size();i++)
        {
          TNode childDataWindowXML = getChildDataWindowXML((String)list.get(i),"Detail");
          String name = childDataWindowXML.getAttributeValue("name");
          int x = (int)(childDataWindowXML.getAttributeValueAsDouble("x").doubleValue() * pageData.getZoom());
          int y = (int)(childDataWindowXML.getAttributeValueAsDouble("y").doubleValue() * pageData.getZoom());
          int w = (int)(childDataWindowXML.getAttributeValueAsDouble("width").doubleValue() * pageData.getZoom());
          int h = (int)(childDataWindowXML.getAttributeValueAsDouble("height").doubleValue() * pageData.getZoom());
          pageData.moveHeight(y - oldy);
          oldy = y + h;
          pageData.addPosition(x,w);
          TDataWindowBase childDataWindow = getChildDataWindow(name);
          if(childDataWindow == null)
            continue;
          childDataWindow.getPageCount(pageData);
          pageData.pop();
        }
        pageData.moveHeight(detailHeight - oldy);
      }
      return pageData.getPageCount();
    }
    public List getChildDataWindowList(String area)
    {
      if (getXML("childdatawindows") == null)
        return null;
      Vector list = new Vector();
      Vector listY = new Vector();
      Iterator iterator = getXML("childdatawindows").getChildElements();
      while (iterator.hasNext())
      {
        TNode childDataWindow = (TNode) iterator.next();
        if (!childDataWindow.getAttributeValue("band").equals(area))
          continue;
        String name = childDataWindow.getAttributeValue("name");
        int y = childDataWindow.getAttributeValueAsInteger("y").intValue();
        boolean isSelected = false;
        FOR:
        for(int i = 0;i < listY.size();i++)
          if(((Integer)listY.get(i)).intValue() > y)
          {
            list.insertElementAt(name,i);
            listY.insertElementAt(new Integer(y),i);
            isSelected = true;
            break FOR;
          }
        if(!isSelected)
        {
          list.add(name);
          listY.add(new Integer(y));
        }
      }
      return list;
    }
    public TNode getChildDataWindowXML(String name,String area)
    {
      if (getXML("childdatawindows") == null)
        return null;
      Iterator iterator = getXML("childdatawindows").getChildElements();
      while (iterator.hasNext())
      {
        TNode childDataWindow = (TNode) iterator.next();
        if (!childDataWindow.getAttributeValue("band").equals(area))
          continue;
        if(childDataWindow.getAttributeValue("name").equals(name))
          return childDataWindow;
      }
      return null;
    }
    /**
     * ����ĳһ������ռ�õĸ߶�
     * @param area String ��������
     * @param zoom double
     * @return int
     */
    public int getAreaHeight(String area,double zoom)
    {
      if(area.equals("Detail"))
        return getAreaDetailHeight(zoom);
      return getAreaHeight(area,0,zoom);
    }
    /**
     * ����������ռ�õ�ȫ���ĸ߶�
     * @param zoom double
     * @return int
     */
    private int getAreaDetailHeight(double zoom)
    {
      int height = (int)(getXML("Detail").getAttributeValueAsDouble("height").
          doubleValue() * zoom);
      if (getXML("childdatawindows") == null)
        return height * rowCount();
      moveCancelAllChildDataWindows("Detail");
      height = 0;
      for (int row = 0; row < rowCount(); row++)
        height += getAreaHeight("Detail", row,zoom);
      return height;
    }
    /**
     * ����ĳһ������ռ�õĸ߶� ��������
     * @param area String
     * @param row int �����к�
     * @param zoom double
     * @return int
     */
    public int getAreaHeight(String area,int row,double zoom)
    {
      int height = (int)(getXML(area).getAttributeValueAsDouble("height").
          doubleValue() * zoom);
      if(getXML("childdatawindows") == null)
        return height;
      moveCancelAllChildDataWindows(area);
      Iterator iterator = getXML("childdatawindows").getChildElements();
      while (iterator.hasNext())
      {
        TNode childDataWindow = (TNode)iterator.next();
        if (childDataWindow.getAttributeValue("band").equals(area))
        {
          String name = childDataWindow.getAttributeValue("name");
          int y = (int)(childDataWindow.getAttributeValueAsDouble("y").doubleValue() * zoom);
          int h = (int)(childDataWindow.getAttributeValueAsDouble("height").doubleValue() * zoom);
          int ch = getChildDataWindowHeight(name,row,zoom);
          if(ch > h)
          {
            height = height - h + ch;
            moveAll(name,y,ch - h,area,zoom);
          }
        }
      }
      return height;
    }
    /**
     * ���������ƶ�ȫ�������ƥ���λ��
     * @param name String ���������ݴ�������
     * @param y int ����y����
     * @param h int �ƶ��߶�
     * @param area String ����
     * @param zoom double
     */
    private void moveAll(String name,int y,int h,String area,double zoom)
    {
      //���������ƶ�ȫ���������ݴ��ڵ�ƥ���λ��
      moveAllChildDataWindows(name,y,h,area,zoom);
    }
    /**
     * ���������ƶ�ȫ���������ݴ��ڵ�ƥ���λ��
     * @param name String ���������ݴ�������
     * @param y int ����y����
     * @param h int �ƶ��߶�
     * @param area String ����
     * @param zoom double
     */
    private void moveAllChildDataWindows(String name,int y,int h,String area,double zoom)
    {
      if(getXML("childdatawindows") == null)
        return;
      Iterator iterator = getXML("childdatawindows").getChildElements();
      while (iterator.hasNext())
      {
        TNode childDataWindow = (TNode) iterator.next();
        if (!childDataWindow.getAttributeValue("band").equals(area))
          continue;
        String dataWindowName = childDataWindow.getAttributeValue("name");
        if(dataWindowName.equals(name))
          continue;
        int cy = (int)(childDataWindow.getAttributeValueAsDouble("y").doubleValue() * zoom);
        if(cy <= y)
          continue;
        TDataWindowBase childdatawindow = getChildDataWindow(dataWindowName);
        if(childdatawindow == null)
          continue;
        //childdatawindow.getUI().setChildHeight(h + childdatawindow.getUI().getChildHeight());
      }
    }
    /**
     * �õ���ĳһ���ϵ������ݴ��ڸ߶�
     * @param name String �����ݴ�������
     * @param row int �к�
     * @param zoom double
     * @return int
     */
    public int getChildDataWindowHeight(String name,int row,double zoom)
    {
      /*TDataWindowBase childdatawindow = getChildDataWindow(name);
      if(childdatawindow == null)
        return 0;
      TParm parm = (TParm)getDataStore().getItemData(row,name);
      parm = getTestParm(name,row);
      if(parm != null)
        childdatawindow.getDataStore().setData(parm);
      return childdatawindow.getUIHeight(zoom);*/
        return -1;
    }
    public TParm getTestParm(String name,int row)
    {
      if(!"dw2".equals(name))
        return null;
      TParm parm = new TParm();
      for(int i = 0;i < row + 1;i++)
      {
        parm.setData("HOSP_AREA", i, "H" + i);
        parm.setData("ON_CODE", i, "C" + i);
        parm.setData("ON_DESC", i, "D" + i);
        parm.setData("OPT_USER", i, "U" + i);
        parm.setData("OPT_DATE", i, "D" + i);
        parm.setData("OPT_TERM", i, "T" + i);
      }
      return parm;
    }
    /**
     * �ָ�һ�������ڵ�ȫ�������ݴ��ڵĳ�ʼ��λ��
     * @param area String ����
     */
    private void moveCancelAllChildDataWindows(String area)
    {
      if(getXML("childdatawindows") == null)
        return;
      Iterator iterator = getXML("childdatawindows").getChildElements();
      while (iterator.hasNext())
      {
        INode childDataWindow = (INode) iterator.next();
        if (!childDataWindow.getAttributeValue("band").equals(area))
          continue;
        String dataWindowName = childDataWindow.getAttributeValue("name");
        TDataWindowBase childdatawindow = getChildDataWindow(dataWindowName);
        if(childdatawindow == null)
          continue;
        //childdatawindow.getUI().setChildHeight(0);
      }
    }
    public TDataWindowBase getChildDataWindow(String name)
    {
      return (TDataWindowBase)childDataWindows.get(name);
    }
    /**
     * �õ�һҳ������
     * @return int
     */
    public int getPageRowCount()
    {
      if (getDetailHeight() == 0)
        return -1;
      return (int) ( (double) (getImageableHeight() - getHeaderHeight() -
                               getFooterHeight()) / (double) getDetailHeight());
    }
    /**
     * �õ��������߶�
     * @return int
     */
    public int getDetailHeight()
    {
      return (int) (getXML("Detail").getAttributeValueAsDouble(
          "height").doubleValue() * getPrintZoom() / 100);
    }
    /**
     * �õ���ӡ����߶�
     * @return double
     */
    public double getImageableHeight()
    {
      return getXML("Print").getAttributeValueAsDouble("ImageableHeight").
          doubleValue();
    }
    /**
     * �õ���ӡ������
     * @return double
     */
    public double getImageableWidth()
    {
      return getXML("Print").getAttributeValueAsDouble("ImageableWidth").
          doubleValue();
    }
    /**
     * �õ�ҳͷ�߶�
     * @return int
     */
    public int getHeaderHeight()
    {
      return (int) (getXML("Header").getAttributeValueAsDouble(
          "height").doubleValue() * getPrintZoom() / 100);
    }
    /**
     * �õ�ҳ�Ÿ߶�
     * @return int
     */
    public int getFooterHeight()
    {
      return (int) (getXML("Footer").getAttributeValueAsDouble(
          "height").doubleValue() * getPrintZoom() / 100);
    }
    /**
     * �õ���̨���ݴ洢����
     * @return DataStore
     */
    public DataStore getDataStore()
    {
      return datastore;
    }
    public void setDataStore(DataStore datastore)
    {
      this.datastore = datastore;
    }
    /**
     * �õ�������
     * @return int
     */
    public int rowCount()
    {
      return getDataStore().rowCount();
    }
    /**
     * �õ�����
     * @param row �к�
     * @param column �к�
     * @return String
     */
    public String getItemString(int row, int column)
    {
      return getDataStore().getItemString(row, column);
    }
    /**
     * �õ�����
     * @param row �к�
     * @param column �к�
     * @return String ����
     */
    public String getItemString(int row, String column)
    {
      return getDataStore().getItemString(row, column);
    }
    /**
     * �õ�����
     * @param column int �к�
     * @return String ����
     */
    public String getItemString(int column)
    {
      return getDataStore().getItemString(column);
    }

    /**
     * �õ�����
     * @param name String ����
     * @return String ����
     */
    public String getItemString(String name)
    {
      return getDataStore().getItemString(name);
    }
    /**
     * �������������һ�ι����ߴ�
     * @param x int
     */
    public void setHeightBlockIncrement(int x)
    {
      scrollBarHeight.setBlockIncrement(x);
    }
    public PageData getPageData()
    {
      return tempPageData;
    }
    /**
     * ���ù��������������ߴ�
     * @param width �����������ߴ�
     * @param height �����������ߴ�
     */
    public void setScrollBarMax(int width, int height)
    {
      if (width > 0)
        scrollBarWidth.setMaximum(width + scrollBarLength - 6);
      else
        scrollBarWidth.setValue(0);
      if (height > 0)
        scrollBarHeight.setMaximum(height + scrollBarLength - 6);
      else
        scrollBarHeight.setValue(0);
      if (hasScrollBarWidth != width > 0 ||
          hasScrollBarHeight != height > 0)
      {
        hasScrollBarWidth = width > 0;
        hasScrollBarHeight = height > 0;
        resizeScrollBar();
      }
    }
    /**
     * �õ�xml����
     * @return INode
     */
    public INode getXML()
    {
      return nodeData;
    }
    public DataWindowUI getUI()
    {
      return datawindowui;
    }
    public DataWindowPriviewUI getPriviewUI()
    {
      return datawindowpriviewui;
    }
    /**
     * ������ӡ���ô���
     */
    public void openPageDialog()
    {
      PageDialog dialog = new PageDialog(this);
      dialog.setVisible(true);
    }
    /**
     * ����ϵͳ��ӡ���ô���
     */
    public void printSetup()
    {
      setPageFormat(PrinterJob.getPrinterJob().pageDialog(getPageFormat()));
    }
    /**
     * ���ô�ӡ��ʽ
     * @param pageFormat ��ӡ��ʽ����
     */
    public void setPageFormat(PageFormat pageFormat)
    {
      INode print = getPrintNode();
      print.setAttributeValue("ImageableX", pageFormat.getImageableX());
      print.setAttributeValue("ImageableY", pageFormat.getImageableY());
      print.setAttributeValue("ImageableWidth", pageFormat.getImageableWidth());
      print.setAttributeValue("ImageableHeight", pageFormat.getImageableHeight());
      print.setAttributeValue("Width", pageFormat.getWidth());
      print.setAttributeValue("Height", pageFormat.getHeight());
      print.setAttributeValue("Orientation", pageFormat.getOrientation());
    }
    /**
     * �õ���ӡ�������ű���
     * @return double
     */
    public double getPriviewZoom()
    {
        return getPrintNode().getAttributeValueAsDouble("PriviewZoom").doubleValue();
    }

    /**
     * ���ô�ӡ�������ű���
     * @param zoom double
     */
    public void setPriviewZoom(double zoom)
    {
        getPrintNode().setAttributeValue("PriviewZoom", zoom);
        if (isPreview())
            repaint();
    }
    /**
     * �õ���ӡ���ű���
     * @return double
     */
    public double getPrintZoom()
    {
      return getPrintNode().getAttributeValueAsDouble("PrintZoom").doubleValue();
    }

    /**
     * ���ô�ӡ���ű���
     * @param zoom double
     */
    public void setPrintZoom(double zoom)
    {
      getPrintNode().setAttributeValue("PrintZoom", zoom);
      if (isPreview())
        repaint();
    }
    /**
     * ����SQL����Զ���������
     * @param sql String SQL���
     */
    public void loadSQL(String sql)
    {
        setNodeData(getConfigXMLDriver().create(sql));
        if (isPreview())
          repaint();
    }
    /**
     * �õ�xml�����豸
     * @return ConfigDataWindowFile
     */
    public ConfigDataWindowFile getConfigXMLDriver()
    {
      return configXML;
    }
    /**
     * ��Ӧ�÷������м���ȫ������
     * @return int
     */
    public int retrieve()
    {
        int count = datastore.retrieve();
        if (isPreview())
          repaint();
        return count;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getErrCode()
    {
      return datastore.getErrCode();
    }

    /**
     * �õ������ı���Ϣ
     * @return String
     */
    public String getErrText()
    {
      return datastore.getErrText();
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
    public void err(String text)
    {
        System.out.println(text);
    }
}
