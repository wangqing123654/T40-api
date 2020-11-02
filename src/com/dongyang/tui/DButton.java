package com.dongyang.tui;

import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import com.dongyang.ui.util.TDrawTool;
import com.dongyang.tui.control.DButtonControl;
import com.dongyang.tui.control.DComponentControl;

/**
 * <p>Title: 按钮</p>
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
     * 构造器
     */
    public DButton()
    {
    }
    /**
     * 设置默认边框尺寸
     */
    public void setDefaultInsets()
    {
        getInsets().setInsets(4,4,4,4);
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
        //设置焦点
        grabFocus();
        repaintButtonBorder();
        return false;
    }
    /**
     * 左键抬起
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        super.onMouseLeftReleased();
        repaintButtonBorder();
        if(inMouse())
            //点击动作
            onClicked();
        return false;
    }
    /**
     * 鼠标拖动
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
     * 点击事件
     */
    public void onClicked()
    {
        DButtonControl control = getIOFrame();
        if(control != null)
            control.onClicked();
        runActionMap("onClicked");
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
     * 得到焦点事件
     * @return boolean
     */
    public boolean onFocusGained()
    {
        super.onFocusGained();
        repaintButtonBorder();
        return false;
    }
    /**
     * 失去焦点事件
     * @return boolean
     */
    public boolean onFocusLost()
    {
        super.onFocusLost();
        repaintButtonBorder();
        return false;
    }
    /**
     * 得到按钮边框绘制状态
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
     * 绘制边框
     * @param g Graphics
     */
    public void paintBorder(Graphics g)
    {
        if(getBorder() != null && getBorder().length() >= 0)
        {
            super.paintBorder(g);
            return;
        }
        //绘制按钮边框
        paintButtonBorder(g);
    }
    /**
     * 绘制按钮边框
     * @param g Graphics
     */
    public void paintButtonBorder(Graphics g)
    {
        DBorder.paintButton(this,g,getButtonBorderDrawState());
    }
    /**
     * 绘制前景
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
     * 得到按钮接口
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
     * 能够得到焦点
     * @return boolean
     */
    public boolean canFocus()
    {
        return true;
    }
}
