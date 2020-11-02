package com.dongyang.tui.text;

import com.dongyang.util.StringTool;
import com.dongyang.util.TList;
import com.dongyang.tui.DText;
import java.awt.Graphics;
import com.dongyang.tui.text.div.MVList;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.awt.Color;
import com.dongyang.tui.DCursor;
import com.dongyang.tui.text.image.GBlock;
import com.dongyang.tui.text.image.BlockPI;
import com.dongyang.ui.TPopupMenu;
import com.dongyang.tui.DMessageIO;
import com.dongyang.util.RunClass;
import com.dongyang.tui.text.image.GLine;
import java.util.List;
import com.dongyang.ui.TWindow;

public class EImage implements IBlock,EEvent,BlockPI,DMessageIO
{
    private static TList copyComponents = new TList();
    private TWindow propertyDialog;
    /**
     * 父类
     */
    private EComponent parent;
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 选中列表
     */
    private TList selectedComponents;
    /**
     * 显示
     */
    private boolean visible = true;
    /**
     * 修改状态
     */
    private boolean modify;
    /**
     * 位置
     * 0 中间
     * 1 行首
     * 2 行尾
     */
    private int position;
    /**
     * 名称
     */
    private String name = "";
    /**
     * 图层管理器
     */
    private MVList mvList;
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
    private int width = 100;
    /**
     * 高度
     */
    private int height = 100;
    /**
     * 移动边框
     */
    private int draggedIndex;
    private int oldMouseX,oldMouseY;
    private int omx,omy,omw,omh;
    private boolean showSelectBlock;
    /**
     * 拖动快
     */
    private GBlock draggedBlock;
    /**
     * 锁定编辑
     */
    private boolean isLockEdit;
    /**
     * 显示边框
     */
    private boolean borderVisible = true;
    /**
     * 性别显示
     */
    private int sexControl = 0;
    /**
     * 构造器
     */
    public EImage()
    {
        components = new TList();
        selectedComponents = new TList();
    }
    /**
     * 构造器
     * @param panel EPanel
     */
    public EImage(EPanel panel)
    {
        this();
        setParent(panel);
    }
    /**
     * 初始化新Pic
     */
    public void initNew()
    {

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
     * 得到面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        EComponent com = getParent();
        if(com == null || !(com instanceof EPanel))
            return null;
        return (EPanel)com;
    }
    /**
     * 得到页面
     * @return EPage
     */
    public EPage getPage()
    {
        EPanel panel = getPanel();
        if(panel == null)
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
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
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
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到事件管理器
     * @return MEvent
     */
    public MEvent getEventManager()
    {
        return getPM().getEventManager();
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
        /*for(int i = 0;i < size();i++)
        {
            ETR tr = get(i);
            if(tr == null)
                continue;
            if(tr.isModify())
                return;
        }*/
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
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action)
    {
    }
    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType()
    {
        return IMAGE_TYPE;
    }
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        if(name == null || name.length() == 0)
        {
            if(getObjectType() == type)
                return this;
            return null;
        }
        if(name.equals(getName()) && getObjectType() == type)
            return this;
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

    }
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        return null;
    }
    /**
     * 得到组值
     * @param group String
     * @return String
     */
    public String findGroupValue(String group)
    {
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
     * 整理
     */
    public void arrangement()
    {

    }
    /**
     * 克隆
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        EImage image = new EImage(panel);
        image.setVisible(isVisible());
        image.setName(getName());
        image.setWidth(getWidth());
        image.setHeight(getHeight());
        image.setLockEdit(isLockEdit());
        for(int i = 0;i < size();i++)
            image.add(get(i).clone(image));
        return image;
    }
    /**
     * 得到坐标位置
     * @return String
     */
    public String getIndexString()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return "Parent:Null";
        return panel.getIndexString() + ",Image:" + findIndex();
    }
    public String toString()
    {
        return "<EImage " +
                "visible=" + isVisible() +
                ",index=" + getIndexString() +
                ">";
    }
    /**
     * 调整尺寸
     */
    public void reset()
    {
        if(!isModify())
            return;
        reset(0);
        setModify(false);
    }
    /**
     * 调整尺寸
     * @param index int
     */
    public void reset(int index)
    {

    }
    /**
     * 调试对象组成
     * @param i int
     */
    public void debugShow(int i)
    {
        System.out.println(StringTool.fill(" ",i) + this);
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
        getPanel().getPath(list);
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
     * 查找组件位置
     * @param image EImage
     * @return int
     */
    public int findIndex(EImage image)
    {
        EPanel panel = getPanel();
        if(panel == null)
            return -1;
        return panel.indexOf(image);
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
        getPanel().getPathIndex(list);
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
    public void setBounds(int x,int y,int width,int height)
    {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * 设置显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        if(this.visible == visible)
            return;
        this.visible = visible;
        setModify(true);
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
     * 得到图层管理器
     * @return MV
     */
    public MVList getMVList()
    {
        return mvList;
    }
    /**
     * 绘制背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void paintBackground(Graphics g,int x,int y,int pageIndex)
    {
        //getMVList().paintBackground(g,x,y,pageIndex);
    }
    /**
     * 绘制前景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void paintForeground(Graphics g,int x,int y,int pageIndex)
    {
        if(showSelectBlock)
        {
            paintSelectedBlock(g,x,y);
        }
        //getMVList().paintForeground(g,x,y,pageIndex);
    }
    public void paintSelectedBlock(Graphics g,int x,int y)
    {
        int x0 = (int)(omx * getZoom() / 75.0) + x;
        int y0 = (int)(omy * getZoom() / 75.0) + y;
        int w0 = (int)(omw * getZoom() / 75.0);
        int h0 = (int)(omh * getZoom() / 75.0);
        if(w0 < 0)
        {
            x0 += w0;
            w0 *= -1;
        }
        if(h0 < 0)
        {
            y0 += h0;
            h0 *= -1;
        }
        g.setColor(new Color(255,0,0));
        g.drawRect(x0,y0,w0,h0);
    }
    /**
     * 绘制背景(打印)
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int x,int y,int pageIndex)
    {
        //getMVList().printBackground(g,x,y,pageIndex);
    }
    /**
     * 绘制前景(打印)
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void printForeground(Graphics g,int x,int y,int pageIndex)
    {
        //getMVList().printForeground(g,x,y,pageIndex);
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
        if(isBorderVisible())
        {
            g.setColor(new Color(0, 0, 0));
            g.drawRect(x, y, width, height);
        }
        //绘制背景
        paintBackground(g,x,y,0);
        int count = size();
        for(int i = 0;i < count;i++)
        {
            GBlock block = get(i);
            int x1 = (int)(block.getX() * getZoom() / 75.0);
            int y1 = (int)(block.getY() * getZoom() / 75.0);
            int w1 = (int)(block.getWidth() * getZoom() / 75.0);
            int h1 = (int)(block.getHeight() * getZoom() / 75.0);
            //绘制
            block.paint(g,x + x1,y + y1,w1,h1);
        }
        paintForeground(g,x,y,0);
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
        if(isBorderVisible())
        {
            g.setColor(new Color(0, 0, 0));
            g.drawRect(x,y,getWidth(),getHeight());
        }
        //绘制背景
        printBackground(g,x,y,0);
        int count = size();
        for(int i = 0;i < count;i++)
        {
            GBlock block = get(i);
            int x1 = block.getX();
            int y1 = block.getY();
            int w1 = block.getWidth();
            int h1 = block.getHeight();
            //绘制
            block.print(g,x + x1,y + y1,w1,h1);
        }
        printForeground(g,x,y,0);
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
     * 5 EImage
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        //设置焦点
        setFocus();
        getEventManager().setDraggedImage(this);
        oldMouseX = getEventManager().getMouseX();
        oldMouseY = getEventManager().getMouseY();
        if(draggedIndex != 0)
        {
            switch(draggedIndex)
            {
            case 2:
                omy = getHeight();
                break;
            case 4:
                omx = getWidth();
                break;
            case 7:
                omx = getWidth();
                omy = getHeight();
                break;
            }
            return 5;
        }
        double zoom = 75.0 / getZoom();
        //拖动子组件
        if(getEventManager().isControlDown())
            draggedBlock = null;
        if(draggedBlock != null)
        {
            saveChildOldSize();
            int x = mouseX - (int)(draggedBlock.getX() * zoom);
            int y = mouseY - (int)(draggedBlock.getY() * zoom);
            draggedBlock.onMouseLeftPressed(x,y);
            return 5;
        }

        int x = (int)(mouseX * zoom);
        int y = (int)(mouseY * zoom);
        //创建组件
        if(createBlock(x,y))
            return 5;
        if(!isLockEdit())
        {
            //选中组件
            GBlock block = getBlock(x, y);
            //清除选中
            if (!getEventManager().isControlDown())
                clearSelected();
            if (block != null)
            {
                if (getEventManager().isControlDown())
                {
                    if (isSelectBlock(block))
                        removeSelected(block);
                    else
                        addSelected(block);
                } else
                    addSelected(block);
                update();
                return 5;
            }
            showSelectBlock = true;
        }
        omx = x;
        omy = y;
        omw = 0;
        omh = 0;
        return 5;
    }
    /**
     * 右键按下
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        if(!isLockEdit())
        {
            double zoom = 75.0 / getZoom();
            int x = (int) (mouseX * zoom);
            int y = (int) (mouseY * zoom);
            //选中组件
            GBlock block = getBlock(x, y);
            //清除选中
            if (!getEventManager().isControlDown())
                clearSelected();
            if (block != null)
            {
                if (!isSelectBlock(block))
                    addSelected(block);
                update();
                int x1 = mouseX - (int) (block.getX() * getZoom() / 75.0 + 0.5);
                int y1 = mouseY - (int) (block.getY() * getZoom() / 75.0 + 0.5);
                block.onMouseRightPressed(x1, y1);
                return;
            }
        }
        String lock = isLockEdit()?"允许编辑,onUnLockEdit;|;属性,propertyDialog":"粘贴,onPaste;|;锁定编辑,onLockEdit;|;属性,propertyDialog";
        String syntax = lock;
        popupMenu(syntax);
    }
    /**
     * 允许编辑
     */
    public void onUnLockEdit()
    {
        setLockEdit(false);
    }
    /**
     * 锁定编辑
     */
    public void onLockEdit()
    {
        setLockEdit(true);
        clearSelected();
    }
    /**
     * 粘贴
     */
    public void onPaste()
    {
        exeAction(new EAction(EAction.PASTE));
    }
    /**
     * 弹出菜单
     * @param syntax String
     */
    public void popupMenu(String syntax)
    {
        if(syntax == null || syntax.length() == 0)
            return;
        TPopupMenu popup = TPopupMenu.createPopupMenu(syntax);
        popup.setMessageIO(this);
        //popup.changeLanguage(getLanguage());
        popup.show(getUI().getTCBase(),getUI().getMouseX(),getUI().getMouseY());
    }
    /**
     * 更新
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * 左键抬起
     */
    public void onMouseLeftReleased()
    {
        if(showSelectBlock)
        {
            checkBlockSelected();
            showSelectBlock = false;
        }
        update();
    }
    public void checkBlockSelected()
    {
        int x0 = omx;
        int y0 = omy;
        int w0 = omw;
        int h0 = omh;
        if(w0 < 0)
        {
            x0 += w0;
            w0 *= -1;
        }
        if(h0 < 0)
        {
            y0 += h0;
            h0 *= -1;
        }
        for(int i = size() - 1;i >= 0;i--)
        {
            GBlock block = get(i);
            if(block.getX() < x0 || block.getWidth() + block.getX() > x0 + w0)
                continue;
            if(block.getY() < y0 || block.getHeight() + block.getY() > y0 + h0)
                continue;
            addSelected(block);
        }
    }
    /**
     * 查找选中的组件
     * @param x int
     * @param y int
     * @return GBlock
     */
    public GBlock getBlock(int x,int y)
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            GBlock block = get(i);
            if(block.isSelectCheck(x,y))
                return block;
        }
        return null;
    }
    /**
     * 查找选中的组件
     * @param x int
     * @param y int
     * @return GBlock
     */
    public GBlock getBlockEnabled(int x,int y)
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            GBlock block = get(i);
            if(!block.isEnabled())
                continue;
            if(block.isSelectCheck(x,y))
                return block;
        }
        return null;
    }
    /**
     * 创建块
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean createBlock(int x,int y)
    {
        switch(getFocusManager().getInsertImageAction())
        {
        case 1:
            GBlock block = new GBlock();
            block.setParent(this);
            block.setX(x);
            block.setY(y);
            block.setWidth(30);
            block.setHeight(30);
            add(block);
            setSelected(block);
            update();
            getFocusManager().setInsertImageAction(0);
            return true;
        case 2:
            GLine line = new GLine();
            line.setParent(this);
            line.setX(x);
            line.setY(y);
            line.setWidth(30);
            line.setHeight(30);
            add(line);
            setSelected(line);
            update();
            getFocusManager().setInsertImageAction(0);
            return true;
        }
        return false;
    }
    private void moveSize(int x,int y)
    {
        switch(draggedIndex)
        {
        case 2:
            setHeight(omy + y);
            break;
        case 4:
            int w = omx + x;
            if(w < 1)
                w = 1;
            if(w > getPanel().getWidth() - getX())
                w = getPanel().getWidth() - getX();
            setWidth(w);
            break;
        case 7:
            w = omx + x;
            if(w < 1)
                w = 1;
            if(w > getPanel().getWidth() - getX())
                w = getPanel().getWidth() - getX();
            setWidth(w);
            setHeight(omy + y);
            break;
        }
    }
    /**
     * 鼠标拖动
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseDragged(int mouseX,int mouseY)
    {
        double zoom = 75.0 / getZoom();
        int x = (int)((mouseX - oldMouseX) * zoom);
        int y = (int)((mouseY - oldMouseY) * zoom);
        moveSize(x,y);
        //拖动子组件
        if(draggedBlock != null)
        {
            draggedBlock.onMouseDragged(x,y);
        }
        setModify(true);
        omw = x;
        omh = y;
        update();
    }
    /**
     * 随动比例
     * @param index int
     * @param x double
     * @param y double
     * @param block GBlock
     */
    public void onMouseDraggedOther(int index,double x,double y,GBlock block)
    {
        for(int i = 0;i < sizeSelected();i++)
        {
            GBlock b = getSelected(i);
            if(b == block)
                continue;
            b.onMouseDraggedOther(index,x,y);
        }
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
     * 得到鼠标所在组件
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInComponentIndexXY(int mouseX,int mouseY)
    {
        int len = 6;
        int el = 4;
        double zoom = getZoom() / 75.0;
        int width = (int)(getWidth() * zoom);
        int height = (int)(getHeight() * zoom);
        //if(mouseX < len && mouseY < len)
        //    return 5;
        //if(mouseX > width - len && mouseY < len)
        //    return 6;
        if(mouseX > width - len && mouseY > height - len)
            return 7;
        //if(mouseX < len && mouseY > width - len)
        //    return 8;
        //if(mouseY < el)
        //    return 1;
        if(mouseY > height - el)
            return 2;
        //if(mouseX < el)
        //    return 3;
        if(mouseX > width - el)
            return 4;
        return 0;
    }
    /**
     * 鼠标移动
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        draggedBlock = null;
        if(!isLockEdit())
        {
            draggedIndex = getMouseInComponentIndexXY(mouseX, mouseY);
            if (draggedIndex > 0)
            {
                setCursor(draggedIndex);
                return true;
            }
            double zoom = getZoom() / 75.0;
            for (int i = 0; i < sizeSelected(); i++)
            {
                GBlock block = getSelected(i);
                int x = (int) (block.getX() * zoom);
                int y = (int) (block.getY() * zoom);
                if (block.onMouseMoved(mouseX - x, mouseY - y))
                {
                    draggedBlock = block;
                    return true;
                }
            }
            getUI().setCursor(DCursor.DEFAULT_CURSOR);
            return true;
        }
        return true;
    }
    public void setCursor(int index)
    {
        switch(index)
        {
        //case 1:
        case 2:
            getUI().setCursor(DCursor.N_RESIZE_CURSOR);
            return;
        //case 3:
        case 4:
            getUI().setCursor(DCursor.E_RESIZE_CURSOR);
            return;
        //case 5:
        case 7:
            getUI().setCursor(DCursor.NW_RESIZE_CURSOR);
            return;
        //case 6:
        //case 8:
        //    getUI().setCursor(DCursor.NE_RESIZE_CURSOR);
        //    return;
        }
    }
    /**
     * 设置焦点
     */
    public void setFocus()
    {
        setFocus(this);
    }
    /**
     * 设置焦点
     * @param com EImage
     */
    public void setFocus(EImage com)
    {
        getFocusManager().setFocusImage(com);
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        if(!isLockEdit())
            return true;
        double zoom = 75.0 / getZoom();
        int x = (int)(mouseX * zoom);
        int y = (int)(mouseY * zoom);
        //选中组件
        GBlock block = getBlockEnabled(x, y);
        if(block != null)
            block.onDoubleClickedS(mouseX - x, mouseY - y);
        return true;
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return;
        int index = panel.indexOf(this);
        if(index > 0)
            panel.get(index - 1).setModify(true);
        if(panel.size() == 0)
        {
            EPanel p = panel.getNextPanel();
            if(p != null)
                p.setModify(true);
        }
        panel.remove(this);
        panel.setModify(true);
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
        //保存内部对象
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            GBlock block = get(i);
            s.writeInt(block.getTypeID());
            block.writeObject(s);
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
        //读取行
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            switch(s.readInt())
            {
            case 0:
                GBlock block = new GBlock();
                block.setParent(this);
                block.readObject(s);
                add(block);
                break;
            case 1:
                GLine line = new GLine();
                line.setParent(this);
                line.readObject(s);
                add(line);
                break;
            }
        }
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeBoolean(1,isVisible(),true);
        s.writeString(2,getName(),"");
        s.writeInt(3,getWidth(),0);
        s.writeInt(4,getHeight(),0);
        s.writeBoolean(5,isLockEdit(),false);
        s.writeBoolean(6,isBorderVisible(),true);
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
        switch (id)
        {
        case 1:
            setVisible(s.readBoolean());
            return true;
        case 2:
            setName(s.readString());
            return true;
        case 3:
            setWidth(s.readInt());
            return true;
        case 4:
            setHeight(s.readInt());
            return true;
        case 5:
            setLockEdit(s.readBoolean());
            return true;
        case 6:
            setBorderVisible(s.readBoolean());
            return true;
        }
        return false;
    }
    /**
     * 增加成员
     * @param com GBlock
     */
    public void add(GBlock com)
    {
        if(com == null)
            return;
        components.add(com);
        //com.setParent(this);
    }
    /**
     * 增加成员
     * @param index int
     * @param com GBlock
     */
    public void add(int index,GBlock com)
    {
        if(com == null)
            return;
        components.add(index,com);
        //com.setParent(this);
    }
    /**
     * 查找位置
     * @param com GBlock
     * @return int
     */
    public int indexOf(GBlock com)
    {
        if(com == null)
            return -1;
        return components.indexOf(com);
    }
    /**
     * 删除成员
     * @param com GBlock
     */
    public void remove(GBlock com)
    {
        components.remove(com);
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
     * @return GBlock
     */
    public GBlock get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (GBlock)components.get(index);
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
     * 得到选中成员个数
     * @return int
     */
    public int sizeSelected()
    {
        return selectedComponents.size();
    }
    /**
     * 增加选中成用
     * @param block GBlock
     */
    public void addSelected(GBlock block)
    {
        if(block == null)
            return;
        selectedComponents.add(block);
        block.setEditSelect(true);
    }
    /**
     * 选中块
     * @param block GBlock
     */
    public void setSelected(GBlock block)
    {
        clearSelected();
        addSelected(block);
    }
    /**
     * 组件是否选中
     * @param block GBlock
     * @return boolean
     */
    public boolean isSelectBlock(GBlock block)
    {
        for(int i = 0;i < sizeSelected();i++)
            if(getSelected(i) == block)
                return true;
        return false;
    }
    /**
     * 取消选中
     * @param block GBlock
     */
    public void removeSelected(GBlock block)
    {
        selectedComponents.remove(block);
        block.setEditSelect(false);
    }
    /**
     * 取消选中
     * @param index int
     */
    public void removeSelected(int index)
    {
        GBlock block = getSelected(index);
        if(block == null)
            return;
        block.setEditSelect(false);
        selectedComponents.remove(index);
    }
    /**
     * 得到选中成员
     * @param index int
     * @return GBlock
     */
    public GBlock getSelected(int index)
    {
        if(index < 0 || index >= sizeSelected())
            return null;
        return (GBlock)selectedComponents.get(index);
    }
    /**
     * 清除选择
     */
    public void clearSelected()
    {
        for(int i = 0;i < sizeSelected();i++)
            getSelected(i).setEditSelect(false);
        selectedComponents.removeAll();
    }
    /**
     * 删除选中
     */
    public void deleteSelected()
    {
        for(int i = 0;i < sizeSelected();i++)
            remove(getSelected(i));
        selectedComponents.removeAll();
    }
    public void saveChildOldSize()
    {
        for(int i = 0;i < sizeSelected();i++)
            getSelected(i).saveOldSize();
    }
    public void onMoveKey(int index)
    {
        for(int i = 0;i < sizeSelected();i++)
        {
            GBlock b = getSelected(i);
            switch(index)
            {
            case 1:
                b.setY(b.getY() - 1);
                break;
            case 2:
                b.setY(b.getY() + 1);
                break;
            case 3:
                b.setX(b.getX() - 1);
                break;
            case 4:
                b.setX(b.getX() + 1);
                break;
            case 5:
                b.setHeight(b.getHeight() - 1);
                break;
            case 6:
                b.setHeight(b.getHeight() + 1);
                break;
            case 7:
                b.setWidth(b.getWidth() - 1);
                break;
            case 8:
                b.setWidth(b.getWidth() + 1);
                break;
            }
        }
        update();
    }
    /**
     * 调整块尺寸
     * @param index int
     */
    public void onSizeBlockMenu(int index)
    {
        if(sizeSelected() < 2)
            return;
        GBlock block = getSelected(0);
        for(int i = 1;i < sizeSelected();i++)
        {
            GBlock b = getSelected(i);
            switch(index)
            {
            case 1:
                b.setY(block.getY());
                break;
            case 2:
                b.setY(block.getY() + block.getHeight() - b.getHeight());
                break;
            case 3:
                b.setX(block.getX());
                break;
            case 4:
                b.setX(block.getX() + block.getWidth() - b.getWidth());
                break;
            case 5:
                b.setWidth(block.getWidth());
                break;
            case 6:
                b.setHeight(block.getHeight());
                break;
            }
        }
        update();
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        switch(action.getType())
        {
        case EAction.COPY:
            copyComponents.removeAll();
            for(int i = 0;i < sizeSelected();i++)
                copyComponents.add(getSelected(i).copyObject());
            break;
        case EAction.PASTE:
            clearSelected();
            for(int i = 0;i < copyComponents.size();i++)
            {
                GBlock block = ((GBlock)copyComponents.get(i)).copyObject();
                block.setParent(this);
                add(block);
                addSelected(block);
            }
            update();
            break;
        case EAction.CUT:
            copyComponents.removeAll();
            for(int i = 0;i < sizeSelected();i++)
                copyComponents.add(getSelected(i).copyObject());
            deleteSelected();
            update();
            break;
        case EAction.DELETE:
            deleteSelected();
            update();
            break;
        }
    }
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message, "=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * 处理消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm)
    {
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        return null;
    }
    /**
     * 设置锁定编辑
     * @param isLockEdit boolean
     */
    public void setLockEdit(boolean isLockEdit)
    {
        this.isLockEdit = isLockEdit;
    }
    /**
     * 是否锁定编辑
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return isLockEdit;
    }
    /**
     * 属性对话框
     */
    public void propertyDialog()
    {
        openProperty("%ROOT%\\config\\database\\BlockImageEdit.x",propertyDialog);
    }
    /**
     * 打开属性对话框
     * @param dialogName String
     * @param window TWindow
     */
    public void openProperty(String dialogName,TWindow window)
    {
        if(dialogName == null || dialogName.length() == 0)
            return;
        if(window == null || window.isClose())
        {
            window = (TWindow) getUI().openWindow(dialogName, this, true);
            window.setVisible(true);
        }
        else
            window.setVisible(true);
    }
    /**
     * 设置是否显示边框
     * @param borderVisible boolean
     */
    public void setBorderVisible(boolean borderVisible)
    {
        this.borderVisible = borderVisible;
    }
    /**
     * 是否显示边框
     * @return boolean
     */
    public boolean isBorderVisible()
    {
        return borderVisible;
    }
    /**
     * 设置性别限制
     * @param sexControl int
     */
    public void setSexControl(int sexControl)
    {
        this.sexControl = sexControl;
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
