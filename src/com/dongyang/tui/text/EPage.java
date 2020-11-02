package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.tui.DInsets;
import com.dongyang.util.StringTool;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.DCursor;
import com.dongyang.tui.DText;
import java.awt.Rectangle;
import com.dongyang.tui.DPoint;
import java.util.List;
import java.awt.Font;

/**
 *
 * <p>Title: ҳ</p>
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
public class EPage implements EComponent
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
     * �����������
     */
    private int baseWidth = 0;
    /**
     * ���������߶�
     */
    private int baseHeight = 0;
    /**
     * �޸�״̬
     */
    private boolean modify;
    /**
     * ���óߴ����
     */
    private ResetPanel resetPanel;
    /**
     * ��ʼ�к�
     */
    private int rowStartID;
    /**
     * �����к�
     */
    private int rowEndID;
    /**
     * ������
     */
    public EPage()
    {
        components = new TList();
        //�����ߴ��������
        setResetPanel(new ResetPanel(this));
    }
    /**
     * ������
     * @param pageManager MPage
     */
    public EPage(MPage pageManager)
    {
        this();
        setParent(pageManager);
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getPageManager().getPM();
    }
    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager()
    {
        return (MPage)getParent();
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
     * �õ��ַ����ݹ�����
     * @return MString
     */
    public MString getStringManager()
    {
        return getPM().getStringManager();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }
    /**
     * �õ��ڲ��ߴ�
     * @return DInsets
     */
    public DInsets getInsets()
    {
        return getPageManager().getInsets();
    }
    /**
     * �õ��༭���ڲ��ߴ�
     * @return DInsets
     */
    public DInsets getEditInsets()
    {
        return getPageManager().getEditInsets();
    }
    /**
     * �õ��ڲ�X
     * @return int
     */
    public int getInsetsX()
    {
        return getInsets().left + 1 + getEditInsets().left;
    }
    /**
     * �õ��ڲ�Y
     * @return int
     */
    public int getInsetsY()
    {
        return getInsets().top + 1 + getEditInsets().top;
    }
    /**
     * �õ��ڲ����
     * @return int
     */
    public int getInsetsWidth()
    {
        DInsets insets = getInsets();
        return getWidth() - insets.left - insets.right - 2 - getEditInsets().left - getEditInsets().right;
    }
    /**
     * �õ��ڲ��߶�
     * @return int
     */
    public int getInsetsHeight()
    {
        DInsets insets = getInsets();
        return getHeight() - insets.top - insets.bottom - 2 - getEditInsets().top - getEditInsets().bottom;
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
        if(size() == 0)
            getPageManager().remove(this);
    }
    /**
     * ɾ����Ա
     * @param index int
     */
    public void remove(int index)
    {
        components.remove(index);
        if(size() == 0)
            getPageManager().remove(this);
    }
    /**
     * ɾ��ȫ����Ա
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
     * @param panel EPanel
     * @return int
     */
    public int indexOf(EPanel panel)
    {
        if(panel == null)
            return -1;
        return components.indexOf(panel);
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
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        if(getViewManager().getViewStyle() == 0)
            getPageManager().setWidth(width);
    }

    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return getPageManager().getWidth();
    }

    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        if(getViewManager().getViewStyle() == 0)
            getPageManager().setHeight(height);
    }

    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return getPageManager().getHeight();
    }
    /**
     * ���û����������
     * @param baseHeight int
     */
    public void setBaseWidth(int baseHeight)
    {
        this.baseWidth = baseWidth;
    }
    /**
     * �õ������������
     * @return int
     */
    public int getBaseWidth()
    {
        if(baseWidth != 0)
            return baseWidth;
        DInsets insets = getInsets();
        return getWidth() - insets.left - insets.right - 2;
    }
    /**
     * ���û��������߶�
     * @param baseHeight int
     */
    public void setBaseHeight(int baseHeight)
    {
        this.baseHeight = baseHeight;
    }
    /**
     * �õ����������߶�
     * @return int
     */
    public int getBaseHeight()
    {
        if(baseHeight != 0)
            return baseHeight;
        DInsets insets = getInsets();
        return getHeight() - insets.top - insets.bottom - 2;
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
     * ���Ʊ���
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void paintBakViewBase(Graphics g,int x,int y)
    {
        if(getViewManager().getViewStyle() != 0)
            return;
        g.setColor(new Color(244, 244, 244));
        g.fillRect(x, y, (int)(getInsets().left * getZoom() / 75.0) - 1, (int)(getHeight() * getZoom() / 75.0) - 1);
    }
    /**
     * �����к�
     * @param g Graphics
     * @param x int
     * @param y int
     * @param index int
     */
    public void paintBakViewBaseHH(Graphics g,int x,int y,int index)
    {
        if(getViewManager().getViewStyle() != 0)
            return;
        g.setColor(new Color(153, 153, 204));
        g.setFont(new Font("����",0,12));
        g.drawString("" +index,x + 3,y - 2);
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
        if(getViewManager().isPageDebug())
        {
            g.setColor(new Color(255, 0, 255));
            g.drawRect(x, y, (int)(width * getZoom() / 75.0) - 1, (int)(height * getZoom() / 75.0) - 1);
        }
        //���Ʊ���
        paintBakViewBase(g,x,y);

        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            Rectangle r = g.getClipBounds();
            int x1 = (int)(panel.getX() * getZoom() / 75.0);
            int y1 = (int)(panel.getY() * getZoom() / 75.0);
            int w1 = (int)(panel.getWidth() * getZoom() / 75.0);
            int h1 = (int)(panel.getHeight() * getZoom() / 75.0);
            //�Ƿ��ڻ�ͼ��Χ��
            if(r.getX() > x + x1 + w1 ||
               r.getY() > y + y1 + h1 ||
               r.getX() + r.getWidth() < x + x1 ||
               r.getY() + r.getHeight() < y + y1)
                continue;
            //�����к�
            paintBakViewBaseHH(g,0,y + y1 + h1,i + 1);
            //�������
            panel.paint(g,x + x1,y + y1,w1,h1);
        }
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
     * �õ�����������
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInPanelIndex(int mouseX,int mouseY)
    {
        if(size() == 0)
            return -1;
        double zoom = getZoom() / 75.0;
        for(int i = 0;i < size();i++)
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
     * �õ�����������
     * @param mouseY int
     * @return int
     */
    public int getMouseInPanelIndex(int mouseY)
    {
        double zoom = getZoom() / 75.0;
        if(size() == 0)
            return -1;
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            int h = (int)((panel.getY() + panel.getHeight()) * zoom + 0.5);
            if(mouseY < h)
                return i;
        }
        return size() - 1;
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
        //����������
        int panelIndex = getMouseInPanelIndex(mouseY);
        if(panelIndex == -1)
            return -1;
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
        int panelIndex = getMouseInPanelIndex(mouseY);
        if(panelIndex == -1)
            return;
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
        int panelIndex = getMouseInPanelIndex(mouseY);
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
     * ҳ���ȱ仯�¼�
     * @param value int
     */
    public void onChanglePagehWidth(int value)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.setWidth(panel.getWidth() + value);
        }
    }
    /**
     * ҳ��߶ȱ仯�¼�
     * @param value int
     */
    public void onChanglePagehHeight(int value)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.setModify(true);
        }
    }
    /**
     * �������λ��
     * @param page EPage
     * @return int
     */
    public int findIndex(EPage page)
    {
        return getPageManager().indexOf(page);
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
     * �õ���ĻY
     * @return int
     */
    public int getScreenY()
    {
        //�õ�ҳλ��
        int pageIndex = findIndex();
        //ҳ��߶�
        int pageHeight = getHeight();
        return pageIndex * (pageHeight + getViewManager().getPageBorderSize()) + getViewManager().getPageBorderSize();
    }
    /**
     * �õ���Ļ�ڲ�Y
     * @return int
     */
    public int getScreenInsetsY()
    {
        return getScreenY();
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
        return null;
    }
    /**
     * �õ���һ��ҳ
     * @return EPage
     */
    public EPage getPreviousPage()
    {
        return getPreviousPage(this);
    }
    /**
     * �õ���һ��ҳ
     * @return EPage
     */
    public EPage getNextPage()
    {
        return getNextPage(this);
    }
    /**
     * �õ���һ��ҳ
     * @param page EPage
     * @return EPage
     */
    public EPage getPreviousPage(EPage page)
    {
        if(getPageManager() == null)
            return null;
        int index = findIndex(page);
        if(index < 1)
            return null;
        return getPageManager().get(index - 1);
    }
    /**
     * �õ���һ��ҳ
     * @param page EPage
     * @return EPage
     */
    public EPage getNextPage(EPage page)
    {
        if(getPageManager() == null)
            return null;
        int index = findIndex(page);
        if(index >= getPageManager().size() - 1)
            return null;
        return getPageManager().get(index + 1);
    }
    /**
     * �õ��������
     * @return EPanel
     */
    public EPanel getLastPanel()
    {
        if(size() == 0)
            return null;
        return get(size() - 1);
    }
    /**
     * �õ���һ�����
     * @return EPanel
     */
    public EPanel getFirstPanel()
    {
        if(size() == 0)
            return null;
        return get(0);
    }
    /**
     * �õ������ı�
     * @return EText
     */
    public EText getLastText()
    {
        if(size() == 0)
            return null;
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
     * ���ҵ�һ��Text
     * @return EText
     */
    public EText getFirstText()
    {
        if(size() == 0)
            return null;
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
     * �õ���һҳ�������
     * @return EPanel
     */
    public EPanel getPreviousPageLastPanel()
    {
        EPage page = getPreviousPage();
        if(page == null)
            return null;
        return page.getLastPanel();
    }
    /**
     * �õ���һҳ��һ�������
     * @return EPanel
     */
    public EPanel getNextPageFirstPanel()
    {
        EPage page = getNextPage();
        if(page == null)
            return null;
        return page.getFirstPanel();
    }
    /**
     * �õ���Ч�߶�
     * @param panel EPanel
     * @return int
     */
    public int getUserHeight(EPanel panel)
    {
        if(panel == null)
            return 0;
        return getHeight() - getInsets().bottom - getEditInsets().bottom - panel.getY();
    }
    /**
     * ������һҳ�������
     * @return EPanel
     */
    public EPanel createNextPageLinkPanel()
    {
        if(getPageManager() == null)
            return null;
        EPage page = getNextPage();
        if(page == null)
            page = getPageManager().newPage();
        return page.newPanel(0);
    }
    /**
     * �õ���һ��ҳ������Text
     * @return EText
     */
    public EText getPreviousPageLastText()
    {
        EPage page = getPreviousPage();
        while(page != null)
        {
            EText text = page.getLastText();
            if(text != null)
                return text;
            page = getPreviousPage(page);
        }
        return null;
    }
    /**
     * �õ���һ��ҳ���һ����Text
     * @return EText
     */
    public EText getNextPageFirstText()
    {
        EPage page = getNextPage();
        while(page != null)
        {
            EText text = page.getFirstText();
            if(text != null)
                return text;
            page = getNextPage(page);
        }
        return null;
    }
    /**
     * �ƶ�������嵽��һ��ҳ��
     * @param index int
     * @param panel EPanel
     */
    public void movePanelToNextPage(int index,EPanel panel)
    {
        if(index >= size())
            return;
        if(panel == null)
            return;
        EPage page = panel.getPage();
        if(page == null)
            return;
        int panelIndex = panel.findIndex();
        if(panelIndex == -1)
            return;
        for(int i = index;i < size();i++)
        {
            EPanel p = get(i);
            if(p == null)
                continue;
            panelIndex ++;
            page.add(panelIndex,p);
        }
        for(int i = size() - 1;i >= index;i--)
            remove(i);
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
        if(!isModify())
            return;
        //����
        getResetPanel().clear();
        //���óߴ緶Χ
        getResetPanel().setRectangle(getInsetsX(),
                                     getInsetsY(),
                                     getInsetsWidth(),
                                     getInsetsHeight());
        //�����ߴ�
        getResetPanel().reset();
        setModify(false);
    }
    /**
     * �����ߴ�(�ϳ�)
     * @param index int
     */
    public void reset(int index)
    {
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
        EPage page = p2.getPage();
        if(page == null)
            return;
        int indexNext = page.indexOf(p2);
        if(indexNext == -1)
            return;
        index ++;
        while(index < size())
        {
            EPanel p = get(index);
            indexNext ++;
            page.add(indexNext,p);
            remove(index);
        }
    }
    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString()
    {
        return "Page:" + findIndex();
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
        System.out.println(StringTool.fill(" ",i) + this);
        for(int j = 0;j < size();j++)
        {
            EPanel panel = get(j);
            panel.debugModify(i + 2);
        }
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
        s.WO("<EPage>",c);
        for(int i = 0;i < size();i ++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            panel.writeObjectDebug(s,c + 1);
        }
        s.WO("</EPage>",c);
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

        //�������
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
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
        while(id > 0)
        {
            id = s.readShort();
        }
        //��ȡ���
        int count = s.readInt();
        //System.out.println("==count=="+count);
        for(int i = 0;i < count;i++)
        {
            EPanel panel = newPanel();
            panel.readObject(s);
        }
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
    }
    /**
     * �õ������ı��б�
     * @param list TList
     * @param endText EText
     */
    public void getFindTextList(TList list,EText endText)
    {
        EPanel next = get(0);
        next.getFindTextList(list,endText);
    }
    /**
     * �õ���һ��ҳ�������б�
     * @param list TList
     * @param endText EText
     */
    public void getFindNextTextList(TList list,EText endText)
    {
        EPage next = getNextPage();
        while(next != null && next.size() == 0)
            next = getNextPage(next);
        if(next == null)
            return;
        next.getFindTextList(list, endText);
    }
    /**
     * ��ӡ
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            if(panel == null)
                continue;
            //�������
            panel.print(g,panel.getX(),panel.getY());
        }
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
     * ����ƶ�
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        int panelIndex = getMouseInPanelIndex(mouseX,mouseY);
        if(panelIndex == -1)
        {
            getUI().setCursor(DCursor.TEXT_CURSOR);
            return true;
        }
        EPanel panel = get(panelIndex);
        int x = mouseX - (int)(panel.getX() * getZoom() / 75.0 + 0.5);
        int y = mouseY - (int)(panel.getY() * getZoom() / 75.0 + 0.5);
        if(panel.onMouseMoved(x,y))
            return true;
        getUI().setCursor(DCursor.TEXT_CURSOR);
        return true;
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
     * �õ���Ļ����
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        DPoint point = getUI().getScreenPoint();
        point.setX(point.getX() + getViewManager().getViewX() + 1);
        point.setY(point.getY() + getViewManager().getViewY(findIndex()) + 1);
        return point;
    }
    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        //System.out.println("EPage action=="+action.getType());
        for(int i = 0;i < size();i++)
            get(i).resetData(action);
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
            next = getNextPanel(next);
        }
        EPage page = getNextPage();
        while(page != null)
        {
            if(page.setFocus())
                return true;
            page = page.getNextPage();
        }
        return false;
    }
    /**
     * ���������ƶ�
     * @param panel EPanel
     * @return boolean
     */
    public boolean onFocusToLeft(EPanel panel)
    {
        EPanel prev = getPreviousPanel(panel);
        while(prev != null)
        {
            if (prev.setFocusLast())
                return true;
            prev = getNextPanel(prev);
        }
        EPage page = getPreviousPage();
        while(page != null)
        {
            if(page.setFocusLast())
                return true;
            page = page.getPreviousPage();
        }
        return false;
    }
    /**
     * �õ�����
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
        return false;
    }
    /**
     * �õ�����
     * @return boolean
     */
    public boolean setFocusLast()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EPanel panel = get(i);
            if(panel.setFocusLast())
                return true;
        }
        return false;
    }
    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setPreviousFocus()
    {
        int index = findIndex() - 1;
        if(index >= 0)
            return getPageManager().get(index).setFocusLast();
        return false;
    }
    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setNextFocus()
    {
        int index = findIndex() + 1;
        if(index < getPageManager().size())
            return getPageManager().get(index).setFocus();
        return false;
    }
    public String toString()
    {
        return "<EPage size=" + size() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",isModify=" + isModify() +
                ",index=" + getIndexString() + ">";
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
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type)
    {
        for(int i = 0;i < size();i++)
            get(i).filterObject(list,name,type);
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
            String value = get(i).findGroupValue(group);
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
     * @param id RowID
     */
    public void resetRowID(RowID id)
    {
        setRowStartID(id.getStart());
        for(int i = 0;i < size();i++)
        {
            EPanel panel = get(i);
            panel.resetRowID(id);
        }
        setRowEndID(id.getEnd());
    }
    /**
     * ���ÿ�ʼ�к�
     * @param rowStartID int
     */
    public void setRowStartID(int rowStartID)
    {
        this.rowStartID = rowStartID;
    }
    /**
     * �õ���ʼ�к�
     * @return int
     */
    public int getRowStartID()
    {
        return rowStartID;
    }
    /**
     * ���ý����к�
     * @param rowEndID int
     */
    public void setRowEndID(int rowEndID)
    {
        this.rowEndID = rowEndID;
    }
    /**
     * �õ������к�
     * @return int
     */
    public int getRowEndID()
    {
        return rowEndID;
    }
}
