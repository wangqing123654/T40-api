package com.dongyang.tui.text;

import java.util.List;

import com.dongyang.ui.TWord;

/**
 *
 * <p>Title: �ı�Ԫ�ػ�����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.22
 * @author whao 2013
 * @version 1.0
 */
public abstract class ETextBase implements IBlock
{
    /**
     * ����
     */
    private EComponent parent;
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
     * ���
     */
    private DStyle style;
    /**
     * �޸�״̬
     */
    private boolean modify;
    /**
     * ������ʾλ��
     */
    private int start;
    /**
     * ���ý���λ��
     */
    private int end;
    /**
     * ��һ������Text
     */
    private EText previousLinkText;
    /**
     * ��һ������Text
     */
    private EText nextLinkText;
    /**
     * ��һ��Text��������(����ר��)
     */
    private int previousTextIndex;
    /**
     * λ��
     * 0 �м�
     * 1 ����
     * 2 ��β
     */
    private int position;
    /**
     * �����
     */
    private int maxWidth;
    /**
     * ���߶�
     */
    private int maxHeight;
    /**
     * ǩ�ֱ��
     */
    private int keyIndex = -1;
    /**
     * ɾ�����
     */
    private boolean isDeleteFlg;
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
     * �����Լ���λ��
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
    }
    /**
     * �������λ��
     * @param com EComponent
     * @return int
     */
    public int findIndex(IBlock com)
    {
        return getPanel().indexOf(com);
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
        this.width = width;
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
     * ���÷��
     * @param style DStyle
     */
    public void setStyle(DStyle style)
    {
        if(this.style == style)
            return;
        this.style = style;
        setModify(true);
    }
    /**
     * ���������ķ��
     * @param style DStyle
     */
    public void setStyleOther(DStyle style)
    {
        setStyle(getStyleManager().getStyle(style.copy()));
    }
    /**
     * ���÷��
     * @param index int
     */
    public void setStyle(int index)
    {
        setStyle(getStyleManager().get(index));
    }
    /**
     * �õ����
     * @return DStyle
     */
    public DStyle getStyle()
    {
    	//
    	if(style==null){
    		MStyle style = getStyleManager();
            if (style == null) {
                return null;
            }
            return style.get("default");
    	}
    	//
        return style;
    }
    /**
     * �õ����
     * @return EPanel
     */
    public EPanel getPanel()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPanel)
            return (EPanel)component;
        return null;
    }
    /**
     * �õ��ַ�����
     * @return DString
     */
    public DString getDString()
    {
        return getPanel().getDString();
    }
    /**
     * �õ���ʾ�ַ�
     * @return String
     */
    public String getString()
    {
        DString s = getDString();
        if(s == null)
            return "";
        return s.getString(getStart(),getEnd());
    }
    /**
     * �õ�ָ��λ�õ��ַ���
     * @param index int
     * @return String
     */
    public String getString(int index)
    {
        DString s = getDString();
        if(s == null)
            return "";
        return s.getString(getStart(),getStart() + index);
    }
    /**
     * �õ�ָ��λ�õ��ַ���
     * @param start int
     * @param end int
     * @return String
     */
    public String getString(int start,int end)
    {
        DString s = getDString();
        if(s == null)
            return "";
        return s.getString(start,end);
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return null;
        return panel.getPM();
    }
    /**
     * �õ���������
     * @return MStyle
     */
    public MStyle getStyleManager()
    {
        PM pm = getPM();
        if(pm == null)
            return null;
        return pm.getStyleManager();
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
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPM().getPageManager();
    }
    /**
     * �õ��¼�������
     * @return MEvent
     */
    public MEvent getEventManager()
    {
        return getPM().getEventManager();
    }
    /**
     * �õ��﷨������
     * @return MSyntax
     */
    public MSyntax getSyntaxManager()
    {
        return getPM().getSyntaxManager();
    }
    /**
     * �õ�Ttext������
     * @return MTextSave
     */
    public MTextSave getTextSaveManager()
    {
        return getPM().getFileManager().getTextSaveManager();
    }
    /**
     * �õ��޸ļ�¼������
     * @return MModifyNode
     */
    public MModifyNode getModifyNodeManager()
    {
        return getPM().getModifyNodeManager();
    }
    /**
     * �õ���ǰ�޸ļ�¼���
     * @return int
     */
    public int getModifyNodeIndex()
    {
        if( TWord.IsMark ){

        	return getModifyNodeManager().getIndex();
        }else{

            //̩��ר��
        	return -1;
        }
    }
    /**
     * �����Ƿ��޸�״̬
     * @param modify boolean
     */
    public void setModify(boolean modify)
    {
        if(this.modify == modify)
            return;
        this.modify = modify;
        if(getParent() != null)
            getParent().setModify(modify);

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
     * ���ÿ�ʼλ��
     * @param start int
     */
    public void setStart(int start)
    {
        if(this.start == start)
            return;
        this.start = start;
        setModify(true);
    }
    /**
     * �õ���ʼλ��
     * @return int
     */
    public int getStart()
    {
        return start;
    }
    /**
     * ���ý���λ��
     * @param end int
     */
    public void setEnd(int end)
    {
        if(this.end == end)
            return;
        this.end = end;
        setModify(true);
    }
    /**
     * �õ�����λ��
     * @return int
     */
    public int getEnd()
    {
        return end;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getLength()
    {
        return getEnd() - getStart();
    }
    /**
     * ����λ�÷�Χ
     * @param start int
     * @param end int
     */
    public void setBlock(int start,int end)
    {
        setStart(start);
        setEnd(end);
    }
    /**
     * ����λ�õ���
     * @param length int
     */
    public void indexMove(int length)
    {
        setStart(getStart() + length);
        setEnd(getEnd() + length);
    }
    /**
     * ������һ������Text
     * @param previousLinkText EText
     */
    public void setPreviousLinkText(EText previousLinkText)
    {
        this.previousLinkText = previousLinkText;
    }
    /**
     * �õ���һ������Text
     * @return EText
     */
    public EText getPreviousLinkText()
    {
        return previousLinkText;
    }
    /**
     * ������һ������Text
     * @param nextLinkText EText
     */
    public void setNextLinkText(EText nextLinkText)
    {
        this.nextLinkText = nextLinkText;
    }
    /**
     * �õ���һ������Text
     * @return EText
     */
    public EText getNextLinkText()
    {
        return nextLinkText;
    }
    /**
     * ������һ��Text����������(����ר��)
     * @param index int
     */
    public void setPreviousTextIndex(int index)
    {
        previousTextIndex = index;
    }
    /**
     * �õ���һ��Text����������(����ר��)
     * @return int
     */
    public int getPreviousTextIndex()
    {
        return previousTextIndex;
    }
    /**
     * ����λ��
     * @param position int
     */
    public void setPosition(int position)
    {
        this.position = position;
    }
    /**
     * ����λ��
     * @param position int
     */
    public void setPositionL(int position)
    {
        setPosition(position | getPosition());
    }
    /**
     * �õ�λ��
     * @return int
     */
    public int getPosition()
    {
        return position;
    }
    /**
     * �õ�λ��
     * @param position int
     * @return boolean
     */
    public boolean getPosition(int position)
    {
        return (getPosition() & position) == position;
    }
    /**
     * ���������
     * @param maxWidth int
     */
    public void setMaxWidth(int maxWidth)
    {
        this.maxWidth = maxWidth;
    }
    /**
     * �õ������
     * @return int
     */
    public int getMaxWidth()
    {
        return maxWidth;
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
     * ���ý���
     * @return boolean
     */
    public boolean setFocus()
    {
        return setFocus(0);
    }
    /**
     * �ƶ�����
     * @param index int
     * @return boolean
     */
    public boolean moveFocus(int index)
    {
        return setFocus(getFocusIndex() + index);
    }
    /**
     * ���ý���
     * @param index int ����λ��
     * @return boolean
     */
    public boolean setFocus(int index)
    {
        setFocus((EText)this,index);
        return true;
    }
    /**
     * ���ý��㵽���
     * @return boolean
     */
    public boolean setFocusLast()
    {
        setFocus((EText)this,getLength());
        return true;
    }
    /**
     * ���ý���
     * @param text EText
     * @param index int
     */
    public void setFocus(EText text,int index)
    {
        getFocusManager().setFocusText(text,index);
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
     * �õ����ű���
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }
    /**
     * �����༭
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return getPanel().isLockEdit();
    }
    /**
     * �ܷ�༭
     * @return boolean
     */
    public boolean canEdit()
    {
        if(!getEventManager().canEdit())
            return false;
        if(isLockEdit())
            return false;
        return true;
    }
    /**
     * �õ���������
     * @return int
     */
    public int getObjectType()
    {
        return TEXT_TYPE;
    }
    /**
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        if(getObjectType() == type)
            return this;
        return null;
    }
    /**
     * ���˶���
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type)
    {

    }
    /**
     * ��ѯ��
     * @param groupName String
     * @return EComponent
     */
    public EComponent findGroup(String groupName)
    {
        return null;
    }
    /**
     * �õ���ֵ
     * @param group String
     * @return String
     */
    public String findGroupValue(String group)
    {
        return null;
    }
    /**
     * �Ƿ�����״̬
     * @return boolean
     */
    public boolean isPreview()
    {
        return getViewManager().isPreview();
    }
    /**
     * �Ƿ�����϶�
     * @return boolean
     */
    public boolean isMouseDragged()
    {
        return getEventManager().isMouseDragged();
    }
    /**
     * ����ǩ�ֱ��
     * @param keyIndex int
     */
    public void setKeyIndex(int keyIndex)
    {
        this.keyIndex = keyIndex;
    }
    /**
     * �õ�ǩ�ֱ��
     * @return int
     */
    public int getKeyIndex()
    {
        if( TWord.IsMark ){

        	return keyIndex;
        }else{

            //̩��ר��
        	return -1;
        }
    }
    /**
     * ����ɾ�����
     * @param isDeleteFlg boolean
     */
    public void setDeleteFlg(boolean isDeleteFlg)
    {
        if( TWord.IsMark ){

        	this.isDeleteFlg = isDeleteFlg;
        }else{

            //̩��ר��
        	this.isDeleteFlg =false;
        }

    }
    /**
     * �Ƿ�ɾ��
     * @return boolean
     */
    public boolean isDeleteFlg()
    {
        return isDeleteFlg;
    }
}
