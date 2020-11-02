package com.dongyang.ui.edit;

import com.dongyang.ui.TComponent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.ui.event.TMouseMotionListener;
import java.awt.event.MouseEvent;
import com.dongyang.ui.TMovePane;
import java.awt.Container;
import com.dongyang.util.Log;

public class TUIBoardAdapter {
    /**
     * UI������
     */
    private TUIAdapter uiAdapter;
    /**
     * �Ƿ��϶���
     */
    private boolean isMove;
    /**
     * �Ƿ������϶�(λ���иı�)
     */
    private boolean dragged;
    /**
     * ѡ���
     */
    private TMovePane selectPane;
    /**
     * ����Xλ��
     */
    private int oldX;
    /**
     * ����Yλ��
     */
    private int oldY;
    /**
     * �Ƿ��������װ��
     */
    private boolean isCreateTComponent;
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * ������
     * @param uiAdapter TUIAdapter
     */
    public TUIBoardAdapter(TUIAdapter uiAdapter)
    {
        log = new Log();
        setUIAdapter(uiAdapter);
    }
    /**
     * ����UI������
     * @param uiAdapter TUIAdapter
     */
    public void setUIAdapter(TUIAdapter uiAdapter)
    {
        this.uiAdapter = uiAdapter;
    }
    /**
     * �õ�UI������
     * @return TUIAdapter
     */
    public TUIAdapter getUIAdapter()
    {
        return uiAdapter;
    }
    /**
     * ����ѡ���
     * @param selectPane TMovePane
     */
    public void setSelectPane(TMovePane selectPane)
    {
        this.selectPane = selectPane;
    }
    /**
     * �õ�ѡ���
     * @return TMovePane
     */
    public TMovePane getSelectPane()
    {
        return selectPane;
    }
    /**
     * �õ�����
     * @return Container
     */
    public Container getContainer()
    {
        TComponent component = getComponent();
        if(component == null)
            return null;
        if(!(component instanceof Container))
            return null;
        return (Container)component;
    }
    /**
     * ����ѡ���
     */
    public void createSelectPane()
    {
        Container container = getContainer();
        if(container == null)
            return;
        setSelectPane(new TMovePane());
        getSelectPane().setStyle(1);
        container.add(getSelectPane(),0);
        container.repaint();
    }
    /**
     * ɾ��ѡ���
     */
    public void deleteSelectPane()
    {
        if(getSelectPane() == null)
            return;
        Container container = getContainer();
        if(container == null)
            return;
        container.remove(getSelectPane());
        container.repaint();
        setSelectPane(null);
    }
    /**
     * �ƶ�ѡ�п�
     * @param x int
     * @param y int
     */
    public void moveSelectPane(int x,int y)
    {
        if(getSelectPane() == null)
            return;
        int x1 = oldX < x?oldX:x;
        int y1 = oldY < y?oldY:y;
        int w = oldX < x?x - oldX:oldX - x;
        int h = oldY < y?y - oldY:oldY - y;
        getSelectPane().setLocation(x1,y1);
        getSelectPane().setSize(w,h);
    }
    /**
     * �õ����
     * @return TComponent
     */
    public TComponent getComponent()
    {
        if(getUIAdapter() == null)
            return null;
        return getUIAdapter().getComponent();
    }
    /**
     * �õ�X
     * @return int
     */
    public int getX()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getX");
    }
    /**
     * �õ�Y
     * @return int
     */
    public int getY()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getY");
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getWidth");
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        TComponent component = getComponent();
        if(component == null)
            return 0;
        return (Integer)component.callFunction("getHeight");
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {
        out("begin");
        TComponent component = getComponent();
        if(component == null)
            return;
        component.callFunction("initListeners");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_PRESSED,this,"onMousePressed");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_RELEASED,this,"onMouseReleased");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_ENTERED,this,"onMouseEntered");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_EXITED,this,"onMouseExited");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_DRAGGED,this,"onMouseDragged");
        component.callFunction("addEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_MOVED,this,"onMouseMoved");
        out("end");
    }
    /**
     * �ͷ�
     */
    public void destroy()
    {
        out("begin");
        TComponent component = getComponent();
        if(component == null)
            return;
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_PRESSED,this,"onMousePressed");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_PRESSED,this,"onMousePressed");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_RELEASED,this,"onMouseReleased");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_ENTERED,this,"onMouseEntered");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseListener.MOUSE_EXITED,this,"onMouseExited");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_DRAGGED,this,"onMouseDragged");
        component.callFunction("removeEventListener",component.getTag() + "->" +
                               TMouseMotionListener.MOUSE_MOVED,this,"onMouseMoved");
        out("end");
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        out("begin");
        if(e.getButton() != 1)
            return;
        oldX = e.getX();
        oldY = e.getY();
        if(getUIAdapter().onBoardPressed(oldX,oldY))
        {
            isCreateTComponent = true;
            out("end onBoardPressed true");
            return;
        }
        isMove = true;
        setDragged(false);
        out("end");
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        if(isCreateTComponent)
        {
            isCreateTComponent = false;
            return;
        }
        if(isDragged())
        {
            setDragged(false);
            deleteSelectPane();
            int x1 = oldX < e.getX()?oldX:e.getX();
            int y1 = oldY < e.getY()?oldY:e.getY();
            int x2 = oldX < e.getX()?e.getX():oldX;
            int y2 = oldY < e.getY()?e.getY():oldY;
            getUIAdapter().onBoardReleased(x1,y1,x2,y2);
            return;
        }
        setDragged(false);
        if(getWidth() - e.getX() > 0 && getHeight() - e.getY() > 0 && getUIAdapter() != null)
            getUIAdapter().onBoardClicked(e.getX(),e.getY());
    }
    /**
     * ������
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
    }
    /**
     * ����뿪
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
    }
    /**
     * ����϶�
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        if(!isMove || isCreateTComponent)
            return;
        //����ѡ���
        if(!isDragged())
            createSelectPane();
        //�ƶ�ѡ���
        moveSelectPane(e.getX(),e.getY());
        //���������϶�
        setDragged(true);
    }
    /**
     * ����ƶ�
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }
    /**
     * �����Ƿ������϶�
     * @param dragged boolean
     */
    public void setDragged(boolean dragged)
    {
        this.dragged = dragged;
    }
    /**
     * �Ƿ������϶�
     * @return boolean
     */
    public boolean isDragged()
    {
        return dragged;
    }
    /**
     * ��־���
     * @param text String ��־����
     */
    public void out(String text) {
        log.out(text);
    }

    /**
     * ��־���
     * @param text String ��־����
     * @param debug boolean true ǿ����� false ��ǿ�����
     */
    public void out(String text, boolean debug) {
        log.out(text, debug);
    }

    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        log.err(text);
    }
}
