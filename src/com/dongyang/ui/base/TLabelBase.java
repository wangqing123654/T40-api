package com.dongyang.ui.base;

import javax.swing.JLabel;
import javax.swing.Icon;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import javax.swing.JComponent;
import com.dongyang.control.TControl;
import java.awt.Container;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.event.TComponentListener;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Font;
import com.dongyang.control.TDrawControl;
import java.awt.Graphics;
import com.dongyang.util.TSystem;

public class TLabelBase extends JLabel
        implements TComponent{
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
     * 绘图控制类
     */
    private TDrawControl drawControl;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 图片名称
     */
    private String pictureName;
    /**
     * 鼠标事件监听对象
     */
    TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    TMouseMotionListener mouseMotionListenerObject;
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
     * 自动H
     */
    private boolean autoH;
    /**
     * 自动W
     */
    private boolean autoW;
    /**
     * 自动W尺寸
     */
    private int autoWidthSize;
    /**
     * 自动H尺寸
     */
    private int autoHeightSize;
    /**
     * 光标
     */
    private int cursorType;
    /**
     * 动作消息
     */
    private String actionMessage;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 自动图标尺寸
     */
    private boolean isAutoIconSize;
    /**
     * 居中
     */
    private boolean isAutoCenter;
    /**
     * 中文显示
     */
    private String zhText;
    /**
     * 英文显示
     */
    private String enText;
    /**
     * 字体
     */
    private Font tfont;
    /**
     * Creates a <code>JLabel</code> instance with the specified
     * text, image, and horizontal alignment.
     * The label is centered vertically in its display area.
     * The text is on the trailing edge of the image.
     *
     * @param text  The text to be displayed by the label.
     * @param icon  The image to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public TLabelBase(String text, Icon icon, int horizontalAlignment) {
        setText(text);
        setIcon(icon);
        setHorizontalAlignment(horizontalAlignment);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
        setTFont(TUIStyle.getLabelDefaultFont());
    }
    /**
     * Creates a <code>JLabel</code> instance with the specified
     * text and horizontal alignment.
     * The label is centered vertically in its display area.
     *
     * @param text  The text to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public TLabelBase(String text, int horizontalAlignment) {
        this(text, null, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text.
     * The label is aligned against the leading edge of its display area,
     * and centered vertically.
     *
     * @param text  The text to be displayed by the label.
     */
    public TLabelBase(String text) {
        this(text, null, LEADING);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified
     * image and horizontal alignment.
     * The label is centered vertically in its display area.
     *
     * @param image  The image to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public TLabelBase(Icon image, int horizontalAlignment) {
        this(null, image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image.
     * The label is centered vertically and horizontally
     * in its display area.
     *
     * @param image  The image to be displayed by the label.
     */
    public TLabelBase(Icon image) {
        this(null, image, CENTER);
    }

    /**
     * Creates a <code>JLabel</code> instance with
     * no image and with an empty string for the title.
     * The label is centered vertically
     * in its display area.
     * The label's contents, once set, will be displayed on the leading edge
     * of the label's display area.
     */
    public TLabelBase() {
        this("", null, LEADING);
    }
    /**
     * 得到标签
     * @return String
     */
    public String getTag()
    {
        return tag;
    }
    /**
     * 设置标签
     * @param tag String
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
        if(loadtag != null)
            return loadtag;
        return getTag();
    }
    /**
     * 设置X坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.setLocation(x,getLocation().y);
    }
    /**
     * 设置Y坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.setLocation(getLocation().x,y);
    }
    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.setSize(width,getSize().height);
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.setSize(getSize().width,height);
    }
    /**
     * X坐标偏移
     * @param d int
     */
    public void setX$(int d)
    {
        if(d == 0)
            return;
        setX(getX() + d);
    }
    /**
     * Y坐标偏移
     * @param d int
     */
    public void setY$(int d)
    {
        if(d == 0)
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
        setWidth$(-d);
    }
    /**
     * 向上升上
     * @param d int
     */
    public void setY_$(int d)
    {
        setY$(d);
        setHeight$(-d);
    }
    /**
     * 宽度坐标偏移
     * @param d int
     */
    public void setWidth$(int d)
    {
        if(d == 0)
            return;
        setWidth(getWidth() + d);
    }
    /**
     * 高度坐标偏移
     * @param d int
     */
    public void setHeight$(int d)
    {
        if(d == 0)
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
        if(control != null)
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
     * 设置绘图控制类
     * @param drawControl TDrawControl
     */
    public void setDrawControl(TDrawControl drawControl)
    {
        this.drawControl = drawControl;
        if(drawControl != null)
            drawControl.setParent(this);
    }
    /**
     * 得到绘图控制类
     * @return TDrawControl
     */
    public TDrawControl getDrawControl()
    {
        return drawControl;
    }
    /**
     * 设置按钮图片
     * @param name 图片名称
     */
    public void setPictureName(String name)
    {
        pictureName = name;
        Icon pic = null;
        if (name != null)
            pic = createImageIcon(name);
        setIcon(pic);
    }

    /**
     * 得到按钮图片
     * @return 图片名称
     */
    public String getPictureName()
    {
        return pictureName;
    }
    /**
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
        if(TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%/image/ImageIcon/" + filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if(icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try{
            icon = new ImageIcon(getClass().getResource(path));
        }catch(NullPointerException e)
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
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, Object object, String methodName)
    {
        getBaseEventObject().add(eventName,object,methodName);
    }
    /**
     * 删除监听方法
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object, String methodName)
    {
      getBaseEventObject().remove(eventName,object,methodName);
    }
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName,Object[] parms,String[] parmType)
    {
        return getBaseEventObject().callEvent(eventName,parms,parmType);
    }
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName)
    {
      return callEvent(eventName,new Object[]{},new String[]{});
    }
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName,Object parm)
    {
      return callEvent(eventName,new Object[]{parm},new String[]{"java.lang.Object"});
    }
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        //监听鼠标事件
        if(mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //监听鼠标移动事件
        if(mouseMotionListenerObject == null)
        {
            mouseMotionListenerObject = new TMouseMotionListener(this);
            addMouseMotionListener(mouseMotionListenerObject);
        }
        //监听Mouse事件
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,"onMouseReleased");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,"onMouseEntered");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,"onMouseExited");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
        addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
        TComponent component = getParentTComponent();
        if(component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
    }
    /**
     * 父容器尺寸改变
     * @param width int
     * @param height int
     */
    public void onParentResize(int width,int height)
    {
        Container c = getParent();
        if(c == null)
            return;
        Insets insets = c.getInsets();
        if(isAutoCenter())
        {
            int w = c.getWidth() - insets.left - insets.right;
            int h = c.getHeight() - insets.top - insets.bottom;
            int x = (w - getWidth()) / 2;
            int y = (h - getHeight()) / 2;
            this.setLocation(x,y);
            return;
        }
        if(isAutoW())
        {
            int w = c.getWidth();
            setX((w == 0?height:w) - insets.right - getAutoSize() - getWidth() - getAutoWidthSize());
        }
        if(isAutoH())
        {
            int h = c.getHeight();
            setY((h == 0?height:h) - insets.bottom - getAutoSize() - getHeight() - getAutoHeightSize());
        }
        if(!isAutoW() && isAutoX())
            setX(insets.left + getAutoSize());
        if(!isAutoH() && isAutoY())
            setY(insets.top + getAutoSize());
        if(!isAutoW() && isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize() - getAutoWidthSize());
        }
        if(!isAutoH() && isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize() - getAutoHeightSize());
        }
    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        sendActionMessage();
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
        if(getCursorType() != 0)
            setCursor(new Cursor(getCursorType()));
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        if(getCursorType() != 0)
            setCursor(new Cursor(0));
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
        if(getControl() != null)
            getControl().onInit();
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
        if(configParm == null)
            return;
        //保存配置对象
        setConfigParm(configParm);
        //加载全部属性
        String value[] = configParm.getConfig().getTagList(configParm.getSystemGroup(),getLoadTag(),getDownLoadIndex());
        for(int index = 0;index < value.length;index++)
            if(filterInit(value[index]))
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
    protected String getDownLoadIndex() {
        return "ControlClassName,DrawControlClassName,ControlConfig,Width,Height,AutoIconSize";
    }
    /**
     * 呼叫方法
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters)
    {
        return callMessage(message,parameters);
    }
    /**
     * 消息处理
     * @param message String 消息处理
     * @return Object
     */
    public Object callMessage(String message)
    {
        return callMessage(message,null);
    }
    /**
     * 处理消息
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
        //消息上传
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
        if(container == null)
            return null;
        if(container instanceof TComponent)
            return(TComponent)container;
        return getParentTComponent(container.getParent());
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message,Object parm)
    {
        if(message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message,"|");
        Object result = null;
        if((result = RunClass.invokeMethodT(this,value,parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message,"=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if((result = RunClass.invokeFieldT(this,value,parm)) != null)
            return result;
        return null;
    }
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[])
    {
        if("pic".equalsIgnoreCase(value[0]))
            value[0] = "pictureName";
        else if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
    }
    /**
     * 设置组件配置
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
     * 设置组件控制类名
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
     * 设置显示组件类名
     * @param value String
     */
    public void setDrawControlClassName(String value)
    {
        if(value == null || value.length() == 0)
        {
            setDrawControl(null);
            return;
        }
        Object obj = getConfigParm().loadObject(value);
        if(obj == null)
        {
            setDrawControl(null);
            err("Class loadObject err className=" + value);
            return;
        }
        if(!(obj instanceof TDrawControl)){
            setDrawControl(null);
            err("class loadObject type err className=" + value + "is not TDrawControl");
            return;
        }
        TDrawControl control = (TDrawControl)obj;
        setDrawControl(control);
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
        return (TComponent)callMessage(tag + "|getThis");
    }
    /**
     * 设置图标名称
     * @param name String 图标名称
     */
    public void setIconName(String name) {
        byte[] data = TIOM_AppServer.readFile(name);
        if (data == null)
            return;
        ImageIcon icon = new ImageIcon(data);
        if(isAutoIconSize())
            setSize(icon.getIconWidth(), icon.getIconHeight());
        setIcon(icon);
    }
    /**
     * 设置自动图标尺寸
     * @param isAutoIconSize boolean
     */
    public void setAutoIconSize(boolean isAutoIconSize)
    {
        this.isAutoIconSize = isAutoIconSize;
        if(!isAutoIconSize)
            return;
        if(getIcon()!= null)
            setSize(getIcon().getIconWidth(),getIcon().getIconHeight());
    }
    /**
     * 是否自动图标尺寸
     * @return boolean
     */
    public boolean isAutoIconSize()
    {
        return isAutoIconSize;
    }
    /**
     * 设置字体
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * 得到字体
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
     * 设置字体
     * @param name String
     */
    public void setFontName(String name)
    {
        Font f = getTFont();
        setTFont(new Font(name,f.getStyle(),f.getSize()));
    }
    /**
     * 得到字体
     * @return String
     */
    public String getFontName()
    {
        return getTFont().getFontName();
    }
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * 得到字体尺寸
     * @return int
     */
    public int getFontSize()
    {
        return getTFont().getSize();
    }
    /**
     * 设置字体类型
     * @param style int
     */
    public void setFontStyle(int style)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),style,f.getSize()));
    }
    /**
     * 得到字体类型
     * @return int
     */
    public int getFontStyle()
    {
        return getTFont().getStyle();
    }
    /**
     * 设置背景颜色
     * @param color String
     */
    public void setBKColor(String color) {
        if(color == null || color.length() == 0)
            return;
        setOpaque(true);//label组件是透明的 所以要改背景色必须设置不透明
        setBackground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * 设置字体颜色
     * @param color String
     */
    public void setColor(String color)
    {
        if(color == null || color.length() == 0)
            return;
        this.setForeground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * 设置边框
     * @param border String
     */
    public void setBorder(String border) {
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
     * 设置自动H
     * @param autoH boolean
     */
    public void setAutoH(boolean autoH)
    {
        this.autoH = autoH;
    }
    /**
     * 是否是自动H
     * @return boolean
     */
    public boolean isAutoH()
    {
        return autoH;
    }
    /**
     * 设置自动H
     * @param autoW boolean
     */
    public void setAutoW(boolean autoW)
    {
        this.autoW = autoW;
    }
    /**
     * 是否是自动W
     * @return boolean
     */
    public boolean isAutoW()
    {
        return autoW;
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
     * 设置自动宽度尺寸
     * @param autoWidthSize int
     */
    public void setAutoWidthSize(int autoWidthSize)
    {
        this.autoWidthSize = autoWidthSize;
    }
    /**
     * 得到自动宽度尺寸
     * @return int
     */
    public int getAutoWidthSize()
    {
        return autoWidthSize;
    }
    /**
     * 设置自定高度尺寸
     * @param autoHeightSize int
     */
    public void setAutoHeightSize(int autoHeightSize)
    {
        this.autoHeightSize = autoHeightSize;
    }
    /**
     * 得到自动高度尺寸
     * @return int
     */
    public int getAutoHeightSize()
    {
        return autoHeightSize;
    }
    /**
     * 设置值
     * @param value String
     */
    public void setValue(String value)
    {
        setText(value);
    }
    /**
     * 得到值
     * @return String
     */
    public String getValue()
    {
        return getText();
    }
    /**
     * 设置光标
     * @param cursorType int
     */
    public void setCursorType(int cursorType)
    {
        this.cursorType = cursorType;
    }
    /**
     * 得到光标
     * @return int
     */
    public int getCursorType()
    {
        return cursorType;
    }
    /**
     * 设置动作消息
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    /**
     * 得到动作消息
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }
    /**
     * 发送动作消息
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
     * 设置父类组件
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent)
    {
        this.parentComponent = parentComponent;
    }
    /**
     * 得到父类组件
     * @return TComponent
     */
    public TComponent getParentComponent()
    {
        return parentComponent;
    }
    public void paint(Graphics g) {
        if(getDrawControl() != null)
            getDrawControl().paintBackground(g);
        super.paint(g);
        if(getDrawControl() != null)
            getDrawControl().paintForeground(g);
    }
    /**
     * 设置居中
     * @param isAutoCenter boolean
     */
    public void setAutoCenter(boolean isAutoCenter)
    {
        this.isAutoCenter = isAutoCenter;
    }
    /**
     * 是否居中
     * @return boolean
     */
    public boolean isAutoCenter()
    {
        return isAutoCenter;
    }
    /**
     * 释放
     */
    public void release()
    {

    }
    /**
     * 设置中文显示
     * @param zhText String
     */
    public void setZhText(String zhText)
    {
        this.zhText = zhText;
    }
    /**
     * 得到中文显示
     * @return String
     */
    public String getZhText()
    {
        return zhText;
    }
    /**
     * 设置英文显示
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * 得到英文显示
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }
    /**
     * 设置语种
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if(language == null)
            return;
        if("zh".equals(language) && getZhText() != null && getZhText().length() > 0)
        {
            setText(getZhText());
            return;
        }
        if("en".equals(language) && getEnText() != null && getEnText().length() > 0)
        {
            setText(getEnText());
            return;
        }
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }
}
