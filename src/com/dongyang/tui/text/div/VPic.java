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
 * <p>Title: ͼƬ</p>
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
     * ͼƬ����
     */
    private String pictureName;
    private Icon icon;
    /**
     * ͼƬ���
     */
    private int iconIndex = -1;
    /**
     * �Ƿ�ȥ������
     */
    private boolean isMrnoFS;
    /**
     * ���ű���
     */
    private double scale = 100;
    /**
     * �Զ�����
     */
    private boolean autoScale;

    /**
     * ����ͼͼƬ����
     */
    private String pictureNameBackground;
    private Icon iconBackground;

    /**
     * ǰ��ͼƬ����
     */
    private String pictureNameForeground;
    private byte[] iconForeground;

    /**
     * ������
     */
    public VPic()
    {
    }
    /**
     * ������
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
     * ���ð�ťͼƬ
     * @param name ͼƬ����
     */
    public void setPictureName(String name)
    {
        if(name == null || name.length() == 0)
            return;
        pictureName = name;
        icon = null;
    }
    /**
     * �õ���ťͼƬ
     * @return ͼƬ����
     */
    public String getPictureName()
    {
      return pictureName;
    }
    /**
     * ����ͼ��
     * @param icon Icon
     */
    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }
    /**
     * �õ�ͼ��
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
     * ����ͼƬ���
     * @param index int
     */
    public void setIconIndex(int index)
    {
        this.iconIndex = index;
        if(getIconIndex() != -1)
            setIcon(getPM().getImageManager().getIcon(getIconIndex()));
    }
    /**
     * �õ�ͼƬ���
     * @return int
     */
    public int getIconIndex()
    {
        return iconIndex;
    }
    /**
     * �����Ƿ����MRNOȥ������Ƭ
     * @param isMrnoFS boolean
     */
    public void setMrnoFS(boolean isMrnoFS)
    {
        this.isMrnoFS = isMrnoFS;
    }
    /**
     * �Ƿ����MRNOȥ������Ƭ
     * @return boolean
     */
    public boolean isMrnoFS()
    {
        return isMrnoFS;
    }
    /**
     * ����ͼƬ
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
                System.out.println("û���ҵ�ͼ��" + filename);
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
            System.out.println("û���ҵ�ͼ��" + path);
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
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "ͼƬ";
    }
    /**
     * �õ�����
     * @return int
     */
    public int getTypeID()
    {
        return 4;
    }
    /**
     * �������ű���
     * @param scale double
     */
    public void setScale(double scale)
    {
        this.scale = scale;
    }
    /**
     * �õ����ű���
     * @return double
     */
    public double getScale()
    {
        return scale;
    }
    /**
     * �����Զ�����
     * @param autoScale boolean
     */
    public void setAutoScale(boolean autoScale)
    {
        this.autoScale = autoScale;
    }
    /**
     * �Ƿ����Զ�����
     * @return boolean
     */
    public boolean isAutoScale()
    {
        return autoScale;
    }
    /**
     * ����
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
     * ��ӡ
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
     * �õ����ԶԻ�������
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "%ROOT%\\config\\database\\VPicDialog.x";
    }
    /**
     * д��������
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
     * ����������
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
     * ���ñ���ͼ����
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
     * �õ�����ͼ����
     * @param name String
     */
    public String getPictureNameBackground()
    {
      return pictureNameBackground;
    }
    /**
     * ���ñ���ͼƬ
     * @param icon Icon
     */
    public void setIconBackground(Icon icon)
    {
        this.iconBackground = icon;
    }

    /**
     * �õ�����ͼƬ
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
     * ���ñ���ͼ����
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
     * �õ�����ͼ����
     * @param name String
     */
    public String getPictureNameForeground()
    {
      return pictureNameForeground;
    }
    /**
     * ���ñ���ͼƬ
     * @param icon Icon
     */
    public void setIconForeground(byte[] icon)
    {
        this.iconForeground = icon;
    }

    /**
     * �õ�����ͼƬ
     * @return Icon
     */
    public byte[] getIconForeground()
    {
        return iconForeground;
    }
}
