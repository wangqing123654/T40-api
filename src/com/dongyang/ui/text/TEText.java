package com.dongyang.ui.text;

import java.awt.Font;
import java.util.Map;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.util.StringTool;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.FontMetrics;
import java.util.HashMap;
import java.util.Vector;

public class TEText extends TElement{
    /**
     * 字体对象
     */
    private Font font;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 构造器
     */
    public TEText()
    {
        setType("Text");
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
        if(font == null)
            font = new Font("宋体", 0, 12);
        return font;
    }
    /**
     * 设置字体名称
     * @param name String
     */
    public void setFontName(String name)
    {
        if(getFontName().equals(name))
            return;
        setFont(new Font(name, font.getStyle(), font.getSize()));
    }
    /**
     * 得到字体名称
     * @return String
     */
    public String getFontName()
    {
        return getFont().getName();
    }
    /**
     * 设置字体类型
     * @param sytle int
     */
    public void setFontStyle(int sytle)
    {
        if(getFontStyle() == sytle)
            return;
        setFont(new Font(font.getName(),sytle, font.getSize()));
    }
    /**
     * 得到字体类型
     * @return int
     */
    public int getFontStyle()
    {
        return getFont().getStyle();
    }
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        if(getFontSize() == size)
            return;
        setFont(new Font(font.getName(),font.getStyle(),size));
    }
    /**
     * 得到字体尺寸
     * @return int
     */
    public int getFontSize()
    {
        return getFont().getSize();
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
        if(color == null)
            color = new Color(0,0,0);
        return color;
    }
    /**
     * 设置颜色
     * @param color String
     */
    public void setColorString(String color)
    {
        setColor(StringTool.getColor(color));
    }
    /**
     * 得到颜色
     * @return String
     */
    public String getColorString()
    {
        return getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue();
    }
    /**
     * 绘制图像
     * @param g Graphics
     * @param pageIndex int
     */
    public void paint(Graphics g, int pageIndex)
    {
        Rectangle r = g.getClipBounds();
        if(r.getWidth() < 0 || r.getHeight() < 0)
            return;
        g.setColor(new Color(0,0,0));
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        if(paintElement(g,pageIndex))
            return;
        //Rectangle r = g.getClipBounds();
        //System.out.println("text=" + r);
        int w = getFontMetrics().stringWidth(getText());
        int h = getFontMetrics().getHeight();
        int y = getFontMetrics().getAscent();
        g.setColor(getColor());
        g.setFont(getFont());
        g.drawRect(getBorderLeft(),getBorderUp(),w,h);
        g.drawString(getText(),getBorderLeft(),getBorderUp() + y);
        if(getText().length() > 0 && getText().charAt(getText().length() - 1) == '\n')
            drawEnterFlg(g,getBorderLeft() + w,getBorderUp() + y);
    }
    public void drawEnterFlg(Graphics g,int x,int y)
    {
        y -= 2;
        g.setColor(new Color(0,0,0));
        g.drawLine(x,y,x + 5,y);
        g.drawLine(x + 1,y - 1,x + 1,y + 1);
        g.drawLine(x + 2,y - 2,x + 2,y + 2);
        g.drawLine(x + 6,y - 6,x + 6,y - 1);
    }
    public void setY(int y)
    {
        if(getY() != y)
            super.setY(y);
        if(getParentElement() == null)
            return;
        if(getRowIndex() != getParentElement().getRowSize() - 1)
            return;
        int h = getParentElement().getRowMaxHeight(getRowIndex()) + getY();
        if(h > getParentElement().getInsideHeight())
            getParentElement().setHeight(h + getParentElement().getBorderUp() + getParentElement().getBorderDown());
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        if(getHeight() != height)
            super.setHeight(height);
        if(getParentElement() == null)
            return;
        if(getRowIndex() != getParentElement().getRowSize() - 1)
            return;
        int h = getParentElement().getRowMaxHeight(getRowIndex()) + getY();
        if(h > getParentElement().getInsideHeight())
            getParentElement().setHeight(h + getParentElement().getBorderUp() + getParentElement().getBorderDown());
    }
    /**
     * 绘制组件
     * @param g Graphics
     * @param pageIndex int
     * @return boolean
     */
    public boolean paintElement(Graphics g, int pageIndex)
    {
        int count = elementSize();
        if(count == 0)
            return false;
        for(int i = 0;i < count;i++)
        {
            IElement element = getElement(i);
            Graphics g1 = getElementGraphics(element,g,pageIndex);
            //组件不可视
            if(!element.isVisible())
                continue;
            //组建不在页内
            if(pageIndex < element.getStartPage() || pageIndex > element.getEndPage())
                continue;
            if(g1 == null)
                continue;
            element.paint(g1,pageIndex);
        }
        return true;
    }
    /**
     * 得到绘图设备
     * @param element IElement
     * @param g Graphics
     * @param pageIndex int
     * @return Graphics
     */
    public Graphics getElementGraphics(IElement element,Graphics g,int pageIndex)
    {
        Rectangle r = g.getClipBounds();
        if(element.getX() + getBorderLeft() > r.getX() + r.getWidth()||
           element.getY() + getBorderUp() > r.getY() + r.getHeight()||
           element.getX() + getBorderLeft() + element.getWidth() < r.getX()||
           element.getY() + getBorderUp() + element.getHeight() < r.getY())
            return null;
        Graphics g1 = g.create(element.getX() + getBorderLeft(),
                               element.getY() + getBorderUp(),
                               element.getWidth(),
                               element.getHeight());
        return g1;
    }
    /**
     * 调整最佳尺寸
     */
    public void fine()
    {
        fineInit();
        if(fineElement())
            return;
        fineText();
    }
    /**
     * 调整最佳尺寸初始化
     */
    public void fineInit()
    {
        if(getParentElement() == null)
            return;
        //如果是第一个元素
        if(getParentElement().elementIndexOf(this) == 0)
        {
            setStartPage(getParentElement().getStartPage());
            setRowIndex(0);
            setX(0);
            setY(0);
            return;
        }
        //不是第一个元素
        IElement element = getPreviousElement();
        if(element.getText().length() > 0 &&
           element.getText().charAt(element.getText().length() - 1) == '\n')
        {
            setRowIndex(element.getRowIndex() + 1);
            setX(0);
            setY(getParentElement().getRowMaxHeight(element.getRowIndex()) + element.getY());
            return;
        }
        setRowIndex(element.getRowIndex());
        setX(element.getX() + element.getWidth());
        setY(element.getY());
    }
    /**
     * 得到合适宽度的字节位置
     * @param width int
     * @return int
     */
    public int fineChar(int width)
    {
        if(width < 0)
            return 0;
        int w = 0;
        String text = getText();
        for(int i = 0; i < text.length();i++)
        {
            char c = text.charAt(i);
            if(c == '\n')
                return i + 1;
            w += getFontMetrics().stringWidth("" + c);
            if(w > width)
                return i;
        }
        return text.length();
    }
    public void fineText()
    {
        //设置高度
        setHeight(getFontMetrics().getHeight() + getBorderUp() + getBorderDown());
        int width = getInsideWidth();
        if(getParentElement() != null)
            width = getParentElement().getInsideWidth() - getX() - getBorderLeft() - getBorderRight();
        int index = fineChar(width);
        //一个字母也放不下
        if(index == 0)
        {
            if(getParentElement() == null)
                return;
            IElement element = getPreviousElement();
            setRowIndex(element.getRowIndex() + 1);
            setX(0);
            setY(getParentElement().getRowMaxHeight(element.getRowIndex()) + element.getY());
            width = getParentElement().getInsideWidth() - getX() - getBorderLeft() - getBorderRight();
            index = fineChar(width);
            if(index == 0)
                return;
        }
        //本行能放下
        if(index == getText().length())
        {
            setWidth(getFontMetrics().stringWidth(getText()) + getBorderLeft() + getBorderRight());
            //处理下一个组件
            finaNextElement();
            return;
        }
        String text = getText().substring(index,getText().length());
        setText(getText().substring(0,index));
        setWidth(getFontMetrics().stringWidth(getText()) + getBorderLeft() + getBorderRight());
        if(getParentElement() == null)
            return;
        //克隆新行
        IElement element = clone();
        //插入父类自己组件的后面
        getParentElement().addElement(getIndex() + 1,element);
        element.setRowIndex(getRowIndex() + 1);
        element.setText(text);
        element.fine();
    }
    /**
     * 处理下一个组件
     */
    public void finaNextElement()
    {
        IElement element = getNextElement();
        if(element != null)
            element.fine();
    }
    public IElement clone()
    {
        IElement element = new TEText();
        Map map = new HashMap();
        getMapValue(map);
        element.setMapValue(map);
        element.setParentElement(getParentElement());
        return element;
    }
    /**
     * 调整最佳尺寸
     * @return boolean
     */
    public boolean fineElement()
    {
        int count = elementSize();
        if(count == 0)
            return false;
        getElement(0).fine();
        fineAlign();
        return true;
    }
    /**
     * 调整对齐位置
     */
    public void fineAlign()
    {
        if("Left".equalsIgnoreCase(getAlign()))
            return;
        Vector data = getElementVector();
        if(data.size() == 0)
            return;
        for(int rowIndex = 0;rowIndex < data.size();rowIndex ++)
        {
            Vector row = (Vector)data.get(rowIndex);
            IElement lastElement = (IElement)row.get(row.size() - 1);
            int x = getInsideWidth() - lastElement.getX() - lastElement.getWidth();
            if("Center".equalsIgnoreCase(getAlign()))
                x = (int)((double)x/2.0);
            for(int columnIndex = 0;columnIndex < row.size();columnIndex ++)
            {
                IElement element = (IElement)row.get(columnIndex);
                element.setX(element.getX() + x);
            }

        }
    }
    /**
     * 得到组件矩阵
     * @return Vector
     */
    public Vector getElementVector()
    {
        int count = elementSize();
        Vector data = new Vector();
        if(count == 0)
            return data;
        Vector rowData = new Vector();
        int row = 0;
        for(int i = 0; i < count;i++)
        {
            IElement element = getElement(i);
            if(row != element.getRowIndex())
            {
                data.add(rowData);
                rowData = new Vector();
                row = element.getRowIndex();
            }
            rowData.add(element);
        }
        data.add(rowData);
        return data;
    }
    /**
     * 导出Map
     * @param map Map
     */
    public void getMapValue(Map map)
    {
        if(map == null)
            return;
        super.getMapValue(map);
        map.put("fontName",getFontName());
        map.put("fontStyle",getFontStyle());
        map.put("fontSize",getFontSize());
        map.put("color",getColorString());
    }
    /**
     * 装载Map
     * @param map Map
     */
    public void setMapValue(Map map)
    {
        if(map == null)
            return;
        super.setMapValue(map);
        setFontName(TCM_Transform.getString(map.get("fontName")));
        setFontStyle(TCM_Transform.getInt(map.get("fontStyle")));
        setFontSize(TCM_Transform.getInt(map.get("fontSize")));
        setColorString(TCM_Transform.getString(map.get("color")));
    }
    /**
     * 消息处理
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message,Object parm)
    {
        super.callMessage(message,parm);
        if (message == null)
            return null;
        String value[] = StringTool.getHead(message, "|");
        if ("setFontName".equalsIgnoreCase(value[0])) {
            setFontName(value[1]);
            return "OK";
        }
        if ("getFontName".equalsIgnoreCase(value[0])) {
            return getFontName();
        }
        if ("setFontStyle".equalsIgnoreCase(value[0])) {
            setFontStyle(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getFontStyle".equalsIgnoreCase(value[0])) {
            return getFontStyle();
        }
        if ("setFontSize".equalsIgnoreCase(value[0])) {
            setFontSize(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("getFontSize".equalsIgnoreCase(value[0])) {
            return getFontSize();
        }
        if("setColor".equalsIgnoreCase(value[0]))
        {
            if(parm != null)
                setColor((Color)parm);
            if(value.length == 2)
                setColorString(value[1]);
            return "OK";
        }
        if("getColor".equalsIgnoreCase(value[0]))
        {
            return getColor();
        }
        if("setColorString".equalsIgnoreCase(value[0]))
        {
            setColorString(value[1]);
            return "OK";
        }
        if("getColorString".equalsIgnoreCase(value[0]))
        {
            return getColorString();
        }
        value = StringTool.getHead(message, "=");
        if ("fontName".equalsIgnoreCase(value[0])) {
            setFontName(value[1]);
            return "OK";
        }
        if ("fontStyle".equalsIgnoreCase(value[0])) {
            setFontStyle(StringTool.getInt(value[1]));
            return "OK";
        }
        if ("fontSize".equalsIgnoreCase(value[0])) {
            setFontSize(StringTool.getInt(value[1]));
            return "OK";
        }
        if("color".equalsIgnoreCase(value[0]))
        {
            setColorString(value[1]);
            return "OK";
        }
        return null;
    }
    /**
     * 得到字体尺寸处理对象
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics()
    {
        return super.getFontMetrics(getFont());
    }
    /**
     * 得到XML属性列表
     * @return String
     */
    public String getXMLAttributeList()
    {
        return super.getXMLAttributeList() + ",fontName,fontStyle,fontSize,color";
    }
    /**
     * 创建XML属性值
     * @param map Map
     * @param name String
     * @return String
     */
    public String getXMLAttributeValue(Map map,String name)
    {
        if("fontName".equals(name) && (getFontName() == null || getFontName().trim().length() == 0 || "宋体".equals(getFontName())))
            return "";
        if("fontStyle".equals(name) && getFontStyle() == 0)
            return "";
        if("fontSize".equals(name) && getFontSize() == 12)
            return "";
        if("color".equals(name) && (getColorString() == null || getColorString().trim().length() == 0 || "0,0,0".equals(getColorString())))
            return "";
        return super.getXMLAttributeValue(map,name);
    }
}
