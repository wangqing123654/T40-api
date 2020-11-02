package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Vector;
import com.dongyang.ui.TWindow;
import com.dongyang.ui.base.TWordBase;
import com.dongyang.tui.DPoint;
import com.dongyang.util.ImageTool;

import java.awt.Dimension;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 单选</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.8.27
 * @version 1.0
 */
public class ESingleChoose extends EFixed
{
    /**
     * 数据
     */
    private Vector data = new Vector();
    /**
     * 构造器
     * @param panel EPanel
     */
    public ESingleChoose(EPanel panel)
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
     * 设置数据
     * @param data Vector
     */
    public void setData(Vector data)
    {
        if(data == null)
            data = new Vector();
        this.data = data;
    }
    /**
     * 得到数据
     * @return Vector
     */
    public Vector getData()
    {
        return data;
    }
    /**
     * 数据个数
     * @return int
     */
    public int sizeData()
    {
        return data.size();
    }
    /**
     * 增加数据
     * @param name String
     * @param value String
     */
    public void addData(String name,String value)
    {
        Vector v = new Vector();
        v.add(name);
        v.add(value);
        getData().add(v);
    }
    /**
     * 插入数据
     * @param index int 位置
     * @param name String
     * @param value String
     */
    public void addData(int index,String name,String value)
    {
        if(name == null)
            name = "";
        Vector v = new Vector();
        v.add(name);
        v.add(value);
        getData().add(index,v);
    }
    /**
     * 删除数据
     * @param index int
     */
    public void removeData(int index)
    {
        getData().remove(index);
    }
    /**
     * 得到数据
     * @param index int
     * @return Vector
     */
    public Vector getData(int index)
    {
        return (Vector)getData().get(index);
    }
    /**
     * 得到数据名称
     * @param index int
     * @return String
     */
    public String getDataName(int index)
    {
        Vector v = getData(index);
        if(v == null || v.size() == 0)
            return null;
        return (String)v.get(0);
    }
    /**
     * 得到数据值
     * @param index int
     * @return String
     */
    public String getDataValue(int index)
    {
        Vector v = getData(index);
        if(v == null || v.size() < 2)
            return null;
        return (String)v.get(1);
    }
    
    /**
     * 得到选中的值
     * @return
     */
    public String getSelectedValue(){
    	int row=this.getSelectRow(this.getText());
    	return this.getDataValue(row);
    }
    
    /**
     * 删除数据
     * @param name String
     */
    public void removeValue(String name)
    {
        if(name == null)
            return;
        for(int i = sizeData() - 1;i >= 0;i --)
            if(name.equals(getDataName(i)))
                removeData(i);
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
        return "<ESingleChoose name=" + getName() + ",start=" + getStart() + ",end=" + getEnd() + ",s=" + getString() + ",position=" + getPosition() + ",index=" + getIndexString() +
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
        TWindow propertyWindow = (TWindow)getPM().getUI().openWindow("%ROOT%\\config\\database\\SingleChoosePopMenu.x", getHeadFixed() ,true);
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
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\SingleChooseEdit.x",fixed);
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
        s.writeShort(100);
        s.writeInt(sizeData());
        for(int i = 0;i < sizeData();i++)
        {
            Vector data = (Vector)getData().get(i);
            s.writeInt(data.size());
            for(int j = 0;j < data.size();j++)
                s.writeString((String)data.get(j));
        }
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
            int count = s.readInt();
            for(int i = 0;i < count;i++)
            {
                Vector v = new Vector();
                int c = s.readInt();
                for(int j = 0;j < c;j++)
                {
                    v.add(s.readString());
                }
                getData().add(v);
            }
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
        return SINGLE_CHOOSE_TYPE;
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
     * 得到数据行数
     * @return int
     */
    public int getDataSize()
    {
        return getData().size();
    }
    /**
     * 查找选中行号
     * @param text String
     * @return int
     */
    private int getSelectRow(String text)
    {
        int count = getDataSize();
        for(int i = 0;i < count;i++)
        {
            String value = getDataName(i);
            if(value != null && value.equals(text))
                return i;
        }
        return -1;
    }
    /**
     * 向上移动光标
     * @param x int
     */
    public void onFocusToUp(int x)
    {
        if(getDataSize() == 0)
            return;
        if(getKeyIndex() != getModifyNodeIndex())
            return;

        int row = getSelectRow(getText());
        if(row == -1)
            row = 0;
        else
        {
            row--;
            if (row == -1)
                row = getDataSize() - 1;
        }
        setText(getDataName(row));
        update();
        
        //add by lx 2015/10/22  进行自动计算
		TWordBase twb = (TWordBase) this.getPageManager().getUI().getParentTCBase();
		twb.onCalculateExpression();
		//
		this.setFocus(1);
    }
    /**
     * 向下移动光标
     * @param x int
     */
    public void onFocusToDown(int x)
    {
        if(getDataSize() == 0)
            return;
        if(getKeyIndex() != getModifyNodeIndex())
            return;
        int row = getSelectRow(getText());
        if(row == -1)
            row = 0;
        else
        {
            row++;
            if (row >= getDataSize())
                row = 0;
        }
        setText(getDataName(row));
        update();
        
        //add by lx 2015/10/22  进行自动计算
		TWordBase twb = (TWordBase) this.getPageManager().getUI().getParentTCBase();
		twb.onCalculateExpression();
		//
		//
		this.setFocus(1);
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
        ESingleChoose singleChoose = new ESingleChoose(panel);
        singleChoose.setStart(panel.getDString().size());
        singleChoose.setEnd(getStart());
        singleChoose.setString(getString());
        singleChoose.setKeyIndex(getKeyIndex());
        singleChoose.setDeleteFlg(isDeleteFlg());
        singleChoose.setStyleOther(getStyle());
        singleChoose.setName(getName());
        singleChoose.setGroupName(getGroupName());
        singleChoose.setMicroName(getMicroName());
        singleChoose.setDataElements(isIsDataElements());
        singleChoose.setAllowNull(isAllowNull());

        for(int i = 0;i < sizeData();i++)
        {
            Vector d = (Vector)getData().get(i);
            singleChoose.addData((String)d.get(0),(String)d.get(1));
        }
        return singleChoose;
    }

}
