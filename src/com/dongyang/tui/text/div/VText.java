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
 * <p>Title: ����</p>
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
     * λ��
     * 0 ����
     * 1 ����
     * 2 ����
     */
    private int alignment;
    /**
     * ��ɫ
     */
    private Color color;
    /**
     * ƫ��X
     */
    private int x0;
    /**
     * ƫ��Y
     */
    private int y0;
    /**
     * �߸���
     */
    private int count;
    /**
     * ��ʾ����
     */
    private String text = "";
    /**
     * ����
     */
    private Font font = new Font("����",0,10);
    /**
     * ��ת�Ƕ�
     */
    private double fontRotate = 0.0;
    /**
     * �ı�ƫ�ƿ���
     */
    private boolean textOffsetFlg;
    /**
     * �ı�ƫ��
     */
    private double textOffset;
    /**
     * ��ʽ
     */
    private String textOffsetFormat = "";
    /**
     * �Զ�����
     */
    private boolean autoEnter;
    /**
     * �Զ����и߶�
     */
    private int autoEnterHeight;
    /**
     * ����TParm����
     */
    private boolean isTextT;
    /**
     * �»���
     */
    private boolean isLine;
    /**
     * ����
     */
    private boolean bottomLine;
    /**
     * ����Ĳ���ʾ
     */
    private boolean isDelOrder;

    //ȡCDA��
    private boolean takeCdaName;
    //CDA��Ӧ��ֵ;
    private String cdaValue;


    /**
     * ������
     */
    public VText()
    {

    }
    /**
     * ������
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
     * ����X
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * �õ�X
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * ����Y
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * �õ�Y1
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * �õ����
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
     * ����ƫ��X
     * @param x0 int
     */
    public void setX0(int x0)
    {
        this.x0 = x0;
    }
    /**
     * �õ�ƫ��X
     * @return int
     */
    public int getX0()
    {
        return x0;
    }
    /**
     * ����ƫ��Y
     * @param y0 int
     */
    public void setY0(int y0)
    {
        this.y0 = y0;
    }
    /**
     * �õ�ƫ��Y
     * @return int
     */
    public int getY0()
    {
        return y0;
    }
    /**
     * �����߸���
     * @param count int
     */
    public void setCount(int count)
    {
        this.count = count;
    }
    /**
     * �õ��߸���
     * @return int
     */
    public int getCount()
    {
        return count;
    }
    /**
     * ��������
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getText()
    {
        return text;
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
     * ������������
     * @param name String
     */
    public void setFontName(String name)
    {
        setFont(new Font(name,getFontStyle(),getFontSize()));
    }
    /**
     * �õ���������
     * @return String
     */
    public String getFontName()
    {
        return getFont().getFontName();
    }
    /**
     * ��������ߴ�
     * @param size int
     */
    public void setFontSize(int size)
    {
        setFont(new Font(getFontName(),getFontStyle(),size));
    }
    /**
     * �õ�����ߴ�
     * @return int
     */
    public int getFontSize()
    {
        return getFont().getSize();
    }
    /**
     * ����������
     * @param style int
     */
    public void setFontStyle(int style)
    {
        setFont(new Font(getFontName(),style,getFontSize()));
    }
    /**
     * �õ�������
     * @return int
     */
    public int getFontStyle()
    {
        return getFont().getStyle();
    }
    /**
     * ���ô���
     * @param b boolean
     */
    public void setFontBold(boolean b)
    {
        setFontStyle((b?Font.BOLD:0)|((isFontItalic())?Font.ITALIC:0));
    }
    /**
     * �Ƿ��Ǵ���
     * @return boolean
     */
    public boolean isFontBold()
    {
        return (getFontStyle() & Font.BOLD) == Font.BOLD;
    }
    /**
     * �����Ƿ���б��
     * @param b boolean
     */
    public void setFontItalic(boolean b)
    {
        setFontStyle((isFontBold()?Font.BOLD:0)|(b?Font.ITALIC:0));
    }
    /**
     * �Ƿ���б��
     * @return boolean
     */
    public boolean isFontItalic()
    {
        return (getFontStyle() & Font.ITALIC) == Font.ITALIC;
    }
    /**
     * ������ת�Ƕ�
     * @param fontRotate double
     */
    public void setFontRotate(double fontRotate)
    {
        this.fontRotate = fontRotate;
    }
    /**
     * �õ���ת�Ƕ�
     * @return double
     */
    public double getFontRotate()
    {
        return fontRotate;
    }
    /**
     * �õ���������
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
     * �����ı�ƫ�ƿ���
     * @param textOffsetFlg boolean
     */
    public void setTextOffsetFlg(boolean textOffsetFlg)
    {
        this.textOffsetFlg = textOffsetFlg;
    }
    /**
     * �õ��ı�ƫ�ƿ���
     * @return boolean
     */
    public boolean isTextOffsetFlg()
    {
        return textOffsetFlg;
    }
    /**
     * ����ƫ����
     * @param textOffset double
     */
    public void setTextOffset(double textOffset)
    {
        this.textOffset = textOffset;
    }
    /**
     * �õ�ƫ����
     * @return double
     */
    public double getTextOffset()
    {
        return textOffset;
    }
    /**
     * ����ƫ�Ƹ�ʽ
     * @param textOffsetFormat String
     */
    public void setTextOffsetFormat(String textOffsetFormat)
    {
        this.textOffsetFormat = textOffsetFormat;
    }
    /**
     * �õ�ƫ�Ƹ�ʽ
     * @return String
     */
    public String getTextOffsetFormat()
    {
        return textOffsetFormat;
    }
    /**
     * �õ���������
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
     * ��ʽ��
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
     * ��������TParm����
     * @param isTextT boolean
     */
    public void setTextT(boolean isTextT)
    {
        this.isTextT = isTextT;
    }
    /**
     * �Ƿ�����TParm����
     * @return boolean
     */
    public boolean isTextT()
    {
        return isTextT;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "����";
    }
    /**
     * �õ�����
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
     * �õ�ԭ��X
     * @return int
     */
    public int getDrawStartX()
    {
        return getParent().getStartX();
    }
    /**
     * �õ�ԭ��Y
     * @return int
     */
    public int getDrawStartY()
    {
        return getParent().getStartY();
    }
    /**
     * �����Զ�����
     * @param autoEnter boolean
     */
    public void setAutoEnter(boolean autoEnter)
    {
        this.autoEnter = autoEnter;
    }
    /**
     * �Ƿ��Զ�����
     * @return boolean
     */
    public boolean isAutoEnter()
    {
        return autoEnter;
    }
    /**
     * ���ö���Ĳ���ʾ
     * @param isDelOrder boolean
     */
    public void setDelOrder(boolean isDelOrder)
    {
        this.isDelOrder = isDelOrder;
    }
    /**
     * �õ�����Ĳ���ʾ
     * @return boolean
     */
    public boolean isDelOrder()
    {
        return isDelOrder;
    }
    /**
     * �����Զ����и߶�
     * @param autoEnterHeight int
     */
    public void setAutoEnterHeight(int autoEnterHeight)
    {
        this.autoEnterHeight = autoEnterHeight;
    }
    /**
     * �õ��Զ����и߶�
     * @return int
     */
    public int getAutoEnterHeight()
    {
        return autoEnterHeight;
    }
    /**
     * ���ö��䷽ʽ
     * 0 ����
     * 1 ����
     * 2 ����
     * @param alignment int
     */
    public void setAlignment(int alignment)
    {
        this.alignment = alignment;
    }
    /**
     * �õ����䷽ʽ
     * 0 ����
     * 1 ����
     * 2 ����
     * @return int
     */
    public int getAlignment()
    {
        return alignment;
    }
    /**
     * �����»���
     * @param isLine boolean
     */
    public void setIsLine(boolean isLine)
    {
        this.isLine = isLine;
    }
    /**
     * �Ƿ���ʵ�»���
     * @return boolean
     */
    public boolean isLine()
    {
        return isLine;
    }
    /**
     * ���õ���
     * @param bottomLine boolean
     */
    public void setBottomLine(boolean bottomLine)
    {
        this.bottomLine = bottomLine;
    }
    /**
     * �Ƿ���Ƶ���
     * @return boolean
     */
    public boolean isBottomLine()
    {
        return bottomLine;
    }
    /**
     * ��ʾ����
     * @return String
     */
    public String getDrawText()
    {
    	//���Ǵ������
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
     * �õ���ӡX
     * @return int
     */
    public int getStartXP()
    {
        if(isX1B())
            return getParent().getStartXP() + getParent().getEndXP() - getX();
        return getParent().getStartXP() + getX();
    }
    /**
     * �õ���ӡY
     * @return int
     */
    public int getStartYP()
    {
        if(isY1B())
            return getParent().getStartYP() + getParent().getEndYP() - getY();
        return getParent().getStartYP() + getY();
    }
    /**
     * ��ʼ��X
     * @return int
     */
    public int getStartX()
    {
        if(isX1B())
            return getDrawStartX() + getParent().getEndX() - (int)(getX() * getZoom() / 75.0 + 0.5);
        return getDrawStartX() + (int)(getX() * getZoom() / 75.0 + 0.5);
    }
    /**
     * ��ʼ��Y
     * @return int
     */
    public int getStartY()
    {
        if(isY1B())
            return getDrawStartY() + getParent().getEndY() - (int)(getY() * getZoom() / 75.0 + 0.5);
        return getDrawStartY() + (int)(getY() * getZoom() / 75.0 + 0.5);
    }
    /**
     * �õ�������
     * @return int
     */
    public int getEndXP()
    {
        if(isX2B())
            return getParent().getEndXP() - getWidth() - getX();
        return getWidth();
    }
    /**
     * �õ�������
     * @return int
     */
    public int getEndX()
    {
        return (int)(getEndXP() * getZoom() / 75.0 + 0.5);
    }
    /**
     * ����
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
        //���Ƶ���
        if(isBottomLine() && width > 0)
            g.drawLine(x,y + 3,x + width,y + 3);
        String text = textFormat(getDrawText());
        paintBaseText(g,text,x,y,width,fontMetrics,false);

        for(int i = 1;i <= count;i++)
        {
            text = getDrawText(text,i);
            int x0 = (int)(getX0() * i * getZoom() / 75.0 + 0.5);
            int y0 = (int)(getY0() * i * getZoom() / 75.0 + 0.5);
            //���Ƶ���
            if(isBottomLine() && width > 0)
                g.drawLine(x + x0,y + y0 + 3,x + width + x0,y + 3 + y0);
            paintBaseText(g,text,x + x0,y + y0,width,fontMetrics,false);
        }
    }
    /**
     * �����ı�
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
     * ��ӡ
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
        //���Ƶ���
        if(isBottomLine() && width > 0)
            g.drawLine(x,y + 3,x + width,y + 3);
        String text = textFormat(getDrawText());
        paintBaseText(g,text,x,y,width,fontMetrics,true);
        for(int i = 1;i <= count;i++)
        {
            text = getDrawText(text,i);
            int x0 = getX0() * i;
            int y0 = getY0() * i;
            //���Ƶ���
            if(isBottomLine() && width > 0)
                g.drawLine(x + x0,y + y0 + 3,x + width + x0,y + 3 + y0);
            paintBaseText(g,text,x + x0,y + y0,width,fontMetrics,true);
        }
    }
    /**
     * �õ����ԶԻ�������
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VTextDialog.x";
    }

    /**
     * ��ʾ������
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
     * д����
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
        //������Ŀ
        s.writeInt(0);
    }
    /**
     * ������
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
        //��ȡ��Ŀ
        int count = s.readInt();
    }

    /**
     * ���ú�����
     * @param code String
     */
    public void setMicroName(String microName){
        this.microName = microName;
    }

    /**
     * �õ�������
     * @return String
     */
    public String getMicroName(){
        return microName;
    }
    /**
     * ����������
     * @param code String
     */
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    /**
     * ����������
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
