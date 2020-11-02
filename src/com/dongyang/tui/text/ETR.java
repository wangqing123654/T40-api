package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.util.TList;
import java.awt.Rectangle;
import com.dongyang.tui.DInsets;
import com.dongyang.util.StringTool;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.DPoint;
import com.dongyang.ui.util.TDrawTool;
import java.util.List;


/**
 * @author whao 2013~
 * @version 1.0
 */
public class ETR implements EComponent,EEvent,IExeAction
{
    /**
     * 显示
     */
    private boolean visible = true;
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
    private ETRModel model;
    /**
     * 显示边框
     */
    int showBorder;
    /**
     * 显示竖线
     */
    boolean showHLine;
    /**
     * 上一个连接TR
     */
    private ETR previousLinkTR;
    /**
     * 下一个连接TR
     */
    private ETR nextLinkTR;
    /**
     * 上一个TR索引号码(保存专用)
     */
    private int previousTRIndex;
    /**
     * 最大高度
     */
    private int maxHeight = -1;
    /**
     * 设置尺寸调整
     */
    private ResetTR resetTR;
    /**
     * 是否选中
     */
    private boolean isSelected;
    /**
     * 选中对象
     */
    private CSelectTRModel selectedModel;
    /**
     * 行编号
     */
    private int rowID;
    /**
     * 构造器
     */
    public ETR()
    {
        components = new TList();
        //创建尺寸调整对象
        setResetTR(new ResetTR(this));
    }
    /**
     * 构造器
     * @param table ETable
     */
    public ETR(ETable table)
    {
        this();
        setParent(table);
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
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 增加成员
     * @param td ETD
     */
    public void add(ETD td)
    {
        if(td == null)
            return;
        components.add(td);
        td.setParent(this);
    }
    /**
     * 增加成员
     * @param index int
     * @param td ETD
     */
    public void add(int index,ETD td)
    {
        if(td == null)
            return;
        components.add(index,td);
        td.setParent(this);
    }
    /**
     * 查找位置
     * @param td ETD
     * @return int
     */
    public int indexOf(ETD td)
    {
        if(td == null)
            return -1;
        return components.indexOf(td);
    }
    /**
     * 删除成员
     * @param td ETD
     */
    public void remove(ETD td)
    {
        components.remove(td);
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
     * @return ETD
     */
    public ETD get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (ETD)components.get(index);
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
     * 得到横向间距
     * @return int
     */
    public int getWSpace()
    {
        ETable table = getTable();
        if(table == null)
            return 0;
        return table.getWSpace();
    }
    /**
     * 得到内部尺寸
     * @return DInsets
     */
    public DInsets getInsets()
    {
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getTRInsets();
    }
    /**
     * 得到内部X
     * @return int
     */
    public int getInsetsX()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return 0;
        return insets.left;
    }
    /**
     * 得到内部Y
     * @return int
     */
    public int getInsetsY()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return 0;
        return insets.top;
    }
    /**
     * 得到内部宽度
     * @return int
     */
    public int getInsetsWidth()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return getWidth();
        return getWidth() - insets.left - insets.right;
    }
    /**
     * 得到内部高度
     * @return int
     */
    public int getInsetsHeight()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return getWidth();
        return getHeight() - insets.top - insets.bottom;
    }
    /**
     * 得到Table
     * @return ETable
     */
    public ETable getTable()
    {
        EComponent com = getParent();
        if(com == null || !(com instanceof ETable))
            return null;
        return (ETable)com;
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
        return getTable().isPreview();
    }
    /**
     * 是否是Table的最后一行
     * @return boolean
     */
    public boolean isLastTR()
    {
        return getTable().isLastTR(this);
    }
    /**
     * 是否是Table的第一行
     * @return boolean
     */
    public boolean isFirstTR()
    {
        return getTable().isFirstTR(this);
    }
    /**
     * 是否是最后一个单元格
     * @param td ETD
     * @return boolean
     */
    public boolean isLastTD(ETD td)
    {
        if(size() == 0)
            return false;
        if(get(size() - 1) == td)
            return true;
        if(td.getSpanX() == 0)
            return false;
        if(td.getSpanX() + td.findIndex() == size() - 1)
            return true;
        return false;
    }
    /**
     * 是否是第一个单元格
     * @param td ETD
     * @return boolean
     */
    public boolean isFirstTD(ETD td)
    {
        if(size() == 0)
            return false;
        return get(0) == td;
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
        if(getViewManager().isTRDebug())
        {
            g.setColor(new Color(0, 255, 255));
            g.drawRect(x,y,width,height);
        }
        //选中表格行反色
        if(isSelectedDraw())
        {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(x,y,width,height);
        }
        g.setColor(new Color(0,0,0));
        int index = findIndex();
        boolean startRow = index == 0;
        boolean endRow = index == (getTRCount() - 1);

        if(isShowBorder(1))
        {
            int y1 = y;
            if(!startRow)
                y -= 1;
            g.drawLine(x, y1, x + width, y1);
        }
        if(isShowBorder(2))
            g.drawLine(x,y + height,x + width,y + height);
        if(isShowBorder(4))
            g.drawLine(x,y,x,y + height);
        if(isShowBorder(8))
            g.drawLine(x + width,y,x + width,y + height);


        double zoom = getZoom() / 75.0;
        int space = (int) ((double) getWSpace() / 2.0 * zoom + 0.5);
        //绘制页面
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            int tdX = (int)(td.getX() * zoom + 0.5);
            int tdY = (int)(td.getY() * zoom + 0.5);
            int tdW = (int)(td.getWidth() * zoom + 0.5);
            int tdH = (int)(td.getHeight() * zoom + 0.5);
            Rectangle r = g.getClipBounds();
            if(!(r.getX() > x + tdX + tdW ||
               r.getY() > y + tdY + tdH||
               r.getX() + r.getWidth() < x + tdX ||
               r.getY() + r.getHeight() < y + tdY))
            {
                //绘制
                td.paint(g, x + tdX,y + tdY,tdW,tdH);
            }

            /*if(isShowHLine() || !isPreview() || getTable().isShowHLine())
                if(i < size() - 1 && space > 0)
                {
                    if(isShowHLine() || getTable().isShowHLine())
                        g.setColor(new Color(0,0,0));
                    else
                        g.setColor(new Color(168,168,168));
                    int x1 = x + tdX + tdW + space;
                    if(getTable().isShowHLine())
                    {
                        int y1 = y;
                        int y2 = y + height;
                        if(!getTable().isShowBorder())
                        {
                            if (startRow)
                                y1-=getTable().getInsets().top;
                            if (endRow)
                                y2+=getTable().getInsets().bottom - 1;
                        }
                        g.drawLine(x1,y1,x1,y2);
                    }else
                        g.drawLine(x1,y,x1,y + height);
                }*/
        }
        //绘制回车符和焦点
        if(!isPreview() && getNextLinkTR() == null)
        {
            int x1 = getWidth() + getTable().getInsets().right + 2;
            int y1 = 14;
            x1 = (int)(x1 * getZoom() / 75.0 + 0.5);
            y1 = (int)(y1 * getZoom() / 75.0 + 0.5);
            //绘制焦点
            if(isSelectedDraw())
            {
                //选蓝光标
                g.setColor(new Color(0, 0, 0));
                g.fillRect(x + x1 - 2, y + y1 - 16, 9, 18);
            }else if(getFocusTREnter() == this && isShowCursor())
            {
                //闪烁光标
                g.setColor(new Color(0, 0, 0));
                g.fillRect(x + x1, y + y1 - 16, 2, 18);
            }
            g.setColor(new Color(128, 128, 128));
            TDrawTool.drawEnter(x + x1, y + y1, g);
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
        g.setColor(new Color(0,0,0));
        int index = findIndex();
        boolean startRow = index == 0;
        boolean endRow = index == (getTRCount() - 1);
        if(isShowBorder(1))
        {
            int yy = y;
            if(!startRow)
                yy -= 1;
            g.drawLine(x, yy, x + getWidth(), yy);
        }
        if(isShowBorder(2))
        {
            int h = y + getHeight();
            g.drawLine(x,h,x + getWidth(),h);
        }
        if(isShowBorder(4))
            g.drawLine(x,y,x,y + getHeight());
        if(isShowBorder(8))
            g.drawLine(x + getWidth(), y, x + getWidth(),
                       y + getHeight());
        //绘制页面
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            //绘制
            td.print(g, x + td.getX(), y + td.getY());
        }
    }
    /**
     * 得到鼠标所在DD
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInTDIndex(int mouseX,int mouseY)
    {
        double zoom = getZoom() / 75.0;
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if((int)(td.getX() * zoom + 0.5) <= mouseX &&
               (int)((td.getX() + td.getWidth()) * zoom + 0.5) >= mouseX &&
               (int)(td.getY() * zoom + 0.5) <= mouseY &&
               (int)((td.getY() + td.getHeight()) * zoom + 0.5) >= mouseY)
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
     * 2 ETR
     * 3 ETR Enter
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        if(size() == 0)
            return -1;
        //鼠标所在TD
        int tdIndex = getMouseInTDIndex(mouseX,mouseY);
        if(tdIndex != -1)
        {
            ETD td = get(tdIndex);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //事件传递
            return td.onMouseLeftPressed(x,y);
        }
        //如果存在下一个连接不能得到回车焦点
        if(getNextLinkTR() != null)
        {
            ETD td = get(size() - 1);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //事件传递
            return td.onMouseLeftPressed(x,y);
        }
        if(mouseX > getWidth() * getZoom() / 75.0 + 0.5)
        {
            //设置回车焦点
            getFocusManager().setFocusTRE(this);
            return 3;
        }
        return -1;
    }
    /**
     * 右键按下
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        if(size() == 0)
            return;
        //鼠标所在TD
        int tdIndex = getMouseInTDIndex(mouseX,mouseY);
        if(tdIndex != -1)
        {
            ETD td = get(tdIndex);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //事件传递
            td.onMouseRightPressed(x,y);
            return;
        }
        //如果存在下一个连接不能得到回车焦点
        if(getNextLinkTR() != null)
        {
            ETD td = get(size() - 1);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //事件传递
            td.onMouseRightPressed(x,y);
            return;
        }
    }
    /**
     * 是否显示光标
     * @return boolean
     */
    public boolean isShowCursor()
    {
        return getFocusManager().isShowCursor();
    }
    /**
     * 得到回车焦点
     * @return ETR
     */
    public ETR getFocusTREnter()
    {
        return getFocusManager().getFocusTRE();
    }
    /**
     * 得到焦点行
     * @return ETR
     */
    public ETR getFocusTR()
    {
        return getFocusManager().getFocusTR();
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        //鼠标所在TD
        int tdIndex = getMouseInTDIndex(mouseX,mouseY);
        if(tdIndex != -1)
        {
            ETD td = get(tdIndex);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //事件传递
            return td.onDoubleClickedS(x,y);
        }
        //测试用
        //setSelected(!isSelected());
        return false;
    }
    /**
     * 新列
     * @return ETD
     */
    public ETD newTD()
    {
        ETD td = new ETD();
        add(td);
        return td;
    }
    /**
     * 得到TD最大高度
     * @return int
     */
    public int getTDMaxHeight()
    {
        int height = 0;
        DInsets insets = getInsets();
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            height = Math.max(height,td.getHeight());
        }
        return height + insets.top + insets.bottom;
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
     * @param td ETD
     * @return EText
     */
    public EText getPreviousText(ETD td)
    {
        if(td == null)
            return null;
        int index = indexOf(td);
        if(index == -1)
            return null;
        EText text = getLastText(index - 1);
        if(text != null)
            return text;
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getPreviousText(this);
    }
    /**
     * 得到最后一个
     * @param index int
     * @return EText
     */
    public EText getLastText(int index)
    {
        for(int i = index;i >= 0;i--)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            EText text = td.getLastText();
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
        return getFirstText(0);
    }
    /**
     * 得到下一个
     * @param td ETD
     * @return EText
     */
    public EText getNextText(ETD td)
    {
        if(td == null)
            return null;
        int index = indexOf(td);
        if(index == -1)
            return null;
        EText text = getFirstText(index + 1);
        if(text != null)
            return text;
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getNextText(this);
    }
    /**
     * 查找第一个
     * @param index int
     * @return EText
     */
    public EText getFirstText(int index)
    {
        for(int i = index;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            EText text = td.getFirstText();
            if(text != null)
                return text;
        }
        return null;
    }
    /**
     * 测试高度
     * @param userHeight int
     * @return boolean
     */
    public boolean checkHeight(int userHeight)
    {
        return true;
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
            if(getParent() != null)
                getParent().setModify(true);
            return;
        }
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            if(td.isModify())
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
        //清零
        getResetTR().clear();
        //设置尺寸范围
        getResetTR().setRectangle(0,
                                  0,
                                  getWidth(),
                                  getMaxHeight());
        //调整尺寸
        getResetTR().reset();
        setModify(false);
    }
    /**
     * 连接
     */
    public void linkTR()
    {
        ETR tr = getNextLinkTR();
        if(tr == null)
            return;
        for(int i = 0;i < tr.size();i++)
        {
            ETD td = tr.get(i);
            ETD tdP = td.getPreviousLinkTD();
            for(int j = 0;j < td.size();j++)
            {
                EPanel panel = td.get(j);
                if(panel.getPreviousLinkPanel() != null)
                {
                    panel.getPreviousLinkPanel().add(panel);
                    continue;
                }
                tdP.add(panel);
            }
            tdP.setNextLinkTD(td.getNextLinkTD());
            if(td.getNextLinkTD() != null)
                td.getNextLinkTD().setPreviousLinkTD(tdP);
        }
        setNextLinkTR(tr.getNextLinkTR());
        if(getNextLinkTR() != null)
            getNextLinkTR().setPreviousLinkTR(this);
        ETable table = tr.getTable();
        table.remove(tr);
        if(table.size() == 0)
            table.removeThis();
    }
    /**
     * 调整尺寸
     * @param index int
     */
    public void reset(int index)
    {
        if(index < 0 || index >= size())
            return;
        int x = getInsetsX();
        int y = getInsetsY();
        int space = getWSpace();
        int height = 0;
        //全部面板调整尺寸
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            td.setX(x);
            td.setY(y);
            if(getMaxHeight() > 0)
            {
                td.setMaxHeight(getMaxHeight() - y - getInsets().bottom);
                td.setModify(true);
            }
            td.reset();
            height = Math.max(height,td.getHeight());
            x = td.getX() + td.getWidth() + space;
        }
        DInsets insets = getInsets();
        setHeight(height + insets.top + insets.bottom);
    }
    /**
     * 查找组件位置
     * @param tr ETR
     * @return int
     */
    public int findIndex(ETR tr)
    {
        ETable table = getTable();
        if(table == null)
            return -1;
        return table.indexOf(tr);
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
     * 得到行个数
     * @return int
     */
    public int getTRCount()
    {
        return getTable().size();
    }
    /**
     * 得到坐标位置
     * @return String
     */
    public String getIndexString()
    {
        ETable table = getTable();
        if(table == null)
            return "Parent:Null";
        return table.getIndexString() + ",TR:" + findIndex();
    }
    public String toString()
    {
        return "<ETR size=" + size() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",visible=" + isVisible() +
                ",isModify=" + isModify() +
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
            ETD td = get(j);
            td.debugShow(i + 2);
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
        System.out.println(StringTool.fill(" ",i) + getIndexString() + " " + this);
        for(int j = 0;j < size();j++)
        {
            ETD td = get(j);
            td.debugModify(i + 2);
        }
    }
    /**
     * 设置模型
     * @param model ETRModel
     */
    public void setModel(ETRModel model)
    {
        this.model = model;
    }
    /**
     * 得到模型
     * @return ETRModel
     */
    public ETRModel getModel()
    {
        return model;
    }
    /**
     * 创建模型
     * @return ETRModel
     */
    public ETRModel createModel()
    {
        ETRModel model = new ETRModel(this);
        setModel(model);
        return model;
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        removeThis(true);
    }
    /**
     * 删除自己
     * @param b boolean
     */
    public void removeThis(boolean b)
    {
        for(int i = size() - 1;i >= 0;i--)
            get(i).removeThis();
        ETable table = getTable();
        if(table == null)
            return;
        table.remove(this);
        if(getPreviousLinkTR() != null)
            getPreviousLinkTR().setNextLinkTR(getNextLinkTR());
        if(getNextLinkTR() != null)
            getNextLinkTR().setPreviousLinkTR(getPreviousLinkTR());
        if(b && table.size() == 0)
            table.removeThis();
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
        if(getModel() != null)
        {
            s.writeShort(3);
            getModel().writeObject(s);
        }
        s.writeInt(4,getShowBorder(),0);
        s.writeBoolean(5,isShowHLine(),false);
        s.writeBoolean(6,isVisible(),true);
        if(getNextLinkTR() != null)
        {
            int index = getTRSaveManager().add(this);
            getNextLinkTR().setPreviousTRIndex(index);
            s.writeShort(7);
        }
        if(getPreviousLinkTR() != null)
        {
            s.writeShort(8);
            s.writeInt(getPreviousTRIndex());
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
            createModel();
            getModel().readObject(s);
            return true;
        case 4:
            setShowBorder(s.readInt());
            return true;
        case 5:
            setShowHLine(s.readBoolean());
            return true;
        case 6:
            setVisible(s.readBoolean());
            return true;
        case 7:
            getTRSaveManager().add(this);
            break;
        case 8:
            ETR tr = getTRSaveManager().get(s.readInt());
            setPreviousLinkTR(tr);
            tr.setNextLinkTR(this);
            break;
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
        s.WO("<ETR Width=" + getWidth() + " Height=" + getHeight() + " >",c);
        if(getModel() != null)
            getModel().writeObjectDebug(s,c + 1);
        for(int i = 0;i < size();i ++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            td.writeObjectDebug(s,c + 1);
        }
        s.WO("</ETR>",c);
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
            ETD td = get(i);
            td.writeObject(s);
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
        //读取列
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            ETD td = newTD();
            td.readObject(s);
            //System.out.println( "td: " + td);
        }
    }
    /**
     * 得到横坐标位置
     * @return int
     */
    public int getPointX()
    {
        return getTable().getPointX() + getX();
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
        getTable().getPath(list);
    }
    /**
     * 得到路径索引
     * @return TList
     */
    public TList getPathIndex()
    {
        TList list = new TList();
        getPathIndex(list);
        return list;
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
        getTable().getPathIndex(list);
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
     * 设置显示边框
     * @param showBorder int
     */
    public void setShowBorder(int showBorder)
    {
        this.showBorder = showBorder;
    }
    /**
     * 设置显示边框
     * @param x int
     * 1 上
     * 2 下
     * 4 左
     * 8 右
     * @param b boolean true 显示 false 不显示
     */
    public void setShowBorder(int x,boolean b)
    {
        if(b)
            showBorder = showBorder | x;
        else
            showBorder = ~(~showBorder | x);
    }
    /**
     * 得到显示边框
     * @return int
     */
    public int getShowBorder()
    {
        return showBorder;
    }
    /**
     * 得到显示边框
     * @param x int
     * 1 上
     * 2 下
     * 4 左
     * 8 右
     * @return boolean true 显示 false 不显示
     */
    public boolean isShowBorder(int x)
    {
        return (showBorder & x) == x;
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
     * 设置是否显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
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
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DPoint point = getTable().getScreenPoint();
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
     * 设置上一个连接TR
     * @param previousLinkTR ETR
     */
    public void setPreviousLinkTR(ETR previousLinkTR)
    {
        this.previousLinkTR = previousLinkTR;
    }
    /**
     * 得到上一个连接TR
     * @return ETR
     */
    public ETR getPreviousLinkTR()
    {
        return previousLinkTR;
    }
    /**
     * 设置下一个连接TD
     * @param nextLinkTR ETR
     */
    public void setNextLinkTR(ETR nextLinkTR)
    {
        this.nextLinkTR = nextLinkTR;
    }
    /**
     * 得到下一个连接TR
     * @return ETR
     */
    public ETR getNextLinkTR()
    {
        return nextLinkTR;
    }
    /**
     * 是否有下一个连接
     * @return boolean
     */
    public boolean hasNextLinkTR()
    {
        return getNextLinkTR() != null;
    }
    /**
     * 得到TR控制类
     * @return MTRSave
     */
    public MTRSave getTRSaveManager()
    {
        return getPM().getFileManager().getTRSaveManager();
    }
    /**
     * 设置上一个TR的索引号码(保存专用)
     * @param index int
     */
    public void setPreviousTRIndex(int index)
    {
        previousTRIndex = index;
    }
    /**
     * 得到上一个TR的索引号码(保存专用)
     * @return int
     */
    public int getPreviousTRIndex()
    {
        return previousTRIndex;
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
     * 创建下一页连接TR
     * @return ETR
     */
    public ETR createNextLinkTR()
    {
        ETR tr = getNextLinkTR();
        if(tr != null)
            return tr;
        ETable table = getTable().getNextTable();
        if(table == null)
            table = getTable().createNextLinkTable();
        int index = findIndex();
        for(int i = getTable().size() - 1;i > index;i--)
        {
            ETR tr1 = getTable().get(i);
            getTable().remove(tr1);
            table.add(0,tr1);
        }
        tr = table.newTR(0);
        tr.setX(0);
        tr.setY(0);
        tr.setWidth(getWidth());
        tr.setModify(true);
        setNextLinkTR(tr);
        tr.setPreviousLinkTR(this);
        tr.setModel(getModel());
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            ETD newTD = tr.newTD();
            newTD.setX(td.getX());
            newTD.setY(td.getY());
            newTD.setWidth(td.getWidth());
            td.setNextLinkTD(newTD);
            newTD.setPreviousLinkTD(td);
            newTD.setVisible(td.isVisible());
            newTD.setUniteTD(td.getUniteTD());
            newTD.setSpanX(td.getSpanX());
            newTD.setSpanY(td.getSpanY());
        }
        return tr;
    }
    /**
     * 设置尺寸调整
     * @param resetTR ResetTR
     */
    public void setResetTR(ResetTR resetTR)
    {
        this.resetTR = resetTR;
    }
    /**
     * 得到尺寸调整
     * @return ResetTR
     */
    public ResetTR getResetTR()
    {
        return resetTR;
    }
    /**
     * 回车事件
     * @return boolean
     */
    public boolean onEnter()
    {
        int index = getTable().insertRow(findIndex() + 1);
        ETR tr = getTable().get(index);
        if(tr == null || tr.size() == 0)
            return true;
        ETD td = tr.get(0);
        EText text = (EText)td.get(0).get(0);
        getFocusManager().setFocusText(text);
        return true;
    }
    /**
     * 焦点向右移动
     * @param td ETD
     * @return boolean
     */
    public boolean onFocusToRight(ETD td)
    {
        ETD next = getNextTD(td);
        while(next != null)
        {
            //得到首TD
            next = next.getHeadTD();
            if(next.setFocus())
                return true;
            next = getNextTD(next);
        }
        getFocusManager().setFocusTRE(getEndTR());
        return true;
    }
    /**
     * 焦点向左移动
     * @param td ETD
     * @return boolean
     */
    public boolean onFocusToLeft(ETD td)
    {
        ETD next = getPreviousTD(td);
        while(next != null)
        {
            if(next.getEndTD().setFocusLast())
                return true;
            next = getPreviousTD(next);
        }
        ETR tr = getHeadTR();
        tr = getTable().getPreviousTR(tr);
        if(tr != null)
        {
            getFocusManager().setFocusTRE(tr.getEndTR());
            return true;
        }
        if(getTable().getPreviousTable() != null)
            if(getTable().getPreviousTable().setFocusLast())
                return true;;
        if(getTable().getPanel().onNextPanelFocusToLeft())
            return true;
        return false;
    }
    /**
     * 得到下一个TD
     * @param td ETD
     * @return ETD
     */
    public ETD getNextTD(ETD td)
    {
        int index = indexOf(td);
        if(index == -1)
            return null;
        index ++;
        if(index >= size())
            return null;
        return get(index);
    }
    /**
     * 得到上一个TD
     * @param td ETD
     * @return ETD
     */
    public ETD getPreviousTD(ETD td)
    {
        int index = indexOf(td);
        if(index == -1)
            return null;
        index --;
        if(index < 0)
            return null;
        return get(index);
    }
    /**
     * 得到首TR
     * @return ETR
     */
    public ETR getHeadTR()
    {
        if(getPreviousLinkTR() != null)
            return getPreviousLinkTR().getHeadTR();
        return this;
    }
    /**
     * 得到尾TR
     * @return ETR
     */
    public ETR getEndTR()
    {
        if(getNextLinkTR() != null)
            return getNextLinkTR().getEndTR();
        return this;
    }
    /**
     * 焦点向右移动
     */
    public void onFocusToRight()
    {
        if(getTable().onFocusToRight(this))
            return;
        getTable().getPanel().onNextPanelFocusToRight();
    }
    /**
     * 焦点向左移动
     */
    public void onFocusToLeft()
    {
        if(size() == 0)
            return;
        ETD td = get(size() - 1);
        td.setFocusLast();
    }
    /**
     * 得到焦点
     * @return boolean
     */
    public boolean setFocus()
    {
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(!td.isVisible())
                continue;
            if(td.setFocus())
                return true;
        }
        getFocusManager().setFocusTRE(getHeadTR());
        return true;
    }
    /**
     * 设置焦点到最后
     * @return boolean
     */
    public boolean setFocusLast()
    {
        getFocusManager().setFocusTRE(getEndTR());
        return true;
    }
    /**
     * 得到下一个焦点模块
     * @param td ETD
     * @return IBlock
     */
    public IBlock getDownFocusBlock(ETD td)
    {
        int index = indexOf(td);
        //得到下一个有效行
        ETR tr = getNextTrueTR();
        if (tr != null)
        {
            if (index >= tr.size())
                index = tr.size() - 1;
            ETD ntd = tr.get(index);
            EPanel panel = ntd.get(0);
            return panel.get(0);
        }
        //得到下一个面板
        EPanel panel = getTable().getPanel().getNextPanel();
        if (panel != null)
            return panel.getFirstText();
        return null;
    }
    /**
     * 得到上一个焦点模块
     * @param td ETD
     * @return IBlock
     */
    public IBlock getUpFocusBlock(ETD td)
    {
        int index = indexOf(td);
        //得到上一个有效行
        ETR tr = getPreviousTrueTR();
        if (tr != null)
        {
            if (index >= tr.size())
                index = tr.size() - 1;
            ETD ntd = tr.get(index);
            while(ntd.size() == 0)
                ntd = ntd.getPreviousLinkTD();
            EPanel panel = ntd.get(ntd.size() - 1);
            return panel.getLastText();
        }
        //得到下一个面板
        EPanel panel = getTable().getPanel().getPreviousPanel();
        if (panel != null)
            return panel.getLastText();
        return null;
    }
    /**
     * 得到下一个有效行
     * @return ETR
     */
    public ETR getNextTrueTR()
    {
        ETR tr = getEndTR();
        ETable table = tr.getTable();
        int index = tr.findIndex() + 1;
        //在末尾行
        if(index < table.size())
            return tr.getTable().get(index);
        table = table.getNextTable();
        if(table == null || table.size() == 0)
            return null;
        return table.get(0);
    }
    /**
     * 得到上一个有效行
     * @return ETR
     */
    public ETR getPreviousTrueTR()
    {
        ETR tr = getHeadTR();
        ETable table = tr.getTable();
        int index = tr.findIndex() - 1;
        //在末尾行
        if(index >= 0)
            return tr.getTable().get(index);
        table = table.getPreviousTable();
        if(table == null || table.size() == 0)
            return null;
        return table.get(table.size() - 1);
    }
    /**
     * 焦点向上移动
     */
    public void onEocusToUp()
    {
        getPreviousTrueTR().onFocusToLeft();
    }
    /**
     * 焦点向下移动
     */
    public void onEocusToDown()
    {
        getNextTrueTR().onFocusToLeft();
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
     * 同步宽度
     */
    public void sychWidth()
    {
        ETableModel model = getTable().getModel();

        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);

            //
            int width = model.get(i);
            if(width != td.getWidth())
            {
            	if( !td.isVisible() && null!=td.getUniteTD()  ){
            		 td.setWidth(0);

            		 ETD firstTD = td.getUniteTD();

            		 if(  td.getTR().findIndex() == firstTD.getTR().findIndex() ){

                   		 int newW = firstTD.getWidth()+width+1;

                		 int tLength = getTable().getWidth();

                		 newW = newW > tLength ? tLength-width : newW;

                		 firstTD.setWidth(newW);

                	//	 System.out.println( "UniteTD " + firstTD );
            		 }

            	}else{
            		 td.setWidth(width);
            	}

                td.modifyPanels();
            }

           // System.out.println( "moveTD  " +td );
        }
    }

    /**
     * 选中全部TR
     * @param isSelected boolean
     */
    public void setSelectedAll(boolean isSelected)
    {
        ETR tr = getHeadTR();
        while(tr != null)
        {
            tr.setSelected(isSelected);
            tr = tr.getNextLinkTR();
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
     * 是否选中绘制
     * @return boolean
     */
    public boolean isSelectedDraw()
    {
        if(isSelected())
            return true;
        if(!getFocusManager().isSelected() &&
           getFocusManager().getFocusTR() == getHeadTR())
            return true;
        return false;
    }
    /**
     * 得到组件所在的TD
     * @return ETD
     */
    public ETD getComponentInTD()
    {
        return getTable().getComponentInTD();
    }
    /**
     * 得到所在的面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return getTable().getPanel();
    }
    /**
     * 得到行
     * @return int
     */
    public int getRow()
    {
        return getTable().getRow(this);
    }
    /**
     * 得到全部包含面板
     * @param list TList
     * @param all boolean
     */
    public void getPanelAll(TList list,boolean all)
    {
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            tr.get(i).getPanelAll(list,all);
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        if(action == null)
            return;
        ETR tr = getHeadTR();
        //删除动作
        if(action.getType() == EAction.DELETE)
        {
            if(isLockEdit())
                return;
            onDeleteThis(action.getInt(0));
            if(getSelectedModel() != null)
            {
                getSelectedModel().getSelectList().remove(getSelectedModel());
                getSelectedModel().removeThis();
            }
            return;
        }
        for(int i = 0;i < tr.size();i++)
            tr.get(i).exeAction(action);
    }
    /**
     * 焦点在TR中(包括连接TR)
     * @return boolean
     */
    public boolean focusInTR()
    {
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            if(tr.get(i).focusInTD())
                return true;
        return false;
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
        switch(flg)
        {
        case 0:
        case 1:
            deleteTR();
            return;
        case 2:
            clearTR();
            return;
        }
    }
    /**
     * 删除TR
     */
    public void deleteTR()
    {
        //清空TR
        clearTR();
        //如果存在固定文本 不能删除TR
        if(hasFixed())
            return;
        if(getFocusTREnter() == this || getFocusTR() == this || focusInTR())
        {
            ETR tr = getNextTrueTR();
            if(tr != null)
                tr.setFocus();
            else
                getPanel().setNextFocus();
        }
        setModify(true);
        ETR tr = getHeadTR();
        while(tr != null)
        {
            ETR next = tr.getNextLinkTR();
            tr.removeThis(true);
            tr = next;
        }
    }
    /**
     * 存在固定文本
     * @return boolean
     */
    public boolean hasFixed()
    {
        for(int i = 0;i < size();i++)
            if(get(i).hasFixedAll())
                return true;
        return false;
    }
    /**
     * 清空TR
     */
    public void clearTR()
    {
        if(isLockEdit())
            return;
        setModify(true);
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            tr.get(i).onDeleteThis(false);
        if(getFocusTREnter() == this || getFocusTR() == this || focusInTR())
            tr.setFocus();
    }
    /**
     * 设置选中对象
     * @param selectedModel CSelectTRModel
     */
    public void setSelectedModel(CSelectTRModel selectedModel)
    {
        this.selectedModel = selectedModel;
    }
    /**
     * 得到选中对象
     * @return CSelectTRModel
     */
    public CSelectTRModel getSelectedModel()
    {
        return selectedModel;
    }
    /**
     * 创建选中对象
     * @return CSelectTRModel
     */
    public CSelectTRModel createSelectedModel()
    {
        CSelectTRModel model = getSelectedModel();
        if(model != null)
            getFocusManager().getSelectedModel().removeSelectList(model.getSelectList());
        model = new CSelectTRModel(this);
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
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
        {
            EComponent com = tr.get(i).findObject(name,type);
            if(com != null)
                return com;
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
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            tr.get(i).filterObject(list,name,type);
    }
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
        {
            EComponent com = tr.get(i).findGroup(group);
            if(com != null)
                return com;
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
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
        {
            String value = tr.get(i).findGroupValue(group);
            if(value != null)
                return value;
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
        ETD td = get(index);
        return td.onMouseMoved(mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5),
                                           mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5));
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
            ETD td = get(i);
            if(mouseX >= (int)(td.getX() * zoom + 0.5) &&
               mouseX <= (int)((td.getX() + td.getWidth()) * zoom + 0.5) &&
               mouseY >= (int)(td.getY() * zoom + 0.5) &&
               mouseY <= (int)((td.getY() + td.getHeight()) * zoom + 0.5))
                return i;
        }
        return -1;
    }
    /**
     * 得到合并单元格的相关TD句柄
     * @param list TList
     * @param td ETD
     */
    public void getUniteTDHandles(TList list,ETD td)
    {
        for(int i = 0;i < size();i++)
            get(i).getUniteTDHandles(list,td);
        ETR tr = getNextTrueTR();
        if(tr == null)
            return;
        tr.getUniteTDHandles(list,td);
    }
    /**
     * 锁定编辑
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return getTable().isLockEdit();
    }
    /**
     * 克隆
     * @param table ETable
     * @return ETR
     */
    public ETR clone(ETable table)
    {
        ETR tr = new ETR(table);
        tr.setVisible(isVisible());
        tr.setX(getX());
        tr.setY(getY());
        tr.setWidth(getWidth());
        tr.setHeight(getHeight());
        tr.setShowBorder(getShowBorder());
        tr.setShowHLine(isShowHLine());
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            tr.add(td.clone(tr));
        }
        tr.setModify(true);
        return tr;
    }
    /**
     * 设置行号
     * @param rowID int
     */
    public void setRowID(int rowID)
    {
        this.rowID = rowID;
    }
    /**
     * 得到行号
     * @return int
     */
    public int getRowID()
    {
        return rowID;
    }
}
