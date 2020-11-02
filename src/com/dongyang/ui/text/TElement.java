package com.dongyang.ui.text;

import java.util.Map;
import java.util.HashMap;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.util.StringTool;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.FontMetrics;
import java.awt.Font;
import java.util.Vector;
import com.dongyang.config.INode;
import java.util.Iterator;

public class TElement implements IElement {
    /**
     * 调用swing基础方法用
     */
    private static JComponent JComponent = new JButton();
    /**
     * ID
     */
    private String ID;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 显示
     */
    private boolean visible;
    /**
     * 有效
     */
    private boolean enabled;
    /**
     * 文本
     */
    private String text;
    /**
     * 起始页号
     */
    private int startPage;
    /**
     * 结束页号
     */
    private int endPage;
    /**
     * 行号
     */
    private int rowIndex;
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 左边框
     */
    private int borderLeft;
    /**
     * 右边框
     */
    private int borderRight;
    /**
     * 顶边框
     */
    private int borderUp;
    /**
     * 底边框
     */
    private int borderDown;
    /**
     * 父类元素
     */
    private IElement parentElement;

    /**
     * 成员
     */
    private Vector elements;
    /**
     * 对齐方式
     */
    private String align;
    /**
     * 构造器
     */
    public TElement()
    {
        elements = new Vector();
        setID("");
        setName("");
        setType("Element");
        setText("");
        setVisible(true);
        setEnabled(true);
        setAlign("Left");
    }
    /**
     * 设置ID
     * @param ID String
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * 得到ID
     * @return String
     */
    public String getID() {
        return ID;
    }

    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 得到名称
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类型
     * @param type String 类型名称
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 得到类型
     * @return String 类型名称
     */
    public String getType() {
        return type;
    }

    /**
     * 设置显示
     * @param visible boolean
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * 设置有效
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 是否有效
     * @return boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 得到文本
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * 设置起始页号
     * @param page int
     */
    public void setStartPage(int page) {
        this.startPage = page;
    }

    /**
     * 得到起始页号
     * @return int
     */
    public int getStartPage() {
        return startPage;
    }

    /**
     * 设置结束页号
     * @param page int
     */
    public void setEndPage(int page) {
        this.endPage = page;
    }

    /**
     * 得到结束页号
     * @return int
     */
    public int getEndPage() {
        return endPage;
    }
    /**
     * 设置行号
     * @param rowIndex int
     */
    public void setRowIndex(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }
    /**
     * 得到行号
     * @return int
     */
    public int getRowIndex()
    {
        return rowIndex;
    }
    /**
     * 设置x坐标
     * @param x int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 得到x坐标
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * 设置y坐标
     * @param y int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * 得到y坐标
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 得到宽度
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 得到高度
     * @return int
     */
    public int getHeight() {
        return height;
    }
    /**
     * 得到内部宽度
     * @return int
     */
    public int getInsideWidth()
    {
        return getWidth() - getBorderLeft() - getBorderRight();
    }
    /**
     * 得到内部高度
     * @return int
     */
    public int getInsideHeight()
    {
        return getHeight() - getBorderUp() - getBorderDown();
    }
    /**
     * 设置左边框
     * @param borderLeft int
     */
    public void setBorderLeft(int borderLeft) {
        this.borderLeft = borderLeft;
    }

    /**
     * 得到左边框
     * @return int
     */
    public int getBorderLeft() {
        return borderLeft;
    }

    /**
     * 设置右边框
     * @param borderRight int
     */
    public void setBorderRight(int borderRight) {
        this.borderRight = borderRight;
    }

    /**
     * 得到右边框
     * @return int
     */
    public int getBorderRight() {
        return borderRight;
    }

    /**
     * 设置顶边框
     * @param borderUp int
     */
    public void setBorderUp(int borderUp) {
        this.borderUp = borderUp;
    }

    /**
     * 得到顶边框
     * @return int
     */
    public int getBorderUp() {
        return borderUp;
    }

