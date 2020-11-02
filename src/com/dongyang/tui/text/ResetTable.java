package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;

/**
 *
 * <p>Title: 调成表格处理对象</p>
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
public class ResetTable
{
    /**
     * 父类表格
     */
    private ETable table;
    /**
     * 尺寸范围
     */
    private DRectangle rectangle;
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
     * 纵坐标位置
     */
    private int y0;
    /**
     * 当前的TR
     */
    private ETR tr;
    /**
     * 初始化
     * @param table ETable
     */
    public ResetTable(ETable table)
    {
        setTable(table);
    }
    /**
     * 设置表格
     * @param table ETable
     */
    public void setTable(ETable table)
    {
        this.table = table;
    }
    /**
     * 得到表格
     * @return ETable
     */
    public ETable getTable()
    {
        return table;
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
     * 设置当前的TR
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * 得到当前的TR
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
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
     * 调整尺寸
     */
    public void reset()
    {
        if(getTable().isHideTable())
        {
            getTable().setHeight(0);
            return;
        }
        if(getTable().size() == 0)
        {
            getTable().setModify(true);
            getTable().removeThis();
            return;
        }
        start();
        //纵坐标位置归零
        y0 = getTable().getInsets().top;

        //扫描
        while(isDoing())
        {
            switch(getState())
            {
            case 0://向下扫描字
                resetTR_0();
                break;
            case 1://向上移动
                resetTR_1();
                break;
            }
        }
    }
    /**
     * 向下扫描字
     */
    public void resetTR_0()
    {
        if(index >= getTable().size())
        {
            setState(1);
            return;
        }
        //设置当前TR
        setTR(getTable().get(index));
        //是否跳过
        if(isContinueTR())
        {
            getTR().resetModify();
            index ++;
            return;
        }
        if(y0 != getTable().getInsets().top)
            y0 += getTable().getHSpace();
        getTR().setX(getTable().getInsets().left);
        getTR().setY(y0);
        getTR().setWidth(getRectangle().getWidth() - getTable().getInsets().left - getTable().getInsets().right);
        getTR().setMaxHeight(getRectangle().getHeight() - y0 - getTable().getInsets().bottom);
        getTR().reset();
        //去除空行
        if(getTR().getMaxHeight() == -1)
        {
            if(y0 != getTable().getInsets().top)
                y0 -= getTable().getHSpace();
            return;
        }
        //被删除
        if(getTR().getMaxHeight() == -2)
        {
            getTable().setHeight(y0 + getTable().getInsets().bottom - getTable().getHSpace());
            stop();
            getTable().setMaxHeight(-1);
            return;
        }
        y0 += getTR().getHeight();
        index ++;
    }
    /**
     * 是否跳过
     * @return boolean
     */
    private boolean isContinueTR()
    {
        return !getTR().isVisible();
    }
    /**
     * 向上移动
     */
    public void resetTR_1()
    {
        ETable table = getTable().getNextTable();
        if(table == null || table.size() == 0)
        {
            getTable().setHeight(y0 + getTable().getInsets().bottom);
            stop();
            return;
        }
        ETR tr = table.get(0);
        table.remove(tr);
        getTable().add(tr);
        if(table.size() == 0)
            table.removeThis();
        setState(0);
    }
}
