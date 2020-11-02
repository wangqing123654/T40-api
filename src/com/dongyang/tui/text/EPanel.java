package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import com.dongyang.ui.util.TDrawTool;
import com.dongyang.tui.DPoint;
import com.dongyang.util.StringTool;
import java.util.List;
import java.awt.Font;
import com.dongyang.tui.DFont;
import java.awt.FontMetrics;

/**
 *
 * <p>Title: ���</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author not attributable
 * @version lzk 2009.3.11
 */
public class EPanel extends EPanelStore
        implements EEvent,IExeAction
{
    /**
     * ���óߴ����
     */
    private ResetBlock resetBlock;
    /**
     * �Ƿ�ѡ��
     */
    private boolean isSelected;
    /**
     * ѡ�лس���
     */
    private CSelectPanelEnterModel selectedEnterModel;
    /**
     * ѡ�ж���
     */
    private CSelectPanelModel selectedModel;
    /**
     * ������
     */
    public EPanel()
    {
        //�����ߴ��������
        setResetBlock(new ResetBlock(this));
    }
    /**
     * ������
     * @param page EPage
     */
    public EPanel(EPage page)
    {
        this();
        setParent(page);
        //��ʼ���ַ�����
        initString();
        setModify(true);
    }
    /**
     * ������
     * @param td ETD
     */
    public EPanel(ETD td)
    {
        this();
        setParent(td);
        //��ʼ���ַ�����
        initString();
    }
    /**
     * ��ʼ���ַ�����
     */
    public void initString()
    {
        DString dString = new DString();
        setDString(dString);
        getStringManager().add(dString);
    }
    /**
     * ��������
     * @param g Graphics
     * @return boolean true �н��� false û�н���
     */
    public boolean inRectangle(Graphics g)
    {
        if(g == null)
            return false;
        return inRectangle(g.getClipBounds());
    }
    /**
     * ��������
     * @param r Rectangle
     * @return boolean true �н��� false û�н���
     */
    public boolean inRectangle(Rectangle r)
    {
        if(r.getX() > getX() + getWidth() ||
           r.getY() > getY() + getHeight() ||
           r.getX() + r.getWidth() < getX() ||
           r.getY() + r.getHeight() < getY())
            return false;
        return true;
    }
    /**
     * �Ƿ�����״̬
     * @return boolean
     */
    public boolean isPreview()
    {
        return getViewManager().isPreview();
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
        if(!checkSexControl())
            return;
        if(getViewManager().isPanelDebug())
        {
            g.setColor(new Color(255, 0, 0));
            g.drawRect(x, y, width, height);
        }
        //����Ԫ��
        for(int i = 0;i < size();i++)
        {
            EComponent component = get(i);
            if(component == null)
                continue;
            if(component instanceof EText)
            {
                EText text = (EText)component;
                if (text.isPreview() && text.isDeleteFlg())
                    continue;
            }
            Rectangle r = g.getClipBounds();
            int x1 = (int)(component.getX() * getZoom() / 75.0);
            int y1 = (int)(component.getY() * getZoom() / 75.0);
            int w1 = (int)(component.getWidth() * getZoom() / 75.0);
            int h1 = (int)(component.getHeight() * getZoom() / 75.0);
            //�Ƿ��ڻ�ͼ��Χ��
            if(r.getX() > x + x1 + w1 ||
               r.getY() > y + y1 + h1 ||
               r.getX() + r.getWidth() < x + x1 ||
               r.getY() + r.getHeight() < y + y1)
                continue;
            //����
            component.paint(g,x + x1,y + y1,w1,h1);
            //�����к�
            if(getViewManager().isShowRowID() && parentIsPage())
            {
                if (component instanceof EText)
                {
                    EText text = (EText) component;
                    int position = text.getPosition();
                    if (position == 1 || position == 3)
                    {
                        String s = "" + text.getRowID() + "    ";
                        double zoom = text.getZoom() / 75.0;
                        Font font = new Font("����", 0, (int) (11 * zoom + 0.5));
                        FontMetrics metrics = DFont.getFontMetrics(font);
                        int x0 = metrics.stringWidth(s);
                        g.setFont(font);
                        g.setColor(new Color(0, 0, 0));
                        g.drawString(s, x - x0, y + y1 + h1);
                    }
                }
            }
        }
        //������һ���������
        if(hasNextPageLinkPanel() || hasNextLinkPanel() || getHeight() == 0)
            return;
        //���ƻس���
        if(size() == 0)
            return;
        if(getViewManager().getViewStyle() == 0)
            return;
        EComponent component = get(size() - 1);
        if(!isSelectedDraw() && isSelectedEnterModel() && component instanceof EText)
        {
            EText text = (EText)component;
            g.setColor(new Color(0,0,0));
            g.fillRect(x + (int)((text.getX() + text.getWidth()) * getZoom() / 75.0 + 0.5),
                       y + (int)((text.getY() + text.getHeight()) * getZoom() / 75.0 + 0.5) - 12,
                       8,12);
        }
        //��������״̬���Ҳ��Ǳ�����
        if(!isPreview() && getType() != 1)
        {
            int x1 = component.getX() + component.getWidth();
            int y1 = component.getY() + component.getHeight();
            x1 = (int)(x1 * getZoom() / 75.0 + 0.5);
            y1 = (int)(y1 * getZoom() / 75.0 + 0.5);
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
        if(!checkSexControl())
            return;
        //����Ԫ��
        for(int i = 0;i < size();i++)
        {
            EComponent component = get(i);
            if(component == null)
                continue;
            if(component instanceof EText)
            {
                EText text = (EText)component;
                if (text.isPreview() && text.isDeleteFlg())
                    continue;
                //����ж�
                if(!getPageManager().isXDPrint(text.getRowID()))
                    continue;

                text.print(g, x + component.getX(),
                           y + component.getY());
            }
            if(component instanceof ETable)
                ((ETable)component).print(g, x + component.getX(), y + component.getY());
            if(component instanceof EPic)
                ((EPic)component).print(g, x + component.getX(), y + component.getY());
            if(component instanceof EImage)
                ((EImage)component).print(g, x + component.getX(), y + component.getY());
        }
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
            IBlock component = get(i);
            if(mouseX >= (int)(component.getX() * zoom + 0.5) &&
               mouseX <= (int)((component.getX() + component.getWidth()) * zoom + 0.5) &&
               mouseY >= (int)(component.getY() * zoom + 0.5) &&
               mouseY <= (int)((component.getY() + component.getHeight()) * zoom + 0.5))
                return i;
        }
        return -1;
    }
    /**
     * �õ�����������(�ı�ʹ��)
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInComponentIndex(int mouseX,int mouseY)
    {
        if(size() ==0)
            return -1;
        IBlock componentP = get(0);
        int h = (int)((componentP.getY() + componentP.getHeight()) * getZoom() / 75.0 + 0.5);
        for(int i = 0;i < size();i++)
        {
            IBlock component = get(i);
            if(!(component instanceof EEvent))
                continue;
            if(mouseX < (int)((component.getX() + component.getWidth()) * getZoom() / 75.0 + 0.5)&&
               mouseY < (int)((component.getY() + component.getHeight()) * getZoom() / 75.0 + 0.5))
                return i;
            if(h != (int)((component.getY() + component.getHeight()) * getZoom() / 75.0 + 0.5))
            {
                if(mouseX > (int)(componentP.getX() * getZoom() / 75.0 + 0.5)&&
                   mouseY < (int)((componentP.getY() + componentP.getHeight()) * getZoom() / 75.0 + 0.5))
                    return indexOf(componentP);
                h = (int)((component.getY() + component.getHeight()) * getZoom() / 75.0 + 0.5);
            }
            componentP = component;
        }
        TList list = getBottompComponent();
        for(int i = 0;i < list.size();i++)
        {
            IBlock component = (IBlock)list.get(i);
            if(mouseX < (int)((component.getX() + component.getWidth()) * getZoom() / 75.0 + 0.5))
                return indexOf(component);
        }
        return size() - 1;
    }
    /**
     * �õ��������
     * @return TList
     */
    public TList getTopComponent()
    {
        TList list = new TList();
        if(size() == 0)
            return list;
        EComponent component = get(0);
        list.add(component);
        int h = component.getY() + component.getHeight();
        for(int i = 1;i < size();i++)
        {
            component = get(i);
            if(component == null)
                continue;
            if(h == component.getY() + component.getHeight())
                list.add(component);
            else
                break;
        }
        return list;
    }
    /**
     * �õ��ײ����
     * @return TList
     */
    public TList getBottompComponent()
    {
        TList list = new TList();
        if(size() == 0)
            return list;
        EComponent component = get(size() - 1);
        list.add(component);
        int h = component.getY() + component.getHeight();
        for(int i = size() - 2;i >= 0;i--)
        {
            component = get(i);
            if(component == null)
                continue;
            if(h == component.getY() + component.getHeight())
                list.add(0,component);
            else
                break;
        }
        return list;
    }
    /**
     * �õ������
     * @return TList
     */
    public TList getLeftComponent()
    {
        TList list = new TList();
        if(size() == 0)
            return list;
        EComponent component = get(0);
        list.add(component);
        int h = component.getY() + component.getHeight();
        for(int i = 1;i < size();i++)
        {
            component = get(i);
            if(component == null)
                continue;
            if(h != component.getY() + component.getHeight())
            {
                list.add(component);
                h = component.getY() + component.getHeight();
            }
        }
        return list;
    }
    /**
     * �õ��Ҳ����
     * @return TList
     */
    public TList getRightComponent()
    {
        TList list = new TList();
        if(size() == 0)
            return list;
        EComponent componentP = get(0);
        int h = componentP.getY() + componentP.getHeight();
        for(int i = 1;i < size();i++)
        {
            EComponent component = get(i);
            if(component == null)
                continue;
            if(h != component.getY() + component.getHeight())
            {
                list.add(componentP);
                h = component.getY() + component.getHeight();
            }
            componentP = component;
        }
        componentP = get(size() - 1);
        if(list.size() == 0 || list.get(list.size() - 1) != componentP)
            list.add(componentP);
        return list;
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
        //Table ����
        if(getType() == 1)
        {
            if(size() == 0)
                return -1;
            ETable table = (ETable)get(0);
            int x = mouseX - (int)(table.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(table.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            return table.onMouseLeftPressed(x,y);
        }
        //����������
        int componentIndex = getMouseInComponentIndex(mouseX,mouseY);
        if(componentIndex != -1)
        {
            EComponent component = get(componentIndex);
            EEvent com = (EEvent)component;
            int x = mouseX - (int)(component.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(component.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            return com.onMouseLeftPressed(x,y);
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
        //Table ����
        if(getType() == 1)
        {
            if(size() == 0)
                return;
            ETable table = (ETable)get(0);
            int x = mouseX - (int)(table.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(table.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            table.onMouseRightPressed(x,y);
            return;
        }
        //����������
        int componentIndex = getMouseInComponentIndex(mouseX,mouseY);
        if(componentIndex != -1)
        {
            EComponent component = get(componentIndex);
            EEvent com = (EEvent)component;
            int x = mouseX - (int)(component.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(component.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            com.onMouseRightPressed(x,y);
            return;
        }
        return;
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
        int componentIndex = getMouseInComponentIndex(mouseX,mouseY);
        if(componentIndex != -1)
        {
            EComponent component = get(componentIndex);
            EEvent com = (EEvent)component;
            int x = mouseX - (int)(component.getX() * getZoom() / 75.0 + 0.5);
            int y = mouseY - (int)(component.getY() * getZoom() / 75.0 + 0.5);
            //�¼�����
            return com.onDoubleClickedS(x,y);
        }
        return false;
    }
    /**
     * �õ���Ч�߶�
     * @return int
     */
    public int getUserHeight()
    {
        EComponent com = getParent();
        if(com == null)
            return 0;
        if(com instanceof EPage)
            return ((EPage)com).getUserHeight(this);
        if(com instanceof ETD)
        {
            if(getMaxHeight() > 0)
                return getMaxHeight();
            return 20000;
        }
        return 0;
    }
    /**
     * ����λ��ƫ��
     * @param com TComponent
     * @param length int
     */
    public void indexMove(IBlock com,int length)
    {
        int index = indexOf(com);
        if(index == -1)
            return;
        for(int i = index + 1;i < size();i++)
        {
            EComponent c = get(i);
            if(c == null || !(c instanceof EText))
                continue;
            EText text = (EText)c;
            text.indexMove(length);
        }
    }
    /**
     * �õ���ĻY
     * @return int
     */
    public int getScreenY()
    {
        EComponent component = getParent();
        if(component == null)
            return 0;
        if(component instanceof EPage)
            return ((EPage)component).getScreenInsetsY() + getY();
        //temp
        return 0;
    }
    /**
     * �������λ��
     * @param panel EPanel
     * @return int
     */
    public int findIndex(EPanel panel)
    {
        EComponent component = getParent();
        if(component == null)
            return -1;
        if(component instanceof EPage)
            return ((EPage) component).indexOf(panel);
        if(component instanceof ETD)
            return ((ETD)component).indexOf(panel);
        return -1;
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
     * �½���һ�����
     * @return EPanel
     */
    public EPanel newPreviousPanel()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPage)
            return ((EPage) component).newPanel(findIndex());
        if(component instanceof ETD)
            return ((ETD)component).newPanel(findIndex());
        return null;
    }
    /**
     * �½���һ�����
     * @return EPanel
     */
    public EPanel newNextPanel()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPage)
            return ((EPage) component).newPanel(findIndex() + 1);
        if(component instanceof ETD)
            return ((ETD)component).newPanel(findIndex() + 1);
        return null;
    }
    /**
     * ����Text
     * @param text EText
     */
    public void separateText(EText text)
    {
        if(text == null)
            return;
        EPanel panel = newNextPanel();
        if(panel == null)
            return;
        panel.setAlignment(getAlignment());
        int stringIndex = text.getStart();
        String s = getDString().getString(stringIndex,getDString().size());
        getDString().remove(stringIndex,getDString().size());
        panel.getDString().add(s);
        int index = indexOf(text);
        for(int i = index;i < size();i++)
        {
            IBlock block = get(i);
            if(block == null)
                continue;
            panel.add(block);
            if(!(block instanceof EText))
                continue;
            EText t = (EText)block;
            t.setStart(t.getStart() - stringIndex);
            t.setEnd(t.getEnd() - stringIndex);
        }
        for(int i = size() - 1;i >= index;i--)
            remove(i);
        //ɾ���ɾ�������һ��Text
        if(size() == 0)
            newText();
        //�ָ�ҳ����
        EPanel next = getNextPageLinkPanel();
        if(next != null)
        {
            panel.setNextPageLinkPanel(next);
            next.setPreviousPageLinkPanel(panel);
            setNextPageLinkPanel(null);
        }
        //�ָ�������
        next = getNextLinkPanel();
        if(next != null)
        {
            panel.setNextLinkPanel(next);
            next.setPreviousLinkPanel(panel);
            setNextLinkPanel(null);
        }
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
     * �õ����һ��Text
     * @return EText
     */
    public EText getLastText()
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            EComponent com = get(i);
            if(com == null)
                continue;
            if(com instanceof EText)
                return (EText)com;
            if(com instanceof ETable)
            {
                EText text = ((ETable) com).getLastText();
                if(text != null)
                    return text;
            }
        }
        return null;
    }
    /**
     * ���ҵ�һ��Text
     * @return EText
     */
    public EText getFirstText()
    {
        for(int i = 0;i < size();i ++)
        {
            EComponent com = get(i);
            if(com == null)
                continue;
            if(com instanceof EText)
                return (EText)com;
            if(com instanceof ETable)
            {
                EText text = ((ETable) com).getFirstText();
                if(text != null)
                    return text;
            }
        }
        return null;
    }
    /**
     * �õ�ǰһ�����
     * @return EText
     */
    public EText getPreviousText()
    {
        EComponent com = getParent();
        if(com == null)
            return null;
        if(com instanceof EPage)
            return ((EPage)com).getPreviousText(this);
        if(com instanceof ETD)
            return ((ETD)com).getPreviousText(this);
        return null;
    }
    /**
     * �õ���һ�����
     * @param component IBlock
     * @param includeTable boolean
     *  true �ҵ���һ�������Text
     *  false �����һ������Text ���� null
     * @return EText
     */
    public EText getNextText(IBlock component,boolean includeTable)
    {
        if(component == null)
            return null;
        //����λ��
        int index = indexOf(component);
        return getNextText(index + 1,includeTable);
    }
    /**
     * �õ���һ�����
     * @param index int
     * @param includeTable boolean
     *  true �ҵ���һ�������Text
     *  false �����һ������Text ���� null
     * @return EText
     */
    public EText getNextText(int index,boolean includeTable)
    {
        if(index == -1)
            return null;
        for(int i = index;i < size();i++)
        {
            EComponent com = get(i);
            if(com == null)
                continue;
            if(com instanceof EText)
                return(EText)com;
            if(!includeTable)
                return null;
            if(com instanceof ETable)
            {
                EText text = ((ETable)com).getFirstText();
                if(text != null)
                    return text;
            }
        }
        //�õ��������
        EPanel panel = getNextPageLinkPanel();
        if(panel == null)
            return null;
        return panel.getNextText(0,includeTable);
    }
    /**
     * �õ���һ�����
     * @param component IBlock
     * @param includeTable boolean
     * @return EText
     */
    public EText getPreviousText(IBlock component,boolean includeTable)
    {
        if(component == null)
            return null;
        //����λ��
        int thisIndex = indexOf(component);
        if(thisIndex == -1)
            return null;
        for(int i = thisIndex - 1;i >= 0;i--)
        {
            EComponent com = get(i);
            if(com == null)
                continue;
            if(com instanceof EText)
                return(EText)com;
            if(!includeTable)
                return null;
            if(com instanceof ETable)
            {
                EText text = ((ETable)com).getLastText();
                if(text != null)
                    return text;
            }
        }
        return null;
    }
    /**
     * �õ���һ�����
     * @return EText
     */
    public EText getNextText()
    {
        EComponent com = getParent();
        if(com == null)
            return null;
        if(com instanceof EPage)
            return ((EPage)com).getNextText(this);
        if(com instanceof ETD)
            return ((ETD)com).getNextText(this);
        return null;
    }
    /**
     * �ƶ�������嵽��һ��ҳ��
     * @param index int
     * @param panel EPanel
     */
    public void movePanelToNextPage(int index,EPanel panel)
    {
        EComponent component = getParent();
        if(component == null)
            return;
        if(component instanceof EPage)
        {
            ((EPage) component).movePanelToNextPage(index,panel);
            return;
        }
        //if(component instanceof ETD)
        //    return ((ETD)component).indexOf(panel);
        //temp
    }
    /**
     * �������Text
     * @param text EText
     */
    public void insertText(EText text)
    {
        if(text == null)
            return;
        String s = text.getString();
        text.getDString().remove(text.getStart(),text.getEnd());
        text.getPanel().indexMove(text,-s.length());
        getDString().add(0,s);
        indexMove(s.length());
        text.setEnd(s.length());
        text.setStart(0);
        add(0,text);
    }
    /**
     * �������Text�����
     * @param text EText
     */
    public void insertLastText(EText text)
    {
        if(text == null)
            return;
        String s = text.getString();
        text.getDString().remove(text.getStart(),text.getEnd());
        text.getPanel().indexMove(text,-s.length());
        int i = getDString().size();
        getDString().add(s);
        text.setStart(i);
        text.setEnd(i + s.length());
        add(text);
    }
    /**
     * ׷��Text
     * @param text EText
     */
    public void appendText(EText text)
    {
        if(text == null)
            return;
        int length = getDString().size();
        String s = text.getString();
        text.getDString().remove(text.getStart(),text.getEnd());
        text.getPanel().indexMove(text,-s.length());
        getDString().add(s);
        text.setStart(length);
        text.setEnd(length + s.length());
        add(text);
    }
    /**
     * ����ȫ��Text������λ��
     * @param length int
     */
    public void indexMove(int length)
    {
        for(int i = 0;i < size();i++)
        {
            EComponent component = get(i);
            if(component == null)
                continue;
            if(!(component instanceof EText))
                continue;
            EText text = (EText)component;
            text.indexMove(length);
        }
    }
    /**
     * �õ���һ��Panel
     * @return EPanel
     */
    public EPanel getPreviousPanel()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPage)
        {
            EPage page = (EPage)component;
            int index = page.indexOf(this);
            if(index <= 0)
                return null;
            return page.get(index - 1);
        }
        if(component instanceof ETD)
        {
            ETD td = (ETD) component;
            int index = td.indexOf(this);
            if(index <= 0)
                return null;
            return td.get(index - 1);
        }
        return null;
    }
    /**
     * �õ���һ��Panel
     * @return EPanel
     */
    public EPanel getNextPanel()
    {
        return getNextPanel(this);
    }
    /**
     * �õ���һ��Panel
     * @param panel EPanel
     * @return EPanel
     */
    public EPanel getNextPanel(EPanel panel)
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPage)
        {
            EPage page = (EPage)component;
            int index = page.indexOf(panel);
            if(index >= page.size() - 1)
                return null;
            return page.get(index + 1);
        }
        if(component instanceof ETD)
        {
            ETD td = (ETD) component;
            int index = td.indexOf(panel) + 1;
            if(index < td.size())
                return td.get(index);
            return null;
        }
        return null;
    }
    /**
     * ɾ��Panel
     * @param panel EPanel
     */
    public void removePanel(EPanel panel)
    {
        EComponent component = getParent();
        if(component == null)
            return;
        if(component instanceof EPage)
        {
            EPage page = (EPage)component;
            page.remove(panel);
            return;
        }
        if(component instanceof ETD)
        {
            ETD td = (ETD) component;
            td.remove(panel);
            return;
        }
    }
    /**
     * �õ���һ��ҳ���������
     * @return EPanel
     */
    public EPanel getPreviousPageLastPanel()
    {
        if(parentIsPage())
            return getParentPage().getPreviousPageLastPanel();
        if(parentIsTD())
            return getParentTD().getPreviousLinkTDLastPanel();
        return null;
    }
    /**
     * �õ���һ��ҳ���һ�����
     * @return EPanel
     */
    public EPanel getNextPageFirstPanel()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPage)
            return ((EPage)component).getNextPageFirstPanel();
        if(component instanceof ETD)
        {
            ETD td = (ETD) component;
            //temp ��Table ��
            return null;
        }
        return null;
    }
    /**
     * ������һ������
     * @return boolean
     */
    public boolean linkPreviousPanel()
    {
        boolean isPreviousPage = false;
        EPanel panel = getPreviousPanel();
        if(panel == null)
        {
            panel = getPreviousPageLastPanel();
            isPreviousPage = true;
        }
        if(panel == null)
            return false;
        if(panel.getType() == 1)
            return false;
        panel.setModify(true);
        //panel Ϊ��
        if(panel.size() == 1)
        {
            IBlock block = panel.get(0);
            if(!(block instanceof EFixed) && block instanceof EText && ((EText)block).getLength() == 0)
            {
                //��ҳɾ�����ȱ���
                if(isPreviousPage)
                {
                    EPanel previousPagePanel = panel.getPreviousPanel();
                    if(previousPagePanel != null)
                        previousPagePanel.setModify(true);
                }
                panel.removePanel(panel);
                return true;
            }
        }
        if(size() == 1)
        {
            IBlock block = get(0);
            if(block instanceof EText && ((EText)block).getLength() == 0)
            {
                if(getFocus() == block)
                    panel.setFocusLast();
                removeThis();
                return true;
            }
        }
        int stringSize = panel.getDString().size();
        panel.getDString().add(getDString().getString());
        getStringManager().remove(getDString());
        for(int i = 0;i < size();i++)
        {
            IBlock com = get(i);
            if(com == null)
                continue;
            panel.add(com);
            if(!(com instanceof EText))
                continue;
            EText text = (EText) com;
            text.indexMove(stringSize);
            //������һ��
            if(i == 0)
                text.linkText();
        }
        //ɾ���Լ�
        removePanel(this);
        EPanel next = getNextPageLinkPanel();
        panel.setNextPageLinkPanel(next);
        if(next != null)
            next.setPreviousPageLinkPanel(panel);
        next = getNextLinkPanel();
        panel.setNextLinkPanel(next);
        if(next != null)
            next.setPreviousLinkPanel(panel);
        return true;
    }

    /**
     * ������һ������
     * @return boolean
     */
    public boolean linkNextPanel()
    {
        EPanel panel = getNextPanel();
        if(panel == null)
            panel = getNextPageFirstPanel();
        if(panel == null)
            return false;
        setModify(true);
        //panel Ϊ��
        if(size() == 1)
        {
            IBlock block = get(0);
            if(block instanceof EText && ((EText)block).getLength() == 0)
            {
                setFocus(panel.getFirstText());
                setFocusIndex(0);
                removePanel(this);
                return true;
            }
        }
        int stringSize = getDString().size();
        getDString().add(panel.getDString().getString());
        getStringManager().remove(panel.getDString());
        for(int i = 0;i < panel.size();i++)
        {
            IBlock com = panel.get(i);
            if(com == null)
                continue;
            add(com);
            if(!(com instanceof EText))
                continue;
            EText text = (EText) com;
            text.indexMove(stringSize);
            //������һ��
            if(i == 0)
                text.linkText();
        }
        //ɾ����һ��Panel
        panel.removePanel(panel);
        EPanel next = panel.getNextPageLinkPanel();
        setNextPageLinkPanel(next);
        if(next != null)
            next.setPreviousPageLinkPanel(this);
        next = panel.getNextLinkPanel();
        setNextLinkPanel(next);
        if(next != null)
            next.setPreviousLinkPanel(this);
        return true;
    }
    /**
     * �õ���һ��ҳ������Text
     * @return EText
     */
    public EText getPreviousPageLastText()
    {
        if(parentIsPage())
            return getParentPage().getPreviousPageLastText();
        if(parentIsTD())
        {
            ETD td = getParentTD();
            td = td.getPreviousLinkTD();
            if(td == null || td.size() == 0)
                return null;
            return td.get(td.size() - 1).getLastText();
        }
        return null;
    }
    /**
     * �õ���һ��ҳ���һ����Text
     * @return EText
     */
    public EText getNextPageFirstText()
    {
        if(parentIsPage())
            return getParentPage().getNextPageFirstText();
        if(parentIsTD())
        {
            ETD td = getParentTD();
            td = td.getNextLinkTD();
            if(td == null || td.size() == 0)
                return null;
            return td.get(0).getFirstText();
        }
        return null;
    }
    /**
     * �����ߴ�
     */
    public void reset()
    {
        if(!isModify())
            return;
        //����
        getResetBlock().clear();
        //���óߴ緶Χ
        getResetBlock().setRectangle(0,
                                     0,
                                     getWidth(),
                                     getMaxHeight());
        //�����ߴ�
        getResetBlock().reset();
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
     * �ҵ����߶ȵ�Table
     * @param blocks TList
     * @return ETable
     */
    public ETable getTable(TList blocks)
    {
        if(blocks == null || blocks.size() == 0)
            return null;
        for(int i = 0;i < blocks.size();i++)
        {
            IBlock b = (IBlock)blocks.get(i);
            if(b instanceof ETable)
                return(ETable)b;
        }
        return null;
    }
    /**
     * ���ӳ�Ա
     * ���������ȫ����Ա�ӵ����
     * @param panel EPanel
     */
    public void add(EPanel panel)
    {
        if(panel == null)
            return;
        while(panel.size() > 0)
            moveBlockLast(panel.get(0));
    }
    /**
     * �ƶ��鵽���������
     * @param block IBlock
     */
    public void moveBlockLast(IBlock block)
    {
        if(block == null)
            return;
        EPanel parent = (EPanel)block.getParent();
        if(parent == null)
            return;
        parent.setModify(true);
        if(!(block instanceof EText))
            add(block);
        else
            appendText((EText)block);
        parent.remove(block);
        //�������һ��Panelɾ���ɾ���
        if(parent.size() == 0)
        {
            parent.removePanel(parent);
            //Page����
            EPanel next = parent.getNextPageLinkPanel();
            setNextPageLinkPanel(next);
            if(next != null)
                next.setPreviousPageLinkPanel(this);
            //TD����
            next = parent.getNextLinkPanel();
            setNextLinkPanel(next);
            if(next != null)
                next.setPreviousLinkPanel(this);
        }
    }
    /**
     * �ƶ��鵽��������ǰ
     * @param block IBlock
     */
    public void moveBlockFirst(IBlock block)
    {
        if(block == null)
            return;
        EPanel parent = (EPanel)block.getParent();
        if(parent == null)
            return;
        if(!(block instanceof EText))
            add(block);
        else
            insertText((EText)block);
        parent.remove(block);
        block.setModify(true);
    }
    /**
     * ������һҳ�������
     * @return EPanel
     */
    public EPanel createNextLinkPanel()
    {
        EComponent parent = getParent();
        if(parent == null)
            return null;
        EPanel panel = null;
        if(parentIsPage())
        {
            panel = getParentPage().createNextPageLinkPanel();
            EPage page = panel.getParentPage();
            int index = findIndex();
            for(int i = getParentPage().size() - 1;i > index;i --)
            {
                EPanel p = getParentPage().get(i);
                getParentPage().remove(p);
                page.add(1,p);
            }
        }
        else if(parentIsTD())
        {
            panel = getParentTD().createNextPageLinkPanel();
            ETD td = panel.getParentTD();
            int index = findIndex();
            for(int i = getParentTD().size() - 1;i > index;i --)
            {
                EPanel p = getParentTD().get(i);
                getParentTD().remove(p);
                td.add(1,p);
            }
        }
        else
            return null;
        panel.setAlignment(getAlignment());
        panel.setPreviousPageLinkPanel(this);
        setNextPageLinkPanel(panel);
        panel.setModify(true);

        return panel;
    }
    /**
     * �õ���һ����������ƶ�ʹ��
     * @return EPanel
     */
    private EPanel getNextLinkPanelForMove()
    {
        if(parentIsPage())
            return getNextPageLinkPanel();
        if(parentIsTD())
            return getNextLinkPanel();
        return null;
    }
    /**
     * �ƶ��������һ��ҳ
     * @param index int
     */
    public void moveBlockToNextPage(int index)
    {
        //�õ���һ����������ƶ�ʹ��
        EPanel panel = getNextLinkPanelForMove();
        if(panel != null)
            panel.setModify(true);
        if(index >= size())
            return;
        //������һҳ�������
        if(panel == null)
            panel = createNextLinkPanel();
        for(int i = size() - 1;i >= index;i--)
        {
            IBlock block = get(i);
            if(block.getParent() == this)
                panel.moveBlockFirst(block);
        }
        //������panelȫ��ת�Ƶ���ҳ
        if(parentIsPage())
            getParentPage().separatePanel(this,panel);
        else if(parentIsTD())
            getParentTD().separatePanel(this,panel);
    }
    /**
     * ������Y����
     * @param blocks TList
     * @return int
     */
    private int getRowY(TList blocks)
    {
        if(blocks == null || blocks.size() == 0)
            return getParagraphForward();
        return getRowY((IBlock)blocks.get(0));
    }
    /**
     * ������Y����
     * @param block IBlock
     * @return int
     */
    private int getRowY(IBlock block)
    {
        if(block == null)
            return getParagraphForward();
        IBlock previousBlock = getPreviousBlock(block);
        if(previousBlock != null)
            return previousBlock.getY() + previousBlock.getHeight() + getSpaceBetween();
        return getParagraphForward();
    }

    /**
     * �õ���Ŀ����
     * @return int
     */
    public int getBlockSize()
    {
        int count = size();
        EPanel next = getNextPageLinkPanel();
        while(next != null)
        {
            count += next.size();
            next = next.getNextPageLinkPanel();
        }
        return count;
    }
    /**
     * �õ�����
     * @param index int
     * @return IBlock
     */
    public IBlock getBlockHead(int index)
    {
        for(int i = index;i >= 0;i--)
        {
            IBlock block = getBlock(index);
            if((block.getPosition() & 1) == 1)
                return block;
        }
        return null;
    }
    /**
     * �õ�һ�еĿ�����
     * @param index int
     * @return TList
     */
    public TList getBlockRow(int index)
    {
        TList list = new TList();
        for(int i = index;i >= 0;i--)
        {
            IBlock block = getBlock(i);
            if((block.getPosition() & 2) == 2)
                break;
            list.add(0,block);
            if((block.getPosition() & 1) == 1)
                break;
        }
        int size = getBlockSize();
        for(int i = index;i < size;i++)
        {
            IBlock block = getBlock(i);
            if(list.indexOf(block) == -1)
                list.add(block);
            if((block.getPosition() & 2) == 2)
                break;
        }
        return list;
    }
    /**
     * �õ���β
     * @param index int
     * @return IBlock
     */
    public IBlock getBlockEnd(int index)
    {
        for(int i = index;i < getBlockSize();i++)
        {
            IBlock block = getBlock(index);
            if((block.getPosition() & 2) == 2)
                return block;
        }
        return null;
    }
    /**
     * �õ���һ����
     * @param block IBlock
     * @return IBlock
     */
    public IBlock getPreviousBlock(IBlock block)
    {
        if(block == null)
            return null;
        int index = getBlockIndex(block);
        if(index == -1)
            return null;
        return getPreviousBlock(index);
    }
    /**
     * �õ���һ����
     * @param index int
     * @return IBlock
     */
    public IBlock getPreviousBlock(int index)
    {
        if(index < 1)
            return null;
        return getBlock(index - 1);
    }
    /**
     * �õ���һ����
     * @param block IBlock
     * @return IBlock
     */
    public IBlock getNextBlock(IBlock block)
    {
        if(block == null)
            return null;
        int index = getBlockIndex(block);
        if(index == -1)
            return null;
        return getNextBlock(index);
    }
    /**
     * �õ���һ����
     * @param index int
     * @return IBlock
     */
    public IBlock getNextBlock(int index)
    {
        if(index >= getBlockSize())
            return null;
        return getBlock(index + 1);
    }
    /**
     * �õ���λ��
     * @param block IBlock
     * @return int
     */
    public int getBlockIndex(IBlock block)
    {
        if(block == null)
            return -1;
        int index = indexOf(block);
        if(index != -1)
            return index;
        EPanel next = getNextPageLinkPanel();
        int count = size();
        while(next != null)
        {
            index = next.indexOf(block);
            if(index != -1)
                return index + count;
            count += next.size();
            next = getNextPageLinkPanel();
        }
        return -1;
    }
    /**
     * �õ����(�����������)
     * @param index int
     * @return IBlock
     */
    public IBlock getBlock(int index)
    {
        if(index < 0)
            return null;
        if(index < size())
            return get(index);
        EPanel next = getNextPageLinkPanel();
        int count = size();
        while(next != null)
        {
            index -= count;
            if(index < next.size())
                return next.get(index);
            count = next.size();
            next = next.getNextPageLinkPanel();
        }
        return null;
    }
    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString()
    {
        EComponent parent = getParent();
        if(parent == null)
            return "Parent:Null";
        if(parent instanceof EPage)
            return ((EPage)parent).getIndexString() + ",Panel:" + findIndex();
        if(parent instanceof ETD)
            return ((ETD)parent).getIndexString() + ",Panel:" + findIndex();
        return "";
    }
    /**
     * ɾ���Լ�
     */
    public void removeThis()
    {
        removeAll();
        EComponent parent = getParent();
        if(parent == null)
            return;
        if(parent instanceof EPage)
        {
            EPage page = (EPage)parent;
            //TEST
            page.remove(this);
            EPanel pPanel = getPreviousPageLinkPanel();
            EPanel nPanel = getNextPageLinkPanel();
            if(pPanel != null)
                pPanel.setNextPageLinkPanel(nPanel);
            if(nPanel != null)
                nPanel.setPreviousPageLinkPanel(pPanel);
        }
        if(parent instanceof ETD)
        {
            ETD td = (ETD)parent;
            td.remove(this);
            EPanel pPanel = getPreviousLinkPanel();
            EPanel nPanel = getNextLinkPanel();
            if(pPanel != null)
                pPanel.setNextLinkPanel(nPanel);
            if(nPanel != null)
                nPanel.setPreviousLinkPanel(pPanel);
        }
    }
    public String toString()
    {
        return "<EPanel size=" + size() +
                ",x=" + getX() +
                ",y=" + getY() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",hasPreviousPageLinkPanel=" + hasPreviousPageLinkPanel() +
                ",hasNextPageLinkPanel=" + hasNextPageLinkPanel() +
                ",DStringSize=" + getDString().size() +
                ",isModify=" + isModify() +
                ",type=" + getType() +
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
            IBlock block = get(j);
            block.debugShow(i + 2);
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
            IBlock block = get(j);
            block.debugModify(i + 2);
        }
    }
    /**
     * �õ�����
     * @return String
     */
    public String getString()
    {
        if(getDString() == null)
            return "";
        return getDString().getString();
    }
    /**
     * ��������
     * @param s String
     */
    public void setString(String s)
    {
        if(getDString() == null)
            return;
        getDString().removeAll();
        getDString().add(s);
        removeAll();
        EText text = newText();
        text.setEnd(s.length());
        text.setModify(true);
    }
    /**
     * �õ�������λ��
     * @return int
     */
    public int getPointX()
    {
        EComponent parent = getParent();
        if(parent instanceof EPage)
            return getX();
        if(parent instanceof ETD)
            return ((ETD)parent).getPointX() + getX();
        return 0;
    }
    /**
     * �õ�����ģ��
     * @param component IBlock
     * @return IBlock
     */
    public IBlock getHomeBlock(IBlock component)
    {
        if(component == null)
            return null;
        if((component.getPosition() & 1) == 1)
            return component;
        return getHomeBlock(indexOf(component) - 1);
    }
    /**
     * �õ�����ģ��
     * @param index int
     * @return IBlock
     */
    public IBlock getHomeBlock(int index)
    {
        if(index >= size())
            return null;
        for(int i = index;i >= 0;i--)
        {
            IBlock com = get(i);
            if((com.getPosition() & 1) == 1)
                return com;
        }
        return null;
    }
    /**
     * �õ���βģ��
     * @param component IBlock
     * @return IBlock
     */
    public IBlock getEndBlock(IBlock component)
    {
        if(component == null)
            return null;
        if((component.getPosition() & 2) == 2)
            return component;
        return getEndBlock(indexOf(component) + 1);
    }
    /**
     * �õ���βģ��
     * @param index int
     * @return IBlock
     */
    public IBlock getEndBlock(int index)
    {
        if(index >= size())
            return null;
        for(int i = index;i < size();i++)
        {
            IBlock com = get(i);
            if((com.getPosition() & 2) == 2)
                return com;
        }
        return null;
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
        EComponent parent = getParent();
        if(parent instanceof EPage)
        {
            ((EPage)parent).getPath(list);
            return;
        }
        if(parent instanceof ETD)
        {
            ((ETD)parent).getPath(list);
            return;
        }
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
        EComponent parent = getParent();
        if(parent instanceof EPage)
        {
            ((EPage)parent).getPathIndex(list);
            return;
        }
        if(parent instanceof ETD)
        {
            ((ETD)parent).getPathIndex(list);
            return;
        }
    }
    /**
     * �õ������ı��б�
     * @param list TList
     * @param endText EText
     */
    public void getFindTextList(TList list,EText endText)
    {
        EComponent next = get(0);
        if(next instanceof EText)
            ((EText)next).getFindTextList(list,endText);
        //if(next instanceof ETable)
        //    ((ETable)next).getFindTextList(list,endText);
    }
    /**
     * �õ���һ��ҳ�������б�
     * @param list TList
     * @param endText EText
     */
    public void getFindNextTextList(TList list,EText endText)
    {
        EPanel next = getNextPanel();
        while(next != null && next.size() == 0)
            next = getNextPanel(next);
        if(next != null)
        {
            next.getFindTextList(list, endText);
            return;
        }
        EComponent parent = getParent();
        if(parent instanceof EPage)
        {
            EPage page = (EPage)parent;
            page.getFindNextTextList(list,endText);
        }
        if(parent instanceof ETD)
        {
            ETD td = (ETD)parent;
            //td.getFindNextTextList(list,endText);
        }
    }
    /**
     * ����ʹ����ʽ
     */
    public void checkGCStyle()
    {
        for(int i = 0;i < size();i++)
        {
            IBlock b = get(i);
            if(b instanceof EText)
                ((EText)b).checkGCStyle();
            if(b instanceof ETable)
                ((ETable)b).checkGCStyle();
        }
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
        IBlock b = get(index);
        if(b instanceof EText){

        	EText et = ((EText)b);
        	String str = et.getString();
        	//System.out.println( " #### " + str );
        	//et.getResetText().getText().getString();
        	//System.out.println( " @@@@ " + et.getKeyIndex() ); //��ʱ�� 1 �к��� -1û��

        	//û�޸ĺۼ��Ĳ���ʾtooltip �� û���ݵĲ���ʾ
        	Integer mi = et.getModifyIndex();
        	if(  ( et.getKeyIndex() == -1 && null== mi ) || ( null==str || str.equals("") ) ){
        		 getPM().getUI().getParentTCBase().setToolTipText(null);
        		 return false;
        	}

            //
            return et.onMouseMoved( mouseX - (int)(b.getX() * getZoom() / 75.0 + 0.5),
                                    mouseY - (int)(b.getY() * getZoom() / 75.0 + 0.5),mi );
            }
        if(b instanceof ETable)
            return ((ETable)b).onMouseMoved(mouseX - (int)(b.getX() * getZoom() / 75.0 + 0.5),
                                            mouseY - (int)(b.getY() * getZoom() / 75.0 + 0.5));
        if(b instanceof EImage)
            return ((EImage)b).onMouseMoved(mouseX - (int)(b.getX() * getZoom() / 75.0 + 0.5),
                                            mouseY - (int)(b.getY() * getZoom() / 75.0 + 0.5));
        return false;
    }
    /**
     * �õ���Ļ����
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        EComponent parent = getParent();
        DPoint point = null;
        if(parent instanceof EPage)
            point = ((EPage)parent).getScreenPoint();
        if(parent instanceof ETD)
            point = ((ETD)parent).getScreenPoint();
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
        if(action.getType() == EAction.SET_SEX_CONTROL)
            setModify(true);
        for(int i = 0;i < size();i++)
            get(i).resetData(action);
    }
    /**
     * ���óߴ��������
     * @param resetBlock ResetBlock
     */
    public void setResetBlock(ResetBlock resetBlock)
    {
        this.resetBlock = resetBlock;
    }
    /**
     * �õ��ߴ��������
     * @return ResetBlock
     */
    public ResetBlock getResetBlock()
    {
        return resetBlock;
    }
    /**
     * �����ƶ���굽��һ���������
     * @return boolean
     */
    public boolean onLinkPanelFocusToRight()
    {
        if(parentIsPage())
        {
            EPanel panel = getNextPageLinkPanel();
            if(panel != null)
                if (panel.setFocusLast())
                    return true;
        }
        else if(parentIsTD())
        {
            EPanel panel = getNextLinkPanel();
            if(panel != null)
                if (panel.setFocusLast())
                    return true;
        }
        return false;
    }
    /**
     * �����ƶ���굽��һ���������
     * @return boolean
     */
    public boolean onLinkPanelFocusToLeft()
    {
        if(parentIsPage())
        {
            EPanel panel = getPreviousPageLinkPanel();
            if(panel != null)
                if (panel.setFocusLast())
                    return true;
        }
        else if(parentIsTD())
        {
            EPanel panel = getPreviousLinkPanel();
            if(panel != null)
                if (panel.setFocusLast())
                    return true;
        }
        return false;
    }
    /**
     * �����ƶ���굽��һ�����
     * @return boolean
     */
    public boolean onNextPanelFocusToRight()
    {
        if(parentIsPage())
            return getParentPage().onFocusToRight(this);
        if(parentIsTD())
            return getParentTD().onFocusToRight(this);
        return false;
    }
    /**
     * �����ƶ���굽��һ�����
     * @return boolean
     */
    public boolean onNextPanelFocusToLeft()
    {
        if(parentIsPage())
            return getParentPage().onFocusToLeft(this);
        if(parentIsTD())
            return getParentTD().onFocusToLeft(this);
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
            IBlock block = get(i);
            if(block instanceof EText)
                return ((EText)block).setFocus();
            if(block instanceof ETable)
                if(((ETable)block).setFocus())
                    return true;
        }
        if(getNextPageLinkPanel() != null)
            if(getNextPageLinkPanel().setFocus())
                return true;
        if(getNextLinkPanel() != null)
            if(getNextLinkPanel().setFocus())
                return true;
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
            IBlock block = get(i);
            if(block instanceof EText)
                return ((EText)block).setFocusLast();
            if(block instanceof ETable)
                if(((ETable)block).setFocusLast())
                    return true;
        }
        if(getPreviousPageLastPanel() != null)
            if(getPreviousPageLastPanel().setFocusLast())
                return true;
        if(getPreviousLinkPanel() != null)
            if(getPreviousLinkPanel().setFocusLast())
                return true;
        return false;
    }
    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setPreviousFocus()
    {
        if(hasPreviousPageLinkPanel())
            return getPreviousPageLinkPanel().setFocusLast();
        if(hasPreviousLinkPanel())
            return getPreviousLinkPanel().setFocusLast();
        int index = findIndex() - 1;
        if(index >= 0)
        {
            if(parentIsPage())
                return getParentPage().get(index).setFocusLast();
            if(parentIsTD())
                return getParentTD().get(index).setFocusLast();
        }
        if(parentIsPage())
            return getParentPage().setPreviousFocus();
        if(parentIsTD())
            return getParentTD().setPreviousFocus();
        return false;
    }
    /**
     * ������������������
     * @return int
     */
    public int parentSize()
    {
        if(parentIsPage())
            return getParentPage().size();
        if(parentIsTD())
            return getParentTD().size();
        return -1;
    }
    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setNextFocus()
    {
        if(hasNextPageLinkPanel())
            return getNextPageLinkPanel().setFocus();
        if(hasNextLinkPanel())
            return getNextLinkPanel().setFocus();
        int index = findIndex() + 1;
        if(index < parentSize())
        {
            if(parentIsPage())
                return getParentPage().get(index).setFocus();
            if(parentIsTD())
                return getParentTD().get(index).setFocus();
        }
        if(parentIsPage())
            return getParentPage().setNextFocus();
        if(parentIsTD())
            return getParentTD().setNextFocus();
        return false;
    }
    /**
     * ����޸ĺۼ�
     */
    public void resetModify()
    {
        if(!isModify())
            return;
        for(int i = 0;i < size();i ++)
        {
            IBlock block = get(i);
            if(block instanceof EText)
                ((EText)block).resetModify();
        }
    }
    /**
     * ����Table��λ��
     * @return int
     */
    public int findTableIndex()
    {
        for(int i = 0;i < size();i++)
            if(get(i) instanceof ETable)
                return i;
        return -1;
    }
    /**
     * ������Ч��
     */
    public void checkValid()
    {
        if (size() > 0)
            return;
        if(parentIsPage())
        {
            EPage page = getParentPage();
            if(page.size() == 1 && page.getPageManager().size() == 1)
            {
                EText text = newText();
                text.setFocus();
                return;
            }
            removeThis();
            /*int index = findIndex();
            int pageindex = page.findIndex();
            MPage mpage = page.getPageManager();
            EText f = (EText)getFocus();
            ETable table = getFocusManager().getFocusTable();
            if(table != null && table.size() > 0)
                return;
            if(table != null && table.getPanel() != this)
                return;
            if(f != null && f.getPanel() != this)
                return;
            if(page.size() > 0)
            {
                if (index == page.size())
                {
                    page.get(index - 1).setFocusLast();
                    return;
                }
                page.get(index).setFocus();
                return;
            }
            if(pageindex == mpage.size())
            {
                mpage.get(pageindex - 1).setFocusLast();
                return;
            }
            mpage.get(pageindex).setFocus();*/
            return;
        }
        if(parentIsTD())
        {
            removeThis();
            //���ܻ��д���
        }
    }
    /**
     * ��������������
     * @param panel TPanel
     */
    public void setLinkPanelParameter(EPanel panel)
    {
        if(panel == null)
            return;
        panel.setAlignment(getAlignment());
        panel.setParagraphForward(getParagraphForward());
        panel.setParagraphAfter(getParagraphAfter());
        panel.setSpaceBetween(getSpaceBetween());
        panel.setRetractLeft(getRetractLeft());
        panel.setRetractRight(getRetractRight());
        panel.setRetractType(getRetractType());
        panel.setRetractWidth(getRetractWidth());
        panel.setElementEdit(isElementEdit());
    }
    /**
     * ͬ��ȫ������������
     */
    public void setLinkPanelParameterAll()
    {
        EPanel panel = getHeadPanel();
        if(panel != this)
        {
            setLinkPanelParameter(panel);
            panel.setModify(true);
        }
        if(parentIsPage())
        {
            panel = panel.getNextPageLinkPanel();
            while(panel != null)
            {
                if(panel != this)
                {
                    setLinkPanelParameter(panel);
                    panel.setModify(true);
                }
                panel = panel.getNextPageLinkPanel();
            }
        }
        if(parentIsTD())
        {
            panel = panel.getNextLinkPanel();
            while(panel != null)
            {
                if(panel != this)
                {
                    setLinkPanelParameter(panel);
                    panel.setModify(true);
                }
                panel = panel.getNextLinkPanel();
            }
        }
    }
    /**
     * �õ������
     * @return EPanel
     */
    public EPanel getHeadPanel()
    {
        if(parentIsPage())
        {
            if(getPreviousPageLinkPanel() == null)
                return this;
            return getPreviousPageLinkPanel().getHeadPanel();
        }
        if(parentIsTD())
        {
            if(getPreviousLinkPanel() == null)
                return this;
            return getPreviousLinkPanel().getHeadPanel();
        }
        return this;
    }
    /**
     * �õ��������
     * @return EPanel
     */
    public EPanel getEndPanel()
    {
        if(parentIsPage())
        {
            if(getNextPageLinkPanel() == null)
                return this;
            return getNextPageLinkPanel().getEndPanel();
        }
        if(parentIsTD())
        {
            if(getNextLinkPanel() == null)
                return this;
            return getNextLinkPanel().getEndPanel();
        }
        return this;
    }
    /**
     * �õ���һ�����
     * @return EPanel
     */
    public EPanel getPreviousTruePanel()
    {
        EPanel panel = getHeadPanel();
        if(panel.parentIsPage())
        {
            EPage page = panel.getParentPage();
            panel = page.get(panel.findIndex() - 1);
            if(panel != null)
                return panel;
            page = page.getPreviousPage();
            if(page == null || page.size() == 0)
                return null;
            return page.get(page.size() - 1);
        }else if(panel.parentIsTD())
        {
            ETD td = panel.getParentTD();
            panel = td.get(panel.findIndex() - 1);
            if(panel != null)
                return panel;
            td = td.getPreviousLinkTD();
            if(td == null || td.size() == 0)
                return null;
            return td.get(td.size() - 1);
        }
        return null;
    }
    /**
     * �õ���һ�����
     * @return EPanel
     */
    public EPanel getNextTruePanel()
    {
        EPanel panel = getEndPanel();
        if(panel.parentIsPage())
        {
            EPage page = panel.getParentPage();
            panel = page.get(panel.findIndex() + 1);
            if(panel != null)
                return panel;
            page = page.getNextPage();
            if(page == null || page.size() == 0)
                return null;
            return page.get(0);
        }else if(panel.parentIsTD())
        {
            ETD td = panel.getParentTD();
            panel = td.get(panel.findIndex() + 1);
            if(panel != null)
                return panel;
            td = td.getNextLinkTD();
            if(td == null || td.size() == 0)
                return null;
            return td.get(0);
        }
        return null;
    }
    /**
     * �õ��µ���Ч����
     */
    public void setNewFocusPanel()
    {
        EPanel panelEnd = getEndPanel();
        if(parentIsPage())
        {
            int index = panelEnd.findIndex();
            EPage page = panelEnd.getParentPage();
            //�õ���һ��Panel
            if(index < page.size() - 1)
            {
                page.get(index + 1).setFocus();
                return;
            }
            //�õ���һ��ҳ��ĵ�һ��Panel
            EPage pageNext = page.getNextPage();
            if(pageNext != null && pageNext.size() > 0)
            {
                pageNext.get(0).setFocus();
                return;
            }
            //�õ���һ��Panel
            index = findIndex();
            if(index > 0)
            {
                EPanel p = page.get(index - 1);
                if(p != null)
                    p.setFocus();
                return;
            }
            //�õ���һ��ҳ������һ��Panel
            page = getParentPage();
            EPage pagePrev = page.getPreviousPage();
            if(pagePrev != null && pagePrev.size() > 0)
            {
                pagePrev.get(pagePrev.size() - 1).setFocusLast();
                return;
            }
        }
        if(parentIsTD())
        {
            int index = panelEnd.findIndex();
            ETD td = panelEnd.getParentTD();
            //�õ���һ��Panel
            if(index < td.size() - 1)
            {
                td.get(index + 1).setFocus();
                return;
            }
            //�õ���һ��TD�ĵ�һ��Panel
            ETD tdNext = td.getNextLinkTD();
            if(tdNext != null && tdNext.size() > 0)
            {
                tdNext.get(0).setFocus();
                return;
            }
            //�õ���һ��Panel
            index = findIndex();
            if(index > 0)
            {
                td.get(index - 1).setFocus();
                return;
            }
            //�õ���һ��TD�����һ��Panel
            td = getParentTD();
            ETD tdPrev = td.getPreviousLinkTD();
            if(tdPrev != null && tdPrev.size() > 0)
            {
                tdPrev.get(tdPrev.size() - 1).setFocusLast();
                return;
            }
        }
    }
    /**
     * �Ƿ�ѡ�л���
     * @return boolean
     */
    public boolean isSelectedDraw()
    {
        if(parentIsTD())
            return getParentTD().isSelectedDraw();
        if(parentIsPage())
            return false;
        return false;
    }
    /**
     * �õ�������ڵ�TD
     * @return ETD
     */
    public ETD getComponentInTD()
    {
        if(parentIsTD())
            return getParentTD();
        return null;
    }
    /**
     * ��������Ƿ��Ǳ����ĺ������
     * @param panel EPanel
     * @return boolean
     */
    public boolean panelIsNextListPanel(EPanel panel)
    {
        if(panel == null)
            return false;
        if(panel == this)
            return true;
        panel = panel.parentIsPage()?panel.getPreviousPageLinkPanel():panel.getPreviousLinkPanel();
        while(panel != null)
        {
            if(panel == this)
                return true;
            panel = panel.parentIsPage()?panel.getPreviousPageLinkPanel():panel.getPreviousLinkPanel();
        }
        return false;
    }
    /**
     * ��������Ƿ���ͬ�����
     * @param panel EPanel
     * @return boolean
     */
    public boolean panelIsClassPanel(EPanel panel)
    {
        if(panel == null)
            return false;
        if(parentIsPage() && panel.parentIsPage())
            return true;
        if(parentIsTD() && panel.parentIsTD())
            return getParentTD().isNextListTD(panel.getParentTD());
        return false;
    }
    /**
     * ����ȫ�����ѡ��
     * @param isSelected boolean
     */
    public void setSelectedAll(boolean isSelected)
    {
        EPanel panel = getHeadPanel();
        while(panel != null)
        {
            panel.setSelected(isSelected);
            panel = panel.parentIsPage()?panel.getNextPageLinkPanel():panel.getNextLinkPanel();
        }
    }
    /**
     * ����ѡ��
     * @param isSelected boolean
     */
    public void setSelected(boolean isSelected)
    {
        if(this.isSelected == isSelected)
            return;
        this.isSelected = isSelected;
        for(int i = 0;i < size();i++)
        {
            IBlock block = get(i);
            if(block instanceof EText)
            {
                EText text = (EText)block;
                if(isSelected)
                    text.selectAll();
                else
                    text.clearSelected();
            }
            else if(block instanceof ETable)
            {
                ETable table = (ETable)block;
                table.setSelectedAll(isSelected);
            }
        }
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
     * ���ö��䷽ʽ(ȫ���������)
     * @param alignment int
     */
    public void setAlignmentAll(int alignment)
    {
        if(getAlignment() == alignment)
            return;
        EPanel panel = getHeadPanel();
        while(panel != null)
        {
            panel.setAlignment(alignment);
            panel = panel.parentIsPage()?panel.getNextPageLinkPanel():panel.getNextLinkPanel();
        }
    }
    /**
     * �����Ƿ�ѡ�лس�������
     * @param selectedEnterModel CSelectPanelEnterModel
     */
    public void setSelectedEnterModel(CSelectPanelEnterModel selectedEnterModel)
    {
        this.selectedEnterModel = selectedEnterModel;
    }
    /**
     * �õ�ѡ�лس�������
     * @return CSelectPanelEnterModel
     */
    public CSelectPanelEnterModel getSelectedEnterModel()
    {
        return selectedEnterModel;
    }
    /**
     * ����ѡ�лس�������
     * @return CSelectPanelEnterModel
     */
    public CSelectPanelEnterModel createSelectedEnterModel()
    {
        CSelectPanelEnterModel model = getSelectedEnterModel();
        if(model != null)
            getFocusManager().getSelectedModel().removeSelectList(model.getSelectList());
        model = new CSelectPanelEnterModel(this);
        model.setSelectList(getFocusManager().getSelectedModel().getSelectedList());
        setSelectedEnterModel(model);
        return model;
    }
    /**
     * ����ѡ�ж���
     * @param selectedModel CSelectPanelModel
     */
    public void setSelectedModel(CSelectPanelModel selectedModel)
    {
        this.selectedModel = selectedModel;
    }
    /**
     * �õ�ѡ�ж���
     * @return CSelectPanelModel
     */
    public CSelectPanelModel getSelectedModel()
    {
        return selectedModel;
    }
    /**
     * ����ѡ�ж���
     * @return CSelectPanelModel
     */
    public CSelectPanelModel createSelectedModel()
    {
        CSelectPanelModel model = getSelectedModel();
        if(model != null)
            getFocusManager().getSelectedModel().removeSelectList(model.getSelectList());
        model = new CSelectPanelModel(this);
        model.setSelectList(getFocusManager().getSelectedModel().getSelectedList());
        setSelectedModel(model);
        return model;
    }
    /**
     * �Ƿ�ѡ�лس�������
     * @return boolean
     */
    public boolean isSelectedEnterModel()
    {
        return getSelectedEnterModel() != null;
    }
    /**
     * �õ�ȫ���������
     * @param list TList
     * @param all boolean
     */
    public void getPanelAll(TList list,boolean all)
    {
        if(getPreviousLinkPanel() != null || getPreviousPageLinkPanel() != null)
            return;
        if(size() > 0 && getType() != 1)
            return;
        IBlock block = get(0);
        if(block instanceof ETable)
            ((ETable)block).getPanelAll(list,all);
    }
    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        if(action == null)
            return;
        EPanel panel = getHeadPanel();
        switch(action.getType())
        {
        case EAction.DELETE: //ɾ������
            if (getType() == 1)
                panel.onDeleteTable(action.getInt(0));
            else {
                switch (action.getInt(0)) {
                case 0:
                case 1:
                    panel.onDeleteThis(true);
                    break;
                case 2:
                    panel.onDeleteThis(false);
                    break;
                }
            }
            return;
            //$$=========Added by lx 2011-09-07 ����ؼ����ƹ���START==============$$//
        case EAction.COPY: //
            for (int i = 0; i < panel.size(); i++) {
                CopyOperator.addComList(panel.get(i));
            }
            //return;
            //$$=========Added by lx 2011-09-07 ����ؼ����ƹ���START END==============$$//
            while (panel != null) {
                for (int i = 0; i < panel.size(); i++) {
                    IBlock block = panel.get(i);
                    if (block instanceof EText &&
                        ((EText) block).hasPreviousLink())
                        continue;
                    if (block instanceof ETable &&
                        ((ETable) block).hasPreviousLink())
                        continue;
                    if (!(block instanceof IExeAction))
                        continue;
                    ((IExeAction) block).exeAction(action);
                }
                panel = panel.parentIsPage() ? panel.getNextPageLinkPanel() :
                        panel.getNextLinkPanel();
            }
        }
    }
    /**
     * ɾ������
     * @param flg boolean true ȫ��ɾ�� false ��ͨɾ��
     */
    public void onDelete(boolean flg)
    {
        for(int i = size() - 1;i >= 0;i--)
        {
            IBlock block = get(i);
            if(block instanceof EText)
                ((EText)block).onDelete(flg);
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
            if(get(i) instanceof EFixed)
                return true;
        }
        return false;
    }
    /**
     * ���ڹ̶��ı�ȫ��ҳ��
     * @return boolean
     */
    public boolean hasFixedALL()
    {
        EPanel panel = getHeadPanel();
        while(panel != null)
        {
            if(panel.hasFixed())
                return true;
            panel = panel.parentIsPage()?panel.getNextPageLinkPanel():panel.getNextLinkPanel();
        }
        return false;
    }
    /**
     * ɾ�����
     * @param flg int
     * 0 ����ɾ��
     * 1 ��ͨɾ��
     * 2 ���
     */
    public void onDeleteTable(int flg)
    {
        if(size() == 0)
            return;
        IBlock block = get(0);
        if(!(block instanceof ETable))
            return;
        ETable table = (ETable)block;
        table.onDeleteThis(flg);
        if(getSelectedModel() != null)
        {
            getSelectedModel().getSelectList().remove(getSelectedModel());
            getSelectedModel().removeThis();
        }
    }
    /**
     * ɾ������
     * @param flg boolean true ����ɾ�� false ��ͨɾ��
     */
    public void onDeleteThis(boolean flg)
    {
        setModify(true);
        EPanel panel = getEndPanel();
        while(panel != null)
        {
        	//test lx ȷ����ѡ�е���ɾ��
        	if(panel.isSelected()){
        		panel.onDelete(flg);
        	}
            panel = panel.parentIsPage()?
                    panel.getPreviousPageLastPanel():
                    panel.getPreviousLinkPanel();

        }
        if(getSelectedModel() != null)
        {
            getSelectedModel().getSelectList().remove(getSelectedModel());
            getSelectedModel().removeThis();
        }
    }
    /**
     * �����������(�����������)
     * @return boolean
     */
    public boolean focusInPanel()
    {
        EPanel panel = getHeadPanel();
        while(panel != null)
        {
            if(getFocusManager().focusInPanel(panel))
                return true;
            panel = panel.parentIsPage()?panel.getNextPageLinkPanel():panel.getNextLinkPanel();
        }
        return false;
    }
    /**
     * �õ�ͬ�����һ�����
     * @return EPanel
     */
    public EPanel getNextPanelInLayer()
    {
        int index = findIndex() + 1;
        if(index < parentSize())
            return parentIsPage()?getParentPage().get(index):getParentTD().get(index);
        if(parentIsPage())
        {
            EPage page = getParentPage().getNextPage();
            if(page != null && page.size() > 0)
                return page.get(0);
            return null;
        }
        if(parentIsTD())
        {
            ETD td = getParentTD().getNextLinkTD();
            while(td != null)
            {
                if(td.size() > 0)
                    return td.get(0);
                td = td.getNextLinkTD();
            }
            return null;
        }
        return null;
    }
    /**
     * �������
     * @param panel EPanel
     */
    public void linkPanel(EPanel panel)
    {
        if(panel == null)
            return;
        //������ͬ�����
        if(getType() != panel.getType())
            return;
        setModify(true);
        if(parentIsPage())
        {
            setNextPageLinkPanel(panel);
            panel.setPreviousPageLinkPanel(this);
        }else if(parentIsTD())
        {
            setNextLinkPanel(panel);
            panel.setPreviousLinkPanel(this);
        }
        //�������(temp Ӧ�ô���������������EText)
        if(size() == 1)
        {
            IBlock block = get(0);
            if(block instanceof EText)
            {
                EText text = (EText)block;
                if(text.getLength() == 0)
                    text.onDelete(true);
            }
        }
        if(panel.size() == 1)
        {
            IBlock block = panel.get(0);
            if(block instanceof EText)
            {
                EText text = (EText)block;
                if(text.getLength() == 0)
                    text.onDelete(true);
            }
        }
    }
    /**
     * ɾ���س���
     */
    public void onDeleteEnter()
    {
        if(hasNextLinkPanel() || hasNextPageLinkPanel())
            return;
        EPanel panel = getNextPanelInLayer();
        //�������
        linkPanel(panel);
        if(getSelectedEnterModel() != null)
        {
            getSelectedEnterModel().getSelectList().remove(getSelectedEnterModel());
            getSelectedEnterModel().removeThis();
        }
    }
    /**
     * �Ƿ������������
     * @return boolean
     */
    public boolean hasLink()
    {
        if(parentIsTD())
        {
            if(hasNextLinkPanel())
                return true;
            if(this.hasPreviousLinkPanel())
                return true;
        }
        if(parentIsPage())
        {
            if (hasNextPageLinkPanel())
                return true;
            if(hasPreviousPageLinkPanel())
                return true;
        }
        return false;
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
            IBlock block = get(i);
            EComponent com = block.findObject(name,type);
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
            IBlock block = get(i);
            EComponent com = block.findGroup(group);
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
            IBlock block = get(i);
            String value = block.findGroupValue(group);
            if(value != null)
                return value;
        }
        return null;
    }
    /**
     * ճ�����
     * @param panel EPanel
     */
    public void pastePanel(EPanel panel)
    {
        setAlignment(panel.getAlignment());
        setParagraphForward(panel.getParagraphForward());
        setParagraphAfter(panel.getParagraphAfter());
        setSpaceBetween(panel.getSpaceBetween());
        setType(panel.getType());
        setRetractLeft(panel.getRetractLeft());
        setRetractRight(panel.getRetractRight());
        setRetractType(panel.getRetractType());
        setRetractWidth(panel.getRetractWidth());
        setElementEdit(panel.isElementEdit());
        for(int i = 0;i < panel.size();i++)
        {

            IBlock block = panel.get(i);
            //System.out.println("block type====="+block.getObjectType());
             //System.out.println("block value====="+block.getBlockValue());

            add(block.clone(this));
        }
        setModify(true);
    }
    /**
     * ��¡
     * @param td ETD
     * @return EPanel
     */
    public EPanel clone(ETD td)
    {
        EPanel panel = new EPanel(td);
        panel.setAlignment(getAlignment());
        panel.setParagraphForward(getParagraphForward());
        panel.setParagraphAfter(getParagraphAfter());
        panel.setSpaceBetween(getSpaceBetween());
        panel.setType(getType());
        panel.setRetractLeft(getRetractLeft());
        panel.setRetractRight(getRetractRight());
        panel.setRetractType(getRetractType());
        panel.setRetractWidth(getRetractWidth());
        panel.setElementEdit(isElementEdit());
        for(int i = 0;i < size();i++)
        {
            IBlock block = get(i);
            panel.add(block.clone(panel));
        }
        panel.setModify(true);
        return panel;
    }
    /**
     * ����
     */
    public void arrangement()
    {
        int index = 0;
        linkPanel();
        while(index < size())
        {
            get(index).arrangement();
            index ++;
        }
    }
    /**
     * �������
     */
    public void linkPanel()
    {
        EPanel nextPanel = parentIsPage()?
                           getNextPageLinkPanel():
                           getNextLinkPanel();
        while(nextPanel != null)
        {
            while (nextPanel.size() > 0)
            {
                IBlock block = nextPanel.get(0);
                if (block instanceof EText)
                {
                    EText text = (EText) block;
                    int stringSize = getDString().size();
                    String s = text.getString();
                    getDString().add(s);
                    text.indexMove(stringSize);
                    nextPanel.getDString().remove(0, s.length());
                    nextPanel.indexMove(text, -s.length());
                }
                add(block);
                nextPanel.remove(block);
            }
            EPanel n = nextPanel.parentIsPage() ?
                       nextPanel.getNextPageLinkPanel() :
                       nextPanel.getNextLinkPanel();
            nextPanel.removeThis();
            nextPanel = n;
        }
    }
    /**
     * �����༭
     * @return boolean
     */
    public boolean isLockEdit()
    {
        if(parentIsTD())
            return getParentTD().isLockEdit();
        return false;
    }
    /**
     * ����
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * �����Ա����
     * @return boolean
     */
    public boolean checkSexControl()
    {
        switch(getPM().getSexControl())
        {
        case 1:
            if(getSexControl() == 2)
                return false;
            return true;
        case 2:
            if(getSexControl() == 1)
                return false;
            return true;
        }
        return true;
    }
    /**
     * �����к�
     * @param id RowID
     */
    public void resetRowID(RowID id)
    {
        int rowID = 0;
        for(int i = 0;i < size();i++)
        {
            EComponent component = get(i);
            if(component instanceof EText)
            {
                EText text = (EText)component;
                int position = text.getPosition();
                if(position == 1 || position == 3)
                    rowID = id.getIndex();
                text.setRowID(rowID);
                continue;
            }
            if(component instanceof ETable)
            {
                ETable table = (ETable)component;
                table.resetRowID(id);
            }
        }
    }
}
