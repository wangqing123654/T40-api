package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.DInsets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import com.dongyang.tui.DText;
import com.dongyang.util.StringTool;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import javax.print.PrintService;
import com.dongyang.tui.io.EPageable;
import java.awt.print.Printable;
import java.awt.Graphics2D;
import com.dongyang.tui.text.div.MV;
import com.dongyang.tui.text.div.MVList;
import com.dongyang.data.TParm;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: 页管理类</p>
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
public class MPage implements EComponent,Printable
{
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 页面宽度
     */
    private double pageWidth;
    /**
     * 页面高度
     */
    private double pageHeight;
    /**
     * 页宽度
     */
    private int width;
    /**
     * 页高度
     */
    private int height;
    /**
     * 内部尺寸
     */
    private DInsets insets;
    private DInsets editInsets;
    /**
     * 修改状态
     */
    private boolean modify;
    /**
     * 打印方向
     */
    private int orientation = PageFormat.PORTRAIT;
    /**
     * 设置打印有效尺寸X
     */
    private double imageableX;
    /**
     * 设置打印有效尺寸Y
     */
    private double imageableY;
    /**
     * 设置打印有效尺寸宽度
     */
    private double imageableWidth;
    /**
     * 设置打印有效尺寸高度
     */
    private double imageableHeight;
    /**
     * 图层管理器
     */
    private MVList mvList;
    /**
     * 打印页码类型
     */
    private int printPageNo;
    /**
     * 打印页码编号
     */
    private String printPageText;
    /**
     * 打印页数
     */
    private int printCount = 1;
    /**
     * 逐份打印
     */
    private boolean printzf = true;
    /**
     * 打印页码列表
     */
    private List printNOList;
    /**
     * 须打起始行号
     */
    private int printXDStartRow = 0;
    /**
     * 须打结束行号
     */
    private int printXDEndRow = 0;
    /**
     * 是否须打
     */
    private boolean isPrintXD;
    /**
     * 是否打印背景
     */
    private boolean isPrintXDBak;
    /**
     * 构造器
     */
    public MPage()
    {
        components = new TList();
        //setWidth(796);//230
        //setHeight(1200);//330 1200
        setInsets(new DInsets(0,0,0,0));
        setEditInsets(new DInsets(0,0,0,0));
        setPageWidth(595.275590551181);
        setPageHeight(841.8897637795276);
        setImageableX(70.86614173228347);
        setImageableY(70.86614173228347);
        setImageableWidth(453.54330708661416);
        setImageableHeight(700.1574803149606);
        //创建图层管理器
        mvList = new MVList(this);
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
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
     * 得到图层管理器
     * @return MV
     */
    public MVList getMVList()
    {
        return mvList;
    }
    public void setMVList(MVList mvList)
    {
        this.mvList = mvList;
        mvList.setParent(this);
    }
    /**
     * 得到字符数据管理器
     * @return MString
     */
    public MString getStringManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getStringManager();
    }
    /**
     * 得到显示层
     * @return MView
     */
    public MView getViewManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getViewManager();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFocusManager();
    }
    /**
     * 得到文件管理器
     * @return MFile
     */
    public MFile getFileManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFileManager();
    }
    /**
     * 设置焦点位置
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * 得到焦点位置
     * @return int
     */
    public int getFocusIndex()
    {
        if(getFocusManager() == null)
            return 0;
        return getFocusManager().getFocusIndex();
    }
    /**
     * 得到焦点
     * @return EComponent
     */
    public EComponent getFocus()
    {
        if(getFocusManager() == null)
            return null;
        return getFocusManager().getFocus();
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
     * 设置横坐标
     * @param x int
     */
    public void setX(int x)
    {
    }
    /**
     * 得到横坐标
     * @return int
     */
    public int getX()
    {
        return 0;
    }
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
    }
    /**
     * 得到纵坐标
     * @return int
     */
    public int getY()
    {
        return 0;
    }
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
    }
    /**
     * 设置页面宽度
     * @param pageWidth double
     */
    public void setPageWidth(double pageWidth)
    {
        this.pageWidth = pageWidth;
        setWidth((int)pageWidth);
    }
    /**
     * 得到页面宽度
     * @return double
     */
    public double getPageWidth()
    {
        return pageWidth;
    }
    /**
     * 设置页面高度
     * @param pageHeight double
     */
    public void setPageHeight(double pageHeight)
    {
        this.pageHeight = pageHeight;
        setHeight((int)pageHeight);
    }
    /**
     * 得到页面高度
     * @return double
     */
    public double getPageHeight()
    {
        return pageHeight;
    }
    /**
     * 设置打印有效尺寸X
     * @param imageableX double
     */
    public void setImageableX(double imageableX)
    {
        this.imageableX = imageableX;
        getInsets().left = (int)imageableX;
    }
    /**
     * 得到打印有效尺寸X
     * @return double
     */
    public double getImageableX()
    {
        return imageableX;
    }
    /**
     * 设置打印有效尺寸Y
     * @param imageableY double
     */
    public void setImageableY(double imageableY)
    {
        this.imageableY = imageableY;
        getInsets().top = (int)imageableY;
    }
    /**
     * 得到打印有效尺寸Y
     * @return double
     */
    public double getImageableY()
    {
        return imageableY;
    }
    /**
     * 设置打印有效尺寸宽度
     * @param imageableWidth double
     */
    public void setImageableWidth(double imageableWidth)
    {
        this.imageableWidth = imageableWidth;
        getInsets().right = (int)(getPageWidth() - imageableWidth - imageableX);
    }
    /**
     * 得到打印有效尺寸宽度
     * @return double
     */
    public double getImageableWidth()
    {
        return imageableWidth;
    }
    /**
     * 设置打印有效尺寸高度
     * @param imageableHeight double
     */
    public void setImageableHeight(double imageableHeight)
    {
        this.imageableHeight = imageableHeight;
        getInsets().bottom = (int)(getPageHeight() - imageableHeight - imageableY);
    }
    /**
     * 得到打印有效尺寸高度
     * @return double
     */
    public double getImageableHeight()
    {
        return imageableHeight;
    }
    /**
     * 设置页面宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        if(this.width == width)
            return;
        int value = width - (int)this.width;
        this.width = width;
        if(getViewManager() == null)
            return;
        if(getViewManager().getViewStyle() == 0)
            return;
        for(int i = 0;i < size();i++)
        {
            EPage page = get(i);
            if(page == null)
                continue;
            page.onChanglePagehWidth(value);
        }
    }
    /**
     * 得到页面宽度
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * 设置页面高度
     * @param height int
     */
    public void setHeight(int height)
    {
        if(this.height == height)
            return;
        int value = height - (int)this.height;
        this.height = height;
        if(getViewManager() == null)
            return;
        if(getViewManager().getViewStyle() == 0)
            return;
        for(int i = 0;i < size();i++)
        {
            EPage page = get(i);
            if(page == null)
                continue;
            page.onChanglePagehHeight(value);
        }
    }
    /**
     * 得到页面高度
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * 设置内部尺寸
     * @param insets DInsets
     */
    public void setInsets(DInsets insets)
    {
        this.insets = insets;
    }
    /**
     * 得到内部尺寸
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return insets;
    }
    /**
     * 增加成员
     * @param page EPage
     */
    public void add(EPage page)
    {
        if (page == null)
            return;
        components.add(page);
        page.setParent(this);
        //if(getViewManager() != null)
        //    getViewManager().resetSize();
    }

    /**
     * 删除成员
     * @param page EPage
     */
    public void remove(EPage page)
    {
        components.remove(page);
        //if(getViewManager() != null)
        //    getViewManager().resetSize();
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
     * @return EPage
     */
    public EPage get(int index)
    {
        return (EPage) components.get(index);
    }
    /**
     * 查找位置
     * @param page EPage
     * @return int
     */
    public int indexOf(EPage page)
    {
        if(page == null)
            return -1;
        return components.indexOf(page);
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
     * 设置父类
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
    }
    /**
     * 得到父类
     * @return EComponent
     */
    public EComponent getParent()
    {
        return null;
    }
    /**
     * 设置纸张方向
     * @param orientation int
     */
    public void setOrientation(int orientation)
    {
        this.orientation = orientation;
    }
    /**
     * 得到纸张方向
     * @return int
     */
    public int getOrientation()
    {
        return orientation;
    }
    /**
     * 得到基础最大宽度
     * @return int
     */
    public int getBaseMaxWidth()
    {
        int width = 0;
        for(int i = 0;i < size();i ++)
        {
            EPage page = get(i);
            if(page == null)
                continue;
            width = Math.max(width,page.getBaseWidth());
        }
        return width;
    }
    /**
     * 得到基础最大高度
     * @return int
     */
    public int getBaseMaxHeight()
    {
        int height = 0;
        for(int i = 0;i < size();i++)
        {
            EPage page = get(i);
            if(page == null)
                continue;
            height += page.getBaseHeight() - page.getY();
        }
        return height;
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
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int pageIndex)
    {
        getMVList().printBackground(g,pageIndex);
    }
    /**
     * 绘制前景(打印)
     * @param g Graphics
     * @param pageIndex int
     */
    public void printForeground(Graphics g,int pageIndex)
    {
        getMVList().printForeground(g,pageIndex);
    }
    /**
     * 全部清除
     */
    public void removeAll()
    {
        components.removeAll();
        getMVList().removeAll();
    }
    /**
     * 新页
     * @return EPage
     */
    public EPage newPage()
    {
        EPage page = new EPage(this);
        add(page);
        //EPanel panel = page.newPanel();
        //EText text = panel.newText();
        //text.resetSize();
        /*if(getFocus() == null)
        {
            setFocus(text);
            setFocusIndex(0);
        }*/
        //page.resetSize();
        return page;
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
            return;
        }
        for(int i = 0;i < size();i++)
        {
            EComponent com = get(i);
            if(com == null)
                continue;
            if(com.isModify())
                return;
        }
        this.modify = false;
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
        getViewManager().resetSize();
        resetRowID();
    }
    /**
     * 调整尺寸
     * @param index int
     */
    public void reset(int index)
    {
        if(index < 0 || index >= size())
            return;
        for(int i = index;i < size();i++)
        {
            EPage page = get(i);
            if (page == null || !page.isModify())
                continue;
            page.reset();
            //page.debugModify(0);
            //System.out.println("end page " + i + "=" + page.isModify());
        }
    }
    /**
     * 得到第一个Text组件
     * @return EText
     */
    public EText getFirstText()
    {
        if(size() == 0)
            return null;
        for(int i = 0;i < size();i++)
        {
            EPage page = get(i);
            if(page == null)
                continue;
            EText text = page.getFirstText();
            if(text != null)
                return text;
        }
        return null;
    }
    /**
     * 调试对象
     */
    public void debugShow()
    {
        for(int j = 0;j < size();j++)
        {
            EPage page = get(j);
            page.debugShow(0);
        }
    }
    /**
     * 调试修改
     */
    public void debugModify()
    {
        if(!isModify())
            return;
        for(int j = 0;j < size();j++)
        {
            EPage page = get(j);
            page.debugModify(0);
        }
    }
    /**
     * 页面尺寸修改
     */
    public void setPageSizeModify()
    {
        if(getViewManager().getViewStyle() == 0)
            return;
        for(int i = 0;i < size();i++)
        {
            EPage page = get(i);
            for(int j = 0;j < page.size();j++)
                page.get(j).setModify(true);
            page.setModify(true);
        }
    }
    /**
     * 更新
     */
    public void update()
    {
        getFocusManager().update();
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
        s.WO("<MPage Width=" + getWidth() + " Height=" + getHeight() + " >",c);
        for(int i = 0;i < size();i ++)
        {
            EPage page = get(i);
            if(page == null)
                continue;
            page.writeObjectDebug(s,c + 1);
        }
        s.WO("</MPage>",c);
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(1,getWidth(),0);
        s.writeInt(2,getHeight(),0);
        s.writeShort(3);
        getInsets().writeObject(s);
        s.writeShort(4);
        s.writeDouble(getPageWidth());
        s.writeShort(5);
        s.writeDouble(getPageHeight());
        s.writeShort(6);
        s.writeDouble(getImageableX());
        s.writeShort(7);
        s.writeDouble(getImageableY());
        s.writeShort(8);
        s.writeDouble(getImageableWidth());
        s.writeShort(9);
        s.writeDouble(getImageableHeight());
        s.writeShort(10);
        s.writeInt(getOrientation());
        s.writeShort(11);
        getMVList().writeObject(s);
        s.writeShort(12);
        getEditInsets().writeObject(s);
        s.writeShort(-1);
        //保存页
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            EPage page = get(i);
            if(page == null)
                continue;
            page.writeObject(s);
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
        while(id > 0)
        {
            switch (id)
            {
            case 1:
                setWidth(s.readInt());
                break;
            case 2:
                setHeight(s.readInt());
                break;
            case 3:
                getInsets().readObject(s);
                break;
            case 4:
                setPageWidth(s.readDouble());
                break;
            case 5:
                setPageHeight(s.readDouble());
                break;
            case 6:
                setImageableX(s.readDouble());
                break;
            case 7:
                setImageableY(s.readDouble());
                break;
            case 8:
                setImageableWidth(s.readDouble());
                break;
            case 9:
                setImageableHeight(s.readDouble());
                break;
            case 10:
                setOrientation(s.readInt());
                break;
            case 11:
                getMVList().readObject(s);
                break;
            case 12:
                getEditInsets().readObject(s);
                break;
            }
            id = s.readShort();
        }
        //读取页
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EPage page = new EPage(this);
            add(page);
            page.readObject(s);
        }
    }
    /**
     * 弹出系统打印设置窗口
     */
    public void printSetup()
    {
        PageFormat pageFormat = getPageFormat();
        pageFormat = PrinterJob.getPrinterJob().pageDialog(pageFormat);

        if(pageFormat == null)
            return;
        setPageFormat(pageFormat);
        if(size() > 0)
            get(0).setModify(true);
        update();
    }
    /**
     * 设置打印格式
     * @param pageFormat 打印格式对象
     */
    public void setPageFormat(PageFormat pageFormat)
    {
        setPageWidth(pageFormat.getWidth());
        setPageHeight(pageFormat.getHeight());
        setImageableX(pageFormat.getImageableX());
        setImageableY(pageFormat.getImageableY());
        setImageableWidth(pageFormat.getImageableWidth());
        setImageableHeight(pageFormat.getImageableHeight());
        setOrientation(pageFormat.getOrientation());
    }
    /**
     * 得到打印格式对象
     * @return PageFormat
     */
    private PageFormat getPageFormat()
    {
        Paper paper = getPaper();
        if (paper == null)
            return null;
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(getOrientation());

        return pageFormat;
    }
    /**
     * 得到打印页面尺寸
     * @return Paper
     */
    public Paper getPaper()
    {
        Paper paper = new Paper();
        if(getOrientation() == PageFormat.PORTRAIT)
        {
            paper.setSize(getPageWidth(), getHeight());
            paper.setImageableArea(getImageableX(), getImageableY(),
                                   getImageableWidth(), getImageableHeight());
        }else
        {
            paper.setSize(getPageHeight(), getWidth());
            paper.setImageableArea(getImageableY(), getImageableX(),
                                   getImageableHeight(), getImageableWidth());
        }
        return paper;
    }
    /**
     * 打印
     * @return boolean
     */
    public boolean print()
    {
        return print(PrinterJob.getPrinterJob());
    }
    /**
     * 打印对话框
     * @return boolean
     */
    public boolean printDialog()
    {
        return printDialog(1);
    }
    /**
     * 打印对话框
     * @param size int 打印分数
     * @return boolean
     */
    public boolean printDialog(int size)
    {
        TParm p = new TParm();
        p.setData("FS",size);
        TParm parm = (TParm)getUI().openDialog("%ROOT%\\config\\database\\PrintDialog.x",p);
        if(parm == null)
            return false;
        PrintService printService = (PrintService)parm.getData("PRINT");
        setPrintPageNo((Integer)parm.getData("PAGE_NO"));
        setPrintPageText((String)parm.getData("PAGE_TEXT"));
        setPrintCount((Integer)parm.getData("COUNT"));
        setPrintzf((Boolean)parm.getData("ZF"));
        checkprintNoList();

        print(printService);
        setPrintPageNo(0);
        setPrintPageText("");
        setPrintCount(1);
        setPrintzf(true);
        printNOList = null;
        return true;
    }
    /**
     * 须打对话框
     * @return boolean
     */
    public boolean printXDDialog()
    {
        TParm parm = (TParm)getUI().openDialog("%ROOT%\\config\\database\\PrintXDDialog.x");
        if(parm == null)
            return false;
        PrintService printService = (PrintService)parm.getData("PRINT");
        int startRowID = (Integer)parm.getData("START_ROW");
        int endRowID = (Integer)parm.getData("END_ROW");
        boolean isPrintBak = parm.getBoolean("PRINT_BAK");
        return printXD(printService,startRowID,endRowID,isPrintBak);
    }
    /**
     * 须打
     * @param printService PrintService
     * @param startRowID int
     * @param endRowID int
     * @param isPrintBak boolean
     * @return boolean
     */
    public boolean printXD(PrintService printService,int startRowID,int endRowID,boolean isPrintXDBak)
    {
        setPrintPageNo(2);
        checkprintNoListXD(startRowID,endRowID);
        setIsPrintXD(true);
        setPrintXDStartRow(startRowID);
        setPrintXDEndRow(endRowID);
        setIsPrintXDBak(isPrintXDBak);
        print(printService);
        setPrintPageNo(0);
        setIsPrintXD(false);
        setPrintXDStartRow(0);
        setPrintXDEndRow(0);
        printNOList = null;
        return true;
    }
    /**
     * 须打判断
     * @param rowID int
     * @return boolean
     */
    public boolean isXDPrint(int rowID)
    {
        if(!isPrintXD())
            return true;
        if(rowID < getPrintXDStartRow())
            return false;
        if(rowID > getPrintXDEndRow())
            return false;
        return true;
    }
    /**
     * 测试须打行号
     * @param rowID int
     * @return boolean
     */
    public boolean checkXDPrintRow(int rowID)
    {
        if(rowID < getPrintXDStartRow())
            return false;
        if(rowID > getPrintXDEndRow())
            return false;
        return true;
    }
    /**
     * 计算打印页码
     */
    private void checkprintNoList()
    {
        printNOList = new ArrayList();
        String s = getPrintPageText();
        String s1[] = StringTool.parseLine(s,",");
        for(int i = 0;i < s1.length;i++)
        {
            String s2 = s1[i];
            int index = s2.indexOf("-");
            if(index == -1)
            {
                try{
                    int x = Integer.parseInt(s2) - 1;
                    printNOList.add(x);
                }catch(Exception e)
                {
                }
                continue;
            }
            try{
                int x1 = Integer.parseInt(s2.substring(0, index)) - 1;
                int x2 = Integer.parseInt(s2.substring(index + 1)) - 1;
                for(int j = x1;j <= x2;j++)
                    printNOList.add(j);
            }catch(Exception e)
            {
            }
        }
    }
    /**
     * 计算打印页码
     * @param startRowID int
     * @param endRowID int
     */
    private void checkprintNoListXD(int startRowID,int endRowID)
    {
        printNOList = new ArrayList();
        for(int i = 0;i < size();i++)
        {
            EPage page = get(i);
            if(page.getRowEndID() < startRowID)
                continue;
            if(page.getRowStartID() > endRowID)
                break;
            printNOList.add(i);
        }
    }
    /**
     * 打印
     * @param printService PrintService
     * @return boolean
     */
    public boolean print(PrintService printService)
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        try
        {
            printJob.setPrintService(printService);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return print(printJob);
    }
    /**
     * 打印
     * @param printJob PrinterJob
     * @return boolean
     */
    public boolean print(PrinterJob printJob)
    {
        PageFormat pageFormat = getPageFormat();
        printJob.setJobName(getFileManager().getJobName());
        printJob.setPrintable(this, pageFormat);
        printJob.setPageable(new EPageable(pageFormat, this));

        try
        {
            printJob.print();
        } catch (Exception e)
        {
            getUI().messageBox("打印机没有准备好!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 通过文件名打印
     * @param printJob
     * @param fileName
     * @return
     */
    public boolean print(PrinterJob printJob,String fileName)
    {
        PageFormat pageFormat = getPageFormat();
        printJob.setJobName(fileName);
        printJob.setPrintable(this, pageFormat);
        printJob.setPageable(new EPageable(pageFormat, this));

        try
        {
            printJob.print();
        } catch (Exception e)
        {
            getUI().messageBox("打印机没有准备好!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 打印绘制入口
     * @param g Graphics 图形设备
     * @param pageFormat PageFormat 页面格式
     * @param pageIndex int 打印页号
     * @return int
     */
    public synchronized int print(Graphics g, PageFormat pageFormat,
                                  int pageIndex)
    {
        switch(getPrintPageNo())
        {
        case 0:
            if (pageIndex >= size() * getPrintCount())
                return Printable.NO_SUCH_PAGE;
            if(getPrintzf())
                print(g,pageIndex % size());
            else
                print(g,pageIndex / getPrintCount());
            break;
        case 1:
            if(pageIndex == getPrintCount())
                return Printable.NO_SUCH_PAGE;
            int currPageIndex = getFocusManager().getFocusPageIndex();
            print(g,currPageIndex);
            return Printable.PAGE_EXISTS;
        case 2:
            if (pageIndex >= printNOList.size() * getPrintCount())
                return Printable.NO_SUCH_PAGE;
            if(getPrintzf())
                print(g,(Integer)printNOList.get(pageIndex % printNOList.size()));
            else
                print(g,(Integer)printNOList.get(pageIndex / getPrintCount()));
            break;
        }
        return Printable.PAGE_EXISTS;
    }
    /**
     * 打印绘制
     * @param g Graphics
     * @param pageIndex int
     */
    public void print(Graphics g,int pageIndex)
    {
        EPage page = get(pageIndex);
        if(page == null)
            return;
        //绘制背景
        if(!isPrintXD() || (isPrintXD() && isPrintXDBak()))
            printBackground(g,pageIndex);
        page.print(g);
        //绘制前景
        if(!isPrintXD() || (isPrintXD() && isPrintXDBak()))
            printForeground(g,pageIndex);
    }
    /**
     * 测试使用样式
     */
    public void checkGCStyle()
    {
        for(int i = 0;i < size();i++)
            get(i).checkGCStyle();
    }
    /**
     * 设置编辑区内部尺寸
     * @param editInsets DInsets
     */
    public void setEditInsets(DInsets editInsets)
    {
        this.editInsets = editInsets;
    }
    /**
     * 得到编辑区内部尺寸
     * @return DInsets
     */
    public DInsets getEditInsets()
    {
        return editInsets;
    }
    /**
     * 设置打印页码类型
     * @param printPageNo int
     */
    public void setPrintPageNo(int printPageNo)
    {
        this.printPageNo = printPageNo;
    }
    /**
     * 得到打印页码类型
     * @return int
     */
    public int getPrintPageNo()
    {
        return printPageNo;
    }
    /**
     * 设置打印页码编号
     * @param printPageText String
     */
    public void setPrintPageText(String printPageText)
    {
        this.printPageText = printPageText;
    }
    /**
     * 得到打印页码编号
     * @return String
     */
    public String getPrintPageText()
    {
        return printPageText;
    }
    /**
     * 设置打印页数
     * @param printCount int
     */
    public void setPrintCount(int printCount)
    {
        this.printCount = printCount;
    }
    /**
     * 得到打印页数
     * @return int
     */
    public int getPrintCount()
    {
        return printCount;
    }
    /**
     * 设置构造器
     * @param printzf boolean
     */
    public void setPrintzf(boolean printzf)
    {
        this.printzf = printzf;
    }
    /**
     * 得到构造器
     * @return boolean
     */
    public boolean getPrintzf()
    {
        return printzf;
    }
    /**
     * 得到页面对象
     * @return EPage
     */
    public EPage getPage()
    {
        return null;
    }
    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        for(int i = 0;i < size();i++)
            get(i).resetData(action);
    }
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        for(int i = 0;i < size();i++)
        {
            EComponent com = get(i).findObject(name,type);
            if(com != null)
                return com;
        }
        return null;
    }
    /**
     * 过滤对象
     * @param name String
     * @param type int
     * @return List
     */
    public List filterObject(String name,int type)
    {
        List list = new ArrayList();
        for(int i = 0;i < size();i++)
            get(i).filterObject(list,name,type);
        return list;
    }
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        for(int i = 0;i < size();i++)
        {
            EComponent com = get(i).findGroup(group);
            if(com != null)
                return com;
        }
        return null;
    }
    /**
     * 得到组值
     * @param group String
     * @return String
     */
    public String findGroupValue(String group)
    {
        for(int i = 0;i < size();i++)
        {
            String value = (String)get(i).findGroupValue(group);
            if(value != null)
                return value;
        }
        return null;
    }
    /**
     * 整理
     */
    public void arrangement()
    {
        int index = 0;
        while(index < size())
        {
            get(index).arrangement();
            index ++;
        }
    }
    /**
     * 整理行号
     */
    public void resetRowID()
    {
        RowID id = new RowID();
        resetRowID(id);
    }
    /**
     * 整理行号
     * @param id RowID
     */
    public void resetRowID(RowID id)
    {
        for(int i = 0;i < size();i++)
        {
            EPage page = get(i);
            page.resetRowID(id);
        }
    }
    /**
     * 设置须打起始行号
     * @param printXDStartRow int
     */
    public void setPrintXDStartRow(int printXDStartRow)
    {
        this.printXDStartRow = printXDStartRow;
    }
    /**
     * 得到须打起始行号
     * @return int
     */
    public int getPrintXDStartRow()
    {
        return printXDStartRow;
    }
    /**
     * 设置须打结束行号
     * @param printXDEndRow int
     */
    public void setPrintXDEndRow(int printXDEndRow)
    {
        this.printXDEndRow = printXDEndRow;
    }
    /**
     * 得到须打结束行号
     * @return int
     */
    public int getPrintXDEndRow()
    {
        return printXDEndRow;
    }
    /**
     * 设置是否须打
     * @param isPrintXD boolean
     */
    public void setIsPrintXD(boolean isPrintXD)
    {
        this.isPrintXD = isPrintXD;
    }
    /**
     * 是否须打
     * @return boolean
     */
    public boolean isPrintXD()
    {
        return isPrintXD;
    }
    /**
     * 设置是否打印背景
     * @param isPrintXDBak boolean
     */
    public void setIsPrintXDBak(boolean isPrintXDBak)
    {
        this.isPrintXDBak = isPrintXDBak;
    }
    /**
     * 是否打印背景
     * @return boolean
     */
    public boolean isPrintXDBak()
    {
        return isPrintXDBak;
    }
}
