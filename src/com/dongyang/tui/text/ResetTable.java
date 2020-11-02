package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;

/**
 *
 * <p>Title: ���ɱ�������</p>
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
     * ������
     */
    private ETable table;
    /**
     * �ߴ緶Χ
     */
    private DRectangle rectangle;
    /**
     * ������
     */
    private int index;
    /**
     * ��������
     */
    private boolean doing;
    /**
     * ״̬
     */
    private int state;
    /**
     * ������λ��
     */
    private int y0;
    /**
     * ��ǰ��TR
     */
    private ETR tr;
    /**
     * ��ʼ��
     * @param table ETable
     */
    public ResetTable(ETable table)
    {
        setTable(table);
    }
    /**
     * ���ñ��
     * @param table ETable
     */
    public void setTable(ETable table)
    {
        this.table = table;
    }
    /**
     * �õ����
     * @return ETable
     */
    public ETable getTable()
    {
        return table;
    }
    /**
     * ���óߴ緶Χ
     * @param rectangle DRectangle
     */
    public void setRectangle(DRectangle rectangle)
    {
        this.rectangle = rectangle;
    }
    /**
     * ���óߴ緶Χ
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
     * �õ��ߴ緶Χ
     * @return DRectangle
     */
    public DRectangle getRectangle()
    {
        return rectangle;
    }
    /**
     * ���þ��
     * @param index int
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    /**
     * �õ����
     * @return int
     */
    public int getIndex()
    {
        return index;
    }
    /**
     * ���õ�ǰ��TR
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * �õ���ǰ��TR
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
    }
    /**
     * ����
     */
    public void clear()
    {
        //�������
        setIndex(0);
        //״̬����
        setState(0);
    }
    /**
     * ����
     */
    public void start()
    {
        doing = true;
    }
    /**
     * ֹͣ
     */
    public void stop()
    {
        doing = false;
    }
    /**
     * �Ƿ�����
     * @return boolean
     */
    public boolean isDoing()
    {
        return doing;
    }
    /**
     * ����״̬
     * @param state int
     */
    public void setState(int state)
    {
        this.state = state;
    }
    /**
     * �õ�״̬
     * @return int
     */
    public int getState()
    {
        return state;
    }
    /**
     * �����ߴ�
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
        //������λ�ù���
        y0 = getTable().getInsets().top;

        //ɨ��
        while(isDoing())
        {
            switch(getState())
            {
            case 0://����ɨ����
                resetTR_0();
                break;
            case 1://�����ƶ�
                resetTR_1();
                break;
            }
        }
    }
    /**
     * ����ɨ����
     */
    public void resetTR_0()
    {
        if(index >= getTable().size())
        {
            setState(1);
            return;
        }
        //���õ�ǰTR
        setTR(getTable().get(index));
        //�Ƿ�����
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
        //ȥ������
        if(getTR().getMaxHeight() == -1)
        {
            if(y0 != getTable().getInsets().top)
                y0 -= getTable().getHSpace();
            return;
        }
        //��ɾ��
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
     * �Ƿ�����
     * @return boolean
     */
    private boolean isContinueTR()
    {
        return !getTR().isVisible();
    }
    /**
     * �����ƶ�
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
