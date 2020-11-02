package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;
import com.dongyang.util.TList;

/**
 *
 * <p>Title: ��������������</p>
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
     * �������
     */
    private EPanel panel;
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
     * ������λ��
     */
    private int y0;
    /**
     * �ߴ緶Χ
     */
    private DRectangle rectangle;
    /**
     * ��ǰ���
     */
    private IBlock block;
    /**
     * �ж���
     */
    private TList rows;
    /**
     * ����
     */
    private int rowCount;
    /**
     * ������
     * @param panel EPanel
     */
    public ResetBlock(EPanel panel)
    {
        //�������
        setPanel(panel);
    }
    /**
     * �������
     * @param panel EPanel
     */
    public void setPanel(EPanel panel)
    {
        this.panel = panel;
    }
    /**
     * �õ����
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return panel;
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
     * ����
     */
    public void clear()
    {
        //��������
        rowCount = 0;
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
     * ���õ�ǰ���
     * @param block IBlock
     */
    public void setBlock(IBlock block)
    {
        this.block = block;
    }
    /**
     * �õ���ǰ���
     * @return IBlock
     */
    public IBlock getBlock()
    {
        return block;
    }
    /**
     * �õ���ǰ���ı�����
     * @return EText
     */
    public EText getText()
    {
        return(EText)getBlock();
    }
    /**
     * �õ���ǰ��EPic
     * @return EPic
     */
    public EPic getPic()
    {
        return (EPic)getBlock();
    }
    /**
     * �õ���ǰ��EImage
     * @return EImage
     */
    public EImage getImage()
    {
        return (EImage)getBlock();
    }
    /**
     * �õ�����
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
     * �������
     * @param block IBlock
     */
    public void add(IBlock block)
    {
        rows.add(block);
    }
    /**
     * ������б�
     */
    public void clearRow()
    {
        rows = new TList();
    }
    /**
     * �õ����
     * @param index int
     * @return IBlock
     */
    public IBlock get(int index)
    {
        return (IBlock)rows.get(index);
    }
    /**
     * �õ��������
     * @return int
     */
    public int size()
    {
        return rows.size();
    }
    /**
     * �õ�ҳ����
     * @return int
     * 0 ��ͨ
     * 1 ҳ��
     */
    public int getViewStyle()
    {
        return getPanel().getViewManager().getViewStyle();
    }
    /**
     * �����ߴ�
     */
    public void reset()
    {
        switch(getPanel().getType())
        {
        case 0://�������
            resetBase();
            break;
        case 1://������
            resetTable();
            break;
        }
    }
    /**
     * ������ͨ����
     */
    public void resetBase()
    {
        start();
        //������λ�ù���
        x0 = getRectangle().getX();
        //������λ�ù���
        y0 = getRectangle().getY() + getPanel().getSpaceBetween();
        if(isHeadRow())
            y0 += getPanel().getParagraphForward();
        //������б�
        clearRow();
        //ɨ��
        while(isDoing())
        {
            switch(getState())
            {
            case 0://����ɨ��ҳ��
                resetBlock_0();
                break;
            case 1://����ҳ�����������
                resetBlock_1();
                break;
            }
        }
    }
    /**
     * �������
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
     * ����ɨ��ҳ��
     */
    public void resetBlock_0()
    {
        if(index >= getPanel().size())
        {
            setState(1);
            return;
        }
        //���õ�ǰ���
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
            //����Pic
            resetPic();
            return;
        case 4:
            //����Image
            resetImage();
            return;
        }
        index ++;
    }
    /**
     * ����Pic
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
                //ȫ���Ų���
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
     * ����Image
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
                //ȫ���Ų���
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
     * �Ƿ�������
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
     * �õ������������
     * @return int
     */
    public int getRowRetractWidth()
    {
        switch(getPanel().getRetractType())
        {
        case 0://��
            return 0;
        case 1://��������
            if(isHeadRow())
                return getPanel().getRetractWidth();
            return 0;
        case 2://��������
            if(isHeadRow())
                return 0;
            return getPanel().getRetractWidth();
        }
        return 0;
    }
    /**
     * ����EText
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
        case -1://�Ų���
            if(index == 0 || y0 == getRectangle().getY())
            {
                //ȫ���Ų���
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
        case 0://�ŵ���
            x0 += text.getWidth();
            add(text);
            index ++;
            return;
        case 1://�ŵ���һ��
            if(size() > 0)
                resetRow();
            return;
        case 2://ĩβ
            add(text);
            resetRow();
            index ++;
            return;
        }
    }
    /**
     * ����ҳ�����������
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
                         //�Ӷκ�
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
     * �õ�ʹ�õĿ��
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
     * �õ�ʹ�õĸ߶�
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
     * ����������㷨
     * @return int
     */
    private int getAlignementLeftX()
    {
        int x = getPanel().getRetractLeft();
        //������������
        switch(getPanel().getRetractType())
        {
        case 1://��������
            if(isHeadRow())
                x += getPanel().getRetractWidth();
            break;
        case 2://��������
            if(isHeadRow())
                break;
            x += getPanel().getRetractWidth();
            break;
        }
        return x;
    }
    /**
     * ���к������㷨
     * @return int
     */
    private int getAlignementCenterX()
    {
        int x = 0;
        //�õ�ʹ�õĿ��
        int w = getUsingWidth();
        //������������
        switch(getPanel().getRetractType())
        {
        case 0: //��
            x = (int) ((double) (getRectangle().getWidth() -
                                 getPanel().getRetractLeft() -
                                 getPanel().getRetractRight() -
                                 w) / 2.0);
            break;
        case 1://��������
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
        case 2://��������
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
     * ���Һ������㷨
     * @return int
     */
    private int getAlignementRightX()
    {
        //�õ�ʹ�õĿ��
        int w = getUsingWidth();
        return getRectangle().getWidth() - w - getPanel().getRetractRight();
    }
    /**
     * �������㷨
     * @return int
     */
    private int getAlignementX()
    {
        switch(getPanel().getAlignment())
        {
        case 0://����
            return getAlignementLeftX();
        case 1://����
            return getAlignementCenterX();
        case 2://����
            return getAlignementRightX();
        }
        return getPanel().getRetractLeft();
    }
    /**
     * ����һ������
     */
    public void resetRow()
    {
        //�õ�ʹ�õĿ��
        int w = getUsingWidth();
        //�õ�ʹ�õĸ߶�
        int h = getUsingHeight();
        //������������ʼλ��
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
        //������β���
        IBlock blockHead = get(0);
        blockHead.setPosition(1);
        IBlock blockEnd = get(size() - 1);
        if(blockHead == blockEnd)
            blockEnd.setPosition(3);
        else
            blockEnd.setPosition(2);

        //��������
        x0 = getRectangle().getX();
        y0 += h + getPanel().getSpaceBetween();
        //�����
        clearRow();
        rowCount ++;
        if(getViewStyle() == 0)
            getPanel().setWidth(w);
    }
    /**
     * �ָ�
     */
    public void separate()
    {
        if(getPanel().parentIsPage())
            separatePage();
        else if(getPanel().parentIsTD())
            separateTD();
    }
    /**
     * �ָ�ҳ��
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
            //��������
            nextPanel.setPreviousPageLinkPanel(getPanel());
            getPanel().setNextPageLinkPanel(nextPanel);
            //��������������
            getPanel().setLinkPanelParameter(nextPanel);
        }
        //���ָ���ģ��ᵽ��һ�������
        for(int i = getPanel().size() - 1;i >= goindex;i--)
        {
            IBlock block = getPanel().get(i);
            if(!(block instanceof EText))
                nextPanel.add(block);
            else
                nextPanel.insertText((EText)block);
            getPanel().remove(block);
        }
        //���ָ����������ᵽ��һ��ҳ����
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
     * �ָ�TD
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
            //��������
            nextPanel.setPreviousLinkPanel(getPanel());
            getPanel().setNextLinkPanel(nextPanel);
            //��������������
            getPanel().setLinkPanelParameter(nextPanel);
        }
        //���ָ���ģ��ᵽ��һ�������
        for(int i = getPanel().size() - 1;i >= goindex;i--)
        {
            IBlock block = getPanel().get(i);
            if(!(block instanceof EText))
                nextPanel.add(block);
            else
                nextPanel.insertText((EText)block);
            getPanel().remove(block);
        }
        //���ָ����������ᵽ��һ��TD��
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
