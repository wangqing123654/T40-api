package com.dongyang.ui;

import java.awt.Color;
import com.dongyang.util.StringTool;

public class TComboNode {
    /**
     * ID
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 英文名称
     */
    private String enName;
    /**
     * 文字
     */
    private String text;
    /**
     * 英文文本
     */
    private String enText;
    /**
     * 类型
     */
    private String type;
    /**
     * 值
     */
    private String value;
    /**
     * 拼音1
     */
    private String py1;
    /**
     * 拼音2
     */
    private String py2;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 数据对象
     */
    private Object data;
    /**
     * 构造器
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
     * 设置ID
     * @param id String
     */
    public void setID(String id)
    {
        this.id = id;
    }
    /**
     * 得到ID
     * @return String
     */
    public String getID()
    {
        return id;
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 设置英文名称
     * @param enName String
     */
    public void setEnName(String enName)
    {
        this.enName = enName;
    }
    /**
     * 得到英文名称
     * @return String
     */
    public String getEnName()
    {
        return enName;
    }
    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
    }
    /**
     * 得到文本
     * @return String
     */
    public String getText()
    {
        return text;
    }
    /**
     * 设置英文文本
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * 得到英文文本
     * @return String
     */
    public String getEnText()
    {
        return enText;
    }
    /**
     * 设置类型
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * 设置值
     * @param value String
     */
    public void setValue(String value)
    {
        this.value = value;
    }
    /**
     * 得到值
     * @return String
     */
    public String getValue()
    {
        return value;
    }
    /**
     * 设置拼音1
     * @param py1 String
     */
    public void setPy1(String py1)
    {
        this.py1 = py1;
    }
    /**
     * 得到拼音1
     * @return String
     */
    public String getPy1()
    {
        return py1;
    }
    /**
     * 设置拼音2
     * @param py2 String
     */
    public void setPy2(String py2)
    {
        this.py2 = py2;
    }
    /**
     * 得到拼音2
     * @return String
     */
    public String getPy2()
    {
        return py2;
    }
    /**
     * 设置颜色
     * @param color Color
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    /**
     * 得到颜色
     * @return Color
     */
    public Color getColor()
    {
        return color;
    }
    /**
     * 设置数据对象
     * @param data Object
     */
    public void setData(Object data)
    {
        this.data = data;
    }
    /**
     * 得到数据对象
     * @return Object
     */
    public Object getData()
    {
        return data;
    }
    /**
     * 得到显示文字
     * @param showList String
     * @return String
     */
    public String getShowValue(String showList)
    {
        return getShowValue(showList);
    }
    /**
     * 得到显示文字
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
