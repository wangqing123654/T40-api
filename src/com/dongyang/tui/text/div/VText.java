package com.dongyang.tui.text.div;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import com.dongyang.util.TypeTool;
import com.dongyang.util.StringTool;
import com.dongyang.data.TParm;
import java.awt.FontMetrics;
import com.dongyang.tui.DFont;

/**
 *
 * <p>Title: 文字</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.22
 * @version 1.0
 */
public class VText extends DIVBase
{
    private int x;
    private int y;
    private int width;
    private boolean x1B;
    private boolean y1B;
    private boolean x2B;
    private String microName;
    private String groupName;
    private String drawText;
    /**
     * 位置
     * 0 居左
     * 1 居中
     * 2 居右
     */
    private int alignment;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 偏移X
     */
    private int x0;
    /**
     * 偏移Y
     */
    private int y0;
    /**
     * 线个数
     */
    private int count;
    /**
     * 显示文字
     */
    private String text = "";
    /**
     * 字体
     */
    private Font font = new Font("宋体",0,10);
    /**
     * 旋转角度
     */
    private double fontRotate = 0.0;
    /**
     * 文本偏移开关
     */
    private boolean textOffsetFlg;
    /**
     * 文本偏移
     */
    private double textOffset;
    /**
     * 格式
     */
    private String textOffsetFormat = "";
    /**
     * 自动折行
     */
    private boolean autoEnter;
    /**
     * 自动折行高度
     */
    private int autoEnterHeight;
    /**
     * 文字TParm传入
     */
    private boolean isTextT;
    /**
     * 下划线
     */
    private boolean isLine;
    /**
     * 底线
     */
    private boolean bottomLine;
    /**
     * 多余的不显示
     */
    private boolean isDelOrder;

    //取CDA名
    private boolean takeCdaName;
    //CDA对应的值;
    private String cdaValue;


