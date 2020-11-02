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
 * <p>Title: 文本编辑器</p>
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
     * 文本
     */
    private String text = "";
    /**
     * 开始点
     */
    private int startPoint;
    /**
     * 当前光标位置
     */
    private int cursorPoint;
    /**
     * 选中开始位置
     */
    private int selectStart;
    /**
     * 选中结束位置
     */
    private int selectEnd;
    /**
     * 显示光标
     */
    private boolean showCursor;
    /**
     * 光标闪烁线称
     */
    private CursorThread cursorThread;
    /**
     * 光标闪烁休眠时间
     */
    private int cursorSleepCount;
    /**
     * 左侧宽度
     */
    private int leftWidth = 0;
    /**
     * 右侧宽度
     */
    private int rightWidth = 1;
    /**
     * 对其方式
     */
    private int horizontalAlignment;
    /**
     * 输入字符的长度
     */
    private int inputLength;
    /**
     * 格式
     */
    private String format;
    /**
     * 格式类型
     */
    private String formatType;
    /**
     * 存在下拉按钮
     */
    private boolean showDownButton = false;
    /**
     * 下拉按钮风格
     */
    private int downStyle;
    /**
     * 鼠标点住下拉按钮
     */
    private boolean downButtonMouseDown;
    /**
     * 下拉按钮宽度
     */
    private static int downButtonWidth = 15;
    /**
     * 弹出菜单
     */
    private TPopupMenu popupMenu;
    /**
     * 弹出菜单面板
     */
    private TPanel panelPopupMenu;
    /**
     * 弹出菜单Table
     */
    private TTable tablePopupMenu;
    /**
     * 弹出菜单宽度
     */
    private int popupMenuWidth = 200;
    /**
     * 弹出菜单高度
     */
    private int popupMenuHeight = 100;
    /**
     * 弹出菜单表头
     */
    private String popupMenuHeader;
    /**
     * 弹出菜单英文表头
     */
    private String popupMenuEnHeader;
    /**
     * 弹出菜单SQL
     */
    private String popupMenuSQL;
    /**
     * 弹出菜单数据
     */
    private TParm popupMenuData;
    /**
     * 弹出菜单过滤条件
     */
    private String popupMenuFilter;
    /**
     * 弹出菜单过滤条件列表
     */
    private TList popupMenuFilterList;
    /**
     * 显示全部数据
     */
    private boolean popupMenuShowAllData;
    /**
     * 输入时弹出
     */
    private boolean inputPopupMenu = true;
    /**
     * 显示列
     */
    private String showColumnList;
    /**
     * 值列
     */
    private String valueColumn;
    /**
     * 设置Combo值
     */
    private String comboValue;
    /**
     * 动态下载
     */
    private boolean dynamicDownload;
    /**
     * 动态下载线程
     */
    private Thread dynamicDownloadThread;
    /**
     * 下载显示线程
     */
    private Thread showDownloadThread;
    /**
     * 下载显示线程绘制状态
     */
    private boolean showDownloadDrawVisible;
    /**
     * 下一个焦点
     */
    private String nextFocus;
    /**
     * 修改SQL
     */
    private boolean isModifySQL = true;
    /**
     * 存在一个空行
     */
    private boolean hisOneNullRow = false;
    /**
     * Table连接
     */
    public JTableBase tableBase;
    
    /**
     * Table连接
     */
    public JTableSortBase tableSortBase;
    /**
     * 语种
     */
    private String language;
    /**
     * 语法对照
     */
    private String languageMap;
    /**
     * 字体
     */
    private Font tfont;

    /**
     * 数据池名;
     */
    private String dbPoolName;
    
    
    /**
     * 必填项
     */
    private boolean required;


    /**
     * 构造器
     */
    public TTextFormatBase()
    {
        setBackground(new Color(255,255,255));
        setTFont(TUIStyle.getTextFormatDefaultFont());
        setBorder(BorderFactory.createLineBorder(new Color(127,157,185), 1));
    }
    /**
     * 设置文本
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
     * 得到文本
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
     * 设置左侧宽度
     * @param leftWidth int
     */
    public void setLeftWidth(int leftWidth)
    {
        this.leftWidth = leftWidth;
        repaint();
    }
    /**
     * 得到左侧宽度
     * @return int
     */
    public int getLeftWidth()
    {
        return leftWidth;
    }
    /**
     * 设置右侧宽度
     * @param rightWidth int
     */
    public void setRightWidth(int rightWidth)
    {
        this.rightWidth = rightWidth;
        repaint();
    }
    /**
     * 得到右侧宽度
     * @return int
     */
    public int getRightWidth()
    {
        return rightWidth;
    }
    /**
     * 设置对其方式
     * @param horizontalAlignment int
     */
    public void setHorizontalAlignment(int horizontalAlignment)
    {
        this.horizontalAlignment = horizontalAlignment;
        resetCursorSize();
        repaint();
    }
    /**
     * 得到对其方式
     * @return int
     */
    public int getHorizontalAlignment()
    {
        return horizontalAlignment;
    }
    /**
     * 设置输入字符的长度
     * @param inputLength int 0 不限制
     */
    public void setInputLength(int inputLength)
    {
        this.inputLength = inputLength;
    }
    /**
     * 得到输入字符的长度
     * @return int 0 不限制
     */
    public int getInputLength()
    {
        return inputLength;
    }
    /**
     * 设置格式
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
     * 得到格式
     * @return String
     */
    public String getFormat()
    {
        return format;
    }
    /**
     * 设置格式类型
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
     * 得到格式类型
     * @return String
     */
    public String getFormatType()
    {
        return formatType;
    }
    /**
     * 设置失去焦点动作
     * @param action String
     */
    public void setFocusLostAction(String action)
    {
        actionList.setAction("focusLostAction",action);
    }
    /**
     * 得到失去焦点动作
     * @return String
     */
    public String getFocusLostAction()
    {
        return actionList.getAction("focusLostAction");
    }
    /**
     * 设置单击动作
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * 得到单击动作
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * 设置双击动作
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * 得到双击动作
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * 设置右击动作
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * 得到右击动作
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * 设置参数
     * @param value Object
     */
    public void setValue(Object value)
    {
        //返回Combo值
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
     * 得到参数
     * @return Object
     */
    public Object getValue()
    {
        //返回Combo值
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
     * 设置显示下拉按钮
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
     * 是否显示下拉按钮
     * @return boolean
     */
    public boolean isShowDownButton()
    {
        return showDownButton;
    }
    /**
     * 测试int格式
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
     * 测试Long格式
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
     * 测试小数格式
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
     * 检测格式
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
     * 重画
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
     * 绘制内部图案
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
     * 计算光标位置
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
     * 右击
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
        exeAction(getRightClickedAction());
    }
    /**
     * 点击
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
        exeAction(getClickedAction());
    }
    /**
     * 双击
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
     * 鼠标键按下
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
     * 隐含弹出框
     */
    public void hidePopup()
    {
        if(popupMenu != null && popupMenu.isVisible())
            popupMenu.hidePopup();
    }
    /**
     * 显示日期弹出框
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
     * 显示Combo弹出窗口
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
     * 鼠标键抬起
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
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        if(!isEnabled())
            return;
        super.onMouseEntered(e);
    }
    /**
     * 鼠标离开
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
     * 鼠标拖动
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
     * 鼠标移动
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
     * 鼠标位置在下拉按钮范围
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
     * 得到焦点事件
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
     * 失去焦点事件
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
     * 得到选中字符
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
     * 拷贝
     */
    public void copy()
    {
        StringTool.setClipboard(getSelectText());
    }
    /**
     * 粘贴
     */
    public void paste()
    {
        pasteString(StringTool.getClipboard());
        showCursor();
    }
    /**
     * 剪切
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
     * 键盘按下
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
        if(!isEnabled())
            return;
        int keyCode = e.getKeyCode();
        switch(keyCode)
        {
        case KeyEvent.VK_C://复制
            if(e.isControlDown() && selectStart != selectEnd)
                copy();
            break;
        case KeyEvent.VK_V://粘贴
            if(e.isControlDown())
                paste();
            break;
        case KeyEvent.VK_X://粘贴
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
     * 取消
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
     * 回车事件
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
     * 击打
     * @param e KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {
        if(!isEnabled())
            return;
        if(e.isAltDown() || e.isControlDown())
            return;
        char c = e.getKeyChar();
        //删除
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
        //执行输入时弹出
        doInputPopupMenu();
        //过滤
        filter();
    }
    /**
     * 日期格式粘贴一个字符
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
        //本区测试
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
        //是否是本区的最后一个字符,规范字符格式
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
     * 粘贴一个字符
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
     * 初始化数字格式
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
     * 处理显示零问题
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
     * 粘贴字符串
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
     * 删除选中的字符
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
     * 得到日期弹出框
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
     * 得到Combo弹出框
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
     * 得到TDateEdit
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
     * 改变日动作
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
     * 动作
     */
    public void onDateAction()
    {
        modifyDateAction();
        hidePopup();
        exeAction(getActionMessage());
        grabFocus();
    }
    /**
     * 设置弹出菜单面板
     * @param panelPopupMenu TPanel
     */
    public void setPanelPopupMenu(TPanel panelPopupMenu)
    {
        this.panelPopupMenu = panelPopupMenu;
    }
    /**
     * 得到弹出菜单面板
     * @return TPanel
     */
    public TPanel getPanelPopupMenu()
    {
        return panelPopupMenu;
    }
    /**
     * 得到默认弹出菜单面板
     * @return TPanel
     */
    public TPanel getDefaultPanelPopupMenu()
    {
        TPanel panelPopupMenu = new TPanel();
        panelPopupMenu.setTag(".PopupMenu.panel");
        panelPopupMenu.setLayout(new BorderLayout());
        if(getTablePopupMenu() == null)
            setTablePopupMenu(getDefaultTablePopupMenu());
        //显示数据
        popupMenuShowData();
        panelPopupMenu.add(getTablePopupMenu());
        return panelPopupMenu;
    }
    /**
     * 得到弹出菜单Table
     * @param tablePopupMenu TTable
     */
    public void setTablePopupMenu(TTable tablePopupMenu)
    {
        this.tablePopupMenu = tablePopupMenu;
    }
    /**
     * 设置弹出菜单Table
     * @return TTable
     */
    public TTable getTablePopupMenu()
    {
        return tablePopupMenu;
    }
    /**
     * 得到默认弹出菜单Table
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
     * 弹出菜单显示数据
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
            System.out.println("TTextFormatBase<" + getTag() + ">执行popupMenuShowData错误:数据列小于显示个数");
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
     * 弹出菜单加载数据
     */
    public synchronized void popupMenuRetrieve()
    {
        if(!isModifySQL())
            return;
        String sql = getPopupMenuSQL();
        sql = TypeTool.getString(getTagValue(sql));
        if(sql == null || sql.length() == 0)
            return;
        //$$==============Modified by lx支持多数据源 2011-09-02==========================$$//
        TParm parm =null;
        if(this.getDbPoolName()!=null&&!this.getDbPoolName().equals("")){
            parm = new TParm(getDBTool().select(this.getDbPoolName(),sql));
        }else{
            parm = new TParm(getDBTool().select(sql));
        }


        if(parm.getErrCode() < 0)
            System.out.println("TTextFormatBase<" + getTag() + ">执行popupMenuRetrieve错误:" + parm.getErrText());
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
     * 得到数据库工具
     * @return TJDODBTool
     */
    public TJDODBTool getDBTool()
    {
        return TJDODBTool.getInstance();
    }
    /**
     * 设置弹出菜单宽度
     * @param popupMenuWidth int
     */
    public void setPopupMenuWidth(int popupMenuWidth)
    {
        this.popupMenuWidth = popupMenuWidth;
    }
    /**
     * 得到弹出菜单宽度
     * @return int
     */
    public int getPopupMenuWidth()
    {
        return popupMenuWidth;
    }
    /**
     * 设置弹出菜单高度
     * @param popupMenuHeight int
     */
    public void setPopupMenuHeight(int popupMenuHeight)
    {
        this.popupMenuHeight = popupMenuHeight;
    }
    /**
     * 得到弹出菜单高度
     * @return int
     */
    public int getPopupMenuHeight()
    {
        return popupMenuHeight;
    }
    /**
     * 设置弹出菜单表头
     * @param popupMenuHeader String
     */
    public void setPopupMenuHeader(String popupMenuHeader)
    {
        this.popupMenuHeader = popupMenuHeader;
    }
    /**
     * 得到弹出菜单表头
     * @return String
     */
    public String getPopupMenuHeader()
    {
        return popupMenuHeader;
    }
    /**
     * 设置弹出菜单英文表头
     * @param popupMenuEnHeader String
     */
    public void setPopupMenuEnHeader(String popupMenuEnHeader)
    {
        this.popupMenuEnHeader = popupMenuEnHeader;
    }
    /**
     * 得到弹出菜单英文表头
     * @return String
     */
    public String getPopupMenuEnHeader()
    {
        return popupMenuEnHeader;
    }
    /**
     * 设置弹出菜单SQL
     * @param popupMenuSQL String
     */
    public void setPopupMenuSQL(String popupMenuSQL)
    {
        this.popupMenuSQL = popupMenuSQL;
    }
    /**
     * 得到弹出菜单SQL
     * @return String
     */
    public String getPopupMenuSQL()
    {
        return popupMenuSQL;
    }
    /**
     * 设置弹出菜单数据
     * @param popupMenuData TParm
     */
    public void setPopupMenuData(TParm popupMenuData)
    {
        this.popupMenuData = popupMenuData;
    }
    /**
     * 得到弹出菜单数据
     * @return TParm
     */
    public TParm getPopupMenuData()
    {
        return popupMenuData;
    }
    /**
     * 设置弹出菜单过滤条件
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
     * 得到弹出菜单过滤条件
     * @return String
     */
    public String getPopupMenuFilter()
    {
        return popupMenuFilter;
    }
    /**
     * 设置弹出菜单过滤条件列表
     * @param popupMenuFilterList TList
     */
    public void setPopupMenuFilterList(TList popupMenuFilterList)
    {
        this.popupMenuFilterList = popupMenuFilterList;
    }
    /**
     * 得到弹出菜单过滤条件列表
     * @return TList
     */
    public TList getPopupMenuFilterList()
    {
        return popupMenuFilterList;
    }
    /**
     * 过滤
     */
    public void filter()
    {
        filter(text);
    }
    /**
     * 过滤
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
                    case 0: //完全匹配
                        if (s.equals(value))
                        {
                            isShow = true;
                            break F2;
                        }
                        break;
                    case 1: //左匹配
                        if (s.startsWith(value))
                        {
                            isShow = true;
                            break F2;
                        }
                        break;
                    case 2: //右匹配
                        if (s.endsWith(value))
                        {
                            isShow = true;
                            break F2;
                        }
                        break;
                    case 3: //包含
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
     * 设置输入时弹出
     * @param inputPopupMenu boolean
     */
    public void setInputPopupMenu(boolean inputPopupMenu)
    {
        this.inputPopupMenu = inputPopupMenu;
    }
    /**
     * 是否输入时弹出
     * @return boolean
     */
    public boolean isInputPopupMenu()
    {
        return inputPopupMenu;
    }
    /**
     * 执行输入时弹出
     */
    public void doInputPopupMenu()
    {
        if(!isShowDownButton() || !"combo".equalsIgnoreCase(formatType))
            return;
        showComboPopup();
    }
    /**
     * 执行选中
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
        //行改变改变显示数据
        doShowDownValueForRow();
        selectAll();
    }
    /**
     * 语种对照更换
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
     * 行改变改变显示数据
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
        //设置Combo值
        setComboValue(data.getValue(name.trim().toUpperCase(),row));
        repaint();
    }
    /**
     * 设置显示列
     * @param showColumnList String
     */
    public void setShowColumnList(String showColumnList)
    {
        this.showColumnList = showColumnList;
    }
    /**
     * 得到显示列
     * @return String
     */
    public String getShowColumnList()
    {
        return showColumnList;
    }
    /**
     * 弹出菜单选中Table
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
     * 取消选蓝
     */
    public void cancelSelectAll()
    {
        selectStart = selectEnd = cursorPoint;
        cursorPoint = -1;
        repaint();
    }
    /**
     * 选蓝
     */
    public void selectAll()
    {
        selectText(0,text.length());
    }
    /**
     * 光标防止最后
     */
    public void selectTextLast()
    {
        selectText(text.length(),text.length());
    }
    /**
     * 选蓝
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
     * 设置值列
     * @param valueColumn String
     */
    public void setValueColumn(String valueColumn)
    {
        this.valueColumn = valueColumn;
    }
    /**
     * 得到值列
     * @return String
     */
    public String getValueColumn()
    {
        return valueColumn;
    }
    /**
     * 设置Combo值
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
     * 得到combo值
     * @return String
     */
    public String getComboValue()
    {
        return comboValue;
    }
    /**
     * 得到combo指定值
     * @param name String 列名
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
        //设置Combo值
        return data.getData(name.trim().toUpperCase(),row);
    }
    /**
     * 设置值改变动作
     * @param action String
     */
    public void setEditValueAction(String action)
    {
        actionList.setAction("editValueAction",action);
    }
    /**
     * 得到值改变动作
     * @return String
     */
    public String getEditValueAction()
    {
        return actionList.getAction("editValueAction");
    }
    /**
     * 查询
     */
    public void onQuery()
    {
        setModifySQL(true);
        setComboSelectRow();
    }
    /**
     * 设置Combo选中行
     */
    public synchronized void setComboSelectRow()
    {
        //非动态下载
        if(!isDynamicDownload())
        {
            setComboSelectRow(getComboValue());
            return;
        }
        dynamicDownloadThread = new Thread(){
            public void run()
            {
                //启动下载显示线程
                startShowDownloadThread();
                setComboSelectRow(getComboValue());
                dynamicDownloadThread = null;
            }
        };
        dynamicDownloadThread.start();
    }
    /**
     * 设置combo选中行
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
                    //行改变改变显示数据
                    doShowDownValueForRow();
                    break;
                }
            }
        }
    }
    /**
     * 设置动态下载
     * @param dynamicDownload boolean
     */
    public void setDynamicDownload(boolean dynamicDownload)
    {
        this.dynamicDownload = dynamicDownload;
    }
    /**
     * 是否动态下载
     * @return boolean
     */
    public boolean isDynamicDownload()
    {
        return dynamicDownload;
    }
    /**
     * 下载显示线程启动
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
     * 去下一个焦点
     */
    public void goNextFocus()
    {
        if(getNextFocus() == null || getNextFocus().length() == 0)
            return;
        callFunction(getNextFocus() + "|grabFocus");
    }
    /**
     * 设置下一个焦点
     * @param nextFocus String
     */
    public void setNextFocus(String nextFocus)
    {
        this.nextFocus = nextFocus;
    }
    /**
     * 得到下一个焦点
     * @return String
     */
    public String getNextFocus()
    {
        return nextFocus;
    }
    /**
     * 设置修改SQL
     * @param isModifySQL boolean
     */
    public void setModifySQL(boolean isModifySQL)
    {
        this.isModifySQL = isModifySQL;
    }
    /**
     * 是否修改SQL
     * @return boolean
     */
    public boolean isModifySQL()
    {
        return isModifySQL;
    }
    /**
     * 设置存在一个空行
     * @param hisOneNullRow boolean
     */
    public void setHisOneNullRow(boolean hisOneNullRow)
    {
        this.hisOneNullRow = hisOneNullRow;
    }
    /**
     * 得到存在一个空行
     * @return boolean
     */
    public boolean hisOneNullRow()
    {
        return hisOneNullRow;
    }
    /**
     * 得到标签值
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
     * 得到Table组件显示文本
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
     * 设置语种对照
     * @param languageMap String zh|en
     */
    public void setLanguageMap(String languageMap)
    {
        this.languageMap = languageMap;
    }
    /**
     * 得到语种对照
     * @return String zh|en
     */
    public String getLanguageMap()
    {
        return languageMap;
    }
    /**
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * 设置语种
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
     * 设置字体
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * 得到字体
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
     * 设置数据池名
     * @param dbPoolName String
     */
    public void setDbPoolName(String dbPoolName) {
        this.dbPoolName = dbPoolName;
    }

    /**
     * 得到数据池名
     * @return String
     */
    public String getDbPoolName() {
        return dbPoolName;
    }
    
    
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * 得到字体尺寸
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
