package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.io.IOException;

/**
 *
 * <p>Title: ������ʾ���</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.10.29
 * @version 1.0
 */
public class EInputText extends EFixed
{
    /**
     * ������
     * @param panel EPanel
     */
    public EInputText(EPanel panel)
    {
        super(panel);
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        super.setText("{" + text + "}");
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        String text = super.getText();
        return text.substring(1,text.length() - 1);
    }
    /**
     * �õ��¶���
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        return getPanel().newInputText(index);
    }
    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return "Parent:Null";
        return panel.getIndexString() + ",EInputText:" + findIndex();
    }
    public String toString()
    {
        return "<EInputText name=" + getName() +
                ",start=" + getStart() +
                ",end=" + getEnd() +
                ",s=" + getString() +
                ",position=" + getPosition() +
                ",index=" + getIndexString() +
                ",p=" + (getPreviousFixed() != null?"[" + getPreviousFixed().getIndexString() + "]":"null") +
                ",n=" + (getNextFixed() != null?"[" + getNextFixed().getIndexString() + "]":"null") + ">";
    }
    /**
     * ���ƹ̶��ı����㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFixedFocusBack(Graphics g,int x,int y,int width,int height)
    {
        super.paintFixedFocusBack(g,x,y,width,height);
        //if(getNextFixed() == null)
        //    paintComboFlg(g,x + width - 2,y + height);
    }
    /**
     * ������������
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void paintComboFlg(Graphics g,int x,int y)
    {
        g.setColor(new Color(255,0,0));
        g.drawLine(x - 2,y,x + 2,y);
        g.drawLine(x - 1,y + 1,x + 1,y + 1);
        g.drawLine(x - 1,y + 2,x + 1,y + 2);
        g.drawLine(x,y + 3,x,y + 3);
    }
    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        return false;
    }
    /**
     * ���ԶԻ���
     */
    public void openPropertyDialog()
    {
        EFixed fixed = getHeadFixed();
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\InputTextEdit.x",fixed);
        if(value == null || !"OK".equals(value))
            return;
        fixed.getPM().getFileManager().update();
    }
    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        super.writeObjectAttribute(s);
    }
    /**
     * ����������
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        if (super.readObjectAttribute(id, s))
            return true;
        switch (id)
        {
        case 100:
            return true;
        }
        return false;
    }
    /**
     * �̶��ı������ܷ�༭
     * @param index int
     * @param type int
     *  1 �����ַ�
     *  2 ��ǰɾ��
     *  3 ���ɾ��
     * @return boolean true �ܱ༭ false ���ܱ༭
     */
    public boolean canEdit(int index,int type)
    {
        switch(type)
        {
        case 1:
            return true;
        case 2:
            if(index == 1 || index == getLength())
                return false;
            return true;
        case 3:
            if(index == 0 || index == getLength() - 1)
                return false;
            return true;
        }
        return false;
    }
    /**
     * �õ���������
     * @return int
     */
    public int getObjectType()
    {
        return INPUT_TEXT_TYPE;
    }
    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        if(action.getType() == EAction.PREVIEW_STATE ||
           action.getType() == EAction.EDIT_STATE)
        {
            setModify(true);
            return;
        }
    }
    /**
     * �õ���ʾ�ִ�
     * @return String
     */
    public String getShowString()
    {
        if(!isPreview())
            return super.getShowString();
        String s = getString();
        if(getPreviousFixed() == null)
            s = s.substring(1);
        if(getNextFixed() == null)
            s = s.substring(0, s.length() - 1);
        return s;
    }
    /**
     * �Ƿ���Tab����
     * @return boolean
     */
    public boolean isTabFocus()
    {
        return true;
    }
    /**
     * ����Tab����
     */
    public void setTabFocus()
    {
        setFocus(1);
    }
    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        EInputText inputText = new EInputText(panel);
        inputText.setStart(panel.getDString().size());
        inputText.setEnd(getStart());
        inputText.setString(getString());
        inputText.setKeyIndex(getKeyIndex());
        inputText.setDeleteFlg(isDeleteFlg());
        inputText.setStyleOther(getStyle());
        inputText.setName(getName());
        inputText.setGroupName(getGroupName());
        inputText.setMicroName(getMicroName());
        inputText.setDataElements(isIsDataElements());
        inputText.setAllowNull(isAllowNull());
        return inputText;
    }
}
