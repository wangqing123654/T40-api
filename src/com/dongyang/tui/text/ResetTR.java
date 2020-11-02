package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;
import com.dongyang.util.TList;

/**
 *
 * <p>Title: ����TR�������</p>
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
     * ������
     */
    private ETR tr;
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
    private int x0;
    /**
     * ��ǰ��TD
     */
    private ETD td;
    /**
     * ��ʼ��
     * @param tr ETR
     */
    public ResetTR(ETR tr)
    {
        setTR(tr);
    }
    /**
     * ���ñ��
     * @param tr ETR
     */
    public void setTR(ETR tr)
    {
        this.tr = tr;
    }
    /**
     * �õ����
     * @return ETR
     */
    public ETR getTR()
    {
        return tr;
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
     * ���õ�ǰ��TD
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }
    /**
     * �õ���ǰ��TD
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
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
        //�ж�TR�Ƿ���Ч
        int checkTD = checkTR();
        //ȫ��TD��û������
        if(checkTD == 0)
        {
            getTR().removeThis();
            getTR().setMaxHeight(-1);
            return;
        }
        getTR().setHeight(20);
        start();
        //������λ�ù���
        x0 = getTR().getInsets().left;

        //ɨ��
        while(isDoing())
        {
            switch(getState())
            {
            case 0://����ɨ����
                resetTD_0();
                break;
            case 1://�����ƶ�
                resetTD_1();
                break;
            }
        }
    }
    /**
     * ����ɨ����
     */
    public void resetTD_0()
    {
        if(index >= getTR().size())
        {
            state = 1;
            return;
        }
        //�ϲ���Ԫ����Ծ����
        if(index > 0)
        {
            ETD p = getTR().get(index - 1);
            if(!p.isVisible() && p.getUniteTD() != null)
                x0 = p.getUniteTD().getX() + p.getUniteTD().getWidth() + getTR().getWSpace();
        }
        //���õ�ǰTD
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
     * �õ�ʹ�õĸ߶�
     * @return int
     */
    private int getUserHeight()
    {
        int h = 0;
        for(int i = 0;i < getTR().size();i++)
        {
            ETD td = getTR().get(i);
            //���û������ϲ���Ԫ������ʾ(����TD)
            if(td.getSpanY() == 0 && td.isVisible())
            {
                int height = td.getHeight();
                if (h < height)
                    h = height;
                continue;
            }
            //����ÿһ��TDʹ�ø߶ȵ�ʱ��Ҫ���Ǻϲ���Ԫ����������
            // 1 �������һ��������TDȷ���Ƿ������ںϲ���Ԫ�������µ�
            // 2 �ҵ�������TD�������ĺϲ���Ԫ����������
            // 3 ȷ����������Ƿ��ڱ��н���
            // 4 ����ʣ��߶ȶԱ��е�Ӱ��
            if(!td.isVisible() && td.getUniteTD() != null)
            {
                ETD utd = td.getUniteTD();
                //���Ǻϲ���Ԫ������һ������
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
     * ����ʹ�ø߶�
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
                //���Ǻϲ���Ԫ������һ������
                if(utd.getSpanY() != getTR().getRow() - utd.getTR().getRow())
                    continue;
                int h = ((getTR().getY() + getTR().getInsetsY()) -
                         (utd.getTR().getY() + utd.getTR().getInsetsY())) + height;
                utd.setHeight(h);
            }
        }
    }
    /**
     * �ж�TR�Ƿ���Ч
     * @return int
     * 1 ȫ��TD��������
     * 0 ȫ��TD��û������
     * -1 ������Ч���ݵ�TD
     * -2 ����
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
     * ɾ���ϲ���Ԫ���TD
     */
    private void removeUniteTD()
    {
        for(int i = 0;i < getTR().size();i++)
        {
            ETD td = getTR().get(i);
            if(!td.isSpan())
                continue;
            //ת�ƾ��
            gotoTDHandle(td);
        }
    }
    /**
     * ת�ƾ��
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
     * �����ƶ�
     */
    public void resetTD_1()
    {
        //�ж�TR�Ƿ���Ч
        int checkTD = checkTR();
        //ȫ��TD��û������
        if(checkTD == 0)
        {
            //ɾ���ϲ���Ԫ���TD
            removeUniteTD();
            getTR().removeThis();
            getTR().setMaxHeight(-2);
            stop();
            return;
        }
        //������Ч���ݵ�TD
        if(getTR().getPreviousLinkTR() == null && checkTD == -1)
        {
            getTR().setMaxHeight(0);
            getTR().reset();
            getTR().setMaxHeight(-2);
            stop();
            return;
        }

        //�õ�ʹ�õĸ߶�
        int height = getUserHeight();
        //����ʹ�ø߶�
        setUserHeight(height);
        getTR().setHeight(height + getTR().getInsets().top + getTR().getInsets().bottom);
        stop();
    }
}
