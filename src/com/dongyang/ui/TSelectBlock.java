package com.dongyang.ui;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import javax.swing.SwingUtilities;

public class TSelectBlock extends JComponent implements MouseListener,
        MouseMotionListener {
    private TComponent component;
    private int state;
    private int oldX;
    private int oldY;
    private int oldX1;
    private int oldY1;
    /**
     * 随动
     */
    private boolean followFlg = false;
    private boolean draggedFlg;
    private Block block;
    public static final boolean redLine = true;
    /**
     * 内部加载标记
     */
    private boolean updateFlg = false;
    /**
     * 控制尺寸
     */
    private int actionSize = 2;
    class Block extends JComponent
    {
        public Block()
        {
            //setDoubleBuffered(false);
        }
        private Insets insets = new Insets(actionSize,actionSize,actionSize,actionSize);
        private Insets boundsSize = new Insets(0,0,0,0);
        public void setInsetsSize(Insets insets)
        {
            this.insets = insets;
        }
        public Insets getInsetsSize()
        {
            return insets;
        }
        public void setBoundsSize(Insets boundsSize)
        {
            this.boundsSize = boundsSize;
        }
        public Insets getBoundsSize()
        {
            return boundsSize;
        }
        /*public void paint(Graphics g) {
            if(!redLine)
                return;
            Color c1 = new Color(255, 0, 0);
            g.setColor(c1);
            g.drawRect(0,0,getWidth() - 1,getHeight() - 1);
        }*/
    }
    public TSelectBlock()
    {
        //setDoubleBuffered(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        block = new Block();
        block.setLocation(block.getBoundsSize().left,block.getBoundsSize().top);
        add(block);
    }
    public void setX(int x) {
        this.setLocation(x, getLocation().y);
    }

    public void setY(int y) {
        this.setLocation(getLocation().x, y);
    }
    public void setWidth(int width) {
        this.setSize(width, getSize().height);
    }

    public void setHeight(int height) {
        this.setSize(getSize().width, height);
    }

    public void setTComponent(TComponent component)
    {
        this.component = component;
    }
    public TComponent getTComponent()
    {
        return component;
    }
    public Block getBlock()
    {
        return block;
    }
    public void update(boolean updateFlg)
    {
        this.updateFlg = updateFlg;
        if(component == null)
            return;
        int x1 = (Integer)component.callMessage("getX") - block.getInsetsSize().left;
        int y1 = (Integer)component.callMessage("getY") - block.getInsetsSize().left;

        if(updateFlg && getParent() != null && getParent().getParent() != null && getParent().getParent() instanceof TSelectBlock)
        {
            Insets insets = ((TSelectBlock)getParent().getParent()).getBlock().getBoundsSize();
            x1 -= insets.left;
            y1 -= insets.right;
        }
        setLocation(x1,y1);
        setSize((Integer) component.callMessage("getWidth") +
                block.getInsetsSize().left + block.getInsetsSize().right,
                (Integer) component.callMessage("getHeight") +
                block.getInsetsSize().top + block.getInsetsSize().bottom);
        /*if (component instanceof Container) {
            Container container = (Container) component;
            Insets insets = (Insets) component.callMessage("getInsets");
            Border border = (Border) component.callMessage("getBorder");
            if(border != null && border instanceof TitledBorder)
                insets.top = insets.left;
            block.setBoundsSize(insets);
            block.setLocation(block.getBoundsSize().left + block.getInsetsSize().left,block.getBoundsSize().top + block.getInsetsSize().top);
            block.setSize(getWidth() - block.getBoundsSize().left - block.getBoundsSize().right -
                          block.getInsetsSize().left - block.getInsetsSize().right,
                          getHeight() - block.getBoundsSize().top - block.getBoundsSize().bottom -
                          block.getInsetsSize().top - block.getInsetsSize().bottom);
            for (int i = 0; i < container.getComponentCount(); i++) {
                Component c = container.getComponent(i);
                if (!(c instanceof TComponent))
                    continue;
                TSelectBlock cblock = new TSelectBlock();
                cblock.setTComponent((TComponent) c);
                block.add(cblock);
                cblock.update(true);
            }
        }*/
        return;
    }
    /**
     * 鼠标点击
     * @param e
     */
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * 鼠标拖动
     * @param e
     */
    public void mouseDragged(MouseEvent e) {
        draggedFlg = true;
        int length = actionSize * 2;
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        int mX = e.getX();
        int mY = e.getY();
        switch (state)
        {
        case 0:
            x = x + mX - oldX;
            y = y + mY - oldY;
            setLocation(x, y);
            return;
        case 1:
            if(height - mY + oldY < length)
                mY = height + oldY - length;
            y = y + mY - oldY;
            height = height - mY + oldY;
            setLocation(x, y);
            setSize(width,height);
            return;
        case 2:
            if(mY + oldY1 < length)
                mY = length - oldY1;
            height = mY + oldY1;
            setSize(width,height);
            return;
        case 3:
            if(width - mX + oldX < length)
                mX = width + oldX - length;
            x = x + mX - oldX;
            width = width - mX + oldX;
            setLocation(x, y);
            setSize(width,height);
            return;
        case 4:
            if(mX + oldX1 < length)
                mX = length - oldX1;
            width = mX + oldX1;
            setSize(width,height);
            return;
        case 5:
            if(height - mY + oldY < length)
                mY = height + oldY - length;
            if(width - mX + oldX < length)
                mX = width + oldX - length;
            y = y + mY - oldY;
            x = x + mX - oldX;
            height = height - mY + oldY;
            width = width - mX + oldX;
            setLocation(x, y);
            setSize(width,height);
            return;
        case 6:
            if(height - mY + oldY < length)
                mY = height + oldY - length;
            if(mX + oldX1 < length)
                mX = length - oldX1;
            y = y + mY - oldY;
            height = height - mY + oldY;
            width = mX + oldX1;
            setLocation(x, y);
            setSize(width,height);
            return;
        case 7:
            if(mY + oldY1 < length)
                mY = length - oldY1;
            if(mX + oldX1 < length)
                mX = length - oldX1;
            height = mY + oldY1;
            width = mX + oldX1;
            setSize(width,height);
            return;
        case 8:
            if(mY + oldY1 < length)
                mY = length - oldY1;
            if(width - mX + oldX < length)
                mX = width + oldX - length;
            x = x + mX - oldX;
            width = width - mX + oldX;
            height = mY + oldY1;
            setLocation(x, y);
            setSize(width,height);
            return;
        }
    }
    public void setLocation(int x, int y)
    {
        if(followFlg && component != null)
        {
            int x1 = x + block.getInsetsSize().left;
            int y1 = y + block.getInsetsSize().top;
            if(updateFlg && getParent() != null && getParent().getParent() != null && getParent().getParent() instanceof TSelectBlock)
            {
                Insets insets = ((TSelectBlock)getParent().getParent()).getBlock().getBoundsSize();
                x1 += insets.left;
                y1 += insets.right;
            }
            component.callMessage("setX|" + x1);
            component.callMessage("setY|" + y1);
        }
        super.setLocation(x,y);
        if(followFlg && getParent() != null && getParent() instanceof JComponent)
            SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                    ((JComponent)getParent()).repaint();
                }
            });
    }
    public void setSize(int width, int height)
    {
        block.setSize(width - block.getBoundsSize().left - block.getBoundsSize().right -
                      block.getInsetsSize().left - block.getInsetsSize().right,
                      height - block.getBoundsSize().top - block.getBoundsSize().bottom -
                      block.getInsetsSize().top - block.getInsetsSize().bottom);


        if(followFlg && component != null)
        {
            component.callMessage("setWidth|" + (width - block.getInsetsSize().left - block.getInsetsSize().right));
            component.callMessage("setHeight|" + (height - block.getInsetsSize().top - block.getInsetsSize().bottom));
        }
        super.setSize(width,height);
        if(followFlg && getParent() != null && getParent() instanceof JComponent)
            SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                    JComponent com = (JComponent)getParent();
                    com.updateUI();
                    com.repaint();
                }
            });
    }
    /**
     * 鼠标进入
     * @param e
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * 鼠标离开
     * @param e
     */
    public void mouseExited(MouseEvent e) {

    }

    /**
     * 鼠标点下
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();
        oldX1 = getWidth() - e.getX();
        oldY1 = getHeight() - e.getY();

    }

    /**
     * 鼠标移动
     * @param e
     */
    public void mouseMoved(MouseEvent e) {
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        int mX = e.getX();
        int mY = e.getY();
        if (mX < actionSize * 2 && mY < actionSize * 2)
            setState(5);
        else if (mX >= width/2 - actionSize && mX <= width/2 + actionSize && mY < actionSize * 2)
            setState(1);
        else if (mX >= width - actionSize * 2 && mY < actionSize * 2)
            setState(6);
        else if (mX >= width - actionSize * 2 && mY >= height/2 - actionSize && mY <= height / 2 + actionSize)
            setState(4);
        else if (mX >= width - actionSize * 2 && mY >= height - actionSize * 2)
            setState(7);
        else if (mX >= width / 2 - actionSize && mX <= width / 2 + actionSize && mY >= height - actionSize * 2)
            setState(2);
        else if (mX < actionSize * 2 && mY >= height - actionSize * 2)
            setState(8);
        else if (mX < actionSize * 2 && mY >= height / 2 - actionSize && mY <= height / 2 + actionSize)
            setState(3);
        else
            setState(0);
    }
    /**
     * 是指移动状态
     * @param state
     */
    public void setState(int state)
    {
      this.state = state;
      setCursor();
    }
    /**
     * 改变光标
     */
    public void setCursor()
    {
      int cursor = 0;
      switch (state)
      {
        case 1:
        case 2:
          cursor = Cursor.N_RESIZE_CURSOR;
          break;
        case 3:
        case 4:
          cursor = Cursor.E_RESIZE_CURSOR;
          break;
        case 5:
        case 7:
          cursor = Cursor.NW_RESIZE_CURSOR;
          break;
        case 6:
        case 8:
          cursor = Cursor.NE_RESIZE_CURSOR;
          break;
      }
      setCursor(new Cursor(cursor));
    }

    /**
     * 鼠标抬起
     * @param e
     */
    public void mouseReleased(MouseEvent e) {

        draggedFlg = false;
        if(!followFlg && component != null)
        {
            int x1 = getX() + block.getInsetsSize().left;
            int y1 = getY() + block.getInsetsSize().top;
            if(updateFlg && getParent() != null && getParent().getParent() != null && getParent().getParent() instanceof TSelectBlock)
            {
                Insets insets = ((TSelectBlock)getParent().getParent()).getBlock().getBoundsSize();
                x1 += insets.left;
                y1 += insets.right;
            }
            component.callMessage("setX|" + x1);
            component.callMessage("setY|" + y1);
            component.callMessage("setWidth|" + (getWidth() - block.getInsetsSize().left - block.getInsetsSize().right));
            component.callMessage("setHeight|" + (getHeight() - block.getInsetsSize().top - block.getInsetsSize().bottom));
        }
        if(getParent() != null && getParent() instanceof JComponent)
            SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                    JComponent com = (JComponent)getParent();
                    com.updateUI();
                    com.repaint();
                }
            });
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics gh = g.create(block.getX(),block.getY(),block.getWidth(),block.getHeight());
        Color c1 = new Color(0, 0, 255);
        g.setColor(c1);
        int width = getWidth();
        int height = getHeight();

        if(component instanceof Container)
        {
            for(int i = 0;i < block.getComponentCount();i++)
            {
                Component component = block.getComponent(i);
                int x1 = component.getX();
                int y1 = component.getY();
                int width1 = component.getWidth();
                int height1 = component.getHeight();
                if(width1 + x1 > block.getWidth())
                    width1 = block.getWidth() - x1;
                if(height1 + y1 > block.getHeight())
                    height1 = block.getHeight() - y1;
                Graphics gc = gh.create(x1,y1,width1,height1);
                component.paint(gc);
            }
        }

        if(draggedFlg)
        {
            g.drawRect(actionSize, actionSize, width - actionSize * 2 - 1, height - actionSize * 2 - 1);
            return;
        }else
        {
            g.drawRect(0, 0, actionSize * 2, actionSize * 2);
            g.drawRect(width - actionSize * 2 - 1, 0, actionSize * 2, actionSize * 2);
            g.drawRect(0, height - actionSize * 2 - 1, actionSize * 2, actionSize * 2);
            g.drawRect(width - actionSize * 2 - 1, height - actionSize * 2 - 1, actionSize * 2, actionSize * 2);

            g.drawRect(width / 2 - actionSize, 0, actionSize * 2, actionSize * 2);
            g.drawRect(width / 2 - actionSize, height - actionSize * 2 - 1, actionSize * 2, actionSize * 2);
            g.drawRect(0, height / 2 - actionSize, actionSize * 2, actionSize * 2);
            g.drawRect(width - actionSize * 2 - 1, height / 2 - actionSize, actionSize * 2, actionSize * 2);

            /*if(width / 2 - actionSize > actionSize * 2)
                g.drawLine(actionSize * 2, actionSize, width / 2 - actionSize, actionSize);
            if(width - actionSize * 2 - 1 > width / 2 + actionSize)
                g.drawLine(width / 2 + actionSize, actionSize, width - actionSize * 2 - 1, actionSize);
            if(width / 2 - actionSize > actionSize * 2)
                g.drawLine(actionSize * 2, height - actionSize - 1, width / 2 - actionSize, height - actionSize - 1);
            if(width - actionSize * 2 - 1 > width / 2 + actionSize)
                g.drawLine(width / 2 + actionSize, height - actionSize - 1, width - actionSize * 2 - 1, height - actionSize - 1);

            if(height / 2 - actionSize - 1 > actionSize * 2)
                g.drawLine(actionSize, actionSize * 2, actionSize, height / 2 - actionSize - 1);
            if(height - actionSize * 2 - 1 > height / 2 + actionSize)
                g.drawLine(actionSize, height / 2 + actionSize, actionSize, height - actionSize * 2 - 1);
            if(height / 2 - actionSize - 1 > actionSize * 2)
                g.drawLine(width - actionSize - 1, actionSize * 2, width - actionSize - 1, height / 2 - actionSize - 1);
            if(height - actionSize * 2 - 1 > height / 2 + actionSize)
                g.drawLine(width - actionSize - 1, height / 2 + actionSize, width - actionSize - 1, height - actionSize * 2 - 1);*/

        }
    }
}
