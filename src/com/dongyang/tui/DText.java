package com.dongyang.tui;

import com.dongyang.tui.text.PM;
import java.awt.Graphics;
import com.dongyang.tui.text.MView;
import com.dongyang.tui.text.MEvent;
import com.dongyang.tui.text.MFile;
import com.dongyang.tui.text.MFocus;

/**
 *
 * <p>Title: 文本编辑器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @version 1.0
 */
public class DText extends DComponent
{
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 显示横向滚动条
     */
    private boolean isShowHScrollBar;
    /**
     * 显示纵向滚动条
     */
    private boolean isShowVScrollBar;
    /**
     * 是否阅览
     */
    private boolean isPreview;
    /**
     * 构造器
     */
    public DText()
    {
        pm = new PM(this);
        setBKColor("白");
        addPublicMethod("hScrollBarChangeValue",this);
        addPublicMethod("vScrollBarChangeValue",this);
        //新文件
        getFileManager().onNewFile();
    }

    /**
     * 是报表
     * @param isReport boolean
     */
    public DText(boolean isReport){
        pm = new PM(this,isReport);
        setBKColor("白");
        addPublicMethod("hScrollBarChangeValue",this);
        addPublicMethod("vScrollBarChangeValue",this);
        //新文件
        getFileManager().onNewFile();

    }

