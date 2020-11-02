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
     * ����¼���������
     */
    TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    TMouseMotionListener mouseMotionListenerObject;
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
     * �ļ���
     */
    public String fileName;
    /**
     * �ڲ����
     */
    private BackPanel backPanel;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ������
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
        //����Mouse�¼�
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
     * �������ߴ�ı�
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
        if(getControl() != null)
            getControl().onInit();
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
        if(configParm == null)
            return;
        //�������ö���
        setConfigParm(configParm);
        //����ȫ������
        String value[] = configParm.getConfig().getTagList(configParm.getSystemGroup(),getLoadTag(),getDownLoadIndex());
        for(int index = 0;index < value.length;index++)
            if(filterInit(value[index]))
                callMessage(value[index], configParm);
        //���ؿ�����
       if (getControl() != null)
           getControl().init();
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
        if("pic".equalsIgnoreCase(value[0]))
            value[0] = "pictureName";
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
     * ���������������
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
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBKColor(String color) {
        if(color == null || color.length() == 0)
            return;
        setBackground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * ����������ɫ
     * @param color String
     */
    public void setColor(String color)
    {
        if(color == null || color.length() == 0)
            return;
        this.setForeground(StringTool.getColor(color, getConfigParm()));
    }
    /**
     * ���ñ߿�
     * @param border String
     */
    public void setBorder(String border) {
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
        return "";
    }
    /**
     * �����ļ���
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
        if(fileName != null)
            getBackPanel().loadFile(fileName);
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
     * �����ڲ����
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
     * �õ��ڲ����
     * @return BackPanel
     */
    public BackPanel getBackPanel()
    {
        return backPanel;
    }
    /**
     * ����
     * @param filename String
     */
    public void saveFile(String filename)
    {
        getBackPanel().saveFile(filename);
    }
    /**
     * �����ļ�
     */
    public void saveFile()
    {
        if(getFileName() == null || getFileName().length() == 0)
            return;
        saveFile(getFileName());
    }
    /**
     * ���õ�ǰ״̬
     * @param state ״̬
     */
    public void setState(String state)
    {
        getBackPanel().setState(state);
    }
    /**
     * ����
     */
    public void onUndo()
    {
        getBackPanel().onUndo();
    }
    /**
     * �ָ�
     */
    public void onRedo()
    {
        getBackPanel().onRedo();
    }
    /**
     * ɾ��
     */
    public void onDelete()
    {
        getBackPanel().onDelete();
    }
    /**
     * �༭����
     * @param label String
     */
    public void onEditText(String label)
    {
        getBackPanel().editText(label);
    }
    /**
     * �༭����
     * @param font String
     */
    public void onEditFont(String font)
    {
        getBackPanel().editFont(font);
    }
    /**
     * �༭����ߴ�
     * @param size int
     */
    public void onEditFontSize(int size)
    {
        getBackPanel().editFontSize(size);
    }
    /**
     * �༭���뷽ʽ
     * @param alignment int
     */
    public void editAlignment(int alignment)
    {
        getBackPanel().editAlignment(alignment);
    }
    /**
     * ����
     * @param b boolean
     */
    public void onEditB(boolean b)
    {
        getBackPanel().editB(b);
    }
    /**
     * ����
     * @param b boolean
     */
    public void onEditI(boolean b)
    {
        getBackPanel().editI(b);
    }
    /**
     * �õ�xml���ݶ���
     * @return xml���ݶ���
     */
    public INode getConfig()
    {
        return getBackPanel().getConfig();
    }
    /**
     * ����SQL����������
     * @param sql String
     * @return boolean
     */
    public boolean load(String sql)
    {
      return getBackPanel().load(sql);
    }
    /**
     * ����SQL
     * @param sql String
     */
    public void setSql(String sql)
    {
        getBackPanel().setSql(sql);
    }
    /**
     * �õ�SQL
     * @return String
     */
    public String getSql()
    {
        return getBackPanel().getSql();
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
