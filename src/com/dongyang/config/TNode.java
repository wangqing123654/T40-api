package com.dongyang.config;

import java.util.ArrayList;
import java.util.Map;
import org.w3c.dom.*;
import java.util.HashMap;
import com.dongyang.util.StringTool;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class TNode implements INode {
    //父元素
    private INode m_parent;
    //名称
    private String m_strName;
    //参数值
    private String m_strValue;
    //属性集合
    private Map m_attributes;
    //子元素集合
    private ArrayList m_children;

    /**
     * 初始化配置类
     * @param name 名称
     */
    public TNode(String name) {
        setName(name);
    }

    /**
     * 初始化配置类
     * @param element 元素
     * @param configelement 父类对象
     */
    public TNode(Element element, INode configelement) {
        //加载名称和值
        if (element == null)
            return;
        m_strName = element.getTagName();
        Node node = element.getFirstChild();
        if (node != null && node.getNodeType() == 3) {
            m_strValue = node.getNodeValue();
            if (m_strValue != null)
                m_strValue = m_strValue.trim();
        }
        //加载属性
        NamedNodeMap namednodemap = element.getAttributes();
        for (int i = 0; i < namednodemap.getLength(); i++) {
            if (m_attributes == null)
                m_attributes = new HashMap();
            Node node1 = namednodemap.item(i);
            String name = node1.getNodeName();
            String value = node1.getNodeValue();
            m_attributes.put(name, value);
        }
        //加载子元素集合
        NodeList nodelist = element.getChildNodes();
        if (nodelist == null)
            return;
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node2 = nodelist.item(i);
            if (node2.getNodeType() == 1) {
                INode configelementChild = new TNode((Element) node2, this);
                if (m_children == null) {
                    m_children = new ArrayList();
                }
                m_children.add(configelementChild);
                configelementChild.setParent(this);
            }
        }
    }

    /**
     * 得到名称
     * @return 名称
     */
    public String getName() {
        return m_strName;
    }

    /**
     * 设置名称
     * @param name 名称
     */
    public void setName(String name) {
        m_strName = name;
    }

    /**
     * 得到父类
     * @return 父类
     */
    public INode getParent() {
        return m_parent;
    }

    /**
     * 设置父类
     * @param node 父类对象
     */
    public void setParent(INode node) {
        if (m_parent != node) {
            m_parent = node;
            if (m_parent != null)
                m_parent.addChildElement(this);
        }
    }

    /**
     * 增加子元素
     * @param node 元素
     */
    public void addChildElement(INode node) {
        if (node.getParent() != this) {
            if (m_children == null)
                m_children = new ArrayList();
            m_children.add(node);
            node.setParent(this);
        }
    }
    /**
     * 子元素个数
     * @return int
     */
    public int size()
    {
        if(m_children == null)
            return 0;
        return m_children.size();
    }
    /**
     * 得到子成员
     * @param index int
     * @return INode
     */
    public INode getChildElement(int index)
    {
        return (INode)m_children.get(index);
    }
    /**
     * 删除子元素
     * @param node 元素
     */
    public void removeChildElement(INode node) {
        if (node.getParent() == this)
            m_children.remove(node);
    }

    /**
     * 得到值
     * @return 返回值
     */
    public String getValue() {
        if (m_strValue == null)
            return "";
        return m_strValue;
    }

    /**
     * 设置值
     * @param value String
     */
    public void setValue(String value) {
        m_strValue = value;
    }

    /**
     * 得到Boolean对象值
     * @return Boolean对象值
     */
    public Boolean getValueAsBoolean() {

        if (m_strValue == null)
            return null;
        return StringTool.getBoolean(m_strValue);
    }

    /**
     * 得到Integer对象值
     * @return Integer对象值
     */
    public Integer getValueAsInteger() {
        if (m_strValue == null)
            return null;
        return StringTool.getInt(m_strValue);
    }

    /**
     * 得到属性值
     * @param name 属性名称
     * @return String
     */
    public String getAttributeValue(String name) {
        if (m_attributes != null)
            return (String) m_attributes.get(name);
        return null;
    }

    /**
     * 得到属性Boolean值
     * @param name 属性名称
     * @return Boolean
     */
    public Boolean getAttributeValueAsBoolean(String name) {
        if (m_attributes == null)
            return null;
        String s1 = (String) m_attributes.get(name);
        if (s1 == null)
            return null;
        return StringTool.getBoolean(s1);
    }

    /**
     * 得到属性Integer值
     * @param name 属性名称
     * @return Integer 值
     */
    public Integer getAttributeValueAsInteger(String name) {
        if (m_attributes == null)
            return null;
        String s1 = (String) m_attributes.get(name);
        if (s1 == null)
            return null;
        return StringTool.getInt(s1);
    }

    /**
     * 得到属性Double值
     * @param name 属性名称
     * @return Double 值
     */
    public Double getAttributeValueAsDouble(String name) {
        if (m_attributes == null)
            return null;
        String s1 = (String) m_attributes.get(name);
        if (s1 == null)
            return null;
        return StringTool.getDouble(s1);
    }

    /**
     * 得到全部名称
     * @return 迭代器
     */
    public Iterator getAttributeNames() {
        if (m_attributes != null)
            return m_attributes.keySet().iterator();
        else
            return (new ArrayList()).iterator();
    }

    /**
     * 设置属性值
     * @param name 名称
     * @param value 值
     */
    public void setAttributeValue(String name, String value) {
        if (m_attributes == null)
            m_attributes = new HashMap();
        m_attributes.put(name, value);
    }

    /**
     * 设置属性boolean值
     * @param name 名称
     * @param flag 值
     */
    public void setAttributeValue(String name, boolean flag) {
        if (m_attributes == null)
            m_attributes = new HashMap();
        m_attributes.put(name, flag ? "true" : "false");
    }

    /**
     * 设置属性int值
     * @param name 名称
     * @param value 值
     */
    public void setAttributeValue(String name, int value) {
        if (m_attributes == null)
            m_attributes = new HashMap();
        m_attributes.put(name, Integer.toString(value));
    }

    /**
     * 设置属性double值
     * @param name 名称
     * @param value 值
     */
    public void setAttributeValue(String name, double value) {
        if (m_attributes == null)
            m_attributes = new HashMap();
        m_attributes.put(name, Double.toString(value));
    }

    /**
     * 得到全部子元素
     * @return 迭代器
     */
    public Iterator getChildElements() {
        if (m_children != null) {
            ArrayList arraylist = new ArrayList(m_children.size());
            for (int i = 0; i < m_children.size(); i++) {
                INode node = (INode) m_children.get(i);
                arraylist.add(node);
            }

            return arraylist.iterator();
        } else {
            return (new ArrayList()).iterator();
        }
    }

    /**
     * 得到全部子元素
     * @param name 名称
     * @return 迭代器
     */
    public Iterator getChildElements(String name) {
        ArrayList arraylist = new ArrayList(1);
        Pattern pattern = new Pattern(name);
        if (m_children != null && arraylist.size() == 0) {
            for (int i = 0; i < m_children.size(); i++) {
                INode node = (INode) m_children.get(i);
                if (pattern.matches(node))
                    arraylist.add(node);
            }

        }
        return arraylist.iterator();
    }

    /**
     * 得到全部子元素
     * @param name String
     * @return INode 子成员
     */
    public INode getChildElement(String name) {
        INode node = null;
        Pattern pattern = new Pattern(name);
        if (node == null && m_children != null) {
            for (int i = 0; i < m_children.size(); i++) {
                INode node1 = (INode) m_children.get(i);
                if (!pattern.matches(node1))
                    continue;
                if (node != null) {
                    node = null;
                    break;
                }
                node = node1;
            }

        }
        return node;
    }

    /**
     * 得到子值
     * @param name 名称
     * @return 值
     */
    public String getChildValue(String name) {
        INode node = getChildElement(name);
        if (node == null)
            return null;
        return node.getValue();
    }

    /**
     * 得到Boolean子值
     * @param name 名称
     * @return Boolean值
     */
    public Boolean getChildValueAsBoolean(String name) {
        INode node = getChildElement(name);
        if (node == null)
            return null;
        return node.getValueAsBoolean();
    }

    /**
     * 得到Integer子值
     * @param name 名称
     * @return Integer值
     */
    public Integer getChildValueAsInteger(String name) {
        INode node = getChildElement(name);
        if (node == null)
            return null;
        return node.getValueAsInteger();
    }

    /**
     * 得到后代元素
     * @param name 名称
     * @return 后代元素
     */
    public INode getDescendantElement(String name) {
        Object obj = this;
        for (StringTokenizer stringtokenizer = new StringTokenizer(name, ".");
                                               stringtokenizer.hasMoreElements() &&
                                               obj != null;
                                               obj = ((INode) (obj)).
                getChildElement(stringtokenizer.
                                nextToken()))
            ;
        return ((INode) (obj));
    }

    /**
     * 得到后代值
     * @param name 名称
     * @return 值
     */
    public String getDescendantValue(String name) {
        INode node = getDescendantElement(name);
        if (node != null)
            return node.getValue();
        else
            return null;
    }

    /**
     * 得到后代Boolean值
     * @param name 名称
     * @return Boolean值
     */
    public Boolean getDescendantValueAsBoolean(String name) {
        INode node = getDescendantElement(name);
        if (node == null)
            return null;
        return node.getValueAsBoolean();
    }

    /**
     * 得到后代Integer值
     * @param name 名称
     * @return Integer值
     */
    public Integer getDescendantValueAsInteger(String name) {
        INode node = getDescendantElement(name);
        if (node == null)
            return null;
        return node.getValueAsInteger();
    }

    /**
     *
     * <p>Title: 样品规定</p>
     * <p>Description: 内部使用类</p>
     * <p>Copyright: Copyright (c) 2006</p>
     * <p>Company: 东佑项目部</p>
     * @author lzk 2006.06.23
     * @version 1.0
     */
    private class Pattern {

        public boolean matches(INode node) {
            if (!node.getName().equals(m_strName))
                return false;
            if (m_strAttributeName != null) {
                String s = node.getAttributeValue(m_strAttributeName);
                if (s == null || !s.equals(m_strAttributeValue))
                    return false;
            }
            return true;
        }

        public String getName() {
            return m_strName;
        }

        private String m_strName;
        private String m_strAttributeName;
        private String m_strAttributeValue;

        public Pattern(String s) {
            int i = s.indexOf(32);
            if (i != -1)
                throw new IllegalArgumentException(
                        "Invalid Argument 'pattern': 'element-name[attribute-name=attribute-value]'");
            int j = s.indexOf(91);
            if (j != -1) {
                int k = s.indexOf(61, j);
                if (k == -1)
                    throw new IllegalArgumentException("Invalid Argument 'pattern': Missing '=' in 'element-name[attribute-name=attribute-value]'");
                int l = s.indexOf(93, k);
                if (l == -1)
                    throw new IllegalArgumentException("Invalid Argument 'pattern': Missing ']' in 'element-name[attribute-name=attribute-value]'");
                m_strAttributeName = s.substring(j + 1, k);
                if (m_strAttributeName == null ||
                    m_strAttributeName.length() == 0)
                    throw new IllegalArgumentException("Invalid Argument 'pattern': Missing attribute-name in 'element-name[attribute-name=attribute-value]'");
                m_strAttributeValue = s.substring(k + 1, l);
                if (m_strAttributeValue == null ||
                    m_strAttributeValue.length() == 0)
                    throw new IllegalArgumentException("Invalid Argument 'pattern': Missing attribute-value in 'element-name[attribute-name=attribute-value]'");
                m_strName = s.substring(0, j);
            } else {
                m_strName = s;
            }
            if (m_strName == null || m_strName.length() == 0)
                throw new IllegalArgumentException("Invalid Argument 'pattern': Missing element-name in 'element-name[attribute-name=attribute-value]'");
            else
                return;
        }
    }


    /**
     * 对象转换成 String 型方法
     * @return 将全部数据转换成字符串信息
     */
    public String toString() {
        StringBuffer stringbuffer = new StringBuffer(128);
        /*if (m_parent != null && (m_parent instanceof INode)) {
            INode configelement = (INode) m_parent;
            stringbuffer.append(configelement.toString());
            stringbuffer.append('.');
        }*/
        stringbuffer.append(m_strName);
        boolean flag = false;
        String value;
        for (Iterator iterator = getAttributeNames(); iterator.hasNext();
                                 stringbuffer.append(value)) {
            if (!flag) {
                stringbuffer.append('[');
                flag = true;
            } else {
                stringbuffer.append(',');
            }
            String name = (String) iterator.next();
            value = getAttributeValue(name);
            stringbuffer.append(name);
            stringbuffer.append('=');
        }

        if (flag)
            stringbuffer.append(']');
        stringbuffer.append("\n");
        for(int i = 0;i < size();i++)
            stringbuffer.append(getChildElement(i));
        return stringbuffer.toString();
    }

    /**
     * 解析XML
     * @param inputsource InputSource
     * @return INode
     */
    public static INode loadXML(InputSource inputsource) {
        Element element = null;
        try {
            DocumentBuilderFactory documentbuilderfactory =
                    DocumentBuilderFactory.
                    newInstance();
            documentbuilderfactory.setNamespaceAware(true);
            DocumentBuilder documentbuilder = documentbuilderfactory.
                                              newDocumentBuilder();
            Document document = documentbuilder.parse(inputsource);
            element = document.getDocumentElement();
        } catch (Exception e) {
        }
        if (element == null)
            return null;
        return new TNode(element, null);
    }

    /**
     * 解析XML
     * @param inputStream InputStream
     * @return INode
     */
    public static INode loadXML(InputStream inputStream) {
        return loadXML(new InputSource(inputStream));
    }
    /**
     * 解析XML
     * @param data byte[]
     * @return INode
     */
    public static INode loadXML(byte[] data)
    {
        if(data == null)
            return null;
        return loadXML(new ByteArrayInputStream(data));
    }
    /**
     * 解析XML
     * @param data String
     * @return INode
     */
    public static INode loadXML(String data)
    {
        return loadXML(data.getBytes());
    }
}
