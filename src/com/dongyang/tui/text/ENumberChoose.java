package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 * <p>Title: 数字输入组件</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2010.2.5
 * @author whao 2013~
 * @version 1.0
 */
public class ENumberChoose extends EFixed
{
    /**
     * 数字类型
     */
    private int numberType = 1;
    /**
     * 补偿
     */
    private double numberStep = 1.0;
    /**
     * 测试最大的数值
     */
    private boolean isCheckMaxValue;
    /**
     * 测试最小的数值
     */
    private boolean isCheckMinValue;
    /**
     * 最大的数值
     */
    private double numberMaxValue;
    /**
     * 最小的数值
     */
    private double numberMinValue;
    /**
     * 能否输入负数
     */
    private boolean canInputNegativeNumber = true;
    /**
     * 显示格式
     */
    private String numberFormat = "";
    /**
     * 可编辑
     */
    private boolean enabled = true;
    /**
     * 构造器
     * @param panel EPanel
     */
    public ENumberChoose(EPanel panel)
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
        //System.out.println("========text=========="+text);
        if(text.equals("")){
            return "";
        }
        return text.substring(1,text.length() - 1);
    }
    /**
     * 设置数字类型
     * @param numberType int
     */
    public void setNumberType(int numberType)
    {
        this.numberType = numberType;
    }
    /**
     * 得到数字类型
     * @return int
     */
    public int getNumberType()
    {
        return numberType;
    }
    /**
     * 设置步长
     * @param numberStep double
     */
    public void setNumberStep(double numberStep)
    {
        this.numberStep = numberStep;
    }
    /**
     * 得到步长
     * @return double
     */
    public double getNumberStep()
    {
        return numberStep;
    }
    /**
     * 设置是否测试最大的数值
     * @param isCheckMaxValue boolean
     */
    public void setCheckMaxValue(boolean isCheckMaxValue)
    {
        this.isCheckMaxValue = isCheckMaxValue;
    }
    /**
     * 是否测试最大的数值
     * @return boolean
     */
    public boolean isCheckMaxValue()
    {
        return isCheckMaxValue;
    }
    /**
     * 设置是否测试最小的数值
     * @param isCheckMinValue boolean
     */
    public void setCheckMinValue(boolean isCheckMinValue)
    {
        this.isCheckMinValue = isCheckMinValue;
    }
    /**
     * 是否测试最小的数值
     * @return boolean
     */
    public boolean isCheckMinValue()
    {
        return isCheckMinValue;
    }
    /**
     * 设置最大值
     * @param numberMaxValue double
     */
    public void setNumberMaxValue(double numberMaxValue)
    {
        this.numberMaxValue = numberMaxValue;
    }
    /**
     * 得到最大值
     * @return double
     */
    public double getNumberMaxValue()
    {
        return numberMaxValue;
    }
    /**
     * 设置最小值
     * @param numberMinValue double
     */
    public void setNumberMinValue(double numberMinValue)
    {
        this.numberMinValue = numberMinValue;
    }
    /**
     * 得到最小值
     * @return double
     */
    public double getNumberMinValue()
    {
        return numberMinValue;
    }
    /**
     * 设置能否输入负数
     * @param canInputNegativeNumber boolean
     */
    public void setCanInputNegativeNumber(boolean canInputNegativeNumber)
    {
        this.canInputNegativeNumber = canInputNegativeNumber;
    }
    /**
     * 能否输入负数
     * @return boolean
     */
    public boolean canInputNegativeNumber()
    {
        return canInputNegativeNumber;
    }
    /**
     * 设置显示格式
     * @param numberFormat String
     */
    public void setNumberFormat(String numberFormat)
    {
        if(numberFormat == null)
            numberFormat = "";
        this.numberFormat = numberFormat;
    }
    /**
     * 得到显示格式
     * @return String
     */
    public String getNumberFormat()
    {
        return numberFormat;
    }
    /**
     * 设置编辑
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    /**
     * 是否可以编辑
     * @return boolean
     */
    public boolean isEnabled()
    {
        return enabled;
    }
    /**
     * 得到新对象
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        //return getPanel().newSingleChoose(index);
        return getPanel().newNumberChoose(index);
    }
    /**
     * 能否录入该字符
     * @param index int
     * @param c char
     * @return boolean
     */
    public boolean canInputChar(int index,char c)
    {
        if(isNumber(c))
            return true;
        if(c == '-')
        {
            if(!canInputNegativeNumber())
                return false;
            if(getText().startsWith("-"))
                return false;
            return true;
        }
        if(c == '.')
        {
            if(getNumberType() == 1)
                return false;
            if(getText().indexOf(".") != -1)
                return false;
            return true;
        }
        return false;
    }
    /**
     * 是否是数字
     * @param c char
     * @return boolean
     */
    private boolean isNumber(char c)
    {
        return c >= '0' && c <= '9';
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
        return panel.getIndexString() + ",ENumberChoose:" + findIndex();
    }
    public String toString()
    {
        return "<ENumberChoose name=" + getName() + ",start=" + getStart() + ",end=" + getEnd() + ",s=" + getString() + ",position=" + getPosition() + ",index=" + getIndexString() +
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
     * 设置焦点
     * @param index int 焦点位置
     * @return boolean
     */
    public boolean setFocus(int index)
    {
        EComponent com = getFocus();
        super.setFocus(index);
        if(!isThisObject(com))
        {
            //(com instanceof EFixed
            if(com instanceof ENumberChoose)
                ((ENumberChoose)com).setSelectOneAll(false);
            setSelectOneAll(true);
        }
        //(com instanceof EFixed
        if(com instanceof ENumberChoose)
            ((ENumberChoose)com).setSelectOneAll(false);
        return true;
    }
    public boolean isThisObject(EComponent com)
    {
        if(!(com instanceof ENumberChoose))
            return false;
        ENumberChoose c = (ENumberChoose)getHeadFixed();
        while(c != null)
        {
            if(com == c)
                return true;
            c = (ENumberChoose)c.getNextFixed();
        }
        return false;
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        //openVauePopupMenu();
        return true;
    }
    /**
     * 属性对话框
     */
    public void openPropertyDialog()
    {
        EFixed fixed = getHeadFixed();
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\NumberChooseEdit.x",fixed);
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
        s.writeBoolean(100,isCheckMaxValue,false);
        s.writeBoolean(101,isCheckMinValue,false);
        s.writeDouble(102,getNumberMaxValue(),0);
        s.writeDouble(103,getNumberMinValue(),0);
        s.writeDouble(104,getNumberStep(),0);
        s.writeInt(105,getNumberType(),1);
        s.writeBoolean(106,canInputNegativeNumber(),true);
        s.writeBoolean(107,isEnabled(),true);
        s.writeString(108,getNumberFormat(),"");
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
            setCheckMaxValue(s.readBoolean());
            return true;
        case 101:
            setCheckMinValue(s.readBoolean());
            return true;
        case 102:
            setNumberMaxValue(s.readDouble());
            return true;
        case 103:
            setNumberMinValue(s.readDouble());
            return true;
        case 104:
            setNumberStep(s.readDouble());
            return true;
        case 105:
            setNumberType(s.readInt());
            return true;
        case 106:
            setCanInputNegativeNumber(s.readBoolean());
            return true;
        case 107:
            setEnabled(s.readBoolean());
            return true;
        case 108:
            setNumberFormat(s.readString());
            return true;
        }
        return false;
    }
    /**
     * 粘贴字符
     * @param c char
     * @return boolean
     */
    public boolean pasteChar(char c)
    {
        //不能编辑
        if (!canEdit())
            return false;
        if(isSelectOne())
        {
            setText("" + c);
            setSelectOneAll(false);
            return true;
        }
        return super.pasteChar(c);
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
            return isEnabled();
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
        return NUMBER_CHOOSE_TYPE;
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
        //System.out.println("==ENumberChoose s=="+s);
            if(s.length() == 0)
                return "";
            //解决预览有错的问题
            if((s.equals("{")||s.equals("}"))&&s.length() == 1){
                return "";
            }
            //
            if (!hasPreviousLink())
                s = s.substring(1);
            if (!hasNextLink())
                s = s.substring(0, s.length() - 1);
        //
        return s;
    }
    /**
     * 向上移动光标
     * @param x int
     */
    public void onFocusToUp(int x)
    {
        double d = getNumberStep();
        if(d == 0)
            return;
        String s = getText();
        if(s.length() == 0)
            s = "0";
        double v = 0;
        try{
            v = Double.parseDouble(s);
        }catch(Exception e)
        {
        }
        v -= d;
        if(isCheckMinValue)
        {
            if(v < getNumberMinValue())
                v = getNumberMinValue();
        }
        setText(format(v));
        update();
    }
    /**
     * 向下移动光标
     * @param x int
     */
    public void onFocusToDown(int x)
    {
        double d = getNumberStep();
        if(d == 0)
            return;
        String s = getText();
        if(s.length() == 0)
            s = "0";
        double v = 0;
        try{
            v = Double.parseDouble(s);
        }catch(Exception e)
        {
        }
        v += d;
        if(isCheckMaxValue)
        {
            if(v > getNumberMaxValue())
                v = getNumberMaxValue();
        }
        setText(format(v));
        update();
    }
    /**
     * 格式化
     * @param value double
     * @return String
     */
    public String format(double value)
    {
        if(getNumberFormat().length() > 0)
            return new DecimalFormat(getNumberFormat()).format(value);
        if(getNumberType() == 1)
            return "" + (int)value;
        return "" + value;
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
     * 回车事件
     * @return boolean
     */
    public boolean onEnter()
    {
        String s = getText();
        if (s.length() == 0)
            s = "0";
        double v = 0;
        try {
            v = Double.parseDouble(s);
        } catch (Exception e) {
        }
        if (isCheckMinValue()) {
            if (v < getNumberMinValue())
                setText(format(getNumberMinValue()));
        }
        if (isCheckMaxValue()) {
            if (v > getNumberMaxValue())
                setText(format(getNumberMaxValue()));
        }
        return super.onEnter();
    }

    /**
     * 克隆
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        ENumberChoose numberChoose = new ENumberChoose(panel);
        numberChoose.setStart(panel.getDString().size());
        numberChoose.setEnd(getStart());
        numberChoose.setString(getString());
        numberChoose.setKeyIndex(getKeyIndex());
        numberChoose.setDeleteFlg(isDeleteFlg());
        numberChoose.setStyleOther(getStyle());
        numberChoose.setName(getName());
        numberChoose.numberType = numberType;
        numberChoose.numberStep = numberStep;
        numberChoose.isCheckMaxValue = isCheckMaxValue;
        numberChoose.isCheckMinValue = isCheckMinValue;
        numberChoose.numberMaxValue = numberMaxValue;
        numberChoose.numberMinValue = numberMinValue;
        numberChoose.canInputNegativeNumber = canInputNegativeNumber;
        numberChoose.numberFormat = numberFormat;
        numberChoose.enabled = enabled;
        numberChoose.setGroupName(getGroupName());
        numberChoose.setMicroName(getMicroName());
        numberChoose.setDataElements(isIsDataElements());
        numberChoose.setAllowNull(isAllowNull());
        numberChoose.setTip(this.getTip());

        return numberChoose;
    }
}
