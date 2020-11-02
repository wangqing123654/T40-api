package com.dongyang.tui.text;

import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: �﷨��Ŀ</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.10
 * @version 1.0
 */
public class ESyntaxItem
{
    /**
     * ������
     */
    private MSyntax manager;
    /**
     * ����
     * 1 ��ʽ
     * 2 ����
     */
    private int type = 1;
    /**
     * ����
     */
    private String name;
    /**
     * �﷨
     */
    private String syntax = "";
    /**
     * ����
     * 1 ��
     * 2 ͼ��
     */
    private int classify = 1;
    /**
     * �������
     */
    private String inValue = "";
    /**
     * �������
     */
    private String outValue = "void";
    /**
     * ����������
     */
    private String functionNameValue = "";
    /**
     * ������
     */
    public ESyntaxItem()
    {

    }
    /**
     * ������
     * @param manager MSyntax
     */
    public ESyntaxItem(MSyntax manager)
    {
        setManager(manager);
    }
    /**
     * ���ù�����
     * @param manager MSyntax
     */
    public void setManager(MSyntax manager)
    {
        this.manager = manager;
    }
    /**
     * �õ�������
     * @return MSyntax
     */
    public MSyntax getManager()
    {
        return manager;
    }
    /**
     * ��������
     * @param type int
     * 1 ��ʽ
     * 2 ����
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return int
     * 1 ��ʽ
     * 2 ����
     */
    public int getType()
    {
        return type;
    }
    /**
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * �����﷨
     * @param syntax String
     */
    public void setSyntax(String syntax)
    {
        this.syntax = syntax;
    }
    /**
     * �õ��﷨
     * @return String
     */
    public String getSyntax()
    {
        return syntax;
    }
    /**
     * ���÷���
     * @param classify int
     */
    public void setClassify(int classify)
    {
        this.classify = classify;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getClassify()
    {
        return classify;
    }
    /**
     * �����������
     * @param inValue String
     */
    public void setInValue(String inValue)
    {
        this.inValue = inValue;
    }
    /**
     * �����������
     * @return String
     */
    public String getInValue()
    {
        return inValue;
    }
    /**
     * �����������
     * @param outValue String
     */
    public void setOutValue(String outValue)
    {
        this.outValue = outValue;
    }
    /**
     * �õ��������
     * @return String
     */
    public String getOutValue()
    {
        return outValue;
    }
    /**
     * ���÷���������
     * @param functionNameValue String
     */
    public void setFunctionNameValue(String functionNameValue)
    {
        this.functionNameValue = functionNameValue;
    }
    /**
     * �õ�����������
     * @return String
     */
    public String getFunctionNameValue()
    {
        return functionNameValue;
    }
    /**
     * �������λ��
     * @param item ESyntaxItem
     * @return int
     */
    public int findIndex(ESyntaxItem item)
    {
        return getManager().indexOf(item);
    }
    /**
     * �����Լ���λ��
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
    }
    /**
     * �õ�������
     * @return String
     */
    public String getMethodName()
    {
        return "m_" + findIndex();
    }
    /**
     * �õ��ű�
     * @return String
     */
    public String getScript()
    {
        switch(getClassify())
        {
        case 1:
            return getMacroroutineScript();
        case 2:
            return getPicScript();
        }
        return "";
    }
    /**
     * �õ����﷨
     * @return String
     */
    public String getPicScript()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("/**\n * ");
        sb.append(getName());
        sb.append("(");
        if(getType() == 1)
            sb.append("��ʽ");
        else
            sb.append("����");
        sb.append(")\n */\n");
        sb.append("public " + getOutValue() + " " + getMethodName() + getFunctionNameValue() + "(" + getInValue() + "){\n");
        switch(getType())
        {
        case 1:
            sb.append(getSyntaxScript());
            break;
        }
        sb.append("}\n");
        return sb.toString();
    }
    /**
     * �õ����﷨
     * @return String
     */
    public String getMacroroutineScript()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("/**\n * ");
        sb.append(getName());
        sb.append("(");
        if(getType() == 1)
            sb.append("��ʽ");
        else
            sb.append("����");
        sb.append(")\n * @return String ����ֵ\n */\n");
        sb.append("public String " + getMethodName() + "(EMacroroutine This){\n");
        //��������ͷ�ű�
        sb.append(createMethodHeadScriptMacroroutine());
        switch(getType())
        {
        case 1:
            sb.append("return \"\"");
            if(getSyntax() != null && getSyntax().length() > 0)
            {
                sb.append(" + ");
                sb.append(getSyntaxScript());
            }
            sb.append(";\n");
            break;
        case 2:
            sb.append(getSyntaxScript());
            break;
        }
        sb.append("}\n");
        
        return sb.toString();
    }
    /**
     * �õ��﷨�ű�
     * @return String
     */
    public String getSyntaxScript()
    {
        String syntax = getSyntax();
        int index = syntax.indexOf("#");
        while(index >= 0)
        {
            String s1 = syntax.substring(0,index);
            String s2 = syntax.substring(index + 1);
            int i = 0;
            for(i = 0;i < s2.length();i++)
            {
                char c = s2.charAt(i);
                if(c < '0' && c > 9)
                    break;
            }
            if(i == 0 || (s1.endsWith("#")))
            {
                syntax = s1 + "#" + s2;
                index = syntax.indexOf("#",index + 1);
                continue;
            }
            syntax = s1 + "This.getData(" + s2.subSequence(0,i) + ")" + s2.substring(i);
            index = syntax.indexOf("#",index + 20);
        }
        return syntax;
    }
    /**
     * ��������ͷ�ű�
     * @return String
     */
    public String createMethodHeadScriptMacroroutine()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("String thisMethodName = getThisMethodName(0);\n");
        sb.append("int thisMethodIndex = getThisMethodIndex(thisMethodName);\n");
        sb.append("int row = This.getRow();\n");
        sb.append("int page = This.getPageIndex() + 1;\n");
        sb.append("int pageCount = getPageCount();\n");
        return sb.toString();
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
        s.WO("<ESyntaxItem>",c);
        s.WO(1,"Type",getType(),"",c + 1);
        s.WO(2,"Name",getName(),"",c + 1);
        s.WO(3,"Syntax",getSyntax(),"",c + 1);
        s.WO(4,"Classify",getClassify(),"",c + 1);
        s.WO(4,"InValue",getInValue(),"",c + 1);
        s.WO(4,"OutValue",getOutValue(),"",c + 1);
        s.WO(4,"FunctionNameValue",getFunctionNameValue(),"",c + 1);
        s.WO("</ESyntaxItem>",c);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(1, getType(),1);
        s.writeString(2, getName(), "");
        s.writeString(3, getSyntax(), "");
        s.writeInt(4, getClassify(),1);
        s.writeString(5, getInValue(),"");
        s.writeString(6, getOutValue(),"");
        s.writeString(7, getFunctionNameValue(),"");
        s.writeShort( -1);
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
                setType(s.readInt());
                break;
            case 2:
                setName(s.readString());
                break;
            case 3:
                setSyntax(s.readString());
                break;
            case 4:
                setClassify(s.readInt());
                break;
            case 5:
                setInValue(s.readString());
                break;
            case 6:
                setOutValue(s.readString());
                break;
            case 7:
                setFunctionNameValue(s.readString());
                break;
            }
            id = s.readShort();
        }
    }
    /**
     * ɾ���Լ�
     */
    public void removeThis()
    {
        getManager().remove(this);
    }
}
