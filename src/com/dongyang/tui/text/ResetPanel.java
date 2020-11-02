package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;
import com.dongyang.tui.DInsets;

/**
 *
 * <p>Title: 调成面板处理对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.12
 * @version 1.0
 */
public class ResetPanel
{
    /**
     * 父类页面
     */
    private EPage page;
    /**
     * 父类模块
     */
    private ETD td;
    /**
     * 类型
     * 1 Page
     * 2 TD
     */
    private int type;
    /**
     * 处理句柄
     */
    private int index;
    /**
     * 尺寸范围
     */
    private DRectangle rectangle;
    /**
     * 启动开关
     */
    private boolean doing;
    /**
     * 状态
     */
    private int state;
    /**
     * 当前面板
     */
    private EPanel panel;
    /**
     * 纵坐标位置
     */
    private int y0;
    /**
     * 构造器
     * @param page EPage
     */
    public ResetPanel(EPage page)
    {
        //设置页面
        setPage(page);
        //设置类型
        setType(1);
    }

    /**
     * 构造器
     * @param td ETD
     */
    public ResetPanel(ETD td)
    {
        //设置TD
        setTD(td);
        //设置类型
        setType(2);
    }

    /**
     * 设置页面
     * @param page EPage
     */
    public void setPage(EPage page)
    {
        this.page = page;
    }

    /**
     * 得到页面
     * @return EPage
     */
    public EPage getPage()
    {
        return page;
    }

    /**
     * 设置TD
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }

    /**
     * 得到TD
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
    }

    /**
     * 设置类型
     * 1 Page
     * 2 TD
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * 得到类型
     * 1 Page
     * 2 TD
     * @return int
     */
    public int getType()
    {
        return type;
    }

    /**
     * 设置句柄
     * @param index int
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * 得到句柄
     * @return int
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * 清零
     */
    public void clear()
    {
        //句柄归零
        setIndex(0);
        //状态归零
        setState(0);
    }

    /**
     * 设置尺寸范围
     * @param rectangle DRectangle
     */
    public void setRectangle(DRectangle rectangle)
    {
        this.rectangle = rectangle;
    }

    /**
     * 设置尺寸范围
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setRectangle(int x, int y, int width, int height)
    {
        setRectangle(new DRectangle(x, y, width, height));
    }

    /**
     * 得到尺寸范围
     * @return DRectangle
     */
    public DRectangle getRectangle()
    {
        return rectangle;
    }

    /**
     * 启动
     */
    public void start()
    {
        doing = true;
    }

    /**
     * 停止
     */
    public void stop()
    {
        doing = false;
    }

    /**
     * 是否运行
     * @return boolean
     */
    public boolean isDoing()
    {
        return doing;
    }

    /**
     * 设置状态
     * @param state int
     */
    public void setState(int state)
    {
        this.state = state;
    }

    /**
     * 得到状态
     * @return int
     */
    public int getState()
    {
        return state;
    }

    /**
     * 设置当前面板
     * @param panel EPanel
     */
    public void setPanel(EPanel panel)
    {
        this.panel = panel;
    }

    /**
     * 得到当前面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return panel;
    }
    /**
     * 得到页面风格
     * @return int
     * 0 普通
     * 1 页面
     */
    public int getViewStyle()
    {
        if(getType() == 1)
            return getPage().getViewManager().getViewStyle();
        return getTD().getViewManager().getViewStyle();
    }
    /**
     * 调整尺寸
     */
    public void reset()
    {
        start();
        //纵坐标位置归零
        y0 = getRectangle().getY();
        //分类扫描
        switch (getType())
        {
        case 1: //Page
            resetPage();
        case 2: //TD
            resetTD();
        }
    }

