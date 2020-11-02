package com.dongyang.tui;

import com.dongyang.tui.base.DFrameBase;
import com.dongyang.ui.base.TCBase;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Insets;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.control.DFrameControl;
import com.dongyang.tui.control.DComponentControl;
import java.awt.Font;
import com.dongyang.util.TList;
import javax.swing.JFrame;

/**
 * <p>Title: 窗体</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.4
 * @version 1.0
 */
public class DFrame extends DComponent
{
    /**
     * 窗体宿主
     */
    private DFrameBase frameBase;
    /**
     * 地板宿主
     */
    private TCBase tcBase;
    /**
     * 构造器
     */
    public DFrame()
    {
        setBounds(10, 10, 500, 500);
        setBKColor(1,255,255);
        //setBorder("雕刻");
        setTag("DFrame");
        //setText("aaaaa");
        setHorizontalAlignment("中");
        setVerticalAlignment("中");
        //设置为顶级对象
        setTopComponent(true);
        //共享方法
        addPublicMethod("close",this);
    }
    /**
     * 设置标题
     * @param title String
     */
    public void setTitle(String title)
    {
        if(frameBase != null)
            frameBase.setTitle(title);
        if(title == null || title.length() == 0)
        {
            attribute.remove("D_title");
            return;
        }
        attribute.put("D_title",title);
    }
    /**
     * 得到标题
     * @return String
     */
    public String getTitle()
    {
        return (String)attribute.get("D_title");
    }
    /**
     * 得到窗体接口
     * @return DFrameControl
     */
    public DFrameControl getIOFrame()
    {
        DComponentControl control = getIOControlObject();
        if(control == null || !(control instanceof DFrameControl))
            return null;
        return (DFrameControl)control;
    }
    /**
     * 是否监听父类尺寸
     * @return boolean true 在监听 false 不再监听
     */
    public boolean isListenerParentResized()
    {
        return true;
    }
    /**
     * 得到组件尺寸
     * @return DRectangle
     */
    public DRectangle getComponentBounds()
    {
        if(frameBase != null)
        {
            Rectangle r = tcBase.getBounds();
            Insets insetsBase = tcBase.getInsets();
            return new DRectangle(0,0,r.width - insetsBase.left - insetsBase.right,
                                  r.height - insetsBase.top - insetsBase.bottom);
        }
        return getBounds();
    }
    /**
     * 得到窗口尺寸
     */
    public void setWindowSize()
    {
        if(frameBase == null)
            return;
        getBounds().setBounds(frameBase.getBounds());
    }
    /**
     * 窗口尺寸改变事件
     */
    public void onWindowResized()
    {
        setWindowSize();
    }
    /**
     * 窗口移动事件
     */
    public void onWindowMoved()
    {
        setWindowSize();
        super.onWindowMoved();
    }
    /**
     * 显示
     */
    public void open()
    {
        com.dongyang.util.DebugUsingTime.add("open");
        initSystemUIManager();
        if(tcBase == null)
        {
            tcBase = new TCBase();
            tcBase.onInit();
            tcBase.addDComponent(this);
            //tcBase.setBorder(BorderFactory.createLoweredBevelBorder());
            //tcBase.setBackground(new Color(255,100,100));
        }
        com.dongyang.util.DebugUsingTime.add("new TCBase ");
        if(frameBase == null)
        {
            frameBase = new DFrameBase(this);
            frameBase.getContentPane().add(tcBase);
        }else
        {
            //设置窗口位置
            frameBase.setLocation(getX(),getY());
            //设置窗口尺寸
            frameBase.setSize(getWidth(),getHeight());
        }
        com.dongyang.util.DebugUsingTime.add("new Frame ");
        String bkColor = getBKColor();
        if(bkColor != null && bkColor.length() > 0)
        {
            Color color = DColor.getColor(bkColor);
            tcBase.setBackground(color);
            frameBase.setBackground(color);
        }
        //打开事件
        if(!onOpen())
            return;
        frameBase.setVisible(isVisible());
        tcBase.grabFocus();
        com.dongyang.util.DebugUsingTime.add("open DFrame ");
    }
    /**
     * 打开事件
     * @return boolean
     */
    public boolean onOpen()
    {
        DFrameControl control = getIOFrame();
        if(control != null)
            return control.onOpen();
        return true;
    }
    /**
     * 关闭动作
     */
    public void close()
    {
        if(!onClose())
            return;
        frameBase.dispose();
        //关闭之后事件
        onClosed();
    }
    /**
     * 关闭事件
     * @return boolean
     */
    public boolean onClose()
    {
        DFrameControl control = getIOFrame();
        if(control != null)
            return control.onClose();
        return true;
    }
    /**
     * 关闭之后事件
     */
    public void onClosed()
    {
        DFrameControl control = getIOFrame();
        if(control != null)
            control.onClosed();
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DFrame>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        super.writeObjectAttribute(s);
        s.writeString(super.getAttributeIDMax() + 1,getTitle(),null);
        s.writeString(super.getAttributeIDMax() + 2,getTitle(),null);
        //s.writeString(super.getAttributeIDMax() + 2,getHorizontalAlignment(),null);
        //s.writeString(super.getAttributeIDMax() + 3,getVerticalAlignment(),null);
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
        if(super.readObjectAttribute(id,s))
            return true;
        switch (id - super.getAttributeIDMax())
        {
        case 1:
            setTitle(s.readString());
            return true;
        case 2:
            setTitle(s.readString());
            return true;
        }
        return false;
    }
    /**
     * 双击
     * @return boolean
     */
    public boolean onDoubleClickedS()
    {
        super.onDoubleClickedS();
        try{
            com.dongyang.util.FileTool.setDObject("D:\\lzk\\Project\\test\\test.w", this);
        }catch(Exception e)
        {

        }
        return true;
    }
    /**
     * 能够得到焦点
     * @return boolean
     */
    public boolean canFocus()
    {
        return true;
    }
    /**
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint()
    {
        if(tcBase == null)
            return super.getScreenPoint();
        DPoint point = tcBase.getScreenPoint();
        Insets insets = tcBase.getInsets();
        DRectangle rectangle = getComponentBounds();
        point.x += insets.left + rectangle.getX();
        point.y += insets.top + rectangle.getY();
        return point;
    }
    public static void main(String args[])
    {
        com.dongyang.util.TDebug.initClient();
        try{
            //Font font = new Font("宋体",0,12);

            com.dongyang.util.DebugUsingTime.start();
            //DFrame f = (DFrame)com.dongyang.util.FileTool.getDObject("D:\\lzk\\Project\\test\\test.w");
            /*DFrame f = new DFrame();

            DButton b = new DButton();
            b.setBounds(10,10,100,100);
            b.setTag("button1");
            b.setText("button1");
            //b.setIOControl("com.dongyang.tui.control.DButtonControl");

            //b.initIOControl();
            //b.setActionMap("onClicked","close");
            f.addDComponent(b);

            System.out.println(f.getBounds());
            f.setIOControl("com.dongyang.tui.control.DFrameControl");
            f.initIOControl();
            f.setTitle("D组件窗口");

            //b.setLinkComponent(3,f,7,0);
            //b.setLinkComponent(4,f,8,0);
            b.setLinkComponent(1,f,6,20);
            b.setLinkComponent(3,f,8,100);

            for(int i = 0;i < 1;i++)
            {
                DButton b1 = new DButton();
                b1.setBounds(10, 200, 100, 100);
                b1.setTag("button2");
                b1.setText("button2");
                //b1.setIOControl("com.dongyang.tui.control.DButtonControl");
                //b1.initIOControl();
                f.addDComponent(b1);
                //b1.setLinkComponent(4, f, 7, 100);
                //b1.setLinkComponent(4,f,8,0);
                //b1.setLinkComponent(1, f, 6, 30);
            }
            //DScrollBarButton d = new DScrollBarButton();
            //d.setBounds(100,100,16,17);
            //f.addDComponent(d);
            DScrollBar d = new DScrollBar();
            d.setTag("W1");
            d.setBounds(10,10,17,200);
            d.setMinimum(10);
            d.setMaximum(100);
            d.setVisibleAmount(30);
            d.setValue(20);
            f.addDComponent(d);
            d = new DScrollBar();
            d.setTag("W2");
            d.setBounds(30,30,200,17);
            d.setOrientation(d.HORIZONTAL);
            d.setMinimum(30);
            d.setMaximum(100);
            d.setVisibleAmount(10);
            d.setValue(20);
            f.addDComponent(d);*/
            //System.out.println(f.findComponent("W2").toString());


