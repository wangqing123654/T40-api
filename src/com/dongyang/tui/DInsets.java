package com.dongyang.tui;

import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.io.DataOutputStream;

/**
 *
 * <p>Title: 内部尺寸</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.12
 * @version 1.0
 */
public class DInsets
{
    public int top;
    public int left;
    public int bottom;
    public int right;
    /**
     * 构造器
     */
    public DInsets()
    {
    }
    /**
     * 构造器
     * @param top int
     * @param bottom int
     * @param left int
     * @param right int
     */
    public DInsets(int top,int bottom,int left,int right)
    {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
    /**
     * 构造器
     * @param s DataInputStream
     * @throws IOException
     */
    public DInsets(DataInputStream s)
            throws IOException
    {
        readObject(s);
    }
    /**
     * 设置内部尺寸
     * @param top int
     * @param left int
     * @param bottom int
     * @param right int
     */
    public void setInsets(int top,int left,int bottom,int right)
    {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
    /**
     * 内部尺寸是否归零
     * @return boolean
     */
    public boolean zeroth()
    {
        return top == 0 && left == 0 && bottom == 0 && right == 0;
    }
    /**
     * 设置内部尺寸
     * @param insets DInsets
     */
    public void setInsets(DInsets insets)
    {
        setInsets(insets.top,insets.left,insets.bottom,insets.right);
    }
    /**
     * 是否为空
     * @return boolean
     */
    public boolean isEmpty()
    {
        return top == 0 && left == 0 && bottom == 0 && right == 0;
    }
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(top);
        s.writeInt(left);
        s.writeInt(bottom);
        s.writeInt(right);
    }
    public void readObject(DataInputStream s)
      throws IOException
    {
        top = s.readInt();
        left = s.readInt();
        bottom = s.readInt();
        right = s.readInt();
    }
    public DInsets clone()
    {
        return new DInsets(top,bottom,left,right);
    }
    public String toString()
    {
        return "top=" + top + ",bottom=" + bottom + ",left=" + left + ",right=" + right;
    }
}