    /**
     * 页面调整
     */
    private void resetPage()
    {
        //扫描
        while (isDoing())
        {
            switch (getState())
            {
            case 0: //向下扫描页面
                resetPage_0();
                break;
            case 1: //将下页的面板向上提
                resetPage_1();
                break;
            }
        }
        if(getViewStyle() == 0)
        {
            DInsets insets = getPage().getInsets();
            getPage().setWidth(insets.left + getMaxWidth() + insets.right);
            getPage().setHeight(insets.top + getMaxHeight() + insets.bottom);
        }
    }
    public int getMaxHeight()
    {
        EPanel panel = getPage().get(getPage().size() - 1);
        if(panel == null)
            return 0;
        return panel.getY() + panel.getHeight();
    }
    public int getMaxWidth()
    {
        int width = 0;
        for(int i = 0;i < getPage().size();i++)
        {
            EPanel p = getPage().get(i);
            if(width < p.getWidth())
                width = p.getWidth();
        }
        return width;
    }
    /**
     * 向下扫描页面
     */
    private void resetPage_0()
    {
        if (index >= getPage().size())
        {
            state = 1;
            return;
        }
        //设置当前面板
        setPanel(getPage().get(index));
        //检查Panel的合法性
        checkPanel();
        switch(getPanel().getPM().getSexControl())
        {
        case 0:
            break;
        case 1:
            if(getPanel().getSexControl() == 2)
            {
                getPanel().setModify(true);
                index ++;
                return;
            }
            break;
        case 2:
            if(getPanel().getSexControl() == 1)
            {
                getPanel().setModify(true);
                index ++;
                return;
            }
        }
        //是否跳过
        if (isContinuePanel())
        {
            y0 += getPanel().getHeight();
            index++;
            return;
        }
        getPanel().setX(getRectangle().getX());
        getPanel().setY(y0);
        getPanel().setWidth(getRectangle().getWidth());
        getPanel().setMaxHeight(getRectangle().getHeight() - y0 +
                                getRectangle().getY());
        getPanel().reset();
        if(getViewStyle() == 0)
        {
            y0 += getPanel().getHeight();
            index++;
            return;
        }
        //被删除
        if(getPanel().getMaxHeight() == -2)
            return;
        //放不下
        if (getPanel().getMaxHeight() == -1)
        {
            if (index > 0)
                //拷贝到下页
                copyToNextPage();
            stop();
            return;
        }
        if (getPanel().hasNextPageLinkPanel())
        {
            //如果面板中间遗留翻页垃圾，传递下页
            if(index < getPage().size() - 1)
            {
                int t = getPage().findIndex() + 1;
                if (t == getPage().getPageManager().size())
                {
                    EPage page = new EPage(getPage().getPageManager());
                    getPage().getPageManager().add(page);
                }
                EPage page = getPage().getPageManager().get(t);
                for (int i = getPage().size() - 1; i >= index + 1; i--)
                {
                    EPanel panel = getPage().get(i);
                    getPage().remove(i);
                    page.add(0, panel);
                    page.setModify(true);
                }
            }
            stop();
            return;
        }
        y0 += getPanel().getHeight();
        index++;
    }
    /**
     * 检查Panel的合法性
     */
    private void checkPanel()
    {
        //是否存在Table组件
        int index = getPanel().findTableIndex();
        if(index > 0)
            getPanel().setType(1);
        //不是表格不进行处理
        if(getPanel().getType() != 1)
            return;
        //如果只存在一个Table
        if(getPanel().size() == 1)
            return;
        //删除后面
        for(int i = getPanel().size() - 1;i > index;i--)
        {
            IBlock block = getPanel().get(i);
            if(block instanceof EText)
                ((EText)block).removeThis();
        }
        //删除前面
        for(int i = index - 1;i >= 0;i--)
        {
            IBlock block = getPanel().get(i);
            if(block instanceof EText)
                ((EText)block).removeThis();
        }
        getPanel().getDString().removeAll();
    }
    /**
     * 拷贝到下页
     */
    private void copyToNextPage()
    {
        EPage page = getPage().getNextPage();
        if (page == null)
            page = getPage().getPageManager().newPage();
        for (int i = getPage().size() - 1; i >= index; i--)
        {
            EPanel panel = getPage().get(i);
            getPage().remove(i);
            page.add(0, panel);
            //连接下一个Panel(补充作用xxx)
            linkNextPanal(panel,1);
        }
        page.setModify(true);
    }

    /**
     * 连接下一个Panel
     * @param panel EPanel
     * @param type int
     * 1 page
     * 2 td
     */
    public void linkNextPanal(EPanel panel,int type)
    {
        EPanel next = type == 1?panel.getNextPageLinkPanel():panel.getNextLinkPanel();
        if (next == null)
            return;
        while (next.size() > 0)
        {
            IBlock com = next.get(0);
            if (!(com instanceof EText))
            {
                panel.add(com);
                next.remove(0);
                continue;
            }
            EText text = (EText) com;
            panel.insertLastText(text);
            next.remove(0);
        }
        next.removeThis();

    }

