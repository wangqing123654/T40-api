package com.dongyang.ui.base;

import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.util.Vector;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.ui.TLabel;
import com.dongyang.ui.TComponent;
import com.dongyang.util.StringTool;
import java.awt.Container;

public class TMovePaneBase extends TLabel{
    /**
     * 鼠标点击
     */
    public static final String MOUSE_CLICKED = "mouseClicked";
    /**
     * 不能移动
     */
    public static final int NO_MOVE = 0;
    /**
     * 左右横向移动
     */
    public static final int WIDTH_MOVE = 1;
    /**
     * 上下纵向移动
     */
    public static final int HEIGHT_MOVE = 2;
    /**
     * 任意移动
     */
    public static final int MOVE = 3;
    /**
     * 移动类型
     */
    private int moveType = MOVE;
    /**
     * 光标类型
     */
    private int cursorType = 0;
    /**
     * 鼠标旧X位置
     */
    private int oldX;
    /**
     * 鼠标旧Y位置
     */
    private int oldY;
    /**
     * 是否拖动中
     */
    private boolean isMove;
    /**
     * 是否真正拖动(位置有改变)
     */
    private boolean dragged;
    /**
     * 携带实体集
     */
    private Vector entitys;
    /**
     * UI适配器
     */
    private TUIAdapter uiAdapter;
    /**
     * 风格
     */
    private int style;

