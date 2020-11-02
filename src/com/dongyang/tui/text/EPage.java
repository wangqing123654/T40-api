package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.tui.DInsets;
import com.dongyang.util.StringTool;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.DCursor;
import com.dongyang.tui.DText;
import java.awt.Rectangle;
import com.dongyang.tui.DPoint;
import java.util.List;
import java.awt.Font;

/**
 *
 * <p>Title: 页</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @version 1.0
 */
public class EPage implements EComponent
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
     * 基础阅览宽度
     */
    private int baseWidth = 0;
    /**
     * 基础阅览高度
     */
    private int baseHeight = 0;
    /**
     * 修改状态
     */
    private boolean modify;
    /**
     * 设置尺寸调整
     */
    private ResetPanel resetPanel;
    /**
     * 开始行号
     */
    private int rowStartID;
    /**
     * 结束行号
     */
    private int rowEndID;
    /**
     * 构造器
     */
    public EPage()
    {
        components = new TList();
        //创建尺寸调整对象
        setResetPanel(new ResetPanel(this));
    }
    /**
     * 构造器
     * @param pageManager MPage
     */
    public EPage(MPage pageManager)
    {
        this();
        setParent(pageManager);
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getPageManager().getPM();
    }
    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager()
    {
        return (MPage)getParent();
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
     * 得到字符数据管理器
     * @return MString
     */
    public MString getStringManager()
    {
        return getPM().getStringManager();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }
    /**
     * 得到内部尺寸
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return getPageManager().getInsets();
    }
    /**
     * 得到编辑区内部尺寸
     * @return DInsets
     */
    public DInsets getEditInsets()
    {
        return getPageManager().getEditInsets();
    }
    /**
     * 得到内部X
     * @return int
     */
    public int getInsetsX()
    {
        return getInsets().left + 1 + getEditInsets().left;
    }
    /**
     * 得到内部Y
     * @return int
     */
    public int getInsetsY()
    {
        return getInsets().top + 1 + getEditInsets().top;
    }
    /**
     * 得到内部宽度
     * @return int
     */
    public int getInsetsWidth()
    {
        DInsets insets = getInsets();
        return getWidth() - insets.left - insets.right - 2 - getEditInsets().left - getEditInsets().right;
    }
    /**
     * 得到内部高度
     * @return int
     */
    public int getInsetsHeight()
    {
        DInsets insets = getInsets();
        return getHeight() - insets.top - insets.bottom - 2 - getEditInsets().top - getEditInsets().bottom;
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
        if(size() == 0)
            getPageManager().remove(this);
    }
    /**
     * 删除成员
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
        if(size() == 0)
            getPageManager().remove(this);
    }
    /**
     * 删除全部成员
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
     * @param panel EPanel
     * @return int
     */
    public int indexOf(EPanel panel)
    {
        if(panel == null)
            return -1;
        return components.indexOf(panel);
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
    }

    /**
     * 得到横坐标
     * @return int
     */
    public int getX()
    {
        return 0;
    }

    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
    }

    /**
     * 得到纵坐标
     * @return int
     */
    public int getY()
    {
        return 0;
    }
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
    }

    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        if(getViewManager().getViewStyle() == 0)
            getPageManager().setWidth(width);
    }

    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return getPageManager().getWidth();
    }

    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        if(getViewManager().getViewStyle() == 0)
            getPageManager().setHeight(height);
    }

    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return getPageManager().getHeight();
    }
    /**
     * 设置基础阅览宽度
     * @param baseHeight int
     */
    public void setBaseWidth(int baseHeight)
    {
        this.baseWidth = baseWidth;
    }
    /**
     * 得到基础阅览宽度
     * @return int
     */
    public int getBaseWidth()
    {
        if(baseWidth != 0)
            return baseWidth;
        DInsets insets = getInsets();
        return getWidth() - insets.left - insets.right - 2;
    }
    /**
     * 设置基础阅览高度
     * @param baseHeight int
     */
    public void setBaseHeight(int baseHeight)
    {
        this.baseHeight = baseHeight;
    }
    /**
     * 得到基础阅览高度
     * @return int
     */
    public int getBaseHeight()
    {
        if(baseHeight != 0)
            return baseHeight;
        DInsets insets = getInsets();
        return getHeight() - insets.top - insets.bottom - 2;
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
     * 绘制背景
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void paintBakViewBase(Graphics g,int x,int y)
    {
        if(getViewManager().getViewStyle() != 0)
            return;
        g.setColor(new Color(244, 244, 244));
        g.fillRect(x, y, (int)(getInsets().left * getZoom() / 75.0) - 1, (int)(getHeight() * getZoom() / 75.0) - 1);
    }
    /**
     * 绘制行号
     * @param g Graphics
     * @param x int
     * @param y int
     * @param index int
     */
    public void paintBakViewBaseHH(Graphics g,int x,int y,int index)
    {
        if(getViewManager().getViewStyle() != 0)
            return;
        g.setColor(new Color(153, 153, 204));
        g.setFont(new Font("宋体",0,12));
        g.drawString("" +index,x + 3,y - 2);
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
        if(getViewManager().isPageDebug())
        {
            g.setColor(new Color(255, 0, 255));
            g.drawRect(x, y, (int)(width * getZoom() / 75.0) - 1, (int)(height * getZoom() / 75.0) - 1);
        }
        //绘制背景
        paintBakViewBase(g,x,y);

        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            Rectangle r = g.getClipBounds();
            int x1 = (int)(panel.getX() * getZoom() / 75.0);
            int y1 = (int)(panel.getY() * getZoom() / 75.0);
            int w1 = (int)(panel.getWidth() * getZoom() / 75.0);
            int h1 = (int)(panel.getHeight() * getZoom() / 75.0);
            //是否在绘图范围内
            if(r.getX() > x + x1 + w1 ||
               r.getY() > y + y1 + h1 ||
               r.getX() + r.getWidth() < x + x1 ||
               r.getY() + r.getHeight() < y + y1)
                continue;
            //绘制行号
            paintBakViewBaseHH(g,0,y + y1 + h1,i + 1);
            //绘制面板
            panel.paint(g,x + x1,y + y1,w1,h1);
        }
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
     * 得到鼠标所在面板
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInPanelIndex(int mouseX,int mouseY)
    {
        if(size() == 0)
            return -1;
        double zoom = getZoom() / 75.0;
        for(int i = 0;i < size();i++)
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
     * 得到鼠标所在面板
     * @param mouseY int
     * @return int
     */
    public int getMouseInPanelIndex(int mouseY)
    {
        double zoom = getZoom() / 75.0;
        if(size() == 0)
            return -1;
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            int h = (int)((panel.getY() + panel.getHeight()) * zoom + 0.5);
            if(mouseY < h)
                return i;
        }
        return size() - 1;
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
     * 4 EPic
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        //鼠标所在面板
        int panelIndex = getMouseInPanelIndex(mouseY);
        if(panelIndex == -1)
            return -1;
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
        int panelIndex = getMouseInPanelIndex(mouseY);
        if(panelIndex == -1)
            return;
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
        int panelIndex = getMouseInPanelIndex(mouseY);
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
     * 页面宽度变化事件
     * @param value int
     */
    public void onChanglePagehWidth(int value)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.setWidth(panel.getWidth() + value);
        }
    }
    /**
     * 页面高度变化事件
     * @param value int
     */
    public void onChanglePagehHeight(int value)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.setModify(true);
        }
    }
    /**
     * 查找组件位置
     * @param page EPage
     * @return int
     */
    public int findIndex(EPage page)
    {
        return getPageManager().indexOf(page);
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
     * 得到屏幕Y
     * @return int
     */
    public int getScreenY()
    {
        //得到页位置
        int pageIndex = findIndex();
        //页面高度
        int pageHeight = getHeight();
        return pageIndex * (pageHeight + getViewManager().getPageBorderSize()) + getViewManager().getPageBorderSize();
    }
    /**
     * 得到屏幕内部Y
     * @return int
     */
    public int getScreenInsetsY()
    {
        return getScreenY();
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
        return null;
    }
    /**
     * 得到上一个页
     * @return EPage
     */
    public EPage getPreviousPage()
    {
        return getPreviousPage(this);
    }
    /**
     * 得到下一个页
     * @return EPage
     */
    public EPage getNextPage()
    {
        return getNextPage(this);
    }
    /**
     * 得到上一个页
     * @param page EPage
     * @return EPage
     */
    public EPage getPreviousPage(EPage page)
    {
        if(getPageManager() == null)
            return null;
        int index = findIndex(page);
        if(index < 1)
            return null;
        return getPageManager().get(index - 1);
    }
    /**
     * 得到下一个页
     * @param page EPage
     * @return EPage
     */
    public EPage getNextPage(EPage page)
    {
        if(getPageManager() == null)
            return null;
        int index = findIndex(page);
        if(index >= getPageManager().size() - 1)
            return null;
        return getPageManager().get(index + 1);
    }
    /**
     * 得到最后的面板
     * @return EPanel
     */
    public EPanel getLastPanel()
    {
        if(size() == 0)
            return null;
        return get(size() - 1);
    }
    /**
     * 得到第一个面板
     * @return EPanel
     */
    public EPanel getFirstPanel()
    {
        if(size() == 0)
            return null;
        return get(0);
    }
    /**
     * 得到最后的文本
     * @return EText
     */
    public EText getLastText()
    {
        if(size() == 0)
            return null;
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
     * 查找第一个Text
     * @return EText
     */
    public EText getFirstText()
    {
        if(size() == 0)
            return null;
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
     * 得到上一页最后的面板
     * @return EPanel
     */
    public EPanel getPreviousPageLastPanel()
    {
        EPage page = getPreviousPage();
        if(page == null)
            return null;
        return page.getLastPanel();
    }
    /**
     * 得到下一页第一个的面板
     * @return EPanel
     */
    public EPanel getNextPageFirstPanel()
    {
        EPage page = getNextPage();
        if(page == null)
            return null;
        return page.getFirstPanel();
    }
    /**
     * 得到有效高度
     * @param panel EPanel
     * @return int
     */
    public int getUserHeight(EPanel panel)
    {
        if(panel == null)
            return 0;
        return getHeight() - getInsets().bottom - getEditInsets().bottom - panel.getY();
    }
    /**
     * 创建下一页连接面板
     * @return EPanel
     */
    public EPanel createNextPageLinkPanel()
    {
        if(getPageManager() == null)
            return null;
        EPage page = getNextPage();
        if(page == null)
            page = getPageManager().newPage();
        return page.newPanel(0);
    }
    /**
     * 得到上一个页面最后的Text
     * @return EText
     */
    public EText getPreviousPageLastText()
    {
        EPage page = getPreviousPage();
        while(page != null)
        {
            EText text = page.getLastText();
            if(text != null)
                return text;
            page = getPreviousPage(page);
        }
        return null;
    }
    /**
     * 得到下一个页面第一个的Text
     * @return EText
     */
    public EText getNextPageFirstText()
    {
        EPage page = getNextPage();
        while(page != null)
        {
            EText text = page.getFirstText();
            if(text != null)
                return text;
            page = getNextPage(page);
        }
        return null;
    }
    /**
     * 移动后续面板到下一个页面
     * @param index int
     * @param panel EPanel
     */
    public void movePanelToNextPage(int index,EPanel panel)
    {
        if(index >= size())
            return;
        if(panel == null)
            return;
        EPage page = panel.getPage();
        if(page == null)
            return;
        int panelIndex = panel.findIndex();
        if(panelIndex == -1)
            return;
        for(int i = index;i < size();i++)
        {
            EPanel p = get(i);
            if(p == null)
                continue;
            panelIndex ++;
            page.add(panelIndex,p);
        }
        for(int i = size() - 1;i >= index;i--)
            remove(i);
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
        if(!isModify())
            return;
        //清零
        getResetPanel().clear();
        //设置尺寸范围
        getResetPanel().setRectangle(getInsetsX(),
                                     getInsetsY(),
                                     getInsetsWidth(),
                                     getInsetsHeight());
        //调整尺寸
        getResetPanel().reset();
        setModify(false);
    }
    /**
     * 调整尺寸(废除)
     * @param index int
     */
    public void reset(int index)
    {
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
        EPage page = p2.getPage();
        if(page == null)
            return;
        int indexNext = page.indexOf(p2);
        if(indexNext == -1)
            return;
        index ++;
        while(index < size())
        {
            EPanel p = get(index);
            indexNext ++;
            page.add(indexNext,p);
            remove(index);
        }
    }
    /**
     * 得到坐标位置
     * @return String
     */
    public String getIndexString()
    {
        return "Page:" + findIndex();
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
        System.out.println(StringTool.fill(" ",i) + this);
        for(int j = 0;j < size();j++)
        {
            EPanel panel = get(j);
            panel.debugModify(i + 2);
        }
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
        s.WO("<EPage>",c);
        for(int i = 0;i < size();i ++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.writeObjectDebug(s,c + 1);
        }
        s.WO("</EPage>",c);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeShort(-1);

        //保存面板
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
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
        while(id > 0)
        {
            id = s.readShort();
        }
        //读取面板
        int count = s.readInt();
        //System.out.println("==count=="+count);
        for(int i = 0;i < count;i++)
        {
            EPanel panel = newPanel();
            panel.readObject(s);
        }
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
    }
    /**
     * 得到查找文本列表
     * @param list TList
     * @param endText EText
     */
    public void getFindTextList(TList list,EText endText)
    {
        EPanel next = get(0);
        next.getFindTextList(list,endText);
    }
    /**
     * 得到下一个页面侧查找列表
     * @param list TList
     * @param endText EText
     */
    public void getFindNextTextList(TList list,EText endText)
    {
        EPage next = getNextPage();
        while(next != null && next.size() == 0)
            next = getNextPage(next);
        if(next == null)
            return;
        next.getFindTextList(list, endText);
    }
    /**
     * 打印
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            //绘制面板
            panel.print(g,panel.getX(),panel.getY());
        }
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
     * 鼠标移动
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex == -1)
        {
            getUI().setCursor(DCursor.TEXT_CURSOR);
            return true;
        }
        EPanel panel = get(panelIndex);
        int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
        if(panel.onMouseMoved(x,y))
            return true;
        getUI().setCursor(DCursor.TEXT_CURSOR);
        return true;
    }
    /**
     * 得到页面对象
     * @return EPage
     */
    public EPage getPage()
    {
        return null;
    }
    /**
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DPoint point = getUI().getScreenPoint();
        point.setX(point.getX() + getViewManager().getViewX() + 1);
        point.setY(point.getY() + getViewManager().getViewY(findIndex()) + 1);
        return point;
    }
    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        //System.out.println("EPage action=="+action.getType());
        for(int i = 0;i < size();i++)
            get(i).resetData(action);
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
            next = getNextPanel(next);
        }
        EPage page = getNextPage();
        while(page != null)
        {
            if(page.setFocus())
                return true;
            page = page.getNextPage();
        }
        return false;
    }
    /**
     * 焦点向左移动
     * @param panel EPanel
     * @return boolean
     */
    public boolean onFocusToLeft(EPanel panel)
    {
        EPanel prev = getPreviousPanel(panel);
        while(prev != null)
        {
            if (prev.setFocusLast())
                return true;
            prev = getNextPanel(prev);
        }
        EPage page = getPreviousPage();
        while(page != null)
        {
            if(page.setFocusLast())
                return true;
            page = page.getPreviousPage();
        }
        return false;
    }
    /**
     * 得到焦点
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
        return false;
    }
    /**
     * 得到焦点
     * @return boolean
     */
    public boolean setFocusLast()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EPanel panel = get(i);
            if(panel.setFocusLast())
                return true;
        }
        return false;
    }
    /**
     * 设置上一个组件为焦点
     * @return boolean
     */
    public boolean setPreviousFocus()
    {
        int index = findIndex() - 1;
        if(index >= 0)
            return getPageManager().get(index).setFocusLast();
        return false;
    }
    /**
     * 设置下一个组件为焦点
     * @return boolean
     */
    public boolean setNextFocus()
    {
        int index = findIndex() + 1;
        if(index < getPageManager().size())
            return getPageManager().get(index).setFocus();
        return false;
    }
    public String toString()
    {
        return "<EPage size=" + size() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",isModify=" + isModify() +
                ",index=" + getIndexString() + ">";
    }
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        for(int i = 0;i < size();i++)
        {
            EComponent com = get(i).findObject(name,type);
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
        for(int i = 0;i < size();i++)
            get(i).filterObject(list,name,type);
    }
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        for(int i = 0;i < size();i++)
        {
            EComponent com = get(i).findGroup(group);
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
        for(int i = 0;i < size();i++)
        {
            String value = get(i).findGroupValue(group);
            if(value != null)
                return value;
        }
        return null;
    }
    /**
     * 整理
     */
    public void arrangement()
    {
        int index = 0;
        while(index < size())
        {
            get(index).arrangement();
            index ++;
        }
    }
    /**
     * 整理行号
     * @param id RowID
     */
    public void resetRowID(RowID id)
    {
        setRowStartID(id.getStart());
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            panel.resetRowID(id);
        }
        setRowEndID(id.getEnd());
    }
    /**
     * 设置开始行号
     * @param rowStartID int
     */
    public void setRowStartID(int rowStartID)
    {
        this.rowStartID = rowStartID;
    }
    /**
     * 得到开始行号
     * @return int
     */
    public int getRowStartID()
    {
        return rowStartID;
    }
    /**
     * 设置结束行号
     * @param rowEndID int
     */
    public void setRowEndID(int rowEndID)
    {
        this.rowEndID = rowEndID;
    }
    /**
     * 得到结束行号
     * @return int
     */
    public int getRowEndID()
    {
        return rowEndID;
    }
}
