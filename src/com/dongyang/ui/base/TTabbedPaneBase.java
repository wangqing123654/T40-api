package com.dongyang.ui.base;

import javax.swing.JTabbedPane;
import com.dongyang.control.TControl;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.config.TConfigParm;
import java.awt.Container;
import com.dongyang.util.StringTool;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Insets;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TPanel;
import com.dongyang.ui.event.TChangeListener;
import javax.swing.event.ChangeEvent;
import com.dongyang.ui.event.TTabbedPaneEvent;
import java.awt.Font;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.event.TComponentListener;
import java.awt.Cursor;
import com.dongyang.data.TParm;
import javax.swing.Icon;
import java.awt.Rectangle;
import java.awt.FontMetrics;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.UIManager;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.util.TSystem;
import com.dongyang.jdo.MaxLoad;
import java.util.Date;

public class TTabbedPaneBase extends JTabbedPane implements TComponent, TContainer {
    /**
     * 动作列表
     */
    private ActionMessage actionList;
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
     * 控制类
     */
    private TControl control;
    /**
     * 翻页事件监听对象
     */
    private TChangeListener changeListenerObject;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 鼠标事件监听对象
     */
    private TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    private TMouseMotionListener mouseMotionListenerObject;
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
     * tab颜色
     */
    private Map tabColorMap = new HashMap();
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 语种
     */
    private String language;
    /**
     * 关闭图标
     */
    private Icon closeIcon;

