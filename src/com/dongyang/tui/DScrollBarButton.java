package com.dongyang.tui;

import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import com.dongyang.util.TypeTool;
import java.awt.Color;

/**
 *
 * <p>Title: �����鰴ť</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.9
 * @version 1.0
 */
public class DScrollBarButton extends DButton
{
    /**
     * ������
     */
    public DScrollBarButton()
    {
        setType(1);
    }
    /**
     * ������
     * @param scrollBar DScrollBar
     * @param index int
     */
    public DScrollBarButton(DScrollBar scrollBar,int index)
    {
        //���ù�����
        setScrollBar(scrollBar);
        //����λ��
        setIndex(index);
        //��ֹ�༭
        setNoUIEdit(true);
    }
    /**
     * �õ�����ߴ�
     * @return DRectangle
     */
    public DRectangle getComponentBounds()
    {
        DRectangle rt = getBounds();
        DScrollBar scrollBar = getScrollBar();
        if(scrollBar == null)
            return super.getComponentBounds();
        DRectangle r = scrollBar.getComponentBounds();
        switch(getIndex())
        {
        case 1:
            if(scrollBar.getOrientation() == DScrollBar.VERTICAL)
                return new DRectangle(1,0,r.getWidth() - 1,r.getHeight() > 17 * 2?17:r.getHeight() / 2);
            return new DRectangle(0,1,r.getWidth() > 17 * 2?17:r.getWidth() / 2,r.getHeight() - 1);
        case 2:
            if(scrollBar.getOrientation() == DScrollBar.VERTICAL)
            {
                int h = r.getHeight() > 17 * 2?17:r.getHeight() / 2;
                return new DRectangle(1, r.getHeight() - h, r.getWidth() - 1,h);
            }
            int w = r.getWidth() > 17 * 2?17:r.getWidth() / 2;
            return new DRectangle(r.getWidth() - w, 1,w,r.getHeight() - 1);
        case 3:
            if(scrollBar.getOrientation() == DScrollBar.VERTICAL)
                return new DRectangle(1, rt.getY(), r.getWidth() - 1,rt.getHeight());
            return new DRectangle(rt.getX(), 1,rt.getWidth(),r.getHeight() - 1);
        }
        return super.getComponentBounds();
    }
    /**
     * ��������
     * @param type int
     */
    public void setType(int type)
    {
        if(type == 0)
        {
            attribute.remove("D_type");
            return;
        }
        attribute.put("D_type",type);
    }
    /**
     * �õ�����
     * @return int
     */
    public int getType()
    {
        DScrollBar scrollBar = getScrollBar();
        if(scrollBar != null)
        {
            switch(getIndex())
            {
            case 1:
                return scrollBar.getOrientation() == DScrollBar.VERTICAL?1:3;
            case 2:
                return scrollBar.getOrientation() == DScrollBar.VERTICAL?2:4;
            case 3:
                return scrollBar.getOrientation() == DScrollBar.VERTICAL?5:6;
            }
        }
        return TypeTool.getInt(attribute.get("D_type"));
    }
    /**
     * ����λ��
     * @param index int
     */
    public void setIndex(int index)
    {
        if(index == 0)
        {
            attribute.remove("D_index");
            return;
        }
        attribute.put("D_index",index);
        if(getScrollBar() == null || index != 3)
            return;
    }
    public int[] checkYRange()
    {
        if(getScrollBar() == null || getIndex() != 3 || getScrollBar().getOrientation() != DScrollBar.VERTICAL)
            return null;
        DRectangle r = getScrollBar().getComponentBounds();
        DRectangle rt = getComponentBounds();
        int h = r.getHeight() > 17 * 2?17:r.getHeight() / 2;
        if(h == 0)
            return null;
        return new int[]{h,r.getHeight() - h - rt.getHeight()};
    }
    public int[] checkXRange()
    {
        if(getScrollBar() == null || getIndex() != 3 || getScrollBar().getOrientation() != DScrollBar.HORIZONTAL)
            return null;
        DRectangle r = getScrollBar().getComponentBounds();
        DRectangle rt = getComponentBounds();
        int w = r.getWidth() > 17 * 2?17:r.getWidth() / 2;
        if(w == 0)
            return null;
        return new int[]{w,r.getWidth() - w - rt.getWidth()};
    }
    /**
     * �õ�λ��
     * @return int
     */
    public int getIndex()
    {
        return TypeTool.getInt(attribute.get("D_index"));
    }
    /**
     * ���ù�����
     * @param scrollBar DScrollBar
     */
    public void setScrollBar(DScrollBar scrollBar)
    {
        if(scrollBar == null)
        {
            attribute.remove("D_scrollBar");
            return;
        }
        attribute.put("D_scrollBar",scrollBar);
    }
    /**
     * �õ�������
     * @return DScrollBar
     */
    public DScrollBar getScrollBar()
    {
        return (DScrollBar)attribute.get("D_scrollBar");
    }
    /**
     * λ�øı�
     * @return boolean
     */
    public boolean onComponentMoved()
    {
        super.onComponentMoved();
        if(getScrollBar() == null || getIndex() != 3)
            return false;
        //֪ͨ������
        getScrollBar().movedBlock();
        return true;
    }
    /**
     * �������
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        if(getIndex() != 3)
            //���������߳�
            startThread();
        return false;
    }
    /**
     * ���̧��
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        super.onMouseLeftReleased();
        //ֹͣ�����߳�
        stopThread();
        return false;
    }
    /**
     * �����߳�
     */
    private ClickedThread clickedThread;
    /**
     * ���������߳�
     */
    private void startThread()
    {
        if(clickedThread != null)
            return;
        clickedThread = new ClickedThread();
        clickedThread.start();
    }
    /**
     * ֹͣ�����߳�
     */
    private void stopThread()
    {
        clickedThread = null;
    }
    class ClickedThread extends Thread
    {
        public void run()
        {
            if(getScrollBar() == null)
            {
                clickedThread = null;
                return;
            }
            getScrollBar().blockClicked(getIndex());
            try{
                sleep(500);
            }catch(Exception e)
            {
            }
            while(clickedThread != null)
            {
                if(getScrollBar() == null)
                {
                    clickedThread = null;
                    return;
                }
                getScrollBar().blockClicked(getIndex());
                try{
                    sleep(100);
                }catch(Exception e)
                {
                }
            }
        }
    }
    /**
     * ����Ĭ�ϱ߿�ߴ�
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(2,3,2,3);
    }
    /**
     * ���ư�ť�߿�
     * @param g Graphics
     */
    public void paintButtonBorder(Graphics g)
    {
        DRectangle r = getComponentBounds();
        if(getIndex() == 3)
        {
            DBorder.paintScrollBarButtonB(this, g, getButtonBorderDrawState());
            TDrawTool.drawV1(r,getScrollBar() == null?0:getScrollBar().getOrientation(),
                             getButtonBorderDrawState() == 3?1:0,g);
        }
        else
        {
            DBorder.paintScrollBarButton(this, g, getButtonBorderDrawState());
            TDrawTool.drawV(r.getWidth() / 2 - 1,r.getHeight() / 2,getType(),g);
        }
    }
    /**
     * �ܹ��õ�����
     * @return boolean
     */
    public boolean canFocus()
    {
        return false;
    }
}
