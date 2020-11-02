package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: ��������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.7
 * @version 1.0
 */
public abstract class EPanelBase implements EComponent
{
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ����
     */
    private EComponent parent;
    /**
     * �ַ�����
     */
    private DString dString;
    /**
     * ������
     */
    private int x;
    /**
     * ������
     */
    private int y;
    /**
     * ���
     */
    private int width;
    /**
     * �߶�
     */
    private int height;
    /**
     * �޸�״̬
     */
    private boolean modify;
    /**
     * ��һҳ�������
     */
    private EPanel nextPageLinkPanel;
    /**
     * ��һҳ�������
     */
    private EPanel previousPageLinkPanel;
    /**
     * ��һ�������������(����ר��)
     */
    private int previousPanelIndex;
    /**
     * ��һ���������
     */
    private EPanel previousLinkPanel;
    /**
     * ��һ���������
     */
    private EPanel nextLinkPanel;
    /**
     * ����λ��
     * 0 left
     * 1 center
     * 2 right
     */
    private int alignment = 0;
    /**
     * ��ǰ
     */
    private int paragraphForward = 0;
    /**
     * �κ�
     */
    private int paragraphAfter = 0;
    /**
     * ���
     */
    private int spaceBetween = 0;
    /**
     * ���߶�
     */
    private int maxHeight = -1;
    /**
     * ����
     * 0 �ı�
     * 1 ���
     */
    private int type;
    /**
     * �������
     */
    private int retractLeft;
    /**
     * �Ҳ�����
     */
    private int retractRight;
    /**
     * ��������
     * 0 ��
     * 1 ��������
     * 2 ��������
     */
    private int retractType;
    /**
     * �������
     */
    private int retractWidth;
    /**
     * ֻ����Ԫ�ر༭
     */
    private boolean elementEdit;
    /**
     * �Ա���ʾ
     */
    private int sexControl = 0;

