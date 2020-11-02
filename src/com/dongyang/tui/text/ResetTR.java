package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;
import com.dongyang.util.TList;

/**
 *
 * <p>Title: 调成TR处理对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.18
 * @version 1.0
 */
public class ResetTR
{
    /**
     * 父类表格
     */
    private ETR tr;
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
     * 横坐标位置
     */
    private int x0;
    /**
     * 当前的TD
     */
    private ETD td;
    /**
     * 初始化
     * @param tr ETR
     */
    public ResetTR(ETR tr)
    {
        setTR(tr);
    }
    /**
     * 设置表格
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * 得到表格
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
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
     * 设置当前的TD
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }
    /**
     * 得到当前的TD
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
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
        //判断TR是否有效
        int checkTD = checkTR();
        //全部TD都没有数据
        if(checkTD == 0)
        {
            getTR().removeThis();
            getTR().setMaxHeight(-1);
            return;
        }
        getTR().setHeight(20);
        start();
        //纵坐标位置归零
        x0 = getTR().getInsets().left;

        //扫描
        while(isDoing())
        {
            switch(getState())
            {
            case 0://向下扫描字
                resetTD_0();
                break;
            case 1://向上移动
                resetTD_1();
                break;
            }
        }
    }
    /**
     * 向下扫描字
     */
    public void resetTD_0()
    {
        if(index >= getTR().size())
        {
            state = 1;
            return;
        }
        //合并单元格跳跃坐标
        if(index > 0)
        {
            ETD p = getTR().get(index - 1);
            if(!p.isVisible() && p.getUniteTD() != null)
                x0 = p.getUniteTD().getX() + p.getUniteTD().getWidth() + getTR().getWSpace();
        }
        //设置当前TD
        setTD(getTR().get(index));
        getTD().setX(x0);
        getTD().setY(getTR().getInsetsY());
        if(getTD().isLastTD())
           getTD().setWidth(getTR().getWidth() - getTR().getInsets().right - x0);
        getTD().setMaxHeight(getRectangle().getHeight() - getTR().getInsets().top - getTR().getInsets().bottom);
        getTD().reset();
        if(getTD().isVisible())
            x0 += getTD().getWidth() + getTR().getWSpace();
        index ++;
    }
    /**
     * 得到使用的高度
     * @return int
     */
    private int getUserHeight()
    {
        int h = 0;
        for(int i = 0;i < getTR().size();i++)
        {
            ETD td = getTR().get(i);
            //如果没有纵向合并单元格并且显示(正常TD)
            if(td.getSpanY() == 0 && td.isVisible())
            {
                int height = td.getHeight();
                if (h < height)
                    h = height;
                continue;
            }
            //计算每一个TD使用高度的时候，要考虑合并单元格的特殊情况
            // 1 如果发现一个隐含的TD确认是否是由于合并单元格所导致的
            // 2 找到隐含的TD所关联的合并单元格的主体对象
            // 3 确认主体对象是否在本行结束
            // 4 计算剩余高度对本行的影响
            if(!td.isVisible() && td.getUniteTD() != null)
            {
                ETD utd = td.getUniteTD();
                //不是合并单元格的最后一行跳过
                if(utd.getSpanY() != getTR().getRow() - utd.getTR().getRow())
                    continue;
                int height = utd.getHeight() - ((getTR().getY() + getTR().getInsetsY()) -
                                                (utd.getTR().getY() + utd.getTR().getInsetsY()));
                if (h < height)
                    h = height;
                continue;
            }
        }
        return h;
    }
    /**
     * 设置使用高度
     * @param height int
     */
    private void setUserHeight(int height)
    {
        for(int i = 0;i < getTR().size();i++)
        {
            ETD td = getTR().get(i);
            if(td.getSpanY() == 0 && td.isVisible())
            {
                td.setHeight(height);
                continue;
            }
            if(!td.isVisible() && td.getUniteTD() != null)
            {
                ETD utd = td.getUniteTD();
                //不是合并单元格的最后一行跳过
                if(utd.getSpanY() != getTR().getRow() - utd.getTR().getRow())
                    continue;
                int h = ((getTR().getY() + getTR().getInsetsY()) -
                         (utd.getTR().getY() + utd.getTR().getInsetsY())) + height;
                utd.setHeight(h);
            }
        }
    }
    /**
     * 判断TR是否有效
     * @return int
     * 1 全部TD都有数据
     * 0 全部TD都没有数据
     * -1 存在无效数据的TD
     * -2 错误
     */
    private int checkTR()
    {
        int t = 0;
        int f = 0;
        for(int i = 0;i < getTR().size();i++)
            if(getTR().get(i).size() > 0)
                t ++;
            else
                f ++;
        if(t == 0 && f > 0)
            return 0;
        if(f == 0 && t > 0)
            return 1;
        if(t > 0 && f > 0)
            return -1;
        return -2;
    }
    /**
     * 删除合并单元格的TD
     */
    private void removeUniteTD()
    {
        for(int i = 0;i < getTR().size();i++)
        {
            ETD td = getTR().get(i);
            if(!td.isSpan())
                continue;
            //转移句柄
            gotoTDHandle(td);
        }
    }
    /**
     * 转移句柄
     * @param td ETD
     */
    private void gotoTDHandle(ETD td)
    {
        ETD n = td.getNextLinkTD();
        if(n == null)
            return;
        TList list = getTR().getTable().getUniteTDHandles(td);
        for(int i = 0;i < list.size();i++)
            ((ETD)list.get(i)).setUniteTD(n);
    }
    /**
     * 向上移动
     */
    public void resetTD_1()
    {
        //判断TR是否有效
        int checkTD = checkTR();
        //全部TD都没有数据
        if(checkTD == 0)
        {
            //删除合并单元格的TD
            removeUniteTD();
            getTR().removeThis();
            getTR().setMaxHeight(-2);
            stop();
            return;
        }
        //存在无效数据的TD
        if(getTR().getPreviousLinkTR() == null && checkTD == -1)
        {
            getTR().setMaxHeight(0);
            getTR().reset();
            getTR().setMaxHeight(-2);
            stop();
            return;
        }

        //得到使用的高度
        int height = getUserHeight();
        //设置使用高度
        setUserHeight(height);
        getTR().setHeight(height + getTR().getInsets().top + getTR().getInsets().bottom);
        stop();
    }
}
