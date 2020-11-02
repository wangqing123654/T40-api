package com.dongyang.config;

import java.util.Iterator;

public interface INode {
    /**
     * 设置名称
     * @param name 名称
     */
    public void setName(String name);
    /**
     * 得到名称
     * @return 名称
     */
    public String getName();
    /**
     * 得到父类
     * @return 父类
     */
    public INode getParent();
    /**
     * 设置父类
     * @param node 父类对象
     */
    public void setParent(INode node);
    /**
     * 设置值
     * @param value String
     */
    public void setValue(String value);
    /**
     * 得到值
     * @return 返回值
     */
    public String getValue();
    /**
     * 得到Boolean对象值
     * @return Boolean对象值
     */
    public Boolean getValueAsBoolean();
    /**
     * 得到Integer对象值
     * @return Integer对象值
     */
    public Integer getValueAsInteger();
    /**
     * 得到属性值
     * @param name 属性名称
     * @return String
     */
    public String getAttributeValue(String name);
    /**
     * 得到属性Boolean值
     * @param name 属性名称
     * @return Boolean
     */
    public Boolean getAttributeValueAsBoolean(String name);

    /**
     * 得到属性Integer值
     * @param name 属性名称
     * @return Integer
     */
    public Integer getAttributeValueAsInteger(String name);
    /**
     * 得到属性Double值
     * @param name 属性名称
     * @return Double 值
     */
    public Double getAttributeValueAsDouble(String name);
    /**
     * 设置属性值
     * @param name 名称
     * @param value 值
     */
    public void setAttributeValue(String name, String value);

    /**
     * 设置属性boolean值
     * @param name 名称
     * @param flag 值
     */
    public void setAttributeValue(String name, boolean flag);

    /**
     * 设置属性int值
     * @param name 名称
     * @param value 值
     */
    public void setAttributeValue(String name, int value);
    /**
     * 设置属性double值
     * @param name 名称
     * @param value 值
     */
    public void setAttributeValue(String name, double value);
    /**
     * 得到全部名称
     * @return 迭代器
     */
    public Iterator getAttributeNames();
    /**
     * 得到全部子元素
     * @return 迭代器
     */
    public Iterator getChildElements();
    /**
     * 得到全部子元素
     * @param name 名称
     * @return 迭代器
     */
    public Iterator getChildElements(String name);
    /**
     * 得到全部子元素
     * @param name 名称
     * @return 子成员
     */
    public INode getChildElement(String name);
    /**
     * 得到子值
     * @param name 名称
     * @return 值
     */
    public String getChildValue(String name);
    /**
     * 得到Boolean子值
     * @param name 名称
     * @return Boolean值
     */
    public Boolean getChildValueAsBoolean(String name);

    /**
     * 得到Integer子值
     * @param name 名称
     * @return Integer值
     */
    public Integer getChildValueAsInteger(String name);

    /**
     * 得到后代元素
     * @param name 名称
     * @return 后代元素
     */
    public INode getDescendantElement(String name);
    /**
     * 得到后代值
     * @param name 名称
     * @return 值
     */
    public String getDescendantValue(String name);

    /**
     * 得到后代Boolean值
     * @param name 名称
     * @return Boolean值
     */
    public Boolean getDescendantValueAsBoolean(String name);

    /**
     * 得到后代Integer值
     * @param name 名称
     * @return Integer值
     */
    public Integer getDescendantValueAsInteger(String name);
    /**
     * 增加子元素
     * @param node 元素
     */
    public void addChildElement(INode node);
    /**
     * 删除子元素
     * @param node 元素
     */
    public void removeChildElement(INode node);
    /**
     * 子元素个数
     * @return int
     */
    public int size();
    /**
     * 得到子成员
     * @param index int
     * @return INode
     */
    public INode getChildElement(int index);
}
