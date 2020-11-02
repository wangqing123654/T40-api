package com.dongyang.tui.text.div;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.control.TControl;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: ������</p>
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
public class VTable extends DIVBase
{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean x1B;
    private boolean y1B;
    private boolean x2B;
    private boolean y2B;
    /**
     * ��ʾ��Χ
     * 0 ����ҳ
     * 1 ��ҳ
     * 2 βҳ
     */
    private int showPage = 0;
    /**
     * ҳ��
     */
    private int pageIndex;
    /**
     * ͼ��
     */
    private MV mv;
    /**
     * ������
     */
    public VTable()
    {
        setMV(new MV());
    }
    /**
     * ������
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public VTable(int x,int y,int width,int height)
    {
        this();
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * ����ͼ��
     * @param mv MV
     */
    public void setMV(MV mv)
    {
        mv.setParent(this);
        this.mv = mv;
    }
    /**
     * �õ�ͼ��
     * @return MV
     */
    public MV getMV()
    {
        return mv;
    }
    /**
     * ����X
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * �õ�X
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * ����Y
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * �õ�Y1
     * @return int
     */
    public int getY()
    {
        return y;
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
    public void setX1B(boolean x1B)
    {
        this.x1B = x1B;
    }
    public boolean isX1B()
    {
        return x1B;
    }
    public void setY1B(boolean y1B)
    {
        this.y1B = y1B;
    }
    public boolean isY1B()
    {
        return y1B;
    }
    public void setX2B(boolean x2B)
    {
        this.x2B = x2B;
    }
    public boolean isX2B()
    {
        return x2B;
    }
    public void setY2B(boolean y2B)
    {
        this.y2B = y2B;
    }
    public boolean isY2B()
    {
        return y2B;
    }
    public void setXYB(boolean x1B,boolean y1B,boolean x2B,boolean y2B)
    {
        setX1B(x1B);
        setY1B(y1B);
        setX2B(x2B);
        setY2B(y2B);
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "���";
    }
    /**
     * �õ�����
     * @return int
     */
    public int getTypeID()
    {
        return 2;
    }
    /**
     * �õ���ӡX
     * @return int
     */
    public int getStartXP()
    {
        if(isX1B())
            return getParent().getStartXP() + getParent().getEndXP() - getX();
        return getParent().getStartXP() + getX();
    }
    /**
     * �õ���ӡY
     * @return int
     */
    public int getStartYP()
    {
        if(isY1B())
            return getParent().getStartYP() + getParent().getEndYP() - getY();
        return getParent().getStartYP() + getY();
    }
    /**
     * ��ʼ��X
     * @return int
     */
    public int getStartX()
    {
        if(isX1B())
            return getDrawStartX() + getParent().getEndX() - (int)(getX() * getZoom() / 75.0 + 0.5);
        return getDrawStartX() + (int)(getX() * getZoom() / 75.0 + 0.5);
    }
    /**
     * ��ʼ��Y
     * @return int
     */
    public int getStartY()
    {
        if(isY1B())
            return getDrawStartY() + getParent().getEndY() - (int)(getY() * getZoom() / 75.0 + 0.5);
        return getDrawStartY() + (int)(getY() * getZoom() / 75.0 + 0.5);
    }
    /**
     * �õ�������
     * @return int
     */
    public int getEndXP()
    {
        if(isX2B())
            return getParent().getEndXP() - getWidth() - getX();
        return getWidth();
    }
    /**
     * �õ�������
     * @return int
     */
    public int getEndYP()
    {
        if(isY2B())
            return getParent().getEndYP() - getHeight() - getY();
        return getHeight();
    }
    /**
     * �õ�������
     * @return int
     */
    public int getEndX()
    {
        return (int)(getEndXP() * getZoom() / 75.0 + 0.5);
    }
    /**
     * �õ�������
     * @return int
     */
    public int getEndY()
    {
        return (int)(getEndYP() * getZoom() / 75.0 + 0.5);
    }
    /**
     * �õ�ԭ��X
     * @return int
     */
    public int getDrawStartX()
    {
        return getParent().getStartX();
    }
    /**
     * �õ�ԭ��Y
     * @return int
     */
    public int getDrawStartY()
    {
        return getParent().getStartY();
    }
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        MV mv = this.getMV();
        if(!mv.isVisible())
            return;
        if(getShowPage() == 1 && getPageIndex() > 0)
            return;
        if(getShowPage() == 2 && getPageIndex() != getPM().getPageManager().size() - 1)
            return;
        mv.setPageIndex(pageIndex);
        mv.paint(g);
    }
    /**
     * ��ӡ
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        MV mv = this.getMV();
        if(!mv.isVisible())
            return;
        if(getShowPage() == 1 && getPageIndex() > 0)
            return;
        if(getShowPage() == 2 && getPageIndex() != getPM().getPageManager().size() - 1)
            return;
        mv.setPageIndex(pageIndex);
        mv.print(g);
    }
    /**
     * �õ����ԶԻ�������
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VTableDialog.x";
    }
    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeString(1,getName(),"");
        s.writeBoolean(2,isVisible(),true);
        s.writeInt(3,getX(),0);
        s.writeInt(4,getY(),0);
        s.writeInt(5,getWidth(),0);
        s.writeInt(6,getHeight(),0);
        s.writeShort(7);
        getMV().writeObject(s);
        s.writeInt(8,getShowPage(),0);
        s.writeShort(9);
        s.writeBoolean(isX1B());
        s.writeBoolean(isY1B());
        s.writeBoolean(isX2B());
        s.writeBoolean(isY2B());
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
            setName(s.readString());
            return true;
        case 2:
            setVisible(s.readBoolean());
            return true;
        case 3:
            setX(s.readInt());
            return true;
        case 4:
            setY(s.readInt());
            return true;
        case 5:
            setWidth(s.readInt());
            return true;
        case 6:
            setHeight(s.readInt());
            return true;
        case 7:
            getMV().readObject(s);
            return true;
        case 8:
            setShowPage(s.readInt());
            return true;
        case 9:
            setX1B(s.readBoolean());
            setY1B(s.readBoolean());
            setX2B(s.readBoolean());
            setY2B(s.readBoolean());
            return true;
        }
        return false;
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        writeObjectAttribute(s);
        s.writeShort(-1);
        //������Ŀ
        s.writeInt(0);
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
        //��ȡ��Ŀ
        int count = s.readInt();
    }
    /**
     * ��ʼ����Table
     */
    public void initNew()
    {
        VLine line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("�ϱ�");
        line.setXYB(false,false,true,false);

        line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("�±�");
        line.setXYB(false,true,true,true);

        line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("���");
        line.setXYB(false,false,false,true);

        line = getMV().addLine(0,0,0,0,new Color(0,0,0));
        line.setName("�ұ�");
        line.setXYB(true,false,true,true);

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
        if(getPropertyWindow() == null || getPropertyWindow().isClose())
            return;
        TControl control = getPropertyWindow().getControl();
        if(control == null)
            return;
        control.callFunction("modify",mv,div,name);
    }
    /**
     * ����ҳ��
     * @param pageIndex int
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
    /**
     * �õ�ҳ��
     * @return int
     */
    public int getPageIndex()
    {
        return pageIndex;
    }
    /**
     * ������ʾ��Χ
     * 0 ����ҳ
     * 1 ��ҳ
     * 2 βҳ
     * @param showPage int
     */
    public void setShowPage(int showPage)
    {
        this.showPage = showPage;
    }
    /**
     * �õ���ʾ��Χ
     * 0 ����ҳ
     * 1 ��ҳ
     * 2 βҳ
     * @return int
     */
    public int getShowPage()
    {
        return showPage;
    }
    /**
     * �ͷ�
     */
    public void DC()
    {
        super.DC();
        if(getMV() != null)
            getMV().DC();
    }
}
