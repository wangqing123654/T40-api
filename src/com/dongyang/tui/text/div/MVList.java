package com.dongyang.tui.text.div;

import com.dongyang.util.TList;
import java.awt.Graphics;
import com.dongyang.tui.text.EComponent;
import com.dongyang.tui.text.PM;
import com.dongyang.tui.text.MPage;
import com.dongyang.ui.TWindow;
import com.dongyang.tui.DText;
import com.dongyang.control.TControl;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.EPage;
import com.dongyang.tui.text.EPic;
import com.dongyang.tui.text.EMacroroutineModel;
import com.dongyang.tui.text.MView;

/**
 *
 * <p>Title: 图层管理器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.21
 * @version 1.0
 */
public class MVList
{
    /**
     * 父类
     */
    private EComponent parent;
    /**
     * 宏模型
     */
    private EMacroroutineModel macroroutineModel;
    /**
     * 成员列表
     */
    private TList list;
    /**
     * 属性窗口
     */
    private TWindow propertyWindow;
    /**
     * 开始原点X
     */
    private int startX;
    /**
     * 开始原点Y
     */
    private int startY;
    /**
     * 构造器
     */
    public MVList()
    {
        list = new TList();
    }
    /**
     * 构造器
     * @param parent EComponent
     */
    public MVList(EComponent parent)
    {
        this();
        setParent(parent);
    }
    /**
     * 构造器(宏专用)
     * @param macroroutineModel EMacroroutineModel
     */
    public MVList(EMacroroutineModel macroroutineModel)
    {
        this();
        setMacroroutineModel(macroroutineModel);
    }
    /**
     * 设置父类
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return EComponent
     */
    public EComponent getParent()
    {
        return parent;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        if(isMacroroutineModel())
            return getMacroroutineModel().getPM();
        if(getParent() == null)
            return null;
        if(getParent() instanceof MPage)
            return ((MPage)getParent()).getPM();
        if(getParent() instanceof EPic)
            return ((EPic)getParent()).getPM();
        return null;
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
     * 增加图层
     * @param mv MV
     */
    public void add(MV mv)
    {
        list.add(mv);
        mv.setParent(this);
    }
    /**
     * 增加图层
     * @return MV
     */
    public MV add()
    {
        MV mv = new MV();
        mv.setParent(this);
        add(mv);
        return mv;
    }
    /**
     * 删除图层
     * @param mv MV
     */
    public void remove(MV mv)
    {
        list.remove(mv);
    }
    /**
     * 删除图层
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    /**
     * 清除全部
     */
    public void removeAll()
    {
        DC();
        for(int i = 0;i < size();i++)
            get(i).DC();
        list.removeAll();
    }
    /**
     * 得到图层
     * @param index int
     * @return MV
     */
    public MV get(int index)
    {
        return (MV)list.get(index);
    }
    /**
     * 得到图层
     * @param name String
     * @return MV
     */
    public MV get(String name)
    {
        if(name == null || name.length() == 0)
            return null;
        for(int i = 0;i < size();i++)
        {
            MV mv = get(i);
            if(name.equals(mv.getName()))
                return mv;
        }
        return null;
    }
    /**
     * 总数
     * @return int
     */
    public int size()
    {
        return list.size();
    }
    /**
     * 绘制背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void paintBackground(Graphics g,int x,int y,int pageIndex)
    {
        setStartX(x);
        setStartY(y);
        for(int i = 0;i < size();i++)
        {
            MV mv = get(i);
            if(!mv.isVisible() || mv.isForeground())
                continue;
            mv.setPageIndex(pageIndex);
            mv.paint(g);
        }
    }
    /**
     * 绘制前景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void paintForeground(Graphics g,int x,int y,int pageIndex)
    {
        setStartX(x);
        setStartY(y);
        for(int i = 0;i < size();i++)
        {
            MV mv = get(i);
            if(!mv.isVisible() || !mv.isForeground())
                continue;
            mv.setPageIndex(pageIndex);
            mv.paint(g);
        }
    }
    /**
     * 绘制背景(打印)
     * @param g Graphics
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int pageIndex)
    {
        printBackground(g,0,0,pageIndex);
    }
    /**
     * 绘制背景(打印)
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int x,int y,int pageIndex)
    {
        setStartX(x);
        setStartY(y);
        for(int i = 0;i < size();i++)
        {
            MV mv = get(i);
            if(!mv.isVisible() || mv.isForeground())
                continue;
            mv.setPageIndex(pageIndex);
            mv.print(g);
        }
    }
    /**
     * 绘制前景(打印)
     * @param g Graphics
     * @param pageIndex int
     */
    public void printForeground(Graphics g,int pageIndex)
    {
        printForeground(g,0,0,pageIndex);
    }
    /**
     * 绘制前景(打印)
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void printForeground(Graphics g,int x,int y,int pageIndex)
    {
        setStartX(x);
        setStartY(y);
        for(int i = 0;i < size();i++)
        {
            MV mv = get(i);
            if(!mv.isVisible() || !mv.isForeground())
                continue;
            mv.setPageIndex(pageIndex);
            mv.print(g);
        }
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
     * 打开属性对话框
     */
    public void openProperty()
    {
        if(propertyWindow == null || propertyWindow.isClose())
        {
            propertyWindow = (TWindow) getUI().openWindow(
                    "%ROOT%\\config\\database\\MvListDialog.x", this, true);
            propertyWindow.setVisible(true);
        }
        else
            propertyWindow.setVisible(true);
    }
    /**
     * 修改参数
     * @param mv MV
     * @param div DIV
     * @param name String
     */
    public void modify(MV mv,DIV div,String name)
    {
        propertyModify(mv,div,name);
    }
    /**
     * 属性对话框修改参数
     * @param mv MV
     * @param div DIV
     * @param name String
     */
    public void propertyModify(MV mv,DIV div,String name)
    {
        if(propertyWindow == null || propertyWindow.isClose())
            return;
        TControl control = propertyWindow.getControl();
        if(control == null)
            return;
        control.callFunction("modify",mv,div,name);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeShort(-1);
        //保存项目
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            MV mv = get(i);
            if(mv == null)
                continue;
            mv.writeObject(s);
        }
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            switch (id)
            {
            case 1:
                break;
            }
            id = s.readShort();
        }
        //读取项目
        int count = s.readInt();
        for (int i = 0; i < count; i++)
        {
            MV mv = add();
            mv.readObject(s);
        }
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
    public int getEndXP()
    {
        if(isMacroroutineModel())
            return getMacroroutineModel().getWidth();
        if(getParent() instanceof MPage)
            return getPM().getPageManager().getWidth();
        if(getParent() instanceof EPic)
            return ((EPic)getParent()).getWidth();
        return 0;
    }
    public int getEndYP()
    {
        if(isMacroroutineModel())
            return getMacroroutineModel().getHeight();
        if(getParent() instanceof MPage)
            return getPM().getPageManager().getHeight();
        if(getParent() instanceof EPic)
            return ((EPic)getParent()).getHeight();
        return 0;
    }
    /**
     * 得到结束原点X
     * @return int
     */
    public int getEndX()
    {
        if(isMacroroutineModel())
            return (int)(getMacroroutineModel().getWidth() * getZoom() / 75.0 + 0.5);
        if(getParent() instanceof MPage)
            return (int)(getPM().getPageManager().getWidth() * getZoom() / 75.0 + 0.5);
        if(getParent() instanceof EPic)
            return (int)(((EPic)getParent()).getWidth() * getZoom() / 75.0 + 0.5);
        return 0;
    }
    /**
     * 得到结束原点Y
     * @return int
     */
    public int getEndY()
    {
        if(isMacroroutineModel())
            return (int)(getMacroroutineModel().getHeight() * getZoom() / 75.0 + 0.5);
        if(getParent() instanceof MPage)
            return (int)(getPM().getPageManager().getHeight() * getZoom() / 75.0 + 0.5);
        if(getParent() instanceof EPic)
            return (int)(((EPic)getParent()).getHeight() * getZoom() / 75.0 + 0.5);
        return 0;
    }
    /**
     * 设置开始原点X
     * @param startX int
     */
    public void setStartX(int startX)
    {
        this.startX = startX;
    }
    /**
     * 得到开始原点X
     * @return int
     */
    public int getStartX()
    {
        return startX;
    }
    /**
     * 设置开始原点Y
     * @param startY int
     */
    public void setStartY(int startY)
    {
        this.startY = startY;
    }
    /**
     * 得到开始原点Y
     * @return int
     */
    public int getStartY()
    {
        return startY;
    }
    /**
     * 设置宏模型
     * @param macroroutineModel EMacroroutineModel
     */
    public void setMacroroutineModel(EMacroroutineModel macroroutineModel)
    {
        this.macroroutineModel = macroroutineModel;
    }
    /**
     * 得到宏模型
     * @return EMacroroutineModel
     */
    public EMacroroutineModel getMacroroutineModel()
    {
        return macroroutineModel;
    }
    /**
     * 父类是否是宏模型
     * @return boolean
     */
    public boolean isMacroroutineModel()
    {
        return getMacroroutineModel() != null;
    }
    /**
     * 得到数据
     * @param column String
     * @return Object
     */
    public Object getMacroroutineData(String column)
    {
        if(!isMacroroutineModel())
            return null;
        
        return getMacroroutineModel().getData(column);
    }
}
