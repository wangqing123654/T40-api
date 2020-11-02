package com.dongyang.tui.text;

import com.dongyang.tui.DText;
import com.dongyang.util.TList;
import com.dongyang.tui.io.JavaCTool;
import com.dongyang.data.TParm;
import com.dongyang.util.DynamicClassLoader;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.Parameter;
import com.dongyang.util.StringTool;
import java.lang.reflect.Constructor;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.util.RunClass;
import com.dongyang.util.TypeTool;

/**
 *
 * <p>Title: 语法控制器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk
 * @version 1.0
 */
public class MSyntax
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * import列表
     */
    private TList imports;
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 包名
     */
    private String packageName = "jhwc";
    /**
     * 类名
     */
    private String className = "";
    /**
     * JavaC工具
     */
    private JavaCTool tool;
    /**
     * 编译错误信息
     */
    private String makeErrText = "";
    /**
     * 自定义import脚本
     */
    private String customImportScript = "";
    /**
     * 自定义属性方法脚本
     */
    private String customFunction = "";
    /**
     * 类加载器
     */
    private DynamicClassLoader classLoader;
    /**
     * 控制类
     */
    private SyntaxControl control;
    /**
     * 控制类类数据
     */
    private byte[] controlClassData;
    /**
     * 构造器
     */
    public MSyntax()
    {
        components = new TList();
        imports = new TList();
        tool = new JavaCTool();
        //初始化import
        initImports();
    }
    /**
     * 初始化import
     */
    public void initImports()
    {
        addImport("com.dongyang.data.*");
        addImport("com.dongyang.jdo.*");
        addImport("com.dongyang.manager.*");
        addImport("com.dongyang.tui.text.*");
        addImport("com.dongyang.tui.text.ui.*");
        addImport("java.awt.*");
        addImport("java.util.*");
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 得到显示层
     * @return MView
     */
    public MView getViewManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getViewManager();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFocusManager();
    }
    /**
     * 设置焦点位置
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        if(getFocusManager() == null)
            return;
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * 得到焦点位置
     * @return int
     */
    public int getFocusIndex()
    {
        if(getFocusManager() == null)
            return 0;
        return getFocusManager().getFocusIndex();
    }
    /**
     * 得到焦点
     * @return EComponent
     */
    public EComponent getFocus()
    {
        if(getFocusManager() == null)
            return null;
        return getFocusManager().getFocus();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
            return null;
        return getPM().getUI();
    }
    /**
     * 得到文件管理器
     * @return MFile
     */
    public MFile getFileManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFileManager();
    }
    /**
     * 增加成员
     * @param item ESyntaxItem
     */
    public void add(ESyntaxItem item)
    {
        if (item == null)
            return;
        components.add(item);
        item.setManager(this);
    }
    /**
     * 删除成员
     * @param item ESyntaxItem
     */
    public void remove(ESyntaxItem item)
    {
        components.remove(item);
    }
    /**
     * 删除成员
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
    }
    /**
     * 全部清除
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * 清除
     */
    public void clear()
    {
        setPackageName("jhwc");
        setClassName("");
        setMakeErrText("");
        setControl(null);
        setControlClassData(null);
        setClassLoader(null);
        setMakeErrText("");
        setCustomImportScript("");
        setCustomFunction("");
        removeAll();
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * 得到成员
     * @param index int
     * @return ESyntaxItem
     */
    public ESyntaxItem get(int index)
    {
        return (ESyntaxItem) components.get(index);
    }
    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * 增加import
     * @param syntax String
     */
    public void addImport(String syntax)
    {
        imports.add(syntax);
    }
    /**
     * 得到import列表
     * @return TList
     */
    public TList getImportList()
    {
        return imports;
    }
    /**
     * 查找位置
     * @param item ESyntaxItem
     * @return int
     */
    public int indexOf(ESyntaxItem item)
    {
        if (item == null)
            return -1;
        return components.indexOf(item);
    }
    /**
     * 设置包名
     * @param packageName String
     */
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }
    /**
     * 得到包名
     * @return String
     */
    public String getPackageName()
    {
        return packageName;
    }
    /**
     * 设置类名
     * @param className String
     */
    public void setClassName(String className)
    {
        this.className = className;
    }
    /**
     * 得到类名
     * @return String
     */
    public String getClassName()
    {
        return className;
    }
    /**
     * 设置控制类类数据
     * @param controlClassData byte[]
     */
    public void setControlClassData(byte[] controlClassData)
    {
        this.controlClassData = controlClassData;
    }
    /**
     * 得到控制类类数据
     * @return byte[]
     */
    public byte[] getControlClassData()
    {
        return controlClassData;
    }
    /**
     * 设置自定义import脚本
     * @param customImportScript String
     */
    public void setCustomImportScript(String customImportScript)
    {
        this.customImportScript = customImportScript;
    }
    /**
     * 得到自定义import脚本
     * @return String
     */
    public String getCustomImportScript()
    {
        return customImportScript;
    }
    /**
     * 设置自定义属性方法脚本
     * @param customFunction String
     */
    public void setCustomFunction(String customFunction)
    {
        this.customFunction = customFunction;
    }
    /**
     * 得到自定义属性方法脚本
     * @return String
     */
    public String getCustomFunction()
    {
        return customFunction;
    }
    /**
     * 得到包语法
     * @return String
     */
    public String packageScript()
    {
        if(getPackageName() == null || getPackageName().length() == 0)
            return "";
        return "package " + getPackageName() + ";\n";
    }
    /**
     * import 语法
     * @return String
     */
    public String importScript()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < imports.size();i++)
        {
            sb.append("import ");
            sb.append(imports.get(i));
            sb.append(";\n");
        }
        if(getCustomImportScript() != null && getCustomImportScript().length() > 0)
            sb.append(getCustomImportScript());
        return sb.toString();
    }
    /**
     * 类开始语法
     * @return String
     */
    public String classBeginScript()
    {
        return "public class " + getClassName() + " extends SyntaxControl{\n";
    }
    /**
     * 类结束语法
     * @return String
     */
    public String classEndScript()
    {
        return "}\n";
    }
    /**
     * 语法
     * @return String
     */
    public String syntaxScript()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i++)
        {
            ESyntaxItem item = get(i);
            if(item == null)
                continue;
            sb.append(item.getScript());
        }
        return sb.toString();
    }
    /**
     * 创建脚本
     * @return String
     */
    public String createScript()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(packageScript());
        sb.append(importScript());
        sb.append(classBeginScript());
        if(getCustomFunction() != null && getCustomFunction().length() > 0)
            sb.append(getCustomFunction());
        sb.append(syntaxScript());
        sb.append(classEndScript());
        return sb.toString();
    }
    /**
     * 得到Src名称
     * @return String
     */
    public String getSrcName()
    {
        StringBuffer sb = new StringBuffer();
        if(getPackageName() != null && getPackageName().length() > 0)
        {
            sb.append(getPackageName());
            sb.append(".");
        }
        sb.append(getClassName());
        return sb.toString();
    }
    /**
     * 得到JavaC工具
     * @return JavaCTool
     */
    public JavaCTool getTool()
    {
        return tool;
    }
    /**
     * 设置错误信息
     * @param makeErrText String
     */
    public void setMakeErrText(String makeErrText)
    {
        this.makeErrText = makeErrText;
    }
    /**
     * 得到错误信息
     * @return String
     */
    public String getMakeErrText()
    {
        return makeErrText;
    }
    /**
     * 编译
     * @return boolean
     */
    public boolean make()
    {
        if(getClassName() == null || getClassName().length() == 0)
            setClassName(getTool().createClassName(getPackageName()));
        String srcName = getSrcName();
        String script = createScript();
        if(!getTool().saveSrc(srcName,script))
        {
            System.out.println("MSyntax.make()saveSrc err");
            setMakeErrText("脚本文件报错IO错误!");
            return false;
        }
        TParm parm = new TParm(getTool().javac(srcName));
        if(parm.getErrCode() < 0)
        {
            System.out.println("MSyntax.make()javac err");
            System.out.println(parm.getErrText());
            setMakeErrText("编译错误:" + parm.getErrText());
            return false;
        }
        setMakeErrText(parm.getValue("err"));
        //重新加载控制类
        resetControl();
        return true;
    }
    /**
     * 设置类加载器
     * @param classLoader DynamicClassLoader
     */
    public void setClassLoader(DynamicClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }
    /**
     * 得到类加载器
     * @return DynamicClassLoader
     */
    public DynamicClassLoader getClassLoader()
    {
        if(classLoader == null)
            resetClassLoader();
        return classLoader;
    }
    /**
     * 重新设置类加载
     */
    public void resetClassLoader()
    {
        classLoader = new DynamicClassLoader();
        classLoader.setSocket(getSocket());
        classLoader.setRealPath("");
        classLoader.setConfigParm(getUI().getConfigParm());
    }
    /**
     * 得到主连接
     * @return TSocket
     */
    public TSocket getSocket()
    {
        return TIOM_AppServer.SOCKET;
    }
    /**
     * 动态加载对象
     * @param className String
     * @return Object
     */
    public Object loadObject(String className)
    {
        try
        {
            byte[] data = getTool().loadClass(className);
            if(data == null)
                return null;
            Class classObj = getClassLoader().loadClassDynamic(className, data);
            if(classObj == null)
                return null;
            setControlClassData(data);
            return classObj.newInstance();
        } catch (Exception e)
        {
            return null;
        }
    }
    /**
     * 初始化控制类
     */
    public void initControl()
    {
        if(getControlClassData() == null)
            return;
        try{
            Class classObj = getClassLoader().loadClassDynamic(getSrcName(), getControlClassData());
            if(classObj == null)
                return;
            Object obj = classObj.newInstance();
            if (obj == null || !(obj instanceof SyntaxControl))
                return;
            SyntaxControl control = (SyntaxControl) obj;
            setControl(control);
        }catch(Exception e)
        {
        }
    }
    /**
     * 设置控制类
     * @param control SyntaxControl
     */
    public void setControl(SyntaxControl control)
    {
        this.control = control;
        if(control == null)
            return;
        control.setSyntaxManager(this);
        control.onInit();
    }
    /**
     * 打开
     */
    public void onOpen()
    {
        SyntaxControl control = getControl();
        if(control == null)
            return;
        control.onOpen();
    }
    /**
     * 得到控制类
     * @return SyntaxControl
     */
    public SyntaxControl getControl()
    {
        return this.control;
    }
    /**
     * 执行方法
     * @param methodName String
     * @param parameters Object[]
     * @return Object
     */
    public Object invokeMenthod(String methodName,Object[] parameters)
    {
        if(getControl() == null)
            return null;
        return RunClass.invokeMethod(getControl(),methodName,parameters);
    }
    /**
     * 执行方法
     * @param message String
     * @return Object
     */
    public Object invokeMenthodString(String message)
    {
        if(getControl() == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        return RunClass.invokeMethodT(getControl(), value, null);
    }
    /**
     * 重新加载控制类
     */
    public void resetControl()
    {
        //重新设置类加载
        resetClassLoader();
        Object obj = loadObject(getSrcName());
        if(obj == null || !(obj instanceof SyntaxControl))
            return;
        SyntaxControl control = (SyntaxControl)obj;
        setControl(control);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s,int c)
      throws IOException
    {
        s.WO("<MSyntax>",c);
        s.WO(1,"PackageName",getPackageName(),"",c + 1);
        s.WO(2,"ClassName",getClassName(),"",c + 1);
        s.WO(3,"MakeErrText",getMakeErrText(),"",c + 1);
        s.WO(4,"CustomImportScript",getCustomImportScript(),"",c + 1);
        s.WO(5,"CustomFunction",getCustomFunction(),"",c + 1);
        if(getControlClassData() != null)
            s.WO(6,"ControlClassData","[byte " + getControlClassData().length + "]","",c + 1);
        for(int i = 0;i < size();i ++)
        {
            ESyntaxItem item = get(i);
            if(item == null)
                continue;
            item.writeObjectDebug(s,c + 1);
        }
        s.WO("</MSyntax>",c);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeString(1,getPackageName(),"");
        s.writeString(2,getClassName(),"");
        s.writeString(3,getMakeErrText(),"");
        s.writeString(4,getCustomImportScript(),"");
        s.writeString(5,getCustomFunction(),"");
        s.writeShort(6);
        s.writeBytes(getControlClassData());
        s.writeShort( -1);

        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            ESyntaxItem item = get(i);
            if(item == null)
                continue;
            item.writeObject(s);
        }
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            switch (id)
            {
            case 1:
                setPackageName(s.readString());
                break;
            case 2:
                setClassName(s.readString());
                break;
            case 3:
                setMakeErrText(s.readString());
                break;
            case 4:
                setCustomImportScript(s.readString());
                break;
            case 5:
                setCustomFunction(s.readString());
                break;
            case 6:
                setControlClassData(s.readBytes());
                break;
            }
            id = s.readShort();
        }
        //初始化控制类
        initControl();
        //读取页
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            ESyntaxItem item = new ESyntaxItem(this);
            add(item);
            item.readObject(s);
        }
    }
    /**
     * 自定义语法对话框
     */
    public void onCustomScriptDialog()
    {
        Object value[] = (Object[])getUI().openDialog("%ROOT%\\config\\database\\CustomScriptDialog.x",
                                              new Object[]{
                                              getCustomImportScript(),
                                              getCustomFunction()});
        if(value == null)
            return;
        setCustomImportScript(TypeTool.getString(value[0]));
        setCustomFunction(TypeTool.getString(value[1]));
        if(!make())
            messageBox(getMakeErrText());
        getPM().getMacroroutineManager().show();
    }
    /**
     * 弹出对话框提示消息
     * @param message Object
     */
    public void messageBox(Object message){
        getUI().messageBox(message);
    }
}
