package com.dongyang.ui.base;

import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import com.dongyang.ui.TUIStyle;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import com.dongyang.util.StringTool;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import com.dongyang.util.DateTool;
import com.dongyang.ui.util.TDrawTool;
import com.dongyang.ui.TPopupMenu;
import com.dongyang.ui.TDateEdit;
import java.awt.Component;
import com.dongyang.manager.TCM_Transform;
import java.util.Date;
import java.sql.Timestamp;
import java.awt.Cursor;
import com.dongyang.ui.TPanel;
import com.dongyang.ui.TTable;
import java.awt.Point;
import com.dongyang.util.ImageTool;
import java.awt.Dimension;
import java.awt.BorderLayout;
import com.dongyang.jdo.TJDODBTool;
import com.dongyang.data.TParm;
import java.util.Vector;
import com.dongyang.util.TList;
import com.dongyang.util.TypeTool;
import com.dongyang.ui.event.TTableEvent;
import com.dongyang.ui.event.TTextFormatEvent;
import java.awt.Font;
import com.dongyang.util.TSystem;

/**
 *
 * <p>Title: �ı��༭��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.13
 * @version 1.0
 */
public class TTextFormatBase extends TTextComponentBase
{
    /**
     * �ı�
     */
    private String text = "";
    /**
     * ��ʼ��
     */
    private int startPoint;
    /**
     * ��ǰ���λ��
     */
    private int cursorPoint;
    /**
     * ѡ�п�ʼλ��
     */
    private int selectStart;
    /**
     * ѡ�н���λ��
     */
    private int selectEnd;
    /**
     * ��ʾ���
     */
    private boolean showCursor;
    /**
     * �����˸�߳�
     */
    private CursorThread cursorThread;
    /**
     * �����˸����ʱ��
     */
    private int cursorSleepCount;
    /**
     * �����
     */
    private int leftWidth = 0;
    /**
     * �Ҳ���
     */
    private int rightWidth = 1;
    /**
     * ���䷽ʽ
     */
    private int horizontalAlignment;
    /**
     * �����ַ��ĳ���
     */
    private int inputLength;
    /**
     * ��ʽ
     */
    private String format;
    /**
     * ��ʽ����
     */
    private String formatType;
    /**
     * ����������ť
     */
    private boolean showDownButton = false;
    /**
     * ������ť���
     */
    private int downStyle;
    /**
     * ����ס������ť
     */
    private boolean downButtonMouseDown;
    /**
     * ������ť���
     */
    private static int downButtonWidth = 15;
    /**
     * �����˵�
     */
    private TPopupMenu popupMenu;
    /**
     * �����˵����
     */
    private TPanel panelPopupMenu;
    /**
     * �����˵�Table
     */
    private TTable tablePopupMenu;
    /**
     * �����˵����
     */
    private int popupMenuWidth = 200;
    /**
     * �����˵��߶�
     */
    private int popupMenuHeight = 100;
    /**
     * �����˵���ͷ
     */
    private String popupMenuHeader;
    /**
     * �����˵�Ӣ�ı�ͷ
     */
    private String popupMenuEnHeader;
    /**
     * �����˵�SQL
     */
    private String popupMenuSQL;
    /**
     * �����˵�����
     */
    private TParm popupMenuData;
    /**
     * �����˵���������
     */
    private String popupMenuFilter;
    /**
     * �����˵����������б�
     */
    private TList popupMenuFilterList;
    /**
     * ��ʾȫ������
     */
    private boolean popupMenuShowAllData;
    /**
     * ����ʱ����
     */
    private boolean inputPopupMenu = true;
    /**
     * ��ʾ��
     */
    private String showColumnList;
    /**
     * ֵ��
     */
    private String valueColumn;
    /**
     * ����Comboֵ
     */
    private String comboValue;
    /**
     * ��̬����
     */
    private boolean dynamicDownload;
    /**
     * ��̬�����߳�
     */
    private Thread dynamicDownloadThread;
    /**
     * ������ʾ�߳�
     */
    private Thread showDownloadThread;
    /**
     * ������ʾ�̻߳���״̬
     */
    private boolean showDownloadDrawVisible;
    /**
     * ��һ������
     */
    private String nextFocus;
    /**
     * �޸�SQL
     */
    private boolean isModifySQL = true;
    /**
     * ����һ������
     */
    private boolean hisOneNullRow = false;
    /**
     * Table����
     */
    public JTableBase tableBase;
    
    /**
     * Table����
     */
    public JTableSortBase tableSortBase;
    /**
     * ����
     */
    private String language;
    /**
     * �﷨����
     */
    private String languageMap;
    /**
     * ����
     */
    private Font tfont;

    /**
     * ���ݳ���;
     */
    private String dbPoolName;
    
    
    /**
     * ������
     */
    private boolean required;


    /**
     * ������
     */
    public TTextFormatBase()
    {
        setBackground(new Color(255,255,255));
        setTFont(TUIStyle.getTextFormatDefaultFont());
        setBorder(BorderFactory.createLineBorder(new Color(127,157,185), 1));
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        if(text == null)
            text = "";
        if(StringTool.equals(this.text,text))
            return;
        this.text = checkFormat(text)?text:"";
        initFormat();
        resetCursorSize();
        repaint();
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        String t = text;
        initFormat();
        if(!text.equals(t))
            repaint();
        return text;
    }

