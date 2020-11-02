package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.io.DataOutputStream;


/**
 * 
 * ǩ���ؼ�
 * @author lix
 *
 */
public class ESign extends EFixed{
	
	/**
	 * ǩ���˹���
	 */
	String signCode;
	
	/**
	 * ǩ��ʱ���
	 */
	String timestmp;

	public ESign(EPanel panel) {
		super(panel);
	}	
	
	 /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        super.setText(text);
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        String text = super.getText();
     /*   if(text.length() < 2)
            return "";
        return text.substring(1,text.length() - 1);*/
        return text;
    }
    /**
     * �õ��¶���
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        return getPanel().newSign(index);
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
        return panel.getIndexString() + ",ESign:" + findIndex();
    }
    public String toString()
    {
        return "<ESign name=" + getName() + ",start=" + getStart() + ",end=" + getEnd() + ",s=" + getString() + ",position=" + getPosition() + ",index=" + getIndexString() +
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
	public void openPropertyDialog() {
		EFixed fixed = getHeadFixed();
		String value = (String) getPM().getUI().openDialog(
				"%ROOT%\\config\\database\\ESignEdit.x", fixed);
		if (value == null || !"OK".equals(value))
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
        s.writeString(100,this.getSignCode(),"");
        s.writeString(101,this.getTimestmp(),"");

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
	            setSignCode(s.readString());
	            return true;
	        case 101:
	        	setTimestmp(s.readString());
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
    }
    /**
     * �õ���������
     * @return int
     */
    public int getObjectType()
    {
        return SIGN_TYPE;
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
        /*if(getPreviousFixed() == null)
            s = (s.length() > 1?s.substring(1):"");
        if(getNextFixed() == null)
            s = (s.length() > 0?(s.substring(0,s.length() - 1)):"");*/
        return s;
    }

    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
    	ESign sign = new ESign(panel);
    	sign.setStart(panel.getDString().size());
    	sign.setEnd(getStart());
    	sign.setString(getString());
    	sign.setKeyIndex(getKeyIndex());
    	sign.setDeleteFlg(isDeleteFlg());
    	sign.setStyleOther(getStyle());
    	sign.setName(getName());
    	sign.setGroupName(getGroupName());
    	sign.setText(this.getText());
    	sign.setDataElements(isIsDataElements());
    	sign.setAllowNull(isAllowNull());
        return sign;
    }

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public String getTimestmp() {
		return timestmp;
	}

	public void setTimestmp(String timestmp) {
		this.timestmp = timestmp;
	}

}