    /**
     * 设置底边框
     * @param borderDown int
     */
    public void setBorderDown(int borderDown) {
        this.borderDown = borderDown;
    }

    /**
     * 得到底边框
     * @return int
     */
    public int getBorderDown() {
        return borderDown;
    }

    /**
     * 设置父类元素
     * @param element IElement
     */
    public void setParentElement(IElement element) {
        parentElement = element;
    }

    /**
     * 得到父类元素
     * @return IElement
     */
    public IElement getParentElement() {
        return parentElement;
    }
    /**
     * 增加成员
     * @param element IElement
     */
    public void addElement(IElement element)
    {
        elements.add(element);
    }
    /**
     * 增加成员
     * @param index int 位置
     * @param element IElement
     */
    public void addElement(int index,IElement element)
    {
        elements.add(index,element);
    }
    /**
     * 删除成员
     * @param index int 位置
     * @return IElement
     */
    public IElement removeElement(int index)
    {
        return (IElement)elements.remove(index);
    }
    /**
     * 删除成员
     * @param element IElement
     * @return boolean
     */
    public boolean removeElement(IElement element)
    {
        return elements.remove(element);
    }
    /**
     * 查找成员
     * @param element IElement
     * @return int
     */
    public int elementIndexOf(IElement element)
    {
        return elements.indexOf(element);
    }
    /**
     * 查找成员
     * @param element IElement
     * @param index int 开始位置
     * @return int
     */
    public int elementIndexOf(IElement element,int index)
    {
        return elements.indexOf(element,index);
    }
    /**
     * 成员个数
     * @return int
     */
    public int elementSize()
    {
        return elements.size();
    }
    /**
     * 得到成员
     * @param index int
     * @return IElement
     */
    public IElement getElement(int index)
    {
        return (IElement)elements.get(index);
    }
    /**
     * 设置对齐方式
     * @param align String
     */
    public void setAlign(String align)
    {
        this.align = align;
    }
    /**
     * 得到对齐方式
     * @return String
     */
    public String getAlign()
    {
        return align;
    }
    /**
     * 绘制图像
     * @param g Graphics
     * @param pageIndex int
     */
    public void paint(Graphics g, int pageIndex)
    {

    }

    /**
     * 导出Map
     * @param map Map
     */
    public void getMapValue(Map map) {
        if (map == null)
            return;
        map.put("id", getID());
        map.put("name", getName());
        map.put("type", getType());
        map.put("visible", isVisible());
        map.put("enabled", isEnabled());
        map.put("text", getText());
        map.put("startPage", getStartPage());
        map.put("endPage", getEndPage());
        map.put("rowIndex",getRowIndex());
        map.put("x", getX());
        map.put("y", getY());
        map.put("width", getWidth());
        map.put("height", getHeight());
        map.put("borderLeft", getBorderLeft());
        map.put("borderRight", getBorderRight());
        map.put("borderUp", getBorderUp());
        map.put("borderDown", getBorderDown());
        map.put("align",getAlign());
    }

    /**
     * 装载Map
     * @param map Map
     */
    public void setMapValue(Map map) {
        if (map == null)
            return;
        setID(TCM_Transform.getString(map.get("id")));
        setName(TCM_Transform.getString(map.get("name")));
        setType(TCM_Transform.getString(map.get("type")));
        setVisible(TCM_Transform.getBoolean(map.get("visible")));
        setEnabled(TCM_Transform.getBoolean(map.get("enabled")));
        setText(TCM_Transform.getString(map.get("text")));
        setStartPage(TCM_Transform.getInt(map.get("startPage")));
        setEndPage(TCM_Transform.getInt(map.get("endPage")));
        setX(TCM_Transform.getInt(map.get("x")));
        setY(TCM_Transform.getInt(map.get("y")));
        setWidth(TCM_Transform.getInt(map.get("width")));
        setHeight(TCM_Transform.getInt(map.get("height")));
        setBorderLeft(TCM_Transform.getInt(map.get("borderLeft")));
        setBorderRight(TCM_Transform.getInt(map.get("borderRight")));
        setBorderUp(TCM_Transform.getInt(map.get("borderUp")));
        setBorderDown(TCM_Transform.getInt(map.get("borderDown")));
        setRowIndex(TCM_Transform.getInt(map.get("RowIndex")));
        setAlign(TCM_Transform.getString(map.get("align")));
    }

