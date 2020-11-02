package com.dongyang.tui;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.ui.util.TDrawTool;

/**
 *
 * <p>Title: Label</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.11
 * @version 1.0
 */
public class DLabel extends DComponent
{
    /**
     * 构造器
     */
    public DLabel()
    {
    }
    /**
     * 重画
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        super.paint(g);
    }
    /**
     * 绘制前景
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {
        super.paintForeground(g,width,height);
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
        //s.writeString(super.getAttributeIDMax() + 1,getText(),null);
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
        /*switch (id - super.getAttributeIDMax())
        {
        case 1:
            setText(s.readString());
            return true;
        case 2:
            setHorizontalAlignment(s.readString());
            return true;
        case 3:
            setVerticalAlignment(s.readString());
            return true;
        }*/
        return false;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<DLabel>tag=");
        sb.append(getTag());
        sb.append(" {");
        sb.append("}");
        return sb.toString();
    }
}
