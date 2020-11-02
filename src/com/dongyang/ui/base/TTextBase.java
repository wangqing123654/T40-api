package com.dongyang.ui.base;

import javax.swing.JComponent;
import com.dongyang.control.TControl;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.config.TConfigParm;
import java.awt.Container;
import java.awt.BorderLayout;
import com.dongyang.util.StringTool;
import javax.swing.JPanel;
import com.dongyang.ui.text.TTextUI;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.io.IOException;
import com.dongyang.config.INode;
import com.dongyang.config.TNode;
import com.dongyang.ui.TComponent;
import com.dongyang.util.RunClass;

public class TTextBase extends JPanel
        implements TComponent{
    /**
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 文本
     */
    private String text = "";
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
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * UI
     */
    private TTextUI UI;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 构造器
     */
    public TTextBase()
    {
        UI = new TTextUI(this);
        setLayout(new BorderLayout());
        this.add(getTextUI(), BorderLayout.CENTER);
        this.add(getTextUI().getPage().getScrollBarW(), BorderLayout.SOUTH);
        this.add(getTextUI().getPage().getScrollBarH(), BorderLayout.EAST);
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
     * Returns the button's text.
     * @return the buttons text
     * @see #setText
     */
    public String getText()
    {
        return text;
    }
    /**
     * Sets the button's text.
     * @param text the string used to set the text
     * @see #getText
     *  description: The button's text.
     */
    public void setText(String text)
    {
        this.text = text;
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
     * 得到UI对象
     * @return TTextUI
     */
    public TTextUI getTextUI()
    {
        return UI;
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
     * 加载服务器文件
     * @param socket TSocket
     * @param fileName String 文件名
     */
    public void readServerFile(TSocket socket,String fileName)
    {
        read(TIOM_AppServer.readFile(socket,fileName));
    }
    /**
     * 加载服务器文件
     * @param fileName String 文件名
     */
    public void readServerFile(String fileName)
    {
        read(TIOM_AppServer.readFile(fileName));
    }
    /**
     * 读取本地文件
     * @param fileName String 文件名
     */
    public void readFile(String fileName)
    {
        try{
            read(FileTool.getByte(fileName));
        }catch(IOException e)
        {
            err(e.getMessage());
        }
    }
    /**
     * 读取数据
     * @param data byte[] 数据
     */
    public void read(byte[] data)
    {
        read(TNode.loadXML(data));
    }
    /**
     * 读取数据
     * @param data String 字符数据
     */
    public void read(String data)
    {
        read(TNode.loadXML(data));
    }
    /**
     * 读取数据
     * @param node INode
     */
    public void read(INode node)
    {
        getTextUI().getPage().read(node);
    }
    /**
     * 写服务器文件
     * @param socket TSocket
     * @param fileName String 文件名
     */
    public void writeServerFile(TSocket socket,String fileName)
    {
        byte data[] = write();
        if(data == null)
            return;
        TIOM_AppServer.writeFile(socket,fileName,data);
    }
    /**
     * 写服务器文件
     * @param fileName String 文件名
     */
    public void writeServerFile(String fileName)
    {
        byte data[] = write();
        if(data == null)
            return;
        TIOM_AppServer.writeFile(fileName,data);
    }
    /**
     * 写本地文件
     * @param fileName String 文件名
     */
    public void writeFile(String fileName)
    {
        byte data[] = write();
        if(data == null)
            return;
        try{
            FileTool.setByte(fileName, data);
        }catch(IOException e)
        {
            err(e.getMessage());
        }
    }
    /**
     * 写数据
     * @return String 数据
     */
    public String writeString()
    {
        return getTextUI().getPage().write();
    }
    /**
     * 写数据
     * @return byte[] 数据
     */
    public byte[] write()
    {
        String s = writeString();
        if(s == null)
            return null;
        return s.getBytes();
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
     * initialize
     * @param parm TConfigParm
     */
    /**
     * 初始化
     */
    public void onInit()
    {
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        if(getControl() != null)
            getControl().onInit();
    }
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm)
    {
        if(configParm == null)
            return;
        setConfigParm(configParm);
        if(configParm.getSystemGroup() == null)
            configParm.setSystemGroup("");
        String value[] = configParm.getConfig().getTagList(configParm.getSystemGroup(),getLoadTag(),getDownLoadIndex());
        for(int index = 0;index < value.length;index++)
            if(filterInit(value[index]))
                callMessage(value[index], configParm);
    }
    /**
     * 过滤初始化信息
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message)
    {
        return true;
    }
    /**
     * 加载顺序
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig";
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
        //处理基本消息
        Object value = onBaseMessage(message,parm);
        if(value != null)
            return value;
        //处理控制类的消息
        if(getControl() != null)
        {
            value = getControl().callMessage(message,parm);
            if(value != null)
                return value;
        }
        //消息上传
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
        if("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
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
     * 设置组件类名
     * @param value String
     */
    public void setControlClassName(String value)
    {
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
