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
 * <p>Title: ҳ������</p>
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
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    private PM pm;
    /**
     * ҳ����
     */
    private double pageWidth;
    /**
     * ҳ��߶�
     */
    private double pageHeight;
    /**
     * ҳ���
     */
    private int width;
    /**
     * ҳ�߶�
     */
    private int height;
    /**
     * �ڲ��ߴ�
     */
    private DInsets insets;
    private DInsets editInsets;
    /**
     * �޸�״̬
     */
    private boolean modify;
    /**
     * ��ӡ����
     */
    private int orientation = PageFormat.PORTRAIT;
    /**
     * ���ô�ӡ��Ч�ߴ�X
     */
    private double imageableX;
    /**
     * ���ô�ӡ��Ч�ߴ�Y
     */
    private double imageableY;
    /**
     * ���ô�ӡ��Ч�ߴ���
     */
    private double imageableWidth;
    /**
     * ���ô�ӡ��Ч�ߴ�߶�
     */
    private double imageableHeight;
    /**
     * ͼ�������
     */
    private MVList mvList;
    /**
     * ��ӡҳ������
     */
    private int printPageNo;
    /**
     * ��ӡҳ����
     */
    private String printPageText;
    /**
     * ��ӡҳ��
     */
    private int printCount = 1;
    /**
     * ��ݴ�ӡ
     */
    private boolean printzf = true;
    /**
     * ��ӡҳ���б�
     */
    private List printNOList;
    /**
     * �����ʼ�к�
     */
    private int printXDStartRow = 0;
    /**
     * �������к�
     */
    private int printXDEndRow = 0;
    /**
     * �Ƿ����
     */
    private boolean isPrintXD;
    /**
     * �Ƿ��ӡ����
     */
    private boolean isPrintXDBak;
    /**
     * ������
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
        //����ͼ�������
        mvList = new MVList(this);
    }
    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * �õ�ͼ�������
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
     * �õ��ַ����ݹ�����
     * @return MString
     */
    public MString getStringManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getStringManager();
    }
    /**
     * �õ���ʾ��
     * @return MView
     */
    public MView getViewManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getViewManager();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFocusManager();
    }
    /**
     * �õ��ļ�������
     * @return MFile
     */
    public MFile getFileManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFileManager();
    }
    /**
     * ���ý���λ��
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * �õ�����λ��
     * @return int
     */
    public int getFocusIndex()
    {
        if(getFocusManager() == null)
            return 0;
        return getFocusManager().getFocusIndex();
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getFocus()
    {
        if(getFocusManager() == null)
            return null;
        return getFocusManager().getFocus();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
            return null;
        return getPM().getUI();
    }
    /**
     * ���ú�����
     * @param x int
     */
    public void setX(int x)
    {
    }
    /**
     * �õ�������
     * @return int
     */
    public int getX()
    {
        return 0;
    }
    /**
     * ����������
     * @param y int
     */
    public void setY(int y)
    {
    }
    /**
     * �õ�������
     * @return int
     */
    public int getY()
    {
        return 0;
    }
    /**
     * ����λ��
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
    }
    /**
     * ����ҳ����
     * @param pageWidth double
     */
    public void setPageWidth(double pageWidth)
    {
        this.pageWidth = pageWidth;
        setWidth((int)pageWidth);
    }
    /**
     * �õ�ҳ����
     * @return double
     */
    public double getPageWidth()
    {
        return pageWidth;
    }
    /**
     * ����ҳ��߶�
     * @param pageHeight double
     */
    public void setPageHeight(double pageHeight)
    {
        this.pageHeight = pageHeight;
        setHeight((int)pageHeight);
    }
    /**
     * �õ�ҳ��߶�
     * @return double
     */
    public double getPageHeight()
    {
        return pageHeight;
    }
    /**
     * ���ô�ӡ��Ч�ߴ�X
     * @param imageableX double
     */
    public void setImageableX(double imageableX)
    {
        this.imageableX = imageableX;
        getInsets().left = (int)imageableX;
    }
    /**
     * �õ���ӡ��Ч�ߴ�X
     * @return double
     */
    public double getImageableX()
    {
        return imageableX;
    }
    /**
     * ���ô�ӡ��Ч�ߴ�Y
     * @param imageableY double
     */
    public void setImageableY(double imageableY)
    {
        this.imageableY = imageableY;
        getInsets().top = (int)imageableY;
    }
    /**
     * �õ���ӡ��Ч�ߴ�Y
     * @return double
     */
    public double getImageableY()
    {
        return imageableY;
    }
    /**
     * ���ô�ӡ��Ч�ߴ���
     * @param imageableWidth double
     */
    public void setImageableWidth(double imageableWidth)
    {
        this.imageableWidth = imageableWidth;
        getInsets().right = (int)(getPageWidth() - imageableWidth - imageableX);
    }
    /**
     * �õ���ӡ��Ч�ߴ���
     * @return double
     */
    public double getImageableWidth()
    {
        return imageableWidth;
    }
    /**
     * ���ô�ӡ��Ч�ߴ�߶�
     * @param imageableHeight double
     */
    public void setImageableHeight(double imageableHeight)
    {
        this.imageableHeight = imageableHeight;
        getInsets().bottom = (int)(getPageHeight() - imageableHeight - imageableY);
    }
    /**
     * �õ���ӡ��Ч�ߴ�߶�
     * @return double
     */
    public double getImageableHeight()
    {
        return imageableHeight;
    }
    /**
     * ����ҳ����
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
     * �õ�ҳ����
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * ����ҳ��߶�
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
     * �õ�ҳ��߶�
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * �����ڲ��ߴ�
     * @param insets DInsets
     */
    public void setInsets(DInsets insets)
    {
        this.insets = insets;
    }
    /**
     * �õ��ڲ��ߴ�
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return insets;
    }
    /**
     * ���ӳ�Ա
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
     * ɾ����Ա
     * @param page EPage
     */
    public void remove(EPage page)
    {
        components.remove(page);
        //if(getViewManager() != null)
        //    getViewManager().resetSize();
    }

    /**
     * ��Ա����
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * �õ���Ա
     * @param index int
     * @return EPage
     */
    public EPage get(int index)
    {
        return (EPage) components.get(index);
    }
    /**
     * ����λ��
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
     * �õ���Ա�б�
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * ���ø���
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getParent()
    {
        return null;
    }
    /**
     * ����ֽ�ŷ���
     * @param orientation int
     */
    public void setOrientation(int orientation)
    {
        this.orientation = orientation;
    }
    /**
     * �õ�ֽ�ŷ���
     * @return int
     */
    public int getOrientation()
    {
        return orientation;
    }
    /**
     * �õ����������
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
     * �õ��������߶�
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
     * ����
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
     * ���Ʊ���
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
     * ����ǰ��
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
     * ���Ʊ���(��ӡ)
     * @param g Graphics
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int pageIndex)
    {
        getMVList().printBackground(g,pageIndex);
    }
    /**
     * ����ǰ��(��ӡ)
     * @param g Graphics
     * @param pageIndex int
     */
    public void printForeground(Graphics g,int pageIndex)
    {
        getMVList().printForeground(g,pageIndex);
    }
    /**
     * ȫ�����
     */
    public void removeAll()
    {
        components.removeAll();
        getMVList().removeAll();
    }
    /**
     * ��ҳ
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
     * �����Ƿ��޸�״̬
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
     * �Ƿ��޸�
     * @return boolean
     */
    public boolean isModify()
    {
        return modify;
    }
    /**
     * �����ߴ�
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
     * �����ߴ�
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
     * �õ���һ��Text���
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
     * ���Զ���
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
     * �����޸�
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
     * ҳ��ߴ��޸�
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
     * ����
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * д����
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
     * д����
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
        //����ҳ
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
     * ������
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
        //��ȡҳ
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EPage page = new EPage(this);
            add(page);
            page.readObject(s);
        }
    }
    /**
     * ����ϵͳ��ӡ���ô���
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
     * ���ô�ӡ��ʽ
     * @param pageFormat ��ӡ��ʽ����
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
     * �õ���ӡ��ʽ����
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
     * �õ���ӡҳ��ߴ�
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
     * ��ӡ
     * @return boolean
     */
    public boolean print()
    {
        return print(PrinterJob.getPrinterJob());
    }
    /**
     * ��ӡ�Ի���
     * @return boolean
     */
    public boolean printDialog()
    {
        return printDialog(1);
    }
    /**
     * ��ӡ�Ի���
     * @param size int ��ӡ����
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
     * ���Ի���
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
     * ���
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
     * ����ж�
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
     * ��������к�
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
     * �����ӡҳ��
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
     * �����ӡҳ��
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
     * ��ӡ
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
     * ��ӡ
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
            getUI().messageBox("��ӡ��û��׼����!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * ͨ���ļ�����ӡ
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
            getUI().messageBox("��ӡ��û��׼����!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * ��ӡ�������
     * @param g Graphics ͼ���豸
     * @param pageFormat PageFormat ҳ���ʽ
     * @param pageIndex int ��ӡҳ��
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
     * ��ӡ����
     * @param g Graphics
     * @param pageIndex int
     */
    public void print(Graphics g,int pageIndex)
    {
        EPage page = get(pageIndex);
        if(page == null)
            return;
        //���Ʊ���
        if(!isPrintXD() || (isPrintXD() && isPrintXDBak()))
            printBackground(g,pageIndex);
        page.print(g);
        //����ǰ��
        if(!isPrintXD() || (isPrintXD() && isPrintXDBak()))
            printForeground(g,pageIndex);
    }
    /**
     * ����ʹ����ʽ
     */
    public void checkGCStyle()
    {
        for(int i = 0;i < size();i++)
            get(i).checkGCStyle();
    }
    /**
     * ���ñ༭���ڲ��ߴ�
     * @param editInsets DInsets
     */
    public void setEditInsets(DInsets editInsets)
    {
        this.editInsets = editInsets;
    }
    /**
     * �õ��༭���ڲ��ߴ�
     * @return DInsets
     */
    public DInsets getEditInsets()
    {
        return editInsets;
    }
    /**
     * ���ô�ӡҳ������
     * @param printPageNo int
     */
    public void setPrintPageNo(int printPageNo)
    {
        this.printPageNo = printPageNo;
    }
    /**
     * �õ���ӡҳ������
     * @return int
     */
    public int getPrintPageNo()
    {
        return printPageNo;
    }
    /**
     * ���ô�ӡҳ����
     * @param printPageText String
     */
    public void setPrintPageText(String printPageText)
    {
        this.printPageText = printPageText;
    }
    /**
     * �õ���ӡҳ����
     * @return String
     */
    public String getPrintPageText()
    {
        return printPageText;
    }
    /**
     * ���ô�ӡҳ��
     * @param printCount int
     */
    public void setPrintCount(int printCount)
    {
        this.printCount = printCount;
    }
    /**
     * �õ���ӡҳ��
     * @return int
     */
    public int getPrintCount()
    {
        return printCount;
    }
    /**
     * ���ù�����
     * @param printzf boolean
     */
    public void setPrintzf(boolean printzf)
    {
        this.printzf = printzf;
    }
    /**
     * �õ�������
     * @return boolean
     */
    public boolean getPrintzf()
    {
        return printzf;
    }
    /**
     * �õ�ҳ�����
     * @return EPage
     */
    public EPage getPage()
    {
        return null;
    }
    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        for(int i = 0;i < size();i++)
            get(i).resetData(action);
    }
    /**
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
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
     * ���˶���
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
     * ������
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
     * �õ���ֵ
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
     * ����
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
     * �����к�
     */
    public void resetRowID()
    {
        RowID id = new RowID();
        resetRowID(id);
    }
    /**
     * �����к�
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
     * ���������ʼ�к�
     * @param printXDStartRow int
     */
    public void setPrintXDStartRow(int printXDStartRow)
    {
        this.printXDStartRow = printXDStartRow;
    }
    /**
     * �õ������ʼ�к�
     * @return int
     */
    public int getPrintXDStartRow()
    {
        return printXDStartRow;
    }
    /**
     * �����������к�
     * @param printXDEndRow int
     */
    public void setPrintXDEndRow(int printXDEndRow)
    {
        this.printXDEndRow = printXDEndRow;
    }
    /**
     * �õ��������к�
     * @return int
     */
    public int getPrintXDEndRow()
    {
        return printXDEndRow;
    }
    /**
     * �����Ƿ����
     * @param isPrintXD boolean
     */
    public void setIsPrintXD(boolean isPrintXD)
    {
        this.isPrintXD = isPrintXD;
    }
    /**
     * �Ƿ����
     * @return boolean
     */
    public boolean isPrintXD()
    {
        return isPrintXD;
    }
    /**
     * �����Ƿ��ӡ����
     * @param isPrintXDBak boolean
     */
    public void setIsPrintXDBak(boolean isPrintXDBak)
    {
        this.isPrintXDBak = isPrintXDBak;
    }
    /**
     * �Ƿ��ӡ����
     * @return boolean
     */
    public boolean isPrintXDBak()
    {
        return isPrintXDBak;
    }
}