    /**
     * 进入风格
     */
    private boolean inStyle;
    /**
     * ui状态
     */
    private int uiState;
    /**
     * 双击移动类型
     */
    private int doubleClickType;
    /**
     * 旧位置
     */
    private Point oldPoint;
    /**
     * 移动父类位置
     */
    private boolean moveParent;
    /**
     * 调整父类尺寸
     */
    private boolean resizeParent;
    public class Entity
    {
        /**
         * 顶点
         */
        public static final int STATE = 0;
        /**
         * 中点
         */
        public static final int MID  = 1;
        /**
         * 终点
         */
        public static final int END  = 2;
        /**
         * 标签
         */
        private String tag;
        /**
         * 携带实体
         */
        private TComponent com;
        /**
         * 状态
         */
        private int state;
        /**
         * 位置
         */
        private int position;
        /**
         * 构造器
         * @param com TComponent 组件
         * @param state int 联动状态
         * @param position int 位置
         */
        public Entity(TComponent com,int state,int position)
        {
            setCom(com);
            setState(state);
            setPosition(position);
        }
        /**
         * 构造器
         * @param tag String 组件标签
         * @param state int 联动状态
         * @param position int 位置
         */
        public Entity(String tag,int state,int position)
        {
            setTag(tag);
            setState(state);
            setPosition(position);
        }
        /**
         * 设置标签
         * @param tag String
         */
        public void setTag(String tag)
        {
            this.tag = tag;
        }
        /**
         * 得到标签
         * @return String
         */
        public String getTag()
        {
            return tag;
        }
        /**
         * 设置携带实体
         * @param com TComponent
         */
        public void setCom(TComponent com)
        {
            this.com = com;
        }
        /**
         * 得到携带实体
         * @return TComponent
         */
        public TComponent getCom()
        {
            return com;
        }
        /**
         * 设置状态
         * @param state int
         */
        public void setState(int state)
        {
            this.state = state;
        }
        /**
         * 得到状态
         * @return int
         */
        public int getState()
        {
            return state;
        }
        /**
         * 设置位置
         * @param position int
         */
        public void setPosition(int position)
        {
            this.position = position;
        }
        /**
         * 得到位置
         * @return int
         */
        public int getPosition()
        {
            return position;
        }
    }
    /**
     * 构造器
     */
    public TMovePaneBase()
    {
        //初始化实体
        entitys = new Vector();
    }
    /**
     * 初始化
     */
    public void onInit()
    {
        super.onInit();
    }
    /**
     * 携带实体个数
     * @return int
     */
    public int getEntitySize()
    {
        return entitys.size();
    }
    /**
     *
     * @param data String
     */
    public void setEntityData(String data)
    {
        if(data == null)
            return;
        removeEntityAll();
        String list[] = StringTool.parseLine(data,";");
        for(int i = 0;i < list.length;i++)
        {
            if(list[i].length() == 0)
                continue;
            String s[] = StringTool.parseLine(list[i],",");
            if(s[0].length() == 0)
                continue;
            int state = 0;
            if(s.length > 1)
                state = StringTool.getInt(s[1]);
            addEntity(s[0],state);
        }
    }
    /**
     * 增加携带实体
     * @param com TComponent 组件
     * @param state int 状态
     */
    public void addEntity(TComponent com,int state)
    {
        addEntity(com,state,Entity.MID);
    }
    /**
     * 增加携带实体
     * @param com TComponent 组件
     * @param state int 状态
     * @param position int
     */
    public void addEntity(TComponent com,int state,int position)
    {
        Entity entity = new Entity(com,state,position);
        entitys.add(entity);
    }
    /**
     * 增加携带实体
     * @param tag String 组件标签
     * @param state int 状态
     */
    public void addEntity(String tag,int state)
    {
        addEntity(tag,state,Entity.MID);
    }
    /**
     * 增加携带实体
     * @param tag String 组件标签
     * @param state int 状态
     * @param position int
     */
    public void addEntity(String tag,int state,int position)
    {
        Entity entity = new Entity(tag,state,position);
        entitys.add(entity);
    }
    /**
     * 删除携带实体
     * @param index int 位置
     */
    public void removeEntity(int index)
    {
        entitys.remove(index);
    }
    /**
     * 删除携带实体
     * @param com TComponent 组件成员
     */
    public void removeEntity(TComponent com)
    {
        int index = getEntityIndex(com);
        if(index == -1)
            return;
        entitys.remove(index);
    }
    /**
     * 删除全部携带实体
     */
    public void removeEntityAll()
    {
        entitys = new Vector();
    }
    /**
     * 查找实体位置
     * @param com TComponent 组件
     * @return int 位置
     */
    public int getEntityIndex(TComponent com)
    {
        int count = getEntitySize();
        for(int index = 0;index < count;index ++)
        {
            Entity entity = getEntity(index);
            if(entity.getCom() == com)
                return index;
        }
        return -1;
    }
    /**
     * 得到实体成员
     * @param index int 位置
     * @return Entity
     */
    public Entity getEntity(int index)
    {
        return (Entity)entitys.get(index);
    }
    /**
     * 设置移动类型
     * @param moveType int
     */
    public void setMoveType(int moveType)
    {
        this.moveType = moveType;
    }
    /**
     * 得到移动类型
     * @return int
     */
    public int getMoveType()
    {
        return moveType;
    }
    /**
     * 设置光标类型
     * @param cursorType int 1 左上右下 2右上左下
     */
    public void setCursorType(int cursorType)
    {
        this.cursorType = cursorType;
    }
    /**
     * 得到光标类型
     * @return int
     */
    public int getCursorType()
    {
        return cursorType;
    }
    /**
     * 设置风格
     * @param style int
     */
    public void setStyle(int style)
    {
        this.style = style;
        repaint();
    }
    /**
     * 得到风格
     * @return int
     */
    public int getStyle()
    {
        return style;
    }
    /**
     * 设置进入风格
     * @param inStyle boolean
     */
    public void setInStyle(boolean inStyle)
    {
        this.inStyle = inStyle;
    }
    /**
     * 是否启用进入风格
     * @return boolean
     */
    public boolean isInStyle()
    {
        return inStyle;
    }
    /**
     * 设置是否真正拖动
     * @param dragged boolean
     */
    public void setDragged(boolean dragged)
    {
        this.dragged = dragged;
        //同时适配器正在拖动
        if(getUIAdapterIO() != null)
            getUIAdapterIO().setDragged(dragged);
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
     * 设置UI适配器
     * @param uiAdapter TUIAdapter
     */
    public void setUIAdapterIO(TUIAdapter uiAdapter)
    {
        this.uiAdapter = uiAdapter;
    }
    /**
     * 得到UI适配器
     * @return TUIAdapter
     */
    public TUIAdapter getUIAdapterIO()
    {
        return uiAdapter;
    }
    /**
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
        super.onMousePressed(e);
        if(e.getButton() != 1)
            return;
        if(e.getClickCount() == 2)
        {
            onDoubleClicked();
            return;
        }
        if(getMoveType() == NO_MOVE)
            return;
        if(isMoveParent() && getMoveType() == MOVE)
        {
            Container container = getParent();
            Container parent = container.getParent();
            parent.remove(container);
            parent.add(container,0);
            container.repaint();
        }
        oldX = e.getX();
        oldY = e.getY();
        isMove = true;
        setDragged(false);
    }
    /**
     * 否双击过
     * @return boolean true 合 false 开
     */
    public boolean isDocubleClicked()
    {
        return oldPoint != null;
    }
    /**
     * 双击事件
     */
    public void onDoubleClicked()
    {
        onDoubleClicked(!isDocubleClicked());
    }
    /**
     * 双击事件
     * @param b boolean true 合 false 开
     */
    public void onDoubleClicked(boolean b)
    {
        if(getDoubleClickType() == 0)
            return;
        if(b == isDocubleClicked())
            return;
        Point point = oldPoint == null?getDoublcClickedPoint():oldPoint;
        checkMode(point);
        setLocation(point.x + getX(),point.y + getY());
        doMove(point,true);
        if(oldPoint == null)
            oldPoint = new Point(-point.x,-point.y);
        else
            oldPoint = null;
    }
    /**
     * 得到双击移动位置
     * @return Point
     */
    public Point getDoublcClickedPoint()
    {
        if(getDoubleClickType() == 0)
            return null;
        switch(getMoveType())
        {
        case 0:
            return null;
        case 1://width
            if(getDoubleClickType() == 1)
                return new Point(-2000,0);
            else if(getDoubleClickType() == 2)
                return new Point(2000,0);
            return null;
            //h
        case 2:
            if(getDoubleClickType() == 1)
                return new Point(0,-2000);
            else if(getDoubleClickType() == 2)
                return new Point(0,2000);
            return null;
            //m
        case 3:
            if(getDoubleClickType() == 1)
                return new Point(-2000,-2000);
            else if(getDoubleClickType() == 2)
                return new Point(2000,2000);
            return null;
        }
        return null;
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
        super.onMouseReleased(e);
        if(draggedEnd(e))
            return;
        if(getWidth() - e.getX() > 0 && getHeight() - e.getY() > 0)
            callEvent(getTag() + "->" + MOUSE_CLICKED, new Object[] {},
                      new String[] {});
    }
    /**
     * 拖动结束
     * @param e MouseEvent
     * @return boolean
     */
    public boolean draggedEnd(MouseEvent e)
    {
        if(!isMove)
            return false;
        isMove = false;
        if(!isDragged())
            return false;
        setDragged(false);
        return true;
    }
    /**
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
        super.onMouseEntered(e);
        if(isInStyle())
        {
            uiState = 1;
            repaint();
        }
        int cursor = 0;
        switch(getMoveType())
        {
        case 0:
            break;
        case 1:
            cursor = Cursor.E_RESIZE_CURSOR;
            break;
        case 2:
            cursor = Cursor.N_RESIZE_CURSOR;
            break;
        case 3:
            if(getCursorType() == 1)
                cursor = Cursor.NW_RESIZE_CURSOR;
            if(getCursorType() == 2)
                cursor = Cursor.NE_RESIZE_CURSOR;
            break;
        }
        setCursor(new Cursor(cursor));
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
        super.onMouseExited(e);
        if(isInStyle())
        {
            uiState = 0;
            repaint();
        }
        setCursor(new Cursor(0));
    }
    /**
     * 鼠标拖动
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        super.onMouseDragged(e);
        if(!isMove)
            return;
        //设置真正拖动
        setDragged(true);
        int x = 0;
        int y = 0;
        switch(getMoveType())
        {
        case WIDTH_MOVE:
            if(isResizeParent())
            {
                Container container = getParent();
                if(container != null)
                    container.setSize(container.getWidth() + e.getX() - oldX,container.getHeight());
            }
            x += e.getX() - oldX;
            break;
        case HEIGHT_MOVE:
            if(isResizeParent())
            {
                Container container = getParent();
                if(container != null)
                    container.setSize(container.getWidth(),container.getHeight() + e.getY() - oldY);
            }
            y += e.getY() - oldY;
            break;
        case MOVE:
            if(isMoveParent())
            {
                Container container = getParent();
                if(container == null)
                    return;
                container.setLocation(container.getX() + e.getX() - oldX,container.getY() + e.getY() - oldY);
                return;
            }
            if(isResizeParent())
            {
                Container container = getParent();
                if(container != null)
                    container.setSize(container.getWidth() + e.getX() - oldX,container.getHeight() + e.getY() - oldY);
            }
            x += e.getX() - oldX;
            y += e.getY() - oldY;
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            break;
        }
        Point point = new Point(x,y);
        checkMode(point);
        setLocation(point.x + getX(),point.y + getY());
        doMove(point,true);
        oldPoint = null;
    }
    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
        super.onMouseMoved(e);
    }
    /**
     * 测试移动
     * @param point Point
     */
    public void checkMode(Point point)
    {
        int count = getEntitySize();
        for(int i = 0;i < count;i ++)
        {
            Entity entity = getEntity(i);
            TComponent com = entity.getCom();
            if(com == null)
            {
                com = (TComponent)getParentTComponent().callFunction("findObject",entity.getTag());
                if(com == null)
                    continue;
                entity.setCom(com);
            }
            switch(entity.getState())
            {
            case 1://上
                int height = (Integer)com.callFunction("getHeight");
                int position = entity.getPosition();
                if(point.y > height - position)
                    point.y = height - position;
                break;
            case 2://下
                height = (Integer)com.callFunction("getHeight");
                position = entity.getPosition();
                if(-point.y > height - position)
                    point.y = -height + position;
                break;
            case 3://左
                int width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(point.x > width - position)
                    point.x = width - position;
                break;
            case 4://右
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(-point.x > width - position)
                    point.x = -width + position;
                break;
            case 5://左上
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(point.x > width - position)
                    point.x = width - position;
                height = (Integer)com.callFunction("getHeight");
                if(point.y > height - position)
                    point.y = height - position;
                break;
            case 6://右上
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(-point.x > width - position)
                    point.x = -width + position;
                height = (Integer)com.callFunction("getHeight");
                if(point.y > height - position)
                    point.y = height - position;
                break;
            case 7://右下
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(-point.x > width - position)
                    point.x = -width + position;
                height = (Integer)com.callFunction("getHeight");
                if(-point.y > height - position)
                    point.y = -height + position;
                break;
            case 8://左下
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(point.x > width - position)
                    point.x = width - position;
                height = (Integer)com.callFunction("getHeight");
                if(-point.y > height - position)
                    point.y = -height + position;
                break;
            }
        }
    }
    /**
     * 移动动作
     * @param point Point 偏移量
     * @param follow boolean
     */
    public void doMove(Point point,boolean follow)
    {
        int count = getEntitySize();
        for(int i = 0;i < count;i ++)
        {
            Entity entity = getEntity(i);
            TComponent com = entity.getCom();
            if(com == null)
                continue;
            switch(entity.getState())
            {
            case 0://移动
                com.callFunction("setX$",point.x);
                com.callFunction("setY$",point.y);
                break;
            case 1://上
                com.callFunction("setY_$",point.y);
                break;
            case 2://下
                com.callFunction("setHeight$",point.y);
                break;
            case 3://左
                com.callFunction("setX_$",point.x);
                break;
            case 4://右
                com.callFunction("setWidth$",point.x);
                break;
            case 5://左上
                com.callFunction("setX_$",point.x);
                com.callFunction("setY_$",point.y);
                break;
            case 6://右上
                com.callFunction("setY_$",point.y);
                com.callFunction("setWidth$",point.x);
                break;
            case 7://右下
                com.callFunction("setWidth$",point.x);
                com.callFunction("setHeight$",point.y);
                break;
            case 8://左下
                com.callFunction("setX_$",point.x);
                com.callFunction("setHeight$",point.y);
                break;
            case 9://移动X
                com.callFunction("setX$",point.x);
                break;
            case 10://移动Y
                com.callFunction("setY$",point.y);
                break;
            }
        }
    }
    /**
     * 调整实体尺寸
     */
    public void resizeEntity()
    {
        int count = getEntitySize();
        for(int i = 0;i < count;i ++)
        {
            Entity entity = getEntity(i);
            TComponent com = entity.getCom();
            if(com == null)
            {
                com = (TComponent)getParentTComponent().callFunction("findObject",entity.getTag());
                if(com == null)
                    continue;
                entity.setCom(com);
            }
            switch(entity.getState())
            {
            case 1://上
                int y = getY() + getHeight();
                int height = (Integer)com.callFunction("getY") + (Integer)com.callFunction("getHeight") - y;
                com.callFunction("setY",y);
                com.callFunction("setHeight",height);
                break;
            case 2://下
                com.callFunction("setHeight",getY() - (Integer)com.callFunction("getY"));
                break;
            case 3://左
                int x = getX() + getWidth();
                int width = (Integer)com.callFunction("getX") + (Integer)com.callFunction("getWidth") - x;
                com.callFunction("setX",x);
                com.callFunction("setWidth",width);
                break;
            case 4://右
                com.callFunction("setWidth",getX() - (Integer)com.callFunction("getX"));
                break;
            case 5://左上
                break;
            case 6://右上
                break;
            case 7://右下
                break;
            case 8://左下
                break;
            }
        }
    }
    /**
     * 绘制风格
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        g.setColor(getForeground());
        switch(getStyle())
        {
        case 1:
            g.setColor(getBackground());
            g.drawRect(0,0,getWidth() - 1,getHeight() - 1);
            break;
        case 2:
            g.setColor(getBackground());
            g.fillRect(0,0,getWidth(),getHeight());
            break;
        case 3:
            g.setColor(new Color(255,255,255));
            g.drawLine(0,0,0,getHeight() - 1);
            g.setColor(new Color(113,111,100));
            g.drawLine(getWidth() - 1,0,getWidth() - 1,getHeight() - 1);
            break;
        case 4:
            g.setColor(new Color(255,255,255));
            g.drawLine(0,0,getWidth() - 1,0);
            g.setColor(new Color(113,111,100));
            g.drawLine(0,getHeight() - 1,getWidth() - 1,getHeight() - 1);
            break;
        case 5:
            g.setColor(new Color(230,139,44));
            g.drawLine(0,0,getWidth() - 1,0);
            g.setColor(new Color(255,199,60));
            g.drawLine(0,1,getWidth() - 1,1);
            g.drawLine(0,2,getWidth() - 1,2);
            g.drawString("move",0,10);
            break;
        }
        if(isInStyle())
            paintInStyle(g);
        super.paint(g);
    }
    /**
     * 绘制进入风格
     * @param g Graphics
     */
    public void paintInStyle(Graphics g)
    {
        switch(uiState)
        {
        case 1:
            g.setColor(new Color(230,139,44));
            g.drawLine(0,0,getWidth() - 1,0);
            g.setColor(new Color(255,199,60));
            g.drawLine(0,1,getWidth() - 1,1);
            g.drawLine(0,2,getWidth() - 1,2);
        }
    }
    /**
     * 设置双击动作类型
     * @param doubleClickType int
     */
    public void setDoubleClickType(int doubleClickType)
    {
        this.doubleClickType = doubleClickType;
    }
    /**
     * 得到双击动作类型
     * @return int
     */
    public int getDoubleClickType()
    {
        return doubleClickType;
    }
    /**
     * 是否移动父类
     * @param isMoveParent boolean
     */
    public void setMoveParent(boolean isMoveParent)
    {
        this.moveParent = isMoveParent;
    }
    /**
     * 是否移动父类
     * @return boolean
     */
    public boolean isMoveParent()
    {
        return moveParent;
    }
    /**
     * 设置调整父类尺寸
     * @param isResizeParent boolean
     */
    public void setResizeParent(boolean isResizeParent)
    {
        this.resizeParent = isResizeParent;
    }
    /**
     * 是否调整父类尺寸
     * @return boolean
     */
    public boolean isResizeParent()
    {
        return resizeParent;
    }
}
