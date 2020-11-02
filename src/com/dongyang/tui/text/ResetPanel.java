package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;
import com.dongyang.tui.DInsets;

/**
 *
 * <p>Title: ������崦�����</p>
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
     * ����ҳ��
     */
    private EPage page;
    /**
     * ����ģ��
     */
    private ETD td;
    /**
     * ����
     * 1 Page
     * 2 TD
     */
    private int type;
    /**
     * ������
     */
    private int index;
    /**
     * �ߴ緶Χ
     */
    private DRectangle rectangle;
    /**
     * ��������
     */
    private boolean doing;
    /**
     * ״̬
     */
    private int state;
    /**
     * ��ǰ���
     */
    private EPanel panel;
    /**
     * ������λ��
     */
    private int y0;
    /**
     * ������
     * @param page EPage
     */
    public ResetPanel(EPage page)
    {
        //����ҳ��
        setPage(page);
        //��������
        setType(1);
    }

    /**
     * ������
     * @param td ETD
     */
    public ResetPanel(ETD td)
    {
        //����TD
        setTD(td);
        //��������
        setType(2);
    }

    /**
     * ����ҳ��
     * @param page EPage
     */
    public void setPage(EPage page)
    {
        this.page = page;
    }

    /**
     * �õ�ҳ��
     * @return EPage
     */
    public EPage getPage()
    {
        return page;
    }

    /**
     * ����TD
     * @param td ETD
     */
    public void setTD(ETD td)
    {
        this.td = td;
    }

    /**
     * �õ�TD
     * @return ETD
     */
    public ETD getTD()
    {
        return td;
    }

    /**
     * ��������
     * 1 Page
     * 2 TD
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * �õ�����
     * 1 Page
     * 2 TD
     * @return int
     */
    public int getType()
    {
        return type;
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
    public void setRectangle(int x, int y, int width, int height)
    {
        setRectangle(new DRectangle(x, y, width, height));
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
     * ���õ�ǰ���
     * @param panel EPanel
     */
    public void setPanel(EPanel panel)
    {
        this.panel = panel;
    }

    /**
     * �õ���ǰ���
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return panel;
    }
    /**
     * �õ�ҳ����
     * @return int
     * 0 ��ͨ
     * 1 ҳ��
     */
    public int getViewStyle()
    {
        if(getType() == 1)
            return getPage().getViewManager().getViewStyle();
        return getTD().getViewManager().getViewStyle();
    }
    /**
     * �����ߴ�
     */
    public void reset()
    {
        start();
        //������λ�ù���
        y0 = getRectangle().getY();
        //����ɨ��
        switch (getType())
        {
        case 1: //Page
            resetPage();
        case 2: //TD
            resetTD();
        }
    }

    /**
     * ҳ�����
     */
    private void resetPage()
    {
        //ɨ��
        while (isDoing())
        {
            switch (getState())
            {
            case 0: //����ɨ��ҳ��
                resetPage_0();
                break;
            case 1: //����ҳ�����������
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
     * ����ɨ��ҳ��
     */
    private void resetPage_0()
    {
        if (index >= getPage().size())
        {
            state = 1;
            return;
        }
        //���õ�ǰ���
        setPanel(getPage().get(index));
        //���Panel�ĺϷ���
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
        //�Ƿ�����
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
        //��ɾ��
        if(getPanel().getMaxHeight() == -2)
            return;
        //�Ų���
        if (getPanel().getMaxHeight() == -1)
        {
            if (index > 0)
                //��������ҳ
                copyToNextPage();
            stop();
            return;
        }
        if (getPanel().hasNextPageLinkPanel())
        {
            //�������м�������ҳ������������ҳ
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
     * ���Panel�ĺϷ���
     */
    private void checkPanel()
    {
        //�Ƿ����Table���
        int index = getPanel().findTableIndex();
        if(index > 0)
            getPanel().setType(1);
        //���Ǳ�񲻽��д���
        if(getPanel().getType() != 1)
            return;
        //���ֻ����һ��Table
        if(getPanel().size() == 1)
            return;
        //ɾ������
        for(int i = getPanel().size() - 1;i > index;i--)
        {
            IBlock block = getPanel().get(i);
            if(block instanceof EText)
                ((EText)block).removeThis();
        }
        //ɾ��ǰ��
        for(int i = index - 1;i >= 0;i--)
        {
            IBlock block = getPanel().get(i);
            if(block instanceof EText)
                ((EText)block).removeThis();
        }
        getPanel().getDString().removeAll();
    }
    /**
     * ��������ҳ
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
            //������һ��Panel(��������xxx)
            linkNextPanal(panel,1);
        }
        page.setModify(true);
    }

    /**
     * ������һ��Panel
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
     * �Ƿ���Ҫ����
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
     * ����ҳ�����������
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
        //�Ų���
        if (getPanel().getMaxHeight() == -1)
        {
            //��������ҳ
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
     * ģ�����
     */
    private void resetTD()
    {
        //ɨ��
        while (isDoing())
        {
            switch (getState())
            {
            case 0: //����ɨ��TD
                resetTD_0();
                break;
            case 1: //����ҳTD�����������
                resetTD_1();
                break;
            }
        }
    }

    /**
     * ����ɨ��
     */
    private void resetTD_0()
    {
        if (index >= getTD().size())
        {
            state = 1;
            return;
        }
        //���õ�ǰ���
        setPanel(getTD().get(index));
        getPanel().setX(getRectangle().getX());
        getPanel().setY(y0);
        getPanel().setWidth(getRectangle().getWidth());
        getPanel().setMaxHeight(getRectangle().getHeight() - y0 +
                                getRectangle().getY());
        getPanel().setModify(true);
        getPanel().reset();
        //��ɾ��
        if(getPanel().getMaxHeight() == -2)
            return;
        //�Ų���
        if (getPanel().getMaxHeight() == -1)
        {
            //��������ҳ
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
     * ��������һ��TD
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
     * ����ҳTD�����������
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
         //�Ų���
        if (getPanel().getMaxHeight() == -1)
        {
            //��������ҳ
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