    /**
     * 构造器
     */
    public VText()
    {

    }
    /**
     * 构造器
     * @param x int
     * @param y int
     * @param color Color
     */
    public VText(int x,int y,Color color)
    {
        setX(x);
        setY(y);
        setColor(color);
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
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    public void setX1B(boolean x1B)
    {
        this.x1B = x1B;
    }
    public boolean isX1B()
    {
        return x1B;
    }
    public void setY1B(boolean y1B)
    {
        this.y1B = y1B;
    }
    public boolean isY1B()
    {
        return y1B;
    }
    public void setX2B(boolean x2B)
    {
        this.x2B = x2B;
    }
    public boolean isX2B()
    {
        return x2B;
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
     * 设置偏移X
     * @param x0 int
     */
    public void setX0(int x0)
    {
        this.x0 = x0;
    }
    /**
     * 得到偏移X
     * @return int
     */
    public int getX0()
    {
        return x0;
    }
    /**
     * 设置偏移Y
     * @param y0 int
     */
    public void setY0(int y0)
    {
        this.y0 = y0;
    }
    /**
     * 得到偏移Y
     * @return int
     */
    public int getY0()
    {
        return y0;
    }
    /**
     * 设置线个数
     * @param count int
     */
    public void setCount(int count)
    {
        this.count = count;
    }
    /**
     * 得到线个数
     * @return int
     */
    public int getCount()
    {
        return count;
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
    private Font getDrawFont(Font font)
    {
        if(getFontRotate() == 0.0)
            return font;
        AffineTransform fontAT = new AffineTransform();
        fontAT.rotate(Math.toRadians(getFontRotate()));
        return font.deriveFont(fontAT);
    }
    /**
     * 设置文本偏移开关
     * @param textOffsetFlg boolean
     */
    public void setTextOffsetFlg(boolean textOffsetFlg)
    {
        this.textOffsetFlg = textOffsetFlg;
    }
    /**
     * 得到文本偏移开关
     * @return boolean
     */
    public boolean isTextOffsetFlg()
    {
        return textOffsetFlg;
    }
    /**
     * 设置偏移量
     * @param textOffset double
     */
    public void setTextOffset(double textOffset)
    {
        this.textOffset = textOffset;
    }
    /**
     * 得到偏移量
     * @return double
     */
    public double getTextOffset()
    {
        return textOffset;
    }
    /**
     * 设置偏移格式
     * @param textOffsetFormat String
     */
    public void setTextOffsetFormat(String textOffsetFormat)
    {
        this.textOffsetFormat = textOffsetFormat;
    }
    /**
     * 得到偏移格式
     * @return String
     */
    public String getTextOffsetFormat()
    {
        return textOffsetFormat;
    }
    /**
     * 得到绘制文字
     * @param s String
     * @param index int
     * @return String
     */
    public String getDrawText(String s,int index)
    {
        if(!isTextOffsetFlg())
            return s;
        double d = TypeTool.getDouble(s) + getTextOffset();
        return textFormat("" + d);
    }
    /**
     * 格式化
     * @param s String
     * @return String
     */
    public String textFormat(String s)
    {
        if(getTextOffsetFormat() == null || getTextOffsetFormat().length() == 0)
            return s;
        return StringTool.S(s,getTextOffsetFormat());
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
        return "文字";
    }
    /**
     * 得到类型
     * @return int
     */
    public int getTypeID()
    {
        return 3;
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
     * 设置自动折行
     * @param autoEnter boolean
     */
    public void setAutoEnter(boolean autoEnter)
    {
        this.autoEnter = autoEnter;
    }
    /**
     * 是否自动折行
     * @return boolean
     */
    public boolean isAutoEnter()
    {
        return autoEnter;
    }
    /**
     * 设置多余的不显示
     * @param isDelOrder boolean
     */
    public void setDelOrder(boolean isDelOrder)
    {
        this.isDelOrder = isDelOrder;
    }
    /**
     * 得到多余的不显示
     * @return boolean
     */
    public boolean isDelOrder()
    {
        return isDelOrder;
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
     * 设置底线
     * @param bottomLine boolean
     */
    public void setBottomLine(boolean bottomLine)
    {
        this.bottomLine = bottomLine;
    }
    /**
     * 是否绘制底线
     * @return boolean
     */
    public boolean isBottomLine()
    {
        return bottomLine;
    }
    /**
     * 显示文字
     * @return String
     */
    public String getDrawText()
    {
    	//不是传入参数
        if(!isTextT())
            return getGS(getText());
        
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
    public String getGS(String s)
    {
        if(s == null)
            return s;
        String s1 = s.toUpperCase();
       
        int index = s1.indexOf("#");
        if(index == -1)
            return s;
        index = s1.indexOf("#PAGECOUNT");
        while(index != -1)
        {
            s = s.substring(0,index) + getPM().getPageManager().size() + s.substring(index + 10);
            s1 = s.toUpperCase();
            index = s1.indexOf("#PAGECOUNT");
        }
        index = s1.indexOf("#PAGE");
        while(index != -1)
        {
            s = s.substring(0,index) + (getPageIndex() + 1) + s.substring(index + 5);
            s1 = s.toUpperCase();
            index = s1.indexOf("#PAGE");
        }
        return s;
    }
    /**
     * 得到打印X
     * @return int
     */
    public int getStartXP()
    {
        if(isX1B())
            return getParent().getStartXP() + getParent().getEndXP() - getX();
        return getParent().getStartXP() + getX();
    }
    /**
     * 得到打印Y
     * @return int
     */
    public int getStartYP()
    {
        if(isY1B())
            return getParent().getStartYP() + getParent().getEndYP() - getY();
        return getParent().getStartYP() + getY();
    }
    /**
     * 起始点X
     * @return int
     */
    public int getStartX()
    {
        if(isX1B())
            return getDrawStartX() + getParent().getEndX() - (int)(getX() * getZoom() / 75.0 + 0.5);
        return getDrawStartX() + (int)(getX() * getZoom() / 75.0 + 0.5);
    }
    /**
     * 起始点Y
     * @return int
     */
    public int getStartY()
    {
        if(isY1B())
            return getDrawStartY() + getParent().getEndY() - (int)(getY() * getZoom() / 75.0 + 0.5);
        return getDrawStartY() + (int)(getY() * getZoom() / 75.0 + 0.5);
    }
    /**
     * 得到结束点
     * @return int
     */
    public int getEndXP()
    {
        if(isX2B())
            return getParent().getEndXP() - getWidth() - getX();
        return getWidth();
    }
    /**
     * 得到结束点
     * @return int
     */
    public int getEndX()
    {
        return (int)(getEndXP() * getZoom() / 75.0 + 0.5);
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        g.setColor(getColor());
        double zoom = getZoom() / 75.0;
        Font font = getDrawFont(new Font(getFont().getName(),getFont().getStyle(),
                                         (int)(getFont().getSize2D() * zoom + 0.5)));
        g.setFont(font);
        FontMetrics fontMetrics = DFont.getFontMetrics(getFont());

        int x = getStartX();
        int y = getStartY();
        int width = getEndX();
        //绘制底线
        if(isBottomLine() && width > 0)
            g.drawLine(x,y + 3,x + width,y + 3);
        String text = textFormat(getDrawText());
        paintBaseText(g,text,x,y,width,fontMetrics,false);

        for(int i = 1;i <= count;i++)
        {
            text = getDrawText(text,i);
            int x0 = (int)(getX0() * i * getZoom() / 75.0 + 0.5);
            int y0 = (int)(getY0() * i * getZoom() / 75.0 + 0.5);
            //绘制底线
            if(isBottomLine() && width > 0)
                g.drawLine(x + x0,y + y0 + 3,x + width + x0,y + 3 + y0);
            paintBaseText(g,text,x + x0,y + y0,width,fontMetrics,false);
        }
    }
    /**
     * 绘制文本
     * @param g Graphics
     * @param text String
     * @param x int
     * @param y int
     * @param width int
     * @param fontMetrics FontMetrics
     * @param isPrint boolean
     */
    public void paintBaseText(Graphics g,String text,int x,int y,int width,FontMetrics fontMetrics,boolean isPrint)
    {
        CStringDraw csd = new CStringDraw(g,fontMetrics,x,y,
                                          width,text,getZoom(),getAutoEnterHeight(),
                                          isLine(),getAlignment(),isAutoEnter()?1:0,isDelOrder(),isPrint);
        csd.draw();
    }
    /**
     * 打印
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        g.setColor(getColor());
        g.setFont(getDrawFont(getFont()));
        FontMetrics fontMetrics = DFont.getFontMetrics(getFont());
        int x = getStartXP();
        int y = getStartYP();
        int width = getEndXP();
        //绘制底线
        if(isBottomLine() && width > 0)
            g.drawLine(x,y + 3,x + width,y + 3);
        String text = textFormat(getDrawText());
        paintBaseText(g,text,x,y,width,fontMetrics,true);
        for(int i = 1;i <= count;i++)
        {
            text = getDrawText(text,i);
            int x0 = getX0() * i;
            int y0 = getY0() * i;
            //绘制底线
            if(isBottomLine() && width > 0)
                g.drawLine(x + x0,y + y0 + 3,x + width + x0,y + 3 + y0);
            paintBaseText(g,text,x + x0,y + y0,width,fontMetrics,true);
        }
    }
    /**
     * 得到属性对话框名称
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VTextDialog.x";
    }

    /**
     * 显示宏文字
     * @return String
     */
    public String getMicroText()
    {
        if(!isTextT())
            return getText();
        Object obj = getPM().getFileManager().getParameter();
        if(obj == null || !(obj instanceof TParm))
            return getText();
        return ((TParm)obj).getValue(getMicroName(),"TEXT");
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
        s.writeShort(3);
        s.writeInt(getColor().getRGB());
        s.writeInt(4,getX(),0);
        s.writeInt(5,getY(),0);
        s.writeInt(6,getX0(),0);
        s.writeInt(7,getY0(),0);
        s.writeInt(8,getCount(),0);
        s.writeString(9,getText(),"");
        s.writeShort(10);
        s.writeString(getFontName());
        s.writeInt(getFontStyle());
        s.writeInt(getFontSize());
        s.writeDouble(getFontRotate());
        s.writeBoolean(11,isTextOffsetFlg(),false);
        s.writeDouble(12,getTextOffset(),0);
        s.writeString(13,getTextOffsetFormat(),"");
        s.writeBoolean(14,isAutoEnter(),false);
        s.writeInt(15,getAutoEnterHeight(),0);
        s.writeBoolean(16,isTextT(),false);
        s.writeInt(17,getWidth(),0);
        s.writeShort(18);
        s.writeBoolean(isX1B());
        s.writeBoolean(isY1B());
        s.writeBoolean(isX2B());
        s.writeInt(19,getAlignment(),0);
        s.writeBoolean(20,isLine(),false);
        s.writeBoolean(21,isBottomLine(),false);
        s.writeBoolean(22,isDelOrder(),false);
        s.writeString(23,getMicroName(),"");
        s.writeString(24,getGroupName(),"");
        s.writeString(25,drawText,"");
        s.writeBoolean(26,this.getTakeCdaName(),false);
        s.writeString(27,this.getCdaValue(),"");
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
                setColor(new Color(s.readInt()));
                break;
            case 4:
                setX(s.readInt());
                break;
            case 5:
                setY(s.readInt());
                break;
            case 6:
                setX0(s.readInt());
                break;
            case 7:
                setY0(s.readInt());
                break;
            case 8:
                setCount(s.readInt());
                break;
            case 9:
                setText(s.readString());
                break;
            case 10:
                setFont(new Font(s.readString(),s.readInt(),s.readInt()));
                setFontRotate(s.readDouble());
                break;
            case 11:
                setTextOffsetFlg(s.readBoolean());
                break;
            case 12:
                setTextOffset(s.readDouble());
                break;
            case 13:
                setTextOffsetFormat(s.readString());
                break;
            case 14:
                setAutoEnter(s.readBoolean());
                break;
            case 15:
                setAutoEnterHeight(s.readInt());
                break;
            case 16:
                setTextT(s.readBoolean());
                break;
            case 17:
                setWidth(s.readInt());
                break;
            case 18:
                setX1B(s.readBoolean());
                setY1B(s.readBoolean());
                setX2B(s.readBoolean());
                break;
            case 19:
                setAlignment(s.readInt());
                break;
            case 20:
                setIsLine(s.readBoolean());
                break;
            case 21:
                setBottomLine(s.readBoolean());
                break;
            case 22:
                setDelOrder(s.readBoolean());
                break;
            case 23:
                setMicroName(s.readString());
                break;
            case 24:
                setGroupName(s.readString());
                break;
            case 25:
                drawText = s.readString();
                break;
            case 26:
                setTakeCdaName(s.readBoolean());
                break;
            case 27:
                setCdaValue(s.readString());
                break;
            }
            id = s.readShort();
        }
        //读取项目
        int count = s.readInt();
    }

    /**
     * 设置宏名称
     * @param code String
     */
    public void setMicroName(String microName){
        this.microName = microName;
    }

    /**
     * 得到宏名称
     * @return String
     */
    public String getMicroName(){
        return microName;
    }
    /**
     * 设置组名称
     * @param code String
     */
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    /**
     * 设置组名称
     * @return String
     */
    public String getGroupName(){
        return groupName;
    }

    /**
     *
     * @param takeCdaName boolean
     */
    public void setTakeCdaName(boolean takeCdaName)
    {
        this.takeCdaName = takeCdaName;
    }

    /**
     *
     * @return boolean
     */
    public boolean getTakeCdaName()
    {
        return takeCdaName;
    }
    /**
     *
     * @param cdaValue String
     */
    public void setCdaValue(String cdaValue){
        this.cdaValue = cdaValue;
    }

    /**
     *
     * @return String
     */
    public String getCdaValue(){
        return cdaValue;
    }



}
