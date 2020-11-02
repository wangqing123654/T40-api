package com.dongyang.ui;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Font;
import java.awt.Color;
import javax.swing.tree.TreeNode;

public class TTreeNode extends DefaultMutableTreeNode{
    /**
     * 组
     */
    private String group;
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
     * 英文文字
     */
    private String enText;
    /**
     * 类型
     */
    private String type;
    /**
     * 显示类型
     */
    private String showType;
    /**
     * 值
     */
    private String value;
    /**
     * 状态
     */
    private String state;
    /**
     * 字体
     */
    private Font font;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 弹出菜单标签
     */
    private String popupMenuTag;
    /**
     * 数据对象
     */
    private Object data;
    /**
     * 序号
     */
    private int seq;
    /**
     * 初始化展开
     */
    private boolean initExpanded;
    /**
     * 构造器
     */
    public TTreeNode()
    {
        this("");
    }
    /**
     * 构造器
     * @param text String 文本
     */
    public TTreeNode(String text)
    {
        this(text,"");
    }
    /**
     * 构造器
     * @param text String 文本
     * @param type String 类型
     */
    public TTreeNode(String text,String type)
    {
        this("",text,type,"");
    }
    /**
     *
     * @param value String 值
     * @param text String 文本
     * @param type String 类型
     * @param showType String 显示类型
     */
    public TTreeNode(String value,String text,String type,String showType)
    {
        this("","",value,text,type,showType);
    }
    /**
     * 构造器
     * @param id String ID
     * @param name String 名称
     * @param type String 类型
     */
    /**
     * 构造器
     * @param id String ID
     * @param name String 名称
     * @param value String 值
     * @param text String 文本
     * @param type String 类型
     * @param showType String 显示类型
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
     * 设置组
     * @param group String
     */
    public void setGroup(String group)
    {
        this.group = group;
    }
    /**
     * 得到组
     * @return String
     */
    public String getGroup()
    {
        return group;
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
     * 设置英文文字
     * @param enText String
     */
    public void setEnText(String enText)
    {
        this.enText = enText;
    }
    /**
     * 得到英文文字
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
     * 设置显示类型
     * @param showType String
     */
    public void setShowType(String showType)
    {
        this.showType = showType;
    }
    /**
     * 得到显示类型
     * @return String
     */
    public String getShowType()
    {
        return showType;
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
     * 设置字体
     * @param font Font
     */
    public void setFont(Font font)
    {
        this.font = font;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getFont()
    {
        return font;
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
     * 设置弹出菜单标签
     * @param popupMenuTag String
     */
    public void setPopupMenuTag(String popupMenuTag)
    {
        this.popupMenuTag = popupMenuTag;
    }
    /**
     * 得到弹出菜单标签
     * @return String
     */
    public String getPopupMenuTag()
    {
        return popupMenuTag;
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
     * 设置状态
     * @param state String
     */
    public void setState(String state)
    {
        this.state = state;
    }
    /**
     * 得到状态
     * @return String
     */
    public String getState()
    {
        return state;
    }
    /**
     * 设置序号
     * @param seq int
     */
    public void setSeq(int seq)
    {
        this.seq = seq;
    }
    /**
     * 得到序号
     * @return int
     */
    public int getSeq()
    {
        return seq;
    }
    /**
     * 查找标签根据text
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
     * 查找标签根据value
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
     * 查找标签根据data
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
     * 查找标签根据ID
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
     * 查找标签根据ID(在下一层中找)
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
     * 增加标签(有排序)
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
     * 增加标签(有排序)
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
     * 得到插入的最佳位置
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
     * 得到插入的最佳位置
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
     * 设置是否初始化展开
     * @param initExpanded boolean
     */
    public void setInitExpanded(boolean initExpanded)
    {
        this.initExpanded = initExpanded;
    }
    /**
     * 是否初始化展开
     * @return boolean
     */
    public boolean isInitExpanded()
    {
        return initExpanded;
    }
    /**
     * 转换成String
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
