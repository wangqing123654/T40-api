package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.util.Vector;
import java.io.IOException;

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
 * @author lzk 2009.9.1
 * @version 1.0
 */
public class EMicroField extends EFixed
{
    /**
     * ��ֵ����
     */
    String code = "";

    /**
     * ������
     * @param panel EPanel
     */
    public EMicroField(EPanel panel)
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
        if(text.length() < 2)
            return "";
        return text.substring(1,text.length() - 1);
    }
    /**
     * �õ��¶���
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        return getPanel().newMicroField(index);
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
        return panel.getIndexString() + ",EHasChoose:" + findIndex();
    }
    public String toString()
    {
        return "<EHasChoose name=" + getName() + ",start=" + getStart() + ",end=" + getEnd() + ",s=" + getString() + ",position=" + getPosition() + ",index=" + getIndexString() +
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
        if(getNextFixed() == null)
            paintComboFlg(g,x + width - 2,y + height);
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
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\MicroFieldEdit.x",fixed);
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
        s.writeString(100,getCode(),"");

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
            setCode(s.readString());
            return true;
        }
        return false;
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
        if(action.getType() != EAction.SET_MICRO_FIELD)
            return;
        if(getName() == null || getName().length() == 0)
            return;
        if(getPreviousFixed() != null)
            return;
        String value = getPM().getMicroFieldManager().getMicroField(getMicroName());
        if(value == null)
            value = "";
        if(!value.equals(getText()))
            setText(value);
        String code = getPM().getMicroFieldManager().getMicroFieldCode(getMicroName());
        if(code == null)
            code = "";
        if(!code.equals(getCode()))
            setCode(code);
    }
    /**
     * �õ���������
     * @return int
     */
    public int getObjectType()
    {
        return MICRO_FIELD_TYPE;
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
            s = (s.length() > 1?s.substring(1):"");
        if(getNextFixed() == null)
            s = (s.length() > 0?(s.substring(0,s.length() - 1)):"");
        return s;
    }
    /**
     * ���ú�ֵ����
     * @param code String
     */
    public void setCode(String code){
        this.code = code;
    }
    /**
     * �õ���ֵ����
     * @return String
     */
    public String getCode(){
        return code;
    }

    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        EMicroField microField = new EMicroField(panel);
        microField.setStart(panel.getDString().size());
        microField.setEnd(getStart());
        microField.setString(getString());
        microField.setKeyIndex(getKeyIndex());
        microField.setDeleteFlg(isDeleteFlg());
        microField.setStyleOther(getStyle());
        microField.setName(getName());
        microField.setCode(getCode());
        //
        microField.setGroupName(getGroupName());
        microField.setMicroName(getMicroName());
        microField.setDataElements(isIsDataElements());
        microField.setAllowNull(isAllowNull());
        return microField;
    }
}