    /**
     * 是否需要跳过
     * @return boolean
     */
    private boolean isContinuePanel()
    {
        EPanel panel = getPanel();
        if (panel == null)
            return true;
        if (panel.isModify())
            return false;
        if (panel.getY() != y0 ||
            panel.getX() != getRectangle().getX() ||
            panel.getWidth() != getRectangle().getWidth())
        {
            panel.setModify(true);
            return false;
        }
        return true;
    }

    /**
     * 将下页的面板向上提
     */
    private void resetPage_1()
    {
        EPage page = getPage().getNextPage();
        if (page == null || page.size() == 0)
        {
            stop();
            return;
        }
        setPanel(page.get(0));
        getPage().add(getPanel());
        page.remove(getPanel());
        getPanel().setX(getRectangle().getX());
        getPanel().setY(y0);
        getPanel().setWidth(getRectangle().getWidth());
        getPanel().setMaxHeight(getRectangle().getHeight() - y0 +
                                getRectangle().getY());
        getPanel().setModify(true);
        getPanel().reset();
        //放不下
        if (getPanel().getMaxHeight() == -1)
        {
            //拷贝到下页
            copyToNextPage();
            stop();
            return;
        }
        if (getPanel().hasNextPageLinkPanel())
        {
            stop();
            return;
        }
        y0 += getPanel().getHeight();
        index++;
    }

    /**
     * 模块调整
     */
    private void resetTD()
    {
        //扫描
        while (isDoing())
        {
            switch (getState())
            {
            case 0: //向下扫描TD
                resetTD_0();
                break;
            case 1: //将下页TD的面板向上提
                resetTD_1();
                break;
            }
        }
    }

    /**
     * 向下扫描
     */
    private void resetTD_0()
    {
        if (index >= getTD().size())
        {
            state = 1;
            return;
        }
        //设置当前面板
        setPanel(getTD().get(index));
        getPanel().setX(getRectangle().getX());
        getPanel().setY(y0);
        getPanel().setWidth(getRectangle().getWidth());
        getPanel().setMaxHeight(getRectangle().getHeight() - y0 +
                                getRectangle().getY());
        getPanel().setModify(true);
        getPanel().reset();
        //被删除
        if(getPanel().getMaxHeight() == -2)
            return;
        //放不下
        if (getPanel().getMaxHeight() == -1)
        {
            //拷贝到下页
            copyToNextTD();
            getTD().setHeight(y0 + getTD().getInsets().bottom);
            stop();
            return;
        }
        /*if (getPanel().hasNextLinkPanel())
        {
            getTD().setHeight(y0 + getTD().getInsets().bottom);
            stop();
            return;
        }*/
        y0 += getPanel().getHeight();
        index++;
    }

    /**
     * 拷贝到下一个TD
     */
    private void copyToNextTD()
    {
        ETD td = getTD().getNextLinkTD();
        if (td == null)
            td = getTD().newNextTD();
        for (int i = getTD().size() - 1; i >= index; i--)
        {
            EPanel panel = getTD().get(i);
            getTD().remove(i);
            td.add(0, panel);
        }
        td.setModify(true);
    }

    /**
     * 将下页TD的面板向上提
     */
    private void resetTD_1()
    {
        ETD td = getTD().getNextLinkTD();
        if(td == null || td.size() == 0)
        {
            getTD().setHeight(y0 + getTD().getInsets().bottom);
            stop();
            return;
        }
        setPanel(td.get(0));
        getTD().add(getPanel());
        td.remove(getPanel());
        td.setModify(true);
        getPanel().setX(getRectangle().getX());
        getPanel().setY(y0);
        getPanel().setWidth(getRectangle().getWidth());
        getPanel().setMaxHeight(getRectangle().getHeight() - y0 +
                                getRectangle().getY());
        getPanel().setModify(true);
        getPanel().reset();
         //放不下
        if (getPanel().getMaxHeight() == -1)
        {
            //拷贝到下页
            copyToNextTD();
            getTD().setHeight(y0 + getTD().getInsets().bottom);
            stop();
            return;
        }
        if (getPanel().hasNextLinkPanel())
        {
            y0 += getPanel().getHeight();
            getTD().setHeight(y0 + getTD().getInsets().bottom);
            stop();
            return;
        }
        y0 += getPanel().getHeight();
        index++;
    }
}
