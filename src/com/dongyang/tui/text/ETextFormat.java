package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.DPoint;
import com.dongyang.util.ImageTool;
import java.awt.Dimension;
import com.dongyang.ui.TWindow;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ETextFormat extends EFixed
{
    /**
     * 数据集标识
     */
    String data = "";

    /**
     * 数据编码
     */
    String code = "";

    /**
     * 构造器
     * @param panel EPanel
     */
    public ETextFormat(EPanel panel)
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
        if(text.length() < 2)
            return "";
        return text.substring(1,text.length() - 1);
    }
    /**
     * 得到新对象
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        return getPanel().newSingleChoose(index);
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
        return panel.getIndexString() + ",ESingleChoose:" + findIndex();
    }
    public String toString()
    {
        return "<ETextFormat name=" + getName() + ",start=" + getStart() + ",end=" + getEnd() + ",s=" + getString() + ",position=" + getPosition() + ",index=" + getIndexString() +
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
        if(getNextFixed() == null)
            paintComboFlg(g,x + width - 2,y + height);
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

    public boolean onKeyPressed()
    {
        openVauePopupMenu();
        return true;
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        openVauePopupMenu();
        return true;
    }
    /**
     * 值选择对话框
     */
    public void openVauePopupMenu()
    {
        if(getKeyIndex() != getModifyNodeIndex())
            return;
        DPoint point = getScreenPoint();
        int h = (int)(getHeight() * getZoom() / 75.0 + 0.5) + 3;
        Dimension dimension = ImageTool.getScreenSize();
        TWindow propertyWindow = (TWindow)getPM().getUI().openWindow("%ROOT%\\config\\database\\TextFormatPopMenu.x", getHeadFixed() ,true);
        int pw = propertyWindow.getWidth();
        int ph = propertyWindow.getHeight();
        int x = point.getX();
        if(x < 0)
            x = 0;
        if(x + pw > dimension.getWidth())
            x = (int)dimension.getWidth() - pw;
        int y = point.getY();
        if(y + h + ph > dimension.getHeight())
            y = y - ph - 3;
        else
            y = y + h;
        if(y < 0)
            y = 0;
        propertyWindow.setX(x);
        propertyWindow.setY(y);
        propertyWindow.setVisible(true);
        getPM().getEventManager().setPropertyMenuWindow(propertyWindow);
    }

    /**
     * 属性对话框
     */
    public void openPropertyDialog()
    {
        EFixed fixed = getHeadFixed();
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\ETextFormatEdit.x",fixed);
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
        s.writeString(100,getData(),null);
        s.writeString(101,getCode(),null);
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
            setData(s.readString());
            return true;
        case 101:
            setCode(s.readString());
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
        return TEXTFORMAT_TYPE;
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
        if(s.length() == 0)
            return s;
        if(!hasPreviousLink())
            s = s.substring(1);
        if(s.length() == 0)
            return s;
        if(!hasNextLink())
            s = s.substring(0, s.length() - 1);
        return s;
    }
    /**
     * 更新
     */
    public void update()
    {
        getFocusManager().update();
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
        ETextFormat textFormat = new ETextFormat(panel);
        textFormat.setStart(panel.getDString().size());
        textFormat.setEnd(getStart());
        textFormat.setString(getString());
        textFormat.setKeyIndex(getKeyIndex());
        textFormat.setDeleteFlg(isDeleteFlg());
        textFormat.setStyleOther(getStyle());
        textFormat.setName(getName());
        textFormat.setCode(getCode());
        textFormat.setData(getData());
        textFormat.setGroupName(getGroupName());
        textFormat.setMicroName(getMicroName());
        textFormat.setDataElements(isIsDataElements());
        textFormat.setAllowNull(isAllowNull());

        return textFormat;
    }

    /**
     * 设置数据集标识
     * @param data String
     */
    public void setData(String data){
        this.data = data;
    }

    /**
     * 取得数据集标识
     * @return String
     */
    public String getData(){
        return data;
    }

    /**
     * 设置数据集标识
     * @param code String
     */
    public void setCode(String code){
        this.code = code;
    }

    /**
     * 取得数据集标识
     * @return String
     */
    public String getCode(){
        return code;
    }
}
