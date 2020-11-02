package com.dongyang.tui.text.image;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.DCursor;
import com.dongyang.tui.DText;
import com.dongyang.tui.text.PM;
import com.dongyang.ui.TPopupMenu;
import com.dongyang.tui.DMessageIO;
import com.dongyang.util.RunClass;
import com.dongyang.util.StringTool;
import com.dongyang.ui.TWindow;
import com.dongyang.tui.text.MFocus;
import java.awt.Font;
import com.dongyang.tui.DFont;
import java.awt.FontMetrics;
import com.dongyang.tui.text.div.CStringDraw;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Component;
import javax.swing.ImageIcon;
import java.awt.Image;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.EAction;
import com.dongyang.tui.text.EImage;
import com.dongyang.tui.DPoint;
import com.dongyang.util.ImageTool;
import java.awt.Dimension;
import java.util.Vector;
import com.dongyang.tui.text.IBlock;

public class GBlock extends GBlockBase implements DMessageIO
{
    private boolean editSelect;
    private BlockPI parent;
    private int omx,omy,omw,omh;
    /**
     * 移动边框
     */
    private int draggedIndex;
    /**
     * 文本属性对话框窗口对象
     */
    private TWindow propertyDialog;
    private boolean oldLineFX,oldLineFY;
    /**
     * 设置父类
     * @param parent BlockPI
     */
    public void setParent(BlockPI parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return BlockPI
     */
    public BlockPI getParent()
    {
        return parent;
    }
    public void setEditSelect(boolean editSelect)
    {
        this.editSelect = editSelect;
    }
    public boolean isEditSelect()
    {
        return editSelect;
    }
    /**
     * 绘制
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int x,int y,int width,int height)
    {
        //绘制背景
        paintBK(g,x,y,width,height);
        //绘制边框
        paintBorder(g,x,y,width,height);

        //绘制文本
        paintText(g,x,y,width,height);
        //绘制选中标记
        paintEditSelect(g,x,y,width,height);
    }
    /**
     * 打印
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void print(Graphics g,int x,int y,int width,int height)
    {
        //绘制背景
        printBK(g,x,y,width,height);
        //绘制边框
        printBorder(g,x,y,width,height);
        //绘制文本
        printText(g,x,y,width,height);
    }
    /**
     * 绘制边框
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void printBorder(Graphics g,int x,int y,int width,int height)
    {
        if(isBorderVisible() && getBorderColor() != null)
        {
            g.setColor(getBorderColor());
            Stroke oldStroke = ((Graphics2D)g).getStroke();
            ((Graphics2D)g).setStroke(getStroke());
            g.drawRect(x, y, width, height);
            ((Graphics2D)g).setStroke(oldStroke);
        }
    }
    /**
     * 绘制边框
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintBorder(Graphics g,int x,int y,int width,int height)
    {
        if(isBorderVisible() && getBorderColor() != null)
        {
            g.setColor(getBorderColor());
            Stroke oldStroke = ((Graphics2D)g).getStroke();
            ((Graphics2D)g).setStroke(getStroke());
            g.drawRect(x, y, width, height);
            ((Graphics2D)g).setStroke(oldStroke);
        }
    }
    /**
     * 打印背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void printBK(Graphics g,int x,int y,int width,int height)
    {
        if(getBkColor() != null)
        {
            g.setColor(getBkColor());
            g.fillRect(x, y, width, height);
        }
        if(getIcon() != null)
        {
            int x1 = x;
            int y1 = y;
            int w = width;
            int h = height;
            if(!isPictureAutoSize())
            {
                double d1 = (double) width / (double) getIcon().getIconWidth();
                double d2 = (double) height / (double) getIcon().getIconHeight();
                if (d1 < d2)
                {
                    h = (int) ((double) getIcon().getIconHeight() * d1);
                    y1 += (height - h) / 2;
                } else
                {
                    w = (int) ((double) getIcon().getIconWidth() * d2);
                    x1 += (width - w) / 2;
                }
            }
            g.drawImage(((ImageIcon) getIcon()).getImage(),x1,y1,w,h,null);
            /*ImageIcon icon = new ImageIcon(((ImageIcon) getIcon()).getImage().
                                           getScaledInstance(w, h,
                    Image.SCALE_FAST));
            icon.paintIcon((Component) getUI().getTCBase(), g, x1,y1);*/
        }
    }
    /**
     * 绘制背景
     * %ROOT%\image\ImageIcon\bk-01.jpg
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintBK(Graphics g,int x,int y,int width,int height)
    {
        if(getBkColor() != null)
        {
            g.setColor(getBkColor());
            g.fillRect(x, y, width, height);
        }
        if(getIcon() != null)
        {
            int x1 = x;
            int y1 = y;
            int w = width;
            int h = height;
            if(!isPictureAutoSize())
            {
                double d1 = (double) width / (double) getIcon().getIconWidth();
                double d2 = (double) height / (double) getIcon().getIconHeight();
                if (d1 < d2)
                {
                    h = (int) ((double) getIcon().getIconHeight() * d1);
                    y1 += (height - h) / 2;
                } else
                {
                    w = (int) ((double) getIcon().getIconWidth() * d2);
                    x1 += (width - w) / 2;
                }
            }
            ImageIcon icon = new ImageIcon(((ImageIcon) getIcon()).getImage().
                                           getScaledInstance(w, h,
                    Image.SCALE_FAST));
            icon.paintIcon((Component) getUI().getTCBase(), g, x1,y1);
        }
    }
    /**
     * 绘制文本
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void printText(Graphics g,int x,int y,int width,int height)
    {
        if(getColor() == null)
            return;
        g.setColor(getColor());
        Font font = getDrawFont(new Font(getFont().getName(),getFont().getStyle(),
                                         (int)(getFont().getSize2D())));
        g.setFont(font);
        FontMetrics fontMetrics = DFont.getFontMetrics(getFont());
        CStringDraw csd= new CStringDraw(g,fontMetrics,x,y,
                                         width,height,getText(),getZoom(),getAutoEnterHeight(),
                                         isLine(),getAlignment(),getAlignmentH(),getTextEnterMode(),true/*isPrint*/);
        csd.draw();
    }
    /**
     * 绘制文本
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintText(Graphics g,int x,int y,int width,int height)
    {
        if(getColor() == null)
            return;
        g.setColor(getColor());
        double zoom = getZoom() / 75.0;
        Font font = getDrawFont(new Font(getFont().getName(),getFont().getStyle(),
                                         (int)(getFont().getSize2D() * zoom + 0.5)));
        g.setFont(font);
        FontMetrics fontMetrics = DFont.getFontMetrics(getFont());
        CStringDraw csd= new CStringDraw(g,fontMetrics,x,y,
                                         width,height,getText(),getZoom(),getAutoEnterHeight(),
                                         isLine(),getAlignment(),getAlignmentH(),getTextEnterMode(),false/*isPrint*/);
        csd.draw();
    }
    /**
     * 绘制选中标记
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintEditSelect(Graphics g,int x,int y,int width,int height)
    {
        if(!isEditSelect())
            return;
        g.setColor(new Color(255, 255, 255));
        int len = 6;
        int f = getMoveF();
        if((f & (1 << 4)) > 0)
            g.fillOval(x - len / 2, y - len / 2, len, len);
        if((f & (1 << 5)) > 0)
            g.fillOval(x + width - len / 2, y - len / 2, len, len);
        if((f & (1 << 6)) > 0)
            g.fillOval(x + width - len / 2, y + height - len / 2, len, len);
        if((f & (1 << 7)) > 0)
            g.fillOval(x - len / 2, y + height - len / 2, len, len);

        if((f & 1) > 0)
            g.fillOval(x + width / 2 - len / 2, y - len / 2, len, len);
        if((f & (1 << 1)) > 0)
            g.fillOval(x + width / 2 - len / 2, y + height - len / 2, len, len);
        if((f & (1 << 2)) > 0)
            g.fillOval(x - len / 2, y + height / 2 - len / 2, len, len);
        if((f & (1 << 3)) > 0)
            g.fillOval(x + width - len / 2, y + height / 2 - len / 2, len, len);

        g.setColor(new Color(0, 0, 0));
        if((f & (1 << 4)) > 0)
            g.drawOval(x - len / 2, y - len / 2, len, len);
        if((f & (1 << 5)) > 0)
            g.drawOval(x + width - len / 2, y - len / 2, len, len);
        if((f & (1 << 6)) > 0)
            g.drawOval(x + width - len / 2, y + height - len / 2, len, len);
        if((f & (1 << 7)) > 0)
            g.drawOval(x - len / 2, y + height - len / 2, len, len);

        if((f & 1) > 0)
            g.drawOval(x + width / 2 - len / 2, y - len / 2, len, len);
        if((f & (1 << 1)) > 0)
            g.drawOval(x + width / 2 - len / 2, y + height - len / 2, len, len);
        if((f & (1 << 2)) > 0)
            g.drawOval(x - len / 2, y + height / 2 - len / 2, len, len);
        if((f & (1 << 3)) > 0)
            g.drawOval(x + width - len / 2, y + height / 2 - len / 2, len, len);
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom()
    {
        return getParent().getZoom();
    }
    /**
     * 得到鼠标所在组件
     * @param mouseX int
     * @param mouseY int
     * @return int
     */
    public int getMouseInComponentIndexXY(int mouseX,int mouseY)
    {
        int len = 6;
        double zoom = getZoom() / 75.0;
        int width = (int) (getWidth() * zoom);
        int height = (int) (getHeight() * zoom);
        int f = getMoveF();
        //左上
        if((f & (1 << 4)) > 0 && mouseX >= len / 2 * -1 && mouseX <= len / 2 &&
           mouseY > len / 2 * -1 && mouseY <= len / 2)
            return 5;
        //上
        if((f & 1) > 0 && mouseX >= width / 2 - len / 2 && mouseX <= width / 2 + len / 2 &&
           mouseY > len / 2 * -1 && mouseY <= len / 2)
            return 1;
        //右上
        if((f & (1 << 5)) > 0 && mouseX >= width - len / 2 && mouseX <= width + len / 2 &&
           mouseY > len / 2 * -1 && mouseY <= len / 2)
            return 6;
        //左
        if((f & (1 << 2)) > 0 && mouseX >= len / 2 * -1 && mouseX <= len / 2 &&
           mouseY >= height / 2 - len / 2 && mouseY <= height / 2 + len / 2)
            return 3;
        //左下
        if((f & (1 << 7)) > 0 && mouseX >= len / 2 * -1 && mouseX <= len / 2 &&
           mouseY >= height - len / 2 && mouseY <= height + len / 2)
            return 8;
        //下
        if((f & (1 << 1)) > 0 && mouseX >= width / 2 - len / 2 && mouseX <= width / 2 + len / 2 &&
           mouseY >= height - len / 2 && mouseY <= height + len / 2)
            return 2;
        //右
        if((f & (1 << 3)) > 0 && mouseX >= width - len / 2 && mouseX <= width + len / 2 &&
            mouseY >= height / 2 - len / 2 && mouseY <= height / 2 + len / 2)
            return 4;
        //右下
        if((f & (1 << 6)) > 0 && mouseX >= width - len / 2 && mouseX <= width + len / 2 &&
           mouseY >= height - len / 2 && mouseY <= height + len / 2)
            return 7;
        //移动
        if((f & (1 << 8)) > 0 && mouseX >= len / 2 * -1 && mouseX <= width + len / 2 &&
           mouseY >= len / 2 * -1 && mouseY <= height + len / 2)
            return 9;
        //线移动
        if((f & (1 << 9)) > 0 && isSelectLineCheck((int)(mouseX * 75.0 / getZoom()), (int)(mouseY * 75.0 / getZoom())))
            return 9;
        return 0;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getParent().getPM();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
            return null;
        return getPM().getUI();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    public void setCursor(int index)
    {
        switch(index)
        {
        case 1:
        case 2:
            getUI().setCursor(DCursor.N_RESIZE_CURSOR);
            return;
        case 3:
        case 4:
            getUI().setCursor(DCursor.E_RESIZE_CURSOR);
            return;
        case 5:
        case 7:
            getUI().setCursor(DCursor.NW_RESIZE_CURSOR);
            return;
        case 6:
        case 8:
            getUI().setCursor(DCursor.NE_RESIZE_CURSOR);
            return;
        case 9:
            getUI().setCursor(DCursor.MOVE_CURSOR);
            return;
        }
    }
    public boolean onMouseMoved(int mouseX,int mouseY)
    {
        draggedIndex = getMouseInComponentIndexXY(mouseX,mouseY);
        if(draggedIndex > 0)
        {
            setCursor(draggedIndex);
            return true;
        }
        return false;
    }
    public void saveOldSize()
    {
        omy = getY();
        omx = getX();
        omw = getWidth();
        omh = getHeight();
    }
    /**
     * 测试坐标在组件内部
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean isSelectCheck(int x,int y)
    {
        return x >= getX() && x <= getX() + getWidth() &&
                y >= getY() && y <= getY() + getHeight();
    }
    public boolean onMouseLeftPressed(int mouseX,int mouseY)
    {
        oldLineFX = isLineFX();
        oldLineFY = isLineFY();
        return true;
    }
    private void setMoveSize(int index,int moveX,int moveY)
    {
        switch(index)
        {
        case 1:
            if(omh - moveY > 0)
            {
                setY(omy + moveY);
                setHeight(omh - moveY);
                setLineFY(oldLineFY);
            }else
            {
                setY(omy + omh);
                setHeight(moveY - omh);
                setLineFY(!oldLineFY);
            }
            break;
        case 2:
            if(omh + moveY > 0)
            {
                setY(omy);
                setHeight(omh + moveY);
                setLineFY(oldLineFY);
            }
            else
            {
                setY(omy + omh + moveY);
                setHeight((moveY + omh) * -1);
                setLineFY(!oldLineFY);
            }
            break;
        case 3:
            if(omw - moveX > 0)
            {
                setX(omx + moveX);
                setWidth(omw - moveX);
                setLineFX(oldLineFX);
            }else
            {
                setX(omx + omw);
                setWidth(moveX - omw);
                setLineFX(!oldLineFX);
            }
            break;
        case 4:
            if(omw + moveX > 0)
            {
                setX(omx);
                setWidth(omw + moveX);
                setLineFX(oldLineFX);
            }else
            {
                setX(omx + omw + moveX);
                setWidth((moveX + omw) * -1);
                setLineFX(!oldLineFX);
            }
            break;
        case 5:
            if(omw - moveX > 0)
            {
                setX(omx + moveX);
                setWidth(omw - moveX);
                setLineFX(oldLineFX);
            }else
            {
                setX(omx + omw);
                setWidth(moveX - omw);
                setLineFX(!oldLineFX);
            }
            if(omh - moveY > 0)
            {
                setY(omy + moveY);
                setHeight(omh - moveY);
                setLineFY(oldLineFY);
            }else
            {
                setY(omy + omh);
                setHeight(moveY - omh);
                setLineFY(!oldLineFY);
            }
            break;
        case 6:
            if(omw + moveX > 0)
            {
                setX(omx);
                setWidth(omw + moveX);
                setLineFX(oldLineFX);
            }else
            {
                setX(omx + omw + moveX);
                setWidth((moveX + omw) * -1);
                setLineFX(!oldLineFX);
            }
            if(omh - moveY > 0)
            {
                setY(omy + moveY);
                setHeight(omh - moveY);
                setLineFY(oldLineFY);
            }else
            {
                setY(omy + omh);
                setHeight(moveY - omh);
                setLineFY(!oldLineFY);
            }
            break;
        case 7:
            if(omw + moveX > 0)
            {
                setX(omx);
                setWidth(omw + moveX);
                setLineFX(oldLineFX);
            }else
            {
                setX(omx + omw + moveX);
                setWidth((moveX + omw) * -1);
                setLineFX(!oldLineFX);
            }
            if(omh + moveY > 0)
            {
                setY(omy);
                setHeight(omh + moveY);
                setLineFY(oldLineFY);
            }
            else
            {
                setY(omy + omh + moveY);
                setHeight((moveY + omh) * -1);
                setLineFY(!oldLineFY);
            }
            break;
        case 8:
            if(omw - moveX > 0)
            {
                setX(omx + moveX);
                setWidth(omw - moveX);
                setLineFX(oldLineFX);
            }else
            {
                setX(omx + omw);
                setWidth(moveX - omw);
                setLineFX(!oldLineFX);
            }
            if(omh + moveY > 0)
            {
                setY(omy);
                setHeight(omh + moveY);
                setLineFY(oldLineFY);
            }
            else
            {
                setY(omy + omh + moveY);
                setHeight((moveY + omh) * -1);
                setLineFY(!oldLineFY);
            }
            break;
        case 9:
            setX(omx + moveX);
            setY(omy + moveY);
            break;
        }
    }
    /**
     * 鼠标拖动
     * @param moveX int
     * @param moveY int
     */
    public void onMouseDragged(int moveX,int moveY)
    {
        setMoveSize(draggedIndex,moveX,moveY);
        double sx = moveX;
        double sy = moveY;
        if(draggedIndex != 9)
        {
            if (omh == 0)
                sy = moveY;
            else
                sy = (double) moveY / (double) omh;
            if (omw == 0)
                sx = moveX;
            else
                sx = (double) moveX / (double) omw;
        }
        getParent().onMouseDraggedOther(draggedIndex,sx,sy,this);
    }
    /**
     * 随动比例
     * @param index int
     * @param x double
     * @param y double
     */
    public void onMouseDraggedOther(int index,double x,double y)
    {
        int moveX = (int) x;
        int moveY = (int) y;
        if(index != 9)
        {
            moveX = (int) ((double) omw * x);
            moveY = (int) ((double) omh * y);
        }
        setMoveSize(index,moveX,moveY);
    }
    /**
     * 右键按下
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX,int mouseY)
    {
        String syntax = "剪切,onCut;拷贝,onCopy;删除,onDelete;|;属性,propertyDialog";
        popupMenu(syntax);
    }
    /**
     * 删除
     */
    public void onDelete()
    {
        ((EImage)getParent()).exeAction(new EAction(EAction.DELETE));
    }
    /**
     * 拷贝
     */
    public void onCopy()
    {
        ((EImage)getParent()).exeAction(new EAction(EAction.COPY));
    }
    /**
     * 剪切
     */
    public void onCut()
    {
        ((EImage)getParent()).exeAction(new EAction(EAction.CUT));
    }
    /**
     * 属性对话框
     */
    public void propertyDialog()
    {
        openProperty("%ROOT%\\config\\database\\BlockTextEdit.x",propertyDialog);
    }
    /**
     * 右键菜单
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
     * 复制对象
     * @return GBlock
     */
    public GBlock copyObject()
    {
        GBlock block = new GBlock();
        copyObject(block);
        return block;
    }
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
    }
    /**
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message, "=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }
    /**
     * 处理消息
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
     * 弹出对话框提示消息
     * @param message Object
     */
    public void messageBox(Object message){
        getUI().messageBox(message);
    }
    /**
     * 打开属性对话框
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
     * 更新
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeBoolean(1,isVisible(),true);
        s.writeString(2,getName(),"");
        s.writeInt(3,getX(),0);
        s.writeInt(4,getY(),0);
        s.writeInt(5,getWidth(),0);
        s.writeInt(6,getHeight(),0);
        s.writeInt(7,getColor() == null?-1:getColor().getRGB(),0);
        s.writeInt(8,getBkColor() == null?-1:getBkColor().getRGB(),0);
        s.writeInt(9,getBorderColor() == null?-1:getBorderColor().getRGB(),0);
        s.writeShort(10);
        s.writeString(getFontName());
        s.writeInt(getFontStyle());
        s.writeInt(getFontSize());
        s.writeDouble(getFontRotate());
        s.writeInt(11,getAlignment(),0);
        s.writeInt(12,getAlignmentH(),0);
        s.writeBoolean(13,isLine(),false);
        s.writeInt(14,getAutoEnterHeight(),0);
        s.writeBoolean(15,isBorderVisible(),true);
        s.writeFloat(16,getBorderWidth(),0);
        s.writeInt(17,getBorderLineType(),0);
        s.writeString(18,getPictureName(),null);
        s.writeBoolean(19,isPictureAutoSize(),false);
        s.writeBoolean(20,isLineFX(),false);
        s.writeBoolean(21,isLineFY(),false);
        s.writeInt(22,getArrow1(),0);
        s.writeInt(23,getArrow2(),0);
        s.writeInt(24,getArrow1Length(),0);
        s.writeInt(25,getArrow2Length(),0);
        s.writeDouble(26,getArrow1Degree(),0);
        s.writeDouble(27,getArrow2Degree(),0);
        s.writeInt(28,getTextEnterMode(),0);
        s.writeString(29,getText(),"");
        s.writeBoolean(30,isEnabled(),true);
        s.writeShort(31);
        s.writeInt(sizeData());
        for(int i = 0;i < sizeData();i++)
        {
            Vector data = (Vector)getData().get(i);
            s.writeInt(data.size());
            for(int j = 0;j < data.size();j++)
                s.writeString((String)data.get(j));
        }
    }
    /**
     * 读对象属性
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
            setX(s.readInt());
            return true;
        case 4:
            setY(s.readInt());
            return true;
        case 5:
            setWidth(s.readInt());
            return true;
        case 6:
            setHeight(s.readInt());
            return true;
        case 7:
            int v = s.readInt();
            setColor(v == -1?null:new Color(v));
            return true;
        case 8:
            v = s.readInt();
            setBkColor(v == -1?null:new Color(v));
            return true;
        case 9:
            v = s.readInt();
            setBorderColor(v == -1?null:new Color(v));
            return true;
        case 10:
            setFont(new Font(s.readString(),s.readInt(),s.readInt()));
            setFontRotate(s.readDouble());
            return true;
        case 11:
            setAlignment(s.readInt());
            return true;
        case 12:
            setAlignmentH(s.readInt());
            return true;
        case 13:
            setIsLine(s.readBoolean());
            return true;
        case 14:
            setAutoEnterHeight(s.readInt());
            return true;
        case 15:
            setBorderVisible(s.readBoolean());
            return true;
        case 16:
            setBorderWidth(s.readFloat());
            return true;
        case 17:
            setBorderLineType(s.readInt());
            return true;
        case 18:
            setPictureName(s.readString());
            return true;
        case 19:
            setPictureAutoSize(s.readBoolean());
            return true;
        case 20:
            setLineFX(s.readBoolean());
            return true;
        case 21:
            setLineFY(s.readBoolean());
            return true;
        case 22:
            setArrow1(s.readInt());
            return true;
        case 23:
            setArrow2(s.readInt());
            return true;
        case 24:
            setArrow1Length(s.readInt());
            return true;
        case 25:
            setArrow2Length(s.readInt());
            return true;
        case 26:
            setArrow1Degree(s.readDouble());
            return true;
        case 27:
            setArrow2Degree(s.readDouble());
            return true;
        case 28:
            setTextEnterMode(s.readInt());
            return true;
        case 29:
            setText(s.readString());
            return true;
        case 30:
            setEnabled(s.readBoolean());
            return true;
        case 31:
            int count = s.readInt();
            for(int i = 0;i < count;i++)
            {
                Vector d = new Vector();
                int c = s.readInt();
                for(int j = 0;j < c;j++)
                {
                    d.add(s.readString());
                }
                getData().add(d);
            }
            return true;
        }
        return false;
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //读对象属性
            readObjectAttribute(id,s);
            id = s.readShort();
        }
    }
    /**
     * 得到类型编号
     * @return int
     */
    public int getTypeID()
    {
        return 0;
    }
    public DPoint getScreenPoint()
    {
        DPoint point = ((EImage)getParent()).getPanel().getScreenPoint();
        point.setX(point.getX() + (int)(getX() * getZoom() / 75.0 + 0.5));
        point.setY(point.getY() + (int)(getY() * getZoom() / 75.0 + 0.5));
        return point;
    }
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        DPoint point = getScreenPoint();
        int h = (int)(getHeight() * getZoom() / 75.0 + 0.5) + 3;
        Dimension dimension = ImageTool.getScreenSize();
        TWindow propertyWindow = (TWindow)getPM().getUI().openWindow("%ROOT%\\config\\database\\GBlockSingleChoosePopMenu.x", this ,true);
        int pw = propertyWindow.getWidth();
        int ph = propertyWindow.getHeight();
        int x = point.getX();
        if(x < 0)
            x = 0;
        if(x + pw > dimension.getWidth())
            x = (int)dimension.getWidth() - pw;
        int y = point.getY();
        if(y + h + ph > dimension.getHeight())
            y = y - ph - 3;
        else
            y = y + h;
        if(y < 0)
            y = 0;
        propertyWindow.setX(x);
        propertyWindow.setY(y);
        propertyWindow.setVisible(true);
        getPM().getEventManager().setPropertyMenuWindow(propertyWindow);
        return true;
    }
    public GBlock clone(EImage image)
    {
        GBlock block = new GBlock();
        if(this instanceof GLine)
            block = new GLine();
        block.setParent(image);
        block.setX(getX());
        block.setY(getY());
        block.setWidth(getWidth());
        block.setHeight(getHeight());
        block.setVisible(isVisible());
        block.setName(getName());
        block.setColor(getColor());
        block.setBkColor(getBkColor());
        block.setBorderColor(getBorderColor());
        block.setFont(getFont());
        block.setFontRotate(getFontRotate());
        block.setAlignment(getAlignment());
        block.setAlignmentH(getAlignmentH());
        block.setIsLine(isLine());
        block.setAutoEnterHeight(getAutoEnterHeight());
        block.setBorderVisible(isBorderVisible());
        block.setBorderWidth(getBorderWidth());
        block.setBorderLineType(getBorderLineType());
        block.setPictureName(getPictureName());
        block.setPictureAutoSize(isPictureAutoSize());
        block.setLineFX(isLineFX());
        block.setLineFY(isLineFY());
        block.setArrow1(getArrow1());
        block.setArrow2(getArrow2());
        block.setArrow1Length(getArrow1Length());
        block.setArrow2Length(getArrow2Length());
        block.setArrow1Degree(getArrow1Degree());
        block.setArrow2Degree(getArrow2Degree());
        block.setTextEnterMode(getTextEnterMode());
        block.setText(getText());
        block.setEnabled(isEnabled());
        for(int i = 0;i < sizeData();i++)
        {
            Vector data = (Vector)getData().get(i);
            block.addData((String)data.get(0),(String)data.get(1));
        }
        return block;
    }
}
