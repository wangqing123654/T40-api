package com.dongyang.ui.datawindow;

import java.awt.Color;
import com.dongyang.jdo.TJDOObject;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import com.dongyang.config.INode;
import com.dongyang.manager.TCM_Transform;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * <p>Title: 表格头对象</p>
 *
 * <p>Description: 表格头对象</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.11
 * @version 1.0
 */
public class Header extends TJDOObject
{
    /**
     * xml对象
     */
    private INode node;
    /**
     * 配置文件对象
     */
    private TConfigParm config;
    /**
     * 颜色对象
     */
    private Color color;
    /**
     * 高度
     */
    private double height;
    /**
     * 设置xml对象
     * @param node INode
     */
    public void setNode(INode node)
    {
        this.node = node;
    }
    /**
     * 得到xml对象
     * @return INode
     */
    public INode getNode()
    {
        return node;
    }
    /**
     * 设置配置文件对象
     * @param config TConfigParm
     */
    public void setConfigParm(TConfigParm config)
    {
        this.config = config;
    }
    /**
     * 得到配置文件对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return config;
    }
    /**
     * 设置颜色
     * @param color Color
     */
    public void setColor(Color color)
    {
        this.color = color;
        if(getNode() != null)
            node.setAttributeValue("color",getColorString());
    }
    /**
     * 设置颜色(字符串颜色)
     * @param color String "255,0,0"
     */
    public void setColor(String color)
    {
        if(getConfigParm() != null)
            setColor(StringTool.getColor(color,getConfigParm()));
        else
            setColor(StringTool.getColor(color));
    }
    /**
     * 得到颜色
     * @return Color
     */
    public Color getColor()
    {
        return color;
    }
    /**
     * 得到颜色(字符串颜色)
     * @return String "255,0,0"
     */
    public String getColorString()
    {
        return StringTool.getString(color);
    }
    /**
     * 设置高度
     * @param height double
     */
    public void setHeight(double height)
    {
        this.height = height;
        if(node != null)
            node.setAttributeValue("height",height);
    }
    /**
     * 得到高度
     * @return double
     */
    public double getHeight()
    {
        return height;
    }
    /**
     * 初始化XML对象的node
     * @param node INode
     */
    public void initNode(INode node)
    {
        if(node == null)
            return;
        setNode(node);
        setHeight(TCM_Transform.getDouble(node.getAttributeValue("height")));
        setColor(TCM_Transform.getString(node.getAttributeValue("color")));
    }
    public static void main(String args[])
    {
        Header header = new Header();
        header.setColor("255,0,0");
        header.setHeight(100);
        System.out.println(header);
    }
    public void paint(Graphics g)
    {
        Rectangle rectangleg = g.getClipBounds();
        Color color = getColor();
        if(color != null)
        {
            g.setColor(color);
            g.fillRect(0,0,rectangleg.width,(int)getHeight());
        }
    }
}
