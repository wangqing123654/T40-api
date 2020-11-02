package com.dongyang.util;

import java.awt.image.BufferedImage;
import java.awt.Robot;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.awt.Image;
import com.dongyang.root.util.VideoData;
import java.util.Vector;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.Graphics;
/*import javax.media.Buffer;
import javax.media.format.VideoFormat;
import javax.media.format.RGBFormat;
import javax.media.Format;
import javax.media.Codec;
import javax.media.ResourceUnavailableException;
import javax.media.PlugInManager;
*/
public class ImageTool
{
    /**
     * 得到屏幕尺寸
     * @return Dimension
     */
    public static Dimension getScreenSize()
    {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * 得到屏幕的宽度
     * @return int
     */
    public static int getScreenWidth()
    {
        return getScreenSize().width;
    }

    /**
     * 得到屏幕的高度
     * @return int
     */
    public static int getScreenHeight()
    {
        return getScreenSize().height;
    }

    /**
     * 得到给定尺寸的屏幕抓屏
     * @param rectangle Rectangle
     * @return BufferedImage
     */
    public static BufferedImage getScreenBufferedImage(Rectangle rectangle)
    {
        try
        {
            return (new Robot()).createScreenCapture(rectangle);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到给定尺寸的屏幕抓屏
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @return BufferedImage
     */
    public static BufferedImage getScreenBufferedImage(int x, int y, int width,
            int height)
    {
        return getScreenBufferedImage(new Rectangle(x, y, width, height));
    }

    /**
     * 得到给定尺寸的屏幕抓屏
     * @return BufferedImage
     */
    public static BufferedImage getScreenBufferedImage()
    {
        Dimension dimension = getScreenSize();
        return getScreenBufferedImage(0, 0, dimension.width, dimension.height);
    }

    /**
     * 屏幕抓屏
     * @return byte[]
     */
    public static byte[] getScreenByte()
    {
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            ImageIO.write(getScreenBufferedImage(), "JPG", out);
            out.close();
            return out.toByteArray();
        } catch (Exception e)
        {
            return null;
        }
    }

    public static VideoData getScreenVideo()
    {
        //Buffer buffer = javax.media.util.ImageToBuffer.createBuffer(
        //        getScreenBufferedImage(), 0);
        return null;//createVideoData(buffer);
    }

    /*public static VideoData createVideoData(Buffer buffer)
    {
        if (buffer == null)
            return null;
        VideoFormat format = (VideoFormat) buffer.getFormat();
        if (format == null)
            return null;
        RGBFormat vf;
        Dimension size = format.getSize();
        RGBFormat prefFormat = new RGBFormat(size, size.width * size.height,
                                             Format.intArray,
                                             format.getFrameRate(), 32, -1, -1,
                                             -1, 1, -1, 0, -1);
        int outputData[];

        if (format.matches(prefFormat))
        {
            outputData = (int[]) buffer.getData();
            vf = (RGBFormat) buffer.getFormat();
        } else
        {
            Codec codec = findCodec(format, prefFormat);
            Buffer outputBuffer = new Buffer();
            int retVal = codec.process(buffer, outputBuffer);
            if (retVal != 0)
                return null;
            outputData = (int[]) outputBuffer.getData();
            vf = (RGBFormat) outputBuffer.getFormat();
        }
        VideoData data = new VideoData(vf.getRedMask(), vf.getGreenMask(),
                                       vf.getBlueMask(),
                                       size.width, size.height);
        data.setData(outputData);
        return data;
    }

    private static Codec findCodec(VideoFormat input, VideoFormat output)
    {
        Vector codecList = PlugInManager.getPlugInList(input, output, 2);
        if (codecList == null || codecList.size() == 0)
            return null;
        for (int i = 0; i < codecList.size(); i++)
        {
            String codecName = (String) codecList.elementAt(i);
            Class codecClass = null;
            Codec codec = null;
            try
            {
                codecClass = Class.forName(codecName);
                if (codecClass != null)
                    codec = (Codec) codecClass.newInstance();
            } catch (ClassNotFoundException cnfe)
            {} catch (IllegalAccessException iae)
            {} catch (InstantiationException ie)
            {} catch (ClassCastException cce)
            {}
            if (codec != null && codec.setInputFormat(input) != null)
            {
                Format outputs[] = codec.getSupportedOutputFormats(input);
                if (outputs != null && outputs.length != 0)
                {
                    for (int j = 0; j < outputs.length; j++)
                        if (outputs[j].matches(output))
                        {
                            Format out = codec.setOutputFormat(outputs[j]);
                            if (out != null && out.matches(output))
                                try
                                {
                                    codec.open();
                                    return codec;
                                } catch (Exception rue)
                                {}
                        }

                }
            }
        }

        return null;
    }
*/
    public static Image getImage(VideoData data)
    {
        if(data == null)
            return null;
        DirectColorModel dcm = new DirectColorModel(32, data.getRed(),
                data.getGreen(),
                data.getBlue());
        MemoryImageSource sourceImage = new MemoryImageSource(data.getWidth(),
                data.getHeight(), dcm, data.getData(), 0, data.getWidth());
        return Toolkit.getDefaultToolkit().createImage(sourceImage);
    }

    /**
     * 得到图片
     * @param data byte[]
     * @return BufferedImage
     */
    public static BufferedImage getBufferedImage(byte[] data)
    {
        try
        {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            BufferedImage bi = ImageIO.read(in);
            in.close();
            return bi;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到图片
     * @param data byte[] 图片数据
     * @param width int 宽度
     * @param height int 高度
     * @return Image
     */
    public static Image getImage(byte[] data, int width, int height)
    {
        BufferedImage bufferedImage = getBufferedImage(data);
        if (bufferedImage == null)
            return null;
        return bufferedImage.getScaledInstance(width, height,
                                               Image.SCALE_DEFAULT);
    }

    /**
     * 得到图片
     * @param data byte[] 图片数据
     * @return Image
     */
    public static Image getImage(byte[] data)
    {
        BufferedImage bufferedImage = getBufferedImage(data);
        if (bufferedImage == null)
            return null;
        return bufferedImage.getScaledInstance(bufferedImage.getWidth(),
                                               bufferedImage.getHeight(),
                                               Image.SCALE_DEFAULT);
    }
    /**
     * 缩放图片
     * @param srcImageFile String 图片名
     * @param scale double 缩放比例
     * @param flag boolean true 放大 false 缩小
     * @return Image
     */
    public static Image scale(String srcImageFile, double scale,
                             boolean flag) {
        try{
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            return scale(src,scale,flag);
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 缩放图片
     * @param data byte[] 图片数据
     * @param scale double 缩放比例
     * @param flag boolean true 放大 false 缩小
     * @return Image
     */
    public static Image scale(byte[] data, double scale,
                              boolean flag)
    {
        return scale(getBufferedImage(data),scale,flag);
    }
    /**
     * 缩放图片
     * @param src BufferedImage
     * @param scale double 缩放比例
     * @param flag boolean true 放大 false 缩小
     * @return Image
     */
    public static Image scale(BufferedImage src, double scale,
                              boolean flag)
    {
        if (src == null)
            return null;

        int width = src.getWidth(); // 得到源图宽
        int height = src.getHeight(); // 得到源图长
        if (flag)
        {
            // 放大
            width = (int) (width * scale);
            height = (int) (height * scale);
        } else
        {
            // 缩小
            width = (int) (width / scale);
            height = (int) (height / scale);
        }
        Image image = src.getScaledInstance(width, height,
                                            Image.SCALE_DEFAULT);
        return image;
    }

    /**
     * 类型转换
     * GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
     * @param source String 源文件名
     * @param result String 目标文件名
     * @param type String 转换格式
     */
    public static void convert(String source, String result, String type)
    {
        try
        {
            File f = new File(source);
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, type, new File(result));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 彩色转黑白
     * @param source String 源文件名
     * @param result String 目标文件名
     * @param type String 格式
     */
    public static void gray(String source, String result, String type)
    {
        try
        {
            BufferedImage src = ImageIO.read(new File(source));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, type, new File(result));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        for (int i = 0; i < 10; i++)
        {
            System.out.println("i=" + i);
            byte b[] = getScreenByte();
            try
            {
                ByteArrayInputStream in = new ByteArrayInputStream(b);

                BufferedImage bi = ImageIO.read(in);
                in.close();
                ImageIO.write(bi, "JPG", new File("aaa.jpg"));
            } catch (Exception e)
            {

            }
        }
    }
}
