package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 面板基础类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.7
 * @version 1.0
 */
public abstract class EPanelBase implements EComponent
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
     * 字符数据
     */
    private DString dString;
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
     * 下一页连接面板
     */
    private EPanel nextPageLinkPanel;
    /**
     * 上一页连接面板
     */
    private EPanel previousPageLinkPanel;
    /**
     * 上一个面板索引号码(保存专用)
     */
    private int previousPanelIndex;
    /**
     * 上一个连接面板
     */
    private EPanel previousLinkPanel;
    /**
     * 下一个连接面板
     */
    private EPanel nextLinkPanel;
    /**
     * 对其位置
     * 0 left
     * 1 center
     * 2 right
     */
    private int alignment = 0;
    /**
     * 段前
     */
    private int paragraphForward = 0;
    /**
     * 段后
     */
    private int paragraphAfter = 0;
    /**
     * 间距
     */
    private int spaceBetween = 0;
    /**
     * 最大高度
     */
    private int maxHeight = -1;
    /**
     * 类型
     * 0 文本
     * 1 表格
     */
    private int type;
    /**
     * 左侧缩进
     */
    private int retractLeft;
    /**
     * 右侧缩进
     */
    private int retractRight;
    /**
     * 缩进类型
     * 0 无
     * 1 首行缩进
     * 2 悬挂缩进
     */
    private int retractType;
    /**
     * 缩进宽度
     */
    private int retractWidth;
    /**
     * 只允许元素编辑
     */
    private boolean elementEdit;
    /**
     * 性别显示
     */
    private int sexControl = 0;

    /**
     * 构造器
     */
    public EPanelBase()
    {
        components = new TList();
    }
    /**
     * 增加成员
     * @param block IBlock
     */
    public void add(IBlock block)
    {
        if(block == null)
            return;
        components.add(block);
        block.setParent(this);
    }
    /**
     * 增加成员
     * @param index int
     * @param block IBlock
     */
    public void add(int index,IBlock block)
    {
        if(block == null)
            return;
        components.add(index,block);
        block.setParent(this);
    }
    /**
     * 查找位置
     * @param block IBlock
     * @return int
     */
    public int indexOf(IBlock block)
    {
        if(block == null)
            return -1;
        return components.indexOf(block);
    }
    /**
     * 删除成员
     * @param block IBlock
     */
    public void remove(IBlock block)
    {
        components.remove(block);
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
        for(int i = size() - 1;i >= 0;i--)
        {
            IBlock b = get(i);
            if(b instanceof EMacroroutine)
                ((EMacroroutine)b).free();
        }
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
     * @return EComponent
     */
    public IBlock get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (IBlock)components.get(index);
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
     * 得到父类TD
     * @return ETD
     */
    public ETD getParentTD()
    {
        return (ETD)parent;
    }
    /**
     * 得到父类页面
     * @return EPage
     */
    public EPage getParentPage()
    {
        return (EPage)parent;
    }
    /**
     * 设置字符数据
     * @param dString DString
     */
    public void setDString(DString dString)
    {
        this.dString = dString;
    }
    /**
     * 得到字符数据
     * @return DString
     */
    public DString getDString()
    {
        return dString;
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
        if(this.width == width)
            return;
        this.width = width;
        setModify(true);
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
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getPage().getPM();
    }
    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPage().getPageManager();
    }
    /**
     * 得到字符数据管理器
     * @return MString
     */
    public MString getStringManager()
    {
        return getPage().getStringManager();
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
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到面板控制类
     * @return MPanel
     */
    public MPanel getSavePanelManager()
    {
        return getPM().getFileManager().getPanelManager();
    }
    /**
     * 设置焦点
     * @param text EText
     */
    public void setFocus(EText text)
    {
        getFocusManager().setFocusText(text);
    }
    /**
     * 得到焦点
     * @return EComponent
     */
    public EComponent getFocus()
    {
        return getFocusManager().getFocus();
    }
    /**
     * 设置焦点位置
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * 得到焦点位置
     * @return int
     */
    public int getFocusIndex()
    {
        return getFocusManager().getFocusIndex();
    }
    /**
     * 设置上一页连接面板
     * @param panel EPanel
     */
    public void setPreviousPageLinkPanel(EPanel panel)
    {
        previousPageLinkPanel = panel;
    }
    /**
     * 得到上一页连接面板
     * @return EPanel
     */
    public EPanel getPreviousPageLinkPanel()
    {
        return previousPageLinkPanel;
    }
    /**
     * 设置下一页连接面板
     * @param panel EPanel
     */
    public void setNextPageLinkPanel(EPanel panel)
    {
        nextPageLinkPanel = panel;
    }
    /**
     * 得到下一页连接面板
     * @return EPanel
     */
    public EPanel getNextPageLinkPanel()
    {
        return nextPageLinkPanel;
    }
    /**
     * 存在下一个连接面板
     * @return boolean
     */
    public boolean hasNextPageLinkPanel()
    {
        return getNextPageLinkPanel() != null;
    }
    /**
     * 存在上一个连接面板
     * @return boolean
     */
    public boolean hasPreviousPageLinkPanel()
    {
        return getPreviousPageLinkPanel() != null;
    }
    /**
     * 设置上一个面板的索引号码(保存专用)
     * @param index int
     */
    public void setPreviousPanelIndex(int index)
    {
        previousPanelIndex = index;
    }
    /**
     * 得到上一个面板的索引号码(保存专用)
     * @return int
     */
    public int getPreviousPanelIndex()
    {
        return previousPanelIndex;
    }
    /**
     * 存在上一个连接面板
     * @return boolean
     */
    public boolean hasPreviousLinkPanel()
    {
        return getPreviousLinkPanel() != null;
    }
    /**
     * 设置上一个连接面板
     * @param previousLinkPanel EPanel
     */
    public void setPreviousLinkPanel(EPanel previousLinkPanel)
    {
        this.previousLinkPanel = previousLinkPanel;
    }
    /**
     * 得到上一个连接面板
     * @return EPanel
     */
    public EPanel getPreviousLinkPanel()
    {
        return previousLinkPanel;
    }
    /**
     * 存在下一个连接面板
     * @return boolean
     */
    public boolean hasNextLinkPanel()
    {
        return getNextLinkPanel() != null;
    }
    /**
     * 设置下一个连接面板
     * @param nextLinkPanel EPanel
     */
    public void setNextLinkPanel(EPanel nextLinkPanel)
    {
        this.nextLinkPanel = nextLinkPanel;
    }
    /**
     * 得到下一个连接面板
     * @return EPanel
     */
    public EPanel getNextLinkPanel()
    {
        return nextLinkPanel;
    }
    /**
     * 父类是页面
     * @return boolean
     */
    public boolean parentIsPage()
    {
        return getParent() instanceof EPage;
    }
    /**
     * 父类是表格
     * @return boolean
     */
    public boolean parentIsTD()
    {
        return getParent() instanceof ETD;
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
        if(this.alignment == alignment)
            return;
        this.alignment = alignment;
        if(getParent() instanceof ETD)
            ((ETD)getParent()).setAlignment(alignment);
        setModify(true);
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
     * 设置段前
     * @param paragraphForward int
     */
    public void setParagraphForward(int paragraphForward)
    {
        if(this.paragraphForward == paragraphForward)
            return;
        this.paragraphForward = paragraphForward;
        setModify(true);
    }
    /**
     * 得到段前
     * @return int
     */
    public int getParagraphForward()
    {
        return paragraphForward;
    }
    /**
     * 设置段后
     * @param paragraphAfter int
     */
    public void setParagraphAfter(int paragraphAfter)
    {
        if(this.paragraphAfter == paragraphAfter)
            return;
        this.paragraphAfter = paragraphAfter;
        setModify(true);
    }
    /**
     * 得到段后
     * @return int
     */
    public int getParagraphAfter()
    {
        return paragraphAfter;
    }
    /**
     * 设置间距
     * @param spaceBetween int
     */
    public void setSpaceBetween(int spaceBetween)
    {
        if(this.spaceBetween == spaceBetween)
            return;
        this.spaceBetween = spaceBetween;
        setModify(true);
    }
    /**
     * 得到间距
     * @return int
     */
    public int getSpaceBetween()
    {
        return spaceBetween;
    }
    /**
     * 得到页面
     * @return EPage
     */
    public EPage getPage()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPage)
            return (EPage)component;
        if(component instanceof EPanel)
            return ((EPanel) component).getPage();
        if(component instanceof ETD)
            return ((ETD)component).getPage();
        return null;
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
     * 设置类型
     * @param type int
     * 0 文本
     * 1 表格
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return int
     * 0 文本
     * 1 表格
     */
    public int getType()
    {
        return type;
    }
    /**
     * 设置左侧缩进
     * @param retractLeft int
     */
    public void setRetractLeft(int retractLeft)
    {
        this.retractLeft = retractLeft;
    }
    /**
     * 得到左侧缩进
     * @return int
     */
    public int getRetractLeft()
    {
        return retractLeft;
    }
    /**
     * 设置右侧缩进
     * @param retractRight int
     */
    public void setRetractRight(int retractRight)
    {
        this.retractRight = retractRight;
    }
    /**
     * 得到右侧缩进
     * @return int
     */
    public int getRetractRight()
    {
        return retractRight;
    }
    /**
     * 设置缩进类型
     * @param retractType int
     * 0 无
     * 1 首行缩进
     * 2 悬挂缩进
     */
    public void setRetractType(int retractType)
    {
        this.retractType = retractType;
    }
    /**
     * 得到缩进类型
     * @return int
     * 0 无
     * 1 首行缩进
     * 2 悬挂缩进
     */
    public int getRetractType()
    {
        return retractType;
    }
    /**
     * 设置缩进宽度
     * @param retractWidth int
     */
    public void setRetractWidth(int retractWidth)
    {
        this.retractWidth = retractWidth;
    }
    /**
     * 得到缩进宽度
     * @return int
     */
    public int getRetractWidth()
    {
        return retractWidth;
    }
    /**
     * 设置只允许元素编辑
     * @param elementEdit boolean
     */
    public void setElementEdit(boolean elementEdit)
    {
        this.elementEdit = elementEdit;
    }
    /**
     * 得到只允许元素编辑
     * @return boolean
     */
    public boolean isElementEdit()
    {
        return elementEdit;
    }
    /**
     * 设置性别限制
     * @param sexControl int
     */
    public void setSexControl(int sexControl)
    {
        if(this.sexControl == sexControl)
            return;
        this.sexControl = sexControl;
        setModify(true);
    }
    /**
     * 得到性别限制
     * @return int
     */
    public int getSexControl()
    {
        return sexControl;
    }
}
