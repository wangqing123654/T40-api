package com.dongyang.tui.text.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
import java.awt.Stroke;
import javax.swing.Icon;
import com.dongyang.manager.TIOM_FileServer;
import com.dongyang.util.StringTool;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.util.Vector;

public class GBlockBase
{
    /**
     * 显示
     */
    private boolean visible = true;
    /**
     * 名称
     */
    private String name;
    private int x;
    private int y;
    private int width;
    private int height;
    /**
     * 颜色
     */
    private Color color = new Color(0,0,0);
    /**
     * 背景颜色
     */
    private Color bkColor;
    /**
     * 边框颜色
     */
    private Color borderColor = new Color(0,0,0);
    /**
     * 字体
     */
    private Font font = new Font("宋体",0,10);
    /**
     * 旋转角度
     */
    private double fontRotate = 0.0;
    /**
     * 位置
     * 0 居左
     * 1 居中
     * 2 居右
     */
    private int alignment = 1;
    /**
     * 纵向位置
     * 0 居上
     * 1 居中
     * 2 居下
     */
    private int alignmentH = 1;
    /**
     * 下划线
     */
    private boolean isLine;
    /**
     * 自动折行高度
     */
    private int autoEnterHeight = 15;
    /**
     * 显示边框
     */
    private boolean borderVisible = true;
    /**
     * 边框线高
     */
    private float borderWidth = 1.0f;
    /**
     * 边框线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int borderLineType = 0;
    /**
     * 图片名称
     */
    private String pictureName;
    private Icon icon;
    private boolean iconInit;
    /**
     * 自动拉伸尺寸
     */
    private boolean isPictureAutoSize;
    /**
     * 反向标记
     */
    private boolean lineFX,lineFY;
    /**
     * 控制点列队
     */
    private int moveF = 511;
    /**
     * 箭头1
     */
    private int arrow1 = 0;
    /**
     * 箭头2
     */
    private int arrow2 = 0;
    /**
     * 箭头1长度
     */
    private int arrow1Length = 15;
    /**
     * 箭头2长度
     */
    private int arrow2Length = 15;
    /**
     * 箭头1度数
     */
    private double arrow1Degree = 15.0;
    /**
     * 箭头2度数
     */
    private double arrow2Degree = 15.0;
    /**
     * 文本换行模式
     */
    private int textEnterMode;
    /**
     * 文本
     */
    private String text = "";
    /**
     * 能否编辑
     */
    private boolean enabled = true;
    /**
     * 数据
     */
    private Vector data = new Vector();
    /**
     * 设置显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
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
     * 设置背景颜色
     * @param bkColor Color
     */
    public void setBkColor(Color bkColor)
    {
        this.bkColor = bkColor;
    }
    /**
     * 得到背景颜色
     * @return Color
     */
    public Color getBkColor()
    {
        return bkColor;
    }
    /**
     * 设置边框颜色
     * @param borderColor Color
     */
    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }
    /**
     * 得到边框颜色
     * @return Color
     */
    public Color getBorderColor()
    {
        return borderColor;
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
     * 设置字体名称
     * @param name String
     */
    public void setFontName(String name)
    {
        setFont(new Font(name,getFontStyle(),getFontSize()));
    }
    /**
     * 得到字体名称
     * @return String
     */
    public String getFontName()
    {
        return getFont().getFontName();
    }
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        setFont(new Font(getFontName(),getFontStyle(),size));
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
     * 设置字体风格
     * @param style int
     */
    public void setFontStyle(int style)
    {
        setFont(new Font(getFontName(),style,getFontSize()));
    }
    /**
     * 得到字体风格
     * @return int
     */
    public int getFontStyle()
    {
        return getFont().getStyle();
    }
    /**
     * 设置粗体
     * @param b boolean
     */
    public void setFontBold(boolean b)
    {
        setFontStyle((b?Font.BOLD:0)|((isFontItalic())?Font.ITALIC:0));
    }
    /**
     * 是否是粗体
     * @return boolean
     */
    public boolean isFontBold()
    {
        return (getFontStyle() & Font.BOLD) == Font.BOLD;
    }
    /**
     * 设置是否是斜体
     * @param b boolean
     */
    public void setFontItalic(boolean b)
    {
        setFontStyle((isFontBold()?Font.BOLD:0)|(b?Font.ITALIC:0));
    }
    /**
     * 是否是斜体
     * @return boolean
     */
    public boolean isFontItalic()
    {
        return (getFontStyle() & Font.ITALIC) == Font.ITALIC;
    }
    /**
     * 设置旋转角度
     * @param fontRotate double
     */
    public void setFontRotate(double fontRotate)
    {
        this.fontRotate = fontRotate;
    }
    /**
     * 得到旋转角度
     * @return double
     */
    public double getFontRotate()
    {
        return fontRotate;
    }
    /**
     * 得到绘制字体
     * @param font Font
     * @return Font
     */
    public Font getDrawFont(Font font)
    {
        if(getFontRotate() == 0.0)
            return font;
        AffineTransform fontAT = new AffineTransform();
        fontAT.rotate(Math.toRadians(getFontRotate()));
        return font.deriveFont(fontAT);
    }
    /**
     * 设置对其方式
     * 0 居左
     * 1 居中
     * 2 居右
     * @param alignment int
     */
    public void setAlignment(int alignment)
    {
        this.alignment = alignment;
    }
    /**
     * 得到对其方式
     * 0 居左
     * 1 居中
     * 2 居右
     * @return int
     */
    public int getAlignment()
    {
        return alignment;
    }
    /**
     * 设置对其方式
     * 0 居上
     * 1 居中
     * 2 居下
     * @param alignmentH int
     */
    public void setAlignmentH(int alignmentH)
    {
        this.alignmentH = alignmentH;
    }
    /**
     * 得到对其方式
     * 0 居上
     * 1 居中
     * 2 居下
     * @return int
     */
    public int getAlignmentH()
    {
        return alignmentH;
    }
    /**
     * 设置下划线
     * @param isLine boolean
     */
    public void setIsLine(boolean isLine)
    {
        this.isLine = isLine;
    }
    /**
     * 是否现实下划线
     * @return boolean
     */
    public boolean isLine()
    {
        return isLine;
    }
    /**
     * 设置自动折行高度
     * @param autoEnterHeight int
     */
    public void setAutoEnterHeight(int autoEnterHeight)
    {
        this.autoEnterHeight = autoEnterHeight;
    }
    /**
     * 得到自动折行高度
     * @return int
     */
    public int getAutoEnterHeight()
    {
        return autoEnterHeight;
    }
    /**
     * 设置是否显示边框
     * @param borderVisible boolean
     */
    public void setBorderVisible(boolean borderVisible)
    {
        this.borderVisible = borderVisible;
    }
    /**
     * 是否显示边框
     * @return boolean
     */
    public boolean isBorderVisible()
    {
        return borderVisible;
    }
    /**
     * 设置边框线高
     * @param borderWidth float
     */
    public void setBorderWidth(float borderWidth)
    {
        this.borderWidth = borderWidth;
    }
    /**
     * 得到边框线高
     * @return float
     */
    public float getBorderWidth()
    {
        return borderWidth;
    }
    /**
     * 设置边框线类型
     * @param borderLineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setBorderLineType(int borderLineType)
    {
        this.borderLineType = borderLineType;
    }
    /**
     * 得到边框线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getBorderLineType()
    {
        return borderLineType;
    }
    /**
     * 得到线类型对象
     * @return Stroke
     */
    public Stroke getStroke()
    {
        switch(getBorderLineType())
        {
        case 1:
            return new BasicStroke(getBorderWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{5.0f}, 0.0f);
        case 2:
            return new BasicStroke(getBorderWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{5.0f,2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getBorderWidth());
    }
    /**
     * 设置按钮图片
     * @param name 图片名称
     */
    public void setPictureName(String name)
    {
        if(name == null || name.length() == 0)
        {
            if(pictureName == null)
                return;
            pictureName = null;
            icon = null;
            iconInit = true;
            return;
        }
        if(name.equals(pictureName))
            return;
        pictureName = name;
        icon = null;
        iconInit = false;
    }
    /**
     * 得到按钮图片
     * @return 图片名称
     */
    public String getPictureName()
    {
      return pictureName;
    }
    /**
     * 得到图标
     * @return Icon
     */
    public Icon getIcon()
    {
        if(iconInit || icon != null)
            return icon;
        if(getPictureName() != null && getPictureName().length() > 0)
            icon = createImageIcon(getPictureName());
        iconInit = true;
        return icon;
    }
    /**
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
        if(filename.toUpperCase().startsWith("%ROOT%") && TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage(filename);
        if(filename.toUpperCase().startsWith("%FILE%"))
        {
            String n = filename.substring(6);
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),n);
        }
        if(filename.toUpperCase().startsWith("%FILE.ROOT%"))
        {
            String n = filename.substring(11);
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),TIOM_FileServer.getRoot() + n);
        }
        if(filename.toUpperCase().startsWith("%FILE.ROOT."))
        {
            String n = filename.substring(11);
            int index = n.indexOf("%");
            if(index == -1)
            {
                System.out.println("没有找到图标" + filename);
                return null;
            }
            String name = n.substring(0,index);
            n = n.substring(index + 1);
            String path = TIOM_FileServer.getRoot() + TIOM_FileServer.getPath(name) + n;
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),path);
        }
        /*if(filename.toUpperCase().startsWith("%PATPIC%"))
        {
            String n = filename.substring(8);
            if(n.startsWith("<") && n.endsWith(">"))
            {
                n = n.substring(1,n.length() - 1);
                n = getParmValue(n);
            }
            n = StringTool.fill("0",12 - n.length()) + n;
            String p1 = n.substring(0,3);
            String p2 = n.substring(3,6);
            String p3 = n.substring(6,9);
            n = p1 + "\\" + p2 + "\\" + p3 + "\\" + n + ".jpg";
            String path = TIOM_FileServer.getRoot() + TIOM_FileServer.getPath("PatInfPIC.ServerPath") + n;
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),path);
        }*/
        if(TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" + filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if(icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try{
            icon = new ImageIcon(getClass().getResource(path));
        }catch(NullPointerException e)
        {
            System.out.println("没有找到图标" + path);
        }
        return icon;
    }
    /**
     * 设置图片自动拉伸尺寸
     * @param isPictureAutoSize boolean
     */
    public void setPictureAutoSize(boolean isPictureAutoSize)
    {
        this.isPictureAutoSize = isPictureAutoSize;
    }
    /**
     * 图片是否自动拉伸尺寸
     * @return boolean
     */
    public boolean isPictureAutoSize()
    {
        return isPictureAutoSize;
    }
    /**
     * 设置反向标记
     * @param lineFX boolean
     */
    public void setLineFX(boolean lineFX)
    {
        this.lineFX = lineFX;
    }
    /**
     * 得到反响标记
     * @return boolean
     */
    public boolean isLineFX()
    {
        return lineFX;
    }
    /**
     * 设置反向标记
     * @param lineFY boolean
     */
    public void setLineFY(boolean lineFY)
    {
        this.lineFY = lineFY;
    }
    /**
     * 得到反响标记
     * @return boolean
     */
    public boolean isLineFY()
    {
        return lineFY;
    }
    public void setMoveF(int moveF)
    {
        this.moveF = moveF;
    }
    public int getMoveF()
    {
        return moveF;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getX()
    {
        return x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getY()
    {
        return y;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public int getWidth()
    {
        return width;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }
    public int getHeight()
    {
        return height;
    }
    /**
     * 测试坐标在线组件内部
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean isSelectLineCheck(int x,int y)
    {
        int l = 4;
        if(x <  - l / 2 || x > + getWidth() + l / 2 ||
           y <  - l / 2 || y > + getHeight() + l / 2)
            return false;
        if(getWidth() == 0 || getHeight() == 0)
            return true;
        if(!isLineFX() && !isLineFY() || isLineFX() && isLineFY())
        {
            if(getWidth() < getHeight())
            {
                if(Math.abs(x - (double) getWidth() / (double) getHeight() *
                            (double) y) < l)
                    return true;
            }
            else
            {
                if(Math.abs(y - (double) getHeight() / (double) getWidth() *
                            (double) x) < l)
                    return true;
            }
            return false;
        }
        if(getWidth() < getHeight())
        {
            if(Math.abs(getWidth() - x -
                        (double) getWidth() / (double) getHeight() *
                        (double) y) < l)
                return true;
        }
        else
        {
            if(Math.abs(getHeight() - y -
                        (double) getHeight() / (double) getWidth() *
                        (double) x) < l)
                return true;
        }
        return false;
    }
    public void setArrow1(int arrow1)
    {
        this.arrow1 = arrow1;
    }
    public int getArrow1()
    {
        return arrow1;
    }
    public void setArrow2(int arrow2)
    {
        this.arrow2 = arrow2;
    }
    public int getArrow2()
    {
        return arrow2;
    }
    public boolean isArrowDraw(int f)
    {
        if(f == 1 && getArrow1() == 0)
            return false;
        if(f == 2 && getArrow2() == 0)
            return false;
        return true;
    }
    public int getArrowDraw(int f)
    {
        if(f == 1)
            return getArrow1();
        if(f == 2)
            return getArrow2();
        return 0;
    }
    public int getArrowLength(int f)
    {
        switch(f)
        {
        case 1:
            return getArrow1Length();
        case 2:
            return getArrow2Length();
        }
        return 0;
    }
    public void setArrow1Length(int arrow1Length)
    {
        this.arrow1Length = arrow1Length;
    }
    public int getArrow1Length()
    {
        return arrow1Length;
    }
    public void setArrow2Length(int arrow2Length)
    {
        this.arrow2Length = arrow2Length;
    }
    public int getArrow2Length()
    {
        return arrow2Length;
    }
    public void setArrow1Degree(double arrow1Degree)
    {
        this.arrow1Degree = arrow1Degree;
    }
    public double getArrow1Degree()
    {
        return arrow1Degree;
    }
    public void setArrow2Degree(double arrow2Degree)
    {
        this.arrow2Degree = arrow2Degree;
    }
    public double getArrow2Degree()
    {
        return arrow2Degree;
    }
    public double getArrowDegree(int l)
    {
        switch(l)
        {
        case 1:
            return getArrow1Degree();
        case 2:
            return getArrow2Degree();
        }
        return 0;
    }
    public void setTextEnterMode(int textEnterMode)
    {
        this.textEnterMode = textEnterMode;
    }
    public int getTextEnterMode()
    {
        return textEnterMode;
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
     * 设置可以编辑
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    /**
     * 是否可编辑
     * @return boolean
     */
    public boolean isEnabled()
    {
        return enabled;
    }
    /**
     * 复制对象
     * @param block GBlock
     */
    public void copyObject(GBlock block)
    {
        block.setVisible(isVisible());
        block.setName(getName());
        block.setX(getX());
        block.setY(getY());
        block.setWidth(getWidth());
        block.setHeight(getHeight());
        block.setColor(getColor());
        block.setBkColor(getBkColor());
        block.setBorderColor(getBorderColor());
        block.setFont(getFont());
        block.setFontRotate(getFontRotate());
        block.setAlignment(getAlignment());
        block.setAlignmentH(getAlignmentH());
        block.setIsLine(isLine());
        block.setAutoEnterHeight(getAutoEnterHeight());
        block.setBorderVisible(isBorderVisible());
        block.setBorderWidth(getBorderWidth());
        block.setBorderLineType(getBorderLineType());
        block.setPictureName(getPictureName());
        block.setPictureAutoSize(isPictureAutoSize());
        block.setLineFX(isLineFX());
        block.setLineFY(isLineFY());
        block.setArrow1(getArrow1());
        block.setArrow2(getArrow2());
        block.setArrow1Length(getArrow1Length());
        block.setArrow2Length(getArrow2Length());
        block.setArrow1Degree(getArrow1Degree());
        block.setArrow2Degree(getArrow2Degree());
        block.setTextEnterMode(getTextEnterMode());
        block.setText(getText());
        block.setEnabled(isEnabled());
    }
    /**
     * 设置数据
     * @param data Vector
     */
    public void setData(Vector data)
    {
        if(data == null)
            data = new Vector();
        this.data = data;
    }
    /**
     * 得到数据
     * @return Vector
     */
    public Vector getData()
    {
        return data;
    }
    /**
     * 数据个数
     * @return int
     */
    public int sizeData()
    {
        return data.size();
    }
    /**
     * 增加数据
     * @param name String
     * @param value String
     */
    public void addData(String name,String value)
    {
        Vector v = new Vector();
        v.add(name);
        v.add(value);
        getData().add(v);
    }
    /**
     * 插入数据
     * @param index int 位置
     * @param name String
     * @param value String
     */
    public void addData(int index,String name,String value)
    {
        if(name == null)
            name = "";
        Vector v = new Vector();
        v.add(name);
        v.add(value);
        getData().add(index,v);
    }
    /**
     * 删除数据
     * @param index int
     */
    public void removeData(int index)
    {
        getData().remove(index);
    }
    /**
     * 得到数据
     * @param index int
     * @return Vector
     */
    public Vector getData(int index)
    {
        return (Vector)getData().get(index);
    }
    /**
     * 得到数据名称
     * @param index int
     * @return String
     */
    public String getDataName(int index)
    {
        Vector v = getData(index);
        if(v == null || v.size() == 0)
            return null;
        return (String)v.get(0);
    }
    /**
     * 得到数据值
     * @param index int
     * @return String
     */
    public String getDataValue(int index)
    {
        Vector v = getData(index);
        if(v == null || v.size() < 2)
            return null;
        return (String)v.get(1);
    }
    /**
     * 删除数据
     * @param name String
     */
    public void removeValue(String name)
    {
        if(name == null)
            return;
        for(int i = sizeData() - 1;i >= 0;i --)
            if(name.equals(getDataName(i)))
                removeData(i);
    }
}
