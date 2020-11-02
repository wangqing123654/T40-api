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
 * <p>Title: ͼ�����</p>
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
     * ��Ա�б�
     */
    private TList components;
    /**
     * ����
     */
    private EComponent parent;
    /**
     * ����
     */
    private String name;
    /**
     * ͼ�������
     */
    private MVList mvList;
    /**
     * ��ʾ
     */
    private boolean visible = true;
    /**
     * ������
     */
    private int x;
    /**
     * ������
     */
    private int y;
    /**
     * ���
     */
    private int width = 100;
    /**
     * �߶�
     */
    private int height = 100;
    /**
     * �޸�״̬
     */
    private boolean modify;
    /**
     * λ��
     * 0 �м�
     * 1 ����
     * 2 ��β
     */
    private int position;
    /**
     * ģ��
     */
    private EPicModel model;
    /**
     * ͼ��
     */
    private EPicData picData;
    /**
     * ���Դ���
     */
    private TWindow propertyWindow;
    /**
     * ������
     */
    public EPic()
    {
        components = new TList();
        //����ͼ�������
        mvList = new MVList(this);
    }
    /**
     * ��ʼ����Pic
     */
    public void initNew()
    {
        MV mv = mvList.add();
        mv.setName("�߿�");
        VLine line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("�ϱ�");
        line.setXYB(false,false,true,false);

        line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("�±�");
        line.setXYB(false,true,true,true);

        line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("���");
        line.setXYB(false,false,false,true);

        line = mv.addLine(0,0,0,0,new Color(0,0,0));
        line.setName("�ұ�");
        line.setXYB(true,false,true,true);

    }
    /**
     * ������
     * @param panel EPanel
     */
    public EPic(EPanel panel)
    {
        this();
        setParent(panel);
    }
    /**
     * �õ�ͼ�������
     * @return MV
     */
    public MVList getMVList()
    {
        return mvList;
    }
    /**
     * ����ͼ�������
     */
    public void createMVList()
    {
        mvList = new MVList(this);
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
     * �õ����
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
     * �õ�ҳ��
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
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getPage().getPM();
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
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * ���ӳ�Ա
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
     * ���ӳ�Ա
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
     * ����λ��
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
     * ɾ����Ա
     * @param com EComponent
     */
    public void remove(EComponent com)
    {
        components.remove(com);
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
     * @return EComponent
     */
    public EComponent get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (EComponent)components.get(index);
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
     * ���ú�����
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * ����������
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * ����λ��
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        setX(x);
        setY(y);
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * ���ñ߿�
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
     * ������ʾ
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
     * �Ƿ���ʾ
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
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
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int x,int y,int pageIndex)
    {
        getMVList().printBackground(g,x,y,pageIndex);
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
        getMVList().printForeground(g,x,y,pageIndex);
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
        if(!isVisible())
            return;
        //���Ʊ���
        paintBackground(g,x,y,0);
        //���Ʊ���
        if(getModel() != null)
            getModel().paintBackground(g,x,y,width,height);
        //����ͼ��
        if(getPicData() != null)
            getPicData().paint(g,width,height);
        //����ǰ��
        if(getModel() != null)
            getModel().paintForeground(g,x,y,width,height);
        paintForeground(g,x,y,0);
    }
    /**
     * ��ӡ
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g,int x,int y)
    {
        if(!isVisible())
            return;
        //���Ʊ���
        printBackground(g,x,y,0);
        //���Ʊ���
        if(getModel() != null)
            getModel().paintBackground(g,x,y,width,height);
        //����ͼ��
        if(getPicData() != null)
        {
            Graphics gc = g.create(x,y,getWidth() + 10,getHeight() + 10);
            getPicData().paint(gc, getWidth(), getHeight());
        }
        //����ǰ��
        if(getModel() != null)
            getModel().paintForeground(g,x,y,width,height);
        printForeground(g,x,y,0);
    }
    /**
     * �������
     * @param mouseX int
     * @param mouseY int
     * @return int ��ѡ����
     * -1 û��ѡ��
     * 1 EText
     * 2 ETD
     * 3 ETR Enter
     * 4 EPic
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        //���ý���
        setFocus();
        //�������TR
        /*int trIndex = getMouseInTRIndex(mouseX,mouseY);
        if(trIndex != -1)
        {
            ETR tr = get(trIndex);
            int x = mouseX - tr.getX();
            int y = mouseY - tr.getY();
            //�¼�����
            tr.onMouseLeftPressed(x,y);
        }*/
        return 4;
    }
    /**
     * �Ҽ�����
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {

    }
    /**
     * ���ý���
     */
    public void setFocus()
    {
        setFocus(this);
    }
    /**
     * ���ý���
     * @param com EComponent
     */
    public void setFocus(EPic com)
    {
        getFocusManager().setFocusPic(com);
    }
    /**
     * ˫��
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
        setModify(false);
    }
    /**
     * �����ߴ�
     * @param index int
     */
    public void reset(int index)
    {

    }
    /**
     * ����λ��
     * @param position int
     */
    public void setPosition(int position)
    {
        this.position = position;
    }
    /**
     * �õ�λ��
     * @return int
     */
    public int getPosition()
    {
        return position;
    }
    /**
     * �������λ��
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
     * �����Լ���λ��
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
    }
    /**
     * �õ�����λ��
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
     * ���Զ������
     * @param i int
     */
    public void debugShow(int i)
    {
        System.out.println(StringTool.fill(" ",i) + this);
    }
    /**
     * �����޸�
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
     * д��������
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
     * ����������
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
     * д����
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
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //д��������
        writeObjectAttribute(s);
        s.writeShort( -1);
        //����ҳ
        s.writeInt(size());
        /*for(int i = 0;i < size();i ++)
        {
            ETR tr = get(i);
            tr.writeObject(s);
        }*/
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
            //����������
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //��ȡ��
        int count = s.readInt();
        /*for(int i = 0;i < count;i++)
        {
            ETR tr = newTR();
            tr.readObject(s);
        }*/
    }
    /**
     * �õ�·��
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
     * �õ�·������
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
     * ����ģ��
     * @param model EPicModel
     */
    public void setModel(EPicModel model)
    {
        this.model = model;
    }
    /**
     * �õ�ģ��
     * @return EPicModel
     */
    public EPicModel getModel()
    {
        return model;
    }
    /**
     * ����ģ��
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
     * ɾ���Լ�
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
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * ����ͼ�����
     * @param picData EPicData
     */
    public void setPicData(EPicData picData)
    {
        this.picData = picData;
        picData.setParent(this);
    }
    /**
     * �õ�ͼ�����
     * @return EPicData
     */
    public EPicData getPicData()
    {
        return picData;
    }
    /**
     * ����ͼ��
     */
    public void createPicData()
    {
        setPicData(new EPicData());
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
     * ͼ�����öԻ���
     */
    public void openDataProperty()
    {
        if(propertyWindow == null || propertyWindow.isClose())
            propertyWindow = (TWindow)getUI().openWindow("%ROOT%\\config\\database\\PicDataDialog.x", this ,true);
        else
            propertyWindow.setVisible(true);
    }
    /**
     * ����
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action)
    {
    }
    /**
     * �õ���������
     * @return int
     */
    public int getObjectType()
    {
        return PIC_TYPE;
    }
    /**
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
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
     * ���˶���
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type)
    {

    }
    /**
     * ������
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        return null;
    }
    /**
     * �õ���ֵ
     * @param group String
     * @return String
     */
    public String findGroupValue(String group)
    {
        return null;
    }
    /**
     * �õ���ֵ
     * @return String
     */
    public String getBlockValue()
    {
        return "";
    }
    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock()
    {
        return null;
    }
    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getNextTrueBlock()
    {
        return null;
    }
    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        return null;
    }
    /**
     * ����
     */
    public void arrangement()
    {

    }
}
