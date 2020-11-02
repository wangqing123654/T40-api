package com.dongyang.ui.base;

import javax.swing.JToolBar;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.util.StringTool;
import com.dongyang.control.TControl;
import java.awt.Container;
import com.dongyang.config.TConfigParm;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TSeparator;
import com.dongyang.ui.TUIStyle;
import java.awt.LayoutManager2;
import java.beans.PropertyChangeListener;
import javax.swing.plaf.UIResource;
import javax.swing.BoxLayout;
import java.io.Serializable;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.awt.Dimension;
import com.dongyang.jdo.MaxLoad;

public class TToolBarBase extends JToolBar implements TComponent, TContainer {
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
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����
     */
    private String language;
    /**
     * Creates a new tool bar; orientation defaults to <code>HORIZONTAL</code>.
     */
    public TToolBarBase() {
        this(HORIZONTAL);
        uiInit();
    }

    /**
     * Creates a new tool bar with the specified <code>orientation</code>.
     * The <code>orientation</code> must be either <code>HORIZONTAL</code>
     * or <code>VERTICAL</code>.
     *
     * @param orientation  the orientation desired
     */
    public TToolBarBase(int orientation) {
        super(null, orientation);
        uiInit();
    }

    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        DefaultToolBarLayout layout =  new DefaultToolBarLayout(getOrientation());
        setLayout( layout );
        addPropertyChangeListener( layout );
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
     * ���ӷָ��
     */
    public void addSeparator() {
        add(new TSeparator());
    }

    /**
     * ��ʼ��
     */
    public void onInit() {
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callMessage("onInit");
            }
        }
//        if(getControl() != null)
//            getControl().onInit();
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
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm) {
        if (parm == null)
            return;
        //�������ö���
        setConfigParm(configParm);
        //����ȫ������
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),
                getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], parm);
        //���ؿ�����
        if (getControl() != null)
            getControl().init();
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
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig";
    }

    /**
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
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
        TComponent parentTComponent = getParentComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        return null;
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
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
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
     * ��ͼ
     * @param g ͼ�ζ���
     */
    public void paintBorder(Graphics g) {
        int w = getWidth() - 1;
        int h = getHeight() - 1;
        Color c1 = TUIStyle.getToolBarBackColor1();
        Color c2 = TUIStyle.getToolBarBackColor2();
        fillTransition(0, 0, w - 1, h - 1, c1, c2, g);

        //�ұ�
        c1 = TUIStyle.getToolBarBackColor3();
        c2 = TUIStyle.getToolBarBackColor4();
        fillTransition(w - 1, 0, 2, h, c1, c2, g);

        drawPot(g, 4, 5, new Color(255, 255, 255));
        drawPot(g, 3, 4, TUIStyle.getToolBarPotColor());

        //�ױ�
        g.setColor(TUIStyle.getToolBarBackColor5());
        g.fillRect(0, h, w, 1);

        //���ϵ���
        g.setColor(new Color(189, 213, 250));
        g.drawLine(1, 0, 0, 1);

        //���϶���
        g.setColor(new Color(158, 190, 245));
        g.fillRect(0, 0, 1, 1);

        //���µ���
        g.setColor(new Color(158, 190, 245));
        g.fillRect(0, h - 1, 2, 2);
        g.setColor(new Color(141, 177, 235));
        g.fillRect(1, h - 1, 1, 1);
        g.setColor(new Color(123, 164, 224));
        g.fillRect(1, h - 2, 1, 1);

        //���϶���
        g.setColor(new Color(158, 190, 245));
        g.fillRect(0, 0, 1, 1);
    }

    public void drawPot(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        for (int i = 0; i < 8; i++)
            g.fillRect(x, y + i * 4, 2, 2);
    }

    /**
     * ����Ϳɫ
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color
     * @param c2 Color
     * @param g Graphics
     */
    public void fillTransition(int x, int y, int width, int height, Color c1,
                               Color c2, Graphics g) {
        for (int i = 0; i <= height; i++) {
            double d = (double) i / (double) height;
            int R = (int) (c1.getRed() - (c1.getRed() - c2.getRed()) * d);
            int G = (int) (c1.getGreen() - (c1.getGreen() - c2.getGreen()) * d);
            int B = (int) (c1.getBlue() - (c1.getBlue() - c2.getBlue()) * d);
            g.setColor(new Color(R, G, B));
            g.fillRect(x, i + y, width, 1);
        }
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
    private class DefaultToolBarLayout
        implements LayoutManager2, Serializable, PropertyChangeListener, UIResource {

        LayoutManager lm;

        DefaultToolBarLayout(int orientation) {
            if (orientation == JToolBar.VERTICAL) {
                lm = new TBoxLayout(TToolBarBase.this, BoxLayout.PAGE_AXIS);
            } else {
                lm = new TBoxLayout(TToolBarBase.this, BoxLayout.LINE_AXIS);
            }
        }

        public void addLayoutComponent(String name, Component comp) {
        }

        public void addLayoutComponent(Component comp, Object constraints) {
        }

        public void removeLayoutComponent(Component comp) {
        }

        public Dimension preferredLayoutSize(Container target) {
            return lm.preferredLayoutSize(target);
        }

        public Dimension minimumLayoutSize(Container target) {
            return lm.minimumLayoutSize(target);
        }

        public Dimension maximumLayoutSize(Container target) {
            if (lm instanceof LayoutManager2) {
                return ((LayoutManager2)lm).maximumLayoutSize(target);
            } else {
                // Code copied from java.awt.Component.getMaximumSize()
                // to avoid infinite recursion.
                // See also java.awt.Container.getMaximumSize()
                return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
            }
        }

        public void layoutContainer(Container target) {
            lm.layoutContainer(target);
        }

        public float getLayoutAlignmentX(Container target) {
            if (lm instanceof LayoutManager2) {
                return ((LayoutManager2)lm).getLayoutAlignmentX(target);
            } else {
                // Code copied from java.awt.Component.getAlignmentX()
                // to avoid infinite recursion.
                // See also java.awt.Container.getAlignmentX()
                return CENTER_ALIGNMENT;
            }
        }

        public float getLayoutAlignmentY(Container target) {
            if (lm instanceof LayoutManager2) {
                return ((LayoutManager2)lm).getLayoutAlignmentY(target);
            } else {
                // Code copied from java.awt.Component.getAlignmentY()
                // to avoid infinite recursion.
                // See also java.awt.Container.getAlignmentY()
                return CENTER_ALIGNMENT;
            }
        }

        public void invalidateLayout(Container target) {
            if (lm instanceof LayoutManager2) {
                ((LayoutManager2)lm).invalidateLayout(target);
            }
        }

        public void propertyChange(PropertyChangeEvent e) {
            String name = e.getPropertyName();
            if( name.equals("orientation") ) {
                int o = ((Integer)e.getNewValue()).intValue();

                if (o == JToolBar.VERTICAL)
                    lm = new TBoxLayout(TToolBarBase.this, BoxLayout.PAGE_AXIS);
                else {
                    lm = new TBoxLayout(TToolBarBase.this, BoxLayout.LINE_AXIS);
                }
            }
        }
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
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent) component;
                tComponent.callFunction("changeLanguage",language);
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
        return null;
    }
}
