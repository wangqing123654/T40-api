package com.dongyang.data;

import com.dongyang.config.TConfigParm;
import com.dongyang.data.base.TBaseData;
import com.dongyang.data.exception.TException;
import com.dongyang.ui.TComponent;
import com.dongyang.util.Log;
import com.dongyang.control.TControl;
import com.dongyang.util.StringTool;
import com.dongyang.ui.event.BaseEvent;
import com.dongyang.ui.event.TAttributeListener;
import com.dongyang.ui.event.TAttributeEvent;
/**
 * ������������
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author lzk 2008.06.26 0:02
 * @version JavaHis 1.0
 */
public class TData extends TBaseData
        implements TComponent{
    /**
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * ��ǩ
     */
    private String tag;
    /**
     * ���ر�ǩ
     */
    private String loadtag;
    /**
     * ������
     */
    private TControl control;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ���Լ���
     */
    private TAttributeListener attributeListener;
    /**
     * ��־ϵͳ
     */
    Log log;
    /**
     * ������
     * @throws TException
     */
    public TData() throws TException
    {
        log = new Log();
        setTag("Data");
    }
    /**
     * �õ���ǩ
     * @param tag String
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    /**
     * ���ñ�ǩ
     * @return String
     */
    public String getTag()
    {
        return tag;
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
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control)
    {
        this.control = control;
        if(control != null)
        {
            control.callMessage("setComponent",this);
        }
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
    public void addAttributeListener(TAttributeListener attributeListener)
    {
        this.attributeListener = attributeListener;
    }
    /**
     * �����¼�
     * @param event String ʱ������
     * @param row int �к�
     * @param name String ����
     * @param value Object ֵ
     * @param oldValue Object ��ֵ
     */
    public void eventAttributeListener(String event,int row,String name,Object value,Object oldValue)
    {
        if(attributeListener == null)
            return;
        if("SET".equalsIgnoreCase(event))
            attributeListener.attributeSet(new TAttributeEvent(row,name,value,oldValue));
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {
        out("begin");
        //����Action�¼�
        addAttributeListener(new TAttributeListener(this));
        //��ʼ��������
        if(getControl() != null)
            getControl().onInit();
        out("end");
    }
    /**
     * initialize
     * @param parm TConfigParm
     */
    public void init(TConfigParm parm)
    {
        if(parm == null)
            return;
        if(parm.getSystemGroup() == null)
            parm.setSystemGroup("");
        String value[] = parm.getConfig().getTagList(parm.getSystemGroup(),getTag());
        for(int index = 0;index < value.length;index++)
            if(filterInit(value[index]))
                callMessage(value[index], parm);
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
        out("begin message=\"" + message + "\"");
        if (message == null || message.length() == 0)
            return null;
        //���������Ϣ
        Object value = onBaseMessage(message, parm);
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
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message,Object parm)
    {
        out( "begin message=\"" + message + "\"");
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        if("setTag".equalsIgnoreCase(value[0]))
        {
            setTag(value[1]);
            return "OK";
        }
        if("getTag".equalsIgnoreCase(value[0]))
            return getTag();
        if("setDoubleBuffered".equalsIgnoreCase(value[0]))
        {
            setDoubleBuffered(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if("isDoubleBuffered".equalsIgnoreCase(value[0]))
            return isDoubleBuffered();
        if("setControl".equalsIgnoreCase(value[1]))
        {
            if(parm instanceof TControl)
                setControl((TControl)parm);
            return "OK";
        }
        if("getControl".equalsIgnoreCase(value[1]))
            return getControl();
        if("addEventListener".equalsIgnoreCase(value[0]))
        {
            String value1[] = StringTool.parseLine(value[1],"|");
            if(parm == null || value1.length < 2)
                return "addEventListener ��������";
            addEventListener(value1[0],parm,value1[1]);
            return "OK";
        }
        if("onInit".equalsIgnoreCase(value[0]))
        {
            onInit();
            return "OK";
        }
        value = StringTool.getHead(message,"=");
        if("Control".equalsIgnoreCase(value[0]))
        {
            if(parm instanceof TControl)
                setControl((TControl) parm);
            return "OK";
        }
        if("ControlClassName".equalsIgnoreCase(value[0]))
        {
            if(parm instanceof TConfigParm)
            {
                TConfigParm uiParm = (TConfigParm) parm;
                Object obj = ((TConfigParm)parm).loadObject(value[1]);
                if(obj == null)
                {
                    err("Class loadObject err className=" + value[1]);
                    return "ERR";
                }
                if(obj instanceof TControl)
                {
                    TControl control = (TControl)obj;
                    setControl(control);
                    String configValue = uiParm.getConfig().getString(uiParm.getSystemGroup(),getLoadTag() + ".ControlConfig");
                    if(configValue.length() == 0)
                        control.setConfigParm(uiParm);
                    else
                        callMessage("ControlConfig=" + configValue,parm);
                    control.init(control.getConfigParm());
                    return "OK";
                }
            }
        }
        if("ControlConfig".equalsIgnoreCase(value[0]))
        {
            if(getControl() != null && parm instanceof TConfigParm)
            {
                TConfigParm uiParm = (TConfigParm) parm;
                getControl().setConfigParm(uiParm.newConfig(value[1]));
            }
        }
        if("doubleBuffered".equalsIgnoreCase(value[0]))
        {
            setDoubleBuffered(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if("item".equalsIgnoreCase(value[0]))
        {
            if(parm instanceof TConfigParm)
            {
                String s[] = StringTool.parseLine(value[1],';',"[]{}()");
                for(int i = 0;i < s.length;i++)
                    createItem(s[i],(TConfigParm)parm);
                return "OK";
            }
        }
        return null;
    }
    /**
     * ���������
     * @param value String
     * @param parm TConfigParm
     */
    private void createItem(String value,TConfigParm parm)
    {
        out("begin value=\"" + value + "\"");
        String values[] = StringTool.parseLine(value,",");
        if(values.length < 4)
            return;
        //������
        String name = values[0];
        if(name.length() == 0)
            return;
        //����
        String type = values[1];
        if(type.length() == 0)
            return;
        //�Ƿ��Ƕ�ֵ
        boolean multi = StringTool.getBoolean(values[2]);
        //�ߴ�
        int size = StringTool.getInt(values[3]);
        try{
            addAttribute(name, type);
            setAttributeSize(name, size);
            setAttributeMulti(name, multi);
        }catch(TException e)
        {
            err(e.getMessage());
        }
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

    class Data extends TData
    {
        public Data()throws TException
        {
            TConfigParm parm = new TConfigParm();
            parm.setSystemGroup("TEDA");
            parm.setConfig("Data.x");
            parm.setConfigColor("D:\\Project\\client\\Color.ini");
            parm.setConfigClass("D:\\Project\\client\\class.ini");
            init(parm);
            onInit();
        }
        public void setAAA(String s)throws TException
        {
            setAttributeValue("AAA",s);
        }
        public String getAAA()throws TException
        {
            return (String)getAttributeValue("AAA");
        }
    }
    /**
     * �ͷ�
     */
    public void release()
    {

    }
    public static void main(String args[])
    {
        TConfigParm parm = new TConfigParm();
        parm.setSystemGroup("TEDA");
        parm.setConfig("Data.x");
        parm.setConfigColor("D:\\Project\\client\\Color.ini");
        parm.setConfigClass("D:\\Project\\client\\class.ini");
        try{
            TData data = new TData();
            data.init(parm);
            data.onInit();
            data.setAttributeValue("AAA","12");
            data.setAttributeValue("AAA","20");
            data.setAttributeValue("AAA","12");
            System.out.println(data.getAttributeValue("AAA"));
            System.out.println(data.getAttributeModified("AAA"));

            data.addAttributeValue("BBB",1);
            data.addAttributeValue("BBB",200);
            System.out.println(data.getAttributeCount("BBB"));
            System.out.println(data.getAttributeValue(1,"BBB"));

            System.out.println("map=" + data.getDataMap());
            /*data.setAttributeValue("AAA","teterter");
            data.addAttributeValue("BBB",1);
            data.addAttributeValue("BBB",2);
            data.setAttributeValue(4,"BBB",4);
            data.setAttributeValue(4,"BBB",5);
            data.setAttributeValue(4,"BBB",4);
            data.setAttributeValue(1,"BBB",200);
            System.out.println("map=" + data.getDataMap());
            data.addAttributeValue(100,"BBB");
            System.out.println("map=" + data.getDataMap());*/

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
