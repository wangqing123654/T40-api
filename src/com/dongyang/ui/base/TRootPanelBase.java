package com.dongyang.ui.base;

import com.dongyang.ui.TPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Point;
import com.dongyang.ui.TWindow;
import com.dongyang.ui.TComponent;
import java.awt.AWTEvent;


/**
 *
 * <p>Title: �������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.17
 * @version 1.0
 */
public class TRootPanelBase extends TPanel
{
    private boolean isMDISheet;
    /**
     * ���ڹرհ�ť
     */
    private boolean hasCloseButton = true;
    /**
     * ����ڹرհ�ť��
     */
    private boolean mouseInCloseButton;
    /**
     * ����Xλ��
     */
    private int oldX;
    /**
     * ����Yλ��
     */
    private int oldY;
    /**
     * ����Wλ��
     */
    private int oldWx;
    /**
     * ����Hλ��
     */
    private int oldHy;
    /**
     * �ƶ�����
     */
    private int movedType;
    /**
     * �Ƿ��Ǵ���
     */
    private boolean isWindow = true;
    /**
     * ������
     */
    public TRootPanelBase()
    {
    }
    /**
     * �����ڲ��ߴ�
     * @return Insets
     */
    public Insets getInsets() {
        return new Insets(21,3,3,3);
    }
    /**
     * ���ô��ڹرհ�ť
     * @param hasCloseButton boolean
     */
    public void setHasCloseButton(boolean hasCloseButton)
    {
        this.hasCloseButton = hasCloseButton;
    }
    /**
     * �Ƿ���ڹرհ�ť
     * @return boolean
     */
    public boolean getHasCloseButton()
    {
        return hasCloseButton;
    }
    /**
     * �����Ƿ���Window
     * @param isWindow boolean
     */
    public void setIsWindow(boolean isWindow)
    {
        this.isWindow = isWindow;
    }
    /**
     * �Ƿ���Window
     * @return boolean
     */
    public boolean isWindow()
    {
        return isWindow;
    }
    /**
     * �õ��϶�λ��
     * @param x int
     * @param y int
     * @return int
     */
    public int getMovedType(int x,int y)
    {
        if(getHasCloseButton())
            if(x > getWidth() - 20 && x < getWidth() - 4 &&
               y > 3 && y < 19)
                return 10;

        if(x <= 3 && y <= 3)
            return 5;
        if(x > getWidth() - 4 && y <= 3)
            return 6;
        if(x <= 3 && y > getHeight() - 4)
            return 8;
        if(x > getWidth() - 4 && y > getHeight() - 4)
            return 7;
        if(x <= 3)
            return 3;
        if(y <= 3)
            return 1;
        if(x > getWidth() - 4)
            return 4;
        if(y > getHeight() - 4)
            return 2;
        if(y < 20)
            return 9;
        return 0;
    }
    /**
     * ���ù��
     * @param type int
     */
    private void checkCursor(int type)
    {
        switch(type)
        {
        case 1:
        case 2:
            setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
            break;
        case 3:
        case 4:
            setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
            break;
        case 5:
        case 7:
            setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
            break;
        case 6:
        case 8:
            setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
            break;
        default:
            setCursor(new Cursor(0));
        }
    }
    /**
     * ����ƶ�
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        int type = getMovedType(x,y);
        checkCursor(type);
        if(type == 10)
        {
            if(!mouseInCloseButton)
            {
                mouseInCloseButton = true;
                repaint(getWidth() - 20, 3, 16, 16);
            }
            return;
        }
        if(mouseInCloseButton)
        {
            mouseInCloseButton = false;
            repaint(getWidth() - 20, 3, 16, 16);
        }
    }
    /**
     * �õ����
     * @return Cursor
     */
    public Cursor getCursor()
    {
        Point p = getMousePosition();
        Insets insets = getInsets();
        if(p == null || insets == null)
            return super.getCursor();
        if(p.x > insets.left && p.x < getWidth() - insets.right &&
           p.y > insets.top && p.y < getHeight() - insets.bottom)
            return new Cursor(0);
        return super.getCursor();
    }
    /**
     * ����뿪
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        if(mouseInCloseButton)
        {
            mouseInCloseButton = false;
            repaint(getWidth() - 20, 3, 16, 16);
        }
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        if(isMDISheet())
            onSheetShow();
        if(e.getButton() != 1)
            return;
        int x = e.getX();
        int y = e.getY();
        movedType = getMovedType(x,y);
        if(movedType == 0)
            return;
        oldX = e.getX();
        oldY = e.getY();
        oldWx = getWidth() - e.getX();
        oldHy = getHeight() - e.getY();
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        if(movedType == 0)
            return;
        int x = e.getX();
        int y = e.getY();
        int moveTypeNow = getMovedType(x,y);
        if(moveTypeNow == 10 && movedType == 10)
            onCloseWindow();
        if(moveTypeNow != 10 && mouseInCloseButton)
        {
            mouseInCloseButton = false;
            repaint();
        }
        movedType = 0;
    }
    /**
     * ����϶�
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        int x = e.getX() - oldX;
        int y = e.getY() - oldY;
        switch(movedType)
        {
        case 1:
            if(getHeight() - y < 23)
                y = getHeight() - 23;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setY$(y);
                window.setHeight$(-y);
                break;
            }
            setY$(y);
            setHeight$(-y);
            break;
        case 2:
            int h = e.getY() + oldHy;
            if(h < 23)
                h = 23;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setHeight(h);
                break;
            }
            setHeight(h);
            break;
        case 3:
            if(getWidth() - x < 100)
                x = getWidth() - 100;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setX$(x);
                window.setWidth$(-x);
                break;
            }
            setX$(x);
            setWidth$(-x);
            break;
        case 4:
            int w = e.getX() + oldWx;
            if(w < 100)
                w = 100;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setWidth(w);
                break;
            }
            setWidth(w);
            break;
        case 5:
            if(getWidth() - x < 100)
                x = getWidth() - 100;
            if(getHeight() - y < 23)
                y = getHeight() - 23;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setY$(y);
                window.setHeight$(-y);
                window.setX$(x);
                window.setWidth$(-x);
                break;
            }
            setY$(y);
            setHeight$(-y);
            setX$(x);
            setWidth$(-x);
            break;
        case 6:
            if(getHeight() - y < 23)
                y = getHeight() - 23;
            w = e.getX() + oldWx;
            if(w < 100)
                w = 100;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setY$(y);
                window.setHeight$(-y);
                window.setWidth(w);
                break;
            }
            setY$(y);
            setHeight$(-y);
            setWidth(w);
            break;
        case 7:
            w = e.getX() + oldWx;
            if(w < 100)
                w = 100;
            h = e.getY() + oldHy;
            if(h < 23)
                h = 23;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setWidth(w);
                window.setHeight(h);
                break;
            }
            setWidth(w);
            setHeight(h);
            break;
        case 8:
            if(getWidth() - x < 100)
                x = getWidth() - 100;
            h = e.getY() + oldHy;
            if(h < 23)
                h = 23;
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setX$(x);
                window.setWidth$(-x);
                window.setHeight(h);
                break;
            }
            setX$(x);
            setWidth$(-x);
            setHeight(h);
            break;
        case 9:
            if(isWindow())
            {
                TWindow window = getWindow();
                if(window == null)
                    break;
                window.setX$(x);
                window.setY$(y);
                break;
            }
            setX$(x);
            setY$(y);
            break;
        case 10:
            if(getMovedType(e.getX(),e.getY()) == 10)
            {
                if (!mouseInCloseButton)
                {
                    mouseInCloseButton = true;
                    repaint();
                }
            }else
            {
                if (mouseInCloseButton)
                {
                    mouseInCloseButton = false;
                    repaint();
                }
            }
            return;
        default:
            return;
        }
    }
    /**
     * �õ�Window
     * @return TWindow
     */
    public TWindow getWindow()
    {
        TComponent com = getParentTComponent();
        while(com != null && !(com instanceof TWindow))
            com = (TComponent)com.callFunction("getParentTComponent");
        return (TWindow)com;
    }
    /**
     * �رմ���
     */
    public void onCloseWindow()
    {
        if(!isWindow())
            return;
        TWindow window = getWindow();
        if(window != null)
        {
            window.callMessage("onExit");
            return;
        }
        exeAction(getCloseClickedAction());
    }
    /**
     * ���õ�������
     * @param action String
     */
    public void setCloseClickedAction(String action)
    {
        actionList.setAction("closeClickedAction",action);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getCloseClickedAction()
    {
        return actionList.getAction("closeClickedAction");
    }
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        int w = getWidth();
        int h = getHeight();
        g.setColor(new Color(42,102,201));
        g.drawRect(0,0,w - 1,h - 1);
        g.drawRect(1,1,w-3,h-3);
        g.drawRect(2,2,w-5,h-5);
        g.setColor(new Color(196,219,249));
        g.drawLine(3,2,w-4,2);
        g.drawLine(3,h-3,w-4,h-3);
        g.drawLine(2,3,2,h-4);
        g.drawLine(w-3,3,w-3,h-4);
        g.setColor(new Color(42,102,201));
        g.fillRect(3,3,w-6,17);
        g.setColor(new Color(255,255,255));
        if("en".equals(getLanguage()) && getEnTitle() != null && getEnTitle().length() > 0)
        {
            g.setFont(new Font("����",1,12));
            Graphics g1 = g.create(3,3,w-19,17);
            g1.drawString(getEnTitle(), 2, 12);
        }else if(getTitle() != null)
        {
            g.setFont(new Font("����",1,12));
            Graphics g1 = g.create(3,3,w-19,17);
            g1.drawString(getTitle(), 2, 12);
        }
        //���ư�ť
        paintCloseButton(g);
    }
    /**
     * ���ƹرհ�ť
     * @param g Graphics
     */
    public void paintCloseButton(Graphics g)
    {
        g.setColor(new Color(255,255,255));
        if(mouseInCloseButton)
        {
            g.setColor(new Color(0, 0, 128));
            g.drawRect(getWidth() - 20, 3, 16, 16);
            g.setColor(new Color(255, 238, 194));
            g.fillRect(getWidth() - 19, 4, 15, 15);
            g.setColor(new Color(0,0,0));
        }
        int x = getWidth() - 12;
        int y = 12;

        g.drawLine(x-3,y-3,x+3,y+3);
        g.drawLine(x-2,y-3,x+4,y+3);
        g.drawLine(x+3,y-3,x-3,y+3);
        g.drawLine(x+4,y-3,x-2,y+3);
    }
    public void setIsMDISheet(boolean isMDISheet)
    {
        this.isMDISheet = isMDISheet;
    }
    public boolean isMDISheet()
    {
        return isMDISheet;
    }
    /**
     * sheet������ʾ��ǰ��
     */
    public void onSheetShow()
    {
        TPanel panel = (TPanel)getParentComponent();
        if(panel == null)
            return;
        panel.remove(this);
        panel.add(this,0);
        repaint();
    }
}
