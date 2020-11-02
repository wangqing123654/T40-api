package com.dongyang.ui.edit;

import com.dongyang.ui.TComponent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TMovePane;
import java.awt.Container;
import com.dongyang.util.Log;

public class TUIBoardAdapter {
    /**
     * UI适配器
     */
    private TUIAdapter uiAdapter;
    /**
     * 是否拖动中
     */
    private boolean isMove;
    /**
     * 是否真正拖动(位置有改变)
     */
    private boolean dragged;
    /**
     * 选择框
     */
    private TMovePane selectPane;
    /**
     * 鼠标旧X位置
     */
    private int oldX;
    /**
     * 鼠标旧Y位置
     */
    private int oldY;
    /**
     * 是否新增组件装套
     */
    private boolean isCreateTComponent;
    /**
     * 日志系统
     */
    private Log log;
    /**
     * 构造器
     * @param uiAdapter TUIAdapter
     */
    public TUIBoardAdapter(TUIAdapter uiAdapter)
    {
        log = new Log();
        setUIAdapter(uiAdapter);
    }
    /**
     * 设置UI适配器
     * @param uiAdapter TUIAdapter
     */
    public void setUIAdapter(TUIAdapter uiAdapter)
    {
        this.uiAdapter = uiAdapter;
    }
    /**
     * 得到UI适配器
     * @return TUIAdapter
     */
    public TUIAdapter getUIAdapter()
    {
        return uiAdapter;
    }
    /**
     * 设置选择框
     * @param selectPane TMovePane
     */
    public void setSelectPane(TMovePane selectPane)
    {
        this.selectPane = selectPane;
    }
    /**
     * 得到选择框
     * @return TMovePane
     */
    public TMovePane getSelectPane()
    {
        return selectPane;
    }
    /**
     * 得到容器
     * @return Container
     */
    public Container getContainer()
    {
        TComponent component = getComponent();
        if(component == null)
            return null;
        if(!(component instanceof Container))
            return null;
        return (Container)component;
    }
    /**
     * 创建选择框
     */
    public void createSelectPane()
    {
        Container container = getContainer();
        if(container == null)
            return;
        setSelectPane(new TMovePane());
        getSelectPane().setStyle(1);
        container.add(getSelectPane(),0);
        container.repaint();
    }
    /**
     * 删除选择框
     */
    public void deleteSelectPane()
    {
        if(getSelectPane() == null)
            return;
        Container container = getContainer();
        if(container == null)
            return;
        container.remove(getSelectPane());
        container.repaint();
        setSelectPane(null);
    }
    /**
     * 移动选中框
     * @param x int
     * @param y int
     */
    public void moveSelectPane(int x,int y)
    {
        if(getSelectPane() == null)
            return;
        int x1 = oldX < x?oldX:x;
        int y1 = oldY < y?oldY:y;
        int w = oldX < x?x - oldX:oldX - x;
        int h = oldY < y?y - oldY:oldY - y;
        getSelectPane().setLocation(x1,y1);
        getSelectPane().setSize(w,h);
    }
    /**
     * 得到组件
     * @return TComponent
     */
    public TComponent getComponent()
    {
        if(getUIAdapter() == null)
            return null;
        return getUIAdapter().getComponent();
    }
    /**
     * 得到X
     * @return int
     */
    public int getX()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getX");
    }
    /**
     * 得到Y
     * @return int
     */
    public int getY()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getY");
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getWidth");
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getHeight");
    }
    /**
     * 初始化
     */
    public void onInit()
    {
        out("begin");
        TComponent component = getComponent();
        if(component == null)
            return;
        component.callFunction("initListeners");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_PRESSED,this,"onMousePressed");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_RELEASED,this,"onMouseReleased");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_ENTERED,this,"onMouseEntered");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_EXITED,this,"onMouseExited");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_DRAGGED,this,"onMouseDragged");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_MOVED,this,"onMouseMoved");
        out("end");
    }
    /**
     * 释放
     */
    public void destroy()
    {
        out("begin");
        TComponent component = getComponent();
        if(component == null)
            return;
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_PRESSED,this,"onMousePressed");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_PRESSED,this,"onMousePressed");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_RELEASED,this,"onMouseReleased");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_ENTERED,this,"onMouseEntered");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_EXITED,this,"onMouseExited");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_DRAGGED,this,"onMouseDragged");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_MOVED,this,"onMouseMoved");
        out("end");
    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        out("begin");
        if(e.getButton() != 1)
            return;
        oldX = e.getX();
        oldY = e.getY();
        if(getUIAdapter().onBoardPressed(oldX,oldY))
        {
            isCreateTComponent = true;
            out("end onBoardPressed true");
            return;
        }
        isMove = true;
        setDragged(false);
        out("end");
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        if(isCreateTComponent)
        {
            isCreateTComponent = false;
            return;
        }
        if(isDragged())
        {
            setDragged(false);
            deleteSelectPane();
            int x1 = oldX < e.getX()?oldX:e.getX();
            int y1 = oldY < e.getY()?oldY:e.getY();
            int x2 = oldX < e.getX()?e.getX():oldX;
            int y2 = oldY < e.getY()?e.getY():oldY;
            getUIAdapter().onBoardReleased(x1,y1,x2,y2);
            return;
        }
        setDragged(false);
        if(getWidth() - e.getX() > 0 && getHeight() - e.getY() > 0 && getUIAdapter() != null)
            getUIAdapter().onBoardClicked(e.getX(),e.getY());
    }
    /**
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
    }
    /**
     * 鼠标拖动
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        if(!isMove || isCreateTComponent)
            return;
        //创建选择框
        if(!isDragged())
            createSelectPane();
        //移动选择框
        moveSelectPane(e.getX(),e.getY());
        //设置真正拖动
        setDragged(true);
    }
    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }
    /**
     * 设置是否真正拖动
     * @param dragged boolean
     */
    public void setDragged(boolean dragged)
    {
        this.dragged = dragged;
    }
    /**
     * 是否真正拖动
     * @return boolean
     */
    public boolean isDragged()
    {
        return dragged;
    }
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        log.out(text);
    }

    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text, boolean debug) {
        log.out(text, debug);
    }

    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        log.err(text);
    }
}
