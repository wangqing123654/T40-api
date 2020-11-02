package com.dongyang.tui.text;

import com.dongyang.util.StringTool;
import com.dongyang.util.TList;
import com.dongyang.tui.DText;
import java.awt.Graphics;
import com.dongyang.tui.text.div.MVList;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.awt.Color;
import com.dongyang.tui.DCursor;
import com.dongyang.tui.text.image.GBlock;
import com.dongyang.tui.text.image.BlockPI;
import com.dongyang.ui.TPopupMenu;
import com.dongyang.tui.DMessageIO;
import com.dongyang.util.RunClass;
import com.dongyang.tui.text.image.GLine;
import java.util.List;
import com.dongyang.ui.TWindow;

public class EImage implements IBlock,EEvent,BlockPI,DMessageIO
{
    private static TList copyComponents = new TList();
    private TWindow propertyDialog;
    /**
     * ����
     */
    private EComponent parent;
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ѡ���б�
     */
    private TList selectedComponents;
    /**
     * ��ʾ
     */
    private boolean visible = true;
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
     * ����
     */
    private String name = "";
    /**
     * ͼ�������
     */
    private MVList mvList;
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
     * �ƶ��߿�
     */
    private int draggedIndex;
    private int oldMouseX,oldMouseY;
    private int omx,omy,omw,omh;
    private boolean showSelectBlock;
    /**
     * �϶���
     */
    private GBlock draggedBlock;
    /**
     * �����༭
     */
    private boolean isLockEdit;
    /**
     * ��ʾ�߿�
     */
    private boolean borderVisible = true;
    /**
     * �Ա���ʾ
     */
    private int sexControl = 0;
    /**
     * ������
     */
    public EImage()
    {
        components = new TList();
        selectedComponents = new TList();
    }
    /**
     * ������
     * @param panel EPanel
     */
    public EImage(EPanel panel)
    {
        this();
        setParent(panel);
    }
    /**
     * ��ʼ����Pic
     */
    public void initNew()
    {

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
     * �õ���ʾ������
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * �õ��¼�������
     * @return MEvent
     */
    public MEvent getEventManager()
    {
        return getPM().getEventManager();
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
        return IMAGE_TYPE;
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
     * ����
     */
    public void arrangement()
    {

    }
    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        EImage image = new EImage(panel);
        image.setVisible(isVisible());
        image.setName(getName());
        image.setWidth(getWidth());
        image.setHeight(getHeight());
        image.setLockEdit(isLockEdit());
        for(int i = 0;i < size();i++)
            image.add(get(i).clone(image));
        return image;
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
        return panel.getIndexString() + ",Image:" + findIndex();
    }
    public String toString()
    {
        return "<EImage " +
                "visible=" + isVisible() +
                ",index=" + getIndexString() +
                ">";
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
     * �����Լ���λ��
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
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
     * �������λ��
     * @param image EImage
     * @return int
     */
    public int findIndex(EImage image)
    {
        EPanel panel = getPanel();
        if(panel == null)
            return -1;
        return panel.indexOf(image);
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
     * �õ�ͼ�������
     * @return MV
     */
    public MVList getMVList()
    {
        return mvList;
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
        //getMVList().paintBackground(g,x,y,pageIndex);
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
        if(showSelectBlock)
        {
            paintSelectedBlock(g,x,y);
        }
        //getMVList().paintForeground(g,x,y,pageIndex);
    }
    public void paintSelectedBlock(Graphics g,int x,int y)
    {
        int x0 = (int)(omx * getZoom() / 75.0) + x;
        int y0 = (int)(omy * getZoom() / 75.0) + y;
        int w0 = (int)(omw * getZoom() / 75.0);
        int h0 = (int)(omh * getZoom() / 75.0);
        if(w0 < 0)
        {
            x0 += w0;
            w0 *= -1;
        }
        if(h0 < 0)
        {
            y0 += h0;
            h0 *= -1;
        }
        g.setColor(new Color(255,0,0));
        g.drawRect(x0,y0,w0,h0);
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
        //getMVList().printBackground(g,x,y,pageIndex);
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
        //getMVList().printForeground(g,x,y,pageIndex);
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
        if(isBorderVisible())
        {
            g.setColor(new Color(0, 0, 0));
            g.drawRect(x, y, width, height);
        }
        //���Ʊ���
        paintBackground(g,x,y,0);
        int count = size();
        for(int i = 0;i < count;i++)
        {
            GBlock block = get(i);
            int x1 = (int)(block.getX() * getZoom() / 75.0);
            int y1 = (int)(block.getY() * getZoom() / 75.0);
            int w1 = (int)(block.getWidth() * getZoom() / 75.0);
            int h1 = (int)(block.getHeight() * getZoom() / 75.0);
            //����
            block.paint(g,x + x1,y + y1,w1,h1);
        }
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
        if(isBorderVisible())
        {
            g.setColor(new Color(0, 0, 0));
            g.drawRect(x,y,getWidth(),getHeight());
        }
        //���Ʊ���
        printBackground(g,x,y,0);
        int count = size();
        for(int i = 0;i < count;i++)
        {
            GBlock block = get(i);
            int x1 = block.getX();
            int y1 = block.getY();
            int w1 = block.getWidth();
            int h1 = block.getHeight();
            //����
            block.print(g,x + x1,y + y1,w1,h1);
        }
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
     * 5 EImage
     */
    public int onMouseLeftPressed(int mouseX,int mouseY)
    {
        //���ý���
        setFocus();
        getEventManager().setDraggedImage(this);
        oldMouseX = getEventManager().getMouseX();
        oldMouseY = getEventManager().getMouseY();
        if(draggedIndex != 0)
        {
            switch(draggedIndex)
            {
            case 2:
                omy = getHeight();
                break;
            case 4:
                omx = getWidth();
                break;
            case 7:
                omx = getWidth();
                omy = getHeight();
                break;
            }
            return 5;
        }
        double zoom = 75.0 / getZoom();
        //�϶������
        if(getEventManager().isControlDown())
            draggedBlock = null;
        if(draggedBlock != null)
        {
            saveChildOldSize();
            int x = mouseX - (int)(draggedBlock.getX() * zoom);
            int y = mouseY - (int)(draggedBlock.getY() * zoom);
            draggedBlock.onMouseLeftPressed(x,y);
            return 5;
        }

        int x = (int)(mouseX * zoom);
        int y = (int)(mouseY * zoom);
        //�������
        if(createBlock(x,y))
            return 5;
        if(!isLockEdit())
        {
            //ѡ�����
            GBlock block = getBlock(x, y);
            //���ѡ��
            if (!getEventManager().isControlDown())
                clearSelected();
            if (block != null)
            {
                if (getEventManager().isControlDown())
                {
                    if (isSelectBlock(block))
                        removeSelected(block);
                    else
                        addSelected(block);
                } else
                    addSelected(block);
                update();
                return 5;
            }
            showSelectBlock = true;
        }
        omx = x;
        omy = y;
        omw = 0;
        omh = 0;
        return 5;
    }
    /**
     * �Ҽ�����
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        if(!isLockEdit())
        {
            double zoom = 75.0 / getZoom();
            int x = (int) (mouseX * zoom);
            int y = (int) (mouseY * zoom);
            //ѡ�����
            GBlock block = getBlock(x, y);
            //���ѡ��
            if (!getEventManager().isControlDown())
                clearSelected();
            if (block != null)
            {
                if (!isSelectBlock(block))
                    addSelected(block);
                update();
                int x1 = mouseX - (int) (block.getX() * getZoom() / 75.0 + 0.5);
                int y1 = mouseY - (int) (block.getY() * getZoom() / 75.0 + 0.5);
                block.onMouseRightPressed(x1, y1);
                return;
            }
        }
        String lock = isLockEdit()?"����༭,onUnLockEdit;|;����,propertyDialog":"ճ��,onPaste;|;�����༭,onLockEdit;|;����,propertyDialog";
        String syntax = lock;
        popupMenu(syntax);
    }
    /**
     * ����༭
     */
    public void onUnLockEdit()
    {
        setLockEdit(false);
    }
    /**
     * �����༭
     */
    public void onLockEdit()
    {
        setLockEdit(true);
        clearSelected();
    }
    /**
     * ճ��
     */
    public void onPaste()
    {
        exeAction(new EAction(EAction.PASTE));
    }
    /**
     * �����˵�
     * @param syntax String
     */
    public void popupMenu(String syntax)
    {
        if(syntax == null || syntax.length() == 0)
            return;
        TPopupMenu popup = TPopupMenu.createPopupMenu(syntax);
        popup.setMessageIO(this);
        //popup.changeLanguage(getLanguage());
        popup.show(getUI().getTCBase(),getUI().getMouseX(),getUI().getMouseY());
    }
    /**
     * ����
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * ���̧��
     */
    public void onMouseLeftReleased()
    {
        if(showSelectBlock)
        {
            checkBlockSelected();
            showSelectBlock = false;
        }
        update();
    }
    public void checkBlockSelected()
    {
        int x0 = omx;
        int y0 = omy;
        int w0 = omw;
        int h0 = omh;
        if(w0 < 0)
        {
            x0 += w0;
            w0 *= -1;
        }
        if(h0 < 0)
        {
            y0 += h0;
            h0 *= -1;
        }
        for(int i = size() - 1;i >= 0;i--)
        {
            GBlock block = get(i);
            if(block.getX() < x0 || block.getWidth() + block.getX() > x0 + w0)
                continue;
            if(block.getY() < y0 || block.getHeight() + block.getY() > y0 + h0)
                continue;
            addSelected(block);
        }
    }
    /**
     * ����ѡ�е����
     * @param x int
     * @param y int
     * @return GBlock
     */
    public GBlock getBlock(int x,int y)
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            GBlock block = get(i);
            if(block.isSelectCheck(x,y))
                return block;
        }
        return null;
    }
    /**
     * ����ѡ�е����
     * @param x int
     * @param y int
     * @return GBlock
     */
    public GBlock getBlockEnabled(int x,int y)
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            GBlock block = get(i);
            if(!block.isEnabled())
                continue;
            if(block.isSelectCheck(x,y))
                return block;
        }
        return null;
    }
    /**
     * ������
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean createBlock(int x,int y)
    {
        switch(getFocusManager().getInsertImageAction())
        {
        case 1:
            GBlock block = new GBlock();
            block.setParent(this);
            block.setX(x);
            block.setY(y);
            block.setWidth(30);
            block.setHeight(30);
            add(block);
            setSelected(block);
            update();
            getFocusManager().setInsertImageAction(0);
            return true;
        case 2:
            GLine line = new GLine();
            line.setParent(this);
            line.setX(x);
            line.setY(y);
            line.setWidth(30);
            line.setHeight(30);
            add(line);
            setSelected(line);
            update();
            getFocusManager().setInsertImageAction(0);
            return true;
        }
        return false;
    }
    private void moveSize(int x,int y)
    {
        switch(draggedIndex)
        {
        case 2:
            setHeight(omy + y);
            break;
        case 4:
            int w = omx + x;
            if(w < 1)
                w = 1;
            if(w > getPanel().getWidth() - getX())
                w = getPanel().getWidth() - getX();
            setWidth(w);
            break;
        case 7:
            w = omx + x;
            if(w < 1)
                w = 1;
            if(w > getPanel().getWidth() - getX())
                w = getPanel().getWidth() - getX();
            setWidth(w);
            setHeight(omy + y);
            break;
        }
    }
    /**
     * ����϶�
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseDragged(int mouseX,int mouseY)
    {
        double zoom = 75.0 / getZoom();
        int x = (int)((mouseX - oldMouseX) * zoom);
        int y = (int)((mouseY - oldMouseY) * zoom);
        moveSize(x,y);
        //�϶������
        if(draggedBlock != null)
        {
            draggedBlock.onMouseDragged(x,y);
        }
        setModify(true);
        omw = x;
        omh = y;
        update();
    }
    /**
     * �涯����
     * @param index int
     * @param x double
     * @param y double
     * @param block GBlock
     */
    public void onMouseDraggedOther(int index,double x,double y,GBlock block)
    {
        for(int i = 0;i < sizeSelected();i++)
        {
            GBlock b = getSelected(i);
            if(b == block)
                continue;
            b.onMouseDraggedOther(index,x,y);
        }
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
     * �õ�����������
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInComponentIndexXY(int mouseX,int mouseY)
    {
        int len = 6;
        int el = 4;
        double zoom = getZoom() / 75.0;
        int width = (int)(getWidth() * zoom);
        int height = (int)(getHeight() * zoom);
        //if(mouseX < len && mouseY < len)
        //    return 5;
        //if(mouseX > width - len && mouseY < len)
        //    return 6;
        if(mouseX > width - len && mouseY > height - len)
            return 7;
        //if(mouseX < len && mouseY > width - len)
        //    return 8;
        //if(mouseY < el)
        //    return 1;
        if(mouseY > height - el)
            return 2;
        //if(mouseX < el)
        //    return 3;
        if(mouseX > width - el)
            return 4;
        return 0;
    }
    /**
     * ����ƶ�
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        draggedBlock = null;
        if(!isLockEdit())
        {
            draggedIndex = getMouseInComponentIndexXY(mouseX, mouseY);
            if (draggedIndex > 0)
            {
                setCursor(draggedIndex);
                return true;
            }
            double zoom = getZoom() / 75.0;
            for (int i = 0; i < sizeSelected(); i++)
            {
                GBlock block = getSelected(i);
                int x = (int) (block.getX() * zoom);
                int y = (int) (block.getY() * zoom);
                if (block.onMouseMoved(mouseX - x, mouseY - y))
                {
                    draggedBlock = block;
                    return true;
                }
            }
            getUI().setCursor(DCursor.DEFAULT_CURSOR);
            return true;
        }
        return true;
    }
    public void setCursor(int index)
    {
        switch(index)
        {
        //case 1:
        case 2:
            getUI().setCursor(DCursor.N_RESIZE_CURSOR);
            return;
        //case 3:
        case 4:
            getUI().setCursor(DCursor.E_RESIZE_CURSOR);
            return;
        //case 5:
        case 7:
            getUI().setCursor(DCursor.NW_RESIZE_CURSOR);
            return;
        //case 6:
        //case 8:
        //    getUI().setCursor(DCursor.NE_RESIZE_CURSOR);
        //    return;
        }
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
     * @param com EImage
     */
    public void setFocus(EImage com)
    {
        getFocusManager().setFocusImage(com);
    }
    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        if(!isLockEdit())
            return true;
        double zoom = 75.0 / getZoom();
        int x = (int)(mouseX * zoom);
        int y = (int)(mouseY * zoom);
        //ѡ�����
        GBlock block = getBlockEnabled(x, y);
        if(block != null)
            block.onDoubleClickedS(mouseX - x, mouseY - y);
        return true;
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
        panel.setModify(true);
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
        //�����ڲ�����
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            GBlock block = get(i);
            s.writeInt(block.getTypeID());
            block.writeObject(s);
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
            switch(s.readInt())
            {
            case 0:
                GBlock block = new GBlock();
                block.setParent(this);
                block.readObject(s);
                add(block);
                break;
            case 1:
                GLine line = new GLine();
                line.setParent(this);
                line.readObject(s);
                add(line);
                break;
            }
        }
    }
    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeBoolean(1,isVisible(),true);
        s.writeString(2,getName(),"");
        s.writeInt(3,getWidth(),0);
        s.writeInt(4,getHeight(),0);
        s.writeBoolean(5,isLockEdit(),false);
        s.writeBoolean(6,isBorderVisible(),true);
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
            setVisible(s.readBoolean());
            return true;
        case 2:
            setName(s.readString());
            return true;
        case 3:
            setWidth(s.readInt());
            return true;
        case 4:
            setHeight(s.readInt());
            return true;
        case 5:
            setLockEdit(s.readBoolean());
            return true;
        case 6:
            setBorderVisible(s.readBoolean());
            return true;
        }
        return false;
    }
    /**
     * ���ӳ�Ա
     * @param com GBlock
     */
    public void add(GBlock com)
    {
        if(com == null)
            return;
        components.add(com);
        //com.setParent(this);
    }
    /**
     * ���ӳ�Ա
     * @param index int
     * @param com GBlock
     */
    public void add(int index,GBlock com)
    {
        if(com == null)
            return;
        components.add(index,com);
        //com.setParent(this);
    }
    /**
     * ����λ��
     * @param com GBlock
     * @return int
     */
    public int indexOf(GBlock com)
    {
        if(com == null)
            return -1;
        return components.indexOf(com);
    }
    /**
     * ɾ����Ա
     * @param com GBlock
     */
    public void remove(GBlock com)
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
     * @return GBlock
     */
    public GBlock get(int index)
    {
        if(index < 0 || index >= size())
            return null;
        return (GBlock)components.get(index);
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
     * �õ�ѡ�г�Ա����
     * @return int
     */
    public int sizeSelected()
    {
        return selectedComponents.size();
    }
    /**
     * ����ѡ�г���
     * @param block GBlock
     */
    public void addSelected(GBlock block)
    {
        if(block == null)
            return;
        selectedComponents.add(block);
        block.setEditSelect(true);
    }
    /**
     * ѡ�п�
     * @param block GBlock
     */
    public void setSelected(GBlock block)
    {
        clearSelected();
        addSelected(block);
    }
    /**
     * ����Ƿ�ѡ��
     * @param block GBlock
     * @return boolean
     */
    public boolean isSelectBlock(GBlock block)
    {
        for(int i = 0;i < sizeSelected();i++)
            if(getSelected(i) == block)
                return true;
        return false;
    }
    /**
     * ȡ��ѡ��
     * @param block GBlock
     */
    public void removeSelected(GBlock block)
    {
        selectedComponents.remove(block);
        block.setEditSelect(false);
    }
    /**
     * ȡ��ѡ��
     * @param index int
     */
    public void removeSelected(int index)
    {
        GBlock block = getSelected(index);
        if(block == null)
            return;
        block.setEditSelect(false);
        selectedComponents.remove(index);
    }
    /**
     * �õ�ѡ�г�Ա
     * @param index int
     * @return GBlock
     */
    public GBlock getSelected(int index)
    {
        if(index < 0 || index >= sizeSelected())
            return null;
        return (GBlock)selectedComponents.get(index);
    }
    /**
     * ���ѡ��
     */
    public void clearSelected()
    {
        for(int i = 0;i < sizeSelected();i++)
            getSelected(i).setEditSelect(false);
        selectedComponents.removeAll();
    }
    /**
     * ɾ��ѡ��
     */
    public void deleteSelected()
    {
        for(int i = 0;i < sizeSelected();i++)
            remove(getSelected(i));
        selectedComponents.removeAll();
    }
    public void saveChildOldSize()
    {
        for(int i = 0;i < sizeSelected();i++)
            getSelected(i).saveOldSize();
    }
    public void onMoveKey(int index)
    {
        for(int i = 0;i < sizeSelected();i++)
        {
            GBlock b = getSelected(i);
            switch(index)
            {
            case 1:
                b.setY(b.getY() - 1);
                break;
            case 2:
                b.setY(b.getY() + 1);
                break;
            case 3:
                b.setX(b.getX() - 1);
                break;
            case 4:
                b.setX(b.getX() + 1);
                break;
            case 5:
                b.setHeight(b.getHeight() - 1);
                break;
            case 6:
                b.setHeight(b.getHeight() + 1);
                break;
            case 7:
                b.setWidth(b.getWidth() - 1);
                break;
            case 8:
                b.setWidth(b.getWidth() + 1);
                break;
            }
        }
        update();
    }
    /**
     * ������ߴ�
     * @param index int
     */
    public void onSizeBlockMenu(int index)
    {
        if(sizeSelected() < 2)
            return;
        GBlock block = getSelected(0);
        for(int i = 1;i < sizeSelected();i++)
        {
            GBlock b = getSelected(i);
            switch(index)
            {
            case 1:
                b.setY(block.getY());
                break;
            case 2:
                b.setY(block.getY() + block.getHeight() - b.getHeight());
                break;
            case 3:
                b.setX(block.getX());
                break;
            case 4:
                b.setX(block.getX() + block.getWidth() - b.getWidth());
                break;
            case 5:
                b.setWidth(block.getWidth());
                break;
            case 6:
                b.setHeight(block.getHeight());
                break;
            }
        }
        update();
    }
    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        switch(action.getType())
        {
        case EAction.COPY:
            copyComponents.removeAll();
            for(int i = 0;i < sizeSelected();i++)
                copyComponents.add(getSelected(i).copyObject());
            break;
        case EAction.PASTE:
            clearSelected();
            for(int i = 0;i < copyComponents.size();i++)
            {
                GBlock block = ((GBlock)copyComponents.get(i)).copyObject();
                block.setParent(this);
                add(block);
                addSelected(block);
            }
            update();
            break;
        case EAction.CUT:
            copyComponents.removeAll();
            for(int i = 0;i < sizeSelected();i++)
                copyComponents.add(getSelected(i).copyObject());
            deleteSelected();
            update();
            break;
        case EAction.DELETE:
            deleteSelected();
            update();
            break;
        }
    }
    /**
     * ����������������
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
    }
    /**
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message, "=");
        //����������������
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * ������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm)
    {
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        return null;
    }
    /**
     * ���������༭
     * @param isLockEdit boolean
     */
    public void setLockEdit(boolean isLockEdit)
    {
        this.isLockEdit = isLockEdit;
    }
    /**
     * �Ƿ������༭
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return isLockEdit;
    }
    /**
     * ���ԶԻ���
     */
    public void propertyDialog()
    {
        openProperty("%ROOT%\\config\\database\\BlockImageEdit.x",propertyDialog);
    }
    /**
     * �����ԶԻ���
     * @param dialogName String
     * @param window TWindow
     */
    public void openProperty(String dialogName,TWindow window)
    {
        if(dialogName == null || dialogName.length() == 0)
            return;
        if(window == null || window.isClose())
        {
            window = (TWindow) getUI().openWindow(dialogName, this, true);
            window.setVisible(true);
        }
        else
            window.setVisible(true);
    }
    /**
     * �����Ƿ���ʾ�߿�
     * @param borderVisible boolean
     */
    public void setBorderVisible(boolean borderVisible)
    {
        this.borderVisible = borderVisible;
    }
    /**
     * �Ƿ���ʾ�߿�
     * @return boolean
     */
    public boolean isBorderVisible()
    {
        return borderVisible;
    }
    /**
     * �����Ա�����
     * @param sexControl int
     */
    public void setSexControl(int sexControl)
    {
        this.sexControl = sexControl;
    }
    /**
     * �õ��Ա�����
     * @return int
     */
    public int getSexControl()
    {
        return sexControl;
    }
}
