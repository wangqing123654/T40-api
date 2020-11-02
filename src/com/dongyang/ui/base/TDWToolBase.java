package com.dongyang.ui.base;

import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import javax.swing.JComponent;
import com.dongyang.control.TControl;
import java.awt.Container;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.event.TComponentListener;
import com.dongyang.ui.datawindow.BackPanel;
import com.dongyang.ui.TScrollPane;
import com.dongyang.config.INode;
import java.awt.Insets;

public class TDWToolBase extends TScrollPane
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
     * 配置参数对象
     */
    private TConfigParm configParm;

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
     * 文件名
     */
    public String fileName;
    /**
     * 内部面板
     */
    private BackPanel backPanel;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 构造器
     */
    public TDWToolBase() {
        setBackPanel(new BackPanel(this));
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
        if(isAutoX())
            setX(insets.left + getAutoSize());
        if(isAutoY())
            setY(insets.top + getAutoSize());
        if(isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize());
        }
        if(isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize());
        }
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
        TComponent parentTComponent = getParentTComponent();
        if(parentTComponent != null)
        {
            value = parentTComponent.callMessage(message,parm);
            if(value != null)
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
     * 设置背景颜色
     * @param color String
     */
    public void setBKColor(String color) {
        if(color == null || color.length() == 0)
            return;
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
        return "";
    }
    /**
     * 设置文件名
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
        if(fileName != null)
            getBackPanel().loadFile(fileName);
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
     * 设置内部面板
     * @param backPanel BackPanel
     */
    public void setBackPanel(BackPanel backPanel)
    {
        if(this.backPanel == backPanel)
            return;
        if(this.backPanel != null)
            getViewport().remove(this.backPanel);
        this.backPanel = backPanel;
        getViewport().add(backPanel);
    }
    /**
     * 得到内部面板
     * @return BackPanel
     */
    public BackPanel getBackPanel()
    {
        return backPanel;
    }
    /**
     * 保存
     * @param filename String
     */
    public void saveFile(String filename)
    {
        getBackPanel().saveFile(filename);
    }
    /**
     * 保存文件
     */
    public void saveFile()
    {
        if(getFileName() == null || getFileName().length() == 0)
            return;
        saveFile(getFileName());
    }
    /**
     * 设置当前状态
     * @param state 状态
     */
    public void setState(String state)
    {
        getBackPanel().setState(state);
    }
    /**
     * 撤销
     */
    public void onUndo()
    {
        getBackPanel().onUndo();
    }
    /**
     * 恢复
     */
    public void onRedo()
    {
        getBackPanel().onRedo();
    }
    /**
     * 删除
     */
    public void onDelete()
    {
        getBackPanel().onDelete();
    }
    /**
     * 编辑文字
     * @param label String
     */
    public void onEditText(String label)
    {
        getBackPanel().editText(label);
    }
    /**
     * 编辑字体
     * @param font String
     */
    public void onEditFont(String font)
    {
        getBackPanel().editFont(font);
    }
    /**
     * 编辑字体尺寸
     * @param size int
     */
    public void onEditFontSize(int size)
    {
        getBackPanel().editFontSize(size);
    }
    /**
     * 编辑对齐方式
     * @param alignment int
     */
    public void editAlignment(int alignment)
    {
        getBackPanel().editAlignment(alignment);
    }
    /**
     * 粗体
     * @param b boolean
     */
    public void onEditB(boolean b)
    {
        getBackPanel().editB(b);
    }
    /**
     * 粗体
     * @param b boolean
     */
    public void onEditI(boolean b)
    {
        getBackPanel().editI(b);
    }
    /**
     * 得到xml数据对象
     * @return xml数据对象
     */
    public INode getConfig()
    {
        return getBackPanel().getConfig();
    }
    /**
     * 根据SQL语句加载数据
     * @param sql String
     * @return boolean
     */
    public boolean load(String sql)
    {
      return getBackPanel().load(sql);
    }
    /**
     * 设置SQL
     * @param sql String
     */
    public void setSql(String sql)
    {
        getBackPanel().setSql(sql);
    }
    /**
     * 得到SQL
     * @return String
     */
    public String getSql()
    {
        return getBackPanel().getSql();
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
    public void err(String text) {
        System.out.println(text);
    }
}
