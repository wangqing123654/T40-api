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
     * �����
     */
    public static final String MOUSE_CLICKED = "mouseClicked";
    /**
     * �����ƶ�
     */
    public static final int NO_MOVE = 0;
    /**
     * ���Һ����ƶ�
     */
    public static final int WIDTH_MOVE = 1;
    /**
     * ���������ƶ�
     */
    public static final int HEIGHT_MOVE = 2;
    /**
     * �����ƶ�
     */
    public static final int MOVE = 3;
    /**
     * �ƶ�����
     */
    private int moveType = MOVE;
    /**
     * �������
     */
    private int cursorType = 0;
    /**
     * ����Xλ��
     */
    private int oldX;
    /**
     * ����Yλ��
     */
    private int oldY;
    /**
     * �Ƿ��϶���
     */
    private boolean isMove;
    /**
     * �Ƿ������϶�(λ���иı�)
     */
    private boolean dragged;
    /**
     * Я��ʵ�弯
     */
    private Vector entitys;
    /**
     * UI������
     */
    private TUIAdapter uiAdapter;
    /**
     * ���
     */
    private int style;

    /**
     * ������
     */
    private boolean inStyle;
    /**
     * ui״̬
     */
    private int uiState;
    /**
     * ˫���ƶ�����
     */
    private int doubleClickType;
    /**
     * ��λ��
     */
    private Point oldPoint;
    /**
     * �ƶ�����λ��
     */
    private boolean moveParent;
    /**
     * ��������ߴ�
     */
    private boolean resizeParent;
    public class Entity
    {
        /**
         * ����
         */
        public static final int STATE = 0;
        /**
         * �е�
         */
        public static final int MID  = 1;
        /**
         * �յ�
         */
        public static final int END  = 2;
        /**
         * ��ǩ
         */
        private String tag;
        /**
         * Я��ʵ��
         */
        private TComponent com;
        /**
         * ״̬
         */
        private int state;
        /**
         * λ��
         */
        private int position;
        /**
         * ������
         * @param com TComponent ���
         * @param state int ����״̬
         * @param position int λ��
         */
        public Entity(TComponent com,int state,int position)
        {
            setCom(com);
            setState(state);
            setPosition(position);
        }
        /**
         * ������
         * @param tag String �����ǩ
         * @param state int ����״̬
         * @param position int λ��
         */
        public Entity(String tag,int state,int position)
        {
            setTag(tag);
            setState(state);
            setPosition(position);
        }
        /**
         * ���ñ�ǩ
         * @param tag String
         */
        public void setTag(String tag)
        {
            this.tag = tag;
        }
        /**
         * �õ���ǩ
         * @return String
         */
        public String getTag()
        {
            return tag;
        }
        /**
         * ����Я��ʵ��
         * @param com TComponent
         */
        public void setCom(TComponent com)
        {
            this.com = com;
        }
        /**
         * �õ�Я��ʵ��
         * @return TComponent
         */
        public TComponent getCom()
        {
            return com;
        }
        /**
         * ����״̬
         * @param state int
         */
        public void setState(int state)
        {
            this.state = state;
        }
        /**
         * �õ�״̬
         * @return int
         */
        public int getState()
        {
            return state;
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
    }
    /**
     * ������
     */
    public TMovePaneBase()
    {
        //��ʼ��ʵ��
        entitys = new Vector();
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {
        super.onInit();
    }
    /**
     * Я��ʵ�����
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
     * ����Я��ʵ��
     * @param com TComponent ���
     * @param state int ״̬
     */
    public void addEntity(TComponent com,int state)
    {
        addEntity(com,state,Entity.MID);
    }
    /**
     * ����Я��ʵ��
     * @param com TComponent ���
     * @param state int ״̬
     * @param position int
     */
    public void addEntity(TComponent com,int state,int position)
    {
        Entity entity = new Entity(com,state,position);
        entitys.add(entity);
    }
    /**
     * ����Я��ʵ��
     * @param tag String �����ǩ
     * @param state int ״̬
     */
    public void addEntity(String tag,int state)
    {
        addEntity(tag,state,Entity.MID);
    }
    /**
     * ����Я��ʵ��
     * @param tag String �����ǩ
     * @param state int ״̬
     * @param position int
     */
    public void addEntity(String tag,int state,int position)
    {
        Entity entity = new Entity(tag,state,position);
        entitys.add(entity);
    }
    /**
     * ɾ��Я��ʵ��
     * @param index int λ��
     */
    public void removeEntity(int index)
    {
        entitys.remove(index);
    }
    /**
     * ɾ��Я��ʵ��
     * @param com TComponent �����Ա
     */
    public void removeEntity(TComponent com)
    {
        int index = getEntityIndex(com);
        if(index == -1)
            return;
        entitys.remove(index);
    }
    /**
     * ɾ��ȫ��Я��ʵ��
     */
    public void removeEntityAll()
    {
        entitys = new Vector();
    }
    /**
     * ����ʵ��λ��
     * @param com TComponent ���
     * @return int λ��
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
     * �õ�ʵ���Ա
     * @param index int λ��
     * @return Entity
     */
    public Entity getEntity(int index)
    {
        return (Entity)entitys.get(index);
    }
    /**
     * �����ƶ�����
     * @param moveType int
     */
    public void setMoveType(int moveType)
    {
        this.moveType = moveType;
    }
    /**
     * �õ��ƶ�����
     * @return int
     */
    public int getMoveType()
    {
        return moveType;
    }
    /**
     * ���ù������
     * @param cursorType int 1 �������� 2��������
     */
    public void setCursorType(int cursorType)
    {
        this.cursorType = cursorType;
    }
    /**
     * �õ��������
     * @return int
     */
    public int getCursorType()
    {
        return cursorType;
    }
    /**
     * ���÷��
     * @param style int
     */
    public void setStyle(int style)
    {
        this.style = style;
        repaint();
    }
    /**
     * �õ����
     * @return int
     */
    public int getStyle()
    {
        return style;
    }
    /**
     * ���ý�����
     * @param inStyle boolean
     */
    public void setInStyle(boolean inStyle)
    {
        this.inStyle = inStyle;
    }
    /**
     * �Ƿ����ý�����
     * @return boolean
     */
    public boolean isInStyle()
    {
        return inStyle;
    }
    /**
     * �����Ƿ������϶�
     * @param dragged boolean
     */
    public void setDragged(boolean dragged)
    {
        this.dragged = dragged;
        //ͬʱ�����������϶�
        if(getUIAdapterIO() != null)
            getUIAdapterIO().setDragged(dragged);
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
     * ����UI������
     * @param uiAdapter TUIAdapter
     */
    public void setUIAdapterIO(TUIAdapter uiAdapter)
    {
        this.uiAdapter = uiAdapter;
    }
    /**
     * �õ�UI������
     * @return TUIAdapter
     */
    public TUIAdapter getUIAdapterIO()
    {
        return uiAdapter;
    }
    /**
     * ��������
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
     * ��˫����
     * @return boolean true �� false ��
     */
    public boolean isDocubleClicked()
    {
        return oldPoint != null;
    }
    /**
     * ˫���¼�
     */
    public void onDoubleClicked()
    {
        onDoubleClicked(!isDocubleClicked());
    }
    /**
     * ˫���¼�
     * @param b boolean true �� false ��
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
     * �õ�˫���ƶ�λ��
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
     * ����̧��
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
     * �϶�����
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
     * ������
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
     * ����뿪
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
     * ����϶�
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
        super.onMouseDragged(e);
        if(!isMove)
            return;
        //���������϶�
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
     * ����ƶ�
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
        super.onMouseMoved(e);
    }
    /**
     * �����ƶ�
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
            case 1://��
                int height = (Integer)com.callFunction("getHeight");
                int position = entity.getPosition();
                if(point.y > height - position)
                    point.y = height - position;
                break;
            case 2://��
                height = (Integer)com.callFunction("getHeight");
                position = entity.getPosition();
                if(-point.y > height - position)
                    point.y = -height + position;
                break;
            case 3://��
                int width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(point.x > width - position)
                    point.x = width - position;
                break;
            case 4://��
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(-point.x > width - position)
                    point.x = -width + position;
                break;
            case 5://����
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(point.x > width - position)
                    point.x = width - position;
                height = (Integer)com.callFunction("getHeight");
                if(point.y > height - position)
                    point.y = height - position;
                break;
            case 6://����
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(-point.x > width - position)
                    point.x = -width + position;
                height = (Integer)com.callFunction("getHeight");
                if(point.y > height - position)
                    point.y = height - position;
                break;
            case 7://����
                width = (Integer)com.callFunction("getWidth");
                position = entity.getPosition();
                if(-point.x > width - position)
                    point.x = -width + position;
                height = (Integer)com.callFunction("getHeight");
                if(-point.y > height - position)
                    point.y = -height + position;
                break;
            case 8://����
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
     * �ƶ�����
     * @param point Point ƫ����
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
            case 0://�ƶ�
                com.callFunction("setX$",point.x);
                com.callFunction("setY$",point.y);
                break;
            case 1://��
                com.callFunction("setY_$",point.y);
                break;
            case 2://��
                com.callFunction("setHeight$",point.y);
                break;
            case 3://��
                com.callFunction("setX_$",point.x);
                break;
            case 4://��
                com.callFunction("setWidth$",point.x);
                break;
            case 5://����
                com.callFunction("setX_$",point.x);
                com.callFunction("setY_$",point.y);
                break;
            case 6://����
                com.callFunction("setY_$",point.y);
                com.callFunction("setWidth$",point.x);
                break;
            case 7://����
                com.callFunction("setWidth$",point.x);
                com.callFunction("setHeight$",point.y);
                break;
            case 8://����
                com.callFunction("setX_$",point.x);
                com.callFunction("setHeight$",point.y);
                break;
            case 9://�ƶ�X
                com.callFunction("setX$",point.x);
                break;
            case 10://�ƶ�Y
                com.callFunction("setY$",point.y);
                break;
            }
        }
    }
    /**
     * ����ʵ��ߴ�
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
            case 1://��
                int y = getY() + getHeight();
                int height = (Integer)com.callFunction("getY") + (Integer)com.callFunction("getHeight") - y;
                com.callFunction("setY",y);
                com.callFunction("setHeight",height);
                break;
            case 2://��
                com.callFunction("setHeight",getY() - (Integer)com.callFunction("getY"));
                break;
            case 3://��
                int x = getX() + getWidth();
                int width = (Integer)com.callFunction("getX") + (Integer)com.callFunction("getWidth") - x;
                com.callFunction("setX",x);
                com.callFunction("setWidth",width);
                break;
            case 4://��
                com.callFunction("setWidth",getX() - (Integer)com.callFunction("getX"));
                break;
            case 5://����
                break;
            case 6://����
                break;
            case 7://����
                break;
            case 8://����
                break;
            }
        }
    }
    /**
     * ���Ʒ��
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
     * ���ƽ�����
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
     * ����˫����������
     * @param doubleClickType int
     */
    public void setDoubleClickType(int doubleClickType)
    {
        this.doubleClickType = doubleClickType;
    }
    /**
     * �õ�˫����������
     * @return int
     */
    public int getDoubleClickType()
    {
        return doubleClickType;
    }
    /**
     * �Ƿ��ƶ�����
     * @param isMoveParent boolean
     */
    public void setMoveParent(boolean isMoveParent)
    {
        this.moveParent = isMoveParent;
    }
    /**
     * �Ƿ��ƶ�����
     * @return boolean
     */
    public boolean isMoveParent()
    {
        return moveParent;
    }
    /**
     * ���õ�������ߴ�
     * @param isResizeParent boolean
     */
    public void setResizeParent(boolean isResizeParent)
    {
        this.resizeParent = isResizeParent;
    }
    /**
     * �Ƿ��������ߴ�
     * @return boolean
     */
    public boolean isResizeParent()
    {
        return resizeParent;
    }
}
