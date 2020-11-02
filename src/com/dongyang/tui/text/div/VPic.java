package com.dongyang.tui.text.div;

import javax.swing.Icon;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Image;
import com.dongyang.manager.TIOM_FileServer;
import com.dongyang.util.StringTool;
import com.dongyang.util.TypeTool;
import com.dongyang.data.TParm;
import java.awt.Color;

/**
 *
 * <p>Title: 图片</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.26
 * @author whao 2013~
 * @version 1.0
 */
public class VPic extends VTable
{
    /**
     * 图片名称
     */
    private String pictureName;
    private Icon icon;
    /**
     * 图片序号
     */
    private int iconIndex = -1;
    /**
     * 是否去病案号
     */
    private boolean isMrnoFS;
    /**
     * 缩放比例
     */
    private double scale = 100;
    /**
     * 自动缩放
     */
    private boolean autoScale;

    /**
     * 背景图图片名称
     */
    private String pictureNameBackground;
    private Icon iconBackground;

    /**
     * 前景图片名称
     */
    private String pictureNameForeground;
    private byte[] iconForeground;

    /**
     * 构造器
     */
    public VPic()
    {
    }
    /**
     * 构造器
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public VPic(int x,int y,int width,int height)
    {
        super(x,y,width,height);
        //setPictureName("t1.gif");
    }
    /**
     * 设置按钮图片
     * @param name 图片名称
     */
    public void setPictureName(String name)
    {
        if(name == null || name.length() == 0)
            return;
        pictureName = name;
        icon = null;
    }
    /**
     * 得到按钮图片
     * @return 图片名称
     */
    public String getPictureName()
    {
      return pictureName;
    }
    /**
     * 设置图标
     * @param icon Icon
     */
    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }
    /**
     * 得到图标
     * @return Icon
     */
    public Icon getIcon()
    {
        if(icon != null)
            return icon;
        if(getPictureName() != null && getPictureName().length() > 0)
            icon = createImageIcon(getPictureName());
        return icon;
    }
    /**
     * 设置图片序号
     * @param index int
     */
    public void setIconIndex(int index)
    {
        this.iconIndex = index;
        if(getIconIndex() != -1)
            setIcon(getPM().getImageManager().getIcon(getIconIndex()));
    }
    /**
     * 得到图片序号
     * @return int
     */
    public int getIconIndex()
    {
        return iconIndex;
    }
    /**
     * 设置是否根据MRNO去患者照片
     * @param isMrnoFS boolean
     */
    public void setMrnoFS(boolean isMrnoFS)
    {
        this.isMrnoFS = isMrnoFS;
    }
    /**
     * 是否根据MRNO去患者照片
     * @return boolean
     */
    public boolean isMrnoFS()
    {
        return isMrnoFS;
    }
    /**
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
        if(filename.toUpperCase().startsWith("%ROOT%") && TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage(filename);
        if(filename.toUpperCase().startsWith("%FILE%"))
        {
            String n = filename.substring(6);
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),n);
        }
        if(filename.toUpperCase().startsWith("%FILE.ROOT%"))
        {
            String n = filename.substring(11);
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),TIOM_FileServer.getRoot() + n);
        }
        if(filename.toUpperCase().startsWith("%FILE.ROOT."))
        {
            String n = filename.substring(11);
            int index = n.indexOf("%");
            if(index == -1)
            {
                System.out.println("没有找到图标" + filename);
                return null;
            }
            String name = n.substring(0,index);
            n = n.substring(index + 1);
            String path = TIOM_FileServer.getRoot() + TIOM_FileServer.getPath(name) + n;
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),path);
        }
        if(filename.toUpperCase().startsWith("%PATPIC%"))
        {
            String n = filename.substring(8);
            if(n.startsWith("<") && n.endsWith(">"))
            {
                n = n.substring(1,n.length() - 1);
                n = getParmValue(n);
            }
            n = StringTool.fill("0",12 - n.length()) + n;
            String p1 = n.substring(0,3);
            String p2 = n.substring(3,6);
            String p3 = n.substring(6,9);
            n = p1 + "\\" + p2 + "\\" + p3 + "\\" + n + ".jpg";
            String path = TIOM_FileServer.getRoot() + TIOM_FileServer.getPath("PatInfPIC.ServerPath") + n;
            return TIOM_FileServer.getImage(TIOM_FileServer.getSocket(),path);
        }

        //
        if( filename.startsWith("C:/hispic/temp/") )
            return TIOM_AppServer.getImage(filename);

        if(TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" + filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if(icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try{
            icon = new ImageIcon(getClass().getResource(path));
        }catch(NullPointerException e)
        {
            System.out.println("没有找到图标" + path);
        }
        return icon;
    }
    public String getParmValue(String name)
    {
        if(isMacroroutineModel())
            return TypeTool.getString(getMacroroutineData(name));
        Object obj = getPM().getFileManager().getParameter();
        if(obj == null || !(obj instanceof TParm))
            return "";
        return ((TParm)obj).getValue(name);
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "图片";
    }
    /**
     * 得到类型
     * @return int
     */
    public int getTypeID()
    {
        return 4;
    }
    /**
     * 设置缩放比例
     * @param scale double
     */
    public void setScale(double scale)
    {
        this.scale = scale;
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getScale()
    {
        return scale;
    }
    /**
     * 设置自动缩放
     * @param autoScale boolean
     */
    public void setAutoScale(boolean autoScale)
    {
        this.autoScale = autoScale;
    }
    /**
     * 是否是自动缩放
     * @return boolean
     */
    public boolean isAutoScale()
    {
        return autoScale;
    }
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        if(getIcon() != null)
        {
            double scale = getScale() / 100.0 * getZoom() / 75.0;
            if(isAutoScale())
            {
                double dw = (double)getEndX() / (double)getIcon().getIconWidth();
                double dy = (double)getEndY() / (double)getIcon().getIconHeight();
                if(dw < dy)
                    scale = dw;
                else
                    scale = dy;
            }
            int w = (int) (getIcon().getIconWidth() * scale + 0.5);
            int h = (int) (getIcon().getIconHeight() * scale + 0.5);
            g.drawImage(((ImageIcon) getIcon()).getImage(),getStartX(),getStartY(),w,h,null);
            /*ImageIcon icon = new ImageIcon(((ImageIcon) getIcon()).getImage().
                                           getScaledInstance(w, h,
                    Image.SCALE_FAST));
            icon.paintIcon((Component) getUI().getTCBase(), g, getStartX(),
                           getStartY());*/
        }
        super.paint(g);
    }
    /**
     * 打印
     * @param g Graphics
     */
    public void print(Graphics g)
    {
        if(getIcon() != null)
        {
            double scale = getScale() / 100.0;
            if(isAutoScale())
            {
                double dw = (double)getEndXP() / (double)getIcon().getIconWidth();
                double dy = (double)getEndYP() / (double)getIcon().getIconHeight();
                if(dw < dy)
                    scale = dw;
                else
                    scale = dy;
            }
            int w = (int) (getIcon().getIconWidth() * scale +
                           0.5);
            int h = (int) (getIcon().getIconHeight() * scale +
                           0.5);
            g.drawImage(((ImageIcon) getIcon()).getImage(),getStartXP(),getStartYP(),w,h,null);
        }
        super.print(g);
    }
    /**
     * 得到属性对话框名称
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VPicDialog.x";
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
        s.writeString(100,getPictureName(),"");
        s.writeDouble(101,getScale(),100);
        s.writeBoolean(102,isAutoScale(),false);
        s.writeInt(103,getIconIndex(),-1);
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
        switch (id)
        {
        case 100:
            setPictureName(s.readString());
            return true;
        case 101:
            setScale(s.readDouble());
            return true;
        case 102:
            setAutoScale(s.readBoolean());
            return true;
        case 103:
            setIconIndex(s.readInt());
            return true;
        }
        return false;
    }

    /**
     * 设置背景图名称
     * @param name String
     */
    public void setPictureNameBackground(String name)
    {
        if(name == null || name.length() == 0)
            return;
        pictureNameBackground = name;
        iconBackground = null;
    }

    /**
     * 得到背景图名称
     * @param name String
     */
    public String getPictureNameBackground()
    {
      return pictureNameBackground;
    }
    /**
     * 设置背景图片
     * @param icon Icon
     */
    public void setIconBackground(Icon icon)
    {
        this.iconBackground = icon;
    }

    /**
     * 得到背景图片
     * @return Icon
     */
    public Icon getIconBackground()
    {
        if(iconBackground != null)
            return iconBackground;
        if(getPictureNameBackground() != null && getPictureNameBackground().length() > 0)
            iconBackground = createImageIcon(getPictureNameBackground());
        return iconBackground;
    }

    /**
     * 设置背景图名称
     * @param name String
     */
    public void setPictureNameForeground(String name)
    {
        if(name == null || name.length() == 0)
            return;
        pictureNameForeground = name;
        iconForeground = null;
    }

    /**
     * 得到背景图名称
     * @param name String
     */
    public String getPictureNameForeground()
    {
      return pictureNameForeground;
    }
    /**
     * 设置背景图片
     * @param icon Icon
     */
    public void setIconForeground(byte[] icon)
    {
        this.iconForeground = icon;
    }

    /**
     * 得到背景图片
     * @return Icon
     */
    public byte[] getIconForeground()
    {
        return iconForeground;
    }
}