    /**
     * ������
     */
    public EPanelBase()
    {
        components = new TList();
    }
    /**
     * ���ӳ�Ա
     * @param block IBlock
     */
    public void add(IBlock block)
    {
        if(block == null)
            return;
        components.add(block);
        block.setParent(this);
    }
    /**
     * ���ӳ�Ա
     * @param index int
     * @param block IBlock
     */
    public void add(int index,IBlock block)
    {
        if(block == null)
            return;
        components.add(index,block);
        block.setParent(this);
    }
    /**
     * ����λ��
     * @param block IBlock
     * @return int
     */
    public int indexOf(IBlock block)
    {
        if(block == null)
            return -1;
        return components.indexOf(block);
    }
    /**
     * ɾ����Ա
     * @param block IBlock
     */
    public void remove(IBlock block)
    {
        components.remove(block);
    }
    /**
     * ɾ����Ա
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
    }
    /**
     * ɾ��ȫ��
     */
    public void removeAll()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            IBlock b = get(i);
            if(b instanceof EMacroroutine)
                ((EMacroroutine)b).free();
        }
        components.removeAll();
    }
    /**
     * ��Ա����
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * �õ���Ա
     * @param index int
     * @return EComponent
     */
    public IBlock get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (IBlock)components.get(index);
    }
    /**
     * �õ���Ա�б�
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * ���ø���
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getParent()
    {
        return parent;
    }
    /**
     * �õ�����TD
     * @return ETD
     */
    public ETD getParentTD()
    {
        return (ETD)parent;
    }
    /**
     * �õ�����ҳ��
     * @return EPage
     */
    public EPage getParentPage()
    {
        return (EPage)parent;
    }
    /**
     * �����ַ�����
     * @param dString DString
     */
    public void setDString(DString dString)
    {
        this.dString = dString;
    }
    /**
     * �õ��ַ�����
     * @return DString
     */
    public DString getDString()
    {
        return dString;
    }
    /**
     * ���ú�����
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * ����������
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * ���ñ߿�
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
     * ����λ��
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        setX(x);
        setY(y);
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        if(this.width == width)
            return;
        this.width = width;
        setModify(true);
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * �����Ƿ��޸�״̬
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
        for(int i = 0;i < size();i++)
        {
            EComponent com = get(i);
            if(com == null)
                continue;
            if(com.isModify())
                return;
        }
        this.modify = false;
        if(getParent() != null)
            getParent().setModify(false);
    }
    /**
     * �Ƿ��޸�
     * @return boolean
     */
    public boolean isModify()
    {
        return modify;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getPage().getPM();
    }
    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPage().getPageManager();
    }
    /**
     * �õ��ַ����ݹ�����
     * @return MString
     */
    public MString getStringManager()
    {
        return getPage().getStringManager();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * �õ���ʾ������
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * �õ���������
     * @return MPanel
     */
    public MPanel getSavePanelManager()
    {
        return getPM().getFileManager().getPanelManager();
    }
    /**
     * ���ý���
     * @param text EText
     */
    public void setFocus(EText text)
    {
        getFocusManager().setFocusText(text);
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getFocus()
    {
        return getFocusManager().getFocus();
    }
    /**
     * ���ý���λ��
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * �õ�����λ��
     * @return int
     */
    public int getFocusIndex()
    {
        return getFocusManager().getFocusIndex();
    }
    /**
     * ������һҳ�������
     * @param panel EPanel
     */
    public void setPreviousPageLinkPanel(EPanel panel)
    {
        previousPageLinkPanel = panel;
    }
    /**
     * �õ���һҳ�������
     * @return EPanel
     */
    public EPanel getPreviousPageLinkPanel()
    {
        return previousPageLinkPanel;
    }
    /**
     * ������һҳ�������
     * @param panel EPanel
     */
    public void setNextPageLinkPanel(EPanel panel)
    {
        nextPageLinkPanel = panel;
    }
    /**
     * �õ���һҳ�������
     * @return EPanel
     */
    public EPanel getNextPageLinkPanel()
    {
        return nextPageLinkPanel;
    }
    /**
     * ������һ���������
     * @return boolean
     */
    public boolean hasNextPageLinkPanel()
    {
        return getNextPageLinkPanel() != null;
    }
    /**
     * ������һ���������
     * @return boolean
     */
    public boolean hasPreviousPageLinkPanel()
    {
        return getPreviousPageLinkPanel() != null;
    }
    /**
     * ������һ��������������(����ר��)
     * @param index int
     */
    public void setPreviousPanelIndex(int index)
    {
        previousPanelIndex = index;
    }
    /**
     * �õ���һ��������������(����ר��)
     * @return int
     */
    public int getPreviousPanelIndex()
    {
        return previousPanelIndex;
    }
    /**
     * ������һ���������
     * @return boolean
     */
    public boolean hasPreviousLinkPanel()
    {
        return getPreviousLinkPanel() != null;
    }
    /**
     * ������һ���������
     * @param previousLinkPanel EPanel
     */
    public void setPreviousLinkPanel(EPanel previousLinkPanel)
    {
        this.previousLinkPanel = previousLinkPanel;
    }
    /**
     * �õ���һ���������
     * @return EPanel
     */
    public EPanel getPreviousLinkPanel()
    {
        return previousLinkPanel;
    }
    /**
     * ������һ���������
     * @return boolean
     */
    public boolean hasNextLinkPanel()
    {
        return getNextLinkPanel() != null;
    }
    /**
     * ������һ���������
     * @param nextLinkPanel EPanel
     */
    public void setNextLinkPanel(EPanel nextLinkPanel)
    {
        this.nextLinkPanel = nextLinkPanel;
    }
    /**
     * �õ���һ���������
     * @return EPanel
     */
    public EPanel getNextLinkPanel()
    {
        return nextLinkPanel;
    }
    /**
     * ������ҳ��
     * @return boolean
     */
    public boolean parentIsPage()
    {
        return getParent() instanceof EPage;
    }
    /**
     * �����Ǳ��
     * @return boolean
     */
    public boolean parentIsTD()
    {
        return getParent() instanceof ETD;
    }
    /**
     * ���ö���λ��(��ǰλ��)
     * @param alignment int
     * 0 left
     * 1 center
     * 2 right
     */
    public void setAlignment(int alignment)
    {
        if(this.alignment == alignment)
            return;
        this.alignment = alignment;
        if(getParent() instanceof ETD)
            ((ETD)getParent()).setAlignment(alignment);
        setModify(true);
    }
    /**
     * �õ�����λ��
     * @return int
     * 0 left
     * 1 center
     * 2 right
     */
    public int getAlignment()
    {
        return alignment;
    }
    /**
     * ���ö�ǰ
     * @param paragraphForward int
     */
    public void setParagraphForward(int paragraphForward)
    {
        if(this.paragraphForward == paragraphForward)
            return;
        this.paragraphForward = paragraphForward;
        setModify(true);
    }
    /**
     * �õ���ǰ
     * @return int
     */
    public int getParagraphForward()
    {
        return paragraphForward;
    }
    /**
     * ���öκ�
     * @param paragraphAfter int
     */
    public void setParagraphAfter(int paragraphAfter)
    {
        if(this.paragraphAfter == paragraphAfter)
            return;
        this.paragraphAfter = paragraphAfter;
        setModify(true);
    }
    /**
     * �õ��κ�
     * @return int
     */
    public int getParagraphAfter()
    {
        return paragraphAfter;
    }
    /**
     * ���ü��
     * @param spaceBetween int
     */
    public void setSpaceBetween(int spaceBetween)
    {
        if(this.spaceBetween == spaceBetween)
            return;
        this.spaceBetween = spaceBetween;
        setModify(true);
    }
    /**
     * �õ����
     * @return int
     */
    public int getSpaceBetween()
    {
        return spaceBetween;
    }
    /**
     * �õ�ҳ��
     * @return EPage
     */
    public EPage getPage()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPage)
            return (EPage)component;
        if(component instanceof EPanel)
            return ((EPanel) component).getPage();
        if(component instanceof ETD)
            return ((ETD)component).getPage();
        return null;
    }
    /**
     * �õ����ű���
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }
    /**
     * �������߶�
     * @param maxHeight int
     */
    public void setMaxHeight(int maxHeight)
    {
        this.maxHeight = maxHeight;
    }
    /**
     * �õ����߶�
     * @return int
     */
    public int getMaxHeight()
    {
        return maxHeight;
    }
    /**
     * ��������
     * @param type int
     * 0 �ı�
     * 1 ���
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return int
     * 0 �ı�
     * 1 ���
     */
    public int getType()
    {
        return type;
    }
    /**
     * �����������
     * @param retractLeft int
     */
    public void setRetractLeft(int retractLeft)
    {
        this.retractLeft = retractLeft;
    }
    /**
     * �õ��������
     * @return int
     */
    public int getRetractLeft()
    {
        return retractLeft;
    }
    /**
     * �����Ҳ�����
     * @param retractRight int
     */
    public void setRetractRight(int retractRight)
    {
        this.retractRight = retractRight;
    }
    /**
     * �õ��Ҳ�����
     * @return int
     */
    public int getRetractRight()
    {
        return retractRight;
    }
    /**
     * ������������
     * @param retractType int
     * 0 ��
     * 1 ��������
     * 2 ��������
     */
    public void setRetractType(int retractType)
    {
        this.retractType = retractType;
    }
    /**
     * �õ���������
     * @return int
     * 0 ��
     * 1 ��������
     * 2 ��������
     */
    public int getRetractType()
    {
        return retractType;
    }
    /**
     * �����������
     * @param retractWidth int
     */
    public void setRetractWidth(int retractWidth)
    {
        this.retractWidth = retractWidth;
    }
    /**
     * �õ��������
     * @return int
     */
    public int getRetractWidth()
    {
        return retractWidth;
    }
    /**
     * ����ֻ����Ԫ�ر༭
     * @param elementEdit boolean
     */
    public void setElementEdit(boolean elementEdit)
    {
        this.elementEdit = elementEdit;
    }
    /**
     * �õ�ֻ����Ԫ�ر༭
     * @return boolean
     */
    public boolean isElementEdit()
    {
        return elementEdit;
    }
    /**
     * �����Ա�����
     * @param sexControl int
     */
    public void setSexControl(int sexControl)
    {
        if(this.sexControl == sexControl)
            return;
        this.sexControl = sexControl;
        setModify(true);
    }
    /**
     * �õ��Ա�����
     * @return int
     */
    public int getSexControl()
    {
        return sexControl;
    }
}
