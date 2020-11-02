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
 * <p>Title: �﷨������</p>
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
     * ��Ա�б�
     */
    private TList components;
    /**
     * import�б�
     */
    private TList imports;
    /**
     * ������
     */
    private PM pm;
    /**
     * ����
     */
    private String packageName = "jhwc";
    /**
     * ����
     */
    private String className = "";
    /**
     * JavaC����
     */
    private JavaCTool tool;
    /**
     * ���������Ϣ
     */
    private String makeErrText = "";
    /**
     * �Զ���import�ű�
     */
    private String customImportScript = "";
    /**
     * �Զ������Է����ű�
     */
    private String customFunction = "";
    /**
     * �������
     */
    private DynamicClassLoader classLoader;
    /**
     * ������
     */
    private SyntaxControl control;
    /**
     * ������������
     */
    private byte[] controlClassData;
    /**
     * ������
     */
    public MSyntax()
    {
        components = new TList();
        imports = new TList();
        tool = new JavaCTool();
        //��ʼ��import
        initImports();
    }
    /**
     * ��ʼ��import
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
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * �õ���ʾ��
     * @return MView
     */
    public MView getViewManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getViewManager();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFocusManager();
    }
    /**
     * ���ý���λ��
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        if(getFocusManager() == null)
            return;
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * �õ�����λ��
     * @return int
     */
    public int getFocusIndex()
    {
        if(getFocusManager() == null)
            return 0;
        return getFocusManager().getFocusIndex();
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getFocus()
    {
        if(getFocusManager() == null)
            return null;
        return getFocusManager().getFocus();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
            return null;
        return getPM().getUI();
    }
    /**
     * �õ��ļ�������
     * @return MFile
     */
    public MFile getFileManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFileManager();
    }
    /**
     * ���ӳ�Ա
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
     * ɾ����Ա
     * @param item ESyntaxItem
     */
    public void remove(ESyntaxItem item)
    {
        components.remove(item);
    }
    /**
     * ɾ����Ա
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
    }
    /**
     * ȫ�����
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * ���
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
     * ��Ա����
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * �õ���Ա
     * @param index int
     * @return ESyntaxItem
     */
    public ESyntaxItem get(int index)
    {
        return (ESyntaxItem) components.get(index);
    }
    /**
     * �õ���Ա�б�
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * ����import
     * @param syntax String
     */
    public void addImport(String syntax)
    {
        imports.add(syntax);
    }
    /**
     * �õ�import�б�
     * @return TList
     */
    public TList getImportList()
    {
        return imports;
    }
    /**
     * ����λ��
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
     * ���ð���
     * @param packageName String
     */
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getPackageName()
    {
        return packageName;
    }
    /**
     * ��������
     * @param className String
     */
    public void setClassName(String className)
    {
        this.className = className;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getClassName()
    {
        return className;
    }
    /**
     * ���ÿ�����������
     * @param controlClassData byte[]
     */
    public void setControlClassData(byte[] controlClassData)
    {
        this.controlClassData = controlClassData;
    }
    /**
     * �õ�������������
     * @return byte[]
     */
    public byte[] getControlClassData()
    {
        return controlClassData;
    }
    /**
     * �����Զ���import�ű�
     * @param customImportScript String
     */
    public void setCustomImportScript(String customImportScript)
    {
        this.customImportScript = customImportScript;
    }
    /**
     * �õ��Զ���import�ű�
     * @return String
     */
    public String getCustomImportScript()
    {
        return customImportScript;
    }
    /**
     * �����Զ������Է����ű�
     * @param customFunction String
     */
    public void setCustomFunction(String customFunction)
    {
        this.customFunction = customFunction;
    }
    /**
     * �õ��Զ������Է����ű�
     * @return String
     */
    public String getCustomFunction()
    {
        return customFunction;
    }
    /**
     * �õ����﷨
     * @return String
     */
    public String packageScript()
    {
        if(getPackageName() == null || getPackageName().length() == 0)
            return "";
        return "package " + getPackageName() + ";\n";
    }
    /**
     * import �﷨
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
     * �࿪ʼ�﷨
     * @return String
     */
    public String classBeginScript()
    {
        return "public class " + getClassName() + " extends SyntaxControl{\n";
    }
    /**
     * ������﷨
     * @return String
     */
    public String classEndScript()
    {
        return "}\n";
    }
    /**
     * �﷨
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
     * �����ű�
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
     * �õ�Src����
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
     * �õ�JavaC����
     * @return JavaCTool
     */
    public JavaCTool getTool()
    {
        return tool;
    }
    /**
     * ���ô�����Ϣ
     * @param makeErrText String
     */
    public void setMakeErrText(String makeErrText)
    {
        this.makeErrText = makeErrText;
    }
    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getMakeErrText()
    {
        return makeErrText;
    }
    /**
     * ����
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
            setMakeErrText("�ű��ļ�����IO����!");
            return false;
        }
        TParm parm = new TParm(getTool().javac(srcName));
        if(parm.getErrCode() < 0)
        {
            System.out.println("MSyntax.make()javac err");
            System.out.println(parm.getErrText());
            setMakeErrText("�������:" + parm.getErrText());
            return false;
        }
        setMakeErrText(parm.getValue("err"));
        //���¼��ؿ�����
        resetControl();
        return true;
    }
    /**
     * �����������
     * @param classLoader DynamicClassLoader
     */
    public void setClassLoader(DynamicClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }
    /**
     * �õ��������
     * @return DynamicClassLoader
     */
    public DynamicClassLoader getClassLoader()
    {
        if(classLoader == null)
            resetClassLoader();
        return classLoader;
    }
    /**
     * �������������
     */
    public void resetClassLoader()
    {
        classLoader = new DynamicClassLoader();
        classLoader.setSocket(getSocket());
        classLoader.setRealPath("");
        classLoader.setConfigParm(getUI().getConfigParm());
    }
    /**
     * �õ�������
     * @return TSocket
     */
    public TSocket getSocket()
    {
        return TIOM_AppServer.SOCKET;
    }
    /**
     * ��̬���ض���
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
     * ��ʼ��������
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
     * ���ÿ�����
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
     * ��
     */
    public void onOpen()
    {
        SyntaxControl control = getControl();
        if(control == null)
            return;
        control.onOpen();
    }
    /**
     * �õ�������
     * @return SyntaxControl
     */
    public SyntaxControl getControl()
    {
        return this.control;
    }
    /**
     * ִ�з���
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
     * ִ�з���
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
     * ���¼��ؿ�����
     */
    public void resetControl()
    {
        //�������������
        resetClassLoader();
        Object obj = loadObject(getSrcName());
        if(obj == null || !(obj instanceof SyntaxControl))
            return;
        SyntaxControl control = (SyntaxControl)obj;
        setControl(control);
    }
    /**
     * д����
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
     * д����
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
     * ������
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
        //��ʼ��������
        initControl();
        //��ȡҳ
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            ESyntaxItem item = new ESyntaxItem(this);
            add(item);
            item.readObject(s);
        }
    }
    /**
     * �Զ����﷨�Ի���
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
     * �����Ի�����ʾ��Ϣ
     * @param message Object
     */
    public void messageBox(Object message){
        getUI().messageBox(message);
    }
}
