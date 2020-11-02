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
 * <p>Title: ����</p>
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
     * ��ʾ����
     */
    private String text = "";
    /**
     * ����TParm����
     */
    private boolean isTextT;
    /**
     * ������
     */
    public VBarCode()
    {
        barcode = new BarCode();
    }
    /**
     * ������
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
     * ������������
     * @param barType int
     */
    public void setSymbologyID(int barType)
    {
        barcode.setSymbologyID(barType);
    }
    /**
     * �õ���������
     * @return int
     */
    public int getSymbologyID()
    {
        return barcode.getSymbologyID();
    }
    /**
     * ����������ɫ
     * @param color Color
     */
    public void setBarColor(Color color)
    {
        this.barcode.setForeground(color);
    }
    /**
     * �õ�������ɫ
     * @return Color
     */
    public Color getBarColor()
    {
        return barcode.getForeground();
    }
    /**
     * �õ�������ɫ(String)
     * @return String
     */
    public String getBarColorString()
    {
        if(getBarColor() == null)
            return "";
        return getBarColor().getRed() + "," + getBarColor().getGreen() + "," + getBarColor().getBlue();
    }
    /**
     * ���������ı���ɫ
     * @param color Color
     */
    public void setBarTextColor(Color color)
    {
        this.barcode.setTextFontColor(color);
    }
    /**
     * �õ������ı���ɫ
     * @return Color
     */
    public Color getBarTextColor()
    {
        return barcode.getTextFontColor();
    }
    /**
     * �õ������ı���ɫ(String)
     * @return String
     */
    public String getBarTextColorString()
    {
        if(getBarTextColor() == null)
            return "";
        return getBarTextColor().getRed() + "," + getBarTextColor().getGreen() + "," + getBarTextColor().getBlue();
    }
    /**
     * ���ñ�����ɫ
     * @param color Color
     */
    public void setBackground(Color color)
    {
        barcode.setBackground(color);
    }
    /**
     * �õ�������ɫ
     * @return Color
     */
    public Color getBackground()
    {
        return barcode.getBackground();
    }
    /**
     * �õ�������ɫ(String)
     * @return String
     */
    public String getBackgroundString()
    {
        if(getBackground() == null)
            return "";
        return getBackground().getRed() + "," + getBackground().getGreen() + "," + getBackground().getBlue();
    }
    /**
     * ����������ת�Ƕ�
     * @param i int
     */
    public void setRotationAngle(int i)
    {
        barcode.setRotationAngle(i);
    }
    /**
     * �õ�����������ת�Ƕ�
     * @return int
     */
    public int getRotationAngle()
    {
        return barcode.getRotationAngle();
    }
    /**
     * ��������߶�
     * @param d double
     */
    public void setBarHeightCM(double d)
    {
        barcode.setBarHeightCM(d);
    }
    /**
     * �õ�����߶�
     * @return double
     */
    public double getBarHeightCM()
    {
        return barcode.getBarHeightCM();
    }
    /**
     * ����������
     * @param d double
     */
    public void setXDimensionCM(double d)
    {
        barcode.setXDimensionCM(d);
    }
    /**
     * �õ�������
     * @return double
     */
    public double getXDimensionCM()
    {
        return barcode.getXDimensionCM();
    }
    /**
     * ���ü�����֤��
     * @param flag boolean
     */
    public void setCheckCharacter(boolean flag)
    {
        barcode.setCheckCharacter(flag);
    }
    /**
     * �õ�������֤��
     * @return boolean
     */
    public boolean getCheckCharacter()
    {
        return barcode.getCheckCharacter();
    }
    /**
     * ������ʾ��������
     * @param flag boolean
     */
    public void setShowText(boolean flag)
    {
        barcode.setShowText(flag);
    }
    /**
     * �Ƿ���ʾ��������
     * @return boolean
     */
    public boolean isShowText()
    {
        return barcode.getShowText();
    }
    /**
     * ��������
     * @param font Font
     */
    public void setFont(Font font)
    {
        barcode.setFont(font);
    }
    /**
     * �õ�����
     * @return Font
     */
    public Font getFont()
    {
        return barcode.getFont();
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
     * ��ʾ����
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
     * ����
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
     * ��ӡ
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
     * �õ����ԶԻ�������
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VBarCodeDialog.x";
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
        s.writeInt(3,getX(),0);
        s.writeInt(4,getY(),0);
        s.writeString(5,getText(),"");
        s.writeInt(6,getSymbologyID(),13);//0->13 BarCode Ĭ����13 Code128
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
        //��ȡ��Ŀ
        int count = s.readInt();
    }
}
