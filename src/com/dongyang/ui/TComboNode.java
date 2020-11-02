package com.dongyang.ui;

import java.awt.Color;
import com.dongyang.util.StringTool;

public class TComboNode {
    /**
     * ID
     */
    private String id;
    /**
     * ����
     */
    private String name;
    /**
     * Ӣ������
     */
    private String enName;
    /**
     * ����
     */
    private String text;
    /**
     * Ӣ���ı�
     */
    private String enText;
    /**
     * ����
     */
    private String type;
    /**
     * ֵ
     */
    private String value;
    /**
     * ƴ��1
     */
    private String py1;
    /**
     * ƴ��2
     */
    private String py2;
    /**
     * ��ɫ
     */
    private Color color;
    /**
     * ���ݶ���
     */
    private Object data;
    /**
     * ������
     */
    public TComboNode()
    {
        setID("");
        setName("");
        setText("");
        setPy1("");
        setPy2("");
        setType("");
        setValue("");
    }
    /**
     * ����ID
     * @param id String
     */
    public void setID(String id)
    {
        this.id = id;
    }
    /**
     * �õ�ID
     * @return String
     */
    public String getID()
    {
        return id;
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
     * ����Ӣ������
     * @param enName String
     */
    public void setEnName(String enName)
    {
        this.enName = enName;
    }
    /**
     * �õ�Ӣ������
     * @return String
     */
    public String getEnName()
    {
        return enName;
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        return text;
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
     * �õ�Ӣ���ı�
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }
    /**
     * ��������
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * ����ֵ
     * @param value String
     */
    public void setValue(String value)
    {
        this.value = value;
    }
    /**
     * �õ�ֵ
     * @return String
     */
    public String getValue()
    {
        return value;
    }
    /**
     * ����ƴ��1
     * @param py1 String
     */
    public void setPy1(String py1)
    {
        this.py1 = py1;
    }
    /**
     * �õ�ƴ��1
     * @return String
     */
    public String getPy1()
    {
        return py1;
    }
    /**
     * ����ƴ��2
     * @param py2 String
     */
    public void setPy2(String py2)
    {
        this.py2 = py2;
    }
    /**
     * �õ�ƴ��2
     * @return String
     */
    public String getPy2()
    {
        return py2;
    }
    /**
     * ������ɫ
     * @param color Color
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    /**
     * �õ���ɫ
     * @return Color
     */
    public Color getColor()
    {
        return color;
    }
    /**
     * �������ݶ���
     * @param data Object
     */
    public void setData(Object data)
    {
        this.data = data;
    }
    /**
     * �õ����ݶ���
     * @return Object
     */
    public Object getData()
    {
        return data;
    }
    /**
     * �õ���ʾ����
     * @param showList String
     * @return String
     */
    public String getShowValue(String showList)
    {
        return getShowValue(showList);
    }
    /**
     * �õ���ʾ����
     * @param showList String
     * @param language String
     * @return String
     */
    public String getShowValue(String showList,String language)
    {
        StringBuffer sb = new StringBuffer();
        String s[] = StringTool.parseLine(showList,",");
        for(int i = 0;i < s.length;i++)
        {
            String name = s[i].trim();
            if("ID".equalsIgnoreCase(name))
            {
                if(getID() == null||getID().length() == 0)
                    continue;
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(getID());
                continue;
            }
            if("Name".equalsIgnoreCase(name))
            {
                if("en".equals(language) && getEnName() != null && getEnName().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(getEnName());
                }
                else if(getName() != null && getName().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(getName());
                }
                continue;
            }
            if("Text".equalsIgnoreCase(name))
            {
                if("en".equals(language) && getEnText() != null && getEnText().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(getEnText());
                }
                else if(getText() != null && getText().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(getText());
                }
                continue;
            }
            if("Value".equalsIgnoreCase(name))
            {
                if(getValue() == null||getValue().length() == 0)
                    continue;
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(getValue());
                continue;
            }
            if("Type".equalsIgnoreCase(name))
            {
                if(getType() == null||getType().length() == 0)
                    continue;
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(getType());
                continue;
            }
            if("Py1".equalsIgnoreCase(name))
            {
                if(getPy1() == null||getPy1().length() == 0)
                    continue;
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(getPy1());
                continue;
            }
            if("Py2".equalsIgnoreCase(name))
            {
                if(getPy2() == null||getPy2().length() == 0)
                    continue;
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(getPy2());
                continue;
            }
        }
        return sb.toString();
    }
    public String toString()
    {
        return getShowValue("id,name,text","");
    }
}
