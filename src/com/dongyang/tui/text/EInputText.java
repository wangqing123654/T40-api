package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.io.IOException;

/**
 *
 * <p>Title: 输入提示组件</p>
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
     * 构造器
     * @param panel EPanel
     */
    public EInputText(EPanel panel)
    {
        super(panel);
    }
    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text)
    {
        super.setText("{" + text + "}");
    }
    /**
     * 得到文本
     * @return String
     */
    public String getText()
    {
        String text = super.getText();
        return text.substring(1,text.length() - 1);
    }
    /**
     * 得到新对象
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        return getPanel().newInputText(index);
    }
    /**
     * 得到坐标位置
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
     * 绘制固定文本焦点背景
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
     * 绘制下拉焦点
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
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        return false;
    }
    /**
     * 属性对话框
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
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        super.writeObjectAttribute(s);
    }
    /**
     * 读对象属性
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
     * 固定文本区域能否编辑
     * @param index int
     * @param type int
     *  1 输入字符
     *  2 向前删除
     *  3 向后删除
     * @return boolean true 能编辑 false 不能编辑
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
     * 得到对象类型
     * @return int
     */
    public int getObjectType()
    {
        return INPUT_TEXT_TYPE;
    }
    /**
     * 刷新数据
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
     * 得到显示字串
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
     * 是否是Tab焦点
     * @return boolean
     */
    public boolean isTabFocus()
    {
        return true;
    }
    /**
     * 设置Tab焦点
     */
    public void setTabFocus()
    {
        setFocus(1);
    }
    /**
     * 克隆
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