    /**
     * 消息处理
     * @param message String
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * 消息处理
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        if ("setID".equalsIgnoreCase(value[0])) {
            setID(value[1]);
            return "OK";
        }
        if ("getID".equalsIgnoreCase(value[0])) {
            return getID();
        }
        if ("setType".equalsIgnoreCase(value[0])) {
            setType(value[1]);
            return "OK";
        }
        if ("getType".equalsIgnoreCase(value[0])) {
            return getType();
        }
        if ("setName".equalsIgnoreCase(value[0])) {
            setName(value[1]);
            return "OK";
        }
        if ("getName".equalsIgnoreCase(value[0])) {
            return getName();
        }
        if ("setVisible".equalsIgnoreCase(value[0])) {
            setVisible(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if ("isVisible".equalsIgnoreCase(value[0])) {
            return isVisible();
        }
        if ("setEnabled".equalsIgnoreCase(value[0])) {
            setEnabled(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if ("isEnabled".equalsIgnoreCase(value[0])) {
            return isEnabled();
        }
        if ("setText".equalsIgnoreCase(value[0])) {
            setText(value[1]);
            return "OK";
        }
        if ("getText".equalsIgnoreCase(value[0])) {
            return getText();
        }
        if ("setStartPage".equalsIgnoreCase(value[0])) {
            setStartPage(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getStartPage".equalsIgnoreCase(value[0])) {
            return getStartPage();
        }
        if ("setEndPage".equalsIgnoreCase(value[0])) {
            setEndPage(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getEndPage".equalsIgnoreCase(value[0])) {
            return getEndPage();
        }
        if ("setX".equalsIgnoreCase(value[0])) {
            setX(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getX".equalsIgnoreCase(value[0])) {
            return getX();
        }
        if ("setY".equalsIgnoreCase(value[0])) {
            setY(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getY".equalsIgnoreCase(value[0])) {
            return getY();
        }
        if ("setWidth".equalsIgnoreCase(value[0])) {
            setWidth(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getWidth".equalsIgnoreCase(value[0])) {
            return getWidth();
        }
        if ("setHeight".equalsIgnoreCase(value[0])) {
            setHeight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getHeight".equalsIgnoreCase(value[0])) {
            return getHeight();
        }
        if ("setBorderLeft".equalsIgnoreCase(value[0])) {
            setBorderLeft(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getBorderLeft".equalsIgnoreCase(value[0])) {
            return getBorderLeft();
        }
        if ("setBorderRight".equalsIgnoreCase(value[0])) {
            setBorderRight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getBorderRight".equalsIgnoreCase(value[0])) {
            return getBorderRight();
        }
        if ("setBorderUp".equalsIgnoreCase(value[0])) {
            setBorderUp(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getBorderUp".equalsIgnoreCase(value[0])) {
            return getBorderUp();
        }
        if ("setBorderDown".equalsIgnoreCase(value[0])) {
            setBorderDown(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getBorderDown".equalsIgnoreCase(value[0])) {
            return getBorderDown();
        }
        if ("setAlign".equalsIgnoreCase(value[0]))
        {
            setAlign(value[1]);
            return "OK";
        }
        if ("getAlign".equalsIgnoreCase(value[0]))
        {
            return getAlign();
        }
        value = StringTool.getHead(message, "=");
        if ("id".equalsIgnoreCase(value[0])) {
            setID(value[1]);
            return "OK";
        }
        if ("name".equalsIgnoreCase(value[0])) {
            setName(value[1]);
            return "OK";
        }
        if ("type".equalsIgnoreCase(value[0])) {
            setType(value[1]);
            return "OK";
        }
        if ("visible".equalsIgnoreCase(value[0])) {
            setVisible(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if ("enabled".equalsIgnoreCase(value[0])) {
            setVisible(StringTool.getBoolean(value[1]));
            return "OK";
        }
        if ("text".equalsIgnoreCase(value[0])) {
            setText(value[1]);
            return "OK";
        }
        if ("startPage".equalsIgnoreCase(value[0])) {
            setStartPage(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("endPage".equalsIgnoreCase(value[0])) {
            setEndPage(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("x".equalsIgnoreCase(value[0])) {
            setX(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("y".equalsIgnoreCase(value[0])) {
            setY(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("width".equalsIgnoreCase(value[0])) {
            setWidth(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("height".equalsIgnoreCase(value[0])) {
            setHeight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("borderLeft".equalsIgnoreCase(value[0])) {
            setBorderLeft(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("borderRight".equalsIgnoreCase(value[0])) {
            setBorderRight(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("borderUp".equalsIgnoreCase(value[0])) {
            setBorderUp(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("borderDown".equalsIgnoreCase(value[0])) {
            setBorderDown(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("align".equalsIgnoreCase(value[0]))
        {
            setAlign(value[1]);
            return "OK";
        }
        return null;
    }

    /**
     * 转换成字符串
     * @return String
     */
    public String toString() {
        Map map = new HashMap();
        getMapValue(map);
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName() + ":" +
                  Integer.toHexString(hashCode()).toUpperCase() + map.toString());
        sb.append("\n");
        for(int i = 0; i < elementSize();i++)
            sb.append(getElement(i));
        return sb.toString();
    }
    /**
     * 得到字体尺寸处理对象
     * @param font Font
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics(Font font)
    {
        return JComponent.getFontMetrics(font);
    }
    /**
     * 加载xml
     * @param node INode
     */
    public void read(INode node)
    {
        //加载属性
        readAttribute(node);
        //加载子成员
        int count = node.size();
        for(int i = 0; i < count;i++)
        {
            INode childNode = (INode)node.getChildElement(i);
            IElement element = TLoad.read(childNode);
            if(element == null)
                continue;
            element.setParentElement(this);
            addElement(element);
        }
    }
    /**
     * 加载属性
     * @param node INode
     */
    public void readAttribute(INode node)
    {
        String value = node.getValue();

        callMessage("text='" + StringTool.sign(value) + "'");
        Iterator iterator = node.getAttributeNames();
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            value = (String)node.getAttributeValue(name);
            callMessage(name + "=" + value);
        }
    }
    /**
     * 得到下一个组件
     * @return IElement
     */
    public IElement getNextElement()
    {
        if(getParentElement() == null)
            return null;
        int index = getIndex();
        if(index == -1)
            return null;
        if(index == getParentElement().elementSize() - 1)
            return null;
        return getParentElement().getElement(index + 1);
    }
    /**
     * 得到前一个组件
     * @return IElement
     */
    public IElement getPreviousElement()
    {
        if(getParentElement() == null)
            return null;
        int index = getIndex();
        if(index == -1)
            return null;
        if(index == 0)
            return null;
        return getParentElement().getElement(index - 1);
    }
    /**
     * 得到自己在父类中的位置
     * @return int
     */
    public int getIndex()
    {
        if(getParentElement() == null)
            return -1;
        return getParentElement().elementIndexOf(this);
    }
    /**
     * 得到行的最大高度
     * @param row int
     * @return int
     */
    public int getRowMaxHeight(int row)
    {
        int count = elementSize();
        int height = 0;
        boolean b = false;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            if(element.getRowIndex() != row)
                if(b)
                    return height;
                else
                    continue;
            b = true;
            int h = element.getHeight();
            if(h > height)
                height = h;
        }
        return height;
    }
    /**
     * 得到总行数
     * @return int
     */
    public int getRowSize()
    {
        int index = -1;
        int count = elementSize();
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            if(element.getRowIndex() > index)
                index = element.getRowIndex();
        }
        return index + 1;
    }
    /**
     * 生成XML属性
     * @param nameList String 属性列表用逗号分割
     * @param index int
     * @return String
     */
    public String write(String nameList,int index)
    {
        Map map = new HashMap();
        getMapValue(map);
        String names[] = StringTool.parseLine(nameList,",");
        StringBuffer sb = new StringBuffer();
        if(index > 0)
            sb.append(StringTool.fill("\t",index));
        sb.append("<");
        sb.append(getType());

        StringBuffer attributeValue = new StringBuffer();
        for(int i = 0; i < names.length;i++)
            attributeValue.append(getXMLAttributeValue(map,names[i]));
        if(attributeValue.length() > 0)
        {
            sb.append(" ");
            sb.append(attributeValue);
        }
        String childValue = writeChild(index);
        if(childValue.length() > 0)
        {
            sb.append(">\n");
            sb.append(childValue);
            sb.append(StringTool.fill("\t",index));
            sb.append("</");
        }
        sb.append(getType());
        sb.append(">\n");
        return sb.toString();
    }
    /**
     * 生成子成员XML
     * @param index int
     * @return String
     */
    public String writeChild(int index)
    {
        StringBuffer sb = new StringBuffer();
        int count = elementSize();
        if(count == 0)
        {
            if (getText() == null || getText().length() == 0)
                return "";
            if (index > 0)
                sb.append(StringTool.fill("\t", index + 1));
            sb.append(StringTool.setSign(getText()));
            sb.append('\n');
            return sb.toString();
        }
        if(index > -1)
            index ++;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            sb.append(element.write(index));
        }
        return sb.toString();
    }
    /**
     * 生成XML
     * @param index int
     * @return String
     */
    public String write(int index)
    {
        return write(getXMLAttributeList(),index);
    }
    /**
     * 得到XML属性列表
     * @return String
     */
    public String getXMLAttributeList()
    {
        return "ID,name,visible,enabled,startPage,endPage,x,y,width,height,borderLeft,borderRight,borderUp,borderDown,align";
    }
    /**
     * 创建XML属性值
     * @param map Map
     * @param name String
     * @return String
     */
    public String getXMLAttributeValue(Map map,String name)
    {
        if("ID".equals(name) && (getID() == null || getID().trim().length() == 0))
            return "";
        if("name".equals(name) && (getName() == null || getName().trim().length() == 0))
            return "";
        if("visible".equals(name) && isVisible())
            return "";
        if("enabled".equals(name) && isEnabled())
            return "";
        if("startPage".equals(name) && getStartPage() == 0)
            return "";
        if("endPage".equals(name) && getEndPage() == 0)
            return "";
        if("x".equals(name) && getX() == 0)
            return "";
        if("y".equals(name) && getY() == 0)
            return "";
        if("width".equals(name) && getWidth() == 0)
            return "";
        if("height".equals(name) && getHeight() == 0)
            return "";
        if("borderLeft".equals(name) && getBorderLeft() == 0)
            return "";
        if("borderRight".equals(name) && getBorderRight() == 0)
            return "";
        if("borderUp".equals(name) && getBorderUp() == 0)
            return "";
        if("borderDown".equals(name) && getBorderDown() == 0)
            return "";
        if("align".equals(name) && (getAlign() == null || getAlign().trim().length() == 0 || "Left".equals(getAlign())))
            return "";
        StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append("='");
        sb.append(map.get(name));
        sb.append("' ");
        return sb.toString();
    }
    /**
     * 调整最佳尺寸
     */
    public void fine()
    {
    }
}
