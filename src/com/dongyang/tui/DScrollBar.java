package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: ������</p>
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
     * ����
     */
    public static final int HORIZONTAL = 0;

    /**
     * ����
     */
    public static final int VERTICAL = 1;

    /**
     * ������
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
     * ���ù���������
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
     * �õ�����������
     * @return int
     */
    public int getOrientation()
    {
        return TypeTool.getInt(attribute.get("D_orientation"));
    }
    /**
     * ������С�ߴ�
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
     * �õ���С�ߴ�
     * @return int
     */
    public int getMinimum()
    {
        return TypeTool.getInt(attribute.get("D_minimum"));
    }
    /**
     * �������ߴ�
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
     * �õ����ߴ�
     * @return int
     */
    public int getMaximum()
    {
        return TypeTool.getInt(attribute.get("D_maximum"));
    }
    /**
     * ����ֵ
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
     * ����ֵ(˽�з���)
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
        //ֵ�ı��¼�
        onChangeValue();
    }
    /**
     * �õ�ֵ
     * @return int
     */
    public int getValue()
    {
        return TypeTool.getInt(attribute.get("D_value"));
    }
    /**
     * ������ʾ�ߴ�
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
     * �õ���ʾ�ߴ�
     * @return int
     */
    public int getVisibleAmount()
    {
        return TypeTool.getInt(attribute.get("D_visibleAmount"));
    }
    /**
     * ������껬�ֵ�Ԫ�߶�
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
     * �õ���껬�ֵ�Ԫ�߶�
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
     * �õ���ť
     * @param index int 1 �ϻ��� 2 �»��� 3 �м�
     * @return DScrollBarButton
     */
    public DScrollBarButton getButton(int index)
    {
        return (DScrollBarButton)getDComponent(index - 1);
    }
    /**
     * �õ��ƶ����ߴ�
     * @return int
     */
    public int getMoveMaxSize()
    {
        DRectangle r = getComponentBounds();
        switch(getOrientation())
        {
            case VERTICAL://����
                return r.getHeight() > 34?r.getHeight() - 34:0;
            case HORIZONTAL://����
                return r.getWidth() > 34?r.getWidth() - 34:0;
        }
        return 0;
    }
    /**
     * �õ��������ߴ�
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
     * �õ���ߴ�
     * @return int
     */
    public int checkBlockSize()
    {
        //�ƶ����ߴ�
        int maxSize = getMoveMaxSize();
        //��ʾ�ߴ�
        int amout = getVisibleAmount();
        if(maxSize < 8)
            return 0;
        if(amout <= 0)
            return 0;
        //�������ߴ�
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
     * ��ʼ��������ߴ�λ��
     */
    public void resetBlock()
    {
        //���ù�����ߴ�
        setBlockSize();
        //���ù�����λ��
        setBlockLocation();
    }
    /**
     * ���Թ������λ��
     * @return int
     */
    public int checkBlockLocation()
    {
        int value = getValue() - getMinimum();
        if(value == 0)
            return 0;
        //�ƶ����ߴ�
        int maxSize = getMoveMaxSize();
        if(maxSize < 8)
            return 0;
        //�������ߴ�
        int maxScrollSize = getMaxScrollSize();
        if(maxScrollSize <= 0)
            return 0;
        //������ߴ�
        int blockSize = getBlockSize();
        return (int)(((double)maxSize - (double)blockSize)* (double)value / ((double)maxScrollSize - 1.0) + 0.5) + 17;
    }
    /**
     * ����ֵ
     * @param t int
     */
    public void checkValue(int t)
    {
        //�ƶ����ߴ�
        int maxSize = getMoveMaxSize();
        if(maxSize < 8)
            return;
        int blockSize = getBlockSize();
        if(maxSize == blockSize)
            return;
        //�������ߴ�
        int maxScrollSize = getMaxScrollSize();
        if(maxScrollSize <= 0)
            return;
        t -= 17;
        int value = (int)((double)t * ((double)maxScrollSize - 1.0) / ((double)maxSize - (double)blockSize) + 0.5) + getMinimum();
        setValueP(value);
    }
    /**
     * ���ù���λ��
     */
    public void setBlockLocation()
    {
        switch(getOrientation())
        {
            case VERTICAL://����
                int x = checkBlockLocation();
                getButton(3).setY(x);
                return;
            case HORIZONTAL://����
                getButton(3).setX(checkBlockLocation());
                return;
        }
    }
    /**
     * �õ�������λ��
     * @return int
     */
    public int getBlockLocation()
    {
        switch(getOrientation())
        {
            case VERTICAL://����
                return getButton(3).getY();
            case HORIZONTAL://����
                return getButton(3).getX();
        }
        return 0;
    }
    /**
     * ���ù�����ߴ�
     */
    public void setBlockSize()
    {
        switch(getOrientation())
        {
            case VERTICAL://����
                getButton(3).setHeight(checkBlockSize());
                return;
            case HORIZONTAL://����
                getButton(3).setWidth(checkBlockSize());
                return;
        }
    }
    /**
     * �õ�������ĳߴ�
     * @return int
     */
    public int getBlockSize()
    {
        switch(getOrientation())
        {
            case VERTICAL://����
                return getButton(3).getHeight();
            case HORIZONTAL://����
                return getButton(3).getWidth();
        }
        return 0;
    }
    /**
     * ���ð�ť�߿������ʽ
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
     * �õ���ť�߿������ʽ
     * @return int
     */
    public int getButtonBorderDrawState()
    {
        return TypeTool.getInt(attribute.get("D_buttonBorderDrawState"));
    }
    /**
     * ������
     * @return boolean
     */
    public boolean onMouseEntered()
    {
        super.onMouseEntered();
        repaintButtonBorder();
        return false;
    }
    /**
     * ����뿪
     * @return boolean
     */
    public boolean onMouseExited()
    {
        super.onMouseExited();
        repaintButtonBorder();
        return false;
    }
    /**
     * �������
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        repaintButtonBorder();
        //�����߳�
        int index = getButtonBorderDrawState();
        if(index > 10)
            startThread(index);
        return false;
    }
    /**
     * ���̧��
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        if(super.onMouseLeftReleased())
            return true;
        repaintButtonBorder();
        //ֹͣ�߳�
        stopThread();
        return false;
    }
    /**
     * ����ƶ�
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        boolean result = super.onMouseMoved();
        repaintButtonBorder();
        return result;
    }
    /**
     * ��껬��
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
     * ��껬����Ϣ����
     * @param i int
     */
    public void toMouseWheelMoved(int i)
    {
        setMouseWheelMoved(i);
        blockClicked(100);
        repaintButtonBorder();
    }
    /**
     * �ߴ�ı�
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
     * ֵ�ı�
     */
    public void onChangeValue()
    {
        runActionMap("onChangeValue");
    }
    /**
     * �����߳�
     */
    private ClickedThread clickedThread;
    /**
     * ���������߳�
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
     * ֹͣ�����߳�
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
     * �鱻�ƶ�
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
            case VERTICAL://����
                checkValue(button.getY());
                break;
            case HORIZONTAL://����
                checkValue(button.getX());
                break;
        }
    }
    private boolean lockMove = false;
    /**
     * ����
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
     * ����UI�༭״̬
     * @param b boolean
     */
    public void setUIEditStatus(boolean b)
    {
        super.setUIEditStatus(b);
        setButtonBorderDrawState(0);
    }
    /**
     * �ػ水ť�߿�
     */
    public void repaintButtonBorder()
    {
        //�༭״̬ʱֹͣ��ť�߿�Ч��
        if(isUIEditStatus())
            return;
        int stateNow = checkButtonBorderDrawState();
        if(stateNow == getButtonBorderDrawState())
            return;
        setButtonBorderDrawState(stateNow);
        repaint();
    }
    /**
     * ���Դ���ֹͣ
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
     * �õ���ť�߿����״̬
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
     * ���Ʊ߿�
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        DBorder.paintScrollBar(this,g,getButtonBorderDrawState());
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
        s.writeByte(super.getAttributeIDMax() + 1,getOrientation(),0);
        s.writeInt(super.getAttributeIDMax() + 2,getMinimum(),0);
        s.writeInt(super.getAttributeIDMax() + 3,getMaximum(),0);
        s.writeInt(super.getAttributeIDMax() + 4,getVisibleAmount(),0);
        s.writeInt(super.getAttributeIDMax() + 5,getValue(),0);
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
     * �ܹ��õ�����
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
