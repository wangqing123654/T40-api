package com.dongyang.ui.text;

import java.util.Map;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import com.dongyang.config.INode;

public interface IElement {
    /**
     * 设置ID
     * @param ID String
     */
    public void setID(String ID);
    /**
     * 得到ID
     * @return String
     */
    public String getID();
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name);
    /**
     * 得到名称
     * @return String
     */
    public String getName();
    /**
     * 设置类型
     * @param type String 类型名称
     */
    public void setType(String type);
    /**
     * 得到类型
     * @return String 类型名称
     */
    public String getType();
    /**
     * 设置显示
     * @param visible boolean
     */
    public void setVisible(boolean visible);
    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible();
    /**
     * 设置有效
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled);
    /**
     * 是否有效
     * @return boolean
     */
    public boolean isEnabled();
    /**
     * 设置父类元素
     * @param element IElement
     */
    public void setParentElement(IElement element);
    /**
     * 得到父类元素
     * @return IElement
     */
    public IElement getParentElement();
    /**
      * 设置文本
      * @param text String
      */
     public void setText(String text);
     /**
      * 得到文本
      * @return String
      */
     public String getText();
     /**
      * 设置起始页号
      * @param page int
      */
     public void setStartPage(int page);
     /**
      * 得到起始页号
      * @return int
      */
     public int getStartPage();
     /**
      * 设置结束页号
      * @param page int
      */
     public void setEndPage(int page);
     /**
      * 得到结束页号
      * @return int
      */
     public int getEndPage();
     /**
      * 设置行号
      * @param rowIndex int
      */
     public void setRowIndex(int rowIndex);
     /**
      * 得到行号
      * @return int
      */
     public int getRowIndex();
     /**
      * 设置x坐标
      * @param x int
      */
     public void setX(int x);
     /**
      * 得到x坐标
      * @return int
      */
     public int getX();
     /**
      * 设置y坐标
      * @param y int
      */
     public void setY(int y);
     /**
      * 得到y坐标
      * @return int
      */
     public int getY();
     /**
      * 设置宽度
      * @param width int
      */
     public void setWidth(int width);
     /**
      * 得到宽度
      * @return int
      */
     public int getWidth();
     /**
      * 设置高度
      * @param height int
      */
     public void setHeight(int height);
     /**
      * 得到高度
      * @return int
      */
     public int getHeight();
     /**
      * 得到内部宽度
      * @return int
      */
     public int getInsideWidth();
     /**
      * 得到内部高度
      * @return int
      */
     public int getInsideHeight();
     /**
      * 设置左边框
      * @param borderLeft int
      */
     public void setBorderLeft(int borderLeft);
     /**
      * 得到左边框
      * @return int
      */
     public int getBorderLeft();
     /**
      * 设置右边框
      * @param borderRight int
      */
     public void setBorderRight(int borderRight);
     /**
      * 得到右边框
      * @return int
      */
     public int getBorderRight();
     /**
      * 设置顶边框
      * @param borderUp int
      */
     public void setBorderUp(int borderUp);
     /**
      * 得到顶边框
      * @return int
      */
     public int getBorderUp();
     /**
      * 设置底边框
      * @param borderDown int
      */
     public void setBorderDown(int borderDown);
     /**
      * 得到底边框
      * @return int
      */
     public int getBorderDown();
     /**
     * 装载Map
     * @param map Map
     */
    public void setMapValue(Map map);
    /**
     * 导出Map
     * @param map Map
     */
    public void getMapValue(Map map);
    /**
     * 增加成员
     * @param element IElement
     */
    public void addElement(IElement element);
    /**
     * 增加成员
     * @param index int 位置
     * @param element IElement
     */
    public void addElement(int index,IElement element);
    /**
     * 删除成员
     * @param index int 位置
     * @return IElement
     */
    public IElement removeElement(int index);
    /**
     * 删除成员
     * @param element IElement
     * @return boolean
     */
    public boolean removeElement(IElement element);
    /**
     * 查找成员
     * @param element IElement
     * @return int
     */
    public int elementIndexOf(IElement element);
    /**
     * 查找成员
     * @param element IElement
     * @param index int 开始位置
     * @return int
     */
    public int elementIndexOf(IElement element,int index);
    /**
     * 成员个数
     * @return int
     */
    public int elementSize();
    /**
     * 得到成员
     * @param index int
     * @return IElement
     */
    public IElement getElement(int index);
    /**
     * 设置对齐方式
     * @param align String
     */
    public void setAlign(String align);
    /**
     * 得到对齐方式
     * @return String
     */
    public String getAlign();
    /**
     * 消息处理
     * @param message String
     * @return Object
     */
    public Object callMessage(String message);
    /**
     * 消息处理
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message,Object parm);
    /**
     * 绘制图像
     * @param g Graphics
     * @param pageIndex int
     */
    public void paint(Graphics g, int pageIndex);
    /**
     * 得到字体尺寸处理对象
     * @param font Font
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics(Font font);
    /**
     * 加载xml
     * @param node INode
     */
    public void read(INode node);
    /**
     * 加载属性
     * @param node INode
     */
    public void readAttribute(INode node);
    /**
     * 得到下一个组件
     * @return IElement
     */
    public IElement getNextElement();
    /**
     * 得到前一个组件
     * @return IElement
     */
    public IElement getPreviousElement();
    /**
     * 得到自己在父类中的位置
     * @return int
     */
    public int getIndex();
    /**
     * 得到行的最大高度
     * @param row int
     * @return int
     */
    public int getRowMaxHeight(int row);
    /**
     * 得到总行数
     * @return int
     */
    public int getRowSize();
    /**
     * 调整最佳尺寸
     */
    public void fine();
    /**
     * 生成XML
     * @param index int
     * @return String
     */
    public String write(int index);
}