    /**
     * 得到试图管理器
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到事件管理器
     * @return MEvent
     */
    public MEvent getEventManager()
    {
        return getPM().getEventManager();
    }
    /**
     * 得到文件管理器
     * @return MFile
     */
    public MFile getFileManager()
    {
        return getPM().getFileManager();
    }
    /**
     * 得到焦点管理器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 设置显示横向滚动条
     * @param isShow boolean
     */
    public void setShowHScrollBar(boolean isShow)
    {
        if(isShowHScrollBar == isShow)
            return;
        isShowHScrollBar = isShow;
        if(!isShow)
        {
            DScrollBar hScrollBar = getHScrollBar();
            if(hScrollBar != null)
            {
                hScrollBar.removeLinkComponent();
                removeDComponent(hScrollBar);
                DScrollBar vScrollBar = getVScrollBar();
                if(vScrollBar != null)
                    vScrollBar.setLinkComponent(2,this,6,0);
                repaint();
            }
            return;
        }
        DScrollBar hScrollBar = new DScrollBar();
        hScrollBar.setOrientation(DScrollBar.HORIZONTAL);
        hScrollBar.setTag("HScrollBar");
        hScrollBar.setNoUIEdit(true);
        hScrollBar.setActionMap("onChangeValue","hScrollBarChangeValue");
        addDComponent(hScrollBar);
        hScrollBar.setLinkComponent(1,this,6,17);
        hScrollBar.setLinkComponent(3,this,7);
        hScrollBar.setLinkComponent(4,this,8,isShowVScrollBar?17:0);
        DScrollBar vScrollBar = getVScrollBar();
        if(vScrollBar != null)
            vScrollBar.setLinkComponent(2,this,6,17);
        repaint();
        return;
    }
    /**
     * 是否显示横向滚动条
     * @return boolean
     */
    public boolean isShowHScrollBar()
    {
        return isShowHScrollBar;
    }
    /**
     * 设置显示横向滚动条
     * @param isShow boolean
     */
    public void setShowVScrollBar(boolean isShow)
    {
        if(isShowVScrollBar == isShow)
            return;
        isShowVScrollBar = isShow;
        if(!isShow)
        {
            DScrollBar vScrollBar = getVScrollBar();
            if(vScrollBar != null)
            {
                vScrollBar.removeLinkComponent();
                removeDComponent(vScrollBar);
                DScrollBar hScrollBar = getHScrollBar();
                if(hScrollBar != null)
                    hScrollBar.setLinkComponent(4,this,8,0);
                repaint();
            }
            return;
        }
        DScrollBar vScrollBar = new DScrollBar();
        vScrollBar.setMouseWheelValue(50);
        vScrollBar.setOrientation(DScrollBar.VERTICAL);
        vScrollBar.setTag("VScrollBar");
        vScrollBar.setNoUIEdit(true);
        vScrollBar.setActionMap("onChangeValue","vScrollBarChangeValue");
        addDComponent(vScrollBar);
        vScrollBar.setLinkComponent(1,this,5);
        vScrollBar.setLinkComponent(2,this,6,isShowHScrollBar?17:0);
        vScrollBar.setLinkComponent(3,this,8,17);
        DScrollBar hScrollBar = getHScrollBar();
        if(hScrollBar != null)
            hScrollBar.setLinkComponent(4,this,8,17);
        repaint();
        return;
    }
    /**
     * 是否显示横向滚动条
     * @return boolean
     */
    public boolean isShowVScrollBar()
    {
        return isShowVScrollBar;
    }
    /**
     * 得到纵向滚动条控件
     * @return DScrollBar
     */
    public DScrollBar getVScrollBar()
    {
        return (DScrollBar)getDComponent("VScrollBar");
    }
    /**
     * 得到横向滚动条控件
     * @return DScrollBar
     */
    public DScrollBar getHScrollBar()
    {
        return (DScrollBar)getDComponent("HScrollBar");
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 纵向滚动条值改变
     */
    public void vScrollBarChangeValue()
    {
        //事件传递
        getEventManager().onVScrollBarChangeValue(getVScrollBar().getValue());
        repaint();
    }
    /**
     * 横向滚动条值改变
     */
    public void hScrollBarChangeValue()
    {
        repaint();
    }
    /**
     * 尺寸改变
     * @return boolean
     */
    public boolean onComponentResized()
    {
        super.onComponentResized();
        //事件传递
        if(getEventManager() != null)
            getEventManager().onComponentResized();
        return true;
    }
    /**
     * 设置默认光标
     */
    public void setDefaultCursor()
    {
    }
    /**
     * 鼠标移动
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        if(super.onMouseMoved())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            if(getEventManager().onMouseMoved())
                return true;
        setCursor(DCursor.DEFAULT_CURSOR);
        return false;
    }
    /**
     * 鼠标进入
     * @return boolean
     */
    public boolean onMouseEntered()
    {
        if(super.onMouseEntered())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseEntered();
        return false;
    }
    /**
     * 鼠标离开
     * @return boolean
     */
    public boolean onMouseExited()
    {
        if(super.onMouseExited())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseExited();
        return false;
    }
    /**
     * 双击
     * @return boolean
     */
    public boolean onDoubleClickedS()
    {
        if(super.onDoubleClickedS())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onDoubleClickedS();
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
        if(getFocus() != this)
            grabFocus();
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseLeftPressed();
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
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseLeftReleased();
        return false;
    }
    /**
     * 右键按下
     * @return boolean
     */
    public boolean onMouseRightPressed()
    {
        if(super.onMouseRightPressed())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseRightPressed();
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
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseDragged();
        return false;
    }
    /**
     * 鼠标滑轮
     * @return boolean
     */
    public boolean onMouseWheelMoved()
    {
        if(super.onMouseWheelMoved())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            if(getEventManager().onMouseWheelMoved())
                return true;
        //纵向滚动条事件传递
        if(isShowVScrollBar)
            getVScrollBar().toMouseWheelMoved(getMouseWheelMoved());
        return false;
    }
    /**
     * 得到焦点事件
     * @return boolean
     */
    public boolean onFocusGained()
    {
        if(super.onFocusGained())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onFocusGained();
        return false;
    }
    /**
     * 失去焦点事件
     * @return boolean
     */
    public boolean onFocusLost()
    {
        if(super.onFocusLost())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onFocusLost();
        return false;
    }
    /**
     * 键盘按下
     * @return boolean
     */
    public boolean onKeyPressed()
    {
        if(super.onKeyPressed())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onKeyPressed();
        return false;
    }
    /**
     * 键盘抬起
     * @return boolean
     */
    public boolean onKeyReleased()
    {
        if(super.onKeyReleased())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onKeyReleased();
        return false;
    }
    /**
     * 击打
     * @return boolean
     */
    public boolean onKeyTyped()
    {
        if(super.onKeyTyped())
            return true;
        //事件传递
        if(!isPreview() && getEventManager() != null)
            getEventManager().onKeyTyped();
        return false;
    }
    /**
     * 绘制背景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int width,int height)
    {
        super.paintBackground(g,width,height);
        if(pm == null)
            return;
        Graphics gc = g;
        if(isShowHScrollBar && isShowVScrollBar)
        {
            width -= 17;
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowHScrollBar)
        {
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowVScrollBar)
        {
            width -= 17;
            gc = g.create(0,0,width,height);
        }
        //com.dongyang.util.DebugUsingTime.add("paintBackground start");
        if(getViewManager() != null)
            getViewManager().paintBackground(gc,width,height);
        //com.dongyang.util.DebugUsingTime.add("paintBackground end");
    }
    /**
     * 绘制前景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {
        Graphics gc = g;
        if(pm == null)
            return;
        if(isShowHScrollBar && isShowVScrollBar)
        {
            width -= 17;
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowHScrollBar)
        {
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowVScrollBar)
        {
            width -= 17;
            gc = g.create(0,0,width,height);
        }
        if(getViewManager() != null)
            getViewManager().paintForeground(gc,width,height);
    }
    /**
     * 能够得到焦点
     * @return boolean
     */
    public boolean canFocus()
    {
        return isEnabled();
    }
    /**
     * 设置阅览
     * @param isPreview boolean
     */
    public void setPreview(boolean isPreview)
    {
        this.isPreview = isPreview;
    }
    /**
     * 是否阅览
     * @return boolean
     */
    public boolean isPreview()
    {
        return isPreview;
    }
}
