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
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * �ı�
     */
    private String text = "";
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
     * UI
     */
    private TTextUI UI;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ������
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
     * ����X����
     * @param x int
     */
    public void setX(int x)
    {
        this.setLocation(x,getLocation().y);
    }
    /**
     * ����Y����
     * @param y int
     */
    public void setY(int y)
    {
        this.setLocation(getLocation().x,y);
    }
    /**
    * ���ÿ��
    * @param width int
    */
   public void setWidth(int width)
    {
        this.setSize(width,getSize().height);
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.setSize(getSize().width,height);
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
   public void setControl(TControl control)
    {
        this.control = control;
        if(control != null)
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
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject()
    {
      return baseEvent;
    }
    /**
     * �õ�UI����
     * @return TTextUI
     */
    public TTextUI getTextUI()
    {
        return UI;
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
     * ���ط������ļ�
     * @param socket TSocket
     * @param fileName String �ļ���
     */
    public void readServerFile(TSocket socket,String fileName)
    {
        read(TIOM_AppServer.readFile(socket,fileName));
    }
    /**
     * ���ط������ļ�
     * @param fileName String �ļ���
     */
    public void readServerFile(String fileName)
    {
        read(TIOM_AppServer.readFile(fileName));
    }
    /**
     * ��ȡ�����ļ�
     * @param fileName String �ļ���
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
     * ��ȡ����
     * @param data byte[] ����
     */
    public void read(byte[] data)
    {
        read(TNode.loadXML(data));
    }
    /**
     * ��ȡ����
     * @param data String �ַ�����
     */
    public void read(String data)
    {
        read(TNode.loadXML(data));
    }
    /**
     * ��ȡ����
     * @param node INode
     */
    public void read(INode node)
    {
        getTextUI().getPage().read(node);
    }
    /**
     * д�������ļ�
     * @param socket TSocket
     * @param fileName String �ļ���
     */
    public void writeServerFile(TSocket socket,String fileName)
    {
        byte data[] = write();
        if(data == null)
            return;
        TIOM_AppServer.writeFile(socket,fileName,data);
    }
    /**
     * д�������ļ�
     * @param fileName String �ļ���
     */
    public void writeServerFile(String fileName)
    {
        byte data[] = write();
        if(data == null)
            return;
        TIOM_AppServer.writeFile(fileName,data);
    }
    /**
     * д�����ļ�
     * @param fileName String �ļ���
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
     * д����
     * @return String ����
     */
    public String writeString()
    {
        return getTextUI().getPage().write();
    }
    /**
     * д����
     * @return byte[] ����
     */
    public byte[] write()
    {
        String s = writeString();
        if(s == null)
            return null;
        return s.getBytes();
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object, String methodName)
    {
        getBaseEventObject().add(eventName,object,methodName);
    }
    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object, String methodName)
    {
      getBaseEventObject().remove(eventName,object,methodName);
    }
    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName,Object[] parms,String[] parmType)
    {
        return getBaseEventObject().callEvent(eventName,parms,parmType);
    }
    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName)
    {
        return callEvent(eventName,new Object[]{},new String[]{});
    }
    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
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
     * ��ʼ��
     */
    public void onInit()
    {
        //��ʼ������׼��
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
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message)
    {
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
    public Object callMessage(String message)
    {
        return callMessage(message,null);
    }
    /**
     * ������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message,Object parm)
    {
        if(message == null ||message.length() == 0)
            return null;
        //���������Ϣ
        Object value = onBaseMessage(message,parm);
        if(value != null)
            return value;
        //������������Ϣ
        if(getControl() != null)
        {
            value = getControl().callMessage(message,parm);
            if(value != null)
                return value;
        }
        //��Ϣ�ϴ�
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
        if(container == null)
            return null;
        if(container instanceof TComponent)
            return(TComponent)container;
        return getParentTComponent(container.getParent());
    }
    /**
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message,Object parm)
    {
        if(message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message,"|");
        Object result = null;
        if((result = RunClass.invokeMethodT(this,value,parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message,"=");
        //����������������
        baseFieldNameChange(value);
        if((result = RunClass.invokeFieldT(this,value,parm)) != null)
            return result;
        return null;
    }
    /**
     * ����������������
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[])
    {
        if("bkcolor".equalsIgnoreCase(value[0]))
            value[0] = "background";
    }
    /**
     * �����������
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
     * �����������
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