            DFrame f = new DFrame();
            com.dongyang.util.DebugUsingTime.add("create DFrame");
            DText text = new DText();
            text.setTag("text");
            text.setBorder("凹");
            text.setBounds(10,10,400,400);
            f.addDComponent(text);
            text.setLinkComponent(1,f,5,5);
            text.setLinkComponent(2,f,6,20);
            text.setLinkComponent(3,f,7,5);
            text.setLinkComponent(4,f,8,5);
            com.dongyang.util.DebugUsingTime.add("create DText");

            /*DMenuBar mb = new DMenuBar();
            mb.setTag("MenuBar");
            //mb.setText("DMenuBar");
            mb.setBounds(10, 10, 100, 60);
            //mb.setBorder("line");
            f.addDComponent(mb);
            mb.setLinkComponent(1,f,5,0);
            mb.setLinkComponent(3,f,7,0);
            mb.setLinkComponent(4,f,8,0);

            DMenu m1 = new DMenu();
            m1.setTag("文件");
            m1.setText("文件");
            m1.setBounds(0,0,60,40);
            m1.setMnemonic('f');
            //m1.setFont("宋体,0,12");
            mb.addDComponent(m1);
            //m1.resetSize();

            DMenu m2 = new DMenu();
            m2.setTag("编辑");
            m2.setText("编辑");
            m2.setBounds(200,10,60,40);
            m2.setMnemonic('e');
            //m2.setFont("宋体,0,12");
            //m2.setEnabled(false);
            mb.addDComponent(m2);
            //m2.resetSize();

            for(int i = 0;i < 2;i++)
            {
                DMenu mt = new DMenu();
                mt.setTag("菜单" + i);
                mt.setText("菜单" + i);
                mt.setBounds(200, 10, 60, 40);
                mt.setMnemonic('e');
                //mt.setFont("宋体,0,12");
                mb.addDComponent(mt);
            }
            mb.resetSize();

            DPopupMenu pm1 = new DPopupMenu();
            m1.setPopupMenu(pm1);

            DMenu m3 = new DMenu();
            //m3.setFont("宋体,0,12");
            m3.setTag("M3");
            m3.setText("M3");
            m3.setMnemonic('m');
            //m3.setBounds(0,0,60,20);
            //m2.setBorder("line");
            pm1.addDComponent(m3);

            DPopupMenu pm3 = new DPopupMenu();
            DMenu m5 = new DMenu();
            //m5.setFont("宋体,0,12");
            m5.setTag("子菜单");
            m5.setText("子菜单");
            m5.setMnemonic('m');
            pm3.addDComponent(m5);

            DMenuItem mi2 = new DMenuItem();
            mi2.setTag("子菜单");
            mi2.setText("子菜单");
            mi2.setMnemonic('m');
            pm3.addDComponent(mi2);

            m3.setPopupMenu(pm3);



            DSeparator ds = new DSeparator();
            pm1.addDComponent(ds);

            DMenuItem mi1 = new DMenuItem();
            //mi1.setFont("宋体,0,12");
            mi1.setTag("MI1");
            mi1.setText("打开");
            mi1.setMnemonic('O');
            mi1.setKey("Ctrl+Shift+A");
            mi1.setBounds(0,30,60,20);
            pm1.addDComponent(mi1);

            DMenuItem mi3 = new DMenuItem();
            mi3.setTag("MI1");
            mi3.setText("关闭");
            mi3.setMnemonic('O');
            mi3.setKey("Ctrl+Shift+A");
            mi3.setBounds(0,30,60,20);
            mi3.setActionMap("onClicked","close");
            pm1.addDComponent(mi3);


            DPopupMenu pm2 = new DPopupMenu();
            pm2.setBounds(100, 100, 100, 100);
            //pm2.setBorder("line");
            //pm2.setText("PopupMenu");
            //f.addDComponent(pm);

            DMenu m4 = new DMenu();
            //m4.setFont("宋体,0,12");
            m4.setTag("M4");
            m4.setText("M4");
            m4.setMnemonic('m');
            m4.setBounds(0, 0, 60, 20);
            //m2.setBorder("line");
            pm2.addDComponent(m4);

            m2.setPopupMenu(pm2);

            DButton b1 = new DButton();
            b1.setBounds(10, 200, 100, 100);
            b1.setTag("button1");
            b1.setText("button1");
            f.addDComponent(b1);
            b1.setLinkComponent(1,f,6,20);

            DButton b2 = new DButton();
            b2.setBounds(120, 200, 100, 100);
            b2.setTag("button2");
            b2.setText("button2");
            f.addDComponent(b2);
            b2.setLinkComponent(1,f,6,20);

            com.dongyang.util.DebugUsingTime.add("create DButton");*/



            //f.setUIEditStatus(true);
            f.open();
            /*JFrame  f1 = new JFrame();
            f1.setVisible(true);*/
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        /*DFrame frame = new DFrame();

        DButton b = new DButton();
        b.setBounds(10,10,100,100);
        b.setTag("aaaaa");
        b.setText("aaaaa");
        frame.addDComponent(b);
        frame.open();*/
    }
}
