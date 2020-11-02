package com.dongyang.tui.text;

import com.dongyang.util.StringTool;
import com.dongyang.util.TList;
import com.dongyang.tui.DText;
import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.div.MVList;
import com.dongyang.tui.text.div.MV;
import com.dongyang.tui.text.div.VLine;
import com.dongyang.ui.TWindow;
import java.util.List;

/**
 *
 * <p>Title: 图区组件</p>
 *
 * <p>Description:11 </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.29
 * @version 1.0
 */
public class EPic implements IBlock,EEvent
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 父类
     */
    private EComponent parent;
    /**
     * 名称
     */
    private String name;
    /**
     * 图层管理器
     */
    private MVList mvList;
    /**
     * 显示
     */
    private boolean visible = true;
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;
    /**
     * 宽度
     */
    private int width = 100;
    /**
     * 高度
     */
    private int height = 100;
    /**
     * 修改状态
     */
    private boolean modify;
    /**
     * 位置
     * 0 中间
     * 1 行首
     * 2 行尾
     */
    private int position;
    /**
     * 模型
     */
    private EPicModel model;
    /**
     * 图表
     */
    private EPicData picData;
    /**
     * 属性窗口
     */
    private TWindow propertyWindow;
    /**
     * 构造器
     */
    public EPic()
    {
        components = new TList();
        //创建图层管理器
        mvList = new MVList(this);
    }
    /**
     * 初始化新Pic
     */
    public void initNew()
    {
        MV mv = mvList.add();
        mv.setName("边框");
        VLine line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("上边");
        line.setXYB(false,false,true,false);

        line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("下边");
        line.setXYB(false,true,true,true);

        line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("左边");
        line.setXYB(false,false,false,true);

        line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("右边");
        line.setXYB(true,false,true,true);

    }
    /**
     * 构造器
     * @param panel EPanel
     */
    public EPic(EPanel panel)
    {
        this();
        setParent(panel);
    }
    /**
     * 得到图层管理器
     * @return MV
     */
    public MVList getMVList()
    {
        return mvList;
    }
    /**
     * 创建图层管理器
     */
    public void createMVList()
    {
        mvList = new MVList(this);
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
     * 得到面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        EComponent com = getParent();
        if(com == null || !(com instanceof EPanel))
            return null;
        return (EPanel)com;
    }
    /**
     * 得到页面
     * @return EPage
     */
    public EPage getPage()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return null;
        return panel.getPage();
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getPage().getPM();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
            return null;
        return getPM().getUI();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 增加成员
     * @param com EComponent
     */
    public void add(EComponent com)
    {
        if(com == null)
            return;
        components.add(com);
        com.setParent(this);
    }
    /**
     * 增加成员
     * @param index int
     * @param com EComponent
     */
    public void add(int index,EComponent com)
    {
        if(com == null)
            return;
        components.add(index,com);
        com.setParent(this);
    }
    /**
     * 查找位置
     * @param com EComponent
     * @return int
     */
    public int indexOf(EComponent com)
    {
        if(com == null)
            return -1;
        return components.indexOf(com);
    }
    /**
     * 删除成员
     * @param com EComponent
     */
    public void remove(EComponent com)
    {
        components.remove(com);
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * 得到成员
     * @param index int
     * @return EComponent
     */
    public EComponent get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (EComponent)components.get(index);
    }
    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * 设置横坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 得到横坐标
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到纵坐标
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        setX(x);
        setY(y);
    }
    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * 设置边框
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setBounds(int x,int y,int width,int height)
    {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * 设置显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        if(this.visible == visible)
            return;
        this.visible = visible;
        setModify(true);
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
     * 绘制背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void paintBackground(Graphics g,int x,int y,int pageIndex)
    {
        getMVList().paintBackground(g,x,y,pageIndex);
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
        getMVList().paintForeground(g,x,y,pageIndex);
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
        getMVList().printBackground(g,x,y,pageIndex);
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
        getMVList().printForeground(g,x,y,pageIndex);
    }
    /**
     * 绘制
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int x,int y,int width,int height)
    {
        if(!isVisible())
            return;
        //绘制背景
        paintBackground(g,x,y,0);
        //绘制背景
        if(getModel() != null)
            getModel().paintBackground(g,x,y,width,height);
        //绘制图表
        if(getPicData() != null)
            getPicData().paint(g,width,height);
        //绘制前景
        if(getModel() != null)
            getModel().paintForeground(g,x,y,width,height);
        paintForeground(g,x,y,0);
    }
    /**
     * 打印
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g,int x,int y)
    {
        if(!isVisible())
            return;
        //绘制背景
        printBackground(g,x,y,0);
        //绘制背景
        if(getModel() != null)
            getModel().paintBackground(g,x,y,width,height);
        //绘制图表
        if(getPicData() != null)
        {
            Graphics gc = g.create(x,y,getWidth() + 10,getHeight() + 10);
            getPicData().paint(gc, getWidth(), getHeight());
        }
        //绘制前景
        if(getModel() != null)
            getModel().paintForeground(g,x,y,width,height);
        printForeground(g,x,y,0);
    }
    /**
     * 左键按下
     * @param mouseX int
     * @param mouseY int
     * @return int 点选类型
     * -1 没有选中
     * 1 EText
     * 2 ETD
     * 3 ETR Enter
     * 4 EPic
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        //设置焦点
        setFocus();
        //鼠标所在TR
        /*int trIndex = getMouseInTRIndex(mouseX,mouseY);
        if(trIndex != -1)
        {
            ETR tr = get(trIndex);
            int x = mouseX - tr.getX();
            int y = mouseY - tr.getY();
            //事件传递
            tr.onMouseLeftPressed(x,y);
        }*/
        return 4;
    }
    /**
     * 右键按下
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {

    }
    /**
     * 设置焦点
     */
    public void setFocus()
    {
        setFocus(this);
    }
    /**
     * 设置焦点
     * @param com EComponent
     */
    public void setFocus(EPic com)
    {
        getFocusManager().setFocusPic(com);
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        if(getModel() == null)
            return false;
        return getModel().onDoubleClickedS(mouseX,mouseY);
    }
    /**
     * 设置是否修改状态
     * @param modify boolean
     */
    public void setModify(boolean modify)
    {
        if(this.modify == modify)
            return;
        if(modify)
        {
            this.modify = true;
            if(getParent() != null)
                getParent().setModify(true);
            return;
        }
        /*for(int i = 0;i < size();i++)
        {
            ETR tr = get(i);
            if(tr == null)
                continue;
            if(tr.isModify())
                return;
        }*/
        this.modify = false;
        if(getParent() != null)
            getParent().setModify(false);
    }
    /**
     * 是否修改
     * @return boolean
     */
    public boolean isModify()
    {
        return modify;
    }
    /**
     * 调整尺寸
     */
    public void reset()
    {
        if(!isModify())
            return;
        reset(0);
        setModify(false);
    }
    /**
     * 调整尺寸
     * @param index int
     */
    public void reset(int index)
    {

    }
    /**
     * 设置位置
     * @param position int
     */
    public void setPosition(int position)
    {
        this.position = position;
    }
    /**
     * 得到位置
     * @return int
     */
    public int getPosition()
    {
        return position;
    }
    /**
     * 查找组件位置
     * @param pic EPic
     * @return int
     */
    public int findIndex(EPic pic)
    {
        EPanel panel = getPanel();
        if(panel == null)
            return -1;
        return panel.indexOf(pic);
    }
    /**
     * 查找自己的位置
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
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
        return panel.getIndexString() + ",Pic:" + findIndex();
    }
    public String toString()
    {
        return "<EPic size=" + size() +
                ",visible=" + isVisible() +
                ",index=" + getIndexString() +
                ">";
    }
    /**
     * 调试对象组成
     * @param i int
     */
    public void debugShow(int i)
    {
        System.out.println(StringTool.fill(" ",i) + this);
    }
    /**
     * 调试修改
     * @param i int
     */
    public void debugModify(int i)
    {
        if(!isModify())
            return;
        System.out.println(StringTool.fill(" ",i) + getIndexString() + " " + this);
        /*for(int j = 0;j < size();j++)
        {
            ETR tr = get(j);
            tr.debugModify(i + 2);
        }*/
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeInt(1,getWidth(),0);
        s.writeInt(2,getHeight(),0);
        if(getModel() != null)
        {
            s.writeShort(3);
            getModel().writeObject(s);
        }
        s.writeString(4,getName(),"");
        s.writeShort(5);
        getMVList().writeObject(s);
        if(getPicData() != null)
        {
            s.writeShort(6);
            getPicData().writeObject(s);
        }
        s.writeBoolean(7,isVisible(),true);
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
        switch (id)
        {
        case 1:
            setWidth(s.readInt());
            return true;
        case 2:
            setHeight(s.readInt());
            return true;
        case 3:
            createModel();
            getModel().readObject(s);
            return true;
        case 4:
            setName(s.readString());
            return true;
        case 5:
            getMVList().readObject(s);
            return true;
        case 6:
            createPicData();
            getPicData().readObject(s);
            return true;
        case 7:
            setVisible(s.readBoolean());
            return true;
        }
        return false;
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s,int c)
      throws IOException
    {
        s.WO("<EPic>", c);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
        //保存页
        s.writeInt(size());
        /*for(int i = 0;i < size();i ++)
        {
            ETR tr = get(i);
            tr.writeObject(s);
        }*/
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
            //读对象属性
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //读取行
        int count = s.readInt();
        /*for(int i = 0;i < count;i++)
        {
            ETR tr = newTR();
            tr.readObject(s);
        }*/
    }
    /**
     * 得到路径
     * @param list TList
     */
    public void getPath(TList list)
    {
        if(list == null)
            return;
        list.add(0,this);
        getPanel().getPath(list);
    }
    /**
     * 得到路径索引
     * @param list TList
     */
    public void getPathIndex(TList list)
    {
        if(list == null)
            return;
        list.add(0,findIndex());
        getPanel().getPathIndex(list);
    }
    /**
     * 设置模型
     * @param model EPicModel
     */
    public void setModel(EPicModel model)
    {
        this.model = model;
    }
    /**
     * 得到模型
     * @return EPicModel
     */
    public EPicModel getModel()
    {
        return model;
    }
    /**
     * 创建模型
     * @return EPicModel
     */
    public EPicModel createModel()
    {
        EPicModel model = new EPicModel();
        model.setPic(this);
        setModel(model);
        return model;
    }
    /**
     * 删除自己
     */
    public void removeThis()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return;
        int index = panel.indexOf(this);
        if(index > 0)
            panel.get(index - 1).setModify(true);
        if(panel.size() == 0)
        {
            EPanel p = panel.getNextPanel();
            if(p != null)
                p.setModify(true);
        }
        panel.remove(this);
        if(getModel() != null)
            getModel().destroy();
        panel.setModify(true);
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
     * 设置图表对象
     * @param picData EPicData
     */
    public void setPicData(EPicData picData)
    {
        this.picData = picData;
        picData.setParent(this);
    }
    /**
     * 得到图表对象
     * @return EPicData
     */
    public EPicData getPicData()
    {
        return picData;
    }
    /**
     * 创建图表
     */
    public void createPicData()
    {
        setPicData(new EPicData());
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
     * 图表配置对话框
     */
    public void openDataProperty()
    {
        if(propertyWindow == null || propertyWindow.isClose())
            propertyWindow = (TWindow)getUI().openWindow("%ROOT%\\config\\database\\PicDataDialog.x", this ,true);
        else
            propertyWindow.setVisible(true);
    }
    /**
     * 更新
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action)
    {
    }
    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType()
    {
        return PIC_TYPE;
    }
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        if(name == null || name.length() == 0)
        {
            if(getObjectType() == type)
                return this;
            return null;
        }
        if(name.equals(getName()) && getObjectType() == type)
            return this;
        return null;
    }
    /**
     * 过滤对象
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type)
    {

    }
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        return null;
    }
    /**
     * 得到组值
     * @param group String
     * @return String
     */
    public String findGroupValue(String group)
    {
        return null;
    }
    /**
     * 得到块值
     * @return String
     */
    public String getBlockValue()
    {
        return "";
    }
    /**
     * 得到上一个真块
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock()
    {
        return null;
    }
    /**
     * 得到下一个真块
     * @return IBlock
     */
    public IBlock getNextTrueBlock()
    {
        return null;
    }
    /**
     * 克隆
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        return null;
    }
    /**
     * 整理
     */
    public void arrangement()
    {

    }
}
