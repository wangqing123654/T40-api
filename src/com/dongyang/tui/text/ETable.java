package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.util.TList;
import java.awt.Rectangle;
import com.dongyang.tui.DInsets;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.tui.DText;
import com.dongyang.util.StringTool;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.DCursor;
import com.dongyang.tui.DPoint;
import java.util.List;
import java.awt.Font;
import com.dongyang.tui.DFont;
import java.awt.FontMetrics;

/**
 *
 * <p>Title: 表格</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @author whao 2013~
 * @version 1.0
 */
public class ETable implements IBlock, EEvent,IExeAction
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
     * 名称
     */
    private String name;
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
     * 内部尺寸
     */
    DInsets insets;
    /**
     * TR内部尺寸
     */
    DInsets trInsets;
    /**
     * TD内部尺寸
     */
    DInsets tdInsets;
    /**
     * 控制框
     */
    CTable cTable;
    /**
     * 纵向间距
     */
    int hSpace;
    /**
     * 横向间距
     */
    int wSpace;
    /**
     * 显示边框
     */
    boolean showBorder;
    /**
     * 显示边框
     */
    boolean showBorder2;
    /**
     * 显示横线
     */
    boolean showWLine;
    /**
     * 显示竖线
     */
    boolean showHLine;
    /**
     * 修改状态
     */
    boolean modify;
    /**
     * 位置
     * 0 中间
     * 1 行首
     * 2 行尾
     */
    private int position;
    /**
     * 上一个Table
     */
    private ETable previousTable;
    /**
     * 下一个Table
     */
    private ETable nextTable;
    /**
     * Table 模型
     */
    private ETableModel model;
    /**
     * 上一个Table索引号码(保存专用)
     */
    private int previousTableIndex;
    /**
     * 最大高度
     */
    private int maxHeight = -1;
    /**
     * 设置尺寸调整
     */
    private ResetTable resetTable;
    /**
     * 选中
     */
    private boolean isSelected;
    /**
     * 锁定编辑
     */
    public boolean lockEdit;
    /**
     * 构造器
     */
    public ETable()
    {
        components = new TList();
        setInsets(new DInsets(2, 2, 2, 2));
        setTRInsets(new DInsets(2, 2, 2, 2));
        setTDInsets(new DInsets(2, 2, 2, 2));
        setWSpace(1);
        setHSpace(1);
        //创建尺寸调整对象
        setResetTable(new ResetTable(this));
    }

    /**
     * 构造器
     * @param panel EPanel
     */
    public ETable(EPanel panel)
    {
        this();
        setParent(panel);
    }

    /**
     * 得到面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        EComponent com = getParent();
        if (com == null || !(com instanceof EPanel))
            return null;
        return (EPanel) com;
    }

    /**
     * 得到页面
     * @return EPage
     */
    public EPage getPage()
    {
        EPanel panel = getPanel();
        if (panel == null)
            return null;
        return panel.getPage();
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
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        if (getPM() == null)
            return null;
        return getPM().getUI();
    }

    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }

    /**
     * 得到Table控制器管理器
     * @return MCTable
     */
    public MCTable getCTableManager()
    {
        if (getPM() == null)
            return null;
        return getPM().getCTableManager();
    }

    /**
     * 得到面板控制类
     * @return MPanel
     */
    public MTable getSaveTableManager()
    {
        return getPM().getFileManager().getTableManager();
    }

    /**
     * 设置上一个Table的索引号码(保存专用)
     * @param index int
     */
    public void setPreviousTableIndex(int index)
    {
        previousTableIndex = index;
    }

    /**
     * 得到上一个Table的索引号码(保存专用)
     * @return int
     */
    public int getPreviousTableIndex()
    {
        return previousTableIndex;
    }

    /**
     * 设置Table控制框
     * @param cTable CTable
     */
    public void setCTable(CTable cTable)
    {
        this.cTable = cTable;
        if (cTable == null)
            return;
        ETable table = cTable.getTable();
        if (table == this)
            return;
        if (table != null)
            table.setCTable(null);
        cTable.setTable(this);
    }

    /**
     * 设置Table控制框
     * @param index int
     */
    public void setCTable(int index)
    {
        setCTable(getCTableManager().get(index));
    }

    /**
     * 创建控制器
     * @return CTable
     */
    public CTable createControl()
    {
        CTable cTable = getCTableManager().newCTable(this);
        setCTable(cTable);
        return cTable;
    }

    /**
     * Table控制框
     * @return CTable
     */
    public CTable getCTable()
    {
        return cTable;
    }

    /**
     * 增加成员
     * @param tr ETR
     */
    public void add(ETR tr)
    {
        if (tr == null)
            return;
        components.add(tr);
        tr.setParent(this);
    }

    /**
     * 增加成员
     * @param index int
     * @param tr ETR
     */
    public void add(int index, ETR tr)
    {
        if (tr == null)
            return;
        components.add(index, tr);
        tr.setParent(this);
    }

    /**
     * 查找位置
     * @param tr ETR
     * @return int
     */
    public int indexOf(ETR tr)
    {
        if (tr == null)
            return -1;
        return components.indexOf(tr);
    }

    /**
     * 删除成员
     * @param tr ETR
     */
    public void remove(ETR tr)
    {
        components.remove(tr);
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
     * @return ETR
     */
    public ETR get(int index)
    {
        if (index < 0 || index >= size())
            return null;
        return (ETR) components.get(index);
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
    public void setLocation(int x, int y)
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
     * 设置边框
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setBounds(int x, int y, int width, int height)
    {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    /**
     * 设置内部尺寸
     * @param insets DInsets
     */
    public void setInsets(DInsets insets)
    {
        this.insets = insets;
    }

    /**
     * 得到内部尺寸
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return insets;
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
     * 设置TR内部尺寸
     * @param trInsets DInsets
     */
    public void setTRInsets(DInsets trInsets)
    {
        this.trInsets = trInsets;
    }

    /**
     * 得到TR内部尺寸
     * @return DInsets
     */
    public DInsets getTRInsets()
    {
        return trInsets;
    }

    /**
     * 设置TD内部尺寸
     * @param tdInsets DInsets
     */
    public void setTDInsets(DInsets tdInsets)
    {
        this.tdInsets = tdInsets;
    }

    /**
     * 得到TD内部尺寸
     * @return DInsets
     */
    public DInsets getTDInsets()
    {
        return tdInsets;
    }

    /**
     * 设置纵向间距
     * @param hSpace int
     */
    public void setHSpace(int hSpace)
    {
        this.hSpace = hSpace;
    }

    /**
     * 得到纵向间距
     * @return int
     */
    public int getHSpace()
    {
        return hSpace;
    }

    /**
     * 设置横向间距
     * @param wSpace int
     */
    public void setWSpace(int wSpace)
    {
        this.wSpace = wSpace;
    }

    /**
     * 得到横向间距
     * @return int
     */
    public int getWSpace()
    {
        return wSpace;
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
     * 是否阅览状态
     * @return boolean
     */
    public boolean isPreview()
    {
        return getViewManager().isPreview();
    }
    /**
     * 是否是Table的最后一行
     * @param tr ETR
     * @return boolean
     */
    public boolean isLastTR(ETR tr)
    {
        if(size() == 0)
            return false;
        return get(size() - 1) == tr;
    }
    /**
     * 是否是Table的第一行
     * @param tr ETR
     * @return boolean
     */
    public boolean isFirstTR(ETR tr)
    {
        if(size() == 0)
            return false;
        return get(0) == tr;
    }
    /**
     * 计算缩放比例
     * @param x int
     * @return int
     */
    public int computeZoom(int x)
    {
        return (int) ((double) x * getZoom() / 75.0 + 0.5);
    }
    /**
     * 绘制外边框
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintShowBorder2(Graphics g,int x,int y,int width,int height,boolean print)
    {
        if (!isShowBorder2())
            return;
        g.setColor(new Color(0, 0, 0));
        g.drawRect(x, y, width, height);
    }
    /**
     * 绘制内边框
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintShowBorder(Graphics g,int x,int y,int width,int height,boolean print)
    {
        if (!isShowBorder())
            return;
        g.setColor(new Color(0, 0, 0));
        if(getInsets().zeroth())
        {
            if(isShowBorder2())
                return;
            g.drawRect(x, y, width, height);
            return;
        }
        int left = print?getInsets().left:computeZoom(getInsets().left);
        int top = print?getInsets().top:computeZoom(getInsets().top);
        int right = print?getInsets().right:computeZoom(getInsets().right);
        int bottom = print?getInsets().bottom:computeZoom(getInsets().bottom);
        g.drawRect(x + left,
                   y + top,
                   width - left - right,
                   height - top - bottom);
    }
    /**
     * 绘制辅助线
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintAssistantLine(Graphics g,int x,int y,int width,int height)
    {
        if(isShowBorder2() || isShowBorder() || isPreview())
            return;
        g.setColor(new Color(168, 168, 168));
        g.drawRect(x, y, width, height);
    }
    /**
     * 是否隐藏Table
     * @return boolean
     */
    public boolean isHideTable()
    {
        if(getCTable() == null)
            return false;
        if(getCTable().getData() == null)
            return false;
        if(!getCTable().getData().existData("Visible"))
            return false;
        return !getCTable().getData().getBoolean("Visible");
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
        //是否隐藏Table
        if(isHideTable())
            return;
        //绘制外边框
        paintShowBorder2(g,x,y,width,height,false);
        //绘制内边框
        paintShowBorder(g,x,y,width,height,false);
        //绘制辅助线
        paintAssistantLine(g,x,y,width,height);

        double zoom = getZoom() / 75.0;
        //绘制页面
        for (int i = 0; i < size(); i++)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            if (!tr.isVisible())
                continue;
            int trX = (int)(tr.getX() * zoom + 0.5);
            int trY = (int)(tr.getY() * zoom + 0.5);
            int trW = (int)(tr.getWidth() * zoom + 0.5);
            int trH = (int)(tr.getHeight() * zoom + 0.5);
            Rectangle r = g.getClipBounds();
            if (r.getX() > x + trX + trW ||
                r.getY() > y + trY + trH ||
                r.getX() + r.getWidth() < x + trX ||
                r.getY() + r.getHeight() < y + trY)
                continue;
            //绘制
            tr.paint(g, x + trX,y + trY,trW, trH);
            //绘制行号
            if(getViewManager().isShowRowID() && tr.getRowID() > 0)
            {
                String s = "" + tr.getRowID() + "    ";
                Font font = new Font("宋体", 0, (int) (11 * zoom + 0.5));
                FontMetrics metrics = DFont.getFontMetrics(font);
                int x0 = metrics.stringWidth(s);
                g.setFont(font);
                g.setColor(new Color(0, 0, 0));
                g.drawString(s, x - x0, y + trY + trH);
            }
        }
    }

    /**
     * 打印
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g, int x, int y)
    {
        //是否隐藏Table
        if(isHideTable())
            return;
        boolean isPrintXD = getPM().getPageManager().isPrintXD();
        if(isPrintXD)
            getPM().getPageManager().setIsPrintXD(false);
        if(!isPrintXD)
        {
            //绘制外边框
            paintShowBorder2(g, x, y, width, height, true);
            //绘制内边框
            paintShowBorder(g, x, y, width, height, true);
        }
        //绘制页面
        for (int i = 0; i < size(); i++)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            if (!tr.isVisible())
                continue;
            //须打判断
            if(isPrintXD && !getPM().getPageManager().checkXDPrintRow(tr.getRowID()))
                continue;
            //绘制
            tr.print(g, x + tr.getX(), y + tr.getY());
            if(i == 0 && isPrintXD && isShowBorder2())
            {
                g.setColor(new Color(0, 0, 0));
                g.drawLine(x,y,x + width,y);
            }
            if(i == size() - 1 && isPrintXD && isShowBorder2())
            {
                g.setColor(new Color(0, 0, 0));
                g.drawLine(x,y + height,x + width,y + height);
            }
            if(isPrintXD && isShowBorder2())
            {
                g.setColor(new Color(0, 0, 0));
                g.drawLine(x,y + tr.getY(),x,y + tr.getY() + tr.getHeight());
                g.drawLine(x + width,y + tr.getY(),x + width,y + tr.getY() + tr.getHeight());
            }
        }
        if(isPrintXD)
            getPM().getPageManager().setIsPrintXD(true);
    }

    /**
     * 新行
     * @return ETR
     */
    public ETR newTR()
    {
        ETR tr = new ETR();
        add(tr);
        tr.setX(0);
        tr.setWidth(getInsetsWidth());
        return tr;
    }

    public ETR newTR(int index)
    {
        ETR tr = new ETR();
        add(index, tr);
        tr.setX(0);
        tr.setWidth(getInsetsWidth());
        return tr;
    }

    /**
     * 得到鼠标所在DR
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInTRIndex(int mouseX, int mouseY)
    {
        double zoom = getZoom() / 75.0;
        for (int i = 0; i < size(); i++)
        {
            ETR tr = get(i);
            if(!tr.isVisible())
                continue;
            if ((int)(tr.getY() * zoom + 0.5) <= mouseY &&
                (int)((tr.getY() + tr.getHeight()) * zoom + 0.5) >= mouseY)
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
    public int onMouseLeftPressed(int mouseX, int mouseY)
    {
        //鼠标所在TR
        int trIndex = getMouseInTRIndex(mouseX, mouseY);
        if (trIndex == -1)
            return -1;
        ETR tr = get(trIndex);
        //选中一行
        if(mouseX < 0)
        {
            getFocusManager().setFocusTR(tr.getHeadTR());
            return 2;
        }
        int x = mouseX - (int)(tr.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(tr.getY() * getZoom() / 75.0 + 0.5);
        //事件传递
        return tr.onMouseLeftPressed(x, y);
    }
    /**
     * 右键按下
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        //鼠标所在TR
        int trIndex = getMouseInTRIndex(mouseX, mouseY);
        if (trIndex == -1)
            return;
        ETR tr = get(trIndex);
        //选中一行
        if(mouseX < 0)
        {
            getFocusManager().setFocusTR(tr.getHeadTR());
            return;
        }
        int x = mouseX - (int)(tr.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(tr.getY() * getZoom() / 75.0 + 0.5);
        //事件传递
        tr.onMouseRightPressed(x, y);
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX, int mouseY)
    {
        //鼠标所在TR
        int trIndex = getMouseInTRIndex(mouseX, mouseY);
        if (trIndex != -1)
        {
            ETR tr = get(trIndex);
            int x = mouseX - (int)(tr.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(tr.getY() * getZoom() / 75.0 + 0.5);
            //事件传递
            return tr.onDoubleClickedS(x, y);
        }
        return false;
    }

    /**
     * 得到屏幕Y
     * @return int
     */
    public int getScreenY()
    {
        EComponent component = getParent();
        if (component == null)
            return 0;
        if (component instanceof EPanel)
            return ((EPanel) component).getScreenInsetsY() + getY();
        //temp
        return 0;
    }

    /**
     * 得到最后一个
     * @return EText
     */
    public EText getLastText()
    {
        return getLastText(size() - 1);
    }

    /**
     * 得到上一个
     * @param tr ETR
     * @return EText
     */
    public EText getPreviousText(ETR tr)
    {
        if (tr == null)
            return null;
        int index = indexOf(tr);
        if (index == -1)
            return null;
        EText text = getLastText(index - 1);
        if (text != null)
            return text;
        EPanel panel = getPanel();
        if (panel == null)
            return null;
        return panel.getPreviousText(this, true);
    }

    /**
     * 得到最后一个
     * @param index int
     * @return EText
     */
    public EText getLastText(int index)
    {
        for (int i = index; i >= 0; i--)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            EText text = tr.getLastText();
            if (text != null)
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
        return getFirstText(0);
    }

    /**
     * 得到下一个
     * @param tr ETR
     * @return EText
     */
    public EText getNextText(ETR tr)
    {
        if (tr == null)
            return null;
        int index = indexOf(tr);
        if (index == -1)
            return null;
        EText text = getFirstText(index + 1);
        if (text != null)
            return text;
        EPanel panel = getPanel();
        if (panel == null)
            return null;
        return panel.getNextText(this, true);
    }

    /**
     * 查找第一个
     * @param index int
     * @return EText
     */
    public EText getFirstText(int index)
    {
        for (int i = index; i < size(); i++)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            EText text = tr.getFirstText();
            if (text != null)
                return text;
        }
        return null;
    }

    /**
     * 设置是否修改状态
     * @param modify boolean
     */
    public void setModify(boolean modify)
    {
        if (this.modify == modify)
            return;
        if (modify)
        {
            this.modify = true;
            if (getParent() != null)
                getParent().setModify(true);
            return;
        }
        for (int i = 0; i < size(); i++)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            if (tr.isVisible())
                continue;
            if (tr.isModify())
                return;
        }
        this.modify = false;
        if (getParent() != null)
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
        //清零
        getResetTable().clear();
        //设置尺寸范围
        getResetTable().setRectangle(0,
                                     0,
                                     getWidth(),
                                     getMaxHeight());
        //调整尺寸
        getResetTable().reset();
        setModify(false);
    }
    public int getTRY(int index)
    {
        if (index <= 0 || index >= size())
            return getInsetsY();
        ETR tr = get(index - 1);
        while(tr != null)
        {
            if(tr.isVisible())
                return tr.getY() + tr.getHeight() + getHSpace() * index;
            index --;
            if(index == 0)
                return getInsetsY();
            tr = get(index - 1);
        }
        return getInsetsY();
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
        int y = getTRY(index);
        int width = getInsetsWidth();
        for (int i = index; i < size(); i++)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            if (!tr.isVisible())
                continue;
            if (tr.getY() != y || tr.getX() != x || tr.getWidth() != width)
            {
                tr.setX(x);
                tr.setWidth(width);
                tr.setModify(true);
            }
            if (!tr.isModify())
            {
                y = tr.getY() + tr.getHeight() + getHSpace();
                continue;
            }
            tr.setY(y);
            tr.reset();
            y = tr.getY() + tr.getHeight() + getHSpace();
        }
        if (size() == 0)
            return;
        ETR tr = get(size() - 1);
        DInsets insets = getInsets();
        int height = tr.getY() + tr.getHeight() + insets.bottom;
        setHeight(height);
    }

    /**
     * 设置位置
     * @param position int
     */
    public void setPosition(int position)
    {
        this.position = position;
    }

    /**
     * 得到位置
     * @return int
     */
    public int getPosition()
    {
        return position;
    }

    /**
     * 查找组件位置
     * @param table ETable
     * @return int
     */
    public int findIndex(ETable table)
    {
        EPanel panel = getPanel();
        if (panel == null)
            return -1;
        return panel.indexOf(table);
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
        EPanel panel = getPanel();
        if (panel == null)
            return "Parent:Null";
        return panel.getIndexString() + ",Table:" + findIndex();
    }

    /**
     * 设置上一个Table
     * @param table ETable
     */
    public void setPreviousTable(ETable table)
    {
        previousTable = table;
    }

    /**
     * 得到上一个Table
     * @return ETable
     */
    public ETable getPreviousTable()
    {
        return previousTable;
    }

    /**
     * 设置下一个Table
     * @param table ETable
     */
    public void setNextTable(ETable table)
    {
        nextTable = table;
    }

    /**
     * 得到下一个Table
     * @return ETable
     */
    public ETable getNextTable()
    {
        return nextTable;
    }

    /**
     * 分割Table
     * @param table ETable
     */
    public void separateTable(ETable table)
    {
        //初始化基本参数
        table.setX(getX());
        table.setWidth(getWidth());
        table.setHSpace(getHSpace());
        table.setWSpace(getWSpace());
        table.setShowBorder(isShowBorder());
        table.setShowBorder2(isShowBorder2());
        table.setShowWLine(isShowWLine());
        table.setShowHLine(isShowHLine());
        //设置Table连接
        int y = getInsetsY();
        table.setNextTable(this);
        ETable pTable = getPreviousTable();
        table.setPreviousTable(pTable);
        if (pTable != null)
            pTable.setNextTable(table);
        setPreviousTable(table);
        //拷贝下一个Table的第一个TR到本Table最后
        table.copyNextTRTableToLast(y);
        if (table.size() == 0)
        {
            pTable = table.getPreviousTable();
            setPreviousTable(pTable);
            if (pTable != null)
                pTable.setNextTable(this);

            return;
        }
        //设置控制类
        if (getCTable() != null)
            table.setCTable(getCTable());
        //设置模型
        if (getModel() != null)
        {
            table.setModel(getModel());
            getModel().setTable(table);
        }
        table.setModify(true);
        setModify(true);
    }

    /**
     * 调整尺寸
     */
    public void resetTable()
    {
        int x = getInsetsX();
        int y = getInsetsY();
        int width = getInsetsWidth();
        int height = getInsetsHeight();
        boolean isMove = false;
        for (int i = 0; i < size(); i++)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            if (!tr.isVisible())
                continue;
            if (tr.getY() != y || tr.getX() != x || tr.getWidth() != width)
            {
                tr.setY(y);
                tr.setX(x);
                tr.setWidth(width);
                tr.setModify(true);
            }
            if(getMaxHeight() > 0)
            {
                tr.setMaxHeight(getMaxHeight() - y - insets.bottom);
                tr.setModify(true);
            }
            if (tr.isModify())
                tr.reset();
            if (height < y + tr.getHeight())
            {
                //拷贝index之后的全部TR到下一个Table
                copyTRToNextTableLast(i);
                isMove = true;
                break;
            }
            y = tr.getY() + tr.getHeight() + getHSpace();
        }
        if (!isMove)
            //拷贝下一个Table的第一个TR到本Table最后
            copyNextTRTableToLast(y);
        if (size() == 0)
        {
            removeThis();
            return;
        }
        ETR tr = get(size() - 1);
        DInsets insets = getInsets();
        height = tr.getY() + tr.getHeight() + insets.bottom;
        setHeight(height);
    }

    /**
     * 拷贝下一个Table的第一个TR到本Table最后
     * @param y int
     */
    public void copyNextTRTableToLast(int y)
    {
        ETable next = getNextTable();
        if (next == null)
            return;
        if (next.size() == 0)
            return;
        DInsets insets = getInsets();
        while(next.size() > 0)
        {
            ETR tr = next.get(0);
            next.remove(tr);
            add(tr);
            tr.setY(y);
            //设置最大尺寸
            if (getMaxHeight() > 0)
            {
                tr.setMaxHeight(getMaxHeight() - y - insets.bottom);
            }
            tr.setModify(true);
            tr.reset();
            if(tr.hasNextLinkTR())
                break;
            if(tr.isVisible())
                y += getHSpace() + tr.getHeight();
        }
    }
    /**
     * 创建下一个连接Table
     * @return ETable
     */
    public ETable createNextLinkTable()
    {
        EPanel panel = getPanel().createNextLinkPanel();
        if(panel == null)
            return null;
        panel.setType(1);
        ETable newTable = panel.newTable(0);
        newTable.setName(getName());
        newTable.setX(getX());
        newTable.setWidth(getWidth());
        newTable.getInsets().setInsets(getInsets());
        newTable.getTRInsets().setInsets(getTRInsets());
        newTable.getTDInsets().setInsets(getTDInsets());
        newTable.setHSpace(getHSpace());
        newTable.setWSpace(getWSpace());
        newTable.setShowBorder(isShowBorder());
        newTable.setShowBorder2(isShowBorder2());
        newTable.setShowWLine(isShowWLine());
        newTable.setShowHLine(isShowHLine());
        newTable.setPosition(3);
        newTable.setPreviousTable(this);
        newTable.setModel(getModel());
        newTable.setLockEdit(isLockEdit());
        setNextTable(newTable);
        return newTable;
    }
    /**
     * 拷贝index之后的全部TR到下一个Table
     * @param index int
     */
    public void copyTRToNextTableLast(int index)
    {
        ETable next = getNextTable();
        if (next == null)
            return;
        for (int i = size() - 1; i >= index; i--)
        {
            ETR tr = get(i);
            next.add(0, tr);
            remove(tr);
            tr.setModify(true);
        }
    }

    /**
     * 删除自己
     */
    public void removeThis()
    {
        for (int i = size() - 1; i >= 0; i--)
            get(i).removeThis(false);
        EPanel panel = getPanel();
        if (panel == null)
            return;
        panel.remove(this);
        ETable pTable = getPreviousTable();
        ETable nTable = getNextTable();
        if (pTable != null)
            pTable.setNextTable(nTable);
        if (nTable != null)
        {
            nTable.setPreviousTable(pTable);
            if(getCTable() != null)
                nTable.setCTable(getCTable());
        }
        panel.setType(0);
        //测试面板的有效性
        panel.checkValid();
    }

    /**
     * 得到Table首表
     * @return ETable
     */
    public ETable getHeadTable()
    {
        ETable table = this;
        while (table.getPreviousTable() != null)
            table = table.getPreviousTable();
        return table;
    }

    /**
     * 删除自己全部连接表
     */
    public void removeThisAll()
    {
        ETable table = getHeadTable();
        table.getPanel().setNewFocusPanel();
        table.setModify(true);
        table.removeThis();
        if (table.getCTable() != null)
            getCTableManager().remove(table.getCTable());
        while (table != null && table.getNextTable() != null)
        {
            table = table.getNextTable();
            table.setModify(true);
            table.removeThis();
        }
    }

    public String toString()
    {
        return "<ETable size=" + size() +
                ",x=" + getX() + ",y=" + getY() + ",width=" + getWidth() + ",height=" + getHeight() +
                ",P=" + (getPreviousTable() != null) + ",N=" + (getNextTable() != null) +
                ",isModify=" + isModify() +
                ",index=" + getIndexString() + ">";
    }
    /**
     * 调试对象组成
     * @param i int
     */
    public void debugShow(int i)
    {
        System.out.println(StringTool.fill(" ", i) + this);
        for (int j = 0; j < size(); j++)
        {
            ETR tr = get(j);
            tr.debugShow(i + 2);
        }
    }
    /**
     * 调试修改
     * @param i int
     */
    public void debugModify(int i)
    {
        if (!isModify())
            return;
        System.out.println(StringTool.fill(" ", i) + getIndexString() + " " + this);
        for (int j = 0; j < size(); j++)
        {
            ETR tr = get(j);
            tr.debugModify(i + 2);
        }
    }

    /**
     * 设置模型
     * @param model ETableModel
     */
    public void setModel(ETableModel model)
    {
        this.model = model;
    }

    /**
     * 得到模型
     * @return ETableModel
     */
    public ETableModel getModel()
    {
        return model;
    }

    /**
     * 创建模型
     * @return ETableModel
     */
    public ETableModel createModel()
    {
        ETableModel model = new ETableModel();
        model.setTable(this);
        setModel(model);
        return model;
    }

    /**
     * 得到列个数
     * @return int
     */
    public int getColumnCount()
    {
        if (getModel() == null)
            return 0;
        return getModel().size();
    }

    /**
     * 插入一行
     * @return int
     */
    public int insertRow()
    {
        return insertRow(true);
    }

    /**
     * 插入一行
     * @param createText boolean 是否建立Text
     * @return int
     */
    public int insertRow(boolean createText)
    {
        return insertRow(size(), createText);
    }

    /**
     * 插入一行
     * @param index int
     * @return int
     */
    public int insertRow(int index)
    {
        return insertRow(index, true);
    }
    /**
     * 最佳一行
     * @return ETR
     */
    public ETR appendTR()
    {
        int row = insertRow(getTableRowCount(),true);
        return get(row);
    }

    /**
     * 插入一行
     * @param index int
     * @param createText boolean 是否建立Text
     * @return int
     */
    public int insertRow(int index, boolean createText)
    {
        if (getModel() == null)
            return -1;
        if (index < 0 || index > size())
            return index = size();
        ETR tr = newTR(index);
        for (int i = 0; i < getColumnCount(); i++)
        {
            ETD td = tr.newTD();
            td.setWidth(getModel().get(i));
            if (!createText)
                continue;
            EPanel tdpanel = td.newPanel();
            EText text1 = tdpanel.newText();
            text1.setModify(true);
        }
        return index;
    }
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * 得到单元格TD
     * @param row int
     * @param column int
     * @return ETD
     */
    public ETD getTDAt(int row, int column)
    {
        if (row < 0 || row >= size())
            return null;
        ETR tr = get(row);
        if (tr == null)
            return null;
        if (column < 0 || column >= tr.size())
            return null;
        return tr.get(column);
    }

    /**
     * 得到单元格Panel
     * @param row int
     * @param column int
     * @param index int
     * @return EPanel
     */
    public EPanel getPanelAt(int row, int column, int index)
    {
        ETD td = getTDAt(row, column);
        if (td == null)
            return null;
        if (index < 0 || index >= td.size())
            return null;
        return td.get(index);
    }

    /**
     * 设置单元格文本
     * @param row int
     * @param column int
     * @param text String
     */
    public void setTextAt(int row, int column, String text)
    {
        ETD td = getTDAt(row, column);
        if (td == null)
            return;
        td.setString(text);
    }

    /**
     * 得到单元格文本
     * @param row int
     * @param column int
     * @return String
     */
    public String getTextAt(int row, int column)
    {
        ETD td = getTDAt(row, column);
        if (td == null)
            return "";
        return td.getString();
    }

    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s) throws IOException
    {
        s.writeInt(1, getHSpace(), 0);
        s.writeInt(2, getWSpace(), 0);
        s.writeInt(3, getWidth(), 0);
        s.writeInt(4, getHeight(), 0);
        if (getCTable() != null)
        {
            s.writeShort(5);
            s.writeInt(getCTable().findIndex());
        }
        if (getNextTable() != null)
        {
            int index = getSaveTableManager().add(this);
            getNextTable().setPreviousTableIndex(index);
            s.writeShort(6);
        }
        if (getPreviousTable() != null)
        {
            s.writeShort(7);
            s.writeInt(getPreviousTableIndex());
        }
        if (getModel() != null && getPreviousTable() == null)
        {
            s.writeShort(8);
            getModel().writeObject(s);
        }
        s.writeBoolean(9, isShowBorder(), false);
        s.writeBoolean(10, isShowBorder2(), false);
        s.writeBoolean(11, isShowWLine(), false);
        s.writeBoolean(12, isShowHLine(), false);
        s.writeInt(13,getX(),0);
        s.writeString(14,getName(),null);
        s.writeShort(15);
        getInsets().writeObject(s);
        getTRInsets().writeObject(s);
        getTDInsets().writeObject(s);
        s.writeBoolean(16,isLockEdit(),false);
    }

    /**
     * 读对象属性
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id, DataInputStream s) throws
            IOException
    {
        switch (id)
        {
        case 1:
            setHSpace(s.readInt());
            return true;
        case 2:
            setWSpace(s.readInt());
            return true;
        case 3:
            setWidth(s.readInt());
            return true;
        case 4:
            setHeight(s.readInt());
            return true;
        case 5:
            setCTable(s.readInt());
            return true;
        case 6:
            getSaveTableManager().add(this);
            return true;
        case 7:
            ETable table = getSaveTableManager().get(s.readInt());
            setPreviousTable(table);
            table.setNextTable(this);
            setModel(table.getModel());
            return true;
        case 8:
            createModel();
            getModel().readObject(s);
            return true;
        case 9:
            setShowBorder(s.readBoolean());
            return true;
        case 10:
            setShowBorder2(s.readBoolean());
            return true;
        case 11:
            setShowWLine(s.readBoolean());
            return true;
        case 12:
            setShowHLine(s.readBoolean());
            return true;
        case 13:
            setX(s.readInt());
            return true;
        case 14:
            setName(s.readString());
            return true;
        case 15:
            getInsets().readObject(s);
            getTRInsets().readObject(s);
            getTDInsets().readObject(s);
            return true;
        case 16:
            setLockEdit(s.readBoolean());
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
    public void writeObjectDebug(DataOutputStream s, int c) throws IOException
    {
        String cTableIndex = "";
        if (getCTable() != null)
            cTableIndex = " CTable=" + getCTable().findIndex();
        String link = "";
        if (getNextTable() != null)
        {
            int index = getSaveTableManager().add(this);
            getNextTable().setPreviousTableIndex(index);
        }
        if (getPreviousTable() != null)
        {
            link = " link=" + getPreviousTableIndex();
        }
        s.WO("<ETable HSpace=" + getHSpace() + " WSpace=" +
             getWSpace() + " Width=" + getWidth() + " Height=" + getHeight() +
             cTableIndex + link + " >", c);
        if (getModel() != null && getPreviousTable() == null)
        {
            getModel().writeObjectDebug(s, c + 1);
        }
        for (int i = 0; i < size(); i++)
        {
            ETR tr = get(i);
            if (tr == null)
                continue;
            tr.writeObjectDebug(s, c + 1);
        }
        s.WO("</ETable>", c);
    }

    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s) throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
        //保存TD
        s.writeInt(size());
        for (int i = 0; i < size(); i++)
        {
            ETR tr = get(i);
            tr.writeObject(s);
        }
    }

    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s) throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //读对象属性
            readObjectAttribute(id, s);
            id = s.readShort();
        }
        //读取行
        int count = s.readInt();
        for (int i = 0; i < count; i++)
        {
            ETR tr = newTR();
            tr.readObject(s);
        }
    }

    /**
     * 得到横坐标位置
     * @return int
     */
    public int getPointX()
    {
        return getPanel().getPointX() + getX();
    }

    /**
     * 得到路径
     * @param list TList
     */
    public void getPath(TList list)
    {
        if (list == null)
            return;
        list.add(0, this);
        getPanel().getPath(list);
    }

    /**
     * 得到路径索引
     * @param list TList
     */
    public void getPathIndex(TList list)
    {
        if (list == null)
            return;
        list.add(0, findIndex());
        getPanel().getPathIndex(list);
    }

    /**
     * 测试使用样式
     */
    public void checkGCStyle()
    {
        for (int i = 0; i < size(); i++)
            get(i).checkGCStyle();
    }

    /**
     * 鼠标移动
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        int index = getMouseInYLine(mouseX);
        if (index != -1)
        {
            getUI().setCursor(DCursor.E_RESIZE_CURSOR);
            return true;
        }
        index = getMouseInComponentIndexXY(mouseX,mouseY);
        if(index == -1)
            return false;
        ETR tr = get(index);
        return tr.onMouseMoved(mouseX - (int)(tr.getX() * getZoom() / 75.0 + 0.5),
                                           mouseY - (int)(tr.getY() * getZoom() / 75.0 + 0.5));
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
            ETR tr = get(i);
            if(!tr.isVisible())
                continue;
            if(mouseX >= (int)(tr.getX() * zoom + 0.5) &&
               mouseX <= (int)((tr.getX() + tr.getWidth()) * zoom + 0.5) &&
               mouseY >= (int)(tr.getY() * zoom + 0.5) &&
               mouseY <= (int)((tr.getY() + tr.getHeight()) * zoom + 0.5))
                return i;
        }
        return -1;
    }

    /**
     * 计算y线位置
     * @param mouseX int
     * @return int
     */
    public int getMouseInYLine(int mouseX)
    {
        int x0 = 0;
        int x1 = (int)((getInsets().left + getTRInsets().left) * getZoom() / 75.0 + 0.5);
        for (int i = 0; i < getColumnCount(); i++)
        {
            if (mouseX >= x0 - 1 && mouseX <= x1 + 1)
            {
                getFocusManager().setMoveTable(this);
                getFocusManager().setMoveTableIndex(i);
                getFocusManager().setMoveTableX((int)(getPointX() * getZoom() / 75.0 + 0.5) + mouseX);
                return i;
            }
            x1 += (int)(getModel().get(i) * getZoom() / 75.0 + 0.5);
            x0 = x1;
            x1 += (int)(getWSpace() * getZoom() / 75.0 + 0.5);
        }
        if (mouseX >= x0 && mouseX <= (int)(getWidth() * getZoom() / 75.0 + 0.5))
        {
            int index = getColumnCount();
            getFocusManager().setMoveTable(this);
            getFocusManager().setMoveTableIndex(index);
            getFocusManager().setMoveTableX((int)(getPointX() * getZoom() / 75.0 + 0.5) + mouseX);
            return index;
        }
        getFocusManager().setMoveTable(null);
        return -1;
    }

    /**
     * 修改Y线
     * @param index int
     * @param x int
     */
    public void modifyYLine(int index, int x)
    {
        if (x == 0)
            return;
        ETable headTable = getHeadTable();
        if (index == 0)
        {
            if (getX() + x < 0)
                x = -getX();
            headTable.setX(headTable.getX() + x);
            headTable.setWidth(getWidth() - x);
            getModel().set(index, getModel().get(index) - x);
        }else
        {
            if (getModel().get(index - 1) + x < 0)
                x = -getModel().get(index - 1);
            getModel().set(index - 1, getModel().get(index - 1) + x);
            if (index == getModel().size())
                headTable.setWidth(headTable.getWidth() + x);
            else
                getModel().set(index, getModel().get(index) - x);
        }
        //同步宽度
        getHeadTable().sychWidth();
    }
    /**
     * 同步宽度
     */
    public void sychWidth()
    {
        for(int i = 0;i < size();i++)
            get(i).sychWidth();
        if(getNextTable() != null)
        {
            getNextTable().sychWidth();
            getNextTable().setWidth(getWidth());
            getNextTable().setX(getX());
        }
    }
    /**
     * 设置显示边框
     * @param showBorder boolean
     */
    public void setShowBorder(boolean showBorder)
    {
        this.showBorder = showBorder;
    }

    /**
     * 是否显示边框
     * @return boolean
     */
    public boolean isShowBorder()
    {
        return showBorder;
    }

    /**
     * 设置显示边框2
     * @param showBorder2 boolean
     */
    public void setShowBorder2(boolean showBorder2)
    {
        this.showBorder2 = showBorder2;
    }

    /**
     * 是否显示边框2
     * @return boolean
     */
    public boolean isShowBorder2()
    {
        return showBorder2;
    }

    /**
     * 设置显示横线
     * @param showWLine boolean
     */
    public void setShowWLine(boolean showWLine)
    {
        this.showWLine = showWLine;
    }

    /**
     * 是否显示横线
     * @return boolean
     */
    public boolean isShowWLine()
    {
        return showWLine;
    }

    /**
     * 设置显示竖线
     * @param showHLine boolean
     */
    public void setShowHLine(boolean showHLine)
    {
        this.showHLine = showHLine;
    }

    /**
     * 是否显示竖线
     * @return boolean
     */
    public boolean isShowHLine()
    {
        return showHLine;
    }
    /**
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DPoint point = getPanel().getScreenPoint();
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
     * 设置尺寸调整对象
     * @param resetTable ResetTable
     */
    public void setResetTable(ResetTable resetTable)
    {
        this.resetTable = resetTable;
    }
    /**
     * 得到尺寸调整对象
     * @return ResetTable
     */
    public ResetTable getResetTable()
    {
        return resetTable;
    }
    /**
     * 设置焦点
     * @return boolean
     */
    public boolean setFocus()
    {
        for(int i = 0;i < size();i++)
        {
            ETR tr = get(i);
            if(!tr.isVisible())
                continue;
            if(tr.getPreviousLinkTR() != null)
                continue;
            if(tr.setFocus())
                return true;
        }
        if(getNextTable() != null)
            return getNextTable().setFocus();
        return false;
    }
    /**
     * 设置焦点到最后
     * @return boolean
     */
    public boolean setFocusLast()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            ETR tr = get(i);
            if(!tr.isVisible())
                continue;
            if(tr.setFocusLast())
                return true;
        }
        if(getPreviousTable() != null)
            return getPreviousTable().setFocusLast();
        return false;
    }
    /**
     * 焦点向右移动
     * @param tr ETR
     * @return boolean
     */
    public boolean onFocusToRight(ETR tr)
    {
        int index = indexOf(tr);
        for(int i = index + 1;i < size();i++)
        {
            ETR next = get(i);
            if(!next.isVisible())
                continue;
            if(next.getPreviousLinkTR() != null)
                continue;
            if(next.setFocus())
                return true;
        }
        if(getNextTable() != null)
            return getNextTable().setFocus();
        return false;
    }
    /**
     * 得到下一个TR
     * @param tr ETR
     * @return ETR
     */
    public ETR getNextTR(ETR tr)
    {
        int index = indexOf(tr);
        if(index == -1)
            return null;
        index ++;
        if(index >= size())
            return null;
        return get(index);
    }
    /**
     * 得到上一个TR
     * @param tr ETR
     * @return ETR
     */
    public ETR getPreviousTR(ETR tr)
    {
        int index = indexOf(tr);
        if(index == -1)
            return null;
        index --;
        if(index < 0)
            return null;
        return get(index);
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 得到Table有效的总行数
     * @return int
     */
    public int getTableRowCount()
    {
        ETR tr = getHeadTable().get(0);
        int count = 0;
        while(tr != null)
        {
            tr = tr.getNextTrueTR();
            count ++;
        }
        return count;
    }
    /**
     * 得到组件所在的TD
     * @return ETD
     */
    public ETD getComponentInTD()
    {
        return getPanel().getComponentInTD();
    }
    /**
     * 设置全部面板选中
     * @param isSelected boolean
     */
    public void setSelectedAll(boolean isSelected)
    {
        ETable table = getHeadTable();
        while(table != null)
        {
            table.setSelected(isSelected);
            table = table.getNextTable();
        }
    }
    /**
     * 设置选中
     * @param isSelected boolean
     */
    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
        for(int i = 0;i < size();i ++)
            get(i).setSelected(isSelected);
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
     * 测试Table是否是后续Table
     * @param table ETable
     * @return boolean
     */
    public boolean tableIsNextListTable(ETable table)
    {
        if(table == null)
            return false;
        if(this == table)
            return true;
        ETable next = getNextTable();
        while(next != null)
        {
            if(table == next)
                return true;
            next = next.getNextTable();
        }
        return false;
    }
    /**
     * 得到行号
     * @param trNow ETR
     * @return int
     */
    public int getRow(ETR trNow)
    {
        if(trNow == null)
            return -1;
        trNow = trNow.getHeadTR();
        ETR tr = getHeadTable().get(0);
        int row = 0;
        while(tr != null)
        {
            if(trNow == tr)
                return row;
            tr = tr.getNextTrueTR();
            row ++;
        }
        return -1;
    }
    /**
     * 得到TR
     * @param row int
     * @return ETR
     */
    public ETR getTR(int row)
    {
        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return null;
        ETR tr = get(0);
        int i = 0;
        while(tr != null)
        {
            if(i == row)
                return tr;
            tr = tr.getNextTrueTR();
            i ++;
        }
        return null;
    }
    /**
     * 得到全部包含面板
     * @param list TList
     * @param all boolean
     */
    public void getPanelAll(TList list,boolean all)
    {
        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return;
        ETR tr = table.get(0);
        while(tr != null)
        {
            tr.getPanelAll(list,all);
            tr = tr.getNextTrueTR();
        }
        return;
    }
    /**
     * 有上一个连接
     * @return boolean
     */
    public boolean hasPreviousLink()
    {
        return getPreviousTable() != null;
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        if (action == null)
            return;
        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return;
        ETR tr = table.get(0);
        while(tr != null)
        {
            tr.exeAction(action);
            tr = tr.getNextTrueTR();
        }
    }
    /**
     * 删除动作
     * @param flg int
     * 0 彻底删除
     * 1 普通删除
     * 2 清空
     */
    public void onDeleteThis(int flg)
    {
        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return;
        ETR tr = table.get(0);
        while(tr != null)
        {
            ETR next = tr.getNextTrueTR();
            tr.onDeleteThis(flg);
            tr = next;
        }
        setSelected(false);
        setSelectedAll(false);
    }
    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType()
    {
        return TABLE_TYPE;
    }
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        if(getObjectType() == type)
        {
            if(name == null)
                return this;
            if(name.equals(getName()))
                return this;
        }

        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return null;
        ETR tr = table.get(0);
        while(tr != null)
        {
            EComponent com = tr.findObject(name,type);
            if(com != null)
                return com;
            tr = tr.getNextTrueTR();
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
        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return;
        ETR tr = table.get(0);
        while(tr != null)
        {
            tr.filterObject(list,name,type);
            tr = tr.getNextTrueTR();
        }
    }
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return null;
        ETR tr = table.get(0);
        while(tr != null)
        {
            EComponent com = tr.findGroup(group);
            if(com != null)
                return com;
            tr = tr.getNextTrueTR();
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
        ETable table = getHeadTable();
        if(table == null || table.size() == 0)
            return null;
        ETR tr = table.get(0);
        while(tr != null)
        {
            String value = tr.findGroupValue(group);
            if(value != null)
                return value;
            tr = tr.getNextTrueTR();
        }
        return null;
    }

    /**
     * 得到块值
     * @return String
     */
    public String getBlockValue()
    {
        return "";
    }
    /**
     * 得到上一个真块
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock()
    {
        return null;
    }
    /**
     * 得到下一个真块
     * @return IBlock
     */
    public IBlock getNextTrueBlock()
    {
        return null;
    }
    /**
     * 得到最后一行的行号
     * @return int
     */
    public int getThisTableLastRow()
    {
        return getRow(get(size() - 1));
    }
    /**
     * 合并单元格
     * @param startRow int
     * @param startColumn int
     * @param endRow int
     * @param endColumn int
     * @return boolean
     */
    public boolean uniteTD(int startRow,int startColumn,int endRow,int endColumn)
    {
        ETR tr = getTR(startRow);
        ETD td = tr.get(startColumn);
        td.setSpanX(endColumn - startColumn);
        td.setSpanY(endRow - startRow);
        if(td.getSpanX() > 0)
        {
            ETD tde = tr.get(td.findIndex() + td.getSpanX());
            td.setWidth(tde.getX() + tde.getWidth() - td.getX());
        }
        for(int row = startRow;row <= endRow;row ++)
            for(int column = startColumn;column <= endColumn;column ++)
            {
                ETD t = getTR(row).get(column);
                if(t == td)
                    continue;
                t.onDeleteThis(true);
                t.setVisible(false);
                //设置联合TD
                t.setUniteTD(td);
                //
                t.setWidth(0);

                //
                String allStr = t.getLastText().getDString().getString();
                if( null!=allStr && !allStr.equals("")){
                    EText uT = td.getLastText();
                    uT.addString(allStr);
                    uT.setFocus(uT.getString().length());
                }

            }
        return true;
    }
    /**
     * 得到合并单元格的相关TD句柄
     * @param td ETD
     * @return TList
     */
    public TList getUniteTDHandles(ETD td)
    {
        TList list = new TList();
        if(size() == 0)
            return list;
        get(0).getUniteTDHandles(list,td);
        return list;
    }
    /**
     * 克隆
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        ETable table = new ETable(panel);
        table.setName(getName());
        table.setWidth(getWidth());
        table.setHeight(getHeight());
        table.setInsets(getInsets().clone());
        table.setTRInsets(getTRInsets().clone());
        table.setTDInsets(getTDInsets().clone());
        table.setHSpace(getHSpace());
        table.setWSpace(getWSpace());
        table.setShowBorder(isShowBorder());
        table.setShowBorder2(isShowBorder2());
        table.setShowWLine(isShowWLine());
        table.setShowHLine(isShowHLine());
        table.setModel(getModel().clone(table));
        getModel().setTable(this);

        for(int i = 0;i < size();i++)
        {
            ETR tr = get(i);
            table.add(tr.clone(table));
        }
        table.setModify(true);
        return table;
    }
    /**
     * 整理
     */
    public void arrangement()
    {

    }
    /**
     * 是否锁定编辑
     * @param lockEdit boolean
     */
    public void setLockEdit(boolean lockEdit)
    {
        this.lockEdit = lockEdit;
    }
    /**
     * 锁定编辑
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return lockEdit;
    }
    /**
     * 整理行号
     * @param id RowID
     */
    public void resetRowID(RowID id)
    {
        for(int i = 0;i < size();i++)
        {
            ETR tr = get(i);
            tr.setRowID(id.getIndex());
        }
    }
}
