package com.dongyang.tui.text.div;

import com.dongyang.ui.TWindow;
import com.dongyang.tui.text.MPage;
import com.dongyang.tui.DText;
import com.dongyang.tui.text.PM;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.control.TControl;
import com.dongyang.tui.text.MView;
/**
 *
 * <p>Title: DIV父类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.22
 * @version 1.0
 */
public class DIVBase implements DIV
{
    /**
     * 父类
     */
    private MV parent;
    /**
     * 显示
     */
    private boolean visible = true;
    /**
     * 名称
     */
    private String name = "<none>";
    /**
     * 属性窗口
     */
    private TWindow propertyWindow;
    /**
     * 设置父类
     * @param parent MV
     */
    public void setParent(MV parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return MV
     */
    public MV getParent()
    {
        return parent;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        if(getParent() == null)
            return null;
        return getParent().getPM();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        PM pm = getPM();
        if(pm == null)
            return null;
        return pm.getUI();
    }
    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager()
    {
        PM pm = getPM();
        if(pm == null)
            return null;
        return pm.getPageManager();
    }
    /**
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }
    /**
     * 更新
     */
    public void update()
    {
        MPage mPage = getPageManager();
        if(mPage == null)
            return;
        mPage.update();
    }
    /**
     * 设置是否显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 设置属性窗口
     * @param propertyWindow TWindow
     */
    public void setPropertyWindow(TWindow propertyWindow)
    {
        this.propertyWindow = propertyWindow;
    }
    /**
     * 得到属性窗口
     * @return TWindow
     */
    public TWindow getPropertyWindow()
    {
        return propertyWindow;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "DIV";
    }
    /**
     * 得到类型
     * @return int
     */
    public int getTypeID()
    {
        return 0;
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
    }
    /* 绘制
     * @param g Graphics
     */
    public void print(Graphics g)
    {
    }
    /**
     * 得到属性对话框名称
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "";
    }
    /**
     * 打开属性对话框
     */
    public void openProperty()
    {
        String name = getPropertyDialogName();
        if(name == null || name.length() == 0)
            return;
        if(propertyWindow == null || propertyWindow.isClose())
        {
            propertyWindow = (TWindow) getUI().openWindow(name, this, true);
            propertyWindow.setVisible(true);
        }
        else
            propertyWindow.setVisible(true);
    }
    /**
     * 修改参数
     * @param name String
     */
    public void modify(String name)
    {
        getParent().modify(this,name);
    }
    /**
     * 属性对话框修改参数
     * @param name String
     */
    public void propertyModify(String name)
    {
        if(getPropertyWindow() == null || getPropertyWindow().isClose())
            return;
        TControl control = getPropertyWindow().getControl();
        if(control == null)
            return;
        control.callFunction("modify",name);
    }
    /**
     * 释放
     */
    public void DC()
    {
        if(propertyWindow == null || propertyWindow.isClose())
            return;
        propertyWindow.onClosed();
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
    }
    /**
     * 父类是否是宏模型
     * @return boolean
     */
    public boolean isMacroroutineModel()
    {
        if(getParent() == null)
            return false;
        return getParent().isMacroroutineModel();
    }
    /**
     * 得到数据
     * @param column String
     * @return Object
     */
    public Object getMacroroutineData(String column)
    {
        return getParent().getMacroroutineData(column);
    }
    /**
     * 得到页码
     * @return int
     */
    public int getPageIndex()
    {
        return getParent().getPageIndex();
    }
}
