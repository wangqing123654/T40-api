package com.dongyang.tui.text.div;

import java.awt.FontMetrics;
import com.dongyang.util.TList;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * <p>Title: 计算文字绘制位置</p>
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
     * 文字
     */
    private String text;
    /**
     * 设定宽度
     */
    private int width;
    /**
     * 设定高度
     */
    private int height;
    /**
     * 单行高度
     */
    private int h;
    /**
     * 行列表
     */
    private TList rows = new TList();
    /**
     * 绘图设备
     */
    private Graphics g;
    /**
     * 设置横坐标
     */
    private int x;
    /**
     * 设置纵坐标
     */
    private int y;
    /**
     * 缩放比例
     */
    private double zoom;
    /**
     * 设置原始字体模型
     */
    private FontMetrics fontMetrics;
    /**
     * 下划线
     */
    private boolean isLine;
    /**
     * 位置
     * 0 居左
     * 1 居中
     * 2 居右
     */
    private int alignment;
    /**
     * 纵向位置
     * 0 居上
     * 1 居中
     * 2 居下
     */
    private int alignmentH;
    /**
     * 自动这行高度
     */
    private int autoEnterHeight;
    /**
     * 换行模式
     */
    private int enterMode;
    /**
     * 是否打印
     */
    private boolean isPrint;
    /**
     * 多余的不显示
     */
    private boolean isDelOrder;
    /**
     * 构造器2
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
     * 构造器1
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
     * 处理纵向位置
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
     * 处理位置
     */
    public void checkAlignment()
    {
        //居左
        if(getAlignment() == 0)
            return;
        //居中
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
        //居中
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
     * 绘制
     */
    public void draw()
    {
        for(int i = 0;i < rows.size();i++)
        {
            CStringRow row = (CStringRow)rows.get(i);
            for(int j = 0;j < row.getText().length();j++)
                getGraphics().drawString("" + row.getText().charAt(j),getX() + row.getX0()[j],getY() + row.getY());
            //绘制下划线
            if (isLine())
                getGraphics().drawLine(getX() + row.getX(),getY() + row.getY() + 2,
                                       getX() + row.getX() + row.getWidth(),getY() + row.getY() + 2);
        }
    }
    /**
     * 测试字体
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
     * 测试自动这行
     */
    private void checkAutoEnter()
    {
        int y = 0;
        for (int i = 0; i < getText().length(); i++)
        {
            //字符
            char c = getText().charAt(i);
            //字符宽度
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
     * 测试多行
     */
    private void checkRow()
    {
        TList list = new TList();
        int cw = 0;
        int y = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getText().length(); i++)
        {
            //字符
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
            //字符宽度
            int charw = charWidth(c);
            //使用宽度
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
     * 测试一行数据
     */
    private void checkOneRow()
    {
        TList list = new TList();
        int cw = 0;
        StringBuffer sb = new StringBuffer();
        int y = 0;
        for (int i = 0; i < getText().length(); i++)
        {
            //字符
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
     * 计算字符宽度
     * @param c char
     * @return int
     */
    private int charWidth(char c)
    {
        return getFontMetrics().charWidth(c);
    }
    /**
     * 计算高度
     * @return int
     */
    private int charHeight()
    {
        return getFontMetrics().getHeight();
    }
    /**
     * 计算缩放比例
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
     * 设置设定宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * 得到设定宽度
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * 设置单行高度
     * @param h int
     */
    public void setH(int h)
    {
        this.h = h;
    }
    /**
     * 得到设置单行高度
     * @return int
     */
    public int getH()
    {
        return h;
    }
    /**
     * 设置原始字体模型
     * @param fontMetrics FontMetrics
     */
    public void setFontMetrics(FontMetrics fontMetrics)
    {
        this.fontMetrics = fontMetrics;
    }
    /**
     * 得到设置原始字体模型
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics()
    {
        return fontMetrics;
    }
    /**
     * 设置绘图设备
     * @param g Graphics
     */
    public void setGraphics(Graphics g)
    {
        this.g = g;
    }
    /**
     * 得到绘图设备
     * @return Graphics
     */
    public Graphics getGraphics()
    {
        return g;
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
     * 得到Y
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置缩放比例
     * @param zoom double
     */
    public void setZoom(double zoom)
    {
        this.zoom = zoom;
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom()
    {
        return zoom;
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
     * 设置换行模式
     * @param enterMode int
     */
    public void setEnterMode(int enterMode)
    {
        this.enterMode = enterMode;
    }
    /**
     * 得到换行模式
     * @return int
     */
    public int getEnterMode()
    {
        return enterMode;
    }
    /**
     * 设置是否打印
     * @param isPrint boolean
     */
    public void setIsPrint(boolean isPrint)
    {
        this.isPrint = isPrint;
    }
    /**
     * 是否打印
     * @return boolean
     */
    public boolean isPrint()
    {
        return isPrint;
    }
}
