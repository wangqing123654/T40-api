package com.dongyang.tui;

import com.dongyang.tui.base.DWindowBase;
import com.dongyang.tui.control.DWindowControl;
import com.dongyang.tui.control.DComponentControl;
import com.dongyang.ui.base.TCBase;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.JFrame;

/**
 *
 * <p>Title: 窗体</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.24
 * @version 1.0
 */
public class DWindow extends DComponent
{
    /**
     * 窗体宿主
     */
    private DWindowBase windowBase;
    /**
     * 地板宿主
     */
    private TCBase tcBase;
    /**
     * 构造器
     */
    public DWindow()
    {
        setBounds(10, 10, 500, 500);
        setBKColor(1,255,255);
        //setBorder("雕刻");
        setTag("DWindow");
        //setText("aaaaa");
        setHorizontalAlignment("中");
        setVerticalAlignment("中");
        //设置为顶级对象
        setTopComponent(true);
        //共享方法
        addPublicMethod("close",this);
    }
    /**
     * 得到窗体接口
     * @return DWindowControl
     */
    public DWindowControl getIOWindow()
    {
        DComponentControl control = getIOControlObject();
        if(control == null || !(control instanceof DWindowControl))
            return null;
        return (DWindowControl)control;
    }
    /**
     * 是否监听父类尺寸
     * @return boolean true 在监听 false 不再监听
     */
    public boolean isListenerParentResized()
    {
        return true;
    }
    /**
     * 得到组件尺寸
     * @return DRectangle
     */
    public DRectangle getComponentBounds()
    {
        if(windowBase != null)
        {
            Rectangle r = tcBase.getBounds();
            Insets insetsBase = tcBase.getInsets();
            return new DRectangle(0,0,r.width - insetsBase.left - insetsBase.right,
                                  r.height - insetsBase.top - insetsBase.bottom);
        }
        return getBounds();
    }
    /**
     * 得到窗口尺寸
     */
    public void setWindowSize()
    {
        if(windowBase == null)
            return;
        getBounds().setBounds(windowBase.getBounds());
    }
    /**
     * 显示
     */
    public void open(Frame frame)
    {
        initSystemUIManager();
        if(tcBase == null)
        {
            tcBase = new TCBase();
            tcBase.onInit();
            tcBase.addDComponent(this);
        }
        if(windowBase == null)
        {
            windowBase = new DWindowBase(frame,this);
            windowBase.getContentPane().add(tcBase);
        }else
        {
            //设置窗口位置
            windowBase.setLocation(getX(),getY());
            //设置窗口尺寸
            windowBase.setSize(getWidth(),getHeight());
        }
        String bkColor = getBKColor();
        if(bkColor != null && bkColor.length() > 0)
        {
            Color color = DColor.getColor(bkColor);
            tcBase.setBackground(color);
            windowBase.setBackground(color);
        }
        //打开事件
        if(!onOpen())
            return;
        windowBase.setVisible(isVisible());
        tcBase.grabFocus();
    }
    /**
     * 打开事件
     * @return boolean
     */
    public boolean onOpen()
    {
        DWindowControl control = getIOWindow();
        if(control != null)
            return control.onOpen();
        return true;
    }
    /**
     * 关闭动作
     */
    public void close()
    {
        if(!onClose())
            return;
        windowBase.dispose();
        //关闭之后事件
        onClosed();
    }
    /**
     * 关闭事件
     * @return boolean
     */
    public boolean onClose()
    {
        DWindowControl control = getIOWindow();
        if(control != null)
            return control.onClose();
        return true;
    }
    /**
     * 关闭之后事件
     */
    public void onClosed()
    {
        DWindowControl control = getIOWindow();
        if(control != null)
            control.onClosed();
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DWindow>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
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
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        if(windowBase == null)
            return super.getScreenPoint();
        return new DPoint(getX(),getY());
    }
    /**
     * 左键按下
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        return false;
    }
    public static void main(String args[])
    {
        com.dongyang.util.TDebug.initClient();
        JFrame frame = new JFrame();
        DWindow f = new DWindow();
        //f.setUIEditStatus(true);
        f.open(null);
    }
}
