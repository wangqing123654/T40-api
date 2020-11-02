package com.dongyang.tui.text.div;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.awt.Font;
import java.io.IOException;
import com.dongyang.util.TypeTool;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 条码</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.6.17
 * @version 1.0
 */
public class VBarCode extends DIVBase
{
    private int x;
    private int y;
    private BarCode barcode;
    private String drawText;
    /**
     * 显示文字
     */
    private String text = "";
    /**
     * 文字TParm传入
     */
    private boolean isTextT;
    /**
     * 构造器
     */
    public VBarCode()
    {
        barcode = new BarCode();
    }
    /**
     * 构造器
     * @param x int
     * @param y int
     */
    public VBarCode(int x,int y)
    {
        this();
        setX(x);
        setY(y);
    }
    /**
     * 设置X
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 得到X
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * 设置Y
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到Y1
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置文字
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
    }
    /**
     * 得到文字
     * @return String
     */
    public String getText()
    {
        return text;
    }
    /**
     * 设置条码类型
     * @param barType int
     */
    public void setSymbologyID(int barType)
    {
        barcode.setSymbologyID(barType);
    }
    /**
     * 得到条码类型
     * @return int
     */
    public int getSymbologyID()
    {
        return barcode.getSymbologyID();
    }
    /**
     * 设置条码颜色
     * @param color Color
     */
    public void setBarColor(Color color)
    {
        this.barcode.setForeground(color);
    }
    /**
     * 得到条码颜色
     * @return Color
     */
    public Color getBarColor()
    {
        return barcode.getForeground();
    }
    /**
     * 得到条码颜色(String)
     * @return String
     */
    public String getBarColorString()
    {
        if(getBarColor() == null)
            return "";
        return getBarColor().getRed() + "," + getBarColor().getGreen() + "," + getBarColor().getBlue();
    }
    /**
     * 设置条码文本颜色
     * @param color Color
     */
    public void setBarTextColor(Color color)
    {
        this.barcode.setTextFontColor(color);
    }
    /**
     * 得到条码文本颜色
     * @return Color
     */
    public Color getBarTextColor()
    {
        return barcode.getTextFontColor();
    }
    /**
     * 得到条码文本颜色(String)
     * @return String
     */
    public String getBarTextColorString()
    {
        if(getBarTextColor() == null)
            return "";
        return getBarTextColor().getRed() + "," + getBarTextColor().getGreen() + "," + getBarTextColor().getBlue();
    }
    /**
     * 设置背景颜色
     * @param color Color
     */
    public void setBackground(Color color)
    {
        barcode.setBackground(color);
    }
    /**
     * 得到背景颜色
     * @return Color
     */
    public Color getBackground()
    {
        return barcode.getBackground();
    }
    /**
     * 得到背景颜色(String)
     * @return String
     */
    public String getBackgroundString()
    {
        if(getBackground() == null)
            return "";
        return getBackground().getRed() + "," + getBackground().getGreen() + "," + getBackground().getBlue();
    }
    /**
     * 设置条码旋转角度
     * @param i int
     */
    public void setRotationAngle(int i)
    {
        barcode.setRotationAngle(i);
    }
    /**
     * 得到设置条码旋转角度
     * @return int
     */
    public int getRotationAngle()
    {
        return barcode.getRotationAngle();
    }
    /**
     * 设置条码高度
     * @param d double
     */
    public void setBarHeightCM(double d)
    {
        barcode.setBarHeightCM(d);
    }
    /**
     * 得到条码高度
     * @return double
     */
    public double getBarHeightCM()
    {
        return barcode.getBarHeightCM();
    }
    /**
     * 设置条码宽度
     * @param d double
     */
    public void setXDimensionCM(double d)
    {
        barcode.setXDimensionCM(d);
    }
    /**
     * 得到条码宽度
     * @return double
     */
    public double getXDimensionCM()
    {
        return barcode.getXDimensionCM();
    }
    /**
     * 设置计算验证码
     * @param flag boolean
     */
    public void setCheckCharacter(boolean flag)
    {
        barcode.setCheckCharacter(flag);
    }
    /**
     * 得到计算验证码
     * @return boolean
     */
    public boolean getCheckCharacter()
    {
        return barcode.getCheckCharacter();
    }
    /**
     * 设置显示条码内容
     * @param flag boolean
     */
    public void setShowText(boolean flag)
    {
        barcode.setShowText(flag);
    }
    /**
     * 是否显示条码内容
     * @return boolean
     */
    public boolean isShowText()
    {
        return barcode.getShowText();
    }
    /**
     * 设置字体
     * @param font Font
     */
    public void setFont(Font font)
    {
        barcode.setFont(font);
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getFont()
    {
        return barcode.getFont();
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
     * 设置文字TParm代入
     * @param isTextT boolean
     */
    public void setTextT(boolean isTextT)
    {
        this.isTextT = isTextT;
    }
    /**
     * 是否文字TParm代入
     * @return boolean
     */
    public boolean isTextT()
    {
        return isTextT;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "条码";
    }
    /**
     * 得到类型
     * @return int
     */
    public int getTypeID()
    {
        return 5;
    }
    public int getDrawStartXP()
    {
        return getParent().getStartXP();
    }
    public int getDrawStartYP()
    {
        return getParent().getStartYP();
    }
    /**
     * 得到原点X
     * @return int
     */
    public int getDrawStartX()
    {
        return getParent().getStartX();
    }
    /**
     * 得到原点Y
     * @return int
     */
    public int getDrawStartY()
    {
        return getParent().getStartY();
    }
    /**
     * 显示文字
     * @return String
     */
    public String getDrawText()
    {
        if(!isTextT())
            return getText();
        if(drawText != null && drawText.length() != 0)
            return drawText;
        if(this.isMacroroutineModel()){
            drawText = TypeTool.getString(getMacroroutineData(getName()));
            return drawText;
        }
        Object obj = getPM().getFileManager().getParameter();
        if(obj == null || !(obj instanceof TParm))
            return "";
        drawText = ((TParm)obj).getValue(getName(),"TEXT");
        return drawText;
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        String text = getDrawText();
        if(text != null && text.length() > 0)
        {
            barcode.code = text;
            barcode.setCheckCharacter(true);
            barcode.setCheckCharacterInText(true);
            barcode.topMarginCM = 0.0;
            barcode.leftMarginCM = 0.0;
            int x = (int)(getX() * getZoom() / 75.0 + 0.5);
            int y = (int)(getY() * getZoom() / 75.0 + 0.5);
            barcode.paint(g.create(x + getDrawStartX(), y + getDrawStartY(), 300, 100));
        }
    }
    /**
     * 打印
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        String text = getDrawText();
        if(text != null && text.length() > 0)
        {
            barcode.code = text;
            barcode.setCheckCharacter(true);
            barcode.setCheckCharacterInText(true);
            barcode.topMarginCM = 0.0;
            barcode.leftMarginCM = 0.0;
            barcode.paint(g.create(getX() + getDrawStartXP(), getY() + getDrawStartYP(), 300, 300));
        }
    }
    /**
     * 得到属性对话框名称
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VBarCodeDialog.x";
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeString(1,getName(),"");
        s.writeBoolean(2,isVisible(),true);
        s.writeInt(3,getX(),0);
        s.writeInt(4,getY(),0);
        s.writeString(5,getText(),"");
        s.writeInt(6,getSymbologyID(),13);//0->13 BarCode 默认是13 Code128
        s.writeBoolean(7,isShowText(),true);
        if(getBarColor() != null)
        {
            s.writeShort(8);
            s.writeInt(getBarColor().getRGB());
        }
        if(getBarTextColor() != null)
        {
            s.writeShort(9);
            s.writeInt(getBarTextColor().getRGB());
        }
        if(getBackground() != null)
        {
            s.writeShort(10);
            s.writeInt(getBackground().getRGB());
        }
        s.writeInt(11,getRotationAngle(),0);
        s.writeDouble(12,getBarHeightCM(),1);
        s.writeDouble(13,getXDimensionCM(),0.029999999999999999D);
        s.writeBoolean(14,getCheckCharacter(),true);
        s.writeShort(15);
        s.writeString(getFontName());
        s.writeInt(getFontStyle());
        s.writeInt(getFontSize());
        s.writeBoolean(16,isTextT(),false);
        s.writeString(17,drawText,"");
        s.writeShort(-1);
        //保存项目
        s.writeInt(0);
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            switch (id)
            {
            case 1:
                setName(s.readString());
                break;
            case 2:
                setVisible(s.readBoolean());
                break;
            case 3:
                setX(s.readInt());
                break;
            case 4:
                setY(s.readInt());
                break;
            case 5:
                setText(s.readString());
                break;
            case 6:
                setSymbologyID(s.readInt());
                break;
            case 7:
                setShowText(s.readBoolean());
                break;
            case 8:
                setBarColor(new Color(s.readInt()));
                break;
            case 9:
                setBarTextColor(new Color(s.readInt()));
                break;
            case 10:
                setBackground(new Color(s.readInt()));
                break;
            case 11:
                setRotationAngle(s.readInt());
                break;
            case 12:
                setBarHeightCM(s.readDouble());
                break;
            case 13:
                setXDimensionCM(s.readDouble());
                break;
            case 14:
                setCheckCharacter(s.readBoolean());
                break;
            case 15:
                setFont(new Font(s.readString(),s.readInt(),s.readInt()));
                break;
            case 16:
                setTextT(s.readBoolean());
                break;
            case 17:
                drawText = s.readString();
                break;
            }
            id = s.readShort();
        }
        //读取项目
        int count = s.readInt();
    }
}
