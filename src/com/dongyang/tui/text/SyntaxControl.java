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
 * <p>Title: 语法器</p>
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
     * 语法控制器
     */
    private MSyntax syntaxManager;
    /**
     * 设置语法管理器
     * @param syntaxManager MSyntax
     */
    public void setSyntaxManager(MSyntax syntaxManager)
    {
        this.syntaxManager = syntaxManager;
    }
    /**
     * 得到语法管理器
     * @return MSyntax
     */
    public MSyntax getSyntaxManager()
    {
        return syntaxManager;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getSyntaxManager().getPM();
    }
    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPM().getPageManager();
    }
    /**
     * 得到显示层
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }
    /**
     * 得到语法
     * @param index int
     * @return ESyntaxItem
     */
    public ESyntaxItem getSyntaxItem(int index)
    {
        return getSyntaxManager().get(index);
    }
    /**
     * 得到宏名称
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
     * 今天
     * @return Date
     */
    public Date toDay()
    {
        return new Date();
    }
    /**
     * 当前日期时间
     * @return String
     */
    public String now()
    {
        return now("yyyy/MM/dd HH:mm:ss");
    }
    /**
     * 当前日期时间
     * @param format String
     * @return String
     */
    public String now(String format)
    {
        return StringTool.getString(toDay(),format);
    }
    /**
     * 运行方法
     * @param name String
     * @param parameters Object[]
     * @return Object
     */
    public Object runM(String name,Object ... parameters)
    {
        //处理方法
        String value[] = StringTool.parseLine(name, "|");
        return RunClass.invokeMethodT(this, value, parameters);
    }
    /**
     * 得到当前的方法名
     * @param index int 位置
     * @return String 方法名
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
     * 得到方法ID
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
     * 转换int型
     * @param obj Object
     * @return int
     */
    public int I(Object obj)
    {
        return TypeTool.getInt(obj);
    }
    /**
     * 转换成String型
     * @param obj Object
     * @return String
     */
    public String S(Object obj)
    {
        return TypeTool.getString(obj);
    }
    /**
     * 转化成String型
     * @param obj Object
     * @param format String 数字format格式 ###0.00
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
     * 转换成long型
     * @param obj Object
     * @return long
     */
    public long L(Object obj)
    {
        return TypeTool.getLong(obj);
    }
    /**
     * 转换成double型
     * @param obj Object
     * @return double
     */
    public double D(Object obj)
    {
        return TypeTool.getDouble(obj);
    }
    /**
     * 得到日期型 yyyy/MM/dd HH:mm:ss
     * @param obj Object
     * @return String
     */
    public String T(Object obj)
    {
        return T(obj,"yyyy/MM/dd HH:mm:ss");
    }
    /**
     * 得到日期
     * @param obj Object
     * @return String
     */
    public String Date(Object obj)
    {
        return T(obj,"yyyy/MM/dd");
    }
    /**
     * 得到时间
     * @param obj Object
     * @return String
     */
    public String Time(Object obj)
    {
        return T(obj,"HH:mm:ss");
    }
    /**
     * 得到日期型
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
     * 得到子串
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
     * 得到子串
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
     * 得到字符长度
     * @param obj Object
     * @return int
     */
    public int length(Object obj)
    {
        return S(obj).length();
    }
    /**
     * 合计
     * @param obj Object
     * @return String
     */
    public String sum(Object obj)
    {
        return sum(obj,"");
    }
    /**
     * 合计行数
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
     * 合计的名称
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
     * 合计
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
     * 得到总页数
     * @return int
     */
    public int getPageCount()
    {
        return getPageManager().size();
    }
    /**
     * 查询
     * @param sql String
     * @return TParm
     */
    public TParm select(String sql)
    {
        return new TParm(getDBTool().select(sql));
    }
    /**
     * 换中文
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
     * 得到数据库工具
     * @return TJDODBTool
     */
    public TJDODBTool getDBTool()
    {
        return TJDODBTool.getInstance();
    }
    /**
     * 得到系统事件
     * @return Timestamp
     */
    public Timestamp getDBTime()
    {
        return getDBTool().getDBTime();
    }
    /**
     * 设置参数
     * @param parameter Object
     */
    public void setParameter(Object parameter)
    {
        this.getPM().getFileManager().setParameter(parameter);
    }
    /**
     * 得到参数
     * @return Object
     */
    public Object getParameter()
    {
        return this.getPM().getFileManager().getParameter();
    }
    /**
     * 初始化
     */
    public void onInit()
    {

    }
    /**
     * 打开
     */
    public void onOpen()
    {

    }
    /**
     * 得到Table参数
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
     * 弹出对话框提示消息
     * @param message Object
     */
    public void messageBox(Object message){
        getUI().messageBox(message);
    }
    /**
     * 提示消息窗口
     * @param title String 标题
     * @param message Object 信息
     * @param optionType int 按钮类型
     * @return int
     */
    public int messageBox(String title,Object message,int optionType)
    {
        return getUI().messageBox(title,message,optionType);
    }
    /**
     * 得到参数
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
     * 得到字符参数
     * @param name String
     * @return String
     */
    public String getParmString(String name)
    {
        return getParmString(TParm.DEFAULT_GROUP,name);
    }
    /**
     * 得到字符参数
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
     * 得到字符参数
     * @param name String
     * @param row int
     * @return String
     */
    public String getParmString(String name,int row)
    {
        return getParmString(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * 得到字符参数
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
     * 得到int参数
     * @param name String
     * @return int
     */
    public int getParmInt(String name)
    {
        return getParmInt(TParm.DEFAULT_GROUP,name);
    }
    /**
     * 得到int参数
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
     * 得到int参数
     * @param name String
     * @param row int
     * @return int
     */
    public int getParmInt(String name,int row)
    {
        return getParmInt(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * 得到int参数
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
     * 得到double参数
     * @param name String
     * @return double
     */
    public double getParmDouble(String name)
    {
        return getParmDouble(TParm.DEFAULT_GROUP,name);
    }
    /**
     * 得到double参数
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
     * 得到double参数
     * @param name String
     * @param row int
     * @return double
     */
    public double getParmDouble(String name,int row)
    {
        return getParmDouble(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * 得到double参数
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
     * 得到boolean参数
     * @param name String
     * @return boolean
     */
    public boolean getParmBoolean(String name)
    {
        return getParmBoolean(TParm.DEFAULT_GROUP,name);
    }
    /**
     * 得到boolean参数
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
     * 得到boolean参数
     * @param name String
     * @param row int
     * @return boolean
     */
    public boolean getParmBoolean(String name,int row)
    {
        return getParmBoolean(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * 得到boolean参数
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
     * 得到Timestamp参数
     * @param name String
     * @return Timestamp
     */
    public Timestamp getParmTimestamp(String name)
    {
        return getParmTimestamp(TParm.DEFAULT_GROUP,name);
    }
    /**
     * 得到Timestamp参数
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
     * 得到Timestamp参数
     * @param name String
     * @param row int
     * @return Timestamp
     */
    public Timestamp getParmTimestamp(String name,int row)
    {
        return getParmTimestamp(TParm.DEFAULT_GROUP,name,row);
    }
    /**
     * 得到Timestamp参数
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
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        if(name == null || name.length() == 0)
            return null;
        return getPageManager().findObject(name,type);
    }
    /**
     * 查找固定文本
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
     * 设置固定文本文字
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
     * 设置图区显示
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
     * 图区是否显示
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
     * 更新
     */
    public void update()
    {
        getFocusManager().update();
    }
}
