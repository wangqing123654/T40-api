package com.dongyang.tui.text.div;

import java.awt.FontMetrics;
import com.dongyang.util.TList;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * <p>Title: �������ֻ���λ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.20
 * @version 1.0
 */
public class CStringDraw
{
    /**
     * ����
     */
    private String text;
    /**
     * �趨���
     */
    private int width;
    /**
     * �趨�߶�
     */
    private int height;
    /**
     * ���и߶�
     */
    private int h;
    /**
     * ���б�
     */
    private TList rows = new TList();
    /**
     * ��ͼ�豸
     */
    private Graphics g;
    /**
     * ���ú�����
     */
    private int x;
    /**
     * ����������
     */
    private int y;
    /**
     * ���ű���
     */
    private double zoom;
    /**
     * ����ԭʼ����ģ��
     */
    private FontMetrics fontMetrics;
    /**
     * �»���
     */
    private boolean isLine;
    /**
     * λ��
     * 0 ����
     * 1 ����
     * 2 ����
     */
    private int alignment;
    /**
     * ����λ��
     * 0 ����
     * 1 ����
     * 2 ����
     */
    private int alignmentH;
    /**
     * �Զ����и߶�
     */
    private int autoEnterHeight;
    /**
     * ����ģʽ
     */
    private int enterMode;
    /**
     * �Ƿ��ӡ
     */
    private boolean isPrint;
    /**
     * ����Ĳ���ʾ
     */
    private boolean isDelOrder;
    /**
     * ������2
     * @param g Graphics
     * @param fontMetrics FontMetrics
     * @param x int
     * @param y int
     * @param width int
     * @param text String
     * @param zoom double
     * @param autoEnterHeight int
     * @param isLine boolean
     * @param alignment int
     * @param enterMode int
     * @param delOrder boolean
     * @param isPrint boolean
     */
    public CStringDraw(Graphics g,FontMetrics fontMetrics,int x,int y,
                       int width,String text,double zoom,int autoEnterHeight,
                       boolean isLine,int alignment,int enterMode,boolean delOrder,boolean isPrint)
    {
        setGraphics(g);
        setFontMetrics(fontMetrics);
        setX(x);
        setY(y);
        setWidth(width);
        setText(text);
        setZoom(zoom);
        setAutoEnterHeight(autoEnterHeight);
        setIsLine(isLine);
        setEnterMode(enterMode);
        setAlignment(alignment);
        setIsPrint(isPrint);
        setDelOrder(delOrder);

        checkText();
        checkAlignment();
    }
    /**
     * ������1
     * @param g Graphics
     * @param fontMetrics FontMetrics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text String
     * @param zoom double
     * @param autoEnterHeight int
     * @param isLine boolean
     * @param alignment int
     * @param alignmentH int
     * @param enterMode int
     * @param isPrint boolean
     */
    public CStringDraw(Graphics g,FontMetrics fontMetrics,int x,int y,
                       int width,int height,String text,double zoom,int autoEnterHeight,
                       boolean isLine,int alignment,int alignmentH,int enterMode,boolean isPrint)
    {
        setGraphics(g);
        setFontMetrics(fontMetrics);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setText(text);
        setZoom(zoom);
        setAutoEnterHeight(autoEnterHeight);
        setIsLine(isLine);
        setEnterMode(enterMode);
        setAlignment(alignment);
        setAlignmentH(alignmentH);
        setIsPrint(isPrint);

        checkText();
        checkAlignment();
        checkAlignmentH();
    }
    /**
     * ��������λ��
     */
    public void checkAlignmentH()
    {
        if(rows.size() == 0)
            return;
        int end = ((CStringRow)rows.get(rows.size() - 1)).getY();
        int charHeight = charHeight();
        if(getAlignmentH() == 0)
        {
            setY(getY() + charHeight);
            return;
        }
        if(getAlignmentH() == 1)
        {
            setY((getHeight() - end + charHeight) / 2 + getY());
            return;
        }
        if(getAlignmentH() == 2)
        {
            setY(getY() + getHeight() - end - charHeight / 3);
            return;
        }
    }
    /**
     * ����λ��
     */
    public void checkAlignment()
    {
        //����
        if(getAlignment() == 0)
            return;
        //����
        if(getAlignment() == 1)
        {
            for(int i = 0;i < rows.size();i++)
            {
                CStringRow row = (CStringRow) rows.get(i);
                int x0 = (int)(((double)getWidth() - (double)row.getWidth()) / 2.0);
                for(int j = 0;j < row.getText().length();j++)
                    row.getX0()[j] += x0;
                row.setX(row.getX() + x0);
            }
            return;
        }
        //����
        if(getAlignment() == 2)
        {
            for(int i = 0;i < rows.size();i++)
            {
                CStringRow row = (CStringRow) rows.get(i);
                int x0 = getWidth() - row.getWidth();
                for(int j = 0;j < row.getText().length();j++)
                    row.getX0()[j] += x0;
                row.setX(row.getX() + x0);
            }
            return;
        }
    }
    /**
     * ����
     */
    public void draw()
    {
        for(int i = 0;i < rows.size();i++)
        {
            CStringRow row = (CStringRow)rows.get(i);
            for(int j = 0;j < row.getText().length();j++)
                getGraphics().drawString("" + row.getText().charAt(j),getX() + row.getX0()[j],getY() + row.getY());
            //�����»���
            if (isLine())
                getGraphics().drawLine(getX() + row.getX(),getY() + row.getY() + 2,
                                       getX() + row.getX() + row.getWidth(),getY() + row.getY() + 2);
        }
    }
    /**
     * ��������
     */
    public void checkText()
    {
        switch(getEnterMode())
        {
        case 0:
            if(getWidth() == 0)
            {
                checkOneRow();
                return;
            }
            checkRow();
            return;
        case 1:
            checkAutoEnter();
            return;
        case 2:
            checkOneRow();
            return;
        }
    }
    /**
     * �����Զ�����
     */
    private void checkAutoEnter()
    {
        int y = 0;
        for (int i = 0; i < getText().length(); i++)
        {
            //�ַ�
            char c = getText().charAt(i);
            //�ַ����
            int charw = charWidth(c);
            CStringRow row = new CStringRow();
            row.setText("" + c);
            row.setWidth(cZoom(charw));
            row.setX0(new int[]{0});
            row.setY(y);
            rows.add(row);
            y += cZoom(getAutoEnterHeight());
        }
    }
    /**
     * ���Զ���
     */
    private void checkRow()
    {
        TList list = new TList();
        int cw = 0;
        int y = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getText().length(); i++)
        {
            //�ַ�
            char c = getText().charAt(i);
            if(c == '\n')
            {
                CStringRow row = new CStringRow();
                row.setText(sb.toString());
                row.setWidth(cZoom(cw));
                row.setX0(list);
                row.setY(y);
                rows.add(row);
                y += cZoom(getAutoEnterHeight());
                cw = 0;
                list = new TList();
                sb = new StringBuffer();
                continue;
            }
            //�ַ����
            int charw = charWidth(c);
            //ʹ�ÿ��
            int w = cZoom(cw + charw);
            if(w < getWidth())
            {
                sb.append(c);
                list.add(cZoom(cw));
                cw += charw;
                continue;
            }
            if(sb.length() > 0)
            {
                CStringRow row = new CStringRow();
                row.setText(sb.toString());
                row.setWidth(cZoom(cw));
                row.setX0(list);
                row.setY(y);
                rows.add(row);
                y += cZoom(getAutoEnterHeight());
            }
            cw = charw;
            list = new TList();
            sb = new StringBuffer();
            sb.append(c);
            list.add(0);
        }
        if(sb.length() > 0)
        {
            CStringRow row = new CStringRow();
            row.setText(sb.toString());
            row.setWidth(cZoom(cw));
            row.setX0(list);
            row.setY(y);
            rows.add(row);
        }
        if(isDelOrder())
        {
            for(int i = rows.size() - 1;i > 0;i--)
                rows.remove(i);
        }
    }
    /**
     * ����һ������
     */
    private void checkOneRow()
    {
        TList list = new TList();
        int cw = 0;
        StringBuffer sb = new StringBuffer();
        int y = 0;
        for (int i = 0; i < getText().length(); i++)
        {
            //�ַ�
            char c = getText().charAt(i);
            if(c == '\n')
            {
                CStringRow row = new CStringRow();
                row.setText(sb.toString());
                row.setWidth(cZoom(cw));
                row.setX0(list);
                row.setY(y);
                rows.add(row);
                sb = new StringBuffer();
                list = new TList();
                cw = 0;
                y += cZoom(getAutoEnterHeight());
                continue;
            }
            sb.append(c);
            list.add(cZoom(cw));
            cw += charWidth(getText().charAt(i));
        }
        CStringRow row = new CStringRow();
        row.setText(sb.toString());
        row.setWidth(cZoom(cw));
        row.setX0(list);
        row.setY(y);
        rows.add(row);
    }
    /**
     * �����ַ����
     * @param c char
     * @return int
     */
    private int charWidth(char c)
    {
        return getFontMetrics().charWidth(c);
    }
    /**
     * ����߶�
     * @return int
     */
    private int charHeight()
    {
        return getFontMetrics().getHeight();
    }
    /**
     * �������ű���
     * @param x int
     * @return int
     */
    private int cZoom(int x)
    {
        if(isPrint)
            return x;
        return (int)(x * getZoom() / 75.0 + 0.5);
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
     * �����趨���
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * �õ��趨���
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * ���õ��и߶�
     * @param h int
     */
    public void setH(int h)
    {
        this.h = h;
    }
    /**
     * �õ����õ��и߶�
     * @return int
     */
    public int getH()
    {
        return h;
    }
    /**
     * ����ԭʼ����ģ��
     * @param fontMetrics FontMetrics
     */
    public void setFontMetrics(FontMetrics fontMetrics)
    {
        this.fontMetrics = fontMetrics;
    }
    /**
     * �õ�����ԭʼ����ģ��
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics()
    {
        return fontMetrics;
    }
    /**
     * ���û�ͼ�豸
     * @param g Graphics
     */
    public void setGraphics(Graphics g)
    {
        this.g = g;
    }
    /**
     * �õ���ͼ�豸
     * @return Graphics
     */
    public Graphics getGraphics()
    {
        return g;
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
     * �õ�Y
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * �������ű���
     * @param zoom double
     */
    public void setZoom(double zoom)
    {
        this.zoom = zoom;
    }
    /**
     * �õ����ű���
     * @return double
     */
    public double getZoom()
    {
        return zoom;
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
     * ���û���ģʽ
     * @param enterMode int
     */
    public void setEnterMode(int enterMode)
    {
        this.enterMode = enterMode;
    }
    /**
     * �õ�����ģʽ
     * @return int
     */
    public int getEnterMode()
    {
        return enterMode;
    }
    /**
     * �����Ƿ��ӡ
     * @param isPrint boolean
     */
    public void setIsPrint(boolean isPrint)
    {
        this.isPrint = isPrint;
    }
    /**
     * �Ƿ��ӡ
     * @return boolean
     */
    public boolean isPrint()
    {
        return isPrint;
    }
}
