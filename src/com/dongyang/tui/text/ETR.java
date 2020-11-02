package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.util.TList;
import java.awt.Rectangle;
import com.dongyang.tui.DInsets;
import com.dongyang.util.StringTool;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.DPoint;
import com.dongyang.ui.util.TDrawTool;
import java.util.List;


/**
 * @author whao 2013~
 * @version 1.0
 */
public class ETR implements EComponent,EEvent,IExeAction
{
    /**
     * ��ʾ
     */
    private boolean visible = true;
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ����
     */
    private EComponent parent;
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
    private int width;
    /**
     * �߶�
     */
    private int height;
    /**
     * �޸�״̬
     */
    private boolean modify;
    /**
     * ģ��
     */
    private ETRModel model;
    /**
     * ��ʾ�߿�
     */
    int showBorder;
    /**
     * ��ʾ����
     */
    boolean showHLine;
    /**
     * ��һ������TR
     */
    private ETR previousLinkTR;
    /**
     * ��һ������TR
     */
    private ETR nextLinkTR;
    /**
     * ��һ��TR��������(����ר��)
     */
    private int previousTRIndex;
    /**
     * ���߶�
     */
    private int maxHeight = -1;
    /**
     * ���óߴ����
     */
    private ResetTR resetTR;
    /**
     * �Ƿ�ѡ��
     */
    private boolean isSelected;
    /**
     * ѡ�ж���
     */
    private CSelectTRModel selectedModel;
    /**
     * �б��
     */
    private int rowID;
    /**
     * ������
     */
    public ETR()
    {
        components = new TList();
        //�����ߴ��������
        setResetTR(new ResetTR(this));
    }
    /**
     * ������
     * @param table ETable
     */
    public ETR(ETable table)
    {
        this();
        setParent(table);
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
     * �õ���ʾ������
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
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
     * @param td ETD
     */
    public void add(ETD td)
    {
        if(td == null)
            return;
        components.add(td);
        td.setParent(this);
    }
    /**
     * ���ӳ�Ա
     * @param index int
     * @param td ETD
     */
    public void add(int index,ETD td)
    {
        if(td == null)
            return;
        components.add(index,td);
        td.setParent(this);
    }
    /**
     * ����λ��
     * @param td ETD
     * @return int
     */
    public int indexOf(ETD td)
    {
        if(td == null)
            return -1;
        return components.indexOf(td);
    }
    /**
     * ɾ����Ա
     * @param td ETD
     */
    public void remove(ETD td)
    {
        components.remove(td);
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
     * @return ETD
     */
    public ETD get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (ETD)components.get(index);
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
     * �õ�������
     * @return int
     */
    public int getWSpace()
    {
        ETable table = getTable();
        if(table == null)
            return 0;
        return table.getWSpace();
    }
    /**
     * �õ��ڲ��ߴ�
     * @return DInsets
     */
    public DInsets getInsets()
    {
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getTRInsets();
    }
    /**
     * �õ��ڲ�X
     * @return int
     */
    public int getInsetsX()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return 0;
        return insets.left;
    }
    /**
     * �õ��ڲ�Y
     * @return int
     */
    public int getInsetsY()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return 0;
        return insets.top;
    }
    /**
     * �õ��ڲ����
     * @return int
     */
    public int getInsetsWidth()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return getWidth();
        return getWidth() - insets.left - insets.right;
    }
    /**
     * �õ��ڲ��߶�
     * @return int
     */
    public int getInsetsHeight()
    {
        DInsets insets = getInsets();
        if(insets == null)
            return getWidth();
        return getHeight() - insets.top - insets.bottom;
    }
    /**
     * �õ�Table
     * @return ETable
     */
    public ETable getTable()
    {
        EComponent com = getParent();
        if(com == null || !(com instanceof ETable))
            return null;
        return (ETable)com;
    }
    /**
     * �õ�ҳ��
     * @return EPage
     */
    public EPage getPage()
    {
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getPage();
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
     * �Ƿ�����״̬
     * @return boolean
     */
    public boolean isPreview()
    {
        return getTable().isPreview();
    }
    /**
     * �Ƿ���Table�����һ��
     * @return boolean
     */
    public boolean isLastTR()
    {
        return getTable().isLastTR(this);
    }
    /**
     * �Ƿ���Table�ĵ�һ��
     * @return boolean
     */
    public boolean isFirstTR()
    {
        return getTable().isFirstTR(this);
    }
    /**
     * �Ƿ������һ����Ԫ��
     * @param td ETD
     * @return boolean
     */
    public boolean isLastTD(ETD td)
    {
        if(size() == 0)
            return false;
        if(get(size() - 1) == td)
            return true;
        if(td.getSpanX() == 0)
            return false;
        if(td.getSpanX() + td.findIndex() == size() - 1)
            return true;
        return false;
    }
    /**
     * �Ƿ��ǵ�һ����Ԫ��
     * @param td ETD
     * @return boolean
     */
    public boolean isFirstTD(ETD td)
    {
        if(size() == 0)
            return false;
        return get(0) == td;
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
        if(getViewManager().isTRDebug())
        {
            g.setColor(new Color(0, 255, 255));
            g.drawRect(x,y,width,height);
        }
        //ѡ�б���з�ɫ
        if(isSelectedDraw())
        {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(x,y,width,height);
        }
        g.setColor(new Color(0,0,0));
        int index = findIndex();
        boolean startRow = index == 0;
        boolean endRow = index == (getTRCount() - 1);

        if(isShowBorder(1))
        {
            int y1 = y;
            if(!startRow)
                y -= 1;
            g.drawLine(x, y1, x + width, y1);
        }
        if(isShowBorder(2))
            g.drawLine(x,y + height,x + width,y + height);
        if(isShowBorder(4))
            g.drawLine(x,y,x,y + height);
        if(isShowBorder(8))
            g.drawLine(x + width,y,x + width,y + height);


        double zoom = getZoom() / 75.0;
        int space = (int) ((double) getWSpace() / 2.0 * zoom + 0.5);
        //����ҳ��
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            int tdX = (int)(td.getX() * zoom + 0.5);
            int tdY = (int)(td.getY() * zoom + 0.5);
            int tdW = (int)(td.getWidth() * zoom + 0.5);
            int tdH = (int)(td.getHeight() * zoom + 0.5);
            Rectangle r = g.getClipBounds();
            if(!(r.getX() > x + tdX + tdW ||
               r.getY() > y + tdY + tdH||
               r.getX() + r.getWidth() < x + tdX ||
               r.getY() + r.getHeight() < y + tdY))
            {
                //����
                td.paint(g, x + tdX,y + tdY,tdW,tdH);
            }

            /*if(isShowHLine() || !isPreview() || getTable().isShowHLine())
                if(i < size() - 1 && space > 0)
                {
                    if(isShowHLine() || getTable().isShowHLine())
                        g.setColor(new Color(0,0,0));
                    else
                        g.setColor(new Color(168,168,168));
                    int x1 = x + tdX + tdW + space;
                    if(getTable().isShowHLine())
                    {
                        int y1 = y;
                        int y2 = y + height;
                        if(!getTable().isShowBorder())
                        {
                            if (startRow)
                                y1-=getTable().getInsets().top;
                            if (endRow)
                                y2+=getTable().getInsets().bottom - 1;
                        }
                        g.drawLine(x1,y1,x1,y2);
                    }else
                        g.drawLine(x1,y,x1,y + height);
                }*/
        }
        //���ƻس����ͽ���
        if(!isPreview() && getNextLinkTR() == null)
        {
            int x1 = getWidth() + getTable().getInsets().right + 2;
            int y1 = 14;
            x1 = (int)(x1 * getZoom() / 75.0 + 0.5);
            y1 = (int)(y1 * getZoom() / 75.0 + 0.5);
            //���ƽ���
            if(isSelectedDraw())
            {
                //ѡ�����
                g.setColor(new Color(0, 0, 0));
                g.fillRect(x + x1 - 2, y + y1 - 16, 9, 18);
            }else if(getFocusTREnter() == this && isShowCursor())
            {
                //��˸���
                g.setColor(new Color(0, 0, 0));
                g.fillRect(x + x1, y + y1 - 16, 2, 18);
            }
            g.setColor(new Color(128, 128, 128));
            TDrawTool.drawEnter(x + x1, y + y1, g);
        }
    }
    /**
     * ��ӡ
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g,int x,int y)
    {
        g.setColor(new Color(0,0,0));
        int index = findIndex();
        boolean startRow = index == 0;
        boolean endRow = index == (getTRCount() - 1);
        if(isShowBorder(1))
        {
            int yy = y;
            if(!startRow)
                yy -= 1;
            g.drawLine(x, yy, x + getWidth(), yy);
        }
        if(isShowBorder(2))
        {
            int h = y + getHeight();
            g.drawLine(x,h,x + getWidth(),h);
        }
        if(isShowBorder(4))
            g.drawLine(x,y,x,y + getHeight());
        if(isShowBorder(8))
            g.drawLine(x + getWidth(), y, x + getWidth(),
                       y + getHeight());
        //����ҳ��
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            //����
            td.print(g, x + td.getX(), y + td.getY());
        }
    }
    /**
     * �õ��������DD
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInTDIndex(int mouseX,int mouseY)
    {
        double zoom = getZoom() / 75.0;
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if((int)(td.getX() * zoom + 0.5) <= mouseX &&
               (int)((td.getX() + td.getWidth()) * zoom + 0.5) >= mouseX &&
               (int)(td.getY() * zoom + 0.5) <= mouseY &&
               (int)((td.getY() + td.getHeight()) * zoom + 0.5) >= mouseY)
                return i;
        }
        return -1;
    }
    /**
     * �������
     * @param mouseX int
     * @param mouseY int
     * @return int ��ѡ����
     * -1 û��ѡ��
     * 1 EText
     * 2 ETR
     * 3 ETR Enter
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        if(size() == 0)
            return -1;
        //�������TD
        int tdIndex = getMouseInTDIndex(mouseX,mouseY);
        if(tdIndex != -1)
        {
            ETD td = get(tdIndex);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            return td.onMouseLeftPressed(x,y);
        }
        //���������һ�����Ӳ��ܵõ��س�����
        if(getNextLinkTR() != null)
        {
            ETD td = get(size() - 1);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            return td.onMouseLeftPressed(x,y);
        }
        if(mouseX > getWidth() * getZoom() / 75.0 + 0.5)
        {
            //���ûس�����
            getFocusManager().setFocusTRE(this);
            return 3;
        }
        return -1;
    }
    /**
     * �Ҽ�����
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        if(size() == 0)
            return;
        //�������TD
        int tdIndex = getMouseInTDIndex(mouseX,mouseY);
        if(tdIndex != -1)
        {
            ETD td = get(tdIndex);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            td.onMouseRightPressed(x,y);
            return;
        }
        //���������һ�����Ӳ��ܵõ��س�����
        if(getNextLinkTR() != null)
        {
            ETD td = get(size() - 1);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            td.onMouseRightPressed(x,y);
            return;
        }
    }
    /**
     * �Ƿ���ʾ���
     * @return boolean
     */
    public boolean isShowCursor()
    {
        return getFocusManager().isShowCursor();
    }
    /**
     * �õ��س�����
     * @return ETR
     */
    public ETR getFocusTREnter()
    {
        return getFocusManager().getFocusTRE();
    }
    /**
     * �õ�������
     * @return ETR
     */
    public ETR getFocusTR()
    {
        return getFocusManager().getFocusTR();
    }
    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        //�������TD
        int tdIndex = getMouseInTDIndex(mouseX,mouseY);
        if(tdIndex != -1)
        {
            ETD td = get(tdIndex);
            int x = mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            return td.onDoubleClickedS(x,y);
        }
        //������
        //setSelected(!isSelected());
        return false;
    }
    /**
     * ����
     * @return ETD
     */
    public ETD newTD()
    {
        ETD td = new ETD();
        add(td);
        return td;
    }
    /**
     * �õ�TD���߶�
     * @return int
     */
    public int getTDMaxHeight()
    {
        int height = 0;
        DInsets insets = getInsets();
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            height = Math.max(height,td.getHeight());
        }
        return height + insets.top + insets.bottom;
    }
    /**
     * �õ����һ��
     * @return EText
     */
    public EText getLastText()
    {
        return getLastText(size() - 1);
    }
    /**
     * �õ���һ��
     * @param td ETD
     * @return EText
     */
    public EText getPreviousText(ETD td)
    {
        if(td == null)
            return null;
        int index = indexOf(td);
        if(index == -1)
            return null;
        EText text = getLastText(index - 1);
        if(text != null)
            return text;
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getPreviousText(this);
    }
    /**
     * �õ����һ��
     * @param index int
     * @return EText
     */
    public EText getLastText(int index)
    {
        for(int i = index;i >= 0;i--)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            EText text = td.getLastText();
            if(text != null)
                return text;
        }
        return null;
    }
    /**
     * ���ҵ�һ��
     * @return EText
     */
    public EText getFirstText()
    {
        return getFirstText(0);
    }
    /**
     * �õ���һ��
     * @param td ETD
     * @return EText
     */
    public EText getNextText(ETD td)
    {
        if(td == null)
            return null;
        int index = indexOf(td);
        if(index == -1)
            return null;
        EText text = getFirstText(index + 1);
        if(text != null)
            return text;
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getNextText(this);
    }
    /**
     * ���ҵ�һ��
     * @param index int
     * @return EText
     */
    public EText getFirstText(int index)
    {
        for(int i = index;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            EText text = td.getFirstText();
            if(text != null)
                return text;
        }
        return null;
    }
    /**
     * ���Ը߶�
     * @param userHeight int
     * @return boolean
     */
    public boolean checkHeight(int userHeight)
    {
        return true;
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
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            if(td.isModify())
                return;
        }
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
        //����
        getResetTR().clear();
        //���óߴ緶Χ
        getResetTR().setRectangle(0,
                                  0,
                                  getWidth(),
                                  getMaxHeight());
        //�����ߴ�
        getResetTR().reset();
        setModify(false);
    }
    /**
     * ����
     */
    public void linkTR()
    {
        ETR tr = getNextLinkTR();
        if(tr == null)
            return;
        for(int i = 0;i < tr.size();i++)
        {
            ETD td = tr.get(i);
            ETD tdP = td.getPreviousLinkTD();
            for(int j = 0;j < td.size();j++)
            {
                EPanel panel = td.get(j);
                if(panel.getPreviousLinkPanel() != null)
                {
                    panel.getPreviousLinkPanel().add(panel);
                    continue;
                }
                tdP.add(panel);
            }
            tdP.setNextLinkTD(td.getNextLinkTD());
            if(td.getNextLinkTD() != null)
                td.getNextLinkTD().setPreviousLinkTD(tdP);
        }
        setNextLinkTR(tr.getNextLinkTR());
        if(getNextLinkTR() != null)
            getNextLinkTR().setPreviousLinkTR(this);
        ETable table = tr.getTable();
        table.remove(tr);
        if(table.size() == 0)
            table.removeThis();
    }
    /**
     * �����ߴ�
     * @param index int
     */
    public void reset(int index)
    {
        if(index < 0 || index >= size())
            return;
        int x = getInsetsX();
        int y = getInsetsY();
        int space = getWSpace();
        int height = 0;
        //ȫ���������ߴ�
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            td.setX(x);
            td.setY(y);
            if(getMaxHeight() > 0)
            {
                td.setMaxHeight(getMaxHeight() - y - getInsets().bottom);
                td.setModify(true);
            }
            td.reset();
            height = Math.max(height,td.getHeight());
            x = td.getX() + td.getWidth() + space;
        }
        DInsets insets = getInsets();
        setHeight(height + insets.top + insets.bottom);
    }
    /**
     * �������λ��
     * @param tr ETR
     * @return int
     */
    public int findIndex(ETR tr)
    {
        ETable table = getTable();
        if(table == null)
            return -1;
        return table.indexOf(tr);
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
     * �õ��и���
     * @return int
     */
    public int getTRCount()
    {
        return getTable().size();
    }
    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString()
    {
        ETable table = getTable();
        if(table == null)
            return "Parent:Null";
        return table.getIndexString() + ",TR:" + findIndex();
    }
    public String toString()
    {
        return "<ETR size=" + size() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",visible=" + isVisible() +
                ",isModify=" + isModify() +
                ",index=" + getIndexString() + ">";
    }
    /**
     * ���Զ������
     * @param i int
     */
    public void debugShow(int i)
    {
        System.out.println(StringTool.fill(" ",i) + this);
        for(int j = 0;j < size();j++)
        {
            ETD td = get(j);
            td.debugShow(i + 2);
        }
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
        for(int j = 0;j < size();j++)
        {
            ETD td = get(j);
            td.debugModify(i + 2);
        }
    }
    /**
     * ����ģ��
     * @param model ETRModel
     */
    public void setModel(ETRModel model)
    {
        this.model = model;
    }
    /**
     * �õ�ģ��
     * @return ETRModel
     */
    public ETRModel getModel()
    {
        return model;
    }
    /**
     * ����ģ��
     * @return ETRModel
     */
    public ETRModel createModel()
    {
        ETRModel model = new ETRModel(this);
        setModel(model);
        return model;
    }
    /**
     * ɾ���Լ�
     */
    public void removeThis()
    {
        removeThis(true);
    }
    /**
     * ɾ���Լ�
     * @param b boolean
     */
    public void removeThis(boolean b)
    {
        for(int i = size() - 1;i >= 0;i--)
            get(i).removeThis();
        ETable table = getTable();
        if(table == null)
            return;
        table.remove(this);
        if(getPreviousLinkTR() != null)
            getPreviousLinkTR().setNextLinkTR(getNextLinkTR());
        if(getNextLinkTR() != null)
            getNextLinkTR().setPreviousLinkTR(getPreviousLinkTR());
        if(b && table.size() == 0)
            table.removeThis();
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
        s.writeInt(4,getShowBorder(),0);
        s.writeBoolean(5,isShowHLine(),false);
        s.writeBoolean(6,isVisible(),true);
        if(getNextLinkTR() != null)
        {
            int index = getTRSaveManager().add(this);
            getNextLinkTR().setPreviousTRIndex(index);
            s.writeShort(7);
        }
        if(getPreviousLinkTR() != null)
        {
            s.writeShort(8);
            s.writeInt(getPreviousTRIndex());
        }
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
        switch(id)
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
            setShowBorder(s.readInt());
            return true;
        case 5:
            setShowHLine(s.readBoolean());
            return true;
        case 6:
            setVisible(s.readBoolean());
            return true;
        case 7:
            getTRSaveManager().add(this);
            break;
        case 8:
            ETR tr = getTRSaveManager().get(s.readInt());
            setPreviousLinkTR(tr);
            tr.setNextLinkTR(this);
            break;
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
        s.WO("<ETR Width=" + getWidth() + " Height=" + getHeight() + " >",c);
        if(getModel() != null)
            getModel().writeObjectDebug(s,c + 1);
        for(int i = 0;i < size();i ++)
        {
            ETD td = get(i);
            if(td == null)
                continue;
            td.writeObjectDebug(s,c + 1);
        }
        s.WO("</ETR>",c);
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
        for(int i = 0;i < size();i ++)
        {
            ETD td = get(i);
            td.writeObject(s);
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
            //����������
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //��ȡ��
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            ETD td = newTD();
            td.readObject(s);
            //System.out.println( "td: " + td);
        }
    }
    /**
     * �õ�������λ��
     * @return int
     */
    public int getPointX()
    {
        return getTable().getPointX() + getX();
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
        getTable().getPath(list);
    }
    /**
     * �õ�·������
     * @return TList
     */
    public TList getPathIndex()
    {
        TList list = new TList();
        getPathIndex(list);
        return list;
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
        getTable().getPathIndex(list);
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
     * ������ʾ�߿�
     * @param showBorder int
     */
    public void setShowBorder(int showBorder)
    {
        this.showBorder = showBorder;
    }
    /**
     * ������ʾ�߿�
     * @param x int
     * 1 ��
     * 2 ��
     * 4 ��
     * 8 ��
     * @param b boolean true ��ʾ false ����ʾ
     */
    public void setShowBorder(int x,boolean b)
    {
        if(b)
            showBorder = showBorder | x;
        else
            showBorder = ~(~showBorder | x);
    }
    /**
     * �õ���ʾ�߿�
     * @return int
     */
    public int getShowBorder()
    {
        return showBorder;
    }
    /**
     * �õ���ʾ�߿�
     * @param x int
     * 1 ��
     * 2 ��
     * 4 ��
     * 8 ��
     * @return boolean true ��ʾ false ����ʾ
     */
    public boolean isShowBorder(int x)
    {
        return (showBorder & x) == x;
    }
    /**
     * ������ʾ����
     * @param showHLine boolean
     */
    public void setShowHLine(boolean showHLine)
    {
        this.showHLine = showHLine;
    }
    /**
     * �Ƿ���ʾ����
     * @return boolean
     */
    public boolean isShowHLine()
    {
        return showHLine;
    }
    /**
     * �����Ƿ���ʾ
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
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
     * �õ���Ļ����
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DPoint point = getTable().getScreenPoint();
        point.setX(point.getX() + (int)(getX() * getZoom() / 75.0 + 0.5));
        point.setY(point.getY() + (int)(getY() * getZoom() / 75.0 + 0.5));
        return point;
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
     * ������һ������TR
     * @param previousLinkTR ETR
     */
    public void setPreviousLinkTR(ETR previousLinkTR)
    {
        this.previousLinkTR = previousLinkTR;
    }
    /**
     * �õ���һ������TR
     * @return ETR
     */
    public ETR getPreviousLinkTR()
    {
        return previousLinkTR;
    }
    /**
     * ������һ������TD
     * @param nextLinkTR ETR
     */
    public void setNextLinkTR(ETR nextLinkTR)
    {
        this.nextLinkTR = nextLinkTR;
    }
    /**
     * �õ���һ������TR
     * @return ETR
     */
    public ETR getNextLinkTR()
    {
        return nextLinkTR;
    }
    /**
     * �Ƿ�����һ������
     * @return boolean
     */
    public boolean hasNextLinkTR()
    {
        return getNextLinkTR() != null;
    }
    /**
     * �õ�TR������
     * @return MTRSave
     */
    public MTRSave getTRSaveManager()
    {
        return getPM().getFileManager().getTRSaveManager();
    }
    /**
     * ������һ��TR����������(����ר��)
     * @param index int
     */
    public void setPreviousTRIndex(int index)
    {
        previousTRIndex = index;
    }
    /**
     * �õ���һ��TR����������(����ר��)
     * @return int
     */
    public int getPreviousTRIndex()
    {
        return previousTRIndex;
    }
    /**
     * �������߶�
     * @param maxHeight int
     */
    public void setMaxHeight(int maxHeight)
    {
        this.maxHeight = maxHeight;
    }
    /**
     * �õ����߶�
     * @return int
     */
    public int getMaxHeight()
    {
        return maxHeight;
    }
    /**
     * ������һҳ����TR
     * @return ETR
     */
    public ETR createNextLinkTR()
    {
        ETR tr = getNextLinkTR();
        if(tr != null)
            return tr;
        ETable table = getTable().getNextTable();
        if(table == null)
            table = getTable().createNextLinkTable();
        int index = findIndex();
        for(int i = getTable().size() - 1;i > index;i--)
        {
            ETR tr1 = getTable().get(i);
            getTable().remove(tr1);
            table.add(0,tr1);
        }
        tr = table.newTR(0);
        tr.setX(0);
        tr.setY(0);
        tr.setWidth(getWidth());
        tr.setModify(true);
        setNextLinkTR(tr);
        tr.setPreviousLinkTR(this);
        tr.setModel(getModel());
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            ETD newTD = tr.newTD();
            newTD.setX(td.getX());
            newTD.setY(td.getY());
            newTD.setWidth(td.getWidth());
            td.setNextLinkTD(newTD);
            newTD.setPreviousLinkTD(td);
            newTD.setVisible(td.isVisible());
            newTD.setUniteTD(td.getUniteTD());
            newTD.setSpanX(td.getSpanX());
            newTD.setSpanY(td.getSpanY());
        }
        return tr;
    }
    /**
     * ���óߴ����
     * @param resetTR ResetTR
     */
    public void setResetTR(ResetTR resetTR)
    {
        this.resetTR = resetTR;
    }
    /**
     * �õ��ߴ����
     * @return ResetTR
     */
    public ResetTR getResetTR()
    {
        return resetTR;
    }
    /**
     * �س��¼�
     * @return boolean
     */
    public boolean onEnter()
    {
        int index = getTable().insertRow(findIndex() + 1);
        ETR tr = getTable().get(index);
        if(tr == null || tr.size() == 0)
            return true;
        ETD td = tr.get(0);
        EText text = (EText)td.get(0).get(0);
        getFocusManager().setFocusText(text);
        return true;
    }
    /**
     * ���������ƶ�
     * @param td ETD
     * @return boolean
     */
    public boolean onFocusToRight(ETD td)
    {
        ETD next = getNextTD(td);
        while(next != null)
        {
            //�õ���TD
            next = next.getHeadTD();
            if(next.setFocus())
                return true;
            next = getNextTD(next);
        }
        getFocusManager().setFocusTRE(getEndTR());
        return true;
    }
    /**
     * ���������ƶ�
     * @param td ETD
     * @return boolean
     */
    public boolean onFocusToLeft(ETD td)
    {
        ETD next = getPreviousTD(td);
        while(next != null)
        {
            if(next.getEndTD().setFocusLast())
                return true;
            next = getPreviousTD(next);
        }
        ETR tr = getHeadTR();
        tr = getTable().getPreviousTR(tr);
        if(tr != null)
        {
            getFocusManager().setFocusTRE(tr.getEndTR());
            return true;
        }
        if(getTable().getPreviousTable() != null)
            if(getTable().getPreviousTable().setFocusLast())
                return true;;
        if(getTable().getPanel().onNextPanelFocusToLeft())
            return true;
        return false;
    }
    /**
     * �õ���һ��TD
     * @param td ETD
     * @return ETD
     */
    public ETD getNextTD(ETD td)
    {
        int index = indexOf(td);
        if(index == -1)
            return null;
        index ++;
        if(index >= size())
            return null;
        return get(index);
    }
    /**
     * �õ���һ��TD
     * @param td ETD
     * @return ETD
     */
    public ETD getPreviousTD(ETD td)
    {
        int index = indexOf(td);
        if(index == -1)
            return null;
        index --;
        if(index < 0)
            return null;
        return get(index);
    }
    /**
     * �õ���TR
     * @return ETR
     */
    public ETR getHeadTR()
    {
        if(getPreviousLinkTR() != null)
            return getPreviousLinkTR().getHeadTR();
        return this;
    }
    /**
     * �õ�βTR
     * @return ETR
     */
    public ETR getEndTR()
    {
        if(getNextLinkTR() != null)
            return getNextLinkTR().getEndTR();
        return this;
    }
    /**
     * ���������ƶ�
     */
    public void onFocusToRight()
    {
        if(getTable().onFocusToRight(this))
            return;
        getTable().getPanel().onNextPanelFocusToRight();
    }
    /**
     * ���������ƶ�
     */
    public void onFocusToLeft()
    {
        if(size() == 0)
            return;
        ETD td = get(size() - 1);
        td.setFocusLast();
    }
    /**
     * �õ�����
     * @return boolean
     */
    public boolean setFocus()
    {
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            if(!td.isVisible())
                continue;
            if(td.setFocus())
                return true;
        }
        getFocusManager().setFocusTRE(getHeadTR());
        return true;
    }
    /**
     * ���ý��㵽���
     * @return boolean
     */
    public boolean setFocusLast()
    {
        getFocusManager().setFocusTRE(getEndTR());
        return true;
    }
    /**
     * �õ���һ������ģ��
     * @param td ETD
     * @return IBlock
     */
    public IBlock getDownFocusBlock(ETD td)
    {
        int index = indexOf(td);
        //�õ���һ����Ч��
        ETR tr = getNextTrueTR();
        if (tr != null)
        {
            if (index >= tr.size())
                index = tr.size() - 1;
            ETD ntd = tr.get(index);
            EPanel panel = ntd.get(0);
            return panel.get(0);
        }
        //�õ���һ�����
        EPanel panel = getTable().getPanel().getNextPanel();
        if (panel != null)
            return panel.getFirstText();
        return null;
    }
    /**
     * �õ���һ������ģ��
     * @param td ETD
     * @return IBlock
     */
    public IBlock getUpFocusBlock(ETD td)
    {
        int index = indexOf(td);
        //�õ���һ����Ч��
        ETR tr = getPreviousTrueTR();
        if (tr != null)
        {
            if (index >= tr.size())
                index = tr.size() - 1;
            ETD ntd = tr.get(index);
            while(ntd.size() == 0)
                ntd = ntd.getPreviousLinkTD();
            EPanel panel = ntd.get(ntd.size() - 1);
            return panel.getLastText();
        }
        //�õ���һ�����
        EPanel panel = getTable().getPanel().getPreviousPanel();
        if (panel != null)
            return panel.getLastText();
        return null;
    }
    /**
     * �õ���һ����Ч��
     * @return ETR
     */
    public ETR getNextTrueTR()
    {
        ETR tr = getEndTR();
        ETable table = tr.getTable();
        int index = tr.findIndex() + 1;
        //��ĩβ��
        if(index < table.size())
            return tr.getTable().get(index);
        table = table.getNextTable();
        if(table == null || table.size() == 0)
            return null;
        return table.get(0);
    }
    /**
     * �õ���һ����Ч��
     * @return ETR
     */
    public ETR getPreviousTrueTR()
    {
        ETR tr = getHeadTR();
        ETable table = tr.getTable();
        int index = tr.findIndex() - 1;
        //��ĩβ��
        if(index >= 0)
            return tr.getTable().get(index);
        table = table.getPreviousTable();
        if(table == null || table.size() == 0)
            return null;
        return table.get(table.size() - 1);
    }
    /**
     * ���������ƶ�
     */
    public void onEocusToUp()
    {
        getPreviousTrueTR().onFocusToLeft();
    }
    /**
     * ���������ƶ�
     */
    public void onEocusToDown()
    {
        getNextTrueTR().onFocusToLeft();
    }
    /**
     * ����޸ĺۼ�
     */
    public void resetModify()
    {
        if(!isModify())
            return;
        for(int i = 0;i < size();i ++)
            get(i).resetModify();
    }
    /**
     * ͬ�����
     */
    public void sychWidth()
    {
        ETableModel model = getTable().getModel();

        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);

            //
            int width = model.get(i);
            if(width != td.getWidth())
            {
            	if( !td.isVisible() && null!=td.getUniteTD()  ){
            		 td.setWidth(0);

            		 ETD firstTD = td.getUniteTD();

            		 if(  td.getTR().findIndex() == firstTD.getTR().findIndex() ){

                   		 int newW = firstTD.getWidth()+width+1;

                		 int tLength = getTable().getWidth();

                		 newW = newW > tLength ? tLength-width : newW;

                		 firstTD.setWidth(newW);

                	//	 System.out.println( "UniteTD " + firstTD );
            		 }

            	}else{
            		 td.setWidth(width);
            	}

                td.modifyPanels();
            }

           // System.out.println( "moveTD  " +td );
        }
    }

    /**
     * ѡ��ȫ��TR
     * @param isSelected boolean
     */
    public void setSelectedAll(boolean isSelected)
    {
        ETR tr = getHeadTR();
        while(tr != null)
        {
            tr.setSelected(isSelected);
            tr = tr.getNextLinkTR();
        }
    }
    /**
     * �����Ƿ�ѡ��
     * @param isSelected boolean
     */
    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
    /**
     * �Ƿ�ѡ��
     * @return boolean
     */
    public boolean isSelected()
    {
        return isSelected;
    }
    /**
     * �Ƿ�ѡ�л���
     * @return boolean
     */
    public boolean isSelectedDraw()
    {
        if(isSelected())
            return true;
        if(!getFocusManager().isSelected() &&
           getFocusManager().getFocusTR() == getHeadTR())
            return true;
        return false;
    }
    /**
     * �õ�������ڵ�TD
     * @return ETD
     */
    public ETD getComponentInTD()
    {
        return getTable().getComponentInTD();
    }
    /**
     * �õ����ڵ����
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return getTable().getPanel();
    }
    /**
     * �õ���
     * @return int
     */
    public int getRow()
    {
        return getTable().getRow(this);
    }
    /**
     * �õ�ȫ���������
     * @param list TList
     * @param all boolean
     */
    public void getPanelAll(TList list,boolean all)
    {
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            tr.get(i).getPanelAll(list,all);
    }
    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        if(action == null)
            return;
        ETR tr = getHeadTR();
        //ɾ������
        if(action.getType() == EAction.DELETE)
        {
            if(isLockEdit())
                return;
            onDeleteThis(action.getInt(0));
            if(getSelectedModel() != null)
            {
                getSelectedModel().getSelectList().remove(getSelectedModel());
                getSelectedModel().removeThis();
            }
            return;
        }
        for(int i = 0;i < tr.size();i++)
            tr.get(i).exeAction(action);
    }
    /**
     * ������TR��(��������TR)
     * @return boolean
     */
    public boolean focusInTR()
    {
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            if(tr.get(i).focusInTD())
                return true;
        return false;
    }
    /**
     * ɾ������
     * @param flg int
     * 0 ����ɾ��
     * 1 ��ͨɾ��
     * 2 ���
     */
    public void onDeleteThis(int flg)
    {
        switch(flg)
        {
        case 0:
        case 1:
            deleteTR();
            return;
        case 2:
            clearTR();
            return;
        }
    }
    /**
     * ɾ��TR
     */
    public void deleteTR()
    {
        //���TR
        clearTR();
        //������ڹ̶��ı� ����ɾ��TR
        if(hasFixed())
            return;
        if(getFocusTREnter() == this || getFocusTR() == this || focusInTR())
        {
            ETR tr = getNextTrueTR();
            if(tr != null)
                tr.setFocus();
            else
                getPanel().setNextFocus();
        }
        setModify(true);
        ETR tr = getHeadTR();
        while(tr != null)
        {
            ETR next = tr.getNextLinkTR();
            tr.removeThis(true);
            tr = next;
        }
    }
    /**
     * ���ڹ̶��ı�
     * @return boolean
     */
    public boolean hasFixed()
    {
        for(int i = 0;i < size();i++)
            if(get(i).hasFixedAll())
                return true;
        return false;
    }
    /**
     * ���TR
     */
    public void clearTR()
    {
        if(isLockEdit())
            return;
        setModify(true);
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            tr.get(i).onDeleteThis(false);
        if(getFocusTREnter() == this || getFocusTR() == this || focusInTR())
            tr.setFocus();
    }
    /**
     * ����ѡ�ж���
     * @param selectedModel CSelectTRModel
     */
    public void setSelectedModel(CSelectTRModel selectedModel)
    {
        this.selectedModel = selectedModel;
    }
    /**
     * �õ�ѡ�ж���
     * @return CSelectTRModel
     */
    public CSelectTRModel getSelectedModel()
    {
        return selectedModel;
    }
    /**
     * ����ѡ�ж���
     * @return CSelectTRModel
     */
    public CSelectTRModel createSelectedModel()
    {
        CSelectTRModel model = getSelectedModel();
        if(model != null)
            getFocusManager().getSelectedModel().removeSelectList(model.getSelectList());
        model = new CSelectTRModel(this);
        model.setSelectList(getFocusManager().getSelectedModel().getSelectedList());
        setSelectedModel(model);
        return model;
    }
    /**
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
        {
            EComponent com = tr.get(i).findObject(name,type);
            if(com != null)
                return com;
        }
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
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
            tr.get(i).filterObject(list,name,type);
    }
    /**
     * ������
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
        {
            EComponent com = tr.get(i).findGroup(group);
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
        ETR tr = getHeadTR();
        for(int i = 0;i < tr.size();i++)
        {
            String value = tr.get(i).findGroupValue(group);
            if(value != null)
                return value;
        }
        return null;
    }
    /**
     * ����ƶ�
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        int index = getMouseInComponentIndexXY(mouseX,mouseY);
        if(index == -1)
            return false;
        ETD td = get(index);
        return td.onMouseMoved(mouseX - (int)(td.getX() * getZoom() / 75.0 + 0.5),
                                           mouseY - (int)(td.getY() * getZoom() / 75.0 + 0.5));
    }
    /**
     * �õ�����������
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInComponentIndexXY(int mouseX,int mouseY)
    {
        double zoom = getZoom() / 75.0;
        if(size() ==0)
            return -1;
        for(int i = 0; i < size();i++)
        {
            ETD td = get(i);
            if(mouseX >= (int)(td.getX() * zoom + 0.5) &&
               mouseX <= (int)((td.getX() + td.getWidth()) * zoom + 0.5) &&
               mouseY >= (int)(td.getY() * zoom + 0.5) &&
               mouseY <= (int)((td.getY() + td.getHeight()) * zoom + 0.5))
                return i;
        }
        return -1;
    }
    /**
     * �õ��ϲ���Ԫ������TD���
     * @param list TList
     * @param td ETD
     */
    public void getUniteTDHandles(TList list,ETD td)
    {
        for(int i = 0;i < size();i++)
            get(i).getUniteTDHandles(list,td);
        ETR tr = getNextTrueTR();
        if(tr == null)
            return;
        tr.getUniteTDHandles(list,td);
    }
    /**
     * �����༭
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return getTable().isLockEdit();
    }
    /**
     * ��¡
     * @param table ETable
     * @return ETR
     */
    public ETR clone(ETable table)
    {
        ETR tr = new ETR(table);
        tr.setVisible(isVisible());
        tr.setX(getX());
        tr.setY(getY());
        tr.setWidth(getWidth());
        tr.setHeight(getHeight());
        tr.setShowBorder(getShowBorder());
        tr.setShowHLine(isShowHLine());
        for(int i = 0;i < size();i++)
        {
            ETD td = get(i);
            tr.add(td.clone(tr));
        }
        tr.setModify(true);
        return tr;
    }
    /**
     * �����к�
     * @param rowID int
     */
    public void setRowID(int rowID)
    {
        this.rowID = rowID;
    }
    /**
     * �õ��к�
     * @return int
     */
    public int getRowID()
    {
        return rowID;
    }
}
