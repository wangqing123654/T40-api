package com.dongyang.config;

import java.util.Iterator;

public interface INode {
    /**
     * ��������
     * @param name ����
     */
    public void setName(String name);
    /**
     * �õ�����
     * @return ����
     */
    public String getName();
    /**
     * �õ�����
     * @return ����
     */
    public INode getParent();
    /**
     * ���ø���
     * @param node �������
     */
    public void setParent(INode node);
    /**
     * ����ֵ
     * @param value String
     */
    public void setValue(String value);
    /**
     * �õ�ֵ
     * @return ����ֵ
     */
    public String getValue();
    /**
     * �õ�Boolean����ֵ
     * @return Boolean����ֵ
     */
    public Boolean getValueAsBoolean();
    /**
     * �õ�Integer����ֵ
     * @return Integer����ֵ
     */
    public Integer getValueAsInteger();
    /**
     * �õ�����ֵ
     * @param name ��������
     * @return String
     */
    public String getAttributeValue(String name);
    /**
     * �õ�����Booleanֵ
     * @param name ��������
     * @return Boolean
     */
    public Boolean getAttributeValueAsBoolean(String name);

    /**
     * �õ�����Integerֵ
     * @param name ��������
     * @return Integer
     */
    public Integer getAttributeValueAsInteger(String name);
    /**
     * �õ�����Doubleֵ
     * @param name ��������
     * @return Double ֵ
     */
    public Double getAttributeValueAsDouble(String name);
    /**
     * ��������ֵ
     * @param name ����
     * @param value ֵ
     */
    public void setAttributeValue(String name, String value);

    /**
     * ��������booleanֵ
     * @param name ����
     * @param flag ֵ
     */
    public void setAttributeValue(String name, boolean flag);

    /**
     * ��������intֵ
     * @param name ����
     * @param value ֵ
     */
    public void setAttributeValue(String name, int value);
    /**
     * ��������doubleֵ
     * @param name ����
     * @param value ֵ
     */
    public void setAttributeValue(String name, double value);
    /**
     * �õ�ȫ������
     * @return ������
     */
    public Iterator getAttributeNames();
    /**
     * �õ�ȫ����Ԫ��
     * @return ������
     */
    public Iterator getChildElements();
    /**
     * �õ�ȫ����Ԫ��
     * @param name ����
     * @return ������
     */
    public Iterator getChildElements(String name);
    /**
     * �õ�ȫ����Ԫ��
     * @param name ����
     * @return �ӳ�Ա
     */
    public INode getChildElement(String name);
    /**
     * �õ���ֵ
     * @param name ����
     * @return ֵ
     */
    public String getChildValue(String name);
    /**
     * �õ�Boolean��ֵ
     * @param name ����
     * @return Booleanֵ
     */
    public Boolean getChildValueAsBoolean(String name);

    /**
     * �õ�Integer��ֵ
     * @param name ����
     * @return Integerֵ
     */
    public Integer getChildValueAsInteger(String name);

    /**
     * �õ����Ԫ��
     * @param name ����
     * @return ���Ԫ��
     */
    public INode getDescendantElement(String name);
    /**
     * �õ����ֵ
     * @param name ����
     * @return ֵ
     */
    public String getDescendantValue(String name);

    /**
     * �õ����Booleanֵ
     * @param name ����
     * @return Booleanֵ
     */
    public Boolean getDescendantValueAsBoolean(String name);

    /**
     * �õ����Integerֵ
     * @param name ����
     * @return Integerֵ
     */
    public Integer getDescendantValueAsInteger(String name);
    /**
     * ������Ԫ��
     * @param node Ԫ��
     */
    public void addChildElement(INode node);
    /**
     * ɾ����Ԫ��
     * @param node Ԫ��
     */
    public void removeChildElement(INode node);
    /**
     * ��Ԫ�ظ���
     * @return int
     */
    public int size();
    /**
     * �õ��ӳ�Ա
     * @param index int
     * @return INode
     */
    public INode getChildElement(int index);
}
