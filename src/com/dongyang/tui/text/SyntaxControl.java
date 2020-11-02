package com.dongyang.tui.text;

import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;
import java.util.Date;
import com.dongyang.util.TypeTool;
import com.dongyang.tui.DText;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Vector;
import com.dongyang.data.TParm;
import com.dongyang.jdo.TJDODBTool;

/**
 *
 * <p>Title: �﷨��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SyntaxControl
{
    /**
     * �﷨������
     */
    private MSyntax syntaxManager;
    /**
     * �����﷨������
     * @param syntaxManager MSyntax
     */
    public void setSyntaxManager(MSyntax syntaxManager)
    {
        this.syntaxManager = syntaxManager;
    }
    /**
     * �õ��﷨������
     * @return MSyntax
     */
    public MSyntax getSyntaxManager()
    {
        return syntaxManager;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getSyntaxManager().getPM();
    }
    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPM().getPageManager();
    }
    /**
     * �õ���ʾ��
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }
    /**
     * �õ��﷨
     * @param index int
     * @return ESyntaxItem
     */
    public ESyntaxItem getSyntaxItem(int index)
    {
        return getSyntaxManager().get(index);
    }
    /**
     * �õ�������
     * @param index int
     * @return String
     */
    public String getMacroroutineName(int index)
    {
        ESyntaxItem item = getSyntaxItem(index);
        if(item == null)
            return "";
        return item.getName();
    }
    /**
     * ����
     * @return Date
     */
    public Date toDay()
    {
        return new Date();
    }
    /**
     * ��ǰ����ʱ��
     * @return String
     */
    public String now()
    {
        return now("yyyy/MM/dd HH:mm:ss");
    }
    /**
     * ��ǰ����ʱ��
     * @param format String
     * @return String
     */
    public String now(String format)
    {
        return StringTool.getString(toDay(),format);
    }
    /**
     * ���з���
     * @param name String
     * @param parameters Object[]
     * @return Object
     */
    public Object runM(String name,Object ... parameters)
    {
        //������
        String value[] = StringTool.parseLine(name, "|");
        return RunClass.invokeMethodT(this, value, parameters);
    }
    /**
     * �õ���ǰ�ķ�����
     * @param index int λ��
     * @return String ������
     */
    public String getThisMethodName(int index)
    {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        for(int i = 0;i < stack.length;i++)
            if("getThisMethodName".equals(stack[i].getMethodName()))
                return stack[i + index + 1].getMethodName();
        return "";
    }
    /**
     * �õ�����ID
     * @param methodName String
     * @return int
     */
    public int getThisMethodIndex(String methodName)
    {
        if(methodName == null || methodName.length() < 3)
            return -1;
        return TypeTool.getInt(methodName.substring(2));
    }
    /**
     * ת��int��
     * @param obj Object
     * @return int
     */
    public int I(Object obj)
    {
        return TypeTool.getInt(obj);
    }
    /**
     * ת����String��
     * @param obj Object
     * @return String
     */
    public String S(Object obj)
    {
        return TypeTool.getString(obj);
    }
    /**
     * ת����String��
     * @param obj Object
     * @param format String ����format��ʽ ###0.00
     * @return String
     */
    public String S(Object obj,String format)
    {
        if(obj == null)
            return "";
        if(obj instanceof Timestamp)
            return T(obj,format);
        if(obj instanceof Double || obj instanceof Integer || obj instanceof Long)
        {
            try
            {
                DecimalFormat formatObject = new DecimalFormat(format);
                double d = D(obj);
                return formatObject.format(d);
            } catch (Exception e)
            {
                return "format err:";
            }
        }
        return "" + obj;
    }
    /**
     * ת����long��
     * @param obj Object
     * @return long
     */
    public long L(Object obj)
    {
        return TypeTool.getLong(obj);
    }
    /**
     * ת����double��
     * @param obj Object
     * @return double
     */
    public double D(Object obj)
    {
        return TypeTool.getDouble(obj);
    }
    /**
     * �õ������� yyyy/MM/dd HH:mm:ss
     * @param obj Object
     * @return String
     */
    public String T(Object obj)
    {
        return T(obj,"yyyy/MM/dd HH:mm:ss");
    }
    /**
     * �õ�����
     * @param obj Object
     * @return String
     */
    public String Date(Object obj)
    {
        return T(obj,"yyyy/MM/dd");
    }
    /**
     * �õ�ʱ��
     * @param obj Object
     * @return String
     */
    public String Time(Object obj)
    {
        return T(obj,"HH:mm:ss");
    }
    /**
     * �õ�������
     * @param obj Object
     * @param format String
     * @return String
     */
    public String T(Object obj,String format)
    {
        Timestamp time = TypeTool.getTimestamp(obj);
        if(time == null)
            return "";
        return StringTool.getString(time,format);
    }
    /**
     * �õ��Ӵ�
     * @param obj Object
     * @param beginIndex int
     * @return String
     */
    public String substring(Object obj,int beginIndex)
    {
        String s = S(obj);
        return s.substring(beginIndex);
    }
    /**
     * �õ��Ӵ�
     * @param obj Object
     * @param beginIndex int
     * @param count int
     * @return String
     */
    public String substring(Object obj,int beginIndex,int count)
    {
        String s = S(obj);
        return s.substring(beginIndex,count);
    }
    /**
     * �õ��ַ�����
     * @param obj Object
     * @return int
     */
    public int length(Object obj)
    {
        return S(obj).length();
    }
    /**
     * �ϼ�
     * @param obj Object
     * @return String
     */
    public String sum(Object obj)
    {
        return sum(obj,"");
    }
    /**
     * �ϼ�����
     * @param obj Object
     * @return int
     */
    public int sumCount(Object obj)
    {
        if(obj == null || !(obj instanceof Vector))
            return 0;
        Vector v = (Vector)obj;
        return v.size();
    }
    /**
     * �ϼƵ�����
     * @param obj Object
     * @return Object
     */
    public Object sumName(Object obj)
    {
        if(obj == null || !(obj instanceof Vector))
            return 0;
        Vector v = (Vector)obj;
        if(v.size() == 0)
            return null;
        return v.get(0);
    }
    /**
     * �ϼ�
     * @param obj Object
     * @param format String
     * @return String
     */
    public String sum(Object obj,String format)
    {
        if(obj == null || !(obj instanceof Vector))
            return "";
        Vector v = (Vector)obj;
        double d = 0;
        for(int i = 0;i < v.size();i++)
        {
            d += D(v.get(i));
        }
        if(format != null && format.length() > 0)
            return S(d,format);
        return S(d);
    }
    /**
     * �õ���ҳ��
     * @return int
     */
    public int getPageCount()
    {
        return getPageManager().size();
    }
    /**
     * ��ѯ
     * @param sql String
     * @return TParm
     */
    public TParm select(String sql)
    {
        return new TParm(getDBTool().select(sql));
    }
    /**
     * ������
     * @param tableName String
     * @param desc String
     * @param where String
     * @return String
     */
    public String getDesc(String tableName,String desc,String where)
    {
        TParm parm = select("SELECT " + desc + " FROM " + tableName + " WHERE " + where);
        if(parm.getErrCode() < 0)
            return parm.getErrText();
        return parm.getValue(desc,0);
    }
    /**
     * �õ����ݿ⹤��
     * @return TJDODBTool
     */
    public TJDODBTool getDBTool()
    {
        return TJDODBTool.getInstance();
    }
    /**
     * �õ�ϵͳ�¼�
     * @return Timestamp
     */
    public Timestamp getDBTime()
    {
        return getDBTool().getDBTime();
    }
    /**
     * ���ò���
     * @param parameter Object
     */
    public void setParameter(Object parameter)
    {
        this.getPM().getFileManager().setParameter(parameter);
    }
    /**
     * �õ�����
     * @return Object
     */
    public Object getParameter()
    {
        return this.getPM().getFileManager().getParameter();
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {

    }
    /**
     * ��
     */
    public void onOpen()
    {

    }
    /**
     * �õ�Table����
     * @param tableID String
     * @param parmName String
     * @return String
     */
    public String getTableParm(String tableID,String parmName)
    {
        Object parm = getParameter();
        if(parm == null || !(parm instanceof TParm))
            return null;
        TParm p = (TParm)parm;
        return p.getValue(tableID,parmName);
    }
    /**
     * �����Ի�����ʾ��Ϣ
     * @param message Object
     */
    public void messageBox(Object message){
        getUI().messageBox(message);
    }
    /**
     * ��ʾ��Ϣ����
     * @param title String ����
     * @param message Object ��Ϣ
     * @param optionType int ��ť����
     * @return int
     */
    public int messageBox(String title,Object message,int optionType)
    {
        return getUI().messageBox(title,message,optionType);
    }
    /**
     * �õ�����
     * @return TParm
     */
    public TParm getParm()
    {
        Object p = getParameter();
        if(p == null || !(p instanceof TParm))
            return null;
        return (TParm)p;
    }
    /**
     * �õ��ַ�����
     * @param name String
     * @return String
     */
    public String getParmString(String name)
    {
        return getParmString(TParm.DEFAULT_GROUP,name);
    }
    /**
     * �õ��ַ�����
     * @param group String
     * @param name String
     * @return String
     */
    public String getParmString(String group,String name)
    {
        TParm parm = getParm();
        if(parm == null)
            return "";
        return parm.getValue(group,name);
    }
    /**
     * �õ��ַ�����
     * @param name String
     * @param row int
     * @return String
     */
    public String getParmString(String name,int row)
    {
        return getParmString(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * �õ��ַ�����
     * @param group String
     * @param name String
     * @param row int
     * @return String
     */
    public String getParmString(String group,String name,int row)
    {
        TParm parm = getParm();
        if(parm == null)
            return "";
        return parm.getValue(group,name,row);
    }
    /**
     * �õ�int����
     * @param name String
     * @return int
     */
    public int getParmInt(String name)
    {
        return getParmInt(TParm.DEFAULT_GROUP,name);
    }
    /**
     * �õ�int����
     * @param group String
     * @param name String
     * @return int
     */
    public int getParmInt(String group,String name)
    {
        TParm parm = getParm();
        if(parm == null)
            return 0;
        return parm.getInt(group,name);
    }
    /**
     * �õ�int����
     * @param name String
     * @param row int
     * @return int
     */
    public int getParmInt(String name,int row)
    {
        return getParmInt(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * �õ�int����
     * @param group String
     * @param name String
     * @param row int
     * @return int
     */
    public int getParmInt(String group,String name,int row)
    {
        TParm parm = getParm();
        if(parm == null)
            return 0;
        return parm.getInt(group,name,row);
    }
    /**
     * �õ�double����
     * @param name String
     * @return double
     */
    public double getParmDouble(String name)
    {
        return getParmDouble(TParm.DEFAULT_GROUP,name);
    }
    /**
     * �õ�double����
     * @param group String
     * @param name String
     * @return double
     */
    public double getParmDouble(String group,String name)
    {
        TParm parm = getParm();
        if(parm == null)
            return 0;
        return parm.getDouble(group,name);
    }
    /**
     * �õ�double����
     * @param name String
     * @param row int
     * @return double
     */
    public double getParmDouble(String name,int row)
    {
        return getParmDouble(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * �õ�double����
     * @param group String
     * @param name String
     * @param row int
     * @return double
     */
    public double getParmDouble(String group,String name,int row)
    {
        TParm parm = getParm();
        if(parm == null)
            return 0;
        return parm.getDouble(group,name,row);
    }
    /**
     * �õ�boolean����
     * @param name String
     * @return boolean
     */
    public boolean getParmBoolean(String name)
    {
        return getParmBoolean(TParm.DEFAULT_GROUP,name);
    }
    /**
     * �õ�boolean����
     * @param group String
     * @param name String
     * @return boolean
     */
    public boolean getParmBoolean(String group,String name)
    {
        TParm parm = getParm();
        if(parm == null)
            return false;
        return parm.getBoolean(group,name);
    }
    /**
     * �õ�boolean����
     * @param name String
     * @param row int
     * @return boolean
     */
    public boolean getParmBoolean(String name,int row)
    {
        return getParmBoolean(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * �õ�boolean����
     * @param group String
     * @param name String
     * @param row int
     * @return boolean
     */
    public boolean getParmBoolean(String group,String name,int row)
    {
        TParm parm = getParm();
        if(parm == null)
            return false;
        return parm.getBoolean(group,name,row);
    }
    /**
     * �õ�Timestamp����
     * @param name String
     * @return Timestamp
     */
    public Timestamp getParmTimestamp(String name)
    {
        return getParmTimestamp(TParm.DEFAULT_GROUP,name);
    }
    /**
     * �õ�Timestamp����
     * @param group String
     * @param name String
     * @return Timestamp
     */
    public Timestamp getParmTimestamp(String group,String name)
    {
        TParm parm = getParm();
        if(parm == null)
            return null;
        return parm.getTimestamp(group,name);
    }
    /**
     * �õ�Timestamp����
     * @param name String
     * @param row int
     * @return Timestamp
     */
    public Timestamp getParmTimestamp(String name,int row)
    {
        return getParmTimestamp(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * �õ�Timestamp����
     * @param group String
     * @param name String
     * @param row int
     * @return Timestamp
     */
    public Timestamp getParmTimestamp(String group,String name,int row)
    {
        TParm parm = getParm();
        if(parm == null)
            return null;
        return parm.getTimestamp(group,name,row);
    }
    /**
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        if(name == null || name.length() == 0)
            return null;
        return getPageManager().findObject(name,type);
    }
    /**
     * ���ҹ̶��ı�
     * @param name String
     * @return EFixed
     */
    public EFixed getFixed(String name)
    {
        if(name == null || name.length() == 0)
            return null;
        return (EFixed)findObject(name,EComponent.FIXED_TYPE);
    }
    /**
     * ���ù̶��ı�����
     * @param name String
     * @param text String
     */
    public void setFixedText(String name,String text)
    {
        if(name == null || name.length() == 0)
            return;
        EFixed fixed = getFixed(name);
        if(fixed == null)
            return;
        fixed.setText(text);
        update();
    }
    /**
     * ����ͼ����ʾ
     * @param name String
     * @param visible boolean
     * @return boolean
     */
    public boolean setVisiblePic(String name,boolean visible)
    {
        EPic pic = (EPic)findObject(name,EComponent.PIC_TYPE);
        if(pic == null)
            return false;
        pic.setVisible(visible);
        update();
        return true;
    }
    /**
     * ͼ���Ƿ���ʾ
     * @param name String
     * @return boolean
     */
    public boolean isVisiblePic(String name)
    {
        EPic pic = (EPic)findObject(name,EComponent.PIC_TYPE);
        if(pic == null)
            return false;
        return pic.isVisible();
    }
    /**
     * ����
     */
    public void update()
    {
        getFocusManager().update();
    }
}
