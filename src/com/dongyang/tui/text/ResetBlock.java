package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;
import com.dongyang.util.TList;

/**
 *
 * <p>Title: 调成组件处理对象</p>
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
public class ResetBlock
{
    /**
     * 父类面板
     */
    private EPanel panel;
    /**
     * 处理句柄
     */
    private int index;
    /**
     * 启动开关
     */
    private boolean doing;
    /**
     * 状态
     */
    private int state;
    /**
     * 横坐标位置
     */
    private int x0;
    /**
     * 纵坐标位置
     */
    private int y0;
    /**
     * 尺寸范围
     */
    private DRectangle rectangle;
    /**
     * 当前组件
     */
    private IBlock block;
    /**
     * 行对象
     */
    private TList rows;
    /**
     * 行数
     */
    private int rowCount;
    /**
     * 构造器
     * @param panel EPanel
     */
    public ResetBlock(EPanel panel)
    {
        //设置面板
        setPanel(panel);
    }
    /**
     * 设置面板
     * @param panel EPanel
     */
    public void setPanel(EPanel panel)
    {
        this.panel = panel;
    }
    /**
     * 得到面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return panel;
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
    public void setRectangle(int x,int y,int width,int height)
    {
        setRectangle(new DRectangle(x,y,width,height));
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
        //行数归零
        rowCount = 0;
        //句柄归零
        setIndex(0);
        //状态归零
        setState(0);
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
     * 设置当前组件
     * @param block IBlock
     */
    public void setBlock(IBlock block)
    {
        this.block = block;
    }
    /**
     * 得到当前组件
     * @return IBlock
     */
    public IBlock getBlock()
    {
        return block;
    }
    /**
     * 得到当前的文本对象
     * @return EText
     */
    public EText getText()
    {
        return(EText)getBlock();
    }
    /**
     * 得到当前的EPic
     * @return EPic
     */
    public EPic getPic()
    {
        return (EPic)getBlock();
    }
    /**
     * 得到当前的EImage
     * @return EImage
     */
    public EImage getImage()
    {
        return (EImage)getBlock();
    }
    /**
     * 得到类型
     * 1 EText
     * @return int
     */
    public int getType()
    {
        if(getBlock() instanceof EText)
            return 1;
        if(getBlock() instanceof ETable)
            return 2;
        if(getBlock() instanceof EPic)
            return 3;
        if(getBlock() instanceof EImage)
            return 4;
        return 0;
    }
    /**
     * 增加组件
     * @param block IBlock
     */
    public void add(IBlock block)
    {
        rows.add(block);
    }
    /**
     * 清空行列表
     */
    public void clearRow()
    {
        rows = new TList();
    }
    /**
     * 得到组件
     * @param index int
     * @return IBlock
     */
    public IBlock get(int index)
    {
        return (IBlock)rows.get(index);
    }
    /**
     * 得到组件个数
     * @return int
     */
    public int size()
    {
        return rows.size();
    }
    /**
     * 得到页面风格
     * @return int
     * 0 普通
     * 1 页面
     */
    public int getViewStyle()
    {
        return getPanel().getViewManager().getViewStyle();
    }
    /**
     * 调整尺寸
     */
    public void reset()
    {
        switch(getPanel().getType())
        {
        case 0://基础面板
            resetBase();
            break;
        case 1://表格面板
            resetTable();
            break;
        }
    }
    /**
     * 调整普通对象
     */
    public void resetBase()
    {
        start();
        //横坐标位置归零
        x0 = getRectangle().getX();
        //纵坐标位置归零
        y0 = getRectangle().getY() + getPanel().getSpaceBetween();
        if(isHeadRow())
            y0 += getPanel().getParagraphForward();
        //清空行列表
        clearRow();
        //扫描
        while(isDoing())
        {
            switch(getState())
            {
            case 0://向下扫描页面
                resetBlock_0();
                break;
            case 1://将下页的组件向上提
                resetBlock_1();
                break;
            }
        }
    }
    /**
     * 调整表格
     */
    public void resetTable()
    {
        if(getPanel().size() == 0)
            return;
        ETable table = (ETable)getPanel().get(0);
        table.setY(0);
        table.setMaxHeight(getRectangle().getHeight());
        table.reset();
        if(getPanel().size() == 0)
        {
            getPanel().removeThis();
            getPanel().setMaxHeight(-2);
            return;
        }
        getPanel().setHeight(table.getHeight());
        if(getPanel().size() != 1)
        {
            int index = getPanel().findIndex();
            EPanel panel = new EPanel(getPanel().getPage());
            getPanel().getPage().add(index + 1,panel);
            for(int i = 1;i < getPanel().size();i++)
            {
                IBlock block = getPanel().get(i);
                getPanel().remove(i);
                panel.add(block);
                block.setModify(true);
            }
            getPanel().setNextPageLinkPanel(panel);
            panel.setPreviousPageLinkPanel(getPanel());
        }
    }
    /**
     * 向下扫描页面
     */
    public void resetBlock_0()
    {
        if(index >= getPanel().size())
        {
            setState(1);
            return;
        }
        //设置当前组件
        setBlock(getPanel().get(index));
        switch(getType())
        {
        case 1:
            resetText();
            return;
        case 2:
            resetTable();
            getPanel().setType(1);
            y0 += getBlock().getHeight();
            index ++;
            return;
        case 3:
            //调整Pic
            resetPic();
            return;
        case 4:
            //调整Image
            resetImage();
            return;
        }
        index ++;
    }
    /**
     * 调整Pic
     */
    public void resetPic()
    {
        EPic pic = getPic();
        pic.setModify(false);
        if(!pic.isVisible())
        {
            index ++;
            return;
        }
        pic.setX(x0);
        pic.setY(y0);
        if(pic.getHeight() > getRectangle().getHeight() - y0)
        {
            if(index == 0 || y0 == getRectangle().getY())
            {
                //全部放不下
                getPanel().setMaxHeight(-1);
                getPanel().setModify(true);
                getPanel().setHeight(y0 - getPanel().getSpaceBetween());
                stop();
                return;
            }
            getPanel().setHeight(y0 - getPanel().getSpaceBetween());
            stop();
            return;
        }
        if(pic.getWidth() > getRectangle().getWidth() - x0)
        {
            if(size() > 0)
            {
                resetRow();
                return;
            }
            add(pic);
            x0 += pic.getWidth();
            index ++;
            return;
        }
        x0 += pic.getWidth();
        add(pic);
        index ++;
        return;
    }
    /**
     * 调整Image
     */
    public void resetImage()
    {
        EImage image = getImage();
        image.setModify(false);
        if(!image.isVisible())
        {
            index ++;
            return;
        }
        image.setX(x0);
        image.setY(y0);
        if(image.getHeight() > getRectangle().getHeight() - y0)
        {
            if(index == 0 || y0 == getRectangle().getY())
            {
                //全部放不下
                getPanel().setMaxHeight(-1);
                getPanel().setModify(true);
                getPanel().setHeight(y0 - getPanel().getSpaceBetween());
                stop();
                return;
            }
            getPanel().setHeight(y0 - getPanel().getSpaceBetween());
            stop();
            return;
        }
        if(image.getWidth() > getRectangle().getWidth() - x0)
        {
            if(size() > 0)
            {
                resetRow();
                return;
            }
            add(image);
            x0 += image.getWidth();
            index ++;
            return;
        }
        x0 += image.getWidth();
        add(image);
        index ++;
        return;
    }
    /**
     * 是否是首行
     * @return boolean
     */
    public boolean isHeadRow()
    {
        if(getPanel().parentIsPage() && getPanel().hasPreviousPageLinkPanel())
            return false;
        if(getPanel().parentIsTD() && getPanel().hasPreviousLinkPanel())
            return false;
        if(rowCount > 0)
            return false;
        return true;
    }
    /**
     * 得到特殊缩进宽度
     * @return int
     */
    public int getRowRetractWidth()
    {
        switch(getPanel().getRetractType())
        {
        case 0://无
            return 0;
        case 1://首行缩进
            if(isHeadRow())
                return getPanel().getRetractWidth();
            return 0;
        case 2://悬挂缩进
            if(isHeadRow())
                return 0;
            return getPanel().getRetractWidth();
        }
        return 0;
    }
    /**
     * 调整EText
     */
    public void resetText()
    {
        EText text = getText();
        if(text.isPreview() && text.isDeleteFlg())
        {
            text.setModify(false);
            index ++;
            return;
        }
        text.setX(x0);
        text.setY(y0);
        text.setMaxWidth(getRectangle().getWidth() -
                         getPanel().getRetractLeft() -
                         getPanel().getRetractRight() -
                         getRowRetractWidth() -
                         x0);
        text.setMaxHeight(getRectangle().getHeight() - y0 + getPanel().getSpaceBetween() / 2);
        text.setPosition(size() == 0?1:0);
        text.reset();
        switch(text.getPosition())
        {
        case -1://放不下
            if(index == 0 || y0 == getRectangle().getY())
            {
                //全部放不下
                getPanel().setMaxHeight(-1);
                getPanel().setModify(true);
                getPanel().setHeight(y0 - getPanel().getSpaceBetween());
                stop();
                return;
            }
            separate();
            int height = y0 - getPanel().getSpaceBetween();
            if(height > getRectangle().getHeight())
                height = getRectangle().getHeight();
            getPanel().setHeight(height);
            stop();
            return;
        case 0://放得下
            x0 += text.getWidth();
            add(text);
            index ++;
            return;
        case 1://放到下一行
            if(size() > 0)
                resetRow();
            return;
        case 2://末尾
            add(text);
            resetRow();
            index ++;
            return;
        }
    }
    /**
     * 将下页的组件向上提
     */
    public void resetBlock_1()
    {
        EPanel nextPanel = getPanel().parentIsPage()?
                           getPanel().getNextPageLinkPanel():
                           getPanel().getNextLinkPanel();
        if(nextPanel == null || nextPanel.size() == 0)
        {
            if (size() > 0)
                resetRow();
            int height = y0 - getPanel().getSpaceBetween()
                         //加段后
                         + getPanel().getParagraphAfter();
            if(getViewStyle()!= 0 && height > getRectangle().getHeight())
                height = getRectangle().getHeight();
            getPanel().setHeight(height);
            stop();
            return;
        }
        IBlock block = nextPanel.get(0);
        if(block instanceof EText)
        {
            EText text = (EText) block;
            int stringSize = getPanel().getDString().size();
            String s = text.getString();
            getPanel().getDString().add(s);
            text.indexMove(stringSize);
            nextPanel.getDString().remove(0,s.length());
            nextPanel.indexMove(text,-s.length());
        }
        getPanel().add(block);
        nextPanel.remove(block);
        setState(0);
        if(nextPanel.size() == 0)
            nextPanel.removeThis();
    }

    /**
     * 得到使用的宽度
     * @return int
     */
    private int getUsingWidth()
    {
        int width = 0;
        for(int i = 0;i < size();i++)
            width += get(i).getWidth();
        return width;
    }
    /**
     * 得到使用的高度
     * @return int
     */
    private int getUsingHeight()
    {
        int height = 0;
        for(int i = 0;i < size();i++)
        {
            int h = get(i).getHeight();
            if(h > height)
                height = h;
        }
        return height;
    }
    /**
     * 居左横坐标算法
     * @return int
     */
    private int getAlignementLeftX()
    {
        int x = getPanel().getRetractLeft();
        //处理特殊缩进
        switch(getPanel().getRetractType())
        {
        case 1://首行缩进
            if(isHeadRow())
                x += getPanel().getRetractWidth();
            break;
        case 2://悬挂缩进
            if(isHeadRow())
                break;
            x += getPanel().getRetractWidth();
            break;
        }
        return x;
    }
    /**
     * 居中横坐标算法
     * @return int
     */
    private int getAlignementCenterX()
    {
        int x = 0;
        //得到使用的宽度
        int w = getUsingWidth();
        //处理特殊缩进
        switch(getPanel().getRetractType())
        {
        case 0: //无
            x = (int) ((double) (getRectangle().getWidth() -
                                 getPanel().getRetractLeft() -
                                 getPanel().getRetractRight() -
                                 w) / 2.0);
            break;
        case 1://首行缩进
            if(isHeadRow())
            {
                x = (int) ((double) (getRectangle().getWidth() -
                                     getPanel().getRetractLeft() -
                                     getPanel().getRetractRight() -
                                     getPanel().getRetractWidth() -
                                     w) / 2.0) + getPanel().getRetractLeft() +
                    getPanel().getRetractWidth();
                break;
            }
            x = (int) ((double) (getRectangle().getWidth() -
                                 getPanel().getRetractLeft() -
                                 getPanel().getRetractRight() -
                                 w) / 2.0) + getPanel().getRetractLeft();
            break;
        case 2://悬挂缩进
            if(isHeadRow())
            {
                x = (int) ((double) (getRectangle().getWidth() -
                                     getPanel().getRetractLeft() -
                                     getPanel().getRetractRight() -
                                     w) / 2.0) + getPanel().getRetractLeft();
                break;
            }
            x = (int) ((double) (getRectangle().getWidth() -
                                 getPanel().getRetractLeft() -
                                 getPanel().getRetractRight() -
                                 getPanel().getRetractWidth() -
                                 w) / 2.0) + getPanel().getRetractLeft() +
                getPanel().getRetractWidth();
            break;
        }
        return x;
    }
    /**
     * 居右横坐标算法
     * @return int
     */
    private int getAlignementRightX()
    {
        //得到使用的宽度
        int w = getUsingWidth();
        return getRectangle().getWidth() - w - getPanel().getRetractRight();
    }
    /**
     * 横坐标算法
     * @return int
     */
    private int getAlignementX()
    {
        switch(getPanel().getAlignment())
        {
        case 0://居左
            return getAlignementLeftX();
        case 1://居中
            return getAlignementCenterX();
        case 2://局右
            return getAlignementRightX();
        }
        return getPanel().getRetractLeft();
    }
    /**
     * 整理一行数据
     */
    public void resetRow()
    {
        //得到使用的宽度
        int w = getUsingWidth();
        //得到使用的高度
        int h = getUsingHeight();
        //计算横坐标的起始位置
        int x = getAlignementX();
        int y = get(0).getY() + h + getPanel().getSpaceBetween() / 2;

        for(int i = 0;i < size();i++)
        {
            IBlock block = get(i);
            block.setX(x);
            block.setY(y - block.getHeight());
            x += block.getWidth();
            block.setPosition(0);
        }
        //设置首尾标记
        IBlock blockHead = get(0);
        blockHead.setPosition(1);
        IBlock blockEnd = get(size() - 1);
        if(blockHead == blockEnd)
            blockEnd.setPosition(3);
        else
            blockEnd.setPosition(2);

        //整理坐标
        x0 = getRectangle().getX();
        y0 += h + getPanel().getSpaceBetween();
        //清空行
        clearRow();
        rowCount ++;
        if(getViewStyle() == 0)
            getPanel().setWidth(w);
    }
    /**
     * 分割
     */
    public void separate()
    {
        if(getPanel().parentIsPage())
            separatePage();
        else if(getPanel().parentIsTD())
            separateTD();
    }
    /**
     * 分割页面
     */
    public void separatePage()
    {
        int goindex = index;
        if(size() > 0)
            goindex = get(0).findIndex();
        EPanel nextPanel = getPanel().parentIsPage()?
                           getPanel().getNextPageLinkPanel():
                           getPanel().getNextLinkPanel();

        if(nextPanel == null)
        {
            EPage page = getPanel().getParentPage();
            EPage nextPage = page.getNextPage();
            if (nextPage == null)
                nextPage = page.getPageManager().newPage();
            nextPanel = nextPage.newPanel(0);
            //设置连接
            nextPanel.setPreviousPageLinkPanel(getPanel());
            getPanel().setNextPageLinkPanel(nextPanel);
            //传递连接面板参数
            getPanel().setLinkPanelParameter(nextPanel);
        }
        //将分割后的模块搬到下一个面板中
        for(int i = getPanel().size() - 1;i >= goindex;i--)
        {
            IBlock block = getPanel().get(i);
            if(!(block instanceof EText))
                nextPanel.add(block);
            else
                nextPanel.insertText((EText)block);
            getPanel().remove(block);
        }
        //将分割后其余的面板搬到下一个页面中
        int index = getPanel().findIndex();
        EPage page = getPanel().getParentPage();
        EPage pageNext = nextPanel.getParentPage();
        int insertIndex = nextPanel.findIndex() + 1;
        for(int i = page.size() - 1;i > index;i--)
        {
            pageNext.add(insertIndex,page.get(i));
            page.remove(i);
        }
        nextPanel.setModify(true);
    }
    /**
     * 分割TD
     */
    public void separateTD()
    {
        int goindex = index;
        if(size() > 0)
            goindex = get(0).findIndex();
        EPanel nextPanel = getPanel().getNextLinkPanel();
        if(nextPanel == null)
        {
            ETD td = getPanel().getParentTD();
            ETD nexttd = td.getNextLinkTD();
            if (nexttd == null)
                nexttd = td.newNextTD();
            nextPanel = nexttd.newPanel(0);
            //设置连接
            nextPanel.setPreviousLinkPanel(getPanel());
            getPanel().setNextLinkPanel(nextPanel);
            //传递连接面板参数
            getPanel().setLinkPanelParameter(nextPanel);
        }
        //将分割后的模块搬到下一个面板中
        for(int i = getPanel().size() - 1;i >= goindex;i--)
        {
            IBlock block = getPanel().get(i);
            if(!(block instanceof EText))
                nextPanel.add(block);
            else
                nextPanel.insertText((EText)block);
            getPanel().remove(block);
        }
        //将分割后其余的面板搬到下一个TD中
        int index = getPanel().findIndex();
        ETD td = getPanel().getParentTD();
        ETD tdNext = nextPanel.getParentTD();
        int insertIndex = nextPanel.findIndex() + 1;
        for(int i = td.size() - 1;i > index;i--)
        {
            tdNext.add(insertIndex,td.get(i));
            td.remove(i);
        }
        nextPanel.setModify(true);
    }
}