    /**
     * ���������
     * @param leftWidth int
     */
    public void setLeftWidth(int leftWidth)
    {
        this.leftWidth = leftWidth;
        repaint();
    }
    /**
     * �õ������
     * @return int
     */
    public int getLeftWidth()
    {
        return leftWidth;
    }
    /**
     * �����Ҳ���
     * @param rightWidth int
     */
    public void setRightWidth(int rightWidth)
    {
        this.rightWidth = rightWidth;
        repaint();
    }
    /**
     * �õ��Ҳ���
     * @return int
     */
    public int getRightWidth()
    {
        return rightWidth;
    }
    /**
     * ���ö��䷽ʽ
     * @param horizontalAlignment int
     */
    public void setHorizontalAlignment(int horizontalAlignment)
    {
        this.horizontalAlignment = horizontalAlignment;
        resetCursorSize();
        repaint();
    }
    /**
     * �õ����䷽ʽ
     * @return int
     */
    public int getHorizontalAlignment()
    {
        return horizontalAlignment;
    }
    /**
     * ���������ַ��ĳ���
     * @param inputLength int 0 ������
     */
    public void setInputLength(int inputLength)
    {
        this.inputLength = inputLength;
    }
    /**
     * �õ������ַ��ĳ���
     * @return int 0 ������
     */
    public int getInputLength()
    {
        return inputLength;
    }
    /**
     * ���ø�ʽ
     * @param format String
     */
    public void setFormat(String format)
    {
        this.format = format;
        initFormat();
        resetCursorSize();
        repaint();
    }
    /**
     * �õ���ʽ
     * @return String
     */
    public String getFormat()
    {
        return format;
    }
    /**
     * ���ø�ʽ����
     * @param formatType String
     */
    public void setFormatType(String formatType)
    {
        this.formatType = formatType;
        initFormat();
        resetCursorSize();
        repaint();
    }
    /**
     * �õ���ʽ����
     * @return String
     */
    public String getFormatType()
    {
        return formatType;
    }
    /**
     * ����ʧȥ���㶯��
     * @param action String
     */
    public void setFocusLostAction(String action)
    {
        actionList.setAction("focusLostAction",action);
    }
    /**
     * �õ�ʧȥ���㶯��
     * @return String
     */
    public String getFocusLostAction()
    {
        return actionList.getAction("focusLostAction");
    }
    /**
     * ���õ�������
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * ����˫������
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * �õ�˫������
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * �����һ�����
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * �õ��һ�����
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * ���ò���
     * @param value Object
     */
    public void setValue(Object value)
    {
        //����Comboֵ
        if(isShowDownButton() && "combo".equalsIgnoreCase(formatType))
        {
            String v = TypeTool.getString(value);
            setComboValue(v);
            setText(v);
            setComboSelectRow();
            return;
        }
        if(formatType == null || formatType.length() == 0)
            text = TCM_Transform.getString(value);
        else if("int".equalsIgnoreCase(formatType) ||
           "long".equalsIgnoreCase(formatType) ||
           "double".equalsIgnoreCase(formatType))
            text = TCM_Transform.getString(value);
        else if("date".equalsIgnoreCase(formatType))
        {
            if(format == null || format.length() == 0)
                format = "yyyy/MM/dd";
            if(value == null)
                text = "";
            else if(value instanceof String)
                text = (String)value;
            else if(value instanceof Timestamp)
                text = StringTool.getString((Timestamp)value,format);
            else if(value instanceof Date)
                text = StringTool.getString((Date)value,format);
        }
        initFormat();
        resetCursorSize();
        repaint();
    }
    /**
     * �õ�����
     * @return Object
     */
    public Object getValue()
    {
        //����Comboֵ
        if(isShowDownButton() && "combo".equalsIgnoreCase(formatType))
            return getComboValue();

        initFormat();
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        if(formatType == null || formatType.length() == 0)
            return text;
        if("int".equalsIgnoreCase(formatType))
            return StringTool.getInt(text);
        if("long".equalsIgnoreCase(formatType))
            return StringTool.getLong(text);
        if("double".equalsIgnoreCase(formatType))
            return StringTool.getDouble(text);
        if("date".equalsIgnoreCase(formatType) && text.length() > 0)
            try{
                return StringTool.getTimestamp(text, format);
            }catch(Exception e)
            {
            }
        return null;
    }
    /**
     * ������ʾ������ť
     * @param showDownButton boolean
     */
    public void setShowDownButton(boolean showDownButton)
    {
        this.showDownButton = showDownButton;
        rightWidth = showDownButton?downButtonWidth + 2:1;
        resetCursorSize();
        repaint();
    }
    /**
     * �Ƿ���ʾ������ť
     * @return boolean
     */
    public boolean isShowDownButton()
    {
        return showDownButton;
    }
    /**
     * ����int��ʽ
     * @param s String
     * @return boolean
     */
    public boolean checkIntFormat(String s)
    {
        if(format != null && format.length() > 0)
            if((s.startsWith("-")?s.length() - 1:s.length()) > format.length())
                return false;
        if("-".equals(s))
            return true;
        try{
            Integer.parseInt(s);
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
    /**
     * ����Long��ʽ
     * @param s String
     * @return boolean
     */
    public boolean checkLongFormat(String s)
    {
        if(format != null && format.length() > 0)
            if((s.startsWith("-")?s.length() - 1:s.length()) > format.length())
                return false;
        if("-".equals(s))
            return true;
        try{
            Long.parseLong(s);
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
    /**
     * ����С����ʽ
     * @param s String
     * @return boolean
     */
    public boolean checkDoubleFormat(String s)
    {
        if(format != null &&format.length() > 0)
        {
            int x1 = format.indexOf(".");
            int x2 = format.length() - x1 - 1;
            int s1 = s.indexOf(".");
            if(s1 == -1)
            {
                if((s.startsWith("-")?s.length() - 1:s.length()) > x1)
                    return false;
            }else{
                if((s.startsWith("-")?s1 - 1:s1) > x1)
                    return false;
                int s2 = s.length() - s1 - 1;
                if(s2 > x2)
                    return false;
            }
        }
        if("-".equals(s) || ".".equals(s) || "-.".equals(s))
            return true;
        try{
            Double.parseDouble(s);
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
    /**
     * ����ʽ
     * @param s String
     * @return boolean
     */
    private boolean checkFormat(String s)
    {
        if(s.length() == 0)
            return true;
        if(getFormatType() == null || getFormatType().length() == 0)
            return true;
        if("int".equalsIgnoreCase(getFormatType()))
            return checkIntFormat(s);

        if("long".equalsIgnoreCase(getFormatType()))
            return checkLongFormat(s);

        if("double".equalsIgnoreCase(getFormatType()))
            return checkDoubleFormat(s);

        if("date".equalsIgnoreCase(getFormatType()))
            return DateTool.checkDate(s,format);
        return true;
    }
    /**
     * �ػ�
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        Graphics gi = g.create(x,y,width,height);
        paintInset(gi,width,height);
        if(showDownButton)
            TDrawTool.paintDownButton(gi,width - downButtonWidth - 1,
                                      1,downButtonWidth,height - 2,downStyle);
        if(showDownloadDrawVisible)
        {
            gi.setColor(new Color(255,0,0));
            gi.drawLine(0,height - 1,30,height - 1);
        }
    }
    /**
     * �����ڲ�ͼ��
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintInset(Graphics g,int width,int height)
    {
        if(!isEnabled())
            g.setColor(new Color(235,235,228));
        else
            g.setColor(getBackground());
        g.fillRect(0,0,width,height);

        resetCursorSize();
        Graphics gText = g.create(getLeftWidth(),0,width - getLeftWidth() - getRightWidth() + 1,height);
        gText.setFont(getFont());
        FontMetrics fontMetric = gText.getFontMetrics();
        int fontAscent = fontMetric.getAscent();
        int fontHeight = fontMetric.getHeight();
        int sX = (height - fontHeight) / 2;
        int drawY = (height + fontAscent) / 2;
        gText.setColor(getForeground());
        if(selectStart == selectEnd)
            gText.drawString(text,startPoint,drawY);
        else
        {
            int start = selectStart;
            int end = selectEnd;
            if(start > end)
            {
                int temp = start;
                start = end;
                end = temp;
            }
            int x = startPoint;
            if(start > -1)
            {
                String s1 = text.substring(0,start + 1);
                gText.drawString(s1,x,drawY);
                x += fontMetric.stringWidth(s1);
            }
            if(text.length() > 0)
            {
                String s1 = text.substring(start + 1, end + 1);
                int sw = fontMetric.stringWidth(s1);
                gText.setColor(new Color(83, 109, 165));
                gText.fillRect(x, sX, sw, fontHeight);
                gText.setColor(new Color(255, 255, 255));
                gText.drawString(s1, x, drawY);
                x += sw;

                if (end + 1 < text.length())
                {
                    s1 = text.substring(end + 1);
                    gText.setColor(getForeground());
                    gText.drawString(s1, x, drawY);
                }
            }
        }
        if(showCursor)
        {
            gText.setColor(new Color(0,0,0));
            int w = startPoint;
            if(text.length() > 0 && cursorPoint != -1)
                w += fontMetric.stringWidth(
                        text.substring(0, cursorPoint + 1));
            gText.fillRect(w, sX, 1, fontHeight);
        }
    }
    public void showCursor()
    {
        showCursor = true;
        cursorSleepCount = 0;
        repaint();
    }
    private void resetCursorSize()
    {
        Insets insets = getInsets();
        int width = getWidth() - insets.left - insets.right -
                    getLeftWidth() - getRightWidth();
        FontMetrics fontMetric = getFontMetrics(getFont());
        int ws = fontMetric.stringWidth(text);
        if(ws < width)
        {
            switch(getHorizontalAlignment())
            {
            case SwingConstants.LEFT:
                startPoint = 0;
                return;
            case SwingConstants.RIGHT:
                startPoint = width - ws;
                return;
            case SwingConstants.CENTER:
                startPoint = (width - ws) / 2;
                return;
            }
            startPoint = (width - ws) / 2;
            return;
        }//else
        //    startPoint = 0;//??????
        if(cursorPoint + 1 > text.length())
            cursorPoint = text.length() - 1;
        int w = cursorPoint == -1 ?0:
                fontMetric.stringWidth(
                        text.substring(0, cursorPoint + 1));
        if(w + startPoint < 0)
            startPoint = -w;
        else if(w + startPoint > width - 1)
            startPoint = width - w - 1;
        else if(ws + startPoint < width - 1)
            startPoint = width - ws - 1;
    }
    /**
     * ������λ��
     * @param x int
     * @return int
     */
    private int getCPoint(int x)
    {
        Insets insets = getInsets();
        x -= insets.left + leftWidth;
        if(x < startPoint)
            return -1;
        FontMetrics fm = getFontMetrics(getFont());
        int count = text.length();
        int w = startPoint;
        for(int i = 0;i < count;i++)
        {
            int cw = fm.charWidth(text.charAt(i));
            if(x < cw/2 + w)
                return i - 1;
            w += cw;
        }
        return count - 1;
    }
    /**
     * �һ�
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
        exeAction(getRightClickedAction());
    }
    /**
     * ���
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
        exeAction(getClickedAction());
    }
    /**
     * ˫��
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {
        if(!isEnabled())
            return;
        if(showDownButton && inMouseInDownButton(e.getX(),e.getY()))
            return;
        selectAll();
        exeAction(getDoubleClickedAction());
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        if(!isEnabled())
            return;
        if(e.getButton() != e.BUTTON1)
            return;
        if(showDownButton && inMouseInDownButton(e.getX(),e.getY()))
        {
            downButtonMouseDown = true;
            downStyle = 2;
            repaint();
            if("date".equals(formatType))
                showDataPopup();
            if("combo".equalsIgnoreCase(formatType))
                showComboPopup();
            return;
        }
        cursorPoint = getCPoint(e.getX());
        selectStart = cursorPoint;
        selectEnd = cursorPoint;
        showCursor();
    }
    /**
     * ����������
     */
    public void hidePopup()
    {
        if(popupMenu != null && popupMenu.isVisible())
            popupMenu.hidePopup();
    }
    /**
     * ��ʾ���ڵ�����
     */
    public void showDataPopup()
    {
        getDatePopupMenu().show(this, 0, getHeight());
        int year = DateTool.checkYear(text,format,-1);
        int month = DateTool.checkMonth(text,format,-1);
        int day = DateTool.checkDay(text,year,month,format,-1);
        if(year == -1 || year == -100 ||
           month == -1 || month == -100 ||
           day == -1 || day == -100)
            getTDateEdit().nowAction();
        else
            getTDateEdit().setDate(year,month,day);
    }
    /**
     * ��ʾCombo��������
     */
    public void showComboPopup()
    {
        TPopupMenu popupMenu = getComboPopupMenu();
        int y = getHeight();
        Point point = getLocationOnScreen();
        Dimension dimension = ImageTool.getScreenSize();
        int w = popupMenu.getWidth();
        int h = popupMenu.getHeight();
        int h1 = (int)dimension.getHeight() - (int)point.getY() - getHeight() - 30;
        int h2 = (int)point.getY();
        if(h1 < h)
        {
            if(h2 >= h)
                y = - h;
            else if(h1 > h2)
                h = h1;
            else
            {
                h = h2;
                y = - h;
            }
        }
        popupMenu.setPopupSize(w,h);
        popupMenu.setSize(w,h);
        popupMenu.changeLanguage(getLanguage());
        popupMenu.show(this, 0, y);
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        if(!isEnabled())
            return;
        if(e.getButton() != e.BUTTON1)
            return;
        if(downButtonMouseDown)
        {
            downButtonMouseDown = false;
            downStyle = 0;
            repaint();
            return;
        }
        cursorPoint = getCPoint(e.getX());
        selectEnd = cursorPoint;
        showCursor();
    }
    /**
     * ������
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        if(!isEnabled())
            return;
        super.onMouseEntered(e);
    }
    /**
     * ����뿪
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        if(!isEnabled())
            return;
        super.onMouseExited(e);
        setCursor(new Cursor(0));
        if(showDownButton && !downButtonMouseDown)
        {
            downStyle = 0;
            repaint();
        }
    }
    /**
     * ����϶�
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        if(!isEnabled())
            return;
        if(downButtonMouseDown)
        {
            int tempDownStyle = inMouseInDownButton(e.getX(),e.getY())?2:1;
            if(tempDownStyle != downStyle)
            {
                downStyle = tempDownStyle;
                repaint();
            }
            return;
        }
        if(text.length() > 0)
        {
            cursorPoint = getCPoint(e.getX());
            selectEnd = cursorPoint;
            showCursor();
        }
    }
    /**
     * ����ƶ�
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
        if(!isEnabled())
            return;
        if(showDownButton)
        {
            int tempDownStyle = inMouseInDownButton(e.getX(),e.getY())?1:0;
            if(tempDownStyle == 1)
            {
                setCursor(new Cursor(0));
                if(tempDownStyle != downStyle)
                {
                    downStyle = tempDownStyle;
                    repaint();
                }
                return;
            }
        }
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }
    /**
     * ���λ����������ť��Χ
     * @param x int
     * @param y int
     * @return boolean
     */
    private boolean inMouseInDownButton(int x,int y)
    {
        Insets insets = getInsets();
        int x1 = getWidth() - insets.right - downButtonWidth - 1;
        int x2 = x1 + downButtonWidth;
        int y1 = insets.top + 1;
        int y2 = getHeight() - insets.bottom - 1;
        if(x >= x1 && x <= x2 && y >= y1 && y <= y2)
            return true;
        return false;
    }
    /**
     * �õ������¼�
     * @param e FocusEvent
     */
    public void onFocusGainedAction(FocusEvent e)
    {
        if(!isEnabled())
            return;
        cursorThread = new CursorThread();
        cursorThread.start();
        showCursor();
    }
    /**
     * ʧȥ�����¼�
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        if(!isEnabled())
            return;
        initFormat();
        cursorThread = null;
        showCursor = false;
        cursorSleepCount = 0;
        selectStart = selectEnd = cursorPoint;
        cursorPoint = -1;
        //hidePopup();
        repaint();
        exeAction(getFocusLostAction());
    }
    /**
     * �õ�ѡ���ַ�
     * @return String
     */
    public String getSelectText()
    {
        if(selectStart == selectEnd)
            return "";
        int start = selectStart;
        int end = selectEnd;
        if(start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
        return text.substring(start + 1,end + 1);
    }
    /**
     * ����
     */
    public void copy()
    {
        StringTool.setClipboard(getSelectText());
    }
    /**
     * ճ��
     */
    public void paste()
    {
        pasteString(StringTool.getClipboard());
        showCursor();
    }
    /**
     * ����
     */
    public void cut()
    {
        copy();
        String tt = text;
        int ss = selectStart;
        int se = selectEnd;
        int cp = cursorPoint;
        deleteSelectedChar();
        if(!checkFormat(text))
        {
            text = tt;
            selectStart = ss;
            selectEnd = se;
            cursorPoint = cp;
            return;
        }
        showCursor();
    }
    /**
     * ���̰���
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
        if(!isEnabled())
            return;
        int keyCode = e.getKeyCode();
        switch(keyCode)
        {
        case KeyEvent.VK_C://����
            if(e.isControlDown() && selectStart != selectEnd)
                copy();
            break;
        case KeyEvent.VK_V://ճ��
            if(e.isControlDown())
                paste();
            break;
        case KeyEvent.VK_X://ճ��
            if(e.isControlDown() && selectStart != selectEnd)
                cut();
            break;
        case KeyEvent.VK_UP:
            doSelectItem(1);
            break;
        case KeyEvent.VK_DOWN:
            doSelectItem(2);
            break;
        case KeyEvent.VK_LEFT:
            if(cursorPoint > -1)
            {
                if(e.isShiftDown())
                    if(selectStart == selectEnd)
                        selectStart = cursorPoint;
                cursorPoint--;
                if(e.isShiftDown())
                    selectEnd = cursorPoint;
                else
                    selectStart = selectEnd = cursorPoint;
                showCursor();
            }
            break;
        case KeyEvent.VK_RIGHT:
            if(cursorPoint < text.length() - 1)
            {
                if(e.isShiftDown())
                    if(selectStart == selectEnd)
                        selectStart = cursorPoint;
                cursorPoint++;
                if(e.isShiftDown())
                    selectEnd = cursorPoint;
                else
                    selectStart = selectEnd = cursorPoint;
                showCursor();
            }
            break;
        case KeyEvent.VK_HOME:
            if(e.isShiftDown())
                if(selectStart == selectEnd)
                    selectStart = cursorPoint;
            cursorPoint = -1;
            if(e.isShiftDown())
                selectEnd = cursorPoint;
            else
                selectStart = selectEnd = cursorPoint;
            showCursor();
            break;
        case KeyEvent.VK_END:
            if(e.isShiftDown())
                if(selectStart == selectEnd)
                    selectStart = cursorPoint;
            cursorPoint = text.length() - 1;
            if(e.isShiftDown())
                selectEnd = cursorPoint;
            else
                selectStart = selectEnd = cursorPoint;
            showCursor();
            break;
        case KeyEvent.VK_ENTER:
            doShowDownValueForRow();
            initFormat();
            resetCursorSize();
            hidePopup();
            cancelSelectAll();
            goNextFocus();
            exeAction(getActionMessage());
            onEnter();
            break;
        case KeyEvent.VK_ESCAPE:
            hidePopup();
            onEscape();
            break;
        case KeyEvent.VK_TAB:
            hidePopup();
            if(e.isShiftDown())
                onShiftTab();
            else
                onTab();
            break;
        }
    }
    public void onShiftTab()
    {
        callEvent(getTag() + "->" + TTextFormatEvent.EDIT_SHIFT_TAB,
                  new Object[]{},new String[]{});
        if(tableBase != null)
            tableBase.grabFocus();
        if(tableSortBase != null)
            tableSortBase.grabFocus();
        
    }
    public void onTab()
    {
        callEvent(getTag() + "->" + TTextFormatEvent.EDIT_TAB,
                  new Object[]{},new String[]{});
        if(tableBase != null)
            tableBase.grabFocus();
        
        if(tableSortBase != null)
            tableSortBase.grabFocus();
    }
    /**
     * ȡ��
     */
    public void onEscape()
    {
        callEvent(getTag() + "->" + TTextFormatEvent.EDIT_ESC,
                  new Object[]{},new String[]{});
        if(tableBase != null)
            tableBase.grabFocus();
        
        if(tableSortBase != null)
            tableSortBase.grabFocus();
    }
    /**
     * �س��¼�
     */
    public void onEnter()
    {
        callEvent(getTag() + "->" + TTextFormatEvent.EDIT_ENTER,
                  new Object[]{},new String[]{});
        if(tableBase != null)
            tableBase.grabFocus();
        
        if(tableSortBase != null)
            tableSortBase.grabFocus();
    }
    /**
     * ����
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {
        if(!isEnabled())
            return;
        if(e.isAltDown() || e.isControlDown())
            return;
        char c = e.getKeyChar();
        //ɾ��
        if(c == 8)
            backspaceChar();
        //delete
        else if(c == 127)
            deleteChar();
        else if(c == 27 || c == 10)
            return;
        else
            pasteChar(c);
        showCursor();
        //ִ������ʱ����
        doInputPopupMenu();
        //����
        filter();
    }
    /**
     * ���ڸ�ʽճ��һ���ַ�
     * @param c char
     */
    private void pasteDateFormatChar(char c)
    {
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        if(text.length() != format.length())
        {
            text = DateTool.getDateDefaultFormat(format);
            selectStart = selectEnd = cursorPoint = -1;
        }
        deleteSelectedDateFormatChar();
        int p = cursorPoint + 1;
        if(p >= format.length())
            return;
        char formatC = format.charAt(p);
        while(!DateTool.isFormatChar(formatC))
        {
            cursorPoint++;
            p = cursorPoint + 1;
            if(p >= format.length())
                return;
            formatC = format.charAt(p);
        }
        String textString = StringTool.editChar(text,p,c);
        //��������
        if(!DateTool.checkDate(textString,format,formatC,p))
        {
            if(p + 1 >= format.length())
                return;
            char formatC1 = format.charAt(p + 1);
            if(!DateTool.isFormatChar(formatC1))
                return;
            textString = StringTool.editChar(textString,p + 1,'0');
            if(!DateTool.checkDate(textString,format,formatC,p))
                return;
        }
        //�Ƿ��Ǳ��������һ���ַ�,�淶�ַ���ʽ
        if(DateTool.isEndCharIndex(format,formatC,p))
            textString = DateTool.editString(textString,format);

        text = textString;
        cursorPoint ++;
        p++;
        if(p >= format.length())
            return;
        p = DateTool.getNextEditIndex(format,p);
        if(p != -1)
            cursorPoint = p - 1;
    }
    /**
     * ճ��һ���ַ�
     * @param c char
     */
    private void pasteChar(char c)
    {
        if("date".equalsIgnoreCase(this.getFormatType()))
        {
            pasteDateFormatChar(c);
            return;
        }
        deleteSelectedChar();
        if(inputLength > 0 && text.length() >= inputLength)
            return;
        String textString = "";
        if(cursorPoint == -1)
            textString = c + text;
        else if(cursorPoint == text.length() - 1)
            textString = text + c;
        else
            textString = text.substring(0,cursorPoint + 1) + c + text.substring(cursorPoint + 1);
        if(!checkFormat(textString))
            return;
        cursorPoint ++;
        text = textString;
        checkNumberZero();
    }
    /**
     * ��ʼ�����ָ�ʽ
     */
    public void initFormat()
    {
        if(!checkFormat(text))
        {
            text = "";
            return;
        }
        checkNumberZero();
        if("int".equalsIgnoreCase(getFormatType())||
           "long".equalsIgnoreCase(getFormatType()))
        {
            if(text.length() == 0 && format != null && format.endsWith("0"))
                text = "0";
            if("0".equals(text) && format != null && format.endsWith("#"))
                text = "";
            return;
        }
        if("double".equalsIgnoreCase(getFormatType()) && format != null && format.length() > 0)
        {
            int index = format.indexOf(".");
            if(index == -1)
            {
                if(format.endsWith("0"))
                    if(text.length() == 0 || text.equals("-0"))
                        text = "0";
                if(("-0".equals(text) || "0".equals(text)) && format.endsWith("#"))
                    text = "";
                int textIndex = text.indexOf(".");
                if(textIndex > 0)
                    text = text.substring(0,textIndex);
            }else
            {
                if(index > 0)
                    if(format.charAt(index - 1) == '0')
                    {
                        if (text.length() == 0)
                            text = "0.";
                        else if (text.startsWith("."))
                            text = "0" + text;
                        else if (text.startsWith("-."))
                            text = "-0" + text.substring(1);
                        else if (text.equals("0") || text.equals("-0") ||
                                 text.equals("-"))
                            text = "0.";
                    }else if(format.charAt(index - 1) == '#')
                    {
                        if (text.length() == 0)
                            text = ".";
                        else if (text.startsWith("0."))
                            text = text.substring(1);
                        else if (text.equals("0") || text.equals("-0") ||
                                 text.equals("-") || text.equals("-.") ||
                                 text.equals("-0."))
                            text = ".";
                        else if (text.startsWith("-0."))
                            text = "-" + text.substring(2);
                    }
                int x = format.length() - index - 1;
                int textIndex = text.indexOf(".");
                if(textIndex == -1)
                {
                    text += "." + StringTool.fill("0", x);
                }
                else
                {
                    int x1 = text.length() - textIndex - 1;
                    if (x1 > x)
                        text = text.substring(0, text.length() - x1 + x);
                    else
                        text += StringTool.fill("0", x - x1);
                }
                if(text.substring(textIndex + 1).equals(StringTool.fill("0", x)) &&
                   (text.startsWith("-.") || text.startsWith("-0.")))
                    text = text.substring(1);
            }
        }
        if(cursorPoint > text.length() - 1)
            cursorPoint = text.length() - 1;
    }
    /**
     * ������ʾ������
     */
    public void checkNumberZero()
    {
        if("int".equalsIgnoreCase(getFormatType())||
           "long".equalsIgnoreCase(getFormatType()))
        {
            if (text.startsWith("-"))
                while (text.length() > 2 && text.charAt(1) == '0')
                {
                    text = "-" + text.substring(2);
                    if (cursorPoint > 0)
                        cursorPoint--;
                }
            else
                while (text.length() > 1 && text.startsWith("0"))
                {
                    text = text.substring(1);
                    if (cursorPoint > -1)
                        cursorPoint--;
                }
            return;
        }
        if("double".equalsIgnoreCase(getFormatType()))
        {
            int index = text.indexOf(".");
            if(index == -1)
                index = text.length();
            if(text.startsWith("-"))
                while(index > 2 && text.charAt(1) == '0')
                {
                    text = "-" + text.substring(2);
                    if(cursorPoint > 0)
                        cursorPoint --;
                    index = text.indexOf(".");
                    if(index == -1)
                        index = text.length();
                }
            else
                while(index > 1 && text.startsWith("0"))
                {
                    text = text.substring(1);
                    if(cursorPoint > -1)
                        cursorPoint --;
                    index = text.indexOf(".");
                    if(index == -1)
                        index = text.length();
                }
            return;
        }
    }
    /**
     * ճ���ַ���
     * @param s String
     */
    public void pasteString(String s)
    {
        String tt = text;
        int ss = selectStart;
        int se = selectEnd;
        int cp = cursorPoint;
        deleteSelectedChar();
        if(inputLength > 0 && text.length() + s.length() >= inputLength)
            return;
        String textString = "";
        if(cursorPoint == -1)
            textString = s + text;
        else if(cursorPoint == text.length() - 1)
            textString = text + s;
        else
            textString = text.substring(0,cursorPoint + 1) + s + text.substring(cursorPoint + 1);
        if(!checkFormat(textString))
        {
            text = tt;
            selectStart = ss;
            selectEnd = se;
            cursorPoint = cp;
            return;
        }
        cursorPoint += s.length();
        text = textString;
        checkNumberZero();
    }
    /**
     * ɾ��ѡ�е��ַ�
     * @return boolean
     */
    private boolean deleteSelectedChar()
    {
        if(selectStart == selectEnd)
            return false;
        int start = selectStart;
        int end = selectEnd;
        if(start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
        String s = "";
        if(start > -1)
            s = text.substring(0,start + 1);
        if(end + 1 < text.length())
            s += text.substring(end + 1);
        text = s;
        cursorPoint = start;
        selectStart = selectEnd = cursorPoint;
        return true;
    }
    private void backspaceDateFormatChar()
    {
        if(deleteSelectedDateFormatChar())
            return;
        if(cursorPoint == -1)
            return;
        char formatC = format.charAt(cursorPoint);
        while(!DateTool.isFormatChar(formatC))
        {
            cursorPoint--;
            if(cursorPoint == -1)
                return;
            formatC = format.charAt(cursorPoint);
        }

        text = StringTool.editChar(text,cursorPoint,'0');

        cursorPoint --;
        if(cursorPoint == -1)
            return;
        int p = DateTool.getPreviousEditIndex(format,cursorPoint);
        if(p != -1)
            cursorPoint = p;
    }
    private void deleteDateFormatChar()
    {
        if(deleteSelectedDateFormatChar())
            return;
        if(cursorPoint + 1 >= format.length())
            return;
        char formatC = format.charAt(cursorPoint + 1);
        while(!DateTool.isFormatChar(formatC))
        {
            cursorPoint++;
            if(cursorPoint + 1 >= format.length())
                return;
            formatC = format.charAt(cursorPoint + 1);
        }

        text = StringTool.editChar(text,cursorPoint + 1,'0');

        cursorPoint ++;
        if(cursorPoint + 1 >= format.length())
            return;
        int p = DateTool.getNextEditIndex(format,cursorPoint + 1);
        if(p != -1)
            cursorPoint = p - 1;
    }
    private boolean deleteSelectedDateFormatChar()
    {
        if(selectStart == selectEnd)
            return false;
        int start = selectStart;
        int end = selectEnd;
        if(start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
        for(int i = start + 1;i <= end;i++)
        {
            if(DateTool.isFormatChar(format.charAt(i)))
                text = StringTool.editChar(text,i,'0');
        }
        cursorPoint = start;
        selectStart = selectEnd = cursorPoint;
        return true;
    }
    private void backspaceChar()
    {
        if(text.length() == 0)
            return;
        if("date".equalsIgnoreCase(this.getFormatType()))
        {
            backspaceDateFormatChar();
            return;
        }
        if(deleteSelectedChar())
            return;
        if(cursorPoint == -1)
            return;
        else if(cursorPoint == text.length() - 1)
            text = text.substring(0,text.length() - 1);
        else
            text = text.substring(0,cursorPoint) + text.substring(cursorPoint + 1);
        cursorPoint --;
    }
    private void deleteChar()
    {
        if(text.length() == 0)
            return;
        if("date".equalsIgnoreCase(this.getFormatType()))
        {
            deleteDateFormatChar();
            return;
        }
        if(deleteSelectedChar())
            return;
        if(cursorPoint == text.length() - 1)
            return;
        else if(cursorPoint == -1)
            text = text.substring(1,text.length());
        else
            text = text.substring(0,cursorPoint + 1) + text.substring(cursorPoint + 2);
    }
    class CursorThread extends Thread
    {
        public void run()
        {
            while(cursorThread != null)
            {
                try
                {
                    sleep(1);
                } catch (Exception e)
                {
                }
                cursorSleepCount ++;
                if(cursorSleepCount < 500)
                    continue;
                showCursor = !showCursor;
                repaint();
                cursorSleepCount = 0;
            }
        }
    }
    /**
     * �õ����ڵ�����
     * @return TPopupMenu
     */
    public TPopupMenu getDatePopupMenu()
    {
        if(popupMenu == null || !"date".equals(popupMenu.getType()))
        {
            popupMenu = new TPopupMenu();
            popupMenu.setType("date");
            popupMenu.setTag(".PopupMenu");
            popupMenu.setLoadTag("UI");
            popupMenu.setInsetNotHide(true);
            TDateEdit de = new TDateEdit();
            de.setParentComponent(this);
            de.setDayAction("modifyDateAction");
            de.setMonthAction("modifyDateAction");
            de.setYearAction("modifyDateAction");
            de.setActionMessage("onDateAction");
            de.onInit();
            de.changeLanguage(getLanguage());
            popupMenu.add(de);
            popupMenu.onInit();
            popupMenu.setPopupSize(de.getWidth(), de.getHeight());
            popupMenu.setInvokerComponent(this);
        }
        return popupMenu;
    }
    /**
     * �õ�Combo������
     * @return TPopupMenu
     */
    public TPopupMenu getComboPopupMenu()
    {
        if(popupMenu == null || !"combo".equals(popupMenu.getType()))
        {
            popupMenu = new TPopupMenu();
            popupMenu.setType("combo");
            popupMenu.setTag(".PopupMenu");
            popupMenu.setLoadTag("UI");
            popupMenu.setInsetNotHide(true);
            if(getPanelPopupMenu() == null)
                setPanelPopupMenu(getDefaultPanelPopupMenu());
            popupMenu.add(getPanelPopupMenu());
            popupMenu.onInit();
            popupMenu.setPopupSize(getPopupMenuWidth(),getPopupMenuHeight());
            popupMenu.setSize(getPopupMenuWidth(),getPopupMenuHeight());
            popupMenu.setInvokerComponent(this);
        }
        popupMenuRetrieve();
        return popupMenu;
    }
    /**
     * �õ�TDateEdit
     * @return TDateEdit
     */
    public TDateEdit getTDateEdit()
    {
        if(popupMenu == null || !"date".equals(popupMenu.getType()))
            return null;
        if(popupMenu.getComponentCount() == 0)
            return null;
        Component com = popupMenu.getComponent(0);
        if(!(com instanceof TDateEdit))
            return null;
        return (TDateEdit)com;
    }
    /**
     * �ı��ն���
     */
    public void modifyDateAction()
    {
        int year = getTDateEdit().getYear();
        int month = getTDateEdit().getMonth();
        int day = getTDateEdit().getDay();
        String textString = DateTool.setDate(year,month,day,text,format);
        if(!textString.equals(text))
        {
            text = textString;
            repaint();
        }
    }
    /**
     * ����
     */
    public void onDateAction()
    {
        modifyDateAction();
        hidePopup();
        exeAction(getActionMessage());
        grabFocus();
    }
    /**
     * ���õ����˵����
     * @param panelPopupMenu TPanel
     */
    public void setPanelPopupMenu(TPanel panelPopupMenu)
    {
        this.panelPopupMenu = panelPopupMenu;
    }
    /**
     * �õ������˵����
     * @return TPanel
     */
    public TPanel getPanelPopupMenu()
    {
        return panelPopupMenu;
    }
    /**
     * �õ�Ĭ�ϵ����˵����
     * @return TPanel
     */
    public TPanel getDefaultPanelPopupMenu()
    {
        TPanel panelPopupMenu = new TPanel();
        panelPopupMenu.setTag(".PopupMenu.panel");
        panelPopupMenu.setLayout(new BorderLayout());
        if(getTablePopupMenu() == null)
            setTablePopupMenu(getDefaultTablePopupMenu());
        //��ʾ����
        popupMenuShowData();
        panelPopupMenu.add(getTablePopupMenu());
        return panelPopupMenu;
    }
    /**
     * �õ������˵�Table
     * @param tablePopupMenu TTable
     */
    public void setTablePopupMenu(TTable tablePopupMenu)
    {
        this.tablePopupMenu = tablePopupMenu;
    }
    /**
     * ���õ����˵�Table
     * @return TTable
     */
    public TTable getTablePopupMenu()
    {
        return tablePopupMenu;
    }
    /**
     * �õ�Ĭ�ϵ����˵�Table
     * @return TTable
     */
    public TTable getDefaultTablePopupMenu()
    {
        TTable table = new TTable();
        table.setTag(".PopupMenu.Table");
        table.setHeader(getPopupMenuHeader());
        table.setEnHeader(getPopupMenuEnHeader());
        table.setLanguageMap(getLanguageMap());
        table.changeLanguage(getLanguage());
        table.addEventListener(".PopupMenu.Table->" + TTableEvent.CLICKED,this,"onPopupMenuTableClicked");
        return table;
    }
    /**
     * �����˵���ʾ����
     */
    public void popupMenuShowData()
    {
        popupMenuRetrieve();
        if(getPopupMenuData() == null || getPopupMenuData().getErrCode() < 0)
            return;
        TParm parm = getPopupMenuData();
        Vector columns = (Vector)parm.getData("SYSTEM","COLUMNS");
        int count = getTablePopupMenu().getColumnCount();
        if(count > columns.size())
        {
            System.out.println("TTextFormatBase<" + getTag() + ">ִ��popupMenuShowData����:������С����ʾ����");
            return;
        }
        StringBuffer sb = new StringBuffer();
        StringBuffer sba = new StringBuffer();
        StringBuffer lock = new StringBuffer();
        for(int i = 0;i < count;i++)
        {
            if(sb.length() > 0)
                sb.append(";");
            if(sba.length() > 0)
                sba.append(";");
            if(lock.length() > 0)
                lock.append(",");
            String name = (String)columns.get(i);
            sb.append(name);
            sba.append("" + i + ",left");
            lock.append("" + i);
        }
        getTablePopupMenu().setColumnHorizontalAlignmentData(sba.toString());
        getTablePopupMenu().setLockColumns(lock.toString());
        getTablePopupMenu().setParmMap(sb.toString());
        getTablePopupMenu().setParmValue(parm);
        if(parm.getCount() > 0)
            getTablePopupMenu().setSelectedRow(0);
    }
    /**
     * �����˵���������
     */
    public synchronized void popupMenuRetrieve()
    {
        if(!isModifySQL())
            return;
        String sql = getPopupMenuSQL();
        sql = TypeTool.getString(getTagValue(sql));
        if(sql == null || sql.length() == 0)
            return;
        //$$==============Modified by lx֧�ֶ�����Դ 2011-09-02==========================$$//
        TParm parm =null;
        if(this.getDbPoolName()!=null&&!this.getDbPoolName().equals("")){
            parm = new TParm(getDBTool().select(this.getDbPoolName(),sql));
        }else{
            parm = new TParm(getDBTool().select(sql));
        }


        if(parm.getErrCode() < 0)
            System.out.println("TTextFormatBase<" + getTag() + ">ִ��popupMenuRetrieve����:" + parm.getErrText());
        if(hisOneNullRow())
        {
            String names[] = parm.getNames();
            for(int i = 0;i < names.length;i++)
            {
                Object obj = parm.getData(names[i]);
                if(!(obj instanceof Vector))
                    continue;
                Vector v = (Vector)obj;
                v.add(0,"");
            }
            parm.setCount(parm.getCount() + 1);
        }
        setPopupMenuData(parm);
        if(getTablePopupMenu() != null)
        {
            getTablePopupMenu().setParmValue(parm);
        }
        setModifySQL(false);
    }
    /**
     * �õ����ݿ⹤��
     * @return TJDODBTool
     */
    public TJDODBTool getDBTool()
    {
        return TJDODBTool.getInstance();
    }
    /**
     * ���õ����˵����
     * @param popupMenuWidth int
     */
    public void setPopupMenuWidth(int popupMenuWidth)
    {
        this.popupMenuWidth = popupMenuWidth;
    }
    /**
     * �õ������˵����
     * @return int
     */
    public int getPopupMenuWidth()
    {
        return popupMenuWidth;
    }
    /**
     * ���õ����˵��߶�
     * @param popupMenuHeight int
     */
    public void setPopupMenuHeight(int popupMenuHeight)
    {
        this.popupMenuHeight = popupMenuHeight;
    }
    /**
     * �õ������˵��߶�
     * @return int
     */
    public int getPopupMenuHeight()
    {
        return popupMenuHeight;
    }
    /**
     * ���õ����˵���ͷ
     * @param popupMenuHeader String
     */
    public void setPopupMenuHeader(String popupMenuHeader)
    {
        this.popupMenuHeader = popupMenuHeader;
    }
    /**
     * �õ������˵���ͷ
     * @return String
     */
    public String getPopupMenuHeader()
    {
        return popupMenuHeader;
    }
    /**
     * ���õ����˵�Ӣ�ı�ͷ
     * @param popupMenuEnHeader String
     */
    public void setPopupMenuEnHeader(String popupMenuEnHeader)
    {
        this.popupMenuEnHeader = popupMenuEnHeader;
    }
    /**
     * �õ������˵�Ӣ�ı�ͷ
     * @return String
     */
    public String getPopupMenuEnHeader()
    {
        return popupMenuEnHeader;
    }
    /**
     * ���õ����˵�SQL
     * @param popupMenuSQL String
     */
    public void setPopupMenuSQL(String popupMenuSQL)
    {
        this.popupMenuSQL = popupMenuSQL;
    }
    /**
     * �õ������˵�SQL
     * @return String
     */
    public String getPopupMenuSQL()
    {
        return popupMenuSQL;
    }
    /**
     * ���õ����˵�����
     * @param popupMenuData TParm
     */
    public void setPopupMenuData(TParm popupMenuData)
    {
        this.popupMenuData = popupMenuData;
    }
    /**
     * �õ������˵�����
     * @return TParm
     */
    public TParm getPopupMenuData()
    {
        return popupMenuData;
    }
    /**
     * ���õ����˵���������
     * @param popupMenuFilter String
     */
    public void setPopupMenuFilter(String popupMenuFilter)
    {
        this.popupMenuFilter = popupMenuFilter;
        TList list = new TList();
        String s[] = StringTool.parseLine(popupMenuFilter,";");
        for(int i = 0;i < s.length;i++)
        {
            String s1[] = StringTool.parseLine(s[i],",");
            if(s1.length == 0)
                continue;
            String name = s1[0].trim().toUpperCase();
            int f = 0;
            if(s1.length > 1)
                f = TypeTool.getInt(s1[1].trim());
            list.add(new Object[]{name,f});
        }
        setPopupMenuFilterList(list);
    }
    /**
     * �õ������˵���������
     * @return String
     */
    public String getPopupMenuFilter()
    {
        return popupMenuFilter;
    }
    /**
     * ���õ����˵����������б�
     * @param popupMenuFilterList TList
     */
    public void setPopupMenuFilterList(TList popupMenuFilterList)
    {
        this.popupMenuFilterList = popupMenuFilterList;
    }
    /**
     * �õ������˵����������б�
     * @return TList
     */
    public TList getPopupMenuFilterList()
    {
        return popupMenuFilterList;
    }
    /**
     * ����
     */
    public void filter()
    {
        filter(text);
    }
    /**
     * ����
     * @param text String
     */
    public void filter(String text)
    {
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return;
        TParm parm = getPopupMenuData();
        if(parm == null || parm.getErrCode() < 0 || parm.getCount() == 0)
            return;
        TList list = getPopupMenuFilterList();
        if(list == null || list.size() == 0)
        {
            if(!popupMenuShowAllData)
                getTablePopupMenu().setParmValue(parm);
            return;
        }
        if(text.trim().length() == 0)
        {
            getTablePopupMenu().setParmValue(parm);
            return;
        }
        String v[] = StringTool.parseSpace(text);
        TParm data = new TParm();
        int count = parm.getCount();
        F1:
        for(int i = 0;i < count;i++)
        {
            for(int k = 0;k < v.length;k ++)
            {
                boolean isShow = false;
                String value = v[k].trim().toUpperCase();
                F2:for (int j = 0; j < list.size(); j++)
                {
                    Object o[] = (Object[]) list.get(j);
                    String name = (String) o[0];
                    int type = (Integer) o[1];
                    String s = parm.getValue(name, i);
                    if (s == null)
                        s = "";
                    s = s.toUpperCase();
                    switch (type)
                    {
                    case 0: //��ȫƥ��
                        if (s.equals(value))
                        {
                            isShow = true;
                            break F2;
                        }
                        break;
                    case 1: //��ƥ��
                        if (s.startsWith(value))
                        {
                            isShow = true;
                            break F2;
                        }
                        break;
                    case 2: //��ƥ��
                        if (s.endsWith(value))
                        {
                            isShow = true;
                            break F2;
                        }
                        break;
                    case 3: //����
                        if (s.indexOf(value) != -1)
                        {
                            isShow = true;
                            break F2;
                        }
                        break;
                    }
                }
                if (!isShow)
                    continue F1;
            }
            data.addRowData(parm, i);
        }
        getTablePopupMenu().setParmValue(data);
        if(data.getCount() > 0)
            getTablePopupMenu().setSelectedRow(0);
    }
    /**
     * ��������ʱ����
     * @param inputPopupMenu boolean
     */
    public void setInputPopupMenu(boolean inputPopupMenu)
    {
        this.inputPopupMenu = inputPopupMenu;
    }
    /**
     * �Ƿ�����ʱ����
     * @return boolean
     */
    public boolean isInputPopupMenu()
    {
        return inputPopupMenu;
    }
    /**
     * ִ������ʱ����
     */
    public void doInputPopupMenu()
    {
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return;
        showComboPopup();
    }
    /**
     * ִ��ѡ��
     * @param type int
     */
    public void doSelectItem(int type)
    {
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return;
        showComboPopup();
        int count = getTablePopupMenu().getRowCount();
        if(count <= 1)
            return;
        int row = getTablePopupMenu().getSelectedRow();
        switch(type)
        {
        case 1:
            if(row == 0)
                row = count - 1;
            else
                row --;
            break;
        case 2:
            if(row == count - 1)
                row = 0;
            else
                row ++;
            break;
        }
        getTablePopupMenu().setSelectedRow(row);
        //�иı�ı���ʾ����
        doShowDownValueForRow();
        selectAll();
    }
    /**
     * ���ֶ��ո���
     * @param names String
     * @return String
     */
    private String checkLanguage(String names)
    {
        if(!"en".equals(getLanguage()))
           return names;
        if(getLanguageMap() == null || getLanguageMap().length() == 0)
            return names;
        String list[] = StringTool.parseLine(getLanguageMap(),";");
        for(int i = 0;i < list.length;i++)
        {
            String s[] = StringTool.parseLine(list[i], "|");
            if (s.length == 2)
                names = StringTool.replaceAll(names, s[0], s[1]);
        }
        return names;
    }
    /**
     * �иı�ı���ʾ����
     */
    public void doShowDownValueForRow()
    {
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return;
        TParm parm = getPopupMenuData();
        if(parm == null || parm.getCount() <= 0)
            return;
        Vector columns = (Vector)parm.getData("SYSTEM","COLUMNS");
        if(columns == null || columns.size() == 0)
            return;
        String s[] = StringTool.parseLine(checkLanguage(getShowColumnList()),";");
        if(s.length == 0)
            s = new String[]{(String)columns.get(0)};
        int row = getTablePopupMenu().getSelectedRow();
        if(row < 0)
            return;
        TParm data = getTablePopupMenu().getParmValue();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < s.length;i ++)
        {
            if(sb.length() > 0)
                sb.append(" ");
            sb.append(data.getValue(s[i].trim().toUpperCase(),row));
        }
        text = sb.toString();
        resetCursorSize();
        String name = getValueColumn();
        if(name == null || name.length() == 0)
            name = (String)columns.get(0);
        //����Comboֵ
        setComboValue(data.getValue(name.trim().toUpperCase(),row));
        repaint();
    }
    /**
     * ������ʾ��
     * @param showColumnList String
     */
    public void setShowColumnList(String showColumnList)
    {
        this.showColumnList = showColumnList;
    }
    /**
     * �õ���ʾ��
     * @return String
     */
    public String getShowColumnList()
    {
        return showColumnList;
    }
    /**
     * �����˵�ѡ��Table
     * @param row int
     */
    public void onPopupMenuTableClicked(int row)
    {
        doShowDownValueForRow();
        cancelSelectAll();
        hidePopup();
        exeAction(getActionMessage());
    }
    /**
     * ȡ��ѡ��
     */
    public void cancelSelectAll()
    {
        selectStart = selectEnd = cursorPoint;
        cursorPoint = -1;
        repaint();
    }
    /**
     * ѡ��
     */
    public void selectAll()
    {
        selectText(0,text.length());
    }
    /**
     * ����ֹ���
     */
    public void selectTextLast()
    {
        selectText(text.length(),text.length());
    }
    /**
     * ѡ��
     * @param start int
     * @param end int
     */
    public void selectText(int start,int end)
    {
        if(start < 0)
            start = 0;
        if(end > text.length())
            end = text.length();
        selectStart = start - 1;
        selectEnd = end - 1;
        cursorPoint = selectEnd;
        showCursor();
    }
    /**
     * ����ֵ��
     * @param valueColumn String
     */
    public void setValueColumn(String valueColumn)
    {
        this.valueColumn = valueColumn;
    }
    /**
     * �õ�ֵ��
     * @return String
     */
    public String getValueColumn()
    {
        return valueColumn;
    }
    /**
     * ����Comboֵ
     * @param comboValue String
     */
    public void setComboValue(String comboValue)
    {
        if(this.comboValue == comboValue)
            return;
        this.comboValue = comboValue;
        exeAction(getEditValueAction());
    }
    /**
     * �õ�comboֵ
     * @return String
     */
    public String getComboValue()
    {
        return comboValue;
    }
    /**
     * �õ�comboָ��ֵ
     * @param name String ����
     * @return Object
     */
    public Object getComboValue(String name)
    {
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return null;
        TParm parm = getPopupMenuData();
        if(parm.getCount() <= 0)
            return null;
        int row = getTablePopupMenu().getSelectedRow();
        if(row < 0)
            return null;
        if(name == null || name.length() == 0)
            return null;
        TParm data = getTablePopupMenu().getParmValue();
        //����Comboֵ
        return data.getData(name.trim().toUpperCase(),row);
    }
    /**
     * ����ֵ�ı䶯��
     * @param action String
     */
    public void setEditValueAction(String action)
    {
        actionList.setAction("editValueAction",action);
    }
    /**
     * �õ�ֵ�ı䶯��
     * @return String
     */
    public String getEditValueAction()
    {
        return actionList.getAction("editValueAction");
    }
    /**
     * ��ѯ
     */
    public void onQuery()
    {
        setModifySQL(true);
        setComboSelectRow();
    }
    /**
     * ����Comboѡ����
     */
    public synchronized void setComboSelectRow()
    {
        //�Ƕ�̬����
        if(!isDynamicDownload())
        {
            setComboSelectRow(getComboValue());
            return;
        }
        dynamicDownloadThread = new Thread(){
            public void run()
            {
                //����������ʾ�߳�
                startShowDownloadThread();
                setComboSelectRow(getComboValue());
                dynamicDownloadThread = null;
            }
        };
        dynamicDownloadThread.start();
    }
    /**
     * ����comboѡ����
     * @param value String
     */
    public void setComboSelectRow(String value)
    {
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return;
        if(getPanelPopupMenu() == null)
            setPanelPopupMenu(getDefaultPanelPopupMenu());
        popupMenuRetrieve();
        TParm parm = getPopupMenuData();
        if(parm == null)
            return;
        Vector columns = (Vector)parm.getData("SYSTEM","COLUMNS");
        if(columns == null || columns.size() == 0)
            return;
        
        //System.out.println("=======TTextFormatBase columns========"+columns);
        //System.out.println("=======parm========"+parm);
        
        getTablePopupMenu().setParmValue(parm);
        if(parm == null || parm.getErrCode() < 0 || parm.getCount() == 0)
            return;
        if(value != null && value.length() > 0)
        {
            int count = parm.getCount();
            String name = getValueColumn();
            if (name == null || name.length() == 0)
                name = (String) columns.get(0);
            for (int i = 0; i < count; i++)
            {
                String v = parm.getValue(name, i);
                if (v.equals(value))
                {
                    getTablePopupMenu().setSelectedRow(i);
                    //�иı�ı���ʾ����
                    doShowDownValueForRow();
                    break;
                }
            }
        }
    }
    /**
     * ���ö�̬����
     * @param dynamicDownload boolean
     */
    public void setDynamicDownload(boolean dynamicDownload)
    {
        this.dynamicDownload = dynamicDownload;
    }
    /**
     * �Ƿ�̬����
     * @return boolean
     */
    public boolean isDynamicDownload()
    {
        return dynamicDownload;
    }
    /**
     * ������ʾ�߳�����
     */
    public void startShowDownloadThread()
    {
        if(showDownloadThread != null)
            return;
        showDownloadThread = new Thread()
        {
            public void run()
            {
                try{
                    while (dynamicDownloadThread != null)
                    {
                        showDownloadDrawVisible = !showDownloadDrawVisible;
                        repaint();
                        sleep(500);
                    }
                }catch(Exception e)
                {
                }
                showDownloadThread = null;
                showDownloadDrawVisible = false;
                repaint();
            }
        };
        showDownloadThread.start();
    }
    /**
     * ȥ��һ������
     */
    public void goNextFocus()
    {
        if(getNextFocus() == null || getNextFocus().length() == 0)
            return;
        callFunction(getNextFocus() + "|grabFocus");
    }
    /**
     * ������һ������
     * @param nextFocus String
     */
    public void setNextFocus(String nextFocus)
    {
        this.nextFocus = nextFocus;
    }
    /**
     * �õ���һ������
     * @return String
     */
    public String getNextFocus()
    {
        return nextFocus;
    }
    /**
     * �����޸�SQL
     * @param isModifySQL boolean
     */
    public void setModifySQL(boolean isModifySQL)
    {
        this.isModifySQL = isModifySQL;
    }
    /**
     * �Ƿ��޸�SQL
     * @return boolean
     */
    public boolean isModifySQL()
    {
        return isModifySQL;
    }
    /**
     * ���ô���һ������
     * @param hisOneNullRow boolean
     */
    public void setHisOneNullRow(boolean hisOneNullRow)
    {
        this.hisOneNullRow = hisOneNullRow;
    }
    /**
     * �õ�����һ������
     * @return boolean
     */
    public boolean hisOneNullRow()
    {
        return hisOneNullRow;
    }
    /**
     * �õ���ǩֵ
     * @param tag String
     * @return Object
     */
    public Object getTagValue(String tag)
    {
        if(!StringTool.isTag(tag))
            return tag;
        tag = StringTool.getTag(tag);
        Object value = callMessage(tag + "|getValue");
        if(value == null)
            return "";
        return value;
    }
    /**
     * �õ�Table�����ʾ�ı�
     * @param value String
     * @return String
     */
    public String getTableShowValue(String value)
    {
        if(value == null || value.length() == 0 || value.equals("null"))
            return "";
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return getText();
        if(getPanelPopupMenu() == null)
            setPanelPopupMenu(getDefaultPanelPopupMenu());
        popupMenuRetrieve();
        TParm parm = getPopupMenuData();
        Vector columns = (Vector)parm.getData("SYSTEM","COLUMNS");
        if(columns == null || columns.size() == 0)
            return "";
        getTablePopupMenu().setParmValue(parm);
        if(parm == null || parm.getErrCode() < 0 || parm.getCount() == 0)
            return "";
        if(value == null || value.length() == 0)
            return "";

        int count = parm.getCount();
        String name = getValueColumn();
        if(name == null || name.length() == 0)
            name = (String)columns.get(0);
        for(int i = 0;i < count;i++)
        {
            String v = parm.getValue(name,i);
            if (v.equals(value))
            {
                String s[] = StringTool.parseLine(checkLanguage(getShowColumnList()),";");
                if(s.length == 0)
                    s = new String[]{(String)columns.get(0)};
                StringBuffer sb = new StringBuffer();
                for(int j = 0;j < s.length;j ++)
                {
                    if(sb.length() > 0)
                        sb.append(" ");
                    sb.append(parm.getValue(s[j].trim().toUpperCase(),i));
                }
                return sb.toString();
            }
        }
        return getText();
    }
    /**
     * �������ֶ���
     * @param languageMap String zh|en
     */
    public void setLanguageMap(String languageMap)
    {
        this.languageMap = languageMap;
    }
    /**
     * �õ����ֶ���
     * @return String zh|en
     */
    public String getLanguageMap()
    {
        return languageMap;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        return language;
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
        if(getTablePopupMenu() != null)
            getTablePopupMenu().changeLanguage(getLanguage());
        setComboSelectRow(getComboValue());
    }
    /**
     * ��������
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * �õ�����
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * �õ�����
     * @return Font
     */
    public Font getFont() {
        Font f = getTFont();
        if(f == null)
            return super.getFont();
        String language = (String)TSystem.getObject("Language");
        if(language == null)
            return f;
        if("zh".equals(language))
        {
            Object obj = TSystem.getObject("ZhFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);            
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        if("en".equals(language))
        {
            Object obj = TSystem.getObject("EnFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        return f;
    }

    /**
     * �������ݳ���
     * @param dbPoolName String
     */
    public void setDbPoolName(String dbPoolName) {
        this.dbPoolName = dbPoolName;
    }

    /**
     * �õ����ݳ���
     * @return String
     */
    public String getDbPoolName() {
        return dbPoolName;
    }
    
    
    /**
     * ��������ߴ�
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * �õ�����ߴ�
     * @return int
     */
    public int getFontSize()
    {
        return getTFont().getSize();
    }
    
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
    
    

}
