package com.dongyang.ui.edit;

import java.awt.Container;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TUIEditView;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TMovePane;
import com.dongyang.util.Log;
import com.dongyang.ui.TPanel;
import java.awt.Component;

public class TUIAdapter {
    public static final int SIZE = 4;
    public static final int TITLE_WIDTH = 50;
    public static final int TITLE_HEIGHT = 15;
    /**
     * ���
     */
    private TComponent component;
    /**
     * �����
     */
    private TMovePane screenPane;
    /**
     * ��������
     */
    private boolean screen;
    /**
     * �Ƿ�ѡ��
     */
    private boolean selected;
    /**
     * ���ƿ�
     */
    private Map panes;
    /**
     * ���ÿ��Ƶ����
     */
    private String listPanes;
    /**
     * �Ƿ������϶�
     */
    private boolean dragged;
    /**
     * UI�װ�������
     */
    private TUIBoardAdapter uiBoardAdapter;
    /**
     * ���ڵװ�
     */
    private boolean board;
    /**
     * ͷ�����ƶ�
     */
    private boolean titleMove;
    /**
     * ��־ϵͳ
     */
    private Log log;
    /**
     * ������
     * @param component TComponent
     */
    public TUIAdapter(TComponent component)
    {
        log = new Log();
        setComponent(component);
        panes = new HashMap();
    }
    /**
     * �������
     * @param component TComponent
     */
    public void setComponent(TComponent component)
    {
        this.component = component;
    }
    /**
     * �õ����
     * @return TComponent
     */
    public TComponent getComponent()
    {
        return component;
    }
    /**
     * ���������
     * @param screenPane TMovePane
     */
    public void setScreenPane(TMovePane screenPane)
    {
        this.screenPane = screenPane;
    }
    /**
     * �õ������
     * @return TMovePane
     */
    public TMovePane getScreenPane()
    {
        return screenPane;
    }
    /**
     * ���ÿ��Ƶ�
     * @param listPanes String
     */
    public void setListPanes(String listPanes)
    {
        this.listPanes = listPanes;
    }
    /**
     * �õ����Ƶ�
     * @return String
     */
    public String getListPanes()
    {
        return listPanes;
    }
    /**
     * ����ѡ��
     * @param selected boolean
     */
    public void setSelected(boolean selected)
    {
        if(selected == isSelected())
            return;
        this.selected = selected;
        if(selected)
        {
            //�����϶�����
            startDraggedScreenPane();
            if(isNoMove())
            {
                for (int i = 1; i <= 8; i++)
                    createPane(i);
                //ͷ�����ƶ�
                if (isTitleMove())
                    createTitleMovePane();
            }
            Container container = (Container)getComponent().callFunction("getParent");
            if(container != null)
                container.repaint();
            return;
        }
        endDraggedScreenPane();
        destroyPane();
        //�ͷ�ͷ���ƿ�
        destroyTitlePane();
        Container container = (Container)getComponent().callFunction("getParent");
        if(container != null)
            container.repaint();
    }
    /**
     * �Ƿ�ѡ��
     * @return boolean
     */
    public boolean isSelected()
    {
        return selected;
    }
    /**
     * �ܷ񲻿����ƶ����
     * @return boolean
     */
    public boolean isNoMove()
    {

        Container container = (Container)getComponent().callFunction("getParent");
        if(container == null)
            return true;
        if(container.getLayout() instanceof javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout)
            return false;
        return true;
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
     * ����UI�װ�������
     * @param uiBoardAdapter TUIBoardAdapter
     */
    public void setUIBoardAdapter(TUIBoardAdapter uiBoardAdapter)
    {
        this.uiBoardAdapter = uiBoardAdapter;
    }
    /**
     * �õ�UI�װ�������
     * @return TUIBoardAdapter
     */
    public TUIBoardAdapter getUIBoardAdapter()
    {
        return uiBoardAdapter;
    }
    /**
     * �����Ƿ���ڵװ�
     * @param board boolean
     */
    public void setBoard(boolean board)
    {
        out("begin board=" + board);
        if(isBoard() == board)
            return;
        this.board = board;
        if(board)
        {
            //��������ͷ�
            if(getUIBoardAdapter() != null)
                getUIBoardAdapter().destroy();
            //����UI�װ�������
            setUIBoardAdapter(new TUIBoardAdapter(this));
            //��ʼ��
            getUIBoardAdapter().onInit();
            out("end init UIBoardAdapter");
            return;
        }
        //�ͷ�
        getUIBoardAdapter().destroy();
        out("end");
    }
    /**
     * �Ƿ���ڵװ�
     * @return boolean
     */
    public boolean isBoard()
    {
        return board;
    }
    /**
     * ����ͷ�����ƶ�
     * @param titleMove boolean
     */
    public void setTitleMove(boolean titleMove)
    {
        this.titleMove = titleMove;
    }
    /**
     * �õ�ͷ�����ƶ�
     * @return boolean
     */
    public boolean isTitleMove()
    {
        return titleMove;
    }
    /**
     * �ͷ�
     */
    public void destroy()
    {
        //�ͷ�����
        destroyScreenPane();
        //�ͷſ��ƿ�
        destroyPane();
        //�ͷ�ͷ���ƿ�
        destroyTitlePane();
        Container container = (Container)getComponent().callFunction("getParent");
        if(container != null)
            container.repaint();
    }
    /**
     * �ͷ�����
     */
    public void destroyScreenPane()
    {
        if(getScreenPane() == null)
            return;
        Container container = (Container)getComponent().callFunction("getParent");
        if(container == null)
            return;
        container.remove(getScreenPane());
        setScreenPane(null);
    }
    /**
     * �ͷſ��ƿ�
     */
    public void destroyPane()
    {
        Container container = (Container)getComponent().callFunction("getParent");
        if(container == null)
            return;
        for(int i = 1;i <= 8;i++)
        {
            TMovePane pane = getPane(i + "_PANE");
            if(pane == null)
                continue;
            container.remove(pane);
            removePane(i + "_PANE");
        }
    }
    /**
     * �ͷ�ͷ���ƿ�
     */
    public void destroyTitlePane()
    {
        Container container = (Container)getComponent().callFunction("getParent");
        if(container == null)
            return;
        TMovePane pane = getPane("TITLE_PANE");
        if(pane == null)
            return;
        container.remove(pane);
        removePane("TITLE_PANE");
    }
    /**
     * ��������
     * @param screen boolean
     */
    public void setScreen(boolean screen)
    {
        if(screen == isScreen())
            return;
        this.screen = screen;
        if(screen)
            createScreenPane();
        else
            destroyScreenPane();
    }
    /**
     * �Ƿ��������
     * @return boolean
     */
    public boolean isScreen()
    {
        return screen;
    }
    /**
     * ��������
     */
    private void createScreenPane()
    {
        Container container = (Container)getComponent().callFunction("getParent");
        if(container == null)
            return;
        TMovePane pane = new TMovePane();
        pane.setUIAdapterIO(this);
        pane.setTag("SCREEN_PANE");
        pane.setMoveType(TMovePane.NO_MOVE);
        pane.addEntity(getComponent(),0,0);
        pane.setInStyle(true);
        pane.setLocation((Integer)getComponent().callFunction("getX"),
                         (Integer)getComponent().callFunction("getY"));
        pane.setSize((Integer)getComponent().callFunction("getWidth"),
                     (Integer)getComponent().callFunction("getHeight"));
        pane.addEventListener(pane.getTag() + "->" + TMovePane.MOUSE_CLICKED,this,"onScreeClicked");
        int index = getIndexComponent(container,getComponent());
        container.add(pane,index);
        pane.onInit();
        setScreenPane(pane);
    }
    public int getIndexComponent(Container container,TComponent component)
    {
        Component[] com = container.getComponents();
        for(int i = 0;i < com.length;i ++)
            if(com[i] == component)
                return i;
        return 0;
    }
    /**
     * �������Ƶ�
     * @param name String tag����
     * @param moveType int �ƶ�����
     * @param state int Я���齨��״̬
     * @param position int ������Сλ��
     * @param style int ��ʾ���
     * @param type int �����
     * @param x int X����
     * @param y int Y����
     * @param width int ���
     * @param height int �߶�
     */
    private void createPanel(String name,int moveType,int state,int position,
            int style,int type,int x,int y,int width,int height)
    {
        Container container = (Container)getComponent().callFunction("getParent");
        if(container == null)
            return;
        TMovePane pane = new TMovePane();
        pane.setUIAdapterIO(this);
        pane.setTag(name);
        pane.setMoveType(moveType);
        pane.addEntity(getComponent(),state,position);
        pane.setStyle(style);
        pane.setCursorType(type);
        pane.setLocation(x,y);
        pane.setSize(width,height);
        container.add(pane,0);
        pane.onInit();
        setPane(name,pane);
    }
    /**
     * ��������
     * @param index int
     */
    private void createPane(int index)
    {
        //���˿��Ƶ�
        if(getListPanes() != null)
            if(getListPanes().indexOf("" + index) == -1)
                return;
        int x = (Integer) getComponent().callFunction("getX");
        int y = (Integer) getComponent().callFunction("getY");
        int width = (Integer) getComponent().callFunction("getWidth");
        int height = (Integer)getComponent().callFunction("getHeight");
        switch(index)
        {
        case 1:
            createPanel(index + "_PANE", TMovePane.HEIGHT_MOVE, index, 2, 0, 0,
                        x + SIZE / 2,y - SIZE / 2,width - SIZE,SIZE);
            break;
        case 2:
            createPanel(index + "_PANE", TMovePane.HEIGHT_MOVE, index, 2, 0, 0,
                        x + SIZE / 2,y + height - SIZE / 2,width - SIZE,SIZE);
            break;
        case 3:
            createPanel(index + "_PANE", TMovePane.WIDTH_MOVE, index, 2, 0, 0,
                        x - SIZE / 2, y + SIZE / 2,SIZE,height - SIZE);
            break;
        case 4:
            createPanel(index + "_PANE", TMovePane.WIDTH_MOVE, index, 2, 0, 0,
                        x + width - SIZE / 2, y + SIZE / 2,SIZE,height - SIZE);
            break;
        case 5:
            createPanel(index + "_PANE", TMovePane.MOVE, index, 2, 2, 1,
                        x - SIZE / 2, y - SIZE / 2,SIZE,SIZE);
            break;
        case 6:
            createPanel(index + "_PANE", TMovePane.MOVE, index, 2, 2, 2,
                        x + width - SIZE / 2, y - SIZE / 2,SIZE,SIZE);
            break;
        case 7:
            createPanel(index + "_PANE", TMovePane.MOVE, index, 2, 2, 1,
                        x + width - SIZE / 2, y  + height - SIZE / 2,SIZE,SIZE);
            break;
        case 8:
            createPanel(index + "_PANE", TMovePane.MOVE, index, 2, 2, 2,
                        x - SIZE / 2, y  + height - SIZE / 2,SIZE,SIZE);
            break;
        }
    }
    /**
     * ����ͷ�����ƶ����
     */
    public void createTitleMovePane()
    {
        int x = (Integer) getComponent().callFunction("getX");
        int y = (Integer) getComponent().callFunction("getY");
        //int width = (Integer) getComponent().callFunction("getWidth");
        //int height = (Integer)getComponent().callFunction("getHeight");
        createPanel("TITLE_PANE", TMovePane.MOVE, 0, 0, 5, 0,
                    x, y - TITLE_HEIGHT - 1,TITLE_WIDTH,TITLE_HEIGHT);
    }
    /**
     * ���ÿ��ƿ�
     * @param tag String
     * @param pane TMovePane
     */
    public void setPane(String tag,TMovePane pane)
    {
        panes.put(tag,pane);
    }
    /**
     * �õ����ƿ�
     * @param tag String
     * @return TMovePane
     */
    public TMovePane getPane(String tag)
    {
        return (TMovePane)panes.get(tag);
    }
    /**
     * �Ƴ����ƿ�
     * @param tag String
     */
    public void removePane(String tag)
    {
        panes.remove(tag);
    }
    /**
     * �����϶�����
     */
    private void startDraggedScreenPane()
    {
        if(getScreenPane() == null)
            return;
        getScreenPane().setMoveType(TMovePane.MOVE);
    }
    /**
     * ֹͣ�϶�����
     */
    private void endDraggedScreenPane()
    {
        if(getScreenPane() == null)
            return;
        getScreenPane().setMoveType(TMovePane.NO_MOVE);
    }
    /**
     * �õ����������
     * @return TObject
     */
    public TObject getTObject()
    {
        TObject tobject = (TObject)getComponent().callFunction("getTObject");
        if(tobject == null)
            return null;
        return tobject;
    }
    /**
     * �õ��༭��ͼ
     * @return TUIEditView
     */
    public TUIEditView getEditView()
    {
        TObject tobject = getTObject();
        if(tobject == null)
            return null;
        TUIEditView view = (TUIEditView)tobject.getView();
        if(view == null)
            return null;
        return view;
    }
    /**
     * ���类���
     */
    public void onScreeClicked()
    {
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(view == null || tobject == null)
        {
            if(!isSelected())
                setSelected(true);
            return;
        }
        if(view.isKeyCtrl())
        {
            boolean selected = isSelected();
            setSelected(!selected);
            if(!selected)
                view.addSelected(tobject);
            else
                view.removeSelected(tobject);
            return;
        }
        if(!isSelected())
        {
            setSelected(true);
            view.setSelected(tobject);
        }
    }

    /**
     * �װ���갴��
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean onBoardPressed(int x,int y)
    {
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(view == null || tobject == null)
            return false;
        if(!view.isKeyCtrl())
            view.removeSelectedAll();
        TComponent com = tobject.getComponent();
        //�����TPanel��һ������Ǵ��ڱ���,������λ����Ҫ�ų�����ĸ߶�
        if(com != null && com instanceof TPanel)
        {
            TPanel panel = (TPanel)com;
            if(panel.getWorkPanel() != null)
                y = y - panel.getWorkPanelY();
        }
        //���������
        return view.createTComponent(tobject,x,y);
    }
    /**
     * �װ����̧��
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     */
    public void onBoardReleased(int x1,int y1,int x2,int y2)
    {
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(view == null || tobject == null)
            return;
        TComponent com = tobject.getComponent();
        //�����TPanel��һ������Ǵ��ڱ���,ѡȡ��Ҫ�ų�����ĸ߶�
        if(com != null && com instanceof TPanel)
        {
            TPanel panel = (TPanel)com;
            if(panel.getWorkPanel() != null)
            {
                int y = panel.getWorkPanelY();
                y1 = y1 - y;
                y2 = y2 - y;
            }
        }
        view.selectedArea(x1,y1,x2,y2,tobject);
        if(view.getSelectedCount() == 0)
            view.setSelected(getTObject());
    }
    /**
     * �װ屻���
     * @param x int
     * @param y int
     */
    public void onBoardClicked(int x,int y)
    {
        TUIEditView view = getEditView();
        if(view != null)
            if(view.isKeyCtrl())
                view.addSelected(getTObject());
            else
                view.setSelected(getTObject());
    }
    /**
     * ����X
     * @param x int
     * @param oldx int
     */
    public void setX(int x,int oldx)
    {
        if(isScreen() && getScreenPane() != null && getScreenPane().getX() != x)
            getScreenPane().setX(x);
        if(!isSelected())
            return;
        TMovePane pane = getPane("1_PANE");
        if(pane != null && pane.getX() - SIZE/2 != x)
            pane.setX(x + SIZE/2);
        pane = getPane("2_PANE");
        if(pane != null && pane.getX() - SIZE/2 != x)
            pane.setX(x + SIZE/2);
        pane = getPane("3_PANE");
        if(pane != null && pane.getX() + SIZE/2 != x)
            pane.setX(x - SIZE/2);
        pane = getPane("4_PANE");
        int width = (Integer)getComponent().callFunction("getWidth");
        if(pane != null && pane.getX() + SIZE/2 != x + width)
            pane.setX(x + width - SIZE/2);
        pane = getPane("5_PANE");
        if(pane != null && pane.getX() + SIZE/2 != x)
            pane.setX(x - SIZE/2);
        pane = getPane("6_PANE");
        if(pane != null && pane.getX() + SIZE/2 != x + width)
            pane.setX(x + width - SIZE/2);
        pane = getPane("7_PANE");
        if(pane != null && pane.getX() + SIZE/2 != x + width)
            pane.setX(x + width - SIZE/2);
        pane = getPane("8_PANE");
        if(pane != null && pane.getX() + SIZE/2 != x)
            pane.setX(x - SIZE/2);
        pane = getPane("TITLE_PANE");
        if(pane != null && pane.getX() + SIZE/2 != x)
            pane.setX(x);
        //֪ͨ��ͼX����ı�
        int difference = x - oldx;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedX(difference,tobject);
        //�޸�����
        if(tobject != null)
            tobject.setValue("X","" + x);
    }
    /**
     * ����Y
     * @param y int
     * @param oldy int
     */
    public void setY(int y,int oldy)
    {
        if(isScreen() && getScreenPane() != null && getScreenPane().getY() != y)
            getScreenPane().setY(y);
        TMovePane pane = getPane("1_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y)
            pane.setY(y - SIZE / 2);
        pane = getPane("2_PANE");
        int height = (Integer)getComponent().callFunction("getHeight");
        if(pane != null && pane.getY() + SIZE / 2 != y + height)
            pane.setY(y + height - SIZE / 2);
        pane = getPane("3_PANE");
        if(pane != null && pane.getY() - SIZE / 2 != y)
            pane.setY(y + SIZE / 2);
        pane = getPane("4_PANE");
        if(pane != null && pane.getY() - SIZE / 2 != y)
            pane.setY(y + SIZE / 2);
        pane = getPane("5_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y)
            pane.setY(y - SIZE / 2);
        pane = getPane("6_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y)
            pane.setY(y - SIZE / 2);
        pane = getPane("7_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y + height)
            pane.setY(y + height - SIZE / 2);
        pane = getPane("8_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y + height)
            pane.setY(y + height - SIZE / 2);
        pane = getPane("TITLE_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y + height)
            pane.setY(y - TITLE_HEIGHT - 1);

        //֪ͨ��ͼY����ı�
        int difference = y - oldy;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedY(difference,tobject);
        //�޸�����
        if(tobject != null)
            tobject.setValue("Y","" + y);
    }
    /**
     * ���ÿ��
     * @param width int
     * @param oldWidth int
     */
    public void setWidth(int width,int oldWidth)
    {
        if(isScreen() && getScreenPane() != null && getScreenPane().getWidth() != width)
            getScreenPane().setWidth(width);
        TMovePane pane = getPane("1_PANE");
        if(pane != null && pane.getWidth() + SIZE != width)
            pane.setWidth(width - SIZE);
        pane = getPane("2_PANE");
        if(pane != null && pane.getWidth() + SIZE != width)
            pane.setWidth(width - SIZE);
        pane = getPane("4_PANE");
        int x = (Integer)getComponent().callFunction("getX");
        if(pane != null && pane.getX() + SIZE / 2 != x + width)
            pane.setX(x + width - SIZE/2);
        pane = getPane("6_PANE");
        if(pane != null && pane.getX() + SIZE / 2 != x + width)
            pane.setX(x + width - SIZE/2);
        pane = getPane("7_PANE");
        if(pane != null && pane.getX() + SIZE / 2 != x + width)
            pane.setX(x + width - SIZE/2);

        //֪ͨ��ͼ��ȸı�
        int difference = width - oldWidth;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedWidth(difference,tobject);
        //�޸�����
        if(tobject != null)
            tobject.setValue("Width","" + width);
    }
    /**
     * ���ø߶�
     * @param height int
     * @param oldHeight int
     */
    public void setHeight(int height,int oldHeight)
    {
        if(isScreen() && getScreenPane() != null && getScreenPane().getHeight() != height)
            getScreenPane().setHeight(height);
        TMovePane pane = getPane("2_PANE");
        int y = (Integer)getComponent().callFunction("getY");
        if(pane != null && pane.getY() + SIZE / 2 != y + height)
            pane.setY(y + height - SIZE / 2);
        pane = getPane("3_PANE");
        if(pane != null && pane.getHeight() + SIZE != height)
            pane.setHeight(height - SIZE);
        pane = getPane("4_PANE");
        if(pane != null && pane.getHeight() + SIZE != height)
            pane.setHeight(height - SIZE);
        pane = getPane("7_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y + height)
            pane.setY(y + height - SIZE / 2);
        pane = getPane("8_PANE");
        if(pane != null && pane.getY() + SIZE / 2 != y + height)
            pane.setY(y + height - SIZE / 2);
        //֪ͨ��ͼ�߶�����ı�
        int difference = height - oldHeight;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedHeight(difference,tobject);
        //�޸�����
        if(tobject != null)
            tobject.setValue("Height","" + height);
    }
    /**
     * �齨�޸�Tag
     */
    public void modifiedTag()
    {
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(view == null || tobject == null)
            return;
        view.modifiedTag(tobject);
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
