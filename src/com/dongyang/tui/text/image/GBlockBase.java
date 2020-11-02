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
     * ��ʾ
     */
    private boolean visible = true;
    /**
     * ����
     */
    private String name;
    private int x;
    private int y;
    private int width;
    private int height;
    /**
     * ��ɫ
     */
    private Color color = new Color(0,0,0);
    /**
     * ������ɫ
     */
    private Color bkColor;
    /**
     * �߿���ɫ
     */
    private Color borderColor = new Color(0,0,0);
    /**
     * ����
     */
    private Font font = new Font("����",0,10);
    /**
     * ��ת�Ƕ�
     */
    private double fontRotate = 0.0;
    /**
     * λ��
     * 0 ����
     * 1 ����
     * 2 ����
     */
    private int alignment = 1;
    /**
     * ����λ��
     * 0 ����
     * 1 ����
     * 2 ����
     */
    private int alignmentH = 1;
    /**
     * �»���
     */
    private boolean isLine;
    /**
     * �Զ����и߶�
     */
    private int autoEnterHeight = 15;
    /**
     * ��ʾ�߿�
     */
    private boolean borderVisible = true;
    /**
     * �߿��߸�
     */
    private float borderWidth = 1.0f;
    /**
     * �߿�������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int borderLineType = 0;
    /**
     * ͼƬ����
     */
    private String pictureName;
    private Icon icon;
    private boolean iconInit;
    /**
     * �Զ�����ߴ�
     */
    private boolean isPictureAutoSize;
    /**
     * ������
     */
    private boolean lineFX,lineFY;
    /**
     * ���Ƶ��ж�
     */
    private int moveF = 511;
    /**
     * ��ͷ1
     */
    private int arrow1 = 0;
    /**
     * ��ͷ2
     */
    private int arrow2 = 0;
    /**
     * ��ͷ1����
     */
    private int arrow1Length = 15;
    /**
     * ��ͷ2����
     */
    private int arrow2Length = 15;
    /**
     * ��ͷ1����
     */
    private double arrow1Degree = 15.0;
    /**
     * ��ͷ2����
     */
    private double arrow2Degree = 15.0;
    /**
     * �ı�����ģʽ
     */
    private int textEnterMode;
    /**
     * �ı�
     */
    private String text = "";
    /**
     * �ܷ�༭
     */
    private boolean enabled = true;
    /**
     * ����
     */
    private Vector data = new Vector();
    /**
     * ������ʾ
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    /**
     * �Ƿ���ʾ
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
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
     * ���ñ�����ɫ
     * @param bkColor Color
     */
    public void setBkColor(Color bkColor)
    {
        this.bkColor = bkColor;
    }
    /**
     * �õ�������ɫ
     * @return Color
     */
    public Color getBkColor()
    {
        return bkColor;
    }
    /**
     * ���ñ߿���ɫ
     * @param borderColor Color
     */
    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }
    /**
     * �õ��߿���ɫ
     * @return Color
     */
    public Color getBorderColor()
    {
        return borderColor;
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
    public Font getDrawFont(Font font)
    {
        if(getFontRotate() == 0.0)
            return font;
        AffineTransform fontAT = new AffineTransform();
        fontAT.rotate(Math.toRadians(getFontRotate()));
        return font.deriveFont(fontAT);
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
     * ���ö��䷽ʽ
     * 0 ����
     * 1 ����
     * 2 ����
     * @param alignmentH int
     */
    public void setAlignmentH(int alignmentH)
    {
        this.alignmentH = alignmentH;
    }
    /**
     * �õ����䷽ʽ
     * 0 ����
     * 1 ����
     * 2 ����
     * @return int
     */
    public int getAlignmentH()
    {
        return alignmentH;
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
     * �����Ƿ���ʾ�߿�
     * @param borderVisible boolean
     */
    public void setBorderVisible(boolean borderVisible)
    {
        this.borderVisible = borderVisible;
    }
    /**
     * �Ƿ���ʾ�߿�
     * @return boolean
     */
    public boolean isBorderVisible()
    {
        return borderVisible;
    }
    /**
     * ���ñ߿��߸�
     * @param borderWidth float
     */
    public void setBorderWidth(float borderWidth)
    {
        this.borderWidth = borderWidth;
    }
    /**
     * �õ��߿��߸�
     * @return float
     */
    public float getBorderWidth()
    {
        return borderWidth;
    }
    /**
     * ���ñ߿�������
     * @param borderLineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setBorderLineType(int borderLineType)
    {
        this.borderLineType = borderLineType;
    }
    /**
     * �õ��߿�������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getBorderLineType()
    {
        return borderLineType;
    }
    /**
     * �õ������Ͷ���
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
     * ���ð�ťͼƬ
     * @param name ͼƬ����
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
     * �õ���ťͼƬ
     * @return ͼƬ����
     */
    public String getPictureName()
    {
      return pictureName;
    }
    /**
     * �õ�ͼ��
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
     * ����ͼƬ
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
                System.out.println("û���ҵ�ͼ��" + filename);
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
            System.out.println("û���ҵ�ͼ��" + path);
        }
        return icon;
    }
    /**
     * ����ͼƬ�Զ�����ߴ�
     * @param isPictureAutoSize boolean
     */
    public void setPictureAutoSize(boolean isPictureAutoSize)
    {
        this.isPictureAutoSize = isPictureAutoSize;
    }
    /**
     * ͼƬ�Ƿ��Զ�����ߴ�
     * @return boolean
     */
    public boolean isPictureAutoSize()
    {
        return isPictureAutoSize;
    }
    /**
     * ���÷�����
     * @param lineFX boolean
     */
    public void setLineFX(boolean lineFX)
    {
        this.lineFX = lineFX;
    }
    /**
     * �õ�������
     * @return boolean
     */
    public boolean isLineFX()
    {
        return lineFX;
    }
    /**
     * ���÷�����
     * @param lineFY boolean
     */
    public void setLineFY(boolean lineFY)
    {
        this.lineFY = lineFY;
    }
    /**
     * �õ�������
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
     * ����������������ڲ�
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
     * ���ÿ��Ա༭
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    /**
     * �Ƿ�ɱ༭
     * @return boolean
     */
    public boolean isEnabled()
    {
        return enabled;
    }
    /**
     * ���ƶ���
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
     * ��������
     * @param data Vector
     */
    public void setData(Vector data)
    {
        if(data == null)
            data = new Vector();
        this.data = data;
    }
    /**
     * �õ�����
     * @return Vector
     */
    public Vector getData()
    {
        return data;
    }
    /**
     * ���ݸ���
     * @return int
     */
    public int sizeData()
    {
        return data.size();
    }
    /**
     * ��������
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
     * ��������
     * @param index int λ��
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
     * ɾ������
     * @param index int
     */
    public void removeData(int index)
    {
        getData().remove(index);
    }
    /**
     * �õ�����
     * @param index int
     * @return Vector
     */
    public Vector getData(int index)
    {
        return (Vector)getData().get(index);
    }
    /**
     * �õ���������
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
     * �õ�����ֵ
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
     * ɾ������
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
