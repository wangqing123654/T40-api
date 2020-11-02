package com.dongyang.ui.base;

import javax.swing.JMenu;
import com.dongyang.control.TControl;
import com.dongyang.ui.event.BaseEvent;
import javax.swing.Action;
import com.dongyang.config.TConfigParm;
import java.awt.Container;
import com.dongyang.util.StringTool;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Insets;
import javax.swing.border.Border;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.TMenuBar;
import com.dongyang.ui.TMenu;
import com.dongyang.ui.TMenuItem;
import com.dongyang.jdo.MaxLoad;

public class TMenuBase extends JMenu
        implements TComponent,TContainer{
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
     * �����ı�
     */
    private String text;
    /**
     * �����ı�
     */
    private String zhText;
    /**
     * Ӣ���ı�
     */
    private String enText;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ����
     */
    private String language;
    /**
     * Constructs a new <code>TMenu</code> with no text.
     */
    public TMenuBase() {
        this("");
    }

    /**
     * Constructs a new <code>TMenu</code> with the supplied string
     * as its text.
     *
     * @param s  the text for the menu label
     */
    public TMenuBase(String s) {
        super(s);
        uiInit();
    }

    /**
     * Constructs a menu whose properties are taken from the
     * <code>Action</code> supplied.
     * @param a an <code>Action</code>
     *
     * @since 1.3
     */
    public TMenuBase(Action a) {
        this();
        setAction(a);
    }

    /**
     * Constructs a new <code>TMenu</code> with the supplied string as
     * its text and specified as a tear-off menu or not.
     *
     * @param s the text for the menu label
     * @param b can the menu be torn off (not yet implemented)
     */
    public TMenuBase(String s, boolean b) {
        this(s);
    }
    /**
     * UI��ʼ��
     */
    public void uiInit()
    {
        setBackground(TUIStyle.getMenuBackColor());
        setFont(TUIStyle.getMenuFont());
        getPopupMenu().setBorder(new Border(){
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
            {
                int x1 = 0;
                if(getParentComponent() instanceof TMenuBar)
                    x1 = getWidth() - 1;
                g.setColor(TUIStyle.getMenuBorderColor());
                g.drawLine(0,0,0,height - 1);
                g.drawLine(x1,0,width - 1,0);
                g.drawLine(width - 1,0,width - 1,height - 1);
                g.drawLine(0,height - 1,width - 1,height - 1);
            }
            public Insets getBorderInsets(Component c)
            {
                return new Insets(1,1,1,1);
            }
            public boolean isBorderOpaque()
            {
                return true;
            }
        });
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
     * ���ø������
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent)
    {
        this.parentComponent = parentComponent;
    }
    /**
     * �õ��������
     * @return TComponent
     */
    public TComponent getParentComponent()
    {
        return parentComponent;
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        for (int i = 0; i < getMenuComponentCount(); i++)
        {
            Component menu = getMenuComponent(i);
            if(menu instanceof TComponent)
            {
                TComponent tComponent = (TComponent)menu;
                tComponent.callMessage("onInit");
            }
        }
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
    protected boolean filterInit(String message) {
        String value[] = StringTool.getHead(message, "=");
        if ("toolBar".equalsIgnoreCase(value[0]))
            return false;
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
    public BaseEvent getBaseEventObject()
    {
      return baseEvent;
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
        //�����Ӽ�����Ϣ
        value= onTagBaseMessage(message,parm);
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
        TComponent parentTComponent = getParentComponent();
        if(parentTComponent != null)
        {
            value = parentTComponent.callMessage(message,parm);
            if(value != null)
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
    protected Object onTagBaseMessage(String message,Object parm)
    {
        if(message == null)
            return null;
        String value[] = StringTool.getHead(message,"|");
        TComponent component = findObject(value[0]);
        if(component == null)
            return null;
        return component.callMessage(value[1], parm);
    }
    /**
     * ����Tag����
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag)
    {
        if(tag == null || tag.length() == 0)
            return null;
        for (int i = 0; i < getMenuComponentCount(); i++) {
            Component component = getMenuComponent(i);
            if (component instanceof TComponent) {
                TComponent tComponent = (TComponent)component;
                if(tag.equalsIgnoreCase(tComponent.getTag()))
                    return tComponent;
                if(tComponent instanceof TContainer)
                {
                    TContainer container = (TContainer)tComponent;
                    TComponent value = container.findObject(tag);
                    if(value != null)
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
     * ���ÿ�ݼ�
     * @param c char
     */
    public void setM(char c)
    {
        setMnemonic(c);
    }
    /**
     * �õ���ݼ�
     * @return char
     */
    public char getM()
    {
        return (char)getMnemonic();
    }
    /**
     * ������Ŀ
     * @param message String
     * @return Object
     */
    public Object setItem(String message)
    {
        String s[] = StringTool.parseLine(message,';',"[]{}()");
        for(int i = 0;i < s.length;i++)
            createItem(s[i]);
        return "OK";
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
     * ���������
     * @param value String
     */
    private void createItem(String value)
    {
        //���ߴ���ָ���
        if(value.equals("|"))
        {
            addSeparator();
            return;
        }
        String values[] = StringTool.parseLine(value,"|");
        if(values.length == 0)
            return;
        //���ID
        String cid = values[0];
        //�������
        String type = getConfigParm().getConfig().getString(getConfigParm().getSystemGroup(),cid + ".type");
        if(type.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(type);
        if(obj == null || !(obj instanceof Component) )
        {
            err("loadObject " + type + " ʧ�� obj=" + obj);
            return;
        }
        Component component = (Component)obj;
        if(component instanceof TComponent)
        {
            TComponent tComponent = (TComponent)component;
            tComponent.setTag(cid);
            String configValue = getConfigParm().getConfig().getString(getConfigParm().getSystemGroup(),cid + ".Config");
            if(configValue.length() == 0)
                tComponent.init(getConfigParm());
            else
            {
                tComponent.callMessage("setLoadTag|UI");
                tComponent.init(getConfigParm().newConfig(configValue));
            }
        }
        //���ø������
        if(component instanceof TMenu)
            ((TMenu)component).setParentComponent(this);
        if(component instanceof TMenuItem)
            ((TMenuItem)component).setParentComponent(this);
        if(values.length == 1)
            add(component);
        else if(values.length == 2)
            add(component,StringTool.layoutConstraint(values[1]));
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
        if(this.getMnemonic() > 0)
            super.setText(text + "(" + (char)getMnemonic() + ")");
        else
            super.setText(text);
    }
    /**
     * ���������ı�
     * @param zhText String
     */
    public void setZhText(String zhText)
    {
        this.zhText = zhText;
    }
    /**
     * �õ������ı�
     * @return String
     */
    public String getZhText()
    {
        return zhText;
    }
    /**
     * ����Ӣ���ı�
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * �õ�Ӣ�����ı�
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }
    /**
     * ���ÿ�ݼ�
     * @param mnemonic char
     */
    public void setMnemonic(char mnemonic)
    {
        super.setMnemonic(mnemonic);
        if(mnemonic != 0)
            setText(getTextString());
    }
    /**
     * ���ػ����ı���Ϣ
     * @return String
     */
    public String getTextString()
    {
        return text;
    }
    /**
     * ��ͼ
     * @param g Graphics
     */
    public void paint(Graphics g) {
        if(getParentComponent() != null && getParentComponent() instanceof TMenuBar)
        {
            paintMenu(g);
            return;
        }
        paintMenuItem(g);
        //super.paint(g);
    }
    /**
     * ��ͼ
     * @param g Graphics
     */
    public void paintMenuItem(Graphics g)
    {
        g.setColor(TUIStyle.getMenuItemBackColor());
        g.fillRect(0,0,getWidth(),getHeight());

        int picSize = TUIStyle.getMenuItemIconSize();
        fillTransitionW(0,0,picSize + 6,getHeight(),new Color(227,239,255),
                       new Color(136,174,228),g);
        if(getModel().isRollover() || isPopupMenuVisible())
        {
            g.setColor(TUIStyle.getMenuBorderColor());
            g.drawRect(2,1,getWidth() - 4,getHeight() - 3);
            fillTransitionW(3,2,getWidth() - 5,getHeight() - 4,new Color(255,242,200),new Color(255,215,194),g);
        }
        drawMenuItemText(g);
        drawL(g);
    }
    /**
     * �����ײ˵�
     * @param g Graphics
     */
    public void paintMenu(Graphics g)
    {
        if(!getModel().isRollover()&&!getModel().isSelected())
        {
            super.paint(g);
            return;
        }
        g.setColor(TUIStyle.getMenuBackColor());
        g.fillRect(0,0,getWidth(),getHeight());
        if(!isSelected())
        {
            g.setColor(TUIStyle.getMenuBorderColor());
            g.drawRect(0,0,getWidth() - 1,getHeight() - 1);
            fillTransitionH(1,1,getWidth() - 2,getHeight() - 2,new Color(255,242,200),new Color(255,215,194),g);
        }else
        {
            g.setColor(TUIStyle.getMenuBorderColor());
            g.drawRect(0,0,getWidth() - 1,getHeight());
            fillTransitionH(1,1,getWidth() - 2,getHeight() - 1,new Color(227,239,255),new Color(152,185,232),g);
        }
        drawMenuText(g);
    }
    /**
     * ��������
     * @param g Graphics
     */
    public void drawMenuItemText(Graphics g)
    {
        int x = TUIStyle.getMenuItemIconSize() + 14;
        if(isEnabled())
            g.setColor(new Color(0,0,0));
        else
            g.setColor(new Color(141,141,141));
        FontMetrics metrics = getFontMetrics(getFont());
        int y = metrics.getAscent() + 2;
        g.setFont(getFont());
        g.drawString(getText(),x,y);
        if(getMnemonic() == 0)
            return;
        String s = "" + (char)getMnemonic();
        int index = getText().indexOf(s);
        if(index == -1)
            return;
        x += metrics.stringWidth(getText().substring(0,index));
        int w = metrics.stringWidth(s);
        y += 1;
        g.drawLine(x,y,x + w - 1,y);
    }
    /**
     * ���Ƽ�ͷ
     * @param g Graphics
     */
    public void drawL(Graphics g)
    {
        int x = getWidth() - 20;
        int y = (int)((double)getHeight() / 2.0);
        if(isEnabled())
            g.setColor(new Color(0,0,0));
        else
            g.setColor(new Color(141,141,141));
        for(int i = 4;i >= 0;i--)
            g.drawLine(x + 4 - i,y - i,x + 4 - i,y + i);
    }
    /**
     * ��������
     * @param g Graphics
     */
    public void drawMenuText(Graphics g)
    {
        g.setColor(new Color(0,0,0));
        FontMetrics metrics = getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = metrics.getAscent() + 2;
        g.setFont(getFont());
        g.drawString(getText(),x,y);
        if(getMnemonic() == 0)
            return;
        String s = "" + (char)getMnemonic();
        int index = getText().indexOf(s);
        if(index == -1)
            return;
        x += metrics.stringWidth(getText().substring(0,index));
        int w = metrics.stringWidth(s);
        y += 1;
        g.drawLine(x,y,x + w - 1,y);
    }
    /**
     * ���
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color
     * @param c2 Color
     * @param g Graphics
     */
    public void fillTransitionH(int x, int y, int width, int height, Color c1,
                               Color c2, Graphics g)
    {
      for (int i = 0; i < height; i++)
      {
        double d = (double) i / (double) height;
        int R = (int) (c1.getRed() - (c1.getRed() - c2.getRed()) * d);
        int G = (int) (c1.getGreen() - (c1.getGreen() - c2.getGreen()) * d);
        int B = (int) (c1.getBlue() - (c1.getBlue() - c2.getBlue()) * d);
        g.setColor(new Color(R, G, B));
        g.fillRect(x, i + y, width, 1);
      }
    }
    /**
     * ���ƽ���
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color
     * @param c2 Color
     * @param g Graphics
     */
    public void fillTransitionW(int x, int y, int width, int height, Color c1,
                               Color c2, Graphics g)
    {
      for (int i = 0; i < width; i++)
      {
        double d = (double) i / (double) width;
        int R = (int) (c1.getRed() - (c1.getRed() - c2.getRed()) * d);
        int G = (int) (c1.getGreen() - (c1.getGreen() - c2.getGreen()) * d);
        int B = (int) (c1.getBlue() - (c1.getBlue() - c2.getBlue()) * d);
        g.setColor(new Color(R, G, B));
        g.fillRect(i + x, y, 1, height);
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
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if(language == null)
            return;
        for (int i = 0; i < getMenuComponentCount(); i++) {
            Component component = getMenuComponent(i);
            if (component instanceof TMenuItem)
                ((TMenuItem)component).changeLanguage(language);
        }
        if(language.equals(this.language))
            return;
        this.language = language;
        if("en".equals(language) && getEnText() != null && getEnText().length() > 0)
        {
            if(getMnemonic() > 0 && getEnText().toUpperCase().indexOf("" + (char)getMnemonic()) == -1)
                super.setText(getEnText() + "(" + (char)getMnemonic() + ")");
            else
                super.setText(getEnText());
        }
        else if(getZhText() != null && getZhText().length() > 0)
            setText(getZhText());
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * �ͷ�
     */
    public void release()
    {

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
