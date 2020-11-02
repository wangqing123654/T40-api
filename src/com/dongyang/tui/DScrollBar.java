package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 滚动条</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.10
 * @version 1.0
 */
public class DScrollBar extends DComponent
{
    /**
     * 横向
     */
    public static final int HORIZONTAL = 0;

    /**
     * 纵向
     */
    public static final int VERTICAL = 1;

    /**
     * 构造器
     */
    public DScrollBar()
    {
        addDComponent(new DScrollBarButton(this,1));
        addDComponent(new DScrollBarButton(this,2));
        addDComponent(new DScrollBarButton(this,3));
        setOrientation(HORIZONTAL);
        setVisibleAmount(10);
        setMaximum(100);
    }
    /**
     * 设置滚动条方向
     * @param orientation int
     */
    public void setOrientation(int orientation)
    {
        if(orientation == 0)
            attribute.remove("D_orientation");
        else
            attribute.put("D_orientation",orientation);
        if(getButton(3) == null)
            return;
        getButton(3).setMouseMoveType(orientation == VERTICAL?3:2);
    }
    /**
     * 得到滚动条方向
     * @return int
     */
    public int getOrientation()
    {
        return TypeTool.getInt(attribute.get("D_orientation"));
    }
    /**
     * 设置最小尺寸
     * @param minimum int
     */
    public void setMinimum(int minimum)
    {
        if(getValue() < minimum)
            setValueP(minimum);
        if(minimum == 0)
        {
            attribute.remove("D_minimum");
            return;
        }
        attribute.put("D_minimum",minimum);
        resetBlock();
    }
    /**
     * 得到最小尺寸
     * @return int
     */
    public int getMinimum()
    {
        return TypeTool.getInt(attribute.get("D_minimum"));
    }
    /**
     * 设置最大尺寸
     * @param maximum int
     */
    public void setMaximum(int maximum)
    {
        if(getValue() > maximum)
            setValueP(maximum);
        if(maximum == 0)
        {
            attribute.remove("D_maximum");
            return;
        }
        attribute.put("D_maximum",maximum);
        resetBlock();
    }
    /**
     * 得到最大尺寸
     * @return int
     */
    public int getMaximum()
    {
        return TypeTool.getInt(attribute.get("D_maximum"));
    }
    /**
     * 设置值
     * @param value int
     */
    public void setValue(int value)
    {
        if(value < getMinimum())
            value = getMinimum();
        if(value > getMaximum())
            value = getMaximum();
        setValueP(value);
        resetBlock();
    }
    /**
     * 设置值(私有方法)
     * @param value int
     */
    private void setValueP(int value)
    {
        if(getValue() == value)
            return;
        if(value == 0)
            attribute.remove("D_value");
        else
            attribute.put("D_value",value);
        //值改变事件
        onChangeValue();
    }
    /**
     * 得到值
     * @return int
     */
    public int getValue()
    {
        return TypeTool.getInt(attribute.get("D_value"));
    }
    /**
     * 设置显示尺寸
     * @param visibleAmount int
     */
    public void setVisibleAmount(int visibleAmount)
    {
        if(visibleAmount == 0)
        {
            attribute.remove("D_visibleAmount");
            return;
        }
        attribute.put("D_visibleAmount",visibleAmount);
        resetBlock();
    }
    /**
     * 得到显示尺寸
     * @return int
     */
    public int getVisibleAmount()
    {
        return TypeTool.getInt(attribute.get("D_visibleAmount"));
    }
    /**
     * 设置鼠标滑轮单元高度
     * @param value int
     */
    public void setMouseWheelValue(int value)
    {
        if(value < 1)
            value = 1;
        if(value == 1)
        {
            attribute.remove("D_mouseWheelValue");
            return;
        }
        attribute.put("D_mouseWheelValue",value);
    }
    /**
     * 得到鼠标滑轮单元高度
     * @return int
     */
    public int getMouseWheelValue()
    {
        int value = TypeTool.getInt(attribute.get("D_mouseWheelValue"));
        if(value == 0)
            value = 1;
        return value;
    }
    /**
     * 得到按钮
     * @param index int 1 上或左 2 下或右 3 中间
     * @return DScrollBarButton
     */
    public DScrollBarButton getButton(int index)
    {
        return (DScrollBarButton)getDComponent(index - 1);
    }
    /**
     * 得到移动最大尺寸
     * @return int
     */
    public int getMoveMaxSize()
    {
        DRectangle r = getComponentBounds();
        switch(getOrientation())
        {
            case VERTICAL://纵向
                return r.getHeight() > 34?r.getHeight() - 34:0;
            case HORIZONTAL://横向
                return r.getWidth() > 34?r.getWidth() - 34:0;
        }
        return 0;
    }
    /**
     * 得到最大滚动尺寸
     * @return int
     */
    public int getMaxScrollSize()
    {
        int size = getMaximum() - getMinimum();
        if(size < 0)
            size = 0;
        return size;
    }
    /**
     * 得到块尺寸
     * @return int
     */
    public int checkBlockSize()
    {
        //移动最大尺寸
        int maxSize = getMoveMaxSize();
        //显示尺寸
        int amout = getVisibleAmount();
        if(maxSize < 8)
            return 0;
        if(amout <= 0)
            return 0;
        //最大滚动尺寸
        int maxScrollSize = getMaxScrollSize();
        int size = 0;
        if(maxScrollSize < amout)
            size = (int)(((double)maxSize / 2.0) * (((double)amout - (double)maxScrollSize) / (double)amout + 1.0));
        else
            size = maxSize * amout / (maxScrollSize + amout);
        if(size < 8)
            size = 8;
        return size;
    }
    /**
     * 初始化滚动块尺寸位置
     */
    public void resetBlock()
    {
        //设置滚动块尺寸
        setBlockSize();
        //设置滚动块位置
        setBlockLocation();
    }
    /**
     * 测试滚动块的位置
     * @return int
     */
    public int checkBlockLocation()
    {
        int value = getValue() - getMinimum();
        if(value == 0)
            return 0;
        //移动最大尺寸
        int maxSize = getMoveMaxSize();
        if(maxSize < 8)
            return 0;
        //最大滚动尺寸
        int maxScrollSize = getMaxScrollSize();
        if(maxScrollSize <= 0)
            return 0;
        //滚动块尺寸
        int blockSize = getBlockSize();
        return (int)(((double)maxSize - (double)blockSize)* (double)value / ((double)maxScrollSize - 1.0) + 0.5) + 17;
    }
    /**
     * 测试值
     * @param t int
     */
    public void checkValue(int t)
    {
        //移动最大尺寸
        int maxSize = getMoveMaxSize();
        if(maxSize < 8)
            return;
        int blockSize = getBlockSize();
        if(maxSize == blockSize)
            return;
        //最大滚动尺寸
        int maxScrollSize = getMaxScrollSize();
        if(maxScrollSize <= 0)
            return;
        t -= 17;
        int value = (int)((double)t * ((double)maxScrollSize - 1.0) / ((double)maxSize - (double)blockSize) + 0.5) + getMinimum();
        setValueP(value);
    }
    /**
     * 设置滚动位置
     */
    public void setBlockLocation()
    {
        switch(getOrientation())
        {
            case VERTICAL://纵向
                int x = checkBlockLocation();
                getButton(3).setY(x);
                return;
            case HORIZONTAL://横向
                getButton(3).setX(checkBlockLocation());
                return;
        }
    }
    /**
     * 得到滚动块位置
     * @return int
     */
    public int getBlockLocation()
    {
        switch(getOrientation())
        {
            case VERTICAL://纵向
                return getButton(3).getY();
            case HORIZONTAL://横向
                return getButton(3).getX();
        }
        return 0;
    }
    /**
     * 设置滚动块尺寸
     */
    public void setBlockSize()
    {
        switch(getOrientation())
        {
            case VERTICAL://纵向
                getButton(3).setHeight(checkBlockSize());
                return;
            case HORIZONTAL://横向
                getButton(3).setWidth(checkBlockSize());
                return;
        }
    }
    /**
     * 得到滚动块的尺寸
     * @return int
     */
    public int getBlockSize()
    {
        switch(getOrientation())
        {
            case VERTICAL://纵向
                return getButton(3).getHeight();
            case HORIZONTAL://横向
                return getButton(3).getWidth();
        }
        return 0;
    }
    /**
     * 设置按钮边框绘制样式
     * @param state int
     */
    public void setButtonBorderDrawState(int state)
    {
        if(state == 0)
        {
            attribute.remove("D_buttonBorderDrawState");
            return;
        }
        attribute.put("D_buttonBorderDrawState",state);
    }
    /**
     * 得到按钮边框绘制样式
     * @return int
     */
    public int getButtonBorderDrawState()
    {
        return TypeTool.getInt(attribute.get("D_buttonBorderDrawState"));
    }
    /**
     * 鼠标进入
     * @return boolean
     */
    public boolean onMouseEntered()
    {
        super.onMouseEntered();
        repaintButtonBorder();
        return false;
    }
    /**
     * 鼠标离开
     * @return boolean
     */
    public boolean onMouseExited()
    {
        super.onMouseExited();
        repaintButtonBorder();
        return false;
    }
    /**
     * 左键按下
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        repaintButtonBorder();
        //启动线程
        int index = getButtonBorderDrawState();
        if(index > 10)
            startThread(index);
        return false;
    }
    /**
     * 左键抬起
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        if(super.onMouseLeftReleased())
            return true;
        repaintButtonBorder();
        //停止线程
        stopThread();
        return false;
    }
    /**
     * 鼠标移动
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        boolean result = super.onMouseMoved();
        repaintButtonBorder();
        return result;
    }
    /**
     * 鼠标滑轮
     * @return boolean
     */
    public boolean onMouseWheelMoved()
    {
        super.onMouseWheelMoved();
        blockClicked(100);
        repaintButtonBorder();
        return false;
    }
    /**
     * 鼠标滑轮消息传递
     * @param i int
     */
    public void toMouseWheelMoved(int i)
    {
        setMouseWheelMoved(i);
        blockClicked(100);
        repaintButtonBorder();
    }
    /**
     * 尺寸改变
     * @return boolean
     */
    public boolean onComponentResized()
    {
        super.onComponentResized();
        lockMove = true;
        resetBlock();
        lockMove = false;
        return false;
    }
    /**
     * 值改变
     */
    public void onChangeValue()
    {
        runActionMap("onChangeValue");
    }
    /**
     * 触发线程
     */
    private ClickedThread clickedThread;
    /**
     * 启动触发线程
     * @param index int
     */
    private void startThread(int index)
    {
        if(clickedThread != null)
            return;
        clickedThread = new ClickedThread(index);
        clickedThread.start();
    }
    /**
     * 停止触发线程
     */
    private void stopThread()
    {
        clickedThread = null;
    }
    class ClickedThread extends Thread
    {
        int index;
        public ClickedThread(int index)
        {
            this.index = index;
        }
        public void run()
        {
            blockClicked(index);
            try{
                sleep(500);
            }catch(Exception e)
            {
            }
            while(clickedThread != null)
            {
                if(!checkStopThread(index))
                {
                    stopThread();
                    return;
                }
                blockClicked(index);
                try{
                    sleep(100);
                }catch(Exception e)
                {
                }
            }
        }
    }
    /**
     * 块被移动
     */
    public void movedBlock()
    {
        if(lockMove)
            return;
        DScrollBarButton button = getButton(3);
        if(button == null)
            return;
        switch(getOrientation())
        {
            case VERTICAL://纵向
                checkValue(button.getY());
                break;
            case HORIZONTAL://横向
                checkValue(button.getX());
                break;
        }
    }
    private boolean lockMove = false;
    /**
     * 块点击
     * @param index int
     */
    public void blockClicked(int index)
    {
        lockMove = true;
        switch(index)
        {
        case 1:
            int value = getValue() - 1;
            if(value < getMinimum())
                break;
            setValueP(value);
            setBlockLocation();
            break;
        case 2:
            value = getValue() + 1;
            if(value >= getMaximum())
                break;
            setValueP(value);
            setBlockLocation();
            break;
        case 11:
            value = getValue() - getVisibleAmount();
            if(value < getMinimum())
            {
                value = getMinimum();
                stopThread();
            }
            setValueP(value);
            setBlockLocation();
            break;
        case 12:
            value = getValue() + getVisibleAmount();
            if(value >= getMaximum())
            {
                value = getMaximum() - 1;
                stopThread();
            }
            setValueP(value);
            setBlockLocation();
            break;
        case 100:
            value = getValue() + getMouseWheelMoved() * getMouseWheelValue();
            if(value < getMinimum())
                value = getMinimum();
            if(value >= getMaximum())
                value = getMaximum() - 1;
            setValueP(value);
            setBlockLocation();
            break;
        }
        repaint();
        lockMove = false;
    }
    /**
     * 设置UI编辑状态
     * @param b boolean
     */
    public void setUIEditStatus(boolean b)
    {
        super.setUIEditStatus(b);
        setButtonBorderDrawState(0);
    }
    /**
     * 重绘按钮边框
     */
    public void repaintButtonBorder()
    {
        //编辑状态时停止按钮边框效果
        if(isUIEditStatus())
            return;
        int stateNow = checkButtonBorderDrawState();
        if(stateNow == getButtonBorderDrawState())
            return;
        setButtonBorderDrawState(stateNow);
        repaint();
    }
    /**
     * 测试触发停止
     * @param index int
     * @return boolean
     */
    public boolean checkStopThread(int index)
    {
        int location = getBlockLocation();
        int size = getBlockSize();
        DRectangle r = getComponentBounds();
        switch(this.getOrientation())
        {
        case VERTICAL:
            if(getMouseDownY() > 17 && getMouseDownY() < location && index == 11)
                return true;
            if(getMouseDownY() > location + size && getMouseDownY() < r.getHeight() - 17 && index == 12)
                return true;
            return false;
        case HORIZONTAL:
            if(getMouseDownX() > 17 && getMouseDownX() < location && index == 11)
                return true;
            if(getMouseDownX() > location + size && getMouseDownX() < r.getWidth() - 17 && index == 12)
                return true;
            return false;
        }
        return false;
    }
    /**
     * 得到按钮边框绘制状态
     * @return int
     */
    public int checkButtonBorderDrawState()
    {
        if(!isMouseEntered())
            return 0;
        int location = getBlockLocation();
        int size = getBlockSize();
        DRectangle r = getComponentBounds();
        switch(this.getOrientation())
        {
        case VERTICAL:
            if(getMouseY() > 17 && getMouseY() < location)
                return isMouseLeftKeyDown()?11:1;
            if(getMouseY() > location + size && getMouseY() < r.getHeight() - 17)
                return isMouseLeftKeyDown()?12:2;
            break;
        case HORIZONTAL:
            if(getMouseX() > 17 && getMouseX() < location)
                return isMouseLeftKeyDown()?11:1;
            if(getMouseX() > location + size && getMouseX() < r.getWidth() - 17)
                return isMouseLeftKeyDown()?12:2;
            break;
        }
        return 0;
    }
    /**
     * 绘制边框
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        DBorder.paintScrollBar(this,g,getButtonBorderDrawState());
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
        s.writeByte(super.getAttributeIDMax() + 1,getOrientation(),0);
        s.writeInt(super.getAttributeIDMax() + 2,getMinimum(),0);
        s.writeInt(super.getAttributeIDMax() + 3,getMaximum(),0);
        s.writeInt(super.getAttributeIDMax() + 4,getVisibleAmount(),0);
        s.writeInt(super.getAttributeIDMax() + 5,getValue(),0);
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
        if(super.readObjectAttribute(id,s))
            return true;
        switch (id - super.getAttributeIDMax())
        {
        case 1:
            setOrientation(s.readByte());
            return true;
        case 2:
            setMinimum(s.readInt());
            return true;
        case 3:
            setMaximum(s.readInt());
            return true;
        case 4:
            setVisibleAmount(s.readInt());
            return true;
        case 5:
            setValue(s.readInt());
            return true;
        }
        return false;
    }
    /**
     * 能够得到焦点
     * @return boolean
     */
    public boolean canFocus()
    {
        return false;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DDScrollBar>tag=");
        sb.append(getTag());
        sb.append(" { value=");
        sb.append(getValue());
        sb.append(",minimum=");
        sb.append(getMinimum());
        sb.append(",maximum=");
        sb.append(getMaximum());
        sb.append(",visibleAmount=");
        sb.append(getVisibleAmount());
        sb.append(",orientation=");
        sb.append(getOrientation());
        sb.append("}");
        return sb.toString();
    }
}
