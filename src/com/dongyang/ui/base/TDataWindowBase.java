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
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 标签
     */
    private String tag = "";
    /**
     * 加载标签
     */
    private String loadtag;
    /**
     * 控制类名
     */
    private String controlClassName;
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 文本
     */
    private String text;
    /**
     * 鼠标事件监听对象
     */
    TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    TMouseMotionListener mouseMotionListenerObject;
    /**
     * 组件监听对象
     */
    private TComponentListener componentListenerObject;
    /**
     * 自动尺寸
     */
    private int autoSize = 5;
    /**
     * 自动X
     */
    private boolean autoX;
    /**
     * 自动Y
     */
    private boolean autoY;
    /**
     * 自动宽度
     */
    private boolean autoWidth;
    /**
     * 自动高度
     */
    private boolean autoHeight;
    /**
     * 横向滚动条
     */
    JScrollBar scrollBarWidth = new JScrollBar();
    /**
     * 纵向滚动条
     */
    JScrollBar scrollBarHeight = new JScrollBar();
    /**
     * 横向滚动位置
     */
    private int scrollWidthValue;
    /**
     * 纵向滚动位置
     */
    private int scrollHeightValue;
    /**
     * 存在横向滚动条
     */
    private boolean hasScrollBarWidth = true;
    /**
     * 存在纵向滚动条
     */
    private boolean hasScrollBarHeight = true;
    /**
     * 滚动条尺寸
     */
    private int scrollBarLength = 17;
    /**
     * 标题
     */
    private String title = "数据窗口";
    /**
     * 标题文字颜色
     */
    private Color titleTextColor = new Color(255, 255, 255);
    /**
     * 标题背景颜色
     */
    private Color titleBkColor = new Color(0, 0, 255);
    /**
     * 标题字体
     */
    private Font titleFont = new Font("宋体", 0, 14);
    /**
     * 标题高度
     */
    private int titleHeight = 20;
    /**
     * 是否有标题
     */
    private boolean hasTitle = true;
    /**
     * UI
     */
    private DataWindowUI datawindowui = new DataWindowUI(this);
    /**
     * 打印 UI
     */
    private DataWindowPriviewUI datawindowpriviewui = new DataWindowPriviewUI(this);
    /**
     * 当前是否是阅览状态
     */
    private boolean preview = true;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * XML数据
     */
    private INode nodeData;
    /**
     * 打印机类型
     */
    private int printType;
    /**
     * 当前页号
     */
    private int page;
    /**
     * 当前打印的页面区间,例 "1,3,4,5,10 to 20"
     */
    private String printPageID;
    /**
     * 结果集
     */
    private TParm parmValue;
    /**
     * 临时的页面数据包
     */
    private PageData tempPageData;
    /**
     * 子数据窗口
     */
    private Map childDataWindows = new HashMap();
    private DataStore datastore = new DataStore(this);
    /**
     * xml控制设备
     */
    private ConfigDataWindowFile configXML = new ConfigDataWindowFile();
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     *  构造器
     */
    public TDataWindowBase()
    {
        uiInit();
    }

    /**
     * 打印当前页
     */
    public final static int PRINT_CURRENT_PAGE = 1;
    /**
     * 打印自定义页
     */
    public final static int PRINT_CUSTOM_PAGE = 2;

    /**
     * 内部初始化UI
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
     * 设置加载标签
     * @param loadtag String
     */
    public void setLoadTag(String loadtag)
    {
        this.loadtag = loadtag;
    }

    /**
     * 得到加载标签
     * @return String
     */
    public String getLoadTag()
    {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }

    /**
     * 设置X坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.setLocation(x, getLocation().y);
    }

    /**
     * 设置Y坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.setLocation(getLocation().x, y);
    }

    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.setSize(width, getSize().height);
    }

    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.setSize(getSize().width, height);
    }

    /**
     * X坐标偏移
     * @param d int
     */
    public void setX$(int d)
    {
        if (d == 0)
            return;
        setX(getX() + d);
    }

    /**
     * Y坐标偏移
     * @param d int
     */
    public void setY$(int d)
    {
        if (d == 0)
            return;
        setY(getY() + d);
    }

    /**
     * 向左生长
     * @param d int
     */
    public void setX_$(int d)
    {
        setX$(d);
        setWidth$( -d);
    }

    /**
     * 向上升上
     * @param d int
     */
    public void setY_$(int d)
    {
        setY$(d);
        setHeight$( -d);
    }

    /**
     * 宽度坐标偏移
     * @param d int
     */
    public void setWidth$(int d)
    {
        if (d == 0)
            return;
        setWidth(getWidth() + d);
    }

    /**
     * 高度坐标偏移
     * @param d int
     */
    public void setHeight$(int d)
    {
        if (d == 0)
            return;
        setHeight(getHeight() + d);
    }

    /**
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control)
    {
        this.control = control;
        if (control != null)
            control.setComponent(this);
    }

    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl()
    {
        return control;
    }

    /**
     * 加载图片
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
            err("没有找到图标" + path);
        }
        return icon;
    }

    /**
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject()
    {
        return baseEvent;
    }

    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, String methodName)
    {
        addEventListener(eventName, this, methodName);
    }

    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName)
    {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * 删除监听方法
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
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType)
    {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName)
    {
        return callEvent(eventName, new Object[]
                         {}, new String[]
                         {});
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName, Object parm)
    {
        return callEvent(eventName, new Object[]
                         {parm}, new String[]
                         {"java.lang.Object"});
    }

    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        //监听鼠标事件
        if (mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //监听鼠标移动事件
        if (mouseMotionListenerObject == null)
        {
            mouseMotionListenerObject = new TMouseMotionListener(this);
            addMouseMotionListener(mouseMotionListenerObject);
        }
        if (componentListenerObject == null)
        {
            //监听组件事件
            componentListenerObject = new TComponentListener(this);
            addComponentListener(componentListenerObject);
        }
        //监听组件事件
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_HIDDEN,
                         "onComponentHidden");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_MOVED,
                         "onComponentMoved");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_RESIZED,
                         "onComponentResized");
        addEventListener(getTag() + "->" + TComponentListener.COMPONENT_SHOWN,
                         "onComponentShown");
        //监听Mouse事件
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
     * 组件隐藏事件
     * @param e ComponentEvent
     */
    public void onComponentHidden(ComponentEvent e)
    {

    }

    /**
     * 组件移动事件
     * @param e ComponentEvent
     */
    public void onComponentMoved(ComponentEvent e)
    {

    }

    /**
     * 组件尺寸改变事件
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
     * 组件显示事件
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
     * 父容器尺寸改变
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
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
    }

    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
    }

    /**
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
    }

    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
    }

    /**
     * 鼠标拖动
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
    }

    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }

    /**
     * 初始化
     */
    public void onInit()
    {
        //初始化监听事件
        initListeners();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        if (getControl() != null)
            getControl().onInit();
        resizeScrollBar();
    }

    /**
     * 设置配置参数对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }

    /**
     * 得到配置参数对象
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
        //保存配置对象
        setConfigParm(configParm);
        //加载全部属性
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
        //加载控制类
        if (getControl() != null)
            getControl().init();

    }

    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    protected boolean filterInit(String message)
    {
        return true;
    }

    /**
     * 加载顺序
     * @return String
     */
    protected String getDownLoadIndex()
    {
        return "ControlClassName,ControlConfig";
    }

    /**
     * 呼叫方法
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message, Object ...parameters)
    {
        return callMessage(message, parameters);
    }

    /**
     * 消息处理
     * @param message String 消息处理
     * @return Object
     */
    public Object callMessage(String message)
    {
        return callMessage(message, null);
    }

    /**
     * 处理消息
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
     * 得到父类
     * @return TComponent
     */
    public TComponent getParentTComponent()
    {
        return getParentTComponent(getParent());
    }

    /**
     * 得到父类(递归用)
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
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm)
    {
        if (message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message, "=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }

    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[])
    {
        if ("pic".equalsIgnoreCase(value[0]))
            value[0] = "pictureName";
    }

    /**
     * 设置组件配置
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
     * 设置组件控制类名
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
     * 得到组件控制类名
     * @return String
     */
    public String getControlClassName()
    {
        return controlClassName;
    }

    /**
     * 得到自己对象
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }

    /**
     * 查找组件
     * @param tag String 标签
     * @return TComponent
     */
    public TComponent findTComponent(String tag)
    {
        return (TComponent) callMessage(tag + "|getThis");
    }

    /**
     * 设置背景颜色
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
     * 设置字体颜色
     * @param color String
     */
    public void setColor(String color)
    {
        if (color == null || color.length() == 0)
            return;
        this.setForeground(StringTool.getColor(color, getConfigParm()));
    }

    /**
     * 设置边框
     * @param border String
     */
    public void setBorder(String border)
    {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }

    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "TLabelBase";
    }

    /**
     * 设置自动X
     * @param autoX boolean
     */
    public void setAutoX(boolean autoX)
    {
        this.autoX = autoX;
    }

    /**
     * 是否是自动X
     * @return boolean
     */
    public boolean isAutoX()
    {
        return autoX;
    }

    /**
     * 设置自动Y
     * @param autoY boolean
     */
    public void setAutoY(boolean autoY)
    {
        this.autoY = autoY;
    }

    /**
     * 是否是自动Y
     * @return boolean
     */
    public boolean isAutoY()
    {
        return autoY;
    }

    /**
     * 设置自动宽度
     * @param autoWidth boolean
     */
    public void setAutoWidth(boolean autoWidth)
    {
        this.autoWidth = autoWidth;
    }

    /**
     * 是否是自动宽度
     * @return boolean
     */
    public boolean isAutoWidth()
    {
        return autoWidth;
    }

    /**
     * 设置自动高度
     * @param autoHeight boolean
     */
    public void setAutoHeight(boolean autoHeight)
    {
        this.autoHeight = autoHeight;
    }

    /**
     * 是否是自动高度
     * @return boolean
     */
    public boolean isAutoHeight()
    {
        return autoHeight;
    }

    /**
     * 设置自动尺寸
     * @param autoSize int
     */
    public void setAutoSize(int autoSize)
    {
        this.autoSize = autoSize;
    }

    /**
     * 得到自动尺寸
     * @return int
     */
    public int getAutoSize()
    {
        return autoSize;
    }

    /**
     * 设置值
     * @param value String
     */
    public void setValue(String value)
    {
        //setText(value);
    }

    /**
     * 得到值
     * @return String
     */
    public String getValue()
    {
        return ""; //getText();
    }

    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * 得到文本
     * @return String
     */
    public String getText()
    {
        return text;
    }

    /**
     * 绘制组件图形界面
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
     * 绘制标题图形页面
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
     * 是否已经初始化
     * @return boolean true 初始化 false 没有初始化
     */
    public boolean isInit()
    {
        return getNodeData() != null;
    }

    /**
     * 设置当前是否是阅览状态
     * @param preview boolean true 是阅览状态 false 不是阅览状态
     */
    public void setPreview(boolean preview)
    {
        this.preview = preview;
        repaint();
    }

    /**
     * 得到当前是否是打印阅览状态
     * @return boolean true 是阅览状态 false 不是阅览状态
     */
    public boolean isPreview()
    {
        return preview;
    }

    /**
     * 滚动条的值改变
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
     * 得到横向滚动条的位置
     * @return int
     */
    public int getScrollWidthValue()
    {
        return scrollWidthValue;
    }

    /**
     * 得到纵向滚动条的位置
     * @return int
     */
    public int getScrollHeightValue()
    {
        return scrollHeightValue;
    }

    /**
     * 调节滚动条在窗口中的位置
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
     * 得到边框尺寸
     * @return int
     */
    private int getBorderLength()
    {
        if (getBorder() == null)
            return 0;
        return 2;
    }

    /**
     * 是否有标题
     * @return boolean
     */
    public boolean isHasTitle()
    {
        return hasTitle;
    }

    /**
     * 设置是否显示标题
     * @param fag true 显示 false 不显示
     */
    public void setHasTitle(boolean fag)
    {
        hasTitle = fag;
        resizeScrollBar();
        repaint();
    }

    /**
     * 得到标题高度
     * @return int
     */
    public int getTitleHeight()
    {
        return titleHeight;
    }

    /**
     * 设置标题高度
     * @param height 高度
     */
    public void setTitleHeight(int height)
    {
        titleHeight = height;
    }

    /**
     * 得到标题文字
     * @return String
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * 设置标题文字
     * @param title String
     */
    public void setTitle(String title)
    {
        this.title = title;
        repaint();
    }

    /**
     * 得到标题文字颜色
     * @return Color
     */
    public Color getTitleTextColor()
    {
        return titleTextColor;
    }

    /**
     * 设置标题文字颜色
     * @param c Color
     */
    public void setTitleTextColor(Color c)
    {
        titleTextColor = c;
    }

    /**
     * 得到标题背景颜色
     * @return Color
     */
    public Color getTitleBkColor()
    {
        return titleBkColor;
    }

    /**
     * 设置标题背景颜色
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
     * 设置标题背景颜色
     * @param c Color
     */
    public void setTitleBkColor(Color c)
    {
        titleBkColor = c;
    }

    /**
     * 得到标题文字字体
     * @return Font
     */
    public Font getTitleFont()
    {
        return titleFont;
    }

    /**
     * 设置标题文字字体
     * @param f Font
     */
    public void setTitleFont(Font f)
    {
        titleFont = f;
    }

    /**
     * 设置文件名
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
     * 得到文件名
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * 加载文件
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
     * 家在数据
     * @param data String
     */
    public void loadString(String data)
    {
        setNodeData(TNode.loadXML(data));
    }
    /**
     * 设置XML数据
     * @param nodeData INode
     */
    public void setNodeData(INode nodeData)
    {
        this.nodeData = nodeData;
        datastore.load(nodeData);
    }

    /**
     * 得到XML数据
     * @return INode
     */
    public INode getNodeData()
    {
        return nodeData;
    }

    /**
     * 得到打印的XML数据
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
     * 得到打印页面尺寸
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
        case PageFormat.PORTRAIT: //纵向
            paper.setImageableArea(x, y, w, h);
            paper.setSize(width, height);
            break;
        case PageFormat.REVERSE_LANDSCAPE: //横向
            paper.setImageableArea(y, x, h, w);
            paper.setSize(height, width);
            break;
        }
        return paper;
    }

    /**
     * 得到打印格式对象
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
     * 打印
     * @return boolean
     */
    public boolean print()
    {
        return print(PrinterJob.getPrinterJob());
    }

    /**
     * 打印
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
     * 设置当前打印机类型
     * @param type int
     */
    public void setPrintType(int type)
    {
        printType = type;
    }

    /**
     * 得到当前打印机类型
     * @return int
     */
    public int getPrintType()
    {
        return printType;
    }

    /**
     * 设置当前页号
     * @param page int
     */
    public void setPage(int page)
    {
        this.page = page;
    }

    /**
     * 得到当前页号
     * @return int
     */
    public int getPage()
    {
        return page;
    }

    /**
     * 设置当前打印的页码区间
     * @param id int
     */
    public void setPrintPageID(String id)
    {
        printPageID = id;
    }

    /**
     * 得到当前打印的页面区间,例 "1,3,4,5,10 to 20"
     * @return String
     */
    public String getPrintPageID()
    {
        return printPageID;
    }

    /**
     * 打印
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
     * 得到组个数
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
     * 得到组XML数据
     * @param index int 组位置
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
     * 得到组XML数据
     * @param name String 组名
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
     * 设置Parm数据
     * @param parmValue TParm
     */
    public void setParmValue(TParm parmValue)
    {
        this.parmValue = parmValue;
    }
    /**
     * 得到Parm数据
     * @return TParm
     */
    public TParm getParmValue()
    {
        return parmValue;
    }
    /**
     *
     * <p>Title: 打印页属性对象</p>
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
     * 得到总页数
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
     * 计算某一个区间占用的高度
     * @param area String 区间名称
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
     * 计算行区间占用的全部的高度
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
     * 计算某一个区间占用的高度 区间名称
     * @param area String
     * @param row int 数据行号
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
     * 在区间内移动全部组件到匹配的位置
     * @param name String 参照子数据窗口名称
     * @param y int 参照y坐标
     * @param h int 移动高度
     * @param area String 区间
     * @param zoom double
     */
    private void moveAll(String name,int y,int h,String area,double zoom)
    {
      //在区间内移动全部的子数据窗口到匹配的位置
      moveAllChildDataWindows(name,y,h,area,zoom);
    }
    /**
     * 在区间内移动全部的子数据窗口到匹配的位置
     * @param name String 参照子数据窗口名称
     * @param y int 参照y坐标
     * @param h int 移动高度
     * @param area String 区间
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
     * 得到在某一行上的子数据窗口高度
     * @param name String 子数据窗口名称
     * @param row int 行号
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
     * 恢复一个区间内的全部子数据窗口的初始化位置
     * @param area String 区间
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
     * 得到一页总行数
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
     * 得到数据区高度
     * @return int
     */
    public int getDetailHeight()
    {
      return (int) (getXML("Detail").getAttributeValueAsDouble(
          "height").doubleValue() * getPrintZoom() / 100);
    }
    /**
     * 得到打印区域高度
     * @return double
     */
    public double getImageableHeight()
    {
      return getXML("Print").getAttributeValueAsDouble("ImageableHeight").
          doubleValue();
    }
    /**
     * 得到打印区域宽度
     * @return double
     */
    public double getImageableWidth()
    {
      return getXML("Print").getAttributeValueAsDouble("ImageableWidth").
          doubleValue();
    }
    /**
     * 得到页头高度
     * @return int
     */
    public int getHeaderHeight()
    {
      return (int) (getXML("Header").getAttributeValueAsDouble(
          "height").doubleValue() * getPrintZoom() / 100);
    }
    /**
     * 得到页脚高度
     * @return int
     */
    public int getFooterHeight()
    {
      return (int) (getXML("Footer").getAttributeValueAsDouble(
          "height").doubleValue() * getPrintZoom() / 100);
    }
    /**
     * 得到后台数据存储对象
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
     * 得到总行数
     * @return int
     */
    public int rowCount()
    {
      return getDataStore().rowCount();
    }
    /**
     * 得到数据
     * @param row 行号
     * @param column 列号
     * @return String
     */
    public String getItemString(int row, int column)
    {
      return getDataStore().getItemString(row, column);
    }
    /**
     * 得到数据
     * @param row 行号
     * @param column 列号
     * @return String 数据
     */
    public String getItemString(int row, String column)
    {
      return getDataStore().getItemString(row, column);
    }
    /**
     * 得到数据
     * @param column int 列号
     * @return String 数据
     */
    public String getItemString(int column)
    {
      return getDataStore().getItemString(column);
    }

    /**
     * 得到数据
     * @param name String 列名
     * @return String 数据
     */
    public String getItemString(String name)
    {
      return getDataStore().getItemString(name);
    }
    /**
     * 设置纵向滚动块一次滚动尺寸
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
     * 设置滚动条的最大滚动尺寸
     * @param width 横向最大滚动尺寸
     * @param height 纵向最大滚动尺寸
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
     * 得到xml数据
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
     * 弹出打印设置窗口
     */
    public void openPageDialog()
    {
      PageDialog dialog = new PageDialog(this);
      dialog.setVisible(true);
    }
    /**
     * 弹出系统打印设置窗口
     */
    public void printSetup()
    {
      setPageFormat(PrinterJob.getPrinterJob().pageDialog(getPageFormat()));
    }
    /**
     * 设置打印格式
     * @param pageFormat 打印格式对象
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
     * 得到打印阅览缩放比例
     * @return double
     */
    public double getPriviewZoom()
    {
        return getPrintNode().getAttributeValueAsDouble("PriviewZoom").doubleValue();
    }

    /**
     * 设置打印阅览缩放比例
     * @param zoom double
     */
    public void setPriviewZoom(double zoom)
    {
        getPrintNode().setAttributeValue("PriviewZoom", zoom);
        if (isPreview())
            repaint();
    }
    /**
     * 得到打印缩放比例
     * @return double
     */
    public double getPrintZoom()
    {
      return getPrintNode().getAttributeValueAsDouble("PrintZoom").doubleValue();
    }

    /**
     * 设置打印缩放比例
     * @param zoom double
     */
    public void setPrintZoom(double zoom)
    {
      getPrintNode().setAttributeValue("PrintZoom", zoom);
      if (isPreview())
        repaint();
    }
    /**
     * 根据SQL语句自动生成内容
     * @param sql String SQL语句
     */
    public void loadSQL(String sql)
    {
        setNodeData(getConfigXMLDriver().create(sql));
        if (isPreview())
          repaint();
    }
    /**
     * 得到xml控制设备
     * @return ConfigDataWindowFile
     */
    public ConfigDataWindowFile getConfigXMLDriver()
    {
      return configXML;
    }
    /**
     * 从应用服务器中加载全部数据
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
     * 得到错误编号
     * @return int
     */
    public int getErrCode()
    {
      return datastore.getErrCode();
    }

    /**
     * 得到错误文本信息
     * @return String
     */
    public String getErrText()
    {
      return datastore.getErrText();
    }
    /**
     * 设置父类组件
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent) {
        this.parentComponent = parentComponent;
    }

    /**
     * 得到父类组件
     * @return TComponent
     */
    public TComponent getParentComponent() {
        return parentComponent;
    }
    /**
     * 释放
     */
    public void release()
    {

    }

    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text)
    {
        System.out.println(text);
    }
}
