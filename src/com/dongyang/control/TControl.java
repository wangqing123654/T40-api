package com.dongyang.control;

import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.TComponent;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import java.awt.Component;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.data.TParm;
import com.dongyang.util.RunClass;
import com.dongyang.ui.TDialog;
import com.dongyang.ui.TFrame;
import com.dongyang.config.TConfig;
import java.util.Vector;
import com.dongyang.util.TMessage;
import java.sql.Timestamp;
import com.dongyang.data.TNull;
import com.dongyang.ui.TWindow;
import com.dongyang.ui.TPanel;
import com.dongyang.util.TSystem;
import com.dongyang.ui.TRootPanel;

/**
 * @author whao 2013~
 * @version 1.0
 */
public class TControl implements TComponent{

    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         DEFAULT_OPTION = -1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_OPTION = 0;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_CANCEL_OPTION = 1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         OK_CANCEL_OPTION = 2;

    //
    // Return values.
    //
    /** Return value from class method if YES is chosen. */
    public static final int         YES_OPTION = 0;
    /** Return value from class method if NO is chosen. */
    public static final int         NO_OPTION = 1;
    /** Return value from class method if CANCEL is chosen. */
    public static final int         CANCEL_OPTION = 2;
    /** Return value form class method if OK is chosen. */
    public static final int         OK_OPTION = 0;
    /**
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * ����UI
     */
    private TComponent component;
    /**
     * ���ö���
     */
    private TConfigParm configParm;
    /**
     * ��ǩ
     */
    private String tag;
    /**
     * �ӿ���������
     */
    private Map childControls = new HashMap();
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ������
     */
    public TControl()
    {
        setTag("Control");
    }
    /**
     * ��ʼ������
     */
    public void onInitParameter()
    {
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {
        Iterator iterator = childControls.keySet().iterator();
        while(iterator.hasNext())
            ((TChildControl)childControls.get(iterator.next())).onInit();
    }
    /**
     * ���³�ʼ��
     */
    public void onInitReset()
    {

    }
    /**
     *  ��ʼ��
     */
    public void init()
    {
        init(getConfigParm());
    }
    /**
     * ��ʼ��
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm)
    {
        if(parm == null)
            return;
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),getTag());

        for(int index = 0;index < value.length;index++)
            callMessage(value[index], parm);
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
     * �õ�UI��ǩ
     * @return String
     */
    public String getUITag()
    {
        return getComponent().getTag();
    }
    /**
     * ����UI���
     * @param component TComponent
     */
    public void setComponent(TComponent component)
    {
        this.component = component;
    }
    /**
     * �õ�UI���
     * @return TComponent
     */
    public TComponent getComponent()
    {
        return component;
    }
    /**
     * �õ�UI���
     * @param tag String
     * @return TComponent
     */
    public TComponent getComponent(String tag)
    {
        return (TComponent)getComponent().callFunction(tag + "|getThis");
    }
    /**
     * ���ÿ��ƶ���
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm)
    {
        this.configParm = configParm;
    }
    /**
     * �õ����ƶ���
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return configParm;
    }
    /**
     * �õ������ļ�����
     * @return TConfig
     */
    public TConfig getConfig()
    {
        if(getConfigParm() == null)
            return null;
        return getConfigParm().getConfig();
    }
    /**
     * �õ�ϵͳ���
     * @return String
     */
    public String getSystemGroup()
    {
        if(getConfigParm() == null)
            return null;
        return getConfigParm().getSystemGroup();
    }
    /**
     * �õ��ַ�����������
     * @param name String ����
     * @return String ����ֵ
     */
    public String getConfigString(String name)
    {
        return getConfigString(name,"");
    }
    /**
     * �õ��ַ�����������
     * @param name String ����
     * @param defaultValue String Ĭ��ֵ
     * @return String ����ֵ
     */
    public String getConfigString(String name,String defaultValue)
    {
        if(getConfig() == null)
            return "";
        return getConfig().getString(getSystemGroup(),name,defaultValue);
    }
    /**
     * �õ���������������
     * @param name String ����
     * @return int ����ֵ
     */
    public int getConfigInt(String name)
    {
        return getConfigInt(name,0);
    }
    /**
     * �õ���������������
     * @param name String ����
     * @param defaultValue int Ĭ��ֵ
     * @return int ����ֵ
     */
    public int getConfigInt(String name, int defaultValue)
    {
        if(getConfig() == null)
            return 0;
        return getConfig().getInt(getSystemGroup(),name,defaultValue);
    }
    /**
     * �õ�����������������
     * @param name String ����
     * @return long ����ֵ
     */
    public long getConfigLong(String name)
    {
        return getConfigLong(name,0);
    }
    /**
     * �õ�����������������
     * @param name String ����
     * @param defaultValue int Ĭ��ֵ
     * @return long ����ֵ
     */
    public long getConfigLong(String name,int defaultValue)
    {
        return TCM_Transform.getLong(getConfigString(name,"" + defaultValue));
    }
    /**
     * �õ�˫��������������
     * @param name String ����
     * @return double ����ֵ
     */
    public double getConfigDouble(String name)
    {
        return getConfigDouble(name,0);
    }
    /**
     * �õ�˫��������������
     * @param name String ����
     * @param defaultValue double Ĭ��ֵ
     * @return double ����ֵ
     */
    public double getConfigDouble(String name,double defaultValue)
    {
        return TCM_Transform.getDouble(getConfigString(name,"" + defaultValue));
    }
    /**
     * �õ��ַ�����������
     * @param name String ����
     * @return char ����ֵ
     */
    public char getConfigChar(String name)
    {
        return getConfigChar(name,' ');
    }
    /**
     * �õ��ַ�����������
     * @param name String ����
     * @param defaultValue char Ĭ��ֵ
     * @return char ����ֵ
     */
    public char getConfigChar(String name,char defaultValue)
    {
        return TCM_Transform.getChar(getConfigString(name,"" + defaultValue));
    }
    /**
     * �õ���������������
     * @param name String ����
     * @return boolean ����ֵ
     */
    public boolean getConfigBoolean(String name)
    {
        return getConfigBoolean(name,false);
    }
    /**
     * �õ���������������
     * @param name String ����
     * @param defaultValue boolean Ĭ��ֵ
     * @return boolean ����ֵ
     */
    public boolean getConfigBoolean(String name,boolean defaultValue)
    {
        return TCM_Transform.getBoolean(getConfigString(name,"" + defaultValue));
    }
    /**
     * �õ�Vector����������
     * @param name String ����
     * @return Vector ����ֵ
     */
    public Vector getConfigVector(String name)
    {
        return getConfigVector(name,new Vector());
    }
    /**
     * �õ�Vector����������
     * @param name String ����
     * @param defaultValue Vector Ĭ��ֵ
     * @return Vector ����ֵ
     */
    public Vector getConfigVector(String name,Vector defaultValue)
    {
        return TCM_Transform.getVector(getConfigString(name,"" + defaultValue));
    }
    /**
     * �õ���ֵ��������
     * @param name String ����
     * @return String[] ����ֵ
     */
    public String[] getConfigStringList(String name)
    {
        return getConfigStringList(name,"");
    }
    /**
     * �õ���ֵ��������
     * @param name String ����
     * @param defaultValue String Ĭ��ֵ
     * @return String[] ����ֵ
     */
    public String[] getConfigStringList(String name,String defaultValue)
    {
        if(getConfig() == null)
            return new String[]{};
        return getConfig().getStringList(getSystemGroup(),name,defaultValue);
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
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object, String methodName)
    {
        getBaseEventObject().add(eventName,object,methodName);
    }
    /**
     * ���Ӽ�������(�����Լ���)
     * @param eventName String �¼�����
     * @param methodName String �������
     */
    public void addEventListener(String eventName, String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * ɾ����������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String �������
     */
    public void removeEventListener(String eventName, Object object, String methodName)
    {
      getBaseEventObject().remove(eventName,object,methodName);
    }
    /**
     * ɾ����������(�����Լ���)
     * @param eventName String �¼�����
     * @param methodName String �������
     */
    public void removeEventListener(String eventName, String methodName)
    {
        removeEventListener(eventName,this,methodName);
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
        //System.out.println("=====Tcontrol callMessage(message,parm)======="+message);
        if(message == null ||message.length() == 0)
            return null;
        Object value = null;
        if((value = onBaseMessage(message,parm)) != null){
            //System.out.println("=====onBaseMessage(message,parm)=======");
            return value;
        }
        if((value = callEvent(message)) != null){
            //System.out.println("=====callEvent(message)=======");
            return value;
        }
        if((value = callEvent(message,parm)) != null){
            //System.out.println("=====callEvent(message,parm)=======");
            return value;
        }
        if((value = callChildControlsMessage(message,parm)) != null){
             //System.out.println("=====callChildControlsMessage(message,parm)=======");
            return value;
        }
        return null;
    }
    /**
     * ��Conrol����
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callChildControlsMessage(String message,Object parm)
    {
        Iterator iterator = childControls.keySet().iterator();
        Object value = null;
        while(iterator.hasNext())
            if((value = ((TChildControl) childControls.get(iterator.next())).callMessage(
                    message, parm)) != null)
                return value;
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
        if(message.startsWith("UI|"))
        {
            String s = message.substring(3);
            return getComponent().callMessage(s, parm);
        }
        //������
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message, "=");
        //System.out.println("TControl================="+value);

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
     * ������Control
     * @param value String
     */
    private void createItem(String value)
    {
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
        if(obj == null || !(obj instanceof TChildControl) )
          return;
        TChildControl childControl = (TChildControl)obj;
        childControl.setTag(cid);
        childControl.setControl(this);
        String configValue = getConfigParm().getConfig().getString(getConfigParm().getSystemGroup(),cid + ".ControlConfig");
        if(configValue.length() == 0)
            childControl.setConfigParm(getConfigParm());
        else
            childControl.setConfigParm(getConfigParm().newConfig(configValue));
        childControl.init(childControl.getConfigParm());
        childControls.put(cid,childControl);
    }
    /**
     * �õ�������ı�
     * @param tag String �����ǩ
     * @return String �ı�
     */
    public String getText(String tag)
    {
        return TCM_Transform.getString(callMessage("UI|" + tag + "|getText"));
    }
    /**
     * ����������ı�
     * @param tag String �����ǩ
     * @param text String �ı�
     */
    public void setText(String tag,String text)
    {
        callFunction("UI|" + tag + "|setText",text);
    }
    /**
     * ���������ֵ
     * @param tag String �����ǩ
     * @param value Object ֵ
     */
    public void setValue(String tag,Object value)
    {
        callFunction("UI|" + tag + "|setValue",value);
    }
    /**
     * �õ������ֵ
     * @param tag String
     * @return Object
     */
    public Object getValue(String tag)
    {
        return callFunction("UI|" + tag + "|getValue");
    }
    /**
     * �õ������ֵ
     * @param tag String
     * @return String
     */
    public String getValueString(String tag)
    {
        return TCM_Transform.getString(getValue(tag));
    }
    /**
     * �õ������ֵ
     * @param tag String
     * @return int
     */
    public int getValueInt(String tag)
    {
        return TCM_Transform.getInt(getValue(tag));
    }
    /**
     * �õ������ֵ
     * @param tag String
     * @return double
     */
    public double getValueDouble(String tag)
    {
        return TCM_Transform.getDouble(getValue(tag));
    }
    /**
     * �õ������ֵ
     * @param tag String
     * @return boolean
     */
    public boolean getValueBoolean(String tag)
    {
        return TCM_Transform.getBoolean(getValue(tag));
    }
    /**
     * �����ı���ȡParm
     * @param linkNames String "T_ID:ID;T_NAME:NAME"
     * @param parm TParm
     * @param row int
     */
    public void setTextForParm(String linkNames,TParm parm,int row)
    {
        if(parm == null)
            return;
        String names[] = StringTool.parseLine(linkNames,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            if(v.length > 1)
                name = v[1];
            setText(v[0],parm.getValue(name,row));
        }
    }
    /**
     * ����ֵ��ȡParm
     * @param linkNames String "T_ID:ID;T_NAME:NAME"
     * @param parm TParm
     */
    public void setValueForParm(String linkNames,TParm parm)
    {
        setValueForParm(linkNames,parm,-1);
    }
    /**
     * ����ֵ��ȡParm
     * @param linkNames String "T_ID:ID;T_NAME:NAME"
     * @param parm TParm
     * @param row int
     */
    public void setValueForParm(String linkNames,TParm parm,int row)
    {
        if(parm == null)
            return;
        String names[] = StringTool.parseLine(linkNames,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            if(v.length > 1)
                name = v[1];
            if(row < 0)
                setValue(v[0],parm.getData(name));
            else
                setValue(v[0],parm.getData(name,row));
        }
    }
    /**
     * ��ҳ��ץȡ����
     * @param tags String "ID:string:T_ID;NAME;SEQ:int"
     * @return TParm
     */
    public TParm getParmForTag(String tags)
    {
        return getParmForTag(tags,false);
    }
    /**
     * ��ҳ��ץȡ����
     * @param tags String
     * @param delNull boolean true ����Ϊ��ʱ������Tparm false ȫ������Tparm
     * @return TParm
     */
    public TParm getParmForTag(String tags,boolean delNull)
    {
        TParm parm = new TParm();
        String names[] = StringTool.parseLine(tags,";");
        if(names.length == 0)
            return parm;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            String type = "String";
            String tag = name;
            if(v.length > 1)
                type = v[1];
            if(v.length > 2)
                tag = v[2];

            if("string".equalsIgnoreCase(type))
            {
                String value = TCM_Transform.getString(getValue(tag));
                if(value.trim().length() > 0 || !delNull)
                    parm.setData(name, value);
            }
            else if("int".equalsIgnoreCase(type))
            {
                int value = TCM_Transform.getInt(getValue(tag));
                if(value != 0 || !delNull)
                    parm.setData(name, value);
            }
            else if("double".equalsIgnoreCase(type))
            {
                double value = TCM_Transform.getDouble(getValue(tag));
                if(value != 0.0 || !delNull)
                    parm.setData(name, value);
            }
            else if("Timestamp".equalsIgnoreCase(type))
            {
                Timestamp value = (Timestamp)getValue(tag);
                if(value != null)
                    parm.setData(name, value);
                else if(!delNull)
                    parm.setData(name,new TNull(Timestamp.class));
            }
        }
        return parm;
    }
    /**
     * ����ı�
     * @param tags String "ID;NAME"
     */
    public void clearText(String tags)
    {
        String names[] = StringTool.parseLine(tags,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
            setText(names[i],"");
    }
    /**
     * ���ֵ
     * @param tags String
     */
    public void clearValue(String tags)
    {
        String names[] = StringTool.parseLine(tags,";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
            setValue(names[i],"");
    }
    /**
     * �õ������ע����Ϣ
     * @param tag String
     * @return String
     */
    public String getTip(String tag)
    {
        String tip = TCM_Transform.getString(callMessage("UI|" + tag + "|getToolTipText"));
        if(tip == null && tip.trim().length() == 0)
            tip = "[" + tag + "]";
        return tip;
    }
    /**
     * ���ý���
     * @param tag String
     */
    public void grabFocus(String tag)
    {
        callMessage("UI|" + tag + "|grabFocus");
    }
    /**
     * ���¼������Ƿ�ȫ��¼����ȫ
     * @param s String ���tag�б��÷ֺż�� ���� "ID,Name"
     * @return boolean true ȫ����Ч false �����TextΪ��
     */
    public boolean emptyTextCheck(String s)
    {
        String tags[] = StringTool.parseLine(s,",");
        for(int i = 0;i < tags.length;i++)
        {
            String value = "" + getValue(tags[i]);
            if(value == null||value.trim().length() == 0)
            {
                messageBox("��¼��" + getTip(tags[i]) + "!");
                grabFocus(tags[i]);
                return false;
            }
        }
        return true;
    }
    /**
     * �õ��ı��Ĳ���
     * @param s String ���tag�б��÷ֺż�� ���� "ID,Name"
     * @return TParm (Ĭ��"RETURN"��)
     */
    public TParm getTextParm(String s)
    {
        return getTextParm(s,"RETURN");
    }
    /**
     * �õ��ı��Ĳ���
     * @param s String ���tag�б��÷ֺż�� ���� "ID,Name"
     * @param groupName String ���
     * @return TParm
     */
    public TParm getTextParm(String s,String groupName)
    {
        TParm parm = new TParm();
        String tags[] = StringTool.parseLine(s,",");
        for(int i = 0;i < tags.length;i++)
        {
            String value = getText(tags[i]);
            parm.setData(groupName,tags[i],value);
        }
        return parm;
    }
    /**
     * ���ô������
     * @param value Object
     */
    public void setParameter(Object value)
    {
        if(value instanceof String)
            callMessage("UI|setParameter|" + value);
        else
            callMessage("UI|setParameter",value);
    }
    /**
     * �õ��������
     * @return Object
     */
    public Object getParameter()
    {
        Object parm = callMessage("UI|getParameter");
        if("void".equals(parm))
            return null;
        return parm;
    }
    /**
     * �õ�����
     * @return TParm
     */
    public TParm getInputParm()
    {
        Object obj = getParameter();
        if(obj == null)
            return null;
        if(!(obj instanceof TParm))
            return null;
        return (TParm)obj;
    }
    /**
     * ���÷��ز���
     * @param value Object
     */
    public void setReturnValue(Object value)
    {
        if(value instanceof String)
            callMessage("UI|setReturnValue|" + value);
        else
            callMessage("UI|setReturnValue", new Object[]{(Object)value});
    }
    /**
     * �õ����ز���
     * @return Object
     */
    public Object getReturnValue()
    {
        return callMessage("UI|getReturnValue");
    }
    /**
     * �򿪴���
     * @param config String �����ļ���
     * @return TComponent
     */
    public TComponent openWindow(String config)
    {
        return openWindow(config,null);
    }
    /**
     * �򿪴���
     * @param config String
     * @param isTop boolean true �������� false ��ͨ����
     * @return TComponent
     */
    public TComponent openWindow(String config, boolean isTop)
    {
        return openWindow(config,null,isTop);
    }
    /**
     * �򿪴���
     * @param config String �����ļ���
     * @param parameter Object ����
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter)
    {
        return openWindow(config,parameter,false);
    }
    /**
     * �򿪴���
     * @param config String
     * @param parameter Object
     * @param isTop boolean true �������� false ��ͨ����
     * @return TComponent
     */
    public TComponent openWindow(String config, Object parameter,boolean isTop)
    {
        if(isTop)
        {
            return TWindow.openWindow(getConfigParm().newConfig(config),getComponent(),parameter);
        }
        return TFrame.openWindow(getConfigParm().newConfig(config),parameter);
    }
    /**
     * ����Ϣ����
     * @param config String �����ļ���
     * @return Object ����ֵ
     */
    public Object openDialog(String config)
    {
        return openDialog(config,null);
    }
    /**
     * ����Ϣ����
     * @param config String �����ļ���
     * @param parameter Object ����
     * @return Object ����ֵ
     */
    public Object openDialog(String config, Object parameter)
    {
        return openDialog(config,parameter,true);
    }
    /**
     * ����Ϣ����
     * @param config String �����ļ���
     * @param parameter Object ����
     * @param flg boolean true ��̬���� false ��̬����
     * @return Object
     */
    public Object openDialog(String config, Object parameter,boolean flg)
    {
        return TDialog.openWindow(getConfigParm().newConfig(config,flg),parameter);
    }
    /**
     * �򿪴�ӡ��������
     * @param fileName String �����ļ���
     * @param parameter Object ����
     * @return Object
     */
    public Object openPrintWindow(String fileName,Object parameter)
    {
        return openPrintWindow(fileName,parameter,false);
    }
    /**
     * �򿪴�ӡ��������
     * @param fileName String �����ļ���
     * @param parameter Object ����
     * @param isPrint boolean true ��ӡ����ʾ false ����
     * @return Object
     */
    public Object openPrintWindow(String fileName,Object parameter,boolean isPrint)
    {
        return openWindow("%ROOT%\\config\\database\\PreviewWord.x",new Object[]{fileName,parameter,isPrint});
    }
    /**
     * �򿪴�ӡ��������
     * @param fileName String �����ļ���
     * @param parameter Object ����
     * @return Object
     */
    public Object openPrintDialog(String fileName,Object parameter)
    {
        return openPrintDialog(fileName,parameter,false);
    }
    /**
     * �򿪴�ӡ������Ϣ����
     * @param fileName String �����ļ���
     * @param parameter Object ����
     * @param isPrint boolean true ��ӡ����ʾ false ����
     * @return Object
     */
    public Object openPrintDialog(String fileName,Object parameter,boolean isPrint)
    {
        return openDialog("%ROOT%\\config\\database\\PreviewWord.x",new Object[]{fileName,parameter,isPrint});
    }

    /**
     * �����Ի�����ʾ��Ϣ
     * @param message Object
     */
    public void messageBox_(Object message){
        JOptionPane.showMessageDialog((Component)getComponent(),TCM_Transform.getString(message));
    }

    /**
     * �����Ի�����ʾ��Ϣ
     * @param message String
     */
    public void messageBox(String message){
        message = TMessage.get(message);
        String title = "��Ϣ";
        if("en".equals(getLanguage()))
            title = "Message";
        JOptionPane.showMessageDialog((Component)getComponent(),message,title,JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * �����Ի�����ʾ��Ϣ
     * @param title String
     * @param message String
     */
    public void messageBox(String title,String message){

        message = TMessage.get(message);
        JOptionPane.showMessageDialog((Component)getComponent(),message,title,JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * ��ʾ��Ϣ����
     * @param title ����
     * @param message ��Ϣ
     * @param optionType ��ť����
     * @return int
     */
    public int messageBox(String title,String message,int optionType)
    {
        message = TMessage.get(message);
        return JOptionPane.showConfirmDialog((Component)getComponent(),message,title,optionType);
    }

    /**
     * �رմ���
     */
    public void closeWindow()
    {
        callMessage("UI|onClose");
    }
    /**
     * �Ƿ�رմ���
     * @return boolean true �ر� false ���ر�
     */
    public boolean onClosing()
    {
        return true;
    }
    /**
     * �õ�Ȩ��
     * @return Object
     */
    public Object getPopedem()
    {
        Object parm = callMessage("UI|getPopedem");
        if("void".equals(parm))
            return null;
        return parm;
    }
    /**
     * �õ�Ȩ�޵�Parm
     * @return TParm
     */
    public TParm getPopedemParm()
    {
        Object obj = getPopedem();
        if(obj == null)
            return null;
        if(!(obj instanceof TParm))
            return null;
        return (TParm)obj;
    }
    /**
     * �õ�Ȩ��״̬
     * @param id String ���
     * @return boolean true ��Ȩ�� false Ȩ��
     */
    public boolean getPopedem(String id)
    {
        if(id == null || id.length() == 0)
            return false;
        TParm parm = getPopedemParm();
        if(parm == null)
            return false;
        if(parm.getErrCode() < 0)
            return false;
        int count = parm.getCount();
        for(int i = 0;i < count;i ++)
        {
            String s = parm.getValue("ID",i);
            if(id.equals(s))
                return true;
        }
        return false;
    }
    /**
     * ����Ȩ��
     * @param id String
     * @param popedem boolean
     */
    public void setPopedem(String id,boolean popedem)
    {
        if(id == null || id.length() == 0)
            return;
        TParm parm = getPopedemParm();
        if(parm == null)
        {
            parm = new TParm();
            callFunction("UI|setPopedem",parm);
        }
        int count = parm.getCount();
        for(int i = 0;i < count;i ++)
        {
            String s = parm.getValue("ID",i);
            if(id.equals(s))
            {
                if(popedem)
                    parm.setData("ID",i,id);
                else
                    parm.removeRow(i);
                parm.setCount(parm.getCount("ID"));
                return;
            }
        }
        if(popedem)
        {
            parm.addData("ID", id);
            parm.setCount(parm.getCount("ID"));
        }
    }
    /**
     * ����Title
     * @param title String
     */
    public void setTitle(String title)
    {
        TComponent com = getComponent();
        if(com == null)
            return;
        if(com instanceof TFrame)
            ((TFrame)com).setTitle(title);
        if(com instanceof TDialog)
            ((TDialog)com).setTitle(title);
        if(com instanceof TPanel)
            ((TPanel)com).setTitle(title);
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
        //�ͷż���
        baseEvent.release();
        //�ͷ���������
        RunClass.release(this);
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        //System.out.println("==TControl Language=="+(String)TSystem.getObject("Language"));

        String language = (String)callFunction("UI|getLanguage");
        if(language == null || language.length() == 0)
            language = (String)TSystem.getObject("Language");
        return language;
    }
    /**
     * ��������
     * @param language String
     */
    public void onChangeLanguage(String language)
    {

    }
    /**
     * ��Sheet����
     * @param panelTag String
     * @param tag String
     * @param config String
     * @return TRootPanel
     */
    public TRootPanel openSheet(String panelTag,String tag,String config)
    {
        return openSheet(panelTag,tag,config,null);
    }
    /**
     * ��Sheet����
     * @param panelTag String
     * @param tag String
     * @param config String
     * @param parm Object
     * @return TRootPanel
     */
    public TRootPanel openSheet(String panelTag,String tag,String config,Object parm)
    {
        TComponent com = getComponent(panelTag);
        if(!(com instanceof TPanel))
            return null;
        TPanel panel = (TPanel)com;
        if(panel == null)
            return null;
        return panel.openSheet(tag,config,parm);
    }
    /**
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        System.out.println(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text,boolean debug)
    {
        System.out.println(text);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        System.out.println(text);
    }

    /**
     * ������ü�鷽��
     * @return
     */
    public boolean chkEmpty(){
    	//System.out.println("-----come in chkEmpty1111---------");
    	Map m=getConfig().getMap();
    	String strRequireds="";
    	//����������ֵ;
    	for (Object key:m.keySet()) {
    		   //System.out.println("key= "+ key + " and value= " + m.get(key));
    		//������
    		if(((String)key).indexOf("REQUIRED")!=-1){
    			if(((String)m.get(key)).equals("Y")){
    				strRequireds+=((String)key).split("\\.")[0]+",";
    			}
    		}

    	}
    	if(strRequireds!=null&&strRequireds.length()>0){
    		strRequireds.substring(0, strRequireds.length()-1);
    	}
    	//System.out.println("------------"+strRequireds);

    	return this.emptyTextCheck(strRequireds);

    	//return false;
    }


}
