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
     * 组件
     */
    private TComponent component;
    /**
     * 屏风块
     */
    private TMovePane screenPane;
    /**
     * 存在屏风
     */
    private boolean screen;
    /**
     * 是否选中
     */
    private boolean selected;
    /**
     * 控制块
     */
    private Map panes;
    /**
     * 设置控制点个数
     */
    private String listPanes;
    /**
     * 是否正在拖动
     */
    private boolean dragged;
    /**
     * UI底板适配器
     */
    private TUIBoardAdapter uiBoardAdapter;
    /**
     * 存在底板
     */
    private boolean board;
    /**
     * 头控制移动
     */
    private boolean titleMove;
    /**
     * 日志系统
     */
    private Log log;
    /**
     * 构造器
     * @param component TComponent
     */
    public TUIAdapter(TComponent component)
    {
        log = new Log();
        setComponent(component);
        panes = new HashMap();
    }
    /**
     * 设置组件
     * @param component TComponent
     */
    public void setComponent(TComponent component)
    {
        this.component = component;
    }
    /**
     * 得到组件
     * @return TComponent
     */
    public TComponent getComponent()
    {
        return component;
    }
    /**
     * 设置屏风块
     * @param screenPane TMovePane
     */
    public void setScreenPane(TMovePane screenPane)
    {
        this.screenPane = screenPane;
    }
    /**
     * 得到屏风块
     * @return TMovePane
     */
    public TMovePane getScreenPane()
    {
        return screenPane;
    }
    /**
     * 设置控制点
     * @param listPanes String
     */
    public void setListPanes(String listPanes)
    {
        this.listPanes = listPanes;
    }
    /**
     * 得到控制点
     * @return String
     */
    public String getListPanes()
    {
        return listPanes;
    }
    /**
     * 设置选中
     * @param selected boolean
     */
    public void setSelected(boolean selected)
    {
        if(selected == isSelected())
            return;
        this.selected = selected;
        if(selected)
        {
            //启动拖动功能
            startDraggedScreenPane();
            if(isNoMove())
            {
                for (int i = 1; i <= 8; i++)
                    createPane(i);
                //头控制移动
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
        //释放头控制块
        destroyTitlePane();
        Container container = (Container)getComponent().callFunction("getParent");
        if(container != null)
            container.repaint();
    }
    /**
     * 是否选中
     * @return boolean
     */
    public boolean isSelected()
    {
        return selected;
    }
    /**
     * 受否不可以移动组件
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
     * 设置是否真正拖动
     * @param dragged boolean
     */
    public void setDragged(boolean dragged)
    {
        this.dragged = dragged;
    }
    /**
     * 是否真正拖动
     * @return boolean
     */
    public boolean isDragged()
    {
        return dragged;
    }
    /**
     * 设置UI底板适配器
     * @param uiBoardAdapter TUIBoardAdapter
     */
    public void setUIBoardAdapter(TUIBoardAdapter uiBoardAdapter)
    {
        this.uiBoardAdapter = uiBoardAdapter;
    }
    /**
     * 得到UI底板适配器
     * @return TUIBoardAdapter
     */
    public TUIBoardAdapter getUIBoardAdapter()
    {
        return uiBoardAdapter;
    }
    /**
     * 设置是否存在底板
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
            //如果存在释放
            if(getUIBoardAdapter() != null)
                getUIBoardAdapter().destroy();
            //创建UI底板适配器
            setUIBoardAdapter(new TUIBoardAdapter(this));
            //初始化
            getUIBoardAdapter().onInit();
            out("end init UIBoardAdapter");
            return;
        }
        //释放
        getUIBoardAdapter().destroy();
        out("end");
    }
    /**
     * 是否存在底板
     * @return boolean
     */
    public boolean isBoard()
    {
        return board;
    }
    /**
     * 设置头控制移动
     * @param titleMove boolean
     */
    public void setTitleMove(boolean titleMove)
    {
        this.titleMove = titleMove;
    }
    /**
     * 得到头控制移动
     * @return boolean
     */
    public boolean isTitleMove()
    {
        return titleMove;
    }
    /**
     * 释放
     */
    public void destroy()
    {
        //释放屏风
        destroyScreenPane();
        //释放控制块
        destroyPane();
        //释放头控制块
        destroyTitlePane();
        Container container = (Container)getComponent().callFunction("getParent");
        if(container != null)
            container.repaint();
    }
    /**
     * 释放屏风
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
     * 释放控制块
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
     * 释放头控制块
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
     * 设置屏风
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
     * 是否存在屏风
     * @return boolean
     */
    public boolean isScreen()
    {
        return screen;
    }
    /**
     * 创建屏风
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
     * 创建控制点
     * @param name String tag名称
     * @param moveType int 移动类型
     * @param state int 携带组建的状态
     * @param position int 控制最小位置
     * @param style int 显示风格
     * @param type int 光标风格
     * @param x int X坐标
     * @param y int Y坐标
     * @param width int 宽度
     * @param height int 高度
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
     * 创建控制
     * @param index int
     */
    private void createPane(int index)
    {
        //过滤控制点
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
     * 创建头控制移动面板
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
     * 设置控制块
     * @param tag String
     * @param pane TMovePane
     */
    public void setPane(String tag,TMovePane pane)
    {
        panes.put(tag,pane);
    }
    /**
     * 得到控制块
     * @param tag String
     * @return TMovePane
     */
    public TMovePane getPane(String tag)
    {
        return (TMovePane)panes.get(tag);
    }
    /**
     * 移出控制块
     * @param tag String
     */
    public void removePane(String tag)
    {
        panes.remove(tag);
    }
    /**
     * 启动拖动功能
     */
    private void startDraggedScreenPane()
    {
        if(getScreenPane() == null)
            return;
        getScreenPane().setMoveType(TMovePane.MOVE);
    }
    /**
     * 停止拖动功能
     */
    private void endDraggedScreenPane()
    {
        if(getScreenPane() == null)
            return;
        getScreenPane().setMoveType(TMovePane.NO_MOVE);
    }
    /**
     * 得到配置类对象
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
     * 得到编辑试图
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
     * 屏风被点击
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
     * 底板鼠标按键
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
        //如果是TPanel有一种情况是存在标题,创建的位置是要排出标题的高度
        if(com != null && com instanceof TPanel)
        {
            TPanel panel = (TPanel)com;
            if(panel.getWorkPanel() != null)
                y = y - panel.getWorkPanelY();
        }
        //创建新组件
        return view.createTComponent(tobject,x,y);
    }
    /**
     * 底板鼠标抬键
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
        //如果是TPanel有一种情况是存在标题,选取是要排出标题的高度
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
     * 底板被点击
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
     * 设置X
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
        //通知视图X坐标改变
        int difference = x - oldx;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedX(difference,tobject);
        //修改属性
        if(tobject != null)
            tobject.setValue("X","" + x);
    }
    /**
     * 设置Y
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

        //通知视图Y坐标改变
        int difference = y - oldy;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedY(difference,tobject);
        //修改属性
        if(tobject != null)
            tobject.setValue("Y","" + y);
    }
    /**
     * 设置宽度
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

        //通知视图宽度改变
        int difference = width - oldWidth;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedWidth(difference,tobject);
        //修改属性
        if(tobject != null)
            tobject.setValue("Width","" + width);
    }
    /**
     * 设置高度
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
        //通知视图高度坐标改变
        int difference = height - oldHeight;
        TUIEditView view = getEditView();
        TObject tobject = getTObject();
        if(isDragged() && difference != 0 && view != null && tobject != null)
            view.draggedHeight(difference,tobject);
        //修改属性
        if(tobject != null)
            tobject.setValue("Height","" + height);
    }
    /**
     * 组建修改Tag
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
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        log.out(text);
    }

    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text, boolean debug) {
        log.out(text, debug);
    }

    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        log.err(text);
    }
}
