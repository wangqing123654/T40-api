package com.dongyang.ui;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Font;
import java.awt.Color;
import javax.swing.tree.TreeNode;

public class TTreeNode extends DefaultMutableTreeNode{
    /**
     * ��
     */
    private String group;
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
     * Ӣ������
     */
    private String enText;
    /**
     * ����
     */
    private String type;
    /**
     * ��ʾ����
     */
    private String showType;
    /**
     * ֵ
     */
    private String value;
    /**
     * ״̬
     */
    private String state;
    /**
     * ����
     */
    private Font font;
    /**
     * ��ɫ
     */
    private Color color;
    /**
     * �����˵���ǩ
     */
    private String popupMenuTag;
    /**
     * ���ݶ���
     */
    private Object data;
    /**
     * ���
     */
    private int seq;
    /**
     * ��ʼ��չ��
     */
    private boolean initExpanded;
    /**
     * ������
     */
    public TTreeNode()
    {
        this("");
    }
    /**
     * ������
     * @param text String �ı�
     */
    public TTreeNode(String text)
    {
        this(text,"");
    }
    /**
     * ������
     * @param text String �ı�
     * @param type String ����
     */
    public TTreeNode(String text,String type)
    {
        this("",text,type,"");
    }
    /**
     *
     * @param value String ֵ
     * @param text String �ı�
     * @param type String ����
     * @param showType String ��ʾ����
     */
    public TTreeNode(String value,String text,String type,String showType)
    {
        this("","",value,text,type,showType);
    }
    /**
     * ������
     * @param id String ID
     * @param name String ����
     * @param type String ����
     */
    /**
     * ������
     * @param id String ID
     * @param name String ����
     * @param value String ֵ
     * @param text String �ı�
     * @param type String ����
     * @param showType String ��ʾ����
     */
    public TTreeNode(String id,String name,String value,String text,String type,String showType)
    {
        setID(id);
        setName(name);
        setText(text);
        setType(type);
        setShowType(showType);
        setValue(value);
        setUserObject(this);
    }
    /**
     * ������
     * @param group String
     */
    public void setGroup(String group)
    {
        this.group = group;
    }
    /**
     * �õ���
     * @return String
     */
    public String getGroup()
    {
        return group;
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
     * ����Ӣ������
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * �õ�Ӣ������
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
     * ������ʾ����
     * @param showType String
     */
    public void setShowType(String showType)
    {
        this.showType = showType;
    }
    /**
     * �õ���ʾ����
     * @return String
     */
    public String getShowType()
    {
        return showType;
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
     * ��������
     * @param font Font
     */
    public void setFont(Font font)
    {
        this.font = font;
    }
    /**
     * �õ�����
     * @return Font
     */
    public Font getFont()
    {
        return font;
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
     * ���õ����˵���ǩ
     * @param popupMenuTag String
     */
    public void setPopupMenuTag(String popupMenuTag)
    {
        this.popupMenuTag = popupMenuTag;
    }
    /**
     * �õ������˵���ǩ
     * @return String
     */
    public String getPopupMenuTag()
    {
        return popupMenuTag;
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
     * ����״̬
     * @param state String
     */
    public void setState(String state)
    {
        this.state = state;
    }
    /**
     * �õ�״̬
     * @return String
     */
    public String getState()
    {
        return state;
    }
    /**
     * �������
     * @param seq int
     */
    public void setSeq(int seq)
    {
        this.seq = seq;
    }
    /**
     * �õ����
     * @return int
     */
    public int getSeq()
    {
        return seq;
    }
    /**
     * ���ұ�ǩ����text
     * @param text String
     * @return TTreeNode
     */
    public TTreeNode findNodeForText(String text)
    {
        if(text == null)
            return null;
        int count = getChildCount();
        if(getText() != null && getText().equalsIgnoreCase(text))
            return this;
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = getChildAt(i);
            if(tnode == null)
                continue;
            if(!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node = (TTreeNode) tnode;
            TTreeNode result = node.findNodeForText(text);
            if(result != null)
                return result;
        }
        return null;
    }
    /**
     * ���ұ�ǩ����value
     * @param value String
     * @return TTreeNode
     */
    public TTreeNode findNodeForValue(String value)
    {
        if(value == null)
            return null;
        int count = getChildCount();
        if(getValue() != null && getValue().equalsIgnoreCase(value))
            return this;
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = getChildAt(i);
            if(tnode == null)
                continue;
            if(!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node = (TTreeNode) tnode;
            TTreeNode result = node.findNodeForValue(value);
            if(result != null)
                return result;
        }
        return null;
    }
    /**
     * ���ұ�ǩ����data
     * @param data Object
     * @return TTreeNode
     */
    public TTreeNode findNodeForData(Object data)
    {
        if(data == null)
            return null;
        if(getData() == data)
            return this;
        int count = getChildCount();
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = getChildAt(i);
            if(tnode == null)
                continue;
            if(!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node = (TTreeNode) tnode;
            TTreeNode result = node.findNodeForData(data);
            if(result != null)
                return result;
        }
        return null;
    }
    /**
     * ���ұ�ǩ����ID
     * @param id String
     * @return TTreeNode
     */
    public TTreeNode findNodeForID(String id)
    {
        if(id == null)
            return null;
        if(id.equals(getID()))
            return this;
        int count = getChildCount();
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = getChildAt(i);
            if(tnode == null)
                continue;
            if(!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node = (TTreeNode) tnode;
            TTreeNode result = node.findNodeForID(id);
            if(result != null)
                return result;
        }
        return null;
    }
    /**
     * ���ұ�ǩ����ID(����һ������)
     * @param id String
     * @return TTreeNode
     */
    public TTreeNode findChildNodeForID(String id)
    {
        if(id == null)
            return null;
        int count = getChildCount();
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = getChildAt(i);
            if (tnode == null)
                continue;
            if (!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node = (TTreeNode) tnode;
            if(id.equals(node.getID()))
                return node;
        }
        return null;
    }
    /**
     * ���ӱ�ǩ(������)
     * @param node TTreeNode
     */
    public void addSeq(TTreeNode node)
    {
        int index = getInsertSeqIndex(node.getSeq());
        if(index < 0)
            add(node);
        else
            insert(node,index);
    }
    /**
     * ���ӱ�ǩ(������)
     * @param node TTreeNode
     */
    public void addSeqText(TTreeNode node)
    {
        int index = getInsertSeqTextIndex(node.getText());
        if(index < 0)
            add(node);
        else
            insert(node,index);
    }
    /**
     * �õ���������λ��
     * @param text String
     * @return int
     */
    private int getInsertSeqTextIndex(String text)
    {
        int count = getChildCount();
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = getChildAt(i);
            if(tnode == null)
                continue;
            if(!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node = (TTreeNode) tnode;
            String t = node.getText();
            if(t.compareTo(text) > 0)
                return i;
        }
        return -1;
    }
    /**
     * �õ���������λ��
     * @param seq int
     * @return int
     */
    private int getInsertSeqIndex(int seq)
    {
        int count = getChildCount();
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = getChildAt(i);
            if(tnode == null)
                continue;
            if(!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node = (TTreeNode) tnode;
            int x = node.getSeq();
            if(x > seq)
                return i;
        }
        return -1;
    }
    /**
     * �����Ƿ��ʼ��չ��
     * @param initExpanded boolean
     */
    public void setInitExpanded(boolean initExpanded)
    {
        this.initExpanded = initExpanded;
    }
    /**
     * �Ƿ��ʼ��չ��
     * @return boolean
     */
    public boolean isInitExpanded()
    {
        return initExpanded;
    }
    /**
     * ת����String
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TTreeNode{id=");
        sb.append(id);
        sb.append(",name=");
        sb.append(name);
        sb.append(",text=");
        sb.append(text);
        sb.append(",type=");
        sb.append(type);
        sb.append(",showType=");
        sb.append(showType);
        sb.append(",value=");
        sb.append(value);
        sb.append(",popupMenuTag=");
        sb.append(popupMenuTag);
        sb.append("}");
        return sb.toString();
    }
}
