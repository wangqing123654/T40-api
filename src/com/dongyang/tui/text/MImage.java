package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.io.File;
import com.dongyang.util.ImageTool;

/**
 * 图片管理类
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2010.11.2
 * @version 1.0
 */
public class MImage
{
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 成员列表
     */
    private TList components;
    /**
     * 构造器
     */
    public MImage()
    {
        components = new TList();
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 增加成员
     * @param image BufferedImage
     */
    public void add(BufferedImage image)
    {
        if (image == null)
            return;
        components.add(image);
    }
    /**
     * 删除成员
     * @param image BufferedImage
     */
    public void remove(BufferedImage image)
    {
        if(image == null)
            return;
        components.remove(image);
    }
    /**
     * 清空
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * 取得成员
     * @param index int
     * @return BufferedImage
     */
    public BufferedImage get(int index)
    {
        return (BufferedImage)components.get(index);
    }
    /**
     * 得到图标
     * @param index int
     * @return Icon
     */
    public Icon getIcon(int index)
    {
        return new ImageIcon(get(index));
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeShort( -1);

        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            BufferedImage item = get(i);
            if(item == null)
                continue;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(item, "JPG", out);
            out.close();
            byte data[] =out.toByteArray();
            s.writeBytes(data);
        }
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
            id = s.readShort();
        }
        //读取样式
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            byte data[] = s.readBytes();
            BufferedImage image = ImageTool.getBufferedImage(data);
            add(image);
        }
    }
}