    /**
     * 构造器
     */
    public TTabbedPaneBase() {
        uiInit();
    }
    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        //初始化动作列表
        actionList = new ActionMessage();
    }

    /**
     * 更新UI
     */
    public void updateUI() {
        if(getUI() != null && getUI() instanceof TTabbedPaneUI)
            return;
        setUI(new TTabbedPaneUI(this));
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
     * 设置加载标签
     * @param loadtag String
     */
    public void setLoadTag(String loadtag) {
        this.loadtag = loadtag;
    }

    /**
     * 得到加载标签
     * @return String
     */
    public String getLoadTag() {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }

    /**
     * 设置X坐标
     * @param x int
     */
    public void setX(int x) {
        this.setLocation(x, getLocation().y);
    }

    /**
     * 设置Y坐标
     * @param y int
     */
    public void setY(int y) {
        this.setLocation(getLocation().x, y);
    }

    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width) {
        this.setSize(width, getSize().height);
    }

    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height) {
        this.setSize(getSize().width, height);
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
    public void setControl(TControl control) {
        this.control = control;
        if (control != null)
            control.setComponent(this);
    }

    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
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
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * 删除监听方法
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},
                         new String[] {"java.lang.Object"});
    }
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        if(changeListenerObject == null)
        {
            changeListenerObject = new TChangeListener(this);
            addChangeListener(changeListenerObject);
        }
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
        addEventListener(getTag() + "->" + TChangeListener.STATE_CHANGED,"stateChanged");
        addEventListener(getTag() + "->" + TTabbedPaneEvent.STATE_CHANGED,"onTabStateChanged");
        TComponent component = getParentTComponent();
        if(component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
    }
    /**
     * 工作Tab翻页
     * @param tComponent TComponent
     */
    public void onTabStateChanged(TComponent tComponent)
    {
        if(tComponent == null)
            return;
        tComponent.callFunction("onShowWindow");
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
        if (!isEnabled())
            return;
        int tabIndex = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if(tabIndex == -1)
            return;
        if(!isEnabledAt(tabIndex))
            return;
        if(e.getButton() == 1)
        {
            boolean close = ((TTabbedPaneUI) getUI()).tabForCloseCoordinate(this,
                    e.getX(), e.getY(), tabIndex);
            if (close)
            {
                closePanel(tabIndex);
                return;
            }
        }
        setSelectedIndex(tabIndex);
        if(e.getClickCount() == 2)
            exeAction(getDoubleClickAction());
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
    public void onInit() {
        //初始化监听事件
        initListeners();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        //初始化控制类
        if (getControl() != null)
            getControl().onInit();
    }

    /**
     * 设置配置参数对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }

    /**
     * 得到配置参数对象
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
        //保存配置对象
        setConfigParm(configParm);
        //清除全部组件
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
    }

    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message) {
        return true;
    }

    /**
     * 加载顺序
     * @return String
     */
    public String getDownLoadIndex() {
        return "ControlClassName,ControlConfig,Item";
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
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * 处理消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //处理基本消息
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //启动默认动作
        //value = onDefaultActionMessage(message,parm);
        //if(value != null)
        //    return value;
        //处理子集的消息
        value = onTagBaseMessage(message, parm);
        if (value != null)
            return value;
        //处理控制类的消息
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //消息上传
        TComponent parentTComponent = getParentTComponent();
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
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * 得到父类(递归用)
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
     * 处理子集的消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onTagBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        TComponent component = findObject(value[0]);
        if (component == null)
            return null;
        return component.callMessage(value[1], parm);
    }

    /**
     * 查找Tag对象
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag) {
        if (tag == null || tag.length() == 0)
            return null;
        for (int i = 0; i < getTabCount(); i++) {
            Component component = getComponentAt(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                if (tag.equalsIgnoreCase(tComponent.getTag()))
                    return tComponent;
                if (tComponent instanceof TContainer) {
                    TContainer container = (TContainer) tComponent;
                    TComponent value = container.findObject(tag);
                    if (value != null)
                        return value;
                }
            }
        }
        return null;
    }
    /**
     * 得到组建位置
     * @param tag String
     * @return int
     */
    public int findObjectIndex(String tag) {
        if (tag == null || tag.length() == 0)
            return -1;
        for (int i = 0; i < getTabCount(); i++) {
            Component component = getComponentAt(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                if (tag.equalsIgnoreCase(tComponent.getTag()))
                    return i;
            }
        }
        return -1;
    }
    /**
     * 修改标题
     * @param tag String
     * @param title String
     */
    public void setTitleAt(String tag, String title) {
        int index = findObjectIndex(tag);
        setTitleAt(index,title);
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
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
    protected void baseFieldNameChange(String value[]) {
        if ("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
    }

    /**
     * 设置项目
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
    }

    /**
     * 设置组件配置
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
     * 设置组件类名
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
     * 加载成员组件
     * @param value String
     */
    private void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //组件ID
        String cid = values[0];
        //组件类型
        String type = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), cid + ".type");
        if (type.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof Component))
            return;
        Component component = (Component) obj;
        String title = "";
        if (component instanceof TComponent) {
            TComponent tComponent = (TComponent) component;
            tComponent.setTag(cid);
            tComponent.init(getConfigParm());
            tComponent.setParentComponent(this);
            title = (String) tComponent.callMessage("getName");
            if (title == null)
                title = "";
        }
        if (values.length == 1)
            addTab(title, component);
    }
    /**
     * 打开面板
     * @param tag String 标签
     * @param parm TConfigParm 配置参数对象
     * @param parameter Object 传入参数
     */
    public void openPanel(String tag,TConfigParm parm, Object parameter)
    {
        openPanel(tag,parm,parameter,new TParm());
    }
    /**
     * 打开面板
     * @param tag String 标签
     * @param parm TConfigParm 配置参数对象
     * @param parameter Object 传入参数
     * @param popedem Object
     */
    public void openPanel(String tag,TConfigParm parm, Object parameter,Object popedem)
    {
        Date d1 = new Date();
        TComponent component = findObject(tag);
        if(component != null)
        {
            setSelectedComponent((Component)component);
            return;
        }
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        TPanel panel = new TPanel();
        panel.setTag(tag);
        panel.setLoadTag("UI");
        panel.init(parm);
        panel.setParameter(parameter);
        panel.setPopedem(popedem);
        panel.setParentComponent(this);
        String title = panel.getName();
        String language = (String)TSystem.getObject("Language");
        if("en".equals(language) && panel.getEnTitle() != null && panel.getEnTitle().length() > 0)
            title = panel.getEnTitle();
        else if("zh".equals(language) && panel.getZhTitle() != null && panel.getZhTitle().length() > 0)
            title = panel.getZhTitle();
        else if(title == null||title.length() == 0)
            title = panel.getTitle();
        addTab(title, panel);
        //Date d2 = new Date();
        //System.out.println(panel.getTitle() + ".openPanel.init.time->" + (d2.getTime() - d1.getTime()));
        panel.onInit();
        //Date d3 = new Date();
        //System.out.println(panel.getTitle() + ".openPanel.onInit.time->" + (d3.getTime() - d2.getTime()) + " " + panel.isLoadFlg());
        //调整语种
        panel.changeLanguage(language);
        setSelectedComponent((Component)panel);
        setCursor(new Cursor(0));
        //Date d4 = new Date();
        //System.out.println(panel.getTitle() + ".openPanel.Language.time->" + (d4.getTime() - d3.getTime()));
        //System.out.println(panel.getTitle() + ".openPanel.all.time->" + (d4.getTime() - d1.getTime()));
    }
    public void closePanel(int index)
    {
        if(index == -1)
            return;
        Component component = getComponentAt(index);
        if(component == null)
            return;
        if(!(component instanceof TPanel))
            return;
        TPanel panel = (TPanel)component;
        remove(panel);
        callFunction("removeChildMenuBar");
        callFunction("removeChildToolBar");
        //释放
        panel.release();
        TComponent tComponent = getSelectedTComponent();
        if(tComponent == null)
            return;
        tComponent.callFunction("showTopMenu");
    }
    /**
     * 删除面板
     * @param tag String 标签
     */
    public void closePanel(String tag)
    {
        TComponent component = findObject(tag);
        if(component == null)
            return;
        if(!(component instanceof TPanel))
            return;
        TPanel panel = (TPanel)component;
        remove(panel);
        callFunction("removeChildMenuBar");
        callFunction("removeChildToolBar");
        //释放
        panel.release();
        TComponent tComponent = getSelectedTComponent();
        if(tComponent == null)
            return;
        tComponent.callFunction("showTopMenu");
    }
    /**
     * 打开面板
     * @param tag String 标签
     * @param configName String 配置参数文件名
     * @param parameter Object 传入参数
     */
    public void openPanel(String tag,String configName,Object parameter)
    {
        openPanel(tag,configName,parameter,new TParm());
    }
    /**
     * 打开面板
     * @param tag String 标签
     * @param configName String 配置参数文件名
     * @param parameter Object 传入参数
     * @param popedem Object 权限
     */
    public void openPanel(String tag,String configName,Object parameter,Object popedem)
    {
        openPanel(tag,getConfigParm().newConfig(configName),parameter,popedem);
    }
    /**
     * 绘图
     * @param g Graphics
     */
    public void paint(Graphics g) {
        fillTransitionH(0, 0, getWidth(), 22, new Color(196, 218, 250),
                        new Color(252, 253, 254), g);
        g.fillRect(0,22,getWidth(),getHeight() - 23);
        g.setFont(new Font("宋体",0,24));
        g.setColor(new Color(128,128,128));
        g.drawString("TBuilder V1.0",getWidth() - 199,getHeight() - 49);
        g.setColor(new Color(255,0,0));
        g.drawString("TBuilder V1.0",getWidth() - 200,getHeight() - 50);
        g.setFont(new Font("宋体",0,12));
        g.setColor(new Color(128,128,128));
        g.drawString("- JavaHis Tools",getWidth() - 135,getHeight() - 29);
        g.setColor(new Color(255,0,0));
        g.drawString("- JavaHis Tools",getWidth() - 136,getHeight() - 30);
        super.paint(g);
    }

    public void fillTransitionH(int x, int y, int width, int height, Color c1,
                                Color c2, Graphics g) {
        for (int i = 0; i < height; i++) {
            double d = (double) i / (double) height;
            int R = (int) (c1.getRed() - (c1.getRed() - c2.getRed()) * d);
            int G = (int) (c1.getGreen() - (c1.getGreen() - c2.getGreen()) * d);
            int B = (int) (c1.getBlue() - (c1.getBlue() - c2.getBlue()) * d);
            g.setColor(new Color(R, G, B));
            g.fillRect(x, i + y, width, 1);
        }
    }
    /**
     * 翻页事件
     * @param e ChangeEvent
     */
    public void stateChanged(ChangeEvent e)
    {
        TComponent tComponent = getSelectedTComponent();
        callMessage(getTag() + "->" + TTabbedPaneEvent.STATE_CHANGED,tComponent);
        callEvent(getTag() + "->" + TTabbedPaneEvent.STATE_CHANGED,new Object[]{tComponent},new String[]{"com.dongyang.ui.TComponent"});
        exeAction(getChangedAction());
    }
    /**
     * 得到选中的组建
     * @return TComponent
     */
    public TComponent getSelectedTComponent()
    {
        Component com = getSelectedComponent();
        if(com == null)
            return null;
        if(!(com instanceof TComponent))
            return null;
        return (TComponent)com;
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
     * 设置翻页动作
     * @param changedAction String
     */
    public void setChangedAction(String changedAction)
    {
        actionList.setAction("changedAction",changedAction);
    }
    /**
     * 得到翻页动作
     * @return String
     */
    public String getChangedAction()
    {
        return actionList.getAction("changedAction");
    }
    /**
     * 执行动作
     * @param message String 消息
     * @return Object
     */
    public Object exeAction(String message)
    {
        if (message == null || message.length() == 0)
            return null;
        String value[] = StringTool.parseLine(message, ';',"[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            String actionMessage = value[i];
            callMessage(actionMessage);
        }
        return "OK";
    }
    /**
     * 选中页签
     * @param index int
     */
    public void setSelectedIndex(int index)
    {
        if(index < 0 || index >= getComponentCount())
            return;
        super.setSelectedIndex(index);
    }
    /**
     * 设置标签颜色
     * @param row int
     * @param color Color
     */
    public void setTabColor(int row,Color color)
    {
        if(color == null)
        {
            tabColorMap.remove(row);
            repaint();
            return;
        }
        tabColorMap.put(row,color);
        repaint();
    }
    /**
     * 设置标签颜色
     * @param s String
     */
    public void setTabColor(String s)
    {
        if(s == null || s.length() == 0)
            return;
        String d[] = StringTool.parseLine(s,";");
        for(int i = 0;i < d.length;i++)
        {
            String c[] = StringTool.parseLine(d[i],":");
            if(c.length != 2)
                continue;
            int row = StringTool.getInt(c[0]);
            Color color = StringTool.getColor(c[1],getConfigParm());
            setTabColor(row,color);
        }
    }
    /**
     * 得到标签颜色
     * @param row int
     * @return Color
     */
    public Color getTabColor(int row)
    {
        return (Color)tabColorMap.get(row);
    }
    /**
     * 设置双击动作
     * @param action String
     */
    public void setDoubleClickAction(String action)
    {
        actionList.setAction("doubleClickAction",action);
    }
    /**
     * 得到双击动作
     * @return String
     */
    public String getDoubleClickAction()
    {
        return actionList.getAction("doubleClickAction");
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
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * 设置语种
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if(language == null)
            return;
        if(language.equals(this.language))
            return;
        this.language = language;
        for (int i = 0; i < getTabCount(); i++)
        {
            Component component = getComponentAt(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callFunction("changeLanguage",language);

                if(component instanceof TPanel)
                {
                    TPanel panel = (TPanel)component;
                    if("en".equals(language) && panel.getEnTitle() != null && panel.getEnTitle().length() > 0)
                        setTitleAt(i,panel.getEnTitle());
                    else if("zh".equals(language) && panel.getZhTitle() != null && panel.getZhTitle().length() > 0)
                        setTitleAt(i,panel.getZhTitle());
                    else if(panel.getName() != null && panel.getName().length() > 0)
                        setTitleAt(i,panel.getName());
                }
            }
        }
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
    /**
     * 得到继承加载器
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        if(getParentComponent() instanceof TContainer)
            return ((TContainer)getParentComponent()).getMaxLoad();
        return null;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getFont() {
        Font f = super.getFont();
        if(f == null)
            return f;
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
     * 得到关闭图标
     * @return Icon
     */
    public Icon getCloseIcon() {
        return closeIcon;
    }
    /**
     * 设置关闭图标
     * @param closeIcon Icon
     */
    public void setCloseIcon(Icon closeIcon)
    {
        this.closeIcon = closeIcon;
    }
}
