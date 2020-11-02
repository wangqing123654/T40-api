package com.dongyang.control;

import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.TComponent;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.Log;
import com.dongyang.util.StringTool;
import com.dongyang.util.RunClass;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.data.TParm;
import javax.swing.JOptionPane;
import java.awt.Component;
import com.dongyang.ui.TFrame;
import com.dongyang.ui.TDialog;
import java.util.Vector;
import com.dongyang.config.TConfig;

public class TChildControl implements TComponent {
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
     * ������
     */
    private TControl control;
    /**
     * ���ö���
     */
    private TConfigParm configParm;
    /**
     * ��ǩ
     */
    private String tag;
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ������
     */
    public TChildControl()
    {
        log = new Log();
    }
    /**
     * ������
     */
    public void onInit() {
    }
    /**
     * ��ʼ��
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm) {
        out("begin");
        if (parm == null)
            return;
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),
                getTag());
        for (int index = 0; index < value.length; index++)
            callMessage(value[index], parm);
        out("end");
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
        log.setUserInf("[" + tag + "]");
    }
    /**
     * ������Control
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
    }
    /**
     * �õ���Control
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * �õ�UI���
     * @return TComponent
     */
    public TComponent getComponent() {
        return getControl().getComponent();
    }

    /**
     * ���ÿ��ƶ���
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }
    /**
     * �õ����ƶ���
     * @return TConfigParm
     */
    public TConfigParm getConfigParm() {
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
    public BaseEvent getBaseEventObject() {
        return baseEvent;
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
     * ���Ӽ�������(�����Լ���)
     * @param eventName String �¼�����
     * @param methodName String �������
     */
    public void addEventListener(String eventName, String methodName) {
        addEventListener(eventName, this, methodName);
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
     * ɾ����������(�����Լ���)
     * @param eventName String �¼�����
     * @param methodName String �������
     */
    public void removeEventListener(String eventName, String methodName) {
        removeEventListener(eventName, this, methodName);
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
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        callEvent(message);
        callEvent(message, parm);
        return null;
    }
    /**
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        out("begin message=\"" + message + "\"");
        if (message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if("UI".equalsIgnoreCase(value[0]))
            return getComponent().callMessage(value[1], parm);
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
    }
    /**
     * �õ�������ı�
     * @param tag String �����ǩ
     * @return String
     */
    public String getText(String tag)
    {
        return TCM_Transform.getString(callMessage("UI|" + tag + "|getText"));
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
            String value = getText(tags[i]);
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
        return callMessage("UI|getParameter");
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
            callMessage("UI|setReturnValue", value);
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
     */
    public void openWindow(String config)
    {
        openWindow(config,null);
    }
    /**
     * �򿪴���
     * @param config String �����ļ���
     * @param parameter Object ����
     */
    public void openWindow(String config, Object parameter)
    {
        TFrame.openWindow(getConfigParm().newConfig(config),parameter);
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
        return TDialog.openWindow(getConfigParm().newConfig(config),parameter);
    }
    /**
     * �����Ի�����ʾ��Ϣ
     * @param message String
     */
    public void messageBox(String message){
        JOptionPane.showMessageDialog((Component)getComponent(),message);
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
        return JOptionPane.showConfirmDialog((Component)getComponent(),title,message,optionType);
    }
    /**
     * �رմ���
     */
    public void closeWindow()
    {
        callMessage("UI|onClose");
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
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        log.err(text);
    }
}
