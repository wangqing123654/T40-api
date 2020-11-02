package com.dongyang.ui.base;

import javax.swing.JComponent;
import java.util.GregorianCalendar;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import com.dongyang.util.StringTool;
import com.dongyang.util.DateTool;

/**
 *
 * <p>Title: �����б����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.4
 * @version 1.0
 */
public class TDateListBase extends TTextComponentBase
{
    private static final String DAYS[] = {"��", "һ", "��", "��","��", "��", "��"};
    private static final String EN_DAYS[] = {"SUN", "MON", "TUE", "WED","THU", "FRI", "SAT"};
    private static final int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    /**
     * ��Ԫ����
     */
    private int cellWidth = 23;
    /**
     * ��Ԫ��߶�
     */
    private int cellHeight = 16;
    /**
     * ��
     */
    private int year = 2009;
    /**
     * ��
     */
    private int month = 2;
    /**
     * ����
     */
    private int day = 1;
    /**
     * ���ⱳ����ɫ
     */
    private Color headBackground = new Color(122,150,223);
    /**
     * ����ǰ����ɫ
     */
    private Color headForeground = new Color(255,255,255);
    /**
     * ѡ����ɫ
     */
    private Color selectedColor = new Color(0,84,227);
    /**
     * ����߶�
     */
    private int headHeight = 18;
    /**
     * ���ڿ��б�
     */
    private List blocks = new ArrayList();
    /**
     * ����
     */
    private String language;
    /**
     * ������
     */
    public TDateListBase()
    {
        setBackground(new Color(255,255,255));
    }
    /**
     * ������
     * @param year int
     */
    public void setYear(int year)
    {
        this.year = year;
        repaint();
    }
    /**
     * �õ���
     * @return int
     */
    public int getYear()
    {
        return year;
    }
    /**
     * ������
     * @param month int
     */
    public void setMonth(int month)
    {
        this.month = month;
        repaint();
    }
    /**
     * �õ���
     * @return int
     */
    public int getMonth()
    {
        return month;
    }
    /**
     * ��������
     * @param day int
     */
    public void setDay(int day)
    {
        this.day = day;
        repaint();
    }
    /**
     * �õ�����
     * @return int
     */
    public int getDay()
    {
        return day;
    }
    /**
     * ���ñ��ⱳ����ɫ
     * @param headBackground Color
     */
    public void setHeadBackground(Color headBackground)
    {
        this.headBackground = headBackground;
    }
    /**
     * ���ñ��ⱳ����ɫ
     * @param headBackground String
     */
    public void setHeadBackground(String headBackground) {
        if(headBackground == null || headBackground.length() == 0)
            return;
        setHeadBackground(StringTool.getColor(headBackground, getConfigParm()));
    }
    /**
     * �õ����ⱳ����ɫ
     * @return Color
     */
    public Color getHeadBackground()
    {
        return headBackground;
    }
    /**
     * ���ñ���ǰ����ɫ
     * @param headForeground Color
     */
    public void setHeadForeground(Color headForeground)
    {
        this.headForeground = headForeground;
    }
    /**
     * ���ñ���ǰ����ɫ
     * @param headForeground String
     */
    public void setHeadForeground(String headForeground) {
        if(headForeground == null || headForeground.length() == 0)
            return;
        setHeadForeground(StringTool.getColor(headForeground, getConfigParm()));
    }
    /**
     * �õ�����ǰ����ɫ
     * @return Color
     */
    public Color getHeadForeground()
    {
        return headForeground;
    }
    /**
     * ����ѡ����ɫ
     * @param selectedColor Color
     */
    public void setSelectedColor(Color selectedColor)
    {
        this.selectedColor = selectedColor;
    }
    /**
     * ����ѡ����ɫ
     * @param selectedColor String
     */
    public void setSelectedColor(String selectedColor) {
        if(selectedColor == null || selectedColor.length() == 0)
            return;
        setSelectedColor(StringTool.getColor(selectedColor, getConfigParm()));
    }
    /**
     * �õ�ѡ����ɫ
     * @return Color
     */
    public Color getSelectedColor()
    {
        return selectedColor;
    }
    /**
     * ���ñ���߶�
     * @param headHeight int
     */
    public void setHeadHeight(int headHeight)
    {
        this.headHeight = headHeight;
    }
    /**
     * �õ�����߶�
     * @return int
     */
    public int getHeadHeight()
    {
        return headHeight;
    }
    /**
     * �ػ�
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        Insets insets = getInsets();
        g.setColor(this.getBackground());
        g.fillRect(insets.left,insets.top,
                   getWidth() - insets.left - insets.right,
                   getHeight() - insets.top - insets.bottom);
        if(getHeadBackground() != null)
        {
            g.setColor(getHeadBackground());
            g.fillRect(insets.left,insets.top,
                       getWidth() - insets.left - insets.right
                       ,getHeadHeight());
        }
        FontMetrics fontMetric = g.getFontMetrics();
        int fontAscent = fontMetric.getAscent();
        int headHeight = (getHeadHeight() + fontAscent) / 2;
        if(getHeadForeground() != null)
        {
            g.setColor(getHeadForeground());
            for (int i = 0; i < 7; i++)
            {
                String s = "en".equals(language)?EN_DAYS[i]:DAYS[i];
                g.drawString(s,
                             (cellWidth - fontMetric.stringWidth(DAYS[i])) / 2 +
                             i * cellWidth + insets.left,
                             headHeight + insets.top);
            }
        }
        g.setColor(getForeground());
        initBlock();
        for(int i = 0;i < blocks.size();i++)
        {
            Block b = (Block)(blocks.get(i));
            Color fColor = getForeground();
            if(b.month != getMonth())
                fColor = new Color(128,128,128);
            if(getYear() == b.year && getMonth() == b.month && getDay() == b.day)
            {
                g.setColor(getSelectedColor());
                g.fillRect(insets.left + b.x + 2,insets.top + getHeadHeight() + b.y + 3,cellWidth - 8,cellHeight - 3);
                fColor = new Color(255,255,255);
            }else if(b.column == 0 && b.month == getMonth())
                fColor = new Color(255,0,0);
            else if(b.column == 6 && b.month == getMonth())
                fColor = new Color(0,255,0);
            String dayStr = Integer.toString(b.day);
            int xNum = b.x + cellWidth - fontMetric.stringWidth(dayStr) - 7;
            int yNum = getHeadHeight() + (cellHeight + fontAscent) / 2 + b.y;
            g.setColor(fColor);
            g.drawString(dayStr,insets.left + xNum,insets.top + yNum);
        }
    }
    /**
     * ��ʼ�����ڿ�
     */
    private void initBlock()
    {
        blocks = new ArrayList();
        int column = getWeek();
        int numDays = daysInMonth[getMonth() - 1] + (getMonth() == 2 && DateTool.isLeapYear(getYear()) ? 1 : 0);
        int pMonth = getMonth() - 1;
        int nMonth = getMonth() + 1;
        int pYear = getYear();
        int nYear = getYear();
        if(pMonth < 1)
        {
            pYear --;
            pMonth = 12;
        }
        if(nMonth > 12)
        {
            nYear ++;
            nMonth = 1;
        }
        int pumDays = daysInMonth[pMonth - 1] + (pMonth == 2 && DateTool.isLeapYear(pYear) ? 1 : 0);
        int row = 0;
        if(column == 0)
        {
            for(int i = 0;i < 7;i++)
            {
                Block b = new Block();
                b.year = pYear;
                b.month = pMonth;
                b.day = pumDays - 6 + i;
                b.row = row;
                b.column = i;
                b.x = i * cellWidth;
                b.y = row * cellHeight;
                blocks.add(b);
            }
            row ++;
        }else
        {
            for(int i = 0;i < column;i++)
            {
                Block b = new Block();
                b.year = pYear;
                b.month = pMonth;
                b.day = pumDays - column + 1 + i;
                b.row = row;
                b.column = i;
                b.x = i * cellWidth;
                b.y = row * cellHeight;
                blocks.add(b);
            }
        }
        if(getDay() > numDays)
        {
            setDay(numDays);
            exeAction(getChangedAction());
        }
        for(int i = 1;i <= numDays;i++)
        {
            Block b = new Block();
            b.year = getYear();
            b.month = getMonth();
            b.day = i;
            b.row = row;
            b.column = column;
            b.x = column * cellWidth;
            b.y = row * cellHeight;
            blocks.add(b);
            column ++;
            if(column > 6)
            {
                column = 0;
                row++;
            }
        }
        if(column == 0)
        {
            for(int i = 0;i < 7;i++)
            {
                Block b = new Block();
                b.year = nYear;
                b.month = nMonth;
                b.day = i + 1;
                b.row = row;
                b.column = i;
                b.x = i * cellWidth;
                b.y = row * cellHeight;
                blocks.add(b);
            }
            row ++;
        }else
        {
            for(int i = column;i < 7;i++)
            {
                Block b = new Block();
                b.year = nYear;
                b.month = nMonth;
                b.day = i  - column + 1;
                b.row = row;
                b.column = i;
                b.x = i * cellWidth;
                b.y = row * cellHeight;
                blocks.add(b);
            }
            row ++;
            if(row == 5)
                for(int i = 0;i < 7;i++)
                {
                    Block b = new Block();
                    b.year = nYear;
                    b.month = nMonth;
                    b.day = i + 1 +  (7 - column);
                    b.row = row;
                    b.column = i;
                    b.x = i * cellWidth;
                    b.y = row * cellHeight;
                    blocks.add(b);
                }
        }
    }
    /**
     * �����������ڵĿ�
     * @param x int
     * @param y int
     * @return int
     */
    private int findBlock(int x,int y)
    {
        Insets insets = getInsets();
        for (int i = 0; i < blocks.size(); i++)
        {
            Block b = (Block) (blocks.get(i));
            int bx = insets.left + b.x;
            int by = insets.top + getHeadHeight() + b.y;
            if(x >= bx && x <= bx + cellWidth && y >= by && y <= by + cellHeight)
                return i;
        }
        return -1;
    }
    /**
     * �õ�������
     * @return int
     */
    private int maxRow()
    {
        return ((Block)blocks.get(blocks.size() - 1)).row;
    }
    /**
     * �õ���ǰ��Block
     * @return Block
     */
    public Block getBlockNow()
    {
        for(int i = 0;i < blocks.size();i++)
        {
            Block b = (Block) (blocks.get(i));
            if(b.year == getYear() && b.month == getMonth() && b.day == getDay())
                return b;
        }
        return null;
    }
    /**
     * ���ҿ�
     * @param key int
     * @return int
     */
    private int findBlock(int key)
    {
        Block b = getBlockNow();
        if(b == null)
            return -1;
        int row = b.row;
        int column = b.column;
        switch(key)
        {
        case KeyEvent.VK_UP:
            row--;
            if(row < 0)
                row = 0;
            break;
        case KeyEvent.VK_DOWN:
            row ++;
            if(row > maxRow())
                row = maxRow();
            break;
        case KeyEvent.VK_LEFT:
            column --;
            if(column < 0)
            {
                row --;
                column = 6;
                if(row < 0)
                    row = 0;
            }
            break;
        case KeyEvent.VK_RIGHT:
            column ++;
            if(column > 6)
            {
                column = 0;
                row ++;
                if(row > maxRow())
                    row = maxRow();
            }
        }
        for (int i = 0; i < blocks.size(); i++)
        {
            Block bt = (Block) (blocks.get(i));
            if(bt.row == row && bt.column == column)
                return i;
        }
        return -1;
    }
    /**
     * ��������
     * @return int
     */
    private int getWeek()
    {
        return getWeek(getYear(),getMonth(),1);
    }
    /**
     * ��������
     * @param y int ��
     * @param m int ��
     * @param d int ��
     * @return int
     */
    private int getWeek(int y,int m,int d)
    {
        d+=m<3?y--:y-2;
        return (23*m/9+d+4+y/4-y/100+y/400)%7;
    }
    class Block
    {
        int row;
        int column;
        int x;
        int y;
        int day;
        int month;
        int year;
    }
    /**
     * ˫��
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {
        int index = findBlock(e.getX(),e.getY());
        if(index != -1)
        {
            setYear(getBlockYear(index));
            setMonth(getBlockMonth(index));
            setDay(getBlockDay(index));
            repaint();
            exeAction(getActionMessage());
        }
    }
    /**
     * �õ���
     * @param index int
     * @return int
     */
    public int getBlockYear(int index)
    {
        return ((Block)blocks.get(index)).year;
    }
    /**
     * �õ���
     * @param index int
     * @return int
     */
    public int getBlockMonth(int index)
    {
        return ((Block)blocks.get(index)).month;
    }
    /**
     * �õ���
     * @param index int
     * @return int
     */
    public int getBlockDay(int index)
    {
        return ((Block)blocks.get(index)).day;
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        int index = findBlock(e.getX(),e.getY());
        if(index == -1)
            return;
        int y = getBlockYear(index);
        int m = getBlockMonth(index);
        int d = getBlockDay(index);
        if(y != getYear() || m != getMonth() || d != getDay())
        {
            setYear(y);
            setMonth(m);
            setDay(d);
            repaint();
            exeAction(getChangedAction());
        }
    }
    /**
     * ���̰���
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch(keyCode)
        {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_RIGHT:
            int index = findBlock(keyCode);
            if(index == -1)
                break;
            int y = getBlockYear(index);
            int m = getBlockMonth(index);
            int d = getBlockDay(index);
            if(y != getYear() || m != getMonth() || d != getDay())
            {
                setYear(y);
                setMonth(m);
                setDay(d);
                repaint();
                exeAction(getChangedAction());
            }
            break;
        case KeyEvent.VK_ENTER:
            exeAction(getActionMessage());
        }
    }
    /**
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if (language == null)
            return;
        if (language.equals(this.language))
            return;
        this.language = language;
        repaint();
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TDateListBase";
    }
}
