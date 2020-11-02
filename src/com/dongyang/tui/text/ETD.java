package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.util.TList;
import com.dongyang.tui.DInsets;
import java.awt.Rectangle;
import com.dongyang.util.StringTool;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.tui.DPoint;
import java.util.List;
import com.dongyang.data.TParm;
import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

/**
 *
 * <p>Title: 表格单元格</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.9.7
 * @author whao 2013~
 * @version 1.0
 */
public class ETD implements EComponent,EEvent,IExeAction
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 父类
     */
    private EComponent parent;
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 修改状态
     */
    private boolean modify;
    /**
     * 模型
     */
    private ETDModel model;
    /**
     * 列号
     */
    private int columnIndex = -1;
    /**
     * 对其位置
     * 0 left
     * 1 center
     * 2 right
     */
    private int alignment = 0;
    /**
     * 上一个连接TD
     */
    private ETD previousLinkTD;
    /**
     * 下一个连接TD
     */
    private ETD nextLinkTD;
    /**
     * 上一个TD索引号码(保存专用)
     */
    private int previousTDIndex;
    /**
     * 最大高度
     */
    private int maxHeight = -1;
    /**
     * 设置尺寸调整
     */
    private ResetPanel resetPanel;
    /**
     * 是否选中
     */
    private boolean isSelected;
    /**
     * 选中对象
     */
    private CSelectTDModel selectedModel;
    /**
     * 跨越X
     */
    private int spanX;
    /**
     * 跨越Y
     */
    private int spanY;
    /**
     * 显示
     */
    private boolean visible = true;
    /**
     * 联合的TD
     */
    private ETD uniteTD;
    /**
     * 构造器
     */
    public ETD()
    {
        components = new TList();
        //创建尺寸调整对象
        setResetPanel(new ResetPanel(this));
    }
    /**
     * 构造器
     * @param tr ETR
     */
    public ETD(ETR tr)
    {
        this();
        setParent(tr);
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getPage().getPM();
    }
    /**
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到焦点管理器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 增加成员
     * @param component EPanel
     */
    public void add(EPanel component)
    {
        if (component == null)
            return;
        components.add(component);
        component.setParent(this);
    }
    /**
     * 增加成员
     * @param index int
     * @param component EPanel
     */
    public void add(int index,EPanel component)
    {
        if (component == null)
            return;
        components.add(index,component);
        component.setParent(this);
    }
    /**
     * 删除成员
     * @param component EPanel
     */
    public void remove(EPanel component)
    {
        components.remove(component);
    }
    /**
     * 删除成员
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
    }
    /**
     * 删除全部
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * 得到成员
     * @param index int
     * @return EPanel
     */
    public EPanel get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (EPanel) components.get(index);
    }
    /**
     * 查找位置
     * @param component EComponent
     * @return int
     */
    public int indexOf(EComponent component)
    {
        if(component == null)
            return -1;
        return components.indexOf(component);
    }
    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * 设置父类
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return EComponent
     */
    public EComponent getParent()
    {
        return parent;
    }
    /**
     * 设置边框
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setBounds(int x,int y,int width,int height)
    {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * 设置横坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 得到横坐标
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到纵坐标
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        setX(x);
        setY(y);
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
     * 得到TR
     * @return ETR
     */
    public ETR getTR()
    {
        EComponent com = getParent();
        if(com == null || !(com instanceof ETR))
            return null;
        return (ETR)getParent();
    }
    /**
     * 得到Table
     * @return ETable
     */
    public ETable getTable()
    {
        ETR tr = getTR();
        if(tr == null)
            return null;
        return tr.getTable();
    }
    /**
     * 得到页面
     * @return EPage
     */
    public EPage getPage()
    {
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getPage();
    }
    /**
     * 得到内部尺寸
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return getTable().getTDInsets();
    }
    /**
     * 得到内部X
     * @return int
     */
    public int getInsetsX()
    {
        return getInsets().left;
    }
    /**
     * 得到内部Y
     * @return int
     */
    public int getInsetsY()
    {
        return getInsets().top;
    }
    /**
     * 得到内部宽度
     * @return int
     */
    public int getInsetsWidth()
    {
        DInsets insets = getInsets();
        return getWidth() - insets.left - insets.right;
    }
    /**
     * 得到内部高度
     * @return int
     */
    public int getInsetsHeight()
    {
        DInsets insets = getInsets();
        return getHeight() - insets.top - insets.bottom;
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }
    /**
     * 修改面板
     */
    public void modifyPanels()
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if (panel == null)
                continue;
            panel.setModify(true);
        }
    }
    /**
     * 是否阅览状态
     * @return boolean
     */
    public boolean isPreview()
    {
        return getTR().isPreview();
    }
    /**
     * 是否是Table的最后一行
     * @return boolean
     */
    public boolean isLastTR()
    {
        return getTR().isLastTR();
    }
    /**
     * 是否是Table的第一行
     * @return boolean
     */
    public boolean isFirstTR()
    {
        return getTR().isFirstTR();
    }
    /**
     * 是否是最后一个表格
     * @return boolean
     */
    public boolean isLastTD()
    {
        return getTR().isLastTD(this);
    }
    /**
     * 是否是第一个单元格
     * @return boolean
     */
    public boolean isFirstTD()
    {
        return getTR().isFirstTD(this);
    }
    /**
     * 是否是最后一行的TD
     * @return boolean
     */
    public boolean isLastRowTD()
    {
        if(isLastTR())
            return true;
        if(getSpanY() == 0)
            return false;
        if(getSpanY() == getTable().getThisTableLastRow() - getTR().getRow())
            return true;
        return false;
    }
    /**
     * 计算缩放比例
     * @param x double
     * @return int
     */
    public int computeZoom(double x)
    {
        if(x == 0)
            return 0;
        return (int) (x * (double)getZoom() / 75.0);
    }
    /**
     * 绘制行间隔横线根据数据
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintRowShowWLineForData(Graphics g,int x,int y,int width,int height,boolean print)
    {
        CTable ctable = getTable().getHeadTable().getCTable();
        if(ctable == null)
            return;
        TParm parm = ctable.getData();
        if(parm == null)
            return;
        if(getTR().getEndTR() != getTR())
            return;
        ETRModel model = getTR().getHeadTR().getModel();
        if(model == null)
            return;
        int row = model.getRow();
        if(row < 0 || row >= parm.getCount())
            return;
        if(!parm.getBoolean(".TableRowLineShow",row))
            return;
        g.setColor(new Color(0, 0, 0));
        int x1 = x;
        int y1 = y + height;
        int x2 = x + width;
        int y2 = y + height;
        //调整到两行之间
        double dh = (double)getTR().getInsets().bottom +
                    (double)getTable().getHSpace() / 2.0;
        int h = print?(int)dh:computeZoom(dh);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        g.drawLine(x1,y1 + h,x2,y2 + h);
    }
    /**
     * 绘制行间隔横线
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintRowShowWLine(Graphics g,int x,int y,int width,int height,boolean print)
    {
        //最后一行不绘制
        if(isLastRowTD() && (getTable().isShowBorder() || getTable().isShowBorder2()))
            return;
        boolean isShowWLine = getTable().isShowWLine();
        if(!isShowWLine && isPreview())
            return;
        //设置颜色
        if (isShowWLine)
            g.setColor(new Color(0, 0, 0));
        else
            g.setColor(new Color(168, 168, 168));
        int x1 = x;
        int y1 = y + height;
        int x2 = x + width;
        int y2 = y + height;
        //调整到两列之间
        int w = print?(int)((double)getTable().getWSpace() / 2.0):
                computeZoom((double)getTable().getWSpace() / 2.0);
        //第一个单元格考虑表格内尺寸和行内尺寸的影响
        if(isFirstTD())
        {
            int left = getTR().getInsets().left;
            if(!getTable().isShowBorder())
                left += getTable().getInsets().left;
            if(left > 0)
                x1 -= print?left:computeZoom(left);
        }else
            x1 -= w;
        //最后一个单元格考虑表格内尺寸和行内尺寸的影响
        if(isLastTD())
        {
            int right = getTR().getInsets().right;
            if(!getTable().isShowBorder())
                right += getTable().getInsets().right;
            if(right > 0)
                x2 += print?right:computeZoom(right) - 1;
        }else
            x2 += w;
        //调整到两行之间
        double dh = (double)getTR().getInsets().bottom +
                    (double)getTable().getHSpace() / 2.0;
        int h = print?(int)dh:computeZoom(dh);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        g.drawLine(x1,y1 + h,x2,y2 + h);
    }
    public String getPValue()
    {
        ETRModel module = getTR().getModel();
        if(module == null)
            return "";
        int row = module.getRow();
        if(row < 0)
            return "";
        CTable cTable = getTable().getHeadTable().getCTable();
        if(cTable == null)
            return "";
        String tableName = cTable.getTableID();
        if(tableName == null || tableName.length() == 0)
            return "";
        Object obj = getPM().getFileManager().getParameter();
        if(!(obj instanceof TParm))
            return "";
        TParm parm = (TParm) obj;
        if(parm.getData() == null)
            return "";
        parm = parm.getParm(tableName);
        Vector v = (Vector)parm.getData("TABLE_VALUE");
        if(v == null || v.size() == 0)
            return "";
        String s = (String)v.get(row);
        return s;
    }
    public boolean isshowHLine()
    {
        String s = getPValue();
        if(s == null || s.length() == 0)
            return true;
        String value = getSValue(s,"HLine");
        if(value == null || value.length() == 0)
            return true;
        return StringTool.getBoolean(value);
    }
    public String getSValue(String s,String name)
    {
        String ss[] = StringTool.parseLine(s,";");
        for(int i = 0;i < ss.length;i++)
        {
            int index = ss[i].indexOf("=");
            String s1 = ss[i].substring(0,index);
            String s2 = ss[i].substring(index + 1).trim();
            if(!s1.toUpperCase().trim().equals(name.toUpperCase().trim()))
                continue;
            return s2;
        }
        return "";
    }
    /**
     * 绘制行间隔竖线
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintRowShowHLine(Graphics g,int x,int y,int width,int height,boolean print)
    {
        //最后一个单元格不绘制
        if(isLastTD())
            return;
        //是否绘制线
        if(!isshowHLine())
            return;
        boolean isShowHLine = getTable().isShowHLine();
        if(!isShowHLine && isPreview())
            return;
        //设置颜色
        if (isShowHLine)
            g.setColor(new Color(0, 0, 0));
        else
            g.setColor(new Color(168, 168, 168));
        int x1 = x + width;
        int y1 = y - (print?getTR().getInsets().top:computeZoom(getTR().getInsets().top));
        int x2 = x + width;
        int y2 = y + height + (print?getTR().getInsets().bottom:computeZoom(getTR().getInsets().bottom));
        //两行间距
        int h = print?(int)((double)getTable().getHSpace() / 2.0):computeZoom((double)getTable().getHSpace() / 2.0);
        if(isFirstTR())
        {
            int top = 0;
            if(!getTable().isShowBorder())
                top = getTable().getInsets().top;
            if(top > 0)
                y1 -= print?top:computeZoom(top);
        }else
            y1 -= h;
        if(isLastRowTD())
        {
            int bottom = 0;
            if(!getTable().isShowBorder())
                bottom = getTable().getInsets().bottom;
            if(bottom > 0)
                y2 += print?bottom:computeZoom(bottom);
            y2 -= 1;
        }else
            y2 += h;

        //调整到两列之间
        int w = print?(int)((double)getTable().getWSpace() / 2.0):computeZoom((double)getTable().getWSpace() / 2.0);
        g.drawLine(x1 + w,y1,x2 + w,y2);
    }
    /**
     * 绘制
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int x,int y,int width,int height)
    {
        if(!isVisible())
            return;
        if(getViewManager().isTDDebug())
        {
            g.setColor(new Color(0, 0, 128));
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
        }
        //选中表格行反色
        if(isSelected())
        {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(x,y,width,height);
        }
        //绘制行间隔横线
        paintRowShowWLine(g,x,y,width,height,false);
        //绘制行间隔竖线
        paintRowShowHLine(g,x,y,width,height,false);
        //绘制行间隔横线根据数据
        paintRowShowWLineForData(g,x,y,width,height,false);

        double zoom = getZoom() / 75.0;
        //绘制面板
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            int pX = (int)(panel.getX() * zoom + 0.5);
            int pY = (int)(panel.getY() * zoom + 0.5);
            int pW = (int)(panel.getWidth() * zoom + 0.5);
            int pH = (int)(panel.getHeight() * zoom + 0.5);

            Rectangle r = g.getClipBounds();
            if(r.getX() > x + pX + pW ||
               r.getY() > y + pY + pH ||
               r.getX() + r.getWidth() < x + pX ||
               r.getY() + r.getHeight() < y + pY)
                continue;
            //绘制
            panel.paint(g,x + pX,y + pY,pW,pH);
        }
    }
    /**
     * 打印
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g,int x,int y)
    {
        if(!isVisible())
            return;
        //绘制行间隔横线
        paintRowShowWLine(g,x,y,width,height,true);
        //绘制行间隔竖线
        paintRowShowHLine(g,x,y,width,height,true);
        //绘制行间隔横线根据数据
        paintRowShowWLineForData(g,x,y,width,height,true);
        //绘制面板
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            //绘制
            panel.print(g,x + panel.getX(),y + panel.getY());
        }
    }
    /**
     * 得到鼠标所在面板
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInPanelIndex(int mouseX,int mouseY)
    {
        double zoom = getZoom() / 75.0;
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if((int)(panel.getX() * zoom + 0.5) <= mouseX &&
               (int)((panel.getX() + panel.getWidth()) * zoom + 0.5) >= mouseX &&
               (int)(panel.getY() * zoom + 0.5) <= mouseY &&
               (int)((panel.getY() + panel.getHeight()) * zoom + 0.5) >= mouseY)
                return i;
        }
        return -1;
    }
    /**
     * 左键按下
     * @param mouseX int
     * @param mouseY int
     * @return int 点选类型
     * -1 没有选中
     * 1 EText
     * 2 ETD
     * 3 ETR Enter
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        //鼠标所在面板
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex == -1)
        {
            if(size() > 0)
                panelIndex = size() - 1;
            else
            {
                if(getPreviousLinkTD() == null)
                    return -1;
                return getPreviousLinkTD().onMouseLeftPressed(mouseX,mouseY);
            }
        }
        EPanel panel = get(panelIndex);
        int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
        //事件传递
        return panel.onMouseLeftPressed(x,y);
    }
    /**
     * 右键按下
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        //鼠标所在面板
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex == -1)
        {
            if(size() > 0)
                panelIndex = size() - 1;
            else
            {
                if(getPreviousLinkTD() == null)
                    return;
                getPreviousLinkTD().onMouseRightPressed(mouseX,mouseY);
                return;
            }
        }
        EPanel panel = get(panelIndex);
        int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
        //事件传递
        panel.onMouseRightPressed(x,y);
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        //鼠标所在面板
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex != -1)
        {
            EPanel panel = get(panelIndex);
            int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
            //事件传递
            return panel.onDoubleClickedS(x,y);
        }
        return false;
    }
    /**
     * 新面板
     * @return EPanel
     */
    public EPanel newPanel()
    {
        EPanel panel = new EPanel(this);
        //设置横坐标
        panel.setX(getInsetsX());
        //设置纵坐标
        panel.setWidth(getInsetsWidth());
        add(panel);
        return panel;
    }
    /**
     * 新面板
     * @param index int
     * @return EPanel
     */
    public EPanel newPanel(int index)
    {
        EPanel panel = new EPanel(this);
        //设置横坐标
        panel.setX(getInsetsX());
        //设置纵坐标
        panel.setWidth(getInsetsWidth());
        add(index,panel);
        return panel;
    }
    /**
     * 得到最后一个
     * @return EText
     */
    public EText getLastText()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            EText text = panel.getLastText();
            if(text != null)
                return text;
        }
        return null;
    }
    /**
     * 查找第一个
     * @return EText
     */
    public EText getFirstText()
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            EText text = panel.getFirstText();
            if(text != null)
                return text;
        }
        return null;
    }
    /**
     * 查找前一个组件
     * @param panel EPanel
     * @return EText
     */
    public EText getPreviousText(EPanel panel)
    {
        if(panel == null)
            return null;
        int index = indexOf(panel);
        if(index == -1)
            return null;
        for(int i = index - 1;i >= 0;i--)
        {
            EPanel p = get(i);
            if(p == null)
                continue;
            EText text = p.getLastText();
            if(text != null)
                return text;
        }
        if(getPreviousLinkTD() != null)
            return getPreviousLinkTD().getLastText();
        ETR tr = getTR();
        if(tr == null)
            return null;
        EText text = tr.getPreviousText(this);
        if(text != null)
            return text;
        //temp
        return null;
    }
    /**
     * 查找下一个组件
     * @param panel EPanel
     * @return EText
     */
    public EText getNextText(EPanel panel)
    {
        if(panel == null)
            return null;
        int index = indexOf(panel);
        if(index == -1)
            return null;
        for(int i = index + 1;i < size();i++)
        {
            EPanel p = get(i);
            if(p == null)
                continue;
            EText text = p.getFirstText();
            if(text != null)
                return text;
        }
        if(getNextLinkTD() != null)
            return getNextLinkTD().getFirstText();

        ETR tr = getTR();
        EText text = tr.getNextText(this);
        if(text != null)
            return text;
        //temp
        return null;
    }
    /**
     * 设置是否修改状态
     * @param modify boolean
     */
    public void setModify(boolean modify)
    {
        if(this.modify == modify)
            return;
        if(modify)
        {
            this.modify = true;
            //修改会传
            update(false);
            if(getParent() != null)
                getParent().setModify(true);
            return;
        }
        for(int i = 0;i < size();i++)
        {
            EComponent com = get(i);
            if(com == null)
                continue;
            if(com.isModify())
                return;
        }
        this.modify = false;
        if(getParent() != null)
            getParent().setModify(false);
    }
    /**
     * 是否修改
     * @return boolean
     */
    public boolean isModify()
    {
        return modify;
    }
    /**
     * 调整尺寸
     */
    public void reset()
    {
        //if(!isModify())
        //    return;
        //清零
        getResetPanel().clear();
        //设置尺寸范围
        getResetPanel().setRectangle(getInsetsX(),
                                     getInsetsY(),
                                     getInsetsWidth(),
                                     getMaxHeight() - getInsets().top - getInsets().bottom);
        //调整尺寸
        getResetPanel().reset();
        setModify(false);
    }
    /**
     * 调整尺寸
     * @param index int
     */
    public void reset(int index)
    {
        if (index < 0 || index >= size())
            return;
        int x = getInsetsX();
        int y = getInsetsY();
        int width = getInsetsWidth();
        for(int i = index;i < size();i++)
        {
            EPanel panel = get(i);
            if (panel == null)
                continue;
            /*if (!panel.isModify() && panel.getY() != y)
                panel.setModify(true);
            if (!panel.isModify())
            {
                y = panel.getY() + panel.getHeight();
                continue;
            }*/
            panel.setY(y);
            panel.setX(x);
            panel.setWidth(width);
            if(getMaxHeight() > 0)
            {
                panel.setMaxHeight(getMaxHeight() - y - getInsets().bottom);
                panel.setModify(true);
            }
            panel.reset();
            y = panel.getY() + panel.getHeight();
        }
        if(size() == 0)
            return;
        EPanel panel = get(size() - 1);
        DInsets insets = getInsets();
        int height = panel.getY() + panel.getHeight() + insets.bottom;
        setHeight(height);
    }
    /**
     * 查找组件位置
     * @param td ETD
     * @return int
     */
    public int findIndex(ETD td)
    {
        ETR tr = getTR();
        if(tr == null)
            return -1;
        return tr.indexOf(td);
    }
    /**
     * 查找自己的位置
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
    }
    /**
     * 得到坐标位置
     * @return String
     */
    public String getIndexString()
    {
        ETR tr = getTR();
        if(tr == null)
            return "Parent:Null";
        return tr.getIndexString() + ",TD:" + findIndex();
    }
    public String toString()
    {
        return "<ETD size=" + size() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",isModify=" + isModify() +
                ",visible=" + isVisible() +
                ",uniteTD=" + (getUniteTD() == null?"null":getUniteTD().getIndexString()) +
                ",index=" + getIndexString() + ">";
    }
    /**
     * 调试对象组成
     * @param i int
     */
    public void debugShow(int i)
    {
        System.out.println(StringTool.fill(" ",i) + this);
        for(int j = 0;j < size();j++)
        {
            EPanel panel = get(j);
            panel.debugShow(i + 2);
        }
    }
    /**
     * 调试修改
     * @param i int
     */
    public void debugModify(int i)
    {
        if(!isModify())
            return;
        for(int j = 0;j < size();j++)
        {
            EPanel panel = get(j);
            panel.debugModify(i + 2);
        }
    }
    /**
     * 设置文本
     * @param s String
     */
    public void setString(String s)
    {
        //删除全部
        removeAll();
        String data[];
        if(s == null || s.length() == 0)
            data = new String[]{""};
        else
            data = StringTool.separateEnter(s);
        for(int i = 0;i < data.length;i++)
        {
            EPanel panel = newPanel();
            panel.setString(data[i]);
        }
    }
    /**
     * 得到文本
     * @return String
     */
    public String getString()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i++)
        {
            if(i > 0)
                sb.append("\n");
            sb.append(get(i).getString());
        }
        return sb.toString();
    }
    /**
     * 设置模型
     * @param model ETDModel
     */
    public void setModel(ETDModel model)
    {
        this.model = model;
    }
    /**
     * 得到模型
     * @return ETDModel
     */
    public ETDModel getModel()
    {
        return model;
    }
    /**
     * 创建模型
     * @return ETDModel
     */
    public ETDModel createModel()
    {
        ETDModel model = new ETDModel(this);
        setModel(model);
        return model;
    }
    /**
     * 数据同步
     * @param b boolean true 传送TD false 回收TD
     */
    public void update(boolean b)
    {
        if(getModel() == null)
            return;
        //getModel().update(b);
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeInt(1,getWidth(),0);
        s.writeInt(2,getHeight(),0);
        s.writeInt(3,getColumnIndex(),0);
        s.writeInt(4,getAlignment(),0);
        if(getNextLinkTD() != null)
        {
            int index = getTDSaveManager().add(this);
            getNextLinkTD().setPreviousTDIndex(index);
            s.writeShort(5);
        }
        if(getPreviousLinkTD() != null)
        {
            s.writeShort(6);
            s.writeInt(getPreviousTDIndex());
        }
        s.writeInt(7,getSpanX(),0);
        s.writeInt(8,getSpanY(),0);
        s.writeBoolean(9,isVisible(),true);
        if(isSpan())
            getTDSaveUniteManager().add(this);
        if(getUniteTD() != null)
        {
            int index = getTDSaveUniteManager().indexOf(getUniteTD());
            s.writeShort(10);
            s.writeInt(index);
        }
    }
    /**
     * 读对象属性
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        switch(id)
        {
        case 1:
            setWidth(s.readInt());
            return true;
        case 2:
            setHeight(s.readInt());
            return true;
        case 3:
            setColumnIndex(s.readInt());
            return true;
        case 4:
            setAlignment(s.readInt());
            return true;
        case 5:
            getTDSaveManager().add(this);
            return true;
        case 6:
            ETD td = getTDSaveManager().get(s.readInt());
            setPreviousLinkTD(td);
            td.setNextLinkTD(this);
            return true;
        case 7:
            setSpanX(s.readInt());
            return true;
        case 8:
            setSpanY(s.readInt());
            return true;
        case 9:
            setVisible(s.readBoolean());
            return true;
        case 10:
            setUniteTD(getTDSaveUniteManager().get(s.readInt()));
            return true;
        }
        return false;
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s,int c)
      throws IOException
    {
        s.WO("<ETD Width=" + getWidth() + " Height=" + getHeight() + " >",c);
        for(int i = 0;i < size();i ++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.writeObjectDebug(s,c + 1);
        }
        s.WO("</ETD>",c);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
        //保存页
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            EPanel panel = get(i);
            panel.writeObject(s);
        }
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
            //读对象属性
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        if(isSpan())
            getTDSaveUniteManager().add(this);
        //读取行
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EPanel panel = newPanel();
            panel.readObject(s);
        }
    }
    /**
     * 得到横坐标位置
     * @return int
     */
    public int getPointX()
    {
        return getTR().getPointX() + getX();
    }
    /**
     * 得到路径
     * @param list TList
     */
    public void getPath(TList list)
    {
        if(list == null)
            return;
        list.add(0,this);
        getTR().getPath(list);
    }
    /**
     * 得到路径索引
     * @param list TList
     */
    public void getPathIndex(TList list)
    {
        if(list == null)
            return;
        list.add(0,findIndex());
        getTR().getPathIndex(list);
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        getTR().remove(this);
        for(int i = size() - 1;i >= 0;i--)
            get(i).removeThis();
        if(getPreviousLinkTD() != null)
            getPreviousLinkTD().setNextLinkTD(getNextLinkTD());
        if(getNextLinkTD() != null)
            getNextLinkTD().setPreviousLinkTD(getPreviousLinkTD());
    }
    /**
     * 测试使用样式
     */
    public void checkGCStyle()
    {
        for(int i = 0;i < size();i++)
            get(i).checkGCStyle();
    }
    /**
     * 设置列号
     * @param columnIndex int
     */
    public void setColumnIndex(int columnIndex)
    {
        this.columnIndex = columnIndex;
    }
    /**
     * 得到列号
     * @return int
     */
    public int getColumnIndex()
    {
        return columnIndex;
    }
    /**
     * 设置对其位置(当前位置)
     * @param alignment int
     * 0 left
     * 1 center
     * 2 right
     */
    public void setAlignment(int alignment)
    {
        if(getAlignment() == alignment)
            return;
        this.alignment = alignment;
        if(getColumnIndex() == -1)
            return;
        CTable cTable = getCTable();
        if(cTable == null)
            return;
        switch(getTRModuleType())
        {
        case ETRModel.COLUMN_TYPE://列类型
        case ETRModel.COLUMN_EDIT://列编辑
            cTable.getColumnModel().get(getColumnIndex()).setAlignment(alignment);
            break;
        case ETRModel.COLUMN_MAX:
            break;
        case ETRModel.GROUP_HEAD:
            cTable.getColumnModel().get(getColumnIndex()).setAlignment(alignment);
            break;
        case ETRModel.GROUP_SUM:
            cTable.getColumnModel().get(getColumnIndex()).setAlignment(alignment);
            break;
        }
    }
    /**
     * 得到对其位置
     * @return int
     * 0 left
     * 1 center
     * 2 right
     */
    public int getAlignment()
    {
        return alignment;
    }
    /**
     * 得到Table控制类
     * @return CTable
     */
    public CTable getCTable()
    {
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getCTable();
    }
    /**
     * 得到TRModel
     * @return ETRModel
     */
    public ETRModel getTRModel()
    {
        ETR tr = getTR();
        if(tr == null)
            return null;
        return tr.getModel();
    }
    /**
     * 得到TR类型
     * @return int
     */
    public int getTRModuleType()
    {
        ETRModel etrModel= getTRModel();
        if(etrModel == null)
            return -1;
        return etrModel.getType();
    }
    /**
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DPoint point = getTR().getScreenPoint();
        point.setX(point.getX() + (int)(getX() * getZoom() / 75.0 + 0.5));
        point.setY(point.getY() + (int)(getY() * getZoom() / 75.0 + 0.5));
        return point;
    }
    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        for(int i = 0;i < size();i++)
            get(i).resetData(action);
    }
    /**
     * 设置上一个连接TD
     * @param previousLinkTD ETD
     */
    public void setPreviousLinkTD(ETD previousLinkTD)
    {
        this.previousLinkTD = previousLinkTD;
    }
    /**
     * 得到上一个连接TD
     * @return ETD
     */
    public ETD getPreviousLinkTD()
    {
        return previousLinkTD;
    }
    /**
     * 设置下一个连接TD
     * @param nextLinkTD ETD
     */
    public void setNextLinkTD(ETD nextLinkTD)
    {
        this.nextLinkTD = nextLinkTD;
    }
    /**
     * 得到下一个连接TD
     * @return ETD
     */
    public ETD getNextLinkTD()
    {
        return nextLinkTD;
    }
    /**
     * 得到TD控制类
     * @return MTDSave
     */
    public MTDSave getTDSaveManager()
    {
        return getPM().getFileManager().getTDSaveManager();
    }
    /**
     * 得到TD合并单元格控制类
     * @return MTDSave
     */
    public MTDSave getTDSaveUniteManager()
    {
        return getPM().getFileManager().getTDSaveUniteManager();
    }
    /**
     * 设置上一个TD的索引号码(保存专用)
     * @param index int
     */
    public void setPreviousTDIndex(int index)
    {
        previousTDIndex = index;
    }
    /**
     * 得到上一个TD的索引号码(保存专用)
     * @return int
     */
    public int getPreviousTDIndex()
    {
        return previousTDIndex;
    }
    /**
     * 设置最大高度
     * @param maxHeight int
     */
    public void setMaxHeight(int maxHeight)
    {
        this.maxHeight = maxHeight;
    }
    /**
     * 得到最大高度
     * @return int
     */
    public int getMaxHeight()
    {
        return maxHeight;
    }
    /**
     * 创建下一个连接TD
     * @return ETD
     */
    public ETD newNextTD()
    {
        getTR().createNextLinkTR();
        return getNextLinkTD();
    }
    /**
     * 创建下一页连接面板
     * @return EPanel
     */
    public EPanel createNextPageLinkPanel()
    {
        ETD td = getNextLinkTD();
        if(td == null)
        {
            getTR().createNextLinkTR();
            td = getNextLinkTD();
        }
        if(td == null)
            return null;
        EPanel panel = td.get(0);
        if(panel == null)
        {
            panel = td.newPanel();
            panel.setPreviousLinkPanel(get(size() - 1));
            panel.getPreviousLinkPanel().setNextLinkPanel(panel);
        }
        panel.setModify(true);
        return panel;
    }
    /**
     * 分离Panel
     * 将p1后面的全部内容分离到p2后面
     * @param p1 EPanel 自己的Panel
     * @param p2 EPanel 其他页Panel
     */
    public void separatePanel(EPanel p1,EPanel p2)
    {
        if(p1 == null || p2 == null)
            return;
        int index = indexOf(p1);
        if(index == -1)
            return;
        if(index >= size() - 1)
            return;
        ETD td = p2.getParentTD();
        if(td == null)
            return;
        int indexNext = td.indexOf(p2);
        if(indexNext == -1)
            return;
        index ++;
        while(index < size())
        {
            EPanel p = get(index);
            indexNext ++;
            td.add(indexNext,p);
            remove(index);
        }
    }
    /**
     * 设置尺寸调整对象
     * @param resetPanel ResetPanel
     */
    public void setResetPanel(ResetPanel resetPanel)
    {
        this.resetPanel = resetPanel;
    }
    /**
     * 得到尺寸调整对象
     * @return ResetPanel
     */
    public ResetPanel getResetPanel()
    {
        return resetPanel;
    }
    /**
     * 得到下一个面板
     * @param panel EPanel
     * @return EPanel
     */
    public EPanel getNextPanel(EPanel panel)
    {
        int index = indexOf(panel);
        if(index == -1)
            return null;
        index ++;
        if(index >= size())
            return null;
        return get(index);
    }
    /**
     * 得到上一个面板
     * @param panel EPanel
     * @return EPanel
     */
    public EPanel getPreviousPanel(EPanel panel)
    {
        int index = indexOf(panel);
        if(index == -1)
            return null;
        index --;
        if(index < 0)
            return null;
        return get(index);
    }
    /**
     * 焦点向右移动
     * @param panel EPanel
     * @return boolean
     */
    public boolean onFocusToRight(EPanel panel)
    {
        EPanel next = getNextPanel(panel);
        while(next != null)
        {
            if (next.setFocus())
                return true;
            next = getNextPanel(panel);
        }
        if(getNextLinkTD() != null && getNextLinkTD().size() > 0)
            return getNextLinkTD().setFocus();
        return getTR().onFocusToRight(this);
    }
    /**
     * 焦点向左移动
     * @param panel EPanel
     * @return boolean
     */
    public boolean onFocusToLeft(EPanel panel)
    {
        EPanel next = getPreviousPanel(panel);
        while(next != null)
        {
            if (next.setFocusLast())
                return true;
            next = getPreviousPanel(panel);
        }
        if(getPreviousLinkTD() != null && getPreviousLinkTD().size() > 0)
            return getPreviousLinkTD().setFocusLast();
        return getTR().onFocusToLeft(this);
    }
    /**
     * 设置焦点
     * @return boolean
     */
    public boolean setFocus()
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel.setFocus())
                return true;
        }
        if(getNextLinkTD() != null)
            return getNextLinkTD().setFocus();
        return false;
    }
    /**
     * 设置焦点最后
     * @return boolean
     */
    public boolean setFocusLast()
    {
        for (int i = size() - 1; i >= 0; i--)
        {
            EPanel panel = get(i);
            if (panel.setFocusLast())
                return true;
        }
        if(getPreviousLinkTD() != null)
            return getPreviousLinkTD().setFocusLast();
        return false;
    }
    /**
     * 得到首TD
     * @return ETD
     */
    public ETD getHeadTD()
    {
        if(getPreviousLinkTD() != null)
            return getPreviousLinkTD().getHeadTD();
        return this;
    }
    /**
     * 得到尾TD
     * @return ETD
     */
    public ETD getEndTD()
    {
        if(getNextLinkTD() != null)
            return getNextLinkTD().getEndTD();
        return this;
    }
    /**
     * 得到下一个焦点模块
     * @return IBlock
     */
    public IBlock getDownFocusBlock()
    {
        return getTR().getDownFocusBlock(this);
    }
    /**
     * 得到下一个焦点模块
     * @return IBlock
     */
    public IBlock getUpFocusBlock()
    {
        return getTR().getUpFocusBlock(this);
    }
    /**
     * 清除修改痕迹
     */
    public void resetModify()
    {
        if(!isModify())
            return;
        for(int i = 0;i < size();i ++)
            get(i).resetModify();
    }
    /**
     * 是否选中绘制
     * @return boolean
     */
    public boolean isSelectedDraw()
    {
        if(isSelected())
            return true;
        return getTR().isSelectedDraw();
    }
    /**
     * 得到组件所在的TD
     * @return ETD
     */
    public ETD getComponentInTD()
    {
        return getTR().getComponentInTD();
    }
    /**
     * 测试TD是否是本TD的后续TD
     * @param td ETD
     * @return boolean
     */
    public boolean isNextListTD(ETD td)
    {
        if(td == null)
            return false;
        if(td == this)
            return true;
        td = td.getPreviousLinkTD();
        while(td != null)
        {
            if(td == this)
                return true;
            td = td.getPreviousLinkTD();
        }
        return false;
    }
    /**
     * 得到TD所在的面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return getTR().getPanel();
    }
    /**
     * 选中全部TR
     * @param isSelected boolean
     */
    public void setSelectedAll(boolean isSelected)
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            td.setSelected(isSelected);
            td = td.getNextLinkTD();
        }
    }
    /**
     * 设置是否选中
     * @param isSelected boolean
     */
    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
    /**
     * 是否选中
     * @return boolean
     */
    public boolean isSelected()
    {
        return isSelected;
    }
    /**
     * 得到全部包含面板
     * @param list TList
     * @param all boolean
     */
    public void getPanelAll(TList list,boolean all)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel.getPreviousLinkPanel() == null)
            {
                if(list.indexOf(panel) == -1)
                    list.add(panel);
                if(all)
                    panel.getPanelAll(list,all);
            }
        }
        if(getNextLinkTD() != null)
            getNextLinkTD().getPanelAll(list,all);
    }
    /**
     * 焦点在TD中(包括连接TD)
     * @return boolean
     */
    public boolean focusInTD()
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
                if(getFocusManager().focusInPanel(td.get(i)))
                    return true;
            td = td.getNextLinkTD();
        }
        return false;
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        if(action == null)
            return;
        //删除动作
        if(action.getType() == EAction.DELETE)
        {
            if(isLockEdit())
                return;
            switch(action.getInt(0))
            {
            case 0:
                onDeleteThis(true);
                break;
            case 1:
            case 2:
                onDeleteThis(false);
                break;
            }
            if(getSelectedModel() != null)
            {
                getSelectedModel().getSelectList().remove(getSelectedModel());
                getSelectedModel().removeThis();
            }
            return;
        }
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel.getPreviousLinkPanel() == null)
                panel.exeAction(action);
        }
        if(getNextLinkTD() != null)
            getNextLinkTD().exeAction(action);
    }
    /**
     * 设置上一个组件为焦点
     * @return boolean
     */
    public boolean setPreviousFocus()
    {
        if(getPreviousLinkTD() != null)
            getPreviousLinkTD().setFocusLast();
        return false;
    }
    /**
     * 设置下一个组件为焦点
     * @return boolean
     */
    public boolean setNextFocus()
    {
        if(getNextLinkTD() != null)
            getNextLinkTD().setFocus();
        return false;
    }
    /**
     * 删除动作
     * @param flg boolean true 全部删除 false 普通删除
     */
    public void onDelete(boolean flg)
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EPanel panel = get(i);
            //连接面板
            if(i != size() - 1)
                panel.onDeleteEnter();
            //删除面板
            panel.onDelete(flg);
        }
    }
    /**
     * 删除动作
     * @param flg boolean true 彻底删除 false 普通删除
     */
    public void onDeleteThis(boolean flg)
    {
        setModify(true);
        ETD td = getEndTD();
        while (td != null)
        {
            td.onDelete(flg);
            td = td.getPreviousLinkTD();
        }
        if (getSelectedModel() != null)
        {
            getSelectedModel().getSelectList().remove(getSelectedModel());
            getSelectedModel().removeThis();
        }
    }
    /**
     * 存在固定文本
     * @return boolean
     */
    public boolean hasFixed()
    {
        for(int i = 0;i < size();i++)
        {
            if(get(i).hasFixed())
                return true;
        }
        return false;
    }
    /**
     * 存在固定文本
     * @return boolean
     */
    public boolean hasFixedAll()
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            if(td.hasFixed())
                return true;
            td = getNextLinkTD();
        }
        return false;
    }
    /**
     * 设置选中对象
     * @param selectedModel CSelectTDModel
     */
    public void setSelectedModel(CSelectTDModel selectedModel)
    {
        this.selectedModel = selectedModel;
    }
    /**
     * 得到选中对象
     * @return CSelectTDModel
     */
    public CSelectTDModel getSelectedModel()
    {
        return selectedModel;
    }
    /**
     * 创建选中对象
     * @return CSelectTDModel
     */
    public CSelectTDModel createSelectedModel()
    {
        CSelectTDModel model = getSelectedModel();
        if(model != null)
            getFocusManager().getSelectedModel().removeSelectList(model.getSelectList());
        model = new CSelectTDModel(this);
        model.setSelectList(getFocusManager().getSelectedModel().getSelectedList());
        setSelectedModel(model);
        return model;
    }
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
            {
                EComponent com = td.get(i).findObject(name,type);
                if(com != null)
                    return com;
            }
            td = td.getNextLinkTD();
        }
        return null;
    }
    /**
     * 过滤对象
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type)
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
                td.get(i).filterObject(list,name,type);
            td = td.getNextLinkTD();
        }
    }
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
            {
                EComponent com = td.get(i).findGroup(group);
                if(com != null)
                    return com;
            }
            td = td.getNextLinkTD();
        }
        return null;
    }
    /**
     * 得到组值
     * @param group String
     * @return String
     */
    public String findGroupValue(String group)
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
            {
                String value = td.get(i).findGroupValue(group);
                if(value != null)
                    return value;
            }
            td = td.getNextLinkTD();
        }
        return null;
    }
    /**
     * 鼠标移动
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        int index = getMouseInComponentIndexXY(mouseX,mouseY);
        if(index == -1)
            return false;
        EPanel panel = get(index);
        return panel.onMouseMoved(mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5),
                                           mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5));
    }
    /**
     * 得到鼠标所在组件
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInComponentIndexXY(int mouseX,int mouseY)
    {
        double zoom = getZoom() / 75.0;
        if(size() ==0)
            return -1;
        for(int i = 0; i < size();i++)
        {
            EPanel panel = get(i);
            if(mouseX >= (int)(panel.getX() * zoom + 0.5) &&
               mouseX <= (int)((panel.getX() + panel.getWidth()) * zoom + 0.5) &&
               mouseY >= (int)(panel.getY() * zoom + 0.5) &&
               mouseY <= (int)((panel.getY() + panel.getHeight()) * zoom + 0.5))
                return i;
        }
        return -1;
    }
    /**
     * 设置跨越X
     * @param spanX int
     */
    public void setSpanX(int spanX)
    {
        this.spanX = spanX;
    }
    /**
     * 得到跨越X
     * @return int
     */
    public int getSpanX()
    {
        return spanX;
    }
    /**
     * 设置跨越Y
     * @param spanY int
     */
    public void setSpanY(int spanY)
    {
        this.spanY = spanY;
    }
    /**
     * 得到跨越Y
     * @return int
     */
    public int getSpanY()
    {
        return spanY;
    }
    /**
     * 是否合并单元格
     * @return boolean
     */
    public boolean isSpan()
    {
        return getSpanX() > 0 || getSpanY() > 0;
    }
    /**
     * 设置是否显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        if(this.visible == visible)
            return;
        this.visible = visible;
        this.setModify(true);
    }
    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * 设置联合的TD
     * @param uniteTD ETD
     */
    public void setUniteTD(ETD uniteTD)
    {
        this.uniteTD = uniteTD;
    }
    /**
     * 得到联合的TD
     * @return ETD
     */
    public ETD getUniteTD()
    {
        return uniteTD;
    }
    /**
     * 得到上一个连接TD的最后一个面板
     * @return EPanel
     */
    public EPanel getPreviousLinkTDLastPanel()
    {
        if(getPreviousLinkTD() == null)
            return null;
        if(getPreviousLinkTD().size() == 0)
            return getPreviousLinkTD().getPreviousLinkTDLastPanel();
        return getPreviousLinkTD().get(getPreviousLinkTD().size() - 1);
    }
    /**
     * 得到合并单元格的相关TD句柄
     * @param list TList
     * @param td ETD
     */
    public void getUniteTDHandles(TList list,ETD td)
    {
        ETD t = getHeadTD();
        while(t != null)
        {
            if(t.getUniteTD() == td)
                list.add(t);
            t = t.getNextLinkTD();
        }
    }
    /**
     * 锁定编辑
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return getTR().isLockEdit();
    }
    /**
     * 克隆
     * @param tr ETR
     * @return ETD
     */
    public ETD clone(ETR tr)
    {
        ETD td = new ETD(tr);
        td.setVisible(isVisible());
        td.setX(getX());
        td.setY(getY());
        td.setWidth(getWidth());
        td.setHeight(getHeight());
        td.setAlignment(getAlignment());
        td.setSpanX(getSpanX());
        td.setSpanY(getSpanY());
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            td.add(panel.clone(td));
        }
        td.setModify(true);
        return td;
    }
}
