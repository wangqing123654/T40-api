package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.util.TList;
import com.dongyang.tui.DInsets;
import java.awt.Rectangle;
import com.dongyang.util.StringTool;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.tui.DPoint;
import java.util.List;
import com.dongyang.data.TParm;
import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

/**
 *
 * <p>Title: ���Ԫ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.9.7
 * @author whao 2013~
 * @version 1.0
 */
public class ETD implements EComponent,EEvent,IExeAction
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
    private ETDModel model;
    /**
     * �к�
     */
    private int columnIndex = -1;
    /**
     * ����λ��
     * 0 left
     * 1 center
     * 2 right
     */
    private int alignment = 0;
    /**
     * ��һ������TD
     */
    private ETD previousLinkTD;
    /**
     * ��һ������TD
     */
    private ETD nextLinkTD;
    /**
     * ��һ��TD��������(����ר��)
     */
    private int previousTDIndex;
    /**
     * ���߶�
     */
    private int maxHeight = -1;
    /**
     * ���óߴ����
     */
    private ResetPanel resetPanel;
    /**
     * �Ƿ�ѡ��
     */
    private boolean isSelected;
    /**
     * ѡ�ж���
     */
    private CSelectTDModel selectedModel;
    /**
     * ��ԽX
     */
    private int spanX;
    /**
     * ��ԽY
     */
    private int spanY;
    /**
     * ��ʾ
     */
    private boolean visible = true;
    /**
     * ���ϵ�TD
     */
    private ETD uniteTD;
    /**
     * ������
     */
    public ETD()
    {
        components = new TList();
        //�����ߴ��������
        setResetPanel(new ResetPanel(this));
    }
    /**
     * ������
     * @param tr ETR
     */
    public ETD(ETR tr)
    {
        this();
        setParent(tr);
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
     * @param component EPanel
     */
    public void add(EPanel component)
    {
        if (component == null)
            return;
        components.add(component);
        component.setParent(this);
    }
    /**
     * ���ӳ�Ա
     * @param index int
     * @param component EPanel
     */
    public void add(int index,EPanel component)
    {
        if (component == null)
            return;
        components.add(index,component);
        component.setParent(this);
    }
    /**
     * ɾ����Ա
     * @param component EPanel
     */
    public void remove(EPanel component)
    {
        components.remove(component);
    }
    /**
     * ɾ����Ա
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
    }
    /**
     * ɾ��ȫ��
     */
    public void removeAll()
    {
        components.removeAll();
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
     * @return EPanel
     */
    public EPanel get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (EPanel) components.get(index);
    }
    /**
     * ����λ��
     * @param component EComponent
     * @return int
     */
    public int indexOf(EComponent component)
    {
        if(component == null)
            return -1;
        return components.indexOf(component);
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
     * �õ�TR
     * @return ETR
     */
    public ETR getTR()
    {
        EComponent com = getParent();
        if(com == null || !(com instanceof ETR))
            return null;
        return (ETR)getParent();
    }
    /**
     * �õ�Table
     * @return ETable
     */
    public ETable getTable()
    {
        ETR tr = getTR();
        if(tr == null)
            return null;
        return tr.getTable();
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
     * �õ��ڲ��ߴ�
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return getTable().getTDInsets();
    }
    /**
     * �õ��ڲ�X
     * @return int
     */
    public int getInsetsX()
    {
        return getInsets().left;
    }
    /**
     * �õ��ڲ�Y
     * @return int
     */
    public int getInsetsY()
    {
        return getInsets().top;
    }
    /**
     * �õ��ڲ����
     * @return int
     */
    public int getInsetsWidth()
    {
        DInsets insets = getInsets();
        return getWidth() - insets.left - insets.right;
    }
    /**
     * �õ��ڲ��߶�
     * @return int
     */
    public int getInsetsHeight()
    {
        DInsets insets = getInsets();
        return getHeight() - insets.top - insets.bottom;
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
     * �޸����
     */
    public void modifyPanels()
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if (panel == null)
                continue;
            panel.setModify(true);
        }
    }
    /**
     * �Ƿ�����״̬
     * @return boolean
     */
    public boolean isPreview()
    {
        return getTR().isPreview();
    }
    /**
     * �Ƿ���Table�����һ��
     * @return boolean
     */
    public boolean isLastTR()
    {
        return getTR().isLastTR();
    }
    /**
     * �Ƿ���Table�ĵ�һ��
     * @return boolean
     */
    public boolean isFirstTR()
    {
        return getTR().isFirstTR();
    }
    /**
     * �Ƿ������һ�����
     * @return boolean
     */
    public boolean isLastTD()
    {
        return getTR().isLastTD(this);
    }
    /**
     * �Ƿ��ǵ�һ����Ԫ��
     * @return boolean
     */
    public boolean isFirstTD()
    {
        return getTR().isFirstTD(this);
    }
    /**
     * �Ƿ������һ�е�TD
     * @return boolean
     */
    public boolean isLastRowTD()
    {
        if(isLastTR())
            return true;
        if(getSpanY() == 0)
            return false;
        if(getSpanY() == getTable().getThisTableLastRow() - getTR().getRow())
            return true;
        return false;
    }
    /**
     * �������ű���
     * @param x double
     * @return int
     */
    public int computeZoom(double x)
    {
        if(x == 0)
            return 0;
        return (int) (x * (double)getZoom() / 75.0);
    }
    /**
     * �����м�����߸�������
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintRowShowWLineForData(Graphics g,int x,int y,int width,int height,boolean print)
    {
        CTable ctable = getTable().getHeadTable().getCTable();
        if(ctable == null)
            return;
        TParm parm = ctable.getData();
        if(parm == null)
            return;
        if(getTR().getEndTR() != getTR())
            return;
        ETRModel model = getTR().getHeadTR().getModel();
        if(model == null)
            return;
        int row = model.getRow();
        if(row < 0 || row >= parm.getCount())
            return;
        if(!parm.getBoolean(".TableRowLineShow",row))
            return;
        g.setColor(new Color(0, 0, 0));
        int x1 = x;
        int y1 = y + height;
        int x2 = x + width;
        int y2 = y + height;
        //����������֮��
        double dh = (double)getTR().getInsets().bottom +
                    (double)getTable().getHSpace() / 2.0;
        int h = print?(int)dh:computeZoom(dh);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        g.drawLine(x1,y1 + h,x2,y2 + h);
    }
    /**
     * �����м������
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintRowShowWLine(Graphics g,int x,int y,int width,int height,boolean print)
    {
        //���һ�в�����
        if(isLastRowTD() && (getTable().isShowBorder() || getTable().isShowBorder2()))
            return;
        boolean isShowWLine = getTable().isShowWLine();
        if(!isShowWLine && isPreview())
            return;
        //������ɫ
        if (isShowWLine)
            g.setColor(new Color(0, 0, 0));
        else
            g.setColor(new Color(168, 168, 168));
        int x1 = x;
        int y1 = y + height;
        int x2 = x + width;
        int y2 = y + height;
        //����������֮��
        int w = print?(int)((double)getTable().getWSpace() / 2.0):
                computeZoom((double)getTable().getWSpace() / 2.0);
        //��һ����Ԫ���Ǳ���ڳߴ�����ڳߴ��Ӱ��
        if(isFirstTD())
        {
            int left = getTR().getInsets().left;
            if(!getTable().isShowBorder())
                left += getTable().getInsets().left;
            if(left > 0)
                x1 -= print?left:computeZoom(left);
        }else
            x1 -= w;
        //���һ����Ԫ���Ǳ���ڳߴ�����ڳߴ��Ӱ��
        if(isLastTD())
        {
            int right = getTR().getInsets().right;
            if(!getTable().isShowBorder())
                right += getTable().getInsets().right;
            if(right > 0)
                x2 += print?right:computeZoom(right) - 1;
        }else
            x2 += w;
        //����������֮��
        double dh = (double)getTR().getInsets().bottom +
                    (double)getTable().getHSpace() / 2.0;
        int h = print?(int)dh:computeZoom(dh);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        g.drawLine(x1,y1 + h,x2,y2 + h);
    }
    public String getPValue()
    {
        ETRModel module = getTR().getModel();
        if(module == null)
            return "";
        int row = module.getRow();
        if(row < 0)
            return "";
        CTable cTable = getTable().getHeadTable().getCTable();
        if(cTable == null)
            return "";
        String tableName = cTable.getTableID();
        if(tableName == null || tableName.length() == 0)
            return "";
        Object obj = getPM().getFileManager().getParameter();
        if(!(obj instanceof TParm))
            return "";
        TParm parm = (TParm) obj;
        if(parm.getData() == null)
            return "";
        parm = parm.getParm(tableName);
        Vector v = (Vector)parm.getData("TABLE_VALUE");
        if(v == null || v.size() == 0)
            return "";
        String s = (String)v.get(row);
        return s;
    }
    public boolean isshowHLine()
    {
        String s = getPValue();
        if(s == null || s.length() == 0)
            return true;
        String value = getSValue(s,"HLine");
        if(value == null || value.length() == 0)
            return true;
        return StringTool.getBoolean(value);
    }
    public String getSValue(String s,String name)
    {
        String ss[] = StringTool.parseLine(s,";");
        for(int i = 0;i < ss.length;i++)
        {
            int index = ss[i].indexOf("=");
            String s1 = ss[i].substring(0,index);
            String s2 = ss[i].substring(index + 1).trim();
            if(!s1.toUpperCase().trim().equals(name.toUpperCase().trim()))
                continue;
            return s2;
        }
        return "";
    }
    /**
     * �����м������
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param print boolean
     */
    public void paintRowShowHLine(Graphics g,int x,int y,int width,int height,boolean print)
    {
        //���һ����Ԫ�񲻻���
        if(isLastTD())
            return;
        //�Ƿ������
        if(!isshowHLine())
            return;
        boolean isShowHLine = getTable().isShowHLine();
        if(!isShowHLine && isPreview())
            return;
        //������ɫ
        if (isShowHLine)
            g.setColor(new Color(0, 0, 0));
        else
            g.setColor(new Color(168, 168, 168));
        int x1 = x + width;
        int y1 = y - (print?getTR().getInsets().top:computeZoom(getTR().getInsets().top));
        int x2 = x + width;
        int y2 = y + height + (print?getTR().getInsets().bottom:computeZoom(getTR().getInsets().bottom));
        //���м��
        int h = print?(int)((double)getTable().getHSpace() / 2.0):computeZoom((double)getTable().getHSpace() / 2.0);
        if(isFirstTR())
        {
            int top = 0;
            if(!getTable().isShowBorder())
                top = getTable().getInsets().top;
            if(top > 0)
                y1 -= print?top:computeZoom(top);
        }else
            y1 -= h;
        if(isLastRowTD())
        {
            int bottom = 0;
            if(!getTable().isShowBorder())
                bottom = getTable().getInsets().bottom;
            if(bottom > 0)
                y2 += print?bottom:computeZoom(bottom);
            y2 -= 1;
        }else
            y2 += h;

        //����������֮��
        int w = print?(int)((double)getTable().getWSpace() / 2.0):computeZoom((double)getTable().getWSpace() / 2.0);
        g.drawLine(x1 + w,y1,x2 + w,y2);
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
        if(getViewManager().isTDDebug())
        {
            g.setColor(new Color(0, 0, 128));
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
        }
        //ѡ�б���з�ɫ
        if(isSelected())
        {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(x,y,width,height);
        }
        //�����м������
        paintRowShowWLine(g,x,y,width,height,false);
        //�����м������
        paintRowShowHLine(g,x,y,width,height,false);
        //�����м�����߸�������
        paintRowShowWLineForData(g,x,y,width,height,false);

        double zoom = getZoom() / 75.0;
        //�������
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            int pX = (int)(panel.getX() * zoom + 0.5);
            int pY = (int)(panel.getY() * zoom + 0.5);
            int pW = (int)(panel.getWidth() * zoom + 0.5);
            int pH = (int)(panel.getHeight() * zoom + 0.5);

            Rectangle r = g.getClipBounds();
            if(r.getX() > x + pX + pW ||
               r.getY() > y + pY + pH ||
               r.getX() + r.getWidth() < x + pX ||
               r.getY() + r.getHeight() < y + pY)
                continue;
            //����
            panel.paint(g,x + pX,y + pY,pW,pH);
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
        if(!isVisible())
            return;
        //�����м������
        paintRowShowWLine(g,x,y,width,height,true);
        //�����м������
        paintRowShowHLine(g,x,y,width,height,true);
        //�����м�����߸�������
        paintRowShowWLineForData(g,x,y,width,height,true);
        //�������
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            //����
            panel.print(g,x + panel.getX(),y + panel.getY());
        }
    }
    /**
     * �õ�����������
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInPanelIndex(int mouseX,int mouseY)
    {
        double zoom = getZoom() / 75.0;
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if((int)(panel.getX() * zoom + 0.5) <= mouseX &&
               (int)((panel.getX() + panel.getWidth()) * zoom + 0.5) >= mouseX &&
               (int)(panel.getY() * zoom + 0.5) <= mouseY &&
               (int)((panel.getY() + panel.getHeight()) * zoom + 0.5) >= mouseY)
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
     * 2 ETD
     * 3 ETR Enter
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        //����������
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex == -1)
        {
            if(size() > 0)
                panelIndex = size() - 1;
            else
            {
                if(getPreviousLinkTD() == null)
                    return -1;
                return getPreviousLinkTD().onMouseLeftPressed(mouseX,mouseY);
            }
        }
        EPanel panel = get(panelIndex);
        int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
        //�¼�����
        return panel.onMouseLeftPressed(x,y);
    }
    /**
     * �Ҽ�����
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        //����������
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex == -1)
        {
            if(size() > 0)
                panelIndex = size() - 1;
            else
            {
                if(getPreviousLinkTD() == null)
                    return;
                getPreviousLinkTD().onMouseRightPressed(mouseX,mouseY);
                return;
            }
        }
        EPanel panel = get(panelIndex);
        int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
        //�¼�����
        panel.onMouseRightPressed(x,y);
    }
    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        //����������
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex != -1)
        {
            EPanel panel = get(panelIndex);
            int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            return panel.onDoubleClickedS(x,y);
        }
        return false;
    }
    /**
     * �����
     * @return EPanel
     */
    public EPanel newPanel()
    {
        EPanel panel = new EPanel(this);
        //���ú�����
        panel.setX(getInsetsX());
        //����������
        panel.setWidth(getInsetsWidth());
        add(panel);
        return panel;
    }
    /**
     * �����
     * @param index int
     * @return EPanel
     */
    public EPanel newPanel(int index)
    {
        EPanel panel = new EPanel(this);
        //���ú�����
        panel.setX(getInsetsX());
        //����������
        panel.setWidth(getInsetsWidth());
        add(index,panel);
        return panel;
    }
    /**
     * �õ����һ��
     * @return EText
     */
    public EText getLastText()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            EText text = panel.getLastText();
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
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            EText text = panel.getFirstText();
            if(text != null)
                return text;
        }
        return null;
    }
    /**
     * ����ǰһ�����
     * @param panel EPanel
     * @return EText
     */
    public EText getPreviousText(EPanel panel)
    {
        if(panel == null)
            return null;
        int index = indexOf(panel);
        if(index == -1)
            return null;
        for(int i = index - 1;i >= 0;i--)
        {
            EPanel p = get(i);
            if(p == null)
                continue;
            EText text = p.getLastText();
            if(text != null)
                return text;
        }
        if(getPreviousLinkTD() != null)
            return getPreviousLinkTD().getLastText();
        ETR tr = getTR();
        if(tr == null)
            return null;
        EText text = tr.getPreviousText(this);
        if(text != null)
            return text;
        //temp
        return null;
    }
    /**
     * ������һ�����
     * @param panel EPanel
     * @return EText
     */
    public EText getNextText(EPanel panel)
    {
        if(panel == null)
            return null;
        int index = indexOf(panel);
        if(index == -1)
            return null;
        for(int i = index + 1;i < size();i++)
        {
            EPanel p = get(i);
            if(p == null)
                continue;
            EText text = p.getFirstText();
            if(text != null)
                return text;
        }
        if(getNextLinkTD() != null)
            return getNextLinkTD().getFirstText();

        ETR tr = getTR();
        EText text = tr.getNextText(this);
        if(text != null)
            return text;
        //temp
        return null;
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
            //�޸Ļᴫ
            update(false);
            if(getParent() != null)
                getParent().setModify(true);
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
        //if(!isModify())
        //    return;
        //����
        getResetPanel().clear();
        //���óߴ緶Χ
        getResetPanel().setRectangle(getInsetsX(),
                                     getInsetsY(),
                                     getInsetsWidth(),
                                     getMaxHeight() - getInsets().top - getInsets().bottom);
        //�����ߴ�
        getResetPanel().reset();
        setModify(false);
    }
    /**
     * �����ߴ�
     * @param index int
     */
    public void reset(int index)
    {
        if (index < 0 || index >= size())
            return;
        int x = getInsetsX();
        int y = getInsetsY();
        int width = getInsetsWidth();
        for(int i = index;i < size();i++)
        {
            EPanel panel = get(i);
            if (panel == null)
                continue;
            /*if (!panel.isModify() && panel.getY() != y)
                panel.setModify(true);
            if (!panel.isModify())
            {
                y = panel.getY() + panel.getHeight();
                continue;
            }*/
            panel.setY(y);
            panel.setX(x);
            panel.setWidth(width);
            if(getMaxHeight() > 0)
            {
                panel.setMaxHeight(getMaxHeight() - y - getInsets().bottom);
                panel.setModify(true);
            }
            panel.reset();
            y = panel.getY() + panel.getHeight();
        }
        if(size() == 0)
            return;
        EPanel panel = get(size() - 1);
        DInsets insets = getInsets();
        int height = panel.getY() + panel.getHeight() + insets.bottom;
        setHeight(height);
    }
    /**
     * �������λ��
     * @param td ETD
     * @return int
     */
    public int findIndex(ETD td)
    {
        ETR tr = getTR();
        if(tr == null)
            return -1;
        return tr.indexOf(td);
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
        ETR tr = getTR();
        if(tr == null)
            return "Parent:Null";
        return tr.getIndexString() + ",TD:" + findIndex();
    }
    public String toString()
    {
        return "<ETD size=" + size() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",isModify=" + isModify() +
                ",visible=" + isVisible() +
                ",uniteTD=" + (getUniteTD() == null?"null":getUniteTD().getIndexString()) +
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
            EPanel panel = get(j);
            panel.debugShow(i + 2);
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
        for(int j = 0;j < size();j++)
        {
            EPanel panel = get(j);
            panel.debugModify(i + 2);
        }
    }
    /**
     * �����ı�
     * @param s String
     */
    public void setString(String s)
    {
        //ɾ��ȫ��
        removeAll();
        String data[];
        if(s == null || s.length() == 0)
            data = new String[]{""};
        else
            data = StringTool.separateEnter(s);
        for(int i = 0;i < data.length;i++)
        {
            EPanel panel = newPanel();
            panel.setString(data[i]);
        }
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getString()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < size();i++)
        {
            if(i > 0)
                sb.append("\n");
            sb.append(get(i).getString());
        }
        return sb.toString();
    }
    /**
     * ����ģ��
     * @param model ETDModel
     */
    public void setModel(ETDModel model)
    {
        this.model = model;
    }
    /**
     * �õ�ģ��
     * @return ETDModel
     */
    public ETDModel getModel()
    {
        return model;
    }
    /**
     * ����ģ��
     * @return ETDModel
     */
    public ETDModel createModel()
    {
        ETDModel model = new ETDModel(this);
        setModel(model);
        return model;
    }
    /**
     * ����ͬ��
     * @param b boolean true ����TD false ����TD
     */
    public void update(boolean b)
    {
        if(getModel() == null)
            return;
        //getModel().update(b);
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
        s.writeInt(3,getColumnIndex(),0);
        s.writeInt(4,getAlignment(),0);
        if(getNextLinkTD() != null)
        {
            int index = getTDSaveManager().add(this);
            getNextLinkTD().setPreviousTDIndex(index);
            s.writeShort(5);
        }
        if(getPreviousLinkTD() != null)
        {
            s.writeShort(6);
            s.writeInt(getPreviousTDIndex());
        }
        s.writeInt(7,getSpanX(),0);
        s.writeInt(8,getSpanY(),0);
        s.writeBoolean(9,isVisible(),true);
        if(isSpan())
            getTDSaveUniteManager().add(this);
        if(getUniteTD() != null)
        {
            int index = getTDSaveUniteManager().indexOf(getUniteTD());
            s.writeShort(10);
            s.writeInt(index);
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
            setColumnIndex(s.readInt());
            return true;
        case 4:
            setAlignment(s.readInt());
            return true;
        case 5:
            getTDSaveManager().add(this);
            return true;
        case 6:
            ETD td = getTDSaveManager().get(s.readInt());
            setPreviousLinkTD(td);
            td.setNextLinkTD(this);
            return true;
        case 7:
            setSpanX(s.readInt());
            return true;
        case 8:
            setSpanY(s.readInt());
            return true;
        case 9:
            setVisible(s.readBoolean());
            return true;
        case 10:
            setUniteTD(getTDSaveUniteManager().get(s.readInt()));
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
        s.WO("<ETD Width=" + getWidth() + " Height=" + getHeight() + " >",c);
        for(int i = 0;i < size();i ++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.writeObjectDebug(s,c + 1);
        }
        s.WO("</ETD>",c);
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
            EPanel panel = get(i);
            panel.writeObject(s);
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
        if(isSpan())
            getTDSaveUniteManager().add(this);
        //��ȡ��
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EPanel panel = newPanel();
            panel.readObject(s);
        }
    }
    /**
     * �õ�������λ��
     * @return int
     */
    public int getPointX()
    {
        return getTR().getPointX() + getX();
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
        getTR().getPath(list);
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
        getTR().getPathIndex(list);
    }
    /**
     * ɾ���Լ�
     */
    public void removeThis()
    {
        getTR().remove(this);
        for(int i = size() - 1;i >= 0;i--)
            get(i).removeThis();
        if(getPreviousLinkTD() != null)
            getPreviousLinkTD().setNextLinkTD(getNextLinkTD());
        if(getNextLinkTD() != null)
            getNextLinkTD().setPreviousLinkTD(getPreviousLinkTD());
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
     * �����к�
     * @param columnIndex int
     */
    public void setColumnIndex(int columnIndex)
    {
        this.columnIndex = columnIndex;
    }
    /**
     * �õ��к�
     * @return int
     */
    public int getColumnIndex()
    {
        return columnIndex;
    }
    /**
     * ���ö���λ��(��ǰλ��)
     * @param alignment int
     * 0 left
     * 1 center
     * 2 right
     */
    public void setAlignment(int alignment)
    {
        if(getAlignment() == alignment)
            return;
        this.alignment = alignment;
        if(getColumnIndex() == -1)
            return;
        CTable cTable = getCTable();
        if(cTable == null)
            return;
        switch(getTRModuleType())
        {
        case ETRModel.COLUMN_TYPE://������
        case ETRModel.COLUMN_EDIT://�б༭
            cTable.getColumnModel().get(getColumnIndex()).setAlignment(alignment);
            break;
        case ETRModel.COLUMN_MAX:
            break;
        case ETRModel.GROUP_HEAD:
            cTable.getColumnModel().get(getColumnIndex()).setAlignment(alignment);
            break;
        case ETRModel.GROUP_SUM:
            cTable.getColumnModel().get(getColumnIndex()).setAlignment(alignment);
            break;
        }
    }
    /**
     * �õ�����λ��
     * @return int
     * 0 left
     * 1 center
     * 2 right
     */
    public int getAlignment()
    {
        return alignment;
    }
    /**
     * �õ�Table������
     * @return CTable
     */
    public CTable getCTable()
    {
        ETable table = getTable();
        if(table == null)
            return null;
        return table.getCTable();
    }
    /**
     * �õ�TRModel
     * @return ETRModel
     */
    public ETRModel getTRModel()
    {
        ETR tr = getTR();
        if(tr == null)
            return null;
        return tr.getModel();
    }
    /**
     * �õ�TR����
     * @return int
     */
    public int getTRModuleType()
    {
        ETRModel etrModel= getTRModel();
        if(etrModel == null)
            return -1;
        return etrModel.getType();
    }
    /**
     * �õ���Ļ����
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DPoint point = getTR().getScreenPoint();
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
     * ������һ������TD
     * @param previousLinkTD ETD
     */
    public void setPreviousLinkTD(ETD previousLinkTD)
    {
        this.previousLinkTD = previousLinkTD;
    }
    /**
     * �õ���һ������TD
     * @return ETD
     */
    public ETD getPreviousLinkTD()
    {
        return previousLinkTD;
    }
    /**
     * ������һ������TD
     * @param nextLinkTD ETD
     */
    public void setNextLinkTD(ETD nextLinkTD)
    {
        this.nextLinkTD = nextLinkTD;
    }
    /**
     * �õ���һ������TD
     * @return ETD
     */
    public ETD getNextLinkTD()
    {
        return nextLinkTD;
    }
    /**
     * �õ�TD������
     * @return MTDSave
     */
    public MTDSave getTDSaveManager()
    {
        return getPM().getFileManager().getTDSaveManager();
    }
    /**
     * �õ�TD�ϲ���Ԫ�������
     * @return MTDSave
     */
    public MTDSave getTDSaveUniteManager()
    {
        return getPM().getFileManager().getTDSaveUniteManager();
    }
    /**
     * ������һ��TD����������(����ר��)
     * @param index int
     */
    public void setPreviousTDIndex(int index)
    {
        previousTDIndex = index;
    }
    /**
     * �õ���һ��TD����������(����ר��)
     * @return int
     */
    public int getPreviousTDIndex()
    {
        return previousTDIndex;
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
     * ������һ������TD
     * @return ETD
     */
    public ETD newNextTD()
    {
        getTR().createNextLinkTR();
        return getNextLinkTD();
    }
    /**
     * ������һҳ�������
     * @return EPanel
     */
    public EPanel createNextPageLinkPanel()
    {
        ETD td = getNextLinkTD();
        if(td == null)
        {
            getTR().createNextLinkTR();
            td = getNextLinkTD();
        }
        if(td == null)
            return null;
        EPanel panel = td.get(0);
        if(panel == null)
        {
            panel = td.newPanel();
            panel.setPreviousLinkPanel(get(size() - 1));
            panel.getPreviousLinkPanel().setNextLinkPanel(panel);
        }
        panel.setModify(true);
        return panel;
    }
    /**
     * ����Panel
     * ��p1�����ȫ�����ݷ��뵽p2����
     * @param p1 EPanel �Լ���Panel
     * @param p2 EPanel ����ҳPanel
     */
    public void separatePanel(EPanel p1,EPanel p2)
    {
        if(p1 == null || p2 == null)
            return;
        int index = indexOf(p1);
        if(index == -1)
            return;
        if(index >= size() - 1)
            return;
        ETD td = p2.getParentTD();
        if(td == null)
            return;
        int indexNext = td.indexOf(p2);
        if(indexNext == -1)
            return;
        index ++;
        while(index < size())
        {
            EPanel p = get(index);
            indexNext ++;
            td.add(indexNext,p);
            remove(index);
        }
    }
    /**
     * ���óߴ��������
     * @param resetPanel ResetPanel
     */
    public void setResetPanel(ResetPanel resetPanel)
    {
        this.resetPanel = resetPanel;
    }
    /**
     * �õ��ߴ��������
     * @return ResetPanel
     */
    public ResetPanel getResetPanel()
    {
        return resetPanel;
    }
    /**
     * �õ���һ�����
     * @param panel EPanel
     * @return EPanel
     */
    public EPanel getNextPanel(EPanel panel)
    {
        int index = indexOf(panel);
        if(index == -1)
            return null;
        index ++;
        if(index >= size())
            return null;
        return get(index);
    }
    /**
     * �õ���һ�����
     * @param panel EPanel
     * @return EPanel
     */
    public EPanel getPreviousPanel(EPanel panel)
    {
        int index = indexOf(panel);
        if(index == -1)
            return null;
        index --;
        if(index < 0)
            return null;
        return get(index);
    }
    /**
     * ���������ƶ�
     * @param panel EPanel
     * @return boolean
     */
    public boolean onFocusToRight(EPanel panel)
    {
        EPanel next = getNextPanel(panel);
        while(next != null)
        {
            if (next.setFocus())
                return true;
            next = getNextPanel(panel);
        }
        if(getNextLinkTD() != null && getNextLinkTD().size() > 0)
            return getNextLinkTD().setFocus();
        return getTR().onFocusToRight(this);
    }
    /**
     * ���������ƶ�
     * @param panel EPanel
     * @return boolean
     */
    public boolean onFocusToLeft(EPanel panel)
    {
        EPanel next = getPreviousPanel(panel);
        while(next != null)
        {
            if (next.setFocusLast())
                return true;
            next = getPreviousPanel(panel);
        }
        if(getPreviousLinkTD() != null && getPreviousLinkTD().size() > 0)
            return getPreviousLinkTD().setFocusLast();
        return getTR().onFocusToLeft(this);
    }
    /**
     * ���ý���
     * @return boolean
     */
    public boolean setFocus()
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel.setFocus())
                return true;
        }
        if(getNextLinkTD() != null)
            return getNextLinkTD().setFocus();
        return false;
    }
    /**
     * ���ý������
     * @return boolean
     */
    public boolean setFocusLast()
    {
        for (int i = size() - 1; i >= 0; i--)
        {
            EPanel panel = get(i);
            if (panel.setFocusLast())
                return true;
        }
        if(getPreviousLinkTD() != null)
            return getPreviousLinkTD().setFocusLast();
        return false;
    }
    /**
     * �õ���TD
     * @return ETD
     */
    public ETD getHeadTD()
    {
        if(getPreviousLinkTD() != null)
            return getPreviousLinkTD().getHeadTD();
        return this;
    }
    /**
     * �õ�βTD
     * @return ETD
     */
    public ETD getEndTD()
    {
        if(getNextLinkTD() != null)
            return getNextLinkTD().getEndTD();
        return this;
    }
    /**
     * �õ���һ������ģ��
     * @return IBlock
     */
    public IBlock getDownFocusBlock()
    {
        return getTR().getDownFocusBlock(this);
    }
    /**
     * �õ���һ������ģ��
     * @return IBlock
     */
    public IBlock getUpFocusBlock()
    {
        return getTR().getUpFocusBlock(this);
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
     * �Ƿ�ѡ�л���
     * @return boolean
     */
    public boolean isSelectedDraw()
    {
        if(isSelected())
            return true;
        return getTR().isSelectedDraw();
    }
    /**
     * �õ�������ڵ�TD
     * @return ETD
     */
    public ETD getComponentInTD()
    {
        return getTR().getComponentInTD();
    }
    /**
     * ����TD�Ƿ��Ǳ�TD�ĺ���TD
     * @param td ETD
     * @return boolean
     */
    public boolean isNextListTD(ETD td)
    {
        if(td == null)
            return false;
        if(td == this)
            return true;
        td = td.getPreviousLinkTD();
        while(td != null)
        {
            if(td == this)
                return true;
            td = td.getPreviousLinkTD();
        }
        return false;
    }
    /**
     * �õ�TD���ڵ����
     * @return EPanel
     */
    public EPanel getPanel()
    {
        return getTR().getPanel();
    }
    /**
     * ѡ��ȫ��TR
     * @param isSelected boolean
     */
    public void setSelectedAll(boolean isSelected)
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            td.setSelected(isSelected);
            td = td.getNextLinkTD();
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
     * �õ�ȫ���������
     * @param list TList
     * @param all boolean
     */
    public void getPanelAll(TList list,boolean all)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel.getPreviousLinkPanel() == null)
            {
                if(list.indexOf(panel) == -1)
                    list.add(panel);
                if(all)
                    panel.getPanelAll(list,all);
            }
        }
        if(getNextLinkTD() != null)
            getNextLinkTD().getPanelAll(list,all);
    }
    /**
     * ������TD��(��������TD)
     * @return boolean
     */
    public boolean focusInTD()
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
                if(getFocusManager().focusInPanel(td.get(i)))
                    return true;
            td = td.getNextLinkTD();
        }
        return false;
    }
    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        if(action == null)
            return;
        //ɾ������
        if(action.getType() == EAction.DELETE)
        {
            if(isLockEdit())
                return;
            switch(action.getInt(0))
            {
            case 0:
                onDeleteThis(true);
                break;
            case 1:
            case 2:
                onDeleteThis(false);
                break;
            }
            if(getSelectedModel() != null)
            {
                getSelectedModel().getSelectList().remove(getSelectedModel());
                getSelectedModel().removeThis();
            }
            return;
        }
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel.getPreviousLinkPanel() == null)
                panel.exeAction(action);
        }
        if(getNextLinkTD() != null)
            getNextLinkTD().exeAction(action);
    }
    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setPreviousFocus()
    {
        if(getPreviousLinkTD() != null)
            getPreviousLinkTD().setFocusLast();
        return false;
    }
    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setNextFocus()
    {
        if(getNextLinkTD() != null)
            getNextLinkTD().setFocus();
        return false;
    }
    /**
     * ɾ������
     * @param flg boolean true ȫ��ɾ�� false ��ͨɾ��
     */
    public void onDelete(boolean flg)
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EPanel panel = get(i);
            //�������
            if(i != size() - 1)
                panel.onDeleteEnter();
            //ɾ�����
            panel.onDelete(flg);
        }
    }
    /**
     * ɾ������
     * @param flg boolean true ����ɾ�� false ��ͨɾ��
     */
    public void onDeleteThis(boolean flg)
    {
        setModify(true);
        ETD td = getEndTD();
        while (td != null)
        {
            td.onDelete(flg);
            td = td.getPreviousLinkTD();
        }
        if (getSelectedModel() != null)
        {
            getSelectedModel().getSelectList().remove(getSelectedModel());
            getSelectedModel().removeThis();
        }
    }
    /**
     * ���ڹ̶��ı�
     * @return boolean
     */
    public boolean hasFixed()
    {
        for(int i = 0;i < size();i++)
        {
            if(get(i).hasFixed())
                return true;
        }
        return false;
    }
    /**
     * ���ڹ̶��ı�
     * @return boolean
     */
    public boolean hasFixedAll()
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            if(td.hasFixed())
                return true;
            td = getNextLinkTD();
        }
        return false;
    }
    /**
     * ����ѡ�ж���
     * @param selectedModel CSelectTDModel
     */
    public void setSelectedModel(CSelectTDModel selectedModel)
    {
        this.selectedModel = selectedModel;
    }
    /**
     * �õ�ѡ�ж���
     * @return CSelectTDModel
     */
    public CSelectTDModel getSelectedModel()
    {
        return selectedModel;
    }
    /**
     * ����ѡ�ж���
     * @return CSelectTDModel
     */
    public CSelectTDModel createSelectedModel()
    {
        CSelectTDModel model = getSelectedModel();
        if(model != null)
            getFocusManager().getSelectedModel().removeSelectList(model.getSelectList());
        model = new CSelectTDModel(this);
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
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
            {
                EComponent com = td.get(i).findObject(name,type);
                if(com != null)
                    return com;
            }
            td = td.getNextLinkTD();
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
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
                td.get(i).filterObject(list,name,type);
            td = td.getNextLinkTD();
        }
    }
    /**
     * ������
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group)
    {
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
            {
                EComponent com = td.get(i).findGroup(group);
                if(com != null)
                    return com;
            }
            td = td.getNextLinkTD();
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
        ETD td = getHeadTD();
        while(td != null)
        {
            for(int i = 0;i < td.size();i++)
            {
                String value = td.get(i).findGroupValue(group);
                if(value != null)
                    return value;
            }
            td = td.getNextLinkTD();
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
        EPanel panel = get(index);
        return panel.onMouseMoved(mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5),
                                           mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5));
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
            EPanel panel = get(i);
            if(mouseX >= (int)(panel.getX() * zoom + 0.5) &&
               mouseX <= (int)((panel.getX() + panel.getWidth()) * zoom + 0.5) &&
               mouseY >= (int)(panel.getY() * zoom + 0.5) &&
               mouseY <= (int)((panel.getY() + panel.getHeight()) * zoom + 0.5))
                return i;
        }
        return -1;
    }
    /**
     * ���ÿ�ԽX
     * @param spanX int
     */
    public void setSpanX(int spanX)
    {
        this.spanX = spanX;
    }
    /**
     * �õ���ԽX
     * @return int
     */
    public int getSpanX()
    {
        return spanX;
    }
    /**
     * ���ÿ�ԽY
     * @param spanY int
     */
    public void setSpanY(int spanY)
    {
        this.spanY = spanY;
    }
    /**
     * �õ���ԽY
     * @return int
     */
    public int getSpanY()
    {
        return spanY;
    }
    /**
     * �Ƿ�ϲ���Ԫ��
     * @return boolean
     */
    public boolean isSpan()
    {
        return getSpanX() > 0 || getSpanY() > 0;
    }
    /**
     * �����Ƿ���ʾ
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        if(this.visible == visible)
            return;
        this.visible = visible;
        this.setModify(true);
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
     * �������ϵ�TD
     * @param uniteTD ETD
     */
    public void setUniteTD(ETD uniteTD)
    {
        this.uniteTD = uniteTD;
    }
    /**
     * �õ����ϵ�TD
     * @return ETD
     */
    public ETD getUniteTD()
    {
        return uniteTD;
    }
    /**
     * �õ���һ������TD�����һ�����
     * @return EPanel
     */
    public EPanel getPreviousLinkTDLastPanel()
    {
        if(getPreviousLinkTD() == null)
            return null;
        if(getPreviousLinkTD().size() == 0)
            return getPreviousLinkTD().getPreviousLinkTDLastPanel();
        return getPreviousLinkTD().get(getPreviousLinkTD().size() - 1);
    }
    /**
     * �õ��ϲ���Ԫ������TD���
     * @param list TList
     * @param td ETD
     */
    public void getUniteTDHandles(TList list,ETD td)
    {
        ETD t = getHeadTD();
        while(t != null)
        {
            if(t.getUniteTD() == td)
                list.add(t);
            t = t.getNextLinkTD();
        }
    }
    /**
     * �����༭
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return getTR().isLockEdit();
    }
    /**
     * ��¡
     * @param tr ETR
     * @return ETD
     */
    public ETD clone(ETR tr)
    {
        ETD td = new ETD(tr);
        td.setVisible(isVisible());
        td.setX(getX());
        td.setY(getY());
        td.setWidth(getWidth());
        td.setHeight(getHeight());
        td.setAlignment(getAlignment());
        td.setSpanX(getSpanX());
        td.setSpanY(getSpanY());
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            td.add(panel.clone(td));
        }
        td.setModify(true);
        return td;
    }
}
