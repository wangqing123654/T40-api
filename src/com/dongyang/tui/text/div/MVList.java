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
 * <p>Title: ͼ�������</p>
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
     * ����
     */
    private EComponent parent;
    /**
     * ��ģ��
     */
    private EMacroroutineModel macroroutineModel;
    /**
     * ��Ա�б�
     */
    private TList list;
    /**
     * ���Դ���
     */
    private TWindow propertyWindow;
    /**
     * ��ʼԭ��X
     */
    private int startX;
    /**
     * ��ʼԭ��Y
     */
    private int startY;
    /**
     * ������
     */
    public MVList()
    {
        list = new TList();
    }
    /**
     * ������
     * @param parent EComponent
     */
    public MVList(EComponent parent)
    {
        this();
        setParent(parent);
    }
    /**
     * ������(��ר��)
     * @param macroroutineModel EMacroroutineModel
     */
    public MVList(EMacroroutineModel macroroutineModel)
    {
        this();
        setMacroroutineModel(macroroutineModel);
    }
    /**
     * ���ø���
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getParent()
    {
        return parent;
    }
    /**
     * �õ�������
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
     * �õ���ʾ������
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * �õ����ű���
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }

    /**
     * �õ�UI
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
     * �õ�ҳ�������
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
     * ����
     */
    public void update()
    {
        MPage mPage = getPageManager();
        if(mPage == null)
            return;
        mPage.update();
    }
    /**
     * ����ͼ��
     * @param mv MV
     */
    public void add(MV mv)
    {
        list.add(mv);
        mv.setParent(this);
    }
    /**
     * ����ͼ��
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
     * ɾ��ͼ��
     * @param mv MV
     */
    public void remove(MV mv)
    {
        list.remove(mv);
    }
    /**
     * ɾ��ͼ��
     * @param index int
     */
    public void remove(int index)
    {
        list.remove(index);
    }
    /**
     * ���ȫ��
     */
    public void removeAll()
    {
        DC();
        for(int i = 0;i < size();i++)
            get(i).DC();
        list.removeAll();
    }
    /**
     * �õ�ͼ��
     * @param index int
     * @return MV
     */
    public MV get(int index)
    {
        return (MV)list.get(index);
    }
    /**
     * �õ�ͼ��
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
     * ����
     * @return int
     */
    public int size()
    {
        return list.size();
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
     * ����ǰ��
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
     * ���Ʊ���(��ӡ)
     * @param g Graphics
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int pageIndex)
    {
        printBackground(g,0,0,pageIndex);
    }
    /**
     * ���Ʊ���(��ӡ)
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
     * ����ǰ��(��ӡ)
     * @param g Graphics
     * @param pageIndex int
     */
    public void printForeground(Graphics g,int pageIndex)
    {
        printForeground(g,0,0,pageIndex);
    }
    /**
     * ����ǰ��(��ӡ)
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
     * �������Դ���
     * @param propertyWindow TWindow
     */
    public void setPropertyWindow(TWindow propertyWindow)
    {
        this.propertyWindow = propertyWindow;
    }
    /**
     * �õ����Դ���
     * @return TWindow
     */
    public TWindow getPropertyWindow()
    {
        return propertyWindow;
    }
    /**
     * �����ԶԻ���
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
     * �޸Ĳ���
     * @param mv MV
     * @param div DIV
     * @param name String
     */
    public void modify(MV mv,DIV div,String name)
    {
        propertyModify(mv,div,name);
    }
    /**
     * ���ԶԻ����޸Ĳ���
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
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeShort(-1);
        //������Ŀ
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
     * ������
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
        //��ȡ��Ŀ
        int count = s.readInt();
        for (int i = 0; i < count; i++)
        {
            MV mv = add();
            mv.readObject(s);
        }
    }
    /**
     * �ͷ�
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
     * �õ�����ԭ��X
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
     * �õ�����ԭ��Y
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
     * ���ÿ�ʼԭ��X
     * @param startX int
     */
    public void setStartX(int startX)
    {
        this.startX = startX;
    }
    /**
     * �õ���ʼԭ��X
     * @return int
     */
    public int getStartX()
    {
        return startX;
    }
    /**
     * ���ÿ�ʼԭ��Y
     * @param startY int
     */
    public void setStartY(int startY)
    {
        this.startY = startY;
    }
    /**
     * �õ���ʼԭ��Y
     * @return int
     */
    public int getStartY()
    {
        return startY;
    }
    /**
     * ���ú�ģ��
     * @param macroroutineModel EMacroroutineModel
     */
    public void setMacroroutineModel(EMacroroutineModel macroroutineModel)
    {
        this.macroroutineModel = macroroutineModel;
    }
    /**
     * �õ���ģ��
     * @return EMacroroutineModel
     */
    public EMacroroutineModel getMacroroutineModel()
    {
        return macroroutineModel;
    }
    /**
     * �����Ƿ��Ǻ�ģ��
     * @return boolean
     */
    public boolean isMacroroutineModel()
    {
        return getMacroroutineModel() != null;
    }
    /**
     * �õ�����
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
