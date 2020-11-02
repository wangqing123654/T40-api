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
 * ͼƬ������
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
     * ������
     */
    private PM pm;
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    public MImage()
    {
        components = new TList();
    }
    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * ���ӳ�Ա
     * @param image BufferedImage
     */
    public void add(BufferedImage image)
    {
        if (image == null)
            return;
        components.add(image);
    }
    /**
     * ɾ����Ա
     * @param image BufferedImage
     */
    public void remove(BufferedImage image)
    {
        if(image == null)
            return;
        components.remove(image);
    }
    /**
     * ���
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * ȡ�ó�Ա
     * @param index int
     * @return BufferedImage
     */
    public BufferedImage get(int index)
    {
        return (BufferedImage)components.get(index);
    }
    /**
     * �õ�ͼ��
     * @param index int
     * @return Icon
     */
    public Icon getIcon(int index)
    {
        return new ImageIcon(get(index));
    }
    /**
     * ��Ա����
     * @return int
     */
    public int size()
    {
        return components.size();
    }

    /**
     * д����
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
     * ������
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
        //��ȡ��ʽ
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            byte data[] = s.readBytes();
            BufferedImage image = ImageTool.getBufferedImage(data);
            add(image);
        }
    }
}
