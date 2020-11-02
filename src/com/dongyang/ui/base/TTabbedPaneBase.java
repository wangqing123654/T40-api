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
     * �����б�
     */
    private ActionMessage actionList;
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
     * ������
     */
    private TControl control;
    /**
     * ��ҳ�¼���������
     */
    private TChangeListener changeListenerObject;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * ����¼���������
     */
    private TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    private TMouseMotionListener mouseMotionListenerObject;
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
     * �Զ�H
     */
    private boolean autoH;
    /**
     * �Զ�W
     */
    private boolean autoW;
    /**
     * �Զ�W�ߴ�
     */
    private int autoWidthSize;
    /**
     * �Զ�H�ߴ�
     */
    private int autoHeightSize;
    /**
     * tab��ɫ
     */
    private Map tabColorMap = new HashMap();
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����
     */
    private String language;
    /**
     * �ر�ͼ��
     */
    private Icon closeIcon;

    /**
     * ������
     */
    public TTabbedPaneBase() {
        uiInit();
    }
    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        //��ʼ�������б�
        actionList = new ActionMessage();
    }

    /**
     * ����UI
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
        if(changeListenerObject == null)
        {
            changeListenerObject = new TChangeListener(this);
            addChangeListener(changeListenerObject);
        }
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
        addEventListener(getTag() + "->" + TChangeListener.STATE_CHANGED,"stateChanged");
        addEventListener(getTag() + "->" + TTabbedPaneEvent.STATE_CHANGED,"onTabStateChanged");
        TComponent component = getParentTComponent();
        if(component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
    }
    /**
     * ����Tab��ҳ
     * @param tComponent TComponent
     */
    public void onTabStateChanged(TComponent tComponent)
    {
        if(tComponent == null)
            return;
        tComponent.callFunction("onShowWindow");
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
     * ��������
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
    public void onInit() {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
        //��ʼ��������
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
        //���ȫ�����
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
    public String getDownLoadIndex() {
        return "ControlClassName,ControlConfig,Item";
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
        //����Ĭ�϶���
        //value = onDefaultActionMessage(message,parm);
        //if(value != null)
        //    return value;
        //�����Ӽ�����Ϣ
        value = onTagBaseMessage(message, parm);
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
        return null;
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
     * �����Ӽ�����Ϣ
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
     * ����Tag����
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
     * �õ��齨λ��
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
     * �޸ı���
     * @param tag String
     * @param title String
     */
    public void setTitleAt(String tag, String title) {
        int index = findObjectIndex(tag);
        setTitleAt(index,title);
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
    }

    /**
     * ������Ŀ
     * @param message String
     */
    public void setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
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
     * ���س�Ա���
     * @param value String
     */
    private void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //���ID
        String cid = values[0];
        //�������
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
     * �����
     * @param tag String ��ǩ
     * @param parm TConfigParm ���ò�������
     * @param parameter Object �������
     */
    public void openPanel(String tag,TConfigParm parm, Object parameter)
    {
        openPanel(tag,parm,parameter,new TParm());
    }
    /**
     * �����
     * @param tag String ��ǩ
     * @param parm TConfigParm ���ò�������
     * @param parameter Object �������
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
        //��������
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
        //�ͷ�
        panel.release();
        TComponent tComponent = getSelectedTComponent();
        if(tComponent == null)
            return;
        tComponent.callFunction("showTopMenu");
    }
    /**
     * ɾ�����
     * @param tag String ��ǩ
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
        //�ͷ�
        panel.release();
        TComponent tComponent = getSelectedTComponent();
        if(tComponent == null)
            return;
        tComponent.callFunction("showTopMenu");
    }
    /**
     * �����
     * @param tag String ��ǩ
     * @param configName String ���ò����ļ���
     * @param parameter Object �������
     */
    public void openPanel(String tag,String configName,Object parameter)
    {
        openPanel(tag,configName,parameter,new TParm());
    }
    /**
     * �����
     * @param tag String ��ǩ
     * @param configName String ���ò����ļ���
     * @param parameter Object �������
     * @param popedem Object Ȩ��
     */
    public void openPanel(String tag,String configName,Object parameter,Object popedem)
    {
        openPanel(tag,getConfigParm().newConfig(configName),parameter,popedem);
    }
    /**
     * ��ͼ
     * @param g Graphics
     */
    public void paint(Graphics g) {
        fillTransitionH(0, 0, getWidth(), 22, new Color(196, 218, 250),
                        new Color(252, 253, 254), g);
        g.fillRect(0,22,getWidth(),getHeight() - 23);
        g.setFont(new Font("����",0,24));
        g.setColor(new Color(128,128,128));
        g.drawString("TBuilder V1.0",getWidth() - 199,getHeight() - 49);
        g.setColor(new Color(255,0,0));
        g.drawString("TBuilder V1.0",getWidth() - 200,getHeight() - 50);
        g.setFont(new Font("����",0,12));
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
     * ��ҳ�¼�
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
     * �õ�ѡ�е��齨
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
     * �����Զ�H
     * @param autoH boolean
     */
    public void setAutoH(boolean autoH)
    {
        this.autoH = autoH;
    }
    /**
     * �Ƿ����Զ�H
     * @return boolean
     */
    public boolean isAutoH()
    {
        return autoH;
    }
    /**
     * �����Զ�H
     * @param autoW boolean
     */
    public void setAutoW(boolean autoW)
    {
        this.autoW = autoW;
    }
    /**
     * �Ƿ����Զ�W
     * @return boolean
     */
    public boolean isAutoW()
    {
        return autoW;
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
     * �����Զ���ȳߴ�
     * @param autoWidthSize int
     */
    public void setAutoWidthSize(int autoWidthSize)
    {
        this.autoWidthSize = autoWidthSize;
    }
    /**
     * �õ��Զ���ȳߴ�
     * @return int
     */
    public int getAutoWidthSize()
    {
        return autoWidthSize;
    }
    /**
     * �����Զ��߶ȳߴ�
     * @param autoHeightSize int
     */
    public void setAutoHeightSize(int autoHeightSize)
    {
        this.autoHeightSize = autoHeightSize;
    }
    /**
     * �õ��Զ��߶ȳߴ�
     * @return int
     */
    public int getAutoHeightSize()
    {
        return autoHeightSize;
    }
    /**
     * ���÷�ҳ����
     * @param changedAction String
     */
    public void setChangedAction(String changedAction)
    {
        actionList.setAction("changedAction",changedAction);
    }
    /**
     * �õ���ҳ����
     * @return String
     */
    public String getChangedAction()
    {
        return actionList.getAction("changedAction");
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
            callMessage(actionMessage);
        }
        return "OK";
    }
    /**
     * ѡ��ҳǩ
     * @param index int
     */
    public void setSelectedIndex(int index)
    {
        if(index < 0 || index >= getComponentCount())
            return;
        super.setSelectedIndex(index);
    }
    /**
     * ���ñ�ǩ��ɫ
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
     * ���ñ�ǩ��ɫ
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
     * �õ���ǩ��ɫ
     * @param row int
     * @return Color
     */
    public Color getTabColor(int row)
    {
        return (Color)tabColorMap.get(row);
    }
    /**
     * ����˫������
     * @param action String
     */
    public void setDoubleClickAction(String action)
    {
        actionList.setAction("doubleClickAction",action);
    }
    /**
     * �õ�˫������
     * @return String
     */
    public String getDoubleClickAction()
    {
        return actionList.getAction("doubleClickAction");
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
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * ��������
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
    /**
     * �õ��̳м�����
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        if(getParentComponent() instanceof TContainer)
            return ((TContainer)getParentComponent()).getMaxLoad();
        return null;
    }
    /**
     * �õ�����
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
     * �õ��ر�ͼ��
     * @return Icon
     */
    public Icon getCloseIcon() {
        return closeIcon;
    }
    /**
     * ���ùر�ͼ��
     * @param closeIcon Icon
     */
    public void setCloseIcon(Icon closeIcon)
    {
        this.closeIcon = closeIcon;
    }
}
