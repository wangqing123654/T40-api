package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import com.dongyang.tui.control.DButtonControl;
import com.dongyang.tui.control.DComponentControl;

/**
 * <p>Title: ��ť</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.3
 * @version 1.0
 */
public class DButton extends DComponent
{
    /**
     * ������
     */
    public DButton()
    {
    }
    /**
     * ����Ĭ�ϱ߿�ߴ�
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(4,4,4,4);
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
        //���ý���
        grabFocus();
        repaintButtonBorder();
        return false;
    }
    /**
     * ���̧��
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        super.onMouseLeftReleased();
        repaintButtonBorder();
        if(inMouse())
            //�������
            onClicked();
        return false;
    }
    /**
     * ����϶�
     * @return boolean
     */
    public boolean onMouseDragged()
    {
        if(super.onMouseDragged())
            return true;
        repaintButtonBorder();
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
     * ����¼�
     */
    public void onClicked()
    {
        DButtonControl control = getIOFrame();
        if(control != null)
            control.onClicked();
        runActionMap("onClicked");
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
     * �õ������¼�
     * @return boolean
     */
    public boolean onFocusGained()
    {
        super.onFocusGained();
        repaintButtonBorder();
        return false;
    }
    /**
     * ʧȥ�����¼�
     * @return boolean
     */
    public boolean onFocusLost()
    {
        super.onFocusLost();
        repaintButtonBorder();
        return false;
    }
    /**
     * �õ���ť�߿����״̬
     * @return int
     */
    public int checkButtonBorderDrawState()
    {
        if(isMouseLeftKeyDown())
        {
            if(isMouseEntered())
               return 3;
            else
                return 1;
        }else
        {
            if(isMouseEntered())
               return 1;
            else if(isFocus())
                return 2;
            else
                return 0;
        }
    }
    /**
     * ���Ʊ߿�
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        if(getBorder() != null && getBorder().length() >= 0)
        {
            super.paintBorder(g);
            return;
        }
        //���ư�ť�߿�
        paintButtonBorder(g);
    }
    /**
     * ���ư�ť�߿�
     * @param g Graphics
     */
    public void paintButtonBorder(Graphics g)
    {
        DBorder.paintButton(this,g,getButtonBorderDrawState());
    }
    /**
     * ����ǰ��
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {
        Graphics g1 = getButtonBorderDrawState() == 3?g.create(1,1,width - 1,height - 1):g;
        super.paintForeground(g1,width,height);
    }
    /**
     * �õ���ť�ӿ�
     * @return DButtonControl
     */
    public DButtonControl getIOFrame()
    {
        DComponentControl control = getIOControlObject();
        if(control == null || !(control instanceof DButtonControl))
            return null;
        return (DButtonControl)control;
    }
    /**
     * �ܹ��õ�����
     * @return boolean
     */
    public boolean canFocus()
    {
        return true;
    }
}
